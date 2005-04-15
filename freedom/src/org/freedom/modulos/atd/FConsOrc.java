/**
 * @version 02/08/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.atd <BR>
 * Classe: @(#)FConsOrc.java <BR>
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
 * Formulário de consulta de orçamento.
 * 
 */

package org.freedom.modulos.atd;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;

import javax.swing.JButton;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JPanelPad;
import javax.swing.JScrollPane;

import org.freedom.bmps.Icone;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Tabela;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FFilho;

public class FConsOrc extends FFilho implements ActionListener {
	private JPanelPad pinCab = new JPanelPad(0,250);
	private JPanelPad pnCli = new JPanelPad(JPanelPad.TP_JPANEL,new BorderLayout());
	private JTextFieldPad txtCodCli = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
	private JTextFieldFK txtNomeCli = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);	
	private JTextFieldPad txtCodEnc = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
	private JTextFieldPad txtNomeEnc = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
	private JTextFieldPad txtCodConv = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
	private JTextFieldFK txtNomeConv = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
	private JTextFieldPad txtDtIni = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
	private JTextFieldPad txtDtFim = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
	private JTextFieldPad txtCid = new JTextFieldPad(JTextFieldPad.TP_STRING,30,0);
	private JLabelPad lbCid = new JLabelPad("Cidade");
	private JCheckBoxPad cbAberto = new JCheckBoxPad("Aberto","S","N");
	private JCheckBoxPad cbCompleto = new JCheckBoxPad("Completo","S","N");
	private JCheckBoxPad cbLiberado = new JCheckBoxPad("Liberado","S","N");
	private JCheckBoxPad cbFaturado = new JCheckBoxPad("Faturado","S","N");
	private JCheckBoxPad cbConveniado = new JCheckBoxPad("Listar conveniados","S","N");		
	
	private JRadioGroup gbVenc;
	//private JRadioGroup gbStatus;
	private Tabela tab = new Tabela();
	private JButton btPrevimp = new JButton ("Imprimir",Icone.novo("btPrevimp.gif"));
	private JButton btBusca = new JButton("Buscar",Icone.novo("btPesquisa.gif"));
	private JScrollPane spnTab = new JScrollPane(tab);
	private ListaCampos lcConv = new ListaCampos(this,"PR");
	private ListaCampos lcCli = new ListaCampos(this,"CL");
	private ListaCampos lcTipoConv = new ListaCampos(this,"AT");
	private ListaCampos lcEnc = new ListaCampos(this,"EC");
	private JTextFieldPad txtCodTpConv = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
	private JTextFieldFK txtDescTipoConv = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
	public FConsOrc() {
		setTitulo("Pesquisa Orçamentos");
		setAtribos(10,10,605,525);
		
		txtDtIni.setRequerido(true);
		txtDtFim.setRequerido(true);

		lcConv.add(new GuardaCampo( txtCodConv, "CodConv", "Cód.conv.", ListaCampos.DB_PK, false));
		lcConv.add(new GuardaCampo( txtNomeConv, "NomeConv", "Nome do conveniado", ListaCampos.DB_SI, false));
		txtCodConv.setTabelaExterna(lcConv);
		txtCodConv.setNomeCampo("CodConv");
		txtCodConv.setFK(true);
		lcConv.setReadOnly(true);
		lcConv.montaSql(false, "CONVENIADO", "AT");

		lcCli.add(new GuardaCampo( txtCodCli, "CodCli", "Cód.cli.", ListaCampos.DB_PK, false));
		lcCli.add(new GuardaCampo( txtNomeCli, "NomeCli", "Razão social do cliente", ListaCampos.DB_SI, false));
		txtCodCli.setTabelaExterna(lcCli);
		txtCodCli.setNomeCampo("CodCli");
		txtCodCli.setFK(true);
		lcCli.setReadOnly(true);
		lcCli.montaSql(false, "CLIENTE", "VD");

		lcTipoConv.add(new GuardaCampo( txtCodTpConv, "CodTpConv", "Cód.tp.conv.", ListaCampos.DB_PK, false));
		lcTipoConv.add(new GuardaCampo( txtDescTipoConv, "DescTpConv", "Descrição do tipo de conveniado", ListaCampos.DB_SI, false));
		txtCodTpConv.setTabelaExterna(lcTipoConv);
		txtCodTpConv.setNomeCampo("CodTpConv");
		txtCodTpConv.setFK(true);
		lcTipoConv.setReadOnly(true);
		lcTipoConv.montaSql(false, "TIPOCONV", "AT");
		
		
		lcEnc.add(new GuardaCampo( txtCodEnc, "CodEnc", "Cód.enc.", ListaCampos.DB_PK, false));
		lcEnc.add(new GuardaCampo( txtNomeEnc, "NomeEnc", "Descrição do encaminhador", ListaCampos.DB_SI, false));
		txtCodEnc.setTabelaExterna(lcEnc);
		txtCodEnc.setNomeCampo("CodEnc");
		txtCodEnc.setFK(true);
		lcEnc.setReadOnly(true);
		lcEnc.montaSql(false, "ENCAMINHADOR", "AT");
		
		

//		txtDescTipoConv.setListaCampos(lcTipoConv);
//		lcTipoConv.setQueryCommit(false);



		getTela().add(pnCli,BorderLayout.CENTER);
		pnCli.add(pinCab,BorderLayout.NORTH);
		pnCli.add(spnTab,BorderLayout.CENTER);
		adicBotaoSair();
		
		Vector vVals = new Vector();
		vVals.addElement("D");
		vVals.addElement("V");
		Vector vLabs = new Vector();
		vLabs.addElement("Data de emissão");
		vLabs.addElement("Data de validade");
		gbVenc = new JRadioGroup(2,1,vLabs,vVals);
		
		pinCab.adic(new JLabelPad("Período:"),380,0,90,20);
		pinCab.adic(txtDtIni,380,20,87,20);
		pinCab.adic(new JLabelPad("Até"),470,20,27,20);
		pinCab.adic(txtDtFim,494,20,87,20);
		
		pinCab.adic(new JLabelPad("Cód.cli."),7,0,280,20);
		pinCab.adic(txtCodCli,7,20,70,20);
		pinCab.adic(new JLabelPad("Razão social do cliente"),80,0,280,20);
		pinCab.adic(txtNomeCli,80,20,294,20);

		pinCab.adic(new JLabelPad("Cód.conv."),7,40,280,20);
		pinCab.adic(txtCodConv,7,60,70,20);
		pinCab.adic(new JLabelPad("Nome do conveniado"),80,40,280,20);
		pinCab.adic(txtNomeConv,80,60,294,20);
		
		pinCab.adic(lbCid,380,40,60,20);
		pinCab.adic(txtCid,380,60,200,20);
		pinCab.adic(cbConveniado,390,99,180,20);
		
		pinCab.adic(new JLabelPad("Cód.tp.conv."),7,80,250,20);
		pinCab.adic(txtCodTpConv,7,100,70,20);
		pinCab.adic(new JLabelPad("Descrição do tipo de conveniado"),80,80,250,20);
		pinCab.adic(txtDescTipoConv,80,100,294,20);
		
		pinCab.adic(new JLabelPad("Cód.enc."),7,122,250,20);
		//pinCab.adic(lbEnc,7,135,60,20);
		pinCab.adic(txtCodEnc,7,142,70,20);
		pinCab.adic(new JLabelPad("Descrição do Encaminhador"),80,122,250,20);
		pinCab.adic(txtNomeEnc,80,142,294,20);
				
		pinCab.adic(new JLabelPad("Status:"),7,170,90,20);
		pinCab.adic(cbAberto,7,191,80,20);
		pinCab.adic(cbCompleto,7,211,80,20);
		pinCab.adic(cbLiberado,120,191,80,20);
		pinCab.adic(cbFaturado,120,211,80,20);		
		
		pinCab.adic(new JLabelPad("Filtrar por:"),210,170,110,20);
		pinCab.adic(gbVenc,210,189,200,49);
			
	//	pinCab.adic(btBusca,387,122,100,30);

		pinCab.adic(btBusca,470,167,120,30);

		pinCab.adic(btPrevimp,470,210,120,30);
		
		cbAberto.setVlrString("S");
		
		txtDtIni.setVlrDate(new Date());
		txtDtFim.setVlrDate(new Date());

		tab.adicColuna("Status");
		tab.adicColuna("Orc.");
		tab.adicColuna("Cód.cli.");
		tab.adicColuna("Razão social do cliente/conveniado");
		tab.adicColuna("Tipo de conveniado");
		tab.adicColuna("Encaminhador");
		tab.adicColuna("Data");
		tab.adicColuna("Validade");
		tab.adicColuna("Cidade");
		tab.adicColuna("Telefone");
		

		tab.setTamColuna(50,0);
		tab.setTamColuna(50,1);
		tab.setTamColuna(60,2);
		tab.setTamColuna(220,3);
		tab.setTamColuna(150,4);
		tab.setTamColuna(150,5);
		tab.setTamColuna(70,6);
		tab.setTamColuna(70,7);
		tab.setTamColuna(90,8);
		tab.setTamColuna(100,9);
 
		btBusca.addActionListener(this);
		btPrevimp.addActionListener(this);
		tab.addMouseListener(
		  new MouseAdapter() {
		  	public void mouseClicked(MouseEvent mevt) {
				if (mevt.getSource()==tab && mevt.getClickCount()==2)
				  abreOrc();
		  	}
		  }
		);
	}
	/**
	 * 
	 * Carrega os valores para a tabela de consulta.
	 * Este método é executado após carregar o ListaCampos da tabela.
	 *
	 */ 
	
	
	private void carregaTabela() {
		String sWhere = "";
		boolean bUsaOr = false;
		boolean bUsaWhere = false;
		boolean bConv = (txtCodConv.getVlrInteger().intValue() > 0);
		boolean bCli = (txtCodCli.getVlrInteger().intValue() > 0);
        boolean bEnc = (txtCodEnc.getVlrInteger().intValue()> 0);         
		
		
        if (cbAberto.getVlrString().equals("S")){	
			bUsaWhere = true;        
        	sWhere = " STATUSORC ='OA'"; 
        }
        if (cbCompleto.getVlrString().equals("S")) {
            if (sWhere.trim().equals("")){
		      sWhere = " STATUSORC ='OC'";
			}
		    else {
				sWhere = sWhere+" OR STATUSORC ='OC'";
				bUsaOr = true;
		    }
			bUsaWhere = true;
        }    		                  
		if (cbLiberado.getVlrString().equals("S")){
			if (sWhere.trim().equals("")){
			  sWhere = " STATUSORC ='OL'";
			}  
			else {
				sWhere = sWhere+" OR STATUSORC ='OL'";
				bUsaOr = true;
				bUsaWhere = true;
			}
			bUsaWhere = true;
		}		                  
		if (cbFaturado.getVlrString().equals("S")){
			if (sWhere.trim().equals(""))
			  sWhere = " STATUSORC ='OV'";
			else {
				sWhere = sWhere+" OR STATUSORC ='OV'";
				bUsaOr = true;
			}
			bUsaWhere = true;
		}                  
 
		if (bUsaWhere && bUsaOr)
		  sWhere = " AND ("+sWhere+")";
		else if (bUsaWhere)
		  sWhere = " AND "+sWhere;
		else sWhere =" AND STATUSORC=''";  		  
		
		if (gbVenc.getVlrString().equals("V"))
		  sWhere += " AND DTVENCORC BETWEEN ? AND ?";
		else
		  sWhere += " AND DTORC BETWEEN ? AND ?"; 
		  
		if (bConv)
		  sWhere += " AND O.CODCONV=? AND O.CODEMPCV=O.CODEMP AND O.CODFILIALCV=O.CODFILIAL ";
		  
		if (bCli)  
		  sWhere += " AND O.CODCLI="+txtCodCli.getVlrString()+" AND O.CODEMPCV=O.CODEMP AND CODFILIALCV=O.CODFILIAL ";	
		
		if (bEnc)
		  sWhere += " AND O.CODCONV=C.CODCONV AND O.CODEMPCV=C.CODEMP AND O.CODFILIALCV=C.CODFILIAL " +
			  		"AND C.CODENC="+txtCodEnc.getVlrString()+" AND C.CODEMPEC=O.CODEMP AND C.CODFILIALEC="+lcEnc.getCodFilial()+"";
     		
		
		
		if (!txtCid.getVlrString().equals("")) {
			sWhere += " AND C.CIDCONV  = '"+txtCid.getVlrString()+"'";
			
		}
		if (!txtCodTpConv.getVlrString().trim().equals("")){
		  sWhere += "AND EXISTS(SELECT T2.CODTPCONV FROM ATTIPOCONV T2, ATCONVENIADO C2 WHERE " +
		  	"T2.CODEMP=C2.CODEMPTC AND T2.CODFILIAL=C2.CODFILIALTC AND T2.CODTPCONV=C2.CODTPCONV AND "+
		  	"C2.CODEMP=O.CODEMPCV AND C2.CODFILIAL=O.CODFILIALCV AND C2.CODCONV=O.CODCONV AND " +
		  	"T2.CODEMP="+Aplicativo.iCodEmp+" AND T2.CODFILIAL="+ListaCampos.getMasterFilial("ATTIPOCONV")+" AND " +
		  	"T2.CODTPCONV="+txtCodTpConv.getVlrString().trim()+" ) ";

		}  
		/*String sSQL = "SELECT O.STATUSORC,O.CODORC,O.DTORC,O.DTVENCORC,O.CODCONV,O.CODCLI,CL.NOMECLI,"+
					  "(SELECT C.NOMECONV FROM ATCONVENIADO C WHERE C.CODCONV=O.CODCONV AND C.CODEMP=O.CODEMPCV AND C.CODFILIAL=O.CODFILIALCV), "+
					  "(SELECT T.DESCTPCONV FROM ATTIPOCONV T,ATCONVENIADO C WHERE C.CODCONV=O.CODCONV AND C.CODEMP=O.CODEMPCV"+
					  " AND C.CODFILIAL=O.CODFILIALCV AND T.CODTPCONV=C.CODTPCONV AND T.CODEMP=C.CODEMPTC AND T.CODFILIAL=C.CODFILIALTC) "+					  
					  "FROM VDORCAMENTO O,VDCLIENTE CL "+
					  " WHERE O.CODEMP=? AND O.CODFILIAL=? AND O.TIPOORC='O' AND CL.CODEMP=O.CODEMPCL AND CL.CODFILIAL=O.CODFILIALCL AND CL.CODCLI=O.CODCLI "+sWhere+
					  " ORDER BY O.DTORC,O.CODCONV";
		*/
		 
		
		 String sSQL = "SELECT O.STATUSORC, O.CODORC,O.DTORC,O.DTVENCORC,"+
		 	            "O.CODCONV,C.NOMECONV,O.CODCLI,CL.NOMECLI,C.FONECONV ,T.DESCTPCONV, IT.VENCAUTORIZORC,IT.NUMAUTORIZORC,"+
		                "IT.CODPROD, P.CODBARPROD,P.DESCPROD,C.CIDCONV, CL.CIDCLI,"+
                        "(SELECT EC.NOMEENC FROM ATENCAMINHADOR EC WHERE EC.CODENC=C.CODENC AND "+
                        "EC.CODEMP=C.CODEMPEC AND EC.CODFILIAL=C.CODFILIALEC) "+
		                "FROM VDORCAMENTO O,VDCLIENTE CL,"+
		                "ATCONVENIADO C, EQPRODUTO P, VDITORCAMENTO IT,ATTIPOCONV T WHERE O.CODEMP=? "+
		                "AND O.CODFILIAL=? AND O.TIPOORC='O' "+
		                "AND IT.CODORC=O.CODORC AND IT.CODEMP=O.CODEMP AND IT.CODFILIAL=O.CODFILIAL " +
	      	            "AND IT.TIPOORC=O.TIPOORC "+ 
	      	            "AND T.CODTPCONV=C.CODTPCONV AND T.CODEMP=C.CODEMPTC AND T.CODFILIAL=C.CODFILIALTC "+
						"AND CL.CODEMP=O.CODEMPCL AND CL.CODFILIAL=O.CODFILIALCL "+
		                "AND CL.CODCLI=O.CODCLI  AND C.CODEMP=O.CODEMPCV AND C.CODFILIAL=O.CODFILIALCV "+
		                "AND O.CODCONV=C.CODCONV "+					
		                "AND P.CODPROD=IT.CODPROD AND P.CODEMP=IT.CODEMPPD AND " +
		                "P.CODFILIAL=IT.CODFILIALPD "+sWhere;
		
		System.out.println(sSQL);
		try {
			PreparedStatement ps = con.prepareStatement(sSQL);
			ps.setInt(1,Aplicativo.iCodEmp);
			ps.setInt(2,ListaCampos.getMasterFilial("VDORCAMENTO"));
		    ps.setDate(3,Funcoes.dateToSQLDate(txtDtIni.getVlrDate()));
			ps.setDate(4,Funcoes.dateToSQLDate(txtDtFim.getVlrDate()));
			
			if (bConv) {
			  ps.setInt(5,txtCodConv.getVlrInteger().intValue());
			}
			
			
			ResultSet rs = ps.executeQuery();
			int iLin = 0;
            tab.limpa();
			while (rs.next()) {
				tab.adicLinha();
				tab.setValor(rs.getString(1),iLin,0);
				tab.setValor(new Integer(rs.getInt(2)),iLin,1);
				
				if (cbConveniado.getVlrString().equals("S")) {
				  tab.setValor(new Integer(rs.getInt(5)),iLin,2);
				  tab.setValor(rs.getString(6),iLin,3);
				  tab.setValor(rs.getString(16),iLin,8);  
				}
				else {
					tab.setValor(new Integer(rs.getInt(7)),iLin,2);
					tab.setValor(rs.getString(8),iLin,3);
					tab.setValor(rs.getString(17),iLin,8); 
					
				}
				tab.setValor(rs.getString(10),iLin,4);
							
				tab.setValor(Funcoes.sqlDateToStrDate(rs.getDate("DtOrc")),iLin,6);
				tab.setValor(Funcoes.sqlDateToStrDate(rs.getDate("DtVencOrc")),iLin,7);
				tab.setValor(rs.getString(9)!= null ? rs.getString(9) : "",iLin,9);
				tab.setValor(rs.getString(18)!=null ? rs.getString(18): "",iLin,5);
				
				iLin++;
			}
//			rs.close();
//			ps.close();
			if (!con.getAutoCommit())
				con.commit();
		}
		catch (SQLException err) {
			Funcoes.mensagemErro(this,"Erro ao carregar a tabela VDORÇAMENTO!\n"+err.getMessage());
		}
	
	}
	private void imprimir(boolean bVisualizar) {
		ImprimeOS imp = new ImprimeOS("",con);
		int linPag = imp.verifLinPag()-1;
		imp.montaCab();
		imp.setTitulo("Relatório de Orçamentos");
		
		try {
			imp.limpaPags();
			for (int iLin=0;iLin<tab.getNumLinhas();iLin++) {
				if (imp.pRow()==0) {
					imp.impCab(136, false);
					imp.say(imp.pRow()+0,0,""+imp.comprimido());
					imp.say(imp.pRow()+0,1,"| N.ORC.");
					imp.say(imp.pRow()+0,14," | Emissão");
					imp.say(imp.pRow()+0,27,"  | Validade.");
					imp.say(imp.pRow()+0,40," | Tipo conv.");
					imp.say(imp.pRow()+0,54,"  | Nome");
				    imp.say(imp.pRow()+0,86," |Encaminhador");
					imp.say(imp.pRow()+0,101,"  | Cidade");
					imp.say(imp.pRow()+0,120,"   | Telefone    |");
					
					imp.say(imp.pRow()+1,0,""+imp.comprimido());
					imp.say(imp.pRow()+0,0,Funcoes.replicate("-",136));
				}
				imp.say(imp.pRow()+1,0,"|"+imp.comprimido());
				imp.say(imp.pRow()+0,2,""+tab.getValor(iLin,1));
				imp.say(imp.pRow()+0,15,"|"+tab.getValor(iLin,6));
				imp.say(imp.pRow()+0,29,"|"+tab.getValor(iLin,7));
				imp.say(imp.pRow()+0,41,"|"+Funcoes.copy(tab.getValor(iLin,4)+"",13));
				
				imp.say(imp.pRow()+0,56,"|"+Funcoes.copy(tab.getValor(iLin,3)+"",25));

				imp.say(imp.pRow()+0,87,"|"+Funcoes.copy(tab.getValor(iLin,5)+"",15));
				
				imp.say(imp.pRow()+0,103,"|"+Funcoes.copy(tab.getValor(iLin,8)+"",18));
				imp.say(imp.pRow()+0,123,"|"+Funcoes.copy(tab.getValor(iLin,9)+"",12)+" |");
				
				if (imp.pRow()>=linPag) {
					imp.incPags();
					imp.eject();
				}
			}
			
			imp.say(imp.pRow()+1,0,""+imp.comprimido());
			imp.say(imp.pRow()+0,0,Funcoes.replicate("=",136));
			imp.eject();
			
			imp.fechaGravacao();
			
//      rs.close();
//      ps.close();
			if (!con.getAutoCommit())
				con.commit();
			
		}  
		catch ( SQLException err ) {
			Funcoes.mensagemErro(this,"Erro consulta tabela de orçamentos!"+err.getMessage());      
		}
		
		if (bVisualizar) {
			imp.preview(this);
		}
		else {
			imp.print();
		}
	}
	private void abreOrc() {
		int iCodOrc = ((Integer)tab.getValor(tab.getLinhaSel(),1)).intValue();
		if (fPrim.temTela("Orcamento")==false) {
		  FOrcamento tela = new FOrcamento();
		  fPrim.criatela("Orcamento",tela,con);
		  tela.exec(iCodOrc);
	    } 
	}
	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == btBusca) {
			if (txtDtIni.getVlrString().length() < 10)
			  Funcoes.mensagemInforma(this,"Digite a data inicial!");
			else if (txtDtFim.getVlrString().length() < 10)
			  Funcoes.mensagemInforma(this,"Digite a data final!");
			else
			  carregaTabela();
		}
		if (evt.getSource()==btPrevimp){
			imprimir(true);
		}
	}
	public void setConexao(Connection cn) {
		super.setConexao(cn);
		lcConv.setConexao(con);
		lcCli.setConexao(con);
		lcTipoConv.setConexao(con);
		lcEnc.setConexao(con);
	}
}
