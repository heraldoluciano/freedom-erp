/**
 * @version 08/12/2000 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FRComprasFor.java <BR>
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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JLabel;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FRelatorio;
public class FRComprasFor extends FRelatorio {
	private JTextFieldPad txtDataini = new JTextFieldPad();
	private JTextFieldPad txtDatafim = new JTextFieldPad();
	private JTextFieldPad txtCodFor = new JTextFieldPad();
	private JTextFieldFK txtDescFor = new JTextFieldFK();
	private ListaCampos lcFor = new ListaCampos(this);
	private Connection con = null;
	private String sCodProd = "CODPROD";
	public FRComprasFor() {
		setTitulo("Compras por Fornecedor");
		setAtribos(50,50,310,180);

		txtDataini.setTipo(JTextFieldPad.TP_DATE,10,0);
		txtDatafim.setTipo(JTextFieldPad.TP_DATE,10,0);

		txtCodFor.setTipo(JTextFieldPad.TP_INTEGER,10,0);
		txtDescFor.setTipo(JTextFieldPad.TP_STRING,40,0);
		lcFor.add(new GuardaCampo( txtCodFor, 7, 100, 80, 20, "CodFor", "Código", true, false, null, JTextFieldPad.TP_INTEGER,false),"txtCodFor");
		lcFor.add(new GuardaCampo( txtDescFor, 90, 100, 207, 20, "RazFor", "Descrição", false, false, null, JTextFieldPad.TP_STRING,false),"txtDescFor");
		txtCodFor.setTabelaExterna(lcFor);
		txtCodFor.setNomeCampo("CodFor");
		txtCodFor.setFK(true);
		lcFor.setReadOnly(true);
		lcFor.montaSql(false, "FORNECED", "CP");

		adic(new JLabel("Periodo:"),7,5,120,20);
		adic(new JLabel("De:"),7,25,30,20);
		adic(txtDataini,40,25,97,20);
		adic(new JLabel("Até:"),140,25,37,20);
		adic(txtDatafim,180,25,100,20);
		adic(new JLabel("Código e descrição do fornecedor"),7,50,280,20);
		adic(txtCodFor,7,70,70,20);
		adic(txtDescFor,80,70,200,20);
		
		Calendar cPeriodo = Calendar.getInstance();
	    txtDatafim.setVlrDate(cPeriodo.getTime());
		cPeriodo.set(Calendar.DAY_OF_MONTH,cPeriodo.get(Calendar.DAY_OF_MONTH)-30);
		txtDataini.setVlrDate(cPeriodo.getTime());
	}
	public void imprimir(boolean bVisualizar) {
		if (txtDatafim.getVlrDate().before(txtDataini.getVlrDate())) {
			Funcoes.mensagemInforma(this,"Data final maior que a data inicial!");
			return;
		}
		ImprimeOS imp = new ImprimeOS("",con);
		int linPag = imp.verifLinPag()-1;
		boolean termFor = false;
		int iCodFor = 0;
		int iCodForAnt = -1;
		imp.montaCab();
		String sWhere = "";
		String sDataini = txtDataini.getVlrString();
		String sDatafim = txtDatafim.getVlrString();
		double dVlr = 0;
		double dQtd = 0;
		double dVlrFor = 0;
		double dQtdFor = 0;

		imp.setTitulo("Relatório de Compras por Fornecedor");
		if (txtCodFor.getText().trim().length() > 0) {
			sWhere += " AND C.CODFOR = "+txtCodFor.getText().trim();
		}
		if (ehRef()) {
			sCodProd="REFPROD";
		}
		String sSQL = "SELECT C.CODFOR,F.RAZFOR,C.DTEMITCOMPRA,C.CODCOMPRA,C.DOCCOMPRA,"+
	                  "IT."+sCodProd+",P.DESCPROD,IT.QTDITCOMPRA,"+
			  "IT.PRECOITCOMPRA,IT.VLRLIQITCOMPRA "+
	                  "FROM CPCOMPRA C,CPITCOMPRA IT,EQPRODUTO P,CPFORNECED F "+
	                  "WHERE P.CODPROD = IT.CODPROD"+
	                  " AND IT.CODCOMPRA = C.CODCOMPRA"+
			  " AND F.CODFOR = C.CODFOR"+
	                  " AND C.DTEMITCOMPRA BETWEEN"+
	                  " ? AND ? "+sWhere+" AND C.FLAG IN "+
	                  Aplicativo.carregaFiltro(con,org.freedom.telas.Aplicativo.iCodEmp)+
			  " ORDER BY C.CODFOR,C.DTEMITCOMPRA";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(sSQL);
			ps.setDate(1,Funcoes.dateToSQLDate(txtDataini.getVlrDate()));
			ps.setDate(2,Funcoes.dateToSQLDate(txtDatafim.getVlrDate()));
			rs = ps.executeQuery();
			imp.limpaPags();
			while ( rs.next() ) {
				iCodFor = rs.getInt("CodFor");
                        	if (imp.pRow() == linPag) {
                                        imp.say(imp.pRow()+1,0,""+imp.comprimido());
                                        imp.say(imp.pRow()+0,0,"+"+Funcoes.replicate("-",134)+"+");
                                        imp.eject();
                                	imp.incPags();
                        	}
				if (imp.pRow()==0) {
					imp.say(imp.pRow()+1,0,""+imp.comprimido());
					imp.say(imp.pRow()+0,0,"+"+Funcoes.replicate("-",134)+"+");
					imp.say(imp.pRow()+1,0,""+imp.comprimido());
					imp.say(imp.pRow()+0,0,"| Emitido em :"+Funcoes.dateToStrDate(new Date()));
					imp.say(imp.pRow()+0,120,"Pagina : "+(imp.getNumPags()));
					imp.say(imp.pRow()+0,136,"|");
					imp.say(imp.pRow()+1,0,""+imp.comprimido());
					imp.say(imp.pRow()+0,0,"|");
					imp.say(imp.pRow()+0,136,"|");
					imp.say(imp.pRow()+1,0,""+imp.comprimido());
					imp.say(imp.pRow()+0,0,"|");
					imp.say(imp.pRow()+0,5,"RELATORIO DE COMPRAS POR FORNECEDOR  -  PERIODO DE: "+sDataini+" Até: "+sDatafim);imp.say(imp.pRow()+0,136,"|");
					imp.say(imp.pRow()+1,0,""+imp.comprimido());
					imp.say(imp.pRow()+0,0,"|");
					imp.say(imp.pRow()+0,136,"|");
					imp.say(imp.pRow()+1,0,""+imp.comprimido());
					imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",134)+"|");
					imp.say(imp.pRow()+1,0,""+imp.comprimido());
					imp.say(imp.pRow()+0,0,"| Data       ");
					imp.say(imp.pRow()+0,14,"| Pedido   ");
					imp.say(imp.pRow()+0,26,"| Doc      ");
					imp.say(imp.pRow()+0,38,"| Cod. Produto   ");
					imp.say(imp.pRow()+0,55,"| Desc. Produto  ");
					imp.say(imp.pRow()+0,89,"| Qtd.    ");
					imp.say(imp.pRow()+0,100,"| Preco     ");
					imp.say(imp.pRow()+0,113,"| Total     ");
					imp.say(imp.pRow()+0,136,"|");
					imp.say(imp.pRow()+1,0,""+imp.comprimido());
					imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",134)+"|");
				}
				if (iCodFor != iCodForAnt) {
					if (termFor) {
						dQtd += dQtdFor;
						dVlr += dVlrFor;
						imp.say(imp.pRow()+1,0,""+imp.comprimido());
						imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",134)+"|");
						imp.say(imp.pRow()+1,0,""+imp.comprimido());
						imp.say(imp.pRow()+0,0,"|");
						imp.say(imp.pRow()+0,1,"Sub-Total Fornecedor:");
						imp.say(imp.pRow()+0,30,"Quant. Comprada -> ");
						imp.say(imp.pRow()+0,50,Funcoes.copy(dQtdFor+"",0,6));
						imp.say(imp.pRow()+0,60,"Valor Comprado -> ");
						imp.say(imp.pRow()+0,78,Funcoes.strDecimalToStrCurrency(15,2,dVlrFor+""));
						imp.say(imp.pRow()+0,136,"|");
					}
					dQtdFor = 0;
					dVlrFor = 0;
					String sTmp = "FORNECEDOR: "+rs.getInt("CodFor")+" - "+rs.getString("RazFor").trim();
					imp.say(imp.pRow()+1,0,""+imp.comprimido());
					imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",134)+"|");
					imp.say(imp.pRow()+1,0,""+imp.comprimido());
					imp.say(imp.pRow()+0,0,"|");
					imp.say(imp.pRow()+0,(134-sTmp.length())/2,sTmp);
					imp.say(imp.pRow()+0,136,"|");
					imp.say(imp.pRow()+1,0,""+imp.comprimido());
					imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",134)+"|");
					termFor =true;
				}
				imp.say(imp.pRow()+1,0,""+imp.comprimido());
				imp.say(imp.pRow()+0,0,"| "+Funcoes.sqlDateToStrDate(rs.getDate("DtEmitCompra")));
				imp.say(imp.pRow()+0,14,"| "+Funcoes.copy(rs.getString("CodCompra"),0,8));
				imp.say(imp.pRow()+0,26,"| "+Funcoes.copy(rs.getString("DocCompra"),0,8));
				imp.say(imp.pRow()+0,38,"| "+Funcoes.copy(rs.getString(sCodProd),0,13));
				imp.say(imp.pRow()+0,55,"| "+Funcoes.copy(rs.getString("DescProd"),0,30));
				imp.say(imp.pRow()+0,89,"| "+Funcoes.strDecimalToStrCurrency(7,1,rs.getString("QtdItCompra")));
				imp.say(imp.pRow()+0,100,"| "+Funcoes.strDecimalToStrCurrency(9,2,rs.getString("PrecoItCompra")));
				imp.say(imp.pRow()+0,113,"| "+Funcoes.strDecimalToStrCurrency(9,2,rs.getString("VlrLiqItCompra")));
				imp.say(imp.pRow()+0,136,"|");
				dQtdFor += rs.getDouble("QtdItCompra");
				dVlrFor += rs.getDouble("VlrLiqItCompra");
				
				iCodForAnt = iCodFor;
				
			}
			dQtd += dQtdFor;
			dVlr += dVlrFor;
			imp.say(imp.pRow()+1,0,""+imp.comprimido());
			imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",134)+"|");
			imp.say(imp.pRow()+1,0,""+imp.comprimido());
			imp.say(imp.pRow()+0,0,"|");
			imp.say(imp.pRow()+0,1,"Sub-Total Fornecedor:");
			imp.say(imp.pRow()+0,30,"Quant. Comprada -> ");
			imp.say(imp.pRow()+0,50,Funcoes.copy(dQtdFor+"",0,6));
			imp.say(imp.pRow()+0,60,"Valor Comprado -> ");
			imp.say(imp.pRow()+0,78,Funcoes.strDecimalToStrCurrency(15,2,dVlrFor+""));
			imp.say(imp.pRow()+0,136,"|");
			imp.say(imp.pRow()+1,0,""+imp.comprimido());
			imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",134)+"|");
			imp.say(imp.pRow()+1,0,""+imp.comprimido());
			imp.say(imp.pRow()+0,0,"|");
			imp.say(imp.pRow()+0,1,"Total Compras:");
			imp.say(imp.pRow()+0,30,"Quant. Comprada -> ");
			imp.say(imp.pRow()+0,50,Funcoes.copy(dQtd+"",0,6));
			imp.say(imp.pRow()+0,60,"Valor Comprado -> ");
			imp.say(imp.pRow()+0,78,Funcoes.strDecimalToStrCurrency(15,2,dVlr+""));
			imp.say(imp.pRow()+0,136,"|");
			imp.say(imp.pRow()+1,0,""+imp.comprimido());
			imp.say(imp.pRow()+0,0,"+"+Funcoes.replicate("-",134)+"+");
		 
			imp.eject();
		 
			imp.fechaGravacao();
		 
//			rs.close();
//			ps.close();
			if (!con.getAutoCommit())
                con.commit();
		}
		catch ( SQLException err ) {
			Funcoes.mensagemErro(this,"Erro consulta tabela compras!"+err.getMessage());
		}
	 
		if (bVisualizar) {
			imp.preview(this);
		}
		else {
			imp.print();
		}
	}
	private boolean ehRef() {
		boolean bRetorno = false;
		String sSQL = "SELECT USAREFPROD FROM SGPREFERE1 WHERE CODEMP=? AND CODFILIAL=?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(sSQL);
			ps.setInt(1,Aplicativo.iCodEmp);
			ps.setInt(2,ListaCampos.getMasterFilial("SGPREFERE1"));
			rs = ps.executeQuery();
			if (rs.next()) {
				if (rs.getString("UsaRefProd").compareTo("S") == 0) {
					bRetorno = true;
				}
			}
//			rs.close();
//			ps.close();
			if (!con.getAutoCommit())
                con.commit();
		}
		catch (SQLException err) {
			Funcoes.mensagemErro(this,"Erro ao carregar a tabela PREFERE1!\n"+err.getMessage());
			bRetorno = false;
		}
		return bRetorno;
	}
	public void setConexao(Connection cn) {
		con = cn;
		lcFor.setConexao(cn);
	}
}
