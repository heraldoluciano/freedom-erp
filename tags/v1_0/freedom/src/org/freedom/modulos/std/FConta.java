/**
 * @version 11/02/2002 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FConta.java <BR>
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
import org.freedom.telas.FDados;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;

import java.sql.Connection;
import java.util.Vector;
public class FConta extends FDados {
  private JTextFieldPad txtAgConta = new JTextFieldPad();
  private JTextFieldPad txtNumConta = new JTextFieldPad();
  private JTextFieldPad txtDescConta = new JTextFieldPad();
  private JTextFieldPad txtCodBanco = new JTextFieldPad();
  private JTextFieldPad txtDataConta = new JTextFieldPad();
  private JTextFieldPad txtCodMoeda = new JTextFieldPad();
  private JTextFieldPad txtCodPlan = new JTextFieldPad();
  private JTextFieldFK txtDescBanco = new JTextFieldFK();
  private JTextFieldFK txtDescMoeda = new JTextFieldFK();
  private JTextFieldFK txtDescPlan = new JTextFieldFK();
  private Vector vValsTipo = new Vector();
  private Vector vLabsTipo = new Vector();
  private JRadioGroup rgTipo = null;
  private ListaCampos lcBanco = new ListaCampos(this,"BO");
  private ListaCampos lcMoeda = new ListaCampos(this,"MA");
  private ListaCampos lcPlan = new ListaCampos(this,"PN");
  public FConta() {
    setTitulo("Cadastro de Contas");
    setAtribos(50,50,415,290);

    txtCodBanco.setTipo(JTextFieldPad.TP_STRING,4,0);
    lcBanco.add(new GuardaCampo( txtCodBanco, 7, 100, 80, 20, "CodBanco", "Código", true, false, null, JTextFieldPad.TP_STRING,false),"txtCodBancox");
    lcBanco.add(new GuardaCampo( txtDescBanco, 90, 100, 207, 20, "NomeBanco", "Nome", false, false, null, JTextFieldPad.TP_STRING,false),"txtDescBancox");
    lcBanco.montaSql(false, "BANCO", "FN");    
    lcBanco.setQueryCommit(false);
    lcBanco.setReadOnly(true);
    txtCodBanco.setTabelaExterna(lcBanco);

    txtCodMoeda.setTipo(JTextFieldPad.TP_STRING,4,0);
    lcMoeda.add(new GuardaCampo( txtCodMoeda, 7, 100, 80, 20, "CodMoeda", "Código", true, false, null, JTextFieldPad.TP_STRING,true),"txtCodMoedax");
    lcMoeda.add(new GuardaCampo( txtDescMoeda, 90, 100, 207, 20, "SingMoeda", "Descrição", false, false, null, JTextFieldPad.TP_STRING,false),"txtDescMoedax");
    lcMoeda.montaSql(false, "MOEDA", "FN");    
    lcMoeda.setQueryCommit(false);
    lcMoeda.setReadOnly(true);
    txtCodMoeda.setTabelaExterna(lcMoeda);
    
    txtCodPlan.setTipo(JTextFieldPad.TP_STRING,13,0);
    lcPlan.add(new GuardaCampo( txtCodPlan, 7, 100, 80, 20, "CodPlan", "Código", true, false, null, JTextFieldPad.TP_STRING,true),"txtCodPlanx");
    lcPlan.add(new GuardaCampo( txtDescPlan, 90, 100, 207, 20, "DescPlan", "Descrição", false, false, null, JTextFieldPad.TP_STRING,false),"txtDescPlanx");
    lcPlan.montaSql(false, "PLANEJAMENTO", "FN");    
    lcPlan.setQueryCommit(false);
    lcPlan.setReadOnly(true);
    txtCodPlan.setTabelaExterna(lcPlan);
    
    vValsTipo.addElement("B");
    vValsTipo.addElement("C");
    vLabsTipo.addElement("Bancos");
    vLabsTipo.addElement("Caixa");
    rgTipo = new JRadioGroup(1,2,vLabsTipo,vValsTipo);

    adicCampo(txtNumConta, 7, 20, 110, 20, "NumConta", "Número da Conta", JTextFieldPad.TP_STRING, 10, 0, true, false, null, true);
    adicCampo(txtDescConta, 120, 20, 270, 20, "DescConta", "Descrição", JTextFieldPad.TP_STRING, 50, 0, false, false, null, true);
    adicCampo(txtAgConta, 7, 60, 60, 20, "AgenciaConta", "Agência", JTextFieldPad.TP_STRING, 6, 0, false, false, null, false);
    adicCampo(txtCodBanco, 70, 60, 80, 20, "CodBanco", "Código", JTextFieldPad.TP_STRING, 3, 0, false, true, null, false);
    adicDescFK(txtDescBanco, 153, 60, 237, 20, "NomeBanco", "e descrição do Banco", JTextFieldPad.TP_STRING, 50, 0);
    adicCampo(txtDataConta, 7, 100, 80, 20, "DataConta", "Data", JTextFieldPad.TP_DATE, 10, 0, false, false, null, true);
    adicCampo(txtCodMoeda, 90, 100, 60, 20, "CodMoeda", "Código", JTextFieldPad.TP_STRING, 4, 0, false, true, null, true);
    adicDescFK(txtDescMoeda, 153, 100, 237, 20, "SingMoeda", "e descrição da Moeda", JTextFieldPad.TP_STRING, 20, 0);
    adicCampo(txtCodPlan, 7, 140, 140, 20, "CodPlan", "Código", JTextFieldPad.TP_STRING, 13, 0, false, true, null, true);
    adicDescFK(txtDescPlan, 150, 140, 240, 20, "DescPlan", "e descrição do tipo de lanç.", JTextFieldPad.TP_STRING, 40, 0);
    adicDB(rgTipo, 7, 180, 383, 30, "TipoConta", "Tipo",JTextFieldPad.TP_STRING,true);
    setListaCampos(false,"CONTA", "FN");
    lcCampos.setQueryInsert(false);    
  }
  public void execShow(Connection cn) {
    lcBanco.setConexao(cn);
    lcMoeda.setConexao(cn);
    lcPlan.setConexao(cn);
    super.execShow(cn);
  }
}

