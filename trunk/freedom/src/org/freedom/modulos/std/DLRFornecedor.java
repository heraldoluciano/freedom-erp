/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freeedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)DLRFornecedor.java <BR>
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

import java.awt.Component;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.JPanelPad;
import org.freedom.telas.FFDialogo;

import java.sql.Connection;
import java.util.Vector;
public class DLRFornecedor extends FFDialogo {
  private JRadioGroup rgOrdem = null;
  private JRadioGroup rgModo = null;
  private JPanel pnlbSelec = new JPanel(new GridLayout(1,1));
  private JPanelPad pinSelec = new JPanelPad(350,70);
  private JPanel pnlbPessoa = new JPanel(new GridLayout(1,1));
  private JPanelPad pinPessoa = new JPanelPad(450,40);
  private JTextFieldPad txtCid = new JTextFieldPad(JTextFieldPad.TP_STRING,30,0);
  private JLabel lbSelec = new JLabel(" Selecão:");
  private JLabel lbDe = new JLabel("De:");
  private JLabel lbA = new JLabel("À:");
  private JTextFieldPad txtDe = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
  private JTextFieldPad txtA = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
  private JLabel lbOrdem = new JLabel("Ordenar por:");
  private JLabel lbPessoa = new JLabel(" Selecionar pessoas:");
  private JLabel lbCid = new JLabel("Cidade");
  private JLabel lbModo = new JLabel("Modo do relatório:");
  private JCheckBoxPad cbObs = new JCheckBoxPad("Imprimir Observações ?","S","N");
  private JCheckBoxPad cbFis = new JCheckBoxPad("Física","S","N");
  private JCheckBoxPad cbJur = new JCheckBoxPad("Jurídica","S","N");
  private Vector vLabs = new Vector();
  private Vector vVals = new Vector();
  private Vector vLabsModo = new Vector();
  private Vector vValsModo = new Vector();
  private JLabel lbTipoFor = new JLabel("Cód.t.for.");
  private JLabel lbDescTipoFor = new JLabel("Descrição do tipo de fornecedor");
  private JTextFieldPad txtCodTipoFor = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldFK txtDescTipoFor = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
  private ListaCampos lcTipoFor = new ListaCampos(this);
  public DLRFornecedor(Component cOrig, Connection cn) {
  	super(cOrig);
  	setTitulo("Relatório de Fornecedores");
    setAtribos(420,400);
    vLabs.addElement("Código");
    vLabs.addElement("Razão");
    vVals.addElement("C");
    vVals.addElement("D");
    rgOrdem = new JRadioGroup(1,2,vLabs,vVals);
    rgOrdem.setVlrString("D");

    vLabsModo.addElement("Resumido");
    vLabsModo.addElement("Completo");
    vValsModo.addElement("R");
    vValsModo.addElement("C");
    rgModo = new JRadioGroup(1,2,vLabsModo,vValsModo);
    rgModo.setVlrString("R");

    cbObs.setVlrString("N");
	cbFis.setVlrString("N");
	cbJur.setVlrString("S");

    lcTipoFor.add(new GuardaCampo( txtCodTipoFor, "CodTipoFor", "Cód.tp.for.", ListaCampos.DB_PK, false));
    lcTipoFor.add(new GuardaCampo( txtDescTipoFor, "DescTipoFor", "Descrição do tipo de fornecedor",ListaCampos.DB_SI, false));
    lcTipoFor.montaSql(false, "TIPOFOR", "CP");
    lcTipoFor.setReadOnly(true);
    txtCodTipoFor.setTabelaExterna(lcTipoFor);
    txtCodTipoFor.setFK(true);
    txtCodTipoFor.setNomeCampo("CodTipoFor");

    pnlbSelec.add(lbSelec);
    adic(lbOrdem,7,5,180,20);
    adic(rgOrdem,7,25,200,30);
    adic(cbObs,210,35,190,20);
    adic(pnlbSelec,10,63,80,15);
    pinSelec.adic(lbDe,7,10,30,20);
    pinSelec.adic(txtDe,40,15,360,20);
    pinSelec.adic(lbA,7,40,30,20);
    pinSelec.adic(txtA,40,40,360,20);
    adic(pinSelec,7,70,393,70);
	pnlbPessoa.add(lbPessoa);
	adic(pnlbPessoa,10,148,170,15);
	pinPessoa.adic(cbFis,7,10,80,20);
	pinPessoa.adic(cbJur,90,10,80,20);
	adic(pinPessoa,7,155,200,40);
    adic(lbCid,210,155,190,20);
    adic(txtCid,210,175,190,20);
    adic(lbModo,7,200,140,20);
    adic(rgModo,7,220,393,30);
    adic(lbTipoFor,7,250,300,20);
    adic(txtCodTipoFor,7,280,80,20);
    adic(lbDescTipoFor,90,250,300,20);
    adic(txtDescTipoFor,90,280,310,20);
    
	lcTipoFor.setConexao(cn);
  }
  public String[] getValores() {
    String[] sRetorno = new String[10];
    if (rgOrdem.getVlrString().compareTo("C") == 0 )
      sRetorno[0] = "CODFOR";
    else if (rgOrdem.getVlrString().compareTo("D") == 0 )
      sRetorno[0] = "RAZFOR";
    sRetorno[1] = cbObs.getVlrString();
    sRetorno[2] = txtDe.getText();
    sRetorno[3] = txtA.getText();
	sRetorno[4] = cbFis.getVlrString();
	sRetorno[5] = txtCid.getVlrString();
	sRetorno[6] = cbJur.getVlrString();
    sRetorno[7] = rgModo.getVlrString();
    sRetorno[8] = txtCodTipoFor.getText();
    sRetorno[9] = txtDescTipoFor.getText();
    return sRetorno;
  }
}
