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
import org.freedom.telas.FRelatorio;

/**
 * Extrato do estoque.
 *
 * @version 1.0 11/11/2004
 * @author Robson Sanchez e Fernado Oliveira da Silva
 *
 */
public class FKardex extends FRelatorio implements ActionListener {
        private JPanelPad pnCli = new JPanelPad(JPanelPad.TP_JPANEL,new BorderLayout());
        private JPanelPad pinCab = new JPanelPad(560,130);
        private JTextFieldPad txtDataini = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
        private JTextFieldPad txtDatafim = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
        private JTextFieldPad txtCodProd = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
        private JTextFieldFK txtRefProd = new JTextFieldFK(JTextFieldPad.TP_STRING,13,0);
        private JTextFieldFK txtDescProd = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
        private JTextFieldPad txtCodLote = new JTextFieldPad(JTextFieldPad.TP_STRING,13,0);
        private JTextFieldFK txtDescLote = new JTextFieldFK(JTextFieldPad.TP_DATE,10,0);
        private JTextFieldPad txtCodFabProd = new JTextFieldPad(JTextFieldPad.TP_STRING,13,0);
        private JButton btExec = new JButton("Trazer informações",Icone.novo("btExecuta.gif"));
        private Tabela tab = new Tabela();
        private JScrollPane spnTab = new JScrollPane(tab);
        private ListaCampos lcProd = new ListaCampos(this);
        private ListaCampos lcLote = new ListaCampos(this);
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
                txtCodProd.setTabelaExterna(lcProd);
                txtCodProd.setNomeCampo("CodProd");
                txtCodProd.setFK(true);
                lcProd.setReadOnly(true);
                lcProd.montaSql(false, "PRODUTO", "EQ");

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
                JLabelPad lbPeriodo = new JLabelPad(" Periodo:");
                lbPeriodo.setOpaque(true);
                JLabelPad lbProduto = new JLabelPad(" Produto:");
                lbProduto.setOpaque(true);
                
                setPainel(pinCab);
                adic(lbPeriodo,17,5,58,20);
                adic(lbLinha,7,15,135,100);
                adic(new JLabelPad("De:"),20,23,30,20);
                adic(txtDataini,20,43,100,20);
                adic(new JLabelPad("Até:"),20,63,37,20);
                adic(txtDatafim,20,83,100,20);
                adic(lbProduto,156,5,62,20);
                adic(lbLinha2,145,15,300,100);
                adic(new JLabelPad("Cód.prod."),158,23,200,20);
                adic(txtCodProd,158,43,70,20);
                adic(new JLabelPad("Descrição do produto"),232,23,200,20);
                adic(txtDescProd,232,43,200,20);
                adic(new JLabelPad("Cód.lote"),158,63,200,20);
                adic(txtCodLote,158,83,70,20);
                adic(new JLabelPad("Vencimento do lote"),232,63,200,20);
                adic(txtDescLote,232,83,200,20);
                adic(btExec,449,85,200,30);
                
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
                if (txtDatafim.getVlrDate().before(txtDataini.getVlrDate())) {
					Funcoes.mensagemInforma(this,"Data final maior que a data inicial!");
                        return;
                }
                String sWhere = "";
                if (!txtCodLote.getText().trim().equals("")) {
                        sWhere = " AND CODLOTE = '"+txtCodLote.getText().trim()+"'";
                }
                String sSQL = "SELECT MP.DTMOVPROD,TM.TIPOMOV,MP.CODNAT,MP.DOCMOVPROD,MP.CODALMOX,"+
                              "MP.CODLOTE,MP.QTDMOVPROD,MP.PRECOMOVPROD,MP.ESTOQMOVPROD, " +
                              "MP.SLDMOVPROD,MP.CUSTOMPMMOVPROD, MP.CODMOVPROD"+
                              " FROM EQMOVPROD MP, EQTIPOMOV TM WHERE MP.CODPROD=? " +
                              "AND MP.DTMOVPROD BETWEEN ? AND ? AND "+
                              "MP.CODEMPTM=TM.CODEMP AND MP.CODFILIALTM=TM.CODFILIAL AND " +
                              "MP.CODTIPOMOV=TM.CODTIPOMOV "+
                              sWhere+" ORDER BY DTMOVPROD,CODMOVPROD";
                try {
                        PreparedStatement ps = con.prepareStatement(sSQL);
                        ps.setInt(1,txtCodProd.getVlrInteger().intValue());
                        ps.setDate(2,Funcoes.dateToSQLDate(txtDataini.getVlrDate()));
                        ps.setDate(3,Funcoes.dateToSQLDate(txtDatafim.getVlrDate()));
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
                                tab.setValor(new StringDireita(rs.getFloat("SLDMOVPROD")+""),iLinha,9);
                                tab.setValor(new StringDireita(Funcoes.strDecimalToStrCurrency(15,2,rs.getString("CUSTOMPMMOVPROD"))),iLinha,10);
                                tab.setValor(new StringDireita(""+rs.getInt("CODMOVPROD")),iLinha,11);
                                iLinha++;
                        }
                        rs.close();
                        ps.close();
                        if (!con.getAutoCommit())
                        	con.commit();
                }
                catch (SQLException err) {
					Funcoes.mensagemErro(this,"Erro ao carrregar a tabela MOVPROD !\n"+err.getMessage());
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
                imp.setSubTitulo("PERIODO DE :"+sDataini+" ATÉ: "+sDatafim);
                imp.impCab(136, true);
                sCab += "PRODUTO: "+txtDescProd.getText().trim();
                if (txtCodLote.getText().trim().length() > 0) {
                        sCab += "  /  Lote: "+txtCodLote.getText().trim();
                }
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
                    		if (!sCab.trim().equals("")) {
	                            imp.say(imp.pRow()+1,0,""+imp.comprimido());
	                            imp.say(imp.pRow()+0,0,"|"+sCab);
	                            imp.say(imp.pRow()+0,135,"|");
                    		}
                            imp.say(imp.pRow()+1,0,""+imp.comprimido());
                            imp.say(imp.pRow()+0,0,"| Data "); //10
                            imp.say(imp.pRow()+0,14,"| Tp."); //2
                            imp.say(imp.pRow()+0,20,"| Op. "); //4
                            imp.say(imp.pRow()+0,28,"| Doc. "); //13
                            imp.say(imp.pRow()+0,45,"| Cód.almox. "); //13
                            imp.say(imp.pRow()+0,58,"| Cód.lote "); //13
                            imp.say(imp.pRow()+0,75,"| Quantidade "); //8
                            imp.say(imp.pRow()+0,87,"| Valor.unit. "); //15
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
                    imp.say(imp.pRow()+0,87,"| "+tab.getValor(i,7));
                    imp.say(imp.pRow()+0,106,"| "+tab.getValor(i,8));
                    imp.say(imp.pRow()+0,118,"| "+tab.getValor(i,9));
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
            lcLote.setConexao(cn);
            txtCodProd.setBuscaAdic(new DLBuscaProd(this,con,"CODPROD"));
        }
        public void actionPerformed(ActionEvent evt) {
                if (evt.getSource() == btExec) {
                        executar();
                }
                super.actionPerformed(evt);
        }
}

