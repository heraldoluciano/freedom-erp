/**
 * @version 20/05/2004 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)DLBuscaItVenda.java <BR>
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
 */

package org.freedom.modulos.std;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JPanelPad;
import javax.swing.JScrollPane;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.bmps.Icone;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Tabela;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FFDialogo;


public class DLBuscaItVenda extends FFDialogo implements ActionListener, CarregaListener {
  private JPanelPad pinCab = new JPanelPad(0,60);
  private JPanelPad pnCorpo = new JPanelPad(JPanelPad.TP_JPANEL,new BorderLayout());
  private JPanelPad pinRod = new JPanelPad(350,55);
  private JPanelPad pinBtSel = new JPanelPad(40,110);
  private JPanelPad pinBtSelVenda = new JPanelPad(40,110);
  private JPanelPad pnCli = new JPanelPad(JPanelPad.TP_JPANEL,new BorderLayout());
  private JPanelPad pnTabVenda = new JPanelPad(JPanelPad.TP_JPANEL,new BorderLayout());
  private JPanelPad pnCliTab = new JPanelPad(JPanelPad.TP_JPANEL,new BorderLayout());
  private JTextFieldPad txtCodVenda = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldFK txtDocVenda = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldPad txtTipoVenda = new JTextFieldPad(JTextFieldPad.TP_STRING,1,0);
  private JTextFieldFK txtDataVenda = new JTextFieldFK(JTextFieldPad.TP_DATE,10,0);
  private JTextFieldFK txtVlrLiqVenda = new JTextFieldFK(JTextFieldPad.TP_NUMERIC,18,2);
  private JTextFieldFK txtStatusVenda = new JTextFieldFK(JTextFieldPad.TP_STRING,2,0);
  private JTextFieldPad txtVlrProd = new JTextFieldPad(JTextFieldPad.TP_DECIMAL,15,2);
  private JTextFieldPad txtVlrDesc = new JTextFieldPad(JTextFieldPad.TP_DECIMAL,15,2);
  private JTextFieldPad txtVlrLiq = new JTextFieldPad(JTextFieldPad.TP_DECIMAL,15,2);
  private Tabela tab = new Tabela();
  private JScrollPane spnTab = new JScrollPane(tab);
  private Tabela tabVenda = new Tabela();
  private JScrollPane spnTabVenda = new JScrollPane(tabVenda);
  private JButton btExec = new JButton(Icone.novo("btExecuta.gif"));
  private JButton btTudoVenda = new JButton(Icone.novo("btTudo.gif"));
  private JButton btNadaVenda = new JButton(Icone.novo("btNada.gif"));
  private JButton btLimpa = new JButton(Icone.novo("btRetorno.gif"));
  private ListaCampos lcVenda = new ListaCampos(this,"");
  private Vector vTipoVenda = new Vector();
  private Vector vTipo = new Vector();
  public DLBuscaItVenda(Component cOrig) {
    super(cOrig);
// Monta a tela

    setTitulo("Busca itens de venda");
    setAtribos(25,10,700,440);
    
    pnCorpo.add(pnCli,BorderLayout.CENTER);
    pnCorpo.add(pinCab,BorderLayout.NORTH);
    setPanel(pnCorpo);

    lcVenda.add(new GuardaCampo(txtCodVenda, "CodVenda", "Cód.Venda", ListaCampos.DB_PK, true));
    lcVenda.add(new GuardaCampo(txtDocVenda, "DocVenda", "Doc.", ListaCampos.DB_SI, false));
    lcVenda.add(new GuardaCampo(txtDataVenda, "DtEmitVenda", "Data", ListaCampos.DB_SI, false));
    lcVenda.add(new GuardaCampo(txtVlrLiqVenda, "VlrLiqVenda", "V.Liq.", ListaCampos.DB_SI, false));
    lcVenda.add(new GuardaCampo(txtStatusVenda, "StatusVenda", "Status", ListaCampos.DB_SI, false));
    lcVenda.add(new GuardaCampo(txtTipoVenda, "TipoVenda", "Tipo.Venda", ListaCampos.DB_SI, false));
    lcVenda.montaSql(false, "VENDA", "VD");
    lcVenda.setReadOnly(true);
    lcVenda.setConexao(con);
    txtCodVenda.setTabelaExterna(lcVenda);
    txtCodVenda.setFK(true);
    txtCodVenda.setNomeCampo("CodVenda");
	    
	Vector vVals = new Vector();
	vVals.addElement("L");
	vVals.addElement("O");
	Vector vLabs = new Vector();
	vLabs.addElement("Cliente");
	vLabs.addElement("Conveniado");

    setPainel(pinCab);
    adic(new JLabelPad("Nº Pedido"),7,0,80,20);
    adic(txtCodVenda,7,20,80,20);
    adic(new JLabelPad("Doc."),90,0,77,20);
    adic(txtDocVenda,90,20,77,20);
    adic(new JLabelPad("Valor"),170,0,97,20);
    adic(txtVlrLiqVenda,170,20,97,20);
    adic(new JLabelPad("Emissão"),270,0,100,20);
    adic(txtDataVenda,270,20,100,20);

    pnRodape.add(pinRod,BorderLayout.WEST);

    pinRod.tiraBorda();
    pinRod.adic(new JLabelPad("Vlr. Bruto"),7,0,100,20);
	pinRod.adic(txtVlrProd,7,20,100,20);
	pinRod.adic(new JLabelPad("Vlr. Desc."),110,0,97,20);
	pinRod.adic(txtVlrDesc,110,20,97,20);
	pinRod.adic(new JLabelPad("Vlr. Liq."),210,0,97,20);
	pinRod.adic(txtVlrLiq,210,20,97,20);

    pnTabVenda.setPreferredSize(new Dimension(600,130));

    pnTabVenda.add(spnTabVenda, BorderLayout.CENTER);
    pnTabVenda.add(pinBtSelVenda, BorderLayout.EAST);

    pinBtSelVenda.adic(btTudoVenda,5,5,30,30);
    pinBtSelVenda.adic(btNadaVenda,5,38,30,30);
    pinBtSelVenda.adic(btExec,5,71,30,30);

    pnCliTab.add(spnTab, BorderLayout.CENTER);
    pnCliTab.add(pinBtSel,BorderLayout.EAST);
    
    pinBtSel.adic(btLimpa,5,5,30,30);
    
    pnCli.add(pnTabVenda, BorderLayout.NORTH);
    pnCli.add(pnCliTab, BorderLayout.CENTER);
    
	txtVlrProd.setAtivo(false);
	txtVlrDesc.setAtivo(false);
	txtVlrLiq.setAtivo(false);
	
//Seta os comentários    

    btExec.setToolTipText("Executar Montagem");
    btTudoVenda.setToolTipText("Selecionar Tudo");
    btNadaVenda.setToolTipText("Limpar Seleção");
    btLimpa.setToolTipText("Limpar seleção");
    

//Monta as tabelas

    tabVenda.adicColuna("S/N"); //0
    tabVenda.adicColuna("Qtd. Sel."); //1
    tabVenda.adicColuna("Qtd"); //2
    tabVenda.adicColuna("Pedido"); //3
    tabVenda.adicColuna("Item"); //4
    tabVenda.adicColuna("Cód. Prod."); //5
    tabVenda.adicColuna("Descrição"); //6
    tabVenda.adicColuna("Preço"); //7
	tabVenda.adicColuna("Valor Desc."); //8
	tabVenda.adicColuna("Valor Liq."); //9

    tabVenda.setTamColuna(30,0);
    tabVenda.setTamColuna(60,1);
	tabVenda.setTamColuna(60,2);
    tabVenda.setTamColuna(80,3);
    tabVenda.setTamColuna(35,4);
    tabVenda.setTamColuna(80,5);
    tabVenda.setTamColuna(200,6);
    tabVenda.setTamColuna(120,7);
	tabVenda.setTamColuna(120,8);
    
    tabVenda.setColunaEditavel(0,true);
    tabVenda.setColunaEditavel(1,true);

    tab.adicColuna("Qtd Sel.");
    tab.adicColuna("Pedido");
    tab.adicColuna("Ítem");
	tab.adicColuna("Cód. Prod.");
	tab.adicColuna("Descrição");
	tab.adicColuna("Preco.");
	tab.adicColuna("Valor. Desc.");
	tab.adicColuna("Valor. Liq.");

	tab.setTamColuna(60,0);
    tab.setTamColuna(80,1);
	tab.setTamColuna(35,2);
	tab.setTamColuna(80,3);
	tab.setTamColuna(160,4);
	tab.setTamColuna(60,5);
	tab.setTamColuna(100,6);
	tab.setTamColuna(100,7);
    
	tab.setColunaEditavel(0,true);
    
    lcVenda.addCarregaListener(this);
    
    btExec.addActionListener(this);
    btTudoVenda.addActionListener(this);
    btNadaVenda.addActionListener(this);
    btLimpa.addActionListener(this);

  }

  private void buscar() {
    tabVenda.limpa();
    vTipoVenda.clear();
    String sSQL = "SELECT IT.QTDITVENDA,(SELECT SUM(QTDDEV) FROM CPCOMPRAVENDA " +
    		      "WHERE TIPOVENDA=IT.TIPOVENDA AND CODVENDA=IT.CODVENDA AND CODITVENDA=IT.CODITVENDA "+
                  "AND CODEMP=IT.CODEMP AND CODFILIAL=IT.CODFILIAL),IT.CODVENDA,IT.CODITVENDA,IT.CODPROD," +
                  "P.DESCPROD,IT.PRECOITVENDA,IT.VLRDESCITVENDA,IT.VLRLIQITVENDA," +
                  "IT.TIPOVENDA FROM VDITVENDA IT, EQPRODUTO P WHERE" +
                  " P.CODPROD=IT.CODPROD AND P.CODFILIAL=IT.CODFILIALPD" +
                  " AND P.CODEMP=IT.CODEMPPD AND (IT.QTDDEVITVENDA < IT.QTDITVENDA " +
                  " OR IT.QTDDEVITVENDA IS NULL)" +
                  " AND IT.CODVENDA=? AND IT.CODFILIAL=? AND IT.CODEMP=?";
    try {
        PreparedStatement ps = con.prepareStatement(sSQL);
        ps.setInt(1,txtCodVenda.getVlrInteger().intValue());
        ps.setInt(2,lcVenda.getCodFilial());
        ps.setInt(3,Aplicativo.iCodEmp);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) { 
            Vector vVals = new Vector();
            vVals.addElement(new Boolean("true"));
            vVals.addElement(new Integer(rs.getInt(1)-rs.getInt(2)));
            vVals.addElement(new Integer(rs.getInt(1)-rs.getInt(2)));
            vVals.addElement(txtCodVenda.getVlrInteger());
            vVals.addElement(new Integer(rs.getInt("CodItVenda")));
            vVals.addElement(new Integer(rs.getInt("CodProd")));
            vVals.addElement(rs.getString("DescProd").trim());
            vVals.addElement(Funcoes.strDecimalToStrCurrencyd(2,rs.getString("PrecoItVenda") != null ? rs.getString("PrecoItVenda") : "0"));
            vVals.addElement(Funcoes.strDecimalToStrCurrencyd(2,rs.getString("VlrDescItVenda") != null ? rs.getString("VlrDescItVenda") : "0"));
            vVals.addElement(Funcoes.strDecimalToStrCurrencyd(2,rs.getString("VlrLiqItVenda") != null ? rs.getString("VlrLiqItVenda") : "0"));
            vTipoVenda.addElement(rs.getString("TipoVenda"));
            tabVenda.adicLinha(vVals);               
        }
    }
    catch(SQLException err) {
        Funcoes.mensagemErro(this,"Erro ao buscar ítems!\n"+err.getMessage());
        err.printStackTrace();
    }
  }

  private void carregar() {
	double dValProd = 0;
	double dValDesc = 0;
	double dValLiq = 0;
  	for (int i=0; i<tabVenda.getNumLinhas();i++) {
  		if (!((Boolean)tabVenda.getValor(i,0)).booleanValue())
  		  continue;
  		else if (((Integer)tabVenda.getValor(i,1)).intValue() >
  		         ((Integer)tabVenda.getValor(i,2)).intValue()) {
  		  Funcoes.mensagemInforma(this,"Informe uma quantidade menor que a venda!");
  		  break;
  		}
  		
		Vector vVals = new Vector();
		vVals.addElement(tabVenda.getValor(i,1));
        vVals.addElement(tabVenda.getValor(i,3));
		vVals.addElement(tabVenda.getValor(i,4));
        vVals.addElement(tabVenda.getValor(i,5));
        vVals.addElement(tabVenda.getValor(i,6));
        vVals.addElement(tabVenda.getValor(i,7));
        vVals.addElement(tabVenda.getValor(i,8));
        vVals.addElement(tabVenda.getValor(i,9));
		dValProd += Funcoes.strCurrencyToDouble(tabVenda.getValor(i,1).toString())*
                    Funcoes.strCurrencyToDouble(tabVenda.getValor(i,7).toString());
		dValDesc += Funcoes.strCurrencyToDouble(tabVenda.getValor(i,8).toString());
	    dValLiq += Funcoes.strCurrencyToDouble(tabVenda.getValor(i,9).toString());
        vTipo.add(vTipoVenda.elementAt(i));
				
		tab.adicLinha(vVals);				
  	}
    txtVlrProd.setVlrBigDecimal(new BigDecimal(dValProd));
    txtVlrDesc.setVlrBigDecimal(new BigDecimal(dValDesc));
    txtVlrLiq.setVlrBigDecimal(new BigDecimal(dValLiq));
  }
  public Vector getValores() {
     Vector vRet = new Vector();
     for (int i=0;i<tab.getNumLinhas();i++)
        vRet.add(new Object[] {
                        tab.getValor(i,0),
						tab.getValor(i,1),
						tab.getValor(i,2),
                        vTipo.elementAt(i)
                     }
                );
     return vRet;
  }
  
  private void carregaTudo(Tabela tb) {
    for (int i=0; i<tb.getNumLinhas(); i++) {
      tb.setValor(new Boolean(true),i,0);
    }
  }
  private void carregaNada(Tabela tb) {
    for (int i=0; i<tb.getNumLinhas(); i++) {
      tb.setValor(new Boolean(false),i,0);
    }
  }
  public void actionPerformed(ActionEvent evt) {
    if (evt.getSource() == btExec) {
      carregar();
    }
    else if (evt.getSource() == btTudoVenda) {
      carregaTudo(tabVenda);
    }
    else if (evt.getSource() == btNadaVenda) {
      carregaNada(tabVenda);
    }
    else if (evt.getSource() == btLimpa) {
      tab.limpa();
      vTipo.clear();
      txtVlrDesc.setVlrDouble(new Double(0));
      txtVlrLiq.setVlrDouble(new Double(0));
      txtVlrProd.setVlrDouble(new Double(0));
    }
    super.actionPerformed(evt);
  }
  public void setConexao(Connection cn) {
    super.setConexao(cn);
    lcVenda.setConexao(cn);
  }
  public void afterCarrega(CarregaEvent cevt) {
    if (cevt.getListaCampos() == lcVenda && cevt.ok)
        buscar();
  }
  public void beforeCarrega(CarregaEvent cevt) {
  }
}
