package org.freedom.modulos.fnc.business.object;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;

import org.freedom.infra.pojos.Constant;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.frame.Aplicativo;

public class Cheque {

	private static final Constant TIPO_CHEQUE_PAG = new Constant( "Pago/Fornecedor", "PF" );
	
	private static final Constant TIPO_CHEQUE_REC = new Constant( "Recebido/Cliente", "RC" );
	
	private static final Constant SIT_CHEQUE_CADASTRADO = new Constant( "Cadastrado", "CA" );
	
	private static final Constant SIT_CHEQUE_EMITIDO = new Constant( "Emitido", "ED" );
	
	private static final Constant SIT_CHEQUE_COMPENSADO = new Constant( "Compensado", "CD" );
	
	private static final Constant SIT_CHEQUE_DEVOLVIDO = new Constant( "Devolvido", "DV" );

	private Integer seqcheq;
	
	private String codbanc;
	
	private String agenciacheq;
	
	private String contacheq;
	
	private Integer numcheq;
	
	private String nomeemitcheq;
	
	private String nomefavcheq;
	
	private Date dtemitcheq;
	
	private Date dtvenctocheq;
	
	private Date dtcompcheq;
	
	private String tipocheq;
	
	private String predatcheq;
	
	private String sitcheq;
	
	private BigDecimal vlrcheq;
	
	private String histcheq;
	
	private String cnpjemitcheq;
	
	private String cpfemitcheq;
	
	private String cnpjfavcheq;
	
	private String cpffavcheq;
	
	private String dddemitcheq;
	
	private String foneemitcheq;
	
	public Cheque() {

		//getTabJuros();
		
	}

	private BigDecimal getInfoCheque() {

		PreparedStatement ps = null;
		ResultSet rs = null;
		GregorianCalendar cal = null;

		StringBuilder sql = new StringBuilder();
		BigDecimal vlrjuros = new BigDecimal( 0 );

		try {
			sql.append( "select seqcheq, codbanc, agenciacheq, contacheq, numcheq, nomeemitcheq, nomefavcheq, dtemitcheq, dtvenctocheq, " );
			sql.append( "tipocheq, predatcheq, sitcheq, vlrcheq, histcheq, cnpjemitcheq, cpfemitcheq, cnpjfavcheq, cpffavcheq, dddemitcheq, foneemitcheq ");
			sql.append( "from fncheque ");
			sql.append( "where codemp=? and codfilial=? and seqcheq=?" );
			

			ps = Aplicativo.getInstace().con.prepareStatement( sql.toString() );
			cal = new GregorianCalendar();

			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "FNCHEQUE" ) );
//			ps.setInt( 3,  );
			

			rs = ps.executeQuery();

			if ( rs.next() ) {
			//	setPercjuros( rs.getBigDecimal( "percittbj" ) );
			//	setTipotabjuros( rs.getString( "tipotbj" ) );
			}

			rs.close();
			ps.close();
			// Aplicativo.getInstace().con.commit();
		} catch ( SQLException err ) {
			Funcoes.mensagemErro( null, "Erro ao buscar tabela de juros do sistema!\n" + err.getMessage(), true, null, err );
			err.printStackTrace();
		} finally {
			ps = null;
			rs = null;
			cal = null;
			sql = null;
		}
		return vlrjuros;
	}
	
	public static Vector<String> getLabelsTipoCheq( ) {

		Vector<String> ret = new Vector<String>();

		ret.addElement( "<--Selecione-->" );

		ret.add( TIPO_CHEQUE_PAG.getName() );
		ret.add( TIPO_CHEQUE_REC.getName() );

		return ret;

	}

	public static Vector<Object> getValoresTipoCheq( ) {

		Vector<Object> ret = new Vector<Object>();

		ret.addElement( "" );
		
		ret.add( TIPO_CHEQUE_PAG.getValue().toString() );
		ret.add( TIPO_CHEQUE_REC.getValue().toString() );

		return ret;

	}
	
	public static Vector<String> getLabelsSitCheq( ) {

		Vector<String> ret = new Vector<String>();

		ret.addElement( "<--Selecione-->" );

		ret.add( SIT_CHEQUE_CADASTRADO.getName().toString() );
		ret.add( SIT_CHEQUE_COMPENSADO.getName().toString() );
		ret.add( SIT_CHEQUE_DEVOLVIDO.getName().toString() );
		ret.add( SIT_CHEQUE_EMITIDO.getName().toString() );

		return ret;

	}

	public static Vector<Object> getValoresSitCheq( ) {

		Vector<Object> ret = new Vector<Object>();

		ret.addElement( "" );
		
		ret.add( SIT_CHEQUE_CADASTRADO.getValue().toString() );
		ret.add( SIT_CHEQUE_COMPENSADO.getValue().toString() );
		ret.add( SIT_CHEQUE_DEVOLVIDO.getValue().toString() );
		ret.add( SIT_CHEQUE_EMITIDO.getValue().toString() );

		return ret;

	}

	
	public Integer getSeqcheq() {
	
		return seqcheq;
	}

	
	public void setSeqcheq( Integer seqcheq ) {
	
		this.seqcheq = seqcheq;
	}

	
	public String getCodbanc() {
	
		return codbanc;
	}

	
	public void setCodbanc( String codbanc ) {
	
		this.codbanc = codbanc;
	}

	
	public String getAgenciacheq() {
	
		return agenciacheq;
	}

	
	public void setAgenciacheq( String agenciacheq ) {
	
		this.agenciacheq = agenciacheq;
	}

	
	public String getContacheq() {
	
		return contacheq;
	}

	
	public void setContacheq( String contacheq ) {
	
		this.contacheq = contacheq;
	}

	
	public Integer getNumcheq() {
	
		return numcheq;
	}

	
	public void setNumcheq( Integer numcheq ) {
	
		this.numcheq = numcheq;
	}

	
	public String getNomeemitcheq() {
	
		return nomeemitcheq;
	}

	
	public void setNomeemitcheq( String nomeemitcheq ) {
	
		this.nomeemitcheq = nomeemitcheq;
	}

	
	public String getNomefavcheq() {
	
		return nomefavcheq;
	}

	
	public void setNomefavcheq( String nomefavcheq ) {
	
		this.nomefavcheq = nomefavcheq;
	}

	
	public Date getDtemitcheq() {
	
		return dtemitcheq;
	}

	
	public void setDtemitcheq( Date dtemitcheq ) {
	
		this.dtemitcheq = dtemitcheq;
	}

	
	public Date getDtvenctocheq() {
	
		return dtvenctocheq;
	}

	
	public void setDtvenctocheq( Date dtvenctocheq ) {
	
		this.dtvenctocheq = dtvenctocheq;
	}

	
	public Date getDtcompcheq() {
	
		return dtcompcheq;
	}

	
	public void setDtcompcheq( Date dtcompcheq ) {
	
		this.dtcompcheq = dtcompcheq;
	}

	
	public String getTipocheq() {
	
		return tipocheq;
	}

	
	public void setTipocheq( String tipocheq ) {
	
		this.tipocheq = tipocheq;
	}

	
	public String getPredatcheq() {
	
		return predatcheq;
	}

	
	public void setPredatcheq( String predatcheq ) {
	
		this.predatcheq = predatcheq;
	}

	
	public String getSitcheq() {
	
		return sitcheq;
	}

	
	public void setSitcheq( String sitcheq ) {
	
		this.sitcheq = sitcheq;
	}

	
	public BigDecimal getVlrcheq() {
	
		return vlrcheq;
	}

	
	public void setVlrcheq( BigDecimal vlrcheq ) {
	
		this.vlrcheq = vlrcheq;
	}

	
	public String getHistcheq() {
	
		return histcheq;
	}

	
	public void setHistcheq( String histcheq ) {
	
		this.histcheq = histcheq;
	}

	
	public String getCnpjemitcheq() {
	
		return cnpjemitcheq;
	}

	
	public void setCnpjemitcheq( String cnpjemitcheq ) {
	
		this.cnpjemitcheq = cnpjemitcheq;
	}

	
	public String getCpfemitcheq() {
	
		return cpfemitcheq;
	}

	
	public void setCpfemitcheq( String cpfemitcheq ) {
	
		this.cpfemitcheq = cpfemitcheq;
	}

	
	public String getCnpjfavcheq() {
	
		return cnpjfavcheq;
	}

	
	public void setCnpjfavcheq( String cnpjfavcheq ) {
	
		this.cnpjfavcheq = cnpjfavcheq;
	}

	
	public String getCpffavcheq() {
	
		return cpffavcheq;
	}

	
	public void setCpffavcheq( String cpffavcheq ) {
	
		this.cpffavcheq = cpffavcheq;
	}

	
	public String getDddemitcheq() {
	
		return dddemitcheq;
	}

	
	public void setDddemitcheq( String dddemitcheq ) {
	
		this.dddemitcheq = dddemitcheq;
	}

	
	public String getFoneemitcheq() {
	
		return foneemitcheq;
	}

	
	public void setFoneemitcheq( String foneemitcheq ) {
	
		this.foneemitcheq = foneemitcheq;
	}

	
	




}
