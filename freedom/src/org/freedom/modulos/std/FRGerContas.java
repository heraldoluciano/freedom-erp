/**
 * @version 24/03/2004 <BR>
 * @author Setpoint Informática Ltda./Anderson Sanchez <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FRGerContas.java <BR>
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
 * Tela de opções para o relatório de gerenciamento de contas.
 * 
 */

package org.freedom.modulos.std;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Vector;

import org.freedom.componentes.JLabelPad;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.relatorios.GerContas;
import org.freedom.telas.FPrinterJob;
import org.freedom.telas.FRelatorio;

public class FRGerContas extends FRelatorio  {
  private JTextFieldPad txtDataini = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
  private JTextFieldPad txtDatafim = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
  private JTextFieldPad txtCodSetor = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldFK txtDescSetor = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
  private JTextFieldPad txtCodVend = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldFK txtNomeVend = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
  private JTextFieldPad txtCodCli=new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldFK txtRazCli = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);  
  private JCheckBoxPad cbVendas = new JCheckBoxPad("Só vendas?","S","N");
  private JCheckBoxPad cbCliPrinc = new JCheckBoxPad("Mostrar no cliente principal?","S","N");
  private JCheckBoxPad cbIncluiPed = new JCheckBoxPad("Incluir pedidos não faturados?","S","N");
  private JLabelPad lbCodSetor = new JLabelPad("Cód.setor");
  private JLabelPad lbDescSetor = new JLabelPad("Descrição do setor");
  private JLabelPad lbCodVend = new JLabelPad("Cód.comiss.");
  private JLabelPad lbDescVend = new JLabelPad("Nome do comissionado");
  private JLabelPad lbCliente = new JLabelPad("Cód.cli.");
  private JLabelPad lbRazCli = new JLabelPad("Razão social do cliete");
  private ListaCampos lcSetor = new ListaCampos(this);
  private ListaCampos lcVendedor = new ListaCampos(this);
  private ListaCampos lcCliente = new ListaCampos(this);
  private Vector vLabOrdemRel = new Vector();
  private Vector vValOrdemRel = new Vector();
  private JRadioGroup rgOrdemRel = null;
  private FPrinterJob dl = null;
  private Vector vParams = new Vector();

  public FRGerContas() {
    setTitulo("Gerenciamento de contas");
    setAtribos(80,0,425,310);

    GregorianCalendar cal = new GregorianCalendar();
    cal.add(Calendar.DATE,-30);
    txtDataini.setVlrDate(cal.getTime());
    cal.add(Calendar.DATE,30);
    txtDatafim.setVlrDate(cal.getTime());
    txtDataini.setRequerido(true);    
    txtDatafim.setRequerido(true);

    cbVendas.setVlrString("S");
    cbCliPrinc.setVlrString("S");
    cbIncluiPed.setVlrString("N");        
    
    vLabOrdemRel.addElement("Valor");
    vLabOrdemRel.addElement("Razão social");
    vLabOrdemRel.addElement("Cód.cli.");
    vLabOrdemRel.addElement("Cidade");
    vLabOrdemRel.addElement("Categoria");
    
    vValOrdemRel.addElement("VL");
    vValOrdemRel.addElement("RA");
    vValOrdemRel.addElement("CC");
    vValOrdemRel.addElement("CI");
    vValOrdemRel.addElement("CA");
        
    rgOrdemRel = new JRadioGroup(5,1,vLabOrdemRel,vValOrdemRel);
            
    lcSetor.add(new GuardaCampo( txtCodSetor, "CodSetor","Cód.setor", ListaCampos.DB_PK, false ));
    lcSetor.add(new GuardaCampo( txtDescSetor, "DescSetor","Descrição do setor", ListaCampos.DB_SI, false ));
    lcSetor.montaSql(false,"SETOR","VD");
    lcSetor.setReadOnly(true);
    txtCodSetor.setTabelaExterna(lcSetor);
    txtCodSetor.setFK(true);
    txtCodSetor.setNomeCampo("CodSetor");

    lcVendedor.add(new GuardaCampo( txtCodVend, "CodVend","Cód.comiss.", ListaCampos.DB_PK, false ));
    lcVendedor.add(new GuardaCampo( txtNomeVend, "NomeVend","Nome do comissionado", ListaCampos.DB_SI, false ));
    lcVendedor.montaSql(false,"VENDEDOR","VD");
    lcVendedor.setReadOnly(true);
    txtCodVend.setTabelaExterna(lcVendedor);
    txtCodVend.setFK(true);
    txtCodVend.setNomeCampo("CodVend");

	lcCliente.add(new GuardaCampo( txtCodCli, "CodCli", "Cód.cli.", ListaCampos.DB_PK, false));
	lcCliente.add(new GuardaCampo( txtRazCli, "RazCli", "Razão social do cliente", ListaCampos.DB_SI, false));
	txtCodCli.setTabelaExterna(lcCliente);
	txtCodCli.setNomeCampo("CodCli");
	txtCodCli.setFK(true);
	lcCliente.setReadOnly(true);
	lcCliente.montaSql(false, "CLIENTE", "VD");
    
    adic(new JLabelPad("Ordem"),280,0,80,20);
    adic(rgOrdemRel,280,20,120,120);
    adic(new JLabelPad("Período"),7,0,250,20);
    adic(txtDataini,7,20,100,20);
    adic(txtDatafim,110,20,100,20);    
    adic(lbCodSetor,7,45,250,20);
    adic(txtCodSetor,7,65,70,20);
    adic(lbDescSetor,80,45,250,20);
    adic(txtDescSetor,80,65,190,20);
    adic(lbCodVend,7,90,250,20);
    adic(txtCodVend,7,110,70,20);
    adic(lbDescVend,80,90,250,20);
    adic(txtNomeVend,80,110,190,20);
	adic(lbCliente,7,135,250,20);
	adic(txtCodCli,7,155,70,20);
	adic(lbRazCli,80,135,250,20);
	adic(txtRazCli,80,155,320,20);
    
    adic(cbVendas,7,180,100,25);
    adic(cbCliPrinc,110,180,250,25);
    adic(cbIncluiPed,7,205,295,25);
    
  }

  
  public void imprimir(boolean bVisualizar) {
	if (txtDataini.getVlrString().length() < 10 || txtDatafim.getVlrString().length() < 10) {
		Funcoes.mensagemInforma(this,"Período inválido!");
		return;
	}
	
    
	vParams.addElement(txtCodSetor.getVlrString());
	GerContas gerContas = new GerContas();
	gerContas.setParam(vParams);
	gerContas.setConexao(con);
	gerContas.setConsulta(buscaValores());	  

	dl = new FPrinterJob(gerContas,this);
	dl.setVisible(true);

	
  }

  private ResultSet buscaValores() {
  	String sSql = "";
  	String sWhere = "";
  	String sFrom = "";
    ResultSet rs = null;
  	try {
  		   sSql = "SELECT CLI.NOMECLI,CLI.CODCLI,CLI.CIDCLI,CC.DESCCLASCLI " +
  		   	      "FROM VDCLIENTE CLI,VDCLASCLI CC "+
                  "WHERE CC.CODEMP=CLI.CODEMPCC AND CC.CODFILIAL=CLI.CODFILIALCC AND CC.CODCLASCLI=CLI.CODCLASCLI ";

  		    PreparedStatement ps = null;
 			ps = con.prepareStatement(sSql);  		   
  		    rs = ps.executeQuery(sSql);
  			
  		}
  		catch (SQLException e) {
  			Funcoes.mensagemErro(this,"Erro executando a consulta.\n"+e.getMessage());
  			e.printStackTrace();
  		}
  	return rs;
  	}
  	  	
  
  public void setConexao(Connection cn) {
    super.setConexao(cn);
    lcSetor.setConexao(cn);
    lcVendedor.setConexao(cn);
    lcCliente.setConexao(cn);
  }
  
  
  
}
