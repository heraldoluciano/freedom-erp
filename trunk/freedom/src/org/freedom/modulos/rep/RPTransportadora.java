/**
 * @version 02/2007 <BR>
 * @author Setpoint Informática Ltda.<BR>
 * @author Alex Rodrigues<BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.rep <BR>
 * Classe:
 * @(#)RPTransportadora.java <BR>
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
 * Tela para cadastro de transportadoras.
 * 
 */

package org.freedom.modulos.rep;

import java.awt.event.ActionListener;

import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.telas.FDados;

public class RPTransportadora extends FDados implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtCodTran = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );

	private final JTextFieldPad txtRazTran = new JTextFieldPad( JTextFieldPad.TP_STRING, 80, 0 );

	private final JTextFieldPad txtNomeTran = new JTextFieldPad( JTextFieldPad.TP_STRING, 80, 0 );

	private final JTextFieldPad txtCnpjTran = new JTextFieldPad( JTextFieldPad.TP_STRING, 14, 0 );

	private final JTextFieldPad txtInscTran = new JTextFieldPad( JTextFieldPad.TP_STRING, 15, 0 );
	
	private final JTextFieldPad txtEndTran = new JTextFieldPad( JTextFieldPad.TP_STRING, 45, 0 );

	//private final JTextFieldPad txtNumCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private final JTextFieldPad txtCidTran = new JTextFieldPad( JTextFieldPad.TP_STRING, 30, 0 );

	private final JTextFieldPad txtBairTran = new JTextFieldPad( JTextFieldPad.TP_STRING, 30, 0 );

	private final JTextFieldPad txtCepTran = new JTextFieldPad( JTextFieldPad.TP_STRING, 8, 0 );

	private final JTextFieldPad txtUFTran = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );

	private final JTextFieldPad txtDDDTran = new JTextFieldPad( JTextFieldPad.TP_STRING, 4, 0 );

	private final JTextFieldPad txtFoneTran = new JTextFieldPad( JTextFieldPad.TP_STRING, 8, 0 );

	private final JTextFieldPad txtFaxTran = new JTextFieldPad( JTextFieldPad.TP_STRING, 8, 0 );

	private final JTextFieldPad txtEmailTran = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	public RPTransportadora() {

		super( false );
		setTitulo( "Cadastro de Trannecedores" );		
		setAtribos( 50, 50, 435, 380 );
		
		montaTela();
		setListaCampos( true, "TRANSP", "RP" );
		
		txtCnpjTran.setMascara( JTextFieldPad.MC_CNPJ );
		txtCepTran.setMascara( JTextFieldPad.MC_CEP );
		txtFoneTran.setMascara( JTextFieldPad.MC_FONE );
		txtFaxTran.setMascara( JTextFieldPad.MC_FONE );
	}
	
	private void montaTela() {
		
		adicCampo( txtCodTran, 7, 30, 100, 20, "CodTran", "Cód.transp.", ListaCampos.DB_PK, true );
		adicCampo( txtRazTran, 110, 30, 300, 20, "RazTran", "Razão social da transportadora", ListaCampos.DB_SI, true );
		
		adicCampo( txtNomeTran, 7, 70, 403, 20, "NomeTran", "Nome do fantazia", ListaCampos.DB_SI, true );
		
		adicCampo( txtCnpjTran, 7, 110, 200, 20, "CnpjTran", "CNPJ", ListaCampos.DB_SI, false );
		adicCampo( txtInscTran, 210, 110, 200, 20, "InscTran", "Inscrição", ListaCampos.DB_SI, false );
		
		adicCampo( txtEndTran, 7, 150, 403, 20, "EndTran", "Endereço", ListaCampos.DB_SI, false );
		
		adicCampo( txtCidTran, 7, 190, 132, 20, "CidTran", "Cidade", ListaCampos.DB_SI, false );
		adicCampo( txtBairTran, 142, 190, 132, 20, "BairTran", "Bairro", ListaCampos.DB_SI, false );		
		adicCampo( txtCepTran, 277, 190, 80, 20, "CepTran", "Cep", ListaCampos.DB_SI, false );
		adicCampo( txtUFTran, 360, 190, 50, 20, "EstTran", "UF", ListaCampos.DB_SI, false );
		
		adicCampo( txtDDDTran, 7, 230, 52, 20, "DDDTran", "DDD", ListaCampos.DB_SI, false );
		adicCampo( txtFoneTran, 62, 230, 172, 20, "FoneTran", "Fone", ListaCampos.DB_SI, false );		
		adicCampo( txtFaxTran, 237, 230, 172, 20, "FaxTran", "Fax", ListaCampos.DB_SI, false );
		
		adicCampo( txtEmailTran, 7, 270, 403, 20, "EmailTran", "E-mail", ListaCampos.DB_SI, false );
	}
}
