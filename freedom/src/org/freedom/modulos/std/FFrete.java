/**
 * @version 02/11/2003 <BR>
 * @author Setpoint Informática Ltda./Marco  Antonio Sanchez <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FFrete.java <BR>
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
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;

import org.freedom.acao.InsertEvent;
import org.freedom.acao.InsertListener;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FDados;



public class FFrete extends FDados implements InsertListener, FocusListener {
  private int casasDec = Aplicativo.casasDec;
  private JTextFieldPad  txtCodVenda = new JTextFieldPad();
  private JTextFieldFK  txtDocVenda = new JTextFieldFK();
  private JTextFieldPad txtVlrLiqVenda = new JTextFieldPad(JTextFieldPad.TP_NUMERIC,15,2);
  private JTextFieldPad  txtSeries = new JTextFieldPad(JTextFieldPad.TP_STRING,2,0);
  private JTextFieldPad txtConhecFreteVD = new JTextFieldPad(JTextFieldPad.TP_STRING,13,0 );
  private JTextFieldPad txtPercVendaFreteVD = new JTextFieldPad(JTextFieldPad.TP_NUMERIC,9,2);
  private JTextFieldFK txtDescTran = new JTextFieldFK();
  private JTextFieldPad txtCodTran = new JTextFieldPad(5);
  private JTextFieldPad txtPlacaFreteVD = new JTextFieldPad();
  private JTextFieldPad txtUFFreteVD = new JTextFieldPad();
  private JTextFieldPad txtVlrFreteVD = new JTextFieldPad();
  private JRadioGroup rgFreteVD = null;
  private JTextFieldPad txtQtdFreteVD = new JTextFieldPad();
  private JTextFieldPad txtPesoBrutVD = new JTextFieldPad();
  private JTextFieldPad txtPesoLiqVD = new JTextFieldPad();
  private JTextFieldPad txtEspFreteVD = new JTextFieldPad();
  private JTextFieldPad txtMarcaFreteVD = new JTextFieldPad();
  
  private Vector vVals = new Vector();
  private Vector vLabs = new Vector();
 
  private ListaCampos lcTran = new ListaCampos(this,"TN");
  private ListaCampos lcVenda = new ListaCampos(this,"");
 
  public FFrete () {
    setTitulo("Lançamento de Fretes");
    setAtribos( 50, 50, 380, 285);
    
    lcCampos.addPostListener(this);
	 	
    txtCodTran.setNomeCampo("CodTran");
    txtCodTran.setTipo(JTextFieldPad.TP_INTEGER,8,0);
    txtDescTran.setTipo(JTextFieldPad.TP_STRING,40,0);
    lcTran.add(new GuardaCampo( txtCodTran, 7, 100, 80, 20, "CodTran", "Cód.tran.", true, false, null, JTextFieldPad.TP_INTEGER,false),"txtCodTranx");
    lcTran.add(new GuardaCampo( txtDescTran, 90, 100, 207, 20, "NomeTran", "Descrição da transporatadora", false, false, null, JTextFieldPad.TP_STRING,false),"txtDescTran");
    txtDescTran.setListaCampos(lcTran);
    txtCodTran.setTabelaExterna(lcTran);
	txtCodTran.setFK(true);
    lcTran.montaSql(false, "TRANSP", "VD");
    lcTran.setQueryCommit(false);
    lcTran.setReadOnly(true);
    
    
    txtCodVenda.setNomeCampo("CodVenda");
    txtCodVenda.setTipo(JTextFieldPad.TP_INTEGER,9,0);
    txtDocVenda.setTipo(JTextFieldPad.TP_STRING,9,0);
    txtSeries.setTipo(JTextFieldPad.TP_STRING,2,0);
    
    lcVenda.add(new GuardaCampo( txtCodVenda, 7, 100, 80, 20, "CodVenda", "Cód.vd.", true, false, null, JTextFieldPad.TP_INTEGER,false),"txtCodVenda");
    lcVenda.add(new GuardaCampo( txtDocVenda, 90, 100, 207, 20, "DocVenda", "Doc.vd.", false, false, null, JTextFieldPad.TP_STRING,false),"txtDocVenda");
    lcVenda.add(new GuardaCampo( txtSeries, 90, 100, 207, 20, "Serie", "Serie", false, false, null, JTextFieldPad.TP_STRING,false),"txtSeries");    
    lcVenda.add(new GuardaCampo( txtVlrLiqVenda, "VlrLiqVenda", "V.liq.", ListaCampos.DB_SI, false));    
        txtDocVenda.setListaCampos(lcVenda);
    txtCodVenda.setTabelaExterna(lcVenda);
	txtCodVenda.setFK(true);
    lcVenda.montaSql(false, "VENDA", "VD");
    lcVenda.setQueryCommit(false);
    lcVenda.setReadOnly(true);
    
    
    txtPlacaFreteVD.setTipo(JTextFieldPad.TP_STRING,10,0);
    txtUFFreteVD.setTipo(JTextFieldPad.TP_STRING,2,0);
    txtVlrFreteVD.setTipo(JTextFieldPad.TP_DECIMAL,15,2);
    txtQtdFreteVD.setTipo(JTextFieldPad.TP_DECIMAL,15,2);
    txtPesoBrutVD.setTipo(JTextFieldPad.TP_DECIMAL,15,casasDec);
    txtPesoLiqVD.setTipo(JTextFieldPad.TP_DECIMAL,15,casasDec);
    txtEspFreteVD.setTipo(JTextFieldPad.TP_STRING,10,0);
    txtMarcaFreteVD.setTipo(JTextFieldPad.TP_STRING,10,0);
    
    
    vVals.addElement("C");
    vVals.addElement("F");
    vLabs.addElement("CIF");
    vLabs.addElement("FOB");
    
    rgFreteVD = new JRadioGroup(2,1,vLabs, vVals);
    
    txtPercVendaFreteVD.setAtivo(false);
    
    adicCampo(txtCodVenda, 7, 20, 110, 20, "CodVenda", "Nº pedido", JTextFieldPad.TP_INTEGER, 8, 0, true, true, null, true);
    adicDescFK(txtDocVenda,120, 20, 110, 20, "DocVenda", "N.NF", JTextFieldPad.TP_INTEGER, 8, 0);
    adicDB(rgFreteVD,233,20,125,60, "TipoFreteVd", "Tipo ",JTextFieldPad.TP_STRING,true);
    adicCampo(txtMarcaFreteVD,7,60,110,20, "MarcaFreteVd","Marca", JTextFieldPad.TP_STRING, 10, 0, false, false, null, true);        
    adicCampo(txtPercVendaFreteVD,120,60,110,20, "PercVendaFreteVd","Perc.vd.", JTextFieldPad.TP_NUMERIC, 9, 2, false, false, null, false);
    adicCampo(txtCodTran, 7, 100, 80, 20, "CodTran", "Cód.tran.", JTextFieldPad.TP_INTEGER, 8, 0, false, true, null, true);
    adicDescFK(txtDescTran,91, 100, 265, 20, "NomeTran", "Descrição da Transportadora	", JTextFieldPad.TP_STRING, 40, 0);
    adicCampo(txtConhecFreteVD,7,140,90,20,"ConhecFreteVd","Conhec.frete", JTextFieldPad.TP_STRING,13,0,false,false,null,false);    
    adicCampo(txtPlacaFreteVD,99,140,100,20, "PlacaFreteVd","Placa", JTextFieldPad.TP_STRING, 8, 0, false, false, null, true);
    adicCampo(txtUFFreteVD,202,140,44,20,"UfFreteVd","UF.", JTextFieldPad.TP_STRING, 2, 0, false, false, null, true);
    adicCampo(txtVlrFreteVD,248,140,107,20, "VlrFreteVd","Valor", JTextFieldPad.TP_DECIMAL, 15, 2, false, false, null, true);
    adicCampo(txtQtdFreteVD,7,180,90,20, "QtdFreteVd","Volumes", JTextFieldPad.TP_INTEGER, 8, 0, false, false, null, true);
    adicCampo(txtPesoBrutVD,100,180,77,20, "PesoBrutVd","P.bruto", JTextFieldPad.TP_DECIMAL, 9,casasDec, false, false, null, true);
    adicCampo(txtPesoLiqVD,180,180,77,20, "PesoLiqVd","P.liq.", JTextFieldPad.TP_DECIMAL, 9, casasDec, false, false, null, true);    
    adicCampo(txtEspFreteVD,260,180,95,20, "EspFreteVd","Especie", JTextFieldPad.TP_STRING, 10, 0, false, false, null, true);
    txtPlacaFreteVD.setStrMascara("###-####");
    
    setListaCampos( true, "FRETEVD", "VD");
    
  
    
    lcCampos.addInsertListener(this);
    txtVlrFreteVD.addFocusListener(this);
    
    btImp.addActionListener(this);
    btPrevimp.addActionListener(this);
   
  }
    
  public void actionPerformed(ActionEvent evt) {
    if (evt.getSource() == btPrevimp)
      imprimir(true);
    else if (evt.getSource() == btImp) 
      imprimir(false);
    
    super.actionPerformed(evt);
  }
  public void imprimir(boolean bVisualizar) {
    ImprimeOS imp = new ImprimeOS("",con);
    String[] sValores; 
    String sWhere = "";
    
    String sAnd = " AND ";
    int linPag = imp.verifLinPag()-1;
    imp.montaCab();
    imp.setTitulo("Relatório de Lancamentos de Fretes");
    DLRFrete dl = new DLRFrete();
    dl.setVisible(true);
    
    
    if (dl.OK == false) {
      dl.dispose();
      return;
    }
    sValores = dl.getValores();
    dl.dispose();
    
    if (sValores[1].trim().length() > 0) {
      sWhere = sWhere+sAnd+"VD.DtEmitVenda >= '"+Funcoes.strDateToStrDB(sValores[1])+"'";
      sAnd = " AND ";
    }
    
    if (sValores[2].trim().length() > 0) {
      sWhere = sWhere+sAnd+"VD.DtEmitVenda <= '"+Funcoes.strDateToStrDB(sValores[2])+"'";
      sAnd = " AND ";
    }
    
    
    imp.montaCab();
    String sDataini = "";
    String sDatafim = "";
    
    
    sDataini = sValores[1];
    sDatafim = sValores[2];
    
    String sSQL = "SELECT F.CODVENDA,F.TIPOFRETEVD,F.PLACAFRETEVD,F.UFFRETEVD,F.VLRFRETEVD,F.QTDFRETEVD,"+
    "F.PESOLIQVD,F.PESOBRUTVD,F.ESPFRETEVD,F.MARCAFRETEVD, T.CODTRAN,T.NOMETRAN,"+
    "VD.DOCVENDA,F.PERCVENDAFRETEVD,F.CONHECFRETEVD,VD.DTEMITVENDA FROM VDVENDA VD, VDTRANSP T,VDFRETEVD F  WHERE T.CODTRAN=F.CODTRAN  "+
    "AND T.CODEMP=F.CODEMPTN AND T.CODFILIAL=F.CODFILIALTN AND F.CODVENDA=VD.CODVENDA AND " +
    "VD.CODEMP=F.CODEMP AND VD.CODFILIAL=F.CODFILIAL"+sWhere+
    " ORDER BY " +sValores[0] +",VD.DTEMITVENDA";
    
    System.out.println(sSQL);
    ;
    
    PreparedStatement ps = null;
    ResultSet rs = null;
    
    try {
      ps = con.prepareStatement(sSQL);
      rs = ps.executeQuery();
      imp.limpaPags();
      while ( rs.next() ) {
        if (imp.pRow()==0) {
          imp.say(imp.pRow()+1,0,""+imp.comprimido());
          imp.say(imp.pRow()+0,0,"+"+Funcoes.replicate("=",134)+"+");
          imp.say(imp.pRow()+1,0,""+imp.comprimido());
          imp.say(imp.pRow()+0,0,"|   Emitido em :"+Funcoes.dateToStrDate(new Date()));
          imp.say(imp.pRow()+0,120,"Pagina : "+(imp.getNumPags()));
          imp.say(imp.pRow()+0,136,"|");
          imp.say(imp.pRow()+1,0,""+imp.comprimido());
          imp.say(imp.pRow()+0,0,"|");
          imp.say(imp.pRow()+0,5,"RELATÓRIO DE LANÇAMENTO DE FRETES   -   PERIODO DE :"+sDataini+" Até: "+sDatafim);
          imp.say(imp.pRow()+0,136,"|");
          imp.say(imp.pRow()+1,0,""+imp.comprimido());
          imp.say(imp.pRow()+0,0,"+"+Funcoes.replicate("=",134)+"+");
          imp.say(imp.pRow()+1,0,""+imp.comprimido());
          imp.say(imp.pRow()+0,0,"|");
          imp.say(imp.pRow()+0,2,"Tipo frete  ");
          imp.say(imp.pRow()+0,14,"Pedido");
          imp.say(imp.pRow()+0,23,"Doc.vd.");
          imp.say(imp.pRow()+0,34,"Cod.tran.");
          imp.say(imp.pRow()+0,54,"Nome transportadora");
          imp.say(imp.pRow()+0,92,"Placa"); 
          imp.say(imp.pRow()+0,102,"UF");
          imp.say(imp.pRow()+0,136,"|");
          imp.say(imp.pRow()+1,0,""+imp.comprimido());
          imp.say(imp.pRow()+0,0,"|");
          imp.say(imp.pRow()+0,2,"Conhec.");
          imp.say(imp.pRow()+0,14,"Qtd.");
          imp.say(imp.pRow()+0,27,"Valor.");
          imp.say(imp.pRow()+0,38,"% Ft.vd.");
          imp.say(imp.pRow()+0,51,"Especie.");
          imp.say(imp.pRow()+0,64,"Marca.");
          imp.say(imp.pRow()+0,79,"P.bruto :");            	          
          imp.say(imp.pRow()+0,95,"P.liq.:");
          imp.say(imp.pRow()+0,110,"Dt.emit.vd");
          imp.say(imp.pRow()+0,136,"|");       
          imp.say(imp.pRow()+1,0,""+imp.comprimido());
          
        }
        
        imp.say(imp.pRow()+0,0,"+"+Funcoes.replicate("-",134)+"+");
        imp.say(imp.pRow()+1,0,""+imp.comprimido());
        imp.say(imp.pRow()+0,0,"|");
        imp.say(imp.pRow()+0,2,rs.getString("TipoFreteVd").equals("C") ? "CIF" : "FOB" );
        imp.say(imp.pRow()+0,14,rs.getString("CodVenda"));
        imp.say(imp.pRow()+0,23,rs.getString("DocVenda"));
        imp.say(imp.pRow()+0,34,rs.getString("CodTran"));
        imp.say(imp.pRow()+0,54,Funcoes.copy(rs.getString("NomeTran"),20));
        imp.say(imp.pRow()+0,92,Funcoes.setMascara(rs.getString("PlacaFreteVd"),"###-####")); 
        imp.say(imp.pRow()+0,102,rs.getString("UFFRETEVD"));
        imp.say(imp.pRow()+0,136,"|");
        imp.say(imp.pRow()+1,0,""+imp.comprimido());
        imp.say(imp.pRow()+0,0,"|");
        imp.say(imp.pRow()+0,2,Funcoes.copy(rs.getString("ConhecFreteVd"),8));
        imp.say(imp.pRow()+0,14,""+rs.getDouble("QtdFreteVd"));
        imp.say(imp.pRow()+0,25,Funcoes.strDecimalToStrCurrency(9,2,""+rs.getString("VlrFreteVd")));
        imp.say(imp.pRow()+0,38,""+rs.getDouble("PercVendaFreteVd"));
        imp.say(imp.pRow()+0,51,Funcoes.copy(rs.getString("EspFreteVd"),10));
        imp.say(imp.pRow()+0,64,Funcoes.copy(rs.getString("MarcaFreteVd"),10));
        imp.say(imp.pRow()+0,79,Funcoes.strDecimalToStrCurrency(9,casasDec,rs.getString("PesoBrutVd")));
        imp.say(imp.pRow()+0,95,Funcoes.strDecimalToStrCurrency(9,casasDec,rs.getString("PesoLiqVd")));
        imp.say(imp.pRow()+0,110,Funcoes.dateToStrDate(rs.getDate("DTEMITVENDA")));
        imp.say(imp.pRow()+0,136,"|");
        imp.say(imp.pRow()+1,0,""+imp.comprimido());
        if (imp.pRow()>=linPag) {
          imp.incPags();
          imp.eject();
        }
      }
      
      // imp.say(imp.pRow()+1,0,""+imp.comprimido());
      imp.say(imp.pRow()+0,0,Funcoes.replicate("=",136));
      imp.eject();
      
      imp.fechaGravacao();
      
      //    	    rs.close();
      //    	    ps.close();
      if (!con.getAutoCommit())
        con.commit();
      dl.dispose();
    }  
    catch ( SQLException err ) {
      Funcoes.mensagemErro(this,"Erro ao consultar a tabela de lancameto de fretes!"+err.getMessage());      
    }
    
    if (bVisualizar) {
      imp.preview(this);
    }
    else {
      imp.print();
    }
    
  }  
  private void calcPerc() {
    if (txtVlrLiqVenda.getVlrDouble().doubleValue() > 0)
      txtPercVendaFreteVD.setVlrBigDecimal(
        txtVlrFreteVD.getVlrBigDecimal().divide(
            txtVlrLiqVenda.getVlrBigDecimal(),2,BigDecimal.ROUND_HALF_UP
        ).multiply(new BigDecimal(100)));
  }
  public void execShow(Connection cn) {
	
	 lcTran.setConexao(cn);
	 lcVenda.setConexao(cn);
	 
	 super.execShow(cn);
   }

  public void afterInsert(InsertEvent ievt) {
    txtPlacaFreteVD.setVlrString("*******");
    txtUFFreteVD.setVlrString("**");
    txtEspFreteVD.setVlrString("Volume");
    txtMarcaFreteVD.setVlrString("**********");
    txtPesoBrutVD.setVlrBigDecimal(new BigDecimal("1"));
    txtPesoLiqVD.setVlrBigDecimal(new BigDecimal("1"));
  }
  public void beforeInsert(InsertEvent ievt) { }
  public void focusLost(FocusEvent fevt) {
    if (fevt.getSource() == txtVlrFreteVD)
      calcPerc();
  }

  public void focusGained(FocusEvent e) { }
}	  
   	  
	   

