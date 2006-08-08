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
import java.sql.Connection;
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

	private JTextFieldPad txtDe = new JTextFieldPad( JTextFieldPad.TP_STRING, 30, 0 );

	private JTextFieldPad txtA = new JTextFieldPad( JTextFieldPad.TP_STRING, 30, 0 );

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

	private JRadioGroup rgOrdem = null;

	private JRadioGroup rgModo = null;

	private JRadioGroup rgEnd = null;

	private ListaCampos lcSetor = new ListaCampos( this );

	private ListaCampos lcTipoCli = new ListaCampos( this );

	private ListaCampos lcClasCli = new ListaCampos( this );

	private ListaCampos lcVendedor = new ListaCampos( this );

	private Vector vLabsOrdem = new Vector();

	private Vector vValsOrdem = new Vector();

	private Vector vLabsModo = new Vector();

	private Vector vValsModo = new Vector();

	private Vector vLabsEnd = new Vector();

	private Vector vValsEnd = new Vector();

	public DLRCliente( Component cOrig, Connection cn ) {

		super( cOrig );
		setTitulo( "Relatório de Clientes" );
		setAtribos( 465, 520 );
		setLocationRelativeTo( this );

		vLabsOrdem.addElement( "Código" );
		vLabsOrdem.addElement( "Razão" );
		vLabsOrdem.addElement( "Cidade" );
		vValsOrdem.addElement( "C" );
		vValsOrdem.addElement( "R" );
		vValsOrdem.addElement( "I" );
		rgOrdem = new JRadioGroup( 1, 3, vLabsOrdem, vValsOrdem );
		rgOrdem.setVlrString( "R" );

		vLabsModo.addElement( "Resumido 1" );
		vLabsModo.addElement( "Resumido 2" );
		vLabsModo.addElement( "Completo" );
		vLabsModo.addElement( "Alinhar  Filial" );
		vValsModo.addElement( "R" );
		vValsModo.addElement( "J" );
		vValsModo.addElement( "C" );
		vValsModo.addElement( "A" );

		rgModo = new JRadioGroup( 1, 4, vLabsModo, vValsModo );
		rgModo.setVlrString( "R" );
		
		vLabsEnd.addElement( "Cadastro" );
		vLabsEnd.addElement( "Entrega" );
		vLabsEnd.addElement( "Cobrança" );
		vValsEnd.addElement( "A" );
		vValsEnd.addElement( "E" );
		vValsEnd.addElement( "C" );
		rgEnd = new JRadioGroup( 1, 3, vLabsEnd, vValsEnd );
		rgEnd.setVlrString( "A" );

		cbObs.setVlrString( "N" );
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
		adic( cbObs, 270, 35, 190, 20 );

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

		JLabelPad lbPessoa = new JLabelPad( "   Pessoa :" );
		lbPessoa.setOpaque( true );
		JLabelPad lbBordaPessoa = new JLabelPad();
		lbBordaPessoa.setBorder( BorderFactory.createEtchedBorder() );		
		adic( lbPessoa, 277, 75, 75, 20 );
		adic( lbBordaPessoa, 270, 85, 170, 70 );
		adic( cbFis, 290, 100, 80, 20 );
		adic( cbJur, 290, 125, 80, 20 );
		
		JLabelPad lbEnd = new JLabelPad( "Endereço :" );
		lbSelecao.setOpaque( true );
		adic( lbEnd, 7, 155, 85, 20 );
		adic( rgEnd, 7, 175, 289, 30 );

		adic( new JLabelPad( "Cidade" ), 300, 165, 140, 20 );
		adic( txtCid, 300, 185, 140, 20 );

		adic( new JLabelPad( "Modo do relatório:" ), 7, 205, 170, 20 );
		adic( rgModo, 7, 225, 433, 30 );

		adic( new JLabelPad( "Cód.setor" ), 7, 260, 80, 20 );
		adic( txtCodSetor, 7, 280, 80, 20 );
		adic( new JLabelPad( "Descrição do setor" ), 90, 260, 350, 20 );
		adic( txtDescSetor, 90, 280, 350, 20 );
		adic( new JLabelPad( "Cód.comiss." ), 7, 300, 80, 20 );
		adic( txtCodVend, 7, 320, 80, 20 );
		adic( new JLabelPad( "Nome do comissionado" ), 90, 300, 350, 20 );
		adic( txtNomeVend, 90, 320, 350, 20 );
		adic( new JLabelPad( "Cód.tp.cli." ), 7, 340, 80, 20 );
		adic( txtCodTipoCli, 7, 360, 80, 20 );
		adic( new JLabelPad( "Descrição do tipo de cliente" ), 90, 340, 350, 20 );
		adic( txtDescTipoCli, 90, 360, 350, 20 );
		adic( new JLabelPad( "Cód.cl.cli." ), 7, 380, 80, 20 );
		adic( txtCodClasCli, 7, 400, 80, 20 );
		adic( new JLabelPad( "Descrição da classificação do cliente" ), 90, 380, 350, 20 );
		adic( txtDescClasCli, 90, 400, 350, 20 );

		lcSetor.setConexao( cn );
		lcTipoCli.setConexao( cn );
		lcClasCli.setConexao( cn );
		lcVendedor.setConexao( cn );

	}

	public String[] getValores() {

		String[] sRetorno = new String[ 18 ];

		if ( rgOrdem.getVlrString().equals( "C" ) ) {
			sRetorno[ 0 ] = "C1.CODCLI";
		}
		else if ( rgOrdem.getVlrString().equals( "R" ) ) {
			sRetorno[ 0 ] = "C1.RAZCLI";
		}
		else if ( rgOrdem.getVlrString().equals( "I" ) ) {
			sRetorno[ 0 ] = "C1.CIDCLI, C1.RAZCLI";
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

		return sRetorno;

	}
}
