/**
 * @version 05/06/2000 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 * 
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.telas <BR>
 * Classe:
 * @(#)FFDialogo.java <BR>
 * 
 * Este programa é licenciado de acordo com a LPG-PC (Licença Pública Geral para
 * Programas de Computador), <BR>
 * versão 2.1.0 ou qualquer versão posterior. <BR>
 * A LPG-PC deve acompanhar todas PUBLICAÇÕES, DISTRIBUIÇÕES e REPRODUÇÕES deste
 * Programa. <BR>
 * Caso uma cópia da LPG-PC não esteja disponível junto com este Programa, você
 * pode contatar <BR>
 * o LICENCIADOR ou então pegar uma cópia em: <BR>
 * Licença: http://www.lpg.adv.br/licencas/lpgpc.rtf <BR>
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa é
 * preciso estar <BR>
 * de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 * Comentários da classe.....
 */

package org.freedom.telas;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import org.freedom.componentes.JPanelPad;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

import org.freedom.bmps.Icone;

public class FFDialogo extends JDialog implements ActionListener,
        KeyListener, IFilho {

    private static final long serialVersionUID = 1L;

    protected FPrincipal fPrim;

    public static boolean comScroll = false;

    private Component firstFocus = null;

    private boolean initFirstFocus = true;

    public Connection con = null;

    public JButton btCancel = new JButton("Cancelar", Icone
            .novo("btCancelar.gif"));

    public JButton btOK = new JButton("OK", Icone.novo("btOk.gif"));

    private JPanelPad pnBox = new JPanelPad(JPanelPad.TP_JPANEL);

    public JPanelPad pnRodape = new JPanelPad(JPanelPad.TP_JPANEL,
            new BorderLayout());

    protected JPanelPad pnBotoes = new JPanelPad(JPanelPad.TP_JPANEL,
            new FlowLayout(FlowLayout.CENTER, 3, 3));

    private JPanelPad pnGrid = new JPanelPad(JPanelPad.TP_JPANEL,
            new GridLayout(1, 2));

    protected JPanelPad pnBordRodape = new JPanelPad(JPanelPad.TP_JPANEL,
            new BorderLayout());

    private JPanelPad pin = new JPanelPad();

    public Container c = getContentPane();

    private Border br = BorderFactory.createEtchedBorder();

    protected Component cPai = null;

    boolean setArea = true;

    boolean bUltimo = false;

    public boolean OK = false;

    public boolean bAguardando = false;

    public JComponent pnPai = null;

    public FFDialogo() {
        this(Aplicativo.telaPrincipal,true);
    }

    public FFDialogo(Component cOrig) {
        this(cOrig instanceof JFrame ? (JFrame) cOrig
                : Aplicativo.telaPrincipal,true);
        
        cPai = cOrig;

        //Gambs para tornar o form uma modal:

        if (cOrig instanceof JFrame)
            pnPai = ((JFrame) cOrig).getRootPane();
        else if (cOrig instanceof JDialog)
            pnPai = ((JDialog) cOrig).getRootPane();
        else if (cOrig instanceof JInternalFrame)
            pnPai = ((JInternalFrame) cOrig).getDesktopPane();
        else {
            pnPai = JOptionPane.getDesktopPaneForComponent(cOrig);
            if (pnPai == null) {
                Window win = SwingUtilities.getWindowAncestor(cOrig);
                if (win instanceof JDialog)
                    pnPai = ((JDialog) win).getRootPane();
                else if (win instanceof JFrame)
                    pnPai = ((JFrame) win).getRootPane();
            }
        }

    }
    public void setPrimeiroFoco(final JComponent comp) {
    	addWindowListener(
	    	new WindowAdapter() {
	    		public void windowActivated(WindowEvent wevt) {
	    			comp.requestFocusInWindow();
	    		}
	    	}
   		);
    }

    public FFDialogo(Frame fOrig,boolean bModal) {
        super(fOrig, "Dialogo", bModal);
        if (pnPai == null) {
            pnPai = Aplicativo.telaPrincipal.dpArea;
        }
        setTitle("Dialogo");
        setAtribos(50, 50, 500, 300);
        c.setLayout(new BorderLayout());

        //    pnGrid.setPreferredSize(new Dimension(220,30));
        btOK.setPreferredSize(new Dimension(110, 30));
        btCancel.setPreferredSize(new Dimension(110, 30));
        pnGrid.add(btOK);
        pnGrid.add(btCancel);
        pnBotoes.add(pnGrid);

        pnBox.add(pnBotoes);

        pnRodape.add(pnBox, BorderLayout.EAST);

        //    pnBordRodape.setPreferredSize(new Dimension(250,30));
        pnBordRodape.setBorder(br);
        pnBordRodape.add(pnRodape);

        c.add(pnBordRodape, BorderLayout.SOUTH);

        btCancel.addActionListener(this);
        btCancel.addKeyListener(this);
        btOK.addActionListener(this);
        btOK.addKeyListener(this);
        addKeyListener(this);
    }

    public void setPainel(JPanelPad p) {
        pin = p;
        setArea = false;
    }

    public void setPanel(JComponent p) {
        c.add(p, BorderLayout.CENTER);
        setArea = false;
    }

    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource() == btOK)
            ok();
        else if (evt.getSource() == btCancel)
            cancel();
    }

    public void ok() {
        OK = true;
        setVisible(false);
    }

    public void cancel() {
        OK = false;
        setVisible(false);
    }

    public void setTitulo(String tit) {
        if (getName() == null)
            setName(tit);
        setTitle(tit);
    }

    public void setAtribos(int X, int Y, int Larg, int Alt) {
        setBounds(X, Y, Larg, Alt);
    }

    public void setAtribos(int Larg, int Alt) {
        setBounds((pnPai.getSize().width - Larg) / 2,
                (pnPai.getSize().height - Alt) / 2, Larg, Alt);
    }

    public void eUltimo() {
        bUltimo = true;
    }

    public void setToFrameLayout() {
        pnRodape.remove(0);
        pnGrid = new JPanelPad(JPanelPad.TP_JPANEL, new GridLayout(1, 1));
        pnGrid.setPreferredSize(new Dimension(100, 30));
        JButton btSair = new JButton("Sair", Icone.novo("btSair.gif"));
        btSair.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                setVisible(false);
            }
        });
        pnGrid.add(btSair);
        pnRodape.add(pnGrid, BorderLayout.EAST);
    }

    public void setAreaComp() {
        pin = new JPanelPad((int) getSize().getWidth(), (int) getSize()
                .getHeight());
        c.add(pin, BorderLayout.CENTER);
        setArea = false;
    }

    public void adic(Component comp, int X, int Y, int Larg, int Alt) {
        if (setArea)
            setAreaComp();
        comp.addKeyListener(this);
        pin.adic(comp, X, Y, Larg, Alt);
    }

    public void adicInvisivel(Component comp) {
        comp.addKeyListener(this);
    }

    public void keyPressed(KeyEvent kevt) {
        if ((bUltimo) & (kevt.getKeyCode() == KeyEvent.VK_ENTER)
                & (btOK.isEnabled()))
            btOK.doClick();
        else if (kevt.getKeyCode() == KeyEvent.VK_ESCAPE)
            btCancel.doClick();
    }

    public void keyTyped(KeyEvent kevt) {
    }

    public void keyReleased(KeyEvent kevt) {
    }

    public void setTela(Container c) {
        setContentPane(c);
    }

    public Container getTela() {
        Container tela = getContentPane();
        tela.setLayout(new BorderLayout());
        return tela;
    }

    public JPanelPad adicBotaoSair() {
        Container c = getContentPane();
        JButton btSair = new JButton("Sair", Icone.novo("btSair.gif"));
        JPanelPad pnRod = new JPanelPad(JPanelPad.TP_JPANEL, new BorderLayout());
        pnRod.setPreferredSize(new Dimension(200, 30));
        btSair.setPreferredSize(new Dimension(110, 30));
        pnRod.add(btSair, BorderLayout.EAST);
        c.add(pnRod, BorderLayout.SOUTH);
        btSair.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                dispose();
            }
        });
        return pnRod;
    }

    public void setFirstFocus(Component firstFocus) {
        this.firstFocus = firstFocus;
    }

    public void firstFocus() {
        if ((firstFocus != null) && (firstFocus.hasFocus()) && (initFirstFocus))
            firstFocus.requestFocus();

        /*
         * if (firstFocus!=null) { if (firstFocus.hasFocus())
         * firstFocus.requestFocus(); else loadFirstFocus(); } else
         * loadFirstFocus();
         */
    }

    public void setConexao(Connection cn) {
        con = cn;
    }

    
    public void execShow() {
        setVisible(true);
    }

    public boolean getInitFirstFocus() {
        return initFirstFocus;
    }

    public void setInitFirstFocus(boolean initFirstFocus) {
        this.initFirstFocus = initFirstFocus;
    }

    public void setTelaPrim(FPrincipal fP) {
        fPrim = fP;
    }

    public void dispose() {
        System.gc();
        super.dispose();
    }
}