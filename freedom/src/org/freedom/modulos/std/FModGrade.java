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
import org.freedom.componentes.Painel;
import org.freedom.telas.FDetalhe;
public class FModGrade extends FDetalhe {
  private Painel pinCab = new Painel();
  private Painel pinDet = new Painel();
  private JTextFieldPad txtCodModG = new JTextFieldPad(8);
  private JTextFieldPad txtDescModG = new JTextFieldPad(40);
  private JTextFieldPad txtCodProd = new JTextFieldPad(8);
  private JTextFieldPad txtDescProdModG = new JTextFieldPad(20);
  private JTextFieldPad txtRefModG = new JTextFieldPad(10);
  private JTextFieldPad txtCodFabModG = new JTextFieldPad(10);
  private JTextFieldPad txtCodBarModG = new JTextFieldPad(10);
  private JTextFieldPad txtCodItModG = new JTextFieldPad(8);
  private JTextFieldPad txtDescItModG = new JTextFieldPad(8);
  private JTextFieldPad txtCodVarG = new JTextFieldPad(8);
  private JTextFieldPad txtRefItModG = new JTextFieldPad(8);
  private JTextFieldPad txtCodFabItModG = new JTextFieldPad(8);
  private JTextFieldPad txtCodBarItModG = new JTextFieldPad(8);
  private JTextFieldFK txtDescProd = new JTextFieldFK();
  private JTextFieldFK txtDescVarG = new JTextFieldFK();
  private JTextFieldPad txtRefProd = new JTextFieldPad(8);
  private ListaCampos lcProd = new ListaCampos(this,"PD");
  private ListaCampos lcVarG = new ListaCampos(this,"VG");
  public FModGrade() {
    setTitulo("Cadastro de Modelos da Grade");
    setAtribos( 50, 20, 600, 380);
    setAltCab(120);
    pinCab = new Painel(590,110);
    setListaCampos(lcCampos);
    setPainel( pinCab, pnCliCab);
    
    txtCodProd.setTipo(JTextFieldPad.TP_INTEGER,8,0);
    txtRefProd.setTipo(JTextFieldPad.TP_STRING,13,0);
    lcProd.add(new GuardaCampo( txtCodProd, 7, 100, 80, 20, "CodProd", "Cód.prod.", true, false, null, JTextFieldPad.TP_INTEGER,true),"txtCodProdx");
    lcProd.add(new GuardaCampo( txtRefProd, 90, 100, 207, 20, "RefProd", "Referência do produto", false, false, null, JTextFieldPad.TP_STRING,false),"txtDescProdx");
    lcProd.add(new GuardaCampo( txtDescProd, 90, 100, 207, 20, "DescProd", "Descrição do produto", false, false, null, JTextFieldPad.TP_STRING,false),"txtDescProdx");
    lcProd.setWhereAdic("ATIVOPROD='S'");
    lcProd.montaSql(false, "PRODUTO", "EQ");
    lcProd.setQueryCommit(false);
    lcProd.setReadOnly(true);
    txtCodProd.setTabelaExterna(lcProd);
    
    adicCampo(txtCodModG, 7, 20, 70, 20,"CodModG","Cód.mod.g.",JTextFieldPad.TP_INTEGER,8,0,true,false,null,true);
    adicCampo(txtDescModG, 80, 20, 197, 20,"DescModG","Descrição do modelo de grade",JTextFieldPad.TP_STRING,40,0,false,false,null,true);
    adicCampo(txtCodProd, 280, 20, 77, 20,"CodProd","Cód.prod.",JTextFieldPad.TP_INTEGER,8,0,false,true,null,true);
    adicDescFK(txtDescProd, 360, 20, 200, 20, "DescProd", "Descrição do produto", JTextFieldPad.TP_STRING, 40, 0);
    adicCampo(txtDescProdModG, 7, 60, 270, 20,"DescProdModG","Descrição inicial",JTextFieldPad.TP_STRING,20,0,false,false,null,true);
    adicCampo(txtRefModG, 280, 60, 87, 20,"RefModG","Ref.inic.",JTextFieldPad.TP_STRING,10,0,false,false,null,true);
    adicCampo(txtCodFabModG, 370, 60, 87, 20,"CodFabModG","Cód.fab.inic.",JTextFieldPad.TP_STRING,10,0,false,false,null,true);
    adicCampo(txtCodBarModG, 460, 60, 100, 20,"CodBarModG","Cód.bar.inic.",JTextFieldPad.TP_STRING,10,0,false,false,null,true);
    setListaCampos( true, "MODGRADE", "EQ");
    setAltDet(120);
    pinDet = new Painel(590,110);
    setPainel( pinDet, pnDet);
    setListaCampos(lcDet);
    setNavegador(navRod);

    txtCodVarG.setTipo(JTextFieldPad.TP_INTEGER,8,0);
    lcVarG.add(new GuardaCampo( txtCodVarG, 7, 100, 80, 20, "CodVarG", "Cód.var.g.", true, false, null, JTextFieldPad.TP_INTEGER,true),"txtCodVarGx");
    lcVarG.add(new GuardaCampo( txtDescVarG, 90, 100, 207, 20, "DescVarG", "Descrição da variante", false, false, null, JTextFieldPad.TP_STRING,false),"txtDescVarGx");
    lcVarG.montaSql(false, "VARGRADE", "EQ");
    lcVarG.setQueryCommit(false);
    lcVarG.setReadOnly(true);
    txtCodVarG.setTabelaExterna(lcVarG);
    
    adicCampo(txtCodItModG, 7, 20, 70, 20,"CodItModG","Item",JTextFieldPad.TP_INTEGER,8,0,true,false,null,true);
    adicCampo(txtCodVarG, 80, 20, 77, 20,"CodVarG","Cód.var.g.",JTextFieldPad.TP_INTEGER,8,0,false,true,null,true);
    adicDescFK(txtDescVarG, 160, 20, 197, 20, "DescVarG", "Descrição da variante", JTextFieldPad.TP_STRING, 40, 0);
    adicCampo(txtDescItModG, 360, 20, 200, 20,"DescItModG","Descrição",JTextFieldPad.TP_STRING,20,0,false,false,null,true);
    adicCampo(txtRefItModG, 7, 60, 87, 20,"RefItModG","Ref.inicial",JTextFieldPad.TP_STRING,10,0,false,false,null,true);
    adicCampo(txtCodFabItModG, 100, 60, 87, 20,"CodFabItModG","Cód.fab.inic.",JTextFieldPad.TP_STRING,10,0,false,false,null,true);
    adicCampo(txtCodBarItModG, 190, 60, 100, 20,"CodBarItModG","Cód.bar.inic.",JTextFieldPad.TP_STRING,10,0,false,false,null,true);
    setListaCampos( true, "ITMODGRADE", "EQ");
    montaTab();
  }
  public void execShow(Connection cn) {
    lcProd.setConexao(cn);
    lcVarG.setConexao(cn);
    super.execShow(cn);
  }        
}
