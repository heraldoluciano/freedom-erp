/**
 * @version 11/02/2002 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.fnc <BR>
 * Classe: @(#)FPrefereGeral.java <BR>
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

package org.freedom.modulos.fnc;
import java.sql.Connection;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Painel;
import org.freedom.telas.FTabDados;

public class FPrefereGeral extends FTabDados {
	private Painel pinGeral = new Painel(330, 350);
	private Painel pinFin = new Painel();
	private JTextFieldPad txtCodMoeda = new JTextFieldPad(JTextFieldPad.TP_STRING, 4, 0);
	private JTextFieldFK txtDescMoeda = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);
	private JTextFieldPad txtAnoCC = new JTextFieldPad(JTextFieldPad.TP_INTEGER,5,0);
	private ListaCampos lcMoeda = new ListaCampos(this,"MO");
	public FPrefereGeral() {
		setTitulo("Preferências Gerais");
		setAtribos(50, 50, 355, 200);
		
		lcMoeda.add(new GuardaCampo(txtCodMoeda, "CodMoeda","Cód.moeda", ListaCampos.DB_PK, true));
		lcMoeda.add(new GuardaCampo(txtDescMoeda, "SingMoeda","Descrição da moeda", ListaCampos.DB_SI, false));
		lcMoeda.montaSql(false, "MOEDA", "FN");
		lcMoeda.setQueryCommit(false);
		lcMoeda.setReadOnly(true);
		txtCodMoeda.setTabelaExterna(lcMoeda);

//Geral
		
		setPainel(pinGeral);
		adicTab("Geral", pinGeral);
		adicCampo(txtAnoCC, 7,25,100,20, "AnoCentroCusto","Ano Base C.C.", ListaCampos.DB_SI, true);

//Financeiro
	
		setPainel(pinFin);
		adicTab("Financeiro", pinFin);

		adicCampo(txtCodMoeda,7,20,70,20,"CodMoeda","Cód.moeda", ListaCampos.DB_FK, txtDescMoeda, true);
		adicDescFK(txtDescMoeda,80,20,230,20,"SingMoeda","Descrição da moeda corrente.");

		nav.setAtivo(0,false);
		lcCampos.setPodeExc(false);

		setListaCampos(false, "PREFERE1", "SG");
		
	}
	public void execShow(Connection cn) {
		lcMoeda.setConexao(cn);
		super.execShow(cn);
		lcCampos.carregaDados();
	}
}
