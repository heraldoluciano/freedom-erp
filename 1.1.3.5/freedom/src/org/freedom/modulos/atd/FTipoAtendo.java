/**
 * @version 02/11/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.atd <BR>
 * Classe: @(#)FTipoAtendo.java <BR>
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

package org.freedom.modulos.atd;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.JPanelPad;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.FDetalhe;

public class FTipoAtendo extends FDetalhe implements ActionListener {
  private static final long serialVersionUID = 1L;
  private JPanelPad pinCab = new JPanelPad();
  private JPanelPad pinDet = new JPanelPad();
  private JTextFieldPad txtCodTipoAtendo = new JTextFieldPad(JTextFieldPad.TP_INTEGER,5,0);
  private JTextFieldPad txtDescTipoAtendo = new JTextFieldPad(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldPad txtCodSetAt = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldFK txtDescSetAt = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldPad txtCodFluxo = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldFK txtDescFluxo = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
  private ListaCampos lcSetAt = new ListaCampos(this,"ST");
  private ListaCampos lcFluxo = new ListaCampos(this,"FX");
  public FTipoAtendo () {
    setTitulo("Cadastro de Tipo de Atendimento");
    setAtribos( 50, 50, 430, 340);
    
	setListaCampos(lcCampos);
	setPainel( pinCab, pnCliCab);
    
	lcSetAt.add(new GuardaCampo( txtCodSetAt, "CodSetAt", "Cód.setor", ListaCampos.DB_PK,true));
	lcSetAt.add(new GuardaCampo( txtDescSetAt, "DescSetAt", "Descrição do setor", ListaCampos.DB_SI,false));
	lcSetAt.montaSql(false, "SETOR", "AT");
	lcSetAt.setQueryCommit(false);
	lcSetAt.setReadOnly(true);
	txtCodSetAt.setTabelaExterna(lcSetAt);
    
	lcFluxo.add(new GuardaCampo( txtCodFluxo, "CodFluxo", "Cód.fluxo", ListaCampos.DB_PK, true));
	lcFluxo.add(new GuardaCampo( txtDescFluxo, "DescFluxo", "Descrição do fluxo", ListaCampos.DB_SI,false));
	lcFluxo.montaSql(false, "FLUXO", "SG");
	lcFluxo.setQueryCommit(false);
	lcFluxo.setReadOnly(true);
	txtCodFluxo.setTabelaExterna(lcFluxo);

	setAltCab(130);

    adicCampo(txtCodTipoAtendo, 7, 20, 80, 20,"CodTpAtendo","Cód.tp.atend.",ListaCampos.DB_PK,true);
    adicCampo(txtDescTipoAtendo, 90, 20, 250, 20,"DescTpAtendo","Descrição do tipo de atendimento",ListaCampos.DB_SI,true);
	adicCampo(txtCodFluxo, 7, 60, 80, 20,"CodFluxo","Cód.fluxo",ListaCampos.DB_FK,txtDescFluxo,true);
	adicDescFK(txtDescFluxo, 90, 60, 250, 20,"DescFluxo","Descrição do fluxo");
    setListaCampos( true, "TIPOATENDO", "AT");

	setAltDet(60);
	setPainel( pinDet, pnDet);
	setListaCampos(lcDet);
	setNavegador(navRod);

	adicCampo(txtCodSetAt, 7, 20, 80, 20,"CodSetAt","Cód.setor",ListaCampos.DB_PF,txtDescSetAt,true);
	adicDescFK(txtDescSetAt, 90, 20, 250, 20,"DescSetAt","Descrição do setor");
	setListaCampos( false, "TIPOATENDOSETOR", "AT");
	montaTab();
	
	tab.setTamColuna(200,1);

    btImp.addActionListener(this);
    btPrevimp.addActionListener(this);
    lcCampos.setQueryInsert(false);   
    setImprimir(true);
  }
  public void actionPerformed(ActionEvent evt) {
    if (evt.getSource() == btPrevimp) {
        imprimir(true);
    }
    else if (evt.getSource() == btImp) 
        imprimir(false);
    super.actionPerformed(evt);
  }
  public void setConexao(Connection cn) {
	super.setConexao(cn);
	lcSetAt.setConexao(cn);
	lcFluxo.setConexao(cn);
  }
  private void imprimir(boolean bVisualizar) {
    ImprimeOS imp = new ImprimeOS("",con);
    int linPag = imp.verifLinPag()-1;
    imp.montaCab();
    imp.setTitulo("Relatório de Tipos de Atendimentos");
    DLRTipoAtendo dl = new DLRTipoAtendo();
    dl.setVisible(true);
    if (dl.OK == false) {
      dl.dispose();
      return;
    }
    String sSQL = "SELECT CODTPATENDO,DESCTPATENDO FROM ATTIPOATENDO ORDER BY "+dl.getValor();
    PreparedStatement ps = null;
    ResultSet rs = null;
    try {
      ps = con.prepareStatement(sSQL);
      rs = ps.executeQuery();
      imp.limpaPags();
      while ( rs.next() ) {
         if (imp.pRow()==0) {
            imp.impCab(80, false);
            imp.say(imp.pRow()+0,0,""+imp.normal());
            imp.say(imp.pRow()+0,0,"");
            imp.say(imp.pRow()+0,2,"Código");
            imp.say(imp.pRow()+0,25,"Descrição");
            imp.say(imp.pRow()+1,0,""+imp.normal());
            imp.say(imp.pRow()+0,0,Funcoes.replicate("-",79));
         }
         imp.say(imp.pRow()+1,0,""+imp.normal());
         imp.say(imp.pRow()+0,2,rs.getString("CodTpAtendo"));
         imp.say(imp.pRow()+0,25,rs.getString("DescTpAtendo"));
         if (imp.pRow()>=linPag) {
            imp.incPags();
            imp.eject();
         }
      }
      
      imp.say(imp.pRow()+1,0,""+imp.normal());
      imp.say(imp.pRow()+0,0,Funcoes.replicate("=",79));
      imp.eject();
      
      imp.fechaGravacao();
      
//      rs.close();
//      ps.close();
      if (!con.getAutoCommit())
      	con.commit();
      dl.dispose();
    }  
    catch ( SQLException err ) {
       Funcoes.mensagemErro(this,"Erro consulta tabela de tipos de atendimentos!"+err.getMessage(),true,con,err);      
    }
    
    if (bVisualizar) {
      imp.preview(this);
    }
    else {
      imp.print();
    }
  }
}
