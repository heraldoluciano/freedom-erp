/**
 * @version 04/06/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 * 
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.componentes <BR>
 * Classe:
 * @(#)JComboBoxPad.java <BR>
 * 
 * Este arquivo é parte do sistema Freedom-ERP, o Freedom-ERP é um software livre; você pode redistribui-lo e/ou <BR>
 * modifica-lo dentro dos termos da Licença Pública Geral GNU como publicada pela Fundação do Software Livre (FSF); <BR>
 * na versão 2 da Licença, ou (na sua opnião) qualquer versão. <BR>
 * Este programa é distribuido na esperança que possa ser  util, mas SEM NENHUMA GARANTIA; <BR>
 * sem uma garantia implicita de ADEQUAÇÂO a qualquer MERCADO ou APLICAÇÃO EM PARTICULAR. <BR>
 * Veja a Licença Pública Geral GNU para maiores detalhes. <BR>
 * Você deve ter recebido uma cópia da Licença Pública Geral GNU junto com este programa, se não, <BR>
 * de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 * Campo do tipo combobox.
 */

package org.freedom.library.swing.component;

import java.awt.Cursor;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Types;
import java.util.Vector;

import javax.swing.JComboBox;

import org.freedom.acao.JComboBoxEvent;
import org.freedom.acao.JComboBoxListener;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.frame.Aplicativo;

//public class JComboBoxPad<S, T> extends JComboBox implements JComboBoxListener, ItemListener {

public class JComboBoxPad extends JComboBox implements JComboBoxListener, ItemListener {

	private static final long serialVersionUID = 1L;

	public static final int TP_NONE = -1;

	public static final int TP_STRING = 0;

	public static final int TP_INTEGER = 4;

	private Vector<?> valores = new Vector<Object>();

	private JComboBoxListener cbLis = this;

	private ListaCampos lcCombo = null;

	private boolean criando = true;

	private int tipo = -1;

	private int tam = 8;

	private int dec = 0;

	private boolean bZeroNull = false;

	private String nomecampo;

	private String nomecampolabel;

	private String tabelaexterna;

	private String whereadic;

	private String orderby;

	public void setZeroNulo() {

		bZeroNull = true;
	}

	public JComboBoxPad(Vector<String> label, Vector<?> val, int tipo, int tam, int dec) {

		criando = true;
		if (val != null && label != null) {
			valores = val;
			for (int i = 0; i < label.size(); i++) {
				addItem(label.elementAt(i));
			}
		}
		addItemListener(this);
		this.tipo = tipo;
		this.tam = tam;
		this.dec = dec;
		criando = false;
		this.setCursor(new Cursor(Cursor.HAND_CURSOR));

	}

	public void setItens(Vector<String> label, Vector<Object> val) {

		criando = true;
		removeAllItems();
		valores = val;

		for (int i = 0; i < label.size(); i++) {
			addItem(label.elementAt(i));
		}
		criando = false;
	}

	public void setItensGeneric(Vector<?> label, Vector<?> val) {

		criando = true;
		removeAllItems();
		valores = val;

		for (int i = 0; i < label.size(); i++) {
			addItem(label.elementAt(i));
		}
		criando = false;
	}

	public void setAtivo(boolean bVal) {

		setEnabled(bVal);
	}

	public void limpa() {

		if (getItemCount() > 0) {
			setSelectedIndex(0);
		}
	}

	public int getTipo() {

		return tipo;
	}

	public int getTam() {

		return tam;
	}

	public int getDec() {

		return dec;
	}

	public String getVlrString() {

		int iInd = getSelectedIndex();
		if (valores != null && iInd >= 0 && iInd < valores.size()) {
			return valores.elementAt(getSelectedIndex()).toString();
		}
		return "";
	}

	public String getText() {

		String retorno = "";
		int iInd = getSelectedIndex();
		if (valores != null && iInd >= 0 && iInd < valores.size()) {
			retorno = valores.elementAt(getSelectedIndex()).toString();
		}
		return retorno;
	}

	public Integer getVlrInteger() {

		try {
			if (( Integer ) valores.elementAt(getSelectedIndex()) == new Integer(0) && bZeroNull) {
				return null;
			}
			return ( Integer ) valores.elementAt(getSelectedIndex());
		}
		catch (Exception err) {
			return new Integer(0);
		}
	}

	public void setVlrString(String val) {

		for (int i = 0; i < valores.size(); i++) {
			if (valores.elementAt(i).equals(val)) {
				setSelectedIndex(i);
				fireValorAlterado(i);
				break;
			}
		}
	}

	public void setVlrInteger(Integer val) {

		for (int i = 0; i < valores.size(); i++) {
			if (valores.elementAt(i).equals(val)) {
				setSelectedIndex(i);
				fireValorAlterado(i);
				break;
			}
		}
	}

	public void setListaCampos(ListaCampos lc) {

		lcCombo = lc;
	}

	public void addComboBoxListener(JComboBoxListener cb) {

		cbLis = cb;
	}

	private void fireValorAlterado(int ind) {

		cbLis.valorAlterado(new JComboBoxEvent(this, ind));
	}

	public void valorAlterado(JComboBoxEvent cbevt) {

		if (( !criando ) && ( lcCombo != null )) {
			if (lcCombo.getStatus() == ListaCampos.LCS_SELECT) {
				lcCombo.edit();
			}
		}
	}

	public void itemStateChanged(ItemEvent itevt) {

		if (itevt.getStateChange() == ItemEvent.SELECTED) {
			fireValorAlterado(getSelectedIndex());
		}
	}

	public String getNomecampo() {
		return nomecampo;
	}

	public void setNomecampo(String nomecampo) {
		this.nomecampo = nomecampo;
	}

	public String getNomecampolabel() {
		return nomecampolabel;
	}

	public void setNomecampolabel(String nomecampolabel) {
		this.nomecampolabel = nomecampolabel;
	}

	public String getTabelaexterna() {
		return tabelaexterna;
	}

	public void setTabelaexterna(String tabelaexterna) {
		this.tabelaexterna = tabelaexterna;
	}

	public String getWhereadic() {
		return whereadic;
	}

	public void setWhereadic(String whereadic) {
		this.whereadic = whereadic;
	}

	public String getOrderby() {
		return orderby;
	}

	public void setOrderby(String orderby) {
		this.orderby = orderby;
	}

	public void carregaValores() {
		ResultSet rs = null;
		PreparedStatement ps = null;
		StringBuilder sql = new StringBuilder();
		Vector<Object> values = new Vector<Object>();
		Vector<String> labels = new Vector<String>();
		DbConnection con = null;

		try {

			con = Aplicativo.getInstace().getConexao();

			if (con != null) {

				sql.append("select ");
				sql.append(getNomecampo());
				sql.append(",");
				sql.append(getNomecampolabel());

				sql.append(" from ");
				sql.append(getTabelaexterna());
				sql.append(" where codemp=? and codfilial=?");

				if (getWhereadic() != null) {
					sql.append(" and " + getWhereadic());
				}

				if (getOrderby() != null) {
					sql.append(" order by " + getOrderby());
				}

				ps = con.prepareStatement(sql.toString());

				int param = 1;

				ps.setInt(param++, Aplicativo.iCodEmp);
				ps.setInt(param++, ListaCampos.getMasterFilial(getTabelaexterna()));

				rs = ps.executeQuery();

				ResultSetMetaData metadata = rs.getMetaData();

				boolean first = true;

				while (rs.next()) {
					if (metadata.getColumnType(1) == Types.INTEGER) {
						if (first) {
							values.add(new Integer(-1));
						}
						values.add(new Integer(rs.getInt(1)));
					}
					else {
						if (first) {
							values.add("-1");
						}
						values.add(rs.getString(( 1 )));
					}
					if (first) {
						labels.add("<Selecione>");
					}
					labels.add(rs.getString(2));

					first = false;
				}

				rs.close();
				con.commit();
				ps.close();

				setItens(labels, values);

			}
			else {
				System.out.println("Conexão nula no combobox!");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void setAutoSelect(String nomecampo, String nomecampolabel, String tabelaexterna) {

		setNomecampo(nomecampo);
		setNomecampolabel(nomecampolabel);
		setTabelaexterna(tabelaexterna);

	}

}
