/**
 * @version 16/12/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FProcessaSL.java <BR>
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
 * Efetua somatórias nos lançamentos e insere saldos.
 * 
 */


package org.freedom.modulos.std;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import org.freedom.componentes.JLabelPad;

import org.freedom.acao.Processo;
import org.freedom.bmps.Icone;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.ProcessoSec;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FFilho;


/**
 * @author robson
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class FProcessaSL extends FFilho implements ActionListener {
    private JPanelPad pin = new JPanelPad();
	private JButton btProcessar = new JButton("Executar agora!",Icone.novo("btExecuta.gif"));
	private JLabelPad lbStatus = new JLabelPad();
	boolean bRunProcesso = false;
    public FProcessaSL() {
    	setTitulo("Processamento de saldo");
    	setAtribos(100,100,485,390);
    	
    	Container c = getContentPane();
    	c.setLayout(new BorderLayout());
        c.add(pin,BorderLayout.CENTER);

        pin.adic(new JLabelPad("--> PROCESSAMENTO DE SALDO <--"),10,15,250,20);

        JLabelPad lbAviso = new JLabelPad();
        lbAviso.setForeground(Color.RED);
        lbAviso.setText("<HTML> ATENÇÃO! <BR><BR>"+
                               " O processamento de saldo é uma rotina que exige muito processamento "+
                               "tando da estação cliente como do servidor. Assegure-se que neste momento "+
                               "nenhum outro usuário de outra estação esteja relizando tarefas que exijam "+
                               "agilidade no servidor.</HTML>"
                               );
      
        pin.adic(lbAviso,10,60,460,150);
        pin.adic(btProcessar,10,230,180,30);

    	lbStatus.setForeground(Color.BLUE);
      
        pin.adic(lbStatus,10,270,400,20);

		adicBotaoSair();

		btProcessar.addActionListener(this);
        state("Aguardando...");
    }
    private void processar() {
       boolean bOK = false;
       String sSQL = "DELETE FROM FNSALDOLANCA";
       try {
       	 state("Excluindo base atual de saldos...");
       	 PreparedStatement ps = con.prepareStatement(sSQL);
       	 ps.executeUpdate();
       	 ps.close();
       	 state("Base excluida...");
       	 bOK = true;
       }
       catch (SQLException err) {
       	Funcoes.mensagemErro(this,"Erro ao excluir os saldos!\n"+err.getMessage(),true,con,err);
       	err.printStackTrace();
       }
       if (bOK) {
       	 bOK = false;
         sSQL = "SELECT CODPLAN,DATASUBLANCA,SUM(VLRSUBLANCA) FROM "+
         "FNSUBLANCA WHERE CODEMP=? AND CODFILIAL=? GROUP BY CODPLAN,DATASUBLANCA "+
         "ORDER BY CODPLAN,DATASUBLANCA";
         try {
       	   state("Iniciando reconstrução...");
       	   PreparedStatement ps = con.prepareStatement(sSQL);
       	   ps.setInt(1,Aplicativo.iCodEmp);
       	   ps.setInt(2,ListaCampos.getMasterFilial("FNLANCA"));
       	   ResultSet rs = ps.executeQuery();
       	   String sPlanAnt = "";
       	   double dSaldo = 0;
       	   bOK = true;
       	   int iFilialPlan = ListaCampos.getMasterFilial("FNPLANEJAMENTO");
       	   int iFilialSaldo = ListaCampos.getMasterFilial("FNSALDOSL");
       	   while (rs.next() && bOK) {
       	   	  if (sPlanAnt.equals(rs.getString("CodPlan"))) {
       	   	  	dSaldo += rs.getDouble(3);
       	   	  }
       	   	  else
       	   	  	dSaldo = rs.getDouble(3);
       	 	  bOK = insereSaldo(iFilialSaldo,iFilialPlan,rs.getString("CodPlan"),rs.getDate("DataSubLanca"),dSaldo);
       	 	  sPlanAnt = rs.getString("CodPlan");
       	   }
       	   ps.close();
       	   state("Aguardando gravação final...");
         }
         catch (SQLException err) {
         	bOK = false;
         	Funcoes.mensagemErro(this,"Erro ao excluir os lançamentos!\n"+err.getMessage(),true,con,err);
       	    err.printStackTrace();
         }
       }
       try {
         if (bOK) {
         	if (!con.getAutoCommit())
         		con.commit();
       	   	state("Registros precessados com sucesso!");
          }
         else { 
       	   state("Registros antigos restaurados!");
       	   con.rollback();
         }
       }
       catch (SQLException err) {
         Funcoes.mensagemErro(this,"Erro ao relizar precedimento!\n"+err.getMessage(),true,con,err);
         err.printStackTrace();
       }
       bRunProcesso = false;
       btProcessar.setEnabled(true);
    } 
    private boolean insereSaldo(int iFilialSaldo,int iFilialPlan,String sCodPlan,Date dData,double dVal) {
    	boolean bRet = false;
    	String sSQL = "INSERT INTO FNSALDOLANCA "+
                     "(CODEMP,CODFILIAL,CODEMPPN,CODFILIALPN,CODPLAN,DATASL,PREVSL,SALDOSL)"+
    	             " VALUES (?,?,?,?,?,?,?,?)";
    	try {
    		state("Processano plan: "+sCodPlan+"...dia: "+Funcoes.sqlDateToStrDate(dData));
    		PreparedStatement ps = con.prepareStatement(sSQL);
    		ps.setInt(1,Aplicativo.iCodEmp);
    		ps.setInt(2,iFilialSaldo);
    		ps.setInt(3,Aplicativo.iCodEmp);
    		ps.setInt(4,iFilialPlan);
    		ps.setString(5,sCodPlan);
    		ps.setDate(6,dData);
   		    ps.setDouble(7,0);
   		    ps.setDouble(8,dVal);
   		    ps.executeUpdate();
   		    ps.close();
    		bRet = true;
    	}
    	catch (SQLException err) {
    		Funcoes.mensagemErro(this,"Erro ao excluir os lançamentos!"+"\n"+err.getMessage(),true,con,err);
    	}
    	return bRet;
    }
	public void actionPerformed(ActionEvent evt) {
		 if (evt.getSource() == btProcessar) {
			ProcessoSec pSec = new ProcessoSec(100,
			  new Processo() {
				public void run() {
				  lbStatus.updateUI();
				}
			  },
			  new Processo() {
				public void run() {
				  processar();
				}
			  }
			);
			bRunProcesso = true;
			btProcessar.setEnabled(false);
			pSec.iniciar();
		 }
	}
	public void state(String sStatus) {
		lbStatus.setText(sStatus);
	}
}
