/**
 * @version 31/03/2008 <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FGerencVagas.java <BR>
 * 
 * Este programa é licenciado de acordo com a LPG-PC (Licença Pública Geral para Programas de Computador), <BR>
 * versão 2.1.0 ou qualquer versão posterior. <BR>
 * A LPG-PC deve acompanhar todas PUBLICAÇÕES, DISTRIBUIÇÕES e REPRODUÇÕES deste Programa. <BR>
 * Caso uma cópia da LPG-PC não esteja disponível junto com este Programa, você pode contatar <BR>
 * o LICENCIADOR ou então pegar uma cópia em: <BR>
 * Licença: http://www.lpg.adv.br/licencas/lpgpc.rtf <BR>
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa é preciso estar <BR>
 * de acordo com os termos da LPG-PC <BR> <BR>
 *
 * Gerenciamento de vagas.
 * 
 */

package org.freedom.modulos.grh;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import org.freedom.acao.TabelaEditEvent;
import org.freedom.acao.TabelaEditListener;
import org.freedom.bmps.Icone;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Tabela;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FTabDados;

public class FGerencVagas extends FTabDados implements ActionListener, TabelaEditListener, MouseListener {

	private static final long serialVersionUID = 1L;
	
	private JPanelPad pinCandVagas =  new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );
//	private JPanelPad pinVagasCand = new JPanelPad( 680, 200 );
	private JPanelPad pinVagasCand = new JPanelPad(JPanelPad.TP_JPANEL, new BorderLayout()); 
	private JPanelPad pinCabVaga = new JPanelPad( 0, 94 );
	private JPanelPad pinCabCand = new JPanelPad( 0, 94 );
	private JPanelPad pnCabVaga = new JPanelPad(JPanelPad.TP_JPANEL,new BorderLayout());
	private JPanelPad pnCabCand = new JPanelPad(JPanelPad.TP_JPANEL,new BorderLayout());
	private JTextFieldPad txtCodVaga = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
	private JTextFieldPad txtCodCand = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
	private final JTextFieldFK txtNomeCand = new JTextFieldFK( JTextFieldPad.TP_STRING, 60, 0 );
	private final JTextFieldFK txtCodEmpr = new JTextFieldFK( JTextFieldPad.TP_INTEGER, 8, 0 );	
	private final JTextFieldFK txtNomeEmpr = new JTextFieldFK( JTextFieldPad.TP_STRING, 60, 0 );
	private final JTextFieldFK txtCodFunc = new JTextFieldFK( JTextFieldPad.TP_INTEGER, 8, 0 );	
	private final JTextFieldFK txtDescFunc = new JTextFieldFK( JTextFieldPad.TP_STRING, 60, 0 );
	private final JTextFieldFK txtFaixaSalIniVaga = new JTextFieldFK( JTextFieldPad.TP_DECIMAL, 15, 2 );
	private final JTextFieldFK txtFaixaSalFimVaga = new JTextFieldFK( JTextFieldPad.TP_DECIMAL, 15, 2 );

	private final JTextFieldFK txtFaixaSalIniCand = new JTextFieldFK( JTextFieldPad.TP_DECIMAL, 15, 2 );
	private final JTextFieldFK txtFaixaSalFimCand = new JTextFieldFK( JTextFieldPad.TP_DECIMAL, 15, 2 );

	private Tabela tabCand = new Tabela(); 
	private Tabela tabVagas = new Tabela();
	private JButton btRefreshCand = new JButton(Icone.novo("btExecuta.gif"));
	private JButton btRefreshVagas = new JButton(Icone.novo("btExecuta.gif"));
	private JButton btEncaminharCand = new JButton("Encaminhar", Icone.novo("btEncaminharCand.gif"));
	private JButton btEfetivarCand = new JButton("Efetivar", Icone.novo("btEfetivarCand.gif"));
	private ImageIcon imgEditaCampo = Icone.novo("clEditar.gif");
	private JScrollPane spnTabCand = new JScrollPane(tabCand);
	private JScrollPane spnTabVagas = new JScrollPane(tabVagas);
	private ListaCampos lcVaga = new ListaCampos(this);
	private ListaCampos lcCandidato = new ListaCampos(this);
	private ListaCampos lcEmpregador = new ListaCampos(this,"EM");
	private ListaCampos lcFuncao = new ListaCampos(this,"FC");

	private JCheckBoxPad cbQualificacoesVaga = new JCheckBoxPad("Qualificações",new Boolean(true),new Boolean(false));
	private JCheckBoxPad cbRestricoesVaga = new JCheckBoxPad("Restrições",new Boolean(true),new Boolean(false));
	private JCheckBoxPad cbQualificacoesCand = new JCheckBoxPad("Qualificações",new Boolean(true),new Boolean(false));
	private JCheckBoxPad cbRestricoesCand = new JCheckBoxPad("Restrições",new Boolean(true),new Boolean(false));

	
	private JCheckBoxPad cbCursosVaga = new JCheckBoxPad("Cursos",new Boolean(true),new Boolean(false));
	private JCheckBoxPad cbExperienciaVaga = new JCheckBoxPad("Experiência",new Boolean(true),new Boolean(false));
	private JCheckBoxPad cbCursosCand = new JCheckBoxPad("Cursos",new Boolean(true),new Boolean(false));
	private JCheckBoxPad cbExperienciaCand = new JCheckBoxPad("Experiência",new Boolean(true),new Boolean(false));

	
	private JCheckBoxPad cbFaixaSalarialVaga = new JCheckBoxPad("Faixa salarial de:",new Boolean(true),new Boolean(false));
	private JCheckBoxPad cbFaixaSalarialCand = new JCheckBoxPad("Faixa salarial de:",new Boolean(true),new Boolean(false));
	private JCheckBoxPad cbDisponibilidade = new JCheckBoxPad("Disponíveis",new Boolean(true),new Boolean(false));
	private JCheckBoxPad cbEntrevistados = new JCheckBoxPad("Entrevistados",new Boolean(true),new Boolean(false));
	private JCheckBoxPad cbEnvolvimento = new JCheckBoxPad("Envolvidos",new Boolean(true),new Boolean(false));	
	
	private final JCheckBoxPad cbSelecionado = new JCheckBoxPad( "Seleção", "S", "N" );
	
	private BigDecimal bVlrAceito = new BigDecimal("0");
	private BigDecimal bVlrAprovado = new BigDecimal("0");
	private BigDecimal bVlrTotal = new BigDecimal("0");
	
	private JLabelPad lbFiltrosCand = new JLabelPad( " Filtros" );
	private JLabelPad lbFiltrosVaga = new JLabelPad( " Filtros" );
	private JPanelPad pinFiltrosCand = new JPanelPad( 300, 150 );
	private JPanelPad pinFiltrosVaga = new JPanelPad( 300, 150 );
	private JPanelPad pinLbFiltrosCand = new JPanelPad( 53, 15 );
	private JPanelPad pinLbFiltrosVaga = new JPanelPad( 53, 15 );
	private JPanelPad pinRod = new JPanelPad(685,39);
	private Map<String,String> status = new HashMap<String, String>();
	
	private ImageIcon imgIndiponivel = Icone.novo( "clIndisponivel.gif" );
	private ImageIcon imgDisponivel = Icone.novo( "clDisponivel.gif" );
	private ImageIcon imgOcupado = Icone.novo( "clIndisponivelParc.gif" );
	private ImageIcon imgEncaminhado = Icone.novo( "clEfetivar.gif" );
	private ImageIcon imgEfetivado = Icone.novo( "clEfetivado.gif" );
	private ImageIcon imgEntrevistado = Icone.novo( "clEntrevistado.gif" );
	private ImageIcon imgStatus = null;
	
	private Font fontLegenda =  new Font("Arial", Font.PLAIN, 9);		
	private Color corLegenda = new Color(120,120,120); 
	
	private JLabelPad lbTxtIndisponivel = new JLabelPad("Indisponível");
	private JLabelPad lbTxtOcupado = new JLabelPad("Ocupado");
	private JLabelPad lbTxtDisponivel = new JLabelPad("Disponível");
	private JLabelPad lbTxtEncaminhado = new JLabelPad("Encaminhado");
	private JLabelPad lbTxtEfetivado = new JLabelPad("Efetivado");
	private JLabelPad lbTxtEntrevistado = new JLabelPad("Entrevistado");
	
	private JLabelPad lbImgIndisponivel = new JLabelPad(imgIndiponivel);
	private JLabelPad lbImgEntrevistado = new JLabelPad(imgEntrevistado);
	private JLabelPad lbImgDisponivel = new JLabelPad(imgDisponivel);
	private JLabelPad lbImgOcupado = new JLabelPad(imgOcupado);
	private JLabelPad lbImgEncaminhado = new JLabelPad(imgEncaminhado);
	private JLabelPad lbImgEfetivado = new JLabelPad(imgEfetivado);
	
	
	
	public FGerencVagas() {
		super(false);
		setTitulo("Gerenciamento de vagas");
		setAtribos(15,30,796,380);

	
		



		montaListaCampos();
		montaTela();
		montaListeners();
	
	
		
		
		
	}
	
	private void montaListaCampos() {

		//VAGA
		lcVaga.add(new GuardaCampo( txtCodVaga, "CodVaga", "Cód.Vaga",ListaCampos.DB_PK , null, false));		
		lcVaga.add(new GuardaCampo( txtCodEmpr, "CodEmpr","Cód.Empr.",ListaCampos.DB_FK, null, false));
		lcVaga.add(new GuardaCampo( txtCodFunc, "CodFunc","Cód.Func.",ListaCampos.DB_FK, null, false));
		lcVaga.add(new GuardaCampo( txtFaixaSalIniVaga, "FaixaSalIni","Inicial",ListaCampos.DB_SI, null, false));
		lcVaga.add(new GuardaCampo( txtFaixaSalFimVaga, "FaixaSalFim","Inicial",ListaCampos.DB_SI, null, false));

		lcVaga.montaSql(false,"VAGA","RH");
		lcVaga.setQueryCommit(false);
		lcVaga.setReadOnly(true);		

		txtCodVaga.setNomeCampo("CodVaga");
		txtCodVaga.setPK(true);
		txtCodVaga.setListaCampos(lcVaga);
				
		//FK Empregador
		lcEmpregador.add(new GuardaCampo( txtCodEmpr, "CodEmpr", "Código", ListaCampos.DB_PK, null, false));
		lcEmpregador.add(new GuardaCampo( txtNomeEmpr, "NomeEmpr", "Empregador", ListaCampos.DB_SI, null, false));
		lcEmpregador.montaSql(false, "EMPREGADOR","RH");    
		lcEmpregador.setQueryCommit(false);
		lcEmpregador.setReadOnly(true);
		txtCodEmpr.setTabelaExterna(lcEmpregador);
		
		//FK Funcao
		lcFuncao.add(new GuardaCampo( txtCodFunc, "CodFunc", "Código", ListaCampos.DB_PK, null, false));
		lcFuncao.add(new GuardaCampo( txtDescFunc, "DescFunc", "Função", ListaCampos.DB_SI, null, false));
		lcFuncao.montaSql(false, "FUNCAO","RH");    
		lcFuncao.setQueryCommit(false);
		lcFuncao.setReadOnly(true);
		txtCodFunc.setTabelaExterna(lcFuncao);

		
		//Candidatos		
		lcCandidato.add(new GuardaCampo( txtCodCand, "CodCand", "Cód.Cand.", ListaCampos.DB_PK , null, false));		
		lcCandidato.add(new GuardaCampo( txtNomeCand, "NomeCand","Nome", ListaCampos.DB_SI, null, false));
		
		lcCandidato.montaSql(false,"CANDIDATO","RH");
		lcCandidato.setQueryCommit(false);
		lcCandidato.setReadOnly(true);		
		
		txtCodCand.setTabelaExterna(lcCandidato);	
		txtCodCand.setNomeCampo("CodCand");
		txtCodCand.setPK(true);
		txtCodCand.setListaCampos(lcCandidato);
		
	}

	private void montaTela() {
		
		pnBordRod.remove( pnRod );
		pnBordRod.remove( pnRodape );
		
		pnBordRod.setPreferredSize(new Dimension(450, 43));
		
		txtFaixaSalIniVaga.setForeground( new Color(10,95,0) );
		txtFaixaSalIniVaga.setBackground( new Color(240,240,240) );
		txtFaixaSalIniVaga.setBorder( null );
		
		txtFaixaSalFimVaga.setForeground( new Color(255,0,0) );
		txtFaixaSalFimVaga.setBackground( new Color(240,240,240) );
		txtFaixaSalFimVaga.setBorder( null );

		txtFaixaSalIniCand.setForeground( new Color(10,95,0) );
		txtFaixaSalIniCand.setBackground( new Color(240,240,240) );
		txtFaixaSalIniCand.setBorder( null );
		
		txtFaixaSalFimCand.setForeground( new Color(255,0,0) );
		txtFaixaSalFimCand.setBackground( new Color(240,240,240) );
		txtFaixaSalFimCand.setBorder( null );

		status.put( "DI", "Disponivel" );
		status.put( "EN", "Encaminhado" );
		status.put( "EF", "Efetivado" );
		
		btRefreshCand.setToolTipText( "Refazer consulta de candidados" );		
		btRefreshVagas.setToolTipText( "Refazer consulta de vagas" );
		
		btEncaminharCand.setToolTipText( "Encaminhar candidatos" );
		btEfetivarCand.setToolTipText( "Efetivar candidatos" );
		
		pinCabVaga.adic(new JLabelPad("Cód.Vaga"),7,0,60,20);
		pinCabVaga.adic(txtCodVaga,7,20,60,20);
		
		pinCabVaga.adic(new JLabelPad("Cód.Empr."),70,0,60,20);
		pinCabVaga.adic(txtCodEmpr,70,20,60,20);
		
		pinCabVaga.adic(new JLabelPad("Empregador"),133,0,230,20);
		pinCabVaga.adic(txtNomeEmpr,133,20,230,20);
		
		pinCabVaga.adic(new JLabelPad("Cód.Func."),7,40,60,20);
		pinCabVaga.adic(txtCodFunc,7,60,60,20);
		
		pinCabVaga.adic(new JLabelPad("Função"),70,40,230,20);
		pinCabVaga.adic(txtDescFunc,70,60,293,20);
								
		pinLbFiltrosVaga.adic( lbFiltrosVaga, 0, 0, 80, 15 );
		pinLbFiltrosVaga.tiraBorda();

		pinCabVaga.adic( pinLbFiltrosVaga, 375, 4, 55, 15 );
		pinCabVaga.adic( pinFiltrosVaga, 372, 12, 370, 69 );		
		pinCabVaga.adic (btRefreshCand,745,12,30,68);
					
		pinFiltrosVaga.adic( cbQualificacoesVaga, 3, 7, 130, 18 );
		pinFiltrosVaga.adic( cbRestricoesVaga, 3, 25, 130, 18 );
		pinFiltrosVaga.adic( cbFaixaSalarialVaga, 3, 43, 120, 18 );
				
		pinFiltrosVaga.adic( cbCursosVaga, 136, 7, 80, 18 );
		pinFiltrosVaga.adic( cbExperienciaVaga, 136, 25, 100, 18 );
		pinFiltrosVaga.adic (txtFaixaSalIniVaga,125,43,60,18); 
		pinFiltrosVaga.adic( new JLabelPad("à"), 191, 43, 8, 18 );
		pinFiltrosVaga.adic (txtFaixaSalFimVaga,203,43,50,18);		
		
		pinFiltrosVaga.adic( cbDisponibilidade, 255, 7, 230, 18 );		
		pinFiltrosVaga.adic( cbEnvolvimento, 255, 25, 230, 18 );
		pinFiltrosVaga.adic( cbEntrevistados, 255, 43, 230, 18 );
		
		pinRod.adic(btEncaminharCand,5,2,140,30);
		pinRod.adic(btEfetivarCand,148,2,130,30);
		pinRod.adic(btSair,675,2,100,30);
		
		// Adicionando legenda
		
		lbTxtIndisponivel.setFont(fontLegenda);
		lbTxtOcupado.setFont(fontLegenda);
		lbTxtEncaminhado.setFont(fontLegenda);
		lbTxtEfetivado.setFont(fontLegenda);
		lbTxtDisponivel.setFont(fontLegenda);
		lbTxtEntrevistado.setFont(fontLegenda);
		
		lbTxtIndisponivel.setForeground( corLegenda );
		lbTxtOcupado.setForeground( corLegenda );
		lbTxtEncaminhado.setForeground( corLegenda );
		lbTxtEfetivado.setForeground( corLegenda );
		lbTxtDisponivel.setForeground( corLegenda );
		lbTxtEntrevistado.setForeground( corLegenda );
		
		lbImgIndisponivel.setToolTipText( "Candidato indisponível, \njá foi efetivado em outra vaga." );
		lbImgOcupado.setToolTipText( "Candidato ocupado, \njá foi encaminhado para outra outra vaga." );
		lbImgEncaminhado.setToolTipText( "Candidato encaminhado, \njá foi encaminhado para esta vaga." );
		lbImgEfetivado.setToolTipText( "Candidato efetivado, \njá foi efetivado para esta vaga." );
		lbImgDisponivel.setToolTipText( "Candidato disponível, \npode ser encaminhado e/ou efetivado para esta vaga." );
		lbImgEntrevistado.setToolTipText( "Candidato entrevistado." );
						
		pinRod.adic(lbImgIndisponivel,280,3,20,11);
		pinRod.adic(lbTxtIndisponivel,300,3,100,11);

		pinRod.adic(lbImgOcupado,350,3,20,11);
		pinRod.adic(lbTxtOcupado,370,3,100,11);

		pinRod.adic(lbImgEncaminhado,415,3,20,11);
		pinRod.adic(lbTxtEncaminhado,435,3,100,11);

		pinRod.adic(lbImgEfetivado,280,20,20,11);
		pinRod.adic(lbTxtEfetivado,300,20,100,11);

		pinRod.adic(lbImgDisponivel,350,20,20,11);
		pinRod.adic(lbTxtDisponivel,370,20,100,11);
		
		pinRod.adic(lbImgEntrevistado,415,20,20,11);
		pinRod.adic(lbTxtEntrevistado,435,20,100,11);

		setPainel( pinCandVagas );
		
		pinCandVagas.add(pnCabVaga);
		
		pnCabVaga.add(pinCabVaga, BorderLayout.NORTH);
		pnCabVaga.add(spnTabCand, BorderLayout.CENTER);

		pnBordRod.add( pinRod );
		
		adicTab( "Candidatos para vagas", pinCandVagas );
		adicTab( "Vagas para candidatos", pinVagasCand );
		
		pnCabCand.add(pinCabCand, BorderLayout.NORTH );
		pinVagasCand.add(pnCabCand);	
		
		pinCabCand.adic(new JLabelPad("Cód.Cand"),7,0,60,20);
		pinCabCand.adic(txtCodCand,7,20,60,20);
		
		pinCabCand.adic(new JLabelPad("Nome do candidato"),70,0,300,20);
		pinCabCand.adic(txtNomeCand,70,20,300,20);
		
		pinLbFiltrosCand.adic( lbFiltrosCand, 0, 0, 80, 15 );
		pinLbFiltrosCand.tiraBorda();

		pinCabCand.adic( pinLbFiltrosCand, 375, 4, 55, 15 );
		pinCabCand.adic( pinFiltrosCand, 372, 12, 370, 69 );		

		pinCabCand.adic (btRefreshVagas,745,12,30,68);
					
		pinFiltrosCand.adic( cbQualificacoesCand, 3, 7, 130, 18 );
		pinFiltrosCand.adic( cbRestricoesCand, 3, 25, 130, 18 );
		pinFiltrosCand.adic( cbFaixaSalarialCand, 3, 43, 120, 18 );
				
		pinFiltrosCand.adic( cbCursosCand, 136, 7, 80, 18 );
		pinFiltrosCand.adic( cbExperienciaCand, 136, 25, 100, 18 );
		pinFiltrosCand.adic (txtFaixaSalIniCand,125,43,60,18); 
		pinFiltrosCand.adic( new JLabelPad("à"), 191, 43, 8, 18 );
		pinFiltrosCand.adic (txtFaixaSalFimCand,203,43,50,18);		
		
	
		
		
		
		
		montaTab();

	}
	
	private void montaListeners() {
		btRefreshCand.addActionListener(this);
		btEncaminharCand.addActionListener(this);
		btEfetivarCand.addActionListener(this);
		btSair.addActionListener(this);
		
		cbQualificacoesVaga.addMouseListener(
				  new MouseAdapter() {
					public void mouseClicked(MouseEvent mevt) {
						if (mevt.getSource()==cbQualificacoesVaga && mevt.getClickCount()==1){
							
						}
					}
				  }
				);		
				
				txtCodVaga.addKeyListener(
				  new KeyAdapter() {
				    public void keyPressed(KeyEvent kevt) {
				    	if (kevt.getKeyCode() == KeyEvent.VK_ENTER) {
//				    		montaTab();
				    	}
				    }
				  }
				);	
		
	}
	
	private void montaTab() {
		
		tabCand.adicColuna("S/N");
		tabCand.adicColuna("Cód.");
		tabCand.adicColuna("Nome");
		tabCand.adicColuna("Fone");
		tabCand.adicColuna("Qualif.");
		tabCand.adicColuna("Restr.");
		tabCand.adicColuna("Cursos");
		tabCand.adicColuna("Exp.");
		tabCand.adicColuna("Salário");
		tabCand.adicColuna("");
		tabCand.adicColuna("");
		tabCand.adicColuna("");
		
		tabCand.setTamColuna(30,0);
		tabCand.setTamColuna(55,1);
		tabCand.setTamColuna(250,2);
		tabCand.setTamColuna(80,3);
		tabCand.setTamColuna(55,4);
		tabCand.setTamColuna(55,5);
		tabCand.setTamColuna(55,6);
		tabCand.setTamColuna(55,7);
		tabCand.setTamColuna(80,8);
		tabCand.setTamColuna(0,9);
		tabCand.setTamColuna(0,10);
		tabCand.setTamColuna(20,11);
		
		tabCand.setColunaInvisivel( 9 );
		tabCand.setColunaInvisivel( 10 );
				
		tabCand.setColunaEditavel( 0, true );		
		tabCand.addMouseListener( this );
		
	}
	
	private void carregaTabCand(){ 

		StringBuffer sql = new StringBuffer();		
		StringBuffer where = new StringBuffer();
		boolean and = true;
		
		sql.append( "SELECT CODCAND,NOMECAND,FONECAND,PRETENSAOSAL,QUALIFICACOES,RESTRICOES,CURSOS,EXPERIENCIA,STVAGACAND,STCAND " );
		sql.append( "FROM RHLISTACANDVAGASP(?,?,?,?)" );
		where.append( " WHERE STCAND<>'IN' " );
		
		if(cbQualificacoesVaga.getVlrBoolean()) {
			where.append("QUALIFICACOES > 0 " );
			and = true;
		}
		
		if(cbCursosVaga.getVlrBoolean()) {
			where.append( (and ? " AND " : "" ) + ( "CURSOS > 0 " ) );
			and = true;
		}

		if(cbExperienciaVaga.getVlrBoolean()) {
			where.append( (and ? " AND " : "" ) + ( "EXPERIENCIA > 0 " ) );
			and = true;
		}

		if(cbRestricoesVaga.getVlrBoolean()) {
			where.append( ( and ? " AND " : "" ) + ( "RESTRICOES > 0 " ) );
			and = true;
		}

		if(cbFaixaSalarialVaga.getVlrBoolean()) {
			where.append( (and ? " AND " : "" ) + ( "((PRETENSAOSAL BETWEEN  ? AND ? ) OR (PRETENSAOSAL IS NULL))" ) );
			and = true;
		}
		
		if(cbDisponibilidade.getVlrBoolean()) {
			where.append( (and ? " AND " : "" ) + ( "(STVAGACAND='DI' AND STCAND='DI') " ) );
			and = true;
		}
		
		if(cbEnvolvimento.getVlrBoolean()) {
			where.append( (and ? " AND " : "" ) + ( "(STVAGACAND='EN' OR STVAGACAND='EF') " ) );
			and = true;
		}

		if(cbEntrevistados.getVlrBoolean()) {
			where.append( (and ? " AND " : "" ) + ( " STCAND='EV' " ) );			
		}

		sql.append( where );
		
		tabCand.limpa();
		
		try {
			System.out.println("SQL:" + sql.toString());
			
			PreparedStatement ps = con.prepareStatement(sql.toString());
			
			ps.setInt(1,Aplicativo.iCodEmp);
			ps.setInt(2,ListaCampos.getMasterFilial( "ATATRIBUICAO" ));
			ps.setInt(3,txtCodVaga.getVlrInteger().intValue());					
			ps.setInt(4,txtCodFunc.getVlrInteger().intValue());						
			
			if(cbFaixaSalarialVaga.getVlrBoolean()) {
				ps.setDouble( 5, txtFaixaSalIniVaga.getVlrDouble() );
				ps.setDouble( 6, txtFaixaSalFimVaga.getVlrDouble() );				
			}
						
			ResultSet rs = ps.executeQuery();
			System.out.println("SQL:" + sql.toString());			
		
			while (rs.next()) {
			    if(rs.getString( "STCAND" ).equals( "DI" ) && rs.getString( "STVAGACAND" ).equals( "DI" )) {
			    	imgStatus = imgDisponivel;
			    }
			    else if (rs.getString( "STVAGACAND" ).equals( "EF" )){  
			    	imgStatus = imgEfetivado;
			    }
			    else if (rs.getString( "STCAND" ).equals( "EF" ) ){  
			    	imgStatus = imgIndiponivel;
			    }
			    else if (rs.getString( "STVAGACAND" ).equals( "EN" )){
			    	imgStatus = imgEncaminhado;
			    }
			    else if(rs.getString( "STVAGACAND" ).equals( "DI" )){
			    	imgStatus = imgOcupado;
			    }
			    else if (rs.getString( "STCAND" ).equals( "EV" ) ){  
			    	imgStatus = imgEntrevistado;
			    }	    			    
			    else {
			    	imgStatus = Icone.novo( "clPagoParcial.gif" );
			    }
				
				Vector<Object> vVals = new Vector<Object>();
			    vVals.addElement( new Boolean( false ) );
			    vVals.addElement( rs.getString( "CODCAND" ) );
			    vVals.addElement( rs.getString( "NOMECAND" ) );
			    vVals.addElement( (rs.getString( "FONECAND" ) == null) ? "" : rs.getString( "FONECAND" ));
			    vVals.addElement( rs.getString( "QUALIFICACOES" ) );
			    vVals.addElement( rs.getString( "RESTRICOES" ) );
			    vVals.addElement( rs.getString( "CURSOS" ) );
			    vVals.addElement( rs.getString( "EXPERIENCIA" ) );
			    vVals.addElement( (rs.getString( "PRETENSAOSAL" ) == null) ? "" : rs.getString( "PRETENSAOSAL" ) );			    
			    vVals.addElement( rs.getString( "STVAGACAND" ) );
			    vVals.addElement( rs.getString( "STCAND" ) );
			    vVals.addElement( imgStatus );
			    
			    tabCand.adicLinha(vVals);				
				
			}
			if (!con.getAutoCommit())
				con.commit();
		}
		catch (SQLException err) {
			Funcoes.mensagemErro(this,"Erro ao consultar candidatos!\n"+err.getMessage(),true,con,err);
		}
		tabCand.addTabelaEditListener(this);
	}
	
	public void setConexao(Connection cn) {
		super.setConexao(cn);
		lcVaga.setConexao(con);
		lcEmpregador.setConexao(con);
		lcFuncao.setConexao(con);
		lcCandidato.setConexao(con);
	}

	public void actionPerformed( ActionEvent evt ) {
		if (evt.getSource() == btRefreshCand) {
			carregaTabCand();
		}
		else if (evt.getSource() == btEncaminharCand) {
			encaminharCandidato();
		}
		else if (evt.getSource() == btEfetivarCand) {
			efetivarCandidato();
		}
		else if (evt.getSource() == btSair) {
			dispose();
		}
	}

	public void valorAlterado(TabelaEditEvent evt) {
/*		if ((tabCand.getColunaEditada()<2)) {

          }*/
    }
	
	private void encaminharCandidato() {
		StringBuffer sql = new StringBuffer();
		PreparedStatement ps = null;
		int encaminhados = 0;		
		
		try {		
			
			if( Funcoes.mensagemConfirma( this, "Confirma o encaminhamento do(s) candidato(s) para a vaga?") == JOptionPane.YES_NO_OPTION ) {

				sql.append( "INSERT INTO RHVAGACANDIDATO(CODEMP,CODFILIAL,CODVAGA,CODEMPCA,CODFILIALCA,CODCAND,STVAGACAND) " );
				sql.append( "VALUES(?,?,?,?,?,?,?)" );	

				for ( int i = 0; i < tabCand.getNumLinhas(); i++ ) {
	
					if ( ! ( (Boolean) tabCand.getValor( i, 0 ) ).booleanValue() )
						continue;
					
					if( (tabCand.getValor( i, 9 ).toString().equals( "EF" )) ) { 
						Funcoes.mensagemInforma( this, "O candidato " + tabCand.getValor( i, 2 ).toString().trim() 
								+ " nao pode ser encaminhado, pois ja foi efetivado na vaga!" );	
						continue;					
					}
					
					if( (tabCand.getValor( i, 9 ).toString().equals( "DI" )) && ( ! tabCand.getValor( i, 10 ).toString().equals( "DI" )) ) { 
						
						if( Funcoes.mensagemConfirma( this, "O candidato " + tabCand.getValor( i, 2 ).toString().trim() 
								+ " ja foi " + status.get(tabCand.getValor( i, 10 ).toString()).toLowerCase()+ " em outra vaga.\n"
								+ " Confirma seu encaminhamento?") == JOptionPane.NO_OPTION) 						
							continue;	
						
					}
			
														
					ps = con.prepareStatement( sql.toString() );
					ps.setInt( 1, Aplicativo.iCodEmp );
					ps.setInt( 2, ListaCampos.getMasterFilial( "RHVAGACANDIDATO" ) );
					ps.setInt( 3, txtCodVaga.getVlrInteger().intValue() );
					ps.setInt( 4, Aplicativo.iCodEmp );
					ps.setInt( 5, ListaCampos.getMasterFilial( "RHCANDIDATO" ) );
					ps.setInt( 6, new Integer(tabCand.getValor( i, 1 ).toString()) );
					ps.setString( 7, "EN" );
					
					encaminhados += ps.executeUpdate();
					
					ps.close();			
					if ( !con.getAutoCommit() ) {
						con.commit();
					}
				}
				
				Funcoes.mensagemInforma( this, encaminhados > 0 ? 
						( encaminhados + " candidatos encaminhado" + ( encaminhados > 1 ? "s" : "" ) + " com sucesso!"):
					    ( "Nenhum candidato foi encaminhado para vaga! " ) ) ;				
			}
			
			carregaTabCand();
			
		}
		catch (Exception e) {
			Funcoes.mensagemErro( this, "Erro ao encaminhar candidatos!\n" + e.getMessage(), true, con, e );
			e.printStackTrace();
		}		
	}

	private void efetivarCandidato() {
		StringBuffer sql = new StringBuffer();
		PreparedStatement ps = null;
		int efetivados = 0;
		
		try {
			
			if( Funcoes.mensagemConfirma( this, "Confirma a efetivaçao do(s) candidato(s) na vaga?") == JOptionPane.YES_NO_OPTION ) {
			
				sql.append( "UPDATE RHVAGACANDIDATO SET STVAGACAND='EF' " );
				sql.append( "WHERE CODEMP=? AND CODFILIAL=? AND CODVAGA=? " );
				sql.append( "AND CODEMPCA=? AND CODFILIALCA=? AND CODCAND=? AND STVAGACAND='EN' " );	

				for ( int i = 0; i < tabCand.getNumLinhas(); i++ ) {
	
					if ( ! ( (Boolean) tabCand.getValor( i, 0 ) ).booleanValue() )
						continue;
				
					if( ! (tabCand.getValor( i, 9 ).toString().equals( "EN" )) ) { 
						Funcoes.mensagemInforma( this, "O candidato " + tabCand.getValor( i, 2 ).toString().trim() 
								+ " deve ser encaminhado antes de efetivado!" );	
						continue;					
					}
												
					ps = con.prepareStatement( sql.toString() );
					ps.setInt( 1, Aplicativo.iCodEmp );
					ps.setInt( 2, ListaCampos.getMasterFilial( "RHVAGACANDIDATO" ) );
					ps.setInt( 3, txtCodVaga.getVlrInteger().intValue() );
					ps.setInt( 4, Aplicativo.iCodEmp );
					ps.setInt( 5, ListaCampos.getMasterFilial( "RHCANDIDATO" ) );
					ps.setInt( 6, new Integer(tabCand.getValor( i, 1 ).toString()) );
									
					efetivados += ps.executeUpdate();
					
					ps.close();			
					if ( !con.getAutoCommit() ) {
						con.commit();
					}
				}
				
				Funcoes.mensagemInforma( this, efetivados > 0 ? 
						( efetivados + " candidatos efetivado" + ( efetivados > 1 ? "s" : "" ) + " com sucesso!"):
					    ( "Nenhum candidato foi efetivado na vaga! " ) ) ;
				
			}
			
			carregaTabCand();
			
		}
		catch (Exception e) {
			Funcoes.mensagemErro( this, "Erro ao efetivar candidatos!\n" + e.getMessage(), true, con, e );
			e.printStackTrace();
		}		
	}
		
	public void mouseClicked( MouseEvent mevt ) {
		
		Tabela tabEv = (Tabela) mevt.getSource();
	
		if ( mevt.getClickCount() == 1) {			
		}
		else if ( mevt.getClickCount() == 2 ) {			
			if ( tabEv == tabCand && tabEv.getLinhaSel() >= 0 ) {
				
				tabCand.setValor( !((Boolean) tabCand.getValor( tabCand.getLinhaSel(), 0 )).booleanValue(), tabCand.getLinhaSel(), 0 ); 
				
			}
		}
	}

	public void mouseEntered( MouseEvent e ) { }

	public void mouseExited( MouseEvent e ) { }

	public void mousePressed( MouseEvent e ) { }

	public void mouseReleased( MouseEvent e ) { }

}
