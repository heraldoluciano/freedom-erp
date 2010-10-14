/**
 * @version 07/07/2008 <BR>
 * @author Setpoint Informática Ltda.<BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.pcp <BR>
 *         Classe: @(#)FAgendProd.java <BR>
 * 
 *         Este arquivo é parte do sistema Freedom-ERP, o Freedom-ERP é um software livre; você pode redistribui-lo e/ou <BR>
 *         modifica-lo dentro dos termos da Licença Pública Geral GNU como publicada pela Fundação do Software Livre (FSF); <BR>
 *         na versão 2 da Licença, ou (na sua opnião) qualquer versão. <BR>
 *         Este programa é distribuido na esperança que possa ser util, mas SEM NENHUMA GARANTIA; <BR>
 *         sem uma garantia implicita de ADEQUAÇÂO a qualquer MERCADO ou APLICAÇÃO EM PARTICULAR. <BR>
 *         Veja a Licença Pública Geral GNU para maiores detalhes. <BR>
 *         Você deve ter recebido uma cópia da Licença Pública Geral GNU junto com este programa, se não, <BR>
 *         escreva para a Fundação do Software Livre(FSF) Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA <BR>
 * <BR>
 * 
 *         Comentários sobre a classe...
 */
package org.freedom.modulos.pcp.view.frame.utility;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import org.freedom.bmps.Icone;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JButtonPad;
import org.freedom.library.swing.component.JCheckBoxPad;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JPanelPad;
import org.freedom.library.swing.component.JRadioGroup;
import org.freedom.library.swing.component.JTablePad;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FFilho;
import org.freedom.modulos.gms.view.frame.crud.plain.FSecaoProd;
import org.freedom.modulos.pcp.view.frame.crud.detail.FOP;

public class FAcompanhaProd extends FFilho implements ActionListener, MouseListener {

	private static final long serialVersionUID = 1L;

	private enum ecolAcompanhamentos {

		SITOP, DTEMITOP, DTFABROP, CODOP, SEQOP, DESCEST, QTDSUG, QTDPREV, QTDTOTAL, TEMPOTOTAL, TOTALFASE

	};

	private JPanelPad pinCab = new JPanelPad( 0, 125 );

	private JPanelPad pnCab = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad pinRod = new JPanelPad( 685, 39 );

	private JPanelPad pnBotoes = new JPanelPad( 200, 200 );

	private JTextFieldPad txtDataini = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDatafim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JButtonPad btFiltrar = new JButtonPad( Icone.novo( "btExecuta.gif" ) );

	private JButtonPad btNovaOp = new JButtonPad( Icone.novo( "btNovo.gif" ) );
	
	private JButtonPad btImprimir = new JButtonPad( Icone.novo( "btPrevimp.gif" ) );

	private JRadioGroup<?, ?> rgFiltro = null;

	private Vector<String> vValsData = new Vector<String>();

	private Vector<String> vLabsData = new Vector<String>();

	private JCheckBoxPad cbCancelada = new JCheckBoxPad( "Cancelada", "S", "N" );

	private JCheckBoxPad cbPendente = new JCheckBoxPad( "Pendente", "S", "N" );

	private JCheckBoxPad cbFinalizada = new JCheckBoxPad( "Finalizada", "S", "N" );

	private JCheckBoxPad cbAtrasada = new JCheckBoxPad( "Atrasada", "S", "N" );

	private JCheckBoxPad cbPrincipal = new JCheckBoxPad( "Principal", "S", "N" );

	private JCheckBoxPad cbRelacionada = new JCheckBoxPad( "Relacionadas", "S", "N" );

	private JCheckBoxPad cbBloqueada = new JCheckBoxPad( "Bloqueada", "S", "N" );

	private Vector<String> vValsStatus = new Vector<String>();

	private Vector<String> vLabsStatus = new Vector<String>();

	private JTablePad tab = new JTablePad();

	private JScrollPane spnTab = new JScrollPane( tab );

	private JLabelPad lbTxtPendente = new JLabelPad( "Pendente" );

	private JLabelPad lbTxtFinalizada = new JLabelPad( "Finalizada" );

	private JLabelPad lbTxtAtrasado = new JLabelPad( "Atrasada" );

	private JLabelPad lbTxtCancelado = new JLabelPad( "Cancelada" );

	private JLabelPad lbTxtBloqueado = new JLabelPad( "Bloqueada" );

	private ImageIcon imgPendente = Icone.novo( "clIndisponivelParc.gif" );

	private ImageIcon imgFinalizada = Icone.novo( "clEfetivado.gif" );

	private ImageIcon imgAtrasado = Icone.novo( "clVencido.gif" );

	private ImageIcon imgCancelado = Icone.novo( "clCancelado.gif" );

	private ImageIcon imgBloqueado = Icone.novo( "clOpBloqueada.gif" );

	private JLabelPad lbImgPendente = new JLabelPad( imgPendente );

	private JLabelPad lbImgFinalizada = new JLabelPad( imgFinalizada );

	private JLabelPad lbImgAtrasado = new JLabelPad( imgAtrasado );

	private JLabelPad lbImgCancelado = new JLabelPad( imgCancelado );

	private JLabelPad lbImgBloqueada = new JLabelPad( imgBloqueado );

	private ImageIcon imgStatus = null;

	private Font fontLegenda = new Font( "Arial", Font.PLAIN, 9 );

	private Color corLegenda = new Color( 120, 120, 120 );
	
	private ListaCampos lcSecao = new ListaCampos( this );
	
	private JTextFieldPad txtCodSecao = new JTextFieldPad( JTextFieldPad.TP_STRING, 13, 0 );
	
	private JTextFieldFK txtDescSecao = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );


	FOP f;

	public FAcompanhaProd() {

		super( true );
		setAtribos( 50, 50, 900, 470 );

		montaTela();

	}

	private void montaTela() {

		adicBotaoSair();

		getTela().add( pnCab, BorderLayout.CENTER );
		pnCab.add( pinCab, BorderLayout.NORTH );
		pnCab.add( spnTab, BorderLayout.CENTER );

		pnRod.add( pnBotoes, BorderLayout.CENTER );

		JLabelPad lbLinha = new JLabelPad();
		lbLinha.setBorder( BorderFactory.createEtchedBorder() );
		JLabelPad lbPeriodo = new JLabelPad( "Periodo:", SwingConstants.CENTER );
		lbPeriodo.setOpaque( true );

		JLabelPad lbLinhaStatus = new JLabelPad();
		lbLinhaStatus.setBorder( BorderFactory.createEtchedBorder() );
		JLabelPad lbStatus1 = new JLabelPad( "Status:" );
		lbStatus1.setOpaque( true );

		vValsData.addElement( "F" );
		vValsData.addElement( "E" );
		vLabsData.addElement( "Fabricação" );
		vLabsData.addElement( "Emissão" );

		rgFiltro = new JRadioGroup<String, String>( 2, 1, vLabsData, vValsData );
		rgFiltro.setVlrString( "F" );

		pinCab.adic( lbPeriodo, 0, 0, 80, 20 );
		pinCab.adic( lbLinha, 5, 20, 280, 50 );
		pinCab.adic( lbStatus1, 445, 0, 80, 20 );
		pinCab.adic( lbLinhaStatus, 445, 20, 380, 50 );
		pinCab.adic( new JLabelPad( "De:" ), 10, 35, 30, 20 );
		pinCab.adic( txtDataini, 35, 35, 97, 20 );
		pinCab.adic( new JLabelPad( "Até:" ), 140, 35, 37, 20 );
		pinCab.adic( txtDatafim, 165, 35, 100, 20 );
		pinCab.adic( cbCancelada, 460, 25, 90, 20 );
		pinCab.adic( cbFinalizada, 460, 45, 90, 20 );
		pinCab.adic( cbPendente, 548, 25, 90, 20 );
		pinCab.adic( cbAtrasada, 548, 45, 90, 20 );
		pinCab.adic( cbPrincipal, 641, 25, 90, 20 );
		pinCab.adic( cbRelacionada, 641, 45, 105, 20 );
		pinCab.adic( cbBloqueada, 735, 25, 85, 20 );
		
		pinCab.adic( txtCodSecao, 7, 90, 80, 20, "Cód.Seção" );
		pinCab.adic( txtDescSecao, 90, 90, 340, 20, "Descrição da seção" );

		pinCab.adic( new JLabelPad( "Filtrar por: " ), 290, 1, 80, 20 );
		pinCab.adic( rgFiltro, 290, 20, 150, 50 );
		pinCab.adic( btFiltrar, 835, 20, 40, 50 );

		lbTxtPendente.setFont( fontLegenda );
		lbTxtFinalizada.setFont( fontLegenda );
		lbTxtCancelado.setFont( fontLegenda );
		lbTxtAtrasado.setFont( fontLegenda );
		lbTxtPendente.setForeground( corLegenda );
		lbTxtFinalizada.setForeground( corLegenda );
		lbTxtAtrasado.setForeground( corLegenda );
		lbTxtCancelado.setForeground( corLegenda );
		lbTxtBloqueado.setFont( fontLegenda );
		lbTxtBloqueado.setForeground( corLegenda );

		pnBotoes.adic( btNovaOp, 10, 0, 30, 25 );
		pnBotoes.adic( btImprimir, 41, 0, 30, 25 );

		btNovaOp.setToolTipText( "Nova ordem de produção" );
		btImprimir.setToolTipText( "Imprimir relação" );
		
		pnBotoes.adic( lbImgPendente, 160, 4, 20, 20 );
		pnBotoes.adic( lbTxtPendente, 180, 4, 80, 20 );
		pnBotoes.adic( lbImgAtrasado, 240, 4, 20, 20 );
		pnBotoes.adic( lbTxtAtrasado, 260, 4, 90, 20 );
		pnBotoes.adic( lbImgCancelado, 320, 4, 20, 20 );
		pnBotoes.adic( lbTxtCancelado, 340, 4, 90, 20 );
		pnBotoes.adic( lbTxtFinalizada, 420, 4, 80, 20 );
		pnBotoes.adic( lbImgFinalizada, 400, 4, 20, 20 );
		pnBotoes.adic( lbTxtBloqueado, 500, 4, 80, 20 );
		pnBotoes.adic( lbImgBloqueada, 480, 4, 20, 20 );

		
		tab.adicColuna( "" );
		tab.adicColuna( "Emissão" );
		tab.adicColuna( "Fabricação" );
		tab.adicColuna( "O.P." );
		tab.adicColuna( "Seq." );
		tab.adicColuna( "Descrição do produto" );
		tab.adicColuna( "Qtd sug." );
		tab.adicColuna( "Qtd prev." );
		tab.adicColuna( "Qtd tot." );
		tab.adicColuna( "Concl." );
		tab.adicColuna( "Fase" );

		tab.setTamColuna( 10, ecolAcompanhamentos.SITOP.ordinal() );
		tab.setTamColuna( 68, ecolAcompanhamentos.DTEMITOP.ordinal() );
		tab.setTamColuna( 68, ecolAcompanhamentos.DTFABROP.ordinal() );
		tab.setTamColuna( 45, ecolAcompanhamentos.CODOP.ordinal() );
		tab.setTamColuna( 28, ecolAcompanhamentos.SEQOP.ordinal() );
		tab.setTamColuna( 370, ecolAcompanhamentos.DESCEST.ordinal() );
		tab.setTamColuna( 62, ecolAcompanhamentos.QTDSUG.ordinal() );
		tab.setTamColuna( 62, ecolAcompanhamentos.QTDPREV.ordinal() );
		tab.setTamColuna( 62, ecolAcompanhamentos.QTDTOTAL.ordinal() );
		tab.setTamColuna( 40, ecolAcompanhamentos.TOTALFASE.ordinal() );
		tab.setTamColuna( 55, ecolAcompanhamentos.TEMPOTOTAL.ordinal() );

		Calendar cPeriodo = Calendar.getInstance();

		txtDataini.setVlrDate( cPeriodo.getTime() );

		cPeriodo.set( Calendar.DAY_OF_MONTH, cPeriodo.get( Calendar.DAY_OF_MONTH ) + 30 );

		txtDatafim.setVlrDate( cPeriodo.getTime() );

		txtDataini.setRequerido( true );
		txtDatafim.setRequerido( true );

		btFiltrar.addActionListener( this );
		btNovaOp.addActionListener( this );
		btImprimir.addActionListener( this );
		tab.addMouseListener( this );
		
		cbPendente.setVlrString( "S" );
		cbAtrasada.setVlrString( "S" );
		cbPrincipal.setVlrString( "S" );
		cbRelacionada.setVlrString( "S" );
		cbAtrasada.setVlrString( "S" );
		
		montaListaCampos();
	}

	private void montaListaCampos() {
		
		lcSecao.add( new GuardaCampo( txtCodSecao, "CodSecao", "Cód.Seção", ListaCampos.DB_PK, false ) );
		lcSecao.add( new GuardaCampo( txtDescSecao, "DescSecao", "Descrição da seção", ListaCampos.DB_SI, false ) );
		lcSecao.montaSql( false, "SECAO", "EQ" );
		txtCodSecao.setNomeCampo( "CodSecao" );
		txtCodSecao.setFK( true );
		lcSecao.setReadOnly( true );
		lcSecao.setQueryCommit( false );
		txtCodSecao.setTabelaExterna( lcSecao, FSecaoProd.class.getCanonicalName() );

		
	}
	
	private void montaGrid() {

		if ( txtDatafim.getVlrDate().before( txtDataini.getVlrDate() ) ) {

			Funcoes.mensagemInforma( this, "Data final maior que a data inicial!" );
			return;
		}

		StringBuffer sql = new StringBuffer();
		StringBuffer sWhere = new StringBuffer();
		StringBuffer sWhere2 = new StringBuffer();
		StringBuilder sOrderBy = new StringBuilder();
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean or = false;
		boolean order = false;
		String sData = "";

		if ( "S".equals( cbCancelada.getVlrString() ) ) {

			sWhere.append( ( or ? "OR" : "" ) + " SITOP='CA' " );
			or = true;
		}
		if ( "S".equals( cbFinalizada.getVlrString() ) ) {

			sWhere.append( ( or ? "OR" : "" ) + " SITOP='FN' " );
			or = true;
		}
		if ( "S".equals( cbAtrasada.getVlrString() ) ) {

			sWhere.append( ( or ? "OR" : "" ) + " (SITOP='PE' AND DTFABROP < CAST( 'NOW' AS DATE )) " );
			or = true;
		}
		if ( "S".equals( cbPendente.getVlrString() ) ) {

			sWhere.append( ( or ? "OR" : "" ) + "  SITOP='PE' " );
			or = true;
		}
		if ( "F".equals( rgFiltro.getVlrString() ) ) {
			sData = "DTFABROP";
		}
		else if ( "E".equals( rgFiltro.getVlrString() ) ) {
			sData = "DTEMITOP";
		}
		if ( "S".equals( cbPrincipal.getVlrString() ) && !"S".equals( cbRelacionada.getVlrString() ) ) {

			sWhere2.append( " AND SEQOP=0" );
		}
		if ( "S".equals( cbRelacionada.getVlrString() ) ) {

			sOrderBy.append( "AND SEQOP<>0 ORDER BY CODOP, SEQOP" );
			order = true;

		}
		if ( "S".equals( cbPrincipal.getVlrString() ) && "S".equals( cbRelacionada.getVlrString() ) ) {

			sOrderBy.append( order ? "" : "AND SEQOP<>0 ORDER BY CODOP, SEQOP" );
		}

		if ( "S".equals( cbBloqueada.getVlrString() ) ) {

			sWhere.append( ( or ? "OR" : "" ) + " SITOP='BL' " );
			or = true;
		}

		sql.append( "SELECT SITOP,DTEMITOP,DTFABROP,CODOP,SEQOP,DESCEST,CODPROD,REFPROD,CODSECAO, " );
		sql.append( "CAST( QTDSUG AS DECIMAL(15,2)) QTDSUG," );
		sql.append( "CAST( QTDPREV AS DECIMAL(15,2)) QTDPREV," );
		sql.append( "CAST( QTDFINAL AS DECIMAL(15,2)) QTDFINAL," );
		sql.append( "CAST((TEMPOFIN*100/( CASE WHEN COALESCE(TEMPOTOT,0)=0 THEN 1 ELSE TEMPOTOT END  )) AS DECIMAL (15,2)) TEMPO, " );
		sql.append( "FASEATUAL,TOTFASES FROM PPLISTAOPVW01 " );
		sql.append( "WHERE CODEMP=? AND CODFILIAL=? AND " + sData + " BETWEEN ? AND ? " );
		
		sql.append( or ? "AND" : "" );
		sql.append( sWhere.toString() );
		sql.append( sWhere2.toString() );
		
		if(txtCodSecao.getVlrString()!=null && !"".equals( txtCodSecao.getVlrString()) ) {
			
			sql.append( " AND CODSECAO = ? " );
			
		}
		
		
		sql.append( sOrderBy.toString() );
		System.out.println( sql.toString() );

		try {

			ps = con.prepareStatement( sql.toString() );
			
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "PPOP" ) );
			ps.setDate( 3, Funcoes.dateToSQLDate( txtDataini.getVlrDate() ) );
			ps.setDate( 4, Funcoes.dateToSQLDate( txtDatafim.getVlrDate() ) );
			
			if(txtCodSecao.getVlrString()!=null && !"".equals( txtCodSecao.getVlrString()) ) {
				
				ps.setInt( 5, txtCodSecao.getVlrInteger() );
				
			}
			
			rs = ps.executeQuery();

			tab.limpa();

			boolean atrasado = false;
			for ( int i = 0; rs.next(); i++ ) {

				Date dtfab = Funcoes.getDataPura( Funcoes.sqlDateToDate( rs.getDate( "DTFABROP" ) ) );
				Date dtatual = Funcoes.getDataPura( new Date() );

				atrasado = dtfab.before( dtatual );

				if ( rs.getString( "SITOP" ).equals( "PE" ) ) {

					if ( atrasado ) {
						imgStatus = imgAtrasado;
					}
					else {
						imgStatus = imgPendente;
					}
				}
				else if ( rs.getString( "SITOP" ).equals( "CA" ) ) {
					imgStatus = imgCancelado;
				}
				else if ( rs.getString( "SITOP" ).equals( "FN" ) ) {
					imgStatus = imgFinalizada;
				}
				else if ( rs.getString( "SITOP" ).equals( "BL" ) ) {
					imgStatus = imgBloqueado;
				}

				tab.adicLinha();
				tab.setValor( imgStatus, i, ecolAcompanhamentos.SITOP.ordinal() );
				tab.setValor( rs.getDate( "DTEMITOP" ), i, ecolAcompanhamentos.DTEMITOP.ordinal() );
				tab.setValor( rs.getDate( "DTFABROP" ), i, ecolAcompanhamentos.DTFABROP.ordinal() );
				tab.setValor( rs.getInt( "CODOP" ), i, ecolAcompanhamentos.CODOP.ordinal() );
				tab.setValor( rs.getInt( "SEQOP" ), i, ecolAcompanhamentos.SEQOP.ordinal() );
				tab.setValor( rs.getString( "DESCEST" ), i, ecolAcompanhamentos.DESCEST.ordinal() );
				tab.setValor( rs.getBigDecimal( "QTDSUG" ), i, ecolAcompanhamentos.QTDSUG.ordinal() );
				tab.setValor( rs.getBigDecimal( "QTDPREV" ), i, ecolAcompanhamentos.QTDPREV.ordinal() );
				tab.setValor( rs.getBigDecimal( "QTDFINAL" ), i, ecolAcompanhamentos.QTDTOTAL.ordinal() );
				tab.setValor( rs.getBigDecimal( "TEMPO" ) + "%", i, ecolAcompanhamentos.TEMPOTOTAL.ordinal() );
				tab.setValor( rs.getBigDecimal( "FASEATUAL" ) + "/" + rs.getBigDecimal( "TOTFASES" ), i, ecolAcompanhamentos.TOTALFASE.ordinal() );

			}

			con.commit();

		} catch ( Exception e ) {

			Funcoes.mensagemInforma( this, "Erro ao montar grid " + e.getMessage() );

			e.printStackTrace();
		}
	}

	public void actionPerformed( ActionEvent e ) {

		try {
			if ( e.getSource() == btFiltrar ) {
				montaGrid();
			}
			else if ( e.getSource() == btNovaOp ) {

				if ( Aplicativo.telaPrincipal.temTela( "Ordens de produção" ) == false ) {
					f = new FOP( true );
					f.setTelaPrim( Aplicativo.telaPrincipal );
					Aplicativo.telaPrincipal.criatela( "Ordens de produção", f, con );
				}
			}
			else if ( e.getSource() == btImprimir ) {
				
				// Implementar
				
			}
			
		} 
		catch ( Exception err ) {
			Funcoes.mensagemErro( null, "Erro ao abrir OP!\n", true, con, err );
		}
	}

	public void mouseClicked( MouseEvent mevt ) {

		JTablePad tabEv = (JTablePad) mevt.getSource();

		if ( mevt.getClickCount() == 2 ) {

			Integer codOp = (Integer) tab.getValor( tab.getLinhaSel(), ecolAcompanhamentos.CODOP.ordinal() );
			Integer seqOp = (Integer) tab.getValor( tab.getLinhaSel(), ecolAcompanhamentos.SEQOP.ordinal() );

			if ( tabEv == tab && tabEv.getLinhaSel() >= 0 ) {

				if ( Aplicativo.telaPrincipal.temTela( "Ordens de produção" ) == false ) {

					f = new FOP( codOp, seqOp );
					Aplicativo.telaPrincipal.criatela( "Ordens de produção", f, con );
				}
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
	
	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		lcSecao.setConexao( con );

	}


}


