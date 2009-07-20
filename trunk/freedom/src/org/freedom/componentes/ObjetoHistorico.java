package org.freedom.componentes;

import java.math.BigDecimal;
import org.freedom.infra.model.jdbc.DbConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;


public class ObjetoHistorico {
	private String historicocodificado;
	private String documento;
	private String portador;
	private BigDecimal valor;
//	private String serie;
	private Date data;
	private String historicodecodificado;
	private boolean isDecoded = false;
	
	public ObjetoHistorico() {
		super();
	}

	public ObjetoHistorico(final Integer codhist, DbConnection con) {
		super();
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement( "SELECT TXAHISTPAD FROM FNHISTPAD WHERE CODEMP=? AND CODFILIAL=? AND CODHIST=?" );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "FNHISTPAD" ) );
			ps.setInt( 3, codhist );

			rs = ps.executeQuery();

			if ( rs.next() ) {
				setHistoricocodificado( rs.getString( 1 ) );
			}
			
			rs.close();
			ps.close();

			con.commit();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void decodeHistorico( ) {
		
		String decoded = null;
		
		if ( historicocodificado != null ) {	
			
			try {
				
				String tmp = historicocodificado;			
				
				String vlr = "0,00";
				
				if(getValor()!=null) {
					vlr = Funcoes.bdToStr( getValor() ).toString();
				}
				
				tmp = tmp.replaceAll( "<DOCUMENTO>", getDocumento() != null ? getDocumento() : "" );				
				tmp = tmp.replaceAll( "<VALOR>", vlr ) ;								
//				tmp = tmp.replaceAll( "<SERIE>", getSerie() != null ? getSerie() : "" );
				tmp = tmp.replaceAll( "<DATA>", getData() !=null ? Funcoes.dateToStrDate( getData() ) : "");
				tmp = tmp.replaceAll( "<PORTADOR>", getPortador() != null ? getPortador() : "" );
				
				decoded = tmp;
				isDecoded = true;
			}
			catch ( Exception e ) {
				e.printStackTrace();
			}
		}
		setHistoricodecodificado( decoded );

	}

	
	public Date getData() {
	
		return data;
	}

	
	public void setData( Date data ) {
	
		this.data = data;
	}

	
	public String getDocumento() {
	
		return documento;
	}

	
	public void setDocumento( String documento ) {
	
		this.documento = documento;
	}

	/*
	public String getSerie() {
	
		return serie;
	}

	
	public void setSerie( String serie ) {
	
		this.serie = serie;
	}
	 */
	
	public BigDecimal getValor() {
	
		return valor;
	}

	
	public void setValor( BigDecimal valor ) {
	
		this.valor = valor;
	}


	
	public String getHistoricocodificado() {
	
		return historicocodificado;
	}


	
	public void setHistoricocodificado( String historicocodificado ) {
	
		this.historicocodificado = historicocodificado;
	}

	public String getHistoricodecodificado() {
		if(!isDecoded) {
			decodeHistorico();
		}
		return historicodecodificado;
	}
	
	public void setHistoricodecodificado( String historicodecodificado ) {
	
		this.historicodecodificado = historicodecodificado;
	}


	
	public String getPortador() {
	
		return portador;
	}


	
	public void setPortador( String portador ) {
	
		this.portador = portador;
	}
}
