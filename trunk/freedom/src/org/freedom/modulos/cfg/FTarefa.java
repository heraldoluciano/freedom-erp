/**
 * @version 12/08/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.cfg <BR>
 * Classe: @(#)FTarefa.java <BR>
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

package org.freedom.modulos.cfg;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JComboBoxPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.FDados;

public class FTarefa extends FDados implements ActionListener {
  private JTextFieldPad txtCodTarefa = new JTextFieldPad(5);
  private JTextFieldPad txtDescTarefa = new JTextFieldPad(50);
  private JTextFieldPad txtIDObj = new JTextFieldPad(JTextFieldPad.TP_STRING,30,0);
  private JTextFieldFK txtDescObj = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
  private Vector vValsTipo = new Vector();
  private Vector vLabsTipo = new Vector();
  private JComboBoxPad cbTipo = new JComboBoxPad(vLabsTipo, vValsTipo, JComboBoxPad.TP_STRING, 2, 0); 
  private ListaCampos lcObjeto = new ListaCampos(this,"OB");
  public FTarefa () {
    setTitulo("Cadastro de tarefas");
    setAtribos( 50, 50, 330, 205);
    
    
//Montando tipos:

	vValsTipo.add("01");
	vValsTipo.add("02");
	vValsTipo.add("03");
	vValsTipo.add("04");
	vValsTipo.add("05");
	vLabsTipo.add("Consulta");
	vLabsTipo.add("Insere");
	vLabsTipo.add("Atualiza");
	vLabsTipo.add("Exclui");
	vLabsTipo.add("Executa");
    cbTipo.setItens(vLabsTipo,vValsTipo);
    
    
    lcObjeto.setUsaFI(false);
	lcObjeto.add(new GuardaCampo( txtIDObj, "IDObj", "Id.obj.", ListaCampos.DB_PK,true));
	lcObjeto.add(new GuardaCampo( txtDescObj, "DescObj", "Descrição", ListaCampos.DB_SI,false));
	lcObjeto.montaSql(false, "OBJETO", "SG");
	lcObjeto.setQueryCommit(false);
	lcObjeto.setReadOnly(true);
	txtIDObj.setTabelaExterna(lcObjeto);
    
    adicCampo(txtCodTarefa, 7, 20, 50, 20,"CodTarefa","Cód.tar.",ListaCampos.DB_PK,true);
    adicCampo(txtDescTarefa, 60, 20, 250, 20,"DescTarefa","Descrição da tarefa",ListaCampos.DB_FK,true);
	adicCampo(txtIDObj, 7, 65, 50, 20,"IDObj","Id.obj.",ListaCampos.DB_PK,true);
	adicDescFK(txtDescObj, 60, 65, 250, 20,"DescObj","Descrição do objeto");
	adicDB(cbTipo, 7, 105, 230, 25, "TipoTarefa", "Tipo", true);
    setListaCampos( true, "TAREFA", "SG");
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
	lcObjeto.setConexao(cn);
	super.execShow(cn);
  }
  private void imprimir(boolean bVisualizar) {
    ImprimeOS imp = new ImprimeOS("",con);
    int linPag = imp.verifLinPag()-1;
    imp.montaCab();
    imp.setTitulo("Relatório de tarefas");
    DLRTarefa dl = new DLRTarefa();
    dl.setVisible(true);
    if (dl.OK == false) {
      dl.dispose();
      return;
    }
    String sSQL = "SELECT CODTAREFA,DESCTAREFA FROM SGTAREFA ORDER BY "+dl.getValor();
    PreparedStatement ps = null;
    ResultSet rs = null;
    try {
      ps = con.prepareStatement(sSQL);
      rs = ps.executeQuery();
      imp.limpaPags();
      while ( rs.next() ) {
         if (imp.pRow()==0) {
            imp.impCab(80);
            imp.say(imp.pRow()+0,0,""+imp.normal());
            imp.say(imp.pRow()+0,0,"");
            imp.say(imp.pRow()+0,2,"Código");
            imp.say(imp.pRow()+0,25,"Descrição");
            imp.say(imp.pRow()+1,0,""+imp.normal());
            imp.say(imp.pRow()+0,0,Funcoes.replicate("-",80));
         }
         imp.say(imp.pRow()+1,0,""+imp.normal());
         imp.say(imp.pRow()+0,2,rs.getString("CodTarefa"));
         imp.say(imp.pRow()+0,25,rs.getString("DescTarefa"));
         if (imp.pRow()>=linPag) {
            imp.incPags();
            imp.eject();
         }
      }
      
      imp.say(imp.pRow()+1,0,""+imp.normal());
      imp.say(imp.pRow()+0,0,Funcoes.replicate("=",80));
      imp.eject();
      
      imp.fechaGravacao();
      
//      rs.close();
//      ps.close();
      if (!con.getAutoCommit())
      	con.commit();
      dl.dispose();
    }  
    catch ( SQLException err ) {
		Funcoes.mensagemErro(this,"Erro consulta tabela de tarefas!"+err.getMessage());      
    }
    
    if (bVisualizar) {
      imp.preview(this);
    }
    else {
      imp.print();
    }
  }
}
