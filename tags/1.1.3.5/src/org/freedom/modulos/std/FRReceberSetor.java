/**
 * @version 25/03/2004 <BR>
 * @author Setpoint Informática Ltda./Anderson Sanchez <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.pcp <BR>
 * Classe:
 * @(#)FBanco.java <BR>
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
 * Tela de cadastro de tipos de recursos de produção.
 */

package org.freedom.modulos.std;

import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import net.sf.jasperreports.engine.JasperPrintManager;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FPrinterJob;
import org.freedom.telas.FRelatorio;

public class FRReceberSetor extends FRelatorio implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtDataini = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDatafim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtCodCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtRazCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtCodSetor = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescSetor = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtCodVend = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtNomeVend = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JCheckBoxPad cbObs = new JCheckBoxPad( "Imprimir observações?", "S", "N" );

	private JCheckBoxPad cbSoVendas = new JCheckBoxPad( "Mostrar somente recebimentos de vendas?", "S", "N" );

	private JRadioGroup cbTipoRel = null;

	private JRadioGroup cbData = null;

	private ListaCampos lcCli = new ListaCampos( this );

	private ListaCampos lcSetor = new ListaCampos( this );

	private ListaCampos lcVendedor = new ListaCampos( this );

	public FRReceberSetor() {

		super();

		setTitulo( "Descontos por Setor" );
		setAtribos( 80, 80, 387, 380 );

		txtDataini.setVlrDate( new Date() );
		txtDatafim.setVlrDate( new Date() );

		lcCli.add( new GuardaCampo( txtCodCli, "CodCli", "Cód.cli.", ListaCampos.DB_PK, false ) );
		lcCli.add( new GuardaCampo( txtRazCli, "RazCli", "Razão social do cliente", ListaCampos.DB_SI, false ) );
		lcCli.montaSql( false, "CLIENTE", "VD" );
		lcCli.setReadOnly( true );
		txtCodCli.setTabelaExterna( lcCli );
		txtCodCli.setFK( true );
		txtCodCli.setNomeCampo( "CodCli" );

		lcSetor.add( new GuardaCampo( txtCodSetor, "CodSetor", "Cód.setor", ListaCampos.DB_PK, false ) );
		lcSetor.add( new GuardaCampo( txtDescSetor, "DescSetor", "Descrição do setor", ListaCampos.DB_SI, false ) );
		lcSetor.montaSql( false, "SETOR", "VD" );
		lcSetor.setReadOnly( true );
		txtCodSetor.setTabelaExterna( lcSetor );
		txtCodSetor.setFK( true );
		txtCodSetor.setNomeCampo( "CodSetor" );

		lcVendedor.add( new GuardaCampo( txtCodVend, "CodVend", "Cód.comiss.", ListaCampos.DB_PK, false ) );
		lcVendedor.add( new GuardaCampo( txtNomeVend, "NomeVend", "Nome do comissionado", ListaCampos.DB_SI, false ) );
		lcVendedor.montaSql( false, "VENDEDOR", "VD" );
		lcVendedor.setReadOnly( true );
		txtCodVend.setTabelaExterna( lcVendedor );
		txtCodVend.setFK( true );
		txtCodVend.setNomeCampo( "CodVend" );

		Vector vVals = new Vector();
		Vector vLabs = new Vector();
		vLabs.addElement( "Contas a receber" );
		vLabs.addElement( "Contas recebidas" );
		vLabs.addElement( "Ambas as contas" );
		vVals.addElement( "R" );
		vVals.addElement( "P" );
		vVals.addElement( "A" );

		cbTipoRel = new JRadioGroup( 3, 1, vLabs, vVals );
		cbTipoRel.setVlrString( "A" );

		Vector vVals1 = new Vector();
		Vector vLabs1 = new Vector();
		vLabs1.addElement( "Faturamento" );
		vLabs1.addElement( "Vencimento" );
		vVals1.addElement( "F" );
		vVals1.addElement( "V" );

		cbData = new JRadioGroup( 2, 1, vLabs1, vVals1 );
		cbData.setVlrString( "V" );

		cbObs.setVlrString( "S" );
		cbSoVendas.setVlrString( "S" );

		JLabelPad lbLinha = new JLabelPad();
		lbLinha.setBorder( BorderFactory.createEtchedBorder() );
		JLabelPad lbPeriodo = new JLabelPad( "   Periodo:" );
		lbPeriodo.setOpaque( true );

		adic( lbPeriodo, 17, 0, 80, 20 );
		adic( lbLinha, 7, 10, 353, 40 );
		adic( new JLabelPad( "De:" ), 17, 25, 30, 20 );
		adic( txtDataini, 50, 25, 97, 20 );
		adic( new JLabelPad( "Até:" ), 157, 25, 30, 20 );
		adic( txtDatafim, 190, 25, 100, 20 );
		adic( cbTipoRel, 7, 58, 190, 70 );

		JLabelPad lbLinha2 = new JLabelPad();
		lbLinha2.setBorder( BorderFactory.createEtchedBorder() );
		JLabel lbData = new JLabelPad( "   Data por:" );
		lbData.setOpaque( true );
		adic( lbData, 205, 48, 80, 20 );
		adic( lbLinha2, 200, 58, 160, 70 );
		adic( cbData, 200, 68, 160, 50 );
		cbData.setBorder( BorderFactory.createEmptyBorder() );

		adic( new JLabelPad( "Cód.cli." ), 7, 130, 200, 20 );
		adic( txtCodCli, 7, 150, 80, 20 );
		adic( new JLabelPad( "Razão social do cliente" ), 90, 130, 200, 20 );
		adic( txtRazCli, 90, 150, 270, 20 );
		adic( new JLabelPad( "Cód.setor" ), 7, 170, 250, 20 );
		adic( txtCodSetor, 7, 190, 80, 20 );
		adic( new JLabelPad( "Descrição do setor" ), 90, 170, 250, 20 );
		adic( txtDescSetor, 90, 190, 270, 20 );
		adic( new JLabelPad( "Cód.comis." ), 7, 210, 250, 20 );
		adic( txtCodVend, 7, 230, 80, 20 );
		adic( new JLabelPad( "Nome do comissionado" ), 90, 210, 250, 20 );
		adic( txtNomeVend, 90, 230, 270, 20 );
		adic( cbObs, 10, 260, 200, 20 );
		adic( cbSoVendas, 10, 280, 300, 20 );

	}

	public void imprimir( boolean bVisualizar ) {

		if ( txtDatafim.getVlrDate().before( txtDataini.getVlrDate() ) ) {
			Funcoes.mensagemInforma( this, "Data final maior que a data inicial!" );
			return;
		}

		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sSQL = new StringBuffer();
		StringBuffer sWhere = new StringBuffer();
		StringBuffer sFrom = new StringBuffer();
		String sTipoRel = null;
		StringBuffer sFiltros = new StringBuffer();

		try {

			int iCodCli = txtCodCli.getVlrInteger().intValue();
			int iCodSetor = txtCodSetor.getVlrInteger().intValue();
			int iCodVend = txtCodVend.getVlrInteger().intValue();

			sTipoRel = cbTipoRel.getVlrString();

			if ( sTipoRel.equals( "R" ) ) {
				sFiltros.append( "A RECEBER\n" );
			}
			else if ( sTipoRel.equals( "P" ) ) {
				sFiltros.append( "RECEBIDAS\n" );
			}
			else if ( sTipoRel.equals( "A" ) ) {
				sFiltros.append( "A RECEBER  /  RECEBIDAS" );
			}

			sFrom.append( "FROM FNRECEBER R, FNITRECEBER IR, VDCLIENTE C " );

			if ( iCodSetor > 0 || iCodVend > 0 ) {
				sFrom.append( ", VDVENDEDOR VD " );
				sWhere.append( "AND R.CODEMPVD=VD.CODEMP AND R.CODFILIALVD=VD.CODFILIAL AND R.CODVEND=VD.CODVEND " );
				if ( iCodSetor > 0 ) {
					sFrom.append( ", VDSETOR S " );
					sWhere.append( "AND S.CODEMP=VD.CODEMPSE AND S.CODFILIAL=VD.CODFILIALSE AND S.CODSETOR=VD.CODSETOR " );
				}
			}

			if ( iCodSetor > 0 ) {
				sWhere.append( "AND S.CODSETOR=? " );
				sFiltros.append( "SETOR = " + txtDescSetor.getVlrString().trim() );
			}
			if ( iCodCli > 0 ) {
				sWhere.append( "AND C.CODCLI=? " );
				sFiltros.append( sFiltros.indexOf( "SETOR" ) > -1 ? "  /  " : "\n" );
				sFiltros.append( "CLIENTE = " + txtRazCli.getVlrString().trim() );
			}
			if ( iCodVend > 0 ) {
				sWhere.append( "AND VD.CODVEND=? " );
				sFiltros.append( sFiltros.indexOf( "SETOR" ) > -1 || sFiltros.indexOf( "CLIENTE" ) > -1 ? "  /  " : "\n" );
				sFiltros.append( "VENDEDOR = " + txtNomeVend.getVlrString().trim() );
			}

			if ( "S".equals( cbSoVendas.getVlrString() ) ) {
				sFiltros.append( "\nSOMENTE RESEBIMENTOS SOBRE VENDAS" );
				sFrom.append( ", VDVENDA V, EQTIPOMOV TM " );
				sWhere.append( "AND R.CODEMPVA=V.CODEMP AND R.CODFILIALVA=V.CODFILIAL AND R.CODVENDA=V.CODVENDA AND R.TIPOVENDA=V.TIPOVENDA " );
				sWhere.append( "AND V.CODEMPTM=TM.CODEMP AND V.CODFILIALTM=TM.CODFILIAL AND V.CODTIPOMOV=TM.CODTIPOMOV AND TM.TIPOMOV IN('PV','VD','VE','VT','SE') " );
			}

			sSQL.append( "SELECT IR.DTVENCITREC, C.RAZCLI, R.DOCREC, IR.VLRPARCITREC, " );
			sSQL.append( "IR.VLRPAGOITREC, IR.VLRDESCITREC, IR.DTPAGOITREC, IR.OBSITREC " );
			sSQL.append( sFrom );
			sSQL.append( "WHERE R.CODEMP=IR.CODEMP AND R.CODFILIAL=IR.CODFILIAL AND R.CODREC=IR.CODREC " );
			sSQL.append( "AND R.CODEMPCL=C.CODEMP AND R.CODFILIALCL=C.CODFILIAL AND R.CODCLI=C.CODCLI " );
			sSQL.append( "AND R.CODEMP=? AND R.CODFILIAL=? " );

			if ( "V".equals( cbData.getVlrString() ) ) {
				sFiltros.append( "\nDATA POR VENCOMENTO" );
				sSQL.append( "AND IR.DTVENCITREC BETWEEN ? AND ? " );
			}
			else {
				if ( sFrom.indexOf( "VDVENDA" ) == -1 ) {
					sSQL.append( ", VDVENDA V " );
					sWhere.append( "AND R.CODEMPVA=V.CODEMP AND R.CODFILIALVA=V.CODFILIAL AND R.CODVENDA=V.CODVENDA AND R.TIPOVENDA=V.TIPOVENDA " );
				}
				sFiltros.append( "\nDATA POR FATURAMENTO" );
				sSQL.append( "AND V.DTEMITVENDA BETWEEN ? AND ? " );
			}

			sSQL.append( "AND IR.STATUSITREC IN (?,?,?) " );
			sSQL.append( sWhere );
			sSQL.append( "ORDER BY 1" );

			ps = con.prepareStatement( sSQL.toString() );

			int iparam = 1;
			ps.setInt( iparam++, Aplicativo.iCodEmp );
			ps.setInt( iparam++, Aplicativo.iCodFilial );
			ps.setDate( iparam++, Funcoes.dateToSQLDate( txtDataini.getVlrDate() ) );
			ps.setDate( iparam++, Funcoes.dateToSQLDate( txtDatafim.getVlrDate() ) );

			if ( sTipoRel.equals( "R" ) ) {
				ps.setString( iparam++, "R1" );
				ps.setString( iparam++, "RL" );
				ps.setString( iparam++, "RL" );
			}
			else if ( sTipoRel.equals( "P" ) ) {
				ps.setString( iparam++, "RP" );
				ps.setString( iparam++, "RL" );
				ps.setString( iparam++, "RL" );
			}
			else if ( sTipoRel.equals( "A" ) ) {
				ps.setString( iparam++, "R1" );
				ps.setString( iparam++, "RL" );
				ps.setString( iparam++, "RP" );
			}

			if ( iCodSetor > 0 ) {
				ps.setInt( iparam++, iCodSetor );
			}
			if ( iCodCli > 0 ) {
				ps.setInt( iparam++, iCodCli );
			}
			if ( iCodVend > 0 ) {
				ps.setInt( iparam++, iCodVend );
			}

			rs = ps.executeQuery();

			HashMap hParam = new HashMap();
			hParam.put( "MOSTRAOBS", cbObs.getVlrString() );

			FPrinterJob dlGr = null;
			dlGr = new FPrinterJob( "relatorios/receberSetor.jasper", "RELATORIO DE DESCONTOS POR SETOR", sFiltros.toString(), rs, hParam, this );

			if ( bVisualizar ) {
				dlGr.setVisible( true );
			}
			else {
				try {
					JasperPrintManager.printReport( dlGr.getRelatorio(), true );
				} catch ( Exception err ) {
					Funcoes.mensagemErro( this, "Erro na impressão de recursos de produção!" + err.getMessage(), true, con, err );
				}
			}

			if ( !con.getAutoCommit() ) {
				con.commit();
			}

		} catch ( Exception err ) {
			err.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao montar relatorio!.\n" + err.getMessage() );
			return;
		}

	}

	public void setConexao( Connection cn ) {

		super.setConexao( cn );

		lcCli.setConexao( cn );
		lcSetor.setConexao( cn );
		lcVendedor.setConexao( cn );

	}
}
