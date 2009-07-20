/**
 * @version 10/10/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.tmk <BR>
 *         Classe:
 * @(#)FContato.java <BR>
 * 
 * Este arquivo é parte do sistema Freedom-ERP, o Freedom-ERP é um software livre; você pode redistribui-lo e/ou <BR>
 * modifica-lo dentro dos termos da Licença Pública Geral GNU como publicada pela Fundação do Software Livre (FSF); <BR>
 * na versão 2 da Licença, ou (na sua opnião) qualquer versão. <BR>
 * Este programa é distribuido na esperança que possa ser  util, mas SEM NENHUMA GARANTIA; <BR>
 * sem uma garantia implicita de ADEQUAÇÂO a qualquer MERCADO ou APLICAÇÃO EM PARTICULAR. <BR>
 * Veja a Licença Pública Geral GNU para maiores detalhes. <BR>
 * Você deve ter recebido uma cópia da Licença Pública Geral GNU junto com este programa, se não, <BR>
 *                   de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 *                   Tela de cadastro de contatos.
 * 
 */

package org.freedom.modulos.crm;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.freedom.infra.model.jdbc.DbConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;

import javax.swing.JButton;
import org.freedom.componentes.JLabelPad;
import javax.swing.JOptionPane;
import org.freedom.componentes.JPanelPad;
import javax.swing.JScrollPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.freedom.acao.PostEvent;
import org.freedom.acao.PostListener;
import org.freedom.acao.RadioGroupEvent;
import org.freedom.acao.RadioGroupListener;
import org.freedom.bmps.Icone;
import org.freedom.componentes.Endereco;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextAreaPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Navegador;
import org.freedom.componentes.Tabela;
import org.freedom.funcoes.Funcoes;
import org.freedom.modulos.std.FCliente;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FAndamento;
import org.freedom.telas.FTabDados;
import org.freedom.webservices.WSCep;

public class FContato extends FTabDados implements RadioGroupListener, PostListener, ActionListener, ChangeListener {

	private static final long serialVersionUID = 1L;

	private JPanelPad pinCont = new JPanelPad();

	private JPanelPad pinVend = new JPanelPad( 0, 80 );

	private JPanelPad pinRodAtiv = new JPanelPad( 0, 80 );

	private JPanelPad pinRodGrupos = new JPanelPad( 0, 80 );

	private JPanelPad pinGrupos = new JPanelPad( 0, 80 );

	private Tabela tabAtiv = new Tabela();

	private JScrollPane spnAtiv = new JScrollPane( tabAtiv );

	private Tabela tabGrupos = new Tabela();

	private JScrollPane spnGrupos = new JScrollPane( tabGrupos );

	private JPanelPad pnAtiv = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad pnGrupos = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JTextFieldPad txtCodCont = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );

	private JTextFieldPad txtRazCont = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtNomeCont = new JTextFieldPad( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtCargoCont = new JTextFieldPad( JTextFieldPad.TP_STRING, 30, 0 );

	private JTextFieldPad txtContCont = new JTextFieldPad( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtCnpjCont = new JTextFieldPad( JTextFieldPad.TP_STRING, 14, 0 );

	private JTextFieldPad txtInscCont = new JTextFieldPad( JTextFieldPad.TP_STRING, 15, 0 );

	private JTextFieldPad txtCpfCont = new JTextFieldPad( JTextFieldPad.TP_STRING, 11, 0 );

	private JTextFieldPad txtRgCont = new JTextFieldPad( JTextFieldPad.TP_STRING, 10, 0 );

	private JTextFieldPad txtEndCont = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtNumCont = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtComplCont = new JTextFieldPad( JTextFieldPad.TP_STRING, 20, 0 );

	private JTextFieldPad txtBairCont = new JTextFieldPad( JTextFieldPad.TP_STRING, 30, 0 );

	private JTextFieldPad txtCidCont = new JTextFieldPad( JTextFieldPad.TP_STRING, 30, 0 );

	private JTextFieldPad txtUFCont = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );

	private JTextFieldPad txtCepCont = new JTextFieldPad( JTextFieldPad.TP_STRING, 8, 0 );

	private JTextFieldPad txtFoneCont = new JTextFieldPad( JTextFieldPad.TP_STRING, 12, 0 );

	private JTextFieldPad txtFaxCont = new JTextFieldPad( JTextFieldPad.TP_STRING, 8, 0 );

	private JTextFieldPad txtEmailCont = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtNumEmp = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 50, 0 );

	private JTextFieldPad txtCodVend = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescVend = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtDataCont = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtCodSetor = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescSetor = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtCodAtiv = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescAtiv = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtCodGrup = new JTextFieldPad( JTextFieldPad.TP_STRING, 14, 0 );

	private JTextFieldFK txtDescGrup = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtCodPais = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescPais = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtCodMun = new JTextFieldPad( JTextFieldPad.TP_STRING, 8, 0 );

	private JTextFieldFK txtDescMun = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldFK txtDDDMun = new JTextFieldFK( JTextFieldPad.TP_STRING, 4, 0 );

	private JTextFieldPad txtSiglaUF = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );

	private JTextFieldFK txtNomeUF = new JTextFieldFK( JTextFieldPad.TP_STRING, 80, 0 );

	private JTextFieldPad txtCodTipoCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );

	private JTextFieldPad txtCodOrigCont = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );

	private JTextFieldFK txtDescOrigCont = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldFK txtDescTipoCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private Vector<String> vPessoaLab = new Vector<String>();

	private Vector<String> vPessoaVal = new Vector<String>();

	private JRadioGroup<?, ?> rgPessoa = null;

	private JPanelPad pnCompl = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JTextAreaPad txaObs = new JTextAreaPad();

	private JScrollPane spnObs = new JScrollPane( txaObs );

	private ListaCampos lcVend = new ListaCampos( this, "VD" );

	private ListaCampos lcSetor = new ListaCampos( this, "SR" );

	private ListaCampos lcAtiv = new ListaCampos( this, "AV" );

	private ListaCampos lcGrupo = new ListaCampos( this, "GP" );

	private ListaCampos lcMunic = new ListaCampos( this );

	private ListaCampos lcOrigCont = new ListaCampos( this, "OC" );

	private ListaCampos lcTipoCli = new ListaCampos( this, "TC" );

	private ListaCampos lcUF = new ListaCampos( this );

	private ListaCampos lcPais = new ListaCampos( this );

	private ListaCampos lcAtivFK = new ListaCampos( this, "AV" );

	private ListaCampos lcGrupFK = new ListaCampos( this, "GP" );

	private Navegador navAtiv = new Navegador( true );

	private Navegador navGrupos = new Navegador( true );

	private JButton btExportCli = new JButton( Icone.novo( "btExportaCli.gif" ) );

	private JButton btBuscaEnd = new JButton( Icone.novo( "btBuscacep.gif" ) );

	public FContato() {

		super();
		setTitulo( "Cadastro de Contatos" );
		setAtribos( 50, 10, 530, 600 );

		lcAtiv.setMaster( lcCampos );
		lcCampos.adicDetalhe( lcAtiv );
		lcAtiv.setTabela( tabAtiv );

		lcGrupo.setMaster( lcCampos );
		lcCampos.adicDetalhe( lcGrupo );
		lcGrupo.setTabela( tabGrupos );

		pinCont = new JPanelPad( 500, 330 );
		setPainel( pinCont );
		adicTab( "Contato", pinCont );

		lcCampos.addPostListener( this );

		lcVend.add( new GuardaCampo( txtCodVend, "CodVend", "Cód.comiss.", ListaCampos.DB_PK, false ) );
		lcVend.add( new GuardaCampo( txtDescVend, "NomeVend", "Nome do comissionado", ListaCampos.DB_SI, false ) );
		lcVend.montaSql( false, "VENDEDOR", "VD" );
		lcVend.setQueryCommit( false );
		lcVend.setReadOnly( true );
		txtCodVend.setTabelaExterna( lcVend );

		lcSetor.add( new GuardaCampo( txtCodSetor, "CodSetor", "Cód.setor", ListaCampos.DB_PK, true ) );
		lcSetor.add( new GuardaCampo( txtDescSetor, "DescSetor", "Descrição do setor", ListaCampos.DB_SI, false ) );
		lcSetor.montaSql( false, "SETOR", "VD" );
		lcSetor.setQueryCommit( false );
		lcSetor.setReadOnly( true );
		txtCodSetor.setTabelaExterna( lcSetor );

		lcAtivFK.add( new GuardaCampo( txtCodAtiv, "CodAtiv", "Cód.ativ.", ListaCampos.DB_PK, true ) );
		lcAtivFK.add( new GuardaCampo( txtDescAtiv, "DescAtiv", "Descrição da atividade", ListaCampos.DB_SI, false ) );
		lcAtivFK.montaSql( false, "ATIVIDADE", "TK" );
		lcAtivFK.setReadOnly( true );
		lcAtivFK.setQueryCommit( false );
		txtCodAtiv.setTabelaExterna( lcAtivFK );

		lcGrupFK.add( new GuardaCampo( txtCodGrup, "CodGrup", "Cód.grup.", ListaCampos.DB_PK, true ) );
		lcGrupFK.add( new GuardaCampo( txtDescGrup, "DescGrup", "Descrição do grupo", ListaCampos.DB_SI, false ) );
		lcGrupFK.montaSql( false, "GRUPO", "EQ" );
		lcGrupFK.setReadOnly( true );
		lcGrupFK.setQueryCommit( false );
		txtCodGrup.setTabelaExterna( lcGrupFK );

		lcOrigCont.add( new GuardaCampo( txtCodOrigCont, "CodOrigCont", "Cód.origem", ListaCampos.DB_PK, true ) );
		lcOrigCont.add( new GuardaCampo( txtDescOrigCont, "DescOrigCont", "descrição da origem", ListaCampos.DB_SI, false ) );
		lcOrigCont.montaSql( false, "ORIGCONT", "TK" );
		lcOrigCont.setQueryCommit( false );
		lcOrigCont.setReadOnly( true );
		txtCodOrigCont.setTabelaExterna( lcOrigCont );

		/***************
		 * MUNICIPIO *
		 **************/

		lcMunic.setUsaME( false );
		lcMunic.add( new GuardaCampo( txtCodMun, "CodMunic", "Cód.Munic.", ListaCampos.DB_PK, true ) );
		lcMunic.add( new GuardaCampo( txtDescMun, "NomeMunic", "Nome Munic.", ListaCampos.DB_SI, false ) );
		lcMunic.add( new GuardaCampo( txtDDDMun, "DDDMunic", "DDD Munic.", ListaCampos.DB_SI, false ) );
		lcMunic.setDinWhereAdic( "SIGLAUF = #S", txtSiglaUF );
		lcMunic.montaSql( false, "MUNICIPIO", "SG" );
		lcMunic.setQueryCommit( false );
		lcMunic.setReadOnly( true );
		txtCodMun.setTabelaExterna( lcMunic );

		/***************
		 * UF *
		 **************/

		lcUF.setUsaME( false );
		lcUF.add( new GuardaCampo( txtSiglaUF, "SiglaUf", "Sigla", ListaCampos.DB_PK, true ) );
		lcUF.add( new GuardaCampo( txtNomeUF, "NomeUf", "Nome", ListaCampos.DB_SI, false ) );
		lcMunic.setDinWhereAdic( "CODPAIS = #S", txtCodPais );
		lcUF.montaSql( false, "UF", "SG" );
		lcUF.setQueryCommit( false );
		lcUF.setReadOnly( true );
		txtSiglaUF.setTabelaExterna( lcUF );

		/***************
		 * PAIS *
		 **************/

		lcPais.setUsaME( false );
		lcPais.add( new GuardaCampo( txtCodPais, "CodPais", "Cod.país.", ListaCampos.DB_PK, true ) );
		lcPais.add( new GuardaCampo( txtDescPais, "NomePais", "Nome", ListaCampos.DB_SI, false ) );
		lcPais.montaSql( false, "PAIS", "SG" );
		lcPais.setQueryCommit( false );
		lcPais.setReadOnly( true );
		txtCodPais.setTabelaExterna( lcPais );

		/***************
		 * TIPO CLI *
		 **************/

		lcTipoCli.add( new GuardaCampo( txtCodTipoCli, "CodTipoCli", "Cód.tp.cli.", ListaCampos.DB_PK, true ) );
		lcTipoCli.add( new GuardaCampo( txtDescTipoCli, "DescTipoCli", "Descrição do tipo de cliente", ListaCampos.DB_SI, false ) );
		lcTipoCli.montaSql( false, "TIPOCLI", "VD" );
		lcTipoCli.setQueryCommit( false );
		lcTipoCli.setReadOnly( true );
		txtCodTipoCli.setTabelaExterna( lcTipoCli );

		adicCampo( txtCodCont, 7, 20, 80, 20, "CodCto", "Cód.cto.", ListaCampos.DB_PK, true );
		adicCampo( txtRazCont, 90, 20, 307, 20, "RazCto", "Razão social do contato", ListaCampos.DB_SI, true );
		adicCampo( txtEmailCont, 210, 270, 157, 20, "EmailCto", "E-Mail", ListaCampos.DB_SI, false );

		vPessoaLab.addElement( "Jurídica" );
		vPessoaLab.addElement( "Física" );
		vPessoaVal.addElement( "J" );
		vPessoaVal.addElement( "F" );

		rgPessoa = new JRadioGroup<String, String>( 2, 1, vPessoaLab, vPessoaVal );
		rgPessoa.addRadioGroupListener( this );

		adicDB( rgPessoa, 400, 20, 100, 60, "PessoaCto", "Pessoa", true );
		rgPessoa.setVlrString( "F" );

		JCheckBoxPad cbAtivo = new JCheckBoxPad( "Ativo", "S", "N" );

		adicDB( cbAtivo, 19, 60, 70, 20, "AtivoCto", "Ativo", true );
		adicCampo( txtNomeCont, 90, 60, 307, 20, "NomeCto", "Nome do contato", ListaCampos.DB_SI, true );
		adicCampo( txtCpfCont, 273, 110, 111, 20, "CpfCto", "CPF", ListaCampos.DB_SI, false );
		adicCampo( txtCodSetor, 7, 110, 60, 20, "CodSetor", "Cód.setor", ListaCampos.DB_FK, txtDescSetor, true );
		adicDescFK( txtDescSetor, 70, 110, 200, 20, "DescSetor", "Descrição do setor do contato" );
		adicCampo( txtRgCont, 387, 110, 112, 20, "RgCto", "RG", ListaCampos.DB_SI, false );
		adicCampo( txtCnpjCont, 7, 150, 150, 20, "CnpjCto", "CNPJ", ListaCampos.DB_SI, false );
		adicCampo( txtInscCont, 160, 150, 147, 20, "InscCto", "Inscrição Estadual", ListaCampos.DB_SI, false );
		adicCampo( txtContCont, 310, 150, 107, 20, "ContCto", "Falar com:", ListaCampos.DB_SI, false );
		adicCampo( txtCargoCont, 420, 150, 80, 20, "CargoContCto", "Cargo", ListaCampos.DB_SI, false );
		adicCampo( txtEndCont, 125, 190, 375, 20, "EndCto", "Endereço", ListaCampos.DB_SI, false );
		adicCampo( txtNumCont, 7, 230, 77, 20, "NumCto", "Num.", ListaCampos.DB_SI, false );
		adicCampo( txtComplCont, 88, 230, 130, 20, "ComplCto", "Compl.", ListaCampos.DB_SI, false );
		adicCampo( txtBairCont, 221, 230, 280, 20, "BairCto", "Bairro", ListaCampos.DB_SI, false );
		adic( btBuscaEnd, 100, 190, 20, 20 );
		adicCampo( txtCepCont, 7, 190, 90, 20, "CepCto", "Cep", ListaCampos.DB_SI, false );
		adicCampo( txtFoneCont, 7, 270, 100, 20, "FoneCto", "Telefone", ListaCampos.DB_SI, false );
		adicCampo( txtFaxCont, 110, 270, 97, 20, "FaxCto", "Fax", ListaCampos.DB_SI, false );
		adicCampo( txtNumEmp, 370, 270, 97, 20, "numempcto", "Nº Funcionarios", ListaCampos.DB_SI, false );

		adicCampo( txtCodTipoCli, 7, 310, 70, 20, "CodTipoCli", "Cód.Tipo Cli.", ListaCampos.DB_FK, txtDescTipoCli, true );
		adicDescFK( txtDescTipoCli, 80, 310, 250, 20, "DescTipoCli", "Descrição do tipo de cliente" );
		adicCampo( txtCodPais, 7, 350, 70, 20, "CodPais", "Cod.país", ListaCampos.DB_FK, txtDescPais, true );
		adicDescFK( txtDescPais, 80, 350, 250, 20, "NomePais", "Nome do país" );
		adicCampo( txtSiglaUF, 7, 390, 70, 20, "SiglaUf", "Sigla UF", ListaCampos.DB_FK, txtNomeUF, true );
		adicDescFK( txtNomeUF, 80, 390, 250, 20, "NomeUF", "Nome UF" );
		adicCampo( txtCodMun, 7, 430, 70, 20, "CodMunic", "Cod.munic.", ListaCampos.DB_FK, txtDescMun, false );
		adicDescFK( txtDescMun, 80, 430, 250, 20, "NomeMunic", "Nome do municipio" );
		adicCampo( txtCodOrigCont, 7, 470, 70, 20, "CodOrigCont", "Cod.orig.", ListaCampos.DB_FK, txtDescOrigCont, false );
		adicDescFK( txtDescOrigCont, 80, 470, 250, 20, "DescOrigCont", "Descrição da origem" );

		adic( btExportCli, 470, 260, 30, 30 );
		txtCpfCont.setMascara( JTextFieldPad.MC_CPF );
		txtCnpjCont.setMascara( JTextFieldPad.MC_CNPJ );
		txtCepCont.setMascara( JTextFieldPad.MC_CEP );
		txtFoneCont.setMascara( JTextFieldPad.MC_FONEDDD );
		txtFaxCont.setMascara( JTextFieldPad.MC_FONE );
		adicTab( "Informações complementares", pnCompl );
		adicDBLiv( txaObs, "ObsCto", "Observações", false );
		pnCompl.add( pinVend, BorderLayout.NORTH );
		pnCompl.add( spnObs, BorderLayout.CENTER );
		setPainel( pinVend );

		btBuscaEnd.addActionListener( this );
		btBuscaEnd.setToolTipText( "Busca Endereço a partir do CEP" );

		adicCampo( txtCodVend, 7, 25, 80, 20, "CodVend", "Cód.comiss.", ListaCampos.DB_FK, txtDescVend, false );
		adicDescFK( txtDescVend, 90, 25, 237, 20, "NomeVend", "Nome do comissionado" );
		adicCampo( txtDataCont, 330, 25, 80, 20, "DataCto", "Data", ListaCampos.DB_SI, false );
		adic( new JLabelPad( "Observações:" ), 7, 55, 150, 20 );
		setListaCampos( true, "CONTATO", "TK" );

		// Atividade

		setPainel( pinRodAtiv, pnAtiv );
		adicTab( "Atividade", pnAtiv );
		setListaCampos( lcAtiv );
		setNavegador( navAtiv );
		pnAtiv.add( pinRodAtiv, BorderLayout.SOUTH );
		pnAtiv.add( spnAtiv, BorderLayout.CENTER );

		pinRodAtiv.adic( navAtiv, 0, 50, 270, 25 );

		adicCampo( txtCodAtiv, 7, 20, 80, 20, "CodAtiv", "Cód.ativ.", ListaCampos.DB_PF, txtDescAtiv, true );
		adicDescFK( txtDescAtiv, 90, 20, 220, 20, "DescAtiv", "Descrição da atividade" );
		setListaCampos( false, "CTOATIV", "TK" );
		lcAtiv.montaTab();
		lcAtiv.setQueryInsert( false );
		lcAtiv.setQueryCommit( false );

		tabAtiv.setTamColuna( 220, 1 );

		setPainel( pinRodGrupos, pnGrupos );
		adicTab( "Interesses", pnGrupos );
		setListaCampos( lcGrupo );
		setNavegador( navGrupos );
		pnGrupos.add( pinRodGrupos, BorderLayout.SOUTH );
		pnGrupos.add( spnGrupos, BorderLayout.CENTER );

		pinRodGrupos.adic( navGrupos, 0, 50, 270, 25 );

		adicCampo( txtCodGrup, 7, 20, 80, 20, "CodGrup", "Cód.Grupo.", ListaCampos.DB_PF, txtDescGrup, true );
		adicDescFK( txtDescGrup, 90, 20, 220, 20, "DescGrup", "Descrição do grupo" );
		setListaCampos( false, "CTOGRPINT", "TK" );
		lcGrupo.montaTab();
		lcGrupo.setQueryInsert( false );
		lcGrupo.setQueryCommit( false );

		tabGrupos.setTamColuna( 80, 0 );
		tabGrupos.setTamColuna( 250, 1 );

		btImp.addActionListener( this );
		btPrevimp.addActionListener( this );
		btExportCli.addActionListener( this );
		tpn.addChangeListener( this );
		lcCampos.setQueryInsert( false );
		setImprimir( true );
	}

	private void buscaEndereco() {

		if ( !"".equals( txtCepCont.getVlrString() ) ) {

			txtEndCont.setEnabled( false );
			txtComplCont.setEnabled( false );
			txtBairCont.setEnabled( false );
			txtCodPais.setEnabled( false );
			txtSiglaUF.setEnabled( false );
			txtCodMun.setEnabled( false );
			txtDDDMun.setEnabled( false );

			Thread th = new Thread( new Runnable() {

				public void run() {

					try {
						WSCep cep = new WSCep();
						cep.setCon( con );
						cep.setCep( txtCepCont.getVlrString() );
						cep.busca();
						Endereco endereco = cep.getEndereco();

						txtEndCont.setVlrString( endereco.getTipo() + " " + endereco.getLogradouro() );
						txtComplCont.setVlrString( endereco.getComplemento() );
						txtBairCont.setVlrString( endereco.getBairro() );
						txtCidCont.setVlrString( endereco.getCidade() );
						txtUFCont.setVlrString( endereco.getSiglauf() );
						txtCodPais.setVlrInteger( endereco.getCodpais() );
						txtSiglaUF.setVlrString( endereco.getSiglauf() );
						txtCodMun.setVlrString( endereco.getCodmunic() );

						lcPais.carregaDados();
						lcUF.carregaDados();
						lcMunic.carregaDados();

						txtNumCont.requestFocus();
					} catch ( Exception e ) {
						Funcoes.mensagemInforma( null, "Não foi encontrado o endereço para o CEP informado!" );
					} finally {
						txtEndCont.setEnabled( true );
						txtComplCont.setEnabled( true );
						txtBairCont.setEnabled( true );
						txtCodPais.setEnabled( true );
						txtSiglaUF.setEnabled( true );
						txtCodMun.setEnabled( true );
						txtDDDMun.setEnabled( true );
					}
				}
			} );
			try {
				th.start();
			} catch ( Exception err ) {
				Funcoes.mensagemInforma( null, "Não foi encontrado o endereço para o CEP informado!" );
				txtCepCont.requestFocus();
			}
		}
		else {
			Funcoes.mensagemInforma( null, "Digite um CEP para busca!" );
			txtCepCont.requestFocus();
		}

	}

	private boolean duploCNPJ() {

		boolean bRetorno = false;
		String sSQL = "SELECT CNPJCTO FROM TKCONTATO WHERE CNPJCTO=?";
		try {
			PreparedStatement ps = con.prepareStatement( sSQL );
			ps.setString( 1, txtCnpjCont.getVlrString() );
			ResultSet rs = ps.executeQuery();
			if ( rs.next() ) {
				bRetorno = true;
			}
			rs.close();
			ps.close();
		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro ao checar CNPJ.\n" + err.getMessage(), true, con, err );
		}
		return bRetorno;
	}

	private void exportaCli() {

		if ( txtCodCont.getVlrInteger().intValue() == 0 || lcCampos.getStatus() != ListaCampos.LCS_SELECT ) {
			Funcoes.mensagemInforma( this, "Selecione um contato cadastrado antes!" );
			return;
		}
		DLContToCli dl = new DLContToCli( this, txtCodSetor.getVlrInteger().intValue() );
		dl.setConexao( con );
		dl.setVisible( true );
		if ( !dl.OK ) {
			dl.dispose();
			return;
		}
		int[] iVals = dl.getValores();
		dl.dispose();
		String sSQL = "SELECT IRET FROM TKCONTTOCLI(?,?,?,?,?,?,?,?,?)";
		try {
			PreparedStatement ps = con.prepareStatement( sSQL );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, lcCampos.getCodFilial() );
			ps.setInt( 3, txtCodCont.getVlrInteger().intValue() );
			ps.setInt( 4, iVals[ 0 ] );
			ps.setInt( 5, iVals[ 1 ] );
			ps.setInt( 6, iVals[ 2 ] );
			ps.setInt( 7, iVals[ 3 ] );
			ps.setInt( 8, iVals[ 4 ] );
			ps.setInt( 9, iVals[ 5 ] );
			ResultSet rs = ps.executeQuery();
			if ( rs.next() ) {
				if ( Funcoes.mensagemConfirma( this, "Cliente '" + rs.getInt( 1 ) + "' criado com sucesso!\n" + "Gostaria de edita-lo agora?" ) == JOptionPane.OK_OPTION ) {
					abreCli( rs.getInt( 1 ) );
				}
			}
			rs.close();
			ps.close();
			con.commit();
		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro ao criar cliente!\n" + err.getMessage(), true, con, err );
			err.printStackTrace();
		}
		dl.dispose();
	}

	private void abreCli( int iCodCli ) {

		if ( !fPrim.temTela( "Cliente" ) ) {
			FCliente tela = new FCliente();
			fPrim.criatela( "Cliente", tela, con );
			tela.exec( iCodCli );
		}
	}

	private void imprimir( boolean bVisualizar ) {

		PreparedStatement ps = null;
		ResultSet rs = null;
		String sSQL = null;
		String sObs = "";
		String sWhere = "";
		String sAnd = " WHERE ";
		String sTmp = null;
		String[] sValores;
		Vector<String> vFiltros = new Vector<String>();
		ImprimeOS imp = new ImprimeOS( "", con );
		int linPag = imp.verifLinPag() - 1;
		int iContaReg = 0;

		FAndamento And = null;
		DLRCont dl = new DLRCont( this, con );
		dl.setVisible( true );
		if ( dl.OK == false ) {
			dl.dispose();
			return;
		}
		sValores = dl.getValores();
		dl.dispose();
		if ( sValores[ 1 ].equals( "S" ) ) {
			sObs = ",OBSCTO";
		}
		if ( sValores[ 2 ].trim().length() > 0 ) {
			sWhere = sWhere + sAnd + "RAZCTO >= '" + sValores[ 2 ] + "'";
			vFiltros.add( "RAZAO MAIORES QUE " + sValores[ 2 ].trim() );
			sAnd = " AND ";
		}
		if ( sValores[ 3 ].trim().length() > 0 ) {
			sWhere = sWhere + sAnd + "RAZCTO <= '" + sValores[ 3 ] + "'";
			vFiltros.add( "RAZAO MENORES QUE " + sValores[ 3 ].trim() );
			sAnd = " AND ";
		}
		if ( sValores[ 4 ].equals( "N" ) ) {
			sWhere = sWhere + sAnd + "PESSOACTO <> 'F'";
			vFiltros.add( "PESSOAS JURIDICAS" );
			sAnd = " AND ";
		}
		if ( sValores[ 5 ].length() > 0 ) {
			sWhere = sWhere + sAnd + "CIDCTO = '" + sValores[ 5 ] + "'";
			vFiltros.add( "CIDADE = " + sValores[ 5 ].trim() );
			sAnd = " AND ";
		}
		if ( sValores[ 6 ].equals( "N" ) ) {
			sWhere = sWhere + sAnd + "PESSOACTO <> 'J'";
			vFiltros.add( "PESSOAS FISICA" );
			sAnd = " AND ";
		}
		if ( sValores[ 8 ].length() > 0 ) {
			sWhere = sWhere + sAnd + "CODSETOR = " + sValores[ 8 ];
			vFiltros.add( "SETOR = " + sValores[ 9 ] );
			sAnd = " AND ";
		}
		if ( sValores[ 7 ].equals( "C" ) ) {
			sSQL = "SELECT CODCTO,RAZCTO,PESSOACTO,NOMECTO,CONTCTO,ENDCTO,NUMCTO," + 
			"BAIRCTO,CIDCTO,COMPLCTO,UFCTO,CEPCTO,CNPJCTO,INSCCTO,CPFCTO,RGCTO," + 
			"DDDCTO, FONECTO,FAXCTO,EMAILCTO" + 
			sObs + " FROM TKCONTATO" + 
			sWhere + 
			" ORDER BY " + sValores[ 0 ];
			try {
				ps = con.prepareStatement( "SELECT COUNT(*) FROM TKCONTATO" + sWhere );
				rs = ps.executeQuery();
				rs.next();
				And = new FAndamento( "Montando relatório, Aguarde!", 0, rs.getInt( 1 ) - 1 );
				con.commit();

				ps = con.prepareStatement( sSQL );
				rs = ps.executeQuery();

				imp.montaCab();
				imp.setTitulo( "Relatório de Contatos" );
				imp.addSubTitulo( "Relatório de Contatos" );
				imp.addSubTitulo( "Filtrado por:" );
				for ( int i = 0; i < vFiltros.size(); i++ ) {
					sTmp = vFiltros.elementAt( i );
					imp.addSubTitulo( sTmp );
				}
				imp.limpaPags();
				while ( rs.next() ) {
					if ( imp.pRow() >= linPag ) {
						imp.incPags();
						imp.eject();
					}
					if ( imp.pRow() == 0 ) {
						imp.impCab( 136, true );
					    imp.say( imp.pRow()+1, 0, imp.comprimido() );
					    imp.say( imp.pRow(), 0, "|" + Funcoes.replicate( "-", 133 ) + "|" );
					}
					imp.say( imp.pRow() + 1, 0, imp.comprimido() );
					imp.say( imp.pRow(), 0, "|" );
					imp.say( imp.pRow(), 2, "Cód.cto.:" );
					imp.say( imp.pRow(), 10, rs.getString( "CodCto" ) );
					imp.say( imp.pRow(), 20, "Razão:" );
					imp.say( imp.pRow(), 27, rs.getString( "RazCto" ) );
					imp.say( imp.pRow(), 127, "Setor:" );
					imp.say( imp.pRow(), 133, rs.getString( "PessoaCto" ) );
					imp.say( imp.pRow(), 135, "|" );
					imp.say( imp.pRow() + 1, 0, imp.comprimido() );
					imp.say( imp.pRow(), 0, "|" );
					imp.say( imp.pRow(), 1, "Nome:" );
					imp.say( imp.pRow(), 7, rs.getString( "NomeCto" ) );
					imp.say( imp.pRow(), 60, "Contato:" );
					imp.say( imp.pRow(), 69, rs.getString( "ContCto" ) );
					imp.say( imp.pRow(), 135, "|" );
					imp.say( imp.pRow() + 1, 0, imp.comprimido() );
					imp.say( imp.pRow(), 0, "|" );
					imp.say( imp.pRow(), 1, "Endereço:" );
					imp.say( imp.pRow(), 11, rs.getString( "EndCto" ) );
					imp.say( imp.pRow(), 62, "N.:" );
					imp.say( imp.pRow(), 67, "" + rs.getInt( "NumCto" ) );
					imp.say( imp.pRow(), 76, "Compl.:" );
					imp.say( imp.pRow(), 85, rs.getString( "ComplCto" ) != null ? rs.getString( "ComplCto" ).trim() : "" );
					imp.say( imp.pRow(), 94, "Bairro:" );
					imp.say( imp.pRow(), 103, rs.getString( "BairCto" ) != null ? rs.getString( "BairCto" ).trim() : "" );
					imp.say( imp.pRow(), 135, "|" );
					imp.say( imp.pRow() + 1, 0, imp.comprimido() );
					imp.say( imp.pRow(), 0, "|Cidade:" );
					imp.say( imp.pRow(), 8, rs.getString( "CidCto" ) );
					imp.say( imp.pRow(), 88, "UF:" );
					imp.say( imp.pRow(), 93, rs.getString( "UfCto" ) );
					imp.say( imp.pRow(), 120, "CEP:" );
					imp.say( imp.pRow(), 126, rs.getString( "CepCto" ) != null ? Funcoes.setMascara( rs.getString( "CepCto" ), "#####-###" ) : "" );
					imp.say( imp.pRow(), 135, "|" );
					imp.say( imp.pRow() + 1, 0, imp.comprimido() );
					if ( ( rs.getString( "CnpjCto" ) ) != null && ( rs.getString( "InscCto" ) != null ) ) {
						imp.say( imp.pRow(), 0, "|CNPJ:" );
						imp.say( imp.pRow(), 7, Funcoes.setMascara( rs.getString( "CnpjCto" ), "##.###.###/####-##" ) );
						imp.say( imp.pRow(), 50, "IE:" );
						if ( !rs.getString( "InscCto" ).trim().toUpperCase().equals( "ISENTA" ) && rs.getString( "UFCto" ) != null ) {
							Funcoes.vIE( rs.getString( "InscCto" ), rs.getString( "UFCto" ) );
							imp.say( imp.pRow(), 55, Funcoes.sIEValida );
						}
						imp.say( imp.pRow(), 135, "|" );
					}
					else {
						imp.say( imp.pRow(), 0, "|CPF:" );
						imp.say( imp.pRow(), 6, Funcoes.setMascara( rs.getString( "CPFCto" ), "###.###.###-##" ) );
						imp.say( imp.pRow(), 50, "RG:" );
						imp.say( imp.pRow(), 55, rs.getString( "RgCto" ) );
					}
					imp.say( imp.pRow(), 80, "Tel:" );
					imp.say( imp.pRow(), 86, "(" + ( rs.getString( "DddCto" ) != null ? rs.getString( "DddCto" ) : "    " ) + ")" + ( rs.getString( "FoneCto" ) != null ? Funcoes.setMascara( rs.getString( "FoneCto" ), "####-####" ) : "" ) );
					imp.say( imp.pRow(), 120, "Fax:" );
					imp.say( imp.pRow(), 126, rs.getString( "FaxCto" ) != null ? Funcoes.setMascara( rs.getString( "FaxCto" ), "####-####" ) : "" );
					imp.say( imp.pRow(), 135, "|" );
					imp.say( imp.pRow() + 1, 0, imp.comprimido() );
					imp.say( imp.pRow(), 0, "|Contato:" );
					imp.say( imp.pRow(), 9, rs.getString( "ContCto" ) );
					imp.say( imp.pRow(), 70, "E-mail:" );
					imp.say( imp.pRow(), 79, Funcoes.copy( rs.getString( "EmailCto" ) ,54));
					imp.say( imp.pRow(), 135, "|" );
					if ( sObs.length() > 0 ) {
						imp.say( imp.pRow() + 1, 0, imp.comprimido() );
						imp.say( imp.pRow(), 0, "|Obs:" );
						imp.say( imp.pRow(), 6, rs.getString( "ObsCto" ) );
						imp.say( imp.pRow(), 135, "|" );
					}
					imp.say( imp.pRow() + 1, 0, imp.comprimido() );
					imp.say( imp.pRow(), 0, "|" + Funcoes.replicate( "-", 133 ) + "|" );

					And.atualiza( iContaReg );
					iContaReg++;
				}
				imp.eject();
				imp.fechaGravacao();

				con.commit();
				dl.dispose();
				And.dispose();
			} catch ( SQLException err ) {
				Funcoes.mensagemErro( this, "Erro consulta tabela de contatos!" + err.getMessage(), true, con, err );
			}
		}
		else if ( dl.getValores()[ 7 ].equals( "R" ) ) {
			sSQL = "SELECT CODCTO,NOMECTO,ENDCTO,CIDCTO,DDDCTO, FONECTO " + 
			   "FROM TKCONTATO" + sWhere + 
			   " ORDER BY " + dl.getValores()[ 0 ];

			try {
				ps = con.prepareStatement( "SELECT COUNT(*) FROM TKCONTATO" + sWhere );
				rs = ps.executeQuery();
				rs.next();
				And = new FAndamento( "Montando Relatório, Aguarde!", 0, rs.getInt( 1 ) - 1 );
				con.commit();

				ps = con.prepareStatement( sSQL );
				rs = ps.executeQuery();

				imp.montaCab();
				imp.setTitulo( "Relatório de Contatos" );
				imp.addSubTitulo( "Relatório de Contatos" );
				imp.limpaPags();

				while ( rs.next() ) {
					if ( imp.pRow() == 0 ) {
						imp.impCab( 136, true );
						imp.say( imp.pRow() + 0, 2, "|" + Funcoes.replicate( " ", 60 ) + "Filtrado por:" + Funcoes.replicate( " ", 60 ) + "|" );
						for ( int i = 0; i < vFiltros.size(); i++ ) {
							sTmp = vFiltros.elementAt( i );
							sTmp = "|" + Funcoes.replicate( " ", ( ( ( 135 - sTmp.length() ) / 2 ) - 1 ) ) + sTmp;
							sTmp += Funcoes.replicate( " ", 134 - sTmp.length() ) + "|";
							imp.say( imp.pRow() + 1, 0, imp.comprimido() );
							imp.say( imp.pRow(), 2, sTmp );
						}
						imp.say( imp.pRow() + 1, 0, imp.comprimido() );
						imp.say( imp.pRow(), 0, "|" + Funcoes.replicate( "-", 133 ) + "|" );
						imp.say( imp.pRow() + 1, 0, imp.comprimido() );
						imp.say( imp.pRow(), 0, "| Código" );
						imp.say( imp.pRow(), 10, "Nome:" );
						imp.say( imp.pRow(), 50, "Endereço:" );
						imp.say( imp.pRow(), 90, "Cidade:" );
						imp.say( imp.pRow(), 120, "Tel:" );
						imp.say( imp.pRow(), 135, "|" );
						imp.say( imp.pRow() + 1, 0, imp.comprimido() );
						imp.say( imp.pRow(), 0, "|" + Funcoes.replicate( "-", 133 ) + "|" );
					}
					imp.say( imp.pRow() + 1, 0, imp.comprimido() );
					imp.say( imp.pRow(), 0, "|" );
					imp.say( imp.pRow(), 2, rs.getString( "CodCto" ) );
					imp.say( imp.pRow(), 10, rs.getString( "NomeCto" ) != null ? rs.getString( "NomeCto" ).substring( 0, 39 ) : "" );
					imp.say( imp.pRow(), 50, rs.getString( "EndCto" ) != null ? rs.getString( "EndCto" ).substring( 0, 39 ) : "" );
					imp.say( imp.pRow(), 90, rs.getString( "CidCto" ) != null ? rs.getString( "CidCto" ).substring( 0, 29 ) : "" );
					imp.say( imp.pRow(), 120, rs.getString( "FoneCto" ) != null ? Funcoes.setMascara( rs.getString( "FoneCto" ), "####-####" ) : "" );
					imp.say( imp.pRow(), 135, "|" );
					if ( imp.pRow() >= linPag ) {
						imp.incPags();
						imp.eject();
					}
					And.atualiza( iContaReg );
					iContaReg++;
				}
				imp.say( imp.pRow() + 1, 0, imp.comprimido() );
				imp.say( imp.pRow(), 0, "+" + Funcoes.replicate( "-", 133 ) + "+" );
				imp.eject();
				imp.fechaGravacao();
				con.commit();

				dl.dispose();
				And.dispose();
			} catch ( SQLException err ) {
				Funcoes.mensagemErro( this, "Erro consulta tabela de contatos!" + err.getMessage(), true, con, err );
			}
		}
		if ( bVisualizar )
			imp.preview( this );
		else
			imp.print();
	}

	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == btPrevimp ) {
			imprimir( true );
			Vector<?> vVal = Funcoes.stringToVector( txaObs.getText() );
			int iTam = vVal.size();
			for ( int i = 0; i < iTam; i++ ) {
				System.out.println( i + " : " + vVal.elementAt( i ) );
			}
		}
		else if ( evt.getSource() == btImp ) {
			imprimir( false );
		}
		else if ( evt.getSource() == btExportCli ) {
			exportaCli();
		}
		else if ( evt.getSource() == btBuscaEnd ) {
			buscaEndereco();
		}

		super.actionPerformed( evt );
	}

	public void beforePost( PostEvent pevt ) {

		if ( lcCampos.getStatus() == ListaCampos.LCS_INSERT ) {
			if ( ! ( txtCnpjCont.getText().trim().length() < 1 ) && ! ( duploCNPJ() ) ) {
				pevt.cancela();
				Funcoes.mensagemInforma( this, "Este CNPJ ja está cadastrado!" );
				txtCnpjCont.requestFocus();
			}
			txtDataCont.setVlrDate( new Date() );
		}
		if ( ! ( txtInscCont.getText().trim().length() < 1 ) && ! ( txtInscCont.getText().trim().toUpperCase().compareTo( "ISENTA" ) == 0 ) && !Funcoes.vIE( txtInscCont.getText(), txtUFCont.getText() ) ) {
			pevt.cancela();
			Funcoes.mensagemInforma( this, "Inscrição Estadual Inválida ! ! !" );
			txtInscCont.requestFocus();
		}
		txtInscCont.setVlrString( Funcoes.sIEValida );
	}

	public void valorAlterado( RadioGroupEvent rgevt ) {

		if ( rgPessoa.getVlrString().compareTo( "J" ) == 0 ) {
			txtCnpjCont.setEnabled( true );
			txtInscCont.setEnabled( true );
			txtCpfCont.setEnabled( false );
			txtRgCont.setEnabled( false );
		}
		else if ( rgPessoa.getVlrString().compareTo( "F" ) == 0 ) {
			txtCnpjCont.setEnabled( false );
			txtInscCont.setEnabled( false );
			txtCpfCont.setEnabled( true );
			txtRgCont.setEnabled( true );
		}
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		lcAtiv.setConexao( cn );
		lcAtivFK.setConexao( cn );
		lcGrupFK.setConexao( cn );
		lcVend.setConexao( cn );
		lcSetor.setConexao( cn );
		lcUF.setConexao( cn );
		lcMunic.setConexao( cn );
		lcPais.setConexao( cn );
		lcTipoCli.setConexao( cn );
		lcOrigCont.setConexao( cn );
		lcGrupo.setConexao( cn );
	}

	public void stateChanged( ChangeEvent cevt ) {

		if ( cevt.getSource() == tpn ) {
			if ( tpn.getSelectedIndex() == 0 )
				txtCodCont.requestFocus();
			else if ( tpn.getSelectedIndex() == 4 )
				txaObs.requestFocus();
		}
	}
}
