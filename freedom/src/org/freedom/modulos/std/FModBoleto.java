/**
 * @version 18/12/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FModBoleto.java <BR>
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
 * Monta o org.freedom.layout para o boleto bancário.
 * 
 */


package org.freedom.modulos.std;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import org.freedom.acao.JComboBoxEvent;
import org.freedom.acao.JComboBoxListener;
import org.freedom.bmps.Icone;
import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JComboBoxPad;
import org.freedom.componentes.JTextAreaPad;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.Painel;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.FDados;


public class FModBoleto extends FDados implements ActionListener, JComboBoxListener {
	private Painel pinCab = new Painel(0,150);
	private JTextFieldPad txtCodModBol = new JTextFieldPad(JTextFieldPad.TP_INTEGER,5,0);
	private JTextFieldPad txtDescModBol = new JTextFieldPad(JTextFieldPad.TP_STRING,30,0);
	private JTextAreaPad txaBoleto = new JTextAreaPad(10000);
	private JScrollPane spnCli = new JScrollPane(txaBoleto); 
	private JTextFieldPad txtAdic = new JTextFieldPad(JTextFieldPad.TP_DECIMAL,8,2);
	private JButton btAdic = new JButton(Icone.novo("btOk.gif"));
    private JComboBoxPad cbCamposDin = null;
    private JComboBoxPad cbCamposEspec = null;
    private JComboBoxPad cbAcao = null;
	public FModBoleto() {
    	setTitulo("Modelo de boleto");
    	setAtribos(20,100,750,400);
    	
    	pnCliente.add(pinCab,BorderLayout.NORTH);
    	pnCliente.add(spnCli);
    	
    	setPainel(pinCab);
    	
    	txaBoleto.setFont(new Font("Courier",Font.PLAIN,11));
    	
    	adicCampo(txtCodModBol, 7, 20, 90, 20,"CodModBol","Cód.mod.bol.", ListaCampos.DB_PK, true);
    	adicCampo(txtDescModBol, 100, 20, 250, 20,"DescModBol","Descrição do modelo de boleto", ListaCampos.DB_SI, true);
    	adicDBLiv(txaBoleto,"TxaModBol", "Corpo", true);
    	setListaCampos( false, "MODBOLETO", "FN");
    	
    	Vector vLabs = new Vector();
    	vLabs.addElement("Vencimento");
    	vLabs.addElement("Data documento");
    	vLabs.addElement("Nº documento");
        vLabs.addElement("Nº baixa");
    	vLabs.addElement("Parcela (No.)");
    	vLabs.addElement("Parcela (A,B..)");
    	vLabs.addElement("Valor do documento");
    	vLabs.addElement("Valor liquido");
    	vLabs.addElement("Valor extenso");
    	vLabs.addElement("Desconto na parcela");
    	vLabs.addElement("Código do cliente");
    	vLabs.addElement("Razão social do cliente");
    	vLabs.addElement("Nome do cliente");
    	vLabs.addElement("CPF ou CNPJ do cliente");
    	vLabs.addElement("IE ou RG do cliente");
    	vLabs.addElement("Endereço do cliente");
    	vLabs.addElement("Número");
    	vLabs.addElement("Complemento");
    	vLabs.addElement("CEP");
    	vLabs.addElement("Bairro do cliente");
    	vLabs.addElement("Cidade do cliente");
    	vLabs.addElement("UF do cliente");
    	vLabs.addElement("Telefone do cliente");
    	
    	Vector vVals = new Vector();
    	vVals.addElement("[VENCIMEN]"); //larg: 10
    	vVals.addElement("[DATADOC_]"); //larg: 10
    	vVals.addElement("[__DOCUMENTO__]"); //larg: 15
        vVals.addElement("[CODREC]"); //larg: 8
    	vVals.addElement("[P]"); //larg: 3
    	vVals.addElement("[A]"); //larg: 3
    	vVals.addElement("[VALOR_DOCUMEN]"); //larg: 15
    	vVals.addElement("[VLIQ_DOCUMENT]"); //larg: 15
    	vVals.addElement("[VALOR_EXTENSO]"); //larg: 15
    	vVals.addElement("[DESC_DOCUMENT]"); //larg: 15
    	vVals.addElement("[CODCLI]"); //larg: 8
    	vVals.addElement("[_____________RAZAO____DO____CLIENTE_____________]"); //larg: 50
    	vVals.addElement("[_____________NOME_____DO____CLIENTE_____________]"); //larg: 50
    	vVals.addElement("[CPF/CNPJ_ CLIENT]"); //larg: 18
    	vVals.addElement("[____IE/RG____CLIENTE]"); //larg: 22
    	vVals.addElement("[____________ENDERECO____DO____CLIENTE___________]"); //larg: 50
    	vVals.addElement("[NUMERO]"); //larg: 8
    	vVals.addElement("[____COMPLEMENTO___]"); //larg: 20
    	vVals.addElement("[__CEP__]"); //larg: 9
    	vVals.addElement("[___________BAIRRO___________]"); //larg: 30
    	vVals.addElement("[___________CIDADE___________]"); //larg: 30
    	vVals.addElement("[UF]"); //larg: 2
    	vVals.addElement("[__TELEFONE___]"); //larg: 15
    	
    	cbCamposDin = new JComboBoxPad(vLabs,vVals, JComboBoxPad.TP_STRING, 50, 0);
    	
    	Vector vLabs2 = new Vector();
    	vLabs2.addElement("'%' Valor");
    	vLabs2.addElement("Valor '+'");
    	vLabs2.addElement("Valor '-'");
    	vLabs2.addElement("Vencimento '+'");
    	
    	Vector vVals2 = new Vector();
    	vVals2.addElement("[#####.##%_VAL]");
    	vVals2.addElement("[#####.##+_VAL]");
    	vVals2.addElement("[#####.##-_VAL]");
    	vVals2.addElement("[###+_VEN]");

    	cbCamposEspec = new JComboBoxPad(vLabs2,vVals2, JComboBoxPad.TP_STRING, 20, 0);
    	
    	Vector vLabs3 = new Vector();
    	vLabs3.addElement("Limpa se campo vazio");
    	
    	Vector vVals3 = new Vector();
    	vVals3.addElement("<LP><_LP>");

    	cbAcao = new JComboBoxPad(vLabs3,vVals3, JComboBoxPad.TP_STRING, 10, 0);
    	
    	adic(new JLabel("Campos de dados"), 7, 40, 223, 20);
    	adic(cbCamposDin, 7, 60, 223, 20);
    	adic(new JLabel("Campos especiais de dados"), 240, 40, 217, 20);
    	adic(cbCamposEspec, 240, 60, 117, 20);
        adic(txtAdic,360,60,97,20);
    	adic(btAdic, 460, 50, 30, 30);
    	adic(new JLabel("Açoes"), 7, 80, 223, 20);
    	adic(cbAcao, 7, 100, 223, 20);
    	
    	txaBoleto.setTabSize(0);
 
    	btImp.addActionListener(this);
    	btPrevimp.addActionListener(this);
        btAdic.addActionListener(this);
        
        cbCamposDin.addComboBoxListener(this);
        cbAcao.addComboBoxListener(this);
    }
	public void valorAlterado(JComboBoxEvent evt) {
		if (evt.getComboBoxPad() == cbCamposDin) {
		       txaBoleto.insert(cbCamposDin.getVlrString(),txaBoleto.getCaretPosition());
		}
		else if (evt.getComboBoxPad() == cbAcao) {
		       txaBoleto.insert(cbAcao.getVlrString(),txaBoleto.getCaretPosition());
		}
	}
	public void actionPerformed(ActionEvent evt) {
		 if (evt.getSource() == btAdic) {
           BigDecimal bigVal = txtAdic.getVlrBigDecimal().setScale(2,BigDecimal.ROUND_HALF_UP);
           String sVal = bigVal.toString();
           if (cbCamposEspec.getSelectedIndex() < 3) //Campos de valores	
           	 sVal = cbCamposEspec.getVlrString().replaceAll("#####.##",Funcoes.strZero(bigVal.setScale(2,BigDecimal.ROUND_HALF_UP).toString(),8));
           else if (cbCamposEspec.getSelectedIndex() == 3) //Campos de datas
           	 sVal = cbCamposEspec.getVlrString().replaceAll("###",Funcoes.strZero(bigVal.intValue()+"",3));
           
	         txaBoleto.insert(sVal,txaBoleto.getCaretPosition());
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
		imp.setTitulo("Teste de boleto");
	    imp.limpaPags();
	    String[] sLinhas = txaBoleto.getText().split("\n");
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
