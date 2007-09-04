/**
 * @version 19/12/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FRBoleto.java <BR>
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
 * Comentários sobre a classe...
 * 
 */

package org.freedom.modulos.std;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import net.sf.jasperreports.engine.JasperPrintManager;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Extenso;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FPrinterJob;
import org.freedom.telas.FRelatorio;

public class FRBoleto extends FRelatorio {

	private static final long serialVersionUID = 1L;

	public JTextFieldPad txtCodModBol = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescModBol = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldFK txtPreImpModBol = new JTextFieldFK( JTextFieldPad.TP_STRING, 1, 0 );

	private JTextFieldFK txtClassModBol = new JTextFieldFK( JTextFieldPad.TP_STRING, 80, 0 );

	public JTextFieldPad txtCodVenda = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtTipoVenda = new JTextFieldFK( JTextFieldPad.TP_STRING, 1, 0 );

	public JTextFieldFK txtDocVenda = new JTextFieldFK( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDataVenda = new JTextFieldFK( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtCodCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtRazCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtParc = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtDtIni = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDtFim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtCodBanco = new JTextFieldPad( JTextFieldPad.TP_STRING, 8, 0 );

	private JTextFieldFK txtNomeBanco = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtCodTpCob = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescTpCob = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	private final JTextFieldPad txtCodCartCob = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );
	
	private final JTextFieldFK txtDescCartCob = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	// private JCheckBoxPad cbTipoImp = new JCheckBoxPad("Impressão gráfica","S","N");

	private ListaCampos lcModBol = new ListaCampos( this );

	private ListaCampos lcVenda = new ListaCampos( this );

	private ListaCampos lcCli = new ListaCampos( this );

	private ListaCampos lcBanco = new ListaCampos( this );

	private ListaCampos lcTipoCob = new ListaCampos( this );
	
	private final ListaCampos lcCartCob = new ListaCampos( this, "CB" );

	private JInternalFrame fExt = null;

	private String sInfoMoeda[] = new String[ 4 ];

	private List lsParcelas = null;

	private boolean bCodOrc = false;

	private boolean bNomeConv = false;

	private boolean bObsOrc = false;
	
	private boolean bAtlParcela = false;
	

	public FRBoleto() {

		this( null );
	}

	public FRBoleto( JInternalFrame fExt ) {

		setTitulo( "Impressão de boleto/recibo" );
		setAtribos( 80, 80, 545, 375 );

		this.fExt = fExt;

		montaListaCampos();
		montaTela();

		// txtCodVenda.setRequerido( true );

		Calendar cal = Calendar.getInstance();
		txtDtFim.setVlrDate( cal.getTime() );
		txtDtIni.setVlrDate( cal.getTime() );

	}

	private void montaListaCampos() {

		/********************
         * MODELO DE BOLETO *
         ********************/

		lcModBol.add( new GuardaCampo( txtCodModBol, "CodModBol", "Cód.mod.", ListaCampos.DB_PK, true ) );
		lcModBol.add( new GuardaCampo( txtDescModBol, "DescModBol", "Descrição do modelo de boleto", ListaCampos.DB_SI, false ) );
		lcModBol.add( new GuardaCampo( txtPreImpModBol, "PreImpModBol", "Pré-impr.", ListaCampos.DB_SI, false ) );
		lcModBol.add( new GuardaCampo( txtClassModBol, "ClassModBol", "Classe do modelo", ListaCampos.DB_SI, false ) );
		lcModBol.setReadOnly( true );
		lcModBol.montaSql( false, "MODBOLETO", "FN" );
		txtCodModBol.setTabelaExterna( lcModBol );
		txtCodModBol.setFK( true );
		txtCodModBol.setNomeCampo( "CodModBol" );

		/*********
         * VENDA *
         *********/

		lcVenda.add( new GuardaCampo( txtCodVenda, "CodVenda", "Cód.venda", ListaCampos.DB_PK, false ) );
		lcVenda.add( new GuardaCampo( txtDocVenda, "DocVenda", "Doc.", ListaCampos.DB_SI, false ) );
		lcVenda.add( new GuardaCampo( txtDataVenda, "DtEmitVenda", "Data", ListaCampos.DB_SI, false ) );
		lcVenda.add( new GuardaCampo( txtCodCli, "CodCli", "Cód.cli.", ListaCampos.DB_FK, true ) );
		lcVenda.add( new GuardaCampo( txtTipoVenda, "TipoVenda", "Tipo Venda", ListaCampos.DB_SI, false ) );
		lcVenda.add( new GuardaCampo( txtCodBanco, "CodBanco", "Cód.banco", ListaCampos.DB_FK, txtNomeBanco, false) );
		lcVenda.add( new GuardaCampo( txtCodCartCob, "CodCartCob", "Cód.cart.cob", ListaCampos.DB_FK, txtDescCartCob, false ));
		lcVenda.setReadOnly( true );
		lcVenda.montaSql( false, "VENDA", "VD" );
		txtCodVenda.setTabelaExterna( lcVenda );
		txtCodVenda.setFK( true );
		txtCodVenda.setNomeCampo( "CodVenda" );

		/***********
         * CLIENTE *
         ***********/

		lcCli.add( new GuardaCampo( txtCodCli, "CodCli", "Cód.cli.", ListaCampos.DB_PK, false ) );
		lcCli.add( new GuardaCampo( txtRazCli, "RazCli", "Razão social do cliente", ListaCampos.DB_SI, false ) );
		lcCli.setReadOnly( true );
		lcCli.montaSql( false, "CLIENTE", "VD" );
		txtCodCli.setTabelaExterna( lcCli );

		/*********
         * BANCO *
         *********/

		lcBanco.add( new GuardaCampo( txtCodBanco, "CodBanco", "Cód.banco", ListaCampos.DB_PK, false ) );
		lcBanco.add( new GuardaCampo( txtNomeBanco, "NomeBanco", "Nome do Banco", ListaCampos.DB_SI, false ) );
		lcBanco.setReadOnly( true );
		lcBanco.montaSql( false, "BANCO", "FN" );
		txtCodBanco.setTabelaExterna( lcBanco );
		txtCodBanco.setPK( true );
		txtCodBanco.setNomeCampo( "CodBanco" );
		txtCodBanco.setListaCampos( lcBanco );

		/********************
         * TIPO DE COBRANÇA *
         ********************/

		lcTipoCob.add( new GuardaCampo( txtCodTpCob, "CodTipoCob", "Cód.tp.cob.", ListaCampos.DB_PK, false ) );
		lcTipoCob.add( new GuardaCampo( txtDescTpCob, "DescTipoCob", "Descrição do tipo de cobrança", ListaCampos.DB_SI, false ) );
		lcTipoCob.setReadOnly( true );
		lcTipoCob.montaSql( false, "TIPOCOB", "FN" );
		txtCodTpCob.setTabelaExterna( lcTipoCob );
		txtCodTpCob.setPK( true );
		txtCodTpCob.setNomeCampo( "CodTipoCob" );
		txtCodTpCob.setListaCampos( lcTipoCob );
		
		/************************
         * CARTEIRA DE COBRANÇA *
         ************************/
		
		txtCodCartCob.setNomeCampo( "CodCartCob" );
		lcCartCob.add( new GuardaCampo( txtCodCartCob, "CodCartCob", "Cód.cart.cob", ListaCampos.DB_PK, false ) );
		lcCartCob.add( new GuardaCampo( txtCodBanco, "CodBanco", "Cód.banco", ListaCampos.DB_PK, false ) );
		lcCartCob.add( new GuardaCampo( txtDescCartCob, "DescCartCob", "Desc.Cart.Cob", ListaCampos.DB_SI, false ) );
		lcCartCob.setDinWhereAdic( "CODBANCO = #S", txtCodBanco );
		lcCartCob.montaSql( false, "CARTCOB", "FN" );
		lcCartCob.setQueryCommit( false );
		lcCartCob.setReadOnly( true );		
		txtCodCartCob.setTabelaExterna( lcCartCob );
		txtCodCartCob.setListaCampos( lcCartCob );
		txtDescCartCob.setListaCampos( lcCartCob );
		txtCodCartCob.setFK( true );
		
	}

	private void montaTela() {

		adic( new JLabelPad( "Cód.mod." ), 7, 0, 300, 20 );
		adic( txtCodModBol, 7, 20, 80, 20 );
		adic( new JLabelPad( "Descrição do modelo" ), 90, 0, 300, 20 );
		adic( txtDescModBol, 90, 20, 300, 20 );
		adic( new JLabelPad( "Pedido" ), 7, 40, 80, 20 );
		adic( txtCodVenda, 7, 60, 80, 20 );
		adic( new JLabelPad( "Doc." ), 90, 40, 97, 20 );
		adic( txtDocVenda, 90, 60, 97, 20 );
		adic( new JLabelPad( "Data" ), 190, 40, 97, 20 );
		adic( txtDataVenda, 190, 60, 97, 20 );
		adic( new JLabelPad( "Cliente" ), 290, 40, 230, 20 );
		adic( txtRazCli, 290, 60, 230, 20 );
		adic( new JLabelPad( "Nro.parcela" ), 7, 80, 80, 20 );
		adic( txtParc, 7, 100, 80, 20 );
		adic( new JLabelPad( "Cód.banco" ), 90, 80, 80, 20 );
		adic( txtCodBanco, 90, 100, 80, 20 );
		adic( new JLabelPad( "Nome do banco" ), 173, 80, 348, 20 );
		adic( txtNomeBanco, 173, 100, 348, 20 );
		adic( new JLabelPad( "Cód.tp.cob." ), 7, 120, 300, 20 );
		adic( txtCodTpCob, 7, 140, 80, 20 );
		adic( new JLabelPad( "Descrição do tipo de cobrança" ), 90, 120, 430, 20 );
		adic( txtDescTpCob, 90, 140, 430, 20 );

		adic( new JLabelPad( "Cód.cart.cob." ), 7, 160, 80, 20 );
		adic( txtCodCartCob, 7, 180, 80, 20 );
		adic( new JLabelPad( "Descrição da carteira de cobrança" ), 90, 160, 430, 20 );
		adic( txtDescCartCob, 90, 180, 430, 20 );
		
		JLabel periodo = new JLabel( "Período (Emissão)", SwingConstants.CENTER );
		periodo.setOpaque( true );
		
		adic( periodo, 25, 210, 150, 20 );
		JLabel borda = new JLabel();
		borda.setBorder( BorderFactory.createEtchedBorder() );
		adic( borda, 7, 220, 300, 45 );
		adic( txtDtIni, 25, 235, 110, 20 );
		adic( new JLabel( "até", SwingConstants.CENTER ), 135, 235, 40, 20 );
		adic( txtDtFim, 175, 235, 110, 20 );

		// adic( cbTipoImp, 390, 180, 150, 30);
	}

	private String aplicCampos( ResultSet rs, String[] sNat ) {

		Date dCampo = null;
		String sRet = null;
		String sTxa = null;
		String sCampo = null;

		try {

			sTxa = rs.getString( "TxaModBol" );
			sCampo = "";
			dCampo = null;
			String sObsOrc;
			List< String > lObsOrc;
			int iNumLinObs = 0;
			int iNumColObs = 0;

			// Aplicando campos de dados:
			// Estes '\\' que aparecem por ai..são para anular caracteres especiais de "expressão regular".

			if ( sTxa != null ) {

				if ( ( sCampo = rs.getString( "CODORC" ) ) != null )
					sTxa = sTxa.replaceAll( "\\[_CODORC_]", sCampo );

				if ( ( sCampo = rs.getString( "NOMECONV" ) ) != null )
					sTxa = sTxa.replaceAll( "\\[_____________________NOMECONV___________________]", sCampo );

				sObsOrc = rs.getString( "OBSORC" );
				String sObsParam1 = "";
				String sObsParam2 = "";
				if ( ( sCampo = rs.getString( "OBSORC" ) ) != null ) {
					try {
						int iposini = sTxa.indexOf( "[OBSORC_" );
						if ( iposini > -1 ) {

							sObsParam1 = sTxa.substring( iposini + 8, iposini + 11 );
							sObsParam2 = sTxa.substring( iposini + 12, iposini + 15 );

							System.out.println( sObsParam1 );
							System.out.println( sObsParam2 );

							iNumLinObs = new Integer( sObsParam1 ).intValue();
							iNumColObs = new Integer( sObsParam2 ).intValue();

							System.out.println( "iNumLinObs" + iNumLinObs );
							System.out.println( "iNumColObs" + iNumColObs );
						}
					} catch ( Exception e ) {
						e.printStackTrace();
					}
				}

				lObsOrc = (List<String>) Funcoes.stringToVector( sObsOrc, "\n" );

				System.out.println( "tamanho antes" + lObsOrc.size() );

				if ( lObsOrc.size() > iNumLinObs ) {
					lObsOrc = lObsOrc.subList( 0, iNumLinObs );
				}
				else {
					while ( lObsOrc.size() < iNumLinObs ) {
						lObsOrc.add( "" );
					}
				}

				System.out.println( "tamanho depois" + lObsOrc.size() );
				String sLinhaObs;
				String sLinhasObs = "";
				for ( int i = 0; i < lObsOrc.size(); i++ ) {
					sLinhaObs = lObsOrc.get( i ).toString();
					sLinhaObs = sLinhaObs.length() > iNumColObs ? sLinhaObs.substring( 0, iNumColObs ) : sLinhaObs;
					if ( i == 0 ) {
						sLinhasObs = sLinhaObs;
					}
					else {
						sLinhasObs = sLinhasObs + "\n" + sLinhaObs;
					}

				}
				System.out.println( "Linhas " + ":" + sLinhasObs );

				sTxa = sTxa.replaceAll( "\\[OBSORC_" + sObsParam1 + "_" + sObsParam2 + "]", sLinhasObs );

				if ( ( dCampo = rs.getDate( "DtVencItRec" ) ) != null )
					sTxa = sTxa.replaceAll( "\\[VENCIMEN]", Funcoes.sqlDateToStrDate( dCampo ) );
				if ( ( dCampo = rs.getDate( "DtEmitVenda" ) ) != null )
					sTxa = sTxa.replaceAll( "\\[DATADOC_]", Funcoes.sqlDateToStrDate( dCampo ) );
				if ( ( sCampo = rs.getString( "CodRec" ) ) != null )
					sTxa = sTxa.replaceAll( "\\[CODREC]", Funcoes.alinhaDir( sCampo, 8 ) );
				if ( ( sCampo = rs.getString( "DocVenda" ) ) != null )
					sTxa = sTxa.replaceAll( "\\[__DOCUMENTO__]", Funcoes.alinhaDir( sCampo, 15 ) );
				if ( ( sCampo = rs.getString( "ReciboItRec" ) ) != null )
					sTxa = sTxa.replaceAll( "\\[RECIBO]", Funcoes.alinhaDir( sCampo, 8 ) );
				if ( ( sCampo = rs.getString( "NParcItRec" ) ) != null ) {
					sTxa = sTxa.replaceAll( "\\[P]", Funcoes.copy( sCampo, 0, 3 ) );
					if ( rs.getInt( 1 ) > 1 )
						sTxa = sTxa.replaceAll( "\\[A]", "" + ( (char) ( rs.getInt( "NParcItRec" ) + 64 ) ) );
				}
				if ( ( sCampo = rs.getInt( 1 ) + "" ) != null )
					sTxa = sTxa.replaceAll( "\\[T]", "/" + Funcoes.copy( sCampo, 0, 2 ) );
				if ( ( sCampo = rs.getString( "VlrParcItRec" ) ) != null && rs.getDouble( "VlrParcItRec" ) != 0 ) {
					sTxa = sTxa.replaceAll( "\\[VALOR_DOCUMEN]", Funcoes.strDecimalToStrCurrency( 15, 2, sCampo ) );
					sTxa = sTxa.replaceAll( "\\[VALOR_EXTENSO]", Extenso.extenso( rs.getDouble( "VlrParcItRec" ), sInfoMoeda[ 0 ], sInfoMoeda[ 1 ], sInfoMoeda[ 2 ], sInfoMoeda[ 3 ] ) );
				}
				if ( ( sCampo = rs.getString( "VlrApagItRec" ) ) != null && rs.getDouble( "VlrApagItRec" ) != 0 )
					sTxa = sTxa.replaceAll( "\\[VLIQ_DOCUMENT]", Funcoes.strDecimalToStrCurrency( 15, 2, sCampo ) );
				if ( ( sCampo = rs.getString( "VlrDescItRec" ) ) != null && rs.getDouble( "VlrDescItRec" ) != 0 )
					sTxa = sTxa.replaceAll( "\\[DESC_DOCUMENT]", Funcoes.strDecimalToStrCurrency( 15, 2, sCampo ) );
				if ( ( sCampo = rs.getString( "CodCli" ) ) != null )
					sTxa = sTxa.replaceAll( "\\[CODCLI]", Funcoes.copy( sCampo, 0, 8 ) );
				if ( ( sCampo = rs.getString( "RazCli" ) ) != null )
					sTxa = sTxa.replaceAll( "\\[_____________RAZAO____DO____CLIENTE_____________]", Funcoes.copy( sCampo, 0, 50 ) );
				if ( ( sCampo = rs.getString( "NomeCli" ) ) != null )
					sTxa = sTxa.replaceAll( "\\[_____________NOME_____DO____CLIENTE_____________]", Funcoes.copy( sCampo, 0, 50 ) );
				if ( ( sCampo = rs.getString( "CpfCli" ) ) != null )
					sTxa = sTxa.replaceAll( "\\[CPF/CNPJ_ CLIENT]", Funcoes.setMascara( sCampo, "###.###.###-##" ) );
				else if ( ( sCampo = rs.getString( "CnpjCli" ) ) != null )
					sTxa = sTxa.replaceAll( "\\[CPF/CNPJ_ CLIENT]", Funcoes.setMascara( sCampo, "##.###.###/####-##" ) );
				if ( ( sCampo = rs.getString( "RgCli" ) ) != null )
					sTxa = sTxa.replaceAll( "\\[____IE/RG____CLIENTE]", Funcoes.copy( sCampo, 0, 22 ) );
				else if ( ( sCampo = rs.getString( "InscCli" ) ) != null )
					sTxa = sTxa.replaceAll( "\\[____IE/RG____CLIENTE]", Funcoes.copy( sCampo, 0, 22 ) );
				if ( ( sCampo = rs.getString( "EndCob" ) ) != null || ( sCampo = rs.getString( "EndCli" ) ) != null )
					sTxa = sTxa.replaceAll( "\\[____________ENDERECO____DO____CLIENTE___________]", sCampo.trim() );
				if ( ( sCampo = rs.getString( "NumCob" ) ) != null || ( sCampo = rs.getString( "NumCli" ) ) != null )
					sTxa = sTxa.replaceAll( "\\[NUMERO]", sCampo );
				if ( ( sCampo = rs.getString( "ComplCob" ) ) != null || ( sCampo = rs.getString( "ComplCli" ) ) != null )
					sTxa = sTxa.replaceAll( "\\[____COMPLEMENTO___]", sCampo.trim() );
				if ( ( sCampo = rs.getString( "CepCob" ) ) != null || ( sCampo = rs.getString( "CepCli" ) ) != null )
					sTxa = sTxa.replaceAll( "\\[__CEP__]", Funcoes.setMascara( sCampo, "#####-###" ) );
				if ( ( sCampo = rs.getString( "BairCob" ) ) != null || ( sCampo = rs.getString( "BairCli" ) ) != null )
					sTxa = sTxa.replaceAll( "\\[___________BAIRRO___________]", sCampo.trim() );
				if ( ( sCampo = rs.getString( "CidCob" ) ) != null || ( sCampo = rs.getString( "CidCli" ) ) != null )
					sTxa = sTxa.replaceAll( "\\[___________CIDADE___________]", sCampo.trim() );
				if ( ( sCampo = rs.getString( "UfCob" ) ) != null || ( sCampo = rs.getString( "UfCli" ) ) != null )
					sTxa = sTxa.replaceAll( "\\[UF]", Funcoes.copy( sCampo, 0, 2 ) );
				if ( ( sCampo = rs.getString( "FoneCli" ) ) != null )
					sTxa = sTxa.replaceAll( "\\[__TELEFONE___]", Funcoes.setMascara( sCampo.trim(), "####-####" ) );
				if ( ( sCampo = rs.getString( "DDDCli" ) ) != null || ( sCampo = "(" + rs.getString( "DDDCli" ) ) + ")" != null )
					sTxa = sTxa.replaceAll( "\\[DDD]", Funcoes.copy( sCampo, 0, 5 ) );
				if ( ( sCampo = sNat[ 0 ] ) != null )
					sTxa = sTxa.replaceAll( "\\[CODNAT]", Funcoes.copy( sCampo, 0, 8 ) );
				if ( ( sCampo = sNat[ 1 ] ) != null )
					sTxa = sTxa.replaceAll( "\\[______________NATUREZA_DA_OPERACAO______________]", Funcoes.copy( sCampo, 0, 50 ) );

				// Aplicar campos especiais de dados:

				int iPos = 0;
				while ( ( iPos = sTxa.indexOf( "%_VAL", iPos + 1 ) ) > 0 ) {
					double dVal = 0;
					String sCaixa = sTxa.substring( iPos - 9, iPos );
					sCaixa += "\\" + sTxa.substring( iPos, iPos + 6 );
					dVal = rs.getDouble( "VlrParcitRec" );
					dVal *= Double.parseDouble( sTxa.substring( iPos - 8, iPos ) ) / 100;
					sTxa = sTxa.replaceAll( "\\" + sCaixa, Funcoes.strDecimalToStrCurrency( 15, 2, new BigDecimal( dVal ).setScale( 2, BigDecimal.ROUND_HALF_UP ).toString() ) );
				}
				iPos = 0;
				while ( ( iPos = sTxa.indexOf( "+_VAL", iPos + 1 ) ) > 0 ) {
					double dVal = 0;
					String sCaixa = sTxa.substring( iPos - 9, iPos );
					sCaixa += "\\" + sTxa.substring( iPos, iPos + 6 );
					dVal = rs.getDouble( "VlrParcitRec" );
					dVal += Double.parseDouble( sTxa.substring( iPos - 8, iPos ) );
					sTxa = sTxa.replaceAll( "\\" + sCaixa, Funcoes.strDecimalToStrCurrency( 15, 2, new BigDecimal( dVal ).setScale( 2, BigDecimal.ROUND_HALF_UP ).toString() ) );
				}
				iPos = 0;
				while ( ( iPos = sTxa.indexOf( "-_VAL", iPos + 1 ) ) > 0 ) {
					double dVal = 0;
					String sCaixa = sTxa.substring( iPos - 9, iPos );
					sCaixa += "\\" + sTxa.substring( iPos, iPos + 6 );
					dVal = rs.getDouble( "VlrParcitRec" );
					dVal -= Double.parseDouble( sTxa.substring( iPos - 8, iPos ) );
					sTxa = sTxa.replaceAll( "\\" + sCaixa, Funcoes.strDecimalToStrCurrency( 15, 2, new BigDecimal( dVal ).setScale( 2, BigDecimal.ROUND_HALF_UP ).toString() ) );
				}
				iPos = 0;
				while ( ( iPos = sTxa.indexOf( "+_VEN", iPos + 1 ) ) > 0 ) {
					GregorianCalendar cVal = new GregorianCalendar();
					String sCaixa = sTxa.substring( iPos - 4, iPos );
					sCaixa += "\\" + sTxa.substring( iPos, iPos + 6 );
					cVal.setTime( rs.getDate( "DtVencItRec" ) );
					cVal.set( Calendar.DATE, cVal.get( Calendar.DATE ) + Integer.parseInt( sTxa.substring( iPos - 3, iPos ) ) );
					sTxa = sTxa.replaceAll( "\\" + sCaixa, Funcoes.dateToStrDate( cVal.getTime() ) );
				}

				sRet = sTxa;
			}

			// Ajustando campos de ação:

			sRet = sRet.replaceAll( "\\<LP\\>.*].*\\<_LP\\>", "" );
			sRet = sRet.replaceAll( "\\<[_]*LP\\>", "" );

			// Tirando campos não setados:

			Pattern p = Pattern.compile( "\\[.*\\]" );
			Matcher m = p.matcher( sRet );
			StringBuffer sb = new StringBuffer();

			while ( m.find() ) {
				m.appendReplacement( sb, Funcoes.replicate( " ", m.end() - m.start() ) );
			}

			m.appendTail( sb );
			sRet = sb.toString();
			
		} 
		catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro na consulta ao modelo de boleto!\n" + err.getMessage(), true, con, err );
			err.printStackTrace();
		}

		return sRet;
	}

	private String[] getMoeda() {

		String sRet[] = new String[ 5 ];
		StringBuilder sSQL = new StringBuilder();

		try {

			sSQL.append( "SELECT M.SINGMOEDA,M.PLURMOEDA,M.DECSMOEDA,M.DECPMOEDA,M.CODFBNMOEDA FROM FNMOEDA M, SGPREFERE1 P " );
			sSQL.append( "WHERE M.CODMOEDA=P.CODMOEDA AND M.CODEMP=P.CODEMPMO AND M.CODFILIAL=P.CODFILIALMO " );
			sSQL.append( "AND P.CODEMP=? AND P.CODFILIAL=?" );

			PreparedStatement ps = con.prepareStatement( sSQL.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, Aplicativo.iCodFilial );
			ResultSet rs = ps.executeQuery();

			if ( rs.next() ) {
				sRet[ 0 ] = rs.getString( "SingMoeda" ).trim();
				sRet[ 1 ] = rs.getString( "PlurMoeda" ).trim();
				sRet[ 2 ] = rs.getString( "DecSMoeda" ).trim();
				sRet[ 3 ] = rs.getString( "DecPMoeda" ).trim();
				sRet[ 4 ] = rs.getString( "CodFbnMoeda" ).trim();
			}
			rs.close();
			ps.close();

			if ( ! con.getAutoCommit() ) { 
				con.commit(); 
			}
		} catch ( SQLException err ) {
			Funcoes.mensagemErro( null, "Erro ao buscar a moeda padrão!\n" + err.getMessage(), true, con, err );
			err.printStackTrace();
		}

		if ( sRet == null ) {
			Funcoes.mensagemErro( null, "A moeda padrão pode não estar ajustada no preferências!" );
		}

		return sRet;
	}

	private String getClassModelo( final String preImpModbol, final String classModBol ) {

		String retorno = null;

		if ( "N".equals( preImpModbol ) ) {
			retorno = classModBol;
		}
		return retorno;

	}
	
	private int getCodrec( final int codvenda, final String tipovenda ) {
		
		int codrec = 0;
		
		try {
			
			StringBuilder sql = new StringBuilder();

			sql.append( "SELECT CODREC FROM FNRECEBER " );
			sql.append( "WHERE CODEMP=? AND CODFILIAL=? AND " );
			sql.append( "CODEMPVA=? AND CODFILIALVA=? AND CODVENDA=? AND TIPOVENDA=?" );
			
			PreparedStatement ps = con.prepareStatement( sql.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "FNRECEBER" ) );
			ps.setInt( 3, Aplicativo.iCodEmp );
			ps.setInt( 4, ListaCampos.getMasterFilial( "VDVENDA" ) );
			ps.setInt( 5, codvenda );
			ps.setString( 6, tipovenda );
			
			ResultSet rs = ps.executeQuery();
			
			if ( rs.next() ) {
				
				codrec = rs.getInt( "CODREC" );
			}
			
			rs.close();
			ps.close();
			
			if ( ! con.getAutoCommit() ) {
				con.commit();
			}
		}
		catch ( SQLException e ) {
			e.printStackTrace();
		}
		
		return codrec;
	}
	
	private void getAtualizaParcela() {
		
		try {
			
			String sql = "SELECT ATBANCOIMPBOL FROM SGPREFERE1 WHERE CODEMP=? AND CODFILIAL=?";
			
			PreparedStatement ps = con.prepareStatement( sql );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "SGPREFERE1" ) );
			
			ResultSet rs = ps.executeQuery();
			
			if ( rs.next() ) {
				
				bAtlParcela = "S".equalsIgnoreCase( rs.getString( "ATBANCOIMPBOL" ) );
			}
			
			rs.close();
			ps.close();
			
			if ( ! con.getAutoCommit() ) {
				con.commit();
			}
		}
		catch ( SQLException e ) {
			e.printStackTrace();
		}
	}
	
	private boolean atualizaParcela( final int codrec, final Integer codparc, final String codbanco, final String codcartcob ) {
		
		boolean ret = true;
		boolean bcart = false;
		int iparam = 1;
		
		if ( bAtlParcela && ( codbanco != null && codbanco.trim().length() > 0 ) && codrec > 0 ) {
			
			try {
				
				bcart = ( codcartcob != null && codcartcob.trim().length() > 0 ) ;
				
				StringBuilder sql = new StringBuilder();

				sql.append( "UPDATE FNRECEBER SET CODEMPBO=?, CODFILIALBO=?, CODBANCO=? " );
				if ( bcart ) {
					sql.append( ", CODEMPCB=?, CODFILIALCB=?,  CODCARTCOB=? " );
				}
				sql.append( "WHERE CODEMP=? AND CODFILIAL=? AND CODREC=?" );

				PreparedStatement ps = con.prepareStatement( sql.toString() );
				ps.setInt( iparam++, Aplicativo.iCodEmp );
				ps.setInt( iparam++, ListaCampos.getMasterFilial( "FNBANCO" ) );
				ps.setString( iparam++, codbanco );

				if ( bcart ) {
					ps.setInt( iparam++, Aplicativo.iCodEmp );
					ps.setInt( iparam++, ListaCampos.getMasterFilial( "FNCARTCOB" ) );
					ps.setString( iparam++, codcartcob );
				}

				ps.setInt( iparam++, Aplicativo.iCodEmp );
				ps.setInt( iparam++, ListaCampos.getMasterFilial( "FNRECEBER" ) );
				ps.setInt( iparam++, codrec );
				ps.executeUpdate();
				ps.close();

				if ( !con.getAutoCommit() ) {
					con.commit();
				}

				sql = new StringBuilder();

				sql.append( "UPDATE FNITRECEBER SET CODEMPBO=?, CODFILIALBO=?, CODBANCO=? " );
				if ( bcart ) {
					sql.append( ", CODEMPCB=?, CODFILIALCB=?,  CODCARTCOB=? " );
				}
				sql.append( "WHERE CODEMP=? AND CODFILIAL=? AND CODREC=? " );
				if ( codparc != null && codparc > 0 ) {
					sql.append( "AND NPARCITREC=? " );
				}

				iparam = 1;

				ps = con.prepareStatement( sql.toString() );
				ps.setInt( iparam++, Aplicativo.iCodEmp );
				ps.setInt( iparam++, ListaCampos.getMasterFilial( "FNBANCO" ) );
				ps.setString( iparam++, codbanco );

				if ( bcart ) {
					ps.setInt( iparam++, Aplicativo.iCodEmp );
					ps.setInt( iparam++, ListaCampos.getMasterFilial( "FNCARTCOB" ) );
					ps.setString( iparam++, codcartcob );
				}

				ps.setInt( iparam++, Aplicativo.iCodEmp );
				ps.setInt( iparam++, ListaCampos.getMasterFilial( "FNITRECEBER" ) );
				ps.setInt( iparam++, codrec );

				if ( codparc != null && codparc > 0 ) {
					ps.setInt( iparam++, codparc );
				}
				ps.executeUpdate();
				ps.close();
				
				if ( ! con.getAutoCommit() ) {
					con.commit();
				}
			}
			catch ( Exception e ) {
				e.printStackTrace();
				Funcoes.mensagemErro( this, "Erro ao atualizar paracela(s)!\n" + e.getMessage() );
				ret = false;
			}
		}
		
		return ret;
	}

	private HashMap< String, Object > getParametros() {

		HashMap< String, Object > parametros = new HashMap< String, Object >();

		parametros.put( "CODEMP", Aplicativo.iCodEmp );
		parametros.put( "CODFILIAL", ListaCampos.getMasterFilial( "FNITRECEBER" ) );
		//parametros.put( "CODVENDA", txtCodVenda.getVlrInteger() );

		return parametros;
	}

	public void setParcelas( final List lsParcParam ) {

		lsParcelas = lsParcParam;
	}

	public void imprimir( boolean bVisualizar ) {

 		final int codvenda = txtCodVenda.getVlrInteger().intValue();
		final int nparc = txtParc.getVlrInteger().intValue();
		final String codbanco = txtCodBanco.getVlrString().trim();
		final int codtipocob = txtCodTpCob.getVlrInteger().intValue();

		int param = 1;

		if ( txtCodModBol.getVlrString().equals( "" ) ) {
			Funcoes.mensagemInforma( this, "Modelo de boleto não selecionado!" );
			txtCodModBol.requestFocus();
			return;
		}
		else if ( ( codvenda == 0 ) && ( ( "".equals( txtDtIni.getVlrString() ) ) || ( "".equals( txtDtFim.getVlrString() ) ) ) ) {
			Funcoes.mensagemInforma( this, "Período não selecionado!" );
			txtDtIni.requestFocus();
			return;
		}
		

		try {
			
			atualizaParcela( getCodrec( txtCodVenda.getVlrInteger(), txtTipoVenda.getVlrString() ), txtParc.getVlrInteger(), txtCodBanco.getVlrString(), txtCodCartCob.getVlrString() );

			PreparedStatement ps = null;
			ResultSet rs = null;
			StringBuilder sSQL = new StringBuilder();
			StringBuilder sWhere = new StringBuilder();
			ImprimeOS imp = null;

			sWhere.append( "MB.CODEMP=? AND MB.CODFILIAL=? AND MB.CODMODBOL=? AND " );
			if ( codvenda != 0 ) {
				sWhere.append( "R.CODEMPVA=? AND R.CODFILIALVA=? AND R.CODVENDA=? " );
			}
			else {
				sWhere.append( "V.DTEMITVENDA BETWEEN ? AND ? " );
			}
			if ( !"".equals( codbanco ) ) {
				sWhere.append( "AND B.CODEMP=? AND B.CODFILIAL=? AND B.CODBANCO=? " );
			}
			if ( codtipocob != 0 ) {
				sWhere.append( "AND ITR.CODEMPTC=? AND ITR.CODFILIALTC=? AND ITR.CODTIPOCOB=? " );
			}
			if ( nparc != 0 ) {
				sWhere.append( "AND ITR.NPARCITREC=? " );
			}
			if ( ( lsParcelas != null ) && ( lsParcelas.size() > 0 ) ) {
				sWhere.append( "AND ITR.NPARCITREC IN (" );
				for ( int i = 0; i < lsParcelas.size(); i++ ) {
					if ( i != 0 ) {
						sWhere.append( "," );
					}
					sWhere.append( lsParcelas.get( i ) );
				}
				sWhere.append( ")" );
			}

			imp = new ImprimeOS( "", con );
			imp.verifLinPag();
			imp.setTitulo( "Boleto" );

			sSQL.append( "SELECT (SELECT COUNT(*) FROM FNITRECEBER ITR2 ");
			sSQL.append( "WHERE ITR2.CODREC=R.CODREC AND ITR2.CODEMP=R.CODEMP AND ITR2.CODFILIAL=R.CODFILIAL) PARCS, " );
			sSQL.append( "ITR.DTVENCITREC,ITR.NPARCITREC,ITR.VLRAPAGITREC,ITR.VLRPARCITREC,ITR.VLRDESCITREC, " );
			sSQL.append( "(ITR.VLRJUROSITREC+ITR.VLRMULTAITREC) VLRMULTA, " );
			sSQL.append( "R.DOCREC,ITR.CODBANCO, B.DVBANCO, " );
			sSQL.append( "B.IMGBOLBANCO LOGOBANCO01, B.IMGBOLBANCO LOGOBANCO02, B.IMGBOLBANCO LOGOBANCO03, " );
			sSQL.append( "IM.CODCARTCOB, MB.ESPDOCMODBOL ESPDOC, MB.ACEITEMODBOL ACEITE, MB.MDECOB, " );
			sSQL.append( "MB.PREIMPMODBOL, MB.CLASSMODBOL, MB.TXAMODBOL, V.DTEMITVENDA, V.DOCVENDA, " );
			sSQL.append( "C.CODCLI,C.RAZCLI,C.NOMECLI,C.CPFCLI,C.CNPJCLI,C.RGCLI,C.INSCCLI, " );
			sSQL.append( "C.ENDCLI,C.NUMCLI,C.COMPLCLI,C.CEPCLI,C.BAIRCLI,C.CIDCLI,C.UFCLI, " );
			sSQL.append( "C.ENDCOB,C.NUMCOB,C.COMPLCOB,C.CEPCOB,C.BAIRCOB,C.CIDCOB,C.UFCOB, " );
			sSQL.append( "C.FONECLI,C.DDDCLI,R.CODREC, P.CODMOEDA, C.PESSOACLI, ITR.RECIBOITREC, " );
			sSQL.append( "(ITR.DTVENCITREC-CAST('07.10.1997' AS DATE)) FATVENC, M.CODFBNMOEDA, " );
			sSQL.append( "IV.CODNAT, N.DESCNAT, F.RAZFILIAL, CT.AGENCIACONTA, MB.NUMCONTA, " );
			sSQL.append( "MB.DESCLPMODBOL, MB.INSTPAGMODBOL, IM.CONVCOB, " );

			sSQL.append( "(SELECT FIRST 1 VO.CODORC FROM VDVENDAORC VO " );
			sSQL.append( "WHERE VO.CODEMP=V.CODEMP AND VO.CODFILIAL=VO.CODFILIAL AND " );
			sSQL.append( "VO.CODVENDA = V.CODVENDA AND VO.TIPOVENDA=V.TIPOVENDA) AS CODORC, " );

			sSQL.append( "(SELECT AC.NOMECONV FROM ATCONVENIADO AC,VDORCAMENTO VA " );
			sSQL.append( "WHERE VA.CODEMP=V.CODEMP AND VA.CODFILIAL=V.CODFILIAL AND " );
			sSQL.append( "VA.CODORC =(SELECT FIRST 1 VO.CODORC FROM VDVENDAORC VO " );
			sSQL.append( "WHERE VO.CODEMP=V.CODEMP AND VO.CODFILIAL=VO.CODFILIAL AND " );
			sSQL.append( "VO.CODVENDA = V.CODVENDA AND VO.TIPOVENDA=V.TIPOVENDA) AND " );
			sSQL.append( "AC.CODEMP=VA.CODEMPCV AND AC.CODFILIAL=VA.CODFILIALCV AND " );
			sSQL.append( "AC.CODCONV=VA.CODCONV) AS NOMECONV, " );

			sSQL.append( "(SELECT VA.OBSORC FROM VDORCAMENTO VA " );
			sSQL.append( "WHERE VA.CODEMP=V.CODEMP AND VA.CODFILIAL=V.CODFILIAL AND " );
			sSQL.append( "VA.CODORC =(SELECT FIRST 1 VO.CODORC FROM VDVENDAORC VO " );
			sSQL.append( "WHERE VO.CODEMP=V.CODEMP AND VO.CODFILIAL=VO.CODFILIAL AND " );
			sSQL.append( "VO.CODVENDA = V.CODVENDA AND VO.TIPOVENDA=V.TIPOVENDA)) AS OBSORC " );
/*			sql.append( "SELECT F.RAZFILIAL, C.AGENCIACONTA, MB.NUMCONTA, MB.DESCLPMODBOL, " );
			sql.append( "MB.INSTPAGMODBOL, B.IMGBOLBANCO, IM.CONVCOB " );
			sql.append( "FROM SGFILIAL F, FNCONTA C, FNMODBOLETO MB, FNBANCO B, FNITMODBOLETO IM " );
			sql.append( "WHERE MB.CODEMP=? AND MB.CODFILIAL=? AND MB.CODMODBOL =? " );
			sql.append( "F.CODEMP=MB.CODEMP AND F.CODFILIAL=MB.CODFILIAL AND " );
			sql.append( "IM.CODEMP=M.CODEMP AND IM.CODFILIAL=M.CODFILIAL AND IM.CODEMPBO=B.CODEMP AND ");
			sql.append( "IM.CODFILIALBO=B.CODFILIAL AND IM.CODBANCO=B.CODBANCO AND ");
			sql.append( "IM.CODEMPCB=? AND IM.CODFILIALCB=? AND IM.CODCARTCOB=? AND " );
			sql.append( "C.CODEMP=MB.CODEMPCC AND C.CODFILIAL=MB.CODFILIALCC AND C.NUMCONTA=MB.NUMCONTA AND " );
			sql.append( "B.CODEMPMB=MB.CODEMP AND B.CODFILIALMB=MB.CODFILIAL AND B.CODMODBOL=MB.CODMODBOL" );
*/
			sSQL.append( "FROM VDVENDA V,VDCLIENTE C, FNRECEBER R, SGPREFERE1 P, FNMOEDA M, FNBANCO B, " );
			sSQL.append( "FNMODBOLETO MB, FNITMODBOLETO IM, VDITVENDA IV, LFNATOPER N,  FNITRECEBER ITR, ");
			sSQL.append( "SGFILIAL F, FNCONTA CT " );
			sSQL.append( "WHERE ITR.CODREC=R.CODREC AND ITR.CODEMP=R.CODEMP AND ITR.CODFILIAL=R.CODFILIAL AND " );
			sSQL.append( "V.CODVENDA=R.CODVENDA AND V.CODEMP=R.CODEMPVA AND V.CODFILIAL=R.CODFILIALVA AND " );
			sSQL.append( "F.CODEMP=R.CODEMP AND F.CODFILIAL=R.CODFILIAL AND ");
			sSQL.append( "CT.CODEMP=MB.CODEMPCC AND CT.CODFILIAL=MB.CODFILIALCC AND CT.NUMCONTA=MB.NUMCONTA AND " );
			sSQL.append( "C.CODCLI=V.CODCLI AND C.CODEMP=V.CODEMPCL AND C.CODFILIAL=V.CODFILIALCL AND " );
			sSQL.append( "P.CODEMP=R.CODEMP AND P.CODFILIAL=R.CODFILIAL AND " );
			sSQL.append( "M.CODEMP=P.CODEMPMO AND M.CODFILIAL=P.CODFILIALMO AND M.CODMOEDA=P.CODMOEDA AND " );
			sSQL.append( "B.CODEMP=ITR.CODEMPBO AND B.CODFILIAL=ITR.CODFILIALBO AND B.CODBANCO=ITR.CODBANCO AND " );
			sSQL.append( "MB.CODEMP=B.CODEMPMB AND MB.CODFILIAL=B.CODFILIALMB AND MB.CODMODBOL=B.CODMODBOL AND  " );
			sSQL.append( "IM.CODEMP=MB.CODEMP AND IM.CODFILIAL=MB.CODFILIAL AND IM.CODMODBOL=MB.CODMODBOL AND ");
			sSQL.append( "IM.CODEMPBO=ITR.CODEMPBO AND IM.CODFILIALBO=ITR.CODFILIALBO AND IM.CODBANCO=ITR.CODBANCO AND " );
			sSQL.append( "IM.CODEMPCB=ITR.CODEMPCB AND IM.CODFILIALCB=ITR.CODFILIALCB AND IM.CODCARTCOB=ITR.CODCARTCOB AND " );
			sSQL.append( "IV.CODEMP=V.CODEMP AND IV.CODFILIAL=V.CODFILIAL AND IV.TIPOVENDA=V.TIPOVENDA AND " );
			sSQL.append( "IV.CODVENDA=V.CODVENDA AND IV.CODITVENDA=( SELECT MIN(CODITVENDA) FROM VDITVENDA IV2 " );
			sSQL.append( "WHERE IV2.CODEMP=IV.CODEMP AND IV2.CODFILIAL=IV.CODFILIAL AND IV2.TIPOVENDA=IV.TIPOVENDA AND " );
			sSQL.append( "IV2.CODVENDA=IV.CODVENDA AND IV2.CODNAT IS NOT NULL ) AND " );
			sSQL.append( "N.CODEMP=IV.CODEMPNT AND N.CODFILIAL=IV.CODFILIALNT AND N.CODNAT=IV.CODNAT AND  " );
			sSQL.append( "ITR.STATUSITREC IN ('R1','RL') AND ");
			sSQL.append( sWhere );

			String strDebug = sSQL.toString();

			ps = con.prepareStatement( sSQL.toString() );
			ps.setInt( param++, Aplicativo.iCodEmp );

			strDebug = strDebug.replaceFirst( "\\?", Aplicativo.iCodEmp + "" );

			ps.setInt( param++, ListaCampos.getMasterFilial( "FNMODBOLETO" ) );

			strDebug = strDebug.replaceFirst( "\\?", ListaCampos.getMasterFilial( "FNMODBOLETO" ) + "" );

			ps.setInt( param++, txtCodModBol.getVlrInteger().intValue() );

			strDebug = strDebug.replaceFirst( "\\?", txtCodModBol.getVlrInteger().intValue() + "" );

			if ( codvenda != 0 ) {
				ps.setInt( param++, Aplicativo.iCodEmp );

				strDebug = strDebug.replaceFirst( "\\?", Aplicativo.iCodEmp + "" );

				ps.setInt( param++, ListaCampos.getMasterFilial( "VDVENDA" ) );

				strDebug = strDebug.replaceFirst( "\\?", ListaCampos.getMasterFilial( "VDVENDA" ) + "" );

				ps.setInt( param++, txtCodVenda.getVlrInteger().intValue() );

				strDebug = strDebug.replaceFirst( "\\?", txtCodVenda.getVlrInteger().intValue() + "" );

			}
			else {
				ps.setDate( param++, Funcoes.dateToSQLDate( txtDtIni.getVlrDate() ) );

				strDebug = strDebug.replaceFirst( "\\?", Funcoes.dateToSQLDate( txtDtIni.getVlrDate() ) + "" );

				ps.setDate( param++, Funcoes.dateToSQLDate( txtDtFim.getVlrDate() ) );

				strDebug = strDebug.replaceFirst( "\\?", Funcoes.dateToSQLDate( txtDtFim.getVlrDate() ) + "" );

			}
			if ( !"".equals( codbanco ) ) {
				ps.setInt( param++, Aplicativo.iCodEmp );

				strDebug = strDebug.replaceFirst( "\\?", Aplicativo.iCodEmp + "" );

				ps.setInt( param++, ListaCampos.getMasterFilial( "FNBANCO" ) );

				strDebug = strDebug.replaceFirst( "\\?", ListaCampos.getMasterFilial( "FNBANCO" ) + "" );

				ps.setString( param++, codbanco );

				strDebug = strDebug.replaceFirst( "\\?", codbanco + "" );

			}
			if ( codtipocob != 0 ) {
				ps.setInt( param++, Aplicativo.iCodEmp );

				strDebug = strDebug.replaceFirst( "\\?", Aplicativo.iCodEmp + "" );

				ps.setInt( param++, ListaCampos.getMasterFilial( "FNTIPOCOB" ) );

				strDebug = strDebug.replaceFirst( "\\?", ListaCampos.getMasterFilial( "FNTIPOCOB" ) + "" );

				ps.setInt( param++, codtipocob );

				strDebug = strDebug.replaceFirst( "\\?", codtipocob + "" );

			}
			if ( nparc != 0 ) {
				ps.setInt( param++, nparc );
				strDebug = strDebug.replaceFirst( "\\?", nparc + "" );
			}

			rs = ps.executeQuery();

			String classe = getClassModelo( txtPreImpModBol.getVlrString(), txtClassModBol.getVlrString() );
			
			if ( classe == null ) {
				imprimeTexto( bVisualizar, rs );
			}
			else {
				imprimeGrafico( bVisualizar, rs, classe );
			}
			
			rs.close();
			ps.close();

			if ( !con.getAutoCommit() ) {
				con.commit();
			}

			System.out.println( "SQL: " + strDebug );

		} catch ( Exception err ) {
			Funcoes.mensagemErro( null, "Erro ao tentar imprimir!\n" + err.getMessage(), true, con, err );
			err.printStackTrace();
		}
	}

	private void imprimeTexto( final boolean bVisualizar, final ResultSet rs ) throws Exception {

		String sVal = null;
		ImprimeOS imp = null;
		imp = new ImprimeOS( "", con );
		imp.verifLinPag();
		imp.setTitulo( "Boleto" );
		String[] sNat = null;

		while ( rs.next() ) {
			sNat = new String[ 2 ];
			sNat[ 0 ] = rs.getString( "CODNAT" );
			sNat[ 1 ] = rs.getString( "DESCNAT" );
			sVal = aplicCampos( rs, sNat );

			if ( sVal != null ) {

				String[] sLinhas = ( sVal + " " ).split( "\n" );

				for ( int i = 0; i < sLinhas.length; i++ ) {
					if ( i == 0 ) {
						imp.say( imp.pRow() + 1, 0, imp.normal() + imp.comprimido() + "" );
						imp.say( imp.pRow(), 0, sLinhas[ i ] );
					}
					else {
						imp.say( imp.pRow() + 1, 0, sLinhas[ i ] );
					}
				}
			}

		}

		imp.fechaGravacao();

		if ( bVisualizar ) {

			if ( fExt == null ) {
				imp.preview( this );
			}
			else {
				imp.preview( fExt );
			}
		}
		else {
			imp.print();
		}
	}

	private void imprimeGrafico( final boolean bVisualizar, final ResultSet rs, final String classe ) {

		FPrinterJob dlGr = new FPrinterJob( "relatorios/" + classe, "Boleto", null, rs, getParametros(), this );

		if ( bVisualizar ) {
			dlGr.setVisible( true );
		}
		else {
			try {
				JasperPrintManager.printReport( dlGr.getRelatorio(), true );
			} catch ( Exception err ) {
				Funcoes.mensagemErro( this, "Erro ao tentar imprimir boleto!" + err.getMessage(), true, con, err );
			}
		}
	}

	public void setConexao( Connection cn ) {

		super.setConexao( cn );
		lcModBol.setConexao( cn );
		lcVenda.setConexao( cn );
		lcCli.setConexao( cn );
		lcBanco.setConexao( cn );
		lcTipoCob.setConexao( cn );
		lcCartCob.setConexao( con );
		sInfoMoeda = getMoeda();
		
		
		getAtualizaParcela();
	}
}
