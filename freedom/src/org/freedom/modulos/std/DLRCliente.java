/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)DLRCliente.java <BR>
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
import java.sql.Connection;
import java.util.Vector;

import org.freedom.componentes.JLabelPad;
import javax.swing.JPanel;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.JPanelPad;
import org.freedom.telas.FFDialogo;

public class DLRCliente extends FFDialogo {
  private JRadioGroup rgOrdem = null;
  private JRadioGroup rgModo = null;
  private JPanel pnlbSelec = new JPanel(new GridLayout(1,1));
  private JPanelPad pinSelec = new JPanelPad(400,90);
  private JPanel pnlbPessoa = new JPanel(new GridLayout(1,1));
  private JPanelPad pinPessoa = new JPanelPad(450,40);
  private JTextFieldPad txtCid = new JTextFieldPad();
  private JLabelPad lbSelec = new JLabelPad(" Selecão:");
  private JLabelPad lbDe = new JLabelPad("De:");
  private JLabelPad lbA = new JLabelPad("À:");
  private JTextFieldPad txtDe = new JTextFieldPad();
  private JTextFieldPad txtA = new JTextFieldPad();
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
  private JLabelPad lbTipoCli = new JLabelPad("Cód.cli.");
  private JLabelPad lbDescTipoCli = new JLabelPad("Descrição do tipo de cliente");
  private JLabelPad lbVendedor = new JLabelPad("Cód.repr.");
  private JLabelPad lbNomeVendedor = new JLabelPad("Nome do representante");
  private JTextFieldPad txtCodSetor = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtCodTipoCli = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtCodVend = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldFK txtDescSetor = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
  private JTextFieldFK txtDescTipoCli = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
  private JTextFieldFK txtNomeVend = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
  private ListaCampos lcSetor = new ListaCampos(this);
  private ListaCampos lcTipoCli = new ListaCampos(this);
  private ListaCampos lcVendedor = new ListaCampos(this);
  public DLRCliente(Component cOrig, Connection cn) {
  	super(cOrig);
    setTitulo("Relatório de Clientes");
    setAtribos(460,475);
    vLabs.addElement("Código");
    vLabs.addElement("Razão");
    vLabs.addElement("Cidade");
    vVals.addElement("C");
    vVals.addElement("R");
    vVals.addElement("I");
    rgOrdem = new JRadioGroup(1,3,vLabs,vVals);
    rgOrdem.setVlrString("R");

    vLabsModo.addElement("Resumido");
    vLabsModo.addElement("Completo");
    vLabsModo.addElement("Alinhar  Filial");
    vValsModo.addElement("R");
    vValsModo.addElement("C");
    vValsModo.addElement("A");
    
    rgModo = new JRadioGroup(1,3,vLabsModo,vValsModo);
    rgModo.setVlrString("R");

    cbObs.setVlrString("N");
    cbFis.setVlrString("S");
    cbJur.setVlrString("S");

    lcSetor.add(new GuardaCampo( txtCodSetor, "CodSetor", "Cód.setor", ListaCampos.DB_PK,false));
    lcSetor.add(new GuardaCampo( txtDescSetor, "DescSetor", "Descrição do setor",  ListaCampos.DB_SI,false));
    lcSetor.montaSql(false, "SETOR", "VD");
    lcSetor.setReadOnly(true);
    txtCodSetor.setTabelaExterna(lcSetor);
    txtCodSetor.setFK(true);
    txtCodSetor.setNomeCampo("CodSetor");

    lcTipoCli.add(new GuardaCampo( txtCodTipoCli, "CodTipoCli", "Cód.tp.cli.",  ListaCampos.DB_PK,false));
    lcTipoCli.add(new GuardaCampo( txtDescTipoCli, "DescTipoCli", "Descrição do tipo de cliente",  ListaCampos.DB_SI, false));
    lcTipoCli.montaSql(false, "TIPOCLI", "VD");
    lcTipoCli.setReadOnly(true);
    txtCodTipoCli.setTabelaExterna(lcTipoCli);
    txtCodTipoCli.setFK(true);
    txtCodTipoCli.setNomeCampo("CodTipoCli");

    lcVendedor.add(new GuardaCampo( txtCodVend, "CodVend", "Cód.repr.",  ListaCampos.DB_PK,false));
    lcVendedor.add(new GuardaCampo( txtNomeVend, "NomeVend", "Nome do representante",  ListaCampos.DB_SI,false));
    lcVendedor.montaSql(false, "VENDEDOR", "VD");
    lcVendedor.setReadOnly(true);
    txtCodVend.setTabelaExterna(lcVendedor);
    txtCodVend.setFK(true);
    txtCodVend.setNomeCampo("CodVend");
    
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
    adic(lbVendedor,7,300,300,20);
    adic(txtCodVend,7,320,80,20);
    adic(lbNomeVendedor,90,300,300,20);
    adic(txtNomeVend,90,320,350,20);
    adic(lbTipoCli,7,345,300,20);
    adic(txtCodTipoCli,7,365,80,20);
    adic(lbDescTipoCli,90,345,300,20);
    adic(txtDescTipoCli,90,365,350,20);
    
	lcSetor.setConexao(cn);
	lcTipoCli.setConexao(cn);
	lcVendedor.setConexao(cn);
  }
  public String[] getValores() {
    String[] sRetorno = new String[15];
    if (rgOrdem.getVlrString().equals("C"))
      sRetorno[0] = "C1.CODCLI";
    else if (rgOrdem.getVlrString().equals("R"))
      sRetorno[0] = "C1.RAZCLI";
    else if (rgOrdem.getVlrString().equals("I"))
      sRetorno[0] = "C1.CIDCLI, C1.RAZCLI";
    sRetorno[1] = cbObs.getVlrString();
    sRetorno[2] = txtDe.getText();
    sRetorno[3] = txtA.getText();
    sRetorno[4] = cbFis.getVlrString();
    sRetorno[5] = txtCid.getText();        
    sRetorno[6] = cbJur.getVlrString();
    sRetorno[7] = rgModo.getVlrString();
    sRetorno[8] = txtCodSetor.getText();
    sRetorno[9] = txtDescSetor.getText();
    sRetorno[10] = txtCodTipoCli.getText();
    sRetorno[11] = txtDescTipoCli.getText();
    sRetorno[12] = rgOrdem.getVlrString();
    sRetorno[13] = txtCodVend.getVlrString();
    sRetorno[14] = txtNomeVend.getVlrString();
    return sRetorno;
  }
}
