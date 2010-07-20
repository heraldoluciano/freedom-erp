/**
 * @version 02/03/2009 <BR>
 * @author Setpoint Informática Ltda.<BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.std <BR>
 *         Classe:
 * @(#)FPrefereGeral.java <BR>
 * 
 *                        Este programa é licenciado de acordo com a LPG-PC (Licenï¿½a Pï¿½blica Geral para Programas de Computador), <BR>
 *                        versï¿½o 2.1.0 ou qualquer versï¿½o posterior. <BR>
 *                        A LPG-PC deve acompanhar todas PUBLICAï¿½ï¿½ES, DISTRIBUIï¿½ï¿½ES e REPRODUÇÕES deste Programa. <BR>
 *                        Caso uma cï¿½pia da LPG-PC nï¿½o esteja disponï¿½vel junto com este Programa, vocï¿½ pode contatar <BR>
 *                        o LICENCIADOR ou entï¿½o pegar uma cï¿½pia em: <BR>
 *                        Licenï¿½a: http://www.lpg.adv.br/licencas/lpgpc.rtf <BR>
 *                        Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa ï¿½ preciso estar <BR>
 *                        de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 *                        Tela de cadastro das preferências do sistema. Esse cadastro ï¿½ utilizado para parametrizar o sistema de acordo com as necessidades especï¿½ficas da empresa.
 * 
 */

package org.freedom.modulos.gms.view.frame.crud.tabbed;

import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.business.exceptions.ExceptionCarregaDados;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JPanelPad;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.FTabDados;
import org.freedom.modulos.gms.business.object.TipoMov;
import org.freedom.modulos.gms.business.object.TipoRecMerc;
import org.freedom.modulos.gms.view.frame.crud.detail.FTipoRecMerc;

public class FPrefereGMS extends FTabDados {

	private static final long serialVersionUID = 1L;

	/****************
	 * Lista Campos *
	 ****************/

	private ListaCampos lcTipoRecMercRP = new ListaCampos( this, "TR" );

	private ListaCampos lcTipoRecMercCM = new ListaCampos( this, "CM" );

	private ListaCampos lcTipoMovCP = new ListaCampos( this, "TC" );

	private ListaCampos lcTipoRecMercOS = new ListaCampos( this, "TO" );

	/****************
	 * Fields *
	 ****************/

	private JTextFieldPad txtCodTipoRecMercRP = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescTipoRecMercRP = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtCodTipoRecMercCM = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescTipoRecMercCM = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtCodTipoMovTC = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescTipoMovTC = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtCodTipoRecMercOS = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescTipoRecMercOS = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	/****************
	 * Paineis *
	 ****************/

	private JPanelPad pinGeral = new JPanelPad( 330, 200 );

	public FPrefereGMS() {

		super();

		setTitulo( "Preferências GMS" );
		setAtribos( 30, 40, 440, 280 );
		lcCampos.setMensInserir( false );

		montaListaCampos();
		montaTela();
	}

	private void montaListaCampos() {

		/***************************************
		 * Tipo Rec. Mercadoria Com pesagem *
		 **************************************/

		lcTipoRecMercRP.add( new GuardaCampo( txtCodTipoRecMercRP, "CodTipoRecMerc", "Cód.Tipo.Rec.", ListaCampos.DB_PK, false ) );
		lcTipoRecMercRP.add( new GuardaCampo( txtDescTipoRecMercRP, "DescTipoRecMerc", "Tipo de recebimento para pesagem", ListaCampos.DB_SI, false ) );
		lcTipoRecMercRP.montaSql( false, "TIPORECMERC", "EQ" );
		lcTipoRecMercRP.setWhereAdic( "TIPORECMERC='" + TipoRecMerc.TIPO_RECEBIMENTO_PESAGEM.getValue() + "'" );
		lcTipoRecMercRP.setQueryCommit( false );
		lcTipoRecMercRP.setReadOnly( true );
		txtCodTipoRecMercRP.setTabelaExterna( lcTipoRecMercRP, FTipoRecMerc.class.getCanonicalName() );

		/***************************************
		 * Tipo Rec. Mercadoria Coleta *
		 **************************************/

		lcTipoRecMercCM.add( new GuardaCampo( txtCodTipoRecMercCM, "CodTipoRecMerc", "Cód.Tipo.Rec.", ListaCampos.DB_PK, false ) );
		lcTipoRecMercCM.add( new GuardaCampo( txtDescTipoRecMercCM, "DescTipoRecMerc", "Tipo de recebimento com coleta", ListaCampos.DB_SI, false ) );
		lcTipoRecMercCM.montaSql( false, "TIPORECMERC", "EQ" );
		lcTipoRecMercCM.setWhereAdic( "TIPORECMERC='" + TipoRecMerc.TIPO_COLETA_DE_MATERIAIS.getValue() + "'" );
		lcTipoRecMercCM.setQueryCommit( false );
		lcTipoRecMercCM.setReadOnly( true );
		txtCodTipoRecMercCM.setTabelaExterna( lcTipoRecMercCM, FTipoRecMerc.class.getCanonicalName() );

		/***************************************
		 * Tipo de movimento de compra *
		 **************************************/

		lcTipoMovCP.add( new GuardaCampo( txtCodTipoMovTC, "CodTipoMov", "Cód.Tipo.Rec.", ListaCampos.DB_PK, false ) );
		lcTipoMovCP.add( new GuardaCampo( txtDescTipoMovTC, "DescTipoMov", "Tipo de movimento para compra", ListaCampos.DB_SI, false ) );
		lcTipoMovCP.montaSql( false, "TIPOMOV", "EQ" );
		lcTipoMovCP.setWhereAdic( "ESTIPOMOV='" + TipoMov.ENTRADA.getValue() + "'" );
		lcTipoMovCP.setQueryCommit( false );
		lcTipoMovCP.setReadOnly( true );
		txtCodTipoMovTC.setTabelaExterna( lcTipoMovCP, FTipoMov.class.getCanonicalName() );

		/***************************************
		 * Tipo de recepcao de mercadoria para ordem de sevico *
		 **************************************/

		lcTipoRecMercOS.add( new GuardaCampo( txtCodTipoRecMercOS, "CodTipoRecMerc", "Cód.Tipo.Rec.", ListaCampos.DB_PK, false ) );
		lcTipoRecMercOS.add( new GuardaCampo( txtDescTipoRecMercOS, "DescTipoRecMerc", "Tipo de recepção e mercadorias para Ordem de Serviço", ListaCampos.DB_SI, false ) );
		lcTipoRecMercOS.montaSql( false, "TIPORECMERC", "EQ" );
		lcTipoRecMercCM.setWhereAdic( "TIPORECMERC='" + TipoRecMerc.TIPO_ENTRADA_CONCERTO.getValue() + "'" );
		lcTipoRecMercOS.setQueryCommit( false );
		lcTipoRecMercOS.setReadOnly( true );
		txtCodTipoRecMercOS.setTabelaExterna( lcTipoRecMercOS, FTipoRecMerc.class.getCanonicalName() );

	}

	private void montaTela() {

		setPainel( pinGeral );
		adicTab( "Geral", pinGeral );

		adicCampo( txtCodTipoRecMercRP, 7, 20, 70, 20, "CodTipoRecMerc", "Cód.Tp.Rec.", ListaCampos.DB_FK, txtDescTipoRecMercRP, false );
		adicDescFK( txtDescTipoRecMercRP, 80, 20, 330, 20, "DescTipoRecMerc", "Tipo de recebimento padrão para pesagem" );
		txtCodTipoRecMercRP.setFK( true );
		txtCodTipoRecMercRP.setNomeCampo( "CodTipoRecMerc" );

		adicCampo( txtCodTipoRecMercCM, 7, 60, 70, 20, "CodTipoRecMercCM", "Cód.Tp.Rec.", ListaCampos.DB_FK, txtDescTipoRecMercCM, false );
		adicDescFK( txtDescTipoRecMercCM, 80, 60, 330, 20, "DescTipoRecMercCM", "Tipo de recebimento padrão para coleta" );
		txtCodTipoRecMercCM.setFK( true );
		txtCodTipoRecMercCM.setNomeCampo( "CodTipoRecMerc" );

		adicCampo( txtCodTipoRecMercOS, 7, 100, 70, 20, "CodTipoRecMercOS", "Cód.Tp.Rec.", ListaCampos.DB_FK, txtDescTipoRecMercOS, false );
		adicDescFK( txtDescTipoRecMercOS, 80, 100, 330, 20, "DescTipoRecMerc", "Tipo de recebimento padrão para Ordem de Serviço" );
		txtCodTipoRecMercOS.setFK( true );
		txtCodTipoRecMercOS.setNomeCampo( "CodTipoRecMerc" );

		adicCampo( txtCodTipoMovTC, 7, 140, 70, 20, "CodTipoMovTC", "Cód.Tp.Mov.", ListaCampos.DB_FK, txtDescTipoMovTC, false );
		adicDescFK( txtDescTipoMovTC, 80, 140, 330, 20, "DescTipoMov", "Tipo de movimento padrão para compra" );
		txtCodTipoMovTC.setFK( true );
		txtCodTipoMovTC.setNomeCampo( "CodTipoMov" );

		setListaCampos( false, "PREFERE8", "SG" );
		nav.setAtivo( 0, false );
		lcCampos.setPodeExc( false );
	}

	public void setConexao( DbConnection cn ) { // throws ExceptionSetConexao {

		super.setConexao( cn );

		lcTipoRecMercRP.setConexao( cn );
		lcTipoRecMercCM.setConexao( cn );
		lcTipoRecMercOS.setConexao( cn );
		lcTipoMovCP.setConexao( cn );

		try {
			lcCampos.carregaDados();
		}

		catch ( Exception e ) {
			new ExceptionCarregaDados( "Erro ao carregar dados do lista campos " + lcCampos.getName() );
		}

	}
}
