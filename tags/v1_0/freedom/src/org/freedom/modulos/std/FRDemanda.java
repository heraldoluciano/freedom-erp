/**
 * @version 23/11/2004 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva e Robson Sanchez<BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FRDemanda.java <BR>
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
public class FRDemanda extends FRelatorio {
  private Connection con;
  private JTextFieldPad txtDataini = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
  private JTextFieldPad txtDatafim = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
  private JTextFieldPad txtCodMarca = new JTextFieldPad();
  private JTextFieldPad txtDescMarca = new JTextFieldFK();
  private JTextFieldPad txtCodGrup = new JTextFieldPad();
  private JTextFieldPad txtDescGrup = new JTextFieldFK();
  private JTextFieldPad txtSiglaMarca = new JTextFieldFK();
  private JLabel lbCodGrup = new JLabel("Código e descrição do Grupo");
  private JLabel lbCodMarca = new JLabel("Código e descrição da Marca");
  private JRadioGroup rgOrdem = null;
  private Vector vLabs = new Vector(2);
  private Vector vVals = new Vector(2);
  private ListaCampos lcGrup = new ListaCampos(this);
  private ListaCampos lcMarca = new ListaCampos(this);
  private JCheckBoxPad cbGrupo = new JCheckBoxPad("Dividir por grupo","S","N");

  public FRDemanda() {
    setTitulo("Relatório de Demanda");
    setAtribos(80,80,340,280);
    vLabs.addElement("Código");
    vLabs.addElement("Descrição");
    vVals.addElement("C");
    vVals.addElement("D");
    rgOrdem = new JRadioGroup(1,2,vLabs,vVals);
    rgOrdem.setVlrString("D");

    GregorianCalendar cal = new GregorianCalendar();
    cal.add(Calendar.DATE,-30);
    txtDataini.setVlrDate(cal.getTime());
    cal.add(Calendar.DATE,30);
    txtDatafim.setVlrDate(cal.getTime());
    txtDataini.setRequerido(true);    
    txtDatafim.setRequerido(true);    


    txtCodMarca.setTipo(JTextFieldPad.TP_STRING,6,0);
    txtDescMarca.setTipo(JTextFieldPad.TP_STRING,40,0);
    txtSiglaMarca.setTipo(JTextFieldPad.TP_STRING,20,0);
    lcMarca.add(new GuardaCampo( txtCodMarca, 7, 100, 80, 20, "CodMarca", "Código", true, false, null, JTextFieldPad.TP_STRING,false),"txtCodMarca");
    lcMarca.add(new GuardaCampo( txtDescMarca, 90, 100, 207, 20, "DescMarca", "Descrição", false, false, null, JTextFieldPad.TP_STRING,false),"txtDescMarca");
    lcMarca.add(new GuardaCampo( txtSiglaMarca, 90, 100, 207, 20, "SiglaMarca", "Sigla", false, false, null, JTextFieldPad.TP_STRING,false),"txtSiglaMarca");
    lcMarca.montaSql(false, "MARCA", "EQ");
    lcMarca.setReadOnly(true);
    txtCodMarca.setTabelaExterna(lcMarca);
    txtCodMarca.setFK(true);
    txtCodMarca.setNomeCampo("CodMarca");

    txtCodGrup.setTipo(JTextFieldPad.TP_STRING,14,0);
    txtDescGrup.setTipo(JTextFieldPad.TP_STRING,40,0);
    lcGrup.add(new GuardaCampo( txtCodGrup, 7, 100, 80, 20, "CodGrup", "Código", true, false, null, JTextFieldPad.TP_STRING,false),"txtCodGrup");
    lcGrup.add(new GuardaCampo( txtDescGrup, 90, 100, 207, 20, "DescGrup", "Descrição", false, false, null, JTextFieldPad.TP_STRING,false),"txtDescGrup");
    lcGrup.montaSql(false, "GRUPO", "EQ");
    lcGrup.setReadOnly(true);
    txtCodGrup.setTabelaExterna(lcGrup);
    txtCodGrup.setFK(true);
    txtCodGrup.setNomeCampo("CodGrup");
    
    adic(new JLabel("Periodo"),7,0,250,20);
    adic(txtDataini,7,20,100,20);
    adic(txtDatafim,110,20,100,20);

    adic(lbCodMarca,7,40,250,20);
    adic(txtCodMarca,7,60,80,20);
    adic(txtDescMarca,90,60,197,20);
    adic(lbCodGrup,7,80,250,20);
    adic(txtCodGrup,7,100,80,20);
    adic(txtDescGrup,90,100,197,20);
    adic(rgOrdem,7,130,250,30);
    adic(cbGrupo,7,170,250,20);
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
      rs.close();
      ps.close();
      if (!con.getAutoCommit())
      	con.commit();
    }
    catch (SQLException err) {
		Funcoes.mensagemErro(this,"Erro ao carregar a tabela PREFERE1!\n"+err.getMessage());
    }
    return bRetorno;
  }

  public void imprimir(boolean bVisualizar) {
    String sOrdem = rgOrdem.getVlrString();
    String sCampo = "";
    String sCab = "";
    String sWhere = "";
    String sOrdenado = "";
    String sOrdemGrupo = "";
    String sDivGrupo = "";
    String sCodgrup = "";
    String sSQL = "";
    boolean bComRef = false;

   if (txtDataini.getVlrString().length() < 10 || txtDatafim.getVlrString().length() < 10) {
      Funcoes.mensagemInforma(this,"Período inválido!");
      return;
   }

    
    sDivGrupo = cbGrupo.getVlrString();

    if (sDivGrupo.equals("S")) {
      sOrdemGrupo = "P.CODGRUP,";
    }
    else {
      sOrdemGrupo = "";
    }
    bComRef = comRef();
    sCampo = bComRef ? "REFPROD" : "CODPROD";   
    if (sOrdem.equals("C")) {
      if (bComRef) {
        sOrdem = sOrdemGrupo+"P.REFPROD";
        sOrdenado = "ORDENADO POR REFERENCIA";
      }
      else {
        sOrdem = sOrdemGrupo+"P.CODPROD";
        sOrdenado = "ORDENADO POR CODIGO";
      }
    }
    else {
      sOrdem = sOrdemGrupo+"P.DESCPROD";
      sOrdenado = "ORDENADO POR DESCRICAO"; 
    }
    sOrdenado = "|"+Funcoes.replicate(" ",68-(sOrdenado.length()/2))+sOrdenado;
    sOrdenado += Funcoes.replicate(" ",134-sOrdenado.length())+" |";
    
    ImprimeOS imp = new ImprimeOS("",con);
    int linPag = imp.verifLinPag()-1;
//    imp.montaCab();
    imp.setTitulo("Relatorio de Demanda");

    if (txtCodMarca.getText().trim().length() > 0) {
        sWhere += " P.CODMARCA = '"+txtCodMarca.getText().trim()+"'";
        String sTmp = "MARCA: "+txtDescMarca.getText().trim();
        sCab += "\n"+imp.comprimido();
        sTmp = "|"+Funcoes.replicate(" ",68-(sTmp.length()/2))+sTmp;
        sCab += sTmp+Funcoes.replicate(" ",134-sTmp.length())+" |";
    }
    
    if (txtCodGrup.getText().trim().length() > 0) {
            sWhere += " AND P.CODGRUP LIKE '"+txtCodGrup.getText().trim()+"%'";
            String sTmp = "GRUPO: "+txtDescGrup.getText().trim();
            sCab += "\n"+imp.comprimido();
            sTmp = "|"+Funcoes.replicate(" ",68-(sTmp.length()/2))+sTmp;
            sCab += sTmp+Funcoes.replicate(" ",134-sTmp.length())+" |";
    }
    
 /*    (ENTRADA: ICODEMP INTEGER, SCODFILIAL SMALLINT, SCODFILIALPD SMALLINT, 
  *     DDTINI DATE, DDTFIM DATE
  * 
  *    (SAIDA: CODGRUP CHAR(14),CODPROD INTEGER, REFPROD CHAR(13), DESCPROD CHAR(50),
  *     DESCGRUP CHAR(50),  SLDINI NUMERIC(15,3), VLROUTSAI NUMERIC(15,3),
  *     VLROUTENT NUMERIC(15,3), VLRDEVSAI NUMERIC(15,3), VLRCOMPRAS NUMERIC(15,3),
  *     VLRVENDAS NUMERIC(15,3), SLDFIM NUMERIC(15,3)) */

    if (!sWhere.trim().equals(""))
        sWhere = " WHERE "+sWhere;
    sSQL = "SELECT P.CODMARCA, P.CODGRUP,P.CODPROD, " +
       "P.REFPROD, P.DESCPROD, P.DESCGRUP," +
       "P.SLDINI, P.VLRCOMPRAS, P.VLRDEVENT, P.VLROUTENT, " +
       "P.VLRVENDAS, P.VLRDEVSAI, P.VLROUTSAI, P.SLDFIM "+
       "FROM EQRELDEMANDASP (?, ?, ?, ?, ?) P " +
       sWhere+" ORDER BY "+sOrdem;
                  
    try {
      PreparedStatement ps = con.prepareStatement(sSQL);

      ps.setInt(1,Aplicativo.iCodEmp);
      ps.setInt(2,ListaCampos.getMasterFilial("EQMOVPROD"));
      ps.setInt(3,ListaCampos.getMasterFilial("EQPRODUTO"));
      ps.setDate(4,Funcoes.dateToSQLDate(txtDataini.getVlrDate()));
      ps.setDate(5,Funcoes.dateToSQLDate(txtDatafim.getVlrDate()));
      ResultSet rs = ps.executeQuery();
      imp.limpaPags();
      while ( rs.next() ) {
        if (imp.pRow()>=(linPag-1)) {
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,0,"+"+Funcoes.replicate("-",134)+"+");
           imp.incPags();
           imp.eject();
        }
        else if (sDivGrupo.equals("S")) {
           if ((sCodgrup.length() > 0) && (!sCodgrup.equals(rs.getString("Codgrup")))) {
              imp.say(imp.pRow()+1,0,""+imp.comprimido());
              imp.say(imp.pRow()+0,0,"+"+Funcoes.replicate("-",134)+"+");
              imp.incPags();
              imp.eject();
           }
        }
 
        sCodgrup = rs.getString("codgrup");      

        if (imp.pRow()==0) {
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,0,"+"+Funcoes.replicate("-",134)+"+");
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,0,"| Emitido em :"+Funcoes.dateToStrDate(new Date()));
           imp.say(imp.pRow()+0,120,"Pagina : "+(imp.getNumPags()));
           imp.say(imp.pRow()+0,136,"|");
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,0,"|");
           imp.say(imp.pRow()+0,60,"RELATORIO DE DEMANDA");
           imp.say(imp.pRow()+0,136,"|");
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,0,sOrdenado);
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,0,"|");
           imp.say(imp.pRow()+0,53,"PERIODO: "+txtDataini.getVlrString()+" ATE "+txtDatafim.getVlrString());
           imp.say(imp.pRow()+0,136,"|");
           if (sCab.length() > 0) imp.say(imp.pRow()+0,0,sCab);
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",134)+"|");
           
           if (sDivGrupo.equals("S")) {
             String sDescGrup = rs.getString("DescGrup");
             sDescGrup = sDescGrup != null ? sDescGrup.trim() : "";
             sDescGrup = "|"+Funcoes.replicate(" ",68-(sDescGrup.length()/2))+sDescGrup;
             sDescGrup += Funcoes.replicate(" ",134-sDescGrup.length())+" |";
             imp.say(imp.pRow()+1,0,""+imp.comprimido());
             imp.say(imp.pRow()+0,0,sDescGrup);
             imp.say(imp.pRow()+1,0,""+imp.comprimido());
             imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",134)+"|");
           }
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,0,"|Cód.");
           imp.say(imp.pRow()+0,17,"|Produto");
           imp.say(imp.pRow()+0,64,"| S. ini.");
           imp.say(imp.pRow()+0,73,"| Comp.");
           imp.say(imp.pRow()+0,82,"| Dev.c.");
           imp.say(imp.pRow()+0,91,"| O.ent.");
           imp.say(imp.pRow()+0,100,"| Vend.");
           imp.say(imp.pRow()+0,109,"| Dev.f.");
           imp.say(imp.pRow()+0,118,"| O.sai.");
           imp.say(imp.pRow()+0,127,"| Saldo");
           imp.say(imp.pRow()+0,136,"|");
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",134)+"|");
         }

         imp.say(imp.pRow()+1,0,""+imp.comprimido());
         imp.say(imp.pRow()+0,0,"| "+Funcoes.copy(rs.getString(sCampo),0,13));
         imp.say(imp.pRow()+0,17,"| "+Funcoes.copy(rs.getString("DescProd"),0,45));
         imp.say(imp.pRow()+0,64,"| "+Funcoes.alinhaDir(rs.getInt("SLDINI"),7));
         imp.say(imp.pRow()+0,73,"| "+Funcoes.alinhaDir(rs.getInt("VLRCOMPRAS"),7));
         imp.say(imp.pRow()+0,82,"| "+Funcoes.alinhaDir(rs.getInt("VLRDEVENT"),7));
         imp.say(imp.pRow()+0,91,"| "+Funcoes.alinhaDir(rs.getInt("VLROUTENT"),7));
         imp.say(imp.pRow()+0,100,"| "+Funcoes.alinhaDir(rs.getInt("VLRVENDAS"),7));
         imp.say(imp.pRow()+0,109,"| "+Funcoes.alinhaDir(rs.getInt("VLRDEVSAI"),7));
         imp.say(imp.pRow()+0,118,"| "+Funcoes.alinhaDir(rs.getInt("VLROUTSAI"),7));
         imp.say(imp.pRow()+0,127,"| "+Funcoes.alinhaDir(rs.getInt("SLDFIM"),7));
         imp.say(imp.pRow()+0,136,"|");
      }
      imp.say(imp.pRow()+1,0,""+imp.comprimido());
      imp.say(imp.pRow()+0,0,"+"+Funcoes.replicate("-",134)+"+");
      imp.eject();
      imp.fechaGravacao();
      
      rs.close();
      ps.close();
      if (!con.getAutoCommit())
      	con.commit();
    }  
    catch ( SQLException err ) {
		Funcoes.mensagemErro(this,"Erro consulta de estoque!\n"+err.getMessage());
        err.printStackTrace();      
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
    lcGrup.setConexao(cn);
    lcMarca.setConexao(cn);
  }
}
