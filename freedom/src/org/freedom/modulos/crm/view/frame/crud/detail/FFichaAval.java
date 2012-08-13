/**
 * @version 02/11/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.atd <BR>
 *         Classe: @(#)FTipoAtendo.java <BR>
 * 
 *         Este arquivo é parte do sistema Freedom-ERP, o Freedom-ERP é um software livre; você pode redistribui-lo e/ou <BR>
 *         modifica-lo dentro dos termos da Licença Pública Geral GNU como publicada pela Fundação do Software Livre (FSF); <BR>
 *         na versão 2 da Licença, ou (na sua opnião) qualquer versão. <BR>
 *         Este programa é distribuido na esperança que possa ser util, mas SEM NENHUMA GARANTIA; <BR>
 *         sem uma garantia implicita de ADEQUAÇÂO a qualquer MERCADO ou APLICAÇÃO EM PARTICULAR. <BR>
 *         Veja a Licença Pública Geral GNU para maiores detalhes. <BR>
 *         Você deve ter recebido uma cópia da Licença Pública Geral GNU junto com este programa, se não, <BR>
 *         escreva para a Fundação do Software Livre(FSF) Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA <BR>
 * <BR>
 * 
 *         Comentários sobre a classe...
 * 
 */

package org.freedom.modulos.crm.view.frame.crud.detail;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.acao.InsertEvent;
import org.freedom.acao.InsertListener;
import org.freedom.acao.JComboBoxEvent;
import org.freedom.acao.JComboBoxListener;
import org.freedom.bmps.Icone;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JButtonPad;
import org.freedom.library.swing.component.JCheckBoxPad;
import org.freedom.library.swing.component.JComboBoxPad;
import org.freedom.library.swing.component.JPanelPad;
import org.freedom.library.swing.component.JRadioGroup;
import org.freedom.library.swing.component.JTabbedPanePad;
import org.freedom.library.swing.component.JTablePad;
import org.freedom.library.swing.component.JTextAreaPad;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FDetalhe;
import org.freedom.library.swing.frame.FPrinterJob;
import org.freedom.modulos.crm.business.object.FichaOrc;
import org.freedom.modulos.crm.business.object.ItOrcamento;
import org.freedom.modulos.crm.business.object.Orcamento;
import org.freedom.modulos.crm.dao.DAOFicha;
import org.freedom.modulos.crm.view.dialog.utility.DLContToCli;
import org.freedom.modulos.crm.view.frame.crud.plain.FAmbienteAval;
import org.freedom.modulos.crm.view.frame.crud.plain.FMotivoAval;
import org.freedom.modulos.crm.view.frame.crud.tabbed.FContato;
import org.freedom.modulos.gms.view.frame.crud.tabbed.FProduto;
import org.freedom.modulos.std.view.frame.crud.detail.FOrcamento;
import org.freedom.modulos.std.view.frame.crud.detail.FVenda;
import org.freedom.modulos.std.view.frame.crud.tabbed.FCliente;

public class FFichaAval extends FDetalhe implements InsertListener, CarregaListener, FocusListener, JComboBoxListener {

	private static final long serialVersionUID = 1L;
	
	
	private JTabbedPanePad tpnCab = new JTabbedPanePad();
	
	private JPanelPad pinFichaAval = new JPanelPad();

	private JPanelPad pinCabInfCompl = new JPanelPad();
	
	private JPanelPad pinCabOrcamento = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );
	
	private JTablePad tabOrcamento = new JTablePad();

	private JScrollPane spOrcamento = new JScrollPane( tabOrcamento );
	
	private JPanelPad pinObs = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 1, 1 ) );
	
	private JPanelPad pinCab = new JPanelPad();

	private JPanelPad pinDet = new JPanelPad();
	
	private JPanelPad pinImp = new JPanelPad(JPanelPad.TP_JPANEL);

	//FICHAAVAL
	
	private JTextFieldPad txtDtFichaAval = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );
	
	private JTextFieldPad txtSeqFichaAval = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );

	private JTextFieldPad txtCodCont = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );

	private JTextFieldFK txtRazCont = new JTextFieldFK( JTextFieldFK.TP_STRING, 50, 0 );
	
	private JTextFieldPad txtCodSetor = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldPad txtCodTipoCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );
	
	private JTextFieldPad txtCodMotAval= new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescMotAval = new JTextFieldFK( JTextFieldFK.TP_STRING, 50, 0 );

	private JTextFieldPad txtAndarFichaAval = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextAreaPad txaObsFichaAval = new JTextAreaPad();
	
	private JScrollPane spnObs = new JScrollPane( txaObsFichaAval );
	
	private JCheckBoxPad cbCobertFichaAval = new JCheckBoxPad( " INDICA SE É COBERTURA ?", "S", "N" );
	
	private JCheckBoxPad cbEstrutFichaAval = new JCheckBoxPad( "HÁ NECESSIDADE DE ESTRUTURA ?", "S", "N" );
	
	private JCheckBoxPad cbOcupadoFichaAval = new JCheckBoxPad( "IMÓVEL OCUPADO ?", "S", "N" );
		
	private JCheckBoxPad cbJanelaFichaAval = new JCheckBoxPad( "JANELAS ?", "S", "N" );
	
	private JCheckBoxPad cbSacadaFichaAval = new JCheckBoxPad( "SACADAS ?", "S", "N" );
	
	private JCheckBoxPad cbOutrosFichaAval = new JCheckBoxPad( "OUTROS ?", "S", "N" );
	
	//ITFICHAAVAL
	
	private JTextFieldPad txtSeqItFichaAval = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );
	
	private JTextFieldPad txtDescItFichaAval = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );
	
	private JTextFieldPad txtCodAmbAval = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 ); 
	
	private JTextFieldFK txtDescAmbAval = new JTextFieldFK( JTextFieldFK.TP_STRING, 50, 0 ); 
	
	private JTextFieldFK txtSiglaAmbAval = new JTextFieldFK( JTextFieldFK.TP_STRING, 10, 0 ); 
	
	private JTextFieldPad txtCodProd = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );
	
	private JTextFieldFK txtDescProd = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	private JTextFieldPad txtMatItFichaAval = new JTextFieldPad( JTextFieldPad.TP_STRING, 20, 0 );
	
	private JTextFieldPad txtMalhaItFichaAval = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );
	
	private JTextFieldPad txtCorItFichaAval = new JTextFieldPad( JTextFieldPad.TP_STRING, 20, 0 );
	
	private JTextFieldPad txtAltSupItFichaAval = new JTextFieldPad( JTextFieldPad.TP_STRING, 10, 0 );
	
	private JTextFieldPad txtAltItFichaAval = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, 2 );

	private JTextFieldPad txtAltInfItFichaAval = new JTextFieldPad( JTextFieldPad.TP_STRING, 10, 0 );
	
	private JTextFieldPad txtCompEsqItFichaAval = new JTextFieldPad( JTextFieldPad.TP_STRING, 10, 0 );
	
	private JTextFieldPad txtCompItFichaAval = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, 2 );

	private JTextFieldPad txtCompDirItFichaAval = new JTextFieldPad( JTextFieldPad.TP_STRING, 10, 0 );
	
	private JTextFieldPad txtEleFixItFichaAval = new JTextFieldPad( JTextFieldPad.TP_STRING, 20, 0 );

	private JTextFieldPad txtM2ItFichaAval = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, Aplicativo.casasDec );
		
	private JTextFieldPad txtDnmItFichaAval = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, Aplicativo.casasDec );
	
	private JTextFieldPad txtValorItFichaAval = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, Aplicativo.casasDec );
	
	private JTextFieldPad txtCodVarG1 = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8,0);
	
	private JTextFieldPad txtSeqItVarG1 = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8,0);
	
	private JTextFieldPad txtCodVarG2 = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8,0);
	
	private JTextFieldPad txtSeqItVarG2 = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8,0);
	
	private JTextFieldPad txtCodVarG3 = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8,0);
	
	private JTextFieldPad txtSeqItVarG3 = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8,0);
	
	private JTextFieldPad txtCodVarG4 = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8,0);
	
	private JTextFieldPad txtSeqItVarG4 = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8,0);
	
	// 1 Variante apontada no preferências gerais CRM.
	
	private Vector<Integer> vValsVarG1 = new Vector<Integer>();

	private Vector<String> vLabsVarG1 = new Vector<String>();
	
	private JComboBoxPad cbVarG1 = new JComboBoxPad( vLabsVarG1, vValsVarG1, JComboBoxPad.TP_INTEGER, 8, 0 );
	
	// 2 Variante apontada no preferências gerais CRM.
	
	private Vector<Integer> vValsVarG2 = new Vector<Integer>();

	private Vector<String> vLabsVarG2 = new Vector<String>();
	
	private JComboBoxPad cbVarG2 = new JComboBoxPad( vLabsVarG2, vValsVarG2, JComboBoxPad.TP_INTEGER, 8, 0 );
	
	// 3 Variante apontada no preferências gerais CRM.
	
	private Vector<Integer> vValsVarG3 = new Vector<Integer>();

	private Vector<String> vLabsVarG3 = new Vector<String>();
	
	private JComboBoxPad cbVarG3 = new JComboBoxPad( vLabsVarG3, vValsVarG3, JComboBoxPad.TP_INTEGER, 8, 0 );
	
	// 4 Variante apontada no preferências gerais CRM.
	
	private Vector<Integer> vValsVarG4 = new Vector<Integer>();

	private Vector<String> vLabsVarG4 = new Vector<String>();
	
	private JComboBoxPad cbVarG4 = new JComboBoxPad( vLabsVarG4, vValsVarG4, JComboBoxPad.TP_INTEGER, 8, 0 );
		
	private JRadioGroup<?, ?> rgLocalFichaAval = null;

	private JRadioGroup<?, ?> rgFinaliFichaAval = null;
	
	private JRadioGroup<?, ?> rgMobilFichaAval = null;
	
	private JCheckBoxPad cbPredentrfichaAval = new JCheckBoxPad( "PREDIO/CASA ESTÁ SENDO ENTREGUE AGORA?", "S", "N" );
	
	private ListaCampos lcContato = new ListaCampos( this, "CO" );
	
	private ListaCampos lcMotAval = new ListaCampos( this, "MA" );
	
	private ListaCampos lcProduto = new ListaCampos( this, "PD");
	
	private ListaCampos lcAmbAval = new ListaCampos( this, "AM");
	
	private ListaCampos lcVariante1 = new ListaCampos( this, "V1");
	
	private ListaCampos lcVariante2 = new ListaCampos( this, "V2");

	private ListaCampos lcVariante3 = new ListaCampos( this, "V3");
	
	private ListaCampos lcVariante4 = new ListaCampos( this, "V4");
	
	private JButtonPad btExportCli = new JButtonPad( Icone.novo( "btExportaCli.gif" ) );
	
	private JButtonPad btGeraOrc = new JButtonPad( Icone.novo( "btGerar.gif" ) );
	
	private DAOFicha daoficha = null;

	public FFichaAval() {

		nav.setNavigation( true );
		//nav.add( pinImp );
		//pinImp.add( btPrevimp );
		setTitulo( "Ficha Avaliativa" );
	
		setAtribos( 50, 50, 715, 600 );
		montaListaCampos();
		montaTela();
		
		btGeraOrc.setToolTipText( "Gerar Orçamento a partir da ficha avaliativa" );
		btPrevimp.setToolTipText( "Previsão da ficha avaliativa" );
		btExportCli.setToolTipText( "Transforma contato em cliente" );
		btPrevimp.addActionListener( this );
		btExportCli.addActionListener( this );
		btGeraOrc.addActionListener( this );
		lcCampos.addCarregaListener( this );
		txtCompItFichaAval.addFocusListener( this );
		cbVarG1.addComboBoxListener( this );
		cbVarG2.addComboBoxListener( this );
		cbVarG3.addComboBoxListener( this );
		cbVarG4.addComboBoxListener( this );
		lcDet.addCarregaListener( this );
		
	}
	
	public FFichaAval(DbConnection cn, int codCto){
		this();
		setConexao( cn );	
		lcCampos.insert( true );
		txtCodCont.setVlrInteger(codCto);
		lcContato.carregaDados();
		txtDtFichaAval.setVlrDate( new Date() );
		
	}
	
	public void montaListaCampos(){
		
		// FK Contato
		txtCodCont.setTabelaExterna( lcContato, FContato.class.getCanonicalName());
		txtCodCont.setFK( true );
		txtCodCont.setNomeCampo( "CodContr" );
		lcContato.add( new GuardaCampo( txtCodCont, "CodCto", "Cód.Contato", ListaCampos.DB_PK, false ) );
		lcContato.add( new GuardaCampo( txtRazCont, "RazCto", "Razão do contato.", ListaCampos.DB_SI, false ) );
		lcContato.add( new GuardaCampo( txtCodSetor, "CodSetor", "Cód.setor", ListaCampos.DB_SI, false ) );
		lcContato.add( new GuardaCampo( txtCodTipoCli, "CodTipoCli", "Cód.Tipo Cli.", ListaCampos.DB_SI, false ) );
		lcContato.montaSql( false, "CONTATO", "TK" );
		lcContato.setReadOnly( true );
		lcContato.setQueryCommit( false );
		
		// FK Motivo Aval.
		txtCodMotAval.setTabelaExterna( lcMotAval, FMotivoAval.class.getCanonicalName());
		txtCodMotAval.setFK( true );
		txtCodMotAval.setNomeCampo( "MotAval" );
		lcMotAval.add( new GuardaCampo( txtCodMotAval, "CodMotAval", "Cód.Motivo", ListaCampos.DB_PK, false ) );
		lcMotAval.add( new GuardaCampo( txtDescMotAval, "DescMotAval", "Descrição do motivo da avaliação.", ListaCampos.DB_SI, false ) );
		lcMotAval.montaSql( false, "MotivoAval", "CR" );
		lcMotAval.setReadOnly( true );
		lcMotAval.setQueryCommit( false );
		
		// FK Produto

		txtCodProd.setTabelaExterna( lcProduto, FProduto.class.getCanonicalName() );
		txtCodProd.setFK( true );
		txtCodProd.setNomeCampo( "CodProd" );
		lcProduto.add( new GuardaCampo( txtCodProd, "CodProd", "Cód.prod.", ListaCampos.DB_PK, false ) );
		lcProduto.add( new GuardaCampo( txtDescProd, "DescProd", "Descrição do produto", ListaCampos.DB_SI, false ) );
		lcProduto.montaSql( false, "PRODUTO", "EQ" );
		lcProduto.setReadOnly( true );
		lcProduto.setQueryCommit( false );
		
		// FK Ambiente Aval.
		txtCodAmbAval.setTabelaExterna( lcAmbAval, FAmbienteAval.class.getCanonicalName());
		txtCodAmbAval.setFK( true );
		txtCodAmbAval.setNomeCampo( "CodAmbAval" );
		lcAmbAval.add( new GuardaCampo( txtCodAmbAval, "CodAmbAval", "Cód.Ambiente", ListaCampos.DB_PK, false ) );
		lcAmbAval.add( new GuardaCampo( txtDescAmbAval, "DescAmbAval", "Descrição do ambiente.", ListaCampos.DB_SI, false ) );
		lcAmbAval.add( new GuardaCampo( txtSiglaAmbAval, "SiglaAmbAval", "Sigla.Amb.", ListaCampos.DB_SI, false ) );
		lcAmbAval.montaSql( false, "AmbienteAval", "CR" );
		lcAmbAval.setReadOnly( true );
		lcAmbAval.setQueryCommit( false );
		
		// Ficha Avaliativa.
		lcVariante1.add( new GuardaCampo( txtCodVarG1, "CodVarG", "Cód.var.1", ListaCampos.DB_PK, false ) );
		lcVariante1.add( new GuardaCampo( txtSeqItVarG1, "SeqItVarG1", "Sequência da Variante 1", ListaCampos.DB_PK,  false ) );
		lcVariante1.montaSql( false, "ITVARGRADE", "EQ" );
		lcVariante1.setQueryCommit( false );
		lcVariante1.setReadOnly( true );
		txtCodVarG1.setTabelaExterna(lcVariante1, null);
		txtCodVarG1.setListaCampos( lcVariante1 );
		
		lcVariante2.add( new GuardaCampo( txtCodVarG2, "CodVarG", "Cód.var.2", ListaCampos.DB_PK, false ) );
		lcVariante2.add( new GuardaCampo( txtSeqItVarG2, "SeqItVarG2", "Sequência da Variante 2", ListaCampos.DB_PK,  false ) );
		lcVariante2.montaSql( false, "ITVARGRADE", "EQ" );
		lcVariante2.setQueryCommit( false );
		lcVariante2.setReadOnly( true );
		txtCodVarG2.setTabelaExterna(lcVariante2, null);
		txtCodVarG2.setListaCampos( lcVariante2 );
		
		lcVariante3.add( new GuardaCampo( txtCodVarG3, "CodVarG", "Cód.var.3", ListaCampos.DB_PK, false));
		lcVariante3.add( new GuardaCampo( txtSeqItVarG3, "SeqItVarG3", "Sequência da Variante 3", ListaCampos.DB_PK, false));
		lcVariante3.montaSql( false,"ITVARGRADE", "EQ");
		lcVariante3.setQueryCommit( false);
		lcVariante3.setReadOnly( true );
		txtCodVarG3.setTabelaExterna(lcVariante3, null);
		txtCodVarG3.setListaCampos( lcVariante3 );
		
		lcVariante4.add( new GuardaCampo( txtCodVarG4, "CodVarG", "Cód.var.4", ListaCampos.DB_PK, false));
		lcVariante4.add( new GuardaCampo( txtSeqItVarG4, "SeqItVarG4", "Sequência da Variante 4", ListaCampos.DB_PK, false));
		lcVariante4.montaSql( false,"ITVARGRADE", "EQ");
		lcVariante4.setQueryCommit( false);
		lcVariante4.setReadOnly( true );
		txtCodVarG4.setTabelaExterna(lcVariante4, null);
		txtCodVarG4.setListaCampos( lcVariante4 );
	
		
	}
	
	public void montaTela(){
		montaGrupoRadio();

		adicAbas();
		
		montaCombos();
		
		setListaCampos( lcCampos );
		setAltCab( 220 );
		setPainel( pinFichaAval );
			
		adicCampo( txtSeqFichaAval, 7, 20, 80, 20, "SeqFichaAval", "Seq.Ficha", ListaCampos.DB_PK, true );
		adicCampo( txtCodCont, 90, 20, 80, 20, "CodCto", "Cód.Contato", ListaCampos.DB_FK, txtRazCont, true );
		adicDescFK( txtRazCont, 173, 20, 351, 20, "RazCto", "Razão do contato" );
		adicCampo( txtDtFichaAval, 530, 20, 110, 20, "DtFichaAval", "Dt.ficha aval.", ListaCampos.DB_SI, true );
	
		adicCampo( txtCodMotAval, 7, 60, 80, 20, "CodMotAval", "Cód.Motivo", ListaCampos.DB_FK ,txtDescMotAval, true );
		adicDescFK( txtDescMotAval, 90, 60, 467, 20, "DescMotAval", "Descrição do motivo da avaliação" );
		adicCampo( txtAndarFichaAval, 560, 60, 80, 20, "AndarFichaAval", "Andar", ListaCampos.DB_SI , true );
		
		adicDB( rgLocalFichaAval, 7, 100, 320, 30, "LocalFichaAval", "Local Ficha Avaliativa", false );
		adicDB( rgFinaliFichaAval, 330, 100, 320, 30, "FinaliFichaAval", "Finalidade Ficha Avaliativa", false );
		adicDB( cbPredentrfichaAval, 7, 130, 500, 30, "PredentrfichaAval", "", false );
		
		adicDBLiv( txaObsFichaAval, "ObsFichaAval", "Observações ficha aval", false );
		setPainel( pinCabInfCompl );
		
		adicDB( cbCobertFichaAval, 7, 20, 300, 20, "CobertFichaAval", "", true );
		adicDB( cbEstrutFichaAval, 310, 20, 300, 20, "EstrutFichaAval", "", true );
		
		adicDB( cbOcupadoFichaAval, 7, 50, 300, 20, "OcupadoFichaAval", "", true );
		adicDB( cbJanelaFichaAval, 310, 50, 300, 20, "JanelaFichaAval", "", true );
		
		adicDB( cbSacadaFichaAval, 7, 80, 300, 20, "SacadaFichaAval", "", true );
		adicDB( cbOutrosFichaAval, 310, 80, 300, 20, "OutrosFichaAval", "", true );
		
		adicDB( rgMobilFichaAval, 7, 120, 320, 30, "MobilFichaAval", "Imóvel", false );

		setListaCampos( true, "FICHAAVAL", "CR" );
		lcCampos.setQueryInsert( false );
		
		tpnCab.addTab( "Orçamento", pinCabOrcamento );

		pinCabOrcamento.add( spOrcamento, BorderLayout.CENTER );

		tabOrcamento.adicColuna( "Cód.Orc" );
		tabOrcamento.adicColuna( "Cód.cli." );
		tabOrcamento.adicColuna( "Emissão" );
		tabOrcamento.adicColuna( "Vencimento" );
		tabOrcamento.adicColuna( "Cód.pag." );
		tabOrcamento.adicColuna( "Item" );
		tabOrcamento.adicColuna( "Quantidade" );
		tabOrcamento.adicColuna( "Preço" );
		tabOrcamento.adicColuna( "Tipo Orc" );

		tabOrcamento.setTamColuna( 80, Orcamento.GET_ORC.CODORC.ordinal() );
		tabOrcamento.setTamColuna( 80, Orcamento.GET_ORC.CODCLI.ordinal() );
		tabOrcamento.setTamColuna( 80, Orcamento.GET_ORC.DTEMISSAO.ordinal() );
		tabOrcamento.setTamColuna( 80, Orcamento.GET_ORC.DTVENC.ordinal() );
		tabOrcamento.setTamColuna( 80, Orcamento.GET_ORC.CODPAG.ordinal() );
		tabOrcamento.setTamColuna( 80, Orcamento.GET_ORC.CODITORC.ordinal() );
		tabOrcamento.setTamColuna( 80, Orcamento.GET_ORC.QTDITORC.ordinal() );
		tabOrcamento.setTamColuna( 80, Orcamento.GET_ORC.PRECOITORC.ordinal() );
		tabOrcamento.setColunaInvisivel( Orcamento.GET_ORC.TIPOORC.ordinal() );

		tabOrcamento.addMouseListener( new MouseAdapter() {

			@ Override
			public void mouseClicked( MouseEvent e ) {

				if ( e.getClickCount() == 2 ) {
					if ( e.getSource() == tabOrcamento ) {
						abreOrcamento();
					}
				}
			}
		} );
		
		setAltDet( 190 );
		pinDet = new JPanelPad( 600, 80 );
		setPainel( pinDet, pnDet );
		setListaCampos( lcDet );
		lcDet.setMaster( lcCampos );
		setNavegador( navRod );
		
		adicCampo( txtSeqItFichaAval, 7, 25, 60, 20, "SeqItFichaAval", "Seq.item", ListaCampos.DB_PK, true );
		adicCampo( txtCodAmbAval, 70, 25, 60, 20, "CodAmbAval", "Cód.Amb.", ListaCampos.DB_FK, txtDescAmbAval, true );
		adicDescFK( txtSiglaAmbAval, 133, 25, 60, 20, "SiglaAmbAval", "Sigla.Amb.");
		adicCampo( txtDescItFichaAval, 196, 25, 195, 20, "DescItFichaAval", "Descrição", ListaCampos.DB_SI, true );
		adicCampo( txtMatItFichaAval, 394, 25, 80, 20, "MaterialItFichaAval", "Material", ListaCampos.DB_SI, true );
		adicCampo( txtMalhaItFichaAval, 477, 25, 80, 20, "MalhaItFichaAval", "Malha", ListaCampos.DB_SI, true );
		adicCampo( txtCorItFichaAval, 560, 25, 80, 20, "CorItFichaAval", "Cor", ListaCampos.DB_SI, true );
		
		adicCampo( txtCodProd, 7, 65, 60, 20, "CodProd", "Cód.prod.", ListaCampos.DB_FK, txtDescProd, true );
		adicDescFK( txtDescProd, 70, 65, 321, 20, "DescProd", "Descrição do produto/serviço" );
		adicCampo( txtAltSupItFichaAval, 394, 65, 80, 20, "AltSupItFichaAval", "Alt.sup.", ListaCampos.DB_SI, true );
		adicCampo( txtAltItFichaAval, 477, 65, 80, 20, "AltItFichaAval", "Altura", ListaCampos.DB_SI, true );
		adicCampo( txtAltInfItFichaAval, 560, 65, 80, 20, "AltInfItFichaAval", "Alt.inf.", ListaCampos.DB_SI, true );
		adicCampo( txtCompEsqItFichaAval, 7, 105, 80, 20, "CompEsqItFichaAval", "Comp.esq.", ListaCampos.DB_SI, true );
		adicCampo( txtCompItFichaAval, 90, 105, 80, 20, "CompItFichaAval", "Comprimento", ListaCampos.DB_SI, true );
		adicCampo( txtCompDirItFichaAval, 173, 105, 80, 20, "CompDirItFichaAval", "Comp.dir.", ListaCampos.DB_SI, true );
		adicCampo( txtM2ItFichaAval, 256, 105, 80, 20, "M2ItFichaAval", "M²", ListaCampos.DB_SI, true );
		adicCampo( txtEleFixItFichaAval, 339, 105, 80, 20, "EleFixItFichaAval", "Elem.Fixação", ListaCampos.DB_SI, true );
		adicCampo( txtValorItFichaAval, 422, 105, 80, 20, "ValorItFichaAval", "Valor", ListaCampos.DB_SI, true );
		
		
		adic( cbVarG1, 7, 145, 150, 20, "seqitvarg1");
		adic( cbVarG2, 160, 145, 150, 20, "seqitvarg2" );
		adic( cbVarG3, 313, 145, 150, 20, "seqitvarg3" );
		adic( cbVarG4, 466, 145, 150, 20, "seqitvarg4" );
		
		adicCampoInvisivel( txtCodVarG1, "codvarg1", "Cód.var.1", ListaCampos.DB_FK, false);
		adicCampoInvisivel( txtSeqItVarG1, "Seqitvarg1", "Seq.it.var.1", ListaCampos.DB_FK, false);
		adicCampoInvisivel( txtCodVarG2, "codvarg2", "Cód.var.2", ListaCampos.DB_FK, false);
		adicCampoInvisivel( txtSeqItVarG2, "Seqitvarg2", "Seq.it.var.2", ListaCampos.DB_FK, false);
		adicCampoInvisivel( txtCodVarG3, "codvarg3", "Cód.var.3", ListaCampos.DB_FK, false);
		adicCampoInvisivel( txtSeqItVarG3, "Seqitvarg3", "Seq.it.var.3", ListaCampos.DB_FK, false);
		adicCampoInvisivel( txtCodVarG4, "codvarg4", "Cód.var.4", ListaCampos.DB_FK, false);
		adicCampoInvisivel( txtSeqItVarG4, "Seqitvarg4", "Seq.it.var.4", ListaCampos.DB_FK, false);
		
		
		//adicDB( cbVarG2, 206, 60, 314, 20, "SEQITVARG2", "",false );
		
		setListaCampos( true, "ITFICHAAVAL", "CR" );
		lcDet.setQueryInsert( false );
		
		montaTab();
		
		pnGImp.removeAll();
		pnGImp.setLayout( new GridLayout( 1, 2 ) );
		pnGImp.setPreferredSize( new Dimension( 93, 26 ) );
		pnGImp.add( btExportCli );
		pnGImp.add( btGeraOrc );
		pnGImp.add( btPrevimp );
		setImprimir( true );
		lcCampos.addInsertListener( this );
	
	}
	
	public void montaCombos(){
		
		vValsVarG1.addElement( -1 );
		vLabsVarG1.addElement( "<nenhum>" );
		cbVarG1.setItensGeneric( vLabsVarG1, vValsVarG1 );
		
		vValsVarG2.addElement( -1 );
		vLabsVarG2.addElement( "<Todos>" );
		cbVarG2.setItensGeneric( vLabsVarG2, vValsVarG2 );
		
		vValsVarG3.addElement( -1 );
		vLabsVarG3.addElement( "<Todos>" );
		cbVarG3.setItensGeneric( vLabsVarG3, vValsVarG3 );
		
		vValsVarG4.addElement( -1 );
		vLabsVarG4.addElement( "<Todos>" );
		cbVarG4.setItensGeneric( vLabsVarG4, vValsVarG4 );
		
	}
	
	public void abreOrcamento(){
		if ( tabOrcamento.getLinhaSel() > -1 ) {
			FOrcamento tela = null;
			if ( Aplicativo.telaPrincipal.temTela( FVenda.class.getName() ) ) {
				tela = (FOrcamento) Aplicativo.telaPrincipal.getTela( FOrcamento.class.getName() );
			} else {
				tela = new FOrcamento();
				Aplicativo.telaPrincipal.criatela( "Orçamento", tela, con );
			}
			tela.exec( (Integer) tabOrcamento.getValor( tabOrcamento.getLinhaSel(), Orcamento.GET_ORC.CODORC.ordinal() ));
		}
	}
	
	private void carregaOrcamentos() {

		tabOrcamento.limpa();
		ResultSet rs = null;
		try {

			List<Orcamento> orcs =	daoficha.loadOrcamento( Aplicativo.iCodEmp, ListaCampos.getMasterFilial( "CRFICHAORC" ), txtSeqFichaAval.getVlrInteger() );
			int row = 0;
			for ( Orcamento o : orcs) {
				
				tabOrcamento.adicLinha();
				tabOrcamento.setValor( o.getCodorc(), row, Orcamento.GET_ORC.CODORC.ordinal() );
				tabOrcamento.setValor( o.getCodcli(), row, Orcamento.GET_ORC.CODCLI.ordinal() );
				tabOrcamento.setValor( o.getDtorc() , row, Orcamento.GET_ORC.DTEMISSAO.ordinal() );
				tabOrcamento.setValor( o.getDtvencorc() , row, Orcamento.GET_ORC.DTVENC.ordinal() );
				tabOrcamento.setValor( o.getCodplanopag(), row, Orcamento.GET_ORC.CODPAG.ordinal() );
				tabOrcamento.setValor( o.getCoditorc(), row, Orcamento.GET_ORC.CODITORC.ordinal() );
				tabOrcamento.setValor( o.getQtditorc(), row, Orcamento.GET_ORC.QTDITORC.ordinal() );
				tabOrcamento.setValor( o.getPrecoitorc() ,row, Orcamento.GET_ORC.PRECOITORC.ordinal() );
				tabOrcamento.setValor( o.getTipoorc(), row, Orcamento.GET_ORC.TIPOORC.ordinal() );
				row++;
			}
		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro ao consultar orcamento!\n" + err.getMessage(), true, con, err );
			err.printStackTrace();
		}
	}
	
	public void setImprimir(boolean bImp) {
		btImp.setVisible(bImp);
		btPrevimp.setVisible(bImp);
	}
	
	private void montaGrupoRadio(){
		
		Vector<String> vValsLocal = new Vector<String>();
		Vector<String> vLabsLocal = new Vector<String>();
		vLabsLocal.addElement( "Apartamento" );
		vLabsLocal.addElement( "Casa" );
		vLabsLocal.addElement( "Empresa" );
		vValsLocal.addElement( "A" );
		vValsLocal.addElement( "C" );
		vValsLocal.addElement( "E" );
		rgLocalFichaAval = new JRadioGroup<String, String>( 1, 3, vLabsLocal, vValsLocal );
		rgLocalFichaAval.setVlrString( "A" );

		Vector<String> vValsFinali = new Vector<String>();
		Vector<String> vLabsFinali = new Vector<String>();
		vLabsFinali.addElement( "Criança" );
		vLabsFinali.addElement( "Animal" );
		vLabsFinali.addElement( "Outros" );
		vValsFinali.addElement( "C" );
		vValsFinali.addElement( "A" );
		vValsFinali.addElement( "O" );
		rgFinaliFichaAval = new JRadioGroup<String, String>( 1, 3, vLabsFinali, vValsFinali );
		rgFinaliFichaAval.setVlrString( "C" );
		
		Vector<String> vValsMobilidade = new Vector<String>();
		Vector<String> vLabsMobilidade = new Vector<String>();
		vLabsMobilidade.addElement( "Mobiliado" );
		vLabsMobilidade.addElement( "Semi-Mobiliado" );
		vLabsMobilidade.addElement( "Vazio" );
		vValsMobilidade.addElement( "M" );
		vValsMobilidade.addElement( "S" );
		vValsMobilidade.addElement( "V" );
		rgMobilFichaAval = new JRadioGroup<String, String>( 1, 3, vLabsFinali, vValsFinali );
		rgMobilFichaAval.setVlrString( "M" );

	}
	
	private void adicAbas() {

		pnCliCab.add( tpnCab );
		tpnCab.addTab( "Ficha Avaliativa", pinFichaAval );
		tpnCab.addTab( "Inf.Complementares", pinCabInfCompl );
		pinObs.add( spnObs );
		tpnCab.addTab( "Observações", pinObs );

	}

	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == btPrevimp ) {
			impFichaAval( txtCodCont.getVlrInteger(), txtSeqFichaAval.getVlrInteger() );
	
		}		else if ( evt.getSource() == btGeraOrc ) {
			geraOrcamento();
			
		} else if (evt.getSource() == btExportCli){
			exportaCli();
		}
		super.actionPerformed( evt );
	}

	public void impFichaAval(final int codcont, final int seqficha){
		
		Blob fotoemp = FPrinterJob.getLogo( con );
		
		StringBuilder sql = daoficha.getSqlFichaAval();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = con.prepareStatement( sql.toString() );
			int param = 1;
			ps.setInt( param++, Aplicativo.iCodEmp );
			ps.setInt( param++, ListaCampos.getMasterFilial( "TKCONTATO" ) );
			ps.setInt( param++, codcont );
			ps.setInt( param++, Aplicativo.iCodEmp );
			ps.setInt( param++, ListaCampos.getMasterFilial( "CRFICHAAVAL" ) );
			ps.setInt( param++, seqficha );
			ps.setInt( param++, Aplicativo.iCodEmp );
			ps.setInt( param++, ListaCampos.getMasterFilial( "SGFILIAL" ) );
			
			rs = ps.executeQuery();
		} catch (SQLException e) {
			Funcoes.mensagemErro( this, "Erro executando consulta: \n" + e.getMessage() );
			e.printStackTrace();
		}
		

		FPrinterJob dlGr = null;
		HashMap<String, Object> hParam = new HashMap<String, Object>();

		hParam.put( "CODEMP", Aplicativo.iCodEmp );
		hParam.put( "CODFILIAL", ListaCampos.getMasterFilial( "TKCONTATO" ) );
		hParam.put( "RAZAOEMP", Aplicativo.empresa.toString() );
		hParam.put( "SUBREPORT_DIR", "org/freedom/relatorios/");
		try {
			hParam.put( "LOGOEMP", new ImageIcon(fotoemp.getBytes(1, ( int ) fotoemp.length())).getImage() );
		} catch ( SQLException e ) {
			e.printStackTrace();
		}
		
		if(tab.getNumLinhas() >0 ){
			dlGr = new FPrinterJob( "relatorios/ficha_avaliativa_091_Preenchido.jasper", "Ficha avaliativa", "", rs, hParam, this );
		} else { 
			dlGr = new FPrinterJob( daoficha.getPrefs()[FichaOrc.PREFS.LAYOUTFICHAAVAL.ordinal()].toString(), "Ficha avaliativa", "", rs, hParam, this );
		}
		
		dlGr.setVisible( true );
		
	}
	
	private void exportaCli() {

		if ( txtCodCont.getVlrInteger().intValue() == 0 || lcCampos.getStatus() != ListaCampos.LCS_SELECT ) {
			Funcoes.mensagemInforma( this, "Selecione um contato cadastrado antes!" );
			return;
		}

		DLContToCli dl = new DLContToCli( this, txtCodSetor.getVlrInteger() , txtCodTipoCli.getVlrInteger() );
		dl.setConexao( con );
		dl.setVisible( true );

		if ( !dl.OK ) {
			dl.dispose();
			return;
		}

		DLContToCli.ContatoClienteBean contatoClienteBean = dl.getValores();

		dl.dispose();

		try {

			PreparedStatement ps = con.prepareStatement( "SELECT IRET FROM TKCONTCLISP(?,?,?,?,?,?,?,?,?)" );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, lcCampos.getCodFilial() );
			ps.setInt( 3, txtCodCont.getVlrInteger().intValue() );
			ps.setInt( 4, lcCampos.getCodFilial() );
			ps.setInt( 5, contatoClienteBean.getTipo() );
			ps.setInt( 6, lcCampos.getCodFilial() );
			ps.setInt( 7, contatoClienteBean.getClassificacao() );
			ps.setInt( 8, lcCampos.getCodFilial() );
			ps.setInt( 9, contatoClienteBean.getSetor() );

			ResultSet rs = ps.executeQuery();

			if ( rs.next() ) {
				if ( Funcoes.mensagemConfirma( this, "Cliente '" + rs.getInt( 1 ) + "' criado com sucesso!\nGostaria de edita-lo agora?" ) == JOptionPane.OK_OPTION ) {
					abreCli( rs.getInt( 1 ) );
				}
			}

			rs.close();
			ps.close();
			con.commit();

		} catch ( SQLException err ) {
			err.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao criar cliente!\n" + err.getMessage(), true, con, err );
		}
	}
	
	private void geraOrcamento(){
		
	
		if ( Funcoes.mensagemConfirma( this, "Deseja realmente gerar orçamento a partir da ficha avaliativa?" ) == JOptionPane.OK_OPTION ) {
			boolean bPrim = true;
			int row = 0;
			Integer codorc = null;
			
			try {
				for(row = 0; row < tab.getNumLinhas(); row++){
					if(bPrim){
						codorc = daoficha.gravaCabOrc( Aplicativo.iCodEmp, ListaCampos.getMasterFilial( "VDORCAMENTO" ), txtCodCont.getVlrInteger(), new Date(), 
								new Date(), Integer.valueOf( daoficha.getPrefs()[FichaOrc.PREFS.CODPLANOPAG.ordinal()].toString()) );
					}
					Integer numseq = (Integer) tab.getValor( row, ItOrcamento.COLITORC.SEQITFICHAAVAL.ordinal() ) ;
					
					ItOrcamento item = new ItOrcamento();
					item.setCodemp( Aplicativo.iCodEmp );
					item.setCodfilial( ListaCampos.getMasterFilial( "VDITORCAMENTO" ) );
					item.setTipoorc( "O" );
					item.setCodorc( codorc );
					item.setCoditorc( numseq );
					item.setCodemppd( Aplicativo.iCodEmp );
					item.setCodfilialpd( ListaCampos.getMasterFilial( "EQPRODUTO" ) );
					item.setCodprod(  (Integer) tab.getValor( row, ItOrcamento.COLITORC.CODPROD.ordinal() )  );
					item.setCodempax( Aplicativo.iCodEmp );
					item.setCodfilialax( ListaCampos.getMasterFilial( "EQALMOX" ) );
					item.setCodalmox( 1 );
					item.setQtditorc( new BigDecimal("0") );
					item.setPrecoitorc( daoficha.getPrecoBase( Aplicativo.iCodEmp, ListaCampos.getMasterFilial( "EQPRODUTO" ), 
							(Integer) tab.getValor( row, ItOrcamento.COLITORC.CODPROD.ordinal() ) ));
					
					daoficha.insert_item_orc( item );
					
					FichaOrc ficha = new FichaOrc();
					ficha.setCodemp( Aplicativo.iCodEmp );
					ficha.setCodfilial( ListaCampos.getMasterFilial( "CRFICHAAVAL" ) );
					ficha.setSeqfichaaval( txtSeqFichaAval.getVlrInteger() );
					ficha.setSeqitfichaaval( numseq );
					ficha.setCodempor( Aplicativo.iCodEmp );
					ficha.setCodfilialor( ListaCampos.getMasterFilial( "VDITORCAMENTO" ) );
					ficha.setTipoorc( "O" );
					ficha.setCodorc( codorc );
					ficha.setCoditorc( (Integer) tab.getValor( row, ItOrcamento.COLITORC.SEQITFICHAAVAL.ordinal() ) );
					
					daoficha.insert_fichaorc( ficha );
					
					bPrim = false;
					con.commit();
					
					
				}
				Funcoes.mensagemInforma( this, "Orçamento gerado com sucesso!" );
			} catch ( NumberFormatException e ) {
				e.printStackTrace();
	
			} catch ( SQLException e ) {
				Funcoes.mensagemErro( this, "O orçamento não pode ser gerado!" );
				e.printStackTrace();
				try {
					con.rollback();
				} catch ( SQLException e1 ) {
					e1.printStackTrace();
				}
			}
		}
	}
	
	private void abreCli( int codigoCliente ) {

		FCliente cliente = null;
		if ( Aplicativo.telaPrincipal.temTela( FCliente.class.getName() ) ) {
			cliente = (FCliente) Aplicativo.telaPrincipal.getTela( FCliente.class.getName() );
		}
		else {
			cliente = new FCliente();
			Aplicativo.telaPrincipal.criatela( "Cliente", cliente, con );
		}

		cliente.exec( codigoCliente );
	}

	public void beforeInsert( InsertEvent ievt ) {

	}

	public void afterInsert( InsertEvent ievt ) {
		if(ievt.getListaCampos() == lcCampos) {
			txtAndarFichaAval.setVlrInteger( 0 );
		}	
	}
	
	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		lcContato.setConexao( cn );
		lcMotAval.setConexao( cn );
		lcProduto.setConexao( cn );
		lcAmbAval.setConexao( cn );
		
		daoficha = new DAOFicha( cn );
		try{
		daoficha.setPrefs( Aplicativo.iCodEmp, ListaCampos.getMasterFilial( "SGPREFERE3" ) );
		}catch (SQLException e) {
			Funcoes.mensagemErro( this, "Erro carregando preferências !\b" + e.getMessage() );
			e.printStackTrace();
		}
	}



	public void beforeCarrega( CarregaEvent cevt ) {

	}

	public void carregaCombo() {
		
		txtCodVarG1.setVlrInteger( new Integer(daoficha.getPrefs()[FichaOrc.PREFS.CODVARG1.ordinal()].toString()));
	 	txtCodVarG2.setVlrInteger( new Integer(daoficha.getPrefs()[FichaOrc.PREFS.CODVARG2.ordinal()].toString()));
	 	txtCodVarG3.setVlrInteger( new Integer(daoficha.getPrefs()[FichaOrc.PREFS.CODVARG3.ordinal()].toString()));
	 	txtCodVarG4.setVlrInteger( new Integer(daoficha.getPrefs()[FichaOrc.PREFS.CODVARG4.ordinal()].toString()));
	 	
		HashMap<String, Vector<Object>> vals = daoficha.montaComboFicha( con, txtCodVarG1.getVlrInteger() , "nenhum" );
		cbVarG1.setItensGeneric( (Vector<?>) vals.get( "LAB" ), (Vector<?>) vals.get( "VAL" ) );
		
		HashMap<String, Vector<Object>> vals2 = daoficha.montaComboFicha( con, txtCodVarG2.getVlrInteger() , "nenhum" );
		cbVarG2.setItensGeneric( (Vector<?>) vals2.get( "LAB" ), (Vector<?>) vals.get( "VAL" ) );
		
		HashMap<String, Vector<Object>> vals3 = daoficha.montaComboFicha( con, txtCodVarG3.getVlrInteger() , "nenhum" );
		cbVarG3.setItensGeneric( (Vector<?>) vals3.get( "LAB" ), (Vector<?>) vals.get( "VAL" ) );
		
		HashMap<String, Vector<Object>> vals4 = daoficha.montaComboFicha( con, txtCodVarG4.getVlrInteger()  , "nenhum" );
		cbVarG4.setItensGeneric( (Vector<?>) vals4.get( "LAB" ), (Vector<?>) vals.get( "VAL" ) );
	
	}

	public void afterCarrega( CarregaEvent cevt ) {
		 if ( cevt.getListaCampos() == lcCampos ) {
			 carregaOrcamentos();			 
			 carregaCombo();					
		 }		
		 
		 if( cevt.getListaCampos() == lcDet){
			carregaCombo();
		 }
	}	
	
	public void calcM2(){

		BigDecimal vlrm2 = txtAltItFichaAval.getVlrBigDecimal().multiply( txtCompItFichaAval.getVlrBigDecimal() );

		txtM2ItFichaAval.setVlrBigDecimal( vlrm2 );

	}

	public void focusGained( FocusEvent e ) {
		
	}

	public void focusLost( FocusEvent fevt ) {
		if( fevt.getSource() == txtCompItFichaAval ){
			if(( txtAltItFichaAval.getVlrBigDecimal().floatValue() > 0 ) && ( txtCompItFichaAval.getVlrBigDecimal().floatValue() > 0 ) ) {
				calcM2();
			}
			else {
				if(txtAltItFichaAval.getVlrBigDecimal().floatValue() <= 0) {
					Funcoes.mensagemInforma( this, "Informe a altura para o calculo do m²!!!" );
				} else {
					Funcoes.mensagemInforma( this, "Informe o comprimento para o calculo do m²!!!" );
				}
				txtM2ItFichaAval.setVlrBigDecimal( new BigDecimal(0) );
			}
		}
	}

	public void valorAlterado( JComboBoxEvent evt ) {
		
		if ( evt.getComboBoxPad() == cbVarG1 ) {
			if(cbVarG1.getVlrInteger() > -1){
				txtCodVarG1.setVlrInteger( new Integer(daoficha.getPrefs()[FichaOrc.PREFS.CODVARG1.ordinal()].toString()));
				txtSeqItVarG1.setVlrInteger( cbVarG1.getVlrInteger() );
			}
			
		} else if ( evt.getComboBoxPad() == cbVarG2 ) {
			if(cbVarG2.getVlrInteger() > -1){
				txtCodVarG2.setVlrInteger( new Integer(daoficha.getPrefs()[FichaOrc.PREFS.CODVARG2.ordinal()].toString()));
				txtSeqItVarG2.setVlrInteger( cbVarG2.getVlrInteger() );
			}
			
		} else if ( evt.getComboBoxPad() == cbVarG3 ) {
			if(cbVarG3.getVlrInteger() > -1){
				txtCodVarG3.setVlrInteger( new Integer(daoficha.getPrefs()[FichaOrc.PREFS.CODVARG3.ordinal()].toString()));
				txtSeqItVarG3.setVlrInteger( cbVarG3.getVlrInteger() );
			}
			
		} else if ( evt.getComboBoxPad() == cbVarG4 ) {
			if(cbVarG4.getVlrInteger() > -1){
				txtCodVarG4.setVlrInteger( new Integer(daoficha.getPrefs()[FichaOrc.PREFS.CODVARG4.ordinal()].toString()));
				txtSeqItVarG4.setVlrInteger( cbVarG4.getVlrInteger() );
			}
			
		}
		
	}
}
