/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)DLRLancaExp.java <BR>
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
 */

package org.freedom.modulos.std;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.Connection;
import java.util.Calendar;
import java.util.Vector;

import javax.swing.JLabel;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.telas.FDialogo;
public class DLRLancaExp extends FDialogo implements FocusListener {
	private JTextFieldPad txtDataini = new JTextFieldPad(); 
	private JTextFieldPad txtDatafim = new JTextFieldPad(); 
	private JTextFieldPad txtCodCli = new JTextFieldPad(); 
	private JTextFieldFK txtDescCli = new JTextFieldFK(); 
	private JTextFieldPad txtCodVend = new JTextFieldPad(); 
	private JTextFieldFK txtDescVend = new JTextFieldFK(); 
	private JTextFieldPad txtCodTipoExp = new JTextFieldPad(); 
	private JTextFieldFK txtDescTipoExp = new JTextFieldFK(); 
	private ListaCampos lcCli = new ListaCampos(this);
	private ListaCampos lcVend = new ListaCampos(this);
	private ListaCampos lcTipoExp = new ListaCampos(this);
    private Vector vLabsRet = new Vector();
    private Vector vValsRet = new Vector();
    private JRadioGroup rgRet;
	public DLRLancaExp() {
		setTitulo("Lançamentos de Expositores");
		setAtribos(80,80,310,270);
   		txtDataini.setTipo(JTextFieldPad.TP_DATE,10,0);
		txtDatafim.setTipo(JTextFieldPad.TP_DATE,10,0);
		
		txtCodCli.setTipo(JTextFieldPad.TP_INTEGER,8,0);
		txtDescCli.setTipo(JTextFieldPad.TP_STRING,40,0);
		lcCli.add(new GuardaCampo( txtCodCli, 7, 100, 80, 20, "CodCli", "CódCli", true, false, null, JTextFieldPad.TP_INTEGER,false),"txtCodClix");
		lcCli.add(new GuardaCampo( txtDescCli, 90, 100, 207, 20, "RazCli", "Descrição", false, false, null, JTextFieldPad.TP_STRING,false),"txtRazCli");
		lcCli.montaSql(false, "CLIENTE", "VD");
		lcCli.setReadOnly(true);
		txtCodCli.setTabelaExterna(lcCli);
		txtCodCli.setFK(true);
		txtCodCli.setNomeCampo("CodCli");

		txtCodVend.setTipo(JTextFieldPad.TP_INTEGER,8,0);
		txtDescVend.setTipo(JTextFieldPad.TP_STRING,40,0);
		lcVend.add(new GuardaCampo( txtCodVend, 7, 100, 80, 20, "CodVend", "CódVend", true, false, null, JTextFieldPad.TP_INTEGER,false),"txtCodVendx");
		lcVend.add(new GuardaCampo( txtDescVend, 90, 100, 207, 20, "NomeVend", "Descrição", false, false, null, JTextFieldPad.TP_STRING,false),"txtNomeVend");
		lcVend.montaSql(false, "VENDEDOR", "VD");
		lcVend.setReadOnly(true);
		txtCodVend.setTabelaExterna(lcVend);
		txtCodVend.setFK(true);
		txtCodVend.setNomeCampo("CodVend");
 
		txtCodTipoExp.setTipo(JTextFieldPad.TP_INTEGER,8,0);
		txtDescTipoExp.setTipo(JTextFieldPad.TP_STRING,40,0);
		lcTipoExp.add(new GuardaCampo( txtCodTipoExp, 7, 100, 80, 20, "CodTipoExp", "CódExp", true, false, null, JTextFieldPad.TP_INTEGER,false),"txtCodTipoExpx");
		lcTipoExp.add(new GuardaCampo( txtDescTipoExp, 90, 100, 207, 20, "DescTipoExp", "Descrição", false, false, null, JTextFieldPad.TP_STRING,false),"txtDescTipoExp");
		lcTipoExp.montaSql(false, "TIPOEXP", "EQ");
		lcTipoExp.setReadOnly(true);
		txtCodTipoExp.setTabelaExterna(lcTipoExp);
		txtCodTipoExp.setFK(true);
		txtCodTipoExp.setNomeCampo("CodTipoExp");
		
		Calendar cPeriodo = Calendar.getInstance();
		txtDatafim.setVlrDate(cPeriodo.getTime());
		cPeriodo.set(Calendar.DAY_OF_MONTH,cPeriodo.get(Calendar.DAY_OF_MONTH)-30);
		txtDataini.setVlrDate(cPeriodo.getTime());
	
        vValsRet.addElement("E");
        vValsRet.addElement("R");
        vValsRet.addElement("T");
        vLabsRet.addElement("Enviados");
        vLabsRet.addElement("Retornados");
        vLabsRet.addElement("Todos");
        rgRet = new JRadioGroup(1,3,vLabsRet,vValsRet);
        rgRet.setVlrString("T");
		
        adic(new JLabel("Periodo:"),7,5,120,20);
		adic(new JLabel("De:"),7,25,30,20);
		adic(txtDataini,40,25,97,20);
		adic(new JLabel("Até:"),140,25,17,20);
		adic(txtDatafim,160,25,100,20);
		adic(new JLabel("Código e descrição do cliente"),7,45,280,20);
		adic(txtCodCli,7,65,80,20);
		adic(txtDescCli,90,65,200,20);
		adic(new JLabel("Código e nome do representante"),7,85,280,20);
		adic(txtCodVend,7,105,80,20);
		adic(txtDescVend,90,105,200,20);
		adic(new JLabel("Código e descrição do tipo de expositor"),7,125,280,20);
		adic(txtCodTipoExp,7,145,80,20);
		adic(txtDescTipoExp,90,145,200,20);
		adic(rgRet,7,170,290,30);
		txtCodCli.addFocusListener(this);
	}
	public String[] getValores() {
		String sRet[] = new String[6];
		sRet[0] = txtDataini.getVlrString();
		sRet[1] = txtDatafim.getVlrString();
		sRet[2] = txtCodCli.getVlrString();
		sRet[3] = txtCodVend.getVlrString();
		sRet[4] = txtCodTipoExp.getVlrString();
		sRet[5] = rgRet.getVlrString();
		return sRet;
	}
	public void focusLost(FocusEvent fevt) {
		if ((fevt.getSource() == txtCodCli) && txtCodCli.getText().trim().length() > 0) {
			txtCodVend.setAtivo(false);
		}
	}
	public void focusGained(FocusEvent fevt) { }
	public void setConexao(Connection cn) {
		lcCli.setConexao(cn);
		lcVend.setConexao(cn);
		lcTipoExp.setConexao(cn);
	}
}
