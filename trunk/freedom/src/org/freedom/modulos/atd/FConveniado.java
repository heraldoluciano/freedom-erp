/**
 * @version 03/01/2005 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.atd <BR>
 * Classe: @(#)FConveniado.java <BR>
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

import java.awt.BorderLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.freedom.acao.PostEvent;
import org.freedom.acao.PostListener;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JComboBoxPad;
import org.freedom.componentes.JGroupField;
import org.freedom.componentes.JTextAreaPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Navegador;
import org.freedom.componentes.Painel;
import org.freedom.componentes.Tabela;
import org.freedom.funcoes.Funcoes;
import org.freedom.modulos.std.FCliente;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FTabDados;



public class FConveniado extends FTabDados implements PostListener { 
  private Painel pinGeral = new Painel(650,520);
  private Painel pinInfo = new Painel(650,520);
  private JPanel pnAtrib = new JPanel(new BorderLayout());
  private JTextFieldPad txtCodConv = new JTextFieldPad(5);
  private JTextFieldPad txtNomeConv = new JTextFieldPad(40);
  private JTextFieldPad txtRgConv = new JTextFieldPad(12);
  private JTextFieldPad txtCPFConv = new JTextFieldPad(14);
  private JTextFieldPad txtIdentificConv = new JTextFieldPad(20);
  private JTextFieldPad txtDtNascConv = new JTextFieldPad(10);
  private JTextFieldPad txtEndConv = new JTextFieldPad(50);
  private JTextFieldPad txtNumConv = new JTextFieldPad(8);
  private JTextFieldPad txtComplConv = new JTextFieldPad(20);
  private JTextFieldPad txtBairConv = new JTextFieldPad(30);
  private JTextFieldPad txtCidConv = new JTextFieldPad(20);
  private JTextFieldPad txtCepConv = new JTextFieldPad(8);
  private JTextFieldPad txtFoneConv = new JTextFieldPad(12);
  private JTextFieldPad txtCelConv = new JTextFieldPad(8);
  private JTextFieldPad txtFaxConv = new JTextFieldPad(8);
  private JTextFieldPad txtUFConv = new JTextFieldPad(2);
  private JTextFieldPad txtEmailConv = new JTextFieldPad(50);
  private JTextFieldPad txtCNSConv = new JTextFieldPad(15);
  private JTextFieldPad txtCodAtrib = new JTextFieldPad();
  private JTextFieldFK txtDescAtrib = new JTextFieldFK();
  private JTextFieldPad txtCodTipoConv = new JTextFieldPad();
  private JTextFieldFK txtDescTipoConv = new JTextFieldFK();
  private JTextFieldPad txtCodCli = new JTextFieldPad();
  private JTextFieldPad txtCodConvAtrib = new JTextFieldPad();
  private JTextFieldFK txtDescCli = new JTextFieldFK();
  private JTextFieldPad txtCodEnc = new JTextFieldPad();
  private JTextFieldFK txtNomeEnc = new JTextFieldFK();
  private JTextFieldPad txtCodGrauInst = new JTextFieldPad();
  private JTextFieldFK txtDescGrauInst = new JTextFieldFK();
  private JTextFieldPad txtPaiConv = new JTextFieldPad(50);
  private JTextFieldPad txtMaeConv = new JTextFieldPad(50);
  private JTextFieldPad txtRGPaiConv = new JTextFieldPad(12);
  private JTextFieldPad txtRGMaeConv = new JTextFieldPad(12);
  private JTextFieldPad txtCodAtend = new JTextFieldPad();
  private JTextFieldFK txtNomeAtend = new JTextFieldFK();
  private Tabela tabAtrib = new Tabela();
  private JScrollPane spnAtrib = new JScrollPane(tabAtrib);
  private JTextAreaPad txaAtrib = new JTextAreaPad();
  private Painel pinRodAtrib = new Painel(650,250);
  private Navegador navAtrib = new Navegador(true);
  private ListaCampos lcTipoConv = new ListaCampos(this,"TC");
  private ListaCampos lcCli = new ListaCampos(this,"CL");
  private ListaCampos lcAtrib = new ListaCampos(this,"AB");
  private ListaCampos lcConvAtrib = new ListaCampos(this,"");
  private ListaCampos lcGrauInst = new ListaCampos(this,"GI");
  private ListaCampos lcAtend = new ListaCampos(this,"AE");
  private ListaCampos lcEnc = new ListaCampos(this,"EC");
  private Vector vVals = new Vector();
  private Vector vLabs = new Vector();
  private JComboBoxPad cbSexo = new JComboBoxPad();
  private JGroupField gfCamp = new JGroupField();
  private Object[] oPrefs;
 
  public FConveniado () {
    setTitulo("Cadastro de Conveniados");
    setAtribos( 50, 10, 530, 450);

	lcCampos.addPostListener(this);

	lcConvAtrib.setMaster(lcCampos);
	lcCampos.adicDetalhe(lcConvAtrib);
	lcConvAtrib.setTabela(tabAtrib);

    lcTipoConv.add(new GuardaCampo( txtCodTipoConv, 80, 20, 80, 20, "CodTpConv", "Código", true, false, txtDescTipoConv,JTextFieldPad.TP_INTEGER,false),"txtCodVendx");
    lcTipoConv.add(new GuardaCampo( txtDescTipoConv, 100, 80, 200, 20, "DescTpConv", "Descriçao", false, false, null, JTextFieldPad.TP_STRING,false),"txtCodVendx");
    lcTipoConv.montaSql(false, "TIPOCONV", "AT");    
    lcTipoConv.setQueryCommit(false);
    lcTipoConv.setReadOnly(true);
    txtCodTipoConv.setTabelaExterna(lcTipoConv);
    
	lcGrauInst.add(new GuardaCampo( txtCodGrauInst, 80, 20, 80, 20, "CodGri", "Código", true, false, txtDescGrauInst,JTextFieldPad.TP_INTEGER,false),"txtCodVendx");
	lcGrauInst.add(new GuardaCampo( txtDescGrauInst, 100, 80, 200, 20, "DescGri", "Descriçao", false, false, null, JTextFieldPad.TP_STRING,false),"txtCodVendx");
	lcGrauInst.montaSql(false, "GRAUINST", "SG");    
	lcGrauInst.setQueryCommit(false);
	lcGrauInst.setReadOnly(true);
	txtCodGrauInst.setTabelaExterna(lcGrauInst);

	lcCli.add(new GuardaCampo( txtCodCli, 80, 20, 80, 20, "CodCli", "Código", true, false, txtDescCli,JTextFieldPad.TP_INTEGER,false),"txtCodVendx");
	lcCli.add(new GuardaCampo( txtDescCli, 100, 80, 200, 20, "NomeCli", "Nome", false, false, null, JTextFieldPad.TP_STRING,false),"txtCodVendx");
	lcCli.montaSql(false, "CLIENTE", "VD");    
	lcCli.setQueryCommit(false);
	lcCli.setReadOnly(true);
	txtCodCli.setTabelaExterna(lcCli);
	
	lcAtend.add(new GuardaCampo( txtCodAtend, 80, 20, 80, 20, "CodAtend", "Código", true, false, txtNomeAtend,JTextFieldPad.TP_INTEGER,false),"txtCodVendx");
	lcAtend.add(new GuardaCampo( txtNomeAtend, 100, 80, 200, 20, "NomeAtend", "Nome", false, false, null, JTextFieldPad.TP_STRING,false),"txtCodVendx");
	lcAtend.montaSql(false, "ATENDENTE", "AT");    
	lcAtend.setQueryCommit(false);
	lcAtend.setReadOnly(true);
	txtCodAtend.setTabelaExterna(lcAtend);

	lcEnc.add(new GuardaCampo( txtCodEnc, 100, 40, 100, 40, "CodEnc", "Código", true, false, txtNomeEnc,JTextFieldPad.TP_INTEGER,false),"txtCodVendx");
	lcEnc.add(new GuardaCampo( txtNomeEnc, 120, 100, 220, 40, "NomeEnc", "Nome", false, false, null, JTextFieldPad.TP_STRING,false),"txtCodVendx");
	lcEnc.montaSql(false, "ENCAMINHADOR", "AT");    
	lcEnc.setQueryCommit(false);
	lcEnc.setReadOnly(true);
	txtCodEnc.setTabelaExterna(lcEnc);	
	
	vVals.add("M");
	vVals.add("F");
	vLabs.add("Masculino");
	vLabs.add("Feminino");
	cbSexo.setItens(vLabs,vVals);

	setPainel(pinGeral);
	adicTab("Geral",pinGeral);
	
    adicCampo(txtCodConv, 7, 20, 50, 20, "CodConv", "Código", JTextFieldPad.TP_INTEGER, 5, 0, true, false, null, true);
    adicCampo(txtNomeConv, 60, 20, 297, 20, "NomeConv", "Nome", JTextFieldPad.TP_STRING, 50, 0, false, false, null, true);
    adicDB(cbSexo,360,18,140,24,"SexoConv","Sexo",JTextFieldPad.TP_STRING,true);
    adicCampo(txtDtNascConv, 7, 60, 120, 20, "DtNascConv", "Data de Nascimento", JTextFieldPad.TP_DATE, 10, 0, false, false, null, true);
    adicCampo(txtRgConv, 130, 60, 117, 20, "RgConv", "RG", JTextFieldPad.TP_STRING, 12, 0, false, false, null, false);
	adicCampo(txtCPFConv, 250, 60, 117, 20, "CPFConv", "CPF", JTextFieldPad.TP_STRING, 11, 0, false, false, null, false);
	adicCampo(txtIdentificConv, 370, 60, 130, 20, "IdentificConv", "Nº. Identificação", JTextFieldPad.TP_STRING, 20, 0, false, false, null, false);
    adicCampo(txtEndConv, 7, 100, 250, 20, "EndConv", "Endereço", JTextFieldPad.TP_STRING, 50, 0, false, false, null, false);
    adicCampo(txtNumConv, 260, 100, 57, 20, "NumConv", "Num.", JTextFieldPad.TP_INTEGER, 8, 0, false, false, null, false);
	adicCampo(txtComplConv, 320, 100, 47, 20, "ComplConv", "Compl.", JTextFieldPad.TP_STRING, 20, 0, false, false, null, false);
	adicCampo(txtCepConv, 370, 100, 130, 20, "CepConv", "Cep", JTextFieldPad.TP_STRING, 8, 0, false, false, null, false);
    adicCampo(txtBairConv, 7, 140, 120, 20, "BairConv", "Bairro", JTextFieldPad.TP_STRING, 30, 0, false, false, null, false);
    adicCampo(txtCidConv, 130, 140, 117, 20, "CidConv", "Cidade", JTextFieldPad.TP_STRING, 30, 0, false, false, null, false);
    adicCampo(txtUFConv, 250, 140, 37, 20, "UFConv", "UF", JTextFieldPad.TP_STRING, 2, 0, false, false, null, false);
    adicCampo(txtFoneConv, 290, 140, 117, 20, "FoneConv", "Telefone", JTextFieldPad.TP_STRING, 12, 0, false, false, null, false);
    adicCampo(txtFaxConv, 410, 140, 90, 20, "Faxconv", "Fax", JTextFieldPad.TP_STRING, 8, 0, false, false, null, false);
    adicCampo(txtCelConv, 7, 180, 90, 20, "CelConv", "Cel", JTextFieldPad.TP_STRING, 8, 0, false, false, null, false);
	adicCampo(txtCodGrauInst, 100, 180, 97, 20, "CodGri", "Código", JTextFieldPad.TP_INTEGER, 8, 0, false, true, txtDescGrauInst,false);
	adicDescFK(txtDescGrauInst, 200, 180, 300, 20, "DescGri", "e descrição do grau de instrução", JTextFieldPad.TP_STRING, 50, 0);
	adicCampo(txtCodTipoConv, 7, 220, 100, 20, "CodTpConv", "Código", JTextFieldPad.TP_INTEGER, 8, 0, false, true, txtDescTipoConv,true);
	adicDescFK(txtDescTipoConv, 110, 220, 390, 20, "DescTpConv", "e descrição do tipo de conveniado", JTextFieldPad.TP_STRING, 50, 0);
	adicCampo(txtCodCli, 7, 260, 100, 20, "CodCli", "Código", JTextFieldPad.TP_INTEGER, 8, 0, false, true, txtDescCli,true);
	adicDescFK(txtDescCli, 110, 260, 390, 20, "DescCli", "e nome do cliente", JTextFieldPad.TP_STRING, 50, 0);
    adicCampo(txtEmailConv, 7, 300, 200, 20, "EmailConv", "E-Mail", JTextFieldPad.TP_STRING, 50, 0, false, false, null, false);
    adicCampo(txtCNSConv, 210, 300, 200, 20, "CnsConv","CNS-Sus", JTextFieldPad.TP_STRING, 15, 0, false, false, null, false);
	txtRgConv.setMascara(JTextFieldPad.MC_RG);
	txtCPFConv.setMascara(JTextFieldPad.MC_CPF);
    txtCepConv.setMascara(JTextFieldPad.MC_CEP);
    txtFoneConv.setMascara(JTextFieldPad.MC_FONEDDD);
    txtCelConv.setMascara(JTextFieldPad.MC_FONE);
    txtFaxConv.setMascara(JTextFieldPad.MC_FONE);
    
//	Informações complementares

	setPainel(pinInfo);
	adicTab("Inf. complementares",pinInfo);
	adicCampo(txtCodAtend, 7, 20, 100, 20, "CodAtend", "Código", JTextFieldPad.TP_INTEGER, 8, 0, false, true, txtNomeAtend,true);
	adicDescFK(txtNomeAtend, 110, 20, 390, 20, "NomeAtend", "e descrição do atendente", JTextFieldPad.TP_STRING, 50, 0);
	adicCampo(txtCodEnc, 7, 60, 100, 20, "CodEnc", "Código", JTextFieldPad.TP_INTEGER, 50, 0, false, true, null, false);
	adicDescFK(txtNomeEnc, 110, 60, 390, 20, "NomeEnc", "e nome do encaminhador", JTextFieldPad.TP_STRING, 50, 0); 
	adicCampo(txtPaiConv, 7, 100, 240, 20, "PaiConv", "Pai", JTextFieldPad.TP_STRING, 50, 0, false, false, null, false);
	adicCampo(txtMaeConv, 250, 100, 250, 20, "MaeConv", "Mãe", JTextFieldPad.TP_STRING, 50, 0, false, false, null, false);
	adicCampo(txtRGPaiConv, 7, 140, 150, 20, "RGPaiConv", "RG Pai", JTextFieldPad.TP_STRING, 12, 0, false, false, null, false);
	adicCampo(txtRGMaeConv, 160, 140, 147, 20, "RGMaeConv", "RG Mãe", JTextFieldPad.TP_STRING, 12, 0, false, false, null, false);

	setListaCampos( true, "CONVENIADO", "AT");

//Atribuições

	setPainel( pinRodAtrib, pnAtrib);
	adicTab("Atribuições",pnAtrib);
	setListaCampos(lcConvAtrib);
	setNavegador(navAtrib);
	pnAtrib.add(pinRodAtrib, BorderLayout.SOUTH);
	pnAtrib.add(spnAtrib, BorderLayout.CENTER);

	pinRodAtrib.adic(navAtrib,0,222,270,25);

	txtCodAtrib.setTipo(JTextFieldPad.TP_STRING,15,0);
	lcAtrib.add(new GuardaCampo( txtCodAtrib, 7, 100, 80, 20, "CodAtrib", "Código", true, false, null, JTextFieldPad.TP_STRING,false),"txtCodMarcax");
	lcAtrib.add(new GuardaCampo( txtDescAtrib, 90, 100, 207, 20, "DescAtrib", "Descrição", false, false, null, JTextFieldPad.TP_STRING,false),"txtDescMarcax");
	lcAtrib.montaSql(false, "ATRIBUICAO", "AT");
	lcAtrib.setQueryCommit(false);
	lcAtrib.setReadOnly(true);
	txtDescAtrib.setListaCampos(lcAtrib);
	txtCodAtrib.setTabelaExterna(lcAtrib);

	adicCampo(txtCodConvAtrib, 7, 20, 80, 20, "SeqConvAtrib", "NºSeq.", JTextFieldPad.TP_INTEGER, 8, 0, true, false, null,true);
	adicCampo(txtCodAtrib, 90, 20, 77, 20, "CodAtrib", "Código", JTextFieldPad.TP_STRING, 15, 0, false, true, txtDescAtrib,false);
	adicDescFK(txtDescAtrib, 170, 20, 197, 20, "DescAtrib", "e descrição da atribuição.", JTextFieldPad.TP_STRING, 40, 0);
	adicDB(txaAtrib, 7, 60, 360, 60, "ObsConvAtrib", "Obs.",JTextFieldPad.TP_STRING,false);
	adic(gfCamp,7,130,360,80);
	setListaCampos( true, "CONVATRIB", "AT");
	lcConvAtrib.setQueryInsert(false);
	lcConvAtrib.setQueryCommit(false);
	lcConvAtrib.montaTab();
	tabAtrib.setTamColuna(65,0);
	tabAtrib.setTamColuna(60,1);
	tabAtrib.setTamColuna(110,2);
    
    lcCampos.setQueryInsert(false);    
  }
  public void execShow(Connection cn) {
    lcTipoConv.setConexao(cn); 
	lcAtrib.setConexao(cn);
	lcCli.setConexao(cn);
	lcConvAtrib.setConexao(cn);
	lcGrauInst.setConexao(cn);
	lcAtend.setConexao(cn);
	lcEnc.setConexao(cn);
	
	gfCamp.setCampos("ATCONVATRIBTB","CodConv","SeqConvAtrib",lcConvAtrib,cn);
	
    super.execShow(cn);
    oPrefs = prefs(); 
  }
  public void setCodcli(String sCodcli, String sRazCli) {
  	txtCodCli.setVlrString(sCodcli); 
  	txtDescCli.setVlrString(sRazCli);
  	lcCampos.post();
  }
  public void beforePost(PostEvent pevt) {
  	Integer iCodTipoCli = null;
  	Integer iCodClasCli = null;
	if (txtCodCli.getText().equals("")) {
		if (Funcoes.mensagemConfirma(this,"Cliente não selecionado!\nDeseja cadastrar?")==JOptionPane.YES_OPTION) {
			if (oPrefs!=null) {
				iCodTipoCli = (Integer) oPrefs[0];
				iCodClasCli = (Integer) oPrefs[1];
			}
			pevt.cancela();
			FCliente tela; 
			if (Aplicativo.telaPrincipal.temTela("Clientes")==false) {
				tela = new FCliente();
			    Aplicativo.telaPrincipal.criatela("Clientes",tela,con);
			}
			else {
				tela = (FCliente) Aplicativo.telaPrincipal.getTela("Clientes");
			}
			tela.setConveniado(this);
			tela.lcCampos.cancel(false);
			tela.lcCampos.insert(true);
			tela.setVlrConveniado(txtNomeConv.getVlrString(),txtNomeConv.getVlrString(),
			   txtEndConv.getVlrString(), txtNumConv.getVlrInteger(), txtComplConv.getVlrString(), 
			   txtBairConv.getVlrString(), txtCidConv.getVlrString(),  txtCepConv.getVlrString(),
			   txtUFConv.getVlrString(), txtRgConv.getVlrString(), txtCPFConv.getVlrString(),
			   txtFoneConv.getVlrString(), txtFaxConv.getVlrString(), txtEmailConv.getVlrString(),
			   iCodTipoCli,iCodClasCli);
			
		}
	}
  }
  private Object[] prefs() {
	Object[] bRetorno = new Object[2];
	String sSQL = "SELECT CODTIPOCLI,CODCLASCLI FROM SGPREFERE2 WHERE CODEMP=? AND CODFILIAL=?";
	PreparedStatement ps = null;
	ResultSet rs = null;
	try {
	  ps = con.prepareStatement(sSQL);
	  ps.setInt(1,Aplicativo.iCodEmp);
	  ps.setInt(2,ListaCampos.getMasterFilial("SGPREFERE2"));
	  rs = ps.executeQuery();
	  if (rs.next()) {
	  	 bRetorno[0] = new Integer(rs.getInt("CODTIPOCLI"));
	  	 bRetorno[1] = new Integer(rs.getInt("CODCLASCLI"));
	  }
//		rs.close();
//		ps.close();
	  if (!con.getAutoCommit())
	  	con.commit();
	}
	catch (SQLException err) {
		Funcoes.mensagemErro(this,"Erro ao carregar a tabela SGPREFERE2!\n"+err.getMessage());
	}
	return bRetorno;
  }

}