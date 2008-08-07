/**
 * @version 18/07/2008 <BR>
 * @author Setpoint Informática Ltda.
 * @author Reginaldo Garcia Heua <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FRCertAnalise.java <BR>
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
 */
package org.freedom.modulos.pcp;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import net.sf.jasperreports.engine.JasperPrintManager;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FPrinterJob;
import org.freedom.telas.FRelatorio;

public class FRCertAnalise extends FRelatorio implements KeyListener{
	

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtCodProd = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	 
	private JTextFieldFK txtDescProd = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );
	 
	private JTextFieldPad txtCodFabProd = new JTextFieldPad( JTextFieldPad.TP_STRING, 13, 0 );
	 
	private JTextFieldPad txtCodAlmox = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
			
	private JTextFieldFK txtRefProd = new JTextFieldFK( JTextFieldPad.TP_STRING, 13, 0 );
	
	private JTextFieldPad txtCodLote = new JTextFieldPad( JTextFieldPad.TP_STRING, 20, 0 );
	
	private JTextFieldFK txtDtIniLote = new JTextFieldFK( JTextFieldPad.TP_DATE, 10, 0 );
	
	private JTextFieldFK txtDtVencLote = new JTextFieldFK( JTextFieldPad.TP_DATE, 10, 0 );
	
	private JTextFieldFK txtDtEmitPed = new JTextFieldFK( JTextFieldPad.TP_DATE, 10, 0 );
	
	private JTextFieldPad txtCodPed = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );
	
	private JTextFieldPad txtCodCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );
	
	private JTextFieldFK txtRazCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	private JTextFieldFK txtDocVenda = new JTextFieldFK( JTextFieldPad.TP_INTEGER, 10, 0 );
	
    private ListaCampos lcProd = new ListaCampos( this, ""  );
    
    private ListaCampos lcLote = new ListaCampos( this, "" );
    
    private ListaCampos lcPedido = new ListaCampos( this, "" );
    
    private ListaCampos lcCliente = new ListaCampos( this, "" );
	  
	public FRCertAnalise(){
		
		super( false );
		setAtribos( 50, 50, 400, 250 );
		setTitulo( "Certificado de Análise" );
		
		montaListaCampos();
		montaTela();
		
		txtCodPed.addKeyListener( this );
	}
	
	private void montaTela(){
		
		adic( new JLabelPad("Cód.Prod"), 7, 10, 70, 20 );
		adic( txtCodProd, 7, 30, 70, 20 );
		adic( new JLabelPad("Descrição do produto"), 80, 10, 250, 20 );
		adic( txtDescProd, 80, 30, 280, 20 );
		adic( new JLabelPad("Pedido"), 7, 55, 70, 20 );
		adic( txtCodPed, 7, 75, 70, 20 );
		adic( new JLabelPad("Cód.Cli"), 80, 55, 70, 20 );
		adic( txtCodCli, 80, 75, 70, 20 );
		adic( new JLabelPad("Razão social do cliente"), 153, 55, 200, 20 );
		adic( txtRazCli, 153, 75, 205, 20 );	
		adic( new JLabelPad("Dt.Emit.Ped."), 7, 95, 80, 20 );
		adic( txtDtEmitPed, 7, 115, 70, 20 );
		adic( new JLabelPad("Cód.lote"),85, 95, 80, 20 );
		adic( txtCodLote, 85, 115, 100, 20 );
		adic( new JLabelPad("Dt.Lote"), 188, 95, 90, 20 );
		adic( txtDtIniLote, 188, 115, 80, 20 );
		adic( new JLabelPad("Dt.Venc.Lt"), 273, 95, 80, 20 );
		adic( txtDtVencLote, 273, 115, 85, 20 );
		

	}
	
	private void montaListaCampos(){
		
		/**************
		 *  Produto   * 
		 **************/
		
	     lcProd.add( new GuardaCampo( txtCodProd, "CodProd", "Cód.prod.", ListaCampos.DB_PK, false ));
         lcProd.add( new GuardaCampo( txtRefProd, "RefProd", "Referência do produto", ListaCampos.DB_SI, false ));
         lcProd.add( new GuardaCampo( txtDescProd, "DescProd", "Descrição do produto", ListaCampos.DB_SI, false ));
         lcProd.add( new GuardaCampo( txtCodFabProd, "codfabprod", "Cód.fab.prod.",ListaCampos.DB_SI, false ));
         txtCodProd.setTabelaExterna( lcProd );
         txtCodProd.setNomeCampo( "CodProd" );
         txtCodProd.setFK(true);
         lcProd.setReadOnly(true);
         lcProd.montaSql( false, "PRODUTO", "EQ" );
         
         /**************
 		 *    Lote     * 
 		 **************/
         
         lcLote.add( new GuardaCampo( txtCodProd, "CodProd", "Cód.Prod", ListaCampos.DB_PF, false ));
         lcLote.add( new GuardaCampo( txtCodLote, "CodLote", "Cód.lote", ListaCampos.DB_PK, true ));
         lcLote.add( new GuardaCampo( txtDtVencLote, "VenctoLote", "Vencimento do lote", ListaCampos.DB_SI, false ));
         lcLote.add( new GuardaCampo( txtDtIniLote, "DIniLote", "Data do lote", ListaCampos.DB_SI, false ));
         txtCodLote.setTabelaExterna(lcLote);
         txtCodLote.setNomeCampo("CodLote");
         txtCodLote.setFK(true);
         lcLote.setReadOnly(true);
         lcLote.setDinWhereAdic("CODPROD = #N",txtCodProd);
         lcLote.montaSql(false, "LOTE", "EQ");
         
         /**************
  		 *   Pedido    * 
  		 **************/
         
         lcPedido.add( new GuardaCampo( txtCodPed, "CodVenda", "Cód.ped.", ListaCampos.DB_PK, false ));
         lcPedido.add( new GuardaCampo( txtCodCli, "CodCli", "Cód.Cli", ListaCampos.DB_FK, false ));
         lcPedido.add( new GuardaCampo( txtDtEmitPed, "DtEmitVenda", "Dt.Emissão", ListaCampos.DB_FK, false ));
         lcPedido.add( new GuardaCampo( txtDocVenda, "DocVenda", "Doc.Venda", ListaCampos.DB_SI, false ));
         txtCodPed.setTabelaExterna( lcPedido );
         txtCodPed.setNomeCampo( "CodVenda" );
         txtCodPed.setFK(true);
         lcPedido.setReadOnly(true);
         lcPedido.montaSql( false, "VENDA", "VD" );
         
         /**************
   		 *   Cliente   * 
   		 **************/
          
          lcCliente.add( new GuardaCampo( txtCodCli, "CodCli", "Cód.cli.", ListaCampos.DB_PK, false ) );
          lcCliente.add( new GuardaCampo( txtRazCli, "RazCli", "Razão social do cliente", ListaCampos.DB_FK, false ) );
          txtCodCli.setTabelaExterna( lcCliente );
          txtCodCli.setNomeCampo( "CodCli" );
          txtCodCli.setFK(true);
          lcCliente.setReadOnly(true);
          lcCliente.montaSql( false, "CLIENTE", "VD" );
         
         
	}

	public void imprimir( boolean b ) {
		
		StringBuffer sql = new StringBuffer();
		StringBuffer bNumCert = new StringBuffer();
		ResultSet rs = null;
		PreparedStatement ps = null;
		int iCodProd = 0;
		String descProd = "";
		
		try {
			
			sql.append( "select op0.codprod, pd.descprod " );
			sql.append( "from ppop op0,eqproduto pd, ppop opx " );
			sql.append( "where " );
			sql.append( "pd.codemp=op0.codemppd and pd.codfilial=op0.codfilialpd and pd.codprod=op0.codprod " );
			sql.append( "and op0.codemp = opx.codemp and op0.codfilial=opx.codfilial and op0.codop=opx.codop and op0.seqop=0 " );
			sql.append( "and opx.codemp=? and opx.codfilial=? and opx.codlote=? " );

		
			ps = con.prepareStatement( sql.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "PPOP" ) );
			ps.setString( 3, txtCodLote.getVlrString() );
			rs = ps.executeQuery();
			
			if( rs.next() ){				
				iCodProd = rs.getInt( "codprod" );
				descProd = rs.getString( "descprod" );
			}
			
			sql.delete( 0, sql.length() );
			
			sql.append( "select ta.desctpanalise,ea.vlrmin,ea.vlrmax,cq.vlrafer,cq.descafer,pf.nomeresp,pf.identprofresp,pf.imgassresp, " );
			sql.append( "op0.codprod, pd.descprod, pm.descmtanalise, ea.especificacao, eq.casasdec " );
			sql.append( "from ppopcq cq, ppestruanalise ea, pptipoanalise ta, sgprefere5 pf, ppop op0, ppop opx, eqproduto pd, ppmetodoanalise pm, equnidade eq " );
			sql.append( "where " );
			sql.append( "ta.codemp=ea.codempta and ta.codfilial=ea.codfilialta and ta.codtpanalise=ea.codtpanalise " );
			sql.append( "and pm.codemp=ta.codempma and pm.codfilial=ta.codfilialma " );
			sql.append( "and pm.codmtanalise=ta.codmtanalise " );
			sql.append( "and cq.codempea=ea.codemp and cq.codfilialea=ea.codfilial and cq.codestanalise=ea.codestanalise " );
			sql.append( "and cq.codemp=op0.codemp and cq.codfilial=op0.codfilial and cq.codop=op0.codop and cq.seqop=op0.seqop " );
			sql.append( "and cq.status='AP' and ea.emitcert='S' " );
			sql.append( "and op0.codemp = opx.codemp and op0.codfilial=opx.codfilial and op0.codop=opx.codop and op0.seqop=0 " );
			sql.append( "and pf.codemp = op0.codemp and pf.codfilial=op0.codfilial " );
			sql.append( "and pd.codemp=op0.codemppd and pd.codfilial=op0.codfilialpd and pd.codprod=op0.codprod " );
			sql.append( "and eq.codemp=ta.codempud and eq.codfilial=ta.codfilialud " );
			sql.append( "and eq.codunid=ta.codunid " );
			sql.append( "and opx.codemp=? and opx.codfilial=? and opx.codlote=? " );			
			
			ps = con.prepareStatement( sql.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "PPOPCQ" ) );
			ps.setString( 3, txtCodLote.getVlrString() );
			rs = ps.executeQuery();
			
			bNumCert.append( iCodProd );
			bNumCert.append( Funcoes.strZero( txtCodCli.getVlrString().trim(), 4 ));
			
			if( txtDocVenda.getVlrInteger() != 0 && txtDocVenda.getVlrInteger() != null ){
				
				bNumCert.append( txtDocVenda.getVlrInteger() );
			}
			
			
		} catch ( SQLException e ) {
			
			Funcoes.mensagemErro( this, "Erro ao montar relatório" );
			e.getMessage();
			e.printStackTrace();
		}
		
		imprimiGrafico( b, rs, "", bNumCert.toString(), descProd );
	}
	
	private void imprimiGrafico(  final boolean bVisualizar,  ResultSet rs,  final String sCab, String numCert, String descProd  ) {

		FPrinterJob dlGr = null;
		HashMap<String, Object> hParam = new HashMap<String, Object>();

		hParam.put( "CODEMP", Aplicativo.iCodEmp );
		hParam.put( "CODFILIAL", ListaCampos.getMasterFilial( "CPCOMPRA" ) );
		hParam.put( "RAZAOEMP", Aplicativo.sEmpSis );		
	    hParam.put( "CODLOTE", txtCodLote.getVlrString() );
		hParam.put( "DESCPROD", descProd );
		hParam.put( "FABRICACAO", txtDtIniLote.getVlrDate() );
		hParam.put( "VALIDADE", txtDtVencLote.getVlrDate() );
		hParam.put( "NF", txtCodPed.getVlrInteger() == 0 ? null : txtCodPed.getVlrInteger() );
		hParam.put( "EMITNF", txtDtEmitPed.getVlrDate() );
		hParam.put( "CODCLI", txtCodCli.getVlrInteger() == 0 ? null : txtCodCli.getVlrInteger() );
		hParam.put( "RAZCLI", txtRazCli.getVlrString().equals( "" ) ? null : txtRazCli.getVlrString() );
		hParam.put( "CODPROD", txtCodProd.getVlrInteger().toString() );
		hParam.put( "DOCVENDA", txtDocVenda.getVlrInteger() == 0 ? null : txtDocVenda.getVlrInteger() );

		hParam.put( "NUMCERT", numCert );

		dlGr = new FPrinterJob("relatorios/FRCertAnalise.jasper", "Certificado de Análise", "", rs, hParam, this );
		
		if ( bVisualizar ) {
			
			dlGr.setVisible( true );
		}
		else {
			try {
				JasperPrintManager.printReport( dlGr.getRelatorio(), true );
			} catch ( Exception err ) {
				Funcoes.mensagemErro( this, "Erro na impressão de relatório Certificação de análise!" + err.getMessage(), true, con, err );
			}
		}
	}
	
	public void keyPressed( KeyEvent kevt ) {

		super.keyPressed(kevt);
		
		try {
			
			if( kevt.getSource() == txtCodPed ){
				if( !txtCodPed.getVlrString().equals( "" )){
					if( kevt.getKeyCode() == KeyEvent.VK_ENTER ){
						txtCodCli.setEditable( false );
						
					}
				}
				else{
					txtCodCli.setEditable( true );
				}
			}
			
		} catch ( Exception e ) {
			System.out.println("Pedido Nullo!!!");
			e.printStackTrace();
		}
		
	}

	public void setConexao( Connection cn ){
		
		super.setConexao( cn );
		lcProd.setConexao( cn );
		lcLote.setConexao( cn );
		lcPedido.setConexao( cn );
		lcCliente.setConexao( cn );
	}
}
