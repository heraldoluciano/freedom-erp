/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Informática Ltda./Alex Rodrigues <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)DLFechaDistrib.java <BR>
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
 */

package org.freedom.modulos.pcp;
import java.awt.Component;
import java.math.BigDecimal;

import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FFDialogo;

public class DLFechaDistrib extends FFDialogo {
  private JTextFieldPad txtQtdDist = new JTextFieldPad(JTextFieldPad.TP_DECIMAL,15,Aplicativo.casasDec);
  private JTextFieldPad txtSeqDist = new JTextFieldPad(JTextFieldPad.TP_INTEGER,10,0);
  private JTextFieldPad txtCodProd = new JTextFieldPad(JTextFieldPad.TP_INTEGER,15,0);
  private JTextFieldPad txtDescProd = new JTextFieldPad(JTextFieldPad.TP_STRING,50,0);
  public DLFechaDistrib(Component cOrig,int iSeqDist, int iCodProd,String sDescProd, float ftQtdade) {
  	super(cOrig);
    setTitulo("Parcela");
    setAtribos(310,220);
   
    adic(new JLabelPad("Cód.Prod"),7,10,70,20);
    adic(txtCodProd,7,30,70,20);
    adic(new JLabelPad("Descrição da estrutura"),80,10,180,20);
    adic(txtDescProd,80,30,200,20);
    adic(new JLabelPad("Seq.dist."),7,50,80,20);
    adic(txtSeqDist,7,70,80,20);
    adic(new JLabelPad("Quantidade"),90,50,100,20);
    adic(txtQtdDist,90,70,110,20);
    
    txtCodProd.setVlrInteger(new Integer(iCodProd));
    txtDescProd.setVlrString(sDescProd);
    txtSeqDist.setVlrInteger(new Integer(iSeqDist));
    txtQtdDist.setVlrBigDecimal(new BigDecimal(ftQtdade));
    
    txtCodProd.setAtivo(false);
    txtDescProd.setAtivo(false);
    txtSeqDist.setAtivo(false);
  }
  public BigDecimal getValor() {
    BigDecimal bdRetorno = txtQtdDist.getVlrBigDecimal();
    return bdRetorno;
  }
}
