package org.freedom.modulos.crm.business.object;

import java.awt.Color;
import java.util.Vector;

import org.freedom.modulos.crm.business.component.Constant;

public class Contrato {
	public static final Constant SIT_PROJ_NAO_SALVO = new Constant("Não salvo", "",  Color.WHITE);	
	public static final Constant SIT_PROJ_PENDENTE = new Constant("Pendente", "PE",  Color.YELLOW);
	public static final Constant SIT_PROJ_EM_PLANEJAMENTO = new Constant("Em planejamento", "PA", Color.GRAY);
	public static final Constant SIT_PROJ_PLANEJADO = new Constant("Planejado", "PF", Color.DARK_GRAY);
	public static final Constant SIT_PROJ_EM_EXECUCAO = new Constant("Em execução", "EE", Color.CYAN);
	public static final Constant SIT_PROJ_EXECUTADO = new Constant("Executado", "EX", Color.GREEN);
	public static final Constant SIT_PROJ_PARALIZADO = new Constant("Paralizado", "PO", Color.WHITE);
	public static final Constant SIT_PROJ_CANCELADO_CLIENTE = new Constant("Canc. cliente", "CC", Color.RED);
	public static final Constant SIT_PROJ_CANCELADO_PRESTADOR = new Constant("Canc. prestador ", "CP", Color.RED);
	public static final Constant SIT_PROJ_FINALIZADO = new Constant("Finalizado", "FN", Color.BLUE);
	
	private static Vector<Constant> listSitproj = null;
	
	public static Vector<Constant> getListSitproj() {
		if (listSitproj==null) {
			listSitproj = new Vector<Constant>();
			listSitproj.add( SIT_PROJ_PENDENTE );
			listSitproj.add( SIT_PROJ_EM_PLANEJAMENTO );
			listSitproj.add( SIT_PROJ_PLANEJADO );
			listSitproj.add( SIT_PROJ_EM_EXECUCAO );
			listSitproj.add( SIT_PROJ_EXECUTADO );
			listSitproj.add( SIT_PROJ_PARALIZADO );
			listSitproj.add( SIT_PROJ_CANCELADO_CLIENTE );
			listSitproj.add( SIT_PROJ_CANCELADO_PRESTADOR );
			listSitproj.add( SIT_PROJ_FINALIZADO );
		}
		return listSitproj;
	}
	
}
