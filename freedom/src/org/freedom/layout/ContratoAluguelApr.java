/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Informática Ltda./Anderson Sanchez <BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: leiautes <BR>
 * Classe: @(#)ContratoAluguelApr.java <BR>
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
 * Comentários para a classe...
 */

package org.freedom.layout;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;

import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;

public class ContratoAluguelApr extends LeiauteGR {
	private Connection con = null;
	private Font fnTopEmp = new Font("Arial",Font.BOLD,11);
	private Font fnCabEmp = new Font("Arial",Font.PLAIN,9);
	private Font fnCabEmpNeg = new Font("Arial",Font.BOLD,9);	
	Vector vParamOrc = new Vector();
	String sCidade;
	public void montaG() {
		montaRel();
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
	  }
	  catch (SQLException err) {
		  Funcoes.mensagemErro(this,"Erro ao carregar a tabela PREFERE1!\n"+err.getMessage());
	  }
	  return bRetorno;
	}
	
	private int impLabel(String sTexto, int iSalto,int iMargem, int iLargura, int iY) {	
	  double iPixels = getFontMetrics(fnCabEmp).stringWidth(sTexto);        
	  double iNLinhas = iPixels/iLargura;
	  int iNCaracteres = sTexto.length();
	  int iNCaracPorLinha = (int) (iNCaracteres/iNLinhas);  		
	  for (int i2=0;i2<iNCaracteres;i2+=iNCaracPorLinha) {
//	 	sTextoProx = sTexto.substring(i2+iNCaracPorLinha+1,( (i2+iNCaracPorLinha)>iNCaracteres ? iNCaracteres : i2+iNCaracPorLinha+2));
//	 	if (sTextoProx.equals(" ")) {
	 	drawTexto(sTexto.substring(i2,( (i2+iNCaracPorLinha)>iNCaracteres ? iNCaracteres : i2+iNCaracPorLinha)),iMargem,iY);
		iY += iSalto;
	  }		
	  return iY;
	}
	
	private void montaRel() {
	  int iCodOrc = Integer.parseInt(vParamOrc.elementAt(0).toString());
	  imprimeRodape(false);
	  boolean bImpCab = true;
	  try {
		String sSQL = "SELECT "+
					  "(SELECT COUNT(IO.CODITORC) FROM VDITORCAMENTO IO WHERE IO.CODEMP=O.CODEMP" +
					  " AND IO.CODFILIAL=O.CODFILIAL AND IO.CODORC=O.CODORC),"+
					  "(SELECT A.NOMEATEND FROM ATATENDENTE A WHERE A.CODATEND=O.CODATEND"+
					  " AND A.CODEMP=O.CODEMPAE AND A.CODFILIAL=O.CODFILIALAE),IT.VLRPRODITORC/IT.QTDITORC,"+
					  (comRef() ? "IT.REFPROD," : "IT.CODPROD,")+
					  " O.CODORC,C.CODCONV,C.NOMECONV,O.DTORC,O.DTVENCORC,C.CIDCONV,C.UFCONV,C.ENDCONV,C.BAIRCONV,C.FONECONV,C.CPFCONV," +
					  " T.DESCTPCONV,O.OBSORC,O.VLRLIQORC,P.DESCPROD,IT.QTDITORC,IT.VLRDESCITORC,IT.VLRLIQITORC," +
					  " O.VLRDESCORC, O.VLRDESCITORC, O.VLRADICORC, O.VLRPRODORC, F.CIDFILIAL, CL.RAZCLI,CL.DDDCLI" +
					  " FROM VDORCAMENTO O, ATCONVENIADO C, ATTIPOCONV T, VDITORCAMENTO IT, EQPRODUTO P, SGFILIAL F"+
					  ",VDCLIENTE CL "+
					  " WHERE T.CODEMP=C.CODEMPTC AND T.CODFILIAL=C.CODFILIALTC AND T.CODTPCONV=C.CODTPCONV" +
					  " AND IT.CODORC=O.CODORC AND IT.CODEMP=O.CODEMP AND IT.CODFILIAL=O.CODFILIAL"+
					  " AND P.CODPROD=IT.CODPROD AND P.CODEMP=IT.CODEMPPD AND P.CODFILIAL=IT.CODFILIALPD"+
					  " AND C.CODCONV=O.CODCONV AND C.CODEMP=O.CODEMPCV AND C.CODFILIAL=O.CODFILIALCV"+
					  " AND F.CODEMP=O.CODEMP AND F.CODFILIAL=O.CODFILIAL"+
					  " AND O.TIPOORC = 'O' AND O.CODORC=? AND O.CODEMP=? AND O.CODFILIAL=?"+
					  " AND CL.CODEMP=O.CODEMP AND CL.CODFILIAL=O.CODFILIAL AND CL.CODCLI=C.CODCLI";
						
		PreparedStatement ps = con.prepareStatement(sSQL);
		ps.setInt(1,iCodOrc);
		ps.setInt(2,Aplicativo.iCodEmp);
		ps.setInt(3,ListaCampos.getMasterFilial("VDORCAMENTO"));
		ResultSet rs = ps.executeQuery();
		int iY = 97;
		for (int i=1;rs.next();i++) {
		  if (bImpCab) { 
			montaCab(rs);
			bImpCab = false;
			setFonte(fnCabEmp);
		  }
          
          if (i==1) {

//			Concatenando string para montagem do texto
        
		    String sTexto = "A "+sNomeEmp.toUpperCase()+", entidade filantrópica, inscrita no CNPJ do Ministério da Fazenda, sob o nro.: "+sCGCEmp+".";
			sTexto += "Com sede a "+sEndEmp+", daqui em diante denominada LOCATARIA e o Sr.(a) ";
			sTexto += rs.getString("NomeConv").trim().toUpperCase()+
					" residente a rua "+(rs.getString("EndConv")==null?"":rs.getString("EndConv").trim())+
					" telefone "+(rs.getString("DDDCli") != null ? "("+rs.getString("DDDCli")+")" : "")+
					(rs.getString("FoneConv") != null ? Funcoes.setMascara(rs.getString("FoneConv").trim(),"####-####") : "")+
					" CPF: "+(rs.getString("CPFCONV") !=null ? rs.getString("CPFCONV").trim() : "");
			sTexto += " daqui por diante denominado(a) LOCADOR, acertando os seguintes termos:";
		
			iY = impLabel(sTexto,15,5,540,iY+5);

			drawTexto("1° O LOCADOR assume total responsabilidade pelo uso e conservação do(s) objetos Locado(s) a seguir especificado(s):",5,iY);

			setFonte(fnCabEmpNeg);
		  
		    iY += 15;
			drawTexto("Qtd.",8,iY); 
			drawTexto("Descrição",40,iY);
			setFonte(fnCabEmp);
            iY += 15;

//			Fim do texto de cabeçalho

          }

		  String sVal0 = (Funcoes.strDecimalToStrCurrency(2,rs.getInt("QtdItOrc")+"")); 
		  String sVal1 = (rs.getString("DescProd").substring(0,42));
		    
		  drawTexto(sVal0,30,iY,getFontMetrics(fnCabEmp).stringWidth(sVal0),AL_DIR); 
		  drawTexto(sVal1,40,iY); 
		  		 	
		  iY += 10;
			
		  if (i>=rs.getInt(1)) {
//			Por ser o ultimo item termina tudo!!!
			System.out.println("vai executar o monta tot.");
			montaTot(rs,iY);
			setBordaRel();
			termPagina();
		  }
		  else if (iY > 410) {
//			Se estourou o limite de linhas, pula pra outra pagina:
			System.out.println("vai pular.");
			  iY = 230; 
			  montaTot(rs,iY);
			  setBordaRel();
			  termPagina();
			  bImpCab = true;
		  }
		}
		  
//Fecha a conta e passa a regua:
		finaliza();   
		rs.close();
		ps.close();
		System.out.println("Finalizou.");
	  }
	  catch(SQLException err) {
		  Funcoes.mensagemErro(this,"Erro ao montar conteúdo do relatório!!!\n"+err.getMessage());
	  }

	}
	
	public void setParam(Vector vParam) {
		vParamOrc = vParam;
	}

	public void setConexao(Connection cn) {
		con = cn;
	}
	
	private void montaCab(ResultSet rs) {
	  int iY = 35;
	  try {
		montaCabEmp(con);
	  	
		drawLinha(0,iY,0,0,AL_LL);
		
		iY += 20;

		setFonte(fnTopEmp);
		drawTexto("CONTRATO DE LOCAÇÃO",0,iY,getFontMetrics(fnCabEmp).stringWidth("CONTRATO DE LOCAÇÃO"),AL_CEN);
		setFonte(fnCabEmpNeg);

		iY += 15;		
		drawLinha(0,iY,0,0,AL_LL);
		iY += 15;
		drawTexto(rs.getString("CidFilial").trim()+", "+Funcoes.dateToStrExtenso(new Date()),5,iY);
		iY += 5;
		drawLinha(0,iY,0,0,AL_LL);
	  }
	  catch (SQLException err) {
		Funcoes.mensagemErro(this,"Erro ao montar o cabeçalho do relatório!!!\n"+err.getMessage());
	  }
	}	
	
	private void montaTot(ResultSet rs, int iY) {
	  setFont(fnCabEmp);
	  try {	
		iY = impLabel("que quando da entrega deverá(ão) estar em perfeito estado de uso e conservação, ou seja, no mesmo estado que recebeu da LOCATARIA.",15,5,540,iY+5)+5;
		String sValLiq = Funcoes.strDecimalToStrCurrency(2,rs.getString("VlrLiqOrc"));
		drawTexto("2° O preço do presente contrato é de R$ "+sValLiq.trim()+".",5,iY);
		iY += 15;	
		drawTexto("3° O primeiro pagamento efetuado pelo LOCADOR do objeto do contrato será no ato da locação.",5,iY);
        iY +=15;
        String sTexto = "4° Os pagamentos subsequentes a contar de períodos iguais a 30 (trinta) dias, deverão ser pagos na sede da LOCATARIA, no dia 10 de cada mês.";
        iY = impLabel(sTexto,15,5,540,iY)+5;
        sTexto = "5° O presente contrato é valido até a data final do último pagamento, ou com as concordâncias das partes, será renovado, até o prazo que se fizer necessário."; 
        iY = impLabel(sTexto,15,5,540,iY)+5;
		sTexto = "6° Na assinatura do presente deverá o LOCADOR deixar um cheque de caução, no mesmo valor do objeto, ora locado. Que passado o período de 05(cinco) dias após o vencimento de qualquer uma das parcelas autoriza a LOCATARIA a descontar o cheque abaixo descrito, pois o contrato se fará rescindido."; 
		iY = impLabel(sTexto,15,5,540,iY)+25;

        setFonte(fnCabEmpNeg);
        drawTexto("Cheque nro.:",5,iY);
		drawTexto("Banco:",60,iY);         
		drawTexto("Agência:",200,iY);
		drawTexto("Praça:",350,iY);
		iY += 20;
		drawTexto("Responsável/Cheque:",5,iY);
		iY += 20;
		drawTexto("Endereço:",5,iY);
		iY += 20;
		drawTexto("Valor:",5,iY);
		iY += 25;
      
        setFonte(fnCabEmp);

		sTexto = "7° A LOCATARIA assume a responsabilidade de entregar ao LOCADOR, objetos em perfeito estade de funcionamento."; 
		iY = impLabel(sTexto,15,5,540,iY)+5;
		sTexto = "8° O presente contrato será emitido em 02 (duas) vias de igual teor e conteúdo, a 1° via ficará de posse da LOCATARIA e a segunda em poder do LOCADOR."; 
		iY = impLabel(sTexto,15,5,540,iY)+25;
		
		drawTexto("Assinatura do LOCATARIO:",5,iY);
		drawLinha(130,iY,400,iY);
		iY += 20;
		drawTexto("Assinatura do LOCADOR:",5,iY);
		drawLinha(130,iY,400,iY);
		  
	  }
	  catch (SQLException err) {
		Funcoes.mensagemErro(this,"Erro ao montar totalizadores do relatório!!!\n"+err.getMessage());
	  }
	}


}