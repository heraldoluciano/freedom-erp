/**
 * @version 21/11/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FCredCli.java <BR>
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
 * Tela ficha cadastral e crédito de cliente.
 * 
 */

package org.freedom.modulos.std;
import java.awt.event.ActionListener;
import java.sql.Connection;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Painel;
import org.freedom.telas.FTabDados;

public class FCredCli extends FTabDados implements ActionListener {
  private Painel pinGeral = new Painel(330, 200);
  private Painel pinFicha = new Painel(330, 200);
  private JTextFieldPad txtCodCli = new JTextFieldPad(JTextFieldPad.TP_INTEGER,5,0);
 // private JTextFieldPad txtDescCli = new JTextFieldPad(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldFK txtDataCli = new JTextFieldFK(JTextFieldPad.TP_DATE,10,0);
  private JTextFieldPad txtCodTipoCli = new JTextFieldPad(JTextFieldPad.TP_INTEGER,5,0);
  private JTextFieldFK txtDescTipoCli = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldPad txtCodTpCred = new JTextFieldPad(JTextFieldPad.TP_INTEGER,5,0);
  private JTextFieldFK txtDescTpCred = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldFK txtVlrTpCred = new JTextFieldFK(JTextFieldPad.TP_DECIMAL,15,3);
  private JTextFieldPad txtDtIniTr = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
  private JTextFieldPad txtDtVencto = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
  private JTextFieldPad txtRazCli = new JTextFieldPad(JTextFieldPad.TP_STRING, 50, 0);
  private JTextFieldPad txtNomeCli = new JTextFieldPad(JTextFieldPad.TP_STRING, 40, 0);
  private JTextFieldPad txtCnpjCli = new JTextFieldPad(JTextFieldPad.TP_STRING, 14, 0);
  private JTextFieldPad txtInscCli = new JTextFieldPad(JTextFieldPad.TP_STRING, 15, 0);
  private JTextFieldPad txtCpfCli = new JTextFieldPad(JTextFieldPad.TP_STRING, 11, 0);
  private JTextFieldPad txtRgCli = new JTextFieldPad(JTextFieldPad.TP_STRING, 10, 0);
  private JTextFieldPad txtEndCli = new JTextFieldPad(JTextFieldPad.TP_STRING, 50, 0); 
  private JTextFieldPad txtNumCli = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
  private JTextFieldPad txtComplCli = new JTextFieldPad(JTextFieldPad.TP_STRING, 20, 0);
  private JTextFieldPad txtBairCli = new JTextFieldPad(JTextFieldPad.TP_STRING, 30, 0);
  private JTextFieldPad txtCidCli = new JTextFieldPad(JTextFieldPad.TP_STRING, 30, 0);
  private JTextFieldPad txtUFCli = new JTextFieldPad(JTextFieldPad.TP_STRING, 2, 0);
  private JTextFieldPad txtCepCli = new JTextFieldPad(JTextFieldPad.TP_STRING, 8, 0);
  private JTextFieldPad txtFoneCli = new JTextFieldPad(JTextFieldPad.TP_STRING, 12, 0);
  private JTextFieldPad txtRamalCli = new JTextFieldPad(JTextFieldPad.TP_STRING, 8, 0);
  private JTextFieldPad txtFaxCli = new JTextFieldPad(JTextFieldPad.TP_STRING, 8, 0);
  private JTextFieldPad txtCelCli = new JTextFieldPad(JTextFieldPad.TP_STRING, 8, 0);
  
  private ListaCampos lcTipoCred = new ListaCampos(this,"TR");
  private ListaCampos lcTipoCli = new ListaCampos(this,"TI");
  public FCredCli() {
    setTitulo("Ficha cadastral/Crédito por cliente");
    setAtribos(50, 10, 600, 520);

    lcTipoCli.add(new GuardaCampo( txtCodTipoCli, "CodTipoCli", "Cód.tp.cli.", ListaCampos.DB_PK, true));
    lcTipoCli.add(new GuardaCampo( txtDescTipoCli, "DescTipoCli", "Descrição do tipo de cliente", ListaCampos.DB_SI, false));
    lcTipoCli.montaSql(false, "TIPOCLI", "VD");    
    lcTipoCli.setQueryCommit(false);
    lcTipoCli.setReadOnly(true);
    txtCodTipoCli.setTabelaExterna(lcTipoCli);

    lcTipoCred.add(new GuardaCampo( txtCodTpCred, "CodTpCred", "Cód.tp.cred.", ListaCampos.DB_PK, true));
    lcTipoCred.add(new GuardaCampo( txtDescTpCred, "DescTpCred", "Descrição do tipo de crédito", ListaCampos.DB_SI, false));
    lcTipoCred.add(new GuardaCampo( txtVlrTpCred, "VlrTpCred", "Valor", ListaCampos.DB_SI, false));
    lcTipoCred.montaSql(false, "TIPOCRED", "FN");    
    lcTipoCred.setQueryCommit(false);
    lcTipoCred.setReadOnly(true);
    txtCodTpCred.setTabelaExterna(lcTipoCred);

	setPainel(pinGeral);
	adicTab("Crédito", pinGeral);
    adicCampo(txtCodCli, 7, 60, 80, 20,"CodCli","Cód.cli.", ListaCampos.DB_PK, true);
    adicCampo(txtRazCli, 90, 60, 387, 20,"RazCli","Razão social do cliente", ListaCampos.DB_SI, false);
    adicCampo(txtDataCli, 480, 60, 95, 20,"DataCli","Cadastro", ListaCampos.DB_SI, false);
	adicCampoInvisivel(txtCodTipoCli, "CodTipoCli","Cód.tp.cli", ListaCampos.DB_FK, txtDescTipoCli,false);

  	adicCampo(txtEndCli, 7, 100, 307, 20, "EndCli", "Endereço", ListaCampos.DB_SI, false);
  	adicCampo(txtNumCli, 400, 100, 77, 20, "NumCli", "Num.", ListaCampos.DB_SI, false);
  	adicCampo(txtComplCli, 480, 100, 70, 20, "ComplCli", "Compl.", ListaCampos.DB_SI, false);
  	adicCampo(txtBairCli, 7, 140, 180, 20, "BairCli", "Bairro", ListaCampos.DB_SI, false);
  	adicCampo(txtCidCli, 190, 140, 177, 20, "CidCli", "Cidade", ListaCampos.DB_SI, false);
  	adicCampo(txtCepCli, 370, 140, 77, 20, "CepCli", "Cep", ListaCampos.DB_SI, false);
  	adicCampo(txtUFCli, 450, 140, 50, 20, "UFCli", "UF", ListaCampos.DB_SI, false);
  	adicCampo(txtFoneCli, 7, 180, 100, 20, "FoneCli", "Telefone", ListaCampos.DB_SI, false);
  	adicCampo(txtRamalCli, 110, 180, 44, 20, "FaxCli", "Fax", ListaCampos.DB_SI, false); 	
  	adicCampo(txtFaxCli, 157, 180, 77, 20, "FaxCli", "Fax", ListaCampos.DB_SI, false);
  	adicCampo(txtCelCli, 7, 220, 100, 20, "CelCli", "Celular",ListaCampos.DB_SI, false);
	
	
	adicDescFK(txtDescTipoCli, 7, 300, 250, 20, "DescTipoCli", "Descrição do tipo de cliente");
    adicCampo(txtDtIniTr, 450, 20, 90, 20,"DtIniTr","Dt.ab.créd.", ListaCampos.DB_SI, false);

	
    adicCampo(txtCodTpCred, 7, 20, 80, 20,"CodTpCred","Cód.tp.cred", ListaCampos.DB_FK, txtDescTpCred, true);
    adicDescFK(txtDescTpCred, 90, 20, 157, 20, "DescTpCred", "Descrição do crédito");
    adicDescFK(txtVlrTpCred, 250, 20, 87, 20, "VlrTpCred", "Valor");
	adicCampo(txtDtVencto, 340, 20, 100, 20,"DtVenctoTr","Vencimento", ListaCampos.DB_SI, txtDescTpCred, true);
	
	
	
	
	setListaCampos( true, "CLIENTE", "VD");
	lcCampos.setPodeIns(false);
	lcCampos.setPodeExc(false);
    lcCampos.setQueryInsert(false);
    
    
	setPainel(pinFicha);
	adicTab("Ficha cadastral", pinFicha);
    
    
    
  }
  public void execShow(Connection cn) {
    lcTipoCli.setConexao(cn);
    lcTipoCred.setConexao(cn);
    super.execShow(cn);
  }        
}
