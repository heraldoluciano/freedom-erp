/**
 * @version 23/02/2004 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.telas <BR>
 * Classe: @(#)DLF3.java <BR>
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
 * Comentários da classe.....
 */

package org.freedom.telas;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JScrollPane;

import org.freedom.componentes.Tabela;

public abstract class DLF3 extends FFDialogo implements KeyListener {
  public Tabela tab = new Tabela();
  private JScrollPane spnCentro = new JScrollPane(tab); 
  public Object oRetVal = null;
  /**
   * 
   *  Classe mãe para dialogos auxiliares.
   *  Construtor da classe...criado grid com <BR>
   *  2 colunas "origem e código aux", <BR>
   *  origem: origem da chave, ex: tabela de preços. <BR>
   *  código aux.: código auxilial para busca. <BR>
   * 
   * @param cOrig - Janela mãe do dialogo.
   */
  public DLF3(Component cOrig) {
  	super(cOrig);
    setTitulo("Pesquisa auxiliar");
    setAtribos( 550, 260);
    setResizable(true);
    
    c.add( spnCentro, BorderLayout.CENTER);    
    
    addWindowFocusListener(
       new WindowAdapter() {
		 public void windowGainedFocus(WindowEvent e) {
            if (tab.getNumLinhas() > 0) {
              tab.requestFocus();
//              tab.setLinhaSel(0); 
            }
            else
              btCancel.requestFocus();
		 }
       }
    );
  }
  public boolean setValor(Object oVal,String sTipo) {
  	return false;
  }
  public Object getValor() {
    return oRetVal;
  }
  public Object getValorGrid() {
  	Object oRet = null;
  	if (tab.getNumLinhas() > 0 && tab.getLinhaSel() >= 0)
  		oRet = tab.getValor(tab.getLinhaSel(),1);
  	return oRet;  	 	
  }
  public void keyPressed(KeyEvent kevt) {
    if ( kevt.getSource() == tab && kevt.getKeyCode() == KeyEvent.VK_ENTER) {       
      if (tab.getNumLinhas() > 0) {
      	
//Esquematicos para acertar a linha selecionada...
//Quando o form fechar a linha ira pular uma vez uma vez para baixo...
//então eu volto uma linha aqui:

        if (tab.getLinhaSel()==tab.getNumLinhas()-1){
            tab.setLinhaSel(tab.getNumLinhas()-1);
            btOK.doClick();            
        }
        else {
            if (tab.getLinhaSel() > 0)
                tab.setLinhaSel(tab.getLinhaSel()-1);
            else
                tab.setLinhaSel(tab.getNumLinhas()-1);      	
            btOK.doClick();
        }
      }
    }
    else
      super.keyPressed(kevt);
  }
  public void keyReleased(KeyEvent kevt) { }
  public void keyTyped(KeyEvent kevt) { }

}

