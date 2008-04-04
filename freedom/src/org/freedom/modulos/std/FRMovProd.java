/**
 * @version 08/12/2000 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 * 
 * Projeto: Freeedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)FRListaProd.java <BR>
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
 * Relatório de produtos.
 * 
 */

package org.freedom.modulos.std;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.SwingConstants;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FRelatorio;

public class FRMovProd extends FRelatorio {

	private static final long serialVersionUID = 1L;
	
	private JTextFieldPad txtDataini = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDatafim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDe = new JTextFieldPad( JTextFieldPad.TP_STRING, 30, 0 );

	private JTextFieldPad txtA = new JTextFieldPad( JTextFieldPad.TP_STRING, 30, 0 );

	private JTextFieldPad txtCodForn = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescForn = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtCodGrupo = new JTextFieldPad( JTextFieldPad.TP_STRING, 14, 0 );

	private JTextFieldFK txtDescGrupo = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtCodAlmox = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescAlmox = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtCodMarca = new JTextFieldPad( JTextFieldPad.TP_STRING, 6, 0 );

	private JTextFieldFK txtDescMarca = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtSiglaMarca = new JTextFieldPad( JTextFieldPad.TP_STRING, 20, 0 );
	
	private JCheckBoxPad cbAgrupar = new JCheckBoxPad( "Agrupar por fornecedor", "S", "N" );

	private JRadioGroup<String, String> rgOrdem = null;

	private JRadioGroup<String, String> rgAtivoProd = null;

	private JRadioGroup<String, String> rgProduto = null;

	private ListaCampos lcAlmox = new ListaCampos( this );

	private ListaCampos lcCodForn = new ListaCampos( this );

	private ListaCampos lcGrupo = new ListaCampos( this );

	private ListaCampos lcMarca = new ListaCampos( this );

	public FRMovProd() {

		setTitulo( "Relatório de Produtos" );
		setAtribos( 50, 50, 480, 500 );
	
		montaListaCampos();
		montaRadioGrups();
		montaTela();

		cbAgrupar.setVlrString( "N" );

		Calendar periodo = Calendar.getInstance();
		txtDatafim.setVlrDate( periodo.getTime() );
		periodo.set( Calendar.DAY_OF_MONTH, 1 );
		txtDataini.setVlrDate( periodo.getTime() );
	}

	private void montaListaCampos() {
		
		lcAlmox.add( new GuardaCampo( txtCodAlmox, "CodAlmox", "Cód.almox.", ListaCampos.DB_PK, false ) );
		lcAlmox.add( new GuardaCampo( txtDescAlmox, "DescAlmox", "Descrição do almoxarifado", ListaCampos.DB_SI, false ) );
		lcAlmox.montaSql( false, "ALMOX", "EQ" );
		lcAlmox.setReadOnly( true );
		txtCodAlmox.setTabelaExterna( lcAlmox );
		txtCodAlmox.setFK( true );
		txtCodAlmox.setNomeCampo( "CodAlmox" );

		lcCodForn.add( new GuardaCampo( txtCodForn, "CodFor", "Cód.for.", ListaCampos.DB_PK, false ) );
		lcCodForn.add( new GuardaCampo( txtDescForn, "RazFor", "Razão social do fornecedor", ListaCampos.DB_SI, false ) );
		lcCodForn.montaSql( false, "FORNECED", "CP" );
		lcCodForn.setReadOnly( true );
		txtCodForn.setTabelaExterna( lcCodForn );
		txtCodForn.setFK( true );
		txtCodForn.setNomeCampo( "CodFor" );

		lcGrupo.add( new GuardaCampo( txtCodGrupo, "CodGrup", "Cód.grupo", ListaCampos.DB_PK, false ) );
		lcGrupo.add( new GuardaCampo( txtDescGrupo, "DescGrup", "Descrição do grupo", ListaCampos.DB_SI, false ) );
		lcGrupo.montaSql( false, "GRUPO", "EQ" );
		lcGrupo.setReadOnly( true );
		txtCodGrupo.setTabelaExterna( lcGrupo );
		txtCodGrupo.setFK( true );
		txtCodGrupo.setNomeCampo( "CodGrup" );

		lcMarca.add( new GuardaCampo( txtCodMarca, "CodMarca", "Cód.marca", ListaCampos.DB_PK, false ) );
		lcMarca.add( new GuardaCampo( txtDescMarca, "DescMarca", "Descrição da marca", ListaCampos.DB_SI, false ) );
		lcMarca.add( new GuardaCampo( txtSiglaMarca, "SiglaMarca", "Sigla", ListaCampos.DB_SI, false ) );
		txtCodMarca.setTabelaExterna( lcMarca );
		txtCodMarca.setNomeCampo( "CodMarca" );
		txtCodMarca.setFK( true );
		lcMarca.setReadOnly( true );
		lcMarca.montaSql( false, "MARCA", "EQ" );		
	}
	
	private void montaRadioGrups() {
		
		Vector<String> vLabs = new Vector<String>();
		Vector<String> vVals = new Vector<String>();
		
		vLabs.addElement( "Código" );
		vLabs.addElement( "Descrição" );
		vVals.addElement( "C" );
		vVals.addElement( "D" );
		rgOrdem = new JRadioGroup<String, String>( 1, 2, vLabs, vVals );
		rgOrdem.setVlrString( "D" );

		Vector<String> vLabs1 = new Vector<String>();
		Vector<String> vVals1 = new Vector<String>();
		
		vLabs1.addElement( "Ativos" );
		vLabs1.addElement( "Inativos" );
		vLabs1.addElement( "Todos" );
		vVals1.addElement( "A" );
		vVals1.addElement( "N" );
		vVals1.addElement( "T" );
		rgAtivoProd = new JRadioGroup<String, String>( 2, 2, vLabs1, vVals1 );
		rgAtivoProd.setVlrString( "A" );
		
		Vector<String> vLabs2 = new Vector<String>();
		Vector<String> vVals2 = new Vector<String>();

		vLabs2.addElement( "Comercio" );
		vLabs2.addElement( "Serviço" );
		vLabs2.addElement( "Fabricação" );
		vLabs2.addElement( "Mat.Prima" );
		vLabs2.addElement( "Patrimonio" );
		vLabs2.addElement( "Consumo" );
		vLabs2.addElement( "Todos" );
		vVals2.addElement( "P" );
		vVals2.addElement( "S" );
		vVals2.addElement( "F" );
		vVals2.addElement( "M" );
		vVals2.addElement( "O" );
		vVals2.addElement( "C" );
		vVals2.addElement( "T" );

		rgProduto = new JRadioGroup<String, String>( 4, 2, vLabs2, vVals2 );
		rgProduto.setVlrString( "P" );
	}
	
	private void montaTela() {

		JLabelPad lbLinha = new JLabelPad();
		lbLinha.setBorder( BorderFactory.createEtchedBorder() );
		JLabelPad lbLinha2 = new JLabelPad();
		lbLinha2.setBorder( BorderFactory.createEtchedBorder() );
		
		adic( new JLabelPad( "Periodo:" ), 7, 5, 80, 20 );
		adic( lbLinha, 7, 25, 230, 35 );
		adic( new JLabelPad( "De:" ), 17, 32, 25, 20 );
		adic( txtDataini, 42, 32, 80, 20 );
		adic( new JLabelPad( "à", SwingConstants.CENTER ), 122, 32, 25, 20 );
		adic( txtDatafim, 147, 32, 80, 20 );

		adic( new JLabelPad( "Ordenar por:" ), 240, 5, 200, 20 );
		adic( rgOrdem, 240, 25, 200, 35 );
		adic( new JLabelPad( "Filtrar por:" ), 7, 60, 230, 20 );
		adic( rgAtivoProd, 7, 80, 230, 50 );
		adic( new JLabelPad( "Tipo de Produto:" ), 240, 60, 200, 20 );
		adic( rgProduto, 240, 80, 200, 160 );

		adic( new JLabelPad( "Filtro por razão:" ), 7, 130, 230, 20 );
		adic( lbLinha2, 7, 150, 230, 90 );
		adic( new JLabelPad( "de:" ), 17, 160, 30, 20 );
		adic( txtDe, 50, 160, 177, 20 );
		adic( new JLabelPad( "à:" ), 17, 185, 30, 20 );
		adic( txtA, 50, 185, 177, 20 );
		adic( cbAgrupar, 17, 205, 210, 30 );

		adic( new JLabelPad( "Cód.for." ), 7, 250, 300, 20 );
		adic( txtCodForn, 7, 270, 80, 20 );
		adic( new JLabelPad( "Descrição do fornecedor" ), 90, 250, 300, 20 );
		adic( txtDescForn, 90, 270, 350, 20 );
		adic( new JLabelPad( "Cód.almox." ), 7, 290, 250, 20 );
		adic( txtCodAlmox, 7, 310, 80, 20 );
		adic( new JLabelPad( "Descrição do Almoxarifado" ), 90, 290, 250, 20 );
		adic( txtDescAlmox, 90, 310, 350, 20 );
		adic( new JLabelPad( "Cód.grupo" ), 7, 330, 250, 20 );
		adic( txtCodGrupo, 7, 350, 80, 20 );
		adic( new JLabelPad( "Descrição do grupo" ), 90, 330, 250, 20 );
		adic( txtDescGrupo, 90, 350, 350, 20 );
		adic( new JLabelPad( "Cód.marca" ), 7, 370, 250, 20 );
		adic( txtCodMarca, 7, 390, 80, 20 );
		adic( new JLabelPad( "Descrição da Marca" ), 90, 370, 250, 20 );
		adic( txtDescMarca, 90, 390, 350, 20 );

	}

	public String[] getValores() {

		String[] sRetorno = new String[ 11 ];
		
		if ( rgOrdem.getVlrString().compareTo( "C" ) == 0 ) {
			sRetorno[ 0 ] = "1";
		}
		else if ( rgOrdem.getVlrString().compareTo( "D" ) == 0 ) {
			sRetorno[ 0 ] = "2";
		}

		sRetorno[ 1 ] = txtDe.getText();
		sRetorno[ 2 ] = txtA.getText();
		sRetorno[ 3 ] = rgAtivoProd.getVlrString();
		sRetorno[ 4 ] = txtCodForn.getVlrString();
		sRetorno[ 5 ] = txtDescForn.getVlrString();
		sRetorno[ 6 ] = txtCodAlmox.getText();
		sRetorno[ 7 ] = txtDescAlmox.getText();
		sRetorno[ 8 ] = rgProduto.getVlrString();
		sRetorno[ 9 ] = txtCodMarca.getVlrString();
		sRetorno[ 10 ] = txtDescMarca.getText();
		
		return sRetorno;
	}
	
	private void carregaFiltros( String[] valores, StringBuilder where, Vector<String> filtros ) {
		
		if ( valores[ 1 ].trim().length() > 0 ) {
			where.append( " AND DESCPROD>='" + valores[ 1 ] + "'" );
			filtros.add( "PRODUTOS MAIORES QUE " + valores[ 1 ].trim() );
		}
		if ( valores[ 2 ].trim().length() > 0 ) {
			where.append( " AND DESCPROD <= '" + valores[ 2 ] + "'" );
			filtros.add( "PRODUTOS MENORES QUE " + valores[ 2 ].trim() );
		}
		if ( valores[ 3 ].equals( "A" ) ) {
			where.append( " AND ATIVOPROD='S'" );
			filtros.add( "PRODUTOS ATIVOS" );
		}
		else if ( valores[ 3 ].equals( "N" ) ) {
			where.append( " AND ATIVOPROD='N'" );
			filtros.add( "PRODUTOS INATIVOS" );
		}
		else if ( valores[ 3 ].equals( "T" ) ) {
			filtros.add( "PRODUTOS ATIVOS E INATIVOS" );
		}
		if ( valores[ 7 ].length() > 0 ) {
			where.append( " AND CODALMOX = " + valores[ 6 ] );
			filtros.add( "ALMOXARIFADO = " + valores[ 7 ] );
		}
		if ( valores[ 9 ].length() > 0 ) {
			where.append( " AND CODMARCA = '" + valores[ 9 ] + "'" );
			filtros.add( "MARCA: " + valores[ 10 ] );
		}		
		if ( valores[ 8 ].equals( "P" ) ) {
			where.append( " AND TIPOPROD='P'" );
			filtros.add( "TIPO PRODUTOS" );
		}
		else if ( valores[ 8 ].equals( "S" ) ) {
			where.append( " AND TIPOPROD='S'" );
			filtros.add( "TIPO SERVIÇOS" );
		}
		else if ( valores[ 8 ].equals( "F" ) ) {
			where.append( " AND TIPOPROD='F'" );
			filtros.add( "FABRICAÇÃO" );
		}
		else if ( valores[ 8 ].equals( "M" ) ) {
			where.append( " AND TIPOPROD='M'" );
			filtros.add( "MATERIA PRIMA" );
		}
		else if ( valores[ 8 ].equals( "O" ) ) {
			where.append( " AND TIPOPROD='O'" );
			filtros.add( "PATRIMONIO" );
		}
		else if ( valores[ 8 ].equals( "C" ) ) {
			where.append( " AND TIPOPROD='C'" );
			filtros.add( "CONSUMO" );
		}
		else if ( valores[ 8 ].equals( "T" ) ) {
			filtros.add( "TODOS OS TIPOS DE PRODUTOS" );
		}
		if ( cbAgrupar.getVlrString().equals( "N" ) ) {
			if ( !txtCodForn.getVlrString().equals( "" ) ) {
				filtros.add( "Fornecedor: " + txtDescForn.getVlrString() );
			}
		}
		if ( ! ( txtCodGrupo.getVlrString().equals( "" ) ) ) {
			filtros.add( "PRODUTOS DO GRUPO " + txtDescGrupo.getVlrString() );
			where.append( " AND PD.CODGRUP LIKE '" + txtCodGrupo.getVlrString() + "%'" );
		}
	}
	
	private void montaSQL( String[] valores, StringBuilder sql, StringBuilder  where ) {
		
		if ( cbAgrupar.getVlrString().equals( "S" ) ) {// Para agrupamento por fornecedores
			sql.append( "SELECT PD.CODPROD,PD.DESCPROD,PD.CODBARPROD,PD.CODFABPROD,PD.CODUNID,PD.TIPOPROD,PD.CODGRUP,'N',PF.CODFOR, " );
			sql.append( "(SELECT F.RAZFOR FROM CPFORNECED F WHERE F.CODFOR=PF.CODFOR AND F.CODEMP=PF.CODEMP AND F.CODFILIAL=PF.CODFILIAL),PF.REFPRODFOR " );
			sql.append( "FROM EQPRODUTO PD LEFT OUTER JOIN CPPRODFOR PF ON (PD.CODPROD = PF.CODPROD AND PD.CODEMP = PF.CODEMP) " );
			sql.append( "WHERE PD.CODEMP=? AND PD.CODFILIAL=? " );
			sql.append( "AND NOT EXISTS(SELECT * FROM EQMOVPROD MV WHERE MV.CODEMPPD=PD.CODEMP " );
			sql.append( "AND MV.CODFILIALPD=PD.CODFILIAL AND MV.CODPROD=PD.CODPROD) " );
			sql.append( !valores[ 4 ].equals( "" ) ? "AND EXISTS(SELECT * FROM CPPRODFOR PF WHERE PF.CODPROD = PD.CODPROD AND PF.CODEMP = PD.CODEMP AND PF.CODFOR=" + valores[ 4 ] + " )" : "" ); 
			sql.append( where ); 
			sql.append( " UNION " );
			sql.append( "SELECT PD.CODPROD,PD.DESCPROD,PD.CODBARPROD,PD.CODFABPROD,PD.CODUNID,PD.TIPOPROD,PD.CODGRUP,'S',PF.CODFOR, " ); 
			sql.append( "(SELECT F.RAZFOR FROM CPFORNECED F WHERE F.CODFOR=PF.CODFOR AND F.CODEMP=PF.CODEMP AND F.CODFILIAL=PF.CODFILIAL),PF.REFPRODFOR " );
			sql.append( "FROM EQPRODUTO PD LEFT OUTER JOIN CPPRODFOR PF ON (PD.CODPROD = PF.CODPROD AND PD.CODEMP = PF.CODEMP) " + "WHERE PD.CODEMP=? AND PD.CODFILIAL=? " ); 
			sql.append( "AND EXISTS(SELECT * FROM EQMOVPROD MV WHERE MV.CODEMPPD=PD.CODEMP " );
			sql.append( "AND MV.CODFILIALPD=PD.CODFILIAL AND MV.CODPROD=PD.CODPROD) " );
			sql.append( !valores[ 4 ].equals( "" ) ? "AND EXISTS(SELECT * FROM CPPRODFOR PF WHERE PF.CODPROD = PD.CODPROD AND PF.CODEMP = PD.CODEMP AND PF.CODFOR=" + valores[ 4 ] + " )" : "" ); 
			sql.append( where ); 
			sql.append( " ORDER BY 9," );
			sql.append( valores[ 0 ] );
		}
		else {
			sql.append( "SELECT PD.CODPROD,PD.DESCPROD,PD.CODBARPROD,PD.CODFABPROD,PD.CODUNID,PD.TIPOPROD,PD.CODGRUP,'N','' " ); 
			sql.append( "FROM EQPRODUTO PD " ); 
			sql.append( "WHERE PD.CODEMP=? AND PD.CODFILIAL=? " );
			sql.append( "AND NOT EXISTS(SELECT * FROM EQMOVPROD MV WHERE MV.CODEMPPD=PD.CODEMP " );
			sql.append( "AND MV.CODFILIALPD=PD.CODFILIAL AND MV.CODPROD=PD.CODPROD) " );
			sql.append( !valores[ 4 ].equals( "" ) ? "AND EXISTS(SELECT * FROM CPPRODFOR PF WHERE PF.CODPROD = PD.CODPROD AND PF.CODEMP = PD.CODEMP AND PF.CODFOR=" + valores[ 4 ] + " )" : "" ); 
			sql.append( where );
			sql.append( " UNION " );
			sql.append( "SELECT PD.CODPROD,PD.DESCPROD,PD.CODBARPROD,PD.CODFABPROD,PD.CODUNID,PD.TIPOPROD,PD.CODGRUP,'S','' " ); 
			sql.append( "FROM EQPRODUTO PD " ); 
			sql.append( "WHERE PD.CODEMP=? AND PD.CODFILIAL=? " ); 
			sql.append( "AND EXISTS(SELECT * FROM EQMOVPROD MV WHERE MV.CODEMPPD=PD.CODEMP " );
			sql.append( "AND MV.CODFILIALPD=PD.CODFILIAL AND MV.CODPROD=PD.CODPROD) " );
			sql.append( !valores[ 4 ].equals( "" ) ? "AND EXISTS(SELECT * FROM CPPRODFOR PF WHERE PF.CODPROD=PD.CODPROD AND PF.CODEMP=PD.CODEMP AND PF.CODFOR=" + valores[ 4 ] + " )" : "" );
			sql.append( where );
			sql.append( " ORDER BY " );
			sql.append( valores[ 0 ] );
		}
	}

	public void imprimir( boolean bVisualizar ) {

		ImprimeOS imp = new ImprimeOS( "", con );
		String[] valores = getValores();
		StringBuilder sql = new StringBuilder();
		StringBuilder  where = new StringBuilder();
		Vector<String> filtros = new Vector<String>();
		String sTipo = "";
		int linPag = imp.verifLinPag() - 1;
		int iContaReg = 0;

		try {

			carregaFiltros( valores, where, filtros );
			montaSQL( valores, sql, where );
			
			PreparedStatement ps = con.prepareStatement( sql.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "PDPRODUTO" ) );
			ps.setInt( 3, Aplicativo.iCodEmp );
			ps.setInt( 4, ListaCampos.getMasterFilial( "PDPRODUTO" ) );
			
			ResultSet rs = ps.executeQuery();
			
			imp.limpaPags();
			imp.setTitulo( "Relatório de Produtos" );
			imp.montaCab();
			
			boolean bImpNulo = true;
			boolean bPulouPagina = false;
			String sCodFor = "";

			while ( rs.next() ) {
				if ( imp.pRow() == 0 ) {
					imp.impCab( 136, true );
					imp.say( imp.pRow() + 0, 0, "" + imp.comprimido() );
					imp.say( imp.pRow() + 0, 2, "|" + Funcoes.replicate( " ", 60 ) + "Filtrado por:" + Funcoes.replicate( " ", 60 ) + "|" );
					for ( int i = 0; i < filtros.size(); i++ ) {
						String sTmp = filtros.elementAt( i );
						sTmp = "|" + Funcoes.replicate( " ", ( ( ( 134 - sTmp.length() ) / 2 ) - 1 ) ) + sTmp;
						sTmp += Funcoes.replicate( " ", 134 - sTmp.length() ) + "|";
						imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
						imp.say( imp.pRow() + 0, 2, sTmp );
					}

					imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
					imp.say( imp.pRow() + 0, 0, "|" + Funcoes.replicate( "-", 133 ) + "|" );
					imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
					imp.say( imp.pRow() + 0, 0, "|" );
					imp.say( imp.pRow() + 0, 3, "Código:" );
					imp.say( imp.pRow() + 0, 12, "|" );
					imp.say( imp.pRow() + 0, 13, " Cod.Barra:" );
					imp.say( imp.pRow() + 0, 27, "|" );
					imp.say( imp.pRow() + 0, 29, "Cod.Fab:" );
					imp.say( imp.pRow() + 0, 41, "|" );
					imp.say( imp.pRow() + 0, 43, "Descrição:" );
					imp.say( imp.pRow() + 0, 74, "|" );
					imp.say( imp.pRow() + 0, 76, "Unidade:" );
					imp.say( imp.pRow() + 0, 85, "|" );
					imp.say( imp.pRow() + 0, 86, " Mov." );
					imp.say( imp.pRow() + 0, 92, "|     Cod.Grupo" );
					imp.say( imp.pRow() + 0, 114, "|      Tipo" );
					imp.say( imp.pRow() + 0, 135, "|" );
					imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
					imp.say( imp.pRow() + 0, 0, "|" + Funcoes.replicate( "-", 133 ) + "|" );

				}
				if ( ( cbAgrupar.getVlrString().equals( "S" ) ) && ( !sCodFor.equals( rs.getString( 9 ) ) ) && bImpNulo || ( ( bPulouPagina ) && ( cbAgrupar.getVlrString().equals( "S" ) ) ) ) {
					if ( iContaReg > 0 && !bPulouPagina ) {
						imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
						imp.say( imp.pRow() + 0, 0, "|" + Funcoes.replicate( "-", 133 ) + "|" );
					}
					if ( ! ( rs.getString( 10 ) == null ) ) {
						imp.say( imp.pRow() + 1, 0, "|" );
						imp.say( imp.pRow() + 0, 3, rs.getString( 10 ) );
						imp.say( imp.pRow() + 0, 135, "|" );
						imp.say( imp.pRow() + 1, 0, "" );
						imp.say( imp.pRow() + 0, 0, "|" + Funcoes.replicate( "-", 133 ) + "|" );
					}
					else {
						imp.say( imp.pRow() + 1, 0, "|  FORNECEDOR NÃO INFORMADO" );
						imp.say( imp.pRow() + 0, 135, "|" );
						imp.say( imp.pRow() + 1, 0, "|" );
						imp.say( imp.pRow() + 0, 0, Funcoes.replicate( "-", 133 ) + "|" );
						bImpNulo = false;
					}
					bPulouPagina = false;
				}

				imp.say( imp.pRow() + 1, 0, "" );
				imp.say( imp.pRow() + 0, 0, "|" );
				imp.say( imp.pRow() + 0, 3, rs.getString( "CodProd" ) );
				imp.say( imp.pRow() + 0, 12, "|" );
				imp.say( imp.pRow() + 0, 13, rs.getString( "codBarProd" ) );
				imp.say( imp.pRow() + 0, 27, "|" );

				if ( cbAgrupar.getVlrString().equals( "N" ) ) {
					imp.say( imp.pRow() + 0, 29, rs.getString( "CodFabProd" ) != null ? Funcoes.copy( rs.getString( "CodFabProd" ), 12 ) : "" );
				}

				else {
					imp.say( imp.pRow() + 0, 29, rs.getString( "RefProdFor" ) != null ? Funcoes.copy( rs.getString( "RefProdFor" ), 12 ) : "" );
				}
				imp.say( imp.pRow() + 0, 41, "|" );
				imp.say( imp.pRow() + 0, 42, rs.getString( "DescProd" ) != null ? rs.getString( "Descprod" ).substring( 0, 30 ) : "" );
				imp.say( imp.pRow() + 0, 74, "|" );
				imp.say( imp.pRow() + 0, 76, rs.getString( "Codunid" ) );
				imp.say( imp.pRow() + 0, 85, "|" );
				imp.say( imp.pRow() + 0, 89, rs.getString( 8 ) );
				imp.say( imp.pRow() + 0, 92, "|" );
				imp.say( imp.pRow() + 0, 99, rs.getString( "codgrup" ) );
				imp.say( imp.pRow() + 0, 114, "|" );

				if ( valores[ 8 ].equals( "T" ) ) {

					if ( rs.getString( "TIPOPROD" ).equals( "P" ) ) {
						sTipo = "Comercio";
						imp.say( imp.pRow() + 0, 119, sTipo );
					}
					else if ( rs.getString( "TIPOPROD" ).equals( "S" ) ) {
						sTipo = "Serviço";
						imp.say( imp.pRow() + 0, 119, sTipo );
					}
					else if ( rs.getString( "TIPOPROD" ).equals( "F" ) ) {
						sTipo = "Fabricação";
						imp.say( imp.pRow() + 0, 119, sTipo );
					}
					else if ( rs.getString( "TIPOPROD" ).equals( "M" ) ) {
						sTipo = "Mat.Prima";
						imp.say( imp.pRow() + 0, 119, sTipo );
					}
					else if ( rs.getString( "TIPOPROD" ).equals( "O" ) ) {
						sTipo = "Patrimônio";
						imp.say( imp.pRow() + 0, 119, sTipo );
					}
					else if ( rs.getString( "TIPOPROD" ).equals( "C" ) ) {
						sTipo = "Consumo";
						imp.say( imp.pRow() + 0, 119, sTipo );
					}
				}

				imp.say( imp.pRow() + 0, 135, "|" );

				sCodFor = rs.getString( 9 ) == null ? "" : rs.getString( 9 );

				if ( imp.pRow() >= linPag ) {
					imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
					imp.say( imp.pRow() + 0, 0, "+" + Funcoes.replicate( "-", 133 ) + "+" );
					imp.incPags();
					bPulouPagina = true;
					imp.eject();
				}
				iContaReg++;
			}
			imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
			imp.say( imp.pRow() + 0, 0, "+" + Funcoes.replicate( "=", 133 ) + "+" );
			imp.eject();

			imp.fechaGravacao();

			if ( !con.getAutoCommit() ) {
				con.commit();
			}
		} catch ( Exception err ) {
			err.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao monta relátorio!\n" + err.getMessage(), true, con, err );
		}
		
		if ( bVisualizar ) {
			imp.preview( this );
		}
		else {
			imp.print();
		}
	}

	public void setConexao( Connection cn ) {
	
		super.setConexao( cn );
		lcAlmox.setConexao( cn );
		lcCodForn.setConexao( cn );
		lcGrupo.setConexao( cn );
		lcMarca.setConexao( cn );
	}
}
