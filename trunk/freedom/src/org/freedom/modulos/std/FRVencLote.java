/**
 * @version 20/07/2002 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FRVencLote.java <BR>
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
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.freedom.componentes.JLabelPad;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FRelatorio;


/**
 * Tela para verificação de validade dos Lotes 
 *
 * @version 1.0 20/07/2002
 * @author Fernado Oliveira da Silva
 */

public class FRVencLote extends FRelatorio {
  private Connection con;
  private JTextFieldPad txtCodGrup = new JTextFieldPad(JTextFieldPad.TP_STRING,14,0);
  private JTextFieldPad txtDescGrup = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
  private JTextFieldPad txtCodMarca = new JTextFieldPad(JTextFieldPad.TP_STRING,6,0);
  private JTextFieldPad txtDescMarca = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
  private JTextFieldPad txtSiglaMarca = new JTextFieldFK(JTextFieldPad.TP_STRING,20,0);
  private JCheckBoxPad cbLoteZerado = null; 
  private JLabelPad lbCodGrup = new JLabelPad("Cód.grupo");
  private JLabelPad lbDescCodGrup = new JLabelPad("Descrição do grupo");
  private JLabelPad lbCodMarca = new JLabelPad("Cód.marca");
  private JLabelPad lbDescCodMarca = new JLabelPad("Descrição da marca");
  private ListaCampos lcGrup = new ListaCampos(this);
  private ListaCampos lcMarca = new ListaCampos(this);
  private JTextFieldPad txtDataini = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
  private JTextFieldPad txtDatafim = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
  public FRVencLote() {
    setTitulo("Relatório de Vencimentos de Lotes");
    setAtribos(80,80,310,250);
    
    lcGrup.add(new GuardaCampo( txtCodGrup, "CodGrup", "Cód.grupo", ListaCampos.DB_PK, false));
    lcGrup.add(new GuardaCampo( txtDescGrup, "DescGrup", "Descrição do gurpo", ListaCampos.DB_SI, false));
    lcGrup.montaSql(false, "GRUPO", "EQ");
    lcGrup.setReadOnly(true);
    txtCodGrup.setTabelaExterna(lcGrup);
    txtCodGrup.setFK(true);
    txtCodGrup.setNomeCampo("CodGrup");

    lcMarca.add(new GuardaCampo( txtCodMarca, "CodMarca", "Cód.marca", ListaCampos.DB_PK, false));
    lcMarca.add(new GuardaCampo( txtDescMarca, "DescMarca", "Descrição da marca", ListaCampos.DB_SI, false));
    lcMarca.add(new GuardaCampo( txtSiglaMarca, "SiglaMarca", "Sigla", ListaCampos.DB_SI,false),"txtSiglaMarca");
    lcMarca.montaSql(false, "MARCA", "EQ");
    lcMarca.setReadOnly(true);
    txtCodMarca.setTabelaExterna(lcMarca);
    txtCodMarca.setFK(true);
    txtCodMarca.setNomeCampo("CodMarca");

    cbLoteZerado = new JCheckBoxPad("Exibir lotes com saldos zerados?","S","N");
    cbLoteZerado.setVlrString("N");
    
    
    
    adic(new JLabelPad("Período de vencimentos:"),7,0,250,20);
    adic(new JLabelPad("De: "),7,20,40,20);
    adic(txtDataini,50,20,97,20);
    adic(new JLabelPad(" até: "),150,20,37,20);
    adic(txtDatafim,190,20,100,20);
    adic(cbLoteZerado,7,45,250,30);
    adic(lbCodGrup,7,80,250,20);
    adic(txtCodGrup,7,100,80,20);
    adic(lbDescCodGrup,90,80,250,20);
    adic(txtDescGrup,90,100,200,20);
    adic(lbCodMarca,7,120,250,20);
    adic(txtCodMarca,7,140,80,20);
    adic(lbDescCodMarca,90,120,250,20);
    adic(txtDescMarca,90,140,200,20);
    

    GregorianCalendar cPeriodo = new GregorianCalendar();
    txtDataini.setVlrDate(cPeriodo.getTime());
    cPeriodo.add(Calendar.MONTH,3);
    txtDatafim.setVlrDate(cPeriodo.getTime());

  }

  private boolean comRef() {
    boolean bRetorno = false;
    String sSQL = "SELECT USAREFPROD FROM SGPREFERE1 WHERE CODEMP=? AND CODFILIAL=?";
    PreparedStatement ps = null;
    ResultSet rs = null;
    try {
      ps = con.prepareStatement(sSQL);
	  ps.setInt(1,Aplicativo.iCodEmp);
	  ps.setInt(2,ListaCampos.getMasterFilial("SGPREFERE1"));
      rs = ps.executeQuery();
      if (rs.next()) {
        if (rs.getString("UsaRefProd").trim().equals("S"))
          bRetorno = true;
      }
//      rs.close();
//      ps.close();
      if (!con.getAutoCommit())
      	con.commit();
    }
    catch (SQLException err) {
		Funcoes.mensagemErro(this,"Erro ao carregar a tabela PREFERE1!\n"+err.getMessage());
    }
    return bRetorno;
  }

  /**
   *  Impressão. <BR>
   *  Imprime um relatório para o usuário.
   * 
   */

  public void imprimir(boolean bVisualizar) {
    String sCab = "";
    String sCodProd;
    String sWhere = "";
    
    ImprimeOS imp = new ImprimeOS("",con);
    int linPag = imp.verifLinPag()-1;
//    imp.montaCab();
    imp.setTitulo("Relatorio Vencimentos do Lotes");
    
    if (txtCodGrup.getText().trim().length() > 0) {
            sWhere += " AND P.CODGRUP LIKE '"+txtCodGrup.getText().trim()+"%'";
            String sTmp = "GRUPO: "+txtDescGrup.getText().trim();
            sCab += "\n"+imp.comprimido();
            sTmp = "|"+Funcoes.replicate(" ",68-(sTmp.length()/2))+sTmp;
            sCab += sTmp+Funcoes.replicate(" ",134-sTmp.length())+" |";
    }
    if (txtCodMarca.getText().trim().length() > 0) {
            sWhere += " AND P.CODMARCA = '"+txtCodMarca.getText().trim()+"'";
            String sTmp = "MARCA: "+txtDescMarca.getText().trim();
            sCab += "\n"+imp.comprimido();
            sTmp = "|"+Funcoes.replicate(" ",68-(sTmp.length()/2))+sTmp;
            sCab += sTmp+Funcoes.replicate(" ",134-sTmp.length())+" |";
    }
    if (cbLoteZerado.getVlrString().equals("N")){
    	sWhere +=" AND L.SLDLIQLOTE >0 ";
    	String sTmp ="Produtos com saldos ";
		sCab += "\n"+imp.comprimido();
    	sTmp = "|"+Funcoes.replicate(" ",68-(sTmp.length()/2))+sTmp;
    	sCab += sTmp+Funcoes.replicate(" ",134-sTmp.length())+" |";

    }
    	
    if (!txtDataini.getText().trim().equals("") && !txtDatafim.getText().trim().equals("")) {
    	sWhere += " AND L.VENCTOLOTE BETWEEN '"+
		  Funcoes.dateToStrDB(txtDataini.getVlrDate())+"' AND '"+
    	  Funcoes.dateToStrDB(txtDatafim.getVlrDate())+"'";
    	String sTmp = "PERIODO DE "+txtDataini.getVlrString()+" ATE "+txtDatafim.getVlrString();
    	sCab += "\n"+imp.comprimido();
    	sTmp = "|"+Funcoes.replicate(" ",68-(sTmp.length()/2))+sTmp;
    	sCab += sTmp+Funcoes.replicate(" ",134-sTmp.length())+" |";
    }
   if (comRef()) 
      sCodProd = "REFPROD";
    else
      sCodProd = "CODPROD";
    
    String sSQL = "SELECT P."+sCodProd+",P.DESCPROD,L.CODLOTE,L.VENCTOLOTE,L.SLDLIQLOTE "+
		  "FROM EQPRODUTO P, EQLOTE L "+
		  "WHERE L.CODPROD = P.CODPROD"+sWhere+" ORDER BY VENCTOLOTE";
    
    System.out.println(sSQL);
    
    try {
      PreparedStatement ps = con.prepareStatement(sSQL);
      ResultSet rs = ps.executeQuery();
      imp.limpaPags();
      while ( rs.next() ) {
        if (imp.pRow()==0) {
           imp.impCab(136, false);
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,0,"+"+Funcoes.replicate("-",134)+"+");
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,0,"| Emitido em :"+Funcoes.dateToStrDate(new Date()));
           imp.say(imp.pRow()+0,120,"Pagina : "+(imp.getNumPags()));
           imp.say(imp.pRow()+0,136,"|");
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,0,"|");
           imp.say(imp.pRow()+0,60,"VENCIMENTOS DE LOTES");
           imp.say(imp.pRow()+0,136,"|");
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,0,"|");
           imp.say(imp.pRow()+0,136,"|");
           if (sCab.length() > 0) imp.say(imp.pRow()+0,0,sCab);
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,0,"|");
           imp.say(imp.pRow()+0,136,"|");
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",134)+"|");
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,0,"| Código");
           imp.say(imp.pRow()+0,16,"| Descrição");
           imp.say(imp.pRow()+0,69,"| Lote");
           imp.say(imp.pRow()+0,85,"| Vencimento");
           imp.say(imp.pRow()+0,98,"| Saldo");
           imp.say(imp.pRow()+0,136,"|");
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",134)+"|");
         }

         imp.say(imp.pRow()+1,0,""+imp.comprimido());
         imp.say(imp.pRow()+0,0,"| "+(sCodProd.equals("REFPROD") ? rs.getString("REFPROD") : Funcoes.alinhaDir(rs.getInt("CODPROD"),13)));
         imp.say(imp.pRow()+0,16,"| "+rs.getString("DESCPROD"));
         imp.say(imp.pRow()+0,69,"| "+rs.getString("CODLOTE"));
         imp.say(imp.pRow()+0,85,"| "+Funcoes.sqlDateToStrDate(rs.getDate("VENCTOLOTE")));
         imp.say(imp.pRow()+0,98,"| "+Funcoes.strDecimalToStrCurrency(15,1,rs.getString("SLDLIQLOTE")));
         imp.say(imp.pRow()+0,136,"|");
         
		if (imp.pRow()>=linPag) {
			 imp.say(imp.pRow()+1,0,""+imp.comprimido());
			 imp.say(imp.pRow()+0,0,"+"+Funcoes.replicate("-",134)+"+");
			 imp.incPags();
			 imp.eject();
		}
             
      }

      imp.say(imp.pRow()+1,0,""+imp.comprimido());
      imp.say(imp.pRow()+0,0,"+"+Funcoes.replicate("-",134)+"+");
      
      imp.eject();
      
      imp.fechaGravacao();
      
//      rs.close();
//      ps.close();
      if (!con.getAutoCommit())
      	con.commit();
//      dl.dispose();
    }  
    catch ( SQLException err ) {
		Funcoes.mensagemErro(this,"Erro ao consultar a tabela PRODUTOS!\n"+err.getMessage(),true,con,err);
    }
    
    if (bVisualizar) {
      imp.preview(this);
    }
    else {
      imp.print();
    }
  }

  /**
   *  Ajusta conexão da tela. <BR>
   *  Adiciona a conexão vigente a este formulário.
   *  
   *  @param cn: Conexao valida e ativa que será repassada e esta tela.
   *  @see org.freedom.telas.FFilho#setConexao
   */

  public void setConexao(Connection cn) {
    super.setConexao(cn);
    lcGrup.setConexao(cn);
    lcMarca.setConexao(cn);
  }
}
