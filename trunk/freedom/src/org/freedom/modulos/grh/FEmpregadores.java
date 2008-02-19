/**
 * @version 18/02/2008 <BR>
 * @author Setpoint Informática Ltda./Alex Rodrigues <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.grh <BR>
 * Classe:
 * @(#)FEmpregado.java <BR>
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
 * Tela de cadastro de empregadores.
 * 
 */

package org.freedom.modulos.grh;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JScrollPane;

import org.freedom.bmps.Icone;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JTextAreaPad;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FTabDados;

public class FEmpregadores extends FTabDados {

	private static final long serialVersionUID = 1L;
	
	private final JPanelPad panelGeral = new JPanelPad();
	
	private final JPanelPad panelObservacao = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 1, 1 ) );

	private final JTextFieldPad txtCodEmpr = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private final JTextFieldPad txtNomeEmpr = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );
	
	private final JCheckBoxPad cbAtivo = new JCheckBoxPad( "Ativo", "S", "N" );
	
	private final JTextFieldPad txtCNPJEmpr = new JTextFieldPad( JTextFieldPad.TP_STRING, 14, 0 );
	
	private final JTextFieldPad txtINSCEmpr = new JTextFieldPad( JTextFieldPad.TP_STRING, 15, 0 );
	
	private final JTextFieldPad txtEndEmpr = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );
	
	private final JTextFieldPad txtNumEmpr = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private final JTextFieldPad txtBairroEmpr = new JTextFieldPad( JTextFieldPad.TP_STRING, 30, 0 );
	
	private final JTextFieldPad txtCidEmpr = new JTextFieldPad( JTextFieldPad.TP_STRING, 30, 0 );
	
	private final JTextFieldPad txtUfEmpr = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );
	
	private final JTextFieldPad txtCepEmpr = new JTextFieldPad( JTextFieldPad.TP_STRING, 8, 0 );	
	
	private final JTextFieldPad txtDDDEmpr = new JTextFieldPad( JTextFieldPad.TP_STRING, 4, 0 );
	
	private final JTextFieldPad txtFoneEmpr = new JTextFieldPad( JTextFieldPad.TP_STRING, 12, 0 );
	
	private final JTextFieldPad txtRamalEmpr = new JTextFieldPad( JTextFieldPad.TP_STRING, 6, 0 );
	
	private final JTextFieldPad txtDDDFaxEmpr = new JTextFieldPad( JTextFieldPad.TP_STRING, 4, 0 );
	
	private final JTextFieldPad txtFaxEmpr = new JTextFieldPad( JTextFieldPad.TP_STRING, 12, 0 );
	
	private final JTextFieldPad txtDDDCelEmpr = new JTextFieldPad( JTextFieldPad.TP_STRING, 4, 0 );
	
	private final JTextFieldPad txtCelEmpr = new JTextFieldPad( JTextFieldPad.TP_STRING, 12, 0 );

	private final JTextFieldPad txtContatoEmpr = new JTextFieldPad( JTextFieldPad.TP_STRING, 40, 0 );
	
	private final JTextFieldPad txtEmailEmpr = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );
	
	private final JTextFieldPad txtSiteEmpr = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );	
	
	private final JTextAreaPad txaObsEmpr = new JTextAreaPad( 1000 );
	
	private JButton btFirefox = new JButton( Icone.novo( "firefox.gif" ) );
	

	public FEmpregadores() {

		super( false );
		setTitulo( "Cadastro de Empregados" );
		setAtribos( 50, 50, 430, 430 );

		montaTela();
		
		btFirefox.addActionListener( this );

		setImprimir( false );
	}
	
	private void montaTela() {		

		txtCNPJEmpr.setMascara( JTextFieldPad.MC_CNPJ );
		txtCepEmpr.setMascara( JTextFieldPad.MC_CEP );
		txtFoneEmpr.setMascara( JTextFieldPad.MC_FONE );
		txtFaxEmpr.setMascara( JTextFieldPad.MC_FONE );
		txtCelEmpr.setMascara( JTextFieldPad.MC_FONE );
		
		adicTab( "Geral", panelGeral ); 
		setPainel( panelGeral );

		adicCampo( txtCodEmpr, 7, 20, 100, 20, "CodEmpr", "Cód.empregador", ListaCampos.DB_PK, true );		
		adicCampo( txtNomeEmpr, 110, 20, 220, 20, "NomeEmpr", "Nome do empregador", ListaCampos.DB_SI, true );
		adicDB( cbAtivo, 343, 20, 57, 20, "AtivoFor", "", true );				
		adicCampo( txtCNPJEmpr, 7, 60, 195, 20, "CNPJEmpr", "CNPJ", ListaCampos.DB_SI, false );		
		adicCampo( txtINSCEmpr, 205, 60, 195, 20, "INSCEmpr", "INSC", ListaCampos.DB_SI, false );		
		adicCampo( txtEndEmpr, 7, 100, 323, 20, "EndEmpr", "Endereço", ListaCampos.DB_SI, false );		
		adicCampo( txtNumEmpr, 333, 100, 67, 20, "NumEmpr", "Número", ListaCampos.DB_SI, false );		
		adicCampo( txtBairroEmpr, 7, 140, 145, 20, "BairroEmpr", "Bairro", ListaCampos.DB_SI, false );		
		adicCampo( txtCidEmpr, 155, 140, 145, 20, "CidEmpr", "Cidade", ListaCampos.DB_SI, false );		
		adicCampo( txtUfEmpr, 303, 140, 27, 20, "UfEmpr", "UF", ListaCampos.DB_SI, false );		
		adicCampo( txtCepEmpr, 333, 140, 67, 20, "CepEmpr", "CEP", ListaCampos.DB_SI, false );
		adicCampo( txtDDDEmpr, 7, 180, 40, 20, "DDDEmpr", "DDD", ListaCampos.DB_SI, false );		
		adicCampo( txtFoneEmpr, 50, 180, 120, 20, "FoneEmpr", "Fone", ListaCampos.DB_SI, false );		
		adicCampo( txtRamalEmpr, 173, 180, 60, 20, "RamalEmpr", "Ramal", ListaCampos.DB_SI, false );		
		adicCampo( txtDDDFaxEmpr, 236, 180, 40, 20, "DDDFaxEmpr", "DDD", ListaCampos.DB_SI, false );		
		adicCampo( txtFaxEmpr, 279, 180, 121, 20, "DDDFaxEmpr", "Fax", ListaCampos.DB_SI, false );		
		adicCampo( txtDDDCelEmpr, 7, 220, 40, 20, "DDDCelEmpr", "DDD", ListaCampos.DB_SI, false );		
		adicCampo( txtCelEmpr, 50, 220, 120, 20, "CelEmpr", "Celular", ListaCampos.DB_SI, false );
		adicCampo( txtContatoEmpr, 173, 220, 227, 20, "ContatoEmpr", "Contato", ListaCampos.DB_SI, false );			
		adicCampo( txtEmailEmpr, 7, 260, 393, 20, "EmailEmpr", "e-mail", ListaCampos.DB_SI, false );
		adicCampo( txtSiteEmpr, 7, 300, 363, 20, "SiteEmpr", "site", ListaCampos.DB_SI, false );
		adic( btFirefox, 380, 300, 20, 20 );
		
		adicTab( "Observações", panelObservacao ); 
		setPainel( panelObservacao );

		adicDBLiv( txaObsEmpr, "ObsEmpr", "Observações", false );
		panelObservacao.add( new JScrollPane( txaObsEmpr ) );

		setListaCampos( true, "EMPREGADOR", "RH" );
		lcCampos.setQueryInsert( false );
	}

	@ Override
	public void actionPerformed( ActionEvent e ) {

		super.actionPerformed( e );
		
		if ( e.getSource() == btFirefox ) {
			if( txtSiteEmpr.getVlrString().trim().length() > 0  ) {
				Funcoes.executeURL( Aplicativo.strOS, Aplicativo.strBrowser, txtSiteEmpr.getVlrString().trim() );
	    	}
	    	else {
	    		Funcoes.mensagemInforma( this, "Informe o Site do Cliente! " );
	    	}
		}
	}
}
