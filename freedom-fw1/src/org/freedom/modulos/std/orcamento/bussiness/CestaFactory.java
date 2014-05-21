package org.freedom.modulos.std.orcamento.bussiness;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.freedom.modulos.std.orcamento.bean.Cesta;

public class CestaFactory {
	private List<Cesta> cestas = null; 
	public CestaFactory() {
		setCestas(new LinkedList<Cesta>());
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
	
	public Cesta getCesta(Integer codemp, Integer codfilial, Integer codcli) {
		Cesta result = null;
		for (Cesta cesta:getCestas()) {
			if (cesta.getCodemp().equals(codemp) 
					&& cesta.getCodfilial().equals(codfilial) 
					&& cesta.getCodcli().equals(codcli)) {
				result = cesta;
				break;
			}
		}
		return result;
	}
	
	public Cesta createNewCesta(Integer codemp, Integer codfilial, Integer codcli, String razcli
			, Integer codplanopag, String descplanopag, Integer codvend, String nomevend) {
		Cesta result = getCesta(codemp, codfilial, codcli);
		if (result==null) {
			result = new Cesta(codemp, codfilial, codcli, razcli, new Date(), codplanopag, descplanopag, codvend, nomevend);
			cestas.add(result);
		}
		return result;
	}
	
	public void resetCestas() {
		setCestas(null);
	}
}
