/**
 * @version 11/02/2002 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FPlanoPag.java <BR>
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
 * Agrupamento de produtos similares.
 * 
 */

package org.freedom.modulos.std;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.acao.InsertEvent;
import org.freedom.acao.InsertListener;
import org.freedom.acao.PostEvent;
import org.freedom.acao.PostListener;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.telas.FDetalhe;

public class FSimilar extends FDetalhe implements CarregaListener, InsertListener, PostListener {
  private JPanelPad pinCab = new JPanelPad();
  private JPanelPad pinDet = new JPanelPad();
  private JTextFieldPad txtCodSim = new JTextFieldPad(JTextFieldPad.TP_INTEGER,5,0);
  private JTextFieldPad txtDescSim = new JTextFieldPad(JTextFieldPad.TP_STRING,40,0);
  private JTextFieldPad txtCodProd = new JTextFieldPad(JTextFieldPad.TP_INTEGER,5,0);
  private JTextFieldFK txtDescProd = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
  private ListaCampos lcProd = new ListaCampos(this,"PD");
  public FSimilar () {
    setTitulo("Agrupamento de produtos similares");
    setAtribos( 50, 50, 700, 350);        

//  ********************  Lista campos adicional *****************************************************************    
    
    lcProd.setUsaME(false);
    lcProd.add(new GuardaCampo( txtCodProd, "CodProd", "Cód.prod.", ListaCampos.DB_PK, true));
    lcProd.add(new GuardaCampo( txtDescProd, "DescProd", "Descrição do produto", ListaCampos.DB_SI, false));
    lcProd.montaSql(false, "PRODUTO", "EQ");
    lcProd.setQueryCommit(false);
    lcProd.setReadOnly(true);
    txtCodProd.setListaCampos(lcProd);
    txtCodProd.setTabelaExterna(lcProd);   
    
//********************  Master ***********************************************************************************
    
    setAltCab(160);
    pinCab = new JPanelPad();
    setListaCampos(lcCampos);
    setPainel( pinCab, pnCliCab);

    adicCampo(txtCodSim, 7, 20, 70, 20,"CodSim","Cód.Agrup.",ListaCampos.DB_PK,null,true);
    adicCampo(txtDescSim, 80, 20, 217, 20,"DescSim","Descrição do agrupamento",ListaCampos.DB_SI,null,true);
    
    setListaCampos( true, "SIMILAR", "EQ");
    lcCampos.setQueryInsert(true);    

    setAltDet(60);
    pinDet = new JPanelPad(440,50);
    setPainel( pinDet, pnDet);

//  ********************  Detalhe ********************************************************************************    
    
    setListaCampos(lcDet);
    setNavegador(navRod);
     
    adicCampo(txtCodProd,7,20,60,20,"CodProd","Cód.prod",ListaCampos.DB_PK,true);
    adicDescFK(txtDescProd,70,20,217,20,"Descprod","Descrição do produto");

    setListaCampos( true, "ITSIMILAR", "EQ");

    lcDet.setQueryInsert(true);    
    lcDet.setQueryCommit(false);
    
    navRod.setAtivo(4,false);
    navRod.setAtivo(5,false);
    montaTab();
    lcCampos.addCarregaListener(this);
    lcCampos.addInsertListener(this);
    btImp.addActionListener(this);
    btPrevimp.addActionListener(this);
  }
  public void actionPerformed(ActionEvent evt) {
    if (evt.getSource() == btPrevimp) {
        imprimir(true);
    }
    else if (evt.getSource() == btImp) 
      imprimir(false);

    super.actionPerformed(evt);
  }
  private void imprimir(boolean bVisualizar) {
 /*

  	ImprimeOS imp = new ImprimeOS("",con);
    int linPag = imp.verifLinPag()-1;
    imp.montaCab();
    imp.setTitulo("Relatório de Planos de Pagamento");
    DLRPlanoPag dl = new DLRPlanoPag();
    dl.setVisible(true);
    if (dl.OK == false) {
      dl.dispose();
      return;
    }
    String sSQL = "SELECT PLANO.CODPLANOPAG,PLANO.DESCPLANOPAG,PLANO.PARCPLANOPAG,"+
                  "PARC.NROPARCPAG,PARC.PERCPAG,PARC.DIASPAG "+
                  "FROM FNPLANOPAG PLANO,FNPARCPAG PARC "+
                  "WHERE PARC.CODPLANOPAG=PLANO.CODPLANOPAG "+
                  "ORDER BY "+dl.getValor();
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sCodMaster = "";
    try {
      ps = con.prepareStatement(sSQL);
      rs = ps.executeQuery();
      imp.limpaPags();
      sCodMaster = "";
      while (rs.next()) {
         if (imp.pRow()==0) {
            imp.impCab(80, false);
            imp.say(imp.pRow()+0,0,""+imp.normal());
            imp.say(imp.pRow()+0,0,"");
            imp.say(imp.pRow()+0,2,"Código");
            imp.say(imp.pRow()+0,20,"Descrição");
            imp.say(imp.pRow()+0,70,"N. Parcel.");
            imp.say(imp.pRow()+1,0,""+imp.normal());
            imp.say(imp.pRow()+0,0,Funcoes.replicate("*",80));
         }

         if (!rs.getString("CodPlanoPag").equals(sCodMaster)) {
           if (sCodMaster.trim().length()!=0) {
             imp.say(imp.pRow()+1,0,""+imp.normal());
             imp.say(imp.pRow()+0,0,Funcoes.replicate("*",80));
           }
           imp.say(imp.pRow()+1,0,""+imp.normal());
           imp.say(imp.pRow()+0,2,rs.getString("CodPlanoPag"));
           imp.say(imp.pRow()+0,20,rs.getString("DescPlanoPag"));
           imp.say(imp.pRow()+0,70,rs.getString("ParcPlanoPag"));
           imp.say(imp.pRow()+1,0,""+imp.normal());
           imp.say(imp.pRow()+0,0,Funcoes.replicate("-",80));
           imp.say(imp.pRow()+1,0,""+imp.normal());
           imp.say(imp.pRow()+0,10,"Item");
           imp.say(imp.pRow()+0,35,"Perc.");
           imp.say(imp.pRow()+0,60,"Dias");
           imp.say(imp.pRow()+1,0,""+imp.normal());
           imp.say(imp.pRow()+0,0,Funcoes.replicate("-",80));
         }
         
         imp.say(imp.pRow()+1,0,""+imp.normal());
         imp.say(imp.pRow()+0,10,rs.getString("NroParcPag"));
         imp.say(imp.pRow()+0,35,rs.getString("PercPag"));
         imp.say(imp.pRow()+0,60,rs.getString("DiasPag"));
         
         if (imp.pRow()>=linPag) {
            imp.incPags();
            imp.eject();
         }

         sCodMaster = rs.getString("CodPlanoPag");
         
      }
      
      
      imp.say(imp.pRow()+1,0,""+imp.normal());
      imp.say(imp.pRow()+0,0,Funcoes.replicate("=",80));
      imp.eject();
      
      imp.fechaGravacao();
      
//      rs.close();
//      ps.close();
      if (!con.getAutoCommit())
      	con.commit();
      dl.dispose();
    }  
    catch ( SQLException err ) {
		Funcoes.mensagemErro(this,"Erro consulta tabela de Almoxarifados!"+err.getMessage());      
    }
    
    if (bVisualizar) {
      imp.preview(this);
    }
    else {
      imp.print();
    } 
    */
  }
  public void beforeInsert(InsertEvent ievt) { }
  public void beforePost(PostEvent pevt) {  }
  public void beforeCarrega(CarregaEvent cevt) {  }
  public void afterCarrega(CarregaEvent cevt) {  }
  public void afterInsert(InsertEvent ievt) {  }
  public void setConexao(Connection cn) {
    super.setConexao(cn);
    lcProd.setConexao(cn);      
  }

}
