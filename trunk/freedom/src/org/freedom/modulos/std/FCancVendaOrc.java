/**
 * @version 05/03/2005 <BR>
 * @author Setpoint Informática Ltda./Robson Sanchez <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FCancVendaOrc.java <BR>
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
import org.freedom.bmps.Icone;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Painel;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FFilho;

public class FCancVendaOrc extends FFilho implements ActionListener, CarregaListener {
  private Painel pinCli = new Painel(350,100);
  private JPanel pnRod = new JPanel(new BorderLayout());
  private JTextFieldPad txtCodVenda = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtDocVenda = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtSerie = new JTextFieldPad(JTextFieldPad.TP_INTEGER,4,0);
  private JTextFieldPad txtTipoVenda = new JTextFieldPad(JTextFieldPad.TP_STRING,1,0);
  private JTextFieldPad txtBloqVenda = new JTextFieldPad(JTextFieldPad.TP_STRING,1,0);
  private JTextFieldPad txtVlrLiqVenda = new JTextFieldPad(JTextFieldPad.TP_DECIMAL,15,2);
  private JTextFieldPad txtStatusVenda = new JTextFieldPad(JTextFieldPad.TP_STRING,2,0);
  private JButton btCancelar = new JButton("Executar",Icone.novo("btExecuta.gif"));
  private JButton btSair = new JButton("Sair",Icone.novo("btSair.gif"));
  private ListaCampos lcVenda = new ListaCampos(this);
  private Connection con = null;
  public FCancVendaOrc() {
    setTitulo("Canc. de vínc. venda x orçamento");
    setAtribos(50,50,350,250);
    
    Funcoes.setBordReq(txtCodVenda);
    txtDocVenda.setAtivo(false);
    txtSerie.setAtivo(false);
    txtVlrLiqVenda.setAtivo(false);
    txtStatusVenda.setAtivo(false);
    txtBloqVenda.setAtivo(false);
    
    lcVenda.add(new GuardaCampo( txtDocVenda, "DocVenda", "Documento", ListaCampos.DB_SI, false));
    lcVenda.add(new GuardaCampo( txtSerie, "Serie", "Série", ListaCampos.DB_SI, false));
    lcVenda.add(new GuardaCampo( txtTipoVenda, "TipoVenda", "Tp.venda", ListaCampos.DB_SI, false));
    lcVenda.add(new GuardaCampo( txtBloqVenda, "BloqVenda", "Bloqueio", ListaCampos.DB_SI, false));
    lcVenda.add(new GuardaCampo( txtVlrLiqVenda, "VlrLiqVenda", "V. Liq.", ListaCampos.DB_SI, false));
    lcVenda.add(new GuardaCampo( txtStatusVenda, "StatusVenda", "Status", ListaCampos.DB_SI, false));
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
    
    btCancelar.setToolTipText("Executar exclusão de vínculo.");

    pinCli.adic(new JLabel("Nº pedido"),7,0,80,20);
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
    pinCli.adic(btCancelar,7,90,120,30);

  
    btSair.addActionListener(this);
    btCancelar.addActionListener(this);
  }
  
  public void beforeCarrega(CarregaEvent ce) {
      if (ce.getListaCampos()==lcVenda) {
          
      }
  }

  public void afterCarrega(CarregaEvent ce) {
      if (ce.getListaCampos()==lcVenda) {
          
      }
  }
  
  public void cancelar() {
    int iCodVenda = 0;
    String sTipoVenda = null;
    String sStatus = null;
    String sBloqVenda = null;
    String sSQL = null;
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
            Funcoes.mensagemInforma(this,"Venda está cancelada!");
            txtCodVenda.requestFocus();
            return;
        }
        if (sBloqVenda.equals("S")) {
            sBloqVenda = "N";
            Funcoes.mensagemInforma(this,"Esta venda encontra-se bloqueada!");
            return;
        }
        if (Funcoes.mensagemConfirma(this, "Deseja realmente excluir o vínculo venda x orçamento?")==JOptionPane.YES_OPTION ) {
            sSQL = "DELETE FROM VDVENDAORC WHERE CODEMP=? AND CODFILIAL=? AND TIPOVENDA=? AND " +
            		"CODVENDA=?";
            ps = con.prepareStatement(sSQL);
            ps.setInt(1,Aplicativo.iCodEmp);
            ps.setInt(2,ListaCampos.getMasterFilial("VDVENDA"));
            ps.setString(3,sTipoVenda);
            ps.setInt(4,iCodVenda);
            ps.executeUpdate();
            ps.close();
            if (!con.getAutoCommit())
                con.commit();
            lcVenda.carregaDados();
        }
    }
    catch(SQLException e) {
        Funcoes.mensagemErro(this,"Erro cancelando vínculo venda x orçamento!\n"+e.getMessage());
    }
    finally {
        iCodVenda = 0;
        sTipoVenda = null;
        sStatus = null;
        sBloqVenda = null;
        sSQL = null;
        ps = null;
    }
  }
  
  public void actionPerformed(ActionEvent evt) { 
    if (evt.getSource() == btSair)
      dispose();
    else if (evt.getSource() == btCancelar)
      cancelar();
  }
  
  public void setConexao(Connection cn) {
    con = cn;
    lcVenda.setConexao(cn);
  }
}

