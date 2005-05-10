/**
 * @version 11/02/2002 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FNatoPer.java <BR>
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;

import org.freedom.acao.InsertEvent;
import org.freedom.acao.InsertListener;
import org.freedom.bmps.Icone;
import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JTextAreaPad;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.FDados;

public class FNatoPer extends FDados implements ActionListener, InsertListener {
  private JTextFieldPad txtCodNat = new JTextFieldPad(JTextFieldPad.TP_STRING,4,0);
  private JTextFieldPad txtDescNat = new JTextFieldPad(JTextFieldPad.TP_STRING,60,0);
  private JTextFieldPad txtAliqeNat = new JTextFieldPad(JTextFieldPad.TP_NUMERIC,6,2);
  private JTextFieldPad txtAliqfNat = new JTextFieldPad(JTextFieldPad.TP_NUMERIC,6,2);
  private JTextAreaPad txaTxtNat = new JTextAreaPad(500);
  private JCheckBoxPad cbImpDtSaidaNat = new JCheckBoxPad("Imprimir data de saída na NF?","S","N");
  private JButton btItNatoper = new JButton(Icone.novo("btBrasil.gif"));
  public FNatoPer() {
  	//cbImpDtSaidaNat.set
    setTitulo("Cadastro de Naturezas de Opreção");
    setAtribos( 50, 50, 330, 265);
    adicCampo(txtCodNat, 7, 20, 70, 20,"CodNat","Cód.nat.op.",ListaCampos.DB_PK,true);
    adicCampo(txtDescNat, 80, 20, 230, 20,"DescNat","Descrição da natureza da operação",ListaCampos.DB_SI,true);
    adicCampo(txtAliqeNat, 7, 60, 90, 20,"AliqENat","Aliq.estadual",ListaCampos.DB_SI,false);
    adicCampo(txtAliqfNat, 100, 60, 90, 20,"AliqFNat","Aliq.federal",ListaCampos.DB_SI,false);
    adicDB(cbImpDtSaidaNat, 7, 90, 250,20,"ImpDtSaidaNat","",true);
    adicDB(txaTxtNat,7,130,250,50,"txtNat","Texto completo",false);
    //adicCampo(txaTxtNat,7,130,250,50,"txtNat","Texto completo",JTextFieldPad.TP_STRING,false);
    
    
	adic(btItNatoper,200,48,110,40);

    txtCodNat.setStrMascara("#.###");
    
    setListaCampos( false, "NATOPER", "LF");
    btImp.addActionListener(this);
    btPrevimp.addActionListener(this);
    btItNatoper.addActionListener(this);
    lcCampos.setQueryInsert(false);
    lcCampos.addInsertListener(this);
    setImprimir(true);
  }
  public void actionPerformed(ActionEvent evt) {
    if (evt.getSource() == btPrevimp)
        imprimir(true);
    else if (evt.getSource() == btImp) 
      imprimir(false);
	else if (evt.getSource() == btItNatoper && lcCampos.getStatus() == ListaCampos.LCS_SELECT) {
	  abreItNatoper();    	
	}
	super.actionPerformed(evt);
  }
  private void abreItNatoper() {
	  if (!fPrim.temTela("Item Natoper")) {
		FItNatoper tela = new FItNatoper();
		fPrim.criatela("Item Natoper",tela,con);
		tela.exec(txtCodNat.getVlrString());
	  } 
  }
  private void imprimir(boolean bVisualizar) {
    ImprimeOS imp = new ImprimeOS("",con);
    int linPag = imp.verifLinPag()-1;
    
    DLRNatOper dl = new DLRNatOper();
    dl.setVisible(true);
    if (dl.OK == false) {
      dl.dispose();
      return;
    }
    String sSQL = "SELECT CODNAT,DESCNAT,ALIQENAT,ALIQFNAT FROM LFNATOPER ORDER BY "+dl.getValor();
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sAliqe = "";
    String sAliqf = "";
    char[] cAliqe= new char[9];
    char[] cAliqf= new char[9];
    try {
      ps = con.prepareStatement(sSQL);
      rs = ps.executeQuery();
      imp.limpaPags();
      while ( rs.next() ) {
         if (imp.pRow()==0) {
         	imp.montaCab();
            imp.setTitulo("Relatório de naturezas de operações");
            imp.addSubTitulo("Relatório de naturezas de operações");
            imp.impCab(136, true);
            
            imp.say(imp.pRow()+0,0,""+imp.normal());
            imp.say(imp.pRow()+0,0,"");
            imp.say(imp.pRow()+0,0,"|");
            imp.say(imp.pRow()+0,2,"Cód.nat.op.");
            imp.say(imp.pRow()+0,12,"|");
            imp.say(imp.pRow()+0,14,"Descrição da naturaza da operação");
            imp.say(imp.pRow()+0,76,"|");
            imp.say(imp.pRow()+0,78,"Aliq.estadual");
            imp.say(imp.pRow()+0,102,"|");
            imp.say(imp.pRow()+0,108,"Aliq.federal");
            imp.say(imp.pRow()+0,135,"|");
            imp.say(imp.pRow()+1,0,""+imp.normal());
            imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",133)+"|");
         }
                  
         sAliqe = rs.getString("AliqeNat") != null ? rs.getString("AliqeNat") : "";
         if ((sAliqe.length() > 0) & (sAliqe.indexOf('.') != -1)) {
           cAliqe = sAliqe.toCharArray();
           cAliqe[sAliqe.indexOf('.')] = ',';
           sAliqe = new String(cAliqe);           
         }
         
         sAliqf = rs.getString("AliqfNat") != null ? rs.getString("AliqfNat") : "";
         if ((sAliqf.length() > 0) & (sAliqf.indexOf('.') != -1)) {
           cAliqf = sAliqf.toCharArray();
           cAliqf[sAliqf.indexOf('.')] = ',';
           sAliqf = new String(cAliqf);           
         }
         
         imp.say(imp.pRow()+1,0,""+imp.comprimido());
         imp.say(imp.pRow()+0,0,"|");
         imp.say(imp.pRow()+0,2,Funcoes.setMascara(rs.getString("CodNat"),"#.###"));
         imp.say(imp.pRow()+0,12,"|");
         imp.say(imp.pRow()+0,14,rs.getString("DescNat"));
         imp.say(imp.pRow()+0,76,"|");
         imp.say(imp.pRow()+0,78,sAliqe);
         imp.say(imp.pRow()+0,102,"|");
         imp.say(imp.pRow()+0,108,sAliqf);
         imp.say(imp.pRow()+0,135,"|");
         
         if (imp.pRow()>=linPag) {
            imp.incPags();
            imp.eject();
         }
      }
      
      imp.say(imp.pRow()+1,0,""+imp.normal());
      imp.say(imp.pRow()+0,0,"+"+Funcoes.replicate("-",133)+"+");
      imp.eject();
      
      imp.fechaGravacao();
      
//      rs.close();
//      ps.close();
      if (!con.getAutoCommit())
      	con.commit();
      dl.dispose();
    }  
    catch ( SQLException err ) {
		Funcoes.mensagemErro(this,"Erro ao consultar a tabela de natureza de operações!"+err.getMessage());      
    }
    
    if (bVisualizar) {
      imp.preview(this);
    }
    else {
      imp.print();
    }
  }
/* (non-Javadoc)
 * @see org.freedom.acao.InsertListener#beforeInsert(org.freedom.acao.InsertEvent)
 */
  public void afterInsert(InsertEvent ievt) {
	cbImpDtSaidaNat.setVlrString("S");
  }
  public void beforeInsert(InsertEvent ievt) { }
}
