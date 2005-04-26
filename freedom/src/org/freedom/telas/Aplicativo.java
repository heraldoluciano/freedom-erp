/**
 * @version 05/06/2000 <BR>
 * @author Setpoint Informática Ltda./Robson Sanchez <BR>
 * 
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.telas <BR>
 * Classe:
 * @(#)Aplicativo.java <BR>
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
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import org.freedom.bmps.Icone;
import org.freedom.bmps.Imagem;
import org.freedom.componentes.JButtonPad;
import org.freedom.componentes.JMenuItemPad;
import org.freedom.componentes.JMenuPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.TabObjeto;
import org.freedom.funcoes.Funcoes;

public abstract class Aplicativo implements ActionListener, KeyListener {
	public final static int TP_OPCAO_MENU = 0;

	public final static int TP_OPCAO_ITEM = 1;

	public static int casasDec = 2;

	public Connection con = null; // Variavel de conexao com o banco de dados

	protected String strSenha = "";

	public static FPrincipal telaPrincipal = null;

	public static Component framePrinc = null;

	public static String strUsuario = "";

	public static String strTemp = "";

	public static String strOS = "";

	public static String strSplash = "";

	public static int iCodEmp = 0;

	public static int iCodFilial = 0;
	
	public static String sRazFilial = "";

	public static int iCodFilialMz = 0;

	public static int iCodFilialPad = 0;

	public static int iNumEst = 0;

	public static String strBanco = "";

	public static String strDriver = "";

	public static String strTefEnv = "";

	public static String strTefRet = "";

	public static TabObjeto tbObjetos = null;

	public static ImageIcon imgIcone = null;

	public Vector vArqINI = null;

	public String[][][] sConfig = new String[0][0][0];

	public JPanelPad pinBotoes = new JPanelPad(30, 30);

	public int iXPanel = 0;

	public static boolean bBuscaProdSimilar = false;
	
	public static String sMultiAlmoxEmp = "N";

	private static String sFiltro = "";

	private boolean bCtrl = true;

	private static boolean bAutoCommit = false;

	private String sSplashImg = "";

	private JButton btAtualMenu = new JButton(Icone.novo("btAtualMenu.gif"));

	private Vector vOpcoes = null;

	private Vector vBotoes = null;

	private int iCodSis = 0;

	private int iCodModu = 0;

	private String sDescSis = "";
	
	private String sDescModu = "";
	
	protected Connection conIB;

	public static Vector vEquipeSis = new Vector();
	public static String sNomeSis = "";
	public static String sNomeModulo = "";
	public static String sEmpSis = "";
	public static String sArqIni = "";
	public static String sMailSuporte = "";
	
	public Connection getConIB() {
		return conIB;
	}


	public Aplicativo() {
		Locale.setDefault(new Locale("pt", "BR"));
	}

	public Aplicativo(String sIcone, String sSplash, int iCodSis, String sDescSis, 
			int iCodModu, String sDescModu, String sNomeArqIni, String sDirImagem) {
	    if (sDirImagem!=null) {
	        Imagem.dirImages = sDirImagem;
	        Icone.dirImages = sDirImagem;
	    }
		if (System.getProperty("ARQLOG") != null)
			ligaLog(System.getProperty("ARQLOG"));
		strSplash = sSplash;
		Locale.setDefault(new Locale("pt", "BR"));
		vOpcoes = new Vector();
		vBotoes = new Vector();
		telaPrincipal = new FPrincipal();
		this.iCodSis = iCodSis;
		this.iCodModu = iCodModu;
		this.sDescSis = sDescSis;
		this.sDescModu = sDescModu;
		imgIcone = Icone.novo(sIcone);
		telaPrincipal.setIconImage(imgIcone.getImage());
		setSplashName(sSplash);
		if (sNomeArqIni==null)
		    sNomeArqIni="freedom.ini";
		sArqIni = sNomeArqIni;
		String sArqINI = System.getProperty("ARQINI") != null ? System
				.getProperty("ARQINI") : sNomeArqIni;
		vArqINI = getArqINI(sArqINI);
		iniConexao(); // Inicia a variável de conexão
		//telaPrincipal.tiraEmp();
		telaPrincipal.setIdent(sDescSis.trim()+" - "+sDescModu.trim(), iCodSis, iCodModu);
		telaPrincipal.setConexao(con); // Variavel de conexão da Classe
		// Aplicativo
		telaPrincipal.statusBar.setUsuario(strUsuario);//Variavel de usuario da
		// Classe Aplicativo
		telaPrincipal.statusBar.setCodFilial(iCodFilial);
		telaPrincipal.statusBar.setRazFilial(sRazFilial);
//		telaPrincipal.statusBar.setRazFilial();
		telaPrincipal.statusBar.setNumEst(iNumEst);
		telaPrincipal.statusBar.setDescEst(getDescEst());
		if (strUsuario.toUpperCase().trim().equals("SYSDBA")) {
			//		pinBotoes = new JPanelPad();
			//		pinBotoes.setSize(500,34);
			iXPanel = 30;
			pinBotoes.adic(btAtualMenu, 0, 0, 30, 30);
		} else
			iXPanel = 0;

		//	montaMenu();
		telaPrincipal.adicCompInBar(pinBotoes, BorderLayout.WEST);
		//	telaPrincipal.setMenu();
		btAtualMenu.addActionListener(this);

	}

	public JMenuItem getOpcao(int iOpcao) {
		JMenuItem miRetorno = null;
		JMenuItem miTemp = null;
		int iCodMenu = -1;
		try {
			for (int i = 0; i < vOpcoes.size(); i++) {
				miTemp = (JMenuItem) vOpcoes.elementAt(i);
				if (miTemp != null) {
					if (miTemp instanceof JMenuPad)
						iCodMenu = ((JMenuPad) miTemp).getCodMenu();
					else if (miTemp instanceof JMenuItemPad)
						iCodMenu = ((JMenuItemPad) miTemp).getCodItem();
					if (iCodMenu == iOpcao) {
						miRetorno = miTemp;
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return miRetorno;
	}

	private void ligaLog(String sArq) {
		File fArq = new File(sArq);
		try {
			if (!fArq.exists())
				fArq.createNewFile();
			FileOutputStream foArq = new FileOutputStream(fArq, true);
			System.setErr(new PrintStream(foArq));
		} catch (Exception err) {
			err.printStackTrace();
		}
	}

	public void addOpcao(int iSuperMenu, int iTipo, String sCaption,
			String titulo, char cAtalho, int iOpcao, int iNivel, boolean bExec,
			Class tela) {
		JMenuItem mOpcao = null;
		JMenuPad mpMaster = null;
		try {
			if (iTipo == TP_OPCAO_MENU) {
				mOpcao = (new JMenuPad(iCodSis, iCodModu, iOpcao, iNivel));
			} else if (iTipo == TP_OPCAO_ITEM) {
				mOpcao = (new JMenuItemPad(iCodSis, iCodModu, iOpcao, iNivel,
						tela, titulo));
			}
			mOpcao.setText(sCaption);
			mOpcao.setMnemonic(cAtalho);

			if (bExec)
				mOpcao.addActionListener(this);
			if (iSuperMenu == -1) {
				telaPrincipal.adicMenu((JMenuPad) mOpcao);
			} else {
				mpMaster = (JMenuPad) getOpcao(iSuperMenu);
				if (mpMaster != null) {
					if (bExec)
						((JMenuItemPad) mOpcao).setEnabled(verifAcesso(iCodSis,
								iCodModu, iOpcao));
					mpMaster.add(mOpcao);
				}
			}
			vOpcoes.addElement(mOpcao);
		} catch (Exception e) {
			Funcoes.mensagemInforma(null, e.getMessage());
			e.printStackTrace();
		}
	}

	public void addSeparador(int iSuperMenu) {
		Object oSuper = null;
		try {
			try {
				oSuper = getOpcao(iSuperMenu);
				if (oSuper != null) {
					if (oSuper instanceof JMenu) {
						((JMenu) oSuper).addSeparator();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} finally {
			oSuper = null;
		}

	}

	public JButtonPad addBotao(String sImagem, String sToolTip, String titulo,
			int iCodMenu, Class tela) {
		JButtonPad btOpcao = null;
		try {
			btOpcao = new JButtonPad(iCodSis, iCodModu, iCodMenu, tela, titulo);
			btOpcao.setIcon(Icone.novo(sImagem));
			if (sToolTip != null)
				btOpcao.setToolTipText(sToolTip);
			vBotoes.add(btOpcao);
			adicTelaBotao(btOpcao);
			return btOpcao;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void show() {
		telaPrincipal.setVisible(true);
	}

	public void adicTelaBotao(JButtonPad bt) {
		bt.setEnabled(verifAcesso(bt.getCodSistema(), bt.getCodModulo(), bt
				.getCodItem()));
		pinBotoes.adic(bt, iXPanel, 0, 30, 30);
		bt.addActionListener(this);
		iXPanel += 30;
	}

	public boolean verifAcesso(int iCodSis, int iCodModu, int iCodMenu) {
		boolean bRet = false;
		if (strUsuario.toUpperCase().equals("SYSDBA"))
			return true;
		try {
			String sTmp = "";
			String sSQL = "SELECT TPACESSOMU FROM SGACESSOMU WHERE CODEMP = ? "
					+ "AND CODFILIAL = ? " + "AND IDUSU = ? "
					+ "AND CODSIS = ? " + "AND CODMODU = ? "
					+ "AND CODMENU = ?";
			PreparedStatement ps = con.prepareStatement(sSQL);
			ps.setInt(1, Aplicativo.iCodEmp);
			ps.setInt(2, Aplicativo.iCodFilial);
			ps.setString(3, strUsuario);
			ps.setInt(4, iCodSis);
			ps.setInt(5, iCodModu);
			ps.setInt(6, iCodMenu);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				sTmp = rs.getString("TPACESSOMU");
				if (sTmp == null)
					return bRet;
				if (sTmp.toCharArray()[0] > 'A')
					bRet = true;
			}
			rs.close();
			ps.close();
		} catch (SQLException err) {
			killProg(1, "Erro ao verificar acessos para arvore de menus!\n"
					+ err.getMessage());
		}
		return bRet;
	}

	public void adicTelaMenu(JMenuPad menu, JMenuItemPad item) {
		item.setEnabled(verifAcesso(item.getCodSistema(), item.getCodModulo(),
				item.getCodItem()));
		menu.add(item);
		item.addActionListener(this);
	}

	public void actionPerformed(ActionEvent evt) {
		Object oTemp = evt.getSource();
		int iCodMenu = -1;
		if (oTemp != null) {
			if (oTemp instanceof JButtonPad) {
				iCodMenu = ((JButtonPad) oTemp).getCodItem();
			} else if (oTemp instanceof JButton) {
				if (((JButton) oTemp) == btAtualMenu) {
					atualizaMenus();
				}
			} else if (oTemp instanceof JMenuItemPad) {
				iCodMenu = ((JMenuItemPad) oTemp).getCodItem();
			} else if (oTemp instanceof JMenuPad) {
				iCodMenu = ((JMenuPad) oTemp).getCodMenu();
			} else if (oTemp instanceof JMenuItem) {
				if (((JMenuItem) oTemp).getText().equals("Sair")) {
					telaPrincipal.fecharJanela();
				} else if (((JMenuItem) oTemp).getText().equals("Sobre")) {
					FSobre tela = new FSobre();
					tela.setVisible(true);
					tela.dispose();
				} else if (((JMenuItem) oTemp).getText().equals("Atalhos")) {
					FAtalhos tela = new FAtalhos();
					tela.setVisible(true);
					tela.dispose();
				} else if (((JMenuItem) oTemp).getText().equals("Suporte")) {
					FSuporte tela = new FSuporte();
					tela.setConexao(con);
					tela.setVisible(true);
					tela.dispose();
				}
			}
			if (iCodMenu != -1) {
				Class telaClass = null;
				String titulo = "";
				if (oTemp instanceof JMenuItemPad) {
					telaClass = ((JMenuItemPad) oTemp).getTela();
					if (telaClass != null) {
						titulo = ((JMenuItemPad) oTemp).getTitulo();
					}
				} else if (oTemp instanceof JButtonPad) {
					telaClass = ((JButtonPad) oTemp).getTela();
					if (telaClass != null) {
						titulo = ((JButtonPad) oTemp).getTitulo();
					}
				}
				if (telaClass != null) {
					if (telaPrincipal.temTela(titulo) == false) {
						try {
							Object obj = telaClass.newInstance();
							if (obj instanceof FFDialogo) {
								FFDialogo tela = (FFDialogo) obj;

								Class partypes[] = new Class[2];
								partypes[0] = Connection.class;
								partypes[1] = Connection.class;
								Method meth = null;
								try {
									meth = telaClass.getMethod(
											"setConexao", partypes);
								} catch (NoSuchMethodException e) { }

								telaPrincipal.criatela(titulo, tela, con);
								tela.setTelaPrim(telaPrincipal);

								if (meth != null) {
									Object arglist[] = new Object[2];
									arglist[0] = con;
									arglist[1] = conIB;
									meth.invoke(obj, arglist);
								}
							} else if (obj instanceof FFilho) {
								FFilho tela = (FFilho) obj;

								Class partypes[] = new Class[2];
								partypes[0] = Connection.class;
								partypes[1] = Connection.class;
								Method meth = null;
								try {
									meth = telaClass.getMethod(
											"setConexao", partypes);
								} catch (NoSuchMethodException e) { }

								telaPrincipal.criatela(titulo, tela, con);
								tela.setTelaPrim(telaPrincipal);

								if (meth != null) {
									Object arglist[] = new Object[2];
									arglist[0] = con;
									arglist[1] = conIB;
									meth.invoke(obj, arglist);
								}
							} else if (obj instanceof FDialogo) {
								FDialogo tela = (FDialogo) obj;

								Class partypes[] = new Class[2];
								partypes[0] = Connection.class;
								partypes[1] = Connection.class;
								Method meth = null;
								try {
									meth = telaClass.getMethod(
											"setConexao", partypes);
								} catch (NoSuchMethodException e) { }

								telaPrincipal.criatela(titulo, tela, con);

								if (meth != null) {
									Object arglist[] = new Object[2];
									arglist[0] = con;
									arglist[1] = conIB;
									meth.invoke(obj, arglist);
								}
							} else {
								Funcoes.mensagemInforma(framePrinc,
										"Tela construída com "
												+ telaClass.getName()
												+ "\n Não pode ser inciada.");
							}
							obj = null;
						} catch (Exception e) {
							Funcoes.mensagemErro(framePrinc, e.getMessage());
							e.printStackTrace();
						}
					}
				}

			}
		}

	}

	public void atualizaMenus() {
		JMenuBar menuBar = telaPrincipal.bar;
		for (int i = 0; i < menuBar.getMenuCount(); i++) {
			if (!upMenuDB(menuBar.getMenu(i), new JMenuPad()))
				break;
			buscaMenuItem(menuBar.getMenu(i));
		}
	}

	private void buscaMenuItem(JMenu men) {
		for (int i = 0; i < men.getItemCount(); i++) {
			JMenuItem it = men.getItem(i);
			if (it instanceof JMenuPad) {
				if (!upMenuDB(it, (JMenuPad) men))
					break;
				buscaMenuItem((JMenu) it);
			} else if (it instanceof JMenuItemPad) {
				if (!upMenuDB((JMenuItemPad) it, (JMenuPad) men))
					break;
			}
		}
	}

	private boolean upMenuDB(JMenuItem men, JMenuPad menPai) {
		boolean bRet = false;
		Class tela = null;
		String sNomeMenu = null;
		String sAcaoMenu = null;
		int iCodMenu = 0;
		try {
			if (men instanceof JMenuItemPad) {
//				iCodSis = ((JMenuItemPad) men).getCodSistema();
//				iCodModu = ((JMenuItemPad) men).getCodModulo();
				iCodMenu = ((JMenuItemPad) men).getCodItem();
				tela = ((JMenuItemPad) men).getTela();
			} else if (men instanceof JMenuPad) {
//				iCodSis = ((JMenuPad) men).getCodSistema();
//				iCodModu = ((JMenuPad) men).getCodModulo();
				iCodMenu = ((JMenuPad) men).getCodMenu();
			}
			//			System.out.println(iCodSis+","+iCodModu+","+iCodMenu+","+men.getText()+","+menPai.getCodSistema()+","+menPai.getCodModulo()+","+menPai.getCodMenu());
			if (tela!=null) {
				sNomeMenu = tela.getName();
				sAcaoMenu = tela.getName();
			}
			else {
				sNomeMenu = ""+iCodMenu;
				sAcaoMenu = ""+iCodMenu;
			}
			PreparedStatement ps = con
					.prepareStatement("EXECUTE PROCEDURE SGUPMENUSP01(?,?,?,?,?,?,?,?,?,?,?)");
			ps.setInt(1, this.iCodSis);
			ps.setString(2,this.sDescSis);
			ps.setInt(3, this.iCodModu);
			ps.setString(4,Funcoes.copy(this.sDescModu, 50));
			ps.setInt(5, iCodMenu);
			ps.setString(6, men.getText());
			ps.setString(7,sNomeMenu);
			ps.setString(8,sAcaoMenu);
			
			if (menPai.getCodMenu() == 0) {
				ps.setNull(9, java.sql.Types.INTEGER);
				ps.setNull(10, java.sql.Types.INTEGER);
				ps.setNull(11, java.sql.Types.INTEGER);
			}
			else {
				ps.setInt(9, menPai.getCodModulo());
				ps.setInt(10, menPai.getCodModulo());
				ps.setInt(11, menPai.getCodMenu());
			}


			ps.execute();
			ps.close();
			if (!con.getAutoCommit())
				con.commit();
			bRet = true;
		} catch (SQLException err) {
			Funcoes.mensagemInforma(telaPrincipal,
					"Não foi possível atualizar a base de menus!\n" + err + 
					"\n" +this.iCodSis+","+this.sDescSis+"\n"+this.iCodModu+","+this.sDescModu+"\n"+
					iCodMenu+","+men.getText()+"\n"+","+menPai.getCodMenu());
		}
		finally {
			tela = null;
			sNomeMenu = null;
			sAcaoMenu = null;
			iCodMenu = 0;
		}
		return bRet;
	}

	public void adicTelaMenu(JButtonPad bt) {
		iXPanel += 30;
		bt.setEnabled(verifAcesso(bt.getCodSistema(), bt.getCodModulo(), bt
				.getCodItem()));
		pinBotoes.adic(bt, iXPanel, 0, 30, 30);
		bt.addActionListener(this);
	}

	public void ajustaMenu() {
		pinBotoes.setPreferredSize(new Dimension(iXPanel + 4, 30));
		Object oMenu = getOpcao(100000000);
		JMenuItem miSair = null;
		if (oMenu != null) {
			if (oMenu instanceof JMenuPad) {
				miSair = new JMenuItem("Sair", 'r');
				miSair.addActionListener(this);
				((JMenuPad) oMenu).addSeparator();
				((JMenuPad) oMenu).add(miSair);
			}
		}
		JMenu mAjuda = new JMenu("Ajuda");
		JMenuItem miSobre = new JMenuItem("Sobre");
		miSobre.addActionListener(this);
		mAjuda.add(miSobre);
		JMenuItem miAtalhos = new JMenuItem("Atalhos");
		miAtalhos.addActionListener(this);
		mAjuda.add(miAtalhos);
		mAjuda.addSeparator();
		JMenuItem miSuporte = new JMenuItem("Suporte");
		miSuporte.addActionListener(this);
		mAjuda.add(miSuporte);
		telaPrincipal.bar.add(mAjuda);
	}

	public void iniConexao() {
		String sAutoCommit = getParameter("autocommit");
		try {
			strBanco = getParameter("banco");
			strDriver = getParameter("driver");
			if (sAutoCommit == null)
				sAutoCommit = "N";
			if (sAutoCommit.trim().equals(""))
				sAutoCommit = "N";
			if (sAutoCommit.toUpperCase().equals("S"))
				bAutoCommit = true;

			strTemp = getParameter("temp");
			strOS = getParameter("os").toLowerCase();
			strTefEnv = getParameter("tef_path_envio");
			strTefRet = getParameter("tef_path_retorno");

			try {
				iCodEmp = Integer.parseInt(getParameter("codemp"));
			} catch (Exception err) {
				Funcoes.mensagemErro(null,
						"Não foi possível carregar o parâmetro 'codemp'\n"
								+ err.getMessage());
			}

			try {
				iNumEst = Integer.parseInt(getParameter("numterm"));
			} catch (Exception err) {
				Funcoes.mensagemErro(null,
						"Não foi possível carregar o parâmetro 'numterm'\n"
								+ err.getMessage());
			}
			if (strBanco == null) {
				Funcoes.mensagemInforma(null,
						"Parametro banco nao foi preenchido");
				return;
			}
			if (strDriver == null) {
				Funcoes.mensagemInforma(null,
						"Parametro driver nao foi preenchido");
				return;
			}
			con = conexao(strDriver, strBanco);
			if (con == null)
				System.exit(1);
			try {
				con.setAutoCommit(bAutoCommit);
				//      	con.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
				con
						.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			} catch (SQLException err) {
				Funcoes.mensagemErro(null, err.getMessage());
			}
			tbObjetos = new TabObjeto();
			tbObjetos.montaLista(con, iCodEmp, "SGOBJETO", "TB");
			carregaCasasDec();
			getMultiAlmox();
		} finally {
			sAutoCommit = null;
		}
	}

	public void setSplashName(String sImg) {
		sSplashImg = sImg;
	}

	public static boolean getAutoCommit() {
		return bAutoCommit;
	}

	public Connection conexao(String strDriver, String strBanco) {
		String sVals[];
		Connection conRetorno = null;

		if (strUsuario.equals("") && strSenha.equals("")) {

			Login lgBanco = new Login(strBanco, strDriver, sSplashImg, iNumEst);
			if (!lgBanco.OK)
				System.exit(0);
			sVals = lgBanco.getStrVals();
			strUsuario = sVals[0];
			strSenha = sVals[1];
			iCodFilial = lgBanco.getFilial();
			sRazFilial = lgBanco.getRazFilial();
			iCodFilialMz = lgBanco.getFilialMz();
			iCodFilialPad = lgBanco.getFilialPad();
			conRetorno = lgBanco.getConection();
			lgBanco.dispose();
		}

		if (strUsuario.length() == 0) {
			return null;
		} else if (strSenha.length() == 0) {
			return null;
		}

		return conRetorno;

	}

	public static int[] gravaLog(String sClas, String sTipo, String sDesc,
			String sObs, Connection con) {
		return gravaLog(strUsuario, sClas, sTipo, sDesc, sObs, con);
	}

	public static int[] gravaLog(String sIDUSU, String sClas, String sTipo,
			String sDesc, String sObs, Connection con) {
		int iRet[] = new int[2];
		String sSQL = "SELECT CODFILIAL,CODLOG FROM SGLOGSP01(?,?,?,?,?,?)";
		try {
			PreparedStatement ps = con.prepareStatement(sSQL);
			ps.setInt(1, iCodEmp);
			ps.setString(2, sIDUSU);
			ps.setString(3, sClas);
			ps.setString(4, sTipo);
			ps.setString(5, sDesc);
			ps.setString(6, sObs);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				iRet[0] = rs.getInt("CodFilial");
				iRet[1] = rs.getInt("CodLog");
			}
			rs.close();
			ps.close();
			if (!con.getAutoCommit())
				con.commit();
		} catch (SQLException err) {
			Funcoes.mensagemErro(null, "Erro ao gravar LOG!!\n"
					+ err.getMessage());
			err.printStackTrace();
		}
		return iRet;
	}

	public boolean getModoDemo() {
		String sSQL = "SELECT MODODEMOEST FROM SGESTACAO WHERE CODEST="
				+ iNumEst + "AND CODEMP=" + iCodEmp + " AND CODFILIAL="
				+ iCodFilial;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean bModo = true;
		try {
			ps = con.prepareStatement(sSQL);
			rs = ps.executeQuery();
			if (!rs.next())
				Funcoes.mensagemErro(null,
						"Estação de trabalho não cadastrado!");
			else {
				if (rs.getString("ModoDemoEst").equals("S"))
					bModo = true;
				else
					bModo = false;
			}
			if (!con.getAutoCommit())
				con.commit();
		} catch (SQLException err) {
			Funcoes.mensagemErro(null, err.getMessage());
			return true;
		}
		return bModo;
	}

	public String getDescEst() {
		String sSQL = "SELECT DESCEST FROM SGESTACAO WHERE CODEST=" + iNumEst
				+ " AND CODEMP=" + iCodEmp + " AND CODFILIAL=" + iCodFilial;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sDesc = "";
		try {
			ps = con.prepareStatement(sSQL);
			rs = ps.executeQuery();
			if (!rs.next())
				sDesc = "ESTAÇÃO DE TRABALHO NÃO CADASTRADA";
			else
				sDesc = rs.getString("DescEst");
			if (!con.getAutoCommit())
				con.commit();
		} catch (SQLException err) {
			Funcoes.mensagemErro(null, err.getMessage());
			return "NÃO FOI POSSÍVEL REGISTRAR A ESTAÇÃO DE TRABALHO! ! !";
		}
		return sDesc;
	}

	public void getMultiAlmox() {
		String sSQL = "SELECT MULTIALMOXEMP FROM SGEMPRESA WHERE CODEMP=?" ;				
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = con.prepareStatement(sSQL);
			ps.setInt(1,iCodEmp);
			rs = ps.executeQuery();
			
			if (!rs.next())
				sMultiAlmoxEmp = rs.getString(1)==null?"N":rs.getString(1);
			else
				
			if (!con.getAutoCommit())
				con.commit();
		} catch (SQLException err) {
			Funcoes.mensagemErro(null, err.getMessage());			
		}		
	}	
	
	public Connection conexaoIB(String strDriver, String strBanco) {
		try {
			Class.forName(strDriver);
		} catch (java.lang.ClassNotFoundException e) {
			Funcoes.mensagemErro(null, "[internal]:Driver nao foi encontrado: "
					+ e.getMessage());
			return null;
		}

		try {
			conIB = DriverManager.getConnection(strBanco, strUsuario, strSenha);
		} catch (java.sql.SQLException e) {
			if (e.getErrorCode() == 335544472)
				return null;
			Funcoes.mensagemErro(null,
					"[internal]:Não foi possível estabelecer conexão com o banco de dados.\n"
							+ e.getMessage());
			return null;
		}
		return conIB;

	}

	public String[][][] getConfig2() {
		Vector vSessao = new Vector();
		Vector vValSessao = new Vector();
		Vector vValCampo = new Vector();
		String sTmp = "";
		boolean bLeSessao = false;
		boolean bLeCampo = false;
		int iTam = 0;
		int i = 0;
		int iContaCampo = 0;
		int iMaxCampo = 0;
		char c = (char) 0;
		File fArq = null;
		FileReader frArq = null;
		try {
			fArq = new File(sArqIni);
			frArq = new FileReader(fArq);
			iTam = (int) fArq.length();
			try {
				while (i < iTam) {
					//              Vector vValSessao = null;
					if (c != (char) 10)
						c = (char) frArq.read();
					//        System.out.println("N. Char SES:"+(new
					// Character(c)).hashCode()+"\n");
					if ((!bLeSessao) & (!bLeCampo) & (c == '[')) {
						bLeSessao = true;
					} else if ((c == ']') & (bLeSessao)) {
						vValSessao = new Vector();
						bLeSessao = false;
						vValSessao.addElement(new String(sTmp));
						vSessao.addElement(vValSessao);
						//                 System.out.println("Sessao: "+sTmp);
						sTmp = "";
						iContaCampo = 0;
					} else if (bLeSessao) {
						sTmp += c;
					} else if (!bLeSessao) {
						bLeCampo = true;
						while (i < iTam) {
							c = (char) frArq.read();
							//                System.out.println("N. Char CAM:"+(new
							// Character(c)).hashCode()+(bLeCampo ? " OK" : "
							// NO"));
							if ((c == (char) 10) & (bLeCampo)) {
								bLeCampo = false;
								vValCampo.addElement(new String(sTmp));
								vValSessao.addElement(vValCampo);
								//                     System.out.println("Valor: "+sTmp);
								sTmp = "";
								iContaCampo++;
								break;
							} else if ((c == '=') & (bLeCampo)) {
								vValCampo.addElement(sTmp);
								System.out.println("Campo: " + sTmp);
								sTmp = "";
							} else if (bLeCampo) {
								sTmp += c;
							}
							i++;
						}
						if (iContaCampo > iMaxCampo) {
							iMaxCampo = iContaCampo;
						}
					}
					i++;
				}
			} catch (IOException err) {
				Funcoes.mensagemErro(null,
						"Erro ao carregar arquivo de configuração!\n"
								+ err.getMessage());
				System.exit(0);
			}
		} catch (FileNotFoundException err) {
			Funcoes.mensagemErro(null,
					"Erro ao carregar arquivo de configuração!\n"
							+ err.getMessage());
			System.exit(0);
		}
		Funcoes.mensagemErro(null, "TESTE: " + vSessao.size() + " ~ "
				+ iMaxCampo);
		String[][][] sRetorno = new String[vSessao.size()][iMaxCampo][2];
		for (int iS = 0; iS < (vSessao.size()); iS++) {
			sRetorno[iS][0][0] = (String) ((Vector) vSessao.elementAt(iS))
					.elementAt(0);
			for (int iC = 1; iC < iMaxCampo; iC++) {
				sRetorno[iS][iC][0] = (String) ((Vector) ((Vector) vSessao
						.elementAt(iS)).elementAt(iC)).elementAt(0);
				sRetorno[iS][iC][1] = (String) ((Vector) ((Vector) vSessao
						.elementAt(iS)).elementAt(iC)).elementAt(1);
			}
		}
		return sRetorno;
	}

	public Vector getArqINI(String sNomeArq) {
		Vector vRetorno = new Vector();
		String sTemp = "";
		int iTam = 0;
		char c = (char) 0;
		try {
			File fArq = new File(sNomeArq);
			FileReader frArq = new FileReader(fArq);
			try {
				iTam = (int) fArq.length();
				for (int i = 0; i < iTam; i++) {
					c = (char) frArq.read();
					if (c == (char) 10) {
						vRetorno.addElement(sTemp);
						sTemp = "";
					} else if (c == (char) 13) {
						if (i == iTam) {
							vRetorno.addElement(sTemp);
							sTemp = "";
						} else {
							c = (char) frArq.read();
							i++;
							if (c == (char) 10) {
								vRetorno.addElement(sTemp);
								sTemp = "";
							} else {
								vRetorno.addElement(sTemp);
								sTemp = "";
								sTemp += c;
							}
						}
					} else {
						sTemp += c;
					}
				}
			} catch (IOException err) {
				Funcoes.mensagemErro(null,
						"Erro ao carregar arquivo de configuração!\n"
								+ err.getMessage());
				System.exit(0);
			}
		} catch (FileNotFoundException err) {
			Funcoes.mensagemErro(null,
					"Erro ao carregar arquivo de configuração!\n"
							+ err.getMessage());
			System.exit(0);
		}
		return vRetorno;
	}

	public String getValorSecao(String sSecao, String sParam) {
		String sLinha = "";
		String sLabel = "";
		int iLocal = 0;
		for (int i = 0; i < vArqINI.size(); i++) {
			sLinha = ((String) vArqINI.elementAt(i)).trim();
			if (sLinha.indexOf(sSecao) > 0) {
				for (int i2 = i + 1; i2 < vArqINI.size(); i2++) {
					sLinha = (String) vArqINI.elementAt(i2);
					if (sLinha.indexOf('[') > 0) {
						break;
					} else if (sLinha.indexOf('=') > 0) {
						iLocal = sLinha.indexOf('=');
						sLabel = sLinha.substring(0, iLocal).trim();
						if (sLabel.equals(sParam.trim())) {
							return sLinha.substring(iLocal + 1);
						}
					}
				}
			}
		}
		return "";
	}

	public String getParameter(String sParam) {
		return getValorSecao("parametros", sParam);
	}

	public void killProg(int iTerm, String sMess) {
		Funcoes.mensagemErro(null, sMess);
		System.exit(iTerm);
	}

	private void carregaCasasDec() {
		String sSQL = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			sSQL = "SELECT CASASDEC,BUSCAPRODSIMILAR FROM SGPREFERE1 WHERE CODEMP=? AND CODFILIAL=?";
			ps = con.prepareStatement(sSQL);
			ps.setInt(1, iCodEmp);
			ps.setInt(2, ListaCampos.getMasterFilial("SGPREFERE1"));
			rs = ps.executeQuery();
			if (rs.next()) {
				casasDec = rs.getInt("CASASDEC");
				String sBusca = (rs.getString("BUSCAPRODSIMILAR") == null ? "N"
						: "S");
				bBuscaProdSimilar = sBusca.equals("S") ? true : false;
			}
			rs.close();
			ps.close();
			if (!con.getAutoCommit())
				con.commit();
		} catch (SQLException e) {
			Funcoes.mensagemErro(null,
					"Não foi possível obter o número de casas decimais!\n"
							+ e.getMessage());
		} finally {
			sSQL = null;
			ps = null;
			rs = null;
		}
	}

	static public String carregaFiltro(Connection conF, int iCodEmpF) {
		String sSQL = "SELECT FILTRO FROM SGPREFERE1 WHERE CODEMP=? AND CODFILIAL=?";
		//	  String sSQL = "INSERT INTO TESTE (TESTE1) VALUES ('2001-3-23')";
		PreparedStatement ps = null;
		ResultSet rs = null;
		sFiltro = "";
		try {
			ps = conF.prepareStatement(sSQL);
			ps.setInt(1, iCodEmpF);
			ps.setInt(2, ListaCampos.getMasterFilial("SGPREFERE1"));
			rs = ps.executeQuery();
			if (!rs.next()) {
				sFiltro = "";
				Funcoes.mensagemInforma(null,
						"Preferências não foram cadastradas!");
			} else
				sFiltro = rs.getString("FILTRO");
			//		rs.close();
			//		ps.close();
			if (!conF.getAutoCommit())
				conF.commit();
		} catch (SQLException err) {
			Funcoes.mensagemErro(null, "NÃO FOI POSSÍVEL CARREGAR OS FILTROS! "
					+ err.getMessage());
			//		return "NÃO FOI POSSÍVEL CARREGAR OS FILTROSL! ! !";
		}
		if (sFiltro == null)
			sFiltro = "('S','N')";
		else if (sFiltro.trim().equals(""))
			sFiltro = "('S','N')";
		else if (sFiltro.trim().equals("SN"))
			sFiltro = "('S','N')";
		else if (sFiltro.trim().equals("S"))
			sFiltro = "('S')";
		else if (sFiltro.trim().equals("N"))
			sFiltro = "('N')";
		return sFiltro;
	}

	public void setFiltro(char cFiltro) {
		String sFiltro = "";
		switch (cFiltro) {
		case '1':
			sFiltro = "S";
			break;
		case '2':
			sFiltro = "N";
			break;
		case '3':
			sFiltro = "SN";
			break;
		}
		try {
			String sSQL = "UPDATE SGPREFERE1 SET FILTRO=? WHERE CODEMP=?";
			PreparedStatement ps = con.prepareStatement(sSQL);
			ps.setString(1, sFiltro);
			ps.setInt(2, Aplicativo.iCodEmp);
			ps.execute();
			if (!con.getAutoCommit())
				con.commit();
			Funcoes.mensagemInforma(null, "Filtros atualizados para: "
					+ sFiltro);
		} catch (SQLException err) {
			Funcoes.mensagemErro(null, "Erro ao atualizar filtro.\n"
					+ err.getMessage());
		}
	}

	public void keyPressed(KeyEvent kevt) {
		//		System.out.println("Key pressed:
		// "+kevt.getKeyText(kevt.getKeyCode()));
		if (kevt.getKeyCode() == KeyEvent.VK_CONTROL)
			bCtrl = true;
		if (bCtrl) {
			if (kevt.getKeyCode() == KeyEvent.VK_F10)
				setFiltro('1');
			else if (kevt.getKeyCode() == KeyEvent.VK_F11)
				setFiltro('2');
			else if (kevt.getKeyCode() == KeyEvent.VK_F12)
				setFiltro('3');
		}
	}

	public void keyReleased(KeyEvent kevt) {
		//		System.out.println("Key released:
		// "+kevt.getKeyText(kevt.getKeyCode()));
		if (kevt.getKeyCode() == KeyEvent.VK_CONTROL)
			bCtrl = false;
	}

	public void keyTyped(KeyEvent kevt) {
		//		System.out.println("Key typed: "+kevt.getKeyText(kevt.getKeyCode()));
	}

}

