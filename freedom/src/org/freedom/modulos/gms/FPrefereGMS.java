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

package org.freedom.modulos.gms;

import org.freedom.business.object.TipoRecMerc;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.JPanelPad;
import org.freedom.library.swing.JTextFieldFK;
import org.freedom.library.swing.JTextFieldPad;
import org.freedom.library.swing.frame.FTabDados;


public class FPrefereGMS extends FTabDados {

	private static final long serialVersionUID = 1L;	
	
	/****************
	 * Lista Campos *
	 ****************/
	
	private ListaCampos lcTipoRecMercRP = new ListaCampos( this, "TR" );

	private ListaCampos lcTipoRecMercCM = new ListaCampos( this, "CM" );
	
	/****************
	 *    Fields    *
	 ****************/
	
	private JTextFieldPad txtCodTipoRecMercRP = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescTipoRecMercRP = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtCodTipoRecMercCM = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldFK txtDescTipoRecMercCM = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	
	/****************
	 *   Paineis    *
	 ****************/
	

	private JPanelPad pinGeral = new JPanelPad( 330, 200 );
	

	public FPrefereGMS() {

		super();
			
		setTitulo( "Preferências GMS" );
		setAtribos( 30, 40, 440, 200 );
		lcCampos.setMensInserir( false );

		montaListaCampos();		
		montaTela();		
	}	
	
	
	private void montaListaCampos(){
		
		/***************************************
		 * Tipo Rec. Mercadoria Com pesagem    *
		 **************************************/
		
		lcTipoRecMercRP.add( new GuardaCampo( txtCodTipoRecMercRP, "CodTipoRecMerc", "Cód.Tipo.Rec.", ListaCampos.DB_PK, false ) );
		lcTipoRecMercRP.add( new GuardaCampo( txtDescTipoRecMercRP, "DescTipoRecMerc", "Descrição do tipo de recebimento para pesagem", ListaCampos.DB_SI, false ) );
		lcTipoRecMercRP.montaSql( false, "TIPORECMERC", "EQ" );
		lcTipoRecMercRP.setWhereAdic( "TIPORECMERC='" + TipoRecMerc.TIPO_RECEBIMENTO_PESAGEM.getValue() + "'" );
		lcTipoRecMercRP.setQueryCommit( false );
		lcTipoRecMercRP.setReadOnly( true );
		txtCodTipoRecMercRP.setTabelaExterna( lcTipoRecMercRP );

		/***************************************
		 * Tipo Rec. Mercadoria Coleta    *
		 **************************************/
		
		lcTipoRecMercCM.add( new GuardaCampo( txtCodTipoRecMercCM, "CodTipoRecMerc", "Cód.Tipo.Rec.", ListaCampos.DB_PK, false ) );
		lcTipoRecMercCM.add( new GuardaCampo( txtDescTipoRecMercCM, "DescTipoRecMerc", "Descrição do tipo de recebimento com coleta", ListaCampos.DB_SI, false ) );
		lcTipoRecMercCM.montaSql( false, "TIPORECMERC", "EQ" );
		lcTipoRecMercCM.setWhereAdic( "TIPORECMERC='" + TipoRecMerc.TIPO_COLETA_DE_MATERIAIS.getValue() + "'" );
		lcTipoRecMercCM.setQueryCommit( false );
		lcTipoRecMercCM.setReadOnly( true );
		txtCodTipoRecMercCM.setTabelaExterna( lcTipoRecMercCM );

		
	}
	
	private void montaTela(){
		
		setPainel( pinGeral );
		adicTab( "Geral", pinGeral );
		
		adicCampo( txtCodTipoRecMercRP, 7, 20, 70, 20, "CodTipoRecMerc", "Cód.Tp.Rec.", ListaCampos.DB_FK, txtDescTipoRecMercRP, false );
		adicDescFK( txtDescTipoRecMercRP, 80, 20, 330, 20, "DescTipoRecMerc", "Descrição do tipo de recebimento padrão para pesagem" );		
		txtCodTipoRecMercRP.setFK( true );
		txtCodTipoRecMercRP.setNomeCampo( "CodTipoRecMerc" );

		adicCampo( txtCodTipoRecMercCM, 7, 60, 70, 20, "CodTipoRecMercCM", "Cód.Tp.Rec.", ListaCampos.DB_FK, txtDescTipoRecMercCM, false );
		adicDescFK( txtDescTipoRecMercCM, 80, 60, 330, 20, "DescTipoRecMercCM", "Descrição do tipo de recebimento padrão para coleta" );
		txtCodTipoRecMercCM.setFK( true );
		txtCodTipoRecMercCM.setNomeCampo( "CodTipoRecMerc" );
		
		setListaCampos( false, "PREFERE8", "SG" );
		nav.setAtivo( 0, false );
		lcCampos.setPodeExc( false );
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		
		lcTipoRecMercRP.setConexao( cn );
		lcTipoRecMercCM.setConexao( cn );
		
		lcCampos.carregaDados();
	}
}
