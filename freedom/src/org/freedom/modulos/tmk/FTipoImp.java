/**
 * @version 10/10/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.tmk <BR>
 * Classe: @(#)FTipoImp.java <BR>
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


package org.freedom.modulos.tmk;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.acao.DeleteEvent;
import org.freedom.acao.DeleteListener;
import org.freedom.acao.PostEvent;
import org.freedom.acao.PostListener;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.Tabela;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.FDados;
import org.freedom.bmps.Icone;

public class FTipoImp extends FDados implements PostListener, CarregaListener, DeleteListener, ActionListener {
  private JTextFieldPad txtCodTpImp = new JTextFieldPad(5);
  private JTextFieldPad txtDescTpImp = new JTextFieldPad(50);
  private JTextFieldPad txtSepTpImp = new JTextFieldPad(10);
  private JTextFieldPad txtDelCrtTpImp = new JTextFieldPad(2);
  private JTextFieldPad txtLayout = new JTextFieldPad();
  private JCheckBoxPad cbTransBinTpImp = new JCheckBoxPad("Converter arquivo binário","S","N");
  private JCheckBoxPad cbCsepTpImp = new JCheckBoxPad("Utiliza separador","S","N");  
  private JCheckBoxPad cbEnterlnTpImp = new JCheckBoxPad("Enter quebra linha","S","N");
  private Tabela tab = new Tabela();  
  private JScrollPane spnLayout = new JScrollPane(tab);    
  private Tabela tabDest = new Tabela();  
  private JScrollPane spnLayoutDest = new JScrollPane(tabDest);    
  private JButton btAdic = new JButton(Icone.novo("btFlechaDir.gif"));
  private JButton btDel = new JButton(Icone.novo("btFlechaEsq.gif"));
  public FTipoImp() {
    setTitulo("Cadastro de layout de importação");
    setAtribos(50, 20, 510, 390);

    cbCsepTpImp.setVlrString("N");
	cbTransBinTpImp.setVlrString("N");

    adicCampo(txtCodTpImp, 7, 20, 70, 20, "CodTpImp", "Código", JTextFieldPad.TP_INTEGER, 5, 0, true, false, null, true);
    adicCampo(txtDescTpImp,80, 20, 390, 20, "DescTpImp", "Descrição", JTextFieldPad.TP_STRING, 50, 0, false, false, null, true);
	adicDB(cbCsepTpImp,7, 45, 130, 20, "CsepTpImp", "",JTextFieldPad.TP_STRING,true);    
	adicDB(cbEnterlnTpImp,140, 45, 147, 20, "EnterLnTpImp", "",JTextFieldPad.TP_STRING,true);
	adicDB(cbTransBinTpImp, 290,   45, 180, 20, "TransBinTpImp", "",JTextFieldPad.TP_STRING,true);
	adicCampo(txtSepTpImp,7,85,80, 20, "SepTpImp", "Separador", JTextFieldPad.TP_STRING, 10, 0, false, false,null, true);
	adicCampo(txtDelCrtTpImp,90,85, 80, 20, "DelCrtTpImp", "Ignorar carac. até", JTextFieldPad.TP_INTEGER, 5, 0, false, false, null, true);	
    adicDBLiv(txtLayout,"LayoutTpImp", "Layout",JTextFieldPad.TP_STRING, false);
    adic(new JLabel("Compos disponiveis"),7,110,200,20);
	adic(new JLabel("Compos selecionados"),260,110,200,20);
    adic(spnLayout,7,130,213,180);
	adic(spnLayoutDest,260,130,213,180);
	adic(btAdic,225,180,30,30);
	adic(btDel,225,215,30,30);
    setListaCampos( true, "TIPOIMP", "TK");
    lcCampos.setQueryInsert(false);
    
	tab.adicColuna("Campo");    
	tab.adicColuna("Tipo");    
	tabDest.adicColuna("Campo");    
	tabDest.adicColuna("Tipo");    

	tab.setTamColuna(115,0);
	tab.setTamColuna(80,1);
	tabDest.setTamColuna(115,0);
	tabDest.setTamColuna(80,1);
	
	lcCampos.addPostListener(this);
	lcCampos.addCarregaListener(this);
	lcCampos.addDeleteListener(this);
	
    btAdic.addActionListener(this);
	btDel.addActionListener(this);
    
  }
  private void adiciona() {
	if (tab.getNumLinhas() < 1) 
	  return;
	for (int i=tab.getNumLinhas()-1;i>=0;i--) {
	  if (tab.getSelectedRow() == i) {
		tabDest.adicLinha(tab.getLinha(i));
		tab.tiraLinha(i);
	  }
	}
	lcCampos.edit();
  }
  private void remove() {
	if (tabDest.getNumLinhas() < 1) 
	  return;
	for (int i=tab.getNumLinhas()-1;i>=0;i--) {
		if (tabDest.getSelectedRow() == i) {
		  tab.adicLinha(tabDest.getLinha(i));
		  tabDest.tiraLinha(i);
		}
	}
	lcCampos.edit();
  }
  private void ajustaTabela() {
  	String[] sLinhas = txtLayout.getVlrString().split(",");
	String sSQL = "SELECT * FROM TKCONTATO WHERE 1=0";
	try {
		PreparedStatement ps = con.prepareStatement(sSQL);
		ResultSet rs = ps.executeQuery();
		ResultSetMetaData met = rs.getMetaData();
		tab.limpa();
		for (int i=1;i<=met.getColumnCount();i++) {
			boolean bColEq = false;
			if (met.getColumnName(i).indexOf("CODEMP") >= 0  || met.getColumnName(i).indexOf("CODFILIAL") >= 0)
				continue;
			for (int j=0;j<sLinhas.length;j++) {
				String[] sVals = sLinhas[j].split(" ");
				if (bColEq = met.getColumnName(i).equals(sVals[0])) {
					tabDest.adicLinha(sVals);
					break;
				}
			}
			if (!bColEq) {
			  Vector vVals = new Vector();
			  vVals.add(met.getColumnName(i));
			  vVals.add(met.getColumnTypeName(i));
			  tab.adicLinha(vVals);
			}
		}
		rs.close();
		ps.close();
    }
	catch (SQLException err) {
		Funcoes.mensagemErro(this,"Erro ao carregar colunas atuais!\n"+err.getMessage());
		err.printStackTrace();
	}
  }
  public void actionPerformed(ActionEvent evt) {
	if (evt.getSource() == btAdic)
		adiciona();
	else if (evt.getSource() == btDel)
		remove();
	super.actionPerformed(evt);

  }
  public void beforePost(PostEvent pevt) {
  	txtLayout.setText("");
  	String sSep = "";
  	for (int i=0;i<tabDest.getNumLinhas();i++) {
		txtLayout.setText(txtLayout.getText()+sSep+tabDest.getValor(i,0)+" "+tab.getValor(i,1));
		sSep = ",";
  	}
  }
  public void afterPost(PostEvent pevt) { }
  public void beforeCarrega(CarregaEvent cevt) { }
  public void afterCarrega(CarregaEvent cevt) {
  	if (cevt.ok) 
  		ajustaTabela();
  }
  public void beforeDelete(DeleteEvent devt) { }
  public void afterDelete(DeleteEvent devt) { }
  public void execShow(Connection cn) {
	con = cn;
	super.execShow(cn);
  }
}
