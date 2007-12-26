/**
 * @version 04/12/2007 <BR>
 * @author Setpoint Informática Ltda./Robson Sanchez <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.atd <BR>
 * Classe: @(#)FAjustaSitOrc.java <BR>
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

package org.freedom.modulos.atd;

import org.freedom.telas.FDados;


import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.Vector;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JComboBoxPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Navegador;


public class FAjustaSitOrc extends FDados implements ActionListener, CarregaListener {
	
	private static final long serialVersionUID = 1L;
	
	private ListaCampos lcOrc = new ListaCampos(this, "");
	private ListaCampos lcProd = new ListaCampos( this, "PD" );
	private JTextFieldPad txtCodProd = new JTextFieldPad( JTextFieldPad.TP_STRING, 13, 0 );
	private JTextFieldPad txtRefProd = new JTextFieldPad( JTextFieldPad.TP_STRING, 13, 0 );
	private JTextFieldPad txtCodBarras = new JTextFieldPad( JTextFieldPad.TP_STRING, 13, 0 );
	private JTextFieldPad txtCodOrc = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	private JTextFieldFK txtDtOrc = new JTextFieldFK( JTextFieldPad.TP_DATE, 10, 0 );
	private JTextFieldPad txtDtVencOrc = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );
	private JTextFieldPad txtCodPlanoPag = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	private JTextFieldPad txtCodCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	private JTextFieldPad txtCodConv = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	private JTextFieldPad txtCodItOrc = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
	private JTextFieldPad txtSeqIni = new JTextFieldPad(JTextFieldPad.TP_INTEGER,9,0);
	private JTextFieldPad txtSeqMax = new JTextFieldPad(JTextFieldPad.TP_INTEGER,9,0);
	private JTextFieldFK txtDescProd = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	private JComboBoxPad cbSitEntItOrc = null;
	private JComboBoxPad cbSitTermRItOrc = null;
	private Vector<String> vLabs1 = new Vector<String>();
	private Vector<String> vLabs2 =  new Vector<String>();
	private Vector<String> vVals1 = new Vector<String>();
	private Vector<String> vVals2 = new Vector<String>();

	public FAjustaSitOrc() {
		super();
		setTitulo("Ajusta Situação dos Orçamentos");
		setAtribos( 50, 50, 445, 270);

		vLabs1.addElement( "Não entregue" );
		vLabs1.addElement( "Entregue" );
		vVals1.addElement( "N" );
		vVals1.addElement( "E" );
		cbSitEntItOrc = new JComboBoxPad(vLabs1, vVals1, JComboBoxPad.TP_STRING, 1, 0);

		/*E - Emitir
		N - Não emitir
		O - Emitido*/
		
		vLabs2.addElement( "Emitir" );
		vLabs2.addElement( "Não emitir" );
		vLabs2.addElement( "Emitido" );
		vVals2.addElement( "E" );
		vVals2.addElement( "N" );
		vVals2.addElement( "O" );
		cbSitTermRItOrc = new JComboBoxPad(vLabs2, vVals2, JComboBoxPad.TP_STRING, 1, 0);
		
		lcOrc.add( new GuardaCampo( txtCodOrc, "CodOrc", "Cód.Orç.", ListaCampos.DB_PK, false ) );
		lcOrc.add( new GuardaCampo( txtCodCli, "CodCli", "Cód.cli.", ListaCampos.DB_SI, false ) );
		lcOrc.add( new GuardaCampo( txtCodConv, "CodConv", "Cód.conv.", ListaCampos.DB_SI, false ) );
		lcOrc.add( new GuardaCampo( txtDtOrc, "DtOrc", "Dt.emissão", ListaCampos.DB_SI, false ) );
		lcOrc.add( new GuardaCampo( txtDtVencOrc, "DtVencOrc", "Dt.vencto.", ListaCampos.DB_SI, false ) );
		lcOrc.add( new GuardaCampo( txtCodPlanoPag, "CodPlanoPag", "Cód.p.pag.", ListaCampos.DB_SI, false ) );
		lcOrc.montaSql( false, "ORCAMENTO", "VD" );
		lcOrc.setQueryCommit( false );
		lcOrc.setReadOnly( true );
		txtCodOrc.setTabelaExterna(lcOrc);
		txtCodProd.setAtivo( false );

		// FK Produto
		lcProd.add( new GuardaCampo( txtCodProd, "CodProd", "Cód.prod.", ListaCampos.DB_PK, txtDescProd, false ) );
		lcProd.add( new GuardaCampo( txtDescProd, "DescProd", "Descrição do produto", ListaCampos.DB_SI, false ) );
		lcProd.add( new GuardaCampo( txtRefProd, "RefProd", "Referência do produto", ListaCampos.DB_SI, false ) );
		lcProd.add( new GuardaCampo( txtCodBarras, "CodBarProd", "Código de barras", ListaCampos.DB_SI, false ) );
		lcProd.montaSql( false, "PRODUTO", "EQ" );
		lcProd.setQueryCommit( false );
		lcProd.setReadOnly( true );
		txtCodProd.setTabelaExterna( lcProd );
		
		adicCampo(txtCodOrc, 7, 20, 70, 20, "CodOrc", "Núm.orç.", ListaCampos.DB_PF, true);
		adicDescFK( txtDtOrc, 80, 20, 90, 20, "DtOrc", "Dt.emissão");
		adicCampo(txtCodItOrc, 173, 20, 70, 20, "CodItOrc", "Item orç.", ListaCampos.DB_PK, true);
		adicCampo(txtCodProd, 7, 60, 70, 20, "CodProd", "Cód.prod.", ListaCampos.DB_FK, txtDescProd, true);
		adicDescFK(txtDescProd, 80, 60, 310, 20, "DescProd", "Descrição do produto");
		adicDB(cbSitEntItOrc, 7, 100, 150, 30, "SitEntItOrc", "Situação da entrega",true);
		adicDB(cbSitTermRItOrc, 160, 100, 230, 30, "SitTermRItOrc", "Situação do termo de recebimento",true);
		setListaCampos( true, "ITORCAMENTO", "VD");
		nav.btNovo.setVisible( false );
		nav.btExcluir.setVisible( false );
		//nav.setAtivo( 1, false);
		lcCampos.addCarregaListener(this);
	}
	  
	public void setConexao(Connection cn) {
		super.setConexao(cn);
		lcOrc.setConexao( cn );
		lcProd.setConexao( cn );
	
	}
	
	public void beforeCarrega( CarregaEvent e ) { 
		txtSeqIni.setAtivo(true);
		txtSeqMax.setAtivo(true);
	}

	public void afterCarrega( CarregaEvent e ) { 
		if(e.getListaCampos() == lcCampos) {
		}
	}

}
