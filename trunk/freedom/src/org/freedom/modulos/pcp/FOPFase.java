/**
 * @version 06/05/2004 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.pcp <BR>
 * Classe: @(#)FOPFase.java <BR>
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

package org.freedom.modulos.pcp;
import java.sql.Connection;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Painel;
import org.freedom.telas.FDetalhe;

public class FOPFase extends FDetalhe {
  private Painel pinCab = new Painel();
  private Painel pinDet = new Painel();
  private JTextFieldPad txtCodOP = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldFK txtCodProd = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldFK txtDescProd = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldPad txtDtEmit = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
  private JTextFieldPad txtDtValid = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
  private JTextFieldPad txtQtdOP = new JTextFieldPad(JTextFieldPad.TP_DECIMAL,15,3);
  private JTextFieldPad txtCodFase = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldFK txtDescFase = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldPad txtNumSeqOf = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtCodRec = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldFK txtDescRec = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldPad txtTempoOf = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private ListaCampos lcProd = new ListaCampos(this,"PD");
  private ListaCampos lcFase = new ListaCampos(this,"FS");
  private ListaCampos lcRec = new ListaCampos(this,"RP");
  private int iCodOP;
  public FOPFase(int iCodOP) {
    setTitulo("Fases da OP");
    setAtribos( 70, 40, 500, 390);
    setAltCab(130);
    
    this.iCodOP = iCodOP;
    
    txtCodOP.setAtivo(false);
    txtCodProd.setAtivo(false);
    txtDtEmit.setAtivo(false);
    txtDtValid.setAtivo(false);
    txtQtdOP.setAtivo(false);
    
    pinCab = new Painel(500,90);
    setListaCampos(lcCampos);
    setPainel( pinCab, pnCliCab);
    
    lcProd.setUsaME(false);
    lcProd.add(new GuardaCampo( txtCodProd, 7, 100, 80, 20, "CodProd", "Cód.prod.", true, false, null, JTextFieldPad.TP_INTEGER,true),"txtCodVendax");
    lcProd.add(new GuardaCampo( txtDescProd, 90, 100, 207, 20, "DescProd", "Descrição do produto", false, false, null, JTextFieldPad.TP_STRING,false),"txtDescVendax");
    lcProd.setWhereAdic("TIPOPROD='F'");
    lcProd.montaSql(false, "PRODUTO", "EQ");
    lcProd.setQueryCommit(false);
    lcProd.setReadOnly(true);
    txtCodProd.setTabelaExterna(lcProd);
    txtDescProd.setListaCampos(lcProd);
    
    adicCampo(txtCodOP, 7, 20, 80, 20,"CodOP","Nº OP",JTextFieldPad.TP_INTEGER,8,0,true,false,null,true);
    adicCampo(txtCodProd, 90, 20, 77, 20,"CodProd","Cód.prod.",JTextFieldPad.TP_INTEGER,8,0,false,true,txtDescProd,true);
    adicDescFK(txtDescProd, 170, 20, 197, 20, "DescProd", "Descrição do produto", JTextFieldPad.TP_STRING, 50, 0);
    adicCampo(txtQtdOP, 370, 20, 100, 20,"QtdProdOP","Quantidade",JTextFieldPad.TP_DECIMAL,15,3,false,false,null,true);
    adicCampo(txtDtEmit, 7, 60, 100, 20,"DtEmitOP","Emissão",JTextFieldPad.TP_DATE,10,0,false,false,null,true);
    adicCampo(txtDtValid, 110, 60, 100, 20,"DtValidPDOP","Valid.prod.",JTextFieldPad.TP_DATE,10,0,false,false,null,true);
    setListaCampos( false, "OP", "PP");
    lcCampos.setQueryInsert(false);
    
    
    lcFase.add(new GuardaCampo( txtCodFase,"CodFase", "Cód.fase", ListaCampos.DB_PK, true));
    lcFase.add(new GuardaCampo( txtDescFase, "DescFase", "Descrição da fase", ListaCampos.DB_SI, false));
    lcFase.montaSql(false, "FASE", "PP");
    lcFase.setQueryCommit(false);
    lcFase.setReadOnly(true);
    txtCodFase.setTabelaExterna(lcFase);
    txtDescFase.setListaCampos(lcFase);
    
    lcRec.add(new GuardaCampo(txtCodRec,"CodRecP", "Cód.rec.", ListaCampos.DB_PK, true));
    lcRec.add(new GuardaCampo(txtDescRec, "DescRecP", "Descrição do recurso de produção", ListaCampos.DB_SI, false));
    lcRec.montaSql(false, "RECURSO", "PP");
    lcRec.setQueryCommit(false);
    lcRec.setReadOnly(true);
    txtCodRec.setTabelaExterna(lcRec);
    txtDescRec.setListaCampos(lcRec);

    setAltDet(100);
    pinDet = new Painel(590,110);
    setPainel( pinDet, pnDet);
    setListaCampos(lcDet);
    setNavegador(navRod);
    adicCampo(txtNumSeqOf, 7, 20, 40, 20,"SeqOf","Item",ListaCampos.DB_PK,true);
    adicCampo(txtCodFase, 50, 20, 77, 20,"CodFase","Cód.fase",JTextFieldPad.TP_INTEGER,8,0,false,true,txtDescFase,true);
    adicDescFK(txtDescFase, 130, 20, 227, 20,"DescFase", "Descrição da fase", JTextFieldPad.TP_STRING, 50, 0);
    adicCampo(txtTempoOf, 360, 20, 100, 20,"TempoOf","Tempo (Seg.)",ListaCampos.DB_SI,true);
    adicCampo(txtCodRec, 7, 60, 60, 20,"CodRecP","Cód.rec.",JTextFieldPad.TP_INTEGER,8,0,false,true,txtDescRec,true);
    adicDescFK(txtDescRec, 70, 60, 200, 20, "DescRecP", "Descrição do recurso", JTextFieldPad.TP_STRING, 50, 0);
    setListaCampos( true, "OPFASE", "PP");
    lcDet.setQueryInsert(false);
    montaTab();
    
    lcCampos.setReadOnly(true);

    tab.setTamColuna(50,0);
    tab.setTamColuna(150,2);
    tab.setTamColuna(170,3);
    tab.setTamColuna(120,5);
  }
  public void execShow(Connection cn) {
    lcProd.setConexao(cn);
    lcFase.setConexao(cn);
    lcRec.setConexao(cn);
    super.execShow(cn);
    txtCodOP.setVlrInteger(new Integer(iCodOP));
    lcCampos.carregaDados();  }        
}
