/**
 * @version 20/06/2005 <BR>
 * @author Setpoint Informática Ltda./Anderson Sanchez <BR>
 * 
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.pcp <BR>
 * Classe:
 * @(#)FreedomPCP.java <BR>
 * 
 * Este programa é licenciado de acordo com a LPG-PC (Licença Pública Geral para
 * Programas de Computador), <BR>
 * versão 2.1.0 ou qualquer versão posterior. <BR>
 * A LPG-PC deve acompanhar todas PUBLICAÇÕES, DISTRIBUIÇÕES e REPRODUÇÕES deste
 * Programa. <BR>
 * Caso uma cópia da LPG-PC não esteja disponível junto com este Programa, você
 * pode contatar <BR>
 * o LICENCIADOR ou então pegar uma cópia em: <BR>
 * Licença: http://www.lpg.adv.br/licencas/lpgpc.rtf <BR>
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa é
 * preciso estar <BR>
 * de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 * Tela de baixa de RMA por Código de barras.
 *  
 */
package org.freedom.modulos.pcp;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Vector;
import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.FDados;

public class FBaixaRMACodBar extends FDados implements CarregaListener,FocusListener{
  private JTextFieldPad txtEntrada = new JTextFieldPad(JTextFieldPad.TP_STRING,100,0);
  private JTextFieldPad txtSeqOf = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtCodOp = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtCodProd = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtCodLote = new JTextFieldPad(JTextFieldPad.TP_STRING,8,0);
  private JTextFieldPad txtQtdEntrada = new JTextFieldPad(JTextFieldPad.TP_DECIMAL,15,5);
  private int i77 = 0;
    
  public FBaixaRMACodBar () {
    setTitulo("Cadastro de Tipo de Fornecedor");
    setAtribos( 50, 50, 350, 350);
    
    adic(txtEntrada,7,10,150,20);
    txtEntrada.setName("Entrada");
    adic(txtSeqOf,7,30,150,20);
    txtSeqOf.setName("Sequencial");
    adic(txtCodOp,7,70,150,20);
    txtCodOp.setName("OP");
    adic(txtCodProd,7,110,150,20);
    txtCodProd.setName("Produto");
    adic(txtCodLote,7,150,150,20);
    txtCodLote.setName("Lote");
    adic(txtQtdEntrada,7,190,150,20);
    txtQtdEntrada.setName("Qtd");
    
    txtEntrada.addFocusListener(this);
    btImp.addActionListener(this);
    btPrevimp.addActionListener(this);     
    txtEntrada.addKeyListener(this);
  }
  public void beforeCarrega(CarregaEvent cevt){  }
  public void afterCarrega(CarregaEvent cevt){  }
  public void focusGained(FocusEvent e) {  }
  public void focusLost(FocusEvent e) { 
  	if(e.getSource()==txtEntrada){
  		decodeEntrada();
  	}
  }

  private void decodeEntrada(){

  	String sTexto = txtEntrada.getText();
  	
  	if(sTexto!=null){
		if (sTexto.length()>0){
			int iCampos = Funcoes.contaChar(sTexto,'#'); 
			if(iCampos==4) {
				Vector vCampos = new Vector();
				vCampos.addElement(txtSeqOf);
				vCampos.addElement(txtCodOp);
				vCampos.addElement(txtCodProd);
				vCampos.addElement(txtCodLote); 
				vCampos.addElement(txtQtdEntrada);

				String sResto = sTexto;
				
				for(int i=0;vCampos.size()>i;i++){		
					System.out.println("CAMPO:"+((JTextFieldPad)(vCampos.elementAt(i))).getName());
					((JTextFieldPad)(vCampos.elementAt(i))).setVlrString(sResto.substring(0,sResto.indexOf("#")>0?sResto.indexOf("#"):sResto.length()));
					sResto = sResto.substring(sResto.indexOf("#")+1);
				}
			}
			else{
				Funcoes.mensagemInforma(this,"Entrada inválida!\nNúmero de campos incoerente."+Funcoes.contaChar(sTexto,'#'));
			}
		}
		else {
			Funcoes.mensagemInforma(this,"Entrada inválida!\nTexto em branco.");
		}
  	}
  	else{
  		Funcoes.mensagemInforma(this,"Entrada inválida!\nTexto nulo.");
  	}
  }
	
}
