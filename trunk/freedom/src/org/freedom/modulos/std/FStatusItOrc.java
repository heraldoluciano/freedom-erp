/**
 * @version 26/04/2004 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FStatusItOrc.java <BR>
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
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.freedom.bmps.Icone;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Painel;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FFilho;

public class FStatusItOrc extends FFilho implements ActionListener {
  private Painel pinCli = new Painel(350,100);
  private JPanel pnRod = new JPanel(new BorderLayout());
  private JTextFieldPad txtCodOrc = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtItem = new JTextFieldPad(JTextFieldPad.TP_INTEGER,5,0);
  private JTextFieldFK txtVlrLiqItOrc = new JTextFieldFK(JTextFieldPad.TP_DECIMAL,15,2);
  private JTextFieldFK txtStatusOrc = new JTextFieldFK(JTextFieldPad.TP_STRING,2,0);
  private JCheckBoxPad cbEmitItOrc = new JCheckBoxPad("Emitido","S","N");
  private JButton btAltEmit = new JButton("Alterar",Icone.novo("btOk.gif"));
  private JButton btSair = new JButton("Sair",Icone.novo("btSair.gif"));
  private ListaCampos lcItOrc = new ListaCampos(this,"");
  private ListaCampos lcOrc = new ListaCampos(this,"");
  private Connection con = null;
  public FStatusItOrc() {
    setTitulo("Ajusta status do item de orçamento");
    setAtribos(50,50,340,210);
    
    
    lcOrc.add(new GuardaCampo( txtCodOrc, 7, 100, 80, 20, "CodOrc", "Cód.orcamento", true, false, null, JTextFieldPad.TP_INTEGER,true));
    lcOrc.add(new GuardaCampo( txtStatusOrc, 90, 100, 207, 20, "StatusOrc", "Status", false, false, null, JTextFieldPad.TP_STRING,false));
    lcOrc.montaSql(false, "ORCAMENTO", "VD");
    lcOrc.setReadOnly(true);
    txtCodOrc.setTabelaExterna(lcOrc);
    txtCodOrc.setFK(true);

    lcItOrc.add(new GuardaCampo( txtCodOrc, 7, 100, 80, 20, "CodOrc", "Cód.orcamento", true, true, null, JTextFieldPad.TP_INTEGER,true));
    lcItOrc.add(new GuardaCampo( txtItem, 90, 100, 207, 20, "CodItOrc", "Item", true, false, null, JTextFieldPad.TP_INTEGER,true));
    lcItOrc.add(new GuardaCampo( txtVlrLiqItOrc, 90, 100, 207, 20, "VlrLiqItOrc", "V. liq.", false, false, null, JTextFieldPad.TP_DECIMAL,false));
    lcItOrc.add(new GuardaCampo( cbEmitItOrc, 90, 100, 207, 20, "EmitItOrc", "Emit.", false, false, null, JTextFieldPad.TP_STRING,false));
    lcItOrc.montaSql(false, "ITORCAMENTO", "VD");
    lcItOrc.setReadOnly(true);
    txtCodOrc.setListaCampos(lcItOrc);
    txtCodOrc.setPK(true);
    txtCodOrc.setNomeCampo("CodOrc");
    txtItem.setListaCampos(lcItOrc);
    txtItem.setPK(true);
    txtItem.setNomeCampo("CodItOrc");
    

    Container c = getContentPane();
    c.setLayout(new BorderLayout());
    
    btSair.setPreferredSize(new Dimension(100,30));

    pnRod.setPreferredSize(new Dimension(350,30));
    pnRod.add(btSair,BorderLayout.EAST);
    
    c.add(pnRod,BorderLayout.SOUTH);
    c.add(pinCli,BorderLayout.CENTER);
    
    btAltEmit.setToolTipText("Alterar");

    pinCli.adic(new JLabel("Nº orçamento"),7,0,80,20);
    pinCli.adic(txtCodOrc,7,20,80,20);
    pinCli.adic(new JLabel("Item"),90,0,67,20);
    pinCli.adic(txtItem,90,20,67,20);
    pinCli.adic(new JLabel("Valor"),160,0,100,20);
    pinCli.adic(txtVlrLiqItOrc,160,20,100,20);
    pinCli.adic(new JLabel("Status"),7,40,40,20);
    pinCli.adic(txtStatusOrc,7,60,40,20);
    pinCli.adic(cbEmitItOrc,60,60,73,20);
    pinCli.adic(btAltEmit,7,90,120,30);
    
    btSair.addActionListener(this);
    btAltEmit.addActionListener(this);
  }
  private void trocar() {
    if (txtItem.getVlrString().equals("")) {
		Funcoes.mensagemInforma(this,"Nenhum item foi selecionado!");
        txtCodOrc.requestFocus();
        return;
    }
    String sSQL = "UPDATE VDITORCAMENTO SET EMITITORC=? WHERE CODORC=? AND " +
            "CODITORC=? AND CODEMP=? AND CODFILIAL=?";
    try {
      PreparedStatement ps = con.prepareStatement(sSQL);
      ps.setString(1,cbEmitItOrc.getVlrString());
      ps.setInt(2,txtCodOrc.getVlrInteger().intValue());
      ps.setInt(3,txtItem.getVlrInteger().intValue());
      ps.setInt(4,Aplicativo.iCodEmp);
      ps.setInt(5,ListaCampos.getMasterFilial("VDORCAMENTO"));
      ps.executeUpdate();
      ps.close();
      if (!con.getAutoCommit())
      	con.commit();
    }
    catch(SQLException err) {
	  Funcoes.mensagemErro(this,"Erro ao alterar o item!\n"+err.getMessage());
	  err.printStackTrace();
    }
  }
  public void actionPerformed(ActionEvent evt) { 
    if (evt.getSource() == btSair)
      dispose();
    else if (evt.getSource() == btAltEmit)
      trocar();
  }
  public void setConexao(Connection cn) {
    con = cn;
    lcItOrc.setConexao(cn);
    lcOrc.setConexao(cn);
  }
}

