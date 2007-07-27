/**
 * @version 07/2007 <BR>
 * @author Setpoint Informática Ltda./Alex Rodrigues <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FExporta.java <BR>
 * 
 * Este programa é licenciado de acordo com a LPG-PC (Licença Pública Geral para Programas de Computador), <BR>
 * versão 2.1.0 ou qualquer versão posterior. <BR>
 * A LPG-PC deve acompanhar todas PUBLICAÇÕES, DISTRIBUIÇÕES e REPRODUÇÕES deste Programa. <BR>
 * Caso uma cópia da LPG-PC não esteja disponível junto com este Programa, você pode contatar <BR>
 * o LICENCIADOR ou então pegar uma cópia em: <BR>
 * Licença: http://www.lpg.adv.br/licencas/lpgpc.rtf <BR>
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa é preciso estar <BR>
 * de acordo com os termos da LPG-PC <BR> <BR>
 *
 * Formulário de exportação de dados para arquivo, dos dados referentes a contabilidade e livros fiscais.
 * 
 */

package org.freedom.modulos.std;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
	
	private final int NUMERIC = 0;
	
	private final int CHAR = 1;
	
	private final int DATE = 2;
	
	private final String FREEDOM_CONTABIL = "01";
	
	private final String SAFE_CONTABIL = "02";
	
	private static final String RETURN = String.valueOf( (char) 13 ) + String.valueOf( (char) 10 );

	private final JPanelPad panelExporta = new JPanelPad();

	private final JTextFieldPad txtDtIni = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private final JTextFieldPad txtDtFim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private final JTextFieldPad txtFile = new JTextFieldPad( JTextFieldPad.TP_STRING, 100, 0 );

	private final JButtonPad btChecar = new JButtonPad( Icone.novo( "btExecuta.gif" ) );

	private final JButtonPad btGerar = new JButtonPad( Icone.novo( "btGerar.gif" ) );

	private final JButtonPad btFile = new JButtonPad( Icone.novo( "btAbrirPeq.gif" ) );

	private final JProgressBar progress = new JProgressBar();
	
	private JRadioGroup rgModo;
	
	private String sistema;
	
	private List<String> readrows;
	
	private List<SafeBean> erros;

	public FExporta() {

		super( false );
		setTitulo( "Exportar Arquivo" );
		setAtribos( 50, 50, 470, 280 );

		montaRadioGrupos();
		montaTela();

		Calendar cal = Calendar.getInstance();
		txtDtFim.setVlrDate( cal.getTime() );
		cal.set( Calendar.MONTH, cal.get( Calendar.MONTH ) - 1 );
		txtDtIni.setVlrDate( cal.getTime() );

		txtDtIni.setRequerido( true );
		txtDtFim.setRequerido( true );

		btChecar.setToolTipText( "Verificar inconsistências" );
		btGerar.setToolTipText( "Gerar Arquivo" );
		btFile.setToolTipText( "Salvar como" );

		btChecar.addActionListener( this );
		btGerar.addActionListener( this );
		btFile.addActionListener( this );
		
		progress.setStringPainted( true );
		
		btGerar.setEnabled( false );
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

		panelExporta.adic( new JLabel( "Salvar em:" ), 10, 10, 100, 20 );
		panelExporta.adic( txtFile, 10, 30, 400, 20 );
		panelExporta.adic( btFile, 420, 30, 20, 20 );
		
		panelExporta.adic( new JLabel( "Tipo de exportação :" ), 10, 50, 150, 20 );
		panelExporta.adic( rgModo, 10, 70, 430, 30 );

		JLabel periodo = new JLabel( "Periodo", SwingConstants.CENTER );
		periodo.setOpaque( true );
		panelExporta.adic( periodo, 25, 105, 60, 20 );

		JLabel borda = new JLabel();
		borda.setBorder( BorderFactory.createEtchedBorder() );
		panelExporta.adic( borda, 10, 115, 290, 45 );

		panelExporta.adic( txtDtIni, 25, 130, 110, 20 );
		panelExporta.adic( new JLabel( "até", SwingConstants.CENTER ), 135, 130, 40, 20 );
		panelExporta.adic( txtDtFim, 175, 130, 110, 20 );

		panelExporta.adic( btChecar, 360, 120, 35, 35 );
		panelExporta.adic( btGerar, 405, 120, 35, 35 );

		panelExporta.adic( progress, 10, 180, 430, 20 );
		
		adicBotaoSair();
	}

	private void getFile() {

		JFileChooser fileChooser = new JFileChooser();
		
		if ( fileChooser.showOpenDialog( this ) == JFileChooser.APPROVE_OPTION ) {
			
			txtFile.setVlrString( fileChooser.getSelectedFile().getPath() );
		}
	}
	
	private String getSistemaContabil() {
		
		String sistema = "00";
		
		try {
			
			String sql = "SELECT SISCONTABIL FROM SGPREFERE1 WHERE CODEMP=? AND CODFILIAL=?";
			
			PreparedStatement ps = con.prepareStatement( sql );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "SGPREFERE1" ) );
			
			ResultSet rs = ps.executeQuery();
			
			if ( rs.next() ) {
				
				sistema = rs.getString( "SISCONTABIL" );
			}
		}
		catch ( Exception e ) {
			e.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao verificar preferências!\n" + e.getMessage() );
		}
		
		return sistema;
	}
	
	private void showErros() {
		
		DLChecaExporta dl = new DLChecaExporta( rgModo.getVlrString(), sistema );
		
		dl.carregaDados( erros );
		
		dl.setVisible( true );
	}

	private void checar() {

		
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
			
			readrows = getReadRows();
			
			if ( erros == null && readrows != null ) {
				
				btChecar.setEnabled( false );
				btGerar.setEnabled( true );
			}
			else {
				
				btChecar.setEnabled( true );
				btGerar.setEnabled( false );
				
				showErros();
			}
			
		}
		catch ( Exception e ) {
			e.printStackTrace();
		}
		
	}
	
	private void gerar() {
		
		try {
			
			if ( readrows != null && readrows.size() > 0 ) {
				
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
		
		readrows = new ArrayList<String>();
		sistema = getSistemaContabil();
		
		if ( "C".equals( rgModo.getVlrString() ) ) {
			
			if ( FREEDOM_CONTABIL.equals( sistema ) ) {		
				
				getLayoutFreedom();
			}
			else if ( SAFE_CONTABIL.equals( sistema ) ) {		
				
				erros = new ArrayList<SafeBean>();
				getLayoutSafe();
				
				if ( erros.size() > 0 ) {
					readrows = null;
				}
				else if ( erros.size() == 0 ) {
					erros = null;
				}
			} 
		}
		//if ( "L".equals( rgModo.getVlrString() ) ) {
		//		if ( opção de formato ) {	
		//	}
		//}
				
		return readrows;
	}
	
	private void getLayoutFreedom() {
		
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
			Funcoes.mensagemErro( this, "Erro ao buscar dados para sistema Freedom Contábil!" );
			e.printStackTrace();
		}
	}
	
	private void getLayoutSafe() {
		
		try {

			StringBuilder sql = new StringBuilder();
						
			sql.append( "SELECT F.CODFOR CODIGO, F.CODCONTDEB CONTADEB, F.CODCONTCRED CONTACRED, C.VLRLIQCOMPRA VALOR," );
			sql.append( "C.SERIE, C.DOCCOMPRA DOC, H.DESCHIST, C.DTENTCOMPRA DATA, C.CODFILIAL " );
			sql.append( "FROM CPCOMPRA C, EQTIPOMOV T, CPFORNECED F " );
			sql.append( "LEFT OUTER JOIN FNHISTPAD H " );
			sql.append( "ON H.CODEMP=F.CODEMPHP AND H.CODFILIAL=F.CODFILIALHP AND H.CODHIST=F.CODHIST " );
			sql.append( "WHERE C.CODEMP=? AND C.CODFILIAL=? " );
			sql.append( "AND C.DTENTCOMPRA BETWEEN ? AND ? " );
			sql.append( "AND T.CODEMP=C.CODEMPTM AND T.CODFILIAL=C.CODFILIALTM " );					
			sql.append( "AND T.CODTIPOMOV=C.CODTIPOMOV AND T.FISCALTIPOMOV='S' " );
			sql.append( "AND F.CODEMP=C.CODEMPFR AND F.CODFILIAL=C.CODFILIALFR AND F.CODFOR=C.CODFOR " ); 
								
			executeSqlSafe( sql.toString(), "CPCOMPRA", SafeBean.COMPRAS, readrows, erros );
			
			sql.delete( 0, sql.length() );
			
			sql.append( "SELECT F.CODCONTCRED CONTADEB, CT.CODCONTCRED CONTACRED," );
			sql.append( "S.VLRSUBLANCA VALOR, C.SERIE, C.DOCCOMPRA DOC," );
			sql.append( "H.DESCHIST, S.DATASUBLANCA DATA, S.CODFILIAL " );
			sql.append( "FROM FNSUBLANCA S, FNLANCA L, FNITPAGAR I, CPFORNECED F, FNCONTA CT," );
			sql.append( "FNHISTPAD H, FNPAGAR P, CPCOMPRA C " );
			sql.append( "WHERE S.CODEMP=? AND S.CODFILIAL=? AND S.DATASUBLANCA BETWEEN ? AND ? " );
			sql.append( "AND S.CODSUBLANCA<>0 " );
			sql.append( "AND L.CODEMP=S.CODEMP AND L.CODFILIAL=S.CODFILIAL AND L.CODLANCA=S.CODLANCA " );
			sql.append( "AND I.CODEMP=L.CODEMPPG AND I.CODFILIAL=L.CODFILIALPG AND I.CODPAG=L.CODPAG AND I.NPARCPAG=L.NPARCPAG " );
			sql.append( "AND P.CODEMP=I.CODEMP AND P.CODFILIAL=I.CODFILIAL AND P.CODPAG=I.CODPAG " );
			sql.append( "AND F.CODEMP=S.CODEMPFR AND F.CODFILIAL=S.CODFILIALFR AND F.CODFOR=S.CODFOR " );
			sql.append( "AND CT.CODEMPPN=L.CODEMPPN AND CT.CODFILIALPN=L.CODFILIALPN AND CT.CODPLAN=CT.CODPLAN " );
			sql.append( "AND H.CODEMP=CT.CODEMPHP AND H.CODFILIAL=CT.CODFILIALHP AND H.CODHIST=CT.CODHIST " );
			sql.append( "AND C.CODEMP=P.CODEMPCP AND C.CODFILIAL=P.CODFILIALCP AND C.CODCOMPRA=P.CODCOMPRA " );
									
			executeSqlSafe( sql.toString(), "FNSUBLANCA", SafeBean.CONTAS_PAGAR, readrows, erros );
		}
		catch ( Exception e ) {
			e.printStackTrace();
		}
	}
	
	private void executeSqlSafe( final String sql, final String table, final int tipo, final List<String> readrows, final List<SafeBean> erros  ) {
		
		try {
			
			StringBuilder row = new StringBuilder();
			
			PreparedStatement ps = con.prepareStatement( sql );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( table ) );
			ps.setDate( 3, Funcoes.dateToSQLDate( txtDtIni.getVlrDate() ) );
			ps.setDate( 4, Funcoes.dateToSQLDate( txtDtFim.getVlrDate() ) );
			
			ResultSet rs = ps.executeQuery();
			
			SafeBean sb = null;
			
			while( rs.next() ) {
				
				row.delete( 0, row.length() );
				
				sb = new SafeBean();
				
				sb.setContacred( rs.getString( "CONTADEB" ) );
				sb.setContadeb( rs.getString( "CONTACRED" ) );
				sb.setValor( rs.getBigDecimal( "VALOR" ) );
				sb.setDocumento( rs.getString( "DOC" ) );
				sb.setHistorico( decodeHistorico( rs, rs.getString( "DESCHIST" ) ) );
				sb.setData( rs.getDate( "DATA" ) );
				sb.setFilial( rs.getInt( "CODFILIAL" ) );
				sb.setTipo( tipo );
				
				if ( sb.valido() ) {
				
					readrows.add( row.toString() );
				}
				else {
					
					erros.add( sb );
				}
			}
			
			rs.close();
			ps.close();
			
			if ( ! con.getAutoCommit() ) {
				con.commit();
			}
		}
		catch ( Exception e ) {
			Funcoes.mensagemErro( this, "Erro ao buscar dados para sistema Freedom Contábil!" );
			e.printStackTrace();
		}
	}
	
	/**
	 * Este metodo é funcional somente para o layout do sistema Safe Contábil.
	 * @param rs
	 * @param historico
	 * @return
	 */
	private String decodeHistorico( final ResultSet rs, final String historico ) {
		
		String decode = null;
		
		if ( historico != null ) {	
			
			try {
				
				String tmp = historico;
				
				tmp = tmp.replaceAll( "<DOCUMENTO>", rs.getString( "DOC" ) != null ? rs.getString( "DOC" ).trim() : "" );
				tmp = tmp.replaceAll( "<VALOR>", String.valueOf( ( rs.getBigDecimal( "VALOR" ) != null 
						? rs.getBigDecimal( "VALOR" ) : new BigDecimal( "0.00" ) ).setScale( 2, BigDecimal.ROUND_HALF_UP ) ) );
				tmp = tmp.replaceAll( "<SERIE>", rs.getString( "SERIE" ) != null ? rs.getString( "SERIE" ).trim() : "" );
				tmp = tmp.replaceAll( "<DATA>", Funcoes.dateToStrDate( rs.getDate( "DATA" ) ) );
				
				decode = tmp;
			}
			catch ( Exception e ) {
				e.printStackTrace();
			}
		}
		
		return decode;
	}
	
	private void formatSafe( final SafeBean sb, final StringBuilder row ) {
		
		row.append( format( sb.getContacred(), CHAR, 11, 0 ) );
		row.append( format( sb.getContadeb(), CHAR, 11, 0 ) );
		row.append( format( sb.getValor(), NUMERIC, 14, 2 ) );				
		row.append( Funcoes.replicate( " ", 14 ) ); // Centro de custo
		row.append( format( sb.getDocumento(), CHAR, 12, 0 ) );
		row.append( format( sb.getHistorico(), CHAR, 250, 0 ) );
		row.append( format( sb.getData(), DATE, 0, 0 ) );
		row.append( format( sb.getFilial(), NUMERIC, 5, 0 ) );
		row.append( Funcoes.replicate( " ", 25 ) );
	}
	
	private String format( Object obj, int tipo, int tam, int dec ) {

		String retorno = null;
		String str = null;
		
		if ( obj == null ) {
			str = "";
		}
		else {
			str = obj.toString();
		}
		
		if ( tipo == NUMERIC ) {			
			if ( dec > 0 ) {
				retorno = Funcoes.transValor( str, tam, dec, true );	
			}
			else {
				retorno = Funcoes.strZero( str, tam );
			}
		}
		else if ( tipo == CHAR ) {			
			retorno = Funcoes.adicionaEspacos( str, tam );
		}
		else if ( tipo == DATE ) {			
			int[] args = Funcoes.decodeDate( (Date) obj );		
			retorno = 
				Funcoes.strZero( String.valueOf( args[2] ), 2 )	+ 
				Funcoes.strZero( String.valueOf( args[1] ), 2 ) + 
				Funcoes.strZero( String.valueOf( args[0] ), 4 );
		}
		
		return retorno;
	}

	public void actionPerformed( ActionEvent e ) {

		if ( e.getSource() == btGerar ) {
			Thread th = new Thread( new Runnable() {
				public void run() {
					gerar();
				}
			} );
			th.start();
		}
		if ( e.getSource() == btChecar ) {
			checar();
		}
		else if ( e.getSource() == btFile ) {

			getFile();
		}
	}

	@ Override
	public void setConexao( Connection cn ) {

		super.setConexao( cn );
	}

	static class SafeBean {
		
		public static final int COMPRAS = 0;
		
		public static final int CONTAS_PAGAR = 1;
		
		public static final int VENDAS = 2;
		
		public static final int CONTAS_RECEBER = 3;

		private String contadeb;

		private String contacred;

		private BigDecimal valor;

		private String centrocusto;

		private String documento;

		private String historico;

		private Date data;

		private Integer filial;
		
		private int tipo;

		
		public String getCentrocusto() {		
			return centrocusto;
		}
		
		public void setCentrocusto( final String centrocusto ) {		
			this.centrocusto = centrocusto;
		}
		
		public String getContacred() {		
			return contacred;
		}
		
		public void setContacred( final String contacred ) {		
			this.contacred = contacred;
		}
		
		public String getContadeb() {		
			return contadeb;
		}
		
		public void setContadeb( final String contadeb ) {		
			this.contadeb = contadeb;
		}
		
		public Date getData() {		
			return data;
		}
		
		public void setData( final Date data ) {		
			this.data = data;
		}
		
		public String getDocumento() {		
			return documento;
		}
		
		public void setDocumento( final String documento ) {		
			this.documento = documento;
		}
		
		public Integer getFilial() {		
			return filial;
		}
		
		public void setFilial( final Integer filial ) {		
			this.filial = filial;
		}
		
		public String getHistorico() {		
			return historico;
		}
		
		public void setHistorico( final String historico ) {		
			this.historico = historico;
		}
		
		public Integer getTipo() {		
			return tipo;
		}
		
		public String getStrTipo() {
			String stipo = null;
			if ( getTipo() == COMPRAS ) {
				stipo = "Compras";
			}
			else if ( getTipo() == CONTAS_PAGAR ) {
				stipo = "Pagamentos";
			}
			else if ( getTipo() == VENDAS ) {
				stipo = "Vendas";
			}
			else if ( getTipo() == CONTAS_RECEBER ) {
				stipo = "Recebimentos";
			}
			return stipo;
		}
		
		public void setTipo( final Integer tipo ) {		
			this.tipo = tipo;
		}
		
		public BigDecimal getValor() {		
			return valor;
		}
		
		public void setValor( final BigDecimal valor ) {		
			this.valor = valor;
		}
		
		public boolean valido() {
			
			boolean valido = false;
			
			if ( ( getContacred() != null && getContacred().trim().length() > 0 ) && 
					( getContadeb() != null && getContadeb().trim().length() > 0 ) && 
					( getValor() != null && getValor().floatValue() > 0 ) &&	
					( getDocumento() != null && getDocumento().trim().length() > 0 ) && 
					( getHistorico() != null && getHistorico().trim().length() > 0 ) && 
					( getData() != null ) && 
					( getFilial() > 0 ) ) {
				valido = true;
			}
			
			return valido;
		}

		@ Override
		public String toString() {

			return "[" + getDocumento() + "," +
			   			 getContacred() + "," +
						 getContadeb() + "," +
						 getValor() + "," +
						 getData() + "]";
		}
		
	}
}
