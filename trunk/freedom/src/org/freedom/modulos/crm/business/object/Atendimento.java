package org.freedom.modulos.crm.business.object;

import java.util.Date;

public class Atendimento {
	
	public static enum PREFS { IU,CODTPATENDO,CODATEND , CODSETAT, STATUSATENDO,DOCATENDO, DATAATENDO, DATAATENDOFIN, 
		HORAATENDO, HORAATENDOFIN, OBSATENDO, CODCLI, CODCONTR,  CODREC, NPARCITREC ,CODITCONTR, CODCHAMADO, 
		OBSINTERNO, CODESPEC, CODEMPCH, CODFILIALCH, CODEMPAE , CODFILIALAE, CONCLUICHAMADO };

	private Integer codtpatendo ;
	
	private Integer codatend ;
	
	private Integer codsetat ;
	
	private String statusatendo;
	
	private String docatendo;
	
	private Date dataatendo;
	
	private Date dataatendofin;
	
	private Date horaatendo;
	
	private Date horaatendofin;
	
	private String obsatendo;
	
	private Integer codcli;
	
	private Integer codcontr;
	
	private Integer codrec;
	
	private Integer nparcitrec;
	
	private Integer coditcontr;
	
	private Integer codchamado;
	
	private String obsinterno;
	
	private Integer codespc;
	
	private Integer codempch;
	
	private Integer codfilialch;
	
	private Integer codempae;
	
	private Integer codfilialae;
	
	private String concluichamado;
	
	
	public Integer getCodtpatendo() {
	
		return codtpatendo;
	}
	
	public void setCodtpatendo( Integer codtpatendo ) {
	
		this.codtpatendo = codtpatendo;
	}
	
	public Integer getCodatend() {
	
		return codatend;
	}
	
	public void setCodatend( Integer codatend ) {
	
		this.codatend = codatend;
	}
	
	public Integer getCodsetat() {
	
		return codsetat;
	}
	
	public void setCodsetat( Integer codsetat ) {
	
		this.codsetat = codsetat;
	}

	
	public String getStatusatendo() {
	
		return statusatendo;
	}

	
	public void setStatusatendo( String statusatendo ) {
	
		this.statusatendo = statusatendo;
	}

	
	public String getDocatendo() {
	
		return docatendo;
	}

	
	public void setDocatendo( String docatendo ) {
	
		this.docatendo = docatendo;
	}

	public String getObsatendo() {
	
		return obsatendo;
	}

	
	public void setObsatendo( String obsatendo ) {
	
		this.obsatendo = obsatendo;
	}

	
	
	public Integer getCodcli() {
	
		return codcli;
	}

	
	public void setCodcli( Integer codcli ) {
	
		this.codcli = codcli;
	}

	public Integer getCodcontr() {
	
		return codcontr;
	}

	
	public void setCodcontr( Integer codcontr ) {
	
		this.codcontr = codcontr;
	}

	
	public Integer getCoditcontr() {
	
		return coditcontr;
	}

	
	public void setCoditcontr( Integer coditcontr ) {
	
		this.coditcontr = coditcontr;
	}

	
	
	public Integer getCodrec() {
	
		return codrec;
	}

	
	public void setCodrec( Integer codrec ) {
	
		this.codrec = codrec;
	}

	
	public Integer getNparcitrec() {
	
		return nparcitrec;
	}

	
	public void setNparcitrec( Integer nparcitrec ) {
	
		this.nparcitrec = nparcitrec;
	}

	public Integer getCodchamado() {
	
		return codchamado;
	}

	
	public void setCodchamado( Integer codchamado ) {
	
		this.codchamado = codchamado;
	}

	
	public Integer getCodespc() {
	
		return codespc;
	}

	
	public void setCodespc( Integer codespc ) {
	
		this.codespc = codespc;
	}

	
	public Date getDataatendo() {
	
		return dataatendo;
	}

	
	public void setDataatendo( Date dataatendo ) {
	
		this.dataatendo = dataatendo;
	}

	
	public Date getDataatendofin() {
	
		return dataatendofin;
	}

	
	public void setDataatendofin( Date dataatendofin ) {
	
		this.dataatendofin = dataatendofin;
	}

	
	public Date getHoraatendo() {
	
		return horaatendo;
	}

	
	public void setHoraatendo( Date horaatendo ) {
	
		this.horaatendo = horaatendo;
	}

	
	public Date getHoraatendofin() {
	
		return horaatendofin;
	}

	
	public void setHoraatendofin( Date horaatendofin ) {
	
		this.horaatendofin = horaatendofin;
	}
	
	public String getObsinterno() {
		
		return obsinterno;
	}

	
	public void setObsinterno( String obsinterno ) {
	
		this.obsinterno = obsinterno;
	}

	
	public Integer getCodempch() {
	
		return codempch;
	}

	
	public void setCodempch( Integer codempch ) {
	
		this.codempch = codempch;
	}

	
	public Integer getCodfilialch() {
	
		return codfilialch;
	}

	
	public void setCodfilialch( Integer codfilialch ) {
	
		this.codfilialch = codfilialch;
	}

	
	public Integer getCodempae() {
	
		return codempae;
	}

	
	public void setCodempae( Integer codempae ) {
	
		this.codempae = codempae;
	}

	
	public Integer getCodfilialae() {
	
		return codfilialae;
	}

	
	public void setCodfilialae( Integer codfilialae ) {
	
		this.codfilialae = codfilialae;
	}

	
	public String getConcluichamado() {
	
		return concluichamado;
	}

	
	public void setConcluichamado( String concluichamado ) {
	
		this.concluichamado = concluichamado;
	}

}