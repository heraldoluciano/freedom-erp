/**
 * @version 10/10/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.tmk <BR>
 * Classe: @(#)FContato.java <BR>
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
 * Tela de cadastro de contatos.
 * 
 */

package org.freedom.modulos.tmk;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.freedom.acao.PostEvent;
import org.freedom.acao.PostListener;
import org.freedom.acao.RadioGroupEvent;
import org.freedom.acao.RadioGroupListener;
import org.freedom.bmps.Icone;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JRadioGroup;
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
import org.freedom.telas.FAndamento;
import org.freedom.telas.FPrincipal;
import org.freedom.telas.FTabDados;


public class FContato extends FTabDados implements RadioGroupListener, PostListener, ActionListener, ChangeListener {
  private Painel pinCont = new Painel();
  private Painel pinVend = new Painel(0,80);
  private Painel pinRodAtiv = new Painel(0,80);
  private Tabela tabAtiv = new Tabela();
  private JScrollPane spnAtiv = new JScrollPane(tabAtiv);
  private JPanel pnAtiv = new JPanel(new BorderLayout());
  private JTextFieldPad txtCodCont = new JTextFieldPad(8);
  private JTextFieldPad txtRazCont = new JTextFieldPad(50);
  private JTextFieldPad txtNomeCont = new JTextFieldPad(40);
  private JTextFieldPad txtCargoCont = new JTextFieldPad(40);
  private JTextFieldPad txtContCont = new JTextFieldPad(40);
  private JTextFieldPad txtCnpjCont = new JTextFieldPad(14);
  private JTextFieldPad txtInscCont = new JTextFieldPad(15);
  private JTextFieldPad txtCpfCont = new JTextFieldPad(11);
  private JTextFieldPad txtRgCont = new JTextFieldPad(10);
  private JTextFieldPad txtEndCont = new JTextFieldPad(50);
  private JTextFieldPad txtNumCont = new JTextFieldPad(8);
  private JTextFieldPad txtComplCont = new JTextFieldPad(20);
  private JTextFieldPad txtBairCont = new JTextFieldPad(30);
  private JTextFieldPad txtCidCont = new JTextFieldPad(30);
  private JTextFieldPad txtUFCont = new JTextFieldPad(2);
  private JTextFieldPad txtCepCont = new JTextFieldPad(8);
  private JTextFieldPad txtFoneCont = new JTextFieldPad(12);
  private JTextFieldPad txtFaxCont = new JTextFieldPad(8);
  private JTextFieldPad txtEmailCont = new JTextFieldPad(50);
  private JTextFieldPad txtNumEmp = new JTextFieldPad(8);
  private JTextFieldPad txtCodVend = new JTextFieldPad(8);
  private JTextFieldFK  txtDescVend = new JTextFieldFK();
  private JTextFieldPad txtDataCont = new JTextFieldPad(8);
  private JTextFieldPad txtCodSetor = new JTextFieldPad(8);
  private JTextFieldFK  txtDescSetor = new JTextFieldFK();
  private JTextFieldPad txtCodAtiv = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldFK  txtDescAtiv = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
  private Vector vPessoaLab = new Vector();
  private Vector vPessoaVal = new Vector();
  private JRadioGroup rgPessoa = null;
  private JPanel pnCompl = new JPanel(new BorderLayout());
  private JTextAreaPad txaObs = new JTextAreaPad();
  private JScrollPane spnObs = new JScrollPane(txaObs);
  private ListaCampos lcVend = new ListaCampos(this,"VD");
  private ListaCampos lcSetor = new ListaCampos(this,"SR");
  private ListaCampos lcAtiv = new ListaCampos(this,"AV");
  private ListaCampos lcAtivFK = new ListaCampos(this,"AV");
  private Navegador navAtiv = new Navegador(true);
  private JButton btExportCli = new JButton(Icone.novo("btExportaCli.gif"));
  private FPrincipal fPrim;
  public FContato () {
    setTitulo("Cadastro de Contatos"); 
    setAtribos(50, 10, 530, 400);
    
	lcAtiv.setMaster(lcCampos);
	lcCampos.adicDetalhe(lcAtiv);
	lcAtiv.setTabela(tabAtiv);
    
    pinCont = new Painel(500,330);
    setPainel(pinCont);
    adicTab("Contato", pinCont); 
    
    lcCampos.addPostListener(this);

    txtCodVend.setTipo(JTextFieldPad.TP_INTEGER,8,0);
    txtDescVend.setTipo(JTextFieldPad.TP_STRING,50,0);    

    lcVend.add(new GuardaCampo( txtCodVend, 7, 100, 80, 20, "CodVend", "Código", true, false, null, JTextFieldPad.TP_INTEGER,false),"txtCodVendx");
    lcVend.add(new GuardaCampo( txtDescVend, 90, 100, 207, 20, "NomeVend", "Nome", false, false, null, JTextFieldPad.TP_STRING,false),"txtDescVendx");
    lcVend.montaSql(false, "VENDEDOR", "VD");    
    lcVend.setQueryCommit(false);
    lcVend.setReadOnly(true);
    txtCodVend.setTabelaExterna(lcVend);

    txtCodSetor.setTipo(JTextFieldPad.TP_INTEGER,8,0); 
    txtDescSetor.setTipo(JTextFieldPad.TP_STRING,50,0);    

    lcSetor.add(new GuardaCampo( txtCodSetor, 7, 100, 80, 20, "CodSetor", "Código", true, false, null, JTextFieldPad.TP_INTEGER,true),"txtCodSetorx");
    lcSetor.add(new GuardaCampo( txtDescSetor, 90, 100, 207, 20, "DescSetor", "Descrição", false, false, null, JTextFieldPad.TP_STRING,false),"txtDescSetorx");
    lcSetor.montaSql(false, "SETOR", "VD");    
    lcSetor.setQueryCommit(false);
    lcSetor.setReadOnly(true);
    txtCodSetor.setTabelaExterna(lcSetor);
    
	lcAtivFK.add(new GuardaCampo( txtCodAtiv, 7, 100, 80, 20, "CodAtiv", "Código", true, false, null, JTextFieldPad.TP_STRING,true),"txtCodMoedax");
	lcAtivFK.add(new GuardaCampo( txtDescAtiv, 90, 100, 207, 20, "DescAtiv", "Descrição", false, false, null, JTextFieldPad.TP_STRING,false),"txtDescMoedax");
	lcAtivFK.montaSql(false, "ATIVIDADE", "TK");    
	lcAtivFK.setReadOnly(true);
	lcAtivFK.setQueryCommit(false);
	txtCodAtiv.setTabelaExterna(lcAtivFK);

    adicCampo(txtCodCont, 7, 20, 80, 20, "CodCto", "Código", JTextFieldPad.TP_INTEGER, 5, 0, true, false, null,true);
    adicCampo(txtRazCont, 90, 20, 307, 20, "RazCto", "Razão", JTextFieldPad.TP_STRING, 50, 0, false, false, null,true);
    
    vPessoaLab.addElement("Jurídica");
    vPessoaLab.addElement("Física");
    vPessoaVal.addElement("J");
    vPessoaVal.addElement("F");   
    
    rgPessoa = new JRadioGroup( 2, 1, vPessoaLab, vPessoaVal);
	rgPessoa.addRadioGroupListener(this);   

    adicDB(rgPessoa, 400, 20, 100, 60, "PessoaCto", "Pessoa",JTextFieldPad.TP_STRING,true);
    rgPessoa.setVlrString("F");
    
    JCheckBoxPad cbAtivo = new JCheckBoxPad("Ativo","S","N");
    
    adicDB(cbAtivo, 19, 60, 70, 20, "AtivoCto", "Ativo",JTextFieldPad.TP_STRING,true);
    adicCampo(txtNomeCont, 90, 60, 307, 20, "NomeCto", "Nome", JTextFieldPad.TP_STRING, 40, 0, false, false, null,true);
    adicCampo(txtCpfCont, 273, 110, 111, 20, "CpfCto", "CPF", JTextFieldPad.TP_STRING, 11, 0, false, false, null, false);
	adicCampo(txtCodSetor, 7, 110, 60, 20, "CodSetor", "Código", JTextFieldPad.TP_INTEGER, 8, 0, false, true, txtDescSetor,true);
	adicDescFK(txtDescSetor, 70, 110, 200, 20, "DescSetor", "e descrição do setor do contato", JTextFieldPad.TP_STRING, 50, 0);
    adicCampo(txtRgCont, 387, 110, 112, 20, "RgCto", "RG", JTextFieldPad.TP_STRING, 10, 0, false, false, null, false);
    adicCampo(txtCnpjCont, 7, 150, 150, 20, "CnpjCto", "CNPJ", JTextFieldPad.TP_STRING, 14, 0, false, false, null, false);
    adicCampo(txtInscCont, 160, 150, 147, 20, "InscCto", "Inscrição Estadual", JTextFieldPad.TP_STRING, 15, 0, false, false, null, false);
	adicCampo(txtContCont, 310, 150, 107, 20, "ContCto", "Falar com:", JTextFieldPad.TP_STRING, 40, 0, false, false, null,false);
	adicCampo(txtCargoCont, 420, 150, 80, 20, "CargoContCto", "Cargo", JTextFieldPad.TP_STRING, 30, 0, false, false, null,false);
    adicCampo(txtEndCont, 7, 190, 340, 20, "EndCto", "Endereço", JTextFieldPad.TP_STRING, 50, 0, false, false, null, false);
    adicCampo(txtNumCont, 350, 190, 77, 20, "NumCto", "Num.", JTextFieldPad.TP_INTEGER, 8, 0, false, false, null, false);
    adicCampo(txtComplCont, 430, 190, 70, 20, "ComplCto", "Compl.", JTextFieldPad.TP_STRING, 20, 0, false, false, null, false);
    adicCampo(txtBairCont, 7, 230, 180, 20, "BairCto", "Bairro", JTextFieldPad.TP_STRING, 30, 0, false, false, null, false);
    adicCampo(txtCidCont, 190, 230, 177, 20, "CidCto", "Cidade", JTextFieldPad.TP_STRING, 30, 0, false, false, null, false);
    adicCampo(txtCepCont, 370, 230, 77, 20, "CepCto", "Cep", JTextFieldPad.TP_STRING, 8, 0, false, false, null, false);
    adicCampo(txtUFCont, 450, 230, 50, 20, "UFCto", "UF", JTextFieldPad.TP_STRING, 2, 0, false, false, null, false);
    adicCampo(txtFoneCont, 7, 270, 100, 20, "FoneCto", "Telefone", JTextFieldPad.TP_STRING, 12, 0, false, false, null, false);
    adicCampo(txtFaxCont, 110, 270, 97, 20, "FaxCto", "Fax", JTextFieldPad.TP_STRING, 8, 0, false, false, null, false);
    adicCampo(txtEmailCont, 210, 270, 177, 20, "EmailCto", "E-Mail", JTextFieldPad.TP_STRING, 50, 0, false, false, null, false);
    adicCampo(txtNumEmp,390,270,77,20,"numempcto","N. Funcionarios", JTextFieldPad.TP_INTEGER, 50,0,false,false,null,false);
    adic(btExportCli,470,260,30,30);
    txtCpfCont.setMascara(JTextFieldPad.MC_CPF);
    txtCnpjCont.setMascara(JTextFieldPad.MC_CNPJ);
    txtCepCont.setMascara(JTextFieldPad.MC_CEP);
    txtFoneCont.setMascara(JTextFieldPad.MC_FONEDDD);
    txtFaxCont.setMascara(JTextFieldPad.MC_FONE);
    adicTab("Informações Complementares", pnCompl);
    adicDBLiv(txaObs, "ObsCto", "Observações",JTextFieldPad.TP_STRING, false);
	pnCompl.add(pinVend,BorderLayout.NORTH);    
    pnCompl.add(spnObs,BorderLayout.CENTER);
    setPainel(pinVend);
	adicCampo(txtCodVend, 7, 25, 80, 20, "CodVend", "Código", JTextFieldPad.TP_INTEGER, 8, 0, false, true, txtDescVend,false);
	adicDescFK(txtDescVend, 90, 25, 237, 20, "NomeVend", "e descrição do vendedor", JTextFieldPad.TP_STRING, 50, 0);
	adicCampo(txtDataCont, 330, 25, 80, 20, "DataCto", "Data", JTextFieldPad.TP_DATE, 10, 0, false, false, null,false);
	adic(new JLabel("Observações:"),7,55,150,20);
    setListaCampos( true, "CONTATO", "TK");
    
//Atividade    

	setPainel( pinRodAtiv, pnAtiv);
	adicTab("Atividade",pnAtiv);
	setListaCampos(lcAtiv);
	setNavegador(navAtiv);
	pnAtiv.add(pinRodAtiv, BorderLayout.SOUTH);
	pnAtiv.add(spnAtiv, BorderLayout.CENTER);

	pinRodAtiv.adic(navAtiv,0,50,270,25);
   
	adicCampo(txtCodAtiv, 7, 20, 80, 20, "CodAtiv", "Código", JTextFieldPad.TP_STRING, 4, 0, true, true, txtDescAtiv,true);
	adicDescFK(txtDescAtiv, 90, 20, 220, 20, "DescAtiv", "e descrição da atividade", JTextFieldPad.TP_STRING, 40, 0);
	setListaCampos( false, "CTOATIV", "TK");
	lcAtiv.montaTab();
	lcAtiv.setQueryInsert(false);
	lcAtiv.setQueryCommit(false);
	
	tabAtiv.setTamColuna(220,1);
       
    btImp.addActionListener(this);
    btPrevimp.addActionListener(this);
    btExportCli.addActionListener(this);
	tpn.addChangeListener(this);
    lcCampos.setQueryInsert(false);	
  }
  private boolean duploCNPJ() {
          boolean bRetorno = false;
          String sSQL = "SELECT CNPJCTO FROM TKCONTATO WHERE CNPJCTO=?";
          try {
                PreparedStatement ps = con.prepareStatement(sSQL);
                ps.setString(1,txtCnpjCont.getVlrString());
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                        bRetorno = true;
                }
                rs.close();
                ps.close();
          }
          catch(SQLException err) {
			Funcoes.mensagemErro(this,"Erro ao checar CNPJ.\n"+err);
          }
          return bRetorno;
  }
  private void exportaCli() {
  		if (txtCodCont.getVlrInteger().intValue() == 0 || lcCampos.getStatus() != ListaCampos.LCS_SELECT) {
  			Funcoes.mensagemInforma(this,"Selecione um contato cadastrado antes!");
  			return;
  		}
  		DLContToCli dl = new DLContToCli(this,txtCodSetor.getVlrInteger().intValue());
  		dl.setConexao(con);
  		dl.setVisible(true);
  		if (!dl.OK) {
  			dl.dispose();
  			return;
  		}
  		int[] iVals = dl.getValores();
  		dl.dispose();
  		String sSQL = "SELECT IRET FROM TKCONTTOCLI(?,?,?,?,?,?,?,?,?)";
  		try {
  			PreparedStatement ps = con.prepareStatement(sSQL);
  			ps.setInt(1,Aplicativo.iCodEmp);
  			ps.setInt(2,lcCampos.getCodFilial());
  			ps.setInt(3,txtCodCont.getVlrInteger().intValue());
  			ps.setInt(4,iVals[0]);
  			ps.setInt(5,iVals[1]);
  			ps.setInt(6,iVals[2]);
  			ps.setInt(7,iVals[3]);
  			ps.setInt(8,iVals[4]);
  			ps.setInt(9,iVals[5]);
  			ResultSet rs = ps.executeQuery();
  			if (rs.next()) {
  				if (Funcoes.mensagemConfirma(this,"Cliente '"+rs.getInt(1)+"' criado com sucesso!\n"+
  				"Gostaria de edita-lo agora?") == JOptionPane.OK_OPTION) {
  					abreCli(rs.getInt(1));
  				}
  			}
  			rs.close();
  			ps.close();
  			if (!con.getAutoCommit())
  				con.commit();
  		}
  		catch (SQLException err) {
  			Funcoes.mensagemErro(this,"Erro ao criar cliente!\n"+err.getMessage());
  			err.printStackTrace();
  		}
  		dl.dispose();
  }
  private void abreCli(int iCodCli) {
  	if (!fPrim.temTela("Cliente")) {
  		FCliente tela = new FCliente();
  		fPrim.criatela("Cliente",tela,con);
  		tela.exec(iCodCli);
  	} 
  }
  public void setTelaPrim(FPrincipal fP) {
  	fPrim = fP;
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
    imp.setTitulo("Relatório de Contatos");
    imp.montaCab();
    DLRCont dl = new DLRCont(this,con);
	dl.setVisible(true);
	if (dl.OK == false) {
	  dl.dispose();
	  return;
	}
	sValores = dl.getValores();
    dl.dispose();
    if (sValores[1].equals("S")) {
      sObs = ",OBSCTO";
    }
    if (sValores[2].trim().length() > 0) {
      sWhere = sWhere+sAnd+"RAZCTO >= '"+sValores[2]+"'";
      vFiltros.add("RAZAO MAIORES QUE "+sValores[2].trim());
      sAnd = " AND ";
    }
    if (sValores[3].trim().length() > 0) {
      sWhere = sWhere+sAnd+"RAZCTO <= '"+sValores[3]+"'";
      vFiltros.add("RAZAO MENORES QUE "+sValores[3].trim());
      sAnd = " AND ";
    }
    if (sValores[4].equals("N")) {
      sWhere = sWhere+sAnd+"PESSOACTO <> 'F'";
      vFiltros.add("PESSOAS JURIDICAS");
      sAnd = " AND ";
    }
    if (sValores[5].length() > 0) {
      sWhere = sWhere+sAnd+"CIDCTO = '"+sValores[5]+"'";
      vFiltros.add("CIDADE = "+sValores[5].trim());
      sAnd = " AND ";
    }
    if (sValores[6].equals("N")) {
      sWhere = sWhere+sAnd+"PESSOACTO <> 'J'";
      vFiltros.add("PESSOAS FISICA");
      sAnd = " AND ";
    }
    if (sValores[8].length() > 0) {
      sWhere = sWhere+sAnd+"CODSETOR = "+sValores[8];
      vFiltros.add("SETOR = "+sValores[9]);
      sAnd = " AND ";
    }
    if (sValores[7].equals("C")) {
      String sSQL = "SELECT CODCTO,RAZCTO,PESSOACTO,NOMECTO,CONTCTO,ENDCTO,NUMCTO,"+
                    "BAIRCTO,CIDCTO,COMPLCTO,UFCTO,CEPCTO,CNPJCTO,INSCCTO,CPFCTO,RGCTO,"+
                    "FONECTO,FAXCTO,EMAILCTO"+sObs+" FROM TKCONTATO"+sWhere+" ORDER BY "+sValores[0];
      PreparedStatement ps = null;
      ResultSet rs = null;
      try {
        ps = con.prepareStatement("SELECT COUNT(*) FROM TKCONTATO"+sWhere);
        rs = ps.executeQuery();
        rs.next();
        And = new FAndamento("Montando Relatório, Aguarde!",0,rs.getInt(1)-1);
//        rs.close();
//        ps.close();
        if (!con.getAutoCommit())
        	con.commit();
        ps = con.prepareStatement(sSQL);
        rs = ps.executeQuery();
        imp.limpaPags();
        while ( rs.next() ) {
          if (imp.pRow()==0) {
            imp.impCab(136);
            imp.say(imp.pRow()+0,2,"|"+Funcoes.replicate(" ",61)+"Filtrado por:"+Funcoes.replicate(" ",60)+"|");
            for (int i=0;i<vFiltros.size();i++) {            
                    String sTmp = (String)vFiltros.elementAt(i);
                    sTmp = "|"+Funcoes.replicate(" ",(((136-sTmp.length())/2)-1))+sTmp;
                    sTmp += Funcoes.replicate(" ",135-sTmp.length())+"|";
                    imp.say(imp.pRow()+1,0,""+imp.comprimido());
                    imp.say(imp.pRow()+0,2,sTmp);
            }
          }
          imp.say(imp.pRow()+1,0,""+imp.comprimido());
          imp.say(imp.pRow()+0,0,Funcoes.replicate("-",136));
          imp.say(imp.pRow()+1,0,""+imp.comprimido());
          imp.say(imp.pRow()+0,2,"Código:");
          imp.say(imp.pRow()+0,10,rs.getString("CodCto"));
          imp.say(imp.pRow()+0,20,"Razão:");
          imp.say(imp.pRow()+0,27,rs.getString("RazCto"));
          imp.say(imp.pRow()+0,129,"Setor:");
          imp.say(imp.pRow()+0,135,rs.getString("PessoaCto"));
          imp.say(imp.pRow()+1,0,""+imp.comprimido());
          imp.say(imp.pRow()+0,0,"Nome:");
          imp.say(imp.pRow()+0,7,rs.getString("NomeCto"));
          imp.say(imp.pRow()+0,60,"Contato:");
          imp.say(imp.pRow()+0,70,rs.getString("ContCto"));
          imp.say(imp.pRow()+1,0,""+imp.comprimido());
          imp.say(imp.pRow()+0,0,"Endereço:");
          imp.say(imp.pRow()+0,11,rs.getString("EndCto"));
          imp.say(imp.pRow()+0,62,"N.:");
          imp.say(imp.pRow()+0,67,""+rs.getInt("NumCto"));
          imp.say(imp.pRow()+0,76,"Compl.:");
          imp.say(imp.pRow()+0,85,rs.getString("ComplCto") != null ? rs.getString("ComplCto").trim() : "");
          imp.say(imp.pRow()+0,94,"Bairro:");
          imp.say(imp.pRow()+0,103,rs.getString("BairCto") != null ? rs.getString("BairCto").trim() : "");
          imp.say(imp.pRow()+1,0,""+imp.comprimido());
          imp.say(imp.pRow()+0,0,"Cidade:");
          imp.say(imp.pRow()+0,8,rs.getString("CidCto"));
          imp.say(imp.pRow()+0,88,"UF:");
          imp.say(imp.pRow()+0,93,rs.getString("UfCto"));
          imp.say(imp.pRow()+0,121,"CEP:");
          imp.say(imp.pRow()+0,127,rs.getString("CepCto") != null ? Funcoes.setMascara(rs.getString("CepCto"),"#####-###") : "");
          imp.say(imp.pRow()+1,0,""+imp.comprimido());
          if ((rs.getString("CnpjCto")) != null && (rs.getString("InscCto") != null)) {
            imp.say(imp.pRow()+0,0,"CNPJ:");
            imp.say(imp.pRow()+0,7,Funcoes.setMascara(rs.getString("CnpjCto"),"##.###.###/####-##"));
            imp.say(imp.pRow()+0,50,"IE:");
            if (!rs.getString("InscCto").trim().toUpperCase().equals("ISENTO") && rs.getString("UFCto") != null) {
              Funcoes.vIE(rs.getString("InscCto"),rs.getString("UFCto"));
              imp.say(imp.pRow()+0,55,Funcoes.sIEValida);
            }
          }
          else {
            imp.say(imp.pRow()+0,0,"CPF:");
            imp.say(imp.pRow()+0,6,Funcoes.setMascara(rs.getString("CPFCto"),"###.###.###-##"));
            imp.say(imp.pRow()+0,50,"RG:");
            imp.say(imp.pRow()+0,55,rs.getString("RgCto"));
          }
          imp.say(imp.pRow()+0,80,"Tel:");
          imp.say(imp.pRow()+0,86,rs.getString("FoneCto") != null ? Funcoes.setMascara(rs.getString("FoneCto"),"(####)####-####") : "");
          imp.say(imp.pRow()+0,121,"Fax:");
          imp.say(imp.pRow()+0,127,rs.getString("FaxCto") != null ? Funcoes.setMascara(rs.getString("FaxCto"),"####-####") : "");
          imp.say(imp.pRow()+1,0,""+imp.comprimido());
          imp.say(imp.pRow()+0,0,"Contato:");
          imp.say(imp.pRow()+0,9,rs.getString("ContCto"));
          imp.say(imp.pRow()+0,70,"E-mail:");
          imp.say(imp.pRow()+0,79,rs.getString("EmailCto"));
          if (sObs.length() > 0) {
            imp.say(imp.pRow()+1,0,""+imp.comprimido());
            imp.say(imp.pRow()+0,0,"Obs:");
            imp.say(imp.pRow()+0,6,rs.getString("ObsCto"));
          }
          imp.say(imp.pRow()+1,0,""+imp.comprimido());
          imp.say(imp.pRow()+0,0,Funcoes.replicate("-",136));
          if (imp.pRow()>=linPag) {
            imp.incPags();
            imp.eject();
          }
          And.atualiza(iContaReg);
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
        And.dispose();
      }
      catch ( SQLException err ) {
		Funcoes.mensagemErro(this,"Erro consulta tabela de contatos!"+err.getMessage());
      }
    }
    else if (dl.getValores()[7].equals("R")) {
      String sSQL = "SELECT CODCTO,NOMECTO,ENDCTO,CIDCTO,FONECTO FROM TKCONTATO"+sWhere+" ORDER BY "+dl.getValores()[0];
      PreparedStatement ps = null;
      ResultSet rs = null;
      try {
        ps = con.prepareStatement("SELECT COUNT(*) FROM TKCONTATO"+sWhere);
        rs = ps.executeQuery();
        rs.next();
        And = new FAndamento("Montando Relatório, Aguarde!",0,rs.getInt(1)-1);
//        rs.close();
//        ps.close();
        if (!con.getAutoCommit())
        	con.commit();
        ps = con.prepareStatement(sSQL);
        rs = ps.executeQuery();
        imp.limpaPags();
        while ( rs.next() ) {
          if (imp.pRow()==0) {
            imp.impCab(136);
            imp.say(imp.pRow()+0,2,"|"+Funcoes.replicate(" ",61)+"Filtrado por:"+Funcoes.replicate(" ",60)+"|");
            for (int i=0;i<vFiltros.size();i++) {            
                    String sTmp = (String)vFiltros.elementAt(i);
                    sTmp = "|"+Funcoes.replicate(" ",(((136-sTmp.length())/2)-1))+sTmp;
                    sTmp += Funcoes.replicate(" ",135-sTmp.length())+"|";
                    imp.say(imp.pRow()+1,0,""+imp.comprimido());
                    imp.say(imp.pRow()+0,2,sTmp);
            }
            imp.say(imp.pRow()+1,0,""+imp.comprimido());
            imp.say(imp.pRow()+0,0,Funcoes.replicate("-",136));
            imp.say(imp.pRow()+1,0,""+imp.comprimido());
            imp.say(imp.pRow()+0,2,"Código");
            imp.say(imp.pRow()+0,10,"Nome:");
            imp.say(imp.pRow()+0,50,"Endereço:");
            imp.say(imp.pRow()+0,90,"Cidade:");
            imp.say(imp.pRow()+0,120,"Tel:");
            imp.say(imp.pRow()+1,0,""+imp.comprimido());
            imp.say(imp.pRow()+0,0,Funcoes.replicate("-",136));
            imp.say(imp.pRow()+1,0,""+imp.comprimido());
          }
          imp.say(imp.pRow()+0,2,rs.getString("CodCto"));
          imp.say(imp.pRow()+0,10,rs.getString("NomeCto") != null ? rs.getString("NomeCto").substring(0,39) : "");
          imp.say(imp.pRow()+0,50,rs.getString("EndCto") != null ? rs.getString("EndCto").substring(0,39) : "");
          imp.say(imp.pRow()+0,90,rs.getString("CidCto") != null ? rs.getString("CidCto").substring(0,29) : "");
          imp.say(imp.pRow()+0,120,rs.getString("FoneCto") != null ? Funcoes.setMascara(rs.getString("FoneCto"),"(####)####-####") : "");
          imp.say(imp.pRow()+1,0,""+imp.comprimido());
          if (imp.pRow()>=linPag) {
            imp.incPags();
            imp.eject();
          }
          And.atualiza(iContaReg);
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
        And.dispose();
      }
      catch ( SQLException err ) {
		Funcoes.mensagemErro(this,"Erro consulta tabela de contatos!"+err.getMessage());
      }
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
        Vector vVal = Funcoes.stringToVector(txaObs.getText());
        int iTam = vVal.size();
        for (int i=0;i<iTam;i++) {
                System.out.println(i+" : "+vVal.elementAt(i));
        }
    }
    else if (evt.getSource() == btImp) 
      imprimir(false);
    else if (evt.getSource() == btExportCli)
      exportaCli();
      
    super.actionPerformed(evt);
  }
  public void beforePost(PostEvent pevt) {
  	if (lcCampos.getStatus() == ListaCampos.LCS_INSERT) {
      if (!(txtCnpjCont.getText().trim().length() < 1) &&
           !(duploCNPJ())
          )
	     {
            pevt.cancela();
            Funcoes.mensagemInforma(this,"Este CNPJ ja está cadastrado!");
            txtCnpjCont.requestFocus();
         }
      txtDataCont.setVlrDate(new Date());
  	}
    if (!(txtInscCont.getText().trim().length() < 1) &&
	        !(txtInscCont.getText().trim().toUpperCase().compareTo("ISENTO") == 0) &&
            !Funcoes.vIE(txtInscCont.getText(),txtUFCont.getText())) {
      pevt.cancela();
      Funcoes.mensagemInforma(this,"Inscrição Estadual Inválida ! ! !");
      txtInscCont.requestFocus();
    }
	txtInscCont.setVlrString(Funcoes.sIEValida);
  }
  public void valorAlterado(RadioGroupEvent rgevt) {
	if (rgPessoa.getVlrString().compareTo("J") == 0) {
	  txtCnpjCont.setEnabled(true);
	  txtInscCont.setEnabled(true);
	  txtCpfCont.setEnabled(false);
	  txtRgCont.setEnabled(false);
	}
	else if (rgPessoa.getVlrString().compareTo("F") == 0) {
	  txtCnpjCont.setEnabled(false);
	  txtInscCont.setEnabled(false);
	  txtCpfCont.setEnabled(true);
	  txtRgCont.setEnabled(true);
	}
  }
  public void execShow(Connection cn) {
  	lcAtiv.setConexao(cn);
	lcAtivFK.setConexao(cn);
    lcVend.setConexao(cn);      
    lcSetor.setConexao(cn);
    super.execShow(cn);
  }
  public void stateChanged(ChangeEvent cevt){
	  if (cevt.getSource()==tpn) {
		  if (tpn.getSelectedIndex()==0)
	      txtCodCont.requestFocus();
      else if (tpn.getSelectedIndex()==4)
		  txaObs.requestFocus();
	  }
  }	
}