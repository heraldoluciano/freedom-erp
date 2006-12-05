package org.freedom.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.freedom.jdbc.DbConnectionPool;

/**
 * Classe de inicialização do pool de conexões <BR>
 * Projeto: freedom-pool <BR>
 * Pacote: org.freedom.servlet <BR>
 * Classe: @(#)SetupServlet.java <BR>
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
 * @see javax.servlet.http#HttpServlet
 */
public class SetupServlet extends HttpServlet {
   /**
    * Pool de conexões.
    */
   private DbConnectionPool pool;

   /**
    * @version Versão para serialização.
    */
   private static final long serialVersionUID = 1L;

   /**
    * Inicilização do servlet.
    * @param config Instância do web.xml .
    * @throws ServletException Exceção de servlets.
    * @see org.freedom.servlet.SetupServlet#HttpServlet
    * @see SetupServlet#HttpServlet
    */
   public void init(final ServletConfig config) throws ServletException {
       super.init(config);
       //config.getServletContext().
       final ServletContext app = config.getServletContext();
       final String jdbcDriver = config.getInitParameter("jdbcDriver");
       final String jdbcURL = config.getInitParameter("jdbcURL");
       final String jdbcUser = config.getInitParameter("jdbcUser");
       final String jdbcPassword = config.getInitParameter("jdbcPassword");
       final String jdbcInitCons =
          config.getInitParameter("jdbcInitialConnections");
       final String jdbcMaxCons = config.getInitParameter("jdbcMaxConnections");
       int initialCons = 0;
       int maxCons = 0;
       boolean ispool;
       if ((jdbcUser == null) || ("".equals(jdbcUser))) {
          ispool = false;
       } else {
          ispool = true;
          if ((jdbcInitCons != null) && (!"".equals(jdbcInitCons))) {
             initialCons = Integer.parseInt(jdbcInitCons);
          }
          if ((jdbcMaxCons != null) && (!"".equals(jdbcMaxCons))) {
             maxCons = Integer.parseInt(jdbcMaxCons);
          }
       }
       setPool(new DbConnectionPool(jdbcDriver, jdbcURL, initialCons, maxCons,
          jdbcUser, jdbcPassword, ispool));
       app.setAttribute("db-connection-pool", pool);
   }

   /**
    * Acessor do Pool.
    * @return Retorna o pool de conexões.
    */
   public final DbConnectionPool getPool() {
      return this.pool;
   }

   /**
    * Seta o pool interno.
    * @param poolParam Recebe o Pool de conexões.
    */
   public final void setPool(final DbConnectionPool poolParam) {
      this.pool = poolParam;
   }

   /**
    * Finaliza o pool de conexões.
    */
   public final void destroy() {
       pool.shutdown();
       super.destroy();
   }

}
