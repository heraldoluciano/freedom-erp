/**
 * @author Setpoint Informática Ltda.
 * @author Reginaldo Garcia Heua <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.pcp <BR>
 * Classe:
 * @(#)DLJustCanc.java <BR>
 * 
 * Este programa é licenciado de acordo com a LPG-PC (Licença Pública Geral para Programas de Computador), <BR>
 * versão 2.1.0 ou qualquer versão posterior. <BR>
 * A LPG-PC deve acompanhar todas PUBLICAÇÕES, DISTRIBUIÇÕES e REPRODUÇÕES deste Programa. <BR>
 * Caso uma cópia da LPG-PC não esteja disponível junto com este Programa, você pode contatar <BR>
 * o LICENCIADOR ou então pegar uma cópia em: <BR>
 * Licença: http://www.lpg.adv.br/licencas/lpgpc.rtf <BR>
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa é preciso estar <BR>
 * de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 */
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
		setAtribos( 330, 190 );
		
		txaJustCanc.requestFocus();
		adic( new JScrollPane( txaJustCanc ), 7, 7, 300, 70 );
		
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
	        Funcoes.mensagemInforma(this,"Informe o motivo do cancelamento!");
    	    return;
    	}
    	else{
    	    super.ok();
	    }
    }
}
