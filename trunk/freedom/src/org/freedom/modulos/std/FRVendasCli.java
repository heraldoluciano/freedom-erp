/**
 * @version 08/12/2000 <BR>
 * @author Setpoint Informática Ltda./Alex Rodrigues <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)FRVendasCli <BR>
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

package org.freedom.modulos.std;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Vector;

import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FRelatorio;

public class FRVendasCli extends FRelatorio {
	
	private static final long serialVersionUID = 1L;
	
	private JTextFieldPad txtDataini = new JTextFieldPad(JTextFieldPad.TP_DATE,10, 0);
	private JTextFieldPad txtDatafim = new JTextFieldPad(JTextFieldPad.TP_DATE,10, 0);
	private JCheckBoxPad cbListaFilial = new JCheckBoxPad("Listar vendas das filiais ?", "S","N");	
	private JRadioGroup rgOrdem = null;
	private JRadioGroup rgFaturados = null;
	private JRadioGroup rgFinanceiro = null;
	private Vector vLabsOrd = new Vector();
	private Vector vValsOrd = new Vector();
	private Vector vLabsFat = new Vector();
	private Vector vValsFat = new Vector();
	private Vector vLabsFin = new Vector();
	private Vector vValsFin = new Vector();

	public FRVendasCli() {
		setTitulo("Vendas por Cliente");
		setAtribos(80, 80, 290, 300);

		GregorianCalendar cPeriodo = new GregorianCalendar();
		txtDatafim.setVlrDate(cPeriodo.getTime());
		cPeriodo.set(Calendar.DAY_OF_MONTH,cPeriodo.get(Calendar.DAY_OF_MONTH) - 30);
		txtDataini.setVlrDate(cPeriodo.getTime());
		
		
		vLabsOrd.addElement("Código");
		vLabsOrd.addElement("Razão");
		vLabsOrd.addElement("Valor");
		vValsOrd.addElement("C");
		vValsOrd.addElement("R");
		vValsOrd.addElement("V");
		rgOrdem = new JRadioGroup(1, 3, vLabsOrd, vValsOrd);
		rgOrdem.setVlrString("V");
				
		vLabsFat.addElement("Faturado");
		vLabsFat.addElement("Não Faturado");
		vLabsFat.addElement("Ambos");
		vValsFat.addElement("S");
		vValsFat.addElement("N");
		vValsFat.addElement("A");
		rgFaturados = new JRadioGroup(3, 1, vLabsFat, vValsFat);
		rgFaturados.setVlrString("S");
		
		vLabsFin.addElement("Financeiro");
		vLabsFin.addElement("Não Finaceiro");
		vLabsFin.addElement("Ambos");
		vValsFin.addElement("S");
		vValsFin.addElement("N");
		vValsFin.addElement("A");
		rgFinanceiro = new JRadioGroup(3, 1, vLabsFin, vValsFin);
		rgFinanceiro.setVlrString("S");

		adic(new JLabelPad("Periodo:"), 7, 5, 120, 20);
		adic(new JLabelPad("De:"), 7, 27, 30, 20);
		adic(txtDataini, 37, 27, 90, 20);
		adic(new JLabelPad("Até:"), 140, 27, 30, 20);
		adic(txtDatafim, 175, 27, 90, 20);
		adic(cbListaFilial, 7, 55, 200, 20);
		adic(new JLabelPad("Ordem"), 7, 80, 50, 20);
		adic(rgOrdem, 7, 100, 261, 30);
		adic(rgFaturados, 7, 140, 120, 70);
		adic(rgFinanceiro, 148, 140, 120, 70);

	}

	public void imprimir(boolean bVisualizar) {

		if (txtDatafim.getVlrDate().before(txtDataini.getVlrDate())) {
			Funcoes.mensagemInforma(this,"Data final maior que a data inicial!");
			return;
		}

		PreparedStatement ps = null;
		ResultSet rs = null;
		String sSQL = null;
		String sCab = "";
		String sOrdem = "";
	  	String sWhere1 = null;
	  	String sWhere2 = null;		
		ImprimeOS imp = new ImprimeOS("", con);
		int linPag = imp.verifLinPag() - 1;
		int count = 1;
		
		if(rgFaturados.getVlrString().equals("S")){ 
			sWhere1 = " AND TM.FISCALTIPOMOV='S' ";
			sCab += " - SO FATURADO";
		} else if(rgFaturados.getVlrString().equals("N")) {
			sWhere1 = " AND TM.FISCALTIPOMOV='N' ";
			sCab += " - NAO FATURADO";
		} else if(rgFaturados.getVlrString().equals("A"))
			sWhere1 = " AND TM.FISCALTIPOMOV IN ('S','N') ";

		if(rgFinanceiro.getVlrString().equals("S")) {
			sWhere2 = " AND TM.SOMAVDTIPOMOV='S' ";
			sCab += " - SO FINANCEIRO";
		} else if(rgFinanceiro.getVlrString().equals("N")) {
			sWhere2 = " AND TM.SOMAVDTIPOMOV='N' ";
			sCab += " - NAO FINANCEIRO";
		} else if(rgFinanceiro.getVlrString().equals("A"))
			sWhere2 = " AND TM.SOMAVDTIPOMOV IN ('S','N') ";
		
		if(rgOrdem.getVlrString().equals("C"))
			sOrdem = " V.CODCLI, C.RAZCLI, C.FONECLI";
		else if(rgOrdem.getVlrString().equals("R"))
			sOrdem = " C.RAZCLI, V.CODCLI, C.FONECLI";
		else if(rgOrdem.getVlrString().equals("V"))
			sOrdem = " 5";

		try {			

			imp = new ImprimeOS("", con);
			linPag = imp.verifLinPag() - 1;
			imp.montaCab();			
			imp.setTitulo("Relatório de Vendas por Cliente");
			imp.addSubTitulo("VENDAS  -  PERIODO DE :"+ txtDataini.getVlrString() + " ATE: " + txtDatafim.getVlrString());
			imp.addSubTitulo(sCab);
			imp.limpaPags();
			
			sSQL = " SELECT V.CODCLI, C.RAZCLI, C.DDDCLI, C.FONECLI, SUM(V.VLRLIQVENDA)"
				 + " FROM VDVENDA V, VDCLIENTE C, EQTIPOMOV TM"
				 + " WHERE C.CODEMP=? AND C.CODFILIAL=?"
				 + " AND V.CODEMPCL=C.CODEMP AND V.CODFILIALCL=C.CODFILIAL AND V.CODCLI=C.CODCLI"
				 + " AND NOT SUBSTR(V.STATUSVENDA,1,1)='C' AND NOT V.VLRLIQVENDA=0 AND V.TIPOVENDA='V'"
				 + " AND V.DTEMITVENDA BETWEEN ? AND ?"
				 + sWhere1 + sWhere2 
				 + " GROUP BY V.CODCLI, C.RAZCLI, C.DDDCLI, C.FONECLI"
				 + " ORDER BY " + sOrdem + " DESC";
			
			ps = con.prepareStatement(sSQL);
			ps.setInt(1, Aplicativo.iCodEmp);
			ps.setInt(2, ListaCampos.getMasterFilial("VDCLIENTE"));
			ps.setDate(3, Funcoes.dateToSQLDate(txtDataini.getVlrDate()));
			ps.setDate(4, Funcoes.dateToSQLDate(txtDatafim.getVlrDate()));
			rs = ps.executeQuery();
			while (rs.next()) {
				if (imp.pRow() == linPag) {
					imp.say(imp.pRow() + 1, 0, imp.comprimido());
					imp.say(imp.pRow(), 0, "+"+ Funcoes.replicate("-", 133) + "+");
					imp.eject();
					imp.incPags();
				}
				if (imp.pRow() == 0) {		
					imp.impCab(136, true);
					imp.say(imp.pRow(), 0, imp.comprimido());
					imp.say(imp.pRow(), 0, "|"+ Funcoes.replicate("-", 133) + "|");
					imp.say(imp.pRow() + 1, 0, imp.comprimido());
					imp.say(imp.pRow(), 0, "|");
					imp.say(imp.pRow(), 10, "|  Cod.cli");
					imp.say(imp.pRow(), 23, "|  Razao Social");
					imp.say(imp.pRow(), 75, "|  Telefone");
					imp.say(imp.pRow(), 95, "|  Valor Total");
					imp.say(imp.pRow(), 135, "|");
					imp.say(imp.pRow() + 1, 0, imp.comprimido());
					imp.say(imp.pRow(), 0, "|" + Funcoes.replicate("-", 133) + "|");
				}
				imp.say(imp.pRow() + 1, 0, imp.comprimido());
				imp.say(imp.pRow(), 0, "|" + Funcoes.alinhaDir( count++, 7 ) );
				imp.say(imp.pRow(), 10, "| " + Funcoes.alinhaDir(rs.getInt("CODCLI"), 10));
				imp.say(imp.pRow(), 23, "| " + rs.getString("RAZCLI"));
				imp.say(imp.pRow(), 75, "| " + (rs.getString("DDDCli") != null ? "("+rs.getString("DDDCli")+")" : "")+
						(rs.getString("FoneCli") != null ? Funcoes.setMascara(rs.getString("FoneCli").trim(),"####-####") : "").trim());
				imp.say(imp.pRow(), 95, "| " + Funcoes.strDecimalToStrCurrency(18,2, rs.getString(5)));
				imp.say(imp.pRow(), 135, "|");
				
			}
			imp.say(imp.pRow() + 1, 0, imp.comprimido());
			imp.say(imp.pRow(), 0, "+" + Funcoes.replicate("=", 133) + "+");

			imp.eject();

			imp.fechaGravacao();

		} catch (SQLException err) {
			Funcoes.mensagemErro(this, "Erro na consulta ao relatório de vendas!\n" + err.getMessage(),true,con,err);
			err.printStackTrace();
		} finally {
			ps = null;
			rs = null;
			sCab = null;
			sOrdem = null;
		  	sWhere1 = null;
		  	sWhere2 = null;
			sSQL = null;	
			System.gc();
		}

		if (bVisualizar)
			imp.preview(this);
		else
			imp.print();
	}

	public void setConexao(Connection cn) {
		super.setConexao(cn);
	}
}