/**
 * @version 30/05/2003 <BR>
 * @author Setpoint Informática Ltda./Anderson Sanchez <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: projetos.freeedomcfg <BR>
 * Classe: @(#)FTabela.java <BR>
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

import org.freedom.acao.InsertEvent;
import org.freedom.acao.InsertListener;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.telas.FDetalhe;

public class FTabela extends FDetalhe implements InsertListener,ActionListener {
  private static final long serialVersionUID = 1L;
  private JPanelPad pinCab = new JPanelPad();
  private JPanelPad pinDet = new JPanelPad();
  private JTextFieldPad txtCodTb = new JTextFieldPad(JTextFieldPad.TP_INTEGER,5,0);
  private JTextFieldPad txtDescTb = new JTextFieldPad(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldPad txtSiglaTb = new JTextFieldPad(JTextFieldPad.TP_STRING,10,0);
  private JTextFieldPad txtCodItTb = new JTextFieldPad(JTextFieldPad.TP_INTEGER,5,0);
  private JTextFieldPad txtDescItTb = new JTextFieldPad(JTextFieldPad.TP_STRING,50,0);
  private JCheckBoxPad cbPadrao = null;  
  public FTabela() {
    setTitulo("Cadastro de Tabelas auxiliares");
    setAtribos( 50, 20, 500, 350);
    setAltCab(90);
    pinCab = new JPanelPad(500,90);
    setListaCampos(lcCampos);
    setPainel( pinCab, pnCliCab);
       
    adicCampo(txtCodTb, 7, 20, 70, 20,"CodTb","Cód.tab.", ListaCampos.DB_PK, true);
    adicCampo(txtDescTb, 80, 20, 297, 20, "DescTb","Descrição da tabela", ListaCampos.DB_SI, true);
    adicCampo(txtSiglaTb, 380, 20, 80, 20, "SiglaTb","Sigla", ListaCampos.DB_SI, true);
  
    setListaCampos( true, "TABELA", "SG");
    lcCampos.setQueryInsert(false);
    setAltDet(60);
    pinDet = new JPanelPad(590,110);
    setPainel( pinDet, pnDet);
    setListaCampos(lcDet);
    setNavegador(navRod);

	cbPadrao = new JCheckBoxPad("Padrão","S","N");
	cbPadrao.setVlrString("N");

    adicCampo(txtCodItTb, 7, 20, 70, 20,"CodItTb","Item", ListaCampos.DB_PK, true);
    adicCampo(txtDescItTb, 80, 20, 280, 20,"DescItTb","Descrição do item", ListaCampos.DB_SI, true);
	adicDB(cbPadrao, 365, 20, 90, 20, "PadraoIttb", "", true);    
    
    setListaCampos( true, "ITTABELA", "SG");
    lcCampos.setQueryInsert(false);
    montaTab();
    
    tab.setTamColuna(70,0);
    tab.setTamColuna(395,1);
    tab.setTamColuna(20,2);
    
    lcCampos.addInsertListener(this);
    btImp.addActionListener(this);
    btPrevimp.addActionListener(this);
  }
  public void actionPerformed(ActionEvent evt) {
    super.actionPerformed(evt);
  }

   public void afterInsert(InsertEvent ievt) { }
   public void beforeInsert(InsertEvent ievt) { }

}
