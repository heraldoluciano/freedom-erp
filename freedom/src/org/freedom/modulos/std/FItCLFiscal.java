/**
 * @version 29/10/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FItCLFiscal.java <BR>
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
 * Detalhe para classificação fiscal.
 * 
 */

package org.freedom.modulos.std;
import java.sql.Connection;
import java.util.Vector;

import org.freedom.acao.CarregaEvent;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JComboBoxPad;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.telas.FDetalhe;


public class FItCLFiscal extends FDetalhe {

	private static final long serialVersionUID = 1L;

  private JPanelPad pinCab = new JPanelPad();
  private JPanelPad pinDet = new JPanelPad();
  private JTextFieldPad txtCodFisc = new JTextFieldPad(JTextFieldPad.TP_STRING,13,0);
  private JTextFieldPad txtDescFisc = new JTextFieldPad(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldPad txtCodTipoFisc = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldFK txtDescFiscCli = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
  private JTextFieldPad txtCodMens = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldFK txtMens = new JTextFieldFK(JTextFieldPad.TP_STRING,10000,0);
  private JTextFieldPad txtCodItClFiscal = new JTextFieldPad(JTextFieldPad.TP_INTEGER,5,0);
  private JTextFieldPad txtCodTratTrib = new JTextFieldPad(JTextFieldPad.TP_STRING, 2, 0);
  private JTextFieldFK txtDescTratTrib = new JTextFieldFK(JTextFieldPad.TP_STRING, 60, 0);
  private JTextFieldPad txtRedFisc = new JTextFieldPad(JTextFieldPad.TP_DECIMAL,9,2);
  private JTextFieldPad txtAliqICMS = new JTextFieldPad(JTextFieldPad.TP_DECIMAL,9,2);
  private JTextFieldPad txtAliqIPI = new JTextFieldPad(JTextFieldPad.TP_DECIMAL,9,2);
  private JCheckBoxPad cbNoUF = new JCheckBoxPad("Sim","S","N");
  private JComboBoxPad cbOrig = null;
  private Vector vValsOrig = new Vector();
  private Vector vLabsOrig = new Vector();
  private JRadioGroup rgTipoFisc = null;
  private Vector vTipoVals = new Vector();
  private Vector vTipoLabs = new Vector();
  private ListaCampos lcTratTrib = new ListaCampos(this,"TT");
  private ListaCampos lcTipoFiscCli = new ListaCampos(this,"FC");
  private ListaCampos lcMens = new ListaCampos(this,"ME");
  public FItCLFiscal () {
    setTitulo("Cadastro de variações de ICMS");
    setAtribos( 50, 50, 490, 470);

    txtCodFisc.setAtivo(false);
    txtDescFisc.setAtivo(false);

    setListaCampos(lcCampos);
    pnCab.remove(1); //Remove o navegador do cabeçalho.
    setAltCab(60);
    setPainel( pinCab, pnCliCab);
    adicCampo(txtCodFisc, 7, 20, 100, 20,"CodFisc","Cód.c.fisc.", ListaCampos.DB_PK, true);
    adicCampo(txtDescFisc, 110, 20, 340, 20,"DescFisc","Descrição do classificação fiscal", ListaCampos.DB_SI, true);
    setListaCampos( true, "CLFISCAL", "LF");
    lcCampos.setReadOnly(true);    


    lcTratTrib.add(new GuardaCampo( txtCodTratTrib, "CodTratTrib", "Cód.tr.trib.", ListaCampos.DB_PK, true));
    lcTratTrib.add(new GuardaCampo( txtDescTratTrib, "DescTratTrib", "Descrição do tratamento tributário", ListaCampos.DB_SI, false));
    lcTratTrib.montaSql(false, "TRATTRIB", "LF");
    lcTratTrib.setQueryCommit(false);
    lcTratTrib.setReadOnly(true);
    txtCodTratTrib.setTabelaExterna(lcTratTrib);

	lcTipoFiscCli.add(new GuardaCampo( txtCodTipoFisc, "CodFiscCli", "Cód.c.fisc.", ListaCampos.DB_PK, false));
	lcTipoFiscCli.add(new GuardaCampo( txtDescFiscCli, "DescFiscCli", "Descrição da classificação fiscal", ListaCampos.DB_SI, false));
	lcTipoFiscCli.montaSql(false, "TIPOFISCCLI", "LF");
	lcTipoFiscCli.setQueryCommit(false);
	lcTipoFiscCli.setReadOnly(true);
	txtCodTipoFisc.setTabelaExterna(lcTipoFiscCli);

	lcMens.add(new GuardaCampo( txtCodMens, "CodMens", "Cód.mems.", ListaCampos.DB_PK, false));
	lcMens.add(new GuardaCampo( txtMens, "Mens", "Mensagem", ListaCampos.DB_SI, false));
	lcMens.montaSql(false, "MENSAGEM", "LF");
	lcMens.setQueryCommit(false);
	lcMens.setReadOnly(true);
	txtCodMens.setTabelaExterna(lcMens);

	vTipoLabs.addElement("Isensão (não tributada)");
	vTipoLabs.addElement("Substituição Tributária");
	vTipoLabs.addElement("Não inside (outras)");
	vTipoLabs.addElement("Tributação Integral");
	vTipoVals.addElement("II");
	vTipoVals.addElement("FF");
	vTipoVals.addElement("NN");
	vTipoVals.addElement("TT");
	rgTipoFisc = new JRadioGroup( 2, 2, vTipoLabs, vTipoVals);

	vLabsOrig.addElement("<--Selecione-->");
    vLabsOrig.addElement("Nacional");
    vLabsOrig.addElement("Estrangeira - Importação direta");
    vLabsOrig.addElement("Estrangeira - Adquirida no mercado interno");
    vValsOrig.addElement("");
    vValsOrig.addElement("0");
    vValsOrig.addElement("1");
    vValsOrig.addElement("2");
    cbOrig = new JComboBoxPad(vLabsOrig,vValsOrig, JComboBoxPad.TP_STRING,1,0);

    setAltDet(260);
    setPainel( pinDet, pnDet);
    setListaCampos(lcDet);
    setNavegador(navRod);
    adicCampo(txtCodItClFiscal, 7, 20, 60, 20,"CodItFisc","Item", ListaCampos.DB_PK, true);
    adicCampo(txtCodTratTrib, 70, 20, 77, 20, "CodTratTrib", "Cód.tr.tab.", ListaCampos.DB_FK, txtDescTratTrib, true);
    adicDescFK(txtDescTratTrib, 150, 20, 300, 20, "DescTratTrib", "Descrição da tratamento tributário");
	adicCampo(txtRedFisc, 7, 60, 85, 20, "RedFisc", "Redução", ListaCampos.DB_SI, false);
    adicDB(cbOrig, 95, 60, 287, 25, "OrigFisc", "Origem",true);
	adicDB(cbNoUF, 385, 60, 80, 20, "NOUFITFISC", "No Estado",true);
	adicCampo(txtAliqICMS, 7, 105, 80, 20, "AliqFisc", "Alíquota ICMS", ListaCampos.DB_SI, false);
	adicCampo(txtAliqIPI, 90, 105, 80, 20, "AliqIPIFisc", "Alíquota IPI", ListaCampos.DB_SI, false);
	adicCampo(txtCodTipoFisc, 173, 105, 80, 20, "CodFiscCli", "Cód.fisc.cli.", ListaCampos.DB_FK, txtDescFiscCli, false);
	adicDescFK(txtDescFiscCli, 256, 105, 195, 20, "DescTratTrib", "Descrição do tipo fiscal de cliente");
	adicDB(rgTipoFisc, 7, 150, 450, 50, "TipoFisc", "Situação do ICMS:",true);
	adicCampo(txtCodMens, 7, 220, 80, 20, "CodMens", "Cód.mens.", ListaCampos.DB_FK, txtMens, false);
	adicDescFK(txtMens, 90, 220, 360, 20, "Mens", "Mensagem");
    setListaCampos( true, "ITCLFISCAL", "LF");
    lcDet.setQueryInsert(false);


    montaTab();
    
    tab.setTamColuna(190,2);
    
  }
  public void afterCarrega(CarregaEvent cevt) {
	if (cevt.getListaCampos() == lcCampos) {}
  }
  public void exec(String sCodFisc) {
	txtCodFisc.setVlrString(sCodFisc);
	lcCampos.carregaDados();
  }
  public void setConexao(Connection cn) {
	super.setConexao(cn);
	lcMens.setConexao(cn);      
	lcTratTrib.setConexao(cn);      
	lcTipoFiscCli.setConexao(cn);      
  }
}
