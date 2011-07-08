/**
 * @version 06/07/2011 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.crm.frame.utility <BR>
 *         Classe: @(#)FConsisteCRM.java <BR>
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
 *         Consistência e geração automatizada de lançamentos
 * 
 */

package org.freedom.modulos.crm.view.frame.utility;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.Timer;
import org.freedom.bmps.Icone;
import org.freedom.infra.functions.StringFunctions;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JButtonPad;
import org.freedom.library.swing.component.JCheckBoxPad;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JPanelPad;
import org.freedom.library.swing.component.JTablePad;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FFilho;

public class FConsisteCRM extends FFilho implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JPanelPad pinCliente = new JPanelPad( 600, 110 );

	private JPanelPad pnGrid = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 2, 1 ) );

	private JTextFieldPad txtDataini = new JTextFieldPad( JTextFieldPad.TP_DATE, 0, 10 );

	private JTextFieldPad txtDatafim = new JTextFieldPad( JTextFieldPad.TP_DATE, 0, 10 );

	private JCheckBoxPad cbEntrada = new JCheckBoxPad( "Entrada", "S", "N" );

	private JCheckBoxPad cbSaida = new JCheckBoxPad( "Saida", "S", "N" );

	private JTablePad tabexped = null;

	private JScrollPane spnTabexped = null;
	
	private JTablePad tab2 = new JTablePad();

	private JScrollPane spnTab2 = new JScrollPane( tab2 );

	private JProgressBar pbAnd = new JProgressBar();

	private JButtonPad btVisual = new JButtonPad( Icone.novo( "btPesquisa.gif" ) );

	private JButtonPad btChecar = new JButtonPad( Icone.novo( "btExecuta.gif" ) );

	private JButtonPad btGerar = new JButtonPad( Icone.novo( "btGerar.gif" ) );
	
	private ListaCampos lcAtend = new ListaCampos( this, "AE" );

	private JTextFieldPad txtCodAtend = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldFK txtNomeAtend = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private ListaCampos lcEmpregado = new ListaCampos( this, "EP" );
	
	private JTextFieldPad txtMatempr = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtNomeempr = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	private ListaCampos lcTurno = new ListaCampos( this, "TO" );
	
	private JTextFieldPad txtCodTurno = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescTurno = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	private Timer tim = null;

	private int iAnd = 0;

	private int iTotCompras = 0;

	private int iTotVendas = 0;

	private JLabelPad lbAnd = new JLabelPad( "Aguardando:" );

	private enum EColExped {
		DTEXPED, HORASEXPED, HINITURNO, HINIINTTURNO, HFIMINTTURNO, HFIMTURNO
	}

	public FConsisteCRM() {
		super( false );
		montaListaCampos();
		montaTela();
	}

	private void montaTela() {
		setTitulo( "Gerar Informações Fiscais" );
		setAtribos( 10, 10, 670, 500 );

		btVisual.setToolTipText( "Visualizar" );
		btChecar.setToolTipText( "Checar" );
		btGerar.setToolTipText( "Gerar" );

		btChecar.setEnabled( false );
		btGerar.setEnabled( false );

		Container c = getContentPane();
		c.setLayout( new BorderLayout() );

		adicBotaoSair();

		c.add( pinCliente, BorderLayout.NORTH );
		c.add( pnGrid, BorderLayout.CENTER );

		txtDataini.setRequerido( true );
		txtDatafim.setRequerido( true );

		txtMatempr.setAtivo( false );
		txtCodTurno.setAtivo( false );

		pinCliente.adic( new JLabelPad( "Início" ), 7, 0, 110, 25 );
		pinCliente.adic( txtDataini, 7, 20, 110, 20 );
		pinCliente.adic( new JLabelPad( "Fim" ), 120, 0, 107, 25 );
		pinCliente.adic( txtDatafim, 120, 20, 107, 20 );
		pinCliente.adic( new JLabelPad("Cód.atend."), 230, 0, 90, 25);
		pinCliente.adic( txtCodAtend, 230, 20, 90, 20 );
		pinCliente.adic( new JLabelPad("Nome do atendente"), 323, 0, 300, 25 );
		pinCliente.adic( txtNomeAtend, 323, 20, 300, 20 );
		pinCliente.adic( new JLabelPad("Matrícula"), 7, 45, 90, 25);
		pinCliente.adic( txtMatempr, 7, 65, 90, 20 );
		pinCliente.adic( new JLabelPad("Nome do colaborador"), 100, 45, 200, 25 );
		pinCliente.adic( txtNomeempr, 100, 65, 200, 20 );
		pinCliente.adic( new JLabelPad("Cód.turno"), 303, 45, 90, 25);
		pinCliente.adic( txtCodTurno, 303, 65, 90, 20 );
		pinCliente.adic( new JLabelPad("Descrição do turno"), 396, 45, 200, 25 );
		pinCliente.adic( txtDescTurno, 396, 65, 200, 20 );
		
		pinCliente.adic( btVisual, 7, 85, 30, 30 );
		pinCliente.adic( btChecar, 40, 85, 30, 30 );
		pinCliente.adic( btGerar, 73, 85, 30, 30 );
		//pinCliente.adic( cbEntrada, 7, 50, 150, 20 );
		//pinCliente.adic( cbSaida, 170, 50, 150, 20 );
		//pinCliente.adic( lbAnd, 7, 80, 110, 20 );
		//pinCliente.adic( pbAnd, 120, 80, 210, 20 );


        prepTabexped(0);

        pnGrid.add( spnTabexped );
		pnGrid.add( spnTab2 );
        
		tab2.adicColuna( "Dt.emissão" );
		tab2.adicColuna( "Dt.saída" );

		tab2.setTamColuna( 100, 0 );
		tab2.setTamColuna( 100, 1 );

		colocaMes();
		btVisual.addActionListener( this );
		btChecar.addActionListener( this );
		btGerar.addActionListener( this );

		pbAnd.setStringPainted( true );
		
	}

	private void montaListaCampos() {

		txtCodAtend.setTabelaExterna( lcAtend, null );
		txtCodAtend.setFK( true );
		txtCodAtend.setNomeCampo( "CodAtend" );
		lcAtend.add( new GuardaCampo( txtCodAtend, "CodAtend", "Cód.atend.", ListaCampos.DB_PK, false ), "txtCodAtendx" );
		lcAtend.add( new GuardaCampo( txtNomeAtend, "NomeAtend", "Nome", ListaCampos.DB_SI, false ), "txtNomeAtendx" );
		lcAtend.add( new GuardaCampo( txtMatempr, "Matempr", "Matrícula", ListaCampos.DB_FK, false), "txtMatemprx" );
		lcAtend.montaSql( false, "ATENDENTE", "AT" );
		lcAtend.setReadOnly( true );

		txtMatempr.setTabelaExterna( lcEmpregado, null );
		txtMatempr.setFK( true );
		txtMatempr.setNomeCampo( "Matempr" );
		lcEmpregado.add( new GuardaCampo( txtMatempr, "Matempr", "Matrícula", ListaCampos.DB_PK, false ), "txtMatemprx" );
		lcEmpregado.add( new GuardaCampo( txtNomeempr, "Nomeempr", "Nome do colaborador", ListaCampos.DB_SI, false ), "txtNomeemprx" );
		lcEmpregado.add( new GuardaCampo( txtCodTurno, "Codturno", "Cód.turno", ListaCampos.DB_FK, false), "txtCodturnox" );
		lcEmpregado.montaSql( false, "EMPREGADO", "RH" );
		lcEmpregado.setReadOnly( true );
		
		txtCodTurno.setTabelaExterna( lcTurno, null );
		txtCodTurno.setFK( true );
		txtCodTurno.setNomeCampo( "CodTurno" );
		lcTurno.add( new GuardaCampo( txtCodTurno, "CodTurno", "Cód.turno", ListaCampos.DB_PK, false ), "txtCodTurnox" );
		lcTurno.add( new GuardaCampo( txtDescTurno, "DescTurno", "Descrição do turno", ListaCampos.DB_SI, false ), "txtDescTurnox" );
		lcTurno.montaSql( false, "TURNO", "RH" );
		lcTurno.setReadOnly( true );
		
	}

	public void setConexao( DbConnection cn ) {
		super.setConexao( cn );
		lcAtend.setConexao( cn );
		lcEmpregado.setConexao( cn );
		lcTurno.setConexao( cn );
	}
	
	private void colocaMes() {

		GregorianCalendar cData = new GregorianCalendar();
		GregorianCalendar cDataIni = new GregorianCalendar();
		GregorianCalendar cDataFim = new GregorianCalendar();
		cDataIni.set( Calendar.MONTH, cData.get( Calendar.MONTH ) - 1 );
		cDataIni.set( Calendar.DATE, 1 );
		cDataFim.set( Calendar.DATE, -1 );
		txtDataini.setVlrDate( cDataIni.getTime() );
		txtDatafim.setVlrDate( cDataFim.getTime() );

	}

	public void iniGerar() {

		Thread th = new Thread( new Runnable() {

			public void run() {

				gerar();
			}
		} );
		try {
			th.start();
		} catch ( Exception err ) {
			Funcoes.mensagemErro( this, "Não foi possível criar processo!\n" + err.getMessage(), true, con, err );
		}
	}

	private void prepTabexped(int nbatidas) {
		int numcols = 0;
		int qtdant = 0;
		if (nbatidas==0) {
			tabexped =  new JTablePad();
			spnTabexped =  new JScrollPane( tabexped );

			tabexped.adicColuna( "Dt.exped." );
			tabexped.adicColuna( "C. horária" );
			tabexped.adicColuna(  "H.ini.turno" );
			tabexped.adicColuna( "H.ini.interv." );
			tabexped.adicColuna( "H.fim interv." );
			tabexped.adicColuna( "H.fim turno" );
			tabexped.setTamColuna( 70, EColExped.DTEXPED.ordinal() );
			tabexped.setTamColuna( 70, EColExped.HORASEXPED.ordinal() );
			tabexped.setTamColuna( 60, EColExped.HINITURNO.ordinal() );
			tabexped.setTamColuna( 60, EColExped.HINIINTTURNO.ordinal() );
			tabexped.setTamColuna( 60, EColExped.HFIMINTTURNO.ordinal() );
			tabexped.setTamColuna( 60, EColExped.HFIMTURNO.ordinal() );
		} else {
			tabexped.limpa();
			if ( (EColExped.HFIMTURNO.ordinal() + nbatidas ) > tabexped.getNumColunas() ) {
				numcols = EColExped.HFIMTURNO.ordinal() + nbatidas;
				qtdant = tabexped.getNumColunas();
				for (int i=qtdant; i<=numcols ; i++ ) {
	            	tabexped.adicColuna( "H.Ponto " + ( i - EColExped.HFIMTURNO.ordinal() ) );
	            	tabexped.setTamColuna( 60, EColExped.HFIMTURNO.ordinal() + i );
	            }
			}
		}
	}
	
	private void visualizar() {

	
		StringBuffer sqlexped = new StringBuffer();
		StringBuffer sqlcount = new StringBuffer();
		StringBuffer sqlbatidas = new StringBuffer();
		PreparedStatement ps = null;
		ResultSet rs = null;
		PreparedStatement psbat = null;
		ResultSet rsbat = null;
		int totexped = 0;
		int qtdbatidas = 0;
		int col = 0;
		if ( !valida() ) {
			return;
		}
		try {
			
			sqlcount.append( "SELECT B.DTBAT, COUNT(*) QTD FROM PEBATIDA B "); 
			sqlcount.append( "WHERE B.CODEMP=? AND B.CODFILIAL=? AND ");
			sqlcount.append( "B.CODEMPEP=? AND B.CODFILIALEP=? AND B.MATEMPR=? AND ");
			sqlcount.append( "B.DTBAT BETWEEN ? AND ? ");
			sqlcount.append( "GROUP BY B.DTBAT ");
			sqlcount.append( "ORDER BY 2 DESC" );
 		    psbat = con.prepareStatement( sqlcount.toString() );
 		 	
 		    psbat.setInt( 1, Aplicativo.iCodEmp );
 		    psbat.setInt( 2, ListaCampos.getMasterFilial( "PEBATIDA" ) );
 		    psbat.setInt( 3, Aplicativo.iCodEmp );
 		    psbat.setInt( 4, ListaCampos.getMasterFilial( "RHEMPREGADO" ) );
 		    psbat.setInt( 5, txtMatempr.getVlrInteger().intValue() );
			psbat.setDate( 6, Funcoes.dateToSQLDate( txtDataini.getVlrDate() ) );
			psbat.setDate( 7, Funcoes.dateToSQLDate( txtDatafim.getVlrDate() ) );
			rsbat = psbat.executeQuery();
			if ( rsbat.next() ) {
				qtdbatidas = rsbat.getInt( "QTD" );
			}
			rsbat.close();
			psbat.close();
			con.commit();
			
			sqlexped.append("SELECT E.DTEXPED, E.HORASEXPED, T.HINITURNO, T.HINIINTTURNO, T.HFIMINTTURNO, T.HFIMTURNO ");
			sqlexped.append("FROM RHEXPEDIENTE E, RHTURNO T ");
			sqlexped.append("WHERE E.CODEMP=? AND E.CODFILIAL=? AND E.CODTURNO=? AND ");
			sqlexped.append("E.DTEXPED BETWEEN ? AND ? AND ");
			sqlexped.append( "T.CODEMP=E.CODEMP AND T.CODFILIAL=E.CODFILIAL AND T.CODTURNO=E.CODTURNO ");
			sqlexped.append("ORDER BY E.DTEXPED");
			
 		    ps = con.prepareStatement( sqlexped.toString() );
 	
 		    ps.setInt( 1, Aplicativo.iCodEmp );
 		    ps.setInt( 2, ListaCampos.getMasterFilial( "RHEXPEDIENTE" ) );
 		    ps.setInt( 3, txtCodTurno.getVlrInteger().intValue() );
			ps.setDate( 4, Funcoes.dateToSQLDate( txtDataini.getVlrDate() ) );
			ps.setDate( 5, Funcoes.dateToSQLDate( txtDatafim.getVlrDate() ) );
			rs = ps.executeQuery();
			prepTabexped( qtdbatidas );

			sqlbatidas.append( "SELECT B.DTBAT, B.HBAT FROM PEBATIDA B "); 
			sqlbatidas.append( "WHERE B.CODEMP=? AND B.CODFILIAL=? AND ");
			sqlbatidas.append( "B.CODEMPEP=? AND B.CODFILIALEP=? AND B.MATEMPR=? AND ");
			sqlbatidas.append( "B.DTBAT = ? ");
			sqlbatidas.append( "ORDER BY B.DTBAT, B.HBAT" ); 
			
			while ( rs.next() ) {
				tabexped.adicLinha();
				tabexped.setValor( StringFunctions.sqlDateToStrDate( rs.getDate( EColExped.DTEXPED.toString() ) ), totexped, EColExped.DTEXPED.ordinal() );
				tabexped.setValor( new Integer(rs.getInt( EColExped.HORASEXPED.toString() ) ), totexped, EColExped.HORASEXPED.ordinal() );
				tabexped.setValor( Funcoes.copy( rs.getTime( EColExped.HINITURNO.toString() ).toString() ,5 ) , totexped, EColExped.HINITURNO.ordinal() );
				tabexped.setValor( Funcoes.copy( rs.getTime( EColExped.HINIINTTURNO.toString() ).toString(),5 ) , totexped, EColExped.HINIINTTURNO.ordinal() );
				tabexped.setValor( Funcoes.copy( rs.getTime( EColExped.HFIMINTTURNO.toString() ).toString(), 5) , totexped, EColExped.HFIMINTTURNO.ordinal() );
				tabexped.setValor( Funcoes.copy( rs.getTime( EColExped.HFIMTURNO.toString().toString() ).toString(),5 ) , totexped, EColExped.HFIMTURNO.ordinal() );
	 		    psbat = con.prepareStatement( sqlbatidas.toString() );
	 		    psbat.setInt( 1, Aplicativo.iCodEmp );
	 		    psbat.setInt( 2, ListaCampos.getMasterFilial( "PEBATIDA" ) );
	 		    psbat.setInt( 3, Aplicativo.iCodEmp );
	 		    psbat.setInt( 4, ListaCampos.getMasterFilial( "RHEMPREGADO" ) );
	 		    psbat.setInt( 5, txtMatempr.getVlrInteger().intValue() );
				psbat.setDate( 6, rs.getDate(EColExped.DTEXPED.toString() ) );
				rsbat = psbat.executeQuery();
				col = EColExped.HFIMTURNO.ordinal() + 1;
				while ( rsbat.next() && col<tabexped.getNumColunas() ) {
					tabexped.setValor( Funcoes.copy( rsbat.getTime( "HBAT" ).toString(),5 ), totexped, col);
					col ++;
				}
				rsbat.close();
				psbat.close();
				totexped ++;
			}

		    rs.close();
		    ps.close();

			con.commit();

		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro ao realizar consulta!\n" + err.getMessage(), true, con, err );
			err.printStackTrace();
			return;
		}
		btGerar.setEnabled( false );

	}

	private void gerar() {

		if ( ( iTotVendas + iTotCompras ) <= 0 ) {
			btGerar.setEnabled( false );
			return;
		}
		int iQuant = 0;
		iAnd = 0;
		tim.stop();
		pbAnd.setValue( iAnd );
		pbAnd.updateUI();
		lbAnd.setText( "Pronto." );
		btGerar.setEnabled( false );
	}

	private boolean checar() {

		boolean bRetorno = false;
		int iTotErros = 0;

		return bRetorno;
	}

	private boolean valida() {

		if ( txtDatafim.getVlrDate().before( txtDataini.getVlrDate() ) ) {
			Funcoes.mensagemInforma( this, "Data final maior que a data inicial!" );
			return false;
		}

		if ( txtCodAtend.getVlrInteger().intValue()==0) {
			Funcoes.mensagemInforma( this, "Selecione o atendente!" );
			return false;
		}
		
		if (txtMatempr.getVlrInteger().intValue()==0) {
			Funcoes.mensagemInforma( this, "Atendente não possui vínculo com empregado!" );
			return false;
		}
		
		if (txtCodTurno.getVlrInteger().intValue()==0) {
			Funcoes.mensagemInforma( this, "Empregado não possui vínculo com turno!" );
			return false;
		}
		return true;
	}

	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == tim ) {
			// System.out.println("Atualizando\n");
			pbAnd.setValue( iAnd + 1 );
			pbAnd.updateUI();
		}
		else if ( evt.getSource() == btGerar ) {
			iniGerar();
		}
		else if ( evt.getSource() == btChecar ) {
			checar();
		}
		else if ( evt.getSource() == btVisual ) {
			visualizar();
		}
	}
}
