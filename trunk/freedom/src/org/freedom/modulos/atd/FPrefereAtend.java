/**
 * @version 25/07/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.atd <BR>
 * Classe: @(#)FPrefereAtend.java <BR>
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
 * 
 */

package org.freedom.modulos.atd;
import java.sql.Connection;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Painel;
import org.freedom.telas.FTabDados;

public class FPrefereAtend extends FTabDados {
	private Painel pinGeral = null;
	private Painel pinTipo = null;
	private Painel pinSetor = null;
	private Painel pinConv = null;
	private Painel pinOrc = null;

	private JTextFieldPad txtClassMedida = new JTextFieldPad(JTextFieldPad.TP_STRING, 20 , 0);
	private JTextFieldPad txtCodTpAtend = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	private JTextFieldFK txtDescTpAtend = new JTextFieldFK(JTextFieldPad.TP_STRING, 40, 0);
	private ListaCampos lcTpAtend = new ListaCampos(this,"TO");
	private JTextFieldPad txtCodTpAtend2 = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	private JTextFieldFK txtDescTpAtend2 = new JTextFieldFK(JTextFieldPad.TP_STRING, 40, 0);
	private ListaCampos lcTpAtend2 = new ListaCampos(this,"T2");
	private JTextFieldPad txtCodTpAtend3 = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	private JTextFieldFK txtDescTpAtend3 = new JTextFieldFK(JTextFieldPad.TP_STRING, 40, 0);
	private ListaCampos lcTpAtend3 = new ListaCampos(this,"T3");
	private JTextFieldPad txtCodSetor = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	private JTextFieldFK txtDescSetor = new JTextFieldFK(JTextFieldPad.TP_STRING, 40, 0);
	private ListaCampos lcSetor = new ListaCampos(this,"TO");
	private JTextFieldPad txtCodSetor2 = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	private JTextFieldFK txtDescSetor2 = new JTextFieldFK(JTextFieldPad.TP_STRING, 40, 0);
	private ListaCampos lcSetor2 = new ListaCampos(this,"T2");
	private JTextFieldPad txtCodAtend = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8 , 0);
	private JTextFieldFK txtNomeAtend = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0 );
	private ListaCampos lcAtend = new ListaCampos(this,"AE");
	private JTextFieldPad txtCodTipoCli = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	private JTextFieldFK  txtDescTipoCli = new JTextFieldFK(JTextFieldPad.TP_STRING, 40, 0);
	private ListaCampos lcTipoCli = new ListaCampos(this,"TI");
	private JTextFieldPad txtCodClas = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	private JTextFieldFK  txtDescClas = new JTextFieldFK(JTextFieldPad.TP_STRING, 40, 0);
	private ListaCampos lcClas = new ListaCampos(this,"CI");
	private JTextFieldPad txtCodTBA = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	private JTextFieldFK txtDescTBA = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);
	private ListaCampos lcTabAC = new ListaCampos(this,"TA");
	private JTextFieldPad txtCodITTBA = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	private JTextFieldFK txtDescITTBA = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);
	private ListaCampos lcTabITAC = new ListaCampos(this,"TA");
	private JTextFieldPad txtCodTBV = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	private JTextFieldFK txtDescTBV = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);
	private ListaCampos lcTabAV = new ListaCampos(this,"TA");
	private JTextFieldPad txtCodITTBV = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	private JTextFieldFK txtDescITTBV = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);
	private JTextFieldPad txtCodVend = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	private JTextFieldFK txtNomeVend = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);
	
	private ListaCampos lcTabITAV = new ListaCampos(this,"TA");
	private ListaCampos lcVend = new ListaCampos(this,"VD");
	
	public FPrefereAtend() {
		setTitulo("Preferências do Atendimento");
		setAtribos(50, 50, 355, 375);
		
//Tipo:
          
        lcAtend.add(new GuardaCampo(txtCodAtend, "CodAtend","Cód.atend.", ListaCampos.DB_PK ,true));
        lcAtend.add(new GuardaCampo(txtNomeAtend, "NomeAtend","Nome atendente" ,ListaCampos.DB_SI, false));
        lcAtend.montaSql(false,"ATENDENTE", "AT");
        lcAtend.setQueryCommit(false);
        lcAtend.setReadOnly(true);
        txtCodAtend.setTabelaExterna(lcAtend);
        
        lcTipoCli.add(new GuardaCampo(txtCodTipoCli, "CodTipoCli","Cód.tp.cli.", ListaCampos.DB_PK, false));
        lcTipoCli.add(new GuardaCampo(txtDescTipoCli, "DescTipoCli","Descrição do tipo de cliente", ListaCampos.DB_SI, false));
        lcTipoCli.montaSql(false, "TIPOCLI", "VD");
        lcTipoCli.setQueryCommit(false);
        lcTipoCli.setReadOnly(true);
        txtCodTipoCli.setTabelaExterna(lcTipoCli);

		lcClas.add(new GuardaCampo(txtCodClas, "CodClasCli","Cód.c.cli.", ListaCampos.DB_PK, false));
		lcClas.add(new GuardaCampo(txtDescClas, "DescClasCli","Descrição da calssificação do cliente", ListaCampos.DB_SI, false));
		lcClas.montaSql(false, "CLASCLI", "VD");
		lcClas.setQueryCommit(false);
		lcClas.setReadOnly(true);
		txtCodClas.setTabelaExterna(lcClas);
		
		lcTabAC.add(new GuardaCampo(txtCodTBA, "CodTB","Cód.tab.", ListaCampos.DB_PK, false));
		lcTabAC.add(new GuardaCampo(txtDescTBA, "DescTB","Descrição da tabela padrão para aceite", ListaCampos.DB_SI, false));
		lcTabAC.montaSql(false, "TABELA", "SG");
		lcTabAC.setQueryCommit(false);
		lcTabAC.setReadOnly(true);
		txtCodTBA.setTabelaExterna(lcTabAC);

		lcTabITAC.add(new GuardaCampo(txtCodITTBA, "CodITTB","Cód.it.tab.", ListaCampos.DB_PK, false));
		lcTabITAC.add(new GuardaCampo(txtDescITTBA, "DescITTB","Descrição da situação para aceite", ListaCampos.DB_SI, false));
		lcTabITAC.setDinWhereAdic("CODTB = #N",txtCodTBA);
		lcTabITAC.montaSql(false, "ITTABELA", "SG");
		lcTabITAC.setQueryCommit(false);
		lcTabITAC.setReadOnly(true);
		txtCodITTBA.setTabelaExterna(lcTabITAC);

		lcTabAV.add(new GuardaCampo(txtCodTBV, "CodTB","Cód.tab.", ListaCampos.DB_PK , false));
		lcTabAV.add(new GuardaCampo(txtDescTBV, "DescTB","Descrição da tabela padrão para aceite", ListaCampos.DB_SI, false));
		lcTabAV.montaSql(false, "TABELA", "SG");
		lcTabAV.setQueryCommit(false);
		lcTabAV.setReadOnly(true);
		txtCodTBV.setTabelaExterna(lcTabAV);

		lcTabITAV.add(new GuardaCampo(txtCodITTBV, "CodITTB","Cód.it.tab.",ListaCampos.DB_PK, false));
		lcTabITAV.add(new GuardaCampo(txtDescITTBV, "DescITTB","Descrição da situação para aceite", ListaCampos.DB_SI, false));
		lcTabITAV.setDinWhereAdic("CODTB = #N",txtCodTBV);
		lcTabITAV.montaSql(false, "ITTABELA", "SG");
		lcTabITAV.setQueryCommit(false);
		lcTabITAV.setReadOnly(true);
		txtCodITTBV.setTabelaExterna(lcTabITAV);
        
		lcVend.add(new GuardaCampo(txtCodVend, "CodVend","Cód.repr.", ListaCampos.DB_PK, false));
		lcVend.add(new GuardaCampo(txtNomeVend, "NomeVend","Nome do represetante", ListaCampos.DB_SI, false));
		lcVend.montaSql(false, "VENDEDOR", "VD");
		lcVend.setQueryCommit(false);
		lcVend.setReadOnly(true);
		txtCodVend.setTabelaExterna(lcVend);
           
		lcTpAtend.add(new GuardaCampo(txtCodTpAtend, "CodTpAtendo","Cód.tp.atend.", ListaCampos.DB_PK, true));
		lcTpAtend.add(new GuardaCampo(txtDescTpAtend, "DescTpAtendo","Descrição do tipo de atendimento", ListaCampos.DB_SI ,false));
		lcTpAtend.montaSql(false, "TIPOATENDO", "AT");
		lcTpAtend.setQueryCommit(false);
		lcTpAtend.setReadOnly(true);
		txtCodTpAtend.setTabelaExterna(lcTpAtend);

		lcTpAtend2.add(new GuardaCampo(txtCodTpAtend2, "CodTpAtendo","Cód.tp.atend.", ListaCampos.DB_PK, true));
		lcTpAtend2.add(new GuardaCampo(txtDescTpAtend2, "DescTpAtendo","Descrição do tipo de atendimento", ListaCampos.DB_SI ,false));
		lcTpAtend2.montaSql(false, "TIPOATENDO", "AT");
		lcTpAtend2.setQueryCommit(false);
		lcTpAtend2.setReadOnly(true);
		txtCodTpAtend2.setTabelaExterna(lcTpAtend2);

		lcTpAtend3.add(new GuardaCampo(txtCodTpAtend3, "CodTpAtendo","Códtp.atend.",ListaCampos.DB_PK, true));
		lcTpAtend3.add(new GuardaCampo(txtDescTpAtend3, "DescTpAtendo","Descrição do tipo de atendimento", ListaCampos.DB_SI, false));
		lcTpAtend3.montaSql(false, "TIPOATENDO", "AT");
		lcTpAtend3.setQueryCommit(false);
		lcTpAtend3.setReadOnly(true);
		txtCodTpAtend3.setTabelaExterna(lcTpAtend3);
		
		pinGeral = new Painel(330,350);
		setPainel(pinGeral);
		adicTab("Geral", pinGeral);
		adicCampo(txtCodAtend,7,25,80,20,"CodAtend","Cód.atend.",JTextFieldPad.TP_INTEGER,5,0,false,true,txtNomeAtend,true);
		adicDescFK(txtNomeAtend,90,25,230,20,"NameAtend","Nome do atendente responsável.",JTextFieldPad.TP_STRING,50,0);
		adicCampo(txtClassMedida,7,65,200,20,"ClassMedida","Classe p/ ficha de medida",JTextFieldPad.TP_STRING,20,0,false,false,null,false);
		
		pinTipo = new Painel(330, 350);
		setPainel(pinTipo);
		adicTab("Tipos de atendimento", pinTipo);
		adicCampo(txtCodTpAtend,7,25,80,20,"CodTpAtendo","Cód.tp.atend.",JTextFieldPad.TP_INTEGER,5,0,false,true,txtDescTpAtend,true);
		adicDescFK(txtDescTpAtend,90,25,230,20,"DescTpAtendo","Descrição do tipo de levantamento.",JTextFieldPad.TP_STRING,50,0);
		adicCampo(txtCodTpAtend2,7,65,80,20,"CodTpAtendo2","Cód.tp.atend.",JTextFieldPad.TP_INTEGER,5,0,false,true,txtDescTpAtend2,true);
		adicDescFK(txtDescTpAtend2,90,65,230,20,"DescTpAtendo","Descrição do tipo de cadastro.",JTextFieldPad.TP_STRING,50,0);
		adicCampo(txtCodTpAtend3,7,105,80,20,"CodTpAtendo3","Cód.tp.atend.",JTextFieldPad.TP_INTEGER,5,0,false,true,txtDescTpAtend3,true);
		adicDescFK(txtDescTpAtend3,90,105,230,20,"DescTpAtendo","Descrição do tipo de orçamento.",JTextFieldPad.TP_STRING,50,0);
		
		txtCodTpAtend2.setNomeCampo("CodTpAtendo");
		txtCodTpAtend3.setNomeCampo("CodTpAtendo");

//Setor:
		
		lcSetor.add(new GuardaCampo(txtCodSetor, "CodSetAt","Cód.setor", ListaCampos.DB_PK, true));
		lcSetor.add(new GuardaCampo(txtDescSetor, "DescSetAt","Descrição do setor", ListaCampos.DB_SI, false));
		lcSetor.montaSql(false, "SETOR", "AT");
		lcSetor.setQueryCommit(false);
		lcSetor.setReadOnly(true);
		txtCodSetor.setTabelaExterna(lcSetor);

		lcSetor2.add(new GuardaCampo(txtCodSetor2, "CodSetAt","Cód.setor", ListaCampos.DB_PK, true));
		lcSetor2.add(new GuardaCampo(txtDescSetor2, "DescSetAt","Descrição do setor", ListaCampos.DB_SI, false));
		lcSetor2.montaSql(false, "SETOR", "AT");
		lcSetor2.setQueryCommit(false);
		lcSetor2.setReadOnly(true);
		txtCodSetor2.setTabelaExterna(lcSetor2);

		pinSetor = new Painel(330, 350);
		setPainel(pinSetor);
		adicTab("Setor de atendimento", pinSetor);
		adicCampo(txtCodSetor,7,25,80,20,"CodSetAt","Cód.setor",JTextFieldPad.TP_INTEGER,5,0,false,true,txtDescSetor,true);
		adicDescFK(txtDescSetor,90,25,230,20,"DescSetAt","Setor de cadastro.",JTextFieldPad.TP_STRING,50,0);
		adicCampo(txtCodSetor2,7,65,80,20,"CodSetAt2","Cód.setor",JTextFieldPad.TP_INTEGER,5,0,false,true,txtDescSetor2,true);
		adicDescFK(txtDescSetor2,90,65,230,20,"DescSetAt","Setor de orçamento.",JTextFieldPad.TP_STRING,50,0);
		
		pinConv = new Painel(330, 350);
		setPainel(pinConv);
		adicTab("Conveniado", pinConv);
		adicCampo(txtCodTipoCli,7,25,80,20,"CodTipoCli","Cód.tp.cli.",JTextFieldPad.TP_INTEGER,8,0,false,true,txtDescTipoCli,false);
		adicDescFK(txtDescTipoCli,90,25,230,20,"DescTipoCli","Descrição do tipo de cliente.",JTextFieldPad.TP_STRING,40,0);
		adicCampo(txtCodClas,7,65,80,20,"CodClasCli","Cód.c.cli.",JTextFieldPad.TP_INTEGER,8,0,false,true,txtDescClas,false);
		adicDescFK(txtDescClas,90,65,230,20,"DescClasCli","Descrição da classificação do cliente.",JTextFieldPad.TP_STRING,40,0);
		
		pinOrc = new Painel(330, 350);
		setPainel(pinOrc);
		adicTab("Orçamento", pinOrc);
		adicCampo(txtCodTBA,7,25,80,20,"CodTBA","Cód.tab.",JTextFieldPad.TP_INTEGER,8,0,false,true,txtDescTBA,false);
		adicDescFK(txtDescTBA,90,25,230,20,"DescTB","Descrição da tabela padrão para aceite.",JTextFieldPad.TP_STRING,50,0);
		adicCampo(txtCodITTBA,7,65,80,20,"CodITTBA","Cód.tab.",JTextFieldPad.TP_INTEGER,8,0,false,true,txtDescITTBA,false);
		adicDescFK(txtDescITTBA,90,65,230,20,"DescITTB","Descrição da situação para aceite.",JTextFieldPad.TP_STRING,50,0);
		adicCampo(txtCodTBV,7,105,80,20,"CodTBV","Cód.tab.",JTextFieldPad.TP_INTEGER,8,0,false,true,txtDescTBV,false);
		adicDescFK(txtDescTBV,90,105,230,20,"DescTB","Descrição da tabela padrão para aceite.",JTextFieldPad.TP_STRING,50,0);
		adicCampo(txtCodITTBV,7,150,80,20,"CodITTBV","Cód.tab.",JTextFieldPad.TP_INTEGER,8,0,false,true,txtDescITTBV,false);
		adicDescFK(txtDescITTBV,90,150,230,20,"DescITTB","Descrição da situação para aceite.",JTextFieldPad.TP_STRING,50,0);
		adicCampo(txtCodVend,7,195,80,20,"CodVend","Cód.repr.",JTextFieldPad.TP_INTEGER,8,0,false,true,txtNomeVend,true);
		adicDescFK(txtNomeVend,90,195,230,20,"NomeVend","Nome do representante padrão.",JTextFieldPad.TP_STRING,50,0);

		setListaCampos(false, "PREFERE2", "SG");
		
		txtCodSetor2.setNomeCampo("CodSetAt");
		txtCodTBA.setNomeCampo("CodTB");
		txtCodTBV.setNomeCampo("CodTB");
		txtCodITTBA.setNomeCampo("CodITTB");
		txtCodITTBV.setNomeCampo("CodITTB");
		txtCodVend.setNomeCampo("CodVend");

		nav.setAtivo(0,false);
		nav.setAtivo(1,false);
	}
	public void execShow(Connection cn) {
		lcTpAtend.setConexao(cn);
		lcTpAtend2.setConexao(cn);
		lcTpAtend3.setConexao(cn);
		lcSetor.setConexao(cn);
		lcSetor2.setConexao(cn);
		lcAtend.setConexao(cn);
		lcTipoCli.setConexao(cn);
		lcClas.setConexao(cn);
		lcTabAC.setConexao(cn);
		lcTabITAC.setConexao(cn);
		lcTabAV.setConexao(cn);
		lcTabITAV.setConexao(cn);
		lcVend.setConexao(cn);
		super.execShow(cn);
		lcCampos.carregaDados();
	}
}