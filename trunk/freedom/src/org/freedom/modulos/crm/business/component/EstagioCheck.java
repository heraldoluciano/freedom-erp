package org.freedom.modulos.crm.business.component;

import java.util.Vector;

import javax.swing.ImageIcon;

import org.freedom.infra.pojos.Constant;


public class EstagioCheck extends Constant {

	private static final long serialVersionUID = 1L;

	private ImageIcon img = null;
	
	public static final EstagioCheck EPE = new EstagioCheck("Estágio PE", "EPE");

	public static final EstagioCheck E1I = new EstagioCheck("Estágio 1 inconsistente", "E1I");

	public static final EstagioCheck E1O = new EstagioCheck("Estágio 1 Ok", "E1O");
	
	private static Vector<EstagioCheck> listEstagio = null;

	public ImageIcon getImg() {
	
		return img;
	}

	public static Vector<EstagioCheck> getListEstagio() {
		if (listEstagio==null) {
			listEstagio = new Vector<EstagioCheck>();
			listEstagio.addElement( EPE );
			listEstagio.addElement( E1I );
			listEstagio.addElement( E1O );
		}
		return listEstagio;
	}
	
	public static ImageIcon getImg(String estagio) {
		ImageIcon result = null;
		Vector<EstagioCheck> list = getListEstagio();
		for (int i=0; i<list.size(); i++) {
			if (list.elementAt( i ).getValue().equals( estagio )) {
				result = list.elementAt( i ).getImg();
				break;
			}
		}
		return result;
	}

	//Retorna o valor conforme a tabela.
	// Exemplo (Estágio 1 na constant e estágio 1 na tabela): E1I = 1I
	public String getValueTab() {
		String result = "";
		if ( ((String) getValue()!=null) && ((String) getValue()).length()>0) {
			// Retorna a String a partir do segundo caracter
			result = ((String) getValue()).substring(1);
		}
		return result;
	}
	
	public void setImg( ImageIcon img ) {
	
		this.img = img;
	}

	public EstagioCheck( String name, Object value ) {

		super( name, value );

	}
	
	public EstagioCheck( String name, Object value, ImageIcon img ) {

		super( name, value );
		setImg( img );

	}
	

}
