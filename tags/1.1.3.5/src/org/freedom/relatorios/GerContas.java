/**
 * @version 05/03/2005 <BR>
 * @author Setpoint Informática Ltda./Anderson Sanchez <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.relatorios <BR>
 * Classe: @(#)GerContas.java <BR>
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
 * Gráfico de evolução de vendas no formato de linha variante.
 * 
 */

package org.freedom.relatorios;
import java.awt.Font;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import org.freedom.funcoes.Funcoes;
import org.freedom.layout.LeiauteGR;


public class GerContas extends LeiauteGR {
	private static final long serialVersionUID = 1L;

	private Connection con = null;
	private Font fnTituloNeg1 = new Font("Arial",Font.BOLD,10);
	private Font fnTituloNeg2 = new Font("Arial",Font.BOLD,8);
	private Font fnTituloNeg3 = new Font("Arial",Font.BOLD,6);
	private Font fnExtraNeg = new Font("Arial",Font.BOLD,16);
	private ResultSet rs = null;
	private Vector vParam = new Vector();
	
	public void montaG() {
		impRaz(false);
//		montaCabEmp(con);	
		montaRel();
		setBordaRel();
	}
	

	private void montaRel() {
	  imprimeRodape(false);
	
      try {	    
	
      	setBordaRel();

      	setFonte(fnExtraNeg);        
        drawTexto(vParam.elementAt(0).toString(),50,20);
        setFonte(fnTituloNeg1);
      	drawTexto("GERENCIAMENTO DE CONTAS",100,20);
      	
        setFonte(fnTituloNeg2);
        
      	drawRetangulo(10,30,120,30);//Conta
      	drawTexto("CONTA",10,48,120,AL_BCEN);      	
      	drawRetangulo(130,30,30,30);//Cod.
  		drawTexto("CÓD.",130,48,30,AL_BCEN); 
      	drawRetangulo(160,30,30,30);//Cidade
		drawTexto("CID.",160,48,30,AL_BCEN);
      	drawRetangulo(190,30,30,30);//Cat
      	drawTexto("CAT.",190,48,30,AL_BCEN);
      	drawRetangulo(220,30,20,30);//Class
  		drawTexto("CL.",220,48,20,AL_BCEN);
      	drawRetangulo(240,30,180,15);//Contatos 1
		drawTexto("CONTATOS",240,40,180,AL_BCEN);
      	drawRetangulo(420,30,30,30);//Tot
      	drawTexto("TOT.",420,48,30,AL_BCEN);
       	drawRetangulo(450,30,60,30);//%afto
      	drawTexto("% AFTO.",450,48,60,AL_BCEN);
      	drawRetangulo(510,30,60,30);//20%
      	drawTexto("20 %",570,48,60,AL_BCEN);      		      		      		
			
	    setFonte(fnTituloNeg3);
	      	
		drawRetangulo(240,45,15,15);//jan
      	drawTexto("Jan",242,54);
      	drawRetangulo(255,45,15,15);//fev
  		drawTexto("Fev",257,54);
      	drawRetangulo(270,45,15,15);//mar
		drawTexto("Mar",272,54);
      	drawRetangulo(285,45,15,15);//abr
      	drawTexto("Abr",287,54);
      	drawRetangulo(300,45,15,15);//mai      	
      	drawTexto("Mai",302,54);
      	drawRetangulo(315,45,15,15);//jun      	
      	drawTexto("Jun",317,54);
      	drawRetangulo(330,45,15,15);//jul      	
      	drawTexto("Jul",332,54);
      	drawRetangulo(345,45,15,15);//ago
      	drawTexto("Ago",347,54);
      	drawRetangulo(360,45,15,15);//set
      	drawTexto("Set",362,54);
      	drawRetangulo(375,45,15,15);//out
      	drawTexto("Out",377,54);
      	drawRetangulo(390,45,15,15);//nov
      	drawTexto("Nov",392,54);
      	drawRetangulo(405,45,15,15);//dez
      	drawTexto("Dez",407,54);      	
      	
      	
      	while (rs.next()) {

		}
	
	  }
	  catch (SQLException e) {
		Funcoes.mensagemInforma(this,"Erro na consulta!\n"+e.getMessage());
	  }

	  termPagina();
	  finaliza();
	}

	public void setParam(Vector vParam) {
		this.vParam = vParam;
	}

	public void setConsulta(ResultSet rs2) {
		rs = rs2;
	}
	public void setConexao(Connection cn) {
	  con = cn;
	}
	public Connection getConexao() {
		  return con;
		}
}