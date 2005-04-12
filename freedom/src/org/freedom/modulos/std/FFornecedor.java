/**
 * @version 11/02/2002 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FFornecedor.java <BR>
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
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import org.freedom.componentes.JPanelPad;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import org.freedom.acao.InsertEvent;
import org.freedom.acao.InsertListener;
import org.freedom.acao.PostEvent;
import org.freedom.acao.PostListener;
import org.freedom.acao.RadioGroupEvent;
import org.freedom.acao.RadioGroupListener;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextAreaPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FAndamento;
import org.freedom.telas.FTabDados;

public class FFornecedor extends FTabDados implements RadioGroupListener, PostListener, InsertListener {
  private JPanelPad pinFor = new JPanelPad();
  private JTextFieldPad txtCodFor = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 5, 0);
  private JTextFieldPad txtRazFor = new JTextFieldPad(JTextFieldPad.TP_STRING, 50, 0);
  private JTextFieldPad txtNomeFor = new JTextFieldPad(JTextFieldPad.TP_STRING, 50, 0);
  private JTextFieldPad txtCodTipoFor = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
  private JTextFieldFK  txtDescTipoFor = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);
  private JTextFieldPad txtCnpjFor = new JTextFieldPad(JTextFieldPad.TP_STRING, 14, 0);
  private JTextFieldPad txtInscFor = new JTextFieldPad(JTextFieldPad.TP_STRING, 15, 0);
  private JTextFieldPad txtCpfFor = new JTextFieldPad(JTextFieldPad.TP_STRING, 11, 0);
  private JTextFieldPad txtRgFor = new JTextFieldPad(JTextFieldPad.TP_STRING, 10, 0);
  private JTextFieldPad txtEndFor = new JTextFieldPad(JTextFieldPad.TP_STRING, 50, 0);
  private JTextFieldPad txtNumFor = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
  private JTextFieldPad txtComplFor = new JTextFieldPad(JTextFieldPad.TP_STRING, 20, 0);
  private JTextFieldPad txtBairFor = new JTextFieldPad(JTextFieldPad.TP_STRING, 30, 0);
  private JTextFieldPad txtUFFor = new JTextFieldPad(JTextFieldPad.TP_STRING, 2, 0);
  private JTextFieldPad txtCidFor = new JTextFieldPad(JTextFieldPad.TP_STRING, 30, 0);
  private JTextFieldPad txtCepFor = new JTextFieldPad(JTextFieldPad.TP_STRING, 8, 0);
  private JTextFieldPad txtFoneFor = new JTextFieldPad(JTextFieldPad.TP_STRING, 12, 0);
  private JTextFieldPad txtFaxFor = new JTextFieldPad(JTextFieldPad.TP_STRING, 8, 0);
  private JTextFieldPad txtDDDFoneFor = new JTextFieldPad(JTextFieldPad.TP_STRING, 4, 0);
  private JTextFieldPad txtDDDFaxFor = new JTextFieldPad(JTextFieldPad.TP_STRING, 4, 0);
  private JTextFieldPad txtDDDCelFor = new JTextFieldPad(JTextFieldPad.TP_STRING, 4, 0);
  private JTextFieldPad txtEmailFor = new JTextFieldPad(JTextFieldPad.TP_STRING, 50, 0);
  private JTextFieldPad txtContFor = new JTextFieldPad(JTextFieldPad.TP_STRING, 40, 0);
  private JTextFieldPad txtCelFor = new JTextFieldPad(JTextFieldPad.TP_STRING, 8, 0);
  private JCheckBoxPad cbAtivo = new JCheckBoxPad("Ativo","S","N");
  private Vector vPessoaLab = new Vector();
  private Vector vPessoaVal = new Vector();
  private JRadioGroup rgPessoa = null;
  private JPanelPad pnObs = new JPanelPad(JPanelPad.TP_JPANEL,new GridLayout(1,1));
  private JTextAreaPad txaObs = new JTextAreaPad();
  private JScrollPane spnObs = new JScrollPane(txaObs);
  private ListaCampos lcTipoFor = new ListaCampos(this,"TF");
  private boolean[] prefs = null;
  public FFornecedor() {
    setTitulo("Cadastro de Fornecedores");
    setAtribos(50, 20, 480, 430);
    
  }
  private void montaTela() {
    lcCampos.addPostListener(this);
    lcCampos.addInsertListener(this);
    
    
    lcTipoFor.add(new GuardaCampo( txtCodTipoFor, "CodTipoFor", "Cód.tp.for.", ListaCampos.DB_PK, true));
    lcTipoFor.add(new GuardaCampo( txtDescTipoFor, "DescTipoFor", "Descrição do tipo de fornecedor", ListaCampos.DB_SI, false));
    lcTipoFor.montaSql(false, "TIPOFOR", "CP");    
    lcTipoFor.setQueryCommit(false);
    lcTipoFor.setReadOnly(true);
    txtCodTipoFor.setTabelaExterna(lcTipoFor);
    
    vPessoaLab.addElement("Física");
    vPessoaLab.addElement("Jurídica");
    vPessoaVal.addElement("F");
    vPessoaVal.addElement("J");
    rgPessoa = new JRadioGroup( 2, 1, vPessoaLab, vPessoaVal);
    rgPessoa.addRadioGroupListener(this);   
    
    pinFor = new JPanelPad(470,300);
    setPainel(pinFor);
    adicTab("Fornecedor", pinFor); 
    adicCampo(txtCodFor, 7, 20, 70, 20, "CodFor", "Cód.for.", ListaCampos.DB_PK, true);
    adicCampo(txtRazFor, 80, 20, 267, 20, "RazFor", "Razão social do fornecedor", ListaCampos.DB_SI, true);
    adicDB(rgPessoa, 350, 20, 100, 60, "PessoaFor", "Pessoa",true);
	adicDB(cbAtivo, 7, 60, 70, 20, "AtivoFor", "Ativo",true);
    adicCampo(txtNomeFor, 80, 60, 267, 20, "NomeFor", "Nome fantasia", ListaCampos.DB_SI, true);
    adicCampo(txtCodTipoFor, 7, 100, 70, 20, "CodTipoFor", "Cód.tp.for.", ListaCampos.DB_FK, txtDescTipoFor, true);
    adicDescFK(txtDescTipoFor, 80, 100, 207, 20, "DescTipoFor", "Descrição do tipo de Fornecedor");
    adicCampo(txtCpfFor, 290, 100, 160, 20, "CpfFor", "CPF", ListaCampos.DB_SI, false);
    adicCampo(txtRgFor, 7, 140, 150, 20, "RgFor", "RG", ListaCampos.DB_SI, false);
    adicCampo(txtCnpjFor, 160, 140, 147, 20, "CnpjFor", "Cnpj", ListaCampos.DB_SI, false);
    adicCampo(txtInscFor, 310, 140, 140, 20, "InscFor", "Inscrição Estadual", ListaCampos.DB_SI, false);
    adicCampo(txtEndFor, 7, 180, 300, 20, "EndFor", "Endereço", ListaCampos.DB_SI, false);
    adicCampo(txtNumFor, 310, 180, 67, 20, "NumFor", "Num.", ListaCampos.DB_SI, false);
    adicCampo(txtComplFor, 380, 180, 70, 20, "ComplFor", "Compl.", ListaCampos.DB_SI, false);
    adicCampo(txtBairFor, 7, 220, 160, 20, "BairFor", "Bairro", ListaCampos.DB_SI, false);
    adicCampo(txtCidFor, 170, 220, 157, 20, "CidFor", "Cidade", ListaCampos.DB_SI, false);
    adicCampo(txtCepFor, 330, 220, 87, 20, "CepFor", "Cep", ListaCampos.DB_SI, false);
    adicCampo(txtUFFor, 420, 220, 30, 20, "UFFor", "UF", ListaCampos.DB_SI, false);
    
    adicCampo(txtDDDFoneFor, 7, 260, 47, 20, "DDDFoneFor", "DDD", ListaCampos.DB_SI, false);
    adicCampo(txtFoneFor, 57, 260, 90, 20, "FoneFor", "Telefone", ListaCampos.DB_SI, false);
    adicCampo(txtDDDFaxFor, 150, 260, 47, 20, "DDDFaxFor", "DDD", ListaCampos.DB_SI, false);
    adicCampo(txtFaxFor, 200, 260, 90, 20, "FaxFor", "Fax", ListaCampos.DB_SI, false);
    adicCampo(txtDDDCelFor, 293, 260, 47, 20, "DDDCelFor", "DDD", ListaCampos.DB_SI, false);
    adicCampo(txtCelFor , 343,    260, 107, 20, "CelFor",   "Celular", ListaCampos.DB_SI, false);

    
    adicCampo(txtEmailFor, 7, 300, 220, 20, "EmailFor", "E-Mail", ListaCampos.DB_SI, false);
    adicCampo(txtContFor,230,   300, 220, 20, "ContFor",  "Contato", ListaCampos.DB_SI, false);
    txtCnpjFor.setMascara(JTextFieldPad.MC_CNPJ);
    txtCepFor.setMascara(JTextFieldPad.MC_CEP);
    txtFoneFor.setMascara(JTextFieldPad.MC_FONE);
    txtFaxFor.setMascara(JTextFieldPad.MC_FONE);
    adicTab("Observações", pnObs);
    adicDBLiv(txaObs, "ObsFor", "Observações",false);
    pnObs.add(spnObs);
    setListaCampos( true, "FORNECED", "CP");
    lcCampos.setQueryInsert(false);    
    
    btImp.addActionListener(this);
	btPrevimp.addActionListener(this);
  	
  }
  private void imprimir(boolean bVisualizar) {
	FAndamento And = null;
	ImprimeOS imp = new ImprimeOS("",con);
	Vector vFiltros = new Vector();
	int linPag = imp.verifLinPag()-1;
	int iContaReg = 0;
	String sObs = "";
	String sWhere = "";
	String sAnd = " WHERE ";
	String[] sValores; 
	
	DLRFornecedor dl = new DLRFornecedor(this,con);

	dl.setVisible(true);
	if (dl.OK == false) {
	  dl.dispose();
	  return;
	}
	
	sValores = dl.getValores();
	dl.dispose();
	if (sValores[1].equals("S")) {
	  sObs = ",OBSFOR";
	}
	if (sValores[2].trim().length() > 0) {
	  sWhere = sWhere+sAnd+"RAZFOR >= '"+sValores[2]+"'";
	  vFiltros.add("RAZAO MAIORES QUE "+sValores[2].trim());
	  sAnd = " AND ";
	}
	if (sValores[3].trim().length() > 0) {
	  sWhere = sWhere+sAnd+"RAZFOR <= '"+sValores[3]+"'";
	  vFiltros.add("RAZAO MENORES QUE "+sValores[3].trim());
	  sAnd = " AND ";
	}
	if (sValores[4].equals("N")) {
	  sWhere = sWhere+sAnd+"PESSOAFOR <> 'F'";
	  vFiltros.add("PESSOAS JURIDICAS");
	  sAnd = " AND ";
	}
	if (sValores[5].length() > 0) {
	  sWhere = sWhere+sAnd+"CIDFOR = '"+sValores[5]+"'";
	  vFiltros.add("CIDADE = "+sValores[5].trim());
	  sAnd = " AND ";
	}
	if (sValores[6].equals("N")) {
	  sWhere = sWhere+sAnd+"PESSOAFOR <> 'J'";
	  vFiltros.add("PESSOAS FISICA");
	  sAnd = " AND ";
	}
	if (sValores[8].length() > 0) {
	  sWhere = sWhere+sAnd+"CODTIPOFOR = "+sValores[8];
	  vFiltros.add("TIPO DE FORNECEDOR = "+sValores[9]);
	  sAnd = " AND ";
	}
	if (sValores[7].equals("C")) {
	  String sSQL = "SELECT CODFOR,RAZFOR,PESSOAFOR,NOMEFOR,CONTFOR,ENDFOR,NUMFOR,"+
					"BAIRFOR,CIDFOR,COMPLFOR,UFFOR,CEPFOR,CNPJFOR,INSCFOR,CPFFOR,RGFOR,"+
					"FONEFOR,FAXFOR,EMAILFOR"+sObs+" FROM CPFORNECED"+sWhere+" ORDER BY "+sValores[0];
	  PreparedStatement ps = null;
	  ResultSet rs = null;
	  try {
		ps = con.prepareStatement("SELECT COUNT(*) FROM CPFORNECED"+sWhere);
		rs = ps.executeQuery();
		rs.next();
		And = new FAndamento("Montando Relatório, Aguarde!",0,rs.getInt(1)-1);
		if (!con.getAutoCommit())
			con.commit();
		ps = con.prepareStatement(sSQL);
		rs = ps.executeQuery();
		
		imp.limpaPags();
		imp.setTitulo("Relatório de Fornecedores");
	  	imp.addSubTitulo("Relatório de Fornecedores");
		imp.addSubTitulo("Filtrado por:");
		for (int i=0;i<vFiltros.size();i++) {            
			String sTmp = (String)vFiltros.elementAt(i);
			imp.addSubTitulo(sTmp);
	    }
		
		while ( rs.next() ) {
		  if (imp.pRow()==0) {
		  	
			imp.montaCab();
			imp.impCab(136, true);
			imp.say(imp.pRow()+0,0,""+imp.comprimido());
			imp.say(imp.pRow()+0,0,"|");
			imp.say(imp.pRow()+0,135,"|");
		  }
		  if (imp.pRow()!=0) {
		    imp.say(imp.pRow()+1,0,""+imp.comprimido());
			imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",133)+"|");
		  }
		  imp.say(imp.pRow()+1,0,""+imp.comprimido());
		  imp.say(imp.pRow()+0,0,"|");
		  imp.say(imp.pRow()+0,2,"Código:");
		  imp.say(imp.pRow()+0,10,rs.getString("CodFor"));
		  imp.say(imp.pRow()+0,20,"Razão:");
		  imp.say(imp.pRow()+0,27,rs.getString("RazFor"));
		  imp.say(imp.pRow()+0,128,"Tipo:");
		  imp.say(imp.pRow()+0,134,rs.getString("PessoaFor"));
		  imp.say(imp.pRow()+0,135,"|");
		  imp.say(imp.pRow()+1,0,""+imp.comprimido());
		  imp.say(imp.pRow()+0,0,"|");
		  imp.say(imp.pRow()+0,1,"Nome:");
		  imp.say(imp.pRow()+0,7,rs.getString("NomeFor"));
		  imp.say(imp.pRow()+0,60,"Contato:");
		  imp.say(imp.pRow()+0,70,rs.getString("ContFor"));
		  imp.say(imp.pRow()+0,135,"|");
		  imp.say(imp.pRow()+1,0,""+imp.comprimido());
		  imp.say(imp.pRow()+0,0,"|");
		  imp.say(imp.pRow()+0,1,"Endereço:");
		  imp.say(imp.pRow()+0,11,rs.getString("EndFor"));
		  imp.say(imp.pRow()+0,62,"N.:");
		  imp.say(imp.pRow()+0,67,""+rs.getInt("NumFor"));
		  imp.say(imp.pRow()+0,76,"Compl.:");
		  imp.say(imp.pRow()+0,85,rs.getString("ComplFor") != null ? rs.getString("ComplFor").trim() : "");
		  imp.say(imp.pRow()+0,94,"Bairro:");
		  imp.say(imp.pRow()+0,102,rs.getString("BairFor") != null ? rs.getString("BairFor").trim() : "");
		  imp.say(imp.pRow()+0,135,"|");
		  imp.say(imp.pRow()+1,0,""+imp.comprimido());
		  imp.say(imp.pRow()+0,0,"|");
		  imp.say(imp.pRow()+0,1,"Cidade:");
		  imp.say(imp.pRow()+0,8,rs.getString("CidFor"));
		  imp.say(imp.pRow()+0,88,"UF:");
		  imp.say(imp.pRow()+0,93,rs.getString("UfFor"));
		  imp.say(imp.pRow()+0,121,"CEP:");
		  imp.say(imp.pRow()+0,126,rs.getString("CepFor") != null ? Funcoes.setMascara(rs.getString("CepFor"),"#####-###") : "");
		  imp.say(imp.pRow()+0,135,"|");
		  imp.say(imp.pRow()+1,0,""+imp.comprimido());
		  imp.say(imp.pRow()+0,0,"|");
		  if ((rs.getString("CnpjFor")) != null && (rs.getString("InscFor") != null)) {
			imp.say(imp.pRow()+0,1,"CNPJ:");
			imp.say(imp.pRow()+0,7,Funcoes.setMascara(rs.getString("CnpjFor"),"##.###.###/####-##"));
			imp.say(imp.pRow()+0,49,"IE:");
			if (!rs.getString("InscFor").trim().toUpperCase().equals("ISENTO") && rs.getString("UFFor") != null) {
			  Funcoes.vIE(rs.getString("InscFor"),rs.getString("UFFor"));
			  imp.say(imp.pRow()+0,54,Funcoes.sIEValida);
			}
		  }
		  else {
			imp.say(imp.pRow()+0,1,"CPF:");
			imp.say(imp.pRow()+0,6,Funcoes.setMascara(rs.getString("CPFFor"),"###.###.###-##"));
			imp.say(imp.pRow()+0,49,"RG:");
			imp.say(imp.pRow()+0,54,rs.getString("RgFor"));
		  }
		  imp.say(imp.pRow()+0,80,"Tel:");
		  imp.say(imp.pRow()+0,86,rs.getString("FoneFor") != null ? Funcoes.setMascara(rs.getString("FoneFor"),"(####)####-####") : "");
		  imp.say(imp.pRow()+0,121,"Fax:");
		  imp.say(imp.pRow()+0,126,rs.getString("FaxFor") != null ? Funcoes.setMascara(rs.getString("FaxFor"),"####-####") : "");
		  imp.say(imp.pRow()+0,135,"|");
		  imp.say(imp.pRow()+1,0,""+imp.comprimido());
		  imp.say(imp.pRow()+0,0,"|");
		  imp.say(imp.pRow()+0,1,"Contato:");
		  imp.say(imp.pRow()+0,9,rs.getString("ContFor"));
		  imp.say(imp.pRow()+0,70,"E-mail:");
		  imp.say(imp.pRow()+0,79,rs.getString("EmailFor"));
		  imp.say(imp.pRow()+0,135,"|");
		  if (sObs.length() > 0) {
			imp.say(imp.pRow()+1,0,""+imp.comprimido());
			imp.say(imp.pRow()+0,0,"|");
			imp.say(imp.pRow()+0,1,"Obs:");
			imp.say(imp.pRow()+0,6,rs.getString("ObsFor"));
			imp.say(imp.pRow()+0,135,"|");
		  }
		  
		  if (imp.pRow()>=linPag) {
		  	imp.say(imp.pRow()+1,0,""+imp.comprimido());
			imp.say(imp.pRow()+0,0,"+"+Funcoes.replicate("-",133)+"+");
			imp.incPags();
			imp.eject();
		  }
		  And.atualiza(iContaReg);
		  iContaReg++;
		}
		imp.say(imp.pRow()+1,0,""+imp.comprimido());
		imp.say(imp.pRow()+0,0,"+"+Funcoes.replicate("-",133)+"+");
		imp.eject();

		imp.fechaGravacao();

		if (!con.getAutoCommit())
			con.commit();
		dl.dispose();
		And.dispose();
	  }
	  catch ( SQLException err ) {
		Funcoes.mensagemErro(this,"Erro consulta tabela de setores!"+err.getMessage());
	  }
	}
	else if (dl.getValores()[7].equals("R")) {
	  String sSQL = "SELECT CODFOR,RAZFOR,ENDFOR,CIDFOR,FONEFOR FROM CPFORNECED"+sWhere+" ORDER BY "+dl.getValores()[0];
//	  System.out.println("...ERRR>"+sSQL);
	  PreparedStatement ps = null;
	  ResultSet rs = null;
	  try {
		ps = con.prepareStatement("SELECT COUNT(*) FROM CPFORNECED"+sWhere);
		rs = ps.executeQuery();
		rs.next();
		And = new FAndamento("Montando Relatório, Aguarde!",0,rs.getInt(1)-1);
		if (!con.getAutoCommit())
			con.commit();
		ps = con.prepareStatement(sSQL);
		rs = ps.executeQuery();
		imp.limpaPags();
		
		imp.setTitulo("Relatório de Fornecedores");
	  	imp.addSubTitulo("Relatório de Fornecedores");
		while ( rs.next() ) {
		  if (imp.pRow()==0) {
		  	
			imp.montaCab();
			imp.impCab(136, true);
			
			imp.say(imp.pRow()+0,2,"|"+Funcoes.replicate(" ",60)+"Filtrado por:"+Funcoes.replicate(" ",60)+"|");
			for (int i=0;i<vFiltros.size();i++) {            
					String sTmp = (String)vFiltros.elementAt(i);
					sTmp = "|"+Funcoes.replicate(" ",(((135-sTmp.length())/2)-1))+sTmp;
					sTmp += Funcoes.replicate(" ",134-sTmp.length())+"|";
					imp.say(imp.pRow()+1,0,""+imp.comprimido());
					imp.say(imp.pRow()+0,2,sTmp);
			}
			
			imp.say(imp.pRow()+1,0,""+imp.comprimido());
			imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",133)+"|");
			imp.say(imp.pRow()+1,0,""+imp.comprimido());
			imp.say(imp.pRow()+0,0,"|");
			imp.say(imp.pRow()+0,2,"Código");
			imp.say(imp.pRow()+0,10,"Razão:");
			imp.say(imp.pRow()+0,50,"Endereço:");
			imp.say(imp.pRow()+0,90,"Cidade:");
			imp.say(imp.pRow()+0,120,"Tel:");
			imp.say(imp.pRow()+0,135,"|");
			imp.say(imp.pRow()+1,0,""+imp.comprimido());
			imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",133)+"|");
			imp.say(imp.pRow()+1,0,""+imp.comprimido());
		  }
		  imp.say(imp.pRow()+0,0,"|");
		  imp.say(imp.pRow()+0,2,rs.getString("CodFor"));
		  imp.say(imp.pRow()+0,10,rs.getString("RazFor") != null ? rs.getString("RazFor").substring(0,39) : "");
		  imp.say(imp.pRow()+0,50,rs.getString("EndFor") != null ? rs.getString("EndFor").substring(0,39) : "");
		  imp.say(imp.pRow()+0,90,rs.getString("CidFor") != null ? rs.getString("CidFor").substring(0,29) : "");
		  imp.say(imp.pRow()+0,120,rs.getString("FoneFor") != null ? Funcoes.setMascara(rs.getString("FoneFor"),"(####)####-####") : "");
		  imp.say(imp.pRow()+0,135,"|");
		  imp.say(imp.pRow()+1,0,""+imp.comprimido());
		  if (imp.pRow()>=linPag) {
		  	imp.say(imp.pRow()+0,0,""+imp.comprimido());
			imp.say(imp.pRow()+0,0,"+"+Funcoes.replicate("-",133)+"+");
			imp.incPags();
			imp.eject();
		  }
		  And.atualiza(iContaReg);
		  iContaReg++;
		}
		imp.say(imp.pRow()+0,0,""+imp.comprimido());
		imp.say(imp.pRow()+0,0,"+"+Funcoes.replicate("-",133)+"+");
		imp.eject();

		imp.fechaGravacao();

		if (!con.getAutoCommit())
			con.commit();
		dl.dispose();
		And.dispose();
	  }
	  catch ( SQLException err ) {
		Funcoes.mensagemErro(this,"Erro na consulta da tabela de setores!"+err.getMessage());
	  }
	}
	if (bVisualizar) {
	  imp.preview(this);
	}
	else {
	  imp.print();
	}
  }
  public void valorAlterado(RadioGroupEvent rgevt) {
    if (rgPessoa.getVlrString().equals("J")) {
      txtCnpjFor.setEnabled(true);
      if (prefs[0])
         setBordaReq(txtCnpjFor);
      else
         setBordaPad(txtCnpjFor);
      txtInscFor.setEnabled(true);
      if (prefs[1])
      	 setBordaReq(txtInscFor);
      else
     	 setBordaPad(txtInscFor);
      txtCpfFor.setEnabled(false);
      setBordaPad(txtCpfFor);
      txtRgFor.setEnabled(false);
      setBordaPad(txtRgFor);
    }
    else if (rgPessoa.getVlrString().equals("F") ) {
      txtCnpjFor.setEnabled(false);
      setBordaPad(txtCnpjFor);
      txtInscFor.setEnabled(false);
      setBordaPad(txtInscFor);
      txtCpfFor.setEnabled(true);
      setBordaReq(txtCpfFor);
      txtRgFor.setEnabled(true);
      setBordaReq(txtRgFor);
    }
  }
  public void actionPerformed(ActionEvent evt) {
	if (evt.getSource() == btPrevimp) {
		imprimir(true);
		Vector vVal = Funcoes.stringToVector(txaObs.getText());
		int iTam = vVal.size();
		for (int i=0;i<iTam;i++) {
				System.out.println(i+" : "+vVal.elementAt(i));
		}
	}
	else if (evt.getSource() == btImp) 
	  imprimir(false);
	super.actionPerformed(evt);
  }
  public void beforePost(PostEvent pevt) {
    if (rgPessoa.getVlrString().equals("F"))
      return;
    else if ( (txtCnpjFor.getVlrString().trim().equals("") ) && (prefs[0]) ) {
      pevt.cancela();
      Funcoes.mensagemInforma( this,"Campo CNPJ é requerido! ! !");
      txtCnpjFor.requestFocus();
      return;
    }
    else if ( (txtInscFor.getVlrString().trim().equals("")) && (prefs[1]) ){
      if (Funcoes.mensagemConfirma(this, "Inscrição Estadual em branco! Inserir ISENTO?")==JOptionPane.OK_OPTION ) 
        pevt.cancela();
        txtInscFor.setVlrString("ISENTO");
        txtInscFor.requestFocus();
        return;
    }
    else if (txtInscFor.getVlrString().trim().equalsIgnoreCase("ISENTO"))
      return;
    else if (txtUFFor.getText().trim().length() < 2) {
      pevt.cancela();
      Funcoes.mensagemInforma( this,"Campo UF é requerido! ! !");
      txtUFFor.requestFocus();
      return;
    }
    else if (!Funcoes.vIE(txtInscFor.getText(),txtUFFor.getText())) {
      pevt.cancela();
      Funcoes.mensagemInforma( this ,"Inscrição Estadual Inválida ! ! !");
      txtInscFor.requestFocus();
      return;
    }
    txtInscFor.setVlrString(Funcoes.sIEValida);
  }
  public void setConexao(Connection cn) {
    super.setConexao(cn);
    lcTipoFor.setConexao(cn);
    prefs = getPrefs();
    montaTela();
  }
  public void afterPost(PostEvent pevt) { }
  public void afterInsert(InsertEvent ievt) {
  	cbAtivo.setVlrString("S");
  }
  public void beforeInsert(InsertEvent ievt) {
  }
  
  private boolean[] getPrefs() {
  	boolean[] bRet = new boolean[2];
  	String sSQL = null;
  	PreparedStatement ps = null;
  	ResultSet rs = null;
  	String sVal = null;
  	try {
  		bRet[0] = true;
  		bRet[1] = true;
  		sSQL = "SELECT CNPJFOROBRIG, INSCESTFOROBRIG FROM SGPREFERE1 WHERE CODEMP=? AND CODFILIAL=?";
  		try {
  			ps = con.prepareStatement(sSQL);
  			ps.setInt(1,Aplicativo.iCodEmp);
  			ps.setInt(2,Aplicativo.iCodFilial);
  			rs = ps.executeQuery();
  			if (rs.next()) {
  				sVal = rs.getString("CNPJFOROBRIG");
  				if (sVal!= null) 
  					bRet[0] = sVal.equals("S");
  				sVal = rs.getString("INSCESTFOROBRIG");
  				if (sVal!=null) {
  					bRet[1] = sVal.equals("S");
  				}
  			}
  			rs.close();
  			ps.close();
  			if (!con.getAutoCommit()) {
  				con.commit();
  			}
  		}
  		catch(SQLException err) {
  			Funcoes.mensagemErro(this,"Erro ao verificar preferências!\n"+err.getMessage());
  			err.printStackTrace();
  		}
  	}
  	finally {
  		sSQL = null;
  		ps = null;
  		rs = null;
  		sVal = null;
  	}
  	return bRet;
  }  
  
}
