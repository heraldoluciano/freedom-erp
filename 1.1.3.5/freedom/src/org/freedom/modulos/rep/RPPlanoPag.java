/**
 * @version 02/2007 <BR>
 * @author Setpoint Informática Ltda.<BR>
 * @author Alex Rodrigues<BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.rep <BR>
 * Classe:
 * @(#)RPPlanoPag.java <BR>
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
 * Tela de cadastro de planos de pagamento.
 * 
 */

package org.freedom.modulos.rep;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.acao.DeleteEvent;
import org.freedom.acao.DeleteListener;
import org.freedom.acao.InsertEvent;
import org.freedom.acao.InsertListener;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.telas.FDetalhe;

public class RPPlanoPag extends FDetalhe implements CarregaListener, InsertListener, DeleteListener {

	private static final long serialVersionUID = 1L;
	
	private final JPanelPad panelPlanoPag = new JPanelPad();
	
	private final JPanelPad panelParcelas = new JPanelPad();

	private final JTextFieldPad txtCodPlanoPag = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );

	private final JTextFieldPad txtDescPlanoPag = new JTextFieldPad( JTextFieldPad.TP_STRING, 40, 0 );

	private final JTextFieldPad txtNumParc = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );

	private final JTextFieldPad txtNumItemPag = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 2, 0 );

	private final JTextFieldPad txtDiasItemPag = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );

	private final JTextFieldPad txtPercItemPag = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 6, 2 );

	private final JTextFieldPad txtJurosParcPag = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 6, 2 );

	private final JTextFieldPad txtDescItemPag = new JTextFieldPad( JTextFieldPad.TP_STRING, 30, 0 );
	
	
	public RPPlanoPag() {

		super( false );
		setTitulo( "Cadastro de Planos de Pagamento" );
		setAtribos( 50, 50, 405, 350 );
		
		montaMaster();	

		setListaCampos( true, "PLANOPAG", "RP" );
		lcCampos.setQueryInsert( true );

		setListaCampos( lcDet );
		setNavegador( navRod );

		montaDetale();

		setListaCampos( true, "PARCPLANOPAG", "RP" );
		lcDet.setQueryInsert( false );

		navRod.setAtivo( 4, false );
		navRod.setAtivo( 5, false );
		
		montaTab();
		
		tab.setTamColuna( 40, 0 );
		tab.setTamColuna( 40, 1 );
		tab.setTamColuna( 60, 2 );
		tab.setTamColuna( 60, 3 );
		tab.setTamColuna( 189, 4 );
		
		lcCampos.addCarregaListener( this );
		lcCampos.addInsertListener( this );
		lcCampos.addDeleteListener( this );
	}
	
	private void montaMaster() {
		
		/**********************
		 * PLANO DE PAGAMENTO * 
		 **********************/

		setAltCab( 100 );
		setPainel( panelPlanoPag, pnCliCab );
		
		adicCampo( txtCodPlanoPag, 7, 20, 70, 20, "CodPlanoPag", "Cód.p.pag.", ListaCampos.DB_PK, null, true );
		adicCampo( txtDescPlanoPag, 80, 20, 217, 20, "DescPlanoPag", "Descrição do plano de pagamento", ListaCampos.DB_SI, null, true );
		adicCampo( txtNumParc, 300, 20, 67, 20, "ParcPlanoPag", "N° Parcs.", ListaCampos.DB_SI, null, true );
		
	}
	
	private void montaDetale() {
		
		/**********************
		 *    PARCELAMENTO    * 
		 **********************/	

		setAltDet( 100 );
		setPainel( panelParcelas, pnDet );
		
		adicCampo( txtNumItemPag, 7, 20, 60, 20, "NroParcPag", "Item", ListaCampos.DB_PK, true );
		adicCampo( txtDiasItemPag, 70, 20, 100, 20, "DiasPag", "Dias", ListaCampos.DB_SI, true );
		adicCampo( txtPercItemPag, 173, 20, 100, 20, "PercPag", "Percento", ListaCampos.DB_SI, true );
		adicCampo( txtJurosParcPag, 276, 20, 100, 20, "JurosParcPag", "Juros", ListaCampos.DB_SI, false );
		adicCampo( txtDescItemPag, 7, 60, 369, 20, "DescParcPag", "Descrição", ListaCampos.DB_SI, false );
		
	}

	public void beforeCarrega( CarregaEvent e ) { }

	public void afterCarrega( CarregaEvent e ) {

		if ( e.getListaCampos() == lcCampos && e.ok ) {
			txtNumParc.setEditable( false );
		}
	}

	public void afterInsert( InsertEvent e ) {

		txtNumParc.setEditable( true );
	}

	public void beforeInsert( InsertEvent e ) { }

	public void afterDelete( DeleteEvent e ) {

		lcDet.getTab().limpa();
		lcDet.limpaCampos( true );
	}

	public void beforeDelete( DeleteEvent e ) { }

}
