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
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import org.freedom.funcoes.Funcoes;
import org.freedom.layout.LeiauteGR;

public class GerContas extends LeiauteGR {
	private Connection con = null;
	private ResultSet rs = null;
	Vector vParamOrc = new Vector();
	
	public void montaG() {
		impRaz(false);
		montaCabEmp(con);	
		montaRel();
	}
	

	private void montaRel() {
	  imprimeRodape(false);
	
      try {	    
	
      	setBordaRel();
      
      	drawTexto("GERENCIAMENTO DE CONTAS",20,20,100,AL_CEN);
      	
      	
      	
      	
      	
      	
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
		vParamOrc = vParam;
	}

	public void setConsulta(ResultSet rs2) {
		rs = rs2;
	}
	public void setConexao(Connection cn) {
	  con = cn;
	}

}