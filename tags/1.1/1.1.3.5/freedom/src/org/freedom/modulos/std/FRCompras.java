/**
 * @version 08/12/2000 <BR>
 * @author Setpoint Informática Ltda./Alex Rodrigues <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FRCompras.java <BR>
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

package org.freedom.modulos.std;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import org.freedom.componentes.JLabelPad;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FRelatorio;

public class FRCompras extends FRelatorio {
	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtDataini = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
	private JTextFieldPad txtDatafim = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
	private JTextFieldPad txtCodFor = new JTextFieldPad(JTextFieldPad.TP_INTEGER,10,0);
	private JTextFieldFK txtDescFor = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
	private JTextFieldPad txtCodPlanoPag = new JTextFieldPad(JTextFieldPad.TP_INTEGER,10,0);
	private JTextFieldFK txtDescPlanoPag = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
	private ListaCampos lcFor = new ListaCampos(this);
	private ListaCampos lcPlanoPag = new ListaCampos(this);
	
	public FRCompras() {
		setTitulo("Compras por Fornecedor");
		setAtribos(50,50,320,220);

		lcFor.add(new GuardaCampo( txtCodFor, "CodFor", "Cód.for.", ListaCampos.DB_PK, false));
		lcFor.add(new GuardaCampo( txtDescFor, "RazFor", "Razão social do fornecedor", ListaCampos.DB_SI,false));
		txtCodFor.setTabelaExterna(lcFor);
		txtCodFor.setNomeCampo("CodFor");
		txtCodFor.setFK(true);
		lcFor.setReadOnly(true);
		lcFor.montaSql(false, "FORNECED", "CP");
		
		lcPlanoPag.add(new GuardaCampo( txtCodPlanoPag, "CodPlanoPag", "Cód.plano.pag.", ListaCampos.DB_PK, false));
		lcPlanoPag.add(new GuardaCampo( txtDescPlanoPag, "DescPlanoPag", "Descrição do plano de pagamento", ListaCampos.DB_SI,false));
		txtCodPlanoPag.setTabelaExterna(lcPlanoPag);
		txtCodPlanoPag.setNomeCampo("CodPlanoPag");
		txtCodPlanoPag.setFK(true);
		lcPlanoPag.setReadOnly(true);
		lcPlanoPag.montaSql(false, "PLANOPAG", "FN");

		adic(new JLabelPad("Periodo:"),7,5,120,20);
		adic(new JLabelPad("De:"),7,25,30,20);
		adic(txtDataini,40,25,97,20);
		adic(new JLabelPad("Até:"),152,25,37,20);
		adic(txtDatafim,190,25,100,20);
		adic(new JLabelPad("Cód.for."),7,50,80,20);
		adic(txtCodFor,7,70,80,20);
		adic(new JLabelPad("Descrição do fornecedor"),90,50,200,20);
		adic(txtDescFor,90,70,200,20);		
		adic(new JLabelPad("Cód.pl.pag."),7,90,80,20);
		adic(txtCodPlanoPag,7,110,80,20);
		adic(new JLabelPad("Descrição do plano de pagamento"),90,90,200,20);
		adic(txtDescPlanoPag,90,110,200,20);
		
		Calendar cPeriodo = Calendar.getInstance();
	    txtDatafim.setVlrDate(cPeriodo.getTime());
		cPeriodo.set(Calendar.DAY_OF_MONTH,cPeriodo.get(Calendar.DAY_OF_MONTH)-30);
		txtDataini.setVlrDate(cPeriodo.getTime());
	}
	
	public void setConexao(Connection cn) {
		super.setConexao(cn);
		lcFor.setConexao(cn);
		lcPlanoPag.setConexao(cn);
	}
	
	public void imprimir(boolean bVisualizar) {
		if (txtDatafim.getVlrDate().before(txtDataini.getVlrDate())) {
			Funcoes.mensagemInforma(this,"Data final maior que a data inicial!");
			return;
		}

		PreparedStatement ps = null;
		ResultSet rs = null;
		String sSQL = null;
		String sWhere = "";
		BigDecimal bTotal = null;
		ImprimeOS imp = null;
		int linPag = 0;
		int iparam = 1;
		int iCab = 7;
		int iCodCompra = 0;		
		
		try {
			
			imp = new ImprimeOS("",con);
			linPag = imp.verifLinPag()-1;
			imp.montaCab();
			imp.setTitulo("Relatório de Compras");
			imp.addSubTitulo("RELATORIO DE COMPRAS");
			imp.addSubTitulo("PERIODO DE: "+txtDataini.getVlrString()+" Até: "+txtDatafim.getVlrString());
			imp.limpaPags();	
			
			bTotal = new BigDecimal("0");
			
			if (txtCodFor.getVlrInteger().intValue() > 0) {
				sWhere += " AND C.CODFOR = " + txtCodFor.getVlrInteger().intValue();
				imp.addSubTitulo("FORNECEDOR : " + txtDescFor.getVlrString());		
				iCab++;
			}
			if (txtCodPlanoPag.getVlrInteger().intValue() > 0) {
				sWhere += " AND C.CODPLANOPAG = " + txtCodPlanoPag.getVlrInteger().intValue();
				imp.addSubTitulo("PLANO DE PAGAMENTO: " + txtDescPlanoPag.getVlrString());		
				iCab++;
			}
			
			sSQL = "SELECT C.CODCOMPRA, C.DOCCOMPRA, C.DTEMITCOMPRA, C.DTENTCOMPRA, C.VLRLIQCOMPRA, " +
				   "F.NOMEFOR, PG.DESCPLANOPAG, "+
				   "IT.CODITCOMPRA, IT.CODPROD, PD.DESCPROD, IT.CODLOTE, IT.QTDITCOMPRA, "+
				   "IT.VLRLIQITCOMPRA, IT.PERCDESCITCOMPRA, IT.VLRDESCITCOMPRA, IT.VLRLIQITCOMPRA "+
				   "FROM CPCOMPRA C, CPITCOMPRA IT, CPFORNECED F, FNPLANOPAG PG, EQPRODUTO PD "+
				   "WHERE C.CODEMP=? AND C.CODFILIAL=? "+
				   "AND C.CODEMPFR=F.CODEMP AND C.CODFILIALFR=F.CODFILIAL AND C.CODFOR=F.CODFOR "+
				   "AND C.CODEMPPG=PG.CODEMP AND C.CODFILIALPG=PG.CODFILIAL AND C.CODPLANOPAG=PG.CODPLANOPAG "+
				   "AND C.CODEMP=IT.CODEMP AND C.CODFILIAL=IT.CODFILIAL AND C.CODCOMPRA=IT.CODCOMPRA "+
				   "AND IT.CODEMPPD=PD.CODEMP AND IT.CODFILIALPD=PD.CODFILIAL AND IT.CODPROD=PD.CODPROD "+
				   "AND C.DTEMITCOMPRA BETWEEN ? AND ? "+
				   sWhere+
				   " ORDER BY C.CODCOMPRA, IT.CODITCOMPRA";
			
			
			ps = con.prepareStatement(sSQL);
			ps.setInt(iparam++, Aplicativo.iCodEmp);
			ps.setInt(iparam++, ListaCampos.getMasterFilial("CPCOMPRA"));
			ps.setDate(iparam++, Funcoes.strDateToSqlDate(txtDataini.getVlrString()));
			ps.setDate(iparam++, Funcoes.strDateToSqlDate(txtDatafim.getVlrString()));
			rs = ps.executeQuery();
			while( rs.next() ) {
            	if(imp.pRow() >= linPag) {
                    imp.say(imp.pRow()+1, 0, imp.comprimido());
                    imp.say(imp.pRow(),0, "+" + Funcoes.replicate("-",133) + "+");
                    imp.eject();
                    imp.incPags();
            	}
				if(imp.pRow() == 0) {
					imp.impCab(136, true);
        			imp.say(imp.pRow(), 0, imp.comprimido());
					imp.say(imp.pRow(), 0, "|" + Funcoes.replicate("=",133) + "|");
				}
            	if(iCodCompra != rs.getInt("CODCOMPRA")) {
            		iCodCompra = rs.getInt("CODCOMPRA");
            		if(imp.pRow() > iCab) {
            			imp.say(imp.pRow()+1, 0, imp.comprimido());
						imp.say(imp.pRow(), 0, "|" + Funcoes.replicate("=",133) + "|");
            		}
					imp.say(imp.pRow()+1, 0, imp.comprimido());
					imp.say(imp.pRow(), 0, "| N. Compra");
					imp.say(imp.pRow(), 13, "| Doc");
					imp.say(imp.pRow(), 22, "| Fornecedor");
					imp.say(imp.pRow(), 58, "| Plano de Pagamento");
					imp.say(imp.pRow(), 94, "| Valor");
					imp.say(imp.pRow(),109, "|  Emissao");
					imp.say(imp.pRow(),122, "|  Entrada");
					imp.say(imp.pRow(),135, "|");
					imp.say(imp.pRow()+1, 0, imp.comprimido());
					imp.say(imp.pRow(), 0, "|" + Funcoes.replicate("-",133) + "|");
					imp.say(imp.pRow()+1, 0, imp.comprimido());
					imp.say(imp.pRow(), 0, "| " + (rs.getString("CODCOMPRA") != null ? rs.getString("CODCOMPRA") : ""));
					imp.say(imp.pRow(), 13, "| " + (rs.getString("DOCCOMPRA") != null ? rs.getString("DOCCOMPRA") : ""));
					imp.say(imp.pRow(), 22, "| " + (rs.getString("NOMEFOR") != null ? (rs.getString("NOMEFOR").length()>34 ? rs.getString("NOMEFOR").substring(0,34) : rs.getString("NOMEFOR")) : ""));
					imp.say(imp.pRow(), 58, "| " + (rs.getString("DESCPLANOPAG") != null ? (rs.getString("DESCPLANOPAG").length()>34 ? rs.getString("DESCPLANOPAG").substring(0,34) : rs.getString("DESCPLANOPAG")) : ""));
					imp.say(imp.pRow(), 94, "| " + Funcoes.strDecimalToStrCurrency(10,2,(rs.getString("VLRLIQCOMPRA") != null ? rs.getString("VLRLIQCOMPRA") : "")));
					imp.say(imp.pRow(),109, "| " + Funcoes.dateToStrDate(rs.getDate("DTEMITCOMPRA")));
					imp.say(imp.pRow(),122, "| " + Funcoes.dateToStrDate(rs.getDate("DTENTCOMPRA")));
					imp.say(imp.pRow(),135, "|");
					imp.say(imp.pRow()+1, 0, imp.comprimido());
					imp.say(imp.pRow(), 0, "|" + Funcoes.replicate("-",133) + "|");
					imp.say(imp.pRow()+1, 0, imp.comprimido());
					imp.say(imp.pRow(), 0, "| Item");
					imp.say(imp.pRow(), 8, "| Cod.prod.");
					imp.say(imp.pRow(), 22, "| Descricao do produto");
					imp.say(imp.pRow(), 75, "| Lote");
					imp.say(imp.pRow(), 91, "| Qtd");
					imp.say(imp.pRow(),102, "| Vlr.Item");
					imp.say(imp.pRow(),115, "|% Desc");
					imp.say(imp.pRow(),122, "|  Vlr.desc");
					imp.say(imp.pRow(),135, "|");
					imp.say(imp.pRow()+1, 0, imp.comprimido());
					imp.say(imp.pRow(), 0, "|" + Funcoes.replicate("-",133) + "|");
            	}
            	imp.say(imp.pRow()+1, 0, imp.comprimido());
				imp.say(imp.pRow(), 0, "| " + (rs.getString("CODITCOMPRA") != null ? rs.getString("CODITCOMPRA") : ""));
				imp.say(imp.pRow(), 8, "| " + (rs.getString("CODPROD") != null ? rs.getString("CODPROD") : ""));
				imp.say(imp.pRow(), 22, "| " + (rs.getString("DESCPROD") != null ? rs.getString("DESCPROD") : ""));
				imp.say(imp.pRow(), 75, "| " + (rs.getString("CODLOTE") != null ? rs.getString("CODLOTE") : ""));
				imp.say(imp.pRow(), 91, "| " + Funcoes.strDecimalToStrCurrency(8,Aplicativo.casasDec,(rs.getString("QTDITCOMPRA") != null ? rs.getString("QTDITCOMPRA") : "")));
				imp.say(imp.pRow(),102, "| " + Funcoes.strDecimalToStrCurrency(10,2,(rs.getString("VLRLIQITCOMPRA") != null ? rs.getString("VLRLIQITCOMPRA") : "")));
				imp.say(imp.pRow(),115, "| " + Funcoes.strDecimalToStrCurrency(5,2,(rs.getString("PERCDESCITCOMPRA") != null ? rs.getString("PERCDESCITCOMPRA") : "")));
				imp.say(imp.pRow(),122, "| " + Funcoes.strDecimalToStrCurrency(10,2,(rs.getString("VLRDESCITCOMPRA") != null ? rs.getString("VLRDESCITCOMPRA") : "")));
				imp.say(imp.pRow(),135, "|");	
				bTotal = bTotal.add(rs.getBigDecimal("VLRLIQITCOMPRA"));
			}

			imp.say(imp.pRow()+1, 0, imp.comprimido());
			imp.say(imp.pRow(), 0, "+" + Funcoes.replicate("=",133) + "+");
			imp.say(imp.pRow()+1, 0, imp.comprimido());
			imp.say(imp.pRow(), 0, "| ");
			imp.say(imp.pRow(), 94, "VALOR TOTAL DE COMPRAS = " + Funcoes.strDecimalToStrCurrency(15,2,bTotal.toString()));
			imp.say(imp.pRow(),135, "|");
			imp.say(imp.pRow()+1, 0, imp.comprimido());
			imp.say(imp.pRow(), 0, "+" + Funcoes.replicate("=",133) + "+");
		 
			imp.eject();		 
			imp.fechaGravacao();

			if (!con.getAutoCommit())
                con.commit();
		} catch ( SQLException err ) {
			Funcoes.mensagemErro(this,"Erro consulta tabela compras!\n"+err.getMessage(),true,con,err);
			err.printStackTrace();
		} catch ( Exception err ) {
			err.printStackTrace();
		} finally {
			ps = null;
			rs = null;
			sSQL = null;
			sWhere = null;
			bTotal = null;
			System.gc();
		}
	 
		if (bVisualizar)
			imp.preview(this);
		else
			imp.print();
	}
}