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
 * Comentários sobre a classe...
 * 
 */

package org.freedom.modulos.std;
import java.awt.Rectangle;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.freedom.acao.PostEvent;
import org.freedom.acao.PostListener;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FDados;
public class FVendedor extends FDados implements PostListener { 
  private JTextFieldPad txtCodVend = new JTextFieldPad(5);
  private JTextFieldPad txtNomeVend = new JTextFieldPad(40);
  private JTextFieldPad txtCpfVend = new JTextFieldPad(14);
  private JTextFieldPad txtRgVend = new JTextFieldPad(12);
  private JTextFieldPad txtCnpjVend = new JTextFieldPad(14);
  private JTextFieldPad txtInscVend = new JTextFieldPad(15);
  private JTextFieldPad txtEndVend = new JTextFieldPad(50);
  private JTextFieldPad txtNumVend = new JTextFieldPad(8);
  private JTextFieldPad txtBairVend = new JTextFieldPad(30);
  private JTextFieldPad txtComplVend = new JTextFieldPad(20);
  private JTextFieldPad txtCidVend = new JTextFieldPad(30);
  private JTextFieldPad txtCepVend = new JTextFieldPad(8);
  private JTextFieldPad txtFoneVend = new JTextFieldPad(12);
  private JTextFieldPad txtCelVend = new JTextFieldPad(12);
  private JTextFieldPad txtFaxVend = new JTextFieldPad(8);
  private JTextFieldPad txtUFVend = new JTextFieldPad(2);
  private JTextFieldPad txtEmailVend = new JTextFieldPad(50);
  private JTextFieldPad txtPercComVend = new JTextFieldPad(7);
  private JTextFieldPad txtCodFornVend = new JTextFieldPad(JTextFieldPad.TP_STRING,13,0);
  private JTextFieldPad txtCodPlan = new JTextFieldPad();
  private JTextFieldFK txtDescPlan = new JTextFieldFK();
  
  private JTextFieldPad txtCodClComis = new JTextFieldPad();
  private JTextFieldFK txtDescClComis = new JTextFieldFK();
  
  private JTextFieldPad txtCodSetor = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldFK txtDescSetor = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
  private ListaCampos lcPlan = new ListaCampos(this,"PN");
  private ListaCampos lcSetor = new ListaCampos(this,"SE");
  private ListaCampos lcClComis = new ListaCampos(this,"CM");
  
  private Connection con = null;
  public FVendedor () {
    setTitulo("Cadastro de Vendedores");
    setAtribos( 50, 50, 400, 400);

    lcPlan.add(new GuardaCampo( txtCodPlan, 7, 100, 80, 20, "CodPlan", "Código", true, false, txtDescPlan, JTextFieldPad.TP_STRING,false),"txtCodVendx");
    lcPlan.add(new GuardaCampo( txtDescPlan, 7, 100, 80, 20, "DescPlan", "Descriçao", false, false, null, JTextFieldPad.TP_STRING,false),"txtCodVendx");
    lcPlan.setWhereAdic("TIPOPLAN = 'D'");
    lcPlan.montaSql(false, "PLANEJAMENTO", "FN");    
    lcPlan.setQueryCommit(false);
    lcPlan.setReadOnly(true);
    txtCodPlan.setTabelaExterna(lcPlan);
    
    
    lcClComis.add(new GuardaCampo( txtCodClComis, 7, 100, 80, 20, "CodClComis", "Código", true, false, txtDescClComis, JTextFieldPad.TP_STRING,false),"txtCodClComis");
    lcClComis.add(new GuardaCampo( txtDescClComis, 7, 100, 80, 20, "DescClComis", "Descriçao", false, false, null, JTextFieldPad.TP_STRING,false),"txtCodClComis");
    lcClComis.montaSql(false, "CLCOMIS", "VD");    
    lcClComis.setQueryCommit(false);
    lcClComis.setReadOnly(true);
    txtCodClComis.setTabelaExterna(lcClComis);
    
    
    
    
    adicCampo(txtCodVend, 7, 20, 50, 20, "CodVend", "Código", JTextFieldPad.TP_INTEGER, 5, 0, true, false, null, true);
    adicCampo(txtNomeVend, 60, 20, 312, 20, "NomeVend", "Nome", JTextFieldPad.TP_STRING, 40, 0, false, false, null, true);
    adicCampo(txtCpfVend, 7, 60, 180, 20, "CpfVend", "CPF", JTextFieldPad.TP_STRING, 14, 0, false, false, null, false);
    adicCampo(txtRgVend, 190, 60, 182, 20, "RgVend", "RG", JTextFieldPad.TP_STRING, 12, 0, false, false, null, false);
    adicCampo(txtCnpjVend, 7, 100, 180, 20, "CnpjVend", "CNPJ", JTextFieldPad.TP_STRING, 18, 0, false, false, null, false);
    adicCampo(txtInscVend, 190, 100, 182, 20, "InscVend", "IE", JTextFieldPad.TP_STRING, 20, 0, false, false, null, false);
    adicCampo(txtEndVend, 7, 140, 260, 20, "EndVend", "Endereço", JTextFieldPad.TP_STRING, 50, 0, false, false, null, false);
    adicCampo(txtNumVend, 270, 140, 50, 20, "NumVend", "Num.", JTextFieldPad.TP_INTEGER, 8, 0, false, false, null, false);
    adicCampo(txtComplVend, 323, 140, 49, 20, "ComplVend", "Compl.", JTextFieldPad.TP_STRING, 20, 0, false, false, null, false);
    adicCampo(txtBairVend, 7, 180, 120, 20, "BairVend", "Bairro", JTextFieldPad.TP_STRING, 30, 0, false, false, null, false);
    adicCampo(txtCidVend, 130, 180, 120, 20, "CidVend", "Cidade", JTextFieldPad.TP_STRING, 30, 0, false, false, null, false);
    adicCampo(txtCepVend, 253, 180, 80, 20, "CepVend", "Cep", JTextFieldPad.TP_STRING, 8, 0, false, false, null, false);
    adicCampo(txtUFVend, 336, 180, 36, 20, "UFVend", "UF", JTextFieldPad.TP_STRING, 2, 0, false, false, null, false);
    adicCampo(txtFoneVend, 7, 220, 120, 20, "FoneVend", "Telefone", JTextFieldPad.TP_STRING, 12, 0, false, false, null, false);
    adicCampo(txtFaxVend, 130, 220, 120, 20, "FaxVend", "Fax", JTextFieldPad.TP_STRING, 8, 0, false, false, null, false);
    adicCampo(txtCelVend, 253, 220, 119, 20, "CelVend", "Cel", JTextFieldPad.TP_STRING, 12, 0, false, false, null, false);
    adicCampo(txtEmailVend, 7, 260, 200, 20, "EmailVend", "E-Mail", JTextFieldPad.TP_STRING, 50, 0, false, false, null, false);
    adicCampo(txtPercComVend, 210, 260, 77, 20, "PercComVend", "Comissão", JTextFieldPad.TP_DECIMAL, 7, 3, false, false, null, false);
    adicCampo(txtCodFornVend, 290, 260, 82, 20, "CodFornVend", "C.Vend.Forn.", JTextFieldPad.TP_STRING, 13, 0, false, false, null, false);
    adicCampo(txtCodPlan, 7, 300, 100, 20, "CodPlan", "Código", JTextFieldPad.TP_STRING, 13, 0, false, true, txtDescPlan,false);
    adicDescFK(txtDescPlan, 110, 300, 262, 20, "DescPlan", "e descrição do lançamento", JTextFieldPad.TP_STRING, 50, 0);

    txtCpfVend.setMascara(JTextFieldPad.MC_CPF);
    txtCnpjVend.setMascara(JTextFieldPad.MC_CNPJ);
    txtCepVend.setMascara(JTextFieldPad.MC_CEP);
    txtFoneVend.setMascara(JTextFieldPad.MC_FONEDDD);
    txtCelVend.setMascara(JTextFieldPad.MC_FONEDDD);
    txtFaxVend.setMascara(JTextFieldPad.MC_FONE);
    lcCampos.addPostListener(this);
    lcCampos.setQueryInsert(false);    
  }
  private void montaSetor() {

    Rectangle rec = getBounds();
    rec.height += 80;
	setBounds(rec);

	lcSetor.add(new GuardaCampo( txtCodSetor, 7, 100, 80, 20, "CodSetor", "Código", true, false, txtDescSetor, JTextFieldPad.TP_STRING,false),"txtCodVendx");
	lcSetor.add(new GuardaCampo( txtDescSetor, 7, 100, 80, 20, "DescSetor", "Descriçao", false, false, null, JTextFieldPad.TP_STRING,false),"txtCodVendx");
	lcSetor.montaSql(false, "SETOR", "VD");    
	lcSetor.setQueryCommit(false);
	lcSetor.setReadOnly(true);
	txtCodSetor.setTabelaExterna(lcSetor);
    
	adicCampo(txtCodSetor, 7, 340, 100, 20, "CodSetor", "Código", JTextFieldPad.TP_INTEGER, 8, 0, false, true, txtDescSetor,false);
	adicDescFK(txtDescSetor, 110, 340, 262, 20, "DescSetor", "e descrição do setor", JTextFieldPad.TP_STRING, 50, 0);

	adicCampo(txtCodClComis, 7, 380, 100, 20, "CodClComis", "Código", JTextFieldPad.TP_INTEGER, 8, 0, false, true, txtDescClComis,false);
	adicDescFK(txtDescClComis, 110, 380, 262, 20, "DescClComis", "e descrição da Classificacao da comissão", JTextFieldPad.TP_STRING, 50, 0);

	
	
	lcSetor.setConexao(con);
	lcClComis.setConexao(con);
  }
  public void beforePost(PostEvent pevt) {
          if (txtInscVend.getText().trim().length() < 1) {
                if (Funcoes.mensagemConfirma(this, "Inscrição Estadual em branco! Inserir ISENTO?")==0 )
                        txtInscVend.setVlrString("ISENTO");
                else {
                        pevt.cancela();
                        txtInscVend.requestFocus();
                }
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
  public void execShow(Connection cn) {
  	con = cn;
    if (ehSetorVend())
  	  montaSetor();
    lcPlan.setConexao(cn);
	setListaCampos( true, "VENDEDOR", "VD");
    super.execShow(cn);
  }
}
