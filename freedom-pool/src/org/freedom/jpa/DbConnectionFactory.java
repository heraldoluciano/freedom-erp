package org.freedom.jpa;

import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.freedom.util.resource.ResourceException;
import org.freedom.util.resource.ResourceKey;

/**
 * Classe de conexão com o banco de dados especificação JPA <BR>
 * Projeto: org.freedom.jpa <BR>
 * Pacote: org.freedom.jpa <BR>
 * Classe: @(#)DbConnectionFactory.java <BR>
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
 * criada: 11/12/2006. <BR>
 */


public final class DbConnectionFactory {

   /** Log da classe. **/
   private static final Logger LOGGER = createLogger();

   /** Instância no formato Singleton. **/
   private static final DbConnectionFactory INSTANCE =
      new DbConnectionFactory();

   /** Construtor da classe. **/
   private DbConnectionFactory() {
      //Não tem conteúdo.
   }

   /**
    
    * Tem como objetivo retornar a instância da classe.
    * @return Retorna a instância da classe DbConnectionFactory.
    */
   public static DbConnectionFactory getInstance() {
      return INSTANCE;
   }

   /**
    * Retorna a instância do Log.
    * @return Retorna um Log4j.
    */
   private Logger getLogger() {
      return LOGGER;
   }

   /**
    * Retorna uma conexão do concentrador.
    * @param context Contexto http.
    * @param sessionID Sessão utilizada como chave para o recurso.
    * @return Retorna uma conexão JPA.
    * @throws SQLException Propaga exceções JPA.
    */
   public EntityManager  getConnection(final ServletContext context,
         final String sessionID, final String factory) throws SQLException {
      EntityManager manager = null;
      try {
         manager = getConnection(context, sessionID, factory, "", "");
         // conn = dataSource.getConnection();
      } catch (SQLException esql) {
         getLogger().error(esql);
         throw esql;
      }
      return manager;
   }

   /**
    * Libera um recurso para ser reutilizado.
    * @param context Contexto da aplicação.
    * @param sessionID Sessão utilizada como chave.
    * @throws ResourceException Propaga exceção caso não possa
    * liberar o recurso.
    */
   public void recycleConnection(final ServletContext context,
         final String sessionID) throws ResourceException {
      ResourceKey resource = null;
      final DbConnectionPool pool = (DbConnectionPool)
         context.getAttribute("db-connection-pool");
      try {
         resource = pool.getResourceSession(sessionID);
         if (resource != null) {
            pool.recycleResource(resource);
         }
      } catch (Exception e) {
         getLogger().error(e);
         throw new ResourceException(e.getMessage());
      }
   }

   /**
    * Fecha a conexão com banco de dados JDBC.
    * @param context Contexto da aplicação para buscar o pool de recursos.
    * @param sessionID Sessão chave para o recurso.
    * @throws ResourceException Propaga a exceção se não for possível
    * fechar a conexão.
    */
   public void closeConnection(final ServletContext context,
         final String sessionID) throws ResourceException {
      this.closeConnection(context, sessionID, null, null);
   }

   /**
    * Fecha a conexão com banco de dados JDBC.
    * @param context Contexto da aplicação para buscar o pool de recursos.
    * @param sessionID Sessão chave para o recurso.
    * @param useriddb ID do usuário conectado.
    * @param passworddb Senha do usuário.
    * @throws ResourceException Propaga a exceção se não for possível
    * fechar a conexão.
    */
   public void closeConnection(final ServletContext context,
         final String sessionID, final String useriddb, final String passworddb)
         throws ResourceException {
      ResourceKey resource = null;
      final DbConnectionPool pool = (DbConnectionPool)
         context.getAttribute("db-connection-pool");
      if (useriddb == null) {
         resource = pool.getResourceSession(sessionID);
      } else {
         resource = pool.getResource(sessionID, useriddb, passworddb);
      }
      if (resource != null) {
         pool.closeResource(resource);
      }
   }

   /**
    * Retorna a conexão JPA consistindo usuário e senha.
    * @param context Contexto do aplicativo para pesquisar o pool.
    * @param sessionID Identificação da sessão chave.
    * @param fact String com o descritor para a conexão.
    * @param useridcon ID. do usuário a consistir.
    * @param passwordcon Senha do usuário a consistir.
    * @return Retorna uma referência para objeto de conexão JDBC.
    * @throws SQLException Propaga uma exceção SQL caso não encontre um
    * objeto consistente.
    */
   public EntityManager getConnection(
         final ServletContext context, final String sessionID,
         final String fact, final String useridcon,
         final String passwordcon) throws SQLException {
      final DbConnectionPool pool = (DbConnectionPool)
         context.getAttribute("db-connection-pool");
      EntityManager manager = null;
      ResourceKey resource = null;
      resource = pool.getResourceSession(sessionID);
      if (resource == null) {
         try {
            if (useridcon != null && !useridcon.equals("")
                  && passwordcon != null && !passwordcon.equals("")) {
               pool.setUser(useridcon);
               pool.setPassword(passwordcon);
               pool.setSessionID(sessionID);
            }
            resource = pool.getResource(sessionID, useridcon, passwordcon);
            if (resource == null
                  || !resource.getPassword().equals(passwordcon)) {
               recycleConnection(context, sessionID); // Recicla a
               // conexão e
               // retorna
               // erro
               throw new ResourceException("Senha inválida."); // Exceção
               // de senha
               // inválida
            }
            manager = (EntityManager)
               resource.getResource();
         } catch (ResourceException e) {
            getLogger().error(e);
         }
      }
      return manager;
   }

   /**
    * Cria uma instância do log4j da classe.
    * @return Retorna a instância do log da classe.
    */
   private static Logger createLogger() {
      return Logger.getLogger("org.freedom.jpa.DbConnectionFactory");
   }

   /**
    * Fecha a conexão JPA, bem como os recursos SQL.
    * @param manager Recebe como parâmetro a conexão.
    */
   public void closeConnection(final EntityManager manager) {
      if (manager != null) {
         manager.close();
      }
   }

}
