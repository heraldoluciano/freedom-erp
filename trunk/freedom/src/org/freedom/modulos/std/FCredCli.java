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
    setAtribos(50, 50, 440, 205);

    lcTipoCli.add(new GuardaCampo( txtCodTipoCli, 7, 100, 80, 20, "CodTipoCli", "Código", true, false, null, JTextFieldPad.TP_INTEGER,true),"txtCodTipoClix");
    lcTipoCli.add(new GuardaCampo( txtDescTipoCli, 90, 100, 207, 20, "DescTipoCli", "Descrição", false, false, null, JTextFieldPad.TP_STRING,false),"txtDescTipoClix");
    lcTipoCli.montaSql(false, "TIPOCLI", "VD");    
    lcTipoCli.setQueryCommit(false);
    lcTipoCli.setReadOnly(true);
    txtCodTipoCli.setTabelaExterna(lcTipoCli);

    lcTipoCred.add(new GuardaCampo( txtCodTpCred, 7, 100, 80, 20, "CodTpCred", "Código", true, false, null, JTextFieldPad.TP_INTEGER,true),"txtCodTipoClix");
    lcTipoCred.add(new GuardaCampo( txtDescTpCred, 90, 100, 207, 20, "DescTpCred", "Descrição", false, false, null, JTextFieldPad.TP_STRING,false),"txtDescTipoClix");
    lcTipoCred.add(new GuardaCampo( txtVlrTpCred, 90, 100, 207, 20, "VlrTpCred", "Valor", false, false, null, JTextFieldPad.TP_DECIMAL,false),"txtDescTipoClix");
    lcTipoCred.montaSql(false, "TIPOCRED", "FN");    
    lcTipoCred.setQueryCommit(false);
    lcTipoCred.setReadOnly(true);
    txtCodTpCred.setTabelaExterna(lcTipoCred);

    adicCampo(txtCodCli, 7, 20, 50, 20,"CodCli","Código",JTextFieldPad.TP_INTEGER,5,0,true,false,null,true);
    adicCampo(txtDescCli, 60, 20, 247, 20,"RazCli","Descrição",JTextFieldPad.TP_STRING,50,0,false,false,null,false);
    adicCampo(txtDataCli, 310, 20, 100, 20,"DataCli","Cadastro",JTextFieldPad.TP_DATE,10,0,false,false,null,false);
	adicCampoInvisivel(txtCodTipoCli, "CodTipoCli","Cod.Cli",JTextFieldPad.TP_INTEGER,8,0,false,true,txtDescTipoCli,false);
    adicDescFK(txtDescTipoCli, 7, 60, 250, 20, "DescTipoCli", "Tipo de cliente", JTextFieldPad.TP_STRING, 50, 0);
	adicCampo(txtCodTpCred, 7, 100, 50, 20,"CodTpCred","Código",JTextFieldPad.TP_INTEGER,8,0,false,true,txtDescTpCred,true);
    adicDescFK(txtDescTpCred, 60, 100, 147, 20, "DescTpCred", "e descrição do crédito", JTextFieldPad.TP_STRING, 50, 0);
    adicDescFK(txtVlrTpCred, 210, 100, 97, 20, "VlrTpCred", "Valor", JTextFieldPad.TP_DECIMAL, 15, 3);
	adicCampo(txtDtVencto, 310, 100, 100, 20,"DtVenctoTr","Vencimento",JTextFieldPad.TP_DATE,10,0,false,false,txtDescTpCred,true);
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
