/**
 * @version 19/06/2004 <BR>
 * @author Setpoint Informática Ltda./Robson Sanchez <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FRConfEstoq.java <BR>
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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import org.freedom.componentes.JLabelPad;

import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FRelatorio;

public class FRConfEstoq extends FRelatorio {
	private static final long serialVersionUID = 1L;

  
  private Vector vLabTipoRel = new Vector();
  private Vector vValTipoRel = new Vector();
  private JRadioGroup rgTipoRel = null;
  private JCheckBoxPad cbTipoMovEstoq = null;
  private JCheckBoxPad cbAtivo = null;

  public FRConfEstoq() {
    setTitulo("Relatório de Conferência de Estoque");
    setAtribos(80,30,350,250);
    
    vLabTipoRel.addElement("Saldos de lotes");
    vLabTipoRel.addElement("Saldos de produtos");
    vValTipoRel.addElement("L");
    vValTipoRel.addElement("S");
    
    rgTipoRel = new JRadioGroup(1,3,vLabTipoRel,vValTipoRel);
    
    cbTipoMovEstoq = new JCheckBoxPad("Apenas tipos de movimento c/ contr. de estoq.","S","N");
    cbTipoMovEstoq.setVlrString("S");
	
    cbAtivo = new JCheckBoxPad("Somente ativos.","S","N");
    cbAtivo.setVlrString("N");
    
    adic(new JLabelPad("Conferência"),7,0,250,20);
    adic(rgTipoRel,7,20,300,30);
    adic(cbTipoMovEstoq,7,50,300,30);
    adic(cbAtivo,7,80,300,30);
    
  }

  
  public void imprimir(boolean bVisualizar) {
	String sTipoMovEst = cbTipoMovEstoq.getVlrString();
	String sWhere = "";
  	try {
  		if (sTipoMovEst.equals("S"))
  			sWhere = " AND TM.ESTOQTIPOMOV='S' ";
		if (rgTipoRel.getVlrString().equals("L"))
			impLote(bVisualizar,sWhere);
		else if (rgTipoRel.getVlrString().equals("S"))
			impProduto(bVisualizar,sWhere);
	}
	finally {
		sTipoMovEst = null;
		sWhere = null;
	}

  }
  
  private void impProduto(boolean bVisualizar, String sWhere) {
  	String sSql = "";
  	ImprimeOS imp = null;
  	int linPag = 0;
  	PreparedStatement ps = null;
  	ResultSet rs = null;
  	double deSldCalc = 0;
  	double deQtdDif = 0;

  	try {
  		
  		imp = new ImprimeOS("",con);
  		linPag = imp.verifLinPag()-1;
  		imp.setTitulo("Relatorio de Conferência de Estoque por Produto");

  		sSql = "SELECT P.DESCPROD,P.CODPROD,P.REFPROD,P.SLDLIQPROD,"+
			"(SELECT SUM(QTDINVP) FROM EQINVPROD IT WHERE IT.CODEMPPD=P.CODEMP AND " +
			  "IT.CODFILIALPD=P.CODFILIAL AND IT.CODPROD=P.CODPROD ) QTDINVP, "+
			"(SELECT SUM(QTDITCOMPRA) FROM CPITCOMPRA IC, CPCOMPRA C, EQTIPOMOV TM " +
			" WHERE IC.CODEMPPD=P.CODEMP AND IC.CODFILIALPD=P.CODFILIAL AND IC.CODPROD=P.CODPROD AND " +
			" C.CODCOMPRA=IC.CODCOMPRA AND C.CODEMP=IC.CODEMP AND C.CODFILIAL=IC.CODFILIAL AND " +
			" TM.CODTIPOMOV=C.CODTIPOMOV AND TM.CODEMP=C.CODEMPTM AND TM.CODFILIAL=C.CODFILIALTM "+sWhere+
			") QTDITCOMPRA, "+
			"(SELECT SUM(QTDFINALPRODOP) FROM PPOP O, EQTIPOMOV TM " +
			" WHERE O.CODEMPPD=P.CODEMP AND O.CODFILIALPD=P.CODFILIAL AND O.CODPROD=P.CODPROD AND " +
			" TM.CODTIPOMOV=O.CODTIPOMOV AND TM.CODEMP=O.CODEMPTM AND TM.CODFILIAL=O.CODFILIALTM "+sWhere+
			") QTDFINALPRODOP, "+
			"(SELECT SUM(QTDEXPITRMA) FROM EQRMA R, EQITRMA IR, EQTIPOMOV TM " +
			" WHERE IR.CODEMPPD=P.CODEMP AND IR.CODFILIALPD=P.CODFILIAL AND IR.CODPROD=P.CODPROD AND " +
			" R.CODRMA=IR.CODRMA AND R.CODEMP=IR.CODEMP AND R.CODFILIAL=IR.CODFILIAL AND " +
			" TM.CODTIPOMOV=R.CODTIPOMOV AND TM.CODEMP=R.CODEMPTM AND TM.CODFILIAL=R.CODFILIALTM "+sWhere+
			") QTDEXPITRMA, "+
			"(SELECT SUM(QTDITVENDA) FROM VDITVENDA IV, VDVENDA V, EQTIPOMOV TM " +
			" WHERE IV.CODEMPPD=P.CODEMP AND IV.CODFILIALPD=P.CODFILIAL AND IV.CODPROD=P.CODPROD AND " +
			" V.CODVENDA=IV.CODVENDA AND V.TIPOVENDA=IV.TIPOVENDA AND V.CODEMP=IV.CODEMP AND " +
			"V.CODFILIAL=IV.CODFILIAL AND (NOT SUBSTR(V.STATUSVENDA,1,1)='C') AND " +
			"TM.CODTIPOMOV=V.CODTIPOMOV AND TM.CODEMP=V.CODEMPTM AND " +
			"TM.CODFILIAL=V.CODFILIALTM "+sWhere+") QTDITVENDA," +
			"(SELECT FIRST 1 M.SLDMOVPROD FROM EQMOVPROD M" +
			" WHERE M.CODEMPPD=P.CODEMP AND M.CODFILIALPD=P.CODFILIAL AND " +
			" M.CODPROD=P.CODPROD ORDER BY M.DTMOVPROD DESC, M.CODMOVPROD DESC ) SLDMOVPROD "+
			"FROM EQPRODUTO P WHERE P.CODEMP=? AND P.CODFILIAL=? " +
			(cbAtivo.getVlrString().equals("S")?"AND P.ATIVOPROD='S' ":"")+
			"AND ( ( NOT P.SLDLIQPROD=( SELECT FIRST 1 M.SLDMOVPROD FROM EQMOVPROD M" +
			" WHERE M.CODEMPPD=P.CODEMP AND M.CODFILIALPD=P.CODFILIAL AND " +
			" M.CODPROD=P.CODPROD ORDER BY M.DTMOVPROD DESC, M.CODMOVPROD DESC ) ) OR" +
			" ( NOT P.SLDLIQPROD=( "+
			"( COALESCE((SELECT SUM(QTDINVP) FROM EQINVPROD IT WHERE IT.CODEMPPD=P.CODEMP AND " +
			  "IT.CODFILIALPD=P.CODFILIAL AND IT.CODPROD=P.CODPROD),0 )) + " +
			"( COALESCE((SELECT SUM(QTDITCOMPRA) FROM CPITCOMPRA IC, CPCOMPRA C, EQTIPOMOV TM " +
			" WHERE IC.CODEMPPD=P.CODEMP AND IC.CODFILIALPD=P.CODFILIAL AND IC.CODPROD=P.CODPROD AND " +
			" C.CODCOMPRA=IC.CODCOMPRA AND C.CODEMP=IC.CODEMP AND C.CODFILIAL=IC.CODFILIAL AND " +
			" TM.CODTIPOMOV=C.CODTIPOMOV AND TM.CODEMP=C.CODEMPTM AND TM.CODFILIAL=C.CODFILIALTM "+sWhere+"),0 )) + " +
			"( COALESCE((SELECT SUM(QTDFINALPRODOP) FROM PPOP O, EQTIPOMOV TM " +
			" WHERE O.CODEMPPD=P.CODEMP AND O.CODFILIALPD=P.CODFILIAL AND O.CODPROD=P.CODPROD AND " +
			" TM.CODTIPOMOV=O.CODTIPOMOV AND TM.CODEMP=O.CODEMPTM AND TM.CODFILIAL=O.CODFILIALTM "+sWhere+"),0 )) - " + 
			"( COALESCE((SELECT SUM(QTDEXPITRMA) FROM EQRMA R, EQITRMA IR, EQTIPOMOV TM " +
			" WHERE IR.CODEMPPD=P.CODEMP AND IR.CODFILIALPD=P.CODFILIAL AND IR.CODPROD=P.CODPROD AND " +
			" R.CODRMA=IR.CODRMA AND R.CODEMP=IR.CODEMP AND R.CODFILIAL=IR.CODFILIAL AND " +
			" TM.CODTIPOMOV=R.CODTIPOMOV AND TM.CODEMP=R.CODEMPTM AND TM.CODFILIAL=R.CODFILIALTM "+sWhere+"),0 )) - " +
			"( COALESCE((SELECT SUM(QTDITVENDA) FROM VDITVENDA IV, VDVENDA V, EQTIPOMOV TM " +
			" WHERE IV.CODEMPPD=P.CODEMP AND IV.CODFILIALPD=P.CODFILIAL AND IV.CODPROD=P.CODPROD AND " +
			" V.CODVENDA=IV.CODVENDA AND V.TIPOVENDA=IV.TIPOVENDA AND V.CODEMP=IV.CODEMP AND " +
			"V.CODFILIAL=IV.CODFILIAL AND (NOT SUBSTR(V.STATUSVENDA,1,1)='C') AND " +
			"TM.CODTIPOMOV=V.CODTIPOMOV AND TM.CODEMP=V.CODEMPTM AND " +
			"TM.CODFILIAL=V.CODFILIALTM "+sWhere+"),0))"+
			")) ) ORDER BY P.DESCPROD" ;
  		System.out.println(sSql);
  		
  		try {
  			ps = con.prepareStatement(sSql);
  			ps.setInt(1,Aplicativo.iCodEmp);
  			ps.setInt(2,ListaCampos.getMasterFilial("EQPRODUTO"));
  			
  			rs = ps.executeQuery();
  			
  			imp.limpaPags();
  			
  			while ( rs.next() ) {
  				if (imp.pRow()>=(linPag-1)) {
  					imp.say(imp.pRow()+1,0,""+imp.comprimido());
  					imp.say(imp.pRow()+0,0,"+"+Funcoes.replicate("-",133)+"+");
  					imp.incPags();
  					imp.eject();

  				}
	  			if (imp.pRow()==0) {
	  				imp.montaCab();
	  				imp.setTitulo("Relatorio de Comferencia de Estoque");
	  				imp.addSubTitulo("CONFERENCIA DE ESTOQUE CONSIDERANDO SALDOS POR PRODUTO");
	  				imp.impCab(136, true);
	  					  				
  	  				imp.say(imp.pRow()+0,0,""+imp.comprimido());
					imp.say(imp.pRow()+0,1,"+"+Funcoes.replicate("-",133)+"+");
					imp.say(imp.pRow()+1,0,""+imp.comprimido());
					imp.say(imp.pRow()+0,0,"| DESCRICAO DO PRODUTO");
					imp.say(imp.pRow()+0,32,"| CODIGO");
					imp.say(imp.pRow()+0,44,"| SALDO ");
					imp.say(imp.pRow()+0,53,"| QTD.IV.");
					imp.say(imp.pRow()+0,62,"| QTD.OP.");
					imp.say(imp.pRow()+0,71,"| QTD.CP.");
					imp.say(imp.pRow()+0,80,"| QTD.RM.");
					imp.say(imp.pRow()+0,89,"| QTD.VD.");
					imp.say(imp.pRow()+0,98,"| SLD.CA.");
					imp.say(imp.pRow()+0,107,"| SLD.MP.");
					imp.say(imp.pRow()+0,116,"| DIF.SD.");
					imp.say(imp.pRow()+0,135,"|");
					imp.say(imp.pRow()+1,0,""+imp.comprimido());
					imp.say(imp.pRow()+0,1,"+"+Funcoes.replicate("-",133)+"+");
					
				}
				
	  			deSldCalc = rs.getDouble("QTDINVP") + rs.getDouble("QTDITCOMPRA") +
	  			   rs.getDouble("QTDFINALPRODOP") - rs.getDouble("QTDEXPITRMA") - 
	  			   rs.getDouble("QTDITVENDA"); 
	  			deQtdDif = deSldCalc - rs.getDouble("SLDLIQPROD") ;
	  			if (deQtdDif==0) {
	  				deQtdDif = rs.getDouble("SLDMOVPROD") - rs.getDouble("SLDLIQPROD");
	  			}
  				imp.say(imp.pRow()+1,0,""+imp.comprimido());
  				imp.say(imp.pRow()+0,0,"|"+Funcoes.adicionaEspacos(rs.getString("DESCPROD"),30));
  				imp.say(imp.pRow()+0,32,"|"+Funcoes.adicionaEspacos(rs.getString("CODPROD"),10));
  				imp.say(imp.pRow()+0,44,"|"+Funcoes.adicEspacosEsquerda(rs.getDouble("SLDLIQPROD")+"",8));
  				imp.say(imp.pRow()+0,53,"|"+Funcoes.adicEspacosEsquerda(rs.getDouble("QTDINVP")+"",8));
  				imp.say(imp.pRow()+0,62,"|"+Funcoes.adicEspacosEsquerda(rs.getDouble("QTDFINALPRODOP")+"",8));
  				imp.say(imp.pRow()+0,71,"|"+Funcoes.adicEspacosEsquerda(rs.getDouble("QTDITCOMPRA")+"",8));
  				imp.say(imp.pRow()+0,80,"|"+Funcoes.adicEspacosEsquerda(rs.getDouble("QTDITEXPRMA")+"",8));
  				imp.say(imp.pRow()+0,89,"|"+Funcoes.adicEspacosEsquerda(rs.getDouble("QTDITVENDA")+"",8));
  				imp.say(imp.pRow()+0,98,"|"+Funcoes.adicEspacosEsquerda(deSldCalc+"",8));
  				imp.say(imp.pRow()+0,107,"|"+Funcoes.adicEspacosEsquerda(rs.getDouble("SLDMOVPROD")+"",8));
  				imp.say(imp.pRow()+0,116,"|"+Funcoes.adicEspacosEsquerda(deQtdDif+"",8));
  				imp.say(imp.pRow()+0,135,"|");
  				
  			}
  			
  			rs.close();
  			ps.close();
  			if (!con.getAutoCommit())
  				con.commit();

  			// Fim da impressão do total por setor
  			
  			imp.say(imp.pRow()+1,0,""+imp.comprimido());
  			imp.say(imp.pRow()+0,1,"+"+Funcoes.replicate("-",133)+"+");

  			imp.eject();
  			imp.fechaGravacao();
  			
  		}
  		catch (SQLException err) {
  			Funcoes.mensagemErro(this,"Erro executando a consulta.\n"+err.getMessage(),true,con,err);
  			err.printStackTrace();
  		}
  		if (bVisualizar) {
  			imp.preview(this);
  		}
  		else {
  			imp.print();
  		}
  	}
  	finally {
  		sSql = null;
  		imp = null;
  		ps = null;
  		rs = null;
  	  	deSldCalc = 0;
  	  	deQtdDif = 0;
  	}
  	
  }

  private void impLote(boolean bVisualizar, String sWhere) {
  	String sSql = "";
  	ImprimeOS imp = null;
  	int linPag = 0;
  	PreparedStatement ps = null;
  	ResultSet rs = null;
  	double deSldCalc = 0;
  	double deQtdDif = 0;

  	try {
  		
  		imp = new ImprimeOS("",con);
  		linPag = imp.verifLinPag()-1;
  		imp.setTitulo("Relatorio de Conferência de Estoque por Lote");

  		sSql = "SELECT P.DESCPROD,P.CODPROD,L.CODLOTE,L.SLDLIQLOTE,"+
			"(SELECT SUM(QTDINVP) FROM EQINVPROD IT WHERE IT.CODEMPPD=P.CODEMP AND " +
			  "IT.CODFILIALPD=P.CODFILIAL AND IT.CODPROD=P.CODPROD AND IT.CODEMPLE=L.CODEMP AND " +
			  "IT.CODFILIALLE=L.CODFILIAL AND IT.CODLOTE=L.CODLOTE) QTDINVP, "+
			"(SELECT SUM(QTDITCOMPRA) FROM CPITCOMPRA IC, CPCOMPRA C, EQTIPOMOV TM " +
			" WHERE IC.CODEMPPD=P.CODEMP AND IC.CODFILIALPD=P.CODFILIAL AND IC.CODPROD=P.CODPROD AND " +
			" IC.CODEMPLE=L.CODEMP AND IC.CODFILIALLE=L.CODFILIAL AND IC.CODLOTE=L.CODLOTE AND " +
			" C.CODCOMPRA=IC.CODCOMPRA AND C.CODEMP=IC.CODEMP AND C.CODFILIAL=IC.CODFILIAL AND " +
			" TM.CODTIPOMOV=C.CODTIPOMOV AND TM.CODEMP=C.CODEMPTM AND TM.CODFILIAL=C.CODFILIALTM " +
			sWhere+") QTDITCOMPRA, "+
			"(SELECT SUM(QTDFINALPRODOP) FROM PPOP O, EQTIPOMOV TM " +
			" WHERE O.CODEMPPD=P.CODEMP AND O.CODFILIALPD=P.CODFILIAL AND O.CODPROD=P.CODPROD AND " +
			" O.CODEMPLE=L.CODEMP AND O.CODFILIALLE=L.CODFILIAL AND O.CODLOTE=L.CODLOTE AND " +
			" TM.CODTIPOMOV=O.CODTIPOMOV AND TM.CODEMP=O.CODEMPTM AND TM.CODFILIAL=O.CODFILIALTM "+sWhere+
			") QTDFINALPRODOP, "+
			"(SELECT SUM(QTDEXPITRMA) FROM EQRMA R, EQITRMA IR, EQTIPOMOV TM " +
			" WHERE IR.CODEMPPD=P.CODEMP AND IR.CODFILIALPD=P.CODFILIAL AND IR.CODPROD=P.CODPROD AND " +
			" R.CODRMA=IR.CODRMA AND R.CODEMP=IR.CODEMP AND R.CODFILIAL=IR.CODFILIAL AND " +
			" IR.CODEMPLE=L.CODEMP AND IR.CODFILIALLE=L.CODFILIAL AND IR.CODLOTE=L.CODLOTE AND " +
			" TM.CODTIPOMOV=R.CODTIPOMOV AND TM.CODEMP=R.CODEMPTM AND TM.CODFILIAL=R.CODFILIALTM "+sWhere+
			") QTDEXPITRMA, "+
			"(SELECT SUM(QTDITVENDA) FROM VDITVENDA IV, VDVENDA V, EQTIPOMOV TM " +
			" WHERE IV.CODEMPPD=P.CODEMP AND IV.CODFILIALPD=P.CODFILIAL AND IV.CODPROD=P.CODPROD AND " +
			" IV.CODEMPLE=L.CODEMP AND IV.CODFILIALLE=L.CODFILIAL AND IV.CODLOTE=L.CODLOTE AND " +
			" V.CODVENDA=IV.CODVENDA AND V.TIPOVENDA=IV.TIPOVENDA AND V.CODEMP=IV.CODEMP AND " +
			" V.CODFILIAL=IV.CODFILIAL AND (NOT SUBSTR(V.STATUSVENDA,1,1)='C') AND " +
			" TM.CODTIPOMOV=V.CODTIPOMOV AND TM.CODEMP=V.CODEMPTM AND " +
			" TM.CODFILIAL=V.CODFILIALTM "+sWhere+") QTDITVENDA "+
			"FROM EQPRODUTO P,EQLOTE L WHERE P.cloteprod='S' "+
			"AND L.CODEMP=P.CODEMP AND L.CODFILIAL=P.CODFILIAL AND L.CODPROD=P.CODPROD "+
			"AND P.CODEMP=? AND P.CODFILIAL=? "+
			(cbAtivo.getVlrString().equals("S")?"AND P.ATIVOPROD='S'":"")+			
			"AND ( NOT L.SLDLIQLOTE=( "+
			"( COALESCE( (SELECT SUM(QTDINVP) FROM EQINVPROD IT WHERE IT.CODEMPPD=P.CODEMP AND " +
			  "IT.CODFILIALPD=P.CODFILIAL AND IT.CODPROD=P.CODPROD AND IT.CODEMPLE=L.CODEMP AND " +
			  "IT.CODFILIALLE=L.CODFILIAL AND IT.CODLOTE=L.CODLOTE),0) ) + " +
			"( COALESCE( (SELECT SUM(QTDITCOMPRA) FROM CPITCOMPRA IC, CPCOMPRA C, EQTIPOMOV TM " +
			" WHERE IC.CODEMPPD=P.CODEMP AND IC.CODFILIALPD=P.CODFILIAL AND IC.CODPROD=P.CODPROD AND " +
			" IC.CODEMPLE=L.CODEMP AND IC.CODFILIALLE=L.CODFILIAL AND IC.CODLOTE=L.CODLOTE AND " +
			" C.CODCOMPRA=IC.CODCOMPRA AND C.CODEMP=IC.CODEMP AND C.CODFILIAL=IC.CODFILIAL AND " +
			" TM.CODTIPOMOV=C.CODTIPOMOV AND TM.CODEMP=C.CODEMPTM AND TM.CODFILIAL=C.CODFILIALTM "+sWhere+"),0) ) + "+
			"(COALESCE( (SELECT SUM(QTDFINALPRODOP) FROM PPOP O, EQTIPOMOV TM " +
			" WHERE O.CODEMPPD=P.CODEMP AND O.CODFILIALPD=P.CODFILIAL AND O.CODPROD=P.CODPROD AND " +
			" O.CODEMPLE=L.CODEMP AND O.CODFILIALLE=L.CODFILIAL AND O.CODLOTE=L.CODLOTE AND " +
			" TM.CODTIPOMOV=O.CODTIPOMOV AND TM.CODEMP=O.CODEMPTM AND TM.CODFILIAL=O.CODFILIALTM "+sWhere+"),0) ) - "+
			"(COALESCE( (SELECT SUM(QTDEXPITRMA) FROM EQRMA R, EQITRMA IR, EQTIPOMOV TM " +
			" WHERE IR.CODEMPPD=P.CODEMP AND IR.CODFILIALPD=P.CODFILIAL AND IR.CODPROD=P.CODPROD AND " +
			" R.CODRMA=IR.CODRMA AND R.CODEMP=IR.CODEMP AND R.CODFILIAL=IR.CODFILIAL AND " +
			" IR.CODEMPLE=L.CODEMP AND IR.CODFILIALLE=L.CODFILIAL AND IR.CODLOTE=L.CODLOTE AND " +
			" TM.CODTIPOMOV=R.CODTIPOMOV AND TM.CODEMP=R.CODEMPTM AND TM.CODFILIAL=R.CODFILIALTM "+sWhere+"),0) ) - "+			
			"( COALESCE( (SELECT SUM(QTDITVENDA) FROM VDITVENDA IV, VDVENDA V, EQTIPOMOV TM " +
			" WHERE IV.CODEMPPD=P.CODEMP AND IV.CODFILIALPD=P.CODFILIAL AND IV.CODPROD=P.CODPROD AND " +
			" IV.CODEMPLE=L.CODEMP AND IV.CODFILIALLE=L.CODFILIAL AND IV.CODLOTE=L.CODLOTE AND " +
			" V.CODVENDA=IV.CODVENDA AND V.TIPOVENDA=IV.TIPOVENDA AND V.CODEMP=IV.CODEMP AND " +
			" V.CODFILIAL=IV.CODFILIAL AND (NOT SUBSTR(V.STATUSVENDA,1,1)='C') AND " +
			" TM.CODTIPOMOV=V.CODTIPOMOV AND TM.CODEMP=V.CODEMPTM AND " +
			" TM.CODFILIAL=V.CODFILIALTM "+sWhere+"),0 ) ) "+
			")) ORDER BY P.DESCPROD,L.CODLOTE" ;
//  		System.out.println(sSql);
  		
  		try {
  			ps = con.prepareStatement(sSql);
  			ps.setInt(1,Aplicativo.iCodEmp);
  			ps.setInt(2,ListaCampos.getMasterFilial("EQPRODUTO"));
  			
  			rs = ps.executeQuery();
  			
  			imp.limpaPags();
  			
  			while ( rs.next() ) {
  				if (imp.pRow()>=(linPag-1)) {
  					imp.say(imp.pRow()+1,0,""+imp.comprimido());
  					imp.say(imp.pRow()+0,0,"+"+Funcoes.replicate("-",133)+"+");
  					imp.incPags();
  					imp.eject();

  				}
	  			if (imp.pRow()==0) {
	  				imp.montaCab();
	  				imp.setTitulo("Relatorio de Comferencia de Estoque");
	  				imp.addSubTitulo("CONFERENCIA DE ESTOQUE CONSIDERANDO SALDOS POR LOTE");
	  				imp.impCab(136, true);
	  					  				
  	  				imp.say(imp.pRow()+0,0,""+imp.comprimido());
  	  				imp.say(imp.pRow()+0,0,"|");
  	  				imp.say(imp.pRow()+0,135,"|");
  	  				imp.say(imp.pRow()+1,0,""+imp.comprimido());
					imp.say(imp.pRow()+0,1,"+"+Funcoes.replicate("-",133)+"+");
					imp.say(imp.pRow()+1,0,""+imp.comprimido());
					imp.say(imp.pRow()+0,0,"| DESCRICAO");
					imp.say(imp.pRow()+0,35,"| CODIGO");
					imp.say(imp.pRow()+0,47,"| LOTE");
					imp.say(imp.pRow()+0,61,"| SALDO ");
					imp.say(imp.pRow()+0,70,"| QTD.IV.");
					imp.say(imp.pRow()+0,79,"| QTD.OP.");
					imp.say(imp.pRow()+0,88,"| QTD.CP.");
					imp.say(imp.pRow()+0,97,"| QTD.RM.");
					imp.say(imp.pRow()+0,106,"| QTD.VD.");
					imp.say(imp.pRow()+0,115,"| SLD.CA.");
					imp.say(imp.pRow()+0,124,"| DIF.SD.");
					imp.say(imp.pRow()+0,135,"|");
					imp.say(imp.pRow()+1,0,""+imp.comprimido());
					imp.say(imp.pRow()+0,1,"+"+Funcoes.replicate("-",133)+"+");
					
				}
				
	  			deSldCalc = rs.getDouble("QTDINVP") + rs.getDouble("QTDITCOMPRA") +
	  			   rs.getDouble("QTDFINALPRODOP") - rs.getDouble("QTDEXPITRMA") - 
	  			   rs.getDouble("QTDITVENDA"); 
	  			deQtdDif = deSldCalc - rs.getDouble("SLDLIQLOTE") ;

  				imp.say(imp.pRow()+1,0,""+imp.comprimido());
  				imp.say(imp.pRow()+0,0,"|"+Funcoes.adicionaEspacos(rs.getString("DESCPROD"),33));
  				imp.say(imp.pRow()+0,35,"|"+Funcoes.adicionaEspacos(rs.getString("CODPROD"),10));
  				imp.say(imp.pRow()+0,47,"|"+Funcoes.adicionaEspacos(rs.getString("CODLOTE"),13));
  				imp.say(imp.pRow()+0,61,"|"+Funcoes.adicEspacosEsquerda(rs.getDouble("SLDLIQLOTE")+"",8));
  				imp.say(imp.pRow()+0,70,"|"+Funcoes.adicEspacosEsquerda(rs.getDouble("QTDINVP")+"",8));
  				imp.say(imp.pRow()+0,79,"|"+Funcoes.adicEspacosEsquerda(rs.getDouble("QTDFINALPRODOP")+"",8));
  				imp.say(imp.pRow()+0,88,"|"+Funcoes.adicEspacosEsquerda(rs.getDouble("QTDITCOMPRA")+"",8));
  				imp.say(imp.pRow()+0,97,"|"+Funcoes.adicEspacosEsquerda(rs.getDouble("QTDEXPITRMA")+"",8));
  				imp.say(imp.pRow()+0,106,"|"+Funcoes.adicEspacosEsquerda(rs.getDouble("QTDITVENDA")+"",8));
  				imp.say(imp.pRow()+0,115,"|"+Funcoes.adicEspacosEsquerda(deSldCalc+"",8));
  				imp.say(imp.pRow()+0,124,"|"+Funcoes.adicEspacosEsquerda(deQtdDif+"",8));
  				imp.say(imp.pRow()+0,135,"|");
  				
  			}
  			
  			rs.close();
  			ps.close();
  			if (!con.getAutoCommit())
  				con.commit();

  			// Fim da impressão do total por setor
  			
  			imp.say(imp.pRow()+1,0,""+imp.comprimido());
  			imp.say(imp.pRow()+0,1,"+"+Funcoes.replicate("-",133)+"+");

  			imp.eject();
  			imp.fechaGravacao();
  			
  		}
  		catch (SQLException err) {
  			Funcoes.mensagemErro(this,"Erro executando a consulta.\n"+err.getMessage(),true,con,err);
  			err.printStackTrace();
  		}
  		if (bVisualizar) {
  			imp.preview(this);
  		}
  		else {
  			imp.print();
  		}
  	}
  	finally {
  		sSql = null;
  		imp = null;
  		ps = null;
  		rs = null;
  	  	deSldCalc = 0;
  	  	deQtdDif = 0;
  	}
  	
  }
  
  /*
   * 
   * @author robson
   *
UPDATE EQLOTE P SET P.SLDLOTE=
((SELECT SUM(QTDINVP) FROM EQINVPROD IT WHERE IT.CODEMPPD=P.CODEMP AND
IT.CODFILIALPD=P.CODFILIAL AND IT.CODPROD=P.CODPROD AND
 IT.CODEMPLE=P.CODEMP AND IT.CODFILIALLE=P.CODFILIAL AND IT.CODLOTE=P.CODLOTE  ) +
(SELECT SUM(QTDITCOMPRA) FROM CPITCOMPRA IC, CPCOMPRA C, EQTIPOMOV TM
 WHERE IC.CODEMPPD=P.CODEMP AND IC.CODFILIALPD=P.CODFILIAL AND IC.CODPROD=P.CODPROD AND
 IC.CODEMPLE=P.CODEMP AND IC.CODFILIALLE=P.CODFILIAL AND IC.CODLOTE=P.CODLOTE AND
 C.CODCOMPRA=IC.CODCOMPRA AND C.CODEMP=IC.CODEMP AND C.CODFILIAL=IC.CODFILIAL AND
 TM.CODTIPOMOV=C.CODTIPOMOV AND TM.CODEMP=C.CODEMPTM AND TM.CODFILIAL=C.CODFILIALTM AND
 TM.ESTOQTIPOMOV='S'
) -
(SELECT SUM(QTDITVENDA) FROM VDITVENDA IV, VDVENDA V, EQTIPOMOV TM
 WHERE IV.CODEMPPD=P.CODEMP AND IV.CODFILIALPD=P.CODFILIAL AND IV.CODPROD=P.CODPROD AND
  IV.CODEMPLE=P.CODEMP AND IV.CODFILIALLE=P.CODFILIAL AND IV.CODLOTE=P.CODLOTE AND
 V.CODVENDA=IV.CODVENDA AND V.TIPOVENDA=IV.TIPOVENDA AND V.CODEMP=IV.CODEMP AND
V.CODFILIAL=IV.CODFILIAL AND (NOT SUBSTR(V.STATUSVENDA,1,1)='C') AND
TM.CODTIPOMOV=V.CODTIPOMOV AND TM.CODEMP=V.CODEMPTM AND
TM.CODFILIAL=V.CODFILIALTM AND TM.ESTOQTIPOMOV='S') )
 WHERE P.CODEMP=4 AND P.CODFILIAL=1
AND  ( NOT P.SLDLIQLOTE=(
( COALESCE((SELECT SUM(QTDINVP) FROM EQINVPROD IT WHERE IT.CODEMPPD=P.CODEMP AND
              IT.CODFILIALPD=P.CODFILIAL AND IT.CODPROD=P.CODPROD AND
               IT.CODEMPLE=P.CODEMP AND IT.CODFILIALLE=P.CODFILIAL AND IT.CODLOTE=P.CODLOTE AND
              ),0 )) +
( COALESCE((SELECT SUM(QTDITCOMPRA) FROM CPITCOMPRA IC, CPCOMPRA C, EQTIPOMOV TM
 WHERE IC.CODEMPPD=P.CODEMP AND IC.CODFILIALPD=P.CODFILIAL AND IC.CODPROD=P.CODPROD AND
  IC.CODEMPLE=P.CODEMP AND IC.CODFILIALLE=P.CODFILIAL AND IC.CODLOTE=P.CODLOTE AND
 C.CODCOMPRA=IC.CODCOMPRA AND C.CODEMP=IC.CODEMP AND C.CODFILIAL=IC.CODFILIAL AND
 TM.CODTIPOMOV=C.CODTIPOMOV AND TM.CODEMP=C.CODEMPTM AND TM.CODFILIAL=C.CODFILIALTM AND
 TM.ESTOQTIPOMOV='S'),0 )) -
( COALESCE((SELECT SUM(QTDITVENDA) FROM VDITVENDA IV, VDVENDA V, EQTIPOMOV TM
 WHERE IV.CODEMPPD=P.CODEMP AND IV.CODFILIALPD=P.CODFILIAL AND IV.CODPROD=P.CODPROD AND
  IV.CODEMPLE=P.CODEMP AND IV.CODFILIALLE=P.CODFILIAL AND IV.CODLOTE=P.CODLOTE AND
 V.CODVENDA=IV.CODVENDA AND V.TIPOVENDA=IV.TIPOVENDA AND V.CODEMP=IV.CODEMP AND
V.CODFILIAL=IV.CODFILIAL AND (NOT SUBSTR(V.STATUSVENDA,1,1)='C') AND
TM.CODTIPOMOV=V.CODTIPOMOV AND TM.CODEMP=V.CODEMPTM AND
TM.CODFILIAL=V.CODFILIALTM AND TM.ESTOQTIPOMOV='S'),0))
)) ;

   * 
   * 
   * 
   *    * TODO To change the template for this generated type comment go to
   * Window - Preferences - Java - Code Style - Code Templates
   */
  
}
