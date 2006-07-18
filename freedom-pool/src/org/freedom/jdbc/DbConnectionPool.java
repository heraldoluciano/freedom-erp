package org.freedom.jdbc;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.freedom.util.resource.ResourceException;
import org.freedom.util.resource.ResourceKey;
import org.freedom.util.resource.AbstractResourcePool;

/**
 * Pool de conexões JDBC. <BR>
 * Projeto: freedom-pool <BR>
 * Pacote: org.freedom.jdbc <BR>
 * Classe: DbConnectionPool <BR>
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
 * criada: 05/10/2004. <BR>
 */
public class DbConnectionPool extends AbstractResourcePool {

   /** Caminho para o driver JDBC. **/
   private transient String driver;

   /** url URL para a conexão com o banco de dados. **/
   private transient String url;

   /** Número de conexões iniciais. **/
   private transient int initialCons;

   /** Flag que indica se o driver já foi carregado. **/
   private transient boolean driverLoaded = false;

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
    * @param drivercon Caminho para o driver de conexão JDBC.
    * @param urlcon URL para a conexão com o banco de dados.
    * @param nInitialCons Número inicial de conexões.
    * @param nMaxCons Número máximo de conexões.
    */
   public DbConnectionPool(final String drivercon, final String urlcon,
      final int nInitialCons, final int nMaxCons) {
      this(drivercon, urlcon, nInitialCons, nMaxCons, null, null);
   }

   /**
    * Construtor da classe com as informações de usuário e senha.
    * @param drivercon Caminho para o driver de conexão JDBC.
    * @param urlcon URL para a conexão com o banco de dados.
    * @param nInitialCons Número inicial de conexões.
    * @param nMaxCons Número máximo de conexões.
    * @param usercon ID do usuário para conexão com o banco de dados.
    * @param passwordcon Senha do usuário para a conexão com o banco de dados.
    */
   public DbConnectionPool(final String drivercon, final String urlcon,
         final int nInitialCons, final int nMaxCons,
         final String usercon, final String passwordcon) {
      super();
      setInitialCons(nInitialCons);
      setMaxResources(nMaxCons);
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
      return Logger.getLogger("org.freedom.jdbc.DbConnectionPool");
   }

   /**
    * Método que instância e disponibiliza nova conexão de banco de dados.
    * @throws ResourceException Exceção gerada quando não for possível
    * instânciar um novo recurso.
    * @return ResourceKey
    */

   public final ResourceKey createResource() throws ResourceException {
      Connection connection = null;
      ResourceKey resource = null;
      String key = null;
      String pwd = null;
      try {
         if (!driverLoaded) {
            Class.forName(driver);
            driverLoaded = true;
         }
         if ((user == null) || (password == null)) { // se o username ou a
            // password informada
            // estiverem nulos
            // conectará com as informações de web xml
            connection = DriverManager.getConnection(url, userweb, passwordweb);
            key = userweb;
            pwd = passwordweb;
         } else {
            connection = DriverManager.getConnection(url, user, password);
            key = user;
            pwd = password;
         }
         resource = new ResourceKey(sessionID, key, pwd, connection);
      } catch (Exception ex) {
         // ClassNotFoundException ou SQLException
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
      java.sql.Connection connection = null;
      try {
         connection = (Connection)
            resource.getResource();
         connection.close();
      } catch (SQLException ex) {
         LOGGER.error(ex);
         // ignora exceção
      }
   }

   /**
    * Consiste se um recurso é válido.
    * @param resource Recebe o recurso para avaliação.
    * @return Devolve um flag indicando se o recurso é válido.
    */
   public final boolean isResourceValid(final ResourceKey resource) {
      boolean valid = false;
      try {
         final Connection connection = (Connection)
            resource.getResource();
         valid = (!connection.isClosed());
      } catch (SQLException ex) {
         valid = false;
         LOGGER.error(ex);
      }
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
    * @author Robson Sanchez
    * @param passwordcon senha.
    */
   public final void setPassword(final String passwordcon) {
      this.password = passwordcon;
   }

   /**
    * seta a sessão http corrente.
    * @author Robson Sanchez
    * @param sessionIDcon ID da sessão corrente.
    */
   public final void setSessionID(final String sessionIDcon) {
      this.sessionID = sessionIDcon;
   }

   /**
    * Seta o número de conexões iniciais.
    * @author Robson Sanchez
    * @param initial número de conexões iniciais.
    */
   public final void setInitialCons(final int initial) {
      this.initialCons = initial;
   }

   /**
    * Seta o flag indicando se o driver de conexão com banco de dados
    * foi carregado.
    * @param driverLoad Verdadeiro se o driver já foi carregado.
    */
   public final void setDriverLoaded(final boolean driverLoad) {
      this.driverLoaded = driverLoad;
   }
}
