/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *   
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FCliente.java <BR>
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
 * Tela de cadastro de clientes.
 */

package org.freedom.modulos.std;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import org.freedom.componentes.JPanelPad;
import javax.swing.JScrollPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.acao.InsertEvent;
import org.freedom.acao.InsertListener;
import org.freedom.acao.PostEvent;
import org.freedom.acao.PostListener;
import org.freedom.acao.RadioGroupEvent;
import org.freedom.acao.RadioGroupListener;
import org.freedom.acao.TabelaSelEvent;
import org.freedom.acao.TabelaSelListener;
import org.freedom.bmps.Icone;
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
import org.freedom.modulos.atd.FConveniado;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.DLInputText;
import org.freedom.telas.FAndamento;
import org.freedom.telas.FTabDados;

public class FCliente extends FTabDados implements RadioGroupListener, PostListener, ActionListener, 
               TabelaSelListener, ChangeListener, CarregaListener, InsertListener {
  private JPanelPad pinCli = new JPanelPad();
  private JPanelPad pnFor = new JPanelPad(JPanelPad.TP_JPANEL,new BorderLayout());
  private JPanelPad pinFor = new JPanelPad(0,80);
  private Tabela tbObsData = new Tabela();
  private JTextFieldPad txtCodCli = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
  private JTextFieldPad txtRazCli = new JTextFieldPad(JTextFieldPad.TP_STRING, 50, 0);
  private JTextFieldPad txtNomeCli = new JTextFieldPad(JTextFieldPad.TP_STRING, 40, 0);
  private JTextFieldPad txtContCli = new JTextFieldPad(JTextFieldPad.TP_STRING, 40, 0);
  private JTextFieldPad txtCodTipoCli = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
  private JTextFieldFK  txtDescTipoCli = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);
  private JTextFieldPad txtCnpjCli = new JTextFieldPad(JTextFieldPad.TP_STRING, 14, 0);
  private JTextFieldPad txtInscCli = new JTextFieldPad(JTextFieldPad.TP_STRING, 15, 0);
  private JTextFieldPad txtCpfCli = new JTextFieldPad(JTextFieldPad.TP_STRING, 11, 0);
  private JTextFieldPad txtRgCli = new JTextFieldPad(JTextFieldPad.TP_STRING, 10, 0);
  private JTextFieldPad txtSSPCli = new JTextFieldPad(JTextFieldPad.TP_STRING, 10, 0);
  private JTextFieldPad txtEndCli = new JTextFieldPad(JTextFieldPad.TP_STRING, 50, 0);
  private JTextFieldPad txtNumCli = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
  private JTextFieldPad txtComplCli = new JTextFieldPad(JTextFieldPad.TP_STRING, 20, 0);
  private JTextFieldPad txtBairCli = new JTextFieldPad(JTextFieldPad.TP_STRING, 30, 0);
  private JTextFieldPad txtCidCli = new JTextFieldPad(JTextFieldPad.TP_STRING, 30, 0);
  private JTextFieldPad txtUFCli = new JTextFieldPad(JTextFieldPad.TP_STRING, 2, 0);
  private JTextFieldPad txtCepCli = new JTextFieldPad(JTextFieldPad.TP_STRING, 8, 0);
  private JTextFieldPad txtDDDCli = new JTextFieldPad(JTextFieldPad.TP_STRING, 4, 0);
  private JTextFieldPad txtFoneCli = new JTextFieldPad(JTextFieldPad.TP_STRING, 12, 0);
  private JTextFieldPad txtRamalCli = new JTextFieldPad(JTextFieldPad.TP_STRING, 6, 0);
  private JTextFieldPad txtDDDFaxCli = new JTextFieldPad(JTextFieldPad.TP_STRING, 4, 0);
  private JTextFieldPad txtFaxCli = new JTextFieldPad(JTextFieldPad.TP_STRING, 8, 0);
  private JTextFieldPad txtEmailCli = new JTextFieldPad(JTextFieldPad.TP_STRING, 50, 0);
  private JTextFieldPad txtIncraCli = new JTextFieldPad(JTextFieldPad.TP_STRING, 15, 0);
  private JTextFieldPad txtEndCob = new JTextFieldPad(JTextFieldPad.TP_STRING, 50, 0);
  private JTextFieldPad txtNumCob = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
  private JTextFieldPad txtComplCob = new JTextFieldPad(JTextFieldPad.TP_STRING, 20, 0);
  private JTextFieldPad txtBairCob = new JTextFieldPad(JTextFieldPad.TP_STRING, 30, 0);
  private JTextFieldPad txtCidCob = new JTextFieldPad(JTextFieldPad.TP_STRING, 30, 0);
  private JTextFieldPad txtCepCob = new JTextFieldPad(JTextFieldPad.TP_STRING, 8, 0);
  private JTextFieldPad txtUFCob = new JTextFieldPad(JTextFieldPad.TP_STRING, 2, 0);
  private JTextFieldPad txtDDDFoneCob = new JTextFieldPad(JTextFieldPad.TP_STRING, 4, 0);
  private JTextFieldPad txtFoneCob = new JTextFieldPad(JTextFieldPad.TP_STRING, 12, 0);
  private JTextFieldPad txtDDDFaxCob = new JTextFieldPad(JTextFieldPad.TP_STRING, 4, 0);
  private JTextFieldPad txtFaxCob = new JTextFieldPad(JTextFieldPad.TP_STRING, 8, 0);
  private JTextFieldPad txtEndEnt = new JTextFieldPad(JTextFieldPad.TP_STRING, 50, 0);
  private JTextFieldPad txtNumEnt = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
  private JTextFieldPad txtComplEnt = new JTextFieldPad(JTextFieldPad.TP_STRING, 20, 0);
  private JTextFieldPad txtBairEnt = new JTextFieldPad(JTextFieldPad.TP_STRING, 30, 0);
  private JTextFieldPad txtCidEnt = new JTextFieldPad(JTextFieldPad.TP_STRING, 30, 0);
  private JTextFieldPad txtCepEnt = new JTextFieldPad(JTextFieldPad.TP_STRING, 8, 0);
  private JTextFieldPad txtUFEnt = new JTextFieldPad(JTextFieldPad.TP_STRING, 2, 0);
  private JTextFieldPad txtDDDFoneEnt = new JTextFieldPad(JTextFieldPad.TP_STRING, 4, 0);
  private JTextFieldPad txtFoneEnt = new JTextFieldPad(JTextFieldPad.TP_STRING, 12, 0);
  private JTextFieldPad txtDDDFaxEnt = new JTextFieldPad(JTextFieldPad.TP_STRING, 4, 0);
  private JTextFieldPad txtFaxEnt = new JTextFieldPad(JTextFieldPad.TP_STRING, 8, 0);
  private JTextFieldPad txtCodVend = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
  private JTextFieldFK  txtDescVend = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);
  private JTextFieldPad txtCodFiscCli = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
  private JTextFieldFK  txtDescFiscCli = new JTextFieldFK(JTextFieldPad.TP_STRING, 40, 0);
  private JTextFieldPad txtCodPlanoPag = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
  private JTextFieldFK  txtDescPlanoPag = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);
  private JTextFieldPad txtCodTran = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
  private JTextFieldFK  txtDescTran = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);
  private JTextFieldPad txtCodTipoCob = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
  private JTextFieldFK  txtDescTipoCob = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);
  private JTextFieldPad txtCodBanco = new JTextFieldPad(JTextFieldPad.TP_STRING, 3, 0);
  private JTextFieldFK  txtNomeBanco = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);
  private JTextFieldPad txtCodSetor = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
  private JTextFieldFK  txtDescSetor = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);
  private JTextFieldPad txtCodPais = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
  private JTextFieldFK  txtDescPais = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);
  private JTextFieldPad txtCodPesq = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
  private JTextFieldFK  txtDescPesq = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);
  private JTextFieldPad txtCodClas = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
  private JTextFieldFK  txtDescClas = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);
  private JTextFieldPad txtDDDCelCli = new JTextFieldPad(JTextFieldPad.TP_STRING, 4, 0);
  private JTextFieldPad txtCelCli = new JTextFieldPad(JTextFieldPad.TP_STRING, 8, 0);
  private JTextFieldPad txtCodFor = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
  private JTextFieldPad txtCodCliFor = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
  private JTextFieldFK txtDescFor = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);
  private JCheckBoxPad cbAtivo = new JCheckBoxPad("Ativo","S","N");
  private Vector vPessoaLab = new Vector();
  private Vector vPessoaVal = new Vector();
  private JRadioGroup rgPessoa = null;
  private JPanelPad pinEnt = new JPanelPad();
  private JPanelPad pinVend = new JPanelPad();
  private JPanelPad pinCob = new JPanelPad();
  private JPanelPad pnObs1 = new JPanelPad(JPanelPad.TP_JPANEL,new BorderLayout()); // JPanelPad de observações com 2 linha e 1 coluna
  private JPanelPad pnObs1_1 = new JPanelPad(JPanelPad.TP_JPANEL,new BorderLayout()); // JPanelPad de observações gerais
  private JPanelPad pnObs1_2 = new JPanelPad(JPanelPad.TP_JPANEL,new BorderLayout()); // JPanelPad principal de observações por data
  //private JPanelPad pinObs1_2_1 = new JPanelPad(200,100); // JPanelPad para scrool da tabela de datas de observação
  private JPanelPad pnObs1_2_1 = new JPanelPad(JPanelPad.TP_JPANEL,new BorderLayout());
  private JPanelPad pnObs1_2_2 = new JPanelPad(JPanelPad.TP_JPANEL,new BorderLayout()); // Pinel para observações e outros
  private JPanelPad pnObs1_2_2_1 = new JPanelPad(JPanelPad.TP_JPANEL,new BorderLayout());
  private JPanelPad pnObs1_2_2_2 = new JPanelPad(JPanelPad.TP_JPANEL,new BorderLayout());
  private JPanelPad pinObs1_2_1_1 = new JPanelPad(200,200); 
  private JPanelPad pinObs1_2_2_2_1 = new JPanelPad(0,30);
   
  private JTextAreaPad txaObs = new JTextAreaPad();
  private JTextAreaPad txaTxtObsCli = new JTextAreaPad(); // Campo memo para observações por data
  private JScrollPane spnObs = new JScrollPane(txaObs); // Scrool pane para observações gerais 
  private JScrollPane spnObsCli = new JScrollPane(txaTxtObsCli); // Scrool pane para o campo de observações por data
  private JScrollPane spnObsTb = new JScrollPane(tbObsData); // Cria tabela de observações dentro do scroll pane
  private ListaCampos lcTipoCli = new ListaCampos(this,"TI");
  private ListaCampos lcTipoFiscCli = new ListaCampos(this,"FC");
  private ListaCampos lcVend = new ListaCampos(this,"VD");
  private ListaCampos lcPlanoPag = new ListaCampos(this,"PG");
  private ListaCampos lcTran = new ListaCampos(this,"TN");
  private ListaCampos lcTipoCob = new ListaCampos(this,"TC");
  private ListaCampos lcBanco = new ListaCampos(this,"BO");
  private ListaCampos lcSetor = null;
  private ListaCampos lcClas = new ListaCampos(this,"CC");
  private ListaCampos lcPesq = new ListaCampos(this,"PQ");
  private ListaCampos lcCliFor = new ListaCampos(this);
  private ListaCampos lcFor = new ListaCampos(this,"FR");
  private ListaCampos lcPais = new ListaCampos(this,"");
  private Tabela tabFor = new Tabela();
  private JScrollPane spnTabFor = new JScrollPane(tabFor);
  private JButton btAtEntrega = new JButton(Icone.novo("btReset.gif"));
  private JButton btAtCobranca = new JButton(Icone.novo("btReset.gif"));
  private JButton btNovaObs = new JButton(Icone.novo("btNovo.gif"));
  private JButton btExclObs = new JButton(Icone.novo("btExcluir.gif"));
  private JButton btEditObs = new JButton(Icone.novo("btEditar.gif"));
  private JButton btGrpCli = new JButton(Icone.novo("btCliente.gif"));
  private Navegador navFor = new Navegador(true);
  private FConveniado telaConv;
  private boolean[] bPref = null;
  private boolean bExecCargaObs = false;
  public FCliente () {
    setTitulo("Cadastro de Clientes"); 
    setAtribos(50, 0, 530, 520);
    //setFirstFocus(txtCodCli);
    
    lcCliFor.setMaster(lcCampos);
    lcCampos.adicDetalhe(lcCliFor);
    lcCliFor.setTabela(tabFor);
    
    pinCli = new JPanelPad(500,330);
    setPainel(pinCli);
	   
  }
  private void montaTela() {
      
  	adicTab("Cliente", pinCli); 

  	lcCampos.addPostListener(this);
  	lcCampos.addInsertListener(this);
  	
  	lcTipoCli.add(new GuardaCampo( txtCodTipoCli, "CodTipoCli", "Cód.tp.cli.", ListaCampos.DB_PK, true));
  	lcTipoCli.add(new GuardaCampo( txtDescTipoCli, "DescTipoCli", "Descrição do tipo de cliente", ListaCampos.DB_SI, false));
  	lcTipoCli.montaSql(false, "TIPOCLI", "VD");    
  	lcTipoCli.setQueryCommit(false);
  	lcTipoCli.setReadOnly(true);
  	txtCodTipoCli.setTabelaExterna(lcTipoCli);

  	
  	lcVend.add(new GuardaCampo( txtCodVend, "CodVend", "Cód.comiss.",ListaCampos.DB_PK, false));
  	lcVend.add(new GuardaCampo( txtDescVend, "NomeVend", "Nome do comissionado", ListaCampos.DB_SI, false));
  	lcVend.montaSql(false, "VENDEDOR", "VD");    
  	lcVend.setQueryCommit(false);
  	lcVend.setReadOnly(true);
  	txtCodVend.setTabelaExterna(lcVend);

  	
  	lcPlanoPag.add(new GuardaCampo( txtCodPlanoPag, "CodPlanoPag", "Cód.p.pag.", ListaCampos.DB_PK, false));
  	lcPlanoPag.add(new GuardaCampo( txtDescPlanoPag, "DescPlanoPag", "Descrição do plano de pagamento", ListaCampos.DB_SI, false));
  	lcPlanoPag.montaSql(false, "PLANOPAG", "FN");    
  	lcPlanoPag.setQueryCommit(false);
  	lcPlanoPag.setReadOnly(true);
  	txtCodPlanoPag.setTabelaExterna(lcPlanoPag);

  	
  	lcTran.add(new GuardaCampo( txtCodTran,"CodTran", "Cód.tran.", ListaCampos.DB_PK, false));
  	lcTran.add(new GuardaCampo( txtDescTran, "NomeTran", "Razão social da transportadora", ListaCampos.DB_SI, false));
  	lcTran.montaSql(false, "TRANSP", "VD");
  	lcTran.setQueryCommit(false);
  	lcTran.setReadOnly(true);
  	txtCodTran.setTabelaExterna(lcTran);

  	lcPais.setUsaME(false);
  	lcPais.add(new GuardaCampo( txtCodPais, "CodPais", "Cod.país.", ListaCampos.DB_PK, false));
  	lcPais.add(new GuardaCampo( txtDescPais, "NomePais", "Nome", ListaCampos.DB_SI, false));
  	lcPais.montaSql(false, "PAIS", "SG");
  	lcPais.setQueryCommit(false);
  	lcPais.setReadOnly(true);
  	txtCodPais.setTabelaExterna(lcPais);

  	
  	lcTipoCob.add(new GuardaCampo( txtCodTipoCob, "CodTipoCob", "Cód.tp.cob.", ListaCampos.DB_PK,false));
  	lcTipoCob.add(new GuardaCampo( txtDescTipoCob, "DescTipoCob", "Descrição do tipo de cobrança", ListaCampos.DB_SI, false));
  	lcTipoCob.montaSql(false, "TIPOCOB", "FN");    
  	lcTipoCob.setQueryCommit(false);
  	lcTipoCob.setReadOnly(true);
  	txtCodTipoCob.setTabelaExterna(lcTipoCob);
    
    
  	lcTipoFiscCli.add(new GuardaCampo( txtCodFiscCli, "CodFiscCli", "Cód.tp.fisc.", ListaCampos.DB_PK, false));
  	lcTipoFiscCli.add(new GuardaCampo( txtDescFiscCli, "DescFiscCli", "Descrição do tipo fiscal", ListaCampos.DB_SI, false));
  	lcTipoFiscCli.montaSql(false, "TIPOFISCCLI", "LF");    
  	lcTipoFiscCli.setQueryCommit(false);
  	lcTipoFiscCli.setReadOnly(true);
  	txtCodFiscCli.setTabelaExterna(lcTipoFiscCli);

   
  	lcBanco.add(new GuardaCampo( txtCodBanco, "CodBanco", "Cód.banco", ListaCampos.DB_PK, false));
  	lcBanco.add(new GuardaCampo( txtNomeBanco, "NomeBanco", "Nome do banco", ListaCampos.DB_SI, false));
  	lcBanco.montaSql(false, "BANCO", "FN");    
  	lcBanco.setQueryCommit(false);
  	lcBanco.setReadOnly(true);
  	txtCodBanco.setTabelaExterna(lcBanco);

  	
  	lcPesq.add(new GuardaCampo( txtCodPesq, "CodCli", "Cód.cli.p.",ListaCampos.DB_PK, false));
  	lcPesq.add(new GuardaCampo( txtDescPesq, "RazCli", "Razão social do cliente pricipal", ListaCampos.DB_SI, false));
  	lcPesq.montaSql(false, "CLIENTE", "VD");    
  	lcPesq.setQueryCommit(false);
  	lcPesq.setReadOnly(true);
  	txtCodPesq.setTabelaExterna(lcPesq);
  	
  	
  	lcClas.add(new GuardaCampo( txtCodClas, "CodClasCli", "Cód.c.cli.", ListaCampos.DB_PK, true));
  	lcClas.add(new GuardaCampo( txtDescClas, "DescClasCli", "Descrição da classificação do cliente", ListaCampos.DB_SI, false));
  	lcClas.montaSql(false, "CLASCLI", "VD");    
  	lcClas.setQueryCommit(false);
  	lcClas.setReadOnly(true);
  	txtCodClas.setTabelaExterna(lcClas);

  	adicCampo(txtCodCli, 7, 20, 80, 20, "CodCli", "Cód.cli.", ListaCampos.DB_PK, true);
  	adicCampo(txtRazCli, 90, 20, 307, 20, "RazCli", "Razão social do cliente", ListaCampos.DB_SI, true);
  	
  	vPessoaLab.addElement("Jurídica");
  	vPessoaLab.addElement("Física");
  	vPessoaVal.addElement("J");
  	vPessoaVal.addElement("F");
  	rgPessoa = new JRadioGroup( 2, 1, vPessoaLab, vPessoaVal);
  	rgPessoa.addRadioGroupListener(this);   
  	
  	adicDB(rgPessoa, 400, 20, 100, 60, "PessoaCli", "Pessoa",true);
  	rgPessoa.setVlrString("J");
 	
  	adicDB(cbAtivo, 7, 60, 70, 20, "AtivoCli", "Ativo",true);
  	adicCampo(txtNomeCli, 90, 60, 307, 20, "NomeCli", "Nome", ListaCampos.DB_SI, true);
  	adicCampo(txtCodTipoCli, 7, 100, 80, 20, "CodTipoCli", "Cód.tp.cli.", ListaCampos.DB_FK, txtDescTipoCli, true);
  	adicDescFK(txtDescTipoCli, 90, 100, 237, 20, "DescTipoCli", "Descrição do tipo de cliente");
  	adicCampo(txtCpfCli, 330, 100, 170, 20, "CpfCli", "CPF", ListaCampos.DB_SI, false);
  	adicCampo(txtCodClas, 7, 140, 80, 20, "CodClasCli", "Cód.c.cli",ListaCampos.DB_FK, txtDescClas, true);
  	adicDescFK(txtDescClas, 90, 140, 237, 20, "DescClasCli", "Descrição da classificação do cliente");
  	adicCampo(txtRgCli, 330, 140, 90, 20, "RgCli", "RG", ListaCampos.DB_SI, false);
  	adicCampo(txtSSPCli, 423, 140, 77, 20, "SSPCli", "Orgão exp.", ListaCampos.DB_SI, false);
  	adicCampo(txtCnpjCli, 7, 180, 150, 20, "CnpjCli", "CNPJ", ListaCampos.DB_SI, false);
  	adicCampo(txtInscCli, 160, 180, 147, 20, "InscCli", "Inscrição Estadual", ListaCampos.DB_SI, false);
  	adicCampo(txtContCli, 310, 180, 190, 20, "ContCli", "Contato", ListaCampos.DB_SI, false);
  	JCheckBoxPad cbSimples = new JCheckBoxPad("Simples","S","N");
  	adicDB(cbSimples, 7, 220, 80, 20, "SimplesCli", "Fiscal",true);
  	adicCampo(txtEndCli, 90, 220, 257, 20, "EndCli", "Endereço", ListaCampos.DB_SI, false);
  	adicCampo(txtNumCli, 350, 220, 77, 20, "NumCli", "Num.", ListaCampos.DB_SI, false);
  	adicCampo(txtComplCli, 430, 220, 70, 20, "ComplCli", "Compl.", ListaCampos.DB_SI, false);
  	adicCampo(txtBairCli, 7, 260, 180, 20, "BairCli", "Bairro", ListaCampos.DB_SI, false);
  	adicCampo(txtCidCli, 190, 260, 177, 20, "CidCli", "Cidade", ListaCampos.DB_SI, false);
  	adicCampo(txtCepCli, 370, 260, 77, 20, "CepCli", "Cep", ListaCampos.DB_SI, false);
  	adicCampo(txtUFCli, 450, 260, 50, 20, "UFCli", "UF", ListaCampos.DB_SI, false);
  	adicCampo(txtCodPais, 7, 300, 70, 20, "CodPais","Cod.país",ListaCampos.DB_FK,false);
  	adicDescFK(txtDescPais, 80, 300, 217, 20,"DescPais","Nome do país");
  	adicCampo(txtDDDCli, 300, 300, 40, 20, "DDDCli", "DDD", ListaCampos.DB_SI, false);
  	adicCampo(txtFoneCli, 343, 300, 97, 20, "FoneCli", "Telefone", ListaCampos.DB_SI, false);
  	adicCampo(txtRamalCli, 443, 300, 57, 20, "RamalCli", "Ramal", ListaCampos.DB_SI, false);
  	adicCampo(txtDDDFaxCli, 7, 340, 40, 20, "DDDFaxCli", "DDD", ListaCampos.DB_SI, false);
  	adicCampo(txtFaxCli, 50, 340, 97, 20, "FaxCli", "Fax", ListaCampos.DB_SI, false);
  	adicCampo(txtDDDCelCli, 160, 340, 40, 20, "DDDCelCli", "DDD", ListaCampos.DB_SI, false);
  	adicCampo(txtCelCli, 203, 340, 97, 20, "CelCli", "Celular",ListaCampos.DB_SI, false);
  	adicCampo(txtIncraCli, 313, 340, 187, 20, "IncraCli", "Incra", ListaCampos.DB_SI, false);
  	adicCampo(txtEmailCli, 7, 380, 493, 20, "EmailCli", "E-Mail", ListaCampos.DB_SI, false);
    txtCpfCli.setMascara(JTextFieldPad.MC_CPF);
  	txtCnpjCli.setMascara(JTextFieldPad.MC_CNPJ);
  	txtCepCli.setMascara(JTextFieldPad.MC_CEP);
  	txtFoneCli.setMascara(JTextFieldPad.MC_FONE);
  	txtFaxCli.setMascara(JTextFieldPad.MC_FONE);
  	pinEnt = new JPanelPad(500,290);
  	setPainel(pinEnt);
  	
  	adicTab("Entrega", pinEnt);
  	
  	btAtEntrega.setPreferredSize(new Dimension(30,30));
  	btAtEntrega.setToolTipText("Atualiza endereço de entrega.");
  	btAtEntrega.addActionListener(this);
  	
  	adicCampo(txtEndEnt, 7, 20, 260, 20, "EndEnt", "Endereço", ListaCampos.DB_SI,  false);
  	adicCampo(txtNumEnt, 270, 20, 50, 20, "NumEnt", "Num.", ListaCampos.DB_SI,  false);
  	adicCampo(txtComplEnt, 323, 20, 49, 20, "ComplEnt", "Compl.", ListaCampos.DB_SI, false);
  	adicCampo(txtBairEnt, 7, 60, 120, 20, "BairEnt", "Bairro", ListaCampos.DB_SI, false);
  	adicCampo(txtCidEnt, 130, 60, 120, 20, "CidEnt", "Cidade", ListaCampos.DB_SI, false);
  	adicCampo(txtCepEnt, 253, 60, 80, 20, "CepEnt", "Cep", ListaCampos.DB_SI, false);
  	txtCepEnt.setMascara(JTextFieldPad.MC_CEP);
  	adicCampo(txtUFEnt, 336, 60, 36, 20, "UFEnt", "UF", ListaCampos.DB_SI, false);
  	adicCampo(txtDDDFoneEnt, 7, 100, 40, 20, "DDDFoneEnt", "DDD", ListaCampos.DB_SI, false);
  	adicCampo(txtFoneEnt, 50, 100, 138, 20, "FoneEnt", "Telefone", ListaCampos.DB_SI, false);
  	txtFoneEnt.setMascara(JTextFieldPad.MC_FONEDDD);
  	adicCampo(txtDDDFaxEnt, 192, 100, 40, 20, "DDDFaxEnt", "DDD", ListaCampos.DB_SI, false);
  	adicCampo(txtFaxEnt, 235, 100, 138, 20, "FaxEnt", "Fax", ListaCampos.DB_SI, false);
  	txtFaxEnt.setMascara(JTextFieldPad.MC_FONE);
  	adic(btAtEntrega,400,15,30,30); 
  	
  	pinCob = new JPanelPad(500,290);
  	setPainel(pinCob);
  	adicTab("Cobrança", pinCob);

  	btAtCobranca.setPreferredSize(new Dimension(30,30));
  	btAtCobranca.setToolTipText("Atualiza endereço de cobrança.");
  	btAtCobranca.addActionListener(this);
  	
  	adicCampo(txtEndCob, 7, 20, 260, 20, "EndCob", "Endereço", ListaCampos.DB_SI, false);
  	adicCampo(txtNumCob, 270, 20, 50, 20, "NumCob", "Num.", ListaCampos.DB_SI, false);
  	adicCampo(txtComplCob, 323, 20, 49, 20, "ComplCob", "Compl.", ListaCampos.DB_SI, false);
  	adicCampo(txtBairCob, 7, 60, 120, 20, "BairCob", "Bairro", ListaCampos.DB_SI, false);
  	adicCampo(txtCidCob, 130, 60, 120, 20, "CidCob", "Cidade", ListaCampos.DB_SI, false);
  	adicCampo(txtCepCob, 253, 60, 80, 20, "CepCob", "Cep", ListaCampos.DB_SI, false);
  	txtCepCob.setMascara(JTextFieldPad.MC_CEP);
  	adicCampo(txtUFCob, 336, 60, 36, 20, "UFCob", "UF", ListaCampos.DB_SI, false);
  	adicCampo(txtDDDFoneCob, 7, 100, 40, 20, "DDDFoneCob", "DDD", ListaCampos.DB_SI, false);
  	adicCampo(txtFoneCob, 50, 100, 138, 20, "FoneCob", "Telefone", ListaCampos.DB_SI, false);
  	txtFoneCob.setMascara(JTextFieldPad.MC_FONEDDD);
  	adicCampo(txtDDDFaxCob, 192, 100, 40, 20, "DDDFaxCob", "DDD", ListaCampos.DB_SI, false);
  	adicCampo(txtFaxCob, 235, 100, 138, 20, "FaxCob", "Fax", ListaCampos.DB_SI, false);
  	txtFaxCob.setMascara(JTextFieldPad.MC_FONE);
  	adic(btAtCobranca,400,15,30,30);
  	
//Venda:    
    
  	pinVend = new JPanelPad(500,290);
  	setPainel(pinVend);
  	adicTab("Venda", pinVend);
  	adicCampo(txtCodVend, 7, 20, 80, 20, "CodVend", "Cód.comiss.", ListaCampos.DB_FK, txtDescVend, false);
  	adicDescFK(txtDescVend, 90, 20, 237, 20, "NomeVend", "Nome do comissionado");
  	adicCampo(txtCodPlanoPag, 7, 60, 80, 20, "CodPlanoPag", "Cód.p.pag.", ListaCampos.DB_FK, txtDescPlanoPag, false);
  	adicDescFK(txtDescPlanoPag, 90, 60, 237, 20, "DescPlanoPag", "Descrição do plano de pagamento");
  	adicCampo(txtCodTran, 7, 100, 80, 20, "CodTran", "Cód.tran.", ListaCampos.DB_FK, txtDescTran,false);
  	adicDescFK(txtDescTran, 90, 100, 237, 20, "NomeTran", "Nome ou razão social do transportador");
  	adicCampo(txtCodTipoCob, 7, 140, 80, 20, "CodTipoCob", "Cód.t.cob.", ListaCampos.DB_FK, txtDescTipoCob,false);
  	adicDescFK(txtDescTipoCob, 90, 140, 237, 20, "DescTipoCob", "Descrição do tipo de cobrança");
  	adicCampo(txtCodBanco, 7, 180, 80, 20, "CodBanco", "Cód.banco", ListaCampos.DB_FK, txtNomeBanco, false);
  	adicDescFK(txtNomeBanco, 90, 180, 237, 20, "NomeBanco", "Nome do banco");
  	adicCampo(txtCodPesq, 7, 220, 80, 20, "CodPesq", "Cód.cli.p.", ListaCampos.DB_FK, txtDescPesq,false);
  	adicDescFK(txtDescPesq, 90, 220, 237, 20, "RazCli", "Razão social do cliente principal");
  	adicCampo(txtCodFiscCli, 7, 260, 80, 20, "CodFiscCli", "Cód.tp.fisc.", ListaCampos.DB_FK, txtDescFiscCli,false);
  	adicDescFK(txtDescFiscCli, 90, 260, 237, 20, "DescFiscCli", "Descrição do tipo fiscal");
    // Adicionar botão para agrupamento de clientes

  	btGrpCli.setToolTipText("Clientes agrupados");
  	btGrpCli.setPreferredSize(new Dimension(38,26));
  	pnImp.add(btGrpCli);
  	
   // adic(btGrpCli,330,215,25,25);
    btGrpCli.addActionListener(this);
    
    // 
    
  	adicTab("Observações", pnObs1);
  	adicDBLiv(txaObs, "ObsCli", "Observações", false);

  	txaTxtObsCli.setEditable(false);
  	tbObsData.adicColuna("Data");
  	tbObsData.adicColuna("Hora");
  	tbObsData.adicColuna("Seq.");
  	tbObsData.setTamColuna(80,0);
  	tbObsData.setTamColuna(80,0);
  	tbObsData.setTamColuna(40,1);
  	
  	btNovaObs.setToolTipText("Nova observação por data.");
  	btExclObs.setToolTipText("Exclui observação selecionada.");
  	btEditObs.setToolTipText("Edita observação selecionanda.");
  	
  	btNovaObs.addActionListener(this);
  	btExclObs.addActionListener(this);
  	btEditObs.addActionListener(this);
  	
  	pinObs1_2_2_2_1.adic(btNovaObs,0,0,30,26);
  	pinObs1_2_2_2_1.adic(btExclObs,31,0,30,26);
  	pinObs1_2_2_2_1.adic(btEditObs,62,0,30,26);
  	pnObs1_2_2_1.add(spnObsCli);
  	pnObs1_2_2_2.add(pinObs1_2_2_2_1);
  	pinObs1_2_1_1.adic(spnObsTb,0,0,200,200);
  	pnObs1_2_1.add(pinObs1_2_1_1); // adiciona o scrool pane da tabela de datas no painel da esquerda
  	pnObs1_2_2.add(pnObs1_2_2_1,BorderLayout.CENTER); // adiciona memo de observações no painel da direita
  	pnObs1_2_2.add(pnObs1_2_2_2,BorderLayout.SOUTH);
  	pnObs1_1.add(spnObs,BorderLayout.CENTER); // adiciona as observações gerais no painel 
  	pnObs1_2.add(pnObs1_2_1, BorderLayout.WEST);
  	pnObs1_2.add(pnObs1_2_2, BorderLayout.CENTER); 
  	pnObs1.add(pnObs1_1,BorderLayout.CENTER);
  	pnObs1.add(pnObs1_2,BorderLayout.SOUTH);
 
    
    setListaCampos( true, "CLIENTE", "VD");
    
//Fornecedor:
    
    setPainel(pinFor,pnFor);
    adicTab("Cliente X Forn.",pnFor);
    setListaCampos(lcCliFor);
  
    navFor.setAtivo(6,false);

    setNavegador(navFor);
    pnFor.add(pinFor, BorderLayout.SOUTH);
    pnFor.add(spnTabFor, BorderLayout.CENTER);

    pinFor.adic(navFor,0,50,270,25);
 
    lcFor.add(new GuardaCampo( txtCodFor, "CodFor", "Cód.for.", ListaCampos.DB_PK, null, true));
    lcFor.add(new GuardaCampo( txtDescFor, "RazFor", "Razão social do fronecedor", ListaCampos.DB_SI, false));
    lcFor.montaSql(false, "FORNECED", "CP");
    lcFor.setReadOnly(true);
    lcFor.setQueryCommit(false);
    txtCodFor.setListaCampos(lcFor);
    txtCodFor.setTabelaExterna(lcFor);

    adicCampo(txtCodFor, 7, 20, 80, 20, "CodFor", "Cód.forn.", ListaCampos.DB_PF, txtDescFor,true);
    adicDescFK(txtDescFor, 90, 20, 297, 20, "RazFor", "Razão social do fornecedor");
    adicCampo(txtCodCliFor, 390, 20, 105, 20, "CodCliFor", "Cód.cli.for.", ListaCampos.DB_SI, false);
    setListaCampos( false, "CLIENTEFOR", "VD");
    lcCliFor.montaTab();
    lcCliFor.setQueryInsert(false);
    lcCliFor.setQueryCommit(false);
    tabFor.setTamColuna(250,1);
    
    txtCodPesq.setNomeCampo("CodCli");

  	btImp.addActionListener(this);
  	btPrevimp.addActionListener(this);
  	tpn.addChangeListener(this);
  	lcCampos.setQueryInsert(false);
  	
  	if (bPref[0]) {
        lcSetor = new ListaCampos(this,"SR");
  		lcSetor.add(new GuardaCampo( txtCodSetor, "CodSetor", "Cód.setor", ListaCampos.DB_PK, txtDescSetor, false));
  		lcSetor.add(new GuardaCampo( txtDescSetor, "DescSetor", "Descrição do setor", ListaCampos.DB_SI,false));
  		lcSetor.montaSql(false, "SETOR", "VD");    
  		lcSetor.setQueryCommit(false);
  		lcSetor.setReadOnly(true);
  		txtCodSetor.setTabelaExterna(lcSetor);
  	
  		adicCampo(txtCodSetor, 7, 300, 80, 20, "CodSetor", "Cód.setor", ListaCampos.DB_FK, txtDescSetor,true);
  		adicDescFK(txtDescSetor, 90, 300, 237, 20, "DescSetor", "Descrição do setor");
  	}
  	
  	lcCampos.addCarregaListener(this);
  	
  	tbObsData.addTabelaSelListener(this);
  	
  }
  
  private void novaObs() {
      String sSql = null;
      int iCodCli = 0;
      PreparedStatement ps = null;
      DLInputText dl = null;
      iCodCli = txtCodCli.getVlrInteger().intValue();
      java.util.Date dtHoje = null;
      if ( iCodCli!=0 ) {
           try {
               dl = new DLInputText(this, "Observação", false);
               dl.setTexto("");
               dl.setVisible(true);
               if (( dl.OK ) && (!dl.getTexto().trim().equals(""))) {
                   try {
                       dtHoje = new java.util.Date();
                       sSql = "INSERT INTO VDOBSCLI (SEQOBSCLI, CODEMP, CODFILIAL, CODCLI, DTOBSCLI, HOBSCLI, TXTOBSCLI) " +
                       		"VALUES ( (COALESCE( (SELECT MAX(OC.SEQOBSCLI) FROM VDOBSCLI OC " +
                       		" WHERE OC.CODEMP=? AND OC.CODFILIAL=? AND OC.CODCLI=?)+1,1 )) , ?, ?, ? ,? ,? , ?)";
                       ps = con.prepareStatement(sSql);
                       ps.setInt(1,Aplicativo.iCodEmp);
                       ps.setInt(2,ListaCampos.getMasterFilial("VDOBSCLI"));
                       ps.setInt(3,iCodCli);
                       ps.setInt(4,Aplicativo.iCodEmp);
                       ps.setInt(5,ListaCampos.getMasterFilial("VDOBSCLI"));
                       ps.setInt(6,iCodCli);
                       ps.setDate(7,new Date(dtHoje.getTime()) );
                       ps.setTime(8,new Time(dtHoje.getTime()) );
                       ps.setString(9,dl.getTexto());
                       ps.executeUpdate();
                       ps.close();
                       if (!con.getAutoCommit())
                           con.commit();
                   }
                   catch (SQLException e ) {
                       Funcoes.mensagemErro(this,"Não foi possível inserir a observação.\n" +
                              e.getMessage());
                       
                   }
               }
               carregaTabelaObs();
           }
           finally {
               ps = null;
               iCodCli = 0;
               sSql = null;
               dl = null;
               dtHoje = null;
          }
      }
  }
  
  private void exclObs() {
      String sSql = null;
      int iCodCli = 0;
      PreparedStatement ps = null;
      iCodCli = txtCodCli.getVlrInteger().intValue();
      if ( (tbObsData.getSelectedRow()>-1) && (iCodCli!=0) ) {
          if (Funcoes.mensagemConfirma(this,"Confirma exclusão da mensagem?")==JOptionPane.YES_OPTION) {
              try {
                  sSql = "DELETE FROM VDOBSCLI WHERE CODEMP=? AND CODFILIAL=? AND " +
                  		"CODCLI=? AND SEQOBSCLI=?";
                  ps = con.prepareStatement(sSql);
                  ps.setInt(1,Aplicativo.iCodEmp);
                  ps.setInt(2,ListaCampos.getMasterFilial("VDOBSCLI"));
                  ps.setInt(3,iCodCli);
                  ps.setInt(4,Integer.parseInt(tbObsData.getValor(tbObsData.getSelectedRow(),2).toString()));
                  ps.executeUpdate();
                  ps.close();
                  if (!con.getAutoCommit())
                      con.commit();
                  carregaTabelaObs();
              }
              catch (SQLException e ) {
                  Funcoes.mensagemErro(this,"Não foi possível excluir a observação.\n" +
                         e.getMessage());
              }
              finally {
                  ps = null;
                  iCodCli = 0;
                  sSql = null;
              }
          }
      }
  }
  
  private void editObs() {
      String sSql = null;
      int iCodCli = 0;
      PreparedStatement ps = null;
      DLInputText dl = null;
      iCodCli = txtCodCli.getVlrInteger().intValue();
      java.util.Date dtHoje = null;
      if ( (tbObsData.getSelectedRow()>-1) && (iCodCli!=0) ) {
           try {
               dl = new DLInputText(this, "Observação", false);
               dl.setTexto(txaTxtObsCli.getText());
               dl.setVisible(true);
               //dl.show(true);
               if (dl.OK) {
                   try {
                       dtHoje = new java.util.Date();
                       sSql = "UPDATE VDOBSCLI SET DTOBSCLI=?, HOBSCLI=? , TXTOBSCLI=? " +
                       		"WHERE CODEMP=? AND CODFILIAL=? AND CODCLI=? AND SEQOBSCLI=?";
                       ps = con.prepareStatement(sSql);
                       ps.setDate(1,new Date(dtHoje.getTime()) );
                       ps.setTime(2,new Time(dtHoje.getTime()) );
                       ps.setString(3,dl.getTexto());
                       ps.setInt(4,Aplicativo.iCodEmp);
                       ps.setInt(5,ListaCampos.getMasterFilial("VDOBSCLI"));
                       ps.setInt(6,iCodCli);
                       ps.setInt(7,Integer.parseInt(tbObsData.getValor(tbObsData.getSelectedRow(),2).toString()));
                       ps.executeUpdate();
                       ps.close();
                       if (!con.getAutoCommit())
                           con.commit();
                   }
                   catch (SQLException e ) {
                       Funcoes.mensagemErro(this,"Não foi possível alterar a observação.\n" +
                              e.getMessage());
                       
                   }
               }
               carregaTabelaObs();
           }
           finally {
               ps = null;
               iCodCli = 0;
               sSql = null;
               dl = null;
          }
      }
      
  }
  
  private boolean duploCNPJ() {
          boolean bRetorno = false;
          String sSQL = "SELECT CNPJCLI FROM VDCLIENTE WHERE CNPJCLI=?";
          try {
                PreparedStatement ps = con.prepareStatement(sSQL);
                ps.setString(1,txtCnpjCli.getVlrString());
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                        bRetorno = true;
                }
                rs.close();
                ps.close();
          }
          catch(SQLException err) {
			Funcoes.mensagemErro(this,"Erro ao checar CNPJ.\n"+err);
			err.printStackTrace();
          }
          return bRetorno;
  }

  /* (non-Javadoc)
   * @see org.freedom.acao.TabelaSelListener#valorAlterado(org.freedom.acao.TabelaSelEvent)
   */
  public void valorAlterado(TabelaSelEvent evt) {
      if (evt.getTabela()==tbObsData) 
          carregaObs();
  }
  
  
  /* (non-Javadoc)
 * @see org.freedom.acao.CarregaListener#afterCarrega(org.freedom.acao.CarregaEvent)
 */
  public void afterCarrega(CarregaEvent cevt) {
    if (cevt.getListaCampos()==lcCampos) 
       carregaTabelaObs();
    
  }

  public void beforeCarrega(CarregaEvent cevt) {
    
  }

  private void carregaObs() {
      int iCodCli = 0;
      ResultSet rs = null;
      PreparedStatement ps = null;
      String sSql = null;
      try {
          if (!bExecCargaObs) {
              if (tbObsData.getSelectedRow()>-1) {
                  iCodCli = txtCodCli.getVlrInteger().intValue();
                  if (iCodCli!=0) {
                      sSql = "SELECT OC.TXTOBSCLI FROM VDOBSCLI OC " +
          				"WHERE CODEMP=? AND CODFILIAL=? AND CODCLI=? AND "+
          				"SEQOBSCLI=? ";
                      ps = con.prepareStatement(sSql);
                      ps.setInt(1,Aplicativo.iCodEmp);
                      ps.setInt(2,ListaCampos.getMasterFilial("VDOBSCLI"));
                      ps.setInt(3,iCodCli);
                      ps.setInt(4,Integer.parseInt(tbObsData.getValor(tbObsData.getSelectedRow(),2).toString()));
                      rs = ps.executeQuery();
                      if (rs.next()) 
                          txaTxtObsCli.setVlrString(rs.getString("TXTOBSCLI"));
                      rs.close();
                      ps.close();
                      if (!con.getAutoCommit()) 
                          con.commit();
                  }
              }
          }
      }
      catch (Exception e) {
          Funcoes.mensagemErro(this,"Não foi possível carregar dados da tabela " +
            		"observações de cliente (VDOBSCLI).\n"+
                   e.getMessage());
      }
      finally {
          iCodCli = 0;
          rs = null;
          ps = null;
          sSql = null;
      }
  }
  private void carregaTabelaObs() {
      int iCodCli = 0;
      ResultSet rs = null;
      PreparedStatement ps = null;
      String sSql = null;
      Object[] oLinha = null;
      try {
          iCodCli = txtCodCli.getVlrInteger().intValue();
          txaTxtObsCli.setText("");
          bExecCargaObs = true;
          tbObsData.limpa();
          if (iCodCli!=0) {
              sSql = "SELECT OC.DTOBSCLI, OC.HOBSCLI, OC.SEQOBSCLI FROM VDOBSCLI OC "+
              		"WHERE CODEMP=? AND CODFILIAL=? AND CODCLI=? "+
              		"ORDER BY OC.DTOBSCLI DESC, OC.HOBSCLI DESC, OC.SEQOBSCLI DESC";
              ps = con.prepareStatement(sSql);
              ps.setInt(1,Aplicativo.iCodEmp);
              ps.setInt(2,ListaCampos.getMasterFilial("VDOBSCLI"));
              ps.setInt(3,iCodCli);
              rs = ps.executeQuery();
              while (rs.next()) {
                  oLinha = new Object[3];
                  oLinha[0] = rs.getString("DTOBSCLI");
                  oLinha[1] = rs.getString("HOBSCLI");
                  oLinha[2] = rs.getString("SEQOBSCLI");
                  tbObsData.adicLinha(oLinha);
              }
              rs.close();
              ps.close();
              if (!con.getAutoCommit()) 
                  con.commit();
          }
      }
      catch (SQLException e) { 
          Funcoes.mensagemErro(this,"Não foi possível carregar dados da tabela " +
          		"observações de cliente (VDOBSCLI).\n"+
                 e.getMessage());
      }
      finally {
          iCodCli = 0;
          rs = null;
          ps = null;
          sSql = null;
          oLinha = null;
          bExecCargaObs = false;
          if (tbObsData.getNumLinhas()>0)
              tbObsData.setLinhaSel(0);
      }
      
  }
  public void setConveniado(FConveniado telaConv) {
  	this.telaConv = telaConv;
  }
  public void exec(int iCodCli) {
  	txtCodCli.setVlrString(iCodCli+"");
  	lcCampos.carregaDados();
  }
  private boolean[] getPrefere() {
  	boolean[] bRet = new boolean[4];
  	String sSQL = null;
  	PreparedStatement ps = null;
  	ResultSet rs = null;
  	String sVal = null;
  	try {
  		bRet[0] = false;
  		bRet[1] = true;
  		bRet[2] = false;
  		bRet[3] = true;
  		sSQL = "SELECT SETORVENDA,RGCLIOBRIG,CLIMESMOCNPJ,CNPJOBRIGCLI FROM SGPREFERE1 WHERE CODEMP=? AND CODFILIAL=?";
  		//System.out.println(sSQL+":");
  		try {
  			ps = con.prepareStatement(sSQL);
  			ps.setInt(1,Aplicativo.iCodEmp);
  			ps.setInt(2,Aplicativo.iCodFilial);
  			rs = ps.executeQuery();
  			if (rs.next()) {
  				sVal = rs.getString("SetorVenda");
  				if (sVal!= null) {
  					if ("CA".indexOf(sVal) >= 0) //Se tiver C ou A no sVal!
  						bRet[0] = true;
  				}
  				sVal = rs.getString("RGCLIOBRIG");
  				if (sVal!=null) {
  					if (sVal.equals("N")) {
  						bRet[1] = false;
  					}
  				}
  				sVal = rs.getString("CLIMESMOCNPJ");
  				if (sVal!=null) {
  					if (sVal.equals("S")) {
  						bRet[2] = true;
  					}
  				}
  				sVal = rs.getString("CNPJOBRIGCLI");
  				if (sVal!=null) {
  					if (sVal.equals("N")) {
  						bRet[3] = false;
  					}
  				}
  			}
  			rs.close();
  			ps.close();
  			if (!con.getAutoCommit()) {
  				con.commit();
  			}
  		}
  		catch(SQLException err) {
  			Funcoes.mensagemErro(this,"Erro ao verificar preferências!\n"+err.getMessage());
  			err.printStackTrace();
  		}
  	}
  	finally {
  		sSQL = null;
  		ps = null;
  		rs = null;
  		sVal = null;
  	}
  	//	System.out.println("Retornou setor:"+bRet);
  	return bRet;
  }  
  
  private void imprimir(boolean bVisualizar) {
    FAndamento And = null;
    ImprimeOS imp = new ImprimeOS("",con);
    Vector vFiltros = new Vector();
    String sSQL = "";
    int linPag = imp.verifLinPag()-1;
    int iContaReg = 0;
    String sObs = "";
    String sWhere = "";
    String sWhere2 = "";
    String sCodpesq = "";
    String sCodpesqant = "";
    String sOrdem = "";
    String sFrom = "";
    String[] sValores;
    Vector vObs = null;
    imp.setTitulo("Relatório de Clientes");
    imp.montaCab();
    DLRCliente dl = new DLRCliente(this,con);
	dl.setVisible(true);
	if (dl.OK == false) {
	  dl.dispose();
	  return;
	}
	sValores = dl.getValores();
    dl.dispose();
    if (sValores[12].equals("C")) 
    	sOrdem = "1,3,4";
    else
    	sOrdem = "2,3,5";
    if (sValores[1].equals("S")) {
      sObs = ",C1.OBSCLI";
    }
    if (sValores[2].trim().length() > 0) {
      sWhere += " AND C1.RAZCLI >= '"+sValores[2]+"'";
      sWhere2 += " AND C2.RAZCLI >= '"+sValores[2]+"'";
      vFiltros.add("RAZAO MAIORES QUE "+sValores[2].trim());
    }
    if (sValores[3].trim().length() > 0) {
      sWhere += " AND C1.RAZCLI <= '"+sValores[3]+"'";
      sWhere2 += " AND C2.RAZCLI <= '"+sValores[3]+"'";
      vFiltros.add("RAZAO MENORES QUE "+sValores[3].trim());
    }
    if (sValores[4].equals("N")) {
      sWhere += " AND C1.PESSOACLI <> 'F'";
      sWhere2 += " AND C2.PESSOACLI <> 'F'";
      vFiltros.add("PESSOAS JURIDICAS");
    }
    if (sValores[5].length() > 0) {
      System.out.println("CIDADE NO FILTRO:"+sValores[5]);
      sWhere += " AND C1.CIDCLI = '"+sValores[5]+"'";
      sWhere2 += " AND C2.CIDCLI = '"+sValores[5]+"'";
      vFiltros.add("CIDADE = "+sValores[5].trim());
    }
    else {
    	System.out.println("Cidade nula no filtro");
    }
    if (sValores[6].equals("N")) {
      sWhere += " AND C1.PESSOACLI <> 'J'";
      sWhere2 += " AND C2.PESSOACLI <> 'J'";
      vFiltros.add("PESSOAS FISICA");
    }
    if (!sValores[13].trim().equals("")) {
      sWhere += " AND C1.CODVEND = "+sValores[13];
      sWhere2 += " AND C2.CODVEND = "+sValores[13];
      vFiltros.add("REPRES. = "+sValores[13]+"-"+sValores[14]);
    	
    }
    if (sValores[8].length() > 0) {
      if (!bPref[0]) {
      	  sFrom = ",VDVENDEDOR V";
          sWhere += " AND C1.CODEMPVD=V.CODEMP AND C1.CODFILIALVD=V.CODFILIAL AND C1.CODVEND=V.CODVEND AND V.CODSETOR = "+sValores[8];
          sWhere2 += " AND C2.CODEMPVD=V.CODEMP AND C2.CODFILIALVD=V.CODFILIAL AND C2.CODVEND=V.CODVEND AND V.CODSETOR = "+sValores[8];
      }
      else {
        sWhere += " AND C1.CODSETOR = "+sValores[8];
        sWhere2 += " AND C2.CODSETOR = "+sValores[8];
      }
      vFiltros.add("SETOR = "+sValores[9]);
    }
    if (sValores[10].length() > 0) {
      sWhere += " AND C1.CODTIPOCLI = "+sValores[10];
      sWhere2 += " AND C2.CODTIPOCLI = "+sValores[10];
      vFiltros.add("TIPO DE CLIENTE = "+sValores[11]);
    }
    if (sValores[7].equals("C")) {
      sSQL = "SELECT C1.CODCLI,C1.RAZCLI,C1.PESSOACLI,C1.NOMECLI,C1.CONTCLI,C1.ENDCLI,C1.NUMCLI,"+
                    "C1.BAIRCLI,C1.CIDCLI,C1.COMPLCLI,C1.UFCLI,C1.CEPCLI,C1.CNPJCLI,C1.INSCCLI,C1.CPFCLI,C1.RGCLI,"+
                    "C1.FONECLI,C1.FAXCLI,C1.EMAILCLI,C1.CODPESQ"+sObs+" FROM VDCLIENTE C1"+sFrom+
                    " WHERE C1.CODEMP=? AND C1.CODFILIAL=? "+sWhere+" ORDER BY "+sValores[0];
      PreparedStatement ps = null;
      ResultSet rs = null;
      System.out.println("sql é "+sSQL);
      try {
        ps = con.prepareStatement("SELECT COUNT(*) FROM VDCLIENTE"+sFrom+" C1 WHERE C1.CODEMP=? AND C1.CODFILIAL=? "+sWhere);
        ps.setInt(1,Aplicativo.iCodEmp);
        ps.setInt(2,ListaCampos.getMasterFilial("VDCLIENTE"));
        rs = ps.executeQuery();
        rs.next();
        And = new FAndamento("Montando Relatório, Aguarde!",0,rs.getInt(1)-1);
//        rs.close();
//        ps.close();
        if (!con.getAutoCommit())
        	con.commit();
        ps = con.prepareStatement(sSQL);
        ps.setInt(1,Aplicativo.iCodEmp);
        ps.setInt(2,ListaCampos.getMasterFilial("VDCLIENTE"));
        rs = ps.executeQuery();
        imp.limpaPags();
        while ( rs.next() ) {
          if (imp.pRow()==0) {
            imp.impCab(136, false);
            imp.say(imp.pRow()+0,2,"|"+Funcoes.replicate(" ",61)+"Filtrado por:"+Funcoes.replicate(" ",60)+"|");
            for (int i=0;i<vFiltros.size();i++) {            
                    String sTmp = (String)vFiltros.elementAt(i);
                    sTmp = "|"+Funcoes.replicate(" ",(((136-sTmp.length())/2)-1))+sTmp;
                    sTmp += Funcoes.replicate(" ",135-sTmp.length())+"|";
                    imp.say(imp.pRow()+1,0,""+imp.comprimido());
                    imp.say(imp.pRow()+0,2,sTmp);
            }
          }
          imp.say(imp.pRow()+1,0,""+imp.comprimido());
          imp.say(imp.pRow()+0,0,Funcoes.replicate("-",136));
          imp.say(imp.pRow()+1,0,""+imp.comprimido());
          imp.say(imp.pRow()+0,2,"Código:");
          imp.say(imp.pRow()+0,10,rs.getString("CodCli"));
          imp.say(imp.pRow()+0,20,"Razão:");
          imp.say(imp.pRow()+0,27,rs.getString("RazCli") != null ? rs.getString("RazCli").substring(0,30) : "");
                    
          if (rs.getString("CodPesq")!=null && !rs.getString("CodPesq").equals(rs.getString("CodCLi"))){
             imp.say(imp.pRow()+0,57,"Cod.Matriz : "+rs.getString("CodPesq"));	
          }
          
          
          imp.say(imp.pRow()+0,129,"Tipo:");
          imp.say(imp.pRow()+0,135,rs.getString("PessoaCli"));
          imp.say(imp.pRow()+1,0,""+imp.comprimido());
          imp.say(imp.pRow()+0,0,"Nome:");
          imp.say(imp.pRow()+0,7,rs.getString("NomeCli"));
          imp.say(imp.pRow()+0,60,"Contato:");
          imp.say(imp.pRow()+0,70,rs.getString("ContCli"));
          imp.say(imp.pRow()+1,0,""+imp.comprimido());
          imp.say(imp.pRow()+0,0,"Endereço:");
          imp.say(imp.pRow()+0,11,rs.getString("EndCli"));
          imp.say(imp.pRow()+0,62,"N.:");
          imp.say(imp.pRow()+0,67,""+rs.getInt("NumCli"));
          imp.say(imp.pRow()+0,76,"Compl.:");
          imp.say(imp.pRow()+0,85,rs.getString("ComplCli") != null ? rs.getString("ComplCli").trim() : "");
          imp.say(imp.pRow()+0,94,"Bairro:");
          imp.say(imp.pRow()+0,103,rs.getString("BairCli") != null ? rs.getString("BairCli").trim() : "");
          imp.say(imp.pRow()+1,0,""+imp.comprimido());
          imp.say(imp.pRow()+0,0,"Cidade:");
          imp.say(imp.pRow()+0,8,rs.getString("CidCli"));
          imp.say(imp.pRow()+0,88,"UF:");
          imp.say(imp.pRow()+0,93,rs.getString("UfCli"));
          imp.say(imp.pRow()+0,121,"CEP:");
          imp.say(imp.pRow()+0,127,rs.getString("CepCli") != null ? Funcoes.setMascara(rs.getString("CepCli"),"#####-###") : "");
          imp.say(imp.pRow()+1,0,""+imp.comprimido());
          if ((rs.getString("CnpjCli")) != null && (rs.getString("InscCli") != null)) {
            imp.say(imp.pRow()+0,0,"CNPJ:");
            imp.say(imp.pRow()+0,7,Funcoes.setMascara(rs.getString("CnpjCli"),"##.###.###/####-##"));
            imp.say(imp.pRow()+0,50,"IE:");
            if (!rs.getString("InscCli").trim().toUpperCase().equals("ISENTO") && rs.getString("UFCli") != null) {
              Funcoes.vIE(rs.getString("InscCli"),rs.getString("UFCli"));
              imp.say(imp.pRow()+0,55,Funcoes.sIEValida);
            }
          }
          else {
            imp.say(imp.pRow()+0,0,"CPF:");
            imp.say(imp.pRow()+0,6,Funcoes.setMascara(rs.getString("CPFCli"),"###.###.###-##"));
            imp.say(imp.pRow()+0,50,"RG:");
            imp.say(imp.pRow()+0,55,rs.getString("RgCli"));
          }
          imp.say(imp.pRow()+0,80,"Tel:");
          imp.say(imp.pRow()+0,86,rs.getString("FoneCli") != null ? Funcoes.setMascara(rs.getString("FoneCli"),"(####)####-####") : "");
          imp.say(imp.pRow()+0,121,"Fax:");
          imp.say(imp.pRow()+0,127,rs.getString("FaxCli") != null ? Funcoes.setMascara(rs.getString("FaxCli"),"####-####") : "");
          imp.say(imp.pRow()+1,0,""+imp.comprimido());
          imp.say(imp.pRow()+0,0,"Contato:");
          imp.say(imp.pRow()+0,9,rs.getString("ContCli"));
          imp.say(imp.pRow()+0,70,"E-mail:");
          imp.say(imp.pRow()+0,79,rs.getString("EmailCli"));
          if (sObs.length() > 0) {
            imp.say(imp.pRow()+1,0,""+imp.comprimido());
            imp.say(imp.pRow()+0,0,"Obs:");
            imp.say(imp.pRow()+0,6,rs.getString("ObsCli"));
          }
          imp.say(imp.pRow()+1,0,""+imp.comprimido());
          imp.say(imp.pRow()+0,0,Funcoes.replicate("-",136));
          if (imp.pRow()>=linPag) {
            imp.incPags();
            imp.eject();
          }
          And.atualiza(iContaReg);
          iContaReg++;
        }
        imp.say(imp.pRow()+1,0,""+imp.comprimido());
        imp.say(imp.pRow()+0,0,Funcoes.replicate("=",136));
        imp.eject();

        imp.fechaGravacao();

//        rs.close();
//        ps.close();
        if (!con.getAutoCommit())
        	con.commit();
        dl.dispose();
        And.dispose();
      }
      catch ( SQLException err ) {
		Funcoes.mensagemErro(this,"Erro consulta tabela de clientes!"+err.getMessage());
	    err.printStackTrace();
      }
    }
    else if (dl.getValores()[7].equals("R")) {
      sSQL = "SELECT C1.CODCLI,C1.RAZCLI,C1.ENDCLI,C1.CIDCLI,C1.FONECLI,C1.CODPESQ"+sObs+
	        " FROM VDCLIENTE C1" +sFrom+
      		" WHERE C1.CODEMP=? AND C1.CODFILIAL=? "+sWhere+" ORDER BY "+dl.getValores()[0];
      System.out.println("sql é "+sSQL);
      PreparedStatement ps = null;
      ResultSet rs = null;
      try {
        ps = con.prepareStatement("SELECT COUNT(*) FROM VDCLIENTE C1 "+sFrom+" WHERE C1.CODEMP=? AND C1.CODFILIAL=? "+sWhere);
        ps.setInt(1,Aplicativo.iCodEmp);
        ps.setInt(2,ListaCampos.getMasterFilial("VDCLIENTE"));
        rs = ps.executeQuery();
        rs.next();
        And = new FAndamento("Montando Relatório, Aguarde!",0,rs.getInt(1)-1);
//        rs.close();
//        ps.close();
        if (!con.getAutoCommit())
        	con.commit();
        ps = con.prepareStatement(sSQL);
        ps.setInt(1,Aplicativo.iCodEmp);
        ps.setInt(2,ListaCampos.getMasterFilial("VDCLIENTE"));
        rs = ps.executeQuery();
        imp.limpaPags();
        while ( rs.next() ) {
          if (imp.pRow()==0) {
            imp.impCab(136, false);
            imp.say(imp.pRow()+0,2,"|"+Funcoes.replicate(" ",61)+"Filtrado por:"+Funcoes.replicate(" ",60)+"|");
            for (int i=0;i<vFiltros.size();i++) {            
                    String sTmp = (String)vFiltros.elementAt(i);
                    sTmp = "|"+Funcoes.replicate(" ",(((136-sTmp.length())/2)-1))+sTmp;
                    sTmp += Funcoes.replicate(" ",135-sTmp.length())+"|";
                    imp.say(imp.pRow()+1,0,""+imp.comprimido());
                    imp.say(imp.pRow()+0,2,sTmp);
            }
            imp.say(imp.pRow()+1,0,""+imp.comprimido());
            imp.say(imp.pRow()+0,0,Funcoes.replicate("-",136));
            imp.say(imp.pRow()+1,0,""+imp.comprimido());
            imp.say(imp.pRow()+0,0,"|");
            imp.say(imp.pRow()+0,4,"Código");
            imp.say(imp.pRow()+0,12,"|");
            imp.say(imp.pRow()+0,14,"Razão:");
            imp.say(imp.pRow()+0,46,"|");
            imp.say(imp.pRow()+0,49,"Matriz");
            imp.say(imp.pRow()+0,57,"|");
            imp.say(imp.pRow()+0,59,"Endereço:");
            imp.say(imp.pRow()+0,93,"|");
            imp.say(imp.pRow()+0,96,"Cidade:");
            imp.say(imp.pRow()+0,117,"|");
            imp.say(imp.pRow()+0,120,"Tel:");
            imp.say(imp.pRow()+0,136,"|");
            imp.say(imp.pRow()+1,0,""+imp.comprimido());
            imp.say(imp.pRow()+0,0,Funcoes.replicate("-",136));
          }
          imp.say(imp.pRow()+1,0,""+imp.comprimido());
          imp.say(imp.pRow()+0,0,"|");
          imp.say(imp.pRow()+0,4,rs.getString("CodCli"));
          imp.say(imp.pRow()+0,12,"|");
          imp.say(imp.pRow()+0,14,rs.getString("RazCli") != null ? rs.getString("RazCli").substring(0,30) : "");
          imp.say(imp.pRow()+0,46,"|");
          if (rs.getString("CodPesq")!=null && !rs.getString("CodPesq").equals(rs.getString("CodCLi"))){
          	imp.say(imp.pRow()+0,49,""+rs.getString("CodPesq"));
          }
          imp.say(imp.pRow()+0,57,"|");
          imp.say(imp.pRow()+0,59,rs.getString("EndCli") != null ? rs.getString("EndCli").substring(0,30) : "");
          imp.say(imp.pRow()+0,93,"|");
          imp.say(imp.pRow()+0,96,rs.getString("CidCli") != null ? rs.getString("CidCli").substring(0,20) : "");
          imp.say(imp.pRow()+0,117,"|");
          imp.say(imp.pRow()+0,120,rs.getString("FoneCli") != null ? Funcoes.setMascara(rs.getString("FoneCli"),"(####)####-####") : "");
          imp.say(imp.pRow()+0,136,"|");
          if (!sObs.equals("")) {
          	 vObs = Funcoes.quebraLinha(Funcoes.stringToVector(rs.getString("ObsCli")),118);
          	 for (int i=0; i<vObs.size(); i++) {
                imp.say(imp.pRow()+1,0,""+imp.comprimido());
                imp.say(imp.pRow()+0,0,"|");
                imp.say(imp.pRow()+0,14,vObs.elementAt(i).toString());
                imp.say(imp.pRow()+0,136,"|");
                if (imp.pRow()>=linPag) {
                    imp.incPags();
                    imp.eject();
                }
          	 }
          }
          if (imp.pRow()>=linPag) {
            imp.incPags();
            imp.eject();
          }
          And.atualiza(iContaReg);
          iContaReg++;
        }
        imp.say(imp.pRow()+1,0,""+imp.comprimido());
        imp.say(imp.pRow()+0,0,Funcoes.replicate("=",136));
        imp.eject();

        imp.fechaGravacao();

//        rs.close();
//        ps.close();
        if (!con.getAutoCommit())
        	con.commit();
        dl.dispose();
        And.dispose();
      }
      catch ( SQLException err ) {
		Funcoes.mensagemErro(this,"Erro consulta tabela de contatos!"+err.getMessage());
		err.printStackTrace();
      }
    }
    else if  (dl.getValores()[7].equals("A")) {
    	 
    	sSQL="SELECT C1.CODPESQ,C1.RAZCLI RAZMATRIZ,'A' TIPO,C1.CODCLI,C1.RAZCLI,C1.ENDCLI,C1.CIDCLI,C1.FONECLI " +
    			"FROM VDCLIENTE C1"+sFrom+" WHERE C1.CODCLI=C1.CODPESQ " +
    				" AND C1.CODEMP=? AND C1.CODFILIAL=? " +sWhere+ 
					" UNION SELECT C2.CODPESQ,(SELECT C3.RAZCLI FROM VDCLIENTE C3 WHERE C3.CODCLI=C2.CODPESQ) RAZMATRIZ,'B' TIPO,C2.CODCLI,C2.RAZCLI,"+
                    "C2.ENDCLI,C2.CIDCLI,C2.FONECLI FROM VDCLIENTE C2"+sFrom+" WHERE NOT C2.CODCLI=C2.CODPESQ AND " +
                    "C2.CODEMP=? AND C2.CODFILIAL=? "+sWhere2 + " ORDER BY "+sOrdem;
    	System.out.println(sSQL);
            
        try {
        	PreparedStatement  ps = con.prepareStatement(sSQL);
            ps.setInt(1,Aplicativo.iCodEmp);
            ps.setInt(2,ListaCampos.getMasterFilial("VDCLIENTE"));
            ps.setInt(3,Aplicativo.iCodEmp);
            ps.setInt(4,ListaCampos.getMasterFilial("VDCLIENTE"));
        	ResultSet rs =ps.executeQuery();
        	imp.limpaPags();
        	while ( rs.next() ) {
    			sCodpesq = rs.getInt(1)+"";
        		if (sCodpesqant.equals("")) {
        			sCodpesqant = sCodpesq;
        		}
				
        		if (imp.pRow()==0) {
        			imp.impCab(136, false);
        			imp.say(imp.pRow()+0,2,"|"+Funcoes.replicate(" ",61)+"Filtrado por:"+Funcoes.replicate(" ",60)+"|");
        			for (int i=0;i<vFiltros.size();i++) {            
                       String sTmp = (String)vFiltros.elementAt(i);
                       sTmp = "|"+Funcoes.replicate(" ",(((136-sTmp.length())/2)-1))+sTmp;
                       sTmp += Funcoes.replicate(" ",135-sTmp.length())+"|";
                       imp.say(imp.pRow()+1,0,""+imp.comprimido());
                       imp.say(imp.pRow()+0,2,sTmp);
        	       			
        			}                 
        	     
        			imp.say(imp.pRow()+1,0,""+imp.comprimido());
                    imp.say(imp.pRow()+0,0,Funcoes.replicate("-",136));
                    imp.say(imp.pRow()+1,0,""+imp.comprimido());
                    imp.say(imp.pRow()+0,0,"|"); 
        		    imp.say(imp.pRow()+0,2,"Codigo");
        		    imp.say(imp.pRow()+0,12,"|");
        	        imp.say(imp.pRow()+0,14,"Nome");
        	        imp.say(imp.pRow()+0,58,"|");
        	        imp.say(imp.pRow()+0,60,"Endereco");
        	        imp.say(imp.pRow()+0,101,"|");
        	        imp.say(imp.pRow()+0,103,"Cidade");
        	        imp.say(imp.pRow()+0,115,"|");
        	        imp.say(imp.pRow()+0,120,"Telefone");
        	        imp.say(imp.pRow()+0,136,"|");
        	       	imp.say(imp.pRow()+1,0,""+imp.comprimido());
        	        imp.say(imp.pRow()+0,0,Funcoes.replicate("-",136));
        		}
        		else {
        			if (!sCodpesq.equals(sCodpesqant)) {
        				imp.say(imp.pRow()+1,0,""+imp.comprimido());
        				imp.say(imp.pRow()+0,0,Funcoes.replicate("-",136));
        				
        			}
        		}
      		    if (rs.getString(3).equals("A")){
        		   imp.say(imp.pRow()+1,0,""+imp.comprimido()); 
        		   imp.say(imp.pRow()+0,0,"|"); 
        		   imp.say(imp.pRow()+0,2,rs.getString("CodCli")); 
        		   imp.say(imp.pRow()+0,12,"|");
        		   imp.say(imp.pRow()+0,14,rs.getString("RazCli") != null ? rs.getString("RazCli").substring(0,30) : "");
                   imp.say(imp.pRow()+0,47,"(M)");                           		    
        		   imp.say(imp.pRow()+0,58,"|");
        		   imp.say(imp.pRow()+0,59,rs.getString("EndCli") != null ? rs.getString("EndCli").substring(0,30) : "");
        		   imp.say(imp.pRow()+0,101,"|");
        		   imp.say(imp.pRow()+0,103,rs.getString("CidCli") != null ? rs.getString("CidCli").substring(0,12) : "");
        		   imp.say(imp.pRow()+0,115,"|");
        		   imp.say(imp.pRow()+0,120,rs.getString("FoneCli") != null ? Funcoes.setMascara(rs.getString("FoneCli"),"(####)####-####") : "");
        		   imp.say(imp.pRow()+0,136,"|");
        		} 
        		else if (!rs.getString(1).equals(rs.getString(4))) {
        		   imp.say(imp.pRow()+1,0,""+imp.comprimido()); 
        		   imp.say(imp.pRow()+0,0,"|"); 
        		   imp.say(imp.pRow()+0,2,rs.getString("CodCli"));  
        		   imp.say(imp.pRow()+0,12,"|");
        		   imp.say(imp.pRow()+0,15,rs.getString("RazCli") != null ? rs.getString("RazCli").substring(0,30) : "");
                   imp.say(imp.pRow()+0,47,"(F)");                           		    
        		   imp.say(imp.pRow()+0,58,"|");
        		   imp.say(imp.pRow()+0,59,rs.getString("EndCli") != null ? rs.getString("EndCli").substring(0,30) : "");
        		   imp.say(imp.pRow()+0,101,"|");
        		   imp.say(imp.pRow()+0,103,rs.getString("CidCli") != null ? rs.getString("CidCli").substring(0,12) : "");
        		   imp.say(imp.pRow()+0,115,"|");
        		   imp.say(imp.pRow()+0,120,rs.getString("FoneCli") != null ? Funcoes.setMascara(rs.getString("FoneCli"),"(####)####-####") : "");
        		   imp.say(imp.pRow()+0,136,"|");
        		     
        		}
        		if (imp.pRow()>=linPag) {
        		   imp.incPags();
        		   imp.eject();
        		}
     			sCodpesqant = sCodpesq;

           } 
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,0,Funcoes.replicate("=",136));
           imp.eject();
           imp.fechaGravacao();
           if (!con.getAutoCommit())
           	  con.commit();
           dl.dispose();
        }
        catch (SQLException err) {
        	Funcoes.mensagemErro(this,"Erro consulta tabela de clientes!"+err.getMessage());
        }
   
    }
    if (bVisualizar) {
      imp.preview(this);
    }
    else {
      imp.print();
    }
  
   }
   
  public void actionPerformed(ActionEvent evt) {
    if (evt.getSource() == btPrevimp) {
        imprimir(true);
        Vector vVal = Funcoes.stringToVector(txaObs.getText());
        int iTam = vVal.size();
        for (int i=0;i<iTam;i++) {
                System.out.println(i+" : "+vVal.elementAt(i));
        }
    }
    else if (evt.getSource() == btImp) 
      imprimir(false);
    else if (evt.getSource() == btAtEntrega){    
    	if (lcCampos.getStatus()!=ListaCampos.LCS_EDIT)
    		lcCampos.edit();
    	txtEndEnt.setVlrString(txtEndCli.getVlrString());
        txtNumEnt.setVlrString(txtNumCli.getVlrString());
        txtComplEnt.setVlrString(txtComplCli.getVlrString());
        txtBairEnt.setVlrString(txtBairCli.getVlrString());
        txtCidEnt.setVlrString(txtCidCli.getVlrString());
        txtCepEnt.setVlrString(txtCepCli.getVlrString());
		txtUFEnt.setVlrString(txtUFCli.getVlrString());
		txtDDDFoneEnt.setVlrString(txtDDDCli.getVlrString());
		txtFoneEnt.setVlrString(txtFoneCli.getVlrString());
		txtDDDFaxEnt.setVlrString(txtDDDFaxCli.getVlrString());
		txtFaxEnt.setVlrString(txtFaxCli.getVlrString());    	    	
    }
	else if (evt.getSource() == btAtCobranca){    	
    	if (lcCampos.getStatus()!=ListaCampos.LCS_EDIT)
    		lcCampos.edit();
		txtEndCob.setVlrString(txtEndCli.getVlrString());
		txtNumCob.setVlrString(txtNumCli.getVlrString());
		txtComplCob.setVlrString(txtComplCli.getVlrString());
		txtBairCob.setVlrString(txtBairCli.getVlrString());
		txtCidCob.setVlrString(txtCidCli.getVlrString());
		txtCepCob.setVlrString(txtCepCli.getVlrString());
		txtUFCob.setVlrString(txtUFCli.getVlrString());
		txtDDDFoneCob.setVlrString(txtDDDCli.getVlrString());
		txtFoneCob.setVlrString(txtFoneCli.getVlrString());
		txtDDDFaxCob.setVlrString(txtDDDFaxCli.getVlrString());
		txtFaxCob.setVlrString(txtFaxCli.getVlrString());    	    	
	}
	else if (evt.getSource()==btExclObs)
	    exclObs();
	else if (evt.getSource()==btEditObs)
	    editObs();
	else if (evt.getSource()==btNovaObs)
	    novaObs();
	else if (evt.getSource()==btGrpCli) {
	    grpCli();
	}
    super.actionPerformed(evt);
  }
  
  private void grpCli() {
     DLGrpCli dl = null;
     int iCodCli = 0;
     int iCodPesq = txtCodPesq.getVlrInteger().intValue();
     if (iCodPesq!=0) {
         try {
             dl = new DLGrpCli(this);
             dl.carregaClientes(con, iCodPesq, txtDescPesq.getVlrString());
             dl.setVisible(true);
             if (dl.OK) {
                 iCodCli = dl.getCodCli();
                 if (iCodCli!=0) {
                     txtCodCli.setVlrInteger(new Integer(iCodCli));
                     lcCampos.carregaDados();
                 }
             }
         }
     	finally {
     	    dl = null;
     	    iCodPesq = 0;
     	    iCodCli = 0;
     	}
     }
  }
  
  public void valorAlterado(RadioGroupEvent rgevt) {
    if (rgPessoa.getVlrString().compareTo("J") == 0) {
      txtCnpjCli.setEnabled(true);
      txtInscCli.setEnabled(true);
      if (bPref[3]) {
      	setBordaReq(txtCnpjCli);
      	setBordaReq(txtInscCli);
      }
      else {
      	setBordaPad(txtCnpjCli);
      	setBordaPad(txtInscCli);
      }
      txtCpfCli.setEnabled(false);
      setBordaPad(txtCpfCli);
      txtRgCli.setEnabled(false);
      setBordaPad(txtRgCli);
      txtSSPCli.setEnabled(false);
      setBordaPad(txtSSPCli);
    }
    else if (rgPessoa.getVlrString().compareTo("F") == 0) {
      txtCnpjCli.setEnabled(false);
      setBordaPad(txtCnpjCli);
      txtInscCli.setEnabled(false);
      setBordaPad(txtInscCli);
      txtCpfCli.setEnabled(true);
      setBordaReq(txtCpfCli);
      txtRgCli.setEnabled(true);
      setBordaReq(txtRgCli);
      txtSSPCli.setEnabled(true);
      setBordaReq(txtSSPCli);
      
      
    }
  }
  public void beforePost(PostEvent pevt) {
    if (rgPessoa.getVlrString().compareTo("F") == 0) {
        return;
    }
    if ((txtCnpjCli.getText().trim().length() < 1) && (bPref[3])) {
      pevt.cancela();
      Funcoes.mensagemInforma(this,"Campo CNPJ é requerido! ! !");
      //Funcoes.mensagemInforma( this,"Campo CNPJ é requerido! ! !");
      txtCnpjCli.requestFocus();
      return;
    }
    if ((txtInscCli.getText().trim().length() < 1) && (bPref[3])) {
      if (Funcoes.mensagemConfirma(this, "Inscrição Estadual em branco! Inserir ISENTO?")==JOptionPane.OK_OPTION ) {
        txtInscCli.setVlrString("ISENTO");
      }
      pevt.cancela();
      txtInscCli.requestFocus();
      return;
    }
    if ((lcCampos.getStatus() == ListaCampos.LCS_INSERT) && (duploCNPJ())) {
    	if (bPref[2]) {
    		if (Funcoes.mensagemConfirma(this,"Este CNPJ já está cadastrado! Salvar mesmo assim?")!=JOptionPane.OK_OPTION) {
    			pevt.cancela();
    			txtCnpjCli.requestFocus();
    			return;
    		}
    	}
    	else {
            pevt.cancela();
            Funcoes.mensagemInforma(this,"Este CNPJ já está cadastrado!");
            //Funcoes.mensagemInforma( this,"Este CNPJ ja está cadastrado!");
            txtCnpjCli.requestFocus();
            return;
    	}
    }
    if (txtInscCli.getText().trim().toUpperCase().compareTo("ISENTO") == 0)
      return;
    if (txtUFCli.getText().trim().length() < 2) {
      pevt.cancela();
      Funcoes.mensagemInforma(this,"Campo UF é requerido! ! !");
      //Funcoes.mensagemInforma( this,"Campo UF é requerido! ! !");
      txtUFCli.requestFocus();
      return;
    }
    if (!Funcoes.vIE(txtInscCli.getText(),txtUFCli.getText())) {
      if (!txtInscCli.getText().trim().equals("")) {
      	pevt.cancela();
      	Funcoes.mensagemInforma(this,"Inscrição Estadual Inválida ! ! !");
      	//Funcoes.mensagemInforma( null,"Inscrição Estadual Inválida ! ! !");
      	txtInscCli.requestFocus();
      	return;
      }
    }
    if (!txtInscCli.getText().trim().equals("")) 
    	txtInscCli.setVlrString(Funcoes.sIEValida);
  }
  public void setVlrConveniado(String sRazcli, String sNomecli, String sEndcli, Integer iNumcli,
    String sComplcli, String sBaircli, String sCidcli, String sCepcli, String sUFcli, String sRgcli, 
    String sCpfcli, String sFonecli, String sFaxcli, String sEmailcli, Integer iCodTipoCli, Integer iCodClasCli ) {
		rgPessoa.setVlrString("F");
    	txtRazCli.setVlrString(sRazcli);
    	txtNomeCli.setVlrString(sNomecli);
    	txtEndCli.setVlrString(sEndcli);
    	txtNumCli.setVlrInteger(iNumcli);
    	txtComplCli.setVlrString(sComplcli);
    	txtBairCli.setVlrString(sBaircli);
    	txtCidCli.setVlrString(sCidcli);
    	txtCepCli.setVlrString(sCepcli);
		txtUFCli.setVlrString(sUFcli);
    	txtRgCli.setVlrString(sRgcli);
    	txtCpfCli.setVlrString(sCpfcli);
    	txtFoneCli.setVlrString(sFonecli);
    	txtFaxCli.setVlrString(sFaxcli);
    	txtEmailCli.setVlrString(sEmailcli);
    	if (iCodTipoCli!=null) {
    	   txtCodTipoCli.setVlrInteger(iCodTipoCli);
    	   lcTipoCli.carregaDados();
    	}
    	if (iCodClasCli!=null) {
    		txtCodClas.setVlrInteger(iCodClasCli);
    		lcClas.carregaDados();
    	}
    	   
  	
  }
  public void setConexao(Connection cn) {
  	super.setConexao(cn);
  	bPref = getPrefere();
    montaTela();	
    lcTipoCli.setConexao(cn);      
	lcTipoFiscCli.setConexao(cn);      
    lcClas.setConexao(cn);      
    lcVend.setConexao(cn);      
    lcPlanoPag.setConexao(cn);      
    lcTran.setConexao(cn);      
    lcTipoCob.setConexao(cn);      
    lcBanco.setConexao(cn);      
    lcPesq.setConexao(cn);
    if (lcSetor != null)
      lcSetor.setConexao(con);
    lcFor.setConexao(con);
    lcCliFor.setConexao(con);
    lcPais.setConexao(con);
  }
  public void stateChanged(ChangeEvent cevt){
	  if (cevt.getSource()==tpn) {
		  if (tpn.getSelectedIndex()==0)
	      txtCodCli.requestFocus();
      else if (tpn.getSelectedIndex()==1)
	      txtEndEnt.requestFocus();
	  else if (tpn.getSelectedIndex()==2)
		  txtEndCob.requestFocus();
	  else if (tpn.getSelectedIndex()==3)
	      txtCodVend.requestFocus();
      else if (tpn.getSelectedIndex()==4)
		  txaObs.requestFocus();
	
	  }
  }	
 
		
  public void afterPost(PostEvent pevt) {
	if ( (rgPessoa.getVlrString().equals("F")) && (pevt.ok) ) {
   	   if (telaConv!=null) {
	      telaConv.setCodcli(txtCodCli.getVlrString(), txtRazCli.getVlrString());
	      this.btSair.doClick();
   	   }
    }
  }
  public void beforeInsert(InsertEvent ievt) {
  	
  };
  public void afterInsert(InsertEvent ievt) {
  	if (ievt.getListaCampos()==lcCampos)
  		cbAtivo.setVlrString("S");
  };

}
