/**
 * @version 12/08/2004 <BR>
 * @author Setpoint Informática Ltda./Robson Sanchez <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FRPisCofins.java <BR>
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
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Vector;

import org.freedom.componentes.JLabelPad;

import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FRelatorio;

public class FRPisCofins extends FRelatorio {
  private JTextFieldPad txtDataini = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
  private JTextFieldPad txtDatafim = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
  
  private JCheckBoxPad cbSemMov = new JCheckBoxPad("Ocultar produtos sem movimento","S","N");
  
  private Vector vPisLab = new Vector();
  private Vector vPisVal = new Vector();
  private JRadioGroup rgPis = null;

  private Vector vCofinsLab = new Vector();
  private Vector vCofinsVal = new Vector();
  private JRadioGroup rgCofins = null;

  public FRPisCofins() {
    setTitulo("Pis e cofins");
    setAtribos(80,30,500,350);

    GregorianCalendar cal = new GregorianCalendar();
    cal.add(Calendar.DATE,-30);
    txtDataini.setVlrDate(cal.getTime());
    cal.add(Calendar.DATE,30);
    txtDatafim.setVlrDate(cal.getTime());
    txtDataini.setRequerido(true);    
    txtDatafim.setRequerido(true);

    vPisLab.addElement("Tributado");
    vPisLab.addElement("Isento");
    vPisLab.addElement("Sub. Trib.");
    vPisLab.addElement("Não Somar");
    vPisVal.addElement("T");
    vPisVal.addElement("I");
    vPisVal.addElement("S");
    vPisVal.addElement("N");
    
    rgPis = new JRadioGroup(1,4,vPisLab,vPisVal);

    vCofinsLab.addElement("Tributado");
    vCofinsLab.addElement("Isento");
    vCofinsLab.addElement("Sub. Trib.");
    vCofinsLab.addElement("Não Somar");
    vCofinsVal.addElement("T");
    vCofinsVal.addElement("I");
    vCofinsVal.addElement("S");
    vCofinsVal.addElement("N");
    
    rgCofins = new JRadioGroup(1,4,vCofinsLab,vCofinsVal);

    adic(new JLabelPad("Período:"),7,0,250,20);
    adic(txtDataini,7,20,100,20);
    adic(txtDatafim,110,20,100,20);
    
    adic(new JLabelPad("Pis:"),7,40,250,20);
    adic(rgPis,7,60,420,30);
    adic(new JLabelPad("Cofins:"),7,90,250,20);
    adic(rgCofins,7,110,420,30);
    adic(cbSemMov,7,150,420,30);
    cbSemMov.setVlrString("S");
    
  }

  
  public void imprimir(boolean bVisualizar) {
  	String sPis = "";
  	String sCofins = "";
  	try {
  		if ( (txtDataini.getVlrString().length() < 10) || (txtDatafim.getVlrString().length() < 10)) {
  			Funcoes.mensagemInforma(this,"Período inválido!");
  			return;
  		}
  		sPis = rgPis.getVlrString();
  		sCofins = rgCofins.getVlrString();
  		if ( ( sPis.equals("N") ) && ( sCofins.equals("N") ) ) {
  			Funcoes.mensagemInforma(this,"Selecione PIS ou COFINS!");
  			return;
  		}
	
  		impRel(bVisualizar,sPis,sCofins);
  	}
  	finally {
  		sPis = null;
  		sCofins = null;
  	}
	
  }
  

  private void impRel(boolean bVisualizar, String sPis, String sCofins) {
  	String sSql = "";
  	String sWhere = "";
  	String sFiltros1 = "";
  	String sSemMov = "";
  	ImprimeOS imp = null;
  	int linPag = 0;
  	PreparedStatement ps = null;
  	ResultSet rs = null;
  	double deVlrEntradas = 0;
  	double deVlrSaidas = 0;

  	try {
  		
  		imp = new ImprimeOS("",con);
  		linPag = imp.verifLinPag()-1;
  		
  		sFiltros1 = "";
  		
        if (!sPis.equals("N")) {
        	sFiltros1 = "( PIS ";
        	if (sPis.equals("T")) {
        		sFiltros1 += "TRIBUTADO )";
        	}
        	else if (sPis.equals("I")) {
        		sFiltros1 += "ISENTO )";
        	}
        	else if (sPis.equals("S")) {
        		sFiltros1 += "SUBSTITUICAO )";
        	}
        	sWhere = " AND ( CF.SITPISFISC='"+sPis+"'";
        }
        if (!sCofins.equals("N")) {
        	sFiltros1 += (sFiltros1.equals("")?"":" + ")+"( COFINS ";
        	if (sCofins.equals("T")) {
        		sFiltros1 += "TRIBUTADO )";
        	}
        	else if (sCofins.equals("I")) {
        		sFiltros1 += "ISENTO )";
        	}
        	else if (sCofins.equals("S")) {
        		sFiltros1 += "SUBSTITUICAO )";
        	}
        	sWhere += (sWhere.equals("")?" AND ( ":" OR ")+" CF.SITCOFINSFISC='"+sCofins+"'";
        }
        if (!sWhere.equals("")) {
        	sWhere += " ) ";
        }
        sSemMov = cbSemMov.getVlrString();
        
  		sSql = "SELECT P.DESCPROD, P.CODFISC,(SELECT SUM( VLRLIQITCOMPRA ) " +
  				"FROM CPITCOMPRA IC, CPCOMPRA C, EQTIPOMOV TM " +
  				"WHERE C.CODCOMPRA = IC.CODCOMPRA AND C.CODEMP = IC.CODEMP AND " +
  				"C.CODFILIAL = IC.CODFILIAL AND C.CODCOMPRA = IC.CODCOMPRA AND " +
  				"C.DTENTCOMPRA BETWEEN ? AND ? AND IC.CODEMPPD = P.CODEMP AND " +
  				"IC.CODFILIALPD = P.CODFILIAL AND IC.CODPROD = P.CODPROD AND " +
  				"C.CODEMPTM = TM.CODEMP AND C.CODFILIALTM = TM.CODFILIAL AND " +
  				"C.CODTIPOMOV = TM.CODTIPOMOV AND TM.TIPOMOV = 'CP' ) COMPRAS, " +
  				"( SELECT SUM( VLRLIQITVENDA ) FROM VDITVENDA IV, VDVENDA V, EQTIPOMOV TM " +
  				"WHERE V.CODVENDA = IV.CODVENDA AND V.CODEMP = IV.CODEMP AND " +
  				"V.CODFILIAL = IV.CODFILIAL AND V.TIPOVENDA = IV.TIPOVENDA AND " +
  				"V.CODVENDA = IV.CODVENDA AND V.DTEMITVENDA BETWEEN ? AND " +
  				"? AND IV.CODEMPPD = P.CODEMP AND IV.CODFILIALPD = P.CODFILIAL AND " +
  				"IV.CODPROD = P.CODPROD AND V.CODEMPTM = TM.CODEMP AND " +
  				"V.CODFILIALTM = TM.CODFILIAL AND V.CODTIPOMOV = TM.CODTIPOMOV AND " +
  				"TM.TIPOMOV = 'VD' AND ( SUBSTRING( V.STATUSVENDA FROM 1 FOR 1 ) != 'C' OR " +
  				"V.STATUSVENDA IS NULL ) ) VENDAS " +
  				"FROM EQPRODUTO P, LFCLFISCAL CF " +
  				"WHERE P.CODEMPFC=CF.CODEMP AND P.CODFILIALFC=CF.CODFILIAL AND " +
  				"P.CODFISC=CF.CODFISC AND P.CODEMP=? AND P.CODFILIAL=? " + sWhere +
  				"ORDER BY P.DESCPROD ";
  		
  		System.out.println(sSql);
  		
  		try {
  			ps = con.prepareStatement(sSql);
  			ps.setDate(1,Funcoes.dateToSQLDate(txtDataini.getVlrDate()));
  			ps.setDate(2,Funcoes.dateToSQLDate(txtDatafim.getVlrDate()));
  			ps.setDate(3,Funcoes.dateToSQLDate(txtDataini.getVlrDate()));
  			ps.setDate(4,Funcoes.dateToSQLDate(txtDatafim.getVlrDate()));
  			ps.setInt(5,Aplicativo.iCodEmp);
  			ps.setInt(6,ListaCampos.getMasterFilial("EQPRODUTO"));
  			
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
	  		  		imp.setTitulo("Relatório de entradas e saidas");
	  		  		imp.addSubTitulo("RELATORIO DE ENTRADAS E SAIDAS");
	  		    	imp.impCab(136, true);
	  		  		
	  				if (!sFiltros1.equals("")) {
	  					imp.say(imp.pRow()+0,0,""+imp.comprimido());
	 					imp.say(imp.pRow()+0,1,"|");
  	 					imp.say(imp.pRow()+0,68-(sFiltros1.length()/2),sFiltros1);
  	 					imp.say(imp.pRow()+0,136,"|");
  	  				}
  	  				imp.say(imp.pRow()+(!sFiltros1.equals("") ? 1 : 0),0,""+imp.comprimido());
  	  				imp.say(imp.pRow()+0,1,"|");
  	  				imp.say(imp.pRow()+0,49,"PERIODO DE: "+txtDataini.getVlrString()+" ATE: "+txtDatafim.getVlrString());
  	  				imp.say(imp.pRow()+0,136,"|");
  	  				imp.say(imp.pRow()+1,0,""+imp.comprimido());
					imp.say(imp.pRow()+0,1,"+"+Funcoes.replicate("-",133)+"+");
					imp.say(imp.pRow()+1,0,""+imp.comprimido());
					imp.say(imp.pRow()+0,1,"| DESCRICAO DO PRODUTO");
					imp.say(imp.pRow()+0,60,"| CODIGO NBM");
					imp.say(imp.pRow()+0,80,"| ENTRADAS");
					imp.say(imp.pRow()+0,100,"| SAIDAS");
					imp.say(imp.pRow()+0,136,"|");
					imp.say(imp.pRow()+1,0,""+imp.comprimido());
					imp.say(imp.pRow()+0,1,"+"+Funcoes.replicate("-",133)+"+");
					
				}
				if ( (sSemMov.equals("N")) || ( rs.getDouble(3)!=0 ) || ( rs.getDouble(4)!=0 ) )  {
					imp.say(imp.pRow()+1,0,""+imp.comprimido());
					imp.say(imp.pRow()+0,1,"| "+Funcoes.adicionaEspacos(rs.getString(1),50));
					imp.say(imp.pRow()+0,60,"| "+Funcoes.adicEspacosEsquerda(rs.getString(2),13));
					imp.say(imp.pRow()+0,80,"| "+Funcoes.strDecimalToStrCurrency(15,2,""+rs.getDouble(3)));
					imp.say(imp.pRow()+0,100,"| "+Funcoes.strDecimalToStrCurrency(15,2,""+rs.getDouble(4)));
					imp.say(imp.pRow()+0,136,"|");
					deVlrEntradas += rs.getDouble(3);
					deVlrSaidas += rs.getDouble(4);
				}
  				
  			}
  			
  			rs.close();
  			ps.close();
  			if (!con.getAutoCommit())
  				con.commit();

  			// Fim da impressão do total por setor
  			
  			imp.say(imp.pRow()+1,0,""+imp.comprimido());
  			imp.say(imp.pRow()+0,1,"+"+Funcoes.replicate("-",133)+"+");
  			imp.say(imp.pRow()+1,0,""+imp.comprimido());
  			imp.say(imp.pRow()+0,1,"| TOTAL");
 			imp.say(imp.pRow()+0,80,"| "+Funcoes.strDecimalToStrCurrency(10,2,deVlrEntradas+""));
  			imp.say(imp.pRow()+0,100,"| "+Funcoes.strDecimalToStrCurrency(15,2,deVlrSaidas+""));
  			imp.say(imp.pRow()+0,136,"|");
            			
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
  		sWhere = null;
  		sSql = null;
  		sFiltros1 = null;
  		sPis = null;
  		sCofins = null;
  		imp = null;
  		ps = null;
  		rs = null;
  		deVlrEntradas = 0;
  		deVlrSaidas = 0;
  	}
  	
  }
    
}
