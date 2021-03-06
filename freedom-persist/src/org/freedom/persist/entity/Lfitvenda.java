package org.freedom.persist.entity;

// Generated 12/05/2014 09:11:34 by Hibernate Tools 4.0.0

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Lfitvenda generated by hbm2java
 */
@Entity
@Table(name = "LFITVENDA")
public class Lfitvenda implements java.io.Serializable {

	private LfitvendaId id;
	private Lfsittrib lfsittribByLfitvendafklfsittribpis;
	private Lfsittrib lfsittribByLfitvendafklfsittribcof;
	private Vditvenda vditvenda;
	private Lfsittrib lfsittribByLfitvendafklfsittribipi;
	private BigDecimal aliqcofins;
	private BigDecimal vlripiunidtrib;
	private Short modbcicms;
	private Short modbcicmsst;
	private BigDecimal aliqredbcicms;
	private BigDecimal aliqredbcicmsst;
	private BigDecimal aliqicmsst;
	private BigDecimal aliqpis;
	private BigDecimal vlrpisunidtrib;
	private BigDecimal vlrbasepis;
	private BigDecimal vlrbasecofins;
	private BigDecimal vlrcofunidtrib;
	private BigDecimal vlrir;
	private BigDecimal vlrpis;
	private BigDecimal vlrcofins;
	private BigDecimal vlrcsocial;
	private BigDecimal vlrbaseicmsitvenda;
	private BigDecimal vlrbaseicmsfreteitvenda;
	private BigDecimal vlricmsfreteitvenda;
	private BigDecimal vlrbasencm;
	private BigDecimal aliqnacncm;
	private BigDecimal aliqimpncm;
	private BigDecimal vlrnacncm;
	private BigDecimal vlrimpncm;
	private char emmanut;
	private Date dtins;
	private Date hins;
	private String idusuins;
	private Date dtalt;
	private Date halt;
	private String idusualt;

	public Lfitvenda() {
	}

	public Lfitvenda(Vditvenda vditvenda, BigDecimal vlrbasencm,
			BigDecimal aliqnacncm, BigDecimal aliqimpncm, BigDecimal vlrnacncm,
			BigDecimal vlrimpncm, char emmanut, Date dtins, Date hins,
			String idusuins) {
		this.vditvenda = vditvenda;
		this.vlrbasencm = vlrbasencm;
		this.aliqnacncm = aliqnacncm;
		this.aliqimpncm = aliqimpncm;
		this.vlrnacncm = vlrnacncm;
		this.vlrimpncm = vlrimpncm;
		this.emmanut = emmanut;
		this.dtins = dtins;
		this.hins = hins;
		this.idusuins = idusuins;
	}

	public Lfitvenda(Lfsittrib lfsittribByLfitvendafklfsittribpis,
			Lfsittrib lfsittribByLfitvendafklfsittribcof, Vditvenda vditvenda,
			Lfsittrib lfsittribByLfitvendafklfsittribipi,
			BigDecimal aliqcofins, BigDecimal vlripiunidtrib, Short modbcicms,
			Short modbcicmsst, BigDecimal aliqredbcicms,
			BigDecimal aliqredbcicmsst, BigDecimal aliqicmsst,
			BigDecimal aliqpis, BigDecimal vlrpisunidtrib,
			BigDecimal vlrbasepis, BigDecimal vlrbasecofins,
			BigDecimal vlrcofunidtrib, BigDecimal vlrir, BigDecimal vlrpis,
			BigDecimal vlrcofins, BigDecimal vlrcsocial,
			BigDecimal vlrbaseicmsitvenda, BigDecimal vlrbaseicmsfreteitvenda,
			BigDecimal vlricmsfreteitvenda, BigDecimal vlrbasencm,
			BigDecimal aliqnacncm, BigDecimal aliqimpncm, BigDecimal vlrnacncm,
			BigDecimal vlrimpncm, char emmanut, Date dtins, Date hins,
			String idusuins, Date dtalt, Date halt, String idusualt) {
		this.lfsittribByLfitvendafklfsittribpis = lfsittribByLfitvendafklfsittribpis;
		this.lfsittribByLfitvendafklfsittribcof = lfsittribByLfitvendafklfsittribcof;
		this.vditvenda = vditvenda;
		this.lfsittribByLfitvendafklfsittribipi = lfsittribByLfitvendafklfsittribipi;
		this.aliqcofins = aliqcofins;
		this.vlripiunidtrib = vlripiunidtrib;
		this.modbcicms = modbcicms;
		this.modbcicmsst = modbcicmsst;
		this.aliqredbcicms = aliqredbcicms;
		this.aliqredbcicmsst = aliqredbcicmsst;
		this.aliqicmsst = aliqicmsst;
		this.aliqpis = aliqpis;
		this.vlrpisunidtrib = vlrpisunidtrib;
		this.vlrbasepis = vlrbasepis;
		this.vlrbasecofins = vlrbasecofins;
		this.vlrcofunidtrib = vlrcofunidtrib;
		this.vlrir = vlrir;
		this.vlrpis = vlrpis;
		this.vlrcofins = vlrcofins;
		this.vlrcsocial = vlrcsocial;
		this.vlrbaseicmsitvenda = vlrbaseicmsitvenda;
		this.vlrbaseicmsfreteitvenda = vlrbaseicmsfreteitvenda;
		this.vlricmsfreteitvenda = vlricmsfreteitvenda;
		this.vlrbasencm = vlrbasencm;
		this.aliqnacncm = aliqnacncm;
		this.aliqimpncm = aliqimpncm;
		this.vlrnacncm = vlrnacncm;
		this.vlrimpncm = vlrimpncm;
		this.emmanut = emmanut;
		this.dtins = dtins;
		this.hins = hins;
		this.idusuins = idusuins;
		this.dtalt = dtalt;
		this.halt = halt;
		this.idusualt = idusualt;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "codvenda", column = @Column(name = "CODVENDA", nullable = false)),
			@AttributeOverride(name = "coditvenda", column = @Column(name = "CODITVENDA", nullable = false)),
			@AttributeOverride(name = "tipovenda", column = @Column(name = "TIPOVENDA", nullable = false, length = 1)),
			@AttributeOverride(name = "codfilial", column = @Column(name = "CODFILIAL", nullable = false)),
			@AttributeOverride(name = "codemp", column = @Column(name = "CODEMP", nullable = false)) })
	public LfitvendaId getId() {
		return this.id;
	}

	public void setId(LfitvendaId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
			@JoinColumn(name = "CODSITTRIBPIS", referencedColumnName = "CODSITTRIB"),
			@JoinColumn(name = "IMPSITTRIBPIS", referencedColumnName = "IMPSITTRIB"),
			@JoinColumn(name = "CODFILIALSP", referencedColumnName = "CODFILIAL"),
			@JoinColumn(name = "CODEMPSP", referencedColumnName = "CODEMP") })
	public Lfsittrib getLfsittribByLfitvendafklfsittribpis() {
		return this.lfsittribByLfitvendafklfsittribpis;
	}

	public void setLfsittribByLfitvendafklfsittribpis(
			Lfsittrib lfsittribByLfitvendafklfsittribpis) {
		this.lfsittribByLfitvendafklfsittribpis = lfsittribByLfitvendafklfsittribpis;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
			@JoinColumn(name = "CODSITTRIBCOF", referencedColumnName = "CODSITTRIB"),
			@JoinColumn(name = "IMPSITTRIBCOF", referencedColumnName = "IMPSITTRIB"),
			@JoinColumn(name = "CODFILIALSC", referencedColumnName = "CODFILIAL"),
			@JoinColumn(name = "CODEMPSC", referencedColumnName = "CODEMP") })
	public Lfsittrib getLfsittribByLfitvendafklfsittribcof() {
		return this.lfsittribByLfitvendafklfsittribcof;
	}

	public void setLfsittribByLfitvendafklfsittribcof(
			Lfsittrib lfsittribByLfitvendafklfsittribcof) {
		this.lfsittribByLfitvendafklfsittribcof = lfsittribByLfitvendafklfsittribcof;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	public Vditvenda getVditvenda() {
		return this.vditvenda;
	}

	public void setVditvenda(Vditvenda vditvenda) {
		this.vditvenda = vditvenda;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
			@JoinColumn(name = "CODSITTRIBIPI", referencedColumnName = "CODSITTRIB"),
			@JoinColumn(name = "IMPSITTRIBIPI", referencedColumnName = "IMPSITTRIB"),
			@JoinColumn(name = "CODFILIALSI", referencedColumnName = "CODFILIAL"),
			@JoinColumn(name = "CODEMPSI", referencedColumnName = "CODEMP") })
	public Lfsittrib getLfsittribByLfitvendafklfsittribipi() {
		return this.lfsittribByLfitvendafklfsittribipi;
	}

	public void setLfsittribByLfitvendafklfsittribipi(
			Lfsittrib lfsittribByLfitvendafklfsittribipi) {
		this.lfsittribByLfitvendafklfsittribipi = lfsittribByLfitvendafklfsittribipi;
	}

	@Column(name = "ALIQCOFINS", precision = 15, scale = 5)
	public BigDecimal getAliqcofins() {
		return this.aliqcofins;
	}

	public void setAliqcofins(BigDecimal aliqcofins) {
		this.aliqcofins = aliqcofins;
	}

	@Column(name = "VLRIPIUNIDTRIB", precision = 15, scale = 5)
	public BigDecimal getVlripiunidtrib() {
		return this.vlripiunidtrib;
	}

	public void setVlripiunidtrib(BigDecimal vlripiunidtrib) {
		this.vlripiunidtrib = vlripiunidtrib;
	}

	@Column(name = "MODBCICMS")
	public Short getModbcicms() {
		return this.modbcicms;
	}

	public void setModbcicms(Short modbcicms) {
		this.modbcicms = modbcicms;
	}

	@Column(name = "MODBCICMSST")
	public Short getModbcicmsst() {
		return this.modbcicmsst;
	}

	public void setModbcicmsst(Short modbcicmsst) {
		this.modbcicmsst = modbcicmsst;
	}

	@Column(name = "ALIQREDBCICMS", precision = 15, scale = 5)
	public BigDecimal getAliqredbcicms() {
		return this.aliqredbcicms;
	}

	public void setAliqredbcicms(BigDecimal aliqredbcicms) {
		this.aliqredbcicms = aliqredbcicms;
	}

	@Column(name = "ALIQREDBCICMSST", precision = 15, scale = 5)
	public BigDecimal getAliqredbcicmsst() {
		return this.aliqredbcicmsst;
	}

	public void setAliqredbcicmsst(BigDecimal aliqredbcicmsst) {
		this.aliqredbcicmsst = aliqredbcicmsst;
	}

	@Column(name = "ALIQICMSST", precision = 15, scale = 5)
	public BigDecimal getAliqicmsst() {
		return this.aliqicmsst;
	}

	public void setAliqicmsst(BigDecimal aliqicmsst) {
		this.aliqicmsst = aliqicmsst;
	}

	@Column(name = "ALIQPIS", precision = 15, scale = 5)
	public BigDecimal getAliqpis() {
		return this.aliqpis;
	}

	public void setAliqpis(BigDecimal aliqpis) {
		this.aliqpis = aliqpis;
	}

	@Column(name = "VLRPISUNIDTRIB", precision = 15, scale = 5)
	public BigDecimal getVlrpisunidtrib() {
		return this.vlrpisunidtrib;
	}

	public void setVlrpisunidtrib(BigDecimal vlrpisunidtrib) {
		this.vlrpisunidtrib = vlrpisunidtrib;
	}

	@Column(name = "VLRBASEPIS", precision = 15, scale = 5)
	public BigDecimal getVlrbasepis() {
		return this.vlrbasepis;
	}

	public void setVlrbasepis(BigDecimal vlrbasepis) {
		this.vlrbasepis = vlrbasepis;
	}

	@Column(name = "VLRBASECOFINS", precision = 15, scale = 5)
	public BigDecimal getVlrbasecofins() {
		return this.vlrbasecofins;
	}

	public void setVlrbasecofins(BigDecimal vlrbasecofins) {
		this.vlrbasecofins = vlrbasecofins;
	}

	@Column(name = "VLRCOFUNIDTRIB", precision = 15, scale = 5)
	public BigDecimal getVlrcofunidtrib() {
		return this.vlrcofunidtrib;
	}

	public void setVlrcofunidtrib(BigDecimal vlrcofunidtrib) {
		this.vlrcofunidtrib = vlrcofunidtrib;
	}

	@Column(name = "VLRIR", precision = 15, scale = 5)
	public BigDecimal getVlrir() {
		return this.vlrir;
	}

	public void setVlrir(BigDecimal vlrir) {
		this.vlrir = vlrir;
	}

	@Column(name = "VLRPIS", precision = 15, scale = 5)
	public BigDecimal getVlrpis() {
		return this.vlrpis;
	}

	public void setVlrpis(BigDecimal vlrpis) {
		this.vlrpis = vlrpis;
	}

	@Column(name = "VLRCOFINS", precision = 15, scale = 5)
	public BigDecimal getVlrcofins() {
		return this.vlrcofins;
	}

	public void setVlrcofins(BigDecimal vlrcofins) {
		this.vlrcofins = vlrcofins;
	}

	@Column(name = "VLRCSOCIAL", precision = 15, scale = 5)
	public BigDecimal getVlrcsocial() {
		return this.vlrcsocial;
	}

	public void setVlrcsocial(BigDecimal vlrcsocial) {
		this.vlrcsocial = vlrcsocial;
	}

	@Column(name = "VLRBASEICMSITVENDA", precision = 15, scale = 5)
	public BigDecimal getVlrbaseicmsitvenda() {
		return this.vlrbaseicmsitvenda;
	}

	public void setVlrbaseicmsitvenda(BigDecimal vlrbaseicmsitvenda) {
		this.vlrbaseicmsitvenda = vlrbaseicmsitvenda;
	}

	@Column(name = "VLRBASEICMSFRETEITVENDA", precision = 15, scale = 5)
	public BigDecimal getVlrbaseicmsfreteitvenda() {
		return this.vlrbaseicmsfreteitvenda;
	}

	public void setVlrbaseicmsfreteitvenda(BigDecimal vlrbaseicmsfreteitvenda) {
		this.vlrbaseicmsfreteitvenda = vlrbaseicmsfreteitvenda;
	}

	@Column(name = "VLRICMSFRETEITVENDA", precision = 15, scale = 5)
	public BigDecimal getVlricmsfreteitvenda() {
		return this.vlricmsfreteitvenda;
	}

	public void setVlricmsfreteitvenda(BigDecimal vlricmsfreteitvenda) {
		this.vlricmsfreteitvenda = vlricmsfreteitvenda;
	}

	@Column(name = "VLRBASENCM", nullable = false, precision = 15, scale = 5)
	public BigDecimal getVlrbasencm() {
		return this.vlrbasencm;
	}

	public void setVlrbasencm(BigDecimal vlrbasencm) {
		this.vlrbasencm = vlrbasencm;
	}

	@Column(name = "ALIQNACNCM", nullable = false, precision = 9)
	public BigDecimal getAliqnacncm() {
		return this.aliqnacncm;
	}

	public void setAliqnacncm(BigDecimal aliqnacncm) {
		this.aliqnacncm = aliqnacncm;
	}

	@Column(name = "ALIQIMPNCM", nullable = false, precision = 9)
	public BigDecimal getAliqimpncm() {
		return this.aliqimpncm;
	}

	public void setAliqimpncm(BigDecimal aliqimpncm) {
		this.aliqimpncm = aliqimpncm;
	}

	@Column(name = "VLRNACNCM", nullable = false, precision = 15, scale = 5)
	public BigDecimal getVlrnacncm() {
		return this.vlrnacncm;
	}

	public void setVlrnacncm(BigDecimal vlrnacncm) {
		this.vlrnacncm = vlrnacncm;
	}

	@Column(name = "VLRIMPNCM", nullable = false, precision = 15, scale = 5)
	public BigDecimal getVlrimpncm() {
		return this.vlrimpncm;
	}

	public void setVlrimpncm(BigDecimal vlrimpncm) {
		this.vlrimpncm = vlrimpncm;
	}

	@Column(name = "EMMANUT", nullable = false, length = 1)
	public char getEmmanut() {
		return this.emmanut;
	}

	public void setEmmanut(char emmanut) {
		this.emmanut = emmanut;
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

}
