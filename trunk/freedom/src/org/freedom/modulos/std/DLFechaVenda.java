/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)DLFechaVenda.java <BR>
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
 */ 

package org.freedom.modulos.std;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Painel;
import org.freedom.componentes.Tabela;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FFDialogo;

public class DLFechaVenda extends FFDialogo implements FocusListener, MouseListener {
  private int casasDec = Aplicativo.casasDec;
  private JTabbedPane tpn = new JTabbedPane();
  private Painel pinFecha = new Painel(400,300);
  private Painel pinFrete = new Painel(400,300);
  private JPanel pnReceber = new JPanel(new BorderLayout());
  private JPanel pnComis = new JPanel(new GridLayout(1,1));;
  private Painel pinInfEspec = new Painel(0,0);
  private JTextFieldPad txtCodVenda = new JTextFieldPad();
  private JTextFieldPad txtVlrDescItVenda = new JTextFieldPad();
  private JTextFieldPad txtPercDescVenda = new JTextFieldPad();
  private JTextFieldPad txtVlrDescVenda = new JTextFieldPad();
  private JTextFieldPad txtPercAdicVenda = new JTextFieldPad();
  private JTextFieldPad txtVlrAdicVenda = new JTextFieldPad();
  private JTextFieldPad txtVlrProdVenda = new JTextFieldPad();
  private JTextFieldPad txtCodPlanoPag = new JTextFieldPad();
  private JTextFieldPad txtCodTran = new JTextFieldPad();
  private JTextFieldPad txtPlacaFreteVD = new JTextFieldPad();
  private JTextFieldPad txtUFFreteVD = new JTextFieldPad();
  private JTextFieldPad txtVlrFreteVD = new JTextFieldPad();
  private JTextFieldPad txtConhecFreteVD = new JTextFieldPad();
  private JTextFieldPad txtQtdFreteVD = new JTextFieldPad();
  private JTextFieldPad txtPesoBrutVD = new JTextFieldPad();
  private JTextFieldPad txtPesoLiqVD = new JTextFieldPad();
  private JTextFieldPad txtEspFreteVD = new JTextFieldPad();
  private JTextFieldPad txtMarcaFreteVD = new JTextFieldPad();
  private JTextFieldPad txtCodAuxV = new JTextFieldPad();
  private JTextFieldPad txtCPFCliAuxV = new JTextFieldPad();
  private JTextFieldPad txtNomeCliAuxV = new JTextFieldPad();
  private JTextFieldPad txtCidCliAuxV = new JTextFieldPad();
  private JTextFieldPad txtUFCliAuxV = new JTextFieldPad();
  private JTextFieldPad txtCodRec = new JTextFieldPad();
  private JTextFieldPad txtCodBanco = new JTextFieldPad();
  private JTextFieldPad txtCodModBol = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtNParcItRec = new JTextFieldPad();
  private JTextFieldPad txtVlrParcItRec = new JTextFieldPad();
  private JTextFieldPad txtVlrDescItRec = new JTextFieldPad();
  private JTextFieldPad txtVlrParcRec = new JTextFieldPad();
  private JTextFieldPad txtDtVencItRec = new JTextFieldPad();  
  private JTextFieldPad txtCodComi = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtVlrComi = new JTextFieldPad(JTextFieldPad.TP_NUMERIC,15,2);
  private JTextFieldPad txtDtVencComi = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
  private JTextFieldPad txtStatusVenda = new JTextFieldPad();
  private JTextFieldPad txtTipoVenda = new JTextFieldPad();
  private JTextFieldPad txtAltUsuRec = new JTextFieldPad(JTextFieldPad.TP_STRING,1,0);
  private JTextFieldFK txtDescPlanoPag = new JTextFieldFK();
  private JTextFieldFK txtDescTran = new JTextFieldFK();
  private JTextFieldFK txtDescBanco = new JTextFieldFK();
  private JLabel lbPercDescVenda = new JLabel("% Desc.");
  private JLabel lbVlrDescVenda = new JLabel("V Desc.");
  private JLabel lbPercAdicVenda = new JLabel("% Adic.");
  private JLabel lbVlrAdicVenda = new JLabel("V Adic.");
  private JLabel lbCodPlanoPag = new JLabel("Código e Desc. do plano de pagto.");
  private JLabel lbCodTran = new JLabel("Código e Desc. da Transportadora");
  private JLabel lbTipoFreteVD = new JLabel("Tipo");
  private JLabel lbPlacaFreteVD = new JLabel("Placa");
  private JLabel lbUFFreteVD = new JLabel("UF");
  private JLabel lbVlrFreteVD = new JLabel("Valor");
  private JLabel lbQtdFreteVD = new JLabel("Volumes");
  private JLabel lbPesoBrutVD = new JLabel("Peso B.");
  private JLabel lbPesoLiqVD = new JLabel("Peso L.");
  private JLabel lbEspFreteVD = new JLabel("Espec.");
  private JLabel lbMarcaFreteVD = new JLabel("Marca");
  private JCheckBoxPad cbImpPed = new JCheckBoxPad("Imprime Pedido?","S","N");
  private JCheckBoxPad cbImpNot = new JCheckBoxPad("Imprime Nota?","S","N");
  private JCheckBoxPad cbImpBol = new JCheckBoxPad("Imprime Boleto?","S","N");
  private Vector vVals = new Vector();
  private Vector vLabs = new Vector();
  private JRadioGroup rgFreteVD = null;
  private ListaCampos lcVenda = new ListaCampos(this);
  private ListaCampos lcPlanoPag = new ListaCampos(this,"PG");
  private ListaCampos lcTran = new ListaCampos(this,"TN");
  private ListaCampos lcAuxVenda = new ListaCampos(this);
  private ListaCampos lcFreteVD = new ListaCampos(this);
  private ListaCampos lcReceber = new ListaCampos(this);
  private ListaCampos lcBanco = new ListaCampos(this,"BO");
  private ListaCampos lcItReceber = new ListaCampos(this);
  private ListaCampos lcComis = new ListaCampos(this);
  private Connection con = null;
  private Tabela tabRec = new Tabela();
  private Tabela tabComis = new Tabela();
  private int iCodVendaFecha = 0;
  private boolean bCarFrete = false;
  private boolean bPrefs[] = null;
  public DLFechaVenda(Connection cn, Integer iCodVenda, Component cOrig) {
  	super(cOrig);
    con = cn;
    iCodVendaFecha = iCodVenda.intValue();
    setTitulo("Fechar Venda");
    setAtribos(410,350);

    lcItReceber.setMaster(lcReceber);
    lcReceber.adicDetalhe(lcItReceber);
    lcItReceber.setTabela(tabRec);
    lcComis.setMaster(lcReceber);
    lcReceber.adicDetalhe(lcComis);
    lcComis.setTabela(tabComis);

    c.add(tpn);
    
    JScrollPane spnComis = new JScrollPane(tabComis);
    pnComis.add(spnComis);
    
    tpn.add("Fechamento",pinFecha);
    tpn.add("Frete",pinFrete);
    tpn.add("Inf. Específicas",pinInfEspec);
    tpn.add("Receber",pnReceber);
    tpn.add("Comissão",pnComis);
    
    vVals.addElement("C");
    vVals.addElement("F");
    vLabs.addElement("CIF");
    vLabs.addElement("FOB");
    
    rgFreteVD = new JRadioGroup(1,2,vLabs, vVals);
    
    txtCodPlanoPag.setNomeCampo("CodPlanoPag");
    txtCodPlanoPag.setTipo(JTextFieldPad.TP_INTEGER,8,0);
    txtDescPlanoPag.setTipo(JTextFieldPad.TP_STRING,40,0);
    lcPlanoPag.add(new GuardaCampo( txtCodPlanoPag, 7, 100, 80, 20, "CodPlanoPag", "Código", true, false, null, JTextFieldPad.TP_INTEGER,false),"txtCodPlanoPagx");
    lcPlanoPag.add(new GuardaCampo( txtDescPlanoPag, 90, 100, 207, 20, "DescPlanoPag", "Descrição", false, false, null, JTextFieldPad.TP_STRING,false),"txtDescPlanoPagx");
    txtCodPlanoPag.setTabelaExterna(lcPlanoPag);
	txtCodPlanoPag.setFK(true);
    txtDescPlanoPag.setListaCampos(lcPlanoPag);
    lcPlanoPag.montaSql(false, "PLANOPAG", "FN");
    lcPlanoPag.setQueryCommit(false);
    lcPlanoPag.setReadOnly(true);
    lcPlanoPag.setConexao(cn);

    txtCodTran.setNomeCampo("CodTran");
    txtCodTran.setTipo(JTextFieldPad.TP_INTEGER,8,0);
    txtDescTran.setTipo(JTextFieldPad.TP_STRING,40,0);
    lcTran.add(new GuardaCampo( txtCodTran, 7, 100, 80, 20, "CodTran", "Código", true, false, null, JTextFieldPad.TP_INTEGER,false),"txtCodTranx");
    lcTran.add(new GuardaCampo( txtDescTran, 90, 100, 207, 20, "RazTran", "Descrição", false, false, null, JTextFieldPad.TP_STRING,false),"txtDescTranx");
    txtDescTran.setListaCampos(lcTran);
    txtCodTran.setTabelaExterna(lcTran);
	txtCodTran.setFK(true);
    lcTran.montaSql(false, "TRANSP", "VD");
    lcTran.setQueryCommit(false);
    lcTran.setReadOnly(true);
    lcTran.setConexao(cn);

    txtCodBanco.setNomeCampo("CodBanco");
    txtCodBanco.setTipo(JTextFieldPad.TP_STRING,3,0);
    txtDescBanco.setTipo(JTextFieldPad.TP_STRING,40,0);
    lcBanco.add(new GuardaCampo( txtCodBanco, 7, 100, 80, 20, "CodBanco", "Código", true, false, null, JTextFieldPad.TP_STRING,false),"txtCodBancox");
    lcBanco.add(new GuardaCampo( txtDescBanco, 90, 100, 207, 20, "NomeBanco", "Descrição", false, false, null, JTextFieldPad.TP_STRING,false),"txtDescBancox");
    lcBanco.add(new GuardaCampo( txtCodModBol, 90, 100, 207, 20, "CodModBol", "Modelo", false, false, null, JTextFieldPad.TP_INTEGER,false),"txtDescBancox");
    txtDescBanco.setListaCampos(lcBanco);
	txtCodBanco.setFK(true);
    lcBanco.montaSql(false, "BANCO", "FN");
    lcBanco.setQueryCommit(false);
    lcBanco.setReadOnly(true);
    lcBanco.setConexao(cn);
    txtCodBanco.setTabelaExterna(lcBanco);

	txtTipoVenda.setTipo(JTextFieldPad.TP_STRING,1,0);
    txtCodVenda.setTipo(JTextFieldPad.TP_INTEGER,8,0);
    txtCodPlanoPag.setTabelaExterna(lcPlanoPag);
    txtVlrDescItVenda.setTipo(JTextFieldPad.TP_DECIMAL,15,2);
    txtVlrProdVenda.setTipo(JTextFieldPad.TP_DECIMAL,15,2);
    txtVlrAdicVenda.setTipo(JTextFieldPad.TP_DECIMAL,15,2);
    txtVlrDescVenda.setTipo(JTextFieldPad.TP_DECIMAL,15,2);
    txtStatusVenda.setTipo(JTextFieldPad.TP_STRING,2,0);
    lcVenda.add(new GuardaCampo( txtTipoVenda, 7, 100, 80, 20, "TipoVenda", "Tipo", true, false, null, JTextFieldPad.TP_STRING,false),"txtCodPlanoPagx");
	lcVenda.add(new GuardaCampo( txtCodVenda, 7, 100, 80, 20, "CodVenda", "Código", true, false, null, JTextFieldPad.TP_INTEGER,false),"txtCodPlanoPagx");
    lcVenda.add(new GuardaCampo( txtCodPlanoPag, 7, 100, 80, 20, "CodPlanoPag", "CodPlan", false, true, txtDescPlanoPag, JTextFieldPad.TP_INTEGER,false),"txtCodPlanoPagx");
    lcVenda.add(new GuardaCampo( txtVlrDescItVenda, 7, 100, 80, 20, "VlrDescItVenda", "% Desc It.", false, false, null, JTextFieldPad.TP_DECIMAL,false),"txtCodPlanoPagx");
    lcVenda.add(new GuardaCampo( txtVlrDescVenda, 7, 100, 80, 20, "VlrDescVenda", "% Desc It.", false, false, null, JTextFieldPad.TP_DECIMAL,false),"txtCodPlanoPagx");
    lcVenda.add(new GuardaCampo( txtVlrAdicVenda, 7, 100, 80, 20, "VlrAdicVenda", "% Adic.", false, false, null, JTextFieldPad.TP_DECIMAL,false),"txtCodPlanoPagx");
    lcVenda.add(new GuardaCampo( txtVlrProdVenda, 7, 100, 80, 20, "VlrProdVenda", "V. Prod.", false, false, null, JTextFieldPad.TP_DECIMAL,false),"txtCodPlanoPagx");
    lcVenda.add(new GuardaCampo( txtCodBanco, 7, 100, 80, 20, "CodBanco", "CodBanco", false, true, txtDescBanco, JTextFieldPad.TP_STRING,false),"txtCodPlanoPagx");
    lcVenda.add(new GuardaCampo( txtStatusVenda, 7, 100, 80, 20, "StatusVenda", "Status", false, false, null, JTextFieldPad.TP_STRING,false),"txtCodPlanoPagx");
    lcVenda.montaSql(false, "VENDA", "VD");
    lcVenda.setConexao(cn);
	txtCodVenda.setNomeCampo("CodVenda");
    txtVlrAdicVenda.setListaCampos(lcVenda);
    txtPercAdicVenda.setListaCampos(lcVenda);
    txtVlrDescVenda.setListaCampos(lcVenda);
    txtPercDescVenda.setListaCampos(lcVenda);
    txtStatusVenda.setListaCampos(lcVenda);
    txtCodPlanoPag.setListaCampos(lcVenda);

    txtPlacaFreteVD.setTipo(JTextFieldPad.TP_STRING,10,0);
    txtUFFreteVD.setTipo(JTextFieldPad.TP_STRING,2,0);
    txtVlrFreteVD.setTipo(JTextFieldPad.TP_DECIMAL,15,2);
    txtConhecFreteVD.setTipo(JTextFieldPad.TP_STRING,13,0);
    txtQtdFreteVD.setTipo(JTextFieldPad.TP_DECIMAL,15,2);
    txtPesoBrutVD.setTipo(JTextFieldPad.TP_DECIMAL,15,casasDec);
    txtPesoLiqVD.setTipo(JTextFieldPad.TP_DECIMAL,15,casasDec);
    txtEspFreteVD.setTipo(JTextFieldPad.TP_STRING,10,0);
    txtMarcaFreteVD.setTipo(JTextFieldPad.TP_STRING,10,0);
	lcFreteVD.add(new GuardaCampo( txtTipoVenda, 7, 100, 80, 20, "TipoVenda", "Tipo", true, false, null, JTextFieldPad.TP_STRING,false),"txtCodPlanoPagx");
    lcFreteVD.add(new GuardaCampo( txtCodVenda, 7, 100, 80, 20, "CodVenda", "Código", true, false, null, JTextFieldPad.TP_INTEGER,false),"txtCodPlanoPagx");
    lcFreteVD.add(new GuardaCampo( txtCodTran, 7, 100, 80, 20, "CodTran", "CodTran", false, true, txtDescTran, JTextFieldPad.TP_INTEGER,false),"txtCodPlanoPagx");
    lcFreteVD.add(new GuardaCampo( rgFreteVD, 7, 100, 80, 20, "TipoFreteVD", "Tipo", false, false, null, JTextFieldPad.TP_STRING,true),"txtCodPlanoPagx");
    lcFreteVD.add(new GuardaCampo( txtConhecFreteVD, "ConhecFreteVD", "Conhec.", ListaCampos.DB_SI, false));
    lcFreteVD.add(new GuardaCampo( txtPlacaFreteVD, 7, 100, 80, 20, "PlacaFreteVD", "Placa", false, false, null, JTextFieldPad.TP_STRING,true),"txtCodPlanoPagx");
    lcFreteVD.add(new GuardaCampo( txtUFFreteVD, 7, 100, 80, 20, "UFFreteVD", "Placa", false, false, null, JTextFieldPad.TP_STRING,true),"txtCodPlanoPagx");
    lcFreteVD.add(new GuardaCampo( txtVlrFreteVD, 7, 100, 80, 20, "VlrFreteVD", "Valor", false, false, null, JTextFieldPad.TP_DECIMAL,true),"txtCodPlanoPagx");
    lcFreteVD.add(new GuardaCampo( txtQtdFreteVD, 7, 100, 80, 20, "QtdFreteVD", "Qtd.", false, false, null, JTextFieldPad.TP_DECIMAL,true),"txtCodPlanoPagx");
    lcFreteVD.add(new GuardaCampo( txtPesoBrutVD, 7, 100, 80, 20, "PesoBrutVD", "Peso Brut.", false, false, null, JTextFieldPad.TP_DECIMAL,true),"txtCodPlanoPagx");
    lcFreteVD.add(new GuardaCampo( txtPesoLiqVD, 7, 100, 80, 20, "PesoLiqVD", "Peso Liq.", false, false, null, JTextFieldPad.TP_DECIMAL,true),"txtCodPlanoPagx");
    lcFreteVD.add(new GuardaCampo( txtEspFreteVD, 7, 100, 80, 20, "EspFreteVD", "Esp. Fiscal", false, false, null, JTextFieldPad.TP_STRING,true),"txtCodPlanoPagx");
    lcFreteVD.add(new GuardaCampo( txtMarcaFreteVD, 7, 100, 80, 20, "MarcaFreteVD", "Marca", false, false, null, JTextFieldPad.TP_STRING,true),"txtCodPlanoPagx");
    lcFreteVD.montaSql(false, "FRETEVD", "VD");
    lcFreteVD.setConexao(cn);
    rgFreteVD.setListaCampos(lcFreteVD);
    txtPlacaFreteVD.setListaCampos(lcFreteVD);
    txtUFFreteVD.setListaCampos(lcFreteVD);
    txtVlrFreteVD.setListaCampos(lcFreteVD);
    txtQtdFreteVD.setListaCampos(lcFreteVD);
    txtPesoBrutVD.setListaCampos(lcFreteVD);
    txtPesoLiqVD.setListaCampos(lcFreteVD);
    txtEspFreteVD.setListaCampos(lcFreteVD);
    txtMarcaFreteVD.setListaCampos(lcFreteVD);
    txtConhecFreteVD.setListaCampos(lcFreteVD);
    txtCodTran.setListaCampos(lcFreteVD);
    
    lcAuxVenda.add(new GuardaCampo( txtTipoVenda, 7, 100, 80, 20, "TipoVenda", "Tipo", true, false, null, JTextFieldPad.TP_STRING,false),"txtCodPlanoPagx");
    lcAuxVenda.add(new GuardaCampo( txtCodVenda, 7, 100, 80, 20, "CodVenda", "Código", true, false, null, JTextFieldPad.TP_INTEGER,false),"txtCodPlanoPagx");
    lcAuxVenda.add(new GuardaCampo( txtCodAuxV, 7, 100, 80, 20, "CodAuxV", "Cód.Aux.", true, false, null, JTextFieldPad.TP_INTEGER,false),"txtCodPlanoPagx");
    lcAuxVenda.add(new GuardaCampo( txtCPFCliAuxV, 7, 100, 80, 20, "CPFCliAuxV", "CPF", false, false, null, JTextFieldPad.TP_STRING,false),"txtCodPlanoPagx");
    lcAuxVenda.add(new GuardaCampo( txtNomeCliAuxV, 7, 100, 80, 20, "NomeCliAuxV", "Nome", false, false, null, JTextFieldPad.TP_STRING,false),"txtCodPlanoPagx");
    lcAuxVenda.add(new GuardaCampo( txtCidCliAuxV, 7, 100, 80, 20, "CidCliAuxV", "Cidade", false, false, null, JTextFieldPad.TP_STRING,false),"txtCodPlanoPagx");
    lcAuxVenda.add(new GuardaCampo( txtUFCliAuxV, 7, 100, 80, 20, "UFCliAuxV", "UF", false, false, null, JTextFieldPad.TP_STRING,false),"txtCodPlanoPagx");
    lcAuxVenda.montaSql(false, "AUXVENDA", "VD");
    lcAuxVenda.setConexao(cn);
    txtCodAuxV.setListaCampos(lcAuxVenda);
    txtCPFCliAuxV.setListaCampos(lcAuxVenda);
    txtNomeCliAuxV.setListaCampos(lcAuxVenda);
    txtCidCliAuxV.setListaCampos(lcAuxVenda);
    txtUFCliAuxV.setListaCampos(lcAuxVenda);
    txtCPFCliAuxV.setMascara(JTextFieldPad.MC_CPF);
    
    Painel pinTopRec = new Painel(400,60);
    pinTopRec.setPreferredSize(new Dimension(400,60));
    pnReceber.add(pinTopRec,BorderLayout.NORTH);
    JScrollPane spnTabRec = new JScrollPane(tabRec);
    pnReceber.add(spnTabRec,BorderLayout.CENTER);

    txtVlrParcRec.setAtivo(false);

    pinTopRec.adic(new JLabel("Valor Tot."),7,0,130,20);
    pinTopRec.adic(txtVlrParcRec,7,20,130,20);

    txtCodRec.setNomeCampo("CodRec");
    txtCodRec.setTipo(JTextFieldPad.TP_INTEGER,8,0);
    txtCodBanco.setTipo(JTextFieldPad.TP_STRING,3,0);
    txtVlrParcRec.setTipo(JTextFieldPad.TP_DECIMAL,15,2);
    lcReceber.add(new GuardaCampo( txtCodRec, 7, 100, 80, 20, "CodRec", "Código", true, false, null, JTextFieldPad.TP_INTEGER,false),"txtCodPlanoPagx");
    lcReceber.add(new GuardaCampo( txtVlrParcRec, 7, 100, 80, 20, "VlrParcRec", "Valor Tot.", false, false, null, JTextFieldPad.TP_DECIMAL,false),"txtCodPlanoPagx");
	lcReceber.add(new GuardaCampo( txtTipoVenda, 7, 100, 80, 20, "TipoVenda", "Tipo de Venda", false, false, null, JTextFieldPad.TP_STRING,false),"txtTipoVenda");
    lcReceber.add(new GuardaCampo( txtAltUsuRec, "AltUsuRec", "Alt.Usu.", ListaCampos.DB_SI,false));
    lcReceber.montaSql(false, "RECEBER", "FN");
    lcReceber.setConexao(cn);
    txtCodRec.setListaCampos(lcReceber);
    txtVlrParcRec.setListaCampos(lcReceber);
    txtCodBanco.setListaCampos(lcReceber);

    txtNParcItRec.setNomeCampo("NParcItRec");
    txtNParcItRec.setTipo(JTextFieldPad.TP_INTEGER,8,0);
    txtVlrParcItRec.setTipo(JTextFieldPad.TP_DECIMAL,15,2);
    txtVlrDescItRec.setTipo(JTextFieldPad.TP_DECIMAL,15,2);
    txtDtVencItRec.setTipo(JTextFieldPad.TP_DATE,10,0);
    lcItReceber.add(new GuardaCampo( txtNParcItRec, 7, 100, 80, 20, "NParcItRec", "Código", true, false, null, JTextFieldPad.TP_INTEGER,false),"txtCodPlanoPagx");
    lcItReceber.add(new GuardaCampo( txtVlrParcItRec, 7, 100, 80, 20, "VlrParcItRec", "Valor Tot.", false, false, null, JTextFieldPad.TP_DECIMAL,false),"txtCodPlanoPagx");
    lcItReceber.add(new GuardaCampo( txtDtVencItRec, 7, 100, 80, 20, "DtVencItRec", "Valor Tot.", false, false, null, JTextFieldPad.TP_DATE,false),"txtCodPlanoPagx");
    lcItReceber.add(new GuardaCampo( txtVlrDescItRec, 7, 100, 80, 20, "VlrDescItRec", "Valor Desc.", false, false, null, JTextFieldPad.TP_DECIMAL,false),"txtCodPlanoPagx");
    lcItReceber.montaSql(false, "ITRECEBER", "FN");
    lcItReceber.setConexao(cn);
    txtNParcItRec.setListaCampos(lcItReceber);
    txtVlrParcItRec.setListaCampos(lcItReceber);
    txtVlrDescItRec.setListaCampos(lcItReceber);
    txtDtVencItRec.setListaCampos(lcItReceber);
    lcItReceber.montaTab();
    tabRec.addMouseListener(this);
    
    lcComis.add(new GuardaCampo( txtCodComi, 7, 100, 80, 20, "CodComi", "Código", true, false, null, JTextFieldPad.TP_INTEGER,false));
    lcComis.add(new GuardaCampo( txtVlrComi, 7, 100, 80, 20, "VlrComi", "Valor ", false, false, null, JTextFieldPad.TP_DECIMAL,false));
    lcComis.add(new GuardaCampo( txtDtVencComi, 7, 100, 80, 20, "DtVencComi", "Vencimento", false, false, null, JTextFieldPad.TP_DATE,false));
    lcComis.montaSql(false, "COMISSAO", "VD");
    lcComis.setConexao(cn);
    lcComis.montaTab();
    tabComis.addMouseListener(this);

    
    txtTipoVenda.setVlrString("V");
    txtCodVenda.setVlrInteger(iCodVenda);
    lcVenda.carregaDados();
    
//Carrega o frete
    
    lcFreteVD.setReadOnly(true);
    if (lcFreteVD.carregaDados()) {
      lcFreteVD.setReadOnly(false);
      lcFreteVD.setState(ListaCampos.LCS_SELECT); 
      bCarFrete = true;
    }
    else {
      lcFreteVD.setReadOnly(false);
    }
    
//Carrega o aux
    int iCodAux = buscaCodAux();
    if (iCodAux > 0) {
    	txtCodAuxV.setVlrInteger(new Integer(iCodAux));
        lcAuxVenda.carregaDados();
    }
    else
        txtCodAuxV.setVlrInteger(new Integer(1));
    
    txtPercDescVenda.setTipo(JTextFieldPad.TP_DECIMAL,6,2);
    txtPercAdicVenda.setTipo(JTextFieldPad.TP_DECIMAL,6,2);
    
    setPainel(pinFecha);
    adic(lbCodPlanoPag,7,0,270,20);
    adic(txtCodPlanoPag,7,20,80,20);
    adic(txtDescPlanoPag,90,20,270,20);
    adic(lbPercDescVenda,7,40,80,20);
    adic(txtPercDescVenda,7,60,80,20);
    adic(lbVlrDescVenda,90,40,97,20);
    adic(txtVlrDescVenda,90,60,97,20);
    adic(lbPercAdicVenda,190,40,77,20);
    adic(txtPercAdicVenda,190,60,77,20);
    adic(lbVlrAdicVenda,270,40,100,20);
    adic(txtVlrAdicVenda,270,60,100,20);
    adic(new JLabel("Código e Descrição do Banco"),7,80,250,20);
    adic(txtCodBanco,7,100,80,20);
    adic(txtDescBanco,90,100,167,20);
    adic(cbImpPed,7,130,150,20);
    adic(cbImpNot,7,150,150,20);
    adic(cbImpBol,7,170,150,20);
    
    setPainel(pinFrete);
    adic(lbCodTran,7,0,350,20);
    adic(txtCodTran,7,20,80,20);
    adic(txtDescTran,90,20,270,20);
    adic(lbTipoFreteVD,7,40,170,20);
    adic(rgFreteVD,7,60,130,30);
    adic(new JLabel("Conhec."),140,50,97,20);
    adic(txtConhecFreteVD,140,70,97,20);
    adic(lbPlacaFreteVD,240,50,77,20);
    adic(txtPlacaFreteVD,240,70,77,20);
    adic(lbUFFreteVD,320,50,40,20);
    adic(txtUFFreteVD,320,70,40,20);
    adic(lbVlrFreteVD,7,90,90,20);
    adic(txtVlrFreteVD,7,110,90,20);
    adic(lbQtdFreteVD,100,90,77,20);
    adic(txtQtdFreteVD,100,110,77,20);
    adic(lbPesoBrutVD,180,90,87,20);
    adic(txtPesoBrutVD,180,110,87,20);
    adic(lbPesoLiqVD,270,90,87,20);
    adic(txtPesoLiqVD,270,110,87,20);
    adic(lbEspFreteVD,7,130,100,20);
    adic(txtEspFreteVD,7,150,100,20);
    adic(lbMarcaFreteVD,110,130,100,20);
    adic(txtMarcaFreteVD,110,150,100,20);
    
    Funcoes.setBordReq(rgFreteVD);
    Funcoes.setBordReq(txtPlacaFreteVD);
    Funcoes.setBordReq(txtUFFreteVD);
    Funcoes.setBordReq(txtVlrFreteVD);
    Funcoes.setBordReq(txtQtdFreteVD);
    Funcoes.setBordReq(txtPesoBrutVD);
    Funcoes.setBordReq(txtPesoLiqVD);
    Funcoes.setBordReq(txtEspFreteVD);
    Funcoes.setBordReq(txtMarcaFreteVD);

    setPainel(pinInfEspec);
    
    adic(new JLabel("Nome"),7,0,240,20);
    adic(txtNomeCliAuxV,7,20,240,20);
    adic(new JLabel("CPF"),250,0,100,20);
    adic(txtCPFCliAuxV,250,20,100,20);
    adic(new JLabel("Cidade"),7,40,300,20);
    adic(txtCidCliAuxV,7,60,300,20);
    adic(new JLabel("UF"),310,40,40,20);
    adic(txtUFCliAuxV,310,60,40,20);
//    adic(txtCodAuxV,310,80,40,20);
    
    
    if (txtVlrDescItVenda.getVlrBigDecimal().doubleValue() > 0) {
      txtPercDescVenda.setAtivo(false);
      txtVlrDescVenda.setAtivo(false);
    }
    
    tpn.setEnabledAt(1,false);   
    tpn.setEnabledAt(2,false);
    tpn.setEnabledAt(3,false);
    tpn.setEnabledAt(4,false);
    
    txtPercDescVenda.addFocusListener(this);
    txtVlrDescVenda.addFocusListener(this);
    txtPercAdicVenda.addFocusListener(this);
    txtVlrAdicVenda.addFocusListener(this);

    cbImpPed.setVlrString("N");
    cbImpNot.setVlrString("N");
    
    bPrefs = prefs();
    lcVenda.edit();
  }
  private void calcPeso() {
  	lcFreteVD.edit();
    BigDecimal bLiq = new BigDecimal("0");
    BigDecimal bBrut = new BigDecimal("0");
    
    
    txtCodTran.setVlrInteger(new Integer(trazCodTran()));
    lcTran.carregaDados();
    
    txtPlacaFreteVD.setVlrString("***-*****");
    txtUFFreteVD.setVlrString("**");
    txtVlrFreteVD.setVlrBigDecimal(new BigDecimal("0"));
    txtQtdFreteVD.setVlrBigDecimal(new BigDecimal("0"));
    txtEspFreteVD.setVlrString("Volume");
    txtMarcaFreteVD.setVlrString("**********");
    String sSQL = "SELECT SUM(I.QTDITVENDA * P.PESOLIQPROD) AS TOTPESOLIQ, SUM(I.QTDITVENDA * P.PESOBRUTPROD) AS TOTPESOBRUT"+
                  " FROM VDITVENDA I,EQPRODUTO P WHERE I.CODVENDA=? AND I.CODEMP=? AND I.CODFILIAL=? AND P.CODPROD=I.CODPROD";
    PreparedStatement ps = null;
    ResultSet rs = null;
    try {
      ps = con.prepareStatement(sSQL);
      ps.setInt(1,iCodVendaFecha);
	  ps.setInt(2,Aplicativo.iCodEmp);
	  ps.setInt(3,ListaCampos.getMasterFilial("VDITVENDA"));
      rs = ps.executeQuery();
      if (rs.next()) {
        bLiq = new BigDecimal(rs.getString(1) != null ? rs.getString(1) : "0");
        bBrut = new BigDecimal(rs.getString(2) != null ? rs.getString(2) : "0");
        bLiq = bLiq.setScale(3);
        bBrut = bBrut.setScale(3);
        txtPesoLiqVD.setVlrBigDecimal(bLiq);
        txtPesoBrutVD.setVlrBigDecimal(bBrut);
      }
//      rs.close();
//      ps.close();
      if (!con.getAutoCommit())
      	con.commit();
    }
    catch (SQLException err) {
		Funcoes.mensagemErro(this,"Erro ao calcular o peso!\n"+err.getMessage());
    }
  }
  private int trazCodRec() {
    int iRetorno = 0;
    String sSQL = "SELECT CODREC FROM FNRECEBER WHERE CODVENDA=? AND CODEMP=? AND CODFILIAL=?";
    PreparedStatement ps = null;
    ResultSet rs = null;
    try {
      ps = con.prepareStatement(sSQL);
      ps.setInt(1,iCodVendaFecha);
	  ps.setInt(2,Aplicativo.iCodEmp);
	  ps.setInt(3,ListaCampos.getMasterFilial("FNRECEBER"));
      rs = ps.executeQuery();
      if (rs.next()) {
        iRetorno = rs.getInt("CodRec");
      }
//      rs.close();
//      ps.close();
      if (!con.getAutoCommit())
      	con.commit();
    }
    catch (SQLException err) {
		Funcoes.mensagemErro(this,"Erro ao buscar o código da conta a receber!\n"+err.getMessage());
    }
    return iRetorno;
  }
  private int trazCodTran() {
    int iRetorno = 0;
    String sSQL = "SELECT C.CODTRAN  FROM VDCLIENTE C, VDVENDA V WHERE C.CODCLI=V.CODCLI AND C.CODEMP=V.CODEMPCL " +
    		      "AND V.CODVENDA=? AND V.CODEMP=? AND V.CODFILIAL=?";
   
    
    PreparedStatement ps = null;
    ResultSet rs = null;
    try {
      ps = con.prepareStatement(sSQL);
      ps.setInt(1,iCodVendaFecha);
	  ps.setInt(2,Aplicativo.iCodEmp);
	  ps.setInt(3,ListaCampos.getMasterFilial("VDVENDA"));
      rs = ps.executeQuery();
      if (rs.next()) {
        iRetorno = rs.getInt("CodTran");
        
      }
      rs.close();
      ps.close();
      
      if (iRetorno==0 ) {
      	       sSQL="SELECT CODTRAN FROM SGPREFERE1  WHERE CODEMP=? AND CODFILIAL=? ";
      	  
      	  ps=con.prepareStatement(sSQL);
      	  ps.setInt(1,Aplicativo.iCodEmp);
    	  ps.setInt(2,Aplicativo.iCodFilial);
    	  rs = ps.executeQuery();
    	  if (rs.next()) {
             iRetorno = rs.getInt("CodTran");
            
          }
    	  
    	  
      }
        
      if (!con.getAutoCommit())
      	con.commit();
    }
    catch (SQLException err) {
		Funcoes.mensagemErro(this,"Erro ao buscar o código da Transportadora do cliente!\n"+err.getMessage());
		err.printStackTrace();
    }
    System.out.println(sSQL);
    return iRetorno;
  }
  private boolean finaliza() {
     boolean bRet = false;
  	 if (lcReceber.getStatus() == ListaCampos.LCS_EDIT) {
  		lcReceber.post();
  	 }  	
  	 lcVenda.edit();
  	 if (txtStatusVenda.getVlrString().equals("V2"))
  	 	txtStatusVenda.setVlrString("V3");
  	 else if (txtStatusVenda.getVlrString().equals("P2"))
  		txtStatusVenda.setVlrString("P3");
  	bRet = lcVenda.post();
  	return bRet;
  }
  private int buscaCodAux() {
  	int iRet = 0;
  	String sSQL = "SELECT CODAUXV FROM VDAUXVENDA WHERE CODEMP=?" +
  			              " AND CODFILIAL=? AND CODVENDA=?";
  	try {
  	  PreparedStatement ps = con.prepareStatement(sSQL);
  	  ps.setInt(1,Aplicativo.iCodEmp);
  	  ps.setInt(2,ListaCampos.getMasterFilial("VDAUXVENDA"));
  	  ps.setInt(3,txtCodVenda.getVlrInteger().intValue());
  	  ResultSet rs = ps.executeQuery();
  	  if (rs.next()) {
  	  	 iRet = rs.getInt("CodAuxV");
  	  }
  	  rs.close();
  	  ps.close();
  	}
    catch(SQLException err) {
    	Funcoes.mensagemErro(this,"Erro ao buscar codaux.\n"+err.getMessage());
    	err.printStackTrace();
    }
  	return iRet;
  }
  private void gravaVenda() {
  	 lcVenda.edit();
  	 if (cbImpNot.getVlrString().equals("S"))
   		txtStatusVenda.setVlrString("V2");
  	 else if (txtStatusVenda.getVlrString().substring(0,1).equals("P")) 
  		txtStatusVenda.setVlrString("P2");
   	 else if (txtStatusVenda.getVlrString().substring(0,1).equals("V")) 
  		txtStatusVenda.setVlrString("V2");
   	 txtPlacaFreteVD.getVlrString();
   	 if (lcFreteVD.getStatus() == ListaCampos.LCS_EDIT ||
   	 	 lcFreteVD.getStatus() == ListaCampos.LCS_INSERT)
  	   lcFreteVD.post();
   	 if (lcAuxVenda.getStatus() == ListaCampos.LCS_EDIT ||
   	 	 lcAuxVenda.getStatus() == ListaCampos.LCS_INSERT)
   	 	lcAuxVenda.post();
   	 lcVenda.post();
  	 int iCodRec = trazCodRec();
  	 if (iCodRec > 0) {
  	 	txtCodRec.setVlrInteger(new Integer(iCodRec));
  	 	lcReceber.carregaDados();
  	 }
  	 tpn.setEnabledAt(0,false);
  	 tpn.setEnabledAt(1,false);
  	 tpn.setEnabledAt(2,false);
  	 tpn.setSelectedIndex(3);
  }
  private void alteraRec() {
    lcItReceber.edit();
    DLFechaParcela dl = new DLFechaParcela(DLFechaVenda.this,txtVlrParcItRec.getVlrBigDecimal(),txtDtVencItRec.getVlrDate(),txtVlrDescItRec.getVlrBigDecimal());
    dl.setVisible(true);
    if (dl.OK) {
      txtVlrParcItRec.setVlrBigDecimal((BigDecimal)dl.getValores()[0]);
      txtDtVencItRec.setVlrDate((Date)dl.getValores()[1]);
      txtVlrDescItRec.setVlrBigDecimal((BigDecimal)dl.getValores()[2]);
      txtAltUsuRec.setVlrString("S");
      lcItReceber.post();
      txtAltUsuRec.setVlrString("N");
      //Atualiza lcReceber    
      if (lcReceber.getStatus() == ListaCampos.LCS_EDIT) 
        lcReceber.post(); // Caso o lcReceber estaja como edit executa o post que atualiza
      else 
        lcReceber.carregaDados(); //Caso não, atualiza
    }
    else {
      dl.dispose();
      lcItReceber.cancel(false);
    }
    dl.dispose();
  }
  private void alteraComis() {
    lcComis.edit();
    DLFechaParcela dl = new DLFechaParcela(DLFechaVenda.this,txtVlrComi.getVlrBigDecimal(),txtDtVencComi.getVlrDate(),null);
    dl.setVisible(true);
    if (dl.OK) {
      txtVlrComi.setVlrBigDecimal((BigDecimal)dl.getValores()[0]);
      txtDtVencComi.setVlrDate((Date)dl.getValores()[1]);
      lcComis.post();
    }
    else {
      dl.dispose();
      lcComis.cancel(false);
    }
    dl.dispose();
  }
  private boolean prox() {
  	boolean bRet = false;
	switch(tpn.getSelectedIndex()) {
	   case 0:
	   	  if (bPrefs[0]) {
	   	  	tpn.setEnabledAt(1,true);
	   	  	tpn.setSelectedIndex(1);
	   	  }
	   	  else if (bPrefs[1]) {
	   	  	tpn.setEnabledAt(2,true);
	   	  	tpn.setSelectedIndex(2);
	      }
	      else {
	   	    tpn.setEnabledAt(0,false);
	   	    tpn.setEnabledAt(3,true);
	   	    tpn.setEnabledAt(4,true);
	   	    gravaVenda();
	   	  }
	   	  txtCodVenda.setVlrInteger(new Integer(iCodVendaFecha));
	   	  txtTipoVenda.setVlrString("V");
	   	  if (!bCarFrete) {
	   	    calcPeso();
	   	  }
	   	  break;
       case 1:
       	  if (bPrefs[1]) {
       	  	tpn.setEnabledAt(2,true);
       	    tpn.setSelectedIndex(2);
	      }
       	  else {
       	  	tpn.setEnabledAt(1,false);
       		tpn.setEnabledAt(3,true);
       		tpn.setEnabledAt(4,true);
       		gravaVenda();
       	  }
       	  break;
       case 2:
       	  tpn.setEnabledAt(2,false);
       	  tpn.setEnabledAt(3,true);
       	  tpn.setEnabledAt(4,true);
       	  gravaVenda();
       	  break;
       default:
       	  bRet = finaliza();
	}
	return bRet;
  }
  public void actionPerformed(ActionEvent evt) {
    if (evt.getSource() == btCancel) {
      super.actionPerformed(evt);
    }
    else if (evt.getSource() == btOK) {
      if (prox())
      	super.actionPerformed(evt);
    }
  }
  private boolean[] prefs() {
  	boolean[] bRetorno = new boolean[2];
  	String sSQL = "SELECT TABFRETEVD,TABADICVD FROM SGPREFERE1 WHERE CODEMP=? AND CODFILIAL=?";
  	PreparedStatement ps = null;
  	ResultSet rs = null;
  	try {
  		ps = con.prepareStatement(sSQL);
  		ps.setInt(1,Aplicativo.iCodEmp);
  		ps.setInt(2,ListaCampos.getMasterFilial("SGPREFERE1"));
  		rs = ps.executeQuery();
  		if (rs.next()) {
  			if (rs.getString("TabFreteVD").trim().equals("S"))
  				bRetorno[0] = true;
  			if (rs.getString("TabAdicVD").trim().equals("S"))
  				bRetorno[1] = true;
  		}
        rs.close();
        ps.close();
        if (!con.getAutoCommit())
        	con.commit();
  	}
  	catch (SQLException err) {
  		Funcoes.mensagemErro(this,"Erro ao carregar a tabela PREFERE1!\n"+err.getMessage());
  	}
  	return bRetorno;
  }
  public String[] getValores() {
    String[] sRetorno = new String[7];
    sRetorno[0] = txtCodPlanoPag.getVlrString();
    sRetorno[1] = txtVlrDescVenda.getVlrString();
    sRetorno[2] = txtVlrAdicVenda.getVlrString();
    sRetorno[3] = cbImpPed.getVlrString();
    sRetorno[4] = cbImpNot.getVlrString();
    sRetorno[5] = cbImpBol.getVlrString();
    sRetorno[6] = txtCodModBol.getVlrString();
    return sRetorno;
  }
  public void focusLost(FocusEvent fevt) {
    if (fevt.getSource() == txtPercDescVenda) {
      if (txtPercDescVenda.getText().trim().length() < 1) {
        txtVlrDescVenda.setAtivo(true);
      }
      else {
        txtVlrDescVenda.setVlrBigDecimal(
          txtVlrProdVenda.getVlrBigDecimal().multiply(
          txtPercDescVenda.getVlrBigDecimal()).divide(
          new BigDecimal("100"),3,BigDecimal.ROUND_HALF_UP)
        );
        txtVlrDescVenda.setAtivo(false);
      }
    }
    if (fevt.getSource() == txtPercAdicVenda) {
      if (txtPercAdicVenda.getText().trim().length() < 1) {
        txtVlrAdicVenda.setAtivo(true);
      }
      else {
        txtVlrAdicVenda.setVlrBigDecimal(
          txtVlrProdVenda.getVlrBigDecimal().multiply(
          txtPercAdicVenda.getVlrBigDecimal()).divide(
          new BigDecimal("100"),3,BigDecimal.ROUND_HALF_UP)
        );
        txtVlrAdicVenda.setAtivo(false);
      }
    }
  }
  public void mouseClicked(MouseEvent mevt) {
    if (mevt.getClickCount() == 2) {
    	if (mevt.getSource() == tabRec && tabRec.getLinhaSel() >= 0)
    	  alteraRec();
    	else if (mevt.getSource() == tabComis && tabComis.getLinhaSel() >= 0)
      	  alteraComis();
    }
  }
  public void focusGained(FocusEvent fevt) { }
  public void mouseEntered(MouseEvent e) { }
  public void mouseExited(MouseEvent e) { }
  public void mousePressed(MouseEvent e) { }
  public void mouseReleased(MouseEvent e) { }
}
/* (non-Javadoc)
 * @see org.freedom.acao.PostListener#beforePost(org.freedom.acao.PostEvent)
 */
