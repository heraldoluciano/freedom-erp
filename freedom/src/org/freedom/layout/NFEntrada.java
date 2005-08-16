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
			sql = "SELECT C.CODCOMPRA, C.CODFOR, F.RAZFOR,  F.CNPJFOR, F.CPFFOR, F.ENDFOR, F.NUMFOR, F.COMPLFOR," +
					"F.BAIRFOR, F.CEPFOR, F.CIDFOR, F.UFFOR, F.FONEFOR, F.FAXFOR, F.DDDFONEFOR, F.INSCFOR, F.RGFOR," +
					"F.EMAILFOR, F.SITEFOR, F.CONTFOR, C.DTEMITCOMPRA, C.DOCCOMPRA, C.DTENTCOMPRA " + 
					"FROM CPCOMPRA C, CPFORNECED F  " +
					"WHERE F.CODEMP=C.CODEMPFR AND F.CODFILIAL=C.CODFILIALFR AND F.CODFOR=C.CODFOR " +
					"AND C.CODEMP=? AND C.CODFILIAL=? AND C.CODCOMPRA=?";
			ps = con.prepareStatement(sql);
			ps.setInt(1,((Integer) parans.elementAt(0)).intValue());
			ps.setInt(2,((Integer) parans.elementAt(1)).intValue());
			ps.setInt(3,((Integer) parans.elementAt(2)).intValue());
			rs = ps.executeQuery();
			cab = new TabVector(24);
			while (rs.next()) {
				cab.addRow();
				cab.setInt(C_CODPED, rs.getInt("CODCOMPRA"));
				cab.setInt(C_CODEMIT, rs.getInt("CODFOR"));
				cab.setString(C_RAZEMIT, rs.getString("RAZFOR"));
				cab.setString(C_CNPJEMIT, rs.getString("CNPJFOR"));
				cab.setString(C_CPFEMIT, rs.getString("CPFFOR"));
				cab.setString(C_ENDEMIT, rs.getString("ENDFOR"));
				cab.setInt(C_NUMEMIT, rs.getInt("NUMFOR"));
				cab.setString(C_COMPLEMIT, rs.getString("COMPLFOR"));
				cab.setString(C_BAIREMIT, rs.getString("BAIRFOR"));
				cab.setString(C_CEPEMIT, rs.getString("CEPFOR"));
				cab.setString(C_CIDEMIT, rs.getString("CIDFOR"));
				cab.setString(C_UFEMIT, rs.getString("UFFOR"));
				cab.setString(C_FONEEMIT, rs.getString("FONEFOR"));
				cab.setString(C_FAXEMIT, rs.getString("FAXFOR"));
				cab.setString(C_DDDEMIT, rs.getString("DDDFONEFOR"));
				cab.setString(C_INSCEMIT, rs.getString("INSCFOR"));
				cab.setString(C_RGEMIT, rs.getString("RGFOR"));
				cab.setString(C_EMAILEMIT, rs.getString("EMAILFOR"));
				cab.setString(C_SITEEMIT, rs.getString("SITEFOR"));
				cab.setString(C_CONTEMIT, rs.getString("CONTFOR"));
				cab.setDate(C_DTEMITPED, rs.getDate("DTEMITCOMPRA"));
				cab.setInt(C_DOC, rs.getInt("DOCCOMPRA"));
				cab.setString(C_INCRAEMIT, "");
				cab.setDate(C_DTSAIDA, rs.getDate("DTENTCOMPRA"));
			}
			rs.close();
			ps.close();
			if (!con.getAutoCommit())
				con.commit();
			
			sql = "SELECT I.CODITCOMPRA, I.CODPROD, P.REFPROD, P.DESCPROD, P.CODUNID, " +
					"I.QTDITCOMPRA, I.VLRLIQITCOMPRA, I.PERCIPIITCOMPRA, I.PERCICMSITCOMPRA, C.VLRICMSCOMPRA, " +
					"C.VLRIPICOMPRA, C.VLRLIQCOMPRA, N.IMPDTSAIDANAT, I.VLRPRODITCOMPRA, N.DESCNAT, N.CODNAT, " +
					"L.CODLOTE, L.VENCTOLOTE, C.VLRBASEICMSCOMPRA, C.VLRADICCOMPRA " +
					"FROM CPITCOMPRA I, CPCOMPRA C, EQPRODUTO P, LFNATOPER N, EQLOTE L " + 
					"WHERE I.CODEMP=C.CODEMP AND I.CODFILIAL=C.CODFILIAL AND I.CODCOMPRA=C.CODCOMPRA " +
					"AND N.CODEMP=I.CODEMPNT AND N.CODFILIAL=I.CODFILIALNT AND N.CODNAT=I.CODNAT " +
					"AND P.CODEMP=I.CODEMPPD AND P.CODFILIAL=I.CODFILIALPD AND P.CODPROD=I.CODPROD " +
					"AND L.CODEMP=P.CODEMP AND L.CODFILIAL=P.CODFILIAL AND L.CODPROD=P.CODPROD " +					
					"AND C.CODEMP=? AND C.CODFILIAL=? AND C.CODCOMPRA=? ";
			ps = con.prepareStatement(sql);
			ps.setInt(1,((Integer) parans.elementAt(0)).intValue());
			ps.setInt(2,((Integer) parans.elementAt(1)).intValue());
			ps.setInt(3,((Integer) parans.elementAt(2)).intValue());
			rs = ps.executeQuery();
			itens = new TabVector(23);
			while (rs.next()) {
				itens.addRow();
				itens.setInt(C_CODITPED, rs.getInt("CODITCOMPRA"));
				itens.setInt(C_CODPROD, rs.getInt("CODPROD"));
				itens.setString(C_REFPROD, rs.getString("REFPROD"));
				itens.setString(C_DESCPROD, rs.getString("DESCPROD"));
				itens.setString(C_OBSITPED, "");
				itens.setString(C_CODUNID, rs.getString("CODUNID"));
				itens.setFloat(C_QTDITPED, rs.getFloat("QTDITCOMPRA"));
				itens.setFloat(C_VLRLIQITPED, rs.getFloat("VLRLIQITCOMPRA"));
				itens.setString(C_PERCIPIITPED, rs.getString("PERCIPIITCOMPRA"));
				itens.setString(C_PERCICMSITPED, rs.getString("PERCICMSITCOMPRA"));
				itens.setFloat(C_VLRICMSPED, rs.getFloat("VLRICMSCOMPRA"));
				itens.setFloat(C_VLRIPIPED, rs.getFloat("VLRIPICOMPRA"));
				itens.setFloat(C_VLRLIQPED, rs.getFloat("VLRLIQCOMPRA"));
				itens.setString(C_IMPDTSAIDA, rs.getString("IMPDTSAIDANAT"));
				itens.setFloat(C_VLRPRODITPED, rs.getFloat("VLRPRODITCOMPRA"));
				itens.setString(C_DESCNAT, rs.getString("DESCNAT"));
				itens.setInt(C_CODNAT, rs.getInt("CODNAT"));
				itens.setString(C_CODLOTE, rs.getString("CODLOTE"));
				itens.setDate(C_VENCLOTE, rs.getDate("VENCTOLOTE"));
				itens.setString(C_ORIGFISC, "");
				itens.setString(C_CODTRATTRIB, "");
				itens.setFloat(C_VLRBASEICMSPED, rs.getFloat("VLRBASEICMSCOMPRA"));
				itens.setFloat(C_VLRADICPED, rs.getFloat("VLRADICCOMPRA"));
			}
			rs.close();
			ps.close();
			if (!con.getAutoCommit())
				con.commit();
				
			sql = "SELECT  PG.CODPLANOPAG, DESCPLANOPAG " +
					"FROM CPCOMPRA C, CPITCOMPRA I, FNPLANOPAG PG " +
					"WHERE I.CODEMP=C.CODEMP AND C.CODFILIAL=C.CODFILIAL AND I.CODCOMPRA=C.CODCOMPRA " +
					"AND PG.CODEMP=C.CODEMPPG AND PG.CODFILIAL=C.CODFILIALPG AND PG.CODPLANOPAG=C.CODPLANOPAG " +
					"AND C.CODEMP=? AND C.CODFILIAL=? AND C.CODCOMPRA=? ";
			ps = con.prepareStatement(sql);
			ps.setInt(1,((Integer) parans.elementAt(0)).intValue());
			ps.setInt(2,((Integer) parans.elementAt(1)).intValue());
			ps.setInt(3,((Integer) parans.elementAt(2)).intValue());
			rs = ps.executeQuery();
			adic = new TabVector(14);
			while (rs.next()) {
				adic.addRow();
				adic.setInt(C_CODPLANOPG, rs.getInt("CODPLANOPAG"));
				adic.setString(C_DESCPLANOPAG, rs.getString("DESCPLANOPAG"));
				adic.setInt(C_DIASPE, 0);
				adic.setString(C_OBSPED, "");
				adic.setString(C_NOMEVEND, "");
				adic.setString(C_EMAILVEND, "");
				adic.setString(C_DESCFUNC, "");
				adic.setInt(C_CODAUXV, 0);
				adic.setInt(C_CPFEMITAUX, 0);
				adic.setString(C_NOMEEMITAUX, "");
				adic.setString(C_CIDEMITAUX, "");
				adic.setString(C_UFEMITAUX, "");
				adic.setString(C_CODCLCOMIS, "");
				adic.setFloat(C_PERCCOMISVENDA, 0);
			}
			rs.close();
			ps.close();
			if (!con.getAutoCommit())
			con.commit();
						
			parc = new TabVector(3);
			parc.addRow();
			parc.setDate(C_DTVENCTO, null);
			parc.setFloat(C_VLRPARC, 0);
			parc.setInt(C_NPARCITREC, 0);
						
			frete = new TabVector(19);
			frete.addRow();
			frete.setInt(C_CODTRAN, 0);
			frete.setString(C_RAZTRANSP, "");
			frete.setString(C_NUMTRANSP, "");
			frete.setString(C_INSCTRANSP, "");
			frete.setString(C_CNPJTRANSP, "");
			frete.setString(C_TIPOTRANSP, "");
			frete.setString(C_ENDTRANSP, "");
			frete.setInt(C_NUMTRANSP, 0);
			frete.setString(C_CIDTRANSP,"");
			frete.setString(C_UFTRANSP, "");
			frete.setString(C_TIPOFRETE, "");
			frete.setString(C_PLACAFRETE, "");
			frete.setString(C_UFFRETE, "");
			frete.setFloat(C_QTDFRETE, 0);
			frete.setString(C_ESPFRETE, "");
			frete.setString(C_MARCAFRETE, "");
			frete.setFloat(C_PESOBRUTO, 0);
			frete.setFloat(C_PESOLIQ, 0);
			frete.setFloat(C_VLRFRETEPED, 0);
			
		}
		catch (SQLException e) {
			Funcoes.mensagemErro(null,"Erro na NFEntrada\n"+e.getMessage());
			retorno = false;
		}
		finally {
			rs = null;
			ps = null;
		}
		return retorno;
	}
}
