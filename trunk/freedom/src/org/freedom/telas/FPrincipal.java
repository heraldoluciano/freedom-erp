/**
 * @version 14/11/2000 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.telas <BR>
 * Classe: @(#)FPrincipal.java <BR>
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
 * Comentários para a classe...
 */

package org.freedom.telas;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import org.freedom.bmps.Icone;
import org.freedom.componentes.JMenuPad;
import org.freedom.componentes.StatusBar;
import org.freedom.funcoes.Funcoes;

public class FPrincipal extends JFrame implements ActionListener {
   
   private Connection con = null;
   public  JMenuBar bar = new JMenuBar();
   private JToolBar tBar = new JToolBar();
// public  JMenuPad arquivoMenu = new JMenuPad();
   private JMenuItem sairMI = new JMenuItem();
   private Dimension tela = Toolkit.getDefaultToolkit().getScreenSize();
   private JButton btCalc = new JButton(Icone.novo("btCalc.gif"));
   public  Container c = getContentPane();
   public  JDesktopPane dpArea = new JDesktopPane();
   public  StatusBar statusBar = new StatusBar();
   public  FPrincipal() {
     c.setLayout(new BorderLayout());      

     JPanel pn = new JPanel();
     pn.setLayout(new GridLayout( 1, 1));

     setJMenuBar( bar );

//     arquivoMenu.setText("Arquivo");
//     arquivoMenu.setMnemonic('A');

     sairMI.setText("Sair");
     sairMI.setMnemonic('r');

//     bar.add(arquivoMenu);
     
     c.add(pn);
          
     btCalc.setPreferredSize(new Dimension(34,34));
     btCalc.setToolTipText("Calculadora");
     btCalc.addActionListener(this);
     
     c.add(tBar, BorderLayout.NORTH);
     tBar.setLayout(new BorderLayout());
     tBar.add(btCalc,BorderLayout.EAST);
      
     montaStatus();
     
     setSize((int)tela.getWidth() , (int)tela.getHeight()-50);
     		// - (tela.height / 11) // );
     setExtendedState(MAXIMIZED_BOTH);
     c.add( dpArea, BorderLayout.CENTER);
     sairMI.addActionListener(
        new ActionListener() {
           public void actionPerformed( ActionEvent e ) {
               fecharJanela();
           }
        }
     );
     addWindowListener(
       new WindowAdapter() {
         public void windowClosing( WindowEvent e ) {
           fecharJanela();
         }
       }
     );
    
     //setMaximizedBounds()
   }

   public void addKeyListerExterno(KeyListener arg0) {
   	 this.addKeyListener(arg0);
   	 btCalc.addKeyListener(arg0);
   	 bar.addKeyListener(arg0);
   	 tBar.addKeyListener(arg0);
   	 
   }
     
   public void montaStatus() {
     c.add(statusBar, BorderLayout.SOUTH);
   }
   
   public void remConFilial() {
   	 String sSQL = "EXECUTE PROCEDURE SGFIMCONSP(?,?,?)";
   	 try {
   	 	PreparedStatement ps = con.prepareStatement(sSQL);
   	 	ps.setInt(1,Aplicativo.iCodEmp);
   	 	ps.setInt(2,Aplicativo.iCodFilialPad);
   	 	ps.setString(3,Aplicativo.strUsuario);
   	 	ps.execute();
   	 	ps.close();
   	 	if (!con.getAutoCommit())
   	 		con.commit();
   	 }
   	 catch(SQLException err) {
		Funcoes.mensagemErro(this,"Erro ao remover filial ativa no banco!\n"+err.getMessage());
   	 }
   }
   
   public void setConexao(Connection conGeral) {
      con = conGeral;
   }
   public void fecharJanela() {
     if ( con != null  ) {
       try {
		 remConFilial();
         con.close();
       }
       catch (java.sql.SQLException e) {
          Funcoes.mensagemErro( this,"Não foi possível fechar a conexao com o banco de dados!");
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

/*   public void adicItemArq(JMenuItemPad menu) {
     arquivoMenu.add(menu);
   }

   public void setMenu() {
     arquivoMenu.addSeparator();
     arquivoMenu.add(sairMI);
 
   }     
   public void tiraEmp() {
     arquivoMenu.remove(0);     
   }*/
   public void actionPerformed( ActionEvent evt ) {
     if (evt.getSource() == btCalc) {
       Calc calc = new Calc();
       dpArea.add("Calc",calc);
       calc.show();
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
        }
        catch (java.lang.Exception e) {
           break;
        }
        
        if (tela == null) {
           break;
        } 
		else if (tela.getName() == null) {
		   break;
		} 
        else if (tela.getName().equals(nome)) {
	    try {
		      tela.setSelected(true);
    	}
    	catch(Exception e) { }
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
	
    
	for (int i=0; i<telas.length; i++) {

	   try {
		   tela = telas[i];
	   }
	   catch (java.lang.Exception e) {
		  break;
	   }
        
	   if (tela == null) {
		  break;
	   } 
	   else if (tela.getName() == null) {
		  break;
	   } 
	   else if (tela.getName().equals(nome)) {
	     try {
			 retorno = tela;
			 break;
	     }
	     catch(Exception e) { 
	       retorno = null;
	       break;
	     }
       }
    }
     
	return retorno;
     
 }
  
  public void criatela(String nome, FDados comp, Connection cn) {
    comp.setName(nome);
    dpArea.add(nome,comp);
    comp.execShow(cn);
    try {
      comp.setSelected(true);
    }
    catch(Exception e) { }
  }
  public void criatela(String nome, FFilho comp) {
    comp.setName(nome);
    dpArea.add(nome,comp);
    comp.show();
    try {
      comp.setSelected(true);
    }
    catch(Exception e) { }
  }

	/**
	*  Ajusta a identificação do sistema. <BR>
	*  @param sDesc - Descrição do sistema.
	*  @param iCod - Código do sistema.
	*  @param iMod - Código do módulo.
	*
	*/

	public void setIdent(String sDesc, int iCod, int iMod) {
		setTitle(sDesc);
/*		arquivoMenu.setCodSistema(iCod);
		arquivoMenu.setCodModulo(iMod);
		arquivoMenu.setCodMenu(100000000);
		arquivoMenu.setNivel(0);*/
	}

	/**
	*  Adiciona um componente na barra de ferramentas. <BR>
	*  @param comp - Componente a ser adicionado.
	*
	*/

	public void adicCompInBar(Component comp, String sAling) {
		tBar.add(comp,sAling);
	}
}