/**
 * @version 03/08/2004 <BR>
 * @author Setpoint Informática Ltda./Alex Rodrigues <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.pcp <BR>
 * Classe: @(#)FRCustoProducao.java <BR>
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

package org.freedom.modulos.pcp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Vector;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FRelatorio;

public class FRCustoProducao extends FRelatorio {
  private static final long serialVersionUID = 1L;
//  private int casasDecFin = Aplicativo.casasDecFin;
  private JTextFieldPad txtData = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
  private JTextFieldPad txtCodAlmox = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldFK txtDescAlmox = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
  private JTextFieldPad txtCodMarca = new JTextFieldPad(JTextFieldPad.TP_STRING,6,0);
  private JTextFieldFK txtDescMarca = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
  private JTextFieldFK txtSiglaMarca = new JTextFieldFK(JTextFieldPad.TP_STRING,20,0);
  private JTextFieldPad txtCodGrup = new JTextFieldPad(JTextFieldPad.TP_STRING,14,0);
  private JTextFieldFK txtDescGrup = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
  private JLabelPad lbOrdem = new JLabelPad("Ordenar por:");
  private JRadioGroup rgOrdem = null;
  private JLabelPad lbOpcao = new JLabelPad("Opção ");
  private JRadioGroup rgOpcao = null;
  private Vector vLbOrdem = new Vector();
  private Vector vVlrOrdem = new Vector();
  private Vector vLbOpcao = new Vector();
  private Vector vVlrOpcao = new Vector();
  private ListaCampos lcAlmox = new ListaCampos(this);
  private ListaCampos lcGrup = new ListaCampos(this);
  private ListaCampos lcMarca = new ListaCampos(this);
  private boolean[] bPrefs = null;
//  private int iCodAlmox = 0;
//  private String sOrdem = "";
//  private String sMarca = "";
//  private String sGrupo = "";
//  private String sWhere = "";
  
  public FRCustoProducao() {
    setTitulo("Inventário");
    setAtribos(80,30,365,320);
    
    GregorianCalendar cal = new GregorianCalendar();
    cal.add(Calendar.DATE,0);
    txtData.setVlrDate(cal.getTime());
        
    vLbOrdem.addElement("Código");
    vLbOrdem.addElement("Descrição");
    vVlrOrdem.addElement("C");
    vVlrOrdem.addElement("D");
	rgOrdem = new JRadioGroup(1,2,vLbOrdem,vVlrOrdem);
	rgOrdem.setVlrString("D");
	
	vLbOpcao.addElement("detalhado");
	vLbOpcao.addElement("resumido");
	vVlrOpcao.addElement("D");
	vVlrOpcao.addElement("R");
	rgOpcao = new JRadioGroup(1,2,vLbOpcao,vVlrOpcao);
	rgOpcao.setVlrString("R");
	
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
    
    adic(lbOpcao,7,0,100,20);
    adic(rgOpcao,7,20,200,30);
    adic(new JLabelPad("Estoque de:"),223,0,115,20);
    adic(txtData,223,20,115,20);
    adic(new JLabelPad("Cód.almox."),7,60,80,20);
    adic(txtCodAlmox,7,80,80,20);
    adic(new JLabelPad("Descrição do almoxarifado"),90,60,250,20);
    adic(txtDescAlmox,90,80,250,20);
    adic(new JLabelPad("Cód.marca"),7,100,100,20);
    adic(txtCodMarca,7,120,80,20);
    adic(new JLabelPad("Descrição da marca"),90,100,250,20);
    adic(txtDescMarca,90,120,250,20);
    adic(new JLabelPad("Cód.grupo"),7,140,80,20);
    adic(txtCodGrup,7,160,80,20);
    adic(new JLabelPad("Descrição do grupo"),90,140,250,20);
    adic(txtDescGrup,90,160,250,20);    
	adic(lbOrdem,7,185,80,15);
	adic(rgOrdem,7,205,333,30);    
    
  }

  
  public void imprimir(boolean bVisualizar) {
	  String sSql = "";
	  	String sCpCodigo = "";
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
	  		sCodMarca = txtCodMarca.getVlrString().trim();
	  		sCodGrup = txtCodGrup.getVlrString().trim();
	  		//iCodAlmox = txt
	  		
	  		sSql = "SELECT "+sCpCodigo+",DESCPROD,SLDPROD,CUSTOUNIT,CUSTOTOT FROM PPRELCUSTOSP(?,?,?,?,?,?,?,?,?,?,?,?,?) " 
			     + " WHERE SLDPROD!=0 "
			     + " ORDER BY "+(rgOrdem.getVlrString().equals("D")?"DESCPROD":sCpCodigo);
	  		
	  		try {
	  			
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
	  			ps.setString(10,"M");
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
	  			
	  			imp.montaCab();
				imp.setTitulo("Relatorio de inventário de estoque");
	  			imp.addSubTitulo(sFiltros1);
	  			imp.addSubTitulo(sFiltros2);
	  			
	  			while ( rs.next() ) {
	  				if (imp.pRow()>=(linPag-1)) {
	  					imp.say(imp.pRow()+1,0,""+imp.comprimido());
	  					imp.say(imp.pRow()+0,0,"+"+Funcoes.replicate("-",133)+"+");
	  					imp.incPags();
	  					imp.eject();

	  				}
		  			if (imp.pRow()==0) {	  				
		  				imp.impCab(136, true);
		  					  				
						imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",133)+"|");
						imp.say(imp.pRow()+1,0,""+imp.comprimido());
						imp.say(imp.pRow()+0,0,"| CODIGO");
						imp.say(imp.pRow()+0,16,"| DESCRICAO ");
						imp.say(imp.pRow()+0,70,"| SALDO");
						imp.say(imp.pRow()+0,83,"| CUSTO UNIT.");
						imp.say(imp.pRow()+0,101,"| CUSTO TOTAL");
						imp.say(imp.pRow()+0,135,"|");
						imp.say(imp.pRow()+1,0,""+imp.comprimido());
						imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",133)+"|");
						
					}
					
	  				imp.say(imp.pRow()+1,0,""+imp.comprimido());
	  				imp.say(imp.pRow()+0,0,"|"+Funcoes.adicEspacosEsquerda(rs.getString(sCpCodigo).trim(),13));
	  				imp.say(imp.pRow()+0,16,"| "+Funcoes.adicionaEspacos(rs.getString("DESCPROD"),50));
	  				imp.say(imp.pRow()+0,70,"|"+Funcoes.adicEspacosEsquerda(rs.getDouble("SLDPROD")+"",10));
	  				imp.say(imp.pRow()+0,83,"|"+Funcoes.strDecimalToStrCurrency(15,2,rs.getDouble("CUSTOUNIT")+""));
	  				imp.say(imp.pRow()+0,101,"|"+Funcoes.strDecimalToStrCurrency(15,2,rs.getDouble("CUSTOTOT")+""));
	  				imp.say(imp.pRow()+0,135,"|");
	  				deSldProd += rs.getDouble("SLDPROD");
	  				deCustoTot += rs.getDouble("CUSTOTOT");
	  				
	  			}
	  			
	  			rs.close();
	  			ps.close();
	  			if (!con.getAutoCommit())
	  				con.commit();
	  			imp.say(imp.pRow()+1,0,""+imp.comprimido());
	  			imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",133)+"|");
				imp.say(imp.pRow()+1,0,""+imp.comprimido());
	  			imp.say(imp.pRow()+0,0,"| TOTAL");
	  			imp.say(imp.pRow()+0,70,"|"+Funcoes.adicEspacosEsquerda(Funcoes.arredDouble(deSldProd,2)+"",10));
				imp.say(imp.pRow()+0,83,"|");
	  			imp.say(imp.pRow()+0,101,"|"+Funcoes.strDecimalToStrCurrency(15,2,deCustoTot+""));
	  			imp.say(imp.pRow()+0,135,"|");
	  			imp.say(imp.pRow()+1,0,""+imp.comprimido());
	  			imp.say(imp.pRow()+0,0,"+"+Funcoes.replicate("-",133)+"+");

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
  
  public void setConexao(Connection cn) {
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
      if (!con.getAutoCommit())
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
