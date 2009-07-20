/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)DLRCliente.java <BR>
 * 
 * Este arquivo é parte do sistema Freedom-ERP, o Freedom-ERP é um software livre; você pode redistribui-lo e/ou <BR>
 * modifica-lo dentro dos termos da Licença Pública Geral GNU como publicada pela Fundação do Software Livre (FSF); <BR>
 * na versão 2 da Licença, ou (na sua opnião) qualquer versão. <BR>
 * Este programa é distribuido na esperança que possa ser  util, mas SEM NENHUMA GARANTIA; <BR>
 * sem uma garantia implicita de ADEQUAÇÂO a qualquer MERCADO ou APLICAÇÃO EM PARTICULAR. <BR>
 * Veja a Licença Pública Geral GNU para maiores detalhes. <BR>
 * Você deve ter recebido uma cópia da Licença Pública Geral GNU junto com este programa, se não, <BR>
 * de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 * Comentários sobre a classe...
 */

package org.freedom.modulos.std;

import java.awt.Component;
import org.freedom.infra.model.jdbc.DbConnection;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.SwingConstants;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.telas.FFDialogo;

public class DLRCliente extends FFDialogo {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtCid = new JTextFieldPad( JTextFieldPad.TP_STRING, 30, 0 );

	private JTextFieldPad txtBairro = new JTextFieldPad( JTextFieldPad.TP_STRING, 30, 0 );

	private JTextFieldPad txtDe = new JTextFieldPad( JTextFieldPad.TP_STRING, 30, 0 );

	private JTextFieldPad txtA = new JTextFieldPad( JTextFieldPad.TP_STRING, 30, 0 );

	private JTextFieldPad txtEstCli = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );
	
	private JTextFieldPad txtCodSetor = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtCodTipoCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtCodClasCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtCodVend = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescSetor = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldFK txtDescTipoCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldFK txtDescClasCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldFK txtNomeVend = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JCheckBoxPad cbObs = new JCheckBoxPad( "Imprimir Observações ?", "S", "N" );

	private JCheckBoxPad cbFis = new JCheckBoxPad( "Física", "S", "N" );

	private JCheckBoxPad cbJur = new JCheckBoxPad( "Jurídica", "S", "N" );
	
	private JCheckBoxPad cbAtiv = new JCheckBoxPad( "Somente Ativos", "S", "N" );
	
	private JCheckBoxPad cbInativ = new JCheckBoxPad( "Somente Inativos", "S", "N" );

	private JRadioGroup<?, ?> rgOrdem = null;

	private JRadioGroup<?, ?> rgModo = null;

	private JRadioGroup<?, ?> rgEnd = null;

	private ListaCampos lcSetor = new ListaCampos( this );

	private ListaCampos lcTipoCli = new ListaCampos( this );

	private ListaCampos lcClasCli = new ListaCampos( this );

	private ListaCampos lcVendedor = new ListaCampos( this );

	private Vector<String> vLabsOrdem = new Vector<String>();

	private Vector<String> vValsOrdem = new Vector<String>();

	private Vector<String> vLabsModo = new Vector<String>();

	private Vector<String> vValsModo = new Vector<String>();

	private Vector<String> vLabsEnd = new Vector<String>();

	private Vector<String> vValsEnd = new Vector<String>();

	public DLRCliente( Component cOrig, DbConnection cn ) {

		super( cOrig );
		setTitulo( "Relatório de Clientes" );
		setAtribos( 465, 540 );
		setLocationRelativeTo( this );

		vLabsOrdem.addElement( "Código" );
		vLabsOrdem.addElement( "Razão" );
		vLabsOrdem.addElement( "Cidade" );
		vValsOrdem.addElement( "C" );
		vValsOrdem.addElement( "R" );
		vValsOrdem.addElement( "I" );
		rgOrdem = new JRadioGroup<String, String>( 1, 3, vLabsOrdem, vValsOrdem );
		rgOrdem.setVlrString( "R" );

		vLabsModo.addElement( "Resumido 1" );
		vLabsModo.addElement( "Resumido 2" );
		vLabsModo.addElement( "Resumido 3" );
		vLabsModo.addElement( "Completo" );
		vLabsModo.addElement( "Alinhar  Filial" );
		vValsModo.addElement( "1" );
		vValsModo.addElement( "2" );
		vValsModo.addElement( "3" );
		vValsModo.addElement( "C" );
		vValsModo.addElement( "A" );

		rgModo = new JRadioGroup<String, String>( 2, 3, vLabsModo, vValsModo );
		rgModo.setVlrString( "R" );
		
		vLabsEnd.addElement( "Cadast." );
		vLabsEnd.addElement( "Ent." );
		vLabsEnd.addElement( "Cob." );
		vValsEnd.addElement( "A" );
		vValsEnd.addElement( "E" );
		vValsEnd.addElement( "C" );
		rgEnd = new JRadioGroup<String, String>( 1, 3, vLabsEnd, vValsEnd );
		rgEnd.setVlrString( "A" );

		cbObs.setVlrString( "N" );
		cbAtiv.setVlrString( "S" );
		cbInativ.setVlrString( "N" );		
		
		cbFis.setVlrString( "S" );
		cbJur.setVlrString( "S" );

		lcSetor.add( new GuardaCampo( txtCodSetor, "CodSetor", "Cód.setor", ListaCampos.DB_PK, false ) );
		lcSetor.add( new GuardaCampo( txtDescSetor, "DescSetor", "Descrição do setor", ListaCampos.DB_SI, false ) );
		lcSetor.montaSql( false, "SETOR", "VD" );
		lcSetor.setReadOnly( true );
		txtCodSetor.setTabelaExterna( lcSetor );
		txtCodSetor.setFK( true );
		txtCodSetor.setNomeCampo( "CodSetor" );

		lcTipoCli.add( new GuardaCampo( txtCodTipoCli, "CodTipoCli", "Cód.tp.cli.", ListaCampos.DB_PK, false ) );
		lcTipoCli.add( new GuardaCampo( txtDescTipoCli, "DescTipoCli", "Descrição do tipo de cliente", ListaCampos.DB_SI, false ) );
		lcTipoCli.montaSql( false, "TIPOCLI", "VD" );
		lcTipoCli.setReadOnly( true );
		txtCodTipoCli.setTabelaExterna( lcTipoCli );
		txtCodTipoCli.setFK( true );
		txtCodTipoCli.setNomeCampo( "CodTipoCli" );

		lcClasCli.add( new GuardaCampo( txtCodClasCli, "CodClasCli", "Cód.cl.cli.", ListaCampos.DB_PK, false ) );
		lcClasCli.add( new GuardaCampo( txtDescClasCli, "DescClasCli", "Descrição da classificação do cliente", ListaCampos.DB_SI, false ) );
		lcClasCli.montaSql( false, "CLASCLI", "VD" );
		lcClasCli.setReadOnly( true );
		txtCodClasCli.setTabelaExterna( lcClasCli );
		txtCodClasCli.setFK( true );
		txtCodClasCli.setNomeCampo( "CodClasCli" );

		lcVendedor.add( new GuardaCampo( txtCodVend, "CodVend", "Cód.comiss.", ListaCampos.DB_PK, false ) );
		lcVendedor.add( new GuardaCampo( txtNomeVend, "NomeVend", "Nome do comissionado", ListaCampos.DB_SI, false ) );
		lcVendedor.montaSql( false, "VENDEDOR", "VD" );
		lcVendedor.setReadOnly( true );
		txtCodVend.setTabelaExterna( lcVendedor );
		txtCodVend.setFK( true );
		txtCodVend.setNomeCampo( "CodVend" );

		JLabelPad lbOrdem = new JLabelPad( "   Ordenar por:" );
		lbOrdem.setOpaque( true );
		JLabelPad lbBordaOrdem = new JLabelPad();
		lbBordaOrdem.setBorder( BorderFactory.createEtchedBorder() );
		adic( lbOrdem, 15, 5, 100, 20 );
		adic( lbBordaOrdem, 7, 15, 433, 60 );
		adic( rgOrdem, 15, 30, 240, 30 );

		adic( cbObs, 270, 16, 165, 20 );
		adic( cbAtiv, 270, 34, 160, 20 );
		adic( cbInativ, 270, 52, 160, 20 );
		

		JLabelPad lbSelecao = new JLabelPad( "   Seleção :" );
		lbSelecao.setOpaque( true );
		JLabelPad lbBordaSelecao = new JLabelPad();
		lbBordaSelecao.setBorder( BorderFactory.createEtchedBorder() );
		adic( lbSelecao, 15, 75, 85, 20 );
		adic( lbBordaSelecao, 7, 85, 259, 70 );
		adic( new JLabelPad( "De:", SwingConstants.RIGHT ), 12, 100, 30, 20 );
		adic( txtDe, 47, 100, 200, 20 );
		adic( new JLabelPad( "À:", SwingConstants.RIGHT ), 12, 125, 30, 20 );
		adic( txtA, 47, 125, 200, 20 );

		adic( new JLabelPad( "UF" ), 277,75, 30, 20 );
		adic( txtEstCli, 277, 95, 30, 20 );
		
		adic( new JLabelPad( "Cidade" ), 310, 75, 130, 20 );
		adic( txtCid, 310, 95, 130, 20 );
		adic( new JLabelPad( "Bairro" ), 277, 115, 140, 20 );
		adic( txtBairro, 277, 135, 163, 20 );
		
		JLabelPad lbEnd = new JLabelPad( "Endereço :" );
		lbSelecao.setOpaque( true );
		adic( lbEnd, 7, 155, 85, 20 );
		adic( rgEnd, 7, 175, 259, 30 );

		JLabelPad lbBordaPessoa = new JLabelPad();
		lbBordaPessoa.setBorder( BorderFactory.createEtchedBorder() );		
		adic( new JLabelPad( "Pessoa :" ), 270, 155, 75, 20 );
		adic( lbBordaPessoa, 270, 175, 170, 30 );
		adic( cbFis, 287, 180, 70, 20 );
		adic( cbJur, 357, 180, 80, 20 );

		adic( new JLabelPad( "Modo do relatório:" ), 7, 205, 170, 20 );
		adic( rgModo, 7, 225, 433, 50 );

		adic( new JLabelPad( "Cód.setor" ), 7, 280, 80, 20 );
		adic( txtCodSetor, 7, 300, 80, 20 );
		adic( new JLabelPad( "Descrição do setor" ), 90, 280, 350, 20 );
		adic( txtDescSetor, 90, 300, 350, 20 );
		adic( new JLabelPad( "Cód.comiss." ), 7, 320, 80, 20 );
		adic( txtCodVend, 7, 340, 80, 20 );
		adic( new JLabelPad( "Nome do comissionado" ), 90, 320, 350, 20 );
		adic( txtNomeVend, 90, 340, 350, 20 );
		adic( new JLabelPad( "Cód.tp.cli." ), 7, 360, 80, 20 );
		adic( txtCodTipoCli, 7, 380, 80, 20 );
		adic( new JLabelPad( "Descrição do tipo de cliente" ), 90, 360, 350, 20 );
		adic( txtDescTipoCli, 90, 380, 350, 20 );
		adic( new JLabelPad( "Cód.cl.cli." ), 7, 400, 80, 20 );
		adic( txtCodClasCli, 7, 420, 80, 20 );
		adic( new JLabelPad( "Descrição da classificação do cliente" ), 90, 400, 350, 20 );
		adic( txtDescClasCli, 90, 420, 350, 20 );

		lcSetor.setConexao( cn );
		lcTipoCli.setConexao( cn );
		lcClasCli.setConexao( cn );
		lcVendedor.setConexao( cn );

	}

	public String[] getValores() {

		String[] sRetorno = new String[ 22 ];

		if ( rgOrdem.getVlrString().equals( "C" ) ) {
			sRetorno[ 0 ] = "C1.CODCLI";
		}
		else if ( rgOrdem.getVlrString().equals( "R" ) ) {
			sRetorno[ 0 ] = "C1.RAZCLI";
		}
		else if ( rgOrdem.getVlrString().equals( "I" ) ) {
			if( "A".equals( rgEnd.getVlrString() ) ) {
				sRetorno[ 0 ] = "C1.CIDCLI, C1.RAZCLI";
			}
			else if( "E".equals( rgEnd.getVlrString() ) ) {
				sRetorno[ 0 ] = "C1.CIDENT, C1.RAZCLI";
			}
			else if( "C".equals( rgEnd.getVlrString() ) ) {
				sRetorno[ 0 ] = "C1.CIDCOB, C1.RAZCLI";
			}
		}
		sRetorno[ 1 ] = cbObs.getVlrString();
		sRetorno[ 2 ] = txtDe.getVlrString();
		sRetorno[ 3 ] = txtA.getVlrString();
		sRetorno[ 4 ] = cbFis.getVlrString();
		sRetorno[ 5 ] = txtCid.getVlrString();
		sRetorno[ 6 ] = cbJur.getVlrString();
		sRetorno[ 7 ] = rgModo.getVlrString();
		sRetorno[ 8 ] = txtCodSetor.getVlrString();
		sRetorno[ 9 ] = txtDescSetor.getVlrString();
		sRetorno[ 10 ] = txtCodTipoCli.getVlrString();
		sRetorno[ 11 ] = txtDescTipoCli.getVlrString();
		sRetorno[ 12 ] = rgOrdem.getVlrString();
		sRetorno[ 13 ] = txtCodVend.getVlrString();
		sRetorno[ 14 ] = txtNomeVend.getVlrString();
		sRetorno[ 15 ] = txtCodClasCli.getVlrString();
		sRetorno[ 16 ] = txtDescClasCli.getVlrString();
		sRetorno[ 17 ] = rgEnd.getVlrString();
		sRetorno[ 18 ] = txtBairro.getVlrString().trim();
		sRetorno[ 19 ] = txtEstCli.getVlrString().trim();
		sRetorno[ 20 ] = cbAtiv.getVlrString().trim();
		sRetorno[ 21 ] = cbInativ.getVlrString().trim();

		return sRetorno;

	}
}
