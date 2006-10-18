/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)DLFechaRec.java <BR>
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
 * Comentários sobre a classe...
 */

package org.freedom.modulos.std;

import java.awt.Component;
import java.math.BigDecimal;
import java.util.Date;

import org.freedom.componentes.JLabelPad;

import org.freedom.componentes.JTextFieldPad;
import org.freedom.telas.FFDialogo;

public class DLFechaParcela extends FFDialogo {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtParcItRec = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, 2 );

	private JTextFieldPad txtDtVencItRec = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtVlrDescItRec = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, 2 );

	public DLFechaParcela( Component cOrig, BigDecimal bigParcItRec, Date dDtVencItRec, BigDecimal bigDescItRec ) {

		super( cOrig );
		setTitulo( "Parcela" );
		setAtribos( 100, 100, 250, 220 );
		
		if ( bigDescItRec == null ) {
			txtVlrDescItRec.setAtivo( false );
		}

		txtParcItRec.setVlrBigDecimal( bigParcItRec );
		txtDtVencItRec.setVlrDate( dDtVencItRec );
		txtVlrDescItRec.setVlrBigDecimal( bigDescItRec );

		adic( new JLabelPad( "Valor" ), 7, 0, 100, 20 );
		adic( new JLabelPad( "Vencimento" ), 110, 0, 100, 20 );
		adic( new JLabelPad( "Desconto" ), 7, 40, 100, 20 );
		adic( txtParcItRec, 7, 20, 100, 20 );
		adic( txtDtVencItRec, 110, 20, 100, 20 );
		adic( txtVlrDescItRec, 7, 60, 100, 20 );
	}

	public Object[] getValores() {

		Object[] oRetorno = new Object[ 3 ];
		oRetorno[ 0 ] = txtParcItRec.getVlrBigDecimal();
		oRetorno[ 1 ] = txtDtVencItRec.getVlrDate();
		oRetorno[ 2 ] = txtVlrDescItRec.getVlrBigDecimal();

		return oRetorno;
	}
}
