/**
 * @version 30/01/2004 <BR>
 * @author Setpoint Informática Ltda./Anderson Sanchez <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.grh <BR>
 * Classe:
 * @(#)FreedomGRH.java <BR>
 * 
 * Este programa é licenciado de acordo com a LPG-PC (Licença Pública Geral para Programas de Computador), <BR>
 * versão 2.1.0 ou qualquer versão posterior. <BR>
 * A LPG-PC deve acompanhar todas PUBLICAÇÕES, DISTRIBUIÇÕES e REPRODUÇÕES deste Programa. <BR>
 * Caso uma cópia da LPG-PC não esteja disponível junto com este Programa, você pode contatar <BR>
 * o LICENCIADOR ou então pegar uma cópia em: <BR>
 * Licença: http://www.lpg.adv.br/licencas/lpgpc.rtf <BR>
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa é preciso estar <BR>
 * de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 * Tela principal para o módulo de gestão de recursos humanos.
 * 
 */

package org.freedom.modulos.grh;

import org.freedom.funcoes.Funcoes;
import org.freedom.modulos.cfg.FEstadoCivil;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.AplicativoPD;
import org.freedom.telas.FPrincipalPD;
import org.freedom.telas.LoginPD;

public class FreedomGRH extends AplicativoPD {

	public FreedomGRH() {

		super( "iconAtendimento32.gif", "splashGRH.jpg", 1, "Freedom", 9, "Gestão de Recursos Humandos", null, new FPrincipalPD( null, "bgFreedom2.jpg" ), LoginPD.class );

		addOpcao( -1, TP_OPCAO_MENU, "Arquivo", "", 'A', 100000000, 0, false, null );
		addOpcao( 100000000, TP_OPCAO_MENU, "Tabelas", "", 'T', 100100000, 1, false, null );
			addOpcao( 100100000, TP_OPCAO_ITEM, "Candidatos", "Candidatos", 'C', 100100100, 2, true, FCandidato.class );
			addSeparador( 100100000 );
			addOpcao( 100100000, TP_OPCAO_ITEM, "Turnos", "Turnos", 'T', 100100200, 2, true, FTurnos.class );
			addOpcao( 100100000, TP_OPCAO_ITEM, "Funçao", "Função", 'F', 100100300, 2, true, FFuncao.class );
			addOpcao( 100100000, TP_OPCAO_ITEM, "Departamento", "Departamento", 'D', 100100400, 2, true, FDepto.class );
			addOpcao( 100100000, TP_OPCAO_ITEM, "Empregados", "Empregados", 'E', 100100500, 2, true, FEmpregado.class );
			addOpcao( 100100000, TP_OPCAO_ITEM, "Empregadores", "Empregadores", 'p', 100100600, 2, true, FEmpregadores.class );
			addSeparador( 100100000 );
			addOpcao( 100100000, TP_OPCAO_ITEM, "Area", "Area", 'A', 100100700, 2, true, FArea.class );
			addOpcao( 100100000, TP_OPCAO_ITEM, "Nível/Curso", "Nível/Curso", 'N', 100100800, 2, true, FNivelCurso.class );
			addOpcao( 100100000, TP_OPCAO_ITEM, "Curso", "Curso", 'u', 100100900, 2, true, FCurso.class );
			addSeparador( 100100000 );
			addOpcao( 100100000, TP_OPCAO_ITEM, "Estados civis", "Estados civis", 's', 100101000, 2, true, FEstadoCivil.class );
			addSeparador( 100100000 );
			addOpcao( 100100000, TP_OPCAO_ITEM, "Vagas", "Vagas", 'V', 100101100, 2, true, FVaga.class );
			
		addBotao( "barraConveniados.gif", "Empregados", "Empregados", 100100500, FEmpregado.class );
		addBotao( "barraGrupo.gif", "Candidatos", "Candidatos", 100100100, FCandidato.class );

		ajustaMenu();

		sNomeModulo = "Recursos Humanos";
		sMailSuporte = "suporte@stpinf.com";
		sNomeSis = "Freedom";
		sEmpSis = "Setpoint Informática Ltda.";
		vEquipeSis.add( "Robson Sanchez - Supervisão / Analise" );
		vEquipeSis.add( "Anderson Sanchez - Supervisão / Programação" );
		vEquipeSis.add( "Alex Rodrigues - Programação" );
		vEquipeSis.add( "Alexandre Marcondes - Programação" );
		vEquipeSis.add( "Fernando Oliveira - Programação" );
		vEquipeSis.add( "Moyzes Braz - Arte gráfica" );
		vEquipeSis.add( "Reginaldo Garcia - Testes / Suporte" );

	}

	public static void main( String sParams[] ) {

		try {
			Aplicativo.setLookAndFeel( "freedom.ini" );
			FreedomGRH freedom = new FreedomGRH();
			freedom.show();
		} catch ( Throwable e ) {
			Funcoes.criaTelaErro( "Erro de execução\n\n" + e.getMessage() );
			e.printStackTrace();
		}
	}
}
