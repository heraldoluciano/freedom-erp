package org.freedom.infra.beans;

/**
 * Projeto: <a href="http://sourceforge.net/projects/freedom-erp/">Freedom-infra</a> <br>
 * Este programa é licenciado de acordo com a LPG-PC <br>
 * (Licença Pública Geral para Programas de Computador) versão 2.1.0 ou qualquer versão posterior. <br>
 * <br>
 *
 * Interface para padronização de componentes que 
 * 
 * <br>
 * @author 		Alex Rodrigues
 * @version 	0.0.1 – 16/05/2008
 * 
 * @since 		16/05/2008
 */
public interface Component {
	
	void setValue( Object value );
	
	Object getValue();
	
	Field getField();

}
