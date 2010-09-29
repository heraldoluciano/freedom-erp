/*
 * Projeto: Freedom Pacote: org.freedom.modules.crm Classe: @(#)FControServicos.java
 * 
 * Este arquivo é parte do sistema Freedom-ERP, o Freedom-ERP é um software livre; você pode redistribui-lo e/ou <BR> modifica-lo dentro dos termos da Licença Pública Geral GNU como publicada pela Fundação do Software Livre (FSF); <BR> na versão 2 da Licença, ou (na sua opnião) qualquer versão. <BR>
 * Este programa é distribuido na esperança que possa ser util, mas SEM NENHUMA GARANTIA; <BR> sem uma garantia implicita de ADEQUAÇÂO a qualquer MERCADO ou APLICAÇÃO EM PARTICULAR. <BR> Veja a Licença Pública Geral GNU para maiores detalhes. <BR> Você deve ter recebido uma cópia da Licença Pública
 * Geral GNU junto com este programa, se não, <BR> escreva para a Fundação do Software Livre(FSF) Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA <BR>
 */

package org.freedom.modulos.gms.view.frame.utility;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.RowSorter;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.acao.TabelaEditEvent;
import org.freedom.acao.TabelaEditListener;
import org.freedom.acao.TabelaSelEvent;
import org.freedom.acao.TabelaSelListener;
import org.freedom.bmps.Icone;
import org.freedom.infra.model.jdbc.DbConnection;
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
import org.freedom.library.swing.util.SwingParams;
import org.freedom.modulos.atd.view.frame.crud.plain.FAtendente;
import org.freedom.modulos.fnc.view.dialog.utility.DLInfoPlanoPag;
import org.freedom.modulos.gms.business.object.RecMerc;
import org.freedom.modulos.gms.business.object.StatusOS;
import org.freedom.modulos.gms.view.dialog.utility.DLTipoProdServOrc;
import org.freedom.modulos.gms.view.frame.crud.detail.FCompra;
import org.freedom.modulos.gms.view.frame.crud.detail.FOrdemServico;
import org.freedom.modulos.gms.view.frame.crud.detail.FRecMerc;
import org.freedom.modulos.std.view.frame.crud.detail.FOrcamento;
import org.freedom.modulos.std.view.frame.crud.tabbed.FCliente;

/**
 * Painel de controle para Ordens de Serviço
 * 
 * @author Setpoint Informática Ltda./Anderson Sanchez
 * @version 06/05/2010
 */

public class FControleServicos extends FFilho implements ActionListener, TabelaSelListener, MouseListener, KeyListener, CarregaListener, TabelaEditListener, ChangeListener {

	private static final long serialVersionUID = 1L;

	private static final Color GREEN = new Color( 45, 190, 64 );

	// *** Paineis tela

	private JPanelPad panelGeral = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad panelMaster = new JPanelPad( 700, 120 );

	private JPanelPad panelAbas = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 1, 1 ) );

	private JTabbedPanePad tabbedAbas = new JTabbedPanePad();

	private JPanelPad panelSouth = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad panelNavegador = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 1, 1 ) );

	private JPanelPad panelFiltros = new JPanelPad( "Filtros", Color.BLUE );

	// *** Paineis Detalhamento

	private JPanelPad panelDet = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad panelTabDet = new JPanelPad( 700, 0 );

	private JPanelPad panelGridDet = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 1, 1 ) );

	private JPanelPad panelTabDetItens = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 1, 1 ) );

	private JTablePad tabDet = null;

	// *** Labels

	private JLabelPad sepdet = new JLabelPad();

	// *** Geral

	private JTextFieldPad txtCodCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtRazCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtDataini = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDatafim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtCodAtendente = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtNomeAtendente = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldFK txtDDDCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 4, 0 );

	private JTextFieldFK txtFoneCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 12, 0 );

	private JTextFieldFK txtDDDFax = new JTextFieldFK( JTextFieldPad.TP_STRING, 4, 0 );

	private JTextFieldFK txtFaxCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 12, 0 );

	private JTextFieldFK txtDDDCel = new JTextFieldFK( JTextFieldPad.TP_STRING, 4, 0 );

	private JTextFieldFK txtCelCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 12, 0 );

	private JTextFieldFK txtEmailCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldFK txtContatoCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldFK txtEndCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldFK txtCidCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 30, 0 );

	private JTextFieldFK txtUfCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 2, 0 );

	private JTextFieldFK txtNumCli = new JTextFieldFK( JTextFieldPad.TP_INTEGER, 10, 0 );

	// *** Campos

	// private JTextFieldFK txtDescProd = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	// ** Checkbox

	private JCheckBoxPad cbEtapa0 = new JCheckBoxPad( "Pendentes", "S", "N" );

	private JCheckBoxPad cbEtapa1 = new JCheckBoxPad( "Analise", "S", "N" );

	private JCheckBoxPad cbEtapa2 = new JCheckBoxPad( "Orçamento", "S", "N" );

	private JCheckBoxPad cbEtapa3 = new JCheckBoxPad( "Aprovação", "S", "N" );

	private JCheckBoxPad cbEtapa4 = new JCheckBoxPad( "Execução", "S", "N" );

	private JCheckBoxPad cbEtapa5 = new JCheckBoxPad( "Faturado", "S", "N" );

	private JCheckBoxPad cbEtapa6 = new JCheckBoxPad( "entregue", "S", "N" );

	private ImageIcon imgColuna = Icone.novo( "clAgdCanc.png" );

	// *** Listacampos

	// private ListaCampos lcCliente = new ListaCampos( this, "CL" );
	// private ListaCampos lcProd = new ListaCampos( this );

	// *** Botões
	private JButtonPad btAtualiza = new JButtonPad( Icone.novo( "btAtualiza.gif" ) );

	private JButtonPad btNovo = new JButtonPad( Icone.novo( "btNovo.gif" ) );

	private JButtonPad btEditar = new JButtonPad( Icone.novo( "btEditar.gif" ) );

	private JButtonPad btCompra = new JButtonPad( Icone.novo( "btEntrada.png" ) );

	private JButtonPad btOrcamento = new JButtonPad( Icone.novo( "btOrcamento.png" ) );

	private JButtonPad btChamado = new JButtonPad( Icone.novo( "btChamado.png" ) );

	private JButtonPad btRma = new JButtonPad( Icone.novo( "btRma.gif" ) );

	private JTablePad tabstatus = new JTablePad();

	private JScrollPane scpStatus = new JScrollPane( tabstatus );

	private ListaCampos lcAtendente = new ListaCampos( this, "AE" );

	private ListaCampos lcCli = new ListaCampos( this );

	// Enums

	private enum DETALHAMENTO {
		STATUS, STATUSTXT, TICKET, CODTIPORECMERC, DATA, HORA, CODCLI, NOMECLI, CODORC, CODRMAS, CODCHAMADOS;
	}

	public FControleServicos() {

		super( false );

		setTitulo( "Painel de controle de serviços", this.getClass().getName() );
		setAtribos( 10, 10, 740, 600 );

		int x = (int) ( Aplicativo.telaPrincipal.dpArea.getSize().getWidth() - getWidth() ) / 2;
		int y = (int) ( Aplicativo.telaPrincipal.dpArea.getSize().getHeight() - getHeight() ) / 2;

		setLocation( x, y );

		setValoresPadrao();

		montaListaCampos();

		criaTabelas();

		montaTela();

		montaListeners();

		adicToolTips();

	}

	private void montaListaCampos() {

		// Atendimento para funcionamento
		txtCodAtendente.setTabelaExterna( lcAtendente, FAtendente.class.getCanonicalName() );
		txtCodAtendente.setFK( true );
		txtCodAtendente.setNomeCampo( "CodAtend" );
		lcAtendente.add( new GuardaCampo( txtCodAtendente, "CodAtend", "Cód.atend.", ListaCampos.DB_PK, false ) );
		lcAtendente.add( new GuardaCampo( txtNomeAtendente, "NomeAtend", "Nome", ListaCampos.DB_SI, false ) );
		lcAtendente.montaSql( false, "ATENDENTE", "AT" );
		lcAtendente.setReadOnly( true );

		lcCli.add( new GuardaCampo( txtCodCli, "CodCli", "Cód.cli.", ListaCampos.DB_PK, false ) );
		lcCli.add( new GuardaCampo( txtRazCli, "RazCli", "Razão social do cliente", ListaCampos.DB_SI, false ) );
		lcCli.add( new GuardaCampo( txtDDDCli, "DDDCli", "DDD", ListaCampos.DB_SI, false ) );
		lcCli.add( new GuardaCampo( txtFoneCli, "FoneCli", "Telefone", ListaCampos.DB_SI, false ) );
		lcCli.add( new GuardaCampo( txtDDDCli, "DDDCli", "DDD", ListaCampos.DB_SI, false ) );
		lcCli.add( new GuardaCampo( txtFoneCli, "FoneCli", "Telefone", ListaCampos.DB_SI, false ) );
		lcCli.add( new GuardaCampo( txtDDDFax, "DDDFaxCli", "DDD", ListaCampos.DB_SI, false ) );
		lcCli.add( new GuardaCampo( txtFaxCli, "FaxCli", "Fax", ListaCampos.DB_SI, false ) );
		lcCli.add( new GuardaCampo( txtDDDCel, "DDDCelCli", "DDD", ListaCampos.DB_SI, false ) );
		lcCli.add( new GuardaCampo( txtCelCli, "CelCli", "Fax", ListaCampos.DB_SI, false ) );
		lcCli.add( new GuardaCampo( txtEmailCli, "EmailCli", "Email", ListaCampos.DB_SI, false ) );
		lcCli.add( new GuardaCampo( txtContatoCli, "ContCli", "Contato", ListaCampos.DB_SI, false ) );
		lcCli.add( new GuardaCampo( txtEndCli, "EndCli", "Endereço", ListaCampos.DB_SI, false ) );
		lcCli.add( new GuardaCampo( txtCidCli, "CidCli", "Cidade", ListaCampos.DB_SI, false ) );
		lcCli.add( new GuardaCampo( txtUfCli, "UFCli", "UF", ListaCampos.DB_SI, false ) );
		lcCli.add( new GuardaCampo( txtNumCli, "NumCli", "Número", ListaCampos.DB_SI, false ) );
		lcCli.setWhereAdic( "ATIVOCLI='S'" );
		lcCli.montaSql( false, "CLIENTE", "VD" );
		lcCli.setReadOnly( true );
		txtCodCli.setTabelaExterna( lcCli, FCliente.class.getCanonicalName() );
		txtCodCli.setFK( true );
		txtCodCli.setNomeCampo( "CodCli" );

	}

	private void setValoresPadrao() {

		txtDataini.setVlrDate( Funcoes.getDataIniMes( Funcoes.getMes( new Date() ) - 1, Funcoes.getAno( new Date() ) ) );
		txtDatafim.setVlrDate( Funcoes.getDataFimMes( Funcoes.getMes( new Date() ) - 1, Funcoes.getAno( new Date() ) ) );

	}

	private void adicToolTips() {

		btNovo.setToolTipText( "Nova ordem de serviço" );
		btAtualiza.setToolTipText( "Atualize consulta" );
		btChamado.setToolTipText( "Abrir chamado" );
		btCompra.setToolTipText( "Gerar pedido de compra" );
		btEditar.setToolTipText( "Editar ordem de serviço" );
		btOrcamento.setToolTipText( "Gerar orçamento" );
		btRma.setToolTipText( "Gerar Requisição de material" );

	}

	private void montaListeners() {

		btAtualiza.addActionListener( this );
		btNovo.addActionListener( this );

		btEditar.addActionListener( this );
		btCompra.addActionListener( this );
		btOrcamento.addActionListener( this );
		btRma.addActionListener( this );
		btChamado.addActionListener( this );

		tabDet.addTabelaSelListener( this );
		tabDet.addMouseListener( this );

	}

	private void montaGridStatus() {

		tabstatus.adicColuna( "" ); // Selecao
		tabstatus.adicColuna( "Cod." ); // Codigo
		tabstatus.adicColuna( "" ); // Imagem
		tabstatus.adicColuna( "Status" ); // Descrição

		tabstatus.setTamColuna( 10, 0 );

		tabstatus.setColunaInvisivel( 1 );

		tabstatus.setTamColuna( 10, 2 );
		tabstatus.setTamColuna( 100, 3 );

		tabstatus.setRowHeight( 12 );

		tabstatus.setColunaEditavel( 0, new Boolean( true ) );

	}

	private void carregaStatus() {

		Vector<Object> valores = StatusOS.getValores();
		Vector<String> labels = StatusOS.getLabels();
		// Vector<ImageIcon> icones = new Vector<ImageIcon>();

		Vector<Object> item = null;

		for ( int i = 0; i < valores.size(); i++ ) {

			item = new Vector<Object>();

			String valor = valores.elementAt( i ).toString();
			String label = labels.elementAt( i );
			ImageIcon icon = StatusOS.getImagem( valor, StatusOS.IMG_TAMANHO_P );

			if ( StatusOS.OS_FINALIZADA.getValue().equals( valor ) ) {
				item.addElement( new Boolean( false ) );
			}
			else {
				item.addElement( new Boolean( true ) );
			}

			item.addElement( valor );
			item.addElement( icon );
			item.addElement( label );

			tabstatus.adicLinha( item );

		}

	}

	private void montaTela() {

		getTela().add( panelGeral, BorderLayout.CENTER );
		panelGeral.add( panelMaster, BorderLayout.NORTH );

		// ***** Cabeçalho

		panelMaster.adic( panelFiltros, 4, 0, 720, 114 );

		panelFiltros.adic( scpStatus, 517, 0, 150, 82 );
		panelFiltros.adic( btAtualiza, 670, 0, 30, 81 );

		panelFiltros.adic( new JLabelPad( "Data Inicial" ), 7, 0, 70, 20 );
		panelFiltros.adic( txtDataini, 7, 20, 70, 20 );

		panelFiltros.adic( new JLabelPad( "Data Final" ), 80, 0, 70, 20 );
		panelFiltros.adic( txtDatafim, 80, 20, 70, 20 );

		panelFiltros.adic( new JLabelPad( "Cód.Atend." ), 153, 0, 70, 20 );
		panelFiltros.adic( txtCodAtendente, 153, 20, 70, 20 );

		panelFiltros.adic( new JLabelPad( "Nome do Atendente" ), 226, 0, 180, 20 );
		panelFiltros.adic( txtNomeAtendente, 226, 20, 270, 20 );

		panelFiltros.adic( new JLabelPad( "Cód.Cli." ), 153, 40, 70, 20 );
		panelFiltros.adic( txtCodCli, 153, 60, 70, 20 );

		panelFiltros.adic( new JLabelPad( "Razão social do cliente" ), 226, 40, 180, 20 );
		panelFiltros.adic( txtRazCli, 226, 60, 270, 20 );

		// panelMaster.adic( btRecarregar, 595, 8, 123, 42 );

		// ***** Abas

		panelGeral.add( panelAbas, BorderLayout.CENTER );
		panelGeral.add( panelAbas );
		panelAbas.add( tabbedAbas );

		tabbedAbas.addTab( "Detalhamento", panelDet );

		tabbedAbas.addChangeListener( this );

		// ***** Detalhamento

		panelDet.add( panelTabDet, BorderLayout.NORTH );
		panelDet.add( panelGridDet, BorderLayout.CENTER );
		panelGridDet.add( panelTabDetItens );

		panelTabDetItens.add( new JScrollPane( tabDet ) );

		// ***** Rodapé

		Color statusColor = new Color( 111, 106, 177 );
		Font statusFont = SwingParams.getFontpadmed();

		panelGeral.add( panelSouth, BorderLayout.SOUTH );
		panelSouth.setBorder( BorderFactory.createEtchedBorder() );

		panelNavegador.add( btNovo );
		panelNavegador.add( btEditar );
		panelNavegador.add( btCompra );
		panelNavegador.add( btOrcamento );
		panelNavegador.add( btChamado );
		panelNavegador.add( btRma );

		panelSouth.add( panelNavegador, BorderLayout.WEST );

		panelSouth.add( adicBotaoSair(), BorderLayout.EAST );

		montaGridStatus();
		carregaStatus();

	}

	private void criaTabelas() {

		// Tabela de detalhamento

		tabDet = new JTablePad();
		tabDet.setRowHeight( 21 );

		tabDet.adicColuna( "" );
		tabDet.adicColuna( "" );
		tabDet.adicColuna( "Ticket" );
		tabDet.adicColuna( "Cód.Tipo.Rec.Merc." );

		tabDet.adicColuna( "Data" );
		tabDet.adicColuna( "Hora" );
		tabDet.adicColuna( "Cod.Cli." );
		tabDet.adicColuna( "Cliente" );
		tabDet.adicColuna( "Orc." );
		tabDet.adicColuna( "Rmas" );
		tabDet.adicColuna( "Cham." );

		tabDet.setTamColuna( 21, DETALHAMENTO.STATUS.ordinal() );
		tabDet.setColunaInvisivel( DETALHAMENTO.STATUSTXT.ordinal() );
		tabDet.setTamColuna( 60, DETALHAMENTO.TICKET.ordinal() );
		tabDet.setColunaInvisivel( DETALHAMENTO.CODTIPORECMERC.ordinal() );
		tabDet.setTamColuna( 60, DETALHAMENTO.DATA.ordinal() );
		tabDet.setTamColuna( 50, DETALHAMENTO.HORA.ordinal() );
		tabDet.setTamColuna( 60, DETALHAMENTO.CODCLI.ordinal() );
		tabDet.setTamColuna( 300, DETALHAMENTO.NOMECLI.ordinal() );
		tabDet.setTamColuna( 50, DETALHAMENTO.CODORC.ordinal() );
		tabDet.setTamColuna( 50, DETALHAMENTO.CODRMAS.ordinal() );
		tabDet.setTamColuna( 50, DETALHAMENTO.CODCHAMADOS.ordinal() );

		// tabDet.setColunaInvisivel( 2 );

	}

	public void montaGrid() {

		try {

			StringBuilder sql = new StringBuilder();

			sql.append( "select " );
			sql.append( "rm.ticket, rm.codtiporecmerc, rm.status, rm.dtins data, rm.hins hora, rm.codcli, cl.nomecli, " );

			sql.append( "(select first 1 ros.codorc from EQITRECMERCITOSITORC ros " );
			sql.append( "where ros.codemp=rm.codemp and ros.codfilial=rm.codfilial and ros.ticket=rm.ticket" );
			sql.append( ") codorc " );

			sql.append( "from eqrecmerc rm, vdcliente cl " );

			sql.append( "where cl.codemp=rm.codempcl and cl.codfilial=rm.codfilialcl and cl.codcli=rm.codcli " );
			sql.append( "and rm.codemp=? and rm.codfilial=? " );
			sql.append( "and rm.dtins between ? and ? " );

			StringBuffer status = new StringBuffer( "" );

			boolean primeiro = true;

			for ( int i = 0; i < tabstatus.getNumLinhas(); i++ ) {

				if ( (Boolean) tabstatus.getValor( i, 0 ) ) {

					if ( primeiro ) {
						sql.append( " and rm.status in (" );
					}
					else {
						sql.append( "," );
					}

					sql.append( "'" + tabstatus.getValor( i, 1 ) + "'" );

					primeiro = false;
				}

				if ( i == tabstatus.getNumLinhas() - 1 && !primeiro ) {
					sql.append( " ) " );
				}

			}

			if ( txtCodAtendente.getVlrInteger() > 0 ) {
				sql.append( " and rm.codempar=? and rm.codfilialar=? and rm.codatendrec=? " );
			}

			if ( txtCodCli.getVlrInteger() > 0 ) {
				sql.append( " and rm.codempcl=? and rm.codfilialcl=? and rm.codcli=? " );
			}

			sql.append( " order by rm.dtins desc, rm.hins desc " );

			System.out.println( "SQL:" + sql.toString() );

			PreparedStatement ps = con.prepareStatement( sql.toString() );

			int iparam = 1;

			ps.setInt( iparam++, Aplicativo.iCodEmp );
			ps.setInt( iparam++, ListaCampos.getMasterFilial( "EQRECMERC" ) );
			ps.setDate( iparam++, Funcoes.dateToSQLDate( txtDataini.getVlrDate() ) );
			ps.setDate( iparam++, Funcoes.dateToSQLDate( txtDatafim.getVlrDate() ) );

			if ( txtCodAtendente.getVlrInteger() > 0 ) {
				ps.setInt( iparam++, lcAtendente.getCodEmp() );
				ps.setInt( iparam++, lcAtendente.getCodFilial() );
				ps.setInt( iparam++, txtCodAtendente.getVlrInteger() );
			}

			if ( txtCodCli.getVlrInteger() > 0 ) {
				ps.setInt( iparam++, lcCli.getCodEmp() );
				ps.setInt( iparam++, lcCli.getCodFilial() );
				ps.setInt( iparam++, txtCodCli.getVlrInteger() );
			}

			ResultSet rs = ps.executeQuery();

			tabDet.limpa();

			int row = 0;

			while ( rs.next() ) {

				tabDet.adicLinha();

				imgColuna = StatusOS.getImagem( rs.getString( "status" ), StatusOS.IMG_TAMANHO_M );

				tabDet.setValor( imgColuna, row, DETALHAMENTO.STATUS.ordinal() );
				tabDet.setValor( rs.getString( "status" ), row, DETALHAMENTO.STATUSTXT.ordinal() );
				tabDet.setValor( rs.getInt( DETALHAMENTO.TICKET.toString().trim() ), row, DETALHAMENTO.TICKET.ordinal() );
				tabDet.setValor( rs.getInt( DETALHAMENTO.CODTIPORECMERC.toString().trim() ), row, DETALHAMENTO.CODTIPORECMERC.ordinal() );
				tabDet.setValor( Funcoes.dateToStrDate( rs.getDate( DETALHAMENTO.DATA.toString() ) ), row, DETALHAMENTO.DATA.ordinal() );
				tabDet.setValor( rs.getString( DETALHAMENTO.HORA.toString().trim() ), row, DETALHAMENTO.HORA.ordinal() );
				tabDet.setValor( rs.getInt( DETALHAMENTO.CODCLI.toString().trim() ), row, DETALHAMENTO.CODCLI.ordinal() );
				tabDet.setValor( rs.getString( DETALHAMENTO.NOMECLI.toString().trim() ), row, DETALHAMENTO.NOMECLI.ordinal() );
				tabDet.setValor( rs.getString( DETALHAMENTO.CODORC.toString().trim() ), row, DETALHAMENTO.CODORC.ordinal() );

				Vector<Integer> rmas = RecMerc.getRmasOS( rs.getInt( DETALHAMENTO.TICKET.toString().trim() ) );				
				Vector<Integer> chamados = RecMerc.getChamadosOS( rs.getInt( DETALHAMENTO.TICKET.toString().trim() ) );

				tabDet.setValor( Funcoes.vectorToString( rmas, "," ), row, DETALHAMENTO.CODRMAS.ordinal() );				
				tabDet.setValor( Funcoes.vectorToString( chamados, "," ), row, DETALHAMENTO.CODCHAMADOS.ordinal() );

				row++; 

			}

			// Permitindo reordenação

			if ( row > 0 ) {
				RowSorter<TableModel> sorter = new TableRowSorter<TableModel>( tabDet.getModel() );
				tabDet.setRowSorter( sorter );
			}
			else {
				tabDet.setRowSorter( null );
			}

		} catch ( Exception e ) {
			e.printStackTrace();
		}

	}

	public void actionPerformed( ActionEvent e ) {

		if ( e.getSource() == btAtualiza ) {
			montaGrid();
		}
		else if ( e.getSource() == btNovo ) {
			novoRecebimento();
		}
		else if ( e.getSource() == btEditar ) {
			abreRecMerc();
		}
		else if ( e.getSource() == btCompra ) {
			geraCompra();
		}
		else if ( e.getSource() == btOrcamento ) {
			geraOrcamento();
		}
		else if ( e.getSource() == btRma ) {
			gerarRmas();
		}
		else if ( e.getSource() == btChamado ) {
			gerarChamados();
		}



	}

	private void novoRecebimento() {

		FOrdemServico ordemservico = new FOrdemServico( true );

		try {

			Aplicativo.telaPrincipal.criatela( "Ordem de Serviço", ordemservico, con );
			ordemservico.setTelaMae( this );

		} catch ( Exception e ) {
			e.printStackTrace();
		}

	}

	public void valorAlterado( TabelaSelEvent e ) {

		/*
		 * if ( e.getTabela() == tabOrcamentos && tabOrcamentos.getLinhaSel() > -1 && !carregandoOrcamentos ) { buscaItensVenda( (Integer)tabOrcamentos.getValor( tabOrcamentos.getLinhaSel(), VENDAS.CODVENDA.ordinal() ), "V" ); }
		 */
	}

	private void abreRecMerc() {

		FOrdemServico ordemservico = null;

		try {

			if ( tabDet.getLinhaSel() > -1 ) {

				if ( Aplicativo.telaPrincipal.temTela( FRecMerc.class.getName() ) ) {
					ordemservico = (FOrdemServico) Aplicativo.telaPrincipal.getTela( FOrdemServico.class.getName() );
				}
				else {
					ordemservico = new FOrdemServico( false );
					Aplicativo.telaPrincipal.criatela( "Recepção de mercadorias", ordemservico, con );
				}

				int ticket = (Integer) tabDet.getValor( tabDet.getLinhaSel(), DETALHAMENTO.TICKET.ordinal() );
				int codtiporecmerc = (Integer) tabDet.getValor( tabDet.getLinhaSel(), DETALHAMENTO.CODTIPORECMERC.ordinal() );

				ordemservico.exec( ticket, codtiporecmerc, this );
			}
			else {
				Funcoes.mensagemInforma( this, "Não há nenhum registro selecionado para edição!" );
			}

		} catch ( Exception e ) {
			e.printStackTrace();
		}

	}

	public void mouseClicked( MouseEvent mevt ) {

		JTablePad tabEv = (JTablePad) mevt.getSource();

		if ( mevt.getClickCount() == 2 ) {
			if ( tabEv == tabDet && tabEv.getLinhaSel() > -1 ) {

				abreRecMerc();

			}
		}
	}

	public void mouseEntered( MouseEvent e ) {

	}

	public void mouseExited( MouseEvent e ) {

	}

	public void mousePressed( MouseEvent e ) {

	}

	public void mouseReleased( MouseEvent e ) {

	}

	public void keyPressed( KeyEvent e ) {

		if ( e.getSource() == btAtualiza && e.getKeyCode() == KeyEvent.VK_ENTER ) {
			btAtualiza.doClick();
		}
	}

	public void keyReleased( KeyEvent e ) {

	}

	public void keyTyped( KeyEvent e ) {

	}

	public void beforeCarrega( CarregaEvent e ) {

	}

	public void afterCarrega( CarregaEvent e ) {

		// if ( lcProd == e.getListaCampos() || lcCliente == e.getListaCampos() ) {
		montaGrid();
		// }

	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		montaGrid();
		lcAtendente.setConexao( cn );
		lcCli.setConexao( con );
		// lcProd.setConexao( con );

	}

	public void valorAlterado( TabelaEditEvent evt ) {

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

	public void stateChanged( ChangeEvent cevt ) {

		if ( cevt.getSource() == tabbedAbas ) {
			if ( tabbedAbas.getSelectedIndex() == 1 ) {
				// geraTabTemp();
			}
		}
	}

	private Integer getPlanoPag() {

		Integer codplanopag = null;

		try {

			DLInfoPlanoPag dl = new DLInfoPlanoPag( this, con );
			dl.setVisible( true );

			if ( dl.OK ) {
				codplanopag = dl.getValor();
				dl.dispose();
			}
			else {
				dl.dispose();
			}

		} catch ( Exception e ) {
			e.printStackTrace();
		}
		return codplanopag;
	}

	private static HashMap<Object, Object> getInfoOrc(Integer codcli, Component corig) {

		HashMap<Object, Object> ret = new HashMap<Object, Object>();

		try {

			DLTipoProdServOrc dl = new DLTipoProdServOrc( corig );
			dl.setCodcli( codcli );

			dl.setVisible( true );

			if ( dl.OK ) {

				ret.put( DLTipoProdServOrc.COMPONENTES, dl.getComponentes() );
				ret.put( DLTipoProdServOrc.SERVICOS, dl.getServicos() );
				ret.put( DLTipoProdServOrc.NOVOS, dl.getNovos() );
				ret.put( "CODPLANOPAG", dl.getPlanoPag() );

				dl.dispose();
			}
			else {
				ret = null;
				dl.dispose();
			}

		} 
		catch ( Exception e ) {
			e.printStackTrace();
		}

		return ret;
	}


	private void abrecompra( Integer codcompra ) {

		if ( fPrim.temTela( "Compra" ) == false ) {
			FCompra tela = new FCompra();
			fPrim.criatela( "Compra", tela, con );
			tela.exec( codcompra );
		}

	}

	private static void abreorcamento( Integer codorc ) {

		if ( Aplicativo.telaPrincipal.temTela( "Orçamento" ) == false ) {
			FOrcamento tela = new FOrcamento();
			Aplicativo.telaPrincipal.criatela( "Orçamento", tela, Aplicativo.getInstace().getConexao() );
			tela.exec( codorc );
		}

	}

	private void geraCompra() {

		StringBuilder sql = new StringBuilder();

		Integer ticket = null;
		BigDecimal pesoliq = null;
		BigDecimal peso1 = null;
		BigDecimal peso2 = null;
		String unid = null;
		PreparedStatement ps = null;

		RecMerc recmerc = null;

		try {

			if ( tabDet.getLinhaSel() > -1 ) {

				ticket = (Integer) tabDet.getValor( tabDet.getLinhaSel(), DETALHAMENTO.TICKET.ordinal() );

				recmerc = new RecMerc( this, ticket, con );

				if ( tabDet.getValor( tabDet.getLinhaSel(), DETALHAMENTO.STATUSTXT.ordinal() ).equals( StatusOS.OS_ANALISE.getValue() ) ) {

					if ( Funcoes.mensagemConfirma( this, "Confirma a geração do pedido de compra para o ticket nro.:" + ticket.toString() + " ?" ) == JOptionPane.YES_OPTION ) {

						Integer codcompra = recmerc.geraCompra();

						if ( codcompra != null && codcompra > 0 ) {

							abrecompra( codcompra );

						}
					}

				}
				else {
					Funcoes.mensagemInforma( this, "A Ordem de serviço selecionada ainda está pendente de analise!" );
				}

			}
			else {
				Funcoes.mensagemInforma( this, "Selecione um ticket no grid!" );
			}

		} catch ( Exception e ) {
			e.printStackTrace();
		}

	}

	private void geraOrcamento() {


		if ( tabDet.getLinhaSel() > -1 ) {

			String codorcgrid = (String) tabDet.getValor( tabDet.getLinhaSel(), DETALHAMENTO.CODORC.ordinal()  );
			// Se já nao houver orçamento .. deve gerar...
			if ( "".equals( codorcgrid ) || null == codorcgrid ) {

				Integer ticket = (Integer) tabDet.getValor( tabDet.getLinhaSel(), DETALHAMENTO.TICKET.ordinal() );
				Integer codcli = (Integer) tabDet.getValor( tabDet.getLinhaSel(), DETALHAMENTO.CODCLI.ordinal() );

				String statustxt = (String) tabDet.getValor( tabDet.getLinhaSel(), DETALHAMENTO.STATUSTXT.ordinal() );

				FControleServicos.geraOrcamento( ticket, null, statustxt, codcli, this );
			}
			else {
				// Se já existir um orçamento deve abri-lo

				abreorcamento( Integer.parseInt( codorcgrid ) );

			}

		}

	}

	public static void geraOrcamento(Integer ticket, Integer codorcgrid, String statustxt, Integer codcli, Component corig) {

		StringBuilder sql = new StringBuilder();

		BigDecimal pesoliq = null;
		BigDecimal peso1 = null;
		BigDecimal peso2 = null;
		String unid = null;
		PreparedStatement ps = null; 

		RecMerc recmerc = null;

		try {


			recmerc = new RecMerc( corig, ticket, Aplicativo.getInstace().getConexao() );

			if ( statustxt.equals( StatusOS.OS_ANALISE.getValue() ) ||
					statustxt.equals( StatusOS.OS_ENCAMINHADO.getValue() ) ||
					statustxt.equals( StatusOS.OS_ANDAMENTO.getValue() ) ||
					statustxt.equals( StatusOS.OS_PRONTO.getValue() )
			){
				if ( Funcoes.mensagemConfirma( corig, "Confirma a geração do orçamento para o ticket nro.:" + ticket.toString() + " ?" ) == JOptionPane.YES_OPTION ) {

					HashMap<Object, Object> parametros = getInfoOrc(codcli, corig);

					Integer codorc = null;

					if(parametros != null) {
						codorc = recmerc.geraOrcamento(parametros);
					}

					if ( codorc != null && codorc > 0 ) {

						abreorcamento( codorc );

					}
				}

			}
			else {
				Funcoes.mensagemInforma( corig, "A Ordem de serviço selecionada ainda encontra-se pendente!\nPara gerar orçamento deve estar 'Em Análise'!" );
			}

		}

		catch ( Exception e ) {
			e.printStackTrace();
		}

	}

	private void gerarRmas() {

		if ( tabDet.getLinhaSel() > -1 ) {

			Integer ticket = (Integer) tabDet.getValor( tabDet.getLinhaSel(), DETALHAMENTO.TICKET.ordinal() );
			String codrmagrid = tabDet.getValor( tabDet.getLinhaSel(), DETALHAMENTO.CODRMAS.ordinal() ).toString();
			String statustxt = (String) tabDet.getValor( tabDet.getLinhaSel(), DETALHAMENTO.STATUSTXT.ordinal() );

			FControleServicos.gerarRmas(ticket, codrmagrid, statustxt, this);
		}
		else {
			Funcoes.mensagemInforma( this, "Selecione um ticket no grid!" );
		}
	}

	public static void gerarRmas(Integer ticket, String codrmagrid, String statustxt, Component corig) {

		StringBuilder sql = new StringBuilder();
		PreparedStatement ps = null;

		RecMerc recmerc = null;

		try {
			// Se nao houver RMA.. deve gerar...
			if ( "".equals( codrmagrid ) || null == codrmagrid ) {

				recmerc = new RecMerc( corig, ticket, Aplicativo.getInstace().getConexao() );

				if ( statustxt.equals( StatusOS.OS_APROVADA.getValue() ) || 
						statustxt.equals( StatusOS.OS_ANALISE.getValue() ) ||
						statustxt.equals( StatusOS.OS_ENCAMINHADO.getValue() ) ||
						statustxt.equals( StatusOS.OS_ANDAMENTO.getValue() ) ||
						statustxt.equals( StatusOS.OS_PRONTO.getValue() ) ||
						statustxt.equals( StatusOS.OS_ORCAMENTO.getValue() )
				){

					if ( Funcoes.mensagemConfirma( corig, "Confirma a geração de RMA para o ticket nro.:" + ticket.toString() + " ?" ) == JOptionPane.YES_OPTION ) {

//						Vector<Integer> rmas = recmerc.geraRmasPorItem( );
						Vector<Integer> rmas = recmerc.geraRmasPorOS() ;


						if ( rmas != null && rmas.size() > 0 ) {

							System.out.println("RMA(s) " + Funcoes.vectorToString( rmas, "," ) + " gerada(s) com sucesso...");

						}
					}

				}
				else {
					Funcoes.mensagemInforma( corig, "A Ordem de serviço selecionada encontra-se em um status inválido para geração de RMA!\n" 
							+ "Para gerar RMA ela deve estar em uma das situações abaixo:\n"
							+ "'Em Análise', 'Encaminhada', 'Em andamento' ou 'Pronto' !" );
				}

			}




		} catch ( Exception e ) {
			e.printStackTrace();
		}

	}

	private void gerarChamados() {

		StringBuilder sql = new StringBuilder();
		Integer ticket = null;
		PreparedStatement ps = null;

		RecMerc recmerc = null;

		try {

			if ( tabDet.getLinhaSel() > -1 ) {

				String codchamadogrid = tabDet.getValor( tabDet.getLinhaSel(), DETALHAMENTO.CODCHAMADOS.ordinal() ).toString();

				// Se nao houver chamado deve gerar...
				if ( "".equals( codchamadogrid ) || null == codchamadogrid ) {

					ticket = (Integer) tabDet.getValor( tabDet.getLinhaSel(), DETALHAMENTO.TICKET.ordinal() );

					recmerc = new RecMerc( this, ticket, con );

					String statustxt = (String) tabDet.getValor( tabDet.getLinhaSel(), DETALHAMENTO.STATUSTXT.ordinal() ); 

					if ( statustxt.equals( StatusOS.OS_APROVADA.getValue() ) || 
							statustxt.equals( StatusOS.OS_ANALISE.getValue() ) ||
							statustxt.equals( StatusOS.OS_ENCAMINHADO.getValue() ) ||
							statustxt.equals( StatusOS.OS_ANDAMENTO.getValue() )
					){

						if ( Funcoes.mensagemConfirma( this, "Confirma a geração de chamados para o ticket nro.:" + ticket.toString() + " ?" ) == JOptionPane.YES_OPTION ) {

							Vector<Integer> chamados = recmerc.gerarChamados( );


							if ( chamados != null && chamados.size() > 0 ) {

								System.out.println("Chamado(s) " + Funcoes.vectorToString( chamados, "," ) + " gerado(s) com sucesso...");

							}
						}

					}
					else {
						Funcoes.mensagemInforma( this, "A Ordem de serviço selecionada encontra-se em um status inválido para geração de Chamados!\n" 
								+ "Para gerar Chamados ela deve estar em uma das situações abaixo:\n"
								+ "'Em Análise', 'Encaminhada' ou 'Em andamento' !" );
					}

				}

			}
			else {
				Funcoes.mensagemInforma( this, "Selecione um ticket no grid!" );
			}

		} catch ( Exception e ) {
			e.printStackTrace();
		}

	}


}
