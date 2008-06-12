package org.freedom.componentes;

import java.math.BigDecimal;
import java.util.Date;

import org.freedom.funcoes.Funcoes;


public class ObjetoHistorico {
	private String historicocodificado;
	private String documento;
	private BigDecimal valor;
	private String serie;
	private Date data;
	private String historicodecodificado;

	protected void decodeHistorico( ) {
		
		String decoded = null;
		
		if ( historicocodificado != null ) {	
			
			try {
				
				String tmp = historicocodificado;
								
				tmp = tmp.replaceAll( "<DOCUMENTO>", getDocumento() != null ? getDocumento() : "" );				
				tmp = tmp.replaceAll( "<VALOR>", getValor() != null ? String.valueOf( getValor() ) :  "0.00"  ) ;
				tmp = tmp.replaceAll( "<SERIE>", getSerie() != null ? getSerie() : "" );
				tmp = tmp.replaceAll( "<DATA>", getData() !=null ? Funcoes.dateToStrDate( getData() ) : "");
				
				decoded = tmp;
			}
			catch ( Exception e ) {
				e.printStackTrace();
			}
		}
		
		setHistoricoDecodificado( decoded );
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

	
	public String getHistoricoDecodificado() {
		decodeHistorico();
		return historicodecodificado;
	}

	
	public void setHistoricoDecodificado( String historicodecodificado ) {
	
		this.historicodecodificado = historicodecodificado;
	}

	
	public String getSerie() {
	
		return serie;
	}

	
	public void setSerie( String serie ) {
	
		this.serie = serie;
	}

	
	public String getHistoricoCodificado() {
	
		return historicocodificado;
	}

	
	public void setHistoricoCodificado( String historicocodificado ) {
	
		this.historicocodificado = historicocodificado;
	}

	
	public BigDecimal getValor() {
	
		return valor;
	}

	
	public void setValor( BigDecimal valor ) {
	
		this.valor = valor;
	}
}
