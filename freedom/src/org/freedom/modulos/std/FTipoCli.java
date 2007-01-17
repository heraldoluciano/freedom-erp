/**
 * @version 02/11/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)FTipoCli.java <BR>
 * 
 * Este programa é licenciado de acordo com a LPG-PC (Licença Pública Geral para Programas de Computador), <BR>
 * versão 2.1.0 ou qualquer versão posterior. <BR>
 * A LPG-PC deve acompanhar todas PUBLICAÇÕES, DISTRIBUIÇÕES e REPRODUÇÕES deste Programa. <BR>
 * Caso uma cópia da LPG-PC não esteja disponível junto com este Programa, você pode contatar <BR>
 * o LICENCIADOR ou então pegar uma cópia em: <BR>
 * Licença: http://www.lpg.adv.br/licencas/lpgpc.rtf <BR>
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa é preciso estar <BR>
 * de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 * Tela para cadastro de tipos de clientes.
 * 
 */

package org.freedom.modulos.std;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import net.sf.jasperreports.engine.JasperPrintManager;

import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.JPanelPad;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FDados;
import org.freedom.telas.FPrinterJob;

public class FTipoCli extends FDados implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtCod = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );

	private JTextFieldPad txtDesc = new JTextFieldPad( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtSgTpCli = new JTextFieldPad( JTextFieldPad.TP_STRING, 3, 0 );

	private JPanelPad pinInfoFicha = new JPanelPad( 300, 150 );

	private JCheckBoxPad cbTipoCadFis = new JCheckBoxPad( "Pessoa física", "S", "N" );

	private JCheckBoxPad cbTipoCadJur = new JCheckBoxPad( "Pessoa jurídica", "S", "N" );

	private JCheckBoxPad cbTipoCadCheq = new JCheckBoxPad( "Cheque", "S", "N" );

	private JCheckBoxPad cbTipoCadFil = new JCheckBoxPad( "Filiação", "S", "N" );

	private JCheckBoxPad cbTipoCadLocTrab = new JCheckBoxPad( "Local de trabalho", "S", "N" );

	private JCheckBoxPad cbTipoCadRefComl = new JCheckBoxPad( "Referências comerciais", "S", "N" );

	private JCheckBoxPad cbTipoCadRefBanc = new JCheckBoxPad( "Referências bancárias", "S", "N" );

	private JCheckBoxPad cbTipoCadRefPess = new JCheckBoxPad( "Referências pessoais", "S", "N" );

	private JCheckBoxPad cbTipoCadRefConj = new JCheckBoxPad( "Informações do cônjuge", "S", "N" );

	private JCheckBoxPad cbTipoCadRefVeic = new JCheckBoxPad( "Informações de veículos", "S", "N" );

	private JCheckBoxPad cbTipoCadRefImov = new JCheckBoxPad( "Informações de imóveis", "S", "N" );

	private JCheckBoxPad cbTipoCadRefTerra = new JCheckBoxPad( "Informações de terras", "S", "N" );

	private JCheckBoxPad cbTipoCadRefPesAutCp = new JCheckBoxPad( "Autorização de compra", "S", "N" );

	private JCheckBoxPad cbTipoCadRefAval = new JCheckBoxPad( "Avalista", "S", "N" );

	private JCheckBoxPad cbTipoCadRefSocio = new JCheckBoxPad( "Quadro de sócios", "S", "N" );

	private JLabelPad lbInfoFicha = new JLabelPad( " Informações complementares na ficha cadastral" );

	private JPanelPad pinLbInfoCaixa = new JPanelPad( 53, 15 );

	public FTipoCli() {

		super();
		setTitulo( "Cadastro de tipos de clientes" );
		setAtribos( 50, 50, 440, 330 );
		adicCampo( txtCod, 7, 20, 70, 20, "CodTipoCli", "Cód.tp.cli.", ListaCampos.DB_PK, true );
		adicCampo( txtDesc, 80, 20, 250, 20, "DescTipoCli", "Descrição do tipo de cliente", ListaCampos.DB_SI, true );
		adicCampo( txtSgTpCli, 333, 20, 71, 20, "SiglaTipoCli", "Sigla.tp.cli.", ListaCampos.DB_SI, false );
		adicDB( cbTipoCadFis, 10, 70, 200, 20, "FisTipoCli", "", true );
		adicDB( cbTipoCadJur, 220, 70, 170, 20, "JurTipoCli", "", true );
		adicDB( cbTipoCadCheq, 10, 90, 200, 20, "CheqTipoCli", "", true );
		adicDB( cbTipoCadFil, 220, 90, 170, 20, "FilTipoCli", "", true );
		adicDB( cbTipoCadLocTrab, 10, 110, 200, 20, "LocTrabTipoCli", "", true );
		adicDB( cbTipoCadRefComl, 220, 110, 170, 20, "RefComlTipoCli", "", true );
		adicDB( cbTipoCadRefBanc, 10, 130, 200, 20, "BancTipoCli", "", true );
		adicDB( cbTipoCadRefPess, 220, 130, 170, 20, "RefPesTipoCli", "", true );
		adicDB( cbTipoCadRefConj, 10, 150, 200, 20, "ConjTipoCli", "", true );
		adicDB( cbTipoCadRefVeic, 220, 150, 170, 20, "VeicTipoCli", "", true );
		adicDB( cbTipoCadRefImov, 10, 170, 200, 20, "ImovTipoCli", "", true );
		adicDB( cbTipoCadRefTerra, 220, 170, 170, 20, "TerraTipoCli", "", true );
		adicDB( cbTipoCadRefPesAutCp, 10, 190, 200, 20, "PesAutCpTipoCli", "", true );
		adicDB( cbTipoCadRefAval, 220, 190, 170, 20, "AvalTipoCli", "", true );
		adicDB( cbTipoCadRefSocio, 10, 210, 200, 20, "SocioTipoCli", "", true );

		pinLbInfoCaixa.adic( lbInfoFicha, 0, 0, 350, 15 );
		pinLbInfoCaixa.tiraBorda();

		adic( pinLbInfoCaixa, 10, 52, 350, 15 );
		adic( pinInfoFicha, 7, 60, 400, 180 );

		setListaCampos( true, "TIPOCLI", "VD" );
		btImp.addActionListener( this ); 
		btPrevimp.addActionListener( this );
		lcCampos.setQueryInsert( false );
		setImprimir( true );
	}

	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == btPrevimp ) {
		
			imprimir( true );
		}
		else if ( evt.getSource() == btImp ) {
		
			imprimir( false );
		}
		
		super.actionPerformed( evt );
	}

	private void imprimir( boolean bVisualizar ) {
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sSQL = new StringBuffer();
		ImprimeOS imp = null;
		DLRTipoCli dl = new DLRTipoCli();

		try {

			dl.setVisible( true );						
			if ( dl.OK == false ) {
				dl.dispose();
				return;
		}
			sSQL.append( "SELECT TP.CODTIPOCLI AS CODIGO,TP.DESCTIPOCLI AS DESCRICAO," );
			sSQL.append( "(SELECT COUNT(CLI.CODCLI) FROM VDCLIENTE CLI " );
			sSQL.append( "WHERE CLI.CODEMPTI=TP.CODEMP AND CLI.CODFILIALTI=TP.CODFILIAL AND CLI.CODTIPOCLI=TP.CODTIPOCLI) AS QTD " );
			sSQL.append( "FROM VDTIPOCLI TP " );
			sSQL.append( "WHERE TP.CODEMP=? AND TP.CODFILIAL=? " );
			sSQL.append( "ORDER BY "  + dl.getValor() );
			ps = con.prepareStatement( sSQL.toString() );
			rs = ps.executeQuery();
			
			if ( "D".equals( dl.getValor()) ) {
				imprimirTexto( bVisualizar, rs );
			}
			else if ( "G".equals( dl.getTipo() ) ) {
				imprimirGrafico( bVisualizar, rs );
			}
			
			ps.close();
			rs.close();
			
			if ( !con.getAutoCommit() ) {
				con.commit();
			
			}
			dl.dispose();
		} catch ( Exception err ) {
			Funcoes.mensagemErro( this, "Erro ao montar relatório de classificação de cliente!\n" + err.getMessage(), true, con, err );
			err.printStackTrace();
		
		} 
	}
			private void imprimirTexto( final boolean bVisualizar, final ResultSet rs ){
				
			ImprimeOS imp = new ImprimeOS( "", con );
			int linPag = imp.verifLinPag() - 1;
			imp.montaCab();
			imp.setTitulo( "Relatório de Classificação do tipo de cliente" );
				
				
			try {

				linPag = imp.verifLinPag() - 1;
				linPag = imp.verifLinPag() - 1;
				imp.limpaPags();
				imp.montaCab();
				imp.setTitulo( "Relatório de Vendas por Cliente" );
			
			
			while ( rs.next() ) {
				
				if ( imp.pRow() == 0 ) {
					
					imp.impCab( 80, false );
					
					imp.say( 0, imp.normal() );
					imp.say( 2, "Cód.tp.cli." );
					imp.say( 20, "Descrição" );
					imp.say( 70, "Qtd.cli." );
					imp.pulaLinha( 1, imp.normal() );
					imp.say( 0, Funcoes.replicate( "-", 79 ) );
				}
				
				imp.pulaLinha( 1, imp.normal() );
				imp.say( 2, rs.getString( "CodTipoCli" ) );
				imp.say( 20, rs.getString( "DescTipoCli" ) );
				imp.say( 70, Funcoes.alinhaDir( rs.getInt( 3 ), 8 ) );
					
				if ( imp.pRow() >= linPag ) {
					imp.incPags();
					imp.eject();
				}
			}
				
			imp.pulaLinha( 1, imp.normal() );
			imp.say( 0, Funcoes.replicate( "=", 79 ) );
			imp.pulaLinha( 1, imp.normal() );
			imp.say( 0, "|" );
			imp.say( 50, "Total de clientes:" );
			imp.say( 79, "|" );
			imp.pulaLinha( 1, imp.normal() );
			imp.say( 0, Funcoes.replicate( "=", 79 ) );
			
			imp.eject();
			imp.fechaGravacao();

			if ( bVisualizar ) {
				imp.preview( this );
			}
			else {
				imp.print();
			}
			
		} catch ( SQLException err ) {
			err.printStackTrace();
			Funcoes.mensagemErro( this, "Erro consulta tabela de tipos de cliente!\n" + err.getMessage(), true, con, err );
		
		} 
	}
		public void imprimirGrafico( final boolean bVisualizar, final ResultSet rs ) {

				FPrinterJob dlGr = new FPrinterJob( "relatorios/TipoCli.jasper", "Vendas por Cliente",null,  rs, null, this );

				if ( bVisualizar ) {
					dlGr.setVisible( true );
				}
				else {
					try {
						JasperPrintManager.printReport( dlGr.getRelatorio(), true );
					} catch ( Exception err ) {
						Funcoes.mensagemErro( this, "Erro na impressão de relatório de vendas por cliente!" + err.getMessage(), true, con, err );
					}
				}
			}
			
			
		
}

