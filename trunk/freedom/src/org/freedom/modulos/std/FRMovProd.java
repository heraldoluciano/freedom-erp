/**
 * @version 08/12/2000 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freeedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FRListaProd.java <BR>
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
 * Relatório de produtos.
 * 
 */

package org.freedom.modulos.std;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Painel;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FRelatorio;

public class FRMovProd extends FRelatorio {
  private JRadioGroup rgOrdem = null;
  private JRadioGroup rgAtivoProd=null;
  private JRadioGroup rgProduto=null;
  private JPanel pnlbSelec = new JPanel(new GridLayout(1,1));
  private Painel pinSelec = new Painel(350,90);
  private JLabel lbSelec = new JLabel(" Seleção:");
  private JLabel lbDe = new JLabel("de:");
  private JLabel lbA = new JLabel("à:");
  private JTextFieldPad txtDe = new JTextFieldPad();
  private JTextFieldPad txtA = new JTextFieldPad();
  private JLabel lbTipoProd = new JLabel("Tipo de Produto:");
  private JLabel lbOrdem = new JLabel("Ordenar por:");
  private JLabel lbFiltrar = new JLabel("Filtrar por:");
  private JCheckBoxPad cbAgrupar = null;  
  private JLabel lbForn = new JLabel("Código e descrição do Fornecedor");
  private JLabel lbGrupo = new JLabel("Código e descrição do Grupo");
  private JLabel lbMarca = new JLabel("Código e descrição da Marca");
  private Vector vLabs = new Vector();
  private Vector vVals = new Vector();
  private Vector vVals1 = new Vector();
  private Vector vLabs1 = new Vector();
  private Vector vLabs2 = new Vector();
  private Vector vVals2 = new Vector(); 
  private JLabel lbAlmox = new JLabel("Código e descrição do Almoxarifado");
  private JTextFieldPad txtCodForn = new JTextFieldPad();
  private JTextFieldFK txtDescForn = new JTextFieldFK(); 
  private JTextFieldPad txtCodGrupo = new JTextFieldPad();
  private JTextFieldFK txtDescGrupo = new JTextFieldFK();  
  private JTextFieldPad txtCodAlmox = new JTextFieldPad();
  private JTextFieldFK txtDescAlmox = new JTextFieldFK();
  private JTextFieldPad txtCodMarca = new JTextFieldPad();
  private JTextFieldFK txtDescMarca=new JTextFieldFK();
  private JTextFieldPad txtSiglaMarca = new JTextFieldPad();
  
  private ListaCampos lcAlmox = new ListaCampos(this);
  private ListaCampos lcCodForn = new ListaCampos(this);
  private ListaCampos lcGrupo = new ListaCampos(this);
  private ListaCampos lcMarca = new ListaCampos(this);
  
  private Connection con = null;
  public FRMovProd() {
	
    setTitulo("Relatório de Produtos");
    setAtribos(90,90,460,470);
    vLabs.addElement("Código");
    vLabs.addElement("Descrição");
    vVals.addElement("C");
    vVals.addElement("D");
    rgOrdem = new JRadioGroup(1,2,vLabs,vVals);
    rgOrdem.setVlrString("D");
	
	vLabs1.addElement("Ativos");
	vLabs1.addElement("Inativos");
	vLabs1.addElement("Todos");
	vVals1.addElement("A");
	vVals1.addElement("N");
	vVals1.addElement("T");
	rgAtivoProd = new  JRadioGroup(1,3, vLabs1, vVals1);
	rgAtivoProd.setVlrString("A");
	
	vLabs2.addElement("Comercio");
	vLabs2.addElement("Serviço");
	vLabs2.addElement("Fabricação");
	vLabs2.addElement("Mat.Prima");
	vLabs2.addElement("Patrimonio");
	vLabs2.addElement("Consumo");
	vLabs2.addElement("Todos");
	vVals2.addElement("P");
	vVals2.addElement("S");
	vVals2.addElement("F");
	vVals2.addElement("M");
	vVals2.addElement("O");
	vVals2.addElement("C");
	vVals2.addElement("T");
	
	rgProduto= new JRadioGroup(7,1,vLabs2, vVals2);
	rgProduto.setVlrString("P");
    
    txtCodAlmox.setTipo(JTextFieldPad.TP_INTEGER,8,0);
    txtDescAlmox.setTipo(JTextFieldPad.TP_STRING,40,0);
    lcAlmox.add(new GuardaCampo( txtCodAlmox, 7, 80, 110, 30, "CodAlmox", "Código do Almoxarifado", true, false, null, JTextFieldPad.TP_INTEGER,false),"txtCodSetor");
    lcAlmox.add(new GuardaCampo( txtDescAlmox, 90, 100, 220, 30, "DescAlmox", "Descrição", false, false, null, JTextFieldPad.TP_STRING,false),"txtDescSetor");
    lcAlmox.montaSql(false, "ALMOX", "EQ");
    lcAlmox.setReadOnly(true);
    txtCodAlmox.setTabelaExterna(lcAlmox);
    txtCodAlmox.setFK(true);
    txtCodAlmox.setNomeCampo("CodAlmox");

    txtCodForn.setTipo(JTextFieldPad.TP_INTEGER,8,0);
    txtDescForn.setTipo(JTextFieldPad.TP_STRING,40,0);
    lcCodForn.add(new GuardaCampo( txtCodForn, 7, 80, 80, 20, "CodFor", "Código", true, false, null, JTextFieldPad.TP_INTEGER,false),"txtCodForn");
    lcCodForn.add(new GuardaCampo( txtDescForn, 90, 100, 207, 20, "RazFor", "Descrição", false, false, null, JTextFieldPad.TP_STRING,false),"txtDescForn");
    lcCodForn.montaSql(false, "FORNECED", "CP");
    lcCodForn.setReadOnly(true);
    txtCodForn.setTabelaExterna(lcCodForn);
    txtCodForn.setFK(true);
    txtCodForn.setNomeCampo("CodFor");
    
	cbAgrupar = new JCheckBoxPad("Agrupar por fornecedor","S","N");
	cbAgrupar.setVlrString("N");

	txtCodGrupo.setTipo(JTextFieldPad.TP_STRING,14,0);
	txtDescGrupo.setTipo(JTextFieldPad.TP_STRING,40,0);
	lcGrupo.add(new GuardaCampo( txtCodGrupo, 7, 80, 80, 20, "CodGrup", "Código", true, false, null, JTextFieldPad.TP_STRING,false),"txtCodGrupo");
	lcGrupo.add(new GuardaCampo( txtDescGrupo, 90, 100, 207, 20, "DescGrup", "Descrição", false, false, null, JTextFieldPad.TP_STRING,false),"txtDescGrupo");
	lcGrupo.montaSql(false, "GRUPO", "EQ");
	lcGrupo.setReadOnly(true);
	txtCodGrupo.setTabelaExterna(lcGrupo);
	txtCodGrupo.setFK(true);
	txtCodGrupo.setNomeCampo("CodGrup");
	
	txtCodMarca.setTipo(JTextFieldPad.TP_STRING,6,0);
	txtDescMarca.setTipo(JTextFieldPad.TP_STRING,40,0);
	txtSiglaMarca.setTipo(JTextFieldPad.TP_STRING,20,0);
	lcMarca.add(new GuardaCampo( txtCodMarca, 7, 100, 80, 20, "CodMarca", "Código", true, false, null, JTextFieldPad.TP_STRING,false),"txtCodMarca");
	lcMarca.add(new GuardaCampo( txtDescMarca, 90, 100, 207, 20, "DescMarca", "Descrição", false, false, null, JTextFieldPad.TP_STRING,false),"txtDescMarca");
	lcMarca.add(new GuardaCampo( txtSiglaMarca, 90, 100, 207, 20, "SiglaMarca", "Sigla", false, false, null, JTextFieldPad.TP_STRING,false),"txtSiglaMarca");
	txtCodMarca.setTabelaExterna(lcMarca);
	txtCodMarca.setNomeCampo("CodMarca");
	txtCodMarca.setFK(true);
	lcMarca.setReadOnly(true);
	lcMarca.montaSql(false, "MARCA", "EQ");
	
	
	
    pnlbSelec.add(lbSelec);
    adic(lbOrdem,7,3,185,30);
    adic(rgOrdem,7,27,190,30);
    adic(lbFiltrar, 200,3,120,30);
	adic(rgAtivoProd,200,27,240,30);
	adic(cbAgrupar,10,140,180,30);
	adic(lbTipoProd,200,20,200,100);
	adic(rgProduto,200,80,240,130);
	adic(pnlbSelec,7,63,80,15);
	adic(pinSelec,7,80,190,130);
	
	pinSelec.adic(lbDe,7,10,30,20);
	pinSelec.adic(txtDe,30,10,140,20);
	
	pinSelec.adic(lbA,7,36,30,20);
	pinSelec.adic(txtA,30,36,140,20);
    
    adic(lbForn,7,215,300,20);
	adic(txtCodForn,7,235,80,20);
	adic(txtDescForn,90,235,350,20);	
	adic(lbAlmox,7,260,250,20);
	adic(txtCodAlmox,7,280,80,20);
	adic(txtDescAlmox,90,280,350,20);
	adic(lbGrupo,7,305,250,20);
	adic(txtCodGrupo,7,325,80,20); 
	adic(txtDescGrupo,90,325,350,20);
	adic(lbMarca,7,345,250,20);
	adic(txtCodMarca,7,365,80,20); 
	adic(txtDescMarca,90,365,350,20);
		
	
	
  }
  public String[] getValores() {
    String[] sRetorno = new String[11];
    if (rgOrdem.getVlrString().compareTo("C") == 0 )
      sRetorno[0] = "1";
    else if (rgOrdem.getVlrString().compareTo("D") == 0 )
      sRetorno[0] = "2";
       
    sRetorno[1] = txtDe.getText();
    sRetorno[2] = txtA.getText();
    sRetorno[3] = rgAtivoProd.getVlrString();
    sRetorno[4] = txtCodForn.getVlrString();
    sRetorno[5] = txtDescForn.getVlrString();
    sRetorno[6] = txtCodAlmox.getText();
    sRetorno[7] = txtDescAlmox.getText();
    sRetorno[8] = rgProduto.getVlrString();
    sRetorno[9] = txtCodMarca.getVlrString();
    sRetorno[10] = txtDescMarca.getText();
	return sRetorno;

  }
  public void setConexao(Connection cn) {
  	lcAlmox.setConexao(cn);
  	lcCodForn.setConexao(cn);
  	lcGrupo.setConexao(cn);
  	lcMarca.setConexao(cn);
  	con = cn;
  }
  public void imprimir(boolean bVisualizar) {
	 ImprimeOS imp = new ImprimeOS("",con);
	 Vector vFiltros = new Vector();
	 String sSQL = "";
	 int linPag = imp.verifLinPag()-1;
	 int iContaReg = 0;
	 String sWhere = "";
	 String sAnd = " and ";
	 String[] sValores; 
	 String sTipo = "";
	 imp.setTitulo("Relatório de Produtos");
	 imp.montaCab();
	 	 
	 sValores = getValores();
	   
	 if (sValores[1].trim().length() > 0) {
	   sWhere = sWhere+sAnd+"DESCPROD >= '"+sValores[1]+"'";
	   vFiltros.add("PRODUTOS MAIORES QUE "+sValores[1].trim());
	   sAnd = " AND ";
	 }
	 if (sValores[2].trim().length() > 0) {
	   sWhere = sWhere+sAnd+"DESCPROD <= '"+sValores[2]+"'";
	   vFiltros.add("PRODUTOS MENORES QUE "+sValores[2].trim());
	   sAnd = " AND ";
	 }
	 if (sValores[3].equals("A")) {
	   sWhere = sWhere+sAnd+"ATIVOPROD='S'";
	   vFiltros.add("PRODUTOS ATIVOS");
	   sAnd = " AND ";
	 }
	 else if (sValores[3].equals("N")) {
	   sWhere = sWhere+sAnd+"ATIVOPROD='N'";
	   vFiltros.add("PRODUTOS INATIVOS");
	   sAnd = " AND ";
     }
     else if (sValores[3].equals("T")){
  	  vFiltros.add("PRODUTOS ATIVOS E INATIVOS");
     }
	 if (sValores[7].length() > 0) {
	   sWhere = sWhere+sAnd+"CODALMOX = "+sValores[6];
	   vFiltros.add("ALMOXARIFADO = "+sValores[7]);
	   sAnd = " AND ";
	 }
	 if (sValores[9].length()> 0) {
	 	sWhere =sWhere+sAnd+"CODMARCA = '"+sValores[9]+"'";
	 	vFiltros.add("MARCA: "+sValores[10]);
	 	sAnd=" AND ";
	 }
	 
	if (sValores[8].equals("P")) {
		   sWhere = sWhere+sAnd+"TIPOPROD='P'";
		   vFiltros.add("TIPO PRODUTOS");
		   sAnd = " AND ";
	}
	
	else if (sValores[8].equals("S")) {
		   sWhere = sWhere+sAnd+"TIPOPROD='S'";
		   vFiltros.add("TIPO SERVIÇOS");
		   sAnd = " AND ";	 
	}
	
	else if (sValores[8].equals("F")) {
		sWhere = sWhere+sAnd+"TIPOPROD='F'";
		vFiltros.add("FABRICAÇÃO");
		sAnd = " AND ";
	}
	
	else if (sValores[8].equals("M")) {
		   sWhere = sWhere+sAnd+"TIPOPROD='M'";
		   vFiltros.add("MATERIA PRIMA");
		   sAnd = " AND ";
	}
	
	else if (sValores[8].equals("O")) {
		   sWhere = sWhere+sAnd+"TIPOPROD='O'";
		   vFiltros.add("PATRIMONIO");
		   sAnd = " AND ";	   
	}
	
	else if (sValores[8].equals("C")) {
		sWhere = sWhere+sAnd+"TIPOPROD='C'";
		vFiltros.add("CONSUMO");
		sAnd = " AND ";	   
	}
	
	else if (sValores[8].equals("T")){
	    vFiltros.add("TODOS OS TIPOS DE PRODUTOS");
	}	   
    
	  if (cbAgrupar.getVlrString().equals("N")){ 
	     if (!txtCodForn.getVlrString().equals("")){
	      vFiltros.add("Fornecedor: "+txtDescForn.getVlrString());
	     }
	  }    
	if (!(txtCodGrupo.getVlrString().equals(""))){
        vFiltros.add("PRODUTOS DO GRUPO "+txtDescGrupo.getVlrString());
        sWhere = " AND PD.CODGRUP LIKE '"+txtCodGrupo.getVlrString()+"%'";    	
        System.out.println("Filtrou por grupo:"+sWhere);
    }
		
	 try {
	 	
		if (cbAgrupar.getVlrString().equals("S")) {// Para agrupamento por fornecedores
			sSQL = "SELECT PD.CODPROD,PD.DESCPROD,PD.CODBARPROD,PD.CODFABPROD,PD.CODUNID,PD.TIPOPROD,PD.CODGRUP,'N',PF.CODFOR, "+
		              "(SELECT F.RAZFOR FROM CPFORNECED F WHERE F.CODFOR=PF.CODFOR AND F.CODEMP=PF.CODEMP AND F.CODFILIAL=PF.CODFILIAL),PF.REFPRODFOR "+
                      "FROM EQPRODUTO PD LEFT OUTER JOIN CPPRODFOR PF ON (PD.CODPROD = PF.CODPROD AND PD.CODEMP = PF.CODEMP) " +
                      "WHERE PD.CODEMP=? AND PD.CODFILIAL=? "+
                      "AND NOT EXISTS(SELECT * FROM EQMOVPROD MV WHERE MV.CODEMPPD=PD.CODEMP "+
 					  "AND MV.CODFILIALPD=PD.CODFILIAL AND MV.CODPROD=PD.CODPROD) "+
 					  (!sValores[4].equals("")?"AND EXISTS(SELECT * FROM CPPRODFOR PF WHERE PF.CODPROD = PD.CODPROD AND PF.CODEMP = PD.CODEMP AND PF.CODFOR="+sValores[4]+" )":"")+
					  sWhere+" "+
					  " UNION " +
					  "SELECT PD.CODPROD,PD.DESCPROD,PD.CODBARPROD,PD.CODFABPROD,PD.CODUNID,PD.TIPOPROD,PD.CODGRUP,'S',PF.CODFOR, "+
					  "(SELECT F.RAZFOR FROM CPFORNECED F WHERE F.CODFOR=PF.CODFOR AND F.CODEMP=PF.CODEMP AND F.CODFILIAL=PF.CODFILIAL),PF.REFPRODFOR "+
					  "FROM EQPRODUTO PD LEFT OUTER JOIN CPPRODFOR PF ON (PD.CODPROD = PF.CODPROD AND PD.CODEMP = PF.CODEMP) " +
					  "WHERE PD.CODEMP=? AND PD.CODFILIAL=? "+
                      "AND EXISTS(SELECT * FROM EQMOVPROD MV WHERE MV.CODEMPPD=PD.CODEMP "+
					  "AND MV.CODFILIALPD=PD.CODFILIAL AND MV.CODPROD=PD.CODPROD) "+
					  (!sValores[4].equals("")?"AND EXISTS(SELECT * FROM CPPRODFOR PF WHERE PF.CODPROD = PD.CODPROD AND PF.CODEMP = PD.CODEMP AND PF.CODFOR="+sValores[4]+" )":"")+
					  sWhere+" "+
					  " ORDER BY 9,"+sValores[0];
		}		
		else {
			sSQL = "SELECT PD.CODPROD,PD.DESCPROD,PD.CODBARPROD,PD.CODFABPROD,PD.CODUNID,PD.TIPOPROD,PD.CODGRUP,'N',''  "+				  
				   "FROM EQPRODUTO PD "+ 				   
				   "WHERE PD.CODEMP=? AND PD.CODFILIAL=? "+
				   "AND NOT EXISTS(SELECT * FROM EQMOVPROD MV WHERE MV.CODEMPPD=PD.CODEMP "+
				   "AND MV.CODFILIALPD=PD.CODFILIAL AND MV.CODPROD=PD.CODPROD) "+
				   
				   (!sValores[4].equals("")?"AND EXISTS(SELECT * FROM CPPRODFOR PF WHERE PF.CODPROD = PD.CODPROD AND PF.CODEMP = PD.CODEMP AND PF.CODFOR="+sValores[4]+" )":"")+
				   sWhere+" "+

				   "UNION "+				   
				   "SELECT PD.CODPROD,PD.DESCPROD,PD.CODBARPROD,PD.CODFABPROD,PD.CODUNID,PD.TIPOPROD,PD.CODGRUP,'S','' "+
				   "FROM EQPRODUTO PD "+				   
				   "WHERE PD.CODEMP=? AND PD.CODFILIAL=? "+
				   "AND EXISTS(SELECT * FROM EQMOVPROD MV WHERE MV.CODEMPPD=PD.CODEMP "+
				   "AND MV.CODFILIALPD=PD.CODFILIAL AND MV.CODPROD=PD.CODPROD) "+
				   
				   (!sValores[4].equals("")?"AND EXISTS(SELECT * FROM CPPRODFOR PF WHERE PF.CODPROD = PD.CODPROD AND PF.CODEMP = PD.CODEMP AND PF.CODFOR="+sValores[4]+" )":"")+
				    sWhere+" ORDER BY "+sValores[0];
		}

        //System.out.println("SQL:   ---> "+sSQL);
		
		PreparedStatement ps = null;
		ResultSet rs = null;

		if (!con.getAutoCommit())
			con.commit();
		 ps = con.prepareStatement(sSQL);
		 ps.setInt(1,Aplicativo.iCodEmp);
		 ps.setInt(2,ListaCampos.getMasterFilial("PDPRODUTO"));
		 ps.setInt(3,Aplicativo.iCodEmp);
		 ps.setInt(4,ListaCampos.getMasterFilial("PDPRODUTO"));
		 rs = ps.executeQuery();
		 imp.limpaPags();

		 boolean bImpNulo = true;
		 boolean bPulouPagina = false;
		 String sCodFor = "";
		 		 	 		
		 while ( rs.next() ) {		   
		   if (imp.pRow()==0) {
			 imp.impCab(136);
			 imp.say(imp.pRow()+0,0,""+imp.comprimido());
			 imp.say(imp.pRow()+0,2,"|"+Funcoes.replicate(" ",61)+"Filtrado por:"+Funcoes.replicate(" ",60)+"|");
			 for (int i=0;i<vFiltros.size();i++) {            
					 String sTmp = (String)vFiltros.elementAt(i);
					 sTmp = "|"+Funcoes.replicate(" ",(((135-sTmp.length())/2)-1))+sTmp;
					 sTmp += Funcoes.replicate(" ",135-sTmp.length())+"|";
					 imp.say(imp.pRow()+1,0,""+imp.comprimido());
					 imp.say(imp.pRow()+0,2,sTmp);
			 }
			
			 imp.say(imp.pRow()+1,0,""+imp.comprimido());
			 imp.say(imp.pRow()+0,0,"|"+ Funcoes.replicate("-",134)+"|");
			 imp.say(imp.pRow()+1,0,""+imp.comprimido());
			 imp.say(imp.pRow()+0,0,"|");
			 imp.say(imp.pRow()+0,3,"Código:");
			 imp.say(imp.pRow()+0,12,"|");
			 imp.say(imp.pRow()+0,13," Cod.Barra:");
			 imp.say(imp.pRow()+0,27,"|");
			 imp.say(imp.pRow()+0,29,"Cod.Fab:");
		     imp.say(imp.pRow()+0,41,"|");
			 imp.say(imp.pRow()+0,43,"Descrição:");
			 imp.say(imp.pRow()+0,74,"|");
			 imp.say(imp.pRow()+0,76,"Unidade:");
			 imp.say(imp.pRow()+0,85,"|");
			 imp.say(imp.pRow()+0,86," Mov.");
			 imp.say(imp.pRow()+0,92,"|     Cod.Grupo");
			 imp.say(imp.pRow()+0,114,"|      Tipo");
			 imp.say(imp.pRow()+0,136,"|");
			 imp.say(imp.pRow()+1,0,""+imp.comprimido());
			 imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",134)+"|");
			
		   }		   
		   if ((cbAgrupar.getVlrString().equals("S")) && (!sCodFor.equals(rs.getString(9))) && bImpNulo || ((bPulouPagina) && (cbAgrupar.getVlrString().equals("S")))) {
		      if (iContaReg>0 && !bPulouPagina) {
				imp.say(imp.pRow()+1,0,""+imp.comprimido());
			    imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",134)+"|");
		      }							 
			  if (!(rs.getString(10)==null)) {
				imp.say(imp.pRow()+1,0,"|");
			    imp.say(imp.pRow()+0,3,rs.getString(10));
				imp.say(imp.pRow()+0,136,"|");
				imp.say(imp.pRow()+1,0,"");
				imp.say(imp.pRow()+0,0,"|"+ Funcoes.replicate("-",134)+"|");					    
			  } 
			  else {
			     imp.say(imp.pRow()+1,0,"|  FORNECEDOR NÃO INFORMADO");
				 imp.say(imp.pRow()+0,136,"|");
				 imp.say(imp.pRow()+1,0,"|");
				 imp.say(imp.pRow()+0,0, Funcoes.replicate("-",134)+"|");				 				
				 bImpNulo = false;
			  }
		      bPulouPagina = false;
		   }
		   		   
		   imp.say(imp.pRow()+1,0,""); 
		   imp.say(imp.pRow()+0,0,"|");
		   imp.say(imp.pRow()+0,3,rs.getString("CodProd"));
		   imp.say(imp.pRow()+0,12,"|");
		   imp.say(imp.pRow()+0,13,rs.getString("codBarProd"));
		   imp.say(imp.pRow()+0,27,"|");
		   
		   if (cbAgrupar.getVlrString().equals("N")){
              imp.say(imp.pRow()+0,29,rs.getString("CodFabProd")!=null ? Funcoes.copy(rs.getString("CodFabProd"),12):"");
           }
           
		   else {
			  imp.say(imp.pRow()+0,29,rs.getString("RefProdFor")!=null ? Funcoes.copy(rs.getString("RefProdFor"),12):"");
		   }
		   imp.say(imp.pRow()+0,41,"|");
		   imp.say(imp.pRow()+0,42,rs.getString("DescProd") != null ? rs.getString("Descprod").substring(0,30) : "");
		   imp.say(imp.pRow()+0,74,"|");
		   imp.say(imp.pRow()+0,76,rs.getString("Codunid"));
		   imp.say(imp.pRow()+0,85,"|");
		   imp.say(imp.pRow()+0,89,rs.getString(8));
		   imp.say(imp.pRow()+0,92,"|");
		   imp.say(imp.pRow()+0,99,rs.getString("codgrup"));
		   imp.say(imp.pRow()+0,114,"|");
		   		   		  		   
	  if (sValores[8].equals("T")){
		    	
		   if (rs.getString("TIPOPROD").equals("P")){
		   	   sTipo="Comercio";
		   	   imp.say(imp.pRow()+0,119,sTipo);
		   }
		   else if (rs.getString("TIPOPROD").equals("S")){
		   	  sTipo="Serviço";
		   	  imp.say(imp.pRow()+0,119,sTipo);
		   }
		   else if (rs.getString("TIPOPROD").equals("F")){
		   	  sTipo="Fabricação";
      	      imp.say(imp.pRow()+0,119,sTipo);
		   }
		   else if (rs.getString("TIPOPROD").equals("M")){
		   	  sTipo="Mat.Prima";
		   	  imp.say(imp.pRow()+0,119,sTipo);
		   }
		   else if (rs.getString("TIPOPROD").equals("O")){
		   	sTipo="Patrimônio";
		   	imp.say(imp.pRow()+0,119,sTipo);
		   }
		   else if (rs.getString("TIPOPROD").equals("C")){
		   	sTipo="Consumo";
		   		imp.say(imp.pRow()+0,119,sTipo);
		   }
	  }	   		   
		
	       imp.say(imp.pRow()+0,136,"|");

		   sCodFor = rs.getString(9)==null?"":rs.getString(9);
		   
		   if (imp.pRow()>=linPag) {
			 imp.incPags();
			 bPulouPagina = true;
			 imp.eject();
		   }
		     iContaReg++;
		 }
		 imp.say(imp.pRow()+1,0,""+imp.comprimido());
	     imp.say(imp.pRow()+0,0,"+"+ Funcoes.replicate("=",134)+"+");
		 imp.eject();

		 imp.fechaGravacao();
		 con.commit();		 
	 }
	 catch ( SQLException err ) {
		 Funcoes.mensagemErro(this,"Erro consulta tabela de produtos!"+err.getMessage());
	 }
	 if (bVisualizar) {
	   imp.preview(this);
	 }
	 else {
  	   imp.print();
	 }
  }
}