/**
 * @version 15/07/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.pdv <BR>
 * Classe:
 * @(#)FGravaMoeda.java <BR>
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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.swing.BorderFactory;

import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.drivers.ECFDriver;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.AplicativoPDV;
import org.freedom.telas.FFDialogo;

public class FSangria extends FFDialogo {

	private static final long serialVersionUID = 1L;

	private JTextFieldFK txtDataUOper = new JTextFieldFK( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldFK txtSaldoUOper = new JTextFieldFK( JTextFieldPad.TP_DECIMAL, 15, 2 );

	private JTextFieldFK txtStatusUOper = new JTextFieldFK( JTextFieldPad.TP_STRING, 30, 0 );

	private JTextFieldFK txtUsuUOper = new JTextFieldFK( JTextFieldPad.TP_STRING, 20, 0 );

	private JTextFieldFK txtUsu = new JTextFieldFK( JTextFieldPad.TP_STRING, 20, 0 );

	private JTextFieldFK txtData = new JTextFieldFK( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtValor = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, 2 );

	private ECFDriver ecf = new ECFDriver( !AplicativoPDV.usaEcfDriver() );

	public FSangria() {

		super( Aplicativo.telaPrincipal );
		setTitulo( "Sangria de Caixa" );
		setAtribos( 390, 275 );

		adic( new JLabelPad( "Data da última operação" ), 7, 5, 150, 15 );
		adic( txtDataUOper, 7, 20, 150, 20 );
		adic( new JLabelPad( "Saldo atual" ), 160, 5, 97, 15 );
		adic( txtSaldoUOper, 160, 20, 97, 20 );
		adic( new JLabelPad( "Status atual" ), 260, 5, 100, 15 );
		adic( txtStatusUOper, 260, 20, 100, 20 );
		adic( new JLabelPad( "Último operador" ), 7, 40, 250, 20 );
		adic( txtUsuUOper, 7, 60, 250, 20 );

		JLabelPad lbLinha = new JLabelPad();
		lbLinha.setBorder( BorderFactory.createEtchedBorder() );

		adic( lbLinha, 7, 85, 365, 2 );

		adic( new JLabelPad( "Operador atual" ), 7, 90, 150, 20 );
		adic( txtUsu, 7, 110, 200, 20 );
		adic( new JLabelPad( "Data" ), 7, 130, 100, 20 );
		adic( txtData, 7, 150, 100, 20 );
		adic( new JLabelPad( "Valor" ), 110, 130, 100, 20 );
		adic( txtValor, 110, 150, 100, 20 );

		txtUsu.setVlrString( Aplicativo.strUsuario );
		txtData.setVlrDate( new Date() );
		txtValor.setVlrBigDecimal( new BigDecimal( 0 ) );

	}

	private boolean verifCaixa() {

		boolean bRetorno = false;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sSQL = null;
		int iRet = -1;

		try {

			sSQL = "SELECT IRETORNO FROM PVVERIFCAIXASP (?,?,?,?,?,?)";
			ps = con.prepareStatement( sSQL );
			ps.setInt( 1, AplicativoPDV.iCodCaixa );
			ps.setInt( 2, Aplicativo.iCodEmp );
			ps.setInt( 3, ListaCampos.getMasterFilial( "PVCAIXA" ) );
			ps.setDate( 4, Funcoes.dateToSQLDate( new Date() ) );
			ps.setInt( 5, Aplicativo.iCodFilial );
			ps.setString( 6, Aplicativo.strUsuario );
			rs = ps.executeQuery();
			
			if ( rs.next() ) {
				iRet = rs.getInt( 1 );
			}

			rs.close();
			ps.close();
			
			if ( !con.getAutoCommit() ) {
				con.commit();
			}

		} catch ( SQLException err ) {
			err.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao verificar o caixa!!\n" + err.getMessage(), true, con, err );
		} finally {
			ps = null;
			rs = null;
			sSQL = null;
		}

		if ( iRet != 4 && iRet != 2 ) {
			Funcoes.mensagemErro( this, "Caixa não esta aberto!!" );
		}
		else {
			bRetorno = true;
		}

		return bRetorno;

	}

	private void carregaInfo() {

		PreparedStatement ps = null;
		ResultSet rs = null;
		String sSQL = null;

		try {

			sSQL = "SELECT DDTAMOVRET, CTIPOMOV, NVLRSLDMOV, CIDUSU FROM PVRETMOVCAIXASP(?,?,?,?)";
			ps = con.prepareStatement( sSQL );
			ps.setInt( 1, AplicativoPDV.iCodCaixa );
			ps.setInt( 2, Aplicativo.iCodEmp );
			ps.setInt( 3, ListaCampos.getMasterFilial( "PVMOVCAIXA" ) );
			ps.setDate( 4, Funcoes.dateToSQLDate( new Date() ) );
			rs = ps.executeQuery();
			
			if ( rs.next() ) {
				txtDataUOper.setVlrDate( rs.getDate( "DDTAMOVRET" ) );
				txtStatusUOper.setVlrString( ecf.transStatus( rs.getString( "CTIPOMOV" ).toCharArray()[ 0 ] ) );
				txtSaldoUOper.setVlrString( Funcoes.strDecimalToStrCurrency( 10, 2, rs.getString( "NVLRSLDMOV" ) ) );
				txtUsuUOper.setVlrString( rs.getString( "CIDUSU" ) );
			}

			rs.close();
			ps.close();
			
			if ( !con.getAutoCommit() ) {
				con.commit();
			}

		} catch ( SQLException err ) {
			err.printStackTrace();
			Funcoes.mensagemErro( this, "Erro carregar informações do caixa!!\n" + err.getMessage(), true, con, err );
		} finally {
			ps = null;
			rs = null;
			sSQL = null;
		}

	}

	private boolean execSangria() {

		boolean bRet = false;

		if ( txtValor.doubleValue() <= 0 ) {
			Funcoes.mensagemInforma( this, "Valor de sangria inválido!" );
			return false;
		}

		PreparedStatement ps = null;
		String sSQL = null;

		try {
			
			sSQL = "EXECUTE PROCEDURE PVSANGRIASP(?,?,?,?,?,?)";
			ps = con.prepareStatement( sSQL );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "PVMOVCAIXA" ) );
			ps.setBigDecimal( 3, txtValor.getVlrBigDecimal() );
			ps.setInt( 4, AplicativoPDV.iCodCaixa );
			ps.setDate( 5, Funcoes.dateToSQLDate( new Date() ) );
			ps.setString( 6, Aplicativo.strUsuario );
			ps.execute();

			ps.close();
			
			if ( !con.getAutoCommit() ) {
				con.commit();
			}

			bRet = true;

		} catch ( SQLException err ) {
			err.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao executar sangria!\n" + err.getMessage(), true, con, err );
		} finally {
			ps = null;
			sSQL = null;
		}

		return bRet;

	}

	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == btOK ) {
			if ( execSangria() && AplicativoPDV.bECFTerm ) {
				ecf.sangria( txtValor.getVlrBigDecimal() );
				ecf.abreGaveta();
			}
			else {
				return;
			}
		}
		
		super.actionPerformed( evt );
	}

	public void setConexao( Connection cn ) {

		super.setConexao( cn );
		
		if ( verifCaixa() ) {
			carregaInfo();
		}
		else {
			setVisible( false );
		}
	}
}
