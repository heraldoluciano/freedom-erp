/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: projeto.freedomstd <BR>
 * Classe: @(#)DLBaixaComis.java <BR>
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
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JPanel;
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

public class DLBaixaComis extends FFDialogo {
  private JPanel pnCliente = new JPanel(new BorderLayout());
  private Painel pinCentro = new Painel(580,100);

  private JTextFieldPad txtData = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
  private JTextFieldPad txtCodPlan = new JTextFieldPad(JTextFieldPad.TP_STRING,13,0);
  private JTextFieldPad txtCodConta = new JTextFieldPad(JTextFieldPad.TP_STRING,10,0);
  private JTextFieldPad txtDoc = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtVlr = new JTextFieldPad(JTextFieldPad.TP_NUMERIC,15,2);
  private JTextFieldPad txtObs = new JTextFieldPad();
  private JTextFieldFK txtDescConta = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldFK txtDescPlan = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
  private Tabela tab = new Tabela();
  private JScrollPane spnTab = new JScrollPane(tab);
  private ListaCampos lcConta = new ListaCampos(this);
  private ListaCampos lcPlan = new ListaCampos(this);
  private Date dIni= null;
  private Date dFim = null;
  private Integer iCodVend = null;
  private String sEmitRel = "";
  public DLBaixaComis(Component cOrig,Connection cn,String sM,Date dI,Date dF, Integer iCodV) {
  	super(cOrig);
    sEmitRel = sM;
    setTitulo("Baixar");
    setAtribos(600,300);
    con = cn;
    dIni = dI;
    dFim = dF;
    iCodVend = iCodV;
    
    c.add(pnCliente,BorderLayout.CENTER);
    
    pnCliente.add(pinCentro,BorderLayout.SOUTH);
    pnCliente.add(spnTab,BorderLayout.CENTER);
    
    Funcoes.setBordReq(txtCodConta);
    Funcoes.setBordReq(txtCodPlan);
    Funcoes.setBordReq(txtData);
    Funcoes.setBordReq(txtDoc);
    Funcoes.setBordReq(txtVlr);
    
    lcConta.add(new GuardaCampo( txtCodConta, "NumConta", "Nº Conta", ListaCampos.DB_PK, false));
    lcConta.add(new GuardaCampo( txtDescConta, "DescConta", "Descrição", ListaCampos.DB_SI,false));
    lcConta.montaSql(false, "CONTA", "FN");
    lcConta.setReadOnly(true);
    txtCodConta.setTabelaExterna(lcConta);
    txtCodConta.setFK(true);
    txtCodConta.setNomeCampo("NumConta");

    lcPlan.add(new GuardaCampo( txtCodPlan, "CodPlan", "Cód.plan.", ListaCampos.DB_PK,false));
    lcPlan.add(new GuardaCampo( txtDescPlan, "DescPlan", "Descrição", ListaCampos.DB_SI,false));
    lcPlan.setWhereAdic("TIPOPLAN = 'D' AND NIVELPLAN = 6");
    lcPlan.montaSql(false, "PLANEJAMENTO", "FN");
    lcPlan.setReadOnly(true);
    txtCodPlan.setTabelaExterna(lcPlan);
    txtCodPlan.setFK(true);
    txtCodPlan.setNomeCampo("CodPlan");

    setPainel(pinCentro);
    
    adic(new JLabel("Código e descrição da conta"),7,0,250,20);
    adic(txtCodConta,7,20,80,20);
    adic(txtDescConta,90,20,197,20);
    adic(new JLabel("Código e descrição da categoria"),290,0,250,20);
    adic(txtCodPlan,290,20,77,20);
    adic(txtDescPlan,370,20,200,20);
    adic(new JLabel("Data"),7,40,100,20);
    adic(txtData,7,60,100,20);
    adic(new JLabel("Valor tot."),110,40,107,20);
    adic(txtVlr,110,60,107,20);
    adic(new JLabel("Doc"),220,40,77,20);
    adic(txtDoc,220,60,77,20);
    adic(new JLabel("Obs."),300,40,270,20);
    adic(txtObs,300,60,270,20);
    
    tab.adicColuna("Cliente");
    tab.adicColuna("Doc.");
    tab.adicColuna("Parc.");
    tab.adicColuna("Valor");
    tab.adicColuna("Emissão");
    tab.adicColuna("Vencimento");

    tab.setTamColuna(200,0);
    tab.setTamColuna(70,1);
    tab.setTamColuna(30,2);
    tab.setTamColuna(100,3);
    tab.setTamColuna(95,4);
    tab.setTamColuna(95,5);
    tab.setTamColuna(95,6);
    
    montaTabela();
    
    txtData.setVlrDate(new Date());
    txtObs.setVlrString("PAGAMENTO DE COMISSÕES AO VENDEDOR: "+iCodV);
    
  }
  private void montaTabela() {
    tab.limpa();
    BigDecimal bSum = new BigDecimal("0");
    String sSQL = "SELECT CL.RAZCLI,R.DOCREC,ITR.NPARCITREC,"+
       "C.VLRCOMI,C.DATACOMI,C.DTVENCCOMI FROM VDCOMISSAO C, VDCLIENTE CL,"+
       "FNRECEBER R, FNITRECEBER ITR WHERE C.CODEMP=? AND C.CODFILIAL=? AND "+
       "R.CODVEND = ? AND ITR.CODEMP=R.CODEMP AND "+
       " ITR.CODFILIAL=R.CODFILIAL AND ITR.CODREC =R.CODREC AND "+
       "C.CODEMPRC = ITR.CODEMP AND C.CODFILIALRC = ITR.CODFILIAL AND "+
       "C.CODREC = ITR.CODREC AND "+
       (sEmitRel.equals("E")?"C.DATACOMI":"C.DTVENCCOMI")+
       " BETWEEN ? AND ? AND CL.CODEMP=R.CODEMPCL AND CL.CODFILIAL=R.CODFILIALCL AND "+
       "CL.CODCLI=R.CODCLI AND C.STATUSCOMI = 'C2'"+
       "AND C.NPARCITREC = ITR.NPARCITREC ORDER BY C.DTVENCCOMI";  
    try {
      PreparedStatement ps = con.prepareStatement(sSQL);
      ps.setInt(1,Aplicativo.iCodEmp);
      ps.setInt(2,Aplicativo.iCodFilial);
      ps.setInt(3,iCodVend.intValue());
      ps.setDate(4,Funcoes.dateToSQLDate(dIni));
      ps.setDate(5,Funcoes.dateToSQLDate(dFim));
      ResultSet rs = ps.executeQuery();
      for (int i=0; rs.next(); i++) {
        tab.adicLinha();
        tab.setValor(rs.getString("RazCli"),i,0);
        tab.setValor(rs.getString("DocRec"),i,1);
        tab.setValor(rs.getString("NParcItRec"),i,2);
        tab.setValor(Funcoes.strDecimalToStrCurrency(10,2,rs.getString("VlrComi")),i,3);
        tab.setValor(Funcoes.dateToStrDate(rs.getDate("datacomi")),i,4);
        tab.setValor(Funcoes.dateToStrDate(rs.getDate("DtVencComi")),i,5);
        bSum = bSum.add(new BigDecimal(rs.getString("VlrComi")));
      }
      txtVlr.setVlrBigDecimal(bSum);
//      rs.close();
//      ps.close();
      if (!con.getAutoCommit())
      	con.commit();
    }
    catch (SQLException err) {
      Funcoes.mensagemErro(this,"Erro lendo ResultSet!\n"+err.getMessage());
    }
  }
  public void setConexao(Connection cn) {
    lcConta.setConexao(cn);
    lcPlan.setConexao(cn);
  }
  public String[] getValores() {
    String[] sRetorno = new String[6];
    sRetorno[0] = txtCodConta.getVlrString();
    sRetorno[1] = txtCodPlan.getVlrString();
    sRetorno[2] = txtData.getVlrString();
    sRetorno[3] = txtDoc.getVlrString();
    sRetorno[4] = txtVlr.getVlrString();
    sRetorno[5] = txtObs.getVlrString();
    return sRetorno;
  }
}
