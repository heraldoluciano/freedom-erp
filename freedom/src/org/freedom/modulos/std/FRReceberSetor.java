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

	private JRadioGroup cbTipoRel = null;

	private ListaCampos lcCli = new ListaCampos( this );

	private ListaCampos lcSetor = new ListaCampos( this );

	private ListaCampos lcVendedor = new ListaCampos( this );

	public FRReceberSetor() {

		super();

		setTitulo( "Descontos por Setor" );
		setAtribos( 80, 80, 387, 360 );

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
		adic( cbTipoRel, 7, 58, 353, 70 );
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

	}

	public void imprimir( boolean bVisualizar ) {

		if ( txtDatafim.getVlrDate().before( txtDataini.getVlrDate() ) ) {
			Funcoes.mensagemInforma( this, "Data final maior que a data inicial!" );
			return;
		}

		PreparedStatement ps = null;
		ResultSet rs = null;
		String sTipoRel = null;
		String sTitRel = null;

		try {

			int iCodCli = txtCodCli.getVlrInteger().intValue();
			int iCodSetor = txtCodSetor.getVlrInteger().intValue();
			int iCodVend = txtCodVend.getVlrInteger().intValue();

			sTipoRel = cbTipoRel.getVlrString();

			if ( sTipoRel.equals( "R" ) ) {
				sTitRel = "A RECEBER";
			}
			else if ( sTipoRel.equals( "P" ) ) {
				sTitRel = "RECEBIDAS";
			}
			else if ( sTipoRel.equals( "A" ) ) {
				sTitRel = "A RECEBER/RECEBIDAS";
			}

			StringBuffer sSQL = new StringBuffer();

			sSQL.append( "SELECT IR.DTVENCITREC, C.RAZCLI, R.DOCREC, IR.VLRPARCITREC, " );
			sSQL.append( "IR.VLRPAGOITREC, IR.VLRDESCITREC, IR.DTPAGOITREC, IR.OBSITREC " );
			sSQL.append( "FROM FNRECEBER R, FNITRECEBER IR, VDCLIENTE C, VDSETOR S, VDVENDEDOR V " );
			sSQL.append( "WHERE R.CODEMP=IR.CODEMP AND R.CODFILIAL=IR.CODFILIAL AND R.CODREC=IR.CODREC " );
			sSQL.append( "AND R.CODEMPCL=C.CODEMP AND R.CODFILIALCL=C.CODFILIAL AND R.CODCLI=C.CODCLI " );
			sSQL.append( "AND R.CODEMPVD=V.CODEMP AND R.CODFILIALVD=V.CODFILIAL AND R.CODVEND=V.CODVEND " );
			sSQL.append( "AND S.CODEMP=V.CODEMPSE AND S.CODFILIAL=V.CODFILIALSE AND S.CODSETOR=V.CODSETOR " );
			sSQL.append( "AND R.CODEMP=? AND R.CODFILIAL=? AND IR.DTVENCITREC BETWEEN ? AND ? AND IR.STATUSITREC IN (?,?,?) " );
			if ( iCodSetor > 0 ) {
				sSQL.append( "AND S.CODSETOR=? " );
			}
			if ( iCodCli > 0 ) {
				sSQL.append( "AND C.CODCLI=? " );
			}
			if ( iCodVend > 0 ) {
				sSQL.append( "AND V.CODVEND=? " );
			}

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
			dlGr = new FPrinterJob( "relatorios/receberSetor.jasper", "RELATORIO DE DESCONTOS POR SETOR", sTitRel, rs, hParam, this );

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
