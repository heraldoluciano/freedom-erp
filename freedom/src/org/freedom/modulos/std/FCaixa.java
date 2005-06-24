/**
 * @version 02/11/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FCaixa.java <BR>
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
import java.awt.event.ActionListener;
import java.sql.Connection;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.telas.FDados;

public class FCaixa extends FDados implements ActionListener {
  private ListaCampos lcEst = new ListaCampos(this,"ET");
  private JTextFieldPad txtCodCaixa = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtDescCaixa = new JTextFieldPad(JTextFieldPad.TP_STRING,40,0);
  private JTextFieldPad txtCodEst = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldFK txtDescEst = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
  private JCheckBoxPad cbECF = new JCheckBoxPad("Cupom fiscal?","S","N");
  private JCheckBoxPad cbTEF = new JCheckBoxPad("Possui Gerenciador Padrão?","S","N");
  public FCaixa() {
  	super();
    setTitulo("Cadastro de caixa PDV");
    setAtribos( 50, 50, 420, 200);

  	lcEst.add(new GuardaCampo( txtCodEst, "CodEst", "Cód.est.", ListaCampos.DB_PK, false));
  	lcEst.add(new GuardaCampo( txtDescEst, "DescEst", "Descrição da estação de trabalho", ListaCampos.DB_SI,false));
  	lcEst.montaSql(false, "ESTACAO", "SG");    
  	lcEst.setQueryCommit(false);
  	lcEst.setReadOnly(true);
  	txtCodEst.setTabelaExterna(lcEst);
    
    cbECF.setVlrString("N");
    cbTEF.setVlrString("N");
    adicCampo(txtCodCaixa, 7, 20, 70, 20, "CodCaixa", "Cód.caixa", ListaCampos.DB_PK, true);
    adicCampo(txtDescCaixa, 80, 20, 310, 20, "DescCaixa", "Descrição do caixa", ListaCampos.DB_SI, true);
    adicCampo(txtCodEst, 7, 60, 70, 20, "CodEst", "Cód.est.", ListaCampos.DB_FK, txtDescEst, true);
    adicDescFK(txtDescEst, 80, 60, 310, 20, "DescEst", "Descrição da estação de trabalho");
    adicDB(cbECF, 7, 100, 150, 20, "ECFCaixa", "ECF",true);
    adicDB(cbTEF, 160, 100, 230, 20, "TEFCaixa", "TEF",true);
    setListaCampos( true, "CAIXA", "PV");
  }
  
  public void setConexao(Connection cn) {
  	super.setConexao(cn); 
  	lcEst.setConexao(cn);
  }

}
