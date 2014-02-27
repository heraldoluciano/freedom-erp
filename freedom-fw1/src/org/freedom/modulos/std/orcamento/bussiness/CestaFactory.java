package org.freedom.modulos.std.orcamento.bussiness;

import java.util.ArrayList;
import java.util.List;

import org.freedom.modulos.std.orcamento.bean.Cesta;

public class CestaFactory {
	private List<Cesta> cestas = null; 
	public CestaFactory() {
		setCestas(new ArrayList<Cesta>());
	}
	public List<Cesta> getCestas() {
		if (this.cestas==null) {
			this.cestas = new ArrayList<Cesta>();
		}
		return cestas;
	}
	public void setCestas(List<Cesta> cestas) {
		this.cestas = cestas;
	}
}
