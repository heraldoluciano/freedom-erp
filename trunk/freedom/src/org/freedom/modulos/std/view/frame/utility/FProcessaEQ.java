/**
 * @version 11/11/2004 <BR>
 * @author Setpoint Informática Ltda./Robson Sanchez e Fernando Oliveira da Silva <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.std <BR>
 *         Classe: @(#)FProcessaSL.java <BR>
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
 *         Efetua somatórias nos lançamentos e insere saldos.
 * 
 */

package org.freedom.modulos.std.view.frame.utility;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.freedom.infra.functions.StringFunctions;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.business.component.ProcessoSec;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JButtonPad;
import org.freedom.library.swing.component.JCheckBoxPad;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JPanelPad;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.dialog.FFDialogo;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.modulos.std.view.dialog.utility.DLBuscaProd;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.JOptionPane;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.acao.Processo;
import org.freedom.bmps.Icone;

public class FProcessaEQ extends FFDialogo implements ActionListener, CarregaListener {

	private static final long serialVersionUID = 1L;

	private JPanelPad pin = new JPanelPad();

	private JButtonPad btProcessar = new JButtonPad( "Executar agora!", Icone.novo( "btExecuta.png" ) );

	private JTextFieldPad txtDataini = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtCodProd = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtRefProd = new JTextFieldFK( JTextFieldPad.TP_STRING, 20, 0 );

	private JTextFieldFK txtDescProd = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JCheckBoxPad cbTudo = new JCheckBoxPad( "Processar todo estoque (Atenção!)", "S", "N" );

	private JCheckBoxPad cbAtivo = new JCheckBoxPad( "Processar somente produtos ativos", "S", "N" );

	private JLabelPad lbStatus = new JLabelPad();

	private ListaCampos lcProd = new ListaCampos( this );

	private enum paramCons {
		NONE, CODEMPIV, CODPRODIV, CODEMPCP, CODPRODCP, CODEMPOP, CODPRODOP, CODEMPOPSP, CODPRODOPSP, CODEMPRM, CODPRODRM, CODEMPVD, CODPRODVD
	}

	  /*ciud char(1),
	    icodemppd integer,
	    scodfilialpd smallint,
	    codprod integer,
	    icodemple integer,
	    scodfilialle smallint,
	    ccodlote varchar(20),
	    icodemptm integer,
	    scodfilialtm smallint,
	    icodtipomov integer,
	    
	    
	    icodempiv integer,
	    scodfilialiv smallint,
	    icodinvprod integer,
	    
	    
	    icodempcp integer,
	    scodfilialcp smallint,
	    icodcompra integer,
	    scoditcompra smallint,
	    icodempvd integer,
	    scodfilialvd smallint,
	    ctipovenda char(1),
	    icodvenda integer,
	    scoditvenda smallint,
	    
	    icodemprm integer,
	    scodfilialrm smallint,
	    icodrma integer,
	    scoditrma smallint,
	    
	    icodempop integer,
	    scodfilialop smallint,
	    icodop integer,
	    sseqop smallint,
	    sseqentop smallint,
	    icodempnt integer,
	    scodfilialnt smallint,
	    ccodnat char(4),
	    ddtmovprod date,
	    idocmovprod integer,
	    cflag char(1),
	    nqtdmovprod numeric(15,5),
	    nprecomovprod numeric(15,5),
	    icodempax integer,
	    scodfilialax smallint,
	    icodalmox integer,
	    seqsubprod smallint,
	    estoqtipomovpd char(1))*/
	
	
	private enum paramProc {
		NONE, IUD, CODEMPPD, CODFILIALPD, CODPROD, CODEMPLE, CODFILIALLE, CODLOTE, CODEMPTM, CODFILIALTM
		, CODTIPOMOV, CODEMPIV, CODFILIALIV, CODINVPROD, CODEMPCP, CODFILIALCP, CODCOMPRA, CODITCOMPRA
		, CODEMPVD, CODFILIALVD, TIPOVENDA, CODVENDA, CODITVENDA, CODEMPRM, CODFILIALRM, CODRMA, CODITRMA
		, CODEMPOP, CODFILIALOP, CODOP, SEQOP, SEQENT, CODEMPNT, CODFILIALNT, CODNAT, DTMOVPROD, DOCMOVPROD
		, FLAG, QTDMOVPROD, PRECOMOVPROD, CODEMPAX, CODFILIALAX, CODALMOX, SEQSUBPROD, ESTOQTIPOMOVPD, TIPONF
	}

	boolean bRunProcesso = false;
	
	HashMap<String, Object> prefs = null;

	int iFilialMov = 0;

	int iUltProd = 0;

	public FProcessaEQ() {

		setTitulo( "Processamento de estoque" );
		setAtribos( 100, 100, 330, 430 );

		Container c = getContentPane();
		c.setLayout( new BorderLayout() );
		c.add( pin, BorderLayout.CENTER );

		lcProd.add( new GuardaCampo( txtCodProd, "CodProd", "Cód.prod", ListaCampos.DB_PK, true ) );
		lcProd.add( new GuardaCampo( txtRefProd, "RefProd", "Referência", ListaCampos.DB_SI, false ) );
		lcProd.add( new GuardaCampo( txtDescProd, "DescProd", "Descrição do produto", ListaCampos.DB_SI, false ) );
		txtCodProd.setTabelaExterna( lcProd, null );
		txtCodProd.setNomeCampo( "CodProd" );
		txtCodProd.setFK( true );
		lcProd.setReadOnly( true );
		lcProd.montaSql( false, "PRODUTO", "EQ" );

		JLabelPad lbAviso = new JLabelPad();
		lbAviso.setForeground( Color.RED );
		lbAviso.setText( "<HTML> ATENÇÃO! <BR><BR>" + "Assegure-se que apenas esta estação de trabalho<BR>" + "esteja conectada ao sistema.</HTML>" );

		pin.adic( lbAviso, 10, 0, 460, 150 );

		pin.adic( new JLabelPad( "Apartir de:" ), 7, 160, 70, 20 );
		pin.adic( txtDataini, 80, 160, 107, 20 );
		pin.adic( new JLabelPad( "Cód.prod." ), 7, 180, 250, 20 );
		pin.adic( txtCodProd, 7, 200, 70, 20 );
		pin.adic( new JLabelPad( "Descrição do produto" ), 80, 180, 250, 20 );
		pin.adic( txtDescProd, 80, 200, 220, 20 );

		if ( Aplicativo.getUsuario().getIdusu().toUpperCase().equals( "SYSDBA" ) ) {
			cbAtivo.setVlrString( "S" );
			pin.adic( cbTudo, 7, 240, 250, 30 );
			pin.adic( cbAtivo, 7, 270, 250, 30 );
			txtCodProd.setRequerido( false );
		}
		pin.adic( btProcessar, 10, 310, 180, 30 );

		lbStatus.setForeground( Color.BLUE );

		pin.adic( lbStatus, 10, 350, 400, 20 );

		adicBotaoSair();

		lcProd.addCarregaListener( this );
		btProcessar.addActionListener( this );
		
	//	prefs = getPrefere( con );
		
		
		state( "Aguardando..." );
	}

	private HashMap<String, Object> getPrefere( DbConnection con ) {

		HashMap<String, Object> retorno = new HashMap<String, Object>();
		boolean[] bRetorno = new boolean[ 1 ];
		StringBuffer sql = new StringBuffer();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {

			sql.append( "SELECT coalesce(prodetapas,'S') prodetapas from SGPREFERE5 P5 " );
			sql.append( "WHERE P5.CODEMP=? AND P5.CODFILIAL=?" );

			bRetorno[ 0 ] = false;
			ps = con.prepareStatement( sql.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );			
			ps.setInt( 2, ListaCampos.getMasterFilial( "SGPREFERE5" ) );

			rs = ps.executeQuery();

			if ( rs.next() ) {
				retorno.put( "PRODETAPAS", new Boolean( rs.getString( "prodetapas" ).trim().equals( "S" ) ) );
			}
			else {
				retorno.put( "PRODETAPAS", new Boolean( false ) );
			}

			rs.close();
			ps.close();

			con.commit();

		} catch ( SQLException err ) {
			err.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao carregar a tabela PREFERE1!\n" + err.getMessage(), true, con, err );
		} finally {
			ps = null;
			rs = null;
			sql = null;
		}
		return retorno;
	}
	
	private void processarTudo() {

		String sSQL = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Vector<Integer> vProds = null;
		
		if ( iUltProd > 0 ) {
			if ( Funcoes.mensagemConfirma( null, "Gostaria de continuar a partir do produto '" + iUltProd + "'?" ) != JOptionPane.YES_OPTION )
				iUltProd = 0;
		}
		try {
			sSQL = "SELECT CODPROD FROM EQPRODUTO WHERE " 
			   + ( cbAtivo.getVlrString().equals( "S" ) ? "ATIVOPROD='S' AND" : "" ) 
			   + " CODEMP=? AND CODPROD>=?" 
			   + " ORDER BY CODPROD";
			
			ps = con.prepareStatement( sSQL );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, iUltProd );
			rs = ps.executeQuery();
			vProds = new Vector<Integer>();
			
			while ( rs.next() ) {
				vProds.addElement( new Integer( rs.getInt( "CodProd" ) ) );
			}
			
			rs.close();
			ps.close();
			con.commit();

			for ( int i = 0; i < vProds.size(); i++ ) {
				iUltProd = vProds.elementAt( i ).intValue();
				if ( !processar( iUltProd ) ) {
					if ( Funcoes.mensagemConfirma( null, "Ocorreram problemas com o produto: '" + iUltProd + "'.\n" + "Deseja continuar mesmo assim?" ) != JOptionPane.YES_OPTION )
						break;
				}
			}
		} catch ( SQLException err ) {
			err.printStackTrace();
			Funcoes.mensagemErro( null, "Não foi possível processar um produto.\n" + "Ultimo processado: '" + iUltProd + "'.\n" + err.getMessage(), true, con, err );
		} finally {
			sSQL = null;
		}
	}

	private void completaTela() {

		txtCodProd.setBuscaAdic( new DLBuscaProd( con, "CODPROD", lcProd.getWhereAdic() ) );
	}

	private boolean processar( int codprod ) {

		StringBuilder sql = new StringBuilder();
		StringBuilder sqlcompra = new StringBuilder();
		StringBuilder sqlinventario = new StringBuilder();
		StringBuilder sqlvenda = new StringBuilder();
		StringBuilder sqlrma = new StringBuilder();
		StringBuilder sqlop = new StringBuilder();
		StringBuilder sqlop_sp = new StringBuilder();
		StringBuilder where = new StringBuilder();
		String prod = null;
		StringBuilder wherecompra = new StringBuilder();
		StringBuilder whereinventario = new StringBuilder();
		StringBuilder wherevenda = new StringBuilder();
		StringBuilder whererma = new StringBuilder();
		StringBuilder whereop = new StringBuilder();
		StringBuilder whereop_sp = new StringBuilder();
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean bOK = false;
		boolean zeraestoq = false;
		int param = 1;

		try {
			try {
				prod = "";
				if ( cbTudo.getVlrString().equals( "S" ) )
					prod = "[" + codprod + "] ";
				if ( ( txtDataini.getVlrString().equals( "" ) ) ) {
					zeraestoq = true;
				} else {
					where.append(" and dtmovprod >= '");
					where.append(Funcoes.dateToStrDB( txtDataini.getVlrDate() ) );
					where.append("'");
				}
				state( prod + "Limpando movimentações desatualizadas..." );
				if (zeraestoq) {
					sql.delete( 0, sql.length() );
					sql.append( "update eqmovprod set emmanut='S' " );
					sql.append( "where codemp=? and codprod=?" );
					ps = con.prepareStatement( sql.toString() );
					param = 1;
					ps.setInt( param++, Aplicativo.iCodEmp );
					ps.setInt( param++, codprod );
					ps.executeUpdate();
					ps.close();
				}
				sql.delete( 0, sql.length() );
				sql.append( "delete from eqmovprod " );
				sql.append( "where codemp=? and codprod=?" );
				sql.append( where );
				ps = con.prepareStatement( sql.toString() );
				param = 1;
				ps.setInt( param++, Aplicativo.iCodEmp );
				ps.setInt( param++, codprod );
				ps.executeUpdate();
				ps.close();

				if ( zeraestoq ) {
					state( prod + "Limpando saldos..." );
					sql.delete( 0, sql.length() );
					sql.append( "update eqproduto set sldprod=0, sldresprod=0, sldconsigprod=0, sldliqprod=0 " );
					sql.append( "where codemp=? and codprod=?" );
					ps = con.prepareStatement( sql.toString() );
					param = 1;
					ps.setInt( param++, Aplicativo.iCodEmp );
					ps.setInt( param++, codprod );
					ps.executeUpdate();
					ps.close();
					sql.delete( 0, sql.length() );
					sql.append( "update eqlote set sldlote=0, sldreslote=0, sldconsiglote=0, sldliqlote=0 " );
					sql.append( "where codemp=? and codprod=?" );
					ps = con.prepareStatement( sql.toString() );
					param = 1;
					ps.setInt( param++, Aplicativo.iCodEmp );
					ps.setInt( param++, codprod );
					ps.executeUpdate();
					ps.close();
					sql.delete( 0, sql.length() );
					sql.append( "delete from eqsaldoprod " );
					sql.append( "where codemp=? and codprod=?" );
					ps = con.prepareStatement( sql.toString() );
					param = 1;
					ps.setInt( param++, Aplicativo.iCodEmp );
					ps.setInt( param++, codprod );
					ps.executeUpdate();
					ps.close();
					sql.delete( 0, sql.length() );
					sql.append( "delete from eqsaldolote " );
					sql.append( "where codemp=? and codprod=?" );
					ps = con.prepareStatement( sql.toString() );
					param = 1;
					ps.setInt( param++, Aplicativo.iCodEmp );
					ps.setInt( param++, codprod );
					ps.executeUpdate();
					ps.close();
				}
				bOK = true;
			} catch ( SQLException err ) {
				Funcoes.mensagemErro( null, "Erro ao limpar estoques!\n" + err.getMessage(), true, con, err );
			}
			if ( bOK ) {
				bOK = false;
				if ( !txtDataini.getVlrString().equals( "" ) ) {
					wherecompra.append( " and c.dtentcompra >= '").append( Funcoes.dateToStrDB( txtDataini.getVlrDate() ) ).append( "'" );
					whereinventario.append(" and i.datainvp >= '").append( Funcoes.dateToStrDB( txtDataini.getVlrDate() ) ).append( "'" );
					wherevenda.append(" and v.dtemitvenda >= '").append( Funcoes.dateToStrDB( txtDataini.getVlrDate() ) ).append( "'" );
					whererma.append(" and rma.dtaexprma >= '" ).append( Funcoes.dateToStrDB( txtDataini.getVlrDate() ) ).append("'" );
					whereop.append(" and o.dtfabrop >= '" ).append( Funcoes.dateToStrDB( txtDataini.getVlrDate() ) ).append( "'" );
					whereop_sp.append(" and o.dtsubprod >= '" ).append(Funcoes.dateToStrDB( txtDataini.getVlrDate() ) ).append("'");
				}
				else {
					wherecompra.delete( 0, wherecompra.length() );
					whereinventario.delete( 0, whereinventario.length() );
					wherevenda.delete( 0, wherevenda.length() );
					whererma.delete( 0, whererma.length() );
					whereop.delete( 0, whereop.length() );
					whereop_sp.delete( 0, whereop_sp.length() );
				}

				sqlinventario.delete( 0, sqlinventario.length() );
				sqlinventario.append( "select 'A' tipoproc, i.codemppd, i.codfilialpd, i.codprod," ) 
						.append( "i.codemple, i.codfilialle, i.codlote," ) 
						.append( "i.codemptm, i.codfilialtm, i.codtipomov," ) 
						.append( "i.codemp, i.codfilial, cast(null as char(1)) tipovenda, " ) 
						.append( "i.codinvprod codmaster, i.codinvprod coditem, " )
						.append( "cast(null as integer) codempnt, cast(null as smallint) codfilialnt ,cast(null as char(4)) codnat," ) 
						.append( "i.datainvp dtproc, i.codinvprod docproc,'N' flag," ) 
						.append( "i.qtdinvp qtdproc, i.precoinvp custoproc, " ) 
						.append( "i.codempax, i.codfilialax, i.codalmox, cast(null as smallint) as seqent, cast(null as smallint) as seqsubprod  " )
						.append(", 'S' estoqtipomovpd, 'N' tiponf " )
						.append("from eqinvprod i " )
						.append( "where i.codemp=? and i.codprod = ?")
						.append( whereinventario );

				sqlcompra.delete( 0, sqlcompra.length() );
				sqlcompra.append( "select 'C' tipoproc, ic.codemppd, ic.codfilialpd, ic.codprod," )
						.append( "ic.codemple, ic.codfilialle, ic.codlote,"  )
						.append( "c.codemptm, c.codfilialtm, c.codtipomov,"  )
						.append( "c.codemp, c.codfilial, cast(null as char(1)) tipovenda, " ) 
						.append( "c.codcompra codmaster, ic.coditcompra coditem," )
						.append( "ic.codempnt, ic.codfilialnt, ic.codnat, "  )
						.append( "c.dtentcompra dtproc, c.doccompra docproc, c.flag," ) 
						.append( "ic.qtditcompra qtdproc, ic.custoitcompra custoproc, " ) 
						.append( "ic.codempax, ic.codfilialax, ic.codalmox, cast(null as smallint) as seqent, cast(null as smallint) as seqsubprod " )
						.append( ", 'S' estoqtipomovpd, ic.tiponfitcompra tiponf " )
						.append( "from cpcompra c,cpitcompra ic " )
						.append( "where ic.codcompra=c.codcompra and " ) 
						.append( "ic.codemp=c.codemp and ic.codfilial=c.codfilial and ic.qtditcompra > 0 and " ) 
						.append( "c.codemp=? and ic.codprod = ?" ) 
						.append( wherecompra  );

				sqlop.delete( 0, sqlop.length() );
				sqlop.append( "select 'O' tipoproc, o.codemppd, o.codfilialpd, o.codprod," ) 
						.append( "o.codemple, o.codfilialle, o.codlote," )
						.append( "o.codemptm, o.codfilialtm, o.codtipomov," ) 
						.append( "o.codemp, o.codfilial, cast(null as char(1)) tipovenda ," ) 
						.append( "o.codop codmaster, cast(o.seqop as integer) coditem," )
						.append( "cast(null as integer) codempnt, cast(null as smallint) codfilialnt, " ) 
						.append( "cast(null as char(4)) codnat, "  )
						.append( "coalesce(oe.dtent,o.dtfabrop) dtproc, " ) 
						.append( "o.codop docproc, 'N' flag, " )
						.append( "( case when coalesce(o.sitop,'')='CA' then 0 else coalesce(oe.qtdent,o.qtdfinalprodop) end) qtdproc, " ) 
						.append( "( select " )
						.append( "cast(cast(sum( cast((select cast(ncustompm as decimal(15,5)) " )
						.append( "from eqprodutosp01(it.codemppd,it.codfilialpd,it.codprod,null,null,null, coalesce(oe.dtent,o.dtfabrop))) as decimal(15,5)) * it.qtditop ) " )
						.append( "as decimal(15,5)) / o.qtdfinalprodop as decimal(15,5)) " )
						.append( " from ppitop it, eqproduto pd " )
						.append( "where it.codemp=o.codemp and it.codfilial=o.codfilial and " ) 
						.append( "it.codop=o.codop and it.seqop=o.seqop and " )
						.append( "pd.codemp=it.codemppd and pd.codfilial=it.codfilialpd and " ) 
						.append( "pd.codprod=it.codprod) custoproc, " )
						.append( "o.codempax, o.codfilialax, o.codalmox, oe.seqent, cast(null as smallint) as seqsubprod " )
						.append( ", 'S' estoqtipomovpd, 'N' tiponf " )
						.append( "from ppop o " )
						.append( " left outer join ppopentrada oe on oe.codemp=o.codemp and oe.codfilial=o.codfilial and oe.codop=o.codop and oe.seqop=o.seqop " ) 
						.append( "where o.qtdfinalprodop > 0 and " )
						.append( "o.codemp=? and o.codprod = ? " )
						.append( whereop );
				
				sqlop_sp.delete( 0, sqlop_sp.length() );
				sqlop_sp.append( "select 'S' tipoproc, o.codemppd, o.codfilialpd, o.codprod," ) 
					.append( "o.codemple, o.codfilialle, o.codlote," )
					.append( "o.codemptm, o.codfilialtm, o.codtipomov," ) 
					.append( "o.codemp, o.codfilial, cast(null as char(1)) tipovenda ," ) 
					.append( "o.codop codmaster, cast(o.seqop as integer) coditem," )
					.append( "cast(null as integer) codempnt, cast(null as smallint) codfilialnt, " ) 
					.append( "cast(null as char(4)) codnat, " )
					.append( "coalesce(o.dtsubprod,op.dtfabrop) dtproc, " ) 
					.append( "o.codop docproc, 'N' flag, " )
					.append( "o.qtditsp qtdproc, " )
					.append( "( select pd.custompmprod from eqproduto pd " )
					.append( "where pd.codemp=o.codemppd and pd.codfilial=o.codfilialpd and " ) 
					.append( "pd.codprod=o.codprod) custoproc, " )
					.append( "op.codempax, op.codfilialax, op.codalmox, cast(null as smallint) as seqent, o.seqsubprod " )
					.append( ", 'S' estoqtipomovpd, 'N' tiponf " )
					.append( "from ppopsubprod o, ppop op " )
					.append( "where o.qtditsp > 0 and " )
					.append( "o.codemp=op.codemp and o.codfilial=op.codfilial and o.codop=op.codop and o.seqop=op.seqop and " )
					.append( "o.codemp=? and o.codprod = ?" )
					.append( whereop_sp );
						
				sqlrma.delete( 0, sqlrma.length() );
				sqlrma.append( "select 'R' tipoproc, it.codemppd, it.codfilialpd, it.codprod, " ) 
					.append( "it.codemple, it.codfilialle, it.codlote, "  )
					.append( "rma.codemptm, rma.codfilialtm, rma.codtipomov, " ) 
					.append( "rma.codemp, rma.codfilial, cast(null as char(1)) tipovenda, " )
					.append( "it.codrma codmaster, cast(it.coditrma as integer) coditem, " ) 
					.append( "cast(null as integer) codempnt, cast(null as smallint) codfilialnt, " ) 
					.append( "cast(null as char(4)) codnat, " )
					.append( "coalesce(it.dtaexpitrma,rma.dtareqrma) dtproc, " ) 
					.append( "rma.codrma docproc, 'N' flag, " )
					.append( "it.qtdexpitrma qtdproc, it.precoitrma custoproc," )  
					.append( "it.codempax, it.codfilialax, it.codalmox, cast(null as smallint) as seqent, cast(null as smallint) as seqsubprod   " )
					.append( ", 'S' estoqtipomovpd, 'N' tiponf " )
					.append( "from eqrma rma ,eqitrma it " )
					.append( "where it.codrma=rma.codrma and " ) 
					.append( "it.codemp=rma.codemp and it.codfilial=rma.codfilial and " ) 
					.append( "it.qtditrma > 0 and " )
					.append( "rma.codemp=? and it.codprod = ?" )
					.append( whererma );

				sqlvenda.delete( 0, sqlvenda.length() );
				sqlvenda.append( "select 'V' tipoproc, iv.codemppd, iv.codfilialpd, iv.codprod," ) 
						.append( "iv.codemple, iv.codfilialle, iv.codlote," )
						.append( "v.codemptm, v.codfilialtm, v.codtipomov," ) 
						.append( "v.codemp, v.codfilial, v.tipovenda, " ) 
						.append( "v.codvenda codmaster, iv.coditvenda coditem, " )
						.append( "iv.codempnt, iv.codfilialnt, iv.codnat, " ) 
						.append( "v.dtemitvenda dtproc, v.docvenda docproc, v.flag, " ) 
						.append( "iv.qtditvenda qtdproc, iv.vlrliqitvenda custoproc, " ) 
						.append( "iv.codempax, iv.codfilialax, iv.codalmox, cast(null as smallint) as seqent, cast(null as smallint) as seqsubprod " )
						.append( ", case when v.subtipovenda='NC' then 'N' else 'S' end estoqtipomovpd, 'N' tiponf " )
						.append( "from vdvenda v ,vditvenda iv " )
						.append( "where iv.codvenda=v.codvenda and iv.tipovenda = v.tipovenda and " ) 
						.append( "iv.codemp=v.codemp and iv.codfilial=v.codfilial and " ) 
						.append( "iv.qtditvenda > 0 and " ) 
						.append( "v.codemp=? and iv.codprod = ?" ) 
						.append( wherevenda );

				try {
					state( prod + "Iniciando reconstrução..." );
					sql.delete( 0, sql.length() );
					sql.append(  sqlinventario )
					.append(" union all ")
					.append( sqlcompra )
					.append(" union all " )
					.append( sqlop  )
					.append( " union all " )
					.append( sqlop_sp )
					.append(" union all " )
					.append( sqlrma )
					.append(" union all " )
					.append( sqlvenda )
					.append( " order by 19,1,20" );// 1 por que c-compra,i-inventario,v-venda,r-rma
					
					System.out.println(sql.toString());
					
					ps = con.prepareStatement( sql.toString() );
					
					ps.setInt( paramCons.CODEMPIV.ordinal(), Aplicativo.iCodEmp );
					ps.setInt( paramCons.CODPRODIV.ordinal(), codprod );

					ps.setInt( paramCons.CODEMPCP.ordinal(), Aplicativo.iCodEmp );
					ps.setInt( paramCons.CODPRODCP.ordinal(), codprod );

					ps.setInt( paramCons.CODEMPOP.ordinal(), Aplicativo.iCodEmp );
					ps.setInt( paramCons.CODPRODOP.ordinal(), codprod );

					ps.setInt( paramCons.CODEMPOPSP.ordinal(), Aplicativo.iCodEmp );
					ps.setInt( paramCons.CODPRODOPSP.ordinal(), codprod );
					
					ps.setInt( paramCons.CODEMPRM.ordinal(), Aplicativo.iCodEmp );
					ps.setInt( paramCons.CODPRODRM.ordinal(), codprod );
					
					ps.setInt( paramCons.CODEMPVD.ordinal(), Aplicativo.iCodEmp );
					ps.setInt( paramCons.CODPRODVD.ordinal(), codprod );
					
					rs = ps.executeQuery();
					bOK = true;
					
					while ( rs.next() && bOK ) {
						bOK = insereMov( rs, prod );
					}
					
					rs.close();
					ps.close();
					state( prod + "Aguardando gravação final..." );
					
				} 
				catch ( SQLException err ) {
					bOK = false;
					err.printStackTrace();
					Funcoes.mensagemErro( null, "Erro ao reconstruir base!\n" + err.getMessage(), true, con, err );
				}
			}
			try {
				if ( bOK ) {
					con.commit();
					state( prod + "Registros processados com sucesso!" );
				}
				else {
					state( prod + "Registros antigos restaurados!" );
					con.rollback();
				}
			} catch ( SQLException err ) {
				err.printStackTrace();
				Funcoes.mensagemErro( null, "Erro ao realizar procedimento!\n" + err.getMessage(), true, con, err );
			}

		} finally {
			sql = null;
			sqlcompra = null;
			sqlinventario = null;
			sqlvenda = null;
			sqlrma = null;
			where = null;
			prod = null;
			wherecompra = null;
			whereinventario = null;
			wherevenda = null;
			whererma = null;
			rs = null;
			ps = null;
			bRunProcesso = false;
			btProcessar.setEnabled( true );
		}
		return bOK;
	}

	private boolean insereMov( ResultSet rs, String sProd ) {

		/*
		 * Parâmetros da procedure de reconstrução da tabela EQMOVPROD CIUD CHAR(1), ICODEMPPD INTEGER, SCODFILIALPD SMALLINT, codprod INTEGER, ICODEMPLE INTEGER, SCODFILIALLE SMALLINT, CCODLOTE CHAR(13), ICODEMPTM INTEGER, SCODFILIALTM SMALLINT, ICODTIPOMOV INTEGER, 11 ICODEMPIV INTEGER,
		 * SCODFILIALIV SMALLINT, ICODINVPROD INTEGER, 14 ICODEMPCP INTEGER, SCODFILIALCP SMALLINT, ICODCOMPRA INTEGER, SCODITCOMPRA SMALLINT, 18 ICODEMPVD INTEGER, SCODFILIALVD SMALLINT, CTIPOVENDA CHAR(1), ICODVENDA INTEGER, SCODITVENDA SMALLINT, 23 ICODEMPRM INTEGER, SCODFILIALRM SMALLINT,
		 * ICODRMA INTEGER, SCODITRMA SMALLINT, 27 ICODEMPNT INTEGER, SCODFILIALNT SMALLINT, CCODNAT CHAR(4), 30 DDTMOVPROD DATE, IDOCMOVPROD INTEGER, CFLAG CHAR(1), NQTDMOVPROD NUMERIC(15,3), NPRECOMOVPROD NUMERIC(15,3))
		 */
		boolean bRet = false;
		String sSQL = null;
		String sCIV = null;
		PreparedStatement ps = null;
		double dePrecoMovprod = 0;
		try {
			sSQL = "EXECUTE PROCEDURE EQMOVPRODIUDSP(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?," + 
			"?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			
			state( sProd + "Processando dia: " + StringFunctions.sqlDateToStrDate( rs.getDate( 19 ) ) + " Doc: [" + rs.getInt( 20 ) + "]" );
			
			ps = con.prepareStatement( sSQL );
			sCIV = rs.getString( "TIPOPROC" ); // tipo COMPRA, INVENTARIO, VENDA
			
			ps.setString( paramProc.IUD.ordinal(), "I" );
			ps.setInt( paramProc.CODEMPPD.ordinal(), rs.getInt( "CODEMPPD" ) ); // CodEmpPD
			ps.setInt( paramProc.CODFILIALPD.ordinal(), rs.getInt( "CODFILIALPD" ) ); // CodFilialPD
			ps.setInt( paramProc.CODPROD.ordinal(), rs.getInt( "CODPROD" ) ); // CodProd
			
			if ( rs.getString( "CODLOTE" ) != null ) {
				ps.setInt( paramProc.CODEMPLE.ordinal(), rs.getInt( "CODEMPLE" ) ); // CodEmpLE
				ps.setInt( paramProc.CODFILIALLE.ordinal(), rs.getInt( "CODFILIALLE" ) ); // CodFilialLE
				ps.setString( paramProc.CODLOTE.ordinal(), rs.getString( "CODLOTE" ) ); // CodLote
			}
			else {
				ps.setNull( paramProc.CODEMPLE.ordinal(), Types.INTEGER ); // CodEmpLE
				ps.setNull( paramProc.CODFILIALLE.ordinal(), Types.INTEGER ); // CodFilialLE
				ps.setNull( paramProc.CODLOTE.ordinal(), Types.CHAR ); // CodLote
			}
			
			ps.setInt( paramProc.CODEMPTM.ordinal(), rs.getInt( "CODEMPTM" ) ); // CodEmpTM
			ps.setInt( paramProc.CODFILIALTM.ordinal(), rs.getInt( "CODFILIALTM" ) ); // CodFilialTM
			ps.setInt( paramProc.CODTIPOMOV.ordinal(), rs.getInt( "CODTIPOMOV" ) ); // CodTipoMov
			
			if ( sCIV.equals( "A" ) ) {
				ps.setInt( paramProc.CODEMPIV.ordinal(), rs.getInt( "CODEMP" ) ); // CodEmpIv
				ps.setInt( paramProc.CODFILIALIV.ordinal(), rs.getInt( "CODFILIAL" ) ); // CodFilialIv
				ps.setInt( paramProc.CODINVPROD.ordinal(), rs.getInt( "CODMASTER" ) ); // CodInvProd
			}
			else {
				ps.setNull( paramProc.CODEMPIV.ordinal(), Types.INTEGER ); // CodEmpIv
				ps.setNull( paramProc.CODFILIALIV.ordinal(), Types.INTEGER ); // CodFilialIv
				ps.setNull( paramProc.CODINVPROD.ordinal(), Types.INTEGER ); // CodInvProd
			}
			if ( sCIV.equals( "C" ) ) {
				ps.setInt( paramProc.CODEMPCP.ordinal(), rs.getInt( "CODEMP" ) ); // CodEmpCp
				ps.setInt( paramProc.CODFILIALCP.ordinal(), rs.getInt( "CODFILIAL" ) ); // CodFilialCp
				ps.setInt( paramProc.CODCOMPRA.ordinal(), rs.getInt( "CODMASTER" ) ); // CodCompra
				ps.setInt( paramProc.CODITCOMPRA.ordinal(), rs.getInt( "CODITEM" ) ); // CodItCompra
			}
			else {
				ps.setNull( paramProc.CODEMPCP.ordinal(), Types.INTEGER ); // CodEmpCp
				ps.setNull( paramProc.CODFILIALCP.ordinal(), Types.INTEGER ); // CodFilialCp
				ps.setNull( paramProc.CODCOMPRA.ordinal(), Types.INTEGER ); // CodCompra
				ps.setNull( paramProc.CODITCOMPRA.ordinal(), Types.INTEGER ); // CodItCompra
			}
			
			if ( sCIV.equals( "V" ) ) {
				ps.setInt( paramProc.CODEMPVD.ordinal(), rs.getInt( "CODEMP" ) ); // CodEmpVd
				ps.setInt( paramProc.CODFILIALVD.ordinal(), rs.getInt( "CODFILIAL" ) ); // CodFilialVd
				ps.setString( paramProc.TIPOVENDA.ordinal(), rs.getString( "TIPOVENDA" ) ); // TipoVenda
				ps.setInt( paramProc.CODVENDA.ordinal(), rs.getInt( "CODMASTER" ) ); // CodVenda
				ps.setInt( paramProc.CODITVENDA.ordinal(), rs.getInt( "CODITEM" ) ); // CodItVenda
			}
			else {
				ps.setNull( paramProc.CODEMPVD.ordinal(), Types.INTEGER ); // CodEmpVd
				ps.setNull( paramProc.CODFILIALVD.ordinal(), Types.INTEGER ); // CodFilialVd
				ps.setNull( paramProc.TIPOVENDA.ordinal(), Types.CHAR ); // TipoVenda
				ps.setNull( paramProc.CODVENDA.ordinal(), Types.INTEGER ); // CodVenda
				ps.setNull( paramProc.CODITVENDA.ordinal(), Types.INTEGER ); // CodItVenda
			}
			
			if ( sCIV.equals( "R" ) ) {
				ps.setInt( paramProc.CODEMPRM.ordinal(), rs.getInt( "CODEMP" ) ); // CodEmpRm
				ps.setInt( paramProc.CODFILIALRM.ordinal(), rs.getInt( "CODFILIAL" ) ); // CodFilialRm
				ps.setInt( paramProc.CODRMA.ordinal(), rs.getInt( "CODMASTER" ) ); // CodRma
				ps.setInt( paramProc.CODITRMA.ordinal(), rs.getInt( "CODITEM" ) ); // CodItRma
			}
			else {
				ps.setNull( paramProc.CODEMPRM.ordinal(), Types.INTEGER ); // CodEmpRm
				ps.setNull( paramProc.CODFILIALRM.ordinal(), Types.INTEGER ); // CodFilialRm
				ps.setNull( paramProc.CODRMA.ordinal(), Types.INTEGER ); // CodRma
				ps.setNull( paramProc.CODITRMA.ordinal(), Types.INTEGER ); // CodItRma
			}

			if ( sCIV.equals( "O" ) ) {
				ps.setInt( paramProc.CODEMPOP.ordinal(), rs.getInt( "CODEMP" ) ); // CodEmpOp
				ps.setInt( paramProc.CODFILIALOP.ordinal(), rs.getInt( "CODFILIAL" ) ); // CodFilialOp
				ps.setInt( paramProc.CODOP.ordinal(), rs.getInt( "CODMASTER" ) ); // CodOp
				ps.setInt( paramProc.SEQOP.ordinal(), rs.getInt( "CODITEM" ) ); // SeqOp
				ps.setInt( paramProc.SEQENT.ordinal(), rs.getInt( "SEQENT" ) ); // SeqEnt
				ps.setNull( paramProc.SEQSUBPROD.ordinal(), Types.INTEGER ); // SeqSubProd
			}
			else {
				ps.setNull( paramProc.CODEMPOP.ordinal(), Types.INTEGER ); // CodEmpOP
				ps.setNull( paramProc.CODFILIALOP.ordinal(), Types.INTEGER ); // CodFilialOP
				ps.setNull( paramProc.CODOP.ordinal(), Types.INTEGER ); // CodOP
				ps.setNull( paramProc.SEQOP.ordinal(), Types.INTEGER ); // SeqOp
				ps.setNull( paramProc.SEQENT.ordinal(), Types.INTEGER ); // SeqEnt
				ps.setNull( paramProc.SEQSUBPROD.ordinal(), Types.INTEGER ); // SeqSubProd
			}
			
			if ( sCIV.equals( "S" ) ) {
				ps.setInt( paramProc.CODEMPOP.ordinal(), rs.getInt( "CODEMP" ) ); // CodEmpOp
				ps.setInt( paramProc.CODFILIALOP.ordinal(), rs.getInt( "CODFILIAL" ) ); // CodFilialOp
				ps.setInt( paramProc.CODOP.ordinal(), rs.getInt( "CODMASTER" ) ); // CodOp
				ps.setInt( paramProc.SEQOP.ordinal(), rs.getInt( "CODITEM" ) ); // SeqOp
				ps.setInt( paramProc.SEQENT.ordinal(), rs.getInt( "SEQENT" ) ); // SeqEnt
				ps.setInt( paramProc.SEQSUBPROD.ordinal(), rs.getInt( "SEQSUBPROD" ) ); // SeqSubProd
			}
			else {
				ps.setNull( paramProc.CODEMPOP.ordinal(), Types.INTEGER ); // CodEmpOP
				ps.setNull( paramProc.CODFILIALOP.ordinal(), Types.INTEGER ); // CodFilialOP
				ps.setNull( paramProc.CODOP.ordinal(), Types.INTEGER ); // CodOP
				ps.setNull( paramProc.SEQOP.ordinal(), Types.INTEGER ); // SeqOp
				ps.setNull( paramProc.SEQENT.ordinal(), Types.INTEGER ); // SeqEnt  
				ps.setNull( paramProc.SEQENT.ordinal(), Types.INTEGER ); // SeqSubProd
			}

			if ( rs.getString( 18 ) != null ) {
				ps.setInt( paramProc.CODEMPNT.ordinal(), rs.getInt( "CODEMPNT" ) ); // CodEmpNt
				ps.setInt( paramProc.CODFILIALNT.ordinal(), rs.getInt( "CODFILIALNT" ) ); // CodFilialNt
				ps.setString( paramProc.CODNAT.ordinal(), rs.getString( "CODNAT" ) ); // CodNat
			}
			else {
				ps.setNull( paramProc.CODEMPNT.ordinal(), Types.INTEGER ); // CodEmpNt
				ps.setNull( paramProc.CODFILIALNT.ordinal(), Types.INTEGER ); // CodFilialNt
				ps.setNull( paramProc.CODNAT.ordinal(), Types.CHAR ); // CodNat
			}

			ps.setDate( paramProc.DTMOVPROD.ordinal(), rs.getDate( "DTPROC" ) ); // dtMovProd
			ps.setInt( paramProc.DOCMOVPROD.ordinal(), rs.getInt( "DOCPROC" ) ); // docMovProd
			ps.setString( paramProc.FLAG.ordinal(), rs.getString( "FLAG" ) ); // Flag
			ps.setDouble( paramProc.QTDMOVPROD.ordinal(), rs.getDouble( "QTDPROC" ) ); // QtdMovProd
			if ( sCIV.equals( "V" ) ) {
				if ( rs.getDouble( "QTDPROC" ) > 0 )
					dePrecoMovprod = rs.getDouble( "CUSTOPROC" ) / rs.getDouble( "QTDPROC" );
				else
					dePrecoMovprod = 0;
			}
			else {
				dePrecoMovprod = rs.getDouble( "CUSTOPROC" );
			}
			ps.setDouble( paramProc.PRECOMOVPROD.ordinal(), dePrecoMovprod ); // PrecoMovProd
			ps.setDouble( paramProc.CODEMPAX.ordinal(), rs.getInt( "CODEMPAX" ) ); // Codempax
			ps.setDouble( paramProc.CODFILIALAX.ordinal(), rs.getInt( "CODFILIALAX" ) ); // Codfilialax

			ps.setDouble( paramProc.CODALMOX.ordinal(), rs.getInt( "CODALMOX" ) ); // Codalmox
			ps.setString( paramProc.ESTOQTIPOMOVPD.ordinal(), rs.getString( "ESTOQTIPOMOVPD" ) );
			ps.setString( paramProc.TIPONF.ordinal(), rs.getString( "TIPONF" ) );

			ps.executeUpdate();
			ps.close();
			bRet = true;
		} catch ( SQLException err ) {
			Funcoes.mensagemErro( null, "Erro ao inserir novo movimento!\n" + err.getMessage(), true, con, err );
			// err.printStackTrace();
		} catch ( Exception err ) {
			Funcoes.mensagemErro( null, "Erro ao inserir novo movimento!\n" + err.getMessage(), true, con, err );
		} finally {
			sSQL = null;
			sCIV = null;
			ps = null;
		}
		return bRet;
	}

	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == btProcessar ) {
			if ( cbTudo.getVlrString().equals( "S" ) ) {
				if ( Funcoes.mensagemConfirma( null, "ATENÇÃO!!!\n" + "Esta operação exige um longo tempo e muitos recursos do banco de dados,\n" + "assegure-se que NINGUÉM esteja conectado ao banco de dados em outra \n" + "estação de trabalho. Deseja continuar?" ) != JOptionPane.YES_OPTION )
					return;
			}
			else if ( txtCodProd.getVlrString().equals( "" ) ) {
				Funcoes.mensagemInforma( null, "Código do produto em branco!" );
				return;
			}
			ProcessoSec pSec = new ProcessoSec( 100, new Processo() {

				public void run() {

					lbStatus.updateUI();
				}
			}, new Processo() {

				public void run() {

					if ( cbTudo.getVlrString().equals( "S" ) )
						processarTudo();
					else {
						iUltProd = txtCodProd.getVlrInteger().intValue();
						processar( iUltProd );
					}
				}
			} );
			bRunProcesso = true;
			btProcessar.setEnabled( false );
			pSec.iniciar();
		}
	}

	public void state( String sStatus ) {

		lbStatus.setText( sStatus );
		// System.out.println(sStatus);
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		lcProd.setConexao( cn );
		iFilialMov = ListaCampos.getMasterFilial( "EQMOVPROD" );
		completaTela();

	}

	public void beforeCarrega( CarregaEvent cevt ) {

	}

	public void afterCarrega( CarregaEvent cevt ) {

		if ( cevt.ok && cevt.getListaCampos() == lcProd )
			iUltProd = txtCodProd.getVlrInteger().intValue();
	}
}
