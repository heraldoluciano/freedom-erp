/**
 * @version 02/11/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FSerie.java <BR>
 * 
 * Este arquivo é parte do sistema Freedom-ERP, o Freedom-ERP é um software livre; você pode redistribui-lo e/ou <BR>
 * modifica-lo dentro dos termos da Licença Pública Geral GNU como publicada pela Fundação do Software Livre (FSF); <BR>
 * na versão 2 da Licença, ou (na sua opnião) qualquer versão. <BR>
 * Este programa é distribuido na esperança que possa ser  util, mas SEM NENHUMA GARANTIA; <BR>
 * sem uma garantia implicita de ADEQUAÇÂO a qualquer MERCADO ou APLICAÇÃO EM PARTICULAR. <BR>
 * Veja a Licença Pública Geral GNU para maiores detalhes. <BR>
 * Você deve ter recebido uma cópia da Licença Pública Geral GNU junto com este programa, se não, <BR>
 * escreva para a Fundação do Software Livre(FSF) Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA <BR> <BR>
 *
 * Tela de cadastro de séries.
 * 
 */

package org.freedom.modulos.std;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.freedom.infra.model.jdbc.DbConnection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JButton;
import org.freedom.componentes.JLabelPad;

import org.freedom.bmps.Icone;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FDados;
import org.freedom.telas.FFDialogo;

public class FSerie extends FDados implements ActionListener {
	private static final long serialVersionUID = 1L;


  private JTextFieldPad txtSerie = new JTextFieldPad(JTextFieldPad.TP_STRING,4,0);
  private JTextFieldPad txtDocSerie= new JTextFieldPad(JTextFieldPad.TP_INTEGER,5,0);
  private JButton btReset = new JButton(Icone.novo("btResetcont.gif"));
  private JTextFieldPad txtReset = new JTextFieldPad(JTextFieldPad.TP_INTEGER,5,0);
  private JLabelPad lbReset = new JLabelPad("Novo nº.");
  public FSerie () {
  	super();
    setTitulo("Cadastro de Série de Notas");
    setAtribos( 50, 50, 350, 165);
    
    btReset.setToolTipText("Reiniciar contagem");
    
    txtDocSerie.setSoLeitura(true); 

    adicCampo(txtSerie, 7, 20, 70, 20,"Serie","Cód.série",ListaCampos.DB_PK,null,true);
    adicCampo(txtDocSerie, 80, 20, 250, 20,"DocSerie","Doc. número",ListaCampos.DB_SI,null,false);
       
    adic(btReset,100,50,130,30);
    setListaCampos( false, "SERIE", "LF");

    btReset.addActionListener(this);
    nav.setAtivo(2,false);    
  }
  private void resetar() {
    FFDialogo dlReset = new FFDialogo(this);
    dlReset.setTitulo("Reset");
    dlReset.setAtribos(280,140);
    dlReset.adic(lbReset,7,5,100,20);
    dlReset.adic(txtReset,7,25,100,20);
    dlReset.setVisible(true);
    if (dlReset.OK) 
      gravaReset();
    dlReset.dispose();
    lcCampos.carregaDados();
    
  }
  private void gravaReset() {
    PreparedStatement ps = null;
    try {
      ps = con.prepareStatement("UPDATE LFSERIE SET DOCSERIE=? WHERE SERIE=? AND CODEMP=? AND CODFILIAL=?");
      ps.setInt(1,txtReset.getVlrInteger().intValue());
      ps.setString(2,txtSerie.getVlrString());
      ps.setInt(3,Aplicativo.iCodEmp);
      ps.setInt(4,lcCampos.getCodFilial());
      ps.executeUpdate();
//      ps.close();
   	  con.commit();
    }
    catch(SQLException err) {
		Funcoes.mensagemErro(this,"Erro ao gravar o número inicial!\n"+err.getMessage(),true,con,err);
    }
  }
  public void actionPerformed(ActionEvent evt) {
    if (evt.getSource() == btReset) {
      if (lcCampos.getStatus() == ListaCampos.LCS_SELECT) {
        resetar();
      }
    }
    super.actionPerformed(evt);
  }
  public void setConexao(DbConnection cn) {
    super.setConexao(cn);
  }
}

