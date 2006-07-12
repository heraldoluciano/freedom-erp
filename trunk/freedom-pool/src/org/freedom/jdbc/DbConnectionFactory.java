package org.freedom.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.freedom.util.resource.ResourceException;
import org.freedom.util.resource.ResourceKey;

/**
 * Classe de conexão com o banco de dados <BR>
 * Projeto: org.freedom.jdbc <BR>
 * Pacote: org.freedom.jdbc <BR>
 * Classe: DbConnectionFactory.java <BR>
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
    * @return Retorna uma conexão JDBC.
    * @throws SQLException Propaga exceções JDBC.
    */
   public java.sql.Connection getConnection(final ServletContext context,
         final String sessionID) throws SQLException {
      java.sql.Connection conn = null;
      try {
         conn = getConnection(context, sessionID, "", "");
         // conn = dataSource.getConnection();
      } catch (SQLException esql) {
         getLogger().error(esql);
         throw esql;
      }
      return conn;
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
      ResourceKey resource = null;
      final DbConnectionPool pool = (DbConnectionPool)
         context.getAttribute("db-connection-pool");
      resource = pool.getResourceSession(sessionID);
      if (resource != null) {
         pool.closeResource(resource);
      }
   }

   /**
    * Retorna a conexão JDBC consistindo usuário e senha.
    * @param context Contexto do aplicativo para pesquisar o pool.
    * @param sessionID Identificação da sessão chave.
    * @param useridcon ID. do usuário a consistir.
    * @param passwordcon Senha do usuário a consistir.
    * @return Retorna uma referência para objeto de conexão JDBC.
    * @throws SQLException Propaga uma exceção SQL caso não encontre um
    * objeto consistente.
    */
   public java.sql.Connection getConnection(
         final ServletContext context, final String sessionID,
         final String useridcon, final String passwordcon) throws SQLException {
      java.sql.Connection conn = null;
      ResourceKey resource = null;
      final DbConnectionPool pool = (DbConnectionPool)
         context.getAttribute("db-connection-pool");
      resource = pool.getResourceSession(sessionID);
      if (resource == null) {
         try {
            if (useridcon != null && !useridcon.equals("")
                  && passwordcon != null && !passwordcon.equals("")) {
               pool.setUser(useridcon);
               pool.setPassword(passwordcon);
               pool.setSessionID(sessionID);
            }
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
            conn = (java.sql.Connection)
               resource.getResource();
         } catch (ResourceException e) {
            getLogger().error(e);
         }
      }
      return conn;
   }

   /**
    * Cria uma instância do log4j da classe.
    * @return Retorna a instância do log da classe.
    */
   private static Logger createLogger() {
      return Logger.getLogger("org.freedom.jdbc.DbConnectionFactory");
   }

   /**
    * Fecha a conexão JDBC, bem como os recursos SQL.
    * @param conn Recebe como parâmetro a conexão.
    * @param statement Sentença SQL aberta.
    * @param resultset ResultSet aberto.
    */
   public void closeConnection(final java.sql.Connection conn,
         final PreparedStatement statement, final ResultSet resultset) {
      if (resultset != null) {
         try {
            resultset.close();
         } catch (SQLException e) {
            getLogger().error(e);
         }
      }
      if (statement != null) {
         try {
            statement.close();
         } catch (SQLException e) {
            getLogger().error(e);
         }
      }
      if (conn != null) {
         try {
            conn.close();
         } catch (SQLException e) {
            getLogger().error(e);
         }
      }
   }

}
