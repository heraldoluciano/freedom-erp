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
import org.freedom.telas.FDetalhe;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Painel;

import java.sql.Connection;
import java.util.Vector;
public class FRegraFiscal extends FDetalhe {
	private Painel pinCab = new Painel();
	private Painel pinDet = new Painel();
	private JTextFieldPad txtCodRegraFiscal = new JTextFieldPad();
	private JTextFieldPad txtDescRegraFiscal = new JTextFieldPad();
	private JTextFieldPad txtCodNat= new JTextFieldPad();
	private JTextFieldFK txtDescNat = new JTextFieldFK();
	private JTextFieldPad txtCodTipoMov= new JTextFieldPad();
	private JTextFieldFK txtDescTipoMov = new JTextFieldFK();
	private JCheckBoxPad cbNoUF = new JCheckBoxPad("Sim","S","N");
	private Vector vDescCV = new Vector();
	private Vector vValCV = new Vector();
	private ListaCampos lcNat = new ListaCampos(this);
	private ListaCampos lcMov = new ListaCampos(this,"TM");
	public FRegraFiscal () {
		setTitulo("Regras Fiscais");
		setAtribos( 50, 50, 600, 300);
		pinCab = new Painel(440,70);
		setListaCampos(lcCampos);
		setPainel( pinCab, pnCliCab);
		adicCampo(txtCodRegraFiscal, 7, 20, 50, 20,"CodRegra","Código",JTextFieldPad.TP_STRING,4,0,true,false,null,true);
		adicCampo(txtDescRegraFiscal, 60, 20, 220, 20,"DescRegra","Descrição",JTextFieldPad.TP_STRING,40,0,false,false,null,true);
		setListaCampos( true, "REGRAFISCAL", "LF");
        lcCampos.setQueryInsert(false);

		txtCodNat.setTipo(JTextFieldPad.TP_STRING,4,0);    
		txtDescNat.setTipo(JTextFieldPad.TP_STRING,40,0);    
		lcNat.setUsaME(false);
		lcNat.add(new GuardaCampo( txtCodNat, 7, 100, 80, 20, "CodNat", "Natureza", true, false, null, JTextFieldPad.TP_STRING,false),"txtCodSeriex");
		lcNat.add(new GuardaCampo( txtDescNat, 90, 100, 207, 20, "DescNat", "Descrição", false, false, null, JTextFieldPad.TP_STRING,false),"txtDescSeriex");
		lcNat.montaSql(false, "NATOPER", "LF");    
		lcNat.setQueryCommit(false);
		lcNat.setReadOnly(true);
		txtCodNat.setTabelaExterna(lcNat);

		txtCodTipoMov.setTipo(JTextFieldPad.TP_INTEGER,4,0);    
		txtDescTipoMov.setTipo(JTextFieldPad.TP_STRING,40,0);    
		
		lcMov.add(new GuardaCampo( txtCodTipoMov, 7, 120, 80, 20, "CodTipoMov", "Movimento", true, false, null, JTextFieldPad.TP_INTEGER,false));
		lcMov.add(new GuardaCampo( txtDescTipoMov, 90, 120, 207, 20, "DescTipoMov", "Descrição", false, false, null, JTextFieldPad.TP_STRING,false));
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
		adicCampo(txtCodNat, 7, 25, 60, 20,"CodNat","Natureza",JTextFieldPad.TP_STRING,4,0,true,true,null,true);
		adicDescFK(txtDescNat, 70, 25, 197, 20, "DescNat", "Descrição", JTextFieldPad.TP_STRING, 40, 0);
		adicCampo(txtCodTipoMov,7,75,60,20,"CodTipoMov","Tipo Mov.",JTextFieldPad.TP_INTEGER,4,0,false,true,null,false);
		adicDescFK(txtDescTipoMov, 70, 75, 197, 20, "DescTipoMov", "Descrição", JTextFieldPad.TP_STRING, 40, 0);
		adicDB(cbNoUF, 270, 25, 97, 20, "NoUFItRF", "No Estado",JTextFieldPad.TP_STRING,true);
		adicDB(rgCV, 370, 20, 200, 30, "CVItRf", "Compra/Venda",JTextFieldPad.TP_STRING,true);
		
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
