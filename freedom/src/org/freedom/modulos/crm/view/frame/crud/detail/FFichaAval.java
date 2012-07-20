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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.acao.InsertEvent;
import org.freedom.acao.InsertListener;
import org.freedom.bmps.Icone;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JButtonPad;
import org.freedom.library.swing.component.JCheckBoxPad;
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
import org.freedom.modulos.crm.dao.DAOFicha;
import org.freedom.modulos.crm.view.dialog.utility.DLContToCli;
import org.freedom.modulos.crm.view.frame.crud.plain.FAmbienteAval;
import org.freedom.modulos.crm.view.frame.crud.plain.FMotivoAval;
import org.freedom.modulos.crm.view.frame.crud.tabbed.FContato;
import org.freedom.modulos.gms.view.frame.crud.tabbed.FProduto;
import org.freedom.modulos.std.view.frame.crud.tabbed.FCliente;

public class FFichaAval extends FDetalhe implements InsertListener, CarregaListener {

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
	
	private JTextFieldPad txtAltItFichaAval = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, 2 );

	private JTextFieldPad txtLargItFichaAval = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, 2 );

	private JTextFieldPad txtDnmItFichaAval = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, 2 );
	
	private JRadioGroup<?, ?> rgLocalFichaAval = null;

	private JRadioGroup<?, ?> rgFinaliFichaAval = null;
	
	private JRadioGroup<?, ?> rgMobilFichaAval = null;
	
	private JCheckBoxPad cbPredentrfichaAval = new JCheckBoxPad( "PREDIO/CASA ESTÁ SENDO ENTREGUE AGORA?", "S", "N" );
	
	private ListaCampos lcContato = new ListaCampos( this, "CO" );
	
	private ListaCampos lcMotAval = new ListaCampos( this, "MA" );
	
	private ListaCampos lcProduto = new ListaCampos( this, "PD");
	
	private ListaCampos lcAmbAval = new ListaCampos( this, "AM");
	
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
		
		btPrevimp.setToolTipText( "Previsão da ficha avaliativa" );
		btExportCli.setToolTipText( "Transforma contato em cliente" );
		btPrevimp.addActionListener( this );
		btExportCli.addActionListener( this );
		btGeraOrc.addActionListener( this );
		lcCampos.addCarregaListener( this );
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
		
	}
	
	public void montaTela(){
		montaGrupoRadio();

		adicAbas();
		
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
		adicDB( cbPredentrfichaAval, 7, 130, 320, 30, "PredentrfichaAval", "", false );
		
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
		tabOrcamento.adicColuna( "Razão social do cliente" );
		tabOrcamento.adicColuna( "Emissão" );
		tabOrcamento.adicColuna( "Vencimento" );
		tabOrcamento.adicColuna( "Cód.pag." );
		tabOrcamento.adicColuna( "Descrição do plano de pagamento" );
		tabOrcamento.adicColuna( "Item" );
		tabOrcamento.adicColuna( "Quantidade" );
		tabOrcamento.adicColuna( "Preço" );
		tabOrcamento.adicColuna( "Tipo Orc" );

		tabOrcamento.setTamColuna( 80, FichaOrc.GET_ORC.CODORC.ordinal() );
		tabOrcamento.setTamColuna( 80, FichaOrc.GET_ORC.CODCLI.ordinal() );
		tabOrcamento.setTamColuna( 200, FichaOrc.GET_ORC.RAZCLI.ordinal() );
		tabOrcamento.setTamColuna( 80, FichaOrc.GET_ORC.DTEMISSAO.ordinal() );
		tabOrcamento.setTamColuna( 80, FichaOrc.GET_ORC.DTVENC.ordinal() );
		tabOrcamento.setTamColuna( 80, FichaOrc.GET_ORC.CODPAG.ordinal() );
		tabOrcamento.setTamColuna( 200, FichaOrc.GET_ORC.DESCPAG.ordinal() );
		tabOrcamento.setTamColuna( 80, FichaOrc.GET_ORC.CODITORC.ordinal() );
		tabOrcamento.setTamColuna( 80, FichaOrc.GET_ORC.QTDITORC.ordinal() );
		tabOrcamento.setTamColuna( 80, FichaOrc.GET_ORC.PRECOITORC.ordinal() );
		tabOrcamento.setColunaInvisivel( FichaOrc.GET_ORC.TIPOORC.ordinal() );

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
		
		setAltDet( 100 );
		pinDet = new JPanelPad( 600, 80 );
		setPainel( pinDet, pnDet );
		setListaCampos( lcDet );
		setNavegador( navRod );
		
		adicCampo( txtSeqItFichaAval, 7, 25, 60, 20, "SeqItFichaAval", "Seq.item", ListaCampos.DB_PK, true );
		adicCampo( txtCodAmbAval, 70, 25, 60, 20, "CodAmbAval", "Cód.Amb.", ListaCampos.DB_FK, txtDescAmbAval, true );
		adicDescFK( txtSiglaAmbAval, 133, 25, 60, 20, "SiglaAmbAval", "Sigla.Amb.");
		//adicDescFK( txtDescAmbAval, 70, 25, 480, 20, "DescAmbAval", "Descrição do Ambiente");
		adicCampo( txtDescItFichaAval, 196, 25, 444, 20, "DescItFichaAval", "Descrição", ListaCampos.DB_SI, true );
		
		adicCampo( txtCodProd, 7, 65, 60, 20, "CodProd", "Cód.prod.", ListaCampos.DB_FK, txtDescProd, true );
		adicDescFK( txtDescProd, 70, 65, 321, 20, "DescProd", "Descrição do produto/serviço" );
		adicCampo( txtAltItFichaAval, 394, 65, 80, 20, "AltItFichaAval", "Altura", ListaCampos.DB_SI, true );
		adicCampo( txtLargItFichaAval, 477, 65, 80, 20, "LargItFichaAval", "Largura", ListaCampos.DB_SI, true );
		adicCampo( txtDnmItFichaAval, 560, 65, 80, 20, "DnmItFichaAval", "Dinamometro", ListaCampos.DB_SI, true );

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

		/*
			navRod.add( pinImp, BorderLayout.CENTER );
			pinImp.setPreferredSize( new Dimension ( 260, 30 ) );
			pinImp.add( btPrevimp );
			pinImp.add( btImp );
		*/

		
	}
	
	public void abreOrcamento(){
		
	}
	
	private void carregaOrcamentos() {
/*
		tabOrcamento.limpa();
		ResultSet rs = null;
		try {

			rs = daoficha.carregaOrcamentos( Aplicativo.iCodEmp, ListaCampos.getMasterFilial( "CRFICHAORC" ), txtSeqFichaAval.getVlrInteger() );

			for ( int row = 0; rs.next(); row++ ) {

				tabOrcamento.adicLinha();
				tabOrcamento.setValor( rs.getInt( "codorc" ), row, DAOFicha.FichaOrc.CODORC.ordinal() );
				tabOrcamento.setValor( rs.getInt( "codcli" ), row, DAOFicha.FichaOrc.CODCLI.ordinal() );
				tabOrcamento.setValor( rs.getString( "razcli" ), row, DAOFicha.FichaOrc.RAZCLI.ordinal() );
				tabOrcamento.setValor( Funcoes.sqlDateToDate( rs.getDate( "dtemitvenda" ) ), row, DAOFicha.FichaOrc.DTEMISSAO.ordinal() );
				tabOrcamento.setValor( Funcoes.sqlDateToDate( rs.getDate( "dtsaidavenda" ) ), row, DAOFicha.FichaOrc.DTVENC.ordinal() );
				tabOrcamento.setValor( rs.getInt( "codplanopag" ), row, DAOFicha.FichaOrc.CODPAG.ordinal() );
				tabOrcamento.setValor( rs.getString( "descplanopag" ), row, DAOFicha.FichaOrc.DESCPAG.ordinal() );
				tabOrcamento.setValor( rs.getInt( "coditorc" ), row, DAOFicha.FichaOrc.CODITORC.ordinal() );
				tabOrcamento.setValor( rs.getBigDecimal( "qtditorc" ), row, DAOFicha.FichaOrc.QTDITORC.ordinal() );
				tabOrcamento.setValor( rs.getBigDecimal( "precoitorc" ), row, DAOFicha.FichaOrc.PRECOITORC.ordinal() );
				tabOrcamento.setValor( rs.getString( "tipoorc" ), row, DAOFicha.FichaOrc.TIPOORC.ordinal() );
			}

			rs.close();
			
			con.commit();
		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro ao consultar orcamento!\n" + err.getMessage(), true, con, err );
			err.printStackTrace();
		}
		*/
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
		hParam.put( "SUBREPORT_DIR", "org/freedom/relatorios/ficha_avaliativa_091_sub.jasper");
		try {
			hParam.put( "LOGOEMP", new ImageIcon(fotoemp.getBytes(1, ( int ) fotoemp.length())).getImage() );
		} catch ( SQLException e ) {
			e.printStackTrace();
		}
		
		dlGr = new FPrinterJob( daoficha.getPrefs()[FichaOrc.PREFS.LAYOUTFICHAAVAL.ordinal()].toString(), "Ficha avaliativa", "", rs, hParam, this );
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
		boolean bPrim = false;
		
		try {
			for(int row = 0; row < tab.getNumLinhas(); row++){
				if(bPrim){
					daoficha.populaOrc( Aplicativo.iCodEmp, ListaCampos.getMasterFilial( "VDORCAMENTO" ), txtCodCont.getVlrInteger(), new Date(), new Date(), Integer.valueOf( daoficha.getPrefs()[FichaOrc.PREFS.CODPLANOPAG.ordinal()].toString()) );
				}
				
				
				
				
				
				
				bPrim = false;
				
			}
			
		
		} catch ( NumberFormatException e ) {
			e.printStackTrace();
		} catch ( SQLException e ) {
			e.printStackTrace();
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

		// TODO Auto-generated method stub
		
	}



	public void afterCarrega( CarregaEvent cevt ) {
		 if ( cevt.getListaCampos() == lcCampos ) {
			 carregaOrcamentos();
		 }		
	}
}
