/**
 * @version 02/11/2003 <BR>
 * @author Setpoint Informática Ltda./Alex Rodrigues <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.grh <BR>
 * Classe: @(#)FTurnos.java <BR>
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
 * Tela de cadastro de turnos
 * 
 */

package org.freedom.modulos.grh;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.FDados;
public class FTurnos extends FDados implements ActionListener {
  private JTextFieldPad txtCodTurno = new JTextFieldPad(5);
  private JTextFieldPad txtDescTurno = new JTextFieldPad(60);
  private JTextFieldPad txtNhs = new JTextFieldPad(20);
  private JTextFieldPad txtHIniTurno = new JTextFieldPad(10);
  private JTextFieldPad txtHFimTurno = new JTextFieldPad(10);
  private JTextFieldPad txtHIniIntTurno = new JTextFieldPad(10);
  private JTextFieldPad txtHFimIntTurno= new JTextFieldPad(10);
  private Vector vTipoTurnoLab = new Vector();
  private Vector vTipoTurnoVal = new Vector();  
  private JRadioGroup rgTipoTurno = null;
  
  public FTurnos () {
    setTitulo("Cadastro de Turnos");
    setAtribos(50, 50, 400, 280);
    adicCampo(txtCodTurno, 7, 20, 80, 20,"CodTurno","Cód.turno",JTextFieldPad.TP_INTEGER,5,0,true,false,null,true);
    adicCampo(txtDescTurno, 90, 20, 280, 20,"DescTurno","Descrição do turno",JTextFieldPad.TP_STRING,40,0,false,false,null,true);
    adicCampo(txtHIniTurno, 7, 60, 170, 20,"HIniTurno","Hora de incido turno",JTextFieldPad.TP_INTEGER,5,0,true,false,null,true);
    adicCampo(txtHFimTurno, 7, 100, 170, 20,"HFimTurno","Hora do final do turno",JTextFieldPad.TP_INTEGER,5,0,true,false,null,true);
    adicCampo(txtHIniIntTurno, 7, 140, 170, 20,"HIniIntTurno","Hora do inicio do intervalo",JTextFieldPad.TP_INTEGER,5,0,true,false,null,true);
    adicCampo(txtHFimIntTurno, 7, 180, 170, 20,"HIniIntTurno","Hora do fim do in tervalo",JTextFieldPad.TP_INTEGER,5,0,true,false,null,true);
         
    setListaCampos( true,"TURNO","RH");
    btImp.addActionListener(this);
    btPrevimp.addActionListener(this);
    lcCampos.setQueryInsert(false);
    
    
    vTipoTurnoLab.addElement("Normal ( manhã e tarde )");
  	vTipoTurnoLab.addElement("Manhã");
  	vTipoTurnoLab.addElement("Tarde");
  	vTipoTurnoLab.addElement("Noite");
  	vTipoTurnoLab.addElement("Especial");
  	vTipoTurnoVal.addElement("N");
  	vTipoTurnoVal.addElement("M");
  	vTipoTurnoVal.addElement("T");
  	vTipoTurnoVal.addElement("O");
  	vTipoTurnoVal.addElement("E");
  	rgTipoTurno = new JRadioGroup( 5, 1, vTipoTurnoLab, vTipoTurnoVal);
  	  
  	
  	adicDB(rgTipoTurno, 180, 60, 200, 140, "TipoTurno", "Tipo de Turno:",JTextFieldPad.TP_STRING,true);
  	rgTipoTurno.setVlrString("N");
  	
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
    int iTot = 0;
    imp.montaCab();
    imp.setTitulo("Relatório de turnos de funcionários");
    DLRFTurnos dl = new DLRFTurnos();
    dl.setVisible(true);
    if (dl.OK == false) {
      dl.dispose();
      return;
    }
    String sSQL = "SELECT CODTURNO, DESCTURNO, HINITURNO, HFIMTURNO, HINIINTTURNO, HFIMINTTURNO FROM RHTURNO ORDER BY "+dl.getValor();
    		
    PreparedStatement ps = null;
    ResultSet rs = null;
    try {
      ps = con.prepareStatement(sSQL);
      rs = ps.executeQuery();
      imp.limpaPags();
      while ( rs.next() ) {
         if (imp.pRow()==0) {
            imp.impCab(80);
            imp.say(imp.pRow()+0,0,""+imp.normal());
            imp.say(imp.pRow()+0,0,"");
            imp.say(imp.pRow()+0,2,"Cód.turno");
            imp.say(imp.pRow()+0,20,"Descrição do turno");           
            imp.say(imp.pRow()+1,0,""+imp.normal());
            imp.say(imp.pRow()+0,0,Funcoes.replicate("-",80));
         }
         imp.say(imp.pRow()+1,0,""+imp.normal());
         imp.say(imp.pRow()+0,2,rs.getString("CodTurno"));
         imp.say(imp.pRow()+0,20,rs.getString("DescTurno"));       
        
         if (imp.pRow()>=linPag) {
            imp.incPags();
            imp.eject();
         }
      }
      
      imp.say(imp.pRow()+1,0,""+imp.normal());
      imp.say(imp.pRow()+0,0,Funcoes.replicate("=",80));
      imp.say(imp.pRow()+1,0,""+imp.normal());
      imp.say(imp.pRow()+0,0,"|");      
      imp.say(imp.pRow()+0,71,Funcoes.alinhaDir(iTot,8));
      imp.say(imp.pRow()+0,80,"|");
      imp.say(imp.pRow()+1,0,""+imp.normal());
      imp.say(imp.pRow()+0,0,Funcoes.replicate("=",80));
      imp.eject();
      
      imp.fechaGravacao();
      
//      rs.close();
//      ps.close();
      if (!con.getAutoCommit())
      	con.commit();
      dl.dispose();
    }  
    catch ( SQLException err ) {
       Funcoes.mensagemErro(this,"Erro consulta tabela de funcionários!"+err.getMessage());      
    }
    
    if (bVisualizar) {
      imp.preview(this);
    }
    else {
      imp.print();
    }
  }
}
