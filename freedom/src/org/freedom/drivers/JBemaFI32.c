#include <windows.h>
#include <jni.h>
#include <stdio.h>
#include <stdlib.h>
#include <windows.h>
#include "bibli_drivers_JBemaFI32.h"
#ifdef __BORLANDC__
  #pragma argsused
#endif
extern __stdcall Bematech_FI_AlteraSimboloMoeda( char * SimboloMoeda );
extern __stdcall Bematech_FI_ProgramaAliquota( char * Aliquota, int ICMS_ISS );
extern __stdcall Bematech_FI_ProgramaHorarioVerao();
extern __stdcall Bematech_FI_NomeiaDepartamento( int Indice, char * Departamento );
extern __stdcall Bematech_FI_NomeiaTotalizadorNaoSujeitoIcms( int Indice, char * Totalizador );
extern __stdcall Bematech_FI_ProgramaArredondamento();
extern __stdcall Bematech_FI_ProgramaTruncamento();
extern __stdcall Bematech_FI_LinhasEntreCupons( int Linhas );
extern __stdcall Bematech_FI_EspacoEntreLinhas( int Dots );
extern __stdcall Bematech_FI_ForcaImpactoAgulhas( int ForcaImpacto );

// Funções do Cupom Fiscal

extern __stdcall Bematech_FI_AbreCupom( char * CGC_CPF );
extern __stdcall Bematech_FI_VendeItem
     ( char * Codigo, char * Descricao, char * Aliquota, char * TipoQuantidade, char * Quantidade, short CasasDecimais,
     char * ValorUnitario, char * TipoDesconto, char * ValorDesconto );
//   extern __stdcall Bematech_FI_VendeItem( char* Codigo, char* Descricao, char* Aliquota, char* TipoQuantidade, char*
// Quantidade, short CasasDecimais, char* ValorUnitario, char* TipoDesconto, char* Desconto);
extern __stdcall Bematech_FI_VendeItemDepartamento( char * Codigo, char * Descricao, char * Aliquota, char * ValorUnitario,
     char * Quantidade, char * Acrescimo, char * Desconto, char * IndiceDepartamento, char * UnidadeMedida );
extern __stdcall Bematech_FI_CancelaItemAnterior();
extern __stdcall Bematech_FI_CancelaItemGenerico( char * NumeroItem );
extern __stdcall Bematech_FI_CancelaCupom();
extern __stdcall Bematech_FI_FechaCupomResumido( char * FormaPagamento, char * Mensagem );
extern __stdcall Bematech_FI_FechaCupom( char * FormaPagamento, char * AcrescimoDesconto, char * TipoAcrescimoDesconto,
     char * ValorAcrescimoDesconto, char * ValorPago, char * Mensagem );
extern __stdcall Bematech_FI_ResetaImpressora();
extern __stdcall Bematech_FI_IniciaFechamentoCupom( char * AcrescimoDesconto, char * TipoAcrescimoDesconto,
     char * ValorAcrescimoDesconto );
extern __stdcall Bematech_FI_EfetuaFormaPagamento( char * FormaPagamento, char * ValorFormaPagamento );
extern __stdcall Bematech_FI_EfetuaFormaPagamentoDescricaoForma( char * FormaPagamento, char * ValorFormaPagamento,
     char * DescricaoFormaPagto );
extern __stdcall Bematech_FI_TerminaFechamentoCupom( char * Mensagem );
extern __stdcall Bematech_FI_EstornoFormasPagamento( char * FormaOrigem, char * FormaDestino, char * Valor );
extern __stdcall Bematech_FI_UsaUnidadeMedida( char * UnidadeMedida );
extern __stdcall Bematech_FI_AumentaDescricaoItem( char * Descricao );

// Funções dos Relatórios Fiscais

extern __stdcall Bematech_FI_LeituraX();
extern __stdcall Bematech_FI_ReducaoZ( char * Data, char * Hora );
extern __stdcall Bematech_FI_RelatorioGerencial( char * Texto );
extern __stdcall Bematech_FI_FechaRelatorioGerencial();
extern __stdcall Bematech_FI_LeituraMemoriaFiscalData( char * DataInicial, char * DataFinal );
extern __stdcall Bematech_FI_LeituraMemoriaFiscalReducao( char * ReducaoInicial, char * ReducaoFinal );
extern __stdcall Bematech_FI_LeituraMemoriaFiscalSerialData( char * DataInicial, char * DataFinal );
extern __stdcall Bematech_FI_LeituraMemoriaFiscalSerialReducao( char * ReducaoInicial, char * ReducaoFinal );

// Funções das Operações Não Fiscais

extern __stdcall Bematech_FI_RecebimentoNaoFiscal( char * IndiceTotalizador, char * Valor, char * FormaPagamento );
extern __stdcall Bematech_FI_AbreComprovanteNaoFiscalVinculado( char * FormaPagamento, char * Valor, char * NumeroCupom );
extern __stdcall Bematech_FI_UsaComprovanteNaoFiscalVinculado( char * Texto );
extern __stdcall Bematech_FI_FechaComprovanteNaoFiscalVinculado();
extern __stdcall Bematech_FI_Sangria( char * Valor );
extern __stdcall Bematech_FI_Suprimento( char * Valor, char * FormaPagamento );


// Funções de Informações da Impressora

extern __stdcall Bematech_FI_NumeroSerie( char * NumeroSerie );
extern __stdcall Bematech_FI_SubTotal( char * SubTotal );
extern __stdcall Bematech_FI_NumeroCupom( char * NumeroCupom );
extern __stdcall Bematech_FI_LeituraXSerial();
extern __stdcall Bematech_FI_VersaoFirmware( char * VersaoFirmware );
extern __stdcall Bematech_FI_CGC_IE( char * CGC, char * IE );
extern __stdcall Bematech_FI_GrandeTotal( char * GrandeTotal );
extern __stdcall Bematech_FI_Cancelamentos( char * ValorCancelamentos );
extern __stdcall Bematech_FI_Descontos( char * ValorDescontos );
extern __stdcall Bematech_FI_NumeroOperacoesNaoFiscais( char * NumeroOperacoes );
extern __stdcall Bematech_FI_NumeroCuponsCancelados( char * NumeroCancelamentos );
extern __stdcall Bematech_FI_NumeroIntervencoes( char * Numero__stdcallervencoes );
extern __stdcall Bematech_FI_NumeroReducoes( char * NumeroReducoes );
extern __stdcall Bematech_FI_NumeroSubstituicoesProprietario( char * NumeroSubstituicoes );
extern __stdcall Bematech_FI_UltimoItemVendido( char * NumeroItem );
extern __stdcall Bematech_FI_ClicheProprietario( char * Cliche );
extern __stdcall Bematech_FI_NumeroCaixa( char * NumeroCaixa );
extern __stdcall Bematech_FI_NumeroLoja( char * NumeroLoja );
extern __stdcall Bematech_FI_SimboloMoeda( char * SimboloMoeda );
extern __stdcall Bematech_FI_MinutosLigada( char * Minutos );
extern __stdcall Bematech_FI_MinutosImprimindo( char * Minutos );
extern __stdcall Bematech_FI_VerificaModoOperacao( char * Modo );
extern __stdcall Bematech_FI_VerificaEpromConectada( char * Flag );
extern __stdcall Bematech_FI_FlagsFiscais( int Flag );
extern __stdcall Bematech_FI_ValorPagoUltimoCupom( char * ValorCupom );
extern __stdcall Bematech_FI_DataHoraImpressora( char * Data, char * Hora );
extern __stdcall Bematech_FI_ContadoresTotalizadoresNaoFiscais( char * Contadores );
extern __stdcall Bematech_FI_VerificaTotalizadoresNaoFiscais( char * Totalizadores );
extern __stdcall Bematech_FI_DataHoraReducao( char * Data, char * Hora );
extern __stdcall Bematech_FI_DataMovimento( char * Data );
extern __stdcall Bematech_FI_VerificaTruncamento( char * Flag );
extern __stdcall Bematech_FI_Acrescimos( char * ValorAcrescimos );
extern __stdcall Bematech_FI_ContadorBilhetePassagem( char * ContadorPassagem );
extern __stdcall Bematech_FI_VerificaAliquotasIss( char * Flag );
extern __stdcall Bematech_FI_VerificaFormasPagamento( char * Formas );
extern __stdcall Bematech_FI_VerificaRecebimentoNaoFiscal( char * Recebimentos );
extern __stdcall Bematech_FI_VerificaDepartamentos( char * Departamentos );
extern __stdcall Bematech_FI_VerificaTipoImpressora( int TipoImpressora );
extern __stdcall Bematech_FI_VerificaTotalizadoresParciais( char * Totalizadores );
extern __stdcall Bematech_FI_RetornoAliquotas( char * Aliquotas );
extern __stdcall Bematech_FI_VerificaEstadoImpressora( int ACK, int ST1, int ST2 );
extern __stdcall Bematech_FI_DadosUltimaReducao( char * DadosReducao );
extern __stdcall Bematech_FI_MonitoramentoPapel( int Linhas );
extern __stdcall Bematech_FI_VerificaIndiceAliquotasIss( char * Flag );
extern __stdcall Bematech_FI_ValorFormaPagamento( char * FormaPagamento, char * Valor );
extern __stdcall Bematech_FI_ValorTotalizadorNaoFiscal( char * Totalizador, char * Valor );

// Funções de Autenticação e Gaveta de Dinheiro

extern __stdcall Bematech_FI_Autenticacao();
extern __stdcall Bematech_FI_ProgramaCaracterAutenticacao( char * Parametros );
extern __stdcall Bematech_FI_AcionaGaveta();
extern __stdcall Bematech_FI_VerificaEstadoGaveta( int EstadoGaveta );

// Funções para a Impressora Restaurante

extern __stdcall Bematech_FIR_AbreCupomRestaurante( char * Mesa, char * CGC_CPF );
extern __stdcall Bematech_FIR_RegistraVenda( char * Mesa, char * Codigo, char * Descricao, char * Aliquota, char * Quantidade,
     char * ValorUnitario, char * FlagAcrescimoDesconto, char * ValorAcrescimoDesconto );
extern __stdcall Bematech_FIR_CancelaVenda( char * Mesa, char * Codigo, char * Descricao, char * Aliquota, char * Quantidade,
     char * ValorUnitario, char * FlagAcrescimoDesconto, char * ValorAcrescimoDesconto );
extern __stdcall Bematech_FIR_ConferenciaMesa( char * Mesa, char * FlagAcrescimoDesconto, char * TipoAcrescimoDesconto,
     char * ValorAcrescimoDesconto );
extern __stdcall Bematech_FIR_AbreConferenciaMesa( char * Mesa );
extern __stdcall Bematech_FIR_FechaConferenciaMesa( char * FlagAcrescimoDesconto, char * TipoAcrescimoDesconto,
     char * ValorAcrescimoDesconto );
extern __stdcall Bematech_FIR_TransferenciaMesa( char * MesaOrigem, char * MesaDestino );
extern __stdcall Bematech_FIR_ContaDividida( char * NumeroCupons, char * ValorPago, char * CGC_CPF );
extern __stdcall Bematech_FIR_FechaCupomContaDividida( char * NumeroCupons, char * FlagAcrescimoDesconto,
     char * TipoAcrescimoDesconto, char * ValorAcrescimoDesconto, char * FormasPagamento, char * ValorFormasPagamento,
     char * ValorPagoCliente, char * CGC_CPF );
extern __stdcall Bematech_FIR_TransferenciaItem( char * MesaOrigem, char * Codigo, char * Descricao, char * Aliquota,
     char * Quantidade, char * ValorUnitario, char * FlagAcrescimoDesconto,
     char * ValorAcrescimoDesconto, char * MesaDestino );
extern __stdcall Bematech_FIR_RelatorioMesasAbertas( int TipoRelatorio );
extern __stdcall Bematech_FIR_ImprimeCardapio();
extern __stdcall Bematech_FIR_RelatorioMesasAbertasSerial();
extern __stdcall Bematech_FIR_CardapioPelaSerial();
extern __stdcall Bematech_FIR_RegistroVendaSerial( char * Mesa );
extern __stdcall Bematech_FIR_VerificaMemoriaLivre( char * Bytes );
extern __stdcall Bematech_FIR_FechaCupomRestaurante( char * FormaPagamento, char * FlagAcrescimoDesconto,
     char * TipoAcrescimoDesconto, char * ValorAcrescimoDesconto, char * ValorFormaPagto, char * Mensagem );
extern __stdcall Bematech_FIR_FechaCupomResumidoRestaurante( char * FormaPagamento, char * Mensagem );

// Função para a Impressora Bilhete de Passagem

extern __stdcall Bematech_FI_AbreBilhetePassagem( char * ImprimeValorFinal, char * ImprimeEnfatizado, char * Embarque,
     char * Destino, char * Linha, char * Prefixo, char * Agente, char * Agencia, char * Data, char * Hora,
     char * Poltrona, char * Plataforma );

// Funções de Impressão de Cheques

extern __stdcall Bematech_FI_ProgramaMoedaSingular( char * MoedaSingular );
extern __stdcall Bematech_FI_ProgramaMoedaPlural( char * MoedaPlural );
extern __stdcall Bematech_FI_CancelaImpressaoCheque();
extern __stdcall Bematech_FI_VerificaStatusCheque( int StatusCheque );
extern __stdcall Bematech_FI_ImprimeCheque( char * Banco, char * Valor, char * Favorecido, char * Cidade,
     char * Data, char * Mensagem );
extern __stdcall Bematech_FI_IncluiCidadeFavorecido( char * Cidade, char * Favorecido );
extern __stdcall Bematech_FI_ImprimeCopiaCheque();

// Funções para o TEF
/* extern __stdcall Bematech_FITEF_Status(char* Identificacao); extern __stdcall Bematech_FITEF_VendaCartao(char* Identificacao, char*
ValorCompra); extern __stdcall Bematech_FITEF_ConfirmaVenda(char* Identificacao, char* ValorCompra, char* Header);
extern __stdcall Bematech_FITEF_NaoConfirmaVendaImpressao(char* Identificacao, char* ValorCompra);
extern __stdcall Bematech_FITEF_CancelaVendaCartao(char* Identificacao, char* ValorCompra, char* Nsu, char* NumeroCupom, char*
Hora, char* Data, char* Rede); extern __stdcall Bematech_FITEF_ImprimeTEF(char* Identificacao, char* FormaPagamento, char* ValorCompra);
extern __stdcall Bematech_FITEF_ImprimeRelatorio(); extern __stdcall Bematech_FITEF_ADM(char* Identificacao);
extern __stdcall Bematech_FITEF_VendaCompleta(char* Identificacao, char* ValorCompra, char* FormaPagamento, char* Texto);
extern __stdcall Bematech_FITEF_ConfiguraDiretorioTef(char* PathREQ, char* PathRESP);
extern __stdcall Bematech_FITEF_VendaCheque(char* Identificacao, char* ValorCompra); extern __stdcall Bematech_FITEF_ApagaResiduos(); */
// Outras Funções

extern __stdcall Bematech_FI_AbrePortaSerial();
extern __stdcall Bematech_FI_RetornoImpressora( int ACK, int ST1, int ST2 );
extern __stdcall Bematech_FI_FechaPortaSerial();
extern __stdcall Bematech_FI_MapaResumo();
extern __stdcall Bematech_FI_AberturaDoDia( char * ValorCompra, char * FormaPagamento );
extern __stdcall Bematech_FI_FechamentoDoDia();
extern __stdcall Bematech_FI_ImprimeConfiguracoesImpressora();
extern __stdcall Bematech_FI_ImprimeDepartamentos();
extern __stdcall Bematech_FI_RelatorioTipo60Analitico();
extern __stdcall Bematech_FI_RelatorioTipo60Mestre();
extern __stdcall Bematech_FI_VerificaImpressoraLigada();
void logger(char *);

int WINAPI DllMain( HINSTANCE hInst, /* Library instance handle. */
unsigned long reason, /* Reason this function is being called. */
void * lpReserved ) /* Not used. */
{
  return 1;
}

int WINAPI DllEntryPoint( HINSTANCE hInst, unsigned long reason, void * lpReserved )
{
  return DllMain( hInst, reason, lpReserved );
}


// Funções de Inicialização


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bAlteraSimboloMoeda * Signature: (Ljava/lang/String;)I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bAlteraSimboloMoeda( JNIEnv * env, jobject obj, jstring str1 )
{
  char * pStr1;
  int iRetorno;

  pStr1 = ( char * ) ( * env )->GetStringUTFChars( env, str1, 0 );

  iRetorno = Bematech_FI_AlteraSimboloMoeda( pStr1 );

  ( * env )->ReleaseStringChars( env, str1, ( jchar * ) pStr1 );

  return iRetorno;
}

/* * Class:     bibli_drivers_JBemaFI32 * Method:    bProgramaAliquota * Signature: (Ljava/lang/char*;I)I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bProgramaAliquota( JNIEnv * env, jobject obj, jstring str1, jint i1 )
{
  char * pStr1;
  int iRetorno;

  pStr1 = ( char * ) ( * env )->GetStringUTFChars( env, str1, 0 );

  iRetorno = Bematech_FI_ProgramaAliquota( pStr1, i1 );


  ( * env )->ReleaseStringChars( env, str1, ( jchar * ) pStr1 );

  return iRetorno;


}

/* * Class:     bibli_drivers_JBemaFI32 * Method:    bProgramaHorarioVerao * Signature: ()I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bProgramaHorarioVerao( JNIEnv * env, jobject obj )
{
  return Bematech_FI_ProgramaHorarioVerao();
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bNomeiaDepartamento * Signature: (ILjava/lang/char*;)I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bNomeiaDepartamento( JNIEnv * env, jobject obj, jint i1, jstring str1 )
{
  char * pStr1;
  int iRetorno;

  pStr1 = ( char * ) ( * env )->GetStringUTFChars( env, str1, 0 );

  iRetorno = Bematech_FI_NomeiaDepartamento( i1, pStr1 );


  ( * env )->ReleaseStringChars( env, str1, ( jchar * ) pStr1 );

  return iRetorno;
}

/* * Class:     bibli_drivers_JBemaFI32 * Method:    bNomeiaTotalizadorNaoSujeitoIcms * Signature: (ILjava/lang/char*;)I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bNomeiaTotalizadorNaoSujeitoIcms
     ( JNIEnv * env, jobject obj, jint i1, jstring str1 )
     {
       char * pStr1;
       int iRetorno;

       pStr1 = ( char * ) ( * env )->GetStringUTFChars( env, str1, 0 );

       iRetorno = Bematech_FI_NomeiaTotalizadorNaoSujeitoIcms( i1, pStr1 );


       ( * env )->ReleaseStringChars( env, str1, ( jchar * ) pStr1 );

       return iRetorno;
}

/* * Class:     bibli_drivers_JBemaFI32 * Method:    bProgramaArredondamento * Signature: ()I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bProgramaArredondamento( JNIEnv * env, jobject obj )
{
  return Bematech_FI_ProgramaArredondamento();
}

/* * Class:     bibli_drivers_JBemaFI32 * Method:    bProgramaTruncamento * Signature: ()I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bProgramaTruncamento( JNIEnv * env, jobject obj )
{
  return Bematech_FI_ProgramaTruncamento();
}

/* * Class:     bibli_drivers_JBemaFI32 * Method:    bLinhasEntreCupons * Signature: (I)I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bLinhasEntreCupons( JNIEnv * env, jobject obj, jint i1 )
{
  return Bematech_FI_LinhasEntreCupons( i1 );
}

/* * Class:     bibli_drivers_JBemaFI32 * Method:    bEspacoEntreLinhas * Signature: (I)I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bEspacoEntreLinhas( JNIEnv * env, jobject obj, jint i1 )
{
  return Bematech_FI_EspacoEntreLinhas( i1 );
}

/* * Class:     bibli_drivers_JBemaFI32 * Method:    bForcaImpactoAgulhas * Signature: (I)I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bForcaImpactoAgulhas( JNIEnv * env, jobject obj, jint i1 )
{
  return Bematech_FI_ForcaImpactoAgulhas( i1 );
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bAbreCupom * Signature: (Ljava/lang/char*;)I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bAbreCupom( JNIEnv * env, jobject obj, jstring str1 )
{
  char * pStr1;
  int iRetorno;

  pStr1 = ( char * ) ( * env )->GetStringUTFChars( env, str1, 0 );

  iRetorno = Bematech_FI_AbreCupom( pStr1 );

  ( * env )->ReleaseStringChars( env, str1, ( jchar * ) pStr1 );

  return iRetorno;
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bVendeItem
* Signature: (Ljava/lang/char*;Ljava/lang/char*;Ljava/lang/char*;Ljava/lang/char*;Ljava/lang/char*;ILjava/lang/char*;Ljava/lang/char*;Ljava/lang/char*;)I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bVendeItem
     ( JNIEnv * env, jobject obj, jstring str1, jstring str2, jstring str3, jstring str4, jstring str5, jint i1, jstring str6,
     jstring str7, jstring str8 )
     {
       char * pStr1;
       char * pStr2;
       char * pStr3;
       char * pStr4;
       char * pStr5;
       char * pStr6;
       char * pStr7;
       char * pStr8;

       int iRetorno = -1;
       short teste = 2;

       pStr1 = ( char * ) ( * env )->GetStringUTFChars( env, str1, 0 );
       pStr2 = ( char * ) ( * env )->GetStringUTFChars( env, str2, 0 );
       pStr3 = ( char * ) ( * env )->GetStringUTFChars( env, str3, 0 );
       pStr4 = ( char * ) ( * env )->GetStringUTFChars( env, str4, 0 );
       pStr5 = ( char * ) ( * env )->GetStringUTFChars( env, str5, 0 );
       pStr6 = ( char * ) ( * env )->GetStringUTFChars( env, str6, 0 );
       pStr7 = ( char * ) ( * env )->GetStringUTFChars( env, str7, 0 );
       pStr8 = ( char * ) ( * env )->GetStringUTFChars( env, str8, 0 );


       iRetorno = Bematech_FI_VendeItem( pStr1,  pStr2,  pStr3, pStr4, pStr5, i1, pStr6, pStr7, pStr8);

       ( * env )->ReleaseStringChars( env, str1, ( jchar * ) pStr1 );
       ( * env )->ReleaseStringChars( env, str2, ( jchar * ) pStr2 );
       ( * env )->ReleaseStringChars( env, str3, ( jchar * ) pStr3 );
       ( * env )->ReleaseStringChars( env, str4, ( jchar * ) pStr4 );
       ( * env )->ReleaseStringChars( env, str5, ( jchar * ) pStr5 );
       ( * env )->ReleaseStringChars( env, str6, ( jchar * ) pStr6 );
       ( * env )->ReleaseStringChars( env, str7, ( jchar * ) pStr7 );
       ( * env )->ReleaseStringChars( env, str8, ( jchar * ) pStr8 );

       return iRetorno;
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bVendeItemDepartamento
* Signature: (Ljava/lang/char*;Ljava/lang/char*;Ljava/lang/char*;Ljava/lang/char*;Ljava/lang/char*;Ljava/lang/char*;Ljava/lang/char*;Ljava/lang/char*;Ljava/lang/char*;)I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bVendeItemDepartamento
     ( JNIEnv * env, jobject obj, jstring str1, jstring str2, jstring str3, jstring str4, jstring str5, jstring str6,
     jstring str7, jstring str8, jstring str9 )
     {
       char * pStr1;
       char * pStr2;
       char * pStr3;
       char * pStr4;
       char * pStr5;
       char * pStr6;
       char * pStr7;
       char * pStr8;
       char * pStr9;

       int iRetorno;

       pStr1 = ( char * ) ( * env )->GetStringUTFChars( env, str1, 0 );
       pStr2 = ( char * ) ( * env )->GetStringUTFChars( env, str2, 0 );
       pStr3 = ( char * ) ( * env )->GetStringUTFChars( env, str3, 0 );
       pStr4 = ( char * ) ( * env )->GetStringUTFChars( env, str4, 0 );
       pStr5 = ( char * ) ( * env )->GetStringUTFChars( env, str5, 0 );
       pStr6 = ( char * ) ( * env )->GetStringUTFChars( env, str6, 0 );
       pStr7 = ( char * ) ( * env )->GetStringUTFChars( env, str7, 0 );
       pStr8 = ( char * ) ( * env )->GetStringUTFChars( env, str8, 0 );
       pStr9 = ( char * ) ( * env )->GetStringUTFChars( env, str9, 0 );

       iRetorno = Bematech_FI_VendeItemDepartamento( pStr1, pStr2, pStr3, pStr4, pStr5, pStr6, pStr7, pStr8, pStr9 );

       ( * env )->ReleaseStringChars( env, str1, ( jchar * ) pStr1 );
       ( * env )->ReleaseStringChars( env, str2, ( jchar * ) pStr2 );
       ( * env )->ReleaseStringChars( env, str3, ( jchar * ) pStr3 );
       ( * env )->ReleaseStringChars( env, str4, ( jchar * ) pStr4 );
       ( * env )->ReleaseStringChars( env, str5, ( jchar * ) pStr5 );
       ( * env )->ReleaseStringChars( env, str6, ( jchar * ) pStr6 );
       ( * env )->ReleaseStringChars( env, str7, ( jchar * ) pStr7 );
       ( * env )->ReleaseStringChars( env, str8, ( jchar * ) pStr8 );
       ( * env )->ReleaseStringChars( env, str9, ( jchar * ) pStr9 );

       return iRetorno;
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bCancelaItemAnterior * Signature: ()I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bCancelaItemAnterior( JNIEnv * env, jobject obj )
{
  return Bematech_FI_CancelaItemAnterior();
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bCancelaItemGenerico * Signature: (Ljava/lang/char*;)I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bCancelaItemGenerico( JNIEnv * env, jobject obj, jstring str1 )
{
  char * pStr1;
  int iRetorno;

  pStr1 = ( char * ) ( * env )->GetStringUTFChars( env, str1, 0 );

  iRetorno = Bematech_FI_CancelaItemGenerico( pStr1 );

  ( * env )->ReleaseStringChars( env, str1, ( jchar * ) pStr1 );

  return iRetorno;
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bCancelaCupom * Signature: ()I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bCancelaCupom( JNIEnv * env, jobject obj )
{
  return Bematech_FI_CancelaCupom();
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bFechaCupomResumido * Signature: (Ljava/lang/char*;Ljava/lang/char*;)I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bFechaCupomResumido
     ( JNIEnv * env, jobject obj, jstring str1, jstring str2 )
     {
       char * pStr1;
       char * pStr2;
       int iRetorno;

       pStr1 = ( char * ) ( * env )->GetStringUTFChars( env, str1, 0 );
       pStr2 = ( char * ) ( * env )->GetStringUTFChars( env, str2, 0 );

       iRetorno = Bematech_FI_FechaCupomResumido( pStr1, pStr2 );

       ( * env )->ReleaseStringChars( env, str1, ( jchar * ) pStr1 );
       ( * env )->ReleaseStringChars( env, str2, ( jchar * ) pStr2 );

       return iRetorno;
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bFechaCupom
* Signature: (Ljava/lang/char*;Ljava/lang/char*;Ljava/lang/char*;Ljava/lang/char*;Ljava/lang/char*;Ljava/lang/char*;)I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bFechaCupom
     ( JNIEnv * env, jobject obj, jstring str1, jstring str2, jstring str3, jstring str4, jstring str5, jstring str6 )
     {
       char * pStr1;
       char * pStr2;
       char * pStr3;
       char * pStr4;
       char * pStr5;
       char * pStr6;
       int iRetorno;

       pStr1 = ( char * ) ( * env )->GetStringUTFChars( env, str1, 0 );
       pStr2 = ( char * ) ( * env )->GetStringUTFChars( env, str2, 0 );
       pStr3 = ( char * ) ( * env )->GetStringUTFChars( env, str3, 0 );
       pStr4 = ( char * ) ( * env )->GetStringUTFChars( env, str4, 0 );
       pStr5 = ( char * ) ( * env )->GetStringUTFChars( env, str5, 0 );
       pStr6 = ( char * ) ( * env )->GetStringUTFChars( env, str6, 0 );

       iRetorno = Bematech_FI_FechaCupom( pStr1, pStr2, pStr3, pStr4, pStr5, pStr6 );

       ( * env )->ReleaseStringChars( env, str1, ( jchar * ) pStr1 );
       ( * env )->ReleaseStringChars( env, str2, ( jchar * ) pStr2 );
       ( * env )->ReleaseStringChars( env, str3, ( jchar * ) pStr3 );
       ( * env )->ReleaseStringChars( env, str4, ( jchar * ) pStr4 );
       ( * env )->ReleaseStringChars( env, str5, ( jchar * ) pStr5 );
       ( * env )->ReleaseStringChars( env, str6, ( jchar * ) pStr6 );

       return iRetorno;
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bResetaImpressora * Signature: ()I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bResetaImpressora( JNIEnv * env, jobject obj )
{
  return Bematech_FI_ResetaImpressora();
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bIniciaFechamentoCupom
* Signature: (Ljava/lang/char*;Ljava/lang/char*;Ljava/lang/char*;)I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bIniciaFechamentoCupom
     ( JNIEnv * env, jobject obj, jstring str1, jstring str2, jstring str3 )
     {
       char * pStr1;
       char * pStr2;
       char * pStr3;
       int iRetorno;

       pStr1 = ( char * ) ( * env )->GetStringUTFChars( env, str1, 0 );
       pStr2 = ( char * ) ( * env )->GetStringUTFChars( env, str2, 0 );
       pStr3 = ( char * ) ( * env )->GetStringUTFChars( env, str3, 0 );

       iRetorno = Bematech_FI_IniciaFechamentoCupom( pStr1, pStr2, pStr3 );

       ( * env )->ReleaseStringChars( env, str1, ( jchar * ) pStr1 );
       ( * env )->ReleaseStringChars( env, str2, ( jchar * ) pStr2 );
       ( * env )->ReleaseStringChars( env, str3, ( jchar * ) pStr3 );

       return iRetorno;
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bEfetuaFormaPagamento
* Signature: (Ljava/lang/char*;Ljava/lang/char*;)I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bEfetuaFormaPagamento
     ( JNIEnv * env, jobject obj, jstring str1, jstring str2 )
     {
       char * pStr1;
       char * pStr2;
       int iRetorno;

       pStr1 = ( char * ) ( * env )->GetStringUTFChars( env, str1, 0 );
       pStr2 = ( char * ) ( * env )->GetStringUTFChars( env, str2, 0 );

       iRetorno = Bematech_FI_EfetuaFormaPagamento( pStr1, pStr2 );

       ( * env )->ReleaseStringChars( env, str1, ( jchar * ) pStr1 );
       ( * env )->ReleaseStringChars( env, str2, ( jchar * ) pStr2 );

       return iRetorno;
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bEfetuaFormaPagamentoDescricaoForma
* Signature: (Ljava/lang/char*;Ljava/lang/char*;Ljava/lang/char*;)I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bEfetuaFormaPagamentoDescricaoForma
     ( JNIEnv * env, jobject obj, jstring str1, jstring str2, jstring str3 )
     {
       char * pStr1;
       char * pStr2;
       char * pStr3;
       int iRetorno;

       pStr1 = ( char * ) ( * env )->GetStringUTFChars( env, str1, 0 );
       pStr2 = ( char * ) ( * env )->GetStringUTFChars( env, str2, 0 );
       pStr3 = ( char * ) ( * env )->GetStringUTFChars( env, str3, 0 );

       iRetorno = Bematech_FI_EfetuaFormaPagamentoDescricaoForma( pStr1, pStr2, pStr3 );

       ( * env )->ReleaseStringChars( env, str1, ( jchar * ) pStr1 );
       ( * env )->ReleaseStringChars( env, str2, ( jchar * ) pStr2 );
       ( * env )->ReleaseStringChars( env, str3, ( jchar * ) pStr3 );

       return iRetorno;
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bTerminaFechamentoCupom * Signature: (Ljava/lang/char*;)I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bTerminaFechamentoCupom( JNIEnv * env, jobject obj, jstring str1 )
{
  char * pStr1;
  int iRetorno;

  pStr1 = ( char * ) ( * env )->GetStringUTFChars( env, str1, 0 );

  iRetorno = Bematech_FI_TerminaFechamentoCupom( pStr1 );

  ( * env )->ReleaseStringChars( env, str1, ( jchar * ) pStr1 );

  return iRetorno;
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bEstornoFormasPagamento
* Signature: (Ljava/lang/char*;Ljava/lang/char*;Ljava/lang/char*;)I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bEstornoFormasPagamento
     ( JNIEnv * env, jobject obj, jstring str1, jstring str2, jstring str3 )
     {
       char * pStr1;
       char * pStr2;
       char * pStr3;
       int iRetorno;

       pStr1 = ( char * ) ( * env )->GetStringUTFChars( env, str1, 0 );
       pStr2 = ( char * ) ( * env )->GetStringUTFChars( env, str2, 0 );
       pStr3 = ( char * ) ( * env )->GetStringUTFChars( env, str3, 0 );

       iRetorno = Bematech_FI_EstornoFormasPagamento( pStr1, pStr2, pStr3 );

       ( * env )->ReleaseStringChars( env, str1, ( jchar * ) pStr1 );
       ( * env )->ReleaseStringChars( env, str2, ( jchar * ) pStr2 );
       ( * env )->ReleaseStringChars( env, str3, ( jchar * ) pStr3 );

       return iRetorno;
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bUsaUnidadeMedida * Signature: (Ljava/lang/char*;)I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bUsaUnidadeMedida( JNIEnv * env, jobject obj, jstring str1 )
{
  char * pStr1;
  int iRetorno;

  pStr1 = ( char * ) ( * env )->GetStringUTFChars( env, str1, 0 );

  iRetorno = Bematech_FI_UsaUnidadeMedida( pStr1 );

  ( * env )->ReleaseStringChars( env, str1, ( jchar * ) pStr1 );

  return iRetorno;
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bAumentaDescricaoItem * Signature: (Ljava/lang/char*;)I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bAumentaDescricaoItem( JNIEnv * env, jobject obj, jstring str1 )
{
  char * pStr1;
  int iRetorno;

  pStr1 = ( char * ) ( * env )->GetStringUTFChars( env, str1, 0 );

  iRetorno = Bematech_FI_AumentaDescricaoItem( pStr1 );

  ( * env )->ReleaseStringChars( env, str1, ( jchar * ) pStr1 );

  return iRetorno;
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bleiturax * Signature: ()I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bleiturax( JNIEnv * pEnv, jobject thisObj )
{
  return Bematech_FI_LeituraX();
}

/* * Class:     bibli_drivers_JBemaFI32 * Method:    bReducaoZ * Signature: (Ljava/lang/char*;Ljava/lang/char*;)I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bReducaoZ( JNIEnv * env, jobject obj, jstring str1, jstring str2 )
{
  char * pStr1;
  char * pStr2;
  char * sVar = (char *)malloc(500*sizeof(char));
  int iRetorno;

  pStr1 = ( char * ) ( * env )->GetStringUTFChars( env, str1, 0 );
  pStr2 = ( char * ) ( * env )->GetStringUTFChars( env, str2, 0 );

  sprintf(sVar,"reducao z -> %s,%s\n",pStr1, pStr2);
  logger(sVar);
  iRetorno = Bematech_FI_ReducaoZ( pStr1, pStr2 );

  ( * env )->ReleaseStringChars( env, str1, ( jchar * ) pStr1 );
  ( * env )->ReleaseStringChars( env, str2, ( jchar * ) pStr2 );

  return iRetorno;
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bRelatorioGerencial * Signature: (Ljava/lang/char*;)I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bRelatorioGerencial( JNIEnv * env, jobject obj, jstring str1 )
{
  char * pStr1;
  int iRetorno;

  pStr1 = ( char * ) ( * env )->GetStringUTFChars( env, str1, 0 );

  iRetorno = Bematech_FI_RelatorioGerencial( pStr1 );

  ( * env )->ReleaseStringChars( env, str1, ( jchar * ) pStr1 );

  return iRetorno;
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bFechaRelatorioGerencial * Signature: ()I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bFechaRelatorioGerencial( JNIEnv * env, jobject obj )
{
  return Bematech_FI_FechaRelatorioGerencial();
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bLeituraMemoriaFiscalData
* Signature: (Ljava/lang/char*;Ljava/lang/char*;)I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bLeituraMemoriaFiscalData
     ( JNIEnv * env, jobject obj, jstring str1, jstring str2 )
     {
       char * pStr1;
       char * pStr2;
       int iRetorno;

       pStr1 = ( char * ) ( * env )->GetStringUTFChars( env, str1, 0 );
       pStr2 = ( char * ) ( * env )->GetStringUTFChars( env, str2, 0 );

       iRetorno = Bematech_FI_LeituraMemoriaFiscalData( pStr1, pStr2 );

       ( * env )->ReleaseStringChars( env, str1, ( jchar * ) pStr1 );
       ( * env )->ReleaseStringChars( env, str2, ( jchar * ) pStr2 );

       return iRetorno;
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bLeituraMemoriaFiscalReducao
* Signature: (Ljava/lang/char*;Ljava/lang/char*;)I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bLeituraMemoriaFiscalReducao
     ( JNIEnv * env, jobject obj, jstring str1, jstring str2 )
     {
       char * pStr1;
       char * pStr2;
       int iRetorno;

       pStr1 = ( char * ) ( * env )->GetStringUTFChars( env, str1, 0 );
       pStr2 = ( char * ) ( * env )->GetStringUTFChars( env, str2, 0 );

       iRetorno = Bematech_FI_LeituraMemoriaFiscalReducao( pStr1, pStr2 );

       ( * env )->ReleaseStringChars( env, str1, ( jchar * ) pStr1 );
       ( * env )->ReleaseStringChars( env, str2, ( jchar * ) pStr2 );

       return iRetorno;
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bLeituraMemoriaFiscalSerialData
* Signature: (Ljava/lang/char*;Ljava/lang/char*;)I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bLeituraMemoriaFiscalSerialData
     ( JNIEnv * env, jobject obj, jstring str1, jstring str2 )
     {
       char * pStr1;
       char * pStr2;
       int iRetorno;

       pStr1 = ( char * ) ( * env )->GetStringUTFChars( env, str1, 0 );
       pStr2 = ( char * ) ( * env )->GetStringUTFChars( env, str2, 0 );

       iRetorno = Bematech_FI_LeituraMemoriaFiscalSerialData( pStr1, pStr2 );

       ( * env )->ReleaseStringChars( env, str1, ( jchar * ) pStr1 );
       ( * env )->ReleaseStringChars( env, str2, ( jchar * ) pStr2 );

       return iRetorno;
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bLeituraMemoriaFiscalSerialReducao
* Signature: (Ljava/lang/char*;Ljava/lang/char*;)I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bLeituraMemoriaFiscalSerialReducao
     ( JNIEnv * env, jobject obj, jstring str1, jstring str2 )
     {
       char * pStr1;
       char * pStr2;
       int iRetorno;

       pStr1 = ( char * ) ( * env )->GetStringUTFChars( env, str1, 0 );
       pStr2 = ( char * ) ( * env )->GetStringUTFChars( env, str2, 0 );

       iRetorno = Bematech_FI_LeituraMemoriaFiscalSerialReducao( pStr1, pStr2 );

       ( * env )->ReleaseStringChars( env, str1, ( jchar * ) pStr1 );
       ( * env )->ReleaseStringChars( env, str2, ( jchar * ) pStr2 );

       return iRetorno;
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bRecebimentoNaoFiscal
* Signature: (Ljava/lang/char*;Ljava/lang/char*;Ljava/lang/char*;)I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bRecebimentoNaoFiscal
     ( JNIEnv * env, jobject obj, jstring str1, jstring str2, jstring str3 )
     {
       char * pStr1;
       char * pStr2;
       char * pStr3;
       int iRetorno;

       pStr1 = ( char * ) ( * env )->GetStringUTFChars( env, str1, 0 );
       pStr2 = ( char * ) ( * env )->GetStringUTFChars( env, str2, 0 );
       pStr3 = ( char * ) ( * env )->GetStringUTFChars( env, str3, 0 );

       iRetorno = Bematech_FI_RecebimentoNaoFiscal( pStr1, pStr2, pStr3 );

       ( * env )->ReleaseStringChars( env, str1, ( jchar * ) pStr1 );
       ( * env )->ReleaseStringChars( env, str2, ( jchar * ) pStr2 );
       ( * env )->ReleaseStringChars( env, str3, ( jchar * ) pStr3 );

       return iRetorno;
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bAbreComprovanteNaoFiscalVinculado
* Signature: (Ljava/lang/char*;Ljava/lang/char*;Ljava/lang/char*;)I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bAbreComprovanteNaoFiscalVinculado
     ( JNIEnv * env, jobject obj, jstring str1, jstring str2, jstring str3 )
     {
       char * pStr1;
       char * pStr2;
       char * pStr3;
       int iRetorno;

       pStr1 = ( char * ) ( * env )->GetStringUTFChars( env, str1, 0 );
       pStr2 = ( char * ) ( * env )->GetStringUTFChars( env, str2, 0 );
       pStr3 = ( char * ) ( * env )->GetStringUTFChars( env, str3, 0 );

       iRetorno = Bematech_FI_AbreComprovanteNaoFiscalVinculado( pStr1, pStr2, pStr3 );

       ( * env )->ReleaseStringChars( env, str1, ( jchar * ) pStr1 );
       ( * env )->ReleaseStringChars( env, str2, ( jchar * ) pStr2 );
       ( * env )->ReleaseStringChars( env, str3, ( jchar * ) pStr3 );

       return iRetorno;
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bUsaComprovanteNaoFiscalVinculado * Signature: (Ljava/lang/char*;)I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bUsaComprovanteNaoFiscalVinculado
     ( JNIEnv * env, jobject obj, jstring str1 )
     {
       char * pStr1;
       int iRetorno;

       pStr1 = ( char * ) ( * env )->GetStringUTFChars( env, str1, 0 );

       iRetorno = Bematech_FI_UsaComprovanteNaoFiscalVinculado( pStr1 );

       ( * env )->ReleaseStringChars( env, str1, ( jchar * ) pStr1 );

       return iRetorno;
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bFechaComprovanteNaoFiscalVinculado * Signature: ()I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bFechaComprovanteNaoFiscalVinculado( JNIEnv * env, jobject obj )
{
  return Bematech_FI_FechaComprovanteNaoFiscalVinculado();
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bSangria * Signature: (Ljava/lang/char*;)I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bSangria( JNIEnv * env, jobject obj, jstring str1 )
{
  char * pStr1;
  int iRetorno;

  pStr1 = ( char * ) ( * env )->GetStringUTFChars( env, str1, 0 );

  iRetorno = Bematech_FI_Sangria( pStr1 );


  ( * env )->ReleaseStringChars( env, str1, ( jchar * ) pStr1 );

  return iRetorno;


}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bSuprimento * Signature: (Ljava/lang/char*;Ljava/lang/char*;)I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bSuprimento( JNIEnv * env, jobject obj, jstring str1, jstring str2 )
{
  char * pStr1;
  char * pStr2;
  int iRetorno;

  pStr1 = ( char * ) ( * env )->GetStringUTFChars( env, str1, 0 );
  pStr2 = ( char * ) ( * env )->GetStringUTFChars( env, str2, 0 );

  iRetorno = Bematech_FI_Suprimento( pStr1, pStr2 );

  ( * env )->ReleaseStringChars( env, str1, ( jchar * ) pStr1 );
  ( * env )->ReleaseStringChars( env, str2, ( jchar * ) pStr2 );


  return iRetorno;

}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bNumeroSerie * Signature: (Ljava/lang/char*;)I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bNumeroSerie( JNIEnv * env, jobject obj, jstring str1 )
{
  char * pStr1;
  int iRetorno;

  pStr1 = ( char * ) ( * env )->GetStringUTFChars( env, str1, 0 );

  iRetorno = Bematech_FI_NumeroSerie( pStr1 );

  ( * env )->ReleaseStringChars( env, str1, ( jchar * ) pStr1 );

  return iRetorno;
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bSubTotal * Signature: (Ljava/lang/char*;)I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bSubTotal( JNIEnv * env, jobject obj, jstring str1 )
{
  char * pStr1;
  int iRetorno;

  pStr1 = ( char * ) ( * env )->GetStringUTFChars( env, str1, 0 );

  iRetorno = Bematech_FI_SubTotal( pStr1 );

  ( * env )->ReleaseStringChars( env, str1, ( jchar * ) pStr1 );

  return iRetorno;
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bNumeroCupom * Signature: (Ljava/lang/char*;)I */
JNIEXPORT jstring JNICALL Java_bibli_drivers_JBemaFI32_bNumeroCupom( JNIEnv * env, jobject obj )
{
  char * sRet = ( char * ) malloc( 17 * sizeof( char ) );
  char * sVal = ( char * ) malloc( 7 * sizeof( char ) );
  int iRetorno = 0;
  sprintf( sVal, "%6s", " " );
  iRetorno = Bematech_FI_NumeroCupom( sVal );
  sprintf( sRet, "%-10d", iRetorno );
  strcat( sRet, sVal );
  return ( * env )->NewStringUTF( env, sRet );
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bLeituraXSerial * Signature: ()I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bLeituraXSerial( JNIEnv * env, jobject obj )
{
  return Bematech_FI_LeituraXSerial();
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bVersaoFirmware * Signature: (Ljava/lang/char*;)I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bVersaoFirmware( JNIEnv * env, jobject obj, jstring str1 )
{
  char * pStr1;
  int iRetorno;

  pStr1 = ( char * ) ( * env )->GetStringUTFChars( env, str1, 0 );

  iRetorno = Bematech_FI_VersaoFirmware( pStr1 );

  ( * env )->ReleaseStringChars( env, str1, ( jchar * ) pStr1 );

  return iRetorno;
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bCGC_IE * Signature: (Ljava/lang/char*;Ljava/lang/char*;)I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bCGC_1IE( JNIEnv * env, jobject obj, jstring str1, jstring str2 )
{
  char * pStr1;
  char * pStr2;
  int iRetorno;

  pStr1 = ( char * ) ( * env )->GetStringUTFChars( env, str1, 0 );
  pStr2 = ( char * ) ( * env )->GetStringUTFChars( env, str2, 0 );

  iRetorno = Bematech_FI_CGC_IE( pStr1, pStr2 );

  ( * env )->ReleaseStringChars( env, str1, ( jchar * ) pStr1 );
  ( * env )->ReleaseStringChars( env, str2, ( jchar * ) pStr2 );

  return iRetorno;
}



/* * Class:     bibli_drivers_JBemaFI32 * Method:    bGrandeTotal * Signature: ()Ljava/lang/String; */
JNIEXPORT jstring JNICALL Java_bibli_drivers_JBemaFI32_bGrandeTotal( JNIEnv * env, jobject obj )
{
  char * sRet = ( char * ) malloc( 29 * sizeof( char ) );
  char * sVal = ( char * ) malloc( 19 * sizeof( char ) );
  int iRetorno = 0;
  sprintf( sVal, "%18s", " " );

  iRetorno = Bematech_FI_GrandeTotal( sVal );

  sprintf( sRet, "%-10d", iRetorno );
  strcat( sRet, sVal );
  return ( * env )->NewStringUTF( env, sRet );
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bCancelamentos * Signature: ()Ljava/lang/String; */
JNIEXPORT jstring JNICALL Java_bibli_drivers_JBemaFI32_bCancelamentos( JNIEnv * env, jobject obj )
{
  char * sRet = ( char * ) malloc( 25 * sizeof( char ) );
  char * sVal = ( char * ) malloc( 15 * sizeof( char ) );
  int iRetorno = 0;
  sprintf( sVal, "%14s", " " );

  iRetorno = Bematech_FI_Cancelamentos( sVal );

  sprintf( sRet, "%-10d", iRetorno );
  strcat( sRet, sVal );
  return ( * env )->NewStringUTF( env, sRet );

}

/* * Class:     bibli_drivers_JBemaFI32 * Method:    bDescontos * Signature: ()Ljava/lang/String; */
JNIEXPORT jstring JNICALL Java_bibli_drivers_JBemaFI32_bDescontos( JNIEnv * env, jobject obj )
{
  char * sRet = ( char * ) malloc( 25 * sizeof( char ) );
  char * sVal = ( char * ) malloc( 15 * sizeof( char ) );
  int iRetorno = 0;
  sprintf( sVal, "%14s", " " );

  iRetorno = Bematech_FI_Descontos( sVal );

  sprintf( sRet, "%-10d", iRetorno );
  strcat( sRet, sVal );
  return ( * env )->NewStringUTF( env, sRet );
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bNumeroOperacoesNaoFiscais * Signature: (Ljava/lang/char*;)I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bNumeroOperacoesNaoFiscais( JNIEnv * env, jobject obj, jstring str1 )
{
  char * pStr1;
  int iRetorno;

  pStr1 = ( char * ) ( * env )->GetStringUTFChars( env, str1, 0 );

  iRetorno = Bematech_FI_NumeroOperacoesNaoFiscais( pStr1 );

  ( * env )->ReleaseStringChars( env, str1, ( jchar * ) pStr1 );

  return iRetorno;
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bNumeroCuponsCancelados * Signature: (Ljava/lang/char*;)I */
JNIEXPORT jstring JNICALL Java_bibli_drivers_JBemaFI32_bNumeroCuponsCancelados( JNIEnv * env, jobject obj )
{

  char * sRet = ( char * ) malloc( 15 * sizeof( char ) );
  char * sVal = ( char * ) malloc( 5 * sizeof( char ) );
  int iRetorno = 0;
  sprintf( sVal, "%4s", " " );

  iRetorno = Bematech_FI_NumeroCuponsCancelados( sVal );

  sprintf( sRet, "%-10d", iRetorno );
  strcat( sRet, sVal );
  return ( * env )->NewStringUTF( env, sRet );

}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bNumeroIntervencoes * Signature: (Ljava/lang/char*;)I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bNumeroIntervencoes( JNIEnv * env, jobject obj, jstring str1 )
{
  char * pStr1;
  int iRetorno;

  pStr1 = ( char * ) ( * env )->GetStringUTFChars( env, str1, 0 );

  iRetorno = Bematech_FI_NumeroIntervencoes( pStr1 );

  ( * env )->ReleaseStringChars( env, str1, ( jchar * ) pStr1 );

  return iRetorno;
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bNumeroReducoes * Signature: (Ljava/lang/char*;)I */
JNIEXPORT jstring JNICALL Java_bibli_drivers_JBemaFI32_bNumeroReducoes( JNIEnv * env, jobject obj )
{

  char * sRet = ( char * ) malloc( 15 * sizeof( char ) );
  char * sVal = ( char * ) malloc( 5 * sizeof( char ) );
  int iRetorno = 0;
  sprintf( sVal, "%4s", " " );

  iRetorno = Bematech_FI_NumeroReducoes( sVal );

  sprintf( sRet, "%-10d", iRetorno );
  strcat( sRet, sVal );
  return ( * env )->NewStringUTF( env, sRet );

}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bNumeroSubstituicoesProprietario * Signature: (Ljava/lang/char*;)I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bNumeroSubstituicoesProprietario( JNIEnv * env, jobject obj, jstring str1 )
{
  char * pStr1;
  int iRetorno;

  pStr1 = ( char * ) ( * env )->GetStringUTFChars( env, str1, 0 );

  iRetorno = Bematech_FI_NumeroSubstituicoesProprietario( pStr1 );

  ( * env )->ReleaseStringChars( env, str1, ( jchar * ) pStr1 );

  return iRetorno;
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bUltimoItemVendido * Signature: (Ljava/lang/char*;)I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bUltimoItemVendido( JNIEnv * env, jobject obj, jstring str1 )
{
  char * pStr1;
  int iRetorno;

  pStr1 = ( char * ) ( * env )->GetStringUTFChars( env, str1, 0 );

  iRetorno = Bematech_FI_UltimoItemVendido( pStr1 );

  ( * env )->ReleaseStringChars( env, str1, ( jchar * ) pStr1 );

  return iRetorno;
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bClicheProprietario * Signature: (Ljava/lang/char*;)I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bClicheProprietario( JNIEnv * env, jobject obj, jstring str1 )
{
  char * pStr1;
  int iRetorno;

  pStr1 = ( char * ) ( * env )->GetStringUTFChars( env, str1, 0 );

  iRetorno = Bematech_FI_ClicheProprietario( pStr1 );

  ( * env )->ReleaseStringChars( env, str1, ( jchar * ) pStr1 );

  return iRetorno;
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bNumeroCaixa * Signature: (Ljava/lang/char*;)I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bNumeroCaixa( JNIEnv * env, jobject obj, jstring str1 )
{
  char * pStr1;
  int iRetorno;

  pStr1 = ( char * ) ( * env )->GetStringUTFChars( env, str1, 0 );

  iRetorno = Bematech_FI_NumeroCaixa( pStr1 );

  ( * env )->ReleaseStringChars( env, str1, ( jchar * ) pStr1 );

  return iRetorno;
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bNumeroLoja * Signature: (Ljava/lang/char*;)I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bNumeroLoja( JNIEnv * env, jobject obj, jstring str1 )
{
  char * pStr1;
  int iRetorno;

  pStr1 = ( char * ) ( * env )->GetStringUTFChars( env, str1, 0 );

  iRetorno = Bematech_FI_NumeroLoja( pStr1 );

  ( * env )->ReleaseStringChars( env, str1, ( jchar * ) pStr1 );

  return iRetorno;
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bSimboloMoeda * Signature: (Ljava/lang/char*;)I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bSimboloMoeda( JNIEnv * env, jobject obj, jstring str1 )
{
  char * pStr1;
  int iRetorno;

  pStr1 = ( char * ) ( * env )->GetStringUTFChars( env, str1, 0 );

  iRetorno = Bematech_FI_SimboloMoeda( pStr1 );

  ( * env )->ReleaseStringChars( env, str1, ( jchar * ) pStr1 );

  return iRetorno;
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bMinutosLigada * Signature: (Ljava/lang/char*;)I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bMinutosLigada( JNIEnv * env, jobject obj, jstring str1 )
{
  char * pStr1;
  int iRetorno;

  pStr1 = ( char * ) ( * env )->GetStringUTFChars( env, str1, 0 );

  iRetorno = Bematech_FI_MinutosLigada( pStr1 );

  ( * env )->ReleaseStringChars( env, str1, ( jchar * ) pStr1 );

  return iRetorno;
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bMinutosImprimindo * Signature: (Ljava/lang/char*;)I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bMinutosImprimindo( JNIEnv * env, jobject obj, jstring str1 )
{
  char * pStr1;
  int iRetorno;

  pStr1 = ( char * ) ( * env )->GetStringUTFChars( env, str1, 0 );

  iRetorno = Bematech_FI_MinutosImprimindo( pStr1 );

  ( * env )->ReleaseStringChars( env, str1, ( jchar * ) pStr1 );

  return iRetorno;
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bVerificaModoOperacao * Signature: (Ljava/lang/char*;)I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bVerificaModoOperacao( JNIEnv * env, jobject obj, jstring str1 )
{
  char * pStr1;
  int iRetorno;

  pStr1 = ( char * ) ( * env )->GetStringUTFChars( env, str1, 0 );

  iRetorno = Bematech_FI_VerificaModoOperacao( pStr1 );

  ( * env )->ReleaseStringChars( env, str1, ( jchar * ) pStr1 );

  return iRetorno;
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bVerificaEpromConectada * Signature: (Ljava/lang/char*;)I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bVerificaEpromConectada( JNIEnv * env, jobject obj, jstring str1 )
{
  char * pStr1;
  int iRetorno;

  pStr1 = ( char * ) ( * env )->GetStringUTFChars( env, str1, 0 );

  iRetorno = Bematech_FI_VerificaEpromConectada( pStr1 );

  ( * env )->ReleaseStringChars( env, str1, ( jchar * ) pStr1 );

  return iRetorno;
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bFlagsFiscais * Signature: (I)I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bFlagsFiscais( JNIEnv * env, jobject obj, jint i1 )
{
  return Bematech_FI_FlagsFiscais( i1 );
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bValorPagoUltimoCupom * Signature: (Ljava/lang/char*;)I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bValorPagoUltimoCupom( JNIEnv * env, jobject obj, jstring str1 )
{
  char * pStr1;
  int iRetorno;

  pStr1 = ( char * ) ( * env )->GetStringUTFChars( env, str1, 0 );

  iRetorno = Bematech_FI_ValorPagoUltimoCupom( pStr1 );

  ( * env )->ReleaseStringChars( env, str1, ( jchar * ) pStr1 );

  return iRetorno;
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bDataHoraImpressora * Signature: (Ljava/lang/char*;Ljava/lang/char*;)I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bDataHoraImpressora
     ( JNIEnv * env, jobject obj, jstring str1, jstring str2 )
     {
       char * pStr1;
       char * pStr2;
       int iRetorno;

       pStr1 = ( char * ) ( * env )->GetStringUTFChars( env, str1, 0 );
       pStr2 = ( char * ) ( * env )->GetStringUTFChars( env, str2, 0 );

       iRetorno = Bematech_FI_DataHoraImpressora( pStr1, pStr2 );

       ( * env )->ReleaseStringChars( env, str1, ( jchar * ) pStr1 );
       ( * env )->ReleaseStringChars( env, str2, ( jchar * ) pStr2 );

       return iRetorno;
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bContadoresTotalizadoresNaoFiscais * Signature: (Ljava/lang/char*;)I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bContadoresTotalizadoresNaoFiscais
     ( JNIEnv * env, jobject obj, jstring str1 )
     {
       char * pStr1;
       int iRetorno;

       pStr1 = ( char * ) ( * env )->GetStringUTFChars( env, str1, 0 );

       iRetorno = Bematech_FI_ContadoresTotalizadoresNaoFiscais( pStr1 );

       ( * env )->ReleaseStringChars( env, str1, ( jchar * ) pStr1 );

       return iRetorno;
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bVerificaTotalizadoresNaoFiscais * Signature: (Ljava/lang/char*;)I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bVerificaTotalizadoresNaoFiscais( JNIEnv * env, jobject obj, jstring str1 )
{
  char * pStr1;
  int iRetorno;

  pStr1 = ( char * ) ( * env )->GetStringUTFChars( env, str1, 0 );

  iRetorno = Bematech_FI_VerificaTotalizadoresNaoFiscais( pStr1 );

  ( * env )->ReleaseStringChars( env, str1, ( jchar * ) pStr1 );

  return iRetorno;
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bDataHoraReducao * Signature: (Ljava/lang/char*;Ljava/lang/char*;)I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bDataHoraReducao( JNIEnv * env, jobject obj, jstring str1, jstring str2 )
{
  char * pStr1;
  char * pStr2;
  int iRetorno;

  pStr1 = ( char * ) ( * env )->GetStringUTFChars( env, str1, 0 );
  pStr2 = ( char * ) ( * env )->GetStringUTFChars( env, str2, 0 );

  iRetorno = Bematech_FI_DataHoraReducao( pStr1, pStr2 );

  ( * env )->ReleaseStringChars( env, str1, ( jchar * ) pStr1 );
  ( * env )->ReleaseStringChars( env, str2, ( jchar * ) pStr2 );

  return iRetorno;
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bDataMovimento * Signature: (Ljava/lang/char*;)I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bDataMovimento( JNIEnv * env, jobject obj, jstring str1 )
{
  char * pStr1;
  int iRetorno;

  pStr1 = ( char * ) ( * env )->GetStringUTFChars( env, str1, 0 );

  iRetorno = Bematech_FI_DataMovimento( pStr1 );

  ( * env )->ReleaseStringChars( env, str1, ( jchar * ) pStr1 );

  return iRetorno;
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bVerificaTruncamento * Signature: (Ljava/lang/char*;)I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bVerificaTruncamento( JNIEnv * env, jobject obj, jstring str1 )
{
  char * pStr1;
  int iRetorno;

  pStr1 = ( char * ) ( * env )->GetStringUTFChars( env, str1, 0 );

  iRetorno = Bematech_FI_VerificaTruncamento( pStr1 );

  ( * env )->ReleaseStringChars( env, str1, ( jchar * ) pStr1 );

  return iRetorno;
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bAcrescimos * Signature: (Ljava/lang/char*;)I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bAcrescimos( JNIEnv * env, jobject obj, jstring str1 )
{
  char * pStr1;
  int iRetorno;

  pStr1 = ( char * ) ( * env )->GetStringUTFChars( env, str1, 0 );

  iRetorno = Bematech_FI_Acrescimos( pStr1 );

  ( * env )->ReleaseStringChars( env, str1, ( jchar * ) pStr1 );

  return iRetorno;
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bContadorBilhetePassagem * Signature: (Ljava/lang/char*;)I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bContadorBilhetePassagem( JNIEnv * env, jobject obj, jstring str1 )
{
  char * pStr1;
  int iRetorno;

  pStr1 = ( char * ) ( * env )->GetStringUTFChars( env, str1, 0 );

  iRetorno = Bematech_FI_ContadorBilhetePassagem( pStr1 );

  ( * env )->ReleaseStringChars( env, str1, ( jchar * ) pStr1 );

  return iRetorno;
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bVerificaAliquotasIss * Signature: (Ljava/lang/char*;)I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bVerificaAliquotasIss( JNIEnv * env, jobject obj, jstring str1 )
{
  char * pStr1;
  int iRetorno;

  pStr1 = ( char * ) ( * env )->GetStringUTFChars( env, str1, 0 );

  iRetorno = Bematech_FI_VerificaAliquotasIss( pStr1 );

  ( * env )->ReleaseStringChars( env, str1, ( jchar * ) pStr1 );

  return iRetorno;
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bVerificaFormasPagamento * Signature: (Ljava/lang/char*;)I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bVerificaFormasPagamento( JNIEnv * env, jobject obj, jstring str1 )
{
  char * pStr1;
  int iRetorno;

  pStr1 = ( char * ) ( * env )->GetStringUTFChars( env, str1, 0 );

  iRetorno = Bematech_FI_VerificaFormasPagamento( pStr1 );

  ( * env )->ReleaseStringChars( env, str1, ( jchar * ) pStr1 );

  return iRetorno;
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bVerificaRecebimentoNaoFiscal * Signature: (Ljava/lang/char*;)I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bVerificaRecebimentoNaoFiscal( JNIEnv * env, jobject obj, jstring str1 )
{
  char * pStr1;
  int iRetorno;

  pStr1 = ( char * ) ( * env )->GetStringUTFChars( env, str1, 0 );

  iRetorno = Bematech_FI_VerificaRecebimentoNaoFiscal( pStr1 );

  ( * env )->ReleaseStringChars( env, str1, ( jchar * ) pStr1 );

  return iRetorno;
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bVerificaDepartamentos * Signature: (Ljava/lang/char*;)I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bVerificaDepartamentos( JNIEnv * env, jobject obj, jstring str1 )
{
  char * pStr1;
  int iRetorno;

  pStr1 = ( char * ) ( * env )->GetStringUTFChars( env, str1, 0 );

  iRetorno = Bematech_FI_VerificaDepartamentos( pStr1 );

  ( * env )->ReleaseStringChars( env, str1, ( jchar * ) pStr1 );

  return iRetorno;
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bVerificaTipoImpressora * Signature: (I)I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bVerificaTipoImpressora( JNIEnv * env, jobject obj, jint i1 )
{
  return Bematech_FI_VerificaTipoImpressora( i1 );
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bVerificaTotalizadoresParciais * Signature: (Ljava/lang/char*;)I */
JNIEXPORT jstring JNICALL Java_bibli_drivers_JBemaFI32_bVerificaTotalizadoresParciais( JNIEnv * env, jobject obj )
{
  char * sRet = ( char * ) malloc( 456 * sizeof( char ) );
  char * sVal = ( char * ) malloc( 446 * sizeof( char ) );
  int iRetorno = 0;
  sprintf( sVal, "%445s", " " );

  iRetorno = Bematech_FI_VerificaTotalizadoresParciais( sVal );
  sprintf( sRet, "%-10d", iRetorno );
  strcat( sRet, sVal );
  return ( * env )->NewStringUTF( env, sRet );
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bRetornoAliquotas * Signature: ()I */
JNIEXPORT jstring JNICALL Java_bibli_drivers_JBemaFI32_bRetornoAliquotas( JNIEnv * env, jobject obj )
{
  char * sRet = ( char * ) malloc( 91 * sizeof( char ) );
  char * sVal = ( char * ) malloc( 80 * sizeof( char ) );
  int iRetorno = 0;
  sprintf( sVal, "%79s", " " );
  iRetorno = Bematech_FI_RetornoAliquotas( sVal );
  sprintf( sRet, "%-10d", iRetorno );
  strcat( sRet, sVal );
  return ( * env )->NewStringUTF( env, sRet );
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bVerificaEstadoImpressora * Signature: (III)I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bVerificaEstadoImpressora
     ( JNIEnv * env, jobject obj, jint i1, jint i2, jint i3 )
     {
       return Bematech_FI_VerificaEstadoImpressora( i1, i2, i3 );
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bDadosUltimaReducao * Signature: (Ljava/lang/char*;)I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bDadosUltimaReducao( JNIEnv * env, jobject obj, jstring str1 )
{
  char * pStr1;
  int iRetorno;

  pStr1 = ( char * ) ( * env )->GetStringUTFChars( env, str1, 0 );

  iRetorno = Bematech_FI_DadosUltimaReducao( pStr1 );

  ( * env )->ReleaseStringChars( env, str1, ( jchar * ) pStr1 );

  return iRetorno;
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bMonitoramentoPapel * Signature: (I)I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bMonitoramentoPapel( JNIEnv * env, jobject obj, jint str1 )
{
  //   char* pStr1;
  int iRetorno;

  /*pStr1 = (char *)( *env )->GetStringUTFChars( env, str1, 0 );

  iRetorno = Bematech_FI_MonitoramentoPapel(pStr1);

  ( *env )->ReleaseStringChars( env, str1, (jchar *)pStr1);*/

  return iRetorno;
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bVerificaIndiceAliquotasIss * Signature: (Ljava/lang/char*;)I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bVerificaIndiceAliquotasIss( JNIEnv * env, jobject obj, jstring str1 )
{
  char * pStr1;
  int iRetorno;

  pStr1 = ( char * ) ( * env )->GetStringUTFChars( env, str1, 0 );

  iRetorno = Bematech_FI_VerificaIndiceAliquotasIss( pStr1 );

  ( * env )->ReleaseStringChars( env, str1, ( jchar * ) pStr1 );

  return iRetorno;
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bValorFormaPagamento * Signature: (Ljava/lang/char*;Ljava/lang/char*;)I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bValorFormaPagamento
     ( JNIEnv * env, jobject obj, jstring str1, jstring str2 )
     {
       char * pStr1;
       char * pStr2;
       int iRetorno;

       pStr1 = ( char * ) ( * env )->GetStringUTFChars( env, str1, 0 );
       pStr2 = ( char * ) ( * env )->GetStringUTFChars( env, str2, 0 );

       iRetorno = Bematech_FI_ValorFormaPagamento( pStr1, pStr2 );

       ( * env )->ReleaseStringChars( env, str1, ( jchar * ) pStr1 );
       ( * env )->ReleaseStringChars( env, str2, ( jchar * ) pStr2 );

       return iRetorno;
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bValorTotalizadorNaoFiscal
* Signature: (Ljava/lang/char*;Ljava/lang/char*;)I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bValorTotalizadorNaoFiscal
     ( JNIEnv * env, jobject obj, jstring str1, jstring str2 )
     {
       char * pStr1;
       char * pStr2;
       int iRetorno;

       pStr1 = ( char * ) ( * env )->GetStringUTFChars( env, str1, 0 );
       pStr2 = ( char * ) ( * env )->GetStringUTFChars( env, str2, 0 );

       iRetorno = Bematech_FI_ValorTotalizadorNaoFiscal( pStr1, pStr2 );

       ( * env )->ReleaseStringChars( env, str1, ( jchar * ) pStr1 );
       ( * env )->ReleaseStringChars( env, str2, ( jchar * ) pStr2 );

       return iRetorno;
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bAutenticacao * Signature: ()I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bAutenticacao( JNIEnv * env, jobject obj )
{
  return Bematech_FI_Autenticacao();
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bProgramaCaracterAutenticacao * Signature: (Ljava/lang/char*;)I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bProgramaCaracterAutenticacao( JNIEnv * env, jobject obj, jstring str1 )
{
  char * pStr1;
  int iRetorno;

  pStr1 = ( char * ) ( * env )->GetStringUTFChars( env, str1, 0 );

  iRetorno = Bematech_FI_ProgramaCaracterAutenticacao( pStr1 );

  ( * env )->ReleaseStringChars( env, str1, ( jchar * ) pStr1 );

  return iRetorno;
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bAcionaGaveta * Signature: ()I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bAcionaGaveta( JNIEnv * env, jobject obj )
{
  return Bematech_FI_AcionaGaveta();
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bVerificaEstadoGaveta * Signature: (I)I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bVerificaEstadoGaveta( JNIEnv * env, jobject obj, jint i1 )
{
  return Bematech_FI_VerificaEstadoGaveta( i1 );
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bRAbreCupomRestaurante
* Signature: (Ljava/lang/char*;Ljava/lang/char*;)I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bRAbreCupomRestaurante
     ( JNIEnv * env, jobject obj, jstring str1, jstring str2 )
     {
       char * pStr1;
       char * pStr2;
       int iRetorno;

       pStr1 = ( char * ) ( * env )->GetStringUTFChars( env, str1, 0 );
       pStr2 = ( char * ) ( * env )->GetStringUTFChars( env, str2, 0 );

       iRetorno = Bematech_FIR_AbreCupomRestaurante( pStr1, pStr2 );

       ( * env )->ReleaseStringChars( env, str1, ( jchar * ) pStr1 );
       ( * env )->ReleaseStringChars( env, str2, ( jchar * ) pStr2 );

       return iRetorno;
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bRRegistraVenda
* Signature: (Ljava/lang/char*;Ljava/lang/char*;Ljava/lang/char*;Ljava/lang/char*;Ljava/lang/char*;Ljava/lang/char*;Ljava/lang/char*;Ljava/lang/char*;)I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bRRegistraVenda
     ( JNIEnv * env, jobject obj, jstring str1, jstring str2, jstring str3, jstring str4, jstring str5, jstring str6,
     jstring str7, jstring str8 )
     {
       char * pStr1;
       char * pStr2;
       char * pStr3;
       char * pStr4;
       char * pStr5;
       char * pStr6;
       char * pStr7;
       char * pStr8;
       int iRetorno;

       pStr1 = ( char * ) ( * env )->GetStringUTFChars( env, str1, 0 );
       pStr2 = ( char * ) ( * env )->GetStringUTFChars( env, str2, 0 );
       pStr3 = ( char * ) ( * env )->GetStringUTFChars( env, str3, 0 );
       pStr4 = ( char * ) ( * env )->GetStringUTFChars( env, str4, 0 );
       pStr5 = ( char * ) ( * env )->GetStringUTFChars( env, str5, 0 );
       pStr6 = ( char * ) ( * env )->GetStringUTFChars( env, str6, 0 );
       pStr7 = ( char * ) ( * env )->GetStringUTFChars( env, str7, 0 );
       pStr8 = ( char * ) ( * env )->GetStringUTFChars( env, str8, 0 );

       iRetorno = Bematech_FIR_RegistraVenda( pStr1, pStr2, pStr3, pStr4, pStr5, pStr6, pStr7, pStr8 );

       ( * env )->ReleaseStringChars( env, str1, ( jchar * ) pStr1 );
       ( * env )->ReleaseStringChars( env, str2, ( jchar * ) pStr2 );
       ( * env )->ReleaseStringChars( env, str3, ( jchar * ) pStr3 );
       ( * env )->ReleaseStringChars( env, str4, ( jchar * ) pStr4 );
       ( * env )->ReleaseStringChars( env, str5, ( jchar * ) pStr5 );
       ( * env )->ReleaseStringChars( env, str6, ( jchar * ) pStr6 );
       ( * env )->ReleaseStringChars( env, str7, ( jchar * ) pStr7 );
       ( * env )->ReleaseStringChars( env, str8, ( jchar * ) pStr8 );

       return iRetorno;
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bRCancelaVenda
* Signature: (Ljava/lang/char*;Ljava/lang/char*;Ljava/lang/char*;Ljava/lang/char*;Ljava/lang/char*;Ljava/lang/char*;Ljava/lang/char*;Ljava/lang/char*;)I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bRCancelaVenda
     ( JNIEnv * env, jobject obj, jstring str1, jstring str2, jstring str3, jstring str4, jstring str5, jstring str6,
     jstring str7, jstring str8 )
     {
       char * pStr1;
       char * pStr2;
       char * pStr3;
       char * pStr4;
       char * pStr5;
       char * pStr6;
       char * pStr7;
       char * pStr8;
       int iRetorno;

       pStr1 = ( char * ) ( * env )->GetStringUTFChars( env, str1, 0 );
       pStr2 = ( char * ) ( * env )->GetStringUTFChars( env, str2, 0 );
       pStr3 = ( char * ) ( * env )->GetStringUTFChars( env, str3, 0 );
       pStr4 = ( char * ) ( * env )->GetStringUTFChars( env, str4, 0 );
       pStr5 = ( char * ) ( * env )->GetStringUTFChars( env, str5, 0 );
       pStr6 = ( char * ) ( * env )->GetStringUTFChars( env, str6, 0 );
       pStr7 = ( char * ) ( * env )->GetStringUTFChars( env, str7, 0 );
       pStr8 = ( char * ) ( * env )->GetStringUTFChars( env, str8, 0 );

       iRetorno = Bematech_FIR_CancelaVenda( pStr1, pStr2, pStr3, pStr4, pStr5, pStr6, pStr7, pStr8 );

       ( * env )->ReleaseStringChars( env, str1, ( jchar * ) pStr1 );
       ( * env )->ReleaseStringChars( env, str2, ( jchar * ) pStr2 );
       ( * env )->ReleaseStringChars( env, str3, ( jchar * ) pStr3 );
       ( * env )->ReleaseStringChars( env, str4, ( jchar * ) pStr4 );
       ( * env )->ReleaseStringChars( env, str5, ( jchar * ) pStr5 );
       ( * env )->ReleaseStringChars( env, str6, ( jchar * ) pStr6 );
       ( * env )->ReleaseStringChars( env, str7, ( jchar * ) pStr7 );
       ( * env )->ReleaseStringChars( env, str8, ( jchar * ) pStr8 );

       return iRetorno;
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bRConferenciaMesa
* Signature: (Ljava/lang/char*;Ljava/lang/char*;Ljava/lang/char*;Ljava/lang/char*;)I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bRConferenciaMesa
     ( JNIEnv * env, jobject obj, jstring str1, jstring str2, jstring str3, jstring str4 )
     {
       char * pStr1;
       char * pStr2;
       char * pStr3;
       char * pStr4;
       int iRetorno;

       pStr1 = ( char * ) ( * env )->GetStringUTFChars( env, str1, 0 );
       pStr2 = ( char * ) ( * env )->GetStringUTFChars( env, str2, 0 );
       pStr3 = ( char * ) ( * env )->GetStringUTFChars( env, str3, 0 );
       pStr4 = ( char * ) ( * env )->GetStringUTFChars( env, str4, 0 );

       iRetorno = Bematech_FIR_ConferenciaMesa( pStr1, pStr2, pStr3, pStr4 );

       ( * env )->ReleaseStringChars( env, str1, ( jchar * ) pStr1 );
       ( * env )->ReleaseStringChars( env, str2, ( jchar * ) pStr2 );
       ( * env )->ReleaseStringChars( env, str3, ( jchar * ) pStr3 );
       ( * env )->ReleaseStringChars( env, str4, ( jchar * ) pStr4 );

       return iRetorno;
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bRAbreConferenciaMesa * Signature: (Ljava/lang/char*;)I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bRAbreConferenciaMesa( JNIEnv * env, jobject obj, jstring str1 )
{
  char * pStr1;
  int iRetorno;

  pStr1 = ( char * ) ( * env )->GetStringUTFChars( env, str1, 0 );

  iRetorno = Bematech_FIR_AbreConferenciaMesa( pStr1 );

  ( * env )->ReleaseStringChars( env, str1, ( jchar * ) pStr1 );

  return iRetorno;
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bRFechaConferenciaMesa
* Signature: (Ljava/lang/char*;Ljava/lang/char*;Ljava/lang/char*;)I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bRFechaConferenciaMesa
     ( JNIEnv * env, jobject obj, jstring str1, jstring str2, jstring str3 )
     {
       char * pStr1;
       char * pStr2;
       char * pStr3;
       int iRetorno;

       pStr1 = ( char * ) ( * env )->GetStringUTFChars( env, str1, 0 );
       pStr2 = ( char * ) ( * env )->GetStringUTFChars( env, str2, 0 );
       pStr3 = ( char * ) ( * env )->GetStringUTFChars( env, str3, 0 );

       iRetorno = Bematech_FIR_FechaConferenciaMesa( pStr1, pStr2, pStr3 );

       ( * env )->ReleaseStringChars( env, str1, ( jchar * ) pStr1 );
       ( * env )->ReleaseStringChars( env, str2, ( jchar * ) pStr2 );
       ( * env )->ReleaseStringChars( env, str3, ( jchar * ) pStr3 );

       return iRetorno;
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bRTransferenciaMesa * Signature: (Ljava/lang/char*;Ljava/lang/char*;)I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bRTransferenciaMesa
     ( JNIEnv * env, jobject obj, jstring str1, jstring str2 )
     {
       char * pStr1;
       char * pStr2;
       int iRetorno;

       pStr1 = ( char * ) ( * env )->GetStringUTFChars( env, str1, 0 );
       pStr2 = ( char * ) ( * env )->GetStringUTFChars( env, str2, 0 );

       iRetorno = Bematech_FIR_TransferenciaMesa( pStr1, pStr2 );

       ( * env )->ReleaseStringChars( env, str1, ( jchar * ) pStr1 );
       ( * env )->ReleaseStringChars( env, str2, ( jchar * ) pStr2 );

       return iRetorno;
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bRContaDividida
* Signature: (Ljava/lang/char*;Ljava/lang/char*;Ljava/lang/char*;)I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bRContaDividida
     ( JNIEnv * env, jobject obj, jstring str1, jstring str2, jstring str3 )
     {
       char * pStr1;
       char * pStr2;
       char * pStr3;
       int iRetorno;

       pStr1 = ( char * ) ( * env )->GetStringUTFChars( env, str1, 0 );
       pStr2 = ( char * ) ( * env )->GetStringUTFChars( env, str2, 0 );
       pStr3 = ( char * ) ( * env )->GetStringUTFChars( env, str3, 0 );

       iRetorno = Bematech_FIR_ContaDividida( pStr1, pStr2, pStr3 );

       ( * env )->ReleaseStringChars( env, str1, ( jchar * ) pStr1 );
       ( * env )->ReleaseStringChars( env, str2, ( jchar * ) pStr2 );
       ( * env )->ReleaseStringChars( env, str3, ( jchar * ) pStr3 );

       return iRetorno;
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bRFechaCupomContaDividida
* Signature: (Ljava/lang/char*;Ljava/lang/char*;Ljava/lang/char*;Ljava/lang/char*;Ljava/lang/char*;Ljava/lang/char*;Ljava/lang/char*;Ljava/lang/char*;)I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bRFechaCupomContaDividida
     ( JNIEnv * env, jobject obj, jstring str1, jstring str2, jstring str3, jstring str4, jstring str5, jstring str6,
     jstring str7, jstring str8 )
     {
       char * pStr1;
       char * pStr2;
       char * pStr3;
       char * pStr4;
       char * pStr5;
       char * pStr6;
       char * pStr7;
       char * pStr8;
       int iRetorno;

       pStr1 = ( char * ) ( * env )->GetStringUTFChars( env, str1, 0 );
       pStr2 = ( char * ) ( * env )->GetStringUTFChars( env, str2, 0 );
       pStr3 = ( char * ) ( * env )->GetStringUTFChars( env, str3, 0 );
       pStr4 = ( char * ) ( * env )->GetStringUTFChars( env, str4, 0 );
       pStr5 = ( char * ) ( * env )->GetStringUTFChars( env, str5, 0 );
       pStr6 = ( char * ) ( * env )->GetStringUTFChars( env, str6, 0 );
       pStr7 = ( char * ) ( * env )->GetStringUTFChars( env, str7, 0 );
       pStr8 = ( char * ) ( * env )->GetStringUTFChars( env, str8, 0 );

       iRetorno = Bematech_FIR_FechaCupomContaDividida( pStr1, pStr2, pStr3, pStr4, pStr5, pStr6, pStr7, pStr8 );

       ( * env )->ReleaseStringChars( env, str1, ( jchar * ) pStr1 );
       ( * env )->ReleaseStringChars( env, str2, ( jchar * ) pStr2 );
       ( * env )->ReleaseStringChars( env, str3, ( jchar * ) pStr3 );
       ( * env )->ReleaseStringChars( env, str4, ( jchar * ) pStr4 );
       ( * env )->ReleaseStringChars( env, str5, ( jchar * ) pStr5 );
       ( * env )->ReleaseStringChars( env, str6, ( jchar * ) pStr6 );
       ( * env )->ReleaseStringChars( env, str7, ( jchar * ) pStr7 );
       ( * env )->ReleaseStringChars( env, str8, ( jchar * ) pStr8 );

       return iRetorno;
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bRTransferenciaItem
* Signature: (Ljava/lang/char*;Ljava/lang/char*;Ljava/lang/char*;Ljava/lang/char*;Ljava/lang/char*;Ljava/lang/char*;Ljava/lang/char*;Ljava/lang/char*;Ljava/lang/char*;)I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bRTransferenciaItem
     ( JNIEnv * env, jobject obj, jstring str1, jstring str2, jstring str3, jstring str4, jstring str5, jstring str6,
     jstring str7, jstring str8, jstring str9 )
     {
       char * pStr1;
       char * pStr2;
       char * pStr3;
       char * pStr4;
       char * pStr5;
       char * pStr6;
       char * pStr7;
       char * pStr8;
       char * pStr9;
       int iRetorno;

       pStr1 = ( char * ) ( * env )->GetStringUTFChars( env, str1, 0 );
       pStr2 = ( char * ) ( * env )->GetStringUTFChars( env, str2, 0 );
       pStr3 = ( char * ) ( * env )->GetStringUTFChars( env, str3, 0 );
       pStr4 = ( char * ) ( * env )->GetStringUTFChars( env, str4, 0 );
       pStr5 = ( char * ) ( * env )->GetStringUTFChars( env, str5, 0 );
       pStr6 = ( char * ) ( * env )->GetStringUTFChars( env, str6, 0 );
       pStr7 = ( char * ) ( * env )->GetStringUTFChars( env, str7, 0 );
       pStr8 = ( char * ) ( * env )->GetStringUTFChars( env, str8, 0 );
       pStr9 = ( char * ) ( * env )->GetStringUTFChars( env, str9, 0 );

       iRetorno = Bematech_FIR_TransferenciaItem( pStr1, pStr2, pStr3, pStr4, pStr5, pStr6, pStr7, pStr8, pStr9 );

       ( * env )->ReleaseStringChars( env, str1, ( jchar * ) pStr1 );
       ( * env )->ReleaseStringChars( env, str2, ( jchar * ) pStr2 );
       ( * env )->ReleaseStringChars( env, str3, ( jchar * ) pStr3 );
       ( * env )->ReleaseStringChars( env, str4, ( jchar * ) pStr4 );
       ( * env )->ReleaseStringChars( env, str5, ( jchar * ) pStr5 );
       ( * env )->ReleaseStringChars( env, str6, ( jchar * ) pStr6 );
       ( * env )->ReleaseStringChars( env, str7, ( jchar * ) pStr7 );
       ( * env )->ReleaseStringChars( env, str8, ( jchar * ) pStr8 );
       ( * env )->ReleaseStringChars( env, str9, ( jchar * ) pStr9 );

       return iRetorno;
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bRRelatorioMesasAbertas * Signature: (I)I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bRRelatorioMesasAbertas( JNIEnv * env, jobject obj, jint i1 )
{
  return Bematech_FIR_RelatorioMesasAbertas( i1 );
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bRImprimeCardapio * Signature: ()I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bRImprimeCardapio( JNIEnv * env, jobject obj )
{
  return Bematech_FIR_ImprimeCardapio();
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bRRelatorioMesasAbertasSerial * Signature: ()I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bRRelatorioMesasAbertasSerial( JNIEnv * env, jobject obj )
{
  return Bematech_FIR_RelatorioMesasAbertasSerial();
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bRCardapioPelaSerial * Signature: ()I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bRCardapioPelaSerial( JNIEnv * env, jobject obj )
{
  return Bematech_FIR_CardapioPelaSerial();
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bRRegistroVendaSerial * Signature: (Ljava/lang/char*;)I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bRRegistroVendaSerial( JNIEnv * env, jobject obj, jstring str1 )
{
  char * pStr1;
  int iRetorno;

  pStr1 = ( char * ) ( * env )->GetStringUTFChars( env, str1, 0 );

  iRetorno = Bematech_FIR_RegistroVendaSerial( pStr1 );


  ( * env )->ReleaseStringChars( env, str1, ( jchar * ) pStr1 );

  return iRetorno;
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bRVerificaMemoriaLivre * Signature: (Ljava/lang/char*;)I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bRVerificaMemoriaLivre( JNIEnv * env, jobject obj, jstring str1 )
{
  char * pStr1;
  int iRetorno;

  pStr1 = ( char * ) ( * env )->GetStringUTFChars( env, str1, 0 );

  iRetorno = Bematech_FIR_VerificaMemoriaLivre( pStr1 );


  ( * env )->ReleaseStringChars( env, str1, ( jchar * ) pStr1 );

  return iRetorno;
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bRFechaCupomRestaurante
* Signature: (Ljava/lang/char*;Ljava/lang/char*;Ljava/lang/char*;Ljava/lang/char*;Ljava/lang/char*;Ljava/lang/char*;)I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bRFechaCupomRestaurante
     ( JNIEnv * env, jobject obj, jstring str1, jstring str2, jstring str3, jstring str4, jstring str5, jstring str6 )
     {
       char * pStr1;
       char * pStr2;
       char * pStr3;
       char * pStr4;
       char * pStr5;
       char * pStr6;
       int iRetorno;

       pStr1 = ( char * ) ( * env )->GetStringUTFChars( env, str1, 0 );
       pStr2 = ( char * ) ( * env )->GetStringUTFChars( env, str2, 0 );
       pStr3 = ( char * ) ( * env )->GetStringUTFChars( env, str3, 0 );
       pStr4 = ( char * ) ( * env )->GetStringUTFChars( env, str4, 0 );
       pStr5 = ( char * ) ( * env )->GetStringUTFChars( env, str5, 0 );
       pStr6 = ( char * ) ( * env )->GetStringUTFChars( env, str6, 0 );

       iRetorno = Bematech_FIR_FechaCupomRestaurante( pStr1, pStr2, pStr3, pStr4, pStr5, pStr6 );

       ( * env )->ReleaseStringChars( env, str1, ( jchar * ) pStr1 );
       ( * env )->ReleaseStringChars( env, str2, ( jchar * ) pStr2 );
       ( * env )->ReleaseStringChars( env, str3, ( jchar * ) pStr3 );
       ( * env )->ReleaseStringChars( env, str4, ( jchar * ) pStr4 );
       ( * env )->ReleaseStringChars( env, str5, ( jchar * ) pStr5 );
       ( * env )->ReleaseStringChars( env, str6, ( jchar * ) pStr6 );

       return iRetorno;
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bRFechaCupomResumidoRestaurante
* Signature: (Ljava/lang/char*;Ljava/lang/char*;)I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bRFechaCupomResumidoRestaurante
     ( JNIEnv * env, jobject obj, jstring str1, jstring str2 )
     {
       char * pStr1;
       char * pStr2;
       int iRetorno;

       pStr1 = ( char * ) ( * env )->GetStringUTFChars( env, str1, 0 );
       pStr2 = ( char * ) ( * env )->GetStringUTFChars( env, str2, 0 );

       iRetorno = Bematech_FIR_FechaCupomResumidoRestaurante( pStr1, pStr2 );


       ( * env )->ReleaseStringChars( env, str1, ( jchar * ) pStr1 );
       ( * env )->ReleaseStringChars( env, str2, ( jchar * ) pStr2 );

       return iRetorno;
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bAbreBilhetePassagem
* Signature: (Ljava/lang/char*;Ljava/lang/char*;Ljava/lang/char*;Ljava/lang/char*;Ljava/lang/char*;Ljava/lang/char*;Ljava/lang/char*;Ljava/lang/char*;Ljava/lang/char*;Ljava/lang/char*;Ljava/lang/char*;Ljava/lang/char*;)I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bAbreBilhetePassagem
     ( JNIEnv * env, jobject obj, jstring str1, jstring str2, jstring str3, jstring str4, jstring str5, jstring str6,
     jstring str7, jstring str8, jstring str9, jstring str10, jstring str11, jstring str12 )
     {
       char * pStr1;
       char * pStr2;
       char * pStr3;
       char * pStr4;
       char * pStr5;
       char * pStr6;
       char * pStr7;
       char * pStr8;
       char * pStr9;
       char * pStr10;
       char * pStr11;
       char * pStr12;
       int iRetorno;

       pStr1 = ( char * ) ( * env )->GetStringUTFChars( env, str1, 0 );
       pStr2 = ( char * ) ( * env )->GetStringUTFChars( env, str2, 0 );
       pStr3 = ( char * ) ( * env )->GetStringUTFChars( env, str3, 0 );
       pStr4 = ( char * ) ( * env )->GetStringUTFChars( env, str4, 0 );
       pStr5 = ( char * ) ( * env )->GetStringUTFChars( env, str5, 0 );
       pStr6 = ( char * ) ( * env )->GetStringUTFChars( env, str6, 0 );
       pStr7 = ( char * ) ( * env )->GetStringUTFChars( env, str7, 0 );
       pStr8 = ( char * ) ( * env )->GetStringUTFChars( env, str8, 0 );
       pStr9 = ( char * ) ( * env )->GetStringUTFChars( env, str9, 0 );
       pStr10 = ( char * ) ( * env )->GetStringUTFChars( env, str10, 0 );
       pStr11 = ( char * ) ( * env )->GetStringUTFChars( env, str11, 0 );
       pStr12 = ( char * ) ( * env )->GetStringUTFChars( env, str12, 0 );

       iRetorno = Bematech_FI_AbreBilhetePassagem( pStr1, pStr2, pStr3, pStr4, pStr5, pStr6, pStr7, pStr8, pStr9,
            pStr10, pStr11, pStr12 );

       ( * env )->ReleaseStringChars( env, str1, ( jchar * ) pStr1 );
       ( * env )->ReleaseStringChars( env, str2, ( jchar * ) pStr2 );
       ( * env )->ReleaseStringChars( env, str3, ( jchar * ) pStr3 );
       ( * env )->ReleaseStringChars( env, str4, ( jchar * ) pStr4 );
       ( * env )->ReleaseStringChars( env, str5, ( jchar * ) pStr5 );
       ( * env )->ReleaseStringChars( env, str6, ( jchar * ) pStr6 );
       ( * env )->ReleaseStringChars( env, str7, ( jchar * ) pStr7 );
       ( * env )->ReleaseStringChars( env, str8, ( jchar * ) pStr8 );
       ( * env )->ReleaseStringChars( env, str9, ( jchar * ) pStr9 );
       ( * env )->ReleaseStringChars( env, str10, ( jchar * ) pStr10 );
       ( * env )->ReleaseStringChars( env, str11, ( jchar * ) pStr11 );
       ( * env )->ReleaseStringChars( env, str12, ( jchar * ) pStr12 );

       return iRetorno;
}

/* * Class:     bibli_drivers_JBemaFI32 * Method:    bProgramaMoedaSingular * Signature: (Ljava/lang/char*;)I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bProgramaMoedaSingular( JNIEnv * env, jobject obj, jstring str1 )
{
  char * pStr1;
  int iRetorno;

  pStr1 = ( char * ) ( * env )->GetStringUTFChars( env, str1, 0 );

  iRetorno = Bematech_FI_ProgramaMoedaSingular( pStr1 );


  ( * env )->ReleaseStringChars( env, str1, ( jchar * ) pStr1 );

  return iRetorno;

}

/* * Class:     bibli_drivers_JBemaFI32 * Method:    bProgramaMoedaPlural * Signature: (Ljava/lang/char*;)I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bProgramaMoedaPlural( JNIEnv * env, jobject obj, jstring str1 )
{
  char * pStr1;
  int iRetorno;

  pStr1 = ( char * ) ( * env )->GetStringUTFChars( env, str1, 0 );

  iRetorno = Bematech_FI_ProgramaMoedaPlural( pStr1 );


  ( * env )->ReleaseStringChars( env, str1, ( jchar * ) pStr1 );

  return iRetorno;
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bCancelaImpressaoCheque * Signature: ()I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bCancelaImpressaoCheque( JNIEnv * env, jobject obj )
{
  return Bematech_FI_CancelaImpressaoCheque();
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bVerificaStatusCheque * Signature: (I)I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bVerificaStatusCheque( JNIEnv * env, jobject obj, jint i1 )
{
  return Bematech_FI_VerificaStatusCheque( i1 );
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bImprimeCheque
* Signature: (Ljava/lang/char*;Ljava/lang/char*;Ljava/lang/char*;Ljava/lang/char*;Ljava/lang/char*;Ljava/lang/char*;)I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bImprimeCheque
     ( JNIEnv * env, jobject obj, jstring str1, jstring str2, jstring str3, jstring str4, jstring str5, jstring str6 )
     {
       char * pStr1;
       char * pStr2;
       char * pStr3;
       char * pStr4;
       char * pStr5;
       char * pStr6;
       int iRetorno;

       pStr1 = ( char * ) ( * env )->GetStringUTFChars( env, str1, 0 );
       pStr2 = ( char * ) ( * env )->GetStringUTFChars( env, str2, 0 );
       pStr3 = ( char * ) ( * env )->GetStringUTFChars( env, str3, 0 );
       pStr4 = ( char * ) ( * env )->GetStringUTFChars( env, str4, 0 );
       pStr5 = ( char * ) ( * env )->GetStringUTFChars( env, str5, 0 );
       pStr6 = ( char * ) ( * env )->GetStringUTFChars( env, str6, 0 );

       iRetorno = Bematech_FI_ImprimeCheque( pStr1, pStr2, pStr3, pStr4, pStr5, pStr6 );

       ( * env )->ReleaseStringChars( env, str1, ( jchar * ) pStr1 );
       ( * env )->ReleaseStringChars( env, str2, ( jchar * ) pStr2 );
       ( * env )->ReleaseStringChars( env, str3, ( jchar * ) pStr3 );
       ( * env )->ReleaseStringChars( env, str4, ( jchar * ) pStr4 );
       ( * env )->ReleaseStringChars( env, str5, ( jchar * ) pStr5 );
       ( * env )->ReleaseStringChars( env, str6, ( jchar * ) pStr6 );

       return iRetorno;
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bIncluiCidadeFavorecido
* Signature: (Ljava/lang/char*;Ljava/lang/char*;)I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bIncluiCidadeFavorecido
     ( JNIEnv * env, jobject obj, jstring str1, jstring str2 )
     {
       char * pStr1;
       char * pStr2;
       int iRetorno;

       pStr1 = ( char * ) ( * env )->GetStringUTFChars( env, str1, 0 );
       pStr2 = ( char * ) ( * env )->GetStringUTFChars( env, str2, 0 );

       iRetorno = Bematech_FI_IncluiCidadeFavorecido( pStr1, pStr2 );

       ( * env )->ReleaseStringChars( env, str1, ( jchar * ) pStr1 );
       ( * env )->ReleaseStringChars( env, str2, ( jchar * ) pStr2 );

       return iRetorno;
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bImprimeCopiaCheque * Signature: ()I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bImprimeCopiaCheque( JNIEnv * env, jobject obj )
{
  return Bematech_FI_ImprimeCopiaCheque();
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bTEFStatus * Signature: (Ljava/lang/char*;)I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bTEFStatus( JNIEnv * env, jobject obj, jstring str1 )
{
  char * pStr1;
  int iRetorno;

  pStr1 = ( char * ) ( * env )->GetStringUTFChars( env, str1, 0 );

  //   iRetorno = Bematech_FITEF_Status(pStr1);

  ( * env )->ReleaseStringChars( env, str1, ( jchar * ) pStr1 );

  return iRetorno;
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bTEFVendaCartao * Signature: (Ljava/lang/char*;Ljava/lang/char*;)I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bTEFVendaCartao( JNIEnv * env, jobject obj, jstring str1, jstring str2 )
{
  char * pStr1;
  char * pStr2;
  int iRetorno;

  pStr1 = ( char * ) ( * env )->GetStringUTFChars( env, str1, 0 );
  pStr2 = ( char * ) ( * env )->GetStringUTFChars( env, str2, 0 );

  //   iRetorno = Bematech_FITEF_VendaCartao(pStr1, pStr2);

  ( * env )->ReleaseStringChars( env, str1, ( jchar * ) pStr1 );
  ( * env )->ReleaseStringChars( env, str2, ( jchar * ) pStr2 );

  return iRetorno;
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bTEFConfirmaVenda
* Signature: (Ljava/lang/char*;Ljava/lang/char*;Ljava/lang/char*;)I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bTEFConfirmaVenda
     ( JNIEnv * env, jobject obj, jstring str1, jstring str2, jstring str3 )
     {
       char * pStr1;
       char * pStr2;
       char * pStr3;
       int iRetorno;

       pStr1 = ( char * ) ( * env )->GetStringUTFChars( env, str1, 0 );
       pStr2 = ( char * ) ( * env )->GetStringUTFChars( env, str2, 0 );
       pStr3 = ( char * ) ( * env )->GetStringUTFChars( env, str3, 0 );

       //   iRetorno = Bematech_FITEF_ConfirmaVenda(pStr1, pStr2, pStr3);

       ( * env )->ReleaseStringChars( env, str1, ( jchar * ) pStr1 );
       ( * env )->ReleaseStringChars( env, str2, ( jchar * ) pStr2 );
       ( * env )->ReleaseStringChars( env, str3, ( jchar * ) pStr3 );

       return iRetorno;
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bTEFNaoConfirmaVendaImpressao
* Signature: (Ljava/lang/char*;Ljava/lang/char*;)I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bTEFNaoConfirmaVendaImpressao
     ( JNIEnv * env, jobject obj, jstring str1, jstring str2 )
     {
       char * pStr1;
       char * pStr2;
       int iRetorno;

       pStr1 = ( char * ) ( * env )->GetStringUTFChars( env, str1, 0 );
       pStr2 = ( char * ) ( * env )->GetStringUTFChars( env, str2, 0 );

       //   iRetorno = Bematech_FITEF_NaoConfirmaVendaImpressao(pStr1, pStr2);

       ( * env )->ReleaseStringChars( env, str1, ( jchar * ) pStr1 );
       ( * env )->ReleaseStringChars( env, str2, ( jchar * ) pStr2 );

       return iRetorno;
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bTEFCancelaVendaCartao
* Signature: (Ljava/lang/char*;Ljava/lang/char*;Ljava/lang/char*;Ljava/lang/char*;Ljava/lang/char*;Ljava/lang/char*;Ljava/lang/char*;)I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bTEFCancelaVendaCartao
     ( JNIEnv * env, jobject obj, jstring str1, jstring str2, jstring str3, jstring str4, jstring str5,
     jstring str6, jstring str7 )
     {
       char * pStr1;
       char * pStr2;
       char * pStr3;
       char * pStr4;
       char * pStr5;
       char * pStr6;
       char * pStr7;
       int iRetorno;

       pStr1 = ( char * ) ( * env )->GetStringUTFChars( env, str1, 0 );
       pStr2 = ( char * ) ( * env )->GetStringUTFChars( env, str2, 0 );
       pStr3 = ( char * ) ( * env )->GetStringUTFChars( env, str3, 0 );
       pStr4 = ( char * ) ( * env )->GetStringUTFChars( env, str4, 0 );
       pStr5 = ( char * ) ( * env )->GetStringUTFChars( env, str5, 0 );
       pStr6 = ( char * ) ( * env )->GetStringUTFChars( env, str6, 0 );
       pStr7 = ( char * ) ( * env )->GetStringUTFChars( env, str7, 0 );

       //   iRetorno = Bematech_FITEF_CancelaVendaCartao(pStr1, pStr2, pStr3, pStr4, pStr5, pStr6, pStr7);

       ( * env )->ReleaseStringChars( env, str1, ( jchar * ) pStr1 );
       ( * env )->ReleaseStringChars( env, str2, ( jchar * ) pStr2 );
       ( * env )->ReleaseStringChars( env, str3, ( jchar * ) pStr3 );
       ( * env )->ReleaseStringChars( env, str4, ( jchar * ) pStr4 );
       ( * env )->ReleaseStringChars( env, str5, ( jchar * ) pStr5 );
       ( * env )->ReleaseStringChars( env, str6, ( jchar * ) pStr6 );
       ( * env )->ReleaseStringChars( env, str7, ( jchar * ) pStr7 );

       return iRetorno;
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bTEFImprimeTEF
* Signature: (Ljava/lang/char*;Ljava/lang/char*;Ljava/lang/char*;)I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bTEFImprimeTEF
     ( JNIEnv * env, jobject obj, jstring str1, jstring str2, jstring str3 )
     {
       char * pStr1;
       char * pStr2;
       char * pStr3;
       int iRetorno;

       pStr1 = ( char * ) ( * env )->GetStringUTFChars( env, str1, 0 );
       pStr2 = ( char * ) ( * env )->GetStringUTFChars( env, str2, 0 );
       pStr3 = ( char * ) ( * env )->GetStringUTFChars( env, str3, 0 );

       //   iRetorno = Bematech_FITEF_ImprimeTEF(pStr1, pStr2, pStr3);

       ( * env )->ReleaseStringChars( env, str1, ( jchar * ) pStr1 );
       ( * env )->ReleaseStringChars( env, str2, ( jchar * ) pStr2 );
       ( * env )->ReleaseStringChars( env, str3, ( jchar * ) pStr3 );

       return iRetorno;
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bTEFADM * Signature: (Ljava/lang/char*;)I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bTEFADM( JNIEnv * env, jobject obj, jstring str1 )
{
  char * pStr1;
  int iRetorno;

  pStr1 = ( char * ) ( * env )->GetStringUTFChars( env, str1, 0 );

  //   iRetorno = Bematech_FITEF_ADM(pStr1);

  ( * env )->ReleaseStringChars( env, str1, ( jchar * ) pStr1 );

  return iRetorno;
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bTEFVendaCompleta
* Signature: (Ljava/lang/char*;Ljava/lang/char*;Ljava/lang/char*;Ljava/lang/char*;)I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bTEFVendaCompleta
     ( JNIEnv * env, jobject obj, jstring str1, jstring str2, jstring str3, jstring str4 )
     {
       char * pStr1;
       char * pStr2;
       char * pStr3;
       char * pStr4;
       int iRetorno;

       pStr1 = ( char * ) ( * env )->GetStringUTFChars( env, str1, 0 );
       pStr2 = ( char * ) ( * env )->GetStringUTFChars( env, str2, 0 );
       pStr3 = ( char * ) ( * env )->GetStringUTFChars( env, str3, 0 );
       pStr4 = ( char * ) ( * env )->GetStringUTFChars( env, str4, 0 );

       //   iRetorno = Bematech_FITEF_VendaCompleta(pStr1, pStr2, pStr3, pStr4);

       ( * env )->ReleaseStringChars( env, str1, ( jchar * ) pStr1 );
       ( * env )->ReleaseStringChars( env, str2, ( jchar * ) pStr2 );
       ( * env )->ReleaseStringChars( env, str3, ( jchar * ) pStr3 );
       ( * env )->ReleaseStringChars( env, str4, ( jchar * ) pStr4 );

       return iRetorno;
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bTEFConfiguraDiretorioTef
* Signature: (Ljava/lang/char*;Ljava/lang/char*;)I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bTEFConfiguraDiretorioTef
     ( JNIEnv * env, jobject obj, jstring str1, jstring str2 )
     {
       char * pStr1;
       char * pStr2;
       int iRetorno;

       pStr1 = ( char * ) ( * env )->GetStringUTFChars( env, str1, 0 );
       pStr2 = ( char * ) ( * env )->GetStringUTFChars( env, str2, 0 );

       //   iRetorno = Bematech_FITEF_ConfiguraDiretorioTef(pStr1, pStr2);

       ( * env )->ReleaseStringChars( env, str1, ( jchar * ) pStr1 );
       ( * env )->ReleaseStringChars( env, str2, ( jchar * ) pStr2 );

       return iRetorno;
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bTEFVendaCheque * Signature: (Ljava/lang/char*;Ljava/lang/char*;)I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bTEFVendaCheque( JNIEnv * env, jobject obj, jstring str1, jstring str2 )
{
  char * pStr1;
  char * pStr2;
  int iRetorno;

  pStr1 = ( char * ) ( * env )->GetStringUTFChars( env, str1, 0 );
  pStr2 = ( char * ) ( * env )->GetStringUTFChars( env, str2, 0 );

  //   iRetorno = Bematech_FITEF_VendaCheque(pStr1, pStr2);

  ( * env )->ReleaseStringChars( env, str1, ( jchar * ) pStr1 );
  ( * env )->ReleaseStringChars( env, str2, ( jchar * ) pStr2 );

  return iRetorno;
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bTEFApagaResiduos * Signature: ()I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bTEFApagaResiduos( JNIEnv * env, jobject obj )
{
  //   return Bematech_FITEF_ApagaResiduos();
  return 0;
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bAbrePortaSerial * Signature: ()I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bAbrePortaSerial( JNIEnv * env, jobject obj )
{
  return Bematech_FI_AbrePortaSerial();
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bRetornoImpressora * Signature: (III)I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bRetornoImpressora( JNIEnv * env, jobject obj, jint i1, jint i2, jint i3 )
{
  return Bematech_FI_RetornoImpressora( i1, i2, i3 );
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bFechaPortaSerial * Signature: ()I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bFechaPortaSerial( JNIEnv * env, jobject obj )
{
  return Bematech_FI_FechaPortaSerial();
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bMapaResumo * Signature: ()I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bMapaResumo( JNIEnv * env, jobject obj )
{
  return Bematech_FI_MapaResumo();
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bAberturaDoDia * Signature: (Ljava/lang/char*;Ljava/lang/char*;)I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bAberturaDoDia( JNIEnv * env, jobject obj, jstring str1, jstring str2 )
{
  char * pStr1;
  char * pStr2;
  int iRetorno;

  pStr1 = ( char * ) ( * env )->GetStringUTFChars( env, str1, 0 );
  pStr2 = ( char * ) ( * env )->GetStringUTFChars( env, str2, 0 );

  iRetorno = Bematech_FI_AberturaDoDia( pStr1, pStr2 );

  ( * env )->ReleaseStringChars( env, str1, ( jchar * ) pStr1 );
  ( * env )->ReleaseStringChars( env, str2, ( jchar * ) pStr2 );

  return iRetorno;

}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bFechamentoDoDia * Signature: ()I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bFechamentoDoDia( JNIEnv * env, jobject obj )
{
  return Bematech_FI_FechamentoDoDia();
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bImprimeConfiguracoesImpressora * Signature: ()I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bImprimeConfiguracoesImpressora( JNIEnv * env, jobject obj )
{
  return Bematech_FI_ImprimeConfiguracoesImpressora();
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bImprimeDepartamentos * Signature: ()I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bImprimeDepartamentos( JNIEnv * env, jobject obj )
{
  return Bematech_FI_ImprimeDepartamentos();
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bRelatorioTipo60Analitico * Signature: ()I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bRelatorioTipo60Analitico( JNIEnv * env, jobject obj )
{
  return Bematech_FI_RelatorioTipo60Analitico();
}


/* * Class:     bibli_drivers_JBemaFI32 * Method:    bRelatorioTipo60Mestre * Signature: ()I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bRelatorioTipo60Mestre( JNIEnv * env, jobject obj )
{
  return Bematech_FI_RelatorioTipo60Mestre();
}

/* * Class:     bibli_drivers_JBemaFI32 * Method:    bVerificaImpressoraLigada * Signature: ()I */
JNIEXPORT jint JNICALL Java_bibli_drivers_JBemaFI32_bVerificaImpressoraLigada( JNIEnv * env, jobject obj )
{
  return Bematech_FI_VerificaImpressoraLigada();
}
void logger(char *log) {
  FILE *fArq;
  fArq=fopen("dll-jni.log","a");
  fputs(log,fArq);
  fflush(fArq);
  fclose(fArq);
}


