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

import org.freedom.telas.Aplicativo;

public class NF {
	
	public static final int TPNF_NONE = -1;
	public static final int TPNF_ENTRADA = 0;
	public static final int TPNF_SAIDA = 1;
	public static final int T_CAB = 0;
	public static final int T_ITENS = 1;
	public static final int T_PARC = 2;
	public static final int T_ADIC = 3;
	public static final int T_FRETE = 4;
	
	// cab
	public static final int C_CODPED = 0;
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
	public static final int C_DTSAIDA = 23;	
	public static final int C_CODPLANOPG = 24;
	public static final int C_DESCPLANOPAG = 25;
	public static final int C_OBSPED = 26;
	public static final int C_NOMEVEND = 27;
	public static final int C_EMAILVEND = 28;
	public static final int C_DESCFUNC = 29;
	public static final int C_CODCLCOMIS = 30;
	public static final int C_PERCCOMISVENDA = 31;
	public static final int C_CODVEND = 32;
	public static final int C_ENDCOBEMIT = 33;
	public static final int C_CIDCOBEMIT = 34;
	public static final int C_UFCOBEMIT = 35;
	public static final int C_BAIRCOBEMIT = 36;
	public static final int C_NUMCOBEMIT = 37;
	public static final int C_PERCMCOMISPED = 38;
	public static final int C_NOMEEMIT = 39;	
	public static final int C_ENDENTEMIT = 40;
	public static final int C_NUMENTEMIT = 41;
	public static final int C_COMPLENTEMIT = 42;
	public static final int C_BAIRENTEMIT = 43;
	public static final int C_CIDENTEMIT = 44;
	public static final int C_UFENTEMIT = 45;
	public static final int C_CODBANCO = 46;
	public static final int C_NOMEBANCO = 47;
	public static final int C_DESCSETOR = 48;
	public static final int C_VLRDESCITPED = 49;
	public static final int C_DIASPAG = 50;	
	
	//itens
	public static final int C_CODITPED = 0;
	public static final int C_CODPROD = 1;
	public static final int C_REFPROD = 2;
	public static final int C_DESCPROD = 3;
	public static final int C_OBSITPED = 4;
	public static final int C_CODUNID = 5;
	public static final int C_QTDITPED = 6;
	public static final int C_VLRLIQITPED = 7;
	public static final int C_PERCIPIITPED = 8;
	public static final int C_PERCICMSITPED = 9;
	public static final int C_VLRICMSPED = 10;
	public static final int C_VLRIPIPED = 11;
	public static final int C_VLRIPIITPED = 12;
	public static final int C_VLRLIQPED = 13;	
	public static final int C_IMPDTSAIDA = 14;
	public static final int C_VLRPRODITPED = 15;
	public static final int C_DESCNAT = 16;	
	public static final int C_CODNAT = 17;
	public static final int C_CODLOTE = 18;
	public static final int C_VENCLOTE = 19;
	public static final int C_ORIGFISC = 20;
	public static final int C_CODTRATTRIB = 21;
	public static final int C_VLRBASEICMSPED = 22;
	public static final int C_VLRADICPED = 23;
	public static final int C_CONTAITENS = 24;
	public static final int C_DESCFISC = 25;
	public static final int C_DESCFISC2 = 26;
	public static final int C_CODFISC = 27;
	public static final int C_TIPOPROD = 28;
	public static final int C_VLRISSPED = 29;
	public static final int C_VLRPRODPED = 30;	
	public static final int C_VLRDESCITPROD = 31;	
	
	//adic
	public static final int C_CODAUXV = 0;
	public static final int C_CPFEMITAUX = 1;
	public static final int C_NOMEEMITAUX = 2;
	public static final int C_CIDEMITAUX = 3;
	public static final int C_UFEMITAUX = 4;	
	
	//parc	
	public static final int C_DTVENCTO = 0;
	public static final int C_VLRPARC = 1;
	public static final int C_NPARCITREC = 2;	
	
	//frete
	public static final int C_CODTRAN= 0;
	public static final int C_RAZTRANSP = 1;
	public static final int C_NOMETRANSP = 2;
	public static final int C_INSCTRANSP = 3;
	public static final int C_CNPJTRANSP = 4;
	public static final int C_TIPOTRANSP = 5;
	public static final int C_ENDTRANSP = 6;
	public static final int C_NUMTRANSP = 7;
	public static final int C_CIDTRANSP = 8;
	public static final int C_UFTRANSP = 9;
	public static final int C_TIPOFRETE = 10;
	public static final int C_PLACAFRETE = 11;
	public static final int C_UFFRETE = 12;
	public static final int C_QTDFRETE = 13;
	public static final int C_ESPFRETE = 14;
	public static final int C_MARCAFRETE = 15;
	public static final int C_PESOBRUTO = 16;
	public static final int C_PESOLIQ = 17;
	public static final int C_VLRFRETEPED = 18;	
	public static final int C_CONHECFRETEPED = 19;	

	protected TabVector cab = null;
	protected TabVector itens = null;
	protected TabVector parc = null;
	protected TabVector adic = null;
	protected TabVector frete = null;
	protected int tipoNF = TPNF_NONE; 
	protected int casasDec = 2;
	protected int casasDecFin = Aplicativo.casasDecFin;
	
	private Connection con = null;
    
	public NF(int casasDec) {
		super();
		this.casasDec = casasDec;
	}
	
	protected void setConexao(Connection arg0) {
		con = arg0;
	}
	
	public Connection getConexao() {
		return con;
	}
	
	public int getCasasDec() {
		return casasDec;
	}
	
	public int getCasasDecFin() {
		return casasDecFin;
	}
	
	public int getTipoNF() {
		return tipoNF;
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
		   case T_FRETE:
		   	  t = frete;
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
