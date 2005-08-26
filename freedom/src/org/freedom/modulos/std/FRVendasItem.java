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
	
	private JCheckBoxPad cbFaturados = new JCheckBoxPad("Faturados?", "S", "N");
	private JCheckBoxPad cbFinanceiro = new JCheckBoxPad("Financeiro?", "S", "N");
	private JCheckBoxPad cbListaFilial = null; 
	
	private ListaCampos lcVend = new ListaCampos(this);
	private ListaCampos lcGrup = new ListaCampos(this);
	private ListaCampos lcCliente = new ListaCampos(this);
	private ListaCampos lcMarca = new ListaCampos(this);
	private JRadioGroup rgOrdem = null;
	private Vector vLabs = new Vector(2);
	private Vector vVals = new Vector(2);
	public FRVendasItem() {
		setTitulo("Vendas por Item");
		setAtribos(80,80,305,460);
		
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
				
		 
		 cbListaFilial = new JCheckBoxPad("Listar vendas das filiais ?", "S", "N");
		 cbListaFilial.setVlrString("N");
		 cbFaturados.setVlrString("N");
		 cbFinanceiro.setVlrString("N");
		 
		 
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
				
		adic(cbFaturados, 7, 240, 150, 25);
		adic(cbFinanceiro, 153, 240, 150, 25);		
		adic(cbListaFilial, 5, 280, 250, 20 );
		
		adic(lbLinha3,7,314,272,2);
        adic(new JLabelPad("Ordenado por:"),7,330,180,20);
        adic(rgOrdem,7,355,273,30);
       
        
        
        
	}

	public void imprimir(boolean bVisualizar) {
		if (txtDatafim.getVlrDate().before(txtDataini.getVlrDate())) {
			Funcoes.mensagemInforma(this,"Data final maior que a data inicial!");
			return;
		}
		ImprimeOS imp = new ImprimeOS("",con);
		int linPag = imp.verifLinPag()-1;
		
		String sWhere = "";
		String sCab = "";
		String sOrdem = rgOrdem.getVlrString();
		String sOrdenado = "";
		String sCodRel = "";
		String sSQL="";
		String sDataini = txtDataini.getVlrString();
		String sDatafim = txtDatafim.getVlrString();
		double dQtd = 0;
		double dVlr = 0;
		
		

                if (comRef())
                  sCodRel = "REFPROD";
                else
                  sCodRel = "CODPROD";

                if (sOrdem.equals("C")) {
                        sOrdem = "P."+sCodRel;
                        sOrdenado = "ORDENADO POR "+(sCodRel.equals("CODPROD") ? "CODIGO" : "REFERENCIA");
                }
                else {
                        sOrdem = "P.DESCPROD";
                        sOrdenado = "ORDENADO POR DESCRICAO";
                }
                sOrdenado = "|"+Funcoes.replicate(" ",67-(sOrdenado.length()/2))+sOrdenado;
                sOrdenado += Funcoes.replicate(" ",133-sOrdenado.length())+" |";
		if (txtCodVend.getText().trim().length() > 0) {
			sWhere += " AND V.CODVEND = "+txtCodVend.getText().trim();
			String sTmp = "REPR.: "+txtDescVend.getText().trim();
			sCab += "\n"+imp.comprimido();
			sTmp = "|"+Funcoes.replicate(" ",67-(sTmp.length()/2))+sTmp;
			sCab += sTmp+Funcoes.replicate(" ",133-sTmp.length())+" |";
		}
		if (txtCodGrup.getText().trim().length() > 0) {
			sWhere += " AND P.CODGRUP LIKE '"+txtCodGrup.getText().trim()+"%'";
			String sTmp = "GRUPO: "+txtDescGrup.getText().trim();
			sCab += "\n"+imp.comprimido();
			sTmp = "|"+Funcoes.replicate(" ",67-(sTmp.length()/2))+sTmp;
			sCab += sTmp+Funcoes.replicate(" ",133-sTmp.length())+" |";
		}
		if (txtCodMarca.getText().trim().length() > 0) {
			sWhere += " AND P.CODMARCA = '"+txtCodMarca.getText().trim()+"'";
			String sTmp = "MARCA: "+txtDescMarca.getText().trim();
			sCab += "\n"+imp.comprimido();
			sTmp = "|"+Funcoes.replicate(" ",67-(sTmp.length()/2))+sTmp;
			sCab += sTmp+Funcoes.replicate(" ",133-sTmp.length())+" |";
		}
		if (txtCodCli.getText().trim().length() > 0) {
			if (cbListaFilial.getVlrString().equals("S"))
			  sWhere += " AND (C.CODPESQ = "+txtCodCli.getText().trim()+
			  		    " OR C.CODCLI="+txtCodCli.getText().trim()+")";
			else 
			  sWhere += " AND V.CODCLI = "+txtCodCli.getText().trim();
		    String sTmp = "CLIENTE: "+txtRazCli.getText().trim();
			sCab += "\n"+imp.comprimido();
			sTmp = "|"+Funcoes.replicate(" ",67-(sTmp.length()/2))+sTmp;
			sCab += sTmp+Funcoes.replicate(" ",133-sTmp.length())+" |";
		}
		if (cbFaturados.getVlrString().equals("S")) {
		    String sTmp =  "SÓ FATURADOS";
			sCab += "\n"+imp.comprimido();
			sTmp = "|"+Funcoes.replicate(" ",67-(sTmp.length()/2))+sTmp;
			sCab += sTmp+Funcoes.replicate(" ",133-sTmp.length())+" |";
		}
		if (cbFinanceiro.getVlrString().equals("S")) {
		    String sTmp =  "SÓ FINANCEIRO";
			sCab += "\n"+imp.comprimido();
			sTmp = "|"+Funcoes.replicate(" ",67-(sTmp.length()/2))+sTmp;
			sCab += sTmp+Funcoes.replicate(" ",133-sTmp.length())+" |";
		}
		
		
		/**/
		if (cbListaFilial.getVlrString().equals("S")&& (txtCodCli.getText().trim().length() > 0) ){  
		     sSQL = "SELECT P."+sCodRel+",P.DESCPROD,"+
		        "P.CODUNID,SUM(IT.QTDITVENDA),"+
		        "SUM(IT.VLRLIQITVENDA) FROM VDVENDA V,EQTIPOMOV TM, VDCLIENTE C, "+
		        "VDITVENDA IT, EQPRODUTO P WHERE P.CODPROD = IT.CODPROD"+
		        " AND IT.CODVENDA = V.CODVENDA"+
		        " AND TM.CODTIPOMOV=V.CODTIPOMOV"+
				(cbFaturados.getVlrString().equals("S") ? " AND TM.FISCALTIPOMOV='S' " : "")+
				(cbFinanceiro.getVlrString().equals("S") ? " AND TM.SOMAVDTIPOMOV='S' " : "")+
		        " AND TM.CODEMP=V.CODEMPTM"+
		        " AND TM.CODFILIAL=V.CODFILIALTM"+
		        " AND V.CODCLI=C.CODCLI AND V.CODEMPCL=C.CODEMP "+
		        " AND V.CODFILIALCL=C.CODFILIAL"+
		        " AND TM.TIPOMOV IN ('VD','PV','VT','SE')"+
		        " AND V.DTEMITVENDA BETWEEN"+
		        " ? AND ? "+sWhere+" AND V.FLAG IN "+
		        Aplicativo.carregaFiltro(con,org.freedom.telas.Aplicativo.iCodEmp)+
		        "AND NOT SUBSTR(V.STATUSVENDA,1,1)='C' GROUP BY P."+sCodRel+","+
		        "P.DESCPROD,P.CODUNID ORDER BY "+sOrdem;
 		 }
	 
		 else {
						
			 sSQL = "SELECT P."+sCodRel+",P.DESCPROD,"+
				"P.CODUNID,SUM(IT.QTDITVENDA),"+
				"SUM(IT.VLRLIQITVENDA) FROM VDVENDA V,EQTIPOMOV TM,"+
				"VDITVENDA IT, EQPRODUTO P WHERE P.CODPROD = IT.CODPROD"+
				" AND IT.CODVENDA = V.CODVENDA"+
	            " AND TM.CODTIPOMOV=V.CODTIPOMOV" +
				(cbFaturados.getVlrString().equals("S") ? " AND TM.FISCALTIPOMOV='S' " : "")+
				(cbFinanceiro.getVlrString().equals("S") ? " AND TM.SOMAVDTIPOMOV='S' " : "")+
	            " AND TM.CODEMP=V.CODEMPTM" +
	            " AND TM.CODFILIAL=V.CODFILIALTM" +
			    " AND TM.TIPOMOV IN ('VD','PV','VT','SE')"+
				" AND V.DTEMITVENDA BETWEEN"+
				" ? AND ? "+sWhere+" AND V.FLAG IN "+
				Aplicativo.carregaFiltro(con,org.freedom.telas.Aplicativo.iCodEmp)+
				"AND NOT SUBSTR(V.STATUSVENDA,1,1)='C' GROUP BY P."+sCodRel+","+
				"P.DESCPROD,P.CODUNID ORDER BY "+sOrdem;
		}
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(sSQL);
			ps.setDate(1,Funcoes.dateToSQLDate(txtDataini.getVlrDate()));
			ps.setDate(2,Funcoes.dateToSQLDate(txtDatafim.getVlrDate()));
			rs = ps.executeQuery();
			imp.limpaPags();
			imp.montaCab();
			imp.setTitulo("Relatório de Vendas por ítem");
			imp.addSubTitulo("RELATORIO DE VENDAS POR ITEM  -  PERIODO DE :"+sDataini+" Até: "+sDatafim);
			while ( rs.next() ) {
				if (imp.pRow() == linPag) {
					imp.say(imp.pRow()+1,0,""+imp.comprimido());
					imp.say(imp.pRow()+0,0,"+"+Funcoes.replicate("-",133)+"+");
					imp.eject();
					imp.incPags();
				}
				if (imp.pRow()==0) {
					//imp.actionPerformed();
					
					imp.impCab(136, true);
					
					imp.say(imp.pRow()+0,0,""+imp.comprimido());
                    imp.say(imp.pRow()+0,0,sOrdenado);
					
                    if (sCab.length() > 0) 
						imp.say(imp.pRow()+0,0,sCab);
					
                    imp.say(imp.pRow()+1,0,""+imp.comprimido());
					imp.say(imp.pRow()+0,0,"|");
					imp.say(imp.pRow()+0,135,"|");
					imp.say(imp.pRow()+1,0,""+imp.comprimido());
					imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",133)+"|");
					imp.say(imp.pRow()+1,0,""+imp.comprimido());
					imp.say(imp.pRow()+0,1,"| Cod.prod. ");
					imp.say(imp.pRow()+0,15,"| Desc.produto");
					imp.say(imp.pRow()+0,69,"| Unid. ");
					imp.say(imp.pRow()+0,77,"|   Quantidade ");
					imp.say(imp.pRow()+0,100,"|    Vlr.tot.item. ");
					imp.say(imp.pRow()+0,135," |");
					imp.say(imp.pRow()+1,0,""+imp.comprimido());
					imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",133)+"|");
				}
				imp.say(imp.pRow()+1,0,"|");
				imp.say(imp.pRow()+0,3,Funcoes.copy(rs.getString(1),0,10)+" | ");
				imp.say(imp.pRow()+0,17,Funcoes.copy(rs.getString(2),0,50)+" | ");
				imp.say(imp.pRow()+0,70,Funcoes.copy(rs.getString(3),0,5)+" | ");
				
				imp.say(imp.pRow()+0,86,Funcoes.strDecimalToStrCurrency(6,1,rs.getString(4)));
				imp.say(imp.pRow()+0,99,"|");
				imp.say(imp.pRow()+0,100,Funcoes.strDecimalToStrCurrency(15,2,rs.getString(5)));
				imp.say(imp.pRow()+0,135,"|");
				dQtd += rs.getDouble(4);
				dVlr += rs.getDouble(5);
			}	
			imp.say(imp.pRow()+1,0,""+imp.comprimido());
			imp.say(imp.pRow()+0,0,"+"+Funcoes.replicate("=",133)+"+");
			imp.say(imp.pRow()+1,0,""+imp.comprimido());
			imp.say(imp.pRow()+0,0,"|");
			imp.say(imp.pRow()+0,30,"Quant. vendida -> ");
			imp.say(imp.pRow()+0,50,Funcoes.copy(dQtd+"",0,6));
			imp.say(imp.pRow()+0,60,"Valor vendido -> ");
			imp.say(imp.pRow()+0,78,Funcoes.strDecimalToStrCurrency(15,2,dVlr+""));
			imp.say(imp.pRow()+0,135,"|");
			imp.say(imp.pRow()+1,0,""+imp.comprimido());
			imp.say(imp.pRow()+0,0,"+"+Funcoes.replicate("=",133)+"+");
			
			imp.eject();
			
			imp.fechaGravacao();
			
//			rs.close();
//			ps.close();
			if (!con.getAutoCommit())
				con.commit();
//			dl.dispose();
		}	
		catch ( SQLException err ) {
			Funcoes.mensagemErro(this,"Erro consulta tabela vendas!\n"+err.getMessage(),true,con,err);			
		}
		
		if (bVisualizar) {
			imp.preview(this);
		}
		else {
			imp.print();
		}
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
                        if (rs.next()) {
                                if (rs.getString("UsaRefProd").trim().equals("S"))
                                        bRetorno = true;
                        }
//                      rs.close();
//                      ps.close();
                        if (!con.getAutoCommit())
                        	con.commit();
                }
                catch (SQLException err) {
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
	}
}
