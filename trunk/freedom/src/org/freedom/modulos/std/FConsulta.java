/**
 * @version 28/10/2004 <BR>
 * @author Setpoint Informática Ltda./Robson Sanchez e Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FConsulta.java <BR>
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
 * Formulário de consulta de produtos.
 */


package org.freedom.modulos.std;
import java.awt.BorderLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Painel;
import org.freedom.componentes.StringDireita;
import org.freedom.componentes.Tabela;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FFilho;

public class FConsulta extends FFilho implements CarregaListener {
    private final int TAM_GRUPO = 14;
	private Painel pinCabProd = new Painel(700,60);
	private JPanel pnCliProd = new JPanel(new BorderLayout());
	private JTextFieldPad txtCodProd = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
	private JTextFieldPad txtRefProd = new JTextFieldPad(JTextFieldPad.TP_STRING,13,0);
	private JTextFieldFK txtSldProd = new JTextFieldFK(JTextFieldPad.TP_NUMERIC,15,3);
	private JTextFieldFK txtSldRProd = new JTextFieldFK(JTextFieldPad.TP_NUMERIC,15,3);
	private JTextFieldFK txtSldCProd = new JTextFieldFK(JTextFieldPad.TP_NUMERIC,15,3);
	private JTextFieldFK txtSldLProd = new JTextFieldFK(JTextFieldPad.TP_NUMERIC,15,3);
	private JTextFieldFK txtDescProd = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
	private JTextFieldPad txtCodGrup = new JTextFieldPad(JTextFieldPad.TP_STRING,TAM_GRUPO,0);
	private JTextFieldFK txtDescGrup = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
	private Tabela tabProd = new Tabela();
	private JScrollPane spnTabProd = new JScrollPane(tabProd);
	private ListaCampos lcProd = new ListaCampos(this,"PR");
	private ListaCampos lcProd2 = new ListaCampos(this);

	private Painel pinCabGrup = new Painel(700,60);
	private JPanel pnCliGrup = new JPanel(new BorderLayout());
	private Tabela tabGrup = new Tabela();
	private JScrollPane spnTabGrup = new JScrollPane(tabGrup);
	private ListaCampos lcGrup = new ListaCampos(this);
	
	private Connection con = null;
	private JTabbedPane tpnConsulta = new JTabbedPane();
	
	public FConsulta() {
		setTitulo("Consulta");
		setAtribos(10,20,790,460);
		
	    getTela().add(tpnConsulta, BorderLayout.CENTER);
	    tpnConsulta.addTab("Grupo",pnCliGrup);
	    tpnConsulta.addTab("Produto",pnCliProd);
	
		// Início da aba de consulta de saldo por grupo
		
		txtCodGrup.setRequerido(true);
		lcGrup.add(new GuardaCampo( txtCodGrup, "CodGrup", "Cód.grupo", ListaCampos.DB_PK, false));
		lcGrup.add(new GuardaCampo( txtDescGrup, "DescGrup", "Descrição do grupo", ListaCampos.DB_SI,false));
		txtCodGrup.setTabelaExterna(lcGrup);
		txtCodGrup.setNomeCampo("CodGrup");
		txtCodGrup.setFK(true);
		lcGrup.setReadOnly(true);
		lcGrup.montaSql(false, "GRUPO", "EQ");

		pnCliGrup.add(pinCabGrup,BorderLayout.NORTH);
		pnCliGrup.add(spnTabGrup,BorderLayout.CENTER);

		tabGrup.adicColuna("Cód.prod.");
		tabGrup.adicColuna("Descrição do produto");
		tabGrup.adicColuna("Saldo prod.");
		tabGrup.adicColuna("Saldo res.");
		tabGrup.adicColuna("Saldo consig.");
		tabGrup.adicColuna("Saldo líquido");

		tabGrup.setTamColuna(80,0);
		tabGrup.setTamColuna(250,1);
		tabGrup.setTamColuna(80,2);
		tabGrup.setTamColuna(80,3);
		tabGrup.setTamColuna(90,4);
		tabGrup.setTamColuna(90,5);

		pinCabGrup.adic(new JLabel("Cód.grupo"),7,0,100,20);
		pinCabGrup.adic(new JLabel("Descrição do grupo"),110,0,300,20);
		pinCabGrup.adic(txtCodGrup,7,20,100,20);
		pinCabGrup.adic(txtDescGrup,110,20,300,20);
		
		lcGrup.addCarregaListener(this);
		
        // Fim da aba de consulta de saldo por grupo

	    // Aba de consulta de saldo por produto
	    
		txtCodProd.setRequerido(true);
		lcProd.add(new GuardaCampo( txtCodProd, "CodProd", "Cód.prod", ListaCampos.DB_PK, false));
		lcProd.add(new GuardaCampo( txtDescProd, "DescProd", "Descrição do produto", ListaCampos.DB_SI, false));
		lcProd.add(new GuardaCampo( txtSldProd, "SldProd", "Saldo", ListaCampos.DB_SI, false));
		lcProd.add(new GuardaCampo( txtSldRProd, "SldResProd", "Saldo res.", ListaCampos.DB_SI, false));
		lcProd.add(new GuardaCampo( txtSldCProd, "SldConsigProd", "Saldo cons.", ListaCampos.DB_SI, false));
		lcProd.add(new GuardaCampo( txtSldLProd, "SldLiqProd", "Saldo liq.", ListaCampos.DB_SI, false));
		txtCodProd.setTabelaExterna(lcProd);
		txtCodProd.setNomeCampo("CodProd");
		txtCodProd.setFK(true);
		lcProd.setReadOnly(true);
		lcProd.montaSql(false, "PRODUTO", "EQ");

		pnCliProd.add(pinCabProd,BorderLayout.NORTH);
		pnCliProd.add(spnTabProd,BorderLayout.CENTER);

		pinCabProd.adic(txtDescProd,100,20,197,20);
		pinCabProd.adic(new JLabel("Saldo"),300,0,87,20);
		pinCabProd.adic(txtSldProd,300,20,87,20);
		pinCabProd.adic(new JLabel("Saldo cons."),390,0,87,20);
		pinCabProd.adic(txtSldCProd,390,20,87,20);
		pinCabProd.adic(new JLabel("Saldo rest."),480,0,87,20);
		pinCabProd.adic(txtSldRProd,480,20,87,20);
		pinCabProd.adic(new JLabel("Saldo liq."),570,0,87,20);
		pinCabProd.adic(txtSldLProd,570,20,87,20);

		tabProd.adicColuna("Cód.c.cli.");
		tabProd.adicColuna("Descrição da classificação do cliente");
		tabProd.adicColuna("Cód.t.pr.");
		tabProd.adicColuna("Descrição da tab. de preços");
		tabProd.adicColuna("Cód.p.pg.");
		tabProd.adicColuna("Descrição do plano de pagamento.");
		tabProd.adicColuna("Preço");

		tabProd.setTamColuna(70,0);
		tabProd.setTamColuna(250,1);
		tabProd.setTamColuna(70,2);
		tabProd.setTamColuna(210,3);
		tabProd.setTamColuna(70,4);
		tabProd.setTamColuna(240,5);
		tabProd.setTamColuna(90,6);

		lcProd.addCarregaListener(this);
		
		// Fim da aba de consulta de saldo por produto		
		adicBotaoSair();
		
	}
	
	private void montaTela() {
	    // Aba de saldos por produto 
		if (comRef()) {
			txtRefProd.setRequerido(true);
			lcProd2.add(new GuardaCampo( txtRefProd, "RefProd", "Ref.prod", ListaCampos.DB_PK, false));
			lcProd2.add(new GuardaCampo( txtDescProd, "DescProd", "Descrição do produto", ListaCampos.DB_SI, false));
			lcProd2.add(new GuardaCampo( txtCodProd, "CodProd", "Cód.prod", ListaCampos.DB_FK, false));
			txtRefProd.setTabelaExterna(lcProd2);
			txtRefProd.setNomeCampo("Refprod");
			txtRefProd.setFK(true);
			lcProd2.setReadOnly(true);
			lcProd2.montaSql(false, "PRODUTO", "EQ");
			pinCabProd.adic(new JLabel("Ref.prod."),7,0,90,20);
			pinCabProd.adic(new JLabel("Descrição do produto"),100,0,200,20);
			pinCabProd.adic(txtRefProd,7,20,90,20);
			lcProd2.setConexao(con);
		}
		else {
			pinCabProd.adic(new JLabel("Cód.prod."),7,0,90,20);
			pinCabProd.adic(new JLabel("Descrição do produto"),100,0,200,20);
			pinCabProd.adic(txtCodProd,7,20,90,20);
		}
		

	}

	/**
	 * 
	 * Carrega os valores para a tabela de consulta.
	 * Este método é executado após carregar o ListaCampos da tabela.
	 *
	 */ 
	private void carregaTabGrup() {
		String sSQL = "SELECT P.CODPROD,P.DESCPROD,P.SLDPROD, P.SLDRESPROD, " +
				 "P.SLDCONSIGPROD,P.SLDLIQPROD FROM EQPRODUTO P "+
				 "WHERE P.ATIVOPROD='S' AND P.CODEMPGP=? AND P.CODFILIALGP=? AND " +
				 "P.CODGRUP LIKE ? ORDER BY P.DESCPROD ";
		String sCodGrup = null;
		try {
			sCodGrup = txtCodGrup.getVlrString().trim();
			if (sCodGrup.equals("")) {
				Funcoes.mensagemInforma(this,"Selecione um grupo!");
				txtCodGrup.requestFocus();
				return;
			}
			if (sCodGrup.length()<TAM_GRUPO)
				sCodGrup += "%";
			PreparedStatement ps = con.prepareStatement(sSQL);
			ps.setInt(1,Aplicativo.iCodEmp);
			ps.setInt(2,ListaCampos.getMasterFilial("EQGRUPO"));
			ps.setString(3,sCodGrup);
			ResultSet rs = ps.executeQuery();
			int iLin = 0;
            tabGrup.limpa();
			while (rs.next()) {
				tabGrup.adicLinha();
				tabGrup.setValor(new StringDireita(rs.getString("CODPROD")),iLin,0);
				tabGrup.setValor(rs.getString("DESCPROD"),iLin,1);
				tabGrup.setValor(new StringDireita(rs.getDouble("SLDPROD")+""),iLin,2);
				tabGrup.setValor(new StringDireita(rs.getDouble("SLDRESPROD")+""),iLin,3);
				tabGrup.setValor(new StringDireita(rs.getDouble("SLDCONSIGPROD")+""),iLin,4);
				tabGrup.setValor(new StringDireita(rs.getDouble("SLDLIQPROD")+""),iLin,5); 
				iLin++;
			}
			rs.close();
			ps.close();
			if (!con.getAutoCommit())
                con.commit();
		}
		catch (SQLException err) {
			Funcoes.mensagemErro(this,"Erro ao carregar saldos por grupo!\n"+err.getMessage());
		}
		finally {
			sSQL = null;
			sCodGrup = null;
		}
	}
	
	/**
	 * 
	 * Carrega os valores para a tabela de consulta.
	 * Este método é executado após carregar o ListaCampos da tabela.
	 *
	 */ 
	private void carregaTabProd() {
		String sSQL = "SELECT P.CODCLASCLI,(SELECT CL.DESCCLASCLI"+
		              " FROM VDCLASCLI CL WHERE "+
			      "CL.CODCLASCLI=P.CODCLASCLI),P.CODTAB,T.DESCTAB,"+
			      "P.CODPLANOPAG,PP.DESCPLANOPAG,P.PRECOPROD FROM VDPRECOPROD P,"+
			      "VDTABPRECO T, FNPLANOPAG PP WHERE "+
			      "T.CODTAB=P.CODTAB AND PP.CODPLANOPAG=P.CODPLANOPAG AND "+
			      "P.CODPROD = ? ORDER BY P.CODPRECOPROD";
		try {
			PreparedStatement ps = con.prepareStatement(sSQL);
			ps.setInt(1,txtCodProd.getVlrInteger().intValue());
			ResultSet rs = ps.executeQuery();
			int iLin = 0;
            tabProd.limpa();
			while (rs.next()) {
				tabProd.adicLinha();
				tabProd.setValor(rs.getString(1) != null ? rs.getString(1) : "",iLin,0);
				tabProd.setValor(rs.getString(2) != null ? rs.getString(2) : "",iLin,1);
				tabProd.setValor(rs.getString("CodTab"),iLin,2);
				tabProd.setValor(rs.getString("DescTab") != null ? rs.getString("DescTab") : "",iLin,3);
				tabProd.setValor(rs.getString("CodPlanoPag"),iLin,4);
				tabProd.setValor(rs.getString("DescPlanoPag") != null ? rs.getString("DescPlanoPag") : "",iLin,5); 
				tabProd.setValor(rs.getString("PrecoProd") != null ? Funcoes.strDecimalToStrCurrency(15,2,rs.getString("PrecoProd")) : "",iLin,6); 
				iLin++;
			}
//			rs.close();
//			ps.close();
			if (!con.getAutoCommit())
                con.commit();
		}
		catch (SQLException err) {
			Funcoes.mensagemErro(this,"Erro ao carregar a tabela PRECOPROD!\n"+err.getMessage());
		}
	}
	public void beforeCarrega(CarregaEvent pevt) { }
	public void afterCarrega(CarregaEvent pevt) {
		if (pevt.getListaCampos() == lcProd) {
			carregaTabProd();
		}
		if (pevt.getListaCampos() == lcGrup) {
			carregaTabGrup();
		}
	}
	public void setConexao(Connection cn) {
		con = cn;
		lcProd.setConexao(con);
		lcGrup.setConexao(con);
		montaTela();
	}
	private boolean comRef() {
		boolean bRetorno = false;
		String sSQL = "SELECT USAREFPROD FROM SGPREFERE1 WHERE CODEMP=? AND CODFILIAL=?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
		ps = con.prepareStatement(sSQL);
		ps.setInt(1,Aplicativo.iCodEmp);
		ps.setInt(2, ListaCampos.getMasterFilial("SGPREFERE1"));
		rs = ps.executeQuery();
		if (rs.next()) {
			if (rs.getString("UsaRefProd").trim().equals("S"))
				bRetorno = true;
			}
		}
		catch (SQLException err) {
			Funcoes.mensagemErro(this,"Erro ao carregar a tabela PREFERE1!\n"+err.getMessage());
		}
		return bRetorno;
	}
}
