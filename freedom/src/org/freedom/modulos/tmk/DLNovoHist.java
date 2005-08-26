/**
 * @version 23/06/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.tmk <BR>
 * Classe: @(#)DLNovoHist.java <BR>
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
 * Nova chamada de histórico de contatos.
 * 
 */

package org.freedom.modulos.tmk;

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
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JPanelPad;
import javax.swing.JScrollPane;

import org.freedom.bmps.Icone;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JComboBoxPad;
import org.freedom.componentes.JTextAreaPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FFDialogo;


public class DLNovoHist extends FFDialogo {
	private static final long serialVersionUID = 1L;

	private JPanelPad pnCab = new JPanelPad(JPanelPad.TP_JPANEL,new GridLayout(1,1));
	private JTextFieldPad txtCodCont = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
	private JTextFieldFK txtNomeCont = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
	private JTextFieldPad txtCodAtend = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
	private JTextFieldFK txtNomeAtend = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
	private JTextAreaPad txaDescAtend = new JTextAreaPad();
	private ListaCampos lcCont = new ListaCampos(this);
	private ListaCampos lcAtend = new ListaCampos(this);
	private JScrollPane spnDesc = new JScrollPane(txaDescAtend);
	private JLabelPad lbImg = new JLabelPad(Icone.novo("bannerTMKhistorico.jpg"));
	private Vector vVals = new Vector();
	private Vector vLabs = new Vector();
	private JComboBoxPad cbSit = null; 
	private String[] sValsAgen = null;
	public DLNovoHist(int iCodCont,Component cOrig) {
		super(cOrig);
		setTitulo("Nova chamada");
		setAtribos(460,470);		
		
		vVals.addElement("");
		vVals.addElement("RJ");
		vVals.addElement("AG");
		vLabs.addElement("<--Selecione-->");	
		vLabs.addElement("Rejeitado");		
		vLabs.addElement("Agendar ligação/visita");
		cbSit = new JComboBoxPad(vLabs, vVals, JComboBoxPad.TP_STRING, 2, 0);
		//cbSit.setVlrString("AG");
		
		lcCont.add(new GuardaCampo( txtCodCont, "CodCto", "Cód.Cont", ListaCampos.DB_PK, txtNomeCont, false));
		lcCont.add(new GuardaCampo( txtNomeCont, "NomeCto", "Nome", ListaCampos.DB_SI, false));
		lcCont.montaSql(false, "CONTATO", "TK");    
		lcCont.setReadOnly(true);
		txtCodCont.setTabelaExterna(lcCont);
		txtCodCont.setFK(true);
		txtCodCont.setNomeCampo("CodCto");
		
		lcAtend.add(new GuardaCampo( txtCodAtend, "CodAtend", "Cód.Atend", ListaCampos.DB_PK, txtNomeAtend, true));
		lcAtend.add(new GuardaCampo( txtNomeAtend, "NomeAtend", "Nome", ListaCampos.DB_SI, false));
		lcAtend.montaSql(false, "ATENDENTE", "AT");    
		lcAtend.setReadOnly(true);
		txtCodAtend.setTabelaExterna(lcAtend);
		txtCodAtend.setFK(true);
		txtCodAtend.setNomeCampo("CodAtend");
	
	    pnCab.setPreferredSize(new Dimension(500,60));
		pnCab.add(lbImg);
	    c.add(pnCab,BorderLayout.NORTH);
	    
		adic(new JLabelPad("Código e nome do contato"),7,5,200,20);
		adic(txtCodCont,7,25,80,20);
		adic(txtNomeCont,90,25,197,20);
		adic(new JLabelPad("Situação"),290,5,150,20);
		adic(cbSit,290,25,150,20);
		adic(new JLabelPad("Código e nome do atendente"),7,45,200,20);
		adic(txtCodAtend,7,65,80,20);
		adic(txtNomeAtend,90,65,197,20);
		
		JLabelPad lbChamada = new JLabelPad(" Chamada ");
		lbChamada.setOpaque(true);
		JLabelPad lbLinha = new JLabelPad();
		lbLinha.setBorder(BorderFactory.createEtchedBorder());
		
		adic(lbChamada,20,90,80,20);
		adic(lbLinha,7,100,433,2);
		adic(spnDesc,7,115,433,220);
		
		txtCodCont.setVlrInteger(new Integer(iCodCont));
		txtCodCont.setAtivo(false);
		
	}
	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == btOK) {
			if (txtCodAtend.getVlrString().equals("")) { 
				Funcoes.mensagemInforma(this,"Código do atendente inválido!");
				return;
			}
			else if (txaDescAtend.getVlrString().equals("")) {
				Funcoes.mensagemInforma(this,"Não foi digitado nenhum procedimento!");
				return;
			}
			else if (cbSit.getVlrString().equals("AG")) {
				DLNovoAgen dl = new DLNovoAgen(this);
				dl.setConexao(con);
				dl.setVisible(true);
				if (!dl.OK)
					return;
				sValsAgen = dl.getValores();
				dl.dispose();
			}
		}
		super.actionPerformed(evt);
	}
	public void buscaAtend() {
		String sSQL = "SELECT CODATEND FROM ATATENDENTE" +
				              " WHERE CODEMPUS=? AND CODFILIALUS=? AND IDUSU=?";
		try {
			PreparedStatement ps = con.prepareStatement(sSQL);
			ps.setInt(1,Aplicativo.iCodEmp);
			ps.setInt(2,Aplicativo.iCodFilialPad);
			ps.setString(3,Aplicativo.strUsuario);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				txtCodAtend.setVlrInteger(new Integer(rs.getInt("CodAtend")));
				lcAtend.carregaDados();
			}
			rs.close();
			ps.close();
		}
		catch (SQLException err) {
			Funcoes.mensagemErro(this,"Erro ao buscar o atendente atual!\n"+err.getMessage(),true,con,err);
			err.printStackTrace();
		}
		
    }
	public void setValores(String[] sVal) {
		txaDescAtend.setVlrString(sVal[0]);
		txtCodAtend.setVlrString(sVal[1]);
		cbSit.setVlrString(sVal[2]);
		lcAtend.carregaDados();
	}
	public String[] getValores() {
		String[] sVal = new String[12];
		sVal[0] = txaDescAtend.getVlrString();
		sVal[1] = txtCodAtend.getVlrString();
		sVal[2] = cbSit.getVlrString();
		if (sValsAgen != null) {
			sVal[3] = sValsAgen[0];
			sVal[4] = sValsAgen[1];
			sVal[5] = sValsAgen[2];
			sVal[6] = sValsAgen[3];
			sVal[7] = sValsAgen[4];
			sVal[8] = sValsAgen[5];
			sVal[9] = sValsAgen[6];
			sVal[10] = sValsAgen[7];
			sVal[11] = sValsAgen[8];
		}
		return sVal;
	}
	public void setConexao(Connection cn) {
		super.setConexao(cn);
		lcAtend.setConexao(cn);
		lcCont.setConexao(cn);
		lcCont.carregaDados();
		buscaAtend();
	}
}
