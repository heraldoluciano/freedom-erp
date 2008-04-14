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

package org.freedom.layout.nf;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.freedom.componentes.ImprimeOS;
import org.freedom.funcoes.Funcoes;
import org.freedom.layout.componentes.Leiaute;

public class NF002 extends Leiaute {
  
	public boolean imprimir(ResultSet rs,ResultSet rsRec,ImprimeOS imp) {
   
		String[] sObs = new String[8];
		boolean bRetorno;
		int iItImp = 0;

   
		try {
   
			while (rs.next()) {
        
				if (imp.pRow() == 0) {
         
					imp.limpaPags();
					imp.say(imp.pRow()+4,0,imp.comprimido());
					imp.say(121,Funcoes.dateToStrDate(rs.getDate("DTEMITVENDA")));
					imp.say(imp.pRow()+3,0,imp.comprimido());
					
					imp.say(imp.pRow(),6,Funcoes.copy(rs.getString("CODCLI"),0,6)+"- "+Funcoes.copy(rs.getString("RAZCLI"),0,40));
					imp.say(imp.pRow(),106,Funcoes.setMascara(Funcoes.copy(rs.getString("CNPJCLI"),0,14),"##.###.###/####-##"));
					imp.say(imp.pRow()+3,0,imp.comprimido());
					imp.say(imp.pRow(),6,Funcoes.copy(rs.getString("ENDCLI"),0,30));
					imp.say(imp.pRow(),106,Funcoes.copy(rs.getString("INSCCLI"),0,30));
					imp.say(imp.pRow()+2,0,imp.comprimido());
					imp.say(imp.pRow(),58,Funcoes.copy(rs.getString("CEPCLI"),0,9));
					imp.say(imp.pRow(),88,Funcoes.copy(rs.getString("CIDCLI"),0,30));
					imp.say(imp.pRow(),132,Funcoes.copy(rs.getString("UFCLI"),0,2));
					//imp.say(imp.pRow(),102,Funcoes.dateToStrDate(rs.getDate("DTEMITVENDA"))); colocar para cima
					imp.say(imp.pRow()+3,0,"");
				}
				imp.say(imp.pRow()+1,6,Funcoes.copy(rs.getString("CODPROD"),0,5)+"-"+Funcoes.copy(rs.getString("DESCPROD"),0,30));
				imp.say(imp.pRow(),75,Funcoes.copy(rs.getString("CODUNID"),0,4));
				imp.say(imp.pRow(),84,Funcoes.strDecimalToStrCurrency(3,2,rs.getString("QtdItVenda")));
				imp.say(imp.pRow(),89,Funcoes.strDecimalToStrCurrency(13,2,rs.getString("PRECOITVENDA")));
				imp.say(imp.pRow(),100,Funcoes.strDecimalToStrCurrency(13,2,rs.getString("VLRDESCITVENDA")));
				imp.say(imp.pRow(),115,Funcoes.strDecimalToStrCurrency(13,2,rs.getString("VLRLIQITVENDA")));
				
				
				
				iItImp++;
       
				if ((iItImp == rs.getInt(1)) | (imp.pRow() == 27)) {
          
					for (int i=imp.pRow(); i<27; i++) {
            
						imp.say(imp.pRow()+1,0,"");
         
					}
        
					imp.say(30,7,Funcoes.strDecimalToStrCurrency(13,2,rs.getString("VLRBASEISSVENDA")));
					imp.say(30,28,Funcoes.strDecimalToStrCurrency(5,2,rs.getString("PERCISSITVENDA"))+"%");
					imp.say(30,44,Funcoes.strDecimalToStrCurrency(13,2,rs.getString("VLRISSVENDA")));
					imp.say(30,69,Funcoes.strDecimalToStrCurrency(13,2,rs.getString("VLRLIQVENDA")));
					imp.say(30,85,Funcoes.strDecimalToStrCurrency(13,2,rs.getString("VLRDESCVENDA")));
					imp.say(imp.pRow()+1,0,imp.comprimido());
					imp.say(imp.pRow()+1,0,imp.comprimido());
				
					imp.say(imp.pRow(),33,"Pis:");
					imp.say(imp.pRow(),41,Funcoes.strDecimalToStrCurrency(10,2,rs.getString("VLRPISVENDA")));
					imp.say(imp.pRow(),53,"Cofins:");
					imp.say(imp.pRow(),63,Funcoes.strDecimalToStrCurrency(10,2,rs.getString("VLRCOFINSVENDA")));
					imp.say(imp.pRow(),78,Funcoes.copy(rs.getString("DescPlanoPag"),0,30));
					imp.say(imp.pRow()+1,0,imp.comprimido());
					//imp.say(imp.pRow(),30,"1 - I.S.S.");
					imp.say(imp.pRow(),33,"IR:");
					imp.say(imp.pRow(),41,Funcoes.strDecimalToStrCurrency(10,2,rs.getString("VLRIRVENDA")));
					imp.say(imp.pRow(),53,"C.Social:");
					imp.say(imp.pRow(),63,Funcoes.strDecimalToStrCurrency(10,2,rs.getString("VLRCSOCIALVENDA")));
					imp.say(imp.pRow()+1,0,imp.comprimido());
					imp.say(imp.pRow(),33,"B.Imp.:");
					imp.say(imp.pRow(),41,Funcoes.strDecimalToStrCurrency(10,2,rs.getString("VLRBASEISSVENDA")));
					imp.say(imp.pRow(),53,"T.Imp.:");
					
					imp.say(imp.pRow(),63,Funcoes.strDecimalToStrCurrency(10,2,""+(
							rs.getDouble("VLRPISVENDA")+
							rs.getDouble("VLRCOFINSVENDA")+
							rs.getDouble("VLRIRVENDA")+
							rs.getDouble("VLRCSOCIALVENDA")
					
					)));
					
          
					imp.say(imp.pRow()+1,0,imp.comprimido());
					imp.say(imp.pRow(),128,Funcoes.strDecimalToStrCurrency(10,2,rs.getString("VLRLIQVENDA")));
					
					sObs = Funcoes.strToStrArray(rs.getString("ObsVenda") != null ? rs.getString("ObsVenda") : "",8);
					imp.say(imp.pRow()+4,7,sObs[0]);
					imp.say(imp.pRow()+1,7,sObs[1]);
					imp.say(imp.pRow()+1,7,sObs[2]);
					imp.say(imp.pRow()+1,7,sObs[3]);
					imp.say(imp.pRow()+1,7,sObs[4]);
					imp.say(imp.pRow()+1,7,sObs[5]);
					imp.say(imp.pRow()+1,7,sObs[6]);
					imp.say(imp.pRow()+1,7,sObs[7]);
					imp.say(imp.pRow()+5,0,imp.comprimido());
					imp.setPrc(0,0);
					iItImp = 0;

				}

			}
			imp.fechaGravacao();
			bRetorno = true;
		} catch ( SQLException err ) {
			Funcoes.mensagemErro( null, "Erro ao consultar tabela de Venda!" + err.getMessage() );
			bRetorno = false;
		}
		return bRetorno;
	}
}

