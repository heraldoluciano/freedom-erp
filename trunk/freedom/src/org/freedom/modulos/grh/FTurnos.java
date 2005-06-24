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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.FDados;

public class FTurnos extends FDados implements ActionListener {
  private JTextFieldPad txtCodTurno = new JTextFieldPad(JTextFieldPad.TP_INTEGER,10,0);
  private JTextFieldPad txtDescTurno = new JTextFieldPad(JTextFieldPad.TP_STRING,60,0);
  private JTextFieldPad txtNhsTurno = new JTextFieldPad(JTextFieldPad.TP_INTEGER,4,0);
  private JTextFieldPad txtHIniTurno = new JTextFieldPad(JTextFieldPad.TP_TIME,8,0);
  private JTextFieldPad txtHIniIntTurno = new JTextFieldPad(JTextFieldPad.TP_TIME,8,0);
  private JTextFieldPad txtHFimIntTurno = new JTextFieldPad(JTextFieldPad.TP_TIME,8,0);
  private JTextFieldPad txtHFimTurno = new JTextFieldPad(JTextFieldPad.TP_TIME,8,0);
  private Vector vTipoTurnoLab = new Vector();
  private Vector vTipoTurnoVal = new Vector();  
  private JRadioGroup rgTipoTurno = null;
  
  public FTurnos () {
  	super();
    setTitulo("Cadastro de Turnos");
    setAtribos(50, 50, 440, 250);
	    
	      	
	    adicCampo(txtCodTurno, 7, 20, 80, 20,"CodTurno","Cód.turno", ListaCampos.DB_PK, true);
	    adicCampo(txtDescTurno, 90, 20, 223, 20,"DescTurno","Descrição do turno", ListaCampos.DB_SI, true);
	    adicCampo(txtNhsTurno, 316, 20, 100, 20,"NhsTurno","Nº H.semanais", ListaCampos.DB_SI, true);
	    adicCampo(txtHIniTurno, 7, 60, 100, 20,"HIniTurno","Inicío do turno", ListaCampos.DB_SI, true);
	    adicCampo(txtHIniIntTurno, 110, 60, 100, 20,"HIniIntTurno","Inicío do intervalo", ListaCampos.DB_SI, true);
	    adicCampo(txtHFimIntTurno, 213, 60, 100, 20,"HFimIntTurno","Fim do intervalo", ListaCampos.DB_SI, true);
	    adicCampo(txtHFimTurno, 316, 60, 100, 20,"HFimTurno","fim do turno", ListaCampos.DB_SI,true);	   
	    
/*	    txtHIniTurno.setStrMascara("##:##");
	    txtHIniIntTurno.setStrMascara("##:##");
	    txtHFimIntTurno.setStrMascara("##:##");
	    txtHFimTurno.setStrMascara("##:##"); */	    
	    
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
	  	rgTipoTurno = new JRadioGroup( 3, 2, vTipoTurnoLab, vTipoTurnoVal);	  	  	
	  	adicDB(rgTipoTurno, 7, 100, 409, 70, "TipoTurno", "Tipo de Turno:", true);
	  	rgTipoTurno.setVlrString("N");	  	
	  	setListaCampos( true, "TURNO", "RH");
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

  private void imprimir(boolean bVisualizar) {
    ImprimeOS imp = new ImprimeOS("",con);
    int linPag = imp.verifLinPag()-1;
    int iTot = 0;
    imp.montaCab();
    imp.setTitulo("Relatório de turnos");
    DLRFTurnos dl = new DLRFTurnos();
    dl.setVisible(true);
    if (dl.OK == false) {
      dl.dispose();
      return;
    }
    String sSQL = "SELECT CODTURNO, DESCTURNO, NHSTURNO, " +
    		"HINITURNO, HINIINTTURNO, HFIMINTTURNO, HFIMTURNO, TIPOTURNO " +
    		"FROM RHTURNOPK ORDER BY "+dl.getValor();
    		
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
            imp.say(imp.pRow()+1,0,"");
            imp.say(imp.pRow()+0,2,"Cód.turno");            
            imp.say(imp.pRow()+0,14,"Descrição do turno");            
            imp.say(imp.pRow()+0,5,"H/S");            
            imp.say(imp.pRow()+0,4,"Ent.");
            imp.say(imp.pRow()+0,2,"Ent.int.");           
            imp.say(imp.pRow()+0,2,"Fim.int.");            
            imp.say(imp.pRow()+0,2,"Fim.");            
            imp.say(imp.pRow()+0,2,"Tipo turno");
            imp.say(imp.pRow()+1,0,""+imp.normal());
            imp.say(imp.pRow()+0,0,Funcoes.replicate("-",80));
         }
         imp.say(imp.pRow()+1,0,""+imp.normal());
         imp.say(imp.pRow()+0,2,rs.getString("CodTurno"));        
         imp.say(imp.pRow()+0,14,rs.getString("DescTurno").substring(0,18));       
         imp.say(imp.pRow()+0,5,rs.getString("NhsTurno")); 
         imp.say(imp.pRow()+0,12,rs.getString("HIniTurno")); 
         imp.say(imp.pRow()+0,5,rs.getString("HIniIntTurno")); 
         imp.say(imp.pRow()+0,6,rs.getString("HFimIntTurno")); 
         imp.say(imp.pRow()+0,3,rs.getString("HFimTurno")); 
         imp.say(imp.pRow()+0,13,rs.getString("TipoTurno")); 
        
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
       Funcoes.mensagemErro(this,"Erro consulta tabela de funcionários!\n"+err.getMessage(),true,con,err);      
    }
    
    if (bVisualizar) {
      imp.preview(this);
    }
    else {
      imp.print();
    }
  }
}
