/**
 * @author Setpoint Informática Ltda.
 * @author Reginaldo Garcia Heua <BR>
 * @version 15/07/2008
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.pcp <BR>
 * Classe:
 * @(#)DLObsJust.java <BR>
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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JScrollPane;

import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JTextAreaPad;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FFDialogo;


public class DLObsJust extends FFDialogo{

	private static final long serialVersionUID = 1L;
	
	private JTextFieldPad txtDtCanc = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );
	
	private JTextAreaPad txaJustCanc = new JTextAreaPad();
	
	private JTextFieldPad txtHoraCanc = new JTextFieldPad( JTextFieldPad.TP_TIME , 8,0 ); 
	
	private JScrollPane scrJus = new JScrollPane( txaJustCanc );
	
	private JTextFieldPad txtIdUsuCanc = new JTextFieldPad( JTextFieldPad.TP_STRING,  8, 0 );

	public DLObsJust(){
		
	}
	
	public DLObsJust( Connection cn, int seqOp, int codOp ){
		
		setConexao( cn );
		setTitulo( "Motivo do cancelamento" );
		setAtribos( 350, 230 );
		adic( new JLabelPad( "Dt.canc." ), 7, 10, 70, 20 );
		adic( txtDtCanc, 7, 30, 70, 20 );
		adic( new JLabelPad("Hora canc."), 80, 10, 70, 20 );
		adic( txtHoraCanc, 80, 30, 70, 20 );
		adic( new JLabelPad("Id.Usu."), 153, 10, 70, 20 );
		adic( txtIdUsuCanc, 153, 30, 70, 20 );
		adic( scrJus, 7, 60, 300, 70 );
	
		
		txtDtCanc.setSoLeitura( true );
		txtIdUsuCanc.setSoLeitura( true );
		txaJustCanc.setEnabled( false );
		txtHoraCanc.setSoLeitura( true );
		
		btCancel.setVisible( false ); 
		
		buscaObs( seqOp, codOp );
	}
	
	public void buscaObs( int seqOp, int codOp ){
		
		StringBuilder sSQL = new StringBuilder();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		sSQL.append( "SELECT P.JUSTIFICCANC, P.DTCANC, P.HCANC, P.IDUSUCANC FROM PPOP P WHERE " );
		sSQL.append( "P.CODEMP=? AND CODFILIAL=? AND P.SEQOP=? AND P.CODOP=? ");
		
		try {
			
			ps = con.prepareStatement( sSQL.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "PPOP" ) );
			ps.setInt( 3, seqOp );
			ps.setInt( 4, codOp );
			rs = ps.executeQuery();
			
			if( rs.next() ){
				
				txtDtCanc.setVlrDate( rs.getDate( "DTCANC" ) );
				txaJustCanc.setVlrString( rs.getString( "JUSTIFICCANC" ) );
				txtIdUsuCanc.setVlrString( rs.getString( "IDUSUCANC" ) );
				txtHoraCanc.setVlrTime( rs.getTime( "HCANC" ) );
				
			}
			
		} catch ( SQLException err ) {
			
			err.printStackTrace();
		}
	}
	
	public void setConexao( Connection cn ){
		super.setConexao( cn );
	}
}
