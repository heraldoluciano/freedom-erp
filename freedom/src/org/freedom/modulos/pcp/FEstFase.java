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
import org.freedom.componentes.Painel;
import org.freedom.telas.FDetalhe;
public class FEstFase extends FDetalhe {
  private Painel pinCab = new Painel();
  private Painel pinDet = new Painel();
  private JTextFieldPad txtCodProd = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldFK txtDescProd = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldPad txtDescEst = new JTextFieldPad(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldPad txtQtdEst = new JTextFieldPad(JTextFieldPad.TP_DECIMAL,15,3);
  private JTextFieldPad txtCodFase = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldFK txtDescFase = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldPad txtNumSeqEf = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtCodTpRec = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldFK txtDescTpRec = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldPad txtTempoEf = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private ListaCampos lcProd = new ListaCampos(this,"PD");
  private ListaCampos lcFase = new ListaCampos(this,"FS");
  private ListaCampos lcTipoRec = new ListaCampos(this,"TR");
  private int iCodProd;
  public FEstFase(int iCodProd) {
    setTitulo("Fases da estrutura");
    setAtribos( 70, 40, 445, 390);
    setAltCab(130);
    
    this.iCodProd = iCodProd;
    
    txtCodProd.setAtivo(false);
    txtDescEst.setAtivo(false);
    txtQtdEst.setAtivo(false);
    
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
    
    adicCampo(txtCodProd, 7, 20, 80, 20,"CodProd","Código",JTextFieldPad.TP_INTEGER,8,0,true,true,txtDescProd,true);
    adicDescFK(txtDescProd, 90, 20, 247, 20, "DescProd", "e descrição do produto", JTextFieldPad.TP_STRING, 50, 0);
    adicCampo(txtQtdEst, 340, 20, 80, 20,"QtdEst","Quantidade",JTextFieldPad.TP_DECIMAL,15,3,false,false,null,true);
    adicCampo(txtDescEst, 7, 60, 250, 20,"DescEst","Descrição",JTextFieldPad.TP_STRING,50,0,false,false,null,true);
    setListaCampos( false, "ESTRUTURA", "PP");
    lcCampos.setQueryInsert(false);
    
    
    lcFase.add(new GuardaCampo( txtCodFase,"CodFase", "Código", ListaCampos.DB_PK, true));
    lcFase.add(new GuardaCampo( txtDescFase, "DescFase", "Descrição", ListaCampos.DB_SI, false));
    lcFase.montaSql(false, "FASE", "PP");
    lcFase.setQueryCommit(false);
    lcFase.setReadOnly(true);
    txtCodFase.setTabelaExterna(lcFase);
    txtDescFase.setListaCampos(lcFase);
    
    lcTipoRec.add(new GuardaCampo(txtCodTpRec,"CodTpRec", "Código", ListaCampos.DB_PK, true));
    lcTipoRec.add(new GuardaCampo(txtDescTpRec, "DescTpRec", "Descrição", ListaCampos.DB_SI, false));
    lcTipoRec.montaSql(false, "TIPOREC", "PP");
    lcTipoRec.setQueryCommit(false);
    lcTipoRec.setReadOnly(true);
    txtCodTpRec.setTabelaExterna(lcTipoRec);
    txtDescTpRec.setListaCampos(lcTipoRec);

    setAltDet(100);
    pinDet = new Painel(590,110);
    setPainel( pinDet, pnDet);
    setListaCampos(lcDet);
    setNavegador(navRod);
    adicCampo(txtNumSeqEf, 7, 20, 40, 20,"SeqEf","Item",ListaCampos.DB_PK,true);
    adicCampo(txtCodFase, 50, 20, 77, 20,"CodFase","Código",JTextFieldPad.TP_INTEGER,8,0,false,true,txtDescFase,true);
    //adicCampo(txtCodFase, 50, 20, 77, 20,"CodFase","Código",ListaCampos.DB_FK,true);
    adicDescFK(txtDescFase, 130, 20, 227, 20, "DescFase", "e descrição da fase", JTextFieldPad.TP_STRING, 50, 0);
    adicCampo(txtTempoEf, 360, 20, 60, 20,"TempoEf","Tempo",ListaCampos.DB_SI,true);
    adicCampo(txtCodTpRec, 7, 60, 60, 20,"CodTpRec","Código",JTextFieldPad.TP_INTEGER,8,0,false,true,txtDescTpRec,true);
    //adicCampo(txtCodTpRec, 7, 60, 60, 20,"CodTpRec","Código",ListaCampos.DB_FK,true);
    adicDescFK(txtDescTpRec, 70, 60, 200, 20, "DescTpRec", "e descrição do tipo de recurso", JTextFieldPad.TP_STRING, 50, 0);
    setListaCampos( true, "ESTRUFASE", "PP");
    lcDet.setQueryInsert(false);
    montaTab();
    
    lcCampos.setReadOnly(true);

    tab.setTamColuna(50,0);
    tab.setTamColuna(150,2);
  }
/*  private boolean comRefProd() {
    boolean bResultado = false;
    PreparedStatement ps = null;
    ResultSet rs = null;
    try {
      ps = con.prepareStatement("SELECT USAREFPROD FROM SGPREFERE1 WHERE CODEMP=? AND CODFILIAL=?");
      ps.setInt(1,Aplicativo.iCodEmp);
      ps.setInt(2,ListaCampos.getMasterFilial("SGPREFERE1"));
      rs = ps.executeQuery();
      if (rs.next()) {
        bResultado = rs.getString("UsaRefProd").trim().equals("S");
      }
//      rs.close();
//      ps.close();
      if (!con.getAutoCommit())
      	con.commit();
    }
    catch(SQLException err) {
        Funcoes.mensagemErro(this,"Erro ao consultar a tabela PREFERE1!\n"+err.getMessage());
    }
    return bResultado;
  }*/
  public void execShow(Connection cn) {
    lcProd.setConexao(cn);
    lcFase.setConexao(cn);
    lcTipoRec.setConexao(cn);
    super.execShow(cn);
    txtCodProd.setVlrInteger(new Integer(iCodProd));
    lcCampos.carregaDados();  }        
}
