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

import org.freedom.infra.model.jdbc.DbConnection;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.telas.FTabDados;

public class FPreferegms extends FTabDados {

	private static final long serialVersionUID = 1L;	
	
	/****************
	 * Lista Campos *
	 ****************/
	
	private ListaCampos lcTipoRecMerc = new ListaCampos( this, "TR" );
	
	
	/****************
	 *    Fields    *
	 ****************/
	
	private JTextFieldPad txtCodTipoRecMerc = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	private JTextFieldFK txtDescTipoRecMerc = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );
		
	/****************
	 *   Paineis    *
	 ****************/
	

	private JPanelPad pinGeral = new JPanelPad( 330, 200 );
	

	public FPreferegms() {

		super();
			
		setTitulo( "Preferências GMS" );
		setAtribos( 30, 40, 420, 200 );
		lcCampos.setMensInserir( false );

		montaListaCampos();		
		montaTela();		
	}	
	
	
	private void montaListaCampos(){
		
		/****************************
		 * Tipo Rec. Mercadoria     *
		 ****************************/
		
		lcTipoRecMerc.add( new GuardaCampo( txtCodTipoRecMerc, "CodTipoRecMerc", "Cód.tp.Rec.", ListaCampos.DB_PK, false ) );
		lcTipoRecMerc.add( new GuardaCampo( txtDescTipoRecMerc, "DescTipoRecMerc", "Descrição do tipo de recebimento", ListaCampos.DB_SI, false ) );
		lcTipoRecMerc.montaSql( false, "TIPORECMERC", "EQ" );
		lcTipoRecMerc.setQueryCommit( false );
		lcTipoRecMerc.setReadOnly( true );
		txtCodTipoRecMerc.setTabelaExterna( lcTipoRecMerc );
		
		
	}
	
	private void montaTela(){
		
		setPainel( pinGeral );
		adicTab( "Geral", pinGeral );
		
		adicCampo( txtCodTipoRecMerc, 7, 25, 120, 20, "CodTipoRecMerc", "Cód.rec.mercadoria", ListaCampos.DB_FK, txtDescTipoRecMerc, false );
		adicDescFK( txtDescTipoRecMerc, 130, 25, 250, 20, "DescTipoRecMerc", "Descrição do tipo de recebimento" );
		txtCodTipoRecMerc.setFK( true );
		txtCodTipoRecMerc.setNomeCampo( "CodTipoRecMerc" );
		
		
		setListaCampos( false, "PREFERE8", "SG" );
		nav.setAtivo( 0, false );
		lcCampos.setPodeExc( false );
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		
		lcTipoRecMerc.setConexao( cn );
		
		lcCampos.carregaDados();
	}
}
