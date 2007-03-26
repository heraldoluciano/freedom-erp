/**
 * @version 02/11/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FRReceber.java <BR>
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
import org.freedom.telas.AplicativoPD;
import org.freedom.telas.FRelatorio;

public class FRReceber extends FRelatorio {
	private static final long serialVersionUID = 1L;
	private JTextFieldPad txtDataini = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0); 
	private JTextFieldPad txtDatafim = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0); 
	private JTextFieldPad txtCodCli = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
	private JTextFieldFK txtRazCli = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
	private JTextFieldPad txtCodSetor = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
	private JTextFieldFK txtDescSetor = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
	private JTextFieldPad txtCodVend = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
	private JTextFieldFK txtNomeVend = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
	private JTextFieldPad txtCodBanco = new JTextFieldPad(JTextFieldPad.TP_STRING,3,0);
	private JTextFieldFK txtDescBanco = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
	private JTextFieldPad txtCodPlanoPag = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
	private JTextFieldFK txtDescPlanoPag = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
	private JCheckBoxPad cbObs = new JCheckBoxPad("Imprimir observações?","S","N");
	private JCheckBoxPad cbImpTotDia = new JCheckBoxPad("Imprimir totalizador diário?","S","N");  
	private JRadioGroup rgTipoRel = null;
	private JRadioGroup rgOrdem = null;
	private Vector vVals = new Vector();
	private Vector vLabs = new Vector();
	private Vector vVals1 = new Vector();
	private Vector vLabs1 = new Vector();
	private ListaCampos lcCli = new ListaCampos(this);
	private ListaCampos lcSetor = new ListaCampos(this);
	private ListaCampos lcVendedor = new ListaCampos(this);
	private ListaCampos lcBanco = new ListaCampos(this);
	private ListaCampos lcPlanoPag = new ListaCampos(this);
	private boolean[] bPref = null;
	  
	public FRReceber() {
		setTitulo("Contas a Receber");
		setAtribos(80,80,387,440);
		   
		txtDataini.setVlrDate(new Date());
		txtDatafim.setVlrDate(new Date());
		
		lcCli.add(new GuardaCampo( txtCodCli, "CodCli", "Cód.cli.", ListaCampos.DB_PK, false));
		lcCli.add(new GuardaCampo( txtRazCli, "RazCli", "Razão social do cliente", ListaCampos.DB_SI, false));
		lcCli.montaSql(false, "CLIENTE", "VD");
		lcCli.setReadOnly(true);
		txtCodCli.setTabelaExterna(lcCli);
		txtCodCli.setFK(true);
		txtCodCli.setNomeCampo("CodCli");
		
		lcSetor.add(new GuardaCampo( txtCodSetor,"CodSetor","Cód.setor", ListaCampos.DB_PK, false ));
		lcSetor.add(new GuardaCampo( txtDescSetor,"DescSetor","Descrição do setor", ListaCampos.DB_SI, false ));
		lcSetor.montaSql(false,"SETOR","VD");
		lcSetor.setReadOnly(true);
		txtCodSetor.setTabelaExterna(lcSetor);
		txtCodSetor.setFK(true);
		txtCodSetor.setNomeCampo("CodSetor");
		
		lcVendedor.add(new GuardaCampo( txtCodVend,"CodVend","Cód.comiss.", ListaCampos.DB_PK, false ));
		lcVendedor.add(new GuardaCampo( txtNomeVend,"NomeVend","Nome do comissionado", ListaCampos.DB_SI, false ));
		lcVendedor.montaSql(false,"VENDEDOR","VD");
		lcVendedor.setReadOnly(true);
		txtCodVend.setTabelaExterna(lcVendedor);
		txtCodVend.setFK(true);
		txtCodVend.setNomeCampo("CodVend");
		
		lcBanco.add(new GuardaCampo( txtCodBanco,"CodBanco","Cód.banco.", ListaCampos.DB_PK, false ));
		lcBanco.add(new GuardaCampo( txtDescBanco,"NomeBanco","Nome do banco", ListaCampos.DB_SI, false ));
		lcBanco.montaSql(false,"BANCO","FN");
		lcBanco.setReadOnly(true);
		txtCodBanco.setTabelaExterna(lcBanco);
		txtCodBanco.setFK(true);
		txtCodBanco.setNomeCampo("CodBanco");

		lcPlanoPag.add(new GuardaCampo( txtCodPlanoPag, "CodPlanoPag", "Cód.pl.pag.", ListaCampos.DB_PK, false));
		lcPlanoPag.add(new GuardaCampo( txtDescPlanoPag, "DescPlanoPag", "Descrição do plano de pagamento", ListaCampos.DB_SI, false));
		lcPlanoPag.montaSql(false, "PLANOPAG", "FN");
		lcPlanoPag.setReadOnly(true);
		txtCodPlanoPag.setTabelaExterna(lcPlanoPag);
		txtCodPlanoPag.setFK(true);
		txtCodPlanoPag.setNomeCampo("CodPlanoPag");
		
		vLabs.addElement("Contas a receber");
		vLabs.addElement("Contas recebidas");
		vLabs.addElement("Ambas as contas");
		vVals.addElement("R");
		vVals.addElement("P");
		vVals.addElement("A");

		rgTipoRel = new JRadioGroup(3,1,vLabs,vVals);

		vLabs1.addElement( "Emissão" );
		vLabs1.addElement( "Vencimento" );
		vLabs1.addElement( "Pagamento" );
		vVals1.addElement( "E" );
		vVals1.addElement( "V" );
		vVals1.addElement( "P" );

		rgOrdem = new JRadioGroup(3,1,vLabs1,vVals1);
		rgOrdem.setVlrString( "V" );
		
		cbObs.setVlrString("S");
		cbImpTotDia.setVlrString("S");
		
		JLabelPad lbLinha = new JLabelPad();
		lbLinha.setBorder(BorderFactory.createEtchedBorder());
		JLabelPad lbPeriodo = new JLabelPad("   Periodo:");
		lbPeriodo.setOpaque(true);

		adic(lbPeriodo,17,80,80,20);
		adic(lbLinha,7,90,353,40);
		adic(new JLabelPad("De:"),17,100,30,20);
		adic(txtDataini,50,100,97,20);
		adic(new JLabelPad("Até:"),157,100,30,20);
		adic(txtDatafim,190,100,100,20);
		adic(rgTipoRel,7,05,170,70);
		adic(rgOrdem,190,05,170,70);
		adic(new JLabelPad("Cód.cli."),7,130,200,20);
		adic(txtCodCli,7,150,80,20);
		adic(new JLabelPad("Razão social do cliente"),90,130,200,20);
		adic(txtRazCli,90,150,270,20);
		adic(new JLabelPad("Cód.setor"),7,170,250,20);
		adic(txtCodSetor,7,190,80,20);
		adic(new JLabelPad("Descrição do setor"),90,170,250,20);
		adic(txtDescSetor,90,190,270,20);
		adic(new JLabelPad("Cód.comis."),7,210,250,20);
		adic(txtCodVend,7,230,80,20);
		adic(new JLabelPad("Nome do comissionado"),90,210,250,20);
		adic(txtNomeVend,90,230,270,20);
		adic(new JLabelPad("Cód.banco"),7,250,250,20);
		adic(txtCodBanco,7,270,80,20);
		adic(new JLabelPad("Descrição do banco"),90,250,250,20);
		adic(txtDescBanco,90,270,270,20);
		adic(new JLabelPad("Cód.pl.pag."),7,290,80,20);
		adic(txtCodPlanoPag,7,310,80,20);
		adic(new JLabelPad("Descrição do plano de pagamento"),90,290,300,20);
		adic(txtDescPlanoPag,90,310,270,20);
		adic(cbObs,7,335,180,20);
		adic(cbImpTotDia,188,335,180,20);    
	
	}
	
	public void imprimir(boolean bVisualizar) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sSQL = null;
		String sWhere = "";
		String sFrom = " ";
		String sFiltro = "";
		String sTipoRel = null;
		String sOrdem = null;
		String sTitRel = null;
		String sTitRel1 = null;
		String sDtVencItRec = "";
		String sDtPago = "";
		String sObs = "";
		String sImpTotDia = "";
		String sCodBanco = null;
		String sCodPlanoPag = null;
		String sCampoOrdem = null;
		Vector vObs = null;
		ImprimeOS imp = null;
		int linPag = 0;
		int iCodCli = 0;
		int iCodSetor = 0;
		int iCodVend = 0;
		int iParans = 0;
		double deTotalDiaParc = 0;
		double deTotalDiaPago = 0;
		double deTotalDiaApag = 0;
		double deTotParc = 0;
		double deTotalPago = 0;
		double deTotalApag = 0;
		boolean bFimDia = false;
		
		try {
			if (txtDatafim.getVlrDate().before(txtDataini.getVlrDate())) {
				Funcoes.mensagemInforma(this,"Data final maior que a data inicial!");
				return;
			}
			imp = new ImprimeOS("",con);
			linPag = imp.verifLinPag()-1;
			sTipoRel = rgTipoRel.getVlrString();
			
			if (sTipoRel.equals("R"))
				sTitRel = "A RECEBER";
			else if (sTipoRel.equals("P"))
				sTitRel = "RECEBIDAS";
			else if (sTipoRel.equals("A"))
				sTitRel = "A RECEBER/RECEBIDAS";
			
			sOrdem = rgOrdem.getVlrString();
			
			if (sOrdem.equals("P")) {
				sTitRel1 = "PAGAMENTO";
				sCampoOrdem = "IT.DTPAGOITREC";
			}
			else if (sOrdem.equals("E")) {
				sTitRel1 = "EMISSÂO";
				sCampoOrdem = "IT.DTITREC";
			}
			else {
				sTitRel1 = "VENCIMENTO";
				sCampoOrdem = "IT.DTVENCITREC";
			}
			
			iCodCli = txtCodCli.getVlrInteger().intValue();
			iCodSetor = txtCodSetor.getVlrInteger().intValue();
			iCodVend = txtCodVend.getVlrInteger().intValue();
			sCodBanco = txtCodBanco.getVlrString();
			sCodPlanoPag = txtCodPlanoPag.getVlrString();
				  
			sObs = cbObs.getVlrString();
			sImpTotDia = cbImpTotDia.getVlrString();
			
			if (iCodCli!=0) {
				sWhere += " AND R.CODEMPCL=? AND R.CODFILIALCL=? AND R.CODCLI=? ";
				sFiltro = "Cli.: "+iCodCli+" - "+Funcoes.copy(txtRazCli.getVlrString(),30).trim();
			}
			if (iCodSetor!=0) {
				if (bPref[0])
					sWhere += " AND C.CODEMPSR=? AND C.CODFILIALSR=? AND C.CODSETOR=?";
				else {
					sWhere += " AND VD.CODEMPSE=? AND VD.CODFILIALSE=? AND VD.CODSETOR=? AND " +
					" VD.CODEMP=R.CODEMPVD AND VD.CODFILIAL=R.CODFILIALVD AND VD.CODVEND=R.CODVEND ";
					sFrom = ",VDVENDEDOR VD ";
				}
				sFiltro += (!sFiltro.equals("")?" / ":"")+"Setor: "+iCodSetor+" - "+Funcoes.copy(txtDescSetor.getVlrString(),30).trim();
			}
			if (iCodVend!=0) {
				sWhere += " AND R.CODEMPVD=? AND R.CODFILIALVD=? AND R.CODVEND=? ";
				sFiltro += (!sFiltro.equals("")?" / ":"")+"Repr.: "+iCodVend+" - "+Funcoes.copy(txtNomeVend.getVlrString(),30).trim();
			}
			if (sCodBanco.length()>0) {
				sWhere += " AND R.CODEMPBO=? AND R.CODFILIALBO=? AND R.CODBANCO=? ";
				sFiltro += (!sFiltro.equals("")?" / ":"")+"Repr.: "+sCodBanco+" - "+Funcoes.copy(txtCodBanco.getVlrString(),30).trim();
			}
			if (sCodPlanoPag.length()>0) {
				sWhere += " AND R.CODEMPPG=? AND R.CODFILIALPG=? AND R.CODPLANOPAG=? ";
				sFiltro += (!sFiltro.equals("")?" / ":"")+"Repr.: "+sCodPlanoPag+" - "+Funcoes.copy(txtCodPlanoPag.getVlrString(),30).trim();
			}
			
			sSQL =  "SELECT IT.DTVENCITREC,IT.NPARCITREC,R.CODVENDA,R.CODCLI,C.RAZCLI," +
					"IT.VLRPARCITREC,IT.VLRPAGOITREC,IT.VLRAPAGITREC,IT.DTPAGOITREC,R.DOCREC,IT.OBSITREC,"+
					"(SELECT V.STATUSVENDA FROM VDVENDA V "+
						"WHERE V.FLAG IN "+AplicativoPD.carregaFiltro(con,org.freedom.telas.Aplicativo.iCodEmp)+
						" AND V.CODEMP=R.CODEMPVA AND V.CODFILIAL=R.CODFILIALVA AND V.CODVENDA=R.CODVENDA AND V.TIPOVENDA=R.TIPOVENDA) " +
					"FROM FNITRECEBER IT,FNRECEBER R,VDCLIENTE C" + sFrom +
					"WHERE R.FLAG IN "+ AplicativoPD.carregaFiltro(con,org.freedom.telas.Aplicativo.iCodEmp)+
					"AND R.CODEMP=? AND R.CODFILIAL=? AND "+sCampoOrdem+" BETWEEN ? AND ? "+
					"AND IT.STATUSITREC IN (?,?,?) AND R.CODREC = IT.CODREC " +
					"AND IT.CODEMP=R.CODEMP AND IT.CODFILIAL=R.CODFILIAL " +
					"AND C.CODEMP = R.CODEMPCL AND C.CODFILIAL=R.CODFILIALCL AND C.CODCLI=R.CODCLI "+
					sWhere +
					" ORDER BY "+sCampoOrdem+",C.RAZCLI";
			          
			try {
				iParans = 1;
				ps = con.prepareStatement(sSQL);
				ps.setInt(iParans++,Aplicativo.iCodEmp);
				ps.setInt(iParans++,ListaCampos.getMasterFilial("FNRECEBER"));
				ps.setDate(iParans++,Funcoes.dateToSQLDate(txtDataini.getVlrDate()));
				ps.setDate(iParans++,Funcoes.dateToSQLDate(txtDatafim.getVlrDate()));
				
				if (sTipoRel.equals("R")) {
					ps.setString(iParans++,"R1");
					ps.setString(iParans++,"RL");
					ps.setString(iParans++,"RL");
				} else if (sTipoRel.equals("P")) {
					ps.setString(iParans++,"RP");
					ps.setString(iParans++,"RL");
					ps.setString(iParans++,"RL");
				} else if (sTipoRel.equals("A")) {
					ps.setString(iParans++,"R1");
					ps.setString(iParans++,"RL");
					ps.setString(iParans++,"RP");
				}
				
				if (iCodCli!=0) {
					ps.setInt(iParans++,Aplicativo.iCodEmp);
					ps.setInt(iParans++,ListaCampos.getMasterFilial("VDCLIENTE"));
					ps.setInt(iParans++,iCodCli);
				}
				if (iCodSetor!=0) {
					ps.setInt(iParans++,Aplicativo.iCodEmp);
					ps.setInt(iParans++,ListaCampos.getMasterFilial("VDSETOR"));
					ps.setInt(iParans++,iCodSetor);
				}
				if (iCodVend!=0) {
					ps.setInt(iParans++,Aplicativo.iCodEmp);
					ps.setInt(iParans++,ListaCampos.getMasterFilial("VDVENDEDOR"));
					ps.setInt(iParans++,iCodVend);
				}
				if (sCodBanco.length()>0) {
					ps.setInt(iParans++,Aplicativo.iCodEmp);
					ps.setInt(iParans++,ListaCampos.getMasterFilial("FNBANCO"));
					ps.setString(iParans++,sCodBanco);
				}
				if (sCodPlanoPag.length()>0) {
					ps.setInt(iParans++,Aplicativo.iCodEmp);
					ps.setInt(iParans++,ListaCampos.getMasterFilial("FNPLANOPAG"));
					ps.setString(iParans++,sCodPlanoPag);
				}
				
				rs = ps.executeQuery();
				imp.limpaPags();
				imp.montaCab();
				imp.setTitulo("Relatório de contas "+sTitRel);
				imp.addSubTitulo("RELATORIO DE CONTAS "+sTitRel+" - PERIODO DE :"+txtDataini.getVlrString()+" ATE: "+txtDatafim.getVlrString()+" POR: "+sTitRel1 );  				
				while ( rs.next() ) {
					if (imp.pRow()>=(linPag-1)) {
						imp.pulaLinha( 1, imp.comprimido() );
						imp.say(  0, "+" + Funcoes.replicate("-",133) + "+");
						imp.incPags();
						imp.eject();
					} 
					
					if (imp.pRow()==0) {  					
						imp.impCab(136, true);						
						imp.say(  0, imp.comprimido());
						imp.say(  0, "|" + Funcoes.replicate("-",133) + "|");
						imp.pulaLinha( 1, imp.comprimido() );
						imp.say(  0, "| Vencto.    |");
						imp.say( 15, " Cliente                                  |");
						imp.say( 59, " Doc.      |");
						imp.say( 72, " Vlr. da Parc. |");
						imp.say( 89, " Vlr Recebido  |");
						imp.say(106, " Vlr Aberto   |");
						imp.say(122, " Data Receb. |");
						imp.pulaLinha( 1, imp.comprimido() );
						imp.say(  0, "|" + Funcoes.replicate("-",133) + "|");
					}
					
					if ((!Funcoes.sqlDateToStrDate(rs.getDate("DtVencItRec")).equals(sDtVencItRec)) && 
								(bFimDia) && (sImpTotDia.equals("S")) ) {
						imp.pulaLinha( 1, imp.comprimido() );
						imp.say(  0, "|" + Funcoes.replicate("-",133) + "|");
						imp.pulaLinha( 1, imp.comprimido() );
						imp.say(  0, "|");
						imp.say( 41, "Totais do Dia-> | "+sDtVencItRec+" | "+
											Funcoes.strDecimalToStrCurrency(14,2,String.valueOf(deTotalDiaParc))+" | "+
											Funcoes.strDecimalToStrCurrency(14,2,String.valueOf(deTotalDiaPago))+" | "+
											Funcoes.strDecimalToStrCurrency(13,2,String.valueOf(deTotalDiaApag))+" | ");
						imp.say(135, "|");
						imp.pulaLinha( 1, imp.comprimido() );
						imp.say( 0, "|" + Funcoes.replicate("-",133) + "|");
						deTotalDiaParc = 0;
						deTotalDiaPago = 0;
						deTotalDiaApag = 0;
						bFimDia = false;
					}

					imp.pulaLinha( 1, imp.comprimido() );
					imp.say(  0, "|");
					
					if (!Funcoes.sqlDateToStrDate(rs.getDate("DtVencItRec")).equals(sDtVencItRec))
						imp.say(  3, Funcoes.sqlDateToStrDate(rs.getDate("DtVencItRec")) );
					
					imp.say( 14, "| " + Funcoes.copy(rs.getString("CodCli"),0,6) + "-" + Funcoes.copy(rs.getString("RazCli"),0,33)+  " |" );
					
					if (rs.getString("DtPagoItRec") != null)    
						sDtPago = Funcoes.sqlDateToStrDate(rs.getDate("DtPagoItRec"));
					else 
						sDtPago = " "; 
					
					sDtPago = Funcoes.copy(sDtPago,0,10);
					
					imp.say( 61, (Funcoes.copy(rs.getString(10),0,1).equals("P") ? 
									    Funcoes.copy(rs.getString("CodVenda"),0,6) : 
										Funcoes.copy(rs.getString("DocRec"),0,6))+"/"+Funcoes.copy(rs.getString("NParcItRec"),0,2)+"| "+
										Funcoes.strDecimalToStrCurrency(14,2,rs.getString("VlrParcItRec"))+" | "+
										Funcoes.strDecimalToStrCurrency(14,2,rs.getString("VlrPagoItRec"))+" | "+
										Funcoes.strDecimalToStrCurrency(13,2,rs.getString("VlrApagItRec"))+" | "+
										" "+sDtPago+"  |");
					if (sObs.equals("S")) {
						if (rs.getString("OBSITREC")!=null) {
							vObs = getObs(rs.getString("OBSITREC"),108);
							for (int i=0; i<vObs.size(); i++) {
								imp.pulaLinha( 1, imp.comprimido() );
								imp.say(  0, "|");
								imp.say( 16, (i==0?"OBS.: ":"      ")+vObs.elementAt(i).toString());
								imp.say(135, "|");
							}
						}
					}
					if (rs.getString("VlrParcItRec") != null) {
						deTotalDiaParc += rs.getDouble("VlrParcItRec");
						deTotParc += rs.getDouble("VlrParcItRec");
					}
					if (rs.getString("VlrPagoItRec") != null) {
						deTotalDiaPago += rs.getDouble("VlrPagoItRec");
						deTotalPago += rs.getDouble("VlrPagoItRec");
					}					
					if (rs.getString("VlrApagItRec") != null) {
						deTotalDiaApag += rs.getDouble("VlrApagItRec");
						deTotalApag += rs.getDouble("VlrApagItRec");
					}
					 
					bFimDia = true;
					sDtVencItRec = Funcoes.sqlDateToStrDate(rs.getDate("DtVencItRec"));
				}
				
				if ((bFimDia) && (sImpTotDia.equals("S"))) {
					imp.pulaLinha( 1, imp.comprimido() );
					imp.say(  0, "|" + Funcoes.replicate("-",133) + "|");
					imp.pulaLinha( 1, imp.comprimido() );
					imp.say(  0, "|");
					imp.say( 41, "Totais do Dia-> | "+sDtVencItRec+" | "+
										Funcoes.strDecimalToStrCurrency(14,2,String.valueOf(deTotalDiaParc))+" | "+
										Funcoes.strDecimalToStrCurrency(14,2,String.valueOf(deTotalDiaPago))+" | "+
										Funcoes.strDecimalToStrCurrency(13,2,String.valueOf(deTotalDiaApag)));
					imp.say(135, "|");
				}

				imp.pulaLinha( 1, imp.comprimido() );
				imp.say(  0, "|" + Funcoes.replicate("=",133) + "|");
				imp.pulaLinha( 1, imp.comprimido() );
				imp.say(  0, "|");
				imp.say( 55, "Totais Geral-> | "+
									Funcoes.strDecimalToStrCurrency(14,2,String.valueOf(deTotParc))+" | "+
									Funcoes.strDecimalToStrCurrency(14,2,String.valueOf(deTotalPago))+" | "+
									Funcoes.strDecimalToStrCurrency(13,2,String.valueOf(deTotalApag)));
				imp.say(135, "|");
				imp.pulaLinha( 1, imp.comprimido() );
				imp.say( 0, "+" + Funcoes.replicate("=",133) + "+");
				 
				imp.eject();  
				imp.fechaGravacao();
				
				rs.close();
				ps.close();
				if (!con.getAutoCommit())
					con.commit();
				
			} catch ( SQLException err ) {
				Funcoes.mensagemErro(this,"Erro consulta tabela de contas a receber!\n"+err.getMessage(),true,con,err);
				err.printStackTrace();
			}
			
			if (bVisualizar)
				imp.preview(this);
			else
				imp.print();
		} finally {
			imp = null;
			linPag = 0;
			iCodCli = 0;
			iParans = 0;
			iCodSetor = 0;
			iCodVend = 0;
			bFimDia = false;
			sFiltro = null;
			sTipoRel = null;
			sTitRel = null;
			sDtVencItRec = null;
			sDtPago = null;
			sSQL = null;
			sObs = null;
			sImpTotDia = null;
			sWhere = null;
			sFrom = null;
			sCodBanco = null;
			sCodPlanoPag = null;
			ps = null;
			rs = null;
			deTotalDiaParc = 0;
			deTotalDiaPago = 0;
			deTotalDiaApag = 0;
			deTotParc = 0;
			deTotalPago = 0;
			deTotalApag = 0;
			vObs = null;
			System.gc();
		}
	}
	
	private Vector getObs(String sObs, int iTam) {
		Vector vRetorno = null;
		boolean bFim = false;
		try {
			sObs = sObs.replaceAll(((char)13)+""," ");
			sObs = sObs.replaceAll(((char)10)+"","");
			sObs = sObs.trim();
			vRetorno = new Vector();
			if (!sObs.equals("")) {
				while (!bFim) {
					if (sObs.length()<=iTam) {
						vRetorno.addElement(sObs);
						bFim = true;
					} else {
						vRetorno.addElement(sObs.substring(0,iTam));
						sObs = sObs.substring(iTam);
					}
				}
			}
		} finally {
			sObs = null;
			bFim = false;
		}
		return vRetorno;
	}
	
	private boolean[] getPrefere() {
		boolean[] bRet = new boolean[1];
		String sSQL = null;
		String sVal = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			bRet[0] = false;
			sSQL = "SELECT SETORVENDA FROM SGPREFERE1 WHERE CODEMP=? AND CODFILIAL=?";
			try {
				ps = con.prepareStatement(sSQL);
				ps.setInt(1,Aplicativo.iCodEmp);
				ps.setInt(2,Aplicativo.iCodFilial);
				rs = ps.executeQuery();
				if (rs.next()) {
					sVal = rs.getString("SetorVenda");
					if (sVal!= null)
						if ("CA".indexOf(sVal) >= 0) //Se tiver C ou A no sVal!
							bRet[0] = true;
				}
				rs.close();
				ps.close();
				if (!con.getAutoCommit())
					con.commit();
			} catch(SQLException err) {
				Funcoes.mensagemErro(null,"Erro ao verificar preferências!\n"+err.getMessage(),true,con,err);
				err.printStackTrace();
			}
		} finally {
			sSQL = null;
			ps = null;
			rs = null;
			sVal = null;
		}
		return bRet;
	}

	public void setConexao(Connection cn) {
		super.setConexao(cn);
		lcCli.setConexao(cn);
		lcSetor.setConexao(cn);
		lcVendedor.setConexao(cn);
		lcBanco.setConexao(cn);
		lcPlanoPag.setConexao(cn);
		bPref = getPrefere();
	}  

}
