/**
 * @version 27/04/2004 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: org.telas <BR>
 * Classe: @(#)FFWizard.java <BR>
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
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import org.freedom.componentes.Painel;

public class FWizard extends FFDialogo {
    private Painel pinCorpo = new Painel();
    private JPanel pnTit = new JPanel(new FlowLayout(FlowLayout.LEFT,15,10));
    private JPanel pnCorpo = new JPanel(new BorderLayout());
    private JPanel pnRod = new JPanel(new BorderLayout());
    private JPanel pnBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT,5,3));
    private JButton btVoltar = new JButton("< Voltar");
    private JButton btProximo = new JButton("Próximo >");
    private JButton btFinalizar = new JButton("Finalizar");
    private JButton btCancelar = new JButton("Cancelar");
    private JLabel lbTit = new JLabel("Título");
    Dimension dimBotao = new Dimension(100,30);
    Object oInfoCache[] = null;
    protected boolean podeVoltar = false;
    protected boolean podeProximo = true;
    protected boolean podeFinalizar = false;
    protected boolean podeCancelar = true;
    String sNivel = "1";
    public FWizard(Component cPai) {
        super(cPai);
        
//Reconstruindo a tela
        
        c.removeAll();
        
        pnTit.setBorder(new EtchedBorder());
        pnCorpo.setBorder(new EtchedBorder());
        pnRod.setBorder(new EtchedBorder());
        
        c.add(pnTit,BorderLayout.NORTH);
        c.add(pnCorpo,BorderLayout.CENTER);
        c.add(pnRod,BorderLayout.SOUTH);
        
//Montando painel de botoes
        
        btVoltar.setPreferredSize(dimBotao);
        btProximo.setPreferredSize(dimBotao);
        btFinalizar.setPreferredSize(dimBotao);
        btCancelar.setPreferredSize(dimBotao);
        
        JPanel pnNav = new JPanel(new GridLayout(1,2));
        pnNav.add(btVoltar);
        pnNav.add(btProximo);
        pnBotoes.add(pnNav);
        pnBotoes.add(btFinalizar);
        pnBotoes.add(btCancelar);
        pnRod.add(pnBotoes,BorderLayout.EAST);
        
//Montando o tit
        lbTit.setFont(lbTit.getFont().deriveFont((float)14.0));
        lbTit.setForeground(Color.BLUE);
        pnTit.add(lbTit);

//Montando o corpo
        pinCorpo.setBorder(BorderFactory.createEmptyBorder());
        pnCorpo.add(pinCorpo,BorderLayout.CENTER);
        
        btVoltar.setEnabled(false);
        btFinalizar.setEnabled(false);
        
        btVoltar.setMnemonic('V');
        btProximo.setMnemonic('P');
        btFinalizar.setMnemonic('F');
        
        btCancelar.setMnemonic('C');
        
        btVoltar.addActionListener(this);
        btProximo.addActionListener(this);
        btFinalizar.addActionListener(this);
        btCancelar.addActionListener(this);
    }
    public void upBotoes() {
       btVoltar.setEnabled(podeVoltar);   
       btProximo.setEnabled(podeProximo);   
       btFinalizar.setEnabled(podeFinalizar);   
       btCancelar.setEnabled(podeCancelar);   
    }
    public void setCabecalho(String sCab) {
    	lbTit.setText(sCab);
    }
    public void setPanel(JPanel pn) {
		pnCorpo.add(pn, BorderLayout.CENTER);
	}
	public void setPainel(Painel pin) {
        pnCorpo.remove(pinCorpo);
        pin.tiraBorda();
		pnCorpo.add(pin, BorderLayout.CENTER);
		pinCorpo = pin;
        pnCorpo.updateUI();
	}
    public void adic(Component comp,int x, int y, int larg, int alt) {
    	pinCorpo.adic(comp,x,y,larg,alt);
        comp.addKeyListener(this);
    }
    public String getNivel() {
       return sNivel;   
    }
	public void voltar() {
        return;
	}
	public String proximo() {
        return "1";
	}
	public void finalizar() {
		ok();
	}
	public void cancelar() {
		cancel();
	}
	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == btVoltar) {
            sNivel = sNivel.length() > 0 ? sNivel.substring(0,sNivel.length()-1) : sNivel;
			voltar();
        }
		else if (evt.getSource() == btProximo) {
            sNivel = sNivel+proximo();
        }
		else if (evt.getSource() == btFinalizar)
			finalizar();
		else if (evt.getSource() == btCancelar)
			cancelar();
        upBotoes();
		super.actionPerformed(evt);
	}
}
