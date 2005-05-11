/**
 * @version 02/11/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FRPagar.java <BR>
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
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import org.freedom.componentes.JLabelPad;

import org.freedom.bmps.Icone;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FRelatorio;


public class FRPagar extends FRelatorio {
  private JTextFieldPad txtDataini = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0); 
  private JTextFieldPad txtDatafim = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0); 
  private JRadioGroup cbFiltro = null;
  private Vector vVals = new Vector();
  private Vector vLabs = new Vector();
  private JTextFieldPad txtCodFor = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldFK txtRazFor = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
  private JCheckBoxPad cbObs = new JCheckBoxPad("Imprimir observações","S","N");
  private ListaCampos lcFor = new ListaCampos(this);
  private boolean comObs = false;
  private JButton btExp = new JButton(Icone.novo("btTXT.gif"));
 
  public FRPagar() {
    setTitulo("Contas a Pagar");
    setAtribos(80,80,285,280);

//    txtRazFor.setAtivo(false);
    lcFor.add(new GuardaCampo( txtCodFor, "CodFor", "Cód.forn.", ListaCampos.DB_PK, false));
    lcFor.add(new GuardaCampo( txtRazFor, "RazFor", "Razão social do fornecedor", ListaCampos.DB_SI, false));
    lcFor.montaSql(false, "FORNECED", "CP");
    lcFor.setReadOnly(true);
    txtCodFor.setTabelaExterna(lcFor);
    txtCodFor.setFK(true);
    txtCodFor.setNomeCampo("CodFor");

    txtDataini.setVlrDate(new Date());
    txtDatafim.setVlrDate(new Date());

    vLabs.addElement("Contas a pagar");
    vLabs.addElement("Contas pagas");
    vLabs.addElement("Ambas as contas");
    vVals.addElement("N");
    vVals.addElement("P");
    vVals.addElement("A");
    
    cbFiltro = new JRadioGroup(3,1,vLabs,vVals);
    JLabelPad lbLinha = new JLabelPad();
    lbLinha.setBorder(BorderFactory.createEtchedBorder());

    adic(new JLabelPad("Periodo:"),7,5,100,20);
    adic(lbLinha,60,15,210,2);
    adic(new JLabelPad("De:"),7,30,30,20);
    adic(txtDataini,32,30,97,20);
    adic(new JLabelPad("Até:"),135,30,30,20);
    adic(txtDatafim,165,30,97,20);
    adic(new JLabelPad("Cód.for."),7,55,300,20);
    adic(txtCodFor,7,75,60,20);
    adic(new JLabelPad("Razão social do fornecedor"),70,55,300,20);
    adic(txtRazFor,70,75,190,20);
    adic(cbFiltro,7,107,253,70);
    adic(cbObs,7,180,253,20);
  
    btExp.setToolTipText("Exporta para aquivo no formato csv.");
    btExp.setPreferredSize(new Dimension(38,26));
  	pnBotoes.add(btExp);    
  
    btExp.addActionListener(this);
    
  }
  public void setConexao(Connection cn) {
    super.setConexao(cn);
    lcFor.setConexao(cn);
  }

  public void actionPerformed(ActionEvent evt) {
  	if (evt.getSource() == btExp)
      exportaTXT();
  	else
      super.actionPerformed(evt);  	  	
  }
  
  public void exportaTXT() {
  	Vector vLinhas = new Vector();
    ResultSet rs = getResultSet();
    String sVencto = "";
    String sDuplic = "";
    String sPedido = "";
    String sDtCompra = "";
    String sDocPag = "";
    String sNParcPag = "";
    String sForneced = "";
    String sObs = "";
    String sLinha = "";
    String sAtraso = "";
    try {
    	vLinhas.addElement("Vencimento;Duplicata;Pedido;Data da compra;Fornecedor;Parcela;Atraso;Observação");
      	while(rs.next()) {
      		sVencto = rs.getString("DTVENCITPAG")!=null?Funcoes.sqlDateToStrDate(rs.getDate("DTVENCITPAG")).trim():"";			      		 
      		sDocPag = rs.getString("DOCPAG")!=null?rs.getString("DOCPAG").trim():""; 	
      		sNParcPag = rs.getString("NPARCPAG")!=null?rs.getString("NPARCPAG").trim():"";
      		sPedido = rs.getString("CODCOMPRA")!=null?rs.getString("CODCOMPRA").trim():"";
      		sDuplic = sDocPag +"/"+sNParcPag;
      		sForneced = rs.getString("RAZFOR")!=null?rs.getString("RAZFOR").trim():"";
            sDtCompra = rs.getString("DTEMITCOMPRA")!=null?Funcoes.sqlDateToStrDate(rs.getDate("DTEMITCOMPRA")).trim():"";
            sObs = rs.getString("OBSITPAG")!=null?rs.getString("OBSITPAG").trim():"";
            long iAtraso = Funcoes.getNumDias(Funcoes.sqlDateToDate(rs.getDate("DTVENCITPAG")),new Date());
            sAtraso = iAtraso>0?iAtraso+"":"0";
            sLinha = sVencto + ";" + sDuplic + ";" + sPedido + ";"+ sDtCompra + ";" + sForneced + ";" + sNParcPag + ";" + sAtraso + ";" + sObs;
			
      		vLinhas.addElement(sLinha);
      		      		
      		
      	}    	
    }
    catch(SQLException e) {
    	e.printStackTrace();
    }
  	
  	if (vLinhas.size()>1) {
  	    File fArq = Funcoes.buscaArq(this,"csv");
  	    if (fArq == null)
  	        return;
  	    try {
  	      PrintStream ps = new PrintStream(new FileOutputStream(fArq));
  	      for(int i=0;vLinhas.size()>i;++i) {
  	        ps.println(vLinhas.elementAt(i).toString());
  	      }
  	      ps.flush();
  	      ps.close();
  	    }
  	    catch(IOException err) {
  	       Funcoes.mensagemErro(this,"Erro ao gravar o arquivo!\n"+err.getMessage(),true,con,err);
  	       err.printStackTrace();
  	    }  		
  	}
  	else {
  		Funcoes.mensagemInforma(this,"Não há informações para exportar!");
  	}
  }
  
  
  public ResultSet getResultSet(){
    String sFiltroPag = cbFiltro.getVlrString();
//    String sPag = "";
    String sCodfor = "";        
    
    if (cbObs.getVlrString().equals("S"))
        comObs = true;
      else
        comObs = false;

      sCodfor = txtCodFor.getVlrString();
  	
  	String sSQL = "SELECT IT.DTVENCITPAG,IT.NPARCPAG,P.CODCOMPRA,"+
				  "P.CODFOR,F.RAZFOR,IT.VLRPARCITPAG,IT.VLRPAGOITPAG,"+
                  "IT.VLRAPAGITPAG,IT.DTPAGOITPAG,"+
				  "(SELECT C.STATUSCOMPRA FROM CPCOMPRA C "+
				  "WHERE C.FLAG IN "+
				  Aplicativo.carregaFiltro(con,org.freedom.telas.Aplicativo.iCodEmp)+
				  " AND C.CODEMP=P.CODEMPCP AND C.CODFILIAL=P.CODFILIALCP AND C.CODCOMPRA=P.CODCOMPRA)," +
				  "P.DOCPAG,IT.OBSITPAG, " +
				  "(SELECT C.DTEMITCOMPRA FROM CPCOMPRA C "+
				  "WHERE C.FLAG IN "+
				  Aplicativo.carregaFiltro(con,org.freedom.telas.Aplicativo.iCodEmp)+
				  " AND C.CODEMP=P.CODEMPCP AND C.CODFILIAL=P.CODFILIALCP AND C.CODCOMPRA=P.CODCOMPRA) AS DTEMITCOMPRA " +
				  "FROM FNITPAGAR IT,FNPAGAR P,CPFORNECED F "+
  			      " WHERE P.FLAG IN "+
				  Aplicativo.carregaFiltro(con,org.freedom.telas.Aplicativo.iCodEmp)+
				  " AND IT.CODEMP = P.CODEMP AND IT.CODFILIAL=P.CODFILIAL AND IT.DTVENCITPAG BETWEEN ? AND ? AND"+
				  " IT.STATUSITPAG IN (?,?) AND P.CODPAG = IT.CODPAG" +
				  " AND F.CODEMP=P.CODEMPFR AND F.CODFILIAL=P.CODFILIALFR AND F.CODFOR=P.CODFOR"+
				  (sCodfor.trim().equals("")?"":" AND P.CODFOR="+sCodfor)+
				  " AND P.CODEMP=? AND P.CODFILIAL=? ORDER BY IT.DTVENCITPAG,F.RAZFOR";
    System.out.println(sSQL);
  	PreparedStatement ps = null;
  	ResultSet rs = null;
    try {
    	ps = con.prepareStatement(sSQL);
    	ps.setDate(1,Funcoes.dateToSQLDate(txtDataini.getVlrDate()));
    	ps.setDate(2,Funcoes.dateToSQLDate(txtDatafim.getVlrDate()));
    	if (sFiltroPag.equals("N")) {
    		ps.setString(3,"P1");
    		ps.setString(4,"P1");
    	}
    	else if (sFiltroPag.equals("P")) {
    		ps.setString(3,"PP");
    		ps.setString(4,"PP");
    	}
    	else if (sFiltroPag.equals("A")) {
    		ps.setString(3,"P1");
    		ps.setString(4,"PP");
    	}
    	ps.setInt(5,Aplicativo.iCodEmp);
    	ps.setInt(6,ListaCampos.getMasterFilial("FNPAGAR"));
    	
    	rs = ps.executeQuery();
    }
    catch(SQLException e) {
    	e.printStackTrace();
    }
  	
  	return rs;
  }
  public void imprimir(boolean bVisualizar) {

    if (txtDatafim.getVlrDate().before(txtDataini.getVlrDate())) {
		Funcoes.mensagemInforma(this,"Data final maior que a data inicial!");
      return;
    }

    ImprimeOS imp = new ImprimeOS("",con);
    int linPag = imp.verifLinPag()-1;
    boolean bFimDia = false;
    String sFiltroPag = cbFiltro.getVlrString();
    String sPag = "";
//    String sCodfor = "";
    
    BigDecimal bTotalDiaParc = new BigDecimal("0");
    BigDecimal bTotalDiaPago = new BigDecimal("0");
    BigDecimal bTotalDiaApag = new BigDecimal("0");

    BigDecimal bTotParc = new BigDecimal("0");
    BigDecimal bTotalPago = new BigDecimal("0");
    BigDecimal bTotalApag = new BigDecimal("0");
    
    String sDataini = "";
    String sDatafim = "";
    String sDtVencItPag = "";
    String sDtPago = "";
    
    sDataini = txtDataini.getVlrString();
    sDatafim = txtDatafim.getVlrString();
    
    if (sFiltroPag.equals("N")) {
        sPag = "A PAGAR";
      }
      else if (sFiltroPag.equals("P")) {
        sPag = "PAGAS";
      }
      else if (sFiltroPag.equals("A")) {
        sPag = "A PAGAR/PAGAS";
      }
    
    
    ResultSet rs = getResultSet(); 
    
    try {
 
      imp.limpaPags();
      imp.montaCab();
  	imp.setTitulo("Relatório de contas "+sPag);
  	imp.addSubTitulo("RELATORIO DE CONTAS "+sPag+"   -   PERIODO DE :"+sDataini+" ATE: "+sDatafim);
      while ( rs.next() ) {
      	
      	if (imp.pRow()>=(linPag-1)) {
            imp.say(imp.pRow()+1,0,""+imp.comprimido());
            imp.say(imp.pRow()+0,0,"+"+Funcoes.replicate("-",133)+"+");
            imp.incPags();
            imp.eject();
        }
      	if (imp.pRow()==0) {        	
        	imp.impCab(136, true);
           
           imp.say(imp.pRow()+0,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",133)+"|");
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,0,"| Vencto.    |");
           imp.say(imp.pRow()+0,15," Fornecedor                               |");
           imp.say(imp.pRow()+0,59," Doc.      |");
           imp.say(imp.pRow()+0,72," Vlr. da Parc. |");
           imp.say(imp.pRow()+0,89," Vlr Pago      |");
           imp.say(imp.pRow()+0,106," Vlr Aberto   |");
           imp.say(imp.pRow()+0,122," Data Pagto. |");
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",133)+"|");
         }
        if ((!Funcoes.sqlDateToStrDate(rs.getDate("DtVencItPag")).equals(sDtVencItPag)) & (bFimDia)) {
        	
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",133)+"|");
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,0,"|");
           imp.say(imp.pRow()+0,41,"Totais do Dia-> | "+sDtVencItPag+" | "+
             Funcoes.strDecimalToStrCurrency(14,2,""+bTotalDiaParc)+" | "+
             Funcoes.strDecimalToStrCurrency(14,2,""+bTotalDiaPago)+" | "+
             Funcoes.strDecimalToStrCurrency(13,2,""+bTotalDiaApag));
           imp.say(imp.pRow(),135,"|");
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",133)+"|");
           bTotalDiaParc = new BigDecimal("0");
           bTotalDiaPago = new BigDecimal("0");
           bTotalDiaApag = new BigDecimal("0");
           bFimDia = false;
        }
    	 imp.say(imp.pRow()+1,0,""+imp.comprimido());
         imp.say(imp.pRow()+0,0,"|");
         if (!Funcoes.sqlDateToStrDate(rs.getDate("DtVencItPag")).equals(sDtVencItPag)) {
            imp.say(imp.pRow()+0,3,Funcoes.sqlDateToStrDate(rs.getDate("DtVencItPag"))+" |");
         }
         imp.say(imp.pRow()+0,16,Funcoes.copy(rs.getString("CodFor"),0,6)
             +"-"+Funcoes.copy(rs.getString("RazFor"),0,33)+" |");
          if (rs.getString("DtPagoItPag") != null)    
            sDtPago = Funcoes.sqlDateToStrDate(rs.getDate("DtPagoItPag"));
          else 
            sDtPago = " "; 
         sDtPago = Funcoes.copy(sDtPago,0,10);
            
         imp.say(imp.pRow()+0,61,(Funcoes.copy(rs.getString(10),0,1).equals("P") ? Funcoes.copy(rs.getString("CodCompra"),0,6) : Funcoes.copy(rs.getString("DocPag"),0,6))+
              "/"+Funcoes.copy(rs.getString("NParcPag"),0,2)+"| "+
              Funcoes.strDecimalToStrCurrency(14,2,rs.getString("VlrParcItPag"))+" | "+
              Funcoes.strDecimalToStrCurrency(14,2,rs.getString("VlrPagoItPag"))+" | "+
              Funcoes.strDecimalToStrCurrency(13,2,rs.getString("VlrApagItPag"))+" | "+
              " "+sDtPago+"  |");
         if ((comObs) & (rs.getString("ObsItPag") != null)) {
           imp.say(imp.pRow()+1,0,"|   Obs: "+Funcoes.copy(rs.getString("ObsItPag"),0,50));
           imp.say(imp.pRow(),135,"|");
         }
              
         if (rs.getString("VlrParcItPag") != null) {
            bTotalDiaParc = bTotalDiaParc.add(new BigDecimal(rs.getString("VlrParcItPag")));
            bTotParc = bTotParc.add(new BigDecimal(rs.getString("VlrParcItPag")));
         }
         if (rs.getString("VlrPagoItPag") != null) {
            bTotalDiaPago = bTotalDiaPago.add(new BigDecimal(rs.getString("VlrPagoItPag")));
            bTotalPago = bTotalPago.add(new BigDecimal(rs.getString("VlrPagoItPag")));
         }

         if (rs.getString("VlrApagItPag") != null) {
            bTotalDiaApag = bTotalDiaApag.add(new BigDecimal(rs.getString("VlrApagItPag")));
            bTotalApag = bTotalApag.add(new BigDecimal(rs.getString("VlrApagItPag")));
         }
         
         bFimDia = true;
         sDtVencItPag = Funcoes.sqlDateToStrDate(rs.getDate("DtVencItPag"));
      }

      if (bFimDia) {
         imp.say(imp.pRow()+1,0,""+imp.comprimido());
         imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",133)+"|");
         imp.say(imp.pRow()+1,0,""+imp.comprimido());
         imp.say(imp.pRow()+0,0,"|");
         imp.say(imp.pRow()+0,41,"Totais do Dia-> | "+sDtVencItPag+" | "+
           Funcoes.strDecimalToStrCurrency(14,2,""+bTotalDiaParc)+" | "+
           Funcoes.strDecimalToStrCurrency(14,2,""+bTotalDiaPago)+" | "+
           Funcoes.strDecimalToStrCurrency(13,2,""+bTotalDiaApag));
         imp.say(imp.pRow(),135,"|");
      }
      
      imp.say(imp.pRow()+1,0,""+imp.comprimido());
      imp.say(imp.pRow(),0,"|"+Funcoes.replicate("=",133)+"|");
      imp.say(imp.pRow()+1,0,""+imp.comprimido());
      imp.say(imp.pRow()+0,0,"|");
      imp.say(imp.pRow()+0,55,"Totais Geral-> | "+
           Funcoes.strDecimalToStrCurrency(14,2,""+bTotParc)+" | "+
           Funcoes.strDecimalToStrCurrency(14,2,""+bTotalPago)+" | "+
           Funcoes.strDecimalToStrCurrency(13,2,""+bTotalApag));
      imp.say(imp.pRow(),135,"|");

      imp.say(imp.pRow()+1,0,""+imp.comprimido());
      imp.say(imp.pRow()+0,0,"+"+Funcoes.replicate("=",133)+"+");
      
      
      imp.eject();
      
      imp.fechaGravacao();
      
//      rs.close();
//      ps.close();
      if (!con.getAutoCommit())
      	con.commit();
//      dl.dispose();


      
    }  
    catch ( SQLException err ) {
		Funcoes.mensagemErro(this,"Erro consulta tabela de preços!\n"+err.getMessage(),true,con,err);      
    }
    
    if (bVisualizar) {
      imp.preview(this);
    }
    else {
      imp.print();
    }        
  }
}
