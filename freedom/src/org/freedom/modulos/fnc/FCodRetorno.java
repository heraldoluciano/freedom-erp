/**
 * @version 28/02/2007 <BR>
 * @author Setpoint Informática Ltda./Alex Rodrigues<BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.fnc <BR>
 * Classe:
 * @(#)FCodRetorno.java <BR>
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
 * Tela de cadastros de códigos de retorno.
 * 
 */
package org.freedom.modulos.fnc;

import java.sql.Connection;
import java.util.Vector;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.telas.FDados;

/**
 * @author Robson
 * 
 * TODO To change the template for this generated type comment go to Window - Preferences - Java - Code Style - Code Templates
 */
public class FCodRetorno extends FDados implements CarregaListener {

	private static final long serialVersionUID = 1L;

	private final JTextFieldPad txtCodBanco = new JTextFieldPad( JTextFieldPad.TP_STRING, 3, 0 );

	private final JTextFieldFK txtNomeBanco = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private final JTextFieldPad txtCodRet = new JTextFieldPad( JTextFieldPad.TP_STRING, 4, 0 );

	private final JTextFieldPad txtDescRet = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private final JRadioGroup rgTipoFebraban;

	private final ListaCampos lcBanco = new ListaCampos( this, "BO" );

	public FCodRetorno() {

		setTitulo( "Códigos de retorno" );
		setAtribos( 200, 60, 430, 180 );
		
		Vector<String> vLabs = new Vector<String>();
		Vector<String> vVals = new Vector<String>();
		vLabs.add( "SIACC" );
		vLabs.add( "CNAB" );
		vVals.add( "01" );
		vVals.add( "02" );
		rgTipoFebraban = new JRadioGroup( 2, 1, vLabs, vVals );
		
		lcBanco.add( new GuardaCampo( txtCodBanco, "CodBanco", "Cód.banco", ListaCampos.DB_PK, true ) );
		lcBanco.add( new GuardaCampo( txtNomeBanco, "NomeBanco", "Nome do Banco", ListaCampos.DB_SI, false ) );
		lcBanco.montaSql( false, "BANCO", "FN" );
		lcBanco.setQueryCommit( false );
		lcBanco.setReadOnly( true );		
		txtCodBanco.setTabelaExterna( lcBanco );
		
		montaTela();
		
		setListaCampos( false, "FBNCODRET", "FN" );
		
		lcCampos.addCarregaListener( this );
	}
	
	private void montaTela() {

		adicDB( rgTipoFebraban, 300, 30, 100, 60, "TipoFebraban", "Tipo", false );		
		
		adicCampo( txtCodBanco, 7, 30, 80, 20, "CodBanco", "Cód.banco", ListaCampos.DB_PF, txtNomeBanco, true );
		adicDescFK( txtNomeBanco, 90, 30, 200, 20, "NomeBanco", "Nome do banco" );
		adicCampo( txtCodRet, 7, 70, 80, 20, "CodRet", "Cód.retorno", ListaCampos.DB_SI, true );
		adicCampo( txtDescRet, 90, 70, 200, 20, "DescRet", "Descrição do retorno", ListaCampos.DB_SI, true );	
	}

	public void afterCarrega( CarregaEvent cevt ) { }

	public void beforeCarrega( CarregaEvent cevt ) {
		
		if( cevt.getListaCampos() == lcCampos ) {
		//	lcBanco.carregaDados();
		}
	}

	public void setConexao( Connection cn ) {

		super.setConexao( cn );
		lcBanco.setConexao( cn );
	}

}
