/**
 * @version 11/02/2002 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FModGrade.java <BR>
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

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.JPanelPad;
import org.freedom.telas.FDetalhe;
public class FModGrade extends FDetalhe {
  private JPanelPad pinCab = new JPanelPad();
  private JPanelPad pinDet = new JPanelPad();
  private JTextFieldPad txtCodModG = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtDescModG = new JTextFieldPad(JTextFieldPad.TP_STRING,40,0);
  private JTextFieldPad txtCodProd = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtDescProdModG = new JTextFieldPad(JTextFieldPad.TP_STRING,20,0);
  private JTextFieldPad txtRefModG = new JTextFieldPad(JTextFieldPad.TP_STRING,10,0);
  private JTextFieldPad txtCodFabModG = new JTextFieldPad(JTextFieldPad.TP_STRING,10,0);
  private JTextFieldPad txtCodBarModG = new JTextFieldPad(JTextFieldPad.TP_STRING,10,0);
  private JTextFieldPad txtCodItModG = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtDescItModG = new JTextFieldPad(JTextFieldPad.TP_STRING,20,0);
  private JTextFieldPad txtCodVarG = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtRefItModG = new JTextFieldPad(JTextFieldPad.TP_STRING,6,0);
  private JTextFieldPad txtCodFabItModG = new JTextFieldPad(JTextFieldPad.TP_STRING,10,0);
  private JTextFieldPad txtCodBarItModG = new JTextFieldPad(JTextFieldPad.TP_STRING,10,0);
  private JTextFieldFK txtDescProd = new JTextFieldFK(JTextFieldPad.TP_STRING, 40, 0);
  private JTextFieldFK txtDescVarG = new JTextFieldFK(JTextFieldPad.TP_STRING, 40, 0);
  private JTextFieldPad txtRefProd = new JTextFieldPad(JTextFieldPad.TP_STRING,13,0);
  private ListaCampos lcProd = new ListaCampos(this,"PD");
  private ListaCampos lcVarG = new ListaCampos(this,"VG");
  public FModGrade() {
    setTitulo("Cadastro de Modelos da Grade");
    setAtribos( 50, 20, 600, 380);
    setAltCab(120);
    pinCab = new JPanelPad(590,110);
    setListaCampos(lcCampos);
    setPainel( pinCab, pnCliCab);
    
    lcProd.add(new GuardaCampo( txtCodProd, "CodProd", "Cód.prod.", ListaCampos.DB_PK, true));
    lcProd.add(new GuardaCampo( txtRefProd, "RefProd", "Referência do produto", ListaCampos.DB_SI, false));
    lcProd.add(new GuardaCampo( txtDescProd, "DescProd", "Descrição do produto", ListaCampos.DB_SI, false));
    lcProd.setWhereAdic("ATIVOPROD='S'");
    lcProd.montaSql(false, "PRODUTO", "EQ");
    lcProd.setQueryCommit(false);
    lcProd.setReadOnly(true);
    txtCodProd.setTabelaExterna(lcProd);
    
    adicCampo(txtCodModG, 7, 20, 70, 20,"CodModG","Cód.mod.g.", ListaCampos.DB_PK, true);
    adicCampo(txtDescModG, 80, 20, 197, 20,"DescModG","Descrição do modelo de grade", ListaCampos.DB_SI, true);
    adicCampo(txtCodProd, 280, 20, 77, 20,"CodProd","Cód.prod.", ListaCampos.DB_FK, true);
    adicDescFK(txtDescProd, 360, 20, 200, 20, "DescProd", "Descrição do produto");
    adicCampo(txtDescProdModG, 7, 60, 270, 20,"DescProdModG","Descrição inicial", ListaCampos.DB_SI, true);
    adicCampo(txtRefModG, 280, 60, 87, 20,"RefModG","Ref.inic.", ListaCampos.DB_SI, true);
    adicCampo(txtCodFabModG, 370, 60, 87, 20,"CodFabModG","Cód.fab.inic.", ListaCampos.DB_SI, true);
    adicCampo(txtCodBarModG, 460, 60, 100, 20,"CodBarModG","Cód.bar.inic.", ListaCampos.DB_SI, true);
    setListaCampos( true, "MODGRADE", "EQ");
    setAltDet(120);
    pinDet = new JPanelPad(590,110);
    setPainel( pinDet, pnDet);
    setListaCampos(lcDet);
    setNavegador(navRod);

    lcVarG.add(new GuardaCampo( txtCodVarG, "CodVarG", "Cód.var.g.", ListaCampos.DB_PK, true));
    lcVarG.add(new GuardaCampo( txtDescVarG, "DescVarG", "Descrição da variante", ListaCampos.DB_SI, false));
    lcVarG.montaSql(false, "VARGRADE", "EQ");
    lcVarG.setQueryCommit(false);
    lcVarG.setReadOnly(true);
    txtCodVarG.setTabelaExterna(lcVarG);
    
    adicCampo(txtCodItModG, 7, 20, 70, 20,"CodItModG","Item", ListaCampos.DB_PK, true);
    adicCampo(txtCodVarG, 80, 20, 77, 20,"CodVarG","Cód.var.g.", ListaCampos.DB_FK, true);
    adicDescFK(txtDescVarG, 160, 20, 197, 20, "DescVarG", "Descrição da variante");
    adicCampo(txtDescItModG, 360, 20, 200, 20,"DescItModG","Descrição", ListaCampos.DB_SI, true);
    adicCampo(txtRefItModG, 7, 60, 87, 20,"RefItModG","Ref.inicial", ListaCampos.DB_SI, true);
    adicCampo(txtCodFabItModG, 100, 60, 87, 20,"CodFabItModG","Cód.fab.inic.", ListaCampos.DB_SI, true);
    adicCampo(txtCodBarItModG, 190, 60, 100, 20,"CodBarItModG","Cód.bar.inic.", ListaCampos.DB_SI, true);
    setListaCampos( true, "ITMODGRADE", "EQ");
    montaTab();
  }
  public void setConexao(Connection cn) {
    super.setConexao(cn);
    lcProd.setConexao(cn);
    lcVarG.setConexao(cn);
    txtCodProd.setBuscaAdic(new DLBuscaProd(this,con,"CODPROD"));
  }        
}
