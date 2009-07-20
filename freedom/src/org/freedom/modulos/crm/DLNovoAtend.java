package org.freedom.modulos.crm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import org.freedom.infra.model.jdbc.DbConnection;
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
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.SpinnerDateModel;
import javax.swing.JSpinner.DateEditor;

import org.freedom.acao.JComboBoxEvent;
import org.freedom.acao.JComboBoxListener;
import org.freedom.bmps.Icone;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JComboBoxPad;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JTextAreaPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.funcoes.FuncoesCRM;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FFDialogo;


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
	
	private JTextFieldPad txtitContr = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );
	
	private JTextFieldPad txtSetor = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );

	private JTextAreaPad txaDescAtend = new JTextAreaPad();

	private Vector<Integer> vValsTipo = new Vector<Integer>();

	private Vector<String> vLabsTipo = new Vector<String>();

	private JComboBoxPad cbTipo = new JComboBoxPad( vLabsTipo, vValsTipo, JComboBoxPad.TP_INTEGER, 8, 0 );

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

	private JScrollPane spnTxa = new JScrollPane( txaDescAtend );

	private JLabelPad lbImg = new JLabelPad( Icone.novo( "bannerAtendimento.png" ) );

	private JButton btMedida = new JButton( Icone.novo( "btMedida.gif" ) );
	
	private JButton btRun = new JButton( Icone.novo( "btplay.png" ) );

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
		
	public DLNovoAtend( int iCodCli, Component cOrig, boolean isUpdate, DbConnection conn, int codatendo, int codatend, String tipoatendo ){
		
		this( iCodCli, cOrig, conn, isUpdate, tipoatendo);

		update = isUpdate;
			
		txtCodAtendo.setVlrInteger( codatendo );
		
		lcAtendimento.carregaDados();
		
		cbTipo.setVlrInteger( txtTipoAtendimento.getVlrInteger() );
		cbSetor.setVlrInteger( txtSetor.getVlrInteger() );
		cbContr.setVlrInteger( txtContr.getVlrInteger() );
		cbitContr.setVlrInteger( txtitContr.getVlrInteger() );
				
	}

	public DLNovoAtend( int iCodCli, Component cOrig, DbConnection conn, boolean isUpdate, String tipoatendo, Integer codrec, Integer nparcitrec) {
		
		this(iCodCli, cOrig, conn, isUpdate, tipoatendo);
		
		this.codrec = codrec;
		this.nparcitrec = nparcitrec;		
		
	}
	
	public DLNovoAtend( int iCodCli, Component cOrig, DbConnection conn, boolean isUpdate, String tipoatendo) {

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
		
		pnTxa.add( spnTxa ); 
		
		spnTxa.setBorder( BorderFactory.createTitledBorder( "Detalhamento do atendimento" ) );		
		txaDescAtend.setBorder( BorderFactory.createEtchedBorder(Color.RED, null) );
		
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
				
		adic( btRun, 272,150,19,19);

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

		if(iCodCli>0) {		
			txtCodCli.setAtivo( false );
		}

		GregorianCalendar calini = new GregorianCalendar();
		GregorianCalendar calfim = new GregorianCalendar();
		GregorianCalendar cal1 = new GregorianCalendar();
		GregorianCalendar cal2 = new GregorianCalendar();
		GregorianCalendar agora = new GregorianCalendar();
		Date dataini = new Date();
		Date datafim = new Date();
		
		if ( dataini != null ) {
		    agora.setTime( dataini );
		    agora.set( Calendar.HOUR_OF_DAY, calini.get( Calendar.HOUR_OF_DAY ) );
		    agora.set( Calendar.MINUTE, calini.get( Calendar.MINUTE ) );

		    calini.setTime( dataini );
		    calfim.setTime( datafim );

		    cal1.setTime( agora.getTime() );
		    cal2.setTime( agora.getTime() );
		}

		calini.add( Calendar.DATE, 0 );
		cal1.add( Calendar.YEAR, -100 );
		cal2.add( Calendar.YEAR, 100 );
		
		txtDataAtendimento.setVlrDate( calini.getTime() );		
		txtDataAtendimentoFin.setVlrDate( calfim.getTime() );

		txtHoraini.setVlrTime( calini.getTime() );
		
		if(txtDataAtendimento.getVlrDate()==null && txtDataAtendimentoFin.getVlrDate()==null) {
			txtDataAtendimento.setVlrDate( new Date() );
			txtDataAtendimentoFin.setVlrDate( new Date() );
		}
		
		btRun.addActionListener( this );
		
		setConexao( conn );
		
		
		
		if(!update) {
			buscaAtendente();
			iniciaContagem();
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
		lcAtendimento.montaSql( false, "ATENDIMENTO", "AT" );
		lcAtendimento.setReadOnly( true );

	}
	
	private void montaComboTipo() {

		PreparedStatement ps = null;
		ResultSet rs = null;
		String sSQL = "SELECT CODTPATENDO,DESCTPATENDO FROM ATTIPOATENDO WHERE CODEMP=? AND CODFILIAL=? AND TIPOATENDO=? ORDER BY 2";
		try {
			ps = con.prepareStatement( sSQL );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "ATTIPOATENDO" ) );
			ps.setString( 3, tipoatendo );
			rs = ps.executeQuery();
			vValsTipo.clear();
			vLabsTipo.clear();
			vValsTipo.addElement( -1 );
			vLabsTipo.addElement( "<Selecione>" );
			while ( rs.next() ) {
				vValsTipo.addElement( new Integer( rs.getInt( "CodTpAtendo" ) ) );
				vLabsTipo.addElement( rs.getString( "DescTpAtendo" ) );
			}
			cbTipo.setItens( vLabsTipo, vValsTipo );
			rs.close();
			ps.close();
			con.commit();
		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro ao carregar os tipos de atendimento!\n" + err.getMessage(), true, con, err );
		} finally {
			ps = null;
			rs = null;
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
			
			cbSetor.setItens( vLabsSetor, vValsSetor );
			
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

	private String[] getPref() {

		String sRets[] = { "", "" };
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sSQL = "SELECT CODTPATENDO,CLASSMEDIDA FROM SGPREFERE2 WHERE CODEMP=? AND CODFILIAL=?";
		try {
			ps = con.prepareStatement( sSQL );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, Aplicativo.iCodFilial );
			rs = ps.executeQuery();
			if ( rs.next() ) {
				sRets[ 0 ] = rs.getString( "CodTpAtendo" ) != null ? rs.getString( "CodTpAtendo" ) : "";
				sRets[ 1 ] = rs.getString( "ClassMedida" );
			}
			rs.close();
			ps.close();
			con.commit();
		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro ao verificar levantamento. " );
		} finally {
			ps = null;
			rs = null;
		}
		return sRets;
	}

	private int getCodLev() {

		int iRet = 0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sSQL = "SELECT ISEQ FROM SPGERANUM(?,?,?)";
		try {
			ps = con.prepareStatement( sSQL );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, Aplicativo.iCodFilial );
			ps.setString( 3, "LV" );
			rs = ps.executeQuery();
			if ( rs.next() )
				iRet = rs.getInt( 1 );

			rs.close();
			ps.close();
			con.commit();
		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro ao buscar novo código para levantamento.\n" + err.getMessage(), true, con, err );
		} finally {
			ps = null;
			rs = null;
		}
		return iRet;
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

	private void insertAtend() throws Exception {
		Object ORets[] = getValores();

			
		StringBuilder sql = new StringBuilder();

		sql.append( "EXECUTE PROCEDURE ATADICATENDIMENTOCLISP(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)" );
		
		PreparedStatement ps = con.prepareStatement( sql.toString() );

		ps.setString( 1, (String) ORets[ 0 ] ); // Tipo de atendimento
		ps.setString( 2, (String)ORets[ 1 ] ); // Codigo do atendente
		ps.setString( 3, (String)ORets[ 2 ] ); // Setor de atendimento
		ps.setInt( 4, Aplicativo.iCodEmp ); // Código da empresa
		ps.setInt( 5, Aplicativo.iCodFilialPad ); // Código da filial
		ps.setInt( 6, Integer.parseInt( (String) ORets[ 3 ] ) ); // Nro. do atendimento
		ps.setDate( 7, Funcoes.dateToSQLDate( (Date)ORets[4] )   ); // Data de inicio do atendimento
		ps.setDate( 8, Funcoes.dateToSQLDate( (Date)ORets[5] )  ); // Data final do atendimento				
		ps.setTime( 9,  Funcoes.strTimeTosqlTime( ORets[6].toString())); // Hora inicial do atendimento
		ps.setTime( 10,  Funcoes.strTimeTosqlTime( ORets[7].toString())); // Hora final do atendimento				
		ps.setString( 11, (String)ORets[8] ); // Descrição do atendimento
		ps.setInt( 12, Aplicativo.iCodEmp ); // Código da empresa do cliente
		ps.setInt( 13, Aplicativo.iCodFilialPad ); // Código da filial do cliente
		ps.setInt( 14, txtCodCli.getVlrInteger() ); // Código do cliente
		
		if( cbContr.getVlrInteger() == -1  ){
			ps.setNull( 15, Types.INTEGER ); // Código da empresa do contrato
			ps.setNull( 16, Types.INTEGER ); // Código da filial do contrato
			ps.setNull( 17, Types.INTEGER ); // Código do contrato			
		}
		else{
			ps.setInt( 15, Aplicativo.iCodEmp ); // Código da empresa do contrato
			ps.setInt( 16, Aplicativo.iCodFilialPad ); // Código da filial do contrato
			ps.setInt( 17, cbContr.getVlrInteger()); // Código do contrato
		}		
		if( cbitContr.getVlrInteger() == -1  ){
			ps.setInt( 18, cbContr.getVlrInteger()); // Código do ítem de contrato
		}
		else{
			ps.setInt( 18, cbitContr.getVlrInteger() ); // Código do ítem de contrato
		}
		
		if( codrec!=null && nparcitrec !=null ) {
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
		sql.append( "a.codempct=?, a.codfilialct=?, a.codcontr=?, a.coditcontr=? " );
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
		ps.setInt( 17, Aplicativo.iCodEmp );
		ps.setInt( 18, ListaCampos.getMasterFilial( "ATATENDIMENTO" ));
		ps.setInt( 19, txtCodAtendo.getVlrInteger() );
		
		ps.executeUpdate();

		con.commit();
								 	
	}
	
	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		montaComboTipo();
		montaComboSetor();
		
  		HashMap<String,Vector<Object>> vals = FuncoesCRM.montaComboContr( con, txtCodCli.getVlrInteger(), "<Sem contrato>" );
  		cbContr.setItens( (Vector<?>)vals.get( "LAB" ), (Vector<?>)vals.get( "VAL" ) );  	 
		
		lcAtendimento.setConexao( cn );
		lcAtend.setConexao( cn );
		lcCli.setConexao( cn );
		lcCli.carregaDados();
		sPrefs = getPref();

	}

	public void valorAlterado( JComboBoxEvent evt ) {

		if ( evt.getComboBoxPad() == cbTipo ) {
			montaComboSetor();
		}
		if( evt.getComboBoxPad() == cbContr ){
	  		HashMap<String,Vector<Object>> vals = FuncoesCRM.montaComboItContr( con, cbContr.getVlrInteger(), "<Sem contrato>" );
	  		cbitContr.setItens( (Vector<?>)vals.get( "LAB" ), (Vector<?>)vals.get( "VAL" ) ); 	 
		}
	}

	@ Override
	public void keyPressed( KeyEvent kevt ) {

		super.keyPressed( kevt );
		if( kevt.getSource() == txtDataAtendimento ){
			if( kevt.getKeyCode() == KeyEvent.VK_ENTER ){
				if(!"".equals( txtDataAtendimento.getVlrString())){
					txtDataAtendimentoFin.setVlrDate( txtDataAtendimento.getVlrDate() );
				}else{
					Funcoes.mensagemInforma( this, "Digite a data do atendimento!" );
					txtDataAtendimento.requestFocus();
				}
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
			
			if( update ){
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
		if( evt.getSource() == btCancel ){
			dispose();
		}
	}
	
	private void iniciaContagem() {
		if(!contando) {
			btRun.setIcon( Icone.novo( "btpause.png" ) );
			contador = new Thread(
					new Runnable() {
				        public void run() {
				        	try {
				        		
				        		Calendar calini = null;
				        		
				        		while(contando) {
				        			try {
				        				Thread.sleep(1000);
				        			}
				        			catch (Exception e) {
				        				System.out.println("Contagem interrompida");
									}
				        			calini = new GregorianCalendar();
				        			txtHorafim.setVlrTime( calini.getTime() );
				        			
				        		}
				        	}
				        	catch (Exception e) {
				        		e.printStackTrace();
				        		Funcoes.mensagemInforma( null, "Erro na contagem!" );
							}
				        	finally {
				        		Calendar calini = null;
				        		SpinnerDateModel sdm = null;
				        		DateEditor de = null;
				        	}
				        }
					}
			);
			try {				
				contador.start();
				contando = true;
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}	
		else {			
			try {
				btRun.setIcon( Icone.novo( "btplay.png" ) );
				contador.interrupt();
			}
			catch (Exception e) {
				System.out.println("Contagem interrompida");
			}
			finally {
				contando = false;			
			}
	
		}
	}
	
	private void buscaAtendente() {
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuilder sql = new StringBuilder();
		try{

			sql.append( "SELECT CODATEND FROM ATATENDENTE " );
			sql.append( "WHERE CODEMPUS=? AND CODFILIALUS=? AND IDUSU=? " );
	
			ps = con.prepareStatement( sql.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, Aplicativo.iCodFilial );
			ps.setString( 3, Aplicativo.strUsuario );
			
			rs = ps.executeQuery();						
			
			if (rs.next()) {
				txtCodAtend.setVlrInteger( rs.getInt( "CODATEND" ) );
				lcAtend.carregaDados();
			}
			
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}	
