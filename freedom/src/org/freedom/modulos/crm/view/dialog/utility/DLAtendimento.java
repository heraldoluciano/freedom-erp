/*
 * Projeto: Freedom Pacote: org.freedom.modules.crm Classe: @(#)FAtendimento.java
 * 
 * Este arquivo é parte do sistema Freedom-ERP, o Freedom-ERP é um software livre; você pode redistribui-lo e/ou <BR> modifica-lo dentro dos termos da Licença Pública Geral GNU como publicada pela Fundação do Software Livre (FSF); <BR> na versão 2 da Licença, ou (na sua opnião) qualquer versão. <BR>
 * Este programa é distribuido na esperança que possa ser util, mas SEM NENHUMA GARANTIA; <BR> sem uma garantia implicita de ADEQUAÇÂO a qualquer MERCADO ou APLICAÇÃO EM PARTICULAR. <BR> Veja a Licença Pública Geral GNU para maiores detalhes. <BR> Você deve ter recebido uma cópia da Licença Pública
 * Geral GNU junto com este programa, se não, <BR> escreva para a Fundação do Software Livre(FSF) Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA <BR>
 */

package org.freedom.modulos.crm.view.dialog.utility;

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
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.SpinnerDateModel;
import javax.swing.JSpinner.DateEditor;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.bmps.Icone;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JButtonPad;
import org.freedom.library.swing.component.JCheckBoxPad;
import org.freedom.library.swing.component.JComboBoxPad;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JPanelPad;
import org.freedom.library.swing.component.JTextAreaPad;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.dialog.FFDialogo;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.modulos.crm.business.component.Atendimento;
import org.freedom.modulos.crm.business.object.SaldoContrato;
import org.freedom.modulos.crm.dao.DAOAtendimento;
import org.freedom.modulos.crm.view.frame.utility.FCRM;

/**
 * 
 * @author Setpoint Informática Ltda./Alex Rodrigues
 * @version 10/10/2009 - Alex Rodrigues
 * @version 23/04/2010 - Anderson Sanchez
 * @version 28/10/2010 - Robson Sanchez e Bruno Nascimento
 */
public class DLAtendimento extends FFDialogo implements KeyListener, CarregaListener {

	private static final long serialVersionUID = 1L;

	private JPanelPad pnCab = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 1, 1 ) );

	private JPanelPad pnBotoes = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JTextFieldPad txtCodCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtCodCli2 = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldFK txtRazCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 60, 0 );

	private JTextFieldPad txtCodAtend = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtCodChamado = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescChamado = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtCodEspec = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescEspec = new JTextFieldFK( JTextFieldPad.TP_STRING, 100, 0 );

	private JTextFieldPad txtCodAtendo = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtNomeAtend = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtDataAtendimento = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDataAtendimentoFin = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtHoraini = new JTextFieldPad( JTextFieldPad.TP_TIME, 5, 0 );

	private JTextFieldPad txtHorafim = new JTextFieldPad( JTextFieldPad.TP_TIME, 5, 0 );

	private JTextFieldPad txtCodTpAtendo = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );
	
	private JTextFieldFK txtDescTpAtendo = new JTextFieldFK( JTextFieldFK.TP_STRING, 50, 0 );

	//private JTextFieldPad txtContr = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );

	private JTextFieldPad txtCodContr = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldFK txtDescContr = new JTextFieldFK( JTextFieldFK.TP_STRING, 50, 0 );
	
	private JTextFieldPad txtCodItContr = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );
	
	private JTextFieldFK txtDescItContr = new JTextFieldFK( JTextFieldFK.TP_STRING, 50, 0 );

	private JTextFieldPad txtCodContrCh = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldPad txtCodItContrCh = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );
	
	private JTextFieldPad txtRecebContr = new JTextFieldPad( JTextFieldPad.TP_STRING, 1, 0 );
	
	private JTextFieldPad txtStatusAtendo = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );

	private JTextFieldPad txtAtivoAtendo = new JTextFieldPad( JTextFieldPad.TP_STRING, 1, 0 );

	//private JTextFieldPad txtitContr = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );
	
	private JTextFieldPad txtExcedentecob = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, 2 );

	private JTextFieldPad txtQtdhoras = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, 2 );

	private JTextFieldPad txtQtditcontr = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, 2 );
	
	private JTextFieldPad txtSaldo = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, 2 );

	private JTextFieldPad txtCodsetat = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );
	
	private JTextFieldFK txtDescSetor = new JTextFieldFK( JTextFieldFK.TP_STRING, 50, 0 );
	
	private JTextFieldFK txtObrigChamEspec = new JTextFieldFK( JTextFieldPad.TP_STRING, 1, 0 );
	
	private JTextFieldFK txtObrigProjEspec = new JTextFieldFK( JTextFieldPad.TP_STRING, 1, 0 );
	
	private JTextFieldPad txtCodTarefa= new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );
	
	private JTextFieldFK txtDescTarefa = new JTextFieldFK( JTextFieldFK.TP_STRING, 100, 0 );

	private JTextFieldPad txtCobcliEspec= new JTextFieldPad( JTextFieldPad.TP_STRING, 1, 0 );
	
	private JTextAreaPad txaObsAtend = new JTextAreaPad();

	private JTextAreaPad txaObsInterno = new JTextAreaPad();

	private Vector<Integer> vValsTipo = new Vector<Integer>();

	private Vector<String> vLabsTipo = new Vector<String>();

//	private JComboBoxPad cbTipo = new JComboBoxPad( vLabsTipo, vValsTipo, JComboBoxPad.TP_INTEGER, 8, 0 );

	private JComboBoxPad cbStatus = new JComboBoxPad( vLabsTipo, vValsTipo, JComboBoxPad.TP_STRING, 2, 0 );

	private Vector<Integer> vValsSetor = new Vector<Integer>();

	private Vector<String> vLabsSetor = new Vector<String>();

	//private JComboBoxPad cbSetor = new JComboBoxPad( vLabsSetor, vValsSetor, JComboBoxPad.TP_INTEGER, 8, 0 );

	//private Vector<Integer> vValsContr = new Vector<Integer>();

	//private Vector<String> vLabsContr = new Vector<String>();

	//private JComboBoxPad cbContrato = new JComboBoxPad( vLabsContr, vValsContr, JComboBoxPad.TP_INTEGER, 8, 0 );

	//private Vector<Integer> vValsitContr = new Vector<Integer>();

	//private Vector<String> vLabsitContr = new Vector<String>();

	//private JComboBoxPad cbitContrato = new JComboBoxPad( vLabsitContr, vValsitContr, JComboBoxPad.TP_INTEGER, 8, 0 );

	private ListaCampos lcAtend = new ListaCampos( this, "AE" );

	private ListaCampos lcEspec = new ListaCampos( this, "EA" );

	private ListaCampos lcChamado = new ListaCampos( this, "CH" );

	private ListaCampos lcCli = new ListaCampos( this, "CL" );
	
	private ListaCampos lcTpAtendo = new ListaCampos( this, "TO" ); 
	
	private ListaCampos lcSetor = new ListaCampos( this , "SA"); 
	
	private ListaCampos lcContrato = new ListaCampos( this , "CT"); 
	
	private ListaCampos lcItContrato = new ListaCampos( this , "CT"); 
	
	private ListaCampos lcTarefa = new ListaCampos( this , "TA"); 

	private ListaCampos lcAtendimento = new ListaCampos( this );

	private JLabelPad lbImg = new JLabelPad( Icone.novo( "bannerAtendimento.png" ) );

	private JButtonPad btMedida = new JButtonPad( Icone.novo( "btMedida.png" ) );

	private JButtonPad btRun = new JButtonPad( Icone.novo( "btplay.png" ) );

	private int iDoc = 0;

	private boolean update = false;

	private boolean contando = false;

	private Thread contador = null;

	private JLabelPad lbContador = new JLabelPad();

	private JPanelPad pnGeral = new JPanelPad( new BorderLayout() );

	private JPanelPad pnTela = new JPanelPad( new BorderLayout() );

	private JPanelPad pnCampos = new JPanelPad( 500, 300 );

	private JPanelPad pnTxa = new JPanelPad( new GridLayout( 2, 1 ) );

	private String tipoatendo = null;

	private Integer codrec = null;

	private Integer nparcitrec = null;

	private Integer codchamado_ant = null;

	private boolean financeiro = false;

	private JCheckBoxPad cbConcluiChamado = new JCheckBoxPad( "Conclui chamado?", "S", "N" );
	
	private Component corig = null;
	
	private DAOAtendimento daoatend = null;

	public DLAtendimento( int iCodCli, Integer codchamado, Component cOrig, boolean isUpdate, DbConnection conn, int codatendo, int codatend, String tipoatendo, boolean financeirop, String titulo ) {

		this( iCodCli, codchamado, cOrig, conn, tipoatendo, isUpdate, financeirop, titulo);

		corig = cOrig;
		
		update = isUpdate;

		txtCodAtendo.setVlrInteger( codatendo );

		lcAtendimento.carregaDados();
		cbStatus.setVlrString( txtStatusAtendo.getVlrString() );

		//cbTipo.setVlrInteger( txtTipoAtendimento.getVlrInteger() );
		//cbSetor.setVlrInteger( txtCodsetat.getVlrInteger() );
		//cbContrato.setVlrInteger( txtContr.getVlrInteger() );
		//cbitContrato.setVlrInteger( txtitContr.getVlrInteger() );
		txtCodChamado.setVlrInteger( codchamado );

		lcChamado.carregaDados();

		if ( update ) {
			pnCampos.adic( new JLabelPad( "Status" ), 510, 250, 120, 20 );
			pnCampos.adic( cbStatus, 510, 270, 100, 20 );
	
			//txtCoditContrato.setSize( 198, 20 );

		}			

	}
	
	public void abreAtendimento( int codcli, Integer codchamado, Component cOrig, DbConnection conn, boolean isUpdate, String tipoatendo, boolean financeirop ){

		this.financeiro = financeirop;

		String horaini = null;
		
		update = isUpdate;
		this.tipoatendo = tipoatendo;

//		montaListaCampos();

		txtCodCli.setVlrInteger( codcli );

		txtCodChamado.setVlrInteger( codchamado );

		if ( !update ) {

			txtCodAtend.setVlrInteger( Atendimento.buscaAtendente() );
			lcAtend.carregaDados();

			if ( getAutoDataHora() ) {
				
				txtHoraini.setVlrTime( new Date() );
				txtDataAtendimento.setVlrDate( new Date() );
				txtDataAtendimentoFin.setVlrDate( new Date() );
				
				try {
					
					horaini = daoatend.getHoraPrimUltLanca( Aplicativo.iCodEmp, ListaCampos.getMasterFilial( "ATATENDIMENTO" ), 
							new Date(), Funcoes.dateToStrTime(  new Date() ) , Funcoes.dateToStrTime(  new Date() ) ,
							Aplicativo.iCodEmp, ListaCampos.getMasterFilial( "ATATENDENTE" ), txtCodAtend.getVlrInteger(), 
							"F" );
					if (horaini!=null) {
						txtHoraini.setVlrString( horaini );
					}
				} catch ( SQLException e ) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				iniciaContagem();
			}
		}
	}
	
	public boolean isUpdate() {
		return update;
	}
	
	public void abreAtendimento( int iCodCli, Integer codchamado, Component cOrig, boolean isUpdate, DbConnection conn, int codatendo, int codatend, String tipoatendo, boolean financeirop ) {

		abreAtendimento( iCodCli, codchamado, cOrig, conn, isUpdate, tipoatendo, financeirop );

		update = isUpdate;

		txtCodAtendo.setVlrInteger( codatendo );

		lcAtendimento.carregaDados();
		
		cbStatus.setVlrString( txtStatusAtendo.getVlrString() );

		//cbTipo.setVlrInteger( txtTipoAtendimento.getVlrInteger() );
		//cbSetor.setVlrInteger( txtCodsetat.getVlrInteger() );
		//cbContrato.setVlrInteger( txtContr.getVlrInteger() );
		//cbitContrato.setVlrInteger( txtitContr.getVlrInteger() );
		txtCodChamado.setVlrInteger( codchamado );

		lcChamado.carregaDados();

		/*
		if ( update ) {

			cbitContrato.setSize( 198, 20 );

		}
		*/
		
	}

	public DLAtendimento( int codcli, Integer codchamado, Component cOrig, DbConnection conn, boolean isUpdate, Integer codrec, Integer nparcitrec, String tipoatendo, boolean financeirop, String titulo ) {

		this( codcli, codchamado, cOrig, conn, tipoatendo, isUpdate, financeirop, titulo );

		this.codrec = codrec;
		this.nparcitrec = nparcitrec;

	}

	public DLAtendimento(Component cOrig) {
		super( cOrig );
		
	}


	public DLAtendimento( Component cOrig, DbConnection conn, org.freedom.modulos.crm.business.object.Atendimento atd, String tipoatendo, String titulo ) {
		this( atd.getCodcli(), atd.getCodchamado(), cOrig, conn, tipoatendo, false, false, titulo );
		//txtCodAtend.setVlrInteger( atd.getCodatend() );
		txtCodTpAtendo.setVlrInteger( atd.getCodtpatendo() );
		lcTpAtendo.carregaDados();
		if (atd.getCodchamado()!=null) {
			txtCodChamado.setVlrInteger( atd.getCodchamado() );
			lcChamado.carregaDados();
		}
		txtCodsetat.setVlrInteger( atd.getCodsetat() ) ;
		if (atd.getCodcontr()!=null) {
			txtCodContr.setVlrInteger( atd.getCodcontr() );
			txtCodItContr.setVlrInteger( atd.getCoditcontr() );
			lcContrato.carregaDados();
			lcItContrato.carregaDados();
		}
		if (atd.getCodespec()!=null) {
			txtCodEspec.setVlrInteger( atd.getCodespec() );
			lcEspec.carregaDados();
		}
		if ( atd.getObsatendo()!=null ) {
			txaObsAtend.setVlrString( atd.getObsatendo() );
		}
		if ( atd.getObsinterno()!=null ) {
			txaObsInterno.setVlrString( atd.getObsinterno() );
		}
		if ( atd.getCodtarefa()!=null ) {
			txtCodTarefa.setVlrInteger( atd.getCodtarefa() );
			lcTarefa.carregaDados();
		}
		//txtTipoAtendimento.setVlrString( tipoatendo );
	}

	public DLAtendimento( int codcli, Integer codchamado, Component cOrig, DbConnection conn, String tipoatendo, boolean isUpdate, boolean financeirop, String titulo ) {
		this(cOrig);
		
		String horaini = null;
		corig = cOrig;

		this.financeiro = financeirop;
		this.update = isUpdate;
		this.tipoatendo = tipoatendo;

		montaTela(titulo);
		
		txtCodCli.setVlrInteger( codcli );

		txtCodChamado.setVlrInteger( codchamado );
		
		setConexao( conn );

		if ( !update ) {

			txtCodAtend.setVlrInteger( Atendimento.buscaAtendente() );
			lcAtend.carregaDados();

			if ( getAutoDataHora() ) {

				txtHoraini.setVlrTime( new Date() );
				txtDataAtendimento.setVlrDate( new Date() );
				txtDataAtendimentoFin.setVlrDate( new Date() );
				
				try {
					
					horaini = daoatend.getHoraPrimUltLanca( Aplicativo.iCodEmp, ListaCampos.getMasterFilial( "ATATENDIMENTO" ), 
							new Date(), Funcoes.dateToStrTime(  new Date() ) , Funcoes.dateToStrTime(  new Date() ) ,
							Aplicativo.iCodEmp, ListaCampos.getMasterFilial( "ATATENDENTE" ), txtCodAtend.getVlrInteger(), 
							"F" );
					if (horaini!=null) {
						txtHoraini.setVlrString( horaini );
					}
				} catch ( SQLException e ) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				iniciaContagem();

			}
		}
	}

	private void montaTela(String titulo) {
		
		if(titulo == null){
			setTitulo( "Novo atendimento" );
		} else {
			setTitulo(titulo);
		}
		setAtribos( 640, 680 );

		montaListaCampos();

		btMedida.setPreferredSize( new Dimension( 30, 30 ) );

		pnCab.add( lbImg );

		c.add( pnCab, BorderLayout.NORTH );

		c.add( pnGeral );

		pnGeral.add( pnTela, BorderLayout.CENTER );

		pnTela.add( pnCampos, BorderLayout.NORTH );

		pnTela.add( pnTxa, BorderLayout.CENTER );

		JScrollPane spnDetalhamento = new JScrollPane( txaObsAtend );
		JScrollPane spnObsInterno = new JScrollPane( txaObsInterno );

		spnDetalhamento.setBorder( BorderFactory.createTitledBorder( "Detalhamento" ) );
		spnObsInterno.setBorder( BorderFactory.createTitledBorder( "Observações internas" ) );

		pnTxa.add( spnDetalhamento );
		pnTxa.add( spnObsInterno );

		txaObsAtend.setBorder( BorderFactory.createEtchedBorder( Color.RED, null ) );

		setPainel( pnCampos );

		adic( txtCodCli, 7, 30, 80, 20, "Cód.cli." );
		adic( txtRazCli, 90, 30, 524, 20, "Razão social do cliente" );
		
		adic( txtCodChamado, 7, 70, 80, 20, "Cód.chamado" );
		adic( txtDescChamado, 90, 70, 524, 20, "Descrição do chamado" );

		adic( txtCodAtend, 7, 110, 80, 20, "Cód.atend." );
		adic( txtNomeAtend, 90, 110, 200, 20, "Nome do atendente" );
		
		adic( txtCodTpAtendo, 293, 110, 80, 20, "Cód.tp.at." );
		adic( txtDescTpAtendo, 376, 110, 237, 20, "Descrição do tipo de atendimento" );
		adic( txtCodsetat, 7, 150, 80, 20, "Cód.setor" );
		adic( txtDescSetor, 90, 150, 200, 20, "Descrição do setor" );
		
		adic( txtCodContr, 294, 150, 80, 20, "Cód.ctr./proj." );
		adic( txtDescContr, 377, 150, 237, 20, "Descrição do contrato/projeto" );
		adic( txtCodItContr, 7, 190, 80, 20, "Cód.item proj." );
		adic( txtDescItContr, 90, 190, 200, 20, "Descrição do item de contr./proj." );
		adic( txtCodTarefa, 294, 190, 80, 20, "Cód.Tarefa" );
		adic( txtDescTarefa, 377, 190, 237, 20, "Descrição da tarefa" );
		
		
		adic( txtDataAtendimento, 294, 230, 80, 20, "Início" );
		adic( txtHoraini, 377, 230, 53, 20 );
		adic( txtDataAtendimentoFin, 433, 230, 70, 20, "Final" );
		adic( txtHorafim, 506, 230, 53, 20 );
		adic( btRun, 559, 230, 19, 19 );

		adic( txtCodEspec, 7, 230, 80, 20, "Cód.espec." );
		adic( txtDescEspec, 90, 230, 200, 20, "Descrição da especificação do atendimento");

		adic( cbConcluiChamado, 7, 260, 150, 20 );

		adic( new JLabelPad("Franquia"), 160, 250, 80, 20 );
		adic( txtQtditcontr, 160, 270, 80, 20 );

		adic( new JLabelPad("Tot.horas"), 243, 250, 80, 20 );
		adic( txtQtdhoras, 243, 270, 80, 20 );

		adic( new JLabelPad("Saldo"), 326, 250, 80, 20 );
		adic( txtSaldo, 326, 270, 80, 20 );

		adic( new JLabelPad("Excedente"), 409, 250, 80, 20 );
		adic( txtExcedentecob, 409, 270, 80, 20 );
		
		txtDataAtendimento.setRequerido( true );
		txtDataAtendimentoFin.setRequerido( false );
		txtDataAtendimentoFin.setSoLeitura( true );
		txtQtditcontr.setSoLeitura( true );
		txtSaldo.setSoLeitura( true );
		txtExcedentecob.setSoLeitura( true );
		txtQtdhoras.setSoLeitura( true );
		txtDataAtendimento.addKeyListener( this );

		
		btMedida.addActionListener( this );
		//cbTipo.addComboBoxListener( this );
		//cbContrato.addComboBoxListener( this );

		lcChamado.addCarregaListener( this );
		lcEspec.addCarregaListener( this );
		lcCli.addCarregaListener( this );
		lcAtend.addCarregaListener( this );
		lcContrato.addCarregaListener( this );

		txtCodCli.setRequerido( true );
		txtCodTpAtendo.setRequerido( true );
		txtCodsetat.setRequerido( true );

		btRun.addActionListener( this );
		
	}
	
	private void loadSaldoContrato() {
		Date dt = txtDataAtendimento.getVlrDate();
		if (dt!=null) {
			int mes = Funcoes.getMes( dt )-1;
			int ano = Funcoes.getAno( dt );
			Date dtini = Funcoes.getDataIniMes( mes, ano );
			Date dtfin = Funcoes.getDataFimMes( mes, ano );
			try {
				SaldoContrato sld = daoatend.loadSaldoContrato(
						Aplicativo.iCodEmp, ListaCampos.getMasterFilial( "VDCLIENTE" ), txtCodCli.getVlrInteger()
						, Aplicativo.iCodEmp, ListaCampos.getMasterFilial( "VDCONTRATO" ), txtCodContr.getVlrInteger(), 0
						, dtini, dtfin );
				if (sld==null) {
					txtExcedentecob.setVlrString("");
					txtSaldo.setVlrString( "" );
					txtQtditcontr.setVlrString( "" );
					txtQtdhoras.setVlrString( "" );
				} else {
					txtExcedentecob.setVlrBigDecimal( sld.getExcedentemescob() );
					txtSaldo.setVlrBigDecimal( sld.getSaldomes() );
					txtQtditcontr.setVlrBigDecimal( sld.getQtditcontr() );
					txtQtdhoras.setVlrBigDecimal( sld.getQtdhoras() );
				}
				
			} catch (SQLException e ) {
				try {
					con.rollback();
				} catch (SQLException err) {
					err.printStackTrace();
				}
				Funcoes.mensagemErro( this, "Erro carregando saldo de contrato !\n" + e.getMessage() );
				e.printStackTrace();
			}
		}
		
	}
	
	private void montaListaCampos() {

		txtCodCli.setTabelaExterna( lcCli, null );
		txtCodCli.setFK( true );
		txtCodCli.setNomeCampo( "CodCli" );
		lcCli.add( new GuardaCampo( txtCodCli, "CodCli", "Cód.cli.", ListaCampos.DB_PK, false ) );
		lcCli.add( new GuardaCampo( txtRazCli, "RazCli", "Razão social do cliente", ListaCampos.DB_SI, false ) );
		lcCli.montaSql( false, "CLIENTE", "VD" );
		lcCli.setReadOnly( true );

		txtCodAtend.setTabelaExterna( lcAtend, null );
		txtCodAtend.setFK( true );
		txtCodAtend.setNomeCampo( "CodAtend" );
		lcAtend.add( new GuardaCampo( txtCodAtend, "CodAtend", "Cód.atend.", ListaCampos.DB_PK, false ), "txtCodVendx" );
		lcAtend.add( new GuardaCampo( txtNomeAtend, "NomeAtend", "Nome", ListaCampos.DB_SI, false ), "txtCodVendx" );
		lcAtend.montaSql( false, "ATENDENTE", "AT" );
		lcAtend.setReadOnly( true );

		txtCodAtendo.setTabelaExterna( lcAtendimento, null );
		txtCodAtendo.setFK( true );
		txtCodAtendo.setNomeCampo( "CodAtendo" );
		lcAtendimento.add( new GuardaCampo( txtCodAtendo, "CodAtendo", "Cód.atendo", ListaCampos.DB_PK, false ) );
		lcAtendimento.add( new GuardaCampo( txtCodCli, "CodCli", "Cód.Cli.", ListaCampos.DB_FK, false ) );
		lcAtendimento.add( new GuardaCampo( txtCodAtend, "CodAtend", "Cod.Atend.", ListaCampos.DB_FK, false ) );
		lcAtendimento.add( new GuardaCampo( txtCodChamado, "CodChamado", "Cód.Chamado", ListaCampos.DB_FK, false ) );
		lcAtendimento.add( new GuardaCampo( txtDataAtendimento, "dataAtendo", "Data atendimento", ListaCampos.DB_SI, false ) );
		lcAtendimento.add( new GuardaCampo( txtDataAtendimentoFin, "dataAtendoFin", "Data atendimento fin.", ListaCampos.DB_SI, false ) );
		lcAtendimento.add( new GuardaCampo( txtHoraini, "HoraAtendo", "Hora atendimento", ListaCampos.DB_SI, false ) );
		lcAtendimento.add( new GuardaCampo( txtHorafim, "HoraAtendoFin", "Hora atendimento fin.", ListaCampos.DB_SI, false ) );
		lcAtendimento.add( new GuardaCampo( txaObsAtend, "ObsAtendo", "Descrição", ListaCampos.DB_SI, false ) );
		lcAtendimento.add( new GuardaCampo( txtCodTpAtendo, "codtpatendo", "Tipo", ListaCampos.DB_FK, false ) );
		lcAtendimento.add( new GuardaCampo( txtCodsetat, "codsetat", "setor", ListaCampos.DB_FK, false ) );
		lcAtendimento.add( new GuardaCampo( txtCodContr, "codcontr", "Codcontrato", ListaCampos.DB_FK, false ) );
		lcAtendimento.add( new GuardaCampo( txtCodItContr, "coditcontr", "item do contrato", ListaCampos.DB_FK, false ) );
		lcAtendimento.add( new GuardaCampo( txtCodTarefa, "Codtarefa", "Cód.Tarefa", ListaCampos.DB_FK, false ) );
		lcAtendimento.add( new GuardaCampo( txtStatusAtendo, "statusatendo", "Status do atendimento", ListaCampos.DB_SI, false ) );
		lcAtendimento.add( new GuardaCampo( txaObsInterno, "obsinterno", "Observação interna", ListaCampos.DB_SI, false ) );
		lcAtendimento.add( new GuardaCampo( txtCodEspec, "codespec", "Cód.Espec.", ListaCampos.DB_FK, !financeiro ) );
		
		lcAtendimento.montaSql( false, "ATENDIMENTO", "AT" );
		lcAtendimento.setReadOnly( true );

		txtCodChamado.setTabelaExterna( lcChamado, null );
		txtCodChamado.setFK( true );
		txtCodChamado.setNomeCampo( "CodChamado" );
		lcChamado.add( new GuardaCampo( txtCodChamado, "CodChamado", "Cód.Chamado", ListaCampos.DB_PK, false ) );
		lcChamado.add( new GuardaCampo( txtDescChamado, "DescChamado", "Descrição do chamado", ListaCampos.DB_SI, false ) );
		lcChamado.add( new GuardaCampo( txtCodContrCh, "codcontr", "Codcontrato", ListaCampos.DB_SI, false ) );
		lcChamado.add( new GuardaCampo( txtCodItContrCh, "coditcontr", "item do contrato", ListaCampos.DB_SI, false ) );
		lcChamado.add( new GuardaCampo( txtCodCli2, "CodCli", "Cód.Cli.", ListaCampos.DB_SI, false ) );
		lcChamado.setDinWhereAdic( " STATUS NOT IN('CO','CA') AND CODCLI=#N", txtCodCli );
		lcChamado.montaSql( false, "CHAMADO", "CR" );
		lcChamado.setReadOnly( true );


		txtCodEspec.setTabelaExterna( lcEspec, null );
		txtCodEspec.setFK( true );
		txtCodEspec.setNomeCampo( "CodEspec" );
		lcEspec.add( new GuardaCampo( txtCodEspec, "CodEspec", "Cód.Espec.", ListaCampos.DB_PK, !financeiro ) );
		lcEspec.add( new GuardaCampo( txtDescEspec, "DescEspec", "Descrição da especificação", ListaCampos.DB_SI, false ) );
		lcEspec.add( new GuardaCampo( txtObrigChamEspec, "ObrigChamEspec", "Chamado Obrigatório no Atendimento", ListaCampos.DB_SI, false ) );
		lcEspec.add( new GuardaCampo( txtObrigProjEspec, "ObrigProjEspec", "Contrato/Projeto Obrigatório no Atendimento", ListaCampos.DB_SI, false ) );
		lcEspec.add( new GuardaCampo( txtCobcliEspec, "CobCliEspec", "Cobrável", ListaCampos.DB_SI, false ) );
		lcEspec.montaSql( false, "ESPECATEND", "AT" );
		lcEspec.setReadOnly( true );
		
		txtCodTpAtendo.setTabelaExterna( lcTpAtendo, null );
		txtCodTpAtendo.setNomeCampo( "CodTpAtendo" );
		lcTpAtendo.add( new GuardaCampo( txtCodTpAtendo, "CodTpAtendo", "Cód.tp.atendo.", ListaCampos.DB_PK, false) );
		lcTpAtendo.add( new GuardaCampo( txtDescTpAtendo, "DescTpAtendo", "Descrição do Tipo de Atendimento", ListaCampos.DB_SI, false ) );
		lcTpAtendo.add( new GuardaCampo( txtAtivoAtendo, "AtivoAtendo", "Atendimento Ativo", ListaCampos.DB_SI, false ) );
		lcTpAtendo.setWhereAdic( " ATIVOATENDO='S' AND TIPOATENDO='"+tipoatendo+"' ");
		//lcTpAtendo.setDinWhereAdic( " TIPOATENDO='"+tipoatendo+"'" );
		lcTpAtendo.montaSql( false, "TIPOATENDO", "AT" );
		lcTpAtendo.setReadOnly( true );

		txtCodsetat.setTabelaExterna( lcSetor, null );
		txtCodsetat.setFK( true );
		txtCodsetat.setNomeCampo( "CodSetAt" );
		lcSetor.add( new GuardaCampo( txtCodsetat, "CodSetAt", "Cód.Setor", ListaCampos.DB_PK, false) );
		lcSetor.add( new GuardaCampo( txtDescSetor, "DescSetAt", "Descrição do Setor", ListaCampos.DB_SI, false ) );
		lcSetor.montaSql( false, "SETOR", "AT" );
		//lcSetor.setDinWhereAdic( "CodTpAtendo=#N", txtCodTpAtendo );
		txtCodTpAtendo.setFK( true );
		lcSetor.setReadOnly( true );
		
		txtCodContr.setTabelaExterna( lcContrato, null );
		txtCodContr.setFK( true );
		txtCodContr.setNomeCampo( "CodContr" );
		lcContrato.add( new GuardaCampo( txtCodContr, "CodContr", "Cód.Contrato", ListaCampos.DB_PK, false ) );
		lcContrato.add( new GuardaCampo( txtDescContr, "DescContr", "Desc.Contr.", ListaCampos.DB_SI, false ) );
		lcContrato.add( new GuardaCampo( txtRecebContr, "Recebivel", "Recebível", ListaCampos.DB_SI, false ) );
		
		lcContrato.setDinWhereAdic( " CODCLI=#N", txtCodCli );
		lcContrato.montaSql( false, "CONTRATO", "VD" );

		//lcContrato.setQueryCommit( false );
		lcContrato.setReadOnly( true );

		txtCodItContr.setTabelaExterna( lcItContrato, null );
		txtCodItContr.setFK( true );
		txtCodItContr.setNomeCampo( "CodItContr" );
		lcItContrato.add( new GuardaCampo( txtCodItContr, "CodItContr", "Cód.It.Contr.", ListaCampos.DB_PK, false ) );
		lcItContrato.add( new GuardaCampo( txtCodContr, "CodContr", "Cód.Contrato", ListaCampos.DB_PK, false ) );
		lcItContrato.add( new GuardaCampo( txtDescItContr, "DescItContr", "Desc.It.Contr.", ListaCampos.DB_SI, false ) );
		lcItContrato.setDinWhereAdic( " CodContr=#N", txtCodContr );
		lcItContrato.montaSql( false, "ITCONTRATO", "VD" );
	
		//lcItContrato.setQueryCommit( false );
		lcItContrato.setReadOnly( true );
		
		
		//FK Tarefa.
		
		txtCodTarefa.setTabelaExterna( lcTarefa, null );
		txtCodTarefa.setFK( true );
		txtCodTarefa.setNomeCampo( "CodTarefa" );
		lcTarefa.add( new GuardaCampo( txtCodTarefa, "CodTarefa", "Cód.Tarefa", ListaCampos.DB_PK, false ) );
		lcTarefa.add( new GuardaCampo( txtDescTarefa, "DescTarefa", "Descrição da tarefa", ListaCampos.DB_SI, false ) );
		lcTarefa.setWhereAdic( " LanctoTarefa='S' " );
		lcTarefa.setDinWhereAdic( " CodContr=#N", txtCodContr );
		lcTarefa.setDinWhereAdic( " CodItContr=#N", txtCodItContr);
		lcTarefa.montaSql( false, "TAREFA", "CR" );		
		lcTarefa.setReadOnly( true );

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

	private void insertIntervaloAtend(String horaini, String horafim) {
		try {
			daoatend.insertIntervaloAtend( Aplicativo.iCodEmp, ListaCampos.getMasterFilial( "ATATENDIMENTO" ), 
					txtDataAtendimento.getVlrDate(), txtDataAtendimentoFin.getVlrDate(),
					horaini, horafim, 
					Aplicativo.iCodEmp, ListaCampos.getMasterFilial( "ATATENDENTE" ), txtCodAtend.getVlrInteger(), 
					Aplicativo.iCodEmp, ListaCampos.getMasterFilial( "SGUSUARIO" ), Aplicativo.strUsuario );
						
		}
		catch (Exception e) {
			Funcoes.mensagemErro( this, "Erro inserindo lançamento automatizado de intervalo !\n" + e.getMessage() );
			e.printStackTrace();
		}
		
	}
	
/*	private void insertIntervalo(String horaini, String horafim) {
		
		StringBuilder sql = new StringBuilder();
		PreparedStatement ps = null;
		
		try {
		
			sql.append( "insert into atatendimento ( " );
			
			sql.append( "CODEMP, CODFILIAL, CODATENDO, CODEMPTO, CODFILIALTO, CODTPATENDO, CODEMPAE, CODFILIALAE, CODATEND," );
			sql.append( "CODEMPSA, CODFILIALSA, CODSETAT, DATAATENDO, DATAATENDOFIN, HORAATENDO, HORAATENDOFIN, OBSATENDO, OBSINTERNO, STATUSATENDO," );
			sql.append( "CODEMPCL, CODFILIALCL, CODCLI, CODEMPUS, CODFILIALUS, IDUSU, CODEMPCT, CODFILIALCT, CODCONTR, CODITCONTR," );
			sql.append( "CODEMPCA, CODFILIALCA, CODCLASATENDO, CODEMPCH, CODFILIALCH, CODCHAMADO, CODEMPEA, CODFILIALEA, CODESPEC, DOCATENDO ) " );
			
			sql.append( " select " );
			
			sql.append( "?, ?, (select iseq from spgeranum(?, ?, 'AT' )), " );
			sql.append( "atd.codempto, atd.codfilialto, atd.codtpatendo, ?, ?, ?, " );
			sql.append( "atd.codempsa, atd.codfilialsa, atd.codsetat, ?, ?, ?, ?, atd.obsatendo, atd.obsinterno, atd.statusatendo, " );
			sql.append( "atd.codempcl, atd.codfilialcl, atd.codcli, ?, ?, ?, atd.codempct, atd.codfilialct, atd.codcontr, atd.coditcontr, " );
			sql.append( "atd.codempca, atd.codfilialca, atd.codclasatendo, atd.codempch, atd.codfilialch, atd.codchamado, atd.codempea, atd.codfilialea, atd.codespec, ? " );
			sql.append( "from sgprefere3 p3, atmodatendo atd " );
			sql.append( "where " );
			sql.append( "p3.codemp=? and p3.codfilial=? and atd.codemp=p3.codempmi and atd.codfilial=p3.codfilialmi and atd.codmodel=p3.codmodelmi " );
			
			System.out.println("QUERY INSERT:" + sql.toString() );
			
			ps = con.prepareStatement( sql.toString() );

			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "ATATENDIMENTO" ) );
			
			ps.setInt( 3, Aplicativo.iCodEmp );
			ps.setInt( 4, ListaCampos.getMasterFilial( "ATATENDIMENTO" ) );

			ps.setInt( 5, Aplicativo.iCodEmp );
			ps.setInt( 6, ListaCampos.getMasterFilial( "ATATENDENTE" ) );
			
			ps.setInt( 7, txtCodAtend.getVlrInteger() );
		
			ps.setDate( 8, Funcoes.dateToSQLDate( txtDataAtendimento.getVlrDate()) ); // Data de inicio do atendimento
			ps.setDate( 9, Funcoes.dateToSQLDate( txtDataAtendimento.getVlrDate()) ); // Data final do atendimento
			ps.setTime( 10, Funcoes.strTimeTosqlTime( horaini )); // Hora inicial do atendimento
			ps.setTime( 11, Funcoes.strTimeTosqlTime( horafim )); // Hora final do atendimento
			ps.setInt( 12, Aplicativo.iCodEmp );
			ps.setInt( 13, ListaCampos.getMasterFilial( "SGUSUARIO" ) );
			ps.setString( 14, Aplicativo.strUsuario );
			ps.setInt( 15, 0 );
			ps.setInt( 16, Aplicativo.iCodEmp );
			ps.setInt( 17, ListaCampos.getMasterFilial( "SGPREFERE3" ) );
			
			ps.execute(); 
			
			con.commit();
						
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	*/
	private void verificaAtendimentoAnterior(Integer codatend, Date data, String hora) {

 		StringBuilder sql = new StringBuilder();
		PreparedStatement ps = null;
		ResultSet rs = null;

		String hora_ini_intervalo = null;

		Long diferenca_lanca = 0L;
		Long diferenca_turno = 0L;
		Long diferenca = 0L;
		
		String ini_turno = null;
		String fim_turno = null;

		String ini_inter = null;
		String fim_inter = null;
		
		hora = hora.substring( 0,5 );
		
		boolean turno = false;
		
		boolean tem_lancamento = false;

		boolean teste = false;
		
		try {

			sql.append( "select first 1 ");
			sql.append( "atd.dataatendofin, atd.horaatendofin ");
			sql.append( "from atatendimento atd ");
			sql.append( "where ");
			sql.append( "atd.codemp=? and atd.codfilial=? and atd.codempae=? and atd.codfilialae=? and atd.codatend=? and ");
			sql.append( "atd.dataatendofin = ? and atd.horaatendofin <= ? and ");
			sql.append( "atd.codatendo<>? ");
			sql.append( "order by dataatendofin desc, horaatendofin desc " );

			System.out.println("QUERY ULTIMO ATENDIMENTO:" + sql.toString());
			
			ps = con.prepareStatement( sql.toString() );

			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "ATATENDIMENTO" ) );

			ps.setInt( 3, Aplicativo.iCodEmp );
			ps.setInt( 4, ListaCampos.getMasterFilial( "ATATENDENTE" ) );
			ps.setInt( 5, codatend );

			ps.setDate( 6, Funcoes.dateToSQLDate( data ) );
			ps.setTime( 7, Funcoes.strTimeTosqlTime( hora )  );
			ps.setInt( 8, txtCodAtendo.getVlrInteger()  );

			
			rs = ps.executeQuery();

			if(rs.next()) {

				String horafinant = rs.getString( "horaatendofin" );
				horafinant = horafinant.substring( 0,5 );

				System.out.println( "Hora do último lançamento:" + horafinant.toString() );

				diferenca_lanca = Funcoes.subtraiTime( Funcoes.strTimeTosqlTime( horafinant), Funcoes.strTimeTosqlTime( hora ) );

				System.out.println( "Diferença:" + Funcoes.longTostrTime( diferenca_lanca ) );

				hora_ini_intervalo = horafinant;
				
				tem_lancamento = true;

			}

			con.commit();
			rs.close();


			// Verificação do enquadramento no turno

			sql = new StringBuilder();

			sql.append( "select ");
			sql.append( "tu.hiniturno, tu.hfimturno, tu.hiniintturno, tu.hfimintturno ");

			sql.append( "from ");
			sql.append( "atatendente ate ");
			sql.append( "left outer join rhempregado em on em.codemp=ate.codempep and em.codfilial=ate.codfilialep and em.matempr=ate.matempr ");
			sql.append( "left outer join rhturno tu on tu.codemp=em.codempto and tu.codfilial=em.codfilialto and tu.codturno=em.codturno ");
			sql.append( "where ate.codemp=? and ate.codfilial=? and ate.codatend=? " );

			ps = con.prepareStatement( sql.toString() );

			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "ATATENDENTE" ) );
			ps.setInt( 3, codatend );

			rs = ps.executeQuery();

			if(rs.next()) {

				ini_turno = rs.getString( "hiniturno" );
				fim_turno = rs.getString( "hfimturno" );

				ini_inter = rs.getString( "hiniintturno" );
				fim_inter = rs.getString( "hfimintturno" );

				if ( (fim_inter!=null ) && (ini_inter != null ) && (ini_turno != null  ) ) {
				// Verifica se o lançamento é após o fim do intervalo...
				
					
					diferenca_turno = Funcoes.subtraiTime( Funcoes.strTimeTosqlTime( fim_inter ), Funcoes.strTimeTosqlTime( hora ) );
				
					//hora_ini_intervalo =  fim_inter;

					if(diferenca_turno < 0 ) {

						// Se o lançamento é anterior ao fim do intervalo deve verificar se é anterior ao início do intervalo...
						diferenca_turno = Funcoes.subtraiTime( Funcoes.strTimeTosqlTime( ini_inter ), Funcoes.strTimeTosqlTime( hora ) );

						if(diferenca_turno < 0) {

							// Indica que o lançamento é anterior ao inicio do intervalo, ou seja deve obedecer o inicio do turno...							
							diferenca_turno = Funcoes.subtraiTime( Funcoes.strTimeTosqlTime( ini_turno ), Funcoes.strTimeTosqlTime( hora ) );

							//hora_ini_intervalo =  ini_turno;
						}						
					}	
				}

			}
			
			con.commit();
			rs.close();

			String texto_dif = "";

			if(tem_lancamento) { 
			
				//sobrepondo a diferença do turno
				if( ( diferenca_lanca > 1) && ( !"00:00:00".equals( Funcoes.longTostrTime(diferenca_lanca) ) ) ) {
					
					diferenca = diferenca_lanca;
					
					texto_dif = "seu último lançamento";
					
					turno = false;
					
				}
				
			}
			else {
				if( diferenca_turno >0 ) {
					
					diferenca = diferenca_turno;
					
					texto_dif = "o início do seu turno";
					
					turno = true;
					
				}
			}
			
			
			if(diferenca > 0) {

				StringBuilder mensagem = new StringBuilder();

				mensagem.append( "Existe um intervalo de " );
				mensagem.append( Funcoes.longTostrTime( diferenca ) );
				mensagem.append( " entre " );
				
				mensagem.append( texto_dif );
				
				mensagem.append( " e o lançamento atual.\n ");
				
				mensagem.append( "Gostaria de inserir o intervalo automaticamente?" );

				if( Funcoes.mensagemConfirma( null, mensagem.toString() ) == JOptionPane.YES_OPTION ) {
					
					// Inserir atendimento padrão para intervalos
					teste = true;
					//insertIntervalo( turno ? ini_turno : hora_ini_intervalo , txtHoraini.getVlrString() );
					

				}
				else {

					return;

				}

			}

			
			if(teste) {
				insertIntervaloAtend( turno ? ini_turno : hora_ini_intervalo , txtHoraini.getVlrString() );
			}


		}
		catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void insert() throws Exception {
		
		org.freedom.modulos.crm.business.object.Atendimento atd = new org.freedom.modulos.crm.business.object.Atendimento();

		atd.setCodemp( Aplicativo.iCodEmp );
		atd.setCodfilial( ListaCampos.getMasterFilial( "ATATENDIMENTO" ));
		
		atd.setCodempto( Aplicativo.iCodEmp );
		atd.setCodfilialto( ListaCampos.getMasterFilial( "ATTIPOATENDO" ));
		atd.setCodtpatendo( txtCodTpAtendo.getVlrInteger() );
		
		atd.setCodempca( Aplicativo.iCodEmp );
		atd.setCodfilialca( ListaCampos.getMasterFilial( "ATCLASATENDO" ));
		
		if (txtCodCli.getVlrInteger().intValue()!=0) {	
			atd.setCodempcl( Aplicativo.iCodEmp );
			atd.setCodfilialcl( ListaCampos.getMasterFilial( "VDCLIENTE" ));
			atd.setCodcli( txtCodCli.getVlrInteger() );
		}
		
		atd.setCodempcv( Aplicativo.iCodEmp );
		atd.setCodfilialcv( ListaCampos.getMasterFilial( "ATCONVENIADO" ));
		
		atd.setCodempae( Aplicativo.iCodEmp );
		atd.setCodfilialae( ListaCampos.getMasterFilial( "ATATENDENTE" ));
		atd.setCodatend( txtCodAtend.getVlrInteger() ); // Código do atendente logado
		
		atd.setCodempus( Aplicativo.iCodEmp );
		atd.setCodfilialus( ListaCampos.getMasterFilial( "SGUSUARIO" )); // Id do usuário logado
		atd.setIdusu( Aplicativo.strUsuario );
		
		atd.setCodempsa( Aplicativo.iCodEmp );
		atd.setCodfilialsa( ListaCampos.getMasterFilial( "ATATENDENTE" ));
		atd.setCodsetat( txtCodsetat.getVlrInteger() ); // Setor de atendimento 
		
		atd.setDocatendo( String.valueOf( iDoc ) );
		atd.setCodatendo( txtCodAtendo.getVlrInteger() );
		atd.setDataatendo( txtDataAtendimento.getVlrDate() );
		atd.setDataatendofin( txtDataAtendimentoFin.getVlrDate() );
		atd.setHoraatendo( txtHoraini.getVlrString() );
		atd.setHoraatendofin( txtHorafim.getVlrString() );
		atd.setObsatendo( txaObsAtend.getVlrString() );
		atd.setObsinterno( txaObsInterno.getVlrString() );
		atd.setConcluichamado( cbConcluiChamado.getVlrString() );
		
		if (txtCodContr.getVlrInteger().intValue()!= 0) {		
			atd.setCodempct( Aplicativo.iCodEmp );
			atd.setCodfilialct( ListaCampos.getMasterFilial( "VDCONTRATO" ));
			atd.setCodcontr( txtCodContr.getVlrInteger() );
			atd.setCoditcontr( txtCodItContr.getVlrInteger() );
		}

		if (txtCodChamado.getVlrInteger().intValue()!=0) {
			atd.setCodempch( Aplicativo.iCodEmp );
			atd.setCodfilialch( ListaCampos.getMasterFilial( "CRCHAMADO" ));
			atd.setCodchamado( txtCodChamado.getVlrInteger() );
		//	atd.setCodcli( txtCodCli2.getVlrInteger()  );
		}

		if (txtCodEspec.getVlrInteger().intValue()!=0) {	
			atd.setCodempea( Aplicativo.iCodEmp );
			atd.setCodfilialea( ListaCampos.getMasterFilial( "ATESPECATEND" ));
			atd.setCodespec( txtCodEspec.getVlrInteger() );
		}
		if (txtCodTarefa.getVlrInteger().intValue()!=0) {	
			atd.setCodempta( Aplicativo.iCodEmp );
			atd.setCodfilialta( ListaCampos.getMasterFilial( "CRTAREFA" ));
			atd.setCodtarefa( txtCodTarefa.getVlrInteger() );
		}

		if (codrec!=null) {			
			atd.setCodempir( Aplicativo.iCodEmp );
			atd.setCodfilialir( ListaCampos.getMasterFilial( "FNRECEBER" ));
			atd.setCodrec( codrec );
			atd.setNparcitrec( nparcitrec );
		}
		
		daoatend.insert( atd );

		if(corig instanceof FCRM) {
			(( FCRM ) corig).carregaAtendimentos();	
		}
		
	}

	private void update() throws Exception {
		org.freedom.modulos.crm.business.object.Atendimento atd = new org.freedom.modulos.crm.business.object.Atendimento();
		
		if( txtCodAtend.getVlrInteger() != null ){
			atd.setCodatend( txtCodAtend.getVlrInteger() );
		}
		atd.setCodemp( Aplicativo.iCodEmp );
		atd.setCodfilial( ListaCampos.getMasterFilial( "ATATENDIMENTO" ) );
		
		atd.setCodempto( Aplicativo.iCodEmp );
		atd.setCodfilialto( ListaCampos.getMasterFilial( "ATTIPOATENDO" ) );
		atd.setCodtpatendo( txtCodTpAtendo.getVlrInteger() );
		
		atd.setCodempca( Aplicativo.iCodEmp );
		atd.setCodfilialca( ListaCampos.getMasterFilial( "ATCLASATENDO" ));
		
		if (txtCodCli.getVlrInteger().intValue()!= 0) {	
			atd.setCodempcl( Aplicativo.iCodEmp );
			atd.setCodfilialcl( ListaCampos.getMasterFilial( "VDCLIENTE" ));
			atd.setCodcli( txtCodCli.getVlrInteger() );
		}
		
		atd.setCodempae( Aplicativo.iCodEmp );
		atd.setCodfilialae( ListaCampos.getMasterFilial( "ATATENDENTE" ));
		atd.setCodatend( txtCodAtend.getVlrInteger() ); // Código do atendente logado
		
		atd.setCodempcv( Aplicativo.iCodEmp );
		atd.setCodfilialcv( ListaCampos.getMasterFilial( "ATCONVENIADO" ));
		
		atd.setCodempus( Aplicativo.iCodEmp );
		atd.setCodfilialus( ListaCampos.getMasterFilial( "SGUSUARIO" )); // Id do usuário logado
		atd.setIdusu( Aplicativo.strUsuario );
		
		atd.setCodempsa( Aplicativo.iCodEmp );
		atd.setCodfilialsa( ListaCampos.getMasterFilial( "ATSETOR" ) );
		atd.setCodsetat(  txtCodsetat.getVlrInteger() );

		atd.setCodatendo( txtCodAtendo.getVlrInteger() ); //Código do atendimento.
		
		atd.setDocatendo( String.valueOf( iDoc ) );
		atd.setDataatendo( txtDataAtendimento.getVlrDate() );
		atd.setDataatendofin( txtDataAtendimentoFin.getVlrDate() );
		atd.setHoraatendo( txtHoraini.getVlrString() );
		atd.setHoraatendofin( txtHorafim.getVlrString() );
		atd.setObsatendo( txaObsAtend.getVlrString() );
		atd.setObsinterno( txaObsInterno.getVlrString() ); //Observações internas
		atd.setConcluichamado( cbConcluiChamado.getVlrString() );

		if ( txtCodChamado.getVlrInteger().intValue()!= 0 ) {
			atd.setCodempch( Aplicativo.iCodEmp ); // Código da empresa do chamando
			atd.setCodfilialch( ListaCampos.getMasterFilial( "CRCHAMADO" )); // Código da filial da empresa do chamado
			atd.setCodchamado( txtCodChamado.getVlrInteger() ); // Código do chamado
		//	atd.setCodcli( txtCodCli2.getVlrInteger() );
		}
	
		if ( txtCodItContr.getVlrInteger().intValue()!= 0) {		
			atd.setCodempct( Aplicativo.iCodEmp );
			atd.setCodfilialct( ListaCampos.getMasterFilial( "VDCONTRATO" ));
			atd.setCodcontr( txtCodContr.getVlrInteger() ); // Código do Contrato
			atd.setCoditcontr( txtCodItContr.getVlrInteger() ); //Código do item do Contrato
		}
		
		atd.setStatusatendo( cbStatus.getVlrString() );
		
		if (txtCodEspec.getVlrInteger().intValue() != 0) {	
			atd.setCodempea( Aplicativo.iCodEmp );
			atd.setCodfilialea( ListaCampos.getMasterFilial( "ATESPECATEND" ));
			atd.setCodespec( txtCodEspec.getVlrInteger() );
		}
		
		if (txtCodTarefa.getVlrInteger().intValue()!=0) {	
			atd.setCodempta( Aplicativo.iCodEmp );
			atd.setCodfilialta( ListaCampos.getMasterFilial( "CRTAREFA" ));
			atd.setCodtarefa( txtCodTarefa.getVlrInteger() );
		}
		
		if (codrec!=null) {			
			atd.setCodempir( Aplicativo.iCodEmp );
			atd.setCodfilialir( ListaCampos.getMasterFilial( "FNRECEBER" ));
			atd.setCodrec( codrec );
			atd.setNparcitrec( nparcitrec );
		}

		daoatend.update( atd );
		
		if(corig instanceof FCRM) {
			
			(( FCRM ) corig).carregaAtendimentos();	
			
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
	private boolean consistForm(){
		boolean result = true;
		
		if ( txtDataAtendimento.getVlrDate().after( txtDataAtendimentoFin.getVlrDate() ) ) {
			Funcoes.mensagemInforma( this, "Data final menor que a data inicial!" );
			txtDataAtendimento.requestFocus();
			result = false;
		}
		else if ("".equals( txtCodCli.getText().trim() ) ){
			Funcoes.mensagemInforma( this, "Selecione o Cliente!" );
			txtCodCli.requestFocus();
			result = false;
		}
		
		else if ( "S".equals( txtObrigChamEspec.getVlrString() ) && ( "".equals(txtCodChamado.getText().trim() ) ) ) {
			Funcoes.mensagemInforma( this, "Código do Chamado não foi selecionado!" );
			txtCodChamado.requestFocus();
			result = false;
		}
		else if ( "".equals( txtCodAtend.getText().trim() ) ) {
			Funcoes.mensagemInforma( this, "Selecione o atendente!" );
			txtCodAtend.requestFocus();
			result = false;
		}
		else if ( "".equals( txtCodTpAtendo.getText().trim() ) ) {
			Funcoes.mensagemInforma( this, "O tipo de atendimento não foi selecionado!" );
			txtCodTpAtendo.requestFocus();
			result = false;
		}
		else if ( "".equals( txtCodsetat.getText().trim() ) ) {
			Funcoes.mensagemInforma( this, "O setor não foi selecionado!" );
			txtCodsetat.requestFocus();
			result = false;
		}
		else if ( "S".equals( txtObrigProjEspec.getVlrString() ) && 
				(( "".equals( txtCodContr.getText().trim() )) || ("".equals( txtCodItContr.getText().trim() ))) ){
			if ( "".equals( txtCodContr.getText().trim() ) ) {
				Funcoes.mensagemInforma( this, "Contrato/Projeto não foi selecionado!" );
				txtCodContr.requestFocus();
			}
			else if ( "".equals( txtCodItContr.getText().trim() ) ){
				Funcoes.mensagemInforma( this, "O item do Contrato/Projeto não foi selecionado!" );
				txtCodItContr.requestFocus();
			}
			result = false;
		}
		else if ( txtDataAtendimento.getVlrString().equals( "" ) ) {
			Funcoes.mensagemInforma( this, "Data inicial é requerida !" );
			txtDataAtendimento.requestFocus();
			result = false;
		}
		else if ( txtDataAtendimentoFin.getVlrString().equals( "" ) ) {
			Funcoes.mensagemInforma( this, "Data final é requerida !" );
			txtDataAtendimentoFin.requestFocus();
			result = false;
		}
		else if ( txtHoraini.getVlrString().equals( "" ) ) {
			Funcoes.mensagemInforma( this, "Hora inicial é requerida !" );
			txtHoraini.requestFocus();
			result = false;
		}
		else if ( txtHorafim.getVlrString().equals( "" ) ) {
			Funcoes.mensagemInforma( this, "Hora final é requerida !" );
			txtHorafim.requestFocus();
			result = false;
		}
		else if(txtHoraini.getVlrTime().compareTo( txtHorafim.getVlrTime() ) >= 0 )  {
			Funcoes.mensagemInforma( null, "Horário inicial deve ser menor que horário final !");
			result = false;
		}	
		else if( "".equals(txtCodEspec.getText().trim() ) && !financeiro) {
			Funcoes.mensagemInforma(null,"Informe a especificação do atendimento !");
			txtCodEspec.requestFocus();
			result = false;
		}
		else if ( txaObsAtend.getVlrString().equals( "" ) ) {
			Funcoes.mensagemInforma( this, "Não foi digitado nenhum procedimento !" );
			txaObsAtend.requestFocus();
			result = false;
		}
		else if ( "S".equals(txtCobcliEspec.getVlrString()) && !"S".equals( txtRecebContr.getVlrString() ) ) {
			Funcoes.mensagemInforma( this, "Esta especificação não pode ser utilizada para projeto interno !" );
			txtCodEspec.requestFocus();
			result = false;
		}
			
		return result;
	}

	private boolean gravaForm(){
		boolean result = true;
		if ( update ) { 
			try {

				update();

				verificaAtendimentoAnterior( txtCodAtend.getVlrInteger(), txtDataAtendimento.getVlrDate(), txtHoraini.getVlrString()+":01" );

			} 
			catch ( Exception e ) {
				Funcoes.mensagemInforma( this, "Erro ao gravar o atendimento!\n" + e.getMessage() );
				e.printStackTrace();
				result = false;
			}

		}
		else {
			try {

				insert();

				verificaAtendimentoAnterior( txtCodAtend.getVlrInteger(), txtDataAtendimento.getVlrDate(), txtHoraini.getVlrString()+":01"  );

			} 
			catch ( Exception e ) {
				e.printStackTrace();
				Funcoes.mensagemInforma( this, "Erro ao gravar o atendimento!\n" + e.getMessage() );
				result = false;
			}
			
		}
		
		sinalizaChamado( false, txtCodChamado.getVlrInteger() );	
		
		return result;
	}
	
	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == btOK ) {
			if( consistForm() ){
				if( gravaForm() ){
					super.actionPerformed( evt );
				}
			}
		}
		else if ( evt.getSource() == btRun ) {
			iniciaContagem();
		}
		else if ( evt.getSource() == btCancel ) {
			sinalizaChamado( false, txtCodChamado.getVlrInteger() );
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
/*
	public void valorAlterado( JComboBoxEvent evt ) {

		if ( evt.getComboBoxPad() == txtCodTpAtendo.getVlrInteger() ) {
			montaComboSetor();
		}
		
		if ( evt.getComboBoxPad() == cbContrato ) {
			HashMap<String, Vector<Object>> vals = FuncoesCRM.montaComboItContr( con, cbContrato.getVlrInteger(), "<Sem contrato>" );
			cbitContrato.setItensGeneric( (Vector<?>) vals.get( "LAB" ), (Vector<?>) vals.get( "VAL" ) );
		}
	}
*/

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );

		//montaComboTipo();
		//montaComboSetor();
		montaComboStatus();

		lcAtendimento.setConexao( cn );
		
		lcAtend.setConexao( cn );

		lcCli.setConexao( cn );

		lcChamado.setConexao( cn );
	
		lcTpAtendo.setConexao( cn );
		
		lcSetor.setConexao( cn );
		
		lcContrato.setConexao( cn );
		
		lcItContrato.setConexao( cn );
	
		lcTarefa.setConexao( cn );
		
		lcEspec.setConexao( cn );

		lcChamado.carregaDados();
		lcCli.carregaDados();
		lcContrato.carregaDados();
		lcItContrato.carregaDados();
		
		daoatend = new DAOAtendimento( cn );
		try {
			daoatend.setPrefs( Aplicativo.iCodEmp, ListaCampos.getMasterFilial( "SGPREFERE3" ) );
		} catch (SQLException e) {
			Funcoes.mensagemErro( this, "Erro carregando preferências !\b" + e.getMessage() );
		}
		
	}

	/*public Object[] getValores() {

		Object[] sVal = new Object[ 13 ];

		sVal[ 0 ] = String.valueOf( cbTipo.getVlrInteger() );
		sVal[ 1 ] = txtCodAtend.getVlrString();
		sVal[ 2 ] = String.valueOf( cbSetor.getVlrInteger() );
		sVal[ 3 ] = String.valueOf( iDoc );
		sVal[ 4 ] = txtDataAtendimento.getVlrDate();
		sVal[ 5 ] = txtDataAtendimentoFin.getVlrDate();
		sVal[ 6 ] = txtHoraini.getVlrString();
		sVal[ 7 ] = txtHorafim.getVlrString();
		sVal[ 8 ] = txaDescAtend.getVlrString();
		sVal[ 9 ] = cbContrato.getVlrInteger();
		sVal[ 10 ] = cbitContrato.getVlrInteger();
		sVal[ 11 ] = txtCodChamado.getVlrInteger();
		sVal[ 12 ] = txtCodEspec.getVlrInteger();

		return sVal;

	}
*/
	private void sinalizaChamado( boolean em_atendimento, Integer codchamado ) {

		StringBuilder sql = new StringBuilder(); 

		try {

			if ( codchamado != null ) {

				sql.append( "update crchamado set ematendimento=? " );
				sql.append( "where codemp=? and codfilial=? and codchamado=? " );

				PreparedStatement ps = con.prepareStatement( sql.toString() );

				ps.setString( 1, em_atendimento ? "S" : "N" );

				ps.setInt( 2, lcChamado.getCodEmp() );
				ps.setInt( 3, lcChamado.getCodFilial() );

				ps.setInt( 4, codchamado );

				ps.executeUpdate();

				con.commit();
			}

		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}
	public void setContratos(){
		
		txtCodContr.setVlrInteger( txtCodContrCh.getVlrInteger() ); 
		txtCodItContr.setVlrInteger(txtCodItContrCh.getVlrInteger());
		lcContrato.carregaDados();
		lcItContrato.carregaDados();
	}

	public void afterCarrega( CarregaEvent cevt ) {
		
		Integer codsetat = null;
		if ( cevt.getListaCampos() == lcChamado ) { 

			sinalizaChamado( true, txtCodChamado.getVlrInteger() );
			// Guardando o chamado sinalizado
			codchamado_ant = txtCodChamado.getVlrInteger();
			
			if(txtCodItContrCh.getVlrInteger() > 0){
				setContratos();
			}

		} else if (cevt.getListaCampos() == lcEspec ){
			if( "S".equals( txtObrigProjEspec.getVlrString() ) ){
				txtCodContr.setRequerido( true );
				txtCodItContr.setRequerido( true );
			} else {
				txtCodContr.setRequerido( false );
				txtCodItContr.setRequerido( false );
			}
			if( "S".equals( txtObrigChamEspec.getVlrString() ) ){
				txtCodChamado.setRequerido( true );
			} else {
				txtCodChamado.setRequerido( false );
			}
		} else if (cevt.getListaCampos() == lcAtend ) {
			if ( "".equals(txtCodsetat.getVlrString().trim()) ) {
				codsetat = daoatend.locateSetor( 
						Aplicativo.iCodEmp, ListaCampos.getMasterFilial( "ATSETOR" ), 
						Aplicativo.iCodEmp, ListaCampos.getMasterFilial( "ATATENDENTE" ), 
						txtCodAtend.getVlrInteger() );
				if (codsetat!=null) {
					txtCodsetat.setVlrInteger( codsetat );
					lcSetor.carregaDados();
				}
			}
		} else if (cevt.getListaCampos() == lcContrato ) {
			loadSaldoContrato();
		}
	}

	public void beforeCarrega( CarregaEvent cevt ) {

		if ( cevt.getListaCampos() == lcChamado ) {

			sinalizaChamado( false, codchamado_ant );

		}

	}

}
