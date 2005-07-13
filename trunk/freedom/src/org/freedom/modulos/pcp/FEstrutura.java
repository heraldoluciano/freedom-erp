/**
 * @version 01/09/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.pcp <BR>
 * Classe: @(#)FEstrutura.java <BR>
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
 * Tela para cadastro de estruturas de produtos.
 * 
 */ 

package org.freedom.modulos.pcp;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JScrollPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.acao.PostEvent;
import org.freedom.acao.PostListener;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JTabbedPanePad;
import org.freedom.componentes.JTextAreaPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Tabela;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FDetalhe;

public class FEstrutura extends FDetalhe implements ChangeListener,ActionListener, CarregaListener, PostListener{
  private int casasDec = Aplicativo.casasDec;
  private JPanelPad pinCab = new JPanelPad();
  private JPanelPad pinDetFases = new JPanelPad();
  private JPanelPad pinDetItens = new JPanelPad();
  private JPanelPad pinDetDistrib = new JPanelPad();
  private JTabbedPanePad tpnAbas = new JTabbedPanePad();  
  private JPanelPad pinAbaFases = new JPanelPad();
  private JPanelPad pinAbaItens = new JPanelPad();
  private JPanelPad pinAbaDistrib = new JPanelPad();
  private JTextFieldPad txtCodProdEst = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldFK txtDescProdEst = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldFK txtDescEstDistrib = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldFK txtCLoteProd = new JTextFieldFK(JTextFieldPad.TP_STRING,1,0);
  private JTextFieldPad txtCodFase = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldFK txtDescFase = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldPad txtDescEst = new JTextFieldPad(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldPad txtQtdEst = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtSeqItem = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtSeqDistrib = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtCodProdItem = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldFK txtDescProdItem = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);  
  private JTextFieldPad txtCodProdDistrib = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtQtdMat = new JTextFieldPad(JTextFieldPad.TP_NUMERIC,15,casasDec);
  private JTextFieldPad txtRMA = new JTextFieldPad(JTextFieldPad.TP_STRING,1,0);
  private JTextFieldPad txtRefProdEst = new JTextFieldPad(JTextFieldPad.TP_STRING,13,0);
  private JTextFieldPad txtRefProdDistrib = new JTextFieldPad(JTextFieldPad.TP_STRING,13,0);
  private JTextFieldPad txtCodModLote = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtNroDiasValid = new JTextFieldPad(JTextFieldPad.TP_INTEGER,5,0);
  private JTextFieldPad txtSeqEst = new JTextFieldPad(JTextFieldPad.TP_INTEGER,5,0);
  private JTextFieldPad txtSeqEstDistrib = new JTextFieldPad(JTextFieldPad.TP_INTEGER,5,0);
  private JTextFieldFK txtDescModLote = new JTextFieldFK(JTextFieldPad.TP_STRING,30,0);
  private JTextFieldPad txtSeqEfEst = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtSeqEfItem = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtSeqEfDistrib = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtCodTpRec = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldFK txtDescTpRec = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldPad txtTempoEf = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JCheckBoxPad cbFinaliza = new JCheckBoxPad("Sim","S","N");
  private JCheckBoxPad cbRmaAutoItEst = new JCheckBoxPad("Sim","S","N");
  private JCheckBoxPad cbAtiva = new JCheckBoxPad("Sim","S","N");
  private ListaCampos lcProdEst = new ListaCampos(this,"");
  private ListaCampos lcProdItem = new ListaCampos(this,"PD");
  private ListaCampos lcFase = new ListaCampos(this,"FS");
  private ListaCampos lcModLote = new ListaCampos(this,"ML");
  private ListaCampos lcDetItens = new ListaCampos(this);
  private ListaCampos lcDetDistrib = new ListaCampos(this);
  private ListaCampos lcEstDistrib = new ListaCampos(this,"DE");
  public  Tabela tabItens = new Tabela();
  public  Tabela tabDist = new Tabela(); 
  public  JScrollPane spItens = new JScrollPane(tabItens);  
  public  JScrollPane spDist = new JScrollPane(tabDist);
  private JTextAreaPad txaModoPreparo = new JTextAreaPad();
  private JScrollPane spnModoPreparo = new JScrollPane(txaModoPreparo);
  private ListaCampos lcTipoRec = new ListaCampos(this,"TR");
  
  public FEstrutura() { 
    setTitulo("Estrutura de produtos");
    setAtribos( 50, 20, 622, 550);
    setAltCab(170);
    
  	pnMaster.remove(spTab);
  	pnMaster.remove(pnDet);
  	
  	tpnAbas.addTab("Fases",spTab);
  	tpnAbas.addTab("Itens X Fase",spItens);
  	tpnAbas.addTab("Distribuição X Fase",spDist);
  	
  	pnMaster.add(tpnAbas, BorderLayout.CENTER);
    
    pnGImp.removeAll();
	pnGImp.setLayout(new GridLayout(1, 2));
	pnGImp.setPreferredSize(new Dimension(100, 26));
	pnGImp.add(btPrevimp);
	pnGImp.add(btImp);
    
    cbAtiva.setVlrString("N");

    lcDetItens.setMaster(lcDet);    
    lcDet.adicDetalhe(lcDetItens);
    lcDetDistrib.setMaster(lcDet);
    lcDet.adicDetalhe(lcDetDistrib);
         
    pinCab = new JPanelPad(500,90);
    setListaCampos(lcCampos);
    setPainel( pinCab, pnCliCab);
    lcCampos.addPostListener(this);
    lcProdEst.setUsaME(false);    
    lcProdEst.add(new GuardaCampo( txtCodProdEst, "CodProd", "Cód.prod.", ListaCampos.DB_PK, true));
    lcProdEst.add(new GuardaCampo( txtDescProdEst, "DescProd", "Descrição do produto", ListaCampos.DB_SI, false));
    lcProdEst.add(new GuardaCampo( txtRefProdEst, "RefProd", "Referencia", ListaCampos.DB_SI,false));
    lcProdEst.add(new GuardaCampo( txtCLoteProd, "CLoteProd", "Usa Lote", ListaCampos.DB_SI,false));
    lcProdEst.setWhereAdic("TIPOPROD='F'");
    lcProdEst.montaSql(false, "PRODUTO", "EQ");
    lcProdEst.setQueryCommit(false);
    lcProdEst.setReadOnly(true);
    txtRefProdEst.setTabelaExterna(lcProdEst);
    txtCodProdEst.setTabelaExterna(lcProdEst);
    txtDescProdEst.setListaCampos(lcProdEst);
       
    lcProdItem.add(new GuardaCampo( txtCodProdItem, "CodProd", "Cód.prod.", ListaCampos.DB_PK, true));
    lcProdItem.add(new GuardaCampo( txtDescProdItem, "DescProd", "Descrição do produto", ListaCampos.DB_SI, false));
    lcProdItem.add(new GuardaCampo( txtRMA, "RMAProd", "RMA", ListaCampos.DB_SI, false));

    lcProdItem.montaSql(false, "PRODUTO", "EQ");
    lcProdItem.setQueryCommit(false);
    lcProdItem.setReadOnly(true);
    txtCodProdItem.setTabelaExterna(lcProdItem);
    txtDescProdItem.setListaCampos(lcProdItem);

    lcFase.add(new GuardaCampo( txtCodFase, "CodFase", "Cód.fase", ListaCampos.DB_PK, true));
    lcFase.add(new GuardaCampo( txtDescFase, "DescFase", "Descrição da fase", ListaCampos.DB_SI, false));
    lcFase.montaSql(false, "FASE", "PP");
    lcFase.setQueryCommit(false);
    lcFase.setReadOnly(true);
    txtCodFase.setTabelaExterna(lcFase);
  	txtCodFase.setNomeCampo("codfase");
    txtDescFase.setListaCampos(lcFase);

    lcModLote.add(new GuardaCampo( txtCodModLote, "CodModLote", "Cód.Mod.Lote", ListaCampos.DB_PK, false));
    lcModLote.add(new GuardaCampo( txtDescModLote, "DescModLote", "Descrição do modelo de lote", ListaCampos.DB_SI, false));
    lcModLote.montaSql(false, "MODLOTE", "EQ");
    lcModLote.setQueryCommit(false);
    lcModLote.setReadOnly(true);
    txtCodModLote.setTabelaExterna(lcModLote);
    txtDescModLote.setListaCampos(lcModLote);

    adicCampo(txtCodProdEst, 7, 20, 80, 20,"CodProd","Cód.prod.", ListaCampos.DB_PF, txtDescProdEst, true);
    adicDescFK(txtDescProdEst, 90, 20, 372, 20, "DescProd", "Descrição do produto");
    adicCampo(txtSeqEst,465,20,78,20, "SeqEst", "Seq.Est.",ListaCampos.DB_PF,true);
    adicCampo(txtQtdEst, 7, 60, 80, 20,"QtdEst","Quantidade", ListaCampos.DB_SI, true);
    adicCampo(txtDescEst, 90, 60, 297, 20,"DescEst","Descrição", ListaCampos.DB_SI, true);
    adicCampoInvisivel(txtRefProdEst, "RefProd", "Ref.prod.", ListaCampos.DB_SI, false);
    adicDB(cbAtiva,392,60,50,20,"ATIVOEST","Ativa",true);
    adicCampo(txtCodModLote,7,100,80,20,"CodModLote","Cód.Mod.Lote",ListaCampos.DB_FK,txtDescModLote,false);
    adicDescFK(txtDescModLote, 90, 100, 297, 20, "DescModLote", "Descrição do modelo do lote");
    adicCampo(txtNroDiasValid,390,100,100,20,"NroDiasValid","Dias de validade",ListaCampos.DB_SI,false);
    
    setListaCampos( false, "ESTRUTURA", "PP");
    lcCampos.setQueryInsert(false);
      
    //Detalhe Fases
    
    lcTipoRec.add(new GuardaCampo(txtCodTpRec,"CodTpRec", "Cód.tp.rec.", ListaCampos.DB_PK, true));
    lcTipoRec.add(new GuardaCampo(txtDescTpRec, "DescTpRec", "Descrição do tipo de recurso", ListaCampos.DB_SI, false));
    lcTipoRec.montaSql(false, "TIPOREC", "PP");
    lcTipoRec.setQueryCommit(false);
    lcTipoRec.setReadOnly(true);
    txtCodTpRec.setTabelaExterna(lcTipoRec);

    setPainel( pinDetFases);
    setListaCampos(lcDet);

    adicCampo(txtSeqEfEst, 7, 20, 40, 20,"SeqEf","Item", ListaCampos.DB_PK, true);
    adicCampo(txtCodFase, 50, 20, 77, 20,"CodFase","Cód.fase", ListaCampos.DB_PF, txtDescFase, true);
    adicDescFK(txtDescFase, 130, 20, 277, 20, "DescFase", "Descrição da fase");
    adicCampo(txtTempoEf, 410, 20, 100, 20,"TempoEf","Tempo(Seg)",ListaCampos.DB_SI,true);
    adicCampo(txtCodTpRec, 7, 60, 80, 20,"CodTpRec","Cód.tp.rec.", ListaCampos.DB_FK, txtDescTpRec, true);
    adicDescFK(txtDescTpRec, 90, 60, 350, 20, "DescTpRec", "Desc. tipo de recurso");
    adicDB(cbFinaliza,445,60,80,20,"FINALIZAOP","Finaliza",true);

    adicDBLiv(txaModoPreparo, "Instrucoes", "Instruções", false);
	adic(new JLabelPad("Instruções"), 7, 80, 100, 20);
	adic(spnModoPreparo, 7, 100, 510, 70);
    
    setListaCampos( true, "ESTRUFASE", "PP");
    lcDet.setQueryInsert(false);

    //Fim do detalhe fases
    
    //Detalhe Itens

    pinDetItens = new JPanelPad(590,110);
    setPainel(pinDetItens);
    setListaCampos(lcDetItens);
    setNavegador(navRod);
    
    cbRmaAutoItEst.setVlrString("N");

    adicCampo(txtSeqItem, 7, 20, 40, 20,"SeqItEst","Item", ListaCampos.DB_PK, true);
    adicCampo(txtCodProdItem, 50, 20, 77, 20,"CodProdPD","Cód.prod.", ListaCampos.DB_FK, txtDescProdItem, true);
    adicDescFK(txtDescProdItem, 130, 20, 327, 20, "DescProd", "Descrição do produto");
    adicCampo(txtQtdMat, 460, 20, 60, 20,"QtdItEst","Qtd.", ListaCampos.DB_SI, true);
    adicDB(cbRmaAutoItEst,460,60,120,20,"RmaAutoItEst", "RMA", true);
    setListaCampos( true, "ITESTRUTURA", "PP");
    lcDetItens.setQueryInsert(false);    
    txtCodProdItem.setNomeCampo("CodProd");
    lcDetItens.setTabela(tabItens); 
    
    //Fim Detalhe Itens

    //Detalhe Distribuição
    
  	lcEstDistrib.add(new GuardaCampo( txtCodProdDistrib, "Codprod", "Cód.prod.", ListaCampos.DB_PK, txtDescEstDistrib, true));
  	lcEstDistrib.add(new GuardaCampo( txtSeqEstDistrib,"seqest","Seq.Est.",ListaCampos.DB_PK,true));
  	lcEstDistrib.add(new GuardaCampo( txtDescEstDistrib, "DescEst", "Descrição da estrutura", ListaCampos.DB_SI, false));
       
    lcEstDistrib.setWhereAdic("ATIVOEST='S'");    						   
  	lcEstDistrib.montaSql(false, "ESTRUTURA", "PP");    
  	lcEstDistrib.setQueryCommit(false);
  	lcEstDistrib.setReadOnly(true);
  	txtCodProdDistrib.setTabelaExterna(lcEstDistrib);
  	txtSeqEstDistrib.setTabelaExterna(lcEstDistrib);
  	txtCodProdDistrib.setNomeCampo("codprod");
  	txtSeqEstDistrib.setNomeCampo("seqest");      
    
    setPainel( pinDetDistrib);
    setListaCampos(lcDetDistrib);

    adicCampo(txtSeqDistrib,7,20,60,20,"seqde","Seq.",ListaCampos.DB_PK,true);
    adicCampo(txtCodProdDistrib, 70, 20, 77, 20,"CodProdDe","Cód.prod.",ListaCampos.DB_PF, true);
    adicCampo(txtSeqEstDistrib, 150, 20, 77, 20,"SeqEstDe","Seq.Est", ListaCampos.DB_PF,txtDescEstDistrib, true);
    adicDescFK(txtDescEstDistrib, 230, 20, 277, 20, "DescEst", "Descrição da estrutura");
    txtCodProdDistrib.setNomeCampo("CodProdDe");
    
    setListaCampos( true, "DISTRIB", "PP");
    lcDetDistrib.setQueryInsert(false);

    lcDetDistrib.setTabela(tabDist);
    lcDetDistrib.montaTab();
        
    //Fim Detalhe Distribuição
    
    setPainel( pinDetFases, pnDet);
	lcDet.montaTab();
	lcDetItens.montaTab();
    
    btImp.addActionListener(this);
	btPrevimp.addActionListener(this);
	
	setImprimir(true);
	tpnAbas.addChangeListener(this);
    
	lcCampos.addCarregaListener(this);
    lcDet.addCarregaListener(this);
    lcProdEst.addCarregaListener(this);
    lcProdItem.addCarregaListener(this);
    lcFase.addCarregaListener(this);

    tabItens.setTamColuna(45,0);
    tabItens.setTamColuna(60,1);
    tabItens.setTamColuna(200,2);
    tabItens.setTamColuna(0,5);
    tabItens.setTamColuna(150,7);
    tabItens.setTamColuna(30,8);

    tab.setTamColuna(45,0);
    tab.setTamColuna(60,1);
    tab.setTamColuna(200,2);
    tab.setTamColuna(150,5);
    
    cbRmaAutoItEst.setEnabled(false);
	setAltDet(190);
	navRod.setListaCampos(lcDet);
	lcDet.setNavegador(navRod); 
	nav.setListaCampos(lcCampos);
	lcCampos.setNavegador(nav);
	
  }
  private void imprimir(boolean bVisualizar) {
    ImprimeOS imp = new ImprimeOS("",con);
    int linPag = imp.verifLinPag()-1;
    int iTot = 0;
    String[] sValores;
    String sWhere = "";
    imp.montaCab();
    imp.setTitulo("Relatório de Estrutura do Produto");
   
    DLREstrutura dl = new DLREstrutura();
    dl.setVisible(true);
    if (dl.OK == false) {
      dl.dispose();
      return;
    }
    sValores = dl.getValores();
	dl.dispose();
    
	if (sValores[2].equals("A")) {
		sWhere += " AND E.CODPROD="+txtCodProdEst.getVlrString();
		sWhere += " AND E.SEQEST="+txtSeqEst.getVlrString();
	}
    
    if (sValores[1].equals("R")){
    	
    	String sSQL = "SELECT E.CODPROD, PD.DESCPROD, E.SEQEST, E.QTDEST, E.DESCEST, E.ATIVOEST, E.CODMODLOTE, E.NRODIASVALID" +
					  " FROM PPESTRUTURA E, EQPRODUTO PD " +
					  " WHERE PD.CODEMP=E.CODEMP AND PD.CODFILIAL=E.CODFILIAL AND E.CODPROD=PD.CODPROD " +
					    sWhere+
					  " ORDER BY "+sValores[0];

		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
		
		ps = con.prepareStatement(sSQL);
		rs = ps.executeQuery();
		  
		imp.limpaPags();
		
		while ( rs.next() ) {
		   if (imp.pRow()==0) {
		      imp.impCab(136, true);
		      imp.say(imp.pRow()+0,0,""+imp.comprimido());
		      imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",133)+"|");
		      imp.say(imp.pRow()+1,0,"| Cód.prod.");
		      imp.say(imp.pRow()+0,13,"| Descrição do produto");
		      imp.say(imp.pRow()+0,50,"| Seq.Est.");
		      imp.say(imp.pRow()+0,60,"| Qtd.");
		      imp.say(imp.pRow()+0,70,"| Descrição");
		      imp.say(imp.pRow()+0,101,"| Ativo");
		      imp.say(imp.pRow()+0,110,"| Mod.Lote");
		      imp.say(imp.pRow()+0,121,"| Valid");
		      imp.say(imp.pRow()+0,135,"|");
		   }
		   	  
		   	  imp.say(imp.pRow()+1,0,""+imp.comprimido());
		      imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",133)+"|");
		   	  imp.say(imp.pRow()+1,0,""+imp.comprimido());
		      imp.say(imp.pRow()+0,0,"| " + rs.getString("CodProd"));
		      imp.say(imp.pRow()+0,13,"| " + rs.getString("DescProd").substring(0,34));
		      imp.say(imp.pRow()+0,50,"| " + rs.getString("SeqEst"));
		      imp.say(imp.pRow()+0,60,"| " + rs.getString("QtdEst"));
		      imp.say(imp.pRow()+0,70,"| " + rs.getString("DescEst").substring(0,29));
		      imp.say(imp.pRow()+0,100,"| " + (rs.getString("AtivoEst").equals("S") ? "Sim" : "Não"));
		      imp.say(imp.pRow()+0,110,"| " + rs.getString("CodModLote"));
		      imp.say(imp.pRow()+0,121,"| " + rs.getString("NroDiasValid")+" Dias");
		      imp.say(imp.pRow()+0,135,"|");
		      
		   
		   if (imp.pRow()>=linPag) {
			      imp.say(imp.pRow()+1,0,""+imp.comprimido());
			      imp.say(imp.pRow()+0,0,"+"+Funcoes.replicate("=",133)+"+");
			      imp.incPags();
			      imp.eject();
		   }
		}
		imp.say(imp.pRow()+1,0,""+imp.comprimido());
	    imp.say(imp.pRow()+0,0,"+"+Funcoes.replicate("=",133)+"+");
		
	    imp.eject();
		
		imp.fechaGravacao();
		
		if (!con.getAutoCommit())
			con.commit();
		dl.dispose();
		}  
		catch ( SQLException err ) {
		Funcoes.mensagemErro(this,"Erro consulta tabela de estrutura do produto!\n"+err.getMessage(),true,con,err);      
		}
    }
    else if (sValores[1].equals("C")){
    	
    	String sCodProd = "";
    	String sSQL = "SELECT E.CODPROD, PD.DESCPROD, E.SEQEST, E.QTDEST, E.DESCEST, E.ATIVOEST," +
    				  " E.CODMODLOTE, E.NRODIASVALID, IT.SEQITEST, IT.CODPRODPD, PI.DESCPROD, IT.QTDITEST," +
    				  " IT.CODFASE, F.DESCFASE, IT.RMAAUTOITEST" +
					  " FROM PPESTRUTURA E, PPITESTRUTURA IT, EQPRODUTO PD, EQPRODUTO PI, PPFASE F " +
					  " WHERE E.CODPROD=PD.CODPROD AND E.CODEMP=PD.CODEMP AND E.CODFILIAL=PD.CODFILIAL" +
					  " AND IT.CODPROD=E.CODPROD AND IT.SEQEST=E.SEQEST AND IT.CODEMP=E.CODEMP AND IT.CODFILIAL=E.CODFILIAL" +
					  " AND IT.CODPRODPD=PI.CODPROD AND IT.CODEMPPD=PI.CODEMP AND IT.CODFILIALPD=PI.CODFILIAL" +
					  " AND IT.CODFASE=F.CODFASE AND IT.CODEMPFS=F.CODEMP AND IT.CODFILIALFS=F.CODFILIAL" +
					  sWhere+
					  " ORDER BY "+sValores[0]+", IT.CODPROD, IT.CODFASE";
		

		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
		
		ps = con.prepareStatement(sSQL);
		rs = ps.executeQuery();
		
		sCodProd = txtCodProdEst.getVlrString();		
		int cont = 0;
		imp.limpaPags();
		
		while ( rs.next() ) {
		   if (imp.pRow()==0) {
		      imp.impCab(136, true);
		      imp.say(imp.pRow()+ 0,0,""+imp.comprimido());
		      imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate(" ",133)+"|");
		      
		   }
		   if (!sCodProd.equals(rs.getString(1))){
	   		cont=0;
	   		sCodProd = rs.getString(1);
		   }
		   if (sCodProd.equals(rs.getString(1)) && cont==0){
		      imp.say(imp.pRow()+1,0,""+imp.comprimido());
		      imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("=",133)+"|");
		      imp.say(imp.pRow()+1,0,"| Cód.prod.");
		      imp.say(imp.pRow()+0,13,"| Descrição do produto");
		      imp.say(imp.pRow()+0,50,"| Seq.Est.");
		      imp.say(imp.pRow()+0,60,"| Qtd.");
		      imp.say(imp.pRow()+0,70,"| Descrição");
		      imp.say(imp.pRow()+0,101,"| Ativo");
		      imp.say(imp.pRow()+0,110,"| Mod.Lote");
		      imp.say(imp.pRow()+0,121,"| Valid");
		      imp.say(imp.pRow()+0,135,"|");		      
		      imp.say(imp.pRow()+1,0,""+imp.comprimido());
		      imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("=",133)+"|");
		      
		   	  imp.say(imp.pRow()+1,0,""+imp.comprimido());
		      imp.say(imp.pRow()+0,0,"| " + rs.getString(1));
		      imp.say(imp.pRow()+0,13,"| " + rs.getString(2).substring(0,34));
		      imp.say(imp.pRow()+0,50,"| " + rs.getString(3));
		      imp.say(imp.pRow()+0,60,"| " + rs.getString(4));
		      imp.say(imp.pRow()+0,70,"| " + rs.getString(5).substring(0,29));
		      imp.say(imp.pRow()+0,100,"| " + (rs.getString(6).equals("S") ? "Sim" : "Não"));
		      imp.say(imp.pRow()+0,110,"| " + rs.getString(7));
		      imp.say(imp.pRow()+0,121,"| " + rs.getString(8)+" Dias");
		      imp.say(imp.pRow()+0,135,"|");
		      imp.say(imp.pRow()+1,0,""+imp.comprimido());
		      imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",133)+"|");
		      imp.say(imp.pRow()+1,0,"| Item");
		      imp.say(imp.pRow()+0,8,"| Cod.prod");
		      imp.say(imp.pRow()+0,20,"| Descrição do produto");
		      imp.say(imp.pRow()+0,60,"| Qtd.");
		      imp.say(imp.pRow()+0,70,"| Cod.fase");
		      imp.say(imp.pRow()+0,80,"| Descrição da fase");
		      imp.say(imp.pRow()+0,123,"| Auto Rma");
		      imp.say(imp.pRow()+0,135,"|");
		      imp.say(imp.pRow()+1,0,""+imp.comprimido());
		      imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",133)+"|");
		      cont++;
		   }
		   	  
		   	  imp.say(imp.pRow()+1,0,""+imp.comprimido());
		      imp.say(imp.pRow()+0,0,"| " + rs.getString(9));
		      imp.say(imp.pRow()+0,8,"| " + rs.getString(10));
		      imp.say(imp.pRow()+0,20,"| " + rs.getString(11).substring(0,38));
		      imp.say(imp.pRow()+0,60,"| " + rs.getString(12));
		      imp.say(imp.pRow()+0,70,"| " + rs.getString(13));
		      imp.say(imp.pRow()+0,80,"| " + rs.getString(14).substring(0,38));
		      imp.say(imp.pRow()+0,123,"|   " + (rs.getString(15).equals("S") ? "Sim" : "Não"));
		      imp.say(imp.pRow()+0,135,"|");
		   
		  		   
		   if (imp.pRow()>=linPag) {
			      imp.say(imp.pRow()+1,0,""+imp.comprimido());
			      imp.say(imp.pRow()+0,0,"+"+Funcoes.replicate("=",133)+"+");
			      imp.incPags();
			      imp.eject();
		   }
		}
		imp.say(imp.pRow()+1,0,""+imp.comprimido());
	    imp.say(imp.pRow()+0,0,"+"+Funcoes.replicate("=",133)+"+");
	      
		imp.eject();
		
		imp.fechaGravacao();
		
		if (!con.getAutoCommit())
			con.commit();
		dl.dispose();
		}  
		catch ( SQLException err ) {
		Funcoes.mensagemErro(this,"Erro consulta tabela de insumos do produto!\n"+err.getMessage(),true,con,err);      
		}
    }
    
    if (bVisualizar) {
      imp.preview(this);
    }
    else {
      imp.print();
    }
  }

  public void actionPerformed(ActionEvent evt) {
    if (evt.getSource() == btPrevimp) {   	
        imprimir(true);
    }
    else if (evt.getSource() == btImp) 
      imprimir(false);
    super.actionPerformed(evt);
  }
  public void setConexao(Connection cn) {
    super.setConexao(cn);
    lcProdEst.setConexao(cn);
    lcProdItem.setConexao(cn);
    lcModLote.setConexao(cn);
    lcTipoRec.setConexao(cn);
    lcFase.setConexao(cn);
    lcDetDistrib.setConexao(cn);
    lcDetItens.setConexao(cn);
    lcEstDistrib.setConexao(cn);
  }
  public void afterCarrega(CarregaEvent cevt) {  	
  
    if (cevt.getListaCampos() == lcProdItem) {
    	String sRma = txtRMA.getVlrString();
	    if (sRma.equals("S"))
	    	cbRmaAutoItEst.setEnabled(true);
    	else 
    		cbRmaAutoItEst.setEnabled(false);
    }    	
    else if (cevt.getListaCampos() == lcProdEst) {
    	if(txtCLoteProd.getVlrString().equals("S")){
    	    txtCodModLote.setAtivo(true);   
    	    txtNroDiasValid.setAtivo(true);
			
    	} 
    	else {
    	    txtCodModLote.setAtivo(false);
    	    txtNroDiasValid.setAtivo(false);
    	}
    }
  }      
  public void stateChanged(ChangeEvent cevt) {
  	if (((JTabbedPanePad)(cevt.getSource()))==tpnAbas){
  		if(tpnAbas.getSelectedIndex()==0){
  		    setAltDet(190);
  			pnDet.removeAll(); 
  		    setPainel( pinDetFases, pnDet);
  		    setListaCampos(lcDet);
  		    pnDet.repaint();
  		    navRod.setListaCampos(lcDet);  		    
  		}  		
  		else if(tpnAbas.getSelectedIndex()==1){
  		    setAltDet(110);
  			pnDet.removeAll();  			
  		    setPainel( pinDetItens, pnDet);
  		    setListaCampos(lcDetItens);
  		    pnDet.repaint();
  		    navRod.setListaCampos(lcDetItens);
  		}
  		else if(tpnAbas.getSelectedIndex()==2){
  		    setAltDet(110);
  			pnDet.removeAll();  			
  		    setPainel( pinDetDistrib, pnDet);
  		    setListaCampos(lcDetDistrib);
  		    pnDet.repaint();
  		    navRod.setListaCampos(lcDetDistrib);
  		}
    }
  }

  public void beforeCarrega(CarregaEvent cevt) {  }
  public void afterPost(PostEvent pevt) {  }
}
