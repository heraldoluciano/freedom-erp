/**
 * @version 02/11/2003 <BR>
 * @author Setpoint Informática Ltda. Robson Sanchez e Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FTipoMov.java <BR>
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

package org.freedom.modulos.std;
import java.sql.Connection;
import java.util.Vector;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JComboBoxPad;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.telas.FDados;
public class FTipoMov extends FDados {
  private JTextFieldPad txtCodTipoMov = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtCodTipoMov2 = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtDescTipoMov = new JTextFieldPad(JTextFieldPad.TP_STRING,40,0);
  private JTextFieldPad txtCodModNota = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtCodSerie = new JTextFieldPad(JTextFieldPad.TP_STRING,4,0);
  private JTextFieldPad txtCodTab = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtEspecieTipomov = new JTextFieldPad( JTextFieldPad.TP_STRING,4,9);
  private JTextFieldFK txtDescModNota = new JTextFieldFK(JTextFieldPad.TP_STRING,30,0);
  private JTextFieldFK txtDescSerie = new JTextFieldFK(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldFK txtDescTab = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
  private JTextFieldFK txtDescTipoMov2 = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
  private ListaCampos lcModNota = new ListaCampos(this,"MN");
  private ListaCampos lcSerie = new ListaCampos(this,"SE");
  private ListaCampos lcTab = new ListaCampos(this,"TB");
  private ListaCampos lcTipoMov = new ListaCampos(this,"TM");
  private Vector vVals = new Vector();
  private Vector vLabs = new Vector();
  private Vector vValsES = new Vector();
  private Vector vLabsES = new Vector();
  private JComboBoxPad cbTipoMov = null;
  private JRadioGroup rgESTipoMov = null;
  private JCheckBoxPad chbFiscalTipoMov = new JCheckBoxPad("Lanc.fiscal?","S","N");
  private JCheckBoxPad chbEstoqTipoMov = new JCheckBoxPad("Cont.estoque?","S","N");
  private JCheckBoxPad chbSomaTipoMov = new JCheckBoxPad("Soma rel.vendas?","S","N");
  public FTipoMov() {
    setTitulo("Cadastro de Tipos de Movimento");
    setAtribos( 50, 50, 410, 440);
  
    lcModNota.add(new GuardaCampo( txtCodModNota, "CodModNota", "Cód.mod.nota", ListaCampos.DB_PK, false));
    lcModNota.add(new GuardaCampo( txtDescModNota, "DescModNota", "Descrição do modelo de nota", ListaCampos.DB_SI, false));
    lcModNota.montaSql(false, "MODNOTA", "LF");    
    lcModNota.setQueryCommit(false);
    lcModNota.setReadOnly(true);
    txtCodModNota.setTabelaExterna(lcModNota);

    lcSerie.add(new GuardaCampo( txtCodSerie, "Serie", "Cód.serie", ListaCampos.DB_PK, false));
    lcSerie.add(new GuardaCampo( txtDescSerie, "DocSerie", "Nº. doc", ListaCampos.DB_SI, false));
    lcSerie.montaSql(false, "SERIE", "LF");
    lcSerie.setQueryCommit(false);
    lcSerie.setReadOnly(true);
    txtCodSerie.setTabelaExterna(lcSerie);

    lcTab.add(new GuardaCampo( txtCodTab, "CodTab", "Cód.tb.pc.", ListaCampos.DB_PK, false));
    lcTab.add(new GuardaCampo( txtDescTab, "DescTab", "Descrição da tabela de preço", ListaCampos.DB_SI, false));
    lcTab.montaSql(false, "TABPRECO", "VD");
    lcTab.setQueryCommit(false);
    lcTab.setReadOnly(true);
    txtCodTab.setTabelaExterna(lcTab);

    lcTipoMov.add(new GuardaCampo( txtCodTipoMov2, "CodTipoMov", "Cód.tp.mov.", ListaCampos.DB_PK, false));
    lcTipoMov.add(new GuardaCampo( txtDescTipoMov2, "DescTipoMov", "Descrição do tipo de movimento", ListaCampos.DB_SI, false));
    lcTipoMov.montaSql(false, "TIPOMOV", "EQ");
    lcTipoMov.setQueryCommit(false);
    lcTipoMov.setReadOnly(true);
    txtCodTipoMov2.setTabelaExterna(lcTipoMov);
    
    vLabs.addElement("Orçamento (compra)");
    vLabs.addElement("Orçamento (venda)");
    vLabs.addElement("Pedido (compra)");
    vLabs.addElement("Pedido (venda)");
    vLabs.addElement("Compra");
    vLabs.addElement("Venda");
	vLabs.addElement("Venda - ECF");
	vLabs.addElement("Venda - televendas");
	vLabs.addElement("Devolução");
	vLabs.addElement("Cancelamento");
	vLabs.addElement("Bonificação");
	vLabs.addElement("Transferência");
	vLabs.addElement("Perda");
	vLabs.addElement("Consignação - saída");
	vLabs.addElement("Consignação - devolução");
	vLabs.addElement("Serviço");
	vLabs.addElement("Inventário");
	vVals.addElement("OC");
    vVals.addElement("OV");
    vVals.addElement("PC");
    vVals.addElement("PV");
    vVals.addElement("CP");
    vVals.addElement("VD");
	vVals.addElement("VE");
	vVals.addElement("VT");
	vVals.addElement("DV");
	vVals.addElement("CC");
	vVals.addElement("BN");
	vVals.addElement("TR");
	vVals.addElement("PE");
	vVals.addElement("CS");
	vVals.addElement("CE");
	vVals.addElement("SE");
	vVals.addElement("IV");
	
    cbTipoMov = new JComboBoxPad(vLabs,vVals, JComboBoxPad.TP_STRING, 2, 0);
    cbTipoMov.setSelectedIndex(6);
    
    vLabsES.addElement("Entrada");
    vLabsES.addElement("Saída");
    vLabsES.addElement("Inventário");
    vValsES.addElement("E");
    vValsES.addElement("S");
    vValsES.addElement("I");
    
    rgESTipoMov = new JRadioGroup(1,2,vLabsES,vValsES);
 
    adicCampo(txtCodTipoMov, 7, 20, 80, 20,"CodTipoMov","Cód.tp.mov.", ListaCampos.DB_PK, true);
    adicCampo(txtDescTipoMov, 90, 20, 300, 20,"DescTipoMov","Descrição do tipo de movimento", ListaCampos.DB_SI, true);
    adicCampo(txtCodModNota, 7, 60, 80, 20,"CodModNota","Cód.mod.nota", ListaCampos.DB_FK, true);
    adicDescFK(txtDescModNota, 90, 60, 300, 20, "DescModNota", "Descrição do modelo de nota");
    adicCampo(txtCodSerie, 7, 100, 80, 20,"Serie","Série", ListaCampos.DB_FK, txtDescSerie, true);
    adicDescFK(txtDescSerie, 90, 100, 300, 20, "DocSerie", "Documento atual");
    adicCampo(txtCodTab, 7, 140, 80, 20,"CodTab","Cód.tp.pc.", ListaCampos.DB_FK, txtDescTab, false);
    adicDescFK(txtDescTab, 90, 140, 300, 20, "DescTab", "Descrição da tab. de preços");
    adicCampo(txtCodTipoMov2, 7, 180, 80, 20,"CodTipoMovTM","Cód.mov.nf.", ListaCampos.DB_FK, txtDescTipoMov2, false);
    adicDescFK(txtDescTipoMov2, 90, 180, 300, 20, "DescTipoMov", "Descrição do movimento para nota.");
    adicDB(rgESTipoMov, 7, 220, 300, 30, "ESTipoMov", "Fluxo", true);
    
    adicDB(chbFiscalTipoMov, 7, 270, 107, 20, "FiscalTipoMov", "Lançamento", true);
    adicDB(chbEstoqTipoMov, 140, 270, 110, 20, "EstoqTipoMov", "Estoque", true);
    adicDB(chbSomaTipoMov, 260, 270, 200, 20, "SomaVdTipoMov", "Soma venda", true);
    
    adicDB(cbTipoMov, 7, 310, 250, 30, "TipoMov", "Tipo de movimento", true);
    adicCampo(txtEspecieTipomov, 280,320,80 ,20, "EspecieTipomov", "Espécie", ListaCampos.DB_SI, true); 
    lcCampos.setQueryInsert(false);
    
    txtCodTipoMov2.setNomeCampo("CodTipoMov");
    
    setListaCampos( true, "TIPOMOV", "EQ");
  }
  public void setConexao(Connection cn) {
    super.setConexao(cn);
  	lcTipoMov.setConexao(cn);
  	lcModNota.setConexao(cn);
    lcSerie.setConexao(cn);
    lcTab.setConexao(cn);
  }
}
