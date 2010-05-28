package org.freedom.modulos.gms.business.object;

import java.util.Vector;

import javax.swing.ImageIcon;

import org.freedom.bmps.Icone;
import org.freedom.infra.pojos.Constant;

public class StatusRecMerc implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public static final Constant OS_PENDENTE = new Constant("Pendente", "PE"); 
	public static final Constant OS_ANALISE = new Constant("Em analise", "AN");
	public static final Constant OS_ANDAMENTO = new Constant("Em andamento", "EA");
	public static final Constant OS_PRONTO = new Constant("Pronto", "PT");
	public static final Constant OS_FINALIZADA = new Constant("Finalizada", "FN");
	
	public static String IMG_TAMANHO_M = "16x16";
	
	public static String IMG_TAMANHO_P = "10x10";

	public static ImageIcon getImagem(String status, String tamanho) {
		
		ImageIcon img = null;
		
		ImageIcon IMG_OS_PENDENTE = Icone.novo( "os_pendente_" + tamanho + ".png" );

		ImageIcon IMG_OS_ANDAMENTO = Icone.novo( "os_em_andamento_" + tamanho + ".png" );	
		
		ImageIcon IMG_OS_ANALISE = Icone.novo( "os_em_analise_" + tamanho + ".png" );
		
		ImageIcon IMG_OS_PRONTO = Icone.novo( "os_pronta_" + tamanho + ".png" );
		
		ImageIcon IMG_OS_FINALIZADA = Icone.novo( "os_finalizada_" + tamanho + ".png" );

		
		try {
			
			if(status.equals( OS_PENDENTE.getValue() )) {				
				return IMG_OS_PENDENTE;
			}
			else if(status.equals( OS_ANALISE.getValue() )) {			
				return IMG_OS_ANALISE;
			}
			else if(status.equals( OS_ANDAMENTO.getValue() )) {			
				return IMG_OS_ANDAMENTO;	
			}
			else if(status.equals( OS_PRONTO.getValue() )) {			
				return IMG_OS_PRONTO;
			}
			else if(status.equals( OS_FINALIZADA.getValue() )) {			
				return IMG_OS_FINALIZADA;
			}
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return img;
	}
	
	public static Vector<String> getLabels( ) {

		Vector<String> ret = new Vector<String>();
		
		ret.add( OS_PENDENTE.getName() );
		ret.add( OS_ANALISE.getName() );
		ret.add( OS_ANDAMENTO.getName() );
		ret.add( OS_PRONTO.getName() );
		ret.add( OS_FINALIZADA.getName() );
		
		return ret;
		
	}
	
	public static Vector<Object> getValores( ) {
		
		Vector<Object> ret = new Vector<Object>();
		
		ret.add( OS_PENDENTE.getValue() );
		ret.add( OS_ANALISE.getValue() );
		ret.add( OS_ANDAMENTO.getValue() );
		ret.add( OS_PRONTO.getValue() );
		ret.add( OS_FINALIZADA.getValue() );
		
		return ret;

	}
	
	
	
	

}



