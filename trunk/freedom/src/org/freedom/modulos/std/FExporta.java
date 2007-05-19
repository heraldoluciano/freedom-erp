package org.freedom.modulos.std;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
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

import org.freedom.bmps.Icone;
import org.freedom.componentes.JButtonPad;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FFilho;

public class FExporta extends FFilho implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	private static final String RETURN = String.valueOf( (char) 13 ) + String.valueOf( (char) 10 );

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

		montaRadioGrupos();
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
		
		progress.setStringPainted( true );
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
		
		if ( txtFile.getVlrString() == null || txtFile.getVlrString().trim().length() < 1 ) {
			Funcoes.mensagemInforma( this, "Selecione o arquivo!" );
			txtFile.requestFocus();
			return;
		}
		if ( txtDtFim.getVlrDate().before( txtDtIni.getVlrDate() ) ) {
			Funcoes.mensagemInforma( this, "Data final inferior a inicial!" );
			return;
		}
		
		try {
			
			List<String> readrows = getReadRows();
			
			if ( readrows.size() > 0 ) {
				
				String filename = txtFile.getVlrString().trim();
				File filecontabil = new File( filename );
				
				if ( filecontabil.exists() ) {
					int opt = Funcoes.mensagemConfirma( null, "Arquivo: '" + filename + "' já existe! Deseja sobrescrever?" );
					if ( opt != 0 ) {						
						return;
					}
				}
				
				filecontabil.createNewFile();
				
				FileWriter filewritercontabil = new FileWriter( filecontabil );
				
				int countprogress = 1;
				progress.setMaximum( readrows.size() );
				
				for ( String row : readrows ) {
					
					filewritercontabil.write( row );
					filewritercontabil.write( RETURN );
					filewritercontabil.flush();
					progress.setValue( countprogress++ );
				}			
				
				filewritercontabil.close();
				
				Funcoes.mensagemInforma( this, "Arquivo exportado para '" + filename + "' com sucesso!" );
			}
			else {
				Funcoes.mensagemInforma( this, "Nenhum registro encontrado para exportação!" );
			}
		}
		catch ( Exception e ) {
			Funcoes.mensagemErro( this, "Erro ao montar arquivo!\n" + e.getMessage(), true, con, e );
			e.printStackTrace();
		}
		
		progress.setValue( 0 );
	}
	
	private List<String> getReadRows() throws Exception {
		
		List<String> readrows = new ArrayList<String>();

		// implementar seleção de formato de arquivo.
		//if ( "C".equals( rgModo.getVlrString() ) ) {
		//	if ( opção de formato ) {						
			getLayoutContabil( readrows );
		//	}
		//}
		//if ( "L".equals( rgModo.getVlrString() ) ) {
		//	if ( opção de formato ) {	
		//	}
		//}
				
		return readrows;
	}
	
	private void getLayoutContabil( final List<String> readrows ) {
		
		try {
			
			PreparedStatement ps = null;
			ResultSet rs = null;
			StringBuilder sql = new StringBuilder();
			StringBuilder row = new StringBuilder();
						
			sql.append( "SELECT L.TIPOLF,L.CODEMP,L.CODFILIAL,L.CODEMITLF, " );
			sql.append( "(CASE WHEN L.TIPOLF='S' THEN" );
			sql.append( "	COALESCE(C.CNPJCLI,C.CPFCLI)" );
			sql.append( "	ELSE" );
			sql.append( "	COALESCE(F.CNPJFOR, F.CPFFOR)" );
			sql.append( "	END) CNPJEMIT," );
			sql.append( "L.DTEMITLF, L.ESPECIELF, L.SERIELF," );
			sql.append( "L.DOCINILF, L.DOCFIMLF, L.CODNAT," );
			sql.append( "L.ALIQICMSLF," );      
			sql.append( "L.ALIQIPILF," );         
			sql.append( "L.OBSLF," );
			sql.append( "SUM(L.VLRBASEICMSLF) VLRBASECIMSLF," );
			sql.append( "SUM(L.VLRICMSLF) VLRICMSLF," );
			sql.append( "SUM(L.VLRISENTASICMSLF) VLRISENTASICMSLF," );
			sql.append( "SUM(L.VLROUTRASICMSLF) VLROUTRASICMSLF," );
			sql.append( "SUM(L.VLRBASEIPILF) VLRBASEIPILF," );
			sql.append( "SUM(L.VLRIPILF) VLRIPILF," );
			sql.append( "SUM(L.VLRISENTASIPILF) VLRISENTASIPILF," );
			sql.append( "SUM(L.VLROUTRASIPILF) VLROUTRASIPILF " );
			sql.append( "FROM  LFLIVROFISCAL L " );
			sql.append( "LEFT OUTER JOIN VDCLIENTE C ON " );
			sql.append( "	C.CODEMP=L.CODEMP AND" );
			sql.append( "	C.CODFILIAL=L.CODFILIAL AND" );
			sql.append( "	C.CODCLI=L.CODEMITLF AND" );
			sql.append( "	L.TIPOLF='S' " );
			sql.append( "LEFT OUTER JOIN CPFORNECED F ON" );
			sql.append( "	F.CODEMP=L.CODEMP AND" );
			sql.append( "	F.CODFILIAL=L.CODFILIAL AND" );
			sql.append( "	F.CODFOR=L.CODEMITLF AND" );
			sql.append( "	L.TIPOLF='E' " );
			sql.append( "WHERE L.CODEMP=? AND L.CODFILIAL=? AND L.DTEMITLF BETWEEN ? AND ? " );
			sql.append( "GROUP BY L.TIPOLF,L.CODEMP,L.CODFILIAL,L.CODEMITLF," );
			sql.append( "5,L.DTEMITLF,L.ESPECIELF,L.SERIELF," );
			sql.append( "L.DOCINILF,L.DOCFIMLF,L.CODNAT," );
			sql.append( "L.ALIQICMSLF,L.ALIQIPILF,L.OBSLF " );
			sql.append( "ORDER BY L.DTEMITLF" );
			ps = con.prepareStatement( sql.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "LFLIVROFISCAL" ) );
			ps.setDate( 3, Funcoes.dateToSQLDate( txtDtIni.getVlrDate() ) );
			ps.setDate( 4, Funcoes.dateToSQLDate( txtDtFim.getVlrDate() ) );
			rs = ps.executeQuery();
			
			while( rs.next() ) {
				
				row.delete( 0, row.length() );

				row.append( rs.getString( "CODEMITLF" ) );
				row.append( rs.getString( "CNPJEMIT" ) );
				row.append( rs.getString( "DTEMITLF" ) );
				row.append( rs.getString( "ESPECIELF" ) );
				row.append( rs.getString( "SERIELF" ) );
				row.append( rs.getString( "DOCINILF" ) );
				row.append( rs.getString( "DOCFIMLF" ) );
				row.append( rs.getString( "CODNAT" ) );
				row.append( rs.getString( "ALIQICMSLF" ) );
				row.append( rs.getString( "ALIQIPILF" ) );
				row.append( rs.getString( "OBSLF" ) );
				row.append( rs.getString( "VLRBASECIMSLF" ) );
				row.append( rs.getString( "VLRICMSLF" ) );
				row.append( rs.getString( "VLRISENTASICMSLF" ) );
				row.append( rs.getString( "VLROUTRASICMSLF" ) );
				row.append( rs.getString( "VLROUTRASICMSLF" ) );
				row.append( rs.getString( "VLRBASEIPILF" ) );
				row.append( rs.getString( "VLRIPILF" ) );
				row.append( rs.getString( "VLRISENTASIPILF" ) );
				row.append( rs.getString( "VLROUTRASIPILF" ) );
						
				readrows.add( row.toString() );
			}
			
			rs.close();
			ps.close();
			
			if ( ! con.getAutoCommit() ) {
				con.commit();
			}
		}
		catch ( Exception e ) {
			Funcoes.mensagemErro( this, "Erro ao buscar dados!" );
			e.printStackTrace();
		}
	}

	public void actionPerformed( ActionEvent e ) {

		if ( e.getSource() == btGerar ) {

			Thread tdg = new Thread() {
				public void run() {
					gerar();
				}
			};			
			tdg.start();
		}
		else if ( e.getSource() == btFile ) {

			getFile();
		}
	}

	@ Override
	public void setConexao( Connection cn ) {

		super.setConexao( cn );
	}
}
