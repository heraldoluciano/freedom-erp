/**
 * @version 02/11/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FTerminal.java <BR>
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.freedom.bmps.Icone;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Painel;
import org.freedom.componentes.Tabela;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FTabDados;

public class FTerminal extends FTabDados implements ActionListener {
  private Painel pnTerm = new Painel(280,130);
  private JTextFieldPad txtCodCaixa = new JTextFieldPad(8);
  private JTextFieldPad txtDescCaixa = new JTextFieldPad(50);
  private JPanel pnImp = new JPanel(new BorderLayout());
  private Painel pinImp = new Painel(300,90);
  private Tabela tab = new Tabela();
  private JScrollPane spnTab = new JScrollPane(tab);
  private JButton btAdic = new JButton(Icone.novo("btOk.gif"));
  private JButton btRem = new JButton(Icone.novo("btCancelar.gif"));
  private JLabel lbImp = new JLabel("Impressora");
  private JComboBox cbImp = new JComboBox();
  private JCheckBox chbPad = new JCheckBox();
  private JLabel lbPad = new JLabel("Impressora Padrão");
  private int[] codImps = new int[20];
  private Vector vImp = new Vector();
  public FTerminal() {
//Demonta o painel de impressão:
    pnRodape.remove(2);
//Monta a tela de Terminal:
    setTitulo("Cadastro de Informações do Terminal");
    setAtribos( 50, 50, 320, 250);
    setPainel(pnTerm);
    adicTab("Terminal",pnTerm);
    
    JCheckBoxPad cbECF = new JCheckBoxPad("Terminal fiscal","S","N");
    JCheckBoxPad cbModo = new JCheckBoxPad("Modo demonstração","S","N");
    cbECF.setVlrString("N");
    cbModo.setVlrString("S");

    adicCampo(txtCodCaixa, 7, 20, 70, 20, "CodCaixa", "Cód.caixa", JTextFieldPad.TP_INTEGER, 8, 0, true, false, null, true);
    adicCampo(txtDescCaixa, 80, 20, 210, 20, "DescCaixa", "Descrição do caixa", JTextFieldPad.TP_STRING, 50, 0, false, false, null, true);
    adicDB(cbECF, 7, 60, 150, 20, "ECFCaixa", "ECF",JTextFieldPad.TP_STRING,true);
    adicDB(cbModo,7,100,150,20,"ModoDemo","",JTextFieldPad.TP_STRING,true);
    setListaCampos( true, "CAIXA", "PV");
 
    adicTab("Impressora",pnImp);
    pnImp.add(pinImp,BorderLayout.SOUTH);
    pnImp.add(spnTab,BorderLayout.CENTER);
    
    pinImp.adic(lbImp,40,5,100,15);
    pinImp.adic(btAdic,7,15,30,30);
    pinImp.adic(cbImp,40,20,220,20);
    pinImp.adic(btRem,263,15,30,30);
    pinImp.adic(chbPad,7,50,20,20);
    pinImp.adic(lbPad,30,50,200,20);

    cbImp.addActionListener(this);
    txtCodCaixa.addActionListener(this);
    btAdic.addActionListener(this);
    btRem.addActionListener(this);
    chbPad.addActionListener(this);
    tab.getSelectionModel().addListSelectionListener(
      new ListSelectionListener() {
        public void valueChanged(ListSelectionEvent levt) {
          if (tab.getLinhaSel() >= 0) {
            if (((Boolean)tab.getValor(tab.getLinhaSel(),0)).booleanValue())
              chbPad.setSelected(true);
            else
              chbPad.setSelected(false);
            for (int i=0;((i<codImps.length) & (i<vImp.size())); i++) {
              if (codImps[tab.getLinhaSel()] == Integer.parseInt(""+vImp.elementAt(i)))
                cbImp.setSelectedIndex(i);
            }
          }
        }
      }
    );

    tab.adicColuna("Pad.");
    tab.adicColuna("Impressoras do terminal");
    tab.setTamColuna(50,0);
    tab.setTamColuna(250,1);
    txtCodCaixa.setFocusAccelerator((char) 10);
    
  }
  public void carregaCbImp() {
    String sSQL = "SELECT DESCIMP,CODIMP FROM SGIMPRESSORA";
    PreparedStatement ps = null;
    ResultSet rs = null;
    try {
      ps = con.prepareStatement(sSQL);
      rs = ps.executeQuery();
      while (rs.next()) {
        cbImp.addItem(rs.getString(1));
        vImp.addElement(rs.getString(2));
      }
      rs.close();
      ps.close();
//      con.commit();
    }
    catch (SQLException err) {
		Funcoes.mensagemErro(this, "Erro ao carregar tabela IMPRESSORA! ! !");
		Funcoes.mensagemErro(this, err.getMessage());
      return;
    }
  }
  public void montaTab() {
    codImps = new int[20];
    if (txtCodCaixa.getText().trim().length() == 0) 
      return;
    String sSQL = "SELECT CI.CODIMP,I.DESCIMP,CI.IMPPAD "+
             "FROM PVCAIXAIMP CI,SGIMPRESSORA I WHERE CI.CODCAIXA="+txtCodCaixa.getText().trim()+
             " AND I.CODIMP=CI.CODIMP AND I.CODEMP=? AND I.CODFILIAL=?";
    PreparedStatement ps = null;
    ResultSet rs = null;
    tab.limpa();
//faz a SQL do código da impressora:
    try {
      ps = con.prepareStatement(sSQL);
      ps.setInt(1,Aplicativo.iCodEmp);
      ps.setInt(2,ListaCampos.getMasterFilial("PVCAIXAIMP"));
      rs = ps.executeQuery();
      for (int i=0;rs.next();i++) {
        codImps[i] = rs.getInt(1);
        tab.adicLinha();
        if (rs.getString(3).trim().compareTo("S") == 0) {
          tab.setValor(new Boolean(true),i,0);               
        }
        else {
          tab.setValor(new Boolean(false),i,0);               
        }
        tab.setValor(rs.getString(2),i,1);               
      }
      rs.close();
      ps.close();
//      con.commit();
    }
    catch (SQLException err) {
		Funcoes.mensagemErro(this, "Erro ao carregar tabela IMPRESSORAS E CAIXAIMP! ! !");
		Funcoes.mensagemErro(this, err.getMessage());
      return;
    }
  }
  public void adicLin () {
    int CodImp = Integer.parseInt(""+vImp.elementAt(cbImp.getSelectedIndex()));
    for (int i=0;i<tab.getNumLinhas();i++) {
      if (CodImp == codImps[i]) {
		Funcoes.mensagemInforma(this,"Você não pode adicionar duas impressoras iguais "+
                                            "\npara o mesmo terminal.");          
        return;
      }
    }
    if (lcCampos.getStatus() == ListaCampos.LCS_INSERT) {
		Funcoes.mensagemInforma(this,"Salve o registro primeiro ! ! !");
      return;
    }
    else if (lcCampos.getStatus() == ListaCampos.LCS_NONE) {
		Funcoes.mensagemInforma(this,"Não há registro aberto ! ! !");
      return;
    }
    else if (tab.getNumLinhas() == 20) {
		Funcoes.mensagemInforma(this,"Você não pode adicionar mais de 20 impressoras por terminal! ! !");
      return;
    }
    String sSQL = "INSERT INTO PVCAIXAIMP (CODEMP,CODFILIAL,CODCAIXA,CODIMP,IMPPAD) " +
            "VALUES("+Aplicativo.iCodEmp+","+ListaCampos.getMasterFilial("PVCAIXAIMP")+","+txtCodCaixa.getVlrString()+","+CodImp+",'N')";
    PreparedStatement ps = null;
    try {
      ps = con.prepareStatement(sSQL);
      if (ps.executeUpdate() == 0) {
		Funcoes.mensagemInforma(this,"Falhou a consulta na tabela CAIXAIMP! ! !");
      }
//      ps.close();
      if (!con.getAutoCommit())
      	con.commit();
    }
    catch (SQLException err) {
		Funcoes.mensagemInforma(this, "Erro ao inserir na tabela CAIXAIMP! ! !\n"+err.getMessage());
      return;
    }
    montaTab();
  }
  public void remLin() {
    if (tab.getLinhaSel() < 0) {
		Funcoes.mensagemInforma(this,"Selecione uma impressora na tabela");
      return;
    }
    else if (txtCodCaixa.getText().trim().length() == 0)
      return;
    else if (Funcoes.mensagemConfirma(this, "Deseja realmente deletar este registro?")!=0 )
      return;
    String sSQL = "DELETE FROM PVCAIXAIMP WHERE CODCAIXA="+txtCodCaixa.getText().trim()+ 
      " AND CODEMP=? AND CODFILIAL=? AND CODIMP=" + codImps[tab.getLinhaSel()]; 
    PreparedStatement ps = null;
    try {
      ps = con.prepareStatement(sSQL);
      ps.setInt(1,Aplicativo.iCodEmp);
      ps.setInt(2,ListaCampos.getMasterFilial("PVCAIXAIMP")); 
      if (ps.executeUpdate() == 0) {
		Funcoes.mensagemInforma(this,"Falhou a consulta na tabela CAIXAIMP! ! !");
      }
//      ps.close();
      if (!con.getAutoCommit())
      	con.commit();
    }
    catch (SQLException err) {
		Funcoes.mensagemErro(this, "Erro ao deletar registro na tabela CAIXAIMP! ! !");
		Funcoes.mensagemErro(this, err.getMessage());
      return;
    }
    montaTab();
  }
  public void setPad() {
    if (tab.getLinhaSel() < 0) {
		Funcoes.mensagemInforma(this,"Selecione uma impressora na tabela");
      chbPad.setSelected(false);
      return;
    }
    else if (txtCodCaixa.getText().trim().length() == 0) {
      chbPad.setSelected(false);
      return;
    }
//Retira possível impressora padrão
    String sSQL = "UPDATE PVCAIXAIMP SET IMPPAD='N' WHERE CODCAIXA="+txtCodCaixa.getText().trim()+
                  " AND CODEMP=? AND CODFILIAL=? AND IMPPAD='S'"; 
    PreparedStatement ps = null;
    try {
      ps = con.prepareStatement(sSQL);
      ps.setInt(1,Aplicativo.iCodEmp);
      ps.setInt(2,ListaCampos.getMasterFilial("PVCAIXAIMP"));
      ps.executeUpdate();
//      ps.close();
      if (!con.getAutoCommit())
      	con.commit();
    }
    catch (SQLException err) {
		Funcoes.mensagemErro(this, "Erro ao alterar registro na tabela CAIXAIMP! ! !");
		Funcoes.mensagemErro(this, err.getMessage());
      chbPad.setSelected(false);
      return;
    }
    String sSQL2 = "UPDATE PVCAIXAIMP SET IMPPAD='S' WHERE CODCAIXA="+txtCodCaixa.getText().trim()+ 
      " AND CODEMP=? AND CODFILIAL=? AND CODIMP=" + codImps[tab.getLinhaSel()]; 
    PreparedStatement ps2 = null;
    try {
      ps2 = con.prepareStatement(sSQL2);
      ps2.setInt(1,Aplicativo.iCodEmp);
      ps2.setInt(2,ListaCampos.getMasterFilial("PVCAIXAIMP")); 
      if (ps2.executeUpdate() == 0) {
		Funcoes.mensagemInforma(this,"Falhou a consulta na tabela CAIXAIMP! ! !");
      }
//      ps2.close();
      if (!con.getAutoCommit())
      	con.commit();
    }
    catch (SQLException err) {
		Funcoes.mensagemErro(this, "Erro ao alterar registro na tabela CAIXAIMP! ! !");
		Funcoes.mensagemErro(this, err.getMessage());
      chbPad.setSelected(false);
      return;
    }
    montaTab();
  }
  public void execShow(Connection cn) {
    con = cn;
    carregaCbImp();
    super.execShow(cn);
  }
  public void actionPerformed(ActionEvent evt) {
    if (evt.getSource() == txtCodCaixa)
      montaTab();
    else if (evt.getSource() == btAdic) 
      adicLin();
    else if (evt.getSource() == btRem) 
      remLin(); 
    else if (evt.getSource() == chbPad) 
      setPad();
    super.actionPerformed(evt);
  }
}
