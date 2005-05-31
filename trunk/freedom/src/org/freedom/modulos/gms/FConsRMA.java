/**
 * @version 02/08/2003 <BR>
 * @author Setpoint Informática Ltda./Anderson Sanchez <BR>
 *         Projeto: Freedom <BR>
 *         Pacote: org.freedom.modulos.std <BR>
 *         Classe:
 * @(#)FConsOrc.java <BR>
 *                   Este programa é licenciado de acordo com a LPG-PC (Licença
 *                   Pública Geral para Programas de Computador), <BR>
 *                   versão 2.1.0 ou qualquer versão posterior. <BR>
 *                   A LPG-PC deve acompanhar todas PUBLICAÇÕES, DISTRIBUIÇÕES e
 *                   REPRODUÇÕES deste Programa. <BR>
 *                   Caso uma cópia da LPG-PC não esteja disponível junto com
 *                   este Programa, você pode contatar <BR>
 *                   o LICENCIADOR ou então pegar uma cópia em: <BR>
 *                   Licença: http://www.lpg.adv.br/licencas/lpgpc.rtf <BR>
 *                   Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou
 *                   ALTERAR este Programa é preciso estar <BR>
 *                   de acordo com os termos da LPG-PC <BR>
 *                   <BR>
 *                   Formulário de consulta de RMA.
 */

package org.freedom.modulos.gms;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
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

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import org.freedom.bmps.Icone;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Tabela;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FFilho;

public class FConsRMA extends FFilho implements ActionListener {

	private JPanelPad pinCab = new JPanelPad(0, 185);
	private JPanelPad pnCli = new JPanelPad(JPanelPad.TP_JPANEL,new BorderLayout());
	private JPanelPad pnRod = new JPanelPad(JPanelPad.TP_JPANEL,new BorderLayout());
	private JPanelPad pnLegenda = new JPanelPad(JPanelPad.TP_JPANEL,new GridLayout(0,4));
	private JTextFieldPad txtDtIni = new JTextFieldPad(JTextFieldPad.TP_DATE, 10,0);
	private JTextFieldPad txtDtFim = new JTextFieldPad(JTextFieldPad.TP_DATE, 10,0);
	private JCheckBoxPad cbPendentes = new JCheckBoxPad("Requisições pendentes", "S", "N");
//	private JCheckBoxPad cbCompletas = new JCheckBoxPad("Requisições completas", "S", "N");

	private JCheckBoxPad cbAprovadas = new JCheckBoxPad("Requisições aprovadas", "S", "N");
	private JCheckBoxPad cbExpedidas = new JCheckBoxPad("Requisições expedidas", "S", "N");
	private JCheckBoxPad cbCanceladas = new JCheckBoxPad("Requisições canceladas", "S", "N");
//	private JCheckBoxPad cbTomadasDePreco = new JCheckBoxPad("Cotações de preço", "S", "N");
	private JTextFieldPad txtCodUsu = new JTextFieldPad(JTextFieldPad.TP_STRING, 8, 0);
	private JTextFieldFK txtNomeUsu = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);
	private JTextFieldPad txtCodCC = new JTextFieldPad(JTextFieldPad.TP_STRING, 19, 0);
	private JTextFieldPad txtAnoCC = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 4, 0);
	private JTextFieldFK txtDescCC = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);
	private JTextFieldPad txtCodAlmoxarife = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 10, 0);
	private JTextFieldFK txtDescAlmoxarife = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0);
	private Tabela tab = new Tabela();
	private ImageIcon imgCancelada = Icone.novo("clVencido.gif");
	private ImageIcon imgExpedida = Icone.novo("clPago.gif");
	private ImageIcon imgAprovada = Icone.novo("clPagoParcial.gif");
	private ImageIcon imgPendente = Icone.novo("clNaoVencido.gif");
	private ImageIcon imgColuna = null;
	private JButton btBusca = new JButton("Buscar", Icone.novo("btPesquisa.gif"));
	private JButton btPrevimp = new JButton("Imprimir", Icone.novo("btPrevimp.gif"));
			JButton btSair = new JButton("Sair", Icone.novo("btSair.gif"));
	private JScrollPane spnTab = new JScrollPane(tab);
	private ListaCampos lcAlmox = new ListaCampos(this, "AM");
	private ListaCampos lcUsuario = new ListaCampos(this, "");
	private ListaCampos lcCC = new ListaCampos(this, "CC");
	boolean bAprovaParcial = false;
	boolean bExpede = false;
	boolean bAprova = false;
	String  sSitRMA = "";
	public FConsRMA() {
		setTitulo("Pesquisa Requisições de material");
		setAtribos(10, 10, 663, 480);

		txtDtIni.setRequerido(true);
		txtDtFim.setRequerido(true);

		txtCodAlmoxarife.setNomeCampo("CodAlmox");
		txtCodAlmoxarife.setFK(true);
		
		lcAlmox.add(new GuardaCampo(txtCodAlmoxarife, "CodAlmox", "Cód.almox.", ListaCampos.DB_PK, null, false));
		lcAlmox.add(new GuardaCampo(txtDescAlmoxarife, "DescAlmox", "Desc.almox;", ListaCampos.DB_SI, null, false));
		lcAlmox.setQueryCommit(false);
		lcAlmox.setReadOnly(true);

		txtDescAlmoxarife.setSoLeitura(true);
		txtCodAlmoxarife.setTabelaExterna(lcAlmox);
		lcAlmox.montaSql(false, "ALMOX", "EQ");

		txtCodUsu.setNomeCampo("IDUSU");
		txtCodUsu.setFK(true);

		lcUsuario.add(new GuardaCampo(txtCodUsu, "IDUSU", "ID usuario", ListaCampos.DB_PK, null, false));
		lcUsuario.add(new GuardaCampo(txtNomeUsu, "NOMEUSU", "Nome do usuario", ListaCampos.DB_SI, null, false));
		lcUsuario.setQueryCommit(false);
		lcUsuario.setReadOnly(true);

		txtNomeUsu.setSoLeitura(true);
		txtCodUsu.setTabelaExterna(lcUsuario);
		lcUsuario.montaSql(false, "USUARIO", "SG");

		lcCC.add(new GuardaCampo(txtCodCC, "CodCC", "Cód.cc.", ListaCampos.DB_PK, false));
		lcCC.add(new GuardaCampo(txtAnoCC, "AnoCC", "Ano.cc.", ListaCampos.DB_PK, false));
		lcCC.add(new GuardaCampo(txtDescCC, "DescCC", "Descrição do centro de custo", ListaCampos.DB_SI, false));
		lcCC.setReadOnly(true);
		lcCC.setQueryCommit(false);
		lcCC.montaSql(false, "CC", "FN");
		txtCodCC.setTabelaExterna(lcCC);
		txtCodCC.setFK(true);
		txtCodCC.setNomeCampo("CodCC");
		
		Container c = getTela();
		c.add(pnRod, BorderLayout.SOUTH);
		c.add(pnCli, BorderLayout.CENTER);
		pnCli.add(pinCab, BorderLayout.NORTH);
		pnCli.add(spnTab, BorderLayout.CENTER);

		btSair.setPreferredSize(new Dimension(100, 30));
		
		pnLegenda.add(new JLabelPad("Cancelada",imgCancelada,SwingConstants.CENTER));
		pnLegenda.add(new JLabelPad("Aprovada",imgAprovada,SwingConstants.CENTER));
		pnLegenda.add(new JLabelPad("Expedida",imgExpedida,SwingConstants.CENTER));
		pnLegenda.add(new JLabelPad("Pendente",imgPendente,SwingConstants.CENTER));
		
		pnRod.add(pnLegenda,BorderLayout.WEST);
		pnRod.add(btSair, BorderLayout.EAST);
		
		JLabelPad lbLinha = new JLabelPad();
		lbLinha.setBorder(BorderFactory.createEtchedBorder());
		JLabelPad lbLinha2 = new JLabelPad();
		lbLinha2.setBorder(BorderFactory.createEtchedBorder());
		JLabelPad lbStatus = new JLabelPad(" Filtrar:");
		lbStatus.setOpaque(true);

		pinCab.adic(new JLabelPad("Período:"), 7, 5, 50, 20);
		pinCab.adic(txtDtIni, 7, 25, 95, 20);
		pinCab.adic(new JLabelPad("Até"), 111, 25, 27, 20);
		pinCab.adic(txtDtFim, 139, 25, 95, 20);

		pinCab.adic(new JLabelPad("Cód.c.c."), 237, 5, 70, 20);
		pinCab.adic(txtCodCC, 237, 25, 140, 20);
		pinCab.adic(new JLabelPad("Centro de custo"), 310, 5, 410, 20);
		pinCab.adic(txtDescCC, 380, 25, 180, 20);

		pinCab.adic(new JLabelPad("Cód.usu."), 7, 48, 70, 20);
		pinCab.adic(txtCodUsu, 7, 70, 70, 20);
		pinCab.adic(new JLabelPad("Nome do usuário"), 80, 48, 410, 20);
		pinCab.adic(txtNomeUsu, 80, 70, 153, 20);
/*
		pinCab.adic(new JLabelPad("Cód.almox."), 237, 48, 75, 20);
		pinCab.adic(txtCodAlmoxarife, 237, 70, 70, 20);
		pinCab.adic(new JLabelPad("Nome do almoxarifado"), 310, 48, 410, 20);
		pinCab.adic(txtDescAlmoxarife, 310, 70, 180, 20);
*/
		pinCab.adic(lbStatus, 15, 100, 50, 18);
		pinCab.adic(lbLinha2, 7, 110, 373, 66);
		pinCab.adic(cbPendentes, 15, 122, 170, 20);
		pinCab.adic(cbAprovadas, 15, 147, 170, 20);
		pinCab.adic(cbExpedidas, 195, 122, 180, 20);
		pinCab.adic(cbCanceladas, 195, 147, 180, 20);
		
//		pinCab.adic(cbTomadasDePreco, 195, 147, 180, 20);

		pinCab.adic(btBusca, 382, 110, 110, 30);
		pinCab.adic(btPrevimp, 382, 145, 110, 30);

		txtDtIni.setVlrDate(new Date());
		txtDtFim.setVlrDate(new Date());

		tab.adicColuna("");
		tab.adicColuna("Rma.");
		tab.adicColuna("Data");
		tab.adicColuna("Motivo");

		tab.setTamColuna(12, 0);
		tab.setTamColuna(80, 1);
		tab.setTamColuna(90, 2);
		tab.setTamColuna(250, 3);

		btBusca.addActionListener(this);
		btPrevimp.addActionListener(this);

		tab.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent mevt) {
				if (mevt.getSource() == tab && mevt.getClickCount() == 2)
					abreRma();
			}
		});
		btSair.addActionListener(this);

	}

	private void habCampos(){
		getAprova();
		if(!bExpede){
			if(bAprova){
			  if(bAprovaParcial){
			  	txtCodCC.setVlrString(Aplicativo.strCodCCUsu);
				txtAnoCC.setVlrString(Aplicativo.strAnoCCUsu);
				txtCodCC.setNaoEditavel(true);
			  	lcUsuario.setWhereAdic("CODCC='"+Aplicativo.strCodCCUsu+"' AND ANOCC="+Aplicativo.strAnoCCUsu);
			  }
			  else {
			  	txtCodCC.setNaoEditavel(false);
			  	
			  }
			  txtCodUsu.setNaoEditavel(false);
			}
			else {
			  txtCodUsu.setVlrString(Aplicativo.strUsuario);		  
			  txtCodCC.setVlrString(Aplicativo.strCodCCUsu);
			  txtAnoCC.setVlrString(Aplicativo.strAnoCCUsu);
	
			  txtCodUsu.setNaoEditavel(true);
			  txtCodCC.setNaoEditavel(true);
			  lcUsuario.carregaDados();
			  lcCC.carregaDados();
			}
		}		
	}
	
	/**
	 * Carrega os valores para a tabela de consulta. Este método é executado após
	 * carregar o ListaCampos da tabela.
	 */
	private void carregaTabela() {
		String where = "";
		boolean usaOr = false;
		boolean usaWhere = false;
		boolean usuario = (!txtCodUsu.getVlrString().trim().equals(""));
//		boolean almoxarifado = (txtCodAlmoxarife.getVlrInteger().intValue() > 0);
		boolean almoxarifado = false;
		boolean CC = (!txtCodCC.getVlrString().trim().equals(""));

		if (cbPendentes.getVlrString().equals("S")) {
			usaWhere = true;
			where = " SitRma ='PE'";
		}
		if (cbAprovadas.getVlrString().equals("S")) {
			if (where.trim().equals("")) {
				where = " SitRma ='AF'";
			} 
			else {
				where = where + " OR SitRma ='AF'";
				usaOr = true;
			}
			usaWhere = true;
		}
		if (cbExpedidas.getVlrString().equals("S")) {
			if (where.trim().equals("")) {
				where = " SitRma ='EF'";
			} else {
				where = where + " OR SitRma ='EF'";
				usaOr = true;
			}
			usaWhere = true;
		}		
		if (cbCanceladas.getVlrString().equals("S")) {
			if (where.trim().equals("")) {
				where = " SitRma ='CA'";
			} else {
				where = where + " OR SitRma ='CA'";
				usaOr = true;
			}
			usaWhere = true;
		}
/*
		if (cbTomadasDePreco.getVlrString().equals("S")) {
			if (where.trim().equals(""))
				where = " SitRma ='TP'";
			else {
				where = where + " OR SitRma ='TP'";
				usaOr = true;
			}
			usaWhere = true;
		}
*/
		if (usaWhere && usaOr)
			where = " AND (" + where + ")";
		else if (usaWhere)
			where = " AND " + where;
		else
			where = " AND SitRma='PE'";

		if (almoxarifado)
			where += " AND IT.CODALMOX=? AND IT.CODEMPAM=? AND IT.CODFILIALAM=? ";

		if (CC)
			where += " AND R.ANOCC=? AND R.CODCC=? AND R.CODEMPCC=? AND R.CODFILIALCC=? ";

		if (usuario)
			where += " AND (R.IDUSU=?) ";

		String sSQL = "SELECT R.SITRMA, R.CODRMA,R.DTAREQRMA, R.MOTIVORMA "
				+ "FROM  EQRMA R, EQITRMA IT "
				+ "WHERE R.CODEMP=? "
				+ "AND R.CODFILIAL=? "
				+ "AND IT.CODRMA=R.CODRMA AND IT.CODEMP=R.CODEMP AND IT.CODFILIAL=R.CODFILIAL "
				+ "AND ((IT.DTAPROVITRMA BETWEEN ? AND ?) OR  (R.DTAREQRMA BETWEEN ? AND ?)) "
				+ where + " GROUP BY R.CODRMA, R.SitRMA, R.DTAREQRMA, R.MOTIVORMA ";

		System.out.println(sSQL);
		try {
			PreparedStatement ps = con.prepareStatement(sSQL);
			int param = 1;
			ps.setInt(param++, Aplicativo.iCodEmp);
			ps.setInt(param++, ListaCampos.getMasterFilial("EQRMA"));
			ps.setDate(param++, Funcoes.dateToSQLDate(txtDtIni.getVlrDate()));
			ps.setDate(param++, Funcoes.dateToSQLDate(txtDtFim.getVlrDate()));
			ps.setDate(param++, Funcoes.dateToSQLDate(txtDtIni.getVlrDate()));
			ps.setDate(param++, Funcoes.dateToSQLDate(txtDtFim.getVlrDate()));

			if (almoxarifado) {
				ps.setInt(param++, txtCodAlmoxarife.getVlrInteger().intValue());
				ps.setInt(param++, Aplicativo.iCodEmp);
				ps.setInt(param++, Aplicativo.iCodFilial);
			}

			if (CC) {
				ps.setInt(param++, txtAnoCC.getVlrInteger().intValue());
				ps.setString(param++, txtCodCC.getVlrString());
				ps.setInt(param++, Aplicativo.iCodEmp);
				ps.setInt(param++, Aplicativo.iCodFilial);
			}

			if (usuario) {
				ps.setString(param++, txtCodUsu.getVlrString());
			}

			ResultSet rs = ps.executeQuery();
			
			int iLin = 0;

			tab.limpa();
			while (rs.next()) {
				tab.adicLinha();
				
				String sitRMA = rs.getString(1);
				if (sitRMA.equalsIgnoreCase("PE")) {
					imgColuna = imgPendente;
					sSitRMA = "Pendente";
				} 
				else if (sitRMA.equalsIgnoreCase("CA")) {
					imgColuna = imgCancelada;
					sSitRMA = "Cancelada";
				} 
				else if (sitRMA.equalsIgnoreCase("EF")) {
					imgColuna = imgExpedida;
					sSitRMA = "Expedida";
				} 
				else if (sitRMA.equalsIgnoreCase("AF")) {
					imgColuna = imgAprovada;
					sSitRMA = "Aprovada";
				}

				tab.setValor(imgColuna, iLin, 0);
				tab.setValor(new Integer(rs.getInt(2)), iLin, 1);
				tab.setValor(rs.getString(3) == null ? "-" : Funcoes
						.sqlDateToStrDate(rs.getDate(3))
						+ "", iLin, 2);
				tab.setValor(rs.getString(4) == null ? "-" : rs.getString(4) + "",
						iLin, 3);

				iLin++;
				
				
			}

			if (!con.getAutoCommit())
				con.commit();
		} catch (SQLException err) {
			Funcoes.mensagemErro(this, "Erro ao carregar a tabela EQRMA!\n"
					+ err.getMessage(),true,con,err);
			err.printStackTrace();
		}
	}

	private void imprimir(boolean bVisualizar) {
		ImprimeOS imp = new ImprimeOS("", con);
		int linPag = imp.verifLinPag() - 1;
		BigDecimal bTotalLiq = new BigDecimal("0");
		boolean bImpCot = false;

		/*
		 * bImpCot = Funcoes.mensagemConfirma(this, "Deseja imprimir informações de
		 * cotações de preço?") == 0 ? true : false;
		 */

		

		try {
			imp.limpaPags();
			for (int iLin = 0; iLin < tab.getNumLinhas(); iLin++) {
				if (imp.pRow() == 0) {
					imp.montaCab();
					imp.setTitulo("Relatório de Requisições de material");
					imp.addSubTitulo("Relatório de Requisições de material");
					imp.impCab(136, true);
					//	imp.say(imp.pRow()+1,0,""+imp.comprimido());
					imp.say(imp.pRow() + 0, 0, "| Rma.");
					imp.say(imp.pRow() + 0, 15, "| Emissão");
					imp.say(imp.pRow() + 0, 29, "| Situação");
					imp.say(imp.pRow() + 0, 45, "| Motivo.");
					imp.say(imp.pRow() + 0, 135, "|");
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());

					if (bImpCot) {
						imp.say(imp.pRow() + 0, 0, "| Nro. Pedido");
						imp.say(imp.pRow() + 0, 15, "| Nro. Nota");
						imp.say(imp.pRow() + 0, 29, "| Data Fat.");
						imp.say(imp.pRow() + 0, 41, "| ");
						imp.say(imp.pRow() + 0, 56, "| ");
						imp.say(imp.pRow() + 0, 87, "| Vlr. Item Fat.");
						imp.say(imp.pRow() + 0, 105, "| ");
						imp.say(imp.pRow() + 0, 124, "| ");
						imp.say(imp.pRow() + 0, 135, "|");
						imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());

					}

					imp.say(imp.pRow() + 0, 0,"|"+Funcoes.replicate("-", 133)+"|");

				}

				imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
				imp.say(imp.pRow() + 0, 0, "|" + tab.getValor(iLin, 1));
				imp.say(imp.pRow() + 0, 15, "| " + tab.getValor(iLin, 2));
				imp.say(imp.pRow() + 0, 29, "| " + sSitRMA);
				String sMotivo = ""+tab.getValor(iLin, 3);
				imp.say(imp.pRow() + 0, 45, "| " + sMotivo.substring(0, sMotivo.length()>89?89:sMotivo.length()).trim());
				imp.say(imp.pRow() + 0, 135, "| ");

				if (bImpCot) {
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 2, "|" + tab.getValor(iLin, 2));
					imp.say(imp.pRow() + 0, 15, "|" + tab.getValor(iLin, 3));
					imp.say(imp.pRow() + 0, 29, "|");
					imp.say(imp.pRow() + 0, 41, "|");
					imp.say(imp.pRow() + 0, 56, "|");
					imp.say(imp.pRow() + 0, 87, "|" + tab.getValor(iLin, 12));
					imp.say(imp.pRow() + 0, 105, "|");
					imp.say(imp.pRow() + 0, 124, "|");
					imp.say(imp.pRow() + 0, 135, "|");
				}

				
				if (tab.getValor(iLin, 9) != null) {
					bTotalLiq = bTotalLiq.add(new BigDecimal(Funcoes
							.strCurrencyToDouble("" + tab.getValor(iLin, 9))));
				}

				if (imp.pRow() >= linPag) {
					imp.incPags();
					imp.eject();
				}
			}

			imp.say(imp.pRow() + 1, 0,"+"+Funcoes.replicate("-", 133)+"+");
			imp.eject();

			imp.fechaGravacao();

			if (!con.getAutoCommit())
				con.commit();

		} catch (SQLException err) {
			Funcoes.mensagemErro(this, "Erro consulta tabela de orçamentos!\n"
					+ err.getMessage(),true,con,err);
		}

		if (bVisualizar) {
			imp.preview(this);
		} else {
			imp.print();
		}
	}

	private void abreRma() {
		int iRma = ((Integer) tab.getValor(tab.getLinhaSel(), 1)).intValue();
		if (fPrim.temTela("Requisição de material") == false) {
			FRma tela = new FRma();
			fPrim.criatela("Requisição de material", tela, con);
			tela.exec(iRma);
		}
	}

    private void getAprova() {
		String sSQL = "SELECT ANOCC,CODCC,CODEMPCC,CODFILIALCC,APROVRMAUSU,ALMOXARIFEUSU " +
				      "FROM SGUSUARIO WHERE CODEMP=? AND CODFILIAL=? " +
				      "AND IDUSU=?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			
			ps = con.prepareStatement(sSQL);
			ps.setInt(1, Aplicativo.iCodEmp);
			ps.setInt(2, ListaCampos.getMasterFilial("SGUSUARIO"));
			ps.setString(3, Aplicativo.strUsuario);
			rs = ps.executeQuery();
			if (rs.next()) {
				String sAprova = rs.getString("APROVRMAUSU");
				String sExpede = rs.getString("ALMOXARIFEUSU");
				if(sAprova!=null){
					if(!sAprova.equals("ND")) {
						if(sAprova.equals("TD"))						
							bAprova = true;
						else if( (Aplicativo.strCodCCUsu.equals(rs.getString("CODCC"))) &&
								 (Aplicativo.iCodEmp==rs.getInt("CODEMPCC")) &&
								 (ListaCampos.getMasterFilial("FNCC")==rs.getInt("CODFILIALCC")) &&
								 (sAprova.equals("CC"))	) { 
							bAprova = true;	
							bAprovaParcial = true;
						}						
					}
				}
				if(sExpede!=null){
					if(sExpede.equals("S")){
						bExpede = true;
					}
					else {
						bExpede = false;
					}
				}
			}
			if (!con.getAutoCommit())
				con.commit();

		} catch (SQLException err) {
			Funcoes.mensagemErro(this, "Erro ao carregar a tabela PREFERE1!\n"
					+ err.getMessage(),true,con,err);
		}
    }
  
	
	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == btSair) {
			dispose();
		}
		if (evt.getSource() == btBusca) {
			if (txtDtIni.getVlrString().length() < 10)
				Funcoes.mensagemInforma(this, "Digite a data inicial!");
			else if (txtDtFim.getVlrString().length() < 10)
				Funcoes.mensagemInforma(this, "Digite a data final!");
			else
				carregaTabela();
			if (evt.getSource() == btPrevimp) {
				imprimir(true);
			}
		}
		if (evt.getSource() == btPrevimp) {
			imprimir(true);
		}

	}

	public void setConexao(Connection cn) {
		super.setConexao(cn);
		lcAlmox.setConexao(cn);
		lcUsuario.setConexao(cn);
		lcCC.setConexao(cn);
		habCampos();
	}
}