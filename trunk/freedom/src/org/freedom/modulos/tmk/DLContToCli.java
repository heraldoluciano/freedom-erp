/**
 * @version 26/01/2004 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.tmk <BR>
 * Classe: @(#)DLContToCli.java <BR>
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
 * Dialogo de ajuste para campos não compatíveis entre Contato e Cliente.
 */
package org.freedom.modulos.tmk;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.sql.Connection;

import org.freedom.componentes.JLabelPad;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.FFDialogo;

public class DLContToCli extends FFDialogo {
  private JTextFieldPad txtCodTipoCli = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldFK txtDescTipoCli = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldPad txtCodClasCli = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldFK txtDescClasCli = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldPad txtCodSetor = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldFK txtDescSetor = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
  private ListaCampos lcTipoCli = new ListaCampos(this,"");
  private ListaCampos lcClasCli = new ListaCampos(this,"");
  private ListaCampos lcSetor = new ListaCampos(this,"");
  public DLContToCli(Component cOrig, int iCodSetor) {
  	super(cOrig);
    setTitulo("Cópia de orçamento");
    setAtribos(320,210);
    
    lcTipoCli.add(new GuardaCampo( txtCodTipoCli, "CodTipoCli", "Cód.Cli.", ListaCampos.DB_PK, true));
    lcTipoCli.add(new GuardaCampo( txtDescTipoCli, "DescTipoCli", "Razão Social", ListaCampos.DB_SI, false));
    lcTipoCli.montaSql(false, "TIPOCLI", "VD");
    lcTipoCli.setReadOnly(true);
    txtCodTipoCli.setTabelaExterna(lcTipoCli);
    txtCodTipoCli.setFK(true);
    txtCodTipoCli.setNomeCampo("CodTipoCli");
    
    lcClasCli.add(new GuardaCampo( txtCodClasCli, "CodClasCli", "Código", ListaCampos.DB_PK, txtDescClasCli, true));
    lcClasCli.add(new GuardaCampo( txtDescClasCli, "DescClasCli", "Descriçao", ListaCampos.DB_SI, false));
    lcClasCli.montaSql(false, "CLASCLI", "VD");    
    lcClasCli.setReadOnly(true);
    txtCodClasCli.setTabelaExterna(lcClasCli);
    txtCodClasCli.setFK(true);
    txtCodClasCli.setNomeCampo("CodClasCli");
    
    lcSetor.add(new GuardaCampo( txtCodSetor, "CodSetor", "Código", ListaCampos.DB_PK, txtDescSetor, false));
    lcSetor.add(new GuardaCampo( txtDescSetor, "DescSetor", "Descriçao", ListaCampos.DB_SI, false));
    lcSetor.montaSql(false, "SETOR", "VD");    
    lcSetor.setReadOnly(true);
    txtCodSetor.setTabelaExterna(lcSetor);
    txtCodTipoCli.setFK(true);
    txtCodSetor.setNomeCampo("CodSetor");
    txtCodSetor.setText(""+iCodSetor);
    
    adic(new JLabelPad("Código e tipo de cliente"),7,5,250,20);
    adic(txtCodTipoCli,7,25,80,20);
    adic(txtDescTipoCli,90,25,200,20);
    adic(new JLabelPad("Código e classificação do cliente"),7,45,250,20);
    adic(txtCodClasCli,7,65,80,20);
    adic(txtDescClasCli,90,65,200,20);
    adic(new JLabelPad("Código e setor do cliente"),7,85,250,20);
    adic(txtCodSetor,7,105,80,20);
    adic(txtDescSetor,90,105,200,20);
    
  }
  public void actionPerformed(ActionEvent evt) {
    if (evt.getSource() == btOK) {
      if (txtCodTipoCli.getText().trim().length() == 0) {
		Funcoes.mensagemInforma(this,"O campo tipo de cliente está em branco! ! !");
        txtCodTipoCli.requestFocus();
        return;
      }
      if (txtCodClasCli.getText().trim().length() == 0) {
      	Funcoes.mensagemInforma(this,"O campo classificação do cliente está em branco! ! !");
      	txtCodClasCli.requestFocus();
      	return;
      }
    }
    super.actionPerformed(evt);
  }
  public void setConexao(Connection cn) {
  	lcTipoCli.setConexao(cn);
  	lcClasCli.setConexao(cn);
  	lcSetor.setConexao(cn);
  	lcSetor.carregaDados();
  }
  public int[] getValores() {
    int iRet[] = {
    		lcTipoCli.getCodFilial(),
			txtCodTipoCli.getVlrInteger().intValue(),
			lcClasCli.getCodFilial(),
			txtCodClasCli.getVlrInteger().intValue(),
			lcSetor.getCodFilial(),
			txtCodSetor.getVlrInteger().intValue()
    };
    return iRet;
  }
}

