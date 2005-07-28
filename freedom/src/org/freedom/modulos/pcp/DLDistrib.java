/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Informática Ltda./Alex Rodrigues <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)DLDistrib.java <BR>
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

package org.freedom.modulos.pcp;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JScrollPane;

import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Tabela;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FFDialogo;

public class DLDistrib extends FFDialogo implements MouseListener{

	int casasDec = Aplicativo.casasDec;
	private JTextFieldPad txtCodOP = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
	private JTextFieldPad txtSeqOP = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);  
	private JTextFieldPad txtCodProdEst = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
	private JTextFieldPad txtRefProdEst = new JTextFieldPad(JTextFieldPad.TP_STRING,13,0);
	private JTextFieldPad txtSeqEst = new JTextFieldPad(JTextFieldPad.TP_INTEGER,5,0);
	private JTextFieldPad txtDescEst = new JTextFieldPad(JTextFieldPad.TP_STRING, 50, 0);
	private JPanelPad pnDistrib = new JPanelPad(JPanelPad.TP_JPANEL,new BorderLayout());
	private JPanelPad pinCab = new JPanelPad(400,60);
	private Tabela tabDistrib = new Tabela();
	int iCodop = 0;
	int iSeqop = 0;
	//private ListaCampos lcDistrib = new ListaCampos(this);
  
  public DLDistrib(Connection cn,Component cOrig,boolean bPref) {
  	super(cOrig);
    setConexao(cn);
    setTitulo("Distribuição");
    setAtribos(757,380);
    
    pinCab.setPreferredSize(new Dimension(400,100));
    pnDistrib.add(pinCab,BorderLayout.NORTH);
    JScrollPane spnTabRec = new JScrollPane(tabDistrib);
    pnDistrib.add(spnTabRec,BorderLayout.CENTER);
    c.add(pnDistrib, BorderLayout.CENTER);
    
    setPainel(pinCab);
    
    adic(new JLabelPad("Nº.OP."),7,0,100,20);
    adic(txtCodOP,7,20,100,20);
    adic(new JLabelPad("Seq.OP."),110,0,100,20);
    adic(txtSeqOP,110,20,100,20);
    if(bPref){
	    adic(new JLabelPad("Referência"),213,0,100,20);
	    adic(txtRefProdEst,213,20,100,20);
    }
    else{
    	adic(new JLabelPad("Cód.prod"),213,0,100,20);
	    adic(txtCodProdEst,213,20,100,20);
    }	    
    adic(new JLabelPad("Seq.Est."),7,40,100,20);
    adic(txtSeqEst,7,60,100,20);
    adic(new JLabelPad("Descrição da estrutura principal"),110,40,200,20);
    adic(txtDescEst,110,60,300,20);
    
	txtCodOP.setAtivo(false);
	txtSeqOP.setAtivo(false);
	txtCodProdEst.setAtivo(false);
	txtRefProdEst.setAtivo(false);
	txtSeqEst.setAtivo(false); 
	txtDescEst.setAtivo(false);
   
    tabDistrib.adicColuna("Seq.fase");
    tabDistrib.adicColuna("Cód.fase");
    tabDistrib.adicColuna("Descrição da fase");
    tabDistrib.adicColuna("Seq.dist.");
    tabDistrib.adicColuna("Cód.prod.");
    tabDistrib.adicColuna("Descrição da estrutura");
    tabDistrib.adicColuna("Seq.est.dist.");
    tabDistrib.adicColuna("Quant.");
    tabDistrib.adicColuna("Lote");
    
    tabDistrib.setTamColuna(60,0);
    tabDistrib.setTamColuna(60,1);
    tabDistrib.setTamColuna(140,2);
    tabDistrib.setTamColuna(60,3);
    tabDistrib.setTamColuna(70,4);
    tabDistrib.setTamColuna(160,5);
    tabDistrib.setTamColuna(70,6);
    tabDistrib.setTamColuna(60,7);
    tabDistrib.setTamColuna(60,8);
       
    tabDistrib.addMouseListener(this);
    
  }
  
  public void mouseClicked(MouseEvent mevt) {
    if (mevt.getClickCount() == 2) {
    	if (mevt.getSource() == tabDistrib && tabDistrib.getLinhaSel() >= 0)
    		alteraDistrib();
    }
  }
  
  public void alteraDistrib(){
  	    int iLinha = tabDistrib.getLinhaSel();
  	    int iCodProd = 0;
  	    int iSeqDist = 0;
  	    float ftQtdade = 0;
  	    String sDescProd = null;
	  	DLFechaDistrib dl = null; 
	  	try {
	  		iSeqDist =((Integer) tabDistrib.getValor(iLinha, 3)).intValue();
	  		iCodProd = ((Integer) tabDistrib.getValor(iLinha, 4)).intValue();
	  		sDescProd = ((String) tabDistrib.getValor(iLinha,5));
	  		ftQtdade = ((BigDecimal) tabDistrib.getValor(iLinha,7)).floatValue();
	  		
	  		dl = new DLFechaDistrib(DLDistrib.this,iSeqDist,iCodProd,sDescProd,ftQtdade);
	  		dl.setConexao(con);
			dl.setVisible(true);
			if (dl.OK){
				if(dl.gravaLote()){
					tabDistrib.setValor(dl.getValor()[0],iLinha,7);
					tabDistrib.setValor(dl.getValor()[1],iLinha,8);
				}
				else{
					dl = new DLFechaDistrib(DLDistrib.this,iSeqDist,iCodProd,sDescProd,ftQtdade);
			  		dl.setConexao(con);
					dl.setVisible(true);
				}
			}
	  	}
		finally {
			iLinha = 0;
			iCodProd = 0;
			iSeqDist = 0;
			ftQtdade = 0;
			sDescProd = null;
			if (dl!=null)
				dl.dispose();
			dl = null;
		}
  }
  
  public void mouseEntered(MouseEvent e) { }
  public void mouseExited(MouseEvent e) { }
  public void mousePressed(MouseEvent e) { }
  public void mouseReleased(MouseEvent e) { }  
  
  public void carregaTabela(int iCodop, int iSeqop) {
  	  PreparedStatement ps = null;
  	  ResultSet rs = null;
  	  String sql = null;
  	  Vector vLinha = null;
  	  this.iCodop = iCodop;
  	  this.iSeqop = iSeqop;
  	  try {
  	  	sql = "SELECT O.CODPROD, ED.DESCEST, D.SEQEST, D.SEQEF, " +
  	  			"D.CODFASE, F.DESCFASE, D.SEQDE, D.CODEMPDE, " +
  	  			"D.CODFILIALDE, D.CODPRODDE, D.SEQESTDE " +
  	  			"FROM PPDISTRIB D, PPOP O, PPESTRUTURA ED, PPFASE F " +
  	  			"WHERE O.CODEMP=? AND O.CODFILIAL=? AND O.CODOP=? AND O.SEQOP=? AND " +
  	  			"D.CODEMP=O.CODEMPPD AND D.CODFILIAL=O.CODFILIALPD AND " +
  	  			"D.CODPROD=O.CODPROD AND D.SEQEST=O.SEQEST AND " +
  	  			"ED.CODEMP=D.CODEMPDE AND " +
  	  			"ED.CODFILIAL=D.CODFILIALDE AND ED.CODPROD=D.CODPRODDE AND " +
  	  			"ED.SEQEST=D.SEQESTDE AND F.CODEMP=D.CODEMPFS AND " +
  	  			"F.CODFILIAL=D.CODFILIALFS AND F.CODFASE=D.CODFASE";
  	  	ps = con.prepareStatement(sql);
  	  	ps.setInt(1,Aplicativo.iCodEmp);
  	  	ps.setInt(2,ListaCampos.getMasterFilial("PPOP"));
  	  	ps.setInt(3,iCodop);
  	  	ps.setInt(4,iSeqop);
  	  	rs = ps.executeQuery();
  	  	while (rs.next()) {
  	  		
  	  		vLinha = new Vector();
  	  		vLinha.addElement(new Integer(rs.getInt("SEQEF")));
  	  		vLinha.addElement(new Integer(rs.getInt("CODFASE")));
  	  		vLinha.addElement(rs.getString("DESCFASE"));
  	  		vLinha.addElement(new Integer(rs.getInt("SEQDE")));
  	  		vLinha.addElement(new Integer(rs.getInt("CODPROD")));
  	  		vLinha.addElement(rs.getString("DESCEST"));
  	  		vLinha.addElement(new Integer(rs.getInt("SEQESTDE")));
  	  		vLinha.addElement(new BigDecimal(0) );
  	  		vLinha.addElement("");
  	  		
  	  		tabDistrib.adicLinha(vLinha);
  	  		
  	  	}
  	  	rs.close();
  	  	ps.close();
  	  	if (!con.getAutoCommit())
  	  		con.commit();
  	  	
  	  }
  	  catch (SQLException e) {
  	  	Funcoes.mensagemErro(this,"Erro carregando distribuição!\n"+e.getMessage());
  	  }
  	  finally {
  	  	rs = null;
  	  	ps = null;
  	  	sql = null;
  	  	vLinha = null;
  	  }
  	  
  }
  
  public void caregaCampos(Object[] sValores){
  	txtCodOP.setVlrInteger((Integer) sValores[0]); 
	txtSeqOP.setVlrInteger((Integer) sValores[1]); 
	txtCodProdEst.setVlrInteger((Integer) sValores[2]); 
	txtRefProdEst.setVlrString((String)sValores[3]); 
	txtSeqEst.setVlrInteger((Integer) sValores[4]); 
	txtDescEst.setVlrString((String)sValores[5]);
  }
    
 
}
