/*
 * 
 * Projeto: Freedom Pacote: org.freedom.modules.pcp Classe: @(#)FManutPrevEstoque.java
 * 
 * Este arquivo é parte do sistema Freedom-ERP, o Freedom-ERP é um software livre; você pode redistribui-lo e/ou <BR> modifica-lo dentro dos termos da Licença Pública Geral GNU como publicada pela Fundação do Software Livre (FSF); <BR> na versão 2 da Licença, ou (na sua opnião) qualquer versão. <BR>
 * Este programa é distribuido na esperança que possa ser util, mas SEM NENHUMA GARANTIA; <BR> sem uma garantia implicita de ADEQUAÇÂO a qualquer MERCADO ou APLICAÇÃO EM PARTICULAR. <BR> Veja a Licença Pública Geral GNU para maiores detalhes. <BR> Você deve ter recebido uma cópia da Licença Pública
 * Geral GNU junto com este programa, se não, <BR> escreva para a Fundação do Software Livre(FSF) Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA <BR>
 */

package org.freedom.modulos.pcp.view.frame.utility;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.bmps.Icone;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.business.exceptions.ExceptionCarregaDados;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JButtonPad;
import org.freedom.library.swing.component.JCheckBoxPad;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JPanelPad;
import org.freedom.library.swing.component.JTabbedPanePad;
import org.freedom.library.swing.component.JTablePad;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FFilho;
import org.freedom.modulos.gms.business.object.TipoProd;
import org.freedom.modulos.gms.view.frame.crud.tabbed.FProduto;
import org.freedom.modulos.pcp.dao.DAOPrevEstoq;

/**
 * Tela para Manutenção da previsão de estoque.
 * 
 * @author Setpoint Informática Ltda./Bruno Nascimento
 * @version 17/05/2013
 */

public class FManutPrevEstoque extends FFilho implements ActionListener, KeyListener, CarregaListener {
	
	private static enum TAB_PROD {
		MARCACAO, CODPROD, REFPROD, DESCPROD, PRECOBASEPROD, MEDIAVENDAS, PRAZOREPO, SLDPROD, QTDMINPROD, QTDMAXPROD, SUGQTDMINPROD, SUGQTDMAXPROD, SUGPRAZOREPO
		};

	// *** Variáveis estáticas

	private static final long serialVersionUID = 1L;

	//private static final Color GREEN = new Color(45, 190, 60);

	// *** Paineis tela

	private JPanelPad panelGeral = new JPanelPad(JPanelPad.TP_JPANEL, new BorderLayout());

	private JPanelPad panelMaster = new JPanelPad(700, 250);
	
	private JPanelPad panelRod = new JPanelPad(600, 30);
	
	private JPanelPad panelSouth = new JPanelPad(30, 30);

	private JPanelPad panelAbas = new JPanelPad(JPanelPad.TP_JPANEL, new GridLayout(1, 1));

	private JTabbedPanePad tabbedAbas = new JTabbedPanePad();

	
		//	private JPanelPad panelFiltros = new JPanelPad( "Filtros", Color.BLUE );

	// *** Paineis Detalhamento

	private JPanelPad panelDet = new JPanelPad(JPanelPad.TP_JPANEL, new BorderLayout());

	private JPanelPad panelTabDet = new JPanelPad(700, 60);

	private JPanelPad panelGridDet = new JPanelPad(JPanelPad.TP_JPANEL, new GridLayout(1, 1));

	private JPanelPad panelTabDetItens = new JPanelPad(JPanelPad.TP_JPANEL, new GridLayout(1, 1));
		
	private JTablePad tabDet = null;

	// *** Labels

	private JLabelPad sepdet = new JLabelPad();

	// *** Geral

	private JButtonPad btBuscar = new JButtonPad("Buscar", Icone.novo("btExecuta.png" ) );

	// *** Campos
	private JTextFieldPad txtDtIni = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDtFim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtCodGrupo = new JTextFieldPad( JTextFieldPad.TP_STRING, 13, 0 );

	private JTextFieldFK txtDescGrupo = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtCodProd = new JTextFieldPad( JTextFieldPad.TP_STRING, 8, 0 );

	private JTextFieldFK txtDescProd = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	// ** Checkbox

	private JCheckBoxPad cbMercadoriaRevenda = new JCheckBoxPad( TipoProd.MERCADORIA_REVENDA.getName(), "S", "N" );
	private JCheckBoxPad cbMateriaPrima = new JCheckBoxPad( TipoProd.MATERIA_PRIMA.getName(), "S", "N" );
	private JCheckBoxPad cbEmProcesso = new JCheckBoxPad( TipoProd.EM_PROCESSO.getName(), "S", "N" );
	private JCheckBoxPad cbOutros = new JCheckBoxPad( TipoProd.OUTRAS.getName(), "S", "N" );
	private JCheckBoxPad cbServicos = new JCheckBoxPad( TipoProd.SERVICO.getName(), "S", "N" );
	private JCheckBoxPad cbAtivoImobilizado = new JCheckBoxPad( TipoProd.ATIVO_IMOBILIZADO.getName(), "S", "N" );
	private JCheckBoxPad cbSubProd = new JCheckBoxPad( TipoProd.SUB_PRODUTO.getName(), "S", "N" );
	private JCheckBoxPad cbEquipamento = new JCheckBoxPad( TipoProd.EQUIPAMENTO.getName(), "S", "N" );
	private JCheckBoxPad cbMaterialConsumo = new JCheckBoxPad( TipoProd.MATERIAL_CONSUMO.getName(), "S", "N" );
	private JCheckBoxPad cbProdutoIntermediario = new JCheckBoxPad( TipoProd.PRODUTO_INTERMEDIARIO.getName(), "S", "N" );
	private JCheckBoxPad cbProdutoAcabado = new JCheckBoxPad( TipoProd.PRODUTO_ACABADO.getName(), "S", "N" );
	private JCheckBoxPad cbEmbalagem = new JCheckBoxPad( TipoProd.EMBALAGEM.getName(), "S", "N" );
	private JCheckBoxPad cbOutrosInsumos = new JCheckBoxPad( TipoProd.OUTROS_INSUMOS.getName(), "S", "N" );

	// ** Legenda


	// *** Listacampos

	private ListaCampos lcCliente = new ListaCampos(this, "CL");

	private ListaCampos lcProd = new ListaCampos(this);

	private ListaCampos lcProd2 = new ListaCampos(this);

	private ListaCampos lcGrupo = new ListaCampos(this);

	// *** Botões

	private JButtonPad btSelectAllDet = new JButtonPad(Icone.novo("btTudo.png"));

	private JButtonPad btDeselectAllDet = new JButtonPad(Icone.novo("btNada.png"));

	private JButtonPad btLimparGridDet = new JButtonPad( Icone.novo("btVassoura.png"));

	private JButtonPad btSimulaAgrupamentoDet = new JButtonPad(Icone.novo("btVassoura.png"));

	private JButtonPad btIniProdDet = new JButtonPad( Icone.novo("btIniProd.png"));

	private JButtonPad btSelectAllAgrup = new JButtonPad(Icone.novo("btTudo.png"));

	private JButtonPad btDeselectAllAgrup = new JButtonPad(Icone.novo("btNada.png"));

	private JButtonPad btLimparGridAgrup = new JButtonPad(Icone.novo("btVassoura.png"));

	private JButtonPad btSimulaAgrupamentoAgrup = new JButtonPad(Icone.novo("btVassoura.png"));

	private JTextFieldPad txtRefProd = new JTextFieldPad(JTextFieldPad.TP_STRING, 20, 0);
	
	private boolean usaRef = false;
	
	// DAO
	private DAOPrevEstoq daoprev;
	

	// Enums
	private enum DETALHAMENTO {
		MARCACAO, STATUS, CODEMPPD, CODFILIALPD, CODPROD, REFPROD, SEQEST, DESCPROD, QTDMINPROD, QTDESTOQUE, QTDREQ, QTDEMPROD, DTFABROP, QTDAPROD
	}

	public FManutPrevEstoque() {

		super( false );

		setTitulo( "Manutenção de previsão de estoque.", this.getClass().getName() );
		setAtribos( 20, 20, 860, 600 );

		int x = (int) ( Aplicativo.telaPrincipal.dpArea.getSize().getWidth() - getWidth() ) / 2;
		int y = (int) ( Aplicativo.telaPrincipal.dpArea.getSize().getHeight() - getHeight() ) / 2;

		setLocation( x, y );

		//Dao será instanciada em outro lugar posteriormente
		daoprev = new DAOPrevEstoq( con );
		
		montarListaCampos();
		montarTabela();
		montarTela();
		montarListeners();
		carregarValoresPadrao();
		inserirPeriodo();
	
	}

	private void carregarValoresPadrao() {

		cbMercadoriaRevenda.setVlrString( "S" );
		cbMateriaPrima.setVlrString( "S" );
		cbEmProcesso.setVlrString( "S" );
		cbOutros.setVlrString( "S" );
		cbServicos.setVlrString( "S" );
		cbAtivoImobilizado.setVlrString( "S" );
		cbSubProd.setVlrString( "S" );
		cbEquipamento.setVlrString( "S" );
		cbMaterialConsumo.setVlrString( "S" );
		cbProdutoIntermediario.setVlrString( "S" );
		cbProdutoAcabado.setVlrString( "S" );
		cbEmbalagem.setVlrString( "S" );
		cbOutrosInsumos.setVlrString( "S" );

	}

	private void montarListaCampos() {

		lcProd.add( new GuardaCampo( txtCodProd, "CodProd", "Cód.prod.", ListaCampos.DB_PK, false ) );
		lcProd.add( new GuardaCampo( txtDescProd, "DescProd", "Descrição do produto", ListaCampos.DB_SI, false ) );
		lcProd.setWhereAdic( "ATIVOPROD='S' AND TIPOPROD IN ('" + TipoProd.PRODUTO_ACABADO.getValue() + "','" + TipoProd.PRODUTO_INTERMEDIARIO.getValue() + "')" );
		txtCodProd.setTabelaExterna( lcProd, null );
		txtCodProd.setNomeCampo( "CodProd" );
		txtCodProd.setFK( true );
		lcProd.setReadOnly( true );
		lcProd.montaSql( false, "PRODUTO", "EQ" );

		lcProd2.add( new GuardaCampo( txtRefProd, "RefProd", "Referência", ListaCampos.DB_PK, true ) );
		lcProd2.add( new GuardaCampo( txtDescProd, "DescProd", "Descrição", ListaCampos.DB_SI, false ) );
		lcProd2.add( new GuardaCampo( txtCodProd, "codprod", "Cód.prod.", ListaCampos.DB_SI, false ) );
		txtRefProd.setNomeCampo( "RefProd" );

		lcProd2.setWhereAdic( "ATIVOPROD='S' AND TIPOPROD IN ('" + TipoProd.PRODUTO_ACABADO.getValue() + "','" + TipoProd.PRODUTO_INTERMEDIARIO.getValue() + "')" );
		lcProd2.montaSql( false, "PRODUTO", "EQ" );
		lcProd2.setQueryCommit( false );
		lcProd2.setReadOnly( true );
		txtRefProd.setTabelaExterna( lcProd2, FProduto.class.getCanonicalName() );
		txtRefProd.setFK( true );

		lcGrupo.add( new GuardaCampo( txtCodGrupo, "CodGrup", "Cód.grupo", ListaCampos.DB_PK, false ) );
		lcGrupo.add( new GuardaCampo( txtDescGrupo, "DescGrup", "Descrição do grupo", ListaCampos.DB_SI, false ) );
		txtCodGrupo.setTabelaExterna( lcGrupo, null );
		txtCodGrupo.setNomeCampo( "CodGrup" );
		txtCodGrupo.setFK( true );
		lcGrupo.setReadOnly( true );
		lcGrupo.montaSql( false, "GRUPO", "EQ" );

	}

	private void montarListeners() {
		btBuscar.addActionListener( this );
	}

	private void montarTela() {

		getTela().add( panelGeral, BorderLayout.CENTER );
		panelGeral.add( panelMaster, BorderLayout.NORTH );
	
		// ***** Cabeçalho
		
		JLabel periodo = new JLabel( "Período", SwingConstants.CENTER );
		periodo.setOpaque( true );
		panelMaster.adic( periodo, 7, 0, 100, 20 );
		JLabel borda = new JLabel();
		borda.setBorder( BorderFactory.createEtchedBorder() );
		panelMaster.adic( borda, 7, 5, 300, 55 );
		panelMaster.adic( txtDtIni, 25, 25, 110, 20 );
		panelMaster.adic( new JLabel( "até", SwingConstants.CENTER ), 135, 25, 40, 20 );
		panelMaster.adic( txtDtFim, 175, 25, 110, 20 );

		panelMaster.adic( txtCodGrupo, 7, 80, 120, 20, "Cód.Grupo" );
		panelMaster.adic( txtDescGrupo, 130, 80, 400, 20, "Descrição do grupo" );

		if(comRef()) {
			panelMaster.adic( txtRefProd, 7, 120, 120, 20, "Referência" );
		}
		else {
			panelMaster.adic( txtCodProd, 7, 120, 120, 20, "Cód.Prod." );

		}
		panelMaster.adic( txtDescProd, 130, 120, 400, 20, "Descrição do produto" );

		// Primeira coluna
		panelMaster.adic( cbMercadoriaRevenda, 7, 150, 150, 20, "" );
		panelMaster.adic( cbMateriaPrima, 7, 170, 150, 20, "" );
		panelMaster.adic( cbEmProcesso, 7, 190, 150, 20, "" );
		panelMaster.adic( cbOutros, 7, 210, 150, 20, "" );
		
		//Segunda coluna
		panelMaster.adic( cbServicos, 160, 150, 150, 20, "" );
		panelMaster.adic( cbAtivoImobilizado, 160, 170, 150, 20, "" );
		panelMaster.adic( cbSubProd, 160, 190, 150, 20, "" );
	
		//terceira coluna 
		panelMaster.adic( cbEquipamento, 313, 150, 150, 20, "" );
		panelMaster.adic( cbMaterialConsumo, 313, 170, 150, 20, "" );
		panelMaster.adic( cbProdutoIntermediario, 313, 190, 150, 20, "" );
		
		//Quarta e ultima coluna dos checkbox
		panelMaster.adic( cbProdutoAcabado, 466, 150, 150, 20, "" );
		panelMaster.adic( cbEmbalagem, 466, 170, 150, 20, "" );
		panelMaster.adic( cbOutrosInsumos, 466, 190, 150, 20, "" );
		
		panelMaster.adic( btBuscar, 712, 200, 123, 30 );


		// ***** Abas
		panelGeral.add( panelAbas, BorderLayout.CENTER );
		panelGeral.add( panelAbas );
		panelAbas.add( tabbedAbas );

		tabbedAbas.addTab( "Produto", panelDet );

		// ***** Detalhamento
		panelDet.add(panelGridDet);
		panelGridDet.add( panelTabDetItens );
		
		panelTabDetItens.add( new JScrollPane( tabDet ) );
		
		// ***** RODAPÉ
		panelGeral.add( panelSouth, BorderLayout.SOUTH );
		panelSouth.setBorder( BorderFactory.createEtchedBorder() );
		panelSouth.add( adicBotaoSair() );
		
	}

	private boolean comRef() {

		try{
			usaRef = daoprev.comRef();
		}
		catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro ao carregar a tabela PREFERE1!\n" + err.getMessage(), true, con, err );
		} 
		return usaRef;
	}

	private void inserirPeriodo() {

		Date cData = new Date();
		GregorianCalendar cDataIni = new GregorianCalendar();
		GregorianCalendar cDataFim = new GregorianCalendar();
		cDataIni.set( Calendar.DATE, 1 );
		cDataFim.set( Calendar.MONTH, Funcoes.getMes( new Date() ) );
		cDataFim.set( Calendar.DATE, 0 );
		
		txtDtIni.setVlrDate( cDataIni.getTime() );
		txtDtFim.setVlrDate( cDataFim.getTime() );
	}

	private void montarTabela() {
		tabDet = new JTablePad();

		tabDet.adicColuna( "" ); // Marcação
		tabDet.adicColuna( "Cód.Prod." ); //Código do produto
		tabDet.adicColuna( "Ref.Prod." ); // Referência do produto
		tabDet.adicColuna( "Desc.Prod." ); // Descrição do produto
		tabDet.adicColuna( "Média.VD." ); // Média de vendas
		tabDet.adicColuna( "Prazo Repo." ); // Prazo de reposição
		tabDet.adicColuna( "Preco Base" ); // Preço base do produto
		tabDet.adicColuna( "Saldo Prod." ); //Saldo produto
		tabDet.adicColuna( "Qtd.min." ); // Qtd.Minima
		tabDet.adicColuna( "Qtd.max" ); // Qtd.Maxima 
		tabDet.adicColuna( "Sugestao Qtd.min." ); // Sugestão de quantidade minima
		tabDet.adicColuna( "Sugestao Qtd.max." ); // Sugestão de quantidade maxima
		tabDet.adicColuna( "Sugestao Prazo Repo." ); // Sugestão do prazo de reposição
		
		tabDet.setTamColuna( 17, TAB_PROD.MARCACAO.ordinal() );
		tabDet.setTamColuna( 60, TAB_PROD.CODPROD.ordinal() );
		tabDet.setTamColuna( 70, TAB_PROD.REFPROD.ordinal() );
		tabDet.setTamColuna( 300, TAB_PROD.DESCPROD.ordinal() );
		tabDet.setTamColuna( 80, TAB_PROD.MEDIAVENDAS.ordinal() );
		tabDet.setTamColuna( 80, TAB_PROD.PRAZOREPO.ordinal() );
		tabDet.setTamColuna( 60, TAB_PROD.PRECOBASEPROD.ordinal() );
		tabDet.setTamColuna( 60, TAB_PROD.SLDPROD.ordinal() );
		tabDet.setTamColuna( 80, TAB_PROD.QTDMINPROD.ordinal() );
		tabDet.setTamColuna( 80, TAB_PROD.QTDMAXPROD.ordinal() );
		tabDet.setTamColuna( 80, TAB_PROD.SUGQTDMINPROD.ordinal() );
		tabDet.setTamColuna( 80, TAB_PROD.SUGQTDMAXPROD.ordinal() );
		tabDet.setTamColuna( 80, TAB_PROD.SUGPRAZOREPO.ordinal() );
		
		tabDet.setColunaEditavel( TAB_PROD.MARCACAO.ordinal(), true );
		tabDet.setColunaEditavel( TAB_PROD.SUGQTDMINPROD.ordinal(), true );
		tabDet.setColunaEditavel( TAB_PROD.SUGQTDMAXPROD.ordinal(), true );
		
		tabDet.setRowHeight( 22 );
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		lcCliente.setConexao( con );
		lcProd.setConexao( con );
		lcProd2.setConexao( con );
		lcGrupo.setConexao( con );
		
		
		//Dao será instanciada em outro lugar posteriormente
		daoprev = new DAOPrevEstoq( con );
	}

	private void carregarItens() {
		try {
			tabDet.setDataVector(daoprev.carregar( Aplicativo.iCodEmp, ListaCampos.getMasterFilial( "EQPRODUTO" ), txtDtIni.getVlrDate(), txtDtFim.getVlrDate()));
			
		} catch (ExceptionCarregaDados e) {
			Funcoes.mensagemErro( null, e.getMessage());
			tabDet.limpa();
			txtDtIni.requestFocus();
		}
	}
	

	private void selectAll( JTablePad tab ) {

		for ( int i = 0; i < tab.getNumLinhas(); i++ ) {
			tab.setValor( new Boolean( true ), i, 0 );
		}
	}

	private void limpaNaoSelecionados( JTablePad tab ) {

		int linhas = tab.getNumLinhas();
		int pos = 0;
		try {
			for ( int i = 0; i < linhas; i++ ) {
				if ( tab.getValor( i, 0 ) != null && ! ( (Boolean) tab.getValor( i, 0 ) ).booleanValue() ) { // xxx
					tab.tiraLinha( i );
					i--;
				}
			}
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}

	private void selectNecessarios( JTablePad tab ) {

		BigDecimal qtdaprod = null;

		for ( int i = 0; i < tab.getNumLinhas(); i++ ) {
			qtdaprod = (BigDecimal) tab.getValor( i, DETALHAMENTO.QTDAPROD.ordinal() );
			tab.setValor( new Boolean( qtdaprod.floatValue() > 0 ), i, 0 );
		}
	}

	private void deselectAll( JTablePad tab ) {

		for ( int i = 0; i < tab.getNumLinhas(); i++ ) {
			tab.setValor( new Boolean( false ), i, 0 );
		}
	}

	public void beforeCarrega( CarregaEvent cevt ) {
	}

	public void afterCarrega( CarregaEvent cevt ) {
	}

	public void keyTyped( KeyEvent e ) {
	}

	public void keyPressed( KeyEvent e ) {
		if ( e.getSource() == btBuscar && e.getKeyCode() == KeyEvent.VK_ENTER ) {
			btBuscar.doClick();
		}
	}

	public void keyReleased( KeyEvent e ) {
	}

	public void actionPerformed( ActionEvent e ) {

		if ( e.getSource() == btBuscar ) {
			if ( tabbedAbas.getSelectedIndex() == 0 ) {
				tabDet.limpa();
				carregarItens();
			}
		}
	
	}
}