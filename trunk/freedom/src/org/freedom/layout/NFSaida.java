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

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import org.freedom.componentes.NF;
import org.freedom.componentes.TabVector;
import org.freedom.funcoes.Funcoes;

public class NFSaida extends NF {
	public NFSaida(int casasDec) {
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
					"C.EMAILCLI, C.SITECLI, C.CONTCLI, V.DTEMITVENDA" + 
					"FROM VDVENDA V, VDCLIENTE C" +
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
				cab.setInt(C_CODVEND, rs.getInt("CODVENDA"));
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
				cab.setDate(C_DTEMIT, rs.getDate("DTEMITVENDA"));			
			}
			rs.close();
			ps.close();
			if (!con.getAutoCommit())
				con.commit();
			
			sql = "SELECT I.CODITVENDA, I.CODPROD, P.REFPROD, P.DESCPROD, I.OBSITVENDA, P.CODUNID, " +
					"I.QTDITVENDA, I.VLRLIQITVENDA, I.PERCIPIITVENDA, I.PERCICMSITVENDA, V.VLRICMSVENDA, " +
					"V.VLRIPIVENDA, V.VLRLIQVENDA  " +
					"FROM VDITVENDA I, VDVENDA V, EQPRODUTO P " + 
					"WHERE P.CODEMP=I.CODEMPPD AND P.CODFILIAL=I.CODFILIALPD AND P.CODPROD=I.CODPROD " +
					"AND I.CODEMP=V.CODEMP AND I.CODFILIAL=V.CODFILIAL AND I.CODVENDA=V.CODVENDA AND I.TIPOVENDA=V.TIPOVENDA " +
					"AND V.CODEMP=? AND V.CODFILIAL=? AND V.CODVENDA=? AND I.TIPOVENDA='V' ";
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
				itens.setFloat(C_VLRUNIDITPED, ((new BigDecimal(rs.getString("VLRLIQITVENDA"))).divide(new BigDecimal(rs.getDouble("QTDITVENDA")),2,BigDecimal.ROUND_HALF_UP)).floatValue());
				itens.setFloat(C_VLRLIQITPED, rs.getFloat("VLRLIQITVENDA"));
				itens.setString(C_PERCIPIITPED, rs.getString("PERCIPIITVENDA"));
				itens.setString(C_PERCICMSITPED, rs.getString("PERCICMSITVENDA"));
				itens.setFloat(C_VLRICMSPED, rs.getFloat("VLRICMSVENDA"));
				itens.setFloat(C_VLRIPIPED, rs.getFloat("VLRIPIVENDA"));
				itens.setFloat(C_VLRLIQPED, rs.getFloat("VLRLIQVENDA"));
			}
			rs.close();
			ps.close();
			if (!con.getAutoCommit())
				con.commit();
			
			sql = "SELECT  PG.CODPLANOPAG, I.DIASPE, V.OBSVENDA, VEND.NOMEVEND, VEND.EMAILVEND " +
					"(SELECT T.RAZTRAN FROM VDTRANSP T, VDFRETEVD F WHERE T.CODEMP=F.CODEMPTN AND " +
					"T.CODFILIAL=F.CODFILIALTN AND T.CODTRAN=F.CODTRAN AND F.CODEMP=V.CODEMP AND " +
					"F.CODFILIAL=V.CODFILIAL AND F.TIPOVENDA=V.TIPOVENDA AND F.CODVENDA=V.CODVENDA),"  +
					"(SELECT F.TIPOFRETEVD FROM VDFRETEVD F WHERE F.CODEMP=V.CODEMP AND " + 
					"F.CODFILIAL=V.CODFILIAL AND F.TIPOVENDA=V.TIPOVENDA AND F.CODVENDA=V.CODVENDA)," +
					"(SELECT FN.DESCFUNC FROM RHFUNCAO FN WHERE FN.CODEMP=VEND.CODEMPFU AND " +
					"FN.CODFILIAL=VEND.CODFILIALFU AND FN.CODFUNC=VEND.CODFUNC) " +
					"FROM VDVENDA V, VDITVENDA I, VDVENDEDOR VEND, FNPLANOPAG PG, " +
					"WHERE I.CODEMP=V.CODEMP AND I.CODFILIAL=V.CODFILIAL AND I.CODVENDA=V.CODVENDA AND I.TIPOVENDA=V.TIPOVENDA " +
					"AND PG.CODEMP=V.CODEMPPG AND PG.CODFILIAL=V.CODFILALPG AND PG.CODPLANOPAG=V.CODPLANOPAG " +
					"AND VEND.CODEMP=V.CODEMPVD AND VEND.CODFILIAL=V.CODFILIALVD AND VEND.CODVEND=V.CODVEND " +
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
				adic.setInt(C_DDDEMIT, rs.getInt("DIASPE"));
				adic.setString(C_OBSPED, rs.getString("OBSVENDA"));
				adic.setString(C_NOMEVEND, rs.getString("NOMEVEND"));
				adic.setString(C_EMAILVEND, rs.getString("EMAILVEND"));
				adic.setString(C_RAZTRANSP, rs.getString("RAZTRAN"));
				adic.setString(C_TIPOFRETE, rs.getString("TIPOFRETEVD"));
				adic.setString(C_DESCFUNC, rs.getString("DESCFUNC"));
			}
			rs.close();
			ps.close();
			if (!con.getAutoCommit())
			con.commit();
		}
		catch (SQLException e) {
			retorno = false;
		}
		finally {
			rs = null;
			ps = null;
		}
		return retorno;
	}
	
}
