/**
 * @version 12/01/2004 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.tmk <BR>
 * Classe: @(#)FRDiario.java <BR>
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
 * Relatório diário de ligações. 
 * 
 */

package org.freedom.modulos.tmk;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;

import javax.swing.JLabel;

import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JComboBoxPad;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FRelatorio;

public class FRDiario extends FRelatorio {
  private JTextFieldPad txtDataini = new JTextFieldPad(); 
  private JTextFieldPad txtDatafim = new JTextFieldPad(); 
  private Connection con = null;
  private JComboBoxPad cbSit = new JComboBoxPad();
  private JCheckBoxPad cbComp = new JCheckBoxPad("Imprimir completo?","S","N");
  private boolean bComp = false;
  
  public FRDiario() {
    setTitulo("Ralatório diário");
    setAtribos(80,80,290,270);

//    Funcoes.setBordReq(txtCodFor);

    txtDataini.setTipo(JTextFieldPad.TP_DATE,10,0);
    txtDatafim.setTipo(JTextFieldPad.TP_DATE,10,0);
    txtDataini.setVlrDate(new Date());
    txtDatafim.setVlrDate(new Date());

    Vector vVals = new Vector();
    vVals.addElement("RJ");
    vVals.addElement("AG");
    Vector vLabs = new Vector();
    vLabs.addElement("Rejeitado");		
    vLabs.addElement("Agendar ligação/visita");
    cbSit.setItens(vLabs,vVals);
    
    adic(new JLabel("Periodo:"),7,5,120,20);
    adic(new JLabel("De:"),7,25,30,20);
    adic(txtDataini,40,25,100,20);
    adic(new JLabel("Até:"),143,25,23,20);
    adic(txtDatafim,169,25,100,20);
    adic(new JLabel("Status:"),7,45,269,20);
    adic(cbSit,7,65,262,20);
    adic(cbComp,7,100,253,20);
    
  }
  public void setConexao(Connection cn) {
    con = cn;
  }

  public void imprimir(boolean bVisualizar) {

    if (txtDatafim.getVlrDate().before(txtDataini.getVlrDate())) {
		Funcoes.mensagemInforma(this,"Data final maior que a data inicial!");
      return;
    }

    ImprimeOS imp = new ImprimeOS("",con);
    int linPag = imp.verifLinPag()-1;
    String sSit = cbSit.getVlrString();
    String sTit = "";
    if (cbComp.getVlrString().equals("S"))
      bComp = true;
    else
      bComp = false;
    if (sSit.equals("RJ")) {
      sTit = "REJEITADOS";
    }
    else if (sSit.equals("AG")) {
    	sTit = "AGENDADOS";
    }
    else {
        sTit = "";
    }
    
    imp.montaCab();
    String sDataini = "";
    String sDatafim = "";
    String sDatahist = "";
    
    sDataini = txtDataini.getVlrString();
    sDatafim = txtDatafim.getVlrString();
    
    imp.setTitulo("Relatório diário de "+sTit);
    String sSQL = "SELECT H.CODHISTTK,H.SITHISTTK,H.DATAHISTTK,H.DESCHISTTK,C.RAZCTO,H.HORAHISTTK,H.CODCTO" +
  		                  " FROM TKHISTORICO H, TKCONTATO C WHERE H.CODEMP=? AND H.CODFILIAL=? AND H.DATAHISTTK BETWEEN ? AND ? " +
  	                      " AND C.CODCTO=H.CODCTO AND C.CODEMP=H.CODEMPCO AND C.CODFILIAL=H.CODFILIALCO " +
                          (!sSit.equals("") ? " AND H.SITHISTTK='"+sSit+"' " : "") +    
  		                  "ORDER BY H.CODHISTTK";
    PreparedStatement ps = null;
    ResultSet rs = null;
    try {
      ps = con.prepareStatement(sSQL);
      ps.setInt(1,Aplicativo.iCodEmp);
      ps.setInt(2,ListaCampos.getMasterFilial("TKHISTORICO"));
      ps.setDate(3,Funcoes.dateToSQLDate(txtDataini.getVlrDate()));
      ps.setDate(4,Funcoes.dateToSQLDate(txtDatafim.getVlrDate()));
      rs = ps.executeQuery();
      imp.limpaPags();
      
      while ( rs.next() ) {
        if (imp.pRow()==0) {
           String sTitulo = "RELATORIO DE DIÁRIO DE "+sTit+"   -   PERIODO DE :"+sDataini+" ATE: "+sDatafim;
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,0,"+"+Funcoes.replicate("-",134)+"+");
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,0,"|   Emitido em :"+Funcoes.dateToStrDate(new Date()));
           imp.say(imp.pRow()+0,120,"Pagina : "+(imp.getNumPags()));
           imp.say(imp.pRow()+0,136,"|");
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,0,"|");
           imp.say(imp.pRow()+0,(136-sTitulo.length())/2,sTitulo);
           imp.say(imp.pRow()+0,136,"|");
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,0,"|");
           imp.say(imp.pRow()+0,136,"|");
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",134)+"|");
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,0,"| Hora ");
           imp.say(imp.pRow()+0,10,"| Sit ");
           imp.say(imp.pRow()+0,17,"| Contato ");
           if (bComp) {
           	 imp.say(imp.pRow()+0,136,"|");
           	 imp.say(imp.pRow()+1,0,""+imp.comprimido());
           	 imp.say(imp.pRow()+0,0,"|");
           	 imp.say(imp.pRow()+0,5,"Descrição");
            }
           else {
           	imp.say(imp.pRow()+0,80,"| Resumo");
           }
           imp.say(imp.pRow()+0,136,"|");
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",134)+"|");
         }
         if (!Funcoes.sqlDateToStrDate(rs.getDate("DataHistTK")).equals(sDatahist)) {
            imp.say(imp.pRow()+1,0,""+imp.comprimido());
            imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",134)+"|");
            imp.say(imp.pRow()+1,0,""+imp.comprimido());
            imp.say(imp.pRow()+0,0,"|    Dia: "+Funcoes.sqlDateToStrDate(rs.getDate("DataHistTK")));
            imp.say(imp.pRow()+0,136,"|");
            imp.say(imp.pRow()+1,0,""+imp.comprimido());
            imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",134)+"|");
         }
         imp.say(imp.pRow()+1,0,""+imp.comprimido());
         GregorianCalendar calHora = new GregorianCalendar();
         calHora.setTime(rs.getTime("HoraHistTk"));
         imp.say(imp.pRow()+0,0,"| "+Funcoes.strZero(calHora.get(Calendar.HOUR_OF_DAY)+"",2)+":"+Funcoes.strZero(calHora.get(Calendar.MINUTE)+"",2));
         imp.say(imp.pRow()+0,10,"| "+rs.getString("SitHistTK"));
         imp.say(imp.pRow()+0,17,"| "+
           Funcoes.alinhaDir(rs.getInt("CodCto"),8)+" - "+
           Funcoes.copy(rs.getString("RazCto"),0,50));
         if (bComp) {
         	String[] sVal = Funcoes.strToStrArray(rs.getString("DescHistTK"));
         	for (int i=0;i<sVal.length;i++) {
         		imp.say(imp.pRow()+0,136,"|");
         		imp.say(imp.pRow()+1,0,""+imp.comprimido());
         		imp.say(imp.pRow()+0,0,"|");
         		imp.say(imp.pRow()+0,5,Funcoes.copy(sVal[i],0,127));
         	}
         }
         else
         	imp.say(imp.pRow()+0,80,"| "+Funcoes.copy(rs.getString("DescHistTK"),0,45).replaceAll("\n","..."));
         imp.say(imp.pRow()+0,136,"|");
         sDatahist = Funcoes.sqlDateToStrDate(rs.getDate("DataHistTK"));
      	 if (imp.pRow() == linPag) {
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,0,"+"+Funcoes.replicate("-",134)+"+");
           imp.eject();
           imp.incPags();
         }
      }
      imp.say(imp.pRow()+1,0,""+imp.comprimido());
      imp.say(imp.pRow()+0,0,"+"+Funcoes.replicate("-",134)+"+");
      imp.eject();
      
      imp.fechaGravacao();
      
//      rs.close();
//      ps.close();
      if (!con.getAutoCommit())
      	con.commit();
//      dl.dispose();
    }  
    catch ( SQLException err ) {
		Funcoes.mensagemErro(this,"Erro consulta ao histórico!"+err.getMessage());      
    }
    
    if (bVisualizar) {
      imp.preview(this);
    }
    else {
      imp.print();
    }
  }
}
