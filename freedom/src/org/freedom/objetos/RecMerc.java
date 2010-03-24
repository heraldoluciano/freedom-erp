package org.freedom.objetos;

import java.awt.Color;
import java.awt.Font;

import javax.swing.SwingConstants;

import org.freedom.componentes.JLabelPad;
import org.freedom.infra.pojos.Constant;

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

	public static JLabelPad getLabelStatus( String status ) {

		JLabelPad ret = new JLabelPad();
		
		ret.setForeground( Color.WHITE );
		ret.setFont( new Font( "Arial", Font.BOLD, 13 ) );
		ret.setOpaque( true );
		ret.setHorizontalAlignment( SwingConstants.CENTER );
		
		if ( status == STATUS_NAO_SALVO.getValue()) {
			ret.setText( STATUS_PENDENTE.getName() );
			ret.setBackground( COR_NAO_SALVO );
		}
		else if ( STATUS_PENDENTE.getValue().equals( status )) {
			ret.setText( STATUS_PENDENTE.getName() );
			ret.setBackground( COR_PENDENTE );
		}
		else if ( STATUS_PESAGEM_1.getValue().equals( status )) {
			ret.setText( STATUS_PESAGEM_1.getName() );
			ret.setBackground( COR_PESAGEM_1 );
		}
		else if ( STATUS_PESAGEM_2.getValue().equals( status )) {
			ret.setText( STATUS_PESAGEM_2.getName() );
			ret.setBackground( COR_PESAGEM_2 );
		}
		else if ( STATUS_RECEBIMENTO_FINALIZADO.getValue().equals( status )) {
			ret.setText( STATUS_RECEBIMENTO_FINALIZADO.getName() );
			ret.setBackground( COR_RECEBIMENTO_FINALIZADO );
		}
		else if ( STATUS_NOTA_ENTRADA_EMITIDA.getValue().equals( status )) {
			ret.setText( STATUS_NOTA_ENTRADA_EMITIDA.getName() );
			ret.setBackground( COR_NOTA_ENTRADA_EMITIDA );
		}

		return ret;

	}

	
	
	
	

}



