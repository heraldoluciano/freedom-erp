/**
 * @version 17/07/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.pdv <BR>
 * Classe: @(#)FAliquota.java <BR>
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


import javax.swing.JButton;

import org.freedom.bmps.Icone;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.Painel;
import org.freedom.componentes.Tabela;
import org.freedom.drivers.JBemaFI32;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FFDialogo;


public class FAliquota extends FFDialogo implements ActionListener { 
	private Painel pinCab = new Painel(400,60);
	private JPanel pnCli = new JPanel(new BorderLayout());
	private JTextFieldPad txtAliquota = new JTextFieldPad();
	private Tabela tab = new Tabela();
	private JScrollPane spnTab = new JScrollPane(tab);
	private JBemaFI32 bf = (FreedomPDV.bECFTerm ? new JBemaFI32() : null);
	private String sAliquotas = "";
	private JButton btInsere = new JButton(Icone.novo("btExecuta.gif"));
	public FAliquota() {
		setTitulo("Ajusta aliquotas");
		setAtribos(100,150,400,350);
		
		btInsere.setPreferredSize(new Dimension(30,30));
		btInsere.setToolTipText("Insere alíquota");
		btInsere.addActionListener(this);
		
		txtAliquota.setTipo(JTextFieldPad.TP_DECIMAL,15,2);
       	
	    setPanel(pnCli); 
		pnCli.add(spnTab,BorderLayout.CENTER);
		pnCli.add(pinCab,BorderLayout.NORTH);
			
		pinCab.adic(new JLabel("Inserir aliquota"),7,5,87,20);
		pinCab.adic(txtAliquota,7,25,87,20);
		pinCab.adic(btInsere,150,15,30,30);

		tab.adicColuna("");
		tab.adicColuna("");
		tab.adicColuna("");
		
		tab.setTamColuna(120,0);
		tab.setTamColuna(120,1);
		tab.setTamColuna(120,2);
		setToFrameLayout();	
        carregaTabela();
	}
	
	private void insereAliquota() {
		if ((txtAliquota.getVlrDouble().doubleValue()<=0)||(txtAliquota.getVlrDouble().doubleValue()>99.99)) {
		  Funcoes.mensagemErro(this, "Alíquota inválida");
		  txtAliquota.requestFocus();
		}
		else {
		  String sAliq = txtAliquota.getVlrString().trim();
          String sVal = Funcoes.strZero(txtAliquota.getVlrBigDecimal().intValue()+"",2)+sAliq.substring(sAliq.length()-2);
		  if ( sAliquotas.indexOf(sVal)>=0)  {
		    
		    Funcoes.mensagemErro(this,"Alíquota já foi cadastrada!");	
		  } 		
		  else {
			if ( ((sAliquotas.length()+1)/5 )>15) {
			  Funcoes.mensagemErro(this,"Quantidade maxima de aliquotas já foi atingida!");	
			}	
			else {
			  if (FreedomPDV.bECFTerm)
			     bf.programaAliquotas(Aplicativo.strUsuario,sVal,0,FreedomPDV.bModoDemo);
			  carregaTabela();
			}		  
		  
		  }
		}
		
	}
	
	private void carregaTabela() {		  
		  String sAliquota = "";
		  if (!FreedomPDV.bECFTerm)
		  	 return;
		  sAliquotas = (bf.retornaAliquotas(Aplicativo.strUsuario,FreedomPDV.bModoDemo)+"").trim();
		  int iRow = 0;
		  int iCol = 0;
		  int i = 1;
		  int iTot = (((sAliquotas.length())+1)/5);
		  tab.limpa();
		  tab.adicLinha();				 			
			while (i<=iTot) {
				sAliquota = i==1?sAliquotas.substring(0,4):sAliquotas.substring((i*5)-5,(i*5)-1);
				sAliquota = "T"+Funcoes.strZero((i+""),2)+"   =   "+sAliquota.substring(0,2)+"."+sAliquota.substring(2)+" %";		
								
				if (iCol>2) {
				  iCol = 0;
				  iRow++;
				  tab.adicLinha();					
				}
				
				tab.setValor(sAliquota,iRow,iCol);
                i++;
				iCol++;
				 				
			}

	}
	
	public void actionPerformed( ActionEvent evt ) {
	  if (evt.getSource() == btInsere) {
			insereAliquota();
	  } 
	}
	
	
}
