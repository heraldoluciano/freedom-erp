/**
 * @version 03/08/2004 <BR>
 * @author Setpoint Informática Ltda./Robson Sanchez <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FRInvPeps.java <BR>
 * 
 * Este arquivo é parte do sistema Freedom-ERP, o Freedom-ERP é um software livre; você pode redistribui-lo e/ou <BR>
 * modifica-lo dentro dos termos da Licença Pública Geral GNU como publicada pela Fundação do Software Livre (FSF); <BR>
 * na versão 2 da Licença, ou (na sua opnião) qualquer versão. <BR>
 * Este programa é distribuido na esperança que possa ser  util, mas SEM NENHUMA GARANTIA; <BR>
 * sem uma garantia implicita de ADEQUAÇÂO a qualquer MERCADO ou APLICAÇÃO EM PARTICULAR. <BR>
 * Veja a Licença Pública Geral GNU para maiores detalhes. <BR>
 * Você deve ter recebido uma cópia da Licença Pública Geral GNU junto com este programa, se não, <BR>
 * escreva para a Fundação do Software Livre(FSF) Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA <BR> <BR>
 *
 * Comentários sobre a classe...
 * 
 */

package org.freedom.modulos.std;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Vector;

import org.freedom.funcoes.Funcoes;
import org.freedom.infra.functions.StringFunctions;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.GuardaCampo;
import org.freedom.library.ImprimeOS;
import org.freedom.library.ListaCampos;
import org.freedom.library.swing.JCheckBoxPad;
import org.freedom.library.swing.JLabelPad;
import org.freedom.library.swing.JRadioGroup;
import org.freedom.library.swing.JTextFieldFK;
import org.freedom.library.swing.JTextFieldPad;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FRelatorio;

public class FRInvPeps extends FRelatorio {
	private static final long serialVersionUID = 1L;


  private final int TAM_GRUPO = 14;
  
  private JTextFieldPad txtData = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
  private JTextFieldPad txtPagina = new JTextFieldPad(JTextFieldPad.TP_INTEGER,6,0);
  private JTextFieldPad txtCodAlmox = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldFK txtDescAlmox = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
  private JTextFieldPad txtCodMarca = new JTextFieldPad(JTextFieldPad.TP_STRING,6,0);
  private JTextFieldFK txtDescMarca = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
  private JTextFieldFK txtSiglaMarca = new JTextFieldFK(JTextFieldPad.TP_STRING,20,0);
  private JTextFieldPad txtCodGrup = new JTextFieldPad(JTextFieldPad.TP_STRING,TAM_GRUPO,0);
  private JTextFieldFK txtDescGrup = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
  private JCheckBoxPad cbSemEstoq = new JCheckBoxPad("Imprimir produtos sem estoque?","S","N");
  private JCheckBoxPad cbAtivos = new JCheckBoxPad("Somente ativos?","S","N");

  private ListaCampos lcAlmox = new ListaCampos(this);
  private ListaCampos lcGrup = new ListaCampos(this);
  private ListaCampos lcMarca = new ListaCampos(this);

  private JLabelPad lbCodAlmox = new JLabelPad("Cód.almox.");
  private JLabelPad lbCodMarca = new JLabelPad("Cód.marca");
  private JLabelPad lbCodGrup = new JLabelPad("Cód.grupo");
  private JLabelPad lbDescAlmox = new JLabelPad("Descrição do almoxarifado");
  private JLabelPad lbDescMarca = new JLabelPad("Descrição da marca");
  private JLabelPad lbDescGrup = new JLabelPad("Descrição do grupo");
  
  private JRadioGroup<?, ?> rgOrdem = null;
  private Vector<String> vDesc = new Vector<String>();
  private Vector<String> vOpc = new Vector<String>();
  
  private JRadioGroup<?, ?> rgCusto = null;
  private Vector<String> vDescCusto = new Vector<String>();
  private Vector<String> vOpcCusto = new Vector<String>();
  private boolean[] bPrefs = null;
  
  public FRInvPeps() {
    setTitulo("Inventário");
    setAtribos(80,30,400,380);
    
    GregorianCalendar cal = new GregorianCalendar();
    cal.add(Calendar.DATE,0);
    txtData.setVlrDate(cal.getTime());
    txtPagina.setVlrInteger(new Integer(1));

    vDesc.addElement("Descrição");
    vDesc.addElement("Código");
    vOpc.addElement("D");
    vOpc.addElement("C");
    rgOrdem = new JRadioGroup<String, String>(2,1,vDesc,vOpc);
    
    vDescCusto.addElement("C.PEPS");
    vDescCusto.addElement("C.MPM");
    vDescCusto.addElement("P.BASE");
    vOpcCusto.addElement("P");
    vOpcCusto.addElement("M");
    vOpcCusto.addElement("B");
    
    rgCusto = new JRadioGroup<String, String>(1,3,vDescCusto,vOpcCusto);
    

    cbSemEstoq.setVlrString("N");
    cbAtivos.setVlrString("S");
    
    lcAlmox.add(new GuardaCampo( txtCodAlmox, "CodAlmox", "Cód.almox.", ListaCampos.DB_PK, false));
    lcAlmox.add(new GuardaCampo( txtDescAlmox, "DescAlmox", "Descrição do almox.", ListaCampos.DB_SI, false));
    txtCodAlmox.setTabelaExterna(lcAlmox);
    txtCodAlmox.setNomeCampo("CodAlmox");
    txtCodAlmox.setFK(true);
    lcAlmox.setReadOnly(true);
    lcAlmox.montaSql(false, "ALMOX", "EQ");
    
    lcMarca.add(new GuardaCampo( txtCodMarca, "CodMarca", "Cód.marca", ListaCampos.DB_PK, false));
    lcMarca.add(new GuardaCampo( txtDescMarca, "DescMarca", "Descrição da marca", ListaCampos.DB_SI, false));
    lcMarca.add(new GuardaCampo( txtSiglaMarca, "SiglaMarca", "Sigla", ListaCampos.DB_SI, false));
    lcMarca.montaSql(false, "MARCA", "EQ");
    lcMarca.setReadOnly(true);
    txtCodMarca.setTabelaExterna(lcMarca);
    txtCodMarca.setFK(true);
    txtCodMarca.setNomeCampo("CodMarca");
    
    lcGrup.add(new GuardaCampo( txtCodGrup, "CodGrup", "Cód.gurpo", ListaCampos.DB_PK, false));
    lcGrup.add(new GuardaCampo( txtDescGrup, "DescGrup", "Descrição do grupo", ListaCampos.DB_SI, false));
    lcGrup.montaSql(false, "GRUPO", "EQ");
    lcGrup.setReadOnly(true);
    txtCodGrup.setTabelaExterna(lcGrup);
    txtCodGrup.setFK(true);
    txtCodGrup.setNomeCampo("CodGrup");
    
    adic(new JLabelPad("Ordem:"),7,0,100,20);
    adic(rgOrdem,7,20,100,60);
    adic(new JLabelPad("Estoque de:"),117,0,100,20);
    adic(txtData,117,20,100,20);
    adic(new JLabelPad("Página inicial:"),117,40,100,20);
    adic(txtPagina,117,60,100,20);
    adic(lbCodAlmox,7,80,250,20);
    adic(txtCodAlmox,7,100,80,20);
    adic(lbDescAlmox,90,80,250,20);
    adic(txtDescAlmox,90,100,250,20);
    adic(lbCodMarca,7,120,250,20);
    adic(txtCodMarca,7,140,80,20);
    adic(lbDescMarca,90,120,250,20);
    adic(txtDescMarca,90,140,250,20);
    adic(lbCodGrup,7,160,250,20);
    adic(txtCodGrup,7,180,80,20);
    adic(lbDescGrup,90,160,250,20);
    adic(txtDescGrup,90,180,250,20);
    adic(cbSemEstoq,7,200,250,20);
    adic(cbAtivos,7,230,250,30);
    adic(rgCusto,7,260,250,30);
    
    
  }

  
  public void imprimir(boolean bVisualizar) {
  	String sSql = "";
  	String sCpCodigo = "";
  	String sSemEstoq = "";
  	String sCodMarca = "";
  	String sCodGrup = "";
  	String sFiltros1= "";
  	String sFiltros2= "";
  	int iCodAlmox = 0;
  	ImprimeOS imp = null;
  	int linPag = 0;
  	PreparedStatement ps = null;
  	ResultSet rs = null;
  	double deCustoTot = 0;
  	double deSldProd = 0;

  	try {
  		
  		imp = new ImprimeOS("",con);
  		linPag = imp.verifLinPag()-1;
  		  		
  		sCpCodigo = (bPrefs[0]?"REFPROD":"CODPROD");
  		iCodAlmox = txtCodAlmox.getVlrInteger().intValue();
  		sSemEstoq = cbSemEstoq.getVlrString();
  		sCodMarca = txtCodMarca.getVlrString().trim();
  		sCodGrup = txtCodGrup.getVlrString().trim();
  		
  		String swhere = "";
			if( cbAtivos.getVlrString().equals( "S" ) ){
				swhere = "('S')";
				
			}else{
				swhere = "('S','N')";
			}
			
        String swhere2 = (sSemEstoq.equals("N")?" WHERE SLDPROD!=0 ":""); 	
		String swhere3 = "";
        if(swhere2.equals( "" )) {
			swhere3 = " WHERE ATIVOPROD IN ";
		}
        else {
        	swhere3 = " AND ATIVOPROD IN ";
        }
        
  		//iCodAlmox = txt
  		sSql = "SELECT "+sCpCodigo+",DESCPROD,SLDPROD,CUSTOUNIT,CUSTOTOT "
  		 			+ ",COALESCE(CODFABPROD,0) CODFABPROD,COALESCE(CODBARPROD,0) CODBARPROD,ATIVOPROD " 
  		 			+ "FROM EQRELPEPSSP(?,?,?,?,?,?,?,?,?,?,?,?,?) " 
  		 			+ swhere2
  		 			+ swhere3 + swhere + " ORDER BY "+(rgOrdem.getVlrString().equals("D")?"DESCPROD":sCpCodigo);
  		
  		
  		System.out.println(sSql);
  		try {
  			if (sSemEstoq.equals("S")) 
  				sFiltros1 = "PROD.S/ESTOQUE";
  			else 
  				sFiltros1 = "PROD.C/ESTOQUE";
  			ps = con.prepareStatement(sSql);
  			ps.setInt(1,Aplicativo.iCodEmp);
  			ps.setInt(2,ListaCampos.getMasterFilial("EQPRODUTO"));
  			ps.setDate(3,Funcoes.dateToSQLDate(txtData.getVlrDate()));
  			if (iCodAlmox!=0) {
  	  			sFiltros1 += " / ALMOX.: "+iCodAlmox+"-"+txtDescAlmox.getVlrString().trim();
  			}
  			if (sCodMarca.equals("")) {
  	  			ps.setNull(4,Types.INTEGER);
  	  			ps.setNull(5,Types.SMALLINT);
  	  			ps.setNull(6,Types.CHAR);
  			}
  			else {
  	  			ps.setInt(4,Aplicativo.iCodEmp); 
  	  			ps.setInt(5,ListaCampos.getMasterFilial("EQMARCA"));
  	  			ps.setString(6,sCodMarca);
  	  			sFiltros2 += " / MARCA: "+sCodMarca+"-"+txtDescMarca.getVlrString().trim();
  			}
  			if (sCodGrup.equals("")) {
  	  			ps.setNull(7,Types.INTEGER);
  	  			ps.setNull(8,Types.SMALLINT);
  	  			ps.setNull(9,Types.CHAR);
  			}
  			else {
  	  			ps.setInt(7,Aplicativo.iCodEmp);
  	  			ps.setInt(8,ListaCampos.getMasterFilial("EQGRUPO"));
  	  			ps.setString(9,sCodGrup);
  	  			sFiltros2 += " / GRUPO: "+sCodGrup+"-"+txtDescGrup.getVlrString().trim();
  			}
  			ps.setString(10,rgCusto.getVlrString());
  			if (iCodAlmox==0) {
  				ps.setNull(11,Types.INTEGER);
  				ps.setNull(12,Types.INTEGER);
  				ps.setNull(13,Types.INTEGER);
  			}
  			else {
  				ps.setInt(11,Aplicativo.iCodEmp);
  				ps.setInt(12,ListaCampos.getMasterFilial("EQALMOX"));
  				ps.setInt(13,iCodAlmox);
  			}
  			
  			rs = ps.executeQuery();
  			
  			imp.limpaPags();
  			
  			imp.montaCab(txtPagina.getVlrInteger().intValue()-1);
			imp.setTitulo("Relatorio de inventário de estoque");
  			imp.addSubTitulo(sFiltros1);
  			imp.addSubTitulo(sFiltros2);
  			
  			
  			
  			while ( rs.next() ) {
  				if (imp.pRow()>=(linPag-1)) {
  					imp.say(imp.pRow()+1,0,""+imp.comprimido());
  					imp.say(imp.pRow()+0,0,"+"+StringFunctions.replicate("-",133)+"+");
  					imp.incPags();
  					imp.eject();

  				}
	  			if (imp.pRow()==0) {	  				
	  				imp.impCab(136, true);
/*	  					  				
					imp.say(imp.pRow()+0,0,"|"+StringFunctions.replicate("-",133)+"|");
					imp.say(imp.pRow()+1,0,""+imp.comprimido());
					imp.say(imp.pRow()+0,0,"| CODIGO");
					imp.say(imp.pRow()+0,16,"| DESCRICAO ");
					imp.say(imp.pRow()+0,70,"| SALDO");
					imp.say(imp.pRow()+0,83,"| CUSTO UNIT.");
					imp.say(imp.pRow()+0,101,"| CUSTO TOTAL");
					imp.say(imp.pRow()+0,135,"|");
					imp.say(imp.pRow()+1,0,""+imp.comprimido());
					imp.say(imp.pRow()+0,0,"|"+StringFunctions.replicate("-",133)+"|");*/
					
	  				imp.say(imp.pRow()+0,0,"|"+StringFunctions.replicate("-",133)+"|");
					imp.say(imp.pRow()+1,0,""+imp.comprimido());
					imp.say(imp.pRow()+0,0,"| CODIGO");
					imp.say(imp.pRow()+0,16,"| COD.FAB. ");
					imp.say(imp.pRow()+0,29,"| COD.BAR. ");
					imp.say(imp.pRow()+0,43,"| DESCRICAO ");
					imp.say(imp.pRow()+0,95,"| SALDO");//19
					imp.say(imp.pRow()+0,106,"| CUSTO UNIT."); //20
					imp.say(imp.pRow()+0,118,"| CUSTO TOTAL"); //15
					imp.say(imp.pRow()+0,135,"|");
					imp.say(imp.pRow()+1,0,""+imp.comprimido());
					imp.say(imp.pRow()+0,0,"|"+StringFunctions.replicate("-",133)+"|");
	  				
	  				
				}
				/*
  				imp.say(imp.pRow()+1,0,""+imp.comprimido());
  				imp.say(imp.pRow()+0,0,"|"+Funcoes.adicEspacosEsquerda(rs.getString(sCpCodigo).trim(),13));
  				imp.say(imp.pRow()+0,16,"| "+Funcoes.adicionaEspacos(rs.getString("DESCPROD"),50));
  				imp.say(imp.pRow()+0,70,"|"+Funcoes.adicEspacosEsquerda(rs.getDouble("SLDPROD")+"",10));
  				imp.say(imp.pRow()+0,83,"|"+Funcoes.strDecimalToStrCurrency(15,2,rs.getDouble("CUSTOUNIT")+""));
  				imp.say(imp.pRow()+0,101,"|"+Funcoes.strDecimalToStrCurrency(15,2,rs.getDouble("CUSTOTOT")+""));
  				imp.say(imp.pRow()+0,135,"|");
  				*/

  				imp.say(imp.pRow()+1,0,""+imp.comprimido());
  				imp.say(imp.pRow()+0,0,"|"+Funcoes.adicEspacosEsquerda(rs.getString(sCpCodigo).trim(),13));
  				imp.say(imp.pRow()+0,16,"|"+Funcoes.adicEspacosEsquerda(rs.getString("CODFABPROD").trim(),11));
  				imp.say(imp.pRow()+0,29,"|"+Funcoes.adicEspacosEsquerda(rs.getString("CODBARPROD").trim(),12));
  				imp.say(imp.pRow()+0,43,"| "+Funcoes.adicionaEspacos(rs.getString("DESCPROD"),49));
  				imp.say(imp.pRow()+0,95,"|"+Funcoes.adicEspacosEsquerda(rs.getDouble("SLDPROD")+"",8));//19
  				imp.say(imp.pRow()+0,106,"|"+Funcoes.strDecimalToStrCurrency(11,2,rs.getDouble("CUSTOUNIT")+"")); //18
  				imp.say(imp.pRow()+0,119,"|"+Funcoes.strDecimalToStrCurrency(14,2,rs.getDouble("CUSTOTOT")+"")); //17
  				imp.say(imp.pRow()+0,135,"|");
	  			
	  			
  				deSldProd += rs.getDouble("SLDPROD");
  				deCustoTot += rs.getDouble("CUSTOTOT");
  				
  			}
  			
  			rs.close();
  			ps.close();
			con.commit();
/*  			imp.say(imp.pRow()+1,0,""+imp.comprimido());
  			imp.say(imp.pRow()+0,0,"|"+StringFunctions.replicate("-",133)+"|");
			imp.say(imp.pRow()+1,0,""+imp.comprimido());
  			imp.say(imp.pRow()+0,0,"| TOTAL");
  			imp.say(imp.pRow()+0,70,"|"+Funcoes.adicEspacosEsquerda(Funcoes.arredDouble(deSldProd,2)+"",10));
			imp.say(imp.pRow()+0,83,"|");
  			imp.say(imp.pRow()+0,101,"|"+Funcoes.strDecimalToStrCurrency(15,2,deCustoTot+""));
  			imp.say(imp.pRow()+0,135,"|");
  			imp.say(imp.pRow()+1,0,""+imp.comprimido());
  			imp.say(imp.pRow()+0,0,"+"+StringFunctions.replicate("-",133)+"+");*/

  			imp.say(imp.pRow()+1,0,""+imp.comprimido());
  			imp.say(imp.pRow()+0,0,"|"+StringFunctions.replicate("-",133)+"|");
			imp.say(imp.pRow()+1,0,""+imp.comprimido());
  			imp.say(imp.pRow()+0,0,"| TOTAL");
  			imp.say(imp.pRow()+0,95,"|"+Funcoes.adicEspacosEsquerda(Funcoes.arredDouble(deSldProd,2)+"",8));//19
			imp.say(imp.pRow()+0,106,"|"); //18
  			imp.say(imp.pRow()+0,119,"|"+Funcoes.strDecimalToStrCurrency(14,2,deCustoTot+""));//17
  			imp.say(imp.pRow()+0,135,"|");
  			imp.say(imp.pRow()+1,0,""+imp.comprimido());
  			imp.say(imp.pRow()+0,0,"+"+StringFunctions.replicate("-",133)+"+");
  			
  			
  			imp.eject();
  			imp.fechaGravacao();
  			
  		}
  		catch (SQLException err) {
  			Funcoes.mensagemErro(this,"Erro executando a consulta.\n"+err.getMessage(),true,con,err);
  			err.printStackTrace();
  		}
  		if (bVisualizar) {
  			imp.preview(this);
  		}
  		else {
  			imp.print();
  		}
  	}
  	finally {
  		sSql = null;
  		sCpCodigo = null;
  		sSemEstoq = null;
  	  	sCodMarca = null;
  	  	sCodGrup = null;
  	  	sFiltros1 = null;
  	    sFiltros2 = null;
  		imp = null;
  		ps = null;
  		rs = null;
  		deCustoTot = 0;
  		deSldProd = 0;
  	  	linPag = 0;
  	}

  }
  
  public void setConexao(DbConnection cn) {
    super.setConexao(cn);
    lcAlmox.setConexao(cn);
    lcGrup.setConexao(cn);
    lcMarca.setConexao(cn);
    bPrefs = getPrefere();
  }

  private boolean[] getPrefere() {
    boolean[] bRetorno = {false};
    String sSQL = "SELECT USAREFPROD" +
    		" FROM SGPREFERE1 WHERE CODEMP=? AND CODFILIAL=?";
    PreparedStatement ps = null;
    ResultSet rs = null;
    try {
      ps = con.prepareStatement(sSQL);
	  ps.setInt(1,Aplicativo.iCodEmp);
	  ps.setInt(2,ListaCampos.getMasterFilial("SGPREFERE1"));
      rs = ps.executeQuery();
      if (rs.next()) {
      	if (rs.getString("UsaRefProd")!=null) {
          if (rs.getString("UsaRefProd").trim().equals("S"))
             bRetorno[0] = true;
      	}
      }
      rs.close();
      ps.close();
      con.commit();
    }
    catch (SQLException err) {
		Funcoes.mensagemErro(this,"Erro ao carregar a tabela PREFERE1!\n"+err.getMessage(),true,con,err);
    }
    finally {
    	sSQL = null;
    	ps = null;
    	rs = null;
    }
    return bRetorno;
  }

}
