/**
 * @version 10/10/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.tmk <BR>
 * Classe: @(#)DLRCont.java <BR>
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


package org.freedom.modulos.tmk;
import java.awt.Component;
import java.awt.GridLayout;
import java.sql.Connection;
import java.util.Vector;

import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JPanelPad;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.telas.FFDialogo;

public class DLRCont extends FFDialogo {
	private static final long serialVersionUID = 1L;

  private JRadioGroup rgOrdem = null;
  private JRadioGroup rgModo = null;
  private JPanelPad pnlbSelec = new JPanelPad(JPanelPad.TP_JPANEL,new GridLayout(1,1));
  private JPanelPad pinSelec = new JPanelPad(350,70);
  private JPanelPad pnlbPessoa = new JPanelPad(JPanelPad.TP_JPANEL,new GridLayout(1,1));
  private JPanelPad pinPessoa = new JPanelPad(450,40);
  private JTextFieldPad txtCid = new JTextFieldPad(JTextFieldPad.TP_STRING,30,0);
  private JLabelPad lbSelec = new JLabelPad(" Selecão:");
  private JLabelPad lbDe = new JLabelPad("De:");
  private JLabelPad lbA = new JLabelPad("À:");
  private JTextFieldPad txtDe = new JTextFieldPad(JTextFieldPad.TP_STRING,40,0);
  private JTextFieldPad txtA = new JTextFieldPad(JTextFieldPad.TP_STRING,40,0);
  private JLabelPad lbOrdem = new JLabelPad("Ordenar por:");
  private JLabelPad lbPessoa = new JLabelPad(" Selecionar pessoas:");
  private JLabelPad lbCid = new JLabelPad("Cidade");
  private JLabelPad lbModo = new JLabelPad("Modo do relatório:");
  private JCheckBoxPad cbObs = new JCheckBoxPad("Imprimir Observações ?","S","N");
  private JCheckBoxPad cbFis = new JCheckBoxPad("Física","S","N");
  private JCheckBoxPad cbJur = new JCheckBoxPad("Jurídica","S","N");
  private Vector vLabs = new Vector();
  private Vector vVals = new Vector();
  private Vector vLabsModo = new Vector();
  private Vector vValsModo = new Vector();
  private JLabelPad lbSetor = new JLabelPad("Cód.setor");
  private JLabelPad lbDescSetor = new JLabelPad("Descrição do setor");
  private JTextFieldPad txtCodSetor = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldFK txtDescSetor = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
  private ListaCampos lcSetor = new ListaCampos(this);
  public DLRCont(Component cOrig, Connection cn) {
  	super(cOrig);
    setTitulo("Relatório de Contatos");
    setAtribos(460,385);
    vLabs.addElement("Código");
    vLabs.addElement("Nome");
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

    lcSetor.add(new GuardaCampo( txtCodSetor, "CodSetor", "Código", ListaCampos.DB_PK, false));
    lcSetor.add(new GuardaCampo( txtDescSetor, "DescSetor", "Descrição", ListaCampos.DB_SI, false));
    lcSetor.montaSql(false, "SETOR", "VD");
    lcSetor.setReadOnly(true);
    txtCodSetor.setTabelaExterna(lcSetor);
    txtCodSetor.setFK(true);
    txtCodSetor.setNomeCampo("CodSetor");

    pnlbSelec.add(lbSelec);
    adic(lbOrdem,7,5,180,20);
    adic(rgOrdem,7,25,240,30);
    adic(cbObs,250,35,190,20);
    adic(pnlbSelec,10,63,80,15);
    pinSelec.adic(lbDe,7,10,30,20);
    pinSelec.adic(txtDe,40,15,380,20);
    pinSelec.adic(lbA,7,40,30,20);
    pinSelec.adic(txtA,40,40,380,20);
    adic(pinSelec,7,70,433,70);
    pnlbPessoa.add(lbPessoa);
    adic(pnlbPessoa,10,148,170,15);
    pinPessoa.adic(cbFis,7,10,93,20);
    pinPessoa.adic(cbJur,145,10,100,20);
    adic(pinPessoa,7,155,290,40);
    adic(lbCid,300,155,140,20);
    adic(txtCid,300,175,140,20);
    adic(lbModo,7,200,170,20);
    adic(rgModo,7,220,433,30);
    adic(lbSetor,7,255,250,20);
    adic(txtCodSetor,7,275,80,20);
    adic(lbDescSetor,90,255,250,20);
    adic(txtDescSetor,90,275,350,20);
    
	lcSetor.setConexao(cn);
  }
  public String[] getValores() {
    String[] sRetorno = new String[10];
    if (rgOrdem.getVlrString().compareTo("C") == 0 )
      sRetorno[0] = "CODCTO";
    else if (rgOrdem.getVlrString().compareTo("D") == 0 )
      sRetorno[0] = "NOMECTO";
    sRetorno[1] = cbObs.getVlrString();
    sRetorno[2] = txtDe.getText();
    sRetorno[3] = txtA.getText();
    sRetorno[4] = cbFis.getVlrString();
    sRetorno[5] = txtCid.getVlrString();
    sRetorno[6] = cbJur.getVlrString();
    sRetorno[7] = rgModo.getVlrString();
    sRetorno[8] = txtCodSetor.getText();
    sRetorno[9] = txtDescSetor.getText();
    return sRetorno;
  }
}
