/**
 * @version 26/09/2011 <BR>
 * @author Setpoint Informática Ltda./Bruno Nascimento <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.crm <BR>
 *         Classe: @(#)FTarefa.java <BR>
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
 *         Tela de cadastro de Tarefas/subtarefas.
 * 
 */

package org.freedom.modulos.crm.view.frame.crud.plain;

import java.util.Vector;

import javax.swing.JScrollPane;
import org.freedom.acao.InsertEvent;
import org.freedom.acao.InsertListener;
import org.freedom.acao.RadioGroupEvent;
import org.freedom.acao.RadioGroupListener;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JRadioGroup;
import org.freedom.library.swing.component.JTextAreaPad;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.FDados;
import org.freedom.modulos.crm.view.frame.crud.detail.FContrato;

public class FTarefa extends FDados implements RadioGroupListener, InsertListener {

	private static final long serialVersionUID = 1L;
	
	private JTextFieldPad txtCodTarefa= new JTextFieldPad( JTextFieldPad.TP_INTEGER, 6, 0 );
	
	private JTextFieldPad txtDescTarefa = new JTextFieldPad( JTextFieldPad.TP_STRING, 100, 0 );
	
	private JTextFieldPad txtCodTarefaPrinc= new JTextFieldPad( JTextFieldPad.TP_INTEGER, 6, 0 );
	
	private JTextFieldFK txtDescTarefaPrinc = new JTextFieldFK( JTextFieldPad.TP_STRING, 100, 0 );
	
	private JTextFieldPad txtTempoEstTarefa = new JTextFieldPad( JTextFieldPad.TP_TIME, 5, 0 );
	
	private JTextFieldFK txtTempoDecTarefa = new JTextFieldFK( JTextFieldPad.TP_INTEGER, 15, 5 );
	
	private JTextFieldPad txtIndexTarefa = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );
		
	//FK
	
	private JTextFieldPad txtCodContr = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 6, 0 );
	
	private JTextFieldFK txtDescContr = new JTextFieldFK( JTextFieldPad.TP_STRING, 100, 0 );
	
	private JTextFieldPad txtCodChamado = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 6, 0 );
	
	private JTextFieldFK txtDescChamado = new JTextFieldFK( JTextFieldPad.TP_STRING, 100, 0 );
	
	private JTextFieldPad txtCodItContr = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 6, 0 );
	
	private JTextFieldFK txtDescItContr = new JTextFieldFK( JTextFieldPad.TP_STRING, 100, 0 );
	
	private JTextFieldPad txtCodMarcor= new JTextFieldPad( JTextFieldPad.TP_INTEGER, 6, 0 );

	private JTextFieldFK txtDescMarcor = new JTextFieldFK( JTextFieldPad.TP_STRING, 100, 0 );
		
	//Lista Campos.
	
	private ListaCampos lcMarc = new ListaCampos( this, "MO" );
	
	private ListaCampos lcChamado = new ListaCampos( this, "CH" );
	
	private ListaCampos lcContrato = new ListaCampos( this, "CT" );
	
	private ListaCampos lcItContrato = new ListaCampos( this, "CT" );
	
	private ListaCampos lcSuperTarefa = new ListaCampos( this, "TA" );
	
	private ListaCampos lcTarefa = new ListaCampos( this );
	                                                      
	private JTextAreaPad txaDescDetTarefa = new JTextAreaPad( 2000 );
	
	private JScrollPane scrol = new JScrollPane( txaDescDetTarefa );
	
	private JTextAreaPad txaNotasTarefa = new JTextAreaPad( 2000 );
	
	private JScrollPane scrol2 = new JScrollPane( txaNotasTarefa );
	
	private JRadioGroup<?, ?> rgTipoTarefa = null;
	
	
	
	public FTarefa( ) {

		super();
		montaListaCampos();
		montaTela();

	}
	

	private void montaListaCampos() {	
		

		
		/**********************
		 * Marcador * *
		 *******************/
		txtCodMarcor.setTabelaExterna( lcMarc, null );
		txtCodMarcor.setFK( true );
		txtCodMarcor.setNomeCampo( "CodChamado" );
		lcMarc.add( new GuardaCampo( txtCodMarcor, "CodMarcor", "Cód.Marcador", ListaCampos.DB_PK, false ) );
		lcMarc.add( new GuardaCampo( txtDescMarcor, "DescMarcor", "Descrição do Marcador", ListaCampos.DB_SI, false ) );
		lcMarc.montaSql( false, "MARCADOR", "CR" );
		lcMarc.setReadOnly( true );
		
		/**********************
		 * Chamado   * *
		 *******************/
		txtCodChamado.setTabelaExterna( lcChamado, null );
		txtCodChamado.setFK( true );
		txtCodChamado.setNomeCampo( "CodChamado" );
		lcChamado.add( new GuardaCampo( txtCodChamado, "CodChamado", "Cód.Chamado", ListaCampos.DB_PK, false ) );
		lcChamado.add( new GuardaCampo( txtDescChamado, "DescChamado", "Descrição do chamado", ListaCampos.DB_SI, false ) );
		lcChamado.montaSql( false, "CHAMADO", "CR" );
		lcChamado.setReadOnly( true );
		
		/**********************
		 * Contrato   * *
		 *******************/
		lcContrato.add( new GuardaCampo( txtCodContr, "CodContr", "Cód.Contrato", ListaCampos.DB_PK, true ) );
		lcContrato.add( new GuardaCampo( txtDescContr, "DescContr", "Desc.Contr.", ListaCampos.DB_SI, false ) );
		lcContrato.montaSql( false, "CONTRATO", "VD" );
		lcContrato.setQueryCommit( false );
		lcContrato.setReadOnly( true );
		txtCodContr.setTabelaExterna( lcContrato, FContrato.class.getCanonicalName() );
		
		/**********************
		 * Item  Contrato   * *
		 *******************/
		
		lcItContrato.add( new GuardaCampo( txtCodItContr, "CodItContr", "Cód.It.Contr.", ListaCampos.DB_PK, true ) );
		lcItContrato.add( new GuardaCampo( txtCodContr, "CodContr", "Cód.Contrato", ListaCampos.DB_PK, true ) );
		lcItContrato.add( new GuardaCampo( txtDescItContr, "DescItContr", "Desc.It.Contr.", ListaCampos.DB_SI, false ) );
		lcItContrato.setDinWhereAdic( "CodContr=#N", txtCodContr );
		txtCodContr.setPK( true );
		lcItContrato.montaSql( false, "ITCONTRATO", "VD" );
		lcItContrato.setQueryCommit( false );
		lcItContrato.setReadOnly( true );
		txtCodItContr.setTabelaExterna( lcItContrato, FContrato.class.getCanonicalName() );		
			
		/**********************
		 * Tarefa   * *
		 *******************/
		lcTarefa.setQueryCommit( false );
		lcTarefa.setReadOnly( true );
		lcTarefa.add( new GuardaCampo( txtCodTarefa, "CodTarefa", "Cód.Contr.", ListaCampos.DB_PK, txtDescTarefaPrinc, false ) );
		lcTarefa.add( new GuardaCampo( txtDescTarefa, "DescTarefa" , "Descrição do contrato", ListaCampos.DB_SI, false ) );
		lcTarefa.add( new GuardaCampo( txtIndexTarefa, "IndexTarefa" , "Índice", ListaCampos.DB_SI, false ) );
	
		lcTarefa.montaSql( false, "TAREFA", "CR" );
		txtCodTarefa.setTabelaExterna( lcTarefa, FTarefa.class.getCanonicalName() );
		txtCodTarefa.setFK( true );
		
		/**********************
		 * Tarefa PAI   * *
		 *******************/
		lcSuperTarefa.setQueryCommit( false );
		lcSuperTarefa.setReadOnly( true );
		lcSuperTarefa.add( new GuardaCampo( txtCodTarefaPrinc, "CodTarefa", "Cód.Contr.", ListaCampos.DB_PK, txtDescTarefaPrinc, false ) );
		lcSuperTarefa.add( new GuardaCampo( txtDescTarefaPrinc, "DescTarefa" , "Descrição do contrato", ListaCampos.DB_SI, false ) );
		lcSuperTarefa.montaSql( false, "TAREFA", "CR" );
		txtCodTarefaPrinc.setTabelaExterna( lcSuperTarefa, FTarefa.class.getCanonicalName() );
		txtCodTarefaPrinc.setFK( true );

	}
	private void montaTela(){
		
		//nav.setNavigation( true );
		
		setTitulo( "Modelos de Atendimento" );
		setAtribos( 10, 50, 690, 560 );
		
		
		Vector<String> vValsTipo = new Vector<String>();
		Vector<String> vLabsTipo = new Vector<String>();
		vValsTipo.addElement( "T" );
		vValsTipo.addElement( "S" );
		vLabsTipo.addElement( "Tarefa" );
		vLabsTipo.addElement( "Sub-tarefa" );
		rgTipoTarefa = new JRadioGroup<String, String>( 1, 2, vLabsTipo, vValsTipo );
		rgTipoTarefa.setVlrString( "T" );
		rgTipoTarefa.addRadioGroupListener( this );


		txtTempoDecTarefa.setSoLeitura( true );
		
		adicCampo( txtCodTarefa, 7, 20, 80, 20, "CodTarefa", "Cód.tarefa.", ListaCampos.DB_PK, true );
		adicCampo( txtDescTarefa, 90, 20, 510, 20, "DescTarefa", "Descrição da Tarefa", ListaCampos.DB_SI, true );
		adicDB( rgTipoTarefa	, 7, 63, 200, 30, "TipoTarefa", "Tipo de Tarefa", true );
		
		
		adicCampo( txtCodTarefaPrinc , 7, 120, 80, 20, "CodTarefata", "Cód.tarefa.Princ", ListaCampos.DB_FK, txtDescTarefaPrinc, false );
		adicDescFK( txtDescTarefaPrinc, 90, 120, 510, 20, "DescTarefa", "Descrição do projeto principal" );
		txtCodTarefaPrinc.setNomeCampo( "CodTarefa" ); 
		txtCodTarefaPrinc.setEnabled( false );
		txtDescTarefaPrinc.setEnabled( false );
		
		adicCampo( txtCodContr, 7, 160, 80, 20, "CodContr", "Cód.Contrato", ListaCampos.DB_FK, true );
		adicDescFK( txtDescContr, 90, 160, 510, 20, "DescContr", "Descrião do contrato" );
		
		adicCampo( txtCodItContr, 7, 200, 80, 20, "CodItContr", "Cód.It.Contr.", ListaCampos.DB_FK, txtDescItContr, true );
		adicDescFK( txtDescItContr, 90, 200, 510, 20, "DescItContr", "Descrição do item de contrato" );
		
		adicCampo( txtCodChamado, 7, 240, 80, 20, "Codchamado", "Cód.Chamado", ListaCampos.DB_FK, txtDescChamado, false  );
		adicDescFK( txtDescChamado, 90, 240, 510, 20, "Descchamado", "Descrição do chamado" );
		
		adicCampo( txtIndexTarefa, 7, 280, 100, 20, "IndexTarefa", "Índice", ListaCampos.DB_SI, true );
		adicCampo( txtTempoEstTarefa, 110, 280, 100, 20, "TempoEstTarefa", "Tempo Estimado", ListaCampos.DB_SI, true );
		adicCampo( txtTempoDecTarefa,213, 280, 100, 20, "TempoDecTarefa", "Tempo Estimado Decimal", ListaCampos.DB_SI, false );
				
		adicCampo( txtCodMarcor, 7, 390, 80, 20, "CodMarcor", "Cód.Marcador", ListaCampos.DB_FK, txtDescMarcor, false  );
		adicDescFK( txtDescMarcor, 90, 390, 510, 20, "Descmarcor", "Descrição do marcador" );
		
		adicDB(txaDescDetTarefa, 7, 320, 590, 50, "DescDetTarefa", "Descrição Detalhada da tarefa", true);
		adicDB(txaNotasTarefa, 7, 430, 590, 50, "NotasTarefa", "Notas da tarefa", false);
		
		setListaCampos( true, "TAREFA", "CR" );
		lcCampos.setQueryInsert( false );
		
		lcCampos.addInsertListener( this );
	
	}

	
	public void valorAlterado(RadioGroupEvent evt){
		if (evt.getSource() == rgTipoTarefa){
			visualizarSuperProjeto("S".equals( rgTipoTarefa.getVlrString()));
		}
	}
	
	private void visualizarSuperProjeto(boolean flag){
		this.txtCodTarefaPrinc.setEnabled( flag );
		this.txtDescTarefaPrinc.setEnabled( flag );
		if (!flag){
			this.txtCodTarefaPrinc.setVlrString( "" );
			this.txtDescTarefaPrinc.setVlrString( "" );
		}
	}
	
	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		lcTarefa.setConexao( cn );
		lcSuperTarefa.setConexao( cn );
		lcChamado.setConexao( cn );
		lcContrato.setConexao( cn );
		lcItContrato.setConexao( cn );
		lcMarc.setConexao( cn );
	}


	public void afterInsert( InsertEvent ievt ) {

		
	}


	public void beforeInsert( InsertEvent ievt ) {

		
	}
}