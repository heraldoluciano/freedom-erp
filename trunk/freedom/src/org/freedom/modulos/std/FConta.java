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
import java.sql.Connection;
import java.util.Vector;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.telas.FDados;
public class FConta extends FDados {
  private JTextFieldPad txtAgConta = new JTextFieldPad( JTextFieldPad.TP_STRING, 6, 0);
  private JTextFieldPad txtNumConta = new JTextFieldPad(JTextFieldPad.TP_STRING, 10, 0);
  private JTextFieldPad txtDescConta = new JTextFieldPad(JTextFieldPad.TP_STRING, 50, 0);
  private JTextFieldPad txtCodBanco = new JTextFieldPad(JTextFieldPad.TP_STRING,3,0);
  private JTextFieldPad txtDataConta = new JTextFieldPad(JTextFieldPad.TP_DATE, 10, 0);
  private JTextFieldPad txtCodMoeda = new JTextFieldPad(JTextFieldPad.TP_STRING,4,0);
  private JTextFieldPad txtCodPlan = new JTextFieldPad(JTextFieldPad.TP_STRING,13,0);
  private JTextFieldFK txtDescBanco = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);
  private JTextFieldFK txtDescMoeda = new JTextFieldFK(JTextFieldPad.TP_STRING, 50,0);
  private JTextFieldFK txtDescPlan = new JTextFieldFK(JTextFieldPad.TP_STRING, 40, 0);
  private Vector vValsTipo = new Vector();
  private Vector vLabsTipo = new Vector();
  private JRadioGroup rgTipo = null;
  private ListaCampos lcBanco = new ListaCampos(this,"BO");
  private ListaCampos lcMoeda = new ListaCampos(this,"MA");
  private ListaCampos lcPlan = new ListaCampos(this,"PN");
  public FConta() {
    setTitulo("Cadastro de Contas");
    setAtribos(50,50,415,290);

    lcBanco.add(new GuardaCampo( txtCodBanco, "CodBanco", "Cód.banco", ListaCampos.DB_PK, false));
    lcBanco.add(new GuardaCampo( txtDescBanco, "NomeBanco", "Nome do banco", ListaCampos.DB_SI, false));
    lcBanco.montaSql(false, "BANCO", "FN");    
    lcBanco.setQueryCommit(false);
    lcBanco.setReadOnly(true);
    txtCodBanco.setTabelaExterna(lcBanco);

    lcMoeda.add(new GuardaCampo( txtCodMoeda, "CodMoeda", "Cód.mda.",ListaCampos.DB_PK ,true));
    lcMoeda.add(new GuardaCampo( txtDescMoeda, "SingMoeda", "Descrição da moeda", ListaCampos.DB_SI, false));
    lcMoeda.montaSql(false, "MOEDA", "FN");    
    lcMoeda.setQueryCommit(false);
    lcMoeda.setReadOnly(true);
    txtCodMoeda.setTabelaExterna(lcMoeda);
    
    lcPlan.add(new GuardaCampo( txtCodPlan, "CodPlan", "Cód.tp.lanç.", ListaCampos.DB_PK, true));
    lcPlan.add(new GuardaCampo( txtDescPlan, "DescPlan", "Descrição do tipo de lançamento", ListaCampos.DB_SI, false));
    lcPlan.montaSql(false, "PLANEJAMENTO", "FN");    
    lcPlan.setQueryCommit(false);
    lcPlan.setReadOnly(true);
    txtCodPlan.setTabelaExterna(lcPlan);
    
    vValsTipo.addElement("B");
    vValsTipo.addElement("C");
    vLabsTipo.addElement("Bancos");
    vLabsTipo.addElement("Caixa");
    rgTipo = new JRadioGroup(1,2,vLabsTipo,vValsTipo);

    adicCampo(txtNumConta, 7, 20, 110, 20, "NumConta", "Nº da conta", ListaCampos.DB_PK, true);
    adicCampo(txtDescConta, 120, 20, 270, 20, "DescConta", "Descrição da conta", ListaCampos.DB_SI, true);
    adicCampo(txtAgConta, 7, 60, 60, 20, "AgenciaConta", "Agência", ListaCampos.DB_SI, false);
    adicCampo(txtCodBanco, 70, 60, 80, 20, "CodBanco", "Cód.banco", ListaCampos.DB_FK, false);
    adicDescFK(txtDescBanco, 153, 60, 237, 20, "NomeBanco", "Descrição do banco");
    adicCampo(txtDataConta, 7, 100, 80, 20, "DataConta", "Data", ListaCampos.DB_SI, true);
    adicCampo(txtCodMoeda, 90, 100, 60, 20, "CodMoeda", "Cód.mda.", ListaCampos.DB_FK, true);
    adicDescFK(txtDescMoeda, 153, 100, 237, 20, "SingMoeda", "Descrição da moeda");
    adicCampo(txtCodPlan, 7, 140, 140, 20, "CodPlan", "Cód.tp.lanç.", ListaCampos.DB_FK, true);
    adicDescFK(txtDescPlan, 150, 140, 240, 20, "DescPlan", "Descrição do tipo de lançamento");
    adicDB(rgTipo, 7, 180, 383, 30, "TipoConta", "Tipo", true);
    setListaCampos(false,"CONTA", "FN");
    lcCampos.setQueryInsert(false);    
  }
  public void setConexao(Connection cn) {
    super.setConexao(cn);
    lcBanco.setConexao(cn);
    lcMoeda.setConexao(cn);
    lcPlan.setConexao(cn);
  }
}

