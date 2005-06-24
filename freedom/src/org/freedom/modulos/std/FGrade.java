/**
 * @version 11/02/2002 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FGrade.java <BR>
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
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JPanelPad;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.acao.Processo;
import org.freedom.bmps.Icone;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.ProcessoSec;
import org.freedom.componentes.Tabela;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FFilho;


public class FGrade extends FFilho implements ActionListener, CarregaListener {
  private JPanelPad pinCab = new JPanelPad(700,55);
  private JPanelPad pnRod = new JPanelPad(JPanelPad.TP_JPANEL,new BorderLayout());
  private JPanelPad pnSubRod = new JPanelPad(JPanelPad.TP_JPANEL,new BorderLayout());
  private JPanelPad pinRod = new JPanelPad(480,55);
  private JPanelPad pinSair = new JPanelPad(120,45);
  private JPanelPad pinBtSel = new JPanelPad(40,110);
  private JPanelPad pinBtSelMod = new JPanelPad(40,110);
  private JPanelPad pnCli = new JPanelPad(JPanelPad.TP_JPANEL,new BorderLayout());
  private JPanelPad pnTabMod = new JPanelPad(JPanelPad.TP_JPANEL,new BorderLayout());
  private JPanelPad pnCliTab = new JPanelPad(JPanelPad.TP_JPANEL,new BorderLayout());
  private JLabelPad lbCodModG = new JLabelPad("Cód.mod.gp");
  private JLabelPad lbDescModG = new JLabelPad("Descrição do modelo de grupo");
  private JLabelPad lbDescINIModG = new JLabelPad("Descrição ini.");
  private JLabelPad lbRefINIModG = new JLabelPad("Ref.ini.");
  private JLabelPad lbCodFabINIModG = new JLabelPad("Cod.fab.ini.");
  private JLabelPad lbCodBarINIModG = new JLabelPad("Cod.bar.ini.");
  private JTextFieldPad txtCodModG = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldFK txtDescModG = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
  private JTextFieldFK txtDescINIModG = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
  private JTextFieldFK txtRefINIModG = new JTextFieldFK(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldFK txtCodFabINIModG = new JTextFieldFK(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldFK txtCodBarINIModG = new JTextFieldFK(JTextFieldPad.TP_INTEGER,8,0);
  private JButton btExec = new JButton(Icone.novo("btExecuta.gif"));
  private Tabela tab = new Tabela();
  private JScrollPane spnTab = new JScrollPane(tab);
  private Tabela tabMod = new Tabela();
  private JScrollPane spnTabMod = new JScrollPane(tabMod);
  private JButton btTudo = new JButton(Icone.novo("btTudo.gif"));
  private JButton btNada = new JButton(Icone.novo("btNada.gif"));
  private JButton btTudoMod = new JButton(Icone.novo("btTudo.gif"));
  private JButton btNadaMod = new JButton(Icone.novo("btNada.gif"));
  private JButton btGerar = new JButton(Icone.novo("btGerar.gif"));
  private JProgressBar pbGrade = new JProgressBar();
  private JLabelPad lbAnd = new JLabelPad("Andamento:");
  private JButton btSair = new JButton("Sair",Icone.novo("btSair.gif"));
  private ListaCampos lcModG = new ListaCampos(this);
  int iCodProd = 0;
  public FGrade() {
// Monta a tela
    super(false);
    setTitulo("Grade");
    setAtribos(25,10,700,420);
    Container c = getTela();
    c.setLayout(new BorderLayout());
    c.add(pnRod,BorderLayout.SOUTH);
    c.add(pnCli,BorderLayout.CENTER);
    c.add(pinCab,BorderLayout.NORTH);

    pinCab.adic(lbCodModG,7,5,75,20);
    pinCab.adic(txtCodModG,7,25,75,20);
    pinCab.adic(lbDescModG,85,5,192,20);
    pinCab.adic(txtDescModG,85,25,192,20);
    pinCab.adic(lbDescINIModG,280,5,167,20);
    pinCab.adic(txtDescINIModG,280,25,167,20);
    pinCab.adic(lbRefINIModG,450,5,67,20);
    pinCab.adic(txtRefINIModG,450,25,67,20);
    pinCab.adic(lbCodFabINIModG,520,5,77,20);
    pinCab.adic(txtCodFabINIModG,520,25,77,20);
    pinCab.adic(lbCodBarINIModG,600,5,77,20);
    pinCab.adic(txtCodBarINIModG,600,25,77,20);

    pnRod.setPreferredSize(new Dimension(600,50));

    pnSubRod.setPreferredSize(new Dimension(600,50));
    pnRod.add(pnSubRod,BorderLayout.SOUTH);

    pinSair.tiraBorda();
    pinSair.adic(btSair,10,10,100,30);
    btSair.setPreferredSize(new Dimension(120,30));

    pnSubRod.add(pinSair,BorderLayout.EAST);
    pnSubRod.add(pinRod,BorderLayout.CENTER);

    pinRod.tiraBorda();
    pinRod.adic(lbAnd,10,5,400,15);
    pinRod.adic(pbGrade,10,20,300,20);

    pinBtSel.adic(btTudo,5,5,30,30);
    pinBtSel.adic(btNada,5,38,30,30);
    pinBtSel.adic(btGerar,5,71,30,30);

    pnTabMod.setPreferredSize(new Dimension(600,130));

    pnTabMod.add(spnTabMod, BorderLayout.CENTER);
    pnTabMod.add(pinBtSelMod, BorderLayout.EAST);

    pinBtSelMod.adic(btTudoMod,5,5,30,30);
    pinBtSelMod.adic(btNadaMod,5,38,30,30);
    pinBtSelMod.adic(btExec,5,71,30,30);

    pnCliTab.add(spnTab, BorderLayout.CENTER);
    pnCliTab.add(pinBtSel,BorderLayout.EAST);
    
    pnCli.add(pnTabMod, BorderLayout.NORTH);
    pnCli.add(pnCliTab, BorderLayout.CENTER);

//Seta os comentários    

    btExec.setToolTipText("Executar montagem");
    btTudo.setToolTipText("Selecionar tudo");
    btNada.setToolTipText("Limpar seleção");
    btTudoMod.setToolTipText("Selecionar tudo");
    btNadaMod.setToolTipText("Limpar seleção");
    btGerar.setToolTipText("Gerar no banco");
    

//Monta as tabelas

    tab.adicColuna("Adic.prod.");    
    tab.adicColuna("Descrição do produto");
    tab.adicColuna("Referência");    
    tab.adicColuna("Cód.fab.");    
    tab.adicColuna("Cód.bar.");    
    
    tab.setTamColuna(80,0);
    tab.setTamColuna(280,1);
    tab.setTamColuna(100,2);
    tab.setTamColuna(80,3);
    tab.setTamColuna(80,4);
    
    tab.setColunaEditavel(0,true);
    

    tabMod.adicColuna("S/N");
    tabMod.adicColuna("Tipo de variante");
    tabMod.adicColuna("Descrição da variante");
    tabMod.adicColuna("Referência");
    tabMod.adicColuna("Cód.fab.");
    tabMod.adicColuna("Cód.bar.");

    tabMod.setTamColuna(40,0);
    tabMod.setTamColuna(160,1);
    tabMod.setTamColuna(160,2);
    tabMod.setTamColuna(100,3);
    tabMod.setTamColuna(80,4);
    tabMod.setTamColuna(80,5);
    
    tabMod.setColunaEditavel(0,true);

//Seta a FK do Modelo

    txtCodModG.setPKFK(true,false);
    lcModG.add(new GuardaCampo( txtCodModG, "CodModG", "Cód.mod.gp.", ListaCampos.DB_PK, true));
    lcModG.add(new GuardaCampo( txtDescModG, "DescModG", "Descrição do modelo de grupo", ListaCampos.DB_SI, false));
    lcModG.add(new GuardaCampo( txtDescINIModG, "DescProdModG", "Descrição ini.", ListaCampos.DB_SI, false));
    lcModG.add(new GuardaCampo( txtRefINIModG, "RefModG", "Ref. ini.", ListaCampos.DB_SI, false));
    lcModG.add(new GuardaCampo( txtCodFabINIModG, "CodFabModG", "Cód.fab.ini.", ListaCampos.DB_SI, false));
    lcModG.add(new GuardaCampo( txtCodBarINIModG, "CodBarModG", "Cód.bar.ini.", ListaCampos.DB_SI, false));
    lcModG.montaSql(false, "MODGRADE", "EQ");
    lcModG.setReadOnly(true);
    txtCodModG.setNomeCampo("CodModG");
    txtCodModG.setListaCampos(lcModG);
    txtDescModG.setListaCampos(lcModG);
    txtDescINIModG.setListaCampos(lcModG);
    txtRefINIModG.setListaCampos(lcModG);
    txtCodFabINIModG.setListaCampos(lcModG);
    txtCodBarINIModG.setListaCampos(lcModG);

//Adiciona os Listeners
    lcModG.addCarregaListener(this);
    btSair.addActionListener(this);
    btExec.addActionListener(this);
    btGerar.addActionListener(this);
    btTudo.addActionListener(this);
    btNada.addActionListener(this);
    btTudoMod.addActionListener(this);
    btNadaMod.addActionListener(this);

    txtCodModG.requestFocus();
  }

  private void carregar() {
  	
     if (txtCodModG.getText().equals("")) {
		Funcoes.mensagemInforma(this,"Selecione o modelo!");
       txtCodModG.requestFocus();
       return;
     }
     
     tab.limpa();
     
     String sTmp = "";
     Vector vModelos = new Vector();
     Vector vItens = new Vector();
     boolean bAchou = false;
     
     
     if (tabMod.getNumLinhas()>0 ) {
       for ( int i=0 ; i<tabMod.getNumLinhas();i++) {
          if (((Boolean)tabMod.getValor(i,0)).booleanValue()) {
            sTmp = ""+tabMod.getValor(i,1);
            if (vModelos.size()==0) {
              vModelos.addElement(sTmp);
            }
            else {
               bAchou = false;     
               for (int i2=0 ; i2<vModelos.size() ; i2++) {
                  if (sTmp.equals(vModelos.elementAt(i2))) {
                     bAchou = true;
                     break;
                  }
               }
               if (!bAchou) {
                 vModelos.addElement(sTmp);
               }
            } 
          } // fim do if do boolean 
       }  // Fim do for 
       
       for (int i=0 ; i<vModelos.size() ; i++) {
         vItens.addElement( getItens(""+vModelos.elementAt(i)) );
       }
       tab.limpa();
       geraItens(txtDescINIModG.getText(),txtRefINIModG.getText(),txtCodFabINIModG.getText(),txtCodBarINIModG.getText(),0,vItens);
       
     }
  }
  
  private void geraItens(String sDesc,String sRef,String sCodfab,String sCodbar,int iItem,Vector vItens) {
     String sDescAnt = sDesc;
     String sRefAnt = sRef;
     String sCodfabAnt = sCodfab;
     String sCodbarAnt = sCodbar;
     
     if (iItem<vItens.size()) {  
        for (int i=0 ; i<((Vector)vItens.elementAt(iItem)).size() ; i++) {
           sDesc = sDescAnt.trim()+" " + ((String[])((Vector)vItens.elementAt(iItem)).elementAt(i))[0];
           sRef = sRefAnt.trim()+ ((String[])((Vector)vItens.elementAt(iItem)).elementAt(i))[1];
           sCodfab = sCodfabAnt.trim()+ ((String[])((Vector)vItens.elementAt(iItem)).elementAt(i))[2];
           sCodbar = sCodbarAnt.trim()+ ((String[])((Vector)vItens.elementAt(iItem)).elementAt(i))[3];
          
           geraItens(sDesc,sRef,sCodfab,sCodbar,iItem+1,vItens);
          if (iItem==vItens.size()-1) {
            if (!sDesc.equals("")) {
              tab.adicLinha();
              tab.setValor(new Boolean(true),tab.getNumLinhas()-1,0);
              tab.setValor(sDesc,tab.getNumLinhas()-1,1);
              tab.setValor(sRef,tab.getNumLinhas()-1,2);
              tab.setValor(sCodfab,tab.getNumLinhas()-1,3);
              tab.setValor(sCodbar,tab.getNumLinhas()-1,4);
            }
          }   
        }
    }
          
  }
  
  private String[] getMatrizTab(int i) {
    String[] aItem = new String[4];
    aItem[0] = ""+tabMod.getValor(i,2);
    aItem[1] = ""+tabMod.getValor(i,3);
    aItem[2] = ""+tabMod.getValor(i,4);
    aItem[3] = ""+tabMod.getValor(i,5);
    return aItem;
  }
  
  private Vector getItens(String sTipo) {
      Vector vTmp = new Vector();
      for (int i=0 ; i<tabMod.getNumLinhas() ; i++ ) {
        if ( ((Boolean)tabMod.getValor(i,0)).booleanValue() & tabMod.getValor(i,1).equals(sTipo)) {
          vTmp.addElement(getMatrizTab(i));
        }         
      }
      return vTmp;
  }

  private void gerar() {
    int iContaItens = 0;
    String sErros = "";
    for (int i=0; i<tab.getNumLinhas(); i++) {
      if (((Boolean)tab.getValor(i,0)).booleanValue())
        iContaItens++;
    }
    //pbGrade = new JProgressBar(0,iContaItens);
    pbGrade.setMinimum(0);
    pbGrade.setMaximum(iContaItens);
    pbGrade.setStringPainted(true);
    pbGrade.setValue(0);
    String sSQL = "EXECUTE PROCEDURE EQADICPRODUTOSP(?,?,?,?,?,?,?,?)";
    PreparedStatement ps = null;
    try {
      for (int i=0; i<tab.getNumLinhas();i++) {
        ps = con.prepareStatement(sSQL);
        if (((Boolean)tab.getValor(i,0)).booleanValue()){
          ps.setInt(1,iCodProd);
          ps.setString(2,((String)tab.getValor(i,1)).trim());
          ps.setString(3,"");
          ps.setString(4,((String)tab.getValor(i,2)).trim());
          ps.setString(5,((String)tab.getValor(i,3)).trim());
          ps.setString(6,((String)tab.getValor(i,4)).trim());
          ps.setInt(7,Aplicativo.iCodEmp);
          ps.setInt(8,Aplicativo.iCodFilial);
          try {
            ps.execute();
          }
          catch (SQLException err1) {
            sErros = sErros + "Desc.:"+tab.getValor(i,1)+" Ref.:"+tab.getValor(i,2)+"\n"+err1.getMessage()+"\n";
          }
          pbGrade.setValue(i+1);
    //      ps.close();
          if (!con.getAutoCommit())
          	con.commit();
        }
        if (!con.getAutoCommit())
        	con.commit();
      }
      if (!sErros.trim().equals("")) {
         Funcoes.criaTelaErro("Alguns erros foram reportados:\n"+sErros);
      }
    }
    catch(SQLException err) {
		Funcoes.mensagemErro(this,"Erro ao executar um procedimento na tabela PRODUTO!\n"+err.getMessage(),true,con,err); 
    }
  }
  private void carregaTabMod() {
    tabMod.limpa();
    String sSQL = "SELECT M.CODPROD,I.CODMODG,I.CODITMODG,I.CODVARG,V.DESCVARG,"+
                  "I.DESCITMODG,I.REFITMODG,I.CODFABITMODG,I.CODBARITMODG "+
                  "FROM EQITMODGRADE I, EQVARGRADE V, EQMODGRADE M WHERE "+
                  "M.CODEMP = ? AND M.CODFILIAL = ? AND I.CODMODG="+txtCodModG.getText().trim()+
                  " AND V.CODVARG = I.CODVARG AND M.CODMODG=I.CODMODG "+ 
                  "ORDER BY I.CODMODG,I.CODITMODG,I.CODVARG ";
    PreparedStatement ps = null;
    ResultSet rs = null;
    String[] sVals = new String[5];
    int iContaLinha = 0;
    try {
      ps = con.prepareStatement(sSQL);
      ps.setInt(1,Aplicativo.iCodEmp);
      ps.setInt(2,ListaCampos.getMasterFilial("EQMODGRADE"));
      rs = ps.executeQuery();
      while (rs.next()) {
        sVals[0] = rs.getString("DescVarG") != null ? rs.getString("DescVarG") : "";
        sVals[1] = rs.getString("DescItModG") != null ? rs.getString("DescItModG") : "";
        sVals[2] = rs.getString("RefItModG") != null ? rs.getString("RefItModG") : "";
        sVals[3] = rs.getString("CodFabItModG") != null ? rs.getString("CodFabItModG") : "";
        sVals[4] = rs.getString("CodBarItModG") != null ? rs.getString("CodBarItModG") : "";
        tabMod.adicLinha();
        tabMod.setValor(new Boolean(true),iContaLinha,0);
        for (int i=0; i<5; i++) {
          tabMod.setValor(sVals[i],iContaLinha,i+1);
        }
        iCodProd = rs.getInt("CodProd");
        iContaLinha++;
      }
//      rs.close();
//      ps.close();
      if (!con.getAutoCommit())		
      	con.commit();
    }
    catch (SQLException err) {
		Funcoes.mensagemErro(this,"Erro a carregar a tabela ITMODGRADE!\n"+err.getMessage(),true,con,err);
    }
  }
  private void carregaTudo(Tabela tb) {
    for (int i=0; i<tb.getNumLinhas(); i++) {
      tb.setValor(new Boolean(true),i,0);
    }
  }
  private void carregaNada(Tabela tb) {
    for (int i=0; i<tb.getNumLinhas(); i++) {
      tb.setValor(new Boolean(false),i,0);
    }
  }
  public void actionPerformed(ActionEvent evt) {
    if (evt.getSource() == btSair) {
      dispose();
    }
    else if (evt.getSource() == btExec) {
      carregar();
    }
    else if (evt.getSource() == btGerar) {
      ProcessoSec pSec = new ProcessoSec(500,
        new Processo() {
          public void run() {
            pbGrade.updateUI();
          }
        },
        new Processo() {
          public void run() {
            gerar();
          }
        }
      );
      pSec.iniciar();
    }
    else if (evt.getSource() == btTudo) {
      carregaTudo(tab);
    }
    else if (evt.getSource() == btNada) {
      carregaNada(tab);
    }
    else if (evt.getSource() == btTudoMod) {
      carregaTudo(tabMod);
    }
    else if (evt.getSource() == btNadaMod) {
      carregaNada(tabMod);
    }
  }
  public void afterCarrega(CarregaEvent cevt) {
    if (cevt.ok) {
      carregaTabMod();
    }
  }
  public void beforeCarrega(CarregaEvent cevt) { }
  public void setConexao(Connection cn) {
    super.setConexao(cn);
    lcModG.setConexao(cn);
  }
}
