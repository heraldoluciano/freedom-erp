/**
 * @version 02/2007 <BR>
 * @author Setpoint Informática Ltda.<BR>
 * @author Alex Rodrigues<BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.rep <BR>
 * Classe:
 * @(#)RPFornecedor.java <BR>
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
 * Tela para cadastro de fornecedores.
 * 
 */

package org.freedom.modulos.rep;

import java.awt.event.ActionListener;
import java.util.Vector;

import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.telas.FDados;

public class RPFornecedor extends FDados implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtCodFor = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );

	private final JTextFieldPad txtRazFor = new JTextFieldPad( JTextFieldPad.TP_STRING, 80, 0 );

	private final JTextFieldPad txtNomeFor = new JTextFieldPad( JTextFieldPad.TP_STRING, 80, 0 );

	private final JTextFieldPad txtCnpjFor = new JTextFieldPad( JTextFieldPad.TP_STRING, 14, 0 );

	private final JTextFieldPad txtInscFor = new JTextFieldPad( JTextFieldPad.TP_STRING, 15, 0 );

	private final JTextFieldPad txtEndFor = new JTextFieldPad( JTextFieldPad.TP_STRING, 45, 0 );

	// private final JTextFieldPad txtNumCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private final JTextFieldPad txtCidFor = new JTextFieldPad( JTextFieldPad.TP_STRING, 30, 0 );

	private final JTextFieldPad txtBairFor = new JTextFieldPad( JTextFieldPad.TP_STRING, 30, 0 );

	private final JTextFieldPad txtCepFor = new JTextFieldPad( JTextFieldPad.TP_STRING, 8, 0 );

	private final JTextFieldPad txtUFFor = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );

	private final JTextFieldPad txtDDDFor = new JTextFieldPad( JTextFieldPad.TP_STRING, 4, 0 );

	private final JTextFieldPad txtFoneFor = new JTextFieldPad( JTextFieldPad.TP_STRING, 8, 0 );

	private final JTextFieldPad txtFaxFor = new JTextFieldPad( JTextFieldPad.TP_STRING, 8, 0 );

	private final JTextFieldPad txtEmailFor = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private final JTextFieldPad txtCodRepFor = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private final JCheckBoxPad cbAtivo = new JCheckBoxPad( "     Ativo", "S", "N" );

	private JRadioGroup<String, String> TipoForn;

	public RPFornecedor() {

		super( false );
		setTitulo( "Cadastro de fornecedores" );
		setAtribos( 50, 50, 435, 430 );

		montaRadioGrupos();
		montaTela();

		setListaCampos( true, "FORNECEDOR", "RP" );

		txtCnpjFor.setMascara( JTextFieldPad.MC_CNPJ );
		txtCepFor.setMascara( JTextFieldPad.MC_CEP );
		txtFoneFor.setMascara( JTextFieldPad.MC_FONE );
		txtFaxFor.setMascara( JTextFieldPad.MC_FONE );

	}

	private void montaRadioGrupos() {

		Vector<String> labs = new Vector<String>();
		labs.add( "CIF" );
		labs.add( "FOB" );
		Vector<String> vals = new Vector<String>();
		vals.add( "C" );
		vals.add( "F" );
		TipoForn = new JRadioGroup<String, String>( 1, 2, labs, vals );

	}

	private void montaTela() {

		adicDB( TipoForn, 7, 25, 300, 30, "TipoFor", "Tipo", false );
		adicCampo( txtCodFor, 7, 80, 100, 20, "CodFor", "Cód.For.", ListaCampos.DB_PK, true );
		adicCampo( txtRazFor, 110, 80, 295, 20, "RazFor", "Razão social do fornecedor", ListaCampos.DB_SI, true );
		adicDB( cbAtivo, 325, 30, 80, 20, "AtivFor", "", true );

		adicCampo( txtNomeFor, 7, 120, 300, 20, "NomeFor", "Nome do fantasia", ListaCampos.DB_SI, true );
		adicCampo( txtCodRepFor, 313, 120, 95, 20, "CodRepFor", "Cód.rep.for.", ListaCampos.DB_SI, false );

		adicCampo( txtCnpjFor, 7, 160, 200, 20, "CnpjFor", "CNPJ", ListaCampos.DB_SI, false );
		adicCampo( txtInscFor, 210, 160, 200, 20, "InscFor", "Inscrição", ListaCampos.DB_SI, false );

		adicCampo( txtEndFor, 7, 200, 403, 20, "EndFor", "Endereço", ListaCampos.DB_SI, false );

		adicCampo( txtCidFor, 7, 240, 132, 20, "CidFor", "Cidade", ListaCampos.DB_SI, false );
		adicCampo( txtBairFor, 142, 240, 132, 20, "BairFor", "Bairro", ListaCampos.DB_SI, false );
		adicCampo( txtCepFor, 277, 240, 80, 20, "CepFor", "Cep", ListaCampos.DB_SI, false );
		adicCampo( txtUFFor, 360, 240, 50, 20, "EstFor", "UF", ListaCampos.DB_SI, false );

		adicCampo( txtDDDFor, 7, 280, 52, 20, "DDDFor", "DDD", ListaCampos.DB_SI, false );
		adicCampo( txtFoneFor, 62, 280, 172, 20, "FoneFor", "Fone", ListaCampos.DB_SI, false );
		adicCampo( txtFaxFor, 237, 280, 172, 20, "FaxFor", "Fax", ListaCampos.DB_SI, false );

		adicCampo( txtEmailFor, 7, 320, 403, 20, "EmailFor", "E-mail", ListaCampos.DB_SI, false );

		
	}
}
