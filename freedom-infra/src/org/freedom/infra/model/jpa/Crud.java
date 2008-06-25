
package org.freedom.infra.model.jpa;

import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.freedom.infra.beans.LoggerManager;

public class Crud {

	private EntityManagerFactory emf = null;

	private EntityManager em = null;

	private EntityTransaction tx = null;

	private static final Logger LOGGER = LoggerManager.getLogger( "org.freedom.infra.model.jpa.Crud" );

	public Crud( String persistUnit ) {
		this(persistUnit, null);
	}
	
	public Crud( String persistUnit, Map properties ) {

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
				// LOGGER.error(e.getMessage(), e);
			}
		}
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
