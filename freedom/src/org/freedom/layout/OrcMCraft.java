/**
 * @version 14/01/2004<BR>
 * @author Setpoint Informática Ltda./Marco Antonio Sanchez <BR>
 *iPos
 * Projeto: Freedom <BR>UmontaCab
 * Pacote: layout <BR>
 * Classe: @(#)OrcMCraft.java <BR>
 * 
 * Este programa é licenciado de acordo com a LPG-PC (Licença Pública Geral para Programas de Computador), <BR>
 * versão 2.1.0 ou qualquer versão posterior. <BR>
 * A LPG-PC deve acompanhar todas PUBLICAÇÕES, DISTRIBUIÇÕES e REPRODUÇÕES deste Programa. <BR>
 * Caso uma cópia da LPG-PC não esteja disponível junto com este Programa, você pode contatar <BR>
 * o LICENCIADOR ou então pegar uma cópia em: <BR>
 * Licença: http://www.lpg.adv.br/licencas/lpgpc.rtf <BR>
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa é preciso estar <BR>
 * de acordo com os termos da LPG-PC <BR> <BR>m
 *
 * Orcamento padronizado para Modelcraft...
 */

package org.freedom.layout;
import java.awt.Font;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;

import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
public class OrcMCraft extends LeiauteGR {
	private Connection con = null;
	private Font fnTitulo = new Font("Times New Roman",Font.BOLD,15);
	private Font fnCabCliIta = new Font("Times New Roman",Font.ITALIC,14);
	Vector vParamOrc = new Vector();
	final int iPosIniItens = 400;
	final int iPosMaxItens = 740;
	final int iSaltoProd = 13;
	int iYPosProd=0;
	int iYObsVal=0;
	BigDecimal bigTot = new BigDecimal(0);
	String sDescPlan;
	public void montaG() {
		montaRel();
	}

	private int impLabelSilabas(String sTexto, int iSalto,int iMargem, int iLargura, int iY, ResultSet rs) {	
		double iPixels = getFontMetrics(fnCabCliIta).stringWidth(sTexto);        
		double iNLinhas = iPixels/iLargura;
		int iNCaracteres = Funcoes.tiraChar(sTexto,"\n").length();
		int iNCaracPorLinha = (int) (iNCaracteres/iNLinhas);  		
        Vector vTextoSilabas = Funcoes.strToVectorSilabas(sTexto,iNCaracPorLinha);
		for (int i=0;vTextoSilabas.size()>i;i++) {

			if (iY > iPosMaxItens && rs != null) {
				iYPosProd = iY = iPosIniItens-300; 
				termPagina();
			}

			setFonte(fnCabCliIta);			
			
			drawTexto(vTextoSilabas.elementAt(i).toString(),iMargem,iY);	  	
			iY+=iSalto;
		}	  
		return iY;
	}
	
	private void montaTot(ResultSet rs) {
   	  int iY=iYPosProd+10;
 	  try {
	    setFont(fnCabCliIta);
 	    	    
	    String sValLiq = Funcoes.strDecimalToStrCurrency(2,bigTot.toString()); 
	    drawTexto("Total liq.:",340,iY);
	    drawTexto(sValLiq,475,iY,getFontMetrics(fnCabCliIta).stringWidth(sValLiq),AL_DIR);
	    bigTot = new BigDecimal(0);
	    
	    iY+=30;
	    setFonte(fnCabCliIta);
	    
	    drawTexto("Prazo de Entrega:",80,iY);

	    setFonte(fnCabCliIta);
	  
	    if (rs.getString("PRAZOENTORC")==null)					  
	  	  drawTexto("À Combinar.",230,iY);
	    else
	  	  drawTexto(rs.getString("PRAZOENTORC")+" dias.",230,iY);
	  
	    iY+=25;

	    drawTexto("Pagamento.:",80,iY);
	  
	    iY = impLabelSilabas(sDescPlan,15,230,230,iY,rs);   	
	              	  
	    iY+=20;
	    
	    iY = impLabelSilabas("    A validade do orçamento é de 15 dias.Após esta data, os preços e " +
	    		"demais condições, ficarão sujeitos a alterações, por ocasião da confirmação " +
	    		"do pedido.",15,76,500,iY,rs);
	    
	    iY+=40;
	  
	    iY = impLabelSilabas("Atenciosamente,",15,260,200,iY,rs);
	   
	    iY+=40;
	  
	    setFonte(fnCabCliIta);
	    drawLinha(230,iY,405,iY);
	    drawTexto(rs.getString("NOMEVEND"),264,iY+13);
	  
	  }			
	  catch(SQLException err) {
		Funcoes.mensagemErro(this,"Erro ao montar final do orçamento!!!\n"+err.getMessage());
  		err.printStackTrace();
      }
	}
	private void montaRel() {
		setMargemPdf(5,5);
		int iCodOrc = Integer.parseInt(vParamOrc.elementAt(0).toString());
	    iYPosProd = iPosIniItens;
	    imprimeRodape(false);
		try {
		  String sSQLCab = "SELECT "+
		  			  "(SELECT COUNT(IO.CODITORC) FROM VDITORCAMENTO IO WHERE IO.CODEMP=O.CODEMP" +
					  " AND IO.CODFILIAL=O.CODFILIAL AND IO.CODORC=O.CODORC),"+
					  "O.CODORC,O.OBSORC,O.VLRLIQORC,O.VLRDESCORC, O.VLRDESCITORC,O.VLRPRODORC,F.CIDFILIAL," +
					  "C.RAZCLI,C.CONTCLI,C.FONECLI,C.FAXCLI,VD.NOMEVEND,O.CODPLANOPAG,O.CODEMPPG," +
					  "O.CODFILIALPG,O.PRAZOENTORC " +
					  " FROM VDORCAMENTO O,SGFILIAL F, VDCLIENTE C, VDVENDEDOR VD,FNPLANOPAG PG"+
					  " WHERE C.CODEMP=O.CODEMPCL AND C.CODFILIAL=O.CODFILIALCL AND C.CODCLI=O.CODCLI"+
			          " AND O.CODVEND=VD.CODVEND AND O.CODFILIALVD=VD.CODFILIAL AND O.CODEMPVD=VD.CODEMP"+
					  " AND F.CODEMP=O.CODEMP AND F.CODFILIAL=O.CODFILIAL"+
					  " AND O.TIPOORC = 'O' AND O.CODORC=? AND O.CODEMP=? AND O.CODFILIAL=? AND " +
					  " O.CODPLANOPAG=PG.CODPLANOPAG AND O.CODEMPPG=PG.CODEMP AND O.CODFILIALPG=PG.CODFILIAL";

		  PreparedStatement psCab = con.prepareStatement(sSQLCab);
		  psCab.setInt(1,iCodOrc);
		  psCab.setInt(2,Aplicativo.iCodEmp);
		  psCab.setInt(3,ListaCampos.getMasterFilial("VDORCAMENTO"));
		  ResultSet rsCab = psCab.executeQuery();
		  if (!rsCab.next())
		    return;
		  montaCab(rsCab);
		  
		  int iCodPg = rsCab.getInt("CODPLANOPAG");
		  int iCodEmpPg = rsCab.getInt("CODEMPPG");
		  int iCodFilialPg = rsCab.getInt("CODFILIALPG");

		  String sSQL = "SELECT DESCPARCPAG FROM FNPARCPAG WHERE CODPLANOPAG=? AND CODEMP=? AND CODFILIAL=?";

		  PreparedStatement ps = con.prepareStatement(sSQL);
		  ps.setInt(1,iCodPg);
		  ps.setInt(2,iCodEmpPg);
		  ps.setInt(3,iCodFilialPg);
		  ResultSet rs = ps.executeQuery();
		  sDescPlan = "";

		  while (rs.next()) {
		    sDescPlan += rs.getString(1)+"\n";			  			  	
		  }
		  rs.close();
		  ps.close();
			  
		  sSQL = "SELECT "+
		         "IT.VLRPRODITORC/IT.QTDITORC,IT.CODPROD,"+
		         " P.REFPROD,P.DESCPROD,IT.QTDITORC,IT.VLRDESCITORC," +
		  	     "IT.VLRLIQITORC,IT.OBSITORC" +
		         " FROM VDITORCAMENTO IT, EQPRODUTO P WHERE"+
				 " P.CODPROD=IT.CODPROD AND P.CODEMP=IT.CODEMPPD AND P.CODFILIAL=IT.CODFILIALPD"+
				 " AND IT.TIPOORC = 'O' AND IT.CODORC=? AND IT.CODEMP=? AND IT.CODFILIAL=? " +
				 " ORDER BY IT.CODORC,IT.CODITORC";

		  ps = con.prepareStatement(sSQL);
		  ps.setInt(1,iCodOrc);
		  ps.setInt(2,Aplicativo.iCodEmp);
		  ps.setInt(3,ListaCampos.getMasterFilial("VDORCAMENTO"));
		  rs = ps.executeQuery();
		  for (int i=1;(rs.next());i++) {
	  		int iY2 = iYPosProd;
	  		setFonte(fnCabCliIta);
	  		String sVal = Funcoes.strDecimalToStrCurrency(2,rs.getString("VLRLIQITORC"));
	  		drawTexto(sVal,470,iYPosProd+5,getFontMetrics(fnCabCliIta).stringWidth(sVal),AL_DIR);
	  		bigTot = bigTot.add(new BigDecimal(rs.getString("VLRLIQITORC")));
	  		if (rs.getString("ObsItOrc")!=null) {		   
		  		setFonte(fnCabCliIta);    	
		  		iY2 = impLabelSilabas(rs.getString("ObsItOrc"),iSaltoProd,60,350,iYPosProd+3,rsCab)-5;
		  	}
		  	else {
		  		setFonte(fnCabCliIta);
		  		iY2 = impLabelSilabas(rs.getString("DescProd").trim(),iSaltoProd,60,350,iYPosProd,rsCab);
		  		setFonte(fnCabCliIta);
		  	}
		  	
		  	iYPosProd=iY2;
		  	
		    iYPosProd += 15;		    	

			if (i>=rsCab.getInt(1)) {
//			    Por ser o ultimo item termina tudo!!!
			    montaTot(rsCab);
			    termPagina();
		    }
	        
			else if (iYPosProd > iPosMaxItens) {
//				Se estourou o limite de linhas, pula pra outra pagina:
				termPagina();
				iYPosProd = iPosIniItens-300; 
			}
			
		  }
		  
		rsCab.close();
		psCab.close();
		rs.close();
		ps.close();
		}
		catch(SQLException err) {
			Funcoes.mensagemErro(this,"Erro ao montar o cabeçalho do relatório!!!\n"+err.getMessage());
			err.printStackTrace();
		}

	    finaliza();
	}
	private void montaCab(ResultSet rs) {
	  try {		  

//		Informações do paciente
		setFonte(fnTitulo);
		drawTexto("ORÇAMENTO",200,120);
				
		setFonte(fnCabCliIta); 
		drawTexto(rs.getString("CidFilial").trim()+", "+Funcoes.dateToStrExtenso(new Date()),60,180);
//      Linha 1

        setFonte(fnTitulo);
		drawTexto(""+rs.getInt("CodOrc"),440,200);

		setFonte(fnCabCliIta);
		drawTexto("À",60,220);
		setFonte(fnCabCliIta);
		drawTexto(rs.getString("RazCli") !=null ? rs.getString("RazCli").trim() : "",60,235);

//		Linha 2		
		setFonte(fnCabCliIta);
		drawTexto("A/C:",60,250);
		setFonte(fnCabCliIta);
		drawTexto("Sr(a) : "+rs.getString("ContCli"),101,250);
		
		setFonte(fnCabCliIta);
		drawTexto("Fone : ",60,265);	
		drawTexto(Funcoes.setMascara(rs.getString("FoneCli"),"(####)####-####")+
				"    Fax.:  "+Funcoes.setMascara(rs.getString("FaxCli"),"####-####") ,101,265);
						
        setFonte(fnCabCliIta);
        drawTexto("Prezado(a) Senhor(a);",60,315);
        
        setFonte(fnCabCliIta);
        
        impLabelSilabas("Conforme a solitação de V.Sa., temos o máximo prazer de passar " +
        		"às suas mãos a nossa oferta, valendo as condições gerais de venda e " +
				"fornecimento conforme abaixo descriminados",15,60,500,345,rs);
        		
	  }			
	  catch(SQLException err) {
		Funcoes.mensagemErro(this,"Erro ao montar dados do cliente!!!\n"+err.getMessage());
		err.printStackTrace();
	  }
	}
	
	public void setParam(Vector vParam) {
		vParamOrc = vParam;
	}

	public void setConexao(Connection cn) {
		con = cn;
	}
}