/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)DLNovoPag.java <BR>
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
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.freedom.componentes.JLabelPad;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.freedom.acao.PostEvent;
import org.freedom.acao.PostListener;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.Tabela;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FFDialogo;

public class DLNovoPag extends FFDialogo implements PostListener {
  private JPanel pnPag = new JPanel(new BorderLayout());
  private JPanelPad pinCab = new JPanelPad(580,130);
  private JTextFieldPad txtCodFor = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldFK txtDescFor = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldPad txtCodPlanoPag = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldFK txtDescPlanoPag = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
  private JTextFieldPad txtCodBanco = new JTextFieldPad(JTextFieldPad.TP_STRING,3,0);
  private JTextFieldFK txtDescBanco = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
  private JTextFieldPad txtCodPag = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtNParcPag = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtVlrParcItPag = new JTextFieldPad(JTextFieldPad.TP_NUMERIC,15,2);
  private JTextFieldPad txtDtVencItPag = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
  private JTextFieldPad txtVlrParcPag = new JTextFieldPad(JTextFieldPad.TP_NUMERIC,15,2);
  private JTextFieldPad txtDtEmisPag = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
  private JTextFieldPad txtDocPag = new JTextFieldPad(JTextFieldPad.TP_INTEGER,10,0);
  private JTextFieldPad txtObs = new JTextFieldPad(JTextFieldPad.TP_STRING,50,0);
  private Tabela tabPag = new Tabela();
  private JScrollPane spnTab = new JScrollPane(tabPag);
  private ListaCampos lcPagar = new ListaCampos(this);
  private ListaCampos lcItPagar = new ListaCampos(this);
  private ListaCampos lcFor = new ListaCampos(this,"FR");
  private ListaCampos lcPlanoPag = new ListaCampos(this,"PG");
  private ListaCampos lcBanco = new ListaCampos(this,"BO");
  public DLNovoPag(Component cOrig) {
  	super(cOrig);
    setTitulo("Novo");
    setAtribos(600,320);
    
    lcItPagar.setMaster(lcPagar);
    lcPagar.adicDetalhe(lcItPagar);
    lcItPagar.setTabela(tabPag);

    lcFor.add(new GuardaCampo( txtCodFor, "CodFor", "Cód.for.", ListaCampos.DB_PK, false));
    lcFor.add(new GuardaCampo( txtDescFor, "RazFor", "Razão social do fornecedor", ListaCampos.DB_SI,false));
    lcFor.montaSql(false, "FORNECED", "CP");
    lcFor.setQueryCommit(false);
    lcFor.setReadOnly(true);
    txtCodFor.setTabelaExterna(lcFor);
    txtCodFor.setFK(true);
    txtCodFor.setNomeCampo("CodFor");

    lcPlanoPag.add(new GuardaCampo( txtCodPlanoPag, "CodPlanoPag", "Cód.p.pg.", ListaCampos.DB_PK,false));
    lcPlanoPag.add(new GuardaCampo( txtDescPlanoPag, "DescPlanoPag", "Descrição do plano de pagamento", ListaCampos.DB_SI,false));
    lcPlanoPag.montaSql(false, "PLANOPAG", "FN");
    lcPlanoPag.setQueryCommit(false);
    lcPlanoPag.setReadOnly(true);
    txtCodPlanoPag.setTabelaExterna(lcPlanoPag);
    txtCodPlanoPag.setFK(true);
    txtCodPlanoPag.setNomeCampo("CodPlanoPag");

    lcBanco.add(new GuardaCampo( txtCodBanco, "CodBanco", "Cód.banco", ListaCampos.DB_PK,false));
    lcBanco.add(new GuardaCampo( txtDescBanco, "NomeBanco", "Nome do banco", ListaCampos.DB_SI,false));
    lcBanco.montaSql(false, "BANCO", "FN");
    lcBanco.setQueryCommit(false);
    lcBanco.setReadOnly(true);
    txtCodBanco.setTabelaExterna(lcBanco);
    txtCodBanco.setFK(true);
    txtCodBanco.setNomeCampo("CodBanco");


    lcPagar.add(new GuardaCampo( txtCodPag, "CodPag", "Cód.pag.", ListaCampos.DB_PK,true));
    lcPagar.add(new GuardaCampo( txtCodFor, "CodFor", "Cód.for.", ListaCampos.DB_FK, true));
    lcPagar.add(new GuardaCampo( txtCodPlanoPag, "CodPlanoPag", "Cód.p.pg.", ListaCampos.DB_FK,true));
    lcPagar.add(new GuardaCampo( txtCodBanco, "CodBanco", "Cód.banco", ListaCampos.DB_FK, false));
    lcPagar.add(new GuardaCampo( txtVlrParcPag, "VlrParcPag", "Valor da parc.", ListaCampos.DB_SI,false));
    lcPagar.add(new GuardaCampo( txtDtEmisPag, "DataPag", "Dt.emissão", ListaCampos.DB_SI,false));
    lcPagar.add(new GuardaCampo( txtDocPag, "DocPag", "N.documento", ListaCampos.DB_SI,true));
    lcPagar.add(new GuardaCampo( txtObs, "ObsPag", "Obs.", ListaCampos.DB_SI,false));
    lcPagar.montaSql(true,"PAGAR", "FN");

    txtNParcPag.setNomeCampo("NParcPag");
    lcItPagar.add(new GuardaCampo( txtNParcPag, "NParcPag", "N.parc.", ListaCampos.DB_PK, false));
    lcItPagar.add(new GuardaCampo( txtVlrParcItPag, "VlrParcItPag", "Valor tot.", ListaCampos.DB_SI, false));
    lcItPagar.add(new GuardaCampo( txtDtVencItPag, "DtVencItPag", "Dt.vencto.", ListaCampos.DB_SI,false));
    lcItPagar.montaSql(false, "ITPAGAR", "FN");
    lcItPagar.setQueryCommit(false);
    txtNParcPag.setListaCampos(lcItPagar);
    txtVlrParcItPag.setListaCampos(lcItPagar);
    txtDtVencItPag.setListaCampos(lcItPagar);
    
    lcItPagar.montaTab();
    tabPag.addMouseListener( //Adiciona o mouse listener para que possa editar os itens.
      new MouseAdapter() {
        public void mouseClicked(MouseEvent mevt) {
          if ((mevt.getClickCount() == 2) & (tabPag.getLinhaSel() >= 0)) {
             lcItPagar.edit();
             DLFechaPag dl = new DLFechaPag(DLNovoPag.this,txtVlrParcItPag.getVlrBigDecimal(),txtDtVencItPag.getVlrDate());
             dl.setVisible(true);
            if (dl.OK) {
              txtVlrParcItPag.setVlrBigDecimal((BigDecimal)dl.getValores()[0]);
              txtDtVencItPag.setVlrDate((Date)dl.getValores()[1]);
              lcItPagar.post();
              //Atualiza lcPagar              
              if (lcPagar.getStatus() == ListaCampos.LCS_EDIT) 
                lcPagar.post(); // Caso o lcPagar estaja como edit executa o post que atualiza
              else 
                lcPagar.carregaDados(); //Caso não, atualiza
            }
            else {
              dl.dispose();
              lcItPagar.cancel(false);
            }
            dl.dispose();
          }
        }
      }
    );
    c.add(pnPag);
    
    pnPag.add(pinCab,BorderLayout.NORTH);
    pnPag.add(spnTab,BorderLayout.CENTER);

    setPainel(pinCab);
    adic(new JLabelPad("Cód.for."),7,0,250,20);
    adic(txtCodFor,7,20,80,20);
    adic(new JLabelPad("Razão social do fornecedor"),90,0,250,20);
    adic(txtDescFor,90,20,197,20);
    adic(new JLabelPad("Cód.p.pag."),290,0,250,20);
    adic(txtCodPlanoPag,290,20,77,20);
    adic(new JLabelPad("Descrição do plano de pagto."),370,0,250,20);
    adic(txtDescPlanoPag,370,20,200,20);
	adic(new JLabelPad("Cód.banco"),7,40,250,20);
	adic(txtCodBanco,7,60,80,20);
	adic(new JLabelPad("Descriçao do banco"),90,40,250,20);
	adic(txtDescBanco,90,60,197,20);
	adic(new JLabelPad("Valor"),290,40,107,20);
	adic(txtVlrParcPag,290,60,107,20);
	adic(new JLabelPad("Emissão"),400,40,100,20);
	adic(txtDtEmisPag,400,60,100,20);
	adic(new JLabelPad("Doc."),7,80,80,20);
	adic(txtDocPag,7,100,80,20);
	adic(new JLabelPad("Observações"),90,80,300,20);
	adic(txtObs,90,100,300,20);
    
    lcPagar.addPostListener(this);
  }
  private void testaCodPag() { //Traz o verdadeiro número do codvenda através do generator do banco
    PreparedStatement ps = null;
    ResultSet rs = null;
    try {
  	  ps = con.prepareStatement("SELECT * FROM SPGERANUM(?,?,?)");
	  ps.setInt(1,Aplicativo.iCodEmp);
	  ps.setInt(2,ListaCampos.getMasterFilial("FNPAGAR"));
	  ps.setString(3,"PA");
	  rs = ps.executeQuery();
      rs.next();
      txtCodPag.setVlrString(rs.getString(1));
//      rs.close();
//      ps.close();
//      con.commit();
    }
    catch (SQLException err) {
		Funcoes.mensagemErro(this,"Erro ao confirmar código da venda!\n"+err.getMessage());
    }
  }
  public void beforePost(PostEvent evt) { 
    if ((evt.getListaCampos().equals(lcPagar)) & (lcPagar.getStatus() == ListaCampos.LCS_INSERT)) {
        testaCodPag();
    }
  }
  public void afterPost(PostEvent evt) { }
  public void actionPerformed(ActionEvent evt) {
    if (evt.getSource() == btOK) {
      if (txtDtEmisPag.getVlrString().length() < 10) {
		Funcoes.mensagemInforma(this,"Data de emissão é requerido!");
      }
      else {
        if (lcPagar.getStatus() == ListaCampos.LCS_INSERT) {
          lcPagar.post();
        }
        else {
          super.actionPerformed(evt);
        }
      }
    }
    else {
      super.actionPerformed(evt);
    }
  }
  public void setConexao(Connection cn) {
  	super.setConexao(cn);
    lcFor.setConexao(cn);
    lcPlanoPag.setConexao(cn);
    lcPagar.setConexao(cn);
    lcItPagar.setConexao(cn);
    lcBanco.setConexao(cn);
    lcPagar.insert(true);
  }
}
