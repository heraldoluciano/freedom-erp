/**
 * @version 02/11/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FTransp.java <BR>
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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import org.freedom.acao.PostEvent;
import org.freedom.acao.PostListener;
import org.freedom.acao.RadioGroupEvent;
import org.freedom.acao.RadioGroupListener;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FDados;


public class FTransp extends FDados implements PostListener,RadioGroupListener {
  private JTextFieldPad txtCodTran = new JTextFieldPad(5);
  private JTextFieldPad txtRazTran = new JTextFieldPad(50);
  private JTextFieldPad txtNomeTran = new JTextFieldPad(40);
  private JTextFieldPad txtEndTran = new JTextFieldPad(50);
  private JTextFieldPad txtNumTran = new JTextFieldPad(8);
  private JTextFieldPad txtCnpjTran = new JTextFieldPad(14);
  private JTextFieldPad txtInscTran = new JTextFieldPad(15);
  private JTextFieldPad txtBairTran = new JTextFieldPad(30);
  private JTextFieldPad txtComplTran = new JTextFieldPad(20);
  private JTextFieldPad txtCidTran = new JTextFieldPad(30);
  private JTextFieldPad txtCepTran = new JTextFieldPad(8);
  private JTextFieldPad txtFoneTran = new JTextFieldPad(12);
  private JTextFieldPad txtFaxTran = new JTextFieldPad(8);
  private JTextFieldPad txtCelTran = new JTextFieldPad(8);
  private JTextFieldPad txtContTran = new JTextFieldPad(40);
  private JTextFieldPad txtUFTran = new JTextFieldPad(2);
  private Vector vTipoTransp = new Vector();
  private Vector vTipoTranspVal = new Vector();
  private JRadioGroup rgTipoTransp =null ;
  public FTransp () {
    setTitulo("Cadastro de Tranportadoras");
    setAtribos( 50, 50, 396, 360);
    
    lcCampos.addPostListener(this);
	 
	vTipoTransp.addElement("Cliente");
	vTipoTransp.addElement("Transp.");
	vTipoTranspVal.addElement("C");
	vTipoTranspVal.addElement("T");
	rgTipoTransp = new JRadioGroup( 2, 1, vTipoTransp, vTipoTranspVal);
	   
	rgTipoTransp.setVlrString("C");
	
    adicCampo(txtCodTran, 7, 20, 70, 20, "CodTran", "Cód.tran.", JTextFieldPad.TP_INTEGER, 5, 0, true, false, null, true);
    adicCampo(txtRazTran, 80, 20, 205,20, "RazTran", "Razão social da transportadora", JTextFieldPad.TP_STRING, 50, 0, false, false, null, true);
	adicDB(rgTipoTransp, 290, 20, 84,61, "TipoTran", "Tipo",JTextFieldPad.TP_STRING,true);
    adicCampo(txtNomeTran, 7, 60, 278,20, "NomeTran", "Nome fantasia", JTextFieldPad.TP_STRING, 40, 0, false, false, null, true);
    adicCampo(txtCnpjTran, 7, 100, 181, 20, "CnpjTran", "Cnpj", JTextFieldPad.TP_STRING, 14, 0, false, false, null, true);
    adicCampo(txtInscTran, 192, 100, 181, 20, "InscTran", "Inscrição Estadual", JTextFieldPad.TP_STRING, 15, 0, false, false, null, true);
    adicCampo(txtEndTran, 7, 140, 260, 20, "EndTran", "Endereço", JTextFieldPad.TP_STRING, 50, 0, false, false, null, false);
    adicCampo(txtNumTran, 270, 140, 50, 20, "NumTran", "Num.", JTextFieldPad.TP_INTEGER, 8, 0, false, false, null, false);
    adicCampo(txtComplTran, 323, 140, 49, 20, "ComplTran", "Compl.", JTextFieldPad.TP_STRING, 20, 0, false, false, null, false);
    adicCampo(txtBairTran, 7, 180, 120, 20, "BairTran", "Bairro", JTextFieldPad.TP_STRING, 30, 0, false, false, null, false);
    adicCampo(txtCidTran, 130, 180, 120, 20, "CidTran", "Cidade", JTextFieldPad.TP_STRING, 30, 0, false, false, null, false);
    adicCampo(txtCepTran, 253, 180, 80, 20, "CepTran", "Cep", JTextFieldPad.TP_STRING, 8, 0, false, false, null, false);
    adicCampo(txtUFTran, 336, 180, 36, 20, "UFTran", "UF", JTextFieldPad.TP_STRING, 2, 0, false, false, null, false);
    adicCampo(txtFoneTran, 7, 220, 181, 20, "FoneTran", "Telefone", JTextFieldPad.TP_STRING, 12, 0, false, false, null, false);
    adicCampo(txtFaxTran, 191, 220, 181, 20, "FaxTran", "Fax", JTextFieldPad.TP_STRING, 8, 0, false, false, null, false);
    adicCampo(txtContTran,7,260,223,20,"Conttran","Contato",JTextFieldPad.TP_STRING,40,0,false,false,null,false);
    adicCampo(txtCelTran,233,260,139,20,"Celtran","Celular",JTextFieldPad.TP_STRING,8,0,false,false,null,false);

    txtCnpjTran.setMascara(JTextFieldPad.MC_CNPJ);
    txtCepTran.setMascara(JTextFieldPad.MC_CEP);
    txtFoneTran.setMascara(JTextFieldPad.MC_FONEDDD);
    txtFaxTran.setMascara(JTextFieldPad.MC_FONE); 
    setListaCampos( true, "TRANSP", "VD");
    lcCampos.setQueryInsert(false);
    
	rgTipoTransp.addRadioGroupListener(this);

    
        
  }  
  public void beforePost(PostEvent pevt) {
    if (txtCnpjTran.getText().trim().length() < 1) {
      pevt.cancela();
      Funcoes.mensagemInforma( this,"Campo CNPJ é requerido! ! !");
      txtCnpjTran.requestFocus();
    }
    else if (txtInscTran.getText().trim().length() < 1) {
      if (Funcoes.mensagemConfirma(this, "Inscrição Estadual em branco! Inserir ISENTO?")==0 )
        txtInscTran.setVlrString("ISENTO");
      else {
        pevt.cancela();
        txtInscTran.requestFocus();
      }
    }
    else if (txtInscTran.getText().trim().toUpperCase().compareTo("ISENTO") == 0)
      return;
    else if (txtUFTran.getText().trim().length() < 2) {
      pevt.cancela();
      Funcoes.mensagemInforma( this,"Campo UF é requerido! ! !");
      txtUFTran.requestFocus();
    }
    else if (!Funcoes.vIE(txtInscTran.getText(),txtUFTran.getText())) {
      pevt.cancela();
      Funcoes.mensagemInforma( this,"Inscrição Estadual Inválida ! ! !");
      txtInscTran.requestFocus();
    }
    txtInscTran.setVlrString(Funcoes.sIEValida);
  }
  public void afterPost(PostEvent pevt) { }
  public void execShow(Connection cn) {
	 super.execShow(cn);
   }
  
  
   public void valorAlterado(RadioGroupEvent rgevt) {
      if (rgTipoTransp.getVlrString().compareTo("C") == 0){
	      carregaCnpj(); 
      }
    }
   public void carregaCnpj() {
	  String sSQL = "SELECT CNPJFILIAL, INSCFILIAL, UFFILIAL FROM SGFILIAL WHERE CODEMP=? AND CODFILIAL=?";
	  PreparedStatement ps = null;
	  ResultSet rs = null;
	  try {
		ps = con.prepareStatement(sSQL);
		ps.setInt(1,Aplicativo.iCodEmp);
		ps.setInt(2,Aplicativo.iCodFilial);
		rs = ps.executeQuery();
		if (rs.next()){
			txtCnpjTran.setVlrString(rs.getString(1));
			txtInscTran.setVlrString(rs.getString(2));
			txtUFTran.setVlrString(rs.getString(3));
			txtRazTran.setVlrString("O MESMO");
			txtNomeTran.setVlrString("O MESMO");
			txtEndTran.setVlrString("O MESMO");
			txtNumTran.setVlrString("");
			txtComplTran.setVlrString("");
			txtBairTran.setVlrString("O MESMO");
			txtCidTran.setVlrString("O MESMO");
			txtFoneTran.setVlrString("");
			txtFaxTran.setVlrString("");
			
		  }
        }
		catch (SQLException err) {
				Funcoes.mensagemErro(this, "Erro ao carregar tabela SGFILIAL! ! !");
				Funcoes.mensagemErro(this, err.getMessage());
			    return;
	    }
	  }
}	  
   	  
	   

