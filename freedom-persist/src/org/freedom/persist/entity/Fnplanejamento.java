package org.freedom.persist.entity;

// Generated 12/05/2014 09:11:34 by Hibernate Tools 4.0.0

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Fnplanejamento generated by hbm2java
 */
@Entity
@Table(name = "FNPLANEJAMENTO")
public class Fnplanejamento implements java.io.Serializable {

	private FnplanejamentoId id;
	private Sgfilial sgfilial;
	private Fnhistpad fnhistpad;
	private Fnplanejamento fnplanejamento;
	private String descplan;
	private int nivelplan;
	private Integer codredplan;
	private Integer codempsp;
	private Short codfilialsp;
	private char tipoplan;
	private String finplan;
	private char compsldcxplan;
	private String codcontcred;
	private String codcontdeb;
	private char esfinplan;
	private char clasfinplan;
	private char lanctoxplan;
	private Date dtins;
	private Date hins;
	private String idusuins;
	private Date dtalt;
	private Date halt;
	private String idusualt;
	private Set fnsublancas = new HashSet(0);
	private Set cprateios = new HashSet(0);
	private Set fnplanejamentos = new HashSet(0);
	private Set fnitrecebers = new HashSet(0);
	private Set sgprefere1sForSgprefere1fkplancheque = new HashSet(0);
	private Set fncontas = new HashSet(0);
	private Set fnrecebers = new HashSet(0);
	private Set sgprefere1sForSgprefere1fkplanjr = new HashSet(0);
	private Set sgprefere1sForSgprefere1fkplandr = new HashSet(0);
	private Set sgprefere7sForSgpref7fnplanvdconsig = new HashSet(0);
	private Set sgprefere1sForSgprefere1fkplanjp = new HashSet(0);
	private Set fnlancas = new HashSet(0);
	private Set vdvendedors = new HashSet(0);
	private Set fnsaldolancas = new HashSet(0);
	private Set fnitpagars = new HashSet(0);
	private Set fnpagars = new HashSet(0);
	private Set fnfbnclis = new HashSet(0);
	private Set sgprefere7sForSgpref7fkfnplanconsig = new HashSet(0);
	private Set eqprodplans = new HashSet(0);
	private Set sgprefere1sForSgprefere1fkplandc = new HashSet(0);
	private Set cpcompras = new HashSet(0);
	private Set fnpagtocomis = new HashSet(0);
	private Set fnplanopags = new HashSet(0);

	public Fnplanejamento() {
	}

	public Fnplanejamento(FnplanejamentoId id, Sgfilial sgfilial,
			Fnplanejamento fnplanejamento, String descplan, int nivelplan,
			char tipoplan, char compsldcxplan, char esfinplan,
			char clasfinplan, char lanctoxplan, Date dtins, Date hins,
			String idusuins) {
		this.id = id;
		this.sgfilial = sgfilial;
		this.fnplanejamento = fnplanejamento;
		this.descplan = descplan;
		this.nivelplan = nivelplan;
		this.tipoplan = tipoplan;
		this.compsldcxplan = compsldcxplan;
		this.esfinplan = esfinplan;
		this.clasfinplan = clasfinplan;
		this.lanctoxplan = lanctoxplan;
		this.dtins = dtins;
		this.hins = hins;
		this.idusuins = idusuins;
	}

	public Fnplanejamento(FnplanejamentoId id, Sgfilial sgfilial,
			Fnhistpad fnhistpad, Fnplanejamento fnplanejamento,
			String descplan, int nivelplan, Integer codredplan,
			Integer codempsp, Short codfilialsp, char tipoplan, String finplan,
			char compsldcxplan, String codcontcred, String codcontdeb,
			char esfinplan, char clasfinplan, char lanctoxplan, Date dtins,
			Date hins, String idusuins, Date dtalt, Date halt, String idusualt,
			Set fnsublancas, Set cprateios, Set fnplanejamentos,
			Set fnitrecebers, Set sgprefere1sForSgprefere1fkplancheque,
			Set fncontas, Set fnrecebers, Set sgprefere1sForSgprefere1fkplanjr,
			Set sgprefere1sForSgprefere1fkplandr,
			Set sgprefere7sForSgpref7fnplanvdconsig,
			Set sgprefere1sForSgprefere1fkplanjp, Set fnlancas,
			Set vdvendedors, Set fnsaldolancas, Set fnitpagars, Set fnpagars,
			Set fnfbnclis, Set sgprefere7sForSgpref7fkfnplanconsig,
			Set eqprodplans, Set sgprefere1sForSgprefere1fkplandc,
			Set cpcompras, Set fnpagtocomis, Set fnplanopags) {
		this.id = id;
		this.sgfilial = sgfilial;
		this.fnhistpad = fnhistpad;
		this.fnplanejamento = fnplanejamento;
		this.descplan = descplan;
		this.nivelplan = nivelplan;
		this.codredplan = codredplan;
		this.codempsp = codempsp;
		this.codfilialsp = codfilialsp;
		this.tipoplan = tipoplan;
		this.finplan = finplan;
		this.compsldcxplan = compsldcxplan;
		this.codcontcred = codcontcred;
		this.codcontdeb = codcontdeb;
		this.esfinplan = esfinplan;
		this.clasfinplan = clasfinplan;
		this.lanctoxplan = lanctoxplan;
		this.dtins = dtins;
		this.hins = hins;
		this.idusuins = idusuins;
		this.dtalt = dtalt;
		this.halt = halt;
		this.idusualt = idusualt;
		this.fnsublancas = fnsublancas;
		this.cprateios = cprateios;
		this.fnplanejamentos = fnplanejamentos;
		this.fnitrecebers = fnitrecebers;
		this.sgprefere1sForSgprefere1fkplancheque = sgprefere1sForSgprefere1fkplancheque;
		this.fncontas = fncontas;
		this.fnrecebers = fnrecebers;
		this.sgprefere1sForSgprefere1fkplanjr = sgprefere1sForSgprefere1fkplanjr;
		this.sgprefere1sForSgprefere1fkplandr = sgprefere1sForSgprefere1fkplandr;
		this.sgprefere7sForSgpref7fnplanvdconsig = sgprefere7sForSgpref7fnplanvdconsig;
		this.sgprefere1sForSgprefere1fkplanjp = sgprefere1sForSgprefere1fkplanjp;
		this.fnlancas = fnlancas;
		this.vdvendedors = vdvendedors;
		this.fnsaldolancas = fnsaldolancas;
		this.fnitpagars = fnitpagars;
		this.fnpagars = fnpagars;
		this.fnfbnclis = fnfbnclis;
		this.sgprefere7sForSgpref7fkfnplanconsig = sgprefere7sForSgpref7fkfnplanconsig;
		this.eqprodplans = eqprodplans;
		this.sgprefere1sForSgprefere1fkplandc = sgprefere1sForSgprefere1fkplandc;
		this.cpcompras = cpcompras;
		this.fnpagtocomis = fnpagtocomis;
		this.fnplanopags = fnplanopags;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "codplan", column = @Column(name = "CODPLAN", nullable = false, length = 13)),
			@AttributeOverride(name = "codfilial", column = @Column(name = "CODFILIAL", nullable = false)),
			@AttributeOverride(name = "codemp", column = @Column(name = "CODEMP", nullable = false)) })
	public FnplanejamentoId getId() {
		return this.id;
	}

	public void setId(FnplanejamentoId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
			@JoinColumn(name = "CODFILIAL", referencedColumnName = "CODFILIAL", nullable = false, insertable = false, updatable = false),
			@JoinColumn(name = "CODEMP", referencedColumnName = "CODEMP", nullable = false, insertable = false, updatable = false) })
	public Sgfilial getSgfilial() {
		return this.sgfilial;
	}

	public void setSgfilial(Sgfilial sgfilial) {
		this.sgfilial = sgfilial;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
			@JoinColumn(name = "CODHIST", referencedColumnName = "CODHIST"),
			@JoinColumn(name = "CODFILIALHP", referencedColumnName = "CODFILIAL"),
			@JoinColumn(name = "CODEMPHP", referencedColumnName = "CODEMP") })
	public Fnhistpad getFnhistpad() {
		return this.fnhistpad;
	}

	public void setFnhistpad(Fnhistpad fnhistpad) {
		this.fnhistpad = fnhistpad;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
			@JoinColumn(name = "CODSUBPLAN", referencedColumnName = "CODPLAN", insertable = false, updatable = false),
			@JoinColumn(name = "CODFILIAL", referencedColumnName = "CODFILIAL", insertable = false, updatable = false),
			@JoinColumn(name = "CODEMP", referencedColumnName = "CODEMP", insertable = false, updatable = false) })
	public Fnplanejamento getFnplanejamento() {
		return this.fnplanejamento;
	}

	public void setFnplanejamento(Fnplanejamento fnplanejamento) {
		this.fnplanejamento = fnplanejamento;
	}

	@Column(name = "DESCPLAN", nullable = false, length = 50)
	public String getDescplan() {
		return this.descplan;
	}

	public void setDescplan(String descplan) {
		this.descplan = descplan;
	}

	@Column(name = "NIVELPLAN", nullable = false)
	public int getNivelplan() {
		return this.nivelplan;
	}

	public void setNivelplan(int nivelplan) {
		this.nivelplan = nivelplan;
	}

	@Column(name = "CODREDPLAN")
	public Integer getCodredplan() {
		return this.codredplan;
	}

	public void setCodredplan(Integer codredplan) {
		this.codredplan = codredplan;
	}

	@Column(name = "CODEMPSP")
	public Integer getCodempsp() {
		return this.codempsp;
	}

	public void setCodempsp(Integer codempsp) {
		this.codempsp = codempsp;
	}

	@Column(name = "CODFILIALSP")
	public Short getCodfilialsp() {
		return this.codfilialsp;
	}

	public void setCodfilialsp(Short codfilialsp) {
		this.codfilialsp = codfilialsp;
	}

	@Column(name = "TIPOPLAN", nullable = false, length = 1)
	public char getTipoplan() {
		return this.tipoplan;
	}

	public void setTipoplan(char tipoplan) {
		this.tipoplan = tipoplan;
	}

	@Column(name = "FINPLAN", length = 3)
	public String getFinplan() {
		return this.finplan;
	}

	public void setFinplan(String finplan) {
		this.finplan = finplan;
	}

	@Column(name = "COMPSLDCXPLAN", nullable = false, length = 1)
	public char getCompsldcxplan() {
		return this.compsldcxplan;
	}

	public void setCompsldcxplan(char compsldcxplan) {
		this.compsldcxplan = compsldcxplan;
	}

	@Column(name = "CODCONTCRED", length = 20)
	public String getCodcontcred() {
		return this.codcontcred;
	}

	public void setCodcontcred(String codcontcred) {
		this.codcontcred = codcontcred;
	}

	@Column(name = "CODCONTDEB", length = 20)
	public String getCodcontdeb() {
		return this.codcontdeb;
	}

	public void setCodcontdeb(String codcontdeb) {
		this.codcontdeb = codcontdeb;
	}

	@Column(name = "ESFINPLAN", nullable = false, length = 1)
	public char getEsfinplan() {
		return this.esfinplan;
	}

	public void setEsfinplan(char esfinplan) {
		this.esfinplan = esfinplan;
	}

	@Column(name = "CLASFINPLAN", nullable = false, length = 1)
	public char getClasfinplan() {
		return this.clasfinplan;
	}

	public void setClasfinplan(char clasfinplan) {
		this.clasfinplan = clasfinplan;
	}

	@Column(name = "LANCTOXPLAN", nullable = false, length = 1)
	public char getLanctoxplan() {
		return this.lanctoxplan;
	}

	public void setLanctoxplan(char lanctoxplan) {
		this.lanctoxplan = lanctoxplan;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DTINS", nullable = false, length = 10)
	public Date getDtins() {
		return this.dtins;
	}

	public void setDtins(Date dtins) {
		this.dtins = dtins;
	}

	@Temporal(TemporalType.TIME)
	@Column(name = "HINS", nullable = false, length = 8)
	public Date getHins() {
		return this.hins;
	}

	public void setHins(Date hins) {
		this.hins = hins;
	}

	@Column(name = "IDUSUINS", nullable = false, length = 8)
	public String getIdusuins() {
		return this.idusuins;
	}

	public void setIdusuins(String idusuins) {
		this.idusuins = idusuins;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DTALT", length = 10)
	public Date getDtalt() {
		return this.dtalt;
	}

	public void setDtalt(Date dtalt) {
		this.dtalt = dtalt;
	}

	@Temporal(TemporalType.TIME)
	@Column(name = "HALT", length = 8)
	public Date getHalt() {
		return this.halt;
	}

	public void setHalt(Date halt) {
		this.halt = halt;
	}

	@Column(name = "IDUSUALT", length = 8)
	public String getIdusualt() {
		return this.idusualt;
	}

	public void setIdusualt(String idusualt) {
		this.idusualt = idusualt;
	}

	/*@OneToMany(fetch = FetchType.LAZY, mappedBy = "fnplanejamento")
	public Set getFnsublancas() {
		return this.fnsublancas;
	}

	public void setFnsublancas(Set fnsublancas) {
		this.fnsublancas = fnsublancas;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fnplanejamento")
	public Set getCprateios() {
		return this.cprateios;
	}

	public void setCprateios(Set cprateios) {
		this.cprateios = cprateios;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fnplanejamento")
	public Set getFnplanejamentos() {
		return this.fnplanejamentos;
	}

	public void setFnplanejamentos(Set fnplanejamentos) {
		this.fnplanejamentos = fnplanejamentos;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fnplanejamento")
	public Set getFnitrecebers() {
		return this.fnitrecebers;
	}

	public void setFnitrecebers(Set fnitrecebers) {
		this.fnitrecebers = fnitrecebers;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fnplanejamentoBySgprefere1fkplancheque")
	public Set getSgprefere1sForSgprefere1fkplancheque() {
		return this.sgprefere1sForSgprefere1fkplancheque;
	}

	public void setSgprefere1sForSgprefere1fkplancheque(
			Set sgprefere1sForSgprefere1fkplancheque) {
		this.sgprefere1sForSgprefere1fkplancheque = sgprefere1sForSgprefere1fkplancheque;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fnplanejamento")
	public Set getFncontas() {
		return this.fncontas;
	}

	public void setFncontas(Set fncontas) {
		this.fncontas = fncontas;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fnplanejamento")
	public Set getFnrecebers() {
		return this.fnrecebers;
	}

	public void setFnrecebers(Set fnrecebers) {
		this.fnrecebers = fnrecebers;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fnplanejamentoBySgprefere1fkplanjr")
	public Set getSgprefere1sForSgprefere1fkplanjr() {
		return this.sgprefere1sForSgprefere1fkplanjr;
	}

	public void setSgprefere1sForSgprefere1fkplanjr(
			Set sgprefere1sForSgprefere1fkplanjr) {
		this.sgprefere1sForSgprefere1fkplanjr = sgprefere1sForSgprefere1fkplanjr;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fnplanejamentoBySgprefere1fkplandr")
	public Set getSgprefere1sForSgprefere1fkplandr() {
		return this.sgprefere1sForSgprefere1fkplandr;
	}

	public void setSgprefere1sForSgprefere1fkplandr(
			Set sgprefere1sForSgprefere1fkplandr) {
		this.sgprefere1sForSgprefere1fkplandr = sgprefere1sForSgprefere1fkplandr;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fnplanejamentoBySgpref7fnplanvdconsig")
	public Set getSgprefere7sForSgpref7fnplanvdconsig() {
		return this.sgprefere7sForSgpref7fnplanvdconsig;
	}

	public void setSgprefere7sForSgpref7fnplanvdconsig(
			Set sgprefere7sForSgpref7fnplanvdconsig) {
		this.sgprefere7sForSgpref7fnplanvdconsig = sgprefere7sForSgpref7fnplanvdconsig;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fnplanejamentoBySgprefere1fkplanjp")
	public Set getSgprefere1sForSgprefere1fkplanjp() {
		return this.sgprefere1sForSgprefere1fkplanjp;
	}

	public void setSgprefere1sForSgprefere1fkplanjp(
			Set sgprefere1sForSgprefere1fkplanjp) {
		this.sgprefere1sForSgprefere1fkplanjp = sgprefere1sForSgprefere1fkplanjp;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fnplanejamento")
	public Set getFnlancas() {
		return this.fnlancas;
	}

	public void setFnlancas(Set fnlancas) {
		this.fnlancas = fnlancas;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fnplanejamento")
	public Set getVdvendedors() {
		return this.vdvendedors;
	}

	public void setVdvendedors(Set vdvendedors) {
		this.vdvendedors = vdvendedors;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fnplanejamento")
	public Set getFnsaldolancas() {
		return this.fnsaldolancas;
	}

	public void setFnsaldolancas(Set fnsaldolancas) {
		this.fnsaldolancas = fnsaldolancas;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fnplanejamento")
	public Set getFnitpagars() {
		return this.fnitpagars;
	}

	public void setFnitpagars(Set fnitpagars) {
		this.fnitpagars = fnitpagars;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fnplanejamento")
	public Set getFnpagars() {
		return this.fnpagars;
	}

	public void setFnpagars(Set fnpagars) {
		this.fnpagars = fnpagars;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fnplanejamento")
	public Set getFnfbnclis() {
		return this.fnfbnclis;
	}

	public void setFnfbnclis(Set fnfbnclis) {
		this.fnfbnclis = fnfbnclis;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fnplanejamentoBySgpref7fkfnplanconsig")
	public Set getSgprefere7sForSgpref7fkfnplanconsig() {
		return this.sgprefere7sForSgpref7fkfnplanconsig;
	}

	public void setSgprefere7sForSgpref7fkfnplanconsig(
			Set sgprefere7sForSgpref7fkfnplanconsig) {
		this.sgprefere7sForSgpref7fkfnplanconsig = sgprefere7sForSgpref7fkfnplanconsig;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fnplanejamento")
	public Set getEqprodplans() {
		return this.eqprodplans;
	}

	public void setEqprodplans(Set eqprodplans) {
		this.eqprodplans = eqprodplans;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fnplanejamentoBySgprefere1fkplandc")
	public Set getSgprefere1sForSgprefere1fkplandc() {
		return this.sgprefere1sForSgprefere1fkplandc;
	}

	public void setSgprefere1sForSgprefere1fkplandc(
			Set sgprefere1sForSgprefere1fkplandc) {
		this.sgprefere1sForSgprefere1fkplandc = sgprefere1sForSgprefere1fkplandc;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fnplanejamento")
	public Set getCpcompras() {
		return this.cpcompras;
	}

	public void setCpcompras(Set cpcompras) {
		this.cpcompras = cpcompras;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fnplanejamento")
	public Set getFnpagtocomis() {
		return this.fnpagtocomis;
	}

	public void setFnpagtocomis(Set fnpagtocomis) {
		this.fnpagtocomis = fnpagtocomis;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fnplanejamento")
	public Set getFnplanopags() {
		return this.fnplanopags;
	}

	public void setFnplanopags(Set fnplanopags) {
		this.fnplanopags = fnplanopags;
	}
*/
}
