/**
 * @version 20/06/2005 <BR>
 * @author Setpoint Informática Ltda./Anderson Sanchez <BR>
 * 
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.pcp <BR>
 * Classe:
 * @(#)FreedomPCP.java <BR>
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
 * Tela de baixa de RMA por Código de barras.
 *  
 */
package org.freedom.modulos.pcp;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.bmps.Icone;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Tabela;
import org.freedom.funcoes.Funcoes;
import org.freedom.modulos.gms.FRma;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FFilho;

public class FBaixaRMACodBar extends FFilho implements ActionListener,CarregaListener,FocusListener{
  private JPanelPad pinCab = new JPanelPad(0, 215);
  private JPanelPad pnCli = new JPanelPad(JPanelPad.TP_JPANEL,new BorderLayout());
  private JPanelPad pnRod = new JPanelPad(JPanelPad.TP_JPANEL,new BorderLayout());
  private Tabela tab = new Tabela();
  private JScrollPane spnTab = new JScrollPane(tab);	
  private JButton btBusca = new JButton("Buscar", Icone.novo("btPesquisa.gif"));
  private JButton btPrevimp = new JButton("Imprimir", Icone.novo("btPrevimp.gif"));
  private JButton btSair = new JButton("Sair", Icone.novo("btSair.gif"));
  private JTextFieldPad txtEntrada = new JTextFieldPad(JTextFieldPad.TP_STRING,100,0);
  private JTextFieldPad txtSeqOf = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtCodOp = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtCodProd = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtCodLote = new JTextFieldPad(JTextFieldPad.TP_STRING,8,0);
  private JTextFieldPad txtQtdEntrada = new JTextFieldPad(JTextFieldPad.TP_DECIMAL,15,5);
  private ImageIcon imgCancelada = Icone.novo("clVencido.gif");
  private ImageIcon imgExpedida = Icone.novo("clPago.gif");
  private ImageIcon imgAprovada = Icone.novo("clPagoParcial.gif");
  private ImageIcon imgPendente = Icone.novo("clNaoVencido.gif");
  private ImageIcon imgColuna = null;

    
  public FBaixaRMACodBar () {  
    setAtribos( 50, 50, 600, 350);
	Container c = getTela();
	c.add(pnRod, BorderLayout.SOUTH);
	c.add(pnCli, BorderLayout.CENTER);
	pnCli.add(pinCab, BorderLayout.NORTH);
	pnCli.add(spnTab, BorderLayout.CENTER);

	btSair.setPreferredSize(new Dimension(100, 30));

	pnRod.add(btSair, BorderLayout.EAST);
    
    pinCab.adic(new JLabelPad("código de barras"),7,0,150,20);
    pinCab.adic(txtEntrada,7,20,150,20);
    
    pinCab.adic(new JLabelPad("Sequencia da OP"),7,40,150,20);
    pinCab.adic(txtSeqOf,7,60,150,20);
    
    pinCab.adic(txtCodOp,7,100,150,20);
    
    pinCab.adic(txtCodProd,7,140,150,20);
    
    pinCab.adic(txtCodLote,7,200,150,20);
    
    pinCab.adic(txtQtdEntrada,7,240,150,20);
    
	tab.adicColuna("");//0
	tab.adicColuna("Cód.rma.");//1
	tab.adicColuna("Cód.prod.");//2
	tab.adicColuna("Descrição do produto");//3
	tab.adicColuna("Qt. requerida");//4
	tab.adicColuna("Qt. aprovada");//5
	tab.adicColuna("Qt. expedida");//6
	tab.adicColuna("Saldo");//7
	

	tab.setTamColuna(12, 0);
	tab.setTamColuna(70, 1);
	tab.setTamColuna(70, 2);
	tab.setTamColuna(120, 3);
	tab.setTamColuna(90, 4);
	tab.setTamColuna(90, 5);
	tab.setTamColuna(90, 6);
	tab.setTamColuna(100, 7);

    txtEntrada.addFocusListener(this);
    btSair.addActionListener(this);
	tab.addMouseListener(new MouseAdapter() {

		public void mouseClicked(MouseEvent mevt) {
			if (mevt.getSource() == tab && mevt.getClickCount() == 2)
				abreRma();
		}
	});
        
  }
	private void abreRma() {
		int iRma = ((Integer) tab.getValor(tab.getLinhaSel(), 1)).intValue();
		if (fPrim.temTela("Requisição de material") == false) {
			FRma tela = new FRma();
			fPrim.criatela("Requisição de material", tela, con);
			tela.exec(iRma);
		}
	}
  public void buscaItem(){
	String sSQL = "SELECT R.CODRMA, IT.CODPROD,IT.REFPROD,PD.DESCPROD,IT.SITITRMA,"
		+ "IT.SITAPROVITRMA,IT.SITEXPITRMA,IT.DTINS,IT.DTAPROVITRMA,IT.DTAEXPITRMA,"
		+ "IT.QTDITRMA,IT.QTDAPROVITRMA,IT.QTDEXPITRMA,PD.SLDPROD "
		+ "FROM EQRMA R, EQITRMA IT, EQPRODUTO PD "
		+ "WHERE " 
		+ "R.CODEMP=IT.CODEMP AND R.CODFILIAL=IT.CODFILIAL AND R.CODRMA=IT.CODRMA "
		+ "AND PD.CODEMP=IT.CODEMP AND PD.CODFILIAL=IT.CODFILIAL AND PD.CODPROD=IT.CODPROD "
		+ "AND IT.CODEMPPD=? AND IT.CODFILIALPD=? AND IT.CODPROD=? "
		+ "AND IT.CODEMPLE=IT.CODEMPPD AND IT.CODFILIALLE=IT.CODFILIALPD AND IT.CODLOTE=? "
		+ "AND R.CODEMPOF=IT.CODEMP AND R.CODFILIALOF=? AND R.CODOP=? AND R.SEQOF=?";
		
		
	System.out.println(sSQL);
	try {
		PreparedStatement ps = con.prepareStatement(sSQL);
		int param = 1;
		ps.setInt(param++, Aplicativo.iCodEmp);
		ps.setInt(param++, ListaCampos.getMasterFilial("EQPRODUTO"));
		ps.setInt(param++, txtCodProd.getVlrInteger().intValue());
		ps.setString(param++, txtCodLote.getVlrString());
		ps.setInt(param++,ListaCampos.getMasterFilial("PPOP"));
		ps.setInt(param++,txtCodOp.getVlrInteger().intValue());
		ps.setInt(param++,txtSeqOf.getVlrInteger().intValue());

		ResultSet rs = ps.executeQuery();
		

		int iLin = tab.getNumLinhas();
	
//		tab.limpa();
		Vector vSitRMA = new Vector();
		while (rs.next()) {
			tab.adicLinha();
			
			String sitRMA = rs.getString(5);
			if (sitRMA.equalsIgnoreCase("PE")) {
				imgColuna = imgPendente;
				vSitRMA.addElement("Pendente");
			} 
			else if (sitRMA.equalsIgnoreCase("CA")) {
				imgColuna = imgCancelada;
				vSitRMA.addElement("Cancelada");
			} 
			else if (sitRMA.equalsIgnoreCase("EF")) {
				imgColuna = imgExpedida;
				vSitRMA.addElement("Expedida");
			} 
			else if (sitRMA.equalsIgnoreCase("AF")) {
				imgColuna = imgAprovada;
				vSitRMA.addElement("Aprovada");
			}
	
			tab.setValor(imgColuna, iLin, 0);//SitItRma
			tab.setValor(new Integer(rs.getInt(1)), iLin, 1);//CodRma
			tab.setValor(rs.getString(2) == null ? "" : rs.getString(2) + "",iLin, 2);//CodProd 
			tab.setValor(rs.getString(4) == null ? "" : rs.getString(4) + "",iLin, 3);//DescProd
			tab.setValor(rs.getString(11) == null ? "" : rs.getString(11) + "",iLin, 4);//Qtd Req
			tab.setValor(rs.getString(12) == null ? "" : rs.getString(12) + "",iLin, 5);//Qtd Aprov
			tab.setValor(rs.getString(13) == null ? "" : rs.getString(13) + "",iLin, 6);//Qdt Exp
			tab.setValor(rs.getString(14) == null ? "" : rs.getString(14) + "",iLin, 7);//Saldo Prod
	
			iLin++;
			
		}
	
		if (!con.getAutoCommit())
			con.commit();
	} catch (SQLException err) {
		Funcoes.mensagemErro(this, "Erro ao carregar a tabela EQRMA!\n"
				+ err.getMessage(),true,con,err);
		err.printStackTrace();
	}
	  	
	  	
  	
  	
  }
  public void beforeCarrega(CarregaEvent cevt){  }
  public void afterCarrega(CarregaEvent cevt){  }
  public void focusGained(FocusEvent e) {  }
  public void focusLost(FocusEvent e) { 
  	if(e.getSource()==txtEntrada){
  		decodeEntrada();
  	}
  }
	
  public void actionPerformed(ActionEvent evt) {
	if (evt.getSource() == btSair) {
		dispose();
	}
  }

  private void decodeEntrada(){

  	String sTexto = txtEntrada.getText();
  	
  	if(sTexto!=null){
		if (sTexto.length()>0){
			int iCampos = Funcoes.contaChar(sTexto,'#'); 
			if(iCampos==4) {
				Vector vCampos = new Vector();
				vCampos.addElement(txtSeqOf);
				vCampos.addElement(txtCodOp);
				vCampos.addElement(txtCodProd);
				vCampos.addElement(txtCodLote); 
				vCampos.addElement(txtQtdEntrada);

				String sResto = sTexto.replace('_','/');
				
				for(int i=0;vCampos.size()>i;i++){		
					JTextFieldPad jtCampo = ((JTextFieldPad)(vCampos.elementAt(i))); 
					jtCampo.setVlrString(sResto.substring(0,sResto.indexOf("#")>0?sResto.indexOf("#"):sResto.length()));
//					jtCampo.transferFocus();
					sResto = sResto.substring(sResto.indexOf("#")+1);
				}
				buscaItem();
			}
			else{
				Funcoes.mensagemInforma(this,"Entrada inválida!\nNúmero de campos incoerente."+Funcoes.contaChar(sTexto,'#'));
			}
		}
		else {
			Funcoes.mensagemInforma(this,"Entrada inválida!\nTexto em branco.");
		}
  	}
  	else{
  		Funcoes.mensagemInforma(this,"Entrada inválida!\nTexto nulo.");
  	}
  }
	
}
