/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)DLConsultaVenda.java <BR>
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
 */

package org.freedom.modulos.std;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JLabel;
import javax.swing.JScrollPane;

import org.freedom.acao.TabelaSelEvent;
import org.freedom.acao.TabelaSelListener;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JTextAreaPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Painel;
import org.freedom.componentes.Tabela;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.FFDialogo;


public class DLConsultaObs extends FFDialogo implements ActionListener, TabelaSelListener {
  private Painel pinConsulta = new Painel(500,180);
  private JTextFieldPad txtCodCli = new JTextFieldPad(JTextFieldPad.TP_INTEGER,10,0);
  private JTextFieldFK txtRazCli = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
  private JTextAreaPad txaObs = new JTextAreaPad();
  private Tabela tabConsulta = new Tabela();
  private JScrollPane spnTab = new JScrollPane(tabConsulta);
  private JScrollPane spnObs = new JScrollPane(txaObs);
  private ListaCampos lcCliente = new ListaCampos(this);
  public DLConsultaObs(Component cOrig,ResultSet rs,Connection cn) {
  	super(cOrig);
    setTitulo("Consulta de Observçes de clientes!");
    setAtribos(300,300);
    
    c.add(spnTab,BorderLayout.CENTER);
	c.add(pinConsulta,BorderLayout.SOUTH);
    
    txtCodCli.setAtivo(false);
    txaObs.setEditable(false);
    
    pinConsulta.adic(new JLabel("Cód.cli."),7,0,200,20);
    pinConsulta.adic(txtCodCli,7,20,80,20);
    pinConsulta.adic(new JLabel("Razão social do cliente"),90,0,200,20);
    pinConsulta.adic(txtRazCli,90,20,187,20);
    pinConsulta.adic(new JLabel("Observação:"),7,40,100,20);
    pinConsulta.adic(spnObs,7,60,270,80);
    
    txtCodCli.setNomeCampo("CodCli");
    lcCliente.add(new GuardaCampo( txtCodCli, "CodCli", "Cód.cli.", ListaCampos.DB_PK,false));
    lcCliente.add(new GuardaCampo( txtRazCli, "RazCli", "Razão social do cliente", ListaCampos.DB_SI,false));
	lcCliente.add(new GuardaCampo( txaObs, "ObsCli", "Observação", ListaCampos.DB_SI,false));
    txtRazCli.setListaCampos(lcCliente);
    lcCliente.montaSql(false, "CLIENTE", "VD");
    lcCliente.setQueryCommit(false);
    lcCliente.setReadOnly(true);
    lcCliente.setConexao(cn);

    tabConsulta.adicColuna("Código");
    tabConsulta.adicColuna("Razão");
    
    tabConsulta.setTamColuna(50,0);
    tabConsulta.setTamColuna(220,1);
    
    tabConsulta.addTabelaSelListener(this);

    carregaGridConsulta(rs);
  }
  private void carregaGridConsulta(ResultSet rs) {
    try {
      int i = -1; //Essa loucura ehh por que o rs jah foi dado next() fora desta tela.
	  do {
	  	i++;
		tabConsulta.adicLinha();
		tabConsulta.setValor(""+rs.getInt("CodCli"),i,0);
		tabConsulta.setValor((rs.getString("RazCli") != null ? rs.getString("RazCli") : ""),i,1);
	  }    	
      while (rs.next());
      rs.close();
      tabConsulta.setRowSelectionInterval(0,0);
    }
    catch(SQLException err) {
		Funcoes.mensagemErro(this,"Erro ao montar a tabela de consulta!\n"+err.getMessage());
    }
  }
  public void valorAlterado(TabelaSelEvent tevt) {
	if (tevt.getTabela() == tabConsulta) {
		txtCodCli.setVlrString(""+tabConsulta.getValor(tabConsulta.getLinhaSel(),0));
		lcCliente.carregaDados();
	}
  }
}
