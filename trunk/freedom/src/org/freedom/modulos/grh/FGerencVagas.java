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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
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


public class FGerencVagas extends FFilho implements ActionListener, TabelaEditListener {

	private static final long serialVersionUID = 1L;

	private JPanelPad pinCab = new JPanelPad(0,80);
	private JPanelPad pnCab = new JPanelPad(JPanelPad.TP_JPANEL,new BorderLayout());
	private JTextFieldPad txtCodVaga = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
	private final JTextFieldFK txtCodEmpr = new JTextFieldFK( JTextFieldPad.TP_INTEGER, 8, 0 );	
	private final JTextFieldFK txtNomeEmpr = new JTextFieldFK( JTextFieldPad.TP_STRING, 60, 0 );
	private final JTextFieldFK txtCodFunc = new JTextFieldFK( JTextFieldPad.TP_INTEGER, 8, 0 );	
	private final JTextFieldFK txtDescFunc = new JTextFieldFK( JTextFieldPad.TP_STRING, 60, 0 );

	private Tabela tab = new Tabela();
	private JButton btCalc = new JButton(Icone.novo("btExecuta.gif"));
	private JButton btOk = new JButton(Icone.novo("btOk.gif"));
	private JButton btSair = new JButton("Sair",Icone.novo("btSair.gif"));
	private ImageIcon imgEditaCampo = Icone.novo("clEditar.gif");
	private JScrollPane spnTab = new JScrollPane(tab);
	private ListaCampos lcVaga = new ListaCampos(this);
	private ListaCampos lcEmpregador = new ListaCampos(this,"EM");
	private ListaCampos lcFuncao = new ListaCampos(this,"FC");
	private JCheckBoxPad cbQualificacoes = new JCheckBoxPad("Qualificações","S","N");
		
	
	BigDecimal bVlrAceito = new BigDecimal("0");
	BigDecimal bVlrAprovado = new BigDecimal("0");
	BigDecimal bVlrTotal = new BigDecimal("0");

	public FGerencVagas() {
		super(false);
		setTitulo("Gerenciamento de vagas");
		setAtribos(15,30,796,380);

		btCalc.setToolTipText("Recarregar ítens");
		btOk.setToolTipText("Confirmar aprovação");
		
		btCalc.addActionListener(this);
		btOk.addActionListener(this);
		btSair.addActionListener(this);

		JPanelPad pinRod = new JPanelPad(685,50);
			
		lcVaga.add(new GuardaCampo( txtCodVaga, "CodVaga", "Cód.Vaga",ListaCampos.DB_PK , null, false));		
		lcVaga.add(new GuardaCampo( txtCodEmpr, "CodEmpr","Cód.Empr.",ListaCampos.DB_FK, null, false));
		lcVaga.add(new GuardaCampo( txtCodFunc, "CodFunc","Cód.Func.",ListaCampos.DB_FK, null, false));

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

		pinCab.adic(new JLabelPad("Cód.Vaga"),7,0,120,20);
		pinCab.adic(txtCodVaga,7,20,85,20);
		
		pinCab.adic(new JLabelPad("Cód.Empr."),95,0,60,20);
		pinCab.adic(txtCodEmpr,95,20,60,20);
		pinCab.adic(new JLabelPad("Empregador"),158,0,203,20);
		pinCab.adic(txtNomeEmpr,158,20,203,20);
		
		pinCab.adic(new JLabelPad("Cód.Func."),364,0,60,20);
		pinCab.adic(txtCodFunc,364,20,60,20);
		pinCab.adic(new JLabelPad("Função"),427,0,250,20);
		pinCab.adic(txtDescFunc,427,20,203,20);
					
		cbQualificacoes.setVlrString("N");
		pinCab.adic(cbQualificacoes,7,45,200,20);
				
		pinRod.adic(btCalc,10,10,57,30);
		pinRod.adic(btOk,70,10,57,30);
		pinRod.adic(btSair,660,10,100,30);
					
		getTela().add(pnCab,BorderLayout.CENTER);
		pnCab.add(pinCab,BorderLayout.NORTH);
		pnCab.add(pinRod,BorderLayout.SOUTH);
		pnCab.add(spnTab,BorderLayout.CENTER);

		tab.adicColuna("Cód.");
		tab.adicColuna("Nome");
		tab.adicColuna("Fone");
		tab.adicColuna("Qualificações");
		tab.adicColuna("Restrições");
        tab.adicColuna("Cursos");
		tab.adicColuna("Experiência");
		tab.adicColuna("Salário");

		tab.setTamColuna(60,0);
		tab.setTamColuna(200,1);
		tab.setTamColuna(80,2);
		tab.setTamColuna(60,3);
		tab.setTamColuna(60,4);
		tab.setTamColuna(60,5);
		tab.setTamColuna(60,6);
		tab.setTamColuna(80,7);
		
		//tab.setColunaEditavel(0,true);
		
//		tab.setDefaultEditor(String.class,new DefaultCellEditor(new JTextFieldPad(JTextFieldPad.TP_STRING,15,0)));
		
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
		    		montaTab();
		    	}
		    }
		  }
		);	
	}
    
	public void montaTab(){ 

		StringBuffer sql = new StringBuffer();
		
		sql.append( "SELECT CD.CODCAND,CD.NOMECAND,CD.FONECAND,PRETENSAOSAL, " );
		sql.append( "(SELECT COUNT(*) FROM RHCANDIDATOATRIB ATQ WHERE ATQ.CODEMP=CD.CODEMP " );
		sql.append( "AND ATQ.CODFILIAL=CD.CODFILIAL AND ATQ.CODCAND=CD.CODCAND " );
		sql.append( "AND ATQ.CODATRIB IN " );
		sql.append( "(SELECT ATVQ.CODATRIB FROM RHVAGAATRIBQUALI ATVQ ");
		sql.append( "WHERE ATVQ.CODEMP=? AND ATVQ.CODFILIAL=? AND ATVQ.CODVAGA=?) ) AS QUALIFICACOES, " );
		sql.append( "(SELECT COUNT(*) FROM RHCANDIDATOATRIB ATR WHERE ATR.CODEMP=CD.CODEMP " );
		sql.append( "AND ATR.CODFILIAL=CD.CODFILIAL AND ATR.CODCAND=CD.CODCAND " );
		sql.append( "AND ATR.CODATRIB IN " );
		sql.append( "(SELECT ATVR.CODATRIB FROM RHVAGAATRIBREST ATVR " );
		sql.append( "WHERE ATVR.CODEMP=? AND ATVR.CODFILIAL=? AND ATVR.CODVAGA=?) ) AS RESTRICOES, " );
		sql.append( "(SELECT COUNT(*) FROM RHCANDIDATOCURSO CU WHERE CU.CODEMP=CD.CODEMP " );
		sql.append( "AND CU.CODFILIAL=CD.CODFILIAL AND CU.CODCAND=CD.CODCAND " );
		sql.append( "AND CU.CODCURSO IN " );
		sql.append( "(SELECT VC.CODCURSO FROM RHVAGACURSO VC " );				
		sql.append( "WHERE VC.CODEMP=? AND VC.CODFILIAL=? AND VC.CODVAGA=? ) ) AS CURSOS, " );                
		sql.append( "(SELECT COUNT(*) FROM RHCANDIDATOFUNC FU WHERE FU.CODEMP=CD.CODEMP " );            
		sql.append( "AND FU.CODFILIAL=CD.CODFILIAL AND FU.CODCAND=CD.CODCAND AND FU.CODFUNC=? ) AS EXPERIENCIA ");
		sql.append( "FROM RHCANDIDATO CD" );
 
		tab.limpa();
		
		try {
			System.out.println("SQL:" + sql.toString());
			
			PreparedStatement ps = con.prepareStatement(sql.toString());
			
			ps.setInt(1,Aplicativo.iCodEmp);
			ps.setInt(2,ListaCampos.getMasterFilial( "ATATRIBUICAO" ));
			ps.setInt(3,txtCodVaga.getVlrInteger().intValue());
			
			ps.setInt(4,Aplicativo.iCodEmp);
			ps.setInt(5,ListaCampos.getMasterFilial( "ATATRIBUICAO" ));
			ps.setInt(6,txtCodVaga.getVlrInteger().intValue());
			
			ps.setInt(7,Aplicativo.iCodEmp);
			ps.setInt(8,ListaCampos.getMasterFilial( "RHCURSO" ));
			ps.setInt(9,txtCodVaga.getVlrInteger().intValue());
			
			ps.setInt(10,txtCodFunc.getVlrInteger().intValue());						
			
//			ps.setString(4,cbQualificacoes.getVlrString());
			
			ResultSet rs = ps.executeQuery();
						
			while (rs.next()) {
			    Vector<Object> vVals = new Vector<Object>();
			    vVals.addElement( rs.getString( "CODCAND" ) );
			    vVals.addElement( rs.getString( "NOMECAND" ) );
			    vVals.addElement( rs.getString( "FONECAND" ) );
			    vVals.addElement( rs.getString( "QUALIFICACOES" ) );
			    vVals.addElement( rs.getString( "RESTRICOES" ) );
			    vVals.addElement( rs.getString( "CURSOS" ) );
			    vVals.addElement( rs.getString( "EXPERIENCIA" ) );
			    vVals.addElement( rs.getString( "PRETENSAOSAL" ) );
			    
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
		if (evt.getSource() == btCalc) {
			montaTab();
		}
		else if (evt.getSource() == btSair) {
			dispose();
		}
		else if (evt.getSource() == btOk) {
			if (Funcoes.mensagemConfirma(this, "Confirma?")==0 ) {
	    	
			}
		}
	}

	public void valorAlterado(TabelaEditEvent evt) {
/*		if ((tab.getColunaEditada()<2)) {

          }*/
    }

}
