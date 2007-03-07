/**
 * @version 02/2007 <BR>
 * @author Setpoint Informática Ltda.<BR>
 * @author Alex Rodrigues<BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.rep <BR>
 * Classe:
 * @(#)RPProduto.java <BR>
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
 * Tela para cadastro de produtos.
 * 
 */

package org.freedom.modulos.rep;

import java.awt.event.ActionListener;
import java.sql.Connection;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FDados;

public class RPProduto extends FDados implements ActionListener {

	private static final long serialVersionUID = 1L;

	private final JTextFieldPad txtCodProd = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private final JTextFieldPad txtRefProd = new JTextFieldPad( JTextFieldPad.TP_STRING, 13, 0 );

	private final JTextFieldPad txtDescProd = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private final JTextFieldPad txtCodBarra = new JTextFieldPad( JTextFieldPad.TP_STRING, 13, 0 );

	private final JTextFieldPad txtCodGrupo = new JTextFieldPad( JTextFieldPad.TP_STRING, 14, 0 );

	private final JTextFieldPad txtCodUnid = new JTextFieldPad( JTextFieldPad.TP_STRING, 8, 0 );

	private final JTextFieldPad txtCodFor = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private final JTextFieldPad txtRefProdFor = new JTextFieldPad( JTextFieldPad.TP_STRING, 13, 0 );

	private final JTextFieldPad txtPesoLiq = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 10, Aplicativo.casasDec );

	private final JTextFieldPad txtPesoBruto = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 10, Aplicativo.casasDec );

	private final JTextFieldPad txtEmbalagem = new JTextFieldPad( JTextFieldPad.TP_STRING, 20, 0 );

	private final JTextFieldPad txtPercIPI = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 6, Aplicativo.casasDec );

	private final JTextFieldPad txtComiss = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 6, Aplicativo.casasDec );
	
	private final JTextFieldPad txtCubagem = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 6, Aplicativo.casasDec );
	
	private final JTextFieldFK txtDescGrupo = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	private final JTextFieldPad txtSgGrupo = new JTextFieldPad( JTextFieldPad.TP_STRING, 10, 0 );
	
	private final JTextFieldFK txtDescUnid = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	private final JTextFieldFK txtRazFor = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private final JTextFieldPad txtPreco1 = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, Aplicativo.casasDecFin );

	private final JTextFieldPad txtPreco2 = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, Aplicativo.casasDecFin );

	private final JTextFieldPad txtPreco3 = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, Aplicativo.casasDecFin );
	
	private final ListaCampos lcGrupo = new ListaCampos( this, "GP" );
	
	private final ListaCampos lcUnidade = new ListaCampos( this, "UD" );
	
	private final ListaCampos lcFornecedor = new ListaCampos( this, "FO" );
	

	public RPProduto() {

		super( false );
		setTitulo( "Cadastro de produtos" );		
		setAtribos( 50, 50, 500, 420 );
		
		montaListaCampos();
		
		montaTela();
		
		setListaCampos( true, "PRODUTO", "RP" );
	}
	
	private void montaListaCampos() {
		
		/*******************
		 *      GRUPO      *
		 *******************/
		
		lcGrupo.add( new GuardaCampo( txtCodGrupo, "CodGrup", "Cód.grupo", ListaCampos.DB_PK, true ) );
		lcGrupo.add( new GuardaCampo( txtDescGrupo, "DescGrup", "Descrição do grupo", ListaCampos.DB_SI, false ) );
		lcGrupo.add( new GuardaCampo( txtSgGrupo, "SiglaGrup", "Sigla", ListaCampos.DB_SI, false ) );
		lcGrupo.montaSql( false, "GRUPO", "RP" );
		lcGrupo.setQueryCommit( false );
		lcGrupo.setReadOnly( true );
		txtCodGrupo.setTabelaExterna( lcGrupo );
		
		/*******************
		 *     UNIDADE     *
		 *******************/
		
		lcUnidade.add( new GuardaCampo( txtCodUnid, "CodUnid", "Cód.unid.", ListaCampos.DB_PK, true ) );
		lcUnidade.add( new GuardaCampo( txtDescUnid, "DescUnid", "Descrição da unidade", ListaCampos.DB_SI, false ) );
		lcUnidade.montaSql( false, "UNIDADE", "RP" );
		lcUnidade.setQueryCommit( false );
		lcUnidade.setReadOnly( true );
		txtCodUnid.setTabelaExterna( lcUnidade );
		
		/*******************
		 *    FORNECEDOR   *
		 *******************/
		
		lcFornecedor.add( new GuardaCampo( txtCodFor, "CodFor", "Cód.for.", ListaCampos.DB_PK, true ) );
		lcFornecedor.add( new GuardaCampo( txtRazFor, "RazFor", "Razão social do fornecedor", ListaCampos.DB_SI, false ) );
		lcFornecedor.montaSql( false, "FORNECEDOR", "RP" );
		lcFornecedor.setQueryCommit( false );
		lcFornecedor.setReadOnly( true );
		txtCodFor.setTabelaExterna( lcFornecedor );
		
	}
	
	private void montaTela() {
		
		adicCampo( txtCodProd, 7, 30, 100, 20, "CodProd", "Cód.prod.", ListaCampos.DB_PK, true );
		adicCampo( txtRefProd, 110, 30, 100, 20, "RefProd", "Refêrencia", ListaCampos.DB_SI, true );
		adicCampo( txtDescProd, 213, 30, 260, 20, "DescProd", "Descrição do produto", ListaCampos.DB_SI, true );
		
		adicCampo( txtCodBarra, 7, 70, 100, 20, "CodBarProd", "Cód.barras", ListaCampos.DB_SI, false );
		adicCampo( txtPesoLiq, 110, 70, 100, 20, "PesoLiqProd", "Peso liq.", ListaCampos.DB_SI, false );
		adicCampo( txtPesoBruto, 213, 70, 100, 20, "PesoBrutProd", "Peso bruto", ListaCampos.DB_SI, false );
		adicCampo( txtEmbalagem, 316, 70, 157, 20, "EmbalaProd", "Embalagem", ListaCampos.DB_SI, false );
		
		adicCampo( txtPreco1, 7, 110, 100, 20, "PrecoProd1", "Preço 1", ListaCampos.DB_SI, false );
		adicCampo( txtPreco2, 110, 110, 100, 20, "PrecoProd2", "Preço 2", ListaCampos.DB_SI, false );
		adicCampo( txtPreco3, 213, 110, 100, 20, "PrecoProd3", "Preço 3", ListaCampos.DB_SI, false );
		
		adicCampo( txtPercIPI, 7, 150, 100, 20, "PercIPIProd", "% IPI", ListaCampos.DB_SI, false );
		adicCampo( txtComiss, 110, 150, 100, 20, "ComisProd", "% Comissão", ListaCampos.DB_SI, false );
		adicCampo( txtCubagem, 213, 150, 100, 20, "CubagemProd", "Cubagem", ListaCampos.DB_SI, false );
		
		adicCampo( txtCodGrupo, 7, 190, 100, 20, "CodGrup", "Cód.grupo", ListaCampos.DB_FK, txtDescGrupo, true );
		adicDescFK( txtDescGrupo, 110, 190, 363, 20, "DescGrupo", "Descrição do grupo" );
		
		adicCampo( txtCodUnid, 7, 230, 100, 20, "CodUnid", "Cód.unidade", ListaCampos.DB_FK, txtDescUnid, true );
		adicDescFK( txtDescUnid, 110, 230, 363, 20, "DescUnid", "Descrição da unidade" );
		
		adicCampo( txtCodFor, 7, 270, 100, 20, "CodFor", "Cód.for.", ListaCampos.DB_FK, txtRazFor, false );
		adicDescFK( txtRazFor, 110, 270, 363, 20, "RazFor", "Razão social do fornecedor" );
		
		adicCampo( txtRefProdFor, 7, 310, 203, 20, "RefProdFor", "Refêrencia no fornecedor", ListaCampos.DB_SI, false );
	}

	public void setConexao( Connection cn ) {

		super.setConexao( cn );

		lcGrupo.setConexao( cn );
		lcUnidade.setConexao( cn );
		lcFornecedor.setConexao( cn );
	}
}
