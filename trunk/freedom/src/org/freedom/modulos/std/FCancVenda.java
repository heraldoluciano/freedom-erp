/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FCancVenda.java <BR>
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

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Painel;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FFilho;
import org.freedom.bmps.Icone;
public class FCancVenda extends FFilho implements ActionListener {
  private Painel pinCli = new Painel(350,100);
  private JPanel pnRod = new JPanel(new BorderLayout());
  private JTextFieldPad txtCodVenda = new JTextFieldPad();
  private JTextFieldPad txtDocVenda = new JTextFieldPad();
  private JTextFieldPad txtSerie = new JTextFieldPad();
  private JTextFieldPad txtVlrLiqVenda = new JTextFieldPad();
  private JTextFieldPad txtStatusVenda = new JTextFieldPad();
  private JButton btCancelar = new JButton("Cancelar",Icone.novo("btCancelar.gif"));
  private JButton btSair = new JButton("Sair",Icone.novo("btSair.gif"));
  private ListaCampos lcVenda = new ListaCampos(this);
  private Connection con = null;
  public FCancVenda() {
    setTitulo("Cancelamento");
    setAtribos(50,50,350,170);
    
    Funcoes.setBordReq(txtCodVenda);
    txtDocVenda.setAtivo(false);
    txtSerie.setAtivo(false);
    txtVlrLiqVenda.setAtivo(false);
    
    txtCodVenda.setTipo(JTextFieldPad.TP_INTEGER,8,0);
    txtDocVenda.setTipo(JTextFieldPad.TP_INTEGER,8,0);
    txtVlrLiqVenda.setTipo(JTextFieldPad.TP_DECIMAL,15,2);
    txtSerie.setTipo(JTextFieldPad.TP_STRING,4,0);
    txtStatusVenda.setTipo(JTextFieldPad.TP_STRING,2,0);
    lcVenda.add(new GuardaCampo( txtCodVenda, 7, 100, 80, 20, "CodVenda", "Cód.Venda", true, false, null, JTextFieldPad.TP_INTEGER,false),"txtCodVenda");
    lcVenda.add(new GuardaCampo( txtDocVenda, 90, 100, 207, 20, "DocVenda", "Documento", false, false, null, JTextFieldPad.TP_INTEGER,false),"txtDescVenda");
    lcVenda.add(new GuardaCampo( txtSerie, 90, 100, 207, 20, "Serie", "Série", false, false, null, JTextFieldPad.TP_STRING,false),"txtDescVenda");
    lcVenda.add(new GuardaCampo( txtVlrLiqVenda, 90, 100, 207, 20, "VlrLiqVenda", "V. Liq.", false, false, null, JTextFieldPad.TP_DECIMAL,false),"txtDescVenda");
    lcVenda.add(new GuardaCampo( txtStatusVenda, 90, 100, 207, 20, "StatusVenda", "Status", false, false, null, JTextFieldPad.TP_STRING,false),"txtDescVenda");
    lcVenda.montaSql(false, "VENDA", "VD");
    lcVenda.setReadOnly(true);
    txtCodVenda.setTabelaExterna(lcVenda);
    txtCodVenda.setFK(true);
    txtCodVenda.setNomeCampo("CodVenda");
    
    Container c = getContentPane();
    c.setLayout(new BorderLayout());
    
    btSair.setPreferredSize(new Dimension(100,30));

    pnRod.setPreferredSize(new Dimension(350,30));
    pnRod.add(btSair,BorderLayout.EAST);
    
    c.add(pnRod,BorderLayout.SOUTH);
    c.add(pinCli,BorderLayout.CENTER);
    
    btCancelar.setToolTipText("Cancela Venda");

    pinCli.adic(new JLabel("Nº Pedido"),7,0,80,20);
    pinCli.adic(txtCodVenda,7,20,80,20);
    pinCli.adic(new JLabel("Doc."),90,0,67,20);
    pinCli.adic(txtDocVenda,90,20,67,20);
    pinCli.adic(new JLabel("Série"),160,0,67,20);
    pinCli.adic(txtSerie,160,20,67,20);
    pinCli.adic(new JLabel("Valor"),230,0,100,20);
    pinCli.adic(txtVlrLiqVenda,230,20,100,20);
    pinCli.adic(btCancelar,7,50,130,30);

    btSair.addActionListener(this);
    btCancelar.addActionListener(this);
  }
  public boolean cancelar(int iCodVenda,String sStatus) {
    boolean bRet = false;
    if (iCodVenda == 0) {
		Funcoes.mensagemInforma(null,"Nenhuma venda foi selecionada!");
      txtCodVenda.requestFocus();
    }
    else if (sStatus.substring(0,1).equals("C")) {
		Funcoes.mensagemInforma(null,"Venda ja foi cancelada!!");
    }
    else if ((sStatus.substring(0,1).equals("V")) |
             (sStatus.substring(0,1).equals("P"))) {
      if (Funcoes.mensagemConfirma(null, "Deseja realmente cancelar esta venda?")==0 ) {
        String sSQL = "UPDATE VDVENDA SET STATUSVENDA = 'CP' WHERE CODVENDA=? AND CODEMP=? AND CODFILIAL=?";
        try {
          PreparedStatement ps = con.prepareStatement(sSQL);
          ps.setInt(1,iCodVenda);
          ps.setInt(2,Aplicativo.iCodEmp);
          ps.setInt(3,ListaCampos.getMasterFilial("VDVENDA"));
          ps.executeUpdate();
//          ps.close();
          if (!con.getAutoCommit())
          	con.commit();
          bRet = true;
        }
        catch(SQLException err) {
			Funcoes.mensagemErro(null,"Erro ao cancelar a venda!\n"+err.getMessage());
        }
      }
    }
    return bRet;
  }
  public void actionPerformed(ActionEvent evt) { 
    if (evt.getSource() == btSair)
      dispose();
    else if (evt.getSource() == btCancelar)
      cancelar(txtCodVenda.getVlrInteger().intValue(),txtStatusVenda.getVlrString());
  }
  public void setConexao(Connection cn) {
    con = cn;
    lcVenda.setConexao(cn);
  }
}

