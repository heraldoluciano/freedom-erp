/**
 * @version 02/11/2003 <BR>
 * @author Setpoint Informática Ltda./Carlos Eduardo Caetano David <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.atd <BR>
 * Classe: @(#)FEncaminhador.java <BR>
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

package org.freedom.modulos.atd;
import java.sql.Connection;

import org.freedom.acao.PostEvent;
import org.freedom.acao.PostListener;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.FDados;

public class FEncaminhador extends FDados implements PostListener { 
  private JTextFieldPad txtCodEnc = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 5, 0);
  private JTextFieldPad txtNomeEnc = new JTextFieldPad(JTextFieldPad.TP_STRING, 40, 0);
  private JTextFieldPad txtEndEnc = new JTextFieldPad(JTextFieldPad.TP_STRING, 50, 0);
  private JTextFieldPad txtNumEnc = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
  private JTextFieldPad txtBairEnc = new JTextFieldPad(JTextFieldPad.TP_STRING, 30, 0);
  private JTextFieldPad txtComplEnc = new JTextFieldPad(JTextFieldPad.TP_STRING, 20, 0);
  private JTextFieldPad txtCidEnc = new JTextFieldPad(JTextFieldPad.TP_STRING, 30, 0);
  private JTextFieldPad txtCepEnc = new JTextFieldPad(JTextFieldPad.TP_STRING, 8, 0);
  private JTextFieldPad txtFoneEnc = new JTextFieldPad(JTextFieldPad.TP_STRING, 12, 0);
  private JTextFieldPad txtFaxEnc = new JTextFieldPad(JTextFieldPad.TP_STRING, 8, 0);
  private JTextFieldPad txtUFEnc = new JTextFieldPad(JTextFieldPad.TP_STRING, 2, 0);
  public FEncaminhador () {
  	super();
    setTitulo("Cadastro de Encaminhador");
    setAtribos( 50, 50, 400, 250);

   /* lcPlan.add(new GuardaCampo( txtCodPlan, 7, 100, 80, 20, "CodPlan", "Código", true, false, txtDescPlan, JTextFieldPad.TP_STRING,false),"txtCodVendx");
    lcPlan.add(new GuardaCampo( txtDescPlan, 7, 100, 80, 20, "DescPlan", "Descriçao", false, false, null, JTextFieldPad.TP_STRING,false),"txtCodVendx");
    lcPlan.setWhereAdic("TIPOPLAN = 'D'");
    lcPlan.montaSql(false, "PLANEJAMENTO", "FN");    
    lcPlan.setQueryCommit(false);
    lcPlan.setReadOnly(true);
    txtCodPlan.setTabelaExterna(lcPlan);
    */
    adicCampo(txtCodEnc, 7, 20, 50, 20, "CodEnc", "Cód.enc.", ListaCampos.DB_PK, true);
    adicCampo(txtNomeEnc, 60, 20, 312, 20, "NomeEnc", "Descrição do encaminhador", ListaCampos.DB_SI, true);
    adicCampo(txtEndEnc, 7, 60, 260, 20, "EndEnc", "Endereço",ListaCampos.DB_SI, false);
    adicCampo(txtNumEnc, 270, 60, 50, 20, "NumEnc", "Num.", ListaCampos.DB_SI, false);
    adicCampo(txtComplEnc, 323, 60, 49, 20, "ComplEnc", "Compl.", ListaCampos.DB_SI, false);
    adicCampo(txtBairEnc, 7, 100, 120, 20, "BairEnc", "Bairro", ListaCampos.DB_SI, false);
    adicCampo(txtCidEnc, 130, 100, 120, 20, "CidEnc", "Cidade",  ListaCampos.DB_SI, false);
    adicCampo(txtCepEnc, 253, 100, 80, 20, "CepEnc", "Cep", ListaCampos.DB_SI, false);
    adicCampo(txtUFEnc, 336, 100, 36, 20, "UFEnc", "UF", ListaCampos.DB_SI, false);
    adicCampo(txtFoneEnc, 7, 140, 120, 20, "FoneEnc", "Telefone", ListaCampos.DB_SI, false);
    adicCampo(txtFaxEnc, 130, 140, 120, 20, "FaxEnc", "Fax", ListaCampos.DB_SI, false);
   // adicCampo(txtCelVend, 253, 220, 119, 20, "CelEnc", "Cel", JTextFieldPad.TP_STRING, 12, 0, false, false, null, false);
    //adicCampo(txtCodPlan, 7, 300, 100, 20, "CodPlan", "Código", JTextFieldPad.TP_STRING, 13, 0, false, true, txtDescPlan,false);
    //adicDescFK(txtDescPlan, 110, 300, 262, 20, "DescPlan", "e descrição do lançamento", JTextFieldPad.TP_STRING, 50, 0);
        
    txtFoneEnc.setMascara(JTextFieldPad.MC_FONEDDD);   
    txtFaxEnc.setMascara(JTextFieldPad.MC_FONE);
    lcCampos.addPostListener(this);
    lcCampos.setQueryInsert(false);    
  }

  public void beforePost(PostEvent pevt) {
        /*  if (txtInscEnc.getText().trim().length() < 1) {
                if (Funcoes.mensagemConfirma(this, "Inscrição Estadual em branco! Inserir ISENTO?")==0 )
                        txtInscVend.setVlrString("ISENTO");
                else {
                        pevt.cancela();
                        txtInscVend.requestFocus();
                }
          }
          else if (txtInscVend.getText().trim().toUpperCase().compareTo("ISENTO") == 0)
                return;*/
          if (txtUFEnc.getText().trim().length() < 2) {
                pevt.cancela();
                Funcoes.mensagemInforma( this,"Campo UF é requerido! ! !");
                txtUFEnc.requestFocus();
          }
          /*else if (Funcoes.vIE(txtInscVend.getText(),txtUFVend.getText()))
          	 txtInscVend.setVlrString(Funcoes.sIEValida);
          else {
          	    pevt.cancela();
                Funcoes.mensagemInforma( this,"Inscrição Estadual Inválida ! ! !");
                txtInscVend.requestFocus();
          }*/
  }
  
  public void setConexao(Connection cn) {
  	super.setConexao(cn);
	setListaCampos( true, "ENCAMINHADOR", "AT");
  }
}
