/**
 * @version 01/02/2001 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.componentes <BR>
 * Classe: @(#)Tabela.java <BR>
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
 * Tabela tipo grid...
 */

package org.freedom.componentes;
import java.awt.Color;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import org.freedom.acao.TabelaEditEvent;
import org.freedom.acao.TabelaEditListener;
import org.freedom.acao.TabelaSelEvent;
import org.freedom.acao.TabelaSelListener;


public class Tabela extends JTable implements TabelaEditListener, TabelaSelListener {
  private Modelo modelo = new Modelo();
  int ContaColunas = 0;
  int ContaLinhas = 0;
  boolean bAutoRol = false;
  TabelaEditListener edlis = this;
  TabelaSelListener seLis = this;
  public Tabela() { 
    setModel(modelo);  
    setAutoResizeMode(AUTO_RESIZE_OFF);
    setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
    DefaultTableCellRenderer bigDecimalRenderer = new DefaultTableCellRenderer() {
	    public void setValue(Object value) {
   		    setText((value == null) ? "" : ""+value);
	    }
    };
    bigDecimalRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
    setDefaultRenderer(BigDecimal.class, bigDecimalRenderer);
    
    DefaultTableCellRenderer stringDireitaRenderer = new DefaultTableCellRenderer() {
    	public void setValue(Object value) {
    		setText((value == null) ? "" : ""+value);
    	}
    };
    stringDireitaRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
    setDefaultRenderer(StringDireita.class, stringDireitaRenderer);

	setDefaultEditor(Date.class, new DateEditor());

  }
  public void adicColuna(Object obj) {
    modelo.addColumn(obj);
    ContaColunas++;
  }
  public void tiraColuna(int ind) {
    if (ind < ContaColunas) {
      removeColumn(getColumnModel().getColumn(ind));
    }
    ContaColunas--;
  }
  public int getNumColunas() {
    return ContaColunas;
  }
  public void setTamColuna(int tam, int ind) {
    if (ind < ContaColunas)
      getColumnModel().getColumn(ind).setPreferredWidth(tam);
  }
  public void addTabelaEditListener(TabelaEditListener tl){
  	edlis = tl;
  	modelo.setTabelaEditListenerSrc(this);
  }
  public void addTabelaSelListener(TabelaSelListener tl){
	seLis = tl;
  }
  public void adicLinha() {
    Vector vVals = new Vector();
    for (int i= 0; i< ContaColunas; i++) {
      vVals.addElement("");
    }
    modelo.addRow(vVals);
    if (bAutoRol) {
            setLinhaSel(ContaLinhas);
    }
    ContaLinhas++;
  }
  public void adicLinha(Vector valores) {
    modelo.addRow(valores);
    if (bAutoRol) {
            setLinhaSel(ContaLinhas);
    }
    ContaLinhas++;
  }
  public void adicLinha(Object[] valores) {
	modelo.addRow(valores);
	if (bAutoRol) {
			setLinhaSel(ContaLinhas);
	}
	ContaLinhas++;
  }
  public int pesqLinha(int iCol, String sTexto) {
  	int iRetorno = -1;
  	int iTam = 0;
  	Vector vTemp = null;
  	try {
  		if (sTexto!=null) {
  			iTam = sTexto.length();
  			if (iTam>0) {
  				for (int i=0; i<getNumLinhas(); i++) {
  					vTemp = getLinha(i);
  					if ( ((String) vTemp.elementAt(iCol)).substring(0,iTam).equals(sTexto)) {
  						iRetorno = i;
  						break;
  					}
  				}
  			}
  		}
  	}
  	finally {
  		vTemp = null;
  	}
  	return iRetorno;
  }
  public void adicLinha(int iLin, Vector vDados) {
	modelo.insertRow(iLin,vDados);
	ContaLinhas++;
  }
  public void tiraLinha(int ind) {
    if (ind < ContaLinhas && ind >= 0) {
      boolean bSel = false;
	  bSel = (getLinhaSel() == ind);
      modelo.removeRow(ind);
      if (bSel && ind < (getNumLinhas()-1))
      	setLinhaSel(ind);
    }
    ContaLinhas--;
  }
  public Vector getLinha(int iInd) {
  	return modelo.getRow(iInd);
  }
  public int getNumLinhas() {
    return ContaLinhas;
  }
  public void setValor(Object obj, int lin, int col) {
  	if (obj == null)
  		obj = "";
    if ((lin < ContaLinhas) & (col < ContaColunas)) {
      modelo.setValueAt(obj, lin, col);
    }
  }
  public Object getValor(int lin, int col) {
    Object ret = null;
    if ((lin < ContaLinhas) & (col < ContaColunas)) {
      ret = modelo.getValueAt( lin, col);
    }
    return ret;    
  }
  public int getLinhaSel() {
    return getSelectedRow();
  }
  public int getColunaEditada() {
  	return getEditingColumn();
  }
  public int getLinhaEditada() {
	return getEditingRow();
  }
  public void setLinhaSel(int lin) {
    if (lin < 1)
      setRowSelectionInterval(0,0);
    else 
      setRowSelectionInterval(lin-1,lin);   
  }
  public void setEditavel(boolean bEdit) {
    modelo.Editavel(bEdit);
  }
  public void limpa() {
    modelo.limpa();
    ContaLinhas = 0;
  }
  public void limpaTudo() {
  	limpa();
  	
  	while(columnModel.getColumnCount() > 0) {
  		TableColumn col = columnModel.getColumn(0);
  		columnModel.removeColumn(col);
  	}
  }  
  public void setColunaEditavel(int iCol, boolean bVal) {
    modelo.ColunaEditavel(iCol,bVal);
  }
  public void setAutoRol(boolean bVal) {
    bAutoRol = bVal;
  }
  public void delLinha(int iLinha){       
      modelo.removeRow(iLinha);
      ContaLinhas--;
  }
  
  public void setRowBackGround(int iLinha, Color cor) {
  	if (iLinha < ContaLinhas) {
  		 for (int i=0;i<ContaColunas;i++) {
  		 	((DefaultTableCellRenderer)getCellRenderer(iLinha,i)).setBackground(cor);
  		 }
  	}
  }
  
  public void valueChanged(ListSelectionEvent levt) {
  	if (seLis != null && getLinhaSel() >= 0)
  	  fireValorSelAlterado();
  	super.valueChanged(levt);
  }
  
  public void fireValorEditAlterado() {
   	edlis.valorAlterado(new TabelaEditEvent(this));
  }
  public void fireValorSelAlterado() {
	seLis.valorAlterado(new TabelaSelEvent(this));
  }
  public void valorAlterado(TabelaEditEvent tevt) { }
  public void valorAlterado(TabelaSelEvent tevt) { }
  
  
  
  class Modelo extends AbstractTableModel implements Serializable {
    protected Vector    dataVector;
    protected Vector    columnIdentifiers;
    boolean bEditavel = false;
    boolean[] bColsEdit = null;
    Tabela tabEdLis = null;
    public Modelo() {
        this((Vector)null, 0);
    }
    public Modelo(int numRows, int numColumns) {
        Vector names = new Vector(numColumns);
        names.setSize(numColumns);
        setColumnIdentifiers(names);
        dataVector = new Vector();
        setNumRows(numRows);
    }
    public Modelo(Vector columnNames, int numRows) {
        setColumnIdentifiers(columnNames);
        dataVector = new Vector();
        setNumRows(numRows);
    }
    public Modelo(Vector data, Vector columnNames) {
        setDataVector(data, columnNames);
    }
    public Modelo(Object[][] data, Object[] columnNames) {
        setDataVector(data, columnNames);
    }
    public void setTabelaEditListenerSrc(Tabela tab){
    	tabEdLis = tab;
    }
    public Vector getDataVector() {
        return dataVector;
    }
    public void setDataVector(Vector newData, Vector columnNames) {
      if (newData == null)
        throw new IllegalArgumentException("setDataVector() - Null parameter");
        dataVector = new Vector(0);
        setColumnIdentifiers(columnNames);
        dataVector = newData;
        newRowsAdded(new TableModelEvent(this, 0, getRowCount()-1,
                             TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT));
    }
    public void setDataVector(Object[][] newData, Object[] columnNames) {
      setDataVector(convertToVector(newData), convertToVector(columnNames));
    }
    public void newDataAvailable(TableModelEvent event) {
      fireTableChanged(event);
    }
    public void newRowsAdded(TableModelEvent event) {
      int start = event.getFirstRow();
      int end = event.getLastRow();
      if (start < 0)
        start = 0;
      if (end < 0)
        end = getRowCount()-1;
      for (int i = start; i < end; i++)
        ((Vector)dataVector.elementAt(i)).setSize(getColumnCount());
        fireTableChanged(event);
      }
    public void rowsRemoved(TableModelEvent event) {
      fireTableChanged(event);
    }
    public void setColumnIdentifiers(Vector newIdentifiers) {
      if (newIdentifiers != null) {
        columnIdentifiers = newIdentifiers;
      }
      else {
        columnIdentifiers = new Vector();
      }
      fireTableStructureChanged();
    }
    public void setColumnIdentifiers(Object[] newIdentifiers) {
        setColumnIdentifiers(convertToVector(newIdentifiers));
    }
    public void setNumRows(int newSize) {
      if ((newSize < 0) || (newSize == getRowCount()))
        return;
      int oldNumRows = getRowCount();
      if (newSize <= getRowCount()) {
        dataVector.setSize(newSize);
        fireTableRowsDeleted(getRowCount(), oldNumRows-1);
      }
      else {
        int columnCount = getColumnCount();
        while(getRowCount() < newSize) {
          Vector newRow = new Vector(columnCount);
          newRow.setSize(columnCount);
          dataVector.addElement(newRow);
        }
        fireTableRowsInserted(oldNumRows, getRowCount()-1);
      }
    }
    public void addColumn(Object columnName) {
        addColumn(columnName, (Vector)null);
    }
    public void addColumn(Object columnName, Vector columnData) {
      if (columnName == null)
        throw new IllegalArgumentException("addColumn() - null parameter");
        columnIdentifiers.addElement(columnName);
        int index = 0;
        Enumeration enumeration = dataVector.elements();
        while (enumeration.hasMoreElements()) {
          Object value;
          if ((columnData != null) && (index < columnData.size()))
            value = columnData.elementAt(index);
          else
            value = null;
          ((Vector)enumeration.nextElement()).addElement(value);
          index++;
      }
      fireTableStructureChanged();
    }
    public void addColumn(Object columnName, Object[] columnData) {
        addColumn(columnName, convertToVector(columnData));
    }
   public void addRow(Vector rowData) {
      if (rowData == null) {
        rowData = new Vector(getColumnCount());
      }
      else {
        rowData.setSize(getColumnCount());
      }
      dataVector.addElement(rowData);
      newRowsAdded(new TableModelEvent(this, getRowCount()-1, getRowCount()-1,
                       TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT));
    }
    public void addRow(Object[] rowData) {
      addRow(convertToVector(rowData));
    }
    public void insertRow(int row, Vector rowData) {
      if (rowData == null) {
        rowData = new Vector(getColumnCount());
      }
      else {
        rowData.setSize(getColumnCount());
      }
      dataVector.insertElementAt(rowData, row);
      newRowsAdded(new TableModelEvent(this, row, row,
                       TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT));
    }
    public void insertRow(int row, Object[] rowData) {
      insertRow(row, convertToVector(rowData));
    }
    public void moveRow(int startIndex, int endIndex, int toIndex) {
      if ((startIndex < 0) || (startIndex >= getRowCount()))
        throw new ArrayIndexOutOfBoundsException(startIndex);
      if ((endIndex < 0) || (endIndex >= getRowCount()))
        throw new ArrayIndexOutOfBoundsException(endIndex);
      if (startIndex > endIndex)
        throw new ArrayIndexOutOfBoundsException();
      if ((startIndex <= toIndex) && (toIndex <= endIndex))
        return;                    
      boolean shift = toIndex < startIndex;
      for (int i = startIndex; i <= endIndex; i++) {
        Object aRow = dataVector.elementAt(i);
        dataVector.removeElementAt(i);
        dataVector.insertElementAt(aRow, toIndex);
        if (shift)
          toIndex++;
      }
      fireTableDataChanged();
    }
    public void removeRow(int row) {
      dataVector.removeElementAt(row);
      fireTableRowsDeleted(row, row);
    }
    public Vector getRow(int iInd) {
		return (Vector)dataVector.elementAt(iInd);
    }
    public void limpa() {
      dataVector.removeAllElements();
      fireTableRowsDeleted( 0, dataVector.size());
    }
    public int getRowCount() {
      return dataVector.size();
    }
    public int getColumnCount() {
        return columnIdentifiers.size();
    }
    public String getColumnName(int column) {
      if (columnIdentifiers == null || columnIdentifiers.size() <= column) {
        return super.getColumnName(column);
      }
      Object id = columnIdentifiers.elementAt(column);
      if (id == null) {
        return super.getColumnName(column);
      }
      return id.toString();
    }
    public void ColunaEditavel(int iCol,boolean bVal) {
      if (bColsEdit == null) {
        bColsEdit = new boolean[getColumnCount()];
      }
      bColsEdit[iCol] = bVal;
    }
    public void Editavel(boolean bEdit) {
      bEditavel = bEdit;
    }
    public boolean isCellEditable(int row, int column) {
      boolean bRetorno = bEditavel;
      if (bColsEdit != null) {
        bRetorno = bColsEdit[column];
      }
      return bRetorno;
    }
    public Object getValueAt(int row, int column) {
        Vector rowVector = (Vector)dataVector.elementAt(row);
        return rowVector.elementAt(column);
    }
    public void setValueAt(Object aValue, int row, int column) {
      Vector rowVector = (Vector)dataVector.elementAt(row);
      rowVector.setElementAt(aValue, column);
      fireTableChanged(new TableModelEvent(this, row, row, column));
    }
    public Class getColumnClass(int c) {
      Object bRet = getValueAt(0, c); 
      return bRet == null ? null : bRet.getClass();
    }
    
    
    protected Vector convertToVector(Object[] anArray) {
      if (anArray == null)
        return null;
      Vector v = new Vector(anArray.length);
      for (int i=0; i < anArray.length; i++) {
        v.addElement(anArray[i]);
      }
      return v;
    }
    protected Vector convertToVector(Object[][] anArray) {
      if (anArray == null)
        return null;
      Vector v = new Vector(anArray.length);
      for (int i=0; i < anArray.length; i++) {
          v.addElement(convertToVector(anArray[i]));
      }
      return v;
    }
    public void fireTableChanged(TableModelEvent tevt){
    	if (tabEdLis!=null)  
    	    tabEdLis.fireValorEditAlterado();
    	super.fireTableChanged(tevt);
    }
  }
}
class DateEditor extends DefaultCellEditor {
  public DateEditor() {
	super(new JTextFieldPad(JTextFieldPad.TP_DATE,10,0));
	delegate = new EditorDelegate() {
	  public void setValue(Object value) {
		((JTextFieldPad)editorComponent).setVlrDate((Date)value);
	  }
	};
  }
  public Object getCellEditorValue() {
	  return ((JTextFieldPad)editorComponent).getVlrDate();
  }
}
