/**
 * @version 10/12/2003 <BR>
 * @author Setpoint Informática Ltda./Marco Antonio Sanchez <BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: leiautes <BR>
 * Classe: @(#)NFIswara.java <BR>
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
 * Layout da nota fiscal para a empresa ModelCraft
 */

package org.freedom.layout;
import java.math.BigDecimal;
import java.util.Calendar;

import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.NF;
import org.freedom.funcoes.Funcoes;

public class NF016 extends Layout {
	public boolean imprimir(NF nf,ImprimeOS imp) {
		
		boolean bRetorno = super.imprimir(nf, imp);
		
		final int iLinMaxItens = 46;
		int iNumNota = 0;
		int iItImp = 0;
		int iLinPag = imp.verifLinPag("NF");
		boolean bFat = true;
		boolean bNat = true;
		String sNumNota = ""; 
		String sTipoTran = "";		
		String sHora = null;
		String[] sNat = new String[2];
		String[] sVencs = new String[2];
		String[] sVals = new String[2];
		String[] sDuplics = new String[2];
		Calendar cHora = Calendar.getInstance();
		
		try {
			imp.limpaPags();
			
			sHora = Funcoes.strZero(String.valueOf(cHora.get(Calendar.HOUR_OF_DAY)),2) + ":" + 
					Funcoes.strZero(String.valueOf(cHora.get(Calendar.MINUTE)),2) + ":" + 
					Funcoes.strZero(String.valueOf(cHora.get(Calendar.SECOND)),2);
			
			if(cab.next())
				iNumNota = cab.getInt(NF.C_DOC);

			if (iNumNota==0) 
				sNumNota = "000000";
			else 
				sNumNota = Funcoes.strZero(String.valueOf(iNumNota),6);
			
			for (int i=0; i<2; i++) {
				if (bFat) {
					if (parc.next()) {
						sDuplics[i] = sNumNota + "/" + parc.getInt(NF.C_NPARCITREC);
						sVencs[i] = Funcoes.dateToStrDate(parc.getDate(NF.C_DTVENCTO));
						sVals[i] = Funcoes.strDecimalToStrCurrency(12,2,parc.getString(NF.C_VLRPARC));
					} else {
						bFat = false;
						sDuplics[i] = "********";
						sVencs[i] = "";
						sVals[i] = "";
					}
				} else {
					bFat = false;
					sDuplics[i] = "********";
					sVencs[i] = "";
					sVals[i] = "";
				}
			}						
			
			while (itens.next()) {
				if (bNat) {
					sNat[0] = itens.getString(NF.C_DESCNAT);
					sNat[1] = Funcoes.setMascara(itens.getString(NF.C_CODNAT),"#.##");
					bNat = false;
				}
				if (imp.pRow()==0) {
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow(),125, sNumNota);
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					
					if (nf.getTipoNF()==NF.TPNF_ENTRADA)
						imp.say(imp.pRow()+0,103,"X");
					else
						imp.say(imp.pRow()+0,88,"X");
					
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow(),  5, sNat[0]);
					imp.say(imp.pRow(), 52, sNat[1]);
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow(),  5, cab.getInt(NF.C_CODEMIT) + " - " + cab.getString(NF.C_RAZEMIT));
					imp.say(imp.pRow(), 92, !cab.getString(NF.C_CPFEMIT).equals("") ? Funcoes.setMascara(cab.getString(NF.C_CPFEMIT),"###.###.###-##") : Funcoes.setMascara(cab.getString(NF.C_CNPJEMIT),"##.###.###/####-##")) ;
					imp.say(imp.pRow(),125, Funcoes.dateToStrDate(cab.getDate(NF.C_DTEMITPED)));
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow(),  5, Funcoes.copy(cab.getString(NF.C_ENDEMIT),0,50).trim() + ", " + Funcoes.copy(cab.getString(NF.C_NUMEMIT),0,6).trim() + " - " + Funcoes.copy(cab.getString(NF.C_COMPLEMIT),0,9).trim());
					imp.say(imp.pRow(), 80, Funcoes.copy(cab.getString(NF.C_BAIREMIT),0,15));
					imp.say(imp.pRow(),104, Funcoes.setMascara(cab.getString(NF.C_CEPEMIT),"#####-###"));
					imp.say(imp.pRow(),125, Funcoes.dateToStrDate(cab.getDate(NF.C_DTSAIDA)));
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow(),  5, cab.getString(NF.C_CIDEMIT));
					imp.say(imp.pRow(), 53, (Funcoes.setMascara(cab.getString(NF.C_DDDEMIT) + " ","(####)")) + 
											(Funcoes.setMascara(cab.getString(NF.C_FONEEMIT).trim(),"####-####")));
					imp.say(imp.pRow(), 75, cab.getString(NF.C_UFEMIT));
					imp.say(imp.pRow(), 92, !cab.getString(NF.C_RGEMIT).equals("") ? cab.getString(NF.C_RGEMIT) : cab.getString(NF.C_INSCEMIT));
					imp.say(imp.pRow(),126, sHora);
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
				}
				
				imp.say(imp.pRow()+1,0, imp.comprimido());
				imp.say(imp.pRow(),  6, String.valueOf(itens.getInt(NF.C_CODPROD)));
				imp.say(imp.pRow(), 11, itens.getString(NF.C_DESCPROD).trim());					
				imp.say(imp.pRow(), 78, Funcoes.copy(itens.getString(NF.C_ORIGFISC),0,1)+Funcoes.copy(itens.getString(NF.C_CODTRATTRIB),0,2));
				imp.say(imp.pRow(), 86, itens.getString(NF.C_CODUNID).substring(0,4));
				imp.say(imp.pRow(), 92, String.valueOf(itens.getFloat(NF.C_QTDITPED)));
				imp.say(imp.pRow(),105, Funcoes.strDecimalToStrCurrency( 8,2,String.valueOf(((new BigDecimal(itens.getFloat(NF.C_VLRLIQITPED))).divide(new BigDecimal(itens.getFloat(NF.C_QTDITPED)),2,BigDecimal.ROUND_HALF_UP)))));
				imp.say(imp.pRow(),115, Funcoes.strDecimalToStrCurrency(13,2,String.valueOf(itens.getFloat(NF.C_VLRLIQITPED))));
				imp.say(imp.pRow(),132, Funcoes.strDecimalToStrCurrency( 3,2,String.valueOf(itens.getFloat(NF.C_PERCICMSITPED))));
				 
				iItImp++;
				
				if ((iItImp == itens.getInt(NF.C_CONTAITENS)) || (imp.pRow() >= iLinMaxItens)) {
					
					frete.next();
					
					if (imp.pRow()> iLinMaxItens) {
						Funcoes.mensagemInforma(null,"Número de itens ultrapassa capacidade do formulário!");
						imp.fechaGravacao();
						return false;					 	 
					}
					if (iItImp == itens.getInt(NF.C_CONTAITENS)) {
						int iRow = imp.pRow();
						for (int i=0; i<(iLinMaxItens-6-iRow);i++) 
							imp.say(imp.pRow()+1,0, imp.comprimido());
						
						for (int i=0; i<(iLinMaxItens-imp.pRow());i++)
							imp.say(imp.pRow()+1,0, imp.comprimido());

						imp.say(imp.pRow()+1,0, imp.comprimido());
						imp.say(imp.pRow()+1,0, imp.comprimido());
						imp.say(imp.pRow(),  5, Funcoes.strDecimalToStrCurrency(20,2,String.valueOf(itens.getFloat(NF.C_VLRBASEICMSPED))));
						imp.say(imp.pRow(), 33, Funcoes.strDecimalToStrCurrency(20,2,String.valueOf(itens.getFloat(NF.C_VLRICMSPED))));
						imp.say(imp.pRow(),114, Funcoes.strDecimalToStrCurrency(20,2,String.valueOf(itens.getFloat(NF.C_VLRPRODPED))));
						imp.say(imp.pRow()+1,0, imp.comprimido());
						imp.say(imp.pRow()+1,0, imp.comprimido());
						imp.say(imp.pRow(),  5, Funcoes.strDecimalToStrCurrency(20,2,String.valueOf(frete.getFloat(NF.C_VLRFRETEPED))));
						imp.say(imp.pRow(), 62, Funcoes.strDecimalToStrCurrency(20,2,String.valueOf(itens.getFloat(NF.C_VLRADICPED))));
						imp.say(imp.pRow(), 88, Funcoes.strDecimalToStrCurrency(20,2,String.valueOf(itens.getFloat(NF.C_VLRIPIPED))));
						imp.say(imp.pRow(),114, Funcoes.strDecimalToStrCurrency(20,2,String.valueOf(itens.getFloat(NF.C_VLRLIQPED))));
						iItImp = 0;
					}
					else if (imp.pRow() == iLinMaxItens) {
						imp.say(imp.pRow()+1,0, imp.comprimido());
						imp.say(imp.pRow()+1,0, imp.comprimido());
						imp.say(imp.pRow()+1,0, imp.comprimido());
						imp.say(imp.pRow(),  6, "***************");
						imp.say(imp.pRow(), 36, "***************");
						imp.say(imp.pRow(),113, "***************");
						imp.say(imp.pRow()+1,0, imp.comprimido());
						imp.say(imp.pRow()+1,0, imp.comprimido());
						imp.say(imp.pRow(),  6, "***************");
						imp.say(imp.pRow(), 65, "***************");
						imp.say(imp.pRow(), 90, "***************");
						imp.say(imp.pRow(),113, "***************");
					}
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow(),  6, frete.getString(NF.C_RAZTRANSP));
					imp.say(imp.pRow(), 72, frete.getString(NF.C_TIPOFRETE).equals("C") ? "1" : "2");
					imp.say(imp.pRow(), 77, frete.getString(NF.C_PLACAFRETE));
					imp.say(imp.pRow(), 93, frete.getString(NF.C_UFFRETE));
					   
					sTipoTran = frete.getString(NF.C_TIPOTRANSP);
					
					if ( sTipoTran.equals("C") )
						imp.say(imp.pRow(),116, Funcoes.setMascara(cab.getString(NF.C_CNPJEMIT),"##.###.###/####-##"));
					else 
						imp.say(imp.pRow(),116, Funcoes.setMascara(frete.getString(NF.C_CNPJTRANSP),"##.###.###/####-##"));
					   
					   
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow(),  6, Funcoes.copy(frete.getString(NF.C_ENDTRANSP),0,42) + "   " + Funcoes.copy(String.valueOf(frete.getInt(NF.C_NUMTRANSP)),0,6));
					imp.say(imp.pRow(), 77, Funcoes.copy(frete.getString(NF.C_CIDTRANSP),0,30));
					imp.say(imp.pRow(),108, frete.getString(NF.C_UFTRANSP));
					
					if (sTipoTran.equals("C") )
						imp.say(imp.pRow()+0,116, cab.getString(NF.C_INSCEMIT));
					else
						imp.say(imp.pRow()+0,116, frete.getString(NF.C_INSCTRANSP));
					
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow(),  6, frete.getString(NF.C_QTDFRETE));
					imp.say(imp.pRow(), 26, frete.getString(NF.C_ESPFRETE));
					imp.say(imp.pRow(), 55, frete.getString(NF.C_MARCAFRETE));
					imp.say(imp.pRow(),106, Funcoes.strDecimalToStrCurrency(5,casasDec,String.valueOf(frete.getString(NF.C_PESOBRUTO))));
					imp.say(imp.pRow(),129, Funcoes.strDecimalToStrCurrency(5,casasDec,String.valueOf(frete.getString(NF.C_PESOLIQ))));
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());      
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow(),125, sNumNota);
					   
					for (int i=imp.pRow(); i<=iLinPag; i++) 
						imp.say(imp.pRow()+1,0, imp.comprimido());

					imp.setPrc(0,0);
					imp.incPags();
				}
			}
			
			imp.fechaGravacao();
			bRetorno = true;
			
		} catch ( Exception err ) {
			err.printStackTrace();
			Funcoes.mensagemErro(null,"Erro ao consultar tabela de Venda!"+err.getMessage());      
			bRetorno = false;
		} finally {
			sNumNota = null; 
			sTipoTran = null;		
			sHora = null;
			sNat = null;
			sVencs = null;
			sVals = null;
			sDuplics = null;
			cHora = null;
			System.gc();
		}
		return bRetorno;
	}
}