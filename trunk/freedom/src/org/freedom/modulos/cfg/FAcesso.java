/**
 * @version 09/05/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.cfg <BR>
 * Classe: @(#)FAcesso.java <BR>
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
 * 
 */

package org.freedom.modulos.cfg;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;

import org.freedom.acao.ArvoreFace;
import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.bmps.Icone;
import org.freedom.componentes.Arvore;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JComboBoxPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.JPanelPad;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FFDialogo;

public class FAcesso extends FFDialogo implements ArvoreFace, CarregaListener {
  private JPanel pnCliente = new JPanel(new BorderLayout());
  private JPanelPad pinTop = new JPanelPad(475,90);
  private JTextFieldPad txtCodUsu = new JTextFieldPad(JTextFieldPad.TP_STRING,8,0);
  private JTextFieldFK txtNomeUsu = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
  private ListaCampos lcUsuario = new ListaCampos(this);
  private Arvore arvore = new Arvore();
  private JScrollPane spnArv = new JScrollPane(arvore);
  private Vector vAcessos = new Vector();
  private Vector vVals = new Vector();
  private Vector vLabs = new Vector();
  private JComboBoxPad cbFiliais= new JComboBoxPad(vLabs, vVals, JComboBoxPad.TP_INTEGER, 8, 0);
  private JButton btSalva = new JButton(Icone.novo("btGerar.gif"));
  boolean bEOF = false;
  int iConta = 0;
  public FAcesso() {
  	super(Aplicativo.telaPrincipal);

    setTitulo("Controle de acesso por menus.");
    setAtribos( 410, 400);
    
    setToFrameLayout();
    
    btSalva.setPreferredSize(new Dimension(30,30));
    pnRodape.add(btSalva,BorderLayout.WEST);
    btSalva.setEnabled(false);
	btSalva.setToolTipText("Salvar permições");

    txtCodUsu.setNomeCampo("IdUsuario");
    lcUsuario.add(new GuardaCampo( txtCodUsu, "IDUSU", "ID Usuario", ListaCampos.DB_PK, txtNomeUsu, false));
    lcUsuario.add(new GuardaCampo( txtNomeUsu, "NOMEUSU", "Nome", ListaCampos.DB_SI, false));
    lcUsuario.montaSql(false, "USUARIO", "SG");    
    lcUsuario.setQueryCommit(false);
    lcUsuario.setReadOnly(true);
    txtCodUsu.setChave(ListaCampos.DB_PK);
    txtCodUsu.setListaCampos(lcUsuario);
    txtNomeUsu.setListaCampos(lcUsuario);
    
    
    setPanel(pnCliente);
    setPainel(pinTop);
    
    adic(new JLabel("ID Usuário"),7,0,100,20);
    adic(txtCodUsu,7,20,100,20);
    adic(new JLabel("Nome"),110,0,250,20);
    adic(txtNomeUsu,110,20,250,20);
	adic(new JLabel("Filial"),7,40,250,20);
	adic(cbFiliais,7,60,353,20);
    
    c.add(pinTop,BorderLayout.NORTH);
    c.add(spnArv,BorderLayout.CENTER);
    
    arvore.setModel(new DefaultTreeModel(new DefaultMutableTreeNode("Acesso aos menus")));
    
    arvore.getSelectionModel().setSelectionMode
        (TreeSelectionModel.SINGLE_TREE_SELECTION);
        
    arvore.addMouseListener(new MouseAdapter() {
        public void mousePressed(MouseEvent e) {
          upImagem(e.getX(),e.getY());
        }
      }
    );
    arvore.setImgTrat(this);
    
    lcUsuario.addCarregaListener(this);
    cbFiliais.addActionListener(this);
    btSalva.addActionListener(this);
  }
  public void montaArvore() {
  	if (cbFiliais.getVlrInteger().intValue() == 0)
  	  return;
    DefaultMutableTreeNode men = new DefaultMutableTreeNode("Acesso aos menus");

    arvore.getSelectionModel().setSelectionMode
                (TreeSelectionModel.SINGLE_TREE_SELECTION);
   
    try {
      DefaultMutableTreeNode pai = null;
      DefaultMutableTreeNode item = null;
   	  int iCodSis = -1;
   	  int iCodModu = -1;
   	  int iCodSisAnt = -1;
   	  int iCodModuAnt = -1;
   	  
   	  String sSQL = "SELECT (SELECT AC.TPACESSOMU FROM SGACESSOMU AC WHERE AC.CODEMP = ? "+
   	                "AND AC.CODFILIAL = ? AND AC.IDUSU = ? AND AC.CODSIS=MN.CODSIS "+
   	                "AND AC.CODMODU=MN.CODMODU AND AC.CODMENU=MN.CODMENU),MN.CODSIS,"+
   	                "SIS.DESCSIS,MN.CODMODU,MO.DESCMODU,CODMENU,DESCMENU,CODSISPAI,"+
   	                "CODMODUPAI,CODMENUPAI FROM SGMENU MN,SGSISTEMA SIS,SGMODULO MO "+
   	                "WHERE SIS.CODSIS=MN.CODSIS AND MO.CODSIS=MN.CODSIS AND "+
   	                "MO.CODMODU = MN.CODMODU ORDER BY MN.CODSIS,MN.CODMODU,MN.CODMENU";
   	  PreparedStatement ps = con.prepareStatement(sSQL);
   	  ps.setInt(1,Aplicativo.iCodEmp);
   	  ps.setInt(2,cbFiliais.getVlrInteger().intValue());
   	  ps.setString(3,txtCodUsu.getVlrString());
   	  ResultSet rs = ps.executeQuery();
      bEOF = false;
      iConta = 0;
      vAcessos.clear();
   	  while (rs.next()) {
   	    iCodSis = rs.getInt("CodSis");          
   	    iCodModu = rs.getInt("CodModu");
  	    if (iCodSisAnt != iCodSis) {
  	      pai = new DefaultMutableTreeNode(rs.getString("DescSis"));
  	      men.add(pai);
  	      iCodSisAnt = iCodSis;
  	    }
  	    if (iCodModuAnt != iCodModu) {
  		  iCodModuAnt = iCodModu;
  	      item = new DefaultMutableTreeNode(rs.getString("DescModu"));
  	      pai.add(adicFilho(rs,item,1));
  	    }
  	    if (bEOF)
  	      break;
   	  }
   	  rs.close();
   	  ps.close();
    }
    catch (SQLException err) {
		Funcoes.mensagemErro(this,"Erro ao carregar menus.\n"+err.getMessage());
    }             

  	DefaultTreeModel mod = new DefaultTreeModel(men);
    arvore.setModel(mod);
  }
  
  
  private DefaultMutableTreeNode adicFilho(ResultSet rs, DefaultMutableTreeNode pai, int iNivel) {
  	int iSubMenuAnt = -1;
  	int iSubMenu = -1;
  	int iCodModuAnt = -1;
  	DefaultMutableTreeNode item = null;
	try {
	  while (true) {
   	    int iCodMenu = rs.getInt("CodMenu");          
		int iCodModu = rs.getInt("CodModu");          
	    switch (iNivel) {
		  case 1: iSubMenu = Integer.parseInt((iCodMenu+"").substring(0,2)); break;
		  case 2: iSubMenu = Integer.parseInt((iCodMenu+"").substring(2,4)); break;
		  case 3: iSubMenu = Integer.parseInt((iCodMenu+"").substring(4,6)); break;
		  case 4: iSubMenu = Integer.parseInt((iCodMenu+"").substring(6,8)); break;
		  case 5: iSubMenu = Integer.parseInt((iCodMenu+"").substring(8)); break;
  	    }
  	    if (iCodModu != iCodModuAnt && iCodModuAnt != -1)
  	      return pai;
  	    iCodModuAnt = iCodModu; 
  	    if ((iSubMenuAnt != -1) && (iSubMenu == 0)) {
  	      return pai;
  	    }
  	    else if (iSubMenu != iSubMenuAnt) {
  	      item = new DefaultMutableTreeNode(Funcoes.copy(rs.getString("DescMenu"),0,40)+"["+(iConta++)+"]");
  	      pai.add(item);
  	      adicAcesso((rs.getString(1) == null ? 'A' : rs.getString(1).toCharArray()[0]),rs.getInt("CodMenu"),rs.getInt("CodSis"),rs.getInt("CodModu"),iNivel);
  	    }
  	    else {
  	      pai.add(adicFilho(rs,item,iNivel+1));
  	      if (bEOF) 
  	        return pai;
  	      continue;
  	    }
  	    iSubMenuAnt = iSubMenu;
  	    if (!rs.next()) {
  	      bEOF = true;
  	      break;
  	    }
	  }
	}
    catch (SQLException err) {
		Funcoes.mensagemErro(this,"Erro ao carregar menus [nivel "+iNivel+" ].\n"+err.getMessage());
    }             
    return pai;
  }
 

  private void adicAcesso(char cAcesso, int iCodMen, int iCodSis, int iCodModu, int iNivel) {
  	String sVal[] = new String[5];
  	switch (cAcesso) {
  	  case 'A': sVal[0] = "0000"; break;
  	  case 'B': sVal[0] = "0001"; break;
  	  case 'C': sVal[0] = "0010"; break;
  	  case 'D': sVal[0] = "0011"; break;
  	  case 'E': sVal[0] = "0100"; break;
  	  case 'F': sVal[0] = "0101"; break;
  	  case 'G': sVal[0] = "0110"; break;
  	  case 'H': sVal[0] = "0111"; break;
  	  case 'I': sVal[0] = "1000"; break;
  	  case 'J': sVal[0] = "1001"; break;
  	  case 'K': sVal[0] = "1010"; break;
  	  case 'L': sVal[0] = "1011"; break;
  	  case 'M': sVal[0] = "1100"; break;
  	  case 'N': sVal[0] = "1101"; break;
  	  case 'O': sVal[0] = "1110"; break;
  	  case 'P': sVal[0] = "1111"; break;
  	}
  	sVal[1] = ""+iCodMen;
  	sVal[2] = ""+iCodModu;
  	sVal[3] = ""+iCodSis;
  	sVal[4] = ""+iNivel;
    vAcessos.add(sVal);
  }
  
  public ImageIcon getImagem(int iLinha, boolean bNoh, Object src) {
  	String sVal = ((String)((DefaultMutableTreeNode)src).getUserObject());
  	int iPrim = sVal.indexOf('[');
  	int iUlt = sVal.indexOf(']');
  	if (iPrim == -1 || iUlt == -1)
  	  return null;
    int iInd = Integer.parseInt(sVal.substring(iPrim+1,iUlt));
    if (iInd == -1)
      return null;
  	return Icone.novo("ba"+((iInd < vAcessos.size()) && (iInd != -1) ? ((String[])vAcessos.elementAt(iInd))[0] : "0000")+".gif");
  }
  
  
  private void upImagem(int x, int y) {
  	char cAcesso[] = new char[4];
  	int iDif = 0;
  	int iLinha = arvore.getRowForLocation(x,y);
  	int iNivelClick = 0;
  	int iNivel = 0;
  	if (iLinha == -1)
  	  return;
  	DefaultMutableTreeNode src = (DefaultMutableTreeNode)arvore.getPathForRow(iLinha).getLastPathComponent();
  	String sVal = ((String)src.getUserObject());
  	int iPrim = sVal.indexOf('[');
  	int iUlt = sVal.indexOf(']');
  	if (iPrim == -1 || iUlt == -1)
  	  return;
  	int iInd = Integer.parseInt(sVal.substring(iPrim+1,iUlt));
    if (iInd == -1)
      return;

    String sVals[] = (String[])vAcessos.elementAt(iInd);
    cAcesso = sVals[0].toCharArray();
    
    iDif = x-arvore.getRowBounds(iLinha).x;
    
    if ((iDif >= 0) && (iDif <13))
      cAcesso[0] = (cAcesso[0] == '1' ? '0' : '1');
    else if  ((iDif >= 13) && (iDif <27))
      cAcesso[1] = (cAcesso[1] == '1' ? '0' : '1');
    else if  ((iDif >= 27) && (iDif <40))
      cAcesso[2] = (cAcesso[2] == '1' ? '0' : '1');
    else if  ((iDif >= 41) && (iDif <54))
      cAcesso[3] = (cAcesso[3] == '1' ? '0' : '1');
    
    sVals[0] = new String(cAcesso);
    
    iNivelClick = Integer.parseInt(((String[])vAcessos.elementAt(iInd))[4]);
    vAcessos.setElementAt(sVals,iInd);
    
    for (int i=iInd+1; i<vAcessos.size(); i++) {
    	char cAcessoFilho[] = new char[4];
    	sVals = (String[])vAcessos.elementAt(i);
    	
    	iNivel = Integer.parseInt((sVals)[4]);
    	
    	if (iNivel <= iNivelClick)
    	  break;
    	  
    	cAcessoFilho = sVals[0].toCharArray();
    	
    	if ((iDif>=0)&&(iDif<13))
    	  cAcessoFilho[0] = cAcesso[0];
    	else if ((iDif>=13)&&(iDif<27))
    	  cAcessoFilho[1] = cAcesso[1];
    	else if ((iDif>=27)&&(iDif<40))
    	  cAcessoFilho[2] = cAcesso[2];
    	else if ((iDif>=41)&&(iDif<54))
    	  cAcessoFilho[3] = cAcesso[3];
    	  
    	sVals[0] = new String(cAcessoFilho);
    	vAcessos.setElementAt(sVals,i);
    }
 	arvore.updateUI();
	btSalva.setEnabled(true);
  }
  private void gravaAcessos() {
  	for (int i=0;i<vAcessos.size();i++) {
  		try {
  			String sSQL = "EXECUTE PROCEDURE SGUPACESSOMUSP (?,?,?,?,?,?,?)";
  			PreparedStatement ps = con.prepareStatement(sSQL);
  			ps.setInt(1,Aplicativo.iCodEmp);
  			ps.setInt(2,cbFiliais.getVlrInteger().intValue());
  			ps.setString(3,txtCodUsu.getVlrString());
  			ps.setString(4,((String[])vAcessos.elementAt(i))[3]);
  			ps.setString(5,((String[])vAcessos.elementAt(i))[2]);
  			ps.setString(6,((String[])vAcessos.elementAt(i))[1]);
  			if (((String[])vAcessos.elementAt(i))[0].equals("0000"))
    			ps.setString(7,"A");
  			else if (((String[])vAcessos.elementAt(i))[0].equals("0000"))
    			ps.setString(7,"B");
  			else if (((String[])vAcessos.elementAt(i))[0].equals("0010"))
    			ps.setString(7,"C");
  			else if (((String[])vAcessos.elementAt(i))[0].equals("0011"))
    			ps.setString(7,"D");
  			else if (((String[])vAcessos.elementAt(i))[0].equals("0100"))
    			ps.setString(7,"E");
  			else if (((String[])vAcessos.elementAt(i))[0].equals("0101"))
    			ps.setString(7,"F");
  			else if (((String[])vAcessos.elementAt(i))[0].equals("0110"))
    			ps.setString(7,"G");
  			else if (((String[])vAcessos.elementAt(i))[0].equals("0111"))
    			ps.setString(7,"H");
  			else if (((String[])vAcessos.elementAt(i))[0].equals("1000"))
    			ps.setString(7,"I");
  			else if (((String[])vAcessos.elementAt(i))[0].equals("1001"))
    			ps.setString(7,"J");
  			else if (((String[])vAcessos.elementAt(i))[0].equals("1010"))
    			ps.setString(7,"K");
  			else if (((String[])vAcessos.elementAt(i))[0].equals("1011"))
    			ps.setString(7,"L");
  			else if (((String[])vAcessos.elementAt(i))[0].equals("1100"))
    			ps.setString(7,"M");
  			else if (((String[])vAcessos.elementAt(i))[0].equals("1101"))
    			ps.setString(7,"N");
  			else if (((String[])vAcessos.elementAt(i))[0].equals("1110"))
    			ps.setString(7,"O");
  			else if (((String[])vAcessos.elementAt(i))[0].equals("1111"))
    			ps.setString(7,"P");
    		else
    			ps.setString(7,"A");
    		ps.execute();
    	    ps.close();
    	    if (!con.getAutoCommit())
    	    	con.commit();
  		}
  		catch(SQLException err) {
			Funcoes.mensagemErro(this,"Erro ao gravar o acesso: "+i+"\n"+err.getMessage()); 
  			break;
  		}
  	}
  }
  private void montaCombo () {
  	try {
	  String sSQL = "SELECT FL.CODFILIAL,FL.NOMEFILIAL FROM SGFILIAL FL, SGACESSOEU AC WHERE "+
				    "FL.CODEMP = ? AND UPPER(AC.IDUSU) = ? AND FL.CODEMP = AC.CODEMPFL AND FL.CODFILIAL = AC.CODFILIALFL";
	  PreparedStatement ps = con.prepareStatement(sSQL);
	  ps.setInt(1,Aplicativo.iCodEmp);
	  ps.setString(2,txtCodUsu.getVlrString().toUpperCase());
	  ResultSet rs = ps.executeQuery();
	  vVals.clear();
	  vLabs.clear();
	  while (rs.next()) {
	    vVals.addElement(new Integer(rs.getInt("CODFILIAL")));
	    vLabs.addElement(rs.getString("NOMEFILIAL") != null ? rs.getString("NOMEFILIAL") : "");
	  }
	  cbFiliais.setItens(vLabs,vVals);
  	}
  	catch(SQLException err) {
		Funcoes.mensagemErro(this,"Erro ao montar a grade de filiais!\n"+err.getMessage());
  	}
  }
  public void setConexao(Connection cn) {
  	super.setConexao(cn);
  	lcUsuario.setConexao(con);
  }
  public void actionPerformed(ActionEvent evt) {

    if (evt.getSource() == btSalva) {
    	if (txtCodUsu.getVlrString().equals("")) {
			Funcoes.mensagemInforma(this,"Código do usuário inválido!!!");
  	    	return;
  		}
    	gravaAcessos();
		btSalva.setEnabled(false);
    }
    else if (evt.getSource() == cbFiliais) {
    	montaArvore();
    }
  	
  	super.actionPerformed(evt);
  }
  public void afterCarrega(CarregaEvent cevt) {
  	if (cevt.ok) {
		montaCombo();
		montaArvore();
  	}
  	else
        arvore.setModel(new DefaultTreeModel(new DefaultMutableTreeNode("Acesso aos menus")));  	    
  }
  public void beforeCarrega(CarregaEvent cevt) {}
}
