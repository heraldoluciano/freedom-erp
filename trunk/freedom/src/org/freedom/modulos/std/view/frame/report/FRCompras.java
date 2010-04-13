/**
 * @version 08/12/2000 <BR>
 * @author Setpoint Informática Ltda./Alex Rodrigues <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FRCompras.java <BR>
 * 
 * Este arquivo é parte do sistema Freedom-ERP, o Freedom-ERP é um software livre; você pode redistribui-lo e/ou <BR>
 * modifica-lo dentro dos termos da Licença Pública Geral GNU como publicada pela Fundação do Software Livre (FSF); <BR>
 * na versão 2 da Licença, ou (na sua opnião) qualquer versão. <BR>
 * Este programa é distribuido na esperança que possa ser  util, mas SEM NENHUMA GARANTIA; <BR>
 * sem uma garantia implicita de ADEQUAÇÂO a qualquer MERCADO ou APLICAÇÃO EM PARTICULAR. <BR>
 * Veja a Licença Pública Geral GNU para maiores detalhes. <BR>
 * Você deve ter recebido uma cópia da Licença Pública Geral GNU junto com este programa, se não, <BR>
 * escreva para a Fundação do Software Livre(FSF) Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA <BR> <BR>
 *
 * Comentários sobre a classe...
 * 
 */

package org.freedom.modulos.std.view.frame.report;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.SwingConstants;

import net.sf.jasperreports.engine.JasperPrintManager;

import org.freedom.infra.functions.StringFunctions;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.component.ImprimeOS;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JRadioGroup;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FPrinterJob;
import org.freedom.library.swing.frame.FRelatorio;

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

	private Vector<String> vLabs1 = new Vector<String>();
	
	private Vector<String> vVals1 = new Vector<String>();
	
	private JRadioGroup<?, ?> rgTipo = null;
	
	public FRCompras() {
		
		setTitulo("Compras por Fornecedor");
		setAtribos(50,50,345,260);

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
		
		 
		vLabs1.addElement("Texto");
 		vLabs1.addElement("Gráfico"); 
 		vVals1.addElement("T");
 		vVals1.addElement("G");
		    
 		rgTipo = new JRadioGroup<String, String>(1,2,vLabs1,vVals1);
 		rgTipo.setVlrString("T");

		JLabelPad lbLinha = new JLabelPad();
		lbLinha.setBorder(BorderFactory.createEtchedBorder());
		JLabelPad lbPeriodo = new JLabelPad("Período:" , SwingConstants.CENTER );
		lbPeriodo.setOpaque(true);
		
		adic(lbPeriodo,7, 1, 80, 20 );
		adic(lbLinha,5,10,300,45);
		
		adic(new JLabelPad("De:"),10,25,30,20);
		adic(txtDataini,40,25,97,20);
		adic(new JLabelPad("Até:"),152,25,37,20);
		adic(txtDatafim,190,25,100,20);
		adic(new JLabelPad("Cód.for."),7,60,80,20);
		adic(txtCodFor,7,80,80,20);
		adic(new JLabelPad("Descrição do fornecedor"),90,60,200,20);
		adic(txtDescFor,90,80,215,20);		
		adic(new JLabelPad("Cód.pl.pag."),7,100,80,20);
		adic(txtCodPlanoPag,7,120,80,20);
		adic(new JLabelPad("Descrição do plano de pagamento"),90,100,200,20);
		adic(txtDescPlanoPag,90,120,215,20);
		adic(rgTipo,7,150,300,30);
		
		Calendar cPeriodo = Calendar.getInstance();
	    txtDatafim.setVlrDate(cPeriodo.getTime());
		cPeriodo.set(Calendar.DAY_OF_MONTH,cPeriodo.get(Calendar.DAY_OF_MONTH)-30);
		txtDataini.setVlrDate(cPeriodo.getTime());
	}
	
	public void setConexao(DbConnection cn) {
		super.setConexao(cn);
		lcFor.setConexao(cn);
		lcPlanoPag.setConexao(cn);
	}
	
	public void imprimir( boolean bVisualizar ) {
		
		if (txtDatafim.getVlrDate().before(txtDataini.getVlrDate())) {
			Funcoes.mensagemInforma(this,"Data final maior que a data inicial!");
			return;
		}

		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuilder sSQL = new StringBuilder();
		StringBuilder sWhere = new StringBuilder();
		StringBuilder sCab = new StringBuilder();
		
		int iparam = 1;
			
		if (txtCodFor.getVlrInteger().intValue() > 0) {
			sWhere.append( " AND C.CODFOR = " + txtCodFor.getVlrInteger().intValue()); 
			sCab.append( "FORNECEDOR : " + txtDescFor.getVlrString());		
		}
		if (txtCodPlanoPag.getVlrInteger().intValue() > 0) {
			sWhere.append( " AND C.CODPLANOPAG = " + txtCodPlanoPag.getVlrInteger().intValue());
			sCab.append("PLANO DE PAGAMENTO: " + txtDescPlanoPag.getVlrString());		
			
		}
		
		sSQL.append( "SELECT C.CODCOMPRA, C.DOCCOMPRA, C.DTEMITCOMPRA, C.DTENTCOMPRA, C.VLRLIQCOMPRA, " );
		sSQL.append( "F.NOMEFOR, PG.DESCPLANOPAG, " );
		sSQL.append( "IT.CODITCOMPRA, IT.CODPROD, PD.DESCPROD, IT.CODLOTE, IT.QTDITCOMPRA, " );
		sSQL.append( "IT.VLRLIQITCOMPRA, IT.PERCDESCITCOMPRA, IT.VLRDESCITCOMPRA, IT.VLRLIQITCOMPRA " );
		sSQL.append( "FROM CPCOMPRA C, CPITCOMPRA IT, CPFORNECED F, FNPLANOPAG PG, EQPRODUTO PD " );
		sSQL.append( "WHERE C.CODEMP=? AND C.CODFILIAL=? " );
		sSQL.append( "AND C.CODEMPFR=F.CODEMP AND C.CODFILIALFR=F.CODFILIAL AND C.CODFOR=F.CODFOR " );
		sSQL.append( "AND C.CODEMPPG=PG.CODEMP AND C.CODFILIALPG=PG.CODFILIAL AND C.CODPLANOPAG=PG.CODPLANOPAG " );
		sSQL.append( "AND C.CODEMP=IT.CODEMP AND C.CODFILIAL=IT.CODFILIAL AND C.CODCOMPRA=IT.CODCOMPRA " );
		sSQL.append( "AND IT.CODEMPPD=PD.CODEMP AND IT.CODFILIALPD=PD.CODFILIAL AND IT.CODPROD=PD.CODPROD " );
		sSQL.append( "AND C.DTENTCOMPRA BETWEEN ? AND ? ");
		sSQL.append( sWhere );
		sSQL.append( " ORDER BY C.CODCOMPRA, IT.CODITCOMPRA" ) ;
		
		try {
				
			ps = con.prepareStatement( sSQL.toString() );
			ps.setInt(iparam++, Aplicativo.iCodEmp);
			ps.setInt(iparam++, ListaCampos.getMasterFilial("CPCOMPRA"));
			ps.setDate(iparam++, Funcoes.strDateToSqlDate(txtDataini.getVlrString()));
			ps.setDate(iparam++, Funcoes.strDateToSqlDate(txtDatafim.getVlrString()));
			rs = ps.executeQuery();
			
		} catch ( SQLException err ) {
				
			err.printStackTrace();
			Funcoes.mensagemErro( this," Erro na consulta da tabela de compras" );
		}
		
		if("T".equals( rgTipo.getVlrString())){
			
			imprimeTexto( rs, bVisualizar, sCab.toString() );
		}
		else{
			imprimiGrafico( rs, bVisualizar, sCab.toString() ); 
		}
	}
		
	public void imprimeTexto( final ResultSet rs, final boolean bVisualizar, final String sCab ){
		
		ImprimeOS imp = null;
		int linPag = 0;
		BigDecimal bTotal = null;
		int iCodCompra = 0;		
		int iCab = 7;
		
		try {
			
			bTotal = new BigDecimal("0");
			imp = new ImprimeOS("",con);
			linPag = imp.verifLinPag()-1;
			imp.montaCab();
			imp.setTitulo("Relatório de Compras");
			imp.addSubTitulo("RELATORIO DE COMPRAS");
			imp.addSubTitulo("PERIODO DE: "+txtDataini.getVlrString()+" Até: "+txtDatafim.getVlrString());
			imp.addSubTitulo( sCab.toString() );
			imp.limpaPags();	
			
			while( rs.next() ) {
            	if(imp.pRow() >= linPag) {
                    imp.say(imp.pRow()+1, 0, imp.comprimido());
                    imp.say(imp.pRow(),0, "+" + StringFunctions.replicate("-",133) + "+");
                    imp.eject();
                    imp.incPags();
            	}
				if(imp.pRow() == 0) {
					imp.impCab(136, true);
        			imp.say(imp.pRow(), 0, imp.comprimido());
					imp.say(imp.pRow(), 0, "|" + StringFunctions.replicate("=",133) + "|");
				}
            	if(iCodCompra != rs.getInt("CODCOMPRA")) {
            		iCodCompra = rs.getInt("CODCOMPRA");
            		if(imp.pRow() > iCab) {
            			imp.say(imp.pRow()+1, 0, imp.comprimido());
						imp.say(imp.pRow(), 0, "|" + StringFunctions.replicate("=",133) + "|");
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
					imp.say(imp.pRow(), 0, "|" + StringFunctions.replicate("-",133) + "|");
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
					imp.say(imp.pRow(), 0, "|" + StringFunctions.replicate("-",133) + "|");
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
					imp.say(imp.pRow(), 0, "|" + StringFunctions.replicate("-",133) + "|");
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
			imp.say(imp.pRow(), 0, "+" + StringFunctions.replicate("=",133) + "+");
			imp.say(imp.pRow()+1, 0, imp.comprimido());
			imp.say(imp.pRow(), 0, "| ");
			imp.say(imp.pRow(), 94, "VALOR TOTAL DE COMPRAS = " + Funcoes.strDecimalToStrCurrency(15,2,bTotal.toString()));
			imp.say(imp.pRow(),135, "|");
			imp.say(imp.pRow()+1, 0, imp.comprimido());
			imp.say(imp.pRow(), 0, "+" + StringFunctions.replicate("=",133) + "+");
		 
			imp.eject();		 
			imp.fechaGravacao();

            con.commit();
			
		}catch ( Exception err ) {
			
			err.printStackTrace();
		}
		if ( bVisualizar ) {
			imp.preview( this );
		}
		else {
			imp.print();
		}
	}
	private void imprimiGrafico( final ResultSet rs, final boolean bVisualizar,  final String sCab ) {

		FPrinterJob dlGr = null;
		HashMap<String, Object> hParam = new HashMap<String, Object>();

		hParam.put( "CODEMP", Aplicativo.iCodEmp );
		hParam.put( "CODFILIAL", ListaCampos.getMasterFilial( "CPCOMPRA" ) );
		hParam.put( "RAZAOEMP", Aplicativo.empresa.toString() );
		hParam.put( "FILTROS", sCab );

		dlGr = new FPrinterJob( "relatorios/FRCompras.jasper", "Relatório de Compras Geral", sCab, rs, hParam, this );

		if ( bVisualizar ) {
			dlGr.setVisible( true );
		}
		else {
			try {
				JasperPrintManager.printReport( dlGr.getRelatorio(), true );
			} catch ( Exception err ) {
				Funcoes.mensagemErro( this, "Erro na impressão de relatório Compras Geral!" + err.getMessage(), true, con, err );
			}
		}
	}
}