/**
 * @version 21/02/2008 <BR>
 * @author Setpoint Informática Ltda./Anderson Sanchez <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.grh <BR>
 * Classe:
 * @(#)FVaga.java <BR>
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
 * Tela de cadastro de vagas.
 * 
 */

package org.freedom.modulos.grh;

import java.awt.BorderLayout;
import java.sql.Connection;
import javax.swing.JScrollPane;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Navegador;
import org.freedom.componentes.Tabela;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FTabDados;

public class FVaga extends FTabDados {

	private static final long serialVersionUID = 1L;
	
	private final JPanelPad panelGeral = new JPanelPad();
	
	private final JPanelPad panelCurso = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );
	
	private final JPanelPad panelCursoCampos = new JPanelPad( 0, 80 );
	
	private final JPanelPad panelAtribQuali = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );
	
	private final JPanelPad panelAtribQualiCampos = new JPanelPad( 0, 80 );

	private final JPanelPad panelAtribRestr = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );
	
	private final JPanelPad panelAtribRestrCampos = new JPanelPad( 0, 80 );
		
	// GERAL

	private final JTextFieldPad txtCodVaga = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private final JTextFieldPad txtCodEmpr = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private final JTextFieldFK txtNomeEmpr = new JTextFieldFK( JTextFieldPad.TP_STRING, 60, 0 );
	
	private final JTextFieldPad txtCodTurnoVaga = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );	
	
	private final JTextFieldFK txtDescTurnoVaga = new JTextFieldFK( JTextFieldPad.TP_STRING, 60, 0 );
				
	private final JTextFieldPad txtFaixaSalIni = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, Aplicativo.casasDecFin );
	
	private final JTextFieldPad txtFaixaSalFim = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, Aplicativo.casasDecFin );
	
	private final JTextFieldPad txtCodCursoVaga = new JTextFieldPad( JTextFieldPad.TP_STRING, 15, 0 );
	
	private final JTextFieldFK txtDescCursoVaga = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	private final JTextFieldPad txtCodAtribVagaQ = new JTextFieldPad( JTextFieldPad.TP_STRING, 15, 0 );
	
	private final JTextFieldFK txtDescAtribVagaQ = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
			
	private final JTextFieldPad txtCodAtribVagaR = new JTextFieldPad( JTextFieldPad.TP_STRING, 15, 0 );
	
	private final JTextFieldFK txtDescAtribVagaR = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	private final JTextFieldPad txtCodFuncaoVaga = new JTextFieldPad( JTextFieldPad.TP_STRING, 15, 0 );
	
	private final JTextFieldFK txtDescFuncaoVaga = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	

	private final Tabela tabCurso = new Tabela();

	private final Tabela tabAtribuicaoQ = new Tabela();

	private final Tabela tabAtribuicaoR = new Tabela();

	private final Tabela tabFuncao = new Tabela();

	private final ListaCampos lcEmpregador = new ListaCampos( this, "EM" );
	
	private final ListaCampos lcCurso = new ListaCampos( this, "CS" );
	
	private final ListaCampos lcVagaCurso = new ListaCampos( this );
	
	private final ListaCampos lcTurno = new ListaCampos( this, "TN" );

	private final ListaCampos lcVagaAtribuicaoQ = new ListaCampos( this );
	
	private final ListaCampos lcVagaAtribuicaoR = new ListaCampos( this );
	
	private final ListaCampos lcAtribuicaoQ = new ListaCampos( this, "AT" );
	
	private final ListaCampos lcAtribuicaoR = new ListaCampos( this, "AT" );

	private final ListaCampos lcFuncao = new ListaCampos( this, "FC" );

	private final Navegador navCurso = new Navegador( true );

	private final Navegador navAtribuicaoQ = new Navegador( true );
	
	private final Navegador navAtribuicaoR = new Navegador( true );

	private final Navegador navFuncao = new Navegador( true );
	

	public FVaga() {

		super( false );
		setTitulo( "Cadastro de Vagas" );
		setAtribos( 50, 50, 550, 270 );
		
		lcVagaAtribuicaoQ.setMaster( lcCampos );
		lcCampos.adicDetalhe( lcVagaAtribuicaoQ );
		lcVagaAtribuicaoQ.setTabela( tabAtribuicaoQ );

		lcVagaAtribuicaoR.setMaster( lcCampos );
		lcCampos.adicDetalhe( lcVagaAtribuicaoR );
		lcVagaAtribuicaoR.setTabela( tabAtribuicaoR );
		
		lcVagaCurso.setMaster( lcCampos );
		lcCampos.adicDetalhe( lcVagaCurso );
		lcVagaCurso.setTabela( tabCurso );

		montaListaCampos();
		montaTela();
		
		setImprimir( false );
	}
	
	private void montaListaCampos() {

		lcEmpregador.add( new GuardaCampo( txtCodEmpr, "CodEmpr", "Cód.Empreg.", ListaCampos.DB_PK, true ) );
		lcEmpregador.add( new GuardaCampo( txtNomeEmpr, "NomeEmpr", "Nome do empregador", ListaCampos.DB_SI, false ) );
		lcEmpregador.montaSql( false, "EMPREGADOR", "RH" );
		lcEmpregador.setQueryCommit( false );
		lcEmpregador.setReadOnly( true );
		txtCodEmpr.setTabelaExterna( lcEmpregador );

		lcTurno.add( new GuardaCampo( txtCodTurnoVaga, "CodTurno", "Cód.Turno", ListaCampos.DB_PK, null, false ) );
		lcTurno.add( new GuardaCampo( txtDescTurnoVaga, "DescTurno", "Descrição do Turno", ListaCampos.DB_SI, false ) );
		lcTurno.montaSql( false, "TURNO", "RH" );		
		lcTurno.setQueryCommit( false );
		lcTurno.setReadOnly( true );
		txtCodTurnoVaga.setTabelaExterna( lcTurno );
				
		lcFuncao.add( new GuardaCampo( txtCodFuncaoVaga, "CodFunc", "Cód.função", ListaCampos.DB_PK, null, false ) );
		lcFuncao.add( new GuardaCampo( txtDescFuncaoVaga, "DescFunc", "Descrição da função", ListaCampos.DB_SI, false ) );
		lcFuncao.montaSql( false, "FUNCAO", "RH" );
		lcFuncao.setReadOnly( true );
		lcFuncao.setQueryCommit( false );
		txtCodFuncaoVaga.setListaCampos( lcFuncao );
		txtCodFuncaoVaga.setTabelaExterna( lcFuncao );
		
		lcCurso.add( new GuardaCampo( txtCodCursoVaga, "CodCurso", "Cód.curso", ListaCampos.DB_PK, txtDescCursoVaga, false ) );
		lcCurso.add( new GuardaCampo( txtDescCursoVaga, "DescCurso", "Descrição do curso", ListaCampos.DB_SI, false ) );
		lcCurso.montaSql( false, "CURSO", "RH" );
		lcCurso.setReadOnly( true );
		lcCurso.setQueryCommit( false );
		txtCodCursoVaga.setTabelaExterna( lcCurso );
		txtCodCursoVaga.setFK( true );
		txtCodCursoVaga.setListaCampos( lcCurso );
		txtDescCursoVaga.setListaCampos( lcCurso );
		
		lcAtribuicaoQ.add( new GuardaCampo( txtCodAtribVagaQ, "CodAtrib", "Cód.Atrib.", ListaCampos.DB_PK, null, false ) );
		lcAtribuicaoQ.add( new GuardaCampo( txtDescAtribVagaQ, "DescAtrib", "Descrição da atribuição", ListaCampos.DB_SI, false ) );
		lcAtribuicaoQ.montaSql( false, "ATRIBUICAO", "AT" );
		lcAtribuicaoQ.setReadOnly( true );
		lcAtribuicaoQ.setQueryCommit( false );
		txtCodAtribVagaQ.setTabelaExterna( lcAtribuicaoQ );
		txtCodAtribVagaQ.setFK( true );
		txtCodAtribVagaQ.setListaCampos( lcAtribuicaoQ );
		txtDescAtribVagaQ.setListaCampos( lcAtribuicaoQ );
		
		lcAtribuicaoR.add( new GuardaCampo( txtCodAtribVagaR, "CodAtrib", "Cód.Atrib.", ListaCampos.DB_PK, null, false ) );
		lcAtribuicaoR.add( new GuardaCampo( txtDescAtribVagaR, "DescAtrib", "Descrição da atribuição", ListaCampos.DB_SI, false ) );
		lcAtribuicaoR.montaSql( false, "ATRIBUICAO", "AT" );
		lcAtribuicaoR.setReadOnly( true );
		lcAtribuicaoR.setQueryCommit( false );
		txtCodAtribVagaR.setListaCampos( lcAtribuicaoR );
		txtCodAtribVagaR.setTabelaExterna( lcAtribuicaoR );
		txtCodAtribVagaR.setFK( true );
		txtCodAtribVagaR.setListaCampos( lcAtribuicaoR );
		txtDescAtribVagaR.setListaCampos( lcAtribuicaoR );
		
	}
	
	private void montaTela() {		
	
		// Aba geral
		
		adicTab( "Geral", panelGeral ); 
		setPainel( panelGeral );

		adicCampo( txtCodVaga, 7, 20, 90, 20, "CodVaga", "Cód.Vaga", ListaCampos.DB_PK, true );		
		
		adicCampo( txtCodEmpr, 100, 20, 87, 20, "CodEmpr", "Cód.Empreg.", ListaCampos.DB_FK, txtNomeEmpr, true );		
		adicDescFK( txtNomeEmpr, 190, 20, 330, 20, "NomeEmpr", "Nome do empregador" );
		
		adicCampo( txtCodFuncaoVaga, 7, 60, 90, 20, "CodFunc", "Cód.Função", ListaCampos.DB_FK, txtDescFuncaoVaga, true );		
		adicDescFK( txtDescFuncaoVaga, 100, 60, 420, 20, "DescFunc", "Nome da função" );
		
		adicCampo( txtCodTurnoVaga, 7, 100, 90, 20, "CodTurno", "Cód.Turno", ListaCampos.DB_FK, txtDescTurnoVaga, true );		
		adicDescFK( txtDescTurnoVaga, 100, 100, 420, 20, "DescTurno", "Descrição do turno" );			

		adicCampo( txtFaixaSalIni, 7, 140, 150, 20, "FaixaSalIni", "Salário inicial", ListaCampos.DB_SI, false );		
		adicCampo( txtFaixaSalFim, 160, 140, 150, 20, "FaixaSalFim", "Salário final", ListaCampos.DB_SI, false );
				
		// Fim da aba geral
						
		setListaCampos( true, "VAGA", "RH" );
		lcCampos.setQueryInsert( false );
		
		// Aba atribuições Qualificativas
		
		adicTab( "Atribuições Qualificativas", panelAtribQuali ); 
		
		setListaCampos( lcVagaAtribuicaoQ );
		setNavegador( navAtribuicaoQ );
		//navAtribuicaoQ.setAtivo( 6, false );

		panelAtribQuali.add( new JScrollPane( tabAtribuicaoQ ), BorderLayout.CENTER );
		panelAtribQuali.add( panelAtribQualiCampos, BorderLayout.SOUTH );
		
		setPainel( panelAtribQualiCampos );
		
		adicCampo( txtCodAtribVagaQ, 7, 20, 90, 20, "CodAtrib", "Cód.atrib.", ListaCampos.DB_PF, txtDescAtribVagaQ, false );		
		adicDescFK( txtDescAtribVagaQ, 100, 20, 300, 20, "DescAtrib", "Descrição da atribuição" );
		adic( navAtribuicaoQ, 0, 50, 270, 25 );		
		setListaCampos( false, "VAGAATRIBQUALI", "RH" );
		lcVagaAtribuicaoQ.setQueryInsert( false );
		lcVagaAtribuicaoQ.setQueryCommit( false );
		lcVagaAtribuicaoQ.montaTab();
		
		tabAtribuicaoQ.setTamColuna( 335, 1 );
		
		// Fim da aba atribuições qualificativas
		
		
		// Aba atribuições Restritivas
		
		adicTab( "Atribuições Restritivas", panelAtribRestr ); 
		
		setListaCampos( lcVagaAtribuicaoR );
		setNavegador( navAtribuicaoR );
		//navAtribuicaoR.setAtivo( 6, false );

		panelAtribRestr.add( new JScrollPane( tabAtribuicaoR ), BorderLayout.CENTER );
		panelAtribRestr.add( panelAtribRestrCampos, BorderLayout.SOUTH );
		
		setPainel( panelAtribRestrCampos );
		
		adicCampo( txtCodAtribVagaR, 7, 20, 90, 20, "CodAtrib", "Cód.atrib.", ListaCampos.DB_PF, txtDescAtribVagaR, false );		
		adicDescFK( txtDescAtribVagaR, 100, 20, 300, 20, "DescAtrib", "Descrição da atribuição" );
		adic( navAtribuicaoR, 0, 50, 270, 25 );		
		setListaCampos( false, "VAGAATRIBREST", "RH" );
		lcVagaAtribuicaoR.setQueryInsert( false );
		lcVagaAtribuicaoR.setQueryCommit( false );
		lcVagaAtribuicaoR.montaTab();
		
		tabAtribuicaoR.setTamColuna( 335, 1 );

		
		// Fim da aba atribuições restritivas
		
		// Aba cursos		
		
		adicTab( "Cursos requeridos", panelCurso ); 
		setListaCampos( lcVagaCurso );
		setNavegador( navCurso );
		//navAtribuicaoR.setAtivo( 6, false );

		panelCurso.add( new JScrollPane( tabCurso ), BorderLayout.CENTER );
		panelCurso.add( panelCursoCampos, BorderLayout.SOUTH );
		
		setPainel( panelCursoCampos );
		
		adicCampo( txtCodCursoVaga, 7, 20, 90, 20, "CodCurso", "Cód.Curso", ListaCampos.DB_PF, txtDescCursoVaga, false );		
		adicDescFK( txtDescCursoVaga, 100, 20, 300, 20, "DescCurso", "Descrição do Curso" );
		adic( navCurso, 0, 50, 270, 25 );		
		setListaCampos( false, "VAGACURSO", "RH" );
		lcVagaCurso.setQueryInsert( false );
		lcVagaCurso.setQueryCommit( false );
		lcVagaCurso.montaTab();
		
		tabCurso.setTamColuna( 335, 1 );	
		
		// Fim da aba cursos
		
	}

	public void setConexao( Connection cn ) {
 
		super.setConexao( cn );
		lcEmpregador.setConexao( cn );
		lcCurso.setConexao( cn );
		lcVagaCurso.setConexao( cn );
		lcTurno.setConexao( cn );
		lcVagaAtribuicaoQ.setConexao( cn );
		lcVagaAtribuicaoR.setConexao( cn );
		lcAtribuicaoQ.setConexao( cn );
		lcAtribuicaoR.setConexao( cn );
		lcFuncao.setConexao( cn );
	}
}
