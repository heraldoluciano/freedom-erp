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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.FDados;
public class FEmpregado extends FDados implements ActionListener {
  private JTextFieldPad txtCod = new JTextFieldPad(JTextFieldPad.TP_INTEGER,5,0);
  private JTextFieldPad txtCodFuncao = new JTextFieldPad(JTextFieldPad.TP_INTEGER,5,0);
  private JTextFieldPad txtCodTurno = new JTextFieldPad(JTextFieldPad.TP_INTEGER,5,0);
  private JTextFieldPad txtCodDepto = new JTextFieldPad(JTextFieldPad.TP_INTEGER,5,0);
  private JTextFieldFK  txtDescFuncao = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);
  private JTextFieldFK  txtDescTurno = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);
  private JTextFieldFK  txtDescDepto = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);
  private JTextFieldPad txtDesc= new JTextFieldPad(JTextFieldPad.TP_STRING,40,0);
  private ListaCampos lcFuncao = new ListaCampos(this,"FU");
  private ListaCampos lcTurno = new ListaCampos(this,"TU");
  private ListaCampos lcDepto = new ListaCampos(this,"DP");
  public FEmpregado () {
    setTitulo("Cadastro de Empregados");
    setAtribos(50, 50, 500, 400);
    adicCampo(txtCod, 7, 20, 80, 20,"MatEmpr","Matricula", ListaCampos.DB_PK, true);
    adicCampo(txtDesc, 90, 20, 260, 20,"NomeEmpr","Nome do empregado", ListaCampos.DB_SI, true);
    adicCampo(txtCodFuncao, 7, 60, 80, 20,"CodFunc","Cód.Func.", ListaCampos.DB_FK, true);    
  	adicDescFK(txtDescFuncao, 90, 60, 260, 20, "DescFunc", "Descrição da função");
    adicCampo(txtCodTurno, 7, 100, 80, 20,"CodTurno","Cód. Turno", ListaCampos.DB_FK, true);    
  	adicDescFK(txtDescTurno, 90, 100, 260, 20, "DescTurno", "Descrição do turno");
    adicCampo(txtCodDepto, 7, 140, 80, 20,"CodDep","Cód.Depto.", ListaCampos.DB_FK, true);    
  	adicDescFK(txtDescDepto, 90, 140, 260, 20, "DescDepto", "Descrição do departamento");

  	
  	
  	lcFuncao.add(new GuardaCampo( txtCodFuncao, "CodFunc", "Cód.Func.", ListaCampos.DB_PK, true));
  	lcFuncao.add(new GuardaCampo( txtDescFuncao, "DescFunc", "Descrição da função", ListaCampos.DB_SI, false));
  	lcFuncao.montaSql(false, "FUNCAO", "RH");    
  	lcFuncao.setQueryCommit(false);
  	lcFuncao.setReadOnly(true);
  	txtCodFuncao.setTabelaExterna(lcFuncao);

  	lcTurno.add(new GuardaCampo( txtCodTurno, "CodTurno", "Cód. turno", ListaCampos.DB_PK, true));
  	lcTurno.add(new GuardaCampo( txtDescTurno, "DescTurno", "Descrição do turno", ListaCampos.DB_SI, false));
  	lcTurno.montaSql(false, "TURNO", "RH");    
  	lcTurno.setQueryCommit(false);
  	lcTurno.setReadOnly(true);
  	txtCodTurno.setTabelaExterna(lcTurno);
  	
  	lcDepto.add(new GuardaCampo( txtCodDepto, "CodDep", "Cód.Depto.", ListaCampos.DB_PK, true));
  	lcDepto.add(new GuardaCampo( txtDescDepto, "DescDep", "Descrição do departamento", ListaCampos.DB_SI,false));
  	lcDepto.montaSql(false, "DEPTO", "RH");    
  	lcDepto.setQueryCommit(false);
  	lcDepto.setReadOnly(true);
  	txtCodDepto.setTabelaExterna(lcDepto);
  	
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
  
  public void execShow(Connection cn) {
  	con = cn;	
    lcFuncao.setConexao(cn);      
	lcTurno.setConexao(cn);      
	lcDepto.setConexao(cn);
    super.execShow(cn);
  }

  private void imprimir(boolean bVisualizar) {
      Funcoes.mensagemInforma(this,"Opção indisponível nesta versão...");
  }
}
