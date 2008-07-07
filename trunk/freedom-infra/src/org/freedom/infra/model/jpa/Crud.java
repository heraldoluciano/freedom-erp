
package org.freedom.infra.model.jpa;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import org.freedom.infra.util.crypt.SimpleCrypt;
import org.freedom.infra.util.ini.ManagerIni;
import org.freedom.infra.util.logger.FreedomLogger;

public class Crud {

	private EntityManagerFactory emf = null;

	private EntityManager em = null;

	private EntityTransaction tx = null;

	private static Logger LOGGER = null; 
	
	public static int PERSIST_FRAMEWORK_TOPLINK = 1;

	public Crud( String persistUnit ) {
		this(persistUnit, null);
	}
	
	public Crud( int persistFramework, String persistUnit, String initFile, 
			String sessionName, String userParam, String passwordParam) {
		this( persistUnit, getParamsDB( persistFramework, initFile, sessionName, userParam, passwordParam) );
		
	}
	public Crud( String persistUnit, Map<String, String> properties ) {
		
		LOGGER = FreedomLogger.getLogger(this.getClass(), FreedomLogger.LOGGER_JPA);
				
		if ( emf == null ) {
			try {
				if (properties==null) {
					emf = Persistence.createEntityManagerFactory( persistUnit );
				} else {
					emf = Persistence.createEntityManagerFactory( persistUnit, properties );
				}
				em = emf.createEntityManager();
				tx = em.getTransaction();
			}
			catch ( Exception e ) {
				e.printStackTrace();
				LOGGER.error(e.getMessage(), e);
			}
		}
	}

	private static Map<String, String> getParamsDB(final int persistFramework, 
			final String initFile, final String sessionName, final String userParam, 
			final String passwordParam) {
		
		Map<String, String> result = new HashMap<String, String>();
		
		String username = null;
		String password = null;
		try {
			ManagerIni ini = ManagerIni.createManagerIniFile( initFile );
			username = ini.getProperty( sessionName, userParam );
			password = SimpleCrypt.decrypt( ini.getProperty( sessionName, passwordParam ) );
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if ( persistFramework == PERSIST_FRAMEWORK_TOPLINK ) {
			result.put("toplink.jdbc.user", username);
			result.put("toplink.jdbc.password", password);
		}
		return result;
	}
	
	public EntityTransaction getTransaction() {

		return this.tx;
	}
	
	public EntityManager getEm() {
	
		return em;
	}

	public void persist( PersistObject entity ) {

		try {
			tx = em.getTransaction();

			tx.begin();
			
			Key keyback = entity.getKey();

			PersistObject tmp = em.merge( entity );
			
			tmp.setKey( keyback );

			em.persist( tmp );

			tx.commit();

		}
		catch ( RuntimeException e ) {
			e.printStackTrace();
			LOGGER.error( e.getMessage(), e );
			tx.rollback();
		}
	}

	public void remove( PersistObject object ) {

		try {
			
			Object tmp = em.find( object.getClass(), ( (PersistObject) object ).getKey() );

			tx = em.getTransaction();

			tx.begin();

			em.remove( tmp );

			tx.commit();
		}
		catch ( RuntimeException e ) {
			e.printStackTrace();
			LOGGER.error( e.getMessage(), e );
			tx.rollback();
		}

	}

	public Query createQuery( String sql ) {

		return em.createQuery( sql );
	}

	public Query createJDBCQuery( String sql ) {

		return em.createNativeQuery( sql );
	}

	public PersistObject find( Class<? extends PersistObject> clas, Key key ) {

		PersistObject object = null;
		try {

			tx = em.getTransaction();

			tx.begin();
			
			Key keyback = key;

			object = em.find( clas, key );
			
			object.setKey( keyback );

			// REVISAR
			// SEM ESTE, NÃO ESTAVA ATUALIZANDO.
			em.refresh( object );

			tx.commit();
		}
		catch ( Exception e ) {
			e.printStackTrace();
			LOGGER.error( e.getMessage(), e );
			tx.rollback();
		}
		return object;
	}

	public PersistObject find( PersistObject argobj ) {
	
		PersistObject object = null;
		
		if ( argobj != null ) {			
			try {				
				object = find( argobj.getClass(), argobj.getKey() );
			}
			catch ( Exception e ) {
				e.printStackTrace();
				LOGGER.error( e.getMessage(), e );
			}
		}
	
		return object;
	}

}
