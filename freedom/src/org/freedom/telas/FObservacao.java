/**
 * @version 14/11/2000 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.telas <BR>
 * Classe: @(#)FObservacao.java <BR>
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
 * Classe para implementação de observações..<BR>
 * ATENÇÂO!! ESTA CLASSE É DERIVADA DE FDialogo E NÂO FFDialogo. 
 */
package org.freedom.telas;
import java.awt.GridLayout;
import org.freedom.componentes.JPanelPad;
import javax.swing.JScrollPane;

import org.freedom.componentes.JTextAreaPad;
public class FObservacao extends FDialogo {
  private JPanelPad pn = new JPanelPad(JPanelPad.TP_JPANEL,new GridLayout(1,1));
  private JTextAreaPad txa = new JTextAreaPad();
  private JScrollPane spn = new JScrollPane(txa);
  /**
   * 
   * Construtor sem titulo.
   * 
   * @param sPad - Texto inicial.
   */
  public FObservacao(String sPad) {
  	this(null,sPad,0);
  }
  /**
   * 
   * Construtor com titulo.
   * 
   * @param sTit - Título da janela.
   * @param sPad - Texto inicial.
   */
  public FObservacao(String sTit, String sPad) {
  	this(sTit,sPad,0);
  }
  public FObservacao(String sTit, String sPad, int iTam) {
  	if (sTit != null)
  		setTitulo(sTit);
    else
        setTitulo("Observação");
    setAtribos(250,180);      
    pn.add(spn);
    c.add(pn);
    txa.setText(sPad);
    if (iTam > 0)
      txa.iTamanho = iTam;
  }
  /**
   * 
   * Retorna o texto digitado na dialog de observação.
   * @return sTexto - Texto digitado.
   * 
   */
  public String getTexto() {
  	String sTexto = txa.getText();
    return sTexto;
  }
}
