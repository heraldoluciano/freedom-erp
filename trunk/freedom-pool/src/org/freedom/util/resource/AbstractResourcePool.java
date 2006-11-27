package org.freedom.util.resource;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import org.apache.log4j.Logger;


/**
 * Classe Pool de recursos genérico  <BR>
 * Serve para o armazenamento qualquer tipo de objeto.
 * Projeto: freedom-pool <BR>
 * Pacote: org.freedom.util.resource <BR>
 * Classe: @(#)AbstractResourcePool.java <BR>
 * <BR>
 * Este programa é licenciado de acordo com a LGPL (Lesser General Public
 * License), <BR>
 * versão 2.1, Fevereiro de 1999 <BR>
 * A LGPL deve acompanhar todas PUBLICAÇÕES, DISTRIBUIÇÕES e REPRODUÇÕES deste
 * Programa. <BR>
 * Caso uma cópia da LGPL não esteja disponível junto com este Programa, você
 * pode contatar <BR>
 * o LICENCIADOR ou então pegar uma cópia em: <a
 * href=http://creativecommons.org/licenses/LGPL/2.1/legalcode.pt> Creative
 * Commons</a> <BR>
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa é
 * preciso estar de acordo com os termos da LGPL. <BR>
 * <BR>
 * @author Robson Sanchez/Setpoint Informática Ltda. <BR>
 * criada: 24/07/2006. <BR>
 */
public abstract class AbstractResourcePool implements Runnable, HttpSessionBindingListener {

   /** Número inicial de conexões. */
   public static final int INI_CON = 10;

   /** availableRes - Recursos disponíveis. */
   private transient Map availableRes , inUseResources;

   /** maxResources - Número máximo de recursos que podem ser instânciados. */
   private transient int maxResources;

   /** waitIfMaxedOut - Indica se deve aguardar a liberação de recursos. */
   private transient boolean waitIfMaxedOut;

   /** Guarda a última exceção ocorrida ( método @see run() ). */
   private transient ResourceException error = null;

   /** Indica se o comportamento é realmente de um pool de recursos. */
   private transient boolean ispool; 

   /** Log4j da classe. */
   private static final Logger LOGGER = createLogger();

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

   /**
    * Fecha um determinado recurso.
    * @param resource Recurso que devera ser fechado.
    */
   public abstract void closeResource(ResourceKey resource);

   /**
    * Construtor da classe sem parânetros.
    */
   public AbstractResourcePool() {
      this(INI_CON, // por padrão, um máximo de 10 recursos no pool
            false); // não espera pelo recurso se maximizado
   }

   /**
    * Seta o atributo ispool.
    * @param isp Recebe um valor lógico para ispool.
    */
   public final void setIspool(boolean isp) {
      this.ispool = isp;
   }

   /**
    * Retorna flag ispool.
    * @return Retorna um valor lógico indicando o comportamento do pool.
    */
   public final boolean getIspool() {
      return this.ispool;
   }

   /**
    * Construtor da classe com parâmetros iniciais.
    * @param max Número máximo de recursos inicializados.
    * @param waitIfMaxOut Flag que indica se deverá esperar
    * um conexão ser liberada.
    */
   public AbstractResourcePool(final int max, final boolean waitIfMaxOut) {
      availableRes = new HashMap();
      inUseResources = new HashMap();
      this.maxResources = max;
      this.waitIfMaxedOut = waitIfMaxOut;
   }

   /**
    * Retorna um vetor com os recursos disponíveis.
    * @return HashMap com os recursos disponíveis.
    */
   protected final Map getAvailableRes() {
      return this.availableRes;
   }

   /**
    * Valida e retorna os recursos a partir dos parâmetros.
    * @param sessionID Sessão chave.
    * @param key Chave secundária.
    * @param password Senha.
    * @return Retorna um recurso.
    * @throws ResourceException Caso aconteça uma exceção aborta
    * com um ResourceException.
    */
   public final ResourceKey getResource(final String sessionID,
         final String key, final String password) throws ResourceException {
      return getResource(sessionID, key, password, 0);
   }

   /**
    * Retorna um recurso a partir dos parâmetros chave.
    * @param sessionID Sessão chave.
    * @param key Chave secundária.
    * @param password Senha.
    * @param timeout Tempo de espera pelo recurso.
    * @return Retorna um recurso.
    * @throws ResourceException Caso aconteça uma exceção aborta
    * com um ResourceException.
    */
   public final synchronized ResourceKey getResource(
      final String sessionID, final String key, final String password,
      final long timeout) throws ResourceException {
      ResourceKey resource = getFirstAvailableResource(sessionID + key);
      if (resource == null) { // Sem recursos disponíveis
         if ((countResources() < maxResources) || (maxResources == 0)) {
            waitForAvailableResource();
            resource = getFirstResource(sessionID, key, password);
         } else if (waitIfMaxedOut) { // limite máximo de recursos atingido
            try {
               wait(timeout);
            } catch (InterruptedException ex) {
               LOGGER.error(ex);
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

   /** Cria a instância do Log4j da classe.
    ** @return Retorna a instância de log para a classe.
    */
   private static Logger createLogger() {
      return Logger.getLogger("org.freedom.util.resource.AbstractResourcePool");
   }

   /**
    * Recicla o recurso para reutilização.
    * @param resource Recebe o recurso para a reciclagem.
    */
   public final synchronized void recycleResource(final ResourceKey resource) {
      inUseResources.remove(resource.getHashKey());
      availableRes.put(resource.getHashKey(), resource);
      notifyAll(); // notifica threads em espera de con disponíveis
   }

   /**
    * Desliga o Pool fechando todos os recursos.
    */
   public final void shutdown() {
      closeResources(availableRes);
      closeResources(inUseResources);
      availableRes.clear();
      inUseResources.clear();
   }

   /**
    * Inicia o Thread do Pool de recursos.
    */
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

   /**
    * Retorna o primeiro recurso disponível.
    * @param key Chave para buscar o recursos.
    * @return Retorna o recurso disponível.
    */
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

   /**
    * Aguarda a liberação de um recurso.
    * @throws ResourceException Caso aconteça uma exceção aborta
    * o método através de um ResourceException.
    */
   private void waitForAvailableResource() throws ResourceException {
      final Thread thread = new Thread(this);
      thread.start(); // thread cria um recurso: veja run()
      try {
         wait(); // espera que un novo recurso seja criado
         // ou que um recurso seja reciclado
      } catch (InterruptedException ex) {
         LOGGER.error(ex);
      }
      if (error != null) { // exceção pega em run()
         throw error;
      } // reemite exceção pega em run()
   }

   /**
    * Fecha um lista de recursos.
    * @param resources Recebe a lista de recursos a fechar.
    */
   private void closeResources(final Map resources) {
      resources.clear();
   }

   /**
    * Consulta o número de recursos totais.
    * @return Retorna o número de recursos.
    */
   private int countResources() {
      return availableRes.size() + inUseResources.size();
   }

   /**
    * Encontra e retorna o primeiro recurso encontrado com a chave.
    * @param key Chave para a pesquisa do recurso.
    * @return Retorna o recurso encontrado.
    */
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

   /**
    * Retorna o primerio recurso econtrado através de uma chave composta.
    * @param sessionID ID da Sessão chave.
    * @param key Chave secundária.
    * @param password Senha para validação.
    * @return Retorna o recurso encontrado.
    */
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

   /**
    * Limpa o recurso do cache.
    * @param resource ResourceKey Recurso a ser removido.
    */
   public final synchronized void clearResource(final ResourceKey resource) {
      if (availableRes != null) {
         availableRes.remove(resource.getHashKey());
      }
      if (inUseResources != null) {
         inUseResources.remove(resource.getHashKey());
      }
   }

   /**
    * Seta o número máximo de recursos.
    * @param maxRes Recebe o número máximo de recursos.
    */
   public final void setMaxResources(final int maxRes) {
      this.maxResources = maxRes;
   }

   /**
    * Retorna um recurso a partir de uma sessão chave.
    * @param sessionID ID da Sessão chave para a pesquisa.
    * @return Retorna o recurso encontrado.
    */
   public final ResourceKey getResourceSession(final String sessionID) {
      final ResourceKey resource = (ResourceKey)
         inUseResources.get(sessionID);
      return resource;
   }

   /**
    * Método executado quando uma sessão http for iniciada.
    * @param event HttpSessionBindingEvent.
    */
   public final void valueBound(final HttpSessionBindingEvent event) {
   }

   /**
    * Método será executado quando uma sessão http expirar.
    * @param event HttpSessionBindingEvent.
    */
   public final void valueUnbound(final HttpSessionBindingEvent event) {
      final String sessionID = event.getSession().getId();
      final ResourceKey resource = getResourceSession(sessionID);
      if (getIspool()) {
         recycleResource(resource);
      } else {
         closeResource(resource);
      }
   }
}
