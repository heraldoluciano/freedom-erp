/**
 * @version 11/02/2002 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FPlanoPag.java <BR>
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
 * Agrupamento de produtos similares.
 * 
 */

package org.freedom.modulos.std;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.acao.InsertEvent;
import org.freedom.acao.InsertListener;
import org.freedom.acao.PostEvent;
import org.freedom.acao.PostListener;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.telas.FDetalhe;

public class FSimilar extends FDetalhe implements CarregaListener, InsertListener, PostListener {
	private static final long serialVersionUID = 1L;
	private JPanelPad pinCab = new JPanelPad();
	private JPanelPad pinDet = new JPanelPad();
	private JTextFieldPad txtCodSim = new JTextFieldPad(JTextFieldPad.TP_INTEGER,5,0);
	private JTextFieldPad txtDescSim = new JTextFieldPad(JTextFieldPad.TP_STRING,40,0);
	private JTextFieldPad txtCodProd = new JTextFieldPad(JTextFieldPad.TP_INTEGER,5,0);
	private JTextFieldPad txtRefProd = new JTextFieldPad(JTextFieldPad.TP_STRING,13,0);
	private JTextFieldPad txtCodBarras = new JTextFieldPad(JTextFieldPad.TP_STRING,13,0);
	private JTextFieldFK txtDescProd = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
	private ListaCampos lcProd = new ListaCampos(this,"PD");
	
	public FSimilar () {
		setTitulo("Agrupamento de produtos similares");
		setAtribos( 100, 10, 450, 350);        
		
		//  ********************  Lista campos adicional  ********************    
		
		lcProd.add(new GuardaCampo( txtCodProd, "CodProd", "Cód.prod.", ListaCampos.DB_PK, txtDescProd, false));
		lcProd.add(new GuardaCampo( txtDescProd, "DescProd", "Descrição do produto", ListaCampos.DB_SI, false));
		lcProd.add(new GuardaCampo( txtRefProd,  "RefProd", "Referência do produto", ListaCampos.DB_SI, false));
		lcProd.add(new GuardaCampo( txtCodBarras, "CodBarProd", "Código de barras", ListaCampos.DB_SI, false));
		lcProd.setWhereAdic("ATIVOPROD='S'");
		lcProd.montaSql(false, "PRODUTO", "EQ");
		lcProd.setQueryCommit(false);
		lcProd.setReadOnly(true);
		lcProd.setQueryInsert(false);
		txtCodProd.setTabelaExterna(lcProd);
		
		//  ********************  Master  ************************************
		
		pnMaster.setPreferredSize(new Dimension(500,200));
		setAltCab(90);
		pinCab = new JPanelPad();
		setListaCampos(lcCampos);
		setPainel( pinCab, pnCliCab);
		
		adicCampo(txtCodSim, 7, 20, 80, 20,"CodSim","Cód.Agp.",ListaCampos.DB_PK,null,true);
		adicCampo(txtDescSim, 90, 20, 320, 20,"DescSim","Descrição do agrupamento",ListaCampos.DB_SI,null,true);
		
		setListaCampos( true, "SIMILAR", "EQ");
		lcCampos.setQueryInsert(true);    
		
		//  ********************  Detalhe  ***********************************    
		
		setAltDet(60);
		pinDet = new JPanelPad(440,50);
		setPainel( pinDet, pnDet);
		
		setListaCampos(lcDet);
		setNavegador(navRod);
		 
		adicCampo(txtCodProd,7,20,80,20,"CodProd","Cód.prod",ListaCampos.DB_PF,txtDescProd,true);
		adicDescFK(txtDescProd,90,20,320,20,"Descprod","Descrição do produto");
		
		setListaCampos( false, "ITSIMILAR", "EQ");
		
		lcDet.setQueryInsert(false);    
		lcDet.setQueryCommit(false);
		    
		montaTab();
		tab.setTamColuna(80,0);
		tab.setTamColuna(320,1);
		lcCampos.addCarregaListener(this);
		lcCampos.addInsertListener(this);
		btImp.addActionListener(this);
		btPrevimp.addActionListener(this);
	}
	  
	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == btPrevimp)
			imprimir(true);
		else if (evt.getSource() == btImp) 
			imprimir(false);
		super.actionPerformed(evt);
	}
	  
	private void imprimir(boolean bVisualizar) { }
	  
	public void beforeInsert(InsertEvent ievt) { }  
	  
	public void beforePost(PostEvent pevt) {  }
	  
	public void beforeCarrega(CarregaEvent cevt) {  }
	  
	public void afterCarrega(CarregaEvent cevt) {  }
	  
	public void afterInsert(InsertEvent ievt) {  }
	  
	public void setConexao(Connection cn) {
		super.setConexao(cn);
		lcProd.setConexao(cn);      
	}
}
