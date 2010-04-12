/*
 * Projeto: Freedom
 * Pacote: org.freedom.modules.crm
 * Classe: @(#)FAtendimento.java
 * 
 * Este arquivo é parte do sistema Freedom-ERP, o Freedom-ERP é um software livre; você pode redistribui-lo e/ou <BR>
 * modifica-lo dentro dos termos da Licença Pública Geral GNU como publicada pela Fundação do Software Livre (FSF); <BR>
 * na versão 2 da Licença, ou (na sua opnião) qualquer versão. <BR>
 * Este programa é distribuido na esperança que possa ser  util, mas SEM NENHUMA GARANTIA; <BR>
 * sem uma garantia implicita de ADEQUAÇÂO a qualquer MERCADO ou APLICAÇÃO EM PARTICULAR. <BR>
 * Veja a Licença Pública Geral GNU para maiores detalhes. <BR>
 * Você deve ter recebido uma cópia da Licença Pública Geral GNU junto com este programa, se não, <BR>
 * escreva para a Fundação do Software Livre(FSF) Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA <BR> 
 */

package org.freedom.modulos.crm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.SpinnerDateModel;
import javax.swing.JSpinner.DateEditor;

import org.freedom.acao.JComboBoxEvent;
import org.freedom.acao.JComboBoxListener;
import org.freedom.bmps.Icone;
import org.freedom.funcoes.Funcoes;
import org.freedom.funcoes.FuncoesCRM;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.JButtonPad;
import org.freedom.library.swing.JComboBoxPad;
import org.freedom.library.swing.JLabelPad;
import org.freedom.library.swing.JPanelPad;
import org.freedom.library.swing.JTextAreaPad;
import org.freedom.library.swing.JTextFieldFK;
import org.freedom.library.swing.JTextFieldPad;
import org.freedom.library.swing.dialog.FFDialogo;
import org.freedom.library.swing.frame.Aplicativo;


/**
 * 
 * @author Setpoint Informática Ltda./Alex Rodrigues
 * @version 10/10/2009 - Alex Rodrigues
 */
public class DLNovoAtend extends FFDialogo implements JComboBoxListener, KeyListener{

	private static final long serialVersionUID = 1L;

	private JPanelPad pnCab = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 1, 1 ) );

	private JPanelPad pnBotoes = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JTextFieldPad txtCodCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtRazCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	private JTextFieldPad txtCodAtend = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldPad txtCodAtendo = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtNomeAtend = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	private JTextFieldPad txtDataAtendimento = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );
	
	private JTextFieldPad txtDataAtendimentoFin = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );
		
	private JTextFieldPad txtHoraini = new JTextFieldPad( JTextFieldPad.TP_TIME, 5, 0 );
	
	private JTextFieldPad txtHorafim = new JTextFieldPad( JTextFieldPad.TP_TIME, 5, 0 );
	
	private JTextFieldPad txtTipoAtendimento = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );
	
	private JTextFieldPad txtContr = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );
	
	private JTextFieldPad txtStatusAtendo = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );
	
	private JTextFieldPad txtitContr = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );
	
	private JTextFieldPad txtSetor = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );

	private JTextAreaPad txaDescAtend = new JTextAreaPad();

	private Vector<Integer> vValsTipo = new Vector<Integer>();

	private Vector<String> vLabsTipo = new Vector<String>();

	private JComboBoxPad cbTipo = new JComboBoxPad( vLabsTipo, vValsTipo, JComboBoxPad.TP_INTEGER, 8, 0 );
	
	private JComboBoxPad cbStatus = new JComboBoxPad( vLabsTipo, vValsTipo, JComboBoxPad.TP_STRING, 2, 0 );

	private Vector<Integer> vValsSetor = new Vector<Integer>();

	private Vector<String> vLabsSetor = new Vector<String>();

	private JComboBoxPad cbSetor = new JComboBoxPad( vLabsSetor, vValsSetor, JComboBoxPad.TP_INTEGER, 8, 0 );
	
	private Vector<Integer> vValsContr = new Vector<Integer>();

	private Vector<String> vLabsContr = new Vector<String>();

	private JComboBoxPad cbContr = new JComboBoxPad( vLabsContr, vValsContr, JComboBoxPad.TP_INTEGER, 8, 0 );
	
	private Vector<Integer> vValsitContr = new Vector<Integer>();

	private Vector<String> vLabsitContr = new Vector<String>();

	private JComboBoxPad cbitContr = new JComboBoxPad( vLabsitContr, vValsitContr, JComboBoxPad.TP_INTEGER, 8, 0 );

	private ListaCampos lcAtend = new ListaCampos( this, "AE" );
	
	private ListaCampos lcCli = new ListaCampos( this, "CL" );
	
	private ListaCampos lcAtendimento = new ListaCampos( this );

	private JLabelPad lbImg = new JLabelPad( Icone.novo( "bannerAtendimento.png" ) );

	private JButtonPad btMedida = new JButtonPad( Icone.novo( "btMedida.gif" ) );
	
	private JButtonPad btRun = new JButtonPad( Icone.novo( "btplay.png" ) );

	private String sPrefs[] = null;

	private int iDoc = 0;
	
	private boolean update = false;
	
	private boolean contando = false;
	
	private Thread contador = null;
	
	private JLabelPad lbContador = new JLabelPad();
	
	private JPanelPad pnGeral = new JPanelPad( new BorderLayout() );
	
	private JPanelPad pnTela = new JPanelPad( new BorderLayout() );
	
	private JPanelPad pnCampos = new JPanelPad( 500, 190 );
	
	private JPanelPad pnTxa = new JPanelPad( new GridLayout( 1, 1 ) );
	
	private String tipoatendo = null;
	
	private Integer codrec = null;
	
	private Integer nparcitrec = null;
	
    
	
		
	public DLNovoAtend( int iCodCli, Component cOrig, boolean isUpdate, DbConnection conn, int codatendo, int codatend, String tipoatendo ) {

		this( iCodCli, cOrig, conn, isUpdate, tipoatendo );

		update = isUpdate;

		txtCodAtendo.setVlrInteger( codatendo );

		lcAtendimento.carregaDados();
		cbStatus.setVlrString( txtStatusAtendo.getVlrString() );

		cbTipo.setVlrInteger( txtTipoAtendimento.getVlrInteger() );
		cbSetor.setVlrInteger( txtSetor.getVlrInteger() );
		cbContr.setVlrInteger( txtContr.getVlrInteger() );
		cbitContr.setVlrInteger( txtitContr.getVlrInteger() );

		if ( update ) {
			pnCampos.adic( new JLabelPad( "Status" ), 494, 130, 120, 20 );
			pnCampos.adic( cbStatus, 494, 150, 120, 20 );
		}
	}

	public DLNovoAtend( int iCodCli, Component cOrig, DbConnection conn, boolean isUpdate, String tipoatendo, Integer codrec, Integer nparcitrec ) {

		this( iCodCli, cOrig, conn, isUpdate, tipoatendo );

		this.codrec = codrec;
		this.nparcitrec = nparcitrec;

	}

	public DLNovoAtend( int iCodCli, Component cOrig, DbConnection conn, boolean isUpdate, String tipoatendo ) {

		super( cOrig );

		update = isUpdate;
		this.tipoatendo = tipoatendo;

		setTitulo( "Novo atendimento" );
		setAtribos( 640, 640 );

		montaListaCampos();

		btMedida.setPreferredSize( new Dimension( 30, 30 ) );

		pnCab.add( lbImg );

		c.add( pnCab, BorderLayout.NORTH );

		c.add( pnGeral );

		pnGeral.add( pnTela, BorderLayout.CENTER );

		pnTela.add( pnCampos, BorderLayout.NORTH );

		pnTela.add( pnTxa, BorderLayout.CENTER );

		pnTxa.setBorder( BorderFactory.createTitledBorder( "Detalhamento do atendimento" ) );
		pnTxa.add( new JScrollPane( txaDescAtend ) );

		txaDescAtend.setBorder( BorderFactory.createEtchedBorder( Color.RED, null ) );

		setPainel( pnCampos );

		adic( new JLabelPad( "Cód.Cliente" ), 7, 10, 80, 20 );
		adic( txtCodCli, 7, 30, 80, 20 );
		adic( new JLabelPad( "Razão Social do Cliente" ), 90, 10, 480, 20 );
		adic( txtRazCli, 90, 30, 524, 20 );
		adic( new JLabelPad( "Cód.Atend." ), 7, 50, 80, 20 );
		adic( txtCodAtend, 7, 70, 80, 20 );
		adic( new JLabelPad( "Nome do Atendente" ), 90, 50, 200, 20 );
		adic( txtNomeAtend, 90, 70, 200, 20 );
		adic( new JLabelPad( "Tipo de Atendimento" ), 293, 50, 198, 20 );
		adic( cbTipo, 293, 70, 198, 20 );
		adic( new JLabelPad( "Setor" ), 494, 50, 120, 20 );
		adic( cbSetor, 494, 70, 120, 20 );
		adic( new JLabelPad( "Início" ), 7, 130, 80, 20 );
		adic( txtDataAtendimento, 7, 150, 80, 20 );
		adic( txtHoraini, 90, 150, 53, 20 );
		adic( new JLabelPad( "Final" ), 146, 130, 70, 20 );
		adic( txtDataAtendimentoFin, 146, 150, 70, 20 );
		adic( txtHorafim, 219, 150, 53, 20 );
		adic( btRun, 272, 150, 19, 19 );
		adic( new JLabelPad( "Contrato/Projeto" ), 7, 90, 284, 20 );
		adic( cbContr, 7, 110, 284, 20 );
		adic( new JLabelPad( "Item" ), 294, 90, 320, 20 );
		adic( cbitContr, 294, 110, 320, 20 );

		txtDataAtendimento.setRequerido( true );
		txtDataAtendimentoFin.setRequerido( false );
		txtDataAtendimentoFin.setSoLeitura( true );
		txtDataAtendimento.addKeyListener( this );

		btMedida.addActionListener( this );
		cbTipo.addComboBoxListener( this );
		cbContr.addComboBoxListener( this );

		txtCodCli.setVlrInteger( new Integer( iCodCli ) );

		if ( iCodCli > 0 ) {
			txtCodCli.setAtivo( false );
		}

		btRun.addActionListener( this );

		setConexao( conn );

		if ( !update ) {
			buscaAtendente();
			
			if ( getAutoDataHora() ) {
				txtHoraini.setVlrTime( new Date() );
				txtDataAtendimento.setVlrDate( new Date() );
				txtDataAtendimentoFin.setVlrDate( new Date() );
				iniciaContagem();
			}
		}
	}
	
	private void montaListaCampos(){

		txtCodCli.setTabelaExterna( lcCli );
		txtCodCli.setFK( true );
		txtCodCli.setNomeCampo( "CodCli" );
		lcCli.add( new GuardaCampo( txtCodCli, "CodCli", "Cód.cli.", ListaCampos.DB_PK, false ) );
		lcCli.add( new GuardaCampo( txtRazCli, "RazCli", "Razão social do cliente", ListaCampos.DB_SI, false ) );
		lcCli.montaSql( false, "CLIENTE", "VD" );
		lcCli.setReadOnly( true );

		txtCodAtend.setTabelaExterna( lcAtend );
		txtCodAtend.setFK( true );
		txtCodAtend.setNomeCampo( "CodAtend" );
		lcAtend.add( new GuardaCampo( txtCodAtend, "CodAtend", "Cód.atend.", ListaCampos.DB_PK, false ), "txtCodVendx" );
		lcAtend.add( new GuardaCampo( txtNomeAtend, "NomeAtend", "Nome", ListaCampos.DB_SI, false ), "txtCodVendx" );
		lcAtend.montaSql( false, "ATENDENTE", "AT" );
		lcAtend.setReadOnly( true );

		txtCodAtendo.setTabelaExterna( lcAtendimento );
		txtCodAtendo.setFK( true );
		txtCodAtendo.setNomeCampo( "CodAtendo" );
		lcAtendimento.add( new GuardaCampo( txtCodAtendo, "CodAtendo", "Cód.atendo", ListaCampos.DB_PK, false ));
		lcAtendimento.add( new GuardaCampo( txtCodCli, "CodCli", "Cód.atend.", ListaCampos.DB_FK, false ));
		lcAtendimento.add( new GuardaCampo( txtCodAtend, "CodAtend", "Cod.Atend.", ListaCampos.DB_FK, false ));
		lcAtendimento.add( new GuardaCampo( txtDataAtendimento, "dataAtendo", "Data atendimento", ListaCampos.DB_SI, false ));
		lcAtendimento.add( new GuardaCampo( txtDataAtendimentoFin, "dataAtendoFin", "Data atendimento fin.", ListaCampos.DB_SI, false ));
		lcAtendimento.add( new GuardaCampo( txtHoraini, "HoraAtendo", "Hora atendimento", ListaCampos.DB_SI, false ));
		lcAtendimento.add( new GuardaCampo( txtHorafim, "HoraAtendoFin", "Hora atendimento fin.", ListaCampos.DB_SI, false ));
		lcAtendimento.add( new GuardaCampo( txaDescAtend, "ObsAtendo", "Descrição", ListaCampos.DB_SI, false ));				
		lcAtendimento.add( new GuardaCampo( txtTipoAtendimento, "codtpatendo", "Tipo", ListaCampos.DB_SI, false ));				
		lcAtendimento.add( new GuardaCampo( txtSetor, "codsetat", "setor", ListaCampos.DB_SI, false ));
		lcAtendimento.add( new GuardaCampo( txtContr, "codcontr", "Codcontrato", ListaCampos.DB_SI, false ));				
		lcAtendimento.add( new GuardaCampo( txtitContr, "coditcontr", "item do contrato", ListaCampos.DB_SI, false ));
		lcAtendimento.add( new GuardaCampo( txtStatusAtendo, "statusatendo", "Status do atendimento", ListaCampos.DB_SI, false ));				
		lcAtendimento.montaSql( false, "ATENDIMENTO", "AT" );
		lcAtendimento.setReadOnly( true );
	}
	
	private void montaComboStatus() {
		
		Vector<String> vValsStatus = new Vector<String>();
		Vector<String> vLabsStatus = new Vector<String>();
		vValsStatus.addElement( "AA" );
		vValsStatus.addElement( "NC" );		
		vLabsStatus.addElement( "Atendido" );
		vLabsStatus.addElement( "Não computado" );
	
		cbStatus.setItensGeneric( vLabsStatus, vValsStatus );		
	}
	
	private void montaComboTipo() {

		try {
			
			PreparedStatement ps = con.prepareStatement( "SELECT CODTPATENDO,DESCTPATENDO FROM ATTIPOATENDO WHERE CODEMP=? AND CODFILIAL=? AND TIPOATENDO=? ORDER BY 2" );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "ATTIPOATENDO" ) );
			ps.setString( 3, tipoatendo );
			
			ResultSet rs = ps.executeQuery();
			
			vValsTipo.clear();
			vLabsTipo.clear();
			vValsTipo.addElement( -1 );
			vLabsTipo.addElement( "<Selecione>" );
			
			while ( rs.next() ) {
				vValsTipo.addElement( rs.getInt( "CodTpAtendo" ) );
				vLabsTipo.addElement( rs.getString( "DescTpAtendo" ) );
			}
			
			cbTipo.setItensGeneric( vLabsTipo, vValsTipo );
			
			rs.close();
			ps.close();
			
			con.commit();
			
		} catch ( SQLException e ) {
			e.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao carregar os tipos de atendimento!\n" + e.getMessage(), true, con, e );
		} 
	}

	private void montaComboSetor() {

		Integer iTipo = cbTipo.getVlrInteger();
		if ( ( iTipo == null ) || ( iTipo.intValue() <= 0 ) )
			return;

		PreparedStatement ps = null;
		ResultSet rs = null;
		String sSQL = "SELECT S.CODSETAT,S.DESCSETAT FROM ATSETOR S, ATTIPOATENDOSETOR TS " + 
					  "WHERE S.CODEMP=TS.CODEMPST AND S.CODFILIAL=TS.CODFILIAL AND S.CODSETAT=TS.CODSETAT " + "" +
					  "AND TS.CODEMP=? AND TS.CODFILIAL=? AND TS.CODTPATENDO=? ORDER BY 2";

		try {
			ps = con.prepareStatement( sSQL );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "ATTIPOATENDO" ) );
			ps.setInt( 3, iTipo.intValue() );
			rs = ps.executeQuery();
			
			vValsSetor.clear();
			vLabsSetor.clear();
			vValsSetor.addElement( -1 );
			vLabsSetor.addElement( "<Selecione>" );
			
			while ( rs.next() ) {
				vValsSetor.addElement( new Integer( rs.getInt( "CodSetAt" ) ) );
				vLabsSetor.addElement( rs.getString( "DescSetAt" ) );
			}
			
			cbSetor.setItensGeneric( vLabsSetor, vValsSetor );
			
			rs.close();			
			ps.close();
			
			con.commit();
			
		} 
		catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro ao carregar os setores!\n" + err.getMessage(), true, con, err );
		} 
		finally {
			
			if ( vValsSetor.size() <= 1 ) {
				Funcoes.mensagemInforma( this, "Não existe setor cadastrado para este tipo de atendimento." );
			}
			else if ( vValsSetor.size() == 2 ) {
				cbSetor.setSelectedIndex( 1 );
				cbSetor.setEnabled( false );
			}
			else {
				cbSetor.setEnabled( true );
			}
			
			
			ps = null;
			rs = null;
		}

	}

	private boolean getAutoDataHora() {

		boolean autoDataHora = true;

		try {
			
			PreparedStatement ps = con.prepareStatement( "SELECT AUTOHORATEND FROM SGPREFERE3 WHERE CODEMP=? AND CODFILIAL=?" );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, Aplicativo.iCodFilial );
			
			ResultSet rs = ps.executeQuery();
			
			if ( rs.next() ) {
				autoDataHora = "S".equals( rs.getString( "AUTOHORATEND" ) );
			}
			
			rs.close();
			ps.close();
			con.commit();
			
		} catch ( SQLException e ) {
			e.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao verificar preferências.\n" + e.getMessage() );
		} 

		return autoDataHora;
	}

	private String[] getPref() {

		String sRets[] = { "", "" };

		try {
			
			PreparedStatement ps = con.prepareStatement( "SELECT CODTPATENDO,CLASSMEDIDA FROM SGPREFERE2 WHERE CODEMP=? AND CODFILIAL=?" );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, Aplicativo.iCodFilial );
			
			ResultSet rs = ps.executeQuery();
			
			if ( rs.next() ) {
				sRets[ 0 ] = rs.getString( "CodTpAtendo" ) != null ? rs.getString( "CodTpAtendo" ) : "";
				sRets[ 1 ] = rs.getString( "ClassMedida" );
			}
			
			rs.close();
			ps.close();
			con.commit();
			
		} catch ( SQLException e ) {
			e.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao verificar levantamento.\n" + e.getMessage() );
		} 

		return sRets;
	}

	private int getCodLev() {

		int iRet = 0;
		
		try {
			
			PreparedStatement ps = con.prepareStatement( "SELECT ISEQ FROM SPGERANUM(?,?,?)" );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, Aplicativo.iCodFilial );
			ps.setString( 3, "LV" );
			
			ResultSet rs = ps.executeQuery();
			if ( rs.next() ) {
				iRet = rs.getInt( 1 );
			}
			
			rs.close();
			ps.close();
			
			con.commit();
		} catch ( SQLException e ) {
			e.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao buscar novo código para levantamento.\n" + e.getMessage(), true, con, e );
		} 

		return iRet;
	}

	private void insertAtend() throws Exception {

		Object ORets[] = getValores();

		StringBuilder sql = new StringBuilder();

		sql.append( "EXECUTE PROCEDURE ATADICATENDIMENTOCLISP(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)" );

		PreparedStatement ps = con.prepareStatement( sql.toString() );

		ps.setString( 1, (String) ORets[ 0 ] ); // Tipo de atendimento
		ps.setString( 2, (String) ORets[ 1 ] ); // Codigo do atendente
		ps.setString( 3, (String) ORets[ 2 ] ); // Setor de atendimento
		ps.setInt( 4, Aplicativo.iCodEmp ); // Código da empresa
		ps.setInt( 5, Aplicativo.iCodFilialPad ); // Código da filial
		ps.setInt( 6, Integer.parseInt( (String) ORets[ 3 ] ) ); // Nro. do atendimento
		ps.setDate( 7, Funcoes.dateToSQLDate( (Date) ORets[ 4 ] ) ); // Data de inicio do atendimento
		ps.setDate( 8, Funcoes.dateToSQLDate( (Date) ORets[ 5 ] ) ); // Data final do atendimento
		ps.setTime( 9, Funcoes.strTimeTosqlTime( ORets[ 6 ].toString() ) ); // Hora inicial do atendimento
		ps.setTime( 10, Funcoes.strTimeTosqlTime( ORets[ 7 ].toString() ) ); // Hora final do atendimento
		ps.setString( 11, (String) ORets[ 8 ] ); // Descrição do atendimento
		ps.setInt( 12, Aplicativo.iCodEmp ); // Código da empresa do cliente
		ps.setInt( 13, Aplicativo.iCodFilialPad ); // Código da filial do cliente
		ps.setInt( 14, txtCodCli.getVlrInteger() ); // Código do cliente

		if ( cbContr.getVlrInteger() == -1 ) {
			ps.setNull( 15, Types.INTEGER ); // Código da empresa do contrato
			ps.setNull( 16, Types.INTEGER ); // Código da filial do contrato
			ps.setNull( 17, Types.INTEGER ); // Código do contrato
		}
		else {
			ps.setInt( 15, Aplicativo.iCodEmp ); // Código da empresa do contrato
			ps.setInt( 16, Aplicativo.iCodFilialPad ); // Código da filial do contrato
			ps.setInt( 17, cbContr.getVlrInteger() ); // Código do contrato
		}
		if ( cbitContr.getVlrInteger() == -1 ) {
			ps.setInt( 18, cbContr.getVlrInteger() ); // Código do ítem de contrato
		}
		else {
			ps.setInt( 18, cbitContr.getVlrInteger() ); // Código do ítem de contrato
		}

		if ( codrec != null && nparcitrec != null ) {
			ps.setInt( 19, codrec ); // Código do contas a receber
			ps.setInt( 20, nparcitrec ); // Código do ítem do contas a receber
		}
		else {
			ps.setNull( 19, Types.INTEGER ); // Código do contas a receber
			ps.setInt( 20, Types.INTEGER ); // Código do ítem do contas a receber
		}

		ps.execute();
		ps.close();

		con.commit();
	}
	
	private void updateAtend() throws Exception {
		
		StringBuilder sql = new StringBuilder();
		
		sql.append( "update atatendimento a set  " );
		sql.append( "a.codatend=?, a.dataatendo=?, a.dataatendofin=?, " );
		sql.append( "a.horaatendo=?, a.horaatendofin=?, a.obsatendo=?, " );
		sql.append( "a.codempto=?, a.codfilialto=?, a.codtpatendo=?, " );
		sql.append( "a.codempsa=?, a.codfilialsa=?, a.codsetat=?, " );
		sql.append( "a.codempct=?, a.codfilialct=?, a.codcontr=?, a.coditcontr=?, " );
		sql.append( "a.statusatendo=? ");
		sql.append( "where a.codemp=? and a.codfilial=? and a.codatendo=? " );
					
		PreparedStatement ps = con.prepareStatement( sql.toString() );
		ps.setInt( 1, txtCodAtend.getVlrInteger() );
		ps.setDate( 2,  Funcoes.dateToSQLDate( txtDataAtendimento.getVlrDate()  ));
		ps.setDate( 3,  Funcoes.dateToSQLDate( txtDataAtendimentoFin.getVlrDate() ));
			
		ps.setTime( 4, Funcoes.strTimeTosqlTime( txtHoraini.getVlrString()) );
		ps.setTime( 5, Funcoes.strTimeTosqlTime( txtHorafim.getVlrString()) );
		
		ps.setString( 6, txaDescAtend.getVlrString() );
		ps.setInt( 7, Aplicativo.iCodEmp );
		ps.setInt( 8, ListaCampos.getMasterFilial( "ATTIPOATENDO" ));
		ps.setInt( 9, cbTipo.getVlrInteger() );				
		ps.setInt( 10, Aplicativo.iCodEmp );
		ps.setInt( 11, ListaCampos.getMasterFilial( "ATSETOR" ));
		ps.setInt( 12, cbSetor.getVlrInteger() );			
		ps.setInt( 13, Aplicativo.iCodEmp );
		ps.setInt( 14, ListaCampos.getMasterFilial( "ATATENDIMENTO" ));
		if( cbContr.getVlrInteger() == -1  ){
			ps.setNull( 15, Types.INTEGER );
		}else{
			ps.setInt( 15, cbContr.getVlrInteger());
		}		
		if( cbitContr.getVlrInteger() == -1  ){
			ps.setInt( 16, cbContr.getVlrInteger());
		}else{
			ps.setInt( 16, cbitContr.getVlrInteger() );
		}
		
		ps.setString( 17, cbStatus.getVlrString() );
		
		ps.setInt( 18, Aplicativo.iCodEmp );
		ps.setInt( 19, ListaCampos.getMasterFilial( "ATATENDIMENTO" ));
		ps.setInt( 20, txtCodAtendo.getVlrInteger() );
		
		
		
		
		
		ps.executeUpdate();

		con.commit();
								 	
	}
	
	private void buscaAtendente() {
	
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuilder sql = new StringBuilder();
		try {
	
			sql.append( "SELECT CODATEND FROM ATATENDENTE " );
			sql.append( "WHERE CODEMPUS=? AND CODFILIALUS=? AND IDUSU=? " );
	
			ps = con.prepareStatement( sql.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, Aplicativo.iCodFilial );
			ps.setString( 3, Aplicativo.strUsuario );
	
			rs = ps.executeQuery();
	
			if ( rs.next() ) {
				txtCodAtend.setVlrInteger( rs.getInt( "CODATEND" ) );
				lcAtend.carregaDados();
			}
			
			con.commit();
	
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}

	private void iniciaContagem() {
	
		if ( !contando ) {
			
			btRun.setIcon( Icone.novo( "btpause.png" ) );
			
			contador = new Thread( new Runnable() {
				public void run() {
					try {
						Calendar calini = null;
						while ( contando ) {
							try {
								Thread.sleep( 1000 );
							} catch ( Exception e ) {
								System.out.println( "Contagem interrompida" );
							}
							calini = new GregorianCalendar();
							txtHorafim.setVlrTime( calini.getTime() );
						}
					} catch ( Exception e ) {
						e.printStackTrace();
						Funcoes.mensagemInforma( null, "Erro na contagem!" );
					} finally {
						Calendar calini = null;
						SpinnerDateModel sdm = null;
						DateEditor de = null;
					}
				}
			} );
			try {
				contador.start();
				contando = true;
			} catch ( Exception e ) {
				e.printStackTrace();
			}
		}
		else {
			try {
				btRun.setIcon( Icone.novo( "btplay.png" ) );
				contador.interrupt();
			} catch ( Exception e ) {
				System.out.println( "Contagem interrompida" );
			} finally {
				contando = false;
			}
		}
	}

	public void actionPerformed( ActionEvent evt ) {
	
		if ( evt.getSource() == btOK ) {			
			if ( txtDataAtendimento.getVlrDate().after( txtDataAtendimentoFin.getVlrDate() ) ) {
				Funcoes.mensagemInforma( this, "Data final menor que a data inicial!" );
				txtDataAtendimento.requestFocus();
				return;
			}
			if ( cbTipo.getVlrInteger().equals( -1 ) ) {
				Funcoes.mensagemInforma( this, "O tipo de atendimento não foi selecionado!" );
				cbTipo.requestFocus();
				return;
			}
			else if ( cbSetor.getVlrInteger().equals( -1 ) ) {
				Funcoes.mensagemInforma( this, "O setor não foi selecionado!" );
				cbSetor.requestFocus();
				return;
			}
			else if ( txaDescAtend.getVlrString().equals( "" ) ) {
				Funcoes.mensagemInforma( this, "Não foi digitado nenhum procedimento!" );
				txaDescAtend.requestFocus();
				return;
			}
			else if ( txtDataAtendimento.getVlrString().equals( "" ) ) {
				Funcoes.mensagemInforma( this, "Data inicial é requerida!" );
				txtDataAtendimento.requestFocus();
				return;
			}
			else if ( txtDataAtendimentoFin.getVlrString().equals( "" ) ) {
				Funcoes.mensagemInforma( this, "Data final é requerida!" );
				txtDataAtendimentoFin.requestFocus();
				return;
			}
			else if ( txtHoraini.getVlrString().equals( "" ) ) {
				Funcoes.mensagemInforma( this, "Hora inicial é requerida!" );
				txtHoraini.requestFocus();
				return;
			}
			else if ( txtHorafim.getVlrString().equals( "" ) ) {
				Funcoes.mensagemInforma( this, "Hora final é requerida!" );
				txtHorafim.requestFocus();
				return;
			}			
			else if ( txtCodAtend.getVlrInteger()<=0 ) {
				Funcoes.mensagemInforma( this, "Selecione o atendente!" );
				txtCodAtend.requestFocus();
				return;
			}
			
			if( update ) {
				try {
					updateAtend();
				}
				catch (Exception e) {
					Funcoes.mensagemInforma( this, "Erro ao gravar o atendimento!\n" + e.getMessage() );
					e.printStackTrace();
					return;
				}
				
			}
			else {
				try {
					insertAtend();
				}
				catch (Exception e) {
					e.printStackTrace();
					Funcoes.mensagemInforma( this, "Erro ao gravar o atendimento!\n" + e.getMessage() );
					return;
				}				
			}			
			super.actionPerformed( evt );			
		}
		else if ( evt.getSource() == btRun ) {
			iniciaContagem();
		}		
		else if( evt.getSource() == btCancel ){
			dispose();
		}
		else {
			super.actionPerformed( evt );	
		}
	}

	@ Override
	public void keyPressed( KeyEvent kevt ) {

		super.keyPressed( kevt );
		if ( kevt.getSource() == txtDataAtendimento ) {
			if ( kevt.getKeyCode() == KeyEvent.VK_ENTER ) {
				if ( !"".equals( txtDataAtendimento.getVlrString() ) ) {
					txtDataAtendimentoFin.setVlrDate( txtDataAtendimento.getVlrDate() );
				}
				else {
					Funcoes.mensagemInforma( this, "Digite a data do atendimento!" );
					txtDataAtendimento.requestFocus();
				}
			}
		}
	}
	
	public void valorAlterado( JComboBoxEvent evt ) {
	
		if ( evt.getComboBoxPad() == cbTipo ) {
			montaComboSetor();
		}
		if ( evt.getComboBoxPad() == cbContr ) {
			HashMap<String, Vector<Object>> vals = FuncoesCRM.montaComboItContr( con, cbContr.getVlrInteger(), "<Sem contrato>" );
			cbitContr.setItensGeneric( (Vector<?>) vals.get( "LAB" ), (Vector<?>) vals.get( "VAL" ) );
		}
	}

	public void setConexao( DbConnection cn ) {
	
		super.setConexao( cn );
		montaComboTipo();
		montaComboSetor();
		montaComboStatus();
		
		HashMap<String,Vector<Object>> vals = FuncoesCRM.montaComboContr( con, txtCodCli.getVlrInteger(), "<Sem contrato>" );
		cbContr.setItensGeneric( (Vector<?>)vals.get( "LAB" ), (Vector<?>)vals.get( "VAL" ) );  	 
		
		lcAtendimento.setConexao( cn );
		lcAtend.setConexao( cn );
		lcCli.setConexao( cn );
		lcCli.carregaDados();
		
		sPrefs = getPref();	
	}

	public Object[] getValores() {
	
		Object[] sVal = new Object[ 12 ];
		sVal[ 0 ] = String.valueOf( cbTipo.getVlrInteger() );
		sVal[ 1 ] = txtCodAtend.getVlrString();
		sVal[ 2 ] = String.valueOf( cbSetor.getVlrInteger() );
		sVal[ 3 ] = String.valueOf( iDoc );
		sVal[ 4 ] = txtDataAtendimento.getVlrDate();
		sVal[ 5 ] = txtDataAtendimentoFin.getVlrDate();		
		sVal[ 6 ] = txtHoraini.getVlrString();
		sVal[ 7 ] = txtHorafim.getVlrString();		
		sVal[ 8 ] = txaDescAtend.getVlrString();
		sVal[ 9 ] = cbContr.getVlrInteger();
		sVal[ 10 ] =  cbitContr.getVlrInteger();
		
		return sVal;
	}
	
}	
