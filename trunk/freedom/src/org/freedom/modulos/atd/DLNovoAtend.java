/**
 * @version 23/06/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.atd <BR>
 * Classe: @(#)DLNovoAtend.java <BR>
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
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.freedom.acao.JComboBoxEvent;
import org.freedom.acao.JComboBoxListener;
import org.freedom.bmps.Icone;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JComboBoxPad;
import org.freedom.componentes.JTextAreaPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.layout.LeiauteGR;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.DLPrinterJob;
import org.freedom.telas.FFDialogo;


public class DLNovoAtend extends FFDialogo implements JComboBoxListener {
	private JPanel pnCab = new JPanel(new GridLayout(1,1));
	private JPanel pnBotoes = new JPanel(new BorderLayout());
	private JTextFieldPad txtCodConv = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
	private JTextFieldFK txtNomeConv = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
	private JTextFieldPad txtCodAtend = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
	private JTextFieldFK txtNomeAtend = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
	private JTextAreaPad txaDescAtend = new JTextAreaPad();
	private Vector vValsTipo = new Vector();
	private Vector vLabsTipo = new Vector();
	private JComboBoxPad cbTipo = new JComboBoxPad(vLabsTipo,vValsTipo); 
	private Vector vValsSetor = new Vector();
	private Vector vLabsSetor = new Vector();
	private JComboBoxPad cbSetor = new JComboBoxPad(vLabsSetor,vValsSetor); 
	private ListaCampos lcConv = new ListaCampos(this);
	private ListaCampos lcAtend = new ListaCampos(this);
	private JScrollPane spnDesc = new JScrollPane(txaDescAtend);
	private JLabel lbImg = new JLabel(Icone.novo("bannerATD.jpg"));
	private Connection con = null;
	private JButton btMedida = new JButton(Icone.novo("btMedida.gif"));
	private String sPrefs[] = null;
	private int iDoc = 0;
//	boolean bImp = false; ///Verifica se jah foi impresso a ficha de levantamento.
	public DLNovoAtend(int iCodConv,Component cOrig) {
		super(cOrig);
		setTitulo("Novo atendimento");
		setAtribos(505,470);		
		
		lcConv.add(new GuardaCampo( txtCodConv, "CodConv", "Cód.conv.", ListaCampos.DB_PK, false),"txtCodVendx");
		lcConv.add(new GuardaCampo( txtNomeConv, "NomeConv", "Nome", ListaCampos.DB_SI, false),"txtCodVendx");
		lcConv.montaSql(false, "CONVENIADO", "AT");    
		lcConv.setReadOnly(true);
		txtCodConv.setTabelaExterna(lcConv);
		txtCodConv.setFK(true);
		txtCodConv.setNomeCampo("CodConv");
		
		lcAtend.add(new GuardaCampo( txtCodAtend, "CodAtend", "Cód.atend.", ListaCampos.DB_PK, false),"txtCodVendx");
		lcAtend.add(new GuardaCampo( txtNomeAtend, "NomeAtend", "Nome", ListaCampos.DB_SI, false),"txtCodVendx");
		lcAtend.montaSql(false, "ATENDENTE", "AT");    
		lcAtend.setReadOnly(true);
		txtCodAtend.setTabelaExterna(lcAtend);
		txtCodAtend.setFK(true);
		txtCodAtend.setNomeCampo("CodAtend");
	
		btMedida.setPreferredSize(new Dimension(30,30));	
				
	    pnCab.setPreferredSize(new Dimension(500,60));
		pnCab.add(lbImg);
	    c.add(pnCab,BorderLayout.NORTH);
		pnBotoes.setPreferredSize(new Dimension(35,60));
		pnBotoes.add(btMedida,BorderLayout.NORTH);
	    pnBotoes.setToolTipText("Ficha de medidas");
	    c.add(pnBotoes,BorderLayout.EAST);
	    
		adic(new JLabel("Código e nome do conveniado"),7,5,200,20);
		adic(txtCodConv,7,25,80,20);
		adic(txtNomeConv,90,25,197,20);
		adic(new JLabel("Tipo de atendimento"),290,5,150,20);
		adic(cbTipo,290,25,150,20);
		adic(new JLabel("Código e nome do atendente"),7,45,200,20);
		adic(txtCodAtend,7,65,80,20);
		adic(txtNomeAtend,90,65,197,20);
		adic(new JLabel("Setor"),290,45,150,20);
		adic(cbSetor,290,65,150,20);
		
		JPanel pnLbAtend = new JPanel(new GridLayout(1,1));
		pnLbAtend.add(new JLabel(" Atendimento"));
		JLabel lbLinha = new JLabel();
		lbLinha.setBorder(BorderFactory.createEtchedBorder());
		
		btMedida.addActionListener(this);
		cbTipo.addComboBoxListener(this);
		
		adic(pnLbAtend,20,90,90,20);
		adic(lbLinha,7,100,433,2);
		adic(spnDesc,7,115,433,220);
		
		txtCodConv.setVlrInteger(new Integer(iCodConv));
		txtCodConv.setAtivo(false);
				
	}
	private void montaComboTipo() {
		String sSQL = "SELECT CODTPATENDO,DESCTPATENDO FROM ATTIPOATENDO WHERE CODEMP=? AND CODFILIAL=?";
		try {
			PreparedStatement ps = con.prepareStatement(sSQL);
			ps.setInt(1,Aplicativo.iCodEmp);
			ps.setInt(2,ListaCampos.getMasterFilial("ATTIPOATENDO"));
			ResultSet rs = ps.executeQuery();
			vValsTipo.clear();
			vLabsTipo.clear();
			while(rs.next()) {
				vValsTipo.add(rs.getString("CodTpAtendo"));
				vLabsTipo.add(rs.getString("DescTpAtendo"));
			}
			cbTipo.setItens(vLabsTipo,vValsTipo);
			rs.close();
			ps.close();
		}
		catch(SQLException err) {
			Funcoes.mensagemErro(this,"Erro ao carregar os tipos de atendimento!\n"+err.getMessage());
		}
	}
	private void montaComboSetor() {
		String sTipo = cbTipo.getVlrString();
		if (sTipo == null || sTipo.equals(""))
			return; 
		String sSQL = "SELECT S.CODSETAT,S.DESCSETAT FROM ATSETOR S, ATTIPOATENDOSETOR TS" +
			           " WHERE S.CODEMP=TS.CODEMPST AND S.CODFILIAL=TS.CODFILIAL AND S.CODSETAT=TS.CODSETAT"+
			           " AND TS.CODEMP=? AND TS.CODFILIAL=? AND TS.CODTPATENDO=?";
			           
		try {
			PreparedStatement ps = con.prepareStatement(sSQL);
			ps.setInt(1,Aplicativo.iCodEmp);
			ps.setInt(2,ListaCampos.getMasterFilial("ATTIPOATENDO"));
			ps.setInt(3,Integer.parseInt(sTipo));
			ResultSet rs = ps.executeQuery();
			vValsSetor.clear();
			vLabsSetor.clear();
			while(rs.next()) {
				vValsSetor.add(rs.getString("CodSetAt"));
				vLabsSetor.add(rs.getString("DescSetAt"));
			}
			cbSetor.setItens(vLabsSetor,vValsSetor);
			rs.close();
			ps.close();
		}
		catch(SQLException err) {
			Funcoes.mensagemErro(this,"Erro ao carregar os setores!\n"+err.getMessage());
		}

		if (vValsSetor.size() <= 0)
			Funcoes.mensagemInforma(this,"Não existe setor cadastrado para este tipo de atendimento.");
		else if (vValsSetor.size() == 1)
			cbSetor.setEnabled(false);
		else
		    cbSetor.setEnabled(true);
	}
	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == btOK) {
			if (cbTipo.getVlrString().equals("")) {
				Funcoes.mensagemInforma(this,"O tipo de atendimento não foi selecionado!");
				return;
			}
			else if (txtCodAtend.getVlrString().equals("")) { 
				Funcoes.mensagemInforma(this,"Código do atendente inválido!");
				return;
			}
			else if (cbSetor.getVlrString().equals("")) {
				Funcoes.mensagemInforma(this,"O setor não foi selecionado!");
				return;
			}
			else if (txaDescAtend.getVlrString().equals("")) {
				Funcoes.mensagemInforma(this,"Não foi digitado nenhum procedimento!");
				return;
			}
/*			else if (sPrefs[0].equals(cbTipo.getVlrString()) && !bImp) {
				Funcoes.mensagemInforma(this,"Não foi impresso a ficha de levantamento!");
				return;
			} */
		}
		else if (evt.getSource() == btMedida) {
			if (sPrefs[1] == null) {
				Funcoes.mensagemInforma(this,"Não foi possível encontrar um org.freedom.layout de levantamento.");
				return;
			}
			try {
			  iDoc = getCodLev();
			  Vector vParam = new Vector();
			  LeiauteGR lei = (LeiauteGR)Class.forName("org.freedom.layout."+sPrefs[1].trim()).newInstance();      
			  lei.setConexao(con);
			  vParam.addElement(txtCodConv.getVlrInteger());
			  vParam.addElement(txtNomeAtend.getVlrString());
			  vParam.addElement(""+iDoc);
			  lei.setParam(vParam);
			  DLPrinterJob dl = new DLPrinterJob(lei,this);
			  //bImp = dl.OK;
			  dl.dispose();
			}
			catch (Exception err) {
			  Funcoes.mensagemInforma(this,"Não foi possível carregar org.freedom.layout de levantamento!\n"+err.getMessage());
			  err.printStackTrace();
			}
		}	
				
		super.actionPerformed(evt);
		
		
	}
    private String[] verifPref() {
    	String sRets[] = {"",""};
    	String sSQL = "SELECT CODTPATENDO,CLASSMEDIDA FROM SGPREFERE2 WHERE CODEMP=? AND CODFILIAL=?";
    	try {
          PreparedStatement ps = con.prepareStatement(sSQL);
          ps.setInt(1,Aplicativo.iCodEmp);
		  ps.setInt(2,Aplicativo.iCodFilial);
          ResultSet rs = ps.executeQuery();
          if (rs.next()) {
			  sRets[0] = rs.getString("CodTpAtendo") != null ? rs.getString("CodTpAtendo") : "";
        	  sRets[1] = rs.getString("ClassMedida");
          }
          rs.close();
          ps.close();
    	}
    	catch (SQLException err) {
    		Funcoes.mensagemErro(this,"Erro ao veficar levantamento. ");
    	}
    	return sRets;
    }
    private int getCodLev() {
    	int iRet = 0;
    	String sSQL = "SELECT ISEQ FROM SPGERANUM(?,?,?)";
    	try {
    		PreparedStatement ps = con.prepareStatement(sSQL);
    		ps.setInt(1,Aplicativo.iCodEmp);
    		ps.setInt(2,Aplicativo.iCodFilial);
    		ps.setString(3,"LV");
    		ResultSet rs = ps.executeQuery();
    		if (rs.next()) {
    			iRet = rs.getInt(1);
    		}
    		rs.close();
    		ps.close();
    		if (!con.getAutoCommit())
    			con.commit();
    	}
    	catch (SQLException err) {
    		Funcoes.mensagemErro(this,"Erro ao buscar novo código para levantamento.\n"+err.getMessage());
    	}
    	return iRet;
    }
    public void valorAlterado(JComboBoxEvent evt) {
      if (evt.getComboBoxPad() == cbTipo) {
      	montaComboSetor();
      }
    }
	public String[] getValores() {
		String[] sVal = new String[5];
		sVal[0] = cbTipo.getVlrString();
		sVal[1] = txtCodAtend.getVlrString();
		sVal[2] = cbSetor.getVlrString();
		sVal[3] = txaDescAtend.getVlrString();
		sVal[4] = ""+iDoc;
		return sVal;
	}
	public void setConexao(Connection cn) {
		con = cn;
		montaComboTipo();
		montaComboSetor();
		lcAtend.setConexao(cn);
		lcConv.setConexao(cn);
		lcConv.carregaDados();
		sPrefs = verifPref();
	}
}
