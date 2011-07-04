/**
 * @version 04/07/2011 <BR>
 * @author Setpoint Informática Ltda./Anderson Sanchez <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.gpe <BR>
 *         Classe:
 * @(#)FBatida.java <BR>
 * 
 *                Este arquivo é parte do sistema Freedom-ERP, o Freedom-ERP é um software livre; você pode redistribui-lo e/ou <BR>
 *                modifica-lo dentro dos termos da Licença Pública Geral GNU como publicada pela Fundação do Software Livre (FSF); <BR>
 *                na versão 2 da Licença, ou (na sua opnião) qualquer versão. <BR>
 *                Este programa é distribuido na esperança que possa ser util, mas SEM NENHUMA GARANTIA; <BR>
 *                sem uma garantia implicita de ADEQUAÇÂO a qualquer MERCADO ou APLICAÇÃO EM PARTICULAR. <BR>
 *                Veja a Licença Pública Geral GNU para maiores detalhes. <BR>
 *                Você deve ter recebido uma cópia da Licença Pública Geral GNU junto com este programa, se não, <BR>
 *                de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 *                Formulário para cadastro das áreas de conhecimento para uso nas funções de recrutamento e seleção.
 * 
 */

package org.freedom.modulos.gpe.view.frame.crud.plain;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

import org.freedom.acao.InsertEvent;
import org.freedom.acao.InsertListener;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JComboBoxPad;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.FDados;
import org.freedom.modulos.grh.view.frame.crud.plain.FTurnos;

public class FBatida extends FDados implements InsertListener, KeyListener {

	private static final long serialVersionUID = 1L;

	private final JTextFieldPad txtMatempr = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 6, 0 );
	
	private final JTextFieldFK txtNomeempr = new JTextFieldFK( JTextFieldFK.TP_STRING, 50, 0);
	
	private final JTextFieldPad txtDtbat = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private final JTextFieldPad txtHbat = new JTextFieldPad( JTextFieldPad.TP_TIME, 5, 0 );

	private final Vector<String> labels = new Vector<String>();
	
	private final Vector<String> values = new Vector<String>();

	private JComboBoxPad cbTipobat = null;
	
	private final ListaCampos lcEmpr = new ListaCampos(this, "EP");
	
	public FBatida() {

		super();
		setTitulo( "Digitação de Livro Ponto" );
		setAtribos( 50, 50, 500, 300 );
		
		montaListaCampos();

		montaTela();

		lcCampos.addInsertListener( this );
		btImp.addActionListener( this );
		btPrevimp.addActionListener( this );
		txtHbat.addKeyListener( this );
		setImprimir( true );
	}

	private void montaTela() {

		nav.setNavigation( false );

		labels.addElement( "Livro ponto (Manual)" );
		labels.addElement( "Ponto eletrônico" );
		
		values.addElement( "M" );
		values.addElement( "E" );
		
		cbTipobat = new JComboBoxPad( labels, values, JComboBoxPad.TP_STRING, 1, 0 );
		cbTipobat.setEnabled( false );
		
		adicCampo( txtDtbat, 7, 20, 90, 20, "Dtbat", "Data", ListaCampos.DB_PK, true );
		adicCampo( txtHbat, 100, 20, 90, 20, "Hbat", "Horário", ListaCampos.DB_PK, true);
		adicDB( cbTipobat, 193, 20, 180, 20, "Tipobat", "Tipo de batida", false );
		adicCampo( txtMatempr, 7, 60, 70, 20, "Matempr", "Matrícula", ListaCampos.DB_PF, txtNomeempr, true );
		adicDescFK( txtNomeempr, 80, 60, 300, 20, "Nomeempr", "Nome" );
		setListaCampos( true, "BATIDA", "PE" );
		lcCampos.setQueryInsert( false );
		
	}

	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == btPrevimp ) {
			imprimir( true );
		}
		else if ( evt.getSource() == btImp ) {
			imprimir( false );
		}

		super.actionPerformed( evt );
	}

	private void montaListaCampos() {
		lcEmpr.add( new GuardaCampo( txtMatempr, "Matempr", "Matrícula", ListaCampos.DB_PK, true ) );
		lcEmpr.add( new GuardaCampo( txtNomeempr, "Nomeempr", "Nome", ListaCampos.DB_SI, false ) );
		lcEmpr.montaSql( false, "EMPREGADO", "RH" );
		lcEmpr.setQueryCommit( false );
		lcEmpr.setReadOnly( true );
		txtMatempr.setTabelaExterna( lcEmpr, FTurnos.class.getCanonicalName() );

	}
	private void imprimir( boolean bVisualizar ) {

/*		FPrinterJob dlGr = null;
		HashMap<String, Object> hParam = new HashMap<String, Object>();

		hParam.put( "CODEMP", Aplicativo.iCodEmp );
		hParam.put( "CODFILIAL", ListaCampos.getMasterFilial( "RHAREA" ) );

		dlGr = new FPrinterJob( "relatorios/grhArea.jasper", "Lista de Áreas", "", this, hParam, con, null, false );

		if ( bVisualizar ) {
			dlGr.setVisible( true );
		}
		else {
			try {
				JasperPrintManager.printReport( dlGr.getRelatorio(), true );
			} catch ( Exception e ) {
				e.printStackTrace();
				Funcoes.mensagemErro( this, "Erro na geração do relátorio!" + e.getMessage(), true, con, e );
			}
		} */
	}

	public void afterInsert( InsertEvent ievt ) {

		if (ievt.getListaCampos()==lcCampos) {
			cbTipobat.setVlrString( "M" );
		}
		
	}

	public void beforeInsert( InsertEvent ievt ) {

	}
	
	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		
		lcEmpr.setConexao( cn );
		
	}
	public void keyPressed( KeyEvent kevt ) {
		if ( ( kevt.getKeyCode() == KeyEvent.VK_ENTER ) && ( kevt.getSource() == txtMatempr ) && (txtMatempr.getVlrInteger().intValue()>0 ) )  {
			if ( ( lcCampos.getStatus() == ListaCampos.LCS_INSERT ) || ( lcCampos.getStatus() == ListaCampos.LCS_EDIT )  ) {
				lcCampos.post();
				lcCampos.limpaCampos( true );
				txtDtbat.requestFocus();
			}
		}
	}
}
