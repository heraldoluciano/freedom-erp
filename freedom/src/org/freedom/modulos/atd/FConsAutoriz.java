/**
 * @version 30/07/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.atd <BR>cbConveniado
 * Classe: @(#)FConsAutoriz.java <BR>
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
 * Formulário de consulta de orçamento.
 * 
 */

package org.freedom.modulos.atd;
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
import java.util.Date;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JPanelPad;
import javax.swing.JScrollPane;

import org.freedom.bmps.Icone;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Tabela;
import org.freedom.funcoes.Funcoes;
import org.freedom.modulos.std.FOrcamento;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FFilho;

public class FConsAutoriz extends FFilho implements ActionListener {
	private static final long serialVersionUID = 1L;	
	private JPanelPad pinCab = new JPanelPad(0,250);
	private JPanelPad pnCli = new JPanelPad(JPanelPad.TP_JPANEL,new BorderLayout());
	private JTextFieldPad txtCodCli = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
	private JTextFieldFK txtNomeCli = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);	
	private JTextFieldPad txtCodConv = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
	private JTextFieldFK txtNomeConv = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
	private JTextFieldPad txtCodEnc = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
	private JTextFieldPad txtNomeEnc = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
	private JTextFieldPad txtDtIni = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
	private JTextFieldPad txtDtFim = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
	private JTextFieldPad txtCid = new JTextFieldPad(JTextFieldPad.TP_STRING,30,0);
	private JTextFieldPad txtCodTpConv = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
	private JTextFieldFK txtDescTipoConv = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
	
	private JLabelPad lbBorda = new JLabelPad();
	private JCheckBoxPad cbVencidas = new JCheckBoxPad("Vencidas","S","N");
	private JCheckBoxPad cbCompleto = new JCheckBoxPad("Completo","S","N");
	private JCheckBoxPad cbLiberado = new JCheckBoxPad("Liberado","S","N");
	private JCheckBoxPad cbFaturado = new JCheckBoxPad("Faturado","S","N");
	private JRadioGroup<?, ?> gbVenc;
	private Tabela tab = new Tabela();
	private JButton btBusca = new JButton("Buscar",Icone.novo("btPesquisa.gif"));
	private JButton btPrevimp = new JButton ("Imprimir",Icone.novo("btPrevimp.gif"));
	private JScrollPane spnTab = new JScrollPane(tab);
	private ListaCampos lcConv = new ListaCampos(this,"PR");
	private ListaCampos lcCli = new ListaCampos(this,"CL");
	private ListaCampos lcEnc = new ListaCampos(this,"EC");
	private ListaCampos lcTipoConv = new ListaCampos(this,"AT");
	BigDecimal bTotalLiq = null;
	
	public FConsAutoriz() {
		super(false);
		setTitulo("Pesquisa Autorização");
		setAtribos(10,10,605,505);
		cbVencidas.setVlrString("S");
		
		txtDtIni.setRequerido(true);
		txtDtFim.setRequerido(true);
		txtDtIni.setVlrDate(new Date());
		txtDtFim.setVlrDate(new Date());
		lbBorda.setBorder( BorderFactory.createEtchedBorder() );
		
		Vector<String> vVals = new Vector<String>();
		vVals.addElement("D");
		vVals.addElement("V");
		Vector<String> vLabs = new Vector<String>();
		vLabs.addElement("Data de emissão");
		vLabs.addElement("Data de venc. da autorização");
		gbVenc = new JRadioGroup<String, String>(2,1,vLabs,vVals);
		
		lcConv.add(new GuardaCampo( txtCodConv, "CodConv", "Cód.conv", ListaCampos.DB_PK ,false));
		lcConv.add(new GuardaCampo( txtNomeConv, "NomeConv", "Nome do conveniado", ListaCampos.DB_SI, false));
		txtCodConv.setTabelaExterna(lcConv);
		txtCodConv.setNomeCampo("CodConv");
		txtCodConv.setFK(true);
		lcConv.setReadOnly(true);
		lcConv.montaSql(false, "CONVENIADO", "AT");
		
		lcCli.add(new GuardaCampo( txtCodCli, "CodCli", "Cód.cli.", ListaCampos.DB_PK ,false));
		lcCli.add(new GuardaCampo( txtNomeCli, "NomeCli", "Razão social do cliente", ListaCampos.DB_SI, false));
		txtCodCli.setTabelaExterna(lcCli);
		txtCodCli.setNomeCampo("CodCli");
		txtCodCli.setFK(true);
		lcCli.setReadOnly(true);
		lcCli.montaSql(false, "CLIENTE", "VD");	
		
		
		lcTipoConv.add(new GuardaCampo( txtCodTpConv, "CodTpConv", "Cód.tp.conv.", ListaCampos.DB_PK, false));
		lcTipoConv.add(new GuardaCampo( txtDescTipoConv, "DescTpConv", "Descrição do tipo de conveniado", ListaCampos.DB_SI, false));
		txtCodTpConv.setTabelaExterna(lcTipoConv);
		txtCodTpConv.setNomeCampo("CodTpConv");
		txtCodTpConv.setFK(true);
		lcTipoConv.setReadOnly(true);
		lcTipoConv.montaSql(false, "TIPOCONV", "AT");		
		
		lcEnc.add(new GuardaCampo( txtCodEnc, "CodEnc", "Cód.enc.", ListaCampos.DB_PK, false));
		lcEnc.add(new GuardaCampo( txtNomeEnc, "NomeEnc", "Nome do encaminhador", ListaCampos.DB_SI, false));
		txtCodEnc.setTabelaExterna(lcEnc);
		txtCodEnc.setNomeCampo("CodEnc");
		txtCodEnc.setFK(true);
		lcEnc.setReadOnly(true);
		lcEnc.montaSql(false, "ENCAMINHADOR", "AT");
		
		getTela().add(pnCli,BorderLayout.CENTER);
		pnCli.add(pinCab,BorderLayout.NORTH);
		pnCli.add(spnTab,BorderLayout.CENTER);
		adicBotaoSair();		
		
		pinCab.adic(new JLabelPad("Cód.cli."),7,0,280,20);
		pinCab.adic(txtCodCli,7,20,70,20);
		pinCab.adic(new JLabelPad("Razão social do cliente"),80,0,280,20);
		pinCab.adic(txtNomeCli,80,20,294,20);
		
		pinCab.adic(new JLabelPad("Cód.conv."),7,40,280,20);
		pinCab.adic(txtCodConv,7,60,70,20);
		pinCab.adic(new JLabelPad("Nome do conveniado"),80,40,280,20);
		pinCab.adic(txtNomeConv,80,60,294,20);
		
		
		
		
		pinCab.adic(new JLabelPad("Cód.tp.conv."),7,80,250,20);
		pinCab.adic(txtCodTpConv,7,100,70,20);
		pinCab.adic(new JLabelPad("Descrição do tipo de conveniado"),80,80,250,20);
		pinCab.adic(txtDescTipoConv,80,100,294,20);
		
		pinCab.adic(new JLabelPad("Cód.enc."),7,122,250,20);
		pinCab.adic(txtCodEnc,7,142,70,20);
		pinCab.adic(new JLabelPad("Descrição do Encaminhador"),80,122,250,20);
		pinCab.adic(txtNomeEnc,80,142,294,20);
		
						
		pinCab.adic(new JLabelPad("Período:"),380,0,90,20);
		pinCab.adic(txtDtIni,380,20,87,20);
		pinCab.adic(new JLabelPad("Até"),470,20,27,20);
		pinCab.adic(txtDtFim,494,20,87,20);		
		 
		pinCab.adic(new JLabelPad("Cidade"),380,40,50,20);
		pinCab.adic(txtCid,380,60,200,20);			
		
								
		pinCab.adic(new JLabelPad("Status:"),7,164,90,20);
		pinCab.adic(lbBorda,7,183,200,55);
		pinCab.adic(cbVencidas,10,188,80,20);
		pinCab.adic(cbCompleto,10,213,80,20);
		pinCab.adic(cbLiberado,123,188,80,20);
		pinCab.adic(cbFaturado,123,213,80,20);	
		
		
		pinCab.adic(new JLabelPad("Filtrar por:"),225,164,110,20);
		pinCab.adic(gbVenc,225,183,220,55);
			
		pinCab.adic(btBusca,440,90,140,30);
		
		pinCab.adic(btPrevimp,440,130,140,30);
				
		tab.adicColuna("Cód.orc");
		tab.adicColuna("Emissão.");
		tab.adicColuna("Vcto.Orc.");
		tab.adicColuna("Valid.Autoriz.");
		tab.adicColuna("N.Autorização");
		tab.adicColuna("Cod.Conv.");
		tab.adicColuna("Nome do Conveniado");
		tab.adicColuna("End.Conveniado");
		tab.adicColuna("N.Resid.");
		tab.adicColuna("Bairro");
		tab.adicColuna("Cidade");
		tab.adicColuna("DDD ");
		tab.adicColuna("Fone ");
		tab.adicColuna("Cod.Prod.");
		tab.adicColuna("Cod.Barras");
		tab.adicColuna("Desc.Produto");
		tab.adicColuna("Qtd.");
		tab.adicColuna("Vlr.Item");
		
		tab.setTamColuna(70,0);
		tab.setTamColuna(70,1);
		tab.setTamColuna(70,2);
		tab.setTamColuna(70,3);
		tab.setTamColuna(110,4);
		tab.setTamColuna(70,5);
		tab.setTamColuna(200,6);
		tab.setTamColuna(200,7);
		tab.setTamColuna(50,8);
		tab.setTamColuna(100,9);
		tab.setTamColuna(100,10);
		tab.setTamColuna(50,11);
		tab.setTamColuna(90,12);
		tab.setTamColuna(70,13);
		tab.setTamColuna(100,14);
		tab.setTamColuna(250,15);
		tab.setTamColuna(70,16);
		tab.setTamColuna(70,17);
			
		btBusca.addActionListener(this);		
		btPrevimp.addActionListener(this);			
			
		tab.addMouseListener( new MouseAdapter() {
			public void mouseClicked(MouseEvent mevt) {
				if (mevt.getSource()==tab && mevt.getClickCount()==2)
					abreOrc();
			}
		});
	}
	/**
	 * 
	 * Carrega os valores para a tabela de consulta.
	 * Este método é executado  após carregar o ListaCampos da tabela.
	 *
	 */ 
	private void carregaTabela() {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sSQL = null;
		String sWhere = "";
		String sStatusOrc = "";
		int iLin = 0;
		int iParam = 5;
		boolean bConv = (txtCodConv.getVlrInteger().intValue() > 0);
		boolean bCli = (txtCodCli.getVlrInteger().intValue() > 0);
		boolean bEnc = (txtCodEnc.getVlrInteger().intValue()> 0); 
		
		if (cbVencidas.getVlrString().equals("S"))
			sWhere = " AND IT.VENCAUTORIZORC < CAST ('today' AS DATE) "; 
		
		if (cbCompleto.getVlrString().equals("S")) 
			sStatusOrc = "'OC'";
		if (cbLiberado.getVlrString().equals("S"))
			sStatusOrc += (!sStatusOrc.equals("")?",":"")+"'OL'";
		if (cbFaturado.getVlrString().equals("S"))
			sStatusOrc += (!sStatusOrc.equals("")?",":"")+"'OV'";
		if (!sStatusOrc.equals(""))
			sWhere += " AND O.STATUSORC IN ("+sStatusOrc+") ";    
		
		if (gbVenc.getVlrString().equals("V"))
			sWhere += " AND IT.VENCAUTORIZORC BETWEEN ? AND ?";
		else
			sWhere += " AND O.DTORC BETWEEN ? AND ?";
		
		if (bConv)
			sWhere += " AND O.CODCONV=? AND O.CODEMPCV=O.CODEMP AND O.CODFILIALCV=O.CODFILIAL ";		  
		if (bCli)  
			sWhere += " AND O.CODCLI=? AND O.CODEMPCV=O.CODEMP AND CODFILIALCV=O.CODFILIAL ";			
		if (bEnc)
			sWhere += " AND O.CODCONV=C.CODCONV AND O.CODEMPCV=C.CODEMP AND O.CODFILIALCV=C.CODFILIAL " +
					  "AND C.CODENC="+txtCodEnc.getVlrString()+" AND C.CODEMPEC=O.CODEMP AND C.CODFILIALEC="+lcEnc.getCodFilial()+"";
				
		if (!txtCid.getVlrString().equals(""))
			sWhere += " AND C.CIDCONV  = '"+txtCid.getVlrString()+"'";
		
		if (!txtCodTpConv.getVlrString().trim().equals("")){
			sWhere += "AND EXISTS(SELECT T2.CODTPCONV FROM ATTIPOCONV T2, ATCONVENIADO C2 WHERE " +
					  "T2.CODEMP=C2.CODEMPTC AND T2.CODFILIAL=C2.CODFILIALTC AND T2.CODTPCONV=C2.CODTPCONV AND "+
					  "C2.CODEMP=O.CODEMPCV AND C2.CODFILIAL=O.CODFILIALCV AND C2.CODCONV=O.CODCONV AND " +
					  "T2.CODEMP="+Aplicativo.iCodEmp+" AND T2.CODFILIAL="+ListaCampos.getMasterFilial("ATTIPOCONV")+" AND " +
	            	  "T2.CODTPCONV="+txtCodTpConv.getVlrString().trim()+" ) ";		
		}
		try {
			
			sSQL="SELECT  O.CODORC,O.DTORC,O.DTVENCORC,IT.VENCAUTORIZORC,IT.NUMAUTORIZORC,"+
				"O.CODCONV,C.NOMECONV,c.endconv,C.NUMCONV, C.BAIRCONV, c.cidconv, C.DDDCONV, C.FONECONV , "+
				"IT.CODPROD, P.CODBARPROD,P.DESCPROD,it.qtditorc, it.vlrliqitorc,     "+
				"(SELECT EC.NOMEENC FROM ATENCAMINHADOR EC WHERE EC.CODENC=C.CODENC AND "+
				"EC.CODEMP=C.CODEMPEC AND EC.CODFILIAL=C.CODFILIALEC) "+
				"FROM VDORCAMENTO O,VDCLIENTE CL,"+
				"ATCONVENIADO C, EQPRODUTO P, VDITORCAMENTO IT WHERE O.CODEMP=? "+
				"AND O.CODFILIAL=? AND O.TIPOORC='O' "+
				"AND IT.CODORC=O.CODORC AND IT.CODEMP=O.CODEMP AND IT.CODFILIAL=O.CODFILIAL " +
				"AND IT.TIPOORC=O.TIPOORC "+ 
				"AND CL.CODEMP=O.CODEMPCL AND CL.CODFILIAL=O.CODFILIALCL "+
				"AND CL.CODCLI=O.CODCLI  AND C.CODEMP=O.CODEMPCV AND C.CODFILIAL=O.CODFILIALCV "+
				"AND O.CODCONV=C.CODCONV "+					
				"AND P.CODPROD=IT.CODPROD AND P.CODEMP=IT.CODEMPPD AND " +
				"P.CODFILIAL=IT.CODFILIALPD " +sWhere+" ORDER BY O.CODORC"; 
			
			ps = con.prepareStatement(sSQL);
			ps.setInt(1,Aplicativo.iCodEmp);
			ps.setInt(2,ListaCampos.getMasterFilial("VDORCAMENTO"));
			ps.setDate(3,Funcoes.dateToSQLDate(txtDtIni.getVlrDate()));
			ps.setDate(4,Funcoes.dateToSQLDate(txtDtFim.getVlrDate()));
			if (bConv)
				ps.setInt(iParam++,txtCodConv.getVlrInteger().intValue());
			if (bCli)
				ps.setInt(iParam,txtCodCli.getVlrInteger().intValue());			
			rs = ps.executeQuery();
			tab.limpa();
			bTotalLiq = new BigDecimal( "0" );
			while (rs.next()) {
				tab.adicLinha();
				tab.setValor(String.valueOf(rs.getInt(1)), iLin, 0);
				tab.setValor(Funcoes.sqlDateToStrDate(rs.getDate(2)), iLin, 1);
				tab.setValor(Funcoes.sqlDateToStrDate(rs.getDate(3)), iLin, 2);
				tab.setValor(Funcoes.sqlDateToStrDate(rs.getDate(4)), iLin, 3);
				tab.setValor(rs.getString(5)!= null ? rs.getString(5) : "", iLin, 4);
				tab.setValor(String.valueOf(rs.getInt(6)), iLin,5);
				tab.setValor(rs.getString(7)!= null ? rs.getString(7) : "", iLin, 6);
				tab.setValor(rs.getString(8) != null ? rs.getString(8) : "", iLin, 7);
				tab.setValor(String.valueOf(rs.getInt(9)), iLin,8);
				tab.setValor(rs.getString(10) != null ? rs.getString(10) : "", iLin, 9);
				tab.setValor(rs.getString(11) != null ? rs.getString(11) : "", iLin, 10);
				tab.setValor(String.valueOf(rs.getInt(12)), iLin,11);
				tab.setValor(rs.getString(13)!= null ? Funcoes.setMascara(rs.getString(13),"####-####") : "", iLin, 12);
				tab.setValor(String.valueOf(rs.getInt(14)), iLin,13);
				tab.setValor(String.valueOf(rs.getInt(15)), iLin,14);
				tab.setValor(rs.getString(16)!= null ? rs.getString(16) : "", iLin, 15);
				tab.setValor(rs.getString(17) != null ? rs.getString(17) : "", iLin, 16);
				tab.setValor(rs.getString(18) != null ? rs.getString(18) : "", iLin, 17);
				
				bTotalLiq = bTotalLiq.add( new BigDecimal( rs.getString("vlrliqitorc")));
				iLin++;
				
				
			}
			
		
			
			if (!con.getAutoCommit())
				con.commit();
		} catch (SQLException err) {
			Funcoes.mensagemErro(this,"Erro ao carregar a tabela VDORÇAMENTO!\n"+err.getMessage(),true,con,err);
			err.printStackTrace();
		}
	}
	
	
	private void imprimir(boolean bVisualizar) {
		ImprimeOS imp = new ImprimeOS("",con);
		int linPag = imp.verifLinPag()-1;
		imp.montaCab();
		imp.setTitulo("Relatório de Orçamentos");
						
		try {
			imp.limpaPags();
			for (int iLin=0;iLin<tab.getNumLinhas();iLin++) {
				if (imp.pRow()>=linPag) {			
					imp.say(imp.pRow() + 1, 0, imp.comprimido());
					imp.say(imp.pRow(), 0, "+" + Funcoes.replicate("-",133) + "+");
					imp.incPags();
					imp.eject();
				}
				if (imp.pRow()==0) {
					imp.impCab(136, true);
					imp.say(imp.pRow(),0, imp.comprimido());
					imp.say(imp.pRow(),0, "|" + Funcoes.replicate("=",133) + "|");
					imp.say(imp.pRow()+1, 0, imp.comprimido());
					imp.say(imp.pRow(),  0, "|Cod.Orc.");
					imp.say(imp.pRow(), 05, "|Emissão");
					imp.say(imp.pRow(), 14, "|Vcto.Orc.");
					imp.say(imp.pRow(), 24, "|Valid.Autoriz.");
					imp.say(imp.pRow(), 40, "|N.Autorização");
					imp.say(imp.pRow(), 53, "|Cod.Conv.");
					imp.say(imp.pRow(), 64, "|Nome do Conveniado");
					imp.say(imp.pRow(), 112,"|End.Conveniado");
                    imp.say(imp.pRow(), 124,"|N.Resid."); 					
					imp.say(imp.pRow(), 130,"|Bairro");
					imp.say(imp.pRow(), 175,"|Cidade" );
					imp.say(imp.pRow(), 190,"|DDD");
					imp.say(imp.pRow(), 200,"|Fone");
					imp.say(imp.pRow(), 215,"|Cod.Prod.");
					imp.say(imp.pRow(), 235,"|Cod.Barras");
					imp.say(imp.pRow(), 245,"|Desc.Prod.");
					imp.say(imp.pRow(), 300,"|Qtd.");
					imp.say(imp.pRow(), 315,"|Vlr.Item");
					imp.say(imp.pRow(),180, "|");					
					imp.say(imp.pRow()+1, 0, imp.comprimido());
					imp.say(imp.pRow(), 0, "|" + Funcoes.replicate("-",133) + "|");
				} 
				imp.say(imp.pRow()+1, 0, imp.comprimido());
				imp.say(imp.pRow(),  0, "| " + tab.getValor(iLin,0));
				imp.say(imp.pRow(), 05, "| " + tab.getValor(iLin,1));
				imp.say(imp.pRow(), 14, "| " + tab.getValor(iLin,2));
				imp.say(imp.pRow(), 24, "| " + tab.getValor(iLin,3));
				imp.say(imp.pRow(), 40, "| " + Funcoes.copy((String)tab.getValor(iLin,4),11));				
				imp.say(imp.pRow(), 53, "| " + Funcoes.copy((String)tab.getValor(iLin,5),25));				
				imp.say(imp.pRow(), 64, "| " + Funcoes.copy((String)tab.getValor(iLin,6),25));
				imp.say(imp.pRow(),112, "| " + Funcoes.copy((String)tab.getValor(iLin,7),25));
				imp.say(imp.pRow(),124, "| " + tab.getValor(iLin,8));
				imp.say(imp.pRow(),130, "| " + Funcoes.copy((String)tab.getValor(iLin,9),15));
				imp.say(imp.pRow(),175, "| " + Funcoes.copy((String)tab.getValor(iLin,10),15));
				imp.say(imp.pRow(),190, "| " + Funcoes.copy((String)tab.getValor(iLin,11),10));
				imp.say(imp.pRow(),200, "| " + Funcoes.copy((String)tab.getValor(iLin,12),15));
				imp.say(imp.pRow(),215, "| " + tab.getValor(iLin,13));
				imp.say(imp.pRow(),235, "| " + tab.getValor(iLin,14));
				imp.say(imp.pRow(),245, "| " + Funcoes.copy((String)tab.getValor(iLin,15),25));
				imp.say(imp.pRow(),300, "| " + Funcoes.copy((String)tab.getValor(iLin,16),10));				
				imp.say(imp.pRow(),315, "| " + Funcoes.copy((String)tab.getValor(iLin,17),10));
								  
				imp.say(imp.pRow(),185, "|");
				
								
			}			
			imp.say(imp.pRow()+1, 0, imp.comprimido());
			imp.say(imp.pRow(), 0, "+" + Funcoes.replicate("=",133) + "+");
			imp.say( imp.pRow()+1, 103, " Total Geral | "    + Funcoes.strDecimalToStrCurrency( 11, 2, "" + bTotalLiq ) + "      |" );
			imp.say(imp.pRow()+1, 0, imp.comprimido());
			imp.say(imp.pRow(), 0, "+" + Funcoes.replicate("=",133) + "+");
			
			imp.eject();			
			imp.fechaGravacao();
			
			if(!con.getAutoCommit())
				con.commit();
				
		} catch ( SQLException err ) {
			Funcoes.mensagemErro(this,"Erro consulta tabela de orçamentos!"+err.getMessage(),true,con,err);      
		}
		
		if (bVisualizar)
			imp.preview(this);
		else 
			imp.print();
	}
	
	private void abreOrc() {
		int iCodOrc = ((Integer)tab.getValor(tab.getLinhaSel(),1)).intValue();
		if (fPrim.temTela("Orcamento")==false) {
			FOrcamento tela = new FOrcamento();
			fPrim.criatela("Orcamento",tela,con);
			tela.exec(iCodOrc);
		} 
	}
	
	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == btBusca) {
			if (txtDtIni.getVlrString().length() < 10)
				Funcoes.mensagemInforma(this,"Digite a data inicial!");
			else if (txtDtFim.getVlrString().length() < 10)
				Funcoes.mensagemInforma(this,"Digite a data final!");
			else
				carregaTabela();
		}
		if (evt.getSource()==btPrevimp)
			imprimir(true);
	}
		
	public void setConexao(Connection cn) {
		super.setConexao(cn);
		lcConv.setConexao(con);
		lcCli.setConexao(con);
		lcEnc.setConexao(con);
		lcTipoConv.setConexao( con );
		
	}
}
