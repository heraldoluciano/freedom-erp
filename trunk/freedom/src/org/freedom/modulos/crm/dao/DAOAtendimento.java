/**
 * @version 02/08/2011 <BR>
 * @author Setpoint Tecnologia em Informática Ltda./Robson Sanchez <BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.infra.dao <BR>
 * Classe: @(#)AbstractDAO.java <BR>
 * 
 * Este arquivo é parte do sistema Freedom-ERP, o Freedom-ERP é um software livre; você pode redistribui-lo e/ou <BR>
 * modifica-lo dentro dos termos da Licença Pública Geral GNU como publicada pela Fundação do Software Livre (FSF); <BR>
 * na versão 2 da Licença, ou (na sua opnião) qualquer versão. <BR>
 * Este programa é distribuido na esperança que possa ser  util, mas SEM NENHUMA GARANTIA; <BR>
 * sem uma garantia implicita de ADEQUAÇÂO a qualquer MERCADO ou APLICAÇÃO EM PARTICULAR. <BR>
 * Veja a Licença Pública Geral GNU para maiores detalhes. <BR>
 * Você deve ter recebido uma cópia da Licença Pública Geral GNU junto com este programa, se não, <BR>
 * escreva para a Fundação do Software Livre(FSF) Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA <BR> <BR>
 *
 * Classe base para implementações de métodos de acesso a dados para tabela ATATENDIMENTO
 */

package org.freedom.modulos.crm.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.freedom.infra.dao.AbstractDAO;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.modulos.crm.business.object.Atendimento;
import org.freedom.modulos.crm.business.object.Atendimento.PREFS;

enum PROC_IU {
	NONE, IU, CODEMP, CODFILIAL, CODATENDO, CODEMPTO, CODFILIALTO, CODTPATENDO, 
	CODEMPAE, CODFILIALAE, CODATEND, CODEMPSA, CODFILIALSA, CODSETAT, 
	DOCATENDO, DATAATENDO, DATAATENDOFIN, HORAATENDO, HORAATENDOFIN, 
	OBSATENDO, CODEMPCL, CODFILIALCL, CODCLI,
	CODEMPCT, CODFILIALCT, CODCONTR, CODITCONTR, 
	CODEMPIR, CODFILIALIR, CODREC, NPARCITREC, 
	CODEMPCH, CODFILIALCH,CODCHAMADO, 
	OBSINTERNO, CONCLUICHAMADO, CODEMPEA, CODFILIALEA, CODESPEC, 
	CODEMPUS, CODFILIALUS, IDUSU, STATUSATENDO
}

public class DAOAtendimento extends AbstractDAO {
	
	private Object prefs[] = null;

	public DAOAtendimento( DbConnection cn )  {

		super( cn );
		//setPrefs();
	
	}
	
	private Integer getSequencia(Integer codemp, Integer codfilial, String tab) throws SQLException {
		Integer result = null;
		StringBuilder sql = new StringBuilder("select iseq from spgeranum( ?, ?, ? )");
		PreparedStatement ps = getConn().prepareStatement( sql.toString() );
		ps.setInt( 1, codemp );
		ps.setInt( 2, codfilial );
		ps.setString( 3, tab );
		ResultSet rs = ps.executeQuery();
		return result;
	}
	
	public Atendimento loadModelAtend(Integer codemp, Integer codfilial, Integer codempmi, Integer codfilialmi, Integer codmodel) throws SQLException {
		Atendimento result = null;
		Integer codatendo = null;
		
		StringBuilder sql = new StringBuilder("select ");
			sql.append( "mod.codempto, mod.codfilialto, mod.codtpatendo, " );
			sql.append( "mod.codempsa, mod.codfilialsa, mod.codsetat, ");
			sql.append( "mod.obsatendo, mod.obsinterno, mod.statusatendo, " );
			sql.append( "mod.codempcl, mod.codfilialcl, mod.codcli, mod.codempct, ");
			sql.append( "mod.codfilialct, mod.codcontr, mod.coditcontr, " );
			sql.append( "mod.codempca, mod.codfilialca, mod.codclasatendo," );
			sql.append("amod.codempch, mod.codfilialch, mod.codchamado, "); 
			sql.append("mod.codempea, mod.codfilialea, mod.codespec " );
			sql.append( "from atmodatendo mod " );
			sql.append( "where " );
			sql.append( "mod.codemp=? and mod.codfilial=? and atd.codmodel=? " );

		if (codmodel!=null) {
			codatendo = getSequencia(codemp, codfilial, "AT");		
			PreparedStatement ps = getConn().prepareStatement( sql.toString() );
			ps.setInt( 1, codempmi );
			ps.setInt( 2, codfilialmi );
			ps.setInt( 3, codmodel );
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				result = new Atendimento();
				result.setCodempto( rs.getInt( "codempto" ) );
				result.setCodfilialto( rs.getInt( "codfilialto" ) );
				result.setCodtpatendo( rs.getInt( "codtpatendo" ) );
				
			}
		
		}
		return result;
	}
	
	private void insertIntervalo(String horaini, String horafim) {
		
		StringBuilder sql = new StringBuilder();
		PreparedStatement ps = null;
		
		try {
		
			sql.append( "insert into atatendimento ( " );
			
			sql.append( "CODEMP, CODFILIAL, CODATENDO, CODEMPTO, CODFILIALTO, CODTPATENDO, CODEMPAE, CODFILIALAE, CODATEND," );
			sql.append( "CODEMPSA, CODFILIALSA, CODSETAT, DATAATENDO, DATAATENDOFIN, HORAATENDO, HORAATENDOFIN, OBSATENDO, OBSINTERNO, STATUSATENDO," );
			sql.append( "CODEMPCL, CODFILIALCL, CODCLI, CODEMPUS, CODFILIALUS, IDUSU, CODEMPCT, CODFILIALCT, CODCONTR, CODITCONTR," );
			sql.append( "CODEMPCA, CODFILIALCA, CODCLASATENDO, CODEMPCH, CODFILIALCH, CODCHAMADO, CODEMPEA, CODFILIALEA, CODESPEC, DOCATENDO ) " );
			
			sql.append( " select " );
			
			sql.append( "?, ?, (select iseq from spgeranum(?, ?, 'AT' )), " );
			sql.append( "atd.codempto, atd.codfilialto, atd.codtpatendo, ?, ?, ?, " );
			sql.append( "atd.codempsa, atd.codfilialsa, atd.codsetat, ?, ?, ?, ?, atd.obsatendo, atd.obsinterno, atd.statusatendo, " );
			sql.append( "atd.codempcl, atd.codfilialcl, atd.codcli, ?, ?, ?, atd.codempct, atd.codfilialct, atd.codcontr, atd.coditcontr, " );
			sql.append( "atd.codempca, atd.codfilialca, atd.codclasatendo, atd.codempch, atd.codfilialch, atd.codchamado, atd.codempea, atd.codfilialea, atd.codespec, ? " );
			sql.append( "from sgprefere3 p3, atmodatendo atd " );
			sql.append( "where " );
			sql.append( "p3.codemp=? and p3.codfilial=? and atd.codemp=p3.codempmi and atd.codfilial=p3.codfilialmi and atd.codmodel=p3.codmodelmi " );
			
			System.out.println("QUERY INSERT:" + sql.toString() );
			
			ps = getConn().prepareStatement( sql.toString() );

			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "ATATENDIMENTO" ) );
			
			ps.setInt( 3, Aplicativo.iCodEmp );
			ps.setInt( 4, ListaCampos.getMasterFilial( "ATATENDIMENTO" ) );

			ps.setInt( 5, Aplicativo.iCodEmp );
			ps.setInt( 6, ListaCampos.getMasterFilial( "ATATENDENTE" ) );
			
			//ps.setInt( 7, txtCodAtend.getVlrInteger() );
		   	//ps.setDate( 8, Funcoes.dateToSQLDate( txtDataAtendimento.getVlrDate()) ); // Data de inicio do atendimento
		    //ps.setDate( 9, Funcoes.dateToSQLDate( txtDataAtendimento.getVlrDate()) ); // Data final do atendimento
			ps.setTime( 10, Funcoes.strTimeTosqlTime( horaini )); // Hora inicial do atendimento
			ps.setTime( 11, Funcoes.strTimeTosqlTime( horafim )); // Hora final do atendimento
			ps.setInt( 12, Aplicativo.iCodEmp );
			ps.setInt( 13, ListaCampos.getMasterFilial( "SGUSUARIO" ) );
			ps.setString( 14, Aplicativo.strUsuario );
			ps.setInt( 15, 0 );
			ps.setInt( 16, Aplicativo.iCodEmp );
			ps.setInt( 17, ListaCampos.getMasterFilial( "SGPREFERE3" ) );
			
			ps.execute(); 
			
			getConn().commit();
						
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	public void setPrefs(Integer codemp, Integer codfilial) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuilder sql = null;
		
		prefs = new Object[ Atendimento.PREFS.values().length];
		
		try {
			sql = new StringBuilder("select codempmi, codfilialmi, codmodelmi  " );
			sql.append(  "from sgprefere3 p " );
			sql.append(  "where  p.codemp=? and p.codfilial=?" );
			
			ps = getConn().prepareStatement( sql.toString() );
			ps.setInt( 1, codemp );
			ps.setInt( 2, codfilial );
			rs = ps.executeQuery();
			if ( rs.next() ) {
				prefs[ PREFS.CODEMPMI.ordinal() ] = new Integer(rs.getInt( PREFS.CODEMPMI.toString() ));
				prefs[ PREFS.CODFILIALMI.ordinal() ] = new Integer(rs.getInt( PREFS.CODFILIALMI.toString() ));
				prefs[ PREFS.CODMODELMI.ordinal() ] = new Integer(rs.getInt( PREFS.CODMODELMI.toString() ));
			}
			rs.close();
			ps.close();
		} finally {
			ps = null;
			rs = null;
			sql = null;
		}
	}
	
	
	public void insert(Atendimento atd) throws Exception {
	
		StringBuilder sql = new StringBuilder();

		sql.append( "EXECUTE PROCEDURE ATATENDIMENTOIUSP(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)" );

		PreparedStatement ps = getConn().prepareStatement( sql.toString() );
		
		ps.setString( PROC_IU.IU.ordinal(), "I" ); // Define o modo insert para a procedure
		ps.setInt( PROC_IU.CODEMP.ordinal() , atd.getCodemp() ); // Código da empresa
		ps.setInt( PROC_IU.CODFILIAL.ordinal(), atd.getCodfilial() ); // Código da filial
		
		if ( atd.getCodatendo() == null ) {
			ps.setInt( PROC_IU.CODATENDO.ordinal(), Types.INTEGER );
		}
		else {
			ps.setInt( PROC_IU.CODATENDO.ordinal() , atd.getCodatendo() ); // Código do atendimento
		}
		
		if ( atd.getCodtpatendo() == null) {
			ps.setNull( PROC_IU.CODEMPTO.ordinal(), Types.INTEGER ); // Código da empresa tipo atendimento
			ps.setNull( PROC_IU.CODFILIALTO.ordinal(), Types.INTEGER ); // Código da filial tipo atendimento
			ps.setNull( PROC_IU.CODTPATENDO.ordinal(), Types.INTEGER ); // Código tipo atendimento
		}
		else {
			ps.setInt( PROC_IU.CODEMPTO.ordinal(), atd.getCodempto() ); // Código da empresa tipo atendimento
			ps.setInt( PROC_IU.CODFILIALTO.ordinal(), atd.getCodfilialto() ); // Código da filial tipo atendimento
			ps.setInt( PROC_IU.CODTPATENDO.ordinal(), atd.getCodtpatendo() ); // // Código tipo atendimento
		}

		if ( atd.getCodatend() == null) {
			ps.setNull( PROC_IU.CODEMPAE.ordinal(), Types.INTEGER ); // Código da empresa atendente
			ps.setNull( PROC_IU.CODFILIALAE.ordinal(), Types.INTEGER ); // Código da filial atendente
			ps.setNull( PROC_IU.CODATEND.ordinal(), Types.INTEGER ); // Código atendente
		}
		else {
			ps.setInt( PROC_IU.CODEMPAE.ordinal(), atd.getCodempae() ); // Código da empresa atendente
			ps.setInt( PROC_IU.CODFILIALAE.ordinal(), atd.getCodfilialae() ); // Código da filial atendente
			ps.setInt( PROC_IU.CODATEND.ordinal(), atd.getCodatend() ); // Código atendente
		}
		
		if ( atd.getCodatend() == null) {
			ps.setNull( PROC_IU.CODEMPSA.ordinal(), Types.INTEGER ); // Código da empresa situação atendimento
			ps.setNull( PROC_IU.CODFILIALSA.ordinal(), Types.INTEGER ); // Código da filial do contrato
			ps.setNull( PROC_IU.CODSETAT.ordinal(), Types.INTEGER ); // Código do contrato
		}
		else {
			ps.setInt( PROC_IU.CODEMPSA.ordinal(), atd.getCodempae() ); // Código da empresa do contrato
			ps.setInt( PROC_IU.CODFILIALSA.ordinal(), atd.getCodfilialae() ); // Código da filial do contrato
			ps.setInt( PROC_IU.CODSETAT.ordinal() , atd.getCodsetat() ); // Setor de atendimento
		}
		
		if ( atd.getIdusu() == null ) {
			ps.setInt( PROC_IU.CODEMPUS.ordinal(), Types.INTEGER ); // Código Empresa Usuário
			ps.setInt( PROC_IU.CODFILIALUS.ordinal(), Types.INTEGER ); // Código Filial Usuário
			ps.setInt( PROC_IU.IDUSU.ordinal(), Types.INTEGER ); // Id Usuário
		}
		else
		{
			ps.setInt( PROC_IU.CODEMPUS.ordinal(), atd.getCodempus() ); // Código Empresa Usuário
			ps.setInt( PROC_IU.CODFILIALUS.ordinal(), atd.getCodfilialus() ); // Código Filial Usuário
			ps.setString( PROC_IU.IDUSU.ordinal(), atd.getIdusu() ); // Id Usuário
		}
		
		ps.setString( PROC_IU.DOCATENDO.ordinal() , atd.getDocatendo() ); // Nro. do atendimento
		ps.setDate( PROC_IU.DATAATENDO.ordinal() ,Funcoes.dateToSQLDate( atd.getDataatendo() ) ); // Data de inicio do atendimento
		ps.setDate( PROC_IU.DATAATENDOFIN.ordinal(), Funcoes.dateToSQLDate( atd.getDataatendofin() ) ); // Data final do atendimento
		ps.setTime( PROC_IU.HORAATENDO.ordinal() , Funcoes.strTimeTosqlTime( atd.getHoraatendo() ) ) ; // Hora inicial do atendimento
		ps.setTime( PROC_IU.HORAATENDOFIN.ordinal()	, Funcoes.strTimeTosqlTime( atd.getHoraatendofin() ) ) ; // Hora final do atendimento
		ps.setString( PROC_IU.OBSATENDO.ordinal(), atd.getObsatendo() ); // Descrição do atendimento
		
		if (atd.getObsinterno()==null) {
			ps.setNull( PROC_IU.OBSINTERNO.ordinal(), Types.CHAR );
		} else {
			ps.setString( PROC_IU.OBSINTERNO.ordinal(), atd.getObsinterno() ); // Observações internas
		}
		ps.setString( PROC_IU.CONCLUICHAMADO.ordinal(), atd.getConcluichamado() );
		
		ps.setString( PROC_IU.STATUSATENDO.ordinal(), atd.getStatusatendo() ); // Status atendimento
		
		if ( atd.getCodcli() == null) {
			ps.setNull( PROC_IU.CODEMPCL.ordinal(), Types.INTEGER ); // Código da empresa do contrato
			ps.setNull( PROC_IU.CODFILIALCL.ordinal(), Types.INTEGER ); // Código da filial do contrato
			ps.setNull( PROC_IU.CODCLI.ordinal(), Types.INTEGER ); // Código do contrato
		}
		else {
			ps.setInt( PROC_IU.CODEMPCL.ordinal(), atd.getCodempcl() ); // Código da empresa do cliente
			ps.setInt( PROC_IU.CODFILIALCL.ordinal(), atd.getCodfilialcl() ); // Código da filial do cliente
			ps.setInt( PROC_IU.CODCLI.ordinal() , atd.getCodcli() ); // Código do cliente
		} 
		
		if ( atd.getCodcontr() == null) {
			ps.setNull( PROC_IU.CODEMPCT.ordinal(), Types.INTEGER ); // Código da empresa do contrato
			ps.setNull( PROC_IU.CODFILIALCT.ordinal(), Types.INTEGER ); // Código da filial do contrato
			ps.setNull( PROC_IU.CODCONTR.ordinal(), Types.INTEGER ); // Código do contrato
			ps.setNull( PROC_IU.CODITCONTR.ordinal(), Types.INTEGER ); // Código do item do contrato
		}
		else {
			ps.setInt( PROC_IU.CODEMPCT.ordinal(), Aplicativo.iCodEmp ); // Código da empresa do contrato
			ps.setInt( PROC_IU.CODFILIALCT.ordinal(), Aplicativo.iCodFilialPad ); // Código da filial do contrato
			ps.setInt( PROC_IU.CODCONTR.ordinal(), atd.getCodcontr()  ); // Código do contrato
			ps.setInt( PROC_IU.CODITCONTR.ordinal(), atd.getCoditcontr()  ); // Código do ítem de contrato
		}

		if ( atd.getCodrec()== null ) {
			ps.setNull( PROC_IU.CODEMPIR.ordinal(), Types.INTEGER );
			ps.setNull( PROC_IU.CODFILIALIR.ordinal(), Types.INTEGER );
			ps.setNull( PROC_IU.CODREC.ordinal(), Types.INTEGER ); // Código do contas a receber
			ps.setNull( PROC_IU.NPARCITREC.ordinal(), Types.INTEGER ); // Código do ítem do contas a receber
		}
		else {
			ps.setInt( PROC_IU.CODEMPIR.ordinal(), atd.getCodempir() ); // Código do contas a receber
			ps.setInt( PROC_IU.CODFILIALIR.ordinal(), atd.getCodfilialir() ); // Código do ítem do contas a receber
			ps.setInt( PROC_IU.CODREC.ordinal(), atd.getCodrec() ); // Código do contas a receber
			ps.setInt( PROC_IU.NPARCITREC.ordinal(), atd.getNparcitrec() ); // Código do ítem do contas a receber
		}

		if ( atd.getCodchamado() == null ) {
			ps.setNull( PROC_IU.CODEMPCH.ordinal(), Types.INTEGER );
			ps.setNull( PROC_IU.CODFILIALCH.ordinal(), Types.INTEGER );
			ps.setNull( PROC_IU.CODCHAMADO.ordinal(), Types.INTEGER );
		}
		else {
			ps.setInt( PROC_IU.CODEMPCH.ordinal(), atd.getCodempch() ); // Código da empresa do chamado
			ps.setInt( PROC_IU.CODFILIALCH.ordinal(), atd.getCodfilialch() ); // Código da filial do chamado
			ps.setInt( PROC_IU.CODCHAMADO.ordinal() ,  atd.getCodchamado() ); // Código do chamado
		
		}
		
		if ( atd.getCodespec() == null  ) {
			ps.setNull( PROC_IU.CODEMPEA.ordinal(), Types.INTEGER );
			ps.setNull( PROC_IU.CODFILIALEA.ordinal(), Types.INTEGER );
			ps.setNull( PROC_IU.CODESPEC.ordinal(), Types.INTEGER );
		}
		else {
			ps.setInt( PROC_IU.CODEMPEA.ordinal(), atd.getCodempae() ); // Código da empresa do especificação
			ps.setInt( PROC_IU.CODFILIALEA.ordinal(),atd.getCodfilialae() ); // Código da filial da especificação
			ps.setInt( PROC_IU.CODESPEC.ordinal(), atd.getCodespec() ); // Código da especificação
		}

		ps.execute();
		ps.close();

		getConn().commit();

	}
	
	
	public void update(Atendimento atd) throws Exception {

		StringBuilder sql = new StringBuilder();
		
		sql.append( "EXECUTE PROCEDURE ATATENDIMENTOIUSP(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)" );

		PreparedStatement ps = getConn().prepareStatement( sql.toString() );
		
		ps.setString( PROC_IU.IU.ordinal(), "U" ); // Define o modo insert para a procedure
		ps.setInt( PROC_IU.CODEMP.ordinal() , atd.getCodemp() ); // Código da empresa
		ps.setInt( PROC_IU.CODFILIAL.ordinal(), atd.getCodfilial() ); // Código da filial
		
		if ( atd.getCodatendo() == null ) {
			ps.setInt( PROC_IU.CODATENDO.ordinal(), Types.INTEGER );
		}
		else {
			ps.setInt( PROC_IU.CODATENDO.ordinal() , atd.getCodatendo() ); // Código do atendimento
		}

		
		if( atd.getCodtpatendo() == null ){
			ps.setNull( PROC_IU.CODEMPTO.ordinal(), Types.INTEGER );
			ps.setNull( PROC_IU.CODFILIALTO.ordinal(), Types.INTEGER );
			ps.setNull( PROC_IU.CODTPATENDO.ordinal(), Types.INTEGER );
		} 
		else {
			ps.setInt( PROC_IU.CODEMPTO.ordinal(), atd.getCodempto() );
			ps.setInt( PROC_IU.CODFILIALTO.ordinal(), atd.getCodfilialto() );
			ps.setInt( PROC_IU.CODTPATENDO.ordinal(), atd.getCodtpatendo() );
		}
		if ( atd.getCodatend() == null) {
			ps.setNull( PROC_IU.CODEMPAE.ordinal(), Types.INTEGER ); // Código da empresa atendente
			ps.setNull( PROC_IU.CODFILIALAE.ordinal(), Types.INTEGER ); // Código da filial atendente
			ps.setNull( PROC_IU.CODATEND.ordinal(), Types.INTEGER ); // Código atendente
		}
		else {
			ps.setInt( PROC_IU.CODEMPAE.ordinal(), atd.getCodempae() ); // Código da empresa atendente
			ps.setInt( PROC_IU.CODFILIALAE.ordinal(), atd.getCodfilialae() ); // Código da filial atendente
			ps.setInt( PROC_IU.CODATEND.ordinal(), atd.getCodatend() ); // Código atendente
		}
		
		
		ps.setString( PROC_IU.DOCATENDO.ordinal() , atd.getDocatendo() );
		ps.setDate( PROC_IU.DATAATENDO.ordinal(), Funcoes.dateToSQLDate( atd.getDataatendo() ) );
		ps.setDate( PROC_IU.DATAATENDOFIN.ordinal(), Funcoes.dateToSQLDate( atd.getDataatendofin() ) );
		ps.setTime( PROC_IU.HORAATENDO.ordinal(), Funcoes.strTimeTosqlTime( atd.getHoraatendo()) );
		ps.setTime( PROC_IU.HORAATENDOFIN.ordinal(), Funcoes.strTimeTosqlTime( atd.getHoraatendofin() ) );
		ps.setString( PROC_IU.OBSATENDO.ordinal(), atd.getObsatendo() );
		
		if ( atd.getCodcli() == null) {
			ps.setNull( PROC_IU.CODEMPCL.ordinal(), Types.INTEGER ); // Código da empresa do contrato
			ps.setNull( PROC_IU.CODFILIALCL.ordinal(), Types.INTEGER ); // Código da filial do contrato
			ps.setNull( PROC_IU.CODCLI.ordinal(), Types.INTEGER ); // Código do contrato
		}
		else {
			ps.setInt( PROC_IU.CODEMPCL.ordinal(), atd.getCodempcl() ); // Código da empresa do cliente
			ps.setInt( PROC_IU.CODFILIALCL.ordinal(), atd.getCodfilialcl() ); // Código da filial do cliente
			ps.setInt( PROC_IU.CODCLI.ordinal() , atd.getCodcli() ); // Código do cliente
		} 
		
		if( atd.getCodsetat() == null ){
			ps.setNull( PROC_IU.CODEMPSA.ordinal(), Types.INTEGER );
			ps.setNull( PROC_IU.CODFILIALSA.ordinal(), Types.INTEGER );
			ps.setNull(  PROC_IU.CODSETAT.ordinal(), Types.INTEGER );
		} 
		else {
			ps.setInt( PROC_IU.CODEMPSA.ordinal(), atd.getCodempsa() );
			ps.setInt( PROC_IU.CODFILIALSA.ordinal(), atd.getCodfilialsa() );
			ps.setInt( PROC_IU.CODSETAT.ordinal(), atd.getCodsetat() );
		}
		
		if ( atd.getIdusu() == null ) {
			ps.setInt( PROC_IU.CODEMPUS.ordinal(), Types.INTEGER ); // Código Empresa Usuário
			ps.setInt( PROC_IU.CODFILIALUS.ordinal(), Types.INTEGER ); // Código Filial Usuário
			ps.setInt( PROC_IU.IDUSU.ordinal(), Types.INTEGER ); // Id Usuário
		}
		else
		{
			ps.setInt( PROC_IU.CODEMPUS.ordinal(), atd.getCodempus() ); // Código Empresa Usuário
			ps.setInt( PROC_IU.CODFILIALUS.ordinal(), atd.getCodfilialus() ); // Código Filial Usuário
			ps.setString( PROC_IU.IDUSU.ordinal(), atd.getIdusu() ); // Id Usuário
		}
		
		if ( atd.getCodrec()== null ) {
			ps.setNull( PROC_IU.CODEMPIR.ordinal(), Types.INTEGER );
			ps.setNull( PROC_IU.CODFILIALIR.ordinal(), Types.INTEGER );
			ps.setNull( PROC_IU.CODREC.ordinal(), Types.INTEGER ); // Código do contas a receber
			ps.setNull( PROC_IU.NPARCITREC.ordinal(), Types.INTEGER ); // Código do ítem do contas a receber
		}
		else {
			ps.setInt( PROC_IU.CODEMPIR.ordinal(), atd.getCodempir() ); // Código do contas a receber
			ps.setInt( PROC_IU.CODFILIALIR.ordinal(), atd.getCodfilialir() ); // Código do ítem do contas a receber
			ps.setInt( PROC_IU.CODREC.ordinal(), atd.getCodrec() ); // Código do contas a receber
			ps.setInt( PROC_IU.NPARCITREC.ordinal(), atd.getNparcitrec() ); // Código do ítem do contas a receber
		}

		if ( atd.getCodchamado() == null ) {
			ps.setNull( PROC_IU.CODEMPCH.ordinal(), Types.INTEGER );
			ps.setNull( PROC_IU.CODFILIALCH.ordinal(), Types.INTEGER );
			ps.setNull( PROC_IU.CODCHAMADO.ordinal(), Types.INTEGER );
		}
		else {
			ps.setInt( PROC_IU.CODEMPCH.ordinal(), atd.getCodempch() ); // Código da empresa do chamado
			ps.setInt( PROC_IU.CODFILIALCH.ordinal(), atd.getCodfilialch() ); // Código da filial do chamado
			ps.setInt( PROC_IU.CODCHAMADO.ordinal(), atd.getCodchamado() ); // Código do chamado
		}

		if ( atd.getCodcontr() == null ) {
			ps.setNull( PROC_IU.CODEMPCT.ordinal(), Types.INTEGER );
			ps.setNull( PROC_IU.CODFILIALCT.ordinal(), Types.INTEGER );
			ps.setNull( PROC_IU.CODCONTR.ordinal(), Types.INTEGER );
			ps.setInt( PROC_IU.CODITCONTR.ordinal(), Types.INTEGER );
		}
		else {
			ps.setNull( PROC_IU.CODEMPCT.ordinal(), atd.getCodempct() );
			ps.setNull( PROC_IU.CODFILIALCT.ordinal(), atd.getCodfilialct() );
			ps.setInt( PROC_IU.CODCONTR.ordinal(), atd.getCodcontr() );
			ps.setInt( PROC_IU.CODITCONTR.ordinal(), atd.getCoditcontr() );
		}

		ps.setString( PROC_IU.STATUSATENDO.ordinal(), atd.getStatusatendo() );
				
		if (atd.getObsinterno()==null) {
			ps.setNull( PROC_IU.OBSINTERNO.ordinal(), Types.CHAR );
		} else {
			ps.setString( PROC_IU.OBSINTERNO.ordinal(), atd.getObsinterno() ); // Observações internas
		}
		
		ps.setString( PROC_IU.CONCLUICHAMADO.ordinal(), atd.getConcluichamado() );

		if ( atd.getCodespec() == null ) {
			ps.setNull( PROC_IU.CODEMPEA.ordinal(), Types.INTEGER );
			ps.setNull( PROC_IU.CODFILIALEA.ordinal(), Types.INTEGER );
			ps.setNull( PROC_IU.CODESPEC.ordinal(), Types.INTEGER );
		}
		else {

			ps.setInt( PROC_IU.CODEMPEA.ordinal(), atd.getCodempea() );
			ps.setInt(  PROC_IU.CODFILIALEA.ordinal(), atd.getCodfilialea() );
			ps.setInt( PROC_IU.CODESPEC.ordinal(), atd.getCodespec() );
		}

		ps.executeUpdate();
		ps.close();
		
		getConn().commit();		
	}
		

}
