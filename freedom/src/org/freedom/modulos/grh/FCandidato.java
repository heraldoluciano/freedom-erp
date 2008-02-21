/**
 * @version 19/02/2008 <BR>
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
 * Tela de cadastro de cadidatos.
 * 
 */

package org.freedom.modulos.grh;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.sql.Connection;
import java.util.Vector;

import javax.swing.JScrollPane;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextAreaPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Navegador;
import org.freedom.componentes.Tabela;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FTabDados;

public class FCandidato extends FTabDados {

	private static final long serialVersionUID = 1L;
	
	private final JPanelPad panelGeral = new JPanelPad();
	
	private final JPanelPad panelOutrosEmpregos = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 1, 1 ) );
	
	private final JPanelPad panelCurso = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );
	
	private final JPanelPad panelCursoCampos = new JPanelPad( 0, 80 );
	
	private final JPanelPad panelAtribuicao = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );
	
	private final JPanelPad panelAtribuicaoCampos = new JPanelPad( 0, 80 );
	
	private final JPanelPad panelFuncao = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );
	
	private final JPanelPad panelFuncaoCampos = new JPanelPad( 0, 80 );
	
	// GERAL

	private final JTextFieldPad txtCodCand = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private final JTextFieldPad txtNomeCand = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private JRadioGroup<String, String> rgSexo = null;
	
	private final JTextFieldPad txtCPFCand = new JTextFieldPad( JTextFieldPad.TP_STRING, 11, 0 );

	private final JTextFieldPad txtRGCand = new JTextFieldPad( JTextFieldPad.TP_STRING, 10, 0 );

	private final JTextFieldPad txtPISPasepCand = new JTextFieldPad( JTextFieldPad.TP_STRING, 20, 0 );

	private final JTextFieldPad txtCTPSCand = new JTextFieldPad( JTextFieldPad.TP_STRING, 20, 0 );

	private final JTextFieldPad txtSSPCand = new JTextFieldPad( JTextFieldPad.TP_STRING, 10, 0 );

	private final JTextFieldPad txtTituloCand = new JTextFieldPad( JTextFieldPad.TP_STRING, 15, 0 );

	private final JTextFieldPad txtPretensaoCand = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, Aplicativo.casasDecFin );	

	private final JTextFieldPad txtCodEstCivilCand = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private final JTextFieldFK txtDescEstCivilCand = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	private final JTextFieldPad txtEndCand = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );
	
	private final JTextFieldPad txtNumCand = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private final JTextFieldPad txtBairroCand = new JTextFieldPad( JTextFieldPad.TP_STRING, 30, 0 );
	
	private final JTextFieldPad txtCidCand = new JTextFieldPad( JTextFieldPad.TP_STRING, 30, 0 );
	
	private final JTextFieldPad txtUfCand = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );
	
	private final JTextFieldPad txtCepCand = new JTextFieldPad( JTextFieldPad.TP_STRING, 8, 0 );	
	
	private final JTextFieldPad txtDDDCand = new JTextFieldPad( JTextFieldPad.TP_STRING, 4, 0 );
	
	private final JTextFieldPad txtFoneCand = new JTextFieldPad( JTextFieldPad.TP_STRING, 12, 0 );
	
	private final JTextFieldPad txtCelCand = new JTextFieldPad( JTextFieldPad.TP_STRING, 12, 0 );
	
	private final JTextFieldPad txtNascimentoCand = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );
	
	private final JTextFieldPad txtEmailCand = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );
	
	private final JTextAreaPad txaOutrosempregos = new JTextAreaPad( 1000 );
	
	// FIM GERAL.
	
	// CURSOS

	private final JTextFieldPad txtCodCursoCand = new JTextFieldPad( JTextFieldPad.TP_STRING, 15, 0 );
	
	private final JTextFieldFK txtDescCursoCand = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	// FIM CURSOS
	
	// ATRIBUIÇÕES

	private final JTextFieldPad txtCodAtribCand = new JTextFieldPad( JTextFieldPad.TP_STRING, 15, 0 );
	
	private final JTextFieldFK txtDescAtribCand = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	// FIM ATRIBUIÇÕES.
	
	// FUNÇÕES

	private final JTextFieldPad txtCodFuncaoCand = new JTextFieldPad( JTextFieldPad.TP_STRING, 15, 0 );
	
	private final JTextFieldFK txtDescFuncaoCand = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	// FIM FUNÇÕES.

	private final Tabela tabCurso = new Tabela();

	private final Tabela tabAtribuicao = new Tabela();

	private final Tabela tabFuncao = new Tabela();

	private final ListaCampos lcEstadoCivil = new ListaCampos( this, "ES" );

	private final ListaCampos lcCurso = new ListaCampos( this );

	private final ListaCampos lcAtribuicao = new ListaCampos( this );

	private final ListaCampos lcFuncao = new ListaCampos( this );

	private final Navegador navCurso = new Navegador( true );

	private final Navegador navAtribuicao = new Navegador( true );

	private final Navegador navFuncao = new Navegador( true );
	

	public FCandidato() {

		super( false );
		setTitulo( "Cadastro de Candidatos" );
		setAtribos( 50, 50, 430, 520 );
		
		//lcCurso.setMaster( lcCampos );
		//lcCampos.adicDetalhe( lcCurso );
		//lcCurso.setTabela( tabCurso );

		lcAtribuicao.setMaster( lcCampos );
		lcCampos.adicDetalhe( lcAtribuicao );
		lcAtribuicao.setTabela( tabAtribuicao );

		lcFuncao.setMaster( lcCampos );
		lcCampos.adicDetalhe( lcFuncao );
		lcFuncao.setTabela( tabFuncao );

		montaRadioGroups();
		montaListaCampos();
		montaTela();
		
		setImprimir( false );
	}
	
	private void montaRadioGroups() {

		Vector<String> vLabs = new Vector<String>();
		Vector<String> vVals = new Vector<String>();
		
		vLabs.addElement( "Masculino" );
		vLabs.addElement( "Feminino" );
		vVals.addElement( "M" );
		vVals.addElement( "F" );
		
		rgSexo = new JRadioGroup<String, String>( 1, 2, vLabs, vVals );
		rgSexo.setVlrString( "M" );
	}
	
	private void montaListaCampos() {

		lcEstadoCivil.add( new GuardaCampo( txtCodEstCivilCand, "CodEstCivil", "Cód.est.cívil", ListaCampos.DB_PK, true ) );
		lcEstadoCivil.add( new GuardaCampo( txtDescEstCivilCand, "DescEstCivil", "Descrição do estado cívil", ListaCampos.DB_SI, false ) );
		lcEstadoCivil.montaSql( false, "ESTCIVIL", "SG" );
		lcEstadoCivil.setQueryCommit( false );
		lcEstadoCivil.setReadOnly( true );
		txtCodEstCivilCand.setTabelaExterna( lcEstadoCivil );

		lcCurso.add( new GuardaCampo( txtCodCursoCand, "CodCurso", "Cód.curso", ListaCampos.DB_PK, null, false ) );
		lcCurso.add( new GuardaCampo( txtDescCursoCand, "DescCurso", "Descrição do curso", ListaCampos.DB_SI, false ) );
		lcCurso.montaSql( false, "CURSO", "RH" );
		lcCurso.setReadOnly( true );
		lcCurso.setQueryCommit( false );
		txtCodCursoCand.setListaCampos( lcCurso );
		txtCodCursoCand.setTabelaExterna( lcCurso );

		lcAtribuicao.add( new GuardaCampo( txtCodAtribCand, "CodAtrib", "Cód.atrib.", ListaCampos.DB_PK, null, false ) );
		lcAtribuicao.add( new GuardaCampo( txtDescAtribCand, "DescAtrib", "Descrição da atribuição", ListaCampos.DB_SI, false ) );
		lcAtribuicao.montaSql( false, "ATRIBUICAO", "AT" );
		lcAtribuicao.setReadOnly( true );
		lcAtribuicao.setQueryCommit( false );
		txtCodAtribCand.setListaCampos( lcAtribuicao );
		txtCodAtribCand.setTabelaExterna( lcAtribuicao );

		lcFuncao.add( new GuardaCampo( txtCodFuncaoCand, "CodFunc", "Cód.função", ListaCampos.DB_PK, null, false ) );
		lcFuncao.add( new GuardaCampo( txtDescFuncaoCand, "DescFunc", "Descrição da função", ListaCampos.DB_SI, false ) );
		lcFuncao.montaSql( false, "FUNCAO", "RH" );
		lcFuncao.setReadOnly( true );
		lcFuncao.setQueryCommit( false );
		txtCodFuncaoCand.setListaCampos( lcFuncao );
		txtCodFuncaoCand.setTabelaExterna( lcFuncao );
	}
	
	private void montaTela() {		

		txtCPFCand.setMascara( JTextFieldPad.MC_CPF );
		txtRGCand.setMascara( JTextFieldPad.MC_RG );
		txtCepCand.setMascara( JTextFieldPad.MC_CEP );
		txtFoneCand.setMascara( JTextFieldPad.MC_FONE );
		txtCelCand.setMascara( JTextFieldPad.MC_FONE );
		
		// Aba geral
		
		adicTab( "Geral", panelGeral ); 
		setPainel( panelGeral );

		adicCampo( txtCodCand, 7, 20, 90, 20, "CodCand", "Cód.candidato", ListaCampos.DB_PK, true );		
		adicCampo( txtNomeCand, 100, 20, 300, 20, "NomeCand", "Nome do candidato", ListaCampos.DB_SI, true );		
		adicDB( rgSexo, 7, 60, 393, 30, "SexoCand", "Sexo", false );	
		adicCampo( txtCPFCand, 7, 110, 195, 20, "CpfCli", "CPF", ListaCampos.DB_SI, false );		
		adicCampo( txtRGCand, 205, 110, 195, 20, "RgCand", "RG", ListaCampos.DB_SI, false );	
		adicCampo( txtPISPasepCand, 7, 150, 195, 20, "PISPasepCand", "PIS/Pasep", ListaCampos.DB_SI, false );		
		adicCampo( txtCTPSCand, 205, 150, 195, 20, "CTPSCand", "CTPS", ListaCampos.DB_SI, false );	
		adicCampo( txtSSPCand, 7, 190, 90, 20, "SSPCand", "SSP", ListaCampos.DB_SI, false );		
		adicCampo( txtTituloCand, 100, 190, 150, 20, "TitEleitCand", "Titulo de eleitor", ListaCampos.DB_SI, false );	
		adicCampo( txtPretensaoCand, 253, 190, 147, 20, "PretensaoSal", "Pretensão", ListaCampos.DB_SI, false );		
		adicCampo( txtCodEstCivilCand, 7, 230, 90, 20, "CodEstCivil", "Cód.est.cívil", ListaCampos.DB_FK, txtDescEstCivilCand, false );		
		adicDescFK( txtDescEstCivilCand, 100, 230, 300, 20, "DescEstCivil", "Descrição do estado cívil" );			
		adicCampo( txtEndCand, 7, 270, 323, 20, "EndCand", "Endereço", ListaCampos.DB_SI, false );		
		adicCampo( txtNumCand, 333, 270, 67, 20, "NumCand", "Número", ListaCampos.DB_SI, false );		
		adicCampo( txtBairroCand, 7, 310, 145, 20, "BairCand", "Bairro", ListaCampos.DB_SI, false );		
		adicCampo( txtCidCand, 155, 310, 175, 20, "CidCand", "Cidade", ListaCampos.DB_SI, false );		
		//adicCampo( txtUfCand, 303, 310, 27, 20, "UfCand", "UF", ListaCampos.DB_SI, false );		
		adicCampo( txtCepCand, 333, 310, 67, 20, "CepCand", "CEP", ListaCampos.DB_SI, false );
		adicCampo( txtDDDCand, 7, 350, 60, 20, "DDDCand", "DDD", ListaCampos.DB_SI, false );		
		adicCampo( txtFoneCand, 70, 350, 117, 20, "FoneCand", "Fone", ListaCampos.DB_SI, false );	
		adicCampo( txtCelCand, 190, 350, 110, 20, "CelCand", "Celular", ListaCampos.DB_SI, false );	
		adicCampo( txtNascimentoCand, 303, 350, 97, 20, "DtNascCand", "Nascimento", ListaCampos.DB_SI, false );			
		adicCampo( txtEmailCand, 7, 390, 393, 20, "EmailCand", "e-mail", ListaCampos.DB_SI, false );
		
		// Fim da aba geral
		
		// Aba outros empregos
				
		adicTab( "Outros empregos", panelOutrosEmpregos ); 
		setPainel( panelOutrosEmpregos );

		adicDBLiv( txaOutrosempregos, "OutrosEmpregos", "Outros empregos", false );
		panelOutrosEmpregos.add( new JScrollPane( txaOutrosempregos ) );

		setListaCampos( true, "CANDIDATO", "RH" );
		lcCampos.setQueryInsert( false );
		
		// Fim da aba outros empregos
		
		// Aba cursos		
		/*
		adicTab( "Cursos", panelCurso ); 
		setPainel( panelCurso );		

		setListaCampos( lcCurso );
		setNavegador( navCurso );
		navCurso.setAtivo( 6, false );

		panelCurso.add( new JScrollPane( tabCurso ), BorderLayout.CENTER );
		panelCurso.add( panelCursoCampos, BorderLayout.SOUTH );
		
		setPainel( panelCursoCampos );
		
		adicCampo( txtCodCursoCand, 7, 20, 90, 20, "CodCurso", "Cód.curso", ListaCampos.DB_PF, txtDescCursoCand, false );		
		adicDescFK( txtDescCursoCand, 100, 20, 300, 20, "DescCurso", "Descrição do curso" );
		adic( navCurso, 0, 50, 270, 25 );		
		setListaCampos( false, "CANDIDATOCURSO", "RH" );
		lcCurso.setQueryInsert( false );
		lcCurso.setQueryCommit( false );
		lcCurso.montaTab();
		*/
		// Fim da aba cursos
		
		// Aba atribuições
		
		adicTab( "Atribuições", panelAtribuicao ); 
		
		setListaCampos( lcAtribuicao );
		setNavegador( navAtribuicao );
		navAtribuicao.setAtivo( 6, false );

		panelAtribuicao.add( new JScrollPane( tabAtribuicao ), BorderLayout.CENTER );
		panelAtribuicao.add( panelAtribuicaoCampos, BorderLayout.SOUTH );
		
		setPainel( panelAtribuicaoCampos );
		
		adicCampo( txtCodAtribCand, 7, 20, 90, 20, "CodAtrib", "Cód.atrib.", ListaCampos.DB_PK, false );		
		adicDescFK( txtDescAtribCand, 100, 20, 300, 20, "DescAtrib", "Descrição da atribuição" );
		adic( navAtribuicao, 0, 50, 270, 25 );		
		setListaCampos( false, "CANDIDATOATRIB", "RH" );
		lcAtribuicao.setQueryInsert( false );
		lcAtribuicao.setQueryCommit( false );
		lcAtribuicao.montaTab();
		
		// Fim da aba atribuições
		
		// Aba funções
		
		adicTab( "Funções", panelFuncao ); 
		
		setListaCampos( lcFuncao );
		setNavegador( navFuncao );
		navFuncao.setAtivo( 6, false );

		panelFuncao.add( new JScrollPane( tabFuncao ), BorderLayout.CENTER );
		panelFuncao.add( panelFuncaoCampos, BorderLayout.SOUTH );
		
		setPainel( panelFuncaoCampos );
		
		adicCampo( txtCodFuncaoCand, 7, 20, 90, 20, "CodFunc", "Cód.função", ListaCampos.DB_PK, false );		
		adicDescFK( txtDescFuncaoCand, 100, 20, 300, 20, "DescFunc", "Descrição da função" );
		adic( navFuncao, 0, 50, 270, 25 );		
		setListaCampos( false, "CANDIDATOFUNC", "RH" );
		lcFuncao.setQueryInsert( false );
		lcFuncao.setQueryCommit( false );
		lcFuncao.montaTab();
		
		// Fim da aba atribuições
	}

	public void setConexao( Connection cn ) {

		super.setConexao( cn );
		lcEstadoCivil.setConexao( cn );
		lcCurso.setConexao( cn );
		lcAtribuicao.setConexao( cn );
		lcFuncao.setConexao( cn );
	}
}
