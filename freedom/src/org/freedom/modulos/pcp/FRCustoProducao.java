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
  private int casasDecFin = Aplicativo.casasDecFin;
  //private JTextFieldPad txtData = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
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
  private int iCodAlmox = 0;
  private String sOrdem = "";
  private String sMarca = "";
  private String sGrupo = "";
  private String sWhere = "";
  
  public FRCustoProducao() {
    setTitulo("Inventário");
    setAtribos(80,30,365,320);
        
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
   // adic(new JLabelPad("Estoque de:"),223,0,115,20);
   // adic(txtData,223,20,115,20);
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
  	String sCab = "";
  	PreparedStatement ps = null;
  	ResultSet rs = null;
  	ImprimeOS imp = null;
  	int linPag = 0;
  	int iparm;
  	int lin = 0;

	imp = new ImprimeOS("",con);
	linPag = imp.verifLinPag()-1;
	  		
	try {

		iCodAlmox = 0;
		sOrdem = "";
		sMarca = "";
		sGrupo = "";
		sWhere = "";
		
		if(rgOrdem.getVlrString().equals("C")){
			if(bPrefs[0])
				sOrdem = "P.REFPROD";
			else
				sOrdem = "P.CODPROD";
		}
		else
			sOrdem = "P.DESCPROD";

		if(txtCodAlmox.getVlrString().trim().length() > 0) {
			iCodAlmox = txtCodAlmox.getVlrInteger().intValue();
			sWhere += "AND P.CODALMOX=? ";
			sCab += "ALMOXARIFADO : " + txtCodAlmox.getVlrInteger().intValue();
		}
		
		if(txtCodGrup.getVlrString().trim().length() > 0) {
			sGrupo = txtCodGrup.getVlrString();
			sWhere += "AND P.CODGRUP=? ";
			sCab += (sCab.length()>0 ? " - " : "") + "GRUPO : " + txtCodGrup.getVlrString();
		}
		
		if(txtCodMarca.getVlrString().trim().length() > 0) {
			sMarca = txtCodMarca.getVlrString();
			sWhere += "AND P.CODMARCA=? ";
			sCab += (sCab.length()>0 ? " - " : "") + "MARCA : " + txtCodMarca.getVlrString();
		}

		sSql = "SELECT E.CODPROD, E.SEQEST, P.REFPROD, P.DESCPROD, P.CODUNID, E.QTDEST, P.CUSTOMPMPROD "
				+ "FROM EQPRODUTO P, PPESTRUTURA E "
				+ "WHERE E.CODPROD=P.CODPROD AND E.CODEMP=P.CODEMP AND E.CODFILIAL=P.CODFILIAL "
				+ "AND E.CODEMP=? AND E.CODFILIAL=? "
				+ sWhere
				+ "ORDER BY " + sOrdem;
		
		ps = con.prepareStatement(sSql); 
		iparm = 1;
		ps.setInt(iparm++,Aplicativo.iCodEmp);
		ps.setInt(iparm++,Aplicativo.iCodFilial);
		if( iCodAlmox > 0 )
			ps.setInt(iparm++,iCodAlmox);
		if( sGrupo.trim().length() > 0 )
			ps.setString(iparm++,sGrupo);
		if( sMarca.trim().length() > 0 )
			ps.setString(iparm++,sMarca);
		
		rs = ps.executeQuery();
		
		imp.limpaPags();
		
		imp.montaCab(1);
		imp.setTitulo("Relatorio de Custo de Produção");
		imp.addSubTitulo("- CUSTO DE PRODUÇÃO -");
		imp.addSubTitulo(sCab);
		while ( rs.next()) {
			if (imp.pRow()>=(linPag-1)) {
				imp.say(imp.pRow()+1,0,""+imp.comprimido());
				imp.say(imp.pRow()+0,0,"+"+Funcoes.replicate("-",133)+"+");
				imp.incPags();
				imp.eject();

			}
  			
			if(rgOpcao.getVlrString().equals("D")){
				if (imp.pRow()==0) {	  				
	  				imp.impCab(136, true);
	  			} 
				imp.say(imp.pRow()+lin,0,"|"+Funcoes.replicate("-",133)+"|");
				lin = 1;
				imp.say(imp.pRow()+1,0,""+imp.comprimido());
				imp.say(imp.pRow()+0,0,"| "+(bPrefs[0] ? "REFERENCIA" : "CODIGO"));
				imp.say(imp.pRow()+0,16,"| DESCRIÇÃO DO PRODUTO");
				imp.say(imp.pRow()+0,90,"| UND");
				imp.say(imp.pRow()+0,100,"| QUANTIDADE");
				imp.say(imp.pRow()+0,118,"| CUSTO TOTAL");
				imp.say(imp.pRow()+0,135,"|");
				imp.say(imp.pRow()+1,0,""+imp.comprimido());
				imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",133)+"|");
				imp.say(imp.pRow()+1,0,""+imp.comprimido());
				imp.say(imp.pRow()+0,0,"| "+(bPrefs[0] ? rs.getString("REFPROD") : rs.getString("CODPROD")));
				imp.say(imp.pRow()+0,16,"| "+rs.getString("DESCPROD"));
				imp.say(imp.pRow()+0,90,"| "+rs.getString("CODUNID"));
				imp.say(imp.pRow()+0,100,"| "+rs.getString("QTDEST"));
				imp.say(imp.pRow()+0,118,"| "+getCustoTotal(rs));
				//imp.say(imp.pRow()+0,120,"| "+rs.getString("CUSTOMPMPROD"));
				imp.say(imp.pRow()+0,135,"|");
				detalhado(imp, rs);
			}
			else {
				if (imp.pRow()==0) {	  				
	  				imp.impCab(136, true);
					imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",133)+"|");
					imp.say(imp.pRow()+1,0,""+imp.comprimido());
					imp.say(imp.pRow()+0,0,"| "+(bPrefs[0] ? "REFERENCIA" : "CODIGO"));
					imp.say(imp.pRow()+0,16,"| DESCRIÇÃO DO PRODUTO");
					imp.say(imp.pRow()+0,90,"| UND");
					imp.say(imp.pRow()+0,100,"| QUANTIDADE");
					imp.say(imp.pRow()+0,118,"| CUSTO TOTAL");
					imp.say(imp.pRow()+0,135,"|");
					imp.say(imp.pRow()+1,0,"|"+Funcoes.replicate("-",133)+"|");
				}
				imp.say(imp.pRow()+1,0,""+imp.comprimido());
				imp.say(imp.pRow()+0,0,"| "+(bPrefs[0] ? rs.getString("REFPROD") : rs.getString("CODPROD")));
				imp.say(imp.pRow()+0,16,"| "+rs.getString("DESCPROD"));
				imp.say(imp.pRow()+0,90,"| "+rs.getString("CODUNID"));
				imp.say(imp.pRow()+0,100,"| "+rs.getString("QTDEST"));
				imp.say(imp.pRow()+0,118,"| "+getCustoTotal(rs));
				//imp.say(imp.pRow()+0,120,"| "+rs.getString("CUSTOMPMPROD"));
				imp.say(imp.pRow()+0,135,"|");
			}
		}
		
		imp.say(imp.pRow()+1,0,""+imp.comprimido());
		imp.say(imp.pRow()+0,0,"+"+Funcoes.replicate("-",133)+"+");
		
		rs.close();
		ps.close();
		if (!con.getAutoCommit())
			con.commit();

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
  
  private void detalhado(ImprimeOS imp, ResultSet rsP) {
	  String sSQL = null;
	  ResultSet rs = null;
	  PreparedStatement ps = null;
	  int linPag = linPag = imp.verifLinPag()-1;
	  int iparm = 1;
	  int cont = 0;
	  try{
		  sSQL = "SELECT IE.CODPRODPD, P.REFPROD, P.DESCPROD, P.CODUNID, IE.QTDITEST, P.CUSTOMPMPROD "
				+ "FROM EQPRODUTO P, PPITESTRUTURA IE "
				+ "WHERE IE.CODPRODPD=P.CODPROD AND IE.CODEMPPD=P.CODEMP AND IE.CODFILIALPD=P.CODFILIAL "
				+ "AND IE.CODEMP=? AND IE.CODFILIAL=? AND IE.CODPROD=? AND IE.SEQEST=? "
				+ sWhere
				+ "ORDER BY " + sOrdem;
		  ps = con.prepareStatement(sSQL);
		  ps.setInt(iparm++,Aplicativo.iCodEmp);
		  ps.setInt(iparm++,Aplicativo.iCodFilial);
		  ps.setInt(iparm++,rsP.getInt("CODPROD"));
		  ps.setInt(iparm++,rsP.getInt("SEQEST"));
		  if( iCodAlmox > 0 )
			  ps.setInt(iparm++,iCodAlmox);
		  if( sGrupo.trim().length() > 0 )
			  ps.setString(iparm++,sGrupo);
		  if( sMarca.trim().length() > 0 )
			  ps.setString(iparm++,sMarca);
		  rs = ps.executeQuery();
		  
		  while ( rs.next()) {
				if (imp.pRow()>=(linPag-1)) {
					imp.say(imp.pRow()+1,0,""+imp.comprimido());
					imp.say(imp.pRow()+0,0,"+"+Funcoes.replicate("-",133)+"+");
					imp.incPags();
					imp.eject();
				}
	  			if (cont==0) {	    					  				
	  				imp.say(imp.pRow()+1,0,"|"+Funcoes.replicate("-",133)+"|");
					imp.say(imp.pRow()+1,0,""+imp.comprimido());
					imp.say(imp.pRow()+0,0,"| ");
					imp.say(imp.pRow()+0,2,"SEQ.EST.");
					imp.say(imp.pRow()+0,16,"| "+(bPrefs[0] ? "RREFERENCIA" : "CODIGO"));
					imp.say(imp.pRow()+0,32,"| DESCRIÇÃO DO PRODUTO");
					imp.say(imp.pRow()+0,90,"| UND");
					imp.say(imp.pRow()+0,100,"| QUANTIDADE");
					imp.say(imp.pRow()+0,118,"| CUSTO");
					imp.say(imp.pRow()+0,135,"|");
					cont++;				
				}
								
				imp.say(imp.pRow()+1,0,""+imp.comprimido());
				imp.say(imp.pRow()+0,0,"| ");
				
				if(cont==1){
					imp.say(imp.pRow()+0,2,rsP.getString("SEQEST"));
					cont++;
				}
				
				imp.say(imp.pRow()+0,16,"| "+(bPrefs[0] ? (rs.getString("REFPROD")!=null ? rs.getString("REFPROD") : "") : (rs.getString("CODPRODPD")!=null ? rs.getString("CODPRODPD") : "")));
				imp.say(imp.pRow()+0,32,"| "+rs.getString("DESCPROD"));
				imp.say(imp.pRow()+0,90,"| "+rs.getString("CODUNID"));
				imp.say(imp.pRow()+0,100,"| "+rs.getString("QTDITEST"));
				imp.say(imp.pRow()+0,118,"| "+Funcoes.strDecimalToStrCurrency(10,casasDecFin,rs.getString("CUSTOMPMPROD")));
				imp.say(imp.pRow()+0,135,"|");
		  }
		  		  
	  }
	  catch (SQLException e) {
		  Funcoes.mensagemErro(this,"Erro ao montar detalhado.\n"+e.getMessage());
		  e.printStackTrace();
	  }
  }
  
  public String getCustoTotal(ResultSet rsP){
	  String ret = "";
	  String sSQL = null;
	  ResultSet rs = null;
	  PreparedStatement ps = null;
	  float fCustoTotal = 0f;
	  int iparm = 1;
	  try{
		  sSQL = "SELECT P.CUSTOMPMPROD "
				+ "FROM EQPRODUTO P, PPITESTRUTURA IE "
				+ "WHERE IE.CODPRODPD=P.CODPROD AND IE.CODEMPPD=P.CODEMP AND IE.CODFILIALPD=P.CODFILIAL "
				+ "AND IE.CODEMP=? AND IE.CODFILIAL=? AND IE.CODPROD=? AND IE.SEQEST=? "
				+ sWhere
				+ "ORDER BY " + sOrdem;
		  ps = con.prepareStatement(sSQL);
		  ps.setInt(iparm++,Aplicativo.iCodEmp);
		  ps.setInt(iparm++,Aplicativo.iCodFilial);
		  ps.setInt(iparm++,rsP.getInt("CODPROD"));
		  ps.setInt(iparm++,rsP.getInt("SEQEST"));
		  if( iCodAlmox > 0 )
			  ps.setInt(iparm++,iCodAlmox);
		  if( sGrupo.trim().length() > 0 )
			  ps.setString(iparm++,sGrupo);
		  if( sMarca.trim().length() > 0 )
			  ps.setString(iparm++,sMarca);
		  rs = ps.executeQuery();
		  
		  while ( rs.next()) {
			  fCustoTotal = fCustoTotal + rs.getFloat("CUSTOMPMPROD");				
		  }
		  
		  ret = Funcoes.strDecimalToStrCurrency(10,casasDecFin,""+fCustoTotal);
		  
	  }
	  catch (SQLException e) {
		  Funcoes.mensagemErro(this,"Erro ao montar detalhado.\n"+e.getMessage());
		  e.printStackTrace();
	  }
	  return ret;
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
