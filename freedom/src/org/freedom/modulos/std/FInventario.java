/**
 * @version 11/02/2002 <BR>
 * @author Setpoint Informática Ltda./Robson Sanchez e Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FInventario.java <BR>
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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;

import javax.swing.JLabel;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.acao.CheckBoxEvent;
import org.freedom.acao.CheckBoxListener;
import org.freedom.acao.InsertEvent;
import org.freedom.acao.InsertListener;
import org.freedom.acao.PostEvent;
import org.freedom.acao.PostListener;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FDados;

public class FInventario extends FDados implements CarregaListener, InsertListener, PostListener, CheckBoxListener{
  
  private JTextFieldPad txtCodInv = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtCodProd = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtRefProd = new JTextFieldPad(JTextFieldPad.TP_STRING,13,0);
  private JTextFieldPad txtRefProd2 = new JTextFieldPad(JTextFieldPad.TP_STRING,13,0);
  private JTextFieldPad txtCodAlmox = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtCodLote = new JTextFieldPad(JTextFieldPad.TP_STRING,13,0);
  private JTextFieldPad txtDataInvP = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
  private JTextFieldPad txtQtdInvP = new JTextFieldPad(JTextFieldPad.TP_DECIMAL,15,2);
  private JTextFieldPad txtPrecoInvP = new JTextFieldPad(JTextFieldPad.TP_DECIMAL,15,2);
  private JTextFieldPad txtSldAtualInvP = new JTextFieldPad(JTextFieldPad.TP_DECIMAL,15,2);
  private JTextFieldPad txtSldNovoInvP = new JTextFieldPad(JTextFieldPad.TP_DECIMAL,15,2);
  private JTextFieldPad txtCodTipoMov = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldFK txtDescProd = new JTextFieldFK(JTextFieldPad.TP_STRING, 40, 0);
  private JTextFieldFK txtDescLote = new JTextFieldFK(JTextFieldPad.TP_DATE, 10, 0);
  private JTextFieldFK txtDescAlmox = new JTextFieldFK(JTextFieldPad.TP_STRING, 10, 0);
  private JTextFieldFK txtDescTipoMov = new JTextFieldFK(JTextFieldPad.TP_STRING, 40, 0);
  
  private ListaCampos lcProd = new ListaCampos(this,"PD");
  private ListaCampos lcProd2 = new ListaCampos(this,"PD");
  private ListaCampos lcLote = new ListaCampos(this,"LE");
  private ListaCampos lcAlmox = new ListaCampos(this,"AX");
  private ListaCampos lcTipoMov = new ListaCampos(this,"TM");
  private JCheckBoxPad cbLote = new JCheckBoxPad("Lote","S","N");
  private Connection con = null;
  private int iPrefs[] = {0,0,0};
  boolean bLote = false;
  public FInventario () {
    setTitulo("Inventário");
    setAtribos( 50, 50, 330, 370);

    cbLote.addCheckBoxListener(this);

    txtQtdInvP.setAtivo(false);
    txtSldAtualInvP.setAtivo(false);

    lcProd.add(new GuardaCampo( txtCodProd, "CodProd", "Cód.prod.", ListaCampos.DB_PK, txtDescProd, true));
    lcProd.add(new GuardaCampo( txtRefProd, "RefProd", "Referência do produto", ListaCampos.DB_SI, false));
    lcProd.add(new GuardaCampo( txtDescProd, "DescProd", "Descrição do produto", ListaCampos.DB_SI, false));
    lcProd.add(new GuardaCampo( cbLote, "CLoteProd", "Classifica por lote?", ListaCampos.DB_SI,false));
    lcProd.setWhereAdic("NOT TIPOPROD = 'S' AND ATIVOPROD='S'");
    lcProd.montaSql(false, "PRODUTO", "EQ");
    lcProd.setQueryCommit(false);
    lcProd.setReadOnly(true);
    txtCodProd.setTabelaExterna(lcProd);

    lcProd2.add(new GuardaCampo( txtCodProd, "CodProd", "Cód.prod.", ListaCampos.DB_FK, false));
    lcProd2.add(new GuardaCampo( txtRefProd, "RefProd", "Referência do produto", ListaCampos.DB_PK, txtDescProd, false));
    lcProd2.add(new GuardaCampo( txtDescProd, "DescProd", "Descrição do produto", ListaCampos.DB_SI, false));
    lcProd2.add(new GuardaCampo( cbLote, "CLoteProd", "Classifica por lote?", ListaCampos.DB_SI, false));
    txtRefProd.setChave(ListaCampos.DB_PK);
    txtRefProd.setNomeCampo("RefProd");
    txtRefProd.setListaCampos(lcProd2);
    lcProd2.setWhereAdic("NOT TIPOPROD = 'S' AND ATIVOPROD='S'");
    lcProd2.montaSql(false, "PRODUTO", "EQ");    
    lcProd2.setQueryCommit(false);
    lcProd2.setReadOnly(true);

    lcTipoMov.add(new GuardaCampo( txtCodTipoMov, "CodTipoMov", "Cód.tp.mov.", ListaCampos.DB_PK, false));
    lcTipoMov.add(new GuardaCampo( txtDescTipoMov, "DescTipoMov", "Descrição do tipo de movimento", ListaCampos.DB_SI, false));
    lcTipoMov.setWhereAdic(" ESTIPOMOV = 'I' "); 
    lcTipoMov.montaSql(false, "TIPOMOV", "EQ");    
    lcTipoMov.setQueryCommit(false); 
    lcTipoMov.setReadOnly(true); 
    txtCodTipoMov.setTabelaExterna(lcTipoMov);
    
    txtCodLote.setAtivo(false);
    lcLote.add(new GuardaCampo( txtCodLote, "CodLote", "Cód.lote", ListaCampos.DB_PK, false));
    lcLote.add(new GuardaCampo( txtDescLote, "VenctoLote", "Vencimento do lote", ListaCampos.DB_SI, false));
    //lcLote.add(new GuardaCampo( txtSldAtualInvP, 90, 100, 207, 20, "SldLiqLote", "Saldo", false, false, null, JTextFieldPad.TP_DECIMAL,false),"txtDescLotex");
    lcLote.setDinWhereAdic("CODPROD=#N",txtCodProd);
    lcLote.montaSql(false, "LOTE", "EQ");    
    lcLote.setQueryCommit(false);
    lcLote.setReadOnly(true);
    lcLote.setAutoLimpaPK(false);
    txtCodLote.setTabelaExterna(lcLote);

    lcAlmox.add(new GuardaCampo( txtCodAlmox, "CodAlmox", "Cód.almox.", ListaCampos.DB_PK, false));
    lcAlmox.add(new GuardaCampo( txtDescAlmox, "DescAlmox", "Descrição do almoxarifado", ListaCampos.DB_SI, false));
    lcAlmox.montaSql(false, "ALMOX", "EQ");    
    lcAlmox.setQueryCommit(false);
    lcAlmox.setReadOnly(true);
    txtCodAlmox.setTabelaExterna(lcAlmox);

    lcCampos.addInsertListener(this);
    lcCampos.addCarregaListener(this);
    lcProd.addCarregaListener(this);
    lcProd2.addCarregaListener(this);
    lcLote.addCarregaListener(this);
    lcCampos.addPostListener(this);
    btImp.addActionListener(this);
    btPrevimp.addActionListener(this);
    
	txtRefProd.addKeyListener(
	  new KeyAdapter() {
		public void keyPressed(KeyEvent kevt) {
		  lcCampos.edit();
		}
	  }
	);
    
  }
  private void montaTela() {
    adicCampo(txtCodInv, 7, 20, 90, 20,"CodInvProd","Cód.inv.prod.", ListaCampos.DB_PK,true);
    adicCampo(txtDataInvP, 100, 20, 100, 20,"DataInvP","Data", ListaCampos.DB_SI, true);
    
    adicCampo(txtCodTipoMov, 7, 60, 90, 20,"CodTipoMov","Cód.tp.mov.", ListaCampos.DB_FK, txtDescTipoMov, true);
    adicDescFK(txtDescTipoMov, 100, 60, 207, 20, "DescTipoMov", "Descrição do tipo de movimento");
    
    if (comRef()) {
      adicCampoInvisivel(txtRefProd2,"RefProd","Referência do produto", ListaCampos.DB_SI, false);
//      adicCampoInvisivel(txtRefProd,"RefProd","Referência",txtRefProd.TP_STRING,13,0,false,true,null,true);
      adicCampoInvisivel(txtCodProd,"CodProd","Cód.prod.", ListaCampos.DB_FK, txtDescProd, false);
      txtRefProd.setRequerido(true);
      adic(new JLabel("Referência"), 7, 80, 80, 20);
      adic(txtRefProd, 7, 100, 80, 20);
    }
    else {
      adicCampo(txtCodProd, 7, 100, 90, 20,"CodProd","Cód.prod", ListaCampos.DB_FK, txtDescProd, true);
    }
    adicDescFK(txtDescProd, 100, 100, 207, 20, "DescProd", "Descrição do produto");
    adicCampo(txtCodLote, 7, 140, 90, 20,"CodLote","Cód.lote", ListaCampos.DB_FK, txtDescLote, false);
    adicDescFK(txtDescLote, 100, 140, 207, 20, "VenctoLote", "Vencimento do lote");
    adicCampo(txtCodAlmox, 7, 180, 90, 20,"CodAlmox","Cód.amox.", ListaCampos.DB_FK, txtDescAlmox, true);
    adicDescFK(txtDescAlmox, 100, 180, 207, 20, "DescAlmox", "Descrição do almoxarifado");
    adicCampo(txtSldAtualInvP, 7, 220, 140, 20,"SldAtualInvP","Estoque atual", ListaCampos.DB_SI, false);
    adicCampo(txtSldNovoInvP, 150, 220, 137, 20,"SldDigInvP","Estoque novo", ListaCampos.DB_SI, false);
    adicCampo(txtPrecoInvP, 90, 260, 97, 20,"PrecoInvP","Custo unitário", ListaCampos.DB_SI, true);
    adicCampo(txtQtdInvP, 7, 260, 80, 20,"QtdInvP","Quantidade", ListaCampos.DB_SI, true);
    lcCampos.setQueryInsert(false);
    setListaCampos( true, "INVPROD", "EQ");
  }
  public boolean testaCodLote() {
    boolean bRetorno = false;
    boolean bValido = true;
    if (txtCodLote.getText().trim().length() > 0) {
      bValido = false;
      String sSQL = "SELECT COUNT(*) FROM EQLOTE WHERE CODLOTE=? AND CODPROD=?" +
      		" AND CODEMP=? AND CODFILIAL=?";
      PreparedStatement ps = null;
      ResultSet rs = null;
      try {
        ps = con.prepareStatement(sSQL);
        ps.setString(1,txtCodLote.getText().trim());
        ps.setInt(2,txtCodProd.getVlrInteger().intValue());
        ps.setInt(3,Aplicativo.iCodEmp);
        ps.setInt(4,lcLote.getCodFilial());
        rs = ps.executeQuery();
        if (rs.next()) {
          if (rs.getInt(1) > 0) {
            bValido = true;
          }
        }
        rs.close();
        ps.close();
        if (!con.getAutoCommit())
        	con.commit();
      }
      catch (SQLException err) {
		Funcoes.mensagemErro(this,"Erro ao consultar a tabela EQLOTE!\n"+err.getMessage()); 
      }
    }
    if (!bValido) {
      DLLote dl = new DLLote(this,txtCodLote.getText(),txtCodProd.getText(),txtDescProd.getText(),con);
      dl.setVisible(true);
      if (dl.OK) {
        bRetorno = true;
        dl.dispose();
      }
      else {
        dl.dispose();
      }
    }
    else {
      bRetorno = true;
    }
    return bRetorno;
  }
  private void testaCodInvProd() {
    PreparedStatement ps = null;
    ResultSet rs = null;
    try {
	  ps = con.prepareStatement("SELECT * FROM SPGERANUM(?,?,?)");
	  ps.setInt(1,Aplicativo.iCodEmp);
	  ps.setInt(2,ListaCampos.getMasterFilial("EQINVPROD"));
	  ps.setString(3,"IV");
      rs = ps.executeQuery();
      rs.next();
      txtCodInv.setVlrString(rs.getString(1));
//      rs.close();
//      ps.close();
      if (!con.getAutoCommit())
      	con.commit();
    }
    catch (SQLException err) {
		Funcoes.mensagemErro(this,"Erro ao confirmar código do inventário!\n"+err.getMessage());
    }
  }
  
  private int[] prefs() {
      int iRetorno[] = {0,0,0};
      String sSQL = null;
      PreparedStatement ps = null;
      ResultSet rs = null;
      try {
          sSQL = "SELECT USAREFPROD, CODTIPOMOV6, TIPOPRECOCUSTO FROM SGPREFERE1 WHERE CODEMP=? AND CODFILIAL=?";
          ps = con.prepareStatement(sSQL);
          ps.setInt(1,Aplicativo.iCodEmp);
    	  ps.setInt(2,ListaCampos.getMasterFilial("SGPREFERE1"));
          rs = ps.executeQuery();
          if (rs.next()) {
            if (rs.getString("UsaRefProd")!=null) {
               if (rs.getString("UsaRefProd").trim().equals("S"))
                   iRetorno[0] = 1;
            }
            if (rs.getString("CODTIPOMOV6")!=null)
                iRetorno[1] = rs.getInt("CODTIPOMOV6");
            if (rs.getString("TIPOPRECOCUSTO")!=null) {
                if (!rs.getString("TIPOPRECOCUSTO").equals("M")) 
                    iRetorno[2] = 1;
            }
          }
          rs.close();
          ps.close();
          if (!con.getAutoCommit())
          	con.commit();
      }
      catch (SQLException e) {
          Funcoes.mensagemErro(this, "Erro carregando preferências!\n"+e.getMessage());
      }
      finally {
          rs = null;
          ps = null;
          sSQL = null;
      }
      return iRetorno;
  }
  private boolean comRef() {
    return ( iPrefs[0] == 1 );
  }
  public void execShow(Connection cn) {
    con = cn;
    lcProd.setConexao(cn);
    lcProd2.setConexao(cn);
    lcLote.setConexao(cn);
    lcAlmox.setConexao(cn);
    lcTipoMov.setConexao(cn);
    iPrefs = prefs();
    montaTela();
    tipoCusto();
    super.execShow(cn);
  }
  public void keyPressed(KeyEvent kevt) {
    if (kevt.getKeyCode() == KeyEvent.VK_ENTER) {
   	  if (kevt.getSource() == txtPrecoInvP) {
        if (lcCampos.getStatus() == ListaCampos.LCS_INSERT) {
		  lcCampos.post();
		  lcCampos.limpaCampos(true);
          lcCampos.setState(ListaCampos.LCS_NONE);
          lcCampos.insert(true);
          txtRefProd.requestFocus();
        }
      }
   	  else if (kevt.getSource() == txtSldNovoInvP) {
        BigDecimal bSAtual = txtSldAtualInvP.getVlrBigDecimal();
        BigDecimal bSNovo = txtSldNovoInvP.getVlrBigDecimal();
        txtSldAtualInvP.setVlrBigDecimal(bSAtual); //Soh para zerar o campo caso ele estaja vazio.
        txtSldNovoInvP.setVlrBigDecimal(bSNovo); //Soh para zerar o campo caso ele estaja vazio.
        txtQtdInvP.setVlrBigDecimal(bSNovo.subtract(bSAtual));
   	  }
    }
    super.keyPressed(kevt);
  }
  public void valorAlterado(CheckBoxEvent cbevt) {
    if (cbLote.getStatus()) {
      txtCodLote.setAtivo(true);
      txtCodLote.setRequerido(true);
      bLote = true;
    }
    else {
      txtCodLote.setAtivo(false);
      txtCodLote.setRequerido(false);
      bLote = false;
    }
  }
  public void afterCarrega(CarregaEvent cevt) {
    if (cevt.getListaCampos() == lcCampos) {
      if (cevt.ok) {
        txtCodProd.setAtivo(false);
        txtRefProd.setAtivo(false);
        txtDataInvP.setAtivo(false);
        txtCodLote.setAtivo(false);
      }
    }
    else if (cevt.getListaCampos() == lcLote) {
      if (!bLote) 
          setSaldo();
    }
    else if ( (cevt.getListaCampos() == lcProd) || 
              (cevt.getListaCampos() == lcProd) ) {
        setSaldo();
    }
  }
  
  public void setSaldo() {
      double deSaldo[] = {0,0}; 
      deSaldo = buscaSaldo(txtCodProd.getVlrInteger().intValue(), 
           txtDataInvP.getVlrDate());  
      txtSldAtualInvP.setVlrDouble(new Double(deSaldo[0]));
      txtPrecoInvP.setVlrDouble(new Double(deSaldo[1]));
  }
  
  public void beforeInsert(InsertEvent ievt) {
    txtCodProd.setAtivo(true);
    txtRefProd.setAtivo(true);
    txtDataInvP.setAtivo(true);
    txtCodLote.setAtivo(true);
  }
  public void beforePost(PostEvent pevt) {
    BigDecimal bSAtual = txtSldAtualInvP.getVlrBigDecimal();
    BigDecimal bSNovo = txtSldNovoInvP.getVlrBigDecimal();
    txtSldAtualInvP.setVlrBigDecimal(bSAtual); //Soh para zerar o campo caso ele estaja vazio.
    txtSldNovoInvP.setVlrBigDecimal(bSNovo); //Soh para zerar o campo caso ele estaja vazio.
    txtQtdInvP.setVlrBigDecimal(bSNovo.subtract(bSAtual));
    if (bLote) {
      if (!testaCodLote()) {
        pevt.cancela();
      }
    }
    if (lcCampos.getStatus() == ListaCampos.LCS_INSERT)
      testaCodInvProd();
  }
  public void actionPerformed(ActionEvent evt) {
    if (evt.getSource() == btPrevimp) {
        imprimir(true);
    }
    else if (evt.getSource() == btImp) 
      imprimir(false);
    super.actionPerformed(evt);
  }

  private double[] buscaSaldo(int iCodprod, Date deFim) {
    // Método que busca saldo através da stored procedure EQMOVPRODSLDSP
    double deRetorno[] = {0,0};
    String sSQL = null; 
    PreparedStatement ps = null;
    ResultSet rs = null;
    try {
      sSQL = "SELECT NSALDO,NCUSTOMPM FROM EQMOVPRODSLDSP(?,?,?,?,?,?,?,?)";
       /* Parâmetros ICODEMP, SCODFILIAL, ICODMOVPROD, 
        * ICODEMPPD, SCODFILIALPD, ICODPROD, DDTMOVPROD, NCUSTOMPMMOVPROD
        */
      ps = con.prepareStatement(sSQL);
      ps.setNull(1,Types.INTEGER);
      ps.setNull(2,Types.INTEGER);
      ps.setNull(3,Types.INTEGER);
      ps.setInt(4,Aplicativo.iCodEmp);
      ps.setInt(5,ListaCampos.getMasterFilial("EQPRODUTO"));
      ps.setInt(6,iCodprod);
      ps.setDate(7,Funcoes.dateToSQLDate(deFim));
      ps.setDouble(8,0);
      rs = ps.executeQuery();
      if (rs.next()) {
        deRetorno[0] = rs.getDouble("NSALDO");
        deRetorno[1] = rs.getDouble("NCUSTOMPM");
      }
      rs.close();
      ps.close();
      if (!con.getAutoCommit())
        con.commit();
    }
    catch(SQLException err) {
      Funcoes.mensagemErro(this,"Erro ao buscar o saldo!!\n"+err.getMessage());
      err.printStackTrace();
    }
    finally {
       sSQL = null;
       rs = null;
       ps = null;
    }
    return deRetorno;
  }
  
  public void afterInsert(InsertEvent ievt) { 
	txtDataInvP.setVlrDate(new Date());
    txtCodTipoMov.setVlrInteger(new Integer(iPrefs[1]));
    lcTipoMov.carregaDados();
  }
  public void afterPost(PostEvent pevt) { }
  public void beforeCarrega(CarregaEvent cevt) { 
  }
  private String tipoCusto() {
    return (iPrefs[2]==0?"M":"P");
  }

  private void imprimir(boolean bVisualizar) {
      
    ImprimeOS imp = null;
    String sOrdem = null;
    String sOrdenado = null;
    String sRefCod = null;
    String sOrdemGrupo = null;
    String sDivGrupo = null;
    String sCodgrup = null;
    String sCodgrupFiltro = null;
    String sSQL = null;
    double deTotal = 0;
    Date dtEstoq = null;
    Object[] oVals = null;
    DLRInventario dl = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    
    
    try {

       dl = new DLRInventario(con,this);
       dl.setVisible(true);
       if (dl.OK == false) {
       	  dl.dispose();
       	  return;
       }
       
       imp = new ImprimeOS("",con);
       int linPag = imp.verifLinPag()-1;
       imp.setTitulo("Relatório de Inventário");

       oVals = dl.getValores();
       dl.dispose();

       sCodgrupFiltro = (String) oVals[1];
       sDivGrupo = (String) oVals[2];
       dtEstoq = (Date) oVals[3];
       
       if (sDivGrupo.equals("S")) 
          sOrdemGrupo = "P.CODGRUP,";
       else 
          sOrdemGrupo = "";
            
       if (comRef()) {
          sRefCod = "Ref";
          if (oVals[0].equals("CODPROD")) {
              sOrdem = sOrdemGrupo+"P.REFPROD";
              sOrdenado = "Referência";
          }
          else {
              sOrdenado = "Descrição";
              sOrdem = sOrdemGrupo+"P.DESCPROD";
          }
       }
       else {
          sRefCod = "Cod";
          if (oVals[0].equals("CODPROD")) {
              sOrdem = sOrdemGrupo+"P.CODPROD";
              sOrdenado = "Código";
          }
          else {
              sOrdenado = "Descrição";
              sOrdem = sOrdemGrupo+"P.DESQPROD";
          }
       }
       
       sSQL = "SELECT P.CODPROD,P.REFPROD,P.DESCPROD,P.CODGRUP, P.SALDO, P.CUSTO, P.VLRESTOQ " +
       		"FROM EQRELINVPRODSP(?,?,?,?,?,?,?) P " +
       		"ORDER BY "+sOrdem; 

       /*
        * Parâmetros da procedure ICODEMP, ICODFILIAL, CTIPOCUSTO, ICODEMPGP, SCODFILIALGP,
        * CCODGRUP, DDTESTOQ
        */
       ps = con.prepareStatement(sSQL);
       ps.setInt(1,Aplicativo.iCodEmp);
       ps.setInt(2,ListaCampos.getMasterFilial("EQPRODUTO"));
       ps.setString(3,tipoCusto());
       ps.setInt(4,Aplicativo.iCodEmp);
       ps.setInt(5,ListaCampos.getMasterFilial("EQGRUPO"));
       if (sCodgrupFiltro.trim().equals(""))
           ps.setNull(6,Types.CHAR);
       else       
          ps.setString(6,sCodgrupFiltro);
       ps.setDate(7,Funcoes.dateToSQLDate(Funcoes.dateToSQLDate(dtEstoq))); 
       rs = ps.executeQuery();
       imp.limpaPags();
       
       sCodgrup = "";
       
       while ( rs.next() ) {
          if (imp.pRow()>=(linPag-1)) {
            imp.say(imp.pRow()+1,0,""+imp.comprimido());
            imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",134)+"|");
            imp.incPags();
            imp.eject();
          }
          else if (sDivGrupo.equals("S")) {
             if (!sCodgrup.equals(rs.getString("Codgrup"))) {
                imp.say(imp.pRow()+1,0,""+imp.comprimido());
                imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",134)+"|");
                imp.incPags();
                imp.eject();
             }
          }

          if (imp.pRow()==0) {
             imp.say(imp.pRow()+1,0,""+imp.comprimido());
             imp.say(imp.pRow()+0,0,"+"+Funcoes.replicate("-",134)+"+");
             imp.say(imp.pRow()+1,0,""+imp.comprimido());
             imp.say(imp.pRow()+0,0,"| Emitido em :"+Funcoes.dateToStrDate(new Date()));
             imp.say(imp.pRow()+0,120,"Pagina : "+(imp.getNumPags()));
             imp.say(imp.pRow()+0,136,"|");
             imp.say(imp.pRow()+1,0,""+imp.comprimido());
             imp.say(imp.pRow()+0,0,"|");
             imp.say(imp.pRow()+0,44,"POSIÇÃO DO ESTOQUE EM "+Funcoes.dateToStrDate(dtEstoq));
             imp.say(imp.pRow()+0,136,"|");
             imp.say(imp.pRow()+1,0,""+imp.comprimido());
             imp.say(imp.pRow()+0,0,"|");
             imp.say(imp.pRow()+0,136,"|");
             imp.say(imp.pRow()+1,0,""+imp.comprimido());
             imp.say(imp.pRow()+0,0,"|");
             imp.say(imp.pRow()+0,55,"Ordenado por: "+sOrdenado);
             imp.say(imp.pRow()+0,136,"|");
             if (sDivGrupo.equals("S")) {
               imp.say(imp.pRow()+1,0,""+imp.comprimido());
               imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",134)+"|");
               imp.say(imp.pRow()+1,0,""+imp.comprimido());
               imp.say(imp.pRow()+0,0,"|");
               imp.say(imp.pRow()+0,(136-rs.getString("Descgrup").length())/2,rs.getString("Descgrup"));
               imp.say(imp.pRow()+0,136,"|");
             }
             imp.say(imp.pRow()+1,0,""+imp.comprimido());
             imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",134)+"|");
             imp.say(imp.pRow()+1,0,""+imp.comprimido());
             imp.say(imp.pRow()+0,0,"| Grupo/"+sRefCod);
             imp.say(imp.pRow()+0,26,"| Descriçao");
             imp.say(imp.pRow()+0,69,"| Quant.");
             imp.say(imp.pRow()+0,80,"| "+(tipoCusto().equals("M") ? "Custo MPM" : "Custo PEPS"));
             imp.say(imp.pRow()+0,103,"| Total");
             imp.say(imp.pRow()+0,136,"|");
             imp.say(imp.pRow()+1,0,""+imp.comprimido());
             imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",134)+"|");
          }
          String sRef = "";
          if (sRefCod.equals("Ref"))
             sRef = rs.getString("RefProd");
          else 
             sRef = rs.getString("CodProd");
          imp.say(imp.pRow()+1,0,""+imp.comprimido());
          imp.say(imp.pRow()+0,0,"| "+Funcoes.copy(rs.getString("CODGRUP")+"/"+sRef,0,23)+
          Funcoes.copy("| "+rs.getString("DESCPROD"),0,43)+
          Funcoes.copy("| "+rs.getString("SALDO"),0,11)+
             "| "+Funcoes.strDecimalToStrCurrency(21,2,rs.getString("CUSTO"))+
             "| "+Funcoes.strDecimalToStrCurrency(31,2,rs.getString("VLRESTOQ"))+"|");
          deTotal += rs.getDouble("VLRESTOQ");
           
          sCodgrup = rs.getString("CODGRUP");      
       }
       rs.close();
       ps.close();
       if (!con.getAutoCommit())
        	con.commit();
       
       imp.say(imp.pRow()+1,0,""+imp.comprimido());
       imp.say(imp.pRow()+0,0,"+"+Funcoes.replicate("=",134)+"+");
       imp.say(imp.pRow()+1,0,""+imp.comprimido());
       imp.say(imp.pRow()+0,0,"|   VALOR TOTAL DO ESTOQUE EM "+Funcoes.dateToStrDate(dtEstoq)+": "+Funcoes.strDecimalToStrCurrency(15,2,""+deTotal).trim());
       imp.say(imp.pRow()+0,136,"|");
       imp.say(imp.pRow()+1,0,""+imp.comprimido());
       imp.say(imp.pRow()+0,0,"+"+Funcoes.replicate("=",134)+"+");
       imp.incPags();
       imp.eject();
       imp.fechaGravacao();
       if (bVisualizar) {
            imp.preview(this);
       }
       else {
            imp.print();
       }

    }  
       catch ( SQLException err ) {
  	   Funcoes.mensagemErro(this,"Erro consulta tabela de setores!"+err.getMessage());      
    }
    finally {
        imp = null;
        sOrdem = null;
        sOrdenado = null;
        sRefCod = null;
        sOrdemGrupo = null;
        sDivGrupo = null;
        sCodgrup = null;
        sCodgrupFiltro = null;
        sSQL = null;
        dtEstoq = null;
        oVals = null;
        dl = null;
        ps = null;
        rs = null;
    }
      
  }
  
}

