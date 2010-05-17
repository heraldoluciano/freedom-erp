/**
 * @version 02/2007 <BR>
 * @author Setpoint Informática Ltda.<BR>
 * @author Alex Rodrigues<BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.rep <BR>
 * Classe:
 * @(#)RPContatos.java <BR>
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
 * Tela para cadastro de contatos.
 * 
 */

package org.freedom.modulos.rep;

import java.awt.event.ActionListener;

import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.telas.FDados;

public class RPContato extends FDados implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtCodCont = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );

	private JTextFieldPad txtNomeCont = new JTextFieldPad( JTextFieldPad.TP_STRING, 40, 0 );
	
	private final JTextFieldPad txtEndCont = new JTextFieldPad( JTextFieldPad.TP_STRING, 45, 0 );

	//private final JTextFieldPad txtNumCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private final JTextFieldPad txtCidCont = new JTextFieldPad( JTextFieldPad.TP_STRING, 30, 0 );

	private final JTextFieldPad txtBairCont = new JTextFieldPad( JTextFieldPad.TP_STRING, 30, 0 );

	private final JTextFieldPad txtCepCont = new JTextFieldPad( JTextFieldPad.TP_STRING, 8, 0 );

	private final JTextFieldPad txtUFCont = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );

	private final JTextFieldPad txtDDDCont = new JTextFieldPad( JTextFieldPad.TP_STRING, 4, 0 );

	private final JTextFieldPad txtFoneCont = new JTextFieldPad( JTextFieldPad.TP_STRING, 8, 0 );

	private final JTextFieldPad txtFaxCont = new JTextFieldPad( JTextFieldPad.TP_STRING, 8, 0 );

	private final JTextFieldPad txtEmailCont = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private final JTextFieldPad txtNascCont = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	public RPContato() {

		super( false );
		setTitulo( "Cadastro de contato" );		
		setAtribos( 50, 50, 435, 300 );
		
		montaTela();
		setListaCampos( true, "CONTATO", "RP" );
		
		txtCepCont.setMascara( JTextFieldPad.MC_CEP );
		txtFoneCont.setMascara( JTextFieldPad.MC_FONE );
		txtFaxCont.setMascara( JTextFieldPad.MC_FONE );
	}
	
	private void montaTela() {
		
		adicCampo( txtCodCont, 7, 30, 100, 20, "CodCont", "Cód.cont.", ListaCampos.DB_PK, true );
		adicCampo( txtNomeCont, 110, 30, 300, 20, "NomeCont", "Nome do contato", ListaCampos.DB_SI, true );
		
		adicCampo( txtEndCont, 7, 70, 403, 20, "EndCont", "Endereço", ListaCampos.DB_SI, false );
		
		adicCampo( txtCidCont, 7, 110, 132, 20, "CidCont", "Cidade", ListaCampos.DB_SI, false );
		adicCampo( txtBairCont, 142, 110, 132, 20, "BairCont", "Bairro", ListaCampos.DB_SI, false );		
		adicCampo( txtCepCont, 277, 110, 80, 20, "CepCont", "Cep", ListaCampos.DB_SI, false );
		adicCampo( txtUFCont, 360, 110, 50, 20, "EstCont", "UF", ListaCampos.DB_SI, false );
		
		adicCampo( txtDDDCont, 7, 150, 52, 20, "DDDCont", "DDD", ListaCampos.DB_SI, false );
		adicCampo( txtFoneCont, 62, 150, 172, 20, "FoneCont", "Fone", ListaCampos.DB_SI, false );		
		adicCampo( txtFaxCont, 237, 150, 172, 20, "FaxCont", "Fax", ListaCampos.DB_SI, false );
		
		adicCampo( txtEmailCont, 7, 190, 300, 20, "EmailCont", "E-mail", ListaCampos.DB_SI, false );
		adicCampo( txtNascCont, 310, 190, 100, 20, "NascCont", "Nascimento", ListaCampos.DB_SI, false );
	}
}
