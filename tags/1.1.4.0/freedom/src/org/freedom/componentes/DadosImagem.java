/**
 * @version 12/05/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.componentes <BR>
 * Classe: @(#)DadosImagem.java <BR>
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

package org.freedom.componentes;
import java.io.FileInputStream;

public class DadosImagem {
   private FileInputStream fisImagem;
   private int iTamanho = 0;
   private byte[] byImagem = null;
   
   public DadosImagem(FileInputStream fisIm, int iTam, byte[] byI) {
     byImagem = byI;
     fisImagem = fisIm;
     iTamanho = iTam;
   }
   
   public FileInputStream getInputStream() {
      return fisImagem;
   }
   
   public void setInputStream(FileInputStream fisIm)  {
     fisImagem = fisIm;
   }
   
   public int getTamanho() {
     return iTamanho;
   }
   
   public void setTamanho(int iTam) {
      iTamanho = iTam;
   }
   
   public byte[] getBytes() {
     return byImagem;
   }
   
   public void setBytes(byte[] byI) {
     byImagem = byI;
   }
        
}
