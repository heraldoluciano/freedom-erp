/*
 * Created on 05/10/2004
 * Autor: robson 
 * Descrição: Classe para encapsulamento de recursos
 */
package org.freedom.util.resource;

/**
 * @author robson
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ResourceKey {
   private Object resource = null;

   private String key = null;

   private String sessionID = null;

   private String password = null;

   public ResourceKey(String sessionID, String key, String password,
         Object resource) {
      this.resource = resource;
      this.key = key;
      this.sessionID = sessionID;
      this.password = password;
   }

   public Object getResource() {
      return resource;
   }

   public void setResource(Object resource) {
      this.resource = resource;
   }

   public boolean isResource(String sessionID, String key) {
      boolean isresource = false;
      try {
         if (sessionID == null)
            isresource = isResource(key);
         else
            isresource = ((this.key == key) && (this.sessionID == sessionID));
      } catch (Exception e) {

      }
      return isresource;
   }

   public boolean isResource(String key) {
      boolean isresource = false;
      try {
         isresource = (this.key == key);
      } catch (Exception e) {

      }
      return isresource;
   }

   public String getHashKey() {
      return sessionID + key;
   }

   public String getKey() {
      return this.key;
   }

   public String getSessionID() {
      return this.sessionID;
   }

   public void setSessionID(String sessionID) {
      this.sessionID = sessionID;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public String getPassword() {
      return this.password;
   }
}