/**
 * @version 02/11/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
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
import java.util.Vector;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Painel;
import org.freedom.telas.FDetalhe;
public class FRegraFiscal extends FDetalhe {
	private Painel pinCab = new Painel();
	private Painel pinDet = new Painel();
	private JTextFieldPad txtCodRegraFiscal = new JTextFieldPad(JTextFieldPad.TP_STRING,4,0);
	private JTextFieldPad txtDescRegraFiscal = new JTextFieldPad(JTextFieldPad.TP_STRING,40,0);
	private JTextFieldPad txtCodNat= new JTextFieldPad(JTextFieldPad.TP_STRING,4,0);
	private JTextFieldFK txtDescNat = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
	private JTextFieldPad txtCodTipoMov= new JTextFieldPad(JTextFieldPad.TP_INTEGER,4,0);
	private JTextFieldFK txtDescTipoMov = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
	private JCheckBoxPad cbNoUF = new JCheckBoxPad("Sim","S","N");
	private Vector vDescCV = new Vector();
	private Vector vValCV = new Vector();
	private ListaCampos lcNat = new ListaCampos(this);
	private ListaCampos lcMov = new ListaCampos(this,"TM");
	public FRegraFiscal () {
		setTitulo("Regras Fiscais");
		setAtribos( 50, 50, 600, 450);
		pinCab = new Painel(440,70);
		setListaCampos(lcCampos);
		setPainel( pinCab, pnCliCab);
		adicCampo(txtCodRegraFiscal, 7, 20, 80, 20,"CodRegra","Cód.reg.fisc.", ListaCampos.DB_PK,true);
		adicCampo(txtDescRegraFiscal, 90, 20, 220, 20,"DescRegra","Descrição da regra fiscal", ListaCampos.DB_SI, true);
		setListaCampos( true, "REGRAFISCAL", "LF");
        lcCampos.setQueryInsert(false);

		lcNat.setUsaME(false);
		lcNat.add(new GuardaCampo( txtCodNat, "CodNat", "CFOP",  ListaCampos.DB_PK, false));
		lcNat.add(new GuardaCampo( txtDescNat, "DescNat", "Descrição da CFOP",  ListaCampos.DB_SI, false));
		lcNat.montaSql(false, "NATOPER", "LF");    
		lcNat.setQueryCommit(false);
		lcNat.setReadOnly(true);
		txtCodNat.setTabelaExterna(lcNat);

		
		lcMov.add(new GuardaCampo( txtCodTipoMov, "CodTipoMov", "Cód.tp.mov.",  ListaCampos.DB_PK, false));
		lcMov.add(new GuardaCampo( txtDescTipoMov, "DescTipoMov", "Descrição do tipo de movimento",  ListaCampos.DB_SI, false));
		lcMov.montaSql(false, "TIPOMOV", "EQ");    
		lcMov.setQueryCommit(false);
		lcMov.setReadOnly(true);
		txtCodTipoMov.setTabelaExterna(lcMov);
		
		vDescCV.addElement("Venda");
		vDescCV.addElement("Compra");
		vValCV.addElement("V");
		vValCV.addElement("C");
	
		JRadioGroup rgCV = new JRadioGroup(1,2,vDescCV,vValCV);
		rgCV.setVlrString("V");

		setAltDet(120);
		pinDet = new Painel(600,120);
		setPainel( pinDet, pnDet);
		setListaCampos(lcDet);
		setNavegador(navRod);
		adicCampo(txtCodNat, 7, 25, 70, 20,"CodNat","CFOP", ListaCampos.DB_PF,true);
		adicDescFK(txtDescNat, 80, 25, 197, 20, "DescNat", "Descrição da CFOP");
		adicCampo(txtCodTipoMov,7,75,70,20,"CodTipoMov","Cód.tp.mov.", ListaCampos.DB_FK,false);
		adicDescFK(txtDescTipoMov, 80, 75, 197, 20, "DescTipoMov", "Descrição do tipo de movimento");
		adicDB(cbNoUF, 280, 25, 87, 20, "NoUFItRF", "No Estado",true);
		adicDB(rgCV, 370, 20, 200, 30, "CVItRf", "Compra/venda",true);
		
		setListaCampos( false, "ITREGRAFISCAL", "LF");
		txtCodNat.setStrMascara("#.###");
        lcDet.setQueryInsert(false);
		montaTab();
		btImp.addActionListener(this);
		btPrevimp.addActionListener(this);
		
		
		
	}
	public void execShow(Connection con) {
		lcNat.setConexao(con);
		lcMov.setConexao(con);
		super.execShow(con);
	}
}
