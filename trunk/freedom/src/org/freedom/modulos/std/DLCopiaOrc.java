/**
 * @version 26/01/2004 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)DLCopiaOrc.java <BR>
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

import javax.swing.JLabel;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.FFDialogo;

public class DLCopiaOrc extends FFDialogo {
  private JTextFieldPad txtCodCli = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldFK txtRazCli = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
  private ListaCampos lcCli = new ListaCampos(this,"");
  public DLCopiaOrc(Component cOrig) {
  	super(cOrig);
    setTitulo("Cópia de orçamento");
    setAtribos(320,130);
    
    lcCli.add(new GuardaCampo( txtCodCli, 7,55, 67, 20, "CodCli", "Cód.Forn.", true, false, null , JTextFieldPad.TP_INTEGER,true),"txtCodFor");
    lcCli.add(new GuardaCampo( txtRazCli, 78, 55, 150, 20, "RazCli", "Razão Social", false, false, null, JTextFieldPad.TP_STRING,false),"txtRazFor");
    lcCli.montaSql(false, "CLIENTE", "VD");
    lcCli.setReadOnly(true);
    txtCodCli.setTabelaExterna(lcCli);
    txtCodCli.setFK(true);
    txtCodCli.setNomeCampo("CodCli");
    
    adic(new JLabel("Código e razão do cliente"),7,5,200,20);
    adic(txtCodCli,7,25,80,20);
    adic(txtRazCli,90,25,200,20);
    
  }
  public void actionPerformed(ActionEvent evt) {
    if (evt.getSource() == btOK) {
      if (txtCodCli.getText().trim().length() == 0) {
		Funcoes.mensagemInforma(this,"O campo cliente está em branco! ! !");
        txtCodCli.requestFocus();
        return;
      }
    }
    super.actionPerformed(evt);
  }
  public void setConexao(Connection cn) {
  	lcCli.setConexao(cn);
  }
  public int[] getValores() {
    int iRet[] = {txtCodCli.getVlrInteger().intValue(),lcCli.getCodFilial()};
    return iRet;
  }
}

