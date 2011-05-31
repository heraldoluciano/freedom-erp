/**
 * @version 30/05/2011 <BR>
 * @author Setpoint Informática Ltda./Filipe Chagas <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.crm <BR>
 *         Classe: @(#)FVendaContrato.java <BR>
 * 
 *         Este programa é licenciado de acordo com a GPL (Licença Pública Geral para Programas de Computador), <BR>
 *         versão 2.0. <BR>
 *         A GPL deve acompanhar todas PUBLICAÇÕES, DISTRIBUIÇÕES e REPRODUÇÕES deste Programa. <BR>
 *         Caso uma cópia da GPL não esteja disponível junto com este Programa, você pode contatar <BR>
 *         sem uma garantia implicita de ADEQUAÇÂO a qualquer MERCADO ou APLICAÇÃO EM PARTICULAR. <BR>
 *         Veja a Licença Pública Geral GNU para maiores detalhes. <BR>
 *         Você deve ter recebido uma cópia da Licença Pública Geral GNU junto com este programa, se não, <BR>
 *         de acordo com os termos da GPL <BR>
 * <BR>
 * 
 * 
 *         Tela de Relacionamento entre Vendas e Contratos de Clientes
 * 
 */

package org.freedom.modulos.crm.view.frame.crud.plain;

import java.util.Date;

import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FDados;
import org.freedom.modulos.crm.view.frame.crud.detail.FContrato;
import org.freedom.modulos.pdv.FVenda;

public class FVendaContrato extends FDados {

	private static final long serialVersionUID = 1L;

	private static final int CASAS_DEC = Aplicativo.casasDec;

	private static final int CASAS_DEC_PRE = Aplicativo.casasDecPre;

	private JTextFieldPad txtVdCodigo = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtVdTipo = new JTextFieldPad( JTextFieldPad.TP_STRING, 1, 0 );

	private JTextFieldPad txtVdCodItem = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtVdSerie = new JTextFieldFK( JTextFieldPad.TP_STRING, 4, 0 );

	private JTextFieldFK txtVdDoc = new JTextFieldFK( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtVdEmissao = new JTextFieldFK( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldFK txtVdQtd = new JTextFieldFK( JTextFieldPad.TP_DECIMAL, 15, CASAS_DEC );

	private JTextFieldFK txtVdPrecoItem = new JTextFieldFK( JTextFieldPad.TP_DECIMAL, 15, CASAS_DEC_PRE );

	private JTextFieldFK txtVdVlrLiqItem = new JTextFieldFK( JTextFieldPad.TP_DECIMAL, 15, CASAS_DEC_PRE );

	private JTextFieldPad txtCtCodigo = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 30, 0 );

	private JTextFieldPad txtCtCodItem = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtCtDescricao = new JTextFieldFK( JTextFieldPad.TP_STRING, 80, 0 );

	private JTextFieldFK txtCtDescItem = new JTextFieldFK( JTextFieldPad.TP_STRING, 80, 0 );

	private final JTextFieldPad txtDtIniApuracao = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private final JTextFieldPad txtDtFimApuracao = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private ListaCampos lcVenda = new ListaCampos( this, "VD" );

	private ListaCampos lcItVenda = new ListaCampos( this, "IV" );

	private ListaCampos lcContrato = new ListaCampos( this, "CT" );

	private ListaCampos lcItContrato = new ListaCampos( this, "IC" );

	private ListaCampos lcVendaContrato = new ListaCampos( this, "VC" );

	public FVendaContrato() {
		super();
		setTitulo( "Cadastro de Atendentes" );
		setAtribos( 50, 20, 400, 350 );
		
		lcVenda.add( new GuardaCampo( txtVdCodigo, "CodVenda", "Cód.Venda", ListaCampos.DB_PK, true ) );
		lcVenda.add( new GuardaCampo( txtVdTipo, "TipoVenda", "Tp.venda", ListaCampos.DB_SI, false ) );
		lcVenda.add( new GuardaCampo( txtVdSerie, "Serie", "Série", ListaCampos.DB_SI, false ) );
		lcVenda.add( new GuardaCampo( txtVdDoc, "DocVenda", "Nota", ListaCampos.DB_SI, false ) );
		lcVenda.add( new GuardaCampo( txtVdEmissao, "DtEmitVenda", "Emissão", ListaCampos.DB_SI, false ) );
		txtVdCodigo.setPK( true );
		lcVenda.montaSql( false, "VENDA", "VD" );
		lcVenda.setQueryCommit( false );
		lcVenda.setReadOnly( true );
		txtVdCodigo.setTabelaExterna( lcVenda, FVenda.class.getCanonicalName() );

		lcItVenda.add( new GuardaCampo( txtVdCodItem, "CodItVenda", "Cód.It.Venda", ListaCampos.DB_PK, true ) );
		lcItVenda.add( new GuardaCampo( txtVdCodigo, "CodVenda", "Cód.Venda", ListaCampos.DB_PK, true ) );
		lcItVenda.add( new GuardaCampo( txtVdTipo, "TipoVenda", "Tp.venda", ListaCampos.DB_SI, false ) );
		lcItVenda.add( new GuardaCampo( txtVdQtd, "QtdItVenda", "Qtd.It.Venda", ListaCampos.DB_SI, false ) );
		lcItVenda.add( new GuardaCampo( txtVdPrecoItem, "PrecoItVenda", "Preço.Item", ListaCampos.DB_SI, false ) );
		lcItVenda.add( new GuardaCampo( txtVdVlrLiqItem, "VlrLiqItVenda", "V.Liq.Item", ListaCampos.DB_SI, false ) );
		txtVdCodItem.setPK( true );
		lcItVenda.montaSql( false, "ITVENDA", "VD" );
		lcItVenda.setQueryCommit( false );
		lcItVenda.setReadOnly( true );
		txtVdCodItem.setTabelaExterna( lcItVenda, FVenda.class.getCanonicalName() );

		lcContrato.add( new GuardaCampo( txtCtCodigo, "CodContr", "Cód.Contrato", ListaCampos.DB_PK, true ) );
		lcContrato.add( new GuardaCampo( txtCtDescricao, "DescContr", "Desc.Contr.", ListaCampos.DB_SI, false ) );
		lcContrato.montaSql( false, "CONTRATO", "VD" );
		lcContrato.setQueryCommit( false );
		lcContrato.setReadOnly( true );
		txtCtCodigo.setTabelaExterna( lcContrato, FContrato.class.getCanonicalName() );

		lcItContrato.add( new GuardaCampo( txtCtCodItem, "CodItContr", "Cód.It.Contr.", ListaCampos.DB_PK, true ) );
		lcItContrato.add( new GuardaCampo( txtCtCodigo, "CodContr", "Cód.Contrato", ListaCampos.DB_PK, true ) );
		lcItContrato.add( new GuardaCampo( txtCtDescItem, "DescItContr", "Desc.It.Contr.", ListaCampos.DB_SI, false ) );
		lcItContrato.montaSql( false, "ITCONTRATO", "VD" );
		lcItContrato.setQueryCommit( false );
		lcItContrato.setReadOnly( true );
		txtCtCodItem.setTabelaExterna( lcItContrato, FContrato.class.getCanonicalName() );
	}
	
	private void montaTela() {

		adicCampo( txtVdCodigo, 7, 20, 80, 20, "CodVenda", "Cód.Venda", ListaCampos.DB_PK, true );
		adicCampo( txtVdCodItem, 90, 20, 67, 20, "CodItVenda", "Cód.It.Venda", ListaCampos.DB_PK, true );
		adicDescFK( txtVdDoc, 160, 20, 67, 20, "DocVenda", "Nota" );
		adicDescFK( txtVdSerie, 230, 20, 67, 20, "Serie", "Série" );

		adicDescFK( txtVdEmissao, 7, 60, 80, 20, "DtEmitVenda", "Emissão" );
		adicDescFK( txtVdQtd, 90, 60, 67, 20, "QtdItVenda", "Qtd.It.Venda" );
		adicDescFK( txtVdPrecoItem, 160, 60, 67, 20, "PrecoItVenda", "Preçoo.Item" );

		adicDescFK( txtVdVlrLiqItem, 7, 100, 80, 20, "VlrLiqItVenda", "V.Liq.Item" );

		adicCampo( txtCtCodigo, 7, 140, 80, 20, "CodContr", "Cód.Contrato", ListaCampos.DB_FK, true );
		adicCampo( txtCtCodItem, 90, 140, 67, 20, "CodItContr", "Cód.It.Contr.", ListaCampos.DB_SI, true );
		adicDescFK( txtCtDescricao, 160, 140, 200, 20, "DescContr", "Desc.Contr." );
		adicDescFK( txtCtDescItem, 7, 180, 200, 20, "DescItContr", "Desc.It.Contr." );

		adicCampo( txtDtIniApuracao, 7, 220, 80, 20, "DtIniApura", "Dt.Ini.Apuração", ListaCampos.DB_SI, true );
		adicCampo( txtDtFimApuracao, 90, 220, 80, 20, "DtFinApura", "Dt.Fim.Apuração", ListaCampos.DB_SI, true );

		txtDtIniApuracao.setVlrDate( new Date() );
		txtDtFimApuracao.setVlrDate( new Date() );

		setListaCampos( true, "ITVENDAVDITCONTR", "VD" );
		lcCampos.setQueryInsert( false );
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );

		montaTela();

		lcVenda.setConexao( cn );
		lcContrato.setConexao( cn );
		lcItVenda.setConexao( cn );
		lcItContrato.setConexao( cn );
	}
}

