/**
 * @version 24/02/2004 <BR>
 * @author Setpoint Informática Ltda./Robson Sanchez<BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FSimilar.java <BR>
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
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.freedom.componentes.JLabelPad;

import org.freedom.acao.PostEvent;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FDados;

public class FSimilar extends FDados implements ActionListener{
	private JTextFieldPad txtRefProdSim = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
	private JTextFieldPad txtCodProd = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
	private JTextFieldPad txtRefProd = new JTextFieldPad(JTextFieldPad.TP_STRING,13,0);
	private JTextFieldPad txtRefProdSimFK = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
	private JTextFieldFK txtDescProd = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
	private ListaCampos lcProd = new ListaCampos(this,"PD");
	private ListaCampos lcProd2 = new ListaCampos(this,"PD");	
	private boolean[] bPrefs = null;
	
	public FSimilar() {
		setTitulo("Cadastro de Similaridades");
		setAtribos( 30, 30, 570, 170);
 
		lcProd.add(new GuardaCampo( txtCodProd, "CodProd", "Cód.prod.", ListaCampos.DB_PK, true));
		lcProd.add(new GuardaCampo( txtDescProd, "DescProd", "Descrição do produto", ListaCampos.DB_SI, true));
		lcProd.add(new GuardaCampo( txtRefProd, "RefProd", "Referência", ListaCampos.DB_SI, true));
				
		lcProd.montaSql(false, "PRODUTO", "EQ");
		lcProd.setQueryCommit(false);
		lcProd.setReadOnly(true);
		txtCodProd.setTabelaExterna(lcProd);
		txtCodProd.setNomeCampo("CodProd");
	
		//FK do produto (*Somente em caso de referências este listaCampos 
		//Trabalha como gatilho para o listaCampos de produtos, assim
		//carregando o código do produto que será armazenado no Banco)

		lcProd2.add(lcProd.add(new GuardaCampo( txtRefProd, "DescProd", "Referência do produto", ListaCampos.DB_PK, true)));
		lcProd2.add(new GuardaCampo( txtCodProd, "CodProd", "Cód.Prod.", ListaCampos.DB_SI, true));
		lcProd2.add(new GuardaCampo( txtDescProd, "DescProd", "Descrição do produto", ListaCampos.DB_SI, true));
		
		txtRefProd.setNomeCampo("RefProd");
		txtRefProd.setListaCampos(lcCampos);
		lcProd2.setWhereAdic("ATIVOPROD='S'");
		lcProd2.montaSql(false, "PRODUTO", "EQ");
		lcProd2.setQueryCommit(false);
		lcProd2.setReadOnly(true);
		txtRefProd.setTabelaExterna(lcProd2);
	}

	private void montaTela() {

		adicCampo(txtRefProdSim, 7, 20, 120, 20,"RefProdSim","Cód.sim.",ListaCampos.DB_PK,true);
		adicCampo(txtRefProdSimFK, 7,60,120, 20,"RefProdSimFK","Cód.sim.",ListaCampos.DB_SI,false);
	
		
		
		if (bPrefs[0]) {
			txtRefProdSim.setBuscaAdic(new DLBuscaProd(this,con,"REFPROD"),true);
			txtRefProd.setBuscaAdic(new DLBuscaProd(this,con,"REFPROD"),false);
			adicCampoInvisivel(txtCodProd,"CodProd","Cód.prod.",ListaCampos.DB_PK,false);
			adicCampoInvisivel(txtRefProd,"RefProd","Ref.prod.",ListaCampos.DB_PK,false);	
			adic(new JLabelPad("Ref.Prod."), 130, 0, 100, 20);
			adic(txtRefProd, 130, 20, 100, 20);
		}
		else {
			txtRefProdSim.setBuscaAdic(new DLBuscaProd(this,con,"CODPROD"),true);			
			txtCodProd.setBuscaAdic(new DLBuscaProd(this,con,"CODPROD"),false);
			adicCampo(txtCodProd, 130, 20, 100, 20,"CodProd","Cód.prod.",ListaCampos.DB_SI,false);
		}
		adicDescFK(txtDescProd, 233, 20, 300, 20, "DescProd", "Descrição do produto");
		
		setListaCampos( true, "SIMILAR", "EQ");
		btImp.addActionListener(this);
		btPrevimp.addActionListener(this);
		lcCampos.setQueryInsert(false);
		lcCampos.addPostListener(this);
	}
	public void keyPressed(KeyEvent kevt) {
		if (kevt.getKeyCode() == KeyEvent.VK_ENTER) {
			if (kevt.getSource() == txtRefProdSimFK) {//Como este é o ultimo campo da tela executa-se o post.
				if ( (lcCampos.getStatus() == ListaCampos.LCS_INSERT) || (lcCampos.getStatus() == ListaCampos.LCS_EDIT)) {				
				    lcCampos.post();
				}
			}
		}
		super.keyPressed(kevt);
	}
	
	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == btPrevimp) {
			imprimir(true);
		}
		else if (evt.getSource() == btImp) 
			imprimir(false);
		super.actionPerformed(evt);
	}

	private boolean[] prefs() {
		boolean[] bRetorno = new boolean[1];
		String sSQL = "SELECT USAREFPROD FROM SGPREFERE1 WHERE CODEMP=? AND CODFILIAL=?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			bRetorno[0] = false;
			ps = con.prepareStatement(sSQL);
			ps.setInt(1,Aplicativo.iCodEmp);
			ps.setInt(2,ListaCampos.getMasterFilial("SGPREFERE1"));
			rs = ps.executeQuery();
			if (rs.next()) {
				bRetorno[0]=rs.getString("UsaRefProd").trim().equals("S");
			}
//      rs.close();
//      ps.close();
			if (!con.getAutoCommit())
				con.commit();
		}
		catch (SQLException err) {
			Funcoes.mensagemErro(this,"Erro ao carregar a tabela PREFERE1!\n"+err.getMessage());
		}
		return bRetorno;
	}
	
	private void imprimir(boolean bVisualizar) {
		ImprimeOS imp = new ImprimeOS("",con);
		int linPag = imp.verifLinPag()-1;
		imp.montaCab();
		imp.setTitulo("Relatório de Similaridades");
		DLRSimilar dl = new DLRSimilar();
		dl.setVisible(true);
		if (dl.OK == false) {
			dl.dispose();
			return;
		}
		String sSQL = "SELECT S.CODPROD,P.REFPROD,S.REFPRODSIM,P.DESCPROD "+
		     "FROM EQPRODUTO P, EQSIMILAR S WHERE P.CODEMP=S.CODEMPPD AND " +
		     "P.CODFILIAL=S.CODFILIALPD AND P.CODPROD=S.CODPROD AND "+
			 "S.CODEMP=? AND S.CODFILIAL=? ORDER BY "+dl.getValor();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(sSQL);
			ps.setInt(1,Aplicativo.iCodEmp);
			ps.setInt(2,ListaCampos.getMasterFilial("EQSIMILAR"));
			rs = ps.executeQuery();
			imp.limpaPags();
			while ( rs.next() ) {
				if (imp.pRow()==0) {
					imp.impCab(136);
					imp.say(imp.pRow()+0,0,""+imp.comprimido());
					imp.say(imp.pRow()+0,6,"Cód.prod.");
					imp.say(imp.pRow()+0,26,"Referência");
					imp.say(imp.pRow()+0,40,"Descrição do produto");
					imp.say(imp.pRow()+0,92,"Cód.similar");
					imp.say(imp.pRow()+1,0,""+imp.comprimido());
					imp.say(imp.pRow()+0,0,Funcoes.replicate("-",136));
				}
				imp.say(imp.pRow()+1,0,""+imp.comprimido());
				imp.say(imp.pRow()+0,2,Funcoes.alinhaDir(rs.getString("Codprod"),13));
				imp.say(imp.pRow()+0,26,rs.getString("Refprod"));
				imp.say(imp.pRow()+0,40,rs.getString("Descprod"));
				imp.say(imp.pRow()+0,92,rs.getString("refprodsim"));
				if (imp.pRow()>=linPag) {
					imp.incPags();
					imp.eject();
				}
			}
			
			imp.say(imp.pRow()+1,0,""+imp.comprimido());
			imp.say(imp.pRow()+0,0,Funcoes.replicate("=",136));
			imp.eject();
			
			imp.fechaGravacao();
			
//      rs.close();
//      ps.close();
			if (!con.getAutoCommit())
				con.commit();
			dl.dispose();
		}  
		catch ( SQLException err ) {
			Funcoes.mensagemErro(this,"Erro consulta tabela de similaridades!"+err.getMessage());      
		}
		
		if (bVisualizar) {
			imp.preview(this);
		}
		else {
			imp.print();
		}
	}
	public void setConexao(Connection cn) {
		super.setConexao(cn);
		bPrefs = prefs(); //Carrega as preferências
		montaTela();
		lcProd.setConexao(cn);
		lcProd2.setConexao(cn);
	}
	public boolean achaSimilarFK(){
	  boolean bRetorno = false;
	  String sSQL = "SELECT REFPRODSIM FROM EQSIMILAR WHERE CODEMP=? AND CODFILIAL=? AND REFPRODSIM=? ";
	  try {
	  	PreparedStatement ps = con.prepareStatement(sSQL);
	  	ps.setInt(1,Aplicativo.iCodEmp);
	  	ps.setInt(2,ListaCampos.getMasterFilial("SGPREFERE1"));
	  	ps.setString(3,txtRefProdSimFK.getText().trim());
	  	ResultSet rs = ps.executeQuery();
	  	if (rs.next())
	  	  bRetorno = true;
	  	rs.close();
	  	ps.close();
	  }
	  catch (SQLException err) {
	  	Funcoes.mensagemErro(this,"Erro ao buscar similaridade.\n"+err.getMessage());
	  }  
	  return bRetorno;
	}
	public boolean achaSimilar(){
		boolean bRetorno = false;
		String sSQL = "SELECT REFPRODSIM FROM EQSIMILAR WHERE CODEMP=? AND CODFILIAL=? AND REFPRODSIM=? ";
		try {
			PreparedStatement ps = con.prepareStatement(sSQL);
			ps.setInt(1,Aplicativo.iCodEmp);
			ps.setInt(2,ListaCampos.getMasterFilial("SGPREFERE1"));
			ps.setString(3,txtRefProdSim.getText().trim());
			ResultSet rs = ps.executeQuery();
			if (rs.next())
				bRetorno = true;
			rs.close();
			ps.close();
		}
		catch (SQLException err) {
			Funcoes.mensagemErro(this,"Erro ao buscar similaridade.\n"+err.getMessage());
		}  
		return bRetorno;
	}
	
	public boolean gravaSimilar() {
      boolean bRetorno = true;
	  String sSQL = "";      
	  if (txtRefProdSimFK.getText().trim().equals("")){
        if (!achaSimilar())
	  	  txtRefProdSimFK.setText(txtRefProdSim.getText());
        else { 
          Funcoes.mensagemInforma(this,"Similaridade já existe!");
          bRetorno = false;
        }  
      }
	  else {
	    sSQL = "INSERT INTO EQSIMILAR (CODEMP,CODFILIAL,REFPRODSIM,REFPRODSIMFK) VALUES(?,?,?,?)";		
	  
        try {
      	  PreparedStatement ps = con.prepareStatement(sSQL);		
      	  ps.setInt(1,Aplicativo.iCodEmp);
      	  ps.setInt(2,ListaCampos.getMasterFilial("SGPREFERE1"));
      	  ps.setString(3,txtRefProdSimFK.getText().trim());
      	  ps.setString(4,txtRefProdSimFK.getText().trim());
      	  ps.executeUpdate();
      	  ps.close();
      	  if (!con.getAutoCommit())
      		con.commit();      	
        }
        catch (SQLException err) {
      	  Funcoes.mensagemErro(this,"Erro ao gravar  similaridade circular.\n"+err.getMessage());
        }
	  }  
      return bRetorno;
	}

	public void beforePost(PostEvent pevt) { 
		if (pevt.getListaCampos() == lcCampos) {
          if (!achaSimilarFK()) {
            if (!gravaSimilar())
              pevt.cancela();	    
          }
		}
	}
}
