/**
 * @version 11/02/2002 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FLiberaCredito.java <BR>
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
 * Formulário de liberação de crédito por pedido de venda.
 */

package org.freedom.modulos.std;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import org.freedom.componentes.JLabelPad;
import javax.swing.JScrollPane;

import org.freedom.acao.DeleteEvent;
import org.freedom.acao.DeleteListener;
import org.freedom.acao.InsertEvent;
import org.freedom.acao.InsertListener;
import org.freedom.acao.PostEvent;
import org.freedom.acao.PostListener;
import org.freedom.bmps.Icone;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.Tabela;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FDados;

public class FLiberaCredito extends FDados implements ActionListener,InsertListener,DeleteListener,PostListener {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtCodLib = new JTextFieldPad(JTextFieldPad.TP_INTEGER,5,0);
	private JTextFieldPad txtCodCli = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
	private JTextFieldFK txtRazCli = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
	private JTextFieldPad txtVlrLibCred = new JTextFieldPad(JTextFieldPad.TP_DECIMAL,15,2);
	private JTextFieldPad txtDtVencLCred = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
    private JTextFieldPad txtCodVenda = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);

    private JTextFieldFK txtCodTpCred = new JTextFieldFK(JTextFieldPad.TP_INTEGER,5,0);
    private JTextFieldFK txtDescTpCred = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
    private JTextFieldFK txtVlrTpCred = new JTextFieldFK(JTextFieldPad.TP_DECIMAL,15,2);

    private JTextFieldFK txtVlrAberto = new JTextFieldFK(JTextFieldPad.TP_DECIMAL,15,2);
    private JTextFieldFK txtVlrLiberacao = new JTextFieldFK(JTextFieldPad.TP_DECIMAL,15,2);
    private JTextFieldFK txtVlrCredito = new JTextFieldFK(JTextFieldPad.TP_DECIMAL,15,2);
    private JTextFieldFK txtVlrALiberar = new JTextFieldFK(JTextFieldPad.TP_DECIMAL,15,2);
	private JTextFieldFK txtVlrSaldo = new JTextFieldFK(JTextFieldPad.TP_DECIMAL,15,2);

//	private JTextFieldPad txtDtVencto = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
	private ListaCampos lcCli = new ListaCampos(this,"CL");
    private ListaCampos lcTipoCred = new ListaCampos(this,"TR");
	private JPanelPad pinCab = new JPanelPad(0,140);
    private JPanelPad pinCli = new JPanelPad(0,200);	
    private JButton btBusca = new JButton(Icone.novo("btExecuta.gif"));
    private Tabela tab = new Tabela();
    private JScrollPane spnTab = new JScrollPane(tab);
	public FLiberaCredito() {
		super();
		setTitulo("Liberação de crédito");
		setAtribos(10,10,650,400);
		
  	 // Mecanismo de busca e validação de clientes
		lcCli.add(new GuardaCampo( txtCodCli, "CodCli", "Cód.cli.", ListaCampos.DB_PK, true));
		lcCli.add(new GuardaCampo( txtRazCli, "RazCli", "Razão social do cliente", ListaCampos.DB_SI, false));
		lcCli.add(new GuardaCampo( txtCodTpCred, "CodTpCred", "Cód.tp.créd.", ListaCampos.DB_FK, txtDescTpCred, false)); 
		lcCli.montaSql(false, "CLIENTE","VD");    
		lcCli.setQueryCommit(false);
		lcCli.setReadOnly(true);
		txtCodCli.setTabelaExterna(lcCli);
    
	 // Mecanismo de busca de informações de tipo de crédito 
		lcTipoCred.add(new GuardaCampo( txtCodTpCred, "CodTpCred", "Cód.tp.créd", ListaCampos.DB_PK, false));
    	lcTipoCred.add(new GuardaCampo( txtDescTpCred, "DescTpCred", "Descrição do tipo de credito", ListaCampos.DB_SI, false));
    	lcTipoCred.add(new GuardaCampo( txtVlrTpCred, "VlrTpCred", "Valor", ListaCampos.DB_SI, false));
    	lcTipoCred.montaSql(false, "TIPOCRED", "FN");    
    	lcTipoCred.setQueryCommit(false);
    	lcTipoCred.setReadOnly(true);
    	txtCodTpCred.setTabelaExterna(lcTipoCred);
    	txtCodTpCred.setNomeCampo("CodTpCred");
    	
 	 // Adicionando elementos no painel superior da tela (Cabeçalho)
 	    setPainel(pinCab,pnCliente);
	    adicCampo(txtCodLib, 7, 20, 80, 20, "CodLCred", "Nº liberação", ListaCampos.DB_PK, true);
		adicCampo(txtCodVenda, 90, 20, 77, 20, "CodVenda", "Pedido", ListaCampos.DB_SI, true);
		adicCampo(txtCodCli, 170, 20, 77, 20, "CodCli", "Cód.cli.", ListaCampos.DB_FK, true);
	    adicDescFK(txtRazCli, 250, 20, 200, 20, "RazCli", "Razão social do cliente");
		adic(new JLabelPad("Cód.tp.créd."), 7, 40, 80, 20);
		adic(txtCodTpCred, 7, 60, 80, 20);
		adicDescFK(txtDescTpCred, 90, 60, 197, 20, "DescTpCred", "Descrição do tipo de crédito");
		adicDescFK(txtVlrTpCred, 290, 60, 160, 20, "VlrTpCred", "Valor pré-aprovado");
		adicCampo(txtVlrLibCred, 7, 100, 120, 20, "VlrAutorizLCred", "Valor a liberar", ListaCampos.DB_SI, true);
		adicCampo(txtDtVencLCred, 130, 100, 120, 20, "DtaVctoLCred", "Vencimento", ListaCampos.DB_SI, true);
		setListaCampos( true, "LIBCRED", "FN");

		pinCab.adic(btBusca,260,95,30,30);

 	 // Adicionando elementos no painel central da tela (Cliente)

		pinCli.adic(new JLabelPad("Informações para análise"),7,0,200,20);
		
		pinCli.adic(new JLabelPad("Valor em aberto:"),7,30,120,20);
		pinCli.adic(txtVlrAberto,130,30,100,20);		
		pinCli.adic(new JLabelPad("Valor em liberação:"),7,60,120,20);
		pinCli.adic(txtVlrLiberacao,130,60,100,20);
		pinCli.adic(new JLabelPad("Valor do crédito:"),7,90,120,20);
		pinCli.adic(txtVlrCredito,130,90,100,20);
		pinCli.adic(new JLabelPad("Valor a liberar:"),7,120,120,20);
		pinCli.adic(txtVlrALiberar,130,120,100,20);
		pinCli.adic(new JLabelPad("Valor do saldo:"),7,150,120,20);
		pinCli.adic(txtVlrSaldo,130,150,100,20);


		pinCli.adic(new JLabelPad("Duplicatas em aberto"),260,0,200,20);
        pinCli.adic(spnTab,260,30,360,140);
        

 	 // Adicionando elementos no painel inferior da tela (Rodapé)

		pnCliente.add(pinCab,BorderLayout.NORTH);
        pnCliente.add(pinCli,BorderLayout.CENTER);

        tab.adicColuna("Vencto.");
		tab.adicColuna("Valor");
        tab.adicColuna("Série");
        tab.adicColuna("Doc");
        tab.adicColuna("Cód.venda");
        tab.adicColuna("Dt.venda.");
        tab.adicColuna("Dt.pagto.");
        tab.adicColuna("Valor pago.");
        tab.adicColuna("Atraso");
        tab.adicColuna("Observações");
        tab.adicColuna("Banco");
        tab.adicColuna("TV");
    
        tab.setTamColuna(90,0);
		tab.setTamColuna(100,1);
        tab.setTamColuna(50,2);
        tab.setTamColuna(80,3);
        tab.setTamColuna(90,4);
        tab.setTamColuna(90,5);
        tab.setTamColuna(90,6);
        tab.setTamColuna(100,7);
        tab.setTamColuna(60,8);
        tab.setTamColuna(200,9);
        tab.setTamColuna(200,10);
        tab.setTamColuna(20,11);

		btBusca.addActionListener(this);
		lcCampos.addInsertListener(this);
		lcCampos.addDeleteListener(this);
		
		tab.addMouseListener( 
		  new MouseAdapter() {
			public void mouseClicked(MouseEvent mevt) {
			  if (mevt.getSource() == tab) {
				if (mevt.getClickCount() == 2) {
				  int iLin = tab.getLinhaSel();
				  if (iLin >= 0) {
					int iCodVenda = Integer.parseInt((String)tab.getValor(iLin,4));
					String sTipoVenda = (String) tab.getValor(iLin,11);
					DLConsultaVenda dl = new DLConsultaVenda(FLiberaCredito.this,con,iCodVenda, sTipoVenda);
					dl.setVisible(true);
					dl.dispose();
				  }
				}
			  }
			}
		  }
		);

	}

	private void carregaGridConsulta() {
		tab.limpa();
		String sSQL = "SELECT IT.DTVENCITREC,IT.VLRPARCITREC,V.SERIE,R.DOCREC,V.CODVENDA,"+
					  "R.DATAREC,IT.DTPAGOITREC,IT.VLRPAGOITREC,"+
					  "(CAST('today' AS DATE)-IT.DTVENCITREC) AS ATRASO,"+
					  "R.OBSREC,(SELECT B.NOMEBANCO FROM FNBANCO B "+
					  "WHERE B.CODBANCO = R.CODBANCO) AS NOMEBANCO,"+
					  "R.CODREC,IT.NPARCITREC,R.TIPOENDA" +
					  " FROM FNRECEBER R, VDVENDA V, " +
					  "FNITRECEBER IT, VDCLIENTE CL WHERE " +
					  "((CL.CODCLI=? AND CL.CODEMP=? AND CL.CODFILIAL=?) OR " +
					  " (CL.CODPESQ=? AND CL.CODEMPPQ=? AND CL.CODFILIALPQ=?)) AND " +
					  "R.CODCLI=CL.CODCLI AND R.CODEMPCL=CL.CODEMP AND R.CODFILIALCL=CL.CODFILIAL AND " +
					  "V.CODVENDA=R.CODVENDA AND "+
					  "IT.STATUSITREC NOT IN ('RP') AND IT.CODREC = R.CODREC "+
					  "ORDER BY IT.DTVENCITREC,R.CODREC,IT.NPARCITREC";
		try {
		  PreparedStatement ps = con.prepareStatement(sSQL);
		  ps.setInt(1,txtCodCli.getVlrInteger().intValue());
		  ps.setInt(2,Aplicativo.iCodEmp);
		  ps.setInt(3,lcCli.getCodFilial());
		  ps.setInt(4,txtCodCli.getVlrInteger().intValue());
		  ps.setInt(5,Aplicativo.iCodEmp);
		  ps.setInt(6,lcCli.getCodFilial());
		  ResultSet rs = ps.executeQuery();
		  double dVal = 0;
		  for (int i=0; rs.next(); i++) {
			tab.adicLinha();
			tab.setValor((rs.getDate("DtVencItRec") != null ? Funcoes.sqlDateToStrDate(rs.getDate("DtVencItRec")) : ""),i,0);
			tab.setValor(Funcoes.strDecimalToStrCurrency(15,2,rs.getString("VlrParcItRec")),i,1);
			tab.setValor((rs.getString("Serie") != null ? rs.getString("Serie") : ""),i,2);
			tab.setValor((rs.getString("DocRec") != null ? rs.getString("DocRec") : ""),i,3);
			tab.setValor(""+rs.getInt("CodVenda"),i,4);
			tab.setValor((rs.getDate("DataRec") != null ? Funcoes.sqlDateToStrDate(rs.getDate("DataRec")) : ""),i,5);
			tab.setValor((rs.getDate("DtPagoItRec") != null ? Funcoes.sqlDateToStrDate(rs.getDate("DtPagoItRec")) : ""),i,6);
			tab.setValor(Funcoes.strDecimalToStrCurrency(15,2,rs.getString("VlrPagoItRec")),i,7);
			tab.setValor(rs.getString(9),i,8);
			tab.setValor(rs.getString("ObsRec") != null ? rs.getString("ObsRec") : "",i,9);
			tab.setValor(rs.getString(11) != null ? rs.getString(11) : "",i,10);
			tab.setValor(rs.getString("TIPOVENDA"),i,11);
			dVal += rs.getDouble("VlrParcItRec");
		  }
		  txtVlrAberto.setVlrString(Funcoes.strDecimalToStrCurrency(2,""+(new BigDecimal(dVal)).setScale(2,BigDecimal.ROUND_HALF_UP))); 
		  rs.close();
		  ps.close();
//  if (!con.getAutoCommit())
	// 	con.commit();
		}
		catch(SQLException err) {
			Funcoes.mensagemErro(this,"Erro ao montar a tabela de consulta!\n"+err.getMessage(),true,con,err);
		}
	}
	private void buscaVlrLib() {
		String sSQL =  "SELECT SUM(VLRAUTORIZLCRED) FROM FNLIBCRED WHERE CODEMP=?"+
                       " AND CODFILIAL=? AND CODCLI=?" +
                       " AND CODEMPCL=? AND CODFILIALCL=?" +
                       " AND DTAVCTOLCRED >= CAST('today' AS DATE)";
        try {
          PreparedStatement ps = con.prepareStatement(sSQL);
          ps.setInt(1,Aplicativo.iCodEmp);
          ps.setInt(2,ListaCampos.getMasterFilial("FNLIBCRED"));
          ps.setInt(3,txtCodCli.getVlrInteger().intValue());
		  ps.setInt(4,Aplicativo.iCodEmp);
          ps.setInt(5,lcCli.getCodFilial());
          ResultSet rs = ps.executeQuery();
          if (rs.next() && rs.getString(1) != null) {
          	txtVlrLiberacao.setVlrString(Funcoes.strDecimalToStrCurrency(2,rs.getString(1)));
          } 
        }
        catch(SQLException err) {
		  Funcoes.mensagemErro(this,"Erro ao buscar valor liberado!\n"+err.getMessage(),true,con,err);
		  err.printStackTrace();
        }                       
	}
	public void setConexao(Connection cn) {
        super.setConexao(cn);
	    lcCli.setConexao(cn);
        lcTipoCred.setConexao(cn);
	}
	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == btBusca) {
			limpa();
			txtVlrCredito.setVlrString(Funcoes.strDecimalToStrCurrency(2,""+txtVlrTpCred.getVlrBigDecimal().toString()));
			txtVlrALiberar.setVlrString(Funcoes.strDecimalToStrCurrency(2,""+txtVlrLibCred.getVlrBigDecimal().toString()));
			carregaGridConsulta();
			buscaVlrLib();
			txtVlrSaldo.setVlrString(
			  Funcoes.strDecimalToStrCurrency(2,""+
			    (
			      Funcoes.strCurrencyToDouble(txtVlrCredito.getVlrString()) -
			      (
			        Funcoes.strCurrencyToDouble(txtVlrAberto.getVlrString()) +
			        Funcoes.strCurrencyToDouble(txtVlrLiberacao.getVlrString()) +
			        Funcoes.strCurrencyToDouble(txtVlrALiberar.getVlrString())
			      )
			    )
		      )
			);
		}
		super.actionPerformed(evt);
	}
	public void limpa() {
		txtVlrAberto.setVlrString(Funcoes.strDecimalToStrCurrency(2,"0.00"));
		txtVlrLiberacao.setVlrString(Funcoes.strDecimalToStrCurrency(2,"0.00"));
		txtVlrCredito.setVlrString(Funcoes.strDecimalToStrCurrency(2,"0.00"));
		txtVlrALiberar.setVlrString(Funcoes.strDecimalToStrCurrency(2,"0.00"));
		txtVlrSaldo.setVlrString(Funcoes.strDecimalToStrCurrency(2,"0.00"));
		tab.limpa();
	}
	public void afterInsert(InsertEvent ievt) {
		if (ievt.getListaCampos() == lcCampos) {
			GregorianCalendar cal = new GregorianCalendar();
			cal.set(Calendar.DATE,cal.get(Calendar.DATE)+7); //Coloca o vencimento padrao de 7 dias. 
			txtDtVencLCred.setVlrDate(cal.getTime());
			limpa();
		}
  	}
	public void afterDelete(DeleteEvent devt) {
		if (devt.ok) {
			limpa();
		}
	}
	public void beforePost(PostEvent pevt) {
	  String sSQL = "SELECT CODCLI,RAZCLI,OBSCLI FROM VDCLIENTE WHERE" +
	  	            " OBSCLI IS NOT NULL AND " +		
	  				"((CODCLI=? AND CODEMP=? AND CODFILIAL=?) OR " +
	  				" (CODPESQ=? AND CODEMPPQ=? AND CODFILIALPQ=?))";
	  try {
	    PreparedStatement ps = con.prepareStatement(sSQL);
	    ps.setInt(1,txtCodCli.getVlrInteger().intValue());
	    ps.setInt(2,Aplicativo.iCodEmp);
	    ps.setInt(3,lcCli.getCodFilial());
	    ps.setInt(4,txtCodCli.getVlrInteger().intValue());
	    ps.setInt(5,Aplicativo.iCodEmp);
	    ps.setInt(6,lcCli.getCodFilial());
	    ResultSet rs = ps.executeQuery();
	    if (rs.next()) {
	    	DLConsultaObs dl = new DLConsultaObs(this,rs,con);
	    	dl.setVisible(true);
		    if (!dl.OK)
		    	pevt.cancela();
		    dl.dispose();	    	
	    }
	  }
	  catch(SQLException err) {
	  	Funcoes.mensagemErro(this,"Erro ao verificar observações!\n"+err.getMessage(),true,con,err);
	  	err.printStackTrace();
	  }
	  	
	}
	public void beforeInsert(InsertEvent ievt) { }
	public void beforeDelete(DeleteEvent devt) { }
	public void afterPost(PostEvent pevt) { }
}
