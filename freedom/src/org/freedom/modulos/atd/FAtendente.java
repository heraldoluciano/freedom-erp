/**
 * @version 02/11/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.atd <BR>
 * Classe: @(#)FAtendente.java <BR>
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

package org.freedom.modulos.atd;
import java.sql.Connection;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.telas.FDados;

public class FAtendente extends FDados { 
  private JTextFieldPad txtCodAtend = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtNomeAtend = new JTextFieldPad(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldPad txtRgAtend = new JTextFieldPad(JTextFieldPad.TP_STRING,10,0);
  private JTextFieldPad txtCpfAtend = new JTextFieldPad(JTextFieldPad.TP_STRING,11,0);
  private JTextFieldPad txtIdentificAtend = new JTextFieldPad(JTextFieldPad.TP_STRING,18,0);
  private JTextFieldPad txtEndAtend = new JTextFieldPad(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldPad txtNumAtend = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtBairAtend = new JTextFieldPad(JTextFieldPad.TP_STRING,30,0);
  private JTextFieldPad txtCidAtend = new JTextFieldPad(JTextFieldPad.TP_STRING,30,0);
  private JTextFieldPad txtCepAtend = new JTextFieldPad(JTextFieldPad.TP_STRING,8,0);
  private JTextFieldPad txtFoneAtend = new JTextFieldPad(JTextFieldPad.TP_STRING,12,0);
  private JTextFieldPad txtCelAtend = new JTextFieldPad(JTextFieldPad.TP_STRING,8,0);
  private JTextFieldPad txtFaxAtend = new JTextFieldPad(JTextFieldPad.TP_STRING,8,0);
  private JTextFieldPad txtUFAtend = new JTextFieldPad(JTextFieldPad.TP_STRING,2,0);
  private JTextFieldPad txtEmailAtend = new JTextFieldPad(JTextFieldPad.TP_STRING,60,0);
  private JTextFieldPad txtCodTipoAtend = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldFK txtDescTipoAtend = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldPad txtIDUsu = new JTextFieldPad(JTextFieldPad.TP_STRING,8,0);
  private JTextFieldFK txtNomeUsu = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldPad txtCodVend = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldFK txtDescVend = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
  private ListaCampos lcTipoAtend = new ListaCampos(this,"TA");
  private ListaCampos lcUsu = new ListaCampos(this,"US");
  private ListaCampos lcVend = new ListaCampos(this,"VE");
  public FAtendente () {
  	super();
    setTitulo("Cadastro de Atendentes");
    setAtribos( 50, 20, 500, 440);

    lcTipoAtend.add(new GuardaCampo( txtCodTipoAtend, "CodTpAtend", "Cód.tp.atend.", ListaCampos.DB_PK, false),"txtCodVendx");
    lcTipoAtend.add(new GuardaCampo( txtDescTipoAtend, "DescTpAtend", "Descriçao do tipo de atendente", ListaCampos.DB_SI,false),"txtCodVendx");
    lcTipoAtend.montaSql(false, "TIPOATEND", "AT");    
    lcTipoAtend.setQueryCommit(false);
    lcTipoAtend.setReadOnly(true);
    txtCodTipoAtend.setTabelaExterna(lcTipoAtend);
    
    lcVend.add(new GuardaCampo( txtCodVend, "CodVend", "Cód.comis.",ListaCampos.DB_PK, false));
    lcVend.add(new GuardaCampo( txtDescVend, "NomeVend", "Nome do comissionado",ListaCampos.DB_SI, false));
    lcVend.montaSql(false, "VENDEDOR", "VD");    
    lcVend.setQueryCommit(false);
    lcVend.setReadOnly(true);
    txtCodVend.setTabelaExterna(lcVend);
    
    lcUsu.add(new GuardaCampo( txtIDUsu, "IdUsu", "ID", ListaCampos.DB_PK, false));
	lcUsu.add(new GuardaCampo( txtNomeUsu, "NomeUsu", "Nome do usuário", ListaCampos.DB_SI,false));
	lcUsu.montaSql(false, "USUARIO", "SG");    
	lcUsu.setQueryCommit(false);
	lcUsu.setReadOnly(true);
	txtIDUsu.setTabelaExterna(lcUsu);

    adicCampo(txtCodAtend, 7, 20, 80, 20, "CodAtend", "Cód.atend.", ListaCampos.DB_PK, true);
    adicCampo(txtNomeAtend, 90, 20, 370, 20, "NomeAtend", "Nome do atendente", ListaCampos.DB_SI, true);
	adicCampo(txtCpfAtend, 7, 60, 150, 20, "CpfAtend", "CPF", ListaCampos.DB_SI, false);
    adicCampo(txtIdentificAtend, 160, 60, 150, 20, "IdentificAtend", "Identificação", ListaCampos.DB_SI, false);
    adicCampo(txtRgAtend, 313, 60, 149, 20, "RgAtend", "RG", ListaCampos.DB_SI, false);
    adicCampo(txtEndAtend, 7, 100, 360, 20, "EndAtend", "Endereço", ListaCampos.DB_SI, false);
    adicCampo(txtNumAtend, 370, 100, 92, 20, "NumAtend", "Número", ListaCampos.DB_SI, false);
    adicCampo(txtBairAtend, 7, 140, 165, 20, "BairAtend", "Bairro", ListaCampos.DB_SI, false);
    adicCampo(txtCidAtend, 175, 140, 165, 20, "CidAtend", "Cidade", ListaCampos.DB_SI, false);
    adicCampo(txtCepAtend, 343, 140, 80, 20, "CepAtend", "Cep", ListaCampos.DB_SI, false);
    adicCampo(txtUFAtend, 426, 140, 36, 20, "UFAtend", "UF", ListaCampos.DB_SI,  false);
    adicCampo(txtFoneAtend, 7, 180, 150, 20, "FoneAtend", "Telefone", ListaCampos.DB_SI, false);
    adicCampo(txtFaxAtend, 160, 180, 150, 20, "FaxAtend", "Fax", ListaCampos.DB_SI, false);
    adicCampo(txtCelAtend, 313, 180, 149, 20, "CelAtend", "Cel", ListaCampos.DB_SI, false);
	adicCampo(txtCodTipoAtend, 7, 220, 100, 20, "CodTpAtend", "Cód.tp.atend.", ListaCampos.DB_FK, txtDescTipoAtend,true);
	adicDescFK(txtDescTipoAtend, 110, 220, 352, 20, "DescTpAtend", "Descrição do tipo de atendente");
	adicCampo(txtIDUsu, 7, 260, 100, 20, "IdUsu", "ID", ListaCampos.DB_FK, txtNomeUsu, false);
	adicDescFK(txtNomeUsu, 110, 260, 352, 20, "NomeUsu", "Nome do usuário");
	adicCampo(txtCodVend, 7, 300, 100, 20, "CodVend", "Cód.comis.", ListaCampos.DB_FK, txtDescVend, false);
	adicDescFK(txtDescVend, 110, 300, 352, 20, "NomeVend", "Nome do comissionado");
	adicCampo(txtEmailAtend, 7, 340, 340, 20, "EmailAtend", "E-Mail", ListaCampos.DB_SI, false);
    txtRgAtend.setMascara(JTextFieldPad.MC_RG);
    txtCepAtend.setMascara(JTextFieldPad.MC_CEP);
    txtFoneAtend.setMascara(JTextFieldPad.MC_FONEDDD);
    txtCelAtend.setMascara(JTextFieldPad.MC_FONE);
    txtFaxAtend.setMascara(JTextFieldPad.MC_FONE);
    setListaCampos( true, "ATENDENTE", "AT");
    lcCampos.setQueryInsert(false);    
  }
  public void setConexao(Connection cn) {
  	super.setConexao(cn);
    lcTipoAtend.setConexao(cn);
    lcUsu.setConexao(cn);
    lcVend.setConexao(cn);
  	
  }
}
