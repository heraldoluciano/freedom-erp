/**
 * @version 08/04/2004 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)DLCosultaBaixa.java <BR>
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
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Painel;
import org.freedom.componentes.Tabela;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FFDialogo;
public class DLConsultaBaixa extends FFDialogo {
  private JTextFieldPad txtVlrParc = new JTextFieldPad(JTextFieldPad.TP_NUMERIC,9,2);
  private JTextFieldPad txtVlrJuros = new JTextFieldPad(JTextFieldPad.TP_NUMERIC,9,2);
  private JTextFieldPad txtVlrDesc = new JTextFieldPad(JTextFieldPad.TP_NUMERIC,9,2);
  private JTextFieldPad txtVlrPago = new JTextFieldPad(JTextFieldPad.TP_NUMERIC,9,2);
  private JTextFieldPad txtVlrAberto = new JTextFieldPad(JTextFieldPad.TP_NUMERIC,9,2);
  private Tabela tabConsulta = new Tabela();
  private JScrollPane spnTab = new JScrollPane(tabConsulta);
  private Connection con = null;
  private Painel pinConsulta = new Painel(0,60);
  public DLConsultaBaixa(Component cOrig,Connection cn,int iCodRec,int iNParc) {
  	super(cOrig);
    con = cn;
    setTitulo("Consulta de Baixas");
    setAtribos(100,100,520,300);
    
	setToFrameLayout();
    
    pnRodape.setPreferredSize(new Dimension(500,32));
    pnRodape.setBorder(BorderFactory.createEtchedBorder());
    c.add(pinConsulta,BorderLayout.NORTH);
    c.add(spnTab,BorderLayout.CENTER);
    
    txtVlrParc.setAtivo(false);
    txtVlrJuros.setAtivo(false);
    txtVlrDesc.setAtivo(false);
    txtVlrPago.setAtivo(false);
    txtVlrAberto.setAtivo(false);
    
    pinConsulta.adic(new JLabel("V.Parcela"),7,0,250,20);
    pinConsulta.adic(txtVlrParc,7,20,100,20);
    pinConsulta.adic(new JLabel("V.Juros"),110,0,110,20);
    pinConsulta.adic(txtVlrJuros,110,20,97,20);
    pinConsulta.adic(new JLabel("V.Desconto"),210,0,110,20);
    pinConsulta.adic(txtVlrDesc,210,20,97,20);
    pinConsulta.adic(new JLabel("V.Pago"),310,0,110,20);
    pinConsulta.adic(txtVlrPago,310,20,97,20);
    pinConsulta.adic(new JLabel("V.Aberto"),410,0,110,20);
    pinConsulta.adic(txtVlrAberto,410,20,97,20);
    
    tabConsulta.adicColuna("Data do pagto.");
    tabConsulta.adicColuna("Vlr. Pago.");
    tabConsulta.adicColuna("Obs:");
    
    tabConsulta.setTamColuna(100,0);
    tabConsulta.setTamColuna(100,1);
    tabConsulta.setTamColuna(310,2);

    carregaGridConsulta(iCodRec,iNParc);
  }
  private void carregaGridConsulta(int iCodRec, int iNParc) {
    String sSQL = "SELECT S.DATASUBLANCA,S.VLRSUBLANCA,S.HISTSUBLANCA " +
    		      "FROM FNSUBLANCA S, FNLANCA L WHERE S.CODLANCA=L.CODLANCA " +
    		      " AND S.CODEMP=L.CODEMP AND S.CODFILIAL=L.CODFILIAL AND " +
    		      " L.CODREC=? AND L.NPARCITREC=? AND L.CODEMP=? AND " +
    		      " L.CODFILIAL=? AND S.CODSUBLANCA=0 ORDER BY DATASUBLANCA";
    try {
      PreparedStatement ps = con.prepareStatement(sSQL);
      ps.setInt(1,iCodRec);
      ps.setInt(2,iNParc);
      ps.setInt(3,Aplicativo.iCodEmp);
      ps.setInt(4,ListaCampos.getMasterFilial("FNLANCA"));
      ResultSet rs = ps.executeQuery();
      for (int i=0; rs.next(); i++) {
        tabConsulta.adicLinha();
        tabConsulta.setValor(Funcoes.sqlDateToStrDate(rs.getDate("DataSubLanca")),i,0);
        tabConsulta.setValor(Funcoes.strDecimalToStrCurrency(2,rs.getString("VlrSubLanca")),i,1);
        tabConsulta.setValor(rs.getString("HistSubLanca"),i,2);
      }
      rs.close();
      ps.close();
//      con.commit();
    }
    catch(SQLException err) {
		Funcoes.mensagemErro(this,"Erro ao montar a tabela de consulta!\n"+err.getMessage());
    }
  }
  public void setValores(BigDecimal bigVals[]) {
    txtVlrParc.setVlrBigDecimal(bigVals[0]);
    txtVlrPago.setVlrBigDecimal(bigVals[1]);
    txtVlrDesc.setVlrBigDecimal(bigVals[2]);
    txtVlrJuros.setVlrBigDecimal(bigVals[3]);
    txtVlrAberto.setVlrBigDecimal(bigVals[4]);
  }
}