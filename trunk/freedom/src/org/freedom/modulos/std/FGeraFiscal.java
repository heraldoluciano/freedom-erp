 /**
 * @version 11/02/2002 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FGeraFiscal.java <BR>
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
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.Timer;

import org.freedom.bmps.Icone;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Painel;
import org.freedom.componentes.Tabela;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FFilho;

public class FGeraFiscal extends FFilho implements ActionListener {
  private Painel pinCliente = new Painel(600,110);
  private JPanel pnGrid = new JPanel(new GridLayout(2,1));
  private JTextFieldPad txtDataini = new JTextFieldPad(JTextFieldPad.TP_DATE,0,10);
  private JTextFieldPad txtDatafim = new JTextFieldPad(JTextFieldPad.TP_DATE,0,10);
  private JCheckBoxPad cbEntrada = new JCheckBoxPad("Entrada","S","N");
  private JCheckBoxPad cbSaida = new JCheckBoxPad("Saida","S","N");
  private Tabela tab1 = new Tabela();
  private JScrollPane spnTab1 = new JScrollPane(tab1);
  private Tabela tab2 = new Tabela();
  private JScrollPane spnTab2 = new JScrollPane(tab2);
  private JProgressBar pbAnd = new JProgressBar();
  private JButton btVisual = new JButton(Icone.novo("btPesquisa.gif"));
  private JButton btChecar = new JButton(Icone.novo("btExecuta.gif"));
  private JButton btGerar = new JButton(Icone.novo("btGerar.gif"));
  private Timer tim = null;
  private int iAnd = 0;
  private int iTotCompras = 0;
  private int iTotVendas = 0;
  private JLabel lbAnd = new JLabel("Aguardando:");
  public FGeraFiscal() {
    setTitulo("Gerar Livros Fiscais");      
    setAtribos(50,50,610,400);
    
    btVisual.setToolTipText("Visualizar");
    btChecar.setToolTipText("Checar");
    btGerar.setToolTipText("Gerar");
    
    btChecar.setEnabled(false);
    btGerar.setEnabled(false);
    
    Container c = getContentPane();
    c.setLayout(new BorderLayout());
    
    adicBotaoSair();
    
    c.add(pinCliente,BorderLayout.NORTH);
    c.add(pnGrid,BorderLayout.CENTER);
    
    txtDataini.setRequerido(true);
    txtDatafim.setRequerido(true);
    
    pinCliente.adic(new JLabel("Inicio"),7,0,110,25);
    pinCliente.adic(txtDataini,7,20,110,20);
    pinCliente.adic(new JLabel("Fim"),120,0,107,25);
    pinCliente.adic(txtDatafim,120,20,107,20);
    pinCliente.adic(btVisual,230,15,30,30);
    pinCliente.adic(btChecar,263,15,30,30);
    pinCliente.adic(btGerar,296,15,30,30);
    pinCliente.adic(cbEntrada,7,50,150,20);
    pinCliente.adic(cbSaida,170,50,150,20);
    pinCliente.adic(lbAnd,7,80,110,20);
    pinCliente.adic(pbAnd,120,80,210,20);

    pnGrid.add(spnTab1);
    pnGrid.add(spnTab2);
    
    tab1.adicColuna("Dt.emissão");
    tab1.adicColuna("Dt.entrada");
    tab1.adicColuna("Nat.oper.");
    tab1.adicColuna("Cód.for.");
    tab1.adicColuna("UF.");
    tab1.adicColuna("Espécie");
    tab1.adicColuna("Mod.nota");
    tab1.adicColuna("Série");
    tab1.adicColuna("Doc.");
    tab1.adicColuna("%Icms");
    tab1.adicColuna("%Ipi");
    tab1.adicColuna("V.contabil");
    tab1.adicColuna("V.base Icms");
    tab1.adicColuna("V.Icms");
    tab1.adicColuna("V.isentas");
    tab1.adicColuna("V.outras");
    tab1.adicColuna("V.base Ipi");
    tab1.adicColuna("V.Ipi");
    tab1.adicColuna("E1");
    tab1.adicColuna("F1");
    tab1.adicColuna("E2");
    tab1.adicColuna("F2");
    tab1.adicColuna("E3");
    tab1.adicColuna("F3");
    
    tab1.setTamColuna(100,0);
    tab1.setTamColuna(100,1);
    tab1.setTamColuna(70,2);
    tab1.setTamColuna(70,3);
    tab1.setTamColuna(40,4);
    tab1.setTamColuna(70,5);
    tab1.setTamColuna(80,6);
    tab1.setTamColuna(50,7);
    tab1.setTamColuna(70,8);
    tab1.setTamColuna(70,9);
    tab1.setTamColuna(70,10);
    tab1.setTamColuna(100,11);
    tab1.setTamColuna(100,12);
    tab1.setTamColuna(100,13);
    tab1.setTamColuna(100,14);
    tab1.setTamColuna(100,15);
    tab1.setTamColuna(100,16);
    tab1.setTamColuna(100,17);
    tab1.setTamColuna(20,18);
	tab1.setTamColuna(20,19);
	tab1.setTamColuna(20,20);
	tab1.setTamColuna(20,21);
	tab1.setTamColuna(20,22);
	tab1.setTamColuna(20,23);
    
    tab2.adicColuna("Dt.emissão");
    tab2.adicColuna("Dt.saída");
    tab2.adicColuna("Nat.oper.");
    tab2.adicColuna("Cód.For.");
    tab2.adicColuna("UF.");
    tab2.adicColuna("Espécie");
    tab2.adicColuna("Mod.nota");
    tab2.adicColuna("Série");
    tab2.adicColuna("Doc.");
    tab2.adicColuna("%Icms");
    tab2.adicColuna("%Ipi");
    tab2.adicColuna("V.contabil");
    tab2.adicColuna("V.base Icms");
    tab2.adicColuna("V.Icms");
    tab2.adicColuna("V.isentas");
    tab2.adicColuna("V.outras");
    tab2.adicColuna("V.base Ipi");
    tab2.adicColuna("V.Ipi");
	tab2.adicColuna("E1");
	tab2.adicColuna("F1");
	tab2.adicColuna("E2");
	tab2.adicColuna("F2");
	tab2.adicColuna("E3");
	tab2.adicColuna("F3");
    
    tab2.setTamColuna(100,0);
    tab2.setTamColuna(100,1);
    tab2.setTamColuna(70,2);
    tab2.setTamColuna(70,3);
    tab2.setTamColuna(40,4);
    tab2.setTamColuna(70,5);
    tab2.setTamColuna(80,6);
    tab2.setTamColuna(50,7);
    tab2.setTamColuna(70,8);
    tab2.setTamColuna(70,9);
    tab2.setTamColuna(70,10);
    tab2.setTamColuna(100,11);
    tab2.setTamColuna(100,12);
    tab2.setTamColuna(100,13);
    tab2.setTamColuna(100,14);
    tab2.setTamColuna(100,15);
    tab2.setTamColuna(100,16);
    tab2.setTamColuna(100,17);
	tab2.setTamColuna(20,18);
	tab2.setTamColuna(20,19);
	tab2.setTamColuna(20,20);
	tab2.setTamColuna(20,21);
	tab2.setTamColuna(20,22);
	tab2.setTamColuna(20,23);

    colocaMes();   
    btVisual.addActionListener(this);
    btChecar.addActionListener(this);
    btGerar.addActionListener(this);
    
    pbAnd.setStringPainted(true);
  }
  
  private void colocaMes() {
	GregorianCalendar cData = new GregorianCalendar();
	GregorianCalendar cDataIni = new GregorianCalendar();
	GregorianCalendar cDataFim = new GregorianCalendar();
	cDataIni.set(Calendar.MONTH,cData.get(Calendar.MONTH)-1);
	cDataIni.set(Calendar.DATE,1);
	cDataFim.set(Calendar.DATE,-1);
	txtDataini.setVlrDate(cDataIni.getTime());
	txtDatafim.setVlrDate(cDataFim.getTime());
  	
  }
  
/*  private void colocaTrimestre() {
    int iMesAtual = 0;
    GregorianCalendar cData = new GregorianCalendar();
    GregorianCalendar cDataIni = new GregorianCalendar();
    GregorianCalendar cDataFim = new GregorianCalendar();
    iMesAtual = cData.get(Calendar.MONTH)+1;
    if (iMesAtual < 4) {
      cDataIni = new GregorianCalendar(cData.get(Calendar.YEAR)-1,10-1,1);
      cDataFim = new GregorianCalendar(cData.get(Calendar.YEAR)-1,12-1,31);
    }
    else if ((iMesAtual > 3) & (iMesAtual < 7)) {
      cDataIni = new GregorianCalendar(cData.get(Calendar.YEAR),1-1,1);
      cDataFim = new GregorianCalendar(cData.get(Calendar.YEAR),3-1,31);
    }
    else if ((iMesAtual > 6) & (iMesAtual < 10)) {
      cDataIni = new GregorianCalendar(cData.get(Calendar.YEAR),4-1,1);
      cDataFim = new GregorianCalendar(cData.get(Calendar.YEAR),6-1,30);
    }
    else if (iMesAtual > 9) {
      cDataIni = new GregorianCalendar(cData.get(Calendar.YEAR),7-1,1);
      cDataFim = new GregorianCalendar(cData.get(Calendar.YEAR),9-1,30);
    }
    txtDataini.setVlrDate(cDataIni.getTime());
    txtDatafim.setVlrDate(cDataFim.getTime());
    
  } */
  public void iniGerar() {
    Thread th = new Thread(
      new Runnable() {
        public void run() {
          gerar();
        }
      }
    );
    try {
      th.start();
    }
    catch(Exception err) {
		Funcoes.mensagemErro(this,"Não foi possível criar processo!\n"+err.getMessage());
    }
  }
  
  private void visualizar() {
    if (!valida()) {
       return;
    }
    try {
      String sSQL = "SELECT C.DTEMITCOMPRA,C.DTENTCOMPRA,IT.CODNAT,"+
                    "C.CODFOR,F.UFFOR,TM.ESPECIETIPOMOV,TM.CODMODNOTA,"+
                    "C.SERIE,C.DOCCOMPRA,IT.PERCICMSITCOMPRA,"+
                    "IT.PERCIPIITCOMPRA," +
                    "C.CODEMPFR,C.CODFILIALFR," +
                    "IT.CODEMPNT,IT.CODFILIALNT," +
                    "TM.CODEMPMN,TM.CODFILIALMN,"+
                    "SUM(VLRPRODITCOMPRA),"+
                    "SUM(IT.VLRBASEICMSITCOMPRA),"+
                    "SUM(IT.VLRICMSITCOMPRA),"+
                    "SUM(IT.VLRISENTASITCOMPRA),"+
                    "SUM(IT.VLROUTRASITCOMPRA),"+
                    "SUM(IT.VLRBASEIPIITCOMPRA),"+
                    "SUM(IT.VLRIPIITCOMPRA) "+
                    "FROM CPCOMPRA C, CPITCOMPRA IT, CPFORNECED F, EQTIPOMOV TM "+
                    "WHERE C.DTENTCOMPRA BETWEEN ? AND ? AND "+
                    "C.CODEMP=? AND C.CODFILIAL=? AND "+
                    "IT.CODCOMPRA=C.CODCOMPRA AND IT.CODEMP=C.CODEMP AND " +
                    "IT.CODFILIAL=C.CODFILIAL AND "+
                    "F.CODFOR = C.CODFOR AND F.CODEMP=C.CODEMPFR AND " +
                    "F.CODFILIAL=C.CODFILIALFR AND "+
                    "TM.CODTIPOMOV=C.CODTIPOMOV AND TM.CODEMP=C.CODEMPTM AND "+
                    "TM.CODFILIAL=C.CODFILIALTM AND TM.FISCALTIPOMOV='S' "+
                    "GROUP BY C.DTEMITCOMPRA,C.DTENTCOMPRA,IT.CODNAT,"+
                    "C.CODFOR,F.UFFOR,TM.ESPECIETIPOMOV,TM.CODMODNOTA,"+
                    "C.SERIE,C.DOCCOMPRA,IT.PERCICMSITCOMPRA,"+
                    "IT.PERCIPIITCOMPRA,"+
					"C.CODEMPFR,C.CODFILIALFR," +
					"IT.CODEMPNT,IT.CODFILIALNT," +
					"TM.CODEMPMN,TM.CODFILIALMN";

      if (cbEntrada.getVlrString().equals("S")) {
        PreparedStatement ps = con.prepareStatement(sSQL);
        ps.setDate(1,Funcoes.dateToSQLDate(txtDataini.getVlrDate()));
        ps.setDate(2,Funcoes.dateToSQLDate(txtDatafim.getVlrDate()));
        ps.setInt(3,Aplicativo.iCodEmp);
        ps.setInt(4,ListaCampos.getMasterFilial("CPCOMPRA"));
        ResultSet rs = ps.executeQuery();
        tab1.limpa();
        iTotCompras = 0;
        while (rs.next()) {
          tab1.adicLinha();
          
          tab1.setValor(Funcoes.sqlDateToStrDate(rs.getDate(1)),iTotCompras,0);
          tab1.setValor(Funcoes.sqlDateToStrDate(rs.getDate(2)),iTotCompras,1);
          tab1.setValor((rs.getString(3)!=null?rs.getString(3):""),iTotCompras,2);
          tab1.setValor(""+rs.getInt(4),iTotCompras,3);
          tab1.setValor((rs.getString(5)!=null?rs.getString(5):""),iTotCompras,4);
          tab1.setValor((rs.getString(6)!=null?rs.getString(6):""),iTotCompras,5);
          tab1.setValor(""+rs.getInt(7),iTotCompras,6);
          tab1.setValor((rs.getString(8)!=null?rs.getString(8):""),iTotCompras,7);
          tab1.setValor(""+rs.getInt(9),iTotCompras,8);
          tab1.setValor(Funcoes.strDecimalToStrCurrency(6,2,rs.getString(10)!=null?rs.getString(10):""),iTotCompras,9);
          tab1.setValor(Funcoes.strDecimalToStrCurrency(6,2,rs.getString(11)!=null?rs.getString(11):""),iTotCompras,10);
          tab1.setValor(Funcoes.strDecimalToStrCurrency(15,2,rs.getString(18)!=null?rs.getString(18):""),iTotCompras,11);
          tab1.setValor(Funcoes.strDecimalToStrCurrency(15,2,rs.getString(19)!=null?rs.getString(19):""),iTotCompras,12);
          tab1.setValor(Funcoes.strDecimalToStrCurrency(15,2,rs.getString(20)!=null?rs.getString(20):""),iTotCompras,13);
          tab1.setValor(Funcoes.strDecimalToStrCurrency(15,2,rs.getString(21)!=null?rs.getString(21):""),iTotCompras,14);
          tab1.setValor(Funcoes.strDecimalToStrCurrency(15,2,rs.getString(22)!=null?rs.getString(22):""),iTotCompras,15);
          tab1.setValor(Funcoes.strDecimalToStrCurrency(15,2,rs.getString(23)!=null?rs.getString(23):""),iTotCompras,16);
          tab1.setValor(Funcoes.strDecimalToStrCurrency(15,2,rs.getString(24)!=null?rs.getString(24):""),iTotCompras,17);
		  tab1.setValor(""+rs.getInt(12),iTotCompras,18);
		  tab1.setValor(""+rs.getInt(13),iTotCompras,19);
		  tab1.setValor(""+rs.getInt(14),iTotCompras,20);
		  tab1.setValor(""+rs.getInt(15),iTotCompras,21);
		  tab1.setValor(""+rs.getInt(16),iTotCompras,22);
		  tab1.setValor(""+rs.getInt(17),iTotCompras,23);
          iTotCompras++;
        }
        
//        rs.close();
//        ps.close();
        if (!con.getAutoCommit())
        	con.commit();
      
      }

      if (cbSaida.getVlrString().equals("S")) {
      
        sSQL = "SELECT V.DTEMITVENDA,V.DTSAIDAVENDA,IV.CODNAT,"+
             "V.CODCLI,C.UFCLI,TM.ESPECIETIPOMOV,TM.CODMODNOTA,"+
             "V.SERIE,V.DOCVENDA,IV.PERCICMSITVENDA,IV.PERCIPIITVENDA," +
             "V.CODEMPCL,V.CODFILIALCL," +
             "IV.CODEMPNT,IV.CODFILIALNT," +
             "TM.CODEMPMN,TM.CODFILIALMN,"+
             "SUM(IV.VLRPRODITVENDA),SUM(IV.VLRBASEICMSITVENDA),"+
             "SUM(IV.VLRICMSITVENDA),SUM(IV.VLRISENTASITVENDA),"+
             "SUM(IV.VLROUTRASITVENDA),SUM(IV.VLRBASEIPIITVENDA),"+
             "SUM(IV.VLRIPIITVENDA) "+
             "FROM VDVENDA V,VDITVENDA IV, EQTIPOMOV TM, VDCLIENTE C "+
             "WHERE V.DTEMITVENDA BETWEEN ? AND ? AND " +
             "V.CODEMP=? AND V.CODFILIAL=? AND "+
             "IV.CODVENDA=V.CODVENDA AND IV.CODEMP=V.CODEMP AND " +
             "IV.CODFILIAL=V.CODFILIAL AND "+
             "TM.CODTIPOMOV=V.CODTIPOMOV AND TM.CODEMP=V.CODEMPTM AND " +
             "TM.CODFILIAL=V.CODFILIALTM AND TM.FISCALTIPOMOV='S' AND "+
             "C.CODCLI=V.CODCLI AND C.CODEMP=V.CODEMPCL AND C.CODFILIAL=V.CODFILIALCL "+
             "GROUP BY V.DTEMITVENDA,V.DTSAIDAVENDA,IV.CODNAT,"+
             "V.CODCLI,C.UFCLI,TM.ESPECIETIPOMOV,TM.CODMODNOTA,"+
             "V.SERIE,V.DOCVENDA,IV.PERCICMSITVENDA,IV.PERCIPIITVENDA,"+
			 "V.CODEMPCL,V.CODFILIALCL," +
			 "IV.CODEMPNT,IV.CODFILIALNT," +
			 "TM.CODEMPMN,TM.CODFILIALMN";

        PreparedStatement ps2 = con.prepareStatement(sSQL);
        ps2.setDate(1,Funcoes.dateToSQLDate(txtDataini.getVlrDate()));
        ps2.setDate(2,Funcoes.dateToSQLDate(txtDatafim.getVlrDate()));
        ps2.setInt(3,Aplicativo.iCodEmp);
        ps2.setInt(4,ListaCampos.getMasterFilial("VDVENDA"));
        ResultSet rs2 = ps2.executeQuery();
        iTotVendas = 0;
        tab2.limpa();
        while (rs2.next()) {
          tab2.adicLinha();
          tab2.setValor(Funcoes.sqlDateToStrDate(rs2.getDate(1)),iTotVendas,0);
          tab2.setValor(Funcoes.sqlDateToStrDate(rs2.getDate(2)),iTotVendas,1);
          tab2.setValor(rs2.getString(3)!=null?rs2.getString(3):"",iTotVendas,2);
          tab2.setValor(""+rs2.getInt(4),iTotVendas,3);
          tab2.setValor(rs2.getString(5)!=null?rs2.getString(5):"",iTotVendas,4);
          tab2.setValor(rs2.getString(6)!=null?rs2.getString(6):"",iTotVendas,5);
          tab2.setValor(""+rs2.getInt(7),iTotVendas,6);
          tab2.setValor(rs2.getString(8)!=null?rs2.getString(8):"",iTotVendas,7);
          tab2.setValor(""+rs2.getInt(9),iTotVendas,8);
          tab2.setValor(Funcoes.strDecimalToStrCurrency(6,2,rs2.getString(10)),iTotVendas,9);
          tab2.setValor(Funcoes.strDecimalToStrCurrency(6,2,rs2.getString(11)),iTotVendas,10);
          tab2.setValor(Funcoes.strDecimalToStrCurrency(15,2,rs2.getString(18)),iTotVendas,11);
          tab2.setValor(Funcoes.strDecimalToStrCurrency(15,2,rs2.getString(19)),iTotVendas,12);
          tab2.setValor(Funcoes.strDecimalToStrCurrency(15,2,rs2.getString(20)),iTotVendas,13);
          tab2.setValor(Funcoes.strDecimalToStrCurrency(15,2,rs2.getString(21)),iTotVendas,14);
          tab2.setValor(Funcoes.strDecimalToStrCurrency(15,2,rs2.getString(22)),iTotVendas,15);
          tab2.setValor(Funcoes.strDecimalToStrCurrency(15,2,rs2.getString(23)),iTotVendas,16);
          tab2.setValor(Funcoes.strDecimalToStrCurrency(15,2,rs2.getString(24)),iTotVendas,17);
		  tab2.setValor(""+rs2.getInt(12),iTotVendas,18);
		  tab2.setValor(""+rs2.getInt(13),iTotVendas,19);
		  tab2.setValor(""+rs2.getInt(14),iTotVendas,20);
		  tab2.setValor(""+rs2.getInt(15),iTotVendas,21);
		  tab2.setValor(""+rs2.getInt(16),iTotVendas,22);
		  tab2.setValor(""+rs2.getInt(17),iTotVendas,23);
          iTotVendas++;
        }
//        rs2.close();
//        ps2.close();
        if (!con.getAutoCommit())
        	con.commit();
      }
    }
    catch (SQLException err) {
		Funcoes.mensagemErro(this,"Erro ao realizar consulta!\n"+err.getMessage());
      return;
    }
    btGerar.setEnabled(false);
    if ( (iTotVendas>0) | (iTotCompras>0) ) {
       btChecar.setEnabled(true);
    }
  }
  
  private void gerar() {
    if ((iTotVendas+iTotCompras)<=0){
       btGerar.setEnabled(false);
       return;
    }
    int iQuant = 0;    
    iAnd = 0;
    String sSql = "";
    try {
       if (iTotCompras>0) {     
         sSql = "SELECT COUNT(*) FROM LFLIVROFISCAL WHERE TIPOLF='E' AND DTESLF BETWEEN ? AND ? AND CODEMP=? AND CODFILIAL=? ";
         PreparedStatement psA = con.prepareStatement(sSql);
         psA.setDate(1,Funcoes.dateToSQLDate(txtDataini.getVlrDate()));
         psA.setDate(2,Funcoes.dateToSQLDate(txtDatafim.getVlrDate()));
         psA.setInt(3,Aplicativo.iCodEmp);
         psA.setInt(4,ListaCampos.getMasterFilial("LFLIVROFISCAL"));
         ResultSet rsA = psA.executeQuery();
         iQuant = 0;
         if (rsA.next()) {
            iQuant = rsA.getInt(1);    
         };
//         rsA.close();
//         psA.close();
         if (!con.getAutoCommit())
         	con.commit();
         if (iQuant>0) {
           sSql = "DELETE FROM LFLIVROFISCAL WHERE TIPOLF='E' AND DTESLF BETWEEN ? AND ? AND CODEMP=? AND CODFILIAL=?";
           PreparedStatement psB = con.prepareStatement(sSql);
           psB.setDate(1,Funcoes.dateToSQLDate(txtDataini.getVlrDate()));
           psB.setDate(2,Funcoes.dateToSQLDate(txtDatafim.getVlrDate()));
           psB.setInt(3,Aplicativo.iCodEmp);
           psB.setInt(4,ListaCampos.getMasterFilial("LFLIVROFISCAL"));
           psB.executeUpdate();
//           psB.close();
           if (!con.getAutoCommit())
           	  con.commit();
         }
         
       }
       // Livros fiscais de saída
       if (iTotVendas>0) {
         sSql = "SELECT COUNT(*) FROM LFLIVROFISCAL WHERE TIPOLF='S' AND DTEMITLF BETWEEN ? AND ? AND CODEMP=? AND CODFILIAL=?";
         PreparedStatement psC = con.prepareStatement(sSql);
         psC.setDate(1,Funcoes.dateToSQLDate(txtDataini.getVlrDate()));
         psC.setDate(2,Funcoes.dateToSQLDate(txtDatafim.getVlrDate()));
         psC.setInt(3,Aplicativo.iCodEmp);
         psC.setInt(4,ListaCampos.getMasterFilial("LFLIVROFISCAL"));
         ResultSet rsC = psC.executeQuery();
         iQuant = 0;
         if (rsC.next()) {
            iQuant = rsC.getInt(1);    
         };
//         rsC.close();
//         psC.close();
         if (!con.getAutoCommit())
         	con.commit();
         if (iQuant>0) {
           sSql = "DELETE FROM LFLIVROFISCAL WHERE TIPOLF='S' AND DTEMITLF BETWEEN ? AND ?";
           PreparedStatement psD = con.prepareStatement(sSql);
           psD.setDate(1,Funcoes.dateToSQLDate(txtDataini.getVlrDate()));
           psD.setDate(2,Funcoes.dateToSQLDate(txtDatafim.getVlrDate()));
           psD.executeUpdate();
//           psD.close();
           if (!con.getAutoCommit())
           	con.commit();
         }
       }
       
    }
    catch (SQLException err) {
		Funcoes.mensagemErro(this,"Consulta não foi executada\n"+err.getMessage());
       return;
    }
    
    
    tim = new Timer(300,this);
    pbAnd.setMinimum(0);      
//    System.out.println("V: "+iTotVendas+" | C: "+iTotCompras+"\n");
    pbAnd.setMaximum(iTotVendas+iTotCompras);    
    tim.start();
    lbAnd.setText("Gerando...");
    
    sSql = "INSERT INTO LFLIVROFISCAL (CODEMP,CODFILIAL,TIPOLF,ANOMESLF,CODLF,CODEMITLF,SERIELF,"+
        "DOCINILF,DTEMITLF,DTESLF,CODNAT,DOCFIMLF,ESPECIELF,UFLF,VLRCONTABILLF,"+
        "VLRBASEICMSLF,ALIQICMSLF,VLRICMSLF,VLRISENTASICMSLF,VLROUTRASICMSLF,"+
        "VLRBASEIPILF,ALIQIPILF,VLRIPILF,VLRISENTASIPILF,VLROUTRASIPILF,CODMODNOTA," +
        "CODEMPET,CODFILIALET,CODEMPNT,CODFILIALNT,CODEMPMN,CODFILIALMN) "+
        "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    PreparedStatement psI;
        
    for (int i=0; i<iTotCompras ; i++) {
      try {
         psI = con.prepareStatement(sSql);
         psI.setInt(1,Aplicativo.iCodEmp);
         psI.setInt(2,ListaCampos.getMasterFilial("LFLIVROFISCAL"));
         psI.setString(3,"E");
         psI.setString(4,((""+tab1.getValor(i,1)).substring(6,10)+(""+tab1.getValor(i,1)).substring(3,5)).trim());
         psI.setInt(5,i);
         psI.setInt(6,(tab1.getValor(i,3)+"").equals("")?0:Integer.parseInt((tab1.getValor(i,3)+"")));
         psI.setString(7,tab1.getValor(i,7)+"");
         psI.setInt(8,(tab1.getValor(i,8)+"").equals("")?0:Integer.parseInt((tab1.getValor(i,8)+"")));
         psI.setDate(9,Funcoes.strDateToSqlDate(tab1.getValor(i,0)+""));
         psI.setDate(10,Funcoes.strDateToSqlDate(tab1.getValor(i,1)+""));
         psI.setString(11,tab1.getValor(i,2)+"");
         psI.setInt(12,(tab1.getValor(i,8)+"").equals("")?0:Integer.parseInt((tab1.getValor(i,8)+"")));
         psI.setString(13,Funcoes.adicionaEspacos(tab1.getValor(i,5)+"",3));
         psI.setString(14,tab1.getValor(i,4)+"");
         psI.setBigDecimal(15,Funcoes.strCurrencyToBigDecimal(tab1.getValor(i,11)+"")); // VLRCONTABIL
         psI.setBigDecimal(16,Funcoes.strCurrencyToBigDecimal(tab1.getValor(i,12)+"")); // VLRBASEICMS
         psI.setBigDecimal(17,Funcoes.strCurrencyToBigDecimal(tab1.getValor(i,9)+"")); // ALIQICMS
         psI.setBigDecimal(18,Funcoes.strCurrencyToBigDecimal(tab1.getValor(i,13)+"")); // VLRICMS
         psI.setBigDecimal(19,Funcoes.strCurrencyToBigDecimal(tab1.getValor(i,14)+"")); // VLRISENTAS ICMS
         psI.setBigDecimal(20,Funcoes.strCurrencyToBigDecimal(tab1.getValor(i,15)+"")); // VLROUTRAS ICMS
         psI.setBigDecimal(21,Funcoes.strCurrencyToBigDecimal(tab1.getValor(i,16)+"")); // VLRBASEIPI    
         psI.setBigDecimal(22,Funcoes.strCurrencyToBigDecimal(tab1.getValor(i,10)+"")); // ALIQIPI
         psI.setBigDecimal(23,Funcoes.strCurrencyToBigDecimal(tab1.getValor(i,17)+"")); // VLRIPI
         psI.setBigDecimal(24,Funcoes.strCurrencyToBigDecimal("0")); // VLRISENTAS IPI
         psI.setBigDecimal(25,Funcoes.strCurrencyToBigDecimal("0")); // VLRIOUTRAS IPI 
         psI.setInt(26,(tab1.getValor(i,6)+"").equals("")?0:Integer.parseInt((tab1.getValor(i,6)+""))); // MODELO DE NOTA FISCAL
         if ( (!(tab1.getValor(i,18)+"").equals("")) || 
              (!(tab1.getValor(i,18)+"").equals("0")) )
             psI.setInt(27,Integer.parseInt(tab1.getValor(i,18)+"")); // CODEMPET 
         else 
          	 psI.setNull(27,Types.INTEGER); 
         if ( (!(tab1.getValor(i,19)+"").equals("")) || 
			 (!(tab1.getValor(i,19)+"").equals("0")) )
			psI.setInt(28,Integer.parseInt(tab1.getValor(i,19)+"")); // CODFILIALET 
         else 
         	psI.setNull(28,Types.INTEGER); 
         if ( (!(tab1.getValor(i,20)+"").equals("")) || 
			 (!(tab1.getValor(i,20)+"").equals("0")) )
			psI.setInt(29,Integer.parseInt(tab1.getValor(i,20)+"")); // CODEMPNT 
         else 
         	psI.setNull(29,Types.INTEGER); 
         if ( (!(tab1.getValor(i,21)+"").equals("")) || 
			 (!(tab1.getValor(i,21)+"").equals("0")) )
			psI.setInt(30,Integer.parseInt(tab1.getValor(i,21)+"")); // CODFILIALNT 
         else 
         	psI.setNull(30,Types.INTEGER); 
         if ( (!(tab1.getValor(i,22)+"").equals("")) || 
			 (!(tab1.getValor(i,22)+"").equals("0")) )
			psI.setInt(31,Integer.parseInt(tab1.getValor(i,22)+"")); // CODEMPMN 
         else 
         	psI.setNull(31,Types.INTEGER); 
         if ( (!(tab1.getValor(i,23)+"").equals("")) || 
			 (!(tab1.getValor(i,23)+"").equals("0")) )
			psI.setInt(32,Integer.parseInt(tab1.getValor(i,23)+"")); // CODFILIALMN 
         else 
         	psI.setNull(32,Types.INTEGER); 
         psI.executeUpdate();     
//         psI.close();
         if (!con.getAutoCommit())
         	con.commit();
       }
       catch (SQLException err) {
		Funcoes.mensagemErro(this,"Erro gerando livros fiscais de compras!\n"+err.getMessage());
         break;
       }
       iAnd++;
//       System.out.println("And1:"+iAnd+"\n");
    };
    
    
    for (int i=0; i<iTotVendas ; i++) {
      try {
         psI = con.prepareStatement(sSql);
         psI.setInt(1,Aplicativo.iCodEmp);
         psI.setInt(2,ListaCampos.getMasterFilial("LFLIVROFISCAL"));
         psI.setString(3,"S");
         psI.setString(4,((""+tab2.getValor(i,0)).substring(6,10)+(""+tab2.getValor(i,0)).substring(3,5)).trim());
         psI.setInt(5,i);
         psI.setInt(6,(tab2.getValor(i,3)+"").equals("")?0:Integer.parseInt((tab2.getValor(i,3)+"")));
         psI.setString(7,tab2.getValor(i,7)+"");
         psI.setInt(8,(tab2.getValor(i,8)+"").equals("")?0:Integer.parseInt((tab2.getValor(i,8)+"")));
         psI.setDate(9,Funcoes.strDateToSqlDate(tab2.getValor(i,0)+""));
         psI.setDate(10,Funcoes.strDateToSqlDate(tab2.getValor(i,1)+""));
         psI.setString(11,tab2.getValor(i,2)+"");
         psI.setInt(12,(tab2.getValor(i,8)+"").equals("")?0:Integer.parseInt((tab2.getValor(i,8)+"")));
         
         psI.setString(13,Funcoes.adicionaEspacos(tab2.getValor(i,5)+"",3));
         psI.setString(14,tab2.getValor(i,4)+"");
         psI.setBigDecimal(15,Funcoes.strCurrencyToBigDecimal(tab2.getValor(i,11)+"")); // VLRCONTABIL
         psI.setBigDecimal(16,Funcoes.strCurrencyToBigDecimal(tab2.getValor(i,12)+"")); // VLRBASEICMS
         psI.setBigDecimal(17,Funcoes.strCurrencyToBigDecimal(tab2.getValor(i,9)+"")); // ALIQICMS
         psI.setBigDecimal(18,Funcoes.strCurrencyToBigDecimal(tab2.getValor(i,13)+"")); // VLRICMS
         psI.setBigDecimal(19,Funcoes.strCurrencyToBigDecimal(tab2.getValor(i,14)+"")); // VLRISENTAS ICMS
         psI.setBigDecimal(20,Funcoes.strCurrencyToBigDecimal(tab2.getValor(i,15)+"")); // VLROUTRAS ICMS
         psI.setBigDecimal(21,Funcoes.strCurrencyToBigDecimal(tab2.getValor(i,16)+"")); // VLRBASEIPI    
         psI.setBigDecimal(22,Funcoes.strCurrencyToBigDecimal(tab2.getValor(i,10)+"")); // ALIQIPI
         psI.setBigDecimal(23,Funcoes.strCurrencyToBigDecimal(tab2.getValor(i,17)+"")); // VLRIPI
         psI.setBigDecimal(24,Funcoes.strCurrencyToBigDecimal("0")); // VLRISENTAS IPI
         psI.setBigDecimal(25,Funcoes.strCurrencyToBigDecimal("0")); // VLRIOUTRAS IPI 
         psI.setInt(26,(tab2.getValor(i,6)+"").equals("")?0:Integer.parseInt((tab2.getValor(i,6)+""))); // MODELO DE NOTA FISCAL 
		if ( (!(tab2.getValor(i,18)+"").equals("")) || 
			 (!(tab2.getValor(i,18)+"").equals("0")) )
			psI.setInt(27,Integer.parseInt(tab2.getValor(i,18)+"")); // CODEMPET 
		else 
			psI.setNull(27,Types.INTEGER); 
		if ( (!(tab2.getValor(i,19)+"").equals("")) || 
			(!(tab2.getValor(i,19)+"").equals("0")) )
		   psI.setInt(28,Integer.parseInt(tab2.getValor(i,19)+"")); // CODFILIALET 
		else 
		   psI.setNull(28,Types.INTEGER); 
		if ( (!(tab2.getValor(i,20)+"").equals("")) || 
			(!(tab2.getValor(i,20)+"").equals("0")) )
		   psI.setInt(29,Integer.parseInt(tab2.getValor(i,20)+"")); // CODEMPNT 
		else 
			psI.setNull(29,Types.INTEGER); 
		if ( (!(tab2.getValor(i,21)+"").equals("")) || 
			(!(tab2.getValor(i,21)+"").equals("0")) )
		   psI.setInt(30,Integer.parseInt(tab2.getValor(i,21)+"")); // CODFILIALNT 
		else 
			psI.setNull(30,Types.INTEGER); 
		if ( (!(tab2.getValor(i,22)+"").equals("")) || 
			(!(tab2.getValor(i,22)+"").equals("0")) )
		   psI.setInt(31,Integer.parseInt(tab2.getValor(i,22)+"")); // CODEMPMN 
		else 
			psI.setNull(31,Types.INTEGER); 
		if ( (!(tab2.getValor(i,23)+"").equals("")) || 
			(!(tab2.getValor(i,23)+"").equals("0")) )
		   psI.setInt(32,Integer.parseInt(tab2.getValor(i,23)+"")); // CODFILIALMN 
		else 
		   psI.setNull(32,Types.INTEGER); 
		psI.executeUpdate();     
//         psI.close();
		if (!con.getAutoCommit())
			con.commit();
       }
       catch (SQLException err) {
		Funcoes.mensagemErro(this,"Erro gerando livros fiscais de compras!\n"+err.getMessage());
         break;
       }
       iAnd++;
//       System.out.println("And:"+iAnd+"\n");
    };
    tim.stop();
    pbAnd.setValue(iAnd);
    pbAnd.updateUI();
    lbAnd.setText("Pronto.");
    btGerar.setEnabled(false);
  }
  
  private boolean checar() {
    boolean bRetorno = false;
    int iTotErros = 0;
    DLChecaLFSaida dl = new DLChecaLFSaida();
    String sSql = "SELECT V1.CODVENDA,V1.SERIE,"+
         "V1.DOCVENDA,V1.DTEMITVENDA "+
         "FROM VDVENDA V1,EQTIPOMOV TM "+
         " WHERE V1.DTEMITVENDA BETWEEN ? AND ? AND V1.CODEMP=? AND V1.CODFILIAL=? AND "+
         " TM.CODTIPOMOV=V1.CODTIPOMOV AND TM.CODEMP=V1.CODEMPTM AND " +
         " TM.CODFILIAL=V1.CODFILIALTM AND " +
         " TM.FISCALTIPOMOV='S' AND "+
         " (SELECT COUNT(*) from VDVENDA V3 "+
         " WHERE V3.DOCVENDA=V1.DOCVENDA AND V3.CODEMP=V1.CODEMP AND " +
         " V3.CODFILIAL=V1.CODFILIAL AND "+
         " V3.CODTIPOMOV=V1.CODTIPOMOV)>1 "+
         " ORDER BY V1.CODVENDA,V1.SERIE,V1.DOCVENDA";
    try {

      PreparedStatement psChec = con.prepareStatement(sSql);
      psChec.setDate(1,Funcoes.dateToSQLDate(txtDataini.getVlrDate()));
      psChec.setDate(2,Funcoes.dateToSQLDate(txtDatafim.getVlrDate()));
      psChec.setInt(3,Aplicativo.iCodEmp);
      psChec.setInt(4,ListaCampos.getMasterFilial("VDVENDA"));
      ResultSet rsChec = psChec.executeQuery();
      iTotErros = 0;
      dl.tab.limpa();
      while (rsChec.next()) {
         dl.tab.adicLinha();
         dl.tab.setValor(""+rsChec.getInt(1),iTotErros,0);
         dl.tab.setValor(rsChec.getString(2),iTotErros,1);
         dl.tab.setValor(""+rsChec.getInt(3),iTotErros,2);
         dl.tab.setValor(Funcoes.sqlDateToStrDate(rsChec.getDate(4)),iTotErros,3);
         dl.tab.setValor("Numeração de NF. repetida",iTotErros,4);
         iTotErros++;
      }
    
      if (iTotErros>0) {
         btGerar.setEnabled(false);
         dl.setVisible(true);
      }
      else {
         bRetorno = true;
         btGerar.setEnabled(true);
      }

//      rsChec.close();
//      psChec.close();
      if (!con.getAutoCommit())
      	con.commit();
      
    }
    catch (SQLException err) {
		Funcoes.mensagemErro(this,"Erro ao realizar consulta!\n"+err.getMessage());
      bRetorno = false;
    }
    
    return bRetorno ;
  }
  
  private boolean valida() {
    if (txtDatafim.getVlrDate().before(txtDataini.getVlrDate())) {
		Funcoes.mensagemInforma(this,"Data final maior que a data inicial!");
      return false;
    }
    else if ((cbEntrada.getVlrString() != "S") & (cbSaida.getVlrString() != "S")) {
		Funcoes.mensagemInforma(this,"Nenhuma operção foi selecionada!");
      return false;
    }
    return true;
  }
  public void actionPerformed(ActionEvent evt) {
    if (evt.getSource() == tim) {
//      System.out.println("Atualizando\n");
      pbAnd.setValue(iAnd+1);
      pbAnd.updateUI();
    }
    else if (evt.getSource() == btGerar) {
      iniGerar();
    }
    else if (evt.getSource() == btChecar) {
      checar();            
    }
    else if (evt.getSource() == btVisual) {
      visualizar();
    }
  }
}
