/**
 * @version 11/02/2002 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FImpressora.java <BR>
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import javax.swing.JComboBox;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JComboBoxPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.telas.FDados;

import java.util.Vector;
public class FImpressora extends FDados {
  private JTextFieldPad txtCodImp = new JTextFieldPad(8);
  private JTextFieldPad txtDescImp = new JTextFieldPad(40);
  private JTextFieldPad txtTipoImp = new JTextFieldPad(2);
  private JTextFieldPad txtLinPagImp = new JTextFieldPad(8);
  private JTextFieldPad txtNSerieImp = new JTextFieldPad(15);
  private JTextFieldPad txtPortaWinImp = new JTextFieldPad(4);
  private JTextFieldPad txtPortaLinImp = new JTextFieldPad(20);
  private JTextFieldPad txtCodPapel = new JTextFieldPad(20);
  private JTextFieldFK txtDescPapel = new JTextFieldFK();
  private JComboBox cbTipoImp = new JComboBox();
  private JComboBoxPad cbDestImp = null;
  private Vector vVals = new Vector();
  private Vector vLabs = new Vector();
  private ListaCampos lcPapel = new ListaCampos(this,"PL");
  public FImpressora() {
//Remove o painel de impressão:
    pnRodape.remove(2);
//Constroi a tela FImpressoras:
    setTitulo("Cadastro de impressoras");
    setAtribos( 50, 50, 400, 280);

//Prepara o Combo para alterar o campo txtTipoImp    
    cbTipoImp.addItem("");
    cbTipoImp.addItem("Epson Matricial");
    cbTipoImp.addItem("HP Desk Jet");
    cbTipoImp.addItem("HP Laser Jet");
    cbTipoImp.addItem("Epson Stylus");
    cbTipoImp.addItem("Epson Laser");
    cbTipoImp.addItem("Fiscal MP20");
    cbTipoImp.addItem("Fiscal MP40");
    cbTipoImp.setEditable(false);
    cbTipoImp.addActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
          if (evt.getSource() == cbTipoImp) {
            if (((JComboBox) evt.getSource()).getSelectedIndex() == 0) {
              txtTipoImp.setVlrString("");
            }  
            else {
              txtTipoImp.setVlrString(""+((JComboBox) evt.getSource()).getSelectedIndex());
            }
          }
        }
      }
    );
    
    vLabs.addElement("Nota fiscal");           
    vLabs.addElement("Nota fiscal - serviço");           
    vLabs.addElement("Pedido");           
    vLabs.addElement("Relatório simples");           
    vLabs.addElement("Relatório gráfico");           
    vLabs.addElement("Todos (não NF)");           
               
    vVals.addElement("NF");           
    vVals.addElement("NS");           
    vVals.addElement("PD");           
    vVals.addElement("RS");           
    vVals.addElement("RG");           
    vVals.addElement("TO");           
    
    cbDestImp = new JComboBoxPad(vLabs,vVals);
    cbDestImp.setVlrString("TO");

    txtTipoImp.setEditable(false);
    txtTipoImp.addActionListener( 
      new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
          if (evt.getSource() == txtTipoImp) {
            if (txtTipoImp.getText().trim().length() == 1) {
              cbTipoImp.setSelectedIndex(Integer.parseInt(txtTipoImp.getText().trim()));
            }
            else 
              cbTipoImp.setSelectedIndex(0);
          }
        }
      }
    );
//Prepara FKs
    txtCodPapel.setTipo(JTextFieldPad.TP_STRING,20,0);
    txtDescPapel.setTipo(JTextFieldPad.TP_STRING,40,0);    

    lcPapel.add(new GuardaCampo( txtCodPapel, 7, 100, 80, 20, "CodPapel", "Cód.tp.papel", true, false, null, JTextFieldPad.TP_STRING,true),"txtCodPapelx");
    lcPapel.add(new GuardaCampo( txtDescPapel, 90, 100, 207, 20, "DescPapel", "Descrição do tipo de papel", false, false, null, JTextFieldPad.TP_STRING,false),"txtDescPapelx");
    lcPapel.montaSql(false, "PAPEL", "SG");    
    lcPapel.setQueryCommit(false);
    lcPapel.setReadOnly(true);
    txtCodPapel.setTabelaExterna(lcPapel);
//Adiciona componentes   
    adicCampo(txtCodImp, 7, 20, 90, 20, "CodImp", "Cód.imp.", JTextFieldPad.TP_INTEGER, 8, 0, true, false, null, true);
    adicCampo(txtDescImp, 100, 20, 276, 20, "DescImp", "Descrição da impressora", JTextFieldPad.TP_STRING, 40, 0, false, false, null, true);
    adicCampo(txtTipoImp, 7, 60, 90, 20, "TipoImp", "Tp.impressora", JTextFieldPad.TP_STRING, 2, 0, false, false, null, true);
    pinDados.adic(cbTipoImp,100,60,276,20);
    adicCampo(txtLinPagImp, 7, 100, 90, 20, "LinPagImp", "Lin.pag.imp.", JTextFieldPad.TP_INTEGER, 8, 0, false, false, null, true);
    adicCampo(txtNSerieImp, 100, 100, 90, 20, "NSerieImp", "Num. serie", JTextFieldPad.TP_STRING, 15, 0, false, false, null, false);
    adicCampo(txtPortaWinImp, 193, 100, 90, 20, "PortaWinImp", "Porta WIN", JTextFieldPad.TP_STRING, 4, 0, false, false, null, true);
    adicCampo(txtPortaLinImp, 286, 100, 90, 20, "PortaLinImp", "Nome LIN", JTextFieldPad.TP_STRING, 60, 0, false, false, null, true);
    adicCampo(txtCodPapel, 7, 140, 90, 20, "CodPapel", "Cód.tp.papel", JTextFieldPad.TP_STRING, 20, 0, false, true, null, true);
    adicDescFK(txtDescPapel, 100, 140, 276, 20, "DescPapel", "Descrição do tipo de papel", JTextFieldPad.TP_STRING, 40, 0);
    adicDB(cbDestImp, 7, 180, 200, 25, "DestImp", "Padrão para",JTextFieldPad.TP_STRING,true);

    setListaCampos(true, "IMPRESSORA", "SG");
  }
  public void execShow(Connection cn) {
    lcPapel.setConexao(cn);      
    super.execShow(cn);
  }
}
