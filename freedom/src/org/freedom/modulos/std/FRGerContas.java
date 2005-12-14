/**
 * @version 24/03/2004 <BR>
 * @author Setpoint Informática Ltda./Anderson Sanchez <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FRGerContas.java <BR>
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
 * Tela de opções para o relatório de gerenciamento de contas.
 * 
 */

package org.freedom.modulos.std;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Vector;
import net.sf.jasperreports.engine.JasperPrintManager;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FPrinterJob;
import org.freedom.telas.FRelatorio;

public class FRGerContas extends FRelatorio  {

  private static final long serialVersionUID = 1L;
  private final int TAM_GRUPO = 14;
  private JTextFieldPad txtAno = new JTextFieldPad(JTextFieldPad.TP_INTEGER,4,0);
  private JTextFieldPad txtCodVend = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldFK txtNomeVend = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
  private JTextFieldPad txtCodGrup1 = new JTextFieldPad(JTextFieldPad.TP_STRING, TAM_GRUPO, 0);
  private JTextFieldFK txtDescGrup1 = new JTextFieldFK(JTextFieldPad.TP_STRING, 40, 0);
  private JTextFieldPad txtCodGrup2 = new JTextFieldPad(JTextFieldPad.TP_STRING, TAM_GRUPO, 0);
  private JTextFieldFK txtDescGrup2 = new JTextFieldFK(JTextFieldPad.TP_STRING, 40, 0);
  private JTextFieldPad txtCodMarca = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldFK txtDescMarca = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldPad txtPercFat = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JLabelPad lbAno = new JLabelPad("Ano");
  private JLabelPad lbPercFat = new JLabelPad("% de relevância");
  private JLabelPad lbCodVend = new JLabelPad("Cód.comiss.");
  private JLabelPad lbDescVend = new JLabelPad("Nome do comissionado");
  private JLabelPad lbCodGrup1 = new JLabelPad("Cód.grupo/somar");
  private JLabelPad lbDescCodGrup1 = new JLabelPad("Descrição do grupo/somar");
  private JLabelPad lbCodGrup2 = new JLabelPad("Cód.grupo/subtrair");
  private JLabelPad lbDescCodGrup2 = new JLabelPad("Descrição do grupo/subtrair");
  private JLabelPad lbCodMarca = new JLabelPad("Cód.marca");
  private JLabelPad lbDescMarc = new JLabelPad("Descrição da marca");
  private JCheckBoxPad cbVendas = new JCheckBoxPad("Só vendas?","S","N");
  private JCheckBoxPad cbCliPrinc = new JCheckBoxPad("Mostrar no cliente principal?","S","N");
  private JCheckBoxPad cbIncluiPed = new JCheckBoxPad("Incluir pedidos não faturados?","S","N");
  private JRadioGroup rgOrdemRel = null;
  private JRadioGroup rgOrdemRel2 = null;
  private Vector vLabOrdemRel = new Vector();
  private Vector vValOrdemRel = new Vector();
  private ListaCampos lcGrup1 = new ListaCampos(this);
  private ListaCampos lcGrup2 = new ListaCampos(this);
  private ListaCampos lcVendedor = new ListaCampos(this);
  private ListaCampos lcMarca = new ListaCampos(this);
  private final int JAN = 0;
  private final int FEV = 1;
  private final int MAR = 2;
  private final int ABR = 3;
  private final int MAI = 4;
  private final int JUN = 5;
  private final int JUL = 6;  
  private final int AGO = 7;
  private final int SET = 8;
  private final int OUT = 9;
  private final int NOV = 10;
  private final int DEZ = 11;
  private double dbVendasGeral = 0.00;

  
  public FRGerContas() {
    setTitulo("Gerenciamento de contas");
    setAtribos(80,0,550,350);

    txtAno.setRequerido(true);
    txtAno.setVlrInteger(new Integer((new GregorianCalendar()).get(Calendar.YEAR)));

    cbVendas.setVlrString("S");
    cbCliPrinc.setVlrString("S");
    cbIncluiPed.setVlrString("N");        

    vLabOrdemRel.addElement("Cód.cli.");
    vLabOrdemRel.addElement("Razão");    
    vLabOrdemRel.addElement("Cidade");
    vLabOrdemRel.addElement("Categoria");
    vLabOrdemRel.addElement("Classific.");
    vLabOrdemRel.addElement("Valor");
    
    vValOrdemRel.addElement("C");    
    vValOrdemRel.addElement("R");
    vValOrdemRel.addElement("D");
    vValOrdemRel.addElement("T");
    vValOrdemRel.addElement("S");
    vValOrdemRel.addElement("V");
    
    rgOrdemRel = new JRadioGroup(3,2,vLabOrdemRel,vValOrdemRel);
    rgOrdemRel.setVlrString("C");
    
    rgOrdemRel2 = new JRadioGroup(3,2,vLabOrdemRel,vValOrdemRel);
    rgOrdemRel2.setVlrString("R");
	lcGrup1.add(new GuardaCampo(txtCodGrup1, "CodGrup", "Cód.grupo",ListaCampos.DB_PK, false));
	lcGrup1.add(new GuardaCampo(txtDescGrup1, "DescGrup","Descrição do gurpo", ListaCampos.DB_SI, false));
	lcGrup1.montaSql(false, "GRUPO", "EQ");
	lcGrup1.setReadOnly(true);
	txtCodGrup1.setTabelaExterna(lcGrup1);
	txtCodGrup1.setFK(true);
	txtCodGrup1.setNomeCampo("CodGrup");

	lcGrup2.add(new GuardaCampo(txtCodGrup2, "CodGrup", "Cód.grupo",ListaCampos.DB_PK, false));
	lcGrup2.add(new GuardaCampo(txtDescGrup2, "DescGrup","Descrição do grupo", ListaCampos.DB_SI, false));
	lcGrup2.montaSql(false, "GRUPO", "EQ");
	lcGrup2.setReadOnly(true);
	txtCodGrup2.setTabelaExterna(lcGrup2);
	txtCodGrup2.setFK(true);
	txtCodGrup2.setNomeCampo("CodGrup");

	lcVendedor.add(new GuardaCampo( txtCodVend, "CodVend","Cód.comiss.", ListaCampos.DB_PK, false ));
    lcVendedor.add(new GuardaCampo( txtNomeVend, "NomeVend","Nome do comissionado", ListaCampos.DB_SI, false ));
    lcVendedor.montaSql(false,"VENDEDOR","VD");
    lcVendedor.setReadOnly(true);
    txtCodVend.setTabelaExterna(lcVendedor);
    txtCodVend.setFK(true);
    txtCodVend.setNomeCampo("CodVend");
    
    lcMarca.add(new GuardaCampo( txtCodMarca, "CodMarca","Cód.marca", ListaCampos.DB_PK, false ));
    lcMarca.add(new GuardaCampo( txtDescMarca, "DescMarca","Descrição da marca", ListaCampos.DB_SI, false ));
    lcMarca.montaSql(false,"MARCA","EQ");
    lcMarca.setReadOnly(true);
    txtCodMarca.setTabelaExterna(lcMarca);
    txtCodMarca.setFK(true);
    txtCodMarca.setNomeCampo("CodMarca");
    

    adic(lbAno,7,0,100,20);
    adic(txtAno,7,20,100,20);
    adic(lbPercFat,7,50,100,20);
    adic(txtPercFat,7,70,100,20);
    
    adic(new JLabelPad("1º Ordem"),120,0,80,20);
    adic(rgOrdemRel,120,20,200,70);
    adic(new JLabelPad("2° Ordem"),325,0,80,20);
    adic(rgOrdemRel2,325,20,200,70);    
    
    adic(lbCodVend,7,100,110,20);
    adic(txtCodVend,7,120,110,20);
    adic(lbDescVend,120,100,200,20);
    adic(txtNomeVend,120,120,200,20);    
	adic(lbCodGrup1, 7, 140, 110, 20);
	adic(txtCodGrup1, 7, 160, 110, 20);
	adic(lbDescCodGrup1, 120, 140, 200, 20);
	adic(txtDescGrup1, 120, 160, 200, 20);	
	adic(lbCodGrup2, 7, 180, 110, 20);
	adic(txtCodGrup2, 7, 200, 110, 20);
	adic(lbDescCodGrup2, 120, 180, 200, 20);
	adic(txtDescGrup2, 120, 200, 200, 20);	
	adic(lbCodMarca, 7, 220, 110, 20);
	adic(txtCodMarca, 7, 240, 110, 20);
	adic(lbDescMarc, 120, 220, 200, 20);
	adic(txtDescMarca, 120, 240, 200, 20);
	
    adic(cbVendas,330,120,100,20);
    adic(cbCliPrinc,330,150,250,20);
    adic(cbIncluiPed,330,180,295,20);    
    
  }

	private ResultSet rodaQuery() {
		String sSql = "";
		String sWhere = "";
		String sWhereCli = "";
		String sWhereTM = "";
		String sCodGrup1 = "";
		String sOrdemRel = "";
		String sOrdemRel2 = "";
		String sOrderBy = "";
		String sCodGrup2 = "";
		String sFiltros1 = "";
		String sFiltros2 = "";
		double dbPercRel = 0.00;
		int iCodCli = 0;
		int iCodVend = 0;
		int iCodMarca = 0;
		int iParam = 1;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			sFiltros1 = "";
			sFiltros2 = "";
			sWhereTM += " AND TM.SOMAVDTIPOMOV='S' " ;

			dbPercRel = txtPercFat.getVlrDouble().doubleValue();
			
			
			if(dbPercRel>0){
				
			}
				
						
			if (cbCliPrinc.getVlrString().equals("S")) {
				sFiltros2 += (!sFiltros2.equals("") ? " / " : "")+ "ADIC. CLIENTES PRINCIPAIS";
			}
			
			sCodGrup1 = txtCodGrup1.getVlrString().trim();
			sCodGrup2 = txtCodGrup2.getVlrString().trim();
			iCodVend = txtCodVend.getVlrInteger().intValue();
			iCodMarca = txtCodMarca.getVlrInteger().intValue();
			sOrdemRel = rgOrdemRel.getVlrString();
			sOrdemRel2 = rgOrdemRel2.getVlrString();

			if (!sCodGrup1.equals("")) {
				sWhere += "AND G.CODEMP=? AND G.CODFILIAL=? AND G.CODGRUP LIKE ? ";
				sFiltros1 += (!sFiltros1.equals("") ? " / " : "") + "G.: "
						+ txtDescGrup1.getText().trim();
			}
			if (!sCodGrup2.equals("")) {
				sWhere += "AND ( NOT P.CODGRUP=? ) ";
				sFiltros1 += (!sFiltros1.equals("") ? " / " : "")
						+ " EXCL. G.: " + txtDescGrup2.getText().trim();
			}

			if(iCodMarca !=0){
				sWhere += " AND P.CODEMPMC="+lcMarca.getCodEmp()+" AND P.CODFILIALMC="+lcMarca.getCodFilial()+
				" AND P.CODMARCA="+txtCodMarca.getVlrString();
				
			}
			
			if (iCodVend != 0) {
				sWhere += " AND V.CODVEND=? ";
				sWhereCli = " AND C.CODVEND=? ";
				sFiltros2 += (!sFiltros2.equals("") ? " / " : "") + " REPR.: "
						+ iCodVend + "-" + txtNomeVend.getVlrString().trim();
			}

			vValOrdemRel.addElement("C");
		    vValOrdemRel.addElement("R");	    
		    vValOrdemRel.addElement("D");
		    vValOrdemRel.addElement("T");
		    vValOrdemRel.addElement("S");
			vValOrdemRel.addElement("V");
			
			if (sOrdemRel.equals("V")) {
				sOrderBy = "18";
			} 
			else if (sOrdemRel.equals("R")) {
				sOrderBy = "2";
			} 
			else if (sOrdemRel.equals("C")) {
				sOrderBy = "1";
			}
			else if (sOrdemRel.equals("D")) {
				sOrderBy = "3";
			}
			else if (sOrdemRel.equals("T")) {
				sOrderBy = "4";
			}
			else if (sOrdemRel.equals("S")) {
				sOrderBy = "5";
			}

			if ((sOrdemRel2.equals("V")) & (!sOrdemRel2.equals(sOrdemRel))) {
				sOrderBy = sOrderBy+",18";
			} 
			else if ((sOrdemRel2.equals("R")) & (!sOrdemRel2.equals(sOrdemRel))) {
				sOrderBy = sOrderBy+",2";
			} 
			else if ((sOrdemRel2.equals("C")) & (!sOrdemRel2.equals(sOrdemRel))) {
				sOrderBy = sOrderBy+",1";
			}
			else if ((sOrdemRel2.equals("D")) & (!sOrdemRel2.equals(sOrdemRel))) {
				sOrderBy = sOrderBy+",3";
			}
			else if ((sOrdemRel2.equals("T")) & (!sOrdemRel2.equals(sOrdemRel)))  {
				sOrderBy = sOrderBy+",4";
			}
			else if ((sOrdemRel2.equals("S")) & (!sOrdemRel2.equals(sOrdemRel))) {
				sOrderBy = sOrderBy+",5";
			}

			

			int iAno = txtAno.getVlrInteger().intValue();
			
			java.sql.Date dtIniJan = Funcoes.dateToSQLDate(Funcoes.getDataIniMes(JAN,iAno));
			java.sql.Date dtIniFev = Funcoes.dateToSQLDate(Funcoes.getDataIniMes(FEV,iAno));
			java.sql.Date dtIniMar = Funcoes.dateToSQLDate(Funcoes.getDataIniMes(MAR,iAno));
			java.sql.Date dtIniAbr = Funcoes.dateToSQLDate(Funcoes.getDataIniMes(ABR,iAno));
			java.sql.Date dtIniMai = Funcoes.dateToSQLDate(Funcoes.getDataIniMes(MAI,iAno));
			java.sql.Date dtIniJun = Funcoes.dateToSQLDate(Funcoes.getDataIniMes(JUN,iAno));
			java.sql.Date dtIniJul = Funcoes.dateToSQLDate(Funcoes.getDataIniMes(JUL,iAno));
			java.sql.Date dtIniAgo = Funcoes.dateToSQLDate(Funcoes.getDataIniMes(AGO,iAno));
			java.sql.Date dtIniSet = Funcoes.dateToSQLDate(Funcoes.getDataIniMes(SET,iAno));
			java.sql.Date dtIniOut = Funcoes.dateToSQLDate(Funcoes.getDataIniMes(OUT,iAno));
			java.sql.Date dtIniNov = Funcoes.dateToSQLDate(Funcoes.getDataIniMes(NOV,iAno));
			java.sql.Date dtIniDez = Funcoes.dateToSQLDate(Funcoes.getDataIniMes(DEZ,iAno));	
			
			java.sql.Date dtFimJan = Funcoes.dateToSQLDate(Funcoes.getDataFimMes(JAN,iAno));
			java.sql.Date dtFimFev = Funcoes.dateToSQLDate(Funcoes.getDataFimMes(FEV,iAno));
			java.sql.Date dtFimMar = Funcoes.dateToSQLDate(Funcoes.getDataFimMes(MAR,iAno));
			java.sql.Date dtFimAbr = Funcoes.dateToSQLDate(Funcoes.getDataFimMes(ABR,iAno));
			java.sql.Date dtFimMai = Funcoes.dateToSQLDate(Funcoes.getDataFimMes(MAI,iAno));
			java.sql.Date dtFimJun = Funcoes.dateToSQLDate(Funcoes.getDataFimMes(JUN,iAno));
			java.sql.Date dtFimJul = Funcoes.dateToSQLDate(Funcoes.getDataFimMes(JUL,iAno));
			java.sql.Date dtFimAgo = Funcoes.dateToSQLDate(Funcoes.getDataFimMes(AGO,iAno));
			java.sql.Date dtFimSet = Funcoes.dateToSQLDate(Funcoes.getDataFimMes(SET,iAno));
			java.sql.Date dtFimOut = Funcoes.dateToSQLDate(Funcoes.getDataFimMes(OUT,iAno));
			java.sql.Date dtFimNov = Funcoes.dateToSQLDate(Funcoes.getDataFimMes(NOV,iAno));
			java.sql.Date dtFimDez = Funcoes.dateToSQLDate(Funcoes.getDataFimMes(DEZ,iAno));
				
			if(dbPercRel>0.00){
			
				String sSqlGeral =  "SELECT SUM(COALESCE(IV.VLRLIQITVENDA,0)) AS VENDASTOTAL "
					  +"FROM VDVENDA V, VDITVENDA IV,EQPRODUTO P,EQGRUPO G,EQTIPOMOV TM "
					  +"WHERE V.CODEMP=? AND V.CODFILIAL=? AND V.DTEMITVENDA BETWEEN ? AND ? "
					  +"AND  IV.CODEMP=V.CODEMP AND IV.CODFILIAL=V.CODFILIAL AND IV.CODVENDA=V.CODVENDA AND IV.TIPOVENDA=V.TIPOVENDA "
					  +"AND P.CODEMP=IV.CODEMPPD AND P.CODFILIAL=IV.CODFILIALPD AND P.CODPROD=IV.CODPROD "
					  +"AND G.CODEMP=P.codEMPGp AND G.CODFILIAL=P.codfilialgp "
					  +"AND TM.CODEMP=V.CODEMPTM  AND TM.CODFILIAL=V.CODFILIALTM AND TM.CODTIPOMOV=V.CODTIPOMOV "
					  +"AND ( NOT SUBSTR(V.STATUSVENDA,1,1)='C' ) "
					  + sWhereTM + 
						 (sCodGrup1.equals("") ? " AND P.CODGRUP=G.CODGRUP " : " AND SUBSTR(P.CODGRUP,1," + sCodGrup1.length() + ")=G.CODGRUP ")						  
					  	+ sWhere;
					
				
				try {
					System.out.println(sSqlGeral);
					ps = con.prepareStatement(sSqlGeral);								
					ps.setInt(iParam++, Aplicativo.iCodEmp);
					ps.setInt(iParam++, ListaCampos.getMasterFilial("VDVENDA"));
					ps.setDate(iParam++,dtIniJan);
					ps.setDate(iParam++,dtFimDez);
	
					if (!sCodGrup1.equals("")) {
						ps.setInt(iParam++, Aplicativo.iCodEmp);
						ps.setInt(iParam++, ListaCampos.getMasterFilial("EQGRUPO"));
						ps.setString(iParam++, sCodGrup1 + (sCodGrup1.length() < TAM_GRUPO ? "%" : ""));						
					}
					if (!sCodGrup2.equals("")) {
						ps.setString(iParam++, sCodGrup2);
					}
					if (iCodVend != 0) {
						ps.setInt(iParam++, iCodVend);					
					}
					if (iCodCli != 0) {
						ps.setInt(iParam++, iCodCli);
					}
	
					rs = ps.executeQuery();
					
					if(rs.next()){
						dbVendasGeral = rs.getDouble(1);
						dbPercRel = dbPercRel/100;
	 
						System.out.println("VALOR TOTAL DAS VENDAS:"+(dbVendasGeral));
						dbVendasGeral = dbVendasGeral*dbPercRel;
						System.out.println("VALOR A FILTRAR:"+dbVendasGeral);
						
					}
					rs = null;
				}
				catch (Exception err) {
					Funcoes.mensagemErro(this, "Erro consultando total de vendas.\n"+ err.getMessage(),true,con,err);
					err.printStackTrace();
				}
				finally{
					iParam = 1;
				}
			}
			
			sSql = "SELECT C2.CODCLI,C2.RAZCLI,C2.CIDCLI,TI.SIGLATIPOCLI,CLA.SIGLACLASCLI,"
			   	 + "  SUM((SELECT (CAST((COALESCE(SUM(1),0)) AS INTEGER)) FROM TKHISTORICO HIS " +
			   	 		"WHERE HIS.CODEMPCL=C.CODEMP AND HIS.CODFILIALCL=C.CODFILIAL AND " +
			   	 		"HIS.CODCLI=C.CODCLI AND HIS.TIPOHISTTK='V' AND HIS.DATAHISTTK BETWEEN ? AND ? )) AS JAN, "
			   	 + "  SUM((SELECT (CAST((COALESCE(SUM(1),0)) AS INTEGER)) FROM TKHISTORICO HIS " +
			   	 		"WHERE HIS.CODEMPCL=C.CODEMP AND HIS.CODFILIALCL=C.CODFILIAL AND " +
			   	 		"HIS.CODCLI=C.CODCLI AND HIS.TIPOHISTTK='V' AND HIS.DATAHISTTK BETWEEN ? AND ? )) AS FEV, "
				 + "  SUM((SELECT (CAST((COALESCE(SUM(1),0)) AS INTEGER)) FROM TKHISTORICO HIS " +
				 		"WHERE HIS.CODEMPCL=C.CODEMP AND HIS.CODFILIALCL=C.CODFILIAL AND " +
				 		"HIS.CODCLI=C.CODCLI AND HIS.TIPOHISTTK='V' AND HIS.DATAHISTTK BETWEEN ? AND ? )) AS MAR, "
				 + "  SUM((SELECT (CAST((COALESCE(SUM(1),0)) AS INTEGER)) FROM TKHISTORICO HIS " +
				 		"WHERE HIS.CODEMPCL=C.CODEMP AND HIS.CODFILIALCL=C.CODFILIAL AND " +
				 		"HIS.CODCLI=C.CODCLI AND HIS.TIPOHISTTK='V' AND HIS.DATAHISTTK BETWEEN ? AND ? )) AS ABR, "
				 + "  SUM((SELECT (CAST((COALESCE(SUM(1),0)) AS INTEGER)) FROM TKHISTORICO HIS " +
				 		"WHERE HIS.CODEMPCL=C.CODEMP AND HIS.CODFILIALCL=C.CODFILIAL AND " +
				 		"HIS.CODCLI=C.CODCLI AND HIS.TIPOHISTTK='V' AND HIS.DATAHISTTK BETWEEN ? AND ? )) AS MAI, "
				 + "  SUM((SELECT (CAST((COALESCE(SUM(1),0)) AS INTEGER)) FROM TKHISTORICO HIS " +
				 		"WHERE HIS.CODEMPCL=C.CODEMP AND HIS.CODFILIALCL=C.CODFILIAL AND " +
				 		"HIS.CODCLI=C.CODCLI AND HIS.TIPOHISTTK='V' AND HIS.DATAHISTTK BETWEEN ? AND ? )) AS JUN, "
				 + "  SUM((SELECT (CAST((COALESCE(SUM(1),0)) AS INTEGER)) FROM TKHISTORICO HIS " +
				 		"WHERE HIS.CODEMPCL=C.CODEMP AND HIS.CODFILIALCL=C.CODFILIAL AND " +
				 		"HIS.CODCLI=C.CODCLI AND HIS.TIPOHISTTK='V' AND HIS.DATAHISTTK BETWEEN ? AND ? )) AS JUL, "
				 + "  SUM((SELECT (CAST((COALESCE(SUM(1),0)) AS INTEGER)) FROM TKHISTORICO HIS " +
				 		"WHERE HIS.CODEMPCL=C.CODEMP AND HIS.CODFILIALCL=C.CODFILIAL AND " +
				 		"HIS.CODCLI=C.CODCLI AND HIS.TIPOHISTTK='V' AND HIS.DATAHISTTK BETWEEN ? AND ? )) AS AGO, "
				 + "  SUM((SELECT (CAST((COALESCE(SUM(1),0)) AS INTEGER)) FROM TKHISTORICO HIS " +
				 		"WHERE HIS.CODEMPCL=C.CODEMP AND HIS.CODFILIALCL=C.CODFILIAL AND " +
				 		"HIS.CODCLI=C.CODCLI AND HIS.TIPOHISTTK='V' AND HIS.DATAHISTTK BETWEEN ? AND ? )) AS SETE, "
				 + "  SUM((SELECT (CAST((COALESCE(SUM(1),0)) AS INTEGER)) FROM TKHISTORICO HIS " +
				 		"WHERE HIS.CODEMPCL=C.CODEMP AND HIS.CODFILIALCL=C.CODFILIAL AND " +
				 		"HIS.CODCLI=C.CODCLI AND HIS.TIPOHISTTK='V' AND HIS.DATAHISTTK BETWEEN ? AND ? )) AS OUT, "
				 + "  SUM((SELECT (CAST((COALESCE(SUM(1),0)) AS INTEGER)) FROM TKHISTORICO HIS " +
				 		"WHERE HIS.CODEMPCL=C.CODEMP AND HIS.CODFILIALCL=C.CODFILIAL AND " +
				 		"HIS.CODCLI=C.CODCLI AND HIS.TIPOHISTTK='V' AND HIS.DATAHISTTK BETWEEN ? AND ? )) AS NOV, "
				 + "  SUM((SELECT (CAST((COALESCE(SUM(1),0)) AS INTEGER)) FROM TKHISTORICO HIS " +
				 		"WHERE HIS.CODEMPCL=C.CODEMP AND HIS.CODFILIALCL=C.CODFILIAL AND " +
				 		"HIS.CODCLI=C.CODCLI AND HIS.TIPOHISTTK='V' AND HIS.DATAHISTTK BETWEEN ? AND ? )) AS DEZ, "				

				 //Vendas no ano
				 				 
				  +" SUM((SELECT SUM(COALESCE(IV.VLRLIQITVENDA,0))"
				  +" FROM VDVENDA V, VDITVENDA IV, VDVENDEDOR VD, EQPRODUTO P, EQGRUPO G,EQTIPOMOV TM"
				  +" WHERE V.CODEMP=? AND V.CODFILIAL=? AND V.DTEMITVENDA BETWEEN ? AND ?"
				  +" AND V.CODEMPCL=C.CODEMP AND V.CODFILIALCL=C.CODFILIAL AND V.CODCLI=C.CODCLI"
				  +" AND C2.CODEMP=C.CODEMPPQ AND C2.CODFILIAL=C.CODFILIALPQ AND C2.CODCLI=C.CODPESQ"
				  +" AND  IV.CODEMP=V.CODEMP AND IV.CODFILIAL=V.CODFILIAL AND IV.CODVENDA=V.CODVENDA AND IV.TIPOVENDA=V.TIPOVENDA"
				  +" AND VD.CODEMP=V.CODEMPVD AND VD.CODFILIAL=V.CODFILIALVD AND VD.CODVEND=V.CODVEND AND VD.CODSETOR IS NOT NULL AND P.CODEMP=IV.CODEMPPD"
				  +" AND P.CODFILIAL=IV.CODFILIALPD AND P.CODPROD=IV.CODPROD AND G.CODEMP=P.CODEMPGP AND G.CODFILIAL=P.CODFILIALGP "
				  +" AND TM.CODEMP=V.CODEMPTM  AND TM.CODFILIAL=V.CODFILIALTM AND TM.CODTIPOMOV=V.CODTIPOMOV AND ( NOT SUBSTR(V.STATUSVENDA,1,1)='C' )  AND TM.SOMAVDTIPOMOV='S'"
					+ sWhereTM  
					+ (sCodGrup1.equals("") ? " AND P.CODGRUP=G.CODGRUP " : " AND SUBSTR(P.CODGRUP,1," + sCodGrup1.length() + ")=G.CODGRUP ")						  
				  		+ sWhere +")) AS VENDASATUAL,"

				  //Vendas no ano anterior 2
				  		
				  +" SUM((SELECT SUM(COALESCE(IV.VLRLIQITVENDA,0))"
				  +" FROM VDVENDA V, VDITVENDA IV, VDVENDEDOR VD, EQPRODUTO P, EQGRUPO G,EQTIPOMOV TM"
				  +" WHERE V.CODEMP=? AND V.CODFILIAL=? AND V.DTEMITVENDA BETWEEN ? AND ?"
				  +" AND V.CODEMPCL=C.CODEMP AND V.CODFILIALCL=C.CODFILIAL AND V.CODCLI=C.CODCLI"
				  +" AND C2.CODEMP=C.CODEMPPQ AND C2.CODFILIAL=C.CODFILIALPQ AND C2.CODCLI=C.CODPESQ"
				  +" AND  IV.CODEMP=V.CODEMP AND IV.CODFILIAL=V.CODFILIAL AND IV.CODVENDA=V.CODVENDA AND IV.TIPOVENDA=V.TIPOVENDA"
				  +" AND VD.CODEMP=V.CODEMPVD AND VD.CODFILIAL=V.CODFILIALVD AND VD.CODVEND=V.CODVEND AND VD.CODSETOR IS NOT NULL AND P.CODEMP=IV.CODEMPPD"
				  +" AND P.CODFILIAL=IV.CODFILIALPD AND P.CODPROD=IV.CODPROD AND G.CODEMP=P.CODEMPGP AND G.CODFILIAL=P.CODFILIALGP "
				  +" AND TM.CODEMP=V.CODEMPTM  AND TM.CODFILIAL=V.CODFILIALTM AND TM.CODTIPOMOV=V.CODTIPOMOV AND ( NOT SUBSTR(V.STATUSVENDA,1,1)='C' ) AND TM.SOMAVDTIPOMOV='S'"
				  + sWhereTM + 
					 (sCodGrup1.equals("") ? " AND P.CODGRUP=G.CODGRUP " : " AND SUBSTR(P.CODGRUP,1," + sCodGrup1.length() + ")=G.CODGRUP ")						  
					 + sWhere +")) AS VENDASANTERIOR2,"				  		
				  						  		
				 //Vendas no ano anterior
					
				  +" SUM((SELECT SUM(COALESCE(IV.VLRLIQITVENDA,0))"
				  +" FROM VDVENDA V, VDITVENDA IV, VDVENDEDOR VD, EQPRODUTO P, EQGRUPO G,EQTIPOMOV TM "
				  +" WHERE V.CODEMP=? AND V.CODFILIAL=? AND V.DTEMITVENDA BETWEEN ? AND ?"
				  +" AND V.CODEMPCL=C.CODEMP AND V.CODFILIALCL=C.CODFILIAL AND V.CODCLI=C.CODCLI"
				  +" AND C2.CODEMP=C.CODEMPPQ AND C2.CODFILIAL=C.CODFILIALPQ AND C2.CODCLI=C.CODPESQ"
				  +" AND  IV.CODEMP=V.CODEMP AND IV.CODFILIAL=V.CODFILIAL AND IV.CODVENDA=V.CODVENDA AND IV.TIPOVENDA=V.TIPOVENDA"
				  +" AND VD.CODEMP=V.CODEMPVD AND VD.CODFILIAL=V.CODFILIALVD AND VD.CODVEND=V.CODVEND AND VD.CODSETOR IS NOT NULL AND P.CODEMP=IV.CODEMPPD"
				  +" AND P.CODFILIAL=IV.CODFILIALPD AND P.CODPROD=IV.CODPROD AND G.CODEMP=P.CODEMPGP AND G.CODFILIAL=P.CODFILIALGP "
				  +" AND TM.CODEMP=V.CODEMPTM  AND TM.CODFILIAL=V.CODFILIALTM AND TM.CODTIPOMOV=V.CODTIPOMOV AND ( NOT SUBSTR(V.STATUSVENDA,1,1)='C' )  AND TM.SOMAVDTIPOMOV='S'"
				  + sWhereTM + 
					 (sCodGrup1.equals("") ? " AND P.CODGRUP=G.CODGRUP " : " AND SUBSTR(P.CODGRUP,1," + sCodGrup1.length() + ")=G.CODGRUP ")						  
				  	+ sWhere +")) AS VENDASANTERIOR,"
					  			  					  	
				  //meta estimada para ano corrente					
				  +" SUM((SELECT COALESCE(CM.VLRMETAVEND,0) FROM VDCLIMETAVEND CM WHERE CM.CODEMP=C.CODEMP AND" 
				  +" CM.CODFILIAL=C.CODFILIAL AND CM.CODCLI=C.CODCLI AND" 
				  +" ANOMETAVEND=("+txtAno.getVlrInteger()+"))) AS VENDASMETA"					
					
 				  //From principal
					
				  + " FROM VDTIPOCLI TI, VDCLIENTE C, VDCLIENTE C2, VDCLASCLI CLA "

 				  //Where principal
					
				  + " WHERE CLA.CODEMP=C2.CODEMPCC AND CLA.CODFILIAL=C2.CODFILIALCC AND CLA.CODCLASCLI=C2.CODCLASCLI AND "					
				  + (cbCliPrinc.getVlrString().equals("S") ? "C2.CODEMP=C.CODEMPPQ AND C2.CODFILIAL=C.CODFILIALPQ AND C2.CODCLI=C.CODPESQ AND "
							: "C2.CODEMP=C.CODEMP AND C2.CODFILIAL=C.CODFILIAL AND C2.CODCLI=C.CODCLI AND ")
				  + "TI.CODEMP=C2.CODEMPTI AND TI.CODFILIAL=C2.CODFILIALTI AND "
				  + "TI.CODTIPOCLI=C2.CODTIPOCLI "+sWhereCli 
				  
  			  + " GROUP BY 1,2,3,4,5 " + "ORDER BY " + sOrderBy;
												 
			try {
				ps = con.prepareStatement(sSql);								
				ps.setDate(iParam++,dtIniJan);
				ps.setDate(iParam++,dtFimJan);
				ps.setDate(iParam++,dtIniFev);
				ps.setDate(iParam++,dtFimFev);
				ps.setDate(iParam++,dtIniMar);
				ps.setDate(iParam++,dtFimMar);
				ps.setDate(iParam++,dtIniAbr);
				ps.setDate(iParam++,dtFimAbr);
				ps.setDate(iParam++,dtIniMai);
				ps.setDate(iParam++,dtFimMai);
				ps.setDate(iParam++,dtIniJun);
				ps.setDate(iParam++,dtFimJun);
				ps.setDate(iParam++,dtIniJul);
				ps.setDate(iParam++,dtFimJul);
				ps.setDate(iParam++,dtIniAgo);
				ps.setDate(iParam++,dtFimAgo);
				ps.setDate(iParam++,dtIniSet);
				ps.setDate(iParam++,dtFimSet);
				ps.setDate(iParam++,dtIniOut);
				ps.setDate(iParam++,dtFimOut);
				ps.setDate(iParam++,dtIniNov);
				ps.setDate(iParam++,dtFimNov);
				ps.setDate(iParam++,dtIniDez);
				ps.setDate(iParam++,dtFimDez);
				
				ps.setInt(iParam++, Aplicativo.iCodEmp);
				ps.setInt(iParam++, ListaCampos.getMasterFilial("VDVENDA"));

				ps.setDate(iParam++,Funcoes.dateToSQLDate(Funcoes.getDataIniMes(JAN,iAno)));
				ps.setDate(iParam++,Funcoes.dateToSQLDate(Funcoes.getDataFimMes(DEZ,iAno)));										
				
				if (!sCodGrup1.equals("")) {
					ps.setInt(iParam, Aplicativo.iCodEmp);
					ps.setInt(iParam + 1, ListaCampos.getMasterFilial("EQGRUPO"));
					ps.setString(iParam + 2, sCodGrup1 + (sCodGrup1.length() < TAM_GRUPO ? "%" : ""));
					iParam += 3;
				}
				if (!sCodGrup2.equals("")) {
					ps.setString(iParam, sCodGrup2);
					iParam += 1;
				}
				if (iCodVend != 0) {
					ps.setInt(iParam, iCodVend);
					iParam += 1;
				}
				if (iCodCli != 0) {
					ps.setInt(iParam, iCodCli);
					iParam += 1;
				}

				ps.setInt(iParam++, Aplicativo.iCodEmp);
				ps.setInt(iParam++, ListaCampos.getMasterFilial("VDVENDA"));				
				ps.setDate(iParam++,Funcoes.dateToSQLDate(Funcoes.getDataIniMes(JAN,iAno-2)));
				ps.setDate(iParam++,Funcoes.dateToSQLDate(Funcoes.getDataFimMes(DEZ,iAno-2)));										

				if (!sCodGrup1.equals("")) {
					ps.setInt(iParam, Aplicativo.iCodEmp);
					ps.setInt(iParam + 1, ListaCampos.getMasterFilial("EQGRUPO"));
					ps.setString(iParam + 2, sCodGrup1 + (sCodGrup1.length() < TAM_GRUPO ? "%" : ""));
					iParam += 3;
				}
				if (!sCodGrup2.equals("")) {
					ps.setString(iParam, sCodGrup2);
					iParam += 1;
				}

				if (iCodVend != 0) {
					ps.setInt(iParam, iCodVend);
					iParam += 1;
				}
				if (iCodCli != 0) {
					ps.setInt(iParam, iCodCli);
					iParam += 1;
				}
				
				ps.setInt(iParam++, Aplicativo.iCodEmp);
				ps.setInt(iParam++, ListaCampos.getMasterFilial("VDVENDA"));				
				ps.setDate(iParam++,Funcoes.dateToSQLDate(Funcoes.getDataIniMes(JAN,iAno-1)));
				ps.setDate(iParam++,Funcoes.dateToSQLDate(Funcoes.getDataFimMes(DEZ,iAno-1)));										
					
				if (!sCodGrup1.equals("")) {
					ps.setInt(iParam, Aplicativo.iCodEmp);
					ps.setInt(iParam + 1, ListaCampos.getMasterFilial("EQGRUPO"));
					ps.setString(iParam + 2, sCodGrup1 + (sCodGrup1.length() < TAM_GRUPO ? "%" : ""));
					iParam += 3;
				}
				if (!sCodGrup2.equals("")) {
					ps.setString(iParam, sCodGrup2);
					iParam += 1;
				}
				if (iCodVend != 0) {
					ps.setInt(iParam, iCodVend);
					iParam += 1;
				}
				if (iCodCli != 0) {
					ps.setInt(iParam, iCodCli);
					iParam += 1;
				}
				if (iCodVend != 0) {
					ps.setInt(iParam, iCodVend);
					iParam += 1;
				}
								
				rs = ps.executeQuery();
			} 
			catch (SQLException err) {
				Funcoes.mensagemErro(this, "Erro executando a consulta.\n"+ err.getMessage(),true,con,err);
				err.printStackTrace();
			}		
			
		} 
		finally {
			sWhere = null;
			sSql = null;
			sCodGrup1 = null;
			sCodGrup2 = null;
			sOrdemRel = null;
			sOrderBy = null;
			iCodVend = 0;
			iCodCli = 0;
			sWhereTM = null;
			sFiltros1 = null;
			sFiltros2 = null;
			ps = null;
		}
		return rs;
	}
	
  public void imprimir(boolean bVisualizar) {	    
	FPrinterJob dlGr = null;
	HashMap hParam = new HashMap();
	hParam.put("ANO",txtAno.getVlrInteger());
	hParam.put("CODVEND",txtCodVend.getVlrInteger());
	hParam.put("VLRRELEV",new Double(dbVendasGeral)); 
	
	System.out.println("Vai filtrar valor < que:"+dbVendasGeral);
	dlGr = new FPrinterJob("relatorios/gercontas.jasper","Gerenciamento de contas","",rodaQuery(),hParam,this);	
						
	if(bVisualizar)
		dlGr.setVisible(true);  
	else{			
		try {
			JasperPrintManager.printReport(dlGr.getRelatorio(),true);
		}
		catch(Exception err){
			Funcoes.mensagemErro(this,"Erro na impressão de relatório de gerenciamento de contas!"+err.getMessage(),true,con,err);
		}
	}
  }

  public void setConexao(Connection cn) {
    super.setConexao(cn);
    lcVendedor.setConexao(cn);
    lcMarca.setConexao(cn);
    lcGrup1.setConexao(cn);
    lcGrup2.setConexao(cn);    
  }
}
