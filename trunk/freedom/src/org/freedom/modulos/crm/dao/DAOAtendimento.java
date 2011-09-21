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
import java.util.Date;
import java.util.Vector;

import org.freedom.infra.dao.AbstractDAO;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.modulos.crm.business.object.Atendimento;
import org.freedom.modulos.crm.business.object.Atendimento.EColAtend;
import org.freedom.modulos.crm.business.object.Atendimento.EColExped;
import org.freedom.modulos.crm.business.object.Atendimento.PARAM_PRIM_LANCA;
import org.freedom.modulos.crm.business.object.Atendimento.PREFS;
import org.freedom.modulos.crm.business.object.Atendimento.PROC_IU;
import org.freedom.modulos.crm.business.object.Atendimento.SITREV;

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
	
	public Atendimento loadModelAtend(Integer codemp, Integer codfilial, Integer codempmo, Integer codfilialmo, Integer codmodel) throws SQLException {
		Atendimento result = null;
		Integer codatendo = null;
		
		StringBuilder sql = new StringBuilder("select ");
			sql.append( "mod.codempto, mod.codfilialto, mod.codtpatendo, " );
			sql.append( "mod.codempsa, mod.codfilialsa, mod.codsetat, ");
			sql.append( "mod.obsatendo, mod.obsinterno, mod.statusatendo, " );
			sql.append( "mod.codempcl, mod.codfilialcl, mod.codcli, mod.codempct, ");
			sql.append( "mod.codfilialct, mod.codcontr, mod.coditcontr, " );
			sql.append( "mod.codempca, mod.codfilialca, mod.codclasatendo," );
			sql.append( "mod.codempch, mod.codfilialch, mod.codchamado, "); 
			sql.append( "mod.codempea, mod.codfilialea, mod.codespec " );
			sql.append( "from atmodatendo mod " );
			sql.append( "where " );
			sql.append( "mod.codemp=? and mod.codfilial=? and mod.codmodel=? " );

		if (codmodel!= null) {
			codatendo = getSequencia(codemp, codfilial, "AT");		
			PreparedStatement ps = getConn().prepareStatement( sql.toString() );
			ps.setInt( 1, codempmo );
			ps.setInt( 2, codfilialmo );
			ps.setInt( 3, codmodel );
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				result = new Atendimento();
				result.setCodempto( rs.getInt( "codempto" ) );
				result.setCodfilialto( rs.getInt( "codfilialto" ) );
				result.setCodtpatendo( rs.getInt( "codtpatendo" ) );
				result.setCodempsa( rs.getInt(  "codempsa" ) );
				result.setCodfilialsa( rs.getInt( "codfilialsa" ) );
				result.setCodsetat( rs.getInt("codsetat" ) );
				result.setObsatendo( rs.getString(  "obsatendo" ) );
				result.setObsinterno( rs.getString( "obsinterno" ) );
				result.setStatusatendo( rs.getString("statusatendo" ) );
				result.setCodempcl( rs.getInt( "codempcl" ) );
				result.setCodfilialcl( rs.getInt("codfilialcl"));
				result.setCodcli( rs.getInt( "codcli" ) );
				if ( rs.getString( "coditcontr" )!=null ) {
					result.setCodempct( rs.getInt("codempct") );
					result.setCodfilialct( rs.getInt( "codfilialct" ) );
					result.setCodcontr( rs.getInt( "codcontr" ) );
					result.setCoditcontr( rs.getInt( "coditcontr" ) );
				}
				result.setCodempca( rs.getInt( "codempca" ) );
				result.setCodfilialca( rs.getInt( "codfilialca" ) );
				result.setCodclasatendo( rs.getInt( "codclasatendo" ) );
				if ( rs.getString( "codchamado" )!=null ) {
					result.setCodempch( rs.getInt( "codempch" ) );
					result.setCodfilialch (rs.getInt( "codfilialch" ) );
					result.setCodchamado( rs.getInt( "codchamado" ) );
				}
				result.setCodempea( rs.getInt( "codempea" ) );
				result.setCodfilialea( rs.getInt( "codfilialea" ) );
				result.setCodespec( rs.getInt( "codespec" ) );
				result.setDocatendo( "0" );
				result.setConcluichamado( "N" );
			}
		
		}
		return result;
	}
	
	public void insertIntervaloAtend(Integer codemp, Integer codfilial, 
			Date dataatendo, Date dataatendofin, 
			String horaini, String horafim,
			Integer codempae, Integer codfilialae, Integer codatend,
			Integer codempus, Integer codfilialus, String idusu) throws SQLException {
		
			Atendimento intervalo = loadModelAtend( codemp, codfilial, (Integer) prefs[PREFS.CODEMPMI.ordinal()], 
					(Integer) prefs[PREFS.CODFILIALMI.ordinal()], (Integer) prefs[PREFS.CODMODELMI.ordinal()] );
			intervalo.setCodemp( codemp );
			intervalo.setCodfilial( codfilial );
			intervalo.setDataatendo( dataatendo );
			intervalo.setDataatendofin( dataatendofin );
			intervalo.setHoraatendo( horaini );
			intervalo.setHoraatendofin( horafim );
			intervalo.setCodempae( codempae );
			intervalo.setCodfilialae( codfilialae );
			intervalo.setCodatend( codatend );
			intervalo.setCodempus( codempus );
			intervalo.setCodfilialus( codfilialus );
			intervalo.setIdusu( idusu );
			
			insert(intervalo);
	}
	
	public void insertIntervaloChegada(Integer codemp, Integer codfilial, 
			Date dataatendo, Date dataatendofin, 
			String horaini, String horafim,
			Integer codempae, Integer codfilialae, Integer codatend,
			Integer codempus, Integer codfilialus, String idusu) throws SQLException {
		
			Atendimento intervalo = loadModelAtend( codemp, codfilial, (Integer) prefs[PREFS.CODEMPME.ordinal()], 
					(Integer) prefs[PREFS.CODFILIALME.ordinal()], (Integer) prefs[PREFS.CODMODELME.ordinal()] );
			intervalo.setCodemp( codemp );
			intervalo.setCodfilial( codfilial );
			intervalo.setDataatendo( dataatendo );
			intervalo.setDataatendofin( dataatendofin );
			intervalo.setHoraatendo( horaini );
			intervalo.setHoraatendofin( horafim );
			intervalo.setCodempae( codempae );
			intervalo.setCodfilialae( codfilialae );
			intervalo.setCodatend( codatend );
			intervalo.setCodempus( codempus );
			intervalo.setCodfilialus( codfilialus );
			intervalo.setIdusu( idusu );
			
			insert(intervalo);
	}
	
	public void setPrefs(Integer codemp, Integer codfilial) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuilder sql = null;
		
		prefs = new Object[ Atendimento.PREFS.values().length];
		
		try {
			sql = new StringBuilder("select codempmi, codfilialmi, codmodelmi,  " );
			sql.append( "codempme, mi.descmodel descmodelmi, "); 
			sql.append( "codfilialme, codmodelme, me.descmodel descmodelme, tempomaxint, "); 
			sql.append( "mi.codempea codempia, mi.codfilialea codfilialia, ea.codespec codespecia, ea.descespec descespecia " );
			sql.append( "from sgprefere3 p " );
			sql.append( "left outer join atmodatendo mi " );
			sql.append( "on mi.codemp=p.codempmi and mi.codfilial=p.codfilialmi and mi.codmodel=p.codmodelmi ");
			sql.append( "left outer join atmodatendo me " );
			sql.append( "on me.codemp=p.codempme and me.codfilial=p.codfilialme and me.codmodel=p.codmodelme ");
			sql.append( "left outer join atespecatend ea " );
			sql.append( "on ea.codemp=mi.codempea and ea.codfilial=mi.codfilialea and ea.codespec=mi.codespec ");
			sql.append( "where  p.codemp=? and p.codfilial=?" );
			
			ps = getConn().prepareStatement( sql.toString() );
			ps.setInt( 1, codemp );
			ps.setInt( 2, codfilial );
			rs = ps.executeQuery();
			if ( rs.next() ) {
				prefs[ PREFS.CODEMPMI.ordinal() ] = new Integer(rs.getInt( PREFS.CODEMPMI.toString() ));
				prefs[ PREFS.CODFILIALMI.ordinal() ] = new Integer(rs.getInt( PREFS.CODFILIALMI.toString() ));
				prefs[ PREFS.CODMODELMI.ordinal() ] = new Integer(rs.getInt( PREFS.CODMODELMI.toString() ));
				prefs[ PREFS.DESCMODELMI.ordinal() ] = rs.getString( PREFS.DESCMODELMI.toString() );
				prefs[ PREFS.CODEMPME.ordinal() ] = new Integer(rs.getInt( PREFS.CODEMPME.toString() ));
				prefs[ PREFS.CODFILIALME.ordinal() ] = new Integer(rs.getInt( PREFS.CODFILIALME.toString() ));
				prefs[ PREFS.CODMODELME.ordinal() ] = new Integer(rs.getInt( PREFS.CODMODELME.toString() ));
				prefs[ PREFS.DESCMODELME.ordinal() ] = rs.getString( PREFS.DESCMODELME.toString() );
				prefs[ PREFS.TEMPOMAXINT.ordinal() ] = new Integer(rs.getInt( PREFS.TEMPOMAXINT.toString() ));
				prefs[ PREFS.CODEMPIA.ordinal() ] = new Integer(rs.getInt( PREFS.CODEMPIA.toString() ));
				prefs[ PREFS.CODFILIALIA.ordinal() ] = new Integer(rs.getInt( PREFS.CODFILIALIA.toString() ));
				prefs[ PREFS.CODESPECIA.ordinal() ] = new Integer(rs.getInt( PREFS.CODESPECIA.toString() ));
				prefs[ PREFS.DESCESPECIA.ordinal() ] = rs.getString( PREFS.DESCESPECIA.toString() );
			}
			rs.close();
			ps.close();
			getConn().commit();
		} finally {
			ps = null;
			rs = null;
			sql = null;
		}
	}
	
	public Object[] getPrefs() {
		return this.prefs;
	}
	
	// Retorna o primeiro ou o último lançamento, dependendo da requisão (Abertura ou Fechamento).
	public String getHoraPrimUltLanca(Integer codemp, Integer codfilial, 
			Date dataatendo, String horaini, String horafim,
			Integer codempae, Integer codfilialae, Integer codatend,
			String aftela) throws SQLException {
		String result = null;
		StringBuilder sql = new StringBuilder();
		sql.append( "select first 1 a.horaatendo, a.horaatendofin from atatendimento a ");
		sql.append( "where a.codemp=? and a.codfilial=? and a.dataatendo=? and ");
		sql.append( "a.codempae=? and a.codfilialae=? and a.codatend=? and " );
		if ("A".equals( aftela )) {
			sql.append( "a.horaatendo>=? " );
			sql.append( "order by a.dataatendo, a.horaatendo" );
		} else {
			sql.append( "a.horaatendofin<=? " );
			sql.append( "order by a.dataatendo desc, a.horaatendofin desc" );
		}
		PreparedStatement ps = getConn().prepareStatement( sql.toString() );
		ps.setInt( PARAM_PRIM_LANCA.CODEMP.ordinal(), codemp );
		ps.setInt( PARAM_PRIM_LANCA.CODFILIAL.ordinal(), codfilial );
		ps.setDate( PARAM_PRIM_LANCA.DATAATENDO.ordinal(), Funcoes.dateToSQLDate( dataatendo ) );
		ps.setInt( PARAM_PRIM_LANCA.CODEMPAE.ordinal(), codempae );
		ps.setInt( PARAM_PRIM_LANCA.CODFILIALAE.ordinal(), codfilialae );
		ps.setInt( PARAM_PRIM_LANCA.CODATEND.ordinal(), codatend );
		if ("A".equals( aftela )) {
			ps.setTime( PARAM_PRIM_LANCA.HORAATENDO.ordinal(), Funcoes.strTimeTosqlTime( horaini ) );
		} else {
			ps.setTime( PARAM_PRIM_LANCA.HORAATENDO.ordinal(), Funcoes.strTimeTosqlTime( horafim ) );
		}
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			if ("A".equals( aftela )) {
				result = rs.getString( PARAM_PRIM_LANCA.HORAATENDO.toString() );
			} else {
				result = rs.getString( PARAM_PRIM_LANCA.HORAATENDOFIN.toString() );
			}
		}
		rs.close();
		ps.close();
		getConn().commit();
		return result;
	}

	public void insert(Atendimento atd) throws SQLException {
	
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

	public String checarSitrev(final Vector<Vector<Object>> vatend) {
		// loop para checar estágio
		// Stágios: PE = estágio pendente/sem nenhuma revisão
		//			1I = estágio 1 situação (I) inconsistência
		//			1O = estágio 1 situação (O) OK
		// Estágio 1, verifica a necessidade de intervalos entre atendimentos
		
		String sitrev = null;
		String sitrevant = null;
		for (Vector<Object> row: vatend) {
			sitrev = (String) row.elementAt( EColAtend.SITREVATENDO.ordinal() );
			if ( "PE".equals( sitrev ) ) {
				break;
			} else if (sitrevant==null) {
				sitrevant=sitrev;
			} else if ( sitrevant.compareTo( sitrev ) > 0 ) {
				sitrevant=sitrev;
			} else if ( sitrev.compareTo( sitrevant ) > 0 ) {
				sitrev=sitrevant;
			}
		}
		return sitrev;
	}
	
    public boolean checar(final Vector<Vector<Object>> vexped, final Vector<Vector<Object>> vatend, int nbatidas) {
    	boolean result = false;
    	// Verifica o menor estágio da revisão
    	String sitrev = checarSitrev(vatend); 
    	if ("PE".equals( sitrev )) {
    		result = checarEstagio1(vexped, vatend, nbatidas);
    		//result = checarEstagio2(vatend);
    	}
    	return result;
    }
    
    public boolean checarEstagio1(final Vector<Vector<Object>> vexped, final Vector<Vector<Object>> vatend, int nbatidas) {
    	boolean result = false;
    	Vector<Object> row = null;
    	int numcols = 0;
    	int qtdant = 0;
    	String dtexped = null;
    	Vector<String> batidas = null;
        Vector<String> turno = null;
        Vector<String> turnosembatida = null;
        Vector<String> hlanctos = null;
		qtdant = EColExped.HFIMTURNO.ordinal()+1;
		numcols = qtdant + nbatidas;
		for (int r=0; r<vexped.size(); r++) {
			row = vexped.elementAt( r );
			dtexped = (String) row.elementAt( EColExped.DTEXPED.ordinal() );
			// Retorna vetor com informações do turno de trabalho
	    	turno = getTurno( (String) row.elementAt( EColExped.HINITURNO.ordinal() ),
	    			(String) row.elementAt( EColExped.HINIINTTURNO.ordinal() ),
	    			(String) row.elementAt( EColExped.HFIMINTTURNO.ordinal() ),
	    			(String) row.elementAt( EColExped.HFIMTURNO.ordinal() ));
	    	// retorna as batidas registradas
			batidas = getBatidas(row, qtdant, numcols);
			// caso o número de batidas seja menor que o esperado, buscar informações.
            if (batidas.size()<turno.size()) {
            	row.setElementAt( SITREV.E1_ER.toString(), EColExped.SITREVEXPED.ordinal() );
            	// Verifica os horários do truno sem batidas
            	turnosembatida = getTurnosembatida( batidas, turno);
            	hlanctos = getHorariosLanctos(dtexped, vatend );
            } else {
            	row.setElementAt( SITREV.E1_OK.toString(), EColExped.SITREVEXPED.ordinal() );
            }
		}
    	return result;
    }

    private Vector<String> getHorariosLanctos(String dtatend, Vector<Vector<Object>> vatend) {
    	Vector<String> result = new Vector<String>();
    	Vector<Object> row = null;
    	//Vector<Object> rowant = null;
    	int pos = primeiroAtend( vatend, dtatend );
    	String dataatendo = null;
    	String horaatendo = null;
    	String horaatendofin = null;
    	String horaatendofinant = null;
    	int intervalo = 0;
    	if (pos>-1) {
    		// Verifica o primeiro lançamento na data e grava como possível horário para ponto
    		row = vatend.elementAt( pos );
    		dataatendo = (String) row.elementAt( EColAtend.DATAATENDO.ordinal() );
    		horaatendo = (String) row.elementAt( EColAtend.HORAATENDO.ordinal() );
    		horaatendofin = (String) row.elementAt( EColAtend.HORAATENDOFIN.ordinal() );
    		result.add( horaatendo );
    		pos++;
    		// loop enquando for a data a pesquisar
    		while ( (pos<vatend.size()) && (dtatend.equals( dataatendo )) ) {
    			// hora final do atendimento anterior
        		horaatendofinant = (String) row.elementAt( EColAtend.HORAATENDOFIN.ordinal() );
        		row = vatend.elementAt( pos );
        		dataatendo = (String) row.elementAt( EColAtend.DATAATENDO.ordinal() );
        		horaatendo = (String) row.elementAt( EColAtend.HORAATENDO.ordinal() );
        		horaatendofin = (String) row.elementAt( EColAtend.HORAATENDOFIN.ordinal() );
        		intervalo = (Integer) row.elementAt( EColAtend.INTERVATENDO.ordinal() );
        		// Se tiver intervalo adiciona a hora final anterior e a hora inicial do lançamento atual
        		if (intervalo>0) { 
        			result.add( horaatendofinant );
        			result.add( horaatendo );
        		}
    			pos++;
    		}
    		// adiciona o horário final para fechar o turno de trabalho
    		result.add( horaatendofin );
    	}
    	return result;
    }
    
    private Vector<String> getTurno( String hiniturno, String hiniintturno, String hfimintturno, String hfimturno ) {
    	Vector<String> result = new Vector<String>();
		if (hiniintturno.equals( hfimintturno )) { // verifica se é meio período
			result.add( hiniturno );
			result.add( hfimturno );
		} else {
			result.add( hiniturno );
			result.add( hiniintturno );
			result.add( hfimintturno );
			result.add( hfimturno );
		} 	
    	return result;
    }
    
    private Vector<String> getTurnosembatida(Vector<String> batidas, Vector<String> turnos) {
    	Vector<String> result = new Vector<String>();
    	String hbat = null;
    	String hturno = null;
    	int posdif = -1;
    	long dif = 0;
    	long difant = 0;
    	// Clonar turnos
    	for ( int i=0; i<turnos.size(); i++ ) {
    		result.add( turnos.elementAt( i ) );
    	}
    	// Comparar batidas com horários do turno de trabalho
    	for ( int i=0; i<batidas.size(); i++) {
    		hbat = batidas.elementAt( i );
    		posdif = -1;
    		for ( int t=0; t<result.size(); t++ ) {
    			hturno = result.elementAt( t );
    			dif = Funcoes.subtraiTime( Funcoes.strTimeTosqlTime( hturno ), Funcoes.strTimeTosqlTime( hbat ));
    			if (dif<0) {
    				dif = dif * -1;
    			}
    			if (posdif==-1) {
    				difant = dif;
    				posdif = t;
    			} else if (dif<difant) {
    				difant = dif;
    				posdif = t;
    			}
    		}
    		if (posdif>-1) {
    			result.remove( posdif );
    		}
    	}
    	return result;
    }
    
    private Vector<String> getBatidas(Vector<Object> row, int posini, int numcols) {
    	Vector<String> result = new Vector<String>();
    	String hbat = null;
    	for (int i=posini; i<numcols; i++ ) {
    		hbat = (String) row.elementAt( i );
    		if (! "".equals( hbat )) {
    			result.add( hbat );
    		}
    	}
    	return result;
    }
    
    public boolean checarEstagio2(final Vector<Vector<Object>>  vatend) {
    	boolean result = false;
//    	Integer codemp = (Integer) prefs[PREFS.CODEMPMI.ordinal()];
//    	Integer codfilial = (Integer) prefs[PREFS.CODFILIALMI.ordinal()];
    	Integer codmodel = (Integer) prefs[PREFS.CODMODELMI.ordinal()];
    	String descmodel = (String) prefs[PREFS.DESCMODELMI.ordinal()];
    	int tempomaxint = (Integer) prefs[PREFS.TEMPOMAXINT.ordinal()];
    	Integer codespecia = (Integer) prefs[PREFS.CODESPECIA.ordinal()];
    	String descespecia = (String) prefs[PREFS.DESCESPECIA.ordinal()];
    	String dtatendopos = null;
    	String dtatendo = null;
    	Vector<Object> row = null;
    	Vector<Object> rowPos = null;
    	int totalmin = 0;
    	int intervalo = 0;
    	int intervaloinserir = 0;
    	int totintervalo = 0;
    	int pos = 0;
    	// Abre um loop até o final do vetor de atendimentos
		for (int i=0; i<vatend.size(); i++) {
			row = vatend.elementAt( i );
			dtatendo = (String) row.elementAt( EColAtend.DATAATENDO.ordinal() );
			intervalo = (Integer) row.elementAt( EColAtend.INTERVATENDO.ordinal() );
			// Se foi detectado que existe um intervalo sem lançamentos
			if (intervalo>0) {
				pos = primeiroAtend(vatend, dtatendo); // Checar as datas do mesmo dia, para verificar se não ultrapassará o limite para intervalos.
				dtatendopos = dtatendo;
				totalmin = 0;
				while ( ( pos<vatend.size() ) && (dtatendo.equals( dtatendopos ) ) ) {
					rowPos = vatend.elementAt( pos );
					dtatendopos = (String) rowPos.elementAt( EColAtend.DATAATENDO.ordinal() );
					if ( codespecia.equals((Integer) rowPos.elementAt( EColAtend.CODESPEC.ordinal() ))	) {
						totalmin += ( (Integer) rowPos.elementAt( EColAtend.TOTALMIN.ordinal() ) );
					}
					pos ++;
				}
				// Verificar se é possível inserir lançamentos
				intervaloinserir = intervalo;
				if (intervaloinserir>tempomaxint) {
					intervaloinserir = tempomaxint;
				}
				intervaloinserir = intervaloinserir - totalmin;
				// Se restar intervalo a inserir
				if (intervaloinserir>0) {
					if (row!=null) {
						row.setElementAt( codmodel , EColAtend.CODMODEL.ordinal() );
						row.setElementAt( descmodel, EColAtend.DESCMODEL.ordinal() );
					}
				}
			}
		}
    	return result;
    }
    
    public int primeiroAtend(Vector<Vector<Object>> vatend, String dtatend ) {
    	int result = -1;
    	Vector<Object> row = null;
    	for (int i=0; i<vatend.size(); i++) {
    		row = vatend.elementAt( i );
    		if (dtatend.equals( row.elementAt( EColAtend.DATAATENDO.ordinal() ) ) ) {
    			result = i;
    			break;
    		}
    	}
    	return result;
    }
}
