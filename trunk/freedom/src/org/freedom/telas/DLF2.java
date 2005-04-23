/**
 * @version 05/06/2000 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.telas <BR>
 * Classe: @(#)DLF2.java <BR>
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
 * Comentários da classe.....
 */

package org.freedom.telas;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableCellRenderer;

import org.freedom.bmps.Icone;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Tabela;
import org.freedom.funcoes.Funcoes;

public class DLF2 extends FFDialogo implements KeyListener, WindowFocusListener, ActionListener {
  private JLabelPad lbPesq = new JLabelPad("Código");
  private JTextFieldPad txtPesq = new JTextFieldPad(JTextFieldPad.TP_STRING,30,0);
  private JPanelPad pnBordCab = new JPanelPad(JPanelPad.TP_JPANEL,new GridLayout(1,1));
  private JPanelPad pinBt = new JPanelPad(30,30);
  private JPanelPad pinCab = new JPanelPad();
  private DefaultTableCellRenderer cabAnt = new DefaultTableCellRenderer();
  private DefaultTableCellRenderer cab = new DefaultTableCellRenderer();
  public  Tabela tab = new Tabela();
  private JScrollPane spnCentro = new JScrollPane(tab);
  private ListaCampos lcF2 = null;
  private Connection cnF2 = null;
  private PreparedStatement sqlF2 = null;
  private ResultSet rsF2 = null;
  private boolean bConsultaAtiva = false;
  private String sTextoAnt = "";
  public JButton btExecuta = new JButton(Icone.novo("btExecuta.gif"));
  boolean bPrimeira = false;
  int ColunaAtiva = -1;
  String sNomeCampoAtual = "";
  String sSqlF2 = "";
  String sWhereAdic = "";
  public DLF2(ListaCampos lc,Component cOrig) {
  	super(cOrig);
    btExecuta.setFocusable(false);
    cnF2 = lc.getConexao();     
    if (cnF2 == null) {
	  Funcoes.mensagemErro(this,"Conexão nula!");
      OK = false;
      setVisible(false);
    }  
    setTitulo("Pesquisa ("+lc.getNomeTabela().trim()+")");
    setAtribos( 500, 300);
    setResizable(true);
    lcF2 = lc;
    
    pnBordCab.setPreferredSize(new Dimension(300, 55));
    
    pinCab = new JPanelPad(390, 45);
    pinCab.adic(lbPesq, 7, 3, 270, 20);    
    pinCab.adic(btExecuta,290,13,30,30);
    pinCab.adic(txtPesq, 7, 23, 270, 20);

    pnBordCab.add(pinCab);
    
    c.add( pnBordCab, BorderLayout.NORTH);
    c.add( spnCentro, BorderLayout.CENTER);
    
    txtPesq.addKeyListener(this); 
    txtPesq.setEnterSai(false);
    tab.addKeyListener(this);
	
    addWindowFocusListener(this);

    btExecuta.setToolTipText("Executa consulta para campos não alfa-numéricos");
    btExecuta.addActionListener(this);

    montaColunas();

    trocaColuna();
    trocaColuna();
    
    setPrimeiroFoco(txtPesq);
        	              
  }
  public void montaColunas() {
    String tit = "";
    String sComma = ",";
    int tam = 0;
    sSqlF2 = "SELECT ";
    for (int i=0; i < lcF2.getComponentCount(); i++) {
      tit = ((GuardaCampo)(lcF2.getComponent(i))).getTituloCampo();
      tab.adicColuna(tit);
      if (i == lcF2.getComponentCount()-1) 
        sComma = " ";
      sSqlF2 += ((GuardaCampo)(lcF2.getComponent(i))).getNomeCampo()+sComma;
    }
    for (int i=0; i < lcF2.getComponentCount(); i++) {
      tam = ((GuardaCampo)(lcF2.getComponent(i))).getTamanhoCampo();
      if (tam == 0) tam = 80;
      tab.setTamColuna(tam,i);
    }
    sSqlF2 += " FROM "+lcF2.getNomeTabela();
  }
  public void trocaColuna() {
    int iTipo = 0;
    int iTam = 1;
    int iDec = 0;
    int iMascara = 0;
    if (tab.getNumColunas() > 0) {      
      if (ColunaAtiva == (tab.getNumColunas()-1)) {
        tab.getColumnModel().getColumn(ColunaAtiva).setHeaderRenderer(cabAnt); 
        ColunaAtiva = 0;
      }
      else
        ColunaAtiva++;
      if (ColunaAtiva > 0) {
        tab.getColumnModel().getColumn(ColunaAtiva-1).setHeaderRenderer(cabAnt); 
      }
      cabAnt = (DefaultTableCellRenderer)tab.getColumnModel().getColumn(ColunaAtiva).getHeaderRenderer(); 
      cab.setBackground(Color.gray);
      cab.setForeground(Color.yellow);
      tab.getColumnModel().getColumn(ColunaAtiva).setHeaderRenderer(cab); 
      lbPesq.setText(((GuardaCampo)(lcF2.getComponent(ColunaAtiva))).getTituloCampo());
      if (((GuardaCampo)(lcF2.getComponent(ColunaAtiva))).getCampo() != null) {
        iTipo = ((GuardaCampo)(lcF2.getComponent(ColunaAtiva))).getCampo().tipoCampo;
        iTam = ((GuardaCampo)(lcF2.getComponent(ColunaAtiva))).getCampo().iTamanho;
        iDec = ((GuardaCampo)(lcF2.getComponent(ColunaAtiva))).getCampo().iDecimal;
        iMascara = ((GuardaCampo)(lcF2.getComponent(ColunaAtiva))).getCampo().iMascara;
      }
      else {
        iTipo = ((GuardaCampo)(lcF2.getComponent(ColunaAtiva))).getTipo();
      }
      txtPesq.setTipo(iTipo, iTam, iDec);
      if (txtPesq.getEhMascara())
              txtPesq.setMascara(iMascara);
      else
              txtPesq.setEhMascara(false);
      sNomeCampoAtual = ((GuardaCampo)(lcF2.getComponent(ColunaAtiva))).getNomeCampo();
      repaint();
    }
    habBtPesq();
    
  } 
  private void habBtPesq() {
    int iTipo = txtPesq.getTipo();
    if (verifTipoPesq(iTipo)) 
        if (!btExecuta.isEnabled())
            btExecuta.setEnabled(true);
    else
   	    btExecuta.setEnabled(false);	
  }
  public void voltaColuna() {
    int iTipo = 0;
    int iTam = 0;
    int iDec = 0;
    int iMascara = 0;
    if (tab.getNumColunas() > 0) {      
      if (ColunaAtiva == (0)) {
        tab.getColumnModel().getColumn(ColunaAtiva).setHeaderRenderer(cabAnt); 
        ColunaAtiva = tab.getNumColunas()-1;
      }
      else
        ColunaAtiva--;
      if (ColunaAtiva < tab.getNumColunas()-1) {
        tab.getColumnModel().getColumn(ColunaAtiva+1).setHeaderRenderer(cabAnt); 
      }
      cabAnt = (DefaultTableCellRenderer)tab.getColumnModel().getColumn(ColunaAtiva).getHeaderRenderer(); 
      cab.setBackground(Color.gray);
      cab.setForeground(Color.yellow);
      tab.getColumnModel().getColumn(ColunaAtiva).setHeaderRenderer(cab); 
      lbPesq.setText(((GuardaCampo)(lcF2.getComponent(ColunaAtiva))).getTituloCampo());
      if (((GuardaCampo)(lcF2.getComponent(ColunaAtiva))).getCampo() != null) {
        iTipo = ((GuardaCampo)(lcF2.getComponent(ColunaAtiva))).getCampo().tipoCampo;
        iTam = ((GuardaCampo)(lcF2.getComponent(ColunaAtiva))).getCampo().iTamanho;
        iDec = ((GuardaCampo)(lcF2.getComponent(ColunaAtiva))).getCampo().iDecimal;
        iMascara = ((GuardaCampo)(lcF2.getComponent(ColunaAtiva))).getCampo().iMascara;
      }
      else {
        iTipo = ((GuardaCampo)(lcF2.getComponent(ColunaAtiva))).getTipo();
      }
      txtPesq.setTipo(iTipo, iTam, iDec);
      if (txtPesq.getEhMascara())
              txtPesq.setMascara(iMascara);
      sNomeCampoAtual = ((GuardaCampo)(lcF2.getComponent(ColunaAtiva))).getNomeCampo();
      repaint();
    }
    habBtPesq();
  } 
  public void executaSql() {
    String sNomeCampoX = "";
    String sVal = "";
    try {
       rsF2 = sqlF2.executeQuery();
       while (rsF2.next()) {
         Vector data = new Vector();
         for (int i=0 ; i < tab.getNumColunas() ; i++ ) {
           sNomeCampoX = ((GuardaCampo)(lcF2.getComponent(i))).getNomeCampo();
           if (((GuardaCampo)(lcF2.getComponent(i))).getTipo() == JTextFieldPad.TP_DATE) {
             sVal = rsF2.getString(sNomeCampoX) != null ? Funcoes.sqlDateToStrDate(rsF2.getDate(sNomeCampoX)) : "";
           }
           else {
             sVal = rsF2.getString(sNomeCampoX) != null ? rsF2.getString(sNomeCampoX) : "";
           }
           data.addElement(sVal);
         }
         tab.adicLinha(data);
       }
//       rsF2.close();
//       sqlF2.close();
//       cnF2.commit();
    }
    catch ( SQLException e) {
		Funcoes.mensagemErro(this,"Erro ao relizar consulta!\n"+e.getMessage());
    }
  }
  
  private boolean verifTipoPesq(int iTipo) {
  	return ((iTipo == JTextFieldPad.TP_INTEGER) || 
	   (txtPesq.getTipo() == JTextFieldPad.TP_DATE) ||
	   (txtPesq.getTipo() == JTextFieldPad.TP_DECIMAL));
  }
  
  public void montaSql() {
    bConsultaAtiva = true;
    boolean bString1 = false;
    String sSep = "";
    String sWhere = "";
    String sOrderBy = "";
    boolean bLike = (txtPesq.getText().trim().length() < txtPesq.iTamanho);
    int iTipo = txtPesq.getTipo(); 
    if (txtPesq.iTamanho == 1) {
      bString1 = true;
    }
    if ( tab.getNumLinhas() > 0 ) {
      tab.limpa();
    }
    sWhere += " WHERE "+(lcF2.getUsaME() ? "CODEMP="+Aplicativo.iCodEmp+(lcF2.getUsaFI() ? " AND CODFILIAL="+lcF2.getCodFilial() : "") : "");
    sSep = (lcF2.getUsaME() ? " AND " : "");
    sWhereAdic = lcF2.inDinWhereAdic(lcF2.getWhereAdic(),lcF2.vTxtValor);
    try {
      sWhere+=sWhereAdic.trim().equals("") ? "" : sSep + sWhereAdic;
	  sSep=sWhereAdic.trim().equals("") ? sSep : " AND ";
      if ( verifTipoPesq(iTipo) ) {
      	if (sWhere.trim().equals("WHERE")) 
      		sWhere = "";
        sWhere+=(!txtPesq.getVlrString().trim().equals("")?sSep+sNomeCampoAtual+"=?":"");
		sOrderBy = " ORDER BY "+sNomeCampoAtual;
        sqlF2 = cnF2.prepareStatement(sSqlF2+sWhere+sOrderBy);
        if (!txtPesq.getVlrString().trim().equals("")) {
        	if (txtPesq.getTipo() == JTextFieldPad.TP_INTEGER)
               sqlF2.setInt(1,txtPesq.getVlrInteger().intValue());
        	else if (txtPesq.getTipo() == JTextFieldPad.TP_DATE)
                sqlF2.setDate(1,Funcoes.dateToSQLDate(txtPesq.getVlrDate()));
        	else if (txtPesq.getTipo() == JTextFieldPad.TP_DECIMAL)
                sqlF2.setBigDecimal(1,txtPesq.getVlrBigDecimal());
        }
      }
      else if ((
                (txtPesq.getTipo() == JTextFieldPad.TP_STRING) ||
                (txtPesq.getEhMascara()))
                && (!bString1)){
        sWhere+=sSep+sNomeCampoAtual+(bLike ? " LIKE ?":"=?")+" ORDER BY "+sNomeCampoAtual;//Foi usada essa variavel booleana (blike) por que quando o campo estiver todo preenchido (do tamnho do length) nao eh preciso fazer LIKE.
        sqlF2 = cnF2.prepareStatement(sSqlF2+sWhere);
        sqlF2.setString(1,txtPesq.getVlrString().trim()+(bLike ? "%":""));
      }
      else if (bString1) {
      	sOrderBy = " ORDER BY "+sNomeCampoAtual;
        sWhere+=sWhereAdic.trim().equals("") ? "" : sSep + sWhereAdic;
        sWhere+=(!txtPesq.getVlrString().trim().equals("")?sSep+sNomeCampoAtual+"=?":"");
        if (sWhere.trim().equals("WHERE"))
        	sWhere = "";
      	sqlF2 = cnF2.prepareStatement(sSqlF2+sWhere+sOrderBy);
      	if (!txtPesq.getVlrString().trim().equals(""))
           sqlF2.setString(1,txtPesq.getVlrString().trim());
      }
    }
    catch (SQLException e) {
       System.out.println("ERRO AO MONTAR A SQL!:\n"+e.getMessage());
       dispose();
    }
  //  System.out.println("DLF2 -> "+sSqlF2+sWhere);
  }
  
  public Object getValor(String sNomeCampo) {
    int ind = -1;
    for (int i = 0; i < tab.getNumColunas(); i++) {
      if ((((GuardaCampo)(lcF2.getComponent(i))).getNomeCampo()) == (sNomeCampo)) {
        ind = i;
        break;
      }
    }
    if (ind < 0)
      return null;
    if (bPrimeira) 
      return tab.getValor(0, ind);
    else if ((ind >= 0) & (tab.getLinhaSel() >= 0))
      return tab.getValor(tab.getLinhaSel(), ind);
    return null;
  }
  public void keyPressed(KeyEvent kevt) {
    if (kevt.getSource() == txtPesq) {
      if ((kevt.getKeyCode() == KeyEvent.VK_UP) & 
          (txtPesq.getText().trim().length() == 0)) { 
        voltaColuna(); 
        bConsultaAtiva = false;
      }
      if (kevt.getKeyCode() == KeyEvent.VK_ENTER) {
         if (txtPesq.getText().trim().length() == 0) { 
           trocaColuna(); 
           bConsultaAtiva = false;
         }
         else {
           if (txtPesq.getText().compareTo(sTextoAnt) != 0) {
              montaSql();
              executaSql();
           }
           else {
              tab.requestFocus();
           }
           if (tab.getNumLinhas()>0) {
              tab.setRowSelectionInterval(0,0);
           }
         }
         sTextoAnt = txtPesq.getText();     
      }
      else if ( kevt.getKeyCode() == KeyEvent.VK_DOWN ) {
         tab.requestFocus();
      }
    }
    else if ( kevt.getSource() == tab ) {
      if ( kevt.getKeyCode() == KeyEvent.VK_ENTER) {       
        if ((tab.getNumLinhas() > 0) && (tab.getLinhaSel() >= 0)) {
          if (tab.getLinhaSel() != 0)
            tab.setRowSelectionInterval(tab.getLinhaSel()-1,tab.getLinhaSel()-1);
          else 
            bPrimeira = true;
          btOK.doClick();
        }
      }
      if ( ( kevt.getKeyCode() == KeyEvent.VK_UP ) & (tab.getLinhaSel()==0)) {
          txtPesq.requestFocus();
      }
    }
  }
  public void keyReleased(KeyEvent kevt) { 
    if ((kevt.getKeyCode() == KeyEvent.VK_ENTER) & (!bConsultaAtiva)) {
      txtPesq.setVlrString("");
      txtPesq.requestFocus();
    }
  }
  public void windowGainedFocus(WindowEvent e) {
    txtPesq.requestFocus();
  }
  public void windowLostFocus(WindowEvent e)  { }
  public void keyTyped(KeyEvent kevt) { }

  public void actionPerformed(ActionEvent arg0) {
  	if (arg0.getSource()==btExecuta) {
  		montaSql();
  		executaSql();
        sTextoAnt = txtPesq.getText();   		
  	}

  	super.actionPerformed(arg0);

  }
}

