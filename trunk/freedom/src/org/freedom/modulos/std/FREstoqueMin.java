/**
 * @version 08/12/2000 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FREstoqueMin.java <BR>
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
import java.util.Date;
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

public class FREstoqueMin extends FRelatorio {
  private Connection con;
  private JTextFieldPad txtCodGrup = new JTextFieldPad();
  private JTextFieldPad txtDescGrup = new JTextFieldFK();
  private JTextFieldPad txtCodMarca = new JTextFieldPad();
  private JTextFieldPad txtDescMarca = new JTextFieldFK();
  private JTextFieldPad txtSiglaMarca = new JTextFieldFK();
  private JLabel lbCodGrup = new JLabel("Cód.grupo");
  private JLabel lbDescGrup = new JLabel("Descrição do grupo");
  private JLabel lbCodMarca = new JLabel("Cód.marca");
  private JLabel lbDescMarca = new JLabel("Descrição da marca");
  private JRadioGroup rgOrdem = null;
  private Vector vLabs = new Vector(2);
  private Vector vVals = new Vector(2);
  private ListaCampos lcGrup = new ListaCampos(this);
  private ListaCampos lcMarca = new ListaCampos(this);
  private JCheckBoxPad cbGrupo = new JCheckBoxPad("Dividir por grupo","S","N");

  public FREstoqueMin() {
    setTitulo("Relatório de Estoque Mínimo");
    setAtribos(80,80,350,240);
    vLabs.addElement("Código");
    vLabs.addElement("Descrição");
    vVals.addElement("C");
    vVals.addElement("D");
    rgOrdem = new JRadioGroup(1,2,vLabs,vVals);
    rgOrdem.setVlrString("D");

    txtCodGrup.setTipo(JTextFieldPad.TP_STRING,14,0);
    txtDescGrup.setTipo(JTextFieldPad.TP_STRING,40,0);
    lcGrup.add(new GuardaCampo( txtCodGrup, 7, 100, 80, 20, "CodGrup", "Cód.grupo", true, false, null, JTextFieldPad.TP_STRING,false),"txtCodGrup");
    lcGrup.add(new GuardaCampo( txtDescGrup, 90, 100, 207, 20, "DescGrup", "Descrição do grupo", false, false, null, JTextFieldPad.TP_STRING,false),"txtDescGrup");
    lcGrup.montaSql(false, "GRUPO", "EQ");
    lcGrup.setReadOnly(true);
    txtCodGrup.setTabelaExterna(lcGrup);
    txtCodGrup.setFK(true);
    txtCodGrup.setNomeCampo("CodGrup");

    txtCodMarca.setTipo(JTextFieldPad.TP_STRING,6,0);
    txtDescMarca.setTipo(JTextFieldPad.TP_STRING,40,0);
    txtSiglaMarca.setTipo(JTextFieldPad.TP_STRING,20,0);
    lcMarca.add(new GuardaCampo( txtCodMarca, 7, 100, 80, 20, "CodMarca", "Cód.marca", true, false, null, JTextFieldPad.TP_STRING,false),"txtCodMarca");
    lcMarca.add(new GuardaCampo( txtDescMarca, 90, 100, 207, 20, "DescMarca", "Descrição da marca", false, false, null, JTextFieldPad.TP_STRING,false),"txtDescMarca");
    lcMarca.add(new GuardaCampo( txtSiglaMarca, 90, 100, 207, 20, "SiglaMarca", "Sigla", false, false, null, JTextFieldPad.TP_STRING,false),"txtSiglaMarca");
    lcMarca.montaSql(false, "MARCA", "EQ");
    lcMarca.setReadOnly(true);
    txtCodMarca.setTabelaExterna(lcMarca);
    txtCodMarca.setFK(true);
    txtCodMarca.setNomeCampo("CodMarca");

    adic(lbCodGrup,7,0,250,20);
    adic(txtCodGrup,7,20,80,20);
    adic(lbDescGrup,90,0,250,20);
    adic(txtDescGrup,90,20,197,20);
    adic(lbCodMarca,7,40,250,20);
    adic(txtCodMarca,7,60,80,20);
    adic(lbDescMarca,90,40,250,20);
    adic(txtDescMarca,90,60,197,20);
    adic(rgOrdem,7,90,250,30);
    adic(cbGrupo,7,130,250,20);
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
    String sOrdem = rgOrdem.getVlrString();
    String sCab = "";
    String sWhere = "";
    String sOrdenado = "";
    String sOrdemGrupo = "";
    String sDivGrupo = "";
    String sCodgrup = "";
    
    BigDecimal bSldLiqProd = new BigDecimal("0");
    BigDecimal bQtdMinProd = new BigDecimal("0");
    BigDecimal bQtdFaltaProd = new BigDecimal("0");
    BigDecimal bVlrFaltaProd = new BigDecimal("0");
    BigDecimal bVlrEstoqProd = new BigDecimal("0");
    sDivGrupo = cbGrupo.getVlrString();
    
    if (sDivGrupo.equals("S")) {
      sOrdemGrupo = "P.CODGRUP,";
    }
    else {
      sOrdemGrupo = "";
    }
    
    if (sOrdem.equals("C")) {
      if (comRef()) {
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
    imp.setTitulo("Relatorio de Estoque Abaixo do Minimo");
    
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
 
    String sSQL = "SELECT P.CODGRUP,P.CODPROD,P.REFPROD,P.DESCPROD,"+
                  "P.SLDLIQPROD,P.QTDMINPROD,(P.QTDMINPROD-P.SLDLIQPROD),"+
                  "P.CUSTOMPMPROD,(P.SLDLIQPROD*P.CUSTOMPMPROD),"+
                  "(P.QTDMINPROD-P.SLDLIQPROD)*P.CUSTOMPMPROD,"+
                  "G.DESCGRUP,P.DTULTCPPROD FROM EQPRODUTO P,EQGRUPO G "+
                  "WHERE G.CODGRUP=P.CODGRUP AND P.SLDLIQPROD<P.QTDMINPROD "+
                  " AND P.ATIVOPROD='S' "+sWhere+" ORDER BY "+sOrdem;
                  
    PreparedStatement ps = null;
    ResultSet rs = null;
    try {
      ps = con.prepareStatement(sSQL);
      rs = ps.executeQuery();
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
           imp.say(imp.pRow()+0,62,"ESTOQUE MINIMO");
           imp.say(imp.pRow()+0,136,"|");
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,0,"|");
           imp.say(imp.pRow()+0,136,"|");
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,0,sOrdenado);
           if (sCab.length() > 0) imp.say(imp.pRow()+0,0,sCab);
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,0,"|");
           imp.say(imp.pRow()+0,136,"|");
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",134)+"|");
           
           if (sDivGrupo.equals("S")) {
             String sDescGrup = rs.getString("DescGrup");
             sDescGrup = sDescGrup != null ? sDescGrup.trim() : "";
             sDescGrup = "|"+Funcoes.replicate(" ",68-(sDescGrup.length()/2))+sDescGrup;
             sDescGrup += Funcoes.replicate(" ",134-sDescGrup.length())+" |";
             imp.say(imp.pRow()+1,0,""+imp.comprimido());
             imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",134)+"|");
             imp.say(imp.pRow()+1,0,""+imp.comprimido());
             imp.say(imp.pRow()+0,0,sDescGrup);
           }

           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",134)+"|");
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,0,"| Linha.        | Ref.         |"+
                 " Descrição                          | U.Compra  | Estoq.|"+
                 " Min. | Falta| C.Unit. | C.Estoq.  |  C.Falta |");
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",134)+"|");
         }

         imp.say(imp.pRow()+1,0,""+imp.comprimido());
         imp.say(imp.pRow(),0,"| "+Funcoes.copy(rs.getString("Codgrup"),0,14)+
             "| "+Funcoes.copy(rs.getString("Refprod"),0,13)+
             "| "+Funcoes.copy(rs.getString("Descprod"),0,35)+
             "| "+(rs.getDate("DtUltCpProd") != null ? Funcoes.sqlDateToStrDate(rs.getDate("DtUltCpProd")) : "          ")+
             "| "+Funcoes.strDecimalToStrCurrency(6,0,rs.getString("sldliqprod"))+
             "|"+Funcoes.strDecimalToStrCurrency(6,0,rs.getString("qtdminprod"))+
             "|"+Funcoes.strDecimalToStrCurrency(6,0,rs.getString(7))+
             "|"+Funcoes.strDecimalToStrCurrency(9,2,rs.getString("custompmprod"))+
             "|"+Funcoes.strDecimalToStrCurrency(11,2,rs.getString(9))+
             "|"+Funcoes.strDecimalToStrCurrency(10,2,rs.getString(10))+"|");
             
         if (rs.getString("sldliqprod") != null) 
            bSldLiqProd = bSldLiqProd.add(new BigDecimal(rs.getString("sldliqprod")));
         if (rs.getString("qtdminprod") != null) 
            bQtdMinProd = bQtdMinProd.add(new BigDecimal(rs.getString("qtdminprod")));
         if (rs.getString(7) != null) 
            bQtdFaltaProd = bQtdFaltaProd.add(new BigDecimal(rs.getString(7)));
         if (rs.getString(9) != null) 
            bVlrEstoqProd = bVlrEstoqProd.add(new BigDecimal(rs.getString(9)));
         if (rs.getString(10) != null) 
            bVlrFaltaProd = bVlrFaltaProd.add(new BigDecimal(rs.getString(10)));
         
      }

      imp.say(imp.pRow()+1,0,""+imp.comprimido());
      imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",134)+"|");
      imp.say(imp.pRow()+1,0,""+imp.comprimido());
      imp.say(imp.pRow(),0,"| "+Funcoes.copy(" ",0,14)+
             "| "+Funcoes.replicate(" ",13)+
             "| "+Funcoes.replicate(" ",35)+
             "| TOTAL     |"+Funcoes.strDecimalToStrCurrency(6,0,""+bSldLiqProd)+
             "|"+Funcoes.strDecimalToStrCurrency(6,0,""+bQtdMinProd)+
             "|"+Funcoes.strDecimalToStrCurrency(6,0,""+bQtdFaltaProd)+
             "|"+Funcoes.replicate(" ",9)+
             "|"+Funcoes.strDecimalToStrCurrency(11,2,""+bVlrEstoqProd)+
             "|"+Funcoes.strDecimalToStrCurrency(10,2,""+bVlrFaltaProd)+"|");
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
		Funcoes.mensagemErro(this,"Erro consulta tabela de preços!"+err.getMessage());      
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
