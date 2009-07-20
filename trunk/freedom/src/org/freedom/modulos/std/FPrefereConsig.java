/**
 * @version 02/03/2009 <BR>
 * @author Setpoint Informática Ltda.<BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)FPrefereGeral.java <BR>
 * 
 * Este programa é licenciado de acordo com a LPG-PC (Licenï¿½a Pï¿½blica Geral para Programas de Computador), <BR>
 * versï¿½o 2.1.0 ou qualquer versï¿½o posterior. <BR>
 * A LPG-PC deve acompanhar todas PUBLICAï¿½ï¿½ES, DISTRIBUIï¿½ï¿½ES e REPRODUÇÕES deste Programa. <BR>
 * Caso uma cï¿½pia da LPG-PC nï¿½o esteja disponï¿½vel junto com este Programa, vocï¿½ pode contatar <BR>
 * o LICENCIADOR ou entï¿½o pegar uma cï¿½pia em: <BR>
 * Licenï¿½a: http://www.lpg.adv.br/licencas/lpgpc.rtf <BR>
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa ï¿½ preciso estar <BR>
 * de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 * Tela de cadastro das preferências do sistema. Esse cadastro ï¿½ utilizado para parametrizar o sistema de acordo com as necessidades especï¿½ficas da empresa.
 * 
 */

package org.freedom.modulos.std;

import org.freedom.infra.model.jdbc.DbConnection;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.telas.FTabDados;

public class FPrefereConsig extends FTabDados {

	private static final long serialVersionUID = 1L;	
	
	/****************
	 * Lista Campos *
	 ****************/
	
	private ListaCampos lcTipoMov = new ListaCampos( this, "CO" );
	
	private ListaCampos lcTipoMovTv = new ListaCampos( this, "TV" );
	
	private ListaCampos lcTipoMovTP = new ListaCampos( this, "TP" );
	
	/****************
	 *    Fields    *
	 ****************/
	
	private JTextFieldPad txtCodTipoMov = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldFK txtDescTipoMov = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );
	
	private JTextFieldPad txtCodTipoMovTv = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldFK txtDescTipoMovTv = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );
	
	private JTextFieldPad txtCodTipoMovTp = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldFK txtDescTipoMovTp = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );
	
	/****************
	 *   Paineis    *
	 ****************/
	
	private JPanelPad pinGeral = new JPanelPad( 330, 200 );

	public FPrefereConsig() {

		super();
			
		setTitulo( "Preferências Gerais" );
		setAtribos( 30, 40, 400, 250 );
		lcCampos.setMensInserir( false );
		
		montaListaCampos();
		montaTela();
				
	}	
	
	
	private void montaListaCampos(){
		
		/****************************
		 * Tipo de mov. consignação *
		 ***************************/
		
		lcTipoMov.add( new GuardaCampo( txtCodTipoMov, "CodTipoMov", "Cód.tp.mov.", ListaCampos.DB_PK, false ) );
		lcTipoMov.add( new GuardaCampo( txtDescTipoMov, "DescTipoMov", "Descrição do tipo de movimento", ListaCampos.DB_SI, false ) );
		lcTipoMov.montaSql( false, "TIPOMOV", "EQ" );
		lcTipoMov.setQueryCommit( false );
		lcTipoMov.setReadOnly( true );
		txtCodTipoMov.setTabelaExterna( lcTipoMov );
		
		/****************************
		 * Tipo de mov. ped. venda  *
		 ***************************/
		
		lcTipoMovTv.add( new GuardaCampo( txtCodTipoMovTv, "CodTipoMov", "Cód.tp.mov.", ListaCampos.DB_PK, false ) );
		lcTipoMovTv.add( new GuardaCampo( txtDescTipoMovTv, "DescTipoMov", "Descrição do tipo de movimento", ListaCampos.DB_SI, false ) );
		lcTipoMovTv.montaSql( false, "TIPOMOV", "EQ" );
		lcTipoMovTv.setQueryCommit( false );
		lcTipoMovTv.setReadOnly( true );
		txtCodTipoMovTv.setTabelaExterna( lcTipoMovTv );		
		
		/****************************
		 *   Tipo de mov. Venda     *
		 ***************************/
		
		lcTipoMovTP.add( new GuardaCampo( txtCodTipoMovTp, "CodTipoMov", "Cód.tp.mov.", ListaCampos.DB_PK, false ) );
		lcTipoMovTP.add( new GuardaCampo( txtDescTipoMovTp, "DescTipoMov", "Descrição do tipo de movimento", ListaCampos.DB_SI, false ) );
		lcTipoMovTP.montaSql( false, "TIPOMOV", "EQ" );
		lcTipoMovTP.setQueryCommit( false );
		lcTipoMovTP.setReadOnly( true );
		txtCodTipoMovTp.setTabelaExterna( lcTipoMovTP );
	}
	
	private void montaTela(){
		
		setPainel( pinGeral );
		adicTab( "Geral", pinGeral );
		
		adicCampo( txtCodTipoMov, 7, 25, 75, 20, "CodTipoMovCo", "Cód.tp.mov.", ListaCampos.DB_FK, txtDescTipoMov, false );
		adicDescFK( txtDescTipoMov, 85, 25, 250, 20, "DescTipoMov", "Tipo de movimento para venda consignada" );
		adicCampo( txtCodTipoMovTv, 7, 70, 75, 20, "CodTipoMovTv", "Cód.tp.mov.", ListaCampos.DB_FK, txtDescTipoMovTv, false );
		adicDescFK( txtDescTipoMovTv, 85, 70, 250, 20, "DescTipoMov", "Tipo de movimento para pedido de venda" );
		adicCampo( txtCodTipoMovTp, 7, 110, 75, 20, "CodTipoMovTp", "Cód.tp.mov.", ListaCampos.DB_FK, txtDescTipoMovTp, false );
		adicDescFK( txtDescTipoMovTp, 85, 110, 250, 20, "DescTipoMov", "Tipo de movimento para venda" );
		
		setListaCampos( false, "PREFERE7", "SG" );
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		lcTipoMov.setConexao( cn );
		lcTipoMovTv.setConexao( cn );
		lcTipoMovTP.setConexao( cn );
		lcCampos.carregaDados();
	}
}
