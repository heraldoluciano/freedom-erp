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

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Painel;
import org.freedom.telas.FFDialogo;

public class DLRCliente extends FFDialogo {
  private JRadioGroup rgOrdem = null;
  private JRadioGroup rgModo = null;
  private JPanel pnlbSelec = new JPanel(new GridLayout(1,1));
  private Painel pinSelec = new Painel(400,90);
  private JPanel pnlbPessoa = new JPanel(new GridLayout(1,1));
  private Painel pinPessoa = new Painel(450,40);
  private JTextFieldPad txtCid = new JTextFieldPad();
  private JLabel lbSelec = new JLabel(" Selecão:");
  private JLabel lbDe = new JLabel("De:");
  private JLabel lbA = new JLabel("À:");
  private JTextFieldPad txtDe = new JTextFieldPad();
  private JTextFieldPad txtA = new JTextFieldPad();
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
  private JLabel lbSetor = new JLabel("Código e descrição do setor");
  private JLabel lbTipoCli = new JLabel("Código e descrição do tipo de cliente");
  private JLabel lbVendedor = new JLabel("Código e nome do vendedor/repres.");
  private JTextFieldPad txtCodSetor = new JTextFieldPad();
  private JTextFieldPad txtCodTipoCli = new JTextFieldPad();
  private JTextFieldPad txtCodVend = new JTextFieldPad();
  private JTextFieldFK txtDescSetor = new JTextFieldFK();
  private JTextFieldFK txtDescTipoCli = new JTextFieldFK();
  private JTextFieldFK txtNomeVend = new JTextFieldFK();
  private ListaCampos lcSetor = new ListaCampos(this);
  private ListaCampos lcTipoCli = new ListaCampos(this);
  private ListaCampos lcVendedor = new ListaCampos(this);
  public DLRCliente(Component cOrig, Connection cn) {
  	super(cOrig);
    setTitulo("Relatório de Clientes");
    setAtribos(460,475);
    vLabs.addElement("Código");
    vLabs.addElement("Razão");
    vVals.addElement("C");
    vVals.addElement("D");
    rgOrdem = new JRadioGroup(1,2,vLabs,vVals);
    rgOrdem.setVlrString("D");

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

    txtCodSetor.setTipo(JTextFieldPad.TP_INTEGER,8,0);
    txtDescSetor.setTipo(JTextFieldPad.TP_STRING,40,0);
    lcSetor.add(new GuardaCampo( txtCodSetor, 7, 100, 80, 20, "CodSetor", "Código", true, false, null, JTextFieldPad.TP_INTEGER,false),"txtCodSetor");
    lcSetor.add(new GuardaCampo( txtDescSetor, 90, 100, 207, 20, "DescSetor", "Descrição", false, false, null, JTextFieldPad.TP_STRING,false),"txtDescSetor");
    lcSetor.montaSql(false, "SETOR", "VD");
    lcSetor.setReadOnly(true);
    txtCodSetor.setTabelaExterna(lcSetor);
    txtCodSetor.setFK(true);
    txtCodSetor.setNomeCampo("CodSetor");

    txtCodTipoCli.setTipo(JTextFieldPad.TP_INTEGER,8,0);
    txtDescTipoCli.setTipo(JTextFieldPad.TP_STRING,40,0);
    lcTipoCli.add(new GuardaCampo( txtCodTipoCli, 7, 100, 80, 20, "CodTipoCli", "Código", true, false, null, JTextFieldPad.TP_INTEGER,false),"txtCodTipoCli");
    lcTipoCli.add(new GuardaCampo( txtDescTipoCli, 90, 100, 207, 20, "DescTipoCli", "Descrição", false, false, null, JTextFieldPad.TP_STRING,false),"txtDescTipoCli");
    lcTipoCli.montaSql(false, "TIPOCLI", "VD");
    lcTipoCli.setReadOnly(true);
    txtCodTipoCli.setTabelaExterna(lcTipoCli);
    txtCodTipoCli.setFK(true);
    txtCodTipoCli.setNomeCampo("CodTipoCli");

    txtCodVend.setTipo(JTextFieldPad.TP_INTEGER,8,0);
    txtNomeVend.setTipo(JTextFieldPad.TP_STRING,40,0);
    lcVendedor.add(new GuardaCampo( txtCodVend, 7, 100, 80, 20, "CodVend", "Código", true, false, null, JTextFieldPad.TP_INTEGER,false),"txtCodVend");
    lcVendedor.add(new GuardaCampo( txtNomeVend, 90, 100, 207, 20, "NomeVend", "Nome", false, false, null, JTextFieldPad.TP_STRING,false),"txtDescVend");
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
    adic(txtDescSetor,90,275,350,20);
    adic(lbVendedor,7,300,300,20);
    adic(txtCodVend,7,320,80,20);
    adic(txtNomeVend,90,320,350,20);
    adic(lbTipoCli,7,345,300,20);
    adic(txtCodTipoCli,7,365,80,20);
    adic(txtDescTipoCli,90,365,350,20);
    
	lcSetor.setConexao(cn);
	lcTipoCli.setConexao(cn);
	lcVendedor.setConexao(cn);
  }
  public String[] getValores() {
    String[] sRetorno = new String[15];
    if (rgOrdem.getVlrString().compareTo("C") == 0 )
      sRetorno[0] = "C1.CODCLI";
    else if (rgOrdem.getVlrString().compareTo("D") == 0 )
      sRetorno[0] = "C1.RAZCLI";
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
