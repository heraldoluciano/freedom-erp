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
import java.util.Date;
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
  
  private Vector vLabTipoRel = new Vector();
  private Vector vValTipoRel = new Vector();
  private JRadioGroup rgTipoRel = null;
  private JCheckBoxPad cbTipoMovEstoq = null;

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
	
    adic(new JLabelPad("Conferência"),7,0,250,20);
    adic(rgTipoRel,7,20,300,30);
    adic(cbTipoMovEstoq,7,50,300,30);
    
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
			"(SELECT SUM(QTDITVENDA) FROM VDITVENDA IV, VDVENDA V, EQTIPOMOV TM " +
			" WHERE IV.CODEMPPD=P.CODEMP AND IV.CODFILIALPD=P.CODFILIAL AND IV.CODPROD=P.CODPROD AND " +
			" V.CODVENDA=IV.CODVENDA AND V.TIPOVENDA=IV.TIPOVENDA AND V.CODEMP=IV.CODEMP AND " +
			"V.CODFILIAL=IV.CODFILIAL AND (NOT SUBSTR(V.STATUSVENDA,1,1)='C') AND " +
			"TM.CODTIPOMOV=V.CODTIPOMOV AND TM.CODEMP=V.CODEMPTM AND " +
			"TM.CODFILIAL=V.CODFILIALTM "+sWhere+") QTDITVENDA," +
			"(SELECT FIRST 1 M.SLDMOVPROD FROM EQMOVPROD M" +
			" WHERE M.CODEMPPD=P.CODEMP AND M.CODFILIALPD=P.CODFILIAL AND " +
			" M.CODPROD=P.CODPROD ORDER BY M.DTMOVPROD DESC, M.CODMOVPROD DESC ) SLDMOVPROD "+
			"FROM EQPRODUTO P WHERE P.CODEMP=? AND P.CODFILIAL=? "+
			"AND ( ( NOT P.SLDLIQPROD=( SELECT FIRST 1 M.SLDMOVPROD FROM EQMOVPROD M" +
			" WHERE M.CODEMPPD=P.CODEMP AND M.CODFILIALPD=P.CODFILIAL AND " +
			" M.CODPROD=P.CODPROD ORDER BY M.DTMOVPROD DESC, M.CODMOVPROD DESC ) ) OR" +
			" ( NOT P.SLDLIQPROD=( "+
			"( COALESCE((SELECT SUM(QTDINVP) FROM EQINVPROD IT WHERE IT.CODEMPPD=P.CODEMP AND " +
			  "IT.CODFILIALPD=P.CODFILIAL AND IT.CODPROD=P.CODPROD),0 )) + " +
			"( COALESCE((SELECT SUM(QTDITCOMPRA) FROM CPITCOMPRA IC, CPCOMPRA C, EQTIPOMOV TM " +
			" WHERE IC.CODEMPPD=P.CODEMP AND IC.CODFILIALPD=P.CODFILIAL AND IC.CODPROD=P.CODPROD AND " +
			" C.CODCOMPRA=IC.CODCOMPRA AND C.CODEMP=IC.CODEMP AND C.CODFILIAL=IC.CODFILIAL AND " +
			" TM.CODTIPOMOV=C.CODTIPOMOV AND TM.CODEMP=C.CODEMPTM AND TM.CODFILIAL=C.CODFILIALTM "+sWhere+"),0 )) - "+
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
  					imp.say(imp.pRow()+0,0,"+"+Funcoes.replicate("-",134)+"+");
  					imp.incPags();
  					imp.eject();

  				}
	  			if (imp.pRow()==0) {
	  				imp.say(imp.pRow()+1,0,""+imp.comprimido());
	  				imp.say(imp.pRow()+0,1,"+"+Funcoes.replicate("-",133)+"+");
	  				imp.say(imp.pRow()+1,0,""+imp.comprimido());
	  				imp.say(imp.pRow()+0,1,"| Emitido em :"+Funcoes.dateToStrDate(new Date()));
	  				imp.say(imp.pRow()+0,120,"Pagina : "+(imp.getNumPags()));
	  				imp.say(imp.pRow()+0,136,"|");
	  				imp.say(imp.pRow()+1,0,""+imp.comprimido());
	  				imp.say(imp.pRow()+0,1,"|");
	  				imp.say(imp.pRow()+0,55,"CONFERENCIA DE ESTOQUE CONSIDERANDO SALDOS POR PRODUTO");
	  				imp.say(imp.pRow()+0,136,"|");
  	  				imp.say(imp.pRow()+1,0,""+imp.comprimido());
  	  				imp.say(imp.pRow()+0,1,"|");
  	  				imp.say(imp.pRow()+0,136,"|");
  	  				imp.say(imp.pRow()+1,0,""+imp.comprimido());
					imp.say(imp.pRow()+0,1,"+"+Funcoes.replicate("-",133)+"+");
					imp.say(imp.pRow()+1,0,""+imp.comprimido());
					imp.say(imp.pRow()+0,1,"| DESCRICAO DO PRODUTO");
					imp.say(imp.pRow()+0,32,"| CODIGO");
					imp.say(imp.pRow()+0,44,"| REF.");
					imp.say(imp.pRow()+0,59,"| SALDO ");
					imp.say(imp.pRow()+0,70,"| QTD.INV.");
					imp.say(imp.pRow()+0,81,"| QTD.CP.");
					imp.say(imp.pRow()+0,92,"| QTD.VD.");
					imp.say(imp.pRow()+0,103,"| SLD.CALC.");
					imp.say(imp.pRow()+0,114,"| SLD.M.P.");
					imp.say(imp.pRow()+0,125,"| DIF.SLD.");
					imp.say(imp.pRow()+0,136,"|");
					imp.say(imp.pRow()+1,0,""+imp.comprimido());
					imp.say(imp.pRow()+0,1,"+"+Funcoes.replicate("-",133)+"+");
					
				}
				
	  			deSldCalc = rs.getDouble(5) + rs.getDouble(6) - rs.getDouble(7); 
	  			deQtdDif = deSldCalc - rs.getDouble(4) ;
	  			if (deQtdDif==0) {
	  				deQtdDif = rs.getDouble(8) - rs.getDouble(4);
	  			}
  				imp.say(imp.pRow()+1,0,""+imp.comprimido());
  				imp.say(imp.pRow()+0,1,"|"+Funcoes.adicionaEspacos(rs.getString(1),30));
  				imp.say(imp.pRow()+0,32,"|"+Funcoes.adicEspacosEsquerda(rs.getString(2),10));
  				imp.say(imp.pRow()+0,44,"|"+Funcoes.adicionaEspacos(rs.getString(3),13));
  				imp.say(imp.pRow()+0,59,"|"+Funcoes.adicEspacosEsquerda(rs.getDouble(4)+"",10));
  				imp.say(imp.pRow()+0,70,"|"+Funcoes.adicEspacosEsquerda(rs.getDouble(5)+"",10));
  				imp.say(imp.pRow()+0,81,"|"+Funcoes.adicEspacosEsquerda(rs.getDouble(6)+"",10));
  				imp.say(imp.pRow()+0,92,"|"+Funcoes.adicEspacosEsquerda(rs.getDouble(7)+"",10));
  				imp.say(imp.pRow()+0,103,"|"+Funcoes.adicEspacosEsquerda(deSldCalc+"",10));
  				imp.say(imp.pRow()+0,114,"|"+Funcoes.adicEspacosEsquerda(rs.getDouble(8)+"",10));
  				imp.say(imp.pRow()+0,125,"|"+Funcoes.adicEspacosEsquerda(deQtdDif+"",10));
  				imp.say(imp.pRow()+0,136,"|");
  				
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
  		catch (SQLException e) {
  			Funcoes.mensagemErro(this,"Erro executando a consulta.\n"+e.getMessage());
  			e.printStackTrace();
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
			"AND ( NOT L.SLDLIQLOTE=( "+
			"( COALESCE( (SELECT SUM(QTDINVP) FROM EQINVPROD IT WHERE IT.CODEMPPD=P.CODEMP AND " +
			  "IT.CODFILIALPD=P.CODFILIAL AND IT.CODPROD=P.CODPROD AND IT.CODEMPLE=L.CODEMP AND " +
			  "IT.CODFILIALLE=L.CODFILIAL AND IT.CODLOTE=L.CODLOTE),0) ) + " +
			"( COALESCE( (SELECT SUM(QTDITCOMPRA) FROM CPITCOMPRA IC, CPCOMPRA C, EQTIPOMOV TM " +
			" WHERE IC.CODEMPPD=P.CODEMP AND IC.CODFILIALPD=P.CODFILIAL AND IC.CODPROD=P.CODPROD AND " +
			" IC.CODEMPLE=L.CODEMP AND IC.CODFILIALLE=L.CODFILIAL AND IC.CODLOTE=L.CODLOTE AND " +
			" C.CODCOMPRA=IC.CODCOMPRA AND C.CODEMP=IC.CODEMP AND C.CODFILIAL=IC.CODFILIAL AND " +
			" TM.CODTIPOMOV=C.CODTIPOMOV AND TM.CODEMP=C.CODEMPTM AND TM.CODFILIAL=C.CODFILIALTM "+sWhere+"),0) ) - "+
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
  					imp.say(imp.pRow()+0,0,"+"+Funcoes.replicate("-",134)+"+");
  					imp.incPags();
  					imp.eject();

  				}
	  			if (imp.pRow()==0) {
	  				imp.impCab(136, false);
	  				imp.say(imp.pRow()+1,0,""+imp.comprimido());
	  				imp.say(imp.pRow()+0,1,"+"+Funcoes.replicate("-",133)+"+");
	  				imp.say(imp.pRow()+1,0,""+imp.comprimido());
	  				imp.say(imp.pRow()+0,1,"| Emitido em :"+Funcoes.dateToStrDate(new Date()));
	  				imp.say(imp.pRow()+0,120,"Pagina : "+(imp.getNumPags()));
	  				imp.say(imp.pRow()+0,136,"|");
	  				imp.say(imp.pRow()+1,0,""+imp.comprimido());
	  				imp.say(imp.pRow()+0,1,"|");
	  				imp.say(imp.pRow()+0,55,"CONFERENCIA DE ESTOQUE CONSIDERANDO SALDOS POR LOTE");
	  				imp.say(imp.pRow()+0,136,"|");
  	  				imp.say(imp.pRow()+1,0,""+imp.comprimido());
  	  				imp.say(imp.pRow()+0,1,"|");
  	  				imp.say(imp.pRow()+0,136,"|");
  	  				imp.say(imp.pRow()+1,0,""+imp.comprimido());
					imp.say(imp.pRow()+0,1,"+"+Funcoes.replicate("-",133)+"+");
					imp.say(imp.pRow()+1,0,""+imp.comprimido());
					imp.say(imp.pRow()+0,1,"| DESCRICAO DO PRODUTO");
					imp.say(imp.pRow()+0,43,"| CODIGO");
					imp.say(imp.pRow()+0,54,"| LOTE");
					imp.say(imp.pRow()+0,69,"| SALDO ");
					imp.say(imp.pRow()+0,80,"| QTD.INV.");
					imp.say(imp.pRow()+0,91,"| QTD.CP.");
					imp.say(imp.pRow()+0,102,"| QTD.VD.");
					imp.say(imp.pRow()+0,113,"| SLD.CALC.");
					imp.say(imp.pRow()+0,124,"| DIF.SLD.");
					imp.say(imp.pRow()+0,136,"|");
					imp.say(imp.pRow()+1,0,""+imp.comprimido());
					imp.say(imp.pRow()+0,1,"+"+Funcoes.replicate("-",133)+"+");
					
				}
				
	  			deSldCalc = rs.getDouble(5) + rs.getDouble(6) - rs.getDouble(7); 
	  			deQtdDif = deSldCalc - rs.getDouble(4) ; 
  				imp.say(imp.pRow()+1,0,""+imp.comprimido());
  				imp.say(imp.pRow()+0,1,"|"+Funcoes.adicionaEspacos(rs.getString(1),40));
  				imp.say(imp.pRow()+0,42,"|"+Funcoes.adicEspacosEsquerda(rs.getString(2),10));
  				imp.say(imp.pRow()+0,54,"|"+Funcoes.adicionaEspacos(rs.getString(3),13));
  				imp.say(imp.pRow()+0,69,"|"+Funcoes.adicEspacosEsquerda(rs.getDouble(4)+"",10));
  				imp.say(imp.pRow()+0,80,"|"+Funcoes.adicEspacosEsquerda(rs.getDouble(5)+"",10));
  				imp.say(imp.pRow()+0,91,"|"+Funcoes.adicEspacosEsquerda(rs.getDouble(6)+"",10));
  				imp.say(imp.pRow()+0,102,"|"+Funcoes.adicEspacosEsquerda(rs.getDouble(7)+"",10));
  				imp.say(imp.pRow()+0,113,"|"+Funcoes.adicEspacosEsquerda(deSldCalc+"",10));
  				imp.say(imp.pRow()+0,124,"|"+Funcoes.adicEspacosEsquerda(deQtdDif+"",10));
  				imp.say(imp.pRow()+0,136,"|");
  				
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
  		catch (SQLException e) {
  			Funcoes.mensagemErro(this,"Erro executando a consulta.\n"+e.getMessage());
  			e.printStackTrace();
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
  
  
}
