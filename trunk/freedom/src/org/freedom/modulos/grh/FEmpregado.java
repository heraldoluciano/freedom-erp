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
import java.sql.Connection;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.FDados;
public class FEmpregado extends FDados implements ActionListener {
  private JTextFieldPad txtCod = new JTextFieldPad(5);
  private JTextFieldPad txtCodFuncao = new JTextFieldPad(5);
  private JTextFieldPad txtCodTurno = new JTextFieldPad(5);
  private JTextFieldPad txtCodDepto = new JTextFieldPad(5);
  private JTextFieldFK  txtDescFuncao = new JTextFieldFK();
  private JTextFieldFK  txtDescTurno = new JTextFieldFK();
  private JTextFieldFK  txtDescDepto = new JTextFieldFK();
  private JTextFieldPad txtDesc= new JTextFieldPad(20);
  private ListaCampos lcFuncao = new ListaCampos(this,"FU");
  private ListaCampos lcTurno = new ListaCampos(this,"TU");
  private ListaCampos lcDepto = new ListaCampos(this,"DP");
  public FEmpregado () {
    setTitulo("Cadastro de Empregados");
    setAtribos(50, 50, 350, 125);
    adicCampo(txtCod, 7, 20, 50, 20,"MatEmpr","Matricula",JTextFieldPad.TP_INTEGER,5,0,true,false,null,true);
    adicCampo(txtDesc, 60, 20, 250, 20,"NomeEmpr","Nome do empregado",JTextFieldPad.TP_STRING,40,0,false,false,null,true);
    adicCampo(txtCodFuncao, 7, 60, 50, 20,"CodFunc","Cód.Func.",JTextFieldPad.TP_INTEGER,5,0,false,true,null,true);    
  	adicDescFK(txtDescFuncao, 90, 60, 237, 20, "DescFunc", "Descrição da função", JTextFieldPad.TP_STRING, 50, 0);
    adicCampo(txtCodTurno, 7, 100, 50, 20,"CodTurno","Cód.Turnos",JTextFieldPad.TP_INTEGER,5,0,false,true,null,true);    
  	adicDescFK(txtDescTurno, 90, 100, 237, 20, "DescFunc", "Descrição da função", JTextFieldPad.TP_STRING, 50, 0);
    adicCampo(txtCodDepto, 7, 140, 50, 20,"CodDepto","Cód.Depto.",JTextFieldPad.TP_INTEGER,5,0,false,true,null,true);    
  	adicDescFK(txtDescDepto, 90, 140, 237, 20, "DescDepto", "Descrição do departamento", JTextFieldPad.TP_STRING, 50, 0);

  	
  	
  	lcFuncao.add(new GuardaCampo( txtCodFuncao, 7, 100, 80, 20, "CodFunc", "Cód.Func.", true, false, null, JTextFieldPad.TP_INTEGER,true));
  	lcFuncao.add(new GuardaCampo( txtDescFuncao, 90, 100, 207, 20, "DescFunc", "Descrição da função", false, false, null, JTextFieldPad.TP_STRING,false));
  	lcFuncao.montaSql(false, "FUNCAO", "RH");    
  	lcFuncao.setQueryCommit(false);
  	lcFuncao.setReadOnly(true);
  	txtCodFuncao.setTabelaExterna(lcFuncao);

  	lcTurno.add(new GuardaCampo( txtCodTurno, 7, 100, 80, 20, "CodTurno", "Cód.turno.", true, false, null, JTextFieldPad.TP_INTEGER,true));
  	lcTurno.add(new GuardaCampo( txtDescTurno, 90, 100, 207, 20, "DescTurno", "Descrição do turno", false, false, null, JTextFieldPad.TP_STRING,false));
  	lcTurno.montaSql(false, "TURNO", "RH");    
  	lcTurno.setQueryCommit(false);
  	lcTurno.setReadOnly(true);
  	txtCodTurno.setTabelaExterna(lcTurno);
  	
  	lcDepto.add(new GuardaCampo( txtCodDepto, 7, 100, 80, 20, "CodTurno", "Cód.turno.", true, false, null, JTextFieldPad.TP_INTEGER,true));
  	lcDepto.add(new GuardaCampo( txtDescDepto, 90, 100, 207, 20, "DescTurno", "Descrição do turno", false, false, null, JTextFieldPad.TP_STRING,false));
  	lcDepto.montaSql(false, "TURNO", "RH");    
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
