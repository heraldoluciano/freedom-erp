package org.freedom.jpa;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import oracle.toplink.essentials.config.TopLinkProperties;

import org.apache.log4j.Logger;
import org.freedom.util.resource.ResourceException;
import org.freedom.util.resource.ResourceKey;
import org.freedom.util.resource.AbstractResourcePool;


/**
 * Classe Pool de conexões JPA. <BR>
 * Projeto: freedom-pool <BR>
 * Pacote: org.freedom.jpa <BR>
 * Classe: @(#)DbConnectionPool.java <BR>
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
 * criada: 12/12/2006. <BR>
 *  <BR>
 *  @see org.freedom.util.resource.AbstractResourcePool 
 */
public class DbConnectionPool extends AbstractResourcePool {

   /** Caminho para o driver JDBC. **/
   private transient String driver;

   /** url URL para a conexão com o banco de dados. **/
   private transient String url;
   
   /** nome do descritor de parâmetros. **/
   private transient String dscFact;

   /** Fabrica de conexões. **/
   private transient EntityManagerFactory factory;

   /** Manipulador de entidades. **/
   private transient EntityManager manager;
   
   /** Número de conexões iniciais. **/
   private transient int initialCons;

   /** ID do usuário para conexão com banco de dados. **/
   private transient String user;

   /** Senha do usuário de banco de dados. **/
   private transient String password;

   /** Usuário para o pool de conexões configurado no web.xml. **/
   private transient String userweb;

   /** Senha para o pool de conexões configurado no web.xml. **/
   private transient String passwordweb;

   /** ID da sessão do servidor http. Utilizado como chave para pool. **/
   private transient String sessionID;

   /** Log4j da classe. **/
   private static final Logger LOGGER = createLogger();

   /**
    * Construtor da classe sem as informações de usuário e senha.
    * @param fact Descritor da conexão TopLink.
    * @param nInitialCons Número inicial de conexões.
    * @param nMaxCons Número máximo de conexões.
    * @param isp Seta o comportamento do pool.
    */
   public DbConnectionPool(final String drivercon, final String urlcon, final String fact,
      final int nInitialCons, final int nMaxCons, final boolean isp) {
      this(drivercon, urlcon, fact, nInitialCons, nMaxCons, null, null, isp);
   }

   /**
    * Construtor da classe com as informações de usuário e senha.
    * @param fact Descritor das conexões TopLink.
    * @param nInitialCons Número inicial de conexões.
    * @param nMaxCons Número máximo de conexões.
    * @param usercon ID do usuário para conexão com o banco de dados.
    * @param passwordcon Senha do usuário para a conexão com o banco de dados.
    * @param isp Seta o comportamento do pool.
    * @see org.freedom.jdbc.DbConnectionPool#org.freedom.jdbc.DbConnectionFactory
    * @see DbConnectionPoll#AbstractResourcePoll
    * @throws ResourceException Exceção gerada quando não for possível 
    */
   public DbConnectionPool(final String drivercon, final String urlcon, final String fact,
         final int nInitialCons, final int nMaxCons,
         final String usercon, final String passwordcon, final boolean isp) {
      super();
      dscFact = fact;
      setInitialCons(nInitialCons);
      setMaxResources(nMaxCons);
      setIspool(isp);
      this.driver = drivercon;
      this.url = urlcon;
      ResourceKey resource;
      if ((usercon != null) || (passwordcon != null)) { // se o usuário e senha
         // estiverem definidos no
         // web xml
         this.userweb = usercon;
         this.passwordweb = passwordcon;
         try {
            for (int i = 0; i < initialCons; i++) {
               resource = createResource();
               getAvailableRes().put(resource.getHashKey(), resource);
            }
         } catch (Exception ex) { 
            LOGGER.error(ex);
         }
      }
   }

   /**
    * Cria uma instância de Log4j para a classe.
    * @return Retorna o log instânciado.
    */
   private static Logger createLogger() {
      return Logger.getLogger("org.freedom.jpa.DbConnectionPool");
   }

   /**
    * Método que instância e disponibiliza nova conexão de banco de dados.
    * @throws ResourceException Exceção gerada quando não for possível
    * instânciar um novo recurso.
    * @return ResourceKey
    */

   public final ResourceKey createResource() throws ResourceException {
      ResourceKey resource = null;
      String key = null;
      String pwd = null;
      Map<String, String> connectProps = new HashMap<String, String>();
      try {
         connectProps.put(TopLinkProperties.JDBC_DRIVER, driver);
         connectProps.put(TopLinkProperties.JDBC_URL, url);
         if ((user == null) || (password == null)) { // se o username ou a
            // password informada
            // estiverem nulos
            // conectará com as informações de web xml
            connectProps.put(TopLinkProperties.JDBC_USER, userweb);
            connectProps.put(TopLinkProperties.JDBC_PASSWORD, passwordweb);
            key = userweb;
            pwd = passwordweb;
         } else {
            connectProps.put(TopLinkProperties.JDBC_USER, user);
            connectProps.put(TopLinkProperties.JDBC_PASSWORD, password);
            key = user;
            pwd = password;
         }
         factory = Persistence.createEntityManagerFactory(dscFact, connectProps);
         manager = factory.createEntityManager();
         resource = new ResourceKey(sessionID, key, pwd, manager);
      } catch (Exception ex) {
         // ClassNotFoundException ou SQLException
         ex.printStackTrace();
         LOGGER.error(ex);
         throw new ResourceException(ex.getMessage());
      }
      return resource;
   }

   /**
    * Fecha as conexões com banco de dados.
    * @param resource Recebe o recurso a ser finalizado.
    */
   public final void closeResource(final ResourceKey resource) {
      EntityManager manager = null;
      clearResource(resource);
      manager = (EntityManager)
         resource.getResource();
      manager.close();
   }

   /**
    * Consiste se um recurso é válido.
    * @param resource Recebe o recurso para avaliação.
    * @return Devolve um flag indicando se o recurso é válido.
    */
   public final boolean isResourceValid(final ResourceKey resource) {
      boolean valid = false;
      final EntityManager manager = (EntityManager)
         resource.getResource();
      valid = (manager.isOpen());
      return valid;
   }

   /**
    * Seta o usuário corrente para conexão.
    * @author Robson Sanchez/Setpoint Informática Ltda.
    * @param usercon ID do usuário.
    */
   public final void setUser(final String usercon) {
      this.user = usercon;
   }

   /**
    * Seta senha corrente.
    * @author Robson Sanchez/Setpoint Informática Ltda.
    * @param passwordcon senha.
    */
   public final void setPassword(final String passwordcon) {
      this.password = passwordcon;
   }

   /**
    * seta a sessão http corrente.
    * @author Robson Sanchez/Setpoint Informática Ltda.
    * @param sessionIDcon ID da sessão corrente.
    */
   public final void setSessionID(final String sessionIDcon) {
      this.sessionID = sessionIDcon;
   }

   /**
    * Seta o número de conexões iniciais.
    * @author Robson Sanchez/Setpoint Informática Ltda.
    * @param initial número de conexões iniciais.
    */
   public final void setInitialCons(final int initial) {
      this.initialCons = initial;
   }

   
}
