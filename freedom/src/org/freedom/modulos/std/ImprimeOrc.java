 /**
 * @version 02/11/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)ImprimeOrc.java <BR>
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
 * Classe para impressão de orçamento padrão módulo standard. 
 * 
 */

package org.freedom.modulos.std;
import java.awt.BasicStroke;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;

import org.freedom.componentes.ImprimeLayout;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;


public class ImprimeOrc extends ImprimeLayout {
	private static final long serialVersionUID = 1L;

	private Connection con = null;
	private int iCodOrc = 0;
	private Font fnTopEmp = new Font("Arial",Font.BOLD,9);
	private Font fnCabEmp = new Font("Arial",Font.PLAIN,8);
	private Font fnCabEmpNeg = new Font("Arial",Font.BOLD,8);
	private Font fnCabCli = new Font("Arial",Font.PLAIN,8);
	private Font fnCabCliNeg = new Font("Arial",Font.BOLD,8);
	private boolean bImpCab = true;
	private int iResto = 0;
	public ImprimeOrc(int iCodO) {
		iCodOrc = iCodO;
	}
	public void montaG() {
		montaRel();
	}

	private int impLabel(String sTexto, int iSalto,int iMargem, int iLargura, int iY) {	
	  double iPixels = getFontMetrics(fnCabCli).stringWidth(sTexto);        
	  double iNLinhas = iPixels/iLargura;
	  int iNCaracteres = Funcoes.tiraChar(sTexto,"\n").length();
	  int iNCaracPorLinha = (int) (iNCaracteres/iNLinhas);  		
	  Vector vTextoSilabas = Funcoes.strToVectorSilabas(sTexto,iNCaracPorLinha);
	   
	  for (int i=0;vTextoSilabas.size()>i;i++) {
	    drawTexto(vTextoSilabas.elementAt(i).toString(),iMargem,iY);	  	
        iY+=iSalto;
	  }	  
	  return iY;
    }
	
	private void montaRel() {

		try {
		  String sSQL = "SELECT "+
		                "(SELECT COUNT(IO.CODITORC) FROM VDITORCAMENTO IO WHERE IO.CODEMP=O.CODEMP" +
		                " AND IO.CODFILIAL=O.CODFILIAL AND IO.CODORC=O.CODORC),"+
						"IT.VLRPRODITORC/IT.QTDITORC,"+
						(comRef()[0] ? "IT.REFPROD," : "IT.CODPROD,")+
                        " O.VLRDESCITORC, O.CODORC,O.DTORC,O.DTVENCORC," +
		  	            " O.OBSORC,O.VLRLIQORC,P.CODBARPROD, P.DESCPROD,IT.QTDITORC,IT.VLRDESCITORC,IT.VLRLIQITORC," +
		  	            " O.VLRDESCORC,  O.VLRADICORC, O.VLRPRODORC, F.CIDFILIAL, CL.CODCLI," +
		  	            " CL.RAZCLI,CL.ENDCLI,CL.NUMCLI,CL.BAIRCLI,CL.CIDCLI,CL.UFCLI,CL.DDDCLI,CL.FONECLI,IT.OBSITORC,PL.DESCPLANOPAG,VD.NOMEVEND,CL.CONTCLI,O.PRAZOENTORC" +
		  	            " FROM VDORCAMENTO O, VDITORCAMENTO IT, EQPRODUTO P, SGFILIAL F, VDCLIENTE CL,FNPLANOPAG PL,VDVENDEDOR VD"+
						" WHERE IT.CODORC=O.CODORC AND IT.CODEMP=O.CODEMP AND IT.CODFILIAL=O.CODFILIAL AND IT.TIPOORC=O.TIPOORC"+
						" AND P.CODPROD=IT.CODPROD AND P.CODEMP=IT.CODEMPPD AND P.CODFILIAL=IT.CODFILIALPD"+
						" AND F.CODEMP=O.CODEMP AND F.CODFILIAL=O.CODFILIAL"+
						" AND O.CODPLANOPAG=PL.CODPLANOPAG AND O.CODFILIALPG=PL.CODFILIAL AND O.CODEMP=PL.CODEMP"+
				        " AND O.CODVEND=VD.CODVEND AND O.CODFILIALVD=VD.CODFILIAL AND O.CODEMPVD=VD.CODEMP"+	
						" AND O.TIPOORC = 'O' AND O.CODORC=? AND O.CODEMP=? AND O.CODFILIAL=?"+
						" AND CL.CODEMP=O.CODEMP AND CL.CODFILIAL=O.CODFILIAL AND CL.CODCLI=O.CODCLI " +
						" ORDER BY IT.CODORC,IT.CODITORC";
		  
		  //System.out.println("AQUI "+sSQL);			
		  PreparedStatement ps = con.prepareStatement(sSQL);
		  ps.setInt(1,iCodOrc);
		  ps.setInt(2,Aplicativo.iCodEmp);
		  ps.setInt(3,ListaCampos.getMasterFilial("VDORCAMENTO"));
		  ResultSet rs = ps.executeQuery();
		  int iY = 245;
		  for (int i=1;rs.next();i++) {
			if (bImpCab) { 
			  montaCab(rs);
			  iY = 220+iResto;
			  bImpCab = false;
			  setFonte(fnCabEmp);
			}
			 
			drawTexto(rs.getString(3),5,iY); 
			drawTexto(Funcoes.setMascara(rs.getString("CODBARPROD") !=null ? rs.getString("CODBARPROD").trim() : "","##.###.##-######"),55,iY);
			                    								            							
			String sVal0 = Funcoes.strDecimalToStrCurrency(2,rs.getInt("QtdItOrc")+""); 
			String sVal1 = Funcoes.strDecimalToStrCurrency(2,rs.getString(2)); 
			String sVal2 = Funcoes.strDecimalToStrCurrency(2,rs.getString("VlrDescItOrc")); 
			String sVal3 = Funcoes.strDecimalToStrCurrency(2,rs.getString("VlrLiqItOrc")); 
			
			drawTexto(sVal0,350,iY,getFontMetrics(fnCabEmp).stringWidth(sVal0),AL_DIR); 
			drawTexto(sVal1,400,iY,getFontMetrics(fnCabEmp).stringWidth(sVal1),AL_DIR); 
			drawTexto(sVal2,470,iY,getFontMetrics(fnCabEmp).stringWidth(sVal2),AL_DIR); 
			drawTexto(sVal3,540,iY,getFontMetrics(fnCabEmp).stringWidth(sVal3),AL_DIR); 

			int iY2 = iY;
			if (rs.getString("ObsItOrc")!=null) {		   
			   setFonte(fnCabCliNeg);
			   iY = impLabel(rs.getString("DescProd").trim(),10,110,200,iY);
			   setFonte(fnCabCli);    	
			   iY2 = impLabel(rs.getString("ObsItOrc"),10,110,200,iY+3)-5;
			}
			else {
				setFonte(fnCabCliNeg);
				iY2 = impLabel(rs.getString("DescProd").trim(),10,110,200,iY);
				setFonte(fnCabCli);
			}
			
			iY=iY2+15;
			
			if (i>=rs.getInt(1)) {
//			  Por ser o ultimo item termina tudo!!!
			  montaTot(rs,true);
			  setBordaRel();
			  termPagina();
			}
			else if (iY > 605) {
//				Se estourou o limite de linhas, pula pra outra pagina:
				iY = 230; 
				montaTot(rs,false);
				setBordaRel();
				termPagina();
				bImpCab = true;
			}
		  }
		  
//Fecha a conta e passa a regua:
		  finaliza();   
		  rs.close();
		  ps.close();
		}
		catch(SQLException err) {
			Funcoes.mensagemErro(this,"Erro ao montar o relatório!!!\n"+err.getMessage(),true,con,err);
		}
	}
	private void montaCab(ResultSet rs) {
	  int iY = 35;
	  try {
	  	montaCabEmp(con);
	  	
		drawLinha(0,iY,0,0,AL_LL);
		
		iY += 20;

		setFonte(fnTopEmp);
		drawTexto("ORÇAMENTO",0,iY,getFontMetrics(fnCabEmp).stringWidth("ORÇAMENTO"),AL_CEN);
		setFonte(fnCabEmpNeg);

		iY += 10;
		
		drawLinha(0,iY,0,0,AL_LL);
		
		iY += 10;

		setFonte(fnCabEmp);
		drawTexto("Orçamento:   ",5,iY); 
		setFonte(fnCabEmpNeg);
		drawTexto(Funcoes.strZero(""+iCodOrc,8),70,iY);
		setFonte(fnCabEmp);
		drawTexto("Data de emissão: ",200,iY); 
		drawTexto(Funcoes.sqlDateToStrDate(rs.getDate("DtOrc")),300,iY);
		
		setFonte(fnCabEmp);
		drawTexto("Validade: ",380,iY); 
		if (comRef()[1])
		  drawTexto(Funcoes.sqlDateToStrDate(rs.getDate("DtVencOrc")),480,iY);
		else {
		  Date dtOrc = rs.getDate("DtOrc");
          Date dtVal = rs.getDate("DtVencOrc");
          long nDias = Funcoes.getNumDiasAbs(dtOrc,dtVal);            
          String sDias = "";
          if (nDias!=1)
          	sDias = " dias.";
          else {
          	sDias = " dia.";
          }	          	
          drawTexto(""+nDias+sDias,480,iY);
		}
		  
		iY += 5;

		drawLinha(0,iY,0,0,AL_LL);
		
		iY += 10;
		
		setFonte(fnCabCliNeg);
		drawTexto("Cliente:",5,iY);
		setFonte(fnCabCli);
		drawTexto(rs.getString("CodCli")+" - "+rs.getString("RazCli").trim(),80,iY);
		setFonte(fnCabCliNeg);
		drawTexto("A/C Sr(a):",378,iY);
		setFonte(fnCabCli);
		drawTexto(rs.getString("ContCli"),440,iY);
		
		
		iY += 15;
		setFonte(fnCabCliNeg);
		drawTexto("Cond.Pagto.:",378,iY);
		setFonte(fnCabCli);
		
		impLabel(rs.getString("DescPlanoPag"),10,452,110,iY);
		
		//drawTexto("A/C Sr(a):",378,iY);
		//setFonte(fnCabCli);
		//drawTexto(rs.getString("ContCli"),440,iY);
					
		setFonte(fnCabCliNeg);
		drawTexto("Cidade:",5,iY);
		setFonte(fnCabCli);
		drawTexto(rs.getString("CidCli") != null ? rs.getString("CidCli").trim()+" / "+rs.getString("UFCli") : "",80,iY);
		
		System.out.print("y no cliente"+iY);
		iY += 15;
				
		setFonte(fnCabCliNeg);
		drawTexto("Endereço:",5,iY);
		setFonte(fnCabCli);
		drawTexto(rs.getString("EndCli") != null ? (rs.getString("EndCli").trim()+", "+rs.getInt("NumCli")) : "",80,iY);
			
		iY += 15;

		setFonte(fnCabCliNeg);
		drawTexto("Bairro:",5,iY);
		setFonte(fnCabCli);
		drawTexto(rs.getString("BairCli"),80,iY);
			
		iY += 15;

		setFonte(fnCabCliNeg);
		drawTexto("Telefone:",5,iY);
		setFonte(fnCabCli);
		drawTexto((rs.getString("DDDCli")!= null?"("+rs.getString("DDDCli")+")":"")+(rs.getString("FoneCli") != null ? Funcoes.setMascara(rs.getString("FoneCli").trim(),"####-####") : ""),80,iY);
		
		iY += 15;

		setFonte(fnCabCliNeg);
		drawTexto("Observação:",5,iY);
		setFonte(fnCabCli);
		String[] sObs = Funcoes.strToStrArray(rs.getString("ObsOrc"),2);
		drawTexto(sObs[0],80,iY);
		
		iY += 15;
		
		drawTexto(sObs[1],80,iY);
		setFonte(fnCabCliNeg);
		
		drawTexto("Prazo de Entrega:",378,120);
       
		setFonte(fnCabCli); 
		if (rs.getString("PRAZOENTORC")==null)					  
			drawTexto("À Combinar.",470,120);
		else
			drawTexto(rs.getString("PRAZOENTORC")+" dias.",470,120);
	    iY += 5;

		setPincel(new BasicStroke(2));
		drawLinha(0,iY,0,0,AL_LL);
		setPincel(new BasicStroke(1));
		drawLinha(50,iY,50,610);
		drawLinha(108,iY,108,610);
		drawLinha(328,iY,328,610);			
		drawLinha(365,iY,365,610);			
		drawLinha(420,iY,420,610);			
		drawLinha(490,iY,490,610);			

		iY += 15;

		setFonte(fnCabCliNeg);
		drawTexto("Cod.",5,iY);
		drawTexto("Cod.Bar.",55,iY); 
		drawTexto("Descrição",110,iY);
		drawTexto("Qtd.",332,iY);
		drawTexto("Vlr. Unit.",373,iY);
		drawTexto("Vlr. Desc.",430,iY);
		drawTexto("Vlr. Total.",498,iY);
			
		iY += 5;

		drawLinha(0,iY,0,0,AL_LL);

		iY += 405;  // tamanho reservado para itens.

		drawLinha(0,iY,0,0,AL_LL);
	  }
	  catch (SQLException err) {
		Funcoes.mensagemErro(this,"Erro ao montar o cabeçalho do relatório!\n"+err.getMessage(),true,con,err);
	  }
	}
	private void montaTot(ResultSet rs, boolean bValido) {
 	  int iY = 610;
 	  
 	  setFont(fnCabEmp);
 	  
	  try {
		  if (bValido) {
				double dValAdic = rs.getDouble("VlrAdicOrc");
				double dValDesc = rs.getDouble("VlrDescOrc");
				dValDesc += rs.getDouble(4); //Desconto por item.
				if (dValDesc > 0 || dValAdic > 0) { 
				  String sValParc = Funcoes.strDecimalToStrCurrency(2,rs.getString("VlrProdOrc")); 
				  drawTexto("Total parc.:",410,iY+10);
				  drawRetangulo(490,iY,0,15,AL_BDIR);
				  drawTexto(sValParc,545,iY+10,getFontMetrics(fnCabEmp).stringWidth(sValParc),AL_DIR);
				  iY+=15; 
				  if (dValDesc > 0) { 
					String sValDesc = Funcoes.strDecimalToStrCurrency(2,dValDesc+""); 
					drawTexto("Desconto:",410,iY+10);
					drawRetangulo(490,iY,0,15,AL_BDIR);
					drawTexto(sValDesc,545,iY+10,getFontMetrics(fnCabEmp).stringWidth(sValDesc),AL_DIR);
					iY+=15; 
				  }
				  if (dValAdic > 0) { 
					String sValAdic = Funcoes.strDecimalToStrCurrency(2,dValAdic+""); 
					drawTexto("Adicional:",410,iY+10);
					drawRetangulo(490,iY,0,15,AL_BDIR);
					drawTexto(sValAdic,545,iY+10,getFontMetrics(fnCabEmp).stringWidth(sValAdic),AL_DIR);
					iY+=15; 
				  }
				}
				String sValLiq = Funcoes.strDecimalToStrCurrency(2,rs.getString("VlrLiqOrc")); 
				drawTexto("Total liq.:",420,iY+10);
				drawRetangulo(490,iY,0,15,AL_BDIR);
				drawTexto(sValLiq,540,iY+10,getFontMetrics(fnCabEmp).stringWidth(sValLiq),AL_DIR);
		  }
		  else {//Caso ainda não tenha terminado o relatório não imprime valores e coloca 'x':
			String sValLiq = "xxxxxxxxxx"; 
			drawTexto("Total liq.:",410,iY+10);
			drawRetangulo(490,iY,0,15,AL_BDIR);
			drawTexto(sValLiq,550,iY+10,getFontMetrics(fnCabEmp).stringWidth(sValLiq),AL_DIR);
		  }

//Linha de assinatura:		  
		  
		  drawTexto(rs.getString("CidFilial").trim()+", "+Funcoes.dateToStrExtenso(new Date()),70,650);
		  drawLinha(70,690,200,690);
		  setFonte(fnCabCliNeg);
		  drawTexto(rs.getString("NomeVend"),90,700);
		  
		  
	  }
	  catch (SQLException err) {
		Funcoes.mensagemErro(this,"Erro ao montar totalizadores do relatório!\n"+err.getMessage(),true,con,err);
	  }
	}
	private boolean[] comRef() {
	  boolean bRetorno[] = {false,false};
	  String sSQL = "SELECT USAREFPROD,TIPOVALIDORC FROM SGPREFERE1 WHERE CODEMP=? AND CODFILIAL=?";
	  PreparedStatement ps = null;
	  ResultSet rs = null;
	  try {
		ps = con.prepareStatement(sSQL);
		ps.setInt(1,Aplicativo.iCodEmp);
		ps.setInt(2,ListaCampos.getMasterFilial("SGPREFERE1"));
		rs = ps.executeQuery();
		if (rs.next()) {
		  if (rs.getString("UsaRefProd").trim().equals("S"))
			bRetorno[0] = true;
		  if (rs.getString("tipovalidorc").trim().equals("D")) {
		  	bRetorno[1] =true;		  	
		  }
		}
	    rs.close();
		ps.close();
	  }
	  catch (SQLException err) {
		  Funcoes.mensagemErro(this,"Erro ao carregar a tabela PREFERE1!\n"+err.getMessage(),true,con,err);
	  }
	  return bRetorno;
	}
	public void setConexao(Connection cn) {
		con = cn;
	}
}