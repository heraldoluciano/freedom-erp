/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)DLConsultaVenda.java <BR>
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
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JLabel;
import javax.swing.JScrollPane;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Painel;
import org.freedom.componentes.Tabela;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FFDialogo;

public class DLConsultaVenda extends FFDialogo implements ActionListener {
  private Painel pinConsulta = new Painel(500,100);
  private JTextFieldPad txtCodVenda = new JTextFieldPad();
  private JTextFieldPad txtDocVenda = new JTextFieldPad();
  private JTextFieldPad txtVlrVenda = new JTextFieldPad();
  private JTextFieldPad txtDtEmitVenda = new JTextFieldPad();
  private JTextFieldPad txtDtSaida = new JTextFieldPad();
  private JTextFieldPad txtCodPlanoPag = new JTextFieldPad();
  private JTextFieldFK txtDescPlanoPag = new JTextFieldFK();
  private Tabela tabConsulta = new Tabela();
  private JScrollPane spnTab = new JScrollPane(tabConsulta);
  private Connection con = null;
  private ListaCampos lcPlanoPag = new ListaCampos(this);
  private ListaCampos lcVenda = new ListaCampos(this);
  public DLConsultaVenda(Component cOrig,Connection cn,int iCodVenda) {
  	super(cOrig);
    con = cn;
    txtCodVenda.setVlrString(""+iCodVenda);
    setTitulo("Consulta de Venda");
    setAtribos(120,140,500,300);
    
    setToFrameLayout();
    
    c.add(pinConsulta,BorderLayout.NORTH);
    c.add(spnTab,BorderLayout.CENTER);
    
    txtCodVenda.setAtivo(false);
    txtCodPlanoPag.setAtivo(false);
    txtVlrVenda.setAtivo(false);
    txtDocVenda.setAtivo(false);
    txtDtEmitVenda.setAtivo(false);
    txtDtSaida.setAtivo(false);

    
    
    pinConsulta.adic(new JLabel("Cod. Venda"),7,0,100,20);
    pinConsulta.adic(txtCodVenda,7,20,100,20);
    pinConsulta.adic(new JLabel("Doc.Venda"),110,0,200,20);
    pinConsulta.adic(txtDocVenda,110,20,100,20);
    
    pinConsulta.adic(new JLabel("Código e desc.do plano"),212,0,200,20);
    pinConsulta.adic(txtCodPlanoPag,212,20,77,20);
    pinConsulta.adic(txtDescPlanoPag,292,20,187,20);
    pinConsulta.adic(new JLabel("Data Emissão"),7,40,150,20);
    pinConsulta.adic(txtDtEmitVenda,7,60,110,20);
    pinConsulta.adic(new JLabel("Data Saida"),120,40,150,20);
    pinConsulta.adic(txtDtSaida,120,60,100,20);
    pinConsulta.adic(new JLabel("Valor Total"),223,40,100,20);
    pinConsulta.adic(txtVlrVenda,223,60,100,20);
    
    txtCodPlanoPag.setNomeCampo("CodPlanoPag");
    txtCodPlanoPag.setTipo(JTextFieldPad.TP_INTEGER,10,0);
    txtDescPlanoPag.setTipo(JTextFieldPad.TP_STRING,40,0);
    lcPlanoPag.add(new GuardaCampo( txtCodPlanoPag, 7, 100, 80, 20, "CodPlanoPag", "Código", true, false, null, JTextFieldPad.TP_INTEGER,false),"txtCodPlanoPagx");
    lcPlanoPag.add(new GuardaCampo( txtDescPlanoPag, 90, 100, 207, 20, "DescPlanoPag", "Descrição", false, false, null, JTextFieldPad.TP_STRING,false),"txtDescPlanoPagx");
    txtDescPlanoPag.setListaCampos(lcPlanoPag);
    lcPlanoPag.montaSql(false, "PLANOPAG", "FN");
    lcPlanoPag.setQueryCommit(false);
    lcPlanoPag.setReadOnly(true);
    lcPlanoPag.setConexao(con);

    txtCodVenda.setNomeCampo("CodVenda");
    txtCodVenda.setTipo(JTextFieldPad.TP_INTEGER,10,0);
    txtDocVenda.setTipo(JTextFieldPad.TP_INTEGER,10,0);
    txtVlrVenda.setTipo(JTextFieldPad.TP_DECIMAL,13,2);
    lcVenda.add(new GuardaCampo( txtCodVenda, 7, 100, 80, 20, "CodVenda", "Código", true, false, null, JTextFieldPad.TP_INTEGER,false),"txtCodVendax");
    lcVenda.add(new GuardaCampo(txtDocVenda,7,150,80,20, "DocVenda","Documento", false, false, null, JTextFieldPad.TP_INTEGER,false),"txtDocVenda");
    lcVenda.add(new GuardaCampo(txtDtEmitVenda,7,150,80,20, "DtEmitVenda","Data Emissão", false, false, null, JTextFieldPad.TP_DATE,false),"txtDtEmitVenda");    
    lcVenda.add(new GuardaCampo(txtDtSaida,7,150,80,20, "DtSaidaVenda","Data Saida", false, false, null, JTextFieldPad.TP_DATE,false),"txtDtSaida");        
    lcVenda.add(new GuardaCampo( txtCodPlanoPag, 90, 100, 207, 20, "CodPlanoPag", "Cód.Plano", false, true, null, JTextFieldPad.TP_INTEGER,false),"txtDescVendax");
    lcVenda.add(new GuardaCampo( txtVlrVenda, 90, 100, 207, 20, "VlrLiqVenda", "Valor", false, false, null, JTextFieldPad.TP_DECIMAL,false),"txtDescVendax");
    txtCodPlanoPag.setTabelaExterna(lcPlanoPag);
    txtCodPlanoPag.setListaCampos(lcVenda);
    txtVlrVenda.setListaCampos(lcVenda);
    lcVenda.montaSql(false, "VENDA", "VD");
    lcVenda.setReadOnly(true);
    lcVenda.setConexao(con);
    lcVenda.carregaDados();


    tabConsulta.adicColuna("Item");
    tabConsulta.adicColuna("Ref.Prod.");
    tabConsulta.adicColuna("Descrição");
    tabConsulta.adicColuna("Cod.Lote");
    tabConsulta.adicColuna("Quant.");
    tabConsulta.adicColuna("Preço");
    tabConsulta.adicColuna("Desc");
    tabConsulta.adicColuna("Vl.Liq");
    
    tabConsulta.setTamColuna(50,0);
    tabConsulta.setTamColuna(70,1);
    tabConsulta.setTamColuna(180,2);
    tabConsulta.setTamColuna(70,3);
    tabConsulta.setTamColuna(80,4);
    tabConsulta.setTamColuna(100,5);
    tabConsulta.setTamColuna(100,6);
    tabConsulta.setTamColuna(100,7);
    
    carregaGridConsulta();
  }
  private void carregaGridConsulta() {
    String sSQL = "SELECT IT.CODITVENDA, P.DESCPROD, IT.QTDITVENDA,"+
                  "IT.PRECOITVENDA,IT.VLRLIQITVENDA,IT.CODLOTE,IT.VLRDESCITVENDA, IT.REFPROD FROM VDITVENDA IT,"+
                  "EQPRODUTO P WHERE IT.CODVENDA = ? AND IT.CODEMP=? AND IT.CODFILIAL=? AND "+
                  "P.CODPROD = IT.CODPROD AND P.CODEMP=IT.CODEMPPD AND P.CODFILIAL=IT.CODFILIALPD ORDER BY CODITVENDA";
    try {
      PreparedStatement ps = con.prepareStatement(sSQL);
      ps.setInt(1,txtCodVenda.getVlrInteger().intValue());
      ps.setInt(2,Aplicativo.iCodEmp );
      ps.setInt(3,ListaCampos.getMasterFilial("VDITVENDA"));
      
      ResultSet rs = ps.executeQuery();
      for (int i=0; rs.next(); i++) {
        tabConsulta.adicLinha();
        tabConsulta.setValor(""+rs.getInt("CodItVenda"),i,0);
        tabConsulta.setValor(""+rs.getString("RefProd"),i,1);
        tabConsulta.setValor((rs.getString("DescProd") != null ? rs.getString("DescProd") : ""),i,2);
        tabConsulta.setValor((rs.getString("CodLote") != null ? rs.getString("CodLote") : ""),i,3);
        tabConsulta.setValor((rs.getString("QtdItVenda") != null ? rs.getString("QtdItVenda") : ""),i,4);
        tabConsulta.setValor(Funcoes.strDecimalToStrCurrency(13,2,rs.getString("PrecoItVenda")),i,5);
        tabConsulta.setValor(Funcoes.strDecimalToStrCurrency(13,2,rs.getString("VlrDescItVenda")),i,6);
        tabConsulta.setValor(Funcoes.strDecimalToStrCurrency(13,2,rs.getString("VlrLiqItVenda")),i,7);
        
        
      }
      rs.close();
      ps.close();
//      con.commit();
    }
    catch(SQLException err) {
		Funcoes.mensagemErro(this,"Erro ao montar a tabela de consulta!\n"+err.getMessage());
    }
  }
}

