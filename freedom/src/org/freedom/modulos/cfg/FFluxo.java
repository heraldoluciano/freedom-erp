/**
 * @version 11/08/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.cfg <BR>
 * Classe: @(#)FFluxo.java <BR>
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

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Painel;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.FDetalhe;
public class FFluxo extends FDetalhe implements ActionListener {
  private JTextFieldPad txtCodFluxo = new JTextFieldPad();
  private JTextFieldPad txtDescFluxo = new JTextFieldPad();
  private JTextFieldPad txtCodProc = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldFK txtDescProc = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldPad txtCodItem = new JTextFieldPad();
  private ListaCampos lcProc = new ListaCampos(this,"PC");
  private Painel pinCab = new Painel();
  private Painel pinDet = new Painel();
  
  public FFluxo () {

   setTitulo("Cadastro Fluxos");
   setAtribos( 50, 50, 450, 350);

   setAltCab(90);
   pinCab = new Painel(420,90);
   setListaCampos(lcCampos);
   setPainel( pinCab, pnCliCab);

   lcProc.add(new GuardaCampo( txtCodProc, 7, 100, 80, 20, "CodProc", "Código", true, false, null, JTextFieldPad.TP_INTEGER,true),"txtCodProc");
   lcProc.add(new GuardaCampo( txtDescProc, 90, 100, 207, 20, "DescProc", "Descrição", false, false, null, JTextFieldPad.TP_STRING,false),"txtDescProc");
   lcProc.montaSql(false, "PROCESSO", "SG");
   lcProc.setQueryCommit(false);
   lcProc.setReadOnly(true);
   txtCodProc.setTabelaExterna(lcProc);
    
   adicCampo(txtCodFluxo, 7, 20, 50, 20,"CodFluxo","Código",JTextFieldPad.TP_INTEGER,5,0,true,false,null,true);
   adicCampo(txtDescFluxo, 60, 20, 250, 20,"DescFluxo","Descrição",JTextFieldPad.TP_STRING,50,0,false,false,null,true);
   setListaCampos( true, "FLUXO", "SG");

   setAltDet(60);
   setPainel( pinDet, pnDet);
   setListaCampos(lcDet);
   setNavegador(navRod);

   adicCampo(txtCodItem, 7, 20, 40, 20,"SeqItFluxo","Item",JTextFieldPad.TP_INTEGER,8,0,true,false,null,true);
   adicCampo(txtCodProc, 50, 20, 77, 20,"CodProc","Código",JTextFieldPad.TP_INTEGER,8,0,false,true,txtDescProc,true);
   adicDescFK(txtDescProc, 130, 20, 200, 20,"DescProc","e descrição do processo",JTextFieldPad.TP_STRING,50,0);
   setListaCampos( true, "ITFLUXO", "SG");
   
   montaTab();    
   tab.setTamColuna(40,0); 
   tab.setTamColuna(45,1); 
   tab.setTamColuna(350,2);

   btImp.addActionListener(this);
   btPrevimp.addActionListener(this);  
  
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
	lcProc.setConexao(cn);
	super.execShow(cn);
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
