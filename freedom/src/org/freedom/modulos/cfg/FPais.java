/**
 * @version 25/06/2004 <BR>
 * @author Setpoint Informática Ltda./Anderson Sanchez <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.cfg <BR>
 * Classe: @(#)FPais.java <BR>
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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.FDados;

public class FPais extends FDados implements ActionListener {
  private JTextFieldPad txtCodPais = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtNomePais = new JTextFieldPad(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldPad txtSiglaPais = new JTextFieldPad(JTextFieldPad.TP_STRING,10,0);
  private JTextFieldPad txtDDIPais = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  public FPais () {
    setTitulo("Cadastro de Paises");
    setAtribos( 50, 50, 400, 125);
    
    lcCampos.setUsaME(false);
    
    adicCampo(txtCodPais, 7, 20, 70, 20,"CodPais","Cód.pais",ListaCampos.DB_PK,true);
    adicCampo(txtNomePais, 80, 20, 177, 20,"NomePais","Nome do pais",ListaCampos.DB_SI,true);
    adicCampo(txtSiglaPais, 260, 20, 77, 20,"SiglaPais","Sigla",ListaCampos.DB_SI,true);
    adicCampo(txtDDIPais, 340, 20, 40, 20,"DDIPais","DDI",ListaCampos.DB_SI,false);
    setListaCampos( true, "PAIS", "SG");
    btImp.addActionListener(this);
    btPrevimp.addActionListener(this);
    lcCampos.setQueryInsert(false);    
  }
  public void actionPerformed(ActionEvent evt) {
    if (evt.getSource() == btPrevimp) {
        imprimir(true);
    }
    else if (evt.getSource() == btImp) 
      imprimir(false);
    super.actionPerformed(evt);
  }

  private void imprimir(boolean bVisualizar) {
    ImprimeOS imp = new ImprimeOS("",con);
    int linPag = imp.verifLinPag()-1;
    imp.montaCab();
    imp.setTitulo("Relatório de paises cadastrados");
    String sSQL = "SELECT CODPAIS,NOMEPAIS,SIGLAPAIS,DDIPAIS FROM SGPAIS ORDER BY NOMEPAIS";
    PreparedStatement ps = null;
    ResultSet rs = null;
    try {
      ps = con.prepareStatement(sSQL);
      rs = ps.executeQuery();
      imp.limpaPags();
      while ( rs.next() ) {
         if (imp.pRow()==0) {
            imp.impCab(136, false);
            imp.say(imp.pRow()+0,0,""+imp.normal());
            imp.say(imp.pRow()+0,0,"");
            imp.say(imp.pRow()+0,2,"Cód.pais");
            imp.say(imp.pRow()+0,15,"Nome");
            imp.say(imp.pRow()+0,80,"Sigla");
            imp.say(imp.pRow()+0,100,"DDI");
            imp.say(imp.pRow()+1,0,""+imp.normal());
            imp.say(imp.pRow()+0,0,Funcoes.replicate("-",136));
         }
         imp.say(imp.pRow()+1,0,""+imp.normal());
         imp.say(imp.pRow()+0,0,"");
         imp.say(imp.pRow()+0,2,rs.getString("CodPais"));
         imp.say(imp.pRow()+0,15,rs.getString("NomePais"));
         imp.say(imp.pRow()+0,80,rs.getString("SiglaPais"));
         imp.say(imp.pRow()+0,100,rs.getString("DDIPais") != null ? rs.getString("DDIPais") : "");
         if (imp.pRow()>=linPag) {
            imp.incPags();
            imp.eject();
         }
      }
      
      imp.say(imp.pRow()+1,0,""+imp.normal());
      imp.say(imp.pRow()+0,0,Funcoes.replicate("=",136));
      imp.eject();
      
      imp.fechaGravacao();
      
//      rs.close();
//      ps.close();
      if (!con.getAutoCommit())
      	con.commit();
    }  
    catch ( SQLException err ) {
		Funcoes.mensagemErro(this,"Erro consulta tabela de paises!"+err.getMessage());      
    }
    
    if (bVisualizar) {
      imp.preview(this);
    }
    else {
      imp.print();
    }
  }
}
