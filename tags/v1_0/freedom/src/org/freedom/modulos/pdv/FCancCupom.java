/**
 * @version 16/07/2003 <BR>
 * @author Setpoint Informática Ltda./Anderson Sanchez <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.pdv <BR>
 * Classe: @(#)FCancCupom.java <BR>
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


package org.freedom.modulos.pdv;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Painel;
import org.freedom.componentes.Tabela;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FDialogo;
import org.freedom.bmps.Icone;

public class FCancCupom extends FDialogo implements ActionListener,MouseListener,KeyListener { 
	private Connection cn = null;
	private Painel pinCab = new Painel(400,90);
	private JPanel pnCli = new JPanel(new BorderLayout());
	private JPanel pnBt = new JPanel(new GridLayout(1,2));	
	private JTextFieldPad txtVenda = new JTextFieldPad();
	private JTextFieldFK txtNota = new JTextFieldFK();
	private JTextFieldFK txtSerie = new JTextFieldFK();
	private JTextFieldFK txtData = new JTextFieldFK();
	private JTextFieldFK txtValor = new JTextFieldFK();
	private Tabela tab = new Tabela();
	private JScrollPane spnTab = new JScrollPane(tab);
	private JButton btCanc = new JButton(Icone.novo("btExcluir.gif"));
	private JButton btExec = new JButton(Icone.novo("btExecuta.gif"));
	private ListaCampos lcVenda = new ListaCampos(this,"VD");
	public FCancCupom(Connection con) {
		cn = con;
		setTitulo("Cancela Venda");
		setAtribos(100,150,700,300);
		
		txtVenda.setTipo(JTextFieldPad.TP_INTEGER,8,0);
		txtVenda.setPK(true);
		
		txtNota.setTipo(JTextFieldPad.TP_STRING,10,0);
		txtSerie.setTipo(JTextFieldPad.TP_STRING,10,0);
		txtData.setTipo(JTextFieldPad.TP_DATE,10,0);	
		txtValor.setTipo(JTextFieldPad.TP_DOUBLE,10,2);
				
		btCanc.setPreferredSize(new Dimension(30,30));
		btCanc.setToolTipText("Insere alíquota");
		btCanc.addActionListener(this);
		
		btExec.setPreferredSize(new Dimension(30,30));
		btExec.setToolTipText("Lista itens da venda");
		btExec.addActionListener(this);
		
		JCheckBoxPad cbInteira = new JCheckBoxPad("Cancelar venda inteira","S","N");
		cbInteira.setVlrString("N");		
		
		lcVenda.add(new GuardaCampo( txtVenda, 7, 25, 80, 20, "CodVenda", "Código", true, false, null, JTextFieldPad.TP_INTEGER,false),"txtVenda");
		lcVenda.add(new GuardaCampo( txtNota, 94, 25, 80, 20, "DocVenda", "Nota", false, false, null, JTextFieldPad.TP_STRING,false),"txtNota");
        lcVenda.add(new GuardaCampo( txtSerie, 181,25,30,20, "Serie", "Série", false,false,null, JTextFieldPad.TP_STRING,false),"txtSerie");
		lcVenda.add(new GuardaCampo( txtData, 208,25,80,20, "DtEmitVenda", "Data", false,false,null, JTextFieldPad.TP_DATE,false),"txtData");   
		lcVenda.add(new GuardaCampo( txtValor, 295,25,120,20, "VlrLiqVenda", "Valor", false,false,null, JTextFieldPad.TP_DOUBLE,false),"txtValor");
       	
       	txtVenda.setListaCampos(lcVenda);
       	txtVenda.setNomeCampo("CodVenda");
       	       	
	    setPanel(pnCli); 
		pnCli.add(spnTab,BorderLayout.CENTER);
		pnCli.add(pinCab,BorderLayout.NORTH);
			
		pnBt.setPreferredSize(new Dimension(30,30));
		pnBt.add(btCanc);		
    
		pnRodape.add(pnBt, BorderLayout.WEST);			
			
		pinCab.adic(new JLabel("Cód da Venda"),7,5,80,20);
		pinCab.adic(txtVenda,7,25,80,20);
		pinCab.adic(new JLabel("Nota"),90,5,80,20);		
		pinCab.adic(txtNota,90,25,80,20);
		pinCab.adic(new JLabel("Série"),173,5,30,20);
		pinCab.adic(txtSerie,173,25,30,20);
		pinCab.adic(new JLabel("Data"),206,5,80,20);		
		pinCab.adic(txtData,206,25,80,20);
		pinCab.adic(new JLabel("Valor total"),289,5,120,20);
		pinCab.adic(txtValor,289,25,120,20);
		pinCab.adic(btExec,425,15,30,30);
		pinCab.adic(cbInteira,7,55,200,20);
		
		tab.adicColuna("*");
		tab.adicColuna("Item");
		tab.adicColuna("Descrição");
		tab.adicColuna("Qtd");
		tab.adicColuna("Base ICMS");
		tab.adicColuna("Vlr. ICMS");
		tab.adicColuna("Preço");
		tab.adicColuna("Total");
		tab.adicColuna("Status");
			
		tab.setTamColuna(10,0);
		tab.setTamColuna(28,1);
		tab.setTamColuna(180,2);
		tab.setTamColuna(60,3);
		tab.setTamColuna(90,4);
		tab.setTamColuna(90,5);
		tab.setTamColuna(90,6);
		tab.setTamColuna(100,7);
		tab.setTamColuna(25,8);
		
		tab.addMouseListener(this);
		tab.addKeyListener(this);
		
		setToFrameLayout();	
		
		lcVenda.montaSql(false, "VENDA", "VD");  
		lcVenda.setWhereAdic("TIPOVENDA='E'");
		lcVenda.setConexao(con);
		lcVenda.setReadOnly(true);		
	}
	
	private void executaCanc() {
        System.out.println("teste");		
	}

	private void carregaTabela() {		  
		int iRow = 0;  
	    tab.limpa();
				 			
		try {
			String sSQL = "SELECT IT.CODITVENDA,P.DESCPROD,IT.QTDITVENDA,IT.VLRBASEICMSITVENDA,IT.VLRICMSITVENDA,IT.VLRPRODITVENDA FROM VDITVENDA IT, EQPRODUTO P WHERE"+
						  " P.CODPROD=IT.CODPROD AND IT.CODEMP=? AND IT.CODFILIAL=? AND CODVENDA=? AND IT.TIPOVENDA='E'";
			PreparedStatement ps = cn.prepareStatement(sSQL);

			ps = cn.prepareStatement(sSQL);
			ps.setInt(1,Aplicativo.iCodEmp);
			ps.setInt(2,ListaCampos.getMasterFilial("PVMOVCAIXA"));
			ps.setInt(3,txtVenda.getVlrInteger().intValue());
		
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {				
			  tab.adicLinha();			
			  tab.setValor(rs.getInt("CODITVENDA")+"",iRow,1);
			  tab.setValor(rs.getString("DESCPROD"),iRow,2);
			  tab.setValor(Funcoes.strDecimalToStrCurrency(8,2,rs.getDouble("QTDITVENDA")+""),iRow,3);
			  tab.setValor(Funcoes.strDecimalToStrCurrency(13,2,rs.getDouble("VLRBASEICMSITVENDA")+""),iRow,4);
			  tab.setValor(Funcoes.strDecimalToStrCurrency(13,2,rs.getDouble("VLRICMSITVENDA")+""),iRow,5);
			  tab.setValor(Funcoes.strDecimalToStrCurrency(13,2,rs.getDouble("VLRPRODITVENDA")+""),iRow,6);
			  tab.setValor(Funcoes.strDecimalToStrCurrency(13,2,(rs.getDouble("VLRPRODITVENDA"))*(rs.getDouble("QTDITVENDA"))+""),iRow,7);
			  iRow++;
				 				
			}						
			
		} catch (SQLException err) {
			Funcoes.mensagemErro(this,"Erro carregar ítens da venda!\n"+err.getMessage());
		}

	}
	
	public void actionPerformed( ActionEvent evt ) {
	  if (evt.getSource() == btCanc) {
			executaCanc();
	  }
	  if (evt.getSource() == btExec) {
			carregaTabela();
	  } 
 
	}
	
	public void mouseClicked(MouseEvent mevt) {
	  if ((mevt.getSource() == tab) & (mevt.getClickCount() == 2) & (tab.getLinhaSel() >= 0)) {
		if ((""+tab.getValor(tab.getLinhaSel(),0)).trim().length() == 1) {
		  
		  carregaTabela();
		}
		else if (((""+tab.getValor(tab.getLinhaSel(),0)).trim().length() > 1) &
				 ((""+tab.getValor(tab.getLinhaSel(),0)).trim().length() < 13)) {
		  
		  carregaTabela();
		}
		else if (((""+tab.getValor(tab.getLinhaSel(),0)).trim().length() == 13) &
				 ((""+tab.getValor(tab.getLinhaSel(),2)).trim().compareTo("B") == 0)){
		  
		  carregaTabela();
		}
		else if (((""+tab.getValor(tab.getLinhaSel(),0)).trim().length() == 13) &
				 ((""+tab.getValor(tab.getLinhaSel(),2)).trim().compareTo("C") == 0)){
		  
		  carregaTabela();
		}
		else if ((""+tab.getValor(tab.getLinhaSel(),0)).trim().length() == 13) {		  
		  carregaTabela();
		}
	  }
	}

	public void keyPressed(KeyEvent kevt) {
	  if ((kevt.getKeyCode() == KeyEvent.VK_DELETE) & (kevt.getSource() == tab)) {
		carregaTabela();
	  }
	  else if ((kevt.getKeyCode() == KeyEvent.VK_ENTER) & (kevt.getSource() == tab) & 
			   (tab.getLinhaSel() >= 0)) {
		if ((""+tab.getValor(tab.getLinhaSel(),0)).trim().length() == 1) {

		  carregaTabela();
		}
		else if (((""+tab.getValor(tab.getLinhaSel(),0)).trim().length() > 1) &
				 ((""+tab.getValor(tab.getLinhaSel(),0)).trim().length() < 13)) {

		  carregaTabela();
		}
		else if ((""+tab.getValor(tab.getLinhaSel(),0)).trim().length() == 13) {

		  carregaTabela();
		}
	  }
	}
	public void keyTyped(KeyEvent kevt) { }
	public void keyReleased(KeyEvent kevt) { }
	public void mouseEntered(MouseEvent mevt) { }
	public void mouseExited(MouseEvent mevt) { }
	public void mousePressed(MouseEvent mevt) { }
	public void mouseReleased(MouseEvent mevt) { }

	
}
