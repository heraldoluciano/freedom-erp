/**
 * @version 02/11/2003 <BR>
 * @author Setpoint Informática Ltda./Anderson Sanchez <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.grh <BR>
 * Classe: @(#)FEmpregado.java <BR>
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
 * Tela de cadastro de empregados.
 * 
 */

package org.freedom.modulos.grh;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.FDados;
public class FEmpregado extends FDados implements ActionListener {
  private JTextFieldPad txtCod= new JTextFieldPad(5);
  private JTextFieldPad txtDesc= new JTextFieldPad(20);
  public FEmpregado () {
    setTitulo("Cadastro de Empregados");
    setAtribos(50, 50, 350, 125);
    adicCampo(txtCod, 7, 20, 50, 20,"CodEmpr","Cód.Empr.",JTextFieldPad.TP_INTEGER,5,0,true,false,null,true);
    adicCampo(txtDesc, 60, 20, 250, 20,"NomeEmpr","Nome do empregado",JTextFieldPad.TP_STRING,40,0,false,false,null,true);
    setListaCampos( true, "EMPREGADO", "RH");
    btImp.addActionListener(this);
    btPrevimp.addActionListener(this);
    lcCampos.setQueryInsert(false);
  }
  public void actionPerformed(ActionEvent evt) {
    if (evt.getSource() == btPrevimp) {      	
        imprimir(true);
    }
    else if (evt.getSource() == btImp) 
      imprimir(false);
    super.actionPerformed(evt);
  }

  private void imprimir(boolean bVisualizar) {
      Funcoes.mensagemInforma(this,"Opção indisponível nesta versão...");
  }
}
