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
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.Tabela;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FFDialogo;

public class DLConsultaVenda extends FFDialogo implements ActionListener {
  private JPanelPad pinConsulta = new JPanelPad(500,100);
  private JTextFieldPad txtCodVenda = new JTextFieldPad(JTextFieldPad.TP_INTEGER,10,0);
  private JTextFieldPad txtDocVenda = new JTextFieldPad(JTextFieldPad.TP_INTEGER,10,0);
  private JTextFieldPad txtVlrVenda = new JTextFieldPad(JTextFieldPad.TP_NUMERIC,15,2);
  private JTextFieldPad txtDtEmitVenda = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
  private JTextFieldPad txtDtSaida = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
  private JTextFieldPad txtCodPlanoPag = new JTextFieldPad(JTextFieldPad.TP_INTEGER,10,0);
  private JTextFieldFK txtDescPlanoPag = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
  private Tabela tabConsulta = new Tabela();
  private JScrollPane spnTab = new JScrollPane(tabConsulta);
  private ListaCampos lcPlanoPag = new ListaCampos(this);
  private ListaCampos lcVenda = new ListaCampos(this);
  public DLConsultaVenda(Component cOrig,Connection cn,int iCodVenda) {
  	super(cOrig);
    setConexao(cn);
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

    
    
    pinConsulta.adic(new JLabel("Nº pedido"),7,0,100,20);
    pinConsulta.adic(txtCodVenda,7,20,100,20);
    pinConsulta.adic(new JLabel("Doc.venda"),110,0,200,20);
    pinConsulta.adic(txtDocVenda,110,20,100,20);
    
    pinConsulta.adic(new JLabel("Cód.p.pag."),212,0,200,20);
    pinConsulta.adic(txtCodPlanoPag,212,20,77,20);
    pinConsulta.adic(new JLabel("Descrição do plano"),292,0,200,20);
    pinConsulta.adic(txtDescPlanoPag,292,20,187,20);
    pinConsulta.adic(new JLabel("Data emissão"),7,40,150,20);
    pinConsulta.adic(txtDtEmitVenda,7,60,110,20);
    pinConsulta.adic(new JLabel("Data saida"),120,40,150,20);
    pinConsulta.adic(txtDtSaida,120,60,100,20);
    pinConsulta.adic(new JLabel("Valor total"),223,40,100,20);
    pinConsulta.adic(txtVlrVenda,223,60,100,20);
    
    txtCodPlanoPag.setNomeCampo("CodPlanoPag");
    lcPlanoPag.add(new GuardaCampo( txtCodPlanoPag, "CodPlanoPag", "Cód.p.pag.", ListaCampos.DB_PK, false));
    lcPlanoPag.add(new GuardaCampo( txtDescPlanoPag, "DescPlanoPag", "Descrição do plano de pagamento", ListaCampos.DB_SI,false));
    txtDescPlanoPag.setListaCampos(lcPlanoPag);
    lcPlanoPag.montaSql(false, "PLANOPAG", "FN");
    lcPlanoPag.setQueryCommit(false);
    lcPlanoPag.setReadOnly(true);
    lcPlanoPag.setConexao(con);

    txtCodVenda.setNomeCampo("CodVenda");
    lcVenda.add(new GuardaCampo( txtCodVenda, "CodVenda", "Nº pedido", ListaCampos.DB_PK,false));
    lcVenda.add(new GuardaCampo(txtDocVenda,"DocVenda","Nº documento", ListaCampos.DB_SI,false));
    lcVenda.add(new GuardaCampo(txtDtEmitVenda,"DtEmitVenda","Dt.emissão", ListaCampos.DB_SI,false));    
    lcVenda.add(new GuardaCampo(txtDtSaida,"DtSaidaVenda","Dt.saída", ListaCampos.DB_SI,false));        
    lcVenda.add(new GuardaCampo( txtCodPlanoPag, "CodPlanoPag", "Cód.p.pag.", ListaCampos.DB_FK,false));
    lcVenda.add(new GuardaCampo( txtVlrVenda, "VlrLiqVenda", "Valor", ListaCampos.DB_SI,false));
    txtCodPlanoPag.setTabelaExterna(lcPlanoPag);
    txtCodPlanoPag.setListaCampos(lcVenda);
    txtVlrVenda.setListaCampos(lcVenda);
    lcVenda.montaSql(false, "VENDA", "VD");
    lcVenda.setReadOnly(true);
    lcVenda.setConexao(con);
    lcVenda.carregaDados();


    tabConsulta.adicColuna("Item");
    tabConsulta.adicColuna("Ref.prod.");
    tabConsulta.adicColuna("Descrição");
    tabConsulta.adicColuna("Cód.lote");
    tabConsulta.adicColuna("Quant.");
    tabConsulta.adicColuna("Preço");
    tabConsulta.adicColuna("Desc");
    tabConsulta.adicColuna("Vl.liq");
    
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

