/**
 * @version 10/06/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.pdv <BR>
 * Classe:
 * @(#)FAbreCaixa.java <BR>
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
 * 
 */

package org.freedom.modulos.pdv;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JLabel;

import org.freedom.componentes.JTextFieldPad;
import org.freedom.ecf.app.Control;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.AplicativoPDV;
import org.freedom.telas.FDialogo;

public class FAbreCaixa extends FDialogo {

	private static final long serialVersionUID = 1L;

	private final JTextFieldPad txtData = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private final JTextFieldPad txtValor = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 10, 2 );

	private final Control ecf;

	private Connection con = null;

	public FAbreCaixa() {

		setTitulo( "Abertura de Caixa", this.getClass().getName() );
		setAtribos( 235, 140 );

		montaTela();

		txtData.setVlrDate( Calendar.getInstance().getTime() );
		txtData.setAtivo( false );
		txtValor.setVlrBigDecimal( new BigDecimal( 0 ) );

		ecf = new Control( 
				AplicativoPDV.getEcfdriver(), 
				AplicativoPDV.getPortaECF(), 
				AplicativoPDV.bModoDemo );
		
		btOK.addKeyListener( this );
		btCancel.addKeyListener( this );
	}

	private void montaTela() {

		adic( new JLabel( "Data" ), 10, 10, 90, 20 );
		adic( txtData, 10, 30, 90, 20 );
		adic( new JLabel( "Valor" ), 105, 10, 105, 20 );
		adic( txtValor, 105, 30, 105, 20 );
	}

	private void dbAbrirCaixa() {

		if ( ! ecf.leituraX() ) {
			Funcoes.mensagemErro( this, ecf.getMessageLog() );
			return;
		}
		if ( ! ecf.suprimento( txtValor.getVlrBigDecimal() ) ) {
			Funcoes.mensagemErro( this, ecf.getMessageLog() );
			return;
		}

		try {

			PreparedStatement ps = con.prepareStatement( "EXECUTE PROCEDURE PVABRECAIXASP(?,?,?,?,?,?,?)" );

			ps.setInt( 1, AplicativoPDV.iCodCaixa );
			ps.setInt( 2, Aplicativo.iCodFilial );
			ps.setInt( 3, Aplicativo.iCodEmp );
			ps.setBigDecimal( 4, txtValor.getVlrBigDecimal() );
			ps.setDate( 5, Funcoes.dateToSQLDate( new Date() ) );
			ps.setInt( 6, Aplicativo.iCodFilialPad );
			ps.setString( 7, Aplicativo.strUsuario );
			ps.execute();

			ps.close();

			if ( !con.getAutoCommit() ) {
				con.commit();
			}

		} catch ( SQLException e ) {
			Funcoes.mensagemErro( this, "Erro ao abrir o caixa!\n" + e.getMessage(), true, con, e );
			e.printStackTrace();
		}

		ecf.abrirGaveta();
	}

	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == btOK ) {
			dbAbrirCaixa();
		}

		super.actionPerformed( evt );
	}

	public void setConexao( Connection cn ) {

		con = cn;
	}
}
