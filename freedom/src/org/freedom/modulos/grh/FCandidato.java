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
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.JScrollPane;

import net.sf.jasperreports.engine.JasperPrintManager;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextAreaPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Navegador;
import org.freedom.componentes.Tabela;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FPrinterJob;
import org.freedom.telas.FTabDados;

public class FCandidato extends FTabDados {

	private static final long serialVersionUID = 1L;
	
	private final JPanelPad panelGeral = new JPanelPad();
	
	private final JPanelPad panelOutrosEmpregos = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 1, 1 ) );

	private final JPanelPad panelOBS = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 1, 1 ) );
	
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

	private final JTextFieldPad txtCodEstCivilCand = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );
	
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
	
	private final JTextAreaPad txaOBS = new JTextAreaPad( 1000 );
	
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

	private final JTextFieldPad txtSeqFuncaoCand = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private final JTextFieldPad txtCodFuncaoCand = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private final JTextFieldFK txtDescFuncaoCand = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	// FIM FUNÇÕES.

	private final Tabela tabCurso = new Tabela();

	private final Tabela tabAtribuicao = new Tabela();

	private final Tabela tabFuncao = new Tabela();

	private final ListaCampos lcEstadoCivil = new ListaCampos( this, "ES" );

	private final ListaCampos lcCurso = new ListaCampos( this, "CS" );

	private final ListaCampos lcCursoCand = new ListaCampos( this );

	private final ListaCampos lcAtribuicao = new ListaCampos( this, "AT" );

	private final ListaCampos lcAtribuicaoCand = new ListaCampos( this );

	private final ListaCampos lcFuncao = new ListaCampos( this, "FC" );

	private final ListaCampos lcFuncaoCand = new ListaCampos( this );

	private final Navegador navCurso = new Navegador( true );

	private final Navegador navAtribuicao = new Navegador( true );

	private final Navegador navFuncao = new Navegador( true );
	

	public FCandidato() {

		super( true );
		setTitulo( "Cadastro de Candidatos" );
		setAtribos( 50, 50, 550, 520 );
		
		lcCursoCand.setMaster( lcCampos );
		lcCampos.adicDetalhe( lcCursoCand );
		lcCursoCand.setTabela( tabCurso );

		lcAtribuicaoCand.setMaster( lcCampos );
		lcCampos.adicDetalhe( lcAtribuicaoCand );
		lcAtribuicaoCand.setTabela( tabAtribuicao );

		lcFuncaoCand.setMaster( lcCampos );
		lcCampos.adicDetalhe( lcFuncaoCand );
		lcFuncaoCand.setTabela( tabFuncao );

		montaRadioGroups();
		montaListaCampos();
		montaTela();
		
		btImp.addActionListener( this );
		btPrevimp.addActionListener( this );

		setImprimir( true );
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

		lcCurso.add( new GuardaCampo( txtCodCursoCand, "CodCurso", "Cód.curso", ListaCampos.DB_PK, txtDescCursoCand, false ) );
		lcCurso.add( new GuardaCampo( txtDescCursoCand, "DescCurso", "Descrição do curso", ListaCampos.DB_SI, false ) );
		lcCurso.montaSql( false, "CURSO", "RH" );
		lcCurso.setReadOnly( true );
		lcCurso.setQueryCommit( false );
		txtCodCursoCand.setTabelaExterna( lcCurso );
		txtCodCursoCand.setFK( true );
		txtCodCursoCand.setListaCampos( lcCurso );
		txtDescCursoCand.setListaCampos( lcCurso );

		lcAtribuicao.add( new GuardaCampo( txtCodAtribCand, "CodAtrib", "Cód.atrib.", ListaCampos.DB_PK, txtDescAtribCand, false ) );
		lcAtribuicao.add( new GuardaCampo( txtDescAtribCand, "DescAtrib", "Descrição da atribuição", ListaCampos.DB_SI, false ) );
		lcAtribuicao.montaSql( false, "ATRIBUICAO", "AT" );
		lcAtribuicao.setReadOnly( true );
		lcAtribuicao.setQueryCommit( false );
		txtCodAtribCand.setTabelaExterna( lcAtribuicao );
		txtCodAtribCand.setFK( true );
		txtCodAtribCand.setListaCampos( lcAtribuicao );
		txtDescAtribCand.setListaCampos( lcAtribuicao );

		lcFuncao.add( new GuardaCampo( txtCodFuncaoCand, "CodFunc", "Cód.função", ListaCampos.DB_PK, txtDescFuncaoCand, false ) );
		lcFuncao.add( new GuardaCampo( txtDescFuncaoCand, "DescFunc", "Descrição da função", ListaCampos.DB_SI, false ) );
		lcFuncao.montaSql( false, "FUNCAO", "RH" );
		lcFuncao.setReadOnly( true );
		lcFuncao.setQueryCommit( false );
		txtCodFuncaoCand.setTabelaExterna( lcFuncao );
		txtCodFuncaoCand.setFK( true );
		txtCodFuncaoCand.setListaCampos( lcFuncao );
		txtDescFuncaoCand.setListaCampos( lcFuncao );
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
		adicCampo( txtCPFCand, 7, 110, 195, 20, "CpfCand", "CPF", ListaCampos.DB_SI, false );		
		adicCampo( txtRGCand, 205, 110, 195, 20, "RgCand", "RG", ListaCampos.DB_SI, false );	
		adicCampo( txtPISPasepCand, 7, 150, 195, 20, "PISPasepCand", "PIS/Pasep", ListaCampos.DB_SI, false );		
		adicCampo( txtCTPSCand, 205, 150, 195, 20, "CTPSCand", "CTPS", ListaCampos.DB_SI, false );	
		adicCampo( txtSSPCand, 7, 190, 90, 20, "SSPCand", "SSP", ListaCampos.DB_SI, false );		
		adicCampo( txtTituloCand, 100, 190, 150, 20, "TitEleitCand", "Titulo de eleitor", ListaCampos.DB_SI, false );	
		adicCampo( txtPretensaoCand, 253, 190, 147, 20, "PretensaoSal", "Pretensão salarial", ListaCampos.DB_SI, false );		
		adicCampo( txtCodEstCivilCand, 7, 230, 90, 20, "CodEstCivil", "Cód.est.cívil", ListaCampos.DB_FK, txtDescEstCivilCand, false );		
		adicDescFK( txtDescEstCivilCand, 100, 230, 300, 20, "DescEstCivil", "Descrição do estado cívil" );			
		adicCampo( txtEndCand, 7, 270, 323, 20, "EndCand", "Endereço", ListaCampos.DB_SI, false );		
		adicCampo( txtNumCand, 333, 270, 67, 20, "NumCand", "Número", ListaCampos.DB_SI, false );		
		adicCampo( txtBairroCand, 7, 310, 145, 20, "BairCand", "Bairro", ListaCampos.DB_SI, false );		
		adicCampo( txtCidCand, 155, 310, 145, 20, "CidCand", "Cidade", ListaCampos.DB_SI, false );		
		adicCampo( txtUfCand, 303, 310, 27, 20, "UfCand", "UF", ListaCampos.DB_SI, false );		
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

		// Aba outros OBS 
		
		adicTab( "Observações gerais", panelOBS ); 
		setPainel( panelOBS );

		adicDBLiv( txaOBS, "OBSCAND", "Observações", false );
		panelOBS.add( new JScrollPane( txaOBS ) );

		setListaCampos( true, "CANDIDATO", "RH" );
		lcCampos.setQueryInsert( false );
		
		// Fim da aba outros empregos

		
		
		
		
		
		// Aba cursos		
		
		adicTab( "Cursos", panelCurso ); 
		setPainel( panelCurso );		

		setListaCampos( lcCursoCand );
		setNavegador( navCurso );
		navCurso.setAtivo( 6, false );

		panelCurso.add( new JScrollPane( tabCurso ), BorderLayout.CENTER );
		panelCurso.add( panelCursoCampos, BorderLayout.SOUTH );
		
		setPainel( panelCursoCampos );
		
		adicCampo( txtCodCursoCand, 7, 20, 90, 20, "CodCurso", "Cód.curso", ListaCampos.DB_PF, txtDescCursoCand, false );		
		adicDescFK( txtDescCursoCand, 100, 20, 300, 20, "DescCurso", "Descrição do curso" );
		adic( navCurso, 0, 50, 270, 25 );		
		setListaCampos( false, "CANDIDATOCURSO", "RH" );
		lcCursoCand.setQueryInsert( false );
		lcCursoCand.setQueryCommit( false );
		lcCursoCand.montaTab();
		
		tabCurso.setTamColuna( 335, 1 );
		
		// Fim da aba cursos
		
		// Aba atribuições
		
		adicTab( "Atribuições", panelAtribuicao ); 
		
		setListaCampos( lcAtribuicaoCand );
		setNavegador( navAtribuicao );
		navAtribuicao.setAtivo( 6, false );

		panelAtribuicao.add( new JScrollPane( tabAtribuicao ), BorderLayout.CENTER );
		panelAtribuicao.add( panelAtribuicaoCampos, BorderLayout.SOUTH );
		
		setPainel( panelAtribuicaoCampos );
		
		adicCampo( txtCodAtribCand, 7, 20, 90, 20, "CodAtrib", "Cód.atrib.", ListaCampos.DB_PF, txtDescAtribCand, false );		
		adicDescFK( txtDescAtribCand, 100, 20, 300, 20, "DescAtrib", "Descrição da atribuição" );
		adic( navAtribuicao, 0, 50, 270, 25 );		
		setListaCampos( false, "CANDIDATOATRIB", "RH" );
		lcAtribuicaoCand.setQueryInsert( false );
		lcAtribuicaoCand.setQueryCommit( false );
		lcAtribuicaoCand.montaTab();
		
		tabAtribuicao.setTamColuna( 335, 1 );
		
		// Fim da aba atribuições
		
		// Aba funções
		
		adicTab( "Funções", panelFuncao ); 
		
		setListaCampos( lcFuncaoCand );
		setNavegador( navFuncao );
		navFuncao.setAtivo( 6, false );

		panelFuncao.add( new JScrollPane( tabFuncao ), BorderLayout.CENTER );
		panelFuncao.add( panelFuncaoCampos, BorderLayout.SOUTH );
		
		setPainel( panelFuncaoCampos );
		
		adicCampo( txtSeqFuncaoCand, 7, 20, 40, 20, "SeqCandFunc", "Seq.", ListaCampos.DB_PK, false );
		adicCampo( txtCodFuncaoCand, 50, 20, 90, 20, "CodFunc", "Cód.função", ListaCampos.DB_PF, txtDescFuncaoCand, false );		
		adicDescFK( txtDescFuncaoCand, 143, 20, 257, 20, "DescFunc", "Descrição da função" );
		adic( navFuncao, 0, 50, 270, 25 );		
		setListaCampos( false, "CANDIDATOFUNC", "RH" );
		lcFuncaoCand.setQueryInsert( false );
		lcFuncaoCand.setQueryCommit( false );
		lcFuncaoCand.montaTab();

		tabFuncao.setTamColuna( 35, 0 );
		tabFuncao.setTamColuna( 300, 2 );
		
		// Fim da aba atribuições
	}

	@ Override
	public void actionPerformed( ActionEvent e ) {

		super.actionPerformed( e );
		
		if ( e.getSource() == btPrevimp ) {
			imprimir( true );
		}
		else if ( e.getSource() == btImp ) {
			imprimir( false );
		}
	}

	private void imprimir( boolean bVisualizar ) {
		
		FPrinterJob dlGr = null;
		HashMap<String, Object> hParam = new HashMap<String, Object>();

		hParam.put( "CODEMP", Aplicativo.iCodEmp );
		hParam.put( "CODFILIAL", ListaCampos.getMasterFilial( "RHCANDIDATOFUNC" ) );

		dlGr = new FPrinterJob( "relatorios/grhCandidato.jasper", "Lista de Candidatos", "", this, hParam, con, null, false );

		if ( bVisualizar ) {
			dlGr.setVisible( true );
		}
		else {
			try {
				JasperPrintManager.printReport( dlGr.getRelatorio(), true );
			} catch ( Exception e ) {
				e.printStackTrace();
				Funcoes.mensagemErro( this, "Erro na geração do relátorio!" + e.getMessage(), true, con, e );
			}
		}
	}

	public void setConexao( Connection cn ) {

		super.setConexao( cn );
		lcEstadoCivil.setConexao( cn );
		lcCurso.setConexao( cn );
		lcAtribuicao.setConexao( cn );
		lcFuncao.setConexao( cn );
		lcCursoCand.setConexao( cn );
		lcAtribuicaoCand.setConexao( cn );
		lcFuncaoCand.setConexao( cn );
	}
}
