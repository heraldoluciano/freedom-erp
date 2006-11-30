/**
 * @version 14/11/2000 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 * 
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.telas <BR>
 * Classe:
 * @(#)FPrincipal.java <BR>
 * 
 * Este programa é licenciado de acordo com a LPG-PC (Licença Pública Geral para
 * Programas de Computador), <BR>
 * versão 2.1.0 ou qualquer versão posterior. <BR>
 * A LPG-PC deve acompanhar todas PUBLICAÇÕES, DISTRIBUIÇÕES e REPRODUÇÕES deste
 * Programa. <BR>
 * Caso uma cópia da LPG-PC não esteja disponível junto com este Programa, você
 * pode contatar <BR>
 * o LICENCIADOR ou então pegar uma cópia em: <BR>
 * Licença: http://www.lpg.adv.br/licencas/lpgpc.rtf <BR>
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa é
 * preciso estar <BR>
 * de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 * Comentários para a classe...
 */

package org.freedom.telas;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.freedom.bmps.Icone;
import org.freedom.funcoes.Funcoes;
import org.freedom.plugin.Backgroud34;

public class FPrincipal2 extends FPrincipal implements ActionListener, MouseListener {
	private static final long serialVersionUID = 1L;
	
	private Backgroud34 fundo = new Backgroud34();
	
	public FPrincipal2(String sImgFundo) {
		super(null, sImgFundo);
	}

	public void inicializaTela() {
		setBgColor(new Color(255,255,255)); 
	    addLinks(Icone.novo("lgSTP2.jpg"), Icone.novo("lgFreedom2.jpg"));
		adicBotoes();
		adicAgenda();
	}
	
	public void setConexao( Connection con ) {
		super.setConexao( con );
		fundo.setConexao( con );
		addFundo( fundo );
	}

	public void remConFilial(){ 	
		String sSQL = "EXECUTE PROCEDURE SGFIMCONSP(?,?,?)";
		try {
			PreparedStatement ps = con.prepareStatement( sSQL );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, Aplicativo.iCodFilialPad );
			ps.setString( 3, Aplicativo.strUsuario );
			ps.execute();
			ps.close();
			if ( !con.getAutoCommit() )
				con.commit();
		} catch ( SQLException err ) {
			Funcoes.mensagemErro(null, "Erro ao remover filial ativa no banco!\n" + err.getMessage() );
		}
	}	
	
	public void fecharJanela() {
		if ( con != null ) {
			try {
				remConFilial();
				con.close();
			} catch ( java.sql.SQLException e ) {
				Funcoes.mensagemErro(null, "Não foi possível fechar a conexao com o banco de dados!" );
			}
		}
		System.exit( 0 );
	}
		
}