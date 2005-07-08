/**
 * @version 14/11/2000 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 * 
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.telas <BR>
 * Classe:
 * @(#)FPrincipal.java <BR>
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
 * Comentários para a classe...
 */

package org.freedom.telas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JToolBar;
import javax.swing.border.Border;

import org.freedom.bmps.Icone;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JMenuPad;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.StatusBar;
import org.freedom.funcoes.Funcoes;
import org.freedom.modulos.atd.FAgenda;

public class FPrincipal extends JFrame implements ActionListener, MouseListener {

	private Connection con = null;
	public JMenuBar bar = new JMenuBar();
	private JToolBar tBar = new JToolBar();
	// public JMenuPad arquivoMenu = new JMenuPad();
	private JMenuItem sairMI = new JMenuItem();
	private Dimension tela = Toolkit.getDefaultToolkit().getScreenSize();
	private JButton btCalc = new JButton(Icone.novo("btCalc.gif"));
	private JButton btAgenda = new JButton(Icone.novo("btAgenda.gif"));
	public JPanelPad pinBotoesDir = new JPanelPad();
	public Container c = getContentPane();
	public JDesktopPane dpArea = new JDesktopPane();
	public StatusBar statusBar = new StatusBar();
  	private ImageIcon icStpinf = Icone.novo("lgSTP.jpg"); 
	private JLabelPad lbStpinf = new JLabelPad(icStpinf);
  	private ImageIcon icFreedom = Icone.novo("lgFreedom.jpg"); 
	private JLabelPad lbFreedom = new JLabelPad(icFreedom);
	private ImageIcon icFundo = null; 
	private JLabelPad lbFundo = null;
	private int iWidthImgFundo = 0;
	private int iHeightImgFundo = 0;
	private int iWidthImgStpinf = 0;
	private int iHeightImgStpinf = 0;
	private int iWidthImgFreedom = 0;
	private int iHeightImgFreedom = 0;
	private String sURLStpinf = "http://www.stpinf.com";
	private String sURLFreedom = "http://www.freedom.org.br";
	private Border borderStpinf = null;
	private Border borderFreedom = null;
	
	public FPrincipal(String sImgFundo) {
		c.setLayout(new BorderLayout());
		JPanelPad pn = new JPanelPad(JPanelPad.TP_JPANEL);
		pn.setLayout(new GridLayout(1, 1));

		setJMenuBar(bar);

		sairMI.setText("Sair");
		sairMI.setMnemonic('r');

		c.add(pn);

		btCalc.setPreferredSize(new Dimension(34, 34));
		btCalc.setToolTipText("Calculadora");
		btCalc.addActionListener(this);

		btAgenda.setPreferredSize(new Dimension(34, 34));
		btAgenda.setToolTipText("Agenda");
		btAgenda.addActionListener(this);
		pinBotoesDir.setBorder(null);
		c.add(tBar, BorderLayout.NORTH);
		tBar.setLayout(new BorderLayout());
		pinBotoesDir.setPreferredSize(new Dimension(102, 34));
		tBar.add(pinBotoesDir, BorderLayout.EAST);

		pinBotoesDir.add(btCalc);
		pinBotoesDir.add(btAgenda);

		montaStatus();

		int iWidthArea = (int) tela.getWidth();
		int iHeightArea = (int) tela.getHeight();
		
		setSize(iWidthArea, iHeightArea - 50);

		setExtendedState(MAXIMIZED_BOTH);
		c.add(dpArea, BorderLayout.CENTER);

//		dpArea.setBackground(new Color(69,62,113));
		dpArea.setBackground(new Color(153,153,204));
		icFundo = Icone.novo(sImgFundo); 
		lbFundo = new JLabelPad(icFundo);

		iWidthImgFundo = icFundo.getIconWidth();
		iHeightImgFundo = icFundo.getIconHeight();
		iWidthImgStpinf = icStpinf.getIconWidth();
		iHeightImgStpinf = icStpinf.getIconHeight();
		iWidthImgFreedom = icFreedom.getIconWidth();
		iHeightImgFreedom = icFreedom.getIconHeight();
		
	    lbFundo.setBounds((iWidthArea/2)-(iWidthImgFundo/2),((iHeightArea-200)/2)-(iHeightImgFundo/2),iWidthImgFundo,iHeightImgFundo);
	    lbStpinf.setBounds(20,iHeightArea-250,iWidthImgStpinf,iHeightImgStpinf);
	    lbFreedom.setBounds(iWidthArea-155,iHeightArea-265,iWidthImgFreedom,iHeightImgFreedom);
	    lbStpinf.setToolTipText(sURLStpinf);
	    lbFreedom.setToolTipText(sURLFreedom);
	    borderStpinf = lbStpinf.getBorder();
	    borderFreedom = lbFreedom.getBorder();
	    
	    dpArea.add(lbFundo);
	    dpArea.add(lbStpinf);
	    dpArea.add(lbFreedom);

	    lbFreedom.addMouseListener(this);
	    lbStpinf.addMouseListener(this);
	    
		sairMI.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fecharJanela();
			}
		});
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				fecharJanela();
			}
		});

	}

	private void setBordaURL(JComponent comp) {
		comp.setBorder(BorderFactory.createCompoundBorder(BorderFactory
				.createMatteBorder(0, 0, 0, 0, Color.BLUE), BorderFactory
				.createEtchedBorder()));

	}
	
	public void mouseClicked(MouseEvent arg0) {
		if ( (arg0.getSource()==lbStpinf) && (arg0.getClickCount()>=2) ) {
			Funcoes.executeURL(Aplicativo.strOS, Aplicativo.strBrowser, sURLStpinf);
           //System.out.println("lbStpinf "+arg0.getClickCount());		
		}
		else if ( (arg0.getSource()==lbFreedom) && (arg0.getClickCount()>=2) ) {
	       // System.out.println("lbFreedom "+arg0.getClickCount());		
			Funcoes.executeURL(Aplicativo.strOS, Aplicativo.strBrowser, sURLFreedom);
		}

	}
	public void mouseEntered(MouseEvent arg0) {
		if (arg0.getSource()==lbStpinf) {
	        setBordaURL(lbStpinf);		
		}
		else if (arg0.getSource()==lbFreedom) {
	        setBordaURL(lbFreedom);		
		}
	}
	public void mouseExited(MouseEvent arg0) {
		if (arg0.getSource()==lbStpinf) {
	        lbStpinf.setBorder(borderStpinf);		
			
		}
		else if (arg0.getSource()==lbFreedom) {
	        lbFreedom.setBorder(borderFreedom);		
		}
	}
	public void mousePressed(MouseEvent arg0) {

	}
	public void mouseReleased(MouseEvent arg0) {

	}
	public void addKeyListerExterno(KeyListener arg0) {
		this.addKeyListener(arg0);
		btCalc.addKeyListener(arg0);
		bar.addKeyListener(arg0);
		tBar.addKeyListener(arg0);
		btAgenda.addKeyListener(arg0);
	}

	public void montaStatus() {
		c.add(statusBar, BorderLayout.SOUTH);
	}

	public void remConFilial() {
		String sSQL = "EXECUTE PROCEDURE SGFIMCONSP(?,?,?)";
		try {
			PreparedStatement ps = con.prepareStatement(sSQL);
			ps.setInt(1, Aplicativo.iCodEmp);
			ps.setInt(2, Aplicativo.iCodFilialPad);
			ps.setString(3, Aplicativo.strUsuario);
			ps.execute();
			ps.close();
			if (!con.getAutoCommit())
				con.commit();
		} catch (SQLException err) {
			Funcoes.mensagemErro(this,
					"Erro ao remover filial ativa no banco!\n"
							+ err.getMessage());
		}
	}

	public void setConexao(Connection conGeral) {
		con = conGeral;
	}

	public void fecharJanela() {
		if (con != null) {
			try {
				remConFilial();
				con.close();
			} catch (java.sql.SQLException e) {
				Funcoes
						.mensagemErro(this,
								"Não foi possível fechar a conexao com o banco de dados!");
			}
		}
		System.exit(0);
	}

	public void adicFilha(Container filha) {
		dpArea.add(filha);
	}

	public void adicMenu(JMenuPad menu) {
		bar.add(menu);
	}

	/*
	 * public void adicItemArq(JMenuItemPad menu) { arquivoMenu.add(menu); }
	 * 
	 * public void setMenu() { arquivoMenu.addSeparator();
	 * arquivoMenu.add(sairMI);
	 *  } public void tiraEmp() { arquivoMenu.remove(0); }
	 */
	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == btCalc) {
			Calc calc = new Calc();
			dpArea.add("Calc", calc);
			calc.show();
		} else if (evt.getSource() == btAgenda) {
			if (this.temTela("Agenda") == false) {
				FAgenda tela = new FAgenda();
				this.criatela("Agenda", tela, con);
			}
		}

	}

	public boolean temTela(String nome) {
		boolean retorno = false;
		int i = 0;

		JInternalFrame[] telas = dpArea.getAllFrames();
		JInternalFrame tela = null;

		while (true) {

			try {
				tela = telas[i];
			} catch (java.lang.Exception e) {
				break;
			}

			if (tela == null) {
				break;
			} else if (tela.getName() == null) {
				i++;
				continue;
			} else if (tela.getName().equals(nome)) {
				try {
					tela.setSelected(true);
				} catch (Exception e) {
				}
				retorno = true;
				break;
			}

			i++;
		}

		return retorno;

	}

	public JInternalFrame getTela(String nome) {
		JInternalFrame retorno = null;

		JInternalFrame[] telas = dpArea.getAllFrames();
		JInternalFrame tela = null;

		for (int i = 0; i < telas.length; i++) {

			try {
				tela = telas[i];
			} catch (java.lang.Exception e) {
				break;
			}

			if (tela == null) {
				break;
			} else if (tela.getName() == null) {
				break;
			} else if (tela.getName().equals(nome)) {
				try {
					retorno = tela;
					break;
				} catch (Exception e) {
					retorno = null;
					break;
				}
			}
		}

		return retorno;

	}
	public void criatela(String nome, FFDialogo comp, Connection cn) {
		comp.setName(nome);
		comp.setTitulo(nome);
		comp.setConexao(cn);
		comp.execShow();
	}
	public void criatela(String nome, FFilho comp, Connection cn) {
		comp.setName(nome);
		comp.setTitulo(nome);
		dpArea.add(nome, comp);
		comp.setConexao(cn);
		comp.execShow();
		try {
			comp.setSelected(true);
		} catch (Exception e) {
		}
	}

	public void criatela(String nome, FDialogo comp, Connection cn) {
		comp.setName(nome);
		comp.setTitulo(nome);
		comp.setConexao(cn);
		comp.setVisible(true);
	}

	/**
	 * Ajusta a identificação do sistema. <BR>
	 * 
	 * @param sDesc -
	 *            Descrição do sistema.
	 * @param iCod -
	 *            Código do sistema.
	 * @param iMod -
	 *            Código do módulo.
	 *  
	 */

	public void setIdent(String sDesc, int iCod, int iMod) {
		setTitle(sDesc);
		/*
		 * arquivoMenu.setCodSistema(iCod); arquivoMenu.setCodModulo(iMod);
		 * arquivoMenu.setCodMenu(100000000); arquivoMenu.setNivel(0);
		 */
	}

	/**
	 * Adiciona um componente na barra de ferramentas. <BR>
	 * 
	 * @param comp -
	 *            Componente a ser adicionado.
	 *  
	 */

	public void adicCompInBar(Component comp, String sAling) {
		tBar.add(comp, sAling);
	}
}