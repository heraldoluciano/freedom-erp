/**
 * @version 11/08/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.cfg <BR>
 * Classe: @(#)FProcesso.java <BR>
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
import javax.swing.JButton;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Painel;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.FDetalhe;
import org.freedom.telas.FPrincipal;
import org.freedom.bmps.Icone;
public class FProcesso extends FDetalhe implements ActionListener {
  private JTextFieldPad txtCodProc = new JTextFieldPad();
  private JTextFieldPad txtDescProc = new JTextFieldPad();
  private JTextFieldPad txtCodTar = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldFK txtDescTar = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldPad txtCodItem = new JTextFieldPad();
  private ListaCampos lcTarefa = new ListaCampos(this,"TA");
  private JButton btTrat = new JButton(Icone.novo("btRetorno.gif"));
  private Painel pinCab = new Painel();
  private Painel pinDet = new Painel();
  FPrincipal fPrim = null;
  public FProcesso() {

   btTrat.setToolTipText("Tratamento de Retorno");

   setTitulo("Cadastro Processos");
   setAtribos( 50, 50, 450, 350);

   setAltCab(90);
   pinCab = new Painel(420,90);
   setListaCampos(lcCampos);
   setPainel( pinCab, pnCliCab);

   lcTarefa.add(new GuardaCampo( txtCodTar, 7, 100, 80, 20, "CodTarefa", "Código", true, false, null, JTextFieldPad.TP_INTEGER,true),"txtCodProc");
   lcTarefa.add(new GuardaCampo( txtDescTar, 90, 100, 207, 20, "DescTarefa", "Descrição", false, false, null, JTextFieldPad.TP_STRING,false),"txtDescProc");
   lcTarefa.montaSql(false, "TAREFA", "SG");
   lcTarefa.setQueryCommit(false);
   lcTarefa.setReadOnly(true);
   txtCodTar.setTabelaExterna(lcTarefa);
    
   adicCampo(txtCodProc, 7, 20, 50, 20,"CodProc","Código",JTextFieldPad.TP_INTEGER,5,0,true,false,null,true);
   adicCampo(txtDescProc, 60, 20, 250, 20,"DescProc","Descrição",JTextFieldPad.TP_STRING,50,0,false,false,null,true);
   setListaCampos( true, "PROCESSO", "SG");

   setAltDet(60);
   setPainel( pinDet, pnDet);
   setListaCampos(lcDet);
   setNavegador(navRod);

   adicCampo(txtCodItem, 7, 20, 40, 20,"SeqItProc","Item",JTextFieldPad.TP_INTEGER,8,0,true,false,null,true);
   adicCampo(txtCodTar, 50, 20, 77, 20,"CodTarefa","Código",JTextFieldPad.TP_INTEGER,8,0,false,true,txtDescTar,true);
   adicDescFK(txtDescTar, 130, 20, 200, 20,"DescTar","e descrição da tarefa",JTextFieldPad.TP_STRING,50,0);
   setListaCampos( true, "ITPROCESSO", "SG");
   
   adic(btTrat,340,15,60,30);
   
   montaTab();    
   tab.setTamColuna(40,0); 
   tab.setTamColuna(80,1); 
   tab.setTamColuna(200,2);

   btImp.addActionListener(this);
   btPrevimp.addActionListener(this);  
   btTrat.addActionListener(this);
   
  }
  private void abreTrat() {
	if (fPrim.temTela("Orcamento")==false) {
	  FTratRet tela = new FTratRet(txtCodProc.getVlrInteger().intValue(),txtCodItem.getVlrInteger().intValue());
	  fPrim.criatela("Orcamento",tela,con);
	  tela.setConexao(con);
    }
  }
  public void actionPerformed(ActionEvent evt) {
    if (evt.getSource() == btPrevimp) {
        imprimir(true);
    }
    else if (evt.getSource() == btImp) 
      imprimir(false);
	else if (evt.getSource() == btTrat) 
	  abreTrat();
    super.actionPerformed(evt);
  }

  public void execShow(Connection cn) {
	lcTarefa.setConexao(cn);
	super.execShow(cn);
  }
  private void imprimir(boolean bVisualizar) {
    ImprimeOS imp = new ImprimeOS("",con);
    int linPag = imp.verifLinPag()-1;
    imp.montaCab();
    imp.setTitulo("Relatório de Processos");
    DLRProcesso dl = new DLRProcesso();
    dl.show();
    if (dl.OK == false) {
      dl.dispose();
      return;
    }
    String sSQL = "SELECT CODPROC,DESCPROC FROM ATPROCESSO ORDER BY "+dl.getValor();
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
         imp.say(imp.pRow()+0,2,rs.getString("CodProc"));
         imp.say(imp.pRow()+0,25,rs.getString("DescProc"));
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
		Funcoes.mensagemErro(this,"Erro consulta tabela de Processos!\n"+err.getMessage());      
    }
    
    if (bVisualizar) {
      imp.preview(this);
    }
    else {
      imp.print();
    }
  }
  public void setTelaPrim(FPrincipal fP) {
	  fPrim = fP;
  }
}
