/**
 * @version 21/11/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FCredCli.java <BR>
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
 * Valor liberado por criente...
 * 
 */

package org.freedom.modulos.std;
import java.awt.event.ActionListener;
import java.sql.Connection;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.telas.FDados;

public class FCredCli extends FDados implements ActionListener {
  private JTextFieldPad txtCodCli = new JTextFieldPad(JTextFieldPad.TP_INTEGER,5,0);
  private JTextFieldFK txtDescCli = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldFK txtDataCli = new JTextFieldFK(JTextFieldPad.TP_DATE,10,0);
  private JTextFieldPad txtCodTipoCli = new JTextFieldPad(JTextFieldPad.TP_INTEGER,5,0);
  private JTextFieldFK txtDescTipoCli = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldPad txtCodTpCred = new JTextFieldPad(JTextFieldPad.TP_INTEGER,5,0);
  private JTextFieldFK txtDescTpCred = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldFK txtVlrTpCred = new JTextFieldFK(JTextFieldPad.TP_DECIMAL,15,3);
  private JTextFieldPad txtDtVencto = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
  private ListaCampos lcTipoCred = new ListaCampos(this,"TR");
  private ListaCampos lcTipoCli = new ListaCampos(this,"TI");
  public FCredCli() {
    setTitulo("Crédito por cliente");
    setAtribos(50, 50, 460, 205);

    lcTipoCli.add(new GuardaCampo( txtCodTipoCli, "CodTipoCli", "Cód.tp.cli.", ListaCampos.DB_PK, true));
    lcTipoCli.add(new GuardaCampo( txtDescTipoCli, "DescTipoCli", "Descrição do tipo de cliente", ListaCampos.DB_SI, false));
    lcTipoCli.montaSql(false, "TIPOCLI", "VD");    
    lcTipoCli.setQueryCommit(false);
    lcTipoCli.setReadOnly(true);
    txtCodTipoCli.setTabelaExterna(lcTipoCli);

    lcTipoCred.add(new GuardaCampo( txtCodTpCred, "CodTpCred", "Cód.tp.cred.", ListaCampos.DB_PK, true));
    lcTipoCred.add(new GuardaCampo( txtDescTpCred, "DescTpCred", "Descrição do tipo de crédito", ListaCampos.DB_SI, false));
    lcTipoCred.add(new GuardaCampo( txtVlrTpCred, "VlrTpCred", "Valor", ListaCampos.DB_SI, false));
    lcTipoCred.montaSql(false, "TIPOCRED", "FN");    
    lcTipoCred.setQueryCommit(false);
    lcTipoCred.setReadOnly(true);
    txtCodTpCred.setTabelaExterna(lcTipoCred);

    adicCampo(txtCodCli, 7, 20, 80, 20,"CodCli","Cód.cli.", ListaCampos.DB_PK, true);
    adicCampo(txtDescCli, 90, 20, 247, 20,"RazCli","Razão social do cliente", ListaCampos.DB_SI, false);
    adicCampo(txtDataCli, 340, 20, 100, 20,"DataCli","Cadastro", ListaCampos.DB_SI, false);
	adicCampoInvisivel(txtCodTipoCli, "CodTipoCli","Cód.tp.cli", ListaCampos.DB_FK, txtDescTipoCli,false);
    adicDescFK(txtDescTipoCli, 7, 60, 250, 20, "DescTipoCli", "Descrição do tipo de cliente");
	adicCampo(txtCodTpCred, 7, 100, 80, 20,"CodTpCred","Cód.tp.cred", ListaCampos.DB_FK, txtDescTpCred, true);
    adicDescFK(txtDescTpCred, 90, 100, 157, 20, "DescTpCred", "Descrição do crédito");
    adicDescFK(txtVlrTpCred, 250, 100, 87, 20, "VlrTpCred", "Valor");
	adicCampo(txtDtVencto, 340, 100, 100, 20,"DtVenctoTr","Vencimento", ListaCampos.DB_SI, txtDescTpCred, true);
    setListaCampos( true, "CLIENTE", "VD");
	lcCampos.setPodeIns(false);
	lcCampos.setPodeExc(false);
    lcCampos.setQueryInsert(false);
    
  }
  public void execShow(Connection cn) {
    lcTipoCli.setConexao(cn);
    lcTipoCred.setConexao(cn);
    super.execShow(cn);
  }        
}
