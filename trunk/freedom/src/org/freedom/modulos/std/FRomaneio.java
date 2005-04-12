/**
 * @version 02/11/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FRomaneio.java <BR>
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.freedom.acao.InsertEvent;
import org.freedom.acao.InsertListener;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.JPanelPad;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FDetalhe;

public class FRomaneio extends FDetalhe implements InsertListener,ActionListener {
  private JPanelPad pinCab = new JPanelPad();
  private JPanelPad pinDet = new JPanelPad();
  private JTextFieldPad txtCodRoma = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtDataRoma = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
  private JTextFieldPad txtDtSaidaRoma = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
  private JTextFieldPad txtDtPrevRoma = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
  private JTextFieldPad txtDtEntregaRoma = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
  private JTextFieldPad txtStatusRoma = new JTextFieldPad(JTextFieldPad.TP_STRING,8,0);
  private JTextFieldPad txtCodItRoma = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtCodVenda = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtDtPrevItRoma = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
  private JTextFieldFK txtDescVenda = new JTextFieldFK(JTextFieldPad.TP_DECIMAL,15,2);
  private ListaCampos lcVenda = new ListaCampos(this,"VA");
  public FRomaneio() {
    setTitulo("Cadastro de Romaneio");
    setAtribos( 50, 20, 510, 350);
    setAltCab(90);
    pinCab = new JPanelPad(500,90);
    setListaCampos(lcCampos);
    setPainel( pinCab, pnCliCab);
    

    lcVenda.add(new GuardaCampo( txtCodVenda, "CodVenda", "Cód.venda", ListaCampos.DB_PK,true));
    lcVenda.add(new GuardaCampo( txtDescVenda, "VlrLiqVenda", "Valor da venda", ListaCampos.DB_SI,false));
    lcVenda.montaSql(false, "VENDA", "VD");
    lcVenda.setQueryCommit(false);
    lcVenda.setReadOnly(true);
    txtCodVenda.setTabelaExterna(lcVenda);
    txtDescVenda.setListaCampos(lcVenda);
    
    adicCampo(txtCodRoma, 7, 20, 80, 20,"CodRoma","Cód.roma.",ListaCampos.DB_PK,true);
    adicCampo(txtDataRoma, 90, 20, 97, 20,"DataRoma","Data",ListaCampos.DB_SI,true);
    adicCampo(txtDtSaidaRoma, 190, 20, 97, 20,"DtSaidaRoma","Data de sáida",ListaCampos.DB_SI,true);
    adicCampo(txtDtPrevRoma, 290, 20, 97, 20,"DtPrevRoma","Data prevista",ListaCampos.DB_SI,true);
    adicCampo(txtDtEntregaRoma, 390, 20, 97, 20,"DtEntregaRoma","Data de entrega",ListaCampos.DB_SI,false);
    adicCampoInvisivel(txtStatusRoma, "StatusRoma","Status",ListaCampos.DB_SI,false);
    setListaCampos( true, "ROMANEIO", "VD");
    lcCampos.setQueryInsert(false);
    setAltDet(60);
    pinDet = new JPanelPad(590,110);
    setPainel( pinDet, pnDet);
    setListaCampos(lcDet);
    setNavegador(navRod);

    adicCampo(txtCodItRoma, 7, 20, 50, 20,"CodItRoma","Item",ListaCampos.DB_PK,true);
    adicCampo(txtCodVenda, 60, 20, 77, 20,"CodVenda","Cód.venda",ListaCampos.DB_FK,true);
    adicDescFK(txtDescVenda, 140, 20, 147, 20, "VlrLiqVenda", "Valor da venda");
    adicCampo(txtDtPrevItRoma, 290, 20, 100, 20,"DtPrevItRoma","Data de previsão",ListaCampos.DB_SI,true);
    setListaCampos( true, "ITROMANEIO", "VD");
    lcCampos.setQueryInsert(false);
    montaTab();
    
    tab.setTamColuna(150,2);
    tab.setTamColuna(100,3);
    
    lcCampos.addInsertListener(this);
    btImp.addActionListener(this);
    btPrevimp.addActionListener(this);
  }
  public void actionPerformed(ActionEvent evt) {
    if (evt.getSource() == btPrevimp) {
        imprimir(true);
    }
    else if (evt.getSource() == btImp) 
      imprimir(false);
    super.actionPerformed(evt);
  }

  private void imprimir(boolean bVisualizar) {
    String sProd = "CODPROD";
    if (comRefProd()) {
      sProd = "REFPROD";
    }
    ImprimeOS imp = new ImprimeOS("",con);
    int linPag = imp.verifLinPag()-1;
    double dTotal = 0;
    
    imp.montaCab();
    imp.setTitulo("Relatório de Setores");
    imp.addSubTitulo("ROMANEIO No.: "+Funcoes.strZero(""+txtCodRoma.getVlrInteger().intValue(),8));
   
    DLRRomaneio dl = new DLRRomaneio();
    dl.setVisible(true);
    if (dl.OK == false) {
      dl.dispose();
      return;
    }
    String sSQL = "SELECT R.DATAROMA,P."+sProd+",P.DESCPROD,P.CODUNID,SUM(I.QTDITVENDA),\n"+
                  "SUM(I.VLRLIQITVENDA/I.QTDITVENDA),SUM(I.VLRLIQITVENDA) FROM VDROMANEIO R,\n"+
                  "VDITROMANEIO IR,VDVENDA V,VDITVENDA I,EQPRODUTO P WHERE R.CODROMA=?\n"+
                  " AND IR.CODROMA=R.CODROMA AND V.CODVENDA=IR.CODVENDA\n"+
                  " AND I.CODVENDA=V.CODVENDA AND P.CODPROD = I.CODPROD\n"+
                  " GROUP BY R.DATAROMA,P."+sProd+",P.DESCPROD,P.CODUNID\n"+
                  " ORDER BY P."+(dl.getValor().trim().equals("CODPROD") ? sProd : dl.getValor())+";";
    PreparedStatement ps = null;
    ResultSet rs = null;
    try {
      ps = con.prepareStatement(sSQL);
      ps.setInt(1,txtCodRoma.getVlrInteger().intValue());
      rs = ps.executeQuery();
      imp.limpaPags();
      while ( rs.next() ) {
         if (imp.pRow()==0) {         	
         	imp.impCab(136, false);
                        
            imp.say(imp.pRow()+1,0,""+imp.comprimido());
            imp.say(imp.pRow()+0,18,"Código");
            imp.say(imp.pRow()+0,33,"Descrição");
            imp.say(imp.pRow()+0,83,"Unid");
            imp.say(imp.pRow()+0,88,"Qtd");
            imp.say(imp.pRow()+0,96,"  Unitário");
            imp.say(imp.pRow()+0,107,"     Total");
            imp.say(imp.pRow()+1,0,""+imp.comprimido());
            imp.say(imp.pRow()+0,18,"======");
            imp.say(imp.pRow()+0,33,"=================================================");
            imp.say(imp.pRow()+0,83,"====");
            imp.say(imp.pRow()+0,88,"=====");
            imp.say(imp.pRow()+0,96,"==========");
            imp.say(imp.pRow()+0,107,"==========");
         }
         imp.say(imp.pRow()+1,0,""+imp.comprimido());
         imp.say(imp.pRow()+0,18,rs.getString(sProd));
         imp.say(imp.pRow()+0,33,rs.getString("DescProd"));
         imp.say(imp.pRow()+0,83,rs.getString("CodUnid").trim());
         imp.say(imp.pRow()+0,88,Funcoes.strDecimalToStrCurrency(5,1,rs.getString(5)));
         imp.say(imp.pRow()+0,96,Funcoes.strDecimalToStrCurrency(10,2,rs.getString(6)));
         imp.say(imp.pRow()+0,107,Funcoes.strDecimalToStrCurrency(10,2,rs.getString(7)));
         dTotal += rs.getDouble(7);
         if (imp.pRow()>=linPag-1) {
            imp.say(imp.pRow()+1,0,""+imp.comprimido());
            imp.say(imp.pRow()+0,18,Funcoes.replicate("=",100));
            imp.incPags();
            imp.eject();
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,18,"======");
           imp.say(imp.pRow()+0,33,"=================================================");
           imp.say(imp.pRow()+0,83,"====");
           imp.say(imp.pRow()+0,88,"=====");
           imp.say(imp.pRow()+0,96,"==========");
           imp.say(imp.pRow()+0,107,"==========");
         }
      }
      imp.say(imp.pRow()+1,0,""+imp.comprimido());
      imp.say(imp.pRow()+0,18,"======");
      imp.say(imp.pRow()+0,33,"=================================================");
      imp.say(imp.pRow()+0,83,"====");
      imp.say(imp.pRow()+0,88,"=====");
      imp.say(imp.pRow()+0,96,"==========");
      imp.say(imp.pRow()+0,107,"==========");
      imp.say(imp.pRow()+1,0,""+imp.comprimido());
      imp.say(imp.pRow()+0,88,"TOTAL ==>");
      imp.say(imp.pRow()+0,107,Funcoes.strDecimalToStrCurrency(10,2,""+dTotal));
      imp.say(imp.pRow()+1,0,""+imp.comprimido());
      imp.say(imp.pRow()+0,18,Funcoes.replicate("=",100));
      imp.eject();
      
      imp.fechaGravacao();
      
//      rs.close();
//      ps.close();
      if (!con.getAutoCommit())
      	con.commit();
      dl.dispose();
    }  
    catch ( SQLException err ) {
		Funcoes.mensagemErro(this,"Erro consulta do relatório\n!"+err.getMessage());      
    }
    
    if (bVisualizar) {
      imp.preview(this);
    }
    else {
      imp.print();
    }
  }
  private boolean comRefProd() {
    boolean bResultado = false;
    PreparedStatement ps = null;
    ResultSet rs = null;
    try {
      ps = con.prepareStatement("SELECT USAREFPROD FROM SGPREFERE1 WHERE CODEMP=? AND CODFILIAL=?");
	  ps.setInt(1,Aplicativo.iCodEmp);
	  ps.setInt(2,ListaCampos.getMasterFilial("SGPREFERE1"));
      rs = ps.executeQuery();
      if (rs.next()) {
        bResultado = rs.getString("UsaRefProd").trim().equals("S");
      }
//      rs.close();
//      ps.close();
      if (!con.getAutoCommit())
      	con.commit();
    }
    catch(SQLException err) {
		Funcoes.mensagemErro(this,"Erro ao consultar a tabela PREFERE1!\n"+err.getMessage());
    }
    return bResultado;
  }
  public void afterInsert(InsertEvent ievt) {
    txtStatusRoma.setVlrString("R1");
  }
  public void beforeInsert(InsertEvent ievt) { }
  public void setConexao(Connection cn) {
    super.setConexao(cn);
    lcVenda.setConexao(cn);
  }        
}
