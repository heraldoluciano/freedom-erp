/**
 * @version 02/11/2003 <BR>
 * @author Setpoint Informática Ltda./Anderson Sanchez <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.grh <BR>
 * Classe:
 * @(#)FEmpregado.java <BR>
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
 * Tela de cadastro de empregados.
 * 
 */

package org.freedom.modulos.grh;

import java.sql.Connection;
import java.util.Vector;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JComboBoxPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.PainelImagem;
import org.freedom.telas.FDados;

public class FEmpregado extends FDados {

	private static final long serialVersionUID = 1L;

	private final JTextFieldPad txtCod = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );

	private final JTextFieldPad txtCodFuncao = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );

	private final JTextFieldPad txtCodTurno = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );

	private final JTextFieldPad txtCodDepto = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );

	private final JTextFieldFK txtDescFuncao = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private final JTextFieldFK txtDescTurno = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private final JTextFieldFK txtDescDepto = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private final JTextFieldPad txtDesc = new JTextFieldPad( JTextFieldPad.TP_STRING, 40, 0 );
	
	private final JTextFieldPad txtApelido = new JTextFieldPad( JTextFieldPad.TP_STRING, 30, 0 );
	
	private final JTextFieldPad txtEndEmpr = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );
	
	private final JTextFieldPad txtNumEmpr = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 6, 0 );
	
	private final JTextFieldPad txtCidEmpr = new JTextFieldPad( JTextFieldPad.TP_STRING, 30, 0 );
	
	private final JTextFieldPad txtBairEmpr = new JTextFieldPad( JTextFieldPad.TP_STRING, 30, 0 );
	
	private final JTextFieldPad txtCepEmpr = new JTextFieldPad( JTextFieldPad.TP_STRING, 8, 0 );
	
	private final JTextFieldPad txtFoneEmpr = new JTextFieldPad( JTextFieldPad.TP_STRING, 12, 0 );
	
	private final JTextFieldPad txtDtAdmissao = new JTextFieldPad( JTextFieldPad.TP_DATE, 12, 0 );
	
	private JComboBoxPad cbStatus = null;
	
	private PainelImagem fotoEmpr = new PainelImagem( 65000 );
	
	private final ListaCampos lcFuncao = new ListaCampos( this, "FO" );

	private final ListaCampos lcTurno = new ListaCampos( this, "TO" );

	private final ListaCampos lcDepto = new ListaCampos( this, "DP" );
	

	public FEmpregado() {

		super();
		setTitulo( "Cadastro de Empregados" );
		setAtribos( 50, 50, 500, 360 );

		montaListaCampos();
		
		montaTela();
		
		setImprimir( false );
	}
	
	private void montaListaCampos() {

		lcFuncao.add( new GuardaCampo( txtCodFuncao, "CodFunc", "Cód.Func.", ListaCampos.DB_PK, true ) );
		lcFuncao.add( new GuardaCampo( txtDescFuncao, "DescFunc", "Descrição da função", ListaCampos.DB_SI, false ) );
		lcFuncao.montaSql( false, "FUNCAO", "RH" );
		lcFuncao.setQueryCommit( false );
		lcFuncao.setReadOnly( true );
		txtCodFuncao.setTabelaExterna( lcFuncao );

		lcTurno.add( new GuardaCampo( txtCodTurno, "CodTurno", "Cód. turno", ListaCampos.DB_PK, true ) );
		lcTurno.add( new GuardaCampo( txtDescTurno, "DescTurno", "Descrição do turno", ListaCampos.DB_SI, false ) );
		lcTurno.montaSql( false, "TURNO", "RH" );
		lcTurno.setQueryCommit( false );
		lcTurno.setReadOnly( true );
		txtCodTurno.setTabelaExterna( lcTurno );

		lcDepto.add( new GuardaCampo( txtCodDepto, "CodDep", "Cód.Depto.", ListaCampos.DB_PK, true ) );
		lcDepto.add( new GuardaCampo( txtDescDepto, "DescDep", "Descrição do departamento", ListaCampos.DB_SI, false ) );
		lcDepto.montaSql( false, "DEPTO", "RH" );
		lcDepto.setQueryCommit( false );
		lcDepto.setReadOnly( true );
		txtCodDepto.setTabelaExterna( lcDepto );
	}

	private void montaTela() {
		
		Vector<String> vVals = new Vector<String>();
		vVals.addElement( "AD" );
		vVals.addElement( "DE" );
		vVals.addElement( "EF" );
		vVals.addElement( "LM" );
		vVals.addElement( "AI" );
		vVals.addElement( "AP" );		
		Vector<String> vLabs = new Vector<String>();
		vLabs.addElement( "Admitido" );
		vLabs.addElement( "Demitido" );
		vLabs.addElement( "Em férias" );
		vLabs.addElement( "Licença maternidade" );
		vLabs.addElement( "Afastamento INSS" );
		vLabs.addElement( "Aposentado" );
		
		cbStatus = new JComboBoxPad( vLabs, vVals, JComboBoxPad.TP_STRING, 2, 0 );
		
		adicCampo( txtCod, 7, 20, 80, 20, "MatEmpr", "Matricula", ListaCampos.DB_PK, true );
		adicCampo( txtDesc, 90, 20, 260, 20, "NomeEmpr", "Nome do empregado", ListaCampos.DB_SI, true );
		adicDB( fotoEmpr, 365, 20, 100, 133, "FotoEmpr", "Foto ( 3 x 4 )", false );
		adicDB( cbStatus, 365, 180, 100, 20, "StatusEmpr", "Status", false );
		adicCampo( txtApelido, 7, 60, 100, 20, "ApelidoEmpr", "Apelido", ListaCampos.DB_SI, true );
		adicCampo( txtCodTurno, 110, 60, 70, 20, "CodTurno", "Cód. Turno", ListaCampos.DB_FK, true );
		adicDescFK( txtDescTurno, 183, 60, 165, 20, "DescTurno", "Descrição do turno" );
		adicCampo( txtEndEmpr, 7, 100, 260, 20, "EndEmpr", "Endereço", ListaCampos.DB_SI, false );
		adicCampo( txtNumEmpr, 270, 100, 80, 20, "NumEmpr", "Num.", ListaCampos.DB_SI, false );
		adicCampo( txtBairEmpr, 7, 140, 180, 20, "BairEmpr", "Bairro", ListaCampos.DB_SI, false );
		adicCampo( txtCidEmpr, 190, 140, 160, 20, "CidEmpr", "Cidade", ListaCampos.DB_SI, false );
		adicCampo( txtCepEmpr, 7, 180, 120, 20, "CepEmpr", "Cep", ListaCampos.DB_SI, false );		
		adicCampo( txtFoneEmpr, 133, 180, 120, 20, "FoneEmpr", "Telefone", ListaCampos.DB_SI, false );
		adicCampo( txtDtAdmissao , 260, 180, 90, 20, "DtAdmissao", "Data Admissão", ListaCampos.DB_SI, true );
		adicCampo( txtCodFuncao, 7, 220, 80, 20, "CodFunc", "Cód.Func.", ListaCampos.DB_FK, true );
		adicDescFK( txtDescFuncao, 90, 220, 260, 20, "DescFunc", "Descrição da função" );
		adicCampo( txtCodDepto, 7, 260, 80, 20, "CodDep", "Cód.Depto.", ListaCampos.DB_FK, true );
		adicDescFK( txtDescDepto, 90, 260, 260, 20, "DescDepto", "Descrição do departamento" );
		
		txtCepEmpr.setMascara( JTextFieldPad.MC_CEP );
		txtFoneEmpr.setMascara( JTextFieldPad.MC_FONE );
	
		setListaCampos( true, "EMPREGADO", "RH" );
		lcCampos.setQueryInsert( false );
	}

	public void setConexao( Connection cn ) {

		super.setConexao( cn );
		lcFuncao.setConexao( cn );
		lcTurno.setConexao( cn );
		lcDepto.setConexao( cn );
	}
}
