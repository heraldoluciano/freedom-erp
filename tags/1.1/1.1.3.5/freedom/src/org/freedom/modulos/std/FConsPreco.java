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
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JScrollPane;

import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JPanelPad;
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
public class FConsPreco extends FFilho implements KeyListener, FocusListener {
	private static final long serialVersionUID = 1L;
    private JPanelPad pinCab = new JPanelPad(800,55);
    private Tabela tbPreco = new Tabela();
    private JScrollPane spPreco = new JScrollPane(tbPreco);
    private JTextFieldPad txtCodProd = new JTextFieldPad(JTextFieldPad.TP_STRING,13,0);
    private JTextFieldPad txtDescProd = new JTextFieldPad(JTextFieldPad.TP_STRING,50,10);
	
	public FConsPreco() {
		super(false);
		setTitulo("Consulta de preços");
		setAtribos(50,50,778,500);		
	}
	
	private void montaTela() {
		tbPreco.adicColuna("Cód.prod.");
		tbPreco.adicColuna("Referência");
		tbPreco.adicColuna("Descrição");
		tbPreco.adicColuna("Preço");
		tbPreco.adicColuna("Saldo");
		tbPreco.setTamColuna(100,0);
		tbPreco.setTamColuna(100,1);
		tbPreco.setTamColuna(350,2);
		tbPreco.setTamColuna(100,3);
		tbPreco.setTamColuna(100,4);
		tbPreco.setFont( new Font("Tomoha", Font.PLAIN, 14) );
		montaTabela();

		pinCab.adic(new JLabelPad("Código do produto:"),7,2,120,20);
		pinCab.adic(txtCodProd,7,25,120,20);
		pinCab.adic(new JLabelPad("Pesquisa produto pela descrição:"),130,2,200,20);
		pinCab.adic(txtDescProd,130,25,300,20);
		
		pnCliente.add(pinCab, BorderLayout.NORTH);
		pnCliente.add(spPreco, BorderLayout.CENTER);
		
		adicBotaoSair();
		
		tbPreco.addFocusListener(this);
		tbPreco.addKeyListener(this);
		txtDescProd.addKeyListener(this);
		txtCodProd.addKeyListener(this);
		txtCodProd.setPK(true);
	}

	private void pesqDescProd(String sDescProd) {
		int iPesq = -1;
		try {
			iPesq = tbPreco.pesqLinha(2,sDescProd.trim());
			if (iPesq!=-1) {
				tbPreco.changeSelection(iPesq,0,true,true);
				tbPreco.setLinhaSel(iPesq);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			iPesq = 0;
		}
	}
	
	private void pesqCodProd(String sCodProd) {
		Vector vtemp = null;
		int iPesq = -1;
		try {
			for(int i=0; i<tbPreco.getNumLinhas(); i++) {
				vtemp = tbPreco.getLinha(i);
				if(sCodProd.equals(((StringDireita)vtemp.elementAt(0)).toString().trim())) {
					iPesq = i;
					break;
				}
			}
			if (iPesq!=-1) {
				tbPreco.changeSelection(iPesq,0,true,true);
				tbPreco.setLinhaSel(iPesq);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			vtemp = null;
			iPesq = 0;
		}
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
	    		vLinha.addElement(new StringDireita(String.valueOf(rs.getInt("CODPROD"))));
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
	    	sSql = null;
	    	vLinha = null;
	    }
	}
	
	public void setConexao(Connection cn) {
	    super.setConexao(cn);
		montaTela();
		txtCodProd.setBuscaGenProd(new DLCodProd(con,tbPreco));
    }
	
	public void keyTyped(KeyEvent e) { }

	public void keyPressed(KeyEvent e) {
		if (e.getSource()==txtDescProd)
			pesqDescProd(((JTextFieldPad) e.getSource()).getText().trim());
	}
	
	public void keyReleased(KeyEvent e) {
		if (e.getSource()==tbPreco ) {
			if (e.getKeyCode()==KeyEvent.VK_F2)
				txtDescProd.requestFocus();
		}
	}
	
	public void focusGained(FocusEvent e) {
		if(e.getSource()==tbPreco)
			pesqCodProd(txtCodProd.getVlrString().trim());
	}
	
	public void focusLost(FocusEvent e) { }
	
}

