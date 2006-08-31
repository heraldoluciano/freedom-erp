package org.freedom.plugin;

import java.util.Map;


public abstract class AbstractControleVendaPDV extends AbstractControle {

	public AbstractControleVendaPDV() {

		super();
	}
	
	public abstract Map beforeAbreVenda();
    
    public abstract Map afterAbreVenda();
	
    public abstract Map beforeVendaItem();
    
    public abstract Map afterVendaItem();
    
    public abstract Map beforeCancelaItem();
    
    public abstract Map afterCancelaItem();
    
    public abstract Map beforeCancelaVenda();
    
    public abstract Map afterCancelaVenda();
    
    public abstract Map beforeFechaVenda();
    
    public abstract Map afterFechaVenda();
    
}
