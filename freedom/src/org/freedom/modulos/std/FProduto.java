/**
 * @version 08/12/2000 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FProduto.java <BR>
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
 * Cadastro de produtos
 * 
 */

package org.freedom.modulos.std;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import org.freedom.componentes.JLabelPad;
import javax.swing.JOptionPane;
import org.freedom.componentes.JPanelPad;
import javax.swing.JScrollPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.acao.CheckBoxEvent;
import org.freedom.acao.CheckBoxListener;
import org.freedom.acao.EditEvent;
import org.freedom.acao.EditListener;
import org.freedom.acao.InsertEvent;
import org.freedom.acao.InsertListener;
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
import org.freedom.componentes.PainelImagem;
import org.freedom.componentes.Tabela;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FAndamento;
import org.freedom.telas.FTabDados;

public class FProduto extends FTabDados	implements CheckBoxListener, EditListener, 
		InsertListener, ChangeListener, ActionListener, CarregaListener {
  private JPanelPad pinGeral = new JPanelPad(650,340);
  private JPanelPad pnFatConv = new JPanelPad(JPanelPad.TP_JPANEL,new BorderLayout());
  private JPanelPad pnFor = new JPanelPad(JPanelPad.TP_JPANEL,new BorderLayout());
  private JPanelPad pnCodAltProd = new JPanelPad(JPanelPad.TP_JPANEL,new BorderLayout());
  private JPanelPad pnLote = new JPanelPad(JPanelPad.TP_JPANEL,new BorderLayout());
  private JPanelPad pnFoto = new JPanelPad(JPanelPad.TP_JPANEL,new BorderLayout());
  private JPanelPad pnPreco = new JPanelPad(JPanelPad.TP_JPANEL,new BorderLayout());

  private JTextFieldPad txtCodProd = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 10, 0);
  private JTextFieldPad txtRefProd = new JTextFieldPad(JTextFieldPad.TP_STRING, 13, 0);
  private JTextFieldPad txtCodMoeda = new JTextFieldPad(JTextFieldPad.TP_STRING,4,0);
  private JTextFieldPad txtCodUnid = new JTextFieldPad(JTextFieldPad.TP_STRING,8,0);
  private JTextFieldPad txtCodFor = new JTextFieldPad(JTextFieldPad.TP_INTEGER,5,0);
  private JTextFieldPad txtCodAltProd = new JTextFieldPad(JTextFieldPad.TP_STRING,20,0);
  private JTextFieldPad txtCodFisc = new JTextFieldPad(JTextFieldPad.TP_STRING,13,0);
  private JTextFieldPad txtCodMarca = new JTextFieldPad(JTextFieldPad.TP_STRING,8,0);
  private JTextFieldPad txtCodGrup = new JTextFieldPad(JTextFieldPad.TP_STRING,14,0);
  private JTextFieldPad txtCodAlmox = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtDescProd = new JTextFieldPad(JTextFieldPad.TP_STRING, 50, 0);
  private JTextFieldPad txtDescAuxProd = new JTextFieldPad(JTextFieldPad.TP_STRING, 40, 0);
  private JTextFieldPad txtCodBarProd = new JTextFieldPad(JTextFieldPad.TP_STRING, 13, 0);
  private JTextFieldPad txtCodFabProd = new JTextFieldPad( JTextFieldPad.TP_STRING, 13, 0);
  private JTextFieldPad txtComisProd = new JTextFieldPad(JTextFieldPad.TP_NUMERIC, 6, 2);
  private JTextFieldPad txtPesoLiqProd = new JTextFieldPad(JTextFieldPad.TP_NUMERIC, 10, 3);
  private JTextFieldPad txtPesoBrutProd = new JTextFieldPad(JTextFieldPad.TP_NUMERIC, 10, 3);
  private JTextFieldPad txtQtdMinProd = new JTextFieldPad(JTextFieldPad.TP_NUMERIC, 15, 3);
  private JTextFieldPad txtQtdMaxProd = new JTextFieldPad(JTextFieldPad.TP_NUMERIC, 15, 3);
  private JTextFieldPad txtLocalProd = new JTextFieldPad(JTextFieldPad.TP_STRING, 15, 0);
  private JTextFieldPad txtCustoMPMProd = new JTextFieldPad(JTextFieldPad.TP_NUMERIC, 15, 3);
  private JTextFieldPad txtCustoPEPSProd = new JTextFieldPad(JTextFieldPad.TP_NUMERIC,15,3);
  private JTextFieldPad txtSldProd = new JTextFieldPad(JTextFieldPad.TP_NUMERIC, 15, 3);
  private JTextFieldPad txtDtUltCpProd = new JTextFieldPad(JTextFieldPad.TP_DATE, 10, 0);
  private JTextFieldPad txtSldConsigProd = new JTextFieldPad(JTextFieldPad.TP_NUMERIC, 15, 3);
  private JTextFieldPad txtSldResProd = new JTextFieldPad(JTextFieldPad.TP_NUMERIC, 15, 3);
  private JTextFieldPad txtSldLiqProd = new JTextFieldPad(JTextFieldPad.TP_NUMERIC, 15, 3);
  private JTextFieldPad txtPrecoBaseProd = new JTextFieldPad(JTextFieldPad.TP_NUMERIC, 15, 3);
  private JTextFieldPad txtUnidFat = new JTextFieldPad(JTextFieldPad.TP_STRING,4,0);
  private JTextFieldPad txtFatConv = new JTextFieldPad(JTextFieldPad.TP_NUMERIC,10,3);
  private JTextFieldPad txtCodLote = new JTextFieldPad(JTextFieldPad.TP_STRING, 13, 0);
  private JTextFieldPad txtDiniLote = new JTextFieldPad(JTextFieldPad.TP_DATE, 10, 0);
  private JTextFieldPad txtVenctoLote = new JTextFieldPad(JTextFieldPad.TP_DATE, 10, 0);
  private JTextFieldPad txtSldLote = new JTextFieldPad(JTextFieldPad.TP_NUMERIC, 15, 3);
  private JTextFieldPad txtSldResLote = new JTextFieldPad(JTextFieldPad.TP_NUMERIC, 15, 3);
  private JTextFieldPad txtSldConsigLote = new JTextFieldPad(JTextFieldPad.TP_NUMERIC, 15, 3);
  private JTextFieldPad txtSldLiqLote = new JTextFieldPad(JTextFieldPad.TP_NUMERIC, 15, 3);
  private JTextFieldPad txtCodFotoProd = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
  private JTextFieldPad txtDescFotoProd = new JTextFieldPad(JTextFieldPad.TP_STRING,40,0);
  private JTextFieldPad txtLargFotoProd = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
  private JTextFieldPad txtAltFotoProd = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
  private JTextFieldPad txtCodPrecoProd = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
  private JTextFieldPad txtCodClasCliPreco = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtCodTabPreco = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtCodPlanoPagPreco = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtPrecoProd = new JTextFieldPad(JTextFieldPad.TP_NUMERIC, 15, 3);
  
  private JTextFieldFK txtDescMoeda = new JTextFieldFK(JTextFieldPad.TP_STRING, 20, 0);
  private JTextFieldFK txtDescUnid = new JTextFieldFK(JTextFieldPad.TP_STRING, 40, 0);
  private JTextFieldPad txtCodProdFor = new JTextFieldPad(JTextFieldPad.TP_STRING, 18, 0);
  private JTextFieldFK txtDescFor = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldFK txtDescFisc = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);
  private JTextFieldFK txtDescMarca = new JTextFieldFK(JTextFieldPad.TP_STRING, 40, 0);
  private JTextFieldFK txtDescGrup = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);
  private JTextFieldFK txtDescAlmox = new JTextFieldFK(JTextFieldPad.TP_STRING, 40, 0);
  private JTextFieldFK txtDescUnidFat = new JTextFieldFK(JTextFieldPad.TP_STRING, 40, 0);
  private JTextFieldFK txtDescClasCliPreco = new JTextFieldFK(JTextFieldPad.TP_STRING, 40, 0);
  private JTextFieldFK txtDescTabPreco = new JTextFieldFK(JTextFieldPad.TP_STRING, 40, 0);
  private JTextFieldFK txtDescPlanoPagPreco = new JTextFieldFK(JTextFieldPad.TP_STRING, 40, 0);
  private Vector vLabsTipo = new Vector();
  private Vector vValsTipo = new Vector();
  private Vector vLabsCV = new Vector();
  private Vector vValsCV = new Vector();
  private Vector vLabsTF = new Vector();
  private Vector vValsTF = new Vector();
  private JRadioGroup rgTipo = null;
  private JRadioGroup rgCV = null;
  private JRadioGroup rgTF = null;
  private JCheckBoxPad cbLote = null;
  private JCheckBoxPad cbAtivo = null;
  private JCheckBoxPad cbVerif = null;
  private Tabela tabFatConv = new Tabela();
  private JScrollPane spnFatConv = new JScrollPane(tabFatConv);
  private Tabela tabFor = new Tabela();
  private Tabela tabCodAltProd = new Tabela();
  private JScrollPane spnFor = new JScrollPane(tabFor);
  private JScrollPane spnCodAltProd = new JScrollPane(tabCodAltProd);
  private Tabela tabLote = new Tabela();
  private JScrollPane spnLote = new JScrollPane(tabLote);
  private Tabela tabFoto = new Tabela();
  private JScrollPane spnFoto = new JScrollPane(tabFoto);
  private Tabela tabPreco = new Tabela();
  private JScrollPane spnPreco = new JScrollPane(tabPreco);
  private JPanelPad pinRodFatConv = new JPanelPad(650,80);
  private JPanelPad pinRodFor = new JPanelPad(650,80);
  private JPanelPad pinRodCodAltProd = new JPanelPad(650,80);
  private JPanelPad pinRodLote = new JPanelPad(650,120);
  private JPanelPad pinRodFoto = new JPanelPad(650,170);
  private JPanelPad pinRodPreco = new JPanelPad(650,120);
  private JPanelPad pnDesc = new JPanelPad(JPanelPad.TP_JPANEL,new GridLayout(1,1));
  private JTextAreaPad txaDescComp = new JTextAreaPad();
  private JScrollPane spnDesc = new JScrollPane(txaDescComp);
  private ListaCampos lcMoeda = new ListaCampos(this,"MA");
  private ListaCampos lcUnid = new ListaCampos(this,"UD");
  private ListaCampos lcFisc = new ListaCampos(this,"FC");
  private ListaCampos lcMarca = new ListaCampos(this,"MC");
  private ListaCampos lcGrup = new ListaCampos(this,"GP");
  private ListaCampos lcAlmox = new ListaCampos(this,"AX");
  private ListaCampos lcFatConv = new ListaCampos(this);
  private ListaCampos lcFor = new ListaCampos(this);
  private ListaCampos lcCodAltProd = new ListaCampos(this,"");
  private ListaCampos lcUnidFat = new ListaCampos(this);
  private ListaCampos lcForFK = new ListaCampos(this);
  private ListaCampos lcLote = new ListaCampos(this);
  private ListaCampos lcFoto = new ListaCampos(this);
  private ListaCampos lcPreco = new ListaCampos(this);
  private ListaCampos lcClasCliPreco = new ListaCampos(this,"CC");
  private ListaCampos lcTabPreco = new ListaCampos(this,"TB");
  private ListaCampos lcPlanoPagPreco = new ListaCampos(this,"PG");
  private Navegador navFatConv = new Navegador(true);
  private Navegador navFor = new Navegador(true);
  private Navegador navLote = new Navegador(true);
  private Navegador navFoto = new Navegador(true);
  private Navegador navPreco = new Navegador(true);
  private Navegador navCodAltProd = new Navegador(true);
  private JButton btExp = new JButton(Icone.novo("btExportar.gif"));
  private PainelImagem imFotoProd = new PainelImagem(65000);
  private String[] sPrefs = null;
 
  public FProduto() {
    setTitulo("Cadastro de Produtos");
    setAtribos(30,10,700,430);

    lcFatConv.setMaster(lcCampos);
    lcCampos.adicDetalhe(lcFatConv);
	lcFatConv.setTabela(tabFatConv);
	lcFor.setMaster(lcCampos);
	lcCodAltProd.setMaster(lcCampos);
	lcCampos.adicDetalhe(lcFor);
	lcCampos.adicDetalhe(lcCodAltProd);
    lcFor.setTabela(tabFor);
    lcCodAltProd.setTabela(tabCodAltProd);
    lcLote.setMaster(lcCampos);
    lcCampos.adicDetalhe(lcLote);
    lcLote.setTabela(tabLote);
    lcFoto.setMaster(lcCampos);
    lcCampos.adicDetalhe(lcFoto);
    lcFoto.setTabela(tabFoto);
    lcPreco.setMaster(lcCampos);
    lcCampos.adicDetalhe(lcPreco); 
    lcPreco.setTabela(tabPreco);

	lcCampos.addInsertListener(this);
	lcCampos.addCarregaListener(this);
    lcFoto.addEditListener(this);
    lcFoto.addInsertListener(this);

    setPainel(pinGeral);
    adicTab("Geral",pinGeral);

    btExp.setToolTipText("Exportar produto");
    
    lcMoeda.add(new GuardaCampo( txtCodMoeda, "CodMoeda", "Cód.moeda", ListaCampos.DB_PK,true));
    lcMoeda.add(new GuardaCampo( txtDescMoeda, "SingMoeda", "Descrição da moeda",  ListaCampos.DB_SI, false));
    lcMoeda.montaSql(false, "MOEDA", "FN");    
    lcMoeda.setReadOnly(true);
    lcMoeda.setQueryCommit(false);
    txtCodMoeda.setTabelaExterna(lcMoeda);
    
    lcUnid.add(new GuardaCampo( txtCodUnid, "CodUnid", "Cód.unid.",  ListaCampos.DB_PK, true));
    lcUnid.add(new GuardaCampo( txtDescUnid, "DescUnid", "Descrição da unidade",  ListaCampos.DB_SI, false));
    lcUnid.montaSql(false, "UNIDADE", "EQ");    
    lcUnid.setReadOnly(true);
    lcUnid.setQueryCommit(false);
    txtCodUnid.setTabelaExterna(lcUnid);
    
    lcMarca.add(new GuardaCampo( txtCodMarca, "CodMarca", "Cód.marca",  ListaCampos.DB_PK, true));
    lcMarca.add(new GuardaCampo( txtDescMarca, "DescMarca", "Descrição da marca",  ListaCampos.DB_SI, false));
    lcMarca.montaSql(false, "MARCA", "EQ");    
    lcMarca.setReadOnly(true);
    lcMarca.setQueryCommit(false);
    txtCodMarca.setTabelaExterna(lcMarca);

    lcFisc.add(new GuardaCampo( txtCodFisc, "CodFisc", "Cód.c.fisc.",  ListaCampos.DB_PK, true));
    lcFisc.add(new GuardaCampo( txtDescFisc, "DescFisc", "Descrição da classificação fiscal",  ListaCampos.DB_SI, false));
    lcFisc.montaSql(false, "CLFISCAL", "LF");
    lcFisc.setReadOnly(true);
    lcFisc.setQueryCommit(false);
    txtCodFisc.setTabelaExterna(lcFisc);
    
    lcGrup.add(new GuardaCampo( txtCodGrup, "CodGrup", "Cód.grupo",  ListaCampos.DB_PK, true));
    lcGrup.add(new GuardaCampo( txtDescGrup, "DescGrup", "Descrição do grupo",  ListaCampos.DB_SI, false));
    lcGrup.montaSql(false, "GRUPO", "EQ");    
    lcGrup.setReadOnly(true);
    lcGrup.setQueryCommit(false);
    txtCodGrup.setTabelaExterna(lcGrup);
    
    lcAlmox.add(new GuardaCampo( txtCodAlmox, "CodAlmox", "Cód.almox.",  ListaCampos.DB_PK, true));
    lcAlmox.add(new GuardaCampo( txtDescAlmox, "DescAlmox", "Descrição do almoxarifado",  ListaCampos.DB_SI, false));
    lcAlmox.montaSql(false, "ALMOX", "EQ");
    lcAlmox.setReadOnly(true);
    lcAlmox.setQueryCommit(false);
    txtCodAlmox.setTabelaExterna(lcAlmox);

    vValsTipo.addElement("P");
    vValsTipo.addElement("S");
	vValsTipo.addElement("F");
	vValsTipo.addElement("M");
	vValsTipo.addElement("O");
	vValsTipo.addElement("C");
    vLabsTipo.addElement("Comércio");
    vLabsTipo.addElement("Serviço");
	vLabsTipo.addElement("Fabricação");
	vLabsTipo.addElement("Mat.prima");
	vLabsTipo.addElement("Patrimonio");
	vLabsTipo.addElement("Consumo");
	rgTipo = new JRadioGroup(6,1,vLabsTipo,vValsTipo);
    rgTipo.setVlrString("P");

    vValsCV.addElement("C");
    vValsCV.addElement("V");
    vValsCV.addElement("A");
    vLabsCV.addElement("Compra");
    vLabsCV.addElement("Venda");
    vLabsCV.addElement("Ambos");
    rgCV = new JRadioGroup(1,3,vLabsCV,vValsCV);
    rgCV.setVlrString("V");
    
    vValsTF.addElement("P");
    vValsTF.addElement("M");
    vValsTF.addElement("N");
    vValsTF.addElement("G");
    vLabsTF.addElement("Pequena");
    vLabsTF.addElement("Média");
    vLabsTF.addElement("Natural");
    vLabsTF.addElement("Grande");
    rgTF = new JRadioGroup(1,4,vLabsTF,vValsTF);
    rgTF.setVlrString("P");
    
    
    cbLote = new JCheckBoxPad("Lote","S","N");
    cbLote.setVlrString("N");
    cbLote.addCheckBoxListener(this);
    
    cbAtivo = new JCheckBoxPad("Ativo","S","N");
    cbAtivo.setVlrString("S");
    cbVerif = new JCheckBoxPad("Senha","S","N");
    cbVerif.setVlrString("S");
    
    txtCustoMPMProd.setSoLeitura(true);    
    txtCustoPEPSProd.setSoLeitura(true);    
    txtSldProd.setSoLeitura(true);
    txtSldResProd.setSoLeitura(true);
    txtDtUltCpProd.setSoLeitura(true);
    txtSldConsigProd.setSoLeitura(true);
    txtSldLiqProd.setSoLeitura(true);
    
    btImp.addActionListener(this);
	btPrevimp.addActionListener(this);
	tpn.addChangeListener(this);

         
  }
  public void afterCarrega(CarregaEvent cevt) {
  	String sSQL = null;
  	ResultSet rs = null;
  	PreparedStatement ps = null;
  	if (cevt.getListaCampos()==lcCampos) {
  		if (sPrefs[1].equals("S")) {
			if (txtCodProd.getVlrInteger().intValue()!=0) {
	  			try {
  					sSQL = "SELECT NCUSTOPEPS FROM EQCALCPEPSSP(?,?,?,?,CAST('now' AS date))";
  					ps = con.prepareStatement(sSQL);
  					ps.setInt(1,Aplicativo.iCodEmp);
  					ps.setInt(2,ListaCampos.getMasterFilial("EQPRODUTO"));
  					ps.setInt(3,txtCodProd.getVlrInteger().intValue());
  					ps.setDouble(4,txtSldLiqProd.getVlrDouble().doubleValue());
  					rs = ps.executeQuery();
  					if (rs.next()) {
  						txtCustoPEPSProd.setVlrDouble(new Double(rs.getDouble("NCUSTOPEPS")));
  					}
  					rs.close();
  					ps.close();
  					if (!con.getAutoCommit())
  						con.commit();
  				}
	  			catch (SQLException e) {
	  				Funcoes.mensagemErro(this,"Não foi possível carregar o valor de custo PEPS!\n"+e.getMessage());
	  			}
	  			finally {
	  				rs = null;
	  				ps = null;
	  				sSQL = null;
	  			}
  			}
  		}
  	}
  }
  public void beforeCarrega(CarregaEvent cevt) {
  	
  }
  private void montaTela() {
    adicCampo(txtCodProd, 7, 20, 70, 20, "CodProd", "Cód.prod.", ListaCampos.DB_PK, true);
    adicCampo(txtRefProd, 80, 20, 70, 20, "RefProd", "Referência", ListaCampos.DB_SI, true);
    adicCampo(txtDescProd, 153, 20, 360, 20, "DescProd", "Descrição do produto", ListaCampos.DB_SI, true);
    adicDB(rgTipo, 520, 20, 130, 140, "TipoProd", "Fluxo:",true);
    adicCampo(txtDescAuxProd, 7, 60, 250, 20, "DescAuxProd", "Descrição auxiliar", ListaCampos.DB_SI, false);
    adicCampo(txtCodMoeda, 259, 60, 70, 20, "CodMoeda", "Cód.moeda", ListaCampos.DB_FK, true);
    adicDescFK(txtDescMoeda, 332, 60, 181, 20, "SingMoeda", "Descrição da moeda");
    adicCampo(txtCodBarProd, 7, 100, 125, 20, "CodBarProd", "Código de barras", ListaCampos.DB_SI, true);
    adicCampo(txtCodFabProd, 135, 100, 125, 20, "CodFabProd", "Código do fabricante", ListaCampos.DB_SI, true);
    adicCampo(txtCodAlmox, 263, 100, 70, 20, "CodAlmox", "Cód.almox.", ListaCampos.DB_FK, true);
    adicDescFK(txtDescAlmox, 336, 100, 176, 20, "DescAlmox", "Descrição do almoxarifado");
    adicCampo(txtPesoBrutProd, 7, 140, 90, 20, "PesoBrutProd", "Peso bruto", ListaCampos.DB_SI, true);
    adicCampo(txtPesoLiqProd, 100, 140, 87, 20, "PesoLiqProd", "Peso líquido", ListaCampos.DB_SI, true);
    adicCampo(txtPrecoBaseProd, 190, 140, 97, 20, "PrecoBaseProd", "Preço base", ListaCampos.DB_SI, true);
    adicCampo(txtComisProd, 290, 140, 77, 20, "ComisProd", "% Comissão", ListaCampos.DB_SI, true);
    adicCampo(txtQtdMinProd, 370, 140, 67, 20, "QtdMinProd", "Qtd.min.", ListaCampos.DB_SI, true);
    adicCampo(txtQtdMaxProd, 440, 140, 72, 20, "QtdMaxProd", "Qtd.máx.", ListaCampos.DB_SI, true);
    adicCampo(txtLocalProd, 7, 180, 100, 20, "LocalProd", "Local armz.", ListaCampos.DB_SI, false);
    adicCampo(txtCustoMPMProd, 110, 180, 87, 20, "CustoMPMProd", "Custo MPM", ListaCampos.DB_SI, false);
    adic(new JLabelPad("Custo PEPS"),200,160,87,20);
    adic(txtCustoPEPSProd, 200, 180, 87, 20); // Sem inserir no lista campos
    adicCampo(txtSldProd, 290, 180, 87, 20, "SldProd", "Saldo", ListaCampos.DB_SI, false);
    adicCampo(txtSldResProd, 380, 180, 87, 20, "SldResProd", "Saldo res.", ListaCampos.DB_SI, false);
    adicCampo(txtSldConsigProd, 470, 180, 87, 20, "SldConsigProd", "Saldo consig.", ListaCampos.DB_SI, false);
    adicCampo(txtSldLiqProd, 560, 180, 90, 20, "SldLiqProd", "Saldo liq.", ListaCampos.DB_SI, false);
    adicDB(cbLote, 7, 220, 70, 20, "CLoteProd", "Estoque",true);
    adicDB(cbAtivo, 80, 220, 67, 20, "AtivoProd", "Atividade",true);
    adicDB(cbVerif, 150, 220, 77, 20, "VerifProd", "Abaixo custo",true);
    adicCampo(txtDtUltCpProd, 230, 220, 97, 20, "DtUltCpProd", "Ultima compra", ListaCampos.DB_SI, false);
    adicCampo(txtCodUnid, 330, 220, 77, 20, "CodUnid", "Cód.und.", ListaCampos.DB_FK, txtDescUnid,true);
    adicDescFK(txtDescUnid, 410, 220, 240, 20, "DescUnid", "Descrição da unidade");
    adicCampo(txtCodFisc, 7, 260, 80, 20, "CodFisc", "Cód.fisc.", ListaCampos.DB_FK, txtDescFisc,true);
    adicDescFK(txtDescFisc, 90, 260, 237, 20, "DescFisc", "Descrição da classificação fiscal");
    adicCampo(txtCodMarca, 330, 260, 77, 20, "CodMarca", "Cód.marca", ListaCampos.DB_FK, txtDescMarca,true);
    adicDescFK(txtDescMarca, 410, 260, 240, 20, "DescMarca", "Descrição da marca");
    adicCampo(txtCodGrup, 7, 300, 100, 20, "CodGrup", "Cód.grupo", ListaCampos.DB_FK, txtDescGrup,true);
    adicDescFK(txtDescGrup, 110, 300, 237, 20, "DescGrup", "Descrição do grupo");
    adicDB(rgCV, 350, 300, 260, 30, "CVProd", "Cadastro para:",true);
    adic(btExp, 620, 300, 30, 30);
    

//Decrição completa

	adicTab("Descrição completa", pnDesc);
	adicDBLiv(txaDescComp, "DescCompProd", "Descrição completa", false);
	pnDesc.add(spnDesc);

	setListaCampos(true,"PRODUTO", "EQ");

//	Preço

    setPainel( pinRodPreco, pnPreco);
	adicTab("Preços",pnPreco);
	setListaCampos(lcPreco);
	setNavegador(navPreco);
	pnPreco.add(pinRodPreco, BorderLayout.SOUTH);
	pnPreco.add(spnPreco, BorderLayout.CENTER);

	pinRodPreco.adic(navPreco,0,90,270,25);

	lcClasCliPreco.add(new GuardaCampo( txtCodClasCliPreco, "CodClasCli", "Cód.c.cli.", ListaCampos.DB_PK, false));
	lcClasCliPreco.add(new GuardaCampo( txtDescClasCliPreco, "DescClasCli", "Descrição da classificação do cliente", ListaCampos.DB_SI, false));
	lcClasCliPreco.montaSql(false, "CLASCLI", "VD");    
	lcClasCliPreco.setQueryCommit(false);
	lcClasCliPreco.setReadOnly(true);
	txtDescClasCliPreco.setListaCampos(lcClasCliPreco);
	txtCodClasCliPreco.setTabelaExterna(lcClasCliPreco);

	lcTabPreco.add(new GuardaCampo( txtCodTabPreco, "CodTab", "Cód.tab.pç.", ListaCampos.DB_PK, true));
	lcTabPreco.add(new GuardaCampo( txtDescTabPreco, "DescTab", "Descrição da tabela de preço", ListaCampos.DB_SI, false));
	lcTabPreco.montaSql(false, "TABPRECO", "VD");
	lcTabPreco.setReadOnly(true);
	lcTabPreco.setQueryCommit(false);
	txtDescTabPreco.setListaCampos(lcTabPreco);
	txtCodTabPreco.setTabelaExterna(lcTabPreco);

	lcPlanoPagPreco.add(new GuardaCampo( txtCodPlanoPagPreco, "CodPlanoPag", "Cód.p.pag.", ListaCampos.DB_PK, true));
	lcPlanoPagPreco.add(new GuardaCampo( txtDescPlanoPagPreco, "DescPlanoPag", "Descrição do plano de pagamento", ListaCampos.DB_SI, false));
	lcPlanoPagPreco.montaSql(false, "PLANOPAG", "FN");
	lcPlanoPagPreco.setReadOnly(true);
	lcPlanoPagPreco.setQueryCommit(false);
	txtDescPlanoPagPreco.setListaCampos(lcPlanoPagPreco);
	txtCodPlanoPagPreco.setTabelaExterna(lcPlanoPagPreco);

	adicCampo(txtCodPrecoProd, 7, 20, 80, 20, "CodPrecoProd", "Cód.pç.prod.", ListaCampos.DB_PK, true);
	adicCampo(txtCodClasCliPreco, 90, 20, 67, 20, "CodClasCli", "Cód.c.cli.", ListaCampos.DB_FK, txtDescClasCliPreco,false);
	adicDescFK(txtDescClasCliPreco, 160, 20, 217, 20, "DescClasCli", "Descrição da classificação do cliente");
	adicCampo(txtCodTabPreco, 380, 20, 77, 20, "CodTab", "Cód.tab.pc.", ListaCampos.DB_FK, txtDescTabPreco,true);
	adicDescFK(txtDescTabPreco, 460, 20, 190, 20, "DescTab", "Descrição da tab. de preços");
	adicCampo(txtCodPlanoPagPreco, 7, 60, 80, 20, "CodPlanoPag", "Cód.p.pag.", ListaCampos.DB_FK, txtDescPlanoPagPreco,true);
	adicDescFK(txtDescPlanoPagPreco, 90, 60, 197, 20, "DescPlanoPag", "Descrição do plano de pagamento" );
	adicCampo(txtPrecoProd, 290, 60, 110, 20, "PrecoProd", "Preço", ListaCampos.DB_SI, true);
	setListaCampos( true, "PRECOPROD", "VD");
	lcPreco.setOrdem("CodPrecoProd");
	lcPreco.setQueryInsert(false);
	lcPreco.setQueryCommit(false);
	lcPreco.montaTab();
	tabPreco.setTamColuna(65,0);
	tabPreco.setTamColuna(60,1);
	tabPreco.setTamColuna(110,2);
	tabPreco.setTamColuna(60,3);
	tabPreco.setTamColuna(110,4);
	tabPreco.setTamColuna(60,5);
	tabPreco.setTamColuna(110,6);
	tabPreco.setTamColuna(75,7);

//FatConv

    setPainel( pinRodFatConv, pnFatConv);
    adicTab("Fatores de conversão",pnFatConv);
    setListaCampos(lcFatConv);
    setNavegador(navFatConv);
    pnFatConv.add(pinRodFatConv, BorderLayout.SOUTH);
    pnFatConv.add(spnFatConv, BorderLayout.CENTER);


    pinRodFatConv.adic(navFatConv,0,50,270,25);
   
    lcUnidFat.setUsaME(false);
    lcUnidFat.add(new GuardaCampo( txtUnidFat, "CodUnid", "Cód.unid.", ListaCampos.DB_PK, true));
    lcUnidFat.add(new GuardaCampo( txtDescUnidFat, "DescUnid", "Descrição da unidade", ListaCampos.DB_SI, false));
    lcUnidFat.montaSql(false, "UNIDADE", "EQ");
    lcUnidFat.setReadOnly(true);
    lcUnidFat.setQueryCommit(false);
    txtDescUnidFat.setListaCampos(lcUnidFat);
    txtUnidFat.setTabelaExterna(lcUnidFat);
    
    adicCampo(txtUnidFat, 7, 20, 80, 20, "CodUnid", "Cód.unid.", ListaCampos.DB_PF, txtDescUnidFat,true);
    adicDescFK(txtDescUnidFat, 90, 20, 150, 20, "DescUnid", "Descrição da unidade" );
    adicCampo(txtFatConv, 243, 20, 80, 20, "FatConv", "Fator de conv.", ListaCampos.DB_SI, true);
    setListaCampos( false, "FATCONV", "EQ");
    lcFatConv.setOrdem("CodUnid");
    lcFatConv.montaTab();
    lcFatConv.setQueryInsert(false);
    lcFatConv.setQueryCommit(false);
    tabFatConv.setTamColuna(120,1);
    
//	Fornecedor
	  setPainel( pinRodFor, pnFor);
	  adicTab("Fornecedores",pnFor);
	  setListaCampos(lcFor);
    
	  navFor.setAtivo(6,false);

	  setNavegador(navFor);
	  pnFor.add(pinRodFor, BorderLayout.SOUTH);
	  pnFor.add(spnFor, BorderLayout.CENTER);


	  pinRodFor.adic(navFor,0,50,270,25);
   
	  lcForFK.setUsaME(false);
	  lcForFK.add(new GuardaCampo( txtCodFor, "CodFor", "Cód.for.", ListaCampos.DB_PK, true));
	  lcForFK.add(new GuardaCampo( txtDescFor, "RazFor", "Razão social do fornecedor", ListaCampos.DB_SI, false));
	  lcForFK.montaSql(false, "FORNECED", "CP");
	  lcForFK.setReadOnly(true);
	  lcForFK.setQueryCommit(false);
	  txtCodFor.setListaCampos(lcForFK);
	  txtCodFor.setTabelaExterna(lcForFK);

	  adicCampo(txtCodFor, 7, 20, 80, 20, "CodFor", "Cód.for.", ListaCampos.DB_PF, txtDescFor, true);
	  adicDescFK(txtDescFor, 90, 20, 300, 20, "RazFor", "Razão social do fornecedor");
	  adicCampo(txtCodProdFor, 400, 20, 105, 20, "RefProdFor", "Cód.prod.for.", ListaCampos.DB_SI, false);
	  setListaCampos( false, "PRODFOR", "CP");
	  lcFor.montaTab();
	  lcFor.setQueryInsert(false);
	  lcFor.setQueryCommit(false);
	  tabFor.setTamColuna(250,1);

//Lote
    setPainel( pinRodLote, pnLote);
    adicTab("Lotes",pnLote);
    setListaCampos(lcLote);
    setNavegador(navLote);
    pnLote.add(pinRodLote, BorderLayout.SOUTH);
    pnLote.add(spnLote, BorderLayout.CENTER);

    pinRodLote.adic(navLote,0,90,270,25);

    txtSldLote.setSoLeitura(true);
    txtSldResLote.setSoLeitura(true);
    txtSldConsigLote.setSoLeitura(true);
    txtSldLiqLote.setSoLeitura(true);

    adicCampo(txtCodLote, 7, 20, 110, 20, "CodLote", "Cód.lote", ListaCampos.DB_PK, true);
    adicCampo(txtDiniLote, 120, 20, 100, 20, "DIniLote", "Data inicial", ListaCampos.DB_SI, false);
    adicCampo(txtVenctoLote, 223, 20, 100, 20, "VenctoLote", "Vencimento", ListaCampos.DB_SI, true);
    adicCampo(txtSldLote, 7, 60, 80, 20, "SldLote", "Saldo", ListaCampos.DB_SI, false);
    adicCampo(txtSldResLote, 90, 60, 80, 20, "SldResLote", "Saldo res.", ListaCampos.DB_SI, false);
    adicCampo(txtSldConsigLote, 173, 60, 80, 20, "SldConsigLote", "Saldo consig.", ListaCampos.DB_SI, false);
    adicCampo(txtSldLiqLote, 256, 60, 80, 20, "SldLiqLote", "Saldo liq.", ListaCampos.DB_SI, false);
    setListaCampos( false, "LOTE", "EQ");
    lcLote.setOrdem("VenctoLote desc");
    lcLote.setQueryInsert(false);
    lcLote.setQueryCommit(false);
    lcLote.montaTab();
    lcLote.setDinWhereAdic("CODLOTE = #N",txtCodProd);
    tabLote.setTamColuna(110,0);
    tabLote.setTamColuna(100,1);
    tabLote.setTamColuna(100,2);
    
//	Codigo alternativo

    setPainel( pinRodCodAltProd, pnCodAltProd);
	adicTab("Cód.altern.",pnCodAltProd);
	setListaCampos(lcCodAltProd);
	setNavegador(navCodAltProd);
	pnCodAltProd.add(pinRodCodAltProd, BorderLayout.SOUTH);
	pnCodAltProd.add(spnCodAltProd, BorderLayout.CENTER);	
	pinRodCodAltProd.adic(navCodAltProd,0,50,270,25);
	navCodAltProd.setAtivo(6,false);
	
	adicCampo(txtCodAltProd, 7, 20, 150, 20, "CodAltProd", "Código alternativo", ListaCampos.DB_PK, null, true);
	setListaCampos( false, "CODALTPROD", "EQ");
	lcCodAltProd.setQueryInsert(true);
	lcCodAltProd.setQueryCommit(false);
	txtCodAltProd.setTabelaExterna(lcCodAltProd);
	lcCodAltProd.montaTab();
	tabCodAltProd.setTamColuna(150,0);        
        
//Fotos 
    setPainel( pinRodFoto, pnFoto);
    adicTab("Fotos",pnFoto);
    setListaCampos(lcFoto);
    setNavegador(navFoto);
    pnFoto.add(pinRodFoto, BorderLayout.SOUTH);
    pnFoto.add(spnFoto, BorderLayout.CENTER);

    pinRodFoto.adic(navFoto,0,140,270,25);

    txtAltFotoProd.setEnabled(false);
    txtLargFotoProd.setEnabled(false);

    adicCampo(txtCodFotoProd, 7, 20, 70, 20, "CodFotoProd", "Nº foto", ListaCampos.DB_PK, true);
    adicCampo(txtDescFotoProd,80, 20 , 250 , 20, "DescFotoProd","Descrição da foto", ListaCampos.DB_SI,true);
    adicDB(rgTF, 7, 60, 323, 30, "TipoFotoProd", "Tamanho:",true);
    adicCampo(txtLargFotoProd, 7, 110, 80, 20, "LargFotoProd", "Largura", ListaCampos.DB_SI, true);
    adicCampo(txtAltFotoProd, 90, 110, 77, 20, "AltFotoProd", "Altura", ListaCampos.DB_SI, true);
    adicDB(imFotoProd, 350, 20, 150, 140, "FotoProd", "Foto: (máx. 63K)",true);
  
    setListaCampos( true, "FOTOPROD", "VD");
    lcFoto.setOrdem("CodFotoProd");
    lcFoto.setQueryInsert(false);
    lcFoto.setQueryCommit(false);
    lcFoto.montaTab();
    tabFoto.setTamColuna(80,0);
    tabFoto.setTamColuna(250,1);
    tabFoto.setTamColuna(80,2);
    tabFoto.setTamColuna(80,3);
    tabFoto.setTamColuna(80,4);
    
	txtCodProd.requestFocus();
	btExp.addActionListener(this);
  }
  private void exportar() {
  	 if (txtCodProd.getVlrInteger().intValue() == 0 || lcCampos.getStatus() != ListaCampos.LCS_SELECT) {
  	 	Funcoes.mensagemInforma(this,"Selecione um produto cadastrado antes!");
  	 	return;
  	 }
  	 String sSQL = "SELECT ICOD FROM EQCOPIAPROD(?,?,?)";
  	 try {
  	 	PreparedStatement ps = con.prepareStatement(sSQL);
  	 	ps.setInt(1,txtCodProd.getVlrInteger().intValue());
  	 	ps.setInt(2,Aplicativo.iCodEmp);
  	 	ps.setInt(3,lcCampos.getCodFilial());
  	 	ResultSet rs = ps.executeQuery();
  	 	if (rs.next()) {
  	 		if (Funcoes.mensagemConfirma(this,"Produto '"+rs.getInt(1)+"' criado com sucesso!\n"+
  	 		                                                       "Gostaria de edita-lo agora?") == JOptionPane.OK_OPTION) {
  	 			txtCodProd.setVlrInteger(new Integer(rs.getInt(1)));
  	 			lcCampos.carregaDados();
  	 		}
  	 	}
  	 	rs.close();
  	 	ps.close();
  	 }
  	 catch (SQLException err) {
  	 	Funcoes.mensagemErro(this,"Erro ao copiar o produto!\n"+err.getMessage());
  	 	err.printStackTrace();
  	 }
  }
  private String[] getPrefs() {
  	 String sRetorno[] = {"",""};
     String sSQL = null;
     PreparedStatement ps = null;
     ResultSet rs = null;
     try {
     	sSQL = "SELECT CODMOEDA,PEPSPROD FROM SGPREFERE1 WHERE CODEMP=? AND CODFILIAL=?";
     	ps = con.prepareStatement(sSQL);
     	ps.setInt(1,Aplicativo.iCodEmp);
  	   	ps.setInt(2, ListaCampos.getMasterFilial("SGPREFERE1"));
  	   	rs = ps.executeQuery();
  	   	if (rs.next()) {
  	   		sRetorno[0] = rs.getString("CODMOEDA");
  	   		sRetorno[1] = rs.getString("PEPSPROD");
  	   	}
  	   	rs.close();
  	   	ps.close();
  	   	if (!con.getAutoCommit())
  	   		con.commit();
     }
     catch (SQLException err) {
     	Funcoes.mensagemErro(this,"Erro ao carregar a tabela PREFERE1!\n"+err.getMessage());
     	err.printStackTrace();
     }
     finally {
     	rs = null;
     	ps = null;
     	sSQL = null;
     }
  	 return sRetorno;
  }
  private void carregaMoeda() {
  	 if (sPrefs!=null) 
      	txtCodMoeda.setVlrString(sPrefs[0]);
  }
  private void imprimir(boolean bVisualizar) {
	FAndamento And = null;
	ImprimeOS imp = new ImprimeOS("",con);
	Vector vFiltros = new Vector();
	int linPag = imp.verifLinPag()-1;
	int iContaReg = 0;
	String sObs = "";
	String sWhere = "";
	String sAnd = " WHERE ";
	String[] sValores; 
	imp.setTitulo("Relatório de Produtos");
	imp.montaCab();
    DLRProduto dl = new DLRProduto(con);
	dl.setVisible(true);
	if (dl.OK == false) {
	  dl.dispose();
	  return;
	}
	sValores = dl.getValores();
	dl.dispose();
	
	if (sValores[1].trim().length() > 0) {
	  sWhere = sWhere+sAnd+"DESCPROD >= '"+sValores[1]+"'";
	  vFiltros.add("PRODUTOS MAIORES QUE "+sValores[1].trim());
	  sAnd = " AND ";
	}
	if (sValores[2].trim().length() > 0) {
	  sWhere = sWhere+sAnd+"DESCPROD <= '"+sValores[2]+"'";
	  vFiltros.add("PRODUTOS MENORES QUE "+sValores[2].trim());
	  sAnd = " AND ";
	}
	if (sValores[3].equals("S")) {
	  sWhere = sWhere+sAnd+"ATIVOPROD='S'";
	  vFiltros.add("PRODUTOS ATIVOS");
	  sAnd = " AND ";
	}
	if (sValores[4].length() > 0) {
	  sWhere = sWhere+sAnd+"CODPROD IN (SELECT CODPROD FROM CPPRODFOR WHERE CODFOR = "+sValores[4]+")";
	  vFiltros.add("FORNECEDOR = "+sValores[4].trim());
	  sAnd = " AND ";
	}
	if (sValores[7].length() > 0) {
	  sWhere = sWhere+sAnd+"CODALMOX = "+sValores[7];
	  vFiltros.add("ALMOXARIFADO = "+sValores[8]);
	  sAnd = " AND ";
	}
	
	if (sValores[9].length() > 0) {
		sWhere = sWhere+sAnd+"CODMARCA = '"+sValores[9]+"'";
		vFiltros.add("MARCA = "+sValores[10]);
		sAnd = " AND ";
	}
		
	if (sValores[6].equals("C")) {
	  String sSQL = "SELECT CODPROD,REFPROD, CODALMOX, DESCPROD,CODUNID, CODMARCA,TIPOPROD,CODGRUP,CODBARPROD,"+
					"CODFABPROD, COMISPROD, PESOLIQPROD, PESOBRUTPROD, QTDMINPROD, QTDMAXPROD, CLOTEPROD, CUSTOMPMPROD,"+
					"CUSTOPEPSPROD, PRECOBASEPROD, SLDPROD, SLDRESPROD, SLDCONSIGPROD, SLDLIQPROD, DTULTCPPROD, QTDULTCPPROD"+sObs+" FROM EQPRODUTO"+sWhere+" ORDER BY "+sValores[0];
	  PreparedStatement ps = null;
	  ResultSet rs = null;
	  try {
		ps = con.prepareStatement("SELECT COUNT(*) FROM EQPRODUTO"+sWhere);
		rs = ps.executeQuery();
		rs.next();
		And = new FAndamento("Montando Relatório, Aguarde!",0,rs.getInt(1)-1);

		if (!con.getAutoCommit())
			con.commit();
		ps = con.prepareStatement(sSQL);
		rs = ps.executeQuery();
		imp.limpaPags();
		while ( rs.next() ) {
		  if (imp.pRow()==0) {
			imp.impCab(136, false);
			imp.say(imp.pRow()+0,0,""+imp.comprimido());
			imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate(" ",61)+"Filtrado por:"+Funcoes.replicate(" ",60)+"|");
			for (int i=0;i<vFiltros.size();i++) {            
					String sTmp = (String)vFiltros.elementAt(i);
					sTmp = "|"+Funcoes.replicate(" ",(((136-sTmp.length())/2)-1))+sTmp;
					sTmp += Funcoes.replicate(" ",135-sTmp.length())+"|";
					imp.say(imp.pRow()+1,0,""+imp.comprimido());
					imp.say(imp.pRow()+0,0,sTmp);
			}
		  }
		  
		  imp.say(imp.pRow()+1,0,""+imp.comprimido());
		  imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",134)+"|");
		  imp.say(imp.pRow()+1,0,""+imp.comprimido());
		  imp.say(imp.pRow()+0,0,"|");
		  imp.say(imp.pRow()+0,2,"Código:");
		  imp.say(imp.pRow()+0,12,rs.getString("CodProd"));
		  imp.say(imp.pRow()+0,22,"Ref.:");
		  imp.say(imp.pRow()+0,28,rs.getString("RefProd"));
		  imp.say(imp.pRow()+0,42,"Descrição:");
		  imp.say(imp.pRow()+0,53,rs.getString("DescProd"));
		  imp.say(imp.pRow()+0,104,"Cod.Bar.:");
		  imp.say(imp.pRow()+0,115,rs.getString("codBarProd"));
		  imp.say(imp.pRow()+0,136,"|");
		  imp.say(imp.pRow()+1,0,""+imp.comprimido());
		  imp.say(imp.pRow()+0,0,"|");
		  imp.say(imp.pRow()+0,136,"|");
		  imp.say(imp.pRow()+1,0,""+imp.comprimido());
		  imp.say(imp.pRow()+0,0,"|");
 // 	  imp.say(imp.pRow()+0,76,"Codido do Almoxarifado:");
          imp.say(imp.pRow()+0,02,"Cod.Fabr.:");
	      imp.say(imp.pRow()+0,13,rs.getString("CodFabProd"));
		  imp.say(imp.pRow()+0,27,"Grupo:");
		  imp.say(imp.pRow()+0,34,rs.getString("Codgrup"));
		  imp.say(imp.pRow()+0,48,"Custo:");
		  imp.say(imp.pRow()+0,55,rs.getString("custoMPMprod"));
  //	  imp.say(imp.pRow()+0,34,"Custo ");
  //      imp.say(imp.pRow()+0,65,rs.getString("custoPEPSprod"));
		  imp.say(imp.pRow()+0,71,"Preço base:");
		  imp.say(imp.pRow()+0,83,rs.getString("precobaseprod"));
  //	  imp.say(imp.pRow()+1,0,""+imp.comprimido());
  //	  imp.say(imp.pRow()+1,0,""+imp.comprimido());
		  imp.say(imp.pRow()+0,99,"Saldo:");
		  imp.say(imp.pRow()+0,106,rs.getString("sldprod"));
  //	  imp.say(imp.pRow()+0,58,"Saldo Reservado");
  //	  imp.say(imp.pRow()+0,65, rs.getString("slresprod"));
  //	  imp.say(imp.pRow()+1,0,"sldconsigprod");
  //   	  imp.say(imp.pRow()+0,0,rs.getString("sldresprod"));
  //	  imp.say(imp.pRow()+0,35,"Saldo Liquido");
 //		  imp.say(imp.pRow()+0,65,rs.getString("sldligprod"));
          imp.say(imp.pRow()+0,121,"Un.:");
          imp.say(imp.pRow()+0,126,rs.getString("codunid"));
		  imp.say(imp.pRow()+0,136,"|");		  

		  
		
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
		imp.say(imp.pRow()+0,0,"|"+ Funcoes.replicate("-",134)+"|");
		imp.eject();

		imp.fechaGravacao();

//		  rs.close();
//		  ps.close();
		if (!con.getAutoCommit())
			con.commit();
		dl.dispose();
		And.dispose();
	  }
	  catch ( SQLException err ) {
		Funcoes.mensagemErro(this,"Erro consulta tabela de produtos!"+err.getMessage());
	  }
	}
	else if (dl.getValores()[6].equals("R")) {
	  String sSQL = "SELECT CODPROD,DESCPROD,CODUNID, SLDLIQPROD, PRECOBASEPROD FROM EQPRODUTO"+sWhere+" ORDER BY "+dl.getValores()[0];
	  PreparedStatement ps = null;
	  ResultSet rs = null;
	  try {
		ps = con.prepareStatement("SELECT COUNT(*) FROM EQPRODUTO"+sWhere);
		rs = ps.executeQuery();
		rs.next();
		And = new FAndamento("Montando Relatório, Aguarde!",0,rs.getInt(1)-1);
//		  rs.close();
//		  ps.close();
		if (!con.getAutoCommit())
			con.commit();
		ps = con.prepareStatement(sSQL);
		rs = ps.executeQuery();
		imp.limpaPags();
		while ( rs.next() ) {
		  if (imp.pRow()==0) {
			imp.impCab(136, false);
			imp.say(imp.pRow()+0,0,""+imp.comprimido());
			imp.say(imp.pRow()+0,2,"|"+Funcoes.replicate(" ",61)+"Filtrado por:"+Funcoes.replicate(" ",60)+"|");
			for (int i=0;i<vFiltros.size();i++) {            
					String sTmp = (String)vFiltros.elementAt(i);
					sTmp = "|"+Funcoes.replicate(" ",(((136-sTmp.length())/2)-1))+sTmp;
					sTmp += Funcoes.replicate(" ",135-sTmp.length())+"|";
					imp.say(imp.pRow()+1,0,""+imp.comprimido());
					imp.say(imp.pRow()+0,2,sTmp);
			}
			
			imp.say(imp.pRow()+1,0,""+imp.comprimido());
			imp.say(imp.pRow()+0,0,"|"+ Funcoes.replicate("-",134)+"|");
			imp.say(imp.pRow()+1,0,""+imp.comprimido());
			imp.say(imp.pRow()+0,0,"|");
			imp.say(imp.pRow()+0,3,"Código:");
			imp.say(imp.pRow()+0,12,"|");
			imp.say(imp.pRow()+0,13,"Descrição:");
			imp.say(imp.pRow()+0,70,"|");
			imp.say(imp.pRow()+0,72,"Unidade:");
			imp.say(imp.pRow()+0,95,"|");
			imp.say(imp.pRow()+0,97,"Saldo:");
			imp.say(imp.pRow()+0,117,"|");
			imp.say(imp.pRow()+0,120,"Preço Base:");
			imp.say(imp.pRow()+0,136,"|");
			imp.say(imp.pRow()+1,0,""+imp.comprimido());
			imp.say(imp.pRow()+0,0,Funcoes.replicate("-",136));
			imp.say(imp.pRow()+1,0,""+imp.comprimido());
			
		  }
		  imp.say(imp.pRow()+0,0,"|");
		  imp.say(imp.pRow()+0,4,rs.getString("CodProd"));
		  imp.say(imp.pRow()+0,12,"|");
		  imp.say(imp.pRow()+0,13,rs.getString("DescProd") != null ? rs.getString("Descprod").substring(0,50) : "");
		  imp.say(imp.pRow()+0,70,"|");
		  imp.say(imp.pRow()+0,72,rs.getString("codunid"));
		  imp.say(imp.pRow()+0,95,"|");
		  imp.say(imp.pRow()+0,97,rs.getString("sldliqprod"));
		  imp.say(imp.pRow()+0,117,"|");
 		  imp.say(imp.pRow()+0,120,rs.getString("Precobaseprod"));
		  imp.say(imp.pRow()+0,136,"|");
		  imp.say(imp.pRow()+1,0,""+imp.comprimido());
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

//		  rs.close();
//		  ps.close();
		if (!con.getAutoCommit())
			con.commit();
		dl.dispose();
		And.dispose();
	  }
	  catch ( SQLException err ) {
		Funcoes.mensagemErro(this,"Erro consulta tabela de produtos!"+err.getMessage());
	  }
	}
	if (bVisualizar) {
	  imp.preview(this);
	}
	else {
	  imp.print();
	}
  }
  public void setConexao(Connection cn) {
    super.setConexao(cn);
    sPrefs = getPrefs();
    montaTela();
    lcLote.setConexao(cn);
    lcMoeda.setConexao(cn);      
    lcUnid.setConexao(cn);
    lcFisc.setConexao(cn);      
    lcMarca.setConexao(cn);      
    lcGrup.setConexao(cn);      
    lcAlmox.setConexao(cn);   
    lcUnidFat.setConexao(cn);
	lcForFK.setConexao(cn);
    lcFatConv.setConexao(cn);
	lcFor.setConexao(cn);
    lcFoto.setConexao(cn);
    lcPreco.setConexao(cn);
    lcClasCliPreco.setConexao(cn);
    lcTabPreco.setConexao(cn);
    lcPlanoPagPreco.setConexao(cn);
    lcCodAltProd.setConexao(cn);
  }
  public void valorAlterado(CheckBoxEvent cbevt) {
    if (cbLote.getStatus()) {
      txtCodLote.setEditable(true);
      txtDiniLote.setEditable(true);
      txtVenctoLote.setEditable(true);
      lcLote.setReadOnly(false);
    }
    else {
      txtCodLote.setEditable(false);
      txtDiniLote.setEditable(false);
      txtVenctoLote.setEditable(false);
      lcLote.setReadOnly(true);
    }
  }
  public void afterEdit(EditEvent eevt) {
    if (imFotoProd.foiAlterado()) {
      txtLargFotoProd.setVlrString(""+imFotoProd.getLargura());
      txtAltFotoProd.setVlrString(""+imFotoProd.getAltura());
    }
  }
  public void afterInsert(InsertEvent ievt) {
    if (ievt.getListaCampos() == lcFoto && imFotoProd.foiAlterado()) {
      txtLargFotoProd.setVlrString(""+imFotoProd.getLargura());
      txtAltFotoProd.setVlrString(""+imFotoProd.getAltura());
    }
    else if (ievt.getListaCampos() == lcCampos) {
    	carregaMoeda();
    	cbAtivo.setVlrString("S");
    }
  }
  public void actionPerformed(ActionEvent evt) {
  	if (evt.getSource() == btPrevimp) 
  	  imprimir(true);
  	else if (evt.getSource() == btImp) 
	  imprimir(false);
  	else if (evt.getSource() == btExp) 
  		exportar();
  	super.actionPerformed(evt);
  }
  public void stateChanged(ChangeEvent cevt){
  	if (cevt.getSource()==tpn) {
		if (tpn.getSelectedIndex()==0)
		  txtCodProd.requestFocus();
  		else if (tpn.getSelectedIndex()==1)
  		  txtUnidFat.requestFocus();
  	    else if (tpn.getSelectedIndex()==2)
  		  txtCodFor.requestFocus();
		else if (tpn.getSelectedIndex()==3)
		  txtCodLote.requestFocus();
		else if (tpn.getSelectedIndex()==4)
		  txtCodFotoProd.requestFocus();
		else if (tpn.getSelectedIndex()==5)
		  txtCodPrecoProd.requestFocus();                     
  	}
  }
  public void beforeEdit(EditEvent eevt) { }
  public void beforeInsert(InsertEvent eevt) { }
  public void edit(EditEvent eevt) { }
}
