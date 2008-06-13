
package org.freedom.infra.model.jpa;

import java.lang.reflect.Method;

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

		if ( emf == null ) {
			try {
				emf = Persistence.createEntityManagerFactory( persistUnit );
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

	public void persist( Object object ) {

		try {
			tx = em.getTransaction();

			tx.begin();

			Object tmp = em.merge( object );

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

	public PersistObject find( PersistObject argobj ) {

		PersistObject object = null;
		try {

			Method mt = argobj.getClass().getMethod( "getKey" );
			Key key = (Key) mt.invoke( argobj, null );

			object = (PersistObject) find( argobj.getClass(), key );
		}
		catch ( Exception e ) {
			e.printStackTrace();
			LOGGER.error( e.getMessage(), e );
			tx.rollback();
		}

		return object;
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

			object = em.find( clas, key );

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

}
