/**
 * @version 16/03/2004 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FTabJuros.java <BR>
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
import java.util.Vector;

import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.JComboBoxPad;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.JPanelPad;
import org.freedom.telas.FDetalhe;

public class FTabJuros extends FDetalhe {
  private JPanelPad pinCab = new JPanelPad(0,90);
  private JPanelPad pinDet = new JPanelPad(0,60);
  private JTextFieldPad txtCodTbJ = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtDescTbJ = new JTextFieldPad(JTextFieldPad.TP_STRING,50,0);
  private JComboBoxPad cbTipo = null;
  private JTextFieldPad txtAno = new JTextFieldPad(JTextFieldPad.TP_INTEGER,4,0);
  private JTextFieldPad txtMes = new JTextFieldPad(JTextFieldPad.TP_INTEGER,2,0);
  private JTextFieldPad txtPerc = new JTextFieldPad(JTextFieldPad.TP_DECIMAL,9,2);
  public FTabJuros() {
    setTitulo("Tabelas de juros");
    setAtribos( 50, 20, 380, 380);
    setAltCab(140);
    setListaCampos(lcCampos);
    setPainel( pinCab, pnCliCab);
    
    Vector vLabs = new Vector();
    vLabs.addElement("Diário");
    vLabs.addElement("Mensal");
    vLabs.addElement("Bimestral");
    vLabs.addElement("Trimestral");
    vLabs.addElement("Semestral");
    vLabs.addElement("Anual");
    Vector vVals = new Vector();
    vVals.addElement("D");
    vVals.addElement("M");
    vVals.addElement("B");
    vVals.addElement("T");
    vVals.addElement("S");
    vVals.addElement("A");
    cbTipo = new JComboBoxPad(vLabs,vVals,JComboBoxPad.TP_STRING, 1, 0);
    
    adicCampo(txtCodTbJ, 7, 20, 80, 20,"CodTbJ","Cód.tb.juros", ListaCampos.DB_PK, true);
    adicCampo(txtDescTbJ, 90, 20, 250, 20, "DescTbJ", "Descrição da tabela", ListaCampos.DB_SI,true);
    adicDB(cbTipo, 7, 60, 150, 30, "TipoTbJ", "Tipo", true);
    setListaCampos( false, "TBJUROS", "FN");
    lcCampos.setQueryInsert(false);
    setPainel( pinDet, pnDet);
    setListaCampos(lcDet);
    setNavegador(navRod);

    adicCampo(txtAno, 7, 20, 80, 20,"AnoItTbJ","Ano", ListaCampos.DB_PK, true);
    adicCampo(txtMes, 90, 20, 57, 20,"MesItTbJ","Mes",ListaCampos.DB_PK,true);
    adicCampo(txtPerc, 150, 20, 90, 20,"PercItTbJ","Perc.", ListaCampos.DB_SI, true);
    setListaCampos( false, "ITTBJUROS", "FN");
    lcDet.setQueryInsert(false);
    montaTab();
  }
}
