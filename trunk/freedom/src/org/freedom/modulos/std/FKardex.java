/**
 * @version 09/02/2002 <BR>
 * @author Setpoint Informática Ltda./Robson Sanchez e Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FKardex.java <BR>
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
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JPanelPad;
import javax.swing.JScrollPane;

import org.freedom.bmps.Icone;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.StringDireita;
import org.freedom.componentes.Tabela;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FRelatorio;

/**
 * Extrato do estoque.
 *
 * @version 1.0 11/11/2004
 * @author Robson Sanchez e Fernado Oliveira da Silva
 *
 */
public class FKardex extends FRelatorio implements ActionListener {

	private static final long serialVersionUID = 1L;

        private JPanelPad pnCli = new JPanelPad(JPanelPad.TP_JPANEL,new BorderLayout());
        private JPanelPad pinCab = new JPanelPad(560,100);
        private JTextFieldPad txtDataini = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
        private JTextFieldPad txtDatafim = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
        private JTextFieldPad txtCodProd = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
        private JTextFieldFK txtRefProd = new JTextFieldFK(JTextFieldPad.TP_STRING,13,0);
        private JTextFieldFK txtDescProd = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
        private JTextFieldPad txtCodAlmox = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
        private JTextFieldFK txtDescAlmox = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
        private JTextFieldPad txtCodLote = new JTextFieldPad(JTextFieldPad.TP_STRING,13,0);
        private JTextFieldFK txtDescLote = new JTextFieldFK(JTextFieldPad.TP_DATE,10,0);
        private JTextFieldPad txtCodFabProd = new JTextFieldPad(JTextFieldPad.TP_STRING,13,0);
        private JButton btExec = new JButton(Icone.novo("btExecuta.gif"));
        private Tabela tab = new Tabela();
        private JScrollPane spnTab = new JScrollPane(tab);
        private ListaCampos lcProd = new ListaCampos(this);
        private ListaCampos lcLote = new ListaCampos(this);
        private ListaCampos lcAlmox = new ListaCampos(this);
        private Container cTela = null;
        public FKardex() {
                setTitulo("Kardex");
                setAtribos(10,10,688,400);
                
                txtCodProd.setRequerido(true);                
                txtDataini.setRequerido(true);
                txtDatafim.setRequerido(true);

                lcProd.add(new GuardaCampo( txtCodProd, "CodProd", "Cód.prod.", ListaCampos.DB_PK, false));
                lcProd.add(new GuardaCampo( txtRefProd, "RefProd", "Referência do produto", ListaCampos.DB_SI, false));
                lcProd.add(new GuardaCampo( txtDescProd, "DescProd", "Descrição do produto", ListaCampos.DB_SI, false));
                lcProd.add(new GuardaCampo( txtCodFabProd, "codfabprod", "Cód.fab.prod.",ListaCampos.DB_SI,false));
                lcProd.add(new GuardaCampo( txtCodAlmox, "CodAlmox", "Cód.almox.", ListaCampos.DB_FK, txtDescAlmox, false));
                txtCodProd.setTabelaExterna(lcProd);
                txtCodProd.setNomeCampo("CodProd");
                txtCodProd.setFK(true);
                lcProd.setReadOnly(true);
                lcProd.montaSql(false, "PRODUTO", "EQ");

                lcAlmox.add(new GuardaCampo( txtCodAlmox, "CodAlmox", "Cód.almox.", ListaCampos.DB_PK, false));
                lcAlmox.add(new GuardaCampo( txtDescAlmox, "DescAlmox", "Descrição do almox.", ListaCampos.DB_SI, false));
                txtCodAlmox.setTabelaExterna(lcAlmox);
                txtCodAlmox.setNomeCampo("CodAlmox");
                txtCodAlmox.setFK(true);
                lcAlmox.setReadOnly(true);
                lcAlmox.montaSql(false, "ALMOX", "EQ");

                lcLote.add(new GuardaCampo( txtCodLote, "CodLote", "Cód.lote", ListaCampos.DB_PK, false));
                lcLote.add(new GuardaCampo( txtDescLote, "VenctoLote", "Vencimento do lote", ListaCampos.DB_SI, false));
                txtCodLote.setTabelaExterna(lcLote);
                txtCodLote.setNomeCampo("CodLote");
                txtCodLote.setFK(true);
                lcLote.setReadOnly(true);
                lcLote.setDinWhereAdic("CODPROD = #N",txtCodProd);
                lcLote.montaSql(false, "LOTE", "EQ");
                
                cTela = getTela();
                cTela.add(pnCli,BorderLayout.CENTER);
                pnCli.add(pinCab,BorderLayout.NORTH);
                pnCli.add(spnTab,BorderLayout.CENTER);
                JLabelPad lbLinha = new JLabelPad();
                lbLinha.setBorder(BorderFactory.createEtchedBorder());
                JLabelPad lbLinha2 = new JLabelPad();
                lbLinha2.setBorder(BorderFactory.createEtchedBorder());
                
                setPainel(pinCab);
                adic(new JLabelPad("Período"),7,5,100,20);
                adic(txtDataini,7,25,100,20);
                adic(new JLabelPad("até"),110,5,100,20);
                adic(txtDatafim,110,25,100,20);
                adic(new JLabelPad("Cód.prod."),223,5,70,20);
                adic(new JLabelPad("Descrição do produto"),296,5,260,20);
                adic(txtCodProd,223,25,70,20);
                adic(txtDescProd,296,25,260,20);
                adic(new JLabelPad("Cód.almox."),7,50,90,20);
                adic(new JLabelPad("Descrição do almoxarifado"),100,50,200,20);
                adic(txtCodAlmox,7,70,90,20);
                adic(txtDescAlmox,100,70,200,20);
                adic(new JLabelPad("Cód.lote"),303,50,90,20);
                adic(new JLabelPad("Descrição do lote"),396,50,200,20);
                adic(txtCodLote,303,70,90,20);
                adic(txtDescLote,396,70,200,20);
                adic(btExec,566,15,30,30);
                btExec.setToolTipText("Executa consulta.");
                
                tab.adicColuna("Data");
                tab.adicColuna("Tipo");
                tab.adicColuna("Operação");
                tab.adicColuna("Doc.");
                tab.adicColuna("Almox.");
                tab.adicColuna("Cód.lote");
                tab.adicColuna("Quantidade.");
                tab.adicColuna("Valor unit.");
                tab.adicColuna("EQ");
                tab.adicColuna("Saldo");
                tab.adicColuna("Custo MPM");
                tab.adicColuna("Cód.mv.pr.");
                tab.setTamColuna(90,0);
                tab.setTamColuna(40,1);
                tab.setTamColuna(70,2);
                tab.setTamColuna(70,3);
                tab.setTamColuna(70,4);
                tab.setTamColuna(70,5);
                tab.setTamColuna(90,6);
                tab.setTamColuna(100,7);
                tab.setTamColuna(30,8);
                tab.setTamColuna(85,9);
                tab.setTamColuna(90,10);
                tab.setTamColuna(70,11);
                
                btExec.addActionListener(this);
                
				Calendar cPeriodo = Calendar.getInstance();
			    txtDatafim.setVlrDate(cPeriodo.getTime());
				cPeriodo.set(Calendar.DAY_OF_MONTH,cPeriodo.get(Calendar.DAY_OF_MONTH)-30);
				txtDataini.setVlrDate(cPeriodo.getTime());
                
        }
        private void executar() {
        	    String sSQL = null;
        	    String sWhere = null;
        	    String sCodLote = null;
        	    String sCols = null;
        	    int iCodAlmox = 0;
        	    int iCodProd = 0;
        	    int iParam = 0;
        	    try {
	                sWhere = "AND MP.DTMOVPROD BETWEEN ? AND ? " +
	                		"AND MP.CODEMPPD=? AND MP.CODFILIALPD=? AND MP.CODPROD=? ";
	                iCodProd = txtCodProd.getVlrInteger().intValue();
	                iCodAlmox = txtCodAlmox.getVlrInteger().intValue();
	                sCodLote = txtCodLote.getVlrString();
	                
	                if (txtDatafim.getVlrDate().before(txtDataini.getVlrDate())) {
						Funcoes.mensagemInforma(this,"Data final maior que a data inicial!");
	                    return;
	                }
	                if (iCodAlmox!=0) {
	                	sCols = "MP.SLDMOVPRODAX,MP.CUSTOMPMMOVPRODAX,";
	                	sWhere += " AND MP.CODEMPAX=? AND MP.CODFILIALAX=? AND MP.CODALMOX=?";
	                }
	                else {
	                	sCols = "MP.SLDMOVPROD,MP.CUSTOMPMMOVPROD,";
	                }
	                if (!sCodLote.trim().equals("")) 
	                    sWhere += " AND MP.CODEMPLE=? AND MP.CODFILIALLE=? AND MP.CODLOTE=?";
	                
	                
	                sSQL = "SELECT MP.DTMOVPROD,TM.TIPOMOV,MP.CODNAT,MP.DOCMOVPROD,MP.CODALMOX,"+
                              "MP.CODLOTE,MP.QTDMOVPROD,MP.PRECOMOVPROD,MP.ESTOQMOVPROD, " +
                              sCols+"MP.CODMOVPROD"+
                              " FROM EQMOVPROD MP, EQTIPOMOV TM " +
                              "WHERE MP.CODEMPTM=TM.CODEMP AND MP.CODFILIALTM=TM.CODFILIAL AND " +
                              "MP.CODTIPOMOV=TM.CODTIPOMOV "+
                              sWhere+" ORDER BY DTMOVPROD,CODMOVPROD";
	                try {
	                        PreparedStatement ps = con.prepareStatement(sSQL);
	                        ps.setDate(1,Funcoes.dateToSQLDate(txtDataini.getVlrDate()));
	                        ps.setDate(2,Funcoes.dateToSQLDate(txtDatafim.getVlrDate()));
	                        ps.setInt(3,Aplicativo.iCodEmp);
	                        ps.setInt(4,ListaCampos.getMasterFilial("EQPRODUTO"));
	                        ps.setInt(5,iCodProd);
	                        iParam = 6;
	                        if (iCodAlmox!=0) {
		                        ps.setInt(iParam++,Aplicativo.iCodEmp);
		                        ps.setInt(iParam++,ListaCampos.getMasterFilial("EQALMOX"));
		                        ps.setInt(iParam++,iCodAlmox);
	                        }
	                        if (!sCodLote.trim().equals("")) {
		                        ps.setInt(iParam++,Aplicativo.iCodEmp);
		                        ps.setInt(iParam++,ListaCampos.getMasterFilial("EQLOTE"));
		                        ps.setString(iParam++,sCodLote);
	                        }
	                        ResultSet rs = ps.executeQuery();
	                        tab.limpa();
	                        int iLinha = 0;
	                        while (rs.next()) {
	                                tab.adicLinha();
	                                tab.setValor(Funcoes.sqlDateToStrDate(rs.getDate("DTMOVPROD")),iLinha,0);
	                                tab.setValor(rs.getString("TIPOMOV") ,iLinha,1);
	                                tab.setValor(Funcoes.setMascara(rs.getString("CODNAT"),"#.###"),iLinha,2);
	                                tab.setValor(new StringDireita(rs.getInt("DOCMOVPROD")+""),iLinha,3);
	                                tab.setValor(new Integer(rs.getInt("CODALMOX")),iLinha,4);
	                                tab.setValor(rs.getString("CODLOTE") != null ? rs.getString("CODLOTE")+"" : "",iLinha,5);
	                                tab.setValor(new StringDireita(rs.getFloat("QTDMOVPROD")+""),iLinha,6);
	                                tab.setValor(new StringDireita(Funcoes.strDecimalToStrCurrency(15,2,rs.getString("PRECOMOVPROD"))),iLinha,7);
	                                tab.setValor(rs.getString("ESTOQMOVPROD") ,iLinha,8);
	                                tab.setValor(new StringDireita(rs.getFloat((iCodAlmox==0?"SLDMOVPROD":"SLDMOVPRODAX"))+""),iLinha,9);
	                                tab.setValor(new StringDireita(Funcoes.strDecimalToStrCurrency(15,2,rs.getString((iCodAlmox==0?"CUSTOMPMMOVPROD":"CUSTOMPMMOVPRODAX")))),iLinha,10);
	                                tab.setValor(new StringDireita(""+rs.getInt("CODMOVPROD")),iLinha,11);
	                                iLinha++;
	                        }
	                        rs.close();
	                        ps.close();
	                        if (!con.getAutoCommit())
	                        	con.commit();
	                }
	                catch (SQLException err) {
						Funcoes.mensagemErro(this,"Erro ao carrregar a tabela MOVPROD !\n"+err.getMessage(),true,con,err);
	                }
        	    }
        	    finally {
        	    	sSQL = null;
        	    	sWhere = null;
        	    	iCodProd = 0;
        	    	iCodAlmox = 0;
        	    	iParam = 0;
        	    }
        }
        public void imprimir(boolean bVisualizar) {
                ImprimeOS imp = new ImprimeOS("",con);
                int linPag = imp.verifLinPag()-1;
                imp.montaCab();
                String sCab = "";
                String sDataini = txtDataini.getVlrString();
                String sDatafim = txtDatafim.getVlrString();
                imp.setTitulo("EXTRATO DO ESTOQUE");
                imp.addSubTitulo("PERIODO DE :"+sDataini+" ATÉ: "+sDatafim);
                sCab += "PRODUTO: "+txtDescProd.getText().trim();
                if (txtCodLote.getText().trim().length() > 0) {
                        sCab += "  /  Lote: "+txtCodLote.getText().trim();
                }
                if (!sCab.trim().equals("")) {
                    imp.addSubTitulo(sCab);
        		}
                imp.impCab(136, true);
                imp.limpaPags();
                boolean hasData = false;
                for (int i=0; i<tab.getNumLinhas(); i++) {
                	hasData = true;
                        tab.setLinhaSel(i);
                	if (imp.pRow() == linPag) {
                                imp.say(imp.pRow()+1,0,""+imp.comprimido());
                                imp.say(imp.pRow()+0,0,"+"+Funcoes.replicate("-",133)+"+");
                                imp.eject();
                        	imp.incPags();
                	}
                    if (i==0) {
                            imp.say(imp.pRow()+0,0,""+imp.comprimido());
                            imp.say(imp.pRow()+0,0,"| Data "); //10
                            imp.say(imp.pRow()+0,14,"| Tp."); //2
                            imp.say(imp.pRow()+0,20,"| Op. "); //4
                            imp.say(imp.pRow()+0,28,"| Doc. "); //13
                            imp.say(imp.pRow()+0,45,"| Cód.almox. "); //13
                            imp.say(imp.pRow()+0,58,"| Cód.lote "); //13
                            imp.say(imp.pRow()+0,75,"| Quantidade "); //8
                            imp.say(imp.pRow()+0,88,"| Valor.unit. "); //15
                            imp.say(imp.pRow()+0,106,"| Saldo ");//8
                            imp.say(imp.pRow()+0,118,"| Custo MPM "); //15
                            imp.say(imp.pRow()+0,135,"|");
                            imp.say(imp.pRow()+1,0,""+imp.comprimido());
                            imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",133)+"|");
                    }
                    imp.say(imp.pRow()+1,0,""+imp.comprimido());
                    imp.say(imp.pRow()+0,0,"| "+tab.getValor(i,0));
                    imp.say(imp.pRow()+0,14,"| "+tab.getValor(i,1));
                    imp.say(imp.pRow()+0,20,"| "+tab.getValor(i,2));
                    imp.say(imp.pRow()+0,28,"| "+tab.getValor(i,3));
                    imp.say(imp.pRow()+0,45,"| "+tab.getValor(i,4));
                    imp.say(imp.pRow()+0,58,"| "+tab.getValor(i,5));
                    imp.say(imp.pRow()+0,75,"| "+tab.getValor(i,6));
                    imp.say(imp.pRow()+0,88,"| "+tab.getValor(i,7));
                    imp.say(imp.pRow()+0,106,"| "+tab.getValor(i,9));
                    imp.say(imp.pRow()+0,118,"| "+tab.getValor(i,10));
                    imp.say(imp.pRow()+0,135,"|");
                }
                imp.say(imp.pRow()+(hasData ? 1 : 0),0,""+imp.comprimido());
                imp.say(imp.pRow()+0,0,"+"+Funcoes.replicate("-",133)+"+");
      
                imp.eject();
      
                imp.fechaGravacao();
      
                if (bVisualizar) {
                        imp.preview(this);
                }
                else {
                        imp.print();
                }
        }

        public void setConexao(Connection cn) {
       		super.setConexao(cn);
            lcProd.setConexao(cn);
            lcAlmox.setConexao(cn);
            lcLote.setConexao(cn);
            txtCodProd.setBuscaAdic(new DLBuscaProd(con,"CODPROD",lcProd.getWhereAdic()));
        }
        public void actionPerformed(ActionEvent evt) {
                if (evt.getSource() == btExec) {
                        executar();
                }
                super.actionPerformed(evt);
        }
}

