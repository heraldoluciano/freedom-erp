package org.freedom.modulos.std.orcamento.bean;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Cesta {
	private Integer codemp;
	private Integer codfilial;
	private Integer codcli;
	private String razcli;
	private Date datacesta;
	private Integer codplanopag;
	private String descplanopag;
	private Integer codvend;
	private String nomevend;
	private BigDecimal qtdcesta;
	private BigDecimal vlrdesccesta;
	private BigDecimal vlrliqcesta;
	private Integer codorc = new Integer(0);
	public Boolean sel = new Boolean(false);
	private List<Item> itens;
	public Cesta() {
		setItens(new LinkedList<Item>());
	}
	public Cesta(Integer codemp, Integer codfilial, Integer codcli, String razcli, Date datacesta
			, Integer codplanopag, String descplanopag, Integer codvend, String nomevend) {
		this();
		setCodemp(codemp);
		setCodfilial(codfilial);
		setCodcli(codcli);
		setRazcli(razcli);
		setDatacesta(datacesta);
		setCodplanopag(codplanopag);
		setDescplanopag(descplanopag);
		setCodvend(codvend);
		setNomevend(nomevend);
	
	}
	public Integer getCodemp() {
		return codemp;
	}
	public void setCodemp(Integer codemp) {
		this.codemp = codemp;
	}
	public Integer getCodfilial() {
		return codfilial;
	}
	public void setCodfilial(Integer codfilial) {
		this.codfilial = codfilial;
	}
	public Integer getCodcli() {
		return codcli;
	}
	public void setCodcli(Integer codcli) {
		this.codcli = codcli;
	}
	public String getRazcli() {
		return razcli;
	}
	public void setRazcli(String razcli) {
		this.razcli = razcli;
	}
	public Integer getCodplanopag() {
		return codplanopag;
	}
	public void setCodplanopag(Integer codplanopag) {
		this.codplanopag = codplanopag;
	}
	public String getDescplanopag() {
		return descplanopag;
	}
	public void setDescplanopag(String descplanopag) {
		this.descplanopag = descplanopag;
	}
	public Integer getCodvend() {
		return codvend;
	}
	public void setCodvend(Integer codvend) {
		this.codvend = codvend;
	}
	public String getNomevend() {
		return nomevend;
	}
	public void setNomevend(String nomevend) {
		this.nomevend = nomevend;
	}
	public Date getDatacesta() {
		return datacesta;
	}
	public void setDatacesta(Date datacesta) {
		this.datacesta = datacesta;
	}
	public BigDecimal getQtdcesta() {
		if (qtdcesta==null) {
			qtdcesta = new BigDecimal(0);
		}
		return qtdcesta;
	}
	public void setQtdcesta(BigDecimal qtdcesta) {
		this.qtdcesta = qtdcesta;
	}
	public BigDecimal getVlrdesccesta() {
		if (vlrdesccesta==null) {
			vlrdesccesta = new BigDecimal(0);
		}
		return vlrdesccesta;
	}
	public void setVlrdesccesta(BigDecimal vlrdesccesta) {
		this.vlrdesccesta = vlrdesccesta;
	}
	public BigDecimal getVlrliqcesta() {
		if (vlrliqcesta == null) {
			vlrliqcesta = new BigDecimal(0);
		}
		return vlrliqcesta;
	}
	public void setVlrliqcesta(BigDecimal vlrliqcesta) {
		this.vlrliqcesta = vlrliqcesta;
	}
	public Integer getCodorc() {
		return codorc;
	}
	public void setCodorc(Integer codorc) {
		this.codorc = codorc;
	}
	public List<Item> getItens() {
		if (itens==null) {
			itens = new LinkedList<Item>();
		}
		return itens;
	}
	public Boolean getSel() {
		return sel;
	}
	public void setSel(Boolean sel) {
		this.sel = sel;
	}
	public void setItens(List<Item> itens) {
		this.itens = itens;
	}
	public Item getItem(Integer codemp, Integer codfilial, Integer codprod) {
		Item result = null;
		for (Item item:getItens()) {
			if (item.getCodemp().equals(codemp) 
					&& item.getCodfilial().equals(codfilial) 
					&& item.getCodprod().equals(codprod)) {
				result = item;
				break;
			}
		}
		return result;
	}
	public void addItem(Item item) {
		itens.add(item);
		sumarize();
	}
	public void sumarize() {
		for (Item item: getItens()) {
			setQtdcesta(getQtdcesta().add(item.getQtd()));
			setVlrdesccesta(getVlrdesccesta().add(item.getVlrdesc()));
			setVlrliqcesta(getVlrliqcesta().add(item.getVlrliq()));
		}
	}
	public Item createNewItem(Integer codemp, Integer codfilial, Integer codprod, String descprod, Integer codalmox, String descalmox) {
		Item result = getItem(codemp, codfilial, codprod);
		if (result==null) {
			result = new Item(codemp, codfilial, codprod, descprod, codalmox, descalmox);
			itens.add(result);
		}
		return result;
	}
	public Item addItem(Integer codemp, Integer codfilial, Integer codprod, String descprod, Integer codalmox, String descalmox
			, BigDecimal qtd, BigDecimal preco, BigDecimal percdesc, BigDecimal vlrdesc, BigDecimal vlrliq) {
		Item result = getItem(codprod, codfilial, codprod);
		if (result == null || result.getPreco()==null || result.getPreco().equals(BigDecimal.ZERO)) {
			result = createNewItem(codemp, codfilial, codprod, descprod, codalmox, descalmox);
			result.setQtd(qtd);
			result.setPreco(preco);
			result.setPercdesc(percdesc);
			result.setVlrdesc(vlrdesc);
			result.setVlrliq(vlrliq);
			//addItem(result);
		} else {
			/*BigDecimal qtdold = result.getQtd();
			BigDecimal precoold = result.getPreco();
			BigDecimal percdescold = result.getPercdesc();
			BigDecimal vlrdescold = result.getVlrdesc();
			BigDecimal vlrliqold = result.getVlrliq();
			*/
			result.setQtd(result.getQtd().add(qtd));
			result.setVlrdesc(result.getVlrdesc().add(vlrdesc));
			result.setVlrliq(result.getPreco().multiply(result.getQtd()).subtract(result.getVlrdesc()));
		}
		sumarize();
		return result;
	}
}
