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

import javax.swing.JOptionPane;

import org.freedom.acao.InsertEvent;
import org.freedom.acao.InsertListener;
import org.freedom.acao.PostEvent;
import org.freedom.acao.PostListener;
import org.freedom.acao.RadioGroupEvent;
import org.freedom.acao.RadioGroupListener;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FDados;


public class FTransp extends FDados implements PostListener,RadioGroupListener,InsertListener {
	private static final long serialVersionUID = 1L;

  private JTextFieldPad txtCodTran = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 5, 0);
  private JTextFieldPad txtRazTran = new JTextFieldPad(JTextFieldPad.TP_STRING, 50, 0);
  private JTextFieldPad txtNomeTran = new JTextFieldPad(JTextFieldPad.TP_STRING, 40, 0);
  private JTextFieldPad txtEndTran = new JTextFieldPad(JTextFieldPad.TP_STRING, 50, 0);
  private JTextFieldPad txtNumTran = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
  private JTextFieldPad txtCnpjTran = new JTextFieldPad(JTextFieldPad.TP_STRING, 14, 0);
  private JTextFieldPad txtCpfTran = new JTextFieldPad( JTextFieldPad.TP_STRING, 11, 0 );
  private JTextFieldPad txtInscTran = new JTextFieldPad(JTextFieldPad.TP_STRING, 15, 0);
  private JTextFieldPad txtBairTran = new JTextFieldPad(JTextFieldPad.TP_STRING, 30, 0);
  private JTextFieldPad txtComplTran = new JTextFieldPad(JTextFieldPad.TP_STRING, 20, 0);
  private JTextFieldPad txtCidTran = new JTextFieldPad(JTextFieldPad.TP_STRING, 30, 0);
  private JTextFieldPad txtCepTran = new JTextFieldPad(JTextFieldPad.TP_STRING, 8, 0);
  private JTextFieldPad txtFoneTran = new JTextFieldPad(JTextFieldPad.TP_STRING, 12, 0);
  private JTextFieldPad txtDDDFoneTran = new JTextFieldPad(JTextFieldPad.TP_STRING, 4, 0);
  private JTextFieldPad txtDDDFaxTran = new JTextFieldPad(JTextFieldPad.TP_STRING, 4, 0);
  private JTextFieldPad txtFaxTran = new JTextFieldPad(JTextFieldPad.TP_STRING, 8, 0);
  private JTextFieldPad txtDDDCelTran = new JTextFieldPad(JTextFieldPad.TP_STRING, 4, 0);
  private JTextFieldPad txtCelTran = new JTextFieldPad(JTextFieldPad.TP_STRING,8,0);
  private JTextFieldPad txtContTran = new JTextFieldPad(JTextFieldPad.TP_STRING,40,0);
  private JTextFieldPad txtUFTran = new JTextFieldPad(JTextFieldPad.TP_STRING, 2, 0);
  private Vector vTipoTransp = new Vector();
  private Vector vTipoTranspVal = new Vector();
  private JRadioGroup rgTipoTransp =null ;
  public FTransp () {
  	super();
    setTitulo("Cadastro de Tranportadoras");
    setAtribos( 50, 50, 396, 420);
    
    lcCampos.addInsertListener(this);
    lcCampos.addPostListener(this);
	 
	vTipoTransp.addElement("Transp.");
	vTipoTransp.addElement("Cliente");
	vTipoTranspVal.addElement("T");
	vTipoTranspVal.addElement("C");
	rgTipoTransp = new JRadioGroup( 2, 1, vTipoTransp, vTipoTranspVal);
	rgTipoTransp.setVlrString("T");
	
	
    adicCampo(txtCodTran, 7, 20, 70, 20, "CodTran", "Cód.tran.", ListaCampos.DB_PK, true);
    adicCampo(txtRazTran, 80, 20, 205,20, "RazTran", "Razão social da transportadora", ListaCampos.DB_SI, true);
	adicDB(rgTipoTransp, 290, 20, 84,61, "TipoTran", "Tipo", true);
    adicCampo(txtNomeTran, 7, 60, 278,20, "NomeTran", "Nome fantasia", ListaCampos.DB_SI, true);
    adicCampo(txtCnpjTran, 7, 100, 181, 20, "CnpjTran", "Cnpj", ListaCampos.DB_SI, false);
    adicCampo(txtInscTran, 192, 100, 181, 20, "InscTran", "Inscrição Estadual", ListaCampos.DB_SI, false);
	adicCampo( txtCpfTran, 7, 140, 170, 20, "CpfTran", "CPF", ListaCampos.DB_SI, false );
    adicCampo(txtEndTran, 7, 180, 260, 20, "EndTran", "Endereço", ListaCampos.DB_SI, false);
    adicCampo(txtNumTran, 270, 180, 50, 20, "NumTran", "Num.", ListaCampos.DB_SI, false);
    adicCampo(txtComplTran, 323, 180, 49, 20, "ComplTran", "Compl.", ListaCampos.DB_SI, false);
    adicCampo(txtBairTran, 7, 220, 120, 20, "BairTran", "Bairro", ListaCampos.DB_SI, false);
    adicCampo(txtCidTran, 130, 220, 120, 20, "CidTran", "Cidade", ListaCampos.DB_SI, false);
    adicCampo(txtCepTran, 253, 220, 80, 20, "CepTran", "Cep", ListaCampos.DB_SI, false);
    adicCampo(txtUFTran, 336, 220, 36, 20, "UFTran", "UF", ListaCampos.DB_SI, false);
    adicCampo(txtDDDFoneTran, 7, 260, 80, 20, "DDDFoneTran", "DDD", ListaCampos.DB_SI, false);
    adicCampo(txtFoneTran, 90, 260, 98, 20, "FoneTran", "Telefone", ListaCampos.DB_SI, false);
    adicCampo(txtDDDFaxTran, 191, 260, 80, 20, "DDDFaxTran", "DDD", ListaCampos.DB_SI, false);
    adicCampo(txtFaxTran, 273, 260, 100, 20, "FaxTran", "Fax", ListaCampos.DB_SI, false);
    adicCampo(txtContTran,7,300,223,20,"Conttran","Contato", ListaCampos.DB_SI, false);
    adicCampo(txtDDDCelTran,233,300,40,20,"DDDCelTran","DDD", ListaCampos.DB_SI, false);
    adicCampo(txtCelTran,276,300,96,20,"Celtran","Celular",ListaCampos.DB_SI, false);

    txtCnpjTran.setMascara(JTextFieldPad.MC_CNPJ);
	txtCpfTran.setMascara( JTextFieldPad.MC_CPF );
    txtCepTran.setMascara(JTextFieldPad.MC_CEP);
    txtFoneTran.setMascara(JTextFieldPad.MC_FONE);
    txtFaxTran.setMascara(JTextFieldPad.MC_FONE);
    txtCelTran.setMascara(JTextFieldPad.MC_FONE); 
    setListaCampos( true, "TRANSP", "VD");
    lcCampos.setQueryInsert(false);
    
	rgTipoTransp.addRadioGroupListener(this);

    
        
  }  
  public void beforePost(PostEvent pevt) {
    if ( ("".equals( txtCnpjTran.getVlrString().trim())) && ("".equals( txtCpfTran.getVlrString().trim() ) ) ) {
      pevt.cancela();
      Funcoes.mensagemInforma( this,"Campo CNPJ é requerido! ! !");
      txtCnpjTran.requestFocus();
    }
    
    if ( ("".equals(txtInscTran.getVlrString().trim() ) ) && (!"".equals( txtCnpjTran.getVlrString().trim() ) ) ) {
        if (Funcoes.mensagemConfirma(this, "Inscrição Estadual em branco! Inserir ISENTO?")==JOptionPane.OK_OPTION ) {
          txtInscTran.setVlrString("ISENTO");
        }
        pevt.cancela();
        txtInscTran.requestFocus();
        return;
      }    
    else if (txtInscTran.getText().trim().toUpperCase().compareTo("ISENTO") == 0)
      return;
    else if (txtUFTran.getText().trim().length() < 2) {
      pevt.cancela();
      Funcoes.mensagemInforma( this,"Campo UF é requerido! ! !");
      txtUFTran.requestFocus();
    }
    else if ( (!"".equals( txtInscTran.getVlrString().trim() )) && (!Funcoes.vIE(txtInscTran.getText(),txtUFTran.getText()))) {
      pevt.cancela();
      Funcoes.mensagemInforma( this,"Inscrição Estadual Inválida ! ! !");
      txtInscTran.requestFocus();
    }
    txtInscTran.setVlrString(Funcoes.sIEValida);
  }
  public void afterPost(PostEvent pevt) { }
  public void setConexao(Connection cn) {
  	  super.setConexao(cn);
   }
  
  
   public void valorAlterado(RadioGroupEvent rgevt) {
   	  if (rgTipoTransp.getVlrString().equals("C"))
	      carregaCnpj(); 
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
				Funcoes.mensagemErro(this, "Erro ao carregar tabela SGFILIAL!\n"+err.getMessage(),true,con,err);
			    return;
	    }
	  }
	public void afterInsert(InsertEvent ievt) {
		rgTipoTransp.setVlrString("T");
	}
	public void beforeInsert(InsertEvent ievt) {

	}
}	  
   	  
	   

