/**
 * @version 14/17/2003 <BR>
 * @author Setpoint Informática Ltda./Anderson Sanchez <BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: leiautes <BR>
 * Classe: @(#)FichaMedida.java <BR>
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
 * Classe para impressão do documento ficha de medida.
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


public class FichaMedida extends LeiauteGR {
	private static final long serialVersionUID = 1L;
	private Connection con = null;
	private int iCodConv = 0;
	private String sAtend = "";
	private int iCodLev = 0;
	private Font fnTopEmp = new Font("Arial",Font.BOLD,11);
	private Font fnCabEmp = new Font("Arial",Font.PLAIN,8);
	private Font fnCabEmpNeg = new Font("Arial",Font.BOLD,8);
	public void montaG() {
		montaCabEmp(con);
		montaCab();
	}
	private void montaCab() {
		try {
		  String sSQL = "SELECT C.CODCONV,C.NOMECONV,C.CIDCONV,C.UFCONV,T.DESCTPCONV,C.DTNASCCONV,"+
		                "(SELECT TA.DESCTPATEND FROM ATATENDENTE A1, ATTIPOATEND TA WHERE "+
						"A1.CODEMP=C.CODEMPAE AND A1.CODFILIAL=C.CODFILIALAE AND A1.CODATEND=C.CODATEND AND "+
                        "TA.CODEMP=A1.CODEMPTA AND TA.CODFILIAL=A1.CODFILIALTA AND TA.CODTPATEND=A1.CODTPATEND),"+
						"(SELECT A2.NOMEATEND FROM ATATENDENTE A2 WHERE "+
						"A2.CODEMP=C.CODEMPAE AND A2.CODFILIAL=C.CODFILIALAE AND A2.CODATEND=C.CODATEND ),"+
						"(SELECT EC.NOMEENC FROM ATENCAMINHADOR EC WHERE "+
						"EC.CODEMP=C.CODEMP AND EC.CODFILIAL=C.CODFILIAL AND EC.CODENC=C.CODENC ),"+
						"(SELECT EC.CIDENC FROM ATENCAMINHADOR EC WHERE "+
						"EC.CODEMP=C.CODEMP AND EC.CODFILIAL=C.CODFILIAL AND EC.CODENC=C.CODENC ),"+
						"C.FONECONV,CL.RAZCLI, CL.CIDCLI"+
		                " FROM ATCONVENIADO C, ATTIPOCONV T,VDCLIENTE CL"+
		                " WHERE T.CODEMP=C.CODEMPTC AND T.CODFILIAL=C.CODFILIALTC AND T.CODTPCONV=C.CODTPCONV"+
		                " AND CL.CODCLI=C.CODCLI AND CL.CODEMP=C.CODEMPCL AND CL.CODFILIAL=C.CODFILIALCL"+
						" AND C.CODCONV=? AND C.CODEMP=? AND C.CODFILIAL=?";
		  
		  PreparedStatement ps = con.prepareStatement(sSQL);
		  ps.setInt(1,iCodConv);
		  ps.setInt(2,Aplicativo.iCodEmp);
		  ps.setInt(3,ListaCampos.getMasterFilial("ATCONVENIADO"));
		  ResultSet rs = ps.executeQuery();
		  if (rs.next()) {
		  	int iY = 35; 

			montaCabEmp(con);
	  	
			drawLinha(0,iY,0,0,AL_LL);
		
			iY += 20;

			setFonte(fnTopEmp);
			drawTexto("FICHA DE MEDIDA",0,iY,getFontMetrics(fnCabEmp).stringWidth("FICHA DE MEDIDA"),AL_CEN);
			setFonte(fnCabEmpNeg);

			iY += 10;
		
			drawLinha(0,iY,0,0,AL_LL);
		
			iY += 10;

			setFonte(fnCabEmp);
			drawTexto("Ficha Nº:   ",5,iY); 
			setFonte(fnCabEmpNeg);
			drawTexto(Funcoes.strZero(""+iCodLev,8),65,iY);
			drawTexto("Emissão: ",285,iY); 
			setFonte(fnCabEmpNeg);
			drawTexto(Funcoes.dateToStrDataHora(new Date()),350,iY);


			iY+=5;

			drawLinha(0,iY,0,0,AL_LL);
		  	
			iY+=15;
			
			
			setFonte(new Font("Arial",Font.BOLD,10));
			drawTexto("Cliente:    ",5,iY);
					
			setFonte(new Font("Arial",Font.PLAIN,10));
			drawTexto(rs.getString("CodConv")+" - "+rs.getString("NomeConv").trim(),70,iY);
			
			setFonte(new Font("Arial",Font.BOLD,10));
			drawTexto("Atendente:",285,iY);
			setFonte(new Font("Arial",Font.PLAIN,10));
			drawTexto(sAtend,370,iY);
			
			iY+=20;
			
			setFonte(new Font("Arial",Font.BOLD,10));
			drawTexto("Convênio:",5,iY);
			setFonte(new Font("Arial",Font.PLAIN,10));
			//drawTexto(rs.getString("DescTpConv"),60,iY);
			drawTexto(rs.getString("DescTpConv").trim()+" / "+(rs.getString("RAZCLI")).trim()+" / "+(rs.getString("CIDCLI")).trim() ,70,iY);

			
			

			iY+=20;
			
			setFonte(new Font("Arial",Font.BOLD,10));
			drawTexto("Cidade:",5,iY);
			setFonte(new Font("Arial",Font.PLAIN,10));
			drawTexto(rs.getString("CidConv") != null ? rs.getString("CidConv").trim()+(rs.getString("UFConv")!=null ? " / "+rs.getString("UFConv"):""):"",60,iY);
						
			setFonte(new Font("Arial",Font.BOLD,10));
			//drawTexto((rs.getString(7) != null ? rs.getString(7) : "").trim()+" ",285,iY);
			//setFonte(new Font("Arial",Font.PLAIN,10));
			//drawTexto(rs.getString(8),315+getFontMetrics(new Font("Arial",Font.BOLD,8)).stringWidth((rs.getString(7) != null ? rs.getString(7) : "").trim()+" :  "),iY);

			if (rs.getString(9)!=null){
			setFonte(new Font("Arial",Font.BOLD,10));
			drawTexto("Unidade Enc. :",285,iY);
			setFonte(new Font("Arial",Font.PLAIN,10));
			drawTexto(Funcoes.copy((rs.getString(9) != null ? rs.getString(9) : "").trim()+" "+(rs.getString(10) != null ? " / "+rs.getString(10).trim():""),30),380,iY);
			}
			iY+=20;
			
			setFonte(new Font("Arial",Font.BOLD,10));
			drawTexto("Data de Nascimento:",5,iY);
			setFonte(new Font("Arial",Font.PLAIN,8));
			drawTexto(Funcoes.sqlDateToStrDate(rs.getDate("DtNascConv")),130,iY);

			setFonte(new Font("Arial",Font.BOLD,10));
			drawTexto("Cod. Produto: _________________",285,iY);
			
			iY+=20;
			
			setFonte(new Font("Arial",Font.BOLD,10));
			drawTexto("Telefone:",5,iY);
			setFonte(new Font("Arial",Font.PLAIN,10));
			drawTexto(rs.getString("FoneConv") != null ? Funcoes.setMascara(rs.getString("FoneConv"),"(####)####-####") : "",60,iY);

			setFonte(new Font("Arial",Font.BOLD,10));
			drawTexto("Tecnico: _______________, Ass.:_______________",285,iY);

			iY+=10;

			//drawLinha(275,80,275,iY);
			drawLinha(0,iY,0,0,AL_LL);
		  
		  }
		  termPagina();
		  finaliza();   
		  rs.close();
		  ps.close();
		}
		catch(SQLException err) {
			Funcoes.mensagemErro(this,"Erro ao montar o cabeçalho do relatório!!!\n"+err.getMessage());
		}
	}
	public void setParam(Vector vParam) {
	  iCodConv = Integer.parseInt(""+vParam.elementAt(0));
	  sAtend = ""+vParam.elementAt(1);
	  iCodLev = Integer.parseInt(""+vParam.elementAt(2));
	}
	public void setConexao(Connection cn) {
		con = cn;
	}}