package org.freedom.util.resource;

import org.apache.log4j.Logger;



/**
 * Classe utilizada para encapsular objetos que serão utilizados no pool. <BR>
 * Projeto: freedom-pool <BR>
 * Pacote: org.freedom.util.resource <BR>
 * Classe: @(#)ResourceKey.java <BR>
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
public class ResourceKey {
   /** Recurso. */
   private Object resource = null;

   /** Chave do recurso. */
   private String key = null;

   /** ID da sessão chave. */
   private String sessionID = null;

   /** Senha do recurso. */
   private String password = null;

   /** Log4j da classe. */
   private static final Logger LOGGER = createLogger();

   /**
    * Construtor do Recurso.
    * @param sessID ID da Sessão chave.
    * @param keyp Chave secundário.
    * @param pass Senha para validação.
    * @param res Objeto que será armazenado.
    */
   public ResourceKey(final String sessID, final String keyp,
         final String pass, final Object res) {
      setResource(res);
      setKey(keyp);
      setSessionID(sessID);
      setPassword(pass);
   }

   /**
    * Cria a instância do Log4j para a classe.
    * @return Retorna a instância do Log.
    */
   private static Logger createLogger() {
     return Logger.getLogger("org.freedom.util.resource.ResourceKey");
   }

   /**
    * Retorna o recurso encapsulado.
    * @return Retorna a instância do recurso encapsulado.
    */
   public final Object getResource() {
      return resource;
   }

   /**
    * Encapsula o objeto principal.
    * @param res Instância para encapsulamento.
    */
   public final void setResource(final Object res) {
      this.resource = res;
   }

   /**
    * Verifica se é um recurso válido.
    * @param sessID ID da sessão chave.
    * @param keyp Chave secundária.
    * @return Retorna um flag indicando se o recurso é valido.
    */
   public final boolean isResource(final String sessID, final String keyp) {
      boolean isresource = false;
      try {
         if (sessionID == null) {
            isresource = isResource(key);
         } else {
            isresource = ((this.key == key) && (this.sessionID == sessionID));
         }
      } catch (Exception e) {
         LOGGER.error(e);
      }
      return isresource;
   }


   /**
    * Verifica se é um recurso válido.
    * @param keyp Chave secundária.
    * @return Retorna um flag indicando se o recurso é valido.
    */
   public final boolean isResource(final String keyp) {
      boolean isresource = false;
      try {
         isresource = (this.key == keyp);
      } catch (Exception e) {
         LOGGER.error(e);
      }
      return isresource;
   }

   /**
    * Chave para a lista de recursos.
    * @return Retorna a chave.
    */
   public final String getHashKey() {
      return sessionID + key;
   }

   /**
    * Retorna a chave encapsulada.
    * @return Retorna a chave.
    */
   public final String getKey() {
      return this.key;
   }

   /**
    * Encapsula a chave do recurso.
    * @param keyp Recebe a chave para encapsulamento.
    */
   public final void setKey(final String keyp) {
      this.key = keyp;
   }

   /**
    * Retorna o ID da sessão encapsulada.
    * @return Retorna o ID da sessão.
    */
   public final String getSessionID() {
      return this.sessionID;
   }

   /**
    * Encapsula o ID da sessão.
    * @param sessID Recebe o ID da sessão para encapsulamento.
    */
   public final void setSessionID(final String sessID) {
      this.sessionID = sessID;
   }

   /**
    * Encapsula a senha.
    * @param pass Recebe a senha para encapsulamento.
    */
   public final void setPassword(final String pass) {
      this.password = pass;
   }

   /**
    * Retorna a senha encapsulada.
    * @return Retorna a senha.
    */
   public final String getPassword() {
      return this.password;
   }
}
