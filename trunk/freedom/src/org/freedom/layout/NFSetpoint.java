/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: leiautes <BR>
 * Classe: @(#)NFSetpoint.java <BR>
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
 * Layout da nota fiscal para a empresa Setpoint Informática Ltda.
 */

package org.freedom.layout;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.freedom.componentes.ImprimeOS;
import org.freedom.funcoes.Funcoes;
public class NFSetpoint extends Leiaute {
  public boolean imprimir(ResultSet rs,ResultSet rsRec,ImprimeOS imp) {
    String[] sObs = new String[8];
    boolean bRetorno;
    int iItImp = 0;
//    imp.verifLinPag("NF"); //O veriflinpag é feito jah na venda, não precisa ser feito aqui!
    try {
      while (rs.next()) {
        if (imp.pRow() == 0) {
          imp.limpaPags();
          imp.say(imp.pRow()+8,0,imp.comprimido());
          imp.say(imp.pRow(),25,Funcoes.copy(rs.getString("CODCLI"),0,6)+"-"+Funcoes.copy(rs.getString("RAZCLI"),0,40));
          imp.say(imp.pRow(),93,Funcoes.copy(rs.getString("CODVEND"),0,4)+"-"+Funcoes.copy(rs.getString("NomeVend"),0,30));
          imp.say(imp.pRow()+1,91,Funcoes.strZero(rs.getString("CodVenda"),8));
          imp.say(imp.pRow()+2,22,Funcoes.copy(rs.getString("ENDCLI"),0,30));
          imp.say(imp.pRow()+1,101,Funcoes.copy(rs.getString("DescPlanoPag"),0,30));
          imp.say(imp.pRow()+2,22,Funcoes.copy(rs.getString("CIDCLI"),0,30));
          imp.say(imp.pRow(),102,Funcoes.dateToStrDate(rs.getDate("DTEMITVENDA")));
          imp.say(imp.pRow()+3,0,"");
        }
		  imp.say(imp.pRow()+1,6,Funcoes.strDecimalToStrCurrency(3,2,rs.getString("QtdItVenda")));
	  //  imp.say(imp.pRow()+1,6,""+rs.getDouble("QtdItVenda"));
      //  imp.say(imp.pRow()+1,6,Funcoes.copy(rs.getString("QTDITVENDA"),0,3));
        imp.say(imp.pRow(),18,Funcoes.copy(rs.getString("CODUNID"),0,4));
        imp.say(imp.pRow(),26,Funcoes.copy(rs.getString("CODPROD"),0,5)+"-"+Funcoes.copy(rs.getString("DESCPROD"),0,30));
        imp.say(imp.pRow(),70,"1");
        imp.say(imp.pRow(),76,Funcoes.strDecimalToStrCurrency(13,2,rs.getString("PRECOITVENDA")));
        imp.say(imp.pRow(),98,Funcoes.strDecimalToStrCurrency(13,2,rs.getString("VLRLIQITVENDA")));
        iItImp++;
        if ((iItImp == rs.getInt(1)) | (imp.pRow() == 27)) {
          for (int i=imp.pRow(); i<27; i++) {
            imp.say(imp.pRow()+1,0,"");
          }
          imp.say(30,7,Funcoes.strDecimalToStrCurrency(13,2,rs.getString("VLRBASEISSVENDA")));
          imp.say(30,34,Funcoes.strDecimalToStrCurrency(5,2,rs.getString("PERCISSITVENDA"))+"%");
          imp.say(30,42,Funcoes.strDecimalToStrCurrency(13,2,rs.getString("VLRISSVENDA")));
          imp.say(30,62,Funcoes.strDecimalToStrCurrency(13,2,rs.getString("VLRLIQVENDA")));
          imp.say(30,104,Funcoes.strDecimalToStrCurrency(13,2,rs.getString("VLRLIQVENDA")));
          imp.say(imp.pRow()+1,0,imp.comprimido());
          imp.say(imp.pRow()+1,0,imp.comprimido());
          imp.say(imp.pRow(),91,"Pis:");
          imp.say(imp.pRow(),99,Funcoes.strDecimalToStrCurrency(10,2,rs.getString("VLRPISVENDA")));
          imp.say(imp.pRow(),111,"Cofins:");
          imp.say(imp.pRow(),121,Funcoes.strDecimalToStrCurrency(10,2,rs.getString("VLRCOFINSVENDA")));
          imp.say(imp.pRow()+1,0,imp.comprimido());
          imp.say(imp.pRow(),40,"1 - I.S.S.");
          imp.say(imp.pRow(),91,"IR:");
          imp.say(imp.pRow(),99,Funcoes.strDecimalToStrCurrency(10,2,rs.getString("VLRIRVENDA")));
          imp.say(imp.pRow(),111,"C.Social:");
          imp.say(imp.pRow(),121,Funcoes.strDecimalToStrCurrency(10,2,rs.getString("VLRCSOCIALVENDA")));
          imp.say(imp.pRow()+1,0,imp.comprimido());
          imp.say(imp.pRow(),91,"B.Imp.:");
          imp.say(imp.pRow(),99,Funcoes.strDecimalToStrCurrency(10,2,rs.getString("VLRBASEISSVENDA")));
          imp.say(imp.pRow(),111,"T.Imp.:");
          imp.say(imp.pRow(),121,Funcoes.strDecimalToStrCurrency(10,2,""+(
          		rs.getDouble("VLRPISVENDA")+
          		rs.getDouble("VLRCOFINSVENDA")+
          		rs.getDouble("VLRIRVENDA")+
          		rs.getDouble("VLRCSOCIALVENDA")
		  )));
          imp.say(imp.pRow()+1,0,imp.comprimido());
          imp.say(imp.pRow(),100,"T. Nota:");
          imp.say(imp.pRow(),110,Funcoes.strDecimalToStrCurrency(10,2,rs.getString("VLRLIQVENDA")));
          imp.say(imp.pRow()+4,7,Funcoes.dateToStrDate(rs.getDate("DTEMITVENDA")));
          imp.say(imp.pRow(),21,"97.324.966/0001-16");
          imp.say(imp.pRow(),47,"6-022-289.247-7");
          imp.say(imp.pRow(),65,"PR");
          imp.say(imp.pRow(),73,Funcoes.setMascara(Funcoes.copy(rs.getString("CNPJCLI"),0,14),"##.###.###/####-##"));
          imp.say(imp.pRow(),100,Funcoes.copy(rs.getString("INSCCLI"),0,30));
          imp.say(imp.pRow(),125,Funcoes.copy(rs.getString("UFCLI"),0,2));
          sObs = Funcoes.strToStrArray(rs.getString("ObsVenda") != null ? rs.getString("ObsVenda") : "",8);
          imp.say(imp.pRow()+3,7,sObs[0]);
          imp.say(imp.pRow()+1,7,sObs[1]);
          imp.say(imp.pRow()+1,7,sObs[2]);
          imp.say(imp.pRow()+1,7,sObs[3]);
          imp.say(imp.pRow()+1,7,sObs[4]);
          imp.say(imp.pRow()+1,7,sObs[5]);
          imp.say(imp.pRow()+1,7,sObs[6]);
          imp.say(imp.pRow()+1,7,sObs[7]);
          imp.eject();
          imp.setPrc(0,0);
          iItImp = 0;
        }
      }
      imp.fechaGravacao();
      bRetorno = true;
    }
    catch ( SQLException err ) {
      Funcoes.mensagemErro(null,"Erro ao consultar tabela de Venda!"+err.getMessage());      
      bRetorno = false;
    }
    return bRetorno;
  }
}

