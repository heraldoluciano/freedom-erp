/**
 * @version 11/11/2004 <BR>
 * @author Setpoint Informática Ltda. Robson Sanchez e Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)DLRInventario.java <BR>
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
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.util.GregorianCalendar;
import java.util.Vector;

import javax.swing.JLabel;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.FFDialogo;

public class DLRInventario extends FFDialogo {

  private JCheckBoxPad cbGrupo = new JCheckBoxPad("Dividir por grupo","S","N");
  private JRadioGroup rgOrdem = null;
  private JTextFieldPad txtCodGrup = new JTextFieldPad(JTextFieldPad.TP_STRING,14,0);
  private JTextFieldFK txtDescGrup = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
  private JLabel lbOrdem = new JLabel("Ordenar por:");
  private JLabel lbGrup = new JLabel("Código e descrição do grupo");
  private Vector vLabs = new Vector();
  private Vector vVals = new Vector();
  private JTextFieldPad txtData = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
  private ListaCampos lcGrup = new ListaCampos(this);
  public DLRInventario(Connection cn,Component cOrig) {
  	super(cOrig);
    setTitulo("Posição do estoque");
    setAtribos(310,250);
    vLabs.addElement("Código");
    vLabs.addElement("Descrição");
    vVals.addElement("C");
    vVals.addElement("D");
    rgOrdem = new JRadioGroup(1,2,vLabs,vVals);
    rgOrdem.setVlrString("D");
    

    lcGrup.add(new GuardaCampo( txtCodGrup, "CodGrup", "Cód.grupo", ListaCampos.DB_PK,false));
    lcGrup.add(new GuardaCampo( txtDescGrup, "DescGrup", "Descrição do grupo", ListaCampos.DB_SI, false));
    lcGrup.montaSql(false, "GRUPO", "EQ");
    lcGrup.setReadOnly(true);
    txtCodGrup.setTabelaExterna(lcGrup);
    txtCodGrup.setFK(true);
    txtCodGrup.setNomeCampo("CodGrup");
    lcGrup.setConexao(cn);
    
    txtData.setRequerido(true);

    GregorianCalendar cal = new GregorianCalendar();
    txtData.setVlrDate(cal.getTime());
    
    adic(new JLabel("Data do estoque"),7,0,280,20);
    adic(txtData,7,20,110,20);
    adic(lbGrup,7,40,280,20);
    adic(txtCodGrup,7,60,80,20);
    adic(txtDescGrup,90,60,200,20);
    adic(lbOrdem,7,80,80,20);
    adic(rgOrdem,7,100,280,30);
    adic(cbGrupo,7,130,280,20);
  }
  public void actionPerformed(ActionEvent evt) {
    if (evt.getSource() == btOK) {
      if (txtData.getVlrString().equals("")) {
        Funcoes.mensagemErro(null,"Data do estoque em branco!");
        txtData.requestFocus();
        return;
      }
    }
    super.actionPerformed(evt);
  }
  public Object[] getValores() {
    Object[] oRetorno = new Object[5];
    if (rgOrdem.getVlrString().compareTo("C") == 0 )
      oRetorno[0] = "CODPROD";
    else if (rgOrdem.getVlrString().compareTo("D") == 0 )
      oRetorno[0] = "DESCPROD";
    oRetorno[1] = txtCodGrup.getVlrString();
    oRetorno[2] = cbGrupo.getVlrString();
    oRetorno[3] = txtData.getVlrDate();
    return oRetorno;
  }
}
