/**
 * @version 08/01/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.cfg <BR>
 * Classe: @(#)FGrupo.java <BR>
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

package org.freedom.modulos.cfg;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.freedom.componentes.JLabelPad;
import javax.swing.JScrollPane;

import org.freedom.acao.DeleteEvent;
import org.freedom.acao.DeleteListener;
import org.freedom.acao.PostEvent;
import org.freedom.acao.PostListener;
import org.freedom.componentes.JTextAreaPad;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.FDados;

public class FGrupo extends FDados implements PostListener,DeleteListener { 
  private static final long serialVersionUID = 1L;
  private JTextFieldPad txtCodGrup = new JTextFieldPad(JTextFieldPad.TP_STRING, 8, 0);
  private JTextFieldPad txtNomeGrup = new JTextFieldPad(JTextFieldPad.TP_STRING, 50, 0);
  private JTextAreaPad txaComentGrup = new JTextAreaPad(JTextFieldPad.TP_STRING);
  private JScrollPane spnObs = new JScrollPane(txaComentGrup);
  public FGrupo () {
  	super();
    setTitulo("Cadastro de Grupos");
    setAtribos( 50, 50, 400, 200);

    adicCampo(txtCodGrup, 7, 20, 80, 20, "IDGrpUsu", "ID", ListaCampos.DB_PK, true);
    adicCampo(txtNomeGrup, 90, 20, 282, 20, "NomeGrpUsu", "Nome", ListaCampos.DB_SI, true);
    adicDBLiv(txaComentGrup, "ComentGrpUsu", "Comentário", false);
    adic(new JLabelPad("Comentário"),7,40,100,20);
    adic(spnObs,7,60,365,60);
    setListaCampos( false, "GRPUSU", "SG");
    lcCampos.addPostListener(this);
    lcCampos.addDeleteListener(this);
    lcCampos.setQueryInsert(false);    
  }
  public void beforePost(PostEvent pevt) {
  	if (lcCampos.getStatus() != ListaCampos.LCS_INSERT)
  		return;
  	try {
  		PreparedStatement ps = con.prepareStatement("CREATE ROLE "+txtCodGrup.getVlrString());
  		ps.execute();
  		ps.close();
  		if (!con.getAutoCommit())
  			con.commit();
  	}
  	catch(SQLException err) {
		Funcoes.mensagemInforma(this,"Não foi possível criar grupo no banco de dados.\n"+err);
        pevt.cancela();
  	}  		
  }
  public void beforeDelete(DeleteEvent devt) {
  	try {
  		PreparedStatement ps = con.prepareStatement("DROP ROLE "+txtCodGrup.getVlrString());
  		ps.execute();
  		ps.close();
  		if (!con.getAutoCommit())
  			con.commit();
  	}
  	catch(SQLException err) {
		Funcoes.mensagemInforma(this,"Não foi possível excluir o grupo no banco de dados.\n"+err);
        devt.cancela();
  	}  		
  }
  public void afterPost(PostEvent pevt) { }
  public void afterDelete(DeleteEvent devt) { }
}
