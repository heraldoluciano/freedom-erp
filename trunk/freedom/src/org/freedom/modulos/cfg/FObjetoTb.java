/**
 * @version 30/05/2003 <BR>
 * @author Setpoint Informática Ltda./Anderson Sanchez <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.cfg <BR>
 * Classe: @(#)FObjetoTb.java <BR>
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

package org.freedom.modulos.cfg;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import org.freedom.acao.InsertEvent;
import org.freedom.acao.InsertListener;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Painel;
import org.freedom.telas.FDetalhe;

public class FObjetoTb extends FDetalhe implements InsertListener,ActionListener {
  private Painel pinCab = new Painel();
  private Painel pinDet = new Painel();
  private JTextFieldPad txtIDObj = new JTextFieldPad(JTextFieldPad.TP_STRING,30,0);
  private JTextFieldPad txtDescObj = new JTextFieldPad(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldPad txtCodTb = new JTextFieldPad(JTextFieldPad.TP_INTEGER,5,0);
  private JTextFieldFK txtDescTb = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
  private JCheckBoxPad cbRequerido = null;  
  private ListaCampos lcTabela = new ListaCampos(this,"TB");
  public FObjetoTb() {
    setTitulo("Vinculo entre tabelas físicas e auxiliares");
    setAtribos( 50, 20, 500, 350);
    setAltCab(90);
    pinCab = new Painel(500,90);
    
    txtDescObj.setAtivo(false);
    
	lcTabela.add(new GuardaCampo( txtCodTb,  7, 100, 80,  20, "CODTB",  "Cód.tab.",    true,  false, txtDescTb, JTextFieldPad.TP_INTEGER,false),"txtCodTb");
	lcTabela.add(new GuardaCampo( txtDescTb, 7, 100, 180, 20, "DESCTB", "Descriçao da tabela", false, false, null,      JTextFieldPad.TP_STRING, false),"txtDescTb");
	lcTabela.montaSql(false, "TABELA", "SG");    
	lcTabela.setQueryCommit(false);
	lcTabela.setReadOnly(true);

	txtCodTb.setTabelaExterna(lcTabela);
    txtCodTb.setNomeCampo("CODTB");    
    
    setListaCampos(lcCampos);
    setPainel( pinCab, pnCliCab);  
       
    adicCampo(txtIDObj, 7, 20, 70, 20,"IDObj","Id.obj.",ListaCampos.DB_PK,true);
    adicCampo(txtDescObj, 80, 20, 380, 20, "DescObj","Descrição do objeto",ListaCampos.DB_SI,true);
	lcCampos.setUsaFI(false);
    setListaCampos( false, "OBJETO", "SG");
    lcCampos.setReadOnly(true);
    lcCampos.setQueryInsert(false);
        
    setAltDet(60);
    pinDet = new Painel(590,110);
    setPainel( pinDet, pnDet);
    setListaCampos(lcDet);
    setNavegador(navRod);

	cbRequerido = new JCheckBoxPad("Requerido","S","N");
	cbRequerido.setVlrString("N");

    adicCampo(txtCodTb, 7, 20, 70, 20,"CODTB","Item",JTextFieldPad.TP_INTEGER,5,0,true,true,txtDescTb,true);
	adicDescFK(txtDescTb, 80, 20, 280, 20, "DESCTB", "Descrição da tabela",JTextFieldPad.TP_STRING, 50, 0);
	adicDB(cbRequerido, 365, 20, 90, 20, "ObrigObjTb", "",JTextFieldPad.TP_STRING,true);    
	lcDet.setUsaFI(false);
    setListaCampos( false, "OBJETOTB", "SG");
    lcCampos.setQueryInsert(false);
    montaTab();
    
    tab.setTamColuna(50,0);
    tab.setTamColuna(415,1);
    tab.setTamColuna(20,2);
    
    lcCampos.addInsertListener(this);
    btImp.addActionListener(this);
    btPrevimp.addActionListener(this);
  }
  public void execShow(Connection cn) {
	lcTabela.setConexao(cn);
	super.execShow(cn);
  }
  
  public void actionPerformed(ActionEvent evt) {
    super.actionPerformed(evt);
  }

   public void afterInsert(InsertEvent ievt) { }
   public void beforeInsert(InsertEvent ievt) { }

}
