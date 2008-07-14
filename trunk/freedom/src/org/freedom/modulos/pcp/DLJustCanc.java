package org.freedom.modulos.pcp;

import javax.swing.JScrollPane;

import org.freedom.componentes.JTextAreaPad;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.FFDialogo;


public class DLJustCanc extends FFDialogo {

	private static final long serialVersionUID = 1L;
	
	private JTextAreaPad txaJustCanc = new JTextAreaPad();
	
	public DLJustCanc(){
			
		setTitulo("Justificativa do cancelamento");
		setAtribos( 250, 160 );
		
		txaJustCanc.requestFocus();
		adic( new JScrollPane( txaJustCanc ), 7, 7, 222, 50 );
		
	}

	public String getValor(){
		
		String sRet = "";
		
		if( txaJustCanc.getVlrString().equals( "" )){
			sRet = "";
		}
		else{
			sRet = txaJustCanc.getVlrString();
		}
		return sRet;
	}
    public void ok(){
    	if ((txaJustCanc.getVlrString().equals(""))){
	        Funcoes.mensagemErro(this,"Informe o motivo do cancelamento!");
    	    return;
    	}
    	else{
    	    super.ok();
	    }
    }
}
