/**
 * @version 03/09/2007 <BR>
 * @author Setpoint Informática Ltda./ Alex Rodrigues <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)DLEtiqCompra.java <BR>
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

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.telas.FFDialogo;

public class DLEtiqCompra extends FFDialogo {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtCodCompra = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDataCompra = new JTextFieldFK( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldFK txtValorCompra = new JTextFieldFK( JTextFieldPad.TP_NUMERIC, 15, 3 );

	private JTextFieldFK txtStatusCompra = new JTextFieldFK( JTextFieldPad.TP_STRING, 2, 0 );
	
	private JTextFieldPad txtCodFor = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtRazFor = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	private JTextFieldPad txtCodPlanoPag = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescPlanoPag = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private ListaCampos lcCompra = new ListaCampos( this );

	private ListaCampos lcPlanoPag = new ListaCampos( this, "PG" );

	private ListaCampos lcFornecedor = new ListaCampos( this, "FO" );
	

	public DLEtiqCompra( Component cOrig ) {

		super( cOrig );
		setTitulo( "Selecionar Compra" );
		setAtribos( 430, 200 );
		
		montaListaCampos();
		
		montaTela();
	}
	
	private void montaListaCampos() {
		
		/**************
		 *   COMPRA   *
		 **************/
		lcCompra.add( new GuardaCampo( txtCodCompra, "CodCompra", "N.pedido", ListaCampos.DB_PK, false ) );
		lcCompra.add( new GuardaCampo( txtDataCompra, "DtEmitCompra", "Dt.emissão", ListaCampos.DB_SI, false ) );
		lcCompra.add( new GuardaCampo( txtValorCompra, "VlrLiqCompra", "V.compra", ListaCampos.DB_SI, false ) );
		lcCompra.add( new GuardaCampo( txtStatusCompra, "StatusCompra", "Status", ListaCampos.DB_SI, false ) );
		lcCompra.add( new GuardaCampo( txtCodFor, "CodFor", "Cód.for.", ListaCampos.DB_FK, txtRazFor, false ) );
		lcCompra.add( new GuardaCampo( txtCodPlanoPag, "CodPlanoPag", "Cód.p.pg.", ListaCampos.DB_FK, txtDescPlanoPag, false ) );
		lcCompra.montaSql( false, "COMPRA", "CP" );
		lcCompra.setReadOnly( true );
		txtCodCompra.setFK( true );
		txtCodCompra.setNomeCampo( "CodCompra" );
		txtCodCompra.setTabelaExterna( lcCompra );
		txtDataCompra.setListaCampos( lcCompra );
		txtValorCompra.setListaCampos( lcCompra );
		txtStatusCompra.setListaCampos( lcCompra );
		
		/**************************
		 *   PLANO DE PAGAMENTO   *
		 **************************/
		lcPlanoPag.add( new GuardaCampo( txtCodPlanoPag, "CodPlanoPag", "Cód.p.pg.", ListaCampos.DB_PK, false ) );
		lcPlanoPag.add( new GuardaCampo( txtDescPlanoPag, "DescPlanoPag", "Descrição do plano de pagamento", ListaCampos.DB_SI, false ) );
		lcPlanoPag.montaSql( false, "PLANOPAG", "FN" );
		lcPlanoPag.setQueryCommit( false );
		lcPlanoPag.setReadOnly( true );
		txtCodPlanoPag.setFK( true );
		txtCodPlanoPag.setNomeCampo( "CodPlanoPag" );
		txtCodPlanoPag.setTabelaExterna( lcPlanoPag );
		txtDescPlanoPag.setListaCampos( lcPlanoPag );
		
		/******************
		 *   FORNECEDOR   *
		 ******************/
		lcFornecedor.add( new GuardaCampo( txtCodFor, "CodFor", "Cód.for.", ListaCampos.DB_PK, false ) );
		lcFornecedor.add( new GuardaCampo( txtRazFor, "RazFor", "Razão social do fornecedor", ListaCampos.DB_SI, false ) );
		lcFornecedor.montaSql( false, "FORNECED", "CP" );
		lcFornecedor.setQueryCommit( false );
		lcFornecedor.setReadOnly( true );
		txtCodFor.setFK( true );
		txtCodFor.setNomeCampo( "CodFor" );
		txtCodFor.setTabelaExterna( lcFornecedor );
		txtRazFor.setListaCampos( lcFornecedor );
	}
	
	private void montaTela() {
		

		adic( new JLabelPad( "Cód.compra" ), 7, 10, 100, 20 );
		adic( txtCodCompra, 7, 30, 100, 20 );
		adic( new JLabelPad( "Data emissão" ), 110, 10, 97, 20 );
		adic( txtDataCompra, 110, 30, 97, 20 );
		adic( new JLabelPad( "Valor" ), 210, 10, 150, 20 );
		adic( txtValorCompra, 210, 30, 150, 20 );
		adic( new JLabelPad( "Status" ), 363, 10, 47, 20 );
		adic( txtStatusCompra, 363, 30, 47, 20 );
		
		adic( new JLabelPad( "Fornecedor" ), 7, 50, 200, 20 );
		adic( txtRazFor, 7, 70, 200, 20 );
		adic( new JLabelPad( "Plano de pagamento" ), 210, 50, 200, 20 );
		adic( txtDescPlanoPag, 210, 70, 200, 20 );
	}
	
	public Integer getCompra() {
		return txtCodCompra.getVlrInteger();
	}
	
	public void setConexao( Connection cn ) {
		
		lcCompra.setConexao( cn );
		lcPlanoPag.setConexao( cn );
		lcFornecedor.setConexao( cn );
	}
}
