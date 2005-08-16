/**
 * @version 08/08/2005 <BR>
 * @author Setpoint Informática Ltda./Robson Sanchez <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.layout <BR>
 * Classe: @(#)NFEntrada.java <BR>
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

public class NFEntrada extends NF {
	public NFEntrada(int casasDec) {
		super(casasDec);		
	}
	
	public boolean carregaTabelas(Connection con, Vector parans ) {
		boolean retorno = true;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = null;
		try {
			sql = "SELECT V.CODVENDA, V.CODCLI, C.RAZCLI, C.CNPJCLI, C.CPFCLI, C.ENDCLI, C.NUMCLI, C.COMPLCLI," +
					"C.BAIRCLI, C.CEPCLI, C.CIDCLI, C.UFCLI, C.FONECLI, C.FAXCLI, C.DDDCLI, C.INSCCLI, C.RGCLI," +
					"C.EMAILCLI, C.SITECLI, C.CONTCLI, V.DTEMITVENDA, V.DOCVENDA, C.INCRACLI, V.DTSAIDAVENDA " + 
					"FROM VDVENDA V, VDCLIENTE C " +
					"WHERE C.CODEMP=V.CODEMPCL AND C.CODFILIAL=V.CODFILIALCL AND " +
					"C.CODCLI=V.CODCLI AND V.CODEMP=? AND V.CODFILIAL=? AND V.TIPOVENDA='V' AND V.CODVENDA=?";
			ps = con.prepareStatement(sql);
			ps.setInt(1,((Integer) parans.elementAt(0)).intValue());
			ps.setInt(2,((Integer) parans.elementAt(1)).intValue());
			ps.setInt(3,((Integer) parans.elementAt(2)).intValue());
			rs = ps.executeQuery();
			cab = new TabVector(2);
			while (rs.next()) {
				cab.addRow();
				cab.setInt(C_CODPED, rs.getInt("CODVENDA"));
				cab.setInt(C_CODEMIT, rs.getInt("CODCLI"));
				cab.setString(C_RAZEMIT, rs.getString("RAZCLI"));
				cab.setString(C_CNPJEMIT, rs.getString("CNPJCLI"));
				cab.setString(C_CPFEMIT, rs.getString("CPFCLI"));
				cab.setString(C_ENDEMIT, rs.getString("ENDCLI"));
				cab.setInt(C_NUMEMIT, rs.getInt("NUMCLI"));
				cab.setString(C_COMPLEMIT, rs.getString("COMPLCLI"));
				cab.setString(C_BAIREMIT, rs.getString("BAIRCLI"));
				cab.setString(C_CEPEMIT, rs.getString("CEPCLI"));
				cab.setString(C_CIDEMIT, rs.getString("CIDCLI"));
				cab.setString(C_UFEMIT, rs.getString("UFCLI"));
				cab.setString(C_FONEEMIT, rs.getString("FONECLI"));
				cab.setString(C_FAXEMIT, rs.getString("FAXCLI"));
				cab.setString(C_DDDEMIT, rs.getString("DDDCLI"));
				cab.setString(C_INSCEMIT, rs.getString("INSCCLI"));
				cab.setString(C_RGEMIT, rs.getString("RGCLI"));
				cab.setString(C_EMAILEMIT, rs.getString("EMAILCLI"));
				cab.setString(C_SITEEMIT, rs.getString("SITECLI"));
				cab.setString(C_CONTEMIT, rs.getString("CONTCLI"));
				cab.setDate(C_DTEMITPED, rs.getDate("DTEMITVENDA"));
				cab.setInt(C_DOC, rs.getInt("DOCVENDA"));
				cab.setString(C_INCRAEMIT, rs.getString("INCRACLI"));
				cab.setDate(C_DTSAIDA, rs.getDate("DTSAIDAVENDA"));
			}
			rs.close();
			ps.close();
			if (!con.getAutoCommit())
				con.commit();
			
			sql = "SELECT I.CODITVENDA, I.CODPROD, P.REFPROD, P.DESCPROD, I.OBSITVENDA, P.CODUNID, " +
					"I.QTDITVENDA, I.VLRLIQITVENDA, I.PERCIPIITVENDA, I.PERCICMSITVENDA, V.VLRICMSVENDA, " +
					"V.VLRIPIVENDA, V.VLRLIQVENDA, N.IMPDTSAIDANAT, I.VLRPRODITVENDA, N.DESCNAT, N.CODNAT, " +
					"L.CODLOTE, L.VENCTOLOTE, I.ORIGFISC, I.CODTRATTRIB, V.VLRBASEICMSVENDA, V.VLRADICVENDA " +
					"FROM VDITVENDA I, VDVENDA V, EQPRODUTO P, LFNATOPER N, EQLOTE L " + 
					"WHERE P.CODEMP=I.CODEMPPD AND P.CODFILIAL=I.CODFILIALPD AND P.CODPROD=I.CODPROD " +
					"AND L.CODEMP=P.CODPROD AND L.CODFILIAL=P.CODFILIAL AND L.CODPROD=P.CODPROD " +
					"AND N.CODEMP=I.CODEMPNT AND N.CODFILIAL=I.CODFILIALNT AND N.CODNAT=I.CODNAT " +
					"AND I.CODEMP=V.CODEMP AND I.CODFILIAL=V.CODFILIAL AND I.CODVENDA=V.CODVENDA AND I.TIPOVENDA=V.TIPOVENDA " +
					"AND V.CODEMP=? AND V.CODFILIAL=? AND V.CODVENDA=? AND V.TIPOVENDA='V' ";
			ps = con.prepareStatement(sql);
			ps.setInt(1,((Integer) parans.elementAt(0)).intValue());
			ps.setInt(2,((Integer) parans.elementAt(1)).intValue());
			ps.setInt(3,((Integer) parans.elementAt(2)).intValue());
			rs = ps.executeQuery();
			itens = new TabVector(2);
			while (rs.next()) {
				itens.addRow();
				itens.setInt(C_CODITPED, rs.getInt("CODITVENDA"));
				itens.setInt(C_CODPROD, rs.getInt("CODPROD"));
				itens.setString(C_REFPROD, rs.getString("REFPROD"));
				itens.setString(C_DESCPROD, rs.getString("DESCPROD"));
				itens.setString(C_OBSITPED, rs.getString("OBSITVENDA"));
				itens.setString(C_CODUNID, rs.getString("CODUNID"));
				itens.setFloat(C_QTDITPED, rs.getFloat("QTDITVENDA"));
				itens.setFloat(C_VLRLIQITPED, rs.getFloat("VLRLIQITVENDA"));
				itens.setString(C_PERCIPIITPED, rs.getString("PERCIPIITVENDA"));
				itens.setString(C_PERCICMSITPED, rs.getString("PERCICMSITVENDA"));
				itens.setFloat(C_VLRICMSPED, rs.getFloat("VLRICMSVENDA"));
				itens.setFloat(C_VLRIPIPED, rs.getFloat("VLRIPIVENDA"));
				itens.setFloat(C_VLRLIQPED, rs.getFloat("VLRLIQVENDA"));
				itens.setInt(C_IMPDTSAIDA, rs.getInt("IMPDTSAIDANAT"));
				itens.setFloat(C_VLRPRODITPED, rs.getFloat("VLRPRODITVENDA"));
				itens.setString(C_DESCNAT, rs.getString("DESCNAT"));
				itens.setInt(C_CODNAT, rs.getInt("CODNAT"));
				itens.setString(C_CODLOTE, rs.getString("CODLOTE"));
				itens.setDate(C_VENCLOTE, rs.getDate("VENCTOLOTE"));
				itens.setString(C_ORIGFISC, rs.getString("ORIGFISC"));
				itens.setString(C_CODTRATTRIB, rs.getString("CODTRATTRIB"));
				itens.setFloat(C_VLRBASEICMSPED, rs.getFloat("VLRBASEICMSVENDA"));
				itens.setFloat(C_VLRADICPED, rs.getFloat("VLRADICVENDA"));
			}
			rs.close();
			ps.close();
			if (!con.getAutoCommit())
				con.commit();
			
			sql = "SELECT  PG.CODPLANOPAG, I.DIASPE, V.OBSVENDA, VEND.NOMEVEND, VEND.EMAILVEND, " +	
					"(SELECT FN.DESCFUNC FROM RHFUNCAO FN WHERE FN.CODEMP=VEND.CODEMPFU AND " +
					"FN.CODFILIAL=VEND.CODFILIALFU AND FN.CODFUNC=VEND.CODFUNC)," +
					"AX.CODAUXV, AX.CPFCLIAUXV, AX.NOMECLIAUXV, AX.CIDCLIAUXV, AX.UFCLIAUXV, " +
					"V.CODCLCOMIS, V.PERCCOMISVENDA " +
					"FROM VDVENDA V, VDITVENDA I, VDVENDEDOR VEND, FNPLANOPAG PG, VDAUXVENDA AX " +
					"WHERE I.CODEMP=V.CODEMP AND I.CODFILIAL=V.CODFILIAL AND I.CODVENDA=V.CODVENDA AND I.TIPOVENDA=V.TIPOVENDA " +
					"AND PG.CODEMP=V.CODEMPPG AND PG.CODFILIAL=V.CODFILIALPG AND PG.CODPLANOPAG=V.CODPLANOPAG " +
					"AND VEND.CODEMP=V.CODEMPVD AND VEND.CODFILIAL=V.CODFILIALVD AND VEND.CODVEND=V.CODVEND " +
					"AND AX.CODEMP=V.CODEMP AND AX.CODFILIAL=V.CODFILIAL AND AX.CODVENDA=V.CODVENDA AND AX.TIPOVENDA=V.TIPOVENDA " +
					"AND V.CODEMP=? AND V.CODFILIAL=? AND V.CODVENDA=? AND V.TIPOVENDA='V' ";
			ps = con.prepareStatement(sql);
			ps.setInt(1,((Integer) parans.elementAt(0)).intValue());
			ps.setInt(2,((Integer) parans.elementAt(1)).intValue());
			ps.setInt(3,((Integer) parans.elementAt(2)).intValue());
			rs = ps.executeQuery();
			adic = new TabVector(2);
			while (rs.next()) {
				adic.addRow();
				adic.setString(C_CODPLANOPG, rs.getString("CODPLANOPAG"));
				adic.setInt(C_DIASPE, rs.getInt("DIASPE"));
				adic.setString(C_OBSPED, rs.getString("OBSVENDA"));
				adic.setString(C_NOMEVEND, rs.getString("NOMEVEND"));
				adic.setString(C_EMAILVEND, rs.getString("EMAILVEND"));
				adic.setString(C_DESCFUNC, rs.getString("DESCFUNC"));
				adic.setInt(C_CODAUXV, rs.getInt("CODAUXV"));
				adic.setInt(C_CPFEMITAUX, rs.getInt("CPFCLIAUXV"));
				adic.setString(C_NOMEEMITAUX, rs.getString("NOMECLIAUXV"));
				adic.setString(C_CIDEMITAUX, rs.getString("CIDCLIAUXV"));
				adic.setString(C_UFEMITAUX, rs.getString("UFCLIAUXV"));
				adic.setString(C_CODCLCOMIS, rs.getString("CODCLCOMIS"));
				adic.setFloat(C_PERCCOMISVENDA, rs.getFloat("PERCCOMISVENDA"));
			}
			rs.close();
			ps.close();
			if (!con.getAutoCommit())
			con.commit();
			
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
			parc = new TabVector(2);
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
			frete = new TabVector(2);
			while (rs.next()) {
				frete.addRow();
				frete.setInt(C_CODTRAN, rs.getInt("CODTRAN"));
				frete.setString(C_RAZTRANSP, rs.getString("RAZTRAN"));
				frete.setString(C_NUMTRANSP, rs.getString("NOMETRAN"));
				frete.setString(C_INSCTRANSP, rs.getString("INSCTRAN"));
				frete.setString(C_CNPJTRANSP, rs.getString("CNPJTRAN"));
				frete.setString(C_TIPOTRANSP, rs.getString("TIPOTRAN"));
				frete.setString(C_ENDTRANSP, rs.getString("ENDTRAN"));
				frete.setInt(C_NUMTRANSP, rs.getInt("NUMTRAN"));
				frete.setString(C_CIDTRANSP, rs.getString("CIDTRAN"));
				frete.setString(C_UFTRANSP, rs.getString("UFTRAN"));
				frete.setString(C_TIPOFRETE, rs.getString("TIPOFRETEVD"));
				frete.setString(C_PLACAFRETE, rs.getString("PLACAFRETEVD"));
				frete.setString(C_UFFRETE, rs.getString("UFFRETEVD"));
				frete.setFloat(C_QTDFRETE, rs.getFloat("QTDFRETEVD"));
				frete.setString(C_ESPFRETE, rs.getString("ESPFRETEVD"));
				frete.setString(C_MARCAFRETE, rs.getString("MARCAFRETEVD"));
				frete.setFloat(C_PESOBRUTO, rs.getFloat("PESOBRUTVD"));
				frete.setFloat(C_PESOLIQ, rs.getFloat("PESOLIQVD"));
				frete.setFloat(C_VLRFRETEPED, rs.getFloat("VLRFRETEVENDA"));
			}
			rs.close();
			ps.close();
			if (!con.getAutoCommit())
				con.commit();
		}
		catch (SQLException e) {
			Funcoes.mensagemErro(null,"Erro na NFSaida\n"+e.getMessage());
			retorno = false;
		}
		finally {
			rs = null;
			ps = null;
		}
		return retorno;
	}
}
