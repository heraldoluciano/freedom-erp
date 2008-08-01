
package SoftwareExpress.SiTef;

public class jCliSiTefI {

	private byte[] DadosRx;

	private String EnderecoSiTef;

	private String Buffer;

	private String Campo;

	private String ChaveSeguranca;

	private String ChaveTrnCancelamento;

	private String CodigoEmBarras;

	private String CodigoLoja;

	private static short CodigoResposta;

	private static String CodigoServico;

	private static short ConfiguraResultado;

	private static short Confirma;

	private static int ContinuaNavegacao;

	private String DadosContas;

	private String DadosDesfazimento;

	private String DadosServico;

	private String DataFiscal;

	private static short Delimitador;

	private static short FuncaoSiTef;

	private String Horario;

	private static short IndiceParametro;

	private static int Modalidade;

	private String MsgDisplay;

	private String NumeroCuponFiscal;

	private String NumeroTerminal;

	private static short OffsetCartao;

	private String Operador;

	private String Parametro;

	private String ParametrosAdicionais;

	private static short ParametroCartao;

	private String Produtos;

	private static int ProximoComando;

	private static short RedeDestino;

	private String Restricoes;

	private String Senha;

	private static short eSenha;

	private static short TamanhoMinimo;

	private static short TamanhoMaximo;

	private static short TamMaxDadosRx;

	private static short TamMaxDadosServico;

	private static short TempoEsperaRx;

	private static short TipoTransacao;

	private static int TipoCampo;

	private static short TipoCodigoEmBarras;

	private String Trilha1;

	private String Trilha2;

	private String Valor;

	private String ValorTotalCupon;

	private String ValorTotalBonus;

	private byte[] DadosTx;

	private static short TamDadosTx;

	private native int _ConfiguraIntSiTefInterativo();

	private native int _ConfiguraIntSiTefInterativoEx();

	private native int _IniciaFuncaoSiTefInterativo();

	private native int _IniciaFuncaoAASiTefInterativo();

	private native int _IniciaConfiguracaoSiTefInterativo();

	private native int _EfetuaPagamentoSiTefInterativo();

	private native int _FinalizaTransacaoSiTefInterativo();

	private native int _FinalizaTransacaoSiTefInterativoBonus();

	private native int _DesfazTransacaoSiTefInterativo();

	private native int _FuncoesGerenciaisSiTefInterativo();

	private native int _IniciaCancelamentoIdentificadoSiTefInterativo();

	private native int _CorrespondenteBancarioSiTefInterativo();

	private native int _IniciaCancelamentoPagamentoContasSiTefInterativo();

	private native int _ValidaCampoCodigoEmBarras();

	private native int _EnviaRecebeSitServicos();

	private native int _EnviaRecebeSiTefDireto();

	private native int _ForneceParametroEnviaRecebeSiTefDireto();

	private native int _ExecutaEnviaRecebeSiTefDireto();

	private native int _ObtemRetornoEnviaRecebeSiTefDireto();

	private native int _ContinuaFuncaoSiTefInterativo();

	private native int _LeCartaoInterativo();

	private native int _LeCartaoDireto();

	private native int _LeCartaoDiretoEx();

	private native int _InterrompeLeCartaoDireto();

	private native int _LeSenhaInterativo();

	private native int _LeSenhaDireto();

	private native int _LeSenhaDiretoEx();

	private native int _VerificaPresencaPinPad();

	private native int _AbrePinPad();

	private native int _FechaPinPad();

	private native int _EscreveMensagemPinPad();

	private native int _LeSimNaoPinPad();

	private native int _EscreveMensagemPermanentePinPad();

	private native int _LeCampoPinPad();

	private native int _LeTeclaPinPad();

	private native int _EfetuaPagamentoAASiTefInterativo();

	private native int _IniciaFuncaoSiTefInterativoConsultaVidalink();

	private native int _InformaProdutoVendaVidalink();

	private native int _IniciaFuncaoSiTefInterativoVendaVidalink();

	private native int _InformaProdutoCancelamentoVidalink();

	private native int _IniciaFuncaoSiTefInterativoCancelamentoVidalink();

	private native int _ConsultaParametrosSiTef();

	private native int _FinalizaFuncaoSiTefInterativo();

	private native int _RegistraBonusOffLineSiTef();

	private native int _RegistraTefPromocaoSiTef();
	


	public int ConfiguraIntSiTefInterativo( String endereco, String loja, String terminal ) {

		EnderecoSiTef = endereco;
		CodigoLoja = loja;
		NumeroTerminal = terminal;
		
		return _ConfiguraIntSiTefInterativo();
	}

	public int ConfiguraIntSiTefInterativo() {
		return _ConfiguraIntSiTefInterativo();
	}

	public int IniciaFuncaoSiTefInterativo() {
		
		ProximoComando = 0;
		TipoCampo = 0;
		TamanhoMinimo = (short) 0;
		TamanhoMaximo = (short) 0;
		Buffer = "";
		ContinuaNavegacao = 0;
		
		return _IniciaFuncaoSiTefInterativo();
	}

	public int IniciaFuncaoSiTefInterativo( int modalidade, 
			                                String valor, 
			                                String cupom, 
			                                String data, 
			                                String hora, 
			                                String operador ) {
		Modalidade = modalidade;
		Valor = valor;
		NumeroCuponFiscal = cupom;
		DataFiscal = data;
		Horario = hora;
		Operador = operador;
		Restricoes = "";
		
		return IniciaFuncaoSiTefInterativo();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	public int IniciaFuncaoAASiTefInterativo() {
		ProximoComando = 0;
		TipoCampo = 0;
		TamanhoMinimo = (short) 0;
		TamanhoMaximo = (short) 0;
		return _IniciaFuncaoAASiTefInterativo();
	}

	public int IniciaFuncaoSiTefInterativoConsultaVidalink() {
		ProximoComando = 0;
		TipoCampo = 0;
		TamanhoMinimo = (short) 0;
		TamanhoMaximo = (short) 0;
		return _IniciaFuncaoSiTefInterativoConsultaVidalink();
	}

	public int InformaProdutoVendaVidalink() {
		return _InformaProdutoVendaVidalink();
	}

	public int IniciaFuncaoSiTefInterativoVendaVidalink() {
		ProximoComando = 0;
		TipoCampo = 0;
		TamanhoMinimo = (short) 0;
		TamanhoMaximo = (short) 0;
		return _IniciaFuncaoSiTefInterativoVendaVidalink();
	}

	public int InformaProdutoCancelamentoVidalink() {
		return _InformaProdutoCancelamentoVidalink();
	}

	public int IniciaFuncaoSiTefInterativoCancelamentoVidalink() {

		ProximoComando = 0;
		TipoCampo = 0;
		TamanhoMinimo = (short) 0;
		TamanhoMaximo = (short) 0;
		return _IniciaFuncaoSiTefInterativoCancelamentoVidalink();
	}

	public int ContinuaFuncaoSiTefInterativo() {
		return _ContinuaFuncaoSiTefInterativo();
	}

	public int EnviaRecebeSiTefDireto() {
		DadosRx = new byte[ TamMaxDadosRx ];
		return _EnviaRecebeSiTefDireto();
	}

	public int ConfiguraIntSiTefInterativoEx() {
		return _ConfiguraIntSiTefInterativoEx();
	}

	public int IniciaConfiguracaoSiTefInterativo() {
		return _IniciaConfiguracaoSiTefInterativo();
	}

	public int FinalizaTransacaoSiTefInterativo() {
		return _FinalizaTransacaoSiTefInterativo();
	}

	public int FinalizaTransacaoSiTefInterativoBonus() {
		return _FinalizaTransacaoSiTefInterativoBonus();
	}

	public int DesfazTransacaoSiTefInterativo() {
		return _DesfazTransacaoSiTefInterativo();
	}

	public int IniciaCancelamentoIdentificadoSiTefInterativo() {
		return _IniciaCancelamentoIdentificadoSiTefInterativo();
	}

	public int CorrespondenteBancarioSiTefInterativo() {
		return _CorrespondenteBancarioSiTefInterativo();
	}

	public int IniciaCancelamentoPagamentoContasSiTefInterativo() {
		return _IniciaCancelamentoPagamentoContasSiTefInterativo();
	}

	public int ValidaCampoCodigoEmBarras() {
		return _ValidaCampoCodigoEmBarras();
	}

	public int EnviaRecebeSitServicos() {
		DadosRx = new byte[ TamMaxDadosRx ];
		return _EnviaRecebeSitServicos();
	}

	public int ForneceParametroEnviaRecebeSiTefDireto() {
		return _ForneceParametroEnviaRecebeSiTefDireto();
	}

	public int ExecutaEnviaRecebeSiTefDireto() {
		return _ExecutaEnviaRecebeSiTefDireto();
	}

	public int ObtemRetornoEnviaRecebeSiTefDireto() {
		return _ObtemRetornoEnviaRecebeSiTefDireto();
	}

	public int LeCartaoInterativo() {
		return _LeCartaoInterativo();
	}

	public int LeCartaoDireto() {
		return _LeCartaoDireto();
	}

	public int LeSenhaInterativo() {
		return _LeSenhaInterativo();
	}

	public int LeSenhaDireto() {
		return _LeSenhaDireto();
	}

	public int InterrompeLeCartaoDireto() {
		return _InterrompeLeCartaoDireto();
	}

	public int EscreveMensagemPinPad() {
		return _EscreveMensagemPinPad();
	}

	public int LeSimNaoPinPad() {
		return _LeSimNaoPinPad();
	}

	public int EscreveMensagemPermanentePinPad() {
		return _EscreveMensagemPermanentePinPad();
	}

	public int LeCampoPinPad() {
		return _LeCampoPinPad();
	}

	public int ConfiguraIntSiTefInterativoEx( String string, String string_2_, String string_3_, String string_4_ ) {

		EnderecoSiTef = string;
		CodigoLoja = string_2_;
		NumeroTerminal = string_3_;
		ParametrosAdicionais = string_4_;
		return _ConfiguraIntSiTefInterativoEx();
	}

	public int IniciaConfiguracaoSiTefInterativo( String string, String string_5_, String string_6_ ) {

		EnderecoSiTef = string;
		CodigoLoja = string_5_;
		NumeroTerminal = string_6_;
		return _IniciaConfiguracaoSiTefInterativo();
	}

	public int IniciaFuncaoAASiTefInterativo( int i, String string, String string_12_, String string_13_, String string_14_, String string_15_, String string_16_, String string_17_ ) {

		Modalidade = i;
		Valor = string;
		NumeroCuponFiscal = string_12_;
		DataFiscal = string_13_;
		Horario = string_14_;
		Operador = string_15_;
		ParametrosAdicionais = string_16_;
		Produtos = string_17_;
		ProximoComando = 0;
		TipoCampo = 0;
		TamanhoMinimo = (short) 0;
		TamanhoMaximo = (short) 0;
		return _IniciaFuncaoAASiTefInterativo();
	}

	public int FinalizaTransacaoSiTefInterativo( short i, String string, String string_18_, String string_19_ ) {

		Confirma = i;
		NumeroCuponFiscal = string;
		DataFiscal = string_18_;
		Horario = string_19_;
		return _FinalizaTransacaoSiTefInterativo();
	}

	public int FinalizaTransacaoSiTefInterativoBonus( short i, String string, String string_20_, String string_21_, String string_22_, String string_23_ ) {

		
		Confirma = i;
		NumeroCuponFiscal = string;
		DataFiscal = string_20_;
		Horario = string_21_;
		ValorTotalCupon = string_22_;
		ValorTotalBonus = string_23_;
		return _FinalizaTransacaoSiTefInterativoBonus();
	}

	public int DesfazTransacaoSiTefInterativo( String string ) {

		DadosDesfazimento = string;
		return _DesfazTransacaoSiTefInterativo();
	}

	public int IniciaCancelamentoIdentificadoSiTefInterativo( String string, String string_24_, String string_25_, String string_26_, String string_27_ ) {

		NumeroCuponFiscal = string;
		DataFiscal = string_24_;
		Horario = string_25_;
		Operador = string_26_;
		ChaveTrnCancelamento = string_27_;
		return _IniciaCancelamentoIdentificadoSiTefInterativo();
	}

	public int CorrespondenteBancarioSiTefInterativo( String string, String string_28_, String string_29_, String string_30_, String string_31_ ) {

		NumeroCuponFiscal = string;
		DataFiscal = string_28_;
		Horario = string_29_;
		Operador = string_30_;
		ParametrosAdicionais = string_31_;
		return _CorrespondenteBancarioSiTefInterativo();
	}

	public int IniciaCancelamentoPagamentoContasSiTefInterativo( String string, String string_32_, String string_33_, String string_34_, String string_35_ ) {

		NumeroCuponFiscal = string;
		DataFiscal = string_32_;
		Horario = string_33_;
		Operador = string_34_;
		ParametrosAdicionais = string_35_;
		return _IniciaCancelamentoPagamentoContasSiTefInterativo();
	}

	public int ValidaCampoCodigoEmBarras( String string, short i ) {

		CodigoEmBarras = string;
		TipoCodigoEmBarras = i;
		return _ValidaCampoCodigoEmBarras();
	}

	public int EnviaRecebeSitServicos( short i, byte[] is, short i_36_, short i_37_, short i_38_, String string, String string_39_, String string_40_, String string_41_ ) {

		RedeDestino = i;
		DadosTx = is;
		TamDadosTx = i_36_;
		TamMaxDadosRx = i_37_;
		TempoEsperaRx = i_38_;
		NumeroCuponFiscal = string;
		DataFiscal = string_39_;
		Horario = string_40_;
		Operador = string_41_;
		DadosRx = new byte[ i_37_ ];
		return _EnviaRecebeSitServicos();
	}

	public int ForneceParametroEnviaRecebeSiTefDireto( short i, String string, short i_42_, short i_43_ ) {

		IndiceParametro = i;
		Parametro = string;
		ParametroCartao = i_42_;
		Delimitador = i_43_;
		return _ForneceParametroEnviaRecebeSiTefDireto();
	}

	public int ExecutaEnviaRecebeSiTefDireto( short i, short i_44_, short i_45_, String string, String string_46_, String string_47_, String string_48_, short i_49_ ) {

		RedeDestino = i;
		FuncaoSiTef = i_44_;
		TempoEsperaRx = i_45_;
		NumeroCuponFiscal = string;
		DataFiscal = string_46_;
		Horario = string_47_;
		Operador = string_48_;
		TipoTransacao = i_49_;
		return _ExecutaEnviaRecebeSiTefDireto();
	}

	public int ObtemRetornoEnviaRecebeSiTefDireto( String string, short i ) {
		CodigoServico = string;
		TamMaxDadosServico = i;
		return _ObtemRetornoEnviaRecebeSiTefDireto();
	}

	public int LeCartaoInterativo( String string ) {
		MsgDisplay = string;
		return _LeCartaoInterativo();
	}

	public int LeCartaoDireto( String string ) {
		MsgDisplay = string;
		return _LeCartaoDireto();
	}

	public int LeSenhaInterativo( String string ) {
		MsgDisplay = string;
		return _LeSenhaInterativo();
	}

	public int LeSenhaDireto( String string, String string_50_ ) {
		ChaveSeguranca = string;
		MsgDisplay = string_50_;
		return _LeSenhaDireto();
	}

	public int VerificaPresencaPinPad() {
		return _VerificaPresencaPinPad();
	}

	public int AbrePinPad() {
		return _AbrePinPad();
	}

	public int FechaPinPad() {
		return _FechaPinPad();
	}

	public int EscreveMensagemPinPad( String string ) {
		MsgDisplay = string;
		return _EscreveMensagemPinPad();
	}

	public int LeSimNaoPinPad( String string ) {
		MsgDisplay = string;
		return _LeSimNaoPinPad();
	}

	public int EscreveMensagemPermanentePinPad( String string ) {
		MsgDisplay = string;
		return _EscreveMensagemPermanentePinPad();
	}

	public int LeCampoPinPad( String string, short i, short i_51_ ) {

		MsgDisplay = string;
		TamanhoMaximo = i;		
		eSenha = i_51_;
		return _LeCampoPinPad();
	}

	public int LeTeclaPinPad() {
		return _LeTeclaPinPad();
	}

	public int EfetuaPagamentoSiTefInterativo( int i, String string, String string_52_, String string_53_, String string_54_, String string_55_, String string_56_ ) {

		Modalidade = i;
		Valor = string;
		NumeroCuponFiscal = string_52_;
		DataFiscal = string_53_;
		Horario = string_54_;
		Operador = string_55_;
		ParametrosAdicionais = string_56_;
		return _EfetuaPagamentoSiTefInterativo();
	}

	public int FuncoesGerenciaisSiTefInterativo( String string, String string_57_, String string_58_, String string_59_ ) {

		NumeroCuponFiscal = string;
		DataFiscal = string_57_;
		Horario = string_58_;
		Operador = string_59_;
		return _FuncoesGerenciaisSiTefInterativo();
	}

	public void SetEnderecoSiTef( String string ) {
		EnderecoSiTef = string;
	}

	public void SetBuffer( String string ) {
		Buffer = string;
	}

	public String GetBuffer() {
		return Buffer;
	}

	public String GetCampo() {
		return Campo;
	}

	public void SetChaveSeguranca( String string ) {
		ChaveSeguranca = string;
	}

	public void SetChaveTrnCancelamento( String string ) {
		ChaveTrnCancelamento = string;
	}

	public void SetCodigoEmBarras( String string ) {
		CodigoEmBarras = string;
	}

	public void SetCodigoLoja( String string ) {
		CodigoLoja = string;
	}

	public short GetCodigoResposta() {
		return CodigoResposta;
	}

	public void SetCodigoServico( String string ) {
		CodigoServico = string;
	}

	public void SetConfiguraResultado( short i ) {
		ConfiguraResultado = i;
	}

	public void SetConfiguraResultado( int i ) {
		SetConfiguraResultado( ConfiguraResultado );
	}

	public void SetConfirma( short i ) {
		Confirma = i;
	}

	public void SetConfirma( int i ) {
		SetConfirma( (short) i );
	}

	public void SetContinuaNavegacao( int i ) {
		ContinuaNavegacao = i;
	}

	public void SetDadosContas( String string ) {
		DadosContas = string;
	}

	public String GetDadosContas() {
		return DadosContas;
	}

	public void SetDadosDesfazimento( String string ) {
		DadosDesfazimento = string;
	}

	public String GetDadosDesfazimento() {
		return DadosDesfazimento;
	}

	public String GetDadosServico() {
		return DadosServico;
	}

	public void SetDataFiscal( String string ) {
		DataFiscal = string;
	}

	public void SetDelimitador( short i ) {
		Delimitador = i;
	}

	public void SetDelimitador( int i ) {
		SetDelimitador( (short) i );
	}

	public void SetFuncaoSiTef( short i ) {
		FuncaoSiTef = i;
	}

	public void SetFuncaoSiTef( int i ) {
		SetFuncaoSiTef( (short) i );
	}

	public void SetHorario( String string ) {
		Horario = string;
	}

	public void SetIndiceParametro( short i ) {
		IndiceParametro = i;
	}

	public void SetModalidade( int i ) {
		Modalidade = i;
	}

	public void SetMsgDisplay( String string ) {
		MsgDisplay = string;
	}

	public void SetNumeroCuponFiscal( String string ) {
		NumeroCuponFiscal = string;
	}

	public void SetNumeroTerminal( String string ) {
		NumeroTerminal = string;
	}

	public void SetOffsetCartao( short i ) {
		OffsetCartao = i;
	}

	public void SetOffsetCartao( int i ) {
		SetOffsetCartao( (short) i );
	}

	public void SetOperador( String string ) {
		Operador = string;
	}

	public void SetParametro( String string ) {
		Parametro = string;
	}

	public void SetRestricoes( String string ) {
		Restricoes = string;
	}

	public void SetParametrosAdicionais( String string ) {
		ParametrosAdicionais = string;
	}

	public void SetParametroCartao( short i ) {
		ParametroCartao = i;
	}

	public void SetParametroCartao( int i ) {
		SetParametroCartao( (short) i );
	}

	public void SetProdutos( String string ) {
		Produtos = string;
	}

	public int GetProximoComando() {
		return ProximoComando;
	}

	public void SetRedeDestino( short i ) {
		RedeDestino = i;
	}

	public void SetRedeDestino( int i ) {
		SetRedeDestino( (short) i );
	}

	public String GetSenha() {
		return Senha;
	}

	public void SeteSenha( short i ) {
		eSenha = i;
	}

	public void SeteSenha( int i ) {
		SeteSenha( (short) i );
	}

	public short GetTamanhoMinimo() {
		return TamanhoMinimo;
	}

	public short GetTamanhoMaximo() {
		return TamanhoMaximo;
	}

	public void SetTamMaxDadosRx( short i ) {
		TamMaxDadosRx = i;
	}

	public void SetTamMaxDadosRx( int i ) {
		SetTamMaxDadosRx( (short) i );
	}

	public void SetTamMaxDadosServico( short i ) {
		TamMaxDadosServico = i;
	}

	public void SetTamMaxDadosServico( int i ) {
		SetTamMaxDadosServico( (short) i );
	}

	public void SetTempoEsperaRx( short i ) {
		TempoEsperaRx = i;
	}

	public void SetTempoEsperaRx( int i ) {
		SetTempoEsperaRx( (short) i );
	}

	public void SetTipoTransacao( short i ) {
		TipoTransacao = i;
	}

	public void SetTipoTransacao( int i ) {
		SetTipoTransacao( (short) i );
	}

	public int GetTipoCampo() {
		return TipoCampo;
	}

	public short GetTipoCodigoEmBarras() {
		return TipoCodigoEmBarras;
	}

	public void SetTrilha1( String string ) {
		Trilha1 = string;
	}

	public String GetTrilha1() {
		return Trilha1;
	}

	public void SetTrilha2( String string ) {
		Trilha2 = string;
	}

	public String GetTrilha2() {
		return Trilha2;
	}

	public void SetValor( String string ) {
		Valor = string;
	}

	public void SetValorTotalCupon( String string ) {
		ValorTotalCupon = string;
	}

	public void SetValorTotalBonus( String string ) {
		ValorTotalBonus = string;
	}

	public void SetDadosTx( byte[] is ) {
		DadosTx = is;
	}

	public void SetTamDadosTx( short i ) {
		TamDadosTx = i;
	}

	public void SetTamDadosTx( int i ) {
		SetTamDadosTx( (short) i );
	}

	public byte[] GetDadosRx() {
		return DadosRx;
	}

	static {
		System.loadLibrary( "jCliSiTefI" );
	}
}