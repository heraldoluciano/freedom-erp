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

import org.freedom.infra.dao.AbstractDAO;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.modulos.crm.business.object.Atendimento;
import org.freedom.modulos.crm.view.frame.utility.FCRM;

enum PROC_IU {
	NONE, IU, CODTPATENDO, CODATEND, CODSETAT, CODEMP, CODFILIAL, DOCATENDO, DATAATENDO, 
	DATAATENDOFIN, HORAATENDO, HORAATENDOFIN, OBSATENDO, CODEMPCL, CODFILIALCL, CODCLI,
	CODEMPCT, CODFILIALCT, CODCONTR, CODITCONTR, CODREC, NPARCITREC, CODEMPCH, CODFILIALCH,
	CODCHAMADO, OBSINTERNO, CONCLUICHAMADO, CODEMPEA, CODFILIALEA, CODESPEC
}

public class DAOAtendimento extends AbstractDAO {

	public DbConnection con = null;
	
	private boolean prefs[] = null;

	public DAOAtendimento( DbConnection cn ) {

		super( cn );
		setPrefs();
	
	}
	
	private void setPrefs() {
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuilder sql = null;
		
		prefs = new boolean[ Atendimento.PREFS.values().length];
		
		try {
			sql = new StringBuilder("AT.CODTPATENDO  ,AT.CODATEND , AT.CODSETAT, AT.STATUSATENDO, AT.DOCATENDO,  " );
			sql.append(  "AT.DATAATENDO, AT.DATAATENDOFIN,AT.HORAATENDO, AT.HORAATENDOFIN, AT.OBSATENDO, AT.CODCLI, " );
			sql.append(  " AT.CODCONTR, AT.CODITCONTR, AT.CODCHAMADO, AT.OBSINTERNO, AT.CODESPEC " );
			sql.append(  "FROM ATENDIMENTO AT " );
			sql.append( "WHERE AT.CODEMP=? AND AT.CODFILIAL=? " );
			
			ps = con.prepareStatement( sql.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "ATATENDIMENTO" ) );
			rs = ps.executeQuery();
			if ( rs.next() ) {
				if ( "S".equals( rs.getString( Atendimento.PREFS.CODTPATENDO.toString() ) ) ) 
					prefs[ Atendimento.PREFS.CODTPATENDO.ordinal() ] = true;
				if ( "S".equals(rs.getString( Atendimento.PREFS.CODATEND.toString() ) ) )
					prefs[ Atendimento.PREFS.CODATEND.ordinal() ] = true;
				if ( "S".equals( rs.getString(Atendimento.PREFS.CODSETAT.toString() ) ) )
					prefs[ Atendimento.PREFS.CODSETAT.ordinal() ] = true;
				if ( "S".equals( rs.getString(Atendimento.PREFS.STATUSATENDO.toString() ) ) )
					prefs[ Atendimento.PREFS.STATUSATENDO.ordinal() ] = true;
				if ( "S".equals( rs.getString(Atendimento.PREFS.DOCATENDO.toString() ) ) )
					prefs[ Atendimento.PREFS.DOCATENDO.ordinal() ] = true;
				if ( "S".equals( rs.getString(Atendimento.PREFS.DATAATENDO.toString() ) ) )
					prefs[ Atendimento.PREFS.DATAATENDO.ordinal() ] = true;
				if ( "S".equals( rs.getString(Atendimento.PREFS.DATAATENDOFIN.toString() ) ) )
					prefs[ Atendimento.PREFS.DATAATENDOFIN.ordinal() ] = true;
				if ( "S".equals( rs.getString(Atendimento.PREFS.HORAATENDO.toString() ) ) )
					prefs[ Atendimento.PREFS.HORAATENDO.ordinal() ] = true;
				if ( "S".equals( rs.getString(Atendimento.PREFS.HORAATENDOFIN.toString() ) ) )
					prefs[ Atendimento.PREFS.HORAATENDOFIN.ordinal() ] = true;
				if ( "S".equals( rs.getString(Atendimento.PREFS.OBSATENDO.toString() ) ) )
					prefs[ Atendimento.PREFS.OBSATENDO.ordinal() ] = true;
				if ( "S".equals( rs.getString(Atendimento.PREFS.CODCLI.toString() ) ) )
					prefs[ Atendimento.PREFS.CODCLI.ordinal() ] = true;
				if ( "S".equals( rs.getString(Atendimento.PREFS.CODCONTR.toString() ) ) )
					prefs[ Atendimento.PREFS.CODCONTR.ordinal() ] = true;
				if ( "S".equals( rs.getString(Atendimento.PREFS.CODITCONTR.toString() ) ) )
					prefs[ Atendimento.PREFS.CODITCONTR.ordinal() ] = true;
				if ( "S".equals( rs.getString(Atendimento.PREFS.CODCHAMADO.toString() ) ) )
					prefs[ Atendimento.PREFS.CODCHAMADO.ordinal() ] = true;
				if ( "S".equals( rs.getString(Atendimento.PREFS.CODESPEC.toString() ) ) )
					prefs[ Atendimento.PREFS.CODESPEC.ordinal() ] = true;
				if ( "S".equals( rs.getString(Atendimento.PREFS.OBSINTERNO.toString() ) ) )
					prefs[ Atendimento.PREFS.OBSINTERNO.ordinal() ] = true;
			}
			rs.close();
			ps.close();
		} catch ( SQLException err ) {
			err.printStackTrace();
		} finally {
			ps = null;
			rs = null;
			sql = null;
		}
	}
	
	private void insert(Atendimento atd) throws Exception {
	
		StringBuilder sql = new StringBuilder();

		sql.append( "EXECUTE PROCEDURE ATATENDIMENTOIUSP(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)" );

		PreparedStatement ps = con.prepareStatement( sql.toString() );

	
		
		ps.setInt( PROC_IU.CODTPATENDO.ordinal(), atd.getCodtpatendo() ); // Tipo de atendimento
		ps.setInt( PROC_IU.CODATEND.ordinal(), atd.getCodatend() ); // Codigo do atendente
		ps.setInt( PROC_IU.CODSETAT.ordinal() , atd.getCodsetat() ); // Setor de atendimento
		ps.setInt( PROC_IU.CODEMP.ordinal() , Aplicativo.iCodEmp ); // Código da empresa
		ps.setInt( PROC_IU.CODFILIAL.ordinal(), Aplicativo.iCodFilialPad ); // Código da filial
		ps.setString( PROC_IU.DOCATENDO.ordinal() , atd.getDocatendo() ); // Nro. do atendimento
		ps.setDate( PROC_IU.DATAATENDO.ordinal() ,Funcoes.dateToSQLDate( atd.getDataatendo() ) ); // Data de inicio do atendimento
		ps.setDate( PROC_IU.DATAATENDOFIN, Funcoes.dateToSQLDate( atd.getDataatendofin() ) ); // Data final do atendimento
		ps.setTime( PROC_IU.HORAATENDO.ordinal() , Funcoes.strTimeTosqlTime( atd.getHoraatendo().toString() ) ) ; // Hora inicial do atendimento
		ps.setTime( PROC_IU.HORAATENDOFIN.ordinal()	, Funcoes.strTimeTosqlTime( atd.getHoraatendofin().toString() ) ) ; // Hora final do atendimento
		ps.setString( PROC_IU.OBSATENDO.ordinal(), atd.getObsatendo() ); // Descrição do atendimento
		ps.setInt( PROC_IU.CODEMPCL.ordinal(), Aplicativo.iCodEmp ); // Código da empresa do cliente
		ps.setInt( PROC_IU.CODFILIALCL.ordinal(), Aplicativo.iCodFilialPad ); // Código da filial do cliente
		ps.setInt( PROC_IU.CODCLI.ordinal(), atd.getCodcli() ); // Código do cliente
		
		if ( atd.getCodcontr() == -1 ) {
			ps.setNull( PROC_IU.CODEMPCT.ordinal(), Types.INTEGER ); // Código da empresa do contrato
			ps.setNull( PROC_IU.CODFILIALCT.ordinal(), Types.INTEGER ); // Código da filial do contrato
			ps.setNull( PROC_IU.CODCONTR.ordinal(), Types.INTEGER ); // Código do contrato
		}
		else {
			ps.setInt( PROC_IU.CODEMPCT.ordinal(), Aplicativo.iCodEmp ); // Código da empresa do contrato
			ps.setInt( PROC_IU.CODFILIALCT.ordinal(), Aplicativo.iCodFilialPad ); // Código da filial do contrato
			ps.setInt( PROC_IU.CODCONTR.ordinal(), atd.getCodcontr()  ); // Código do contrato
		}
		if ( atd.getCodcontr() == -1 ) {
			ps.setInt( PROC_IU.CODITCONTR.ordinal(), atd.getCodcontr()  ); // Código do ítem de contrato
		}
		else {
			ps.setInt( PROC_IU.CODITCONTR.ordinal(), atd.getCoditcontr()  ); // Código do ítem de contrato
		}

		if ( PROC_IU.CODREC.ordinal() != null && nparcitrec != null ) {
			ps.setInt( PROC_IU.CODREC.ordinal(), PROC_IU.CODREC.ordinal() ); // Código do contas a receber
			ps.setInt( PROC_IU.NPARCITREC.ordinal(), nparcitrec ); // Código do ítem do contas a receber
		}
		else {
			ps.setNull( PROC_IU.CODREC.ordinal(), Types.INTEGER ); // Código do contas a receber
			ps.setNull( PROC_IU.NPARCITREC.ordinal(), Types.INTEGER ); // Código do ítem do contas a receber
		}


		if ( atd.getCodchamado() > 0 ) {
			ps.setInt( PROC_IU.CODEMPCH.ordinal(), lcChamado.getCodEmp() ); // Código da empresa do chamado
			ps.setInt( PROC_IU.CODFILIALCH.ordinal(), lcChamado.getCodFilial() ); // Código da filial do chamado
			ps.setInt( PROC_IU.CODCHAMADO.ordinal() ,  atd.getCodchamado() ); // Código do chamado
		}
		else {
			ps.setNull( PROC_IU.CODEMPCH.ordinal(), Types.INTEGER );
			ps.setNull( PROC_IU.CODFILIALCH.ordinal(), Types.INTEGER );
			ps.setNull( PROC_IU.CODCHAMADO.ordinal(), Types.INTEGER );
		}

		ps.setString( PROC_IU.OBSINTERNO.ordinal(), atd.getObsinterno() );

		ps.setString( PROC_IU.CONCLUICHAMADO.ordinal(), cbConcluiChamado.getVlrString() );

		if ( atd.getCodespc() > 0 ) {
			ps.setInt( PROC_IU.CODEMPEA.ordinal(), .getCodEmp() ); // Código da empresa do especificação
			ps.setInt( PROC_IU.CODFILIALEA.ordinal(),.getCodFilial() ); // Código da filial da especificação
			ps.setInt( PROC_IU.CODESPEC.ordinal(), atd.getCodespc() ); // Código da especificação
		}
		else if (financeiro){
			ps.setNull( PROC_IU.CODEMPEA.ordinal(), Types.INTEGER );
			ps.setNull( PROC_IU.CODFILIALEA.ordinal(), Types.INTEGER );
			ps.setNull( PROC_IU.CODESPEC.ordinal(), Types.INTEGER );
		}
		else {
			Funcoes.mensagemInforma(null,"Informe a especificação do atendimento!");
		}


		ps.execute();
		ps.close();

		con.commit();

		if(corig instanceof FCRM) {
			
			(( FCRM ) corig).carregaAtendimentos();	
			
		}
		
		
	}
	
	private void updateAtend(Atendimento atd) throws Exception {

		StringBuilder sql = new StringBuilder();

		sql.append( "update atatendimento a set  " );

		sql.append( "a.codatend=?, a.dataatendo=?, a.dataatendofin=?, " );
		sql.append( "a.horaatendo=?, a.horaatendofin=?, a.obsatendo=?, " );
		sql.append( "a.codempto=?, a.codfilialto=?, a.codtpatendo=?, " );
		sql.append( "a.codempsa=?, a.codfilialsa=?, a.codsetat=?, " );
		sql.append( "a.codempch=?, a.codfilialch=?, a.codchamado=?, " );
		sql.append( "a.codempct=?, a.codfilialct=?, a.codcontr=?, a.coditcontr=?, " );
		sql.append( "a.statusatendo=?, a.obsinterno=?, a.concluichamado=?, " );
		sql.append( "a.codempea=?, a.codfilialea=?, a.codespec=?, ");
		sql.append( "a.codempcl=?, a.codfilialcl=?, a.codcli=? ");

		sql.append( "where a.codemp=? and a.codfilial=? and a.codatendo=? " );

		PreparedStatement ps = con.prepareStatement( sql.toString() );

		ps.setInt( PROC_IU.CODATEND.ordinal(), atd.getCodatend());
		ps.setDate( PROC_IU.DATAATENDO.ordinal(), Funcoes.dateToSQLDate( txtDataAtendimento.getVlrDate() ) );
		ps.setDate( PROC_IU.DATAATENDOFIN.ordinal(), Funcoes.dateToSQLDate( txtDataAtendimentoFin.getVlrDate() ) );

		ps.setTime( PROC_IU.HORAATENDO.ordinal(), Funcoes.strTimeTosqlTime( txtHoraini.getVlrString() ) );
		ps.setTime( PROC_IU.HORAATENDOFIN.ordinal(), Funcoes.strTimeTosqlTime( txtHorafim.getVlrString() ) );
		ps.setString( PROC_IU.OBSATENDO.ordinal(), txaDescAtend.getVlrString() );

		ps.setInt( PROC_IU.CODEMP.ordinal(), Aplicativo.iCodEmp );
		ps.setInt( PROC_IU.CODFILIAL.ordinal(), ListaCampos.getMasterFilial( "ATTIPOATENDO" ) );
		ps.setInt(  PROC_IU.CODTPATENDO.ordinal(), cbTipo.getVlrInteger() );

		ps.setInt( 10, Aplicativo.iCodEmp );
		ps.setInt( 11, ListaCampos.getMasterFilial( "ATSETOR" ) );
		ps.setInt( 12, cbSetor.getVlrInteger() );

		if ( txtCodChamado.getVlrInteger() > 0 ) {
			ps.setInt( 13, lcChamado.getCodEmp() ); // Código da empresa do chamado
			ps.setInt( 14, lcChamado.getCodFilial() ); // Código da filial do chamado
			ps.setInt( 15, txtCodChamado.getVlrInteger() ); // Código do chamado
		}
		else {
			ps.setNull( 13, Types.INTEGER );
			ps.setNull( 14, Types.INTEGER );
			ps.setNull( 15, Types.INTEGER );
		}

		ps.setInt( 16, Aplicativo.iCodEmp );
		ps.setInt( 17, ListaCampos.getMasterFilial( "ATATENDIMENTO" ) );

		if ( cbContrato.getVlrInteger() == -1 ) {
			ps.setNull( 18, Types.INTEGER );
		}
		else {
			ps.setInt( 18, cbContrato.getVlrInteger() );
		}
		if ( cbitContrato.getVlrInteger() == -1 ) {
			ps.setInt( 19, cbContrato.getVlrInteger() );
		}
		else {
			ps.setInt( 19, cbitContrato.getVlrInteger() );
		}

		ps.setString( 20, cbStatus.getVlrString() );

		ps.setString( 21, txaObsInterno.getVlrString() );

		ps.setString( 22, cbConcluiChamado.getVlrString() );

		if ( txtCodEspec.getVlrInteger() > 0 ) {
			ps.setInt( 23, Aplicativo.iCodEmp );
			ps.setInt( 24, ListaCampos.getMasterFilial( "ATESPECATEND" ) );
			ps.setInt( 25, txtCodEspec.getVlrInteger() );
		}
		else {
			ps.setNull( 23, Types.INTEGER );
			ps.setNull( 24, Types.INTEGER );
			ps.setNull( 25, Types.INTEGER );
		}


		ps.setInt( 26, Aplicativo.iCodEmp );
		ps.setInt( 27, ListaCampos.getMasterFilial( "VDCLIENTE" ) );
		ps.setInt( 28, txtCodCli.getVlrInteger() );

		ps.setInt( 29, Aplicativo.iCodEmp );
		ps.setInt( 30, ListaCampos.getMasterFilial( "ATATENDIMENTO" ) );
		ps.setInt( 31, txtCodAtendo.getVlrInteger() );

		ps.executeUpdate();

		con.commit();
		
		ps.close();
		
		if(corig instanceof FCRM) {
			
			(( FCRM ) corig).carregaAtendimentos();	
			
		}
		

	}
	



}
