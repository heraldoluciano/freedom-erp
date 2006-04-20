/**
 * @version 08/12/2000 <BR>
 * @author Setpoint Informática Ltda./Marco Antonio Sanchez <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)FRUltimaVenda <BR>
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

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Vector;

import javax.swing.BorderFactory;
import org.freedom.componentes.JLabelPad;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FRelatorio;

public class FRUltimaVenda extends FRelatorio {
	private static final long serialVersionUID = 1L;
	private JTextFieldPad txtDataini = new JTextFieldPad(JTextFieldPad.TP_DATE,10, 0);
	private JTextFieldPad txtDatafim = new JTextFieldPad(JTextFieldPad.TP_DATE,10, 0);
	private JTextFieldPad txtCodCli = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	private JTextFieldFK txtNomeCli = new JTextFieldFK(JTextFieldPad.TP_STRING,50, 0);
	private JTextFieldPad txtCodVend = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	private JTextFieldFK txtDescVend = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);
	private JCheckBoxPad cbListaFilial = new JCheckBoxPad("Listar vendas das filiais ?", "S","N");	
	private JCheckBoxPad cbObsVenda = new JCheckBoxPad("Imprimir Observações da venda?", "S", "N");
	private JCheckBoxPad cbVendaCanc = new JCheckBoxPad("Mostrar Canceladas", "S", "N");
	private JRadioGroup rgFaturados = null;
	private JRadioGroup rgFinanceiro = null;
	private Vector vLabsFat = new Vector();
	private Vector vValsFat = new Vector();
	private Vector vLabsFin = new Vector();
	private Vector vValsFin = new Vector();
	private Vector vObs = null;
	private ListaCampos lcVend = new ListaCampos(this);
	private ListaCampos lcCli = new ListaCampos(this, "CL");

	public FRUltimaVenda() {
		setTitulo("Ultima Venda por Cliente");
		setAtribos(80, 80, 290, 390);

		GregorianCalendar cPeriodo = new GregorianCalendar();
		txtDatafim.setVlrDate(cPeriodo.getTime());
		cPeriodo.set(Calendar.DAY_OF_MONTH,cPeriodo.get(Calendar.DAY_OF_MONTH) - 30);
		txtDataini.setVlrDate(cPeriodo.getTime());
		
		lcVend.add(new GuardaCampo(txtCodVend, "CodVend", "Cód.comiss.",ListaCampos.DB_PK, false));
		lcVend.add(new GuardaCampo(txtDescVend, "NomeVend","Nome do comissionado", ListaCampos.DB_SI, false));
		lcVend.montaSql(false, "VENDEDOR", "VD");
		lcVend.setQueryCommit(false);
		lcVend.setReadOnly(true);
		txtCodVend.setNomeCampo("CodVend");
		txtCodVend.setFK(true);
		txtCodVend.setTabelaExterna(lcVend);

		lcCli.add(new GuardaCampo(txtCodCli, "CodCli", "Cód.cli.",ListaCampos.DB_PK, false));
		lcCli.add(new GuardaCampo(txtNomeCli, "NomeCli","Razão social do cliente", ListaCampos.DB_SI, false));
		txtCodCli.setTabelaExterna(lcCli);
		txtCodCli.setNomeCampo("CodCli");
		txtCodCli.setFK(true);
		lcCli.setReadOnly(true);
		lcCli.montaSql(false, "CLIENTE", "VD");
		JLabelPad lbLinha = new JLabelPad();
		lbLinha.setBorder(BorderFactory.createEtchedBorder());
		
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
		adic(lbLinha, 7, 62, 260, 2);
		adic(new JLabelPad("Cód.cli."), 7, 70, 250, 20);
		adic(txtCodCli, 07, 90, 70, 20);
		adic(new JLabelPad("Razão social do cliente:"), 80, 70, 250, 20);
		adic(txtNomeCli, 80, 90, 186, 20);
		adic(new JLabelPad("Cód.comiss."), 7, 113, 210, 20);
		adic(txtCodVend, 7, 136, 70, 20);
		adic(new JLabelPad("Nome do comissionado"), 80, 113, 210, 20);
		adic(txtDescVend, 80, 136, 186, 20);
		adic(cbListaFilial, 7, 165, 200, 20);
		adic(cbObsVenda, 7, 185, 250, 20);
		adic(rgFaturados, 7, 215, 120, 70);
		adic(rgFinanceiro, 148, 215, 120, 70);
		adic(cbVendaCanc, 7, 295, 200, 20);

	}

	public void setConexao(Connection cn) {
		super.setConexao(cn);
		lcCli.setConexao(con);
		lcVend.setConexao(con);
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
	  	String sWhere1 = null;
	  	String sWhere2 = null;
	  	String sWhere3 = "";
		String sCab = "";
		String sCab1 = "";
		String sTmp = null;
		BigDecimal bTotalVd = null;
		ImprimeOS imp = null;
		int linPag = 0;


		if (txtCodCli.getText().trim().length() > 0) {
			if (cbListaFilial.getVlrString().equals("S"))
				sWhere += " AND (C.CODPESQ = " + txtCodCli.getText().trim()
						+ " OR C.CODCLI=" + txtCodCli.getText().trim() + ")";
			else
				sWhere += " AND C.CODCLI = " + txtCodCli.getText().trim();
			sCab = "CLIENTE: " + txtNomeCli.getText().trim();
		}

		if (txtCodVend.getText().trim().length() > 0) {
			sWhere += " AND VD.CODVEND = " + txtCodVend.getText().trim();
			sTmp = "COMISSs.: " + txtCodVend.getVlrString() + " - "+ txtDescVend.getText().trim();
			sWhere += " AND VD.CODEMPVD=" + Aplicativo.iCodEmp+ " AND VD.CODFILIALVD=" + lcVend.getCodFilial();			
			sCab = sTmp ;
		}
		
		if(rgFaturados.getVlrString().equals("S")) {
			sWhere1 = " AND TM.FISCALTIPOMOV='S' ";
			sCab1 += " - SO FATURADO";
		} else if(rgFaturados.getVlrString().equals("N")) {
			sWhere1 = " AND TM.FISCALTIPOMOV='N' ";
			sCab1 += " - NAO FATURADO";
		} else if(rgFaturados.getVlrString().equals("A"))
			sWhere1 = " AND TM.FISCALTIPOMOV IN ('S','N') ";

		if(rgFinanceiro.getVlrString().equals("S")) {
			sWhere2 = " AND TM.SOMAVDTIPOMOV='S' ";
			sCab1 += " - SO FINANCEIRO";
		} else if(rgFinanceiro.getVlrString().equals("N")) {
			sWhere2 = " AND TM.SOMAVDTIPOMOV='N' ";
			sCab1 += " - NAO FINANCEIRO";
		} else if(rgFinanceiro.getVlrString().equals("A"))
			sWhere2 = " AND TM.SOMAVDTIPOMOV IN ('S','N') ";
		
		if(cbVendaCanc.getVlrString().equals("N"))
			sWhere3 = " AND NOT SUBSTR(VD.STATUSVENDA,1,1)='C' ";
						
		try {
			imp = new ImprimeOS("", con);
			linPag = imp.verifLinPag() - 1;
			imp.montaCab();			
			imp.setTitulo("Relatório de Ultimas Vendas");
			imp.addSubTitulo("ULTIMAS VENDAS  -   PERIODO DE :"+ txtDataini.getVlrString() + " ATE: " + txtDatafim.getVlrString());
			if (sCab.length() > 0) 
				imp.addSubTitulo(sCab);
			imp.addSubTitulo(sCab1);
			imp.limpaPags();
			
			bTotalVd = new BigDecimal("0");
			
			sSQL = "SELECT C.CODCLI,C.RAZCLI,C.FONECLI,C.DDDCLI,VD.CODVENDA, "
				 + "VD.DOCVENDA, VD.VLRLIQVENDA, MAX(VD.DTEMITVENDA), VD.OBSVENDA "
				 + "FROM VDCLIENTE C, VDVENDA VD, EQTIPOMOV TM " 
				 + "WHERE C.CODFILIAL=? AND C.CODEMP=? "
				 + "AND C.CODCLI=VD.CODCLI AND C.CODEMP=VD.CODEMPCL AND C.CODFILIAL=VD.CODFILIALCL "
				 + "AND VD.DTEMITVENDA BETWEEN ? AND ? "
				 + "AND TM.CODEMP=VD.CODEMPTM AND TM.CODFILIAL=VD.CODFILIALTM AND TM.CODTIPOMOV=VD.CODTIPOMOV "
				 +  sWhere + sWhere1 + sWhere2 + sWhere3	
				 + "GROUP BY C.CODCLI, C.RAZCLI,C.FONECLI,C.DDDCLI,VD.CODVENDA,VD.DOCVENDA,VD.VLRLIQVENDA,VD.OBSVENDA ";

			
			ps = con.prepareStatement(sSQL);
			ps.setInt(1, ListaCampos.getMasterFilial("VDCLIENTE"));
			ps.setInt(2, Aplicativo.iCodEmp);
			ps.setDate(3, Funcoes.dateToSQLDate(txtDataini.getVlrDate()));
			ps.setDate(4, Funcoes.dateToSQLDate(txtDatafim.getVlrDate()));
			rs = ps.executeQuery();
						
			while (rs.next()) {
				if (imp.pRow() == linPag) {
					imp.say(imp.pRow() + 1, 0, imp.comprimido());
					imp.say(imp.pRow(), 0, "+" + Funcoes.replicate("-", 133) + "+");
					imp.eject();
					imp.incPags();
				}
				if (imp.pRow() == 0) {		
					imp.impCab(136, true);

					imp.say(imp.pRow(), 0, imp.comprimido());
					imp.say(imp.pRow(), 0, "|" + Funcoes.replicate("-", 133) + "|");
					imp.say(imp.pRow() + 1, 0, imp.comprimido());
					imp.say(imp.pRow(), 0, "|  Cod.CLi.");
					imp.say(imp.pRow(), 14, " | Cliente.");
					imp.say(imp.pRow(), 48, "| Nota Fiscal");
					imp.say(imp.pRow(), 60, " |   Pedido");
					imp.say(imp.pRow(), 74, "  | Data Emis.");
					imp.say(imp.pRow(), 90, "|      Valor");
					imp.say(imp.pRow(), 110, "| Telefone");
					imp.say(imp.pRow(), 135, "|");
					imp.say(imp.pRow() + 1, 0, imp.comprimido());
					imp.say(imp.pRow(), 0, "|" + Funcoes.replicate("-", 133) + "|");
				}
				imp.say(imp.pRow() + 1, 0, imp.comprimido());
				imp.say(imp.pRow(), 0, "|" + Funcoes.alinhaDir(rs.getInt("CODCLI"), 10));
				imp.say(imp.pRow(), 15, "|" + Funcoes.copy(rs.getString("RAZCLI"), 0, 30));
				imp.say(imp.pRow(), 47, " |");
				imp.say(imp.pRow(), 48, Funcoes.alinhaDir(rs.getInt("DOCVENDA"), 8));
				imp.say(imp.pRow(), 62, "|");
				imp.say(imp.pRow(), 65, Funcoes.alinhaDir(rs.getInt("CODVENDA"), 8));
				imp.say(imp.pRow(), 76, "|");
				imp.say(imp.pRow(), 79, Funcoes.dateToStrDate(rs.getDate(8)));
				imp.say(imp.pRow(), 90, "|");
				imp.say(imp.pRow(), 91, Funcoes.strDecimalToStrCurrency(18,2, rs.getString("VlrLiqVenda")));
				imp.say(imp.pRow(), 110, "|");
				imp.say(imp.pRow(), 112, (rs.getString("DDDCli") != null ? "("+rs.getString("DDDCli")+")" : "")+
											 (rs.getString("FoneCli") != null ? Funcoes.setMascara(rs.getString("FoneCli").trim(),"####-####") : "").trim());

				imp.say(imp.pRow(), 135, "|");
				
				if(cbObsVenda.getVlrString().equals("S")){
					if(rs.getString("ObsVenda")!=null && rs.getString("ObsVenda").length()>0){
						vObs = Funcoes.quebraLinha(Funcoes.stringToVector(rs.getString("ObsVenda")),115);
			          	for (int i=0; i<vObs.size(); i++) {
			                imp.say(imp.pRow() + 1, 0, imp.comprimido());
			                imp.say(imp.pRow(), 0, "|");
			                imp.say(imp.pRow(), 15, "|  " + vObs.elementAt(i).toString());
			                imp.say(imp.pRow(), 135, "|");
			                if (imp.pRow()>=linPag) {
			                    imp.incPags();
			                    imp.eject();
			                }
			          	}
					}
				}
				
				imp.say(imp.pRow() + 1, 0, imp.comprimido());
				imp.say(imp.pRow(), 0, "|" + Funcoes.replicate("-", 133) + "|");
				
				bTotalVd = bTotalVd.add(new BigDecimal(rs.getString("VlrLiqVenda")));
			}
			imp.say(imp.pRow() + 1, 0, imp.comprimido());
			imp.say(imp.pRow(), 0, "+" + Funcoes.replicate("=", 133) + "+");
			imp.say(imp.pRow() + 1, 0, imp.comprimido());
			imp.say(imp.pRow(), 0, "|");
			imp.say(imp.pRow(), 68, "Total de Vendas no Periodo: "+ Funcoes.strDecimalToStrCurrency(13, 2, "" + bTotalVd));
			imp.say(imp.pRow(), 135, "|");
			imp.say(imp.pRow() + 1, 0, imp.comprimido());
			imp.say(imp.pRow(), 0, "+" + Funcoes.replicate("=", 133) + "+");

			imp.eject();
			imp.fechaGravacao();
			
			if(!con.getAutoCommit())
				con.commit();
		} catch (SQLException err) {
			Funcoes.mensagemErro(this, "Erro na consulta ao relatório de vendas!\n" + err.getMessage(),true,con,err);
		} finally {
			ps = null;
			rs = null;
			sSQL = null;
			sWhere = null;
		  	sWhere1 = null;
		  	sWhere2 = null;
		  	sWhere3 = null;
			sCab = null;
			sCab1 = null;
			sTmp = null;
			bTotalVd = null;
			System.gc();
		}

		if (bVisualizar) 
			imp.preview(this);
		else
			imp.print();
	}
}