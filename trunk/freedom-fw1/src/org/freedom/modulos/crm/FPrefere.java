/*
 * Projeto: Freedom-fw1
 * Pacote: org.freedom.modules.crm
 * Classe: @(#)FPrefere.java
 * 
 * Este arquivo é parte do sistema Freedom-ERP, o Freedom-ERP é um software livre; você pode redistribui-lo e/ou <BR>
 * modifica-lo dentro dos termos da Licença Pública Geral GNU como publicada pela Fundação do Software Livre (FSF); <BR>
 * na versão 2 da Licença, ou (na sua opnião) qualquer versão. <BR>
 * Este programa é distribuido na esperança que possa ser  util, mas SEM NENHUMA GARANTIA; <BR>
 * sem uma garantia implicita de ADEQUAÇÂO a qualquer MERCADO ou APLICAÇÃO EM PARTICULAR. <BR>
 * Veja a Licença Pública Geral GNU para maiores detalhes. <BR>
 * Você deve ter recebido uma cópia da Licença Pública Geral GNU junto com este programa, se não, <BR>
 * escreva para a Fundação do Software Livre(FSF) Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA <BR> 
 */

package org.freedom.modulos.crm;

import javax.swing.BorderFactory;

import org.freedom.acao.InsertEvent;
import org.freedom.acao.InsertListener;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JCheckBoxPad;
import org.freedom.library.swing.component.JPanelPad;
import org.freedom.library.swing.component.JPasswordFieldPad;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.FTabDados;

/**
 * 
 * @author Setpoint Informática Ltda./Alex Rodrigues
 * @version 10/10/2009 - Alex Rodrigues
 */
public class FPrefere extends FTabDados implements InsertListener {

	private static final long serialVersionUID = 1L;

	private JPanelPad panelCampanhas = new JPanelPad();
	
	private JPanelPad panelEmail = new JPanelPad();
	
	private JPanelPad panelAtendimentos = new JPanelPad();
	
	private JPanelPad panelPonto = new JPanelPad();

	private JPanelPad pnEmail = new JPanelPad();

	private JTextFieldPad txtSmtpMail = new JTextFieldPad(JTextFieldPad.TP_STRING, 40, 0);

	private JTextFieldPad txtUserMail = new JTextFieldPad(JTextFieldPad.TP_STRING, 40, 0);

	private JTextFieldPad txtCodAtivTE = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 5, 0);

	private JTextFieldFK txtDescAtivTE = new JTextFieldFK(JTextFieldPad.TP_STRING, 40, 0);

	private JTextFieldPad txtCodAtivCE = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 5, 0);

	private JTextFieldFK txtDescAtivCE = new JTextFieldFK(JTextFieldPad.TP_STRING, 40, 0);

	private JTextFieldPad txtCodEmailNC = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 5, 0);

	private JTextFieldFK txtDescEmailNC = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);

	private JTextFieldPad txtCodEmailEA = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 5, 0);

	private JTextFieldFK txtDescEmailEA = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);
	
	private JTextFieldPad txtCodModel = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 5, 0);
	
	private JTextFieldFK txtDescModAtendo = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);
	
	private JTextFieldPad txtCodModelME = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 5, 0);
	
	private JTextFieldFK txtDescModAtendoME = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);

	private JTextFieldPad txtCodEmailEC = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 5, 0);

	private JTextFieldFK txtDescEmailEC = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);
	
	private JTextFieldPad txtTempoMax = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 5, 0);
	
	private JTextFieldPad txtTolerancia = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 5, 0);
	
	private JPasswordFieldPad txpPassMail = new JPasswordFieldPad(16);

	private JCheckBoxPad cbAutoHorario = new JCheckBoxPad("Data/Horário automático no atendimento?", "S", "N");
	
	private JCheckBoxPad cbMostraCliAtraso = new JCheckBoxPad("Mostra clientes em atraso no painel de controle ?", "S", "N");
	
	private JCheckBoxPad cbBloqueiaCliAtraso = new JCheckBoxPad("Bloquear atendimentos para clientes em atraso ?", "S", "N");
	
	private JCheckBoxPad cbLancaPontoAF = new JCheckBoxPad("Lança ponto na abertura e fechamento do sistema ?", "S", "N");

	private ListaCampos lcAtivTE = new ListaCampos(this, "TE");

	private ListaCampos lcAtivCE = new ListaCampos(this, "CE");
	
	private ListaCampos lcEmailNC = new ListaCampos( this, "NC" );
	
	private ListaCampos lcEmailEA = new ListaCampos( this, "EA" );
	
	private ListaCampos lcEmailEC = new ListaCampos( this, "EC" );
	
	private ListaCampos lcModAtendo = new ListaCampos( this, "MI" );
	
	private ListaCampos lcModAtendoME = new ListaCampos( this, "ME" );
	
	public FPrefere() {

		super();
		setTitulo("Preferências CRM");
		setAtribos(50, 50, 450, 375);

		montaListaCampos();


		/******************
		 * ABA EMAIL
		 *****************/
		
		adicTab("Campanhas", panelCampanhas);

		setPainel(panelCampanhas);

		adicCampo(txtCodAtivCE, 10, 30, 80, 20, "CodAtivCE", "Cód.Ativ.", ListaCampos.DB_FK, txtDescAtivCE, false);
		adicDescFK(txtDescAtivCE, 93, 30, 320, 20, "DescAtiv", "Atividade padrão para campanha enviada");
		adicCampo(txtCodAtivTE, 10, 70, 80, 20, "CodAtivTE", "Cód.Ativ.", ListaCampos.DB_FK, txtDescAtivTE, false);
		adicDescFK(txtDescAtivTE, 93, 70, 320, 20, "DescAtiv", "Atividade padrão para tentativa de envio de campanha");

	
		/******************
		 * ABA EMAIL
		 *****************/

		adicTab("Email", panelEmail);
		
		setPainel(panelEmail);
				
		pnEmail.setBorder(BorderFactory.createTitledBorder("Servidor para envio de email"));
		adic(pnEmail, 10, 10, 405, 120);
		setPainel(pnEmail);
		adicCampo(txtSmtpMail, 10, 20, 190, 20, "SmtpMail", "SMTP", ListaCampos.DB_SI, false);
		adicCampo(txtUserMail, 10, 60, 190, 20, "UserMail", "Usuario", ListaCampos.DB_SI, false);
		adicCampo(txpPassMail, 203, 60, 180, 20, "PassMail", "Senha", ListaCampos.DB_SI, false);
		
		/******************
		 * ABA ATENDIMENTOS
		 *****************/

		adicTab("Atendimentos", panelAtendimentos);

		setPainel(panelAtendimentos);
		
		adicDB(cbAutoHorario, 10, 10, 405, 20, "AUTOHORATEND", "", false);
		adicDB(cbMostraCliAtraso, 10, 30, 405, 20, "MOSTRACLIATRASO", "", false);
		adicDB(cbBloqueiaCliAtraso, 10, 50, 405, 20, "BLOQATENDCLIATRASO", "", false);
		
		adicCampo(txtCodEmailNC, 7, 100, 80, 20, "CodEmailNC", "Cód.Email", ListaCampos.DB_FK, txtDescEmailNC, false);
		adicDescFK(txtDescEmailNC, 90, 100, 320, 20, "DescEmail", "Email para notificação de chamados ao técnico");

		adicCampo(txtCodEmailEA, 7, 140, 80, 20, "CodEmailEA", "Cód.Email", ListaCampos.DB_FK, txtDescEmailEA, false);
		adicDescFK(txtDescEmailEA, 90, 140, 320, 20, "DescEmail", "Email para notificação de chamados ao atendente");

		adicCampo(txtCodEmailEC, 7, 180, 80, 20, "CodEmailEC", "Cód.Email", ListaCampos.DB_FK, txtDescEmailEC, false);
		adicDescFK(txtDescEmailEC, 90, 180, 320, 20, "DescEmail", "Email para notificação de chamados ao cliente");
		
		adicCampo(txtCodModel, 7, 220, 80, 20, "CodModelMi", "Cód.Model", ListaCampos.DB_FK, txtDescModAtendo, false);
		adicDescFK(txtDescModAtendo, 90, 220, 320, 20, "DescModel", "Descrição modelo de atendimento para intervalo");
		
			
				
		/******************
		 * ABA PONTO
		 *****************/
		
		adicTab("Ponto", panelPonto);
		
		setPainel(panelPonto);
		
		adicCampo(txtCodModelME, 7, 30, 80, 20, "CodModelMe", "Cód.Model", ListaCampos.DB_FK, txtDescModAtendoME, false);
		adicDescFK(txtDescModAtendoME, 90, 30, 320, 20, "DescModel", "Desc. mod. interv. entre chegada e inic. equip. " );
		adicCampo(txtTempoMax, 7, 70, 140, 20, "TempoMaxInt", "Tempo máx.int.(min.)", ListaCampos.DB_SI, false); 
		adicCampo(txtTolerancia, 7, 108, 140, 20, "TolRegPonto", "Tolerância (min.)", ListaCampos.DB_SI, false); 
		adicDB(cbLancaPontoAF, 7, 130, 340, 20, "LANCAPONTOAF", "", true);
	
	
		
		setListaCampos(false, "PREFERE3", "SG");

		nav.setAtivo(0, false);
		nav.setAtivo(1, false);
	}

	private void montaListaCampos() {

		lcAtivCE.add(new GuardaCampo(txtCodAtivCE, "CodAtiv", "Cód.ativ.", ListaCampos.DB_PK, false));
		lcAtivCE.add(new GuardaCampo(txtDescAtivCE, "DescAtiv", "Descrição da atividade", ListaCampos.DB_SI, false));
		lcAtivCE.montaSql(false, "ATIVIDADE", "TK");
		lcAtivCE.setReadOnly(true);
		lcAtivCE.setQueryCommit(false);
		txtCodAtivCE.setTabelaExterna(lcAtivCE, null);

		lcAtivTE.add(new GuardaCampo(txtCodAtivTE, "CodAtiv", "Cód.ativ.", ListaCampos.DB_PK, false));
		lcAtivTE.add(new GuardaCampo(txtDescAtivTE, "DescAtiv", "Descrição da atividade", ListaCampos.DB_SI, false));
		lcAtivTE.montaSql(false, "ATIVIDADE", "TK");
		lcAtivTE.setReadOnly(true);
		lcAtivTE.setQueryCommit(false);
		txtCodAtivTE.setTabelaExterna(lcAtivTE, null);
		
		// Email Notificação de Técnico
		lcEmailNC.add( new GuardaCampo( txtCodEmailNC, "CodEmail", "Cód.Email", ListaCampos.DB_PK, false ) );
		lcEmailNC.add( new GuardaCampo( txtDescEmailNC, "DescEmail", "Descrição do Email", ListaCampos.DB_SI,  false ) );
		lcEmailNC.montaSql( false, "EMAIL", "TK" );
		lcEmailNC.setQueryCommit( false );
		lcEmailNC.setReadOnly( true );
		txtCodEmailNC.setTabelaExterna(lcEmailNC, null);
		txtCodEmailNC.setNomeCampo( "CodEmail" );
		txtCodEmailNC.setPK( true );
		txtCodEmailNC.setListaCampos( lcEmailNC );
		
		
		// Email Notificação de Atendente
		lcEmailEA.add( new GuardaCampo( txtCodEmailEA, "CodEmail", "Cód.Email", ListaCampos.DB_PK, false ) );
		lcEmailEA.add( new GuardaCampo( txtDescEmailEA, "DescEmail", "Descrição do Email", ListaCampos.DB_SI,  false ) );
		lcEmailEA.montaSql( false, "EMAIL", "TK" );
		lcEmailEA.setQueryCommit( false );
		lcEmailEA.setReadOnly( true );
		txtCodEmailEA.setTabelaExterna(lcEmailEA, null);
		txtCodEmailEA.setNomeCampo( "CodEmail" );
		txtCodEmailEA.setPK( true );
		txtCodEmailEA.setListaCampos( lcEmailEA );
		
		// Email Notificação de Cliente
		lcEmailEC.add( new GuardaCampo( txtCodEmailEC, "CodEmail", "Cód.Email", ListaCampos.DB_PK, false ) );
		lcEmailEC.add( new GuardaCampo( txtDescEmailEC, "DescEmail", "Descrição do Email", ListaCampos.DB_SI,  false ) );
		lcEmailEC.montaSql( false, "EMAIL", "TK" );
		lcEmailEC.setQueryCommit( false );
		lcEmailEC.setReadOnly( true );
		txtCodEmailEC.setTabelaExterna(lcEmailEC, null);
		txtCodEmailEC.setNomeCampo( "CodEmail" );
		txtCodEmailEC.setPK( true );
		txtCodEmailEC.setListaCampos( lcEmailEC );
		
		//Modelo de Atendimento.
		lcModAtendo.add( new GuardaCampo(txtCodModel, "CodModel", "Cód.Model", ListaCampos.DB_PK, false ) );
		lcModAtendo.add( new GuardaCampo(txtDescModAtendo, "DescModel", "Descrição do Modelo de Atendimento", ListaCampos.DB_SI, false ));
		lcModAtendo.montaSql( false, "MODATENDO", "AT" );
		lcModAtendo.setQueryCommit( false );
		lcModAtendo.setReadOnly( true );
		txtCodModel.setTabelaExterna(lcModAtendo, null);
		txtCodModel.setNomeCampo( "CodModel" );
		txtCodModel.setPK( true );
		txtCodModel.setListaCampos( lcModAtendo );
		
		
		//Modelo de Atendimento - ABA PONTO.
		lcModAtendoME.add( new GuardaCampo(txtCodModelME, "CodModel", "Cód.Model", ListaCampos.DB_PK, false ) );
		lcModAtendoME.add( new GuardaCampo(txtDescModAtendoME, "DescModel", "Descrição do Modelo de Atendimento", ListaCampos.DB_SI, false ));
		lcModAtendoME.montaSql( false, "MODATENDO", "AT" );
		lcModAtendoME.setQueryCommit( false );
		lcModAtendoME.setReadOnly( true );
		txtCodModelME.setTabelaExterna(lcModAtendoME, null);
		txtCodModelME.setNomeCampo( "CodModel" );
		txtCodModelME.setPK( true );
		txtCodModelME.setListaCampos( lcModAtendoME );
		
	}

	public void setConexao(DbConnection cn) {

		super.setConexao(cn);
		
		lcAtivCE.setConexao(cn);
		lcAtivTE.setConexao(cn);
		lcEmailNC.setConexao(cn);
		lcEmailEA.setConexao(cn);
		lcEmailEC.setConexao(cn);
		lcModAtendo.setConexao(cn);
		lcModAtendoME.setConexao(cn);
		lcCampos.carregaDados();
		
	}
	
	public void afterInsert(InsertEvent ievt) {
		/*
		if (ievt.getListaCampos() == lcCampos) {
			txtTempoMax.setVlrInteger(18);	
		}
		if (ievt.getListaCampos() == lcCampos) {
			txtTolerancia.setVlrInteger(20);	
		}
		*/

	}

	public void beforeInsert(InsertEvent ievt) {
		
	}
}
