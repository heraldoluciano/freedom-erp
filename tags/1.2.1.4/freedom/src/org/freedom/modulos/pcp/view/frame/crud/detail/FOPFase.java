/**
 * @version 06/05/2004 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.pcp <BR>
 * Classe: @(#)FOPFase.java <BR>
 * 
 * Este arquivo é parte do sistema Freedom-ERP, o Freedom-ERP é um software livre; você pode redistribui-lo e/ou <BR>
 * modifica-lo dentro dos termos da Licença Pública Geral GNU como publicada pela Fundação do Software Livre (FSF); <BR>
 * na versão 2 da Licença, ou (na sua opnião) qualquer versão. <BR>
 * Este programa é distribuido na esperança que possa ser  util, mas SEM NENHUMA GARANTIA; <BR>
 * sem uma garantia implicita de ADEQUAÇÂO a qualquer MERCADO ou APLICAÇÃO EM PARTICULAR. <BR>
 * Veja a Licença Pública Geral GNU para maiores detalhes. <BR>
 * Você deve ter recebido uma cópia da Licença Pública Geral GNU junto com este programa, se não, <BR>
 * escreva para a Fundação do Software Livre(FSF) Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA <BR> <BR>
 *
 * Comentários sobre a classe...
 * 
 */

package org.freedom.modulos.pcp.view.frame.crud.detail;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;

import org.freedom.acao.CancelEvent;
import org.freedom.acao.CancelListener;
import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.acao.InsertEvent;
import org.freedom.acao.InsertListener;
import org.freedom.acao.PostEvent;
import org.freedom.acao.PostListener;
import org.freedom.bmps.Icone;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JButtonPad;
import org.freedom.library.swing.component.JPanelPad;
import org.freedom.library.swing.component.JTextAreaPad;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FDetalhe;
import org.freedom.modulos.gms.view.frame.crud.tabbed.FProduto;
import org.freedom.modulos.pcp.view.dialog.utility.DLFinalizaOP;
import org.freedom.modulos.pcp.view.frame.crud.plain.FFase;
import org.freedom.modulos.pcp.view.frame.crud.plain.FRecursos;

public class FOPFase extends FDetalhe implements PostListener,CancelListener,InsertListener,ActionListener,CarregaListener {

	private static final long serialVersionUID = 1L;
	private JPanelPad pinCab = new JPanelPad();
	private JPanelPad pinDet = new JPanelPad(new GridLayout( 2, 1 ));
	private JPanelPad pinDetFasesInstrucao = new JPanelPad( new GridLayout( 1, 1 ) );
	private JPanelPad pinDetFasesCampos = new JPanelPad();
	private JTextAreaPad txaObs = new JTextAreaPad();
	private JScrollPane spnFase = new JScrollPane( txaObs );
	private JTextFieldPad txtCodOP = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
	private JTextFieldPad txtSeqOP = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
	private JTextFieldFK txtCodProd = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
	private JTextFieldFK txtDescProd = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
	private JTextFieldPad txtDtEmit = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
	private JTextFieldPad txtDtValid = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
	private JTextFieldPad txtDtFabProd = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
	private JTextFieldPad txtQtdPrevOP = new JTextFieldPad(JTextFieldPad.TP_DECIMAL,15,3);
	private JTextFieldPad txtQtdFinalOP = new JTextFieldPad(JTextFieldPad.TP_DECIMAL,15,3);
	private JTextFieldPad txtCodFase = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
	private JTextFieldFK txtDescFase = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
	private JTextFieldPad txtJustificqtdprod = new JTextFieldPad(JTextFieldPad.TP_STRING,500,0);
	private JTextFieldPad txtNumSeqOf = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
	private JTextFieldPad txtCodRec = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
	private JTextFieldPad txtCodTpRec = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
	private JTextFieldFK txtDescRec = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
	private JTextFieldPad txtTempoOf = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
	private JTextFieldPad txtDataIniProdFs = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0); 
	private JTextFieldPad txtHIniProdFs = new JTextFieldPad(JTextFieldPad.TP_TIME,8,0);
	private JTextFieldPad txtDataFimProdFs = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0); 
	private JTextFieldPad txtHFimProdFs = new JTextFieldPad(JTextFieldPad.TP_TIME,8,0);
	private JTextFieldPad txtSitFS = new JTextFieldPad(JTextFieldPad.TP_STRING,2,0);
	private JTextFieldFK txtTpFase = new JTextFieldFK(JTextFieldPad.TP_STRING,2,0);
	private JButtonPad btContraProva = new JButtonPad("Retenção de contra prova", Icone.novo( "btFinalizaOP.gif" ) );
	private ListaCampos lcProd = new ListaCampos(this,"PD");
	private ListaCampos lcFase = new ListaCampos(this,"FS");
	private ListaCampos lcRec = new ListaCampos(this,"RP");
	private int iCodOP;
	private int iSeqOP;
	private int iSeqEst;
	private FOP telaant = null;
	
	public FOPFase(int iCodOP,int iSeqOP,int iSeqEst, FOP telaOP) { //,boolean bExecuta
		
		setTitulo("Fases da OP");
		setName( "Fases da OP" );
		
	    setAtribos( 70, 40, 675, 470);
	    setAltCab(130);
	    setAltDet(180);
		
		this.iCodOP = iCodOP;
		this.iSeqOP = iSeqOP;
		this.iSeqEst = iSeqEst;
		this.telaant = telaOP;
		txtCodOP.setAtivo(false);
		txtCodProd.setAtivo(false);
		txtDtEmit.setAtivo(false);
		txtDtValid.setAtivo(false);
		txtQtdPrevOP.setAtivo(false);
		txtQtdFinalOP.setAtivo(false);
		txtTempoOf.setAtivo(false);
		txtSeqOP.setAtivo(false);
		
//		if(bExecuta) {
			txtCodFase.setAtivo(false);
			txtNumSeqOf.setAtivo(false);
			txtCodRec.setAtivo(false);
			txtTempoOf.setAtivo(false);
//		}
		
		pinCab = new JPanelPad(500,90);
		setListaCampos(lcCampos);
		setPainel( pinCab, pnCliCab);
		
		lcProd.setUsaME(false);
		lcProd.add(new GuardaCampo( txtCodProd, "CodProd", "Cód.prod.", ListaCampos.DB_PK, true));
		lcProd.add(new GuardaCampo( txtDescProd, "DescProd", "Descrição do produto", ListaCampos.DB_SI, false));
		lcProd.setWhereAdic("TIPOPROD='F'");
		lcProd.montaSql(false, "PRODUTO", "EQ");
		lcProd.setQueryCommit(false);
		lcProd.setReadOnly(true);
		txtCodProd.setTabelaExterna(lcProd, FProduto.class.getCanonicalName());
		txtDescProd.setListaCampos(lcProd);
		
		adicCampo(txtCodOP, 7, 20, 80, 20,"CodOP","Nº.OP", ListaCampos.DB_PK, true);
		adicCampo(txtSeqOP, 90, 20, 60, 20,"SeqOP","Seq.OP", ListaCampos.DB_PK, true);
		adicCampo(txtCodProd, 153, 20, 77, 20,"CodProd","Cód.prod.", ListaCampos.DB_FK, txtDescProd, true);
		adicDescFK(txtDescProd, 233, 20, 410, 20, "DescProd", "Descrição do produto");
		adicCampo(txtDtEmit, 7, 60, 110, 20,"DtEmitOP","Emissão", ListaCampos.DB_SI, true);
		adicCampo(txtDtValid, 120, 60, 110, 20,"DtValidPDOP","Valid.prod.", ListaCampos.DB_SI, true);
		adicCampo(txtQtdPrevOP, 233, 60, 87, 20,"QtdPrevProdOP","Qtd.prevista", ListaCampos.DB_SI, true);
		adicCampo(txtQtdFinalOP, 323, 60, 100, 20,"QtdFinalProdOP","Qtd.produzida", ListaCampos.DB_SI, true);
		adicCampoInvisivel(txtJustificqtdprod,"JUSTFICQTDPROD","Justificativa",ListaCampos.DB_SI,false);
		adicCampoInvisivel(txtDtFabProd,"dtfabrop","Dt.Fabric.",ListaCampos.DB_SI, true);
		adic(btContraProva, 435,55,210,30);
		
		setListaCampos( false, "OP", "PP");
		lcCampos.setQueryInsert(false);
		    
		lcFase.add(new GuardaCampo( txtCodFase,"CodFase", "Cód.fase", ListaCampos.DB_PK, true));
		lcFase.add(new GuardaCampo( txtDescFase, "DescFase", "Descrição da fase", ListaCampos.DB_SI, false));
		lcFase.add(new GuardaCampo( txtTpFase, "TipoFase", "Tipo", ListaCampos.DB_SI, false ));
		lcFase.montaSql(false, "FASE", "PP");
		lcFase.setQueryCommit(false);
		lcFase.setReadOnly(true);
		txtCodFase.setTabelaExterna(lcFase, FFase.class.getCanonicalName());
		txtDescFase.setListaCampos(lcFase);
		
		lcRec.add(new GuardaCampo(txtCodRec,"CodRecP", "Cód.rec.", ListaCampos.DB_PK, true));
		lcRec.add(new GuardaCampo(txtDescRec, "DescRecP", "Descrição do recurso de produção", ListaCampos.DB_SI, false));
		//lcRec.add(new GuardaCampo(txtCodTpRec,"CodTpRec","Cód.Tp.Rec.",ListaCampos.DB_FK,false));
		lcRec.setDinWhereAdic("	CODTPREC=#N ",txtCodTpRec);
		lcRec.montaSql(false, "RECURSO", "PP");
		lcRec.setQueryCommit(false);
		lcRec.setReadOnly(true);
		txtCodRec.setTabelaExterna(lcRec, FRecursos.class.getCanonicalName());
		txtDescRec.setListaCampos(lcRec);
		
		setPainel( pinDet, pnDet);
		pinDet.add( pinDetFasesCampos );
		setPainel( pinDetFasesCampos );
		setListaCampos(lcDet);
		setNavegador(navRod);
		
		
		adicCampo(txtNumSeqOf, 5, 20, 40, 20,"SeqOf","Item",ListaCampos.DB_PK,true);
		adicCampo(txtCodFase, 48, 20, 77, 20,"CodFase","Cd.fase", ListaCampos.DB_FK, txtDescFase, true);
		adicDescFK(txtDescFase, 128, 20, 227, 20,"DescFase", "Descrição da fase");
		adicDescFKInvisivel( txtTpFase, "DescFase", "Descrição da fase");
		adicCampo(txtTempoOf, 358, 20, 100, 20,"TempoOf","Tempo",ListaCampos.DB_SI, false);
		adicCampoInvisivel(txtCodTpRec,"CodTpRec", "Cód.tp.rec.", ListaCampos.DB_SI, false);
		adicCampo(txtCodRec, 7, 60, 60, 20,"CodRecP","Cód.rec.", ListaCampos.DB_FK, txtDescRec, false);
		adicDescFK(txtDescRec, 70, 60, 390, 20, "DescRecP", "Descrição do recurso");
		
		pinDet.add( pinDetFasesInstrucao );
		setPainel( pinDetFasesInstrucao );
		GridLayout gi = (GridLayout)pinDetFasesInstrucao.getLayout();
		gi.setHgap( 10 );
		gi.setVgap( 10 );

		pinDetFasesInstrucao.setBorder( BorderFactory.createTitledBorder( 
				BorderFactory.createEtchedBorder(), "Instruções" ) );
		adicDBLiv( txaObs, "ObsFS", "Observações", false );
		pinDetFasesInstrucao.add( spnFase );

//		if (bExecuta){
			setPainel( pinDetFasesCampos );
			adicCampo(txtDataIniProdFs, 470, 20, 80, 20,"DataIniProdFs","Dt.inicial", ListaCampos.DB_SI, false);
			adicCampo(txtHIniProdFs, 553, 20, 80, 20,"HIniProdFs","Hr.inicial", ListaCampos.DB_SI, false);
			adicCampo(txtDataFimProdFs, 470, 60, 80, 20,"DataFimProdFs","Dt.final", ListaCampos.DB_SI, false);
			adicCampo(txtHFimProdFs, 553, 60, 80, 20,"HFimProdFs","Hr.final", ListaCampos.DB_SI, false);
//		}
		
		adicCampoInvisivel(txtSitFS,"SITFS", "Sit.", ListaCampos.DB_SI, false);
		setListaCampos( true, "OPFASE", "PP");
		lcDet.setQueryInsert(true);
		montaTab();
		    
		lcCampos.setReadOnly(true);
		
		tab.setTamColuna(30,0); // Item
		tab.setTamColuna(50,1); // CodFase
		tab.setTamColuna(230,2); // Desc.Fase		
		tab.setTamColuna(55,3); // Tempo
		tab.setTamColuna(70,8); // Dt. Inicial
		tab.setTamColuna(55,9); // Hr.Inicial
		tab.setTamColuna(70,10); //Dt. Final
		tab.setTamColuna(55,11); //Hr. Final
		tab.setTamColuna(25,12); //Situação
		
		tab.setColunaInvisivel( 4 );
		tab.setColunaInvisivel( 5 );
		tab.setColunaInvisivel( 6 );
		tab.setColunaInvisivel( 7 );
				    
		lcCampos.addCarregaListener(this);
		lcDet.addCarregaListener(this);
		lcFase.addCarregaListener(this);
		lcProd.addCarregaListener(this);
		lcRec.addCarregaListener(this);
		lcDet.addPostListener(this);
		btContraProva.addActionListener( this );
	     
	}	  

	public void atualizaOP(){
		PreparedStatement ps = null;
		StringBuffer sql = new StringBuffer();
		
		try {
			  		
			sql.append( "UPDATE PPOP SET QTDFINALPRODOP=?, JUSTFICQTDPROD=? " );
			sql.append( "WHERE CODOP=? AND SEQOP=? AND CODEMP=? AND CODFILIAL=? " );
			
			ps = con.prepareStatement(sql.toString());
			ps.setBigDecimal( 1, txtQtdFinalOP.getVlrBigDecimal() );
			ps.setString(2,txtJustificqtdprod.getVlrString());
			ps.setInt(3,txtCodOP.getVlrInteger().intValue());
			ps.setInt(4,txtSeqOP.getVlrInteger().intValue());
			ps.setInt(5,Aplicativo.iCodEmp);
			ps.setInt(6,ListaCampos.getMasterFilial("PPOP"));
			ps.executeUpdate();
			    
			con.commit();
		  	
		} 
		catch(Exception err) {
			err.printStackTrace();
			Funcoes.mensagemErro(this, "Erro ao atualizar quantidade produzida na OP.!\n",true,con,err);  		
		} 
		finally {
			ps = null;
			sql = null;
		}
	}
	
	private boolean temCQ(){
		
		boolean ret = true;
		String sTipo = txtTpFase.getVlrString();
		StringBuffer sSQL = new StringBuffer();
		PreparedStatement ps = null;
		
		if( sTipo.equals( "CQ" )){
			
			sSQL.append( "SELECT COUNT(*) FROM PPOPCQ WHERE CODEMP=? AND CODFILIAL=? AND STATUS='PE' AND CODOP=? AND SEQOP=?"  );
			
			try {
				
				ps = con.prepareStatement( sSQL.toString() );
				ps.setInt( 1, Aplicativo.iCodEmp );
				ps.setInt( 2, ListaCampos.getMasterFilial( "PPOPCQ" ) );
				ps.setInt( 3, txtCodOP.getVlrInteger() );
				ps.setInt( 4, txtSeqOP.getVlrInteger() );
				
				ResultSet rs = ps.executeQuery();
				
				if( rs.next() && rs.getInt( 1 ) == 0 ){
					ret = true;
				}
				else{
					ret = false;
				}
					
			} catch ( SQLException e ) {
				
				e.printStackTrace();
			}
		}
		else{
			return true;
		}
		return ret;
	}
	
	public boolean getFinalizaProcesso(){
	
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sSQL = null;
		String sFinaliza = null;
		boolean bRet = false;
		
			
		try {

			sSQL = "SELECT FINALIZAOP FROM PPESTRUFASE WHERE CODEMP=? AND CODFILIAL=? AND CODPROD=? AND SEQEST=? AND CODFASE=? AND SEQEF=?";

			ps = con.prepareStatement( sSQL );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "PPOP" ) );
			ps.setInt( 3, txtCodProd.getVlrInteger().intValue() );
			ps.setInt( 4, iSeqEst );
			ps.setInt( 5, txtCodFase.getVlrInteger().intValue() );
			ps.setInt( 6, txtNumSeqOf.getVlrInteger().intValue() );
			
			rs = ps.executeQuery();
			if ( rs.next() ) {
				sFinaliza = rs.getString( 1 );
				if ( sFinaliza != null )
					if ( sFinaliza.equals( "S" ) )
						bRet = true;
			}

			rs.close();
			ps.close();
			con.commit();

		} catch ( SQLException err ) {
			err.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao verificar se fase finaliza processo!\n", true, con, err );
		} finally {
			ps = null;
			rs = null;
			sSQL = null;
			sFinaliza = null;
		}
		return bRet;
	}

	public void beforeCarrega(CarregaEvent cevt) { }
	  
	public void afterCarrega(CarregaEvent cevt) {
		if (cevt.getListaCampos() == lcDet) {
			if (!(txtDataIniProdFs.getVlrString().length() > 0) || (txtDataIniProdFs.getVlrString() == null)) {
				txtDataIniProdFs.setVlrDate(txtDtFabProd.getVlrDate());
				txtHIniProdFs.setVlrString(Funcoes.getTimeString(new Date()));
				txtDataFimProdFs.setVlrDate(new Date());
				txtHFimProdFs.setVlrString(Funcoes.getTimeString(new Date()));
			} 
				
			if (txtSitFS.getVlrString().equals("FN")){
				txtDataIniProdFs.setAtivo(false);
				txtHIniProdFs.setAtivo(false);
				txtDataFimProdFs.setAtivo(false);
				txtHFimProdFs.setAtivo(false);
				txaObs.setEnabled(false);    	
			} 
			else {
				txtDataIniProdFs.setAtivo(true);
				txtHIniProdFs.setAtivo(true);
				txtDataFimProdFs.setAtivo(true);
				txtHFimProdFs.setAtivo(true);
				txaObs.setEnabled(true);    		  		
			}
		}
		btContraProva.setEnabled( !temCQ() );
	}
	
	public void afterInsert(InsertEvent ievt) { }

	public void beforeInsert(InsertEvent ievt) { }

	public void beforePost(PostEvent pevt) {
		if(pevt.getListaCampos()==lcDet) {
			
			if( !temCQ() ){
				Funcoes.mensagemErro( this, "Não foi possível finalizar a fase.\nExistem análises pendentes!" );
				pevt.cancela();
				return;
			}
			
			if(getFinalizaProcesso() && (txtSitFS.getVlrString().equals("PE"))){
				DLFinalizaOP dl = new DLFinalizaOP(this,txtQtdPrevOP.getVlrBigDecimal());
				dl.setVisible(true);
				if (!dl.OK)
					pevt.cancela(); 
				else {
					txtQtdFinalOP.setVlrBigDecimal( dl.getValor() );
					txtJustificqtdprod.setVlrString(dl.getObs());
					atualizaOP();
				}
				dl.dispose();
			}
		}
	}

	public void actionPerformed( ActionEvent evt ) {

		super.actionPerformed( evt );
		
		if( evt.getSource() == btContraProva ){
			
			if ( Aplicativo.telaPrincipal.temTela( "Contra-Prova" ) == false ) {
				
				FContraProva f = new FContraProva( txtCodOP.getVlrInteger(), txtSeqOP.getVlrInteger() );
				Aplicativo.telaPrincipal.criatela( "Contra-Prova", f, con );
			}
			
			
		}
	}
	
	public void afterPost(PostEvent pevt) {
		if (pevt.getListaCampos()==lcDet) {
			lcCampos.carregaDados();
		}
		
	}
	    
	public void beforeCancel(CancelEvent cevt) { }

	public void afterCancel(CancelEvent cevt) { }
	
    public void dispose() {
    	telaant.recarrega();
        super.dispose();
    }
  
	public void setConexao(DbConnection cn) {		
		super.setConexao(cn);
		lcProd.setConexao(cn);
		lcFase.setConexao(cn);
		lcRec.setConexao(cn);
		txtCodOP.setVlrInteger(new Integer(iCodOP));
		txtSeqOP.setVlrInteger(new Integer(iSeqOP));
		lcCampos.carregaDados();	
		
		}        
}
