/**
 * @version 03/10/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva/Robson Sanchez <BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: org.funcoes <BR>
 * Classe: @(#)Funcoes.java <BR>
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
 * Comentários da classe.....
 */

package org.freedom.funcoes;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;
import java.util.Vector;

import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileFilter;

import org.freedom.componentes.StringDireita;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FFDialogo;

//import org.telas.Aplicativo;

public class Funcoes {
	
  private static Vector vIE = new Vector(34);
  private static Vector vPesoIE = new Vector(13);
  //private static ImageIcon imgIcone = null;
  public static String sIEValida = "";
  private static JDialog dlErro = null;
  public Funcoes() { }
  public static String replicate(String texto, int Quant) {
     String sRetorno = "";
     for ( int i = 1; i<=Quant ; i++ ) {
       sRetorno = sRetorno + texto;      
     }
     return sRetorno;
  }

   public static double arredDouble(double deValor, int iDec) {
	    long lgValor = 0;
		double deOper = 1;
		try {
		  for (int i=1; i<=iDec; i++) deOper = deOper * 10;
		  lgValor = (long) ( deValor * deOper);
		  deValor = (double) lgValor ;
		  deValor = lgValor / deOper;
		}
	    finally {
	      lgValor = 0;
	    }
		
		return deValor;
   }
 
  public static void espera(int iSec) {
  	long iIni = getSeconds();
  	long iFim = getSeconds();
  	while ( (iFim-iIni) < iSec) {
  		iFim = getSeconds();
  	}
  }
  
  public static long getSeconds() {
  	java.util.Date dtHora = new Date();
  	return dtHora.getTime()/1000;
  }
  
  public static boolean ehInteiro(String sNum) {
  	 boolean bRetorno = false;
  	 for (int i=0; i<sNum.length(); i++) {
  	 	if ("0123456789-".indexOf(sNum.charAt(i))==-1) {
  	 		bRetorno = false;
  	 		break;
  	 	}
 		bRetorno = true;
  	 }
  	 return bRetorno;
  }
  
  public static int[] decodeDate(java.util.Date dtSel) {
    GregorianCalendar gcSel = new GregorianCalendar();
    int[] iRetorno = {0,0,0};
    try {
       gcSel.setTime(dtSel);
       iRetorno[0] = gcSel.get(Calendar.YEAR);
       iRetorno[1] = gcSel.get(Calendar.MONTH)+1;
       iRetorno[2] = gcSel.get(Calendar.DAY_OF_MONTH);
    }
    finally {
    	gcSel = null;
    }
    return iRetorno;
  }
  public static Date encodeDate(int iAno, int iMes, int iDia) {
  	 Date dtRetorno = new Date();
     GregorianCalendar gcSel = new GregorianCalendar();
     try {
     	gcSel.setTime(dtRetorno);
     	gcSel.set(Calendar.YEAR,iAno);
     	gcSel.set(Calendar.MONTH,iMes-1);
     	gcSel.set(Calendar.DAY_OF_MONTH,iDia);
     	dtRetorno = gcSel.getTime();
     }
     finally {
     	gcSel = null;
     }
  	 return dtRetorno;
  }
  
  public static java.util.Date encodeTime(Date dtSel, int iHora, int iMinuto, int iSegundo, int iMilesegundo) {
  	 Date dtRetorno = dtSel;
     GregorianCalendar gcSel = new GregorianCalendar();
     try {
     	gcSel.setTime(dtSel);
     	gcSel.set(Calendar.HOUR,iHora);
     	gcSel.set(Calendar.MINUTE,iMinuto);
     	gcSel.set(Calendar.SECOND,iSegundo);
     	gcSel.set(Calendar.MILLISECOND,iMilesegundo);
		dtSel = gcSel.getTime();
     }
     finally {
     	gcSel = null;
     }
  	 return dtRetorno;
  }
  public static int mensagemConfirma(Component frame, String sMensagem) {
  	if (frame == null)
  	  return JOptionPane.showConfirmDialog(frame,sMensagem,"Confirmação",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
    return JOptionPane.showInternalConfirmDialog(frame,sMensagem,"Confirmação",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
  }
  
  public static String adicEspacosEsquerda(String sTexto, int iEspacos) {
  	 if (iEspacos>sTexto.length()) {
  	 	sTexto = replicate(" ",iEspacos-sTexto.length())+sTexto;
  	 }
  	 return sTexto;
  }
  public static Vector ordenaVector(Vector v, int iEspacos) {
  	String s1 = "";
  	String s2 = "";
  	try {
  		if (v!=null) {
  			for (int i1=1; i1 < v.size(); i1++) {
  				s1 = adicEspacosEsquerda(v.elementAt(i1).toString().trim(),iEspacos);
  				s2 = adicEspacosEsquerda(v.elementAt(i1-1).toString().trim(),iEspacos);
  				if (s1.compareTo(s2)<0) {
  					for (int i2=0; i2<i1; i2++) {
  						s1 = adicEspacosEsquerda(v.elementAt(i1).toString(),iEspacos);
  						s2 = adicEspacosEsquerda(v.elementAt(i2).toString(),iEspacos);
  		  				if (s1.compareTo(s2)<0) {
  		  					for (int i3=i2; i3<i1; i3++) {
  		  						//mostraVector(v);
  		  						s1 = v.elementAt(i1).toString();
  		  						s2 = v.elementAt(i3).toString();
  		  						v.setElementAt(s2,i1);
  		  						v.setElementAt(s1,i3);
  		  						//mostraVector(v);
  		  					}
  		  				}
  					}
  				}
  			}
  		}
  	}
  	finally {
  		s1 = null;
  		s2 = null;
  	}
  	return v;
  }
  public static void mostraVector(Vector v) {
  	String sMostra = "";
  	try {
  		for (int i=0; i<v.size(); i++) {
  		  sMostra += v.elementAt(i).toString()+";";
  		}
  		mensagemInforma(null,sMostra);
  	}
  	finally {
  		sMostra = null;
  	}
  }
  public static long getNumDias(Date dt1, Date dt2) {
  	long lResult = 0;
	long lDias1 = 0;
	long lDias2 = 0;
	GregorianCalendar cal1 = new GregorianCalendar();
	GregorianCalendar cal2 = new GregorianCalendar();
	cal1.setTime(dt1);
	cal1.set(cal1.get(Calendar.YEAR),cal1.get(Calendar.MONTH),cal1.get(Calendar.DATE),0,0,0);
	cal2.setTime(dt2);
	cal2.set(cal2.get(Calendar.YEAR),cal2.get(Calendar.MONTH),cal2.get(Calendar.DATE),0,0,0);
	
	lDias1 = cal1.getTimeInMillis();
	lDias2 = cal2.getTimeInMillis();

	lResult = (lDias2-lDias1) / (60*24*60*1000);  
  	return lResult;
  }
  
  public static long getNumDiasAbs(Date dt1, Date dt2) {
  	long lResult = 0;
	long lDias1 = 0;
	long lDias2 = 0;
	GregorianCalendar cal1 = new GregorianCalendar();
	GregorianCalendar cal2 = new GregorianCalendar();
	cal1.setTime(dt1);
	cal1.set(cal1.get(Calendar.YEAR),cal1.get(Calendar.MONTH),cal1.get(Calendar.DATE),0,0,0);
	cal2.setTime(dt2);
	cal2.set(cal2.get(Calendar.YEAR),cal2.get(Calendar.MONTH),cal2.get(Calendar.DATE),0,0,0);
	
	lDias1 = cal1.getTimeInMillis();
	lDias2 = cal2.getTimeInMillis();
	if (lDias1>lDias2) 
		lResult = (lDias1-lDias2) / (60*24*60*1000);  
	else 
		lResult = (lDias2-lDias1) / (60*24*60*1000);
  	return lResult;
  }
  public static void mensagem(Component frame, String sMensagem, String sTitulo, int iOpcao) {
  //	imgIcone = Aplicativo.imgIcone;
	if (frame == null) {
	   //if (imgIcone==null) 
	      JOptionPane.showMessageDialog(frame,sMensagem,sTitulo,iOpcao);
	   //else
	     // JOptionPane.showMessageDialog(frame,sMensagem,sTitulo,iOpcao,imgIcone);
	}	    
	else if (frame instanceof JInternalFrame) {
	   //if (imgIcone==null)
	      JOptionPane.showInternalMessageDialog(frame, sMensagem, sTitulo , iOpcao);
	   //else
   	     // JOptionPane.showInternalMessageDialog(frame, sMensagem, sTitulo , iOpcao, imgIcone);
	}
	else {
	   //if (imgIcone==null)
	      JOptionPane.showMessageDialog(frame,sMensagem, sTitulo, iOpcao);
	   //else
	     // JOptionPane.showMessageDialog(frame,sMensagem, sTitulo, iOpcao,imgIcone);
	}
  }
  
   
  public static void mensagemInforma(Component frame, String sMensagem) {
  	mensagem(frame, sMensagem, "Informação" , JOptionPane.INFORMATION_MESSAGE);
  }

  public static void mensagemErro(Component frame, String sMensagem) {
	mensagem(frame, sMensagem, "Erro" , JOptionPane.ERROR_MESSAGE);
  	
  }
  
  public static String trimFinal(String sVal) {
    char[] cVal = sVal.toCharArray();
    String sRetorno = sVal;
    for (int i=sVal.length()-1;i>=0;i--) {
      if (cVal[i] != ' ') {
        sRetorno = sVal.substring(0,i+1);
        break;
      }
    }
    return sRetorno;
  }
  
  public static void setBordReq(JComponent comp) {
  	 setBordReq(comp,true);
  }

  public static void setBordReq(JComponent comp, boolean bReq) {
  	if (bReq) {
  		comp.setBorder(
  				BorderFactory.createCompoundBorder(
  					BorderFactory.createMatteBorder(1, 1, 1, 1, Color.red),
					BorderFactory.createEtchedBorder()
                )
  		);
  	}
  	else {
  		comp.setBorder(
  				BorderFactory.createCompoundBorder(
  					BorderFactory.createMatteBorder(0, 0, 0, 0, Color.darkGray),
					BorderFactory.createEtchedBorder()
                )
  		);
  	}
  }
  
  public static char getPontoDec() {
    return ',';
  }
  public static String setPontoDec(String sVal) {
    int iLocal = sVal.indexOf('.') >= 0 ? sVal.indexOf('.') : sVal.indexOf(',');
    if (iLocal >= 0) {
      char[] cVal = new char[sVal.length()];
      cVal = sVal.toCharArray();
      cVal[iLocal] = getPontoDec();
      sVal = new String(cVal);
    }
    return sVal;
  }
  public static String substringByChar(String sVal,char cVal,boolean bOrient) {
    String sRetorno = "";
    sVal = copy(sVal,0,sVal.length());
    char[] cStr = sVal.toCharArray();
    if (bOrient) {
      for (int i=0;i<sVal.length();i++) {
        if (cStr[i] == cVal)
          break;
        sRetorno += cStr[i];
      }
    }
    else {
      for (int i=(sVal.length()-1);i>=0;i--) {
        if (cStr[i] == cVal)
          break;
        sRetorno = cStr[i]+sRetorno;
      }
    }
    return sRetorno;
  }
  public static String copy(String sTmp,int iPos,int iTam) {
    String sResultado = "";
  	if (sTmp == null)
       sTmp = "";
     if (sTmp.length()<(iTam+1)) {
        sResultado = sTmp+replicate(" ",iTam-sTmp.length());      
     }
     else {
        sResultado = sTmp.substring(iPos,iTam);
     }
     return sResultado;
  }
  public static String copy(String sTmp,int iTam) {
  	return copy(sTmp,0,iTam);
  }
  public static String limpaString(String sTexto) {
     String sResult = "";
     String sCaracs = "- .,;/\\";
     if (sTexto!=null) {
       for (int i=0; i<sTexto.length(); i++) {
         if (sCaracs.indexOf(sTexto.substring(i,i+1))==-1) 
            sResult = sResult + sTexto.substring(i,i+1);
       }
     }
     return sResult;
  }
  public static int contaMeses(Date dDataIni,Date dDataFim) {
  	int iMeses = 0; 
	GregorianCalendar cIni = new GregorianCalendar();
	GregorianCalendar cFim = new GregorianCalendar();
	cIni.setTime(dDataIni);
	cFim.setTime(dDataFim);

  	try{		
  	  iMeses = 1+(cFim.get(Calendar.MONTH) + 
  	           (12*( (cFim.get(Calendar.YEAR)-cIni.get(Calendar.YEAR)-1)) + 
  	           (12-cIni.get(Calendar.MONTH))));		
  	}
  	catch (Exception e) {
  		e.printStackTrace();
  	}
    return iMeses; 	
  }
  
  public static String dateToStrExtenso(Date data) {
  	String sRet = "";
	GregorianCalendar cal = new GregorianCalendar();
	cal.setTime(data);
	int iDia = 0;
	int iAno = 0;
	int iMes = 0;
	if (data != null) {
	   iDia = cal.get(Calendar.DAY_OF_MONTH);
	   iMes = cal.get(Calendar.MONTH)+1;
	   iAno = cal.get(Calendar.YEAR);
	}
	sRet = strZero(""+iDia,2)+" de "+strMes(iMes).toLowerCase()+" de "+iAno;
	return sRet;
  }
  public static String strMes(int iMes) {
  	String sRet = "";
  	switch(iMes) {
  		case 1: sRet = "Janeiro"; break;
		case 2: sRet = "Fevereiro"; break;
		case 3: sRet = "Março"; break;
		case 4: sRet = "Abril"; break;
		case 5: sRet = "Maio"; break;
		case 6: sRet = "Junho"; break;
		case 7: sRet = "Julho"; break;
		case 8: sRet = "Agosto"; break;
		case 9: sRet = "Setembro"; break;
		case 10: sRet = "Outubro"; break;
		case 11: sRet = "Novembro"; break;
		case 12: sRet = "Dezembro"; break;
  	}
  	return sRet;
  }
  public static String dataAAAAMMDD(Date data) {
  	GregorianCalendar cal = new GregorianCalendar();
  	cal.setTime(data);
    int iDia = 0;
    int iAno = 0;
    int iMes = 0;
    if (data != null) {
       iDia = cal.get(Calendar.DAY_OF_MONTH);
       iMes = cal.get(Calendar.MONTH)+1;
       iAno = cal.get(Calendar.YEAR);
    }
    return strZero(iAno+"",4)+strZero(iMes+"",2)+strZero(iDia+"",2);
  }
  public static String dataDDMMAAAA(Date data) {
  	GregorianCalendar cal = new GregorianCalendar();
  	cal.setTime(data);
    int iDia = 0;
    int iAno = 0;
    int iMes = 0;
    if (data != null) {
       iDia = cal.get(Calendar.DAY_OF_MONTH);
       iMes = cal.get(Calendar.MONTH)+1;
       iAno = cal.get(Calendar.YEAR);
    }
    return strZero(iDia+"",2)+strZero(iMes+"",2)+strZero(iAno+"",4);
  }  
  public static String doubleToStrCurExtenso(double dVal,String sMoeda[]) {
  	String sRet = "";
  	String sVals[] = (""+dVal).split("\\.");
  	String sTmp = intToStrExtenso(new Integer(sVals[0]).intValue());
  	if (!sTmp.trim().equals("")) {
  	  sRet = sTmp;
  	   if (sRet.substring(0,2).indexOf("um") == 0)
  		 sRet += " "+sMoeda[2];
  	   else if (sRet.substring(sRet.length()-2).indexOf("ão") == 0)
  		 sRet += " de "+sMoeda[3];
	   else if (sRet.substring(sRet.length()-3).indexOf("ões") == 0)
	  	 sRet += " de "+sMoeda[3];
	   else 
	     sRet += " "+sMoeda[3];
  	}
  	
  	if (!sVals[1].equals("") && (new Integer(sVals[1])).intValue() > 0) {
  		if (!sRet.equals(""))
  			sTmp = " e ";
  		else
  			sTmp = " ";
  		sTmp += intToStrExtenso(new Integer(sVals[1]).intValue());
  	    if (!sTmp.trim().equals("")) {
  		   if (sTmp.substring(0,2).indexOf("um") < 4) //4 porque pode ter o 'e' na frente.
  			  sRet += sTmp+" "+sMoeda[0];
  		   else 
  			  sRet += sTmp+" "+sMoeda[1];
  	    }
  	}
  	return sRet;
  }
  public static String intToStrExtenso(int iVal) {
  	 String sRet = "";
  	 int iTmp = 0;
  	 String sNomes[][] = {
  			{"","um","dois","três","quatro","cinco","seis","sete","oito","nove","dez","onze","doze","treze","quatorze","quinze","dezesseis","dezesete","dezoito","dezenove"},
			{"","","vinte","trinta","quarenta","cinquenta","sessenta","setenta","oitenta","noventa"},
			{"","cem","duzentos","trezentos","quatrocentos","quinhentos","seiscentos","setecentos","oitocentos","novecentos"}
  	 };
  	 if (iVal == 1000000000) {
  	 	sRet += sNomes[0][1] + " bilhão";
  	 	iVal = 0;
  	 }
  	 if (iVal > 999999999) {
  		iTmp = iVal/1000000000;
  		sRet += intToStrExtenso(iTmp) + " bilhões";
  		iVal -= iTmp*1000000000;
  		sRet += iVal > 0 ? " e " : "";
  	 }
  	 if (iVal == 1000000) {
  	 	sRet += sNomes[0][1] + " milhão";
  	 	iVal = 0;
  	 }
  	 if (iVal > 999999) {
  		iTmp = iVal/1000000;
  		sRet += intToStrExtenso(iTmp) + " milhões";
  		iVal -= iTmp*1000000;
  		sRet += iVal > 0 ? " e " : "";
  	 }
  	 if (iVal > 999) {
  		iTmp = iVal/1000;
  		sRet += (iTmp > 1) ? intToStrExtenso(iTmp)+" mil" : "mil";
  		iVal -= iTmp*1000;
// Regra do 'e':
  		if (iVal != 0) {
  		  int iCent = (iVal/100);
  		  int iDez = (iVal - (iCent*100))/10;
  		  int iUnid = (iVal - ((iDez*10)+(iCent*100)));
  		  if (iCent == 0 || (iDez == 0 && iUnid == 0))
  		    sRet += " e ";
  		  else
  		    sRet += " ";
  		}
  	    else
  	    	sRet += "";
  	 }
  	 if (iVal > 99) {
  		iTmp = iVal/100;
  		sRet += sNomes[2][iTmp];
  		iVal -= iTmp*100;
  		sRet += iVal > 0 ? " e " : "";
  	 }
  	 if (iVal > 19) {
  		iTmp = iVal/10;
  		sRet += sNomes[1][iTmp];
  		iVal -= iTmp*10;
  		sRet += iVal > 0 ? " e " : "";
  	 }
  	 if (iVal > 0) {
  		iTmp = iVal;
  		sRet += sNomes[0][iTmp];
  	 }
  	 return sRet;
  }
  public static String tiraAcentos(String sTexto) {
  	String sRet = "";
  	char cVals[] = sTexto.toCharArray();
  	for (int i=0; i<cVals.length;i++) {
  	  cVals[i] = tiraAcento(cVals[i]);
  	}
  	sRet = new String(cVals);
  	return sRet;
  }
  public static char tiraAcento(char cKey) {

	char cTmp = cKey;
           
	if (contido(cTmp,"ãâáà")) 
	  cTmp = 'a';
	else if (contido(cTmp,"ÃÂÁÀ"))
	  cTmp = 'A';
	else if (contido(cTmp,"êéè"))
	  cTmp = 'e';
    else if (contido(cTmp,"ÊÉÈ"))
  	  cTmp = 'E';
    else if (contido(cTmp,"îíì"))
      cTmp = 'i';
	else if (contido(cTmp,"ÎÍÌ"))
  	  cTmp = 'I';
    else if (contido(cTmp,"õôóò"))
  	  cTmp = 'o';
	else if (contido(cTmp,"ÕÔÓÒ"))
	  cTmp = 'O';
	else if (contido(cTmp,"ûúù"))
	  cTmp = 'u';
	else if (contido(cTmp,"ÛÚÙ"))
	  cTmp = 'U';
    else if (contido(cTmp,"ç"))
  	  cTmp = 'c';
	else if (contido(cTmp,"Ç")) 
  	  cTmp = 'C';
    return cTmp;
  }
  public static boolean contido(char cTexto, String sTexto) {
	boolean bRetorno = false;
	for (int i=0 ; i<sTexto.length() ; i++) {
	  if (cTexto==sTexto.charAt(i)) {
	    bRetorno = true;
	    break;
	  }
	}
    return bRetorno;
  }  
  public static String adicionaEspacos(String sTexto,int iTamanho) {
    int iTamIni = 0;
    String sRetorno = "";    
    if (sTexto==null)
        sTexto = "";
    iTamIni = sTexto.length();
    if (iTamIni>iTamanho) {
       sRetorno = sTexto.substring(0,iTamanho);
    }
    else {
       sRetorno = sTexto;
       for ( int i=iTamIni; i<iTamanho; i++) {
         sRetorno = sRetorno + ' ';
       }
    }
    return sRetorno;
  }
  public static String alinhaDir(int iValor,int iTam) {
  	return alinhaDir(""+iValor,iTam);
  }
  public static String alinhaDir(String sVal,int iTam) {
  	if (sVal != null)
  		sVal = sVal.trim();
    else
    	sVal = "";
  	int iTamStr = sVal.length();
  	if (iTamStr <= iTam) {
  		sVal = replicate(" ",iTam - iTamStr) + sVal;
  	}
  	return sVal;
  }
  public static String transValor(String sValor,int iTam, int iDec, boolean bZeroEsq) {
    if (sValor==null) {
      sValor = "0";
    }
//    System.out.println("Antes de converter: "+sValor);
    String sDec = "";
    String sResult = sValor;
    for (int i=0; i<sValor.length(); i++) {
       if ((sValor.substring(i,i+1).equals(".")) | (sValor.substring(i,i+1).equals(","))) {
         sResult = sValor.substring(0,i);
         sDec = sValor.substring(i+1,sValor.length());
         if (sDec.length()<iDec) {
//           System.out.println("sDec e menor que idec");
           sDec = sDec + replicate("0",iDec-sDec.length());      
         }
         else if (sDec.length()>iDec) {
//           System.out.println("sDec e maior que idec");
           sDec = sDec.substring(0,iDec);
         }
         break;
       }
    }
    
    if ((sDec.trim().equals("")) & (iDec>0)) {
       sDec = replicate("0",iDec);
    }
    if (sResult.length()>(iTam-iDec)) {
       sResult = sResult.substring(sResult.length()-(iTam-iDec)-1,(iTam-iDec));
    }
    if (bZeroEsq) {
      if (sResult.length()<(iTam-iDec))
        sResult = replicate("0",(iTam-iDec)-sResult.length())+sResult;
    }
  //  System.out.println("Depois de converter: "+sResult+sDec);

    return sResult+sDec;  
  }
  public static void setImpAtrib(PrintRequestAttributeSet p) {
	HashPrintRequestAttributeSet pra = (HashPrintRequestAttributeSet)p; 
	File fArq = new File("impres.cfg");
	try {
  	  if (!fArq.exists())
  		if (!fArq.createNewFile())
		  return;
	  FileOutputStream foArq = new FileOutputStream(fArq);
	  ObjectOutputStream obj = new ObjectOutputStream(foArq);
	  obj.writeObject(pra);
	  obj.flush();
	  foArq.close();	  
    }
    catch(IOException err) {
		err.printStackTrace();
    }
  }
  public static PrintRequestAttributeSet getImpAtrib() {
	HashPrintRequestAttributeSet pra = new HashPrintRequestAttributeSet(); 
	File fArq = new File("impres.cfg");
	try {
	  if (!fArq.exists())
	    return pra;
	  FileInputStream foArq = new FileInputStream(fArq);
	  ObjectInputStream obj = new ObjectInputStream(foArq);
	  pra = (HashPrintRequestAttributeSet)obj.readObject();
	  foArq.close();	  
	}
	catch(Exception err) {
		err.printStackTrace(); 
	}
	return pra;
  }
  public static Component getOwnerTela(Component comp) {
	Component cFrame = null;
	Component cRetorno = null;
	cFrame = comp.getParent();
	if (cFrame!=null) {
	  for (int i=1; 1<=10 ; i++) {
		if ( (cFrame instanceof JFrame) || (cFrame instanceof JInternalFrame) || 
			 (cFrame instanceof JDialog) ) {
		  cRetorno = cFrame;
		  break;      
	    }
	    cFrame = cFrame.getParent(); 
	  }
    }
    return cRetorno;
  }
  public static Vector stringToVector(String sTexto) {
    Vector vRetorno = new Vector();
    String sLinha = "";
    char c10 = (char) 10;
    char c13 = (char) 13;
    if (sTexto!=null) {
       int iPos = sTexto.indexOf(c13);
       int iPosIni = 0;
       int iTam = sTexto.length();
       if (iPos==-1) iPos = sTexto.indexOf(c10);
       if ( (iPos==-1) & (sTexto!=null) ) vRetorno.addElement(sTexto);
       while (iPos>=0) {
//	  System.out.println("PosIni: "+iPosIni);
//	  System.out.println("Pos: "+iPos);
          sLinha = sTexto.substring(iPosIni,iPos);
          vRetorno.addElement(sLinha);
          iPosIni = iPos+1;
          if ( (iPosIni) < iTam ) {
             if (sTexto.charAt(iPosIni)==c10) iPosIni++;
          }
          iPos = sTexto.indexOf(c13,iPosIni);
          if (iPos==-1) iPos = sTexto.indexOf(c10,iPosIni);
          if ( (iPos==-1) & (iTam>iPosIni) ) {
             sLinha = sTexto.substring(iPosIni);
             vRetorno.addElement(sLinha);
             break;
          }
       }
    }
    return vRetorno;
  }
  public static String tiraChar(String sVal,String sChar) {
    String sRetorno = sVal;
    sVal = sVal.trim();
    int iPos = sVal.indexOf(sChar);
    if (iPos >= 0) {
      if (iPos < (sVal.length()-1))
        sRetorno = sVal.substring(0,iPos)+sVal.substring(iPos+1);
      else
        sRetorno = sVal.substring(0,iPos);
    }
    return sRetorno;
  }
  
  public static String verData(String sData) {
    if (sData.length() < 10) {
      return "";
    }
    char cDate[] = sData.toCharArray();
    if (!Character.isDigit(cDate[0]))
      return "";
    else if (!Character.isDigit(cDate[1]))
      return "";
    else if (cDate[2] != '/')
      return "";
    else if (!Character.isDigit(cDate[3]))
      return "";
    else if (!Character.isDigit(cDate[4]))
      return "";
    else if (cDate[5] != '/')
      return "";
    else if (!Character.isDigit(cDate[6]))
      return "";
    else if (!Character.isDigit(cDate[7]))
      return "";
    else if (!Character.isDigit(cDate[8]))
      return "";
    else if (!Character.isDigit(cDate[9]))
      return "";
    else if (!validaData(sData))
      return "";
    return sData;
  }
  
  /**
   * Retorna o path para um arquivo que será criado
   * radomicamento no diretório TEMP.
   * @return - Path para o arquivo.
   */
  public static String arquivoTemp() {
	int iNum = (new Random()).nextInt();
	return Aplicativo.strTemp+(""+iNum).substring(0,8)+".tmp";
  }
  
  
  /**
   * Retorna um File já verificado que
   * foi selecionado pelo usuário.
   * @return - Ponteiro do arquivo.
   * @see #criaArqTemp(String)
   */
  public static File buscaArq(Component pai,String sExt) {
    File  fRet = null;
    JFileChooser fc = new JFileChooser();
    fc.setFileFilter(Funcoes.getFiler(new String[] {sExt}));
    fc.setAcceptAllFileFilterUsed(false);
    if (fc.showSaveDialog(pai) == JFileChooser.APPROVE_OPTION) {
        fRet = fc.getSelectedFile();
        if (!fRet.getPath().trim().equals("")) {
            if (fRet.getPath().indexOf(".")==-1) {
                fRet = new File(fRet.getPath().trim()+"."+sExt);
            }
        }
    }
    try {
        if (fRet.exists()) {
          if (Funcoes.mensagemConfirma(pai,"Arquivo já existe, sobrescrever?") != JOptionPane.YES_OPTION)
              return null;
        }
        else
          fRet.createNewFile();
    }
    catch(IOException err) {
      Funcoes.mensagemErro(pai,"Erro ao verificar o arquivo!\n"+err.getMessage());
      err.printStackTrace();
    }
    return fRet;
  }

  public static FileFilter getFiler(final String sExts[]) {
  	return new FileFilter() {
  		public boolean accept(File fArq) {
  			boolean bRet = false;
  			if (fArq.isDirectory())
  				return true;
  			String sExt = getExt(fArq);
  			for(int i=0;i<sExts.length;i++)
  			  if (sExt.equals(sExts[i]))
  				 bRet = true;
  			return bRet;
  		}
  		public String getDescription() {
  			String sVal = "";
  			String sVirg = "";
			for (int i=0;i<sExts.length;i++) {
				sVal += sVirg+sExts[i];
				sVirg = ",";
			}
  			return "Arquivo(s): "+sVal;
  		}
  		public String getExt(File f) {
  			String ext = "";
  			String s = f.getName();
  			int i = s.lastIndexOf('.');

  			if (i > 0 &&  i < s.length() - 1) {
  				ext = s.substring(i+1).toLowerCase();
  			}
  			return ext;
  		}
  	};
  }
  
  /**
   * Retorna um File que foi gerado
   * automáticamente no diretório temporário.
   * @return - Ponteiro do arquivo.
   * @see #criaArqTemp(String)
   */
  public static File criaArqTemp() {
	return criaArqTemp("tmp");
  }
  
  /**
   * Retorna um File que foi gerado
   * automáticamente no diretório temporário.
   * Este arquivo será excluido automáticamente
   * quando o java for finalizado.
   * @return - Ponteiro do arquivo.
   */
  public static File criaArqTemp(String sExt) {
  	File fRet = null;
	try {
	    fRet = File.createTempFile("arq","."+sExt);
	}
	catch (IOException err) {
		JOptionPane.showMessageDialog(null,"Erro ao criar arquivo temporário!!"+err.getMessage());
	}
	fRet.deleteOnExit();
	return fRet;
  }
  public static Date[] periodoMes(int iMes,int iAno) {
  	Date[] dRets = new Date[2];
  	GregorianCalendar cal = new GregorianCalendar(iAno,iMes,1);
  	dRets[0] = cal.getTime();
  	cal.set(Calendar.DAY_OF_MONTH,cal.getMaximum(Calendar.DAY_OF_MONTH));
  	dRets[1] = (cal.getTime());
  	return dRets;
  }
  
  public static String binToStr(byte[] byteData) {
    if (byteData == null)  return  null;
    int iSrcIdx;      // index into source (byteData)
    int iDestIdx;     // index into destination (byteDest)
    byte byteDest[] = new byte[((byteData.length+2)/3)*4];

    for (iSrcIdx=0, iDestIdx=0; iSrcIdx < byteData.length-2; iSrcIdx += 3) {
      byteDest[iDestIdx++] = (byte) ((byteData[iSrcIdx] >>> 2) & 077);
      byteDest[iDestIdx++] = (byte) ((byteData[iSrcIdx+1] >>> 4) & 017 |
										  (byteData[iSrcIdx] << 4) & 077);
      byteDest[iDestIdx++] = (byte) ((byteData[iSrcIdx+2] >>> 6) & 003 |
										  (byteData[iSrcIdx+1] << 2) & 077);
      byteDest[iDestIdx++] = (byte) (byteData[iSrcIdx+2] & 077);

      if (iSrcIdx < byteData.length) {
        byteDest[iDestIdx++] = (byte) ((byteData[iSrcIdx] >>> 2) & 077);
        if (iSrcIdx < byteData.length-1) {
          byteDest[iDestIdx++] = (byte) ((byteData[iSrcIdx+1] >>> 4) & 017 |
 											 (byteData[iSrcIdx] << 4) & 077);
          byteDest[iDestIdx++] = (byte) ((byteData[iSrcIdx+1] << 2) & 077);
        }
        else
          byteDest[iDestIdx++] = (byte) ((byteData[iSrcIdx] << 4) & 077);
      }

      for (iSrcIdx = 0; iSrcIdx < iDestIdx; iSrcIdx++) {
        if      (byteDest[iSrcIdx] < 26)  byteDest[iSrcIdx] = (byte)(byteDest[iSrcIdx] + 'A');
        else if (byteDest[iSrcIdx] < 52)  byteDest[iSrcIdx] = (byte)(byteDest[iSrcIdx] + 'a'-26);
        else if (byteDest[iSrcIdx] < 62)  byteDest[iSrcIdx] = (byte)(byteDest[iSrcIdx] + '0'-52);
        else if (byteDest[iSrcIdx] < 63)  byteDest[iSrcIdx] = '+';
        else                              byteDest[iSrcIdx] = '/';
      }

      for ( ; iSrcIdx < byteDest.length; iSrcIdx++)
         byteDest[iSrcIdx] = '=';

    }  

    return new String(byteDest);
  }
  public static String arredondaData(String sData) {
  	if (sData.length() < 10)
  		return "";
  	int iDia = 0;
    int iMes = 0;
    int iAno = 0;
    int ano = Integer.parseInt(sData.substring(6,10));
    int mes = Integer.parseInt(sData.substring(3,5));
    int dia = Integer.parseInt(sData.substring(0,2));
    if (mes > 12)
      mes = 13;
    if (dia > 32)
      dia = 32;
    GregorianCalendar cal  = new GregorianCalendar(ano,mes-1,dia);
      
    if (mes != (cal.get(Calendar.MONTH)+1)) {
      cal.set(Calendar.DAY_OF_MONTH,0);
      iDia = cal.get(Calendar.DAY_OF_MONTH);
      iMes = cal.get(Calendar.MONTH)+1;
      iAno = cal.get(Calendar.YEAR);
    }
    return strZero(""+iDia,2)+"/"+strZero(""+iMes,2)+"/"+strZero(""+iAno,4);
  }
  public static boolean validaData(String data) {
    boolean retorno = true;
    GregorianCalendar cal = null;
    if (data.length() < 10)
    	return false;
    int ano = Integer.parseInt(data.substring(6,10));
    int mes = Integer.parseInt(data.substring(3,5));
    int dia = Integer.parseInt(data.substring(0,2));

    cal = new GregorianCalendar(ano,mes-1,dia);
    
    if ((mes > 12) | (ano == 0))
      retorno = false;
    else if ((mes != (cal.get(Calendar.MONTH)+1)) | (dia == 0)) {
      retorno = false;
    }
    else if ((ano != (cal.get(Calendar.YEAR))) | (mes == 0))
      retorno = false;
    return retorno;   
  }

  public static String timeStampToStrDate(Timestamp tVal) {
  	GregorianCalendar cal = new GregorianCalendar();
  	cal.setTime(tVal);
    String sRetorno = strZero(""+cal.get(Calendar.DATE),2);
    sRetorno += "/"+strZero(""+(cal.get(Calendar.MONTH)+1),2);
    sRetorno += "/"+(cal.get(Calendar.YEAR));
    return sRetorno;
  }
  public static java.sql.Date dateToSQLDate(Date d) {
    return new java.sql.Date(d.getTime());
  }
  public static Date sqlDateToDate(java.sql.Date dVal) {
    Date dRetorno = new Date(dVal.getTime());
    return dRetorno;
  }
  public static GregorianCalendar sqlDateToGregorianCalendar(java.sql.Date dVal) {
    GregorianCalendar cal = new GregorianCalendar();
    cal.setTimeInMillis(dVal.getTime());
    return cal;
  }

  public static String[] strToStrArray(String sVals) {
  	int iConta = 0;
  	int iPos=0;
  	if (sVals != null) {
  		while (iPos >= 0) {
  			iPos = sVals.indexOf('\n',iPos+1);
  			iConta++;
  		}
  	}
  	return strToStrArray(sVals,iConta);
  }

  public static String[] strToStrArray(String sVals,int iNumLinhas) {
    String[] sRetorno = new String[iNumLinhas];
    String sTemp = sVals != null ? sVals : "";
    int iPos;
    for (int i=0; i<iNumLinhas; i++) {
      iPos = sTemp.indexOf('\n');
      if (iPos >= 0) {
  	    sRetorno[i] = sTemp.substring(0,iPos);
        if (iPos+1 < sTemp.length())
          sTemp = sTemp.substring(iPos+1,sTemp.length());
        else
          sTemp = "";
      }
      else if ((iPos == -1) && (sTemp.length() == 0)) {
        sRetorno[i] = "";
      }
      else {
        sRetorno[i] = sTemp;
        sTemp = "";
      }        
    }
    return sRetorno;
  }

  public static String strTostrQuebra(String sVals,int iNumChar) {
  	String sRetorno = "";
  	int iPos = 0;
  	int iPosBranco = 0;
  	int iPosEnter = 0;
  	String sBusca = sVals;
  	String sResto = "";
  	if ( (sVals!=null) && (sVals.length()>iNumChar)){
  		while ((sBusca.length()>iNumChar) && ( ((sBusca.indexOf(" ")>iNumChar) || (sBusca.indexOf("\n")>iNumChar)) || ((sBusca.indexOf(" ")<0) || (sBusca.indexOf("\n")<0)) )) {	
  			iPos = 0;
  			iPosBranco = sBusca.lastIndexOf(" ");
  			iPosEnter = sBusca.lastIndexOf("\n");
  			if (((iPosBranco<iPosEnter) && (iPosBranco>0)) || ((iPosEnter<0) && (iPosBranco>0)) ){
  				iPos = iPosBranco; 
  			} 
  			else if (((iPosBranco>iPosEnter) || (iPosBranco==0)) && (iPosEnter>0)){
  				iPos = iPosEnter;	
  			}
  			if ( (iPosEnter == iPosBranco) || (iPos>iNumChar) || (iPos==0) ){
  				iPos = iNumChar;
  			}
  			if (iPos>iNumChar) {
  			  sResto = sBusca.substring(0,iPos);	        
  			  sBusca = sBusca.substring(iPos,sBusca.length());	  			
  			  sRetorno += sResto+" ";
  			}
  			else {
  			  sRetorno=sVals;
  			  sBusca = "";
  			}
  		}
  		sRetorno += sBusca;
  	}
  	else {
  		return sBusca;
  	}
  	return sRetorno; 
  }
  
    
  public static Vector strToVectorSilabas(String sVals,int iNumColunas) { 
  	sVals = strTostrQuebra(sVals,iNumColunas);
  	Vector vRetorno = new Vector(); 
  	String[] sSeparadaPorEnter = null;
  	String sLinhaAnt = "";
  	String sLinhaNova = "";
  	String sResto = "";
  	String sLinhaCortada = "";
  	if (sVals!=null) {
  	  sSeparadaPorEnter = strToStrArray(sVals);  	
  	  for (int i=0; sSeparadaPorEnter.length>i; i++) {
  	    if (!sResto.trim().equals(""))
  	  	  sLinhaAnt = sResto+" "+sSeparadaPorEnter[i];
  	    else
  	      sLinhaAnt = sSeparadaPorEnter[i];
  	    if (sLinhaAnt.length()>iNumColunas) { 
  	      sLinhaCortada = sLinhaAnt.substring(0,iNumColunas<sLinhaAnt.length()?iNumColunas:sLinhaAnt.length()); 
  	      if (sLinhaCortada.lastIndexOf(" ")>0) 
  	        sLinhaNova = sLinhaCortada.substring(0,sLinhaCortada.lastIndexOf(' ')); 
          else
          	sLinhaNova = sLinhaCortada.substring(0,iNumColunas);
          
  	      sResto = sLinhaAnt.substring(sLinhaNova.length());
  	      
  	    }
  	    else {
  	      sLinhaNova = sLinhaAnt;  	  
          sResto = "";
  	    } 	    	
        sLinhaNova = (sLinhaNova.replaceAll(" +"," "));
        if (sLinhaNova.indexOf(" ")==0)
          vRetorno.addElement(sLinhaNova.substring(1));
        else
          vRetorno.addElement(sLinhaNova);        
  	  }  	  	  	  
      if (sResto.length()>0){
      	while (sResto.length()>0){ 
          sLinhaAnt = sResto;
          sLinhaCortada = sLinhaAnt.substring(0,iNumColunas<sLinhaAnt.length()?iNumColunas:sLinhaAnt.length());
          if (sLinhaAnt.length()>iNumColunas){
            if (sLinhaCortada.lastIndexOf(" ")>0) {
          	  sLinhaNova = sLinhaCortada.substring(0,sLinhaCortada.lastIndexOf(' '));
            }
          	else if(sLinhaCortada.length()>iNumColunas)
              sLinhaNova = sLinhaCortada.substring(0,iNumColunas);
          	else sLinhaNova = sLinhaCortada;
          	
            sResto = sLinhaAnt.substring(sLinhaNova.length());
            
            sLinhaNova = (sLinhaNova.replaceAll(" +"," "));
            if (sLinhaNova.indexOf(" ")==0)
            	vRetorno.addElement(sLinhaNova.substring(1));
            else
            	vRetorno.addElement(sLinhaNova);        
            
          }
          else {
          	sLinhaCortada = (sLinhaCortada.replaceAll(" +"," "));
          	if (sLinhaCortada.indexOf(" ")==0)
          		vRetorno.addElement(sLinhaCortada.substring(1));
          	else {
          	  vRetorno.addElement(sLinhaCortada);                  	          	
          	}
            sResto = "";
          }	
      	}      	
      }
  	}  	
  	return vRetorno;
  }
  
  
  public static String sqlDateToStrDate(java.sql.Date d) {
  	if (d == null)
  	  return "";
  	GregorianCalendar cal = new GregorianCalendar();
  	cal.setTime(d);
  	int iDia = cal.get(Calendar.DAY_OF_MONTH);
  	int iMes = cal.get(Calendar.MONTH)+1;
  	int iAno = cal.get(Calendar.YEAR);
    return strZero(""+iDia,2)+"/"+strZero(""+iMes,2)+"/"+iAno;
  }
  public static java.sql.Date strDateToSqlDate(String sVal) {
    GregorianCalendar cal = new GregorianCalendar();
    if (sVal.trim().length() == 10) {
      sVal = sVal.trim();
      try {
        int iAno = Integer.parseInt(sVal.substring(6));
        int iMes = Integer.parseInt(sVal.substring(3,5))-1;
        int iDia = Integer.parseInt(sVal.substring(0,2));
        cal = new GregorianCalendar(iAno,iMes,iDia);
      }
      catch (Exception err) {
        cal = null;
      }
    }
    else 
      cal = null;
    if (cal == null)
      return null;
    return new java.sql.Date(cal.getTimeInMillis());
  }
  public static Date strDateToDate(String sVal) {
    GregorianCalendar cal = new GregorianCalendar();
    if (sVal.trim().length() == 10) {
      sVal = sVal.trim();
      try {
        int iAno = Integer.parseInt(sVal.substring(6));
        int iMes = Integer.parseInt(sVal.substring(3,5))-1;
        int iDia = Integer.parseInt(sVal.substring(0,2));
        cal = new GregorianCalendar(iAno,iMes,iDia);
      }
      catch (Exception err) {
        cal = null;
      }
    }
    else 
      cal = null;
    if (cal == null)
      return null;
    return cal.getTime();
  }
  public static String dateToStrDataHora(Date dVal) {
	GregorianCalendar cal = new GregorianCalendar();
	cal.setTime(dVal);
	int iAno = cal.get(Calendar.YEAR);
	int iMes = cal.get(Calendar.MONTH)+1;
	int iDia = cal.get(Calendar.DAY_OF_MONTH);
	int iHora = cal.get(Calendar.HOUR_OF_DAY);
	int iMinuto = cal.get(Calendar.MINUTE);
	int iSegundo = cal.get(Calendar.SECOND);
	return strZero(""+iDia,2)+"/"+
	       strZero(""+iMes,2)+"/"+
	       iAno+" - "+
	       Funcoes.strZero(""+iHora,2)+":"+
	       Funcoes.strZero(""+iMinuto,2)+":"+
	       Funcoes.strZero(""+iSegundo,2);
  }
  public static String dateToStrDate(Date dVal) {
  	GregorianCalendar cal = new GregorianCalendar();
  	cal.setTime(dVal);
    int iAno = cal.get(Calendar.YEAR);
    int iMes = cal.get(Calendar.MONTH)+1;
    int iDia = cal.get(Calendar.DAY_OF_MONTH);
    return strZero(""+iDia,2)+"/"+strZero(""+iMes,2)+"/"+iAno;
  }
  public static String strDateToStrDB(String sVal) {
    String sRet = "";
    if (sVal.trim().length() == 10) {
      sVal = sVal.trim();
      try {
        int iAno = Integer.parseInt(sVal.substring(6));
        int iMes = Integer.parseInt(sVal.substring(3,5));
        int iDia = Integer.parseInt(sVal.substring(0,2));
        sRet = iAno+"-"+iMes+"-"+iDia;
      }
      catch (Exception err) {
        sRet = "";
      }
    }
    else 
      sRet = "";
    return sRet;
  }
  public static String dateToStrDB(Date d) {
  	GregorianCalendar cal = new GregorianCalendar();
  	cal.setTime(d);
    String sDia =""+cal.get(Calendar.DAY_OF_MONTH);
    String sMes =""+(cal.get(Calendar.MONTH)+1);
    String sAno =""+cal.get(Calendar.YEAR);
    return sAno+"-"+sMes+"-"+sDia;
  }
  public static Timestamp dateToTimestamp(Date d) {
    return new Timestamp(d.getTime());
  }
  public static BigDecimal strCurrencyToBigDecimal(String sVal) {
    BigDecimal bigRetorno = new BigDecimal("0");
    if (sVal == null)
      return new BigDecimal("0");
    int iPosPonto = sVal.indexOf('.');
    if (iPosPonto > -1) 
      sVal = sVal.substring(0,iPosPonto)+sVal.substring(iPosPonto+1);
    char[] cVal = sVal.toCharArray();
    int iPos = sVal.indexOf(",");
    if (iPos >= 0)
      cVal[iPos] = '.';
    sVal = new String(cVal);
    try {
      bigRetorno = new BigDecimal(sVal.trim());
    }
    catch (Exception err) { }
    return bigRetorno;
  }
  public static double strCurrencyToDouble(String sVal) {
  	if (sVal == null)
  		return 0;
	sVal = sVal.replace(getPontoDec(),'.');
	int iPos = sVal.lastIndexOf('.');
    int iPosTmp = -1;
    if (iPos >= 0) {
      while (iPos != (iPosTmp = sVal.indexOf('.'))) {//Tirando os pontos de milhar.
        	sVal = sVal.substring(0,iPosTmp) + sVal.substring(iPosTmp+1);
			iPos--;
      }
    }
    return Double.parseDouble(sVal);
  }
  /**
   * Aplica uma mascara sobre uma string. <BR>
   * A mascara funciona da seguinte forma: <BR>
   * Uma string contém a mascara, esta string <BR>
   * será a matriz para transformação, esta matriz <BR>
   * é composta de caracteres mas um caracter em especial<BR>
   * o '#' que serve para busca de seu respectivo valor bruto. <BR>
   * Por exemplo a mascara a seguir: <BR>
   * "###.###,##". <BR>
   * Se aplicarmos esta marcara sobre a string: <BR>
   * "1022000" <BR>
   * O Resultado será: "10.220,00".
   * @param sVal - String com o valor 'bruto'.
   * @param sMasc - String com a mascara.
   * @return Rotorna o valor bruto mascarado.
   */
  public static String setMascara(String sVal,String sMasc) {
    if (sVal == null)
      return "";
    String texto = "";
    sVal = sVal.trim();
    int i2 = 0;
    if ((sVal.length() > 0) & (sMasc.length() > 0) & 
        (sMasc.length() > sVal.length())){
      char[] va = sVal.toCharArray();
      char[] ma = sMasc.toCharArray();
      for (int i = 0; i < sVal.length(); i++) {
        if (ma[i2] == '#') {
          texto += va[i];
        }  
        else {
          texto += ma[i2];
          texto += va[i];
          i2++;
        }
        i2++;
      }
    }
    else
      texto = sVal;
    return texto;
  }
  public static BigDecimal strDecimalToBigDecimal( int iDec, String sVal) {
          BigDecimal bigRet = new BigDecimal(sVal);
          bigRet = bigRet.setScale(iDec);
          return bigRet;
  }
  public static String strDecimalToStrCurrency( int iTam, int iDec, String sVal) {
    if (sVal == null)
      return replicate(" ",iTam);

    sVal = strDecimalToStrCurrency(iDec,sVal);
	sVal = replicate(" ",iTam - sVal.length())+sVal;      
    return sVal;
  }
  public static StringDireita strDecimalToStrCurrencyd( int iDec, String sVal) {
	return new StringDireita(strDecimalToStrCurrency(iDec,sVal));
  }
  public static String strDecimalToStrCurrency( int iDec, String sVal) {
	String sRetorno = "";
	if (sVal == null || sVal.trim().equals("")) 
		return sRetorno;
	int iPonto = sVal.indexOf('.');
	char[] cVal = sVal.toCharArray();
	int iCont = (iDec+1)*-1;
	if (iPonto != -1) {
	  cVal[iPonto] = ',';
	  sVal = new String(cVal);
	  sVal += replicate("0",iDec-(sVal.length()-(iPonto+1)));
	  sVal = sVal.substring(0,iPonto+1+iDec);
	  cVal = sVal.toCharArray();
	  for (int i=(sVal.length()-1); i>=0; i--) {
		if (iCont == 3) {
		  if (cVal[i] == '-')
			sRetorno = cVal[i]+sRetorno;
		  else 
			sRetorno = cVal[i]+"."+sRetorno;
		  iCont = 1;
		}
		else {
		  sRetorno = cVal[i]+sRetorno;
		  iCont ++;
		}
	  }
	}
	else {
	  sRetorno = sVal+','+replicate("0",iDec);
	}
	if (iDec == 0) {
	  sRetorno = sRetorno.substring(0,sRetorno.length()-1);
	}
	return sRetorno;
  }
  public static void criaTelaErro(String sErro) {
    dlErro = new JDialog();
    dlErro.setSize(600,350);
    dlErro.setLocationRelativeTo(dlErro);
    dlErro.setTitle("Relatório de Erros");
    Container c = dlErro.getContentPane();
    JPanel pnRod = new JPanel();
    JPanel pnOK = new JPanel();
    JTextArea txt = new JTextArea(sErro);
    txt.setEditable(false);
    JScrollPane spnTxt = new JScrollPane(txt);
    JButton btOK = new JButton("OK");
    c.setLayout(new BorderLayout());
    c.add(pnRod, BorderLayout.SOUTH);
    pnRod.setPreferredSize(new Dimension(400,40));
    btOK.setPreferredSize(new Dimension(90,30));
    pnRod.add(pnOK);
    pnOK.add(btOK);
    c.add(spnTxt,BorderLayout.CENTER);
    btOK.addActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
          dlErro.dispose();
        }
      }
    );
    dlErro.setModal(true);
    dlErro.show();
  }
  public static void criaTelaErro(String sErro, Component cOrig) {
  	FFDialogo dlErro = new FFDialogo(cOrig);
  	dlErro.setAtribos(100,100,600,350);
  	dlErro.setTitle("Relatório de Erros");
  	dlErro.setToFrameLayout();
  	JTextArea txt = new JTextArea(sErro);
  	JScrollPane spnTxt = new JScrollPane(txt);
  	dlErro.setPanel(spnTxt);
  	txt.setEditable(false);
  	dlErro.setVisible(true);
  	dlErro.dispose();
  }
  public static String strZero(String val, int zeros) {
    if (val == null)
        return val;
    String sRetorno = replicate("0",zeros-val.trim().length());
    sRetorno += val.trim();
    return sRetorno;
  }
  public static boolean ValidaCNPJ (String sDado) {
    byte[] D1 = {0,1,2,3,4,5,6,7,8,9,10,11,12};
    int DF1, DF2, DF3, DF4, DF5, DF6, Resto1, Resto2, PrimeiroDigito, SegundoDigito;
    String sConvert = " "+sDado.trim();
    char[] Dado = sConvert.toCharArray();
    boolean bRetorno = true;
    if (sDado.trim().length() == 14) {
      for (int i = 1; i <= 12; i++)
        if (Character.isDigit(Dado[i])) 
          D1[i] = Byte.parseByte(""+Dado[i]);
        else 
          bRetorno = false;
      if (bRetorno) {
        DF1 = 5*D1[1] + 4*D1[2] + 3*D1[3] + 2*D1[4] + 9*D1[5] + 8*D1[6] +
              7*D1[7] + 6*D1[8] + 5*D1[9] + 4*D1[10] + 3*D1[11] + 2*D1[12];
        DF2 = DF1 / 11;
        DF3 = DF2 * 11;
        Resto1 = DF1 - DF3;
        if ((Resto1 == 0) | (Resto1 == 1)) 
          PrimeiroDigito = 0;
        else 
          PrimeiroDigito = 11 - Resto1;
        DF4 = 6*D1[1] + 5*D1[2] + 4*D1[3] + 3*D1[4] + 2*D1[5] + 9*D1[6] +
              8*D1[7] + 7*D1[8] + 6*D1[9] + 5*D1[10] + 4*D1[11] + 3*D1[12] +
              2*PrimeiroDigito;
        DF5 = DF4 / 11;
        DF6 = DF5 * 11;
        Resto2 = DF4 - DF6;
        if ((Resto2 == 0) | (Resto2 == 1))
          SegundoDigito = 0;
        else 
          SegundoDigito = 11- Resto2;
        if ((PrimeiroDigito != Integer.parseInt(""+Dado[13])) |
            (SegundoDigito != Integer.parseInt(""+Dado[14])))
           bRetorno = false;
      }
    }
    else 
      if (sDado.trim().length() != 0)
        bRetorno = false;
    return bRetorno;
  }
  public static boolean ValidaCPF(String sDado) {
    byte[] D1 = {0,1,2,3,4,5,6,7,8,9};
    int DF1, DF2, DF3, DF4, DF5, DF6, Resto1, Resto2, PrimeiroDigito, SegundoDigito;
    String sConvert = sDado.trim();
    char[] Dado = sConvert.toCharArray();
    boolean bRetorno = true;
    if (sDado.trim().length() == 11) {
      for (int i = 1; i <= 9; i++)
        if (Character.isDigit(Dado[i])) 
          D1[i] = Byte.parseByte(""+Dado[i-1]);
        else 
          bRetorno = false;
      if (bRetorno) {
        DF1 = 10*D1[1] + 9*D1[2] + 8*D1[3] + 7*D1[4] + 6*D1[5] + 
              5*D1[6] + 4*D1[7] + 3*D1[8] + 2*D1[9];
        DF2 = DF1 / 11;
        DF3 = DF2 * 11;
        Resto1 = DF1 - DF3;
        if ((Resto1 == 0) || (Resto1 == 1)) 
          PrimeiroDigito = 0;
        else 	
          PrimeiroDigito = 11 - Resto1;
        DF4 = 11*D1[1] + 10*D1[2] + 9*D1[3] + 8*D1[4] + 7*D1[5] + 
        6*D1[6] + 5*D1[7] + 4*D1[8] + 3*D1[9] + 2*PrimeiroDigito;				
        DF5 = DF4 / 11;
        DF6 = DF5 * 11;
        Resto2 = DF4 - DF6;
        if ((Resto2 == 0) || (Resto2 == 1))
          SegundoDigito = 0;
        else 
          SegundoDigito = 11- Resto2;
        if ((PrimeiroDigito != Integer.parseInt(Dado[9]+"")) ||
            (SegundoDigito != Integer.parseInt(Dado[10]+"")))
           bRetorno = false;
      }
    }
    else 
        if (sDado.trim().length() != 0)
          bRetorno = false;
    return bRetorno;
  }
  public static boolean vIE(String sIE, String sEstado) {
    boolean bRetorno = false;
    sEstado.toUpperCase();
    montaTabCalcIE();
    montaTabPesoIE();
    for (int i=1; i <= 3; i++) {
      if (testaDigIE(sEstado, sIE, i)) {
        bRetorno = true;
        break;
      }
    }
    return bRetorno;
  }
  private static boolean testaDigIE(String sEstado, String sIE, int iCaso) {
    boolean bRetorno = false;
    String sIE2 = "";
   
    for (int i=0; i < 34; i++) {
      if (((String)((Vector) vIE.elementAt(i)).elementAt(0)).equals(sEstado) &&
         ((Integer)((Vector) vIE.elementAt(i)).elementAt(1)).intValue() == iCaso) {
        sIE2 = sIE;
        if (testaCasoIE(sIE2,(Vector) vIE.elementAt(i))) {
          sIE = sIE2;
          bRetorno = true;
          break;
        }
      }
    }
    return bRetorno;
  }
  private static boolean testaCasoIE(String sIE, Vector vXIE) {
    String sIENova = "";
    int iSX, iSY, iSQiX, iSQiY, iPosDVX, iPosDVY; 
    int iDVX = -1;
    int iDVY = -1;
    int[] aMiX, aMiY, aQiX, aQiY; 
    int[] vPesoX, vPesoY;
    boolean bDVY = false;
    iPosDVX = carregaPosDV(vXIE)[0];
    iPosDVY = carregaPosDV(vXIE)[1];
    vPesoX = retPeso(vXIE,'X');
    if (iPosDVY > 0)
      bDVY = true;
    else
      bDVY = false;
    for (int i=0; i < sIE.length(); i++) {
      if (Character.isDigit(sIE.toCharArray()[i]))
        sIENova += sIE.toCharArray()[i];
    }
    if (sIENova.length() != ((Integer) vXIE.elementAt(2)).intValue())
      return false;
    for (int i=(24-sIENova.length()); i<24; i++) {
      if (retValUF((String) vXIE.elementAt(i)).indexOf((sIENova.toCharArray())[i -(24-sIENova.length())]) < 0)
        return false;
    }
//****** Irá calcular digitos verificadores !!!
    aMiX = new int[14];      
    aQiX = new int[14];      
    iSX = 0;
    iSQiX = 0;
    for (int i=0; i<sIENova.length(); i++) {
      if ((i != iPosDVX) & (i != iPosDVY)) {
        aMiX[i] = (Integer.parseInt(""+(sIENova.toCharArray())[i]))*vPesoX[i];        
        iSX += aMiX[i];
      }
    }
//    System.out.println("iSX: "+iSX);
    if (((String) vXIE.elementAt(4)).indexOf('A') == 0) {
      for (int i=(sIENova.length()-1); i>0; i--) {
        if ((i!=iPosDVX) & (i!=iPosDVY)) {
          aQiX[i] = aMiX[i] / 10;
          iSQiX += aQiX[i];
        }
      }
      iSX += iSQiX;
    }
    else if (((String) vXIE.elementAt(4)).indexOf('B') >= 0)
      iSX *= 10;
    else if (((String) vXIE.elementAt(4)).indexOf('C') >= 0)
      iSX += (5+(4*((Integer) vXIE.elementAt(3)).intValue()));
    if (((String) vXIE.elementAt(4)).indexOf('D') >= 0)
      iDVX = iSX % ((Integer) vXIE.elementAt(5)).intValue();
    else if (((String) vXIE.elementAt(4)).indexOf('E') >= 0) {
      iDVX = iSX % ((Integer) vXIE.elementAt(5)).intValue();
      iDVX = ((Integer) vXIE.elementAt(5)).intValue() - iDVX;
    }
    if (iDVX == 10)
      iDVX = 0;
    else if (iDVX == 11)
      iDVX = ((Integer) vXIE.elementAt(3)).intValue();
    if (bDVY) {
      vPesoY = retPeso(vXIE,'Y');
      aMiY = new int[14];      
      aQiY = new int[14];      
      iSY = 0;
      iSQiY = 0;
      for (int i=0; i<sIENova.length(); i++) {
        if (i != iPosDVY) {
          aMiY[i] = (Integer.parseInt(""+(sIENova.toCharArray())[i]))*vPesoY[i];        
          iSY += aMiY[i];
//          System.out.println("MI: "+Integer.parseInt(""+(sIENova.toCharArray())[i])+" X "+vPesoY[i]);
        }
      }
//     System.out.println("iSY: "+iSY);
      if (((String) vXIE.elementAt(7)).indexOf('A') >= 0) {
        for (int i=(sIENova.length()-1); i>0; i--) {
          if (i!=iPosDVY) {
            aQiY[i] = aMiY[i] / 10;
            iSQiY += aQiY[i];
          }
        }
        iSY += iSQiY;
      }
      else if (((String) vXIE.elementAt(7)).indexOf('B') >= 0)
        iSY *= 10;
      else if (((String) vXIE.elementAt(7)).indexOf('C') >= 0)
        iSY += (5+(4*((Integer) vXIE.elementAt(3)).intValue()));
      if (((String) vXIE.elementAt(7)).indexOf('D') >= 0)
        iDVY = iSY % ((Integer) vXIE.elementAt(8)).intValue();
      else if (((String) vXIE.elementAt(7)).indexOf('E') >= 0) {
        iDVY = iSY % ((Integer) vXIE.elementAt(8)).intValue();
        iDVY = ((Integer) vXIE.elementAt(8)).intValue() - iDVY;
      }
      if (iDVY == 10)
        iDVY = 0;
      else if (iDVY == 11)
        iDVY = ((Integer) vXIE.elementAt(3)).intValue();
    }
//      System.out.println(Integer.parseInt(""+(sIENova.toCharArray())[sIENova.length()-(14-iPosDVX)])+" = "+iDVX);
//    if (bDVY)
//      System.out.println(""+(sIENova.toCharArray())[sIENova.length()-(14-iPosDVY)]+" = "+iDVY);
    if (Integer.parseInt(""+(sIENova.toCharArray())[sIENova.length()-(14-iPosDVX)]) == iDVX) {
      if (bDVY) {
        if (Integer.parseInt(""+(sIENova.toCharArray())[sIENova.length()-(14-iPosDVY)]) != iDVY) {
          return false;
        }
      }
    }
    else {
      return false;
    }
    sIEValida = setMascara(sIENova,(String)vXIE.elementAt(24));
//    System.out.println("TRUE");
    return true;
  }
  private static int[] retPeso(Vector vXIE,char XY) {
    String sPeso = "";
    int[] aRetorno = new int[14];
    int tam = ((Integer) vXIE.elementAt(2)).intValue();
    String peso = "";
    if (XY == 'X') {
      sPeso = (String) vXIE.elementAt(6);
      for (int i=0; i<vPesoIE.size(); i++) {
        if (((String)((Vector) vPesoIE.elementAt(i)).elementAt(0)).compareTo(sPeso) == 0) {
          for(int i2=(15-tam); i2<=14; i2++) {
            aRetorno[i2-(15-tam)] = ((Integer)((Vector) vPesoIE.elementAt(i)).elementAt(i2)).intValue();
          }
          break;
        }
      }
    }
    else if (XY == 'Y') {
      sPeso = (String) vXIE.elementAt(9);
      for (int i=1; i<vPesoIE.size(); i++) {
        if (((String)((Vector) vPesoIE.elementAt(i)).elementAt(0)).compareTo(sPeso) == 0) {
          for(int i2=(15-tam); i2<=14; i2++) {
            aRetorno[i2-(15-tam)] = ((Integer)((Vector) vPesoIE.elementAt(i)).elementAt(i2)).intValue();
            peso += ((Vector) vPesoIE.elementAt(i)).elementAt(i2) + " ,";
          }
          break;
        }
      }
    }
//    System.out.println(peso);
    return aRetorno;
  }
  private static String retValUF(String sVal) {
    String sRetorno = "";
    if (sVal == null) 
        return "";
    char[] cVal = sVal.toCharArray();
    if (sVal.compareTo("0/9") == 0)
      sRetorno = "0123456789";
    else if (sVal.compareTo("DVX") == 0)
      sRetorno = "0123456789";
    else if (sVal.compareTo("DVY") == 0)
      sRetorno = "0123456789";
    else if (cVal[0] == '=') {
      sRetorno = sVal;
    }
    else if (cVal[1] == '/') {
      for (int i= cVal[0];i<=cVal[2]; i++) {
        sRetorno += i;
      }
    }
    return sRetorno;
  }
  private static int[] carregaPosDV(Vector vXIE) {
    int[] aRetorno = new int[2];
    aRetorno[0] = -1;
    aRetorno[1] = -1;
    for (int i=10; i<=24; i++) {
      if (vXIE.elementAt(i) != null) {
        if (((String)vXIE.elementAt(i)).compareTo("DVX") == 0) 
          aRetorno[0] = i-10;
        else if (((String)vXIE.elementAt(i)).compareTo("DVY") == 0)
          aRetorno[1] = i-10;
      }
    }
    return aRetorno;
  }
  private static void montaTabPesoIE() {
    
    Vector linha01 = new Vector();
    Vector linha02 = new Vector();
    Vector linha03 = new Vector();
    Vector linha04 = new Vector();
    Vector linha05 = new Vector();
    Vector linha06 = new Vector();
    Vector linha07 = new Vector();
    Vector linha08 = new Vector();
    Vector linha09 = new Vector();
    Vector linha10 = new Vector();
    Vector linha11 = new Vector();
    Vector linha12 = new Vector();
    Vector linha13 = new Vector();

    linha01.addElement("P1");
    linha01.addElement(new Integer(6));
    linha01.addElement(new Integer(5));
    linha01.addElement(new Integer(4));
    linha01.addElement(new Integer(3));
    linha01.addElement(new Integer(2));
    linha01.addElement(new Integer(9));
    linha01.addElement(new Integer(8));
    linha01.addElement(new Integer(7));
    linha01.addElement(new Integer(6));
    linha01.addElement(new Integer(5));
    linha01.addElement(new Integer(4));
    linha01.addElement(new Integer(3));
    linha01.addElement(new Integer(2));
    linha01.addElement(new Integer(0));
    vPesoIE.addElement(linha01);

    linha02.addElement("P2");
    linha02.addElement(new Integer(5));
    linha02.addElement(new Integer(4));
    linha02.addElement(new Integer(3));
    linha02.addElement(new Integer(2));
    linha02.addElement(new Integer(9));
    linha02.addElement(new Integer(8));
    linha02.addElement(new Integer(7));
    linha02.addElement(new Integer(6));
    linha02.addElement(new Integer(5));
    linha02.addElement(new Integer(4));
    linha02.addElement(new Integer(3));
    linha02.addElement(new Integer(2));
    linha02.addElement(new Integer(0));
    linha02.addElement(new Integer(0));
    vPesoIE.addElement(linha02);

    linha03.addElement("P3");
    linha03.addElement(new Integer(6));
    linha03.addElement(new Integer(5));
    linha03.addElement(new Integer(4));
    linha03.addElement(new Integer(3));
    linha03.addElement(new Integer(2));
    linha03.addElement(new Integer(9));
    linha03.addElement(new Integer(8));
    linha03.addElement(new Integer(7));
    linha03.addElement(new Integer(6));
    linha03.addElement(new Integer(5));
    linha03.addElement(new Integer(4));
    linha03.addElement(new Integer(3));
    linha03.addElement(new Integer(0));
    linha03.addElement(new Integer(2));
    vPesoIE.addElement(linha03);

    linha04.addElement("P4");
    linha04.addElement(new Integer(0));
    linha04.addElement(new Integer(0));
    linha04.addElement(new Integer(0));
    linha04.addElement(new Integer(0));
    linha04.addElement(new Integer(0));
    linha04.addElement(new Integer(1));
    linha04.addElement(new Integer(2));
    linha04.addElement(new Integer(3));
    linha04.addElement(new Integer(4));
    linha04.addElement(new Integer(5));
    linha04.addElement(new Integer(6));
    linha04.addElement(new Integer(7));
    linha04.addElement(new Integer(8));
    linha04.addElement(new Integer(0));
    vPesoIE.addElement(linha04);

    linha05.addElement("P5");
    linha05.addElement(new Integer(0));
    linha05.addElement(new Integer(0));
    linha05.addElement(new Integer(0));
    linha05.addElement(new Integer(0));
    linha05.addElement(new Integer(0));
    linha05.addElement(new Integer(1));
    linha05.addElement(new Integer(2));
    linha05.addElement(new Integer(3));
    linha05.addElement(new Integer(4));
    linha05.addElement(new Integer(5));
    linha05.addElement(new Integer(6));
    linha05.addElement(new Integer(7));
    linha05.addElement(new Integer(8));
    linha05.addElement(new Integer(0));
    vPesoIE.addElement(linha05);

    linha06.addElement("P6");
    linha06.addElement(new Integer(0));
    linha06.addElement(new Integer(0));
    linha06.addElement(new Integer(0));
    linha06.addElement(new Integer(9));
    linha06.addElement(new Integer(8));
    linha06.addElement(new Integer(0));
    linha06.addElement(new Integer(0));
    linha06.addElement(new Integer(7));
    linha06.addElement(new Integer(6));
    linha06.addElement(new Integer(5));
    linha06.addElement(new Integer(4));
    linha06.addElement(new Integer(3));
    linha06.addElement(new Integer(2));
    linha06.addElement(new Integer(0));
    vPesoIE.addElement(linha06);

    linha07.addElement("P7");
    linha07.addElement(new Integer(5));
    linha07.addElement(new Integer(4));
    linha07.addElement(new Integer(3));
    linha07.addElement(new Integer(2));
    linha07.addElement(new Integer(1));
    linha07.addElement(new Integer(9));
    linha07.addElement(new Integer(8));
    linha07.addElement(new Integer(7));
    linha07.addElement(new Integer(6));
    linha07.addElement(new Integer(5));
    linha07.addElement(new Integer(4));
    linha07.addElement(new Integer(3));
    linha07.addElement(new Integer(2));
    linha07.addElement(new Integer(0));
    vPesoIE.addElement(linha07);

    linha08.addElement("P8");
    linha08.addElement(new Integer(8));
    linha08.addElement(new Integer(7));
    linha08.addElement(new Integer(6));
    linha08.addElement(new Integer(5));
    linha08.addElement(new Integer(4));
    linha08.addElement(new Integer(3));
    linha08.addElement(new Integer(2));
    linha08.addElement(new Integer(7));
    linha08.addElement(new Integer(6));
    linha08.addElement(new Integer(5));
    linha08.addElement(new Integer(4));
    linha08.addElement(new Integer(3));
    linha08.addElement(new Integer(2));
    linha08.addElement(new Integer(0));
    vPesoIE.addElement(linha08);

    linha09.addElement("P9");
    linha09.addElement(new Integer(7));
    linha09.addElement(new Integer(6));
    linha09.addElement(new Integer(5));
    linha09.addElement(new Integer(4));
    linha09.addElement(new Integer(3));
    linha09.addElement(new Integer(2));
    linha09.addElement(new Integer(7));
    linha09.addElement(new Integer(6));
    linha09.addElement(new Integer(5));
    linha09.addElement(new Integer(4));
    linha09.addElement(new Integer(3));
    linha09.addElement(new Integer(2));
    linha09.addElement(new Integer(0));
    linha09.addElement(new Integer(0));
    vPesoIE.addElement(linha09);

    linha10.addElement("P10");
    linha10.addElement(new Integer(0));
    linha10.addElement(new Integer(1));
    linha10.addElement(new Integer(2));
    linha10.addElement(new Integer(1));
    linha10.addElement(new Integer(1));
    linha10.addElement(new Integer(2));
    linha10.addElement(new Integer(1));
    linha10.addElement(new Integer(2));
    linha10.addElement(new Integer(1));
    linha10.addElement(new Integer(2));
    linha10.addElement(new Integer(1));
    linha10.addElement(new Integer(2));
    linha10.addElement(new Integer(0));
    linha10.addElement(new Integer(0));
    vPesoIE.addElement(linha10);
 
    linha11.addElement("P11");
    linha11.addElement(new Integer(0));
    linha11.addElement(new Integer(3));
    linha11.addElement(new Integer(2));
    linha11.addElement(new Integer(11));
    linha11.addElement(new Integer(10));
    linha11.addElement(new Integer(9));
    linha11.addElement(new Integer(8));
    linha11.addElement(new Integer(7));
    linha11.addElement(new Integer(6));
    linha11.addElement(new Integer(5)); 
    linha11.addElement(new Integer(4));
    linha11.addElement(new Integer(3));
    linha11.addElement(new Integer(2));
    linha11.addElement(new Integer(0));
    vPesoIE.addElement(linha11);

    linha12.addElement("P12");
    linha12.addElement(new Integer(0));
    linha12.addElement(new Integer(0));
    linha12.addElement(new Integer(1));
    linha12.addElement(new Integer(3));
    linha12.addElement(new Integer(4));
    linha12.addElement(new Integer(5));
    linha12.addElement(new Integer(6));
    linha12.addElement(new Integer(7));
    linha12.addElement(new Integer(8));
    linha12.addElement(new Integer(10));
    linha12.addElement(new Integer(0));
    linha12.addElement(new Integer(0));
    linha12.addElement(new Integer(0));
    linha12.addElement(new Integer(0));
    vPesoIE.addElement(linha12);

    linha13.addElement("P13");
    linha13.addElement(new Integer(0));
    linha13.addElement(new Integer(0));
    linha13.addElement(new Integer(3));
    linha13.addElement(new Integer(2));
    linha13.addElement(new Integer(10));
    linha13.addElement(new Integer(9));
    linha13.addElement(new Integer(8));
    linha13.addElement(new Integer(7));
    linha13.addElement(new Integer(6));
    linha13.addElement(new Integer(5));
    linha13.addElement(new Integer(4));
    linha13.addElement(new Integer(3));
    linha13.addElement(new Integer(2));
    linha13.addElement(new Integer(0));
    vPesoIE.addElement(linha13);
  }
  private static void montaTabCalcIE() {
    Vector linha01 = new Vector();
    Vector linha02 = new Vector();
    Vector linha03 = new Vector();
    Vector linha04 = new Vector();
    Vector linha05 = new Vector();
    Vector linha06 = new Vector();
    Vector linha07 = new Vector();
    Vector linha08 = new Vector();
    Vector linha09 = new Vector();
    Vector linha10 = new Vector();
    Vector linha11 = new Vector();
    Vector linha12 = new Vector();
    Vector linha13 = new Vector();
    Vector linha14 = new Vector();
    Vector linha15 = new Vector();
    Vector linha16 = new Vector();
    Vector linha17 = new Vector();
    Vector linha18 = new Vector();
    Vector linha19 = new Vector();
    Vector linha20 = new Vector();
    Vector linha21 = new Vector();
    Vector linha22 = new Vector();
    Vector linha23 = new Vector();
    Vector linha24 = new Vector();
    Vector linha25 = new Vector();
    Vector linha26 = new Vector();
    Vector linha27 = new Vector();
    Vector linha28 = new Vector();
    Vector linha29 = new Vector();
    Vector linha30 = new Vector();
    Vector linha31 = new Vector();
    Vector linha32 = new Vector();
    Vector linha33 = new Vector();
    Vector linha34 = new Vector();
    
    linha01.addElement("AC");
    linha01.addElement(new Integer(1));
    linha01.addElement(new Integer(9));
    linha01.addElement(new Integer(0));
    linha01.addElement("E");
    linha01.addElement(new Integer(11));
    linha01.addElement("P1");
    linha01.addElement(null);
    linha01.addElement(null);
    linha01.addElement(null);
    linha01.addElement(null);
    linha01.addElement(null);
    linha01.addElement(null);
    linha01.addElement(null);
    linha01.addElement(null);
    linha01.addElement("=0");
    linha01.addElement("=1");
    linha01.addElement("0/9");
    linha01.addElement("0/9");
    linha01.addElement("0/9");
    linha01.addElement("0/9");
    linha01.addElement("0/9");
    linha01.addElement("0/9");
    linha01.addElement("DVX");
    linha01.addElement("#########");
    vIE.addElement(linha01);
    
    
    linha02.addElement("AC");
    linha02.addElement(new Integer(2));
    linha02.addElement(new Integer(13));
    linha02.addElement(new Integer(0));
    linha02.addElement("E");
    linha02.addElement(new Integer(11));
    linha02.addElement("P2");
    linha02.addElement("E");
    linha02.addElement(new Integer(11));
    linha02.addElement("P1");
    linha02.addElement(null);
    linha02.addElement("=0");
    linha02.addElement("=1");
    linha02.addElement("0/9");
    linha02.addElement("0/9");
    linha02.addElement("0/9");
    linha02.addElement("0/9");
    linha02.addElement("0/9");
    linha02.addElement("0/9");
    linha02.addElement("0/9");
    linha02.addElement("0/9");
    linha02.addElement("0/9");
    linha02.addElement("DVX");
    linha02.addElement("DVY");
    linha02.addElement("########/###-##");
    vIE.addElement(linha02);

    linha03.addElement("AL");
    linha03.addElement(new Integer(1));
    linha03.addElement(new Integer(9));
    linha03.addElement(new Integer(0));
    linha03.addElement("B,D");
    linha03.addElement(new Integer(11));
    linha03.addElement("P1");
    linha03.addElement(null);
    linha03.addElement(null);
    linha03.addElement(null);
    linha03.addElement(null);
    linha03.addElement(null);
    linha03.addElement(null);
    linha03.addElement(null);
    linha03.addElement(null);
    linha03.addElement("=2");
    linha03.addElement("=4");
    linha03.addElement("=0,3,5,7,8");
    linha03.addElement("0/9");
    linha03.addElement("0/9");
    linha03.addElement("0/9");
    linha03.addElement("0/9");
    linha03.addElement("0/9");
    linha03.addElement("DVX");
    linha03.addElement("#########");
    vIE.addElement(linha03);

    linha04.addElement("AP");
    linha04.addElement(new Integer(1));
    linha04.addElement(new Integer(9));
    linha04.addElement(new Integer(0));
    linha04.addElement("C,E");
    linha04.addElement(new Integer(11));
    linha04.addElement("P1");
    linha04.addElement(null);
    linha04.addElement(null);
    linha04.addElement(null);
    linha04.addElement(null);
    linha04.addElement(null);
    linha04.addElement(null);
    linha04.addElement(null);
    linha04.addElement(null);
    linha04.addElement("=0");
    linha04.addElement("=3");
    linha04.addElement("0/9");
    linha04.addElement("0/9");
    linha04.addElement("0/9");
    linha04.addElement("0/9");
    linha04.addElement("0/9");
    linha04.addElement("0/9");
    linha04.addElement("DVX");
    linha04.addElement("#########");
    vIE.addElement(linha04);
    
    linha05.addElement("AP");
    linha05.addElement(new Integer(2));
    linha05.addElement(new Integer(9));
    linha05.addElement(new Integer(1));
    linha05.addElement("C,E");
    linha05.addElement(new Integer(11));
    linha05.addElement("P1");
    linha05.addElement(null);
    linha05.addElement(null);
    linha05.addElement(null);
    linha05.addElement(null);
    linha05.addElement(null);
    linha05.addElement(null);
    linha05.addElement(null);
    linha05.addElement(null);
    linha05.addElement("=0");
    linha05.addElement("=3");
    linha05.addElement("0/9");
    linha05.addElement("0/9");
    linha05.addElement("0/9");
    linha05.addElement("0/9");
    linha05.addElement("0/9");
    linha05.addElement("0/9");
    linha05.addElement("DVX");
    linha05.addElement("#########");
    vIE.addElement(linha05);

    linha06.addElement("AP");
    linha06.addElement(new Integer(3));
    linha06.addElement(new Integer(9));
    linha06.addElement(new Integer(0));
    linha06.addElement("E");
    linha06.addElement(new Integer(11));
    linha06.addElement("P1");
    linha06.addElement(null);
    linha06.addElement(null);
    linha06.addElement(null);
    linha06.addElement(null);
    linha06.addElement(null);
    linha06.addElement(null);
    linha06.addElement(null);
    linha06.addElement(null);
    linha06.addElement("=0");
    linha06.addElement("=3");
    linha06.addElement("0/9");
    linha06.addElement("0/9");
    linha06.addElement("0/9");
    linha06.addElement("0/9");
    linha06.addElement("0/9");
    linha06.addElement("0/9");
    linha06.addElement("DVX");
    linha06.addElement("#########");
    vIE.addElement(linha06);
    
    linha07.addElement("AM");
    linha07.addElement(new Integer(1));
    linha07.addElement(new Integer(9));
    linha07.addElement(new Integer(0));
    linha07.addElement("E");
    linha07.addElement(new Integer(11));
    linha07.addElement("P1");
    linha07.addElement(null);
    linha07.addElement(null);
    linha07.addElement(null);
    linha07.addElement(null);
    linha07.addElement(null);
    linha07.addElement(null);
    linha07.addElement(null);
    linha07.addElement(null);
    linha07.addElement("=0");
    linha07.addElement("=4,7");
    linha07.addElement("0/9");
    linha07.addElement("0/9");
    linha07.addElement("0/9");
    linha07.addElement("0/9");
    linha07.addElement("0/9");
    linha07.addElement("0/9");
    linha07.addElement("DVX");
    linha07.addElement("##.###.###-#");
    vIE.addElement(linha07);
    
    linha08.addElement("BA");
    linha08.addElement(new Integer(1));
    linha08.addElement(new Integer(8));
    linha08.addElement(new Integer(0));
    linha08.addElement("E");
    linha08.addElement(new Integer(10));
    linha08.addElement("P2");
    linha08.addElement("E");
    linha08.addElement(new Integer(10));
    linha08.addElement("P3");
    linha08.addElement(null);
    linha08.addElement(null);
    linha08.addElement(null);
    linha08.addElement(null);
    linha08.addElement(null);
    linha08.addElement(null);
    linha08.addElement("0/9");
    linha08.addElement("0/9");
    linha08.addElement("0/9");
    linha08.addElement("0/9");
    linha08.addElement("0/9");
    linha08.addElement("0/9");
    linha08.addElement("DVY");
    linha08.addElement("DVX");
    linha08.addElement("######-##");
    vIE.addElement(linha08);
    
    linha09.addElement("BA");
    linha09.addElement(new Integer(2));
    linha09.addElement(new Integer(8));
    linha09.addElement(new Integer(0));
    linha09.addElement("E");
    linha09.addElement(new Integer(11));
    linha09.addElement("P2");
    linha09.addElement("E");
    linha09.addElement(new Integer(11));
    linha09.addElement("P3");
    linha09.addElement(null);
    linha09.addElement(null);
    linha09.addElement(null);
    linha09.addElement(null);
    linha09.addElement(null);
    linha09.addElement(null);
    linha09.addElement("0/9");
    linha09.addElement("0/9");
    linha09.addElement("0/9");
    linha09.addElement("0/9");
    linha09.addElement("0/9");
    linha09.addElement("0/9");
    linha09.addElement("DVY");
    linha09.addElement("DVX");
    linha09.addElement("######-##");
    vIE.addElement(linha09);
    
    linha10.addElement("CE");
    linha10.addElement(new Integer(1));
    linha10.addElement(new Integer(9));
    linha10.addElement(new Integer(0));
    linha10.addElement("E");
    linha10.addElement(new Integer(11));
    linha10.addElement("P1");
    linha10.addElement(null);
    linha10.addElement(null);
    linha10.addElement(null);
    linha10.addElement(null);
    linha10.addElement(null);
    linha10.addElement(null);
    linha10.addElement(null);
    linha10.addElement(null);
    linha10.addElement("=0");
    linha10.addElement("0/9");
    linha10.addElement("0/9");
    linha10.addElement("0/9");
    linha10.addElement("0/9");
    linha10.addElement("0/9");
    linha10.addElement("0/9");
    linha10.addElement("0/9");
    linha10.addElement("DVX");
    linha10.addElement("########-#");
    vIE.addElement(linha10);
    
    linha11.addElement("DF");
    linha11.addElement(new Integer(1));
    linha11.addElement(new Integer(13));
    linha11.addElement(new Integer(0));
    linha11.addElement("E");
    linha11.addElement(new Integer(11));
    linha11.addElement("P2");
    linha11.addElement("E");
    linha11.addElement(new Integer(11));
    linha11.addElement("P1");
    linha11.addElement(null);
    linha11.addElement("=0");
    linha11.addElement("=7");
    linha11.addElement("=3,4");
    linha11.addElement("0/9");
    linha11.addElement("0/9");
    linha11.addElement("0/9");
    linha11.addElement("0/9");
    linha11.addElement("0/9");
    linha11.addElement("0/9");
    linha11.addElement("0/9");
    linha11.addElement("0/9");
    linha11.addElement("DVX");
    linha11.addElement("DVY");
    linha11.addElement("###.########-##");
    vIE.addElement(linha11);
    
    linha12.addElement("ES");
    linha12.addElement(new Integer(1));
    linha12.addElement(new Integer(9));
    linha12.addElement(new Integer(0));
    linha12.addElement("E");
    linha12.addElement(new Integer(11));
    linha12.addElement("P1");
    linha12.addElement(null);
    linha12.addElement(null);
    linha12.addElement(null);
    linha12.addElement(null);
    linha12.addElement(null);
    linha12.addElement(null);
    linha12.addElement(null);
    linha12.addElement(null);
    linha12.addElement("=0");
    linha12.addElement("=0,8");
    linha12.addElement("0/9");
    linha12.addElement("0/9");
    linha12.addElement("0/9");
    linha12.addElement("0/9");
    linha12.addElement("0/9");
    linha12.addElement("0/9");
    linha12.addElement("DVX");
    linha12.addElement("########-#");
    vIE.addElement(linha12);
    
    linha13.addElement("GO");
    linha13.addElement(new Integer(1));
    linha13.addElement(new Integer(9));
    linha13.addElement(new Integer(1));
    linha13.addElement("E");
    linha13.addElement(new Integer(11));
    linha13.addElement("P1");
    linha13.addElement(null);
    linha13.addElement(null);
    linha13.addElement(null);
    linha13.addElement(null);
    linha13.addElement(null);
    linha13.addElement(null);
    linha13.addElement(null);
    linha13.addElement(null);
    linha13.addElement("=1");
    linha13.addElement("=0,1,5");
    linha13.addElement("0/9");
    linha13.addElement("0/9");
    linha13.addElement("0/9");
    linha13.addElement("0/9");
    linha13.addElement("0/9");
    linha13.addElement("0/9");
    linha13.addElement("DVX");
    linha13.addElement("##.###.###-#");
    vIE.addElement(linha13);
    
    linha14.addElement("GO");
    linha14.addElement(new Integer(2));
    linha14.addElement(new Integer(9));
    linha14.addElement(new Integer(0));
    linha14.addElement("E");
    linha14.addElement(new Integer(11));
    linha14.addElement("P1");
    linha14.addElement(null);
    linha14.addElement(null);
    linha14.addElement(null);
    linha14.addElement(null);
    linha14.addElement(null);
    linha14.addElement(null);
    linha14.addElement(null);
    linha14.addElement(null);
    linha14.addElement("=1");
    linha14.addElement("=0,1,5");
    linha14.addElement("0/9");
    linha14.addElement("0/9");
    linha14.addElement("0/9");
    linha14.addElement("0/9");
    linha14.addElement("0/9");
    linha14.addElement("0/9");
    linha14.addElement("DVX");
    linha14.addElement("##.###.###-#");
    vIE.addElement(linha14);
    
    linha15.addElement("MA");
    linha15.addElement(new Integer(1));
    linha15.addElement(new Integer(9));
    linha15.addElement(new Integer(0));
    linha15.addElement("E");
    linha15.addElement(new Integer(11));
    linha15.addElement("P1");
    linha15.addElement(null);
    linha15.addElement(null);
    linha15.addElement(null);
    linha15.addElement(null);
    linha15.addElement(null);
    linha15.addElement(null);
    linha15.addElement(null);
    linha15.addElement(null);
    linha15.addElement("=1");
    linha15.addElement("=2");
    linha15.addElement("0/9");
    linha15.addElement("0/9");
    linha15.addElement("0/9");
    linha15.addElement("0/9");
    linha15.addElement("0/9");
    linha15.addElement("0/9");
    linha15.addElement("DVX");
    linha15.addElement("#########");
    vIE.addElement(linha15);
    
    linha16.addElement("MT");
    linha16.addElement(new Integer(1));
    linha16.addElement(new Integer(11));
    linha16.addElement(new Integer(0));
    linha16.addElement("E");
    linha16.addElement(new Integer(11));
    linha16.addElement("P1");
    linha16.addElement(null);
    linha16.addElement(null);
    linha16.addElement(null);
    linha16.addElement(null);
    linha16.addElement(null);
    linha16.addElement(null);
    linha16.addElement("0/9");
    linha16.addElement("0/9");
    linha16.addElement("0/9");
    linha16.addElement("0/9");
    linha16.addElement("0/9");
    linha16.addElement("0/9");
    linha16.addElement("0/9");
    linha16.addElement("0/9");
    linha16.addElement("0/9");
    linha16.addElement("0/9");
    linha16.addElement("DVX");
    linha16.addElement("##########-#");
    vIE.addElement(linha16);
    
    linha17.addElement("MS");
    linha17.addElement(new Integer(1));
    linha17.addElement(new Integer(9));
    linha17.addElement(new Integer(0));
    linha17.addElement("E");
    linha17.addElement(new Integer(11));
    linha17.addElement("P1");
    linha17.addElement(null);
    linha17.addElement(null);
    linha17.addElement(null);
    linha17.addElement(null);
    linha17.addElement(null);
    linha17.addElement(null);
    linha17.addElement(null);
    linha17.addElement(null);
    linha17.addElement("=2");
    linha17.addElement("=8");
    linha17.addElement("0/9");
    linha17.addElement("0/9");
    linha17.addElement("0/9");
    linha17.addElement("0/9");
    linha17.addElement("0/9");
    linha17.addElement("0/9");
    linha17.addElement("DVX");
    linha17.addElement("#########");
    vIE.addElement(linha17);
    
    linha18.addElement("MG");
    linha18.addElement(new Integer(1));
    linha18.addElement(new Integer(13));
    linha18.addElement(new Integer(0));
    linha18.addElement("A,E");
    linha18.addElement(new Integer(10));
    linha18.addElement("P10");
    linha18.addElement("E");
    linha18.addElement(new Integer(11));
    linha18.addElement("P11");
    linha18.addElement(null);
    linha18.addElement("0/9");
    linha18.addElement("0/9");
    linha18.addElement("0/9");
    linha18.addElement("0/9");
    linha18.addElement("0/9");
    linha18.addElement("0/9");
    linha18.addElement("0/9");
    linha18.addElement("0/9");
    linha18.addElement("0/9");
    linha18.addElement("0/9");
    linha18.addElement("0/9");
    linha18.addElement("DVX");
    linha18.addElement("DVY");
    linha18.addElement("###.######/####");
    vIE.addElement(linha18);
    
    linha19.addElement("PA");
    linha19.addElement(new Integer(1));
    linha19.addElement(new Integer(9));
    linha19.addElement(new Integer(0));
    linha19.addElement("E");
    linha19.addElement(new Integer(11));
    linha19.addElement("P1");
    linha19.addElement(null);
    linha19.addElement(null);
    linha19.addElement(null);
    linha19.addElement(null);
    linha19.addElement(null);
    linha19.addElement(null);
    linha19.addElement(null);
    linha19.addElement(null);
    linha19.addElement("=1");
    linha19.addElement("=5");
    linha19.addElement("0/9");
    linha19.addElement("0/9");
    linha19.addElement("0/9");
    linha19.addElement("0/9");
    linha19.addElement("0/9");
    linha19.addElement("0/9");
    linha19.addElement("DVX");
    linha19.addElement("########-#");
    vIE.addElement(linha19);
    
    linha20.addElement("PB");
    linha20.addElement(new Integer(1));
    linha20.addElement(new Integer(9));
    linha20.addElement(new Integer(0));
    linha20.addElement("E");
    linha20.addElement(new Integer(11));
    linha20.addElement("P1");
    linha20.addElement(null);
    linha20.addElement(null);
    linha20.addElement(null);
    linha20.addElement(null);
    linha20.addElement(null);
    linha20.addElement(null);
    linha20.addElement(null);
    linha20.addElement(null);
    linha20.addElement("=1");
    linha20.addElement("=6");
    linha20.addElement("0/9");
    linha20.addElement("0/9");
    linha20.addElement("0/9");
    linha20.addElement("0/9");
    linha20.addElement("0/9");
    linha20.addElement("0/9");
    linha20.addElement("DVX");
    linha20.addElement("########-#");
    vIE.addElement(linha20);
    
    linha21.addElement("PR");
    linha21.addElement(new Integer(1));
    linha21.addElement(new Integer(10));
    linha21.addElement(new Integer(0));
    linha21.addElement("E");
    linha21.addElement(new Integer(11));
    linha21.addElement("P9");
    linha21.addElement("E");
    linha21.addElement(new Integer(11));
    linha21.addElement("P8");
    linha21.addElement(null);
    linha21.addElement(null);
    linha21.addElement(null);
    linha21.addElement(null);
    linha21.addElement("0/9");
    linha21.addElement("0/9");
    linha21.addElement("0/9");
    linha21.addElement("0/9");
    linha21.addElement("0/9");
    linha21.addElement("0/9");
    linha21.addElement("0/9");
    linha21.addElement("0/9");
    linha21.addElement("DVX");
    linha21.addElement("DVY");
    linha21.addElement("########-##");
    vIE.addElement(linha21);
    
    linha22.addElement("PE");
    linha22.addElement(new Integer(1));
    linha22.addElement(new Integer(14));
    linha22.addElement(new Integer(1));
    linha22.addElement("E");
    linha22.addElement(new Integer(11));
    linha22.addElement("P7");
    linha22.addElement(null);
    linha22.addElement(null);
    linha22.addElement(null);
    linha22.addElement("=1");
    linha22.addElement("=8");
    linha22.addElement("1/9");
    linha22.addElement("0/9");
    linha22.addElement("0/9");
    linha22.addElement("0/9");
    linha22.addElement("0/9");
    linha22.addElement("0/9");
    linha22.addElement("0/9");
    linha22.addElement("0/9");
    linha22.addElement("0/9");
    linha22.addElement("0/9");
	linha22.addElement("0/9");
    linha22.addElement("DVX");
    linha22.addElement("#############-#");
    vIE.addElement(linha22);

    linha23.addElement("PI");
    linha23.addElement(new Integer(1));
    linha23.addElement(new Integer(9));
    linha23.addElement(new Integer(0));
    linha23.addElement("E"); 
    linha23.addElement(new Integer(11)); 
    linha23.addElement("P1"); 
    linha23.addElement(null);
    linha23.addElement(null);
    linha23.addElement(null);
    linha23.addElement(null);
    linha23.addElement(null);
    linha23.addElement(null);
    linha23.addElement(null);
    linha23.addElement(null);
    linha23.addElement("=1");
    linha23.addElement("=9");
    linha23.addElement("0/9");
    linha23.addElement("0/9");
    linha23.addElement("0/9");
    linha23.addElement("0/9");
    linha23.addElement("0/9");
    linha23.addElement("0/9");
    linha23.addElement("DVX");
    linha23.addElement("#########");
    vIE.addElement(linha23);

    linha24.addElement("RJ");
    linha24.addElement(new Integer(1));
    linha24.addElement(new Integer(8));
    linha24.addElement(new Integer(0));
    linha24.addElement("E");
    linha24.addElement(new Integer(11));
    linha24.addElement("P8");
    linha24.addElement(null);
    linha24.addElement(null);
    linha24.addElement(null);
    linha24.addElement(null);
    linha24.addElement(null);
    linha24.addElement(null);
    linha24.addElement(null);
    linha24.addElement(null);
    linha24.addElement(null);
    linha24.addElement("=1,7,8,9");
    linha24.addElement("0/9");
    linha24.addElement("0/9");
    linha24.addElement("0/9");
    linha24.addElement("0/9");
    linha24.addElement("0/9");
    linha24.addElement("0/9");
    linha24.addElement("DVX");
    linha24.addElement("##.###.##-#");
    vIE.addElement(linha24);

    linha25.addElement("RN");
    linha25.addElement(new Integer(1));
    linha25.addElement(new Integer(9));
    linha25.addElement(new Integer(0));
    linha25.addElement("B,D");
    linha25.addElement(new Integer(11));
    linha25.addElement("P1");
    linha25.addElement(null);
    linha25.addElement(null);
    linha25.addElement(null);
    linha25.addElement(null);
    linha25.addElement(null);
    linha25.addElement(null);
    linha25.addElement(null);
    linha25.addElement(null);
    linha25.addElement("=2");
    linha25.addElement("=0");
    linha25.addElement("0/3");
    linha25.addElement("0/9");
    linha25.addElement("0/9");
    linha25.addElement("0/9");
    linha25.addElement("0/9");
    linha25.addElement("0/9");
    linha25.addElement("DVX");
    linha25.addElement("##.###.###-#");
    vIE.addElement(linha25);

    linha26.addElement("RS");
    linha26.addElement(new Integer(1));
    linha26.addElement(new Integer(10));
    linha26.addElement(new Integer(0));
    linha26.addElement("E"); 
    linha26.addElement(new Integer(11));
    linha26.addElement("P1");
    linha26.addElement(null);
    linha26.addElement(null);
    linha26.addElement(null);
    linha26.addElement(null);
    linha26.addElement(null);
    linha26.addElement(null);
    linha26.addElement(null);
    linha26.addElement("0/4");
    linha26.addElement("0/9");
    linha26.addElement("0/9");
    linha26.addElement("0/9");
    linha26.addElement("0/9");
    linha26.addElement("0/9");
    linha26.addElement("0/9");
    linha26.addElement("0/9");
	linha26.addElement("0/9");
    linha26.addElement("DVX");
    linha26.addElement("###/#######");
    vIE.addElement(linha26);

    linha27.addElement("RO");
    linha27.addElement(new Integer(1));
    linha27.addElement(new Integer(9));
    linha27.addElement(new Integer(1));
    linha27.addElement("E"); 
    linha27.addElement(new Integer(11));
    linha27.addElement("P4"); 
    linha27.addElement(null);
    linha27.addElement(null);
    linha27.addElement(null);
    linha27.addElement(null);
    linha27.addElement(null);
    linha27.addElement(null);
    linha27.addElement(null);
    linha27.addElement(null);
    linha27.addElement("1/9");
    linha27.addElement("0/9");
    linha27.addElement("0/9");
    linha27.addElement("0/9");
    linha27.addElement("0/9");
    linha27.addElement("0/9");
    linha27.addElement("0/9");
    linha27.addElement("0/9");
    linha27.addElement("DVX");
    linha27.addElement("###.#####-#");
    vIE.addElement(linha27);

    linha28.addElement("RO");
    linha28.addElement(new Integer(2));
    linha28.addElement(new Integer(14));
    linha28.addElement(new Integer(0));
    linha28.addElement("E"); 
    linha28.addElement(new Integer(11));
    linha28.addElement("P1"); 
    linha28.addElement(null);
    linha28.addElement(null);
    linha28.addElement(null);
    linha28.addElement("0/9");
    linha28.addElement("0/9");
    linha28.addElement("0/9");
    linha28.addElement("0/9");
    linha28.addElement("0/9");
    linha28.addElement("0/9");
    linha28.addElement("0/9");
    linha28.addElement("0/9");
    linha28.addElement("0/9");
    linha28.addElement("0/9");
    linha28.addElement("0/9");
    linha28.addElement("0/9");
    linha28.addElement("0/9");
    linha28.addElement("DVX");
    linha28.addElement("#############-#");
    vIE.addElement(linha28);

    linha29.addElement("RR");
    linha29.addElement(new Integer(1));
    linha29.addElement(new Integer(9));
    linha29.addElement(new Integer(0));
    linha29.addElement("D");
    linha29.addElement(new Integer(9));
    linha29.addElement("P5");
    linha29.addElement(null);
    linha29.addElement(null);
    linha29.addElement(null);
    linha29.addElement(null);
    linha29.addElement(null);
    linha29.addElement(null);
    linha29.addElement(null);
    linha29.addElement(null);
    linha29.addElement("=2");
    linha29.addElement("=4");
    linha29.addElement("0/9");
    linha29.addElement("0/9");
    linha29.addElement("0/9");
    linha29.addElement("0/9");
    linha29.addElement("0/9");
    linha29.addElement("0/9");
    linha29.addElement("DVX");
    linha29.addElement("########-#");
    vIE.addElement(linha29);

    linha30.addElement("SC");
    linha30.addElement(new Integer(1));
    linha30.addElement(new Integer(9));
    linha30.addElement(new Integer(0));
    linha30.addElement("E");
    linha30.addElement(new Integer(11));
    linha30.addElement("P1");
    linha30.addElement(null);
    linha30.addElement(null);
    linha30.addElement(null);
    linha30.addElement(null);
    linha30.addElement(null);
    linha30.addElement(null);
    linha30.addElement(null);
    linha30.addElement(null);
    linha30.addElement("0/9");
    linha30.addElement("0/9");
    linha30.addElement("0/9");
    linha30.addElement("0/9");
    linha30.addElement("0/9");
    linha30.addElement("0/9");
    linha30.addElement("0/9");
    linha30.addElement("0/9");
    linha30.addElement("DVX");
    linha30.addElement("###.###.###");
    vIE.addElement(linha30);

    linha31.addElement("SP");
    linha31.addElement(new Integer(1));
    linha31.addElement(new Integer(12));
    linha31.addElement(new Integer(0));
    linha31.addElement("D");
    linha31.addElement(new Integer(11));
    linha31.addElement("P12");
    linha31.addElement("D");
    linha31.addElement(new Integer(11));
    linha31.addElement("P13");
    linha31.addElement(null);
    linha31.addElement(null);
    linha31.addElement("0/9");
    linha31.addElement("0/9");
    linha31.addElement("0/9");
    linha31.addElement("0/9");
    linha31.addElement("0/9");
    linha31.addElement("0/9");
    linha31.addElement("0/9");
    linha31.addElement("0/9");
    linha31.addElement("DVX");
    linha31.addElement("0/9");
    linha31.addElement("0/9");
    linha31.addElement("DVY");
    linha31.addElement("###.###.###.###");
    vIE.addElement(linha31);

    linha32.addElement("SP");
    linha32.addElement(new Integer(2));
    linha32.addElement(new Integer(13));
    linha32.addElement(new Integer(0));
    linha32.addElement("D");
    linha32.addElement(new Integer(11));
    linha32.addElement("P12");
    linha32.addElement(null);
    linha32.addElement(null);
    linha32.addElement(null);
    linha32.addElement(null);
    linha32.addElement("=P");
    linha32.addElement("0/9");
    linha32.addElement("0/9");
    linha32.addElement("0/9");
    linha32.addElement("0/9");
    linha32.addElement("0/9");
    linha32.addElement("0/9");
    linha32.addElement("0/9");
    linha32.addElement("0/9");
    linha32.addElement("DVX");
    linha32.addElement("0/9");
    linha32.addElement("0/9");
    linha32.addElement("0/9");
    linha32.addElement("###.###.###.###");
    vIE.addElement(linha32);

    linha33.addElement("SE");
    linha33.addElement(new Integer(1));
    linha33.addElement(new Integer(9));
    linha33.addElement(new Integer(0));
    linha33.addElement("E");
    linha33.addElement(new Integer(11));
    linha33.addElement("P1"); 
    linha33.addElement(null);
    linha33.addElement(null);
    linha33.addElement(null);
    linha33.addElement(null);
    linha33.addElement(null);
    linha33.addElement(null);
    linha33.addElement(null);
    linha33.addElement(null);
    linha33.addElement("0/9");
    linha33.addElement("0/9");
    linha33.addElement("0/9");
    linha33.addElement("0/9");
    linha33.addElement("0/9");
    linha33.addElement("0/9");
    linha33.addElement("0/9");
    linha33.addElement("0/9");
    linha33.addElement("DVX");
    linha33.addElement("########-#");
    vIE.addElement(linha33);

    linha34.addElement("TO");
    linha34.addElement(new Integer(1));
    linha34.addElement(new Integer(11));
    linha34.addElement(new Integer(0));
    linha34.addElement("E"); 
    linha34.addElement(new Integer(11));
    linha34.addElement("P6");
    linha34.addElement(null);
    linha34.addElement(null);
    linha34.addElement(null);
    linha34.addElement(null);
    linha34.addElement(null);
    linha34.addElement(null);
    linha34.addElement("=2");
    linha34.addElement("=9");
    linha34.addElement("=0,9");
    linha34.addElement("=1,2,3,9");
    linha34.addElement("0/9");
    linha34.addElement("0/9");
    linha34.addElement("0/9");
    linha34.addElement("0/9");
    linha34.addElement("0/9");
    linha34.addElement("0/9");
    linha34.addElement("DVX");
    linha34.addElement("##.##.######.#");
    vIE.addElement(linha34);
  }
  public static String transValor(BigDecimal bdValor,int iTam, int iDec, boolean bZeroEsq) {
    String sValor;
    sValor = strDecimalToStrCurrency(iTam+1,iDec,""+bdValor);

    
//    System.out.println("sValor: "+sValor); 
    if ( sValor.indexOf(",")>=0) 
       sValor = sValor.replaceAll(",","");
//    System.out.println("sValor: "+sValor); 

    if ( sValor.indexOf(".")>=0) 
       sValor = sValor.replaceAll(".","");
//    System.out.println("sValor: "+sValor); 

    if (bZeroEsq) 
       if ( sValor.indexOf(" ")>=0) 
          sValor = sValor.replace(' ','0');
     
//    System.out.println("sValor: "+sValor); 

    return sValor;
  }


}


