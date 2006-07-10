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
import java.awt.Color;
import java.awt.Container;
import java.awt.FileDialog;
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
import org.freedom.componentes.JLabelPad;

import org.freedom.bmps.Icone;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.JPanelPad;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FFilho;

public class FSintegra extends FFilho implements ActionListener {

	private static final long serialVersionUID = 1L;
	private FileWriter fwSintegra;
	private String sFileName = "";
	private String sCnpjEmp = "";
	private String sInscEmp = "";
	private JPanelPad pinCliente = new JPanelPad(700,490);
	private JTextFieldPad txtDataini = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
	private JTextFieldPad txtDatafim = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
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
	private JLabelPad lbAnd = new JLabelPad();
	private int iCodEmp = Aplicativo.iCodEmp;
	private String sUsaRefProd = "N"; 
	
	public FSintegra() {
		
		super(false);
		setTitulo("Gera Arquivo Sintegra");
		setAtribos(50,20,710,470);
		
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
		vLabConvenio.addElement("Convênio 57/95 (a partir de Jan.2004)");
		vValConvenio.addElement("1");
		vValConvenio.addElement("2");
		vValConvenio.addElement("3");
		
		rgConvenio = new JRadioGroup(3,1,vLabConvenio,vValConvenio);
		rgConvenio.setVlrString("3");
		
		vLabNatoper.addElement("Interestaduais - Somente operações sujeitas ao regime de substituição tributária");
		vLabNatoper.addElement("Interestaduais - Operações com ou sem substituição tributária");
		vLabNatoper.addElement("Totalidade das operações do informante");
		vValNatoper.addElement("1");
		vValNatoper.addElement("2");
		vValNatoper.addElement("3");
		rgNatoper = new JRadioGroup(3,3,vLabNatoper,vValNatoper);
		rgNatoper.setVlrString("3");
		
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
		
		pinCliente.adic(new JLabelPad("Inicio"),7,0,110,25);
		pinCliente.adic(txtDataini,7,20,110,20);
		pinCliente.adic(new JLabelPad("Fim"),120,0,107,25);
		pinCliente.adic(txtDatafim,120,20,107,20);
		pinCliente.adic(btGerar,296,15,30,30);
		pinCliente.adic(cbEntrada,7,50,150,20);
		pinCliente.adic(cbSaida,170,50,150,20);
		pinCliente.adic(cbConsumidor,333,50,150,20);
		pinCliente.adic(rgConvenio,7,80,680,80);
		pinCliente.adic(rgNatoper,7,170,680,80);
		pinCliente.adic(rgFinalidade,7,260,680,110);
		
		pinCliente.adic(lbAnd,7,380,680,20);
		lbAnd.setForeground(Color.BLUE);
		lbAnd.setText("Aguardando...");
		colocaMes();   
		btGerar.addActionListener(this);
		
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
		
		Thread th = new Thread( new Runnable() {
			public void run() { gerar(); } } 
		);
		
		try {
			th.start();
		} catch(Exception err) {
			Funcoes.mensagemInforma(this,"Não foi possível criar processo!\n"+err.getMessage());
		}
		
	}
	  
	private void gerar() {
			
		String sSql = "";
		StringBuffer sBuffer = new StringBuffer();
		int iTot50 = 0;
		int iTot54 = 0;
		int iTot60 = 0;
		int iTot61 = 0;
		int iTot75 = 0;
		int iTotreg = 0;
		int iCodEmp = 0;
		
		iCodEmp = Aplicativo.iCodEmp;
				
		if (!valida()) {
			return;
		}
		
		if (sFileName.trim().equals("")) {
			Funcoes.mensagemInforma(this,"Selecione o arquivo!");
			return;
		}
		
		File fSintegra = new File(sFileName);
		
		if (fSintegra.exists()) {
			if (Funcoes.mensagemConfirma(this,"Arquivo: '"+sFileName+"' já existe! Deseja sobrescrever?")!=0) {
				return;
			}
		}
		
		try {
			fSintegra.createNewFile();     
		} catch(IOException err) {
			Funcoes.mensagemErro(this,"Erro limpando arquivo: "+sFileName+"\n"+err.getMessage(),true,con,err);
			return;
		}
		
		try {
			fwSintegra = new FileWriter(fSintegra);
		} catch(IOException ioError) {
			Funcoes.mensagemErro(this,"Erro Criando o arquivo: "+sFileName+"\n"+ioError.getMessage());
			return;
		}
		
		btGerar.setEnabled(false);
		
		// REGISTRO TIPO 1 HEADER DO ARQUIVO 
		
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
			   
		} catch (SQLException sqlErr) {
			sqlErr.printStackTrace();
			Funcoes.mensagemErro(this,"Erro ao consultar preferencias.\n"+sqlErr.getMessage());
		}
		
		geraRegistro10e11();

		iTot50 = geraRegistro50();
		iTot54 = geraRegistro54();
		iTot60 = geraRegistro60();
		iTot61 = geraRegistro61();
		iTot75 = geraRegistro75();
		
		iTotreg = iTot50 + iTot54 + iTot60 + iTot61 + iTot75 + 3;
		  
		if (iTot50 > 0) 
			sBuffer.append(retorna90(sBuffer.toString(),"50",iTot50));
		
		if (iTot54 > 0)
			sBuffer.append(retorna90(sBuffer.toString(),"54",iTot54));
		
		if (iTot60 > 0)
			sBuffer.append(retorna90(sBuffer.toString(),"60",iTot60));
		
		if (iTot61 > 0)
			sBuffer.append(retorna90(sBuffer.toString(),"61",iTot61));
		
		if (iTot75 > 0) 
			sBuffer.append(retorna90(sBuffer.toString(),"75",iTot75));
		     
		sBuffer.append("99");
		sBuffer.append(Funcoes.strZero(String.valueOf(iTotreg),8));
		sBuffer.append(Funcoes.replicate(" ",125-sBuffer.length()) + "1" + CR);
		gravaBuffer(sBuffer.toString());
		
		
		try {
			fwSintegra.close();
		} catch(IOException ioError) {
			ioError.printStackTrace();
			Funcoes.mensagemInforma(this,"Fechando o arquivo: "+sFileName+"\n"+ioError.getMessage());
		}
		
		Funcoes.mensagemInforma(this,"Arquivo exportado!");
		lbAnd.setText("Pronto.");
		btGerar.setEnabled(true);
		    
	}
	
	private void geraRegistro10e11() {
		
		PreparedStatement ps;
		ResultSet rs;
		StringBuffer sSql = new StringBuffer();
		StringBuffer sBuffer = new StringBuffer();
		String sConvenio = rgConvenio.getVlrString();
		String sNatoper = rgNatoper.getVlrString();
		String sFinalidade = rgFinalidade.getVlrString();
		
		try {
			
			sSql.append("SELECT F.CODEMP,F.CNPJFILIAL,F.INSCFILIAL,F.RAZFILIAL,F.CIDFILIAL,F.UFFILIAL,F.FAXFILIAL,");
			sSql.append("F.BAIRFILIAL,F.ENDFILIAL,F.NUMFILIAL,E.NOMECONTEMP,F.FONEFILIAL,F.CEPFILIAL ");
			sSql.append("FROM SGFILIAL F,SGEMPRESA E WHERE ");
			sSql.append("E.CODEMP=F.CODEMP AND F.CODEMP=? AND F.CODFILIAL=?");
			        
			ps = con.prepareStatement(sSql.toString());
			ps.setInt(1,iCodEmp);
			ps.setInt(2,Aplicativo.iCodFilial);
			rs = ps.executeQuery();
			
			if (rs.next()) {
				
				sBuffer.delete(0,sBuffer.length());
				
				sCnpjEmp = Funcoes.adicionaEspacos(rs.getString("CNPJFILIAL"),14);
				sInscEmp = Funcoes.adicionaEspacos(Funcoes.limpaString(rs.getString("INSCFILIAL")),14);
				 
				sBuffer.append("10");
				sBuffer.append(Funcoes.adicionaEspacos(rs.getString("CNPJFILIAL"),14));
				sBuffer.append(Funcoes.adicionaEspacos(Funcoes.limpaString(rs.getString("INSCFILIAL")),14));
				sBuffer.append(Funcoes.adicionaEspacos(rs.getString("RAZFILIAL"),35));
				sBuffer.append(Funcoes.adicionaEspacos(rs.getString("CIDFILIAL"),30));
				sBuffer.append(Funcoes.adicionaEspacos(rs.getString("UFFILIAL"),2));
				sBuffer.append(Funcoes.strZero(rs.getString("FAXFILIAL"),10));
				sBuffer.append(Funcoes.dataAAAAMMDD(txtDataini.getVlrDate()));
				sBuffer.append(Funcoes.dataAAAAMMDD(txtDatafim.getVlrDate()));
				sBuffer.append(sConvenio);
				sBuffer.append(sNatoper);
				sBuffer.append(sFinalidade + CR );
				
				sBuffer.append("11");
				sBuffer.append(Funcoes.adicionaEspacos(rs.getString("ENDFILIAL"),34));
				sBuffer.append(Funcoes.strZero(String.valueOf(rs.getInt("NUMFILIAL")),5));
				sBuffer.append(Funcoes.replicate(" ",22));
				sBuffer.append(Funcoes.adicionaEspacos(rs.getString("BAIRFILIAL"),15));
				sBuffer.append(Funcoes.strZero(rs.getString("CEPFILIAL"),8));
				sBuffer.append(Funcoes.adicionaEspacos(rs.getString("NOMECONTEMP"),28));
				sBuffer.append(Funcoes.strZero(Funcoes.limpaString(rs.getString("FONEFILIAL")),12) + CR );
				
				gravaBuffer(sBuffer.toString());
				
			}
			rs.close();
			ps.close();
			if (!con.getAutoCommit())
				con.commit();
			
		} catch ( Exception e ) {
			e.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao gerar registro 10 e 11!\n" + e.getMessage(), true, con, e );
		}
		
	}
	
	private int geraRegistro50() {
		
		PreparedStatement ps;
		ResultSet rs;
		StringBuffer sSql = new StringBuffer();
		StringBuffer sBuffer = new StringBuffer();
		String sConvenio = rgConvenio.getVlrString();
		int cont = 0;
		
		try {
			

			if ("S".equals(cbEntrada.getVlrString())) {
				// REGISTRO 50 LIVROS FISCAIS DE ENTRADA
				sSql.append("SELECT LF.TIPOLF,LF.ANOMESLF,LF.ESPECIELF,LF.DOCINILF,");
				sSql.append("LF.DOCFIMLF,LF.SERIELF,LF.CODEMITLF,LF.UFLF,LF.DTEMITLF,");
				sSql.append("LF.DTESLF,LF.VLRCONTABILLF,LF.VLRBASEICMSLF,LF.ALIQICMSLF,");
				sSql.append("LF.VLRICMSLF,LF.VLRISENTASICMSLF,LF.VLROUTRASICMSLF,");
				sSql.append("LF.CODNAT,LF.CODMODNOTA,F.CNPJFOR,F.INSCFOR ");
				sSql.append("FROM LFLIVROFISCAL LF,CPFORNECED F ");
				sSql.append("WHERE LF.DTESLF BETWEEN ? AND ? AND ");
				sSql.append("LF.CODEMP=? AND LF.CODFILIAL=? AND ");
				sSql.append("F.CODFOR=LF.CODEMITLF AND F.CODEMP=LF.CODEMPET AND ");
				sSql.append("F.CODFILIAL=LF.CODFILIALET AND LF.TIPOLF='E' AND F.PESSOAFOR='J' ");
				sSql.append("ORDER BY LF.DTESLF");
				ps = con.prepareStatement(sSql.toString());
				ps.setDate(1,Funcoes.dateToSQLDate(txtDataini.getVlrDate()));
				ps.setDate(2,Funcoes.dateToSQLDate(txtDatafim.getVlrDate()));
				ps.setInt(3,iCodEmp);
				ps.setInt(4,ListaCampos.getMasterFilial("LFLIVROFISCAL"));
				rs = ps.executeQuery();
				
				lbAnd.setText("Gerando Entrada...");
				
				while (rs.next()) {
					
					sBuffer.delete(0,sBuffer.length());
					
					/* 01 */  sBuffer.append("50");
					/* 02 */  sBuffer.append(Funcoes.adicionaEspacos(rs.getString("CNPJFOR"),14));
					/* 03 */  sBuffer.append(Funcoes.adicionaEspacos(Funcoes.limpaString(rs.getString("INSCFOR")),14));
					/* 04 */  sBuffer.append(Funcoes.dataAAAAMMDD(Funcoes.sqlDateToDate(rs.getDate("DTESLF"))));
					/* 05 */  sBuffer.append(Funcoes.adicionaEspacos(rs.getString("UFLF"),2));
					/* 06 */  sBuffer.append(Funcoes.strZero(rs.getInt("CODMODNOTA")+"",2));
					/* 07 */  sBuffer.append(Funcoes.adicionaEspacos(rs.getString("SERIELF"),3));
					/* 08 */  sBuffer.append(Funcoes.strZero(rs.getInt("DOCINILF")+"",6));
					/* 09 */  sBuffer.append(Funcoes.adicionaEspacos(rs.getString("CODNAT"),(sConvenio.equals("1")?3:4)));
					/* 10 */  sBuffer.append((sConvenio.equals("1")?"":"T")); // Emitente da nota fiscal P-Própio ou T-Terceiros
					/* 11 */  sBuffer.append(Funcoes.transValor(rs.getString("VLRCONTABILLF"),13,2,true));
					/* 12 */  sBuffer.append(Funcoes.transValor(rs.getString("VLRBASEICMSLF"),13,2,true));
					/* 13 */  sBuffer.append(Funcoes.transValor(rs.getString("VLRICMSLF"),13,2,true));
					/* 14 */  sBuffer.append(Funcoes.transValor(rs.getString("VLRISENTASICMSLF"),13,2,true));
					/* 15 */  sBuffer.append(Funcoes.transValor(rs.getString("VLROUTRASICMSLF"),13,2,true));
					/* 16 */  sBuffer.append(Funcoes.transValor(rs.getString("ALIQICMSLF"),4,2,true));
					/* 17 */  sBuffer.append("N" + CR);
					
					gravaBuffer(sBuffer.toString());
					cont ++;
					
				}
				
				rs.close();
				ps.close();
				
				if (!con.getAutoCommit())
					con.commit();
				
			}
			
			if ("S".equals(cbSaida.getVlrString())) {
				
				// REGISTRO 50 LIVROS FISCAIS DE SAIDA
				
				sSql.delete(0,sSql.length());
				
				sSql.append("SELECT LF.TIPOLF,LF.ANOMESLF,LF.ESPECIELF,LF.DOCINILF,");
				sSql.append("LF.DOCFIMLF,LF.SERIELF,LF.CODEMITLF,LF.UFLF,LF.DTEMITLF,");
				sSql.append("LF.DTESLF,LF.VLRCONTABILLF,LF.VLRBASEICMSLF,LF.ALIQICMSLF,");
				sSql.append("LF.VLRICMSLF,LF.VLRISENTASICMSLF,LF.VLROUTRASICMSLF,");
				sSql.append("LF.CODNAT,LF.CODMODNOTA,C.CNPJCLI,C.INSCCLI ");
				sSql.append("FROM LFLIVROFISCAL LF,VDCLIENTE C ");
				sSql.append("WHERE LF.DTEMITLF BETWEEN ? AND ? ");
				sSql.append("AND LF.CODEMP=? AND LF.CODFILIAL=? ");
				sSql.append("AND C.CODCLI=LF.CODEMITLF AND C.CODEMP=LF.CODEMPET ");
				sSql.append("AND C.CODFILIAL=LF.CODFILIALET AND C.PESSOACLI='J' ");
				sSql.append("AND LF.TIPOLF='S' ");
				sSql.append("ORDER BY LF.DTEMITLF");
				ps = con.prepareStatement(sSql.toString());
				ps.setDate(1,Funcoes.dateToSQLDate(txtDataini.getVlrDate()));
				ps.setDate(2,Funcoes.dateToSQLDate(txtDatafim.getVlrDate()));
				ps.setInt(3,iCodEmp);
				ps.setInt(4,ListaCampos.getMasterFilial("LFLIVROFISCAL"));
				rs = ps.executeQuery();
				
				lbAnd.setText("Gerando Saídas...");
				
				while (rs.next()) {
					
					sBuffer.delete(0,sBuffer.length());
					
					/* 01 */ sBuffer.append("50");
					/* 02 */ sBuffer.append(Funcoes.adicionaEspacos(rs.getString("CNPJCLI"),14));
					/* 03 */ sBuffer.append(Funcoes.adicionaEspacos(Funcoes.limpaString(rs.getString("INSCCLI")),14));
					/* 04 */ sBuffer.append(Funcoes.dataAAAAMMDD(Funcoes.sqlDateToDate(rs.getDate("DTEMITLF"))));
					/* 05 */ sBuffer.append(Funcoes.adicionaEspacos(rs.getString("UFLF"),2));
					/* 06 */ sBuffer.append(Funcoes.strZero(String.valueOf(rs.getInt("CODMODNOTA")),2));
					/* 07 */ sBuffer.append(Funcoes.adicionaEspacos(rs.getString("SERIELF"),3));
					/* 08 */ sBuffer.append(Funcoes.strZero(String.valueOf(rs.getInt("DOCINILF")),6));
					/* 09 */ sBuffer.append(Funcoes.adicionaEspacos(rs.getString("CODNAT"),(sConvenio.equals("1")?3:4)));
					/* 10 */ sBuffer.append((sConvenio.equals("1")?"":"P")); //Emitente da nota fiscal P-Própio ou T-Terceiros
					/* 11 */ sBuffer.append(Funcoes.transValor(rs.getString("VLRCONTABILLF"),13,2,true));
					/* 12 */ sBuffer.append(Funcoes.transValor(rs.getString("VLRBASEICMSLF"),13,2,true));
					/* 13 */ sBuffer.append(Funcoes.transValor(rs.getString("VLRICMSLF"),13,2,true));
					/* 14 */ sBuffer.append(Funcoes.transValor(rs.getString("VLRISENTASICMSLF"),13,2,true));
					/* 15 */ sBuffer.append(Funcoes.transValor(rs.getString("VLROUTRASICMSLF"),13,2,true));
					/* 16 */ sBuffer.append(Funcoes.transValor(rs.getString("ALIQICMSLF"),4,2,true));
					/* 17 */ sBuffer.append("N" + CR);
					
					gravaBuffer(sBuffer.toString());
					cont ++;
				}
				
				rs.close();
				ps.close();
				
				if (!con.getAutoCommit())
					con.commit();
				
			}
			
			
		} catch ( Exception e ) {
			e.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao gerar registro 50!\n" + e.getMessage(), true, con, e );
		}
		
		return cont;
		
	}
	
	private int geraRegistro54() {
		
		PreparedStatement ps;
		ResultSet rs;
		StringBuffer sSql = new StringBuffer();
		StringBuffer sBuffer = new StringBuffer();
		String sConvenio = rgConvenio.getVlrString();
		String sDocTmp = "";
		int iOrdem = 0;
		int cont = 0;
		
		try {
			
			if ( "S".equals(cbEntrada.getVlrString()) ) {
				
				// REGISTRO 54 INFORMAÇÕES DOS ITENS DAS NOTAS FISCAIS DE ENTRADA
				
				sSql.append("SELECT C.DTENTCOMPRA,C.DOCCOMPRA,IC.CODITCOMPRA,C.CODFOR,");
				sSql.append("F.UFFOR,F.CNPJFOR,IC.CODNAT,IC.CODPROD,P.REFPROD,");
				sSql.append("C.DTEMITCOMPRA,C.SERIE,TM.CODMODNOTA,IC.PERCICMSITCOMPRA,");
				sSql.append("IC.QTDITCOMPRA,IC.VLRLIQITCOMPRA,IC.VLRBASEICMSITCOMPRA,");
				sSql.append("IC.PERCICMSITCOMPRA,IC.VLRBASEICMSITCOMPRA,IC.VLRIPIITCOMPRA,");
				sSql.append("CF.ORIGFISC, CF.CODTRATTRIB ");
				sSql.append("FROM CPCOMPRA C,CPFORNECED F,CPITCOMPRA IC,EQTIPOMOV TM,EQPRODUTO P, LFCLFISCAL CF ");
				sSql.append("WHERE C.DTENTCOMPRA BETWEEN ? AND ? AND C.CODEMP=? AND C.CODFILIAL=? AND ");
				sSql.append("IC.CODCOMPRA=C.CODCOMPRA AND  IC.CODEMP=C.CODEMP AND IC.CODFILIAL=C.CODFILIAL AND ");
				sSql.append("TM.CODTIPOMOV=C.CODTIPOMOV AND TM.CODEMP=C.CODEMPTM AND TM.CODFILIAL=C.CODFILIALTM AND ");
				sSql.append("F.CODFOR=C.CODFOR AND F.CODEMP=C.CODEMPFR AND F.CODFILIAL=C.CODFILIALFR AND ");
				sSql.append("P.CODPROD=IC.CODPROD AND P.CODEMP=IC.CODEMPPD AND P.CODFILIAL=IC.CODFILIALPD AND ");
				sSql.append("CF.CODFISC=P.CODFISC AND CF.CODEMP=P.CODEMPFC AND CF.CODFILIAL=P.CODFILIALFC AND ");
				sSql.append("TM.FISCALTIPOMOV='S' ORDER BY C.DTENTCOMPRA,C.DOCCOMPRA,IC.CODITCOMPRA");				     
				  
				ps = con.prepareStatement(sSql.toString());
				ps.setDate(1,Funcoes.dateToSQLDate(txtDataini.getVlrDate()));
				ps.setDate(2,Funcoes.dateToSQLDate(txtDatafim.getVlrDate()));
				ps.setInt(3,iCodEmp);
				ps.setInt(4,ListaCampos.getMasterFilial("CPCOMPRA"));
				rs = ps.executeQuery();
				
				lbAnd.setText("Gerando Itens NF Entrada...");
				
				sDocTmp = "";
				  
				String s54E01 = "54";
				String s54E02 = "";
				String s54E03 = "";
				String s54E04 = "";
				String s54E05 = "";
				String s54E06 = "";
				String s54E07 = "";
				String s54E08 = "";
				String s54E09 = "";
				String s54E10 = "";
				String s54E11 = "";
				String s54E12 = "";
				String s54E13 = "";
				String s54E14 = "";
				String s54E15 = "";
				String s54E16 = "";		  
				  
				  
				while (rs.next()) {
					if (!sDocTmp.equals(""+rs.getInt("DOCCOMPRA"))) {
						iOrdem = 1;
					}
					
					sBuffer.delete(0,sBuffer.length());
					
					s54E02 = Funcoes.adicionaEspacos(rs.getString("CNPJFOR"),14);
					s54E03 = Funcoes.strZero((rs.getString("CODMODNOTA")==null?0:rs.getInt("CODMODNOTA"))+"",2);
					s54E04 = Funcoes.adicionaEspacos(rs.getString("SERIE"),3);
					s54E05 = Funcoes.strZero(rs.getInt("DOCCOMPRA")+"",6);
					s54E06 = Funcoes.adicionaEspacos(rs.getString("CODNAT"),(sConvenio.equals("1")?3:4));
					s54E07 = Funcoes.copy((sConvenio.equals("1")?"":(rs.getString("ORIGFISC").trim()+rs.getString("CODTRATTRIB").trim())),3);
					s54E08 = Funcoes.strZero(iOrdem+"",3);
					s54E09 = Funcoes.adicionaEspacos(rs.getString((sUsaRefProd.equals("S")?"REFPROD":"CODPROD")),14);
					s54E10 = Funcoes.transValor(rs.getString("QTDITCOMPRA"),(sConvenio.equals("1")?13:11),3,true);
					s54E11 = Funcoes.transValor(rs.getString("VLRLIQITCOMPRA"),12,2,true);
					s54E12 = Funcoes.transValor("0",12,2,true);
					s54E13 = Funcoes.transValor(rs.getString("VLRBASEICMSITCOMPRA"),12,2,true);
					s54E14 = Funcoes.transValor("0",12,2,true);
					s54E15 = Funcoes.transValor(rs.getString("VLRIPIITCOMPRA"),12,2,true);
					s54E16 = Funcoes.transValor(rs.getString("PERCICMSITCOMPRA"),4,2,true);
					
					sBuffer.append(s54E01);
					sBuffer.append(s54E02);
					sBuffer.append(s54E03);
					sBuffer.append(s54E04);
					sBuffer.append(s54E05);
					sBuffer.append(s54E06);
					sBuffer.append(s54E07);
					sBuffer.append(s54E08);
					sBuffer.append(s54E09);
					sBuffer.append(s54E10);
					sBuffer.append(s54E11);
					sBuffer.append(s54E12);
					sBuffer.append(s54E13);
					sBuffer.append(s54E14);
					sBuffer.append(s54E15);
					sBuffer.append(s54E16 + CR);
					 
					gravaBuffer(sBuffer.toString());
					sDocTmp = String.valueOf(rs.getInt("DOCCOMPRA"));
					iOrdem++;
					cont ++;
				}
				
				rs.close();
				ps.close();
				
				if (!con.getAutoCommit())
					con.commit();
				      
			}
				  
			if ( "S".equals(cbSaida.getVlrString()) ) {
				
				// REGISTRO 54 INFORMAÇÕES DOS ITENS DAS NOTAS FISCAIS DE SAÍDA
				
				sSql.delete(0,sSql.length());
				
				sSql.append("SELECT V.DTSAIDAVENDA,V.DOCVENDA,IV.CODITVENDA,V.CODCLI,");
				sSql.append("C.UFCLI,C.CNPJCLI,IV.CODNAT,IV.CODPROD,P.REFPROD,");
				sSql.append("V.DTEMITVENDA,V.SERIE,TM.CODMODNOTA,IV.PERCICMSITVENDA,"); 
				sSql.append("IV.QTDITVENDA,IV.VLRLIQITVENDA,IV.VLRBASEICMSITVENDA,");
				sSql.append("IV.PERCICMSITVENDA,IV.VLRBASEICMSITVENDA,IV.VLRIPIITVENDA,");
				sSql.append("CF.ORIGFISC,CF.CODTRATTRIB ");
				sSql.append("FROM VDVENDA V,VDCLIENTE C,VDITVENDA IV,EQTIPOMOV TM,EQPRODUTO P,LFCLFISCAL CF ");
				sSql.append("WHERE V.DTEMITVENDA BETWEEN ? AND ? ");
				sSql.append("AND V.CODEMP=? AND V.CODFILIAL=? AND V.TIPOVENDA='V' ");
				sSql.append("AND IV.CODVENDA=V.CODVENDA AND TM.CODTIPOMOV=V.CODTIPOMOV ");
				sSql.append("AND C.CODCLI=V.CODCLI AND P.CODPROD=IV.CODPROD AND TM.FISCALTIPOMOV='S' ");
				sSql.append("AND CF.CODFISC=P.CODFISC AND CF.CODEMP=P.CODEMPFC AND CF.CODFILIAL=P.CODFILIALFC ");
				sSql.append("ORDER BY V.DTEMITVENDA,V.DOCVENDA,IV.CODITVENDA");
				 
				ps = con.prepareStatement(sSql.toString());
				ps.setDate(1,Funcoes.dateToSQLDate(txtDataini.getVlrDate()));
				ps.setDate(2,Funcoes.dateToSQLDate(txtDatafim.getVlrDate()));
				ps.setInt(3,iCodEmp);
				ps.setInt(4,ListaCampos.getMasterFilial("VDVENDA"));
				rs = ps.executeQuery();
				
				lbAnd.setText("Gerando Itens NF Saída...");
				
				sDocTmp = "";
				
				while (rs.next()) {
						
					if (!sDocTmp.equals(String.valueOf(rs.getInt("DOCVENDA")))) {
						iOrdem = 1;
					}
					
					sBuffer.delete(0,sBuffer.length());
					
					sBuffer.append("54");
					sBuffer.append(Funcoes.adicionaEspacos(rs.getString("CNPJCLI"),14));
					sBuffer.append(Funcoes.strZero((rs.getString("CODMODNOTA")==null?0:rs.getInt("CODMODNOTA"))+"",2));
					sBuffer.append(Funcoes.adicionaEspacos(rs.getString("SERIE"),3));
					sBuffer.append((sConvenio.equals("1")?Funcoes.replicate(" ",2):"")); // Sub serie 
					sBuffer.append(Funcoes.strZero(rs.getInt("DOCVENDA")+"",6));
					sBuffer.append(Funcoes.adicionaEspacos(rs.getString("CODNAT"),(sConvenio.equals("1")?3:4)));
					sBuffer.append(Funcoes.copy((sConvenio.equals("1")?"":rs.getString("ORIGFISC").trim()+rs.getString("CODTRATTRIB").trim()),3));
					sBuffer.append(Funcoes.strZero(iOrdem+"",3));
					sBuffer.append(Funcoes.adicionaEspacos(rs.getString((sUsaRefProd.equals("S")?"REFPROD":"CODPROD")),14));
					sBuffer.append(Funcoes.transValor(rs.getString("QTDITVENDA"),(sConvenio.equals("1")?13:11),3,true));
					sBuffer.append(Funcoes.transValor(rs.getString("VLRLIQITVENDA"),12,2,true));
					sBuffer.append(Funcoes.transValor("0",12,2,true));
					sBuffer.append(Funcoes.transValor(rs.getString("VLRBASEICMSITVENDA"),12,2,true));
					sBuffer.append(Funcoes.transValor("0",12,2,true));
					sBuffer.append(Funcoes.transValor(rs.getString("VLRIPIITVENDA"),12,2,true));
					sBuffer.append(Funcoes.transValor(rs.getString("PERCICMSITVENDA"),4,2,true)+CR);
					
					gravaBuffer(sBuffer.toString());
					iOrdem++;
					cont ++;
					
					sDocTmp = String.valueOf(rs.getInt("DOCVENDA"));
					
				}
				
				rs.close();
				ps.close();
				
				if (!con.getAutoCommit())
					con.commit();
				      
			}
			
		} catch ( Exception e ) {
			e.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao gerar registro 54!\n" + e.getMessage(), true, con, e );
		}
		
		return cont;
		
	}
	
	private int geraRegistro60() {
		
		PreparedStatement ps;
		ResultSet rs;
		StringBuffer sSql = new StringBuffer();
		StringBuffer sBuffer = new StringBuffer();
		String sAliq = "";
		String sCampo = "";
		float fValor = 0;
		int cont = 0;
		
		if ( "S".equals(cbSaida.getVlrString()) ) {
			
			try {
				//	REGISTRO 60 INFORMAÇÕES DOS ITENS DAS NOTAS FISCAIS DE SAÍDA POR ECF
				sSql.append("SELECT L.DTLX, L.CODCAIXA, L.PRIMCUPOMLX, L.ULTCUPOMLX, L.NUMREDLX, L.TGTOTAL,L.VLRCONTABILLX,");
				sSql.append("( SELECT I.NSERIEIMP FROM PVCAIXA C, SGESTACAOIMP EI, SGIMPRESSORA I ");
				sSql.append("        WHERE C.CODEMP=L.CODEMP AND C.CODFILIAL=L.CODFILIAL AND C.CODCAIXA=L.CODCAIXA ");
				sSql.append("        AND EI.CODEMP=C.CODEMPET AND EI.CODFILIAL=C.CODFILIALET AND EI.CODEST=C.CODEST ");
				sSql.append("		 AND I.CODEMP=EI.CODEMPIP AND I.CODFILIAL=EI.CODFILIALIP AND I.CODIMP=EI.CODIMP AND I.TIPOIMP IN (6,8) ) AS NUMSERIEIMP,");
				sSql.append("L.TSUBSTITUICAO,L.TISENCAO,L.TNINCIDENCIA,");
				sSql.append("L.ALIQ01,L.ALIQ02,L.ALIQ03,L.ALIQ04,L.ALIQ05,L.ALIQ06,L.ALIQ07,L.ALIQ08,");
				sSql.append("L.ALIQ09,L.ALIQ10,L.ALIQ11,L.ALIQ01,L.ALIQ12,L.ALIQ13,L.ALIQ14,L.ALIQ15,L.ALIQ16,");
				sSql.append("L.TT01,L.TT02,L.TT03,L.TT04,L.TT05,L.TT06,L.TT07,L.TT08,");
				sSql.append("L.TT09,L.TT10,L.TT11,L.TT12,L.TT13,L.TT14,L.TT15,L.TT16 ");
				sSql.append("FROM PVLEITURAX L ");
				sSql.append("WHERE L.DTLX BETWEEN ? AND ? ");
				sSql.append("AND L.CODEMP=? AND L.CODFILIAL=? ");
				sSql.append("ORDER BY L.DTLX");
				  
				ps = con.prepareStatement(sSql.toString());
				ps.setDate(1,Funcoes.dateToSQLDate(txtDataini.getVlrDate()));
				ps.setDate(2,Funcoes.dateToSQLDate(txtDatafim.getVlrDate()));
				ps.setInt(3,iCodEmp);
				ps.setInt(4,ListaCampos.getMasterFilial("PVLEITURAX"));
				rs = ps.executeQuery();
				
				lbAnd.setText("Gerando registro de ECF...");
				
				while (rs.next()) {
															
					/* 01 */ sBuffer.append("60");
					/* 02 */ sBuffer.append("M"); 
					/* 03 */ sBuffer.append(Funcoes.dataAAAAMMDD( Funcoes.sqlDateToDate(rs.getDate("DTLX")) ));
					/* 04 */ sBuffer.append(Funcoes.adicionaEspacos( rs.getString("NUMSERIEIMP"), 20 ));
					/* 05 */ sBuffer.append(Funcoes.strZero( String.valueOf(rs.getInt("CODCAIXA")), 3 ));
					/* 06 */ sBuffer.append("2D"); // por se tratar de emição por ECF.
					/* 07 */ sBuffer.append(Funcoes.strZero( String.valueOf(rs.getInt("PRIMCUPOMLX")), 6 ));
					/* 08 */ sBuffer.append(Funcoes.strZero( String.valueOf(rs.getInt("ULTCUPOMLX")), 6 ));
					/* 09 */ sBuffer.append(Funcoes.strZero( String.valueOf(rs.getInt("NUMREDLX")), 6 ));
					/* ?? */ sBuffer.append("000");
					/* 10 */ sBuffer.append(Funcoes.transValor( String.valueOf(rs.getInt("VLRCONTABILLX")), 16, 2, true ));
					/* 11 */ sBuffer.append(Funcoes.transValor( String.valueOf(rs.getInt("TGTOTAL")), 16, 2, true ));
					/* 12 */ sBuffer.append(Funcoes.replicate( " ", 37 ) + CR );					

					gravaBuffer(sBuffer.toString());
					sBuffer.delete(0,sBuffer.length());
					cont ++;		
					
					//	19 é o número de aliquotas. 
					for( int i=1; i<= 19; i++ ) {
						
						fValor = 0;
						
						if( i <= 16 ) {
							sCampo = "ALIQ"+Funcoes.strZero(String.valueOf(i),2);
							sAliq = rs.getString(sCampo);
							sCampo = "TT"+Funcoes.strZero(String.valueOf(i),2);
							fValor = rs.getFloat(sCampo);
						}
						else {
							if( i == 17) {
								sAliq = "F   ";
								sCampo = "TSUBSTITUICAO";
							}
							else if( i == 18) {
								sAliq = "I   ";
								sCampo = "TISENCAO";
							}
							else if( i == 19) {
								sAliq = "N   ";
								sCampo = "TNINCIDENCIA";
							}
							fValor = rs.getFloat(sCampo);
						}
						
						if( sAliq == null )
							continue;
						
						/* 01 */ sBuffer.append("60"); 
						/* 02 */ sBuffer.append("A");
						/* 03 */ sBuffer.append(Funcoes.dataAAAAMMDD( Funcoes.sqlDateToDate(rs.getDate("DTLX")) ));
						/* 04 */ sBuffer.append(Funcoes.adicionaEspacos( rs.getString("NUMSERIEIMP"), 20 ) );	
						
						if( i >= 16 )
							/* 05 */ sBuffer.append(Funcoes.adicionaEspacos( sAliq, 4 ) );	
						else
							/* 05 */ sBuffer.append( Funcoes.transValor( sAliq, 4, 2, true ) );
						 
						/* 06 */ sBuffer.append(Funcoes.transValor( String.valueOf(fValor), 12, 2, true ));
						/* 07 */ sBuffer.append(Funcoes.replicate( " ", 79 ) + CR);
									
						gravaBuffer(sBuffer.toString());
						sBuffer.delete(0,sBuffer.length());
						cont ++;
					}
				}
				rs.close();
				ps.close();
				if (!con.getAutoCommit())
					con.commit();
				  
				sSql.delete(0,sSql.length());
				
				sSql.append("SELECT EXTRACT (YEAR FROM V.DTEMITVENDA) AS ANO, EXTRACT (MONTH FROM V.DTEMITVENDA) AS MES , ");
				sSql.append("I.CODPROD, I.QTDITVENDA, (I.QTDITVENDA * I.PRECOITVENDA) AS VLRBRUTO, I.VLRBASEICMSITVENDA, ");
				sSql.append("I.TIPOFISC, I.PERCICMSITVENDA ");
				sSql.append("FROM VDITVENDA I, VDVENDA V ");
				sSql.append("WHERE I.CODEMP=? AND I.CODFILIAL=? AND I.TIPOVENDA='E' ");
				sSql.append("AND V.CODEMP=I.CODEMP AND V.CODFILIAL=I.CODFILIAL ");
				sSql.append("AND V.CODVENDA=I.CODVENDA AND V.TIPOVENDA=I.TIPOVENDA ");
				sSql.append("AND V.DTEMITVENDA BETWEEN ? AND ? ");
				sSql.append("GROUP BY 3,1,2,8,7,4,5,6 ");
				sSql.append("ORDER BY 3,1,2,8,7,4,5,6");          
				
				ps = con.prepareStatement(sSql.toString());
				ps.setInt(1,iCodEmp);
				ps.setInt(2,ListaCampos.getMasterFilial("VDITVENDA"));
				ps.setDate(3,Funcoes.dateToSQLDate(txtDataini.getVlrDate()));
				ps.setDate(4,Funcoes.dateToSQLDate(txtDatafim.getVlrDate()));
				rs = ps.executeQuery();
				
				lbAnd.setText("Gerando registro dos itens de ECF...");			
				
				while (rs.next()) {
									
					sBuffer.delete(0,sBuffer.length());
					
					/* 01 */ sBuffer.append("60");
					/* 02 */ sBuffer.append("R");
					/* 03 */ sBuffer.append(Funcoes.strZero( rs.getString("MES"), 2 ));
					/* 03 */ sBuffer.append(Funcoes.strZero( rs.getString("ANO"), 4 ));
					/* 04 */ sBuffer.append(Funcoes.adicionaEspacos( rs.getString("CODPROD").trim(), 14 ));
					/* 05 */ sBuffer.append(Funcoes.transValor( rs.getString("QTDITVENDA"), 13, 3, true ));
					/* 06 */ sBuffer.append(Funcoes.transValor( rs.getString("VLRBRUTO"), 16, 2, true ));
					/* 07 */ sBuffer.append(Funcoes.transValor( rs.getString("VLRBASEICMSITVENDA"), 16, 2, true ));	
					
					if("TT".equals(rs.getString("TIPOFISC").trim())) {
						/* 08 */ sBuffer.append(Funcoes.transValor( rs.getString("PERCICMSITVENDA"), 4, 2, true ) );
					} else {
						/* 08 */ sBuffer.append(Funcoes.copy(rs.getString("TIPOFISC"),1) + "   " );
					}
		
					/* 09 */ sBuffer.append(Funcoes.replicate( " ", 54 ) + CR);
					
					gravaBuffer(sBuffer.toString());
					cont ++;
				}
				rs.close();
				ps.close();
				if (!con.getAutoCommit())
					con.commit();
			} catch ( Exception e ) {
				e.printStackTrace();
				Funcoes.mensagemErro( this, "Erro ao gerar registro 60!\n" + e.getMessage(), true, con, e );
			}
			          
		}
		
		return cont;
		
	}
	
	private int geraRegistro61() {
		
		PreparedStatement ps;
		ResultSet rs;
		StringBuffer sSql = new StringBuffer();
		StringBuffer sBuffer = new StringBuffer();
		String sConvenio = rgConvenio.getVlrString();
		int cont = 0;
		
		try {
			
			if ( "S".equals(cbConsumidor.getVlrString()) && "1".equals(sConvenio) ) {
								
				//	REGISTRO 61 LIVROS FISCAIS DE SAIDA
				
				sSql.append("SELECT LF.TIPOLF,LF.ANOMESLF,LF.ESPECIELF,LF.DOCINILF,");
				sSql.append("LF.DOCFIMLF,LF.SERIELF,LF.CODEMITLF,LF.UFLF,LF.DTEMITLF,");
				sSql.append("LF.DTESLF,LF.VLRCONTABILLF,LF.VLRBASEICMSLF,LF.ALIQICMSLF,");
				sSql.append("LF.VLRICMSLF,LF.VLRISENTASICMSLF,LF.VLROUTRASICMSLF,");
				sSql.append("LF.CODNAT,LF.CODMODNOTA,C.CNPJCLI,C.INSCCLI ");
				sSql.append("FROM LFLIVROFISCAL LF,VDCLIENTE C ");
				sSql.append("WHERE LF.DTEMITLF BETWEEN ? AND ? AND LF.CODEMP=? AND LF.CODFILIAL=? AND ");
				sSql.append("C.CODCLI=LF.CODEMITLF AND C.CODEMP=LF.CODEMPET AND C.CODFILIAL=LF.CODFILIALET AND ");
				sSql.append("LF.TIPOLF='S' AND C.PESSOACLI='F' ");
				sSql.append("ORDER BY LF.DTEMITLF");
				
				ps = con.prepareStatement(sSql.toString());
				ps.setDate(1,Funcoes.dateToSQLDate(txtDataini.getVlrDate()));
				ps.setDate(2,Funcoes.dateToSQLDate(txtDatafim.getVlrDate()));
				ps.setInt(3,iCodEmp);
				ps.setInt(4,ListaCampos.getMasterFilial("LFLIVROFISCAL"));
				rs = ps.executeQuery();
				
				lbAnd.setText("Gerando NF Saída (Consumidor)...");
				
				while (rs.next()) {
					
					sBuffer.delete(0,sBuffer.length());
					
					sBuffer.append("61"+Funcoes.replicate(" ",14));
					sBuffer.append(Funcoes.replicate(" ",14));
					sBuffer.append(Funcoes.dataAAAAMMDD(Funcoes.sqlDateToDate(rs.getDate("DTEMITLF"))));
					sBuffer.append(Funcoes.strZero(rs.getInt("CODMODNOTA")+"",2));
					sBuffer.append(Funcoes.adicionaEspacos(rs.getString("SERIELF"),3));
					sBuffer.append(Funcoes.replicate(" ",2)); // Sub serie 
					sBuffer.append(Funcoes.strZero(rs.getInt("DOCINILF")+"",6));
					sBuffer.append(Funcoes.strZero(rs.getInt("DOCFIMLF")+"",6));
					sBuffer.append(Funcoes.transValor(rs.getString("VLRCONTABILLF"),13,2,true));
					sBuffer.append(Funcoes.transValor(rs.getString("VLRBASEICMSLF"),13,2,true));
					sBuffer.append(Funcoes.transValor(rs.getString("VLRICMSLF"),12,2,true));
					sBuffer.append(Funcoes.transValor(rs.getString("VLRISENTASICMSLF"),13,2,true));
					sBuffer.append(Funcoes.transValor(rs.getString("VLROUTRASICMSLF"),13,2,true));
					sBuffer.append(Funcoes.transValor(rs.getString("ALIQICMSLF"),4,2,true) + CR );
					
					gravaBuffer(sBuffer.toString());
					cont ++;
				}
				
				rs.close();
				ps.close();
				
				if (!con.getAutoCommit())
					con.commit();
				
			}
			
		} catch ( Exception e ) {
			e.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao gerar registro 61!\n" + e.getMessage(), true, con, e );
		}
		
		return cont;
		
	}
	
	private int geraRegistro75() {
		
		PreparedStatement ps;
		ResultSet rs;
		StringBuffer sSql = new StringBuffer();
		StringBuffer sBuffer = new StringBuffer();
		final int COL_75_CODPROD = 1;
		final int COL_75_REFPROD = 2;
		final int COL_75_DESCPROD = 3;
		final int COL_75_PERCIPI = 4;
		final int COL_75_PERCICMS = 5;
		final int COL_75_CODUNID = 6;
		final int COL_75_ORIGFISC = 7;
		final int COL_75_CODTRATTRIB = 8;
		String sConvenio = rgConvenio.getVlrString();
		String sSqlEntrada = "";
		String sSqlSaida = "";
		String sSqlConsumidor = "";
		int iParam = 1;
		int cont = 0;
		
		try {
			
			if ( ( "S".equals(cbEntrada.getVlrString()) ) || 
					( "S".equals(cbSaida.getVlrString()) || "S".equals(cbConsumidor.getVlrString()) ) ) {
				
				// REGISTRO 75 TABELA DE PRODUTOS ENTRADAS, SAIDAS, CONSUMIDOR
				
				sSqlEntrada = "";
				sSqlSaida = "";
				sSqlConsumidor = "";
				  
				if ( cbEntrada.getVlrString().equals("S") ) //IC.PERCICMSITCOMPRA 
					sSqlEntrada = "SELECT IC.CODPROD,P.REFPROD,P.DESCPROD,COALESCE(CF.ALIQIPIFISC,0),"+
					"COALESCE(CF.ALIQLFISC,0),P.CODUNID,CF.ORIGFISC,CF.CODTRATTRIB "+
					"FROM CPCOMPRA C,CPITCOMPRA IC,EQTIPOMOV TM,EQPRODUTO P,LFCLFISCAL CF "+
					"WHERE C.DTENTCOMPRA BETWEEN ? AND ? AND C.CODEMP=? AND C.CODFILIAL=? AND "+
					"IC.CODCOMPRA=C.CODCOMPRA AND IC.CODEMP=C.CODEMP AND IC.CODFILIAL=C.CODFILIAL AND "+
					"TM.CODTIPOMOV=C.CODTIPOMOV AND TM.CODEMP=C.CODEMPTM AND TM.CODFILIAL=C.CODFILIALTM AND "+
					"P.CODPROD=IC.CODPROD AND P.CODEMP=IC.CODEMPPD AND P.CODFILIAL=IC.CODFILIALPD AND "+
					"CF.CODFISC=P.CODFISC AND CF.CODEMP=P.CODEMPFC AND CF.CODFILIAL=P.CODFILIALFC AND "+
					"TM.FISCALTIPOMOV='S' ";
				
				if ( cbSaida.getVlrString().equals("S") ) // IV.PERCICMSITVENDA 
					sSqlSaida = "SELECT IV.CODPROD,P.REFPROD,P.DESCPROD,COALESCE(CF.ALIQIPIFISC,0),"+
					"COALESCE(CF.ALIQLFISC,0),P.CODUNID,CF.ORIGFISC,CF.CODTRATTRIB "+
					"FROM VDVENDA V,VDITVENDA IV,EQTIPOMOV TM,EQPRODUTO P,VDCLIENTE C,LFCLFISCAL CF "+
					"WHERE V.DTEMITVENDA BETWEEN ? AND ? AND V.CODEMP=? AND V.CODFILIAL=? AND "+
					"C.CODCLI=V.CODCLI AND C.CODEMP=V.CODEMPCL AND C.CODFILIAL=V.CODFILIALCL AND " +
					//"C.PESSOACLI='J' AND "+
					"IV.CODVENDA=V.CODVENDA AND IV.TIPOVENDA=V.TIPOVENDA AND IV.CODEMP=V.CODEMP AND " +
					"IV.CODFILIAL=V.CODFILIAL AND "+
					"TM.CODTIPOMOV=V.CODTIPOMOV AND TM.CODEMP=V.CODEMPTM AND " +
					"TM.CODFILIAL=V.CODFILIALTM AND TM.FISCALTIPOMOV='S' AND "+
					"P.CODPROD=IV.CODPROD AND P.CODEMP=IV.CODEMPPD AND P.CODFILIAL=IV.CODFILIALPD AND " +
					"CF.CODFISC=P.CODFISC AND CF.CODEMP=P.CODEMPFC AND CF.CODFILIAL=P.CODFILIALFC ";
				
				if ( cbConsumidor.getVlrString().equals("S") && (sConvenio.equals("1")) ) // IV.PERCICMSITVENDA
					sSqlConsumidor = "SELECT IV.CODPROD,P.REFPROD,P.DESCPROD,COALESCE(CF.ALIQIPIFISC,0),"+
					"COALESCE(CF.ALIQLFISC,0),P.CODUNID,CF.ORIGFISC,CF.CODTRATTRIB "+
					"FROM VDVENDA V,VDITVENDA IV,EQTIPOMOV TM,EQPRODUTO P,VDCLIENTE C,LFCLFISCAL CF "+
					"WHERE V.DTEMITVENDA BETWEEN ? AND ? AND V.CODEMP=? AND V.CODFILIAL=? AND "+
					"C.CODCLI=V.CODCLI AND C.CODEMP=V.CODEMPCL AND C.CODFILIAL=V.CODFILIALCL AND " +
					"C.PESSOACLI='F' AND "+
					"IV.CODVENDA=V.CODVENDA AND IV.TIPOVENDA=V.TIPOVENDA AND IV.CODEMP=V.CODEMP AND " +
					"IV.CODFILIAL=V.CODFILIAL AND "+
					"TM.CODTIPOMOV=V.CODTIPOMOV AND TM.CODEMP=V.CODEMPTM AND " +
					"TM.CODFILIAL=V.CODFILIALTM AND TM.FISCALTIPOMOV='S' AND "+
					"P.CODPROD=IV.CODPROD AND P.CODEMP=IV.CODEMPPD AND P.CODFILIAL=IV.CODFILIALPD AND " +
					"CF.CODFISC=P.CODFISC AND CF.CODEMP=P.CODEMPFC AND CF.CODFILIAL=P.CODFILIALFC ";
				   
				if (!sSqlEntrada.equals(""))
					sSql.append(sSqlEntrada);
				
				if (!sSqlSaida.equals("")) 
					sSql.append( ( sSql.equals("") ? "" : " UNION " ) + sSqlSaida );
				
				if (!sSqlConsumidor.equals("")) 
					sSql.append( ( sSql.equals("") ? "" : " UNION " ) + sSqlConsumidor);
				
				sSql.append(" GROUP BY 1,2,3,4,5,6,7,8 ");
				  
				ps = con.prepareStatement(sSql.toString());
				iParam = 1;
				if (!sSqlEntrada.equals("")) {
					ps.setDate(iParam++,Funcoes.dateToSQLDate(txtDataini.getVlrDate()));
					ps.setDate(iParam++,Funcoes.dateToSQLDate(txtDatafim.getVlrDate()));
					ps.setInt(iParam++,iCodEmp);
					ps.setInt(iParam++,ListaCampos.getMasterFilial("CPCOMPRA"));
				}
				if (!sSqlSaida.equals("")) {
					ps.setDate(iParam++,Funcoes.dateToSQLDate(txtDataini.getVlrDate()));
					ps.setDate(iParam++,Funcoes.dateToSQLDate(txtDatafim.getVlrDate()));
					ps.setInt(iParam++,iCodEmp);
					ps.setInt(iParam++,ListaCampos.getMasterFilial("VDVENDA"));
				}
				if (!sSqlConsumidor.equals("")) {
					ps.setDate(iParam++,Funcoes.dateToSQLDate(txtDataini.getVlrDate()));
					ps.setDate(iParam++,Funcoes.dateToSQLDate(txtDatafim.getVlrDate()));
					ps.setInt(iParam++,iCodEmp);
					ps.setInt(iParam++,ListaCampos.getMasterFilial("VDVENDA"));
				}
				rs = ps.executeQuery();
				lbAnd.setText("Gerando Tabela de Produtos de entradas e saídas...");
				while (rs.next()) {
					
					sBuffer.delete(0,sBuffer.length());
					
					btGerar.setEnabled(false);
					
					sBuffer.append("75");
					sBuffer.append(Funcoes.dataAAAAMMDD(txtDataini.getVlrDate()));
					sBuffer.append(Funcoes.dataAAAAMMDD(txtDatafim.getVlrDate()));
					sBuffer.append(Funcoes.adicionaEspacos(rs.getString((sUsaRefProd.equals("S")?COL_75_REFPROD:COL_75_CODPROD)),14));
					sBuffer.append(Funcoes.replicate(" ",8));
					sBuffer.append(Funcoes.adicionaEspacos(rs.getString(COL_75_DESCPROD),53));
					sBuffer.append(Funcoes.adicionaEspacos(rs.getString(COL_75_CODUNID),4));//97
					sBuffer.append(Funcoes.copy(rs.getString(COL_75_ORIGFISC).trim()+rs.getString(COL_75_CODTRATTRIB).trim(),3));
					sBuffer.append(Funcoes.transValor(rs.getString(COL_75_PERCIPI),4,2,true));
					sBuffer.append(Funcoes.transValor(rs.getString(COL_75_PERCICMS),4,2,true));
					sBuffer.append(Funcoes.strZero("0",4));
					sBuffer.append(Funcoes.transValor("0",14,2,true) + CR );
					
					gravaBuffer(sBuffer.toString());
					
					cont ++;
					
				}
				rs.close();
				ps.close();
				if (!con.getAutoCommit())
				con.commit();
				
			} 
			
		} catch ( Exception e ) {
			e.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao gerar registro 75!\n" + e.getMessage(), true, con, e );
		}
		
		return cont;
		
	}
	  
	private String retorna90(String sBufferAnt, String sTipo, int iTot) {
		
		StringBuffer sBuf = new StringBuffer();
		if ("".equals(sBufferAnt.trim())) {
			sBuf.append("90");
			sBuf.append(sCnpjEmp);
			sBuf.append(sInscEmp);
		}
		sBuf.append(sTipo);
		sBuf.append(Funcoes.strZero( String.valueOf(iTot), 8 ));
		
		return sBuf.toString();       
		
	}
	
	private void gravaBuffer(String sBuf) {
		
		try {
			fwSintegra.write(sBuf);
			fwSintegra.flush();
		} catch (IOException err) {
			Funcoes.mensagemErro(this, "Erro grando no arquivo: '"+sFileName+"\n"+err.getMessage(),true,con,err);
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
		fdSintegra = new FileDialog(Aplicativo.telaPrincipal,"Salvar sintegra",FileDialog.SAVE);
		fdSintegra.setFile("Receita.txt");
		fdSintegra.setVisible(true);
		if (fdSintegra.getFile() == null)
			return false;

		sFileName = fdSintegra.getDirectory()+fdSintegra.getFile();
		    
		return true;
		
	}
	  
	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == btGerar) {
			//iniGerar();
			gerar();
		}
	}
	
}
