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
	int iSeqOP = 0;
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
		iSeqOP = Integer.parseInt(vParamOP.elementAt(1).toString());
	    iYPosProd = iPosIniItens;
		try {
		  String sSQL = "SELECT ITOP.CODOP,ITOP.SEQITOP,OP.DTEMITOP,OP.CODPROD,(SELECT PROD2.DESCPROD FROM EQPRODUTO PROD2 WHERE PROD2.CODPROD=OP.CODPROD  AND PROD2.CODEMP=OP.CODEMPPD  AND PROD2.CODFILIAL=OP.CODFILIALPD),"+
			"EST.DESCEST,EST.QTDEST,OP.DTFABROP,OP.QTDPREVPRODOP,DTVALIDPDOP,OP.DTINS,ITOP.CODPROD,PROD.DESCPROD,UNID.DESCUNID,ITOP.CODLOTE,ITOP.QTDITOP,OP.QTDPREVPRODOP,ITOP.CODFASE,OP.CODLOTE,PROD.CODUNID "+
			"FROM PPESTRUTURA EST,PPOP OP, PPITOP ITOP, EQUNIDADE UNID, EQPRODUTO PROD "+
			"WHERE EST.CODPROD=OP.CODPROD AND ITOP.CODOP=OP.CODOP AND ITOP.SEQOP=OP.SEQOP AND UNID.CODUNID=PROD.CODUNID "+
			"AND PROD.CODPROD = ITOP.CODPROD AND OP.CODOP=? AND OP.SEQOP=? AND OP.CODEMP=? AND OP.CODFILIAL=?";

		  PreparedStatement ps = con.prepareStatement(sSQL);
		  

  		  ps.setInt(1,iCodOP);
  		  ps.setInt(2,iSeqOP);
   	      ps.setInt(3,Aplicativo.iCodEmp);
  	      ps.setInt(4,ListaCampos.getMasterFilial("PPOP"));
		  
		  ResultSet rs = ps.executeQuery();
		  		   
		  while (rs.next()) {
            vItem = new Vector();
            vItem.addElement((rs.getString(12)!=null?rs.getString(12):"")); //Código
            vItem.addElement((rs.getString(13)!=null?rs.getString(13):"")); //Descrição
            vItem.addElement((rs.getString(16)!=null?Funcoes.strDecimalToStrCurrency(3,rs.getString(16)):"0")); //Quantidade
            vItem.addElement((rs.getString(20)!=null?rs.getString(20):"")); //Código da Unidade
            vItem.addElement((rs.getString(15)!=null?rs.getString(15):"")); //Lote
            vItem.addElement((rs.getString(18)!=null?rs.getString(18):"0")); //Fase            
            vItens.addElement(vItem.clone());
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
				 "AND OP.CODEMP=OPF.CODEMP AND OP.CODFILIAL=OPF.CODFILIAL AND OP.CODOP=OPF.CODOP AND OP.SEQOP=OPF.SEQOP "+
				 "AND REC.CODRECP = OPF.CODRECP AND REC.CODEMP = OPF.CODEMPRP AND REC.CODFILIAL = OPF.CODFILIALRP "+
				 "AND OPF.CODOP=? AND OPF.SEQOP=? AND OPF.CODEMP=? AND OPF.CODFILIAL=? " +
				 "AND EF.CODEMP=OPF.CODEMP AND EF.CODFILIAL=OPF.CODFILIAL AND EF.CODPROD=OP.CODPROD " +
				 "AND EF.SEQEF=OPF.SEQOF " +				 
				 "ORDER BY OPF.SEQOF";
		  		  
		  PreparedStatement psFases = con.prepareStatement(sSQL);
		  
  		  psFases.setInt(1,iCodOP);
  		  psFases.setInt(2,iSeqOP);
   	      psFases.setInt(3,Aplicativo.iCodEmp);
  	      psFases.setInt(4,ListaCampos.getMasterFilial("PPOPFASE"));

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
		String sCod = "";
        String sQtd = "";
        String sUnid = "";
        String sLote = "";
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
            drawTexto("Cód.",30,iY);
            drawTexto("Cód.Barras",100,iY);
            drawTexto("Qtd.",340,iY);
            drawTexto("Unid",400,iY);
            drawTexto("Lote",460,iY);
    		drawLinha(5,iY+5,5,0,AL_CDIR);
            iY = iY + 15;
            setFonte(fnArial9);
                        
                        
            for(int i=0;vItens.size()>i;i++) {
              sCod  = ((Vector) vItens.elementAt(i)).elementAt(0).toString();
              sQtd  = ((Vector) vItens.elementAt(i)).elementAt(2).toString();
              sUnid = ((Vector) vItens.elementAt(i)).elementAt(3).toString();
              sLote = ((Vector) vItens.elementAt(i)).elementAt(4).toString();
              iCodFaseI = Integer.parseInt(((Vector) vItens.elementAt(i)).elementAt(5).toString());
              
              if(iCodFaseI==iCodFaseF) {
                drawTexto(sCod,30,iY); //Codigo	
                    		  	
  		        Barcode128 b = new Barcode128();
  		        String sBarCode = iSeqOf+"#"+iCodOP+"#"+iSeqOP+"#"+sCod.trim()+"#"+sLote.trim()+"#"+sQtd.trim();
  		        sBarCode = sBarCode.replace('/','_');
  		           
  		        b.setCode(sBarCode);

  		        Image image = b.createAwtImage(Color.BLACK, Color.WHITE);
  		        ImageIcon icon = new ImageIcon(image);
  		  	
  		        drawImagem(icon,100,iY-9,200,15);
  		  	
                drawTexto(Funcoes.alinhaDir(sQtd,15),320,iY);//Quantidade
                drawTexto(sUnid,400,iY);//Unidade
                drawTexto(sLote,460,iY);//Lote
                iY = iY+18;	                            	              	
              }            	
            }
            iY = iY+10;          
            drawRetangulo(5,iYIni-15,5,iY-iYIni,AL_CDIR);
						 								 		
		}
		catch(Exception e) {
			e.printStackTrace();
		}		
		finally{
			sCod = null;
            sQtd = null;
            sUnid = null;
            sLote = null;
		}
	}

	private void impFaseEB(ResultSet rsFases) {        
	  	int iCodFaseF = 0;
	  	int iCodFaseI = 0;
	  	int iYIni = 0;
	  	Vector vItensEB = null;
	  	Vector vColunasEB = null;
        String sCod = null;
        String sDesc = null;
        String sQtd = null;
        String sUnid = null;
        String sLote = null;
        String sSQL = null;
        String sBarCode = null;
        String sSeqOF = null;
        String sSeqOP = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
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
            drawTexto("EMBALAGENS A SEREM DESCARREGADAS",150,iY); 
            iY = iY+16;
            setFonte(fnArial9N); 
            drawTexto("Cód.",10,iY);
            drawTexto("Tipo de Embalagem",40,iY);
            drawTexto("Código de Barras",180,iY);
            drawTexto("Lote",385,iY);
            drawTexto("Qtd.",450,iY);
            drawTexto("Emb.",480,iY);
            iY = iY+20;
            
	            sSQL = "SELECT I.SEQOP, I.CODPROD, P.DESCPROD, I.QTDITOP, P.CODUNID, I.CODLOTE, I.CODFASE, OPF.SEQOF " +
	            		"FROM PPOP O, PPITOP I, EQPRODUTO P, PPOPFASE OPF " +
	            		"WHERE O.CODEMPOPM=? AND O.CODFILIALOPM=? AND O.CODOPM=? AND O.SEQOPM=? AND " +
	            		"I.CODEMP=O.CODEMP AND I.CODFILIAL=O.CODFILIAL AND I.CODOP=O.CODOP AND I.SEQOP=O.SEQOP AND " +
	            		"P.CODEMP=I.CODEMPPD AND P.CODFILIAL=I.CODFILIALPD AND P.CODPROD=I.CODPROD " +
	            		"AND OPF.CODEMP=O.CODEMP AND OPF.CODFILIAL=O.CODFILIAL AND OPF.CODOP=O.CODOP AND OPF.SEQOP=O.SEQOP";                       
				ps = con.prepareStatement(sSQL);						
				ps.setInt(1,Aplicativo.iCodEmp);
				ps.setInt(2,ListaCampos.getMasterFilial("PPOP"));
				ps.setInt(3,iCodOP);
				ps.setInt(4, iSeqOP);			 
				rs = ps.executeQuery();
			
			vItensEB = new Vector();
			while (rs.next()) {
				vColunasEB = new Vector();
			    vColunasEB.addElement((rs.getString("CODPROD")!=null?rs.getString("CODPROD"):"")); //Código
			    vColunasEB.addElement((rs.getString("DESCPROD")!=null?rs.getString("DESCPROD"):"")); //Descrição
			    vColunasEB.addElement((rs.getString("QTDITOP")!=null?Funcoes.strDecimalToStrCurrency(3,rs.getString("QTDITOP")):"0")); //Quantidade
			    vColunasEB.addElement((rs.getString("CODUNID")!=null?rs.getString("CODUNID"):"")); //Unidade
			    vColunasEB.addElement((rs.getString("CODLOTE")!=null?rs.getString("CODLOTE"):"")); //Lote
			    vColunasEB.addElement((rs.getString("CODFASE")!=null?rs.getString("CODFASE"):"0")); //Código da Fase
			    vColunasEB.addElement((rs.getString("SEQOF")!=null?rs.getString("SEQOF"):"0")); //Sequencia da Fase
			    vColunasEB.addElement((rs.getString("SEQOP")!=null?rs.getString("SEQOP"):"0")); //Sequencia da OP
			    vItensEB.addElement(vColunasEB);
			}
			rs.close();
			ps.close();
			if (!con.getAutoCommit())
				con.commit();			

            setFonte(fnArial9);
			
			for(int i=0;vItensEB.size()>i;i++) {
				if(iY>=736){
				    drawRetangulo(5,iYIni-15,5,(iY-iYIni2)+60,AL_CDIR);
					termPagina();
					montaCabEmp(con);
					montaCab();
					iY = 110;
					iYIni = iY;
					iYIni2 = iY;
					setFonte(fnArial9N); 
		            drawTexto("Cód.",10,iY);
		            drawTexto("Tipo de Embalagem",40,iY);
		            drawTexto("Código de Barras",180,iY);
		            drawTexto("Lote",385,iY);
		            drawTexto("Qtd.",450,iY);
		            drawTexto("Emb.",480,iY);
					iY = iY+20;
				}
				
				vColunasEB = (Vector) vItensEB.elementAt(i);
			    sCod  = vColunasEB.elementAt(0).toString();
			    sDesc = vColunasEB.elementAt(1).toString();
			    sQtd  = vColunasEB.elementAt(2).toString();
			    sUnid = vColunasEB.elementAt(3).toString();
			    sLote = vColunasEB.elementAt(4).toString();
			    sSeqOF = vColunasEB.elementAt(6).toString();
			    sSeqOP = vColunasEB.elementAt(7).toString();
			    iCodFaseI = Integer.parseInt(vColunasEB.elementAt(5).toString());
			    
			    if(iCodFaseI==iCodFaseF) {
				      drawTexto(sCod,10,iY); //Codigo
				      drawTexto(sDesc.substring(0,20),40,iY); //Descrição	
				      
				      Barcode128 barra = new Barcode128();
	  		          sBarCode = sSeqOF+"#"+iCodOP+"#"+sSeqOP+"#"+sCod.trim()+"#"+sLote.trim()+"#"+sQtd.trim();
	  		          sBarCode = sBarCode.replace('/','_');  
	  		          barra.setCode(sBarCode);
	  		          Image image = barra.createAwtImage(Color.BLACK, Color.WHITE);
	  		          ImageIcon icon = new ImageIcon(image);
	  		  	
		  		      drawImagem(icon,163,iY-9,200,15);
				      
				      drawTexto(sLote,370,iY);//Lote
				      drawTexto(Funcoes.alinhaDir(sQtd,15)+"   "+sUnid,415,iY);//Quantidade
				      drawLinha(475,iY,55,0,AL_BCEN);
				      iY = iY+20;	                            	              	
			    }            	
			}
			
			if(iY>=736){
			    drawRetangulo(5,iYIni-15,5,(iY-iYIni2)+60,AL_CDIR);
				termPagina();
				montaCabEmp(con);
				montaCab();
				iY = 110;
				iYIni = iY;
				iYIni2 = iY;
			}
			setFonte(fnArial9N);
			iY = iY+15;	 
			drawTexto("OBS.:___________________________________________________________________________________________",20,iY);
			iY = iY+15;
			drawTexto("Nome:__________________________________________   Data:__________________________________________",20,iY);			
			iY = iY+20;						
			
			drawRetangulo(5,iYIni-15,5,(iY-iYIni2)+20,AL_CDIR);   
		}
		catch(SQLException e) {
			Funcoes.mensagemErro(null, "Erro carregando itens!\n"+e.getMessage());
		}
		catch(Exception e) {
			Funcoes.mensagemErro(null, "Erro carregando itens!\n"+e.getMessage());
		}
		finally {
		  	iCodFaseF = 0;
		  	iCodFaseI = 0;
		  	iYIni = 0;
		  	vColunasEB = null;
		  	vItensEB = null;
	        sCod = null;
	        sDesc = null;
	        sQtd = null;
	        sUnid = null;
	        sLote = null;
	        sSQL = null;
	        sBarCode = null;
	        sSeqOF = null;
	        sSeqOP = null;
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

		drawTexto("O.P. número:",10,70);
	    drawTexto("Produto:",110,70);
	    drawTexto("Quantidade:",10,82);
	    drawTexto("Data de fabricação:",110,82);
	    drawTexto("Data de validade:",270,82);
        drawTexto("Emissão:",420,82);
	    drawTexto("Lote:",420,70);	    

	    setFonte(fnArial9);

	    drawTexto((iCodOP+"").trim(),70,70); //Código da OP
	    drawTexto(sDescProd,153,70); //Descrição do produto a ser fabricado 
	    drawTexto(sQtd,70,82); //qtd. a fabricar 
	    drawTexto(sDtFabrica,200,82); //Data de fabricação
	    drawTexto(sDtValidade,350,82); //Data de validade
	    drawTexto(Funcoes.dateToStrDate(new Date()),475,82);
	    drawTexto(sLote,475,70);
	    	    
	  }			
	  catch(Exception err) {
		Funcoes.mensagemErro(this,"Erro ao montar dados do cliente!!!\n"+err.getMessage());
		err.printStackTrace();
	  }
	}
    
	public void setConexao(Connection cn) {
		con = cn;
	}
}