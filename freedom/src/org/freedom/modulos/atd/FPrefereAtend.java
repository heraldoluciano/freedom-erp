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
	private JTextFieldPad txtClassMedida = new JTextFieldPad();
	private JTextFieldPad txtCodTpAtend = new JTextFieldPad();
	private JTextFieldFK txtDescTpAtend = new JTextFieldFK();
	private ListaCampos lcTpAtend = new ListaCampos(this,"TO");
	private JTextFieldPad txtCodTpAtend2 = new JTextFieldPad();
	private JTextFieldFK txtDescTpAtend2 = new JTextFieldFK();
	private ListaCampos lcTpAtend2 = new ListaCampos(this,"T2");
	private JTextFieldPad txtCodTpAtend3 = new JTextFieldPad();
	private JTextFieldFK txtDescTpAtend3 = new JTextFieldFK();
	private ListaCampos lcTpAtend3 = new ListaCampos(this,"T3");
	private JTextFieldPad txtCodSetor = new JTextFieldPad();
	private JTextFieldFK txtDescSetor = new JTextFieldFK();
	private ListaCampos lcSetor = new ListaCampos(this,"TO");
	private JTextFieldPad txtCodSetor2 = new JTextFieldPad();
	private JTextFieldFK txtDescSetor2 = new JTextFieldFK();
	private ListaCampos lcSetor2 = new ListaCampos(this,"T2");
	private JTextFieldPad txtCodAtend = new JTextFieldPad();
	private JTextFieldFK txtNomeAtend = new JTextFieldFK();
	private ListaCampos lcAtend = new ListaCampos(this,"AE");
	private JTextFieldPad txtCodTipoCli = new JTextFieldPad(8);
	private JTextFieldFK  txtDescTipoCli = new JTextFieldFK();
	private ListaCampos lcTipoCli = new ListaCampos(this,"TI");
	private JTextFieldPad txtCodClas = new JTextFieldPad(8);
	private JTextFieldFK  txtDescClas = new JTextFieldFK();
	private ListaCampos lcClas = new ListaCampos(this,"CI");
	private JTextFieldPad txtCodTBA = new JTextFieldPad(8);
	private JTextFieldFK txtDescTBA = new JTextFieldFK();
	private ListaCampos lcTabAC = new ListaCampos(this,"TA");
	private JTextFieldPad txtCodITTBA = new JTextFieldPad(8);
	private JTextFieldFK txtDescITTBA = new JTextFieldFK();
	private ListaCampos lcTabITAC = new ListaCampos(this,"TA");
	private JTextFieldPad txtCodTBV = new JTextFieldPad(8);
	private JTextFieldFK txtDescTBV = new JTextFieldFK();
	private ListaCampos lcTabAV = new ListaCampos(this,"TA");
	private JTextFieldPad txtCodITTBV = new JTextFieldPad(8);
	private JTextFieldFK txtDescITTBV = new JTextFieldFK();
	private JTextFieldPad txtCodVend = new JTextFieldPad(8);
	private JTextFieldFK txtNomeVend = new JTextFieldFK();
	
	private ListaCampos lcTabITAV = new ListaCampos(this,"TA");
	private ListaCampos lcVend = new ListaCampos(this,"VD");
	
	public FPrefereAtend() {
		setTitulo("Preferências do Atendimento");
		setAtribos(50, 50, 355, 375);
		
//Tipo:
        txtClassMedida.setTipo(JTextFieldPad.TP_STRING, 20 , 0);
          
        txtCodAtend.setTipo(JTextFieldPad.TP_INTEGER, 8 , 0);
        txtNomeAtend.setTipo(JTextFieldPad.TP_STRING, 50, 0 );
        lcAtend.add(new GuardaCampo(txtCodAtend,7,100,80,20,"CodAtend","Cód.atend.",true,false,null,JTextFieldPad.TP_INTEGER,true),"txtCodAtend");
        lcAtend.add(new GuardaCampo(txtNomeAtend,90,100,207,20,"NomeAtend","Nome atendente",false,false,null,JTextFieldPad.TP_STRING,false),"txtNomeAtend");
        lcAtend.montaSql(false,"ATENDENTE", "AT");
        lcAtend.setQueryCommit(false);
        lcAtend.setReadOnly(true);
        txtCodAtend.setTabelaExterna(lcAtend);
        
        txtCodTipoCli.setTipo(JTextFieldPad.TP_INTEGER, 8, 0);
        txtDescTipoCli.setTipo(JTextFieldPad.TP_STRING, 40, 0);
        lcTipoCli.add(new GuardaCampo(txtCodTipoCli,7,100,80,20,"CodTipoCli","Cód.tp.cli.",true,false,null,JTextFieldPad.TP_INTEGER,false),"txtCodTipoCli");
        lcTipoCli.add(new GuardaCampo(txtDescTipoCli,90,100,207,20,"DescTipoCli","Descrição do tipo de cliente",false,false,null,JTextFieldPad.TP_STRING,false),"txtDescTipoCli");
        lcTipoCli.montaSql(false, "TIPOCLI", "VD");
        lcTipoCli.setQueryCommit(false);
        lcTipoCli.setReadOnly(true);
        txtCodTipoCli.setTabelaExterna(lcTipoCli);

		txtCodClas.setTipo(JTextFieldPad.TP_INTEGER, 8, 0);
		txtDescClas.setTipo(JTextFieldPad.TP_STRING, 40, 0);
		lcClas.add(new GuardaCampo(txtCodClas,7,100,80,20,"CodClasCli","Cód.c.cli.",true,false,null,JTextFieldPad.TP_INTEGER,false),"txtCodClas");
		lcClas.add(new GuardaCampo(txtDescClas,90,100,207,20,"DescClasCli","Descrição da calssificação do cliente",false,false,null,JTextFieldPad.TP_STRING,false),"txtDescClas");
		lcClas.montaSql(false, "CLASCLI", "VD");
		lcClas.setQueryCommit(false);
		lcClas.setReadOnly(true);
		txtCodClas.setTabelaExterna(lcClas);
		
		txtCodTBA.setTipo(JTextFieldPad.TP_INTEGER, 8, 0);
		txtDescTBA.setTipo(JTextFieldPad.TP_STRING, 50, 0);
		lcTabAC.add(new GuardaCampo(txtCodTBA,7,100,80,20,"CodTB","Cód.tab.",true,false,null,JTextFieldPad.TP_INTEGER,false),"txtCodTBA");
		lcTabAC.add(new GuardaCampo(txtDescTBA,90,100,207,20,"DescTB","Descrição da tabela padrão para aceite",false,false,null,JTextFieldPad.TP_STRING,false),"txtDescTBA");
		lcTabAC.montaSql(false, "TABELA", "SG");
		lcTabAC.setQueryCommit(false);
		lcTabAC.setReadOnly(true);
		txtCodTBA.setTabelaExterna(lcTabAC);

		txtCodITTBA.setTipo(JTextFieldPad.TP_INTEGER, 8, 0);
		txtDescITTBA.setTipo(JTextFieldPad.TP_STRING, 50, 0);
		lcTabITAC.add(new GuardaCampo(txtCodITTBA,7,100,80,20,"CodITTB","Cód.it.tab.",true,false,null,JTextFieldPad.TP_INTEGER,false),"txtCodITTBA");
		lcTabITAC.add(new GuardaCampo(txtDescITTBA,90,100,207,20,"DescITTB","Descrição da situação para aceite",false,false,null,JTextFieldPad.TP_STRING,false),"txtDescITTBA");
		lcTabITAC.setDinWhereAdic("CODTB = #N",txtCodTBA);
		lcTabITAC.montaSql(false, "ITTABELA", "SG");
		lcTabITAC.setQueryCommit(false);
		lcTabITAC.setReadOnly(true);
		txtCodITTBA.setTabelaExterna(lcTabITAC);

		txtCodTBV.setTipo(JTextFieldPad.TP_INTEGER, 8, 0);
		txtDescTBV.setTipo(JTextFieldPad.TP_STRING, 50, 0);
		lcTabAV.add(new GuardaCampo(txtCodTBV,7,100,80,20,"CodTB","Cód.tab.",true,false,null,JTextFieldPad.TP_INTEGER,false),"txtCodTBV");
		lcTabAV.add(new GuardaCampo(txtDescTBV,90,100,207,20,"DescTB","Descrição da tabela padrão para aceite",false,false,null,JTextFieldPad.TP_STRING,false),"txtDescTBV");
		lcTabAV.montaSql(false, "TABELA", "SG");
		lcTabAV.setQueryCommit(false);
		lcTabAV.setReadOnly(true);
		txtCodTBV.setTabelaExterna(lcTabAV);

		txtCodITTBV.setTipo(JTextFieldPad.TP_INTEGER, 8, 0);
		txtDescITTBV.setTipo(JTextFieldPad.TP_STRING, 50, 0);
		lcTabITAV.add(new GuardaCampo(txtCodITTBV,7,100,80,20,"CodITTB","Cód.it.tab.",true,false,null,JTextFieldPad.TP_INTEGER,false),"txtCodITTBV");
		lcTabITAV.add(new GuardaCampo(txtDescITTBV,90,100,207,20,"DescITTB","Descrição da situação para aceite",false,false,null,JTextFieldPad.TP_STRING,false),"txtDescITTBV");
		lcTabITAV.setDinWhereAdic("CODTB = #N",txtCodTBV);
		lcTabITAV.montaSql(false, "ITTABELA", "SG");
		lcTabITAV.setQueryCommit(false);
		lcTabITAV.setReadOnly(true);
		txtCodITTBV.setTabelaExterna(lcTabITAV);
        
		txtCodVend.setTipo(JTextFieldPad.TP_INTEGER, 8, 0);
		txtNomeVend.setTipo(JTextFieldPad.TP_STRING, 50, 0);
		lcVend.add(new GuardaCampo(txtCodVend,7,100,80,20,"CodVend","Cód.repr.",true,false,null,JTextFieldPad.TP_INTEGER,false),"txtCodVend");
		lcVend.add(new GuardaCampo(txtNomeVend,90,100,207,20,"NomeVend","Nome do represetante",false,false,null,JTextFieldPad.TP_STRING,false),"txtNomeVend");
		lcVend.montaSql(false, "VENDEDOR", "VD");
		lcVend.setQueryCommit(false);
		lcVend.setReadOnly(true);
		txtCodVend.setTabelaExterna(lcVend);
           
		txtCodTpAtend.setTipo(JTextFieldPad.TP_INTEGER, 8, 0);
		txtDescTpAtend.setTipo(JTextFieldPad.TP_STRING, 40, 0);
		lcTpAtend.add(new GuardaCampo(txtCodTpAtend,7,100,80,20,"CodTpAtendo","Cód.tp.atend.",true,false,null,JTextFieldPad.TP_INTEGER,true),"txtCodUnidx");
		lcTpAtend.add(new GuardaCampo(txtDescTpAtend,90,100,207,20,"DescTpAtendo","Descrição do tipo de atendimento",false,false,null,JTextFieldPad.TP_STRING,false),"txtDescUnidx");
		lcTpAtend.montaSql(false, "TIPOATENDO", "AT");
		lcTpAtend.setQueryCommit(false);
		lcTpAtend.setReadOnly(true);
		txtCodTpAtend.setTabelaExterna(lcTpAtend);

		txtCodTpAtend2.setTipo(JTextFieldPad.TP_INTEGER, 8, 0);
		txtDescTpAtend2.setTipo(JTextFieldPad.TP_STRING, 40, 0);
		lcTpAtend2.add(new GuardaCampo(txtCodTpAtend2,7,100,80,20,"CodTpAtendo","Cód.tp.atend.",true,false,null,JTextFieldPad.TP_INTEGER,true),"txtCodUnidx");
		lcTpAtend2.add(new GuardaCampo(txtDescTpAtend2,90,100,207,20,"DescTpAtendo","Descrição do tipo de atendimento",false,false,null,JTextFieldPad.TP_STRING,false),"txtDescUnidx");
		lcTpAtend2.montaSql(false, "TIPOATENDO", "AT");
		lcTpAtend2.setQueryCommit(false);
		lcTpAtend2.setReadOnly(true);
		txtCodTpAtend2.setTabelaExterna(lcTpAtend2);

		txtCodTpAtend3.setTipo(JTextFieldPad.TP_INTEGER, 8, 0);
		txtDescTpAtend3.setTipo(JTextFieldPad.TP_STRING, 40, 0);
		lcTpAtend3.add(new GuardaCampo(txtCodTpAtend3,7,100,80,20,"CodTpAtendo","Códtp.atend.",true,false,null,JTextFieldPad.TP_INTEGER,true),"txtCodUnidx");
		lcTpAtend3.add(new GuardaCampo(txtDescTpAtend3,90,100,207,20,"DescTpAtendo","Descrição do tipo de atendimento",false,false,null,JTextFieldPad.TP_STRING,false),"txtDescUnidx");
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
		
		txtCodSetor.setTipo(JTextFieldPad.TP_INTEGER, 8, 0);
		txtDescSetor.setTipo(JTextFieldPad.TP_STRING, 40, 0);
		lcSetor.add(new GuardaCampo(txtCodSetor,7,100,80,20,"CodSetAt","Cód.setor",true,false,null,JTextFieldPad.TP_INTEGER,true),"txtCodUnidx");
		lcSetor.add(new GuardaCampo(txtDescSetor,90,100,207,20,"DescSetAt","Descrição do setor",false,false,null,JTextFieldPad.TP_STRING,false),"txtDescUnidx");
		lcSetor.montaSql(false, "SETOR", "AT");
		lcSetor.setQueryCommit(false);
		lcSetor.setReadOnly(true);
		txtCodSetor.setTabelaExterna(lcSetor);

		txtCodSetor2.setTipo(JTextFieldPad.TP_INTEGER, 8, 0);
		txtDescSetor2.setTipo(JTextFieldPad.TP_STRING, 40, 0);
		lcSetor2.add(new GuardaCampo(txtCodSetor2,7,100,80,20,"CodSetAt","Cód.setor",true,false,null,JTextFieldPad.TP_INTEGER,true),"txtCodSetAt2");
		lcSetor2.add(new GuardaCampo(txtDescSetor2,90,100,207,20,"DescSetAt","Descrição do setor",false,false,null,JTextFieldPad.TP_STRING,false),"txtDescSetor2");
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