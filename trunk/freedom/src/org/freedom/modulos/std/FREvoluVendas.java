/**
 * @version 08/12/2000 <BR>
 * @author Setpoint Informática Ltda./Anderson Sanchez <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FREvoluVendas.java <BR>
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
 * Tela de opções para o gráfico de evolução de vendas.
 * 
 */

package org.freedom.modulos.std;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Calendar;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import org.freedom.bmps.Icone;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.graficos.EvoluVendasBarras;
import org.freedom.graficos.EvoluVendasLinha;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FPrinterJob;
import org.freedom.telas.FRelatorio;

public class FREvoluVendas extends FRelatorio {
  private JTextFieldPad txtDataini = new JTextFieldPad(); 
  private JTextFieldPad txtDatafim = new JTextFieldPad(); 
  private Connection con = null;
  private FPrinterJob dl = null;
  private JRadioGroup rgGrafico = null;
  private Vector vLabs = new Vector(2);
  private Vector vVals = new Vector(2);
  private JTextFieldPad txtCodTipoCli = new JTextFieldPad(8);
  private JTextFieldFK  txtDescTipoCli = new JTextFieldFK();
  private ListaCampos lcTipoCli = new ListaCampos(this,"TI");
  private JTextFieldPad txtCodCli = new JTextFieldPad(8);
  private JTextFieldFK  txtRazCli = new JTextFieldFK();
  private ListaCampos lcCli = new ListaCampos(this,"TI");
  private JCheckBoxPad cbVendas = new JCheckBoxPad("Só vendas?","S","N");  

  public FREvoluVendas() {

    setTitulo("Evolução de vendas");   
    setAtribos(80,80,300,350);    
    
    txtDataini.setTipo(JTextFieldPad.TP_DATE,10,0);
    txtDatafim.setTipo(JTextFieldPad.TP_DATE,10,0);

	txtCodTipoCli.setTipo(JTextFieldPad.TP_INTEGER,8,0);
	txtDescTipoCli.setTipo(JTextFieldPad.TP_STRING,50,0);

	txtCodTipoCli.setTipo(JTextFieldPad.TP_INTEGER, 8, 0);
	txtDescTipoCli.setTipo(JTextFieldPad.TP_STRING, 40, 0);
	lcTipoCli.add(new GuardaCampo(txtCodTipoCli,7,100,80,20,"CodTipoCli","Código",true,false,null,JTextFieldPad.TP_INTEGER,false),"txtCodTipoCli");
	lcTipoCli.add(new GuardaCampo(txtDescTipoCli,90,100,207,20,"DescTipoCli","Descrição",false,false,null,JTextFieldPad.TP_STRING,false),"txtDescTipoCli");
	lcTipoCli.montaSql(false, "TIPOCLI", "VD");
	lcTipoCli.setQueryCommit(false);
	lcTipoCli.setReadOnly(true);
	txtCodTipoCli.setTabelaExterna(lcTipoCli);
	txtCodTipoCli.setFK(true);
	txtCodTipoCli.setNomeCampo("CodTipoCli");

	txtCodCli.setTipo(JTextFieldPad.TP_INTEGER,8,0);
	txtRazCli.setTipo(JTextFieldPad.TP_STRING,50,0);
	
	txtCodCli.setTipo(JTextFieldPad.TP_INTEGER, 8, 0);
	txtRazCli.setTipo(JTextFieldPad.TP_STRING, 40, 0);
	lcCli.add(new GuardaCampo(txtCodCli,7,100,80,20,"CodCli","Código",true,false,null,JTextFieldPad.TP_INTEGER,false),"txtCodCli");
	lcCli.add(new GuardaCampo(txtRazCli,90,100,207,20,"RazCli","Descrição",false,false,null,JTextFieldPad.TP_STRING,false),"txtRazCli");
	lcCli.montaSql(false, "CLIENTE", "VD");
	lcCli.setQueryCommit(false);
	lcCli.setReadOnly(true);
	txtCodCli.setTabelaExterna(lcCli);
	txtCodCli.setFK(true);
	txtCodCli.setNomeCampo("CodCli");

	Calendar cPeriodo = Calendar.getInstance();
	txtDatafim.setVlrDate(cPeriodo.getTime());
	cPeriodo.set(Calendar.DAY_OF_MONTH,1);
	cPeriodo.set(Calendar.MONTH,0);
	txtDataini.setVlrDate(cPeriodo.getTime());
	cPeriodo.set(Calendar.DAY_OF_MONTH,31);
	cPeriodo.set(Calendar.MONTH,11);
	txtDatafim.setVlrDate(cPeriodo.getTime());		

    adic(new JLabel("Periodo:"),7,5,120,20);
    adic(new JLabel("De:"),7,25,30,20);
    adic(txtDataini,40,25,97,20);
    adic(new JLabel("A:"),140,25,17,20);
    adic(txtDatafim,160,25,100,20);      
   
	adic(new JLabel("Código e Razão do cliente"),7,45,300,20);
	adic(txtCodCli, 7,65,50,20);
	adic(txtRazCli,60,65,200,20);

    adic(new JLabel("Código e descrição do tipo de cliente"),7,85,300,20);
    adic(txtCodTipoCli, 7,105,50,20);
    adic(txtDescTipoCli,60,105,200,20);
    
    cbVendas.setVlrString("S");
    adic(cbVendas,5,130,265,25);
	
	adic(new JLabel("Tipo de gráfico:"),7,160,220,20);
	adic(new JLabel(Icone.novo("graficoBarra.gif")),7,190,30,30);
	adic(new JLabel(Icone.novo("graficoLinha.gif")),7,230,30,30);
    
	vLabs.addElement("Barras 3D");
	vLabs.addElement("Linha horizontal");
	vVals.addElement("B");
	vVals.addElement("L");
	rgGrafico = new JRadioGroup(2,1,vLabs,vVals);
	rgGrafico.setVlrString("B");    
    rgGrafico.setBorder(BorderFactory.createEmptyBorder());
    adic(rgGrafico,42,193,200,82);

  }

  public void setConexao(Connection cn) {
    con = cn;
	lcTipoCli.setConexao(con);
	lcCli.setConexao(con);
  }

  private ResultSet buscaValores() {
	String sSQL = "SELECT * FROM VDEVOLUVENDAS(?,?,?,?,?,?,?)";
	PreparedStatement ps = null;
	ResultSet rs = null;
	java.sql.Date dataIni = Funcoes.dateToSQLDate(txtDataini.getVlrDate());
	java.sql.Date dataFim = Funcoes.dateToSQLDate(txtDatafim.getVlrDate());
	try {
	  ps = con.prepareStatement(sSQL);
	  ps.setInt(1,Aplicativo.iCodEmp);
	  ps.setInt(2,Aplicativo.iCodFilialMz);
	  ps.setDate(3,dataIni);
	  ps.setDate(4,dataFim);
	  if ((!txtCodTipoCli.getVlrString().equals("")) && (txtCodCli.getVlrString().equals(""))) {
        ps.setInt(5,Integer.parseInt(txtCodTipoCli.getVlrString()));    
        ps.setNull(6,Types.INTEGER);
	  }
	  else { 
        ps.setNull(5,Types.INTEGER);    	
        if ((txtCodTipoCli.getVlrString().equals("")) && (!txtCodCli.getVlrString().equals(""))) {
		  ps.setInt(6,Integer.parseInt(txtCodCli.getVlrString()));    	
	    }
	    else if ((!txtCodCli.getVlrString().equals("")) && (!txtCodTipoCli.getVlrString().equals(""))) {
		  ps.setInt(6,Integer.parseInt(txtCodCli.getVlrString()));
	  	  Funcoes.mensagemInforma(this,"O filtro por tipo de cliente será desconsiderado...");
	    }
	    else
          ps.setNull(6,Types.INTEGER);                   
      }
	  ps.setString(7,cbVendas.getVlrString());
	  	
	  rs = ps.executeQuery();
	}
	catch(SQLException err) {
		Funcoes.mensagemErro(this,"Erro ao buscar valores das vendas!\n"+err.getMessage());
	}
	return rs;
  }

  public void imprimir(boolean bVisualizar) {
	if (txtDatafim.getVlrDate().before(txtDataini.getVlrDate())) {
	  Funcoes.mensagemInforma(this,"Data final maior que a data inicial!");
	  return;
	}
	else if (Funcoes.contaMeses(txtDataini.getVlrDate(),txtDatafim.getVlrDate())<2) {
	  Funcoes.mensagemInforma(this,"Período inferior a 2 meses!");
	  return;	
	}
	
	
	try {
	  
	  if (rgGrafico.getVlrString().equals("B")) {
		EvoluVendasBarras evVendas = new EvoluVendasBarras();
		evVendas.setConexao(con);
		evVendas.setConsulta(buscaValores());	  	
		dl = new FPrinterJob(evVendas,this);
		dl.setVisible(true);
	  }	        
	  else if (rgGrafico.getVlrString().equals("L")) { 
		EvoluVendasLinha evVendas = new EvoluVendasLinha();
		evVendas.setConexao(con);
		evVendas.setConsulta(buscaValores());	  	
		dl = new FPrinterJob(evVendas,this);
		dl.setVisible(true);
	  }
	  
		  
	} 
	catch (Exception err) {
	  Funcoes.mensagemInforma(this,"Não foi possível carregar relatório!\n"+err.getMessage());
	  err.printStackTrace();
	}

  }
}
