/**
 * @version 02/11/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FSintegra.java <BR>
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
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;

import org.freedom.bmps.Icone;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Painel;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FFilho;

public class FSintegra extends FFilho implements ActionListener {
  private FileWriter fwSintegra;
  private Frame fOrigem;
  private String sFileName = "";
  private String sCnpjEmp = "";
  private String sInscEmp = "";
  private Painel pinCliente = new Painel(700,490);
  private JTextFieldPad txtDataini = new JTextFieldPad();
  private JTextFieldPad txtDatafim = new JTextFieldPad();
  private JCheckBoxPad cbEntrada = new JCheckBoxPad("Entrada","S","N");
  private JCheckBoxPad cbSaida = new JCheckBoxPad("Saida","S","N");
  private JCheckBoxPad cbConsumidor = new JCheckBoxPad("NF Consumidor","S","N");
  private JButton btGerar = new JButton(Icone.novo("btGerar.gif"));
  private String CR = ""+((char) 13)+""+((char) 10);
  private JRadioGroup rgConvenio;
  private JRadioGroup rgNatoper;
  private JRadioGroup rgFinalidade;
  private Vector vLabConvenio = new Vector();
  private Vector vValConvenio = new Vector();
  private Vector vLabNatoper = new Vector();
  private Vector vValNatoper = new Vector();
  private Vector vLabFinalidade = new Vector();
  private Vector vValFinalidade = new Vector();
  
  
  private JLabel lbAnd = new JLabel("Aguardando");
  public FSintegra() {
    setTitulo("Gera Arquivo Sintegra");
    setAtribos(50,20,710,410);
    
    btGerar.setToolTipText("Exporta arquivo");
    
    Container c = getContentPane();
    c.setLayout(new BorderLayout());
    
    adicBotaoSair();
    
    c.add(pinCliente,BorderLayout.CENTER);
    
    txtDataini.setTipo(JTextFieldPad.TP_DATE,0,10);
    txtDatafim.setTipo(JTextFieldPad.TP_DATE,0,10);
    txtDataini.setRequerido(true);
    txtDatafim.setRequerido(true);
    
    vLabConvenio.addElement("Convênio 57/95");
    vLabConvenio.addElement("Convênio 69/02");
	vValConvenio.addElement("1");
    vValConvenio.addElement("2");
    
    rgConvenio = new JRadioGroup(1,2,vLabConvenio,vValConvenio);
    rgConvenio.setVlrString("2");
    
    vLabNatoper.addElement("Interestaduais - Somente operações sujeitas ao regime de substituição tributária");
    vLabNatoper.addElement("Interestaduais - Operações com ou sem substituição tributária");
    vLabNatoper.addElement("Totalidade das operações do informante");
    vValNatoper.addElement("1");
    vValNatoper.addElement("2");
    vValNatoper.addElement("3");
    rgNatoper = new JRadioGroup(3,3,vLabNatoper,vValNatoper);
    
    vLabFinalidade.addElement("Normal");
    vLabFinalidade.addElement("Retificação total de arquivo");
    vLabFinalidade.addElement("Retificação aditíva de arquivo");
    vLabFinalidade.addElement("Retificação corretiva de arquivo");
    vLabFinalidade.addElement("Desfaziamento");
    vValFinalidade.addElement("1");
    vValFinalidade.addElement("2");
    vValFinalidade.addElement("3");
    vValFinalidade.addElement("4");
    vValFinalidade.addElement("5");
    rgFinalidade = new JRadioGroup(5,5,vLabFinalidade,vValFinalidade);
    
    
    pinCliente.adic(new JLabel("Inicio"),7,0,110,25);
    pinCliente.adic(txtDataini,7,20,110,20);
    pinCliente.adic(new JLabel("Fim"),120,0,107,25);
    pinCliente.adic(txtDatafim,120,20,107,20);
    pinCliente.adic(btGerar,296,15,30,30);
    pinCliente.adic(cbEntrada,7,50,150,20);
    pinCliente.adic(cbSaida,170,50,150,20);
    pinCliente.adic(cbConsumidor,333,50,150,20);
    pinCliente.adic(rgConvenio,7,80,680,30);
    pinCliente.adic(rgNatoper,7,120,680,80);
    pinCliente.adic(rgFinalidade,7,210,680,100);
    
    pinCliente.adic(lbAnd,7,320,680,20);
    colocaMes();   
    btGerar.addActionListener(this);
  }
  
  public void setOrigem(Frame f) {
    fOrigem = f;
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
		Funcoes.mensagemInforma(this,"Não foi possível criar processo!\n"+err.getMessage());
    }
  }
  
  private void gerar() {
	String sBuffer = "";
	String sReg10 = "";
	String sReg11 = "";
	String sDocTmp = "";
	String sConvenio = rgConvenio.getVlrString();
	String sNatoper = rgNatoper.getVlrString();
	String sFinalidade = rgFinalidade.getVlrString();
	String sTabela = "";
	String sCodEmp = "";
	int iOrdem = 0;
	int iTot50 = 0;
	int iTot54 = 0;
	int iTot61 = 0;
	int iTot75 = 0;
	int iTotreg = 0;
	int iCodEmp = 0;
	String sUsaRefProd = "N";
    
	iCodEmp = Aplicativo.iCodEmp;
	if (sCodEmp!=null) {
	   if (!sCodEmp.trim().equals("")) {
		  iCodEmp = Integer.parseInt(sCodEmp.trim());
	   }
	}
    
	String sSql = "";
    
	if (!valida()) {
	   return;
	}
	if (sFileName.trim().equals("")) {
		Funcoes.mensagemInforma(this,"Selecione o arquivo!");
	   return;
	}

	File fSintegra = null;

	fSintegra = new File(sFileName);
    
	if (fSintegra.exists()) {
	   if (Funcoes.mensagemConfirma(this,"Arquivo: '"+sFileName+"' já existe! Deseja sobrescrever?")!=0) {
		  return;
	   }
	}
    
	try {
	   fSintegra.createNewFile();     
	}
	catch(IOException ioError) {
		Funcoes.mensagemErro(this,"Erro limpando arquivo: "+sFileName+"\n"+ioError.getMessage());
	   return;
	}
    
	try {
	  fwSintegra = new FileWriter(fSintegra);
	}
	catch(IOException ioError) {
		Funcoes.mensagemErro(this,"Erro Criando o arquivo: "+sFileName+"\n"+ioError.getMessage());
	   return;
	}


	btGerar.setEnabled(false);

	// REGISTRO TIPO 1 HEADER DO ARQUIVO 
//	  System.out.println("Antes da SQL EMPRESA");
    
	PreparedStatement ps;
	ResultSet rs;
    
	try {
		
	   sSql = "SELECT USAREFPROD FROM SGPREFERE1 WHERE CODEMP=? AND CODFILIAL=?";
	   ps = con.prepareStatement(sSql);
	   ps.setInt(1,iCodEmp);
	   ps.setInt(2,ListaCampos.getMasterFilial("SGPREFERE1"));
	   rs = ps.executeQuery();
	   if (rs.next()) {
	   	  sUsaRefProd = rs.getString("USAREFPROD"); 
	   	  if (sUsaRefProd==null) 
	   	     sUsaRefProd = "N"; 
	   }	
	   if (!con.getAutoCommit())
	   	con.commit();

   	   sSql = "SELECT F.CODEMP,F.CNPJFILIAL,F.INSCFILIAL,F.RAZFILIAL,F.CIDFILIAL,F.UFFILIAL,F.FAXFILIAL,F.BAIRFILIAL,"+
			   "F.ENDFILIAL,F.NUMFILIAL,E.NOMECONTEMP,F.FONEFILIAL,F.CEPFILIAL FROM SGFILIAL F,SGEMPRESA E WHERE " +
			   "E.CODEMP=F.CODEMP AND F.CODEMP=? AND F.CODFILIAL=?";
            
	   ps = con.prepareStatement(sSql);
	   ps.setInt(1,iCodEmp);
	   ps.setInt(2,Aplicativo.iCodFilial);
	   rs = ps.executeQuery();
	   if (rs.next()) {
		 sCnpjEmp = Funcoes.adicionaEspacos(rs.getString("CNPJFILIAL"),14);
		 sInscEmp = Funcoes.adicionaEspacos(Funcoes.limpaString(rs.getString("INSCFILIAL")),14);
         
		 sReg10 = "10"+Funcoes.adicionaEspacos(rs.getString("CNPJFILIAL"),14)+
			Funcoes.adicionaEspacos(Funcoes.limpaString(rs.getString("INSCFILIAL")),14)+
			Funcoes.adicionaEspacos(rs.getString("RAZFILIAL"),35)+
			Funcoes.adicionaEspacos(rs.getString("CIDFILIAL"),30)+
			Funcoes.adicionaEspacos(rs.getString("UFFILIAL"),2)+
			Funcoes.strZero(rs.getString("FAXFILIAL"),10)+
			Funcoes.dataAAAAMMDD(txtDataini.getVlrDate())+
			Funcoes.dataAAAAMMDD(txtDatafim.getVlrDate())+
			sConvenio+sNatoper+sFinalidade+CR;
		 sReg11 = "11"+Funcoes.adicionaEspacos(rs.getString("ENDFILIAL"),34)+
			Funcoes.strZero(rs.getInt("NUMFILIAL")+"",5)+
			Funcoes.replicate(" ",22)+
			Funcoes.adicionaEspacos(rs.getString("BAIRFILIAL"),15)+
			Funcoes.strZero(rs.getString("CEPFILIAL"),8)+
			Funcoes.adicionaEspacos(rs.getString("NOMECONTEMP"),28)+
			Funcoes.strZero(Funcoes.limpaString(rs.getString("FONEFILIAL")),12)+CR;
		 sBuffer = sReg10+sReg11;
		 gravaBuffer(sBuffer);
	   }
//		 rs.close();
//		 ps.close();
	   if (!con.getAutoCommit())
	   	  con.commit();
	   if (cbEntrada.getVlrString().equals("S")) {
		  // REGISTRO 50 LIVROS FISCAIS DE ENTRADA
		  sSql = "SELECT LF.TIPOLF,LF.ANOMESLF,LF.ESPECIELF,LF.DOCINILF,"+
			"LF.DOCFIMLF,LF.SERIELF,LF.CODEMITLF,LF.UFLF,LF.DTEMITLF,"+
			"LF.DTESLF,LF.VLRCONTABILLF,LF.VLRBASEICMSLF,LF.ALIQICMSLF,"+
			"LF.VLRICMSLF,LF.VLRISENTASICMSLF,LF.VLROUTRASICMSLF,"+
			"LF.CODNAT,LF.CODMODNOTA,F.CNPJFOR,F.INSCFOR "+
			"FROM LFLIVROFISCAL LF,CPFORNECED F "+
			"WHERE LF.DTESLF BETWEEN ? AND ? AND "+
			"LF.CODEMP=? AND LF.CODFILIAL=? AND "+
            "F.CODFOR=LF.CODEMITLF AND F.CODEMP=LF.CODEMPET AND "+
            "F.CODFILIAL=LF.CODFILIALET AND LF.TIPOLF='E' "+
			"ORDER BY LF.DTESLF";
		  ps = con.prepareStatement(sSql);
		  ps.setDate(1,Funcoes.dateToSQLDate(txtDataini.getVlrDate()));
		  ps.setDate(2,Funcoes.dateToSQLDate(txtDatafim.getVlrDate()));
		  ps.setInt(3,iCodEmp);
		  ps.setInt(4,ListaCampos.getMasterFilial("LFLIVROFISCAL"));
		  rs = ps.executeQuery();
		  lbAnd.setText("Gerando Entrada...");
		  while (rs.next()) {
			 sBuffer = "50"+Funcoes.adicionaEspacos(rs.getString("CNPJFOR"),14)+
			   Funcoes.adicionaEspacos(Funcoes.limpaString(rs.getString("INSCFOR")),14)+
			   Funcoes.dataAAAAMMDD(Funcoes.sqlDateToDate(rs.getDate("DTESLF")))+
			   Funcoes.adicionaEspacos(rs.getString("UFLF"),2)+
			   Funcoes.strZero(rs.getInt("CODMODNOTA")+"",2)+
			   Funcoes.adicionaEspacos(rs.getString("SERIELF"),3)+
			   (sConvenio.equals("1")?Funcoes.replicate(" ",2):"")+ // Sub serie 
			   Funcoes.strZero(rs.getInt("DOCINILF")+"",6)+
			   Funcoes.adicionaEspacos(rs.getString("CODNAT"),(sConvenio.equals("1")?3:4))+
			   (sConvenio.equals("1")?"":"T")+ // Emitente da nota fiscal P-Própio ou T-Terceiros
			   Funcoes.transValor(rs.getString("VLRCONTABILLF"),13,2,true)+
			   Funcoes.transValor(rs.getString("VLRBASEICMSLF"),13,2,true)+
			   Funcoes.transValor(rs.getString("VLRICMSLF"),13,2,true)+
			   Funcoes.transValor(rs.getString("VLRISENTASICMSLF"),13,2,true)+
			   Funcoes.transValor(rs.getString("VLROUTRASICMSLF"),13,2,true)+
			   Funcoes.transValor(rs.getString("ALIQICMSLF"),4,2,true)+
			   "N" + CR;
			 gravaBuffer(sBuffer);
			 iTot50 ++;
		  }
//			rs.close();
//		  ps.close();
		  if (!con.getAutoCommit())
		  	con.commit();
		}

		if (cbSaida.getVlrString().equals("S")) {
		  // REGISTRO 50 LIVROS FISCAIS DE SAIDA
		  sSql = "SELECT LF.TIPOLF,LF.ANOMESLF,LF.ESPECIELF,LF.DOCINILF,"+
			"LF.DOCFIMLF,LF.SERIELF,LF.CODEMITLF,LF.UFLF,LF.DTEMITLF,"+
			"LF.DTESLF,LF.VLRCONTABILLF,LF.VLRBASEICMSLF,LF.ALIQICMSLF,"+
			"LF.VLRICMSLF,LF.VLRISENTASICMSLF,LF.VLROUTRASICMSLF,"+
			"LF.CODNAT,LF.CODMODNOTA,C.CNPJCLI,C.INSCCLI "+
			"FROM LFLIVROFISCAL LF,VDCLIENTE C "+
			"WHERE LF.DTEMITLF BETWEEN ? AND ? AND "+
			"LF.CODEMP=? AND LF.CODFILIAL=? AND "+
			"C.CODCLI=LF.CODEMITLF AND C.CODEMP=LF.CODEMPET AND "+
            "C.CODFILIAL=LF.CODFILIALET AND "+
			"LF.TIPOLF='S' AND C.PESSOACLI='J' "+
			"ORDER BY LF.DTEMITLF";
		  ps = con.prepareStatement(sSql);
		  ps.setDate(1,Funcoes.dateToSQLDate(txtDataini.getVlrDate()));
		  ps.setDate(2,Funcoes.dateToSQLDate(txtDatafim.getVlrDate()));
		  ps.setInt(3,iCodEmp);
		  ps.setInt(4,ListaCampos.getMasterFilial("LFLIVROFISCAL"));
		  rs = ps.executeQuery();
		  lbAnd.setText("Gerando Saídas...");
		  while (rs.next()) {
			 sBuffer = "50"+Funcoes.adicionaEspacos(rs.getString("CNPJCLI"),14)+
			   Funcoes.adicionaEspacos(Funcoes.limpaString(rs.getString("INSCCLI")),14)+
			   Funcoes.dataAAAAMMDD(Funcoes.sqlDateToDate(rs.getDate("DTEMITLF")))+
			   Funcoes.adicionaEspacos(rs.getString("UFLF"),2)+
			   Funcoes.strZero(rs.getInt("CODMODNOTA")+"",2)+
			   Funcoes.adicionaEspacos(rs.getString("SERIELF"),3)+
			   (sConvenio.equals("1")?Funcoes.replicate(" ",2):"")+ // Sub serie 
			   Funcoes.strZero(rs.getInt("DOCINILF")+"",6)+
			   Funcoes.adicionaEspacos(rs.getString("CODNAT"),(sConvenio.equals("1")?3:4))+
			   (sConvenio.equals("1")?"":"P") + //Emitente da nota fiscal P-Própio ou T-Terceiros
			   Funcoes.transValor(rs.getString("VLRCONTABILLF"),13,2,true)+
			   Funcoes.transValor(rs.getString("VLRBASEICMSLF"),13,2,true)+
			   Funcoes.transValor(rs.getString("VLRICMSLF"),13,2,true)+
			   Funcoes.transValor(rs.getString("VLRISENTASICMSLF"),13,2,true)+
			   Funcoes.transValor(rs.getString("VLROUTRASICMSLF"),13,2,true)+
			   Funcoes.transValor(rs.getString("ALIQICMSLF"),4,2,true)+
			   "N" + CR;
			 gravaBuffer(sBuffer);
			 iTot50 ++;
		  }
//		  rs.close();
//			ps.close();
		  if (!con.getAutoCommit())
		  	con.commit();
		}



		if (cbEntrada.getVlrString().equals("S")) {
		  // REGISTRO 54 INFORMAÇÕES DOS ITENS DAS NOTAS FISCAIS DE ENTRADA
		  sSql = "SELECT C.DTENTCOMPRA,C.DOCCOMPRA,IC.CODITCOMPRA,C.CODFOR,"+
			 "F.UFFOR,F.CNPJFOR,IC.CODNAT,IC.CODPROD,P.REFPROD,"+
			 "C.DTEMITCOMPRA,C.SERIE,TM.CODMODNOTA,IC.PERCICMSITCOMPRA,"+
			 "IC.QTDITCOMPRA,IC.VLRLIQITCOMPRA,IC.VLRBASEICMSITCOMPRA,"+
			 "IC.PERCICMSITCOMPRA,IC.VLRBASEICMSITCOMPRA,IC.VLRIPIITCOMPRA,"+
             "CF.ORIGFISC, CF.CODTRATTRIB "+
			 "FROM CPCOMPRA C,CPFORNECED F,CPITCOMPRA IC,EQTIPOMOV TM,EQPRODUTO P, LFCLFISCAL CF "+
			 "WHERE C.DTENTCOMPRA BETWEEN ? AND ? AND C.CODEMP=? AND C.CODFILIAL=? AND "+
			 "IC.CODCOMPRA=C.CODCOMPRA AND  IC.CODEMP=C.CODEMP AND IC.CODFILIAL=C.CODFILIAL AND "+
			 "TM.CODTIPOMOV=C.CODTIPOMOV AND TM.CODEMP=C.CODEMPTM AND TM.CODFILIAL=C.CODFILIALTM AND "+
			 "F.CODFOR=C.CODFOR AND F.CODEMP=C.CODEMPFR AND F.CODEMP=C.CODFILIALFR AND "+
			 "P.CODPROD=IC.CODPROD AND P.CODEMP=IC.CODEMPPD AND P.CODFILIAL=IC.CODFILIALPD AND "+
			 "CF.CODFISC=P.CODFISC AND CF.CODEMP=P.CODEMPFC AND CF.CODFILIAL=P.CODFILIALFC AND "+
             "TM.FISCALTIPOMOV='S' ORDER BY C.DTENTCOMPRA,C.DOCCOMPRA,IC.CODITCOMPRA";
             
          
		  ps = con.prepareStatement(sSql);
		  ps.setDate(1,Funcoes.dateToSQLDate(txtDataini.getVlrDate()));
		  ps.setDate(2,Funcoes.dateToSQLDate(txtDatafim.getVlrDate()));
		  ps.setInt(3,iCodEmp);
		  ps.setInt(4,ListaCampos.getMasterFilial("CPCOMPRA"));
		  rs = ps.executeQuery();
		  lbAnd.setText("Gerando Itens NF Entrada...");
		  sDocTmp = "";
		  while (rs.next()) {
			 if (!sDocTmp.equals(""+rs.getInt("DOCCOMPRA"))) {
				iOrdem = 1;
			 }
			 sBuffer = "54"+Funcoes.adicionaEspacos(rs.getString("CNPJFOR"),14)+
			   Funcoes.strZero((rs.getString("CODMODNOTA")==null?0:rs.getInt("CODMODNOTA"))+"",2)+
			   Funcoes.adicionaEspacos(rs.getString("SERIE"),3)+
			   (sConvenio.equals("1")?Funcoes.replicate(" ",2):"")+ // Sub serie 
			   Funcoes.strZero(rs.getInt("DOCCOMPRA")+"",6)+
			   Funcoes.adicionaEspacos(rs.getString("CODNAT"),(sConvenio.equals("1")?3:4))+
			   (sConvenio.equals("1")?"":rs.getString("ORIGFISC")+rs.getString("CODTRATTRIB"))+
			   Funcoes.strZero(iOrdem+"",3)+
			   Funcoes.adicionaEspacos(rs.getString((sUsaRefProd.equals("S")?"REFPROD":"CODPROD")),14)+
			   Funcoes.transValor(rs.getString("QTDITCOMPRA"),(sConvenio.equals("1")?13:11),3,true)+
			   Funcoes.transValor(rs.getString("VLRLIQITCOMPRA"),12,2,true)+
			   Funcoes.transValor("0",12,2,true)+
			   Funcoes.transValor(rs.getString("VLRBASEICMSITCOMPRA"),12,2,true)+
			   Funcoes.transValor("0",12,2,true)+
			   Funcoes.transValor(rs.getString("VLRIPIITCOMPRA"),12,2,true)+
			   Funcoes.transValor(rs.getString("PERCICMSITCOMPRA"),4,2,true)+CR;
			 gravaBuffer(sBuffer);
			 sDocTmp = rs.getInt("DOCCOMPRA")+"";
			 iOrdem++;
			 iTot54 ++;
		  }
//			rs.close();
//			ps.close();
		  if (!con.getAutoCommit())
		  	con.commit();
          
	  }
      
		if (cbSaida.getVlrString().equals("S")) {
		  // REGISTRO 54 INFORMAÇÕES DOS ITENS DAS NOTAS FISCAIS DE SAÍDA
		  sSql = "SELECT V.DTSAIDAVENDA,V.DOCVENDA,IV.CODITVENDA,V.CODCLI,"+
			 "C.UFCLI,C.CNPJCLI,IV.CODNAT,IV.CODPROD,P.REFPROD,"+
			 "V.DTEMITVENDA,V.SERIE,TM.CODMODNOTA,IV.PERCICMSITVENDA,"+
			 "IV.QTDITVENDA,IV.VLRLIQITVENDA,IV.VLRBASEICMSITVENDA,"+
			 "IV.PERCICMSITVENDA,IV.VLRBASEICMSITVENDA,IV.VLRIPIITVENDA,"+
			 "CF.ORIGFISC,CF.CODTRATTRIB "+
			 "FROM VDVENDA V,VDCLIENTE C,VDITVENDA IV,EQTIPOMOV TM,EQPRODUTO P,LFCLFISCAL CF "+
			 "WHERE V.DTEMITVENDA BETWEEN ? AND ? AND V.CODEMP=? AND V.CODFILIAL=? AND "+
			 "IV.CODVENDA=V.CODVENDA AND TM.CODTIPOMOV=V.CODTIPOMOV AND "+
			 "C.CODCLI=V.CODCLI AND C.PESSOACLI='J' AND "+
			 "P.CODPROD=IV.CODPROD AND TM.FISCALTIPOMOV='S' AND "+
			 "CF.CODFISC=P.CODFISC AND CF.CODEMP=P.CODEMPFC AND CF.CODFILIAL=P.CODFILIALFC "+
			 "ORDER BY V.DTEMITVENDA,V.DOCVENDA,IV.CODITVENDA";
             
          
		  ps = con.prepareStatement(sSql);
		  ps.setDate(1,Funcoes.dateToSQLDate(txtDataini.getVlrDate()));
		  ps.setDate(2,Funcoes.dateToSQLDate(txtDatafim.getVlrDate()));
		  ps.setInt(3,iCodEmp);
		  ps.setInt(4,ListaCampos.getMasterFilial("VDVENDA"));
		  rs = ps.executeQuery();
		  lbAnd.setText("Gerando Itens NF Saída...");
		  sDocTmp = "";
		  while (rs.next()) {
			 if (!sDocTmp.equals(""+rs.getInt("DOCVENDA"))) {
				iOrdem = 1;
			 }
			 sBuffer = "54"+Funcoes.adicionaEspacos(rs.getString("CNPJCLI"),14)+
			   Funcoes.strZero((rs.getString("CODMODNOTA")==null?0:rs.getInt("CODMODNOTA"))+"",2)+
			   Funcoes.adicionaEspacos(rs.getString("SERIE"),3)+
			   (sConvenio.equals("1")?Funcoes.replicate(" ",2):"")+ // Sub serie 
			   Funcoes.strZero(rs.getInt("DOCVENDA")+"",6)+
			   Funcoes.adicionaEspacos(rs.getString("CODNAT"),(sConvenio.equals("1")?3:4))+
			   (sConvenio.equals("1")?"":rs.getString("ORIGFISC")+rs.getString("CODTRATTRIB"))+
			   Funcoes.strZero(iOrdem+"",3)+
			   Funcoes.adicionaEspacos(rs.getString((sUsaRefProd.equals("S")?"REFPROD":"CODPROD")),14)+
			   Funcoes.transValor(rs.getString("QTDITVENDA"),(sConvenio.equals("1")?13:11),3,true)+
			   Funcoes.transValor(rs.getString("VLRLIQITVENDA"),12,2,true)+
			   Funcoes.transValor("0",12,2,true)+
			   Funcoes.transValor(rs.getString("VLRBASEICMSITVENDA"),12,2,true)+
			   Funcoes.transValor("0",12,2,true)+
			   Funcoes.transValor(rs.getString("VLRIPIITVENDA"),12,2,true)+
			   Funcoes.transValor(rs.getString("PERCICMSITVENDA"),4,2,true)+CR;
			 gravaBuffer(sBuffer);
			 iOrdem++;
			 iTot54 ++;
			 sDocTmp = rs.getInt("DOCVENDA")+"";
		  }
//			rs.close();
//			ps.close();
		  if (!con.getAutoCommit())
		  	con.commit();
          
	  }

      
	  if ( (cbConsumidor.getVlrString().equals("S")) && (sConvenio.equals("1"))) {
		  lbAnd.setText("Gerando NF Saída (Consumidor)...");
		  // REGISTRO 61 LIVROS FISCAIS DE SAIDA
		  sSql = "SELECT LF.TIPOLF,LF.ANOMESLF,LF.ESPECIELF,LF.DOCINILF,"+
			"LF.DOCFIMLF,LF.SERIELF,LF.CODEMITLF,LF.UFLF,LF.DTEMITLF,"+
			"LF.DTESLF,LF.VLRCONTABILLF,LF.VLRBASEICMSLF,LF.ALIQICMSLF,"+
			"LF.VLRICMSLF,LF.VLRISENTASICMSLF,LF.VLROUTRASICMSLF,"+
			"LF.CODNAT,LF.CODMODNOTA,C.CNPJCLI,C.INSCCLI "+
			"FROM LFLIVROFISCAL LF,VDCLIENTE C "+
			"WHERE LF.DTEMITLF BETWEEN ? AND ? AND LF.CODEMP=? AND LF.CODFILIAL=? "+
			"C.CODCLI=LF.CODEMITLF AND C.CODEMP=LF.CODEMPET AND C.CODFILIAL=LF.CODFILIALET AND " +
			"LF.TIPOLF='S' AND C.PESSOACLI='F' "+
			"ORDER BY LF.DTEMITLF";
		  ps = con.prepareStatement(sSql);
		  ps.setDate(1,Funcoes.dateToSQLDate(txtDataini.getVlrDate()));
		  ps.setDate(2,Funcoes.dateToSQLDate(txtDatafim.getVlrDate()));
		  ps.setInt(3,iCodEmp);
		  ps.setInt(4,ListaCampos.getMasterFilial("LFLIVROFISCAL"));
		  rs = ps.executeQuery();
		  while (rs.next()) {
			 sBuffer = "61"+Funcoes.replicate(" ",14)+
			   Funcoes.replicate(" ",14)+
			   Funcoes.dataAAAAMMDD(Funcoes.sqlDateToDate(rs.getDate("DTEMITLF")))+
			   Funcoes.strZero(rs.getInt("CODMODNOTA")+"",2)+
			   Funcoes.adicionaEspacos(rs.getString("SERIELF"),3)+
			   Funcoes.replicate(" ",2)+ // Sub serie 
			   Funcoes.strZero(rs.getInt("DOCINILF")+"",6)+
			   Funcoes.strZero(rs.getInt("DOCFIMLF")+"",6)+
			   Funcoes.transValor(rs.getString("VLRCONTABILLF"),13,2,true)+
			   Funcoes.transValor(rs.getString("VLRBASEICMSLF"),13,2,true)+
			   Funcoes.transValor(rs.getString("VLRICMSLF"),12,2,true)+
			   Funcoes.transValor(rs.getString("VLRISENTASICMSLF"),13,2,true)+
			   Funcoes.transValor(rs.getString("VLROUTRASICMSLF"),13,2,true)+
			   Funcoes.transValor(rs.getString("ALIQICMSLF"),4,2,true)+
			   " "+ CR;
			 gravaBuffer(sBuffer);
			 iTot61 ++;
		  }
//			rs.close();
//			ps.close();
		  if (!con.getAutoCommit())
		  	con.commit();

	  }


	  if ( cbEntrada.getVlrString().equals("S")) {
		  // REGISTRO 75 TABELA DE PRODUTOS ENTRADAS
          
         
		  sSql = "SELECT IC.CODPROD,P.REFPROD,P.DESCPROD,IC.PERCIPIITCOMPRA,"+
			 "IC.PERCICMSITCOMPRA,P.CODUNID,CF.ORIGFISC,CF.CODTRATTRIB,COUNT(*) "+
			 "FROM CPCOMPRA C,CPITCOMPRA IC,EQTIPOMOV TM,EQPRODUTO P,LFCLFISCAL CF "+
			 "WHERE C.DTENTCOMPRA BETWEEN ? AND ? AND C.CODEMP=? AND C.CODFILIAL=? AND "+
			 "IC.CODCOMPRA=C.CODCOMPRA AND IC.CODEMP=C.CODEMP AND IC.CODFILIAL=C.CODFILIAL AND "+
			 "TM.CODTIPOMOV=C.CODTIPOMOV AND TM.CODEMP=C.CODEMPTM AND TM.CODFILIAL=C.CODFILIALTM AND "+
			 "P.CODPROD=IC.CODPROD AND P.CODEMP=IC.CODEMPPD AND P.CODFILIAL=IC.CODFILIALPD AND "+
			 "CF.CODFISC=P.CODFISC AND CF.CODEMP=P.CODEMPFC AND CF.CODFILIAL=P.CODFILIALFC AND "+
			 "TM.FISCALTIPOMOV='S' "+
			 "GROUP BY IC.CODPROD,P.REFPROD,P.DESCPROD,IC.PERCIPIITCOMPRA,"+
			 "IC.PERCICMSITCOMPRA,P.CODUNID,CF.ORIGFISC,CF.CODTRATTRIB ";
          
		  ps = con.prepareStatement(sSql);
		  ps.setDate(1,Funcoes.dateToSQLDate(txtDataini.getVlrDate()));
		  ps.setDate(2,Funcoes.dateToSQLDate(txtDatafim.getVlrDate()));
		  ps.setInt(3,iCodEmp);
		  ps.setInt(4,ListaCampos.getMasterFilial("CPCOMPRA"));
		  rs = ps.executeQuery();
		  lbAnd.setText("Gerando Tabela de Produtos de Compra...");
		  while (rs.next()) {
	         btGerar.setEnabled(false);
			 sBuffer = "75"+Funcoes.dataAAAAMMDD(txtDataini.getVlrDate())+
			   Funcoes.dataAAAAMMDD(txtDatafim.getVlrDate())+
			   Funcoes.adicionaEspacos(rs.getString((sUsaRefProd.equals("S")?"REFPROD":"CODPROD")),14)+
			   Funcoes.replicate(" ",8)+
			   Funcoes.adicionaEspacos(rs.getString("DESCPROD"),53)+
			   Funcoes.adicionaEspacos(rs.getString("CODUNID"),6)+
			   rs.getString("ORIGFISC")+rs.getString("CODTRATTRIB")+
			   Funcoes.transValor(rs.getString("PERCIPIITCOMPRA"),4,2,true)+
			   Funcoes.transValor(rs.getString("PERCICMSITCOMPRA"),4,2,true)+
			   Funcoes.strZero("0",4)+
			   Funcoes.transValor("0",12,2,true)+CR;
			 gravaBuffer(sBuffer);
			 iTot75 ++;
		  }
//			rs.close();
//			ps.close();
		  if (!con.getAutoCommit())
		  	con.commit();

	  } 
      

	  if ( cbSaida.getVlrString().equals("S")) {
		  // REGISTRO 75 TABELA DE PRODUTOS SAIDAS
          
         
		  sSql = "SELECT IV.CODPROD,P.REFPROD,P.DESCPROD,IV.PERCIPIITVENDA,"+
			 "IV.PERCICMSITVENDA,P.CODUNID,CF.ORIGFISC,CF.CODTRATTRIB,COUNT(*) "+
			 "FROM VDVENDA V,VDITVENDA IV,EQTIPOMOV TM,EQPRODUTO P,VDCLIENTE C,LFCLFISCAL CF "+
			 "WHERE V.DTEMITVENDA BETWEEN ? AND ? AND V.CODEMP=? AND V.CODFILIAL=? AND "+
			 "C.CODCLI=V.CODCLI AND C.CODEMP=V.CODEMPCL AND C.CODFILIAL=V.CODFILIALCL AND " +
			 "C.PESSOACLI='J' AND "+
			 "IV.CODVENDA=V.CODVENDA AND IV.TIPOVENDA=V.TIPOVENDA AND IV.CODEMP=V.CODEMP AND " +
			 "IV.CODFILIAL=V.CODFILIAL AND "+
			 "TM.CODTIPOMOV=V.CODTIPOMOV AND TM.CODEMP=V.CODEMPTM AND " +
			 "TM.CODFILIAL=V.CODFILIALTM AND TM.FISCALTIPOMOV='S' AND "+
			 "P.CODPROD=IV.CODPROD AND P.CODEMP=IV.CODEMPPD AND P.CODFILIAL=IV.CODFILIALPD AND " +
			 "CF.CODFISC=P.CODFISC AND CF.CODEMP=P.CODEMPFC AND CF.CODFILIAL=P.CODFILIALFC "+
			 "GROUP BY IV.CODPROD,P.REFPROD,P.DESCPROD,IV.PERCIPIITVENDA,"+
			 "IV.PERCICMSITVENDA,P.CODUNID,CF.ORIGFISC,CF.CODTRATTRIB ";
          
		  ps = con.prepareStatement(sSql);
		  ps.setDate(1,Funcoes.dateToSQLDate(txtDataini.getVlrDate()));
		  ps.setDate(2,Funcoes.dateToSQLDate(txtDatafim.getVlrDate()));
		  ps.setInt(3,iCodEmp);
		  ps.setInt(4,ListaCampos.getMasterFilial("VDVENDA"));
		  rs = ps.executeQuery();
		  lbAnd.setText("Gerando Tabela de Produtos de Venda...");
		  while (rs.next()) {
			 sBuffer = "75"+Funcoes.dataAAAAMMDD(txtDataini.getVlrDate())+
			   Funcoes.dataAAAAMMDD(txtDatafim.getVlrDate())+
			   Funcoes.adicionaEspacos(rs.getString((sUsaRefProd.equals("S")?"REFPROD":"CODPROD")),14)+
			   Funcoes.replicate(" ",8)+
			   Funcoes.adicionaEspacos(rs.getString("DESCPROD"),53)+
			   Funcoes.adicionaEspacos(rs.getString("CODUNID"),6)+
			   rs.getString("ORIGFISC")+rs.getString("CODTRATTRIB")+
			   Funcoes.transValor(rs.getString("PERCIPIITVENDA"),4,2,true)+
			   Funcoes.transValor(rs.getString("PERCICMSITVENDA"),4,2,true)+
			   Funcoes.strZero("0",4)+
			   Funcoes.transValor("0",12,2,true)+CR;
			 gravaBuffer(sBuffer);
			 iTot75 ++;
		  }
//			rs.close();
//			ps.close();
		  if (!con.getAutoCommit())
		  	con.commit();

	  } 

	  if ( cbConsumidor.getVlrString().equals("S")) {
		  // REGISTRO 75 TABELA DE PRODUTOS SAIDAS
          
         
		sSql = "SELECT IV.CODPROD,P.REFPROD,P.DESCPROD,IV.PERCIPIITVENDA,"+
		   "IV.PERCICMSITVENDA,P.CODUNID,CF.ORIGFISC,CF.CODTRATTRIB,COUNT(*) "+
		   "FROM VDVENDA V,VDITVENDA IV,EQTIPOMOV TM,EQPRODUTO P,VDCLIENTE C,LFCLFISCAL CF "+
		   "WHERE V.DTEMITVENDA BETWEEN ? AND ? AND V.CODEMP=? AND V.CODFILIAL=? AND "+
		   "C.CODCLI=V.CODCLI AND C.CODEMP=V.CODEMPCL AND C.CODFILIAL=V.CODFILIALCL AND " +
		   "C.PESSOACLI='F' AND "+
		   "IV.CODVENDA=V.CODVENDA AND IV.TIPOVENDA=V.TIPOVENDA AND IV.CODEMP=V.CODEMP AND " +
		   "IV.CODFILIAL=V.CODFILIAL AND "+
		   "TM.CODTIPOMOV=V.CODTIPOMOV AND TM.CODEMP=V.CODEMPTM AND " +
		   "TM.CODFILIAL=V.CODFILIALTM AND TM.FISCALTIPOMOV='S' AND "+
		   "P.CODPROD=IV.CODPROD AND P.CODEMP=IV.CODEMPPD AND P.CODFILIAL=IV.CODFILIALPD AND " +
		   "CF.CODFISC=P.CODFISC AND CF.CODEMP=P.CODEMPFC AND CF.CODFILIAL=P.CODFILIALFC "+
		   "GROUP BY IV.CODPROD,P.REFPROD,P.DESCPROD,IV.PERCIPIITVENDA,"+
		   "IV.PERCICMSITVENDA,P.CODUNID,CF.ORIGFISC,CF.CODTRATTRIB ";
          
		  ps = con.prepareStatement(sSql);
		  ps.setDate(1,Funcoes.dateToSQLDate(txtDataini.getVlrDate()));
		  ps.setDate(2,Funcoes.dateToSQLDate(txtDatafim.getVlrDate()));
		  ps.setInt(3,iCodEmp);
		  ps.setInt(4,ListaCampos.getMasterFilial("VDVENDA"));
		  rs = ps.executeQuery();
		  lbAnd.setText("Gerando Tabela de Produtos de Venda (Consumidor)...");
		  while (rs.next()) {
			 sBuffer = "75"+Funcoes.dataAAAAMMDD(txtDataini.getVlrDate())+
			   Funcoes.dataAAAAMMDD(txtDatafim.getVlrDate())+
			   Funcoes.adicionaEspacos(rs.getString((sUsaRefProd.equals("S")?"REFPROD":"CODPROD")),14)+
			   Funcoes.replicate(" ",8)+
			   Funcoes.adicionaEspacos(rs.getString("DESCPROD"),53)+
			   Funcoes.adicionaEspacos(rs.getString("CODUNID"),6)+
			   rs.getString("ORIGFISC")+rs.getString("CODTRATTRIB")+
			   Funcoes.transValor(rs.getString("PERCIPIITVENDA"),4,2,true)+
			   Funcoes.transValor(rs.getString("PERCICMSITVENDA"),4,2,true)+
			   Funcoes.strZero("0",4)+
			   Funcoes.transValor("0",12,2,true)+CR;
			 gravaBuffer(sBuffer);
			 iTot75 ++;
		  }
//			rs.close();
//			ps.close();
		  if (!con.getAutoCommit())
		  	con.commit();

	  } 
      
	  iTotreg = iTot50 + iTot54 + iTot61 + iTot75 + 3;
      
	  sBuffer = "";
	  if (iTot50 > 0) 
		 sBuffer = sBuffer + retorna90(sBuffer,"50",iTot50);
	  if (iTot54 > 0)
		 sBuffer = sBuffer + retorna90(sBuffer,"54",iTot54);
	  if (iTot61 > 0)
		 sBuffer = sBuffer + retorna90(sBuffer,"61",iTot61);
	  if (iTot75 > 0) 
		 sBuffer = sBuffer + retorna90(sBuffer,"75",iTot75);
         
	  sBuffer = sBuffer + "99"+Funcoes.strZero(iTotreg+"",8);
	  sBuffer = sBuffer + Funcoes.replicate(" ",125-sBuffer.length())+"1"+CR;
	  gravaBuffer(sBuffer);
       
	}
	catch (SQLException sqlErr) {
		Funcoes.mensagemErro(this,"Erro consultando arquivo: "+sTabela+"\n"+sqlErr.getMessage());
	}
    
	try {
	  fwSintegra.close();
	}
	catch(IOException ioError) {
		Funcoes.mensagemInforma(this,"Fechando o arquivo: "+sFileName+"\n"+ioError.getMessage());
	}
    
	Funcoes.mensagemInforma(this,"Arquivo exportado!");
	lbAnd.setText("Pronto.");
	btGerar.setEnabled(true);
    
  }
  
  private String retorna90(String sBufferAnt, String sTipo, int iTot) {
    String sBuf = "";
    if (sBufferAnt.trim().equals("")) {
       sBuf = "90"+sCnpjEmp+sInscEmp;
    }
    sBuf = sBuf + sTipo + Funcoes.strZero(iTot+"",8);
    return sBuf;       
  }


  private void gravaBuffer(String sBuf) {
    try {
      fwSintegra.write(sBuf);
      fwSintegra.flush();
    }
    catch (IOException ioerr) {
		Funcoes.mensagemErro(this, "Erro grando no arquivo: '"+sFileName+"\n"+ioerr.getMessage());
    }
          
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
    
    FileDialog fdSintegra = null;
    fdSintegra = new FileDialog(fOrigem,"Salvar sintegra",FileDialog.SAVE);
    fdSintegra.setFile("Receita.txt");
    fdSintegra.setVisible(true);
    if (fdSintegra.getFile() == null) {
      return false;
    }
    sFileName = fdSintegra.getDirectory()+fdSintegra.getFile();
    
    return true;
  }
  
  public void actionPerformed(ActionEvent evt) {
    if (evt.getSource() == btGerar) {
      iniGerar();
    }
  }
}
