/**
 * @version 09/07/2004 <BR>
 * @author Setpoint Informática Ltda./Robson Sanchez <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FConsPreco.java <BR>
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
 */

package org.freedom.modulos.std;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.sql.ResultSet;
import java.util.Vector;

import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JPanelPad;
import javax.swing.JScrollPane;

import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.StringDireita;
import org.freedom.componentes.Tabela;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FFilho;


/**
 * @author robson
 *
 * Tela de consulta de preços
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class FConsPreco extends FFilho implements KeyListener {
	private static final long serialVersionUID = 1L;

    //private JPanelPad pnClienteGeral = new JPanelPad(JPanelPad.TP_JPANEL,new BorderLayout());
    private JPanelPad pinCab = new JPanelPad(800,55);
    private Tabela tbPreco = new Tabela();
    private JScrollPane spPreco = new JScrollPane(tbPreco);
    private JTextFieldPad txtDescProd = new JTextFieldPad(JTextFieldPad.TP_STRING,50,10);
	
	public FConsPreco() {
		super(false);
		//super();
		// 
		setTitulo("Consulta de preços");
		setAtribos(0,0,800,500);
		//setmmaximizable
		
	}
	
	private void montaTela() {
		tbPreco.adicColuna("Cód.prod.");
		tbPreco.adicColuna("Referência");
		tbPreco.adicColuna("Descrição");
		tbPreco.adicColuna("Preço");
		tbPreco.adicColuna("Saldo");
		tbPreco.setTamColuna(90,0);
		tbPreco.setTamColuna(90,1);
		tbPreco.setTamColuna(350,2);
		tbPreco.setTamColuna(100,3);
		tbPreco.setTamColuna(100,4);
		montaTabela();
		
		pinCab.adic(new JLabelPad("Pesquisa produto pela descrição:"),7,2,200,20);
		pinCab.adic(txtDescProd,7,25,300,20);
		
		//pinGrid.add(spPreco, BorderLayout.CENTER);
		pnCliente.add(pinCab, BorderLayout.NORTH);
		//pnClienteGeral.add(tbPreco, BorderLayout.CENTER);
		pnCliente.add(spPreco, BorderLayout.CENTER);
        //c.add(pnClienteGeral, BorderLayout.CENTER);
		adicBotaoSair();
		txtDescProd.addKeyListener(this);
		tbPreco.addKeyListener(this);
	}

	private void pesqProduto(String sDescProd) {
		int iPesq = -1;
		try {
			iPesq = tbPreco.pesqLinha(2,sDescProd);
			if (iPesq!=-1) {
				tbPreco.changeSelection(iPesq,0,true,true);
				tbPreco.setLinhaSel(iPesq);
			}
		}
		finally {
			iPesq = 0;
		}
	}
	public void setConexao(Connection cn) {
	    super.setConexao(cn);
		montaTela();
    }
	
	private void montaTabela() {
	    ResultSet rs = null;
	    PreparedStatement ps = null;
	    String sSql = "SELECT P.CODPROD,P.REFPROD,P.DESCPROD,PP.PRECOPROD,P.SLDLIQPROD " +
	    		"FROM EQPRODUTO P, VDPRECOPROD PP, SGPREFERE1 PF " +
	    		"WHERE P.ATIVOPROD='S' AND TIPOPROD IN ('P','S','F') AND CVPROD IN ('V','A') AND " +
	    		"PP.CODEMP=P.CODEMP AND PP.CODFILIAL=P.CODFILIAL AND PP.CODPROD=P.CODPROD AND " +
				"PF.CODEMPPG=PP.CODEMPPG AND PF.CODFILIALPG=PP.CODFILIALPG AND PF.CODPLANOPAG=PP.CODPLANOPAG AND " +
				"PF.CODEMPTB=PP.CODEMPTB AND PF.CODFILIALTB=PP.CODFILIALTB AND PF.CODTAB=PP.CODTAB AND " +
	    		"( (PF.CODCLASCLI IS NULL) OR " +
	    		"(PF.CODEMPCE=PP.CODEMPCC AND PF.CODFILIALCE=PP.CODFILIALCC AND PF.CODCLASCLI=PP.CODCLASCLI ) ) " +
	    		"AND PF.CODEMP=" + Aplicativo.iCodEmp + 
	    		" ORDER BY P.DESCPROD";
	    Vector vLinha = null;
	    try {
	    	ps = con.prepareStatement(sSql);
	    	rs = ps.executeQuery();
	    	tbPreco.limpa();
	    	while (rs.next()) {
	    		vLinha = new Vector();
	    		vLinha.addElement(new Integer(rs.getInt("CODPROD")));
	    		vLinha.addElement(new StringDireita(rs.getString("REFPROD")));
	    		vLinha.addElement(rs.getString("DESCPROD"));
	    		vLinha.addElement(new StringDireita(Funcoes.strDecimalToStrCurrency(15,2,rs.getDouble("PRECOPROD")+"")));
	    		vLinha.addElement(new StringDireita(rs.getDouble("SLDLIQPROD")+""));
	    		tbPreco.adicLinha(vLinha);
	    	}
	    	if (!con.getAutoCommit()) {
	    		con.commit();
	    	}
	    	rs.close();
	    	ps.close();
	    }
	    catch (SQLException err) {
	    	Funcoes.mensagemErro(null,"Erro consultando a tabela de preços!\n"+err.getMessage(),true,con,err);
	    }
	    finally {
	    	rs = null;
	    	ps = null;
	    	vLinha = null;
	    }
	}
	
	public void keyTyped(KeyEvent e) {
		
	}

	public void keyPressed(KeyEvent e) {

		if (e.getSource()==txtDescProd) {
			pesqProduto(((JTextFieldPad) e.getSource()).getText().trim());
		}
	}
	
	public void keyReleased(KeyEvent e) {
		if (e.getSource()==tbPreco ) {
			if (e.getKeyCode()==KeyEvent.VK_F2)
				txtDescProd.requestFocus();
		}
	}


}

