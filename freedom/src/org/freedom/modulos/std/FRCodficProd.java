/**
 * @version 24/07/2007 <BR>
 * @author Setpoint Informática Ltda./Reginaldo Garcia <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FRCodficProd.java <BR>
 * 
 * Este programa é licenciado de acordo com a LPG-PC (Licença Pública Geral para Programas de Computador), <BR>
 * versão 2.1.0 ou qualquer versão posterior. <BR>
 * A LPG-PC deve acompanhar todas PUBLICAÇÕES, DISTRIBUIÇÕES e REPRODUÇÕES deste Programa. <BR>
 * Caso uma cópia da LPG-PC não esteja disponível junto com este Programa, você pode contatar <BR>
 * o LICENCIADOR ou então pegar uma cópia em: <BR>
 * Licença: http://www.lpg.adv.br/licencas/lpgpc.rtf <BR>
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa é preciso estar <BR>
 * de acordo com os termos da LPG-PC <BR> <BR>
 *
 * Formulário de liberação de crédito por pedido de venda.
 */
package org.freedom.modulos.std;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Vector;

import net.sf.jasperreports.engine.JasperPrintManager;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FPrinterJob;
import org.freedom.telas.FRelatorio;


public class FRCodficProd extends FRelatorio {

	private static final long serialVersionUID = 1L;
	
	private JTextFieldPad txtCodMarca = new JTextFieldPad(JTextFieldPad.TP_STRING,6,0);

	private JTextFieldFK txtDescMarca = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
	
	private JTextFieldPad txtCodGrupo = new JTextFieldPad(JTextFieldPad.TP_STRING,14,0);
	
	private JTextFieldFK txtDescGrupo = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0); 
	
	private ListaCampos lcGrupo = new ListaCampos(this);
	
	private ListaCampos lcMarca = new ListaCampos(this);
	
	private JRadioGroup rgOrdem = null;
	
	private JRadioGroup rgAtivoProd = null;
	
	private JRadioGroup rgProd = null;
	
	private JCheckBoxPad cbRel = new JCheckBoxPad("Agrupar por grupo?", true, true);
	 
	private Vector<String> vLabs = new Vector<String>();
	
	private Vector<String> vVals = new Vector<String>();
	
	private Vector<String> vVals1 = new Vector<String>();
	
	private Vector<String> vLabs1 = new Vector<String>();
	
	private Vector<String> vVals2 = new Vector<String>();
	
	private Vector<String> vLabs2 = new Vector<String>();
	  
	
	public FRCodficProd(){
		
		setTitulo("Codificação de Produtos");
	    setAtribos(80,30,490,300);
	    
	    vLabs.addElement("Código");
	    vLabs.addElement("Descrição");
	    vVals.addElement("CODPROD ");
	    vVals.addElement("DESCPROD ");
	   
	    rgOrdem = new JRadioGroup<String, String>(1,2,vLabs,vVals);
	    rgOrdem.setVlrString("D");
	    
	    vLabs1.addElement("Ativos");
		vLabs1.addElement("Inativos");
		vLabs1.addElement("Todos");
		vVals1.addElement("A");
		vVals1.addElement("N");
		vVals1.addElement("T");
		rgAtivoProd = new  JRadioGroup<String, String>(1,3, vLabs1, vVals1);
		rgAtivoProd.setVlrString("A");
		
		vLabs2.addElement("Código");
		vLabs2.addElement("Referência");
		vLabs2.addElement("Cód. Barras");
		vVals2.addElement("C");
		vVals2.addElement("R");
		vVals2.addElement("B");
		rgProd = new  JRadioGroup<String, String>(1,3, vLabs2, vVals2);
		rgProd.setVlrString("C");
	    
		montaTela();
		montaListaCampos();
	}

	public void montaTela(){
	
		adic( new JLabelPad("Ordenar por: "),7, 3, 80, 30 );
		adic(rgOrdem,7, 27, 190, 30);
		adic( new JLabelPad("Filtrar por: "),200, 3, 80, 30 );
		adic( rgAtivoProd, 200, 27, 240, 30 );
		adic( new JLabelPad("Listar por: "), 7, 60, 80, 20 );
		adic( rgProd, 7, 78, 433, 30 );
		adic( new JLabelPad("Cod.grupo"), 7, 117, 100, 20 );
		adic( txtCodGrupo, 7, 137, 80, 20 );
		adic( new JLabelPad("Descrição do grupo"),90, 117, 250, 20 );
		adic( txtDescGrupo, 90, 137, 350, 20 );
		adic( new JLabelPad ("Cod.marca"), 7, 155, 350, 20 );
		adic( txtCodMarca, 7, 175, 80, 20 );
		adic( new JLabelPad("Descrição da marca"),90, 155, 200, 20 );
		adic( txtDescMarca, 90, 175, 350, 20 );
		adic(cbRel, 7, 200, 250, 20 );
	}
	
	public void montaListaCampos(){
		

		/************
		 * LC Grupo *
		 ************/
		lcGrupo.add(new GuardaCampo( txtCodGrupo, "CodGrup", "Cód.grupo", ListaCampos.DB_PK, false));
		lcGrupo.add(new GuardaCampo( txtDescGrupo, "DescGrup", "Descrição do grupo", ListaCampos.DB_SI, false));
		lcGrupo.montaSql(false, "GRUPO", "EQ");
		lcGrupo.setReadOnly(true);
		txtCodGrupo.setTabelaExterna(lcGrupo);
		txtCodGrupo.setFK(true);
		txtCodGrupo.setNomeCampo("CodGrup");
		
		/************
		 * LC Marca *
		 ************/
		lcMarca.add(new GuardaCampo( txtCodMarca, "CodMarca", "Cód.marca", ListaCampos.DB_PK, false));
		lcMarca.add(new GuardaCampo( txtDescMarca, "DescMarca", "Descrição da marca", ListaCampos.DB_SI, false));
		txtCodMarca.setTabelaExterna(lcMarca);
		txtCodMarca.setNomeCampo("CodMarca");
		txtCodMarca.setFK(true);
		lcMarca.setReadOnly(true);
		lcMarca.montaSql(false, "MARCA", "EQ");
		
	}
	
	@ Override
	public void imprimir( boolean bVisualizar ) {
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sSQL = new StringBuffer();
		StringBuffer filtro = new StringBuffer();
		
		if ( "A".equals( rgAtivoProd.getVlrString() ) ) {
			filtro.append( "AND P.ATIVOPROD='S' " );
		}
		else if ( "N".equals( rgAtivoProd.getVlrString() ) ) {
			filtro.append( "AND P.ATIVOPROD='N' " );
		}
		
		if ( txtCodGrupo.getVlrString() != null && txtCodGrupo.getVlrString().trim().length()>0 ) {
			filtro.append( " AND P.CODEMPGP=" );
			filtro.append( Aplicativo.iCodEmp );
			filtro.append( " AND P.CODFILIALGP=" );
			filtro.append( ListaCampos.getMasterFilial( "EQGRUPO" ) );
			filtro.append( "AND P.CODGRUP LIKE '" );
			filtro.append( txtCodGrupo.getVlrString().trim() + "%'" );
		}
		
		if ( txtCodMarca.getVlrString() != null && txtCodMarca.getVlrString().trim().length()>0 ) {
			filtro.append( " AND P.CODEMPMC=" );
			filtro.append( Aplicativo.iCodEmp );
			filtro.append( " AND P.CODFILIALMC=" );
			filtro.append( ListaCampos.getMasterFilial( "EQMARCA" ) );
			filtro.append( "AND P.CODMARCA='" );
			filtro.append( txtCodMarca.getVlrString().trim() + "'" );
		}
		
		sSQL.append( "SELECT P.CODGRUP, G.DESCGRUP, P.CODPROD,P.DESCPROD,P.CODBARPROD, P.REFPROD, LC.ALIQIPIFISC " );
		sSQL.append( "FROM EQPRODUTO  P, LFCLFISCAL LC, EQGRUPO G WHERE P.CODEMP=? AND P.CODFILIAL=? " );
		sSQL.append( "AND LC.CODEMP=P.CODEMPFC AND LC.CODFILIAL=P.CODFILIALFC AND LC.CODFISC=P.CODFISC AND ");
		sSQL.append( "G.CODEMP=P.CODEMPGP AND G.CODFILIAL=P.CODFILIALGP AND G.CODGRUP=P.CODGRUP ");
		sSQL.append( filtro );
		sSQL.append( " ORDER BY " );
		if ( cbRel.getStatus() == true ) {
			sSQL.append("P.CODGRUP, ");
		}
		sSQL.append( rgOrdem.getVlrString() );
		
		try {
			
			ps = con.prepareStatement( sSQL.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "EQPRODUTO" ) );
			rs = ps.executeQuery();
			
		} catch ( Exception e ) {
		
			Funcoes.mensagemErro( this, "Erro ao buscar dados do produto !\n" + e.getMessage());
			e.printStackTrace();
		}
		
		HashMap<String, Object> hParam = new HashMap<String, Object>();

		hParam.put( "CODREF", rgProd.getVlrString() );
		hParam.put( "CODEMP", Aplicativo.iCodEmp );
		hParam.put( "CODFILIAL", ListaCampos.getMasterFilial( "EQPRODUTO" ));
		
		if( cbRel.getStatus() == true){
		
			FPrinterJob dlGr = new FPrinterJob( "relatorios/CodficProdGrup.jasper", "Codificação de produto", null, rs, hParam, this );
			
			if ( bVisualizar ) {
				dlGr.setVisible( true );
			}
			else {		
				try {				
					JasperPrintManager.printReport( dlGr.getRelatorio(), true );				
				} catch ( Exception err ) {					
					Funcoes.mensagemErro( this, "Erro na impressão de relatório de codificação de produto!\n" + err.getMessage(), true, con, err );
				}
			}
		}
		else{
			
			FPrinterJob dlGr = new FPrinterJob( "relatorios/CodficProd.jasper", "Codificação de produto", null, rs, hParam, this );
			
			if ( bVisualizar ) {
				dlGr.setVisible( true );
			}
			else {		
				try {				
			
					JasperPrintManager.printReport( dlGr.getRelatorio(), true );				
				} catch ( Exception err ) {					
					Funcoes.mensagemErro( this, "Erro na impressão de relatório de codificação de produto!\n" + err.getMessage(), true, con, err );
				}
			}
		}
	}
	
	public void setConexao(Connection cn) {
		  	
		super.setConexao(cn);
		lcGrupo.setConexao( cn );
		lcMarca.setConexao( cn );
	
	}
}
