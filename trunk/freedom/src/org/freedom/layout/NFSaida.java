/**
 * @version 08/08/2005 <BR>
 * @author Setpoint Informática Ltda./Robson Sanchez <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.layout <BR>
 * Classe: @(#)NFSaida.java <BR>
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

package org.freedom.layout;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import org.freedom.componentes.NF;
import org.freedom.componentes.TabVector;
import org.freedom.funcoes.Funcoes;

public class NFSaida extends NF {
	protected int tipoNF = TPNF_SAIDA;
	
	public NFSaida(int casasDec) {
		super(casasDec);
	}
	
	public int getTipoNF(){
		return this.tipoNF;
	}
	
	public boolean carregaTabelas(Connection con, Vector parans ) {
		boolean retorno = true;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = null;
		int cont = 0;
		try {
			sql = "SELECT V.CODVENDA, V.CODCLI, C.RAZCLI, C.CNPJCLI, C.CPFCLI, C.ENDCLI, C.NUMCLI, C.COMPLCLI,"+
					"C.BAIRCLI, C.CEPCLI, C.CIDCLI, C.UFCLI, C.FONECLI, C.FAXCLI, C.DDDCLI, C.INSCCLI, C.RGCLI,"+
					"C.EMAILCLI, C.SITECLI, C.CONTCLI, V.DTEMITVENDA, V.DOCVENDA, C.INCRACLI, V.DTSAIDAVENDA,"+
					"V.CODPLANOPAG, PG.DESCPLANOPAG, V.OBSVENDA, VEND.NOMEVEND, VEND.EMAILVEND,"+
					"(SELECT F.DESCFUNC FROM RHFUNCAO F "+
					"WHERE F.CODFUNC=VEND.CODFUNC AND F.CODEMP=VEND.CODEMPFU AND F.CODFILIAL=VEND.CODFILIALFU),"+
					"V.CODCLCOMIS, V.PERCCOMISVENDA, V.CODVEND, C.ENDCOB, C.CIDCOB, C.NUMCOB, C.UFCOB, C.BAIRCOB, V.PERCMCOMISVENDA "+
					"FROM VDVENDA V, VDCLIENTE C, FNPLANOPAG PG, VDVENDEDOR VEND "+
					"WHERE C.CODEMP=V.CODEMPCL AND C.CODFILIAL=V.CODFILIALCL AND C.CODCLI=V.CODCLI "+
					"AND V.CODEMPPG=PG.CODEMP AND V.CODFILIALPG=PG.CODFILIAL AND V.CODPLANOPAG=PG.CODPLANOPAG "+
					"AND V.CODVEND=VEND.CODVEND AND V.CODEMPVD=VEND.CODEMP AND V.CODFILIALVD=VEND.CODFILIAL AND C.CODCLI=V.CODCLI " +
					"AND V.CODEMP=? AND V.CODFILIAL=? AND V.TIPOVENDA='V' AND V.CODVENDA=?";
			ps = con.prepareStatement(sql);
			ps.setInt(1,((Integer) parans.elementAt(0)).intValue());
			ps.setInt(2,((Integer) parans.elementAt(1)).intValue());
			ps.setInt(3,((Integer) parans.elementAt(2)).intValue());
			rs = ps.executeQuery();
			cont++;
			cab = new TabVector(39);
			while (rs.next()) {
				cab.addRow();
				cab.setInt(C_CODPED, rs.getInt("CODVENDA"));
				cab.setInt(C_CODEMIT, rs.getInt("CODCLI"));
				cab.setString(C_RAZEMIT, (rs.getString("RAZCLI")!=null ? rs.getString("RAZCLI") : ""));
				cab.setString(C_CNPJEMIT, (rs.getString("CNPJCLI")!=null ? rs.getString("CNPJCLI") : ""));
				cab.setString(C_CPFEMIT, rs.getString("CPFCLI")!= null ? rs.getString("CPFCLI") : "");
				cab.setString(C_ENDEMIT, (rs.getString("ENDCLI")!=null ? rs.getString("ENDCLI") : ""));
				cab.setInt(C_NUMEMIT, rs.getInt("NUMCLI"));
				cab.setString(C_COMPLEMIT, (rs.getString("COMPLCLI")!=null ? rs.getString("COMPLCLI") : ""));
				cab.setString(C_BAIREMIT, (rs.getString("BAIRCLI")!=null ? rs.getString("BAIRCLI") : ""));
				cab.setString(C_CEPEMIT, (rs.getString("CEPCLI")!=null ? rs.getString("CEPCLI") : ""));
				cab.setString(C_CIDEMIT, (rs.getString("CIDCLI")!=null ? rs.getString("CIDCLI") : ""));
				cab.setString(C_UFEMIT, (rs.getString("UFCLI")!=null ? rs.getString("UFCLI") : ""));
				cab.setString(C_FONEEMIT, (rs.getString("FONECLI")!=null ? rs.getString("FONECLI") : ""));
				cab.setString(C_FAXEMIT, (rs.getString("FAXCLI")!=null ? rs.getString("FAXCLI") : ""));
				cab.setString(C_DDDEMIT, (rs.getString("DDDCLI")!=null ? rs.getString("DDDCLI") : ""));
				cab.setString(C_INSCEMIT, (rs.getString("INSCCLI")!=null ? rs.getString("INSCCLI") : ""));
				cab.setString(C_RGEMIT, rs.getString("RGCLI")!= null ? rs.getString("RGCLI") : "");
				cab.setString(C_EMAILEMIT, (rs.getString("EMAILCLI")!=null ? rs.getString("EMAILCLI") : ""));
				cab.setString(C_SITEEMIT, (rs.getString("SITECLI")!=null ? rs.getString("SITECLI") : ""));
				cab.setString(C_CONTEMIT, (rs.getString("CONTCLI")!=null ? rs.getString("CONTCLI") : ""));
				cab.setDate(C_DTEMITPED, rs.getDate("DTEMITVENDA"));
				cab.setInt(C_DOC, rs.getInt("DOCVENDA"));
				cab.setString(C_INCRAEMIT, rs.getString("INCRACLI")!=null ? rs.getString("INCRACLI") : "");
				cab.setDate(C_DTSAIDA, rs.getDate("DTSAIDAVENDA"));					
				cab.setString(C_CODPLANOPG, (rs.getString("CODPLANOPAG")!=null ? rs.getString("CODPLANOPAG") : ""));
				cab.setString(C_DESCPLANOPAG, (rs.getString("DESCPLANOPAG")!=null ? rs.getString("DESCPLANOPAG") : ""));
				cab.setString(C_OBSPED, (rs.getString("OBSVENDA")!=null ? rs.getString("OBSVENDA") : ""));
				cab.setString(C_NOMEVEND, (rs.getString("NOMEVEND")!=null ? rs.getString("NOMEVEND") : ""));
				cab.setString(C_EMAILVEND, (rs.getString("EMAILVEND")!=null ? rs.getString("EMAILVEND") : ""));
				cab.setString(C_DESCFUNC, (rs.getString(30)!=null ? rs.getString(30) : ""));
				cab.setString(C_CODCLCOMIS, (rs.getString("CODCLCOMIS")!=null ? rs.getString("CODCLCOMIS") : ""));
				cab.setFloat(C_PERCCOMISVENDA, rs.getFloat("PERCCOMISVENDA"));
				cab.setInt(C_CODVEND, rs.getInt("CODVEND"));
				cab.setString(C_ENDCOBEMIT, (rs.getString("ENDCOB")!=null ? rs.getString("ENDCOB").trim() : ""));
				cab.setString(C_CIDCOBEMIT, (rs.getString("CIDCOB")!=null ? rs.getString("CIDCOB").trim() : ""));
				cab.setString(C_UFCOBEMIT, (rs.getString("UFCOB")!=null ? rs.getString("UFCOB").trim() : ""));
				cab.setString(C_BAIRCOBEMIT, (rs.getString("BAIRCOB")!=null ? rs.getString("BAIRCOB").trim() : ""));
				cab.setInt(C_NUMCOBEMIT, rs.getInt("NUMCOB"));
				cab.setFloat(C_PERCMCOMISPED, rs.getFloat("PERCMCOMISVENDA"));
			}
			rs.close();
			ps.close();
			if (!con.getAutoCommit())
				con.commit();
			cab.setRow(-1);
			
			sql = "SELECT I.CODITVENDA, I.CODPROD, P.REFPROD, P.DESCPROD, I.OBSITVENDA, P.CODUNID, I.VLRIPIITVENDA,"+
					"I.QTDITVENDA, I.VLRLIQITVENDA, I.PERCIPIITVENDA, I.PERCICMSITVENDA, V.VLRICMSVENDA, V.VLRPRODVENDA,"+
					"V.VLRIPIVENDA, V.VLRLIQVENDA, V.VLRISSVENDA, N.IMPDTSAIDANAT, I.VLRPRODITVENDA, N.DESCNAT, N.CODNAT,"+
					"I.CODLOTE, I.ORIGFISC, I.CODTRATTRIB, V.VLRBASEICMSVENDA, V.VLRADICVENDA, P.CODFISC, P.TIPOPROD,"+
					"(SELECT L.VENCTOLOTE FROM EQLOTE L WHERE L.CODEMP=I.CODEMPLE AND L.CODFILIAL=I.CODFILIALLE AND L.CODPROD=I.CODPROD AND L.CODLOTE=I.CODLOTE),"+
					"(SELECT COUNT(IC.CODITVENDA) FROM VDITVENDA IC WHERE IC.CODVENDA=V.CODVENDA AND IC.CODEMP=V.CODEMP AND IC.CODFILIAL=V.CODFILIAL AND IC.TIPOVENDA=V.TIPOVENDA),"+
					"(SELECT M.MENS FROM LFMENSAGEM M WHERE M.CODMENS=CL.CODMENS AND M.CODFILIAL=CL.CODFILIALME AND M.CODEMP=CL.CODEMPME),"+
					"(SELECT M.MENS FROM LFMENSAGEM M WHERE M.CODMENS=I.CODMENS AND M.CODFILIAL=I.CODFILIALME AND M.CODEMP=I.CODEMPME) "+
					"FROM VDITVENDA I, VDVENDA V, EQPRODUTO P, LFNATOPER N, LFCLFISCAL CL "+
					"WHERE P.CODEMP=I.CODEMPPD AND P.CODFILIAL=I.CODFILIALPD AND P.CODPROD=I.CODPROD "+
					"AND N.CODEMP=I.CODEMPNT AND N.CODFILIAL=I.CODFILIALNT "+
					"AND N.CODNAT=I.CODNAT AND I.CODEMP=V.CODEMP AND I.CODFILIAL=V.CODFILIAL "+
					"AND CL.CODFISC=P.CODFISC AND CL.CODEMP=P.CODEMPFC AND CL.CODFILIAL=P.CODFILIALFC "+
					"AND I.CODVENDA=V.CODVENDA AND I.TIPOVENDA=V.TIPOVENDA "+
					"AND V.CODEMP=? AND V.CODFILIAL=? AND V.CODVENDA=? AND V.TIPOVENDA='V' ";
			ps = con.prepareStatement(sql);
			ps.setInt(1,((Integer) parans.elementAt(0)).intValue());
			ps.setInt(2,((Integer) parans.elementAt(1)).intValue());
			ps.setInt(3,((Integer) parans.elementAt(2)).intValue());
			rs = ps.executeQuery();
			cont++;
			itens = new TabVector(31);
			while (rs.next()) {
				itens.addRow();
				itens.setInt(C_CODITPED, rs.getInt("CODITVENDA"));
				itens.setInt(C_CODPROD, rs.getInt("CODPROD"));
				itens.setString(C_REFPROD, (rs.getString("REFPROD")!=null ? rs.getString("REFPROD") : ""));
				itens.setString(C_DESCPROD, (rs.getString("DESCPROD")!=null ? rs.getString("DESCPROD") : ""));
				itens.setString(C_OBSITPED, (rs.getString("OBSITVENDA")!=null ? rs.getString("OBSITVENDA") : ""));
				itens.setString(C_CODUNID, (rs.getString("CODUNID")!=null ? rs.getString("CODUNID") : ""));
				itens.setFloat(C_QTDITPED, rs.getFloat("QTDITVENDA"));
				itens.setFloat(C_VLRLIQITPED, rs.getFloat("VLRLIQITVENDA"));
				itens.setFloat(C_PERCIPIITPED, rs.getFloat("PERCIPIITVENDA"));
				itens.setFloat(C_PERCICMSITPED, rs.getFloat("PERCICMSITVENDA"));
				itens.setFloat(C_VLRICMSPED, rs.getFloat("VLRICMSVENDA"));
				itens.setFloat(C_VLRIPIPED, rs.getFloat("VLRIPIVENDA"));
				itens.setFloat(C_VLRIPIITPED, rs.getFloat("VLRIPIITVENDA"));
				itens.setFloat(C_VLRLIQPED, rs.getFloat("VLRLIQVENDA"));
				itens.setString(C_IMPDTSAIDA, (rs.getString("IMPDTSAIDANAT")!=null ? rs.getString("IMPDTSAIDANAT") : ""));
				itens.setFloat(C_VLRPRODITPED, rs.getFloat("VLRPRODITVENDA"));
				itens.setString(C_DESCNAT, (rs.getString("DESCNAT")!=null ? rs.getString("DESCNAT") : ""));
				itens.setInt(C_CODNAT, rs.getInt("CODNAT"));
				itens.setString(C_CODLOTE, (rs.getString("CODLOTE")!=null ? rs.getString("CODLOTE") : ""));
				itens.setDate(C_VENCLOTE, rs.getDate(28));
				itens.setString(C_ORIGFISC, (rs.getString("ORIGFISC")!=null ? rs.getString("ORIGFISC") : ""));
				itens.setString(C_CODTRATTRIB, (rs.getString("CODTRATTRIB")!=null ? rs.getString("CODTRATTRIB") : ""));
				itens.setFloat(C_VLRBASEICMSPED, rs.getFloat("VLRBASEICMSVENDA"));
				itens.setFloat(C_VLRADICPED, rs.getFloat("VLRADICVENDA"));
				itens.setInt(C_CONTAITENS, rs.getInt(29));
				itens.setString(C_DESCFISC, (rs.getString(30)!=null ? rs.getString(30) : ""));
				itens.setString(C_DESCFISC2, (rs.getString(31)!=null ? rs.getString(31) : ""));
				itens.setString(C_CODFISC, rs.getString("CODFISC")!= null ? rs.getString("CODFISC") : "");
				itens.setString(C_TIPOPROD, rs.getString("TIPOPROD")!= null ? rs.getString("TIPOPROD") : "");
				itens.setFloat(C_VLRISSPED, rs.getFloat("VLRISSVENDA"));
				itens.setFloat(C_VLRPRODPED, rs.getFloat("VLRPRODVENDA"));
			}
			rs.close();
			ps.close();
			if (!con.getAutoCommit())
				con.commit();
			itens.setRow(-1);
			
			sql = "SELECT AUX.CODAUXV, AUX.CPFCLIAUXV, AUX.NOMECLIAUXV, AUX.CIDCLIAUXV, AUX.UFCLIAUXV "+
					"FROM  VDAUXVENDA AUX, VDVENDA V "+
					"WHERE AUX.CODEMP=V.CODEMP AND AUX.CODFILIAL=V.CODFILIAL AND AUX.CODVENDA=V.CODVENDA AND AUX.TIPOVENDA=V.TIPOVENDA " +
					"AND V.CODEMP=? AND V.CODFILIAL=? AND V.CODVENDA=? AND V.TIPOVENDA='V' ";
			ps = con.prepareStatement(sql);
			ps.setInt(1,((Integer) parans.elementAt(0)).intValue());
			ps.setInt(2,((Integer) parans.elementAt(1)).intValue());
			ps.setInt(3,((Integer) parans.elementAt(2)).intValue());
			rs = ps.executeQuery();
			cont++;
			adic = new TabVector(5);
			while (rs.next()) {
				adic.addRow();				
				adic.setInt(C_CODAUXV, rs.getInt("CODAUXV"));
				adic.setString(C_CPFEMITAUX, rs.getString("CPFCLIAUXV")!= null ? rs.getString("CPFCLIAUXV") : "");
				adic.setString(C_NOMEEMITAUX, (rs.getString("NOMECLIAUXV")!=null ? rs.getString("NOMECLIAUXV") : ""));
				adic.setString(C_CIDEMITAUX, (rs.getString("CIDCLIAUXV")!=null ? rs.getString("CIDCLIAUXV") : ""));
				adic.setString(C_UFEMITAUX, (rs.getString("UFCLIAUXV")!=null ? rs.getString("UFCLIAUXV") : ""));
			}
			rs.close();
			ps.close();
			if (!con.getAutoCommit())
				con.commit();
			adic.setRow(-1);
			
			sql = "SELECT I.DTVENCITREC,I.VLRPARCITREC,I.NPARCITREC " +
					"FROM FNRECEBER R, FNITRECEBER I, VDVENDA V " +
					"WHERE R.CODVENDA=V.CODVENDA AND I.CODREC=R.CODREC " +
					"AND V.CODEMP=? AND V.CODFILIAL=? AND V.CODVENDA=? AND V.TIPOVENDA='V' " +
					"ORDER BY I.DTVENCITREC";
			ps = con.prepareStatement(sql);
			ps.setInt(1,((Integer) parans.elementAt(0)).intValue());
			ps.setInt(2,((Integer) parans.elementAt(1)).intValue());
			ps.setInt(3,((Integer) parans.elementAt(2)).intValue());
			rs = ps.executeQuery();
			cont++;
			parc = new TabVector(3);
			while (rs.next()) {
				parc.addRow();
				parc.setDate(C_DTVENCTO, rs.getDate("DTVENCITREC"));
				parc.setFloat(C_VLRPARC, rs.getFloat("VLRPARCITREC"));
				parc.setInt(C_NPARCITREC, rs.getInt("NPARCITREC"));
			}
			rs.close();
			ps.close();
			if (!con.getAutoCommit())
				con.commit();
			parc.setRow(-1);
			
			sql = "SELECT T.CODTRAN, T.RAZTRAN, T.NOMETRAN, T.INSCTRAN, T.CNPJTRAN, T.TIPOTRAN, " +
					"T.ENDTRAN, T.NUMTRAN, T.CIDTRAN, T.UFTRAN , F.TIPOFRETEVD, F.PLACAFRETEVD, " +
					"F.UFFRETEVD, F.QTDFRETEVD, F.ESPFRETEVD, F.MARCAFRETEVD, F.PESOBRUTVD, F.PESOLIQVD, " +
					"V.VLRFRETEVENDA " +
					"FROM VDTRANSP T, VDFRETEVD F, VDVENDA V " +
					"WHERE T.CODEMP=F.CODEMPTN AND T.CODFILIAL=F.CODFILIALTN AND T.CODTRAN=F.CODTRAN " +
					"AND F.CODEMP=V.CODEMP AND F.CODFILIAL=V.CODFILIAL AND F.CODVENDA=V.CODVENDA AND F.TIPOVENDA=V.TIPOVENDA " +
					"AND V.CODEMP=? AND V.CODFILIAL=? AND V.CODVENDA=? AND V.TIPOVENDA='V' ";
			ps = con.prepareStatement(sql);
			ps.setInt(1,((Integer) parans.elementAt(0)).intValue());
			ps.setInt(2,((Integer) parans.elementAt(1)).intValue());
			ps.setInt(3,((Integer) parans.elementAt(2)).intValue());
			rs = ps.executeQuery();
			cont++;
			frete = new TabVector(19);
			while (rs.next()) {
				frete.addRow();
				frete.setInt(C_CODTRAN, rs.getInt("CODTRAN"));
				frete.setString(C_RAZTRANSP, (rs.getString("RAZTRAN")!=null ? rs.getString("RAZTRAN") : ""));
				frete.setString(C_NUMTRANSP, (rs.getString("NOMETRAN")!=null ? rs.getString("NOMETRAN") : ""));
				frete.setString(C_INSCTRANSP, (rs.getString("INSCTRAN")!=null ? rs.getString("INSCTRAN") : ""));
				frete.setString(C_CNPJTRANSP, (rs.getString("CNPJTRAN")!=null ? rs.getString("CNPJTRAN") : ""));
				frete.setString(C_TIPOTRANSP, (rs.getString("TIPOTRAN")!=null ? rs.getString("TIPOTRAN") : ""));
				frete.setString(C_ENDTRANSP, (rs.getString("ENDTRAN")!=null ? rs.getString("ENDTRAN") : ""));
				frete.setInt(C_NUMTRANSP, rs.getInt("NUMTRAN"));
				frete.setString(C_CIDTRANSP, (rs.getString("CIDTRAN")!=null ? rs.getString("CIDTRAN") : ""));
				frete.setString(C_UFTRANSP, (rs.getString("UFTRAN")!=null ? rs.getString("UFTRAN") : ""));
				frete.setString(C_TIPOFRETE, (rs.getString("TIPOFRETEVD")!=null ? rs.getString("TIPOFRETEVD") : ""));
				frete.setString(C_PLACAFRETE, (rs.getString("PLACAFRETEVD")!=null ? rs.getString("PLACAFRETEVD") : ""));
				frete.setString(C_UFFRETE, (rs.getString("UFFRETEVD")!=null ? rs.getString("UFFRETEVD") : ""));
				frete.setFloat(C_QTDFRETE, rs.getFloat("QTDFRETEVD"));
				frete.setString(C_ESPFRETE, (rs.getString("ESPFRETEVD")!=null ? rs.getString("ESPFRETEVD") : ""));
				frete.setString(C_MARCAFRETE, (rs.getString("MARCAFRETEVD")!=null ? rs.getString("MARCAFRETEVD") : ""));
				frete.setFloat(C_PESOBRUTO, rs.getFloat("PESOBRUTVD"));
				frete.setFloat(C_PESOLIQ, rs.getFloat("PESOLIQVD"));
				frete.setFloat(C_VLRFRETEPED, rs.getFloat("VLRFRETEVENDA"));
			}
			rs.close();
			ps.close();
			if (!con.getAutoCommit())
				con.commit();
			frete.setRow(-1);
		}
		catch (SQLException e) {
			Funcoes.mensagemErro(null,"Erro na NFSaida   "+cont+"\n"+e.getMessage());
			retorno = false;
		}
		finally {
			rs = null;
			ps = null;
		}
		return retorno;
	}
	
}
