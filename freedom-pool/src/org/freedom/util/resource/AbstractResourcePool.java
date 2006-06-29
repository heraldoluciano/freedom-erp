package org.freedom.util.resource;

import java.util.HashMap;
import java.util.Map;

/**
 * Pool de recursos genérico.
 * Serve para o armazenamento qualquer tipo de objeto.
 * @author: Robson Sanchez/Setpoint Informática Ltda.
 */
public abstract class AbstractResourcePool implements Runnable {

   /** availableRes - Recursos disponíveis. */
   private transient Map availableRes , inUseResources;

   /** maxResources - Número máximo de recursos que podem ser instânciados. */
   private transient int maxResources;

   /** waitIfMaxedOut - Indica se deve aguardar a liberação de recursos. */
   private transient boolean waitIfMaxedOut;

   /** error - Guarda a última exceção ocorrida ( método @see run() ). */
   private transient ResourceException error = null;

   // Extensões têm que implementar estes três métodos
   /**
    * Criador de recursos.
    * @throws ResourceException Exceção na criação de recurso.
    * @return Retorna um recurso.
    */
   public abstract ResourceKey createResource() throws ResourceException;

   /**
    * Verifica se um recurso é válido.
    * @param resource Recebe um recurso para avaliação.
    * @return Indica se o recurso é válido.
    */
   public abstract boolean isResourceValid(ResourceKey resource);

   public abstract void closeResource(ResourceKey resource);

   public AbstractResourcePool() {
      this(10, // por padrão, um máximo de 10 recursos no pool
            false); // não espera pelo recurso se maximizado
   }
   
   public AbstractResourcePool(int max, boolean waitIfMaxedOut) {
      availableRes = new HashMap();
      inUseResources = new HashMap();
      this.maxResources = max;
      this.waitIfMaxedOut = waitIfMaxedOut;
   }
   
   protected final Map getAvailableRes() {
      return this.availableRes;
   }

   public ResourceKey getResource(String sessionID, String key, String password)
         throws ResourceException {
      return getResource(sessionID, key, password, 0);
   }

   public final synchronized ResourceKey getResource(
      final String sessionID, final String key, final String password,
      final long timeout) throws ResourceException {
      ResourceKey resource = getFirstAvailableResource(sessionID + key);
      if (resource == null) { // Sem recursos disponíveis
         if (countResources() < maxResources) {
            waitForAvailableResource();
            resource = getFirstResource(sessionID, key, password);
         } else if (waitIfMaxedOut) { // limite máximo de recursos atingido
            try {
               wait(timeout);
            } catch (InterruptedException ex) {
            }
            resource = getFirstResource(sessionID, key, password);
         } else {
            throw new ResourceException(
               "Número máximo de recursos atingidos. Tente mais tarde.");
         }
      }
      if (resource != null) {
         resource.setSessionID(sessionID);
         inUseResources.put(resource.getHashKey(), resource);
      }
      return resource;
   }

   public final synchronized void recycleResource(ResourceKey resource) {
      inUseResources.remove(resource.getHashKey());
      availableRes.put(resource.getHashKey(), resource);
      notifyAll(); // notifica threads em espera de con disponíveis
   }

   public final void shutdown() {
      closeResources(availableRes);
      closeResources(inUseResources);
      availableRes.clear();
      inUseResources.clear();
   }

   public final synchronized void run() {
      ResourceKey resource;
      ResourceException errorrun = null;
      try {
         resource = createResource(); // criação de subclasses
         if (resource != null) {
            availableRes.put(resource.getHashKey(), resource);
         }
      } catch (ResourceException ex) {
         errorrun = ex; // armazena a exceção
      }
      error = errorrun;
      notifyAll(); // notifica threas em espera
   }

   private ResourceKey getFirstAvailableResource(final String key) {
      ResourceKey resource = null;

      if (availableRes.size() > 0) {
         resource = getFirstResource(key);
      }
      if ((resource != null) && (!isResourceValid(resource))) {
         resource = getFirstAvailableResource(key); // tenta novamente
      }
      return resource;
   }

   private void waitForAvailableResource() throws ResourceException {
      final Thread thread = new Thread(this);
      thread.start(); // thread cria um recurso: veja run()
      try {
         wait(); // espera que un novo recurso seja criado
         // ou que um recurso seja reciclado
      } catch (InterruptedException ex) {
      }
      if (error != null) { // exceção pega em run()
         throw error;
      } // reemite exceção pega em run()
   }

   private void closeResources(final Map resources) {
      resources.clear();
   }

   private int countResources() {
      return availableRes.size() + inUseResources.size();
   }

   private ResourceKey getFirstResource(final String key) {
      String keypesq = null;
      if (key == null) {
         keypesq = "";
      } else {
         keypesq = key;
      }
      final ResourceKey resource = (ResourceKey)
         availableRes.get(keypesq);
      if (resource != null) {
         availableRes.remove(key);
      }
      return resource;
   }

   private ResourceKey getFirstResource(final String sessionID,
         final String key, final String password) {
      final String keypesq = (key == null ? "" : key);
      final ResourceKey resource = (ResourceKey)
         availableRes.get(sessionID + keypesq);
      if (resource != null && resource.getPassword().equals(password)) {
            availableRes.remove(resource.getHashKey());
      }
      return resource;
   }

   public final ResourceKey getResourceSession(final String sessionID) {
      final ResourceKey resource = (ResourceKey)
         inUseResources.get(sessionID);
      return resource;
   }

}
