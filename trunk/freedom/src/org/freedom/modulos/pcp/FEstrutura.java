/**
 * @version 01/09/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.pcp <BR>
 * Classe: @(#)FEstrutura.java <BR>
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import javax.swing.JButton;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.bmps.Icone;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Painel;
import org.freedom.telas.FDetalhe;
import org.freedom.telas.FPrincipal;

public class FEstrutura extends FDetalhe implements ActionListener, CarregaListener {
  private Painel pinCab = new Painel();
  private Painel pinDet = new Painel();
  private JTextFieldPad txtCodProd = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldFK txtDescProd = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldPad txtCodFase = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldFK txtDescFase = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldPad txtDescEst = new JTextFieldPad(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldPad txtQtdEst = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtNumSeq = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtCodProd2 = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldFK txtDescProd2 = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldPad txtQtdMat = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JButton btFase = new JButton("Fases",Icone.novo("btExecuta.gif"));
  private ListaCampos lcProd = new ListaCampos(this,"PD");
  private ListaCampos lcProd2 = new ListaCampos(this,"PD");
  private ListaCampos lcFase = new ListaCampos(this,"FS");
  FPrincipal fPrim = null;
  public FEstrutura() {
    setTitulo("Estrutura de produtos");
    setAtribos( 50, 20, 445, 390);
    setAltCab(130);
    
    btFase.setEnabled(false);

    pinCab = new Painel(500,90);
    setListaCampos(lcCampos);
    setPainel( pinCab, pnCliCab);
    
    lcProd.setUsaME(false);
    lcProd.add(new GuardaCampo( txtCodProd, 7, 100, 80, 20, "CodProd", "Código", true, false, null, JTextFieldPad.TP_INTEGER,true),"txtCodVendax");
    lcProd.add(new GuardaCampo( txtDescProd, 90, 100, 207, 20, "DescProd", "Descrição", false, false, null, JTextFieldPad.TP_STRING,false),"txtDescVendax");
    lcProd.setWhereAdic("TIPOPROD='F'");
    lcProd.montaSql(false, "PRODUTO", "EQ");
    lcProd.setQueryCommit(false);
    lcProd.setReadOnly(true);
    txtCodProd.setTabelaExterna(lcProd);
    txtDescProd.setListaCampos(lcProd);
    
    lcProd2.add(new GuardaCampo( txtCodProd2, 7, 100, 80, 20, "CodProd", "Código", true, false, null, JTextFieldPad.TP_INTEGER,true),"txtCodVendax");
    lcProd2.add(new GuardaCampo( txtDescProd2, 90, 100, 207, 20, "DescProd", "Descrição", false, false, null, JTextFieldPad.TP_STRING,false),"txtDescVendax");
    lcProd2.montaSql(false, "PRODUTO", "EQ");
    lcProd2.setQueryCommit(false);
    lcProd2.setReadOnly(true);
    txtCodProd2.setTabelaExterna(lcProd2);
    txtDescProd2.setListaCampos(lcProd2);

    lcFase.add(new GuardaCampo( txtCodFase, 7, 100, 80, 20, "CodFase", "Código", true, false, null, JTextFieldPad.TP_INTEGER,true));
    lcFase.add(new GuardaCampo( txtDescFase, 90, 100, 207, 20, "DescFase", "Descrição", false, false, null, JTextFieldPad.TP_STRING,false));
    lcFase.setDinWhereAdic("CODFASE IN (SELECT CODFASE FROM PPESTRUFASE WHERE " +
            "CODEMP=PPFASE.CODEMP AND CODFILIAL=PPFASE.CODFILIAL AND CODPROD=#N)",txtCodProd);
    lcFase.montaSql(false, "FASE", "PP");
    lcFase.setQueryCommit(false);
    lcFase.setReadOnly(true);
    txtCodFase.setTabelaExterna(lcFase);
    txtDescFase.setListaCampos(lcFase);

    adicCampo(txtCodProd, 7, 20, 80, 20,"CodProd","Código",JTextFieldPad.TP_INTEGER,8,0,true,true,txtDescProd,true);
    adicDescFK(txtDescProd, 90, 20, 247, 20, "DescProd", "e descrição do produto", JTextFieldPad.TP_STRING, 50, 0);
    adicCampo(txtQtdEst, 340, 20, 80, 20,"QtdEst","Quantidade",JTextFieldPad.TP_DECIMAL,15,3,false,false,null,true);
    adicCampo(txtDescEst, 7, 60, 253, 20,"DescEst","Descrição",JTextFieldPad.TP_STRING,50,0,false,false,null,true);
    adic(btFase,300,50,120,30);
    setListaCampos( false, "ESTRUTURA", "PP");
    lcCampos.setQueryInsert(false);
    setAltDet(100);
    pinDet = new Painel(590,110);
    setPainel( pinDet, pnDet);
    setListaCampos(lcDet);
    setNavegador(navRod);

    adicCampo(txtNumSeq, 7, 20, 40, 20,"SeqItEst","Item",JTextFieldPad.TP_INTEGER,8,0,true,false,null,true);
    adicCampo(txtCodProd2, 50, 20, 77, 20,"CodProdPD","Código",JTextFieldPad.TP_INTEGER,8,0,false,true,txtDescProd2,true);
    adicDescFK(txtDescProd2, 130, 20, 227, 20, "DescProd", "e descrição do produto", JTextFieldPad.TP_STRING, 50, 0);
    adicCampo(txtQtdMat, 360, 20, 60, 20,"QtdItEst","Quant.",JTextFieldPad.TP_DECIMAL,15,3,false,false,null,true);
    adicCampo(txtCodFase, 7, 60, 70, 20,"CodFase","Código",JTextFieldPad.TP_INTEGER,8,0,false,true,txtDescFase,true);
    adicDescFK(txtDescFase, 80, 60, 227, 20, "DescFase", "e descrição da fase", JTextFieldPad.TP_STRING, 50, 0);
    setListaCampos( true, "ITESTRUTURA", "PP");
    lcDet.setQueryInsert(false);
    montaTab();
    
    txtCodProd2.setNomeCampo("CodProd");
    
    btFase.addActionListener(this);
    lcCampos.addCarregaListener(this);
    
    tab.setTamColuna(50,0);
    tab.setTamColuna(150,2);
    tab.setTamColuna(150,5);
  }
  private void abreFase() {
    if (fPrim.temTela("Estrutura x Fase")==false) {
      FEstFase tela = new FEstFase(txtCodProd.getVlrInteger().intValue());
      fPrim.criatela("Estrutura x Fase",tela,con);
      tela.setConexao(con);
    }
  }
  public void setTelaPrim(FPrincipal fP) {
      fPrim = fP;
  }
  public void actionPerformed(ActionEvent evt) {
    if (evt.getSource() == btFase)
        abreFase();
    super.actionPerformed(evt);
  }
  public void execShow(Connection cn) {
    lcProd.setConexao(cn);
    lcProd2.setConexao(cn);
    lcFase.setConexao(cn);
    super.execShow(cn);
  }
  public void afterCarrega(CarregaEvent cevt) {
    if (cevt.getListaCampos() == lcCampos) {
       btFase.setEnabled(lcCampos.getStatus() == ListaCampos.LCS_SELECT);   
    }
  }        
  public void beforeCarrega(CarregaEvent cevt) {
  }
}
