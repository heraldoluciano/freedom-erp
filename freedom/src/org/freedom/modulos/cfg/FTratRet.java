/**
 * @version 12/08/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.cfg <BR>
 * Classe: @(#)FTratRet.java <BR>
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
import org.freedom.componentes.Painel;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.FDetalhe;

public class FTratRet extends FDetalhe implements ActionListener {
  private JTextFieldPad txtCodProc = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtCodTar = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtCodTar2 = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldFK txtDescTar = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldPad txtCodItem = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtCodRet = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtCodItProc = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private Painel pinCab = new Painel();
  private Painel pinDet = new Painel();
  private Vector vValsTipo = new Vector();
  private Vector vLabsTipo = new Vector();
  private JComboBoxPad cbTipo = new JComboBoxPad(vLabsTipo,vValsTipo); 
  private ListaCampos lcTarefa = new ListaCampos(this,"TA");
  private ListaCampos lcItProc = new ListaCampos(this,"GT");
  int iCodProc = 0;
  int iCodItem = 0;
  public FTratRet (int iCodP,int iCodI) {
   iCodProc = iCodP;
   iCodItem = iCodI;

   setTitulo("Tratamento de retornos");
   setAtribos(100, 100, 450, 350);

   setAltCab(90);
   pinCab = new Painel(420,90);
   setListaCampos(lcCampos);
   setPainel( pinCab, pnCliCab);
   
   txtCodProc.setAtivo(false);
   txtCodItem.setAtivo(false);
   txtCodTar.setAtivo(false);

    
// Montando tipos:

	 vValsTipo.add("01");
	 vValsTipo.add("02");
	 vValsTipo.add("03");
	 vLabsTipo.add("IR PARA");
	 vLabsTipo.add("ABORTA");
	 vLabsTipo.add("AGUARDE");
	 cbTipo.setItens(vLabsTipo,vValsTipo);
    
   lcTarefa.add(new GuardaCampo( txtCodTar, "CodTarefa", "Cód.tarefa", ListaCampos.DB_PK, true));
   lcTarefa.add(new GuardaCampo( txtDescTar, "DescTarefa", "Descrição da tarefa", ListaCampos.DB_SI, false));
   lcTarefa.montaSql(false, "TAREFA", "SG");
   lcTarefa.setQueryCommit(false);
   lcTarefa.setReadOnly(true);
   txtCodTar.setTabelaExterna(lcTarefa);
    
   lcItProc.add(new GuardaCampo( txtCodItProc, "SeqItProc", "Item.", ListaCampos.DB_PK, false));
   lcItProc.add(new GuardaCampo( txtCodTar2, "CodTarefa", "Cód.tarefa.", ListaCampos.DB_SI, false));
   lcItProc.setWhereAdic("CodProc="+iCodProc);
   lcItProc.montaSql(false, "ITPROCESSO", "SG");
   lcItProc.setQueryCommit(false);
   lcItProc.setReadOnly(true);
   txtCodItProc.setTabelaExterna(lcItProc);

   adicCampo(txtCodProc, 7, 20, 70, 20,"CodProc","Cód.proc.", ListaCampos.DB_PK, true);
   adicCampo(txtCodItem, 80, 20, 37, 20,"SeqItProc","Item", ListaCampos.DB_PK, true);
   adicCampo(txtCodTar, 120, 20, 70, 20,"CodTarefa","Cód.tarefa", ListaCampos.DB_FK, txtDescTar, true);
   adicDescFK(txtDescTar, 193, 20, 197, 20,"DescTar","Descrição da tarefa");
   setListaCampos( false, "ITPROCESSO", "SG");

   setAltDet(60);
   setPainel( pinDet, pnDet);
   setListaCampos(lcDet);
   setNavegador(navRod);

   adicCampo(txtCodRet, 7, 20, 40, 20,"CodTratRet","Ret.", ListaCampos.DB_PK, true);
   adicDB(cbTipo, 50, 18, 227, 25, "TipoTratRet", "Tipo", true);
   adicCampo(txtCodItProc, 280, 20, 77, 20,"SeqItProcGT","Passo", ListaCampos.DB_FK, false);
   setListaCampos( true, "TRATARET", "SG");
   
   montaTab();    
   tab.setTamColuna(40,0); 
   tab.setTamColuna(80,1); 

   btImp.addActionListener(this);
   btPrevimp.addActionListener(this);  
   
   lcCampos.setReadOnly(true);
  
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
	lcTarefa.setConexao(cn);
	lcItProc.setConexao(cn);
	super.execShow(cn);
	txtCodProc.setVlrInteger(new Integer(iCodProc));
	txtCodItem.setVlrInteger(new Integer(iCodItem));
	lcCampos.carregaDados();
  }
  private void imprimir(boolean bVisualizar) {
    ImprimeOS imp = new ImprimeOS("",con);
    int linPag = imp.verifLinPag()-1;
    imp.montaCab();
    imp.setTitulo("Relatório de Fluxos");
    DLRFluxo dl = new DLRFluxo(this);
    dl.setVisible(true);
    if (dl.OK == false) {
      dl.dispose();
      return;
    }
    String sSQL = "SELECT CODFLUXO,DESCFLUXO FROM ATFLUXO ORDER BY "+dl.getValor();
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
         imp.say(imp.pRow()+0,2,rs.getString("CodFluxo"));
         imp.say(imp.pRow()+0,25,rs.getString("DescFluxo"));
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
		Funcoes.mensagemErro(this,"Erro consulta tabela de fluxos!\n"+err.getMessage());      
    }
    
    if (bVisualizar) {
      imp.preview(this);
    }
    else {
      imp.print();
    }
  }
}
