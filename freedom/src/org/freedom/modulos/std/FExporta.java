package org.freedom.modulos.std;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;

import org.freedom.acao.Processo;
import org.freedom.bmps.Icone;
import org.freedom.componentes.JButtonPad;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.ProcessoSec;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FFilho;

public class FExporta extends FFilho implements ActionListener {

	private static final long serialVersionUID = 1L;

	private final JPanelPad panelExporta = new JPanelPad();

	private final JTextFieldPad txtDtIni = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private final JTextFieldPad txtDtFim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private final JTextFieldPad txtFile = new JTextFieldPad( JTextFieldPad.TP_STRING, 100, 0 );

	private JButtonPad btGerar = new JButtonPad( "Exportar", Icone.novo( "btGerar.gif" ) );

	private JButtonPad btFile = new JButtonPad( Icone.novo( "btAbrirPeq.gif" ) );

	private final JProgressBar progress = new JProgressBar();
	
	private JRadioGroup rgModo;

	public FExporta() {

		super( false );
		setTitulo( "Exportar Arquivo" );
		setAtribos( 50, 50, 470, 220 );

		montaTela();

		Calendar cal = Calendar.getInstance();
		txtDtFim.setVlrDate( cal.getTime() );
		cal.set( Calendar.MONTH, cal.get( Calendar.MONTH ) - 1 );
		txtDtIni.setVlrDate( cal.getTime() );

		txtDtIni.setRequerido( true );
		txtDtFim.setRequerido( true );

		btGerar.setToolTipText( "Gerar Arquivo" );
		btFile.setToolTipText( "Salvar como" );

		btGerar.addActionListener( this );
		btFile.addActionListener( this );
	}
	
	private void montaRadioGrupos() {

		Vector<String> labs = new Vector<String>();
		labs.add( "Contábil" );
		labs.add( "Livros Fiscais" );
		Vector<String> vals = new Vector<String>();
		vals.add( "C" );
		vals.add( "L" );
		rgModo = new JRadioGroup( 1, 2, labs, vals );
		rgModo.setVlrString( "C" );
	}

	private void montaTela() {

		getTela().add( panelExporta, BorderLayout.CENTER );

		panelExporta.adic( new JLabel( "Salvar em:" ), 10, 0, 100, 20 );
		panelExporta.adic( txtFile, 10, 20, 400, 20 );
		panelExporta.adic( btFile, 420, 20, 20, 20 );

		JLabel periodo = new JLabel( "Periodo", SwingConstants.CENTER );
		periodo.setOpaque( true );
		panelExporta.adic( periodo, 25, 45, 60, 20 );

		JLabel borda = new JLabel();
		borda.setBorder( BorderFactory.createEtchedBorder() );
		panelExporta.adic( borda, 10, 55, 290, 45 );

		panelExporta.adic( txtDtIni, 25, 70, 110, 20 );
		panelExporta.adic( new JLabel( "até", SwingConstants.CENTER ), 135, 70, 40, 20 );
		panelExporta.adic( txtDtFim, 175, 70, 110, 20 );

		panelExporta.adic( btGerar, 310, 65, 130, 25 );

		panelExporta.adic( progress, 10, 120, 430, 20 );

		adicBotaoSair();
	}

	private void getFile() {

		JFileChooser fileChooser = new JFileChooser();
		
		if ( fileChooser.showOpenDialog( this ) == JFileChooser.APPROVE_OPTION ) {
			
			txtFile.setVlrString( fileChooser.getSelectedFile().getPath() );
		}
	}

	private void gerar() {

		if ( "C".equals( rgModo.getVlrString() ) ) {
			gerarContabil();
		}
		if ( "L".equals( rgModo.getVlrString() ) ) {
			//gerarLivrosFicais();
		}
	}
	
	private void gerarContabil() {
		
		if ( txtDtFim.getVlrDate().before( txtDtIni.getVlrDate() ) ) {
			Funcoes.mensagemInforma( this, "Data final inferior a inicial!" );
			return;
		}
		
		try {
			
			List<String> readrows = getReadRows();
			
		}
		catch ( Exception e ) {
			Funcoes.mensagemErro( this, "Erro ao montar arquivo contábil!\n" + e.getMessage(), true, con, e );
			e.printStackTrace();
		}
	}
	
	private List<String> getReadRows() throws Exception {
		
		List<String> readrows = new ArrayList<String>();

		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuilder sql = new StringBuilder();
		
		// implementar seleção de formato de arquivo.
		//if ( opção de formato ) 
		
		sql.append( "" );
		ps = con.prepareStatement( sql.toString() );
		ps.setInt( 1, Aplicativo.iCodEmp );
		ps.setInt( 2, ListaCampos.getMasterFilial( "" ) );
		rs = ps.executeQuery();
		
		while( rs.next() ) {
			
		}
		
		rs.close();
		ps.close();
		
		if ( ! con.getAutoCommit() ) {
			con.commit();
		}
		
		return readrows;
	}

	public void actionPerformed( ActionEvent e ) {

		if ( e.getSource() == btGerar ) {

			ProcessoSec pSec = new ProcessoSec( 500, 
				new Processo() {
					public void run() {
						progress.updateUI();
					}
				}, 
				new Processo() {
					public void run() {
						gerar();
					}
				} 
			);
			
			pSec.iniciar();
		}
		else if ( e.getSource() == btFile ) {

			getFile();
		}
	}
}
