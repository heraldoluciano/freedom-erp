/**
 * @version 20/05/2008 <BR>
 * @author Setpoint Informática Ltda./Felipe Daniel Elias <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FRegraFiscal.java <BR>
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
 * Comentários sobre a classe...
 * 
 */

package org.freedom.modulos.std;
import java.sql.Connection;

import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.JPanelPad;
import org.freedom.telas.FDetalhe;

public class FRestrCli extends FDetalhe {
	
	private static final long serialVersionUID = 1L;

	private JPanelPad pinCab = new JPanelPad();
	
	private JPanelPad pinDet = new JPanelPad();
	
	private JTextFieldPad txtCodCli = new JTextFieldPad(JTextFieldPad.TP_INTEGER,4,0);
	
	private JTextFieldPad txtRazCli = new JTextFieldPad(JTextFieldPad.TP_STRING,50,0);
	
	private JTextFieldPad txtSeqRestr = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	//private ListaCampos lcCli = new ListaCampos(this);
	
	public FRestrCli () {
		
		setTitulo("Restrição de clientes");
		setAtribos( 50, 50, 600, 450);
		pinCab = new JPanelPad(440,70);
		txtRazCli.setSoLeitura( true );
		setListaCampos(lcCampos);
		setPainel( pinCab, pnCliCab);
		

		adicCampo( txtCodCli, 7, 20, 100, 20,"Codcli","Cód.cli", ListaCampos.DB_PF,true);
		adicCampo( txtRazCli, 110, 20, 220, 20,"Razcli","Razão social", ListaCampos.DB_SI, false);  
		setListaCampos( true, "CLIENTE", "VD" );
		lcCampos.setQueryCommit(false);
		lcCampos.setReadOnly(true);
		//txtCodCli.setTabelaExterna(lcCli);
		
		setAltDet( 90 );
		pinDet = new JPanelPad( 600, 90 );
		setPainel( pinDet, pnDet );
		setListaCampos( lcDet );
		setNavegador( navRod );

		adicCampo( txtSeqRestr, 7, 20, 60, 20, "SeqRestr", "Item", ListaCampos.DB_PK, true );
		//adicCampo( txtPercItemPag, 70, 20, 97, 20, "PercPag", "Percento", ListaCampos.DB_SI, true );
		//adicCampo( txtDiasItemPag, 170, 20, 57, 20, "DiasPag", "Dias", ListaCampos.DB_SI, false );
		//adicCampo( txtDescItemPag, 230, 20, 143, 20, "DescParcPag", "Descrição", ListaCampos.DB_SI, false );

		setListaCampos( true, "RESTRICAO", "FN" );
		lcDet.setQueryInsert( true );

		//navRod.setAtivo( 4, false );
		//navRod.setAtivo( 5, false );
		montaTab();
		
		btImp.addActionListener(this);
		btPrevimp.addActionListener(this);
	}
	
	public void setConexao(Connection con) {
		
		super.setConexao( con );
	
	}
}
