/**
 * @version 23/05/2008 <BR>
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
import java.util.Date;

import javax.swing.JScrollPane;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.acao.InsertEvent;
import org.freedom.acao.InsertListener;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JTextAreaPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.JPanelPad;
import org.freedom.telas.FDetalhe;

public class FRestrCli extends FDetalhe implements CarregaListener, InsertListener  {
	
	private static final long serialVersionUID = 1L;

	private JPanelPad pinCab = new JPanelPad();
	
	private JPanelPad pinDet = new JPanelPad();
	
	private JTextFieldPad txtCodCli = new JTextFieldPad(JTextFieldPad.TP_INTEGER,4,0);
	
	private JTextFieldPad txtRazCli = new JTextFieldPad(JTextFieldPad.TP_STRING,50,0);
	
	private JTextFieldPad txtSeqRestr = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	
	private JTextFieldPad txtCodTipoRestr = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	
	private JTextFieldFK txtDescTipoRestr = new JTextFieldFK(JTextFieldFK.TP_STRING,50,0);
	
	private JTextFieldPad txtSitRestr = new JTextFieldPad(JTextFieldPad.TP_STRING,1,0);
	
	private JTextFieldPad txtDataRestr = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
	
	private JTextFieldPad txtDataCancRestr = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
	
	private JTextFieldPad txtLegenda = new JTextFieldPad(JTextFieldPad.TP_STRING,50,0);
	
	private JTextAreaPad txaObs= new JTextAreaPad(512);
	
	private JScrollPane spnObs = new JScrollPane(txaObs);
	
	private ListaCampos lcTipoRestr = new ListaCampos(this, "TR");
	
	
	public FRestrCli () {
		
		setTitulo("Restrição de clientes");
		setAtribos( 50, 50, 600, 450);
		pinCab = new JPanelPad(440,70);
		txtRazCli.setSoLeitura( true );
		setListaCampos(lcCampos);
		setPainel( pinCab, pnCliCab);
		
		navRod.setAtivo( 5, false );
		

		adicCampo( txtCodCli, 7, 20, 100, 20,"Codcli","Cód.cli", ListaCampos.DB_PF,true);
		adicCampo( txtRazCli, 110, 20, 300, 20,"Razcli","Razão social", ListaCampos.DB_SI, false);  
		setListaCampos( true, "CLIENTE", "VD" );
		lcCampos.setQueryCommit(false);
		lcCampos.setReadOnly(true);
		
		
		lcTipoRestr.add(new GuardaCampo( txtCodTipoRestr, "CodTpRestr", "Cód.tp.restr.",  ListaCampos.DB_PK, false));
		lcTipoRestr.add(new GuardaCampo( txtDescTipoRestr, "DescTpRestr", "Descriçao da restrição",  ListaCampos.DB_SI, false));
		lcTipoRestr.montaSql(false, "TIPORESTR", "FN");    
		lcTipoRestr.setQueryCommit(false);
		lcTipoRestr.setReadOnly(true);
		txtCodTipoRestr.setTabelaExterna(lcTipoRestr);
		
		setAltDet( 150 );
		pinDet = new JPanelPad( 600, 90 );
		setPainel( pinDet, pnDet );
		setListaCampos( lcDet );
		setNavegador( navRod );

		txtLegenda.setSoLeitura( true );
		txtSitRestr.setSoLeitura( true );
		adicCampo( txtSeqRestr, 7, 20, 30, 20, "SeqRestr", "Item", ListaCampos.DB_PK, true );
		adicCampo( txtCodTipoRestr, 40, 20, 80, 20, "CodTpRestr", "Cód.tp.restr.", ListaCampos.DB_FK, txtDescTipoRestr, true );
		adicDescFK( txtDescTipoRestr, 123, 20, 200, 20, "DescTpRestr", "Descriçao da restrição" );
		adic( new JLabelPad ("Situação"), 326, 0, 200, 20 );
		adic( txtLegenda, 326, 20, 200, 20 );
		adicCampo( txtSitRestr, 529, 20, 30, 20, "SitRestr", "", ListaCampos.DB_SI, false );
		adicCampo( txtDataRestr, 7, 60, 100, 20, "DtRestr", "Data cadastro", ListaCampos.DB_SI, true );
		adicCampo( txtDataCancRestr, 7, 100, 100, 20, "DtCancRestr", "Data canc.", ListaCampos.DB_SI, false );
		adicDBLiv( txaObs, 113, 60, 450, 60,"ObsRestr","Observação",false);
		adic( new JLabelPad ("Observação"), 113, 40, 200, 20 );
		setListaCampos( true, "RESTRICAO", "FN" );
		lcDet.setQueryInsert( true );
		
		montaTab();
		tab.setTamColuna( 200, 2 );
		tab.setTamColuna( 130, 4 );
		tab.setTamColuna( 130, 5 );
		tab.setTamColuna( 300, 6 );
		
		btImp.addActionListener(this);
		btPrevimp.addActionListener(this);
		lcTipoRestr.addCarregaListener( this );
		lcDet.addCarregaListener( this );
		lcDet.addInsertListener( this );
		lcCampos.addCarregaListener( this );
	}
	
	public void setConexao(Connection con) {
		
		super.setConexao( con );
		lcTipoRestr.setConexao( con );
	
	}

	public void afterCarrega( CarregaEvent cevt ) {

		
		if( cevt.getListaCampos() == lcDet ){
			
			if( "I".equals( txtSitRestr.getVlrString())){
				txtLegenda.setVlrString( "Inclusão" );
			}else if("C".equals( txtSitRestr.getVlrString())){
				txtLegenda.setVlrString( "Cancelamento" );
			}
			txtDataCancRestr.setEnabled( true );
		}
	}

	public void beforeCarrega( CarregaEvent cevt ) {

		
	}

	public void afterInsert( InsertEvent ievt ) {

		if( ievt.getListaCampos() == lcDet ){
			txtLegenda.setVlrString( "" );
			txtDataCancRestr.setEnabled( false );
			txtDataRestr.setVlrDate( new Date() );
		}
	}

	public void beforeInsert( InsertEvent ievt ) {

	
	}
}
