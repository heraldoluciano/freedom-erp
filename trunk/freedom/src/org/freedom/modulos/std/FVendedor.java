/**
 * @version 02/11/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FVendedor.java <BR>
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
 * Tela de cadastro de comissionados (vendedores).
 * 
 */

package org.freedom.modulos.std;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JOptionPane;

import org.freedom.acao.PostEvent;
import org.freedom.acao.PostListener;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FDados;

public class FVendedor extends FDados implements PostListener { 
  private JTextFieldPad txtCodVend = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 5, 0);
  private JTextFieldPad txtNomeVend = new JTextFieldPad(JTextFieldPad.TP_STRING, 40, 0);
  private JTextFieldPad txtCpfVend = new JTextFieldPad(JTextFieldPad.TP_STRING, 14, 0);
  private JTextFieldPad txtRgVend = new JTextFieldPad(JTextFieldPad.TP_STRING, 12, 0);
  private JTextFieldPad txtCnpjVend = new JTextFieldPad(JTextFieldPad.TP_STRING, 18, 0);
  private JTextFieldPad txtInscVend = new JTextFieldPad(JTextFieldPad.TP_STRING, 20, 0);
  private JTextFieldPad txtEndVend = new JTextFieldPad(JTextFieldPad.TP_STRING, 50, 0);
  private JTextFieldPad txtNumVend = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
  private JTextFieldPad txtBairVend = new JTextFieldPad(JTextFieldPad.TP_STRING, 30, 0);
  private JTextFieldPad txtComplVend = new JTextFieldPad(JTextFieldPad.TP_STRING, 20, 0);
  private JTextFieldPad txtCidVend = new JTextFieldPad(JTextFieldPad.TP_STRING, 30, 0);
  private JTextFieldPad txtCepVend = new JTextFieldPad(JTextFieldPad.TP_STRING, 8, 0);
  private JTextFieldPad txtDDDFoneVend = new JTextFieldPad(JTextFieldPad.TP_STRING, 4, 0);
  private JTextFieldPad txtFoneVend = new JTextFieldPad(JTextFieldPad.TP_STRING, 12, 0);
  private JTextFieldPad txtDDDCelVend = new JTextFieldPad(JTextFieldPad.TP_STRING, 4, 0);
  private JTextFieldPad txtCelVend = new JTextFieldPad(JTextFieldPad.TP_STRING, 12, 0);
  private JTextFieldPad txtDDDFaxVend = new JTextFieldPad(JTextFieldPad.TP_STRING, 4, 0);
  private JTextFieldPad txtFaxVend = new JTextFieldPad(JTextFieldPad.TP_STRING, 8, 0);
  private JTextFieldPad txtUFVend = new JTextFieldPad(JTextFieldPad.TP_STRING, 2, 0);
  private JTextFieldPad txtEmailVend = new JTextFieldPad(JTextFieldPad.TP_STRING, 50, 0);
  private JTextFieldPad txtPercComVend = new JTextFieldPad(JTextFieldPad.TP_DECIMAL, 7, 3);
  private JTextFieldPad txtCodFornVend = new JTextFieldPad(JTextFieldPad.TP_STRING,13,0);
  private JTextFieldPad txtCodPlan = new JTextFieldPad(JTextFieldPad.TP_STRING, 13, 0);
  private JTextFieldFK txtDescPlan = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);
  private JTextFieldPad txtCodFunc = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
  private JTextFieldFK txtDescFunc = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);
  private JTextFieldPad txtCodClComis = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
  private JTextFieldFK txtDescClComis = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);
  private JTextFieldPad txtSSPVend = new JTextFieldPad(JTextFieldPad.TP_STRING, 10, 0);
  private JTextFieldPad txtCodSetor = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldFK txtDescSetor = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
  private ListaCampos lcPlan = new ListaCampos(this,"PN");
  private ListaCampos lcSetor = new ListaCampos(this,"SE");
  private ListaCampos lcClComis = new ListaCampos(this,"CM");
  private ListaCampos lcFuncao = new ListaCampos(this,"FU");

  public FVendedor () {
    setTitulo("Cadastro de comissionados");
    setAtribos( 50, 50, 400, 460);

    lcPlan.add(new GuardaCampo( txtCodPlan, "CodPlan", "Cód.plan.", ListaCampos.DB_PK, txtDescPlan, false));
    lcPlan.add(new GuardaCampo( txtDescPlan, "DescPlan", "Descriçao do planejamento", ListaCampos.DB_SI, false));
    lcPlan.setWhereAdic("TIPOPLAN = 'D'");
    lcPlan.montaSql(false, "PLANEJAMENTO", "FN");    
    lcPlan.setQueryCommit(false);
    lcPlan.setReadOnly(true);
    txtCodPlan.setTabelaExterna(lcPlan);
    
    lcFuncao.add(new GuardaCampo( txtCodFunc, "CodFunc", "Cód.função", ListaCampos.DB_PK, txtDescFunc, false));
    lcFuncao.add(new GuardaCampo( txtDescFunc, "DescFunc", "Descriçao da função", ListaCampos.DB_SI, false));
    
//    lcFuncao.setQueryCommit(false);

    txtCodFunc.setTabelaExterna(lcFuncao);
    txtCodFunc.setNomeCampo("codfunc");
    txtCodFunc.setFK(true);
    lcFuncao.setReadOnly(true);
    lcFuncao.montaSql(false, "FUNCAO", "RH");    

    
    
    
    lcClComis.add(new GuardaCampo( txtCodClComis, "CodClComis", "Cód.cl.comis.", ListaCampos.DB_PK, txtDescClComis, false));
    lcClComis.add(new GuardaCampo( txtDescClComis, "DescClComis", "Descriçao da classificação da comissão", ListaCampos.DB_SI, false));
    lcClComis.montaSql(false, "CLCOMIS", "VD");    
    lcClComis.setQueryCommit(false);
    lcClComis.setReadOnly(true);
    txtCodClComis.setTabelaExterna(lcClComis);
    
    adicCampo(txtCodVend, 7, 20, 100, 20, "CodVend", "Cód.comiss.", ListaCampos.DB_PK, true);
    adicCampo(txtNomeVend, 110, 20, 262, 20, "NomeVend", "Nome do comissionado", ListaCampos.DB_SI, true);
    adicCampo(txtCpfVend, 7, 60, 130, 20, "CpfVend", "CPF", ListaCampos.DB_SI, false);
    adicCampo(txtRgVend, 140, 60, 149, 20, "RgVend", "RG", ListaCampos.DB_SI, false);
    adicCampo(txtSSPVend, 292, 60, 80, 20, "SSPVend", "SSP", ListaCampos.DB_SI, false);

    adicCampo(txtCnpjVend, 7, 100, 180, 20, "CnpjVend", "CNPJ", ListaCampos.DB_SI, false);
    adicCampo(txtInscVend, 190, 100, 182, 20, "InscVend", "IE", ListaCampos.DB_SI, false);
    adicCampo(txtEndVend, 7, 140, 260, 20, "EndVend", "Endereço", ListaCampos.DB_SI, false);
    adicCampo(txtNumVend, 270, 140, 50, 20, "NumVend", "Num.", ListaCampos.DB_SI, false);
    adicCampo(txtComplVend, 323, 140, 49, 20, "ComplVend", "Compl.", ListaCampos.DB_SI, false);
    adicCampo(txtBairVend, 7, 180, 120, 20, "BairVend", "Bairro", ListaCampos.DB_SI, false);
    adicCampo(txtCidVend, 130, 180, 120, 20, "CidVend", "Cidade", ListaCampos.DB_SI, false);
    adicCampo(txtCepVend, 253, 180, 80, 20, "CepVend", "Cep", ListaCampos.DB_SI, false);
    adicCampo(txtUFVend, 336, 180, 36, 20, "UFVend", "UF", ListaCampos.DB_SI, false);
    adicCampo(txtDDDFoneVend, 7, 220, 40, 20, "DDDFoneVend", "DDD", ListaCampos.DB_SI, false);
    adicCampo(txtFoneVend, 50, 220, 77, 20, "FoneVend", "Telefone", ListaCampos.DB_SI, false);
    adicCampo(txtDDDFaxVend, 130, 220, 40, 20, "DDDFaxVend", "DDD", ListaCampos.DB_SI, false);
    adicCampo(txtFaxVend, 177, 220, 77, 20, "FaxVend", "Fax", ListaCampos.DB_SI, false);
    adicCampo(txtDDDCelVend, 257, 220, 40, 20, "DDDCelVend", "DDD", ListaCampos.DB_SI, false);
    adicCampo(txtCelVend, 300, 220, 72, 20, "CelVend", "Cel", ListaCampos.DB_SI, false);
    adicCampo(txtEmailVend, 7, 260, 200, 20, "EmailVend", "E-Mail", ListaCampos.DB_SI, false);
    adicCampo(txtPercComVend, 210, 260, 77, 20, "PercComVend", "Comissão", ListaCampos.DB_SI, false);
    adicCampo(txtCodFornVend, 290, 260, 82, 20, "CodFornVend", "Cód.comis.for.", ListaCampos.DB_SI, false);
    adicCampo(txtCodPlan, 7, 300, 100, 20, "CodPlan", "Cód.plan.", ListaCampos.DB_FK, txtDescPlan, false);
    adicDescFK(txtDescPlan, 110, 300, 262, 20, "DescPlan", "Descrição do planejamento");
    adicCampo(txtCodFunc, 7, 420, 100, 20, "CodFunc", "Cód.função", ListaCampos.DB_FK, txtDescFunc, false);
    adicDescFK(txtDescFunc, 110, 420, 262, 20, "DescFunc", "Descrição da função");
    
    txtCpfVend.setMascara(JTextFieldPad.MC_CPF);
    txtCnpjVend.setMascara(JTextFieldPad.MC_CNPJ);
    txtCepVend.setMascara(JTextFieldPad.MC_CEP);
    txtFoneVend.setMascara(JTextFieldPad.MC_FONE);
    txtCelVend.setMascara(JTextFieldPad.MC_FONE);
    txtFaxVend.setMascara(JTextFieldPad.MC_FONE);
    lcCampos.addPostListener(this);
    lcCampos.setQueryInsert(false);    
  	btImp.addActionListener(this);
  	btPrevimp.addActionListener(this);
  }
  private void montaSetor() {

    Rectangle rec = getBounds();
    rec.height += 80;
	setBounds(rec);

	lcSetor.add(new GuardaCampo( txtCodSetor, "CodSetor", "Cód.setor", ListaCampos.DB_PK, txtDescSetor, false));
	lcSetor.add(new GuardaCampo( txtDescSetor, "DescSetor", "Descriçao do setor", ListaCampos.DB_SI, false));
	lcSetor.montaSql(false, "SETOR", "VD");    
	lcSetor.setQueryCommit(false);
	lcSetor.setReadOnly(true);
	txtCodSetor.setTabelaExterna(lcSetor);
    
	adicCampo(txtCodSetor, 7, 340, 100, 20, "CodSetor", "Cód.setor", ListaCampos.DB_FK, txtDescSetor, false);
	adicDescFK(txtDescSetor, 110, 340, 262, 20, "DescSetor", "Descrição do setor");

	adicCampo(txtCodClComis, 7, 380, 100, 20, "CodClComis", "Cód.cl.comis.", ListaCampos.DB_FK, txtDescClComis, false);
	adicDescFK(txtDescClComis, 110, 380, 262, 20, "DescClComis", "Descrição da Classificacao da comissão");
	
	lcSetor.setConexao(con);
	lcClComis.setConexao(con);
  }
  public void beforePost(PostEvent pevt) {
          if (txtInscVend.getText().trim().length() < 1) {
          	if (Funcoes.mensagemConfirma(this, "Inscrição Estadual em branco! Inserir ISENTO?")==JOptionPane.OK_OPTION)
          		txtInscVend.setVlrString("ISENTO");

          	pevt.cancela();
          	txtInscVend.requestFocus();
          }
          else if (txtInscVend.getText().trim().toUpperCase().compareTo("ISENTO") == 0)
                return;
          else if (txtUFVend.getText().trim().length() < 2) {
                pevt.cancela();
                Funcoes.mensagemInforma( this,"Campo UF é requerido! ! !");
                txtUFVend.requestFocus();
          }
          else if (Funcoes.vIE(txtInscVend.getText(),txtUFVend.getText()))
          	 txtInscVend.setVlrString(Funcoes.sIEValida);
          else {
          	    pevt.cancela();
                Funcoes.mensagemInforma( this,"Inscrição Estadual Inválida ! ! !");
                txtInscVend.requestFocus();
          }
  }
  private boolean ehSetorVend() {
  	boolean bRet = false; 
  	String sSQL = "SELECT SETORVENDA FROM SGPREFERE1 WHERE CODEMP=? AND CODFILIAL=?";
  	try {
  	  PreparedStatement ps = con.prepareStatement(sSQL);
      ps.setInt(1,Aplicativo.iCodEmp);
      ps.setInt(2,Aplicativo.iCodFilial);
      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
		String sVal = null;
      	if ((sVal = rs.getString("SetorVenda")) != null) {
      		if ("VA".indexOf(sVal) >= 0) //Se tiver V ou A no sVal!
      			bRet = true;
      	}
      }
  	}
  	catch(SQLException err) {
  		Funcoes.mensagemErro(this,"Erro ao verificar setor!\n"+err.getMessage());
  		err.printStackTrace();
  	}      
    return bRet;
  	
  }  
  public void setConexao(Connection cn) {
    super.setConexao(cn);
    if (ehSetorVend())
  	  montaSetor();
    lcPlan.setConexao(cn);
    lcFuncao.setConexao(cn);
	setListaCampos( true, "VENDEDOR", "VD");
  }
  
  private void imprimir(boolean bVisualizar) {
    ImprimeOS imp = new ImprimeOS("",con);
    Vector vFiltros = new Vector();
    String sSQL = "";
    int linPag = imp.verifLinPag()-1;
    int iContaReg = 0;
    String sWhere = "";
    String sWhere2 = "";
    String sCodpesq = "";
    String sCodpesqant = "";
    String sOrdem = "";
    String sValores[] = null;
    imp.setTitulo("Relatório de Comissionados");
    imp.montaCab();
    DLRVendedor dl = new DLRVendedor(this,con);
	dl.setVisible(true);
	if (dl.OK == false) {
	  dl.dispose();
	  return;
	}
	sValores = dl.getValores();
    dl.dispose();
 
    sOrdem = sValores[0];
    
    if (sValores[1].length() > 0) {
      System.out.println("CIDADE NO FILTRO:"+sValores[1]);
      sWhere += " AND VD.CIDVEND = '"+sValores[1]+"'";
      vFiltros.add("CIDADE = "+sValores[1].trim());
    }
    else {
    	System.out.println("Cidade nula no filtro");
    }
    if (sValores[2].length() > 0) {
          sWhere += " AND VD.CODCLCOMIS="+sValores[2];
          vFiltros.add("CLAS.COMISSÃO = "+sValores[2]);
    }
    if (sValores[3].length() > 0) {
      sWhere += " AND VD.CODSETOR = "+sValores[3];
      vFiltros.add("SETOR = "+sValores[3]);
    }
    if (sValores[4].length() > 0) {
        sWhere += " AND VD.CODFUNC = "+sValores[4];
        vFiltros.add("FUNÇÃO = "+sValores[4]);
      }    
    
        	
     sSQL = "select VD.CODVEND,VD.NOMEVEND,VD.DDDFONEVEND,VD.CIDVEND,VD.FONEVEND,VD.FAXVEND,VD.EMAILVEND,VD.PERCCOMVEND,VD.COMIPIVEND,"+
       		 "VD.CELVEND,VD.CODSETOR,"+
       		 "(SELECT SE.DESCSETOR FROM VDSETOR SE WHERE SE.CODEMP=VD.CODEMPSE AND SE.CODFILIAL=VD.CODFILIALSE AND SE.CODSETOR=VD.CODSETOR),"+
			 "VD.CODCLCOMIS,"+
			 "(SELECT CM.DESCCLCOMIS FROM VDCLCOMIS CM WHERE CM.CODEMP=VD.codempcm AND CM.CODFILIAL=VD.codfilialcm AND CM.codclcomis=VD.codclcomis),"+
			 "VD.CODFUNC,"+
			 "(SELECT FU.DESCFUNC FROM RHFUNCAO FU WHERE FU.CODEMP=VD.codempfu AND FU.CODFILIAL=VD.codfilialfu AND FU.codfunc=VD.codfunc)"+
			 "FROM vdvendedor VD "+
			 "WHERE VD.CODEMP=? AND VD.CODFILIAL=? "+sWhere+
			 " order by "+sValores[0];

      PreparedStatement ps = null;
      ResultSet rs = null;
      System.out.println("sql é "+sSQL);
      try {

        ps = con.prepareStatement(sSQL);
        ps.setInt(1,Aplicativo.iCodEmp);
        ps.setInt(2,ListaCampos.getMasterFilial("VDVENDEDOR"));
        rs = ps.executeQuery();
        imp.limpaPags();
        while ( rs.next() ) {
          if (imp.pRow()==0) {
            imp.impCab(136);

            for (int i=0;i<vFiltros.size();i++) {            
                	imp.say(imp.pRow()+0,2,"|"+Funcoes.replicate(" ",61)+"Filtrado por:"+Funcoes.replicate(" ",60)+"|");
                	String sTmp = (String)vFiltros.elementAt(i);
                    sTmp = "|"+Funcoes.replicate(" ",(((136-sTmp.length())/2)-1))+sTmp;
                    sTmp += Funcoes.replicate(" ",135-sTmp.length())+"|";
                    imp.say(imp.pRow()+1,0,""+imp.comprimido());
                    imp.say(imp.pRow()+0,2,sTmp);
            }
          
          imp.say(imp.pRow()+1,0,""+imp.comprimido());
          imp.say(imp.pRow()+0,0,Funcoes.replicate("-",136));
          imp.say(imp.pRow()+1,0,""+imp.comprimido());
          imp.say(imp.pRow()+0,0,"|");
          imp.say(imp.pRow()+0,4,"Código");
          imp.say(imp.pRow()+0,12,"|");
          imp.say(imp.pRow()+0,14,"Nome:");
          imp.say(imp.pRow()+0,46,"|"); 
          imp.say(imp.pRow()+0,48,"Setor:");
          imp.say(imp.pRow()+0,58,"|");
          imp.say(imp.pRow()+0,60,"Fução:");
          imp.say(imp.pRow()+0,70,"|");
          imp.say(imp.pRow()+0,72,"Cl.comis.:");
          imp.say(imp.pRow()+0,84,"|");
          imp.say(imp.pRow()+0,92,"Fone:");
          imp.say(imp.pRow()+0,106,"|");
         // if(sValores[1].length()>0)
          	imp.say(imp.pRow()+0,108,"Cidade:");
          imp.say(imp.pRow()+0,136,"|");
          imp.say(imp.pRow()+1,0,""+imp.comprimido());
          imp.say(imp.pRow()+0,0,Funcoes.replicate("-",136));
          }


          imp.say(imp.pRow()+1,0,""+imp.comprimido());
          imp.say(imp.pRow()+0,0,"|");
          imp.say(imp.pRow()+0,4,rs.getString("CodVend"));
          imp.say(imp.pRow()+0,12,"|");
          imp.say(imp.pRow()+0,14,rs.getString("NomeVend") != null ? rs.getString("NomeVend").substring(0,30) : "");
          imp.say(imp.pRow()+0,46,"|");
          imp.say(imp.pRow()+0,48,rs.getString("CodSetor") != null ? rs.getString("CodSetor") : "");
          imp.say(imp.pRow()+0,58,"|");
          imp.say(imp.pRow()+0,60,rs.getString("CodFunc") != null ? rs.getString("CodFunc") : "");
          imp.say(imp.pRow()+0,70,"|");   
          imp.say(imp.pRow()+0,72,rs.getString("CodClComis") != null ? rs.getString("CodClComis") : "");
          imp.say(imp.pRow()+0,84,"|");
          imp.say(imp.pRow()+0,86,rs.getString("DDDFoneVend") != null ? rs.getString("DDDFoneVend") : "");
          imp.say(imp.pRow()+0,92,rs.getString("FoneVend") != null ? Funcoes.setMascara(rs.getString("FoneVend"),"####-####") : "");
          imp.say(imp.pRow()+0,106,"|");
          imp.say(imp.pRow()+0,108,rs.getString("CidVend") != null ? rs.getString("CidVend").substring(0,20) : "");                         
          imp.say(imp.pRow()+0,136,"|");

 
          
          if (imp.pRow()>=linPag) {
            imp.incPags();
            imp.eject();
          }
          iContaReg++;
        }
        imp.say(imp.pRow()+1,0,""+imp.comprimido());
        imp.say(imp.pRow()+0,0,Funcoes.replicate("=",136));
        imp.eject();

        imp.fechaGravacao();

//        rs.close();
//        ps.close();
        if (!con.getAutoCommit())
        	con.commit();
        dl.dispose();
      }
      catch ( SQLException err ) {
		Funcoes.mensagemErro(this,"Erro consulta tabela de clientes!"+err.getMessage());
	    err.printStackTrace();
      }
    
    if (bVisualizar) {
      imp.preview(this);
    }
    else {
      imp.print();
    }
  
   }
  public void actionPerformed(ActionEvent evt) {
    if (evt.getSource() == btPrevimp) {
        imprimir(true);        
    }
    else if (evt.getSource() == btImp) 
      imprimir(false);
    super.actionPerformed(evt);
  }  
    
}
