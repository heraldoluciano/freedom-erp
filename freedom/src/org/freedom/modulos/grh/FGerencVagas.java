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
import org.freedom.telas.FFilho;

public class FGerencVagas extends FFilho implements ActionListener, TabelaEditListener, MouseListener {

	private static final long serialVersionUID = 1L;

	private JPanelPad pinCab = new JPanelPad( 0, 94 );
	private JPanelPad pnCab = new JPanelPad(JPanelPad.TP_JPANEL,new BorderLayout());
	private JTextFieldPad txtCodVaga = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
	private final JTextFieldFK txtCodEmpr = new JTextFieldFK( JTextFieldPad.TP_INTEGER, 8, 0 );	
	private final JTextFieldFK txtNomeEmpr = new JTextFieldFK( JTextFieldPad.TP_STRING, 60, 0 );
	private final JTextFieldFK txtCodFunc = new JTextFieldFK( JTextFieldPad.TP_INTEGER, 8, 0 );	
	private final JTextFieldFK txtDescFunc = new JTextFieldFK( JTextFieldPad.TP_STRING, 60, 0 );
	private final JTextFieldFK txtFaixaSalIni = new JTextFieldFK( JTextFieldPad.TP_DECIMAL, 15, 2 );
	private final JTextFieldFK txtFaixaSalFim = new JTextFieldFK( JTextFieldPad.TP_DECIMAL, 15, 2 );
		
	private Tabela tab = new Tabela();
	private JButton btRefresh = new JButton(Icone.novo("btExecuta.gif"));
	private JButton btEncaminharCand = new JButton("Encaminhar", Icone.novo("btEncaminharCand.gif"));
	private JButton btEfetivarCand = new JButton("Efetivar", Icone.novo("btEfetivarCand.gif"));
	private JButton btSair = new JButton("Sair",Icone.novo("btSair.gif"));
	private ImageIcon imgEditaCampo = Icone.novo("clEditar.gif");
	private JScrollPane spnTab = new JScrollPane(tab);
	private ListaCampos lcVaga = new ListaCampos(this);
	private ListaCampos lcEmpregador = new ListaCampos(this,"EM");
	private ListaCampos lcFuncao = new ListaCampos(this,"FC");

	private JCheckBoxPad cbQualificacoes = new JCheckBoxPad("Qualificações",new Boolean(true),new Boolean(false));
	private JCheckBoxPad cbRestricoes = new JCheckBoxPad("Restrições",new Boolean(true),new Boolean(false));
	private JCheckBoxPad cbCursos = new JCheckBoxPad("Cursos",new Boolean(true),new Boolean(false));
	private JCheckBoxPad cbExperiencia = new JCheckBoxPad("Experiência",new Boolean(true),new Boolean(false));
	private JCheckBoxPad cbFaixaSalarial = new JCheckBoxPad("Faixa salarial de:",new Boolean(true),new Boolean(false));
	private JCheckBoxPad cbDisponibilidade = new JCheckBoxPad("Disponíveis",new Boolean(true),new Boolean(false));
	private JCheckBoxPad cbEnvolvimento = new JCheckBoxPad("Envolvidos",new Boolean(true),new Boolean(false));	
	
	private final JCheckBoxPad cbSelecionado = new JCheckBoxPad( "Seleção", "S", "N" );
	
	private BigDecimal bVlrAceito = new BigDecimal("0");
	private BigDecimal bVlrAprovado = new BigDecimal("0");
	private BigDecimal bVlrTotal = new BigDecimal("0");
	
	private JLabelPad lbFiltros = new JLabelPad( " Filtros" );
	private JPanelPad pinFiltros = new JPanelPad( 300, 150 );
	private JPanelPad pinLbFiltros = new JPanelPad( 53, 15 );
	private Map<String,String> status = new HashMap<String, String>();
	
	private ImageIcon imgIndiponivel = Icone.novo( "clIndisponivel.gif" );
	private ImageIcon imgDisponivel = Icone.novo( "clDisponivel.gif" );
	private ImageIcon imgOcupado = Icone.novo( "clIndisponivelParc.gif" );
	private ImageIcon imgEncaminhado = Icone.novo( "clEfetivar.gif" );
	private ImageIcon imgEfetivado = Icone.novo( "clEfetivado.gif" );
	private ImageIcon imgStatus = null;
	
	private Font fontLegenda =  new Font("Arial", Font.PLAIN, 9);		
	private Color corLegenda = new Color(120,120,120); 
	
	private JLabelPad lbTxtIndisponivel = new JLabelPad("Indisponível");
	private JLabelPad lbTxtOcupado = new JLabelPad("Ocupado");
	private JLabelPad lbTxtDisponivel = new JLabelPad("Disponível");
	private JLabelPad lbTxtEncaminhado = new JLabelPad("Encaminhado");
	private JLabelPad lbTxtEfetivado = new JLabelPad("Efetivado");
	
	private JLabelPad lbImgIndisponivel = new JLabelPad(imgIndiponivel);
	private JLabelPad lbImgDisponivel = new JLabelPad(imgDisponivel);
	private JLabelPad lbImgOcupado = new JLabelPad(imgOcupado);
	private JLabelPad lbImgEncaminhado = new JLabelPad(imgEncaminhado);
	private JLabelPad lbImgEfetivado = new JLabelPad(imgEfetivado);
	

	
	
	public FGerencVagas() {
		super(false);
		setTitulo("Gerenciamento de vagas");
		setAtribos(15,30,796,380);

		txtFaixaSalIni.setForeground( new Color(10,95,0) );
		txtFaixaSalIni.setBackground( new Color(240,240,240) );
		txtFaixaSalIni.setBorder( null );
		
		txtFaixaSalFim.setForeground( new Color(255,0,0) );
		txtFaixaSalFim.setBackground( new Color(240,240,240) );
		txtFaixaSalFim.setBorder( null );

		status.put( "DI", "Disponivel" );
		status.put( "EN", "Encaminhado" );
		status.put( "EF", "Efetivado" );
		
		btRefresh.setToolTipText( "Refazer consulta" );		
		btEncaminharCand.setToolTipText( "Encaminhar candidatos" );
		btEfetivarCand.setToolTipText( "Efetivar candidatos" );
		
		btRefresh.addActionListener(this);
		btEncaminharCand.addActionListener(this);
		btEfetivarCand.addActionListener(this);
		btSair.addActionListener(this);

		JPanelPad pinRod = new JPanelPad(685,39);
			
		lcVaga.add(new GuardaCampo( txtCodVaga, "CodVaga", "Cód.Vaga",ListaCampos.DB_PK , null, false));		
		lcVaga.add(new GuardaCampo( txtCodEmpr, "CodEmpr","Cód.Empr.",ListaCampos.DB_FK, null, false));
		lcVaga.add(new GuardaCampo( txtCodFunc, "CodFunc","Cód.Func.",ListaCampos.DB_FK, null, false));
		lcVaga.add(new GuardaCampo( txtFaixaSalIni, "FaixaSalIni","Inicial",ListaCampos.DB_SI, null, false));
		lcVaga.add(new GuardaCampo( txtFaixaSalFim, "FaixaSalFim","Inicial",ListaCampos.DB_SI, null, false));

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

		pinCab.adic(new JLabelPad("Cód.Vaga"),7,0,60,20);
		pinCab.adic(txtCodVaga,7,20,60,20);
		
		pinCab.adic(new JLabelPad("Cód.Empr."),70,0,60,20);
		pinCab.adic(txtCodEmpr,70,20,60,20);
		
		pinCab.adic(new JLabelPad("Empregador"),133,0,230,20);
		pinCab.adic(txtNomeEmpr,133,20,230,20);
		
		pinCab.adic(new JLabelPad("Cód.Func."),7,40,60,20);
		pinCab.adic(txtCodFunc,7,60,60,20);
		
		pinCab.adic(new JLabelPad("Função"),70,40,230,20);
		pinCab.adic(txtDescFunc,70,60,293,20);
								
		pinLbFiltros.adic( lbFiltros, 0, 0, 80, 15 );
		pinLbFiltros.tiraBorda();

		pinCab.adic( pinLbFiltros, 375, 4, 55, 15 );
		pinCab.adic( pinFiltros, 372, 12, 370, 69 );		
		pinCab.adic (btRefresh,745,12,30,68);
					
		pinFiltros.adic( cbQualificacoes, 3, 7, 130, 18 );
		pinFiltros.adic( cbRestricoes, 3, 25, 130, 18 );
		pinFiltros.adic( cbFaixaSalarial, 3, 43, 120, 18 );
				
		pinFiltros.adic( cbCursos, 136, 7, 80, 18 );
		pinFiltros.adic( cbExperiencia, 136, 25, 100, 18 );
		pinFiltros.adic (txtFaixaSalIni,125,43,60,18); 
		pinFiltros.adic( new JLabelPad("à"), 191, 43, 8, 18 );
		pinFiltros.adic (txtFaixaSalFim,203,43,60,18);		
		
		pinFiltros.adic( cbDisponibilidade, 250, 7, 230, 18 );		
		pinFiltros.adic( cbEnvolvimento, 250, 25, 230, 18 );
		
		pinRod.adic(btEncaminharCand,5,2,140,30);
		pinRod.adic(btEfetivarCand,148,2,130,30);
		pinRod.adic(btSair,675,2,100,30);
		
		// Adicionando legenda
		
		lbTxtIndisponivel.setFont(fontLegenda);
		lbTxtOcupado.setFont(fontLegenda);
		lbTxtEncaminhado.setFont(fontLegenda);
		lbTxtEfetivado.setFont(fontLegenda);
		lbTxtDisponivel.setFont(fontLegenda);
		
		lbTxtIndisponivel.setForeground( corLegenda );
		lbTxtOcupado.setForeground( corLegenda );
		lbTxtEncaminhado.setForeground( corLegenda );
		lbTxtEfetivado.setForeground( corLegenda );
		lbTxtDisponivel.setForeground( corLegenda );
		
		lbImgIndisponivel.setToolTipText( "Candidato indisponível, \njá foi efetivado em outra vaga." );
		lbImgOcupado.setToolTipText( "Candidato ocupado, \njá foi encaminhado para outra outra vaga." );
		lbImgEncaminhado.setToolTipText( "Candidato encaminhado, \njá foi encaminhado para esta vaga." );
		lbImgEfetivado.setToolTipText( "Candidato efetivado, \njá foi efetivado para esta vaga." );
		lbImgDisponivel.setToolTipText( "Candidato disponível, \npode ser encaminhado e/ou efetivado para esta vaga." );
						
		pinRod.adic(lbImgIndisponivel,300,6,20,20);
		pinRod.adic(lbTxtIndisponivel,320,6,100,20);

		pinRod.adic(lbImgOcupado,370,6,20,20);
		pinRod.adic(lbTxtOcupado,390,6,100,20);

		pinRod.adic(lbImgEncaminhado,430,6,20,20);
		pinRod.adic(lbTxtEncaminhado,450,6,100,20);

		pinRod.adic(lbImgEfetivado,510,6,20,20);
		pinRod.adic(lbTxtEfetivado,530,6,100,20);

		pinRod.adic(lbImgDisponivel,580,6,20,20);
		pinRod.adic(lbTxtDisponivel,600,6,100,20);
	
		getTela().add(pnCab,BorderLayout.CENTER);
		pnCab.add(pinCab,BorderLayout.NORTH);
		pnCab.add(pinRod,BorderLayout.SOUTH);
		pnCab.add(spnTab,BorderLayout.CENTER);

		tab.adicColuna("S/N");
		tab.adicColuna("Cód.");
		tab.adicColuna("Nome");
		tab.adicColuna("Fone");
		tab.adicColuna("Qualif.");
		tab.adicColuna("Restr.");
        tab.adicColuna("Cursos");
		tab.adicColuna("Exp.");
		tab.adicColuna("Salário");
		tab.adicColuna("");
		tab.adicColuna("");
		tab.adicColuna("");
		
		tab.setTamColuna(30,0);
		tab.setTamColuna(55,1);
		tab.setTamColuna(250,2);
		tab.setTamColuna(80,3);
		tab.setTamColuna(55,4);
		tab.setTamColuna(55,5);
		tab.setTamColuna(55,6);
		tab.setTamColuna(55,7);
		tab.setTamColuna(80,8);
		tab.setTamColuna(0,9);
		tab.setTamColuna(0,10);
		tab.setTamColuna(20,11);
		
		tab.setColunaInvisivel( 9 );
		tab.setColunaInvisivel( 10 );
				
		tab.setColunaEditavel( 0, true );		
		tab.addMouseListener( this );
				
		cbQualificacoes.addMouseListener(
		  new MouseAdapter() {
			public void mouseClicked(MouseEvent mevt) {
				if (mevt.getSource()==cbQualificacoes && mevt.getClickCount()==1){
					
				}
			}
		  }
		);		
		
		txtCodVaga.addKeyListener(
		  new KeyAdapter() {
		    public void keyPressed(KeyEvent kevt) {
		    	if (kevt.getKeyCode() == KeyEvent.VK_ENTER) {
//		    		montaTab();
		    	}
		    }
		  }
		);	
	}
    
	public void montaTab(){ 

		StringBuffer sql = new StringBuffer();		
		StringBuffer where = new StringBuffer();
		boolean and = false;
		
		sql.append( "SELECT CODCAND,NOMECAND,FONECAND,PRETENSAOSAL,QUALIFICACOES,RESTRICOES,CURSOS,EXPERIENCIA,STVAGACAND,STCAND " );
		sql.append( "FROM RHLISTACANDVAGASP(?,?,?,?)" );
				
		if(cbQualificacoes.getVlrBoolean() || cbCursos.getVlrBoolean() ||
		   cbExperiencia.getVlrBoolean() || cbFaixaSalarial.getVlrBoolean() ||
		   cbRestricoes.getVlrBoolean() || cbDisponibilidade.getVlrBoolean() || 
		   cbEnvolvimento.getVlrBoolean()) {
		
			where.append( " WHERE " );
		
		}
				
		if(cbQualificacoes.getVlrBoolean()) {
			where.append("QUALIFICACOES > 0 " );
			and = true;
		}
		
		if(cbCursos.getVlrBoolean()) {
			where.append( (and ? " AND " : "" ) + ( "CURSOS > 0 " ) );
			and = true;
		}

		if(cbExperiencia.getVlrBoolean()) {
			where.append( (and ? " AND " : "" ) + ( "EXPERIENCIA > 0 " ) );
			and = true;
		}

		if(cbRestricoes.getVlrBoolean()) {
			where.append( ( and ? " AND " : "" ) + ( "RESTRICOES > 0 " ) );
			and = true;
		}

		if(cbFaixaSalarial.getVlrBoolean()) {
			where.append( (and ? " AND " : "" ) + ( "((PRETENSAOSAL BETWEEN  ? AND ? ) OR (PRETENSAOSAL IS NULL))" ) );			
		}
		
		if(cbDisponibilidade.getVlrBoolean()) {
			where.append( (and ? " AND " : "" ) + ( "(STVAGACAND='DI' AND STCAND='DI') " ) );			
		}
		
		if(cbEnvolvimento.getVlrBoolean()) {
			where.append( (and ? " AND " : "" ) + ( "(STVAGACAND='EN' OR STVAGACAND='EF') " ) );			
		}
		
		sql.append( where );
		
		tab.limpa();
		
		try {
			System.out.println("SQL:" + sql.toString());
			
			PreparedStatement ps = con.prepareStatement(sql.toString());
			
			ps.setInt(1,Aplicativo.iCodEmp);
			ps.setInt(2,ListaCampos.getMasterFilial( "ATATRIBUICAO" ));
			ps.setInt(3,txtCodVaga.getVlrInteger().intValue());					
			ps.setInt(4,txtCodFunc.getVlrInteger().intValue());						
			
			if(cbFaixaSalarial.getVlrBoolean()) {
				ps.setDouble( 5, txtFaixaSalIni.getVlrDouble() );
				ps.setDouble( 6, txtFaixaSalFim.getVlrDouble() );				
			}
						
			ResultSet rs = ps.executeQuery();
						
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
			    
				tab.adicLinha(vVals);				
				
			}
			if (!con.getAutoCommit())
				con.commit();
		}
		catch (SQLException err) {
			Funcoes.mensagemErro(this,"Erro ao consultar candidatos!\n"+err.getMessage(),true,con,err);
		}
		tab.addTabelaEditListener(this);
	}
	
	public void setConexao(Connection cn) {
		super.setConexao(cn);
		lcVaga.setConexao(con);
		lcEmpregador.setConexao(con);
		lcFuncao.setConexao(con);
	}

	public void actionPerformed( ActionEvent evt ) {
		if (evt.getSource() == btRefresh) {
			montaTab();
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
/*		if ((tab.getColunaEditada()<2)) {

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

				for ( int i = 0; i < tab.getNumLinhas(); i++ ) {
	
					if ( ! ( (Boolean) tab.getValor( i, 0 ) ).booleanValue() )
						continue;
					
					if( (tab.getValor( i, 9 ).toString().equals( "EF" )) ) { 
						Funcoes.mensagemInforma( this, "O candidato " + tab.getValor( i, 2 ).toString().trim() 
								+ " nao pode ser encaminhado, pois ja foi efetivado na vaga!" );	
						continue;					
					}
					
					if( (tab.getValor( i, 9 ).toString().equals( "DI" )) && ( ! tab.getValor( i, 10 ).toString().equals( "DI" )) ) { 
						
						if( Funcoes.mensagemConfirma( this, "O candidato " + tab.getValor( i, 2 ).toString().trim() 
								+ " ja foi " + status.get(tab.getValor( i, 10 ).toString()).toLowerCase()+ " em outra vaga.\n"
								+ " Confirma seu encaminhamento?") == JOptionPane.NO_OPTION) 						
							continue;	
						
					}
			
														
					ps = con.prepareStatement( sql.toString() );
					ps.setInt( 1, Aplicativo.iCodEmp );
					ps.setInt( 2, ListaCampos.getMasterFilial( "RHVAGACANDIDATO" ) );
					ps.setInt( 3, txtCodVaga.getVlrInteger().intValue() );
					ps.setInt( 4, Aplicativo.iCodEmp );
					ps.setInt( 5, ListaCampos.getMasterFilial( "RHCANDIDATO" ) );
					ps.setInt( 6, new Integer(tab.getValor( i, 1 ).toString()) );
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
			
			montaTab();
			
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

				for ( int i = 0; i < tab.getNumLinhas(); i++ ) {
	
					if ( ! ( (Boolean) tab.getValor( i, 0 ) ).booleanValue() )
						continue;
				
					if( ! (tab.getValor( i, 9 ).toString().equals( "EN" )) ) { 
						Funcoes.mensagemInforma( this, "O candidato " + tab.getValor( i, 2 ).toString().trim() 
								+ " deve ser encaminhado antes de efetivado!" );	
						continue;					
					}
												
					ps = con.prepareStatement( sql.toString() );
					ps.setInt( 1, Aplicativo.iCodEmp );
					ps.setInt( 2, ListaCampos.getMasterFilial( "RHVAGACANDIDATO" ) );
					ps.setInt( 3, txtCodVaga.getVlrInteger().intValue() );
					ps.setInt( 4, Aplicativo.iCodEmp );
					ps.setInt( 5, ListaCampos.getMasterFilial( "RHCANDIDATO" ) );
					ps.setInt( 6, new Integer(tab.getValor( i, 1 ).toString()) );
									
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
			
			montaTab();
			
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
			if ( tabEv == tab && tabEv.getLinhaSel() >= 0 ) {
				
				tab.setValor( !((Boolean) tab.getValor( tab.getLinhaSel(), 0 )).booleanValue(), tab.getLinhaSel(), 0 ); 
				
			}
		}
	}

	public void mouseEntered( MouseEvent e ) { }

	public void mouseExited( MouseEvent e ) { }

	public void mousePressed( MouseEvent e ) { }

	public void mouseReleased( MouseEvent e ) { }

}
