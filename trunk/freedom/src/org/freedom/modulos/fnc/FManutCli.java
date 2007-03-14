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
 * Tela de manutenção dos dados dos clientes referentes ao esquema Febraban.
 * 
 */
package org.freedom.modulos.fnc;

import java.sql.Connection;
import java.util.Vector;

import javax.swing.JLabel;

import org.freedom.acao.PostEvent;
import org.freedom.acao.PostListener;
import org.freedom.acao.RadioGroupEvent;
import org.freedom.acao.RadioGroupListener;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FDados;

public class FManutCli extends FDados implements RadioGroupListener, PostListener {

	private static final long serialVersionUID = 1L;
	
	private final JTextFieldPad txtCodCli = new JTextFieldPad( JTextFieldPad.TP_STRING, 8, 0 );

	private final JTextFieldFK txtRazCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	private final JTextFieldPad txtCodEmpPF = new JTextFieldPad( JTextFieldPad.TP_STRING, 8, 0 );
	
	private final JTextFieldPad txtCodFilialPF = new JTextFieldPad( JTextFieldPad.TP_STRING, 8, 0 );

	private final JTextFieldPad txtCodBanco = new JTextFieldPad( JTextFieldPad.TP_STRING, 3, 0 );

	private final JTextFieldFK txtNomeBanco = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private final JTextFieldPad txtAgencia = new JTextFieldPad( JTextFieldPad.TP_STRING, 4, 0 );

	private final JTextFieldPad txtIdentificacao = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private final JTextFieldPad txtTipoFebraban = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );

	private final JRadioGroup rgTipoFebraban;

	private final JRadioGroup rgSubTipoFebraban;

	private final ListaCampos lcBanco = new ListaCampos( this, "BO" );
	
	private final ListaCampos lcCliente = new ListaCampos( this, "" );

	private Vector<String> vLabs = new Vector<String>();

	private Vector<String> vVals = new Vector<String>();

	private Vector<String> vLabs1 = new Vector<String>();

	private Vector<String> vVals1 = new Vector<String>();

	public FManutCli() {

		setTitulo( "Códigos de retorno" );
		setAtribos( 200, 60, 367, 290 );
		
		
		vLabs.add( "SIACC" );
		vLabs.add( "CNAB" );
		vVals.add( "01" );
		vVals.add( "02" );
		rgTipoFebraban = new JRadioGroup( 1, 2, vLabs, vVals );

		vLabs1.add( "Débito em folha" );
		vLabs1.add( "Débito em conta" );
		vVals1.add( "01" );
		vVals1.add( "02" );
		rgSubTipoFebraban = new JRadioGroup( 2, 1, vLabs1, vVals1 );
		rgSubTipoFebraban.setVlrString( "02" );

		lcBanco.add( new GuardaCampo( txtCodBanco, "CodBanco", "Cód.banco", ListaCampos.DB_PK, true ) );
		lcBanco.add( new GuardaCampo( txtNomeBanco, "NomeBanco", "Nome do Banco", ListaCampos.DB_SI, false ) );
		lcBanco.montaSql( false, "BANCO", "FN" );
		lcBanco.setQueryCommit( false );
		lcBanco.setReadOnly( true );
		txtCodBanco.setTabelaExterna( lcBanco );
		
		lcCliente.add( new GuardaCampo( txtCodCli, "CodCli", "Cód.cli.", ListaCampos.DB_PK, true ) );
		lcCliente.add( new GuardaCampo( txtRazCli, "RazCli", "Razão social do cliente", ListaCampos.DB_SI, false ) );
		lcCliente.montaSql( false, "CLIENTE", "VD" );
		lcCliente.setQueryCommit( false );
		lcCliente.setReadOnly( true );
		txtCodCli.setTabelaExterna( lcCliente );

		montaTela();

		rgTipoFebraban.addRadioGroupListener( this );
		
		lcCampos.addPostListener( this );
	}

	private void montaTela() {
		
		adic( new JLabel( "Tipo:"), 7, 0, 333, 20  );
		adic( rgTipoFebraban, 7, 20, 333, 30  );	
		
		txtTipoFebraban.setVlrString( "01" );		
		lcCampos.add( new GuardaCampo( txtTipoFebraban, "TipoFebraban", "Tipo", ListaCampos.DB_PK, true ) );
		
		adicCampo( txtCodCli, 7, 70, 90, 20, "CodCli", "Cód.cli.", ListaCampos.DB_PF, txtRazCli, true );
		adicDescFK( txtRazCli, 100, 70, 240, 20, "RazCli", "Razão social do cliente" );
		adicCampo( txtCodBanco, 7, 110, 90, 20, "CodBanco", "Cód.banco", ListaCampos.DB_PF, txtNomeBanco, true );
		adicDescFK( txtNomeBanco, 100, 110, 240, 20, "NomeBanco", "Nome do banco" );
		adicCampo( txtAgencia, 7, 150, 150, 20, "AgenciaCli", "Agência", ListaCampos.DB_SI, true );
		adicCampo( txtIdentificacao, 7, 190, 150, 20, "IdentCli", "Identificação", ListaCampos.DB_SI, true );
		adicDB( rgSubTipoFebraban, 170, 150, 170, 60, "STipoFebraban", "", false );
		adicCampoInvisivel( txtCodEmpPF, "CodEmpPF", "Cód.emp.pf.", ListaCampos.DB_SI, false );
		adicCampoInvisivel( txtCodFilialPF, "CodFilialPF", "Cód.filial.pf.", ListaCampos.DB_SI, false );

		setListaCampos( false, "FBNCLI", "FN" );
	}

	@ Override
	public void beforePost( PostEvent e ) {

		super.beforePost( e );
		
		txtCodEmpPF.setVlrInteger( Aplicativo.iCodEmp );
		txtCodFilialPF.setVlrInteger( ListaCampos.getMasterFilial( "SGPREFERE6" ) );
	}

	public void valorAlterado( RadioGroupEvent evt ) {

		if ( evt.getIndice() >= 0 ) {
			lcCampos.limpaCampos( true );
			txtTipoFebraban.setVlrString( (String) vVals.elementAt( evt.getIndice() ) );
		}
	}

	public void setConexao( Connection cn ) {

		super.setConexao( cn );
		lcBanco.setConexao( cn );
		lcCliente.setConexao( cn );
	}

}
