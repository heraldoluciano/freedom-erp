package org.freedom.infra.model.jpa;
import java.lang.reflect.Method;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.apache.log4j.Logger;

public class Crud {
	private EntityManagerFactory emf = null;
	private EntityManager em = null;
	private EntityTransaction tx = null;
	private static final Logger LOGGER = Logger.
    	getLogger("org.freedom.infra.model.jpa.EnityManager");
	public Crud(String persistUnit) {
		 if (emf==null) {
			try {
				emf = Persistence.createEntityManagerFactory( persistUnit );
				em = emf.createEntityManager();
				tx = em.getTransaction();
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
			}
		 }
	}
	
	public void persist(Object object) {
		try {	
			
			tx.begin();
			
			em.persist( object );
						
			tx.commit();
			 
		}
		catch ( RuntimeException e ) {
			LOGGER.error(e.getMessage(), e);
			tx.rollback();
		}
	}
	
	public void remove(PersistObject object) {

		try {
			Object tmp = em.find(object.getClass(), ((PersistObject) object).getKey());
			
			tx.begin();
			
            em.remove( tmp );
            
			tx.commit();
						
		}
		catch ( RuntimeException e ) {
			LOGGER.error(e.getMessage(), e);
			tx.rollback();
		}

	}
	
	public PersistObject find(PersistObject argobj) {
		PersistObject object = null;
		try {
			
			Method mt = argobj.getClass().getMethod("getKey");			
			Key key = (Key) mt.invoke(argobj,null);

			object = (PersistObject) find(argobj.getClass(), key);
		}
		catch ( Exception e ) {
			LOGGER.error(e.getMessage(), e);
			tx.rollback();
		}
		
		return object;
	}
	
	public PersistObject find(Class<? extends PersistObject> clas, Key key) {
		
		PersistObject object = null;
		try {
			
			tx.begin();

			object = em.find(clas, key);
			
			tx.commit(); 
			
		}
		catch ( Exception e ) {
			LOGGER.error(e.getMessage(), e);
			tx.rollback();
		}
        return object;
	}
	
}
