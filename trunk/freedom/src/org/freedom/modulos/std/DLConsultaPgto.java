/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)DLConsultaPgto.java <BR>
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
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Painel;
import org.freedom.componentes.Tabela;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.FFDialogo;

public class DLConsultaPgto extends FFDialogo {
  private JTextFieldPad txtCodCli = new JTextFieldPad();
  private JTextFieldFK txtDescCli = new JTextFieldFK();
  private Tabela tabConsulta = new Tabela();
  private JScrollPane spnTab = new JScrollPane(tabConsulta);
  private Connection con = null;
  private ListaCampos lcCli = new ListaCampos(this);
  private Painel pinConsulta = new Painel(0,60);
  public DLConsultaPgto(Component cOrig,Connection cn,int iCodCli) {
  	super(cOrig);
    con = cn;
    txtCodCli.setVlrString(""+iCodCli);
    setTitulo("Consulta de Pagamentos");
    setAtribos(100,100,500,300);
    
	setToFrameLayout();
    
    pnRodape.setPreferredSize(new Dimension(500,32));
    pnRodape.setBorder(BorderFactory.createEtchedBorder());
    c.add(pinConsulta,BorderLayout.NORTH);
    c.add(spnTab,BorderLayout.CENTER);
    
    txtCodCli.setAtivo(false);
    
    pinConsulta.adic(new JLabel("Código e descrição do cliente"),7,0,250,20);
    pinConsulta.adic(txtCodCli,7,20,100,20);
    pinConsulta.adic(txtDescCli,110,20,250,20);
    
    txtCodCli.setNomeCampo("CodCli");
    txtCodCli.setTipo(JTextFieldPad.TP_INTEGER,10,0);
    txtDescCli.setTipo(JTextFieldPad.TP_STRING,40,0);
    lcCli.add(new GuardaCampo( txtCodCli, 7, 100, 80, 20, "CodCli", "Código", true, false, null, JTextFieldPad.TP_INTEGER,false),"txtCodClix");
    lcCli.add(new GuardaCampo( txtDescCli, 90, 100, 207, 20, "RazCli", "Descrição", false, false, null, JTextFieldPad.TP_STRING,false),"txtDescClix");
    txtDescCli.setListaCampos(lcCli);
    lcCli.montaSql(false, "CLIENTE", "VD");
    lcCli.setReadOnly(true);
    lcCli.setConexao(con);
    lcCli.carregaDados();

    tabConsulta.adicColuna("Vencto.");
    tabConsulta.adicColuna("Série");
    tabConsulta.adicColuna("Doc");
    tabConsulta.adicColuna("Cód. Venda");
    tabConsulta.adicColuna("Dt. Venda.");
    tabConsulta.adicColuna("Valor");
    tabConsulta.adicColuna("Dt. Pagto.");
    tabConsulta.adicColuna("Vlr. Pago.");
    tabConsulta.adicColuna("Atraso");
    tabConsulta.adicColuna("Observações");
    tabConsulta.adicColuna("Banco");
    
    tabConsulta.setTamColuna(90,0);
    tabConsulta.setTamColuna(50,1);
    tabConsulta.setTamColuna(80,2);
    tabConsulta.setTamColuna(80,3);
    tabConsulta.setTamColuna(90,4);
    tabConsulta.setTamColuna(100,5);
    tabConsulta.setTamColuna(90,6);
    tabConsulta.setTamColuna(100,7);
    tabConsulta.setTamColuna(60,8);
    tabConsulta.setTamColuna(200,9);
    tabConsulta.setTamColuna(200,10);
    tabConsulta.addMouseListener( 
      new MouseAdapter() {
        public void mouseClicked(MouseEvent mevt) {
          if (mevt.getSource() == tabConsulta) {
            if (mevt.getClickCount() == 2) {
              int iLin = tabConsulta.getLinhaSel();
              if (iLin >= 0) {
                int iCodVenda = Integer.parseInt((String)tabConsulta.getValor(iLin,3));
                DLConsultaVenda dl = new DLConsultaVenda(DLConsultaPgto.this,con,iCodVenda);
                dl.setVisible(true);
                dl.dispose();
              }
            }
          }
        }
      }
    );

    carregaGridConsulta();
  }
  private void carregaGridConsulta() {
    String sSQL = "SELECT IT.DTVENCITREC,V.SERIE,R.DOCREC,V.CODVENDA,"+
                  "R.DATAREC,IT.VLRPARCITREC,IT.DTPAGOITREC,IT.VLRPAGOITREC,"+
                  "(CAST('today' AS DATE)-IT.DTVENCITREC) AS ATRASO,"+
                  "R.OBSREC,(SELECT B.NOMEBANCO FROM FNBANCO B "+
                  "WHERE B.CODBANCO = R.CODBANCO) AS NOMEBANCO,"+
                  "R.CODREC,IT.NPARCITREC FROM FNRECEBER R, VDVENDA V, FNITRECEBER IT "+
                  "WHERE R.CODCLI=? AND V.CODVENDA=R.CODVENDA AND"+
                  " IT.STATUSITREC NOT IN ('RP') AND IT.CODREC = R.CODREC "+
                  "ORDER BY IT.DTVENCITREC,R.CODREC,IT.NPARCITREC";  
    try {
      PreparedStatement ps = con.prepareStatement(sSQL);
      ps.setInt(1,txtCodCli.getVlrInteger().intValue());
      ResultSet rs = ps.executeQuery();
      for (int i=0; rs.next(); i++) {
        tabConsulta.adicLinha();
        tabConsulta.setValor((rs.getDate("DtVencItRec") != null ? Funcoes.sqlDateToStrDate(rs.getDate("DtVencItRec")) : ""),i,0);
        tabConsulta.setValor((rs.getString("Serie") != null ? rs.getString("Serie") : ""),i,1);
        tabConsulta.setValor((rs.getString("DocRec") != null ? rs.getString("DocRec") : ""),i,2);
        tabConsulta.setValor(""+rs.getInt("CodVenda"),i,3);
        tabConsulta.setValor((rs.getDate("DataRec") != null ? Funcoes.sqlDateToStrDate(rs.getDate("DataRec")) : ""),i,4);
        tabConsulta.setValor(Funcoes.strDecimalToStrCurrency(15,2,rs.getString("VlrParcItRec")),i,5);
        tabConsulta.setValor((rs.getDate("DtPagoItRec") != null ? Funcoes.sqlDateToStrDate(rs.getDate("DtPagoItRec")) : ""),i,6);
        tabConsulta.setValor(Funcoes.strDecimalToStrCurrency(15,2,rs.getString("VlrPagoItRec")),i,7);
        tabConsulta.setValor(rs.getString(9),i,8);
        tabConsulta.setValor(rs.getString("ObsRec") != null ? rs.getString("ObsRec") : "",i,9);
        tabConsulta.setValor(rs.getString(11) != null ? rs.getString(11) : "",i,10);
      }
      rs.close();
      ps.close();
//      con.commit();
    }
    catch(SQLException err) {
		Funcoes.mensagemErro(this,"Erro ao montar a tabela de consulta!\n"+err.getMessage());
    }
  }
}

