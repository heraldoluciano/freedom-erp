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
 * Tela para cadastro dos modelos padrões para geração automatica de lotes.
 * 
 */

package org.freedom.modulos.pcp;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.Date;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JScrollPane;

import org.freedom.acao.JComboBoxEvent;
import org.freedom.acao.JComboBoxListener;
import org.freedom.acao.PostEvent;
import org.freedom.acao.PostListener;
import org.freedom.bmps.Icone;
import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JComboBoxPad;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JTextAreaPad;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.FDados;

public class FModLote extends FDados implements ActionListener, JComboBoxListener,PostListener {
	
	private static final long serialVersionUID = 1L;
	
    private JPanelPad pinCab = new JPanelPad(0,100);
	private JTextFieldPad txtCodModLote = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
	private JTextFieldPad txtDescModLote = new JTextFieldPad(JTextFieldPad.TP_STRING,30,0);
	private JTextAreaPad txaModLote = new JTextAreaPad(350); 
	private JScrollPane spnCli = new JScrollPane(txaModLote); 
	private JButton btAdic = new JButton(Icone.novo("btOk.gif"));
    private JComboBoxPad cbCampos = null;
    private Vector vTamanhos = new Vector();
	private Vector vLabs = new Vector();    	
	private Vector vVals = new Vector();
	private ObjetoModLote objModLote = new ObjetoModLote();

	public FModLote() {
		super();
    	setTitulo("Modelos de lote");
    	setAtribos(20,100,430,200);
    	setImprimir(true);
    	pnCliente.add(pinCab,BorderLayout.NORTH);
    	pnCliente.add(spnCli);
    	
    	setPainel(pinCab);

    	txaModLote.setFont(new Font("Courier",Font.PLAIN,11));

    	adicCampo(txtCodModLote, 7, 20, 90, 20,"CodModLote","Cód.mod.lote", ListaCampos.DB_PK, true);
    	adicCampo(txtDescModLote, 100, 20, 280, 20,"DescModLote","Descrição do modelo de lote", ListaCampos.DB_SI, true);    	
    	adicDBLiv(txaModLote,"TxaModLote", "Corpo", true);
    	
    	setListaCampos( true, "MODLOTE", "EQ");   	    
    	
    	vLabs = new Vector();    	
    	vVals = new Vector();
    	vTamanhos = new Vector();
    	
    	vLabs = objModLote.getLabels();
    	vVals = objModLote.getValores();
    	vTamanhos = objModLote.getTams();
    	
    	cbCampos = new JComboBoxPad(vLabs,vVals, JComboBoxPad.TP_STRING, 50, 0);    	    	    	
      	    	
    	adic(new JLabelPad("Campos dinâmicos"), 7, 40, 220, 20); 
       	adic(cbCampos, 7, 60, 220, 20); 
       	adic(btAdic, 230, 60, 30, 30); 
    	txaModLote.setTabSize(0);
 
    	btImp.addActionListener(this);
    	btPrevimp.addActionListener(this);
    	
    	cbCampos.addComboBoxListener(this); 
     	btAdic.addActionListener(this);
    	    	
    }
	
    public void setConexao(Connection cn) {
    	super.setConexao(cn);
    }
    
	public void beforePost(PostEvent pevt) {	     
		int iMax = 13;	
		objModLote.setTexto(txaModLote.getVlrString());//carrega o texto criado para o objeto
		Vector vTemp = new Vector();
		String sTexto = txaModLote.getVlrString();
		String sTmp = sTexto;

		vTemp = objModLote.getValoresAdic();	

		for(int i = 0;vTemp.size()>i;i++){
			sTmp = sTmp.replaceAll("\\"+vTemp.elementAt(i).toString(),"");
        	sTmp = sTmp.replaceAll("\\[","");
        	sTmp = sTmp.replaceAll("\\]","");	 
		}
		
		int iTam = sTmp.length();
		
		System.out.println(sTmp);
		if (iTam>iMax) {	        
	        pevt.cancela();
	        Funcoes.mensagemErro(this,"Texto muito grande para o lote ("+iTam+" caracteres).\n " +
	        						  "O código do lote deve conter no máximo 13 caracteres.");	        		
	    }
			
	}
	
	public void valorAlterado(JComboBoxEvent evt) { 
		if (evt.getComboBoxPad() == cbCampos) { 
			adicionaCampo(); 
		} 
	} 

	private void adicionaCampo(){
	    int iTam = Integer.parseInt(vTamanhos.elementAt(cbCampos.getSelectedIndex()).toString());	
	    txaModLote.insert("["+cbCampos.getVlrString()+Funcoes.replicate("-",iTam)+"]",txaModLote.getCaretPosition());
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
		objModLote.setTexto(txaModLote.getVlrString());
		imp.verifLinPag();
		imp.setTitulo("Teste de Modelo de Lote"); 
	    imp.limpaPags();
	    String sTexto = "É necessário selecionar um modelo de lote válido!";
	    if(objModLote.getLote(new Integer(30),new Date(),con)!=null){
	    	sTexto = objModLote.getLote(new Integer(30),new Date(),con);
	    } 

		imp.say(imp.pRow()+1,0,sTexto);

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

