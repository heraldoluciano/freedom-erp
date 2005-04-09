/**
 * @version 04/02/2004 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FModEtiqueta.java <BR>
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
 * Monta o org.freedom.layout para etiquetas.
 * 
 */


package org.freedom.modulos.std;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.Vector;

import javax.swing.JButton;
import org.freedom.componentes.JLabelPad;
import javax.swing.JScrollPane;

import org.freedom.acao.JComboBoxEvent;
import org.freedom.acao.JComboBoxListener;
import org.freedom.bmps.Icone;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JComboBoxPad;
import org.freedom.componentes.JTextAreaPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.JPanelPad;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.FDados;

//import bibli.funcoes.Funcoes; // TESTE


public class FModEtiqueta extends FDados implements ActionListener, JComboBoxListener {       
	
    private JPanelPad pinCab = new JPanelPad(0,150);
	private JTextFieldPad txtCodModEtiq = new JTextFieldPad(JTextFieldPad.TP_INTEGER,5,0);
	private JTextFieldPad txtDescModEtiq = new JTextFieldPad(JTextFieldPad.TP_STRING,30,0);
	private JTextFieldPad txtNColModEtiq = new JTextFieldPad(JTextFieldPad.TP_INTEGER,5,0);
	private JTextFieldPad txtCodpapel = new JTextFieldPad(JTextFieldPad.TP_STRING,10,0); 
	private JTextFieldFK txtDescpapel = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
	private JTextAreaPad txaEtiqueta = new JTextAreaPad(500); 
	private JScrollPane spnCli = new JScrollPane(txaEtiqueta); 
	private JButton btAdic = new JButton(Icone.novo("btOk.gif"));
    private JComboBoxPad cbCampos = null;
    private ListaCampos lcPapel = new ListaCampos(this,"PL");
    private Vector vTamanhos = new Vector();
	public FModEtiqueta() {
    	setTitulo("Modelo de etiqueta");
    	setAtribos(20,100,750,400);
    	
    	pnCliente.add(pinCab,BorderLayout.NORTH);
    	pnCliente.add(spnCli);
    	
    	setPainel(pinCab);

    	lcPapel.add(new GuardaCampo( txtCodpapel, "Codpapel", "Cod.papel", ListaCampos.DB_PK, false));
    	lcPapel.add(new GuardaCampo( txtDescpapel, "Descpapel", "Descrição do papel", ListaCampos.DB_SI, false)); 
    	lcPapel.montaSql(false, "PAPEL", "SG");
     	lcPapel.setQueryCommit(false);
     	lcPapel.setReadOnly(true);
     	txtCodpapel.setTabelaExterna(lcPapel);

    	txaEtiqueta.setFont(new Font("Courier",Font.PLAIN,11));

    	adicCampo(txtCodModEtiq, 7, 20, 90, 20,"CodModEtiq","Cód.mod.etiq.", ListaCampos.DB_PK, true);
    	adicCampo(txtDescModEtiq, 100, 20, 237, 20,"DescModEtiq","Descrição do modelo de etiqueta", ListaCampos.DB_SI, true);
    	adicCampo(txtNColModEtiq, 340, 20, 60, 20,"NColModEtiq","Colunas", ListaCampos.DB_SI, true);
    	
    	adicDBLiv(txaEtiqueta,"TxaModEtiq", "Corpo", true);
    	
     	adicCampo(txtCodpapel, 7, 60, 90, 20,"Codpapel","Cód.papel", ListaCampos.DB_FK, txtDescpapel, true); 
   	    adicDescFK(txtDescpapel, 100, 60, 297, 20,"Descpapel","Descrição do papel"); 

    	setListaCampos( false, "MODETIQUETA", "SG");
   	        	
    	ObjetoEtiquetaCli objEtiqCli = new ObjetoEtiquetaCli();
    	Vector vLabs = objEtiqCli.getLabel();    	
    	Vector vVals = objEtiqCli.getValor();
    	vTamanhos = objEtiqCli.getTam();
    	    	
    	cbCampos = new JComboBoxPad(vLabs,vVals, JComboBoxPad.TP_STRING, 50, 0);
  
    	adic(new JLabelPad("Campos dinâmicos"), 7, 80, 220, 20); 
       	adic(cbCampos, 7, 100, 220, 20); 
       	adic(btAdic, 230, 100, 30, 30); 
        
    	txaEtiqueta.setTabSize(0);
 
    	btImp.addActionListener(this);
    	btPrevimp.addActionListener(this);
    	
    	cbCampos.addComboBoxListener(this); 
     	btAdic.addActionListener(this);
    	    	
    }
	
    public void setConexao(Connection cn) {
	  	super.setConexao(cn);
	  	lcPapel.setConexao(cn);
    }
	
	public void valorAlterado(JComboBoxEvent evt) { 
		if (evt.getComboBoxPad() == cbCampos) { 
			adicionaCampo(); 
		} 
	} 

	private void adicionaCampo(){
	    int iTam = Integer.parseInt(vTamanhos.elementAt(cbCampos.getSelectedIndex()).toString());		   
	    txaEtiqueta.insert(cbCampos.getVlrString().substring(0,cbCampos.getVlrString().length()-1)+Funcoes.replicate("?",iTam)+"]",txaEtiqueta.getCaretPosition());
	}
	public void actionPerformed(ActionEvent evt) {
		 String Aux, Aux1;// teste
		 int Tam = 0;
		 if (evt.getSource() == btAdic) {
		     adicionaCampo();
	    }
		 else if (evt.getSource() == btImp)
		 	 imprimir(false);
		 else if (evt.getSource() == btPrevimp)
		 	 imprimir(true);
		 super.actionPerformed(evt);
	}
	private void imprimir(boolean bVisualizar) {
		ImprimeOS imp = new ImprimeOS("",con);
		imp.verifLinPag();
		imp.setTitulo("Teste de etiqueta"); 
	    imp.limpaPags();
	    String[] sLinhas = txaEtiqueta.getText().split("\n");
	    for(int i=0;i<sLinhas.length;i++) {
			imp.say(imp.pRow()+1,0,sLinhas[i]);
	    }
	    imp.eject();
	    imp.fechaGravacao();
		if (bVisualizar) {
			imp.preview(this);
		}
		else {
			imp.print();
		}
	}

}
