/**
 * @version 03/08/2004 <BR>
 * @author Setpoint Informática Ltda./Robson Sanchez <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FRInvPeps.java <BR>
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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;

import javax.swing.JLabel;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FRelatorio;

public class FRInvPeps extends FRelatorio {

  private final int TAM_GRUPO = 14;
  
  private Connection con;
  private JTextFieldPad txtData = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
  private JTextFieldPad txtPagina = new JTextFieldPad(JTextFieldPad.TP_INTEGER,6,0);
  private JTextFieldPad txtCodMarca = new JTextFieldPad(JTextFieldPad.TP_STRING,6,0);
  private JTextFieldFK txtDescMarca = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
  private JTextFieldFK txtSiglaMarca = new JTextFieldFK(JTextFieldPad.TP_STRING,20,0);
  private JTextFieldPad txtCodGrup = new JTextFieldPad(JTextFieldPad.TP_STRING,TAM_GRUPO,0);
  private JTextFieldFK txtDescGrup = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
  private JCheckBoxPad cbSemEstoq = new JCheckBoxPad("Imprimir produtos sem estoque?","S","N");

  private ListaCampos lcGrup = new ListaCampos(this);
  private ListaCampos lcMarca = new ListaCampos(this);

  private JLabel lbCodMarca = new JLabel("Cód.marca");
  private JLabel lbCodGrup = new JLabel("Cód.grupo");
  private JLabel lbDescMarca = new JLabel("Descrição da marca");
  private JLabel lbDescGrup = new JLabel("Descrição do grupo");
  
  private JRadioGroup rgOrdem = null;
  private Vector vDesc = new Vector();
  private Vector vOpc = new Vector();
  
  private JRadioGroup rgCusto = null;
  private Vector vDescCusto = new Vector();
  private Vector vOpcCusto = new Vector();
  private boolean[] bPrefs = null;
  
  public FRInvPeps() {
    setTitulo("Inventário");
    setAtribos(80,30,400,300);
    
    GregorianCalendar cal = new GregorianCalendar();
    cal.add(Calendar.DATE,0);
    txtData.setVlrDate(cal.getTime());
    txtPagina.setVlrInteger(new Integer(1));

    vDesc.addElement("Descrição");
    vDesc.addElement("Código");
    vOpc.addElement("D");
    vOpc.addElement("C");
    rgOrdem = new JRadioGroup(2,1,vDesc,vOpc);
    
    vDescCusto.addElement("C.PEPS");
    vDescCusto.addElement("C.MPM");
    vDescCusto.addElement("P.BASE");
    vOpcCusto.addElement("P");
    vOpcCusto.addElement("M");
    vOpcCusto.addElement("B");
    
    rgCusto = new JRadioGroup(1,3,vDescCusto,vOpcCusto);
    

    cbSemEstoq.setVlrString("N");
    
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
    
    adic(new JLabel("Ordem:"),7,0,100,20);
    adic(rgOrdem,7,20,100,60);
    adic(new JLabel("Estoque de:"),117,0,100,20);
    adic(txtData,117,20,100,20);
    adic(new JLabel("Página inicial:"),117,40,100,20);
    adic(txtPagina,117,60,100,20);
    adic(lbCodMarca,7,80,250,20);
    adic(txtCodMarca,7,100,80,20);
    adic(lbDescMarca,90,80,250,20);
    adic(txtDescMarca,90,100,250,20);
    adic(lbCodGrup,7,120,250,20);
    adic(txtCodGrup,7,140,80,20);
    adic(lbDescGrup,90,120,250,20);
    adic(txtDescGrup,90,140,250,20);
    adic(cbSemEstoq,7,160,250,20);
    adic(rgCusto,7,180,250,30);
    
    
  }

  
  public void imprimir(boolean bVisualizar) {
  	String sSql = "";
  	String sCpCodigo = "";
  	String sSemEstoq = "";
  	String sCodMarca = "";
  	String sCodGrup = "";
  	String sFiltros = "";
  	ImprimeOS imp = null;
  	int linPag = 0;
  	int iPagina = 0;
  	PreparedStatement ps = null;
  	ResultSet rs = null;
  	double deCustoTot = 0;
  	double deSldProd = 0;

  	try {
  		
  		imp = new ImprimeOS("",con);
  		linPag = imp.verifLinPag()-1;
 		iPagina = txtPagina.getVlrInteger().intValue()-1;
  		imp.setTitulo("Relatorio de inventário de estoque");
  		
  		sCpCodigo = (bPrefs[0]?"REFPROD":"CODPROD");
  		sSemEstoq = cbSemEstoq.getVlrString();
  		sCodMarca = txtCodMarca.getVlrString().trim();
  		sCodGrup = txtCodGrup.getVlrString().trim();
  		
  		sSql = "SELECT "+sCpCodigo+",DESCPROD,SLDPROD,CUSTOUNIT,CUSTOTOT FROM EQRELPEPSSP(?,?,?,?,?,?,?,?,?,?) " +
		       (sSemEstoq.equals("N")?" WHERE SLDPROD!=0 ":"")+
  				"ORDER BY "+(rgOrdem.getVlrString().equals("D")?"DESCPROD":sCpCodigo);
  		try {
  			if (sSemEstoq.equals("S")) 
  				sFiltros = "INCLUI PRODUTOS SEM ESTOQUE";
  			else 
  				sFiltros = "SOMENTE PRODUTOS COM ESTOQUE";
  			ps = con.prepareStatement(sSql);
  			ps.setInt(1,Aplicativo.iCodEmp);
  			ps.setInt(2,ListaCampos.getMasterFilial("EQPRODUTO"));
  			ps.setDate(3,Funcoes.dateToSQLDate(txtData.getVlrDate()));
  			if (sCodMarca.equals("")) {
  	  			ps.setNull(4,Types.INTEGER);
  	  			ps.setNull(5,Types.SMALLINT);
  	  			ps.setNull(6,Types.CHAR);
  			}
  			else {
  	  			ps.setInt(4,Aplicativo.iCodEmp);
  	  			ps.setInt(5,ListaCampos.getMasterFilial("EQMARCA"));
  	  			ps.setString(6,sCodMarca);
  	  			sFiltros += " / MARCA: "+sCodMarca+"-"+txtDescMarca.getVlrString().trim();
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
  	  			sFiltros += " / GRUPO: "+sCodGrup+"-"+txtDescGrup.getVlrString().trim();
  			}
  			ps.setString(10,rgCusto.getVlrString());
  			rs = ps.executeQuery();
  			
  			imp.limpaPags();
  			
  			while ( rs.next() ) {
  				if (imp.pRow()>=(linPag-1)) {
  					imp.say(imp.pRow()+1,0,""+imp.comprimido());
  					imp.say(imp.pRow()+0,0,"+"+Funcoes.replicate("-",133)+"+");
  					imp.incPags();
  					imp.eject();

  				}
	  			if (imp.pRow()==0) {
	  				imp.say(imp.pRow()+1,0,""+imp.comprimido());
	  				imp.say(imp.pRow()+0,1,"+"+Funcoes.replicate("-",133)+"+");
	  				imp.say(imp.pRow()+1,0,""+imp.comprimido());
	  				imp.say(imp.pRow()+0,1,"| Emitido em :"+Funcoes.dateToStrDate(new Date()));
	  				imp.say(imp.pRow()+0,120,"Pagina : "+(imp.getNumPags()+iPagina));
	  				imp.say(imp.pRow()+0,136,"|");
	  				imp.say(imp.pRow()+1,0,""+imp.comprimido());
	  				imp.say(imp.pRow()+0,1,"|");
	  				imp.say(imp.pRow()+0,45,"RELATORIO DE INVENTARIO DE ESTOQUE - ESTOQUE DE: "+txtData.getVlrString());
	  				imp.say(imp.pRow()+0,136,"|");
  	  				imp.say(imp.pRow()+1,0,""+imp.comprimido());
  	  				imp.say(imp.pRow()+0,1,"|");
  	  				imp.say(imp.pRow()+0,65-(sFiltros.length()/2),sFiltros);
  	  				imp.say(imp.pRow()+0,136,"|");
  	  				imp.say(imp.pRow()+1,0,""+imp.comprimido());
					imp.say(imp.pRow()+0,1,"+"+Funcoes.replicate("-",133)+"+");
					imp.say(imp.pRow()+1,0,""+imp.comprimido());
					imp.say(imp.pRow()+0,1,"| CODIGO");
					imp.say(imp.pRow()+0,16,"| DESCRICAO ");
					imp.say(imp.pRow()+0,70,"| SALDO");
					imp.say(imp.pRow()+0,83,"| CUSTO UNIT.");
					imp.say(imp.pRow()+0,101,"| CUSTO TOTAL");
					imp.say(imp.pRow()+0,136,"|");
					imp.say(imp.pRow()+1,0,""+imp.comprimido());
					imp.say(imp.pRow()+0,1,"+"+Funcoes.replicate("-",133)+"+");
					
				}
				
  				imp.say(imp.pRow()+1,0,""+imp.comprimido());
  				imp.say(imp.pRow()+0,1,"|"+Funcoes.adicEspacosEsquerda(rs.getString(sCpCodigo).trim(),13));
  				imp.say(imp.pRow()+0,16,"| "+Funcoes.adicionaEspacos(rs.getString("DESCPROD"),50));
  				imp.say(imp.pRow()+0,70,"|"+Funcoes.adicEspacosEsquerda(rs.getDouble("SLDPROD")+"",10));
  				imp.say(imp.pRow()+0,83,"|"+Funcoes.strDecimalToStrCurrency(15,2,rs.getDouble("CUSTOUNIT")+""));
  				imp.say(imp.pRow()+0,101,"|"+Funcoes.strDecimalToStrCurrency(15,2,rs.getDouble("CUSTOTOT")+""));
  				imp.say(imp.pRow()+0,136,"|");
  				deSldProd += rs.getDouble("SLDPROD");
  				deCustoTot += rs.getDouble("CUSTOTOT");
  				
  			}
  			
  			rs.close();
  			ps.close();
  			if (!con.getAutoCommit())
  				con.commit();
  			imp.say(imp.pRow()+1,0,""+imp.comprimido());
  			imp.say(imp.pRow()+0,1,"+"+Funcoes.replicate("-",133)+"+");
			imp.say(imp.pRow()+1,0,""+imp.comprimido());
  			imp.say(imp.pRow()+0,1,"| TOTAL");
  			imp.say(imp.pRow()+0,70,"|"+Funcoes.adicEspacosEsquerda(Funcoes.arredDouble(deSldProd,2)+"",10));
			imp.say(imp.pRow()+0,83,"|");
  			imp.say(imp.pRow()+0,101,"|"+Funcoes.strDecimalToStrCurrency(15,2,deCustoTot+""));
  			imp.say(imp.pRow()+0,136,"|");
  			imp.say(imp.pRow()+1,0,""+imp.comprimido());
  			imp.say(imp.pRow()+0,1,"+"+Funcoes.replicate("-",133)+"+");

  			imp.eject();
  			imp.fechaGravacao();
  			
  		}
  		catch (SQLException e) {
  			Funcoes.mensagemErro(this,"Erro executando a consulta.\n"+e.getMessage());
  			e.printStackTrace();
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
  	  	sFiltros = null;
  		imp = null;
  		ps = null;
  		rs = null;
  		deCustoTot = 0;
  		deSldProd = 0;
  	  	linPag = 0;
  		iPagina = 0;
  	}

  }
  
  public void setConexao(Connection cn) {
    con = cn;
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
		Funcoes.mensagemErro(this,"Erro ao carregar a tabela PREFERE1!\n"+err.getMessage());
    }
    finally {
    	sSQL = null;
    	ps = null;
    	rs = null;
    }
    return bRetorno;
  }

}
