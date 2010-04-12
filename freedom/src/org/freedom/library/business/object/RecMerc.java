package org.freedom.library.business.object;

import java.awt.Color;
import java.awt.Font;

import javax.swing.SwingConstants;

import org.freedom.infra.pojos.Constant;
import org.freedom.library.swing.JLabelPad;

public class RecMerc implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	
	//Status do recebimento
	
	public static final Constant STATUS_NAO_SALVO = new Constant("Não Salvo", null );
	
	public static final Color COR_NAO_SALVO = Color.GRAY;
	
	public static final Constant STATUS_PENDENTE = new Constant("Pendente", "PE"); 
	
	public static final Color COR_PENDENTE = Color.ORANGE;
	
	public static final Constant STATUS_PESAGEM_1 = new Constant("Pesagem 1", "E1");
	
	public static final Color COR_PESAGEM_1 = Color.BLUE;
	
	public static final Constant STATUS_PESAGEM_2 = new Constant("Pesagem 1", "E2");
	
	public static final Color COR_PESAGEM_2 = Color.BLUE;
		
	public static final Constant STATUS_RECEBIMENTO_FINALIZADO = new Constant("Finalizado", "FN");
	
	public static final Color COR_RECEBIMENTO_FINALIZADO = new Color( 45, 190, 60 );

	public static final Constant STATUS_NOTA_ENTRADA_EMITIDA = new Constant("Nota emitida", "NE");
	
	public static final Color COR_NOTA_ENTRADA_EMITIDA = Color.RED;

	public static void atualizaStatus( String status, JLabelPad lbstatus ) {

		lbstatus.setForeground( Color.WHITE );
		lbstatus.setFont( new Font( "Arial", Font.BOLD, 13 ) );
		lbstatus.setOpaque( true );
		lbstatus.setHorizontalAlignment( SwingConstants.CENTER );
		
		if ( status == STATUS_NAO_SALVO.getValue()) {
			lbstatus.setText( STATUS_PENDENTE.getName() );
			lbstatus.setBackground( COR_NAO_SALVO );
		}
		else if ( STATUS_PENDENTE.getValue().equals( status )) {
			lbstatus.setText( STATUS_PENDENTE.getName() );
			lbstatus.setBackground( COR_PENDENTE );
		}
		else if ( STATUS_PESAGEM_1.getValue().equals( status )) {
			lbstatus.setText( STATUS_PESAGEM_1.getName() );
			lbstatus.setBackground( COR_PESAGEM_1 );
		}
		else if ( STATUS_PESAGEM_2.getValue().equals( status )) {
			lbstatus.setText( STATUS_PESAGEM_2.getName() );
			lbstatus.setBackground( COR_PESAGEM_2 );
		}
		else if ( STATUS_RECEBIMENTO_FINALIZADO.getValue().equals( status )) {
			lbstatus.setText( STATUS_RECEBIMENTO_FINALIZADO.getName() );
			lbstatus.setBackground( COR_RECEBIMENTO_FINALIZADO );
		}
		else if ( STATUS_NOTA_ENTRADA_EMITIDA.getValue().equals( status )) {
			lbstatus.setText( STATUS_NOTA_ENTRADA_EMITIDA.getName() );
			lbstatus.setBackground( COR_NOTA_ENTRADA_EMITIDA );
		}

	}	

}



