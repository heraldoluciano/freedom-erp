/**
 * @version 19/12/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FRBoleto.java <BR>
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
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Extenso;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FRelatorio;

public class FRBoleto extends FRelatorio {
  public JTextFieldPad txtCodModBol = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldFK txtDescModBol = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
  public JTextFieldPad txtCodVenda = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0); 
  public JTextFieldFK txtDocVenda = new JTextFieldFK(JTextFieldPad.TP_INTEGER,8,0); 
  private JTextFieldFK txtDataVenda = new JTextFieldFK(JTextFieldPad.TP_DATE,10,0);
  private JTextFieldPad txtCodCli = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldFK txtRazCli = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldPad txtParc = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
 
  private JLabel lbParc = new JLabel("Imp.Parcela  :");
  
  private ListaCampos lcModBol = new ListaCampos(this);
  private ListaCampos lcVenda = new ListaCampos(this);
  private ListaCampos lcCli = new ListaCampos(this);
    
  
  private Connection con = null;
  private JInternalFrame fExt = null;
  private String sInfoMoeda[] = new String[4];
  public FRBoleto() {
    this(null);
  }
  public FRBoleto(JInternalFrame fExt) {
     setTitulo("Impressão de boleto");
     setAtribos(80,80,530,200);
     
     this.fExt = fExt;
     
     txtCodVenda.setRequerido(true);
     
     lcModBol.add(new GuardaCampo( txtCodModBol, "CodModBol", "Cód.mod.", ListaCampos.DB_PK, true));
     lcModBol.add(new GuardaCampo( txtDescModBol, "DescModBol", "Descrição do modelo de boleto", ListaCampos.DB_SI, false));
     lcModBol.setReadOnly(true);
     lcModBol.montaSql(false, "MODBOLETO", "FN");
     txtCodModBol.setTabelaExterna(lcModBol);
     txtCodModBol.setFK(true);
     txtCodModBol.setNomeCampo("CodModBol");
    
     lcVenda.add(new GuardaCampo( txtCodVenda, "CodVenda", "Cód.venda",  ListaCampos.DB_PK, true));
     lcVenda.add(new GuardaCampo( txtDocVenda, "DocVenda", "Doc.",  ListaCampos.DB_SI,false));
     lcVenda.add(new GuardaCampo( txtDataVenda, "DtEmitVenda", "Data",  ListaCampos.DB_SI,false));
     lcVenda.add(new GuardaCampo( txtCodCli, "CodCli", "Cód.cli.",  ListaCampos.DB_FK, true));
     lcVenda.setReadOnly(true);
     lcVenda.montaSql(false, "VENDA", "VD");
     txtCodVenda.setTabelaExterna(lcVenda);
     txtCodVenda.setFK(true);
     txtCodVenda.setNomeCampo("CodVenda");

     lcCli.add(new GuardaCampo( txtCodCli, "CodCli", "Cód.cli.", ListaCampos.DB_PK, false));
     lcCli.add(new GuardaCampo( txtRazCli, "RazCli", "Razão social do cliente", ListaCampos.DB_SI, false));
     lcCli.setReadOnly(true);
     lcCli.montaSql(false, "CLIENTE", "VD");
     txtCodCli.setTabelaExterna(lcCli);

     adic(new JLabel("Venda"),7,5,80,20);
     adic(txtCodVenda,7,25,80,20);
     adic(new JLabel("Doc."),90,5,97,20);
     adic(txtDocVenda,90,25,97,20);
     adic(new JLabel("Data"),190,5,97,20);
     adic(txtDataVenda,190,25,97,20);
     adic(new JLabel("Cliente"),290,5,200,20);
     adic(txtRazCli,290,25,200,20);
     adic(new JLabel("Cód.mod."),7,45,300,20);
     adic(txtCodModBol,7,65,80,20);
     adic(new JLabel("Descrição do modelo"),90,45,300,20);
     adic(txtDescModBol,90,65,300,20);
     adic(lbParc,7,95,90,20);
     adic(txtParc,100,95,35,20);
     
  }
  public void setConexao(Connection cn) {
    con = cn;
    lcModBol.setConexao(cn);
    lcVenda.setConexao(cn);
    lcCli.setConexao(cn);
    sInfoMoeda = buscaMoeda();
  }

  public void imprimir(boolean bVisualizar) {
    String sParc = "";
    if (txtCodVenda.getVlrString().equals("")) {
       Funcoes.mensagemInforma(this,"Código da venda em branco!");
       return;
    }
    else if (txtCodModBol.getVlrString().equals("")) {
       Funcoes.mensagemInforma(this,"Código do modelo em branco!");
       return;
    }
    
    if (txtParc.getVlrInteger().intValue()> 0)
            sParc = " AND ITR.NPARCITREC = "+txtParc.getVlrString();
    
    ImprimeOS imp = new ImprimeOS("",con);
    imp.verifLinPag();
    imp.setTitulo("Boleto");
    String sSQL = "SELECT (SELECT COUNT(*) FROM FNITRECEBER ITR2 WHERE " +
                "ITR2.CODREC=R.CODREC AND ITR2.CODEMP=R.CODEMP AND " +
                "ITR2.CODFILIAL=R.CODFILIAL),ITR.DTVENCITREC,V.DTEMITVENDA," +
                "V.DOCVENDA,ITR.NPARCITREC,ITR.VLRAPAGITREC,ITR.VLRPARCITREC," +
                "ITR.VLRDESCITREC,C.CODCLI,C.RAZCLI,C.NOMECLI,C.CPFCLI,C.CNPJCLI," +
                "C.RGCLI,C.INSCCLI,C.ENDCLI,C.NUMCLI,C.COMPLCLI,C.CEPCLI,C.BAIRCLI," +
                "C.CIDCLI,C.UFCLI,C.ENDCOB,C.NUMCOB,C.COMPLCOB,C.CEPCOB,C.BAIRCOB," +
                "C.CIDCOB,C.UFCOB,C.FONECLI,R.CODREC " +
                "FROM FNITRECEBER ITR,VDVENDA V,VDCLIENTE C, FNRECEBER R" +
                " WHERE ITR.CODREC=R.CODREC" +
                " AND ITR.CODEMP=R.CODEMP AND ITR.CODFILIAL=R.CODFILIAL" +
                " AND V.CODVENDA=R.CODVENDA AND V.CODEMP=R.CODEMPVA" +
                " AND V.CODFILIAL=R.CODFILIALVA AND C.CODCLI=V.CODCLI" +
                " AND C.CODEMP=V.CODEMPCL AND C.CODFILIAL=V.CODFILIALCL" +
                " AND R.CODEMPVA=? AND R.CODFILIALVA=? AND R.CODVENDA=?"+sParc;
    try {
        PreparedStatement ps = con.prepareStatement(sSQL);
        ps.setInt(1,Aplicativo.iCodEmp);
        ps.setInt(2,ListaCampos.getMasterFilial("VDVENDA"));
        ps.setInt(3,txtCodVenda.getVlrInteger().intValue());
        ResultSet rs = ps.executeQuery();
        while ( rs.next() ) {
            String sVal = aplicCampos(rs);
            if (sVal != null) {
                String[] sLinhas = (sVal+" ").split("\n");
                for(int i=0;i<sLinhas.length;i++) {
                	if (i==0) {
                		imp.say(imp.pRow()+1,0,imp.normal()+imp.comprimido()+"");
                        imp.say(imp.pRow(),0,sLinhas[i]);
                	}
                	else {
                        imp.say(imp.pRow()+1,0,sLinhas[i]);
                	}
                }
            }
            else
                break;
        }
        rs.close();
        ps.close();
    }
    catch ( SQLException err ) {
        Funcoes.mensagemErro(null,"Erro ao consultar informãoes!"+err.getMessage());
        err.printStackTrace();      
    }
//    imp.eject();
    imp.fechaGravacao();
    if (bVisualizar) {
      if (fExt == null)
        imp.preview(this);
      else
        imp.preview(fExt);
    }
    else {
      imp.print();
    }
  }
  private String aplicCampos(ResultSet rs) {
    String sRet = null;

    String sSQL = "SELECT TXAMODBOL FROM FNMODBOLETO" +
                          " WHERE CODEMP=? AND CODFILIAL=? AND CODMODBOL=?";
    try {
        PreparedStatement ps = con.prepareStatement(sSQL);
        ps.setInt(1,Aplicativo.iCodEmp);
        ps.setInt(2,lcModBol.getCodFilial());
        ps.setInt(3,txtCodModBol.getVlrInteger().intValue());
        ResultSet rs2 = ps.executeQuery();
        if ( rs2.next() ) {
            String sTxa = rs2.getString("TxaModBol");
            String sCampo = "";
            Date dCampo = null;
            
//Aplicando campos de dados:
// Estes '\\' que  aparecem por ai..são para anular caracteres especiais de "expressão regular".            
            
            if (sTxa != null) {
                if ((dCampo = rs.getDate("DtVencItRec")) != null)
                    sTxa = sTxa.replaceAll("\\[VENCIMEN]",Funcoes.sqlDateToStrDate(dCampo));
                if ((dCampo = rs.getDate("DtEmitVenda")) != null)
                    sTxa = sTxa.replaceAll("\\[DATADOC_]",Funcoes.sqlDateToStrDate(dCampo));
                if ((sCampo = rs.getString("CodRec")) != null)
                    sTxa = sTxa.replaceAll("\\[CODREC]",Funcoes.alinhaDir(sCampo,8));
                if ((sCampo = rs.getString("DocVenda")) != null)
                    sTxa = sTxa.replaceAll("\\[__DOCUMENTO__]",Funcoes.alinhaDir(sCampo,15));
                if ((sCampo = rs.getString("NParcItRec")) != null) {
                    sTxa = sTxa.replaceAll("\\[P]",Funcoes.copy(sCampo,0,3));
                    if (rs.getInt(1) > 1)
                        sTxa = sTxa.replaceAll("\\[A]",""+((char)(rs.getInt("NParcItRec")+64)));
                }
                if ((sCampo = rs.getString("VlrParcItRec")) != null && rs.getDouble("VlrParcItRec") != 0) {
                    sTxa = sTxa.replaceAll("\\[VALOR_DOCUMEN]",Funcoes.strDecimalToStrCurrency(15,2,sCampo));
                    sTxa = sTxa.replaceAll("\\[VALOR_EXTENSO]",Extenso.extenso(rs.getDouble("VlrParcItRec"),sInfoMoeda[0],sInfoMoeda[1],sInfoMoeda[2],sInfoMoeda[3]));
                }
                if ((sCampo = rs.getString("VlrApagItRec")) != null && rs.getDouble("VlrApagItRec") != 0)
                    sTxa = sTxa.replaceAll("\\[VLIQ_DOCUMENT]",Funcoes.strDecimalToStrCurrency(15,2,sCampo));
                if ((sCampo = rs.getString("VlrDescItRec")) != null && rs.getDouble("VlrDescItRec") != 0)
                    sTxa = sTxa.replaceAll("\\[DESC_DOCUMENT]",Funcoes.strDecimalToStrCurrency(15,2,sCampo));
                if ((sCampo = rs.getString("CodCli")) != null)
                    sTxa = sTxa.replaceAll("\\[CODCLI]",Funcoes.copy(sCampo,0,8));
                if ((sCampo = rs.getString("RazCli")) != null)
                    sTxa = sTxa.replaceAll("\\[_____________RAZAO____DO____CLIENTE_____________]",Funcoes.copy(sCampo,0,50));
                if ((sCampo = rs.getString("NomeCli")) != null)
                    sTxa = sTxa.replaceAll("\\[_____________NOME_____DO____CLIENTE_____________]",Funcoes.copy(sCampo,0,50));
                if ((sCampo = rs.getString("CpfCli")) != null)
                    sTxa = sTxa.replaceAll("\\[CPF/CNPJ_ CLIENT]",Funcoes.setMascara(sCampo,"###.###.###-##"));
                else if ((sCampo = rs.getString("CnpjCli")) != null)
                    sTxa = sTxa.replaceAll("\\[CPF/CNPJ_ CLIENT]",Funcoes.setMascara(sCampo,"##.###.###/####-##"));
                if ((sCampo = rs.getString("RgCli")) != null)
                    sTxa = sTxa.replaceAll("\\[____IE/RG____CLIENTE]",Funcoes.copy(sCampo,0,22));
                else if ((sCampo = rs.getString("InscCli")) != null)
                    sTxa = sTxa.replaceAll("\\[____IE/RG____CLIENTE]",Funcoes.copy(sCampo,0,22));
                if ((sCampo = rs.getString("EndCob")) != null ||( sCampo = rs.getString("EndCli")) != null)
                    sTxa = sTxa.replaceAll("\\[____________ENDERECO____DO____CLIENTE___________]",sCampo.trim());
                if ((sCampo = rs.getString("NumCob")) != null || (sCampo = rs.getString("NumCli")) != null)
                    sTxa = sTxa.replaceAll("\\[NUMERO]",sCampo);
                if ((sCampo = rs.getString("ComplCob")) != null || (sCampo = rs.getString("ComplCli")) != null)
                    sTxa = sTxa.replaceAll("\\[____COMPLEMENTO___]",sCampo.trim());
                if ((sCampo = rs.getString("CepCob")) != null || (sCampo = rs.getString("CepCli")) != null)
                    sTxa = sTxa.replaceAll("\\[__CEP__]",Funcoes.setMascara(sCampo,"#####-###"));
                if ((sCampo = rs.getString("BairCob")) != null || (sCampo = rs.getString("BairCli")) != null)
                    sTxa = sTxa.replaceAll("\\[___________BAIRRO___________]",sCampo.trim());
                if ((sCampo = rs.getString("CidCob")) != null || (sCampo = rs.getString("CidCli")) != null)
                    sTxa = sTxa.replaceAll("\\[___________CIDADE___________]",sCampo.trim());
                if ((sCampo = rs.getString("UfCob")) != null || (sCampo = rs.getString("UfCli")) != null)
                    sTxa = sTxa.replaceAll("\\[UF]",Funcoes.copy(sCampo,0,2));
                if ((sCampo = rs.getString("FoneCli")) != null)
                    sTxa = sTxa.replaceAll("\\[__TELEFONE___]",Funcoes.setMascara(sCampo,"(####)####-####"));
                
//Aplicar campos especiais de dados:
                
                int iPos = 0;
                while ((iPos = sTxa.indexOf("%_VAL",iPos+1)) > 0) {
                  double dVal = 0;
                  String sCaixa = sTxa.substring(iPos-9,iPos);
                  sCaixa += "\\"+sTxa.substring(iPos,iPos+6);
                  dVal = rs.getDouble("VlrParcitRec");
                  dVal *= Double.parseDouble(sTxa.substring(iPos-8,iPos))/100;
                  sTxa = sTxa.replaceAll("\\"+sCaixa,Funcoes.strDecimalToStrCurrency(15,2,new BigDecimal(dVal).setScale(2,BigDecimal.ROUND_HALF_UP).toString()));
                }
                iPos = 0;
                while ((iPos = sTxa.indexOf("+_VAL",iPos+1)) > 0) {
                    double dVal = 0;
                    String sCaixa = sTxa.substring(iPos-9,iPos);
                    sCaixa += "\\"+sTxa.substring(iPos,iPos+6);
                    dVal = rs.getDouble("VlrParcitRec");
                    dVal += Double.parseDouble(sTxa.substring(iPos-8,iPos));
                    sTxa = sTxa.replaceAll("\\"+sCaixa,Funcoes.strDecimalToStrCurrency(15,2,new BigDecimal(dVal).setScale(2,BigDecimal.ROUND_HALF_UP).toString()));
                }
                iPos = 0;
                while ((iPos = sTxa.indexOf("-_VAL",iPos+1)) > 0) {
                    double dVal = 0;
                    String sCaixa = sTxa.substring(iPos-9,iPos);
                    sCaixa += "\\"+sTxa.substring(iPos,iPos+6);
                    dVal = rs.getDouble("VlrParcitRec");
                    dVal -= Double.parseDouble(sTxa.substring(iPos-8,iPos));
                    sTxa = sTxa.replaceAll("\\"+sCaixa,Funcoes.strDecimalToStrCurrency(15,2,new BigDecimal(dVal).setScale(2,BigDecimal.ROUND_HALF_UP).toString()));
                }
                iPos = 0;
                while ((iPos = sTxa.indexOf("+_VEN",iPos+1)) > 0) {
                    GregorianCalendar cVal = new GregorianCalendar();
                    String sCaixa = sTxa.substring(iPos-4,iPos);
                    sCaixa += "\\"+sTxa.substring(iPos,iPos+6);
                    cVal.setTime(rs.getDate("DtVencItRec"));
                    cVal.set(Calendar.DATE,cVal.get(Calendar.DATE)+Integer.parseInt(sTxa.substring(iPos-3,iPos)));
                    sTxa = sTxa.replaceAll("\\"+sCaixa,Funcoes.dateToStrDate(cVal.getTime()));
                }
                
                sRet = sTxa;
            }
        }
        
//Ajustando campos de ação:
        
        sRet = sRet.replaceAll("\\<LP\\>.*].*\\<_LP\\>","");
        sRet = sRet.replaceAll("\\<[_]*LP\\>","");
        
//Tirando campos não setados:
        
        Pattern p = Pattern.compile("\\[.*\\]");
        Matcher m = p.matcher(sRet);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            m.appendReplacement(sb,Funcoes.replicate(" ",m.end()-m.start()));;
        }
        m.appendTail(sb);
        
        sRet = sb.toString();
        rs2.close();
        ps.close();
    }
    catch ( SQLException err ) {
        Funcoes.mensagemErro(this,"Erro consulta ao modelo de orçamento!"+err.getMessage());      
        err.printStackTrace();      
    }
    return sRet;
  }
  public String[] buscaMoeda() {
    String sRet[] = new String[4];
    String sSQL = "SELECT M.SINGMOEDA,M.PLURMOEDA,M.DECSMOEDA,M.DECPMOEDA FROM FNMOEDA M, SGPREFERE1 P" +
                  " WHERE M.CODMOEDA=P.CODMOEDA AND M.CODEMP=P.CODEMPMO AND M.CODFILIAL=P.CODFILIALMO" +
                  " AND P.CODEMP=? AND P.CODFILIAL=?";
    try {
        PreparedStatement ps = con.prepareStatement(sSQL);
        ps.setInt(1,Aplicativo.iCodEmp);
        ps.setInt(2,Aplicativo.iCodFilial);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            sRet[0] = rs.getString("SingMoeda").trim();
            sRet[1] = rs.getString("PlurMoeda").trim();
            sRet[2] = rs.getString("DecSMoeda").trim();
            sRet[3] = rs.getString("DecPMoeda").trim();
        }
        rs.close();
        ps.close();
    }
    catch(SQLException err) {
        Funcoes.mensagemErro(null,"Erro ao buscar a moeda padrão!\n"+err.getMessage());
        err.printStackTrace();
    }
    if (sRet == null) {
        Funcoes.mensagemErro(null,"A moeda padrão pode não estar ajustada no preferências!");
    }
    return sRet;
  }
}
