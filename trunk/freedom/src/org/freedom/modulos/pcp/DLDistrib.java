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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JScrollPane;

import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.StringDireita;
import org.freedom.componentes.Tabela;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FFDialogo;

public class DLDistrib extends FFDialogo implements MouseListener{

	private int casasDec = Aplicativo.casasDec;
	private JTextFieldPad txtSeqEF = new JTextFieldPad(JTextFieldPad.TP_INTEGER,5,0);
	private JTextFieldPad txtCodFase = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
	private JTextFieldPad txtSeqDE = new JTextFieldPad(JTextFieldPad.TP_INTEGER,5,0);
	private JTextFieldPad txtCodProd = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
	private JTextFieldPad txtSeqEstDE = new JTextFieldPad(JTextFieldPad.TP_INTEGER,5,0);
	private JTextFieldPad txtQuant = new JTextFieldPad(JTextFieldPad.TP_NUMERIC,15,casasDec);
	private JPanelPad pnDistrib = new JPanelPad(JPanelPad.TP_JPANEL,new BorderLayout());
	private JPanelPad pinCab = new JPanelPad(400,60);
	private Tabela tabDistrib = new Tabela();
	private int iCodop = 0;
	private int iSeqop = 0;
	//private ListaCampos lcDistrib = new ListaCampos(this);
  
  public DLDistrib(Connection cn,Component cOrig) {
  	super(cOrig);
    setConexao(cn);
    setTitulo("Distribuição");
    setAtribos(780,500);
    
    //lcDistrib.setTabela(tabDistrib);
   
    pinCab.setPreferredSize(new Dimension(400,60));
    pnDistrib.add(pinCab,BorderLayout.NORTH);
    JScrollPane spnTabRec = new JScrollPane(tabDistrib);
    pnDistrib.add(spnTabRec,BorderLayout.CENTER);

    c.add(pnDistrib, BorderLayout.CENTER);
    tabDistrib.adicColuna("Seq.fase");
    tabDistrib.adicColuna("Cód.fase");
    tabDistrib.adicColuna("Descrição da fase");
    tabDistrib.adicColuna("Seq.dist.");
    tabDistrib.adicColuna("Cód.prod.");
    tabDistrib.adicColuna("Descrição da estrutura");
    tabDistrib.adicColuna("Seq.est.dist.");
    tabDistrib.adicColuna("Quant.");
    
    tabDistrib.setTamColuna(50,0);
    tabDistrib.setTamColuna(50,1);
    tabDistrib.setTamColuna(160,2);
    tabDistrib.setTamColuna(50,3);
    tabDistrib.setTamColuna(70,4);
    tabDistrib.setTamColuna(230,5);
    tabDistrib.setTamColuna(50,6);
    tabDistrib.setTamColuna(80,7);
    
    
    
    //txtSeqEF.setNomeCampo("SeqEF");
//    lcDistrib.add(new GuardaCampo( txtSeqEF, "SeqEF", "Seq.fase", ListaCampos.DB_PK,false));
    //lcDistrib.add(new GuardaCampo( txtCodFase, "CodFase", "Cód.fase", ListaCampos.DB_SI,false));
    //lcDistrib.add(new GuardaCampo( txtSeqDE, "SeqDE", "Seq.distrib.", ListaCampos.DB_SI,false));
    //lcDistrib.add(new GuardaCampo( txtCodProd, "CodProd", "Cód.prod", ListaCampos.DB_SI,false));
    //lcDistrib.add(new GuardaCampo( txtSeqEstDE, "SeqEstDE", "N.distrib.", ListaCampos.DB_SI,false));
   // lcDistrib.add(new GuardaCampo( txtQuant, "", "Quantidade", ListaCampos.DB_SI,false));
    //lcDistrib.montaSql(false, "DISTRIB", "PP");
    //lcDistrib.setConexao(cn);
    //txtSeqEF.setListaCampos(lcDistrib);
    //txtCodFase.setListaCampos(lcDistrib);
    //txtSeqDE.setListaCampos(lcDistrib);
    //txtCodProd.setListaCampos(lcDistrib);
    //txtSeqEstDE.setListaCampos(lcDistrib);
    //lcDistrib.montaTab();
    
    tabDistrib.addMouseListener(this);
    
  }
  public Object[] getValores() {
    Object[] oRetorno = new Object[3];

    return oRetorno;
  }
  
  public void mouseClicked(MouseEvent mevt) {
    if (mevt.getClickCount() == 2) {
    	if (mevt.getSource() == tabDistrib && tabDistrib.getLinhaSel() >= 0)
    	  alteraDistrib();
    }
  }
  
  public void alteraDistrib(){
	  	DLFechaDistrib dl = new DLFechaDistrib(DLDistrib.this);
		dl.setVisible(true);
		if (dl.OK) {
			dl.dispose();
		} else {
			dl.dispose();
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
/*  	      tabDistrib.adicColuna("Seq.fase");
  	    tabDistrib.adicColuna("Cód.fase");
  	    tabDistrib.adicColuna("Descrição da fase");
  	    tabDistrib.adicColuna("Seq.distrib.");
  	    tabDistrib.adicColuna("Cód.prod.");
  	    tabDistrib.adicColuna("Descrição do produto");
  	    tabDistrib.adicColuna("Quant.");*/
  	  		vLinha = new Vector();
  	  		vLinha.addElement(new Integer(rs.getInt("SEQEF")));
  	  		vLinha.addElement(new Integer(rs.getInt("CODFASE")));
  	  		vLinha.addElement(rs.getString("DESCFASE"));
  	  		vLinha.addElement(new Integer(rs.getInt("SEQDE")));
  	  		vLinha.addElement(new Integer(rs.getInt("CODPROD")));
  	  		vLinha.addElement(rs.getString("DESCEST"));
  	  		vLinha.addElement(new Integer(rs.getInt("SEQESTDE")));
  	  		vLinha.addElement(new StringDireita( "0" ));
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
  
}
