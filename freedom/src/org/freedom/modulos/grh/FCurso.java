/**
 * @version 18/02/2008 <BR>
 * @author Setpoint Informática Ltda./Alex Rodrigues <BR>
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

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JTextAreaPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.telas.FDados;

public class FCurso extends FDados {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtCodCurso = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldPad txtDescCurso = new JTextFieldPad( JTextFieldPad.TP_STRING, 60, 0 );
	
	private JTextFieldPad txtIntituicaoCurso = new JTextFieldPad( JTextFieldPad.TP_STRING, 80, 0 );
	
	private JTextFieldPad txtDuracaoCurso = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );
	
	private JTextAreaPad txtConteudoCurso = new JTextAreaPad( 1000 );

	private JTextFieldPad txtCodNivel = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescNivel = new JTextFieldFK( JTextFieldPad.TP_STRING, 60, 0 );

	private JTextFieldPad txtCodArea = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescArea = new JTextFieldFK( JTextFieldPad.TP_STRING, 60, 0 );

	private ListaCampos lcNivel = new ListaCampos( this, "NC" );

	private ListaCampos lcArea = new ListaCampos( this, "AR" );
	

	public FCurso() {

		super();
		setTitulo( "Cadastro de Cursos" );
		setAtribos( 50, 50, 425, 400 );

		montaListaCampos();
		
		montaTela();
		
		setImprimir( false );
	}
	
	private void montaListaCampos() {

		lcNivel.add( new GuardaCampo( txtCodNivel, "CodNivelCurso", "Cód.nível", ListaCampos.DB_PK, true ) );
		lcNivel.add( new GuardaCampo( txtDescNivel, "DescNivelCurso", "Descrição do nível do curso", ListaCampos.DB_SI, false ) );
		lcNivel.montaSql( false, "NIVELCURSO", "RH" );
		lcNivel.setQueryCommit( false );
		lcNivel.setReadOnly( true );
		txtCodNivel.setTabelaExterna( lcNivel );

		lcArea.add( new GuardaCampo( txtCodArea, "CodArea", "Cód.área", ListaCampos.DB_PK, true ) );
		lcArea.add( new GuardaCampo( txtDescArea, "DescArea", "Descrição da área", ListaCampos.DB_SI, false ) );
		lcArea.montaSql( false, "AREA", "RH" );
		lcArea.setQueryCommit( false );
		lcArea.setReadOnly( true );
		txtCodArea.setTabelaExterna( lcArea );
	}

	private void montaTela() {
		
		adicCampo( txtCodCurso, 7, 20, 90, 20, "CodCurso", "Cód.curso", ListaCampos.DB_PK, true );
		adicCampo( txtDescCurso, 100, 20, 300, 20, "DescCurso", "Descrição do curso", ListaCampos.DB_SI, true );
		adicCampo( txtCodNivel, 7, 60, 90, 20, "CodNivelCurso", "Cód.nível", ListaCampos.DB_FK, true );
		adicDescFK( txtDescNivel, 100, 60, 300, 20, "DescNivelCurso", "Descrição do nível" );
		adicCampo( txtCodArea, 7, 100, 90, 20, "CodArea", "Cód.área", ListaCampos.DB_FK, false );
		adicDescFK( txtDescArea, 100, 100, 300, 20, "DescArea", "Descrição da área" );
		
		adicCampo( txtIntituicaoCurso, 7, 140, 270, 20, "instituicaoCurso", "Instituição de ensino", ListaCampos.DB_SI, false );
		adicCampo( txtDuracaoCurso, 280, 140, 120, 20, "DuracaoCurso", "Duracao(em meses)", ListaCampos.DB_SI, false );
	
		adicDB( txtConteudoCurso, 7, 180, 393, 140, "ContProgCurso", "Conteúdo programático", false );
		
		setListaCampos( true, "CURSO", "RH" );
		lcCampos.setQueryInsert( false );
	}

	public void setConexao( Connection cn ) {

		super.setConexao( cn );
		lcNivel.setConexao( cn );
		lcArea.setConexao( cn );
	}
}
