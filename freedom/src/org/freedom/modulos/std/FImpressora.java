/**
 * @version 11/02/2002 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FImpressora.java <BR>
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

package org.freedom.modulos.std;
import java.sql.Connection;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JComboBoxPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.telas.FDados;

import java.util.Vector;
public class FImpressora extends FDados {
  private JTextFieldPad txtCodImp = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
  private JTextFieldPad txtDescImp = new JTextFieldPad(JTextFieldPad.TP_STRING, 40, 0);
  //private JTextFieldPad txtLinPagImp = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
  private JTextFieldPad txtNSerieImp = new JTextFieldPad(JTextFieldPad.TP_STRING, 15, 0);
  //private JTextFieldPad txtPortaWinImp = new JTextFieldPad(JTextFieldPad.TP_STRING, 4, 0);
  //private JTextFieldPad txtPortaLinImp = new JTextFieldPad(JTextFieldPad.TP_STRING, 60, 0);
  private JTextFieldPad txtCodPapel = new JTextFieldPad(JTextFieldPad.TP_STRING,10,0);
  private JTextFieldFK txtDescPapel = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
  private JComboBoxPad cbTipoImp = null;
  private JComboBoxPad cbDestImp = null;
  private Vector vValsDest = new Vector();
  private Vector vLabsDest = new Vector();
  private Vector vValsTipo = new Vector();
  private Vector vLabsTipo = new Vector();
  private ListaCampos lcPapel = new ListaCampos(this,"PL");
  public FImpressora() {
  	super();
//Remove o painel de impressão:
    pnRodape.remove(2);
//Constroi a tela FImpressoras:
    setTitulo("Cadastro de impressoras");
    setAtribos( 50, 50, 420, 250);

//Prepara o Combo para alterar o campo txtTipoImp    
    vLabsTipo.addElement("<--Selecione-->");           
    vLabsTipo.addElement("Epson Matricial");           
    vLabsTipo.addElement("HP Desk Jet");           
    vLabsTipo.addElement("HP Laser Jet");           
    vLabsTipo.addElement("Epson Stylus");           
    vLabsTipo.addElement("Epson Laser");           
    vLabsTipo.addElement("Fiscal MP20");           
    vLabsTipo.addElement("Fiscal MP40");           
               
    vValsTipo.addElement(new Integer(0));           
    vValsTipo.addElement(new Integer(1));           
    vValsTipo.addElement(new Integer(2));           
    vValsTipo.addElement(new Integer(3));           
    vValsTipo.addElement(new Integer(4));           
    vValsTipo.addElement(new Integer(5));
    vValsTipo.addElement(new Integer(6));
    vValsTipo.addElement(new Integer(7));
    
    cbTipoImp = new JComboBoxPad(vLabsTipo, vValsTipo, JComboBoxPad.TP_INTEGER, 8, 0); 
    
    vLabsDest.addElement("<--Selecione-->");           
    vLabsDest.addElement("Nota fiscal");           
    vLabsDest.addElement("Nota fiscal - serviço");           
    vLabsDest.addElement("Pedido");           
    vLabsDest.addElement("Relatório simples");           
    vLabsDest.addElement("Relatório gráfico");           
    vLabsDest.addElement("Todos (não NF)");           
               
    vValsDest.addElement("");           
    vValsDest.addElement("NF");           
    vValsDest.addElement("NS");           
    vValsDest.addElement("PD");           
    vValsDest.addElement("RS");           
    vValsDest.addElement("RG");           
    vValsDest.addElement("TO");           
    
    cbDestImp = new JComboBoxPad(vLabsDest,vValsDest, JComboBoxPad.TP_STRING, 2, 0);
    //cbDestImp.setVlrString("");

//Prepara FKs

    lcPapel.add(new GuardaCampo( txtCodPapel, "CodPapel", "Cód.tp.papel", ListaCampos.DB_PK, true));
    lcPapel.add(new GuardaCampo( txtDescPapel, "DescPapel", "Descrição do tipo de papel", ListaCampos.DB_SI, false));
    lcPapel.montaSql(false, "PAPEL", "SG");    
    lcPapel.setQueryCommit(false);
    lcPapel.setReadOnly(true);
    txtCodPapel.setTabelaExterna(lcPapel);
//Adiciona componentes   
    adicCampo(txtCodImp, 7, 20, 90, 20, "CodImp", "Cód.imp.", ListaCampos.DB_PK, true);
    adicCampo(txtDescImp, 100, 20, 276, 20, "DescImp", "Descrição da impressora", ListaCampos.DB_SI, true);
    adicDB(cbTipoImp,7,60,140,25,"TipoImp", "Tipo de impressora", true);
    adicDB(cbDestImp, 150, 60, 140, 25, "DestImp", "Padrão para",true);
    //adicCampo(txtLinPagImp, 7, 105, 90, 20, "LinPagImp", "Lin.pag.imp.", ListaCampos.DB_SI, true);
    adicCampo(txtNSerieImp, 293, 60, 90, 25, "NSerieImp", "Num. serie", ListaCampos.DB_SI, false);
    //adicCampo(txtPortaWinImp, 193, 105, 90, 20, "PortaWinImp", "Porta WIN", ListaCampos.DB_SI, true);
    //adicCampo(txtPortaLinImp, 286, 105, 90, 20, "PortaLinImp", "Nome LIN", ListaCampos.DB_SI, true);
    adicCampo(txtCodPapel, 7, 105, 90, 20, "CodPapel", "Cód.tp.papel", ListaCampos.DB_FK, true);
    adicDescFK(txtDescPapel, 100, 105, 276, 20, "DescPapel", "Descrição do tipo de papel");

    setListaCampos(true, "IMPRESSORA", "SG");
  }
  public void setConexao(Connection cn) {
    super.setConexao(cn);
    lcPapel.setConexao(cn);      
  }
}
