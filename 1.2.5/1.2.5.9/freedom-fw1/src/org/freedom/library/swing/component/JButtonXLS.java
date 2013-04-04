/**
 * @version 04/01/2013 <BR>
 * @author Setpoint Informática Ltda./Robson Sanchez <BR>
 * 
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.library.swing.component <BR>
 * Classe:
 * @(#)JButtonXLS.java <BR>
 * 
 * Este programa é licenciado de acordo com a LPG-PC (Licença Pública Geral para
 * Programas de Computador), <BR>
 * modifica-lo dentro dos termos da Licença Pública Geral GNU como publicada pela Fundação do Software Livre (FSF); <BR>
 * A LPG-PC deve acompanhar todas PUBLICAÇÕES, DISTRIBUIÇÕES e REPRODUÇÕES deste
 * Programa. <BR>
 * Caso uma cópia da LPG-PC não esteja disponível junto com este Programa, você
 * pode contatar <BR>
 * sem uma garantia implicita de ADEQUAÇÂO a qualquer MERCADO ou APLICAÇÃO EM PARTICULAR. <BR>
 * Veja a Licença Pública Geral GNU para maiores detalhes. <BR>
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa é
 * preciso estar <BR>
 * de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 * Botão especialista em exportação de dados para formato XLS
 */

package org.freedom.library.swing.component;

import java.awt.FileDialog;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.Icon;

import net.sf.jxls.exception.ParsePropertyException;
import net.sf.jxls.transformer.XLSTransformer;

import org.apache.commons.beanutils.RowSetDynaClass;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.freedom.bmps.Icone;
import org.freedom.library.component.ResultSetToExcel;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.swing.frame.Aplicativo;


public class JButtonXLS extends JButtonPad {
	
	/**
	 * 

	 */
	
	private static Icon iconXLS = Icone.novo("btXls.png");

	private static final long serialVersionUID = 1L;
	
	public JButtonXLS() {
		super(iconXLS);
		this.setEnabled(false);
		this.setToolTipText("Exportação para formato XLS");
	}

	public boolean execute(ResultSet rs) {
		boolean result = false;
		
		FileDialog fdExcel = null;
		fdExcel = new FileDialog( Aplicativo.telaPrincipal, "Salvar arquivo excell", FileDialog.SAVE );
		fdExcel.setFile( "relatorio.xls" );
		fdExcel.setVisible( true );
		if ( fdExcel.getFile() == null )
			return result;

		String filename = fdExcel.getDirectory() + fdExcel.getFile();
		
        //Map<String, List<Object>> beans = new HashMap<String, List<Object>>();
		try {
			
			//RowSetDynaClass rowSet = new RowSetDynaClass(rs, false);
			//rsc = new ResultSetCollection(rs, false);
			FileOutputStream out = new FileOutputStream(new File(filename));
			BufferedOutputStream output = new BufferedOutputStream(out);
			ResultSetToExcel rse = new ResultSetToExcel(rs, "report");
			rse.generate(output);
	        //beans.put( "report", rowSet.getRows() );
	        //XLSTransformer transformer = new XLSTransformer();
	        //transformer.transformXLS( output, beans);
	        result = true;
		} catch (NullPointerException e) {
			Funcoes.mensagemErro(null,"Erro convertendo resultado da consulta \n" + e.getMessage());
			e.printStackTrace();
		}/* catch (SQLException e) {
			Funcoes.mensagemErro(null,"Erro convertendo resultado da consulta \n" + e.getMessage());
			e.printStackTrace();
		} */catch (ParsePropertyException e) {
			Funcoes.mensagemErro(null,"Erro convertendo resultado da consulta \n" + e.getMessage());
			e.printStackTrace();
		} /*catch (InvalidFormatException e) {
			Funcoes.mensagemErro(null,"Erro convertendo resultado da consulta \n" + e.getMessage());
			e.printStackTrace();
		}*/ catch (IOException e) {
			Funcoes.mensagemErro(null,"Erro convertendo resultado da consulta \n" + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			Funcoes.mensagemErro(null,"Erro convertendo resultado da consulta \n" + e.getMessage());
			e.printStackTrace();
		}

		return result;
	}
}

