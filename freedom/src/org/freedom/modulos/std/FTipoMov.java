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
import org.freedom.telas.FDados;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JComboBoxPad;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;

import java.sql.Connection;
import java.util.Vector;
public class FTipoMov extends FDados {
  private JTextFieldPad txtCodTipoMov = new JTextFieldPad(8);
  private JTextFieldPad txtCodTipoMov2 = new JTextFieldPad(8);
  private JTextFieldPad txtDescTipoMov = new JTextFieldPad(40);
  private JTextFieldPad txtCodModNota = new JTextFieldPad(8);
  private JTextFieldPad txtCodSerie = new JTextFieldPad(4);
  private JTextFieldPad txtCodTab = new JTextFieldPad(4);
  private JTextFieldPad txtEspecieTipomov = new JTextFieldPad(4);
  private JTextFieldFK txtDescModNota = new JTextFieldFK();
  private JTextFieldFK txtDescSerie = new JTextFieldFK();
  private JTextFieldFK txtDescTab = new JTextFieldFK();
  private JTextFieldFK txtDescTipoMov2 = new JTextFieldFK();
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
  private JCheckBoxPad chbFiscalTipoMov = new JCheckBoxPad("Lanc. Fiscal?","S","N");
  private JCheckBoxPad chbEstoqTipoMov = new JCheckBoxPad("Cont. Estoque?","S","N");
  public FTipoMov() {
    setTitulo("Cadastro de Tipos de Movimento");
    setAtribos( 50, 50, 410, 440);

    txtCodModNota.setTipo(JTextFieldPad.TP_INTEGER,8,0);
    txtDescModNota.setTipo(JTextFieldPad.TP_STRING,30,0);    
    lcModNota.add(new GuardaCampo( txtCodModNota, 7, 100, 80, 20, "CodModNota", "Código", true, false, null, JTextFieldPad.TP_INTEGER,false),"txtCodModNotax");
    lcModNota.add(new GuardaCampo( txtDescModNota, 90, 100, 207, 20, "DescModNota", "Descrição", false, false, null, JTextFieldPad.TP_STRING,false),"txtDescModNotax");
    lcModNota.montaSql(false, "MODNOTA", "LF");    
    lcModNota.setQueryCommit(false);
    lcModNota.setReadOnly(true);
    txtCodModNota.setTabelaExterna(lcModNota);

    txtCodSerie.setTipo(JTextFieldPad.TP_STRING,4,0);
    txtDescSerie.setTipo(JTextFieldPad.TP_INTEGER,8,0);    
    lcSerie.add(new GuardaCampo( txtCodSerie, 7, 100, 80, 20, "Serie", "Código", true, false, null, JTextFieldPad.TP_STRING,false),"txtCodSeriex");
    lcSerie.add(new GuardaCampo( txtDescSerie, 90, 100, 207, 20, "DocSerie", "Nº. Doc", false, false, null, JTextFieldPad.TP_INTEGER,false),"txtDescSeriex");
    lcSerie.montaSql(false, "SERIE", "LF");
    lcSerie.setQueryCommit(false);
    lcSerie.setReadOnly(true);
    txtCodSerie.setTabelaExterna(lcSerie);

    txtCodTab.setTipo(JTextFieldPad.TP_INTEGER,8,0);
    txtDescTab.setTipo(JTextFieldPad.TP_STRING,40,0);    
    lcTab.add(new GuardaCampo( txtCodTab, 7, 100, 80, 20, "CodTab", "Código", true, false, null, JTextFieldPad.TP_INTEGER,false),"txtCodTabx");
    lcTab.add(new GuardaCampo( txtDescTab, 90, 100, 207, 20, "DescTab", "Descrição", false, false, null, JTextFieldPad.TP_STRING,false),"txtDescTabx");
    lcTab.montaSql(false, "TABPRECO", "VD");
    lcTab.setQueryCommit(false);
    lcTab.setReadOnly(true);
    txtCodTab.setTabelaExterna(lcTab);

    txtCodTipoMov2.setTipo(JTextFieldPad.TP_INTEGER,8,0);
    txtDescTipoMov2.setTipo(JTextFieldPad.TP_STRING,40,0);    
    lcTipoMov.add(new GuardaCampo( txtCodTipoMov2, 7, 100, 80, 20, "CodTipoMov", "Código", true, false, null, JTextFieldPad.TP_INTEGER,false),"txtCodTabx");
    lcTipoMov.add(new GuardaCampo( txtDescTipoMov2, 90, 100, 207, 20, "DescTipoMov", "Descrição", false, false, null, JTextFieldPad.TP_STRING,false),"txtDescTabx");
    lcTipoMov.montaSql(false, "TIPOMOV", "EQ");
    lcTipoMov.setQueryCommit(false);
    lcTipoMov.setReadOnly(true);
    txtCodTipoMov2.setTabelaExterna(lcTipoMov);
    
    vLabs.addElement("Orçamento (Compra)");
    vLabs.addElement("Orçamento (Venda)");
    vLabs.addElement("Pedido (Compra)");
    vLabs.addElement("Pedido (Venda)");
    vLabs.addElement("Compra");
    vLabs.addElement("Venda");
	vLabs.addElement("Venda - ECF");
	vLabs.addElement("Venda - Televendas");
	vLabs.addElement("Devolução");
	vLabs.addElement("Cancelamento");
	vLabs.addElement("Bonificação");
	vLabs.addElement("Transferência");
	vLabs.addElement("Perda");
	vLabs.addElement("Consignação - Saída");
	vLabs.addElement("Consignação - Devolução");
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
	
    cbTipoMov = new JComboBoxPad(vLabs,vVals);
    cbTipoMov.setSelectedIndex(6);
    
    vLabsES.addElement("Entrada");
    vLabsES.addElement("Saída");
    vLabsES.addElement("Inventário");
    vValsES.addElement("E");
    vValsES.addElement("S");
    vValsES.addElement("I");
    
    rgESTipoMov = new JRadioGroup(1,2,vLabsES,vValsES);
 
    adicCampo(txtCodTipoMov, 7, 20, 80, 20,"CodTipoMov","Código",JTextFieldPad.TP_INTEGER,8,0,true,false,null,true);
    adicCampo(txtDescTipoMov, 90, 20, 300, 20,"DescTipoMov","Descrição",JTextFieldPad.TP_STRING,40,0,false,false,null,true);
    adicCampo(txtCodModNota, 7, 60, 80, 20,"CodModNota","Código",JTextFieldPad.TP_INTEGER,8,0,false,true,null,true);
    adicDescFK(txtDescModNota, 90, 60, 300, 20, "DescModNota", "e descrição do modelo de nota", JTextFieldPad.TP_STRING, 30, 0);
    adicCampo(txtCodSerie, 7, 100, 80, 20,"Serie","Série",JTextFieldPad.TP_STRING,4,0,false,true,txtDescSerie,true);
    adicDescFK(txtDescSerie, 90, 100, 300, 20, "DocSerie", "documento atual", JTextFieldPad.TP_INTEGER, 8, 0);
    adicCampo(txtCodTab, 7, 140, 80, 20,"CodTab","Código",JTextFieldPad.TP_INTEGER,8,0,false,true,txtDescTab,false);
    adicDescFK(txtDescTab, 90, 140, 300, 20, "DescTab", "e descrição da tab. de preços", JTextFieldPad.TP_STRING, 40, 0);
    adicCampo(txtCodTipoMov2, 7, 180, 80, 20,"CodTipoMovTM","Código",JTextFieldPad.TP_INTEGER,8,0,false,true,txtDescTipoMov2,false);
    adicDescFK(txtDescTipoMov2, 90, 180, 300, 20, "DescTipoMov", "e descrição do movimento para nota.", JTextFieldPad.TP_STRING, 40, 0);
    adicDB(rgESTipoMov, 7, 220, 300, 30, "ESTipoMov", "Fluxo",JTextFieldPad.TP_STRING,true);
    
    adicDB(chbFiscalTipoMov, 7, 270, 107, 20, "FiscalTipoMov", "Lançamento",JTextFieldPad.TP_STRING,true);
    adicDB(chbEstoqTipoMov, 140, 270, 110, 20, "EstoqTipoMov", "Estoque",JTextFieldPad.TP_STRING,true);
    
    adicDB(cbTipoMov, 7, 310, 250, 30, "TipoMov", "Tipo de Movimento",JTextFieldPad.TP_STRING,true);
    adicCampo(txtEspecieTipomov, 280,320,80 ,20, "EspecieTipomov", "Espécie", JTextFieldPad.TP_STRING,4,9,false,false,null,true); 
    lcCampos.setQueryInsert(false);
    
    txtCodTipoMov2.setNomeCampo("CodTipoMov");
    
    setListaCampos( true, "TIPOMOV", "EQ");
  }
  public void execShow(Connection cn) {
  	lcTipoMov.setConexao(cn);
  	lcModNota.setConexao(cn);
    lcSerie.setConexao(cn);
    lcTab.setConexao(cn);
    super.execShow(cn);
  }
}
