/**
 * @version 02/08/2003 <BR>
 * @author Setpoint Informática Ltda./Alex Rodrigues <BR>
 *         Projeto: Freedom <BR>
 *         Pacote: org.freedom.modulos.std <BR>
 *         Classe:
 * @(#)FConsSolItem.java <BR>
 *                       Este programa é licenciado de acordo com a LPG-PC (Licença Pública Geral para Programas de Computador), <BR>
 *                       modifica-lo dentro dos termos da Licença Pública Geral GNU como publicada pela Fundação do Software Livre (FSF); <BR>
 *                       A LPG-PC deve acompanhar todas PUBLICAÇÕES, DISTRIBUIÇÕES e REPRODUÇÕES deste Programa. <BR>
 *                       Caso uma cópia da LPG-PC não esteja disponível junto com este Programa, você pode contatar <BR>
 *                       sem uma garantia implicita de ADEQUAÇÂO a qualquer MERCADO ou APLICAÇÃO EM PARTICULAR. <BR>
 *                       Veja a Licença Pública Geral GNU para maiores detalhes. <BR>
 *                       Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa é preciso estar <BR>
 *                       de acordo com os termos da LPG-PC <BR>
 * <BR>
 *                       Formulário de consulta de RMA.
 */

package org.freedom.modulos.gms.view.frame.utility;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import org.freedom.bmps.Icone;
import org.freedom.infra.functions.StringFunctions;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.component.ImprimeOS;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JButtonPad;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JPanelPad;
import org.freedom.library.swing.component.JTablePad;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FFilho;
import org.freedom.modulos.gms.business.object.GestaoSol.GRID_SOL;
import org.freedom.modulos.gms.dao.DAOGestaoSol;
import org.freedom.modulos.gms.view.frame.crud.detail.FRma;

public class FGestaoSol extends FFilho implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JPanelPad pinCab = new JPanelPad( 0, 145 );
	
	private JPanelPad pinBarraFerramentas = new JPanelPad( 40, 0 );
	
	private JPanelPad pnCli = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );
	
	private JPanelPad pnDetalhe = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad pnRod = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad pnLegenda = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 0, 5 ) );

	private JTextFieldPad txtDtIni = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDtFim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtCodUsu = new JTextFieldPad( JTextFieldPad.TP_STRING, 8, 0 );

	private JTextFieldFK txtNomeUsu = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtCodCC = new JTextFieldPad( JTextFieldPad.TP_STRING, 19, 0 );

	private JTextFieldPad txtAnoCC = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 4, 0 );

	private JTextFieldFK txtDescCC = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtCodAlmoxarife = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );

	private JTextFieldFK txtDescAlmoxarife = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtCodProd = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescProd = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtRefProd = new JTextFieldPad( JTextFieldPad.TP_STRING, 20, 0 );

	private JTablePad tab = new JTablePad();

	private ImageIcon imgCancelada = Icone.novo( "clVencido.gif" );

	private ImageIcon imgExpedida = Icone.novo( "clPago.gif" );

	private ImageIcon imgAprovada = Icone.novo( "clPagoParcial.gif" );

	private ImageIcon imgPendente = Icone.novo( "clNaoVencido.gif" );

	private ImageIcon imgColuna = null;
	
	private JButtonPad btTudo = new JButtonPad( Icone.novo( "btTudo.gif" ) );
	
	private JButtonPad btNada = new JButtonPad( Icone.novo( "btNada.gif" ) );

	private JButtonPad btGerarSol = new JButtonPad( Icone.novo( "btGerar.gif" ) );

	private JButtonPad btBusca = new JButtonPad( "Buscar", Icone.novo( "btPesquisa.gif" ) );

	private JButtonPad btPrevimp = new JButtonPad( "Imprimir", Icone.novo( "btPrevimp.gif" ) );

	private JButtonPad btSair = new JButtonPad( "Sair", Icone.novo( "btSair.gif" ) );

	private JScrollPane spnTab = new JScrollPane( tab );

	private ListaCampos lcAlmox = new ListaCampos( this, "AM" );

	private ListaCampos lcUsuario = new ListaCampos( this, "" );

	private ListaCampos lcCC = new ListaCampos( this, "CC" );

	private ListaCampos lcProd = new ListaCampos( this, "PD" );

	boolean bAprovaParcial = false;

	boolean bExpede = false;

	boolean bAprova = false;

	private Vector<?> vSitSol = new Vector<Object>();
	
	private DAOGestaoSol daocons = null;

	public FGestaoSol() {

		super( false );
		setTitulo( "Gestão de Solicitações de Compra" );
		setAtribos( 10, 10, 795, 480 );

		btGerarSol.setToolTipText( "Criar solicitação sumarizada" );
		btTudo.setToolTipText( "Selecionar tudo" );
		btNada.setToolTipText( "Limpar seleção" );
		btGerarSol.addActionListener( this );
		btTudo.addActionListener( this );
		btNada.addActionListener( this );
		btGerarSol.addActionListener( this );

		txtDtIni.setRequerido( true );
		txtDtFim.setRequerido( true );

		txtCodAlmoxarife.setNomeCampo( "CodAlmox" );
		txtCodAlmoxarife.setFK( true );

		lcAlmox.add( new GuardaCampo( txtCodAlmoxarife, "CodAlmox", "Cód.almox.", ListaCampos.DB_PK, null, false ) );
		lcAlmox.add( new GuardaCampo( txtDescAlmoxarife, "DescAlmox", "Desc.almox;", ListaCampos.DB_SI, null, false ) );
		lcAlmox.setQueryCommit( false );
		lcAlmox.setReadOnly( true );

		txtDescAlmoxarife.setSoLeitura( true );
		txtCodAlmoxarife.setTabelaExterna( lcAlmox, null );
		lcAlmox.montaSql( false, "ALMOX", "EQ" );

		txtCodProd.setNomeCampo( "CodProd" );
		txtCodProd.setFK( true );

		lcProd.add( new GuardaCampo( txtCodProd, "CodProd", "Cód.prod.", ListaCampos.DB_PK, null, false ) );
		lcProd.add( new GuardaCampo( txtDescProd, "DescProd", "Descrição do produto", ListaCampos.DB_SI, null, false ) );
		lcProd.add( new GuardaCampo( txtRefProd, "RefProd", "Referência", ListaCampos.DB_SI, null, false ) );
		lcProd.setQueryCommit( false );
		lcProd.setReadOnly( true );

		txtDescProd.setSoLeitura( true );
		txtRefProd.setSoLeitura( true );
		txtCodProd.setTabelaExterna( lcProd, null );
		lcProd.montaSql( false, "PRODUTO", "EQ" );

		txtCodUsu.setNomeCampo( "IDUSU" );
		txtCodUsu.setFK( true );

		lcUsuario.add( new GuardaCampo( txtCodUsu, "IDUSU", "ID usuario", ListaCampos.DB_PK, null, false ) );
		lcUsuario.add( new GuardaCampo( txtNomeUsu, "NOMEUSU", "Nome do usuario", ListaCampos.DB_SI, null, false ) );
		lcUsuario.setQueryCommit( false );
		lcUsuario.setReadOnly( true );

		txtNomeUsu.setSoLeitura( true );
		txtCodUsu.setTabelaExterna( lcUsuario, null );
		lcUsuario.montaSql( false, "USUARIO", "SG" );

		lcCC.add( new GuardaCampo( txtCodCC, "CodCC", "Cód.cc.", ListaCampos.DB_PK, false ) );
		lcCC.add( new GuardaCampo( txtAnoCC, "AnoCC", "Ano.cc.", ListaCampos.DB_PK, false ) );
		lcCC.add( new GuardaCampo( txtDescCC, "DescCC", "Descrição do centro de custo", ListaCampos.DB_SI, false ) );
		lcCC.setReadOnly( true );
		lcCC.setQueryCommit( false );
		lcCC.montaSql( false, "CC", "FN" );
		txtCodCC.setTabelaExterna( lcCC, null );
		txtCodCC.setFK( true );
		txtCodCC.setNomeCampo( "CodCC" );

		Container c = getTela();
		c.add( pnCli, BorderLayout.NORTH );
		c.add( pnDetalhe, BorderLayout.CENTER );
		c.add( pnRod, BorderLayout.SOUTH );
		
		pnCli.add( pinCab );
		pnDetalhe.add( spnTab, BorderLayout.CENTER );
		pnDetalhe.add( pinBarraFerramentas, BorderLayout.EAST );
		btGerarSol.setPreferredSize( new Dimension ( 30, 30 ) );
		pinBarraFerramentas.adic( btTudo,  3, 10, 30, 30 );
		pinBarraFerramentas.adic( btNada,  3, 40, 30, 30 );
		pinBarraFerramentas.adic( btGerarSol,  3, 70, 30, 30 );
		
		
		btSair.setPreferredSize( new Dimension( 100, 30 ) );

		pnLegenda.add( new JLabelPad( "Cancelada", imgCancelada, SwingConstants.CENTER ) );
		pnLegenda.add( new JLabelPad( "Aprovada", imgAprovada, SwingConstants.CENTER ) );
		pnLegenda.add( new JLabelPad( "Em Cotação", imgExpedida, SwingConstants.CENTER ) );
		pnLegenda.add( new JLabelPad( "Pendente", imgPendente, SwingConstants.CENTER ) );
		

		pnRod.add( pnLegenda );
		pnRod.add( btSair, BorderLayout.EAST );

		pinCab.adic( new JLabelPad( "Período:" ), 7, 5, 50, 20 );
		pinCab.adic( txtDtIni, 7, 25, 95, 20 );
		pinCab.adic( new JLabelPad( "Até" ), 111, 25, 27, 20 );
		pinCab.adic( txtDtFim, 139, 25, 95, 20 );

		pinCab.adic( new JLabelPad( "Cód.usu." ), 237, 5, 70, 20 );
		pinCab.adic( txtCodUsu, 237, 25, 80, 20 );
		pinCab.adic( new JLabelPad( "Nome do usuário" ), 320, 5, 153, 20 );
		pinCab.adic( txtNomeUsu, 320, 25, 163, 20 );

		pinCab.adic( new JLabelPad( "Cód.prod." ), 7, 45, 80, 20 );
		pinCab.adic( txtCodProd, 7, 65, 80, 20 );
		pinCab.adic( new JLabelPad( "Descrição do produto" ), 90, 45, 200, 20 );
		pinCab.adic( txtDescProd, 90, 65, 200, 20 );

		pinCab.adic( new JLabelPad( "Cód.c.c." ), 7, 85, 70, 20 );
		pinCab.adic( txtCodCC, 7, 105, 140, 20 );
		pinCab.adic( new JLabelPad( "Centro de custo" ), 150, 85, 410, 20 );
		pinCab.adic( txtDescCC, 150, 105, 180, 20 );

		pinCab.adic( btBusca, 352, 57, 130, 30 );
		pinCab.adic( btPrevimp, 352, 93, 130, 30 );

		txtDtIni.setVlrDate( new Date() );
		txtDtFim.setVlrDate( new Date() );

		montaGrid();

		btBusca.addActionListener( this );
		btPrevimp.addActionListener( this );

		tab.addMouseListener( new MouseAdapter() {

			public void mouseClicked( MouseEvent mevt ) {

				if ( mevt.getSource() == tab && mevt.getClickCount() == 2 )
					abreRma();
			}
		} );
		btSair.addActionListener( this );

	}
	
	private void montaGrid(){
		
		tab.adicColuna( "Sit." );// STATUS DA SOLICITAÇÃO
		tab.adicColuna( "" );// IMGCOLUNA
		tab.adicColuna( "Sel." );// SEL
		tab.adicColuna( "Cód.prod." );// CODPROD
		tab.adicColuna( "Ref.prod" );// REFPROD
		tab.adicColuna( "Descrição do produto" );// DESCPROD
		tab.adicColuna( "Qt. requerida" );// QTDITSOL
		tab.adicColuna( "Qt. aprovada" );// QTDAPROVITSOL
		tab.adicColuna( "Cód.sol." ); // CODSOL
		tab.adicColuna( "Item sol." ); // Iten da solicitação
		tab.adicColuna( "Saldo" );// SLDPROD

		tab.setTamColuna( 12, GRID_SOL.STATUSITSOL.ordinal() );
		tab.setTamColuna( 12, GRID_SOL.IMGCOLUNA.ordinal() );
		tab.setTamColuna( 35, GRID_SOL.SEL.ordinal() );
		tab.setTamColuna( 70, GRID_SOL.CODPROD.ordinal() );
		tab.setTamColuna( 70, GRID_SOL.REFPROD.ordinal() );
		tab.setTamColuna( 280, GRID_SOL.DESCPROD.ordinal() );
		tab.setTamColuna( 90, GRID_SOL.QTDITSOL.ordinal() );
		tab.setTamColuna( 90, GRID_SOL.QTDAPROVITSOL.ordinal() );
		tab.setTamColuna( 70, GRID_SOL.CODSOL.ordinal() );
		tab.setTamColuna( 70, GRID_SOL.CODITSOL.ordinal() );
		tab.setTamColuna( 80, GRID_SOL.SLDPROD.ordinal() );

		tab.setColunaEditavel( GRID_SOL.SEL.ordinal() , true );
		
	}

	private void habCampos() {

		getAprova();
		if ( !bExpede ) {
			if ( bAprova ) {
				if ( bAprovaParcial ) {
					txtCodCC.setVlrString( Aplicativo.strCodCCUsu );
					txtAnoCC.setVlrString( Aplicativo.strAnoCCUsu );
					txtCodCC.setNaoEditavel( true );
					lcUsuario.setWhereAdic( "CODCC='" + Aplicativo.strCodCCUsu + "' AND ANOCC=" + Aplicativo.strAnoCCUsu );
				}
				else {
					txtCodCC.setNaoEditavel( false );

				}
				txtCodUsu.setNaoEditavel( false );
			}
			else {
				txtCodUsu.setVlrString( Aplicativo.strUsuario );
				txtCodCC.setVlrString( Aplicativo.strCodCCUsu );
				txtAnoCC.setVlrString( Aplicativo.strAnoCCUsu );

				txtCodUsu.setNaoEditavel( true );
				txtCodCC.setNaoEditavel( true );
				lcUsuario.carregaDados();
				lcCC.carregaDados();
			}
		}
	}

	/**
	 * Carrega os valores para a tabela de consulta. Este método é executado após carregar o ListaCampos da tabela.
	 */
	private void loadSolicitacao() {
		
		try {
			Vector<Vector<Object>> datavector = daocons.loadSolicitacao( imgColuna, imgAprovada, txtCodProd.getVlrInteger(), 
					txtDtIni.getVlrDate(), 	txtDtFim.getVlrDate(),
					Aplicativo.iCodEmp, ListaCampos.getMasterFilial( "EQALMOX" ), txtCodAlmoxarife.getVlrInteger(),
					Aplicativo.iCodEmp,	ListaCampos.getMasterFilial( "FNCC" ), txtAnoCC.getVlrInteger(), txtCodCC.getVlrString(), 
					Aplicativo.iCodEmp, ListaCampos.getMasterFilial("SGUSUARIO" ),Aplicativo.strUsuario );
			
			tab.setDataVector( datavector );
			
		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro carregando grid de contratos !\b" + err.getMessage() );
			err.printStackTrace();
		}
		
	}

	private void imprimir( boolean bVisualizar ) {

		ImprimeOS imp = new ImprimeOS( "", con );
		int linPag = imp.verifLinPag() - 1;
		BigDecimal bTotalLiq = new BigDecimal( "0" );
		boolean bImpCot = false;

		try {
			imp.limpaPags();
			for ( int iLin = 0; iLin < tab.getNumLinhas(); iLin++ ) {
				if ( imp.pRow() == 0 ) {
					imp.montaCab();
					imp.setTitulo( "Relatório de Requisições de material" );
					imp.addSubTitulo( "Relatório de Requisições de material" );
					imp.impCab( 136, true );
					// imp.say(imp.pRow()+1,0,""+imp.comprimido());
					imp.say( imp.pRow() + 0, 0, "| Rma." );
					imp.say( imp.pRow() + 0, 15, "| Emissão" );
					imp.say( imp.pRow() + 0, 29, "| Situação" );
					imp.say( imp.pRow() + 0, 45, "| Motivo." );
					imp.say( imp.pRow() + 0, 135, "|" );
					imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );

					if ( bImpCot ) {
						imp.say( imp.pRow() + 0, 0, "| Nro. Pedido" );
						imp.say( imp.pRow() + 0, 15, "| Nro. Nota" );
						imp.say( imp.pRow() + 0, 29, "| Data Fat." );
						imp.say( imp.pRow() + 0, 41, "| " );
						imp.say( imp.pRow() + 0, 56, "| " );
						imp.say( imp.pRow() + 0, 87, "| Vlr. Item Fat." );
						imp.say( imp.pRow() + 0, 105, "| " );
						imp.say( imp.pRow() + 0, 124, "| " );
						imp.say( imp.pRow() + 0, 135, "|" );
						imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );

					}

					imp.say( imp.pRow() + 0, 0, "|" + StringFunctions.replicate( "-", 133 ) + "|" );

				}

				imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
				imp.say( imp.pRow() + 0, 0, "|" + tab.getValor( iLin, 1 ) );
				imp.say( imp.pRow() + 0, 15, "| " + tab.getValor( iLin, 2 ) );
				imp.say( imp.pRow() + 0, 29, "| " + vSitSol.elementAt( iLin ).toString() );
				String sMotivo = "" + tab.getValor( iLin, 3 );
				imp.say( imp.pRow() + 0, 45, "| " + sMotivo.substring( 0, sMotivo.length() > 89 ? 89 : sMotivo.length() ).trim() );
				imp.say( imp.pRow() + 0, 135, "| " );

				if ( bImpCot ) {
					imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
					imp.say( imp.pRow() + 0, 2, "|" + tab.getValor( iLin, 2 ) );
					imp.say( imp.pRow() + 0, 15, "|" + tab.getValor( iLin, 3 ) );
					imp.say( imp.pRow() + 0, 29, "|" );
					imp.say( imp.pRow() + 0, 41, "|" );
					imp.say( imp.pRow() + 0, 56, "|" );
					imp.say( imp.pRow() + 0, 87, "|" + tab.getValor( iLin, 12 ) );
					imp.say( imp.pRow() + 0, 105, "|" );
					imp.say( imp.pRow() + 0, 124, "|" );
					imp.say( imp.pRow() + 0, 135, "|" );
				}

				if ( tab.getValor( iLin, 9 ) != null ) {
					bTotalLiq = bTotalLiq.add( new BigDecimal( Funcoes.strCurrencyToDouble( "" + tab.getValor( iLin, 9 ) ) ) );
				}

				if ( imp.pRow() >= linPag ) {
					imp.incPags();
					imp.eject();
				}
			}

			imp.say( imp.pRow() + 1, 0, "+" + StringFunctions.replicate( "-", 133 ) + "+" );
			imp.eject();

			imp.fechaGravacao();

			con.commit();

		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro consulta tabela de orçamentos!\n" + err.getMessage(), true, con, err );
		}

		if ( bVisualizar ) {
			imp.preview( this );
		}
		else {
			imp.print();
		}
	}

	private void abreRma() {

		int iRma = ( (Integer) tab.getValor( tab.getLinhaSel(), 1 ) ).intValue();
		if ( fPrim.temTela( "Requisição de material" ) == false ) {
			FRma tela = new FRma();
			fPrim.criatela( "Requisição de material", tela, con );
			tela.exec( iRma );
		}
	}

	private void getAprova() {

		String sSQL = "SELECT ANOCC,CODCC,CODEMPCC,CODFILIALCC,APROVRMAUSU,ALMOXARIFEUSU " + "FROM SGUSUARIO WHERE CODEMP=? AND CODFILIAL=? " + "AND IDUSU=?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {

			ps = con.prepareStatement( sSQL );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "SGUSUARIO" ) );
			ps.setString( 3, Aplicativo.strUsuario );
			rs = ps.executeQuery();
			if ( rs.next() ) {
				String sAprova = rs.getString( "APROVRMAUSU" );
				String sExpede = rs.getString( "ALMOXARIFEUSU" );
				if ( sAprova != null ) {
					if ( !sAprova.equals( "ND" ) ) {
						if ( sAprova.equals( "TD" ) )
							bAprova = true;
						else if ( ( Aplicativo.strCodCCUsu.equals( rs.getString( "CODCC" ) ) ) && ( Aplicativo.iCodEmp == rs.getInt( "CODEMPCC" ) ) && ( ListaCampos.getMasterFilial( "FNCC" ) == rs.getInt( "CODFILIALCC" ) ) && ( sAprova.equals( "CC" ) ) ) {
							bAprova = true;
							bAprovaParcial = true;
						}
					}
				}
				if ( sExpede != null ) {
					if ( sExpede.equals( "S" ) ) {
						bExpede = true;
					}
					else {
						bExpede = false;
					}
				}
			}
			con.commit();

		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro ao carregar a tabela PREFERE1!\n" + err.getMessage(), true, con, err );
		}
	}

	public void criaCotacao( int codprod ) {

		String sSQLautoincrement = "SELECT coalesce(MAX(SS.CODSUMSOL) + 1, 1) AS CODSUMSOL FROM cpsumsol SS WHERE SS.CODEMP=? AND SS.CODFILIAL=?";

		String sSQLsumsol = "INSERT INTO CPSUMSOL " + "(CODEMP, CODFILIAL, CODSUMSOL, CODEMPPD, CODFILIALPD, CODPROD, REFPROD, QTDITSOL, QTDAPROVITSOL, SITITSOL, SITAPROVITSOL) " + "SELECT ?, ?, ?, " + "IT.CODEMPPD, IT.CODFILIALPD, IT.CODPROD,IT.REFPROD, "
				+ "SUM(IT.QTDITSOL), SUM(IT.QTDAPROVITSOL), 'PE', 'PE' " + "FROM CPSOLICITACAO O, CPITSOLICITACAO IT " + "WHERE O.CODEMP=IT.CODEMP AND O.CODFILIAL=IT.CODFILIAL AND O.CODSOL=IT.CODSOL " + "AND CODEMPPD = ? AND CODFILIALPD = ? AND CODPROD = ? "
				+ "group BY IT.CODEMPPD, IT.CODFILIALPD, IT.CODPROD, IT.REFPROD";

		String sSQLitsumsol = "INSERT INTO CPITSUMSOL " + "(CODEMP, CODFILIAL, CODSUMSOL, CODEMPSC, CODFILIALSC, CODSOL, CODITSOL) " + "SELECT ?, ?, ?, IT.CODEMP, IT.CODFILIAL, IT.CODSOL, IT.CODITSOL " + "FROM CPSOLICITACAO O, CPITSOLICITACAO IT "
				+ "WHERE O.CODEMP=IT.CODEMP AND O.CODFILIAL=IT.CODFILIAL AND O.CODSOL=IT.CODSOL " + "AND CODEMPPD = ? AND CODFILIALPD = ? AND CODPROD = ?";

		int codsumsol = 0;
		try {
			PreparedStatement ps = con.prepareStatement( sSQLautoincrement );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, Aplicativo.iCodFilial );

			ResultSet rs = ps.executeQuery();

			if ( rs.next() ) {
				codsumsol = rs.getInt( "CODSUMSOL" );
			}
		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro ao atualizar a tabela CPSUMSOL!\n" + err.getMessage(), true, con, err );
		}

		try {
			PreparedStatement ps2 = con.prepareStatement( sSQLsumsol );

			ps2.setInt( 1, Aplicativo.iCodEmp );
			ps2.setInt( 2, Aplicativo.iCodFilial );
			ps2.setInt( 3, codsumsol );
			ps2.setInt( 4, Aplicativo.iCodEmp );
			ps2.setInt( 5, Aplicativo.iCodFilial );
			ps2.setInt( 6, codprod );

			ps2.execute();

			con.commit();
		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro ao atualizar a tabela SUMSOL!\n" + err.getMessage(), true, con, err );
		}

		try {
			PreparedStatement ps3 = con.prepareStatement( sSQLitsumsol );

			ps3.setInt( 1, Aplicativo.iCodEmp );
			ps3.setInt( 2, Aplicativo.iCodFilial );
			ps3.setInt( 3, codsumsol );
			ps3.setInt( 4, Aplicativo.iCodEmp );
			ps3.setInt( 5, Aplicativo.iCodFilial );
			ps3.setInt( 6, codprod );

			ps3.execute();

			con.commit();
		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro ao atualizar a tabela ITSUMSOL!\n" + err.getMessage(), true, con, err );
		}
	}
	
	private void carregaTudo( JTablePad tb ) {

		for ( int i = 0; i < tb.getNumLinhas(); i++ ) {
			tb.setValor( new Boolean( true ), i, GRID_SOL.SEL.ordinal() );
		}
	}

	private void carregaNada( JTablePad tb ) {

		for ( int i = 0; i < tb.getNumLinhas(); i++ ) {
			tb.setValor( new Boolean( false ), i, GRID_SOL.SEL.ordinal() );
		}
	}

	public void createSol() {
		Vector<Integer> solSel = new Vector<Integer>();
		if ( tab.getRowCount() <= 0 ) {
			Funcoes.mensagemInforma( this, "Não há nenhum ítem para sumarização !" );
			return;
		} 
		for (Vector<Object> row: tab.getDataVector()) {
			if ( (Boolean) row.elementAt(GRID_SOL.SEL.ordinal()) ) {
				solSel.addElement( (Integer) row.elementAt( GRID_SOL.CODSOL.ordinal() ) ); 
			}
		}
		if (solSel.size()==0) {
			Funcoes.mensagemInforma( this, "Selecione as solicitações a sumarizar !" );
			return;
		}

		
	}
	
	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == btGerarSol ) {
			createSol();
		}
		else if ( evt.getSource() == btTudo ) {
			carregaTudo( tab );
		}
		else if ( evt.getSource() == btNada ) {
			carregaNada( tab );
		} 
		else if ( evt.getSource() == btSair ) {
			dispose();
		} else if ( evt.getSource() == btBusca ) {
			if ( txtDtIni.getVlrString().length() < 10 )
				Funcoes.mensagemInforma( this, "Digite a data inicial!" );
			else if ( txtDtFim.getVlrString().length() < 10 )
				Funcoes.mensagemInforma( this, "Digite a data final!" );
			else
				loadSolicitacao();
			if ( evt.getSource() == btPrevimp ) {
				imprimir( true );
			}
		}
		if ( evt.getSource() == btPrevimp ) {
			imprimir( true );
		}

	}

	private int buscaVlrPadrao() {

		int iRet = 0;
		String sSQL = "SELECT ANOCENTROCUSTO FROM SGPREFERE1 WHERE CODEMP=? AND CODFILIAL=?";
		try {
			PreparedStatement ps = con.prepareStatement( sSQL );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "SGPREFERE1" ) );
			ResultSet rs = ps.executeQuery();
			if ( rs.next() )
				iRet = rs.getInt( "ANOCENTROCUSTO" );
			rs.close();
			ps.close();
		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro ao buscar o ano-base para o centro de custo.\n" + err.getMessage() );
		}

		return iRet;
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		lcAlmox.setConexao( cn );
		lcProd.setConexao( cn );
		lcUsuario.setConexao( cn );
		lcCC.setConexao( cn );
		lcCC.setWhereAdic( "NIVELCC=10 AND ANOCC=" + buscaVlrPadrao() );
		habCampos();
		
		daocons = new DAOGestaoSol(cn);
	}
}
