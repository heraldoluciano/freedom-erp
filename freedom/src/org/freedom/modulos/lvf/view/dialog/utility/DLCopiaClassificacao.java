package org.freedom.modulos.lvf.view.dialog.utility;

import java.awt.event.ActionEvent;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.dialog.FFDialogo;


public class DLCopiaClassificacao  extends FFDialogo implements CarregaListener {
	
	private static final long serialVersionUID = 1L;
	
	private String codfisc;
	
	private final JTextFieldPad txtCodFisc = new JTextFieldPad( JTextFieldPad.TP_STRING, 13, 0 );
	
	public DLCopiaClassificacao(){
		super();
	}
	
	public DLCopiaClassificacao(String codfisc){
		this.codfisc = codfisc;
		txtCodFisc.setVlrString( codfisc );
		montaTela();
	}
	
	public void montaTela(){
		setTitulo( "Copia Classificação Fiscal" );
		setAtribos( 50, 50, 450, 200 );
		
		adic( txtCodFisc , 7, 25, 100, 20, "Cód.class.fiscal" );
	}
	
		
	public void beforeCarrega( CarregaEvent cevt ) {

	}

	public void afterCarrega( CarregaEvent cevt ) {

	}
	
	public void actionPerformed( ActionEvent evt ) {
		boolean result = false;
		if (evt.getSource()==btOK) {
            result = copiaClassificacao();			
		} else if (evt.getSource()==btCancel) {
			result = true;
		}
		if ( result ) {
			super.actionPerformed( evt );
		}
	}
	
	public boolean copiaClassificacao(){
		
		return true;
	}
	

}
