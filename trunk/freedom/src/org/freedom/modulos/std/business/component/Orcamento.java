package org.freedom.modulos.std.business.component;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.freedom.infra.pojos.Constant;
import org.freedom.library.functions.Funcoes;

public class Orcamento {

	public static final Constant STATUS_ABERTO = new Constant( "Aberto", "*" );

	public static final Constant STATUS_PENDENTE = new Constant( "Pendente", "OA" );

	public static final Constant STATUS_COMPLETO = new Constant( "Completo/Impresso", "OC" );

	public static final Constant STATUS_APROVADO = new Constant( "Liberado/Aprovado", "OL" );

	public static final Constant STATUS_FATURADO = new Constant( "Faturado", "OV" );

	public static final Constant STATUS_FATURADO_PARCIAL = new Constant( "Fat. parcial", "FP" );

	public static final Constant STATUS_PRODUZIDO = new Constant( "Produzido", "OP" );

	public static final Constant STATUS_CANCELADO = new Constant( "Cancelado", "CA" );

	public enum PrefOrc {
		USAREFPROD, USALIQREL, TIPOPRECOCUSTO, CODTIPOMOV2, DESCCOMPPED, USAORCSEQ, OBSCLIVEND, RECALCPCORC, USABUSCAGENPROD, USALOTEORC, CONTESTOQ, 
		TITORCTXT01, VENDAMATPRIM, VISUALIZALUCR, DIASVENCORC, CODCLI, CODPLANOPAG, PRAZO, CLASSORC, DESCORC, CONTRIBIPI, ABATRANSP, ORDNOTA, TIPOCUSTO, 
		HABVLRTOTITORC, SMODONOTA, COMISSAODESCONTO, VDPRODQQCLAS, BLOQDESCCOMPORC, BLOQPRECOORC, BLOQCOMISSORC, PERMITIMPORCANTAP
		, BLOQEDITORCAPOSAP, CODMODELOR, REPLICAORC, SQLREPLICAORC;
	}

	public static Date getVencimento( int diasvencorc ) {

		Date dtRet = null;

		try {

			GregorianCalendar clVenc = new GregorianCalendar();
			clVenc.add( Calendar.DATE, diasvencorc );

			dtRet = clVenc.getTime();

		} catch ( Exception err ) {
			Funcoes.mensagemErro( null, "Erro ao buscar a data de vencimento.\n" + err.getMessage(), true, err );
			err.printStackTrace();
		}

		return dtRet;
	}


}
