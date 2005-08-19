/**
 * @version 05/07/2005 <BR>
 * @author Setpoint Informática Ltda./Robson Sanchez <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.componentes <BR>
 * Classe:
 * @(#)TabVector.java <BR>
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
 * Comentários sobre a classe...
 *  
 */
package org.freedom.componentes;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Vector;

public class TabVector {

	private Vector vRows = new Vector();
	int cols = 0;
	int row = -1;
	public TabVector(int cols) {
		super();
		this.cols = cols;
	}
	
	public void setRow(int row) {
		this.row = row;
	}
	
	public boolean next() {
		if (row<(vRows.size())) {
			row++;
			return true;
		}
		else
			return false;
	}
	
	public void addRow() {
		Vector vTemp = new Vector();
		vTemp.setSize(cols);
		vRows.addElement(vTemp);
		row++;
	}
	
	public void setInt(int pos, int value) {
		setObject(pos, new Integer(value));
	}
	
	public int getInt(int pos) {
		int value = 0;
		Object o = getObject(pos);
		if (o!=null) 
			value = ((Integer) o).intValue();
		return value;
	}

	public void setString(int pos, String value) {
		setObject(pos, value);
	}
	
	public String getString(int pos) {
		String value = null;
		Object o = getObject(pos);
		if (o!=null) 
			value = o.toString();
		return value;
	}
	
	public void setDate(int pos, Date value) {
		setObject(pos, value);
	}
	
	public Date getDate(int pos) {
		Date value = null;
		Object o = getObject(pos);
		if (o!=null) 
			value = (Date) o;
		return value;
	}
	
	public void setFloat(int pos, float value) {
		setObject(pos, new BigDecimal(value));
	}
	
	public float getFloat(int pos) {
		float value = 0;
		Object o = getObject(pos);
		if (o!=null) 
			value = ((BigDecimal) o).floatValue();
		return value;
	}
	public void setObject(int pos, Object obj) {
		Vector v = getCol(this.row);
		if (v!=null) { 
			v.setElementAt(obj, pos);
			vRows.setElementAt(v, this.row);
		}
	}

	public Object getObject(int pos) {
		Object o = null;
		Vector v = getCol(this.row);
		if (v!=null) 
			o = v.elementAt(pos);
		return o;
	}
	
	public Vector getCol(int row) {
		Vector col = null;
		if ((row!=-1) && (row<vRows.size()) )
			col = (Vector) vRows.elementAt(row);
		return col;	
	}

}
