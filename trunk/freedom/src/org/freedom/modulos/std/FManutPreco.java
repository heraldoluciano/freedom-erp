/**
 * @version 12/07/2004 <BR>
 * @author Setpoint Informática Ltda./Robson Sanchez<BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FManutPreco.java <BR>
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
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import org.freedom.componentes.JLabelPad;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.freedom.acao.RadioGroupEvent;
import org.freedom.acao.RadioGroupListener;
import org.freedom.bmps.Icone;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JComboBoxPad;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.JPanelPad;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FFilho;

public class FManutPreco extends FFilho implements ActionListener, RadioGroupListener {
  private JPanelPad pinCli = new JPanelPad(400,400);
  private JPanel pnRod = new JPanel(new BorderLayout());
  private JTextFieldPad txtCodMarca = new JTextFieldPad(JTextFieldPad.TP_STRING,6,0);
  private JTextFieldPad txtCodGrup = new JTextFieldPad(JTextFieldPad.TP_STRING,14,0);
  private JTextFieldPad txtCodPlanoPag = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtCodTab = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtCodClasCli = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldFK txtDescMarca = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
  private JTextFieldFK txtDescGrup = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
  private JTextFieldFK txtDescPlanoPag = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
  private JTextFieldFK txtDescTab = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
  private JTextFieldFK txtDescClasCli = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
  private JTextFieldPad txtMultiplic = new JTextFieldPad(JTextFieldPad.TP_DECIMAL,7,2);
  private JRadioGroup rgTipoOper = null;
  private JRadioGroup rgOrigem = null;
  private JComboBoxPad cbOperador = null;
  private JButton btGerar = new JButton("Gerar",Icone.novo("btGerar.gif"));
  private JButton btSair = new JButton("Sair",Icone.novo("btSair.gif"));
  private ListaCampos lcMarca = new ListaCampos(this);
  private ListaCampos lcGrup = new ListaCampos(this);
  private ListaCampos lcPlanoPag = new ListaCampos(this);
  private ListaCampos lcTabPreco = new ListaCampos(this);
  private ListaCampos lcClasCli = new ListaCampos(this);
  private Vector vDescTipoOper = new Vector();
  private Vector vSelTipoOper = new Vector();
  private Vector vDescOrigem = new Vector();
  private Vector vSelOrigem = new Vector();
  private Vector vDescOperador = new Vector();
  private Vector vSelOperador = new Vector();
  private int iCasasDec = 0; 
  public FManutPreco() {
    setTitulo("Manutenção de Preços");
    setAtribos(10,10,450,450);
    
    Container c = getContentPane();
        
    vDescTipoOper.addElement("Atualiza preço base");
    vDescTipoOper.addElement("Atualiza preço da tabela");
    vSelTipoOper.addElement("B");
    vSelTipoOper.addElement("P");
    rgTipoOper = new JRadioGroup(1,2,vDescTipoOper,vSelTipoOper);
    
    vDescOperador.addElement("/");
    vDescOperador.addElement("X");
    vSelOperador.addElement("/");
    vSelOperador.addElement("*");
    
    cbOperador = new JComboBoxPad(vDescOperador, vSelOperador, JComboBoxPad.TP_STRING, 1, 0);
    cbOperador.setVlrString("/");
    
    Funcoes.setBordReq(txtMultiplic,true);
    habFiltros("B");

    vDescOrigem.addElement("Custo MPM");
    vDescOrigem.addElement("Custo PEPS");
    vDescOrigem.addElement("Preço base");
    vDescOrigem.addElement("Filtros(Tab.Preços)");
    vSelOrigem.addElement("M");
    vSelOrigem.addElement("P");
    vSelOrigem.addElement("B");
    vSelOrigem.addElement("T");
    rgOrigem = new JRadioGroup(2,2,vDescOrigem,vSelOrigem);

    txtCodMarca.setNomeCampo("CodMarca");
    lcMarca.add(new GuardaCampo( txtCodMarca, "CodMarca", "Cód.marca", ListaCampos.DB_PK, false));
    lcMarca.add(new GuardaCampo( txtDescMarca, "DescMarca", "Decrição da marca", ListaCampos.DB_SI, false));
    lcMarca.montaSql(false,"MARCA", "EQ");
    lcMarca.setReadOnly(true);
    txtCodMarca.setTabelaExterna(lcMarca);
    txtCodMarca.setFK(true);
    txtDescMarca.setListaCampos(lcMarca);
    
    txtCodGrup.setNomeCampo("CodGrup");
    lcGrup.add(new GuardaCampo( txtCodGrup, "CodGrup", "Cód.grupo", ListaCampos.DB_PK, false));
    lcGrup.add(new GuardaCampo( txtDescGrup, "DescGrup", "Decrição do grupo", ListaCampos.DB_SI, false));
    lcGrup.montaSql(false,"GRUPO", "EQ");
    lcGrup.setReadOnly(true);
    txtCodGrup.setTabelaExterna(lcGrup);
    txtCodGrup.setFK(true);
    txtDescGrup.setListaCampos(lcGrup);

    txtCodPlanoPag.setNomeCampo("CodPlanoPag");
    lcPlanoPag.add(new GuardaCampo( txtCodPlanoPag, "CodPlanoPag", "Cód.p.pag.", ListaCampos.DB_PK, false));
    lcPlanoPag.add(new GuardaCampo( txtDescPlanoPag, "DescPlanoPag", "Descrição do plano de pagamento", ListaCampos.DB_SI, false));
    lcPlanoPag.montaSql(false,"PLANOPAG", "FN");
    lcPlanoPag.setReadOnly(true);
    txtCodPlanoPag.setTabelaExterna(lcPlanoPag);
    txtCodPlanoPag.setFK(true);
    txtDescPlanoPag.setListaCampos(lcPlanoPag);
    
    txtCodTab.setNomeCampo("CodTab");
    lcTabPreco.add(new GuardaCampo( txtCodTab, "CodTab", "Cód.tab.preço", ListaCampos.DB_PK, false));
    lcTabPreco.add(new GuardaCampo( txtDescTab, "DescTab", "Descrição da tabela de preço", ListaCampos.DB_SI, false));
    lcTabPreco.montaSql(false,"TABPRECO", "VD");
    lcTabPreco.setReadOnly(true);
    txtCodTab.setTabelaExterna(lcTabPreco);
    txtCodTab.setFK(true);
    txtDescTab.setListaCampos(lcTabPreco);

    txtCodClasCli.setNomeCampo("CodClasCli");
    lcClasCli.add(new GuardaCampo( txtCodClasCli, "CodClasCli", "Cód.c.cli", ListaCampos.DB_PK, false));
    lcClasCli.add(new GuardaCampo( txtDescClasCli, "DescClasCli", "Descrição da classificação do cliente", ListaCampos.DB_SI, false));
    lcClasCli.montaSql(false,"CLASCLI", "VD");
    lcClasCli.setReadOnly(true);
    txtCodClasCli.setTabelaExterna(lcClasCli);
    txtCodClasCli.setFK(true);
    txtDescClasCli.setListaCampos(lcClasCli);
    
    c.setLayout(new BorderLayout());
    c.add(pnRod,BorderLayout.SOUTH);
    c.add(pinCli,BorderLayout.CENTER);
    
    btSair.setPreferredSize(new Dimension(100,30));
    
    pnRod.setBorder(BorderFactory.createEtchedBorder());
    pnRod.setPreferredSize(new Dimension(300,30));
    pnRod.setLayout(new BorderLayout());
    pnRod.add(btSair,BorderLayout.EAST);
    
    pinCli.adic(rgTipoOper,7,3,380,30);
    pinCli.adic(new JLabelPad(" Preço origem: "),7,35,300,20);
    pinCli.adic(rgOrigem,7,55,300,60);
    pinCli.adic(new JLabelPad("Preço"),310,35,80,20);
    pinCli.adic(cbOperador,310,55,80,20);
    pinCli.adic(txtMultiplic,310,75,80,20);
    pinCli.adic(new JLabelPad("Cód.marca"),7,115,300,20);
    pinCli.adic(txtCodMarca,7,135,90,20);
    pinCli.adic(new JLabelPad("Descrição da marca"),100,115,300,20);
    pinCli.adic(txtDescMarca,100,135,300,20);
    pinCli.adic(new JLabelPad("Cód.grupo"),7,155,300,20);
    pinCli.adic(txtCodGrup,7,175,90,20);
    pinCli.adic(new JLabelPad("Descrição do grupo"),100,155,300,20);
    pinCli.adic(txtDescGrup,100,175,300,20);
    pinCli.adic(new JLabelPad("Cód.tab.preço"),7,195,300,20);
    pinCli.adic(txtCodTab,7,215,90,20);
    pinCli.adic(new JLabelPad("Descrição da tabela de preços"),100,195,300,20);
    pinCli.adic(txtDescTab,100,215,300,20);
    pinCli.adic(new JLabelPad("Cód.p.pag."),7,235,300,20);
    pinCli.adic(txtCodPlanoPag,7,255,90,20);
    pinCli.adic(new JLabelPad("Descrição do plano de pagamento"),100,235,300,20);
    pinCli.adic(txtDescPlanoPag,100,255,300,20);
    pinCli.adic(new JLabelPad("Cód.c.cli."),7,275,300,20);
    pinCli.adic(txtCodClasCli,7,295,90,20);
    pinCli.adic(new JLabelPad("Descrição da classificação do cliente"),100,275,300,20);
    pinCli.adic(txtDescClasCli,100,295,300,20);
    
    pinCli.adic(btGerar,7,320,100,30);
    
    rgTipoOper.addRadioGroupListener(this);
    
    btSair.addActionListener(this);
    btGerar.addActionListener(this);
  }
  
  private void habFiltros(String sTipo) {
  	if (sTipo.equals("B")) {
  	    Funcoes.setBordReq(txtCodPlanoPag,false);
  	    Funcoes.setBordReq(txtCodTab,false);
  	}
  	else {
  	    Funcoes.setBordReq(txtCodPlanoPag,true);
  	    Funcoes.setBordReq(txtCodTab,true);
  	}
  }
  private void gerar(String sTipoOper, String sOrigem) {
  	double deMultiplic = 0;
  	String sCodMarca = "";
  	String sCodGrup = "";
  	String sSqlConsultaProd = "";
  	String sWhereConsultaProd = "";
  	String sSqlConsultaPreco = "";
  	String sWhereConsultaPreco = "";
  	String sSqlInclusao = "";
  	String sSqlAtualizar = "";
  	String sOperador = "/";
  	int iCodPlanoPag = 0;
  	int iCodTab = 0;
  	int iCodClasCli = 0;
  	int iRegsAtu = 0;
  	int iRegsInc = 0;
  	double dePrecoProd = 0;
  	Cursor cursorAtual = getCursor();
  	ResultSet rsProd = null;
  	PreparedStatement psProd = null;
  	ResultSet rsPreco = null;
  	PreparedStatement psPreco = null;
  	PreparedStatement psInclusao = null;
  	PreparedStatement psAtualizar = null;
  	boolean bTemPreco = false;
  	try {
  		deMultiplic = txtMultiplic.getVlrDouble().doubleValue();
  		sCodMarca = txtCodMarca.getVlrString().trim();
  		sCodGrup = txtCodGrup.getVlrString().trim();
  		iCodPlanoPag = txtCodPlanoPag.getVlrInteger().intValue();
  		iCodTab = txtCodTab.getVlrInteger().intValue();
  		iCodClasCli = txtCodClasCli.getVlrInteger().intValue();
  		sOperador = cbOperador.getVlrString();
  		
  		if (deMultiplic<=0) {
  			Funcoes.mensagemInforma(this,"Multiplicador inválido!");
  			txtMultiplic.requestFocus();
  			return;
  		}
  		
  		if (sTipoOper.equals("B")) {
  			Funcoes.mensagemInforma(this,"Opção não disponível!");
  			return;
  		}
  		else {
  			if (iCodTab==0) {
  				Funcoes.mensagemInforma(this,"Tabela de preços obrigatória!");
  				txtCodTab.requestFocus();
  				return;
  			}
  			if (iCodPlanoPag==0) {
  				Funcoes.mensagemInforma(this,"Plano de pagamento obrigatório!");
  				txtCodPlanoPag.requestFocus();
  				return;
  			}
  			if (sOrigem.equals("B")) {
  				sWhereConsultaProd = " WHERE CVPROD IN ('V','A') AND TIPOPROD IN ('P','S','F') ";
  				if (!sCodGrup.equals("")) {
  					sWhereConsultaProd += " AND CODGRUP LIKE '"+sCodGrup+"%'";
  				}
  				if (!sCodMarca.equals("")) {
  					sWhereConsultaProd += " AND CODMARCA='"+sCodMarca+"'";
  				}
  				sSqlConsultaProd = "SELECT CODEMP,CODFILIAL,CODPROD,PRECOBASEPROD FROM EQPRODUTO "+sWhereConsultaProd;
  				sWhereConsultaPreco = " WHERE CODEMPTB="+Aplicativo.iCodEmp+
  						" AND CODFILIALTB="+ListaCampos.getMasterFilial("VDTABPRECO")+" AND CODTAB="+iCodTab+
						" AND CODEMPPG="+Aplicativo.iCodEmp+" AND CODFILIALPG="+
						ListaCampos.getMasterFilial("FNPLANOPAG")+" AND CODPLANOPAG="+iCodPlanoPag+
						" AND CODEMP=? AND CODFILIAL=? AND CODPROD=?";
  				if (iCodClasCli!=0) {
  					sWhereConsultaPreco += " AND CODEMPCC="+Aplicativo.iCodEmp+" AND CODFILIALCC="+
						ListaCampos.getMasterFilial("VDCLASCLI")+" AND CODCLASCLI="+iCodClasCli;
  				}
  				sSqlConsultaPreco = "SELECT CODEMP,CODFILIAL,CODPROD,CODPRECOPROD,CODEMPTB,CODFILIALTB,CODTAB," +
  						"CODEMPCC,CODFILIALCC,CODCLASCLI,CODEMPPG,CODFILIALPG,CODPLANOPAG,PRECOPROD " +
  						"FROM VDPRECOPROD "+sWhereConsultaPreco;
  				sSqlInclusao = "INSERT INTO VDPRECOPROD " +
  						"(CODEMP,CODFILIAL,CODPROD,CODPRECOPROD,CODEMPTB,CODFILIALTB,CODTAB," +
  						"CODEMPCC,CODFILIALCC,CODCLASCLI,CODEMPPG,CODFILIALPG,CODPLANOPAG,PRECOPROD) " +
  						"VALUES (?,?,?,("+
						" COALESCE( (SELECT MAX(CODPRECOPROD)+1 FROM VDPRECOPROD WHERE CODEMP=? AND " +
						"CODFILIAL=? AND CODPROD=?) , 1)"+
						"),?,?,?,?,?,?,?,?,?,?)";
  				sSqlAtualizar = "UPDATE VDPRECOPROD SET PRECOPROD=? "+
				      sWhereConsultaPreco+" AND CODPRECOPROD=?"; 
  			}
  			else {
  				Funcoes.mensagemInforma(this,"Origem de preço inválida para a operação!");
  				return;
  			}
  		}
  		if (Funcoes.mensagemConfirma(this,"Confirma processamento?")!=JOptionPane.YES_OPTION) {
  			return;
  		}
  		try {
  	  		setCursor(new Cursor(Cursor.WAIT_CURSOR));
  	  		try {
  	  			psProd = con.prepareStatement(sSqlConsultaProd);
  	  			rsProd = psProd.executeQuery();
  	  			while (rsProd.next()) {
  	  				psPreco = con.prepareStatement(sSqlConsultaPreco);
  	  				psPreco.setInt(1,rsProd.getInt("CODEMP"));
  	  				psPreco.setInt(2,rsProd.getInt("CODFILIAL"));
  	  				psPreco.setInt(3,rsProd.getInt("CODPROD"));
  	  				rsPreco = psPreco.executeQuery();
  	  				bTemPreco = rsPreco.next();
					dePrecoProd = rsProd.getDouble("PRECOBASEPROD");
					if (sOperador.equals("/"))
						dePrecoProd = Funcoes.arredDouble(dePrecoProd / deMultiplic,iCasasDec);
					else
						dePrecoProd = Funcoes.arredDouble(dePrecoProd * deMultiplic,iCasasDec);
					//dePrecoProd = dePrecoProd * deMultiplic;
					
  	  				if (bTemPreco) {
  	  					if (rsProd.getInt("CODPROD")!=rsPreco.getInt("CODPROD")) {
  	  						bTemPreco = false;
  	  					}
  	  				}
  	  				if (bTemPreco) {
 						psAtualizar = con.prepareStatement(sSqlAtualizar);
 						psAtualizar.setDouble(1,dePrecoProd);
    	  				psAtualizar.setInt(2,rsProd.getInt("CODEMP"));
    	  				psAtualizar.setInt(3,rsProd.getInt("CODFILIAL"));
    	  				psAtualizar.setInt(4,rsProd.getInt("CODPROD"));
    	  				psAtualizar.setInt(5,rsPreco.getInt("CODPRECOPROD"));
    	  				psAtualizar.execute();
    	  				psAtualizar.close();
      	  				iRegsAtu ++;
  	  				}
  	  				else {
  						psInclusao = con.prepareStatement(sSqlInclusao);
						psInclusao.setInt(1,rsProd.getInt("CODEMP"));
  						psInclusao.setInt(2,rsProd.getInt("CODFILIAL"));
  						psInclusao.setInt(3,rsProd.getInt("CODPROD"));
  						psInclusao.setInt(4,rsProd.getInt("CODEMP"));
  						psInclusao.setInt(5,rsProd.getInt("CODFILIAL"));
  						psInclusao.setInt(6,rsProd.getInt("CODPROD"));
  					    psInclusao.setInt(7,Aplicativo.iCodEmp);
  					    psInclusao.setInt(8,ListaCampos.getMasterFilial("VDTABPRECO"));
  					    psInclusao.setInt(9,iCodTab);
  					    if (iCodClasCli==0) {
  					    	psInclusao.setNull(10,Types.INTEGER);
  					    	psInclusao.setNull(11,Types.INTEGER);
  					    	psInclusao.setNull(12,Types.INTEGER);
  					    }
  					    else {
  					    	psInclusao.setInt(10,Aplicativo.iCodEmp);
  					    	psInclusao.setInt(11,ListaCampos.getMasterFilial("VDCLASCLI"));
  					    	psInclusao.setInt(12,iCodClasCli);
  					    }
 					    psInclusao.setInt(13,Aplicativo.iCodEmp);
  					    psInclusao.setInt(14,ListaCampos.getMasterFilial("FNPLANOPAG"));
  					    psInclusao.setInt(15,iCodPlanoPag);
  						psInclusao.setDouble(16,dePrecoProd);
  						psInclusao.execute();
  						psInclusao.close();
      	  				iRegsInc ++;
  	  				}
  	  				rsPreco.close();
  	  				psPreco.close();
  	  			}
  	  			rsProd.close();
  	  			psProd.close();
  	  			if (!con.getAutoCommit()) {
  	  				con.commit();
  	  			}
  	  			Funcoes.mensagemInforma(this,"Registros incluídos: "+iRegsInc+"\n"+
  	  					"Registros atualizados: "+iRegsAtu+"\n"+
						"Total processados: "+(iRegsAtu+iRegsInc));
  	  		}
  	  		catch (SQLException e) {
  	  			Funcoes.mensagemErro(this,"Erro atualizando tabela!\n"+e.getMessage());
  	  		}
  		}
  		finally {
  			setCursor(cursorAtual);
  		}
  	}
  	finally {
  		deMultiplic = 0;
  		sCodMarca = null;
  		sCodGrup = null;
  		sSqlConsultaProd = null;
  		sWhereConsultaProd = null;
  		sSqlConsultaPreco = null;
  		sWhereConsultaPreco = null;
  		sSqlInclusao = null;
  		sSqlAtualizar = null;
  		sOperador = null;
  		iCodPlanoPag = 0;
  		iCodTab = 0;
  		iCodClasCli = 0;
  		iRegsAtu = 0;
  		iRegsInc = 0;
  		cursorAtual = null;
  		rsProd = null;
  		psProd = null;
  		rsPreco = null;
  		psPreco = null;
  		psInclusao = null;
  		psAtualizar = null;
  		bTemPreco = false;
  		dePrecoProd = 0 ;
  	}
 	
  }
  public void actionPerformed(ActionEvent evt) {
    if (evt.getSource() == btSair) {
      dispose();
    }
    else if (evt.getSource() == btGerar) {
      gerar(rgTipoOper.getVlrString(),rgOrigem.getVlrString());
    }
  }
  public void valorAlterado(RadioGroupEvent rgevt) {
  	habFiltros(rgTipoOper.getVlrString());
  }
  
  public void setConexao(Connection cn) {
    setConexao(cn);
    lcMarca.setConexao(cn);
    lcGrup.setConexao(cn);
    lcPlanoPag.setConexao(cn);
    lcTabPreco.setConexao(cn);
    lcClasCli.setConexao(cn);
    iCasasDec = getCasasDecimais();
  }
  
  private int getCasasDecimais() {
  	int iRetorno = 2;
  	String sSQL = null;
  	PreparedStatement ps = null;
  	ResultSet rs = null;
  	try {
  		sSQL = "SELECT CASASDEC FROM SGPREFERE1 WHERE CODEMP=? AND CODFILIAL=?";
  		try {
  			ps = con.prepareStatement(sSQL);
  			ps.setInt(1,Aplicativo.iCodEmp);
  			ps.setInt(2,Aplicativo.iCodFilial);
  			rs = ps.executeQuery();
  			if (rs.next()) {
  				if (rs.getString("CASASDEC")!=null) {
  					iRetorno = rs.getInt("CASASDEC");
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
  	}
  	//	System.out.println("Retornou setor:"+bRet);
  	return iRetorno;
  }  
  
}


