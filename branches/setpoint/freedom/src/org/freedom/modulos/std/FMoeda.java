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

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.freedom.acao.RadioGroupEvent;
import org.freedom.acao.RadioGroupListener;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Navegador;
import org.freedom.componentes.Painel;
import org.freedom.componentes.Tabela;
import org.freedom.telas.FTabDados;
public class FMoeda extends FTabDados implements RadioGroupListener {
  private Painel pinGeral = new Painel(370,220);
  private JTextFieldPad txtCodMoeda = new JTextFieldPad(4);
  private Vector vValsTipo = new Vector();
  private Vector vLabsTipo = new Vector();
  private Vector vValsAtua = new Vector();
  private Vector vLabsAtua = new Vector();
  private JRadioGroup rgTipo = null;
  private JRadioGroup rgAtua = null;
  private JTextFieldPad txtSingMoeda = new JTextFieldPad(20);
  private JTextFieldPad txtPlurMoeda = new JTextFieldPad(20);
  private JTextFieldPad txtDecsMoeda = new JTextFieldPad(20);
  private JTextFieldPad txtDecpMoeda = new JTextFieldPad(20);
  private JPanel pnCot = new JPanel(new BorderLayout());
  private Painel pinRod = new Painel(370,80);
  private Tabela tab = new Tabela();
  private JScrollPane spnTab = new JScrollPane(tab);
  private JTextFieldPad txtData = new JTextFieldPad(10);
  private JTextFieldPad txtValor = new JTextFieldPad(18);
  private ListaCampos lcCot = new ListaCampos(this);
  private Navegador navCot = new Navegador(true);
  public FMoeda() {
    setTitulo("Cadastro de Moedas");
    setAtribos(50,50,420,340);

    lcCot.setMaster(lcCampos);
    lcCampos.adicDetalhe(lcCot);
    lcCot.setTabela(tab);

    setPainel(pinGeral);
    adicTab("Geral",pinGeral);
    vValsTipo.addElement("I");
    vValsTipo.addElement("C");
    vLabsTipo.addElement("Indice de Valores");
    vLabsTipo.addElement("Moeda Corrente");
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
    vLabsAtua.addElement("Moeda Corrente");
    rgAtua = new JRadioGroup(3,2,vLabsAtua,vValsAtua);
    rgAtua.setVlrString("M");
    rgAtua.setAtivo(4,false);
    
    setListaCampos(lcCampos);
    adicCampo(txtCodMoeda, 7, 20, 50, 20, "CodMoeda", "Código", JTextFieldPad.TP_STRING, 4, 0, true, false, null,true);
    adicCampo(txtSingMoeda, 60, 20, 310, 20, "SingMoeda", "Nome no singular", JTextFieldPad.TP_STRING, 20, 0, false, false, null,true);
    adicDB(rgTipo, 7, 60, 363, 30, "TipoMoeda", "Tipo",JTextFieldPad.TP_STRING,true);
    adicCampo(txtPlurMoeda, 7, 110, 115, 20, "PlurMoeda", "Nome no plural", JTextFieldPad.TP_STRING, 20, 0, false, false, null,true);
    adicCampo(txtDecsMoeda, 125, 110, 115, 20, "DecsMoeda", "Decimal no singular", JTextFieldPad.TP_STRING, 20, 0, false, false, null,true);
    adicCampo(txtDecpMoeda, 243, 110, 127, 20, "DecpMoeda", "Decimal no plural", JTextFieldPad.TP_STRING, 20, 0, false, false, null,true);
    adicDB(rgAtua, 7, 150, 363, 70, "AtualizaMoeda", "Tempo de Atualização",JTextFieldPad.TP_STRING,true);
    setListaCampos( false, "MOEDA", "FN");
    lcCampos.setQueryInsert(false);    

    adicTab("Cotação",pnCot);
    setPainel( pinRod, pnCot);
    setListaCampos(lcCot);
    setNavegador(navCot);
    pnCot.add(pinRod, BorderLayout.SOUTH);
    pnCot.add(spnTab, BorderLayout.CENTER);

    txtData.setTipo(JTextFieldPad.TP_DATE,10,0);
    txtValor.setTipo(JTextFieldPad.TP_DECIMAL,15,2);

      pinRod.adic(navCot,0,45,270,25);
        
    adicCampo(txtData, 7, 20, 80, 20, "DataCot", "Data", JTextFieldPad.TP_DATE, 10, 0, true, false, null,true);
    adicCampo(txtValor, 90, 20, 80, 20, "ValorCot", "Valor", JTextFieldPad.TP_DECIMAL, 15, 3, false, false, null,true);
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
