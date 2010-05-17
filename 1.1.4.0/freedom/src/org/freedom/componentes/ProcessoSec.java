/**
 * @version 23/01/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.componentes <BR>
 * Classe: @(#)ProcessoSec.java <BR>
 * 
 * Este programa é licenciado de acordo com a LPG-PC (Licença Pública Geral para Programas de Computador), <BR>
 * versão 2.1.0 ou qualquer versão posterior. <BR>
 * A LPG-PC deve acompanhar todas PUBLICAÇÕES, DISTRIBUIÇÕES e REPRODUÇÕES deste Programa. <BR>
 * Caso uma cópia da LPG-P
 * C não esteja disponível junto com este Programa, você pode contatar <BR>
 * o LICENCIADOR ou então pegar uma cópia em: <BR>
 * Licença: http://www.lpg.adv.br/licencas/lpgpc.rtf <BR>
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa é preciso estar <BR>
 * de acordo com os termos da LPG-PC <BR> <BR>
 *
 * Comentários da classe.....
 */

package org.freedom.componentes;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.Timer;

import org.freedom.acao.Processo;
public class ProcessoSec implements Processo {
  private Thread th = null;
  private Timer tim = null;
  private Processo pPros = this;
  private Processo pTimer = this;
  public ProcessoSec(int iTempo,Processo pTim,Processo proc) {
    pPros = proc;
    pTimer = pTim;
    tim = new Timer(iTempo,
      new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
          pTimer.run();
        }
      }
    );
    th = new Thread(proc);
  }
  public void iniciar() {
    if (th == null) 
      th = new Thread(pPros);
    th.start();
    tim.start();
  }
  public void parar() {
    th.interrupt();
    th = null;
    tim.stop();
  }
  public int getTempo() {
    return tim.getDelay();
  }
  public void run() { }
}

