/*
 * Created on 05/10/2004
 * Autor: robson 
 * Descrição: Classe de pool de recursos
 */
package org.freedom.util.resource;

import java.util.Enumeration;
import java.util.Hashtable;

/**
 * @author robson
 * 
 * Pool de recursos baseado no livro JavaServer Pages avançado
 */
public abstract class ResourcePool implements Runnable {
	protected final Hashtable availableResources = new Hashtable(),
			inUseResources = new Hashtable();

	private final int maxResources;

	private final boolean waitIfMaxedOut;

	private ResourceException error = null; // Definido por run()

	// Extensões têm que implementar estes três métodos
	public abstract ResourceKey createResource() throws ResourceException;

	public abstract boolean isResourceValid(ResourceKey resource);

	public abstract void closeResource(ResourceKey resource);

	public ResourcePool() {
		this(10, // por padrão, um máximo de 10 recursos no pool
				false); // não espera pelo recurso se maximizado
	}

	public ResourcePool(int max, boolean waitIfMaxedOut) {
		this.maxResources = max;
		this.waitIfMaxedOut = waitIfMaxedOut;
	}

	public ResourceKey getResource(String sessionID, String key, String password)
			throws ResourceException {
		return getResource(sessionID, key, password, 0);
	}

	public synchronized ResourceKey getResource(String sessionID, String key,
			String password, long timeout) throws ResourceException {
		ResourceKey resource = getFirstAvailableResource(sessionID + key);

		if (resource == null) { // Sem recursos disponíveis
			if (countResources() < maxResources) {
				waitForAvailableResource();
				return getFirstResource(sessionID, key, password);
			}
			// limite máximo de recursos atingido
			if (waitIfMaxedOut) {
				try {
					wait(timeout);
				} catch (InterruptedException ex) {
				}
				return getFirstResource(sessionID, key, password);
			}
			throw new ResourceException(
					"Número máximo de recursos atingidos. Tente mais tarde.");

		}
		resource.setSessionID(sessionID);
		inUseResources.put(resource.getHashKey(), resource);
		return resource;
	}

	public synchronized void recycleResource(ResourceKey resource) {
		inUseResources.remove(resource.getHashKey());
		availableResources.put(resource.getHashKey(), resource);
		notifyAll(); // notifica threads em espera de con disponíveis
	}

	public void shutdown() {
		closeResources(availableResources);
		closeResources(inUseResources);
		availableResources.clear();
		inUseResources.clear();
	}

	public synchronized void run() {
		ResourceKey resource;
		error = null;
		try {
			resource = createResource(); // criação de subclasses
		} catch (ResourceException ex) {
			error = ex; // armazena a exceção
			notifyAll(); // thread em espera irá emitir uma exceção
			return;
		}
		if (resource != null)
			availableResources.put(resource.getHashKey(), resource);
		notifyAll(); // notifica threas em espera
	}

	private ResourceKey getFirstAvailableResource(String key) {
		ResourceKey resource = null;

		if (availableResources.size() > 0)
			resource = getFirstResource(key);
		if ((resource != null) && (!isResourceValid(resource)))
			resource = getFirstAvailableResource(key); // tenta novamente
		return resource;
	}

	private void waitForAvailableResource() throws ResourceException {
		Thread thread = new Thread(this);
		thread.start(); // thread cria um recurso: veja run()

		try {
			wait(); // espera que un novo recurso seja criado
			// ou que um recurso seja reciclado
		} catch (InterruptedException ex) {
		}

		if (error != null) // exceção pega em run()
			throw error; // reemite exceção pega em run()
	}

	private void closeResources(Hashtable resources) {
		Enumeration enumer = resources.elements();
		while (enumer.hasMoreElements())
			closeResource((ResourceKey) enumer.nextElement());
	}

	private int countResources() {
		return availableResources.size() + inUseResources.size();
	}

	private ResourceKey getFirstResource(String key) {
		ResourceKey resource = null;
		if (key == null)
			key = "";
		resource = (ResourceKey) availableResources.get(key);
		if (resource != null)
			availableResources.remove(key);
		return resource;
	}

	private ResourceKey getFirstResource(String sessionID, String key,
			String password) {
		ResourceKey resource = null;
		if (key == null)
			key = "";
		resource = (ResourceKey) availableResources.get(sessionID + key);
		if (resource != null) {
			if (resource.getPassword().equals(password)) {
				availableResources.remove(resource.getHashKey());
			}
		}
		return resource;
	}

	public ResourceKey getResourceSession(String sessionID) {
		ResourceKey resource = null;
		Enumeration enumer = inUseResources.elements();
		while (enumer.hasMoreElements()) {
			resource = (ResourceKey) enumer.nextElement();
			if (resource.getSessionID().equals(sessionID)) {
				break;
			}
		}
		return resource;
	}

}