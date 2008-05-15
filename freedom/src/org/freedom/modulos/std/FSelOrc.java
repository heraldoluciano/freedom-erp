/**
 * @version 13/05/2008 <BR>
 * @author Setpoint Inform�tica Ltda./Reginaldo Garcia Heua <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)FSelOrc.java <BR>
 * 
 * Este programa � licenciado de acordo com a LPG-PC (Licen�a P�blica Geral para Programas de Computador), <BR>
 * vers�o 2.1.0 ou qualquer vers�o posterior. <BR>
 * A LPG-PC deve acompanhar todas PUBLICA��ES, DISTRIBUI��ES e REPRODU��ES deste Programa. <BR>
 * Caso uma c�pia da LPG-PC n�o esteja dispon�vel junto com este Programa, voc� pode contatar <BR>
 * o LICENCIADOR ou ent�o pegar uma c�pia em: <BR>
 * Licen�a: http://www.lpg.adv.br/licencas/lpgpc.rtf <BR>
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa � preciso estar <BR>
 * de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 * 
 * 
 */

package org.freedom.modulos.std;

import java.util.Vector;

import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JRadioGroup;
import org.freedom.telas.FFDialogo;

public class FSelOrc extends FFDialogo{

	private static final long serialVersionUID = 1L;
	
	String retorno = null;

	JRadioGroup<?, ?> Rgrel = null;
	
	Vector<String> vLabs = new Vector<String>();
	
	Vector<String> vVals = new Vector<String>();
	
	int tamanho = 0;
	
	public FSelOrc(){
		
		setTitulo( "Layouts","Layouts" );
		setAtribos( 300, 220 );
	}
	
	public String seleciona( String[] arg, String[] desc ){
		
		for(int i=0; i<arg.length; i++){
			if (i<desc.length) {
				vLabs.addElement( desc[i].trim() );
			} else {
				vLabs.addElement( arg[i].trim() );
			}
			vVals.addElement( arg[i].trim() );
		}
		Rgrel = new JRadioGroup<String, String>( arg.length, 1, vLabs, vVals );
		tamanho = arg.length;
		montaTela();
		setVisible( true );
		
		
		if( OK ){
			retorno = Rgrel.getVlrString();
	    }
		
		return retorno;
	}

	public void montaTela(){
		
		adic( new JLabelPad("Escolha o Layout: "),7, 5,  150, 10);
		adic( Rgrel, 7, 25, 250, tamanho + 75 );
	}
}
