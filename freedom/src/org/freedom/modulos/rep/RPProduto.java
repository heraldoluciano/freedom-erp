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
	
	private final JTextFieldFK txtDescGrupo = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	private final JTextFieldFK txtDescUnid = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	private final JTextFieldFK txtDescFor = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private final JTextFieldPad txtPreco1 = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, Aplicativo.casasDecFin );

	private final JTextFieldPad txtPreco2 = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, Aplicativo.casasDecFin );

	private final JTextFieldPad txtPreco3 = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, Aplicativo.casasDecFin );
	

	public RPProduto() {

		super();
		setTitulo( "Cadastro de produtos" );		
		setAtribos( 50, 50, 500, 420 );
		
		montaTela();
		setListaCampos( true, "PRODUTO", "RP" );
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
		adicCampo( txtComiss, 110, 150, 100, 20, "ComissProd", "% Comissão", ListaCampos.DB_SI, false );
		
		adicCampo( txtCodGrupo, 7, 190, 100, 20, "CodGrup", "Cód.grupo", ListaCampos.DB_FK, txtDescGrupo, true );
		adicCampo( txtDescGrupo, 110, 190, 363, 20, "DescGrupo", "Descrição do grupo", ListaCampos.DB_SI, false );
		
		adicCampo( txtCodUnid, 7, 230, 100, 20, "CodUnid", "Cód.unidade", ListaCampos.DB_FK, txtDescUnid, true );
		adicCampo( txtDescUnid, 110, 230, 363, 20, "DescUnid", "Descrição da unidade", ListaCampos.DB_SI, false );
		
		adicCampo( txtCodFor, 7, 270, 100, 20, "CodFor", "Cód.for.", ListaCampos.DB_FK, txtDescFor, false );
		adicCampo( txtDescFor, 110, 270, 363, 20, "RazFor", "Razão social do fornecedor", ListaCampos.DB_SI, false );
		
		adicCampo( txtRefProdFor, 7, 310, 203, 20, "RefProdFor", "Refêrencia no fornecedor", ListaCampos.DB_SI, false );
	}
}
