/**
 * @version 23/11/2004 <BR>
 * @author Setpoint Informática Ltda./Robson Sanchez <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FBloqVenda.java <BR>
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

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Painel;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FFilho;
import org.freedom.bmps.Icone;
public class FBloqVenda extends FFilho implements ActionListener, CarregaListener {
  private Painel pinCli = new Painel(350,100);
  private JPanel pnRod = new JPanel(new BorderLayout());
  private JTextFieldPad txtCodVenda = new JTextFieldPad();
  private JTextFieldPad txtDocVenda = new JTextFieldPad();
  private JTextFieldPad txtSerie = new JTextFieldPad();
  private JTextFieldPad txtTipoVenda = new JTextFieldPad();
  private JTextFieldPad txtBloqVenda = new JTextFieldPad();
  private JTextFieldPad txtVlrLiqVenda = new JTextFieldPad();
  private JTextFieldPad txtStatusVenda = new JTextFieldPad();
  private JButton btBloquear = new JButton("Executar",Icone.novo("btExecuta.gif"));
  private JButton btSair = new JButton("Sair",Icone.novo("btSair.gif"));
  private ListaCampos lcVenda = new ListaCampos(this);
  private Connection con = null;
  public FBloqVenda() {
    setTitulo("Bloqueio e desbloqueio de vendas");
    setAtribos(50,50,350,250);
    
    Funcoes.setBordReq(txtCodVenda);
    txtDocVenda.setAtivo(false);
    txtSerie.setAtivo(false);
    txtVlrLiqVenda.setAtivo(false);
    txtStatusVenda.setAtivo(false);
    txtBloqVenda.setAtivo(false);
    
    txtCodVenda.setTipo(JTextFieldPad.TP_INTEGER,8,0);
    txtDocVenda.setTipo(JTextFieldPad.TP_INTEGER,8,0);
    txtVlrLiqVenda.setTipo(JTextFieldPad.TP_DECIMAL,15,2);
    txtSerie.setTipo(JTextFieldPad.TP_STRING,4,0);
    txtStatusVenda.setTipo(JTextFieldPad.TP_STRING,2,0);
    txtTipoVenda.setTipo(JTextFieldPad.TP_STRING,1,0); 
    txtBloqVenda.setTipo(JTextFieldPad.TP_STRING,1,0);
    
    lcVenda.add(new GuardaCampo( txtCodVenda, 7, 100, 80, 20, "CodVenda", "N.pedido", true, false, null, JTextFieldPad.TP_INTEGER,false),"txtCodVenda");
    lcVenda.add(new GuardaCampo( txtDocVenda, 90, 100, 207, 20, "DocVenda", "Documento", false, false, null, JTextFieldPad.TP_INTEGER,false),"txtDescVenda");
    lcVenda.add(new GuardaCampo( txtSerie, 90, 100, 207, 20, "Serie", "Série", false, false, null, JTextFieldPad.TP_STRING,false),"txtDescVenda");
    lcVenda.add(new GuardaCampo( txtTipoVenda, 90, 100 ,207 ,20, "TipoVenda", "Tp.venda", false, false, null, JTextFieldPad.TP_STRING,false),"txtTipoVenda");
    lcVenda.add(new GuardaCampo( txtBloqVenda, 90, 100 ,207 ,20, "BloqVenda", "Bloqueio", false, false, null, JTextFieldPad.TP_STRING,false),"txtBloqVenda");
    lcVenda.add(new GuardaCampo( txtVlrLiqVenda, 90, 100, 207, 20, "VlrLiqVenda", "V. Liq.", false, false, null, JTextFieldPad.TP_DECIMAL,false),"txtDescVenda");
    lcVenda.add(new GuardaCampo( txtStatusVenda, 90, 100, 207, 20, "StatusVenda", "Status", false, false, null, JTextFieldPad.TP_STRING,false),"txtDescVenda");
    lcVenda.montaSql(false, "VENDA", "VD");
    lcVenda.setReadOnly(true);
    lcVenda.addCarregaListener(this);
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
    
    btBloquear.setToolTipText("Bloqueia venda");

    pinCli.adic(new JLabel("Nº Pedido"),7,0,80,20);
    pinCli.adic(txtCodVenda,7,20,80,20);
    pinCli.adic(new JLabel("Doc."),90,0,67,20);
    pinCli.adic(txtDocVenda,90,20,67,20);
    pinCli.adic(new JLabel("Série"),160,0,67,20);
    pinCli.adic(txtSerie,160,20,67,20);
    pinCli.adic(new JLabel("Valor"),230,0,100,20);
    pinCli.adic(txtVlrLiqVenda,230,20,100,20);
    pinCli.adic(new JLabel("Situação"),7,40,70,20);
    pinCli.adic(txtStatusVenda,7,60,70,20);
    pinCli.adic(new JLabel("Bloqueada"),80,40,70,20);
    pinCli.adic(txtBloqVenda,80,60,70,20);
    pinCli.adic(btBloquear,7,90,120,30);

  
    btSair.addActionListener(this);
    btBloquear.addActionListener(this);
  }
  
  public void beforeCarrega(CarregaEvent ce) {
      if (ce.getListaCampos()==lcVenda) {
          
      }
  }

  public void afterCarrega(CarregaEvent ce) {
      if (ce.getListaCampos()==lcVenda) {
          
      }
  }
  
  public void bloquear() {
    int iCodVenda = 0;
    String sTipoVenda = null;
    String sStatus = null;
    String sBloqVenda = null;
    String sSQL = null;
    String sTexto = null;
    PreparedStatement ps = null;
    
    try {
        iCodVenda = txtCodVenda.getVlrInteger().intValue();
        sTipoVenda = txtTipoVenda.getVlrString();
        sStatus = txtStatusVenda.getVlrString();
        sBloqVenda = txtBloqVenda.getVlrString();

        if (iCodVenda == 0) {
            Funcoes.mensagemInforma(this,"Nenhuma venda foi selecionada!");
            txtCodVenda.requestFocus();
            return;
        }
        if (sStatus.substring(0,1).equals("C")) {
            Funcoes.mensagemInforma(this,"Venda cancelada!");
            txtCodVenda.requestFocus();
            return;
        }
        if (sBloqVenda.equals("S")) {
            sBloqVenda = "N";
            sTexto = "desbloquear";
            Funcoes.mensagemInforma(this,"Esta venda encontra-se bloqueada!");
        }
        else { 
            sBloqVenda = "S";
            sTexto = "bloquear";
        }
        if (Funcoes.mensagemConfirma(this, "Deseja realmente "+sTexto+" esta venda?")==JOptionPane.YES_OPTION ) {
            sSQL = "EXECUTE PROCEDURE VDBLOQVENDASP(?,?,?,?,?)";
            ps = con.prepareStatement(sSQL);
            ps.setInt(1,Aplicativo.iCodEmp);
            ps.setInt(2,ListaCampos.getMasterFilial("VDVENDA"));
            ps.setString(3,sTipoVenda);
            ps.setInt(4,iCodVenda);
            ps.setString(5,sBloqVenda);
            ps.executeUpdate();
            ps.close();
            if (!con.getAutoCommit())
                con.commit();
            lcVenda.carregaDados();
        }
    }
    catch(SQLException e) {
        Funcoes.mensagemErro(this,"Erro bloqueando ou desbloqueando venda!\n"+e.getMessage());
    }
    finally {
        iCodVenda = 0;
        sTipoVenda = null;
        sStatus = null;
        sBloqVenda = null;
        sSQL = null;
        sTexto = null;
        ps = null;
    }
  }
  
  public void actionPerformed(ActionEvent evt) { 
    if (evt.getSource() == btSair)
      dispose();
    else if (evt.getSource() == btBloquear)
      bloquear();
  }
  
  public void setConexao(Connection cn) {
    con = cn;
    lcVenda.setConexao(cn);
  }
}

