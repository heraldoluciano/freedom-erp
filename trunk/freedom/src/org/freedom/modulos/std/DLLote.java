/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)DLLote.java <BR>
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
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import org.freedom.componentes.JLabelPad;

import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FFDialogo;

public class DLLote extends FFDialogo {
  private JTextFieldPad txtCodLote = new JTextFieldPad(JTextFieldPad.TP_STRING,13,0);
  private JTextFieldPad txtCodProd = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtDataINILote = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
  private JTextFieldPad txtVenctoLote = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
  private JTextFieldFK txtDescProd = new JTextFieldFK();
  private JLabelPad lbCodLote = new JLabelPad("Cód.lote");
  private JLabelPad lbCodProd = new JLabelPad("Cód.prod.");
  private JLabelPad lbDescProd = new JLabelPad("Descrição do produto");
  private JLabelPad lbDataINILote = new JLabelPad("Data ini.");
  private JLabelPad lbVenctoLote = new JLabelPad("Vencimento");
  public DLLote (Component cOrig,String sCodLote, String sCodProd, String sDescProd, Connection cn) {
  	super(cOrig);
    setConexao(cn);
    setTitulo("Lote");
    setAtribos(400, 200);
    
    txtCodProd.setEditable(false);

    txtCodLote.setRequerido(true);
    txtVenctoLote.setRequerido(true);
         
    adic(lbCodLote,7,0,80,20);
    adic(txtCodLote,7,20,80,20);
    adic(lbDataINILote,90,0,97,20);
    adic(txtDataINILote,90,20,97,20);
    adic(lbVenctoLote,190,0,100,20);
    adic(txtVenctoLote,190,20,100,20);
    adic(lbCodProd,7,40,300,20);
    adic(txtCodProd,7,60,80,20);
    adic(lbDescProd,90,40,300,20);
    adic(txtDescProd,90,60,200,20);
    
    txtCodLote.setVlrString(sCodLote);
    txtCodProd.setVlrString(sCodProd);
    txtDescProd.setVlrString(sDescProd);

    txtCodLote.requestFocus();
  }
  private boolean gravaLote() {
  	boolean bRet = false;
    String sSQL = "INSERT INTO EQLOTE (CODEMP,CODFILIAL,CODLOTE,CODPROD,DINILOTE,VENCTOLOTE) VALUES(?,?,?,?,?,?)";
    PreparedStatement ps = null;
    try {
      ps = con.prepareStatement(sSQL);
      ps.setInt(1,Aplicativo.iCodEmp);
      ps.setInt(2,ListaCampos.getMasterFilial("EQLOTE"));
      ps.setString(3,txtCodLote.getText().trim());
      ps.setInt(4,txtCodProd.getVlrInteger().intValue());
      if (txtDataINILote.getVlrString().equals(""))
      	ps.setNull(5,Types.DATE);
      else
        ps.setDate(5,Funcoes.dateToSQLDate(txtDataINILote.getVlrDate()));
      ps.setDate(6,Funcoes.dateToSQLDate(txtVenctoLote.getVlrDate()));
      ps.executeUpdate();
      ps.close();
      if (!con.getAutoCommit())
      	con.commit();
      bRet = true;
    }
    catch(SQLException err) {
    	if (err.getErrorCode() == ListaCampos.FB_PK_DUPLA)
    		Funcoes.mensagemErro(this,"Este lote já existe!");
    	else
    	    Funcoes.mensagemErro(this,"Erro ao inserir registro na tabela EQLOTE!\n"+err.getMessage());
    }
    return bRet;
  }
  public String getValor() {
  	 return txtCodLote.getVlrString();
  }
  public void actionPerformed(ActionEvent evt) {
    if (evt.getSource() == btOK) {
      if (txtCodLote.getText().trim().length() == 0) {
		Funcoes.mensagemInforma(this,"Campo Código do lote é requerido!");
        txtCodLote.requestFocus();
      }
      else if (txtVenctoLote.getText().trim().length() == 0) {
		Funcoes.mensagemInforma(this,"Campo Vencimento do lote é requerido!");
        txtVenctoLote.requestFocus();
      }
      else {
        if (gravaLote())
          super.actionPerformed(evt);
      }
    }
    else if (evt.getSource() == btCancel) {
      super.actionPerformed(evt);
    }
  }
}