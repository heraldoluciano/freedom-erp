/**
 * @version 02/11/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FRMediaItem.java <BR>
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
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FRelatorio;

public class FRMediaItem extends FRelatorio {
  private JTextFieldPad txtMesfim = new JTextFieldPad(JTextFieldPad.TP_INTEGER,2,0); 
  private JTextFieldPad txtAnofim = new JTextFieldPad(JTextFieldPad.TP_INTEGER,4,0); 
  private JTextFieldPad txtNumMes = new JTextFieldPad(JTextFieldPad.TP_INTEGER,2,0); 
  private JTextFieldPad txtCodGrup = new JTextFieldPad(JTextFieldPad.TP_STRING,14,0);
  private JTextFieldPad txtDescGrup = new JTextFieldPad(JTextFieldPad.TP_STRING,40,0);
  private JTextFieldPad txtCodMarca = new JTextFieldPad(JTextFieldPad.TP_STRING,6,0);
  private JTextFieldPad txtDescMarca = new JTextFieldPad(JTextFieldPad.TP_STRING,40,0);
  private JTextFieldPad txtSiglaMarca = new JTextFieldPad(JTextFieldPad.TP_STRING,20,0);
  private JTextFieldPad txtCodVend = new JTextFieldPad(JTextFieldPad.TP_INTEGER,10,0);
  private JTextFieldFK txtDescVend = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
  
  
  private JRadioGroup rgOrdem = null;
  private JLabel lbOrdem = new JLabel("Ordenar por:");
  private Vector vLabs = new Vector();
  private Vector vVals = new Vector();
  private ListaCampos lcGrup = new ListaCampos(this);
  private ListaCampos lcMarca = new ListaCampos(this);
  private ListaCampos lcVend = new ListaCampos(this);
  private Connection con = null;
  public FRMediaItem() {
    setTitulo("Media de vendas por item");
    setAtribos(80,80,305,400);
    
    txtDescGrup.setAtivo(false);
    txtDescMarca.setAtivo(false);
    
    lcGrup.add(new GuardaCampo( txtCodGrup, "CodGrup", "Cód.grupo", ListaCampos.DB_PK, false));
    lcGrup.add(new GuardaCampo( txtDescGrup, "DescGrup", "Descrição do grupo", ListaCampos.DB_SI, false));
    txtCodGrup.setTabelaExterna(lcGrup);
    txtCodGrup.setNomeCampo("CodGrup");
    txtCodGrup.setFK(true);
    lcGrup.setReadOnly(true);
    lcGrup.montaSql(false, "GRUPO", "EQ");

    lcMarca.add(new GuardaCampo( txtCodMarca, "CodMarca", "Cód.marca", ListaCampos.DB_PK, false));
    lcMarca.add(new GuardaCampo( txtDescMarca, "DescMarca", "Descrição da marca", ListaCampos.DB_SI, false));
    lcMarca.add(new GuardaCampo( txtSiglaMarca, "SiglaMarca", "Sigla", ListaCampos.DB_SI, false));

	lcVend.add(new GuardaCampo( txtCodVend, "CodVend", "Cód.repr.", ListaCampos.DB_PK, false));
	lcVend.add(new GuardaCampo( txtDescVend, "NomeVend", "Nome do representante", ListaCampos.DB_SI, false));
	txtCodVend.setTabelaExterna(lcVend);
	txtCodVend.setNomeCampo("CodVend");
	txtCodVend.setFK(true);
	lcVend.setReadOnly(true);
	lcVend.montaSql(false, "VENDEDOR", "VD");
    
    
    txtCodMarca.setTabelaExterna(lcMarca);
    txtCodMarca.setNomeCampo("CodMarca");
    txtCodMarca.setFK(true);
    lcMarca.setReadOnly(true);
    lcMarca.montaSql(false, "MARCA", "EQ");
    
    vLabs.addElement("Código");
    vLabs.addElement("Descrição");
    vVals.addElement("C");
    vVals.addElement("D");
    rgOrdem = new JRadioGroup(1,2,vLabs,vVals);
    rgOrdem.setVlrString("D");
    JLabel lbLinha = new JLabel();
    lbLinha.setBorder(BorderFactory.createEtchedBorder());
    JLabel lbLinha2 = new JLabel();
    lbLinha2.setBorder(BorderFactory.createEtchedBorder());
    JLabel lbLinha3 = new JLabel();
    lbLinha3.setBorder(BorderFactory.createEtchedBorder());
    JLabel lbLinha4 = new JLabel();
    lbLinha4.setBorder(BorderFactory.createEtchedBorder());

    adic(new JLabel("Média de vendas anteriores a:"),7,5,240,20);
    adic(lbLinha,180,15,100,2);
    adic(new JLabel("Mes:"),7,30,40,20);
    adic(txtMesfim,40,30,47,20);
    adic(new JLabel("Ano:"),100,30,37,20);
    adic(txtAnofim,130,30,70,20);
    adic(new JLabel("Calcular a média dos últmos meses:"),7,60,240,20);
    adic(lbLinha2,218,70,62,2);
    adic(new JLabel("Nº de meses (máx. 12):"),7,85,200,20);
    adic(txtNumMes,150,85,40,20);
    adic(lbLinha3,7,117,273,2);
    
   
    adic(new JLabel("Cód.grupo"),7,125,240,20);
    adic(txtCodGrup,7,145,90,20);
    adic(new JLabel("Descrição do grupo"),100,125,240,20);
    adic(txtDescGrup,100,145,180,20);
    adic(new JLabel("Cód.marca"),7,165,240,20);
    adic(txtCodMarca,7,185,90,20);
    adic(new JLabel("Descrição da Marca"),100,165,240,20);
    adic(txtDescMarca,100,185,180,20);
    adic(new JLabel("Cód.repr."),7,205,200,20);
	adic(txtCodVend,7,225,70,20);
	adic(new JLabel("Nome do representante"),80,205,200,20);
	adic(txtDescVend,80,225,200,20);
    
    
    adic(lbLinha4,7,259,273,2);
    adic(lbOrdem,7,270,80,15);
    adic(rgOrdem,7,285,273,30);
    
    
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
  public void imprimir(boolean bVisualizar) {
    ImprimeOS imp = new ImprimeOS("",con);
    int linPag = imp.verifLinPag()-1;
    imp.montaCab();
    String sWhere = " WHERE ";
    String sFiltroVend = "";
    String sCab = "";
    String sOrder = "";
    String sSubSel = "";
    String sCodProd = "";
    String sSubCab = "";
    String sSubWhere = "";
    double dQtd[] = new double[12];
    int iPos = 68;
    int iAno = txtAnofim.getVlrInteger().intValue();
    int iMes = txtMesfim.getVlrInteger().intValue();
    int iNumMes = txtNumMes.getVlrInteger().intValue();
    int iNumItens = 0;
    if (iAno < 1) {
		Funcoes.mensagemInforma(this,"Ano inválido!");
      return;
    }
    else if ((iMes < 1) || (iMes > 12)) {
		Funcoes.mensagemInforma(this,"Mês inválido!");
      return;
    }
    else if ((iNumMes < 1) || (iNumMes > 12)) {
		Funcoes.mensagemInforma(this,"Num de Médias Inválido!");
      return;
    }
    GregorianCalendar cFim = new GregorianCalendar(iAno,iMes-1,1);
    int iMesIni = cFim.get(Calendar.MONTH)-iNumMes;
    int iAnoIni = cFim.get(Calendar.YEAR);
    GregorianCalendar cIni = new GregorianCalendar(iAnoIni,iMesIni,1);
    imp.setTitulo("Relatório de media de vendas por item");
    if (comRef()) {
      sCodProd = "REFPROD";
    }
    else {
      sCodProd = "CODPROD";
    }
    if (rgOrdem.getVlrString().equals("C")) {
      sOrder = "P."+sCodProd;
    }
    else {
      sOrder = "P.DESCPROD";
    }
    int iSoma = 0;
    String sOr = "";
    for (int i=0;i<iNumMes;i++) {
      cIni.set(Calendar.MONTH,cIni.get(Calendar.MONTH)+1);
      if (txtCodVend.getText().trim().length() > 0) {
		sFiltroVend = " V"+(i+2)+".CODVEND = "+txtCodVend.getText().trim();
		String sTmp = "REPR.: "+txtCodVend.getVlrString()+" - "+txtDescVend.getText().trim();
		sFiltroVend += " AND V"+(i+2)+".CODEMPVD="+Aplicativo.iCodEmp+" AND V"+(i+2)+".CODFILIALVD="+lcVend.getCodFilial()+" AND ";
        sCab = "\n"+imp.comprimido();
		sTmp = "|"+Funcoes.replicate(" ",68-(sTmp.length()/2))+sTmp;
		sCab += sTmp+Funcoes.replicate(" ",134-sTmp.length())+" |";
	  }  
      sSubSel += ",(SELECT SUM(IT.QTDITVENDA) FROM VDITVENDA IT, VDVENDA V,\n"+
                 " EQTIPOMOV TM WHERE V.FLAG IN "+
                 Aplicativo.carregaFiltro(con,org.freedom.telas.Aplicativo.iCodEmp)+
                 " AND IT.CODVENDA=V.CODVENDA AND IT.CODPROD=P.CODPROD\n"+
                 " AND TM.CODTIPOMOV=V.CODTIPOMOV" +
		         " AND TM.CODEMP=V.CODEMPTM" +
		         " AND TM.CODFILIAL=V.CODFILIALTM" +
				 " AND TM.TIPOMOV IN ('VD','PV','VT','SE')"+
                 " AND V.DTEMITVENDA BETWEEN '"+
                 Funcoes.dateToStrDB(cIni.getTime())+"' AND '"+
                 Funcoes.dateToStrDB(Funcoes.periodoMes(cIni.get(Calendar.MONTH)+1,cIni.get(Calendar.YEAR))[1])+"')";
      sSubWhere += sOr+"EXISTS (SELECT IT"+(i+2)+".CODPROD FROM VDITVENDA IT"+(i+2)+",\n"+
                   "VDVENDA V"+(i+2)+" WHERE "+sFiltroVend+"V"+(i+2)+".FLAG IN "+
                   Aplicativo.carregaFiltro(con,org.freedom.telas.Aplicativo.iCodEmp)+" AND IT"+(i+2)+".CODPROD = P.CODPROD\n"+
                   " AND V"+(i+2)+".CODVENDA = IT"+(i+2)+".CODVENDA\n"+
                   " AND V"+(i+2)+".DTEMITVENDA BETWEEN '"+
                   Funcoes.dateToStrDB(cIni.getTime())+"' AND '"+
                   Funcoes.dateToStrDB(Funcoes.periodoMes(cIni.get(Calendar.MONTH)+1,cIni.get(Calendar.YEAR))[1])+"')";
      sSubCab += " | "+Funcoes.strZero(""+(cIni.get(Calendar.MONTH)+1),2);
      sSubCab += "/"+(cIni.get(Calendar.YEAR));
      sOr = " OR "; 
      iSoma++;
    }
    sSubCab += " | Media    ";
    if (txtCodGrup.getText().trim().length() > 0) {
            sWhere += "P.CODGRUP LIKE '"+txtCodGrup.getText().trim()+"%' AND ";
            String sTmp = "GRUPO: "+txtDescGrup.getText().trim();
            sCab += "\n"+imp.comprimido();
            sTmp = "|"+Funcoes.replicate(" ",68-(sTmp.length()/2))+sTmp;
            sCab += sTmp+Funcoes.replicate(" ",(135-sTmp.length()))+"|";
    }
    if (txtCodMarca.getText().trim().length() > 0) {
            sWhere += "P.CODMARCA = '"+txtCodMarca.getText().trim()+"' AND ";
            String sTmp = "MARCA: "+txtDescMarca.getText().trim();
            sCab += "\n"+imp.comprimido();
            sTmp = "|"+Funcoes.replicate(" ",68-(sTmp.length()/2))+sTmp;
            sCab += sTmp+Funcoes.replicate(" ",(135-sTmp.length()))+"|";
    }
    
    String sSQL = "SELECT P."+sCodProd+",P.DESCPROD,P.SLDPROD,P.DTULTCPPROD,P.QTDULTCPPROD\n"+sSubSel+
                  " FROM EQPRODUTO P"+sWhere+" ("+sSubWhere+") ORDER BY "+sOrder;
    PreparedStatement ps = null;
    ResultSet rs = null;
    System.out.println(sSQL);
    try {
      ps = con.prepareStatement(sSQL);
      rs = ps.executeQuery();
      imp.limpaPags();
      while ( rs.next() ) {
        if (imp.pRow()==0) {
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,0,"+"+Funcoes.replicate("-",134)+"+");
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,0,"| Emitido em :"+Funcoes.dateToStrDate(new Date()));
           imp.say(imp.pRow()+0,120,"Pagina : "+(imp.getNumPags()));
           imp.say(imp.pRow()+0,136,"|");
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,0,"|");
           imp.say(imp.pRow()+0,49,"RELATORIO DE MEDIAS DE VENDAS POR ITEM");
           imp.say(imp.pRow()+0,136,"|");
           if (sCab.length() > 0) imp.say(imp.pRow()+0,0,sCab);
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,0,"|");
           imp.say(imp.pRow()+0,136,"|");
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",134)+"|");
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,0,"| Cod. Prod     ");
           imp.say(imp.pRow()+0,16,"| Desc. Produto                            ");
           imp.say(imp.pRow()+0,59,"| Estoque  ");
           imp.say(imp.pRow()+0,70,"| Dt.Ult.Cp. ");
           imp.say(imp.pRow()+0,83,"| Q.Ult.Cp ");
           imp.say(imp.pRow()+0,136,"|");
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,0,"|  ");
           imp.say(imp.pRow()+0,4,sSubCab);
           imp.say(imp.pRow()+0,136,"|");
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",134)+"|");
         }
         imp.say(imp.pRow()+1,0,""+imp.comprimido());
         imp.say(imp.pRow()+0,0,"| "+Funcoes.copy(rs.getString(1),0,13)+" ");
         imp.say(imp.pRow()+0,16,"| "+Funcoes.copy(rs.getString("DescProd"),0,40)+" ");
         imp.say(imp.pRow()+0,59,"| "+Funcoes.strDecimalToStrCurrency(8,0,rs.getString("SldProd"))+" ");
         if (rs.getDate("DTULTCPPROD") != null) {
              imp.say(imp.pRow()+0,70,"| "+Funcoes.sqlDateToStrDate(rs.getDate("DTULTCPPROD"))+" ");
         }
         else {
              imp.say(imp.pRow()+0,70,"|            ");
         }
         imp.say(imp.pRow()+0,83,"| "+Funcoes.strDecimalToStrCurrency(8,0,""+rs.getDouble("QTDULTCPPROD"))+" ");
         imp.say(imp.pRow()+0,136,"|");
         imp.say(imp.pRow()+1,0,""+imp.comprimido());
         imp.say(imp.pRow()+0,0,"|  ");
         double dSomaItem = 0;
         double dMediaItem = 0;
         iPos = 4;
         for (int i=0; i<iSoma; i++) {
           imp.say(imp.pRow()+0,iPos," | "+Funcoes.strDecimalToStrCurrency(7,0,rs.getString(6+i) != null ? rs.getString(6+i):" "));
           dQtd[i] += rs.getDouble(6+i);
	       dSomaItem += rs.getDouble(6+i);
           iPos += 10;
         }
	     dMediaItem = dSomaItem / iNumMes;
         imp.say(imp.pRow()+0,iPos," | "+Funcoes.strDecimalToStrCurrency(7,0,""+dMediaItem));
         imp.say(imp.pRow()+0,136,"|");
         imp.say(imp.pRow()+1,0,""+imp.comprimido());
         imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",134)+"|");
         if (imp.pRow()>=(linPag-1)) {
             imp.incPags();
             imp.eject();
         }
         iNumItens++;
      }
      imp.say(imp.pRow()+1,0,""+imp.comprimido());
      imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",134)+"|");
      imp.say(imp.pRow()+1,0,""+imp.comprimido());
      imp.say(imp.pRow()+0,0,"|T:");
      iPos = 4;
      for (int i=0; i<iSoma; i++) {
        BigDecimal bVal = new BigDecimal(dQtd[i]);
        bVal = bVal.setScale(1);
        imp.say(imp.pRow()+0,iPos," | "+Funcoes.strDecimalToStrCurrency(7,0,""+bVal));
        iPos += 10;
      }
      imp.say(imp.pRow()+0,136,"|");
      imp.say(imp.pRow()+1,0,""+imp.comprimido());
      imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",134)+"|");
      
      imp.eject();
      
      imp.fechaGravacao();
      
//      rs.close();
//      ps.close();
      if (!con.getAutoCommit())
      	con.commit();
    }  
    catch ( SQLException err ) {
		Funcoes.mensagemErro(this,"Erro consulta tabela vendas!"+err.getMessage());      
    }
    
    if (bVisualizar) {
      imp.preview(this);
    }
    else {
      imp.print();
    }
  }
  public void setConexao(Connection cn) {
    con = cn;
    lcGrup.setConexao(con);
    lcMarca.setConexao(con);
    lcVend.setConexao(con);
  }
}
