/**
 * @version 11/02/2002 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FMoeda.java <BR>
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
import java.awt.BorderLayout;
import java.util.Vector;

import org.freedom.componentes.JPanelPad;
import javax.swing.JScrollPane;

import org.freedom.acao.RadioGroupEvent;
import org.freedom.acao.RadioGroupListener;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Navegador;
import org.freedom.componentes.Tabela;
import org.freedom.telas.FTabDados;

public class FMoeda extends FTabDados implements RadioGroupListener {
	private static final long serialVersionUID = 1L;

  private JPanelPad pinGeral = new JPanelPad(370,220);

  private Vector vValsTipo = new Vector();
  private Vector vLabsTipo = new Vector();
  private Vector vValsAtua = new Vector();
  private Vector vLabsAtua = new Vector();
  private JRadioGroup rgTipo = null;
  private JRadioGroup rgAtua = null;
  private JTextFieldPad txtCodMoeda = new JTextFieldPad(JTextFieldPad.TP_STRING, 4, 0);
  private JTextFieldPad txtSingMoeda = new JTextFieldPad(JTextFieldPad.TP_STRING, 20, 0);
  private JTextFieldPad txtPlurMoeda = new JTextFieldPad(JTextFieldPad.TP_STRING, 20, 0);
  private JTextFieldPad txtDecsMoeda = new JTextFieldPad(JTextFieldPad.TP_STRING, 20, 0);
  private JTextFieldPad txtDecpMoeda = new JTextFieldPad(JTextFieldPad.TP_STRING, 20, 0);
  private JTextFieldPad txtData = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
  private JTextFieldPad txtValor = new JTextFieldPad(JTextFieldPad.TP_NUMERIC,15,3);
  private JPanelPad pnCot = new JPanelPad(JPanelPad.TP_JPANEL,new BorderLayout());
  private JPanelPad pinRod = new JPanelPad(370,80);
  private Tabela tab = new Tabela();
  private JScrollPane spnTab = new JScrollPane(tab);
  private ListaCampos lcCot = new ListaCampos(this);
  private Navegador navCot = new Navegador(true);
  public FMoeda() {
  	super();
    setTitulo("Cadastro de Moedas");
    setAtribos(50,50,420,340);

    lcCot.setMaster(lcCampos);
    lcCampos.adicDetalhe(lcCot);
    lcCot.setTabela(tab);

    setPainel(pinGeral);
    adicTab("Geral",pinGeral);
    vValsTipo.addElement("I");
    vValsTipo.addElement("C");
    vLabsTipo.addElement("Indice de valores");
    vLabsTipo.addElement("Moeda corrente");
    rgTipo = new JRadioGroup(1,2,vLabsTipo,vValsTipo);
    rgTipo.addRadioGroupListener(this);
    vValsAtua.addElement("D");
    vValsAtua.addElement("S");
    vValsAtua.addElement("M");
    vValsAtua.addElement("A");
    vValsAtua.addElement("C");
    vLabsAtua.addElement("Diária");
    vLabsAtua.addElement("Semanal");
    vLabsAtua.addElement("Mensal");
    vLabsAtua.addElement("Anual");
    vLabsAtua.addElement("Moeda corrente");
    rgAtua = new JRadioGroup(3,2,vLabsAtua,vValsAtua);
    rgAtua.setVlrString("M");
    rgAtua.setAtivo(4,false);
    
    setListaCampos(lcCampos);
    adicCampo(txtCodMoeda, 7, 20, 70, 20, "CodMoeda", "Cód.mda.", ListaCampos.DB_PK, true);
    adicCampo(txtSingMoeda, 80, 20, 315, 20, "SingMoeda", "Nome no singular", ListaCampos.DB_SI, true);
    adicDB(rgTipo, 7, 60, 388, 30, "TipoMoeda", "Tipo",true);
    adicCampo(txtPlurMoeda, 7, 110, 120, 20, "PlurMoeda", "Nome no plural", ListaCampos.DB_SI, true);
    adicCampo(txtDecsMoeda, 130, 110, 125, 20, "DecsMoeda", "Decimal no singular", ListaCampos.DB_SI, true);
    adicCampo(txtDecpMoeda, 258, 110, 137, 20, "DecpMoeda", "Decimal no plural", ListaCampos.DB_SI, true);
    adicDB(rgAtua, 7, 150, 388, 70, "AtualizaMoeda", "Tempo de atualização", true);
    setListaCampos( false, "MOEDA", "FN");
    lcCampos.setQueryInsert(false);    

    adicTab("Cotação",pnCot);
    setPainel( pinRod, pnCot);
    setListaCampos(lcCot);
    setNavegador(navCot);
    pnCot.add(pinRod, BorderLayout.SOUTH);
    pnCot.add(spnTab, BorderLayout.CENTER);

    pinRod.adic(navCot,0,45,270,25);
        
    adicCampo(txtData, 7, 20, 80, 20, "DataCot", "Data", ListaCampos.DB_PK,true);
    adicCampo(txtValor, 90, 20, 80, 20, "ValorCot", "Valor", ListaCampos.DB_SI,true);
    setListaCampos( false, "COTMOEDA", "FN");
    lcCot.setQueryInsert(false);
    lcCot.montaTab();
  }
  public void valorAlterado(RadioGroupEvent rgevt) {
    if (rgTipo.getVlrString().compareTo("C") == 0) {
      rgAtua.setVlrString("C");
      rgAtua.setAtivo(0,false);
      rgAtua.setAtivo(1,false);
      rgAtua.setAtivo(2,false);
      rgAtua.setAtivo(3,false);
      rgAtua.setAtivo(4,true);
    }
    else {
      rgAtua.setVlrString("M");
      rgAtua.setAtivo(0,true);
      rgAtua.setAtivo(1,true);
      rgAtua.setAtivo(2,true);
      rgAtua.setAtivo(3,true);
      rgAtua.setAtivo(4,false);
    }
  }
}
