/*
 * Created on 05/10/2004
 * Autor: Robson Sanchez
 * Descrição: Classe de conexão com banco de dados
 */
package org.freedom.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletContext;

import org.freedom.util.resource.ResourceException;
import org.freedom.util.resource.ResourceKey;

/**
 * @author robson TODO To change the template for this generated type comment go
 *         to Window - Preferences - Java - Code Style - Code Templates
 */

public class DbConnectionFactory {
   // protected static ServletContext context;
   protected static DbConnectionPool pool;

   /*
    * protected static void initPool() throws Exception { if (pool == null) {
    * try { } catch (Exception ex) { System.out.println(ex.getMessage()); throw
    * ex; } } }
    */

   public static java.sql.Connection getConnection(final ServletContext context,
         final String sessionID) throws SQLException {
      java.sql.Connection conn = null;
      try {
         conn = getConnection(context, sessionID, "", "");
         // conn = dataSource.getConnection();
      } catch (SQLException esql) {
         throw esql;
      }
      return conn;
   }

   public static void recycleConnection(final ServletContext context,
         final String sessionID) throws ResourceException {
      ResourceKey resource = null;

      pool = (DbConnectionPool)
         context.getAttribute("db-connection-pool");
      try {
         resource = pool.getResourceSession(sessionID);
         if (resource != null) {
            pool.recycleResource(resource);
         }
      } catch (Exception e) {
         throw new ResourceException(e.getMessage());
      }
   }

   public static void closeConnection(final ServletContext context,
         final String sessionID) throws ResourceException {
      ResourceKey resource = null;
      pool = (DbConnectionPool)
         context.getAttribute("db-connection-pool");
      resource = pool.getResourceSession(sessionID);
      if (resource != null) {
         pool.closeResource(resource);
      }
   }

   public static java.sql.Connection getConnection(
         final ServletContext context, final String sessionID,
         final String useridcon, final String passwordcon) throws SQLException {
      java.sql.Connection conn = null;
      ResourceKey resource = null;
      pool = (DbConnectionPool) context.getAttribute("db-connection-pool");
      resource = pool.getResourceSession(sessionID);
      if (resource == null) {
         try {
            if (useridcon != null && !useridcon.equals("")
                  && passwordcon != null && !passwordcon.equals("")) {
               pool.setUser(useridcon);
               pool.setPassword(passwordcon);
               pool.setSessionID(sessionID);
            }
            if (resource == null || !resource.getPassword().equals(passwordcon)) {
               recycleConnection(context, sessionID); // Recicla a
               // conexão e
               // retorna
               // erro
               throw new ResourceException("Senha inválida."); // Exceção
               // de senha
               // inválida
            }
            conn = (java.sql.Connection) resource.getResource();
         } catch (ResourceException e) {

         }
      }
      return conn;
   }

   public static void closeConnection(final java.sql.Connection conn,
         final PreparedStatement statement, final ResultSet resultset) {
      if (resultset != null) {
         try {
            resultset.close();
         } catch (SQLException e) {
         }
      }
      if (statement != null) {
         try {
            statement.close();
         } catch (SQLException e) {
         }
      }
      if (conn != null) {
         try {
            conn.close();
         } catch (SQLException e) {
         }
      }
   }

}
