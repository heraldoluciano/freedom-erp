/**
 * @version 21/02/2005 <BR>
 * @author Setpoint Inform�tica Ltda./Robson Sanchez <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FRBordero.java <BR>
 * 
 * Este programa � licenciado de acordo com a LPG-PC (Licen�a P�blica Geral para Programas de Computador), <BR>
 * vers�o 2.1.0 ou qualquer vers�o posterior. <BR>
 * A LPG-PC deve acompanhar todas PUBLICA��ES, DISTRIBUI��ES e REPRODU��ES deste Programa. <BR>
 * Caso uma c�pia da LPG-PC n�o esteja dispon�vel junto com este Programa, voc� pode contatar <BR>
 * o LICENCIADOR ou ent�o pegar uma c�pia em: <BR>
 * Licen�a: http://www.lpg.adv.br/licencas/lpgpc.rtf <BR>
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa � preciso estar <BR>
 * de acordo com os termos da LPG-PC <BR> <BR>
 *
 * Coment�rios sobre a classe...
 * 
 */

package org.freedom.modulos.std;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.swing.JLabel;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FRelatorio;

public class FRBordero extends FRelatorio {
  private JTextFieldPad txtDataini = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0); 
  private JTextFieldPad txtDatafim = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0); 
  private JTextFieldPad txtCodBanco = new JTextFieldPad(JTextFieldPad.TP_STRING,3,0);
  private JTextFieldFK txtNomeBanco = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
  private JTextFieldPad txtCodSetor = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldFK txtDescSetor = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
  private JTextFieldPad txtCodVend = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldFK txtNomeVend = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
  
  private Connection con = null;
  private ListaCampos lcBanco = new ListaCampos(this);
  private ListaCampos lcSetor = new ListaCampos(this);
  private ListaCampos lcVendedor = new ListaCampos(this);
  private boolean[] bPref = null;
  
  public FRBordero() {
    setTitulo("Bordero de cobran�a");
    setAtribos(80,0,400,300);
   
    txtDataini.setVlrDate(new Date());
    txtDatafim.setVlrDate(new Date());

    txtCodBanco.setRequerido(true);
	lcBanco.add(new GuardaCampo( txtCodBanco, "CodBanco", "C�d.banco", ListaCampos.DB_PK, false));
	lcBanco.add(new GuardaCampo( txtNomeBanco, "NomeBanco", "Nome do banco", ListaCampos.DB_SI, false));
	lcBanco.montaSql(false, "BANCO", "FN");
	lcBanco.setReadOnly(true);
	txtCodBanco.setTabelaExterna(lcBanco);
	txtCodBanco.setFK(true);
	txtCodBanco.setNomeCampo("CodBanco");

    lcSetor.add(new GuardaCampo( txtCodSetor,"CodSetor","C�d.setor",ListaCampos.DB_PK,false ));
    lcSetor.add(new GuardaCampo( txtDescSetor,"DescSetor","Descri��o do setor",ListaCampos.DB_SI,false ));
    lcSetor.montaSql(false,"SETOR","VD");
    lcSetor.setReadOnly(true);
    txtCodSetor.setTabelaExterna(lcSetor);
    txtCodSetor.setFK(true);
    txtCodSetor.setNomeCampo("CodSetor");

    lcVendedor.add(new GuardaCampo( txtCodVend,"CodVend","C�d.repr.",ListaCampos.DB_PK, false ));
    lcVendedor.add(new GuardaCampo( txtNomeVend,"NomeVend","Nome do representante",ListaCampos.DB_SI, false ));
    lcVendedor.montaSql(false,"VENDEDOR","VD");
    lcVendedor.setReadOnly(true);
    txtCodVend.setTabelaExterna(lcVendedor);
    txtCodVend.setFK(true);
    txtCodVend.setNomeCampo("CodVend");

    adic(new JLabel("Periodo:"),7,5,120,20);
    adic(new JLabel("De:"),7,25,30,20);
    adic(txtDataini,40,25,97,20);
    adic(new JLabel("At�:"),160,25,30,20);
    adic(txtDatafim,190,25,100,20);
    adic(new JLabel("C�d.banco"),7,45,80,20);
    adic(new JLabel("Nome do banco"),90,45,250,20);
	adic(txtCodBanco,7,65,80,20);
	adic(txtNomeBanco,90,65,250,20);
    adic(new JLabel("C�d.setor"),7,85,80,20);
    adic(new JLabel("Descri��o do setor"),90,85,250,20);
    adic(txtCodSetor,7,105,80,20);
    adic(txtDescSetor,90,105,250,20);
    adic(new JLabel("C�d.repr."),7,125,80,20);
    adic(new JLabel("Nome do repres."),90,125,250,20);
    adic(txtCodVend,7,145,80,20);
    adic(txtNomeVend,90,145,250,20);
    
  }
  public void setConexao(Connection cn) {
    con = cn;
    lcBanco.setConexao(cn);
    lcSetor.setConexao(cn);
    lcVendedor.setConexao(cn);
    bPref = getPrefere();
  }

  public void imprimir(boolean bVisualizar) {
  	ImprimeOS imp = null;
  	int linPag = 0;
  	String sCodBanco = null;
  	int iCodSetor = 0;
  	int iCodVend = 0;
  	int iCodCli = 0;
  	int iParans = 0;
	String sFiltro = "";
	String sTitulo = "";
	String sDataini = "";
	String sDatafim = "";
	String sDtVencItRec = "";
	String sDtPago = "";
	String sSQL = "";
	String sWhere = "";
	String sFrom = "";
	String sImpTotDia = "";
    double deTotParc = 0;
	PreparedStatement ps = null;
	ResultSet rs = null;
  	try {
  		if (txtDatafim.getVlrDate().before(txtDataini.getVlrDate())) {
  			Funcoes.mensagemInforma(this,"Data final maior que a data inicial!");
  			return;
  		}
  		imp = new ImprimeOS("",con);
  		linPag = imp.verifLinPag()-1;
   
  		sCodBanco = txtCodBanco.getVlrString().trim();
  		iCodSetor = txtCodSetor.getVlrInteger().intValue();
  		iCodVend = txtCodVend.getVlrInteger().intValue();
  		
  		if (sCodBanco.equals("")) {
  		    Funcoes.mensagemInforma(this,"Selecione um banco \"portador\"!");
  		    txtCodBanco.requestFocus();
  		    return;
  		}
    
  		imp.montaCab();
    
  		sDataini = txtDataini.getVlrString();
  		sDatafim = txtDatafim.getVlrString();
    
  		imp.setTitulo("Bordero de cobran�a");
  		
       	sWhere += " AND IT.CODEMPBO=? AND IT.CODFILIALBO=? AND IT.CODBANCO=? ";
        sFiltro = "Banco: "+sCodBanco+" - "+Funcoes.copy(txtNomeBanco.getVlrString(),30).trim();
        	
        if (iCodSetor!=0) {
        	if (bPref[0]) {
        		sWhere += " AND C.CODEMPSR=? AND C.CODFILIALSR=? AND C.CODSETOR=?";
        	}
        	else {
        		sWhere += " AND VD.CODEMPSE=? AND VD.CODFILIALSE=? AND VD.CODSETOR=? AND " +
        				" VD.CODEMP=R.CODEMPVD AND VD.CODFILIAL=R.CODFILIALVD AND VD.CODVEND=R.CODVEND ";
        		sFrom = ",VDVENDEDOR VD ";
        	}
        	sFiltro += (!sFiltro.equals("")?" / ":"")+"Setor: "+iCodSetor+" - "+Funcoes.copy(txtDescSetor.getVlrString(),30).trim();
        }
        if (iCodVend!=0) {
        	sWhere += " AND R.CODEMPVD=? AND R.CODFILIALVD=? AND R.CODVEND=? ";
        	sFiltro += (!sFiltro.equals("")?" / ":"")+"Repr.: "+iCodVend+" - "+Funcoes.copy(txtNomeVend.getVlrString(),30).trim();
        }
  		
  		sSQL = "SELECT IT.DTVENCITREC,IT.NPARCITREC,R.CODVENDA,"+
                  "R.CODCLI,C.RAZCLI, C.CNPJCLI, " +
                  "C.ENDCOB, C.NUMCOB, C.BAIRCOB, C.CIDCOB, " +
                  "C.UFCOB, C.CEPCOB, " +
                  "C.ENDCLI, C.NUMCLI, C.BAIRCLI, C.CIDCLI, " +
                  "C.UFCLI, C.CEPCLI, " +
                  "IT.VLRPARCITREC, IT.DTPAGOITREC, R.DATAREC, "+
                  "(SELECT V.STATUSVENDA FROM VDVENDA V "+
                  "WHERE V.FLAG IN "+
                  Aplicativo.carregaFiltro(con,org.freedom.telas.Aplicativo.iCodEmp)+
                  " AND V.CODEMP = R.CODEMPVA AND V.CODFILIAL=R.CODFILIALVA AND V.CODVENDA=R.CODVENDA)," +
                  "R.DOCREC "+
                  " FROM FNITRECEBER IT,FNRECEBER R,VDCLIENTE C"+sFrom+
                  " WHERE R.FLAG IN "+
                  Aplicativo.carregaFiltro(con,org.freedom.telas.Aplicativo.iCodEmp)+
                  " AND R.CODEMP=? AND R.CODFILIAL=? AND IT.DTVENCITREC BETWEEN ? AND ? AND"+
                  " IT.STATUSITREC IN (?,?) AND R.CODREC = IT.CODREC" +
                  " AND IT.CODEMP=R.CODEMP AND IT.CODFILIAL=R.CODFILIAL" +
                  " AND C.CODEMP = R.CODEMPCL AND C.CODFILIAL=R.CODFILIALCL AND C.CODCLI=R.CODCLI "+
				  sWhere+" ORDER BY C.RAZCLI, R.DOCREC, IT.NPARCITREC";
                  
  		try {
  			ps = con.prepareStatement(sSQL);
  			ps.setInt(1,Aplicativo.iCodEmp);
  			ps.setInt(2,ListaCampos.getMasterFilial("FNRECEBER"));
  			ps.setDate(3,Funcoes.dateToSQLDate(txtDataini.getVlrDate()));
  			ps.setDate(4,Funcoes.dateToSQLDate(txtDatafim.getVlrDate()));
 			ps.setString(5,"R1");
  			ps.setString(6,"RL");
  			iParans = 7;
  			ps.setInt(iParans,Aplicativo.iCodEmp);
  			ps.setInt(iParans+1,ListaCampos.getMasterFilial("FNBANCO"));
  			ps.setString(iParans+2,sCodBanco);
  			iParans += 3;
  			if (iCodSetor!=0) {
  				ps.setInt(iParans,Aplicativo.iCodEmp);
  				ps.setInt(iParans+1,ListaCampos.getMasterFilial("VDSETOR"));
  				ps.setInt(iParans+2,iCodSetor);
  				iParans += 3;
  			}
  			if (iCodVend!=0) {
  				ps.setInt(iParans,Aplicativo.iCodEmp);
  				ps.setInt(iParans+1,ListaCampos.getMasterFilial("VDVENDEDOR"));
  				ps.setInt(iParans+2,iCodVend);
  				iParans += 3;
  			}

  			rs = ps.executeQuery();
  			imp.limpaPags();
      
  			while ( rs.next() ) {

  				if (imp.pRow()>=(linPag-1)) {
  					imp.say(imp.pRow()+1,0,""+imp.comprimido());
  					imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",134)+"|");
  					imp.incPags();
  					imp.eject();
  				}

  				if (imp.pRow()==0) {
  					sTitulo = "BORDERO DE COBRANCA  - PERIODO DE :"+sDataini+" ATE: "+sDatafim;
  					imp.say(imp.pRow()+1,0,""+imp.comprimido());
  					imp.say(imp.pRow()+0,0,"+"+Funcoes.replicate("-",134)+"+");
  					imp.say(imp.pRow()+1,0,""+imp.comprimido());
  					imp.say(imp.pRow()+0,0,"|   Emitido em :"+Funcoes.dateToStrDate(new Date()));
  					imp.say(imp.pRow()+0,120,"Pagina : "+(imp.getNumPags()));
  					imp.say(imp.pRow()+0,136,"|");
  					imp.say(imp.pRow()+1,0,""+imp.comprimido());
  					imp.say(imp.pRow()+0,0,"|");
  					imp.say(imp.pRow()+0,(136-sTitulo.length())/2,sTitulo);
  					imp.say(imp.pRow()+0,136,"|");
  					imp.say(imp.pRow()+1,0,""+imp.comprimido());
  					imp.say(imp.pRow()+0,0,"|");
  					imp.say(imp.pRow()+0,2+((130-sFiltro.length())/2),sFiltro);
  					imp.say(imp.pRow()+0,136,"|");
  					imp.say(imp.pRow()+1,0,""+imp.comprimido());
  					imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",134)+"|");
  					imp.say(imp.pRow()+1,0,""+imp.comprimido());
  					imp.say(imp.pRow()+0,0,"|Raz�o social do cliente");
  					imp.say(imp.pRow()+0,43,"Cod.cli");
  					imp.say(imp.pRow()+0,53,"CNPJ");
  					imp.say(imp.pRow()+0,73,"Endereco");
  					imp.say(imp.pRow()+0,136,"|");
  					imp.say(imp.pRow()+1,0,""+imp.comprimido());
  					imp.say(imp.pRow()+0,0,"|");
  					imp.say(imp.pRow()+0,10,"Cidade");
  					imp.say(imp.pRow()+0,42,"UF");
  					imp.say(imp.pRow()+0,45,"Bairro");
  					imp.say(imp.pRow()+0,77,"C.E.P.");
  					imp.say(imp.pRow()+0,89,"N.dupl.");
  					imp.say(imp.pRow()+0,100,"Emissao");
  					imp.say(imp.pRow()+0,111,"Vencto.");
  					imp.say(imp.pRow()+0,122,"Valor");
  					imp.say(imp.pRow()+0,136,"|");
  					imp.say(imp.pRow()+1,0,""+imp.comprimido());
  					imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",134)+"|");
  				}
  				else if (iCodCli!=rs.getInt("CODCLI")) {
  					imp.say(imp.pRow()+1,0,""+imp.comprimido());
  					imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",134)+"|");
  				}
  				if (rs.getInt("CODCLI")!=iCodCli) { 
  				    imp.say(imp.pRow()+1,0,""+imp.comprimido());
  				    imp.say(imp.pRow()+0,0,"|"+Funcoes.copy(rs.getString("RAZCLI"),40));
  				    imp.say(imp.pRow()+0,43,Funcoes.alinhaDir(""+rs.getInt("CODCLI"),8));
  				    imp.say(imp.pRow()+0,53,Funcoes.setMascara(rs.getString("CNPJCLI"),"########/####-##"));
  				    if ( (rs.getString("ENDCOB")==null) || (rs.getString("ENDCOB").trim().equals("")) ) {
  				        imp.say(imp.pRow()+0,73,Funcoes.copy(rs.getString("ENDCLI"),50).trim()+", "+Funcoes.copy(rs.getString("NUMCLI"),8));
  				        imp.say(imp.pRow()+0,136,"|");
  				        imp.say(imp.pRow()+1,0,""+imp.comprimido());
  				        imp.say(imp.pRow()+0,0,"|");
  				        imp.say(imp.pRow()+0,10,Funcoes.copy(rs.getString("CIDCLI"),30));
  				        imp.say(imp.pRow()+0,42,Funcoes.copy(rs.getString("UFCLI"),2));
  				        imp.say(imp.pRow()+0,45,Funcoes.copy(rs.getString("BAIRCLI"),30));
  				        imp.say(imp.pRow()+0,77,Funcoes.setMascara(rs.getString("CEPCLI"),"#####-###"));
  				    }
  				    else {
  				        imp.say(imp.pRow()+0,73,Funcoes.copy(rs.getString("ENDCOB"),50).trim()+", "+Funcoes.copy(rs.getString("NUMCOB"),8));
  				        imp.say(imp.pRow()+0,136,"|");
  				        imp.say(imp.pRow()+1,0,""+imp.comprimido());
  				        imp.say(imp.pRow()+0,0,"|");
  				        imp.say(imp.pRow()+0,10,Funcoes.copy(rs.getString("CIDCOB"),30));
  				        imp.say(imp.pRow()+0,42,Funcoes.copy(rs.getString("UFCOB"),2));
  				        imp.say(imp.pRow()+0,45,Funcoes.copy(rs.getString("BAIRCOB"),30));
  				        imp.say(imp.pRow()+0,77,Funcoes.setMascara(rs.getString("CEPCOB"),"#####-###"));
  				    }
  				}
  				else { 
  				   imp.say(imp.pRow()+1,0,""+imp.comprimido());
  				   imp.say(imp.pRow()+0,0,"|");
  				}
  				imp.say(imp.pRow()+0,89,Funcoes.alinhaDir(rs.getString("DOCREC")+"-"+rs.getInt("NPARCITREC"),10));
  				imp.say(imp.pRow()+0,100,Funcoes.dateToStrDate(rs.getDate("DATAREC")));
  				imp.say(imp.pRow()+0,111,Funcoes.dateToStrDate(rs.getDate("DTVENCITREC")));
  				imp.say(imp.pRow()+0,122,Funcoes.strDecimalToStrCurrency(12,2,rs.getString("VLRPARCITREC")));
  				imp.say(imp.pRow()+0,136,"|");

  				if (rs.getString("VlrParcItRec") != null) {
  					deTotParc += rs.getDouble("VlrParcItRec");
  				}
  				iCodCli = rs.getInt("CODCLI");
        
 			}

  			imp.say(imp.pRow()+1,0,""+imp.comprimido());
  			imp.say(imp.pRow(),0,"|"+Funcoes.replicate("-",134)+"|");
  			imp.say(imp.pRow()+1,0,""+imp.comprimido());
  			imp.say(imp.pRow()+0,0,"|");
  			imp.say(imp.pRow()+0,55,"Total geral-> ");
  			imp.say(imp.pRow()+0,122,Funcoes.strDecimalToStrCurrency(12,2,""+deTotParc));
  			imp.say(imp.pRow(),136,"|");
  			imp.say(imp.pRow()+1,0,""+imp.comprimido());
  			imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",134)+"|");
      
      
  			imp.eject();
      
  			imp.fechaGravacao();
  			
  			rs.close();
  			ps.close();
  			if (!con.getAutoCommit())
  				con.commit();
//      dl.dispose();
  		}  
  		catch ( SQLException err ) {
  			Funcoes.mensagemErro(this,"Erro consulta tabela de contas a receber!\n"+err.getMessage());      
  		}
    
  		if (bVisualizar) {
  			imp.preview(this);
  		}
  		else {
  			imp.print();
  		}
  	}
    finally {
      	imp = null;
      	linPag = 0;
      	sCodBanco = null;
      	iParans = 0;
      	iCodSetor = 0;
      	iCodVend = 0;
      	iCodCli = 0;
    	sFiltro = null;
    	sTitulo = null;
    	sDataini = null;
    	sDatafim = null;
    	sDtVencItRec = null;
    	sDtPago = null;
    	sSQL = null;
    	sImpTotDia = null;
    	sWhere = null;
    	sFrom = null;
    	ps = null;
    	rs = null;
        deTotParc = 0;
    }
  }

  private boolean[] getPrefere() {
  	boolean[] bRet = new boolean[1];
  	String sSQL = null;
  	String sVal = null;
  	PreparedStatement ps = null;
  	ResultSet rs = null;
  	try {
  		bRet[0] = false;
  		sSQL = "SELECT SETORVENDA FROM SGPREFERE1 WHERE CODEMP=? AND CODFILIAL=?";
  		try {
  			ps = con.prepareStatement(sSQL);
  			ps.setInt(1,Aplicativo.iCodEmp);
  			ps.setInt(2,Aplicativo.iCodFilial);
  			rs = ps.executeQuery();
  			if (rs.next()) {
  				sVal = rs.getString("SetorVenda");
  				if (sVal!= null) {
  					if ("CA".indexOf(sVal) >= 0) //Se tiver C ou A no sVal!
  						bRet[0] = true;
  				}
  			}
  			rs.close();
  			ps.close();
  			if (!con.getAutoCommit()) {
  				con.commit();
  			}
  		}
  		catch(SQLException err) {
  			Funcoes.mensagemErro(null,"Erro ao verificar prefer�ncias!\n"+err.getMessage());
  			err.printStackTrace();
  		}
  	}
  	finally {
  		sSQL = null;
  		ps = null;
  		rs = null;
  		sVal = null;
  	}
  	//	System.out.println("Retornou setor:"+bRet);
  	return bRet;
  }  

}
