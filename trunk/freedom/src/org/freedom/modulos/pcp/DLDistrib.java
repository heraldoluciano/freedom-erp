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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
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

public class DLDistrib extends FFDialogo implements MouseListener, ActionListener{

	int casasDec = Aplicativo.casasDec;
	private JTextFieldPad txtCodOP = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
	private JTextFieldPad txtSeqOP = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);  
	private JTextFieldPad txtCodProdEst = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
	private JTextFieldPad txtRefProdEst = new JTextFieldPad(JTextFieldPad.TP_STRING,13,0);
	private JTextFieldPad txtSeqEst = new JTextFieldPad(JTextFieldPad.TP_INTEGER,5,0);
	private JTextFieldPad txtDescEst = new JTextFieldPad(JTextFieldPad.TP_STRING, 50, 0);
	private JTextFieldPad txtQtdDist = new JTextFieldPad(JTextFieldPad.TP_DECIMAL,15,Aplicativo.casasDec);
	private JTextFieldPad txtQtdProd = new JTextFieldPad(JTextFieldPad.TP_DECIMAL,15,Aplicativo.casasDec);
	private JPanelPad pnDistrib = new JPanelPad(JPanelPad.TP_JPANEL,new BorderLayout());
	private JPanelPad pinCab = new JPanelPad(400,60);
	private Tabela tabDistrib = new Tabela();
	int iCodop = 0;
	int iSeqop = 0;
	int iQtdDistrib = 0;
	
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
    adic(new JLabelPad("Qtd.produzida"),316,0,80,20);
    adic(txtQtdProd,316,20,80,20);
    adic(new JLabelPad("Qtd.distrb"),399,0,80,20);
    adic(txtQtdDist,399,20,80,20);
     
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
	txtQtdProd.setAtivo(false); 
	txtQtdDist.setAtivo(false);
   
    tabDistrib.adicColuna("Seq.fase");//0
    tabDistrib.adicColuna("Cód.fase");//1
    tabDistrib.adicColuna("Descrição da fase");//2
    tabDistrib.adicColuna("Seq.dist.");//3
    tabDistrib.adicColuna("Cód.prod.");//4
    tabDistrib.adicColuna("Descrição da estrutura");//5
    tabDistrib.adicColuna("Seq.est.dist.");//6
    tabDistrib.adicColuna("Qtd.distrib");//7
    tabDistrib.adicColuna("Fator conv.");//8
    tabDistrib.adicColuna("Qtd.final");//9
    tabDistrib.adicColuna("Lote");//10
    tabDistrib.adicColuna("Validate");//11
    
    tabDistrib.setTamColuna(60,0);
    tabDistrib.setTamColuna(60,1);
    tabDistrib.setTamColuna(140,2);
    tabDistrib.setTamColuna(60,3);
    tabDistrib.setTamColuna(70,4);
    tabDistrib.setTamColuna(160,5);
    tabDistrib.setTamColuna(70,6);
    tabDistrib.setTamColuna(60,7);
    tabDistrib.setTamColuna(60,10);
       
    tabDistrib.addMouseListener(this);
    //btOK.addActionListener(this);
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
  	    float ftQtdant = 0;
  	    float ftQtddig = 0;
  	    float ftQtddist = 0;
  	    float ftQtdprod = 0;
  	    float ftFator = 0;
  	    float ftFinal = 0;
  	    String sDescProd = null;
	  	DLFechaDistrib dl = null;
	  	boolean ok = false;
	  	try {
	  		iSeqDist =((Integer) tabDistrib.getValor(iLinha, 3)).intValue();
	  		iCodProd = ((Integer) tabDistrib.getValor(iLinha, 4)).intValue();
	  		sDescProd = ((String) tabDistrib.getValor(iLinha,5));
	  		ftQtdade = ((BigDecimal) tabDistrib.getValor(iLinha,7)).floatValue();
	  		ftQtdant = ftQtdade;
	  		ftFator = ((BigDecimal) tabDistrib.getValor(iLinha,8)).floatValue();
	  		while (!ok) {
		  		dl = new DLFechaDistrib(DLDistrib.this,iSeqDist,iCodProd,sDescProd,ftQtdade);
		  		try {
			  		dl.setConexao(con);
					dl.setVisible(true);
					if (dl.OK){
						ftQtddig = ( (BigDecimal) dl.getValor()[0]).floatValue(); // Quantidade digitada
						ftQtddist = getSomaTab(); // Quantidade que já foi distribuida(soma do valor total)
						ftQtdprod = txtQtdProd.getVlrBigDecimal().floatValue(); // Quantida produzida
						ftFinal = ftQtddig*ftFator; // valor total
				  		
						if (ftQtdprod<(ftQtddist+ftFinal-(ftQtdant*ftFator))) {
							Funcoes.mensagemInforma(null, "Quantidade inválida! \nQuantida total de distribuição ultrapassa quantidade produzida!");
							ftQtdade = (ftQtdprod - ftQtddist )/ftFator + ftQtdant;
						}
						else {
							tabDistrib.setValor(new BigDecimal(ftQtddig),iLinha,7);
							tabDistrib.setValor(new BigDecimal(ftFinal),iLinha,9);
							ftQtddist = getSomaTab();
							txtQtdDist.setVlrBigDecimal(new BigDecimal(ftQtddist));
							ok = true;
						}
						if(dl.getUsaModLote()){
							if(dl.gravaLote()){
								tabDistrib.setValor(dl.getValor()[1],iLinha,10);
								tabDistrib.setValor(dl.getValor()[2],iLinha,11);
							}
							else 
								ok = false;
						}
						else
							tabDistrib.setValor(dl.getValor()[1],iLinha,10);
					}
					else {
						ok = true;
					}
		  		}
				finally {
					if (dl!=null)
						dl.dispose();
					dl = null;
				}
	  		}
	  	}
		finally {
			iLinha = 0;
			iCodProd = 0;
			iSeqDist = 0;
			ftQtdade = 0;
			ftFator = 0;
			ftFinal = 0;
			sDescProd = null;
		}
  }
  
  public void mouseEntered(MouseEvent e) { }
  public void mouseExited(MouseEvent e) { }
  public void mousePressed(MouseEvent e) { }
  public void mouseReleased(MouseEvent e) { }  
  
  public float getSomaTab() {
	  float ftTotal =  0;
	  Vector v = null;
	  try {
		  for (int i=0; i<tabDistrib.getNumLinhas(); i++) {
			  v = tabDistrib.getLinha(i);
			  if ( (v!=null) && (v.size()>10)) {
				  if (v.elementAt(9)!=null) 
					  ftTotal += ((BigDecimal) v.elementAt(9)).floatValue();
			  }
		  }
	  }
	  finally {
		  v = null;
	  }
	  return ftTotal;
  }
  public void carregaTabela(int iCodop, int iSeqop) {
  	  PreparedStatement ps = null;
  	  ResultSet rs = null;
  	  String sql = null;
  	  Vector vLinha = null;
  	  this.iCodop = iCodop;
  	  this.iSeqop = iSeqop;
  	  try {
  	  	sql = "SELECT D.CODPRODDE, ED.DESCEST, D.SEQEST, D.SEQEF, " +
  	  			"D.CODFASE, F.DESCFASE, D.SEQDE, D.CODEMPDE, " +
  	  			"D.CODFILIALDE, D.CODPRODDE, D.SEQESTDE, " +
  	  			"CAST(ID.QTDITEST*E.QTDEST AS NUMERIC(15,"+casasDec+")) " +
  	  			"FROM PPDISTRIB D, PPOP O, PPESTRUTURA ED, PPFASE F, " +
  	  			"PPITESTRUTURA ID, PPESTRUTURA E " +
  	  			"WHERE O.CODEMP=? AND O.CODFILIAL=? AND O.CODOP=? AND O.SEQOP=? AND " +
  	  			"D.CODEMP=O.CODEMPPD AND D.CODFILIAL=O.CODFILIALPD AND " +
  	  			"D.CODPROD=O.CODPROD AND D.SEQEST=O.SEQEST AND " +
  	  			"ED.CODEMP=D.CODEMPDE AND " +
  	  			"ED.CODFILIAL=D.CODFILIALDE AND ED.CODPROD=D.CODPRODDE AND " +
  	  			"ED.SEQEST=D.SEQESTDE AND F.CODEMP=D.CODEMPFS AND " +
  	  			"F.CODFILIAL=D.CODFILIALFS AND F.CODFASE=D.CODFASE AND " +
  	  			"ID.CODEMP=ED.CODEMP AND ID.CODFILIAL=ED.CODFILIAL AND " +
  	  			"ID.CODPROD=ED.CODPROD AND ID.SEQEST=ED.SEQEST AND " +
  	  			"ID.CODEMPPD=D.CODEMP AND ID.CODFILIALPD=D.CODFILIAL AND " +
  	  			"ID.CODPRODPD=D.CODPROD AND E.CODEMP=D.CODEMP AND " +
  	  			"E.CODFILIAL=D.CODFILIAL AND E.CODPROD=D.CODPROD AND " +
  	  			"E.SEQEST=D.SEQEST ";
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
  	  		vLinha.addElement(new Integer(rs.getInt("CODPRODDE")));
  	  		vLinha.addElement(rs.getString("DESCEST"));
  	  		vLinha.addElement(new Integer(rs.getInt("SEQESTDE")));
  	  		vLinha.addElement(new BigDecimal(0) );
  	  		vLinha.addElement(rs.getBigDecimal(12)); // Fator de conversão
  	  		vLinha.addElement(new BigDecimal(0) );
  	  		vLinha.addElement("");
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
  
  public void carregaCampos(Object[] sValores){
	txtCodOP.setVlrInteger((Integer) sValores[0]); 
	txtSeqOP.setVlrInteger((Integer) sValores[1]); 
	txtCodProdEst.setVlrInteger((Integer) sValores[2]); 
	txtRefProdEst.setVlrString((String) sValores[3]); 
	txtSeqEst.setVlrInteger((Integer) sValores[4]); 
	txtDescEst.setVlrString((String) sValores[5]);
	txtQtdProd.setVlrBigDecimal((BigDecimal) sValores[6]); 
	txtQtdDist.setVlrBigDecimal(new BigDecimal(0));
	//txtQtdDist.setVlrBigDecimal((BigDecimal)(Integer.parseInt(""+sValores[6])-iQtdDistrib)); 
  }
  
  public void actionPerformed(ActionEvent evt) {
	super.actionPerformed(evt);
	if (evt.getSource()==btOK) {
		gravaDistrib();
	}
	
  }
	  
  private void gravaDistrib() {
	  Vector linha = null;
	  try {
		  for (int i=0; i<tabDistrib.getNumLinhas(); i++) {
			  linha = tabDistrib.getLinha(i);
			  gravaOp(linha);
		  }
	  }
      finally {
    	  linha = null;
      }
  }
  
  private void gravaOp(Vector op) {
	  PreparedStatement ps = null;
	  String sql = null;
	  ResultSet rs = null;
	  int seqop = 0;
	  int codTipoMov = 0;
	  int codAlmox = 0;
	  Date dtFabrOP = null;
	  
	  try {
		 	  
		  sql = "SELECT MAX(SEQOP) FROM PPOP WHERE CODEMP=? AND CODFILIAL=? AND CODOP=?";
		  ps = con.prepareStatement(sql);
		  ps.setInt(1,Aplicativo.iCodEmp);
		  ps.setInt(2,ListaCampos.getMasterFilial("PPOP"));
		  ps.setInt(3,txtCodOP.getVlrInteger().intValue());
		  rs = ps.executeQuery();
		  if (rs.next()) {
			  seqop = rs.getInt(1)+1;
		  }		  
		  rs.close();
		  ps.close();
		  if (!con.getAutoCommit())
			  con.commit();
		  
		  sql = "SELECT DTFABROP,CODTIPOMOV,CODALMOX FROM PPOP WHERE CODEMP=? AND CODFILIAL=? AND CODOP=? AND SEQOP=?";
		  ps = con.prepareStatement(sql);
		  ps.setInt(1,Aplicativo.iCodEmp);
		  ps.setInt(2,ListaCampos.getMasterFilial("PPOP"));
		  ps.setInt(3,txtCodOP.getVlrInteger().intValue());
		  ps.setInt(4,txtSeqOP.getVlrInteger().intValue());
		  rs = ps.executeQuery();
		  if (rs.next()) {
			  dtFabrOP = rs.getDate(1);
			  codTipoMov = rs.getInt(2);
			  codAlmox = rs.getInt(3);
		  }		  
		  rs.close();
		  ps.close();
		  if (!con.getAutoCommit())
			  con.commit();
		  
		  sql = "INSERT INTO PPOP (CODEMP,CODFILIAL,CODOP,SEQOP,CODEMPPD,CODFILIALPD,CODPROD,SEQEST,DTFABROP," +
		  		"QTDPREVPRODOP,QTDFINALPRODOP,DTVALIDPDOP,CODEMPLE,CODFILIALLE,CODLOTE,CODEMPTM,CODFILIALTM,CODTIPOMOV," +
		  		"CODEMPAX,CODFILIALAX,CODALMOX,CODEMPOPM,CODFILIALOPM,CODOPM,SEQOPM,QTDDISTIOP)" +
		  		" VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		  ps = con.prepareStatement(sql);
		  ps.setInt(1,Aplicativo.iCodEmp);
		  ps.setInt(2,ListaCampos.getMasterFilial("PPOP"));
		  ps.setInt(3,txtCodOP.getVlrInteger().intValue());
		  ps.setInt(4,seqop);
		  ps.setInt(5,Aplicativo.iCodEmp);
		  ps.setInt(6,ListaCampos.getMasterFilial("PPESTRUTURA"));
		  ps.setInt(7,((Integer)op.elementAt(4)).intValue()); // Código do produto		  
		  ps.setInt(8,((Integer)op.elementAt(6)).intValue()); // Sequencia da estrutura
		  ps.setDate(9,dtFabrOP); // Data de fabricação 			  
		  ps.setFloat(10,((BigDecimal)op.elementAt(7)).floatValue()); // Qtdade prevista
		  ps.setFloat(11,((BigDecimal)op.elementAt(7)).floatValue()); // Quantidade produzida		  
		  ps.setDate(12,(Funcoes.strDateToSqlDate((String)op.elementAt(11)))); // data de validade		   
		  ps.setInt(13,Aplicativo.iCodEmp); 
		  ps.setInt(14,ListaCampos.getMasterFilial("EQLOTE"));
		  ps.setString(15,((String)op.elementAt(10))); // lote 		  
		  ps.setInt(16,Aplicativo.iCodEmp);
		  ps.setInt(17,ListaCampos.getMasterFilial("EQTIPOMOV"));
		  ps.setInt(18,codTipoMov); // tipo de movimento 
		  ps.setInt(19,Aplicativo.iCodEmp);
		  ps.setInt(20,ListaCampos.getMasterFilial("EQALMOX"));
		  ps.setInt(21,codAlmox); // Código do almoxarifado 
		  ps.setInt(22,Aplicativo.iCodEmp);
		  ps.setInt(23,ListaCampos.getMasterFilial("PPOP"));
		  ps.setInt(24,txtCodOP.getVlrInteger().intValue()); // CODOP Principal
		  ps.setInt(25,txtSeqOP.getVlrInteger().intValue()); // SEQOP Principal
		  ps.setFloat(26,((BigDecimal)op.elementAt(9)).floatValue()); // Qtdade distribuída
		  
		  
		  ps.executeUpdate();
		  ps.close();
		  rs.close();
		  if (!con.getAutoCommit())
			  con.commit();
	  }
	  catch (SQLException e) {
		  Funcoes.mensagemErro(null, "Erro ao gerar OP's de distribuição!\n"+e.getMessage());
		  try { 
		  	con.rollback();
		  }
		  catch (SQLException eb) {
		  }
	  }
	  finally {
		  rs = null;
		  ps = null;
		  sql = null;
	  }
	  
  }
  
}
