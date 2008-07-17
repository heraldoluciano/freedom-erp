package SoftwareExpress.SiTef ;


public class jCliSiTefI
{ 
  public String EnderecoSiTef;
  public String CodigoLoja;
  public String NumeroTerminal;
  public static short ConfiguraResultado;

  public static int Modalidade; 
  public String Valor;
  public String NumeroCuponFiscal;
  public String DataFiscal;
  public String Horario;
  public String Operador;
  public String Restricoes;

  public static int ProximoComando;
  public static int TipoCampo;
  public static short TamanhoMinimo;
  public static short TamanhoMaximo;
  public String Buffer;
  public static int ContinuaNavegacao;

  public static short Confirma;


  // metodos de acesso a DLL
  private native int _ConfiguraIntSiTefInterativo ( );
  private native int _IniciaFuncaoSiTefInterativo ( );
  private native int _ContinuaFuncaoSiTefInterativo ( );
  private native int _FinalizaTransacaoSiTefInterativo ( );


  // Carga da DLL
  static {
    System.loadLibrary ("jCliSiTefI"); 
  } 



  public int ConfiguraIntSiTefInterativo ( )
  {
    return _ConfiguraIntSiTefInterativo ( );
  }

  public int IniciaFuncaoSiTefInterativo ( )
  {
    return _IniciaFuncaoSiTefInterativo ( );
  }

  public int ContinuaFuncaoSiTefInterativo ( )
  {
    return _ContinuaFuncaoSiTefInterativo ( );
  }

  public int FinalizaTransacaoSiTefInterativo ( )
  {
    return _FinalizaTransacaoSiTefInterativo ( );
  }



  public void SetEnderecoSiTef (String _EnderecoSiTef) 
  {
    EnderecoSiTef = _EnderecoSiTef;
  }
  public String GetEnderecoSiTef ( ) 
  {
    return EnderecoSiTef;
  }


  public void SetCodigoLoja (String _CodigoLoja) 
  {
    CodigoLoja = _CodigoLoja;
  }
  public String GetCodigoLoja ( ) 
  {
    return CodigoLoja;
  }


  public void SetNumeroTerminal (String _NumeroTerminal) 
  {
    NumeroTerminal = _NumeroTerminal;
  }
  public String GetNumeroTerminal ( ) 
  {
    return NumeroTerminal;
  }


  public void SetConfiguraResultado (int _ConfiguraResultado)
  {
    ConfiguraResultado = (short) _ConfiguraResultado;
  }
  public void SetConfiguraResultado (short _ConfiguraResultado)
  {
    ConfiguraResultado = _ConfiguraResultado;
  }
  public short GetConfiguraResultado ( )
  {
    return ConfiguraResultado;
  }


  public void SetModalidade (int _Modalidade)
  {
    Modalidade = _Modalidade; 
  }
  public int GetModalidade ( )
  {
    return Modalidade; 
  }


  public void SetValor (String _Valor)
  {
    Valor = _Valor;
  }
  public String GetValor ( )
  {
    return Valor;
  }


  public void SetNumeroCuponFiscal (String _NumeroCuponFiscal)
  {
    NumeroCuponFiscal = _NumeroCuponFiscal;
  }
  public String GetNumeroCuponFiscal ( )
  {
   return NumeroCuponFiscal;
  }


  public void SetDataFiscal (String _DataFiscal)
  {
    DataFiscal = _DataFiscal;
  }
  public String GetDataFiscal ( )
  {
   return DataFiscal;
  }


  public void SetHorario (String _Horario)
  {
    Horario = _Horario;
  }
  public String GetHorario ( )
  {
    return Horario;
  }


  public void SetOperador (String _Operador)
  {
    Operador = _Operador;
  }
  public String GetOperador ( )
  {
    return Operador;
  }


  public void SetRestricoes (String _Restricoes)
  {
    Restricoes = _Restricoes;
  }
  public String GetRestricoes ( )
  {
    return Restricoes;
  }


  public void SetProximoComando (int _ProximoComando)
  {
    ProximoComando = _ProximoComando;
  }
  public int GetProximoComando ( )
  {
    return (ProximoComando);
  }


  public void SetTipoCampo (int _TipoCampo)
  {
    TipoCampo = _TipoCampo;
  }
  public int GetTipoCampo ( )
  {
    return TipoCampo;
  }


  public void SetTamanhoMinimo (int _TamanhoMinimo)
  {
    TamanhoMinimo = (short) _TamanhoMinimo;
  }
  public void SetTamanhoMinimo (short _TamanhoMinimo)
  {
    TamanhoMinimo = _TamanhoMinimo;
  }
  public short GetTamanhoMinimo ( )
  {
    return TamanhoMinimo;
  }


  public void SetTamanhoMaximo (int _TamanhoMaximo)
  {
    TamanhoMaximo = (short) _TamanhoMaximo;
  }
  public void SetTamanhoMaximo (short _TamanhoMaximo)
  {
    TamanhoMaximo = _TamanhoMaximo;
  }
  public short GetTamanhoMaximo ( )
  {
    return TamanhoMaximo;
  }


  public void SetBuffer (String _Buffer)
  {
    Buffer = _Buffer;
  }
  public String GetBuffer ( )
  {
    return Buffer;
  }


  public void SetContinuaNavegacao (int _ContinuaNavegacao)
  {
    ContinuaNavegacao = _ContinuaNavegacao;
  }
  public int GetContinuaNavegacao ( )
  {
    return ContinuaNavegacao;
  }


  public void SetConfirma (short _Confirma)
  {
    Confirma = _Confirma;
  }
  public short GetConfirma ( )
  {
    return Confirma;
  }
} 

