/**
 * @version 10/12/2003 <BR>
 * @author Setpoint Informática Ltda./Alex Rodrigues <BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: leiautes <BR>
 * Classe: @(#)NF099.java <BR>
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
 * Layout da nota fiscal para a empresa 99 Ltda.
 */

package org.freedom.layout;

import java.math.BigDecimal;
import java.util.Calendar;

import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.NF;
import org.freedom.funcoes.Funcoes;
public class NF099c extends Layout {

	public boolean imprimir(NF nf,ImprimeOS imp) {
			 
		boolean bRetorno = super.imprimir(nf, imp);
		boolean bNat = true;
		int iNumNota = 0;
		int numMax = 38;
		int iItImp = 0;
		int iLinPag = imp.verifLinPag("NF");
		Calendar cHora = Calendar.getInstance();
		String[] sNat = new String[2];
		String sNumNota = ""; 
		String sTipoTran = "";
		String sHora;
		
		try {
			imp.limpaPags();
			
			sHora = Funcoes.strZero(""+cHora.get(Calendar.HOUR_OF_DAY),2)+":"
						+Funcoes.strZero(""+cHora.get(Calendar.MINUTE),2)+":"
							+Funcoes.strZero(""+cHora.get(Calendar.SECOND),2);
			  
			cab.next();
			iNumNota = cab.getInt(NF.C_DOC);
			if(iNumNota==0) {
				sNumNota = "000000";
			} 
			else {
				sNumNota = Funcoes.strZero(""+iNumNota,6);
			}			
			     
			while (itens.next()) {  
				  
				if (bNat) {
					sNat[0] = Funcoes.copy(itens.getString(NF.C_DESCNAT),35);
					sNat[1] = Funcoes.setMascara(""+itens.getInt(NF.C_CODNAT),"#.##");
					
					bNat = false;
				}
				 
				if (imp.pRow()==0) {
					imp.say(imp.pRow()+1,0,""+imp.comprimido());
					imp.say(imp.pRow()+2,0,"");
					if (nf.getTipoNF()==NF.TPNF_ENTRADA)
						imp.say(imp.pRow()+0,103,"X");
					else
						imp.say(imp.pRow()+0,93,"X");
					
					imp.say(imp.pRow()+5,0,""+imp.comprimido());
					imp.say(imp.pRow()+0,8,sNat[0]);
					imp.say(imp.pRow()+0,45,sNat[1]);
					
					imp.say(imp.pRow()+3,0,""+imp.comprimido());
					imp.say(imp.pRow()+0,8,cab.getString(NF.C_RAZEMIT));
					imp.say(imp.pRow()+0,92,!cab.getString(NF.C_CPFEMIT).equals("") ? Funcoes.setMascara(cab.getString(NF.C_CPFEMIT),"###.###.###-##") : Funcoes.setMascara(cab.getString(NF.C_CNPJEMIT),"##.###.###/####-##")) ;
					imp.say(imp.pRow()+0,125,Funcoes.dateToStrDate(cab.getDate(NF.C_DTEMITPED)));
					imp.say(imp.pRow()+1,0,"");
					imp.say(imp.pRow()+1,0,""+imp.comprimido());
					imp.say(imp.pRow()+0,8,Funcoes.copy(cab.getString(NF.C_ENDEMIT),0,50).trim()+", "+(Funcoes.copy(""+cab.getInt(NF.C_NUMEMIT),0,6).trim()).trim()+" - "+(cab.getString(NF.C_COMPLEMIT) != null ? Funcoes.copy(cab.getString(NF.C_COMPLEMIT),0,9).trim() : "").trim());
					imp.say(imp.pRow()+0,68,!cab.getString(NF.C_BAIREMIT).equals("") ? Funcoes.copy(cab.getString(NF.C_BAIREMIT),0,25) : "");
					imp.say(imp.pRow()+0,98,Funcoes.setMascara(cab.getString(NF.C_CEPEMIT),"#####-###"));
					imp.say(imp.pRow()+0,125,Funcoes.dateToStrDate(cab.getDate(NF.C_DTSAIDA)));
					imp.say(imp.pRow()+1,0,"");
					imp.say(imp.pRow()+1,0,""+imp.comprimido());
					imp.say(imp.pRow()+0,8,cab.getString(NF.C_CIDEMIT));
					imp.say(imp.pRow()+0,52,(!cab.getString(NF.C_DDDEMIT).equals("") ? "("+cab.getString(NF.C_DDDEMIT)+")" : "")+(!cab.getString(NF.C_FONEEMIT).equals("") ? Funcoes.setMascara(cab.getString(NF.C_FONEEMIT).trim(),"####-####") : ""));
					imp.say(imp.pRow()+0,83,cab.getString(NF.C_UFEMIT));
					imp.say(imp.pRow()+0,92,!cab.getString(NF.C_RGEMIT).equals("") ? cab.getString(NF.C_RGEMIT) : cab.getString(NF.C_INSCEMIT));
					imp.say(imp.pRow()+0,126,sHora);
					imp.say(imp.pRow()+3,0,""+imp.comprimido());
				}	   
				
				imp.say(imp.pRow()+1,0,""+imp.comprimido());
				imp.say(imp.pRow()+0,8,""+itens.getInt(NF.C_CODPROD));
				imp.say(imp.pRow()+0,17,Funcoes.copy(itens.getString(NF.C_DESCPROD),0,48));
				imp.say(imp.pRow()+0,84,Funcoes.copy(itens.getString(NF.C_ORIGFISC),0,1)+Funcoes.copy(itens.getString(NF.C_CODTRATTRIB),0,2));
				imp.say(imp.pRow()+0,90,itens.getString(NF.C_CODUNID).substring(0,4));
				imp.say(imp.pRow()+0,98,Funcoes.strDecimalToStrCurrency(7,2,""+itens.getFloat(NF.C_QTDITPED)));
				imp.say(imp.pRow()+0,106,Funcoes.strDecimalToStrCurrency(12,2,""+((new BigDecimal(itens.getFloat(NF.C_VLRLIQITPED))).divide(new BigDecimal(itens.getFloat(NF.C_QTDITPED)),2,BigDecimal.ROUND_HALF_UP))));
				imp.say(imp.pRow()+0,120,Funcoes.strDecimalToStrCurrency(12,2,""+itens.getFloat(NF.C_VLRLIQITPED)));
				imp.say(imp.pRow()+0,134,Funcoes.strDecimalToStrCurrency(2,0,""+itens.getFloat(NF.C_PERCICMSITPED)));
				     
				iItImp++;
				if ((iItImp == itens.getInt(NF.C_CONTAITENS)) || (imp.pRow() == numMax)) {       	 
					if (iItImp == itens.getInt(NF.C_CONTAITENS)) {
						int iRow = imp.pRow();						
						for (int i=0; i<(numMax-iRow);i++) {
							imp.say(imp.pRow()+1,0,"");
						}
							
						frete.next();
						imp.say(imp.pRow()+1,0,"");
						imp.say(imp.pRow()+1,0,"");
						imp.say(imp.pRow()+1,0,""+imp.comprimido());
						imp.say(imp.pRow()+0,8,Funcoes.strDecimalToStrCurrency(20,2,""+itens.getFloat(NF.C_VLRBASEICMSPED)));
						imp.say(imp.pRow()+0,32,Funcoes.strDecimalToStrCurrency(20,2,""+itens.getFloat(NF.C_VLRICMSPED)));
						imp.say(imp.pRow()+0,116,Funcoes.strDecimalToStrCurrency(20,2,""+itens.getFloat(NF.C_VLRLIQPED)));
						imp.say(imp.pRow()+1,0,"");
						imp.say(imp.pRow()+1,0,""+imp.comprimido());
						imp.say(imp.pRow()+0,8,Funcoes.strDecimalToStrCurrency(20,2,""+frete.getFloat(NF.C_VLRFRETEPED)));
						imp.say(imp.pRow()+0,60,Funcoes.strDecimalToStrCurrency(20,2,""+itens.getFloat(NF.C_VLRADICPED)));
						imp.say(imp.pRow()+0,87,Funcoes.strDecimalToStrCurrency(20,2,""+itens.getFloat(NF.C_VLRIPIPED)));
						imp.say(imp.pRow()+0,116,Funcoes.strDecimalToStrCurrency(20,2,""+itens.getFloat(NF.C_VLRLIQPED)));
						iItImp = 0;
					}
					else if (imp.pRow() == numMax) {
						imp.say(imp.pRow()+1,0,"");
						imp.say(imp.pRow()+1,0,"");
						imp.say(imp.pRow()+1,0,""+imp.comprimido());
						imp.say(imp.pRow()+0,8,"***************");
						imp.say(imp.pRow()+0,32,"***************");
						imp.say(imp.pRow()+0,116,"***************");
						imp.say(imp.pRow()+1,0,"");
						imp.say(imp.pRow()+1,0,""+imp.comprimido());
						imp.say(imp.pRow()+0,8,"***************");
						imp.say(imp.pRow()+0,62,"***************");
						imp.say(imp.pRow()+0,87,"***************");
						imp.say(imp.pRow()+0,116,"***************");
					}
					
					imp.say(imp.pRow()+3,0,""+imp.comprimido());
					
					if (frete.getString(NF.C_TIPOFRETE)!=null) {   
						imp.say(imp.pRow()+0,8,frete.getString(NF.C_RAZTRANSP));
						imp.say(imp.pRow()+0,83,frete.getString(NF.C_TIPOFRETE).equals("C") ? "1" : "2");
						imp.say(imp.pRow()+0,88,frete.getString(NF.C_PLACAFRETE));
						imp.say(imp.pRow()+0,105,frete.getString(NF.C_UFFRETE));
					}
					
					sTipoTran = frete.getString(NF.C_TIPOTRANSP);
					if (sTipoTran==null) 
						sTipoTran = "T";    
					if ( sTipoTran.equals("C") )
						imp.say(imp.pRow()+0,115,Funcoes.setMascara(cab.getString(NF.C_CNPJEMIT),"##.###.###/####-##"));
					else 
						imp.say(imp.pRow()+0,115,Funcoes.setMascara(frete.getString(NF.C_CNPJTRANSP),"##.###.###/####-##"));					   
					   
					imp.say(imp.pRow()+1,0,"");
					imp.say(imp.pRow()+1,0,""+imp.comprimido());
					imp.say(imp.pRow()+0,8,Funcoes.copy(frete.getString(NF.C_ENDTRANSP),0,50)+", "+Funcoes.copy(frete.getString(NF.C_NUMTRANSP),0,6));
					imp.say(imp.pRow()+0,76,frete.getString(NF.C_CIDTRANSP));
					imp.say(imp.pRow()+0,105,frete.getString(NF.C_UFTRANSP));
					
					if (sTipoTran.equals("C") )
						imp.say(imp.pRow()+0,115,cab.getString(NF.C_INSCEMIT));
					else 
						imp.say(imp.pRow()+0,115,frete.getString(NF.C_INSCTRANSP));
					
					imp.say(imp.pRow()+1,0,"");
					imp.say(imp.pRow()+1,0,""+imp.comprimido());
					imp.say(imp.pRow()+0,8,""+frete.getFloat(NF.C_QTDFRETE));
					imp.say(imp.pRow()+0,25,frete.getString(NF.C_ESPFRETE));
					imp.say(imp.pRow()+0,50,frete.getString(NF.C_MARCAFRETE));
					imp.say(imp.pRow()+0,97,""+frete.getFloat(NF.C_PESOBRUTO));
					imp.say(imp.pRow()+0,122,""+frete.getFloat(NF.C_PESOLIQ));					 
										   
					imp.say(imp.pRow()+14,0,""+imp.comprimido());
					imp.say(imp.pRow()+0,125,sNumNota);
					
					for (int i=imp.pRow(); i<=iLinPag; i++)  
						imp.say(imp.pRow()+1,0,"");
					
					imp.setPrc(0,0);
					imp.incPags();
				}
			}

			imp.fechaGravacao();
			bRetorno = true;
		}
		catch ( Exception err ) {
			Funcoes.mensagemErro(null,"Erro ao montar nf de venda.\n"+err.getMessage());     
			err.printStackTrace();
			bRetorno = false;
		}
		return bRetorno;
	}
}

