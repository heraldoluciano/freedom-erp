/**
 * @version 23/12/2008 <BR>
 * @author Setpoint Informática Ltda.
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)FEmpresa.java <BR>
 * 
 * Este arquivo é parte do sistema Freedom-ERP, o Freedom-ERP é um software livre; você pode redistribui-lo e/ou <BR>
 * modifica-lo dentro dos termos da Licença Pública Geral GNU como publicada pela Fundação do Software Livre (FSF); <BR>
 * na versão 2 da Licença, ou (na sua opnião) qualquer versão. <BR>
 * Este programa é distribuido na esperança que possa ser  util, mas SEM NENHUMA GARANTIA; <BR>
 * sem uma garantia implicita de ADEQUAÇÂO a qualquer MERCADO ou APLICAÇÃO EM PARTICULAR. <BR>
 * Veja a Licença Pública Geral GNU para maiores detalhes. <BR>
 * Você deve ter recebido uma cópia da Licença Pública Geral GNU junto com este programa, se não, <BR>
 * de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 * Tela de cadastro de empresas e filiais.
 * 
 */

package org.freedom.modulos.std.view.frame.detail;

import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.JCheckBoxPad;
import org.freedom.library.swing.JPanelPad;
import org.freedom.library.swing.JTextFieldFK;
import org.freedom.library.swing.JTextFieldPad;
import org.freedom.library.swing.PainelImagem;
import org.freedom.library.swing.frame.FDetalhe;


public class FEmpresa extends FDetalhe{

	private static final long serialVersionUID = 1L;
	
	private JPanelPad pinCab = new JPanelPad();
	
	private JPanelPad pinDet = new JPanelPad();
	
	private JTextFieldPad txtCodEmp = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );

	private JTextFieldPad txtRazEmp = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtNomeEmp = new JTextFieldPad( JTextFieldPad.TP_STRING, 40, 0 );
	
	private JTextFieldPad txtEndEmp = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtNumEmp = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtCnpjEmp = new JTextFieldPad( JTextFieldPad.TP_STRING, 14, 0 );

	private JTextFieldPad txtInscEmp = new JTextFieldPad( JTextFieldPad.TP_STRING, 15, 0 );

	private JTextFieldPad txtBairEmp = new JTextFieldPad( JTextFieldPad.TP_STRING, 30, 0 );

	private JTextFieldPad txtComplEmp = new JTextFieldPad( JTextFieldPad.TP_STRING, 20, 0 );

	private JTextFieldPad txtCidEmp = new JTextFieldPad( JTextFieldPad.TP_STRING, 30, 0 );

	private JTextFieldPad txtCepEmp = new JTextFieldPad( JTextFieldPad.TP_STRING, 8, 0 );

	private JTextFieldPad txtFoneEmp = new JTextFieldPad( JTextFieldPad.TP_STRING, 12, 0 );

	private JTextFieldPad txtDDDFilial = new JTextFieldPad( JTextFieldPad.TP_STRING, 4, 0 );
	
	private JTextFieldPad txtFaxEmp = new JTextFieldPad( JTextFieldPad.TP_STRING, 8, 0 );

	private JTextFieldPad txtUFEmp = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );

	private JTextFieldPad txtEmailEmp = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtWWWEmp = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtPercIssFilial = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 5, 2 );

	private JTextFieldPad txtCodEANEmp = new JTextFieldPad( JTextFieldPad.TP_STRING, 10, 0 );

	private JTextFieldPad txtCodPaisEmp = new JTextFieldPad( JTextFieldPad.TP_STRING, 3, 0 );

	private JTextFieldPad txtNomeContEmp = new JTextFieldPad( JTextFieldPad.TP_STRING, 40, 0 );
	
	private JTextFieldPad txtCodFilial = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );
	
	private JTextFieldPad txtRazFilial = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtNomeFilial = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );
	
	private JTextFieldPad txtCodDistFilial = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldPad txtPercPIS = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 5, 2 );

	private JTextFieldPad txtPercCofins = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 5, 2 );

	private JTextFieldPad txtPercIR = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 5, 2 );

	private JTextFieldPad txtPercCSocial = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 5, 2 );
	
	private JTextFieldPad txtPercSimples = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 5, 2 );
	
	private JCheckBoxPad cbMultiAlmox = new JCheckBoxPad( "Sim", "S", "N" );
	
	private JCheckBoxPad cbMatriz = new JCheckBoxPad( "Matriz", "S", "N" );

	private JCheckBoxPad cbSimples = new JCheckBoxPad( "Simples", "S", "N" );
	
	private JCheckBoxPad cbContribIPI = new JCheckBoxPad( "Sim", "S", "N" );
	
	private PainelImagem imFotoEmp = new PainelImagem( 65000 );
	
	private ListaCampos lcUF = new ListaCampos( this );

	private ListaCampos lcMunic = new ListaCampos( this );
	
	private ListaCampos lcPais = new ListaCampos( this );
	
	private JTextFieldPad txtCodPais = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescPais = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	private JTextFieldPad txtSiglaUF = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );
	
	private JTextFieldFK txtNomeUF = new JTextFieldFK( JTextFieldPad.TP_STRING, 80, 0 ); 
	
	private JTextFieldPad txtCodMun = new JTextFieldPad( JTextFieldPad.TP_STRING, 8, 0 );

	private JTextFieldFK txtDescMun = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	private JTextFieldPad txtInscMun = new JTextFieldPad( JTextFieldPad.TP_STRING, 15, 0 );
	
	private JTextFieldPad txtCNAE = new JTextFieldPad( JTextFieldPad.TP_STRING, 7, 0 );

	public FEmpresa(){
		
		setTitulo( "Cadastro de Empresa" );
		setAtribos( 50, 50, 530, 730 );		
	
		lcCampos.setUsaME( false );
		
		montaTela();
		montaListaCampos();		
		
	}
	
	private void montaTela(){
		
		setAltCab( 210 );
		setAltDet( 375 );
		
		pinCab = new JPanelPad( 440, 60 );
		setPainel( pinCab, pnCliCab );
		
		lcDet.setUsaME( false );
		
		adicCampo( txtCodEmp, 7, 20, 60, 20, "CodEmp", "Cód.Emp", ListaCampos.DB_PK, true );
		adicCampo( txtRazEmp, 70, 20, 260, 20, "RazEmp", "Razão social da empresa", ListaCampos.DB_SI, true );
		adicDB( imFotoEmp, 340, 20, 150, 100, "FotoEmp", "Foto:(64Kb 18x12)", true );
		adicCampo( txtNomeEmp, 7, 60, 323, 20, "NomeEmp", "Nome fantasia", ListaCampos.DB_SI, false );	
		adicCampo( txtNomeContEmp, 7, 100, 323, 20, "NomeContEmp", "Contato", ListaCampos.DB_SI, false );
		adicCampo( txtCodEANEmp, 7, 140, 95, 20, "CodEANEmp", "Cod. EAN", ListaCampos.DB_SI, false );

		adicDB( cbMultiAlmox, 105, 140, 300, 20, "MultiAlmoxEmp", "Intercambio de almox. entre filiais?", true );
		
		txtCnpjEmp.setMascara( JTextFieldPad.MC_CNPJ );
		txtCepEmp.setMascara( JTextFieldPad.MC_CEP );
		txtFoneEmp.setMascara( JTextFieldPad.MC_FONE );
		txtFaxEmp.setMascara( JTextFieldPad.MC_FONE );
		setListaCampos( true, "EMPRESA", "SG" );
					
		pinDet = new JPanelPad( 600, 80 );
		setPainel( pinDet, pnDet );
		setListaCampos( lcDet );
		setNavegador( navRod );	
		adicCampo( txtCodFilial, 7, 20, 60, 20, "CodFilial", "Cód.Filial", ListaCampos.DB_PK, true );
		adicCampo( txtRazFilial, 70, 20, 180, 20, "RazFilial", "Razão social da filial", ListaCampos.DB_SI, true );
		adicCampo( txtNomeFilial, 253, 20, 229, 20, "NomeFilial", "Nome fantasia", ListaCampos.DB_SI, false );		
		adicCampo( txtCnpjEmp, 7, 60, 120, 20, "CnpjFilial", "CNPJ", ListaCampos.DB_SI, true );
		adicCampo( txtInscEmp, 130, 60, 120, 20, "InscFilial", "Inscrição Estadual", ListaCampos.DB_SI, true );
		adicCampo( txtInscMun, 253, 60, 120, 20, "InscMunFilial", "Inscrição Municipal", ListaCampos.DB_SI, false );
		adicCampo( txtCNAE, 376, 60, 105, 20, "CNAEFILIAL", "CNAE", ListaCampos.DB_SI, false );
				
		adicCampo( txtEndEmp, 7, 100, 403, 20, "EndFilial", "Endereço", ListaCampos.DB_SI, false );
		adicCampo( txtNumEmp, 413, 100, 68, 20, "NumFilial", "Num.", ListaCampos.DB_SI, false );
		
		adicCampo( txtComplEmp, 7, 140, 160, 20, "ComplFilial", "Complemento", ListaCampos.DB_SI, false );
		adicCampo( txtBairEmp, 170, 140, 180, 20, "BairFilial", "Bairro", ListaCampos.DB_SI, false );
		adicCampo( txtCepEmp, 353, 140, 128, 20, "CepFilial", "Cep", ListaCampos.DB_SI, false );	
		adicCampo( txtDDDFilial, 7, 180, 40, 20, "DDDFilial", "DDD", ListaCampos.DB_SI, false );
		adicCampo( txtFoneEmp, 50, 180, 77, 20, "FoneFilial", "Telefone", ListaCampos.DB_SI, false );
		adicCampo( txtFaxEmp, 130, 180, 120, 20, "FaxFilial", "Fax", ListaCampos.DB_SI, false );
		adicCampo( txtEmailEmp, 253, 180, 227, 20, "EmailFilial", "Email", ListaCampos.DB_SI, false );
		adicCampo( txtWWWEmp, 7, 220, 183, 20, "WWWFilial", "Página da WEB", ListaCampos.DB_SI, false );
		
		adicCampo( txtCodDistFilial, 193, 220, 67, 20, "CodDistFilial", "C.dist.fil", ListaCampos.DB_SI, false );
		adicDB( cbMatriz, 265, 220, 72, 20, "MzFilial", "Sede", false );
		adicDB( cbSimples, 336, 220, 75, 20, "SimplesFilial", "Fiscal", false );
		adicDB( cbContribIPI, 416, 220, 80, 20, "ContribIPIFilial", "Contrib. IPI", false );
		
		
		adicCampo( txtPercPIS, 7, 260, 75, 20, "PercPISFilial", "% PIS", ListaCampos.DB_SI, false );
		adicCampo( txtPercCofins, 85, 260, 75, 20, "PercCofinsFilial", "% COFINS", ListaCampos.DB_SI, false );
		adicCampo( txtPercIR, 163, 260, 75, 20, "PercIRFilial", "% IR", ListaCampos.DB_SI, false );
		adicCampo( txtPercCSocial, 241, 260, 75, 20, "PercCSocialFilial", "% Cont.social", ListaCampos.DB_SI, false );
		adicCampo( txtPercIssFilial, 319, 260, 75, 20, "PercISSFilial", "% ISS", ListaCampos.DB_SI, false );
		adicCampo( txtPercSimples, 397, 260, 83, 20, "PercSimplesFilial", "% Simples", ListaCampos.DB_SI, false );
		
		
		
		adicCampo( txtCodPais, 7, 300, 50, 20, "CodPais", "Cd.país", ListaCampos.DB_FK, true );
		adicDescFK( txtDescPais, 60, 300, 177, 20, "DescPais", "Nome do país" );
		
		adicCampo( txtSiglaUF, 240, 300, 50, 20, "SiglaUf", "UF", ListaCampos.DB_FK, true );		
		adicDescFK( txtNomeUF, 293, 300, 186, 20, "NomeUF", "Nome UF" );

		
		adicCampo( txtCodMun, 7, 340, 50, 20, "CodMunic", "Cd.mun.", ListaCampos.DB_FK, false );
		adicDescFK( txtDescMun, 60, 340, 177, 20, "NomeMunic", "Nome do municipio" );			
		
		setListaCampos( true, "FILIAL", "SG" );
		lcDet.setOrdem( "RazFilial" );
		montaTab();
		lcDet.setQueryInsert( false );
		lcDet.setQueryCommit( false );
		
		tab.setTamColuna( 120, 1 );
		tab.setTamColuna( 80, 0 );
		tab.setTamColuna( 220, 1 );
		tab.setTamColuna( 220, 2 );
		tab.setTamColuna( 80, 3 );
		tab.setTamColuna( 140, 4 );
		tab.setTamColuna( 90, 5 );
		tab.setTamColuna( 50, 6 );
		tab.setTamColuna( 70, 7 );
		tab.setTamColuna( 70, 8 );
		tab.setTamColuna( 80, 9 );
		tab.setTamColuna( 50, 10 );
		tab.setTamColuna( 40, 11 );
		tab.setTamColuna( 80, 12 );
		tab.setTamColuna( 50, 13 );
		tab.setTamColuna( 70, 14 );
		tab.setTamColuna( 100, 15 );
		tab.setTamColuna( 80, 16 );
		tab.setTamColuna( 60, 17 );
		tab.setTamColuna( 40, 18 );
		tab.setTamColuna( 80, 19 );
		
	}
	
	private void montaListaCampos(){
		
		/***************
		 *      UF     *
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
		 *  MUNICIPIO  *
		 **************/
		
		lcMunic.setUsaME( false );		
		lcMunic.add( new GuardaCampo( txtCodMun, "CodMunic", "Cód.Muni", ListaCampos.DB_PK, true ) );
		lcMunic.add( new GuardaCampo( txtDescMun, "NomeMunic", "Nome Muni.", ListaCampos.DB_SI, false ) );
		lcMunic.setDinWhereAdic( "SIGLAUF = #S", txtSiglaUF );
		lcMunic.montaSql( false, "MUNICIPIO", "SG" );
		lcMunic.setQueryCommit( false );
		lcMunic.setReadOnly( true );
		txtCodMun.setTabelaExterna( lcMunic );	
		
		/***************
		 *    PAÍS     *
		 **************/
		
		lcPais.setUsaME( false );
		lcPais.add( new GuardaCampo( txtCodPais, "CodPais", "Cod.país.", ListaCampos.DB_PK, false ) );
		lcPais.add( new GuardaCampo( txtDescPais, "NomePais", "Nome", ListaCampos.DB_SI, false ) );
		lcPais.montaSql( false, "PAIS", "SG" );
		lcPais.setQueryCommit( false );
		lcPais.setReadOnly( true );
		txtCodPais.setTabelaExterna( lcPais );
		
	}
	
	public void setConexao( DbConnection cn ) {
		super.setConexao( cn );
		lcPais.setConexao( cn );
		lcMunic.setConexao( cn );
		lcUF.setConexao( cn );
	}
}
