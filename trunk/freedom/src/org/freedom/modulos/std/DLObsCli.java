/**
 * @version 22/10/2004 <BR>
 * @author Setpoint Informática Ltda./Robson Sanchez <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)DLObsCli.java <BR>
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
 * Dialog de edição e inserção de observações por data nos clientes
 */
package org.freedom.modulos.std;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JScrollPane;

import org.freedom.componentes.JTextAreaPad;
import org.freedom.telas.FFDialogo;



/**
 * @author robson
 *
 * 
 */
public class DLObsCli extends FFDialogo {
    private JTextAreaPad txaObs = new JTextAreaPad();
    private JScrollPane spnObs = new JScrollPane(txaObs);

    public DLObsCli(Component cOrig) {
        super(cOrig);
        setAtribos(350,250);
        c.add(spnObs,BorderLayout.CENTER);
        //btOK.addActionListener(this);
        //btCancel.addActionListener(this);
    }
    
    public String getTexto() {
        return txaObs.getText();
    }
    
    public void setTexto(String sTexto) {
        txaObs.setText(sTexto);
    }
    
    /*public void actionPerformed(ActionEvent evt) {
        if (evt.getSource() == btOK) 
            OK = true;
        else if (evt.getSource() == btCancel)
            OK = false;
        super.actionPerformed(evt);
    }*/
}

