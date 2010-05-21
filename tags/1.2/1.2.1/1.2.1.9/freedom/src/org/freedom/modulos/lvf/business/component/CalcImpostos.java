package org.freedom.modulos.lvf.business.component;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.frame.Aplicativo;

public class CalcImpostos {

	public static final String ICMS_ISENTO = "II";

	public static final String ICMS_TRIBUTADO_INTEGRALMENTE = "TT";
	
	public static final String ICMS_NAO_INSIDENCIA = "NN";
	
	public static final String ICMS_SUBSTITUICAO_TRIBUTARIA = "FF";
	
	public static final String TIPO_ST_ICMS_SUBSTITUIDO = "SI";
	
	public static final String TIPO_ST_ICMS_SUBSTITUTO = "SU";
	
	public static final String TIPO_RED_ICMS_BASE = "B"; 
	
	public static final String TIPO_RED_ICMS_VALOR = "V";
	
	public static final String TRANSACAO_ENTRADA = "CP";
	
	public static final String TRANSACAO_SAIDA = "VD";
	
	public static BigDecimal zero = new BigDecimal(0);
	
	public static BigDecimal cem = new BigDecimal(100);
	
	private int casasDec = Aplicativo.casasDec;

	private int casasDecFin = Aplicativo.casasDecFin;
	
	private boolean buscabase = true;

	private BigDecimal vlrprod = zero;
	
	private BigDecimal redfisc = zero;
	
	private BigDecimal vlrbaseipiit = zero;
	
	private BigDecimal vlrbaseicmsit = zero;
	
	private BigDecimal vlrbaseicmsitbrut = zero;
	
	private BigDecimal vlrliqit = zero;
	
	private BigDecimal vlricmsit = zero;
	
	private BigDecimal vlripiit = zero;
	
	private String tpredicmsfisc = null;
		
	private BigDecimal vlrdescit = zero;
	
	private String tipofisc = "";
	
	private String tipost = "";
	
	private String codnat = null;
	
	private String uftransacao = null;
	
	private Integer codprod = null;
	
	private String tipotransacao = null;
	
	private Integer coddestinatario = null;
	
	private Integer codtipomov = null;
	
	private String origfisc = null;
	private String codtrattrib = null;
	private Integer codmens = null; 
	
	private BigDecimal aliqfisc = zero;
	private BigDecimal aliqipifisc = zero;
	private BigDecimal margemvlragr = zero; 
	
	private Integer codempif = null;
	private Integer codfilialif = null;
	private String codfisc = null;
	private Integer coditfisc = null;
	
	public CalcImpostos( ) {
		
	}
	
	public void calcVlrLiqIt() {
	
		try {
			setVlrliqit( calcVlrTotalProd( getVlrprod(), getVlrdescit() ) );
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void calcIPI() {
		
		try {
			
			setVlrbaseipiit( getVlrprod().setScale( casasDecFin, BigDecimal.ROUND_UP ));		
			setVlripiit( getVlrbaseipiit().multiply( getAliqipifisc().divide( cem ) ));
	
		}
		catch (Exception e) {
			e.printStackTrace();
		}
				
	}	
		
	public void calcICMS(){
		
		BigDecimal percicmsit_calc = getAliqfisc().divide( cem );
		BigDecimal percredicmsit_calc = getRedfisc() !=null ? getRedfisc() : zero;
		BigDecimal vlrprod_calc = calcVlrTotalProd( getVlrprod(), getVlrdescit());
		
		
		try { 
			
			/** 
			*
			* Verifica se o tipo fiscal de ICMS se enquadra nas seguintes situações: 
			* 
			* 1 - ISENTO do ICMS
			* 2 - SUBSTITUICAO TRIBUTARIA do tipo SUBSTITUIDO (SI) (ICMS pago na operação anterior)
			* 3 - NAO INSIDENCIA
			* 
			* Nestes casos o percentual do ICMS deve ser "ZERO" bem como a base de calculo liquida e bruta.
			*
			**/
			

			if ( ( ICMS_ISENTO.equals( getTipofisc() ) ) || 
			   ( ( ICMS_SUBSTITUICAO_TRIBUTARIA.equals( getTipofisc() ) )  &&  ( TIPO_ST_ICMS_SUBSTITUIDO.equals(getTipost())) ) ||
			   ( ( ICMS_NAO_INSIDENCIA.equals( getTipofisc() ))  )) {
				
				setAliqfisc( zero );
				setVlricmsit( zero );

				if ( isBuscabase() ) {
					setVlrbaseicmsit( zero );
					setVlrbaseicmsitbrut( zero );
				}

			}
			

			/** 
			*
			* Verifica se o tipo fiscal de ICMS se enquadra nas seguintes situações: 
			* 
			* 1 - TRITUTADO INTEGRALMENTE
			* 2 - SUBSTITUICAO TRIBUTARIA do tipo SUBSTITUTO (SU) (ICMS deve ser recolhido normalmente + recolhimento de tributo relativo a operação posterior)
			* 
			* Nestes casos deve ser calculado o percentual, o valor e a base de calculo do ICMS.
			*
			**/
			
			else if ( ( ICMS_TRIBUTADO_INTEGRALMENTE.equals( getTipofisc() ) ) ||
					( ( ICMS_SUBSTITUICAO_TRIBUTARIA.equals( getTipofisc() ) )  &&  ( TIPO_ST_ICMS_SUBSTITUTO.equals(getTipost()))  )) {

				if ( vlrprod_calc.floatValue() > 0 ) {

					
					if ( isBuscabase() ) {
						
						/** 
						 * Definindo a base de calculo do ICMS: 
						 * 
						 * Verifica se há redução na base de calculo e aplica a redução necessária  
						 * 
						 * **/
						
						if ( TIPO_RED_ICMS_BASE.equals( getTpredicmsfisc()) && ( getRedfisc().floatValue() > 0 ) ) {
							BigDecimal vlrreducao = vlrprod.multiply( getRedfisc()).divide( cem ); 
							setVlrbaseicmsit( vlrprod_calc.subtract( vlrreducao )) ;  							
						}
						else {
							setVlrbaseicmsit( vlrprod_calc.setScale( casasDecFin, BigDecimal.ROUND_UP ));
						}

						setVlrbaseicmsitbrut( vlrprod_calc.setScale( casasDecFin, BigDecimal.ROUND_UP ));

					}

					/** 
					 * Definindo o valor do ICMS 
					 * 
					 * Verifica se há redução no valor e aplica a redução necessária  
					 * 
					 * **/
					
					if ( ( TIPO_RED_ICMS_VALOR.equals( tpredicmsfisc ) ) && ( getRedfisc().floatValue() > 0 ) ) {
						BigDecimal vlricmsit_calc = getVlrbaseicmsit().multiply( getAliqfisc().divide( cem ) );
						BigDecimal vlrreducao = vlricmsit_calc.multiply( getRedfisc() ).divide( cem ); 
						setVlricmsit( vlricmsit_calc.subtract( vlrreducao ));
						
					}
					else {
						setVlricmsit( getVlrbaseicmsit().multiply( getAliqfisc().divide( cem ) ));
					}
					
				}
				
			}
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void calcAliqFisc(BigDecimal aliq_atual) {


		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuilder sql = new StringBuilder();
		
		if ( aliq_atual.floatValue() > 0 ) {
			return; // Ele cai fora porque se existe um valor no CLFISCAL ele nem busca a Aliq. por Natureza da operaçao.
		}

		try {

			sql.append( "select percicms from lfbuscaicmssp( ?, ?, ?, ? )" );
			
			ps = Aplicativo.getInstace().getConexao().prepareStatement( sql.toString() );
			
			ps.setString( 1, getCodnat() );
			ps.setString( 2, getUftransacao() );
			ps.setInt( 3, Aplicativo.iCodEmp );
			ps.setInt( 4, Aplicativo.iCodFilialMz );
			
			rs = ps.executeQuery();

			if ( rs.next() ) {
				setAliqfisc( rs.getBigDecimal( 1 ) ) ;
			}

			rs.close();
			ps.close();

		} 
		catch ( SQLException err ) {
			err.printStackTrace();
			Funcoes.mensagemErro( null, "Erro ao buscar percentual de ICMS!\n" + err.getMessage(), true, err );
		} 
		finally {
			ps = null;
			rs = null;
		}
	}
		
	public void calcTratTrib() { 

		PreparedStatement ps = null;

		ResultSet rs = null;
		StringBuffer sql = new StringBuffer();

		sql.append( "select origfisc,codtrattrib,coalesce(redfisc,0) redfisc,tipofisc,codmens,");
		sql.append( "aliqfisc,coalesce(aliqipifisc,0) aliqipifisc,tpredicmsfisc,tipost,coalesce(margemvlagr,0) margemvlagr," );
		sql.append( "codempif,codfilialif,codfisc,coditfisc " );
		sql.append( "from lfbuscafiscalsp(?,?,?,?,?,?,?,?,?,?,?)" );				

		try {

			ps = Aplicativo.getInstace().getConexao().prepareStatement( sql.toString() );

			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "EQPRODUTO" ) );
			ps.setInt( 3, getCodprod() );

			ps.setInt( 4, Aplicativo.iCodEmp );
			
			if(TRANSACAO_ENTRADA.equals( getTipotransacao() )) {
				ps.setInt( 5, ListaCampos.getMasterFilial( "CPFORNECED" ) );
			}
			else if (TRANSACAO_SAIDA.equals( getTipotransacao() )) {
				ps.setInt( 5, ListaCampos.getMasterFilial( "VDCLIENTE" ) );				
			}
			
			ps.setInt( 6, getCoddestinatario() );			
			
			ps.setInt( 7, Aplicativo.iCodEmp );
			ps.setInt( 8, ListaCampos.getMasterFilial( "EQTIPOMOV" ) );
			
			ps.setInt( 9, getCodtipomov() );
			ps.setString( 10, getTipotransacao() );
			ps.setNull( 11, Types.CHAR );

			rs = ps.executeQuery();

			if ( rs.next() ) {
				
				setOrigfisc( rs.getString( "origfisc" ) );
				setCodtrattrib( rs.getString( "codtrattrib" ) );
				
				setRedfisc( rs.getBigDecimal( "redfisc" )  );
				setTipofisc( rs.getString( "tipofisc" ) );
				setTipost( rs.getString( "tipost" ) );
				setCodmens( rs.getInt( "codmens" ) );
				
				setAliqfisc( rs.getBigDecimal( "aliqfisc" ) );
				setAliqipifisc( rs.getBigDecimal( "aliqipifisc" ) );
				setTpredicmsfisc( rs.getString( "tpredicmsfisc" ) );
				setMargemvlragr( margemvlragr );
				
				if ( rs.getInt( "coditfisc" ) > 0 ) {
					setCodempif( rs.getInt( "codempif" ) );
					setCodfilialif( rs.getInt( "codfilialif" ) );
					setCodfisc( rs.getString( "codfisc" ) );
					setCoditfisc( rs.getInt( "coditfisc" ) );
					
				}					
			}

			rs.close();
			ps.close();

			Aplicativo.getInstace().getConexao().commit();

		} 
		catch ( SQLException err ) {
			err.printStackTrace();
			Funcoes.mensagemErro( null, "Erro ao buscar tratamento tributário!\n" + err.getMessage(), true, err );
		} 
		finally {
			ps = null;
			rs = null;
			sql = null;
		}

	}
	
	public BigDecimal calcVlrTotalProd( BigDecimal vlrprod, BigDecimal vlrdesc ) {

		BigDecimal bdRetorno = vlrprod.subtract( vlrdesc ).setScale( Aplicativo.casasDecFin, BigDecimal.ROUND_HALF_UP );
		return bdRetorno;
		
	}

	public BigDecimal getVlrprod() {
	
		return vlrprod;
	}

	
	public void setVlrprod( BigDecimal vlrprod ) {
	
		this.vlrprod = vlrprod;
	}

	
	public BigDecimal getVlrbaseipiit() {
	
		return vlrbaseipiit;
	}

	
	public void setVlrbaseipiit( BigDecimal vlrbaseipiit ) {
	
		this.vlrbaseipiit = vlrbaseipiit;
	}

	
	public BigDecimal getVlrbaseicmsit() {
	
		return vlrbaseicmsit;
	}

	
	public void setVlrbaseicmsit( BigDecimal vlrbaseicmsit ) {
	
		this.vlrbaseicmsit = vlrbaseicmsit;
	}

	
	public BigDecimal getVlrbaseicmsitbrut() {
	
		return vlrbaseicmsitbrut;
	}

	
	public void setVlrbaseicmsitbrut( BigDecimal vlrbaseicmsitbrut ) {
	
		this.vlrbaseicmsitbrut = vlrbaseicmsitbrut;
	}

	
	public BigDecimal getVlricmsit() {
	
		return vlricmsit;
	}

	
	public void setVlricmsit( BigDecimal vlricmsit ) {
	
		this.vlricmsit = vlricmsit;
	}

	
	public BigDecimal getVlripiit() {
	
		return vlripiit;
	}

	
	public void setVlripiit( BigDecimal vlripiitx ) {
	
		this.vlripiit = vlripiitx;
	}

	
	public String getTpredicmsfisc() {
	
		return tpredicmsfisc;
	}

	
	public void setTpredicmsfisc( String tpredicmsfisc ) {
	
		this.tpredicmsfisc = tpredicmsfisc;
	}

		
	public BigDecimal getVlrdescit() {
	
		return vlrdescit;
	}

	
	public void setVlrdescit( BigDecimal vlrdescit ) {
	
		this.vlrdescit = vlrdescit;
	}

	
	public String getTipofisc() {
	
		return tipofisc;
	}

	
	public void setTipofisc( String tipofisc ) {
	
		this.tipofisc = tipofisc;
	}

	
	public String getTipost() {
	
		return tipost;
	}

	
	public void setTipost( String tipost ) {
	
		this.tipost = tipost;
	}

	
	public BigDecimal getVlrliqit() {
	
		return vlrliqit;
	}

	
	public void setVlrliqit( BigDecimal vlrliqit ) {
	
		this.vlrliqit = vlrliqit;
	}
	
	public boolean isBuscabase() {
		
		return buscabase;
	}

	
	public void setBuscabase( boolean buscabase ) {
	
		this.buscabase = buscabase;
	}

	
	public String getCodnat() {
	
		return codnat;
	}

	
	public void setCodnat( String codnat ) {
	
		this.codnat = codnat;
	}

	public Integer getCodprod() {
	
		return codprod;
	}

	
	public void setCodprod( Integer codprod ) {
	
		this.codprod = codprod;
	}

	
	public String getUftransacao() {
	
		return uftransacao;
	}

	
	public void setUftransacao( String uftransacao ) {
	
		this.uftransacao = uftransacao;
	}

	
	public String getTipotransacao() {
	
		return tipotransacao;
	}

	
	public void setTipotransacao( String tipotransacao ) {
	
		this.tipotransacao = tipotransacao;
	}

	
	public Integer getCoddestinatario() {
	
		return coddestinatario;
	}

	
	public void setCoddestinatario( Integer coddestinatario ) {
	
		this.coddestinatario = coddestinatario;
	}

	
	public Integer getCodtipomov() {
	
		return codtipomov;
	}

	
	public void setCodtipomov( Integer codtipomov ) {
	
		this.codtipomov = codtipomov;
	}

	
	public String getOrigfisc() {
	
		return origfisc;
	}

	
	public void setOrigfisc( String origfisc ) {
	
		this.origfisc = origfisc;
	}

	
	public String getCodtrattrib() {
	
		return codtrattrib;
	}

	
	public void setCodtrattrib( String codtrattrib ) {
	
		this.codtrattrib = codtrattrib;
	}

	
	public Integer getCodmens() {
	
		return codmens;
	}

	
	public void setCodmens( Integer codmens ) {
	
		this.codmens = codmens;
	}

	
	public BigDecimal getMargemvlragr() {
	
		return margemvlragr;
	}

	
	public void setMargemvlragr( BigDecimal margemvlragr ) {
	
		this.margemvlragr = margemvlragr;
	}

	
	public Integer getCodempif() {
	
		return codempif;
	}

	
	public void setCodempif( Integer codempif ) {
	
		this.codempif = codempif;
	}

	
	public Integer getCodfilialif() {
	
		return codfilialif;
	}

	
	public void setCodfilialif( Integer codfilialif ) {
	
		this.codfilialif = codfilialif;
	}

	
	public String getCodfisc() {
	
		return codfisc;
	}

	
	public void setCodfisc( String codfisc ) {
	
		this.codfisc = codfisc;
	}

	
	public Integer getCoditfisc() {
	
		return coditfisc;
	}

	
	public void setCoditfisc( Integer coditfisc ) {
	
		this.coditfisc = coditfisc;
	}

	
	public BigDecimal getRedfisc() {
	
		return redfisc;
	}

	
	public void setRedfisc( BigDecimal redfisc ) {
	
		this.redfisc = redfisc;
	}

	
	public BigDecimal getAliqfisc() {
	
		return aliqfisc;
	}

	
	public void setAliqfisc( BigDecimal aliqfisc ) {
	
		this.aliqfisc = aliqfisc;
	}

	
	public BigDecimal getAliqipifisc() {
	
		return aliqipifisc;
	}

	
	public void setAliqipifisc( BigDecimal aliqipifisc ) {
	
		this.aliqipifisc = aliqipifisc;
	}
	
}
