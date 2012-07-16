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

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JScrollPane;

import org.freedom.acao.InsertEvent;
import org.freedom.acao.InsertListener;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JCheckBoxPad;
import org.freedom.library.swing.component.JPanelPad;
import org.freedom.library.swing.component.JRadioGroup;
import org.freedom.library.swing.component.JTabbedPanePad;
import org.freedom.library.swing.component.JTextAreaPad;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FDetalhe;
import org.freedom.library.swing.frame.FPrinterJob;
import org.freedom.modulos.crm.dao.DAOContato;
import org.freedom.modulos.crm.dao.DAOContato.CONT_PREFS;
import org.freedom.modulos.crm.view.frame.crud.plain.FAmbienteAval;
import org.freedom.modulos.crm.view.frame.crud.plain.FMotivoAval;
import org.freedom.modulos.crm.view.frame.crud.tabbed.FContato;
import org.freedom.modulos.gms.view.frame.crud.tabbed.FProduto;

public class FFichaAval extends FDetalhe implements InsertListener {

	private static final long serialVersionUID = 1L;
	
	
	private JTabbedPanePad tpnCab = new JTabbedPanePad();
	
	private JPanelPad pinFichaAval = new JPanelPad();

	private JPanelPad pinCabInfCompl = new JPanelPad();
	
	private JPanelPad pinObs = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 1, 1 ) );
	
	private JPanelPad pinCab = new JPanelPad();

	private JPanelPad pinDet = new JPanelPad();
	
	//FICHAAVAL
	
	private JTextFieldPad txtDtFichaAval = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );
	
	private JTextFieldPad txtSeqFichaAval = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );

	private JTextFieldPad txtCodCont = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );

	private JTextFieldFK txtRazCont = new JTextFieldFK( JTextFieldFK.TP_STRING, 50, 0 );
	
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
	
	/*
    COBERTFICHAAVAL CHAR(1) DEFAULT 'N' NOT NULL, -- INDICA SE É COBERTURA
    ESTRUTFICHAAVAL CHAR(1) DEFAULT 'N' NOT NULL, -- HÁ NECESSIDADE DE ESTRUTURA S/N/E (SIM,NÃO,NÃO SABE)
    OCUPADOFICHAAVAL CHAR(1) DEFAULT 'N' NOT NULL, -- IMÓVEL OCUPADO (S/N)
    MOBILFICHAAVAL CHAR(1) DEFAULT 'M' NOT NULL, -- IMÓVEL MOBILIADO M-MOBILIADO, S-SEMI-MOBILIADO, V-VAZIO
    JANELAFICHAAVAL CHAR(1) DEFAULT 'N' NOT NULL, -- JANELAS (S/N)
    SACADAFICHAAVAL CHAR(1) DEFAULT 'N' NOT NULL, -- SACADAS (S/N)
    OUTROSFICHAAVAL CHAR(1) DEFAULT 'N' NOT NULL, -- OUTROS (S/N)
    */
	
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
	
	private DAOContato daocontato = null;

	public FFichaAval() {

		nav.setNavigation( true );

		setTitulo( "Ficha Avaliativa" );
	
		setAtribos( 50, 50, 715, 600 );
		montaListaCampos();
		montaTela();

	}
	
	public void montaListaCampos(){
		
		// FK Contato
		txtCodCont.setTabelaExterna( lcContato, FContato.class.getCanonicalName());
		txtCodCont.setFK( true );
		txtCodCont.setNomeCampo( "CodContr" );
		lcContato.add( new GuardaCampo( txtCodCont, "CodCto", "Cód.Contato", ListaCampos.DB_PK, false ) );
		lcContato.add( new GuardaCampo( txtRazCont, "RazCto", "Razão do contato.", ListaCampos.DB_SI, false ) );
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
		
		lcCampos.addInsertListener( this );
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
			impFichaAval( txtCodCont.getVlrInteger() );
	
		}		else if ( evt.getSource() == btImp ) {
			//imprimir( false );
		}
		super.actionPerformed( evt );
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		lcContato.setConexao( cn );
		lcMotAval.setConexao( cn );
		lcProduto.setConexao( cn );
		lcAmbAval.setConexao( cn );

	}

	public void impFichaAval(final int codcont){
		
		Blob fotoemp = FPrinterJob.getLogo( con );
		
		StringBuilder sql = daocontato.getSqlFichaAval();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = con.prepareStatement( sql.toString() );
			int param = 1;
			ps.setInt( param++, Aplicativo.iCodEmp );
			ps.setInt( param++, ListaCampos.getMasterFilial( "TKCONTATO" ) );
			ps.setInt( param++, codcont );
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
		
		dlGr = new FPrinterJob( daocontato.getPrefs()[CONT_PREFS.LAYOUTFICHAAVAL.ordinal()].toString(), "Ficha avaliativa", "", rs, hParam, this );
		dlGr.setVisible( true );
		
	}

	public void beforeInsert( InsertEvent ievt ) {

	}

	public void afterInsert( InsertEvent ievt ) {
		if(ievt.getListaCampos() == lcCampos) {
			txtAndarFichaAval.setVlrInteger( 0 );
		}
		
	}
}
