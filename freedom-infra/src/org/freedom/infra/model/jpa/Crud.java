package org.freedom.infra.model.jpa;
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
	
	/*public void persist() {
		try {	
			
			Entidade ent = (Entidade) objeto;
			
			System.out.println("CIDADE TESTE:" + ent.getCidade());
			
			tx.begin();
			
			manager.persist( objeto );
						
			tx.commit();
			 
		}
		catch ( RuntimeException e ) {
			e.printStackTrace();
			tx.rollback();
		}
	}
	
	public void remove(PersistObject obj) {

		try {
			Objeto tmp = (Objeto) manager.find(objeto.getClass(), ((Objeto) objeto).getChave());
			
			tx.begin();
			
            manager.remove( tmp );
            
            objeto = tmp;
            
			tx.commit();
						
		}
		catch ( RuntimeException e ) {
			e.printStackTrace();
			tx.rollback();
		}

	}
	
	public void find() {
		
		try {
			
			Constructor ct = objeto.getClass().getConstructor(null);			
			Objeto objtst = (Objeto) ct.newInstance(null);			
			Method mt = objtst.getClass().getMethod("getChave");			
			Object chave = mt.invoke(objeto,null);
			
			tx.begin();
			
			objtst = manager.find(objtst.getClass(), chave );			
			objeto = objtst;
								
			tx.commit(); 
			
//			manager.flush();
//			manager.close();
			
		}
		catch ( Exception e ) {
			e.printStackTrace();
			tx.rollback();
		}

	}
	*/
}
