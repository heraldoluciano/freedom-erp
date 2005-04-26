/**
 * @version 23/04/2004 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.pcp <BR>
 * Classe: @(#)FEstFase.java <BR>
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
import org.freedom.componentes.JPanelPad;
import org.freedom.modulos.std.DLBuscaProd;
import org.freedom.telas.FDetalhe;

public class FEstFase extends FDetalhe {
  private JPanelPad pinCab = new JPanelPad();
  private JPanelPad pinDet = new JPanelPad();
  private JTextFieldPad txtCodProd = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldFK txtDescProd = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldPad txtDescEst = new JTextFieldPad(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldPad txtQtdEst = new JTextFieldPad(JTextFieldPad.TP_DECIMAL,15,3);
  private JTextFieldPad txtCodFase = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldFK txtDescFase = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldPad txtNumSeqEf = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtCodTpRec = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldFK txtDescTpRec = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldPad txtTempoEf = new JTextFieldPad(JTextFieldPad.TP_DECIMAL,15,3);
  private ListaCampos lcProd = new ListaCampos(this,"PD");
  private ListaCampos lcFase = new ListaCampos(this,"FS");
  private ListaCampos lcTipoRec = new ListaCampos(this,"TR");
  private int iCodProd;
  
  public FEstFase() {
  	this(0);
  }
  
  public FEstFase(int iCodProd) {
    setTitulo("Fases da estrutura");
    setAtribos( 70, 40, 550, 390);
    setAltCab(130);
    
    this.iCodProd = iCodProd;
    
    txtCodProd.setAtivo(false);
    txtDescEst.setAtivo(false);
    txtQtdEst.setAtivo(false);
    
    pinCab = new JPanelPad(500,90);
    setListaCampos(lcCampos);
    setPainel( pinCab, pnCliCab);
    
    lcProd.setUsaME(false);
    lcProd.add(new GuardaCampo( txtCodProd, "CodProd", "Cód.prod.", ListaCampos.DB_PK, true));
    lcProd.add(new GuardaCampo( txtDescProd, "DescProd", "Descrição do produto", ListaCampos.DB_SI, false));
    lcProd.setWhereAdic("TIPOPROD='F'");
    lcProd.montaSql(false, "PRODUTO", "EQ");
    lcProd.setQueryCommit(false);
    lcProd.setReadOnly(true);
    txtCodProd.setTabelaExterna(lcProd);
    txtDescProd.setListaCampos(lcProd);
    
    adicCampo(txtCodProd, 7, 20, 80, 20,"CodProd","Cód.prod.", ListaCampos.DB_PF, txtDescProd, true);
    adicDescFK(txtDescProd, 90, 20, 247, 20, "DescProd", "Descrição do produto");
    adicCampo(txtQtdEst, 340, 20, 80, 20,"QtdEst","Quantidade", ListaCampos.DB_SI, true);
    adicCampo(txtDescEst, 7, 60, 250, 20,"DescEst","Descrição da estrutura", ListaCampos.DB_SI,true);
    setListaCampos( false, "ESTRUTURA", "PP");
    lcCampos.setQueryInsert(false);
    
    
    lcFase.add(new GuardaCampo( txtCodFase,"CodFase", "Cód.fase", ListaCampos.DB_PK, true));
    lcFase.add(new GuardaCampo( txtDescFase, "DescFase", "Descrição da fase", ListaCampos.DB_SI, false));
    lcFase.montaSql(false, "FASE", "PP");
    lcFase.setQueryCommit(false);
    lcFase.setReadOnly(true);
    txtCodFase.setTabelaExterna(lcFase);
    txtDescFase.setListaCampos(lcFase);
    
    lcTipoRec.add(new GuardaCampo(txtCodTpRec,"CodTpRec", "Cód.tp.rec.", ListaCampos.DB_PK, true));
    lcTipoRec.add(new GuardaCampo(txtDescTpRec, "DescTpRec", "Descrição do tipo de recurso", ListaCampos.DB_SI, false));
    lcTipoRec.montaSql(false, "TIPOREC", "PP");
    lcTipoRec.setQueryCommit(false);
    lcTipoRec.setReadOnly(true);
    txtCodTpRec.setTabelaExterna(lcTipoRec);
    txtDescTpRec.setListaCampos(lcTipoRec);

    setAltDet(100);
    pinDet = new JPanelPad(590,110);
    setPainel( pinDet, pnDet);
    setListaCampos(lcDet);
    setNavegador(navRod);
    adicCampo(txtNumSeqEf, 7, 20, 40, 20,"SeqEf","Item", ListaCampos.DB_PK, true);
    adicCampo(txtCodFase, 50, 20, 77, 20,"CodFase","Cód.fase", ListaCampos.DB_FK, txtDescFase, true);
    //adicCampo(txtCodFase, 50, 20, 77, 20,"CodFase","Código",ListaCampos.DB_FK,true);
    adicDescFK(txtDescFase, 130, 20, 227, 20, "DescFase", "Descrição da fase");
    adicCampo(txtTempoEf, 360, 20, 100, 20,"TempoEf","Tempo (Seg.)",ListaCampos.DB_SI,true);
    adicCampo(txtCodTpRec, 7, 60, 80, 20,"CodTpRec","Cód.tp.rec.", ListaCampos.DB_SI, txtDescTpRec, true);
    //adicCampo(txtCodTpRec, 7, 60, 60, 20,"CodTpRec","Código",ListaCampos.DB_FK,true);
    adicDescFK(txtDescTpRec, 90, 60, 350, 20, "DescTpRec", "Descrição do tipo de recurso");
    setListaCampos( true, "ESTRUFASE", "PP");
    lcDet.setQueryInsert(false);
    montaTab();
    
    lcCampos.setReadOnly(true);

    tab.setTamColuna(50,0);
    tab.setTamColuna(150,2);
    tab.setTamColuna(120,3);
    tab.setTamColuna(170,5);
  }

  public void setConexao(Connection cn) {
    super.setConexao(cn);
    lcProd.setConexao(cn);
    lcFase.setConexao(cn);
    lcTipoRec.setConexao(cn);
    txtCodProd.setVlrInteger(new Integer(iCodProd));
    lcCampos.carregaDados();
    txtCodProd.setBuscaAdic(new DLBuscaProd(con,"CODPROD"));
   }        
}
