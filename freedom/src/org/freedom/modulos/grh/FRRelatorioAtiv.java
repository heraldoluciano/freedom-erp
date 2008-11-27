/**
 * @version 21/06/2008 <BR>
 * @author Setpoint Informática Ltda./Felipe Daniel Elias <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.grh <BR>
 * Classe:
 * @(#)FreedomGRH.java <BR>
 * 
 * Este programa é licenciado de acordo com a LPG-PC (Licença Pública Geral para Programas de Computador), <BR>
 * versão 2.1.0 ou qualquer versão posterior. <BR>
 * A LPG-PC deve acompanhar todas PUBLICAÇÕES, DISTRIBUIÇÕES e REPRODUÇÕES deste Programa. <BR>
 * Caso uma cópia da LPG-PC não esteja disponível junto com este Programa, você pode contatar <BR>
 * o LICENCIADOR ou então pegar uma cópia em: <BR>
 * Licença: http://www.lpg.adv.br/licencas/lpgpc.rtf <BR>
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa é preciso estar <BR>
 * de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 * Tela para filtro de relatório de atividades.
 * 
 */
package org.freedom.modulos.grh;


import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.util.Date;
import java.util.HashMap;
import javax.swing.BorderFactory;
import net.sf.jasperreports.engine.JasperPrintManager;

import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FPrinterJob;
import org.freedom.telas.FRelatorio;

public class FRRelatorioAtiv extends FRelatorio {
  private static final long serialVersionUID = 1L;
  private JTextFieldPad txtDataini = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0); 
  private JTextFieldPad txtDatafim = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0); 
  private JCheckBoxPad cbEncaminhados = new JCheckBoxPad("Encaminhados",new Boolean(true),new Boolean(false));
  
  public FRRelatorioAtiv() {
    setTitulo("Relatório de atividades");
    setAtribos(160,80,285,180);

    txtDataini.setVlrDate(Funcoes.getDataIniMes( Funcoes.getMes( new Date() ), Funcoes.getAno( new Date() ) ));
    txtDatafim.setVlrDate(Funcoes.getDataFimMes( Funcoes.getMes( new Date() ), Funcoes.getAno( new Date() ) ));
    
    JLabelPad lbLinha = new JLabelPad();
    lbLinha.setBorder(BorderFactory.createEtchedBorder());

    adic(new JLabelPad("Periodo:"),7,5,100,20);
    adic(lbLinha,60,15,210,2);
    adic(new JLabelPad("De:"),7,30,30,20);
    adic(txtDataini,32,30,97,20);
    adic(new JLabelPad("Até:"),135,30,30,20);
    adic(txtDatafim,165,30,97,20);
    
    
  }
  
  public void setConexao(Connection cn) {
    super.setConexao(cn);
  }
  
  public void actionPerformed(ActionEvent evt) {
      super.actionPerformed(evt);  	  	
  }  
  
  public void imprimir( boolean bVisualizar ) {
		
	  FPrinterJob dlGr = null;
	  HashMap<String, Object> hParam = new HashMap<String, Object>();

	  hParam.put( "CODEMP", Aplicativo.iCodEmp );
	  hParam.put( "CODFILIAL", ListaCampos.getMasterFilial( "RHVAGACANDIDATO" ) );
	  hParam.put( "DATAINI", txtDataini.getVlrDate() ) ;
	  hParam.put( "DATAFIM", txtDatafim.getVlrDate() ) ;
	  
	  dlGr = new FPrinterJob( "relatorios/grhAtividadeCand.jasper", "Resumo de atividades", "", this, hParam, con, null, false );

	  if ( bVisualizar ) {
		  dlGr.setVisible( true );
	  }
	  else {
		  try {
			  JasperPrintManager.printReport( dlGr.getRelatorio(), true );
		  } 
		  catch ( Exception e ) {
			  e.printStackTrace();
			  Funcoes.mensagemErro( this, "Erro na geração do relátorio!" + e.getMessage(), true, con, e );
		  }
	  }
  }

}
