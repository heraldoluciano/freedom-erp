/**
 * @version 05/07/2005 <BR>
 * @author Setpoint Informática Ltda./Robson Sanchez <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.componentes <BR>
 * Classe:
 * @(#)NF.java <BR>
 * 
 * Este programa é licenciado de acordo com a LPG-PC (Licença Pública Geral para
 * Programas de Computador), <BR>
 * versão 2.1.0 ou qualquer versão posterior. <BR>
 * A LPG-PC deve acompanhar todas PUBLICAÇÕES, DISTRIBUIÇÕES e REPRODUÇÕES deste
 * Programa. <BR> 
 * Caso uma cópia da LPG-PC não esteja disponível junto com este Programa, você
 * pode contatar <BR>
 * o LICENCIADOR ou então pegar uma cópia em: <BR>
 * Licença: http://www.lpg.adv.br/licencas/lpgpc.rtf <BR>
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa é
 * preciso estar <BR>
 * de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 * Comentários sobre a classe...
 *  
 */

package org.freedom.componentes;

import java.sql.Connection;
import java.util.Vector;

public class NF {
	
	public static final int T_CAB = 0;
	public static final int T_ITENS = 1;
	public static final int T_PARC = 2;
	public static final int T_ADIC = 3;
	public static final int T_TRANSP = 3;

	// cab
	public static final int C_CODVEND = 0;
	public static final int C_CODEMIT = 1;
	public static final int C_RAZEMIT = 2;
	public static final int C_CNPJEMIT = 3;
	public static final int C_CPFEMIT = 4;
	public static final int C_ENDEMIT = 5;
	public static final int C_NUMEMIT = 6;
	public static final int C_COMPLEMIT = 7;
	public static final int C_BAIREMIT = 8;
	public static final int C_CEPEMIT = 9;
	public static final int C_CIDEMIT = 10;
	public static final int C_UFEMIT = 11;
	public static final int C_FONEEMIT = 12;
	public static final int C_FAXEMIT = 13;
	public static final int C_DDDEMIT = 14;
	public static final int C_INSCEMIT = 15;
	public static final int C_RGEMIT = 16;
	public static final int C_EMAILEMIT = 17;
	public static final int C_SITEEMIT = 18;
	public static final int C_CONTEMIT = 19;
	public static final int C_DTEMITPED = 20;
	public static final int C_DOC = 21;
	public static final int C_INCRAEMIT = 22;
	
	
	//itens
	public static final int C_CODITPED = 0;
	public static final int C_CODPROD = 1;
	public static final int C_REFPROD = 2;
	public static final int C_DESCPROD = 3;
	public static final int C_OBSITPED = 4;
	public static final int C_CODUNID = 5;
	public static final int C_VLRUNIDITPED = 6;
	public static final int C_PERCIPIITPED = 7;
	public static final int C_PERCICMSITPED = 8;
	public static final int C_VLRICMSPED = 9;
	public static final int C_VLRIPIPED = 10;
	public static final int C_VLRLIQPED = 11;	
	
	//adic
	public static final int C_CODPLANOPG = 0;
	public static final int C_DIASPE= 1;
	public static final int C_OBSPED = 2;
	public static final int C_NOMEVEND = 3;
	public static final int C_EMAILVEND = 4;
	public static final int C_RAZTRANSP = 5;
	public static final int C_TIPOFRETE = 6;
	public static final int C_DESCFUNC = 7;

	
	
	
	
	public static final int C_DTVENCTO = 0;
	public static final int C_VLRPARC = 1;
	public static final int C_DESCNAT = 3;	
	public static final int C_CODNAT = 4;
	public static final int C_CPFEMITAUX = 10;
	public static final int C_NOMEEMITAUX = 11;
	public static final int C_CIDEMITAUX = 12;
	public static final int C_UFEMITAUX = 13;
	public static final int C_IMPDTSAIDA = 22;
	public static final int C_DTSAIDA = 23;
	public static final int C_VENCLOTE = 28;
	public static final int C_CODLOTE = 29;
	public static final int C_ORIGFISC = 31;
	public static final int C_CODTRATTRIB = 32;
	public static final int C_QTDITPED = 35;
	public static final int C_VLRLIQITPED = 36;
	public static final int C_VLRBASEICMSPED = 39;
	public static final int C_VLRFRETEPED = 41;
	public static final int C_VLRADICPED = 42;
	public static final int C_PLACAFRETE = 48;
	public static final int C_UFFRETE = 49;
	public static final int C_TIPOTRANSP = 50;
	public static final int C_CNPJTRANSP = 52;
	public static final int C_ENDTRANSP = 53;
	public static final int C_NUMTRANSP = 54;
	public static final int C_CIDTRANSP = 55;
	public static final int C_UFTRANSP = 56;
	public static final int C_INSCTRANSP = 58;
	public static final int C_QTDFRETE = 59;
	public static final int C_ESPFRETE = 60;
	public static final int C_MARCAFRETE = 61;
	public static final int C_PESOBRUTO = 62;
	public static final int C_PESOLIQ = 63;
	public static final int C_CODCLCOMIS = 66;
	public static final int C_PERCMCOMISPED = 67;
	
	
	
	
	private int casasDec = 2;

	protected TabVector cab = null;
	protected TabVector itens = null;
	protected TabVector parc = null;
	protected TabVector adic = null;
    
	public NF(int casasDec) {
		super();
		this.casasDec = casasDec;
	}
	
	public TabVector getTabVector(int vector) {
		TabVector t = null;
		switch(vector) {
		   case T_CAB:
		   	  t = cab;
		   	  break;
		   case T_ITENS:
		   	  t = itens;
		   	  break;
		   case T_PARC:
		   	  t = parc;
		   	  break;
		   case T_ADIC:
			   	  t = adic;
			   	  break;
		}
		return t;
	}
	
	public boolean carregaTabelas(Connection con, Vector parans ) {
		return false;
	}
/* 		String sSQL = "SELECT (SELECT COUNT(IC.CODITVENDA) FROM VDITVENDA IC WHERE IC.CODVENDA=V.CODVENDA),"
				+ "(SELECT L.CODLOTE FROM EQLOTE L WHERE L.CODPROD=I.CODPROD AND L.CODLOTE=I.CODLOTE),"
				+ "(SELECT L.VENCTOLOTE FROM EQLOTE L WHERE L.CODPROD=I.CODPROD AND L.CODLOTE=I.CODLOTE),"
				+ "(SELECT M.MENS FROM LFMENSAGEM M WHERE M.CODMENS=I.CODMENS"
				+ " AND M.CODFILIAL=I.CODFILIALME AND M.CODEMP=I.CODEMPME),"
				+ "(SELECT M.MENS FROM LFMENSAGEM M WHERE M.CODMENS=CL.CODMENS"
				+ " AND M.CODFILIAL=CL.CODFILIALME AND M.CODEMP=CL.CODEMPME),"
				+ "(SELECT S.DESCSETOR FROM VDSETOR S WHERE S.CODSETOR=C.CODSETOR"
				+ " AND S.CODFILIAL=C.CODFILIALSR AND S.CODEMP=C.CODEMPSR),"
				+ "(SELECT B.NOMEBANCO FROM FNBANCO B WHERE B.CODEMP=V.CODEMPBO"
				+ " AND B.CODFILIAL=V.CODFILIALBO AND B.CODBANCO=V.CODBANCO),"
				+ "(SELECT P.SIGLAPAIS FROM SGPAIS P WHERE P.CODPAIS=C.CODPAIS),"
				+ "V.DOCVENDA,V.CODVENDA,V.CODCLI,C.RAZCLI,C.CNPJCLI,C.CPFCLI,V.DTEMITVENDA,C.ENDCLI,"
				+ "C.BAIRCLI,C.CEPCLI,V.OBSVENDA,V.DTSAIDAVENDA,C.CIDCLI,C.UFCLI,C.FONECLI,C.FONECLI,C.NUMCLI,C.COMPLCLI,"
				+ "C.FAXCLI,C.INSCCLI,C.RGCLI,I.CODPROD,P.REFPROD,P.CODBARPROD,P.DESCPROD, P.CODUNID,N.CODNAT,"
				+ "I.VLRLIQITVENDA,N.DESCNAT,I.QTDITVENDA,I.PRECOITVENDA,I.VLRPRODITVENDA,I.CODNAT,I.PERCICMSITVENDA,"
				+ "I.PERCISSITVENDA,PERCIPIITVENDA,VLRIPIITVENDA,V.VLRBASEICMSVENDA,V.VLRICMSVENDA,V.VLRPRODVENDA,"
				+ "V.VLRISSVENDA,V.VLRFRETEVENDA,V.VLRDESCVENDA,V.VLRDESCITVENDA,V.VLRADICVENDA,V.VLRIPIVENDA,"
				+ "V.VLRBASEISSVENDA,V.VLRLIQVENDA,V.CODVEND,VEND.NOMEVEND,V.CODPLANOPAG,PG.DESCPLANOPAG,F.CODTRAN,"
				+ "T.RAZTRAN,F.TIPOFRETEVD,F.PLACAFRETEVD,F.UFFRETEVD,T.TIPOTRAN,T.CNPJTRAN,T.ENDTRAN,T.NUMTRAN,T.CIDTRAN,"
				+ "T.UFTRAN,T.INSCTRAN,F.QTDFRETEVD,F.ESPFRETEVD,F.MARCAFRETEVD,F.PESOBRUTVD,"
				+ "F.PESOLIQVD, I.ORIGFISC, I.CODTRATTRIB, FL.CNPJFILIAl,FL.INSCFILIAL,FL.ENDFILIAL,"
				+ "FL.NUMFILIAL,FL.COMPLFILIAL,FL.BAIRFILIAL,FL.CEPFILIAL,FL.CIDFILIAL,FL.UFFILIAL,FL.FONEFILIAL,"
				+ "FL.FAXFILIAL,C.ENDCOB, C.NUMCOB, C.COMPLCOB,C.CEPCOB, C.CIDCOB, P.TIPOPROD, C.INCRACLI, V.CODBANCO,"
				+ "P.CODFISC, C.ENDENT, C.NUMENT, C.COMPLENT,C.CIDENT,C.UFENT,C.BAIRENT,C.NOMECLI,I.OBSITVENDA,"
				+ "V.VLRPISVENDA,V.VLRCOFINSVENDA,V.VLRIRVENDA,V.VLRCSOCIALVENDA,V.CODCLCOMIS,V.PERCCOMISVENDA,"
				+ "V.PERCMCOMISVENDA, N.IMPDTSAIDANAT,P.DESCAUXPROD, C.DDDCLI "
				+ " FROM VDVENDA V, VDCLIENTE C, VDITVENDA I, EQPRODUTO P, VDVENDEDOR VEND, FNPLANOPAG PG,"
				+ " VDFRETEVD F, VDTRANSP T, LFNATOPER N, SGFILIAL FL, LFCLFISCAL CL WHERE V.TIPOVENDA='V' AND V.CODVENDA="
				+ iCodVenda
				+ " AND V.CODEMP=?"
				+ " AND V.CODFILIAL=? AND FL.CODEMP=V.CODEMP AND FL.CODFILIAL=V.CODFILIAL AND C.CODCLI=V.CODCLI AND I.CODVENDA=V.CODVENDA"
				+ " AND P.CODPROD=I.CODPROD AND VEND.CODVEND=V.CODVEND AND PG.CODPLANOPAG=V.CODPLANOPAG AND F.CODVENDA=V.CODVENDA"
				+ " AND T.CODTRAN=F.CODTRAN AND N.CODNAT=I.CODNAT AND CL.CODFISC = P.CODFISC AND CL.CODFILIAL=P.CODFILIAL"
				+ " AND CL.CODEMP = P.CODEMP ORDER BY P."
				+ dl.getValor()
				+ ",P.DESCPROD";
		String sSQLRec = "SELECT I.DTVENCITREC,I.VLRPARCITREC,I.NPARCITREC FROM FNRECEBER R, FNITRECEBER I WHERE R.CODVENDA="
				+ iCodVenda + " AND I.CODREC=R.CODREC ORDER BY I.DTVENCITREC";
		String sSQLInfoAdic = "SELECT CODAUXV,CPFCLIAUXV,NOMECLIAUXV,CIDCLIAUXV,UFCLIAUXV "
				+ "FROM VDAUXVENDA WHERE CODEMP=? AND CODFILIAL=? AND CODVENDA=?";

 */
	
}
