/**
 * @version 11/02/2002 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FContComis.java <BR>
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
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import org.freedom.bmps.Icone;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.Tabela;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FFilho;

public class FManutComis extends FFilho implements ActionListener {
  private JPanelPad pinPeriodo = new JPanelPad(275,65);
  private JPanelPad pinLabel = new JPanelPad(50,20);
  private JLabel lbPeriodo = new JLabel(" Periodo");
  private JLabel lbDe = new JLabel("De:");
  private JLabel lbA = new JLabel("A:");
  private JLabel lbCodVend = new JLabel("Cód.repr.");
  private JLabel lbVend = new JLabel("Nome do representante");
  private JLabel lbTotComi = new JLabel("Total comis.");
  private JLabel lbTotLib = new JLabel("Total liber.");
  private JLabel lbTotPg = new JLabel("Total pago");
  private Vector vVals = new Vector();
  private Vector vLabs = new Vector();
  private JRadioGroup rgEmitRel = null; 
  private JTextFieldPad txtDataini = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
  private JTextFieldPad txtDatafim = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
  private JTextFieldPad txtCodVend = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldFK txtDescVend = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
  private JTextFieldPad txtTotComi = new JTextFieldPad(JTextFieldPad.TP_DECIMAL,10,3);
  private JTextFieldPad txtTotLib = new JTextFieldPad(JTextFieldPad.TP_DECIMAL,10,3);
  private JTextFieldPad txtTotPg = new JTextFieldPad(JTextFieldPad.TP_DECIMAL,10,3);
  private JCheckBoxPad cbComPag = new JCheckBoxPad("Pagas","S","N");
  private JCheckBoxPad cbLiberadas = new JCheckBoxPad("Liberadas","S","N");
  private JCheckBoxPad cbNLiberadas = new JCheckBoxPad("Não liberadas","S","N");
  
  private Tabela tab = new Tabela();
  private JScrollPane spnTab = new JScrollPane(tab);
  private ListaCampos lcVend = new ListaCampos(this);
  private JButton btBusca = new JButton(Icone.novo("btPesquisa.gif"));
  private JButton btBaixa = new JButton(Icone.novo("btGerar.gif"));
  private JButton btCalc = new JButton(Icone.novo("btExecuta.gif"));
  private JButton btEstorno = new JButton(Icone.novo("btCancelar.gif"));
  private JButton btSair = new JButton("Sair",Icone.novo("btSair.gif"));
  private JButton btLib = new JButton(Icone.novo("btOk.gif"));
  private Vector vCodComi = new Vector();
  String sEmitRel = "";      
  BigDecimal bVlrTot = new BigDecimal("0");
  BigDecimal bVlrTotPago = new BigDecimal("0");
  public FManutComis() {
    setTitulo("Controle de Comissões");
    setAtribos(50,25,660,400);
    cbLiberadas.setVlrString("S");

    vVals.addElement("E");
    vVals.addElement("V");
    vLabs.addElement("Emissão");
    vLabs.addElement("Vencimento");
    rgEmitRel = new JRadioGroup(2,2,vLabs,vVals);
    rgEmitRel.setVlrString("E");
    rgEmitRel.setAtivo(0,true);
    rgEmitRel.setAtivo(1,true);
    
    btCalc.setToolTipText("Recalcular");
    btLib.setToolTipText("Liberar");
    btBaixa.setToolTipText("Baixar");
    btEstorno.setToolTipText("Estornar");
    
    Funcoes.setBordReq(txtDataini);
    Funcoes.setBordReq(txtDatafim);
    Funcoes.setBordReq(txtCodVend);
   
    txtTotComi.setAtivo(false);
    txtTotLib.setAtivo(false);
    txtTotPg.setAtivo(false);
    
    lcVend.add(new GuardaCampo( txtCodVend, "CodVend", "Cód.repr.", ListaCampos.DB_PK, txtDescVend, false));
    lcVend.add(new GuardaCampo( txtDescVend, "NomeVend", "Descrição do representante", ListaCampos.DB_SI, false));
    lcVend.montaSql(false,"VENDEDOR", "VD");
    lcVend.setReadOnly(true);
	txtCodVend.setPK(true);
    txtCodVend.setTabelaExterna(lcVend);
    txtCodVend.setListaCampos(lcVend);
    txtCodVend.setNomeCampo("CodVend");
    
    Container c = getContentPane();
    
    c.setLayout(new BorderLayout());
    
    JPanelPad pinTop = new JPanelPad(600,92);
    JPanelPad pinRod = new JPanelPad(600,50);

    pinPeriodo.adic(lbDe,7,10,30,20);
    pinPeriodo.adic(txtDataini,40,10,97,20);
    pinPeriodo.adic(lbA,7,35,15,20);
    pinPeriodo.adic(txtDatafim,40,35,97,20);
    pinPeriodo.adic(rgEmitRel,150,10,110,45);
    
    pinLabel.adic(lbPeriodo,0,0,55,20);
    pinLabel.tiraBorda();
    
    pinTop.adic(pinLabel,15,2,55,17);
    pinTop.adic(pinPeriodo,7,10,275,70);
    pinTop.adic(lbCodVend,285,0,80,20);
    pinTop.adic(lbVend,365,0,200,20);
    pinTop.adic(txtCodVend,285,20,77,20);
    pinTop.adic(txtDescVend,365,20,250,20);
    pinTop.adic(cbComPag,285,50,70,20);
    pinTop.adic(cbLiberadas,355,50,100,20);
    pinTop.adic(cbNLiberadas,460,50,120,20);
    pinTop.adic(btBusca,585,45,30,30);
    
    pinRod.adic(btCalc,10,10,37,30);
    pinRod.adic(btLib,50,10,37,30);
    pinRod.adic(btBaixa,90,10,37,30);
    pinRod.adic(btEstorno,130,10,37,30);
    pinRod.adic(lbTotComi,185,0,97,20);
    pinRod.adic(txtTotComi,185,20,97,20);
    pinRod.adic(lbTotLib,285,0,97,20);
    pinRod.adic(txtTotLib,285,20,97,20);
    pinRod.adic(lbTotPg,385,0,100,20);
    pinRod.adic(txtTotPg,385,20,100,20);
	pinRod.adic(btSair,500,10,90,30);
    
    c.add(pinTop,BorderLayout.NORTH);
    c.add(pinRod,BorderLayout.SOUTH);
    c.add(spnTab,BorderLayout.CENTER);
    
    
    tab.adicColuna("Cliente"); //0
    tab.adicColuna("Doc."); //1
    tab.adicColuna("Parcelamento"); //2
    tab.adicColuna("TP."); //3
    tab.adicColuna("Valor"); //4
    tab.adicColuna("Emissão"); //5
    tab.adicColuna("Vencimento"); //6
    tab.adicColuna("Data pagamento"); //7
    
   
    tab.setTamColuna(80,0);
    tab.setTamColuna(50,1);
    tab.setTamColuna(92,2);
    tab.setTamColuna(30,3);
    tab.setTamColuna(70,4);
    tab.setTamColuna(80,5);
    tab.setTamColuna(85,6);
    tab.setTamColuna(160,7);
    tab.setColunaEditavel(0,true);
    
    btBusca.addActionListener(this);
    btCalc.addActionListener(this);
    btLib.addActionListener(this);
    btBaixa.addActionListener(this);
    btEstorno.addActionListener(this);
	btSair.addActionListener(this);
    
	Calendar cPeriodo = Calendar.getInstance();
    txtDatafim.setVlrDate(cPeriodo.getTime());
	cPeriodo.set(Calendar.DAY_OF_MONTH,cPeriodo.get(Calendar.DAY_OF_MONTH)-30);
	txtDataini.setVlrDate(cPeriodo.getTime());
    cbComPag.setVlrString("N");
    
  }
  private void pesq() {
    if (txtDataini.getText().trim().equals("")) {
		Funcoes.mensagemInforma(this,"Data inicial é requerido!");          
      return;
    }
    else if (txtDatafim.getText().trim().equals("")) {
		Funcoes.mensagemInforma(this,"Data final é requerido!");          
      return;
    }
    else if (txtCodVend.getText().trim().equals("")) {
		Funcoes.mensagemInforma(this,"Código do representante é requerido!");          
      return;
    }
    else if (txtDataini.getVlrDate().after(txtDatafim.getVlrDate())) {
		Funcoes.mensagemInforma(this,"Data inicial não pode ser maior que a data final!");          
      return;
    }
    String sStatus = "'CE'";

    if (cbComPag.getVlrString().equals("S")) {
      sStatus += ",'CP'";
    }
    if (cbLiberadas.getVlrString().equals("S")) {
      sStatus += ",'C2'";
    }
    if (cbNLiberadas.getVlrString().equals("S")) {
        sStatus += ",'C1'";
    }
    sStatus = " AND C.STATUSCOMI IN ("+sStatus+")";
    sEmitRel = rgEmitRel.getVlrString();
    
    String sSQL = "SELECT C.CODCOMI,C.STATUSCOMI,CL.RAZCLI,R.DOCREC,ITR.NPARCITREC,"+
       "C.VLRCOMI,C.DATACOMI,C.DTVENCCOMI,C.DTPAGTOCOMI,C.TIPOCOMI " +
       "FROM VDCOMISSAO C, VDCLIENTE CL,FNRECEBER R, FNITRECEBER ITR " +
       "WHERE R.CODEMPVD=? AND R.CODFILIALVD=? AND R.CODVEND = ? " +
       "AND ITR.CODREC = R.CODREC AND C.CODREC = ITR.CODREC AND " +
       (sEmitRel=="E" ? "C.DATACOMI" : "C.DTVENCCOMI")+" BETWEEN ? AND ? " +
       "AND CL.CODCLI=R.CODCLI"+sStatus+" AND ITR.CODEMP=C.CODEMPRC AND ITR.CODFILIAL=C.CODFILIALRC " +
       "AND R.CODEMP=C.CODEMPRC AND R.CODFILIAL=C.CODFILIALRC AND CL.CODEMP=R.CODEMPCL " +
       "AND CL.CODFILIAL=R.CODFILIALCL AND C.CODEMP=? AND C.CODFILIAL=? AND C.NPARCITREC = ITR.NPARCITREC " +
       "ORDER BY "+(sEmitRel=="E" ? "C.DATACOMI" : "C.DTVENCCOMI");  
     try {
       PreparedStatement ps = con.prepareStatement(sSQL);
       ps.setInt(1,Aplicativo.iCodEmp);
       ps.setInt(2,ListaCampos.getMasterFilial("VDVENDEDOR"));
       ps.setInt(3,txtCodVend.getVlrInteger().intValue());
       ps.setDate(4,Funcoes.dateToSQLDate(txtDataini.getVlrDate()));
       ps.setDate(5,Funcoes.dateToSQLDate(txtDatafim.getVlrDate()));
	   ps.setInt(6,Aplicativo.iCodEmp)      ;
	   ps.setInt(7,ListaCampos.getMasterFilial("VDCOMISSAO"));
       ResultSet rs = ps.executeQuery();
       tab.limpa();
       bVlrTot = new BigDecimal("0.0");
         bVlrTot.setScale(3);
       bVlrTotPago = new BigDecimal("0.0");
       vCodComi = new Vector();
       for (int i=0; rs.next(); i++) {
         tab.adicLinha();
         vCodComi.addElement(rs.getString("CodComi"));
         if ( rs.getString("StatusComi").equals("C1") )
           tab.setValor(new Boolean(false),i,0);
         else if ( (rs.getString("StatusComi").equals("C2")) || ( rs.getString("StatusComi").equals("CE") ) )
           tab.setValor(new Boolean(true),i,0);
         else if (rs.getString("StatusComi").equals("CP")) {
           tab.setValor(new Boolean(true),i,0);
           tab.setValor(Funcoes.sqlDateToStrDate(rs.getDate("DtPagtoComi")),i,8);
           bVlrTotPago = bVlrTotPago.add(new BigDecimal(rs.getString("VlrComi")));
         }
         tab.setValor(rs.getString("RazCli"),i,1);
         tab.setValor(rs.getString("DocRec"),i,2);
         tab.setValor(rs.getString("NParcItRec"),i,3);
         tab.setValor(rs.getString("TipoComi") != null ? rs.getString("TipoComi") : "",i,4);
         tab.setValor(Funcoes.strDecimalToStrCurrency(10,2,rs.getString("VlrComi")),i,5);
         tab.setValor(Funcoes.sqlDateToStrDate(rs.getDate("Datacomi")),i,6);
         tab.setValor(Funcoes.sqlDateToStrDate(rs.getDate("DtVencComi")),i,7);
         bVlrTot = bVlrTot.add((new BigDecimal(rs.getString("VlrComi"))));
       }
       txtTotComi.setVlrBigDecimal(bVlrTot);
       txtTotPg.setVlrBigDecimal(bVlrTotPago);
       calcTotal();
       rs.close();
       ps.close();
       if (!con.getAutoCommit())
       	  con.commit();
    }
    catch (SQLException err) {
		Funcoes.mensagemErro(this,"Erro na consulta!"+err.getMessage());
    }
  }
  private void liberar() {
    String sSQL = "UPDATE VDCOMISSAO SET STATUSCOMI='C2' WHERE STATUSCOMI='C1' AND CODCOMI=? AND CODEMP=? AND CODFILIAL=?";
    String sSQL2 = "UPDATE VDCOMISSAO SET STATUSCOMI='C1' WHERE STATUSCOMI='C2' AND CODCOMI=? AND CODEMP=? AND CODFILIAL=?";
    for (int i=0; i<tab.getNumLinhas(); i++) {
      PreparedStatement ps = null;
      try {
        if (((Boolean)tab.getValor(i,0)).booleanValue()) 
          ps = con.prepareStatement(sSQL);
        else 
          ps = con.prepareStatement(sSQL2);
        ps.setInt(1,Integer.parseInt((String)vCodComi.elementAt(i)));
		ps.setInt(2,Aplicativo.iCodEmp)      ;
		ps.setInt(3,ListaCampos.getMasterFilial("VDCOMISSAO"));
        ps.executeUpdate();
//        ps.close();
        if (!con.getAutoCommit())
        	con.commit();
      }
      catch (SQLException err) {
		Funcoes.mensagemErro(this,"Erro ao atualizar os status na tabela COMISSÃO!\n"+err.getMessage());
      }
    }
    calcTotal();
  }
  private void calcTotal() {
    BigDecimal bVal = new BigDecimal("0");
    for (int i=0; i<tab.getNumLinhas(); i++) {
      if (((Boolean)tab.getValor(i,0)).booleanValue())
        bVal = bVal.add(Funcoes.strCurrencyToBigDecimal((String)tab.getValor(i,5)));
    }
    txtTotLib.setVlrBigDecimal(bVal);
  }
  private void baixar() {
    DLBaixaComis dl = new DLBaixaComis(this,con,sEmitRel,txtDataini.getVlrDate(),txtDatafim.getVlrDate(),txtCodVend.getVlrInteger());
    dl.setConexao(con);
    dl.setVisible(true);
    if (dl.OK) {
      String[] sVals = dl.getValores();
      String sSQL = "EXECUTE PROCEDURE VDBAIXACOMISSAOSP(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      try {
        PreparedStatement ps = con.prepareStatement(sSQL);
        ps.setString(1,sEmitRel);
        ps.setDate(2,Funcoes.dateToSQLDate(txtDataini.getVlrDate()));
        ps.setDate(3,Funcoes.dateToSQLDate(txtDatafim.getVlrDate()));
        ps.setInt(4,txtCodVend.getVlrInteger().intValue());
		ps.setInt(5,Aplicativo.iCodEmp);
		ps.setInt(6,ListaCampos.getMasterFilial("VDVENDEDOR"));
        ps.setString(7,sVals[0]);
		ps.setInt(8,Aplicativo.iCodEmp);
		ps.setInt(9,ListaCampos.getMasterFilial("FNCONTA"));
        ps.setString(10,sVals[1]);
		ps.setInt(11,Aplicativo.iCodEmp);
		ps.setInt(12,ListaCampos.getMasterFilial("FNPLANEJAMENTO"));
        ps.setDate(13,Funcoes.strDateToSqlDate(sVals[2]));
        ps.setInt(14,Integer.parseInt(sVals[3]));
        ps.setBigDecimal(15,Funcoes.strCurrencyToBigDecimal(sVals[4]));
        ps.setString(16,sVals[5]);
		ps.setInt(17,Aplicativo.iCodEmp);
		ps.setInt(18,ListaCampos.getMasterFilial("FNRECEBER"));
        ps.execute();
        if (!con.getAutoCommit())
        	con.commit();
      }
      catch (SQLException err) {
		Funcoes.mensagemErro(this,"Erro ao baixar a comissão!\n"+err.getMessage());
      }
      dl.dispose();
    }
    else 
      dl.dispose();
  }
  private void estornar() {
  	String sSQL = null;
  	try {
  		if (txtCodVend.getVlrInteger().intValue()==0) {
  			Funcoes.mensagemInforma(this,"Selecione o comissionado!");
  			txtCodVend.requestFocus();
  			return;
  		}
  		if (txtDataini.getVlrString().trim().equals("")) {
  			Funcoes.mensagemInforma(this,"Selecione a data inicial!");
  			txtDataini.requestFocus();
  			return;
  		}
  		if (txtDatafim.getVlrString().trim().equals("")) {
  			Funcoes.mensagemInforma(this,"Selecione a data final!");
  			txtDatafim.requestFocus();
  			return;
  		}
        if (cbComPag.getVlrString().equals("N")) {
        	Funcoes.mensagemInforma(this,"Comissões pagas não foram selecionadas");
        	cbComPag.requestFocus();
        	return;
        }
  		if (Funcoes.mensagemConfirma(this,"Confirma estorno de comissões?")!=JOptionPane.YES_OPTION)
  	  		return;
  	    sSQL = "EXECUTE PROCEDURE VDDESBAIXACOMISSAOSP(?,?,?,?,?,?)";
  		PreparedStatement ps = con.prepareStatement(sSQL);
        ps.setInt(1,Aplicativo.iCodEmp);
        ps.setInt(2,ListaCampos.getMasterFilial("VDVENDEDOR"));
        ps.setInt(3,txtCodVend.getVlrInteger().intValue());
        ps.setString(4,rgEmitRel.getVlrString());
        ps.setDate(5,Funcoes.dateToSQLDate(txtDataini.getVlrDate()));
        ps.setDate(6,Funcoes.dateToSQLDate(txtDatafim.getVlrDate()));
        ps.executeUpdate();

        ps.close();
        
        if (!con.getAutoCommit()) {
        	con.commit();
        }
        
  	}
  	catch (SQLException e) {
		Funcoes.mensagemErro(this,"Erro ao estornar baixas!\n"+e.getMessage());
  	}	
  	finally {
  		sSQL = null;
  	}
  }
  public void actionPerformed(ActionEvent evt) {
	if (evt.getSource() == btSair) 
	  dispose();
    else if (evt.getSource() == btBusca) 
      pesq();
    else if (evt.getSource() == btCalc) 
      calcTotal();
    else if (evt.getSource() == btLib) 
      liberar();
    else if (evt.getSource() == btBaixa) 
      baixar();
    else if (evt.getSource() == btEstorno) 
      estornar();
  }          
  public void setConexao(Connection cn) {
    super.setConexao(cn);
    lcVend.setConexao(cn);
  }
}
