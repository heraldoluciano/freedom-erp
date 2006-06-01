/**
 * @version 02/11/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FRVendasItem.java <BR>
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
import java.util.Date;
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

public class FRVendasItem extends FRelatorio {
	private static final long serialVersionUID = 1L;
	private JTextFieldPad txtDataini = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0); 
	private JTextFieldPad txtDatafim = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0); 
	private JTextFieldPad txtCodVend = new JTextFieldPad(JTextFieldPad.TP_INTEGER,10,0);
	private JTextFieldFK txtDescVend = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
	private JTextFieldPad txtCodCli=new JTextFieldPad(JTextFieldPad.TP_STRING,14,0);
	private JTextFieldFK txtRazCli = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
	private JTextFieldPad txtCodGrup = new JTextFieldPad(JTextFieldPad.TP_STRING,14,0);
	private JTextFieldFK txtDescGrup = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
	private JTextFieldPad txtCodMarca = new JTextFieldPad(JTextFieldPad.TP_STRING,6,0);
	private JTextFieldFK txtDescMarca = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
	private JTextFieldPad txtSiglaMarca = new JTextFieldPad(JTextFieldPad.TP_STRING,20,0);
	private JCheckBoxPad cbListaFilial = new JCheckBoxPad("Listar vendas das filiais ?", "S", "N");	
	private JCheckBoxPad cbVendaCanc = new JCheckBoxPad("Mostrar Canceladas", "S", "N");
	private JRadioGroup rgOrdem = null;
	private JRadioGroup rgFaturados = null;
	private JRadioGroup rgFinanceiro = null;
	private Vector vLabsFat = new Vector();
	private Vector vValsFat = new Vector();
	private Vector vLabsFin = new Vector();
	private Vector vValsFin = new Vector();
	private Vector vLabs = new Vector(2);
	private Vector vVals = new Vector(2);
	private ListaCampos lcVend = new ListaCampos(this);
	private ListaCampos lcGrup = new ListaCampos(this);
	private ListaCampos lcCliente = new ListaCampos(this);
	private ListaCampos lcMarca = new ListaCampos(this);
	private boolean comref = false;
	
	public FRVendasItem() {
		setTitulo("Vendas por Item");
		setAtribos(80,80,305,520);
		
		txtDescVend.setAtivo(false);
		txtDescGrup.setAtivo(false);
		txtDescMarca.setAtivo(false);
		txtRazCli.setAtivo(false);
		
		vLabs.addElement("Código");
		vLabs.addElement("Descrição");
		vVals.addElement("C");
		vVals.addElement("D");
		rgOrdem = new JRadioGroup(1,2,vLabs,vVals);
		rgOrdem.setVlrString("D");
		
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
		
		lcGrup.add(new GuardaCampo( txtCodGrup, "CodGrup", "Cód.grupo", ListaCampos.DB_PK, false));
		lcGrup.add(new GuardaCampo( txtDescGrup, "DescGrup", "Descrição do grupo", ListaCampos.DB_SI, false));
		txtCodGrup.setTabelaExterna(lcGrup);
		txtCodGrup.setNomeCampo("CodGrup");
		txtCodGrup.setFK(true);
		lcGrup.setReadOnly(true);
		lcGrup.montaSql(false, "GRUPO", "EQ");
		
		lcMarca.add(new GuardaCampo( txtCodMarca, "CodMarca", "Cód.marca", ListaCampos.DB_PK, false));
		lcMarca.add(new GuardaCampo( txtDescMarca, "DescMarca", "Descrição da marca", ListaCampos.DB_SI, false));
		lcMarca.add(new GuardaCampo( txtSiglaMarca, "SiglaMarca", "Sigla", ListaCampos.DB_SI, false));
		txtCodMarca.setTabelaExterna(lcMarca);
		txtCodMarca.setNomeCampo("CodMarca");
		txtCodMarca.setFK(true);
		lcMarca.setReadOnly(true);
		lcMarca.montaSql(false, "MARCA", "EQ");
		 
		lcVend.add(new GuardaCampo( txtCodVend, "CodVend", "Cód.comiss.", ListaCampos.DB_PK, false));
		lcVend.add(new GuardaCampo( txtDescVend, "NomeVend", "Nome do comissionado", ListaCampos.DB_SI, false));
		txtCodVend.setTabelaExterna(lcVend);
		txtCodVend.setNomeCampo("CodVend");
		txtCodVend.setFK(true);
		lcVend.setReadOnly(true);
		lcVend.montaSql(false, "VENDEDOR", "VD");
		
		JLabelPad lbLinha = new JLabelPad();
		lbLinha.setBorder(BorderFactory.createEtchedBorder());
		JLabelPad lbLinha2 = new JLabelPad();
		lbLinha2.setBorder(BorderFactory.createEtchedBorder());
		JLabelPad lbLinha3 = new JLabelPad();
		lbLinha3.setBorder(BorderFactory.createEtchedBorder());
		txtDataini.setVlrDate(new Date());
		txtDatafim.setVlrDate(new Date());
		
		lcCliente.add(new GuardaCampo( txtCodCli, "CodCli", "Cód.cli.", ListaCampos.DB_PK, false));
		lcCliente.add(new GuardaCampo( txtRazCli, "RazCli", "Razão social do cliente", ListaCampos.DB_SI, false));
		txtCodCli.setTabelaExterna(lcCliente);
		txtCodCli.setNomeCampo("CodCli");
		txtCodCli.setFK(true);
		lcCliente.setReadOnly(true);
		lcCliente.montaSql(false, "CLIENTE", "VD");
				
				 
		adic(new JLabelPad("Periodo:"),7,5,100,20);
		adic(lbLinha,60,15,218,2);
		adic(new JLabelPad("De:"),7,30,30,20);
		adic(txtDataini,32,30,97,20);
		adic(new JLabelPad("Até:"),140,30,30,20);
		adic(txtDatafim,170,30,100,20);
		adic(lbLinha2,7,60,272,2);
		adic(new JLabelPad("Cód.comiss."),7,68,200,20);
		adic(txtCodVend,7,88,70,20);
		adic(new JLabelPad("Nome do comissionado"),80,68,200,20);
		adic(txtDescVend,80,88,200,20);
		adic(new JLabelPad("Cód.grupo"),7,108,200,20);
		adic(txtCodGrup,7,128,70,20);
		adic(new JLabelPad("Descrição do grupo"),80,108,200,20);
		adic(txtDescGrup,80,128,200,20);
		adic(new JLabelPad("Cód.marca"),7,148,200,20);
		adic(txtCodMarca,7,168,70,20);
		adic(new JLabelPad("Descrição da marca"),80,148,200,20);
		adic(txtDescMarca,80,168,200,20);		
		adic(new JLabelPad("Cód.cli."),7,188,200,20);
		adic(txtCodCli,7,208,70,20);
		adic(new JLabelPad("Razão social do cliente"),80,188,200,20);
		adic(txtRazCli,80,208,200,20);				
		adic(rgFaturados, 7, 240, 125, 70);
		adic(rgFinanceiro, 157, 240, 125, 70);	
		adic(cbListaFilial, 5, 325, 250, 20 );		
		adic(lbLinha3,7,350,272,2);
		adic(new JLabelPad("Ordenado por:"),7,360,180,20);
		adic(rgOrdem,7,385,273,30);
		adic(cbVendaCanc, 7, 425, 200, 20);
				
	}
	
	public void imprimir(boolean bVisualizar) {
		if (txtDatafim.getVlrDate().before(txtDataini.getVlrDate())) {
			Funcoes.mensagemInforma(this,"Data final maior que a data inicial!");
			return;
		}

		PreparedStatement ps = null;
		ResultSet rs = null;
		String sWhere = "";
		String sWhere1 = null;
		String sWhere2 = null;
		String sWhere3 = "";
		String sOrdem = rgOrdem.getVlrString();
		String sOrdenado = "";
		String sCodRel = null;
		String sSQL= null;
		String sLinhaFina = Funcoes.replicate("-",133);
		String sLinhaLarga = Funcoes.replicate("=",133);
		ImprimeOS imp = new ImprimeOS("",con);
		int linPag = imp.verifLinPag()-1;
		double dQtd = 0;
		double dVlr = 0;
		boolean listaFilial = false;
		
		if (comref)
			sCodRel = "REFPROD";
		else
			sCodRel = "CODPROD";
		
		if (sOrdem.equals("C")) {
			sOrdem = "P." + sCodRel;
			sOrdenado = "ORDENADO POR " + (sCodRel.equals("CODPROD") ? "CODIGO" : "REFERENCIA");
		} else {
			sOrdem = "P.DESCPROD";
			sOrdenado = "ORDENADO POR DESCRICAO";
		}
		
		imp.addSubTitulo( sOrdenado );
		
		if (txtCodVend.getText().trim().length() > 0) {
			sWhere += " AND V.CODVEND=" + txtCodVend.getText().trim();
			imp.addSubTitulo("REPR.: " + txtDescVend.getText().trim());
		}
		if (txtCodGrup.getText().trim().length() > 0) {
			sWhere += " AND P.CODGRUP LIKE '" + txtCodGrup.getText().trim() + "%'";
			imp.addSubTitulo("GRUPO: " + txtDescGrup.getText().trim());
		}
		if (txtCodMarca.getText().trim().length() > 0) {
			sWhere += " AND P.CODMARCA='" + txtCodMarca.getText().trim() + "'";
			imp.addSubTitulo("MARCA: " + txtDescMarca.getText().trim());
		}
		if (txtCodCli.getText().trim().length() > 0) {
			if (cbListaFilial.getVlrString().equals("S"))
				sWhere += " AND (C.CODPESQ=" + txtCodCli.getText().trim()+
			  		      " OR C.CODCLI=" + txtCodCli.getText().trim()+")";
			else 
				sWhere += " AND V.CODCLI=" + txtCodCli.getText().trim();
			imp.addSubTitulo("CLIENTE: "+txtRazCli.getText().trim());
		}
		
		if(rgFaturados.getVlrString().equals("S")){
			sWhere1 = " AND TM.FISCALTIPOMOV='S' ";
			imp.addSubTitulo("FATURADOS");
		}
		else if(rgFaturados.getVlrString().equals("N")){
			sWhere1 = " AND TM.FISCALTIPOMOV='N' ";
			imp.addSubTitulo("NÃO FATURADOS");
		}
		else if(rgFaturados.getVlrString().equals("A"))
			sWhere1 = " AND TM.FISCALTIPOMOV IN ('S','N') ";
		
		if(rgFinanceiro.getVlrString().equals("S")){
			sWhere2 = " AND TM.SOMAVDTIPOMOV='S' ";
			imp.addSubTitulo("FINANCEIRO");
		}
		else if(rgFinanceiro.getVlrString().equals("N")){
			sWhere2 = " AND TM.SOMAVDTIPOMOV='N' ";
			imp.addSubTitulo("NÃO FINANCEIRO");
		}
		else if(rgFinanceiro.getVlrString().equals("A"))
			sWhere2 = " AND TM.SOMAVDTIPOMOV IN ('S','N') ";
		
		if(cbVendaCanc.getVlrString().equals("N"))
			sWhere3 = " AND NOT SUBSTR(V.STATUSVENDA,1,1)='C' ";
		
		
		if (cbListaFilial.getVlrString().equals("S") && (txtCodCli.getText().trim().length() > 0) ){  
			sSQL = "SELECT P."+sCodRel+", P.DESCPROD, P.CODUNID, SUM(IT.QTDITVENDA), SUM(IT.VLRLIQITVENDA) "+
				   "FROM VDVENDA V,EQTIPOMOV TM, VDCLIENTE C, VDITVENDA IT, EQPRODUTO P "+
				   "WHERE P.CODPROD=IT.CODPROD AND IT.CODVENDA=V.CODVENDA "+
		           sWhere1 + sWhere2 + sWhere3 +			  
		           "AND TM.CODEMP=V.CODEMPTM AND TM.CODFILIAL=V.CODFILIALTM AND TM.CODTIPOMOV=V.CODTIPOMOV "+
		           "AND TM.TIPOMOV IN ('VD','PV','VT','SE') "+
		           "AND V.CODCLI=C.CODCLI AND V.CODEMPCL=C.CODEMP AND V.CODFILIALCL=C.CODFILIAL "+
		           "AND V.DTEMITVENDA BETWEEN ? AND ? "+
		           sWhere + " AND V.FLAG IN " + Aplicativo.carregaFiltro(con,org.freedom.telas.Aplicativo.iCodEmp)+
		           " GROUP BY P."+sCodRel+","+
		           "P.DESCPROD,P.CODUNID " +
		           "ORDER BY "+sOrdem;
			listaFilial = true;
		} else {							
			sSQL = "SELECT P."+sCodRel+",P.DESCPROD,P.CODUNID,SUM(IT.QTDITVENDA),SUM(IT.VLRLIQITVENDA) " +
				   "FROM VDVENDA V,EQTIPOMOV TM,VDITVENDA IT, EQPRODUTO P " +
				   "WHERE P.CODEMP=? AND P.CODFILIAL=? " +
				   "AND IT.CODEMPPD=P.CODEMP AND IT.CODFILIALPD=P.CODFILIAL AND IT.CODPROD=P.CODPROD " +
				   "AND V.CODEMP=IT.CODEMP AND V.CODFILIAL=IT.CODFILIAL AND V.CODVENDA=IT.CODVENDA " +   
				   "AND V.DTEMITVENDA BETWEEN ? AND ? AND V.FLAG IN ('S','N') " +
				   sWhere + sWhere1 + sWhere2 + sWhere3 +
				   "AND TM.CODEMP=V.CODEMPTM AND TM.CODFILIAL=V.CODFILIALTM AND TM.CODTIPOMOV=V.CODTIPOMOV " +
				   "AND TM.TIPOMOV IN ('VD','PV','VT','SE') " +
				   "GROUP BY P."+sCodRel+",P.DESCPROD,P.CODUNID " +
				   "ORDER BY "+sOrdem;
		}
		
		try {
			
			imp.limpaPags();
			imp.montaCab();
			imp.setTitulo("Relatório de Vendas por ítem");
			imp.addSubTitulo("RELATORIO DE VENDAS POR ITEM  -  PERIODO DE :" + txtDataini.getVlrString() + " Até: " + txtDatafim.getVlrString());
			
			int paran = 1;
			ps = con.prepareStatement(sSQL);
			if(!listaFilial) {
				ps.setInt(paran++,Aplicativo.iCodEmp);
				ps.setInt(paran++,ListaCampos.getMasterFilial("EQPRODUTO"));
			}
			ps.setDate(paran++,Funcoes.dateToSQLDate(txtDataini.getVlrDate()));
			ps.setDate(paran++,Funcoes.dateToSQLDate(txtDatafim.getVlrDate()));
			rs = ps.executeQuery();
			while ( rs.next() ) {
				if (imp.pRow() == linPag) {
					imp.pulaLinha( 1, imp.comprimido() );
					imp.say(  0, "+" + sLinhaFina + "+" );
					imp.eject();
					imp.incPags();
				}
				if (imp.pRow()==0) {								
					imp.impCab(136, true);
					imp.pulaLinha( 0, imp.comprimido() );
					imp.say(  0, "|");
					imp.say(135, "|");
					imp.pulaLinha( 1, imp.comprimido() );
					imp.say(  0, "|" + sLinhaFina + "|" );
					imp.pulaLinha( 1, imp.comprimido());
					imp.say(  1, "| Cod.prod. " );
					imp.say( 15, "| Desc.produto" );
					imp.say( 69, "| Unid. " );
					imp.say( 77, "|   Quantidade " );
					imp.say(100, "|    Vlr.tot.item. " );
					imp.say(136, "|");
					imp.pulaLinha( 1, imp.comprimido());
					imp.say(  0, "|" + sLinhaFina + "|" );
				}

				imp.pulaLinha( 1, imp.comprimido());
				imp.say(  0, "|");
				imp.say(  3, Funcoes.copy(rs.getString(1),0,10) + " | " );
				imp.say( 17, Funcoes.copy(rs.getString(2),0,50) + " | " );
				imp.say( 70, Funcoes.copy(rs.getString(3),0,5) + " | " );						
				imp.say( 86, Funcoes.strDecimalToStrCurrency(6,1,rs.getString(4)) );
				imp.say( 99, "|");
				imp.say(100, Funcoes.strDecimalToStrCurrency(15,2,rs.getString(5)) );
				imp.say(135, "|");
				dQtd += rs.getDouble(4);
				dVlr += rs.getDouble(5);
			}	
			
			imp.pulaLinha( 1, imp.comprimido() );
			imp.say(  0, "|" + sLinhaLarga + "|" );
			imp.pulaLinha( 1, imp.comprimido() );
			imp.say(  0, "|");
			imp.say( 30, "Quant. vendida -> " );
			imp.say( 50, Funcoes.copy(String.valueOf(dQtd),6) );
			imp.say( 60, "Valor vendido -> " );
			imp.say( 78, Funcoes.strDecimalToStrCurrency(15,2,String.valueOf(dVlr)) );
			imp.say(135, "|");
			imp.pulaLinha( 1, imp.comprimido() );
			imp.say(  0, "+" + sLinhaLarga + "+" );
				
			imp.eject();
				
			imp.fechaGravacao();
			if (!con.getAutoCommit())
				con.commit();
		} catch ( SQLException err ) {
			err.printStackTrace();
			Funcoes.mensagemErro(this,"Erro consulta tabela vendas!\n"+err.getMessage(),true,con,err);			
		} catch ( Exception err ) {
			err.printStackTrace();	
		} finally {
			ps = null;
			rs = null;
			sWhere = null;
			sWhere1 = null;
			sWhere2 = null;
			sWhere3 = null;
			sOrdem = null;
			sOrdenado = null;
			sCodRel = null;
			sSQL= null;
			sLinhaFina = null;
			sLinhaLarga = null;
			System.gc();
		}
		
		if (bVisualizar) 
			imp.preview(this);
		else 
			imp.print();
	}
	
	private boolean comRef() {
		boolean bRetorno = false;
		String sSQL = "SELECT USAREFPROD FROM SGPREFERE1 WHERE CODEMP=? AND CODFILIAL=?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(sSQL);
			ps.setInt(1,Aplicativo.iCodEmp);
			ps.setInt(2,ListaCampos.getMasterFilial("SGPREFERE1"));
			rs = ps.executeQuery();
			if (rs.next())
				if (rs.getString("UsaRefProd").trim().equals("S"))
					bRetorno = true;
			if (!con.getAutoCommit())
				con.commit();
		} catch (SQLException err) {
			Funcoes.mensagemErro(this,"Erro ao carregar a tabela PREFERE1!\n"+err.getMessage(),true,con,err);
		}
		return bRetorno;
	}
	
	public void setConexao(Connection cn) {
		super.setConexao(cn);
		lcVend.setConexao(cn);
		lcGrup.setConexao(cn);
		lcMarca.setConexao(cn);
		lcCliente.setConexao(cn);		

		comref = comRef();
	}
}
