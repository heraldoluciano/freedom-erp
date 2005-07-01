/**
 * @version 25/03/2004 <BR>
 * @author Setpoint Informática Ltda./Anderson Sanchez <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.pcp <BR>
 * Classe: @(#)FBanco.java <BR>
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
 * Tela de cadastro de tipos de recursos de produção.
 */

package org.freedom.modulos.pcp;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import net.sf.jasperreports.engine.JasperPrintManager;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.FDados;
import org.freedom.telas.FPrinterJob;

public class FRecursos extends FDados implements ActionListener {
  private JTextFieldPad txtCodRecp = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtDescRecp = new JTextFieldPad(JTextFieldPad.TP_STRING,40,0);
  private JTextFieldPad txtCodTpRecp = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldFK txtDescTpRecp = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
  private ListaCampos lcTpRecp = new ListaCampos(this,"TR");
  public FRecursos () {
  	super();
    setTitulo("Cadastro de recursos de producão.");
    setAtribos( 50, 50, 355, 165);

    lcTpRecp.add(new GuardaCampo( txtCodTpRecp, "Codtprec", "Cód.rec.", ListaCampos.DB_PK, txtDescTpRecp, true));
    lcTpRecp.add(new GuardaCampo( txtDescTpRecp, "Desctprec", "Descriçao do recurso", ListaCampos.DB_SI, false));
    lcTpRecp.montaSql(false, "TIPOREC", "PP");    
    lcTpRecp.setQueryCommit(false);
    lcTpRecp.setReadOnly(true);
    txtCodTpRecp.setTabelaExterna(lcTpRecp);
    
    adicCampo(txtCodRecp, 7, 20, 70, 20,"Codrecp","Cód.rec.", ListaCampos.DB_PK, true);
    adicCampo(txtDescRecp, 80, 20, 240, 20,"descrecp","Descrição do recurso", ListaCampos.DB_SI, true);
    adicCampo(txtCodTpRecp, 7, 60, 70, 20, "Codtprec", "Cód.tp.rec.", ListaCampos.DB_FK, txtDescTpRecp, false);
    adicDescFK(txtDescTpRecp, 80, 60, 240, 20, "desctprec", "Descrição do tipo de recurso");
    setListaCampos( true, "RECURSO", "PP");
    btImp.addActionListener(this);
    btPrevimp.addActionListener(this);
//    lcCampos.setQueryInsert(false);    
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
    DLRRecursos dl = new DLRRecursos(this);
    dl.setVisible(true);
    if (dl.OK == false) {
      dl.dispose();
      return;
    }

    String sSQL = "SELECT CODRECP,DESCRECP FROM PPRECURSO ORDER BY "+dl.getValores().elementAt(0).toString();
    PreparedStatement ps = null;
    ResultSet rs = null;
    try {
      ps = con.prepareStatement(sSQL);
      rs = ps.executeQuery();
    }
    catch(Exception err){
		Funcoes.mensagemErro(this,"Erro consulta tabela de recursos de produção!"+err.getMessage(),true,con,err);
    	err.printStackTrace();
    }
    	
    if(dl.getValores().elementAt(1).equals("T")){      
      ImprimeOS imp = new ImprimeOS("",con);
      int linPag = imp.verifLinPag()-1;
      imp.montaCab();
      imp.setTitulo("Relatório de Recursos de produção");

      imp.limpaPags();
      try{
	      while ( rs.next() ) {
	         if (imp.pRow()==0) {
	            imp.impCab(80, false);
	            imp.say(imp.pRow()+0,0,""+imp.normal());
	            imp.say(imp.pRow()+0,0,"");
	            imp.say(imp.pRow()+0,2,"Código");
	            imp.say(imp.pRow()+0,30,"Descrição");
	            imp.say(imp.pRow()+1,0,""+imp.normal());
	            imp.say(imp.pRow()+0,0,Funcoes.replicate("-",79));
	         }
	         imp.say(imp.pRow()+1,0,""+imp.normal());
	         imp.say(imp.pRow()+0,2,rs.getString("Codrecp"));
	         imp.say(imp.pRow()+0,30,rs.getString("descrecp"));
	         if (imp.pRow()>=linPag) {
	            imp.incPags();
	            imp.eject();
	         }
	      }
	      
	      imp.say(imp.pRow()+1,0,""+imp.normal());
	      imp.say(imp.pRow()+0,0,Funcoes.replicate("=",79));
	      imp.eject();
	      
	      imp.fechaGravacao();
	      if (!con.getAutoCommit())
	      	con.commit();
	      dl.dispose();
	    }
        catch(Exception err){
    		Funcoes.mensagemErro(this,"Erro na impressão de recursos de produção!"+err.getMessage(),true,con,err);
        	err.printStackTrace();
        }
        if (bVisualizar) {
            imp.preview(this);
          }
          else {
            imp.print();
          }
    }
    else {
    	FPrinterJob dlGr = null;
		dlGr = new FPrinterJob("recursos.jasper",rs,this);
		if(bVisualizar)
			dlGr.setVisible(true);  
		else{			
			try {
				JasperPrintManager.printReport(dlGr.getRelatorio(),true);
			}
			catch(Exception err){
				Funcoes.mensagemErro(this,"Erro na impressão de recursos de produção!"+err.getMessage(),true,con,err);
			}
		}
    }
    
  }
  public void setConexao(Connection cn) {
  	super.setConexao(cn);
  	lcTpRecp.setConexao(cn);
  }
}
