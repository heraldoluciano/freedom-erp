/**
 * @version 11/02/2002 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FPrecoBase.java <BR>
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
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.freedom.bmps.Icone;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Painel;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.FFilho;

public class FPrecoBase extends FFilho implements ActionListener {
  private Painel pinCli = new Painel(300,150);
  private JPanel pnRod = new JPanel(new BorderLayout());

  private JTextFieldPad txtCodGrup = new JTextFieldPad(JTextFieldPad.TP_STRING,14,0);
  private JTextFieldPad txtCodPlanoPag = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldFK txtDescGrup = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
  private JTextFieldFK txtDescPlanoPag = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
  private JButton btGerar = new JButton("Gerar",Icone.novo("btGerar.gif"));
  private JButton btSair = new JButton("Sair",Icone.novo("btSair.gif"));
  private ListaCampos lcGrup = new ListaCampos(this,"GP");
  private ListaCampos lcPlanoPag = new ListaCampos(this,"PG");
  public FPrecoBase() {
    setTitulo("Ajusta Preço Base");
    setAtribos(50,50,310,190);
    
    Container c = getContentPane();
    
    Funcoes.setBordReq(txtCodGrup);
    Funcoes.setBordReq(txtCodPlanoPag);
    
    txtCodGrup.setNomeCampo("CodGrup");
    lcGrup.add(new GuardaCampo( txtCodGrup, "CodGrup", "Cód.grupo", ListaCampos.DB_PK, false));
    lcGrup.add(new GuardaCampo( txtDescGrup, "DescGrup", "Decrição do grupo", ListaCampos.DB_SI, false));
    lcGrup.montaSql(false,"GRUPO", "EQ");
    lcGrup.setReadOnly(true);
    txtCodGrup.setTabelaExterna(lcGrup);
    txtCodGrup.setFK(true);
    txtDescGrup.setListaCampos(lcGrup);

    txtCodPlanoPag.setNomeCampo("CodPlanoPag");
    lcPlanoPag.add(new GuardaCampo( txtCodPlanoPag, "CodPlanoPag", "Cód.p.pg.", ListaCampos.DB_PK, false));
    lcPlanoPag.add(new GuardaCampo( txtDescPlanoPag, "DescPlanoPag", "Descrição do plano de pagto.", ListaCampos.DB_SI, false));
    lcPlanoPag.montaSql(false,"PLANOPAG", "FN");
    lcPlanoPag.setReadOnly(true);
    txtCodPlanoPag.setTabelaExterna(lcPlanoPag);
    txtCodPlanoPag.setFK(true);
    txtDescPlanoPag.setListaCampos(lcPlanoPag);
    
    c.setLayout(new BorderLayout());
    c.add(pnRod,BorderLayout.SOUTH);
    c.add(pinCli,BorderLayout.CENTER);
    
    btSair.setPreferredSize(new Dimension(100,30));
    
    pnRod.setBorder(BorderFactory.createEtchedBorder());
    pnRod.setPreferredSize(new Dimension(300,30));
    pnRod.setLayout(new BorderLayout());
    pnRod.add(btSair,BorderLayout.EAST);
    
    pinCli.adic(new JLabel("Código e descrição do grupo"),7,0,250,20);
    pinCli.adic(txtCodGrup,7,20,80,20);
    pinCli.adic(txtDescGrup,90,20,200,20);
    pinCli.adic(new JLabel("Código e descrição do plano de pagamento"),7,40,250,20);
    pinCli.adic(txtCodPlanoPag,7,60,80,20);
    pinCli.adic(txtDescPlanoPag,90,60,200,20);
    pinCli.adic(btGerar,7,90,120,30);
    
    btSair.addActionListener(this);
    btGerar.addActionListener(this);
  }
  private void gerar() {
    if (txtCodPlanoPag.getVlrInteger().intValue() == 0) {
		Funcoes.mensagemInforma(this,"Código do plano de pagamento inválido!");
      return;
    }
    String sSQL = "UPDATE EQPRODUTO PR SET PRECOBASEPROD=(SELECT MAX(P.PRECOPROD)"+
                 " FROM VDPRECOPROD P WHERE P.CODPROD=PR.CODPROD"+
                 " AND P.CODPLANOPAG=?) WHERE PR.CODGRUP LIKE ?";
    try {
      PreparedStatement ps = con.prepareStatement(sSQL);
      ps.setInt(1,txtCodPlanoPag.getVlrInteger().intValue());
      ps.setString(2,txtCodGrup.getVlrString().length() < 14 ? txtCodGrup.getVlrString()+"%":txtCodGrup.getVlrString());
      ps.executeUpdate();
      if (!con.getAutoCommit())
      	con.commit();
    }
    catch (SQLException err) {
		Funcoes.mensagemErro(this,"Erro ao Gerar Preço Base!\n"+err.getMessage());
    }
  }
  public void actionPerformed(ActionEvent evt) {
    if (evt.getSource() == btSair) {
      dispose();
    }
    else if (evt.getSource() == btGerar) {
      gerar();
    }
  }
  public void setConexao(Connection cn) {
    super.setConexao(cn);
    lcGrup.setConexao(cn);
    lcPlanoPag.setConexao(cn);
  }
}


