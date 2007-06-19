/**
 * @version 11/02/2002 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FConta.java <BR>
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
 * Comentários sobre a classe...
 */

package org.freedom.modulos.std;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.sql.Connection;
import java.util.Vector;
import javax.swing.JScrollPane;
import org.freedom.acao.CheckBoxEvent;
import org.freedom.acao.CheckBoxListener;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Navegador;
import org.freedom.componentes.Tabela;
import org.freedom.telas.FTabDados;
public class FConta extends FTabDados implements CheckBoxListener {

	private static final long serialVersionUID = 1L;

  private JTextFieldPad txtAgConta = new JTextFieldPad( JTextFieldPad.TP_STRING, 6, 0);
  private JTextFieldPad txtNumConta = new JTextFieldPad(JTextFieldPad.TP_STRING, 10, 0);
  private JTextFieldPad txtDescConta = new JTextFieldPad(JTextFieldPad.TP_STRING, 50, 0);
  private JTextFieldPad txtCodBanco = new JTextFieldPad(JTextFieldPad.TP_STRING,3,0);
  private JTextFieldPad txtDataConta = new JTextFieldPad(JTextFieldPad.TP_DATE, 10, 0);
  private JTextFieldPad txtCodMoeda = new JTextFieldPad(JTextFieldPad.TP_STRING,4,0);
  private JTextFieldPad txtCodPlan = new JTextFieldPad(JTextFieldPad.TP_STRING,13,0);
  private JTextFieldPad txtConvCobConta = new JTextFieldPad(JTextFieldPad.TP_STRING,10,0);
  private JTextFieldFK txtDescBanco = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);
  private JTextFieldFK txtDescMoeda = new JTextFieldFK(JTextFieldPad.TP_STRING, 50,0);
  private JTextFieldFK txtDescPlan = new JTextFieldFK(JTextFieldPad.TP_STRING, 40, 0);
  private JCheckBoxPad cbAtivaConta = new JCheckBoxPad("Sim","S","N");
  private JCheckBoxPad chbRestritoTipoMov = new JCheckBoxPad( "Permitir todos os usuários?", "S", "N" );
  private Vector<String> vValsTipo = new Vector<String>();
  private Vector<String> vLabsTipo = new Vector<String>();
  private JRadioGroup rgTipo = null;
  private ListaCampos lcBanco = new ListaCampos(this,"BO");
  private ListaCampos lcMoeda = new ListaCampos(this,"MA");
  private ListaCampos lcPlan = new ListaCampos(this,"PN");
  private ListaCampos lcRestricoes = new ListaCampos( this, "" );
  private ListaCampos lcUsu = new ListaCampos( this, "US" );
  
  private JPanelPad pinGeral = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );
  private JPanelPad pinCamposGeral = new JPanelPad( 430, 460 );
  private JPanelPad pnRestricoes = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );
  private JPanelPad pinDetRestricoes = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );  
  private Tabela tbRestricoes = new Tabela();
  private JScrollPane spnRestricoes = new JScrollPane( tbRestricoes );
  private JPanelPad pinNavRestricoes = new JPanelPad( 680, 30 );
  private JPanelPad pinCamposRestricoes = new JPanelPad( 430, 460 );
  private Navegador navRestricoes = new Navegador( true );
  private JTextFieldPad txtIDUsu = new JTextFieldPad( JTextFieldPad.TP_STRING, 8, 0 );
  private JTextFieldFK txtNomeUsu = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
  
  public FConta() {
  	super();
    setTitulo("Cadastro de Contas");
    setAtribos(50,50,450,400);
    
	lcRestricoes.setMaster( lcCampos );
	lcCampos.adicDetalhe( lcRestricoes );
	lcRestricoes.setTabela( tbRestricoes );
	
    lcBanco.add(new GuardaCampo( txtCodBanco, "CodBanco", "Cód.banco", ListaCampos.DB_PK, false));
    lcBanco.add(new GuardaCampo( txtDescBanco, "NomeBanco", "Nome do banco", ListaCampos.DB_SI, false));
    lcBanco.montaSql(false, "BANCO", "FN");    
    lcBanco.setQueryCommit(false);
    lcBanco.setReadOnly(true);
    txtCodBanco.setTabelaExterna(lcBanco);

    lcMoeda.add(new GuardaCampo( txtCodMoeda, "CodMoeda", "Cód.mda.",ListaCampos.DB_PK ,true));
    lcMoeda.add(new GuardaCampo( txtDescMoeda, "SingMoeda", "Descrição da moeda", ListaCampos.DB_SI, false));
    lcMoeda.montaSql(false, "MOEDA", "FN");    
    lcMoeda.setQueryCommit(false);
    lcMoeda.setReadOnly(true);
    txtCodMoeda.setTabelaExterna(lcMoeda);
    
    lcPlan.add(new GuardaCampo( txtCodPlan, "CodPlan", "Cód.planj.", ListaCampos.DB_PK, true));
    lcPlan.add(new GuardaCampo( txtDescPlan, "DescPlan", "Descrição do plano de contas", ListaCampos.DB_SI, false));
    lcPlan.montaSql(false, "PLANEJAMENTO", "FN");    
    lcPlan.setQueryCommit(false);
    lcPlan.setReadOnly(true);
    txtCodPlan.setTabelaExterna(lcPlan);
   
	lcUsu.add( new GuardaCampo( txtIDUsu, "IDUsu", "ID", ListaCampos.DB_PK, txtNomeUsu, false ) );
	lcUsu.add( new GuardaCampo( txtNomeUsu, "NomeUsu", "Nome nome do usuário", ListaCampos.DB_SI, false ) );
	lcUsu.montaSql( false, "USUARIO", "SG" );
	lcUsu.setQueryCommit( false );
	lcUsu.setReadOnly( true );
	txtIDUsu.setFK( true );
	txtIDUsu.setNomeCampo( "IDUsu" );
	txtIDUsu.setTabelaExterna( lcUsu );
    
    vValsTipo.addElement("B");
    vValsTipo.addElement("C");
    vLabsTipo.addElement("Bancos");
    vLabsTipo.addElement("Caixa");
    rgTipo = new JRadioGroup(1,2,vLabsTipo,vValsTipo);

	pinGeral.setPreferredSize( new Dimension( 430, 560 ) );
	pinGeral.add( pinCamposGeral, BorderLayout.CENTER );

	setPainel( pinCamposGeral );

	adicTab( "Geral", pinCamposGeral );
    
    adicCampo(txtNumConta, 7, 20, 110, 20, "NumConta", "Nº da conta", ListaCampos.DB_PK, true);
    adicCampo(txtDescConta, 120, 20, 270, 20, "DescConta", "Descrição da conta", ListaCampos.DB_SI, true);
    adicCampo(txtAgConta, 7, 60, 60, 20, "AgenciaConta", "Agência", ListaCampos.DB_SI, false);
    adicCampo(txtCodBanco, 70, 60, 80, 20, "CodBanco", "Cód.banco", ListaCampos.DB_FK, false);
    adicDescFK(txtDescBanco, 153, 60, 237, 20, "NomeBanco", "Descrição do banco");
    adicCampo(txtDataConta, 7, 100, 80, 20, "DataConta", "Data", ListaCampos.DB_SI, true);
    adicCampo(txtCodMoeda, 90, 100, 60, 20, "CodMoeda", "Cód.mda.", ListaCampos.DB_FK, true);
    adicDescFK(txtDescMoeda, 153, 100, 237, 20, "SingMoeda", "Descrição da moeda");
    adicCampo(txtCodPlan, 7, 140, 140, 20, "CodPlan", "Cód.tp.lanç.", ListaCampos.DB_FK, true);
    adicDescFK(txtDescPlan, 150, 140, 240, 20, "DescPlan", "Descrição do tipo de lançamento");
    adicCampo(txtConvCobConta, 7, 180, 150, 20, "ConvCobConta", "Convênio de cobrança", ListaCampos.DB_SI, true);

    adicDB(rgTipo, 7, 220, 200, 30, "TipoConta", "Tipo", true);
    adicDB(cbAtivaConta,220,220,120,20,"ativaconta","Ativa",true);
	adicDB( chbRestritoTipoMov, 7, 260, 240, 20, "TUSUCONTA", "", true );
    setListaCampos(false,"CONTA", "FN");
    lcCampos.setQueryInsert(false);   
        
	setPainel( pinDetRestricoes, pnRestricoes );

	pinDetRestricoes.setPreferredSize( new Dimension( 430, 80 ) );
	pinDetRestricoes.add( pinNavRestricoes, BorderLayout.SOUTH );
	pinDetRestricoes.add( pinCamposRestricoes, BorderLayout.CENTER );

	setListaCampos( lcRestricoes );
	setNavegador( navRestricoes );

	pnRestricoes.add( pinDetRestricoes, BorderLayout.SOUTH );
	pnRestricoes.add( spnRestricoes, BorderLayout.CENTER );
	pinNavRestricoes.adic( navRestricoes, 0, 0, 270, 25 );

	setPainel( pinCamposRestricoes );

	adicCampo( txtIDUsu, 7, 20, 80, 20, "IdUsu", "Id", ListaCampos.DB_PF, txtNomeUsu, true );
	adicDescFK( txtNomeUsu, 90, 20, 250, 20, "NomeUsu", " e nome do usuário" );

	setListaCampos( true, "CONTAUSU", "FN" );
	lcRestricoes.setQueryInsert( false );
	lcRestricoes.setQueryCommit( false );
	lcRestricoes.montaTab();

	txtNumConta.setTabelaExterna( lcRestricoes );

	tbRestricoes.setTamColuna( 80, 0 );
	tbRestricoes.setTamColuna( 280, 1 );
	
	chbRestritoTipoMov.addCheckBoxListener( this );
	
		
  }
  
  public void valorAlterado( CheckBoxEvent evt ) {	  
	  if ( evt.getCheckBox() == chbRestritoTipoMov )
		  if ( evt.getCheckBox().isSelected() )
			  removeTab( "Restrições de Usuário", pnRestricoes );
		  else
			  adicTab( "Restrições de Usuário", pnRestricoes );
  }
  
  public void setConexao(Connection cn) {
    super.setConexao(cn);
    lcBanco.setConexao(cn);
    lcMoeda.setConexao(cn);
    lcPlan.setConexao(cn);
    lcRestricoes.setConexao( cn );
    lcUsu.setConexao( cn );
  }
}

