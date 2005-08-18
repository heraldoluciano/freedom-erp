/**
 * @version 12/08/2005 <BR>
 * @author Setpoint Informática Ltda./Alex Rodrigues <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)DLVisitas.java <BR>
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
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JScrollPane;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JTextAreaPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Navegador;
import org.freedom.componentes.Tabela;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FFDialogo;

public class DLVisitas extends FFDialogo implements MouseListener{
  private JTextFieldPad txtCodHist = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtSitHist = new JTextFieldPad(JTextFieldPad.TP_STRING,4,0);
  private JTextFieldPad txtTipoHist = new JTextFieldPad(JTextFieldPad.TP_STRING,4,0);
  private JTextFieldPad txtAno = new JTextFieldPad(JTextFieldPad.TP_INTEGER,4,0);
  private JTextFieldPad txtCodCli = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldFK txtRazCli = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldPad txtCodCont = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldFK txtRazCont = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldPad txtCodAtend = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldFK txtNomeAtend = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldPad txtHoraHist = new JTextFieldPad(JTextFieldPad.TP_STRING,5,0);
  private JTextFieldPad txtDataHist = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
  private JTextAreaPad txaHist = new JTextAreaPad(1000);
  private JPanelPad pnCab = new JPanelPad(JPanelPad.TP_JPANEL,new BorderLayout());
  private JPanelPad pnRod = new JPanelPad(JPanelPad.TP_JPANEL,new BorderLayout());
  private JPanelPad pinCab = new JPanelPad(300,70);
  private JPanelPad pinRod = new JPanelPad(300,190);
  private JPanelPad pinNavHist = new JPanelPad(510, 30);
  private Tabela tab = new Tabela();
  private JLabel lbMes = new JLabel();
  private ListaCampos lchistorico = new ListaCampos(this);
  private ListaCampos lcAtendente = new ListaCampos(this);
  private ListaCampos lcContato = new ListaCampos(this);
  private Navegador navHist = new Navegador(false);
  
  public DLVisitas(Component cOrig, Connection con) {
    super(cOrig);    
    setTitulo("Alteração de historico");
    setAtribos(520, 532);
    setConexao(con);
    
    lchistorico.add(new GuardaCampo( txtCodHist, "CodHistTK", "Cód.hist.", ListaCampos.DB_PK, false));
    lchistorico.add(new GuardaCampo( txtDataHist, "DataHistTk", "Data", ListaCampos.DB_SI,false));
    lchistorico.add(new GuardaCampo( txtHoraHist, "HoraHistTk", "Hora", ListaCampos.DB_SI,false));
    lchistorico.add(new GuardaCampo( txtCodCont, "CodCto", "Cod.cont.", ListaCampos.DB_SI,false));
    lchistorico.add(new GuardaCampo( txtRazCont, "RazCto", "Nome do contato", ListaCampos.DB_SI,false));
    lchistorico.add(new GuardaCampo( txtCodAtend, "CodAtend", "Cod.cont.", ListaCampos.DB_SI,false));
    lchistorico.add(new GuardaCampo( txtNomeAtend, "NomeAtend", "Nome do atendente", ListaCampos.DB_SI,false));
    lchistorico.add(new GuardaCampo( txaHist, "DescHistTK", "Observações", ListaCampos.DB_SI,false));
    lchistorico.add(new GuardaCampo( txtSitHist, "SitHistTK", "Sit.", ListaCampos.DB_SI,false));
    lchistorico.add(new GuardaCampo( txtTipoHist, "TipoHistTK", "Tipo", ListaCampos.DB_SI,false));
    lchistorico.montaSql(false, "HISTORICO", "TK");
    lchistorico.setReadOnly(true);
    txtCodHist.setTabelaExterna(lchistorico);
    txtCodHist.setFK(true);
    txtCodHist.setNomeCampo("CodHistTK");
    
    lcAtendente.add(new GuardaCampo( txtCodAtend, "CodAtend", "Cód.atendente.", ListaCampos.DB_PK, false));
    lcAtendente.add(new GuardaCampo( txtNomeAtend, "NomeAtend", "Nome do atendente", ListaCampos.DB_SI,false));
    lcAtendente.montaSql(false, "ATENDENTE", "AT");
    lcAtendente.setReadOnly(true);
    txtCodAtend.setTabelaExterna(lcAtendente);
    txtCodAtend.setFK(true);
    txtCodAtend.setNomeCampo("CodAtend");
    
    lcContato.add(new GuardaCampo( txtCodCont, "CodCto", "Cód.cont.", ListaCampos.DB_PK, false));
    lcContato.add(new GuardaCampo( txtRazCont, "RazCto", "Nome do contato", ListaCampos.DB_SI,false));
    lcContato.montaSql(false, "CONTATO", "TK");
    lcContato.setReadOnly(true);
    txtCodCont.setTabelaExterna(lcContato);
    txtCodCont.setFK(true);
    txtCodCont.setNomeCampo("CodCto");
    
    c.add(pnCab, BorderLayout.NORTH);
    pnCab.setPreferredSize(new Dimension(300, 230));
    pnCab.add(pinCab, BorderLayout.NORTH);
    setPainel(pinCab);
        
    adic(new JLabelPad("Cód.cli."),7,0,80,20);
    adic(txtCodCli,7,20,80,20);
    adic(new JLabelPad("Razão social"),90,0,100,20);
    adic(txtRazCli,90,20,240,20);
    adic(new JLabelPad("Ano"),333,0,60,20);
    adic(txtAno,333,20,60,20);
    adic(lbMes,15,45,150,20);
    lbMes.setFont(new Font("Arial",Font.BOLD,14));
    lbMes.setForeground(Color.blue);
    txtCodCli.setAtivo(false);
    txtAno.setAtivo(false);
   
    JScrollPane spnTabRec = new JScrollPane(tab);
    pnCab.add(spnTabRec,BorderLayout.CENTER);
    
    tab.adicColuna("Cód.visita");
    tab.adicColuna("Contato");
    tab.adicColuna("Atendente");
    tab.adicColuna("Data");
    tab.adicColuna("Hora");
    tab.adicColuna("Observações");
    tab.adicColuna("Sit.");
    
    tab.setTamColuna(70,0);
    tab.setTamColuna(60,1);
    tab.setTamColuna(70,2);
    tab.setTamColuna(70,3);
    tab.setTamColuna(70,4);
    tab.setTamColuna(210,5);
    tab.setTamColuna(50,6);
    
    tab.addMouseListener(this);
    

    c.add(pnRod, BorderLayout.CENTER);
    pnRod.add(pinRod, BorderLayout.CENTER);
    setPainel(pinRod);
    
    adic(new JLabelPad("Cód.hist."),7,0,70,20);
    adic(txtCodHist,7,20,70,20);
    adic(new JLabelPad("Cód.cont."),80,0,70,20);
    adic(txtCodCont,80,20,70,20);
    adic(new JLabelPad("Nome do contato"),153,0,260,20);
    adic(txtRazCont,153,20,260,20);
    adic(new JLabelPad("Data"),416,0,80,20);
    adic(txtDataHist,416,20,80,20);
    adic(new JLabelPad("Situação"),7,40,70,20);
    adic(txtSitHist,7,60,70,20);
    adic(new JLabelPad("Cód.atend."),80,40,70,20);
    adic(txtCodAtend,80,60,70,20);
    adic(new JLabelPad("Nome do atendente"),153,40,260,20);
    adic(txtNomeAtend,153,60,260,20);
    adic(new JLabelPad("Hora"),416,40,80,20);
    adic(txtHoraHist,416,60,80,20);
    adic(new JLabelPad("Historico"),7,80,70,20);
    adic(new JScrollPane(txaHist),7,100,496,80);
    
    pnRod.add(pinNavHist, BorderLayout.SOUTH);
    pinNavHist.adic(navHist, 0, 0, 270, 25);
    
  }
  
  public void setCampos(Vector args){
	  txtCodCli.setVlrInteger((Integer)args.elementAt(0));
	  txtRazCli.setVlrString((String)args.elementAt(1));
	  txtAno.setVlrInteger(((Integer)args.elementAt(2)));	    
	  lbMes.setText(getMes(((Integer)args.elementAt(3)).intValue()));
  }
  
  public void mouseEntered(MouseEvent e) { }
  public void mouseExited(MouseEvent e) { }
  public void mousePressed(MouseEvent e) { }
  public void mouseReleased(MouseEvent e) { } 

  public void mouseClicked(MouseEvent mevt) {
    if (mevt.getClickCount() == 2) {
    	if (mevt.getSource() == tab && tab.getLinhaSel() >= 0){
    		carregaCampos();
    	}
    }
  }
  
  public void carregaCampos(){
	  int iLinha = tab.getLinhaSel();
	  txtCodHist.setVlrInteger((Integer)tab.getValor(iLinha,0));
	  txtCodCont.setVlrInteger((Integer)tab.getValor(iLinha,1));
	  txtCodAtend.setVlrInteger((Integer)tab.getValor(iLinha,2));
	  txtDataHist.setVlrDate((Date)tab.getValor(iLinha,3));
	  txtHoraHist.setVlrString((String)tab.getValor(iLinha,4));
	  txaHist.setVlrString((String)tab.getValor(iLinha,5));
	  txtSitHist.setVlrString((String)tab.getValor(iLinha,6));
  }
  
  public void carregaTabela(int year,int month){
	  PreparedStatement ps = null;
  	  ResultSet rs = null;
  	  String sql = null;
  	  Vector vLinha = null;
  	  int ano = year;
  	  int mes = month;
  	  try {
  	  	sql = "SELECT TK.CODHISTTK, TK.CODCTO, TK.CODATEND, TK.DATAHISTTK, " +
  	  		  "TK.HORAHISTTK, TK.DESCHISTTK, TK.SITHISTTK " +
  	  		  "FROM TKHISTORICO TK WHERE CODEMP=? AND CODFILIAL=? " +
  	  		  "AND EXTRACT(MONTH FROM TK.DATAHISTTK)=?" +
  	  		  "AND EXTRACT(YEAR FROM TK.DATAHISTTK)=?";
  	  	ps = con.prepareStatement(sql);
  	  	ps.setInt(1,Aplicativo.iCodEmp);
  	  	ps.setInt(2,ListaCampos.getMasterFilial("TKHISTORICO"));
  	  	ps.setInt(3,mes);
	  	ps.setInt(4,ano);
  	  	
  	  	rs = ps.executeQuery();
  	  	while (rs.next()) {
 	  		
  	  		vLinha = new Vector();
  	  		vLinha.addElement(new Integer(rs.getInt("CODHISTTK")));
  	  		vLinha.addElement(new Integer(rs.getInt("CODCTO")));
  	  		vLinha.addElement(new Integer(rs.getInt("CODATEND")));
  	  		vLinha.addElement(rs.getDate("DATAHISTTK"));
  	  		vLinha.addElement(rs.getString("HORAHISTTK"));
  	  		vLinha.addElement(rs.getString("DESCHISTTK"));
  	  		vLinha.addElement(rs.getString("SITHISTTK"));
 	  		
  	  		tab.adicLinha(vLinha);
  	  		 	  		
  	  	}
  	  	rs.close();
  	  	ps.close();
  	  	if (!con.getAutoCommit())
  	  		con.commit();  	  	
  	  }
  	  catch (SQLException e) {
  	  	Funcoes.mensagemErro(this,"Erro ao carregar historico!\n"+e.getMessage());
  	  }
  	  finally {
  	  	rs = null;
  	  	ps = null;
  	  	sql = null;
  	  	vLinha = null;
  	  	ano = 0;
    	mes = 0;
  	  }  	  
  }
  
  public String getMes(int mes){
	  String[] meses = new String[]{"Janeiro","Fevereiro","Março","Abril","Maio","Junho",
			  "Julho","Agosto","Setembro","Outubro","Novembro","Dezembro"};
	  String retorno = meses[mes-1];
	  return retorno;
  }
 
  public void actionPerformed(ActionEvent evt) {
	  super.actionPerformed(evt);
  }
  
  public void setConexao(Connection cn) {
  	  super.setConexao(cn);
  	  lchistorico.setConexao(cn);
      lcContato.setConexao(cn);
      lcAtendente.setConexao(cn);
  }
}