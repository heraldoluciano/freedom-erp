/**
 * @version 17/07/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.pdv <BR>
 * Classe: @(#)FLeFiscal.java <BR>
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

package org.freedom.modulos.pdv;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JPanelPad;
import javax.swing.JScrollPane;

import org.freedom.bmps.Icone;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Tabela;
import org.freedom.drivers.JBemaFI32;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.AplicativoPDV;
import org.freedom.telas.FTabDados;

public class FLeFiscal extends FTabDados {

	private static final long serialVersionUID = 1L;

	private JPanelPad pinCab = new JPanelPad(0,60);
	private JPanelPad pinGeral = new JPanelPad();
	private JPanelPad pinAliq = new JPanelPad();
	private JPanelPad pinTrib = new JPanelPad();
	private JPanelPad pinTributado = new JPanelPad();
	private JPanelPad pinIsen = new JPanelPad();
	private JPanelPad pinIsento = new JPanelPad();
	private JPanelPad pnMapa = new JPanelPad(JPanelPad.TP_JPANEL,new GridLayout(1,1));
	private JTextFieldPad txtDataX = new JTextFieldPad(JTextFieldPad.TP_DATE, 10, 0);
	private JTextFieldPad txtNumTermX = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
	private JTextFieldFK txtDescTermX = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
	private JTextFieldPad txtAliq1 = new JTextFieldPad(JTextFieldPad.TP_DECIMAL, 5, 2);
	private JTextFieldPad txtAliq2 = new JTextFieldPad(JTextFieldPad.TP_DECIMAL, 5, 2);
	private JTextFieldPad txtAliq3 = new JTextFieldPad(JTextFieldPad.TP_DECIMAL, 5, 2);
	private JTextFieldPad txtAliq4 = new JTextFieldPad(JTextFieldPad.TP_DECIMAL, 5, 2);
	private JTextFieldPad txtAliq5 = new JTextFieldPad(JTextFieldPad.TP_DECIMAL, 5, 2);
	private JTextFieldPad txtAliq6 = new JTextFieldPad(JTextFieldPad.TP_DECIMAL, 5, 2);
	private JTextFieldPad txtAliq7 = new JTextFieldPad(JTextFieldPad.TP_DECIMAL, 5, 2);
	private JTextFieldPad txtAliq8 = new JTextFieldPad(JTextFieldPad.TP_DECIMAL, 5, 2);
	private JTextFieldPad txtAliq9 = new JTextFieldPad(JTextFieldPad.TP_DECIMAL, 5, 2);
	private JTextFieldPad txtAliq10 = new JTextFieldPad(JTextFieldPad.TP_DECIMAL, 5, 2);
	private JTextFieldPad txtAliq11 = new JTextFieldPad(JTextFieldPad.TP_DECIMAL, 5, 2);
	private JTextFieldPad txtAliq12 = new JTextFieldPad(JTextFieldPad.TP_DECIMAL, 5, 2);
	private JTextFieldPad txtAliq13 = new JTextFieldPad(JTextFieldPad.TP_DECIMAL, 5, 2);
	private JTextFieldPad txtAliq14 = new JTextFieldPad(JTextFieldPad.TP_DECIMAL, 5, 2);
	private JTextFieldPad txtAliq15 = new JTextFieldPad(JTextFieldPad.TP_DECIMAL, 5, 2);
	private JTextFieldPad txtAliq16 = new JTextFieldPad(JTextFieldPad.TP_DECIMAL, 5, 2);
	private JTextFieldPad txtT1 = new JTextFieldPad(JTextFieldPad.TP_DECIMAL, 15, 2);
	private JTextFieldPad txtT2 = new JTextFieldPad(JTextFieldPad.TP_DECIMAL, 15, 2);
	private JTextFieldPad txtT3 = new JTextFieldPad(JTextFieldPad.TP_DECIMAL, 15, 2);
	private JTextFieldPad txtT4 = new JTextFieldPad(JTextFieldPad.TP_DECIMAL, 15, 2);
	private JTextFieldPad txtT5 = new JTextFieldPad(JTextFieldPad.TP_DECIMAL, 15, 2);
	private JTextFieldPad txtT6 = new JTextFieldPad(JTextFieldPad.TP_DECIMAL, 15, 2);
	private JTextFieldPad txtT7 = new JTextFieldPad(JTextFieldPad.TP_DECIMAL, 15, 2);
	private JTextFieldPad txtT8 = new JTextFieldPad(JTextFieldPad.TP_DECIMAL, 15, 2);
	private JTextFieldPad txtT9 = new JTextFieldPad(JTextFieldPad.TP_DECIMAL, 15, 2);
	private JTextFieldPad txtT10 = new JTextFieldPad(JTextFieldPad.TP_DECIMAL, 15, 2);
	private JTextFieldPad txtT11 = new JTextFieldPad(JTextFieldPad.TP_DECIMAL, 15, 2);
	private JTextFieldPad txtT12 = new JTextFieldPad(JTextFieldPad.TP_DECIMAL, 15, 2);
	private JTextFieldPad txtT13 = new JTextFieldPad(JTextFieldPad.TP_DECIMAL, 15, 2);
	private JTextFieldPad txtT14 = new JTextFieldPad(JTextFieldPad.TP_DECIMAL, 15, 2);
	private JTextFieldPad txtT15 = new JTextFieldPad(JTextFieldPad.TP_DECIMAL, 15, 2);
	private JTextFieldPad txtT16 = new JTextFieldPad(JTextFieldPad.TP_DECIMAL, 15, 2);
	private JTextFieldPad txtTN1 = new JTextFieldPad(JTextFieldPad.TP_DECIMAL, 15, 2);
	private JTextFieldPad txtTN2 = new JTextFieldPad(JTextFieldPad.TP_DECIMAL, 15, 2);
	private JTextFieldPad txtTN3 = new JTextFieldPad(JTextFieldPad.TP_DECIMAL, 15, 2);
	private JTextFieldPad txtTN4 = new JTextFieldPad(JTextFieldPad.TP_DECIMAL, 15, 2);
	private JTextFieldPad txtTN5 = new JTextFieldPad(JTextFieldPad.TP_DECIMAL, 15, 2);
	private JTextFieldPad txtTN6 = new JTextFieldPad(JTextFieldPad.TP_DECIMAL, 15, 2);
	private JTextFieldPad txtTN7 = new JTextFieldPad(JTextFieldPad.TP_DECIMAL, 15, 2);
	private JTextFieldPad txtTN8 = new JTextFieldPad(JTextFieldPad.TP_DECIMAL, 15, 2);
	private JTextFieldPad txtTN9 = new JTextFieldPad(JTextFieldPad.TP_DECIMAL, 15, 2);
	private JTextFieldPad txtTSangria = new JTextFieldPad(JTextFieldPad.TP_DECIMAL, 15, 2);
	private JTextFieldPad txtPrimCupom = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	private JTextFieldPad txtTSuprimento = new JTextFieldPad(JTextFieldPad.TP_DECIMAL, 15, 2);
	private JTextFieldPad txtUltCupom = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	private JTextFieldPad txtTotal = new JTextFieldPad(JTextFieldPad.TP_DECIMAL, 15, 2);
	private JTextFieldPad txtCanc = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	private JTextFieldPad txtVlrCanc = new JTextFieldPad(JTextFieldPad.TP_DECIMAL, 15, 2);
	private JTextFieldPad txtRed = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	private JTextFieldPad txtVlrDesc = new JTextFieldPad(JTextFieldPad.TP_DECIMAL, 15, 2);
	private JTextFieldPad txtSequencia = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	private JTextFieldPad txtVlrIsento = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	private JTextFieldPad txtVlrNI = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	private JTextFieldPad txtVlrSubst = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	private Tabela tab = new Tabela();
	private JScrollPane spnTab = new JScrollPane(tab);
	private ListaCampos lcCaixa = new ListaCampos(this,"");
	private JButton btExec = new JButton(Icone.novo("btExecuta.gif"));
	private JBemaFI32 bf = (AplicativoPDV.bECFTerm ? new JBemaFI32() : null);
	public FLeFiscal() {
		super();
		setTitulo("Leitura de memória fiscal");
		setAtribos(60,60,540,470);

		txtPrimCupom.setAtivo(false);
		txtRed.setAtivo(false);
		
		lcCaixa.add(new GuardaCampo( txtNumTermX, "CodCaixa", "Cód.caixa", ListaCampos.DB_PK, true));
		lcCaixa.add(new GuardaCampo( txtDescTermX, "DescCaixa", "Descrição do caixa", ListaCampos.DB_SI, false));
		lcCaixa.montaSql(false, "CAIXA", "PV");    
		lcCaixa.setQueryCommit(false);
		lcCaixa.setReadOnly(true);
		txtNumTermX.setTabelaExterna(lcCaixa);

		pnCliente.add(pinCab,BorderLayout.NORTH);
		
		setPainel(pinCab);
		adicCampo(txtDataX, 7, 20, 100, 20, "Dtlx", "Data da leitura", ListaCampos.DB_PK, true);
		adicCampo(txtNumTermX, 110,20,77,20, "CodCaixa", "Cód.caixa", ListaCampos.DB_PF, true);
		adicDescFK(txtDescTermX, 190,20,240,20, "DescCaixa", "Descrição do caixa/máquina");
		adic(btExec,470,10,30,30);

		adicTab("Geral", pinGeral); 
		setPainel(pinGeral);
		
		JLabelPad lbAliq = new JLabelPad(" Aliquotas ICMS");
		lbAliq.setOpaque(true);
		
		adic(lbAliq,15,5,90,15);
		adic(pinAliq,7,10,423,100);
		
		setPainel(pinAliq);
		adicCampo(txtAliq1,  7,25,50,20,   "Aliq01", "T1",  ListaCampos.DB_SI, false);
		adicCampo(txtAliq2,  60,25,47,20,  "Aliq02", "T2",  ListaCampos.DB_SI, false);
		adicCampo(txtAliq3,  110,25,47,20, "Aliq03", "T3",  ListaCampos.DB_SI, false);
		adicCampo(txtAliq4,  160,25,47,20, "Aliq04", "T4",  ListaCampos.DB_SI, false);
		adicCampo(txtAliq5,  210,25,47,20, "Aliq05", "T5",  ListaCampos.DB_SI, false);
		adicCampo(txtAliq6,  260,25,47,20, "Aliq06", "T6",  ListaCampos.DB_SI, false);
		adicCampo(txtAliq7,  310,25,47,20, "Aliq07", "T7",  ListaCampos.DB_SI, false);
		adicCampo(txtAliq8,  360,25,50,20, "Aliq08", "T8",  ListaCampos.DB_SI, false);
		adicCampo(txtAliq9,  7,65,50,20,   "Aliq09", "T9",  ListaCampos.DB_SI, false);
		adicCampo(txtAliq10, 60,65,47,20,  "Aliq10", "T10", ListaCampos.DB_SI, false);
		adicCampo(txtAliq11, 110,65,47,20, "Aliq11", "T11", ListaCampos.DB_SI, false);
		adicCampo(txtAliq12, 160,65,47,20, "Aliq12", "T12", ListaCampos.DB_SI, false);
		adicCampo(txtAliq13, 210,65,47,20, "Aliq13", "T13", ListaCampos.DB_SI, false);
		adicCampo(txtAliq14, 260,65,47,20, "Aliq14", "T14", ListaCampos.DB_SI, false);
		adicCampo(txtAliq15, 310,65,47,20, "Aliq15", "T15", ListaCampos.DB_SI, false);
		adicCampo(txtAliq16, 360,65,50,20, "Aliq16", "T16", ListaCampos.DB_SI, false);
		
		setPainel(pinGeral);
		
		JLabelPad lbLinha = new JLabelPad("");
		lbLinha.setBorder(BorderFactory.createEtchedBorder());
		JLabelPad lbContadores = new JLabelPad("  Contadores:");
		lbContadores.setOpaque(true);
		adic(lbContadores,15,125,100,15);
		adic(lbLinha,7,130,500,2);
		
		adicCampo(txtPrimCupom, 7,160,85,20, "PrimCupomLX", "Prim.cupom", ListaCampos.DB_SI, false);
		adicCampo(txtUltCupom, 95,160,82,20, "UltCupomLX", "Ult.cupom", ListaCampos.DB_SI, false);
		adicCampo(txtCanc,180,160,82,20, "NumCancLX", "Cancelados", ListaCampos.DB_SI, false);
		adicCampo(txtRed,265,160,82,20, "NumRedLX", "Reduções", ListaCampos.DB_SI, false);
		adicCampo(txtSequencia,350,160,80,20, "ContaLX", "Contador", ListaCampos.DB_SI, false);
		
		JLabelPad lbLinha2 = new JLabelPad("");
		lbLinha2.setBorder(BorderFactory.createEtchedBorder());
		JLabelPad lbContadores2 = new JLabelPad("  Totalizadores:");
		lbContadores2.setOpaque(true);
		adic(lbContadores2,15,195,100,15);
		adic(lbLinha2,7,200,500,2);
		
		adicCampo(txtTSangria, 7,230,105,20, "TSangria", "Sangria", ListaCampos.DB_SI, false);
		adicCampo(txtTSuprimento, 115,230,102,20, "TSuprimento", "Suprimento", ListaCampos.DB_SI, false);
		adicCampo(txtVlrCanc, 220,230,102,20, "VlrCancLX", "Cancelamento", ListaCampos.DB_SI, false);
		adicCampo(txtVlrDesc, 325,230,105,20, "VlrDescLX", "Desconto", ListaCampos.DB_SI, false);
		adicCampo(txtTotal, 7,270,100,20, "TGTotal", "Grande total", ListaCampos.DB_SI, false);
		
		adicTab("Tributado", pinTributado); 
		setPainel(pinTributado);
		
		JLabelPad lbTrib = new JLabelPad("  Valor dos totalizadores de ICMS.");
		lbTrib.setOpaque(true);
		
		adic(lbTrib,15,5,200,15);
		adic(pinTrib,7,10,423,180);
		
		setPainel(pinTrib);
		adicCampo(txtT1,  7,25,100,20,   "TT01", "T1",  ListaCampos.DB_SI, false);
		adicCampo(txtT2,  110,25,97,20,  "TT02", "T2",  ListaCampos.DB_SI, false);
		adicCampo(txtT3,  210,25,97,20,  "TT03", "T3",  ListaCampos.DB_SI, false);
		adicCampo(txtT4,  310,25,100,20, "TT04", "T4",  ListaCampos.DB_SI, false);
		adicCampo(txtT5,  7,65,100,20,   "TT05", "T5",  ListaCampos.DB_SI, false);
		adicCampo(txtT6,  110,65,97,20,  "TT06", "T6",  ListaCampos.DB_SI, false);
		adicCampo(txtT7,  210,65,97,20,  "TT07", "T7",  ListaCampos.DB_SI, false);
		adicCampo(txtT8,  310,65,100,20, "TT08", "T8",  ListaCampos.DB_SI, false);
		adicCampo(txtT9,  7,105,100,20,  "TT09", "T9",  ListaCampos.DB_SI, false);
		adicCampo(txtT10, 110,105,97,20, "TT10", "T10", ListaCampos.DB_SI, false);
		adicCampo(txtT11, 210,105,97,20, "TT11", "T11", ListaCampos.DB_SI, false);
		adicCampo(txtT12, 310,105,100,20,"TT12", "T12", ListaCampos.DB_SI, false);
		adicCampo(txtT13, 7,145,100,20,  "TT13", "T13", ListaCampos.DB_SI, false);
		adicCampo(txtT14, 110,145,97,20, "TT14", "T14", ListaCampos.DB_SI, false);
		adicCampo(txtT15, 210,145,97,20, "TT15", "T15", ListaCampos.DB_SI, false);
		adicCampo(txtT16, 310,145,100,20,"TT16", "T16", ListaCampos.DB_SI, false);
		
		adicTab("Isento", pinIsento); 
		setPainel(pinIsento);
		
		JLabelPad lbIsento = new JLabelPad("  Valor dos totalizadores não sujeitos ao ICMS.");
		lbIsento.setOpaque(true);
		
		adic(lbIsento,15,5,280,15);
		adic(pinIsen,7,10,323,140);
		
		setPainel(pinIsen);
		adicCampo(txtTN1, 7,25,100,20,  "TN1", "T1", ListaCampos.DB_SI, false);
		adicCampo(txtTN2, 110,25,97,20, "TN2", "T2", ListaCampos.DB_SI, false);
		adicCampo(txtTN3, 210,25,97,20, "TN3", "T3", ListaCampos.DB_SI, false);
		adicCampo(txtTN4, 7,65,100,20,  "TN4", "T4", ListaCampos.DB_SI, false);
		adicCampo(txtTN5, 110,65,97,20, "TN5", "T5", ListaCampos.DB_SI, false);
		adicCampo(txtTN6, 210,65,97,20, "TN6", "T6", ListaCampos.DB_SI, false);
		adicCampo(txtTN7, 7,105,100,20, "TN7", "T7", ListaCampos.DB_SI, false);
		adicCampo(txtTN8, 110,105,97,20,"TN8", "T8", ListaCampos.DB_SI, false);
		adicCampo(txtTN9, 210,105,97,20,"TN9", "T9", ListaCampos.DB_SI, false);
        
		setPainel(pinIsento);
		adicCampo(txtVlrIsento, 350,45,130,20, "TIsencao", "Tot. isenção", ListaCampos.DB_SI, false);
		adicCampo(txtVlrNI, 350,85,130,20, "TNIncidencia", "Tot. de não incidência", ListaCampos.DB_SI, false);
		adicCampo(txtVlrSubst, 350,125,130,20, "TSubstituicao", "Tot. de substituição", ListaCampos.DB_SI, false);

		setListaCampos( false, "LEITURAX", "PV");

        pnMapa.add(spnTab);
		adicTab("Mapa fiscal", pnMapa);
		
		montaTabela();
		
		btExec.addActionListener(this); 

    }
    private void montaTabela() {
    	tab.adicColuna("%Aliq");
		tab.adicColuna("Total isenção");
		tab.adicColuna("Total não incidência");
		tab.adicColuna("Total substituição");
		tab.adicColuna("Total sangria");
		tab.adicColuna("Total suprimento");
		tab.adicColuna("Grande total");
		tab.adicColuna("Valor contábil");
		tab.adicColuna("Base de cálculo");
		tab.adicColuna("Valor do imposto");
		tab.adicColuna("Total cancelamentos");
		tab.adicColuna("Total descontos");
		tab.adicColuna("Nº cancelamentos");
		tab.adicColuna("Nº reduções");
		tab.adicColuna("Contador");
		tab.adicColuna("Ultimo cupom");
		
		tab.setTamColuna(70,0);  
		tab.setTamColuna(100,1);  
		tab.setTamColuna(120,2);
		tab.setTamColuna(100,3);
		tab.setTamColuna(90,4);
		tab.setTamColuna(100,5);
		tab.setTamColuna(90,6);
		tab.setTamColuna(100,7);
		tab.setTamColuna(110,8);
		tab.setTamColuna(120,9);
		tab.setTamColuna(130,10);
		tab.setTamColuna(110,11);
		tab.setTamColuna(130,12);
		tab.setTamColuna(100,13);
		tab.setTamColuna(80,14);
		tab.setTamColuna(100,15);
    }
    private void buscaAliquotas() {
		String sAliquota = "";
		if (AplicativoPDV.bECFTerm) {
		   String sAliquotas = (bf.retornaAliquotas(Aplicativo.strUsuario,AplicativoPDV.bModoDemo)+"").trim();
		   int iTot = (((sAliquotas.length())+1)/5);
		   for (int i=1;i<=iTot;i++) {
		      sAliquota = sAliquotas.substring((5*i)-5,(5*i)-3)+"."+sAliquotas.substring((5*i)-3,(5*i)-1);
 		      lcCampos.getCampo("Aliq"+Funcoes.strZero(""+(i),2)).setVlrDouble(new Double(sAliquota));
	       }
		}
    }

    private void carregaContadores(){        
    	String sSqlPrimCupom = "SELECT FIRST 1 DOCVENDA FROM VDVENDA WHERE CODEMP=? AND "+
    	                       "CODFILIAL=? AND CODEMPCX=? AND CODFILIALCX=? AND CODCAIXA=? AND "+
							   "DTEMITVENDA=? AND TIPOVENDA='E' ORDER BY DTEMITVENDA,DOCVENDA";

	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    try {
		  ps = con.prepareStatement(sSqlPrimCupom);
		  ps.setInt(1,Aplicativo.iCodEmp);
		  ps.setInt(2,ListaCampos.getMasterFilial("VDVENDA"));
          ps.setInt(3,Aplicativo.iCodEmp);
          ps.setInt(4,Aplicativo.iCodFilial);
		  ps.setInt(5,AplicativoPDV.iCodCaixa);
          ps.setDate(6,Funcoes.dateToSQLDate(txtDataX.getVlrDate()));
		  rs = ps.executeQuery();

		  if (rs.next()) {
		     txtPrimCupom.setVlrInteger(new Integer(rs.getInt("DOCVENDA")));    
		  }
		  else {
		  	 txtPrimCupom.setVlrInteger(new Integer(0));
		  }
		  rs.close();
		  ps.close();
	    }
	    catch (SQLException err) {
		  Funcoes.mensagemErro(this,"Erro ao buscar primeiro cupom!\n"+err.getMessage(),true,con,err);
	    }
    	
		if (AplicativoPDV.bECFTerm) {
            txtRed.setVlrInteger(new Integer (bf.numeroReducoes(Aplicativo.strUsuario,AplicativoPDV.bModoDemo)));
            txtUltCupom.setVlrInteger(new Integer (bf.numeroCupom(Aplicativo.strUsuario,AplicativoPDV.bModoDemo)-1));
            txtCanc.setVlrInteger(new Integer (bf.numeroCancelados(Aplicativo.strUsuario,AplicativoPDV.bModoDemo)));
            txtRed.setVlrInteger(new Integer (bf.numeroReducoes(Aplicativo.strUsuario,AplicativoPDV.bModoDemo)));
		}
		else {
			txtRed.setVlrInteger(new Integer(0));
			txtUltCupom.setVlrInteger(new Integer(0));
			txtCanc.setVlrInteger(new Integer(0));
			txtRed.setVlrInteger(new Integer(0));
		}
			
			
    	
    }
    private void carregaTotalizadores(){
		if (AplicativoPDV.bECFTerm) {
			String sTotalizadores[] = (bf.retornaTotalizadores(Aplicativo.strUsuario,AplicativoPDV.bModoDemo)+"").trim().split(",");
			String sTot = "";
			
			sTot = sTotalizadores[5].substring(0,12)+"."+sTotalizadores[5].substring(12);
            txtTSangria.setVlrDouble(new Double(sTot));
			sTot = sTotalizadores[6].substring(0,12)+"."+sTotalizadores[6].substring(12);
            txtTSuprimento.setVlrDouble(new Double(sTot));
            txtVlrCanc.setVlrDouble(new Double(bf.cancelamentos(Aplicativo.strUsuario,AplicativoPDV.bModoDemo)));
            txtVlrDesc.setVlrDouble(new Double(bf.descontos(Aplicativo.strUsuario,AplicativoPDV.bModoDemo)));
			sTot = sTotalizadores[7].substring(0,16)+"."+sTotalizadores[7].substring(16);
            txtTotal.setVlrBigDecimal(new BigDecimal(sTot));

            //Totalizadores fiscais:
			for (int i=0;i<(224-13);i+=14) {
			      sTot = sTotalizadores[0].substring(i,i+12)+"."+sTotalizadores[0].substring(i+12,i+14);
			      lcCampos.getCampo("TT"+Funcoes.strZero(""+((i/14)+1),2)).setVlrDouble(new Double(sTot));
			}
			
			//Totalizadores não fiscais:
			for (int i=0;i<(126-13);i+=14) {
			      sTot = sTotalizadores[4].substring(i,i+12)+"."+sTotalizadores[4].substring(i+12,i+14);
			      lcCampos.getCampo("TN"+((i/14)+1)).setVlrDouble(new Double(sTot));
			}
			sTot = sTotalizadores[1].substring(0,12)+"."+sTotalizadores[1].substring(12);
            txtVlrIsento.setVlrDouble(new Double(sTot));
			sTot = sTotalizadores[2].substring(0,12)+"."+sTotalizadores[2].substring(12);
            txtVlrNI.setVlrDouble(new Double(sTot));
			sTot = sTotalizadores[3].substring(0,12)+"."+sTotalizadores[3].substring(12);
            txtVlrSubst.setVlrDouble(new Double(sTot));
		}
		else {
			txtVlrCanc.setVlrInteger(new Integer(0));
			txtVlrDesc.setVlrInteger(new Integer(0));
			txtTotal.setVlrInteger(new Integer(0));
		}    

    }
    
	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == btExec) {
			buscaAliquotas();
			carregaContadores();
			carregaTotalizadores();
		}
		super.actionPerformed(evt);
	}
	
	public boolean gravaReducaoZ( Date data, int codCaixa ) {
		
		try {
			
			lcCampos.insert( false );
			txtDataX.setVlrDate( data );
			txtNumTermX.setVlrInteger( new Integer( codCaixa ) );
			lcCaixa.carregaDados();
			buscaAliquotas();
			carregaContadores();
			carregaTotalizadores();
			lcCampos.post();
			
		} catch ( Exception e ) {
			Funcoes.mensagemErro( null, "Erro ao gravar Redução Z\n" + e.getMessage() );
			e.printStackTrace();
			return false;
		}
		
		return true;
		
	}
	
	public void setConexao(Connection cn) {
		super.setConexao(cn);
		lcCampos.setConexao(cn);
		lcCaixa.setConexao(cn);
/*		if (verifCaixa())
		  carregaInfo();*/
	}
}
