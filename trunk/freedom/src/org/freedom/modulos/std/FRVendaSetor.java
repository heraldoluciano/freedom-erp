/**
 * @version 24/03/2004 <BR>
 * @author Setpoint Informática Ltda./Robson Sanchez <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FRVendaSetor.java <BR>
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
import java.util.GregorianCalendar;
import java.util.Vector;

import javax.swing.JLabel;

import org.freedom.acao.RadioGroupEvent;
import org.freedom.acao.RadioGroupListener;
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

public class FRVendaSetor extends FRelatorio implements RadioGroupListener {
  private final int POS_CODSETOR = 0;
  private final int POS_MES = 1;
  private final int POS_CODGRUP = 2;
  private final int POS_SIGLAGRUP = 3;
  private final int POS_CODVEND = 4;
  private final int POS_VALOR = 5;
  private final int POS_TOTSETOR = 6;
  private final int TAM_GRUPO = 14;
  private Connection con;
  private JTextFieldPad txtDataini = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
  private JTextFieldPad txtDatafim = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
  private JTextFieldPad txtCodMarca = new JTextFieldPad();
  private JTextFieldFK txtDescMarca = new JTextFieldFK();
  private JTextFieldPad txtCodGrup1 = new JTextFieldPad();
  private JTextFieldFK txtDescGrup1 = new JTextFieldFK();
  private JTextFieldPad txtCodGrup2 = new JTextFieldPad();
  private JTextFieldFK txtDescGrup2 = new JTextFieldFK();
  private JTextFieldFK txtSiglaMarca = new JTextFieldFK();
  private JTextFieldPad txtCodSetor = new JTextFieldPad();
  private JTextFieldFK txtDescSetor = new JTextFieldFK();
  private JTextFieldPad txtCodVend = new JTextFieldPad();
  private JTextFieldFK txtNomeVend = new JTextFieldFK();
  private JTextFieldPad txtCodCli=new JTextFieldPad();
  private JTextFieldFK txtRazCli = new JTextFieldFK();
  
  private JCheckBoxPad cbVendas = new JCheckBoxPad("Só vendas?","S","N");
  private JCheckBoxPad cbCliPrinc = new JCheckBoxPad("Mostrar no cliente principal?","S","N");
  private JCheckBoxPad cbIncluiPed = new JCheckBoxPad("Incluir pedidos não faturados?","S","N");
  private JLabel lbCodMarca = new JLabel("Código e descrição da marca");
  private JLabel lbCodGrup1 = new JLabel("Código e descrição do grupo/somar");
  private JLabel lbCodGrup2 = new JLabel("Código e descrição do grupo/subtrair");
  private JLabel lbCodSetor = new JLabel("Código e descrição do setor");
  private JLabel lbCodVend = new JLabel("Código e nome do vendedor/repr.");
  private ListaCampos lcGrup1 = new ListaCampos(this);
  private ListaCampos lcGrup2 = new ListaCampos(this);
  private ListaCampos lcMarca = new ListaCampos(this);
  private ListaCampos lcSetor = new ListaCampos(this);
  private ListaCampos lcVendedor = new ListaCampos(this);
  private ListaCampos lcCliente = new ListaCampos(this);
  private Vector vLabTipoRel = new Vector();
  private Vector vValTipoRel = new Vector();
  private Vector vLabOrdemRel = new Vector();
  private Vector vValOrdemRel = new Vector();
  private JRadioGroup rgTipoRel = null;
  private JRadioGroup rgOrdemRel = null;

  public FRVendaSetor() {
    setTitulo("Relatório de Vendas por Setor");
    setAtribos(80,0,450,470);

    GregorianCalendar cal = new GregorianCalendar();
    cal.add(Calendar.DATE,-30);
    txtDataini.setVlrDate(cal.getTime());
    cal.add(Calendar.DATE,30);
    txtDatafim.setVlrDate(cal.getTime());
    txtDataini.setRequerido(true);    
    txtDatafim.setRequerido(true);

    cbVendas.setVlrString("S");
    cbCliPrinc.setVlrString("S");
    cbIncluiPed.setVlrString("N");
    
    vLabTipoRel.addElement("Vendedor");
    vLabTipoRel.addElement("Produto");
    vLabTipoRel.addElement("Cliente");
    vValTipoRel.addElement("V");
    vValTipoRel.addElement("P");
    vValTipoRel.addElement("C");
    
    rgTipoRel = new JRadioGroup(1,3,vLabTipoRel,vValTipoRel);
    rgTipoRel.addRadioGroupListener(this);
    
    vLabOrdemRel.addElement("Valor");
    vLabOrdemRel.addElement("Razão social");
    vLabOrdemRel.addElement("Cód.cli.");
    
    vValOrdemRel.addElement("V");
    vValOrdemRel.addElement("R");
    vValOrdemRel.addElement("C");
    
    
    rgOrdemRel = new JRadioGroup(3,1,vLabOrdemRel,vValOrdemRel);
    
    txtCodMarca.setTipo(JTextFieldPad.TP_STRING,6,0);
    txtDescMarca.setTipo(JTextFieldPad.TP_STRING,40,0);
    txtSiglaMarca.setTipo(JTextFieldPad.TP_STRING,20,0);
    lcMarca.add(new GuardaCampo( txtCodMarca, 7, 100, 80, 20, "CodMarca", "Código", true, false, null, JTextFieldPad.TP_STRING,false),"txtCodMarca");
    lcMarca.add(new GuardaCampo( txtDescMarca, 90, 100, 207, 20, "DescMarca", "Descrição", false, false, null, JTextFieldPad.TP_STRING,false),"txtDescMarca");
    lcMarca.add(new GuardaCampo( txtSiglaMarca, 90, 100, 207, 20, "SiglaMarca", "Sigla", false, false, null, JTextFieldPad.TP_STRING,false),"txtSiglaMarca");
    lcMarca.montaSql(false, "MARCA", "EQ");
    lcMarca.setReadOnly(true);
    txtCodMarca.setTabelaExterna(lcMarca);
    txtCodMarca.setFK(true);
    txtCodMarca.setNomeCampo("CodMarca");
    
    txtCodGrup1.setTipo(JTextFieldPad.TP_STRING,TAM_GRUPO,0);
    txtDescGrup1.setTipo(JTextFieldPad.TP_STRING,40,0);
    lcGrup1.add(new GuardaCampo( txtCodGrup1, 7, 100, 80, 20, "CodGrup", "Código", true, false, null, JTextFieldPad.TP_STRING,false),"txtCodGrup1");
    lcGrup1.add(new GuardaCampo( txtDescGrup1, 90, 100, 207, 20, "DescGrup", "Descrição", false, false, null, JTextFieldPad.TP_STRING,false),"txtDescGrup1");
    lcGrup1.montaSql(false, "GRUPO", "EQ");
    lcGrup1.setReadOnly(true);
    txtCodGrup1.setTabelaExterna(lcGrup1);
    txtCodGrup1.setFK(true);
    txtCodGrup1.setNomeCampo("CodGrup");

    txtCodGrup2.setTipo(JTextFieldPad.TP_STRING,TAM_GRUPO,0);
    txtDescGrup2.setTipo(JTextFieldPad.TP_STRING,40,0);
    lcGrup2.add(new GuardaCampo( txtCodGrup2, 7, 100, 80, 20, "CodGrup", "Código", true, false, null, JTextFieldPad.TP_STRING,false),"txtCodGrup2");
    lcGrup2.add(new GuardaCampo( txtDescGrup2, 90, 100, 207, 20, "DescGrup", "Descrição", false, false, null, JTextFieldPad.TP_STRING,false),"txtDescGrup2");
    lcGrup2.montaSql(false, "GRUPO", "EQ");
    lcGrup2.setReadOnly(true);
    txtCodGrup2.setTabelaExterna(lcGrup2);
    txtCodGrup2.setFK(true);
    txtCodGrup2.setNomeCampo("CodGrup");
    
    txtCodSetor.setTipo(JTextFieldPad.TP_INTEGER,8,0);
    txtDescSetor.setTipo(JTextFieldPad.TP_STRING,40,0);
    lcSetor.add(new GuardaCampo( txtCodSetor,0,0,0,0,"CodSetor","Cód.Setor",true,false, null, JTextFieldPad.TP_INTEGER, false ),"txtCodSetor");
    lcSetor.add(new GuardaCampo( txtDescSetor,0,0,0,0,"DescSetor","Descrição",false,false, null, JTextFieldPad.TP_STRING, false ),"txtDescSetor");
    lcSetor.montaSql(false,"SETOR","VD");
    lcSetor.setReadOnly(true);
    txtCodSetor.setTabelaExterna(lcSetor);
    txtCodSetor.setFK(true);
    txtCodSetor.setNomeCampo("CodSetor");

    txtCodVend.setTipo(JTextFieldPad.TP_INTEGER,8,0);
    txtNomeVend.setTipo(JTextFieldPad.TP_STRING,40,0);
    lcVendedor.add(new GuardaCampo( txtCodVend,0,0,0,0,"CodVend","Cód.Repr.",true,false, null, JTextFieldPad.TP_INTEGER, false ),"txtCodVend");
    lcVendedor.add(new GuardaCampo( txtNomeVend,0,0,0,0,"NomeVend","Nome",false,false, null, JTextFieldPad.TP_STRING, false ),"txtNomeVend");
    lcVendedor.montaSql(false,"VENDEDOR","VD");
    lcVendedor.setReadOnly(true);
    txtCodVend.setTabelaExterna(lcVendedor);
    txtCodVend.setFK(true);
    txtCodVend.setNomeCampo("CodVend");

	txtCodCli.setTipo(JTextFieldPad.TP_INTEGER,8,0);
	txtRazCli.setTipo(JTextFieldPad.TP_STRING,40,0);
	lcCliente.add(new GuardaCampo( txtCodCli, 7, 100, 80, 20, "CodCli", "Código", true, false, null, JTextFieldPad.TP_STRING,false),"txtCodCli");
	lcCliente.add(new GuardaCampo( txtRazCli, 90, 100, 207, 20, "RazCli", "Descrição", false, false, null, JTextFieldPad.TP_STRING,false),"txtRazCli");
	txtCodCli.setTabelaExterna(lcCliente);
	txtCodCli.setNomeCampo("CodCli");
	txtCodCli.setFK(true);
	lcCliente.setReadOnly(true);
	lcCliente.montaSql(false, "CLIENTE", "VD");
    
    adic(new JLabel("Formato de impressão"),7,0,200,20);
    adic(rgTipoRel,7,20,270,30);
    adic(new JLabel("Ordem"),280,0,80,20);
    adic(rgOrdemRel,280,20,120,80);
    adic(new JLabel("Período"),7,50,250,20);
    adic(txtDataini,7,70,100,20);
    adic(txtDatafim,110,70,100,20);
    adic(lbCodMarca,7,90,250,20);
    adic(txtCodMarca,7,110,80,20);
    adic(txtDescMarca,90,110,307,20);
    adic(lbCodGrup1,7,130,250,20);
    adic(txtCodGrup1,7,150,80,20);
    adic(txtDescGrup1,90,150,307,20);
    adic(lbCodGrup2,7,170,250,20);
    adic(txtCodGrup2,7,190,80,20);
    adic(txtDescGrup2,90,190,307,20);
    adic(lbCodSetor,7,210,250,20);
    adic(txtCodSetor,7,230,80,20);
    adic(txtDescSetor,90,230,307,20);
    adic(lbCodVend,7,250,250,20);
    adic(txtCodVend,7,270,80,20);
    adic(txtNomeVend,90,270,307,20);
    adic(cbVendas,7,295,100,25);
    adic(cbCliPrinc,110,295,200,25);
    adic(cbIncluiPed,7,315,295,25);
	adic(new JLabel("Código e razão social do cliente"),7,340,200,20);
	adic(txtCodCli,7,360,80,20);
	adic(txtRazCli,90,360,307,20);
    
  }

  
  public void imprimir(boolean bVisualizar) {
	if (txtDataini.getVlrString().length() < 10 || txtDatafim.getVlrString().length() < 10) {
		Funcoes.mensagemInforma(this,"Período inválido!");
		return;
	}
	
    if (rgTipoRel.getVlrString().equals("V"))
    	impVendedor(bVisualizar);
    else if (rgTipoRel.getVlrString().equals("P"))
    	impProduto(bVisualizar);
    else if (rgTipoRel.getVlrString().equals("C"))
    	impCliente(bVisualizar);
	
  }

  public void valorAlterado(RadioGroupEvent rge) {
      String sTipoRel = null;
      try {
          sTipoRel = rge.getRadioButton().getText();
          if (sTipoRel.equalsIgnoreCase("CLIENTE"))
              rgOrdemRel.setAtivo(true);
          else
              rgOrdemRel.setAtivo(false);
      }
      finally {
          sTipoRel = null;
      }
  }
  
  private void impVendedor(boolean bVisualizar) {
  	String sSql = "";
  	String sWhere = "";
  	String sFrom = "";
  	String sCodMarca = "";
  	String sCodGrup1 = "";
  	String sCodGrup2 = "";
  	String sCodSetor = "";
  	String sCodSetorAnt = "";
  	String sCodGrupAnt = "";
  	String sCodGrup = "";
  	String sMesAnt = "";
  	String sSiglaGroup = "";
  	String sMes = "";
  	String sFiltros1 = "";
  	String sFiltros2 = "";
  	String sTipoMov = "";
  	ImprimeOS imp = null;
  	int linPag = 0;
  	int iParam = 1;
  	int iCol = 0;
  	int iColAnt = 0;
  	int iPos = 0;
  	int iLinsSetor = 0;
  	int iCodSetor = 0;
  	int iCodCli = 0;
  	int iCodVend = 0;
  	PreparedStatement ps = null;
  	ResultSet rs = null;
  	Vector vItens = null;
  	Vector vItem = null;
  	Vector vTotSetor = null;
  	Vector vCols = null;
  	double deValor = 0;
  	double deTotal1 = 0;
  	double deTotalGeral = 0;

  	try {
  		
  		imp = new ImprimeOS("",con);
  		linPag = imp.verifLinPag()-1;
  		imp.setTitulo("Relatorio de Vendas por Setor");
  		sCodMarca = txtCodMarca.getVlrString().trim();
  		sCodGrup1 = txtCodGrup1.getVlrString().trim();
  		sCodGrup2 = txtCodGrup2.getVlrString().trim();
  		iCodSetor = txtCodSetor.getVlrInteger().intValue();
  		iCodVend = txtCodVend.getVlrInteger().intValue();
  		iCodCli = txtCodCli.getVlrInteger().intValue();
		if (cbVendas.getVlrString().equals("S")) {
  			sFiltros1 = "SOMENTE VENDAS";
  			sTipoMov = "'VD'";
  			
		}
		if (cbIncluiPed.getVlrString().equals("S")) {
			if (!sTipoMov.equals("")) {
			   sFiltros1 += (!sFiltros1.equals("")?" / ":"")+"INCLUI PEDIDOS";
			   sTipoMov += ",'PV'";
			}
		}
		if (!sTipoMov.equals("")) {
			sTipoMov = " TM.TIPOMOV IN ("+sTipoMov+") AND ";
		}

  		if (!sCodMarca.equals("")) { 
  			sWhere += "AND P.CODEMPMC=? AND P.CODFILIALMC=? AND P.CODMARCA=? ";
  			sFiltros1 += (!sFiltros1.equals("")?" / ":"")+"M.: "+txtDescMarca.getText().trim();
  		}
  		if (!sCodGrup1.equals("")) {
  			sWhere += "AND G.CODEMP=? AND G.CODFILIAL=? AND G.CODGRUP LIKE ? ";
  			sFiltros1 += (!sFiltros1.equals("")?" / ":"")+"G.: "+txtDescGrup1.getText().trim();
  		}
  		if (!sCodGrup2.equals("")) {
  			sWhere += "AND ( NOT P.CODGRUP=? ) ";
  			sFiltros1 += (!sFiltros1.equals("")?" / ":"")+" EXCL. G.: "+txtDescGrup2.getText().trim();
  		}
  		if (iCodSetor!=0) {
  			sWhere += "AND VD.CODSETOR=? ";
  			sFiltros2 += (!sFiltros2.equals("")?" / ":"")+" SETOR: "+iCodSetor+"-"+txtDescSetor.getVlrString().trim();
  		}
  		if (iCodVend!=0) {
  			sWhere += "AND VD.CODVEND=? ";
  			sFiltros2 += (!sFiltros2.equals("")?" / ":"")+" REPR.: "+iCodVend+"-"+txtNomeVend.getVlrString().trim();
  		}
  		if (iCodCli!=0) {
  			if (cbCliPrinc.getVlrString().equals("S")) {
  				sFiltros2 += (!sFiltros2.equals("")?" / ":"")+"AGRUP. CLI. PRINC.";
  			}
  			if (cbCliPrinc.getVlrString().equals("S")) {
  				sWhere += "AND C2.CODCLI=V.CODCLI AND C2.CODEMP=V.CODEMPCL AND C2.CODFILIAL=V.CODFILIALCL AND " +
  						"C2.CODPESQ=C1.CODPESQ AND C2.CODEMPPQ=C1.CODEMPPQ AND C2.CODFILIALPQ=C1.CODFILIALPQ AND " +
  						"C1.CODCLI=? ";
  				sFrom = ",VDCLIENTE C1, VDCLIENTE C2 ";
  			}
  			else {
  				sWhere += "AND V.CODCLI=? ";
  			}
  			sFiltros2 += (!sFiltros2.equals("")?" / ":"")+" CLI.: "+iCodCli+"-"+Funcoes.copy(txtRazCli.getVlrString(),30);
  		}
  			
  		sSql = "SELECT VD.CODSETOR,"+
		"CAST(SUBSTR(CAST(V.DTEMITVENDA AS CHAR(10)),1,7) AS CHAR(7)) ANO_MES,"+
		"G.CODGRUP,G.SIGLAGRUP,V.CODVEND,SUM(VLRLIQITVENDA) VLRVENDA "+
		"FROM VDVENDA V, VDITVENDA IV, VDVENDEDOR VD, EQPRODUTO P, EQGRUPO G, EQTIPOMOV TM "+sFrom+
		"WHERE V.CODEMP=? AND V.CODFILIAL=? AND " +
		"V.DTEMITVENDA BETWEEN ? AND ? AND "+
		"IV.CODEMP=V.CODEMP AND IV.CODFILIAL=V.CODFILIAL AND "+
		"IV.CODVENDA=V.CODVENDA AND IV.TIPOVENDA=V.TIPOVENDA AND "+
		"VD.CODEMP=V.CODEMPVD AND VD.CODFILIAL=V.CODFILIALVD AND "+
		"VD.CODVEND=V.CODVEND AND VD.CODSETOR IS NOT NULL AND "+
		"P.CODEMP=IV.CODEMPPD AND P.CODFILIAL=IV.CODFILIALPD AND "+
		"P.CODPROD=IV.CODPROD AND G.CODEMP=P.CODEMPGP AND " +
		"G.CODFILIAL=P.CODFILIALGP AND "+
		"TM.CODEMP=V.CODEMPTM AND TM.CODFILIAL=V.CODFILIALTM AND " +
		"TM.CODTIPOMOV=V.CODTIPOMOV AND ( NOT SUBSTR(V.STATUSVENDA,1,1)='C' ) AND "+sTipoMov+
		(sCodGrup1.equals("")?"P.CODGRUP=G.CODGRUP ":"SUBSTR(P.CODGRUP,1,"+sCodGrup1.length()+")=G.CODGRUP ")+
		sWhere+
		"GROUP BY 1,2,3,4,5"+
		"ORDER BY 1,2,3,4,5";
  		
  		//System.out.println(sSql);
  		
  		try {
  			ps = con.prepareStatement(sSql);
  			ps.setInt(1,Aplicativo.iCodEmp);
  			ps.setInt(2,ListaCampos.getMasterFilial("VDVENDA"));
  			ps.setDate(3,Funcoes.dateToSQLDate(txtDataini.getVlrDate()));
  			ps.setDate(4,Funcoes.dateToSQLDate(txtDatafim.getVlrDate()));
  			iParam = 5;
  			if (!sCodMarca.equals("")) {
  				ps.setInt(iParam,Aplicativo.iCodEmp);
  				ps.setInt(iParam+1,ListaCampos.getMasterFilial("EQMARCA"));
  				ps.setString(iParam+2,sCodMarca);
  				iParam += 3;
  			}
  			if (!sCodGrup1.equals("")) {
  				ps.setInt(iParam,Aplicativo.iCodEmp);
  				ps.setInt(iParam+1,ListaCampos.getMasterFilial("EQGRUPO"));
  				ps.setString(iParam+2,sCodGrup1+(sCodGrup1.length()<TAM_GRUPO?"%":""));
  				iParam += 3;
  			}
  			if (!sCodGrup2.equals("")) {
  				ps.setString(iParam,sCodGrup2);
  				iParam += 1;
  			}
  			if (iCodSetor!=0) {
  				ps.setInt(iParam,iCodSetor);
  				iParam += 1;
  			}
  			if (iCodVend!=0) {
  				ps.setInt(iParam,iCodVend);
  				iParam += 1;
  			}
  			if (iCodCli!=0) {
  				ps.setInt(iParam,iCodCli);
  				iParam += 1;
  			}
  			rs = ps.executeQuery();
  			vItens = new Vector();
  			sCodSetorAnt = "";
  			sCodSetor = "";
  			sCodGrupAnt = "";
  			sCodGrup = "";
  			sMes = "";
  			sMesAnt = "";
  			
  			while ( rs.next() ) {
  				vItem = new Vector();
  				sCodSetor = ""+rs.getInt(1);
  				sMes = rs.getString(2);
  				sCodGrup = rs.getString(3);
  				if ( (!sCodSetorAnt.equals("")) && ( (!sCodSetorAnt.equals(sCodSetor))  
  					 || (!sCodGrupAnt.equals(sCodGrup)) || (!sMesAnt.equals(sMes) ) ) )
  					deTotal1 = 0;
  				sSiglaGroup = rs.getString(4);
  				if (sSiglaGroup==null) sSiglaGroup="";
  				vItem.addElement(sCodSetor); // 0 - Setor  
  				vItem.addElement(sMes); // 1 - Mês
  				vItem.addElement(sCodGrup); // 2 - Cód. Grupo
  				vItem.addElement(sSiglaGroup); // 3 - Sigla Grupo 
  				vItem.addElement(""+rs.getInt(5)); // 4 - Cód. Vendedor
  				deValor = rs.getDouble(6);
  				deTotal1 += deValor;
  				deTotalGeral += deValor;
  				vItem.addElement(new Double(deValor)); // 5 - Valor
  				vItem.addElement(new Double(deTotal1)); // 6 - Total do setor
  				vItens.addElement(vItem);
  				sCodSetorAnt = sCodSetor;
  				sMesAnt = sMes;
  				sCodGrupAnt = sCodGrup;
  			}
  			
  			rs.close();
  			ps.close();
  			if (!con.getAutoCommit())
  				con.commit();

  			imp.limpaPags();
  			vTotSetor = new Vector();
  			
  			sCodSetorAnt = "";
  			sCodSetor = "";
  			sMesAnt = "";
  			sMes = "";
  			sCodGrupAnt = "";
  			sCodGrup = "";
  			vCols = new Vector();
  			
  			for (int i=0; i<vItens.size(); i++) {
  				vItem = (Vector) vItens.elementAt(i);

  				if ( (!sCodSetor.equals(vItem.elementAt(POS_CODSETOR).toString())) 
  					 || (!sMes.equals(vItem.elementAt(POS_MES).toString()))
  					 || (!sCodGrup.equals(vItem.elementAt(POS_CODGRUP).toString())) ) {
  					if (!sCodSetorAnt.equals("")) {
  						for (int iConta=iCol; iConta<9; iConta++) {
  		  					imp.say(imp.pRow()+0,21+(iConta*11),"|"+Funcoes.replicate(" ",10));
  						}
  						imp.say(imp.pRow()+0,124, "|"+Funcoes.strDecimalToStrCurrency(11,2,((Vector) vItens.elementAt(i-1)).elementAt(POS_TOTSETOR).toString()));
  		  				imp.say(imp.pRow()+0,136,"|");
  		  				iLinsSetor ++;
  		  				if (imp.pRow()>=(linPag-1)) {
  		  					imp.say(imp.pRow()+1,0,""+imp.comprimido());
  		  					imp.say(imp.pRow()+0,0,"+"+Funcoes.replicate("-",134)+"+");
  		  					imp.incPags();
  		  					imp.eject();
  		  				}
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
  		  				imp.say(imp.pRow()+0,60,"VENDAS POR SETOR");
  		  				imp.say(imp.pRow()+0,136,"|");
  		  				if (!sFiltros1.equals("")) {
  		  					imp.say(imp.pRow()+1,0,""+imp.comprimido());
  		 					imp.say(imp.pRow()+0,0,"|");
  	  	 					imp.say(imp.pRow()+0,68-(sFiltros1.length()/2),sFiltros1);
  	  	 					imp.say(imp.pRow()+0,136,"|");
  	  	  				}
  		  				if (!sFiltros2.equals("")) {
  		  					imp.say(imp.pRow()+1,0,""+imp.comprimido());
  		 					imp.say(imp.pRow()+0,0,"|");
  	  	 					imp.say(imp.pRow()+0,68-(sFiltros2.length()/2),sFiltros2);
  	  	 					imp.say(imp.pRow()+0,136,"|");
  	  	  				}
  		  				imp.say(imp.pRow()+1,0,""+imp.comprimido());
  		  				imp.say(imp.pRow()+0,0,"|");
  		  				imp.say(imp.pRow()+0,50,"PERIODO DE: "+txtDataini.getVlrString()+" ATE: "+txtDatafim.getVlrString());
  		  				imp.say(imp.pRow()+0,136,"|");
  		  				imp.say(imp.pRow()+1,0,""+imp.comprimido());
						imp.say(imp.pRow()+0,0,"+"+Funcoes.replicate("-",134)+"+");
						iCol=0;
	  				}

  					if (!sCodSetor.equals(vItem.elementAt(POS_CODSETOR).toString())) {
  						if ( !sCodSetorAnt.equals("") ) {
  							if (iLinsSetor>1) {
  		  	  					imp.say(imp.pRow()+1,0,""+imp.comprimido());
  		  	  					imp.say(imp.pRow()+0,0,"+"+Funcoes.replicate("-",134)+"+");
  								imp.say(imp.pRow()+1,0,""+imp.comprimido());
  		  	  					imp.say(imp.pRow()+0,0,"| SUBTOTAL");
  		  	  					imp.say(imp.pRow()+0,21,getTotSetor(vTotSetor));
  		  	  					imp.say(imp.pRow()+0,136,"|");
  							}
  							iLinsSetor = 0;
  						}
  					    sCodSetor = vItem.elementAt(POS_CODSETOR).toString();
  	  					vCols = getVendedores(sCodSetor,vItens);
                        vTotSetor = initTotSetor(vCols);
  	  					imp.say(imp.pRow()+1,0,""+imp.comprimido());
  	  					imp.say(imp.pRow()+0,0,"+"+Funcoes.replicate("-",134)+"+");
  	  					imp.say(imp.pRow()+1,0,""+imp.comprimido());
  	  					imp.say(imp.pRow()+0,0,"|");
  	  					imp.say(imp.pRow()+0,70,"SETOR: "+sCodSetor);
  	  					imp.say(imp.pRow()+0,136,"|");
  	  					imp.say(imp.pRow()+1,0,""+imp.comprimido());
  	  					imp.say(imp.pRow()+0,0,"+"+Funcoes.replicate("-",134)+"+");
  	  					imp.say(imp.pRow()+1,0,"|MES");
  	  					imp.say(imp.pRow()+0,9,"|GRUPO");
  	  					imp.say(imp.pRow()+0,21,getColSetor(vCols));
  	  					imp.say(imp.pRow()+0,136,"|");
  	  					imp.say(imp.pRow()+1,0,""+imp.comprimido());
  	  					imp.say(imp.pRow()+0,0,"+"+Funcoes.replicate("-",134)+"+");
  	  					iLinsSetor = 0;
  					}
				    sCodSetor = vItem.elementAt(POS_CODSETOR).toString();
  					sMes = vItem.elementAt(POS_MES).toString();
  					sCodGrup = vItem.elementAt(POS_CODGRUP).toString();
  					sCodSetorAnt = sCodSetor;
  					sCodGrupAnt = sCodGrup;
  					sMesAnt = sMes;
					iCol=0;
  				}
  				
  				if (iCol==0) {
  					imp.say(imp.pRow()+1,0,""+imp.comprimido());
  	  				imp.say(imp.pRow()+0,0,"|"+vItem.elementAt(POS_MES).toString());
  	  				imp.say(imp.pRow()+0,9,"| "+(vItem.elementAt(POS_SIGLAGRUP).equals("")?Funcoes.copy(vItem.elementAt(POS_CODGRUP).toString(),10):vItem.elementAt(POS_SIGLAGRUP).toString()));
  				}

  				if (vItem.elementAt(POS_SIGLAGRUP).toString().trim().equals("GOLD")) {
  	  				iColAnt = iCol;
  				}
  				iColAnt = iCol;
  				iCol = posVendedor(vItem.elementAt(POS_CODVEND).toString(),vCols);
  				for (int iAjusta=iColAnt; iAjusta<iCol; iAjusta++) {
  	  				imp.say(imp.pRow()+0,21+(iAjusta*11),"|"+Funcoes.replicate(" ",10));
  				}
  				imp.say(imp.pRow()+0,21+(iCol*11),"|"+Funcoes.strDecimalToStrCurrency(10,2,vItem.elementAt(POS_VALOR)+""));
  				vTotSetor = adicValorSetor(iCol,(Double) vItem.elementAt(POS_VALOR),vTotSetor);
//  				imp.say(imp.pRow()+0,136,"|");
  				iCol++;
  				iPos = i;
//  				bVlrTotal += v
  			}
			// Imprime o total do setor após a impressão do relatório, caso não tenha sido impresso
  			if (iPos<vItens.size()) {
  				for (int iConta=iCol; iConta<9; iConta++) {
  					imp.say(imp.pRow()+0,21+(iConta*11),"|"+Funcoes.replicate(" ",10));
  				}
  				imp.say(imp.pRow()+0,124,"|"+Funcoes.strDecimalToStrCurrency(11,2,((Vector) vItens.elementAt(iPos)).elementAt(POS_TOTSETOR).toString()));
  				imp.say(imp.pRow()+0,136,"|");
  				iLinsSetor ++ ;
  				iCol = 0;
  			}

  			if ( !sCodSetorAnt.equals("") ) {
				if (iLinsSetor>1) {
	  	  			imp.say(imp.pRow()+1,0,""+imp.comprimido());
	  	  			imp.say(imp.pRow()+0,0,"+"+Funcoes.replicate("-",134)+"+");
	  	  			imp.say(imp.pRow()+1,0,""+imp.comprimido());
  					imp.say(imp.pRow()+0,0,"| SUBTOTAL");
  					imp.say(imp.pRow()+0,21,getTotSetor(vTotSetor));
  					imp.say(imp.pRow()+0,136,"|");
				}
				// Total Geral 
  	  			imp.say(imp.pRow()+1,0,""+imp.comprimido());
  	  			imp.say(imp.pRow()+0,0,"+"+Funcoes.replicate("-",134)+"+");
  	  			imp.say(imp.pRow()+1,0,""+imp.comprimido());
				imp.say(imp.pRow()+0,0,"| TOTAL");
				imp.say(imp.pRow()+0,124,"|"+Funcoes.strDecimalToStrCurrency(11,2,deTotalGeral+""));
				imp.say(imp.pRow()+0,136,"|");

  			}
  			// Fim da impressão do total por setor
  			
  			imp.say(imp.pRow()+1,0,""+imp.comprimido());
  			imp.say(imp.pRow()+0,0,"+"+Funcoes.replicate("-",134)+"+");
  			imp.eject();
  			imp.fechaGravacao();
  			
  		}
  		catch (SQLException e) {
  			Funcoes.mensagemErro(this,"Erro executando a consulta.\n"+e.getMessage());
  			e.printStackTrace();
  		}
  		if (bVisualizar) {
  			imp.preview(this);
  		}
  		else {
  			imp.print();
  		}
  	}
  	finally {
  		sWhere = null;
  		sSql = null;
  		sCodMarca = null; 
  		sCodSetor = null;
  		sCodSetorAnt = null;
  		sCodGrup1 = null;
  		sCodGrup2 = null;
  		sCodGrupAnt = null;
  		sFiltros1 = null;
  		sFiltros2 = null;
  		sFrom = null;
  		iCodSetor = 0;
  		iCodVend = 0;
  		iCodCli = 0;
		sCodGrup = null;
		sSiglaGroup = null;
		sTipoMov = null;
  		sMesAnt = null;
  		sMes = null;
  		imp = null;
  		ps = null;
  		rs = null;
  		vItens = null;
  		vItem = null;
  		vCols = null;
  		deTotal1 = 0;
  		deTotalGeral = 0;
  		deValor = 0;
  	}
  	
  }

  private void impProduto(boolean bVisualizar) {
  	String sSql = "";
  	String sWhere = "";
  	String sFrom = "";
  	String sCodMarca = "";
  	String sCodGrup1 = "";
  	String sCodGrup2 = "";
  	String sFiltros1 = "";
  	String sFiltros2 = "";
  	String sTipoMov = "";
  	int iCodSetor = 0;
  	int iCodVend = 0;
  	int iCodCli = 0;
  	ImprimeOS imp = null;
  	int linPag = 0;
  	int iParam = 0;
  	PreparedStatement ps = null;
  	ResultSet rs = null;
  	double deVlrTotal = 0;
  	double deQtdTotal = 0;

  	try {
  		
  		imp = new ImprimeOS("",con);
  		linPag = imp.verifLinPag()-1;
  		imp.setTitulo("Relatorio de Vendas por Setor x Produto");
  		sFiltros1 = "";
  		sFiltros2 = "";
		if (cbVendas.getVlrString().equals("S")) {
			sFiltros1 = "SOMENTE VENDAS";
			sTipoMov = "'VD'";
		}
		if (cbIncluiPed.getVlrString().equals("S")) {
			if (!sTipoMov.equals("")) {
			   sFiltros1 += (!sFiltros1.equals("")?" / ":"")+"INCLUI PEDIDOS";
			   sTipoMov += ",'PV'";
			}
		}
	    if (!sTipoMov.equals("")) {
	    	sTipoMov = " TM.TIPOMOV IN ("+sTipoMov+") AND ";
	    }

  		sCodMarca = txtCodMarca.getVlrString().trim();
  		sCodGrup1 = txtCodGrup1.getVlrString().trim();
  		sCodGrup2 = txtCodGrup2.getVlrString().trim();
  		iCodSetor = txtCodSetor.getVlrInteger().intValue();
  		iCodVend = txtCodVend.getVlrInteger().intValue();
  		iCodCli = txtCodCli.getVlrInteger().intValue();
  		if (!sCodMarca.equals("")) { 
  			sWhere += "AND P.CODEMPMC=? AND P.CODFILIALMC=? AND P.CODMARCA=? ";
  			sFiltros1 += (!sFiltros1.equals("")?" / ":"")+"M.: "+txtDescMarca.getText().trim();
  		}
  		if (!sCodGrup1.equals("")) {
  			sWhere += "AND G.CODEMP=? AND G.CODFILIAL=? AND G.CODGRUP LIKE ? ";
  			sFiltros1 += (!sFiltros1.equals("")?" / ":"")+"G.: "+txtDescGrup1.getText().trim();
  		}
  		if (!sCodGrup2.equals("")) {
  			sWhere += "AND ( NOT P.CODGRUP=? ) ";
  			sFiltros1 += (!sFiltros1.equals("")?" / ":"")+" EXCL. G.: "+txtDescGrup2.getText().trim();
  		}
  		if (iCodSetor!=0) {
  			sWhere += "AND VD.CODSETOR=? ";
  			sFiltros2 += (!sFiltros2.equals("")?" / ":"")+" SETOR: "+iCodSetor+"-"+txtDescSetor.getVlrString().trim();
  		}
  		if (iCodVend!=0) {
  			sWhere += "AND V.CODVEND=? ";
  			sFiltros2 += (!sFiltros2.equals("")?" / ":"")+" REPR.: "+iCodVend+"-"+txtNomeVend.getVlrString().trim();
  		}
  		if (iCodCli!=0) {
  	  		if (iCodCli!=0) {
  	  			if (cbCliPrinc.getVlrString().equals("S")) {
  	  				sFiltros2 += (!sFiltros2.equals("")?" / ":"")+"AGRUP. CLI. PRINC.";
  	  			}
  	  			if (cbCliPrinc.getVlrString().equals("S")) {
  	  				sWhere += "AND C2.CODCLI=V.CODCLI AND C2.CODEMP=V.CODEMPCL AND C2.CODFILIAL=V.CODFILIALCL AND " +
  	  						"C2.CODPESQ=C1.CODPESQ AND C2.CODEMPPQ=C1.CODEMPPQ AND C2.CODFILIALPQ=C1.CODFILIALPQ AND " +
  	  						"C1.CODCLI=? ";
  	  				sFrom = ",VDCLIENTE C1, VDCLIENTE C2 ";
  	  			}
  	  			else {
  	  				sWhere += "AND V.CODCLI=? ";
  	  			}
  	  			sFiltros2 += (!sFiltros2.equals("")?" / ":"")+" CLI.: "+iCodCli+"-"+Funcoes.copy(txtRazCli.getVlrString(),30);
  	  		}
  		}
  			
  		sSql = "SELECT P.DESCPROD,P.CODPROD,P.REFPROD,SUM(IV.QTDITVENDA) QTDVENDA ,SUM(IV.VLRLIQITVENDA) VLRVENDA "+
		"FROM VDVENDA V, VDITVENDA IV, VDVENDEDOR VD, EQPRODUTO P, EQGRUPO G, EQTIPOMOV TM "+sFrom+
		"WHERE V.CODEMP=? AND V.CODFILIAL=? AND " +
		"V.DTEMITVENDA BETWEEN ? AND ? AND "+
		"IV.CODEMP=V.CODEMP AND IV.CODFILIAL=V.CODFILIAL AND "+
		"IV.CODVENDA=V.CODVENDA AND IV.TIPOVENDA=V.TIPOVENDA AND "+
		"VD.CODEMP=V.CODEMPVD AND VD.CODFILIAL=V.CODFILIALVD AND "+
		"VD.CODVEND=V.CODVEND AND VD.CODSETOR IS NOT NULL AND "+
		"P.CODEMP=IV.CODEMPPD AND P.CODFILIAL=IV.CODFILIALPD AND "+
		"P.CODPROD=IV.CODPROD AND G.CODEMP=P.CODEMPGP AND " +
		"G.CODFILIAL=P.CODFILIALGP AND "+
		"TM.CODEMP=V.CODEMPTM AND TM.CODFILIAL=V.CODFILIALTM AND " +
		"TM.CODTIPOMOV=V.CODTIPOMOV AND ( NOT SUBSTR(V.STATUSVENDA,1,1)='C' ) AND "+sTipoMov+
		(sCodGrup1.equals("")?"P.CODGRUP=G.CODGRUP ":"SUBSTR(P.CODGRUP,1,"+sCodGrup1.length()+")=G.CODGRUP ")+
		sWhere+
		"GROUP BY 1,2,3"+
		"ORDER BY 1,2,3";
  		
//  		System.out.println(sSql);
  		
  		try {
  			ps = con.prepareStatement(sSql);
  			ps.setInt(1,Aplicativo.iCodEmp);
  			ps.setInt(2,ListaCampos.getMasterFilial("VDVENDA"));
  			ps.setDate(3,Funcoes.dateToSQLDate(txtDataini.getVlrDate()));
  			ps.setDate(4,Funcoes.dateToSQLDate(txtDatafim.getVlrDate()));
  			iParam = 5;
  			if (!sCodMarca.equals("")) {
  				ps.setInt(iParam,Aplicativo.iCodEmp);
  				ps.setInt(iParam+1,ListaCampos.getMasterFilial("EQMARCA"));
  				ps.setString(iParam+2,sCodMarca);
  				iParam += 3;
  			}
  			if (!sCodGrup1.equals("")) {
  				ps.setInt(iParam,Aplicativo.iCodEmp);
  				ps.setInt(iParam+1,ListaCampos.getMasterFilial("EQGRUPO"));
  				ps.setString(iParam+2,sCodGrup1+(sCodGrup1.length()<TAM_GRUPO?"%":""));
  				iParam += 3;
  			}
  			if (!sCodGrup2.equals("")) {
  				ps.setString(iParam,sCodGrup2);
  				iParam += 1;
  			}
  			if (iCodSetor!=0) {
  				ps.setInt(iParam,iCodSetor);
  				iParam += 1;
  			}
  			if (iCodVend!=0) {
  				ps.setInt(iParam,iCodVend);
  				iParam += 1;
  			}
  			if (iCodCli!=0) {
  				ps.setInt(iParam,iCodCli);
  				iParam += 1;
  			}
  			
  			rs = ps.executeQuery();
  			
  			imp.limpaPags();
  			
  			while ( rs.next() ) {
  				if (imp.pRow()>=(linPag-1)) {
  					imp.say(imp.pRow()+1,0,""+imp.comprimido());
  					imp.say(imp.pRow()+0,0,"+"+Funcoes.replicate("-",134)+"+");
  					imp.incPags();
  					imp.eject();

  				}
	  			if (imp.pRow()==0) {
	  				imp.say(imp.pRow()+1,0,""+imp.comprimido());
	  				imp.say(imp.pRow()+0,1,"+"+Funcoes.replicate("-",133)+"+");
	  				imp.say(imp.pRow()+1,0,""+imp.comprimido());
	  				imp.say(imp.pRow()+0,1,"| Emitido em :"+Funcoes.dateToStrDate(new Date()));
	  				imp.say(imp.pRow()+0,120,"Pagina : "+(imp.getNumPags()));
	  				imp.say(imp.pRow()+0,136,"|");
	  				imp.say(imp.pRow()+1,0,""+imp.comprimido());
	  				imp.say(imp.pRow()+0,1,"|");
	  				imp.say(imp.pRow()+0,55,"VENDAS POR SETOR X PRODUTO");
	  				imp.say(imp.pRow()+0,136,"|");
	  				if (!sFiltros1.equals("")) {
	  					imp.say(imp.pRow()+1,0,""+imp.comprimido());
	 					imp.say(imp.pRow()+0,1,"|");
  	 					imp.say(imp.pRow()+0,68-(sFiltros1.length()/2),sFiltros1);
  	 					imp.say(imp.pRow()+0,136,"|");
  	  				}
	  				if (!sFiltros2.equals("")) {
	  					imp.say(imp.pRow()+1,0,""+imp.comprimido());
	 					imp.say(imp.pRow()+0,1,"|");
  	 					imp.say(imp.pRow()+0,68-(sFiltros2.length()/2),sFiltros2);
  	 					imp.say(imp.pRow()+0,136,"|");
  	  				}
  	  				imp.say(imp.pRow()+1,0,""+imp.comprimido());
  	  				imp.say(imp.pRow()+0,1,"|");
  	  				imp.say(imp.pRow()+0,50,"PERIODO DE: "+txtDataini.getVlrString()+" ATE: "+txtDatafim.getVlrString());
  	  				imp.say(imp.pRow()+0,136,"|");
  	  				imp.say(imp.pRow()+1,0,""+imp.comprimido());
					imp.say(imp.pRow()+0,1,"+"+Funcoes.replicate("-",133)+"+");
					imp.say(imp.pRow()+1,0,""+imp.comprimido());
					imp.say(imp.pRow()+0,1,"| DESCRICAO DO PRODUTO");
					imp.say(imp.pRow()+0,55,"| CODIGO");
					imp.say(imp.pRow()+0,67,"| QUANTIDADE");
					imp.say(imp.pRow()+0,81,"|     VALOR");
					imp.say(imp.pRow()+0,99,"|");
					imp.say(imp.pRow()+0,136,"|");
					imp.say(imp.pRow()+1,0,""+imp.comprimido());
					imp.say(imp.pRow()+0,1,"+"+Funcoes.replicate("-",133)+"+");
					
				}
				
  				imp.say(imp.pRow()+1,0,""+imp.comprimido());
  				imp.say(imp.pRow()+0,1,"|");
  				imp.say(imp.pRow()+0,4,Funcoes.adicionaEspacos(rs.getString(1),50)+" |");
  				imp.say(imp.pRow()+0,56,Funcoes.adicEspacosEsquerda(rs.getString(2),10)+" |");
  				imp.say(imp.pRow()+0,70,Funcoes.adicEspacosEsquerda(rs.getDouble(4)+"",10)+" |");
  				imp.say(imp.pRow()+0,83,Funcoes.strDecimalToStrCurrency(15,2,rs.getString(5))+" |");
  				imp.say(imp.pRow()+0,136,"|");
  				deQtdTotal += rs.getDouble(4);
  				deVlrTotal += rs.getDouble(5);
  				
  			}
  			
  			rs.close();
  			ps.close();
  			if (!con.getAutoCommit())
  				con.commit();

  			// Fim da impressão do total por setor
  			
  			imp.say(imp.pRow()+1,0,""+imp.comprimido());
  			imp.say(imp.pRow()+0,1,"+"+Funcoes.replicate("-",133)+"+");
  			imp.say(imp.pRow()+1,0,""+imp.comprimido());
  			imp.say(imp.pRow()+0,1,"| TOTAL");
 			imp.say(imp.pRow()+0,68,"| "+Funcoes.strDecimalToStrCurrency(10,2,deQtdTotal+""));
  			imp.say(imp.pRow()+0,81,"| "+Funcoes.strDecimalToStrCurrency(15,2,deVlrTotal+"")+" |");
  			imp.say(imp.pRow()+0,136,"|");
            			
  			imp.say(imp.pRow()+1,0,""+imp.comprimido());
  			imp.say(imp.pRow()+0,1,"+"+Funcoes.replicate("-",133)+"+");

  			imp.eject();
  			imp.fechaGravacao();
  			
  		}
  		catch (SQLException e) {
  			Funcoes.mensagemErro(this,"Erro executando a consulta.\n"+e.getMessage());
  			e.printStackTrace();
  		}
  		if (bVisualizar) {
  			imp.preview(this);
  		}
  		else {
  			imp.print();
  		}
  	}
  	finally {
  		sWhere = null;
  		sSql = null;
  		sFrom = null;
  		sCodMarca = null; 
  		sCodGrup1 = null;
  		sCodGrup2 = null;
  		iCodSetor = 0;
  		iCodVend = 0;
  		iCodCli = 0;
  		sFiltros1 = null;
  		sFiltros2 = null;
  		sTipoMov = null;
  		imp = null;
  		ps = null;
  		rs = null;
  		deVlrTotal = 0;
  		deQtdTotal = 0;
  	}
  	
  }

  private void impCliente(boolean bVisualizar) {
  	String sSql = "";
  	String sWhere = "";
  	String sCodMarca = "";
  	String sCodGrup1 = "";
  	String sOrdemRel = "";
  	String sOrderBy = "";
  	String sDescOrdemRel = "";
  	String sCodGrup2 = "";
  	String sFiltros1 = "";
  	String sFiltros2 = "";
  	String sCodTipoCli = "";
  	String sDescTipoCli = "";
  	String sCodTipoCliAnt = "";
  	String sDescTipoCliAnt = "";
  	String sCab = "";
  	String sTipoMov = "";
  	
  	int iCodSetor = 0;
  	int iCodCli = 0;
  	int iCodVend = 0;
  	ImprimeOS imp = null;
  	int linPag = 0;
  	int iParam = 0;
  	PreparedStatement ps = null;
  	ResultSet rs = null;
  	double deVlrTotal = 0;
  	double deQtdTotal = 0;
  	double deVlrSubTotal = 0;
  	double deQtdSubTotal = 0;

  	try {
  		
  		imp = new ImprimeOS("",con);
  		linPag = imp.verifLinPag()-1;
  		imp.setTitulo("Relatorio de Vendas por Setor x Clientes");
  		sFiltros1 = "";
  		sFiltros2 = "";
		if (cbVendas.getVlrString().equals("S")) {
			sFiltros1 = "SOMENTE VENDAS";
			sTipoMov = "'VD'";
		}
		if (cbIncluiPed.getVlrString().equals("S")) {
			if (!sTipoMov.equals("")) {
			   sFiltros1 += (!sFiltros1.equals("")?" / ":"")+"INCLUI PEDIDOS";
			   sTipoMov += ",'PV'";
			}
		}
	    if (!sTipoMov.equals("")) {
	    	sTipoMov = " TM.TIPOMOV IN ("+sTipoMov+") AND ";
	    }

	    if (cbIncluiPed.getVlrString().equals("S")) {
			sFiltros1 += (!sFiltros1.equals("")?" / ":"")+"INCLUI PEDIDOS";
		}
		if (cbCliPrinc.getVlrString().equals("S")) {
			sFiltros2 += (!sFiltros2.equals("")?" / ":"")+"ADIC. CLIENTES PRINCIPAIS";
		}
  		sCodMarca = txtCodMarca.getVlrString().trim();
  		sCodGrup1 = txtCodGrup1.getVlrString().trim();
  		sCodGrup2 = txtCodGrup2.getVlrString().trim();
  		iCodSetor = txtCodSetor.getVlrInteger().intValue();
  		iCodVend = txtCodVend.getVlrInteger().intValue();
  		iCodCli = txtCodCli.getVlrInteger().intValue();
  		sOrdemRel = rgOrdemRel.getVlrString();
  		if (!sCodMarca.equals("")) { 
  			sWhere += "AND P.CODEMPMC=? AND P.CODFILIALMC=? AND P.CODMARCA=? ";
  			sFiltros1 += (!sFiltros1.equals("")?" / ":"")+"M.: "+txtDescMarca.getText().trim();
  		}
  		if (!sCodGrup1.equals("")) {
  			sWhere += "AND G.CODEMP=? AND G.CODFILIAL=? AND G.CODGRUP LIKE ? ";
  			sFiltros1 += (!sFiltros1.equals("")?" / ":"")+"G.: "+txtDescGrup1.getText().trim();
  		}
  		if (!sCodGrup2.equals("")) {
  			sWhere += "AND ( NOT P.CODGRUP=? ) ";
  			sFiltros1 += (!sFiltros1.equals("")?" / ":"")+" EXCL. G.: "+txtDescGrup2.getText().trim();
  		}
  		if (iCodSetor!=0) {
  			sWhere += "AND VD.CODSETOR=? ";
  			sFiltros2 += (!sFiltros2.equals("")?" / ":"")+" SETOR: "+iCodSetor+"-"+txtDescSetor.getVlrString().trim();
  		}
  		if (iCodVend!=0) {
  			sWhere += "AND V.CODVEND=? ";
  			sFiltros2 += (!sFiltros2.equals("")?" / ":"")+" REPR.: "+iCodVend+"-"+txtNomeVend.getVlrString().trim();
  		}
  		if (iCodCli!=0) {
  			sWhere += "AND C2.CODCLI=? ";
  			sFiltros2 += (!sFiltros2.equals("")?" / ":"")+" CLI.: "+iCodCli+"-"+Funcoes.copy(txtRazCli.getVlrString(),30);
  		}
  			
  		if (sOrdemRel.equals("V")) {
  		    sOrderBy = "1,2,6 desc";
  		    sDescOrdemRel = "Valor";
  		}
  		else if (sOrdemRel.equals("R")) {
  		    sOrderBy = "1,2,3,4";
  		    sDescOrdemRel = "Razão social";
  		}
  		else if (sOrdemRel.equals("C")) {
  		    sOrderBy = "1,2,4";
  		    sDescOrdemRel = "Código do cliente";
  		}
  		sSql = "SELECT C2.CODTIPOCLI,TI.DESCTIPOCLI,C2.RAZCLI,C2.CODCLI," +
 		"SUM(IV.QTDITVENDA) QTDVENDA, SUM(IV.VLRLIQITVENDA) VLRVENDA " +
		"FROM VDVENDA V, VDITVENDA IV, VDVENDEDOR VD, EQPRODUTO P, EQGRUPO G, " +
		"EQTIPOMOV TM, VDTIPOCLI TI, VDCLIENTE C, VDCLIENTE C2 "+
		"WHERE V.CODEMP=? AND V.CODFILIAL=? AND " +
		"V.DTEMITVENDA BETWEEN ? AND ? AND " +
		"V.CODEMPCL=C.CODEMP AND V.CODFILIALCL=C.CODFILIAL AND V.CODCLI=C.CODCLI AND "+
		(cbCliPrinc.getVlrString().equals("S")?
		   "C2.CODEMP=C.CODEMPPQ AND C2.CODFILIAL=C.CODFILIALPQ AND C2.CODCLI=C.CODPESQ AND ":
		   "C2.CODEMP=C.CODEMP AND C2.CODFILIAL=C.CODFILIAL AND C2.CODCLI=C.CODCLI AND ")+
		"TI.CODEMP=C2.CODEMPTI AND TI.CODFILIAL=C2.CODFILIALTI AND " +
		"TI.CODTIPOCLI=C2.CODTIPOCLI AND "+
		"IV.CODEMP=V.CODEMP AND IV.CODFILIAL=V.CODFILIAL AND "+
		"IV.CODVENDA=V.CODVENDA AND IV.TIPOVENDA=V.TIPOVENDA AND "+
		"VD.CODEMP=V.CODEMPVD AND VD.CODFILIAL=V.CODFILIALVD AND "+
		"VD.CODVEND=V.CODVEND AND VD.CODSETOR IS NOT NULL AND "+
		"P.CODEMP=IV.CODEMPPD AND P.CODFILIAL=IV.CODFILIALPD AND "+
		"P.CODPROD=IV.CODPROD AND G.CODEMP=P.CODEMPGP AND " +
		"G.CODFILIAL=P.CODFILIALGP AND "+
		"TM.CODEMP=V.CODEMPTM AND TM.CODFILIAL=V.CODFILIALTM AND " +
		"TM.CODTIPOMOV=V.CODTIPOMOV AND ( NOT SUBSTR(V.STATUSVENDA,1,1)='C' ) AND "+sTipoMov+
		(sCodGrup1.equals("")?"P.CODGRUP=G.CODGRUP ":"SUBSTR(P.CODGRUP,1,"+sCodGrup1.length()+")=G.CODGRUP ")+
		sWhere+
		"GROUP BY 1,2,3,4 "+
		"ORDER BY "+sOrderBy;
  		
 		//System.out.println(sSql);
  		
  		try {
  			ps = con.prepareStatement(sSql);
  			ps.setInt(1,Aplicativo.iCodEmp);
  			ps.setInt(2,ListaCampos.getMasterFilial("VDVENDA"));
  			ps.setDate(3,Funcoes.dateToSQLDate(txtDataini.getVlrDate()));
  			ps.setDate(4,Funcoes.dateToSQLDate(txtDatafim.getVlrDate()));
  			iParam = 5;
  			if (!sCodMarca.equals("")) {
  				ps.setInt(iParam,Aplicativo.iCodEmp);
  				ps.setInt(iParam+1,ListaCampos.getMasterFilial("EQMARCA"));
  				ps.setString(iParam+2,sCodMarca);
  				iParam += 3;
  			}
  			if (!sCodGrup1.equals("")) {
  				ps.setInt(iParam,Aplicativo.iCodEmp);
  				ps.setInt(iParam+1,ListaCampos.getMasterFilial("EQGRUPO"));
  				ps.setString(iParam+2,sCodGrup1+(sCodGrup1.length()<TAM_GRUPO?"%":""));
  				iParam += 3;
  			}
  			if (!sCodGrup2.equals("")) {
  				ps.setString(iParam,sCodGrup2);
  				iParam += 1;
  			}
  			if (iCodSetor!=0) {
  				ps.setInt(iParam,iCodSetor);
  				iParam += 1;
  			}
  			if (iCodVend!=0) {
  				ps.setInt(iParam,iCodVend);
  				iParam += 1;
  			}
  			if (iCodCli!=0) {
  				ps.setInt(iParam,iCodCli);
  				iParam += 1;
  			}
  			
  			rs = ps.executeQuery();
  			
  			imp.limpaPags();
  			
  			while ( rs.next() ) {
  				sCodTipoCli = rs.getString(1);
  				sDescTipoCli = rs.getString(2);
  				if (imp.pRow()>=(linPag-1)) {
  					imp.say(imp.pRow()+1,0,""+imp.comprimido());
  					imp.say(imp.pRow()+0,0,"+"+Funcoes.replicate("-",134)+"+");
  					imp.incPags();
  					imp.eject();
  				}
	  			if (imp.pRow()==0) {
	  				imp.say(imp.pRow()+1,0,""+imp.comprimido());
	  				imp.say(imp.pRow()+0,1,"+"+Funcoes.replicate("-",133)+"+");
	  				imp.say(imp.pRow()+1,0,""+imp.comprimido());
	  				imp.say(imp.pRow()+0,1,"| Emitido em :"+Funcoes.dateToStrDate(new Date()));
	  				imp.say(imp.pRow()+0,120,"Pagina : "+(imp.getNumPags()));
	  				imp.say(imp.pRow()+0,136,"|");
	  				imp.say(imp.pRow()+1,0,""+imp.comprimido());
	  				imp.say(imp.pRow()+0,1,"|");
	  				imp.say(imp.pRow()+0,55,"VENDAS POR SETOR X CLIENTE");
	  				imp.say(imp.pRow()+0,136,"|");
	  				if (!sFiltros1.equals("")) {
	  					imp.say(imp.pRow()+1,0,""+imp.comprimido());
	 					imp.say(imp.pRow()+0,1,"|");
  	 					imp.say(imp.pRow()+0,68-(sFiltros1.length()/2),sFiltros1);
  	 					imp.say(imp.pRow()+0,136,"|");
  	  				}
	  				if (!sFiltros2.equals("")) {
	  					imp.say(imp.pRow()+1,0,""+imp.comprimido());
	 					imp.say(imp.pRow()+0,1,"|");
  	 					imp.say(imp.pRow()+0,68-(sFiltros2.length()/2),sFiltros2);
  	 					imp.say(imp.pRow()+0,136,"|");
  	  				}
  	  				imp.say(imp.pRow()+1,0,""+imp.comprimido());
  	  				imp.say(imp.pRow()+0,1,"|");
  	  				imp.say(imp.pRow()+0,40,"PERIODO DE: "+txtDataini.getVlrString()+" ATE: "+
  	  				        txtDatafim.getVlrString()+" - Ordem: "+sDescOrdemRel);
  	  				imp.say(imp.pRow()+0,136,"|");
  	  				imp.say(imp.pRow()+1,0,""+imp.comprimido());
					imp.say(imp.pRow()+0,1,"+"+Funcoes.replicate("-",133)+"+");
					imp.say(imp.pRow()+1,0,""+imp.comprimido());
					imp.say(imp.pRow()+0,1,"| RAZAO SOCIAL");
					imp.say(imp.pRow()+0,55,"| CODIGO");
					imp.say(imp.pRow()+0,67,"| QUANTIDADE");
					imp.say(imp.pRow()+0,81,"|     VALOR");
					imp.say(imp.pRow()+0,99,"|");
					imp.say(imp.pRow()+0,136,"|");
					imp.say(imp.pRow()+1,0,""+imp.comprimido());
					imp.say(imp.pRow()+0,1,"+"+Funcoes.replicate("-",133)+"+");
				}
	  			if (!sCodTipoCli.equals(sCodTipoCliAnt)) {
	  				if (!sCodTipoCliAnt.equals("")) {
	  					sCab = "SUB-TOTAL "+sDescTipoCliAnt.trim()+":";
	  		  			imp.say(imp.pRow()+1,0,""+imp.comprimido());
	  		  			imp.say(imp.pRow()+0,1,"+"+Funcoes.replicate("-",133)+"+");
	  					imp.say(imp.pRow()+1,0,""+imp.comprimido());
	  					imp.say(imp.pRow()+0,1,"|");
						imp.say(imp.pRow()+0,3,sCab);
			 			imp.say(imp.pRow()+0,68,"| "+Funcoes.strDecimalToStrCurrency(10,2,deQtdSubTotal+""));
			  			imp.say(imp.pRow()+0,81,"| "+Funcoes.strDecimalToStrCurrency(15,2,deVlrSubTotal+"")+" |");
						imp.say(imp.pRow()+0,136,"|");
			  			imp.say(imp.pRow()+1,0,""+imp.comprimido());
			  			imp.say(imp.pRow()+0,1,"+"+Funcoes.replicate("-",133)+"+");
				  		deVlrSubTotal = 0;
				  		deQtdSubTotal = 0;
	  				}
  					sCab = sCodTipoCli+" - "+sDescTipoCli.trim(); 
  					imp.say(imp.pRow()+1,0,""+imp.comprimido());
  					imp.say(imp.pRow()+0,1,"|");
					imp.say(imp.pRow()+0,((136-sCab.length())/2),sCab);
					imp.say(imp.pRow()+0,136,"|");
		  			imp.say(imp.pRow()+1,0,""+imp.comprimido());
		  			imp.say(imp.pRow()+0,1,"+"+Funcoes.replicate("-",133)+"+");
	  			}
				
  				imp.say(imp.pRow()+1,0,""+imp.comprimido());
  				imp.say(imp.pRow()+0,1,"|");
  				imp.say(imp.pRow()+0,4,Funcoes.adicionaEspacos(rs.getString(3),50)+" |");
  				imp.say(imp.pRow()+0,56,Funcoes.adicEspacosEsquerda(rs.getString(4),10)+" |");
  				imp.say(imp.pRow()+0,70,Funcoes.adicEspacosEsquerda(rs.getDouble(5)+"",10)+" |");
  				imp.say(imp.pRow()+0,83,Funcoes.strDecimalToStrCurrency(15,2,rs.getString(6))+" |");
  				/*System.out.println(Funcoes.adicionaEspacos(rs.getString(3),50)+
  						"-"+Funcoes.adicEspacosEsquerda(rs.getString(4),10)+"-"+
						Funcoes.adicEspacosEsquerda(rs.getDouble(5)+"",10)+"-"+
						Funcoes.strDecimalToStrCurrency(15,2,rs.getString(6)));*/
  				imp.say(imp.pRow()+0,136,"|");
  				deQtdTotal += rs.getDouble(5);
  				deVlrTotal += rs.getDouble(6);
  				deQtdSubTotal += rs.getDouble(5);
  				deVlrSubTotal += rs.getDouble(6);
  				sCodTipoCliAnt = sCodTipoCli;
  				sDescTipoCliAnt = sDescTipoCli;
  			}
  			
  			rs.close();
  			ps.close();
  			if (!con.getAutoCommit())
  				con.commit();

			sCab = "SUB-TOTAL "+sDescTipoCliAnt.trim()+":";
  			imp.say(imp.pRow()+1,0,""+imp.comprimido());
  			imp.say(imp.pRow()+0,1,"+"+Funcoes.replicate("-",133)+"+");
			imp.say(imp.pRow()+1,0,""+imp.comprimido());
			imp.say(imp.pRow()+0,1,"|");
			imp.say(imp.pRow()+0,3,sCab);
	 		imp.say(imp.pRow()+0,68,"| "+Funcoes.strDecimalToStrCurrency(10,2,deQtdSubTotal+""));
	  		imp.say(imp.pRow()+0,81,"| "+Funcoes.strDecimalToStrCurrency(15,2,deVlrSubTotal+"")+" |");
			imp.say(imp.pRow()+0,136,"|");
  			
  			imp.say(imp.pRow()+1,0,""+imp.comprimido());
  			imp.say(imp.pRow()+0,1,"+"+Funcoes.replicate("-",133)+"+");
  			imp.say(imp.pRow()+1,0,""+imp.comprimido());
  			imp.say(imp.pRow()+0,1,"| TOTAL");
 			imp.say(imp.pRow()+0,68,"| "+Funcoes.strDecimalToStrCurrency(10,2,deQtdTotal+""));
  			imp.say(imp.pRow()+0,81,"| "+Funcoes.strDecimalToStrCurrency(15,2,deVlrTotal+"")+" |");
  			imp.say(imp.pRow()+0,136,"|");
            			
  			imp.say(imp.pRow()+1,0,""+imp.comprimido());
  			imp.say(imp.pRow()+0,1,"+"+Funcoes.replicate("-",133)+"+");

  			imp.eject();
  			imp.fechaGravacao();
  			
  		}
  		catch (SQLException e) {
  			Funcoes.mensagemErro(this,"Erro executando a consulta.\n"+e.getMessage());
  			e.printStackTrace();
  		}
  		if (bVisualizar) {
  			imp.preview(this);
  		}
  		else {
  			imp.print();
  		}
  	}
  	finally {
  		sWhere = null;
  		sSql = null;
  		sCodMarca = null; 
  		sCodGrup1 = null;
  		sCodGrup2 = null;
  		sOrdemRel = null;
  		sOrderBy = null;
  		sDescOrdemRel = null;
  		iCodSetor = 0;
  		iCodVend = 0;
  		iCodCli = 0;
  		sCodTipoCli = null;
  		sDescTipoCli = null;
  		sCodTipoCliAnt = null;
  		sDescTipoCliAnt = null;
  		sTipoMov = null;
  		sCab = null;
  		sFiltros1 = null;
  		sFiltros2 = null;
  		imp = null;
  		ps = null;
  		rs = null;
  		deVlrTotal = 0;
  		deQtdTotal = 0;
  		deVlrSubTotal = 0;
  		deQtdSubTotal = 0;
  	}
  	
  }
  
  private String getColSetor(Vector vCols) {
     String sRetorno = "";
     int iCols = 0;
     try { 
        for (int i=0; i<vCols.size(); i++) {
        	sRetorno += "| "+Funcoes.adicionaEspacos(vCols.elementAt(i).toString(),9);
        	iCols = i;
        }
        for (int i=iCols; i<8; i++ ) {
        	sRetorno += "|"+Funcoes.replicate(" ",10);
        }
        sRetorno += Funcoes.replicate(" ",103-sRetorno.length())+"| TOTAL";
     }
     finally {
     	vCols = null;
     }
     return sRetorno;
  }

  private String getTotSetor(Vector vTotSetor) {
    String sRetorno = "";
    double deTotal = 0;
    Double deTemp = null;
    int iCols = 0;
    try { 
       for (int i=0; i<vTotSetor.size(); i++) {
       	deTemp = (Double) vTotSetor.elementAt(i);
       	if (deTemp==null) 
       		deTemp = new Double(0);
       	deTotal += deTemp.doubleValue();
       	sRetorno += "|"+Funcoes.strDecimalToStrCurrency(10,2,deTemp.toString());
       	iCols = i;
       }
       for (int i=iCols; i<8; i++ ) {
       	sRetorno += "|"+Funcoes.replicate(" ",10);
       }
       sRetorno += Funcoes.replicate(" ",103-sRetorno.length())+"|"+Funcoes.strDecimalToStrCurrency(11,2,deTotal+"");
    }
    finally {
    	vTotSetor = null;
    	deTemp = null;
    	deTotal = 0;
    }
    return sRetorno;
 }
  
  private int posVendedor(String sCodVend, Vector vCols) {
  	 int iRetorno=-1;
  	 if (vCols!=null) {
  	 	for (int i=0; i<vCols.size(); i++) {
  	 		if (sCodVend.equals(vCols.elementAt(i).toString())) {
  	 			iRetorno = i;
  	 			break;
  	 		}
  	 	}
  	 }
  	 return iRetorno;
  }
  
  private Vector initTotSetor(Vector vCols) {
  	 Vector vRetorno = new Vector();
  	 for (int i=0; i<vCols.size();i++) {
  	 	vRetorno.addElement(new Double(0));
  	 }
  	 return vRetorno;
  }
  
  private Vector adicValorSetor(int iPos, Double deValor, Vector vTotSetor) {
  	 Double dlTemp = null; 
  	 double dTemp = 0;
  	 try {
  	 	if ( (vTotSetor!=null) && (deValor!=null) ) {
  	 		if (iPos<vTotSetor.size()) {
  	 			dlTemp = (Double) vTotSetor.elementAt(iPos);
  	 			if (dlTemp!=null) {
  	 				dTemp = dlTemp.doubleValue()+deValor.doubleValue();
  	 				dlTemp = new Double(dTemp);
  	 				vTotSetor.setElementAt(dlTemp,iPos);
  	 			}
  	 		}
  	 	}
  	 }
  	 finally {
  	 	dlTemp = null;
  	 	dTemp = 0;
  	 }
  	 return vTotSetor;
  }
  
  private Vector getVendedores(String sCodSetor, Vector vItens) {
  	Vector vRetorno = new Vector();
  	String sCodVend = "";
  	boolean bInicio = false;
  	try {
  		for (int i=0; i<vItens.size(); i++) {
  	   		if (((Vector) vItens.elementAt(i)).elementAt(POS_CODSETOR).toString().equals(sCodSetor)) {
  	   	   		bInicio = true;
  	   	   		sCodVend = new String(((Vector) vItens.elementAt(i)).elementAt(POS_CODVEND).toString());
  	   	   		if (vRetorno.indexOf(sCodVend)==-1)
  	   	   	   		vRetorno.addElement(sCodVend);
  	   		}
  	   		else {
  	   	   		if (bInicio) 
  	   	   	   		break;
  	   		}
  		}

  		vRetorno = Funcoes.ordenaVector(vRetorno,8);
  	}
  	finally {
  		sCodVend = null;
  	}
  	return vRetorno;
  }
  
  public void setConexao(Connection cn) {
    con = cn;
    lcMarca.setConexao(cn);
    lcGrup1.setConexao(cn);
    lcGrup2.setConexao(cn);
    lcSetor.setConexao(cn);
    lcVendedor.setConexao(cn);
    lcCliente.setConexao(cn);
  }
  
}
