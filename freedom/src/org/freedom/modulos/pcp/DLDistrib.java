/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Informática Ltda./Alex Rodrigues <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)DLDistrib.java <BR>
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
 */

package org.freedom.modulos.pcp;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;

import javax.swing.JScrollPane;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Tabela;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FFDialogo;

public class DLDistrib extends FFDialogo implements MouseListener{

	private int casasDec = Aplicativo.casasDec;
	private JTextFieldPad txtSeqEF = new JTextFieldPad(JTextFieldPad.TP_INTEGER,5,0);
	private JTextFieldPad txtCodFase = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
	private JTextFieldPad txtSeqDE = new JTextFieldPad(JTextFieldPad.TP_INTEGER,5,0);
	private JTextFieldPad txtCodProd = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
	private JTextFieldPad txtSeqEstDE = new JTextFieldPad(JTextFieldPad.TP_INTEGER,5,0);
	private JTextFieldPad txtQuant = new JTextFieldPad(JTextFieldPad.TP_NUMERIC,15,casasDec);
	private JPanelPad pnDistrib = new JPanelPad(JPanelPad.TP_JPANEL,new BorderLayout());
	private JPanelPad pinCab = new JPanelPad(400,60);
	private Tabela tabDistrib = new Tabela();
	private ListaCampos lcDistrib = new ListaCampos(this);
  
  public DLDistrib(Connection cn,Component cOrig) {
  	super(cOrig);
    setConexao(cn);
    setTitulo("Distribuição");
    setAtribos(470,300);
    
    lcDistrib.setTabela(tabDistrib);
   
    pinCab.setPreferredSize(new Dimension(400,60));
    pnDistrib.add(pinCab,BorderLayout.NORTH);
    JScrollPane spnTabRec = new JScrollPane(tabDistrib);
    pnDistrib.add(spnTabRec,BorderLayout.CENTER);

    c.add(pnDistrib, BorderLayout.CENTER);
    
    txtSeqEF.setNomeCampo("SeqEF");
    lcDistrib.add(new GuardaCampo( txtSeqEF, "SeqEF", "Seq.fase", ListaCampos.DB_PK,false));
    lcDistrib.add(new GuardaCampo( txtCodFase, "CodFase", "Cód.fase", ListaCampos.DB_SI,false));
    lcDistrib.add(new GuardaCampo( txtSeqDE, "SeqDE", "Seq.distrib.", ListaCampos.DB_SI,false));
    lcDistrib.add(new GuardaCampo( txtCodProd, "CodProd", "Cód.prod", ListaCampos.DB_SI,false));
    lcDistrib.add(new GuardaCampo( txtSeqEstDE, "SeqEstDE", "N.distrib.", ListaCampos.DB_SI,false));
   // lcDistrib.add(new GuardaCampo( txtQuant, "", "Quantidade", ListaCampos.DB_SI,false));
    lcDistrib.montaSql(false, "DISTRIB", "PP");
    lcDistrib.setConexao(cn);
    txtSeqEF.setListaCampos(lcDistrib);
    txtCodFase.setListaCampos(lcDistrib);
    txtSeqDE.setListaCampos(lcDistrib);
    txtCodProd.setListaCampos(lcDistrib);
    txtSeqEstDE.setListaCampos(lcDistrib);
    lcDistrib.montaTab();
    tabDistrib.addMouseListener(this);
    
  }
  public Object[] getValores() {
    Object[] oRetorno = new Object[3];

    return oRetorno;
  }
  
  public void mouseClicked(MouseEvent mevt) {
    if (mevt.getClickCount() == 2) {
    	if (mevt.getSource() == tabDistrib && tabDistrib.getLinhaSel() >= 0)
    	  alteraDistrib();
    }
  }
  
  public void alteraDistrib(){
	  	DLFechaDistrib dl = new DLFechaDistrib(DLDistrib.this);
		dl.setVisible(true);
		if (dl.OK) {
			dl.dispose();
		} else {
			dl.dispose();
		}
  }
  
  public void mouseEntered(MouseEvent e) { }
  public void mouseExited(MouseEvent e) { }
  public void mousePressed(MouseEvent e) { }
  public void mouseReleased(MouseEvent e) { }
  
}
