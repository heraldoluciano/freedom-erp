/**
 * @version 04/2007 <BR>
 * @author Setpoint Informática Ltda.<BR>
 * @author Alex Rodrigues<BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.rep <BR>
 * Classe:
 * @(#)RelPedido.java <BR>
 * 
 * Este programa é licenciado de acordo com a LPG-PC (Licença Pública Geral para Programas de Computador), <BR>
 * versão 2.1.0 ou qualquer versão posterior. <BR>
 * A LPG-PC deve acompanhar todas PUBLICAÇÕES, DISTRIBUIÇÕES e REPRODUÇÕES deste Programa. <BR>
 * Caso uma cópia da LPG-PC não esteja disponível junto com este Programa, você pode contatar <BR>
 * o LICENCIADOR ou então pegar uma cópia em: <BR>
 * Licença: http://www.lpg.adv.br/licencas/lpgpc.rtf <BR>
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa é preciso estar <BR>
 * de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 * Relatorio de pedidos, em dois modos: completo e resumido.
 * 
 */

package org.freedom.modulos.rep;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import net.sf.jasperreports.engine.JasperPrintManager;

import org.freedom.acao.RadioGroupEvent;
import org.freedom.acao.RadioGroupListener;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.modulos.rep.RPPrefereGeral.EPrefere;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FPrinterJob;
import org.freedom.telas.FRelatorio;

public class RelPedido extends FRelatorio implements RadioGroupListener {

	private static final long serialVersionUID = 1;

	private final JTextFieldPad txtCodCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private final JTextFieldFK txtRazCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private final JTextFieldPad txtCodFor = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private final JTextFieldFK txtRazFor = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private final JTextFieldPad txtCodVend = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private final JTextFieldFK txtNomeVend = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private final JTextFieldPad txtCodMoeda = new JTextFieldPad( JTextFieldPad.TP_STRING, 4, 0 );

	private final JTextFieldFK txtNomeMoeda = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	private final JTextFieldPad txtDtIni = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );
	
	private final JTextFieldPad txtDtFim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );
	
	private JRadioGroup rgModo;
	
	private JRadioGroup rgOrdem1;
	
	private JRadioGroup rgOrdem2;
	
	private final ListaCampos lcCliente = new ListaCampos( this );
	
	private final ListaCampos lcFornecedor = new ListaCampos( this );
	
	private final ListaCampos lcVendedor = new ListaCampos( this );
	
	private final ListaCampos lcMoeda = new ListaCampos( this );
	
	private List<Object> prefere = new ArrayList<Object>();

	public RelPedido() {

		super();
		setTitulo( "Relatorio de Pedidos" );		
		setAtribos( 100, 50, 325, 430 );
		
		montaRadioGrupos();
		montaListaCampos();
		montaTela();
		
		Calendar cal = Calendar.getInstance();			
		txtDtFim.setVlrDate( cal.getTime() );		
		cal.set( cal.get( Calendar.YEAR ), cal.get( Calendar.MONTH ) - 1, cal.get( Calendar.DATE ) );
		txtDtIni.setVlrDate( cal.getTime() );	
		
		rgModo.addRadioGroupListener( this );
	}
	
	private void montaRadioGrupos() {
		
		Vector<String> labs = new Vector<String>();
		labs.add( "completo" );
		labs.add( "resumido" );
		Vector<String> vals = new Vector<String>();
		vals.add( "C" );
		vals.add( "R" );
		rgModo = new JRadioGroup( 1, 2, labs, vals );
		
		Vector<String> labs1 = new Vector<String>();
		labs1.add( "Item" );
		labs1.add( "Descrição" );
		Vector<String> vals1 = new Vector<String>();
		vals1.add( "IT.CODITPED" );
		vals1.add( "P.DESCPROD" );
		rgOrdem1 = new JRadioGroup( 1, 2, labs1, vals1 );
		
		Vector<String> labs2 = new Vector<String>();
		labs2.add( "Pedido" );
		labs2.add( "Cliente" );
		Vector<String> vals2 = new Vector<String>();
		vals2.add( "P.CODPED" );
		vals2.add( "C.RAZCLI" );
		rgOrdem2 = new JRadioGroup( 1, 2, labs2, vals2 );
	}
	
	private void montaListaCampos() {
		
		/***********
		 * CLIENTE *
		 ***********/
		
		lcCliente.add( new GuardaCampo( txtCodCli, "CodCli", "Cód.cli.", ListaCampos.DB_PK, false ) );
		lcCliente.add( new GuardaCampo( txtRazCli, "RazCli", "Razão social do cliente", ListaCampos.DB_SI, false ) );
		lcCliente.montaSql( false, "CLIENTE", "RP" );
		lcCliente.setQueryCommit( false );
		lcCliente.setReadOnly( true );
		txtCodCli.setListaCampos( lcCliente );
		txtCodCli.setTabelaExterna( lcCliente );
		txtCodCli.setPK( true );
		txtCodCli.setNomeCampo( "CodCli" );
		
		/**************
		 * FORNECEDOR *
		 **************/
		
		lcFornecedor.add( new GuardaCampo( txtCodFor, "CodFor", "Cód.for.", ListaCampos.DB_PK, false ) );
		lcFornecedor.add( new GuardaCampo( txtRazFor, "RazFor", "Razão social do fornecedor", ListaCampos.DB_SI, false ) );
		lcFornecedor.montaSql( false, "FORNECEDOR", "RP" );
		lcFornecedor.setQueryCommit( false );
		lcFornecedor.setReadOnly( true );
		txtCodFor.setListaCampos( lcFornecedor );
		txtCodFor.setTabelaExterna( lcFornecedor );
		txtCodFor.setPK( true );
		txtCodFor.setNomeCampo( "CodFor" );
		
		/************
		 * VENDEDOR *
		 ************/
		
		lcVendedor.add( new GuardaCampo( txtCodVend, "CodVend", "Cód.vend.", ListaCampos.DB_PK, false ) );
		lcVendedor.add( new GuardaCampo( txtNomeVend, "NomeVend", "Nome do vendedor", ListaCampos.DB_SI, false ) );
		lcVendedor.montaSql( false, "VENDEDOR", "RP" );
		lcVendedor.setQueryCommit( false );
		lcVendedor.setReadOnly( true );
		txtCodVend.setListaCampos( lcVendedor );
		txtCodVend.setTabelaExterna( lcVendedor );
		txtCodVend.setPK( true );
		txtCodVend.setNomeCampo( "CodVend" );
		
		/*********
		 * MOEDA *
		 *********/
		
		lcMoeda.add( new GuardaCampo( txtCodMoeda, "CodMoeda", "Cód.moeda", ListaCampos.DB_PK, true ) );
		lcMoeda.add( new GuardaCampo( txtNomeMoeda, "SingMoeda", "Descrição da moeda", ListaCampos.DB_SI, false ) );
		lcMoeda.montaSql( false, "MOEDA", "RP" );
		lcMoeda.setQueryCommit( false );
		lcMoeda.setReadOnly( true );
		txtCodMoeda.setListaCampos( lcMoeda );
		txtCodMoeda.setTabelaExterna( lcMoeda );
		txtCodMoeda.setPK( true );
		txtCodMoeda.setNomeCampo( "CodMoeda" );
	}
	
	private void montaTela() {
		
		adic( new JLabel( "Modo :" ), 10, 10, 200, 20 );
		adic( rgModo, 10, 35, 290, 30 );
		adic( new JLabel( "Ordem do relatorio :" ), 10, 70, 200, 20 );
		adic( rgOrdem1, 10, 95, 290, 30 );
		adic( rgOrdem2, 10, 95, 290, 30 );
		
		rgOrdem2.setVisible( false );
		
		JLabel periodo = new JLabel( "Periodo", SwingConstants.CENTER );
		periodo.setOpaque( true );
		adic( periodo, 25, 130, 60, 20 );
		
		JLabel borda = new JLabel();
		borda.setBorder( BorderFactory.createEtchedBorder() );
		adic( borda, 10, 140, 290, 45 );
		
		adic( txtDtIni, 25, 155, 110, 20 );
		adic( new JLabel( "até", SwingConstants.CENTER ), 135, 155, 40, 20 );
		adic( txtDtFim, 175, 155, 110, 20 );
		
		adic( new JLabel( "Cód.for." ), 10, 190, 77, 20 );
		adic( txtCodFor, 10, 210, 77, 20 );
		adic( new JLabel( "Razão social do fornecedor" ), 90, 190, 210, 20 );
		adic( txtRazFor, 90, 210, 210, 20 );
		
		adic( new JLabel( "Cód.vend." ), 10, 230, 77, 20 );
		adic( txtCodVend, 10, 250, 77, 20 );
		adic( new JLabel( "Nome do vendedor" ), 90, 230, 210, 20 );
		adic( txtNomeVend, 90, 250, 210, 20 );
		
		adic( new JLabel( "Cód.cli." ), 10, 270, 77, 20 );
		adic( txtCodCli, 10, 290, 77, 20 );
		adic( new JLabel( "Razão social do cliente" ), 90, 270, 210, 20 );
		adic( txtRazCli, 90, 290, 210, 20 );
		
		adic( new JLabel( "Cód.moeda" ), 10, 310, 77, 20 );
		adic( txtCodMoeda, 10, 330, 77, 20 );
		adic( new JLabel( "Descrição da moeda" ), 90, 310, 210, 20 );
		adic( txtNomeMoeda, 90, 330, 210, 20 );
	}

	@ Override
	public void imprimir( boolean visualizar ) {
		
		if ( txtCodMoeda.getVlrString().trim().length() < 1 ) {
			Funcoes.mensagemInforma( this, "O campo \"Cód.moeda\" é requerido!" );
			return;
		}
		
		if ( txtDtIni.getVlrDate() != null && txtDtFim.getVlrDate() != null ) {
			if ( txtDtFim.getVlrDate().before( txtDtIni.getVlrDate() ) ) {
				Funcoes.mensagemInforma( this, "Data final inferior a inicial!" );
				return;
			}
		}

		try {
			
			String relatorio = "C".equals( rgModo.getVlrString() ) ? "rppedidocomp.jasper" : "rppedidoresum.jasper";
			String modo = "C".equals( rgModo.getVlrString() ) ? "( completo )" : " ( resumido )";
			String nomevend = null;
			String moeda = null;
			String razcli = null;
			String razfor = null;
			Date dtini = txtDtIni.getVlrDate();
			Date dtfim = txtDtFim.getVlrDate();
			
			StringBuilder sql = new StringBuilder();
			
			sql.append( "SELECT IT.CODPED,P.DATAPED,P.CODCLI,C.RAZCLI,P.CODVEND,V.NOMEVEND,P.CODPLANOPAG,PG.DESCPLANOPAG, " );
			sql.append( "P.CODMOEDA,M.SINGMOEDA,P.CODFOR,F.RAZFOR,P.CODTRAN, " );
			sql.append( "(SELECT T.RAZTRAN FROM RPTRANSP T WHERE T.CODEMP=P.CODEMPTP AND T.CODFILIAL=P.CODFILIALTP AND T.CODTRAN=P.CODTRAN) AS RAZTRAN, " );
			sql.append( "P.TIPOFRETEPED,P.TIPOREMPED,P.NUMPEDCLI,P.NUMPEDFOR,P.VLRTOTPED, " );
			sql.append( "P.QTDTOTPED,P.VLRLIQPED,P.VLRIPIPED,P.VLRDESCPED,P.VLRADICPED,P.VLRRECPED, " );
			sql.append( "P.VLRPAGPED,P.OBSPED,IT.CODITPED,IT.CODPROD,PD.DESCPROD,IT.CODFOR,FI.RAZFOR, " );
			sql.append( "IT.QTDITPED,IT.PRECOITPED,IT.VLRITPED,IT.VLRLIQITPED,IT.PERCIPIITPED, " );
			sql.append( "IT.VLRIPIITPED,IT.PERCDESCITPED,IT.VLRDESCITPED,IT.PERCADICITPED, " );
			sql.append( "IT.VLRADICITPED,IT.PERCRECITPED,IT.VLRRECITPED,IT.PERCPAGITPED,IT.VLRPAGITPED, " );
			sql.append( "C.ENDCLI,C.CIDCLI,C.ESTCLI,C.CEPCLI,C.BAIRCLI,C.DDDCLI,C.FONECLI,C.FAXCLI,C.EMAILCLI,C.CNPJCLI,C.INSCCLI " );
			sql.append( "FROM RPPEDIDO P, RPITPEDIDO IT, RPPRODUTO PD, RPFORNECEDOR FI, " );
			sql.append( "RPCLIENTE C,RPVENDEDOR V, RPPLANOPAG PG, RPMOEDA M, RPFORNECEDOR F " );
			sql.append( "WHERE IT.CODEMP=? AND IT.CODFILIAL=? " );
			sql.append( "AND P.DATAPED BETWEEN ? AND ? " );
			sql.append( "AND P.CODEMP=IT.CODEMP AND P.CODFILIAL=IT.CODFILIAL AND P.CODPED=IT.CODPED " );
			sql.append( "AND C.CODEMP=P.CODEMPCL AND C.CODFILIAL=P.CODFILIALCL AND C.CODCLI=P.CODCLI " );
			sql.append( "AND V.CODEMP=P.CODEMPVD AND V.CODFILIAL=P.CODFILIALVD AND V.CODVEND=P.CODVEND " );
			sql.append( "AND PG.CODEMP=P.CODEMPPG AND PG.CODFILIAL=P.CODFILIALPG AND PG.CODPLANOPAG=P.CODPLANOPAG " );
			sql.append( "AND M.CODEMP=P.CODEMPMO AND M.CODFILIAL=P.CODFILIALMO AND M.CODMOEDA=P.CODMOEDA " );
			sql.append( "AND F.CODEMP=P.CODEMPFO AND F.CODFILIAL=P.CODFILIALFO AND F.CODFOR=P.CODFOR " );
			sql.append( "AND PD.CODEMP=IT.CODEMPPD AND PD.CODFILIAL=IT.CODFILIALPD AND PD.CODPROD=IT.CODPROD " );
			sql.append( "AND FI.CODEMP=IT.CODEMPFO AND FI.CODFILIAL=IT.CODFILIALFO AND FI.CODFOR=IT.CODFOR " );
			
			if ( txtCodMoeda.getVlrString().trim().length() > 0 ) {
				sql.append( "AND M.CODMOEDA='" + txtCodMoeda.getVlrString() + "'" );
				moeda = txtNomeMoeda.getVlrString();
			}
			if ( txtCodCli.getVlrString().trim().length() > 0 ) {
				sql.append( "AND C.CODCLI=" + txtCodCli.getVlrInteger() );
				razcli = txtRazCli.getVlrString();
			}
			if ( txtCodFor.getVlrString().trim().length() > 0 ) {
				sql.append( "AND F.CODFOR=" + txtCodFor.getVlrInteger() );
				razfor = txtRazFor.getVlrString();
			}
			if ( txtCodVend.getVlrString().trim().length() > 0 ) {
				sql.append( "AND V.CODVEND=" + txtCodVend.getVlrInteger().intValue() );
				nomevend = txtNomeVend.getVlrString();
			}

			if ( "C".equals( rgModo.getVlrString() ) ) {
			
				sql.append( " ORDER BY P.CODPED, " + rgOrdem1.getVlrString() );
			}
			else if ( "R".equals( rgModo.getVlrString() ) ) {
			
				sql.append( " ORDER BY " + rgOrdem2.getVlrString() );
			}
			
			PreparedStatement ps = con.prepareStatement( sql.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "RPITPEDIDO" ) );
			ps.setDate( 3, Funcoes.dateToSQLDate( dtini ) );
			ps.setDate( 4, Funcoes.dateToSQLDate( dtfim ) );
			ResultSet rs = ps.executeQuery();
			
			HashMap<String,Object> hParam = new HashMap<String, Object>();

			hParam.put( "CODEMP", Aplicativo.iCodEmp );
			hParam.put( "SUBREPORT_DIR", "/opt/freedom/reports/" );
			hParam.put( "REPORT_CONNECTION", con );
			hParam.put( "DTINI", dtini );
			hParam.put( "DTFIM", dtfim );
			hParam.put( "NOMEVEND", nomevend );
			hParam.put( "MOEDA", moeda );
			hParam.put( "RAZFOR", razfor );
			hParam.put( "RAZCLI", razcli );
			
			FPrinterJob dlGr = new FPrinterJob( "modulos/rep/relatorios/"+relatorio, "PEDIDOS" + modo, null, rs, hParam, this );

			if ( visualizar ) {
				dlGr.setVisible( true );
			}
			else {
				JasperPrintManager.printReport( dlGr.getRelatorio(), true );
			}
			
		} catch ( Exception e ) {
			Funcoes.mensagemErro( this, "Erro ao montar relatorio!\n" + e.getMessage() );
			e.printStackTrace();
		}

	}

	public void valorAlterado( RadioGroupEvent e ) {

		if ( "C".equals( rgModo.getVlrString() ) ) {
			
			rgOrdem1.setVisible( true );
			rgOrdem2.setVisible( false );
		}
		else if ( "R".equals( rgModo.getVlrString() ) ) {
			
			rgOrdem1.setVisible( false );
			rgOrdem2.setVisible( true );
		}
	}

	public void setConexao( Connection cn ) {

		super.setConexao( cn );

		lcCliente.setConexao( cn );
		lcFornecedor.setConexao( cn );
		lcVendedor.setConexao( cn );
		lcMoeda.setConexao( cn );
		
		prefere = RPPrefereGeral.getPrefere( cn );
		
		txtCodMoeda.setVlrString( (String) prefere.get( EPrefere.CODMOEDA.ordinal() ) );
		lcMoeda.carregaDados();
	}

}
