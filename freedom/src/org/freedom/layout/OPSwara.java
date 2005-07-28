/** @version 23/04/2004<BR>
 * @author Setpoint Informática Ltda./Anderson Sanchez <BR>
 *iPos
 * Projeto: Freedom <BR>
 * Pacote: leiautes <BR>
 * Classe: @(#)OPSwara.java <BR>
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
 * Layout de Ordem de produção personalizada para empresa Swara...
 */

package org.freedom.layout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;

import javax.swing.ImageIcon;

import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;

import com.lowagie.text.pdf.Barcode128;

public class OPSwara extends LeiauteGR {
	private static final long serialVersionUID = 1L;
	private Connection con = null;
	private Font fnTitulo = new Font("Times New Roman",Font.BOLD,14);
	private Font fnArial9 = new Font("Arial",Font.PLAIN,9);
	private Font fnInstrucoes = new Font("Arial",Font.PLAIN,6);
	private Font fnArial9N = new Font("Arial",Font.BOLD,9);
	Vector vParamOP = new Vector();
	final int iPosIniItens = 400;
	final int iPosMaxItens = 740;
	int iYPosProd=0;
	int iY = 100;
	Vector vItens = new Vector();
	Vector vItem = new Vector();
	int iCodOP = 0;
	String sDescProd = "";
	String sLote = "";
	String sQtd = "";
	Double dbQtd = new Double(1);
	String sDtFabrica = "";
	String sDtValidade = "";
  
	public void montaG() {
	    impRaz(false);
		montaRel();
	}
	public void setParam(Vector vParam) {
		vParamOP = vParam;
	}
	
	private void montaRel() {
		setMargemPdf(5,5);
		iCodOP = Integer.parseInt(vParamOP.elementAt(0).toString());
	    iYPosProd = iPosIniItens;
		try {
		  String sSQL = "SELECT ITOP.CODOP,ITOP.SEQITOP,OP.DTEMITOP,OP.CODPROD,(SELECT PROD2.DESCPROD FROM EQPRODUTO PROD2 WHERE PROD2.CODPROD=OP.CODPROD  AND PROD2.CODEMP=OP.CODEMPPD  AND PROD2.CODFILIAL=OP.CODFILIALPD),"+
			"EST.DESCEST,EST.QTDEST,OP.DTFABROP,OP.QTDPREVPRODOP,DTVALIDPDOP,OP.DTINS,ITOP.CODPROD,PROD.DESCPROD,UNID.DESCUNID,ITOP.CODLOTE,ITOP.QTDITOP,OP.QTDPREVPRODOP,ITOP.CODFASE,OP.CODLOTE "+
			"FROM PPESTRUTURA EST,PPOP OP, PPITOP ITOP, EQUNIDADE UNID, EQPRODUTO PROD "+
			"WHERE EST.CODPROD=OP.CODPROD AND ITOP.CODOP=OP.CODOP AND UNID.CODUNID=PROD.CODUNID "+
			"AND PROD.CODPROD = ITOP.CODPROD AND OP.CODOP=? AND OP.CODEMP=? AND OP.CODFILIAL=?";

		  PreparedStatement ps = con.prepareStatement(sSQL);
		  
		  System.out.println("SQL:"+sSQL);

  		  ps.setInt(1,iCodOP);
   	      ps.setInt(2,Aplicativo.iCodEmp);
  	      ps.setInt(3,ListaCampos.getMasterFilial("PPOP"));
		  
		  ResultSet rs = ps.executeQuery();
		  		   
		  while (rs.next()) {
            vItem = new Vector();
            vItem.addElement((rs.getString(12)!=null?rs.getString(12):"")); //Código
            vItem.addElement((rs.getString(13)!=null?rs.getString(13):"")); //Descrição
            vItem.addElement((rs.getString(16)!=null?Funcoes.strDecimalToStrCurrency(3,rs.getString(16)):"0")); //Quantidade
            vItem.addElement((rs.getString(14)!=null?rs.getString(14):"")); //Unidade
            vItem.addElement((rs.getString(15)!=null?rs.getString(15):"")); //Lote
            vItem.addElement((rs.getString(18)!=null?rs.getString(18):"0")); //Fase            
            vItens.addElement(vItem.clone());
            System.out.println("Adicionou:"+rs.getString(13));
		  }

		  sDescProd  = (rs.getString(5)!=null?rs.getString(5).trim():"");
//		  sQtd       = (rs.getString(16)!=null?Funcoes.strDecimalToStrCurrency(3,rs.getString(16)):"");
//		  sQtd       = (rs.getString(9)!=null?Funcoes.strDecimalToStrCurrency(3,rs.getString(9)):"");
		  dbQtd      = (rs.getString(9)!=null?new Double(Funcoes.strDecimalToBigDecimal(3,rs.getString(9)).doubleValue()):dbQtd);
		  sQtd       = dbQtd.toString();
		  sDtFabrica = (rs.getDate(8) !=null ? Funcoes.sqlDateToStrDate(rs.getDate(8)) : ""); 
		  sDtValidade= (rs.getDate(10) !=null ? Funcoes.sqlDateToStrDate(rs.getDate(10)) : "");
		  sLote  = (rs.getString(19)!=null?rs.getString(19).trim():"");
		  montaCabEmp(con);
		  montaCab();		   	        		  

		  sSQL = "SELECT OPF.SEQOF, OPF.CODFASE,F.DESCFASE,F.TIPOFASE,OPF.TEMPOOF,OPF.CODRECP,REC.DESCRECP,EF.INSTRUCOES "+
		  		 "FROM PPOP OP, PPOPFASE OPF, PPFASE F, PPRECURSO REC,PPESTRUFASE EF WHERE "+
				 "F.CODFASE = OPF.CODFASE AND F.CODEMP = OPF.CODEMPFS  AND F.CODFILIAL = OPF.CODFILIALFS "+				 
				 "AND OP.CODEMP=OPF.CODEMP AND OP.CODFILIAL=OPF.CODFILIAL AND OP.CODOP=OPF.CODOP "+
				 "AND REC.CODRECP = OPF.CODRECP AND REC.CODEMP = OPF.CODEMPRP AND REC.CODFILIAL = OPF.CODFILIALRP "+
				 "AND OPF.CODOP=? AND OPF.CODEMP=? AND OPF.CODFILIAL=? " +
				 "AND EF.CODEMP=OPF.CODEMP AND EF.CODFILIAL=OPF.CODFILIAL AND EF.CODPROD=OP.CODPROD " +
				 "AND EF.SEQEF=OPF.SEQOF " +				 
				 "ORDER BY OPF.SEQOF";
		  		  
		  PreparedStatement psFases = con.prepareStatement(sSQL);
		  
  		  psFases.setInt(1,iCodOP);
   	      psFases.setInt(2,Aplicativo.iCodEmp);
  	      psFases.setInt(3,ListaCampos.getMasterFilial("PPOPFASE"));

  	      ResultSet rsFases = psFases.executeQuery();
              	      
  	      montaFases(rsFases);
  	      	      
		  rs.close();
		  ps.close();

          impAssinatura();
		  termPagina();
		  finaliza();
		}
		catch(SQLException err) {
			Funcoes.mensagemErro(this,"Erro ao montar o cabeçalho do relatório!!!\n"+err.getMessage());
			err.printStackTrace();
		}	         
	}	
	private void montaFases(ResultSet rsFases) {
		try {
			while (rsFases.next()) {
	              if (rsFases.getString(4).equals("EX")) {
                     impFaseEx(rsFases);	              	
	              }
	              else if (rsFases.getString(4).equals("CQ")) {
	                 impFaseCq(rsFases);
	              }
	              else if (rsFases.getString(4).equals("EB")) {
	              	 impFaseEB(rsFases);
	              }	              
				}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	private int impLabelSilabas(String sTexto, int iSalto,int iMargem, int iLargura, int iY, Font fonte) {	
		double iPixels = getFontMetrics(fonte).stringWidth(sTexto);        
		double iNLinhas = iPixels/iLargura;
		int iNCaracteres = Funcoes.tiraChar(sTexto,"\n").length();
		int iNCaracPorLinha = (int) (iNCaracteres/iNLinhas);  		
        Vector vTextoSilabas = Funcoes.strToVectorSilabas(sTexto,iNCaracPorLinha);
		for (int i=0;vTextoSilabas.size()>i;i++) {

			setFonte(fonte);			
			
			drawTexto(vTextoSilabas.elementAt(i).toString(),iMargem,iY);	  	
			iY+=iSalto;
		}	  
		return iY;
	}

	private void impFaseEx(ResultSet rsFases) {        
		try {
			int iSeqOf = rsFases.getInt(1);
			int iCodFaseF = rsFases.getInt(2);
		  	int iCodFaseI = 0;
		  	
            iY = iY+12;
		  	int iYIni = iY;	 
            int iInst = 0;
            if(rsFases.getString("INSTRUCOES")!=null) {  
                setFonte(fnArial9N);		    
                drawTexto("Instrução de preparo",250,iY-5);
                iInst = impLabelSilabas(rsFases.getString("INSTRUCOES").trim(),7,250,280,iY+2,fnInstrucoes);
            }

//		  	drawTexto("FASE: "+rsFases.getString(1).trim(),0,iY,50,AL_CEN);				
			
            setFonte(fnTitulo);
		  	drawTexto("FASE: "+rsFases.getString(1).trim(),10,iY);
		  	
            iY = iY+12;
 
            setFonte(fnArial9N);		    
            drawTexto("Recurso:",10,iY);
            setFonte(fnArial9);
            drawTexto(rsFases.getString(7),60,iY);
            iY = iY+12;
            setFonte(fnArial9N);
//            drawTexto("Tempo estimado(min.):",220,iY);
            drawTexto("Tempo estimado(min.):",10,iY);
            
            setFonte(fnArial9); 
            Double dbQtdEstr = new Double(rsFases.getFloat(5)/60);
            drawTexto((dbQtdEstr.floatValue())*(dbQtd.floatValue())+"",120,iY);

            iY = iY+10;
        
            if(iInst>iY){
                iY = iInst+2;
            }
            
    		drawLinha(5,iY,5,0,AL_CDIR);
            iY = iY+12;
            setFonte(fnArial9N);
            drawTexto("Cód.",10,iY);
            drawTexto("Descrição",50,iY); 
            drawTexto("Cód.Barras",210,iY);
            drawTexto("Qtd.",400,iY);
            drawTexto("Unidade",430,iY);
            drawTexto("Lote",500,iY);
    		drawLinha(5,iY+5,5,0,AL_CDIR);
            iY = iY + 15;
            setFonte(fnArial9);
            
            String sCod = "";
            String sDesc = "";
            String sQtd = "";
            String sUnid = "";
            String sLote = "";
                        
            for(int i=0;vItens.size()>i;i++) {
              sCod  = ((Vector) vItens.elementAt(i)).elementAt(0).toString();
              sDesc = ((Vector) vItens.elementAt(i)).elementAt(1).toString();
              sQtd  = ((Vector) vItens.elementAt(i)).elementAt(2).toString();
              sUnid = ((Vector) vItens.elementAt(i)).elementAt(3).toString();
              sLote = ((Vector) vItens.elementAt(i)).elementAt(4).toString();
              iCodFaseI = Integer.parseInt(((Vector) vItens.elementAt(i)).elementAt(5).toString());
              
              if(iCodFaseI==iCodFaseF) {
                drawTexto(sCod,10,iY); //Codigo	
                    		  	
  		        Barcode128 b = new Barcode128();
  		        String sBarCode = iSeqOf+"#"+iCodOP+"#"+sCod.trim()+"#"+sLote.trim()+"#"+sQtd.trim();
  		        sBarCode = sBarCode.replace('/','_');
  		           
  		        b.setCode(sBarCode);

  		        Image image = b.createAwtImage(Color.BLACK, Color.WHITE);
  		        ImageIcon icon = new ImageIcon(image);
  		  	
  		        drawImagem(icon,210,iY-8,170,14);
  		  	
                drawTexto(sDesc,50,iY); //Descrição	
                drawTexto(Funcoes.alinhaDir(sQtd,15),370,iY);//Quantidade
                drawTexto(sUnid,430,iY);//Unidade
                drawTexto(sLote,500,iY);//Lote
                iY = iY+18;	                            	              	
              }            	
            }
            iY = iY+10;          
            drawRetangulo(5,iYIni-15,5,iY-iYIni,AL_CDIR);
						 								 		
		}
		catch(Exception e) {
			e.printStackTrace();
		}		
	}

	private void impFaseEB(ResultSet rsFases) {        
		  	int iCodFaseF = 0;
		  	int iCodFaseI = 0;
		  	int iYIni = 0;
		  	
		try {
		  	iCodFaseF = rsFases.getInt(2);


            iY = iY+10;

            iYIni = iY;	 
            
            int iInst = 0;
            if(rsFases.getString("INSTRUCOES")!=null) { 
                setFonte(fnArial9N);		    
                drawTexto("Instrução de preparo",250,iY-5);
                iInst = impLabelSilabas(rsFases.getString("INSTRUCOES").trim(),7,250,280,iY+2,fnInstrucoes);
            }
             
            
            setFonte(fnTitulo);
		  	drawTexto("FASE: "+rsFases.getString(1).trim(),10,iY);
//		  	drawTexto("FASE: "+rsFases.getString(1).trim(),0,iY,getFontMetrics(fnTitulo).stringWidth("  "+"FASE: "+rsFases.getString(1).trim()+"  "),AL_CEN);				
		  			  	
		  	iY = iY+15;
//		  	drawTexto(rsFases.getString(3).trim().toUpperCase(),0,iY,getFontMetrics(fnTitulo).stringWidth("  "+rsFases.getString(3).trim().toUpperCase()+"  "),AL_CEN);		  			  	
		  	drawTexto(rsFases.getString(3).trim().toUpperCase(),10,iY);
            iY = iY+13;
 
            setFonte(fnArial9N);		    
            drawTexto("Recurso:",10,iY);
            setFonte(fnArial9);
            drawTexto(rsFases.getString(7),60,iY);

            iY = iY+10; 
            setFonte(fnArial9N);
//            drawTexto("Tempo estimado(min.):",220,iY);
            drawTexto("Tempo estimado(min.):",10,iY);
            setFonte(fnArial9);
//            drawTexto((rsFases.getFloat(5)/60)+"",330,iY);
            drawTexto((rsFases.getFloat(5)/60)+"",120,iY);
            iY = iY+10;

//            int iDif = 0;                        
            if(iInst>iY){
//                iDif = (iInst+2) - iY;
                iY = iInst+2;
            }
 
		  	
            int iYIni2=iY;
            
            drawLinha(5,iY,5,0,AL_CDIR);

            iY = iY+5;
                        
            setFonte(fnArial9N);            
            iY = iY+14;
            drawTexto("Embalagens a serem descarregadas",60,iY); 
            iY = iY+16;
            setFonte(fnArial9N); 
            drawTexto("Cód.   Tipo de Embalagem    Lote          Qtd. Emb.",20,iY);
            iY = iY+20;
            setFonte(fnArial9);
            iY = iY+15;
            iY = iY+15;           

            int iX = 280;
            iY = iY - 65;
            setFonte(fnArial9N);  
            drawTexto("Descarregamento, pesagem e rotulagem",50+iX,iY); 
            iY = iY+16;            
            drawTexto("Cód.   Tipo de Embalagem    Lote          Qtd. Emb.",12+iX,iY);
            setFonte(fnArial9);
            iY = iY+12;

            String sCod = "";
            String sDesc = "";
            String sQtd = "";
            String sUnid = "";
            String sLote = "";

            for(int i=0;vItens.size()>i;i++) {
                sCod  = ((Vector) vItens.elementAt(i)).elementAt(0).toString();
                sDesc = ((Vector) vItens.elementAt(i)).elementAt(1).toString();
                sLote = ((Vector) vItens.elementAt(i)).elementAt(4).toString();
                sQtd  = ((Vector) vItens.elementAt(i)).elementAt(2).toString();
                sUnid = ((Vector) vItens.elementAt(i)).elementAt(3).toString();

                iCodFaseI = Integer.parseInt(((Vector) vItens.elementAt(i)).elementAt(5).toString());
                
                if(iCodFaseI==iCodFaseF) {
                  drawTexto(sCod,18,iY); //Codigo
                  drawTexto(sCod,iX+12,iY); //Codigo
                  drawTexto(sDesc.substring(0,20),47,iY); //Descrição	
                  drawTexto(sDesc.substring(0,20),iX+42,iY); //Descrição
                  drawTexto(sLote,145,iY);//Lote
                  drawTexto(sLote,iX+140,iY);//Lote
                  drawTexto(Funcoes.alinhaDir(sQtd,15)+" "+sUnid,170,iY);//Quantidade
                  drawTexto(Funcoes.alinhaDir(sQtd,15)+" "+sUnid,iX+162,iY);//Quantidade
                  iY = iY+12;	                            	              	
                }            	
            }
            
            setFonte(fnArial9N);
            iY = iY+15;	 
            drawTexto("OBS.:____________________________________________",12+iX,iY);
            iY = iY+15;
            drawTexto("_________________________________________________",12+iX,iY);
            iY = iY+15;
            drawTexto("_________________________________________________",12+iX,iY);
            iY = iY+20;
            drawTexto("Nome:____________________________________________",12+iX,iY);
            iY = iY+15;
            drawTexto("Data:____________________________________________",12+iX,iY);
            iY = iY+30;

            drawLinha(280,iYIni2+5,280,iYIni2+137);
            drawRetangulo(10,iYIni2+5,10,132,AL_CDIR);
            drawRetangulo(5,iYIni-15,5,210,AL_CDIR);   
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void impFaseCq(ResultSet rsFases) {        
		try {

            iY = iY+10;
		  	int iYIni = iY;	 

            int iInst = 0;
            if(rsFases.getString("INSTRUCOES")!=null) { 
                setFonte(fnArial9N);		    
                drawTexto("Instrução de preparo",250,iY-5);
                iInst = impLabelSilabas(rsFases.getString("INSTRUCOES").trim(),7,250,280,iY+2,fnInstrucoes);
            }

			setFonte(fnTitulo);
			
//		  	drawTexto("FASE: "+rsFases.getString(1).trim(),0,iY,getFontMetrics(fnTitulo).stringWidth("  "+"FASE: "+rsFases.getString(1).trim()+"  "),AL_CEN);				
		  	drawTexto("FASE: "+rsFases.getString(1).trim(),10,iY);
		  	
		  	iY = iY+15;
//		  	drawTexto(rsFases.getString(3).trim().toUpperCase(),0,iY,getFontMetrics(fnTitulo).stringWidth("  "+rsFases.getString(3).trim().toUpperCase()+"  "),AL_CEN);		  			  	
		  	drawTexto(rsFases.getString(3).trim().toUpperCase(),10,iY);		  	
            iY = iY+13;
 
            setFonte(fnArial9N);		    
            drawTexto("Recurso:",10,iY);
            setFonte(fnArial9);
            drawTexto(rsFases.getString(7),60,iY);
            
            iY = iY+13;         
            
            setFonte(fnArial9N);
            drawTexto("Tempo estimado(min.):",10,iY);
            setFonte(fnArial9);
            drawTexto((rsFases.getFloat(5)/60)+"",120,iY);
            iY = iY+10;

            if(iInst>iY){
                iY = iInst+2;
            }
                        
            drawLinha(5,iY,5,0,AL_CDIR);

            iY = iY+5;
            drawLinha(280,iY,280,iY+100);
   		
            drawRetangulo(10,iY,10,100,AL_CDIR);
            
            setFonte(fnArial9N);            
            iY = iY+14;
            drawTexto("PRODUÇÃO",110,iY); 
            iY = iY+16;
            setFonte(fnArial9N); 
            drawTexto("Amostra retirada para análise",75,iY);
            iY = iY+20;
            setFonte(fnArial9);
            drawTexto("Quantidade:",15,iY);
            drawLinha(70,iY,240,iY);
            iY = iY+15;
            drawTexto("Nome:",15,iY);
            drawLinha(70,iY,240,iY);
            iY = iY+15;
            drawTexto("Data:",15,iY);
            drawLinha(70,iY,240,iY);            

            int iX = 280;
            iY = iY - 65;
            setFonte(fnArial9N); 
            drawTexto("LABORATÓRIO",110+iX,iY); 
            iY = iY+16;            
            drawTexto("Amostra retirada para análise",75+iX,iY);
            iY = iY+20;
            setFonte(fnArial9);
            drawTexto("Resultado:",15+iX,iY);
            drawLinha(70+iX,iY,240+iX,iY);
            iY = iY+15;
            drawTexto("Nome:",15+iX,iY);
            drawLinha(70+iX,iY,240+iX,iY);
            iY = iY+15;
            drawTexto("Data:",15+iX,iY);
            drawLinha(70+iX,iY,240+iX,iY);
            iY = iY+39;
            
            
            drawRetangulo(5,iYIni-15,5,iY-iYIni,AL_CDIR);

		}
		catch(Exception e) {
			e.printStackTrace();
		}		
	}

	private void impAssinatura(){
	  String sSql = "SELECT NOMERESP,IDENTPROFRESP,CARGORESP FROM SGPREFERE5 WHERE CODEMP=? AND CODFILIAL=?";
	  String sNome = "";
	  String sCargo = "";
	  String sID = "";
	  try {
	    
	    PreparedStatement ps = null;
		ResultSet rs = null;
		ps = con.prepareStatement(sSql);
		ps.setInt(1,Aplicativo.iCodEmp);
		ps.setInt(2,ListaCampos.getMasterFilial("SGPREFERE5"));
		rs = ps.executeQuery();

		if (rs.next()) {
		  if (rs.getString("NOMERESP")!=null)
		    sNome = rs.getString("NOMERESP").trim();
		  if (rs.getString("CARGORESP")!=null)
		    sCargo = rs.getString("CARGORESP").trim();
		  if (rs.getString("IDENTPROFRESP")!=null)
		    sID = rs.getString("IDENTPROFRESP").trim();
	  	}
		 
		rs.close();
		ps.close();
		}
	    catch (SQLException e) {
	    	e.printStackTrace();
	    }
	    iY = iY + 30;
	    setFonte(fnArial9N);
        drawLinha(0,iY,300,0,AL_CEN);
		iY = iY+10;
	    drawTexto(sNome,0,iY,getFontMetrics(fnArial9N).stringWidth("  "+sNome+"  "),AL_CEN);
        iY = iY+12;
	  	drawTexto(sCargo,0,iY,getFontMetrics(fnArial9N).stringWidth("  "+sCargo+"  "),AL_CEN);
        iY = iY+12;
	  	drawTexto(sID,0,iY,getFontMetrics(fnArial9N).stringWidth("  "+sID+"  "),AL_CEN);

		
	}

	private void montaCab() {
	  try {		  
		setBordaRel();
	  	setFonte(fnTitulo);
		drawLinha(0,35,0,0,AL_BDIR);
	  	drawRetangulo(5,40,5,50,AL_CDIR);

		drawTexto("ORDEM DE PRODUÇÃO",0,55,150,AL_CEN);	
		setFonte(fnArial9N); 
/*
		drawTexto("O.P. número:",10,70);
	    drawTexto("Produto:",10,82);
	    drawTexto("Quantidade:",10,94);
	    drawTexto("Data de fabricação:",10,106);
	    drawTexto("Data de validade:",10,118);
        drawTexto("Data de emissão:",10,130);
	    drawTexto("Lote:",10,142);	    
*/

		drawTexto("O.P. número:",10,70);
	    drawTexto("Produto:",110,70);
	    drawTexto("Quantidade:",10,82);
	    drawTexto("Data de fabricação:",110,82);
	    drawTexto("Data de validade:",270,82);
        drawTexto("Data de emissão:",420,82);
	    drawTexto("Lote:",420,70);	    

	    setFonte(fnArial9);

	    drawTexto((iCodOP+"").trim(),70,70); //Código da OP
	    drawTexto(sDescProd,153,70); //Descrição do produto a ser fabricado 
	    drawTexto(sQtd,70,82); //qtd. a fabricar 
	    drawTexto(sDtFabrica,200,82); //Data de fabricação
	    drawTexto(sDtValidade,350,82); //Data de validade
	    drawTexto(Funcoes.dateToStrDate(new Date()),505,82);
	    drawTexto(sLote,505,70);
	    
	    /*
	    drawTexto(iCodOP+"",110,70); //Código da OP
	    drawTexto(sDescProd,110,82); //Descrição do produto a ser fabricado 
	    drawTexto(sQtd,110,94); //qtd. a fabricar 
	    drawTexto(sDtFabrica,110,106); //Data de fabricação
	    drawTexto(sDtValidade,110,118); //Data de validade
	    drawTexto(Funcoes.dateToStrDate(new Date()),110,130);
	    drawTexto(sLote,110,142);
	    */
	  }			
	  catch(Exception err) {
		Funcoes.mensagemErro(this,"Erro ao montar dados do cliente!!!\n"+err.getMessage());
		err.printStackTrace();
	  }
	}
    	
	public void stParam(Vector vParam) {
		vParamOP = vParam;
	}

	public void setConexao(Connection cn) {
		con = cn;
	}
}