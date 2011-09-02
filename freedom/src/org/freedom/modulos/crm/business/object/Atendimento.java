package org.freedom.modulos.crm.business.object;

import java.util.Date;

public class Atendimento {
	
	private enum PREFS { IU,CODTPATENDO,CODATEND , CODSETAT, STATUSATENDO,DOCATENDO, DATAATENDO, DATAATENDOFIN, 
		HORAATENDO, HORAATENDOFIN, OBSATENDO,CODCONTR, CODITCONTR, CODCHAMADO, CODESPEC  };

	private int codtpatendo ;
	
	private int codatend ;
	
	private int codsetat ;
	
	private String statusatendo;
	
	private String docatendo;
	
	private Date dataatendo;
	
	private Date dataatendofin;
	
	private Date horaatendo;
	
	private Date horaatendofin;
	
	private String obsatendo;
	
	private int codcontr;
	
	private int coditcontr;
	
	private int codchamado;
	
	private int codespc;
	
	public int getCodtpatendo() {
	
		return codtpatendo;
	}
	
	public void setCodtpatendo( int codtpatendo ) {
	
		this.codtpatendo = codtpatendo;
	}
	
	public int getCodatend() {
	
		return codatend;
	}
	
	public void setCodatend( int codatend ) {
	
		this.codatend = codatend;
	}
	
	public int getCodsetat() {
	
		return codsetat;
	}
	
	public void setCodsetat( int codsetat ) {
	
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

	
	public int getCodcontr() {
	
		return codcontr;
	}

	
	public void setCodcontr( int codcontr ) {
	
		this.codcontr = codcontr;
	}

	
	public int getCoditcontr() {
	
		return coditcontr;
	}

	
	public void setCoditcontr( int coditcontr ) {
	
		this.coditcontr = coditcontr;
	}

	
	public int getCodchamado() {
	
		return codchamado;
	}

	
	public void setCodchamado( int codchamado ) {
	
		this.codchamado = codchamado;
	}

	
	public int getCodespc() {
	
		return codespc;
	}

	
	public void setCodespc( int codespc ) {
	
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
	
}