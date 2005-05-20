/**
 * @version 11/02/2002 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FGrupo.java <BR>
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

package org.freedom.modulos.std;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import org.freedom.componentes.JPanelPad;
import javax.swing.JScrollPane;

import org.freedom.bmps.Icone;
import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Tabela;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FFilho;

public class FGrupo extends FFilho implements ActionListener,MouseListener,KeyListener {
  private Tabela tab = new Tabela();
  private JScrollPane spnTab = new JScrollPane(tab);
  private FlowLayout flCliRod = new FlowLayout(FlowLayout.CENTER,0,0);
  private JPanelPad pnCli = new JPanelPad(JPanelPad.TP_JPANEL,new GridLayout(1,1));
  private JPanelPad pnRodape = new JPanelPad(JPanelPad.TP_JPANEL,new BorderLayout());
  private JPanelPad pnCliRod = new JPanelPad(JPanelPad.TP_JPANEL,flCliRod);
  private JPanelPad pnBotoes = new JPanelPad(JPanelPad.TP_JPANEL,new GridLayout(1,2,2,0));
  private JPanelPad pnImp = new JPanelPad(JPanelPad.TP_JPANEL,new GridLayout(1,2,0,0));
  private JButton btSair = new JButton("Sair",Icone.novo("btSair.gif"));
  private JButton btGrupo = new JButton("Grupo",Icone.novo("btNovo.gif"));
  private JButton btSubGrupo = new JButton("Sub-Grupo",Icone.novo("btNovo.gif"));
  private JButton btImp = new JButton(Icone.novo("btImprime.gif"));
  private JButton btPrevimp = new JButton(Icone.novo("btPrevimp.gif"));
  private boolean bEstNeg = false;
  int iCodFilial = 0;
  public FGrupo() {
    setTitulo("Cadastro de Grupos e Sub-Grupos");
    setAtribos( 5, 5, 590, 350);
    
    Container c = getContentPane();
    
    c.setLayout(new BorderLayout());
    
    pnRodape.setPreferredSize(new Dimension(740, 33));
    pnRodape.setBorder(BorderFactory.createEtchedBorder());
    pnRodape.setLayout(new BorderLayout());
    pnRodape.add(btSair, BorderLayout.EAST);
    pnRodape.add(pnCliRod, BorderLayout.CENTER);
    
    pnCliRod.add(pnBotoes);
    
    pnImp.add(btImp);
    pnImp.add(btPrevimp);
    
    pnBotoes.setPreferredSize(new Dimension(400,29));
    pnBotoes.add(btGrupo);
    pnBotoes.add(btSubGrupo);
    pnBotoes.add(pnImp);

    c.add(pnRodape, BorderLayout.SOUTH);
    
    pnCli.add(spnTab);
    c.add(pnCli, BorderLayout.CENTER);
    
    tab.adicColuna("Cód.grupo");
    tab.adicColuna("Descrição do grupo");
    tab.adicColuna("Sigla");
    tab.setTamColuna(100,0);
    tab.setTamColuna(385,1);
    tab.setTamColuna(80,2);
    
    btSair.addActionListener(this);
    btGrupo.addActionListener(this);
    btSubGrupo.addActionListener(this);
    btImp.addActionListener(this);
    btPrevimp.addActionListener(this);    
    tab.addMouseListener(this);
    tab.addKeyListener(this);

  }
  public void setConexao(Connection cn) {
    super.setConexao(cn);
    buscaPrefere();
    montaTab();
  }
  
  private void buscaPrefere(){
  	String sSQL = "SELECT ESTNEGGRUP FROM SGPREFERE1 WHERE CODEMP=? AND CODFILIAL=?";
  	PreparedStatement ps = null;
  	ResultSet rs = null;
  	try{
  		ps = con.prepareStatement(sSQL);
  		ps.setInt(1,Aplicativo.iCodEmp);
  		ps.setInt(2,ListaCampos.getMasterFilial("SGPREFERE1"));
  		rs = ps.executeQuery();
  		
  		if(rs.next()){
  			if(rs.getString(1)!=null){
  				if(rs.getString(1).equals("S"))
  					bEstNeg = true;
  				else
  					bEstNeg = false;
  			}
  		}
  	}
  	catch(Exception err){
  		Funcoes.mensagemErro(this,"Erro ao consultar tabela de preferencias!",true,con,err);
  		err.printStackTrace();
  	}
  }
  
  private void montaTab() {
    String sSQL = "SELECT CODGRUP,DESCGRUP,SIGLAGRUP FROM EQGRUPO WHERE "+
                  "CODEMP=? AND CODFILIAL =? ORDER BY CODGRUP";
    PreparedStatement ps = null;
    ResultSet rs = null;
    tab.limpa();
    try {
      ps = con.prepareStatement(sSQL);
      ps.setInt(1,Aplicativo.iCodEmp);
      ps.setInt(2,ListaCampos.getMasterFilial("EQGRUPO"));
      rs = ps.executeQuery();
      if(bEstNeg){
        tab.adicColuna("Est.neg.");
        tab.adicColuna("Est.neg.lote");
        tab.setTamColuna(80,3);
        tab.setTamColuna(80,4);
      }
      
      for (int i=0;rs.next();i++) {
        tab.adicLinha();
        tab.setValor(rs.getString("CodGrup"),i,0);
        tab.setValor(rs.getString("DescGrup"),i,1);
        tab.setValor(rs.getString("SiglaGrup"),i,2);
      }
//      rs.close();
//      ps.close();
      if (!con.getAutoCommit())
      	con.commit();
    }
    catch(SQLException err) {
		Funcoes.mensagemErro(this,"Erro ao consultar a tabela EQGRUPO! ! !\n"+err.getMessage(),true,con,err);
    }
  }
  private void gravaNovoGrup() {
    DLGrupo dl = new DLGrupo(this,con,null,null,"",bEstNeg);
    dl.setVisible(true);
    if (!dl.OK)
      return;
    String sCod = (dl.getValores())[0];
    String sDesc = (dl.getValores())[1];
    String sSigla = (dl.getValores())[2];
    String sEstNeg = (dl.getValores())[3];
    String sEstNegLot = (dl.getValores())[4];
    dl.dispose();
    String sSQL = "INSERT INTO EQGRUPO (codemp,codfilial,codgrup,descgrup,nivelgrup,siglagrup,estneggrup,estlotneggrup) VALUES(?,?,'"+
                   sCod+Funcoes.replicate("0",4-sCod.trim().length())+"','"+
                   sDesc.trim()+"',1,'"+sSigla+"',?,?)";
    PreparedStatement ps = null;
    try {
      ps = con.prepareStatement(sSQL);
      ps.setInt(1,Aplicativo.iCodEmp);
      ps.setInt(2,ListaCampos.getMasterFilial("EQGRUPO"));
      ps.setString(3,sEstNeg);
      ps.setString(4,sEstNegLot);
      if (ps.executeUpdate() == 0)
	  Funcoes.mensagemInforma(this,"Não foi possível inserir na tabela EQGRUPO");
      if (!con.getAutoCommit())
      	con.commit();
//      ps.close();
    }
    catch (SQLException err) {
		Funcoes.mensagemInforma(this,"Erro ao inserir registro na tabela EQGRUPO! ! !\n"+err.getMessage());
    }
    if (tab.getNumLinhas() > 0)
      tab.setRowSelectionInterval(0,1);
  }
  private void gravaNovoSubGrup() {
    String sCodPai = "";
    String sDescPai= "";
    String sSiglaPai= "";
    String sCodFilho = "";
    int iCodFilho = 0;
    int iNivelPai = 0;
    int iNivel = 0;
    String sMax = "";
    if (tab.getLinhaSel() < 0) {
		Funcoes.mensagemInforma(this,"Selecione a origem do Sub-Grupo na tabela! ! !");
      return;
    }
    else if ((""+tab.getValor(tab.getLinhaSel(),0)).trim().length() == 14) {
		Funcoes.mensagemInforma(this,"Cada grupo pode ter no máximo cinco sub-divisões! ! !");
      return;
    }
    String sSQLQuery = "SELECT S.NIVELGRUP,(SELECT MAX(M.CODGRUP) FROM EQGRUPO M "+
                       "WHERE M.CODSUBGRUP=S.CODGRUP AND M.CODEMP=S.CODEMP AND M.CODFILIAL=S.CODFILIAL) FROM EQGRUPO S "+
                       "WHERE S.CODEMP=? AND S.CODFILIAL=? AND S.CODGRUP='"+(tab.getValor(tab.getLinhaSel(),0)+"").trim()+"'";
    PreparedStatement psQuery = null;
    ResultSet rsQuery = null;
    try {
      psQuery = con.prepareStatement(sSQLQuery);
      psQuery.setInt(1,Aplicativo.iCodEmp);
      psQuery.setInt(2,ListaCampos.getMasterFilial("EQGRUPO"));
      rsQuery = psQuery.executeQuery();
      if (!rsQuery.next()) {
		Funcoes.mensagemInforma(this,"Não foi possível consultar a tabela GRUPO");
        return;
      }
      sCodPai = (tab.getValor(tab.getLinhaSel(),0)+"").trim();
      sDescPai = (tab.getValor(tab.getLinhaSel(),1)+"").trim();
      sSiglaPai = (tab.getValor(tab.getLinhaSel(),2)+"").trim();
      iNivelPai = rsQuery.getInt("NivelGrup");
      sMax = rsQuery.getString(2) != null ? rsQuery.getString(2).trim() : sCodPai+"00";
      iCodFilho = Integer.parseInt(sMax.substring(sMax.length()-2,sMax.length()));
      sCodFilho = sCodPai+Funcoes.strZero(""+(iCodFilho+1),2);
      iNivel = iNivelPai+1;          
//      rsQuery.close();
//      psQuery.close();
      if (!con.getAutoCommit())
      	con.commit();
    }
    catch(SQLException err) {
		Funcoes.mensagemInforma(this,"Erro ao consultar a tabela EQGRUPO! ! !\n"+err.getMessage());    
      return;
    }
    DLSubGrupo dl = new DLSubGrupo(sCodPai,sDescPai,sCodFilho,null,"",sSiglaPai,bEstNeg);
    dl.setVisible(true);
    if (!dl.OK) {
      dl.dispose();
      return;
    }
    String sDesc = dl.getValor()[0];
    dl.dispose();
    String sSQL = "INSERT INTO EQGRUPO (codemp,codfilial,codgrup,descgrup,codsubgrup,nivelgrup,siglagrup,codempsg,codfilialsg,estneggrup,estlotneggrup) VALUES(?,?,'"+sCodFilho+"','"+sDesc+"','"+sCodPai+"',"+iNivel+",?,?,?)";
    PreparedStatement ps = null;
    String sSigla = dl.getValor()[1];
    String sEstNeg = (dl.getValor())[2];
    String sEstNegLot = (dl.getValor())[3];
    try {
      ps = con.prepareStatement(sSQL);
      ps.setInt(1,Aplicativo.iCodEmp);
      ps.setInt(2,ListaCampos.getMasterFilial("EQGRUPO"));     
      ps.setString(3,sSigla);
      ps.setInt(4,Aplicativo.iCodEmp);
      ps.setInt(5,ListaCampos.getMasterFilial("EQGRUPO"));
      ps.setString(6,sEstNeg);
      ps.setString(7,sEstNegLot);
      
      if (ps.executeUpdate() == 0)
	  Funcoes.mensagemInforma(this,"Não foi possível inserir na tabela EQGRUPO");
//      ps.close();
      if (!con.getAutoCommit())
      	con.commit();
    }
    catch (SQLException err) {
		Funcoes.mensagemErro(this,"Erro ao inserir registro na tabela EQGRUPO!\n"+err.getMessage(),true,con,err);
    }
  }
  public void editaGrup() {
    DLGrupo dl = new DLGrupo(this,con,""+tab.getValor(tab.getLinhaSel(),0),(""+tab.getValor(tab.getLinhaSel(),1)).trim(),(""+tab.getValor(tab.getLinhaSel(),2)).trim(),bEstNeg);
    dl.setVisible(true);
    if (!dl.OK) {
      dl.dispose();
      return;
    }
    String sCod = dl.getValores()[0];
    sCod = Funcoes.replicate("0",4-sCod.trim().length())+sCod.trim();
    String sDesc = dl.getValores()[1];
    sDesc = sDesc.trim();
    String sSigla = dl.getValores()[2];
    sSigla = sSigla.trim();
    String sEstNeg = (dl.getValores())[3];
    String sEstNegLot = (dl.getValores())[4];
    dl.dispose();
    String sSQL = "UPDATE EQGRUPO SET CODGRUP='"+sCod+"',DESCGRUP='"+sDesc+"',SIGLAGRUP='"+sSigla+"', ESTNEGGRUP='"+sEstNeg+"',ESTLOTNEGGRUP='"+sEstNegLot+"'"+
                  " WHERE CODEMP=? AND CODFILIAL = ? AND CODGRUP='"+(tab.getValor(tab.getLinhaSel(),0)+"").trim()+"'";     
    PreparedStatement ps = null;
    try {
      ps = con.prepareStatement(sSQL);
      ps.setInt(1,Aplicativo.iCodEmp);
      ps.setInt(2,ListaCampos.getMasterFilial("EQGRUPO"));
      if (ps.executeUpdate() == 0)
	  Funcoes.mensagemInforma(this,"Não foi possível editar na tabela GRUPO");
      if (!con.getAutoCommit())
      	con.commit();
//      ps.close();
    }
    catch (SQLException err) {
		Funcoes.mensagemErro(this, "Erro ao editar a tabela EQGRUPO!\n"+err.getMessage(),true,con,err);
    }
  }
  public void editaSubGrup() {
    String sCodPai = (""+tab.getValor(tab.getLinhaSel(),0)).trim().substring(0,
                  (""+tab.getValor(tab.getLinhaSel(),0)).trim().length()-2); 
    String sDescPai = "";
    String sSiglaPai = "";
    String sCodFilho = ""+tab.getValor(tab.getLinhaSel(),0);
    String sSQLQuery = "SELECT DESCGRUP,SIGLAGRUP FROM EQGRUPO WHERE CODEMP = ? AND CODFILIAL = ? AND CODGRUP='"+sCodPai+"'";
    PreparedStatement psQuery = null;
    ResultSet rs = null;
    try {
      psQuery = con.prepareStatement(sSQLQuery);
      psQuery.setInt(1,Aplicativo.iCodEmp);
      psQuery.setInt(2,ListaCampos.getMasterFilial("EQGRUPO"));
      rs = psQuery.executeQuery();
      if (!rs.next()) {
		Funcoes.mensagemInforma(this, "Não foi possível editar a tabela GRUPO! ! !");
        return;
      }
      sDescPai = rs.getString("DescGrup");
      sSiglaPai = rs.getString("SiglaGrup");
      if (!con.getAutoCommit())
      	con.commit();
    }
    catch (SQLException err) {
		Funcoes.mensagemErro(this,"Erro ao consultar a tabela EQGRUPO!\n"+err.getMessage(),true,con,err);
    }
    DLSubGrupo dl = new DLSubGrupo(sCodPai,sDescPai,sCodFilho,(""+tab.getValor(tab.getLinhaSel(),1)).trim(),(""+tab.getValor(tab.getLinhaSel(),2)).trim(),sSiglaPai,bEstNeg);
    dl.setVisible(true);
    if (!dl.OK) {
      dl.dispose();
      return;
    }
    String sDesc = dl.getValor()[0];
    String sSigla = dl.getValor()[1];
    String sEstNeg = (dl.getValor())[2];
    String sEstNegLot = (dl.getValor())[3];
    sDesc = sDesc.trim();
    sSigla = sSigla.trim();
    dl.dispose();
    String sSQL = "UPDATE EQGRUPO SET DESCGRUP='"+sDesc+"',SIGLAGRUP='"+sSigla+"',SIGLAGRUP='"+sSigla+"', ESTNEGGRUP='"+sEstNeg+"',ESTLOTNEGGRUP='"+sEstNegLot+"'"+
                  " WHERE CODEMP=? AND CODFILIAL=? AND CODGRUP='"+sCodFilho+"'";     
    PreparedStatement ps = null;
    try {
      ps = con.prepareStatement(sSQL);
      ps.setInt(1,Aplicativo.iCodEmp);
      ps.setInt(2,ListaCampos.getMasterFilial("EQGRUPO"));
      if (ps.executeUpdate() == 0)
	  Funcoes.mensagemInforma(this,"Não foi possível editar na tabela GRUPO");
      if (!con.getAutoCommit())
      	con.commit();
//      ps.close();
    }
    catch (SQLException err) {
		Funcoes.mensagemErro(this, "Erro ao editar a tabela EQGRUPO!\n"+err.getMessage(),true,con,err);
    }
  }
  public void deletar() {
    if (tab.getLinhaSel() < 0) 
      return;
    if (Funcoes.mensagemConfirma(this, "Deseja realmente deletar este registro?")!=0 ) 
      return;
    String sSQL = "DELETE FROM EQGRUPO WHERE CODEMP=? AND CODFILIAL=? AND CODGRUP='"+(""+tab.getValor(tab.getLinhaSel(),0)).trim()+"'";
    PreparedStatement ps = null;
    try {
      ps = con.prepareStatement(sSQL);
      ps.setInt(1,Aplicativo.iCodEmp);
      ps.setInt(2,ListaCampos.getMasterFilial("EQGRUPO"));
      if (ps.executeUpdate() == 0) {
		Funcoes.mensagemInforma(this,"Não foi possível deletar um registro na tabela GRUPO! ! !");
      }
      if (!con.getAutoCommit())
      	con.commit();
    }
    catch (SQLException err) {
      if (err.getErrorCode() == 335544466)
	  Funcoes.mensagemInforma(this, "O registro possui vínculos, não pode ser deletado! ! !");
      else 
	  Funcoes.mensagemErro(this,"Erro ao deletar um registro na tabela GRUPO!\n"+err.getMessage(),true,con,err);
    }
  }
  public void actionPerformed(ActionEvent evt) {
    if (evt.getSource() == btSair) {
      dispose();
    }
    else if (evt.getSource() == btGrupo) {
      gravaNovoGrup();
      montaTab();
    }
    else if (evt.getSource() == btSubGrupo) {
      gravaNovoSubGrup();
      montaTab();
    }
    else if (evt.getSource() == btPrevimp) {
    	imprimir(true);
    }
    else if (evt.getSource() == btImp) 
    	imprimir(false);
    
  }
  public void mouseClicked(MouseEvent mevt) {
    if ((mevt.getSource() == tab) & (mevt.getClickCount() == 2) & (tab.getLinhaSel() >= 0)) {
      if ((""+tab.getValor(tab.getLinhaSel(),0)).trim().length() > 4) {
        editaSubGrup();
        montaTab();
      }
      else {
        editaGrup();
        montaTab();
      }
    }
  }
  public void keyPressed(KeyEvent kevt) {
    if ((kevt.getKeyCode() == KeyEvent.VK_DELETE) & (kevt.getSource() == tab)) {
      deletar();
      montaTab();
    }
  }

  private void imprimir(boolean bVisualizar) {
  	ImprimeOS imp = new ImprimeOS("",con);
  	int linPag = imp.verifLinPag()-1;
  	
  	String sSQL = "SELECT CODGRUP,DESCGRUP "
  		+"FROM EQGRUPO WHERE CODEMP=? AND CODFILIAL=? ORDER BY 1";
  	PreparedStatement ps = null;
  	ResultSet rs = null;
  	try {
  		ps = con.prepareStatement(sSQL);
  		ps.setInt(1,Aplicativo.iCodEmp);
  		ps.setInt(2,ListaCampos.getMasterFilial("EQPRODUTO"));
  		rs = ps.executeQuery();
  		imp.limpaPags();
  		while ( rs.next() ) {
  			if (imp.pRow()==0) {
  				imp.montaCab();
  			  	imp.setTitulo("Relatório de Grupos");
  			  	imp.addSubTitulo("Relatório de Grupos");
  				imp.impCab(80, false);
  				imp.say(imp.pRow()+0,0,""+imp.normal());
  				imp.say(imp.pRow()+0,0,"");
  				imp.say(imp.pRow()+0,2,"Cód.grupo");
  				imp.say(imp.pRow()+0,29,"Descrição do grupo");
  				imp.say(imp.pRow()+1,0,""+imp.normal());
  				imp.say(imp.pRow()+0,0,Funcoes.replicate("-",79));
  			}
  			imp.say(imp.pRow()+1,0,""+imp.normal());
  			imp.say(imp.pRow()+0,0,"");
  			imp.say(imp.pRow()+0,2,rs.getString("CodGrup"));
  			imp.say(imp.pRow()+0,29,Funcoes.copy(rs.getString("DescGrup"),0,40));
  			if (imp.pRow()>=linPag) {
  				imp.say(imp.pRow()+1,0,""+imp.normal());
  				imp.say(imp.pRow()+0,0,Funcoes.replicate("=",79));
  				imp.incPags();
  				imp.eject();
  			}
  		}
  		
  		imp.say(imp.pRow()+1,0,""+imp.normal());
  		imp.say(imp.pRow()+0,0,Funcoes.replicate("=",79));
  		imp.eject();
  		
  		imp.fechaGravacao();
  		
  		if (!con.getAutoCommit())
  			con.commit();

  	}  
  	catch ( SQLException err ) {
  		Funcoes.mensagemErro(this,"Erro consulta tabela de Grupo!\n"+err.getMessage(),true,con,err);      
  	}
  	
  	if (bVisualizar) {
  		imp.preview(this);
  	}
  	else {
  		imp.print();
  	}
  }
  public void keyTyped(KeyEvent kevt) { }
  public void keyReleased(KeyEvent kevt) { }
  public void mouseEntered(MouseEvent mevt) { }
  public void mouseExited(MouseEvent mevt) { }
  public void mousePressed(MouseEvent mevt) { }
  public void mouseReleased(MouseEvent mevt) { }
}
