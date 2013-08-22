/* Comments for database objects. */
COMMENT ON TABLE        ATATENDENTE IS 'Atendentes.';
COMMENT ON    COLUMN    ATATENDIMENTO.DATAATENDOFIN IS 'Data final do atendimento';
COMMENT ON    COLUMN    ATATENDIMENTO.HORAATENDOFIN IS 'Hora final do atendimento.';
COMMENT ON    COLUMN    ATATENDIMENTO.OBSINTERNO IS 'Campo para observações de interesse interno.';
COMMENT ON    COLUMN    ATATENDIMENTO.CODEMPCT IS 'Código da empresa do contrato.';
COMMENT ON    COLUMN    ATATENDIMENTO.CODFILIALCT IS 'Código da filial do contrato.';
COMMENT ON    COLUMN    ATATENDIMENTO.CODCONTR IS 'Código do contrato.';
COMMENT ON    COLUMN    ATATENDIMENTO.CODITCONTR IS 'Código do ítem de contrato.';
COMMENT ON    COLUMN    ATATENDIMENTO.CODEMPCH IS 'Código da empresa do chamado.';
COMMENT ON    COLUMN    ATATENDIMENTO.CODFILIALCH IS 'Código da filial do chamado.';
COMMENT ON    COLUMN    ATATENDIMENTO.CODCHAMADO IS 'Código do Chamado.';
COMMENT ON    COLUMN    ATATENDIMENTO.CONCLUICHAMADO IS 'Indica se o atendimento deve concluir o chamado';
COMMENT ON    COLUMN    ATATENDIMENTO.CODEMPEA IS 'Código da empresa na tabela de especificação.';
COMMENT ON    COLUMN    ATATENDIMENTO.CODFILIALEA IS 'Código da filial na tabela de especificação.';
COMMENT ON    COLUMN    ATATENDIMENTO.CODESPEC IS 'Código de especificação.';
COMMENT ON TABLE        ATATENDIMENTOITREC IS 'Tabela para vinculo entre item de contas a receber e atendimentos, a fim de registrar contatos e atendimentos,
referentes a cobranças e negociações de títulos.';
COMMENT ON TABLE        ATCLASATENDO IS 'Tabela para classificar um atendimento.
ex.: bug, cortesia, garantia, etc.';
COMMENT ON    COLUMN    ATCLASATENDO.GERACOB IS 'Indica se deve gerar cobrança no relatório de atendimentos.';
COMMENT ON    COLUMN    ATCLASATENDO.GERAREL IS 'Indica se deve aparecer no relatório de atendimentos.';
COMMENT ON    COLUMN    ATCONVENIADO.CODEMPEC IS 'Código da empresa - Encaminhador
';
COMMENT ON    COLUMN    ATCONVENIADO.CODFILIALEC IS 'Código da filial na tabela de encaminhador.
';
COMMENT ON    COLUMN    ATCONVENIADO.CODENC IS 'Código do encaminhador.
';
COMMENT ON    COLUMN    ATCONVENIADO.CODEMPFC IS 'Código da empresa na tabela RHFUNCAO';
COMMENT ON    COLUMN    ATCONVENIADO.CODFILIALFC IS 'Código da filial na tabela RHFUNCAO.';
COMMENT ON    COLUMN    ATCONVENIADO.CODFUNC IS 'Código da função.';
COMMENT ON    COLUMN    ATCONVENIADO.DDDCONV IS 'Código DDD do telefone principal do conveniado.';
COMMENT ON    COLUMN    ATCONVENIADO.DDDFAXCONV IS 'CÓDIGO DDD DO FAX DO CONVENIADO.';
COMMENT ON    COLUMN    ATCONVENIADO.DDDCELCONV IS 'CÓDIGO DDD DO CELULAR DO CONVENIADO.';
COMMENT ON TABLE        ATENCAMINHADOR IS 'Tabela de empresas encaminhadoras de conveniados';
COMMENT ON    COLUMN    ATENCAMINHADOR.CODEMP IS 'Código da empresa
';
COMMENT ON    COLUMN    ATENCAMINHADOR.CODFILIAL IS 'Código da filial
';
COMMENT ON    COLUMN    ATENCAMINHADOR.CODENC IS 'Código do encaminhador
';
COMMENT ON    COLUMN    ATENCAMINHADOR.NOMEENC IS 'Nome do encaminhador
';
COMMENT ON    COLUMN    ATENCAMINHADOR.ENDENC IS 'Endereço
';
COMMENT ON    COLUMN    ATENCAMINHADOR.NUMENC IS 'Número do logradouro
';
COMMENT ON    COLUMN    ATENCAMINHADOR.COMPLENC IS 'Complemento do endereço
';
COMMENT ON    COLUMN    ATENCAMINHADOR.CIDENC IS 'Cidade
';
COMMENT ON    COLUMN    ATENCAMINHADOR.BAIRENC IS 'Bairro
';
COMMENT ON    COLUMN    ATENCAMINHADOR.UFENC IS 'Estado UF
';
COMMENT ON    COLUMN    ATENCAMINHADOR.CEPENC IS 'Cep
';
COMMENT ON    COLUMN    ATENCAMINHADOR.FONEENC IS 'Telefone
';
COMMENT ON    COLUMN    ATENCAMINHADOR.FAXENC IS 'Número do fax
';
COMMENT ON TABLE        ATESPECATEND IS 'Especificação dos atendimentos';
COMMENT ON    COLUMN    ATESPECATEND.CODEMP IS 'Código da empresa.';
COMMENT ON    COLUMN    ATESPECATEND.CODFILIAL IS 'Código da filial.';
COMMENT ON    COLUMN    ATESPECATEND.CODESPEC IS 'Código da especificação.';
COMMENT ON    COLUMN    ATESPECATEND.DESCESPEC IS 'Descrição da especificação.';
COMMENT ON    COLUMN    ATESPECATEND.PGCOMIESPEC IS 'Paga comissão (S/N).';
COMMENT ON    COLUMN    ATESPECATEND.COBCLIESPEC IS 'Cobrar do cliente (S/N).';
COMMENT ON    COLUMN    ATESPECATEND.CONTMETAESPEC IS 'Contabiliza para meta (S/N).';
COMMENT ON    COLUMN    ATESPECATEND.TEMPOMINCOBESPEC IS 'Tempo mínimo a contabilizar.';
COMMENT ON    COLUMN    ATESPECATEND.TEMPOMAXCOBESPEC IS 'Tempo máximo a contabilizar.';
COMMENT ON    COLUMN    ATESPECATEND.PERCCOMIESPEC IS 'Percentual de comissão do atendente.';
COMMENT ON    COLUMN    ATESPECATEND.DTINS IS 'Data de inserção.';
COMMENT ON    COLUMN    ATESPECATEND.IDUSUINS IS 'Id do usuário que inseriu.';
COMMENT ON    COLUMN    ATESPECATEND.DTALT IS 'Data da última alteração.';
COMMENT ON    COLUMN    ATESPECATEND.IDUSUALT IS 'Id do usuário que fez a última alteração.';
COMMENT ON    COLUMN    ATESPECATEND.HINS IS 'Hora de inserção.';
COMMENT ON    COLUMN    ATESPECATEND.HALT IS 'Hora da última alteração.';
COMMENT ON    COLUMN    ATTIPOATENDO.TIPOATENDO IS 'Tipo de atendimento.
C - Contato
A - Atendimento
';
COMMENT ON TABLE        CBCONTAEXT IS 'Tabela de contas contábeis externas.';
COMMENT ON    COLUMN    CPCOMPRA.DTCOMPCOMPRA IS 'Data de competência.';
COMMENT ON    COLUMN    CPCOMPRA.VLRBASEICMSSTCOMPRA IS 'Valor da base de cálculo do ICMS de substituição tributária.';
COMMENT ON    COLUMN    CPCOMPRA.VLRICMSSTCOMPRA IS 'Valor do ICMS de substituição tributária.';
COMMENT ON    COLUMN    CPCOMPRA.VLRFUNRURALCOMPRA IS 'Valor da retenção referente ao Funrural.';
COMMENT ON    COLUMN    CPCOMPRA.CODEMPTC IS 'Código da empresa na tabela tipo de cobrança.
';
COMMENT ON    COLUMN    CPCOMPRA.CODFILIALTC IS 'Código da filial na tabela tipo de cobrança.
';
COMMENT ON    COLUMN    CPCOMPRA.CODTIPOCOB IS 'Código do tipo de cobrança.
';
COMMENT ON    COLUMN    CPCOMPRA.IMPNOTACOMPRA IS 'Flag que indica se a nota fiscal foi impressa (S/N).';
COMMENT ON    COLUMN    CPCOMPRA.BLOQCOMPRA IS 'Flag de bloqueio de compra.';
COMMENT ON    COLUMN    CPCOMPRA.EMMANUT IS 'Flag que indica se a tabela está em manutenção (S/N).';
COMMENT ON    COLUMN    CPCOMPRA.ADICFRETECOMPRA IS 'Flag que indica se soma o valor do frete ao custo do produto.';
COMMENT ON    COLUMN    CPCOMPRA.TIPOFRETECOMPRA IS 'Tipo do frete na compra.';
COMMENT ON    COLUMN    CPCOMPRA.OBS01 IS 'Campo "coringa" para outras observações.';
COMMENT ON    COLUMN    CPCOMPRA.OBS02 IS 'Campo "coringa" para outras observações.';
COMMENT ON    COLUMN    CPCOMPRA.OBS03 IS 'Campo "coringa" para outras observações.';
COMMENT ON    COLUMN    CPCOMPRA.OBS04 IS 'Campo "coringa" para outras observações.';
COMMENT ON    COLUMN    CPCOMPRA.ADICADICCOMPRA IS 'Flag que indica se soma o valor adicional ao custo do produto.';
COMMENT ON    COLUMN    CPCOMPRA.QTDFRETECOMPRA IS 'Quantidade de volumes na nota de entrada.';
COMMENT ON    COLUMN    CPCOMPRA.ADICFRETEBASEICMS IS 'Indica se deve adicionar o valor do frete à base de calculo do icms.';
COMMENT ON    COLUMN    CPCOMPRA.ADICADICBASEICMS IS 'Indica se deve somar os valores adicionais à base de calculo do ICMS.';
COMMENT ON    COLUMN    CPCOMPRA.ADICIPIBASEICMS IS 'Indica se deve adicionar o valor do IPI à base de calculo do ICMS.';
COMMENT ON    COLUMN    CPCOMPRA.CHAVENFECOMPRA IS 'Chave de acesso da nota fiscal eletrônica de compra.';
COMMENT ON    COLUMN    CPCOMPRA.STATUSCOMPRA IS 'P1 - Pendente;
P2 - Em aberto;
P3 - Em aberto;
C2 - Emitida;
C3 - Emitida;
EP - Pedido de compra emitida parcialmente em outra compra;
ET - Pedido de compra emitido totalmente em outra compra;
X - Cancelada;';
COMMENT ON    COLUMN    CPCOMPRA.NRODI IS 'Número do documento de importação DI/DSI/DA';
COMMENT ON    COLUMN    CPCOMPRA.DTREGDI IS 'Data de registro da DI';
COMMENT ON    COLUMN    CPCOMPRA.LOCDESEMBDI IS 'Loca de desembaraçõ da DI';
COMMENT ON    COLUMN    CPCOMPRA.SIGLAUFDESEMBDI IS 'Estado onde ocorreu o desembaraço da DI';
COMMENT ON    COLUMN    CPCOMPRA.CODPAISDESEMBDI IS 'Código do país onde ocorreu o desembaraço da DI (será sempre o pais da filial, mantido por integridade referencial tabela SGUF)';
COMMENT ON    COLUMN    CPCOMPRA.DTDESEMBDI IS 'Data do desembaraço da DI';
COMMENT ON    COLUMN    CPCOMPRA.IDENTCONTAINER IS 'Identificação do container da compra (importação)';
COMMENT ON    COLUMN    CPCOMPRA.CALCTRIB IS 'Indica se deve realizar o calculo de tributos';
COMMENT ON    COLUMN    CPCOMPRA.CODEMPRM IS 'Código da empresa do recebimento de mercadoria vinculado.';
COMMENT ON    COLUMN    CPCOMPRA.CODFILIALRM IS 'Código da filial do recebimento de mercadoria vinculado.';
COMMENT ON    COLUMN    CPCOMPRA.TICKET IS 'Ticket do recebimento de mercadoria vinculado.';
COMMENT ON    COLUMN    CPCOMPRA.CODEMPCT IS 'Código da empresa da conta.';
COMMENT ON    COLUMN    CPCOMPRA.CODFILIALCT IS 'Código da filial da compra.';
COMMENT ON    COLUMN    CPCOMPRA.NUMCONTA IS 'Número da conta.';
COMMENT ON    COLUMN    CPCOMPRA.CODEMPCC IS 'Código da empresa do centro de custos';
COMMENT ON    COLUMN    CPCOMPRA.CODFILIALCC IS 'Código da filial do centro de custos.';
COMMENT ON    COLUMN    CPCOMPRA.ANOCC IS 'Ano do centro de custo';
COMMENT ON    COLUMN    CPCOMPRA.CODCC IS 'Código do centro de custos';
COMMENT ON    COLUMN    CPCOMPRA.CODEMPPN IS 'Código da empresa do planejamento financeiro.';
COMMENT ON    COLUMN    CPCOMPRA.CODFILIALPN IS 'Código do filial do planejamento financeiro.';
COMMENT ON    COLUMN    CPCOMPRA.CODPLAN IS 'Código do planejamento financeiro.';
COMMENT ON    COLUMN    CPCOMPRA.INFCOMPL IS 'Informações complementares (interesse do fisco)';
COMMENT ON    COLUMN    CPCOMPRA.NUMACDRAW IS 'Número do ato concessório do regime drawback (importação)';
COMMENT ON    COLUMN    CPCOMPRA.TIPODOCIMP IS 'Tipo de documento de importação:
0 - Declaração de importação
1 - Declaração simplificada de importação';
COMMENT ON    COLUMN    CPCOMPRA.SITDOC IS 'Situação do documento fiscal:
00-Documento regular;
01-Documento regular expontâneo;
02-Documento cancelado;
03-Documento cancelado expontâneo
04-NFE Denegada;
05-NFE Numeração inutilizada;
06-Documento fiscal complementar;
07-Documento fiscal complementar expontâneo;
08-Documento emitido com base em Regime Especial ou Norma Específica;';
COMMENT ON    COLUMN    CPCOMPRA.OBSNFE IS 'Observacao gerada na emissao de nfe.';
COMMENT ON    COLUMN    CPCOMPRA.CODEMPIM IS 'Código da empresa de importação.';
COMMENT ON    COLUMN    CPCOMPRA.CODFILIALIM IS 'Código da filial da importação.';
COMMENT ON    COLUMN    CPCOMPRA.CODIMP IS 'Código da importação.
';
COMMENT ON    COLUMN    CPCOMPRA.VLRBASEIICOMPRA IS 'Valor da base de calculo do imposto de importação.
';
COMMENT ON    COLUMN    CPCOMPRA.VLRIICOMPRA IS 'Valor do imposto de importação da compra.
';
COMMENT ON TABLE        CPCOMPRASOL IS 'Tabela de vinculos para amarração entre solicitações de compra
e pedidos/nf de compra.';
COMMENT ON    COLUMN    CPCOMPRASOL.CODCOMPRA IS 'Código da compra.';
COMMENT ON    COLUMN    CPCOMPRASOL.CODSOL IS 'Código da solicitação de compra.';
COMMENT ON    COLUMN    CPCOTACAO.DTVALIDCOT IS 'Data de validade da proposta de preço.';
COMMENT ON    COLUMN    CPCOTACAO.SITCOMPITSOL IS '"PE" - Pendente;
"FN" - Finalizada;';
COMMENT ON    COLUMN    CPCOTACAO.SITAPROVITSOL IS '"PE" - Pendente;
"AP" - Aprovada;';
COMMENT ON    COLUMN    CPCOTACAO.SITITSOL IS '"PE" - Pendente;
"FN" - Finalizada;';
COMMENT ON    COLUMN    CPCOTACAO.USARENDACOT IS 'Indica se deve usar o critério da renda na busca do preço de cotações.';
COMMENT ON    COLUMN    CPCOTACAO.RENDACOT IS 'Indica a renda (qualidade) do produto cotado.';
COMMENT ON    COLUMN    CPCOTACAO.CODEMPPP IS 'Código da empresa do plano de pagamento.';
COMMENT ON    COLUMN    CPCOTACAO.CODFILIALPP IS 'Código da filial do plano de pagamento.';
COMMENT ON    COLUMN    CPCOTACAO.CODPLANOPAG IS 'Código do plano de pagamento.';
COMMENT ON TABLE        CPDEVOLUCAO IS 'Tabela para relacionamento entre itens de compra e venda, caracterizando a devolução de mercadorias.';
COMMENT ON    COLUMN    CPDEVOLUCAO.CODEMP IS 'Código da empresa da compra.';
COMMENT ON    COLUMN    CPDEVOLUCAO.CODFILIAL IS 'Código da filial da compra.';
COMMENT ON    COLUMN    CPDEVOLUCAO.CODCOMPRA IS 'Código da compra.';
COMMENT ON    COLUMN    CPDEVOLUCAO.CODITCOMPRA IS 'Código do ítem de compra.';
COMMENT ON    COLUMN    CPDEVOLUCAO.QTDDEV IS 'Quantidade devolvida.';
COMMENT ON    COLUMN    CPDEVOLUCAO.CODEMPVD IS 'Código da empresa da venda (devolução)';
COMMENT ON    COLUMN    CPDEVOLUCAO.CODFILIALVD IS 'Código da filial da venda (devolução)';
COMMENT ON    COLUMN    CPDEVOLUCAO.CODVENDA IS 'Código da venda (devolução)';
COMMENT ON    COLUMN    CPDEVOLUCAO.TIPOVENDA IS 'Tipo da venda (devolução)';
COMMENT ON    COLUMN    CPDEVOLUCAO.CODITVENDA IS 'Código do ítem da venda (devolução)';
COMMENT ON    COLUMN    CPFORNECED.CELFOR IS 'Número do celular
';
COMMENT ON    COLUMN    CPFORNECED.SSPFOR IS 'Orgão de espedição do rg.';
COMMENT ON    COLUMN    CPFORNECED.DDDFONEFOR IS 'DDD do telefone principal.';
COMMENT ON    COLUMN    CPFORNECED.DDDFAXFOR IS 'DDD do fax do fornecedor.';
COMMENT ON    COLUMN    CPFORNECED.DDDCELFOR IS 'DDD do celular do fornecedor.';
COMMENT ON    COLUMN    CPFORNECED.SITEFOR IS 'Endereço eletrônico do site do fornecedor.';
COMMENT ON    COLUMN    CPFORNECED.CODFORCONTAB IS 'Código contabil';
COMMENT ON    COLUMN    CPFORNECED.CODUNIFCOD IS 'Código na tabela de unificação de códigos (SGUNIFCOD).';
COMMENT ON    COLUMN    CPFORNECED.CODEMPFF IS 'Código da empresa do tipo fiscal do fornecedor.';
COMMENT ON    COLUMN    CPFORNECED.CODFILIALFF IS 'Código da filial do tipo fiscal do fornecedor.';
COMMENT ON    COLUMN    CPFORNECED.CODFISCFOR IS 'Código do tipo fiscal do fornecedor';
COMMENT ON    COLUMN    CPFORNECED.SUFRAMAFOR IS 'Código do SUFRAMA do fornecedor.';
COMMENT ON    COLUMN    CPFORNECED.NRODEPENDFOR IS 'Número de dependes do fornecedor (pessoa física) (calculo de irrf)';
COMMENT ON    COLUMN    CPFORNECED.INSCCONREG IS 'Inscrição no conselho regional (contabilidae, administração, medicina, etc..)';
COMMENT ON    COLUMN    CPFRETECP.VLRSEGFRETECP IS 'Valor do seguro no frete.';
COMMENT ON TABLE        CPIMPORTACAO IS 'Tabela de informações sobre importações.';
COMMENT ON    COLUMN    CPIMPORTACAO.CODEMP IS 'Código da empresa.';
COMMENT ON    COLUMN    CPIMPORTACAO.CODFILIAL IS 'Código da filial';
COMMENT ON    COLUMN    CPIMPORTACAO.CODIMP IS 'Código da importação';
COMMENT ON    COLUMN    CPIMPORTACAO.CODEMPMI IS 'Código da empresa da moeda de importação.';
COMMENT ON    COLUMN    CPIMPORTACAO.CODFILIALMI IS 'Código da filial da moeda de importação.';
COMMENT ON    COLUMN    CPIMPORTACAO.CODMOEDA IS 'Código da moeda de importação.';
COMMENT ON    COLUMN    CPIMPORTACAO.COTACAOMOEDA IS 'Fator de conversão da moeda de importação para moeda corrente.';
COMMENT ON    COLUMN    CPIMPORTACAO.CODEMPPG IS 'Código da empresa do plano de pagamento.';
COMMENT ON    COLUMN    CPIMPORTACAO.CODFILIALPG IS 'Código da filial do plano de pagamento';
COMMENT ON    COLUMN    CPIMPORTACAO.CODPLANOPAG IS 'Código do plano de pagamento acordado no invoice.';
COMMENT ON    COLUMN    CPIMPORTACAO.CODEMPFR IS 'Código da empresa do fornecedor.';
COMMENT ON    COLUMN    CPIMPORTACAO.CODFILIALFR IS 'Código da filial do fornecedor.';
COMMENT ON    COLUMN    CPIMPORTACAO.CODFOR IS 'Código do fornecedor na importação.';
COMMENT ON    COLUMN    CPIMPORTACAO.TIPOIMP IS 'Tipo de importação - O = Original / C = Complementar';
COMMENT ON    COLUMN    CPIMPORTACAO.INVOICE IS 'Identificação do invoice (pedido de compra de importação)';
COMMENT ON    COLUMN    CPIMPORTACAO.DI IS 'Número da declaração de importação .';
COMMENT ON    COLUMN    CPIMPORTACAO.MANIFESTO IS 'Identificação do manifesto de carga.';
COMMENT ON    COLUMN    CPIMPORTACAO.CERTORIGEM IS 'Certificado de origem da mercadoria.';
COMMENT ON    COLUMN    CPIMPORTACAO.LACRE IS 'Identificação do lacre';
COMMENT ON    COLUMN    CPIMPORTACAO.PRESCARGA IS 'Presença de carga';
COMMENT ON    COLUMN    CPIMPORTACAO.IDENTHOUSE IS 'Identificação House B/L';
COMMENT ON    COLUMN    CPIMPORTACAO.CONHECCARGA IS 'Identificação do conhecimento de carga.';
COMMENT ON    COLUMN    CPIMPORTACAO.IDENTCONTAINER IS 'Identificação do container';
COMMENT ON    COLUMN    CPIMPORTACAO.TIPOMANIFESTO IS 'Tipo de manifesto.';
COMMENT ON    COLUMN    CPIMPORTACAO.DTIMP IS 'Data da importação';
COMMENT ON    COLUMN    CPIMPORTACAO.DTEMB IS 'Data de embarque.';
COMMENT ON    COLUMN    CPIMPORTACAO.DTCHEGADA IS 'Data de chegada';
COMMENT ON    COLUMN    CPIMPORTACAO.DTDESEMBDI IS 'Data do desembaraço da declaração de importação';
COMMENT ON    COLUMN    CPIMPORTACAO.DTREGDI IS 'Data de registro da declaração de importação.';
COMMENT ON    COLUMN    CPIMPORTACAO.LOCALEMB IS 'Local do desembaraço da declaração de importação.';
COMMENT ON    COLUMN    CPIMPORTACAO.RECINTOADUANEIRO IS 'Recinto Aduaneiro';
COMMENT ON    COLUMN    CPIMPORTACAO.CODPAISDESEMBDI IS 'Código do país do desembaraço da DI';
COMMENT ON    COLUMN    CPIMPORTACAO.SIGLAUFDESEMBDI IS 'Sigla da unidade da federação do desembaraço da DI';
COMMENT ON    COLUMN    CPIMPORTACAO.LOCDESEMBDI IS ' Local de desembaraço da DI.';
COMMENT ON    COLUMN    CPIMPORTACAO.OBS IS 'Observações';
COMMENT ON    COLUMN    CPIMPORTACAO.VEICULO IS 'Identificação do veículo (ex.: Nome do navio)';
COMMENT ON    COLUMN    CPIMPORTACAO.PESOBRUTO IS 'Peso bruto total.';
COMMENT ON    COLUMN    CPIMPORTACAO.PESOLIQUIDO IS 'Peso líquido total';
COMMENT ON    COLUMN    CPIMPORTACAO.VLRFRETEMI IS 'Valor do frete na moeda de importação';
COMMENT ON    COLUMN    CPIMPORTACAO.VLRFRETE IS 'Valor do frete na moeda corrente.';
COMMENT ON    COLUMN    CPIMPORTACAO.VMLEMI IS 'Valor liquido sem o frete na moeda de importação.';
COMMENT ON    COLUMN    CPIMPORTACAO.VMLDMI IS 'Valor liquido mais o frete na moeda de importação.';
COMMENT ON    COLUMN    CPIMPORTACAO.VMLE IS 'Valor liquido sem o frete na moeda corrente.';
COMMENT ON    COLUMN    CPIMPORTACAO.VMLD IS 'Valor liquido mais o frete na moeda corrente.';
COMMENT ON    COLUMN    CPIMPORTACAO.VLRSEGUROMI IS 'Valor do seguro na moeda de importação.';
COMMENT ON    COLUMN    CPIMPORTACAO.VLRSEGURO IS 'Valor do seguro na moeda corrente.';
COMMENT ON    COLUMN    CPIMPORTACAO.VLRII IS 'Valor do imposto de importação.';
COMMENT ON    COLUMN    CPIMPORTACAO.VLRIPI IS 'Valor do  IPI';
COMMENT ON    COLUMN    CPIMPORTACAO.VLRPIS IS 'Valor do PIS';
COMMENT ON    COLUMN    CPIMPORTACAO.VLRCOFINS IS 'Valor do cofins';
COMMENT ON    COLUMN    CPIMPORTACAO.VLRDIREITOSAD IS 'Valor de direitos antidumping.';
COMMENT ON    COLUMN    CPIMPORTACAO.VLRTHC IS 'Valor da taxa de manutenção no terminal (Terminal Handling Charges)';
COMMENT ON    COLUMN    CPIMPORTACAO.VLRTHCMI IS 'Valor do THC em moeda de importação';
COMMENT ON    COLUMN    CPIMPORTACAO.VLRTXSISCOMEX IS 'Valor da taxa siscomex.';
COMMENT ON    COLUMN    CPIMPORTACAO.VLRAD IS 'Valor aduaneiro valor da mercadoria + frete +seguro +thc
';
COMMENT ON    COLUMN    CPIMPORTACAO.VLRADMI IS 'Valor aduaneiro em moeda de importação
';
COMMENT ON    COLUMN    CPIMPORTACAO.VLRBASEICMS IS 'Base de calculo do icms
';
COMMENT ON    COLUMN    CPIMPORTACAO.VLRCOMPL IS 'Valor para nota fiscal complementar';
COMMENT ON    COLUMN    CPIMPORTACAO.EMMANUT IS 'Flag que indica se a tabela está em manutenção (S/N).';
COMMENT ON    COLUMN    CPIMPORTACAO.DTINS IS 'Data de inserção do registro.';
COMMENT ON    COLUMN    CPIMPORTACAO.HINS IS 'Hora de inserção do registro.';
COMMENT ON    COLUMN    CPIMPORTACAO.IDUSUINS IS 'Identificação do usuário que inseriu o registro';
COMMENT ON    COLUMN    CPIMPORTACAO.DTALT IS 'Data de alteração do registro';
COMMENT ON    COLUMN    CPIMPORTACAO.HALT IS 'Hora de alteração do registro.';
COMMENT ON    COLUMN    CPIMPORTACAO.IDUSUALT IS 'Identificação do usuário que alterou o registro.';
COMMENT ON TABLE        CPIMPORTACAOADIC IS 'Tabela de adições da importação.';
COMMENT ON    COLUMN    CPIMPORTACAOADIC.CODEMP IS 'Código da empresa';
COMMENT ON    COLUMN    CPIMPORTACAOADIC.CODFILIAL IS 'Código da filial';
COMMENT ON    COLUMN    CPIMPORTACAOADIC.CODIMP IS 'Código da importação';
COMMENT ON    COLUMN    CPIMPORTACAOADIC.CODNCM IS 'Código da NCM';
COMMENT ON    COLUMN    CPIMPORTACAOADIC.CODADIC IS 'Código da adicao';
COMMENT ON    COLUMN    CPIMPORTACAOADIC.VLRTXSISCOMEX IS 'Valor da taxa siscomex para a adicão';
COMMENT ON    COLUMN    CPIMPORTACAOADIC.DTINS IS 'Data de inserção do registro';
COMMENT ON    COLUMN    CPIMPORTACAOADIC.HINS IS 'Hora de inserção do registro';
COMMENT ON    COLUMN    CPIMPORTACAOADIC.IDUSUINS IS 'Identificação do usuário de inseriu o registro';
COMMENT ON    COLUMN    CPIMPORTACAOADIC.DTALT IS 'Data da alteração do registro';
COMMENT ON    COLUMN    CPIMPORTACAOADIC.HALT IS 'Hora da alteração do registro';
COMMENT ON    COLUMN    CPIMPORTACAOADIC.IDUSUALT IS 'Identificação do usuário que alterou o registro';
COMMENT ON    COLUMN    CPITCOMPRA.CODEMPAX IS 'Código da empresa na tabela de almoxarifados.';
COMMENT ON    COLUMN    CPITCOMPRA.CODFILIALAX IS 'Código da filial na tabela de almoxarifados.';
COMMENT ON    COLUMN    CPITCOMPRA.CODALMOX IS 'Código do almoxarifado.';
COMMENT ON    COLUMN    CPITCOMPRA.QTDITCOMPRACANC IS 'Quantidade cancelada';
COMMENT ON    COLUMN    CPITCOMPRA.VLRBASEFUNRURALITCOMPRA IS 'Valor para base de calculo da retenção do funrural.';
COMMENT ON    COLUMN    CPITCOMPRA.ALIQFUNRURALITCOMPRA IS 'Percentual de retenção do funrural no item de compra.';
COMMENT ON    COLUMN    CPITCOMPRA.VLRFUNRURALITCOMPRA IS 'Valor de retenção do funrural do item.';
COMMENT ON    COLUMN    CPITCOMPRA.CUSTOVDITCOMPRA IS 'Custo para formação de preços
';
COMMENT ON    COLUMN    CPITCOMPRA.CODEMPIF IS 'Codigo da empresa do item de classificação (regra no momento da inserção)';
COMMENT ON    COLUMN    CPITCOMPRA.CODFILIALIF IS 'Codigo da filial do item de classificação (regra no momento da inserção)';
COMMENT ON    COLUMN    CPITCOMPRA.CODFISC IS 'Codigo da classificação (regra no momento da inserção)';
COMMENT ON    COLUMN    CPITCOMPRA.EMMANUT IS 'Flag que indica se a tabela está em manutenção.';
COMMENT ON    COLUMN    CPITCOMPRA.CODEMPNS IS 'Código da empresa do número de série';
COMMENT ON    COLUMN    CPITCOMPRA.CODFILIALNS IS 'Código da filial do número de série
';
COMMENT ON    COLUMN    CPITCOMPRA.NUMSERIETMP IS 'Campo para abrigar temporariamente o número de serie do produto (para uso do trigger quando produto for unitário)
';
COMMENT ON    COLUMN    CPITCOMPRA.NADICAO IS 'Número da adicao (entrada de importação)';
COMMENT ON    COLUMN    CPITCOMPRA.SEQADIC IS 'Número sequencial do ítem dentro da adição.';
COMMENT ON    COLUMN    CPITCOMPRA.DESCDI IS 'Valor do desconto do item da DI
';
COMMENT ON    COLUMN    CPITCOMPRA.APROVPRECO IS 'Indica se o preço da compra foi aprovado (veio de cotação anterior);';
COMMENT ON    COLUMN    CPITCOMPRA.EMITITCOMPRA IS 'Indica se o ítem de compra já foi emitido em compra compra (processo de faturamento de pedidos de compra)';
COMMENT ON TABLE        CPITCOMPRASERIE IS 'Tabela de vinculo entre item de compra e seus respectivos numeros de serie.';
COMMENT ON TABLE        CPITIMPORTACAO IS 'Tabela de item de importação.';
COMMENT ON    COLUMN    CPITIMPORTACAO.CODEMP IS 'Código da empresa.';
COMMENT ON    COLUMN    CPITIMPORTACAO.CODFILIAL IS 'Código da filial.';
COMMENT ON    COLUMN    CPITIMPORTACAO.CODIMP IS 'Código da importação.';
COMMENT ON    COLUMN    CPITIMPORTACAO.CODITIMP IS 'Código do item de importação.';
COMMENT ON    COLUMN    CPITIMPORTACAO.CODEMPPD IS 'Código da empresa do produto.';
COMMENT ON    COLUMN    CPITIMPORTACAO.CODFILIALPD IS 'Código da filial do produto.';
COMMENT ON    COLUMN    CPITIMPORTACAO.CODPROD IS 'Código do produto.';
COMMENT ON    COLUMN    CPITIMPORTACAO.REFPROD IS 'Referência do produto.';
COMMENT ON    COLUMN    CPITIMPORTACAO.QTD IS 'Quantidade de itens.';
COMMENT ON    COLUMN    CPITIMPORTACAO.CODEMPUN IS 'Código da empresa da unidade de medida.';
COMMENT ON    COLUMN    CPITIMPORTACAO.CODFILIALUN IS 'Código da filial da unidade de medida.';
COMMENT ON    COLUMN    CPITIMPORTACAO.CODUNID IS 'Código da unidade de medida
';
COMMENT ON    COLUMN    CPITIMPORTACAO.PESOLIQUIDO IS 'Peso liquido';
COMMENT ON    COLUMN    CPITIMPORTACAO.PESOBRUTO IS 'Peso bruto';
COMMENT ON    COLUMN    CPITIMPORTACAO.PRECOMI IS 'Preco na moeda de importação';
COMMENT ON    COLUMN    CPITIMPORTACAO.PRECO IS 'Preço na moeda corrente.';
COMMENT ON    COLUMN    CPITIMPORTACAO.VMLEMI IS 'Valor bruto da mercadoria na moeda de importação.';
COMMENT ON    COLUMN    CPITIMPORTACAO.VMLDMI IS 'Valor bruto da mercadoria + frete e seguro, na moeda de importação.';
COMMENT ON    COLUMN    CPITIMPORTACAO.VMLE IS 'Valor bruto da mercadoria na moeda corrente.';
COMMENT ON    COLUMN    CPITIMPORTACAO.VMLD IS 'Valor bruto da mercadoria + frete e seguro, na moeda corrente.';
COMMENT ON    COLUMN    CPITIMPORTACAO.VLRFRETEMI IS 'Valor do frete na moeda de importação.';
COMMENT ON    COLUMN    CPITIMPORTACAO.VLRFRETE IS 'Valor do frete na moeda corrente.';
COMMENT ON    COLUMN    CPITIMPORTACAO.VLRSEGUROMI IS 'Valor do seguro na moeda de importação.';
COMMENT ON    COLUMN    CPITIMPORTACAO.VLRSEGURO IS 'Valor do seguro na moeda corrente.';
COMMENT ON    COLUMN    CPITIMPORTACAO.VLRTHCMI IS 'Valor do THC na moeda de importação.';
COMMENT ON    COLUMN    CPITIMPORTACAO.VLRTHC IS 'Valor do THC';
COMMENT ON    COLUMN    CPITIMPORTACAO.VLRADMI IS 'Valor aduaneiro (Valor bruto+frete+seguro+thc) em moeda de importação.';
COMMENT ON    COLUMN    CPITIMPORTACAO.VLRAD IS 'Valor aduaneiro (Valor bruto+frete+seguro+thc) na moeda corrente.';
COMMENT ON    COLUMN    CPITIMPORTACAO.ALIQICMSIMP IS 'Aliquota do ICMS de importação.';
COMMENT ON    COLUMN    CPITIMPORTACAO.ALIQICMSUF IS 'Aliquota do ICMS da UF (Operação futura)';
COMMENT ON    COLUMN    CPITIMPORTACAO.PERCDIFERICMS IS 'Percentual do diferimento do ICMS.';
COMMENT ON    COLUMN    CPITIMPORTACAO.ALIQIPI IS 'Aliquota de IPI';
COMMENT ON    COLUMN    CPITIMPORTACAO.ALIQPIS IS 'Aliquota do PIS';
COMMENT ON    COLUMN    CPITIMPORTACAO.ALIQCOFINS IS 'Aliquota do cofins';
COMMENT ON    COLUMN    CPITIMPORTACAO.ALIQII IS 'Aliquota do imposto de importação.';
COMMENT ON    COLUMN    CPITIMPORTACAO.VLRII IS 'Valor do imposto de importação.';
COMMENT ON    COLUMN    CPITIMPORTACAO.VLRIPI IS 'Valor do IPI';
COMMENT ON    COLUMN    CPITIMPORTACAO.VLRPIS IS 'Valor do PIS';
COMMENT ON    COLUMN    CPITIMPORTACAO.VLRCOFINS IS 'Valor do cofins';
COMMENT ON    COLUMN    CPITIMPORTACAO.VLRBASEICMS IS 'Valor da base do ICMS.';
COMMENT ON    COLUMN    CPITIMPORTACAO.VLRICMS IS 'Valor do ICMS bruto';
COMMENT ON    COLUMN    CPITIMPORTACAO.VLRICMSDIFERIDO IS 'Valor do ICMS diferido';
COMMENT ON    COLUMN    CPITIMPORTACAO.VLRICMSDEVIDO IS 'Valor do ICMS devido';
COMMENT ON    COLUMN    CPITIMPORTACAO.VLRICMSCREDPRESUM IS 'Valor do crédito do ICMS presumido.';
COMMENT ON    COLUMN    CPITIMPORTACAO.VLRICMSRECOLHIMENTO IS 'Valor do ICMS a recolher.';
COMMENT ON    COLUMN    CPITIMPORTACAO.VLRTXSISCOMEX IS 'Valor da taxa siscomex rateada por ítem.
';
COMMENT ON    COLUMN    CPITIMPORTACAO.VLRVMCV IS 'Valor bruto
';
COMMENT ON    COLUMN    CPITIMPORTACAO.CODEMPCF IS 'Código da empresa do da classificação fiscal.';
COMMENT ON    COLUMN    CPITIMPORTACAO.CODFILIALCF IS 'Código da filial da classificação fiscal.';
COMMENT ON    COLUMN    CPITIMPORTACAO.CODFISC IS 'Codigo da classificação fiscal.';
COMMENT ON    COLUMN    CPITIMPORTACAO.CODITFISC IS 'Código do item de classificação fiscal.';
COMMENT ON    COLUMN    CPITIMPORTACAO.CODNCM IS 'Código da NCM';
COMMENT ON    COLUMN    CPITIMPORTACAO.SEQADIC IS 'Sequencia do item na adição.
';
COMMENT ON    COLUMN    CPITIMPORTACAO.DTINS IS 'Data de inserção do registro.';
COMMENT ON    COLUMN    CPITIMPORTACAO.HINS IS 'Hora da inserção do registro.';
COMMENT ON    COLUMN    CPITIMPORTACAO.IDUSUINS IS 'Identificação do usuário que inseriu o registro.';
COMMENT ON    COLUMN    CPITIMPORTACAO.DTALT IS 'Data da alteração do registro.';
COMMENT ON    COLUMN    CPITIMPORTACAO.HALT IS 'Hora da alteração do registro.';
COMMENT ON    COLUMN    CPITIMPORTACAO.IDUSUALT IS 'Identificação do usuário que alterou o registro.';
COMMENT ON    COLUMN    CPITSOLICITACAO.CODEMP IS 'Código da empresa';
COMMENT ON    COLUMN    CPITSOLICITACAO.CODFILIAL IS 'Código da filial';
COMMENT ON    COLUMN    CPITSOLICITACAO.CODSOL IS 'Código da solicitação';
COMMENT ON    COLUMN    CPITSOLICITACAO.CODITSOL IS 'Número sequencial do item';
COMMENT ON    COLUMN    CPITSOLICITACAO.CODEMPPD IS 'Código da empresa para produtos';
COMMENT ON    COLUMN    CPITSOLICITACAO.CODFILIALPD IS 'Código da filial para produtos';
COMMENT ON    COLUMN    CPITSOLICITACAO.CODPROD IS 'Código do produto ou serviço';
COMMENT ON    COLUMN    CPITSOLICITACAO.REFPROD IS 'Referência do produto.';
COMMENT ON    COLUMN    CPITSOLICITACAO.QTDITSOL IS 'Quantidade solicitada';
COMMENT ON    COLUMN    CPITSOLICITACAO.QTDAPROVITSOL IS 'Quantidade aprovada';
COMMENT ON    COLUMN    CPITSOLICITACAO.IDUSUITSOL IS 'Id do usuário que solicitou';
COMMENT ON    COLUMN    CPITSOLICITACAO.DTITSOL IS 'Data da solicitação do item';
COMMENT ON    COLUMN    CPITSOLICITACAO.IDUSUAPROVITSOL IS 'Id do usuário que aprovou';
COMMENT ON    COLUMN    CPITSOLICITACAO.DTAPROVITSOL IS 'Data da aprovação do item';
COMMENT ON    COLUMN    CPITSOLICITACAO.SITAPROVITSOL IS 'Situação da aprovação do item:
PE - Pendente
AP - Aprovação parcial
AT - Aprovação total
NA - Não aprovada';
COMMENT ON    COLUMN    CPITSOLICITACAO.SITCOMPITSOL IS 'Situação da compra:
PE - Pendente
CP - Compra parcial
CT - Compra total
';
COMMENT ON    COLUMN    CPITSOLICITACAO.SITITSOL IS 'Situação do item da solicitação:
PE - Pendente
FN - Solicitação finalizada
CA - Cancelada';
COMMENT ON    COLUMN    CPRATEIO.CODEMP IS 'Código da empresa.';
COMMENT ON    COLUMN    CPRATEIO.CODFILIAL IS 'Código da filial.';
COMMENT ON    COLUMN    CPRATEIO.CODCOMPRA IS 'Código da compra.';
COMMENT ON    COLUMN    CPRATEIO.SEQRATEIO IS 'Número sequencial do rateio.';
COMMENT ON    COLUMN    CPRATEIO.CODEMPFN IS 'Código da empresa na tabela FNPLANEJAMENTO';
COMMENT ON    COLUMN    CPRATEIO.CODFILIALFN IS 'Código da filial na tabela FNPLANEJAMENTO.';
COMMENT ON    COLUMN    CPRATEIO.CODPLAN IS 'Código do planejamento.';
COMMENT ON    COLUMN    CPRATEIO.CODEMPCC IS 'Código da empresa na tabela FNCC.';
COMMENT ON    COLUMN    CPRATEIO.CODFILIALCC IS 'Código da filial na tabela FNCC';
COMMENT ON    COLUMN    CPRATEIO.ANOCC IS 'Ano do centro de custo.';
COMMENT ON    COLUMN    CPRATEIO.CODCC IS 'Código do centro de custo.';
COMMENT ON    COLUMN    CPRATEIO.PERCRATEIO IS 'Percentual de rateio.';
COMMENT ON    COLUMN    CPRATEIO.VLRRATEIO IS 'Valor do rateio.';
COMMENT ON    COLUMN    CPRATEIO.SITRATEIO IS 'Situação da conciliação do rateio:
N-Pendente
P-Parcial
T-Total';
COMMENT ON    COLUMN    CPRATEIO.DTINS IS 'Data de inserção.';
COMMENT ON    COLUMN    CPRATEIO.HINS IS 'Hora de inserção.';
COMMENT ON    COLUMN    CPRATEIO.IDUSUINS IS 'ID do usuário que inseriu.';
COMMENT ON    COLUMN    CPRATEIO.DTALT IS 'Data da última alteração.';
COMMENT ON    COLUMN    CPRATEIO.HALT IS 'Hora da última alteração.';
COMMENT ON    COLUMN    CPRATEIO.IDUSUALT IS 'ID do usuário que fez a última alteração.';
COMMENT ON    COLUMN    CPSOLICITACAO.CODEMP IS 'Código da empresa';
COMMENT ON    COLUMN    CPSOLICITACAO.CODFILIAL IS 'Código da filial';
COMMENT ON    COLUMN    CPSOLICITACAO.CODSOL IS 'Código da solicitação';
COMMENT ON    COLUMN    CPSOLICITACAO.DTEMITSOL IS 'Data de emissão da solicitação';
COMMENT ON    COLUMN    CPSOLICITACAO.SITSOL IS 'PE - Pendente
CT - Em Cotação
EF - Finalizada
CA - Cancelada';
COMMENT ON    COLUMN    CPSOLICITACAO.ORIGSOL IS 'Origem da solicitação
RM - RMA
AX - ALMOXARIFE';
COMMENT ON    COLUMN    CPSOLICITACAO.MOTIVOSOL IS 'Motivo de cancelamento.';
COMMENT ON    COLUMN    CPSOLICITACAO.DTINS IS 'Data de inserção';
COMMENT ON    COLUMN    CPSOLICITACAO.HINS IS 'Hora de inserção';
COMMENT ON    COLUMN    CPSOLICITACAO.IDUSUINS IS 'ID do usuário que inseriu';
COMMENT ON    COLUMN    CPSOLICITACAO.DTALT IS 'Data da última alteração';
COMMENT ON    COLUMN    CPSOLICITACAO.HALT IS 'Hora da última alteração';
COMMENT ON    COLUMN    CPSOLICITACAO.IDUSUALT IS 'ID do usuário da última alteração';
COMMENT ON TABLE        CPTIPOFOR IS 'Tabela de tipos de fornecedores.';
COMMENT ON    COLUMN    CPTIPOFOR.RETENCAOINSS IS 'Indica se deve realizar a retenção do inss nos pagamentos (autonomo).';
COMMENT ON    COLUMN    CPTIPOFOR.PERCBASEINSS IS 'Percentual de base de calculo do INSS.';
COMMENT ON    COLUMN    CPTIPOFOR.RETENCAOOUTROS IS 'Indica se deve calcular outras retenções agregadas ao INSS ex.: SEST/SENAT';
COMMENT ON    COLUMN    CPTIPOFOR.PERCRETOUTROS IS 'Percentual de retenção de outros tributos agregados ao inss (SEST/SENAT)';
COMMENT ON    COLUMN    CPTIPOFOR.RETENCAOIRRF IS 'Indica se deve realizar a retenção do imposto de renda nos pagamentos (autonomo).';
COMMENT ON    COLUMN    CPTIPOFOR.PERCBASEIRRF IS 'Percentual de base de calculo do IRRF.';
COMMENT ON    COLUMN    CRCHAMADO.FATOGERADOR IS 'Identificação da ação que gerou o problema.';
COMMENT ON    COLUMN    CRCHAMADO.AMBIENTE IS 'Informações sobre o ambiente onde ocorreu o fato;
';
COMMENT ON    COLUMN    CRCHAMADO.OBSCHAMADO IS 'Outras observações sobre o chamado';
COMMENT ON    COLUMN    CRCHAMADO.PRIORIDADE IS 'Ordem de prioridade:
1 - Muito Alta
2 - Alta
3 - Media
4 - Baixa
5 - Muito Baixa';
COMMENT ON    COLUMN    CRCHAMADO.CODEMPTC IS 'Código da empresa do tipo de chamado';
COMMENT ON    COLUMN    CRCHAMADO.CODFILIALTC IS 'Código da filial do tipo de chamado';
COMMENT ON    COLUMN    CRCHAMADO.CODTPCHAMADO IS 'Código do tipo de chamado.';
COMMENT ON    COLUMN    CRCHAMADO.STATUS IS 'Status do chamado:
PE - Pendente
AN - Em analise
EA - Em andamento
CA - Cancelado
CO - Concluído';
COMMENT ON    COLUMN    CRCHAMADO.DTCHAMADO IS 'Data de abertura do chamado.';
COMMENT ON    COLUMN    CRCHAMADO.DTPREVISAO IS 'Data de previsão do encerramento.';
COMMENT ON    COLUMN    CRCHAMADO.QTDHORASPREVISAO IS 'Quantidade de horas prevista pra execução do chamado.';
COMMENT ON    COLUMN    CRCHAMADO.DTCONCLUSAO IS 'Data de conclusão do chamado';
COMMENT ON    COLUMN    CRCHAMADO.CODEMPAE IS 'Código da empresa do atendente designado.';
COMMENT ON    COLUMN    CRCHAMADO.CODFILIALAE IS 'Codigo da filial do atendente designado.';
COMMENT ON    COLUMN    CRCHAMADO.CODATEND IS 'Código do atendente designado.';
COMMENT ON    COLUMN    CRCHAMADO.CODEMPOS IS 'Código da empresa do item de ordem de serviço.';
COMMENT ON    COLUMN    CRCHAMADO.CODFILIALOS IS 'Código da filial da ordem de serviço.';
COMMENT ON    COLUMN    CRCHAMADO.TICKET IS 'Ticket da ordem de serviço.';
COMMENT ON    COLUMN    CRCHAMADO.CODITRECMERC IS 'Código do ítem de recebimento de mercadorias.';
COMMENT ON    COLUMN    CRCHAMADO.CODITOS IS 'Código do ítem da ordem de serviço.';
COMMENT ON    COLUMN    CRCHAMADO.EMATENDIMENTO IS 'Indica se o chamado está sendo atendido neste momento.';
COMMENT ON    COLUMN    CRCHAMADO.CODEMPQL IS 'Código da empresa da qualificação do chamado';
COMMENT ON    COLUMN    CRCHAMADO.CODFILIALQL IS 'Código da filial da qualificação do chamado.
';
COMMENT ON    COLUMN    CRCHAMADO.CODQUALIFIC IS 'Código da qualificação do atendimento.
';
COMMENT ON    COLUMN    CRCHAMADOANEXO.CODEMP IS 'Código da empresa do anexo';
COMMENT ON    COLUMN    CRCHAMADOANEXO.CODFILIAL IS 'Código da filial do anexo;';
COMMENT ON    COLUMN    CRCHAMADOANEXO.CODCHAMADO IS 'Códiugo do chamado;';
COMMENT ON    COLUMN    CRCHAMADOANEXO.CODANEXO IS 'Código do anexo;';
COMMENT ON    COLUMN    CRCHAMADOANEXO.DESCANEXO IS 'Descrição do anexo;';
COMMENT ON    COLUMN    CRCHAMADOANEXO.ANEXO IS 'Arquivo Binário.
';
COMMENT ON    COLUMN    CRCHAMADOANEXO.DTINS IS 'Data de inserção do registro no banco de dados;';
COMMENT ON    COLUMN    CRCHAMADOANEXO.HINS IS 'Hora de inserção do registro no banco de dados';
COMMENT ON    COLUMN    CRCHAMADOANEXO.IDUSUINS IS 'Usuário que inseriu o registro no banco de dados;';
COMMENT ON    COLUMN    CRCHAMADOANEXO.DTALT IS 'Data de alteração do registro no banco de dados;';
COMMENT ON    COLUMN    CRCHAMADOANEXO.HALT IS 'Hora de alteração do registro no banco de dados;';
COMMENT ON    COLUMN    CRCHAMADOANEXO.IDUSUALT IS 'Usuário que alterou o registro no banco de dados;';
COMMENT ON TABLE        CRQUALIFICACAO IS 'Tabela para qualificação de chamados/atendimentos.';
COMMENT ON    COLUMN    CRQUALIFICACAO.CODEMP IS 'Código da empresa.';
COMMENT ON    COLUMN    CRQUALIFICACAO.CODFILIAL IS 'Código da filial';
COMMENT ON    COLUMN    CRQUALIFICACAO.CODQUALIFIC IS 'Código da qualificação';
COMMENT ON    COLUMN    CRQUALIFICACAO.DESCQUALIFIC IS 'Descrição da qualificação';
COMMENT ON    COLUMN    CRQUALIFICACAO.FINALIZA IS 'Indica se a qualificação finaliza o processo (chamado/atendimento)
"S" - Sim
"N" - Não';
COMMENT ON    COLUMN    CRQUALIFICACAO.DTINS IS 'Data de inserção do registro';
COMMENT ON    COLUMN    CRQUALIFICACAO.HINS IS 'Hora de inserção do registro';
COMMENT ON    COLUMN    CRQUALIFICACAO.IDUSUINS IS 'Identificação do usuário que inseriu o registro';
COMMENT ON    COLUMN    CRQUALIFICACAO.DTALT IS 'Data de altereção do registro';
COMMENT ON    COLUMN    CRQUALIFICACAO.HALT IS 'Hora de alteração do registro';
COMMENT ON    COLUMN    CRQUALIFICACAO.IDUSUALT IS 'Identificação do usuário que alterou o registro';
COMMENT ON TABLE        EQALMOX IS 'Tabela de almoxarifados.';
COMMENT ON TABLE        EQALMOXFILIAL IS 'Amarração Almoxarifado x Filiais.';
COMMENT ON    COLUMN    EQALMOXFILIAL.CODEMP IS 'Código da empresa na tabela EQALMOX.';
COMMENT ON    COLUMN    EQALMOXFILIAL.CODFILIAL IS 'Código da filial na tabela EQALMOX.';
COMMENT ON    COLUMN    EQALMOXFILIAL.CODALMOX IS 'Código do almoxarifado.';
COMMENT ON    COLUMN    EQALMOXFILIAL.CODEMPAF IS 'Código da empresa na tabela SGFILIAL.';
COMMENT ON    COLUMN    EQALMOXFILIAL.CODFILIALAF IS 'Código da filial na tabela SGFILIAL.';
COMMENT ON    COLUMN    EQALMOXFILIAL.BAIXAESTOQAF IS 'Flag que indica se é possível baixar o estoque dessa filial.';
COMMENT ON TABLE        EQCLIFOR IS 'Amarração Cliente x Fornecedor.';
COMMENT ON TABLE        EQCODALTPROD IS 'Tabela de códigos alternativos para produtos.';
COMMENT ON    COLUMN    EQFATCONV.TIPOCONV IS 'Indica o tipo de conversão:
"U" - Apenas converte a unidade no mesmo produto;
"P" - Executa processo de conversão de produtos através de estrutura;';
COMMENT ON    COLUMN    EQFATCONV.CPFATCONV IS 'Flag que indica se a unidade é preferencial para compras.';
COMMENT ON    COLUMN    EQFATCONV.CODPRODET IS 'Código do produto da estrutura de conversão.';
COMMENT ON    COLUMN    EQGRUPO.ESTNEGGRUP IS 'Flag para permitir o estoque negativo de produtos.';
COMMENT ON    COLUMN    EQGRUPO.ESTLOTNEGGRUP IS 'Flag que define se permite estoque negativo de lotes.';
COMMENT ON    COLUMN    EQINVPROD.OBSINVP IS 'Observações relativas ao inventário, como motivo, justificativa, etc.';
COMMENT ON TABLE        EQITRECMERC IS 'Média entre as amostragens.';
COMMENT ON    COLUMN    EQITRECMERC.DESCITRECMERC IS 'Descrição detalhada do item recebido.';
COMMENT ON    COLUMN    EQITRECMERC.NUMSERIE IS 'Número de série.
';
COMMENT ON    COLUMN    EQITRECMERC.GARANTIA IS 'Indica se o produto recebido está na garantia - (S/N)';
COMMENT ON    COLUMN    EQITRECMERC.REFPROD IS 'Referência do produto
';
COMMENT ON    COLUMN    EQITRECMERC.CODEMPTP IS 'Código da empresa do processo de recebimento.';
COMMENT ON    COLUMN    EQITRECMERC.CODFILIALTP IS 'Código da filial do processo de recebimento.';
COMMENT ON    COLUMN    EQITRECMERC.CODTIPORECMERC IS 'Código do tipo de recebimento de mercadorial.';
COMMENT ON    COLUMN    EQITRECMERC.CODPROCRECMERC IS 'Código do processo de recepção de mercadoria.';
COMMENT ON    COLUMN    EQITRECMERC.MEDIAAMOSTRAGEM IS '0';
COMMENT ON    COLUMN    EQITRECMERC.RENDAAMOSTRAGEM IS 'Renda amostragem *';
COMMENT ON    COLUMN    EQITRECMERC.QTDITRECMERC IS 'Quantidade de itens recebidos (Coleta)
';
COMMENT ON    COLUMN    EQITRECMERC.STATUSITRECMERC IS 'Status do processo de recebimento.
PE - Pendente;
AN - Em analise;
EA - Em andamento;
FN - Finalizado;';
COMMENT ON    COLUMN    EQITRECMERC.DEFEITOITRECMERC IS 'Indica o defeito reclamado do item recebido para concerto.
';
COMMENT ON    COLUMN    EQITRECMERC.CONDICOESITRECMERC IS 'Campo para informar as condicoes do item para concerto no momento da recepcao.
';
COMMENT ON    COLUMN    EQITRECMERC.SERVICOSOLICITRECMERC IS 'Campo para informar o serviço solicitado pelo cliente no item recebido.
';
COMMENT ON    COLUMN    EQITRECMERC.OBSITRECMERC IS 'Campo para informar os acessórios que acompanham o item recebido.';
COMMENT ON TABLE        EQITRECMERCITCP IS 'Tabela de vinculo entre ítens de recebimento de mercadoria e itens de compra.';
COMMENT ON TABLE        EQITRECMERCITOS IS 'Tabela para lançamento de componentes e serviços utilizando em ordem de produção /eqitrecmerc.';
COMMENT ON    COLUMN    EQITRECMERCITOS.CODITOS IS 'Código sequencial do item de ordem de serviço.
';
COMMENT ON    COLUMN    EQITRECMERCITOS.QTDITOS IS 'Quantidade de produtos ou servicos';
COMMENT ON    COLUMN    EQITRECMERCITOS.STATUSITOS IS '"PE" - Item pendente / previsto / Orçado
"EC" - Encaminhado / Chamado aberto / RMA aberta
"EA" - Em andamento
"CO" - Item Concluído;';
COMMENT ON    COLUMN    EQITRECMERCITOS.GERARMA IS 'Indica se deve gerar RMA para o ítem de O.S.';
COMMENT ON    COLUMN    EQITRECMERCITOS.GERACHAMADO IS 'Indica se deve gerar chamado para esse item.';
COMMENT ON    COLUMN    EQITRECMERCITOS.GERANOVO IS 'Indica se o item de OS é um produto novo para substituição, 
ao com defeito;';
COMMENT ON    COLUMN    EQITRECMERCITOSITORC.STATUS IS 'Coluna de status para repasse via triggers à tabela de ordem de seviço na atualização do status do orçamento.
(Vide status da ordem de seviço).';
COMMENT ON TABLE        EQITRECMERCSERIE IS 'Tabela de vinculo entre item de recebimento de mercadoria e seus respectivos numeros de serie."';
COMMENT ON TABLE        EQITRMA IS 'Ítens de RMA.';
COMMENT ON    COLUMN    EQITRMA.CODEMPAX IS 'Código da empresa na tabela de almoxarifados.';
COMMENT ON    COLUMN    EQITRMA.CODFILIALAX IS 'Código da filail na tabela de almoxarifados.';
COMMENT ON    COLUMN    EQITRMA.CODALMOX IS 'Código do almoxarifado.';
COMMENT ON    COLUMN    EQITRMA.DTAPROVITRMA IS 'Data da aprovação do item.';
COMMENT ON    COLUMN    EQITRMA.SITITRMA IS 'Situação geral do item de rma:
PE - Pendente 
EA - Em andamento
AF - Aprovação finalizada
EF - Expedição finalizada
CA - Cancelado 
';
COMMENT ON    COLUMN    EQITRMA.SITAPROVITRMA IS 'Situação da aprovação do item:
"PE" - Aprovação pendente
"AP" - Aprovação parcial
"AT" - Aprovação total
"NA" - Não aprovado';
COMMENT ON    COLUMN    EQITRMA.SITEXPITRMA IS 'Situação da expedição do item:
"PE" - Expedição pendente
"EP" - Expedição parcial
"ET" - Expedição total
"NE" - Não expedida';
COMMENT ON    COLUMN    EQITRMA.MOTIVOCANCITRMA IS 'Motivo do cancelamento do ítem de rma.';
COMMENT ON    COLUMN    EQITRMA.PRIORITRMA IS 'Prioridade da RMA.
(B-Baixa, M-Media, A-Alta).
';
COMMENT ON    COLUMN    EQITRMA.MOTIVOPRIORITRMA IS 'Motivo de prioridade para item de RMA.
';
COMMENT ON    COLUMN    EQITRMA.CODEMPOS IS 'Código da empresa da ordem de serviço vinculada.';
COMMENT ON    COLUMN    EQITRMA.CODFILIALOS IS 'Código da filial da ordem de serviço vinculada.';
COMMENT ON    COLUMN    EQITRMA.TICKET IS 'Ticket da ordem de serviço vinculada.';
COMMENT ON    COLUMN    EQITRMA.CODITRECMERC IS 'Item de recebimento da ordem de serviço vinculada';
COMMENT ON    COLUMN    EQITRMA.CODITOS IS 'Item de suplemento da ordem de serviço vinculada.';
COMMENT ON    COLUMN    EQLOTE.QTDPRODLOTE IS 'Quantidade de produtos no lote (nfe - medicamentos)';
COMMENT ON    COLUMN    EQLOTE.DIASAVISOLOTE IS 'Indica quantos dias antes do vencimento do lote deve emitir aviso.';
COMMENT ON TABLE        EQMODLOTE IS 'Tabela de modelos de lotes, para geração automatica de lotes a partir da Ordem de produção.';
COMMENT ON    COLUMN    EQMODLOTE.CODEMP IS 'Código da empresa do modelo de lote.';
COMMENT ON    COLUMN    EQMODLOTE.CODFILIAL IS 'Filial do modelo de lote.';
COMMENT ON    COLUMN    EQMODLOTE.CODMODLOTE IS 'Código do modelo de lote.';
COMMENT ON    COLUMN    EQMODLOTE.DESCMODLOTE IS 'Descrição do modelo de lote.';
COMMENT ON    COLUMN    EQMOVPROD.CODEMPAX IS 'Código da empresa na tabela de almoxarifados.';
COMMENT ON    COLUMN    EQMOVPROD.CODFILIALAX IS 'Código da filial na tabela de almoxarifados.';
COMMENT ON    COLUMN    EQMOVPROD.CODALMOX IS 'Código do almoxarifado.';
COMMENT ON    COLUMN    EQMOVPROD.CODEMPOP IS 'Código da empresa da ordem de produção.';
COMMENT ON    COLUMN    EQMOVPROD.CODFILIALOP IS 'Codigo da filial da Ordem de produção.';
COMMENT ON    COLUMN    EQMOVPROD.CODOP IS 'Código da Ordem de produção.';
COMMENT ON    COLUMN    EQMOVPROD.SEQOP IS 'Número sequencial da OP.';
COMMENT ON    COLUMN    EQMOVPROD.SEQENTOP IS 'Sequencial de entrada da ordem de produção ';
COMMENT ON    COLUMN    EQMOVPROD.SEQSUBPROD IS 'Sequencial de subproduto.
';
COMMENT ON TABLE        EQMOVSERIE IS 'Tabela de controle de movimentações de nemeros de série de produtos.';
COMMENT ON    COLUMN    EQMOVSERIE.CODMOVSERIE IS 'Código da movimentação da série do produto.';
COMMENT ON    COLUMN    EQMOVSERIE.CODPROD IS 'Código do produto.';
COMMENT ON    COLUMN    EQMOVSERIE.NUMSERIE IS 'Número de série.';
COMMENT ON    COLUMN    EQMOVSERIE.CODVENDA IS 'Código da venda';
COMMENT ON    COLUMN    EQMOVSERIE.CODCOMPRA IS 'Código da compra.';
COMMENT ON    COLUMN    EQMOVSERIE.CODINVPROD IS 'Código do inventário.';
COMMENT ON    COLUMN    EQMOVSERIE.DTMOVSERIE IS 'Data da movimentação.';
COMMENT ON    COLUMN    EQMOVSERIE.TIPOMOVSERIE IS 'Quantidade movimentada:
1 - Entrada em estoque;
0 - Sem movimentação de estoque;
-1 - Saída de estoque;';
COMMENT ON    COLUMN    EQMOVSERIE.CODLOTE IS 'Código do lote do produto.
';
COMMENT ON    COLUMN    EQMOVSERIE.CODEMPLE IS 'Código da empresa do lote.
';
COMMENT ON    COLUMN    EQMOVSERIE.CODFILIALLE IS 'Codigo da filial do lote.
';
COMMENT ON    COLUMN    EQMOVSERIE.CODALMOX IS 'Código do almoxarifado.';
COMMENT ON    COLUMN    EQMOVSERIE.CODEMPAX IS 'Código da empresa do almoxarifado.';
COMMENT ON    COLUMN    EQMOVSERIE.CODFILIALAX IS 'Código da filial do almoxarifado.';
COMMENT ON    COLUMN    EQMOVSERIE.CODEMPRC IS 'Código da empresa do recebimento de mercadoria.';
COMMENT ON    COLUMN    EQMOVSERIE.CODFILIALRC IS 'Código da filial do recebimento de mercadoria.
';
COMMENT ON    COLUMN    EQMOVSERIE.TICKET IS 'Código do recebimento de mercadoria.';
COMMENT ON    COLUMN    EQMOVSERIE.CODITRECMERC IS 'Código do ítem de recebimento de mercadoria.';
COMMENT ON    COLUMN    EQPROCRECMERC.TIPOPROCRECMERC IS 'PI - Pesagem inicial
TR - Descarregamento/tiragem de renda
PF - Pesagem final';
COMMENT ON    COLUMN    EQPROCRECMERC.NROAMOSTPROCRECMERC IS 'Indica o numero de amostras necessárias para finalizar o processo.';
COMMENT ON TABLE        EQPRODACESSO IS 'Tabela de controle de acesso do produto para RMA, PDV e outras implementações futuras.';
COMMENT ON    COLUMN    EQPRODACESSO.CODEMP IS 'Código da empresa.';
COMMENT ON    COLUMN    EQPRODACESSO.CODFILIAL IS 'Código da filial.';
COMMENT ON    COLUMN    EQPRODACESSO.CODPROD IS 'Código do produto.';
COMMENT ON    COLUMN    EQPRODACESSO.TIPOPA IS 'Tipo do acesso (RMA, PDV).
';
COMMENT ON    COLUMN    EQPRODACESSO.CODPA IS 'Código do acesso.';
COMMENT ON    COLUMN    EQPRODACESSO.CODEMPCC IS 'Código da empresa na tabela de centro de custos.';
COMMENT ON    COLUMN    EQPRODACESSO.CODFILIALCC IS 'Código da filial na tabela de centro de custos.';
COMMENT ON    COLUMN    EQPRODACESSO.ANOCC IS 'Ano do centro de custo.';
COMMENT ON    COLUMN    EQPRODACESSO.CODCC IS 'Código do centro de custo.';
COMMENT ON    COLUMN    EQPRODACESSO.CODEMPCX IS 'Código da empresa na tabela de caixa ECF.';
COMMENT ON    COLUMN    EQPRODACESSO.CODFILIALCX IS 'Código da filial na tabela de caixa ECF.';
COMMENT ON    COLUMN    EQPRODACESSO.CODCAIXA IS 'Código do caixa ECF.';
COMMENT ON    COLUMN    EQPRODACESSO.DTINS IS 'Data de inserção.';
COMMENT ON    COLUMN    EQPRODACESSO.HINS IS 'Horário de inserção.';
COMMENT ON    COLUMN    EQPRODACESSO.IDUSUINS IS 'ID. do usuário que inseriu o registro.';
COMMENT ON    COLUMN    EQPRODACESSO.DTALT IS 'Data da última alteração.';
COMMENT ON    COLUMN    EQPRODACESSO.HALT IS 'Hora da última alteração.';
COMMENT ON    COLUMN    EQPRODACESSO.IDUSUALT IS 'ID. do usuário que fez a última alteração.';
COMMENT ON TABLE        EQPRODPLAN IS 'Tabela de vínculo produto x planejamento, para uso nos rateios.';
COMMENT ON    COLUMN    EQPRODPLAN.CODEMP IS 'Código da empresa na tabela EQPRODUTO';
COMMENT ON    COLUMN    EQPRODPLAN.CODFILIAL IS 'Código da filial na tabela EQPRODUTO';
COMMENT ON    COLUMN    EQPRODPLAN.CODPROD IS 'Código do produto.';
COMMENT ON    COLUMN    EQPRODPLAN.SEQPP IS 'Número sequencial.';
COMMENT ON    COLUMN    EQPRODPLAN.TIPOPP IS 'Tipo (R-Receita / D-Despesa).';
COMMENT ON    COLUMN    EQPRODPLAN.CODEMPPN IS 'Código da empresa na tabela EQPLANEJAMENTO.';
COMMENT ON    COLUMN    EQPRODPLAN.CODFILIALPN IS 'Código da filial na tabela EQPLANEJAMENTO.';
COMMENT ON    COLUMN    EQPRODPLAN.CODPLAN IS 'Código do planejamento.';
COMMENT ON    COLUMN    EQPRODPLAN.CODEMPCC IS 'Código da empresa na tabela FNCC.';
COMMENT ON    COLUMN    EQPRODPLAN.CODFILIALCC IS 'Código da filial na tabela FNCC.';
COMMENT ON    COLUMN    EQPRODPLAN.ANOCC IS 'Ano do centro de custo.';
COMMENT ON    COLUMN    EQPRODPLAN.CODCC IS 'Código do centro de custo.';
COMMENT ON    COLUMN    EQPRODPLAN.PERCPP IS 'Percentual de rateio.';
COMMENT ON    COLUMN    EQPRODPLAN.DTINS IS 'Data de inserção.';
COMMENT ON    COLUMN    EQPRODPLAN.HINS IS 'Hora de inserção.';
COMMENT ON    COLUMN    EQPRODPLAN.IDUSUINS IS 'ID do usuário que inseriu.';
COMMENT ON    COLUMN    EQPRODPLAN.DTALT IS 'Data da última alteração.';
COMMENT ON    COLUMN    EQPRODPLAN.HALT IS 'Hora da última alteração.';
COMMENT ON    COLUMN    EQPRODPLAN.IDUSUALT IS 'Id do usuário da última alteração.';
COMMENT ON    COLUMN    EQPRODUTO.TIPOPROD IS 'Tipo de produto (campo "fluxo" do cadastro de produtos)
P  - Mercadoria p/revenda
S  - Serviço
E  - Equipamento
F  - Produto acabado
M  - Matéria prima
O  - Ativo imobilizado
C  - Material de Consumo
02 - Embalagem
03 - Em processo
05 - SubProduto
06 - Produto Intermediário
10 - Outros insumos
99 - Outros';
COMMENT ON    COLUMN    EQPRODUTO.CVPROD IS 'Indica se o produto e de compra ou venda:
V - Venda
C - Compra
A - Ambos';
COMMENT ON    COLUMN    EQPRODUTO.CLOTEPROD IS 'Indica se o produto é controlado em lotes.';
COMMENT ON    COLUMN    EQPRODUTO.SERIEPROD IS 'Indica se será exigido o número de série para o produto.';
COMMENT ON    COLUMN    EQPRODUTO.CUSTOINFOPROD IS 'Custo informado do produto. Pode incluir os custos de aquisição, tributos, e rateio de custos fixo.';
COMMENT ON    COLUMN    EQPRODUTO.PRECOBASEPROD IS 'Preço base do produto (primeiro preço de venda) base para geração de outras tabelas de preço.';
COMMENT ON    COLUMN    EQPRODUTO.PRECOCOMISPROD IS 'Preço base do produto para cálculo de comissões especiais.';
COMMENT ON    COLUMN    EQPRODUTO.VERIFPROD IS '"S" - Exige senha para venda abaixo de custo deste produto
"N" - Bloqueado (depende de parametro nas preferencias)
"L" - Liberado sem senha para o produto.';
COMMENT ON    COLUMN    EQPRODUTO.LOCALPROD IS 'Local de armazenamento do produto (Prateleira)
';
COMMENT ON    COLUMN    EQPRODUTO.RMAPROD IS 'Flag que indica se podem ser feitas RMA´s do produto.';
COMMENT ON    COLUMN    EQPRODUTO.CODEMPPE IS 'Código da empresa do prazo de entrega padrão.';
COMMENT ON    COLUMN    EQPRODUTO.CODFILIALPE IS 'Código da filial do prazo de entrega padrão.';
COMMENT ON    COLUMN    EQPRODUTO.CODPE IS 'Código do prazo de entrega.';
COMMENT ON    COLUMN    EQPRODUTO.USARECEITAPROD IS 'Flag indicando se o produto nescessita de receita.';
COMMENT ON    COLUMN    EQPRODUTO.USATELAADICPDV IS 'Flag indicando se o produto abre uma tela para informações adicionais na venda do PDV';
COMMENT ON    COLUMN    EQPRODUTO.VLRDENSIDADE IS 'Valor da densidade';
COMMENT ON    COLUMN    EQPRODUTO.VLRCONCENT IS 'Valor da concentração.';
COMMENT ON    COLUMN    EQPRODUTO.COMPRIMENTO IS 'Comprimento do produto em cm.';
COMMENT ON    COLUMN    EQPRODUTO.LARGURA IS 'Largura do produto em cm.';
COMMENT ON    COLUMN    EQPRODUTO.ESPESSURA IS 'Espessura do produto em cm.';
COMMENT ON    COLUMN    EQPRODUTO.QTDEMBALAGEM IS 'Quantidade unitaria contida na embalagem.';
COMMENT ON    COLUMN    EQPRODUTO.CODEANPROD IS 'GTIN-8,GTIN-12,GTIN-13 ou GTIN-14';
COMMENT ON    COLUMN    EQPRODUTO.CODEMPSC IS 'Código da empresa da seção de produção/estoque.';
COMMENT ON    COLUMN    EQPRODUTO.CODFILIALSC IS 'Código da filial da seção de produção/estoque.';
COMMENT ON    COLUMN    EQPRODUTO.CODSECAO IS 'Código da secão de produção/estoque.';
COMMENT ON    COLUMN    EQPRODUTO.CODEMPTC IS 'Código da empresa do tipo de chamado vinculado ao serviço (Integração CRM/GMS)';
COMMENT ON    COLUMN    EQPRODUTO.CODFILIALTC IS 'Código da filial do tipo de chamado vinculado ao serviço (Integração CRM/GMS)';
COMMENT ON    COLUMN    EQPRODUTO.CODTPCHAMADO IS 'Código do tipo de chamado vinculado ao serviço (Integração CRM/GMS)';
COMMENT ON    COLUMN    EQPRODUTO.QTDHORASSERV IS 'Quantidade de horas padrão para execução do serviço (Integraçao CRM/GMS)';
COMMENT ON    COLUMN    EQPRODUTO.NRODIASVALID IS 'Número de dias para validade do produto.';
COMMENT ON    COLUMN    EQPRODUTO.DESCCLI IS 'Indica se deve aplicar o desconto padrão do cliente.';
COMMENT ON    COLUMN    EQPRODUTO.QTDPORPLANO IS 'Indica a quantidade de produto acabado por plano da folha (industria gráfica/etiquetas)';
COMMENT ON    COLUMN    EQPRODUTO.NROPLANOS IS 'Indica o número de planos por folha (industria gráfica/etiquetas)';
COMMENT ON    COLUMN    EQPRODUTO.CERTFSC IS 'Indica se o produto participa da Certificação FSC.
Forest Stewardship Council (Conselho de Manejo Florestal).
"S" - Sim
"N" - Não
';
COMMENT ON    COLUMN    EQPRODUTO.FATORFSC IS 'Fator para calculo dos relatórios FSC em folhas.
';
COMMENT ON    COLUMN    EQPRODUTOLOG.CODEMP IS 'Código da empresa';
COMMENT ON    COLUMN    EQPRODUTOLOG.CODFILIAL IS 'Código da filial';
COMMENT ON    COLUMN    EQPRODUTOLOG.CODPROD IS 'Código do produto';
COMMENT ON    COLUMN    EQPRODUTOLOG.PRECOBASEPRODANT IS 'Preco base anterior.';
COMMENT ON    COLUMN    EQPRODUTOLOG.PRECOBASEPRODNOVO IS 'Novo preço base.';
COMMENT ON    COLUMN    EQPRODUTOLOG.SEQLOG IS 'Sequência do log';
COMMENT ON    COLUMN    EQPRODUTOLOG.PRECOPROC IS 'Indica se o preço base já foi processado (tabelas de preço)';
COMMENT ON    COLUMN    EQPRODUTOLOG.DTINS IS 'Data de inserção do registro';
COMMENT ON    COLUMN    EQPRODUTOLOG.HINS IS 'Hora de inserção do registro';
COMMENT ON    COLUMN    EQPRODUTOLOG.IDUSUINS IS 'Usuário que inseriu o registro';
COMMENT ON    COLUMN    EQPRODUTOLOG.DTALT IS 'Data de alteração do registro';
COMMENT ON    COLUMN    EQPRODUTOLOG.HALT IS 'Hora de alteração do registro';
COMMENT ON    COLUMN    EQPRODUTOLOG.IDUSUALT IS 'Usuário que alterou o registro.';
COMMENT ON    COLUMN    EQRECAMOSTRAGEM.PESOAMOST IS 'Peso 1 (Pesagem comum)';
COMMENT ON    COLUMN    EQRECAMOSTRAGEM.PESOAMOST2 IS 'Peso 2 (Pesagem em balança hidrostática)';
COMMENT ON    COLUMN    EQRECAMOSTRAGEM.SEQAMOSTRAGEM IS 'Número sequencia para controle das pesagens.';
COMMENT ON TABLE        EQRECMERC IS 'Tabela de registro de informações do recebimento de cargas de mercadorias.';
COMMENT ON    COLUMN    EQRECMERC.TICKET IS 'Ticket de entrada do recebimento ou coleta.';
COMMENT ON    COLUMN    EQRECMERC.REFPROD IS 'Referência do produto';
COMMENT ON    COLUMN    EQRECMERC.CODEMPTR IS 'Código da empresa do tipo de recepção de mercadorias.';
COMMENT ON    COLUMN    EQRECMERC.CODFILIALTR IS 'Código da filial to tipo de recepção de mercadorias.';
COMMENT ON    COLUMN    EQRECMERC.CODTIPORECMERC IS 'Código do tipo de recepção de mercadorias.';
COMMENT ON    COLUMN    EQRECMERC.STATUS IS 'Status do recebimento da mercadoria.
PE - Pendente;
AN - Em análise (OS)
EA - Em andamento (OS)
PT - Pronto (OS)
EO - Em orçamento (entrada para concerto);
CA - Cancelada;
OA - Orçamento aprovado (entrada para concerto);
E1 - Em andamento finalizada primeira etapa;
E2 - Em andamento finalizada segunda etapa;
FN - Finalizado;
PC - Pedido de compra emitido;
NE - Nota de entrada emitida;';
COMMENT ON    COLUMN    EQRECMERC.TIPOFRETE IS 'Tipo de frete:
Na compra:
C - CIF frete por conta sua conta.
F - FOB frete por conta do fornecedor.';
COMMENT ON    COLUMN    EQRECMERC.DTENT IS 'Data da entrada da coleta.';
COMMENT ON    COLUMN    EQRECMERC.DTPREVRET IS 'Data prevista para retorno da mercadoria (concerto)';
COMMENT ON    COLUMN    EQRECMERC.DOCRECMERC IS 'Número do documento (nota de entrada)';
COMMENT ON    COLUMN    EQRECMERC.CODEMPVD IS 'Código da empresa do vendedor (coleta)';
COMMENT ON    COLUMN    EQRECMERC.CODFILIALVD IS 'Código da filial do vendedor (coleta)';
COMMENT ON    COLUMN    EQRECMERC.CODVEND IS 'Código do vendedor (coleta)';
COMMENT ON    COLUMN    EQRECMERC.CODEMPCL IS 'Código da empresa do cliente (coleta)
';
COMMENT ON    COLUMN    EQRECMERC.CODFILIALCL IS 'Código da filial do cliente (coleta)
';
COMMENT ON    COLUMN    EQRECMERC.CODCLI IS 'Código do cliente (coleta)
';
COMMENT ON    COLUMN    EQRECMERC.SOLICITANTE IS 'Nome do solicitante do serviço/entrega da mercadoria.';
COMMENT ON    COLUMN    EQRECMERC.MEDIAAMOSTRAGEM IS 'Media geral da amostragem.';
COMMENT ON    COLUMN    EQRECMERC.RENDAAMOSTRAGEM IS 'Renda media geral da amostragem';
COMMENT ON    COLUMN    EQRECMERC.CODEMPAX IS 'Código da empresa do almoxarifado de descarregamento da mercadoria.';
COMMENT ON    COLUMN    EQRECMERC.CODFILIALAX IS 'Código da filial do almoxarifado de descarregamento da mercadoria.';
COMMENT ON    COLUMN    EQRECMERC.CODALMOX IS 'Código do almoxarifado de descarregamento da mercadoria.';
COMMENT ON    COLUMN    EQRECMERC.OBSRECMERC IS 'Observações.
';
COMMENT ON    COLUMN    EQRECMERC.DESCONTO IS 'Percentual de desconto ao peso total do recebimento da mercadoria.
';
COMMENT ON    COLUMN    EQRMA.MOTIVORMA IS 'Motivo da solicitação.';
COMMENT ON    COLUMN    EQRMA.MOTIVOCANCRMA IS 'Motivo do cancelamento.';
COMMENT ON    COLUMN    EQRMA.SITRMA IS 'Situação geral da rma:
PE - Pendente (não foi realizado nenhum procedimento de aprovação, ou expedição)
EA - Em andamento
AF - Aprovação finalizada
EF - Expedição finalizada
CA - Cancelada (requisição foi cancelada)
';
COMMENT ON    COLUMN    EQRMA.DTAREQRMA IS 'Data da requisição.';
COMMENT ON    COLUMN    EQRMA.DTAEXPRMA IS 'Data da expedição da RMA.';
COMMENT ON    COLUMN    EQRMA.SITAPROVRMA IS 'Status da aprovação da RMA.
"PE" - Aprovação pendente.
"AP" - Aprovação parcial.
"AT" - Aprovação total.
"NA" - Não aprovada.';
COMMENT ON    COLUMN    EQRMA.SITEXPRMA IS 'Status da expedição da RMA.
"PE" - Expedição pendente.
"EP" - Expedida parcial.
"ET" - Expedida total.
"NE" - Não expedida.';
COMMENT ON    COLUMN    EQRMA.IDUSUAPROV IS 'ID do usuário que aprovou/aprovará a RMA.';
COMMENT ON    COLUMN    EQRMA.CODEMPUA IS 'Código da empresa do usuário que aprovou/aprovará a RMA.';
COMMENT ON    COLUMN    EQRMA.CODFILIALUA IS 'Código da filial do usuário que aprovou/aprovará a RMA.';
COMMENT ON    COLUMN    EQRMA.IDUSUEXP IS 'ID do usuário que expediu/expedirá a RMA.';
COMMENT ON    COLUMN    EQRMA.CODEMPUE IS 'Código da empresa do usuário que expediu/expedirá a RMA.';
COMMENT ON    COLUMN    EQRMA.CODFILIALUE IS 'Código da filial do usuário que expediu/expedirá a RMA.';
COMMENT ON    COLUMN    EQRMA.DTAAPROVRMA IS 'Data da aprovação da RMA.';
COMMENT ON    COLUMN    EQRMA.CODEMPOF IS 'Código da empresa a OP/Fase';
COMMENT ON    COLUMN    EQRMA.CODFILIALOF IS 'Código da filial da OP X Fase';
COMMENT ON    COLUMN    EQRMA.CODOP IS 'Código da ordem de produção.';
COMMENT ON    COLUMN    EQRMA.SEQOP IS 'Número sequencial da OP.';
COMMENT ON    COLUMN    EQRMA.SEQOF IS 'Sequencial da fase de produção.';
COMMENT ON    COLUMN    EQRMA.CODEMPCT IS 'Código da empresa do contrato/projeto';
COMMENT ON    COLUMN    EQRMA.CODFILIALCT IS 'Código da filial da empresa/contrato';
COMMENT ON    COLUMN    EQRMA.CODCONTR IS 'Código do contrato/projeto';
COMMENT ON    COLUMN    EQRMA.CODITCONTR IS 'Código do ítem de contrato/projeto';
COMMENT ON    COLUMN    EQRMA.CODEMPOS IS 'Codigo da empresa da ordem de serviço.';
COMMENT ON    COLUMN    EQRMA.CODFILIALOS IS 'Código da filial da ordem de serviço.';
COMMENT ON    COLUMN    EQRMA.TICKET IS 'Número do ticket da ordem de serviço vinculada.';
COMMENT ON    COLUMN    EQRMA.CODITRECMERC IS 'Código do ítem de recebimento de mercadoria (Ordem de serviço)';
COMMENT ON TABLE        EQSALDOLOTE IS 'Tabelas de saldos por lote e almoxarifado.';
COMMENT ON TABLE        EQSECAO IS 'Tabela de seções de produção/estoque.';
COMMENT ON TABLE        EQSERIE IS 'Tabela para controle de números de série de produtos.';
COMMENT ON    COLUMN    EQSERIE.DTFABRICSERIE IS 'Data de fabricação do produto';
COMMENT ON    COLUMN    EQSERIE.DTVALIDSERIE IS 'Data de validade do produto';
COMMENT ON    COLUMN    EQSERIE.OBSSERIE IS 'Observações
';
COMMENT ON    COLUMN    EQTIPOMOV.ESTOQTIPOMOV IS 'Indica se o tipo de movimento movimenta estoque.';
COMMENT ON    COLUMN    EQTIPOMOV.IMPPEDTIPOMOV IS 'Flag que indica se imprime pedido.';
COMMENT ON    COLUMN    EQTIPOMOV.IMPNFTIPOMOV IS 'Flag que indica se imprime NF.';
COMMENT ON    COLUMN    EQTIPOMOV.IMPBOLTIPOMOV IS 'Flag que indica se imprime boleto.';
COMMENT ON    COLUMN    EQTIPOMOV.IMPRECTIPOMOV IS 'Flag que indica se imprime recibo no fechamento da venda.';
COMMENT ON    COLUMN    EQTIPOMOV.REIMPNFTIPOMOV IS 'Flag que indica se reimprime NF.';
COMMENT ON    COLUMN    EQTIPOMOV.TUSUTIPOMOV IS 'Flag que indica se todos os usuários podem utilizar o tipo de movimento.';
COMMENT ON    COLUMN    EQTIPOMOV.SOMAVDTIPOMOV IS 'Indica se as movimentações geradas com esse tipo de movimento devem aparecer nos relatórios de venda.';
COMMENT ON    COLUMN    EQTIPOMOV.SEQNFTIPOMOV IS 'Flag que indica alocação do número da nota fiscal.
';
COMMENT ON    COLUMN    EQTIPOMOV.VLRMFINTIPOMOV IS 'Permite valor financeiro menor que total da nota.';
COMMENT ON    COLUMN    EQTIPOMOV.MCOMISTIPOMOV IS 'Indica se o sistema de comissões é com múltiplos comissionados.';
COMMENT ON    COLUMN    EQTIPOMOV.OPERTIPOMOV IS 'Operação:
"C" - Conjugada;
"P" - Produto;
"S" - Serviço;
';
COMMENT ON    COLUMN    EQTIPOMOV.CTIPOFRETE IS 'flag de preferencia para o tipo de frete na venda com este tipo de movimento.';
COMMENT ON    COLUMN    EQTIPOMOV.CODEMPTN IS 'Código da empresa da transportadora padrão para o tipo de movimento.';
COMMENT ON    COLUMN    EQTIPOMOV.CODFILIALTN IS 'Código da filial da transportadora padrão para o tipo de movimento.';
COMMENT ON    COLUMN    EQTIPOMOV.CODTRAN IS 'Código da transportadora padrão para o tipo de movimento.';
COMMENT ON    COLUMN    EQTIPOMOV.EMITNFCPMOV IS 'Indica se a nota será digitada ou emitida na entrada.';
COMMENT ON    COLUMN    EQTIPOMOV.CODEMPDF IS 'Codigo da empresa do modelo de documento fiscal.';
COMMENT ON    COLUMN    EQTIPOMOV.CODFILIALDF IS 'Codigo da filial do modelo de documento fiscal.';
COMMENT ON    COLUMN    EQTIPOMOV.CODMODDOCFISC IS 'Codigo do modelo do documento fiscal.';
COMMENT ON    COLUMN    EQTIPOMOV.CODEMPPP IS 'Código da empresa para o plano de pagamento preferencial
';
COMMENT ON    COLUMN    EQTIPOMOV.CODFILIALPP IS 'Código da filial para o plano de pagamento preferencial
';
COMMENT ON    COLUMN    EQTIPOMOV.CODPLANOPAG IS 'Código do plano de pagamento preferencial
';
COMMENT ON TABLE        EQTIPOMOVUSU IS 'Tabela de vínculo de tipos de movimento com usuários.';
COMMENT ON    COLUMN    EQTIPOMOVUSU.CODEMP IS 'Código da empresa na tabela de tipos de movimento.';
COMMENT ON    COLUMN    EQTIPOMOVUSU.CODFILIAL IS 'Código da filial na tabela de tipos de movimento.';
COMMENT ON    COLUMN    EQTIPOMOVUSU.CODTIPOMOV IS 'Código do tipo de movimento.';
COMMENT ON    COLUMN    EQTIPOMOVUSU.CODEMPUS IS 'Código de empresa na tabela de usuários.';
COMMENT ON    COLUMN    EQTIPOMOVUSU.CODFILIALUS IS 'Código da filial na tabela de usuários.';
COMMENT ON    COLUMN    EQTIPOMOVUSU.IDUSU IS 'ID do usuário.';
COMMENT ON TABLE        EQTIPORECMERC IS 'Tabela de tipos de recebimento de mercadorias.';
COMMENT ON    COLUMN    EQTIPORECMERC.TIPORECMERC IS 'Tipo de recepção de mercadoria:
"RP" - Recebimento com pesagem;
"CM" - Coleta de materiais;
';
COMMENT ON    COLUMN    EQTIPORECMERC.CODEMPTC IS 'Codigo da empresa do tipo de movimento para compra.';
COMMENT ON    COLUMN    EQTIPORECMERC.CODFILIALTC IS 'Codigo da filial para o tipo de movimento para compra.';
COMMENT ON    COLUMN    EQTIPORECMERC.CODTIPOMOVCP IS 'Codigo do tipo de movimento para compra.';
COMMENT ON    COLUMN    EQTIPORECMERC.CODEMPPD IS 'Código da empresa do produto padrão para recebimento de mercadorias.';
COMMENT ON    COLUMN    EQTIPORECMERC.CODFILIALPD IS 'Código da filial do produto padrão para recebimeno de mercadorias.';
COMMENT ON    COLUMN    EQTIPORECMERC.CODPROD IS 'Código do produto padrão para recebimento de mercadorias.';
COMMENT ON    COLUMN    EQUNIDADE.CASASDEC IS 'Número de casas decimais utilizadas para a unidade.';
COMMENT ON    COLUMN    EQUNIDADE.CALCVOLEMB IS 'Indica se deve calcular o numero de volumes baseado na quantidade por embalagem.';
COMMENT ON TABLE        FNBANCO IS 'Bancos.';
COMMENT ON    COLUMN    FNBANCO.DVBANCO IS 'Dígito verificador do banco.';
COMMENT ON    COLUMN    FNBANCO.LAYOUTCHEQBANCO IS 'Layout de impressão de cheques.';
COMMENT ON    COLUMN    FNBORDERO.STATUSBOR IS 'Status do bordero:
BA - Aberto
BC - Completo
BF - Finalizado';
COMMENT ON TABLE        FNCARTCOB IS 'Carteiras de cobrança.';
COMMENT ON    COLUMN    FNCARTCOB.CODEMPBO IS 'Código da empresa na tabela FNBANCO.';
COMMENT ON    COLUMN    FNCARTCOB.CODFILIALBO IS 'Código da filial na tabela FNBANCO.';
COMMENT ON    COLUMN    FNCARTCOB.CODBANCO IS 'Código do banco.';
COMMENT ON    COLUMN    FNCARTCOB.CODEMP IS 'Código da empresa.';
COMMENT ON    COLUMN    FNCARTCOB.CODFILIAL IS 'Código da filial.';
COMMENT ON    COLUMN    FNCARTCOB.CODCARTCOB IS 'Código da carteira de cobrança.';
COMMENT ON    COLUMN    FNCARTCOB.VARIACAOCARTCOB IS 'Variação da carteira de cobrança.';
COMMENT ON    COLUMN    FNCARTCOB.DESCCARTCOB IS 'Descrição da carteira de cobrança.';
COMMENT ON    COLUMN    FNCARTCOB.CARTCOBCNAB IS 'Carteira de cobrança para CNAB.';
COMMENT ON TABLE        FNCHEQUE IS 'Tabela de cheques.';
COMMENT ON    COLUMN    FNCHEQUE.CODEMP IS 'Código da empresa';
COMMENT ON    COLUMN    FNCHEQUE.CODFILIAL IS 'Código da filial';
COMMENT ON    COLUMN    FNCHEQUE.CODEMPBO IS 'Código empresa na tabela banco.';
COMMENT ON    COLUMN    FNCHEQUE.SEQCHEQ IS 'Sequencia.';
COMMENT ON    COLUMN    FNCHEQUE.CODFILIALBO IS 'Código da filial na tabela banco.';
COMMENT ON    COLUMN    FNCHEQUE.CODBANC IS 'Código do banco.';
COMMENT ON    COLUMN    FNCHEQUE.AGENCIACHEQ IS 'Agência.';
COMMENT ON    COLUMN    FNCHEQUE.CONTACHEQ IS 'Número da conta
';
COMMENT ON    COLUMN    FNCHEQUE.NUMCHEQ IS 'Número do cheque.';
COMMENT ON    COLUMN    FNCHEQUE.NOMEEMITCHEQ IS 'Nome do emitente.';
COMMENT ON    COLUMN    FNCHEQUE.NOMEFAVCHEQ IS 'Nome do favorecido.';
COMMENT ON    COLUMN    FNCHEQUE.DTEMITCHEQ IS 'Emissão';
COMMENT ON    COLUMN    FNCHEQUE.DTVENCTOCHEQ IS 'Data de vencimento.';
COMMENT ON    COLUMN    FNCHEQUE.DTCOMPCHEQ IS 'Data de compensação';
COMMENT ON    COLUMN    FNCHEQUE.TIPOCHEQ IS 'Tipo de cheque.
PF - Pagamento de fornecedor;
RC - Recebimento de cliente.';
COMMENT ON    COLUMN    FNCHEQUE.PREDATCHEQ IS 'Cheque pré-datado (S/N)
';
COMMENT ON    COLUMN    FNCHEQUE.SITCHEQ IS 'Situação do cheque.
CA - Cadastrado;
ED - Emitido;
CD - Compensado;
DV - Devolvido.
';
COMMENT ON    COLUMN    FNCHEQUE.VLRCHEQ IS 'Valor do cheque';
COMMENT ON    COLUMN    FNCHEQUE.HISTCHEQ IS 'Histórico.';
COMMENT ON    COLUMN    FNCHEQUE.DDDFAVCHEQ IS 'DDD do favorecido do cheque';
COMMENT ON    COLUMN    FNCHEQUE.DDDEMITCHEQ IS 'DDD Tel. emitente.
';
COMMENT ON    COLUMN    FNCHEQUE.FONEEMITCHEQ IS 'Telefone emitente.';
COMMENT ON    COLUMN    FNCHEQUE.FONEFAVCHEQ IS 'Telefone do favorecido';
COMMENT ON    COLUMN    FNCHEQUE.DTINS IS 'Data de inserção.';
COMMENT ON    COLUMN    FNCHEQUE.HINS IS 'Hora de inserção.';
COMMENT ON    COLUMN    FNCHEQUE.IDUSUINS IS 'Ident. do usuário que inseriu.';
COMMENT ON    COLUMN    FNCHEQUE.DTALT IS 'Data de alteração.
';
COMMENT ON    COLUMN    FNCHEQUE.HALT IS 'Hora de alteração.';
COMMENT ON    COLUMN    FNCHEQUE.IDUSUALT IS 'Ident. usuário que alterou.';
COMMENT ON    COLUMN    FNCONTA.TIPOCONTA IS 'Indica o tipo de conta:
"C" - Baixa
"B" - Bancos';
COMMENT ON    COLUMN    FNCONTA.CODEMPHP IS 'Código da empresa na tabela de histórico padrão.';
COMMENT ON    COLUMN    FNCONTA.CODFILIALHP IS 'Código da filial na tabela de histórico padrão.';
COMMENT ON    COLUMN    FNCONTA.CODHIST IS 'Código do histórico padrão.';
COMMENT ON    COLUMN    FNCONTA.CODCONTDEB IS 'Código da conta débito contábil.';
COMMENT ON    COLUMN    FNCONTA.CODCONTCRED IS 'Código da conta crédito contábil.';
COMMENT ON    COLUMN    FNCONTA.CONVCOBCONTA IS 'Convênio de cobrança';
COMMENT ON    COLUMN    FNCONTA.ATIVACONTA IS 'Indica se a conta está ativa.';
COMMENT ON    COLUMN    FNCONTA.TIPOCAIXA IS 'Indica o tipo de caixa
"F" - Físico
"P" - Previsionamentos';
COMMENT ON TABLE        FNCONTAVINCULADA IS 'Tabela de vinculo entre contas para composisão de saldo interno/externo';
COMMENT ON    COLUMN    FNCONTAVINCULADA.CODEMPCV IS 'Código da empresa da conta vinculada';
COMMENT ON    COLUMN    FNCONTAVINCULADA.CODFILIALCV IS 'Código da filial da conta vinculada.';
COMMENT ON    COLUMN    FNCONTAVINCULADA.NUMCONTACV IS 'Número da conta vinculada.';
COMMENT ON    COLUMN    FNCONTAVINCULADA.CONTACHEQUE IS 'Indica se a conta é para controle de compensação de cheques.';
COMMENT ON    COLUMN    FNFBNCLI.STIPOFEBRABAN IS 'Sub-Tipo Febraban 01=Débito em folha / 02=Débito em conta corrente.';
COMMENT ON    COLUMN    FNFBNCODRET.TIPORET IS 'Indica o tipo de ocorrência:
RE - Rejeição de entrada;
CE - Confirmação de entrada;
AD - Advertência;
CB - Confirmação de baixa;
RB - Rejeição de baixa;
IN - Indefinido;
';
COMMENT ON    COLUMN    FNFBNREC.STIPOFEBRABAN IS 'Sub-Tipo Febraban 01=Débito em folha / 02=Débito em conta corrente.';
COMMENT ON    COLUMN    FNFBNREC.SITREMESSA IS 'Situação da remessa 00=Selecionado / 01=Exportada';
COMMENT ON    COLUMN    FNFBNREC.SITRETORNO IS 'Situação do retorno. 00=Sem retorno.';
COMMENT ON    COLUMN    FNFBNREC.NOMEARQUIVO IS 'Nome do arquivo de remessa em que o registro foi exportado.
';
COMMENT ON TABLE        FNFINALIDADE IS 'Classificação de finalidade das contas financeiras.';
COMMENT ON    COLUMN    FNFINALIDADE.CODFIN IS 'Código da finalidade.';
COMMENT ON    COLUMN    FNFINALIDADE.DESCFIN IS 'Descrição da finalidade.';
COMMENT ON    COLUMN    FNFINALIDADE.ESFIN IS 'Define se é entrada ou saída (E/S).';
COMMENT ON    COLUMN    FNFINALIDADE.CLASFIN IS 'Classificação operacional ou não operacional (O/N).';
COMMENT ON TABLE        FNHISTPAD IS 'Tabela de histórico padrão.';
COMMENT ON    COLUMN    FNHISTPAD.TXAHISTPAD IS 'Campo para criação de modelo dinâmico de histórico padrão.';
COMMENT ON TABLE        FNITMODBOLETO IS 'Tabela de amarração modelo de boleto e banco';
COMMENT ON    COLUMN    FNITMODBOLETO.CODEMP IS 'Código da empresa';
COMMENT ON    COLUMN    FNITMODBOLETO.CODFILIAL IS 'Código filial.';
COMMENT ON    COLUMN    FNITMODBOLETO.CODMODBOL IS 'Código do modelo de boleto.';
COMMENT ON    COLUMN    FNITMODBOLETO.CODEMPBO IS 'Código da empresa na tabela FNBANCO.';
COMMENT ON    COLUMN    FNITMODBOLETO.CODFILIALBO IS 'Código da filial na tabela FNBANCO.';
COMMENT ON    COLUMN    FNITMODBOLETO.CODBANCO IS 'Código do banco.';
COMMENT ON    COLUMN    FNITMODBOLETO.CODEMPCB IS 'Código da empresa na tabela FNCARTCOB.';
COMMENT ON    COLUMN    FNITMODBOLETO.CODFILIALCB IS 'Código da filial na tabela FNCARTCOB.';
COMMENT ON    COLUMN    FNITMODBOLETO.CODCARTCOB IS 'Código da carteira de cobrança.';
COMMENT ON    COLUMN    FNITMODBOLETO.CONVCOB IS 'Convênio de cobrança.';
COMMENT ON    COLUMN    FNITMODBOLETO.DVCONVCOB IS 'Dígito do codigo da empresa.';
COMMENT ON    COLUMN    FNITMODBOLETO.SEQNOSSONUMERO IS 'Sequencia do nosso numero, para geração de nosso numero no padrão "S" SGPREFER1.TPNOSSONUMERO.';
COMMENT ON    COLUMN    FNITPAGAR.VLRDEVITPAG IS 'Valor das devoluções de compra da parcela.';
COMMENT ON    COLUMN    FNITPAGAR.DTCOMPITPAG IS 'Data de competência.';
COMMENT ON    COLUMN    FNITPAGAR.STATUSITPAG IS 'P1 - Pendente
PL - Pagamento parcial
PP - Pagamento total
CP - Cancelado';
COMMENT ON    COLUMN    FNITPAGAR.CODEMPSN IS 'Código da empresa do sinalizador.
';
COMMENT ON    COLUMN    FNITPAGAR.CODFILIALSN IS 'Código da filial do sinalizador.
';
COMMENT ON    COLUMN    FNITPAGAR.CODSINAL IS 'Código do sinalizador.
';
COMMENT ON    COLUMN    FNITRECEBER.VLRDESCITREC IS 'Valor do desconto.';
COMMENT ON    COLUMN    FNITRECEBER.VLRMULTAITREC IS 'Valor da multa.';
COMMENT ON    COLUMN    FNITRECEBER.VLRJUROSITREC IS 'Valor dos juros.';
COMMENT ON    COLUMN    FNITRECEBER.VLRDEVITREC IS 'Valor de devolução da parcela.';
COMMENT ON    COLUMN    FNITRECEBER.VLRPARCITREC IS 'Valor original da parcela.';
COMMENT ON    COLUMN    FNITRECEBER.VLRPAGOITREC IS 'Valor pago da parcela.';
COMMENT ON    COLUMN    FNITRECEBER.VLRAPAGITREC IS 'Valor a pagar.';
COMMENT ON    COLUMN    FNITRECEBER.VLRCOMIITREC IS 'Valor de comissão por parcela.';
COMMENT ON    COLUMN    FNITRECEBER.VLRBASECOMIS IS 'Valor base para comissionamento especial';
COMMENT ON    COLUMN    FNITRECEBER.DESCPONT IS 'Indica se o desconto e do tipo pontualidade.';
COMMENT ON    COLUMN    FNITRECEBER.DTCOMPITREC IS 'Data de competência.
';
COMMENT ON    COLUMN    FNITRECEBER.DTPREVITREC IS 'Data prevista para o recebimento.';
COMMENT ON    COLUMN    FNITRECEBER.DTPAGOITREC IS 'Data de pagamento ou data de compensação.';
COMMENT ON    COLUMN    FNITRECEBER.DTLIQITREC IS 'Data de liquidação do título.
';
COMMENT ON    COLUMN    FNITRECEBER.STATUSITREC IS 'R1 (Conta em aberto)
RP (Recebimento total)
RB (Em Bordero)
RL (Recebimento parcial)
RN (Recebimento Renegociado) 
RR (Recebimento em Renegociacao)';
COMMENT ON    COLUMN    FNITRECEBER.CODEMPTC IS 'Código da filial em FNTIPOCOB.';
COMMENT ON    COLUMN    FNITRECEBER.CODFILIALTC IS 'Código filial em FNTIPOCOB.';
COMMENT ON    COLUMN    FNITRECEBER.CODTIPOCOB IS 'Código do tipo de cobrança.';
COMMENT ON    COLUMN    FNITRECEBER.CODEMPCB IS 'Código da empresa na tabela FNCARTCOB.';
COMMENT ON    COLUMN    FNITRECEBER.CODFILIALCB IS 'Código filial na tabela FNCARTCOB.';
COMMENT ON    COLUMN    FNITRECEBER.CODCARTCOB IS 'Código da carteira de cobrança.';
COMMENT ON    COLUMN    FNITRECEBER.IMPRECIBOITREC IS 'Flag que indica se será impresso recibo.';
COMMENT ON    COLUMN    FNITRECEBER.RECIBOITREC IS 'Armazena o número do recibo.';
COMMENT ON    COLUMN    FNITRECEBER.ALTUSUITREC IS 'Flag que indica se está sendo feita alteração de item de contas a receber pelo usuário.';
COMMENT ON    COLUMN    FNITRECEBER.PDVITREC IS 'Indica se foi recebido pelo PDV.';
COMMENT ON    COLUMN    FNITRECEBER.RECEMCOB IS 'Definie se recebimento está em processo de cobrança';
COMMENT ON    COLUMN    FNITRECEBER.DTINIEMCOB IS 'Data de inicio de cobrança';
COMMENT ON    COLUMN    FNITRECEBER.DTFIMEMCOB IS 'Fim do processo de cobrança';
COMMENT ON    COLUMN    FNITRECEBER.SEQNOSSONUMERO IS 'Sequencial do nosso número.';
COMMENT ON    COLUMN    FNITRECEBER.NOSSONUMERO IS 'Nosso número (utilizado para armazenar o nosso número quando gerado pelo banco e retornado através do CNAB)
';
COMMENT ON    COLUMN    FNITRECEBER.EMMANUT IS 'Coloca registro em estado de manutenção.
';
COMMENT ON TABLE        FNITTBJUROS IS 'Itens das tabelas de juros.
';
COMMENT ON    COLUMN    FNLANCA.DTCOMPLANCA IS 'Data de competência.';
COMMENT ON    COLUMN    FNLANCA.HISTBLANCA IS 'Histórico bancário.';
COMMENT ON    COLUMN    FNLANCA.PDVITREC IS 'Indica se foi recebido pelo PDV';
COMMENT ON    COLUMN    FNLANCA.CODCLI IS 'Código do cliente, para vínculo com contas a receber.';
COMMENT ON    COLUMN    FNLANCA.CODEMPCL IS 'Código da empresa do cliente';
COMMENT ON    COLUMN    FNLANCA.CODFILIALCL IS 'Código da filial do cliente.';
COMMENT ON    COLUMN    FNLANCA.EMMANUT IS 'Estado de manutenção (S/N).
';
COMMENT ON    COLUMN    FNLIBCRED.VLRVENDACRED IS 'Valor da venda no momento da liberação de crédito.';
COMMENT ON    COLUMN    FNMODBOLETO.PREIMPMODBOL IS 'Flag para indicar se usa boleto pre-impresso.[ S ]';
COMMENT ON    COLUMN    FNMODBOLETO.CARTCOB IS 'Carteira de cobrança.';
COMMENT ON    COLUMN    FNMODBOLETO.MDECOB IS 'Modalidade de cobrança.';
COMMENT ON    COLUMN    FNMODBOLETO.ACEITEMODBOL IS 'Flag indicador para aceitação do boleto pelo cliente.';
COMMENT ON    COLUMN    FNMODBOLETO.DESCLPMODBOL IS 'Descrição do local de pagamento do boleto.';
COMMENT ON    COLUMN    FNMODBOLETO.INSTPAGMODBOL IS 'Instruções para o pagamento do boleto.';
COMMENT ON    COLUMN    FNMODBOLETO.IMPINFOPARC IS 'Indica se deve imprimir informações da parcela nas intruções de cobrança para boletos gráficos.';
COMMENT ON    COLUMN    FNMOEDA.CODFBNMOEDA IS 'Código da moeda padrão Febraban.';
COMMENT ON    COLUMN    FNMOVIMENTO.TIPOMOV IS 'Tipo de movimento:
"R" - Receber
"P" - Pagar';
COMMENT ON    COLUMN    FNMOVIMENTO.SITMOV IS 'Situação do movimento:
"E" - Edição
"B" - Baixa
"D" - Devolução
"C" - Cancelada
"P" - Pagar';
COMMENT ON    COLUMN    FNMOVIMENTO.STATUSOLD IS 'Status antigo.';
COMMENT ON    COLUMN    FNPAGAR.VLRDESCPAG IS 'Valor dos descontos. (Deve ser incluidos as retenções se houverem)';
COMMENT ON    COLUMN    FNPAGAR.VLRDEVPAG IS 'Valor das devoluções de compra.';
COMMENT ON    COLUMN    FNPAGAR.DTCOMPPAG IS 'Data de competência.';
COMMENT ON    COLUMN    FNPAGAR.VLRBASEIRRF IS 'Valor da base de calculo do IRRF (Pagamento de autonomos)';
COMMENT ON    COLUMN    FNPAGAR.VLRBASEINSS IS 'Valor da base de calculo do INSS (Pagamento de autonomos)';
COMMENT ON    COLUMN    FNPAGAR.VLRRETIRRF IS 'Valor de desconto/retenção do IRRF (Pagamento de autonomos)';
COMMENT ON    COLUMN    FNPAGAR.VLRRETINSS IS 'Valor do desconto/retenção do INSS (Pagamento de autonomos)';
COMMENT ON    COLUMN    FNPAGAR.CODEMPPN IS 'Código da empresa do planejamento financeiro.';
COMMENT ON    COLUMN    FNPAGAR.CODFILIALPN IS 'Código da filial do planejamento financeiro.';
COMMENT ON    COLUMN    FNPAGAR.CODPLAN IS 'Código do planejamento financeiro.';
COMMENT ON    COLUMN    FNPAGAR.CODEMPCC IS 'Código da empresa do centro de custo.';
COMMENT ON    COLUMN    FNPAGAR.CODFILIALCC IS 'Código da filial do centro de custo.';
COMMENT ON    COLUMN    FNPAGAR.ANOCC IS 'Ano do centro de custo.';
COMMENT ON    COLUMN    FNPAGAR.CODCC IS 'Código do centro de custo.';
COMMENT ON    COLUMN    FNPAGCHEQ.CODEMP IS 'Código da empresa na tabela FNPAGAR.';
COMMENT ON    COLUMN    FNPAGCHEQ.CODFILIAL IS 'Código da filial na tabela FNPAGAR.';
COMMENT ON    COLUMN    FNPAGCHEQ.CODPAG IS 'Código do pagamento.';
COMMENT ON    COLUMN    FNPAGCHEQ.NPARCPAG IS 'Número sequencial.';
COMMENT ON    COLUMN    FNPAGCHEQ.CODEMPCH IS 'Código da empresa na tabela FNCHEQUE.';
COMMENT ON    COLUMN    FNPAGCHEQ.CODFILIALCH IS 'Código filial na tabela FNCHEQUE.';
COMMENT ON    COLUMN    FNPAGCHEQ.SEQCHEQ IS 'Sequencia de cheque.';
COMMENT ON    COLUMN    FNPAGCHEQ.BAIXA IS 'Indica se deve realizar a baixa do titulo vinculado (gatilho cheque emitido)
S - Sim;
N - Não;
';
COMMENT ON    COLUMN    FNPAGCHEQ.TRANSFERE IS 'Indica se deve transferir o titulo vinculado da conta de cheques para conta real (gatilho cheque compensado)
S - Sim;
N - Não;';
COMMENT ON    COLUMN    FNPAGCHEQ.DTINS IS 'Data de inserção.';
COMMENT ON    COLUMN    FNPAGCHEQ.HINS IS 'Hora de inserção.';
COMMENT ON    COLUMN    FNPAGCHEQ.IDUSUINS IS 'Id. do usuário que inseriu.';
COMMENT ON    COLUMN    FNPAGCHEQ.DTALT IS 'Data de alteração.';
COMMENT ON    COLUMN    FNPAGCHEQ.HALT IS 'Hora de alteração.';
COMMENT ON    COLUMN    FNPAGCHEQ.IDUSUALT IS 'Id. do usuário que alterou.';
COMMENT ON    COLUMN    FNPAGTOCOMI.DTCOMPPCOMI IS 'Data de competência.';
COMMENT ON    COLUMN    FNPAGTOCOMI.EMMANUT IS 'Estado de manutenção (S/N).';
COMMENT ON    COLUMN    FNPARCPAG.AUTOBAIXAPARC IS 'Flag para marcar baixa automática da parcela.';
COMMENT ON    COLUMN    FNPLANEJAMENTO.CODREDPLAN IS 'Código reduzido';
COMMENT ON    COLUMN    FNPLANEJAMENTO.FINPLAN IS 'Finalidade do planejamento.';
COMMENT ON    COLUMN    FNPLANEJAMENTO.COMPSLDCXPLAN IS 'Flag indicando se compõe saldo de caixa.';
COMMENT ON    COLUMN    FNPLANEJAMENTO.CODCONTCRED IS 'Código da conta crédito para contabilidade.';
COMMENT ON    COLUMN    FNPLANEJAMENTO.CODCONTDEB IS 'Código da conta débido para contabilidade.';
COMMENT ON    COLUMN    FNPLANEJAMENTO.ESFINPLAN IS 'Natureza da categoria E-Entrada / S-Saída';
COMMENT ON    COLUMN    FNPLANEJAMENTO.CLASFINPLAN IS 'Classificação da categoria O-Operacional / N-Não Operacional.';
COMMENT ON    COLUMN    FNPLANOPAG.APORCPLANOPAG IS 'Flag que indica se orçamento será aprovado por padrão na tela de fechamento de orçamento.';
COMMENT ON    COLUMN    FNPLANOPAG.ATIVOPLANOPAG IS 'Flag que indica se o plano de pagamento está ativo "S" ou inativo "N".';
COMMENT ON    COLUMN    FNPLANOPAG.CVPLANOPAG IS 'Indica se o plano de pagamento é para compra "C", venda "V" ou ambos "A", deve ser usado como filtro nas telas correspondentes.';
COMMENT ON    COLUMN    FNPLANOPAG.CODEMPTBJ IS 'Código da empresa da tabela de juros.';
COMMENT ON    COLUMN    FNPLANOPAG.CODFILIALTBJ IS 'Código da filial da tabela de juros.';
COMMENT ON    COLUMN    FNPLANOPAG.CODTBJ IS 'Código da tabela de juros.';
COMMENT ON    COLUMN    FNPLANOPAG.PERCDESC IS 'Percentual de desconto.';
COMMENT ON    COLUMN    FNPLANOPAG.REGRVCTOPLANOPAG IS 'Regra de para vencimento.
N - Nenhuma
A - Antecipar
P - Prorrogar';
COMMENT ON    COLUMN    FNPLANOPAG.RVSABPLANOPAG IS 'Define se a regra de vencimento vale para os sábados.';
COMMENT ON    COLUMN    FNPLANOPAG.RVDOMPLANOPAG IS 'Define se a regra de vencimento vale para os domingos.';
COMMENT ON    COLUMN    FNPLANOPAG.RVFERPLANOPAG IS 'Define se a regra de vencimento vale para os feriados.';
COMMENT ON    COLUMN    FNPLANOPAG.RVDIAPLANOPAG IS 'Regra do dia de vencimento
A - Automático
F - Dia fixo
U - Dia util
';
COMMENT ON    COLUMN    FNPLANOPAG.DIAVCTOPLANOPAG IS 'Dia fixo ou útil para o vencimento.';
COMMENT ON    COLUMN    FNRECEBER.CODEMPTC IS 'Código da empresa em FNTIPOCOB.';
COMMENT ON    COLUMN    FNRECEBER.CODFILIALTC IS 'Código da filial em FNTIPOCOB.';
COMMENT ON    COLUMN    FNRECEBER.CODTIPOCOB IS 'Código do tipo de cobrança.';
COMMENT ON    COLUMN    FNRECEBER.CODEMPCB IS 'Código da empresa na tabela FNCARTCOB.';
COMMENT ON    COLUMN    FNRECEBER.CODFILIALCB IS 'Código da filial na tabela FNCARTCOB.';
COMMENT ON    COLUMN    FNRECEBER.CODCARTCOB IS 'Código da carteira de cobrança.';
COMMENT ON    COLUMN    FNRECEBER.VLRDEVREC IS 'Valor total das devoluções.';
COMMENT ON    COLUMN    FNRECEBER.VLRBASECOMIS IS 'Valor da base de calculo de comissionamento especial';
COMMENT ON    COLUMN    FNRECEBER.VLRRETENSAOISS IS 'VALOR DO ISS RETIDO PELO CLIENTE.';
COMMENT ON    COLUMN    FNRECEBER.DTCOMPREC IS 'Data de competência.';
COMMENT ON    COLUMN    FNRECEBER.CODEMPPN IS 'Código da empresa do planejamento financeiro.';
COMMENT ON    COLUMN    FNRECEBER.CODFILIALPN IS 'Código da filial do planejamento financeiro.';
COMMENT ON    COLUMN    FNRECEBER.CODPLAN IS 'Código do planejamento financeiro.';
COMMENT ON    COLUMN    FNRECEBER.CODEMPCC IS 'Código da empresa do centro de custo.';
COMMENT ON    COLUMN    FNRECEBER.CODFILIALCC IS 'Código da filial do centro de custo.';
COMMENT ON    COLUMN    FNRECEBER.ANOCC IS 'Ano do centro de custo.';
COMMENT ON    COLUMN    FNRECEBER.CODCC IS 'Código do centro de custo.';
COMMENT ON    COLUMN    FNRECEBER.CODEMPRR IS 'Código da empresa da renegociação.';
COMMENT ON    COLUMN    FNRECEBER.CODFILIALRR IS 'Código da filial da renegociação.';
COMMENT ON    COLUMN    FNRECEBER.CODRENEGREC IS 'Código da renegociação.';
COMMENT ON    COLUMN    FNRECEBER.EMMANUT IS 'Estado de manutenção (S/N).';
COMMENT ON TABLE        FNRENEGREC IS 'Tabela para gerenciamento de títulos renegociados.';
COMMENT ON TABLE        FNRESTRICAO IS 'Tabela de restrições dos clientes.';
COMMENT ON    COLUMN    FNRESTRICAO.SITRESTR IS 'Situaçao da restrição 
I - Inclusão
C - Cancelada';
COMMENT ON    COLUMN    FNSALDOLANCA.FECHADO IS 'Indica se o caixa foi fechado para este dia, se "S" não deverá permitir lançamentos para esta data.';
COMMENT ON    COLUMN    FNSALDOLANCA.EMMANUT IS 'Indica se o registro está em processo de manutenção.';
COMMENT ON TABLE        FNSINAL IS 'Tabela de sinalização de lançamentos.';
COMMENT ON    COLUMN    FNSINAL.CODEMP IS 'Código da empresa.';
COMMENT ON    COLUMN    FNSINAL.CODFILIAL IS 'Código da filial.';
COMMENT ON    COLUMN    FNSINAL.CODSINAL IS 'Código de sinalização';
COMMENT ON    COLUMN    FNSINAL.DESCSINAL IS 'Descrição da sinalização';
COMMENT ON    COLUMN    FNSINAL.CORSINAL IS 'Cor da sinalização';
COMMENT ON    COLUMN    FNSINAL.DTINS IS 'Data de inserção';
COMMENT ON    COLUMN    FNSINAL.HINS IS 'Hora da inserção';
COMMENT ON    COLUMN    FNSINAL.IDUSUINS IS 'ID do usuário que inseriu';
COMMENT ON    COLUMN    FNSINAL.DTALT IS 'Data da últ. alteração';
COMMENT ON    COLUMN    FNSINAL.HALT IS 'Hora da últ. alteração';
COMMENT ON    COLUMN    FNSINAL.IDUSUALT IS 'ID do usuário que alterou';
COMMENT ON    COLUMN    FNSUBLANCA.DTCOMPSUBLANCA IS 'Data de competência.';
COMMENT ON    COLUMN    FNSUBLANCA.TIPOSUBLANCA IS 'Tipo de lançamento (P-Padrão, D-Desconto, J-Juros, M-Multa)';
COMMENT ON    COLUMN    FNSUBLANCA.CODEMPCT IS 'Código da empresa do contrato/projeto';
COMMENT ON    COLUMN    FNSUBLANCA.CODFILIALCT IS 'Código da filial da empresa/contrato';
COMMENT ON    COLUMN    FNSUBLANCA.CODCONTR IS 'Código do contrato/projeto';
COMMENT ON    COLUMN    FNSUBLANCA.CODITCONTR IS 'Código do ítem de contrato/projeto';
COMMENT ON    COLUMN    FNSUBLANCA.EMMANUT IS 'Estado de manutenção (S/N).';
COMMENT ON TABLE        FNTALAOCHEQ IS 'Tabela de talonário de cheques.';
COMMENT ON    COLUMN    FNTALAOCHEQ.CODEMP IS 'Código da empresa';
COMMENT ON    COLUMN    FNTALAOCHEQ.CODFILIAL IS 'Código da filial.';
COMMENT ON    COLUMN    FNTALAOCHEQ.NUMCONTA IS 'Número da conta';
COMMENT ON    COLUMN    FNTALAOCHEQ.SEQTALAO IS 'Sequencia do talão.';
COMMENT ON    COLUMN    FNTALAOCHEQ.DTTALAO IS 'Data do talão.';
COMMENT ON    COLUMN    FNTALAOCHEQ.CHEQINITALAO IS 'Número do cheque inicial.';
COMMENT ON    COLUMN    FNTALAOCHEQ.CHEQFIMTALAO IS 'Número final do cheque.';
COMMENT ON    COLUMN    FNTALAOCHEQ.CHEQATUALTALAO IS 'Último cheque impresso.';
COMMENT ON    COLUMN    FNTALAOCHEQ.ATIVOTALAO IS 'Talonário ativo (S/N).';
COMMENT ON TABLE        FNTBJUROS IS 'Tabela de juros financeiros
';
COMMENT ON    COLUMN    FNTBJUROS.TIPOTBJ IS 'Tipo da tabela de juros: 
"D" = Diário
"M" = Mensal
"B" = Bimestral
"T" = Trimestral
"S" = Semestral
"A" = Anual
"F" = Fixo';
COMMENT ON    COLUMN    FNTIPOCOB.TIPOFEBRABAN IS 'Define se é febraban. 00=não / 01-SIACC / 02-CNAB';
COMMENT ON    COLUMN    FNTIPOCOB.TIPOSPED IS 'Indicador do tipo de título de crédito para o SPED:
00 - Duplicata
01 - Cheque
02 - Promissória
03 - recibo
';
COMMENT ON    COLUMN    FNTIPOCOB.OBRIGCARTCOB IS 'Define se é obrigatório carteira de cobrança.';
COMMENT ON    COLUMN    FNTIPOCOB.CODEMPCT IS 'Código da empresa na tabela de contas.
';
COMMENT ON    COLUMN    FNTIPOCOB.CODFILIALCT IS 'Código da filial na tabela de contas.';
COMMENT ON    COLUMN    FNTIPOCOB.NUMCONTA IS 'Número da conta para impressão de cheques.
';
COMMENT ON    COLUMN    FNTIPOCOB.SEQTALAO IS 'Sequencia do talonário.';
COMMENT ON    COLUMN    FNTIPOCOB.NRODIASCOMP IS 'Numero de dias para compensação do valor.';
COMMENT ON TABLE        FNTIPORESTR IS 'Tipos de restrições.';
COMMENT ON    COLUMN    FNTIPORESTR.BLOQTPRESTR IS 'Define se a restrição bloqueia os lançamentos.';
COMMENT ON    COLUMN    LFCLFISCAL.TIPOFISC IS 'Campo inutilizado (mantido por retrocompatibilidade)';
COMMENT ON    COLUMN    LFCLFISCAL.TPREDICMSFISC IS 'Campo inutilizado (mantido por retrocompatibilidade)';
COMMENT ON    COLUMN    LFCLFISCAL.ALIQFISC IS 'Campo inutilizado (mantido por retrocompatibilidade)';
COMMENT ON    COLUMN    LFCLFISCAL.ALIQLFISC IS 'Campo inutilizado (mantido por retrocompatibilidade)';
COMMENT ON    COLUMN    LFCLFISCAL.REDFISC IS 'Campo inutilizado (mantido por retrocompatibilidade)';
COMMENT ON    COLUMN    LFCLFISCAL.ALIQIPIFISC IS 'Campo inutilizado (mantido por retrocompatibilidade)';
COMMENT ON    COLUMN    LFCLFISCAL.ORIGFISC IS 'Campo inutilizado (mantido por retrocompatibilidade)';
COMMENT ON    COLUMN    LFCLFISCAL.CODEMPTT IS 'Campo inutilizado (mantido por retrocompatibilidade)';
COMMENT ON    COLUMN    LFCLFISCAL.CODFILIALTT IS 'Campo inutilizado (mantido por retrocompatibilidade)';
COMMENT ON    COLUMN    LFCLFISCAL.CODTRATTRIB IS 'Campo inutilizado (mantido por retrocompatibilidade)';
COMMENT ON    COLUMN    LFCLFISCAL.CODEMPME IS 'Campo inutilizado (mantido por retrocompatibilidade)';
COMMENT ON    COLUMN    LFCLFISCAL.CODFILIALME IS 'Campo inutilizado (mantido por retrocompatibilidade)';
COMMENT ON    COLUMN    LFCLFISCAL.CODMENS IS 'Campo inutilizado (mantido por retrocompatibilidade)';
COMMENT ON    COLUMN    LFCLFISCAL.SITPISFISC IS 'Campo inutilizado (mantido por retrocompatibilidade)';
COMMENT ON    COLUMN    LFCLFISCAL.SITCOFINSFISC IS 'Campo inutilizado (mantido por retrocompatibilidade)';
COMMENT ON    COLUMN    LFCLFISCAL.TIPOST IS 'Campo inutilizado (mantido por retrocompatibilidade)';
COMMENT ON    COLUMN    LFCLFISCAL.MARGEMVLAGR IS 'Campo inutilizado (mantido por retrocompatibilidade)';
COMMENT ON    COLUMN    LFCLFISCAL.CODSERV IS 'Código do serviço (tabela de serviços)';
COMMENT ON TABLE        LFCSOSN IS 'Tabela de situação da operação no simples nacional.';
COMMENT ON    COLUMN    LFCSOSN.CSOSN IS 'Código de situação da operação no simples nacional.';
COMMENT ON    COLUMN    LFCSOSN.DESCCSOSN IS 'Descrição da situação da operação no simples nacional.';
COMMENT ON TABLE        LFFRETE IS 'Tabela para lançamento de conhecimentos de frete.';
COMMENT ON    COLUMN    LFFRETE.CODNAT IS 'Natureza da operação.';
COMMENT ON    COLUMN    LFFRETE.TIPOFRETE IS 'Modalidade do frete "1" CIF ou "2" FOB.';
COMMENT ON    COLUMN    LFFRETE.TIPOPGTO IS 'Tipo de pagamento do frete "P" pago ou "A" a pagar.';
COMMENT ON    COLUMN    LFFRETE.CODREMET IS 'Código do remetente. Relacionada à tabela de códigos unificados SGUNIFCOD
Pode ser cliente, fornecedor, empresa, etc...';
COMMENT ON    COLUMN    LFFRETE.CODDESTINAT IS 'Código do destinatario. Relacionada à tabela de códigos unificados SGUNIFCOD
Pode ser cliente, fornecedor, empresa, etc...';
COMMENT ON    COLUMN    LFFRETE.DTEMITFRETE IS 'Data de emissão do conhecimento de frete.';
COMMENT ON    COLUMN    LFFRETE.QTDFRETE IS 'Quantidade de volumes.';
COMMENT ON    COLUMN    LFFRETE.VLRMERCADORIA IS 'Valor da mercadoria transportada.';
COMMENT ON    COLUMN    LFFRETE.VLRFRETE IS 'Valor cobrado pelo frete.';
COMMENT ON    COLUMN    LFFRETE.VLRFRETENOTA IS 'Valor do frete destacado na nota fiscal.';
COMMENT ON    COLUMN    LFFRETE.PESOBRUTO IS 'Peso bruto da mercadoria transportada.';
COMMENT ON    COLUMN    LFFRETE.PESOLIQUIDO IS 'Peso liquido da mercadoria transportada.';
COMMENT ON    COLUMN    LFFRETE.VLRBASEICMSFRETE IS 'Base do ICMS.';
COMMENT ON    COLUMN    LFFRETE.CODPAG IS 'Codigo no contas a pagar.';
COMMENT ON    COLUMN    LFFRETE.SERIE IS 'Série do documento de conhecimento de frete.';
COMMENT ON    COLUMN    LFFRETE.CODEMPRM IS 'Código da empresa do recebimento de mercadorias.';
COMMENT ON    COLUMN    LFFRETE.CODFILIALRM IS 'Código da filial do recebimento de mercadorias.';
COMMENT ON    COLUMN    LFFRETE.TICKET IS 'Ticket do recebimento de mercadoria.';
COMMENT ON    COLUMN    LFFRETE.CHAVECTE IS 'Chave do conhecimento de transporte eletronico.';
COMMENT ON    COLUMN    LFFRETE.CODEMPTT IS 'Código da empresa do tratamento tributario do icms.';
COMMENT ON    COLUMN    LFFRETE.CODFILIALTT IS 'Código da filial do tratamento tributário do ICMS';
COMMENT ON    COLUMN    LFFRETE.CODTRATTRIB IS 'Código do tratamento tributário do ICMS';
COMMENT ON    COLUMN    LFITCLFISCAL.ORIGFISC IS 'Origem da mercadoria:
0 - Nacional
1 - Estrangeira Importação direta
2 - Estrangeira - Adquirida no mercado interno';
COMMENT ON    COLUMN    LFITCLFISCAL.TPREDICMSFISC IS 'Tipo de redução de ICMS
B - BASE DE CÁLCULO
V - VALOR DO ICMS';
COMMENT ON    COLUMN    LFITCLFISCAL.ALIQFISC IS 'Alíquota interestadual do produto.';
COMMENT ON    COLUMN    LFITCLFISCAL.ALIQFISCINTRA IS 'Alíquota intraestadual de ICMS.';
COMMENT ON    COLUMN    LFITCLFISCAL.ALIQLFISC IS 'Aliquota de livros fiscais (uso pdv)';
COMMENT ON    COLUMN    LFITCLFISCAL.ALIQIPIFISC IS 'Alíquota de IPI';
COMMENT ON    COLUMN    LFITCLFISCAL.ALIQPISFISC IS 'Alíquota diferenciada do PIS para a classificação fiscal (sobrepõe aliquota da filial)';
COMMENT ON    COLUMN    LFITCLFISCAL.ALIQCOFINSFISC IS 'Alíquota diferenciada do Cofins para a classificação fiscal (sobrepõe alíquota da filial)';
COMMENT ON    COLUMN    LFITCLFISCAL.ALIQCSOCIALFISC IS 'Aliquota da contribuição social.';
COMMENT ON    COLUMN    LFITCLFISCAL.ALIQIRFISC IS 'Aliquota do imposto de renda.';
COMMENT ON    COLUMN    LFITCLFISCAL.ALIQFUNRURALFISC IS 'Aliquota de recolhimento do Funrural.';
COMMENT ON    COLUMN    LFITCLFISCAL.ALIQIIFISC IS 'Aliquota do imposto de importação para a classificação fiscal.';
COMMENT ON    COLUMN    LFITCLFISCAL.TIPOST IS 'Tipo da Substituição tributária.
SU - Substituto (Responsável pela retenção e recolhimento de todo o imposto (subsequente também);
SI - Substituído (Imposto pago pelo contribuite substituto);';
COMMENT ON    COLUMN    LFITCLFISCAL.MARGEMVLAGR IS 'Margem de valor agregado para calculo da base de calculo do icms de substituição tributária.';
COMMENT ON    COLUMN    LFITCLFISCAL.GERALFISC IS 'Indica se é a regra geral da classificação fiscal.';
COMMENT ON    COLUMN    LFITCLFISCAL.CODEMPSP IS 'Código da empresa da situação tributária do PIS.';
COMMENT ON    COLUMN    LFITCLFISCAL.CODFILIALSP IS 'Código da filial da situação tributária do PIS';
COMMENT ON    COLUMN    LFITCLFISCAL.CODSITTRIBPIS IS 'Código da situação tributário do PIS.';
COMMENT ON    COLUMN    LFITCLFISCAL.IMPSITTRIBPIS IS 'Código do imposto da situação tributária do PIS.';
COMMENT ON    COLUMN    LFITCLFISCAL.CODEMPSC IS 'Código da empresa da situação tributária do COFINS.';
COMMENT ON    COLUMN    LFITCLFISCAL.CODFILIALSC IS 'Código da situação tributária do COFINS.';
COMMENT ON    COLUMN    LFITCLFISCAL.CODSITTRIBCOF IS 'Código da situação tributária do COFINS.';
COMMENT ON    COLUMN    LFITCLFISCAL.IMPSITTRIBCOF IS 'Código do imposto da situação tributária do COFINS.';
COMMENT ON    COLUMN    LFITCLFISCAL.CODEMPSI IS 'Código da empresa da situação tributária do IPI';
COMMENT ON    COLUMN    LFITCLFISCAL.CODFILIALSI IS 'Código da filial da situação tributária do IPI';
COMMENT ON    COLUMN    LFITCLFISCAL.CODSITTRIBIPI IS 'Código da situação tributária do IPI';
COMMENT ON    COLUMN    LFITCLFISCAL.IMPSITTRIBIPI IS 'Código do imposto da situação tributária do IPI';
COMMENT ON    COLUMN    LFITCLFISCAL.TPCALCIPI IS 'Tipo de cálculo do IPI 
P - Percentual
V - Valor';
COMMENT ON    COLUMN    LFITCLFISCAL.VLRIPIUNIDTRIB IS 'Valor do IPI por unidade tributável.
Utilizado caso o TPCALCIPI = V';
COMMENT ON    COLUMN    LFITCLFISCAL.MODBCICMS IS 'Modalidade de determinação da base de cálculo do ICMS.
0 - Margem Valor Agregado (%)
1 - Pauta (Valor)
2 - Preço Tabelado Max. (valor)
3 - Valor da operação';
COMMENT ON    COLUMN    LFITCLFISCAL.MODBCICMSST IS 'Modalidade de determinação da base de cálculo do ICMS substituição tributária.
0 - Preço tabelado ou máximo sugerido;
1 - Lista Negativa (valor)
2 - Lista Positiva (valor)
3 - Lista Neutra (valor)
4 - Margem Valor Agregado (%);
5 - Pauta (valor)';
COMMENT ON    COLUMN    LFITCLFISCAL.CODPAIS IS 'Código do país para amarração com estado.';
COMMENT ON    COLUMN    LFITCLFISCAL.SIGLAUF IS 'Sigla da UF';
COMMENT ON    COLUMN    LFITCLFISCAL.VLRPISUNIDTRIB IS 'Valor de tributação do PIS por unidade vendida.';
COMMENT ON    COLUMN    LFITCLFISCAL.VLRCOFUNIDTRIB IS 'Valor de tributação do COFINS por unidade vendida.';
COMMENT ON    COLUMN    LFITCLFISCAL.TIPOUSOITFISC IS 'Tipo de uso para a regra de classificação fiscal:
VD - Venda
CP - Compra';
COMMENT ON    COLUMN    LFITCLFISCAL.REDBASEST IS 'Indica se há redução na base do icms de substituição tributária.';
COMMENT ON    COLUMN    LFITCLFISCAL.REDBASEFRETE IS 'Indica se deve reduzir o valor do frete adicionado a base do icms.';
COMMENT ON    COLUMN    LFITCLFISCAL.CODEMPIS IS 'Código da empresa da situação tributário para o ISS.';
COMMENT ON    COLUMN    LFITCLFISCAL.CODFILIALIS IS 'Código da filial para a situação tributária do ISS';
COMMENT ON    COLUMN    LFITCLFISCAL.CODSITTRIBISS IS 'Código da situação tributária para o ISS';
COMMENT ON    COLUMN    LFITCLFISCAL.IMPSITTRIBISS IS 'Sigla do imposto definido para situação tributária do ISS';
COMMENT ON    COLUMN    LFITCLFISCAL.ALIQISSFISC IS 'Alíquota do ISS';
COMMENT ON    COLUMN    LFITCLFISCAL.RETENSAOISS IS 'Indica se deve ISS deve ser retido pelo cliente na fonte (pago pelo cliente e descontado na nota);';
COMMENT ON    COLUMN    LFITCLFISCAL.INDAPURIPI IS 'Indicativo de apuração do IPI
0 - Mensal
1 - Decendial';
COMMENT ON    COLUMN    LFITCLFISCAL.CODEMPCN IS 'Código da empresa do CSOSN
';
COMMENT ON    COLUMN    LFITCLFISCAL.CODFILIALCN IS 'Código da filial do CSOSN
';
COMMENT ON    COLUMN    LFITCLFISCAL.CSOSN IS 'Código de situação da operação no simples nacional.
';
COMMENT ON    COLUMN    LFITCLFISCAL.ALIQICMSIMP IS 'Aliquota do ICMS de importação.
';
COMMENT ON TABLE        LFITCOMPRA IS 'Tabela auxiliar de informações fiscais do item de compra.';
COMMENT ON    COLUMN    LFITCOMPRA.ALIQREDBCICMS IS 'Alíquota da redução da base de calculo do ICMS.';
COMMENT ON    COLUMN    LFITCOMPRA.ALIQREDBCICMSST IS 'Aliquota da redução da base de calculo do ICMS Substituição tributária.';
COMMENT ON    COLUMN    LFITCOMPRA.ALIQICMSST IS 'Aliquota do icms de substituição tributária.';
COMMENT ON    COLUMN    LFITCOMPRA.ALIQPIS IS 'Alíquota do PIS';
COMMENT ON    COLUMN    LFITCOMPRA.VLRPISUNIDTRIB IS 'Valor tributável para o PIS';
COMMENT ON    COLUMN    LFITCOMPRA.VLRBASEPIS IS 'Valor da base de cálculo do PIS';
COMMENT ON    COLUMN    LFITCOMPRA.VLRBASECOFINS IS 'Valor da base de cálculo do COFINS';
COMMENT ON    COLUMN    LFITCOMPRA.VLRCOFUNIDTRIB IS 'Valor tributável do pis por unidade comprada.';
COMMENT ON    COLUMN    LFITCOMPRA.VLRIR IS 'Valor do IR sobre o ítem de compra.';
COMMENT ON    COLUMN    LFITCOMPRA.VLRPIS IS 'Valor do PIS sobre o ítem de compra.';
COMMENT ON    COLUMN    LFITCOMPRA.VLRCOFINS IS 'Valor do COFINS sobre o item de compra.';
COMMENT ON    COLUMN    LFITCOMPRA.VLRCSOCIAL IS 'Valor da Contribuição Social sobre o item de venda.';
COMMENT ON    COLUMN    LFITCOMPRA.ORIGFISC IS 'Origem da mercadoria:
0 - Nacional
1 - Estrangeira Importação direta
2 - Estrangeira - Adquirida no mercado interno';
COMMENT ON    COLUMN    LFITCOMPRA.CODTRATTRIB IS 'Código do tratamento tributário.';
COMMENT ON    COLUMN    LFITCOMPRA.PERCICMSSIMPLES IS 'Percentual de crédito de icms (simples)
';
COMMENT ON    COLUMN    LFITCOMPRA.EMMANUT IS 'Indica se a tabela está em manutenção.';
COMMENT ON    COLUMN    LFITCOMPRA.VLRBASEICMSSTITRETCOMPRA IS 'Base de calculo do icms st retido na operação anterior.';
COMMENT ON    COLUMN    LFITCOMPRA.VLRICMSSTRETITCOMPRA IS 'Valor do ICMS ST retido na operação anterior.
';
COMMENT ON    COLUMN    LFITCOMPRA.ALIQII IS 'Aliquota do imposto de importação do item.
';
COMMENT ON    COLUMN    LFITCOMPRA.VLRBASEII IS 'Valor da base de calculo do imposto de importação do item.
';
COMMENT ON    COLUMN    LFITCOMPRA.VLRII IS 'Valor do imposto de importação do item.
';
COMMENT ON    COLUMN    LFITCOMPRA.VLRICMSDIFERIDO IS 'Valor do ICMS diferido.';
COMMENT ON    COLUMN    LFITCOMPRA.VLRICMSDEVIDO IS 'Valor do ICMS devido.';
COMMENT ON    COLUMN    LFITCOMPRA.VLRICMSCREDPRESUM IS 'Valor do crédito presumido do ICMS';
COMMENT ON TABLE        LFITVENDA IS 'Tabela auxiliar de informações fiscais do item de venda.';
COMMENT ON    COLUMN    LFITVENDA.ALIQREDBCICMS IS 'Alíquota da redução da base de calculo do ICMS.';
COMMENT ON    COLUMN    LFITVENDA.ALIQREDBCICMSST IS 'Aliquota da redução da base de calculo do ICMS Substituição tributária.';
COMMENT ON    COLUMN    LFITVENDA.ALIQICMSST IS 'Aliquota do icms de substituição tributária.';
COMMENT ON    COLUMN    LFITVENDA.ALIQPIS IS 'Alíquota do PIS';
COMMENT ON    COLUMN    LFITVENDA.VLRPISUNIDTRIB IS 'Valor tributável para o PIS';
COMMENT ON    COLUMN    LFITVENDA.VLRBASEPIS IS 'Valor da base de cálculo do PIS';
COMMENT ON    COLUMN    LFITVENDA.VLRBASECOFINS IS 'Valor da base de cálculo do COFINS';
COMMENT ON    COLUMN    LFITVENDA.VLRCOFUNIDTRIB IS 'Valor tributável do pis por unidade vendida';
COMMENT ON    COLUMN    LFITVENDA.VLRIR IS 'Valor do IR sobre o ítem de venda.';
COMMENT ON    COLUMN    LFITVENDA.VLRPIS IS 'Valor do PIS sobre o ítem de venda.';
COMMENT ON    COLUMN    LFITVENDA.VLRCOFINS IS 'Valor do COFINS sobre o item de venda.';
COMMENT ON    COLUMN    LFITVENDA.VLRCSOCIAL IS 'Valor da Contribuição Social sobre o item de venda.';
COMMENT ON    COLUMN    LFITVENDA.VLRBASEICMSITVENDA IS 'Valor da base de calculo do icms sobre o frete do item.';
COMMENT ON    COLUMN    LFITVENDA.VLRBASEICMSFRETEITVENDA IS 'Valor da base de calculo do icms do item de venda.';
COMMENT ON    COLUMN    LFITVENDA.VLRICMSFRETEITVENDA IS 'Valor do icms do frete no item de venda.';
COMMENT ON    COLUMN    LFITVENDA.VLRBASENCM IS 'Valor base de cálculo para estimativa de impostos, referente a lei de transparência';
COMMENT ON    COLUMN    LFITVENDA.ALIQNACNCM IS 'Alíquota estimada de tributos nacionais, referente a lei de transparência';
COMMENT ON    COLUMN    LFITVENDA.ALIQIMPNCM IS 'Alíquota estimada de tributos de importação, referente a lei de transparência';
COMMENT ON    COLUMN    LFITVENDA.VLRNACNCM IS 'Valor estimado de tributos nacionais, referente a lei de transparência';
COMMENT ON    COLUMN    LFITVENDA.VLRIMPNCM IS 'Valor estimado de tributos de importação, referente a lei de transparência';
COMMENT ON    COLUMN    LFLIVROFISCAL.SITUACAOLF IS 'Situação do registro:
"N" - Documento fiscal normal;
"S" - Documento fiscal cancelado;
"E" - Documento fiscal extemporâneo;
"X" - Documento fiscal extemporâneo cancelado;';
COMMENT ON    COLUMN    LFMODNOTA.TIPOMODNOTA IS 'N - Normal
E - Nota fiscal Eletronica
O - Outras';
COMMENT ON    COLUMN    LFNATOPER.IMPDTSAIDANAT IS 'Flag para impressão de data de saida na nota fiscal.
';
COMMENT ON TABLE        LFNCM IS 'NCM Nomenclatura Comum do Mercosul';
COMMENT ON TABLE        LFSEQSERIE IS 'Tabela de sequencia de séries.';
COMMENT ON    COLUMN    LFSEQSERIE.CODEMP IS 'Código da empresa conforme a tabela LFSERIE.';
COMMENT ON    COLUMN    LFSEQSERIE.CODFILIAL IS 'Código da filial conforme a tabela LFSERIE.';
COMMENT ON    COLUMN    LFSEQSERIE.SERIE IS 'Código da série.';
COMMENT ON    COLUMN    LFSEQSERIE.CODEMPSS IS 'Código da empresa.';
COMMENT ON    COLUMN    LFSEQSERIE.CODFILIALSS IS 'Código da filial.';
COMMENT ON    COLUMN    LFSEQSERIE.SEQSERIE IS 'Sequencia da série.';
COMMENT ON    COLUMN    LFSEQSERIE.DOCSERIE IS 'Número sequencia do documento.';
COMMENT ON    COLUMN    LFSEQSERIE.ATIVSERIE IS 'Define se a sequencia está ativa (S/N).';
COMMENT ON    COLUMN    LFSEQSERIE.DTINS IS 'Data de inserção.';
COMMENT ON    COLUMN    LFSEQSERIE.HINS IS 'Hora de inserção.';
COMMENT ON    COLUMN    LFSEQSERIE.IDUSUINS IS 'ID do usuário que inseriu.';
COMMENT ON    COLUMN    LFSEQSERIE.DTALT IS 'Data de alteração.';
COMMENT ON    COLUMN    LFSEQSERIE.HALT IS 'Hora de alteração.';
COMMENT ON    COLUMN    LFSEQSERIE.IDUSUALT IS 'ID do usuário que alterou.';
COMMENT ON TABLE        LFSITTRIB IS 'Tabela de situações tributárias para os impostos:
PIS e Cofins.';
COMMENT ON    COLUMN    LFSITTRIB.IMPSITTRIB IS 'Imposto, pode ser: CO (Cofins),PI (PIS),IC (ICMS),IR (IMPOSTO DE RENDA),CS (Contribuição Social), IP (IPI), IS (ISS)';
COMMENT ON    COLUMN    LFSITTRIB.OPERACAO IS 'Operacao de (S) - Saida, (E) - Entrada';
COMMENT ON    COLUMN    LFTABICMS.UFTI IS 'Sigla do estado';
COMMENT ON    COLUMN    LFTABICMS.ALIQTI IS 'Aliquota de icms interestadual';
COMMENT ON    COLUMN    LFTABICMS.ALIQINTRATI IS 'Aliquota de icms intraestadual.';
COMMENT ON    COLUMN    LFTIPOFISCCLI.CALCCOFINSTF IS 'Indica se deve calcular o cofins.';
COMMENT ON    COLUMN    LFTIPOFISCCLI.CALCCSOCIALTF IS 'Indica se calcula a contribuição social.';
COMMENT ON    COLUMN    LFTIPOFISCCLI.CALCICMSTF IS 'Indica se calcula o ICMS.';
COMMENT ON    COLUMN    LFTIPOFISCCLI.CALCIPITF IS 'Indica se calcula o IPI.';
COMMENT ON    COLUMN    LFTIPOFISCCLI.CALCIRTF IS 'Indica se calcula o IR.';
COMMENT ON    COLUMN    LFTIPOFISCCLI.CALCISSTF IS 'Indica se calcula o iss.';
COMMENT ON    COLUMN    LFTIPOFISCCLI.CALCPISTF IS 'Indica se calcula o PIS.';
COMMENT ON    COLUMN    LFTIPOFISCCLI.IMPCOFINSTF IS 'Indica se imprime o cofins.';
COMMENT ON    COLUMN    LFTIPOFISCCLI.IMPCSOCIALTF IS 'indica se imprime a contribuição social.';
COMMENT ON    COLUMN    LFTIPOFISCCLI.IMPICMSTF IS 'indica se imprime o ICMS.';
COMMENT ON    COLUMN    LFTIPOFISCCLI.IMPISSTF IS 'indica se imprime o ISS.';
COMMENT ON    COLUMN    LFTIPOFISCCLI.IMPIPITF IS 'indica se imprime o IPI.';
COMMENT ON    COLUMN    LFTIPOFISCCLI.IMPIRTF IS 'indica se imprime o IR.';
COMMENT ON    COLUMN    LFTIPOFISCCLI.IMPPISTF IS 'indica se imprime o PIS.';
COMMENT ON    COLUMN    LFTIPOFISCCLI.CODEMPOC IS 'Código da empresa para o tipo de movimento de orçamento para o tipo fiscal de cliente.';
COMMENT ON    COLUMN    LFTIPOFISCCLI.CODFILIALOC IS 'Código da filial para o tipo de movimento de orçamento para o tipo fiscal de cliente.';
COMMENT ON    COLUMN    LFTIPOFISCCLI.CODTIPOMOVOC IS 'Código do tipo de movimento de orçamento para o tipo fiscal de cliente.';
COMMENT ON TABLE        LFTRATTRIB IS 'Tabela de tratamentos tributários de ICMS.';
COMMENT ON TABLE        PPDISTRIB IS 'Distribuição da estrutura para mais de um produto final.';
COMMENT ON    COLUMN    PPDISTRIB.CODEMP IS 'Código da empresa.';
COMMENT ON    COLUMN    PPDISTRIB.CODFILIAL IS 'Código da filial.';
COMMENT ON    COLUMN    PPDISTRIB.CODPROD IS 'Código do produto referente a estrutura.';
COMMENT ON    COLUMN    PPDISTRIB.SEQEST IS 'Número sequencial da estrutura.';
COMMENT ON    COLUMN    PPDISTRIB.SEQEF IS 'Sequencia da fase.';
COMMENT ON    COLUMN    PPDISTRIB.CODEMPFS IS 'Código da empresa na tabela PPFASE.';
COMMENT ON    COLUMN    PPDISTRIB.CODFILIALFS IS 'Código da filial na tabela PPFASE.';
COMMENT ON    COLUMN    PPDISTRIB.CODFASE IS 'Código da fase.';
COMMENT ON    COLUMN    PPDISTRIB.SEQDE IS 'Sequencia da distribuição da estrutura.';
COMMENT ON    COLUMN    PPDISTRIB.CODEMPDE IS 'Código da empresa para vínculo com estrutura usada para distribuição.';
COMMENT ON    COLUMN    PPDISTRIB.CODFILIALDE IS 'Código da filial para estrutura de distribuição.';
COMMENT ON    COLUMN    PPDISTRIB.CODPRODDE IS 'Código do produto para estrutura de distribuição.';
COMMENT ON    COLUMN    PPDISTRIB.SEQESTDE IS 'Número sequencial da estrutura de distribuição.';
COMMENT ON TABLE        PPESTRUANALISE IS 'Cadastro de etapas de analise e controle de qualidade, previstas para a estrutura de um produto.';
COMMENT ON    COLUMN    PPESTRUANALISE.CODEMP IS 'Código da empresa.';
COMMENT ON    COLUMN    PPESTRUANALISE.CODFILIAL IS 'Código da filial.';
COMMENT ON    COLUMN    PPESTRUANALISE.CODESTANALISE IS 'Código da estrutra x analise.';
COMMENT ON    COLUMN    PPESTRUANALISE.CODPROD IS 'Código do produto vinculado a estrutura.';
COMMENT ON    COLUMN    PPESTRUANALISE.SEQEST IS 'Sequência da estrutura.';
COMMENT ON    COLUMN    PPESTRUANALISE.SEQEF IS 'Sequência da estrutura x fase.';
COMMENT ON    COLUMN    PPESTRUANALISE.CODEMPFS IS 'Código da emprea da estrutura x fase.';
COMMENT ON    COLUMN    PPESTRUANALISE.CODFILIALFS IS 'Código da filial da estrutura x fase.';
COMMENT ON    COLUMN    PPESTRUANALISE.CODFASE IS 'Código da fase.';
COMMENT ON    COLUMN    PPESTRUANALISE.CODEMPTA IS 'Código da empresa do tipo de analise.';
COMMENT ON    COLUMN    PPESTRUANALISE.CODFILIALTA IS 'Código da filial do tipo de analise.';
COMMENT ON    COLUMN    PPESTRUANALISE.CODTPANALISE IS 'Código do tipo de análise.';
COMMENT ON    COLUMN    PPESTRUANALISE.VLRMIN IS 'Valor mínimo de tolerância.';
COMMENT ON    COLUMN    PPESTRUANALISE.VLRMAX IS 'Valor máximo de tolerância.';
COMMENT ON    COLUMN    PPESTRUANALISE.ESPECIFICACAO IS 'Espessificação padrão da análise, utilizada quando a análise é descritiva.';
COMMENT ON    COLUMN    PPESTRUANALISE.EMITCERT IS 'Indica se a analise deve sair no certificado de analises.';
COMMENT ON    COLUMN    PPESTRUANALISE.DTINS IS 'Data de inserção do registro.';
COMMENT ON    COLUMN    PPESTRUANALISE.HINS IS 'Hora de inserção do registro.';
COMMENT ON    COLUMN    PPESTRUANALISE.IDUSUINS IS 'Usuário que inseriu o registro.';
COMMENT ON    COLUMN    PPESTRUANALISE.DTALT IS 'Data de alteração do registro.';
COMMENT ON    COLUMN    PPESTRUANALISE.HALT IS 'Hora da ultima alteração no registro.';
COMMENT ON    COLUMN    PPESTRUANALISE.IDUSUALT IS 'Usuário que alterou o registro.';
COMMENT ON TABLE        PPESTRUFASE IS 'Vínculo estrutura, fase de produção e recursos de produção.
';
COMMENT ON    COLUMN    PPESTRUFASE.SEQEST IS 'Número sequencial da estrutura.';
COMMENT ON    COLUMN    PPESTRUFASE.FINALIZAOP IS 'Indica se a fase finaliza o processo de produção (S/N).';
COMMENT ON    COLUMN    PPESTRUFASE.INSTRUCOES IS 'Instruções da fase de produção para a estrutura.';
COMMENT ON TABLE        PPESTRUTURA IS 'Estrutura de produtos.';
COMMENT ON    COLUMN    PPESTRUTURA.SEQEST IS 'Número sequencial da estrutura.';
COMMENT ON    COLUMN    PPESTRUTURA.ATIVOEST IS 'Indica se a estrutura está ativa.(S/N)';
COMMENT ON    COLUMN    PPESTRUTURA.CODEMPML IS 'Código da empresa para o modelo de lote.';
COMMENT ON    COLUMN    PPESTRUTURA.CODFILIALML IS 'Código da filial do modelo de lote.';
COMMENT ON    COLUMN    PPESTRUTURA.CODMODLOTE IS 'Código do modelo de lote.';
COMMENT ON    COLUMN    PPESTRUTURA.NRODIASVALID IS 'Número de dias de validade do produto fabricado.';
COMMENT ON    COLUMN    PPESTRUTURA.GLOTEOPP IS 'Flag que indica, se quando distribuido, produto gera codigo do lote a partir do produto da op principal.';
COMMENT ON    COLUMN    PPESTRUTURA.OBSERVACAO IS 'Observacoes sobre a estrutura.';
COMMENT ON    COLUMN    PPESTRUTURA.ESTDINAMICA IS 'Indica se a estrutura é dinâmica.';
COMMENT ON TABLE        PPFASE IS 'Fases de produção
';
COMMENT ON    COLUMN    PPFASE.TIPOFASE IS '"EX" = Execução;
"CQ" = Controle de qualidade;
"EB" = Embalagem.
';
COMMENT ON    COLUMN    PPFASE.EXTERNAFASE IS 'Indica se a fase é realizada externamente (terceirização)
S - Sim
N - Nao
';
COMMENT ON TABLE        PPFOTOMTAN IS 'Fotos das características dos métodos analíticos.';
COMMENT ON    COLUMN    PPFOTOMTAN.CODMTANALISE IS 'Código do método analítico.';
COMMENT ON    COLUMN    PPFOTOMTAN.CODFOTOMTAN IS 'Código da foto.';
COMMENT ON    COLUMN    PPFOTOMTAN.DESCFOTOMTAN IS 'Descrição da foto.';
COMMENT ON TABLE        PPITESTRUTURA IS 'Ítens de estrutura de produtos.';
COMMENT ON    COLUMN    PPITESTRUTURA.SEQEST IS 'Número sequencial da estrutura.';
COMMENT ON    COLUMN    PPITESTRUTURA.REFPROD IS 'Referencia do produto (estrutura)';
COMMENT ON    COLUMN    PPITESTRUTURA.REFPRODPD IS 'Referencia do produto (item).';
COMMENT ON    COLUMN    PPITESTRUTURA.SEQEF IS 'Sequencia da fase.';
COMMENT ON    COLUMN    PPITESTRUTURA.RMAAUTOITEST IS 'Flag para geração de RMA automática (S/N)';
COMMENT ON    COLUMN    PPITESTRUTURA.CPROVA IS 'Indica se o ítem será utilizado para retenção de contra-prova (controle de qualidade).';
COMMENT ON    COLUMN    PPITESTRUTURA.QTDVARIAVEL IS 'Indica se a quantidade varia em virtude da quantidade final produzida.';
COMMENT ON    COLUMN    PPITESTRUTURA.QTDFIXA IS 'Indica se a quantidade não deve ser multiplicada pela quantidade de itens de estrutura produzida.';
COMMENT ON    COLUMN    PPITESTRUTURA.PERMITEAJUSTEITEST IS 'Indica se deve permitir o ajuste da quantidade a ser utilizad na produção no momento da OP.
';
COMMENT ON    COLUMN    PPITESTRUTURA.TIPOEXTERNO IS 'Tipo de fluxo externo
E - Envio/Remessa
R - Retorno

';
COMMENT ON TABLE        PPITOP IS 'Ítens de ordens de produção.';
COMMENT ON    COLUMN    PPITOP.SEQOP IS 'Número sequencial da OP.';
COMMENT ON    COLUMN    PPITOP.REFPROD IS 'Referencia do produto.';
COMMENT ON    COLUMN    PPITOP.CODEMPLE IS 'Código da empresa - lote
';
COMMENT ON    COLUMN    PPITOP.CODFILIALLE IS 'Código da filial - Lote
';
COMMENT ON    COLUMN    PPITOP.CODLOTE IS 'Código do lote
';
COMMENT ON    COLUMN    PPITOP.SEQITOPCP IS 'Sequência do ítem copiado (utilizado no rateamento automático de lotes)';
COMMENT ON    COLUMN    PPITOP.CODLOTERAT IS 'Código do lote rateado';
COMMENT ON    COLUMN    PPITOP.QTDCOPIAITOP IS 'Quantidade da inserida na cópia do item. Este valor dispara o trigger para criação da cópia.';
COMMENT ON    COLUMN    PPITOP.GERARMA IS 'Indica se deve gerar rma para o item. pode ser que não deva ser gerado por padrão, ou por já ter sido gerado.';
COMMENT ON    COLUMN    PPITOP.SEQAC IS 'Sequencial da ação corretiva.';
COMMENT ON    COLUMN    PPITOP.BLOQOP IS 'Marca se item bloqueou op.';
COMMENT ON    COLUMN    PPITOP.PERMITEAJUSTEITOP IS 'Indica se deve permitir o ajuste de quantidades.
S/N
';
COMMENT ON    COLUMN    PPITOP.TIPOEXTERNO IS 'Tipo de fluxo externo
E - Envio/Remessa
R - Retorno';
COMMENT ON    COLUMN    PPITOP.CODEMPVD IS 'Código da empresa da venda (remessa ou retorno industrialização);
';
COMMENT ON    COLUMN    PPITOP.CODFILIALVD IS 'Código da filial da venda (remessa ou retorno industrialização);';
COMMENT ON    COLUMN    PPITOP.TIPOVENDA IS 'Tipo da venda (remessa ou retorno industrialização);';
COMMENT ON    COLUMN    PPITOP.CODVENDA IS 'Código da venda (remessa ou retorno industrialização);';
COMMENT ON TABLE        PPMETODOANALISE IS 'Cadastro de métodos de análise.';
COMMENT ON    COLUMN    PPMETODOANALISE.DESCMTANALISE IS 'Descrição do tipo de análise.';
COMMENT ON    COLUMN    PPMETODOANALISE.TITULOANALISE IS 'Título do método analítico.';
COMMENT ON    COLUMN    PPMETODOANALISE.FONTEMTANALISE IS 'Fonte do método analítico.';
COMMENT ON    COLUMN    PPMETODOANALISE.MATANALISE IS 'Material utilizado para a análise.';
COMMENT ON    COLUMN    PPMETODOANALISE.REAGANALISE IS 'Reagente da análise.';
COMMENT ON    COLUMN    PPMETODOANALISE.PROCANALISE IS 'Procedimentos';
COMMENT ON    COLUMN    PPMETODOANALISE.DTINS IS 'Data de inserção do registro.';
COMMENT ON    COLUMN    PPMETODOANALISE.HINS IS 'Hora da inserção do registro.';
COMMENT ON    COLUMN    PPMETODOANALISE.IDUSUINS IS 'Usuário que inseriu o registro';
COMMENT ON    COLUMN    PPMETODOANALISE.DTALT IS 'Data da ultima alteração do registro.';
COMMENT ON    COLUMN    PPMETODOANALISE.HALT IS 'Hora da ultima alteração no registro.';
COMMENT ON    COLUMN    PPMETODOANALISE.IDUSUALT IS 'Usuário que realizou a ultima alteração no registro.';
COMMENT ON TABLE        PPOP IS 'Ordens de produção.';
COMMENT ON    COLUMN    PPOP.CODOP IS 'Código da ordem de produção.';
COMMENT ON    COLUMN    PPOP.SEQOP IS 'Número sequencial da OP.';
COMMENT ON    COLUMN    PPOP.DTEMITOP IS 'Data de emissão
';
COMMENT ON    COLUMN    PPOP.CODEMPPD IS 'Código da empresa do produto acabado.';
COMMENT ON    COLUMN    PPOP.CODFILIALPD IS 'Código da filial do produto acabado.';
COMMENT ON    COLUMN    PPOP.CODPROD IS 'Código do produto acabado.';
COMMENT ON    COLUMN    PPOP.SEQEST IS 'Numero sequencial da estrutura.';
COMMENT ON    COLUMN    PPOP.REFPROD IS 'Referencia do produto acabado.';
COMMENT ON    COLUMN    PPOP.DTFABROP IS 'Data de fabricação do produto acabado.';
COMMENT ON    COLUMN    PPOP.QTDSUGPRODOP IS 'Quantidade final sugerida de produção.';
COMMENT ON    COLUMN    PPOP.QTDPREVPRODOP IS 'Quantidade prevista a ser produzida.';
COMMENT ON    COLUMN    PPOP.QTDFINALPRODOP IS 'Quantidade realmente produzida.';
COMMENT ON    COLUMN    PPOP.QTDDISTPOP IS 'Quantidade distribuída referente a OP principal.';
COMMENT ON    COLUMN    PPOP.QTDDISTIOP IS 'Quantidade distribuída referente a OP de distribuição.';
COMMENT ON    COLUMN    PPOP.DTVALIDPDOP IS 'Data de validade do lote do produto acabado.';
COMMENT ON    COLUMN    PPOP.CODEMPLE IS 'Código da empresa do lote do produto acabado.';
COMMENT ON    COLUMN    PPOP.CODFILIALLE IS 'Código da filial do lote do produto acabado.';
COMMENT ON    COLUMN    PPOP.CODLOTE IS 'Código do Lote para o produto acabado.';
COMMENT ON    COLUMN    PPOP.CODEMPTM IS 'Código da empresa do tipo de movimento.';
COMMENT ON    COLUMN    PPOP.CODFILIALTM IS 'Código da filial do tipo de movimento.';
COMMENT ON    COLUMN    PPOP.CODTIPOMOV IS 'Código do tipo de movimento.';
COMMENT ON    COLUMN    PPOP.CODEMPAX IS 'Código da empresa do almoxarifado.';
COMMENT ON    COLUMN    PPOP.CODFILIALAX IS 'Codigo da filial do almoxarifado.';
COMMENT ON    COLUMN    PPOP.CODALMOX IS 'Código do almoxarifado';
COMMENT ON    COLUMN    PPOP.CODEMPOPM IS 'Código da empresa da ordem de produção mestre (principal).';
COMMENT ON    COLUMN    PPOP.CODFILIALOPM IS 'Código da filial da ordem de produção mestre (principal).';
COMMENT ON    COLUMN    PPOP.CODOPM IS 'Código da ordem de produção mestre (principal).';
COMMENT ON    COLUMN    PPOP.SEQOPM IS 'Sequencial da ordem de produção mestre (principal).';
COMMENT ON    COLUMN    PPOP.SITOP IS 'Situação da OP.
PE - Pendente;
CA - Cancelada;
FN - Finalizada;
BL - Bloqueada;';
COMMENT ON    COLUMN    PPOP.OBSOP IS 'Campo para observações a cerca da ordem de produção.';
COMMENT ON    COLUMN    PPOP.JUSTFICQTDPROD IS 'Justificativa pela divergencia entre quantidade prevista e quantidade produzida.';
COMMENT ON    COLUMN    PPOP.JUSTIFICCANC IS 'Justificativa relativa ao cancelamento da OP.';
COMMENT ON    COLUMN    PPOP.DTCANC IS 'Data e hora do cancelamento da O.P.';
COMMENT ON    COLUMN    PPOP.IDUSUCANC IS 'Id do usuário que cancelou a O.P.';
COMMENT ON    COLUMN    PPOP.HCANC IS 'Hora do cancelamento da O.P.';
COMMENT ON    COLUMN    PPOP.CODEMPCP IS 'Código da empresa do item de compra vinculado à OP (conversão de produtos)';
COMMENT ON    COLUMN    PPOP.CODFILIALCP IS 'Código da filial do item de compra vinculado à OP (conversão de produtos)';
COMMENT ON    COLUMN    PPOP.CODCOMPRA IS 'Código da compra do item vinculado à OP (conversão de produtos)';
COMMENT ON    COLUMN    PPOP.CODITCOMPRA IS 'Código do item de compra vinculado à OP (conversão de produtos)';
COMMENT ON    COLUMN    PPOP.ESTDINAMICA IS 'Indica se a ordem de produção deve utilizar o mecanismo de estruturas dinâmicas.';
COMMENT ON    COLUMN    PPOP.GARANTIA IS 'Indica se a ordem de produção e proveniente de garantia.';
COMMENT ON    COLUMN    PPOP.CODEMPOS IS 'Código da empresa da Ordem de serviço vinculada.';
COMMENT ON    COLUMN    PPOP.CODFILIALOS IS 'Código da filial da ordem de serviço vinculada.';
COMMENT ON    COLUMN    PPOP.TICKET IS 'Ticket da ordem de serviço vinculada.';
COMMENT ON    COLUMN    PPOP.CODITRECMERC IS 'Código do item de recebimento da OS vinculada.';
COMMENT ON    COLUMN    PPOP.CODITOS IS 'Código do item da OS vinculada.';
COMMENT ON    COLUMN    PPOP.DTINS IS 'Data de inserção do registro.';
COMMENT ON    COLUMN    PPOP.HINS IS 'Hora de inserção do registro.';
COMMENT ON    COLUMN    PPOP.IDUSUINS IS 'ID do usuário que inseriu o registro.';
COMMENT ON    COLUMN    PPOP.DTALT IS 'Data da última alteração no registro.';
COMMENT ON    COLUMN    PPOP.HALT IS 'Hora da última alteração no registro.';
COMMENT ON    COLUMN    PPOP.IDUSUALT IS 'Id do usuário que realizou a ultima alteração no registro.';
COMMENT ON TABLE        PPOPACAOCORRET IS 'Cadastro de ações corretivas.';
COMMENT ON    COLUMN    PPOPACAOCORRET.TPCAUSA IS 'Tipo de causas fundamentais:
"1M" - Material;
"2M" - Máquina;
"3M" - Método;
"4M" - Meio ambiente;
"5M" - Mão-de-obra;
"6M" - Medida;


';
COMMENT ON    COLUMN    PPOPACAOCORRET.TPACAO IS 'Tipo de ação corretiva pré-definida:
"II" - Inclusão de insumos;
"DP" - Descarte da produção;';
COMMENT ON TABLE        PPOPCQ IS 'Cadastros de lançamento de resultados de análises de controle de qualidade.';
COMMENT ON    COLUMN    PPOPCQ.CODEMP IS 'Código da empresa.';
COMMENT ON    COLUMN    PPOPCQ.CODFILIAL IS 'Código da filial.';
COMMENT ON    COLUMN    PPOPCQ.CODOP IS 'Código da ordem de produção.';
COMMENT ON    COLUMN    PPOPCQ.SEQOP IS 'Sequência da ordem de produção.';
COMMENT ON    COLUMN    PPOPCQ.SEQOPCQ IS 'Sequência do controle de qualidade.';
COMMENT ON    COLUMN    PPOPCQ.SEQAC IS 'Sequencial da ação corretiva para o problema detectado.';
COMMENT ON    COLUMN    PPOPCQ.CODEMPEA IS 'Código da empresa da estrutura x analise.';
COMMENT ON    COLUMN    PPOPCQ.CODFILIALEA IS 'Código da filial da estrutura x analise.';
COMMENT ON    COLUMN    PPOPCQ.CODESTANALISE IS 'Código da estrutura x analise.';
COMMENT ON    COLUMN    PPOPCQ.SEQEF IS 'Sequencial da fase na estrutura.
';
COMMENT ON    COLUMN    PPOPCQ.VLRAFER IS 'Valor da aferição.';
COMMENT ON    COLUMN    PPOPCQ.DESCAFER IS 'Descritivo da aferição.';
COMMENT ON    COLUMN    PPOPCQ.STATUS IS 'Status da análise de controle de qualidade.
"PE" - Pendente;
"AP" - Aprovado;
"RC" - Recusado;
"CO" - Corrigido.';
COMMENT ON    COLUMN    PPOPCQ.DTINS IS 'Data de inserção do registro.';
COMMENT ON    COLUMN    PPOPCQ.HINS IS 'Hora de inserção do registro.';
COMMENT ON    COLUMN    PPOPCQ.IDUSUINS IS 'Usuário que inseriu o registro.';
COMMENT ON    COLUMN    PPOPCQ.DTALT IS 'Data de alteração no registro.';
COMMENT ON    COLUMN    PPOPCQ.HALT IS 'Hora de alteração do registro.';
COMMENT ON    COLUMN    PPOPCQ.IDUSUALT IS 'Usuário que alterou o registro.';
COMMENT ON    COLUMN    PPOPENTRADA.DTENT IS 'Data da entrada.';
COMMENT ON    COLUMN    PPOPENTRADA.HENT IS 'Hora da entrada.';
COMMENT ON TABLE        PPOPFASE IS 'Vínculo OP x Fase
';
COMMENT ON    COLUMN    PPOPFASE.SEQOP IS 'Número sequencial da OP.';
COMMENT ON    COLUMN    PPOPFASE.CODEMPTR IS 'Código da empresa para tabela de tipos de recurso.';
COMMENT ON    COLUMN    PPOPFASE.CODFILIALTR IS 'Código da filial para tabela de tipos de recurso.';
COMMENT ON    COLUMN    PPOPFASE.CODTPREC IS 'Código do tipo de recurso.';
COMMENT ON    COLUMN    PPOPFASE.DATAINIPRODFS IS 'Data de inicio da produção da fase.';
COMMENT ON    COLUMN    PPOPFASE.HINIPRODFS IS 'Hora de inicio da produção da fase.';
COMMENT ON    COLUMN    PPOPFASE.DATAFIMPRODFS IS 'Data de termino da produção da fase.';
COMMENT ON    COLUMN    PPOPFASE.HFIMPRODFS IS 'Hora final da produção da fase.';
COMMENT ON    COLUMN    PPOPFASE.OBSFS IS 'Observações referentes a fase de produção.';
COMMENT ON    COLUMN    PPOPFASE.SITFS IS 'Indica se da fase está pendente ou finalizada.
"PE" - Pendente
"FN" - Finalizada';
COMMENT ON    COLUMN    PPOPITORC.QTDPROD IS 'Quantidade do item de orçamento que deverá ser produzida nessa OP. (previsão)';
COMMENT ON    COLUMN    PPOPITORC.QTDFINALPRODITORC IS 'Quantidade final produzida para atendimento a este ítem de orçamento.
';
COMMENT ON TABLE        PPOPSUBPROD IS 'Tabela para lançamento de sub-produtos em ordens de produção.';
COMMENT ON    COLUMN    PPOPSUBPROD.SEQOF IS 'Sequencial da fase
';
COMMENT ON    COLUMN    PPOPSUBPROD.CODEMPTM IS 'Código da empresa do tipo de movimento.';
COMMENT ON    COLUMN    PPOPSUBPROD.CODFILIALTM IS 'Código da filial do tipo de movimento';
COMMENT ON    COLUMN    PPOPSUBPROD.CODTIPOMOV IS 'Código do tipo de movimento.';
COMMENT ON    COLUMN    PPOPSUBPROD.DTSUBPROD IS 'Data para entrada da subproducao (finalização da fase)
';
COMMENT ON TABLE        PPPROCESSAOPTMP IS 'Tabela temporária para geração de ordens de produção com base em orçamentos.';
COMMENT ON    COLUMN    PPPROCESSAOPTMP.CODEMPET IS 'Código da empresa da estação de trabalho.';
COMMENT ON    COLUMN    PPPROCESSAOPTMP.CODFILIALET IS 'Códiga da filial da estação de trabalho.';
COMMENT ON    COLUMN    PPPROCESSAOPTMP.CODEST IS 'Código da estação de trabalho.';
COMMENT ON TABLE        PPRECURSO IS 'Recursos de produção
';
COMMENT ON TABLE        PPRETCP IS 'Retenção de contra-provas (controle de qualidade).';
COMMENT ON TABLE        PPTIPOANALISE IS 'Tabela para cadastro dos tipos de análise de controle de qualidade.';
COMMENT ON    COLUMN    PPTIPOANALISE.CODEMP IS 'Código da empresa.';
COMMENT ON    COLUMN    PPTIPOANALISE.CODFILIAL IS 'Código da filial.';
COMMENT ON    COLUMN    PPTIPOANALISE.CODTPANALISE IS 'Código do tipo de análise.';
COMMENT ON    COLUMN    PPTIPOANALISE.DESCTPANALISE IS 'Descrição do tipo de análise.';
COMMENT ON    COLUMN    PPTIPOANALISE.OBSTPANALISE IS 'Observação relativa ao tipo de análise, pode descrever o método de análise, instruções de coleta e/ou aferição, etc...';
COMMENT ON    COLUMN    PPTIPOANALISE.TIPOEXPEC IS 'Tipo de registro de especificações.
"MM" - Mínimo e máximo;
"DT" - Descritivo.';
COMMENT ON    COLUMN    PPTIPOANALISE.CODEMPUD IS 'Código da empresa da unidade.';
COMMENT ON    COLUMN    PPTIPOANALISE.CODFILIALUD IS 'Código da filial da unidade.';
COMMENT ON    COLUMN    PPTIPOANALISE.CODUNID IS 'Código da unidade de medida da análise.';
COMMENT ON    COLUMN    PPTIPOANALISE.CODEMPMA IS 'Código da empresa do método analítico.';
COMMENT ON    COLUMN    PPTIPOANALISE.CODFILIALMA IS 'Código da filial do método analítico.';
COMMENT ON    COLUMN    PPTIPOANALISE.CODMTANALISE IS 'Código do método analítico.';
COMMENT ON    COLUMN    PPTIPOANALISE.DTINS IS 'Data de inserção do registro.';
COMMENT ON    COLUMN    PPTIPOANALISE.HINS IS 'Hora de inserção do registro.';
COMMENT ON    COLUMN    PPTIPOANALISE.IDUSUINS IS 'Usuário que inseriu o registro.';
COMMENT ON    COLUMN    PPTIPOANALISE.DTALT IS 'Data da alteração no registro.';
COMMENT ON    COLUMN    PPTIPOANALISE.HALT IS 'Hora da alteração no registro.';
COMMENT ON    COLUMN    PPTIPOANALISE.IDUSUALT IS 'Usuário que alterou o registro.';
COMMENT ON TABLE        PPTIPOREC IS 'Tipos de recurso de produção.
';
COMMENT ON    COLUMN    PVCAIXA.CODEMPET IS 'Código da empresa na tabela de estação de trabalho.';
COMMENT ON    COLUMN    PVCAIXA.CODFILIALET IS 'Código da filial na tabela de estações de trabalho.';
COMMENT ON    COLUMN    PVCAIXA.CODEST IS 'Código da estação de trabalho.';
COMMENT ON    COLUMN    PVCAIXA.SEQINI IS 'FAIXA INICIAL PARA SEQUENCIA DE VENDA DE ECF';
COMMENT ON    COLUMN    PVCAIXA.SEQMAX IS 'FAIXA FINAL PARA SEQUENCIA DE VENDA DE ECF';
COMMENT ON    COLUMN    PVCAIXA.ORCCAIXA IS 'Habilita a venda para pdv somente com orçamento.';
COMMENT ON TABLE        RHAREA IS 'Areas de negócio para cursos e vagas módulo RH (Recrutamento e seleção).';
COMMENT ON    COLUMN    RHAREA.CODAREA IS 'Código da área.';
COMMENT ON TABLE        RHBENEFICIO IS 'Cadastro de benefícios.';
COMMENT ON TABLE        RHCANDIDATO IS 'Cadastro de candidatos a vaga para uso no módulo de recrutamento e seleção.';
COMMENT ON    COLUMN    RHCANDIDATO.UFCAND IS 'Unidade federativa do candidato.';
COMMENT ON    COLUMN    RHCANDIDATO.OUTROSEMPREGOS IS 'Outros empregos.';
COMMENT ON    COLUMN    RHCANDIDATO.CODEMPES IS 'Código da empresa do estado civil.';
COMMENT ON    COLUMN    RHCANDIDATO.CODFILIALES IS 'Código da filial do estado civil.';
COMMENT ON    COLUMN    RHCANDIDATO.CODESTCIVIL IS 'Código do estado civil.';
COMMENT ON    COLUMN    RHCANDIDATO.PRETENSAOSAL IS 'Pretensão salarial.';
COMMENT ON    COLUMN    RHCANDIDATO.STCAND IS 'Status do candidato:
"IN" - Inativo;
"DI" - Disponível;
"EN" - Encaminhado;
"EL" - Eliminado de processo seletivo;
"EF" - Efetivado;
"EM" - Empregado;
"EV" - Entrevistado;
"DL" - Desligado;';
COMMENT ON    COLUMN    RHCANDIDATO.OBSCAND IS 'Observações do candidato.';
COMMENT ON    COLUMN    RHCANDIDATO.NROFILHOS IS 'Número de filhos.';
COMMENT ON    COLUMN    RHCANDIDATO.ISENCTRANSP IS 'Indica se candidato é isento de tarifa de transporte - S/N';
COMMENT ON TABLE        RHCANDIDATOCARAC IS 'Tabela de vinculo entre candidatos e suas caracteristicas.';
COMMENT ON TABLE        RHCANDIDATOCURSO IS 'Tabela de vinculo entre candidatos e cursos.';
COMMENT ON TABLE        RHCANDIDATOFUNC IS 'Tabela de vinculo entre candidatos e funcoes exercidas.';
COMMENT ON TABLE        RHCANDIDATOSTATUS IS 'Tabela de histórico dos status do candidato.';
COMMENT ON    COLUMN    RHCANDIDATOSTATUS.DTSTATUS IS 'Data do status.';
COMMENT ON TABLE        RHCARACTERISTICA IS 'Cadastro de caracteristicas pessoais.';
COMMENT ON TABLE        RHCURSO IS 'Cadastro de cursos para utilização no módulo de RH (Recrutamento e seleção).';
COMMENT ON    COLUMN    RHCURSO.DESCCURSO IS 'Descrição do curso.';
COMMENT ON    COLUMN    RHCURSO.CONTPROGCURSO IS 'Conteúdo programático do curso.';
COMMENT ON    COLUMN    RHCURSO.INSTITUICAOCURSO IS 'Instituição de ensino onde foi realizado o curso.';
COMMENT ON    COLUMN    RHCURSO.DURACAOCURSO IS 'Duração do curso (em meses).';
COMMENT ON    COLUMN    RHCURSO.CODAREA IS 'Código da área ';
COMMENT ON    COLUMN    RHCURSO.CODEMPAR IS 'Código da empresa da área.';
COMMENT ON    COLUMN    RHCURSO.CODFILIALAR IS 'Código da filial da área.';
COMMENT ON    COLUMN    RHCURSO.CODNIVELCURSO IS 'Nível do curso';
COMMENT ON    COLUMN    RHCURSO.CODEMPNC IS 'Código da empresa do nível do curso.';
COMMENT ON    COLUMN    RHCURSO.CODFILIALNC IS 'Código da filial do nível do curso.';
COMMENT ON TABLE        RHDEPTO IS 'Cadastro de departamentos.';
COMMENT ON TABLE        RHEMPREGADO IS 'Cadastro de empregados.';
COMMENT ON    COLUMN    RHEMPREGADO.APELIDOEMPR IS 'Nome reduzido ou apelido para uso no cracha do empregado.';
COMMENT ON    COLUMN    RHEMPREGADO.DTADMISSAO IS 'Data de admissão do empregado.';
COMMENT ON    COLUMN    RHEMPREGADO.FOTOEMPR IS 'Foto do empregado.';
COMMENT ON    COLUMN    RHEMPREGADO.STATUSEMPR IS 'Status do empregado
"AD" - Admitido;
"DE" - Demitido;
"EF" - Em férias;
"LM" - Licença maternidade;
"AI" - Afastamento INSS;
"AP" - Aposentado;';
COMMENT ON    COLUMN    RHEMPREGADO.SEXOEMPR IS 'Sexo do empregado';
COMMENT ON    COLUMN    RHEMPREGADO.DTNASCEMPR IS 'Data de nascimento do empregado.';
COMMENT ON    COLUMN    RHEMPREGADO.CTPSEMPR IS 'Identificação da Carteira de Trabalho e Previdência Social.';
COMMENT ON    COLUMN    RHEMPREGADO.SERIECTPSEMPR IS 'Série da carteira de trabalho e previdência social.';
COMMENT ON    COLUMN    RHEMPREGADO.UFCTPSEMPR IS 'Estado de emissão da carteira de trabalho e previdência social.';
COMMENT ON    COLUMN    RHEMPREGADO.CERTRESERVEMPR IS 'Certificado de reservista (Exército)';
COMMENT ON    COLUMN    RHEMPREGADO.PISPASEPEMPR IS 'PIS PASEP do empregado.';
COMMENT ON    COLUMN    RHEMPREGADO.RGEMPR IS 'RG do empregado.';
COMMENT ON    COLUMN    RHEMPREGADO.ORGEXPRHEMPR IS 'Orgão de expedição do RG do Empregado.';
COMMENT ON    COLUMN    RHEMPREGADO.CPFEMPR IS 'CPF do Empregado.';
COMMENT ON    COLUMN    RHEMPREGADO.UFRGEMPR IS 'Estado de expedição do RG.';
COMMENT ON    COLUMN    RHEMPREGADO.DTEXPRGEMPR IS 'Data de expedição do RG.';
COMMENT ON    COLUMN    RHEMPREGADO.TITELEITEMPR IS 'Titulo de eleitor.';
COMMENT ON    COLUMN    RHEMPREGADO.ZONAELEITEMPR IS 'Zona eleitoral do empregado.';
COMMENT ON    COLUMN    RHEMPREGADO.SECAOELEITEMPR IS 'Secao eleitoral do empregado.';
COMMENT ON    COLUMN    RHEMPREGADO.CNHEMPR IS 'Carteira nacional de habilitação do empregado.';
COMMENT ON    COLUMN    RHEMPREGADO.MAEEMPR IS 'Nome da mãe do empregado.';
COMMENT ON    COLUMN    RHEMPREGADO.PAIEMPR IS 'Nome do pai do empregado.';
COMMENT ON    COLUMN    RHEMPREGADO.COMPLENDEMPR IS 'Complemento do endereço do empregado.';
COMMENT ON    COLUMN    RHEMPREGADO.UFEMPR IS 'Estado do empregado (Endereço).';
COMMENT ON    COLUMN    RHEMPREGADO.DTDEMISSAOEMPR IS 'Data de demissão do empregado.';
COMMENT ON    COLUMN    RHEMPREGADO.DDDEMPR IS 'DDD do telefone do cliente.';
COMMENT ON    COLUMN    RHEMPREGADO.FONE2EMPR IS 'Segundo telefone do empregado.';
COMMENT ON    COLUMN    RHEMPREGADO.CELEMPR IS 'Telefone celular do empregado.';
COMMENT ON    COLUMN    RHEMPREGADO.EMAILEMPR IS 'Email do empregado.';
COMMENT ON TABLE        RHEMPREGADOBENEF IS 'Tabela de vínculos entre empregados e seus benefícios.';
COMMENT ON TABLE        RHEMPREGADOR IS 'Cadastro de empregadores conveniados para módulo de recrutamento e seleção.';
COMMENT ON    COLUMN    RHEMPREGADOR.NOMEEMPR IS 'Nome do empregador conveniado.';
COMMENT ON    COLUMN    RHEMPREGADOR.ATIVOEMPR IS 'Indica se empregador está (A) ativo ou inativo (I).';
COMMENT ON    COLUMN    RHEMPREGADOR.CNPJEMPR IS 'CNPJ do empregador conveniado.';
COMMENT ON    COLUMN    RHEMPREGADOR.INSCEMPR IS 'Inscrição estadual do empregador conveniado.';
COMMENT ON    COLUMN    RHEMPREGADOR.ENDEMPR IS 'Endereço do empregador conveniado.';
COMMENT ON    COLUMN    RHEMPREGADOR.NUMEMPR IS 'Número do endereço do empregador conveniado.';
COMMENT ON TABLE        RHEMPREGADOSAL IS 'Table de histório salarial do empregado.';
COMMENT ON    COLUMN    RHEMPREGADOSAL.VALORSAL IS 'Valor do salário mensal.';
COMMENT ON    COLUMN    RHEMPREGADOSAL.CUSTOHORATRAB IS 'Custo da hora trabalhada.';
COMMENT ON    COLUMN    RHEMPREGADOSAL.DTVIGOR IS 'Data de vigor do novo salário.';
COMMENT ON    COLUMN    RHEMPREGADOSAL.OBSSAL IS 'Observações a respeito do salário.';
COMMENT ON TABLE        RHFUNCAO IS 'Tabela de funções profissionais.';
COMMENT ON    COLUMN    RHFUNCAO.CBOFUNC IS 'Código brasileiro de ocupações (CBO)';
COMMENT ON TABLE        RHNIVELCURSO IS 'Cadastro dos níveis dos cursos do módulo de RH (Recrutamento e seleção).';
COMMENT ON TABLE        RHPONTO IS 'Tabela para registro do ponto dos funcionários.';
COMMENT ON    COLUMN    RHTABELAIRRF.DEDUCAO IS 'Dedução';
COMMENT ON TABLE        RHTURNO IS 'Tabela de turnos.';
COMMENT ON    COLUMN    RHTURNO.DESCTURNO IS 'Descrição do turno.';
COMMENT ON    COLUMN    RHTURNO.NHSTURNO IS 'Número de horas semanais.';
COMMENT ON    COLUMN    RHTURNO.TIPOTURNO IS 'Tipo de turno:
N - Normal (Manhã e tarde)
M - Manhã
T - Tarde
O - Noite
E - Especial
';
COMMENT ON    COLUMN    RHTURNO.HINITURNO IS 'Hora inicial do turno.';
COMMENT ON    COLUMN    RHTURNO.HFIMTURNO IS 'Hora final do turno.';
COMMENT ON    COLUMN    RHTURNO.HINIINTTURNO IS 'Hora inicial do intervalo.';
COMMENT ON    COLUMN    RHTURNO.HFIMINTTURNO IS 'Hora final do intervalo.';
COMMENT ON    COLUMN    RHTURNO.TOLENTRADA IS 'Tolerância na entrada (minutos).';
COMMENT ON    COLUMN    RHTURNO.TOLSAIDA IS 'Tolerância na saída (minutos).';
COMMENT ON TABLE        RHVAGA IS 'Cadastro de vagas de emprego.';
COMMENT ON    COLUMN    RHVAGA.CODVAGA IS 'Código da vaga.';
COMMENT ON    COLUMN    RHVAGA.CODEMPR IS 'Código do empregador.';
COMMENT ON    COLUMN    RHVAGA.CODFUNC IS 'Código da função.';
COMMENT ON    COLUMN    RHVAGA.CODEMPTN IS 'Código da empresa do turno.';
COMMENT ON    COLUMN    RHVAGA.CODFILIALTN IS 'Código da filial do turno.';
COMMENT ON    COLUMN    RHVAGA.CODTURNO IS 'Código do turno.';
COMMENT ON    COLUMN    RHVAGA.STVAGA IS 'Status da vaga:
"AB" - Aberta;
"SL" - Em processo de seleção;
"CA" - Cancelada;
"PR" - Preenchida;';
COMMENT ON    COLUMN    RHVAGA.DTULTST IS 'Data do ultimo status.';
COMMENT ON TABLE        RHVAGACANDIDATO IS 'Tabela de vínculo entre vaga e candidatos.';
COMMENT ON    COLUMN    RHVAGACANDIDATO.STVAGACAND IS 'Situação do candidato na vaga:
"EN" Encaminhado
"EF" Efetivado ';
COMMENT ON TABLE        RHVAGACARACQUALI IS 'Tabela de relacionamento entre vagas e caracteristicas qualificativas.';
COMMENT ON TABLE        RHVAGACARACREST IS 'Tabela de relacionamento entre vagas e atribuições restritivas.';
COMMENT ON TABLE        RHVAGACURSO IS 'Tabela de vínculo entre vagas e cursos.';
COMMENT ON TABLE        RHVAGASTATUS IS 'Tabela histórica dos status das vagas.';
COMMENT ON    COLUMN    RHVAGASTATUS.CODVAGA IS 'Código da vaga.';
COMMENT ON    COLUMN    RHVAGASTATUS.SQSTVAGA IS 'Sequencial do status da vaga.';
COMMENT ON    COLUMN    RHVAGASTATUS.STVAGA IS 'Status da vaga:
""AB"" - Aberta;
""SL"" - Em processo de seleção;
""CA"" - Cancelada;
""PR"" - Preenchida;';
COMMENT ON    COLUMN    RHVAGASTATUS.DTSTATUS IS 'Data do ustatus.';
COMMENT ON    COLUMN    SGAGENDA.TIPOAGE IS 'Tipo do agente.';
COMMENT ON    COLUMN    SGAGENDA.CODAGE IS 'Código do agente.';
COMMENT ON    COLUMN    SGAGENDA.SITAGD IS 'Status do agendamento:
"PE" - Pendente;
"CA" - Cancelado;
"FN" - Finalizado;';
COMMENT ON    COLUMN    SGAGENDA.CODAGEEMIT IS 'Código do agente criador do agendamento';
COMMENT ON    COLUMN    SGAGENDA.CAAGD IS 'Controle de acesso ao agendamento
"PU" - publico
"PR" - privado
';
COMMENT ON    COLUMN    SGAGENDA.CODTIPOAGD IS 'Código do tipo de agendamento.';
COMMENT ON    COLUMN    SGAGENDA.CODEMPAR IS 'Código da empresa do agendamento vinculado (repetitivos).';
COMMENT ON    COLUMN    SGAGENDA.CODFILIALAR IS 'Código da filial do agendamento vinculado (repetitivos).';
COMMENT ON    COLUMN    SGAGENDA.TIPOAGEAR IS 'Código da tipo do agendamento vinculado (repetitivos).';
COMMENT ON    COLUMN    SGAGENDA.CODAGEAR IS 'Código do agente do agendamento vinculado (repetitivos).';
COMMENT ON    COLUMN    SGAGENDA.CODAGDAR IS 'Código do agendamento vinculado (repetitivos).';
COMMENT ON    COLUMN    SGAGENDA.DIATODO IS 'Indica se compromisso ocupará o dia inteiro.';
COMMENT ON TABLE        SGAGENTE IS 'Tabela de pessoas, recursos e outros para os quais podemos agendar tarefas.';
COMMENT ON    COLUMN    SGAGENTE.CODEMP IS 'Código da empresa
';
COMMENT ON    COLUMN    SGAGENTE.CODFILIAL IS 'Código da filial';
COMMENT ON    COLUMN    SGAGENTE.TIPOAGE IS 'Tipo do agente:
SGUSU - Usuário
';
COMMENT ON    COLUMN    SGAGENTE.CODAGE IS 'Código do agente.';
COMMENT ON    COLUMN    SGAGENTE.DESCAGE IS 'Descrição ou nome do agente.';
COMMENT ON    COLUMN    SGATRIBUSU.DTINS IS 'Data de inserção.';
COMMENT ON    COLUMN    SGATRIBUSU.HINS IS 'Hora de inserção.';
COMMENT ON    COLUMN    SGATRIBUSU.IDUSUINS IS 'ID do usuário que inseriu.';
COMMENT ON    COLUMN    SGATRIBUSU.DTALT IS 'Data da última alteração.';
COMMENT ON    COLUMN    SGATRIBUSU.HALT IS 'Hora da última alteração.';
COMMENT ON    COLUMN    SGATRIBUSU.IDUSUALT IS 'ID do último usuário que alterou.';
COMMENT ON TABLE        SGBAIRRO IS 'Tabela de cadastro de bairros.';
COMMENT ON    COLUMN    SGBAIRRO.VLRFRETE IS 'Valor de frete estimado para carga vinda deste bairro.';
COMMENT ON    COLUMN    SGBAIRRO.QTDFRETE IS 'Quantidade referente ao valor de frete.';
COMMENT ON TABLE        SGCNAE IS 'Tabela de atividades padrão CNAE.';
COMMENT ON    COLUMN    SGEMPRESA.MULTIALMOXEMP IS 'Flag que indica se o estoque será controlado por multiplos almoxarifados.';
COMMENT ON    COLUMN    SGEMPRESA.PERCISSEMP IS 'Campo inutilizado (transferência para a tabela SGFILIAL)';
COMMENT ON    COLUMN    SGEMPRESA.CODPAISEMP IS 'Campo inutilizado (transferência para a tabela SGPAIS)';
COMMENT ON    COLUMN    SGESTACAO.MODODEMOEST IS 'Indica se a estação está em modo demonstrativo.';
COMMENT ON    COLUMN    SGESTACAO.NFEEST IS 'Habilita NFE para estação de trabalho (S/N).';
COMMENT ON    COLUMN    SGESTACAO.TAMFONTETXT IS 'Tamanho da fonte para visualização dos relatórios texto.';
COMMENT ON    COLUMN    SGESTACAO.FONTETXT IS 'Nome da fonte para visualização dos relatórios texto.';
COMMENT ON    COLUMN    SGESTACAO.DTINS IS 'Data de inserção.';
COMMENT ON    COLUMN    SGESTACAO.HINS IS 'Hora de inserção.';
COMMENT ON    COLUMN    SGESTACAO.IDUSUINS IS 'ID do usuário que inseriu.';
COMMENT ON    COLUMN    SGESTACAO.DTALT IS 'Data da última alteração.';
COMMENT ON    COLUMN    SGESTACAO.HALT IS 'Hora da última alteração.';
COMMENT ON    COLUMN    SGESTACAO.IDUSUALT IS 'ID do último usuário que alterou.';
COMMENT ON    COLUMN    SGESTACAOBAL.DRIVERBAL IS 'Driver de comunicação com a balança.';
COMMENT ON    COLUMN    SGESTACAOBAL.TIPOPROCRECMERC IS 'Indica o uso principal da balança.
"TO" - Todos;
"PI" - Pesagem inicial;
"TR" - Tiragem de renda/descarregamento;
"PF" - Pesagem final;';
COMMENT ON    COLUMN    SGESTACAOIMP.CODFILIAL IS 'Código da filial na tabela de estações de trabalho.';
COMMENT ON    COLUMN    SGESTACAOIMP.CODEST IS 'Código da estação de trabalho.';
COMMENT ON    COLUMN    SGESTACAOIMP.NROIMP IS 'Número da impressora na estação.';
COMMENT ON    COLUMN    SGESTACAOIMP.CODEMPIP IS 'Código da empresa na tabela de impressoras.';
COMMENT ON    COLUMN    SGESTACAOIMP.CODFILIALIP IS 'Código da filial na tabela de impressoras.';
COMMENT ON    COLUMN    SGESTACAOIMP.CODIMP IS 'Código da impressora.';
COMMENT ON    COLUMN    SGESTACAOIMP.CODEMPPP IS 'Código da empresa na tabela de papel.';
COMMENT ON    COLUMN    SGESTACAOIMP.CODFILIALPP IS 'Código da filial na tabela papel.';
COMMENT ON    COLUMN    SGESTACAOIMP.CODPAPEL IS 'Código do papel.';
COMMENT ON    COLUMN    SGESTACAOIMP.PORTAWIN IS 'Porta de impressão no windows.';
COMMENT ON    COLUMN    SGESTACAOIMP.PORTALIN IS 'Porta de impressão no linux.';
COMMENT ON    COLUMN    SGESTACAOIMP.IMPPAD IS 'Flag que define se a impressora é padrão.';
COMMENT ON    COLUMN    SGESTACAOIMP.TIPOUSOIMP IS 'Tipo de uso da impressora.:
NF - Nota fiscal;
NS - Nota fiscal - serviço;
PD - Pedido;
RS - Relatório simples;
RG - Relatório gráfico;
TO - Todos;
';
COMMENT ON    COLUMN    SGESTACAOIMP.IMPGRAFICA IS 'Flag que define se a impressão é gráfica.';
COMMENT ON    COLUMN    SGESTACAOIMP.DTINS IS 'Data de inserção do registro.';
COMMENT ON    COLUMN    SGESTACAOIMP.HINS IS 'Hora da inserção.';
COMMENT ON    COLUMN    SGESTACAOIMP.IDUSUINS IS 'ID do usuário que inseriu.';
COMMENT ON    COLUMN    SGESTACAOIMP.DTALT IS 'Data da última alteração.';
COMMENT ON    COLUMN    SGESTACAOIMP.HALT IS 'Hora da última alteração.';
COMMENT ON    COLUMN    SGESTACAOIMP.IDUSUALT IS 'ID do último usuário que alterou.';
COMMENT ON TABLE        SGESTCIVIL IS 'Tabela de estados civis.';
COMMENT ON    COLUMN    SGESTCIVIL.DESCESTCIVIL IS 'Descrição do estado civil.';
COMMENT ON    COLUMN    SGFERIADO.BANCFER IS 'Indica se é um feriado bancário.';
COMMENT ON    COLUMN    SGFERIADO.TRABFER IS 'Indica se é um feriado trabalhista.';
COMMENT ON    COLUMN    SGFILIAL.DDDFILIAL IS 'DDD da filial';
COMMENT ON    COLUMN    SGFILIAL.PERCSIMPLESFILIAL IS 'Percentual de tributação da empresa, no caso de enquadramento no simples.';
COMMENT ON    COLUMN    SGFILIAL.CODUNIFCOD IS 'Código na tabela de unificação de códigos (SGUNIFCOD).';
COMMENT ON    COLUMN    SGFILIAL.INSCMUNFILIAL IS 'Inscrição municipal';
COMMENT ON    COLUMN    SGFILIAL.CNAEFILIAL IS 'Classificação Nacional de Atividades Econômicas (CNAE).';
COMMENT ON    COLUMN    SGFILIAL.PERCISSFILIAL IS 'Percentual de ISS pago pela filial.';
COMMENT ON    COLUMN    SGFILIAL.CONTRIBIPIFILIAL IS 'Indica se a filial é contribuinte do IPI.';
COMMENT ON    COLUMN    SGFILIAL.PERFILFILIAL IS 'Perfil de apresentacao do arquivo fiscal (SPED-EFD)
A - Perfil A
B - Perfil B
C - Perfil C';
COMMENT ON    COLUMN    SGFILIAL.INDATIVFILIAL IS 'Indicador de tipo de atividadade  (SPED-EFD)
0 - Industrial ou equiparado a industrial
1 - Outros';
COMMENT ON    COLUMN    SGFILIAL.CODEMPCO IS 'Código da empresa do fornecedor (contador)';
COMMENT ON    COLUMN    SGFILIAL.CODFILIALCO IS 'Código da filial do fornecedor (contador)';
COMMENT ON    COLUMN    SGFILIAL.CODFOR IS 'Código do fornecedor (contador)';
COMMENT ON    COLUMN    SGFILIAL.SUFRAMA IS 'Código da filial no suframa';
COMMENT ON TABLE        SGITPREFERE6 IS 'Tabela de preferências Febraban detalhada por banco e tipo (SIACC E CNAB)';
COMMENT ON    COLUMN    SGITPREFERE6.TIPOFEBRABAN IS 'Tipo de layout SIACC=01 ou CNAB=02';
COMMENT ON    COLUMN    SGITPREFERE6.CODCONV IS 'Código do convênio.';
COMMENT ON    COLUMN    SGITPREFERE6.CONVCOB IS 'Convênio de cobrança do bloqueto.';
COMMENT ON    COLUMN    SGITPREFERE6.VERLAYOUT IS 'Versão do layout.';
COMMENT ON    COLUMN    SGITPREFERE6.IDENTSERV IS 'Identificação do serviço.';
COMMENT ON    COLUMN    SGITPREFERE6.CONTACOMPR IS 'Conta compromisso.';
COMMENT ON    COLUMN    SGITPREFERE6.IDENTAMBCLI IS 'Identificação de ambiente do cliente. P=Produção - T=Teste.';
COMMENT ON    COLUMN    SGITPREFERE6.IDENTAMBBCO IS 'Identificação do ambiente do banco. P=Produção - T=Teste.';
COMMENT ON    COLUMN    SGITPREFERE6.NUMCONTA IS 'Número da conta corrente.';
COMMENT ON    COLUMN    SGITPREFERE6.FORCADTIT IS 'Forma de cadastramento do titulo.
1 - Com cadastro.
2 - Sem cadastro.';
COMMENT ON    COLUMN    SGITPREFERE6.TIPODOC IS 'Tipo de documento.
1 - Tradicional.
2 - Escrutiral.
';
COMMENT ON    COLUMN    SGITPREFERE6.IDENTEMITBOL IS 'Identificação da emissão de bloqueto.
1 - Banco emite.
2 - Cliente emite.
3 - Banco pré-emite e o cliente completa.
4 - Banco reemite.
5 - Banco não reemite.
6 - Cobrança sem papel.
Obs.: Os campos 4 e 5 só serão aceitos para código de movimento para remessa 31.';
COMMENT ON    COLUMN    SGITPREFERE6.IDENTDISTBOL IS 'Identificação da distribuição.
1 - Banco.
2 - Cliente.';
COMMENT ON    COLUMN    SGITPREFERE6.ESPECTIT IS 'Especie do titulo.
01 - CH Cheque.
02 - DM Duplicata mercantíl.
03 - DMI Duplicata mercantíl p/ indicação.
04 - DS Duplicata de serviço.
05 - DSI DUplicata de serviçõ p/ indicação.
06 - DR Duplicata rural.
07 - LC Letra de cambio.
08 - NCC Nota de crédito comercial.
09 - NCE Nota de crédito a exportação.
10 - NCI Nota de crédito indústria.
11 - NCR Nota de crédito rural.
12 - NP Nota promissória.
13 - NPR Nota promissória rural.
14 - TM Triplicata mercantíl.
15 - TS Triplicata de serviço.
16 - NS Nota de seguro.
17 - RC Recibo.
18 - FAT Fatura.
19 - ND Nota de débito.
20 - AP Apolice de seguro.
21 - ME Mensalidade escolar.
22 - PC Parcela de consórcio.
99 - Outros.

';
COMMENT ON    COLUMN    SGITPREFERE6.CODJUROS IS 'Código do juros de mora.
1 - Valor por dia.
2 - Taxa nensal.
3 - Isento.
';
COMMENT ON    COLUMN    SGITPREFERE6.VLRPERCJUROS IS 'Valor ou percentual do juros.
';
COMMENT ON    COLUMN    SGITPREFERE6.CODDESC IS 'Código do desconto.
1 - Valor fixo até a data informada.
2 - Percentual até a data informada.
3 - Valor por antecipação por dia corrido.
4 - Valor por antecipação por dia util.
5 - Percentual sobre o valor nominal dia corrido.
6 - Percentual sobre o valor nominal dia util.
Obs.: Para as opções 1 e 2 será obrigatório a informação da data.';
COMMENT ON    COLUMN    SGITPREFERE6.VLRPERCDESC IS 'Valor ou percentual do desconto.';
COMMENT ON    COLUMN    SGITPREFERE6.CODPROT IS 'Código para protesto.
1 - Dias corridos.
2 - Dias utéis.
3 - Não protestar.';
COMMENT ON    COLUMN    SGITPREFERE6.DIASPROT IS 'Número de dias para protesto.';
COMMENT ON    COLUMN    SGITPREFERE6.CODBAIXADEV IS 'Código para baixa/devolução.
1 - Baixar/Devolver.
2 - Não baixar/ Não devolver.';
COMMENT ON    COLUMN    SGITPREFERE6.DIASBAIXADEV IS 'Número de dias para a Baixa / Devolução.';
COMMENT ON    COLUMN    SGITPREFERE6.ACEITE IS 'Defini o aceite do arquivo de cnab S - sim e N - não';
COMMENT ON    COLUMN    SGITPREFERE6.PADRAOCNAB IS 'Indica o padrão CNAB, pode ser: 240 ou 400
';
COMMENT ON    COLUMN    SGITPREFERE6.PADRAOSIACC IS 'Indica o padrão SIACC, pode ser: 150 ou 240
';
COMMENT ON    COLUMN    SGLOG.CLASLOG IS 'Classificacao do log
PR - Procedimento
ER - Erro 
';
COMMENT ON    COLUMN    SGLOG.TIPOLOG IS 'Tipo do log:
LIB - Liberação de venda abaixo do custo
';
COMMENT ON    COLUMN    SGLOG.DESCLOG IS 'Descrição resumida da operação
';
COMMENT ON    COLUMN    SGLOG.OBSLOG IS 'Descrição completa e observações sobre a operação
';
COMMENT ON TABLE        SGLOGCRUD IS 'Log das ações de insert, update e delete.';
COMMENT ON    COLUMN    SGLOGCRUD.ID IS 'Identificação';
COMMENT ON    COLUMN    SGLOGCRUD.TABLENAME IS 'Nome da tabela';
COMMENT ON    COLUMN    SGLOGCRUD.OPERATION IS 'Operação:
U - Update
D - Delete
I - Insert
';
COMMENT ON    COLUMN    SGLOGCRUD.EVENTLOG IS 'Evento:
B - Before antes da operacão
A - After após a operação';
COMMENT ON    COLUMN    SGLOGCRUD.DTLOG IS 'Data da operação';
COMMENT ON    COLUMN    SGLOGCRUD.HLOG IS 'Hora da operação';
COMMENT ON    COLUMN    SGLOGCRUD.IDUSU IS 'ID do usuário';
COMMENT ON    COLUMN    SGLOGCRUD.XML IS 'Conteúdo do registro antes ou depois da alteração.';
COMMENT ON    COLUMN    SGMODETIQUETA.EECMODETIQ IS 'Número de espaços entre colunas.';
COMMENT ON    COLUMN    SGMODETIQUETA.COMPRIMIDO IS 'Indica se deve imprimir comprimido.';
COMMENT ON    COLUMN    SGMODETIQUETA.POSSCRIPT IS 'indica se a etiqueta é pós script.';
COMMENT ON    COLUMN    SGMODETIQUETA.MODETIQ IS 'indica o nome do jasper.';
COMMENT ON TABLE        SGMUNICIPIO IS 'Tabela de municipios.';
COMMENT ON TABLE        SGPAIS IS 'Tabela de Paises';
COMMENT ON    COLUMN    SGPAIS.CODPAIS IS 'Código do país segundo o ISO 3166-1';
COMMENT ON    COLUMN    SGPAIS.SIGLA3CPAIS IS 'Sigla do país com 3 caracteres.';
COMMENT ON    COLUMN    SGPAIS.DDIPAIS IS 'Código de discagem direta a distancia do pais (ddi)';
COMMENT ON    COLUMN    SGPAIS.SIGLA2CPAIS IS 'Sigla do pais com 2 caracteres';
COMMENT ON    COLUMN    SGPAIS.CODBACENPAIS IS 'Código do país na tabela do BACEN (Brasil=1058)';
COMMENT ON    COLUMN    SGPAIS.CODEANPAIS IS 'Código do país na tabela EAN.';
COMMENT ON TABLE        SGPREFERE1 IS 'Preferências do módulo STD (Gerais).';
COMMENT ON    COLUMN    SGPREFERE1.USAORCSEQ IS 'FLAG QUE INDICA SE USA PEDIDO SEQUENCIAL NO ORÇAMENTO';
COMMENT ON    COLUMN    SGPREFERE1.CODTIPOMOV2 IS 'Tipo de movimento para orçamento';
COMMENT ON    COLUMN    SGPREFERE1.TIPOPREFCRED IS 'Indica quando deve ser feita a verificação do crédito:
FV - Fechamento de venda;
II - Inserção de ítem;
AB - Ambos;';
COMMENT ON    COLUMN    SGPREFERE1.CODTIPOMOV4 IS 'Tipo de movimento para serviços';
COMMENT ON    COLUMN    SGPREFERE1.TABFRETEVD IS 'Indica se a aba frete será obrigatória na tela de vendas.
';
COMMENT ON    COLUMN    SGPREFERE1.TABADICVD IS 'Indica se a aba adicionais será obrigatória na tela de vendas.
';
COMMENT ON    COLUMN    SGPREFERE1.TRAVATMNFVD IS 'Trava tela de venda para não receber tipo de movimento de NF na inserção.
';
COMMENT ON    COLUMN    SGPREFERE1.TIPOVALIDORC IS 'Opção de validade para impressão nos orçamentos:
"N"=número de dias ; 
"D" data 
';
COMMENT ON    COLUMN    SGPREFERE1.CLIMESMOCNPJ IS 'Permitir clientes com mesmo CNPJ:
S-SIM N-NÃO
';
COMMENT ON    COLUMN    SGPREFERE1.CNPJOBRIGCLI IS 'CNPJ Obrigatório para o cadastro de clientes S ou N
';
COMMENT ON    COLUMN    SGPREFERE1.CODEMPTN IS 'Código da empresa para transportadora';
COMMENT ON    COLUMN    SGPREFERE1.CODFILIALTN IS 'Código da filial para transportadora.';
COMMENT ON    COLUMN    SGPREFERE1.CODTRAN IS 'Código da transportadora.';
COMMENT ON    COLUMN    SGPREFERE1.ESTLOTNEG IS 'Permitir estoque de lote negativo
';
COMMENT ON    COLUMN    SGPREFERE1.NATVENDA IS 'CFOP habilitada na tela de venda
';
COMMENT ON    COLUMN    SGPREFERE1.IPIVENDA IS 'Habilita campo IPI ';
COMMENT ON    COLUMN    SGPREFERE1.CUSTOSICMS IS 'Preço de custo na compra sem ICMS.';
COMMENT ON    COLUMN    SGPREFERE1.CASASDECFIN IS 'Define as casas decimas para assuntos financeiros';
COMMENT ON    COLUMN    SGPREFERE1.COMISPDUPL IS 'Cálculo de comissões por duplicata
"S" calcula pelas parcelas do contas a receber.
"N" calcula pelo valor da venda.';
COMMENT ON    COLUMN    SGPREFERE1.CODEMPT6 IS 'Código da empresa para tipo de movimento de inventário';
COMMENT ON    COLUMN    SGPREFERE1.CODFILIALT6 IS 'Código da filial para tipo de movimento de inventário';
COMMENT ON    COLUMN    SGPREFERE1.CODTIPOMOV6 IS 'Código de tipo de movimento de inventário';
COMMENT ON    COLUMN    SGPREFERE1.BLOQCOMPRA IS 'FLAG PARA BLOQUEAR A COMPRA';
COMMENT ON    COLUMN    SGPREFERE1.VENDAMATPRIM IS 'Flag para permitir venda de matéria prima (S/N).';
COMMENT ON    COLUMN    SGPREFERE1.VENDAPATRIM IS 'Flag para permitir venda de matéria prima (S/N)';
COMMENT ON    COLUMN    SGPREFERE1.PEPSPROD IS 'Flag que indica se deve aparecer o campo custo peps na tela de cadastro de produtos.';
COMMENT ON    COLUMN    SGPREFERE1.CNPJFOROBRIG IS 'Flag que indica se o CNPJ do fornecedor é obrigatório.';
COMMENT ON    COLUMN    SGPREFERE1.INSCESTFOROBRIG IS 'Flag que indica se a insc. est. do fornecedor é obrigatória.';
COMMENT ON    COLUMN    SGPREFERE1.BUSCAPRODSIMILAR IS 'Flag que indica se ao entrar com código de produto em textfield deverá buscar os produtos similares.';
COMMENT ON    COLUMN    SGPREFERE1.MULTIALMOX IS 'Flag que indica o trabalho com multiplos almoxarifados.';
COMMENT ON    COLUMN    SGPREFERE1.CODEMPT8 IS 'Cod. emp. do tipo de movimento padrão para RMA.';
COMMENT ON    COLUMN    SGPREFERE1.CODFILIALT8 IS 'Cod. filial do tipo de movimento padrão para RMA.';
COMMENT ON    COLUMN    SGPREFERE1.CODTIPOMOV8 IS 'Codigo do tipo de movimento padrão para RMA.';
COMMENT ON    COLUMN    SGPREFERE1.ESTNEGGRUP IS 'Define se controla o estoque negativo por grupo.';
COMMENT ON    COLUMN    SGPREFERE1.USATABPE IS 'Flag para usar tabela de prazos de entrega.';
COMMENT ON    COLUMN    SGPREFERE1.DESCCOMPPED IS 'Indica se será utilizada a descrição completa nos orçamentos e pedidos.';
COMMENT ON    COLUMN    SGPREFERE1.OBSCLIVEND IS 'Flag para mostrar observações do cliente na tela de venda.';
COMMENT ON    COLUMN    SGPREFERE1.CONTESTOQ IS 'Flag que define se o sistema vai trabalhar com estoque.';
COMMENT ON    COLUMN    SGPREFERE1.DIASPEDT IS 'S - mostra data de entrega no pedido
N - mostra número de dias para entrega';
COMMENT ON    COLUMN    SGPREFERE1.RECALCPCVENDA IS 'flag que marca se deve recalcular os valores do item quando alterar o cabeçalho';
COMMENT ON    COLUMN    SGPREFERE1.RECALCPCORC IS 'flag que marca se deve recalcular os valores do item quando alterar o cabeçalho';
COMMENT ON    COLUMN    SGPREFERE1.USALAYOUTPED IS 'define se usa layout proprio para pedido';
COMMENT ON    COLUMN    SGPREFERE1.VERIFALTPARCVENDA IS 'FLAG INDICA SE FAZ VERIFICAÇÃO PARA ALTERAR PARCELA DA VENDA';
COMMENT ON    COLUMN    SGPREFERE1.BUSCACODPRODGEN IS 'BUSCA GENERICA DO CÓDIGO DO PRODUTO';
COMMENT ON    COLUMN    SGPREFERE1.FILBUSCGENPROD IS 'FILTAR POR PRODUTO NA BUSCA COM O CÓDIGO DE BARRAS';
COMMENT ON    COLUMN    SGPREFERE1.FILBUSCGENREF IS 'FILTAR POR REFERENCIA NA BUSCA COM O CÓDIGO DE BARRAS';
COMMENT ON    COLUMN    SGPREFERE1.FILBUSCGENCODBAR IS 'FILTAR POR CODIGO DE BARRAS NA BUSCA COM O CÓDIGO DE BARRAS';
COMMENT ON    COLUMN    SGPREFERE1.FILBUSCGENCODFAB IS 'FILTAR POR CODIGO DO FABRICANTE NA BUSCA COM O CÓDIGO DE BARRAS';
COMMENT ON    COLUMN    SGPREFERE1.FILBUSCGENCODFOR IS 'Busca produro por código de fornecedor.';
COMMENT ON    COLUMN    SGPREFERE1.BUSCAVLRULTCOMPRA IS 'Busca o valor da ultima compra na tela de compras.';
COMMENT ON    COLUMN    SGPREFERE1.USAIMGASSORC IS 'Flag para usar imagem de assinatura no orçamento.';
COMMENT ON    COLUMN    SGPREFERE1.IMGASSORC IS 'Imagem de assinatura pra orçamento.';
COMMENT ON    COLUMN    SGPREFERE1.CONSISTCPFCLI IS 'Indica se deve consistir CPF no cliente';
COMMENT ON    COLUMN    SGPREFERE1.CONSISTEIECLI IS 'Indica se deve consistir IE no cliente';
COMMENT ON    COLUMN    SGPREFERE1.CONSISTEIEFOR IS 'Indica se deve consistir IE no fornecedor.';
COMMENT ON    COLUMN    SGPREFERE1.CONSISTECPFFOR IS 'Indica se deve validar o CPF do fornecedor pessoa física.';
COMMENT ON    COLUMN    SGPREFERE1.USANOMEVENDORC IS 'Flag "S" para indicar o uso de nome do vendedor no orçamento, caso contrário utilizará o nome da empresa.';
COMMENT ON    COLUMN    SGPREFERE1.SISCONTABIL IS 'Seleciona o sistema de contabilidade utilizado.
00 - Nenhum
01 - Freedom Contábil
02 - Safe Contábil
';
COMMENT ON    COLUMN    SGPREFERE1.ATBANCOIMPBOL IS 'Flag que indica se o banco poderá ser atualizado na tela de impressão de boleto.';
COMMENT ON    COLUMN    SGPREFERE1.TIPOCODBAR IS 'Tipo de código de barras.
1-EAN
2-39';
COMMENT ON    COLUMN    SGPREFERE1.ADICORCOBSPED IS 'Flag que indica se deve carregar os códigos dos orçamentos nas observações dos pedidos, gerados a partir de orçamentos.';
COMMENT ON    COLUMN    SGPREFERE1.CODEMPMENSORC IS 'Código da empresa para a mensagem padrão para orçamento.';
COMMENT ON    COLUMN    SGPREFERE1.CODFILIALMENSORC IS 'Código da filial para mensagem padrão para orçamento.';
COMMENT ON    COLUMN    SGPREFERE1.CODMENSORC IS 'Código da mensagem padrão para orçamento.';
COMMENT ON    COLUMN    SGPREFERE1.CUSTOCOMPRA IS 'Habilita para digitação campo de custo na compra.';
COMMENT ON    COLUMN    SGPREFERE1.TABTRANSPCP IS 'Indica se deve mostrar aba de transportadora na tela de compra.';
COMMENT ON    COLUMN    SGPREFERE1.TABTRANSPORC IS 'Indica se deve mostrar aba de transportadora na tela de orçamento.';
COMMENT ON    COLUMN    SGPREFERE1.TABSOLCP IS 'Habilita e desabilita aba de solicitação na tela de compras.';
COMMENT ON    COLUMN    SGPREFERE1.ADICFRETEBASEICM IS 'Indica se deve permitir adicionar frete à base de calculo do ICMS.
';
COMMENT ON    COLUMN    SGPREFERE1.PRECOCPREL IS 'Indica se deve mostrar o preço de compra nos relatórios de compra.';
COMMENT ON    COLUMN    SGPREFERE1.MULTICOMIS IS 'Habilita e desabilita multi-comissionados.';
COMMENT ON    COLUMN    SGPREFERE1.USUATIVCLI IS 'S - Define controle de acesso para ativação de cliente por usuário.';
COMMENT ON    COLUMN    SGPREFERE1.CODEMPHISTREC IS 'Código da empresa do histórico padrão para lançamentos financeiros provenientes do contas a receber.';
COMMENT ON    COLUMN    SGPREFERE1.CODFILIALHISTREC IS 'Código da filial do histórico padrão para lançamentos financeiros provenientes do contas a receber.';
COMMENT ON    COLUMN    SGPREFERE1.CODHISTREC IS 'Código do histórico padrão para lançamentos financeiros provenientes do contas a receber.';
COMMENT ON    COLUMN    SGPREFERE1.CODEMPHISTPAG IS 'Código da empresa do histórico padrão para lançamentos financeiros provenientes do contas a pagar.';
COMMENT ON    COLUMN    SGPREFERE1.CODFILIALHISTPAG IS 'Código da filial do histórico padrão para lançamentos financeiros provenientes do contas a pagar.';
COMMENT ON    COLUMN    SGPREFERE1.CODHISTPAG IS 'Código do histórico padrão para lançamentos financeiros provenientes do contas a pagar.';
COMMENT ON    COLUMN    SGPREFERE1.ESTITRECALTDTVENC IS 'Flag que indica se estorna recebimento ao alterar data de vencimento da parcela com baixa automatica.';
COMMENT ON    COLUMN    SGPREFERE1.LCREDGLOBAL IS 'Indica se a liberação de crédito deve ser agrupada, para clientes com sub-clientes.';
COMMENT ON    COLUMN    SGPREFERE1.VDMANUTCOMOBRIG IS 'Indica se o filtro de vendedor é obrigatória na tela de manutenção de comissões.';
COMMENT ON    COLUMN    SGPREFERE1.CLASSPED IS 'Classe padrão para pedidos gráficos.';
COMMENT ON    COLUMN    SGPREFERE1.TIPOCLASSPED IS 'Indica o tipo de classe padrão para orçamentos.
QA - Resultset como parâmetro (Query na Aplicação);
QJ - Parametros de filtro (Query no Jasper).
';
COMMENT ON    COLUMN    SGPREFERE1.USAIBGECLI IS 'Indica se deve usar a tabela de IBGE de cidades para o cadastro de clientes.';
COMMENT ON    COLUMN    SGPREFERE1.USAIBGEFOR IS 'Indica se deve usar a tabela de IBGE de cidades para o cadastro de fornecedores.';
COMMENT ON    COLUMN    SGPREFERE1.USAIBGETRANSP IS 'Indica se deve usar a tabela de IBGE de cidades para o cadastro de transportadoras.';
COMMENT ON    COLUMN    SGPREFERE1.SOMAVOLUMES IS 'Indica se deve somar as quantidades dos itens de venda e lançar no campo "volumes".';
COMMENT ON    COLUMN    SGPREFERE1.BUSCACEP IS 'Indica se deve habilitar o botão para busca de ceps nos cadastros.';
COMMENT ON    COLUMN    SGPREFERE1.URLWSCEP IS 'URL do web service de busca de endereço pelo cep.';
COMMENT ON    COLUMN    SGPREFERE1.CLASSCP IS 'Classe padrão para pedido de compra.';
COMMENT ON    COLUMN    SGPREFERE1.LABELOBS01CP IS 'Descrição para o campo coringa obs01 da tabela CPCOMPRA.';
COMMENT ON    COLUMN    SGPREFERE1.LABELOBS02CP IS 'Descrição para o campo coringa obs02 da tabela CPCOMPRA.';
COMMENT ON    COLUMN    SGPREFERE1.LABELOBS03CP IS 'Descrição para o campo coringa obs03 da tabela CPCOMPRA.';
COMMENT ON    COLUMN    SGPREFERE1.LABELOBS04CP IS 'Descrição para o campo coringa obs04 da tabela CPCOMPRA.';
COMMENT ON    COLUMN    SGPREFERE1.CONSISTEIEPF IS 'Indica se deve consistir a inscrição estadual de clientes do tipo pessoa física.';
COMMENT ON    COLUMN    SGPREFERE1.CREDICMSSIMPLES IS 'Indica se deve destacar crédito de ICMS (empesa simples.)';
COMMENT ON    COLUMN    SGPREFERE1.CODEMPMS IS 'Código da empresa da mensagem de destaque de ICMS de empresa enquadrada no simples.';
COMMENT ON    COLUMN    SGPREFERE1.CODFILIALMS IS 'Código da filial da mensagem de destaque de ICMS para empresa enquadrada no simples.';
COMMENT ON    COLUMN    SGPREFERE1.CODMENSICMSSIMPLES IS 'Código da mensagem a ser destacada na nota fiscal, quando empresa destacar crédito de icms, estando enquadrada no simples.';
COMMENT ON    COLUMN    SGPREFERE1.GERACOMISVENDAORC IS 'Indica se deve gerar gerar comissão padrão nas vendas 
geradas através de busca de orçamentos.';
COMMENT ON    COLUMN    SGPREFERE1.GERACODUNIF IS 'Indica se deve gerar código unificado na tabela SGUNIFCOD 
para uso como destinatario ou remetente no conhecimento de frete.';
COMMENT ON    COLUMN    SGPREFERE1.CODTIPOMOV9 IS 'Código do tipo de movimento padrão para conhecimento de frete.';
COMMENT ON    COLUMN    SGPREFERE1.CODFILIALT9 IS 'Código da filial do tipo de movimento para conheciemento de frete.';
COMMENT ON    COLUMN    SGPREFERE1.CODEMPT9 IS 'Código da empresa do tipo de moviento padrão para conhecimento de frete.';
COMMENT ON    COLUMN    SGPREFERE1.CODEMPJP IS 'Código da empresa da conta de planejamento de Juros Pagos.';
COMMENT ON    COLUMN    SGPREFERE1.CODFILIALJP IS 'Código da filial da conta de planejamento de Juros Pagos.';
COMMENT ON    COLUMN    SGPREFERE1.CODPLANJP IS 'Código da conta de planejamento de Juros Pagos.';
COMMENT ON    COLUMN    SGPREFERE1.CODEMPJR IS 'Código da empresa da conta de planejamento de Juros Recebidos.';
COMMENT ON    COLUMN    SGPREFERE1.CODFILIALJR IS 'Código da filial da conta de planejamento de Juros Recebidos.';
COMMENT ON    COLUMN    SGPREFERE1.CODPLANJR IS 'Código da conta de planejamento de Juros Recebidos.';
COMMENT ON    COLUMN    SGPREFERE1.CODEMPDR IS 'Código da empresa da conta de planejamento de Descontos recebidos.';
COMMENT ON    COLUMN    SGPREFERE1.CODFILIALDR IS 'Código da filial da conta de planejamento de Descontos recebidos.';
COMMENT ON    COLUMN    SGPREFERE1.CODPLANDR IS 'Código da conta de planejamento de Descontos recebidos.';
COMMENT ON    COLUMN    SGPREFERE1.CODEMPDC IS 'Código da empresa da conta de planejamento de Descontos concedidos.';
COMMENT ON    COLUMN    SGPREFERE1.CODFILIALDC IS 'Código da filial da conta de planejamento de Descontos concedidos.';
COMMENT ON    COLUMN    SGPREFERE1.CODPLANDC IS 'Código da conta de planejamento de Descontos concedidos.';
COMMENT ON    COLUMN    SGPREFERE1.GERAPAGEMIS IS 'Geração de contas a pagar por data de emissão.';
COMMENT ON    COLUMN    SGPREFERE1.LANCAFINCONTR IS 'Indica se deve habilitar o vinculo entre lançamento financeiro (fnsublanca) e contrato/projeto. Para
apuração de custos.';
COMMENT ON    COLUMN    SGPREFERE1.LANCARMACONTR IS 'Indica se eve habilitar o vinculo entre rma e contrato/projeto. Para apuração de custos.';
COMMENT ON    COLUMN    SGPREFERE1.VISUALIZALUCR IS 'Indica se deve habilitar a aba "Lucratividade" na tela de venda.';
COMMENT ON    COLUMN    SGPREFERE1.CLASSNFE IS 'Classe do plugin de integração Nfe';
COMMENT ON    COLUMN    SGPREFERE1.DIRNFE IS 'Diretório padrão para arquivos NFE (windows).';
COMMENT ON    COLUMN    SGPREFERE1.DIRNFELIN IS 'Diretório padrão para arquivos NFE (linux).';
COMMENT ON    COLUMN    SGPREFERE1.FORMATODANFE IS '1 - Retrato, 2 - Paisagem';
COMMENT ON    COLUMN    SGPREFERE1.AMBIENTENFE IS '1 - Produção, 2 - Homologação';
COMMENT ON    COLUMN    SGPREFERE1.PROCEMINFE IS 'Identificador do processo de emissão da NFe:
0 - emissão de NF-e com aplicativo do contribuinte;
3 - emissão NF-e pelo contribuinte com aplicativo fornecido pelo Fisco.';
COMMENT ON    COLUMN    SGPREFERE1.VERPROCNFE IS 'Identificador da versão do processo de emissão (informar a ersão do aplicativo emissor de NFe)';
COMMENT ON    COLUMN    SGPREFERE1.KEYLICNFE IS 'Chave de licenciamento NFE.
';
COMMENT ON    COLUMN    SGPREFERE1.DTVENCTONFE IS 'Data de vencimento da licença NFE.
';
COMMENT ON    COLUMN    SGPREFERE1.INFADPRODNFE IS 'Indica se deve incluir as informações adicionais do produto na nota fiscal eletronica (Campo HinfAdProd)';
COMMENT ON    COLUMN    SGPREFERE1.CODEMPNF IS 'Código da empresa do email padrão para envio de nfe.';
COMMENT ON    COLUMN    SGPREFERE1.CODFILIALNF IS 'Código da filial do email padrão para envio nfe';
COMMENT ON    COLUMN    SGPREFERE1.CODEMAILNF IS 'Código do email padrão para envio de nfe.';
COMMENT ON    COLUMN    SGPREFERE1.EXIBEPARCOBSDANFE IS 'Indica se deve adicionar o desdobramento das parcelas nas observações da DANFE.';
COMMENT ON    COLUMN    SGPREFERE1.REGIMETRIBNFE IS 'Regime tributário para NFE.
1 - Simples Nacional
2 - Simples Nacional (excesso de sub-limite)
3 - Normal';
COMMENT ON    COLUMN    SGPREFERE1.INFCPDEVOLUCAO IS 'Indica se deve informar a compra na devolução.';
COMMENT ON    COLUMN    SGPREFERE1.INFVDREMESSA IS 'Indica de informa nota de remessa.';
COMMENT ON    COLUMN    SGPREFERE1.GERARECEMIS IS 'Indica se deve gerar contas a receber a partir da data de emissão do pedido.';
COMMENT ON    COLUMN    SGPREFERE1.RETENSAOIMP IS 'Indica se deve realizar a retensão de impostos na emissão da nota, reduzindo o valor liquido da nota fiscal.';
COMMENT ON    COLUMN    SGPREFERE1.TIPOCUSTOLUC IS 'Informa qual o tipo de custo deve ser usado no cálculo de lucratividade de orçamentos/pedidos.
U - Ultima compra;
M - MPM
P - PEPS';
COMMENT ON    COLUMN    SGPREFERE1.TABIMPORTCP IS 'Indica se deve habilitar a aba importação na tela de compras.';
COMMENT ON    COLUMN    SGPREFERE1.HABVLRTOTITORC IS 'Indica se deve habilitar o valor total do item para digitação, na tela de orçamentos.';
COMMENT ON    COLUMN    SGPREFERE1.USABUSCAGENPRODCP IS 'Indica se deve realizar a busca genérica de produtos na compra.';
COMMENT ON    COLUMN    SGPREFERE1.ADICOBSORCPED IS 'Indica se deve transferir as observações do orçamento para o pedido.';
COMMENT ON    COLUMN    SGPREFERE1.USAPRECOCOT IS 'Indica se deve utilizar o preço de cotações para pedidos de compras.';
COMMENT ON    COLUMN    SGPREFERE1.BLOQPRECOAPROV IS 'Indica se deve bloquear o faturamento de pedido de compra com preço não aprovado.';
COMMENT ON    COLUMN    SGPREFERE1.CODEMPFT IS 'Código da empresa do tipo de fornecedor para transportadoras.';
COMMENT ON    COLUMN    SGPREFERE1.CODFILIALFT IS 'Código da filial do tipo de fornecedor para transportadoras.';
COMMENT ON    COLUMN    SGPREFERE1.CODTIPOFORFT IS 'Código do tipo de fornecedor para transportadoras.';
COMMENT ON    COLUMN    SGPREFERE1.USAPRECOCOMIS IS 'Indica se deve utilizar o preço para comissionamento do cadastro de produtos, para calculo de comissioament especial por seção de produção.';
COMMENT ON    COLUMN    SGPREFERE1.ESPECIALCOMIS IS 'Indica se deve utilizar o mecanismo de comissionamento especial (por setor de produção)';
COMMENT ON    COLUMN    SGPREFERE1.CODFILIALTS IS 'Código da filial do tipo de movimento para orçamentos de serviço.';
COMMENT ON    COLUMN    SGPREFERE1.CODTIPOMOVS IS 'Código do tipo de movimento para orçamentos de serviço.';
COMMENT ON    COLUMN    SGPREFERE1.CODEMPTS IS 'Código da empresa do tipo de movimento para orçamentos de serviço.';
COMMENT ON    COLUMN    SGPREFERE1.CODEMPSV IS 'Código da empresa de plano de pagamento sem valor financeiro para uso em devoluções e afins.';
COMMENT ON    COLUMN    SGPREFERE1.CODFILIALSV IS 'Código da filial de plano de pagamento sem valor financeiro para uso em devoluções e afins.';
COMMENT ON    COLUMN    SGPREFERE1.CODPLANOPAGSV IS 'Código de plano de pagamento sem valor financeiro para uso em devoluções e afins.';
COMMENT ON    COLUMN    SGPREFERE1.ARREDPRECO IS 'Indice de arredondamento de precos:
null ou 0 = sem arredondamento;
> 0 fator de arredondamento decimal';
COMMENT ON    COLUMN    SGPREFERE1.CODEMPPC IS 'Código da empresa padrão planejamento para pagamento com cheques.';
COMMENT ON    COLUMN    SGPREFERE1.CODFILIALPC IS 'Código da empresa para o planejamento padrão para pagamento com cheques.';
COMMENT ON    COLUMN    SGPREFERE1.CODPLANPC IS 'Código do planejamento padrão para pagamento com cheques.';
COMMENT ON    COLUMN    SGPREFERE1.TPNOSSONUMERO IS 'Indica o padrão para geração do nosso número (boletos e arquivos de remessa)
D - Número do documento (doc)
R - Número do contas a receber (codrec)
S - Sequencial único (recomendado)';
COMMENT ON    COLUMN    SGPREFERE1.IMPDOCBOL IS 'Define se o número  NF será impresso no campo documento do boleto (S/N).
';
COMMENT ON    COLUMN    SGPREFERE1.FECHACAIXA IS 'Indica se deve bloquear caixas para lançamentos retroativos.';
COMMENT ON    COLUMN    SGPREFERE1.FECHACAIXAAUTO IS 'Indica se deve realizar o fechamento de caixas automaticamente, 
ou através de procedimento manual.';
COMMENT ON    COLUMN    SGPREFERE1.NUMDIGIDENTTIT IS 'Numero de digitos para campo de identificacao do titulo em arquivos de remessa padrao cnab (parametro corretivo para periodo intermediario entre a versao 1.2.4.1 e 1.2.4.2);';
COMMENT ON    COLUMN    SGPREFERE1.KEYLICEFD IS 'Chave de licenciamento do módulo Sped EFD.';
COMMENT ON    COLUMN    SGPREFERE1.DTVENCTOEFD IS 'Data de vencimento da licencao do SPED Efd.';
COMMENT ON    COLUMN    SGPREFERE1.ENCORCPROD IS 'Indica se deve encaminhar orçamentos contendo produtos acabados para a produção (Sistema Pull)
';
COMMENT ON    COLUMN    SGPREFERE1.CODEMPIM IS 'Código do tipo de movimento para NF de importação.';
COMMENT ON    COLUMN    SGPREFERE1.CODFILIALIM IS 'Código da filial do tipo de movimento para NF de importação';
COMMENT ON    COLUMN    SGPREFERE1.CODTIPOMOVIM IS 'Código do tipo de movimento para nota fiscal de importação.';
COMMENT ON    COLUMN    SGPREFERE1.COMISSAODESCONTO IS 'Indica se deve habilitar o mecanismo de comissionamento progressivo, de acordo com o desconto concedido.
"S" - Sim
"N" - Não
';
COMMENT ON    COLUMN    SGPREFERE1.CODEMPHC IS 'Código da empresa para histórico padrão para baixa CNAB.
';
COMMENT ON    COLUMN    SGPREFERE1.CODFILIALHC IS 'Código da filial para histórico padrão para baixa CNAB.
';
COMMENT ON    COLUMN    SGPREFERE1.CODHISTCNAB IS 'Código do histórico padrão para baixa CNAB.
';
COMMENT ON    COLUMN    SGPREFERE1.ALINHATELALANCA IS 'Indica se deve alinhar a tela de lançamentos financeiros a direta.
S - Sim
N - Não
';
COMMENT ON    COLUMN    SGPREFERE1.IDENTCLIBCO IS 'Identificação cliente (SIACC)- D - Documento - CPF/CNPJ / C - Código do cliente ';
COMMENT ON    COLUMN    SGPREFERE1.VDPRODQQCLAS IS 'Permite venda de produto independentemente da classificação.';
COMMENT ON    COLUMN    SGPREFERE1.UTILIZATBCALCCA IS 'Utiliza tabela para cálculo do custo de aquisição.';
COMMENT ON    COLUMN    SGPREFERE1.BLOQPEDVD IS 'Bloqueia pedido de venda após emissão.';
COMMENT ON TABLE        SGPREFERE2 IS 'Preferências do módulo ATD.';
COMMENT ON    COLUMN    SGPREFERE2.CABTERMR01 IS 'Cabeçalho 1 do termo de recebimento.';
COMMENT ON    COLUMN    SGPREFERE2.CABTERMR02 IS 'Cabeçalho 2 do termo de recebimento.';
COMMENT ON    COLUMN    SGPREFERE2.RODTERMR IS 'Rodapé do termo de recebimento.';
COMMENT ON TABLE        SGPREFERE3 IS 'Preferencias do módulo TMK.';
COMMENT ON    COLUMN    SGPREFERE3.AUTOHORATEND IS 'Define se utiliza contagem automatica do tempo de atendimento.';
COMMENT ON    COLUMN    SGPREFERE3.CODATIVCE IS 'Código da atividade padrão para envio de campanha.';
COMMENT ON    COLUMN    SGPREFERE3.CODATIVTE IS 'Código da atividade padrão para envio de campanha frustrado.';
COMMENT ON    COLUMN    SGPREFERE3.BLOQATENDCLIATRASO IS 'Indica se deve bloquear atendimentos para clientes em atraso. S - Sim
N - Não
';
COMMENT ON    COLUMN    SGPREFERE3.MOSTRACLIATRASO IS 'Indica se deve exibir informação de cliente em atraso, na tela de gerenciamento de atendimentos.
S - Sim
N - Não';
COMMENT ON    COLUMN    SGPREFERE3.CODEMPNC IS 'Código da empresa do email de notificação de chamados a técnicos designados.';
COMMENT ON    COLUMN    SGPREFERE3.CODFILIALNC IS 'Código da filial do email de notificação de chamados a técnicos designados.';
COMMENT ON    COLUMN    SGPREFERE3.CODEMAILNC IS 'Código do email de notificação de chamados a técnicos designados.';
COMMENT ON    COLUMN    SGPREFERE3.CODEMPEC IS 'Código da empresa do email de notificação de chamados ao cliente.
';
COMMENT ON    COLUMN    SGPREFERE3.CODFILIALEC IS 'Código da filial do email de notificação de chamados ao cliente.
';
COMMENT ON    COLUMN    SGPREFERE3.CODEMAILEC IS 'Código do email de notificação de chamados ao cliente.
';
COMMENT ON TABLE        SGPREFERE4 IS 'Preferências do módulo de PDV
';
COMMENT ON    COLUMN    SGPREFERE4.CODEMPCL IS 'Código da empresa para cliente.';
COMMENT ON    COLUMN    SGPREFERE4.CODFILIALCL IS 'Código filial para cliente.';
COMMENT ON    COLUMN    SGPREFERE4.CODCLI IS 'Código do cliente padrão para venda.';
COMMENT ON    COLUMN    SGPREFERE4.CODEMPPD IS 'CODIGO DA EMPRESA DO PRODUTO PARA FRETE';
COMMENT ON    COLUMN    SGPREFERE4.CODFILIALPD IS 'CODIGO DA FILIAL DO PRODUTO PARA FRETE';
COMMENT ON    COLUMN    SGPREFERE4.CODPROD IS 'CODIGO DO PRODUTO PRA FRETE';
COMMENT ON    COLUMN    SGPREFERE4.ADICPDV IS 'flag que indica se mostra frete no fechamento da venda no PDV';
COMMENT ON    COLUMN    SGPREFERE4.APROVORC IS 'FLAG QUE PERMITE APROVAÇÃO DO ORÇAMENTO DA TELA DE CADASTRO DO ORÇAMENTO';
COMMENT ON    COLUMN    SGPREFERE4.DIASVENCORC IS 'DIAS PARA O VENCIMENTO DO ORÇAMENTO.';
COMMENT ON    COLUMN    SGPREFERE4.USABUSCAGENPROD IS 'indica se abilita o campo para fazer a busca generica de produtos';
COMMENT ON    COLUMN    SGPREFERE4.AUTOFECHAVENDA IS 'Flag indica se chama a tela de fecha venda automatico quando buscar orçamento.';
COMMENT ON    COLUMN    SGPREFERE4.USALOTEORC IS 'Flag que indica se usa lote na tela de orçamento.';
COMMENT ON    COLUMN    SGPREFERE4.HABRECEBER IS 'Habilita aba de receber no fechamento da venda de ECF.';
COMMENT ON TABLE        SGPREFERE5 IS 'Preferências do módulo de PCP
';
COMMENT ON    COLUMN    SGPREFERE5.MESESDESCCP IS 'Número de meses para descarte de análises de contra-prova.';
COMMENT ON    COLUMN    SGPREFERE5.CODTIPOMOV IS 'Código do tipo de movimento padrão para Ordem de produção.';
COMMENT ON    COLUMN    SGPREFERE5.CODEMPTM IS 'Código da empresa do tipo de movimento padrão para Ordem de Produção.';
COMMENT ON    COLUMN    SGPREFERE5.CODFILIALTM IS 'Código da filial do tipo de movimento padrão para Ordem de Produção.';
COMMENT ON    COLUMN    SGPREFERE5.SITRMAOP IS 'Situação da RMA gerada pela OP.
Pode ser:
"PE" - Pendente
"AF" - Aprovada 
"EF" - Expedida ';
COMMENT ON    COLUMN    SGPREFERE5.IMGASSRESP IS 'Imagem da assinatura do responsável técnico.';
COMMENT ON    COLUMN    SGPREFERE5.BAIXARMAAPROV IS 'Indica se deve baixar o estoque de insumos em RMA"s aprovadas não expedidas.';
COMMENT ON    COLUMN    SGPREFERE5.RATAUTO IS 'Defini rateio automatico de itens de OP.';
COMMENT ON    COLUMN    SGPREFERE5.APAGARMAOP IS 'Indica se deve permitir a exclusão de RMAs geradas por outro usuário.
Regra se aplica apenas em RMA vinculadas a Ordens de produção.';
COMMENT ON    COLUMN    SGPREFERE5.NOMERELANAL IS 'Indica qual o nome a ser impresso no relatório de análise.
"U" - Usuário de cadastrou a anáse;
"R" - Responsável pela produção;';
COMMENT ON    COLUMN    SGPREFERE5.SITPADOP IS 'Status padrão da OP após inserção.
"PE" Pendente
"FN" Finalizada';
COMMENT ON    COLUMN    SGPREFERE5.SITPADOPCONV IS 'Status padrão da OP de conversão de produtos após inserção.
"PE" Pendente
"FN" Finalizada';
COMMENT ON    COLUMN    SGPREFERE5.HABCONVCP IS 'Indica se deve habilitar conversão de produtos na compra.';
COMMENT ON    COLUMN    SGPREFERE5.PRODETAPAS IS 'Indica se deve permitir a finalização de Ops em etapas.';
COMMENT ON    COLUMN    SGPREFERE5.CODEMPTS IS 'Código da empresa para tipo de movimento para entrada de subprodutos.
';
COMMENT ON    COLUMN    SGPREFERE5.CODFILIALTS IS 'Código da filial para tipo de movimento para entrada de subprodutos.
';
COMMENT ON    COLUMN    SGPREFERE5.CODTIPOMOVSP IS 'Código do tipo de movimento para entrada de subprodutos.
';
COMMENT ON    COLUMN    SGPREFERE5.CODEMPEN IS 'Código da empresa do tipo de movimento padrão para envio de remessa para produção externa.';
COMMENT ON    COLUMN    SGPREFERE5.CODFILIALEN IS 'Código da filial do tipo de movimento padrão para envio de remessa para produção externa.';
COMMENT ON    COLUMN    SGPREFERE5.CODTIPOMOVEN IS 'Código do tipo de movimento padrão para envio de remessa para produção externa.';
COMMENT ON    COLUMN    SGPREFERE5.CODEMPRE IS 'Código da empresa do tipo de movimento padrão para retorno de produção externa.';
COMMENT ON    COLUMN    SGPREFERE5.CODFILIALRE IS 'Código da filial do tipo de movimento padrão para retorno de produção externa.';
COMMENT ON    COLUMN    SGPREFERE5.CODTIPOMOVRE IS 'Código do tipo de movimento padrão para retorno de produção externa.';
COMMENT ON TABLE        SGPREFERE6 IS 'Tabela de preferências Febraban (SIACC E CNAB)';
COMMENT ON    COLUMN    SGPREFERE6.NOMEEMP IS 'Nome da empresa para remessa.';
COMMENT ON    COLUMN    SGPREFERE6.NOMEEMPCNAB IS 'Nome da empresa para cnab.';
COMMENT ON TABLE        SGPREFERE7 IS 'Tabela de preferências para mecanismo de venda consignada.';
COMMENT ON    COLUMN    SGPREFERE7.CODTIPOMOVCO IS 'Tipo de movimento de consignação.';
COMMENT ON    COLUMN    SGPREFERE7.CODTIPOMOVTV IS 'Tipo de movimento de pedido de venda.';
COMMENT ON    COLUMN    SGPREFERE7.CODTIPOMOVTP IS 'Tipo de movimento de venda.';
COMMENT ON    COLUMN    SGPREFERE7.CODEMPPV IS 'Código da empresa do planejamento padrão para venda consignada.';
COMMENT ON    COLUMN    SGPREFERE7.CODEMPPC IS 'Código da empresa do planejamento padrão para consignação.';
COMMENT ON    COLUMN    SGPREFERE7.CODFILIALPC IS 'Código da filial do planejamento padrão para consignação.';
COMMENT ON    COLUMN    SGPREFERE7.CODPLANCONSIG IS 'Código do planejamento padrão para consignação.';
COMMENT ON    COLUMN    SGPREFERE7.CODFILIALPV IS 'Código da filial do planejamento padrão para venda consignada.';
COMMENT ON    COLUMN    SGPREFERE7.CODPLANVDCONSIG IS 'Código do planejamento padrão para venda consignada.';
COMMENT ON TABLE        SGPREFERE8 IS 'Tabela de preferencias do módulo GMS.';
COMMENT ON    COLUMN    SGPREFERE8.CODEMPTR IS 'Código da empresa para o tipo de recepção padrão para recebimento com pesagem.';
COMMENT ON    COLUMN    SGPREFERE8.CODFILIALTR IS 'Código da filial para o tipo de recepção padrão para recebimento com pesagem.';
COMMENT ON    COLUMN    SGPREFERE8.CODTIPORECMERC IS 'Código do tipo de recepção padrão para recebimento com pesagem.';
COMMENT ON    COLUMN    SGPREFERE8.CODEMPCM IS 'Código da empresa to tipo de recebimento para coletas';
COMMENT ON    COLUMN    SGPREFERE8.CODFILIALCM IS 'Código da filial do tipo de recebimento para coleta.';
COMMENT ON    COLUMN    SGPREFERE8.CODTIPORECMERCCM IS 'Código do tipo de recebimento para coleta.';
COMMENT ON    COLUMN    SGPREFERE8.CODEMPTC IS 'Código da empresa para o tipo de movimento de compra gera a partir de pedidos de compra.';
COMMENT ON    COLUMN    SGPREFERE8.CODFILIALTC IS 'Código da filial para o tipo de movimento de compra gerada a partir de pedidos de compra.';
COMMENT ON    COLUMN    SGPREFERE8.CODTIPOMOVTC IS 'Código do tipo de movimento padrão para compra, gerada a partir de pedidos de compra. ';
COMMENT ON    COLUMN    SGPREFERE8.GERACHAMADOOS IS 'Indica se deve gerar chamado (CRM) a partir de itens de ordem de serviço.';
COMMENT ON    COLUMN    SGPREFERE8.USAPRECOPECASERV IS 'Indica se deve utilizar o preço do peça consertada no orçamento de serviços.';
COMMENT ON    COLUMN    SGPREFERE8.CODEMPDS IS 'Código da empresa para o tipo de movimento padrão para devolução de peças consertadas.';
COMMENT ON    COLUMN    SGPREFERE8.CODFILIALDS IS 'Código da filial para o tipo de movimento padrão devolução de peças consertadas.';
COMMENT ON    COLUMN    SGPREFERE8.CODTIPOMOVDS IS 'Código do tipo de movimento padrão para devolução de peças consertadas.';
COMMENT ON    COLUMN    SGPREFERE8.CODEMPSE IS 'Código da empresa do produto padrão para serviços.';
COMMENT ON    COLUMN    SGPREFERE8.CODFILIALSE IS 'Código da filial para produto padrão para serviço.';
COMMENT ON    COLUMN    SGPREFERE8.CODPRODSE IS 'Código do produto padrão para serviço.';
COMMENT ON    COLUMN    SGTABELA.SIGLATB IS 'Sigla da tabela.';
COMMENT ON    COLUMN    SGTIPOAGENDA.CODTIPOAGD IS 'Código do tipo de agendamento';
COMMENT ON TABLE        SGUF IS 'Tabela de Unidades Federativas.';
COMMENT ON    COLUMN    SGUF.REGIAOUF IS 'Região geográfica
N - Norte
NE - Nordeste
S - Sul
SE - Sudeste
CO - Centro Oeste';
COMMENT ON TABLE        SGUNIFCOD IS 'Tabela de unificação de códigos para realização de vínculos em tabelas heterogêneas;';
COMMENT ON    COLUMN    SGUNIFCOD.TIPOUNIFCOD IS 'Tipo de código:
"C" - Cliente;
"F" - Fornecedor;
"T" - Transportadora;
"E" - Empresa/Filial;';
COMMENT ON    COLUMN    SGUNIFCOD.DESCUNIFCOD IS 'Descrição do código unificado (no caso de cliente/fornecedor/transportadora utilizar a razão social).';
COMMENT ON    COLUMN    SGUSUARIO.BAIXOCUSTOUSU IS 'Indica se o usuário pode liberar venda abaixo do custo.';
COMMENT ON    COLUMN    SGUSUARIO.APROVCPSOLICITACAOUSU IS 'Indica o nível de aprovação do usuario para solicitação de compras.
ND : Nenhuma
CC : Mesmo Centro de Custo
TD : Todas';
COMMENT ON    COLUMN    SGUSUARIO.APROVRMAUSU IS 'Indica o nível de aprovação do usuario para RMA.
ND : Nenhuma
CC : Mesmo Centro de Custo
TD : Todas';
COMMENT ON    COLUMN    SGUSUARIO.ALTPARCVENDA IS 'FLAG INDICA DE PODE ALTERAR PARCELA DA VENDA';
COMMENT ON    COLUMN    SGUSUARIO.APROVRECEITA IS 'Permite que o usuario aprove venda de produto com receita.';
COMMENT ON    COLUMN    SGUSUARIO.ATIVCLI IS 'Define se o usuário tem acesso para ativar clientes.';
COMMENT ON    COLUMN    SGUSUARIO.CORAGENDA IS 'Cor representativa para visualização na agenda corporativa.';
COMMENT ON    COLUMN    SGUSUARIO.CODEMPCE IS 'Código da empresa para configuração de email.';
COMMENT ON    COLUMN    SGUSUARIO.CODFILIALCE IS 'Código da filial para configuração de email.';
COMMENT ON    COLUMN    SGUSUARIO.CODCONFEMAIL IS 'Código da configuração de email.';
COMMENT ON    COLUMN    SGUSUARIO.CANCELAOP IS 'Permite que o usuário cancele Ordens de Produção geradas
por outros usuários.';
COMMENT ON    COLUMN    SGUSUARIO.VENDAPATRIMUSU IS 'Define se o usuário pode liberar venda de produtos do patrimônio (imobilizado).';
COMMENT ON    COLUMN    SGUSUARIO.RMAOUTCC IS 'Indica se permite criar RMA em outros Centros de custo.';
COMMENT ON    COLUMN    SGUSUARIO.ATIVOUSU IS 'Indica se o usuário está ativo.';
COMMENT ON    COLUMN    SGUSUARIO.VISUALIZALUCR IS 'Indica se o usuário pode visualizar a aba lucratividade na tela de venda.';
COMMENT ON    COLUMN    SGUSUARIO.LIBERACAMPOPESAGEM IS 'Indica se o usuário possui permissão para digitação
do peso nas telas de pesagem (recebimento de mercadoria)';
COMMENT ON TABLE        SVOS IS 'Cabeçalho para ordem de serviço.
';
COMMENT ON TABLE        TKATIVIDADE IS 'Tabela de atividades.';
COMMENT ON TABLE        TKCAMPANHA IS 'Tabela de cadastro de campanhas de marketing.';
COMMENT ON    COLUMN    TKCAMPANHA.CODCAMP IS 'Código da campanha.';
COMMENT ON    COLUMN    TKCAMPANHA.DESCCAMP IS 'Descrição da campanha.';
COMMENT ON    COLUMN    TKCAMPANHA.OBSCAMP IS 'Observações relativas à campanha.';
COMMENT ON TABLE        TKCAMPANHACLI IS 'Tabela de relacionamento entre clientes e campanhas.';
COMMENT ON    COLUMN    TKCAMPANHACLI.CODCAMP IS 'Código da campanha.';
COMMENT ON    COLUMN    TKCAMPANHACLI.CODCLI IS 'Código do cliente.';
COMMENT ON TABLE        TKCAMPANHACTO IS 'Tabela de relacionamento entre contatos e campanhas.';
COMMENT ON    COLUMN    TKCAMPANHACTO.CODCAMP IS 'Código da campanha.';
COMMENT ON    COLUMN    TKCAMPANHACTO.CODCTO IS 'Código do contato.';
COMMENT ON TABLE        TKCAMPANHAEMAIL IS 'Tabela de relacionamento entre campanhas e seus respectivos emails associados.';
COMMENT ON TABLE        TKCONFEMAIL IS 'Tabela de configurações para utilização nos emails de campanhas de marketing.';
COMMENT ON    COLUMN    TKCONFEMAIL.ASSINATREMET IS 'Assinatura do email, do remetente.';
COMMENT ON TABLE        TKCONTATO IS 'Tabela de contatos.';
COMMENT ON    COLUMN    TKCONTATO.CODORIGCONT IS 'Código da origem do contato.';
COMMENT ON    COLUMN    TKCONTATO.CODEMPTO IS 'Código da empresa do tipo de contato.';
COMMENT ON    COLUMN    TKCONTATO.CODFILIALTO IS 'Código da filial do tipo de contato.';
COMMENT ON    COLUMN    TKCONTATO.CODTIPOCONT IS 'Código do tipo de contato.';
COMMENT ON    COLUMN    TKCONTATO.CELCTO IS 'Nro de telefone celular do contato';
COMMENT ON TABLE        TKCTOATIV IS 'Tabela de relacionamento entre contatos e atividades.';
COMMENT ON TABLE        TKCTOGRPINT IS 'Tabela de relacionamento entre contatos e os grupos de interesse.';
COMMENT ON TABLE        TKEMAIL IS 'Tabela de emails para campanhas de marketing.';
COMMENT ON    COLUMN    TKEMAIL.FORMATO IS 'text/plain ou text/html';
COMMENT ON    COLUMN    TKEMAIL.CHARSET IS 'Formato da pagina de códigos.';
COMMENT ON    COLUMN    TKHISTORICO.TIPOHISTTK IS 'Tipo de histórico: 
H - Histórico 
V - Visita ao cliente
N - Visita a novos clientes(não cadastrados)
C - Campanha
O - Cobrança
L - Ligação Pré-venda
P - Ligação Pós-venda
I - Indefinida';
COMMENT ON    COLUMN    TKHISTORICO.CODEMPCA IS 'Código da empresa da campanha.';
COMMENT ON    COLUMN    TKHISTORICO.CODFILIALCA IS 'Código da filial da campanha.';
COMMENT ON    COLUMN    TKHISTORICO.CODCAMP IS 'Código da campanha.';
COMMENT ON    COLUMN    TKHISTORICO.SEQSITCAMP IS 'Sequencia da situação da campanha (para fk com tksitcamp)';
COMMENT ON TABLE        TKORIGCONT IS 'Tabela de origens de contatos ex: lista telefonica, pesquisa na internet, panfletagem, etc...';
COMMENT ON TABLE        TKSITCAMP IS 'Tabela da situação do cantato com relação à campanha, de acordo com a atividade desenvolvida.';
COMMENT ON    COLUMN    VDCLASCLI.SIGLACLASCLI IS 'Sigla ou abreviação da descrição da classificação do cliente (utilizado em alguns relatórios)';
COMMENT ON TABLE        VDCLCOMIS IS 'Classificação de comissões
';
COMMENT ON TABLE        VDCLIAUTP IS 'Pessoas autorizadas a comprar em nome do cliente';
COMMENT ON    COLUMN    VDCLIAUTP.NOMEAUTP IS 'Nome da pessoa autorizada a comprar em nome do cliente.';
COMMENT ON    COLUMN    VDCLIAUTP.ENDAUTP IS 'Endereco da pessoa autorizada a comprar em nome do cliente.';
COMMENT ON    COLUMN    VDCLIAUTP.NUMAUTP IS 'Número do endereço da pessoa autorizada a comprar em nome do cliente.';
COMMENT ON    COLUMN    VDCLIAUTP.COMPLAUTP IS 'Complemento do endereço da pessoa autorizada a comprar em nome do cliente.';
COMMENT ON TABLE        VDCLICOMPL IS 'Ficha de informações complementares do cliente.';
COMMENT ON    COLUMN    VDCLICOMPL.CARGOTRABCLI IS 'Cargo ocupado pelo cliente.';
COMMENT ON    COLUMN    VDCLICOMPL.DTADMTRABCLI IS 'Data de admissão do cliente.';
COMMENT ON    COLUMN    VDCLICOMPL.DDDTRABCLI IS 'DDD do local de trabalho do cliente.';
COMMENT ON    COLUMN    VDCLICOMPL.FONETRABCLI IS 'Telefone do local de trabalho do cliente.';
COMMENT ON    COLUMN    VDCLICOMPL.ENDTRABCLI IS 'Endereço do trabalho do cliente.';
COMMENT ON    COLUMN    VDCLICOMPL.NUMTRABCLI IS 'Número do logradouro do trabalho do cliente.';
COMMENT ON    COLUMN    VDCLICOMPL.BAIRTRABCLI IS 'Bairro do trabalho do cliente.';
COMMENT ON    COLUMN    VDCLICOMPL.CIDTRABCLI IS 'Cidade do trabalho do cliente.';
COMMENT ON    COLUMN    VDCLICOMPL.UFTRABCLI IS 'Estado (UF) do trabalho do cliente.';
COMMENT ON    COLUMN    VDCLICOMPL.OUTRENDACLI IS 'Outras rendas do cliente.';
COMMENT ON    COLUMN    VDCLICOMPL.FONTRENDACLI IS 'Fonte das outras rendas do cliente.';
COMMENT ON    COLUMN    VDCLICOMPL.NOMECONJCLI IS 'Nome do conjuge do cliente.';
COMMENT ON    COLUMN    VDCLICOMPL.DTNASCCONJCLI IS 'Data de nascimento do conjuge do cliente.';
COMMENT ON    COLUMN    VDCLICOMPL.RENDACONJCLI IS 'Renda do conjuge do cliente.';
COMMENT ON    COLUMN    VDCLICOMPL.COMPLTRABCLI IS 'Complemento de endereco do trabalho do cliente.';
COMMENT ON    COLUMN    VDCLICOMPL.CEPTRABCLI IS 'Cep do trabalho do cliente.';
COMMENT ON    COLUMN    VDCLICOMPL.RAMALTRABCLI IS 'Ramal do trabalho do cliente.';
COMMENT ON    COLUMN    VDCLICOMPL.RGCONJCLI IS 'RG do conjuge.';
COMMENT ON    COLUMN    VDCLICOMPL.SSPCONJCLI IS 'Orgao de espedicao do RG do conjuge.';
COMMENT ON    COLUMN    VDCLICOMPL.CPFCONJCLI IS 'CPF do conjuge.';
COMMENT ON    COLUMN    VDCLICOMPL.NATCONJCLI IS 'Naturalidade do conjuge.';
COMMENT ON    COLUMN    VDCLICOMPL.UFNATCONJCLI IS 'Estado de onde e natural o conjuge.';
COMMENT ON    COLUMN    VDCLICOMPL.CARGOCONJCLI IS 'Cargo do conjuge.';
COMMENT ON    COLUMN    VDCLICOMPL.EMPTRABCONJCLI IS 'Empresa onde trabalha o conjuge.';
COMMENT ON    COLUMN    VDCLICOMPL.DTADMTRABCONJCLI IS 'Data de admissao do conjuge.';
COMMENT ON    COLUMN    VDCLICOMPL.NOMEAVALCLI IS 'Nome do avalista.';
COMMENT ON    COLUMN    VDCLICOMPL.DTNASCAVALCLI IS 'Data de nascimento do avalista do cliente.';
COMMENT ON    COLUMN    VDCLICOMPL.RGAVALCLI IS 'Rg do avalista.';
COMMENT ON    COLUMN    VDCLICOMPL.SSPAVALCLI IS 'Orgao de espedicao do Rg do avalista.';
COMMENT ON    COLUMN    VDCLICOMPL.CPFAVALCLI IS 'CPF do avalista do cliente.';
COMMENT ON    COLUMN    VDCLICOMPL.ENDAVALCLI IS 'Endereco do avalista do cliente.';
COMMENT ON    COLUMN    VDCLICOMPL.NUMAVALCLI IS 'Numero no endereco do avalista do cliente.';
COMMENT ON    COLUMN    VDCLICOMPL.BAIRAVALCLI IS 'Bairro do avalista do cliente.';
COMMENT ON    COLUMN    VDCLICOMPL.CIDVALCLI IS 'Cidade do avalista do cliente.';
COMMENT ON    COLUMN    VDCLICOMPL.CEPAVALCLI IS 'Cep do avalista do cliente.';
COMMENT ON    COLUMN    VDCLICOMPL.UFAVALCLI IS 'Estado do avalista do cliente.';
COMMENT ON    COLUMN    VDCLICOMPL.DDDAVALCLI IS 'DDD do telefone do avalista do cliente.';
COMMENT ON    COLUMN    VDCLICOMPL.FONEAVALCLI IS 'Fone do avalista do cliente.';
COMMENT ON    COLUMN    VDCLICOMPL.EMPTRABAVALCLI IS 'Empresa onde trabalha o avalista do cliente.';
COMMENT ON    COLUMN    VDCLICOMPL.DTADMTRABAVALCLI IS 'Data de admissao do avalista do cliente.';
COMMENT ON    COLUMN    VDCLICOMPL.CARGOAVALCLI IS 'Cargo do avalista do cliente.';
COMMENT ON TABLE        VDCLICONTAS IS 'Contas bancarias dos clientes.';
COMMENT ON TABLE        VDCLIENTE IS 'Clientes.';
COMMENT ON    COLUMN    VDCLIENTE.CODEMPEC IS 'Código da empresa para tabela de estado civil.';
COMMENT ON    COLUMN    VDCLIENTE.CODFILIALEC IS 'Código da filial para tabela de estado civil.';
COMMENT ON    COLUMN    VDCLIENTE.CODTBEC IS 'Código da tabela de estado civil.';
COMMENT ON    COLUMN    VDCLIENTE.CODITTBEC IS 'Código do estado civil.';
COMMENT ON    COLUMN    VDCLIENTE.CODEMPCB IS 'Código da empresa na tabela FNCARTCOB.';
COMMENT ON    COLUMN    VDCLIENTE.CODFILIALCB IS 'Código da filial na tabela FNCARTCOB.';
COMMENT ON    COLUMN    VDCLIENTE.CODCARTCOB IS 'Código da carteira de cobrança.';
COMMENT ON    COLUMN    VDCLIENTE.INSCCLI IS ' Inscrição estadual do cliente.';
COMMENT ON    COLUMN    VDCLIENTE.SSPCLI IS 'Orgão de espedição do RG.';
COMMENT ON    COLUMN    VDCLIENTE.EMAILCOB IS 'Email de conbrança.';
COMMENT ON    COLUMN    VDCLIENTE.EMAILENT IS 'Email de entrega';
COMMENT ON    COLUMN    VDCLIENTE.EMAILNFECLI IS 'Email para envio de nota fiscal eletronica';
COMMENT ON    COLUMN    VDCLIENTE.DTINITR IS 'Data de abertura do crédito';
COMMENT ON    COLUMN    VDCLIENTE.CELCLI IS 'Número do Celular
';
COMMENT ON    COLUMN    VDCLIENTE.NATCLI IS 'Naturalidade do cliente';
COMMENT ON    COLUMN    VDCLIENTE.UFNATCLI IS 'UF ref. a naturalizade.';
COMMENT ON    COLUMN    VDCLIENTE.TEMPORESCLI IS 'Tempo de residência do cliente';
COMMENT ON    COLUMN    VDCLIENTE.APELIDOCLI IS 'Apelido do cliente.';
COMMENT ON    COLUMN    VDCLIENTE.SITECLI IS 'Endereço eletrônico do site do cliente.';
COMMENT ON    COLUMN    VDCLIENTE.CODCONTDEB IS 'Código contabil de debito.';
COMMENT ON    COLUMN    VDCLIENTE.CODCONTCRED IS 'Código contabil de credito';
COMMENT ON    COLUMN    VDCLIENTE.CODCLICONTAB IS 'Código contabil';
COMMENT ON    COLUMN    VDCLIENTE.FOTOCLI IS 'Foto do cliente.';
COMMENT ON    COLUMN    VDCLIENTE.IMGASSCLI IS 'Imagem da assinatura do cliente';
COMMENT ON    COLUMN    VDCLIENTE.CODMUNIC IS 'Código do municipio (IBGE)';
COMMENT ON    COLUMN    VDCLIENTE.SIGLAUF IS 'Sigla da Unidade da Federeção (Estado)';
COMMENT ON    COLUMN    VDCLIENTE.CODPAIS IS 'Código do pais.';
COMMENT ON    COLUMN    VDCLIENTE.CODMUNICENT IS 'Código do municipio (IBGE) do endereço de entrega.';
COMMENT ON    COLUMN    VDCLIENTE.SIGLAUFENT IS 'Sigla da Unidade da Federação do endereço de entrega.';
COMMENT ON    COLUMN    VDCLIENTE.CODPAISENT IS 'Código do país do endereço de entrega.';
COMMENT ON    COLUMN    VDCLIENTE.CODMUNICCOB IS 'Código do municipio (IBGE) do endereço de cobrança.';
COMMENT ON    COLUMN    VDCLIENTE.SIGLAUFCOB IS 'Sigla da Unidade da Federação do endereço de cobrança.';
COMMENT ON    COLUMN    VDCLIENTE.CODPAISCOB IS 'Código do país do endereço de cobrança.';
COMMENT ON    COLUMN    VDCLIENTE.CODUNIFCOD IS 'Código na tabela de unificação de códigos (SGUNIFCOD).';
COMMENT ON    COLUMN    VDCLIENTE.SUFRAMACLI IS 'Código do SUFRAMA do cliente.';
COMMENT ON    COLUMN    VDCLIENTE.CTOCLI IS 'C - Cliente
O - Contato';
COMMENT ON    COLUMN    VDCLIENTE.CODCNAE IS 'Código da atividade principal, padrão CNAE.
';
COMMENT ON    COLUMN    VDCLIENTE.INSCMUNCLI IS 'Inscrição municipal do cliente.';
COMMENT ON    COLUMN    VDCLIENTE.PERCDESCCLI IS 'Percentual padrão de desconto para o cliente.';
COMMENT ON    COLUMN    VDCLIENTE.CONTCLICOB IS 'Contato no cliente, para cobrança.';
COMMENT ON    COLUMN    VDCLIENTE.CONTCLIENT IS 'Contato no cliente, para entrega.';
COMMENT ON    COLUMN    VDCLIENTE.DESCIPI IS 'Indica se deve haver o desconto do IPI ao preço da mercadoria.
';
COMMENT ON    COLUMN    VDCLIENTE.IDENTCLIBCO IS 'Identificação cliente (SIACC)- D - Documento - CPF/CNPJ / C - Código do cliente ';
COMMENT ON    COLUMN    VDCLIENTEFOR.CODCPCLIFOR IS 'Código complementar ou da campanha que o cliente participa  junto ao fornecedor.';
COMMENT ON TABLE        VDCLIIMOV IS 'Cadastro de imoveis do cliente.';
COMMENT ON    COLUMN    VDCLIIMOV.TIPOIMOV IS 'Tipo de imovel.';
COMMENT ON    COLUMN    VDCLIIMOV.CONSTRIMOV IS 'Tipo de construcao no imovel';
COMMENT ON    COLUMN    VDCLIIMOV.AREATERIMOV IS 'Area do terrano do imovel.';
COMMENT ON    COLUMN    VDCLIIMOV.VALORIMOV IS 'Valor do imovel.';
COMMENT ON    COLUMN    VDCLIMETAVEND.ANOMETAVEND IS 'Ano para a meta de vendas';
COMMENT ON    COLUMN    VDCLIMETAVEND.VLRMETAVEND IS 'Valor de meta para vendas.';
COMMENT ON    COLUMN    VDCLIMETAVEND.OBSMETAVEND IS 'Observações referentes a meta de vendas para um determinado ano.';
COMMENT ON TABLE        VDCLIREFC IS 'Referências comerciais do cliente.';
COMMENT ON    COLUMN    VDCLIREFC.NOMEEMPREFC IS 'Nome da empresa.';
COMMENT ON    COLUMN    VDCLIREFC.DDDREFC IS 'DDD da referencia comercial.';
COMMENT ON    COLUMN    VDCLIREFC.FONEREFC IS 'Fone da referencia comercial.';
COMMENT ON    COLUMN    VDCLIREFC.DTMAIORCP IS 'Data da maior compra do cliente na referencia comercial.';
COMMENT ON    COLUMN    VDCLIREFC.VLRMAIORCP IS 'Valor da maior compra do cliente na referencia comercial.';
COMMENT ON    COLUMN    VDCLIREFC.NROPARCMAIORCP IS 'Numero de parcelas da maior compra do cliente na referencia comercial.';
COMMENT ON    COLUMN    VDCLIREFC.DTULTCP IS 'Data da ultima compra do cliente na referencia comercial.';
COMMENT ON    COLUMN    VDCLIREFC.VLRULTCP IS 'Valor da ultima compra do cliente na referencia comercial.';
COMMENT ON    COLUMN    VDCLIREFC.NROPARCULTCP IS 'Nro de parcelas da ultima compra do cliente na referencia comercial.';
COMMENT ON    COLUMN    VDCLIREFC.DTPRIMCP IS 'Data da primeira compra do cliente na referencia comercial.';
COMMENT ON    COLUMN    VDCLIREFC.PONTUAL IS 'Flag que indica se o cliente é pontual na referencia comercial. (S ou N)';
COMMENT ON    COLUMN    VDCLIREFC.MEDIAATRASO IS 'Média de atraso do cliente na referencia comercial.';
COMMENT ON    COLUMN    VDCLIREFC.CONCEITO IS 'Conceito do cliente junto à referencia comercial.';
COMMENT ON    COLUMN    VDCLIREFC.AVALISTA IS 'Flag que indica se a empresa é avalista do cliente.';
COMMENT ON    COLUMN    VDCLIREFC.INFORMANTE IS 'Nome do informante na empresa.';
COMMENT ON    COLUMN    VDCLIREFC.OBSREFC IS 'Observações.';
COMMENT ON    COLUMN    VDCLIREFP.CODEMP IS 'Codigo da empresa.';
COMMENT ON    COLUMN    VDCLIREFP.CODFILIAL IS 'Codigo da filial.';
COMMENT ON    COLUMN    VDCLIREFP.CODCLI IS 'Codigo do cliente.';
COMMENT ON    COLUMN    VDCLIREFP.NOMEREFP IS 'Nome da referência pessoal.';
COMMENT ON    COLUMN    VDCLIREFP.ENDREFP IS 'Endereco.';
COMMENT ON    COLUMN    VDCLIREFP.NUMREFP IS 'Numero.';
COMMENT ON    COLUMN    VDCLIREFP.COMPLREFP IS 'Complemento do endereço.';
COMMENT ON    COLUMN    VDCLIREFP.BAIRREFP IS 'Bairro.';
COMMENT ON    COLUMN    VDCLIREFP.CIDREFP IS 'Cidade.';
COMMENT ON    COLUMN    VDCLIREFP.UFREFP IS 'Estado.';
COMMENT ON    COLUMN    VDCLIREFP.CEPREFP IS 'Cep.';
COMMENT ON    COLUMN    VDCLIREFP.DDDREFP IS 'DDD.';
COMMENT ON    COLUMN    VDCLIREFP.FONEREFP IS 'Fone.';
COMMENT ON    COLUMN    VDCLIREFP.CODREFP IS 'Codigo da referencia.';
COMMENT ON TABLE        VDCLISOCIOS IS 'Socios da empresa cliente.';
COMMENT ON TABLE        VDCLITERRA IS 'Terras do cliente.';
COMMENT ON    COLUMN    VDCLITERRA.ENDTERRA IS 'Endereço da terra.';
COMMENT ON TABLE        VDCLIVEIC IS 'Veículos do cliente.';
COMMENT ON    COLUMN    VDCLIVEIC.PLACAVEIC IS 'Placa do veículo.';
COMMENT ON    COLUMN    VDCLIVEIC.MODELOVEIC IS 'Modelo do veículo.';
COMMENT ON    COLUMN    VDCLIVEIC.ALIENADOVEIC IS 'Indica se o veículo está alienado.';
COMMENT ON    COLUMN    VDCLIVEIC.ANOVEIC IS 'Ano de fabricação.';
COMMENT ON    COLUMN    VDCLIVEIC.VALORVEIC IS 'Valor do veículo.';
COMMENT ON    COLUMN    VDCOMISSAO.TIPOCOMI IS 'Tipo de comissão:
F-Valor gerado no faturamento
R-Valor gerado no recebimento
E-Valor gerado no estorno de contas a receber
';
COMMENT ON    COLUMN    VDCOMISSAO.DTCOMPCOMI IS 'Data de competência.
';
COMMENT ON    COLUMN    VDCOMISSAO.STATUSCOMI IS 'Situação da comissão:
C1 - Em aberto
C2 - Liberada
CP - Paga';
COMMENT ON    COLUMN    VDCOMISSAO.CODEMPVD IS 'Código da empresa do vendedor.';
COMMENT ON    COLUMN    VDCOMISSAO.CODFILIALVD IS 'Código da filial do vendedor.';
COMMENT ON    COLUMN    VDCOMISSAO.CODVEND IS 'Código do vendedor.';
COMMENT ON    COLUMN    VDCOMISSAO.CODEMPVE IS 'Código da empresa da venda vinculada (comissionamento especial)';
COMMENT ON    COLUMN    VDCOMISSAO.CODFILIALVE IS 'Código da filial da venda vinculada (comissionamento especial)';
COMMENT ON    COLUMN    VDCOMISSAO.CODVENDA IS 'Código da venda (comissionamento especial)';
COMMENT ON    COLUMN    VDCOMISSAO.TIPOVENDA IS 'Tipo da venda';
COMMENT ON    COLUMN    VDCOMISSAO.EMMANUT IS 'Estado de manutenção (S/N).';
COMMENT ON TABLE        VDCONSIGNACAO IS 'Table de vendas consignadas';
COMMENT ON    COLUMN    VDCONSIGNACAO.DOCCONSIG IS 'Documento da consignação';
COMMENT ON    COLUMN    VDCONSIGNACAO.CODEMPSL IS 'Código da empresa do sub-lançamento financeiro.';
COMMENT ON    COLUMN    VDCONSIGNACAO.CODFILIALSL IS 'Código da filial do sub-lançamento financeiro.';
COMMENT ON    COLUMN    VDCONSIGNACAO.CODLANCA IS 'Código do lançamento financeiro.';
COMMENT ON    COLUMN    VDCONSIGNACAO.CODSUBLANCA IS 'Código do sub-lançamento financeiro.';
COMMENT ON    COLUMN    VDCONSIGNACAO.CODEMPSD IS 'Código da empresa do sub-lançamento financeiro de devolução.';
COMMENT ON    COLUMN    VDCONSIGNACAO.CODFILIALSD IS 'Código da filial do sub-lançamento financeiro de devolução.';
COMMENT ON    COLUMN    VDCONSIGNACAO.CODLANCASD IS 'Código do lançamento financeiro de devolução.';
COMMENT ON    COLUMN    VDCONSIGNACAO.CODSUBLANCASD IS 'Código do sub-lançamento financeiro de devolução.';
COMMENT ON TABLE        VDCONTRATO IS 'Tabela de contratos.';
COMMENT ON    COLUMN    VDCONTRATO.CODCONTR IS 'Código do contrato.';
COMMENT ON    COLUMN    VDCONTRATO.DESCCONTR IS 'Descrição do contrato.';
COMMENT ON    COLUMN    VDCONTRATO.MINUTACONTR IS 'Minuta do contrato.';
COMMENT ON    COLUMN    VDCONTRATO.CODEMPCL IS 'Código da empresa do cliente';
COMMENT ON    COLUMN    VDCONTRATO.CODFILIALCL IS 'Código da filial do cliente.';
COMMENT ON    COLUMN    VDCONTRATO.CODCLI IS 'Código do cliente.';
COMMENT ON    COLUMN    VDCONTRATO.DTINICIO IS 'Data de inicio de vigor do contrato.';
COMMENT ON    COLUMN    VDCONTRATO.DTFIM IS 'Data do fim do contrato.';
COMMENT ON    COLUMN    VDCONTRATO.TPCOBCONTR IS 'Tipo de cobrança do contrato:
"ME" - Contrato Mensal
"BI" - Contrato Bimestral
"AN" - Contrato Anual
"ES" - Contrato Esporádico';
COMMENT ON    COLUMN    VDCONTRATO.DIAVENCCONTR IS 'Dia de vencimento da cobrança.';
COMMENT ON    COLUMN    VDCONTRATO.DIAFECHCONTR IS 'Dia do mes para fechamento das cobranças.';
COMMENT ON    COLUMN    VDCONTRATO.TPCONTR IS 'Indica se tem características de contrato ou de projeto.
"C" - Contrato;
"P" - Projeto;
"S" - Sub-projeto';
COMMENT ON    COLUMN    VDCONTRATO.SITCONTR IS 'Situação do contrato:
"PE" - Pendente
"PA" - Em planejamento
"PF" - Planejado
"EE" - Em execução
"EX" - Executado
"PO" - Paralizado
"CC" - Canc. cliente
"CP" - Canc. prestador
"FN" - Finalizado';
COMMENT ON TABLE        VDETIQCLI IS '  tabela temporária para impressão de etiquetas de cliente.';
COMMENT ON    COLUMN    VDFRETEVD.ADICFRETEVD IS 'FLAG QUE INDICA SE ADICIONA O VALOR DO FRETE NA NOTA';
COMMENT ON    COLUMN    VDFRETEVD.ADICFRETEBASEICM IS 'Indica se deve adicionar o valor do frete na base de calculo do icms da venda.';
COMMENT ON    COLUMN    VDFRETEVD.VLRBASEICMSFRETEVD IS 'Base de calculo do icms do frete.';
COMMENT ON    COLUMN    VDFRETEVD.ALIQICMSFRETEVD IS 'Aliquota de icms incidente no frete.';
COMMENT ON    COLUMN    VDFRETEVD.VLRSEGFRETEVD IS 'Valor do seguro do frete.';
COMMENT ON TABLE        VDITCONTRATO IS 'Tabela de ítens de contrato.';
COMMENT ON    COLUMN    VDITCONTRATO.DESCITCONTR IS 'Descrição do item de contrato';
COMMENT ON    COLUMN    VDITCONTRATO.CODEMPPD IS 'Código da empresa do produto.';
COMMENT ON    COLUMN    VDITCONTRATO.CODFILIALPD IS 'Código da filial do produto.';
COMMENT ON    COLUMN    VDITCONTRATO.CODPROD IS 'Código do produto.';
COMMENT ON    COLUMN    VDITCONTRATO.QTDITCONTR IS 'Quantidade do produto no ítem de contrato.';
COMMENT ON    COLUMN    VDITCONTRATO.VLRITCONTR IS 'Valor contratado para o produto.';
COMMENT ON    COLUMN    VDITCONTRATO.CODEMPPE IS 'Código da empresa do produto excedente.';
COMMENT ON    COLUMN    VDITCONTRATO.CODFILIALPE IS 'Código da filial do produto excedente.';
COMMENT ON    COLUMN    VDITCONTRATO.CODPRODPE IS 'Código do produto excedente.';
COMMENT ON    COLUMN    VDITCONTRATO.VLRITCONTREXCED IS 'Valor cobrado por excedente à quantidade contratada.';
COMMENT ON    COLUMN    VDITCONTRATO.KEYLIC IS 'Chave de licenciamento do produto/contrato.';
COMMENT ON    COLUMN    VDITCONTRATO.FRANQUIAITCONTR IS 'Agregar quantidade ao valor da franquia (S/N).';
COMMENT ON TABLE        VDITCONTRATOAND IS 'Tabela para lançamento do andamento de execução de um projeto.';
COMMENT ON    COLUMN    VDITCONTRATOAND.CODCONTR IS 'Código do contrato.';
COMMENT ON    COLUMN    VDITCONTRATOAND.CODITCONTR IS 'Código do ítem de contrato.';
COMMENT ON    COLUMN    VDITCONTRATOAND.OBSAND IS 'Observação sobre o andamento do projeto.';
COMMENT ON    COLUMN    VDITCONTRATOAND.PERCAND IS 'Percentual de conclusão do projeto.';
COMMENT ON    COLUMN    VDITCONTRATOAND.DATAAND IS 'Data do andamento.';
COMMENT ON    COLUMN    VDITCONTRATOAND.HORAAND IS 'Hora do andamento.';
COMMENT ON    COLUMN    VDITORCAMENTO.CODEMPAX IS 'Código da empresa na tabela de almoxarifados.';
COMMENT ON    COLUMN    VDITORCAMENTO.CODFILIALAX IS 'Código da filial na tabela de almoxarifados.';
COMMENT ON    COLUMN    VDITORCAMENTO.CODALMOX IS 'Código do almoxarifado.';
COMMENT ON    COLUMN    VDITORCAMENTO.QTDAPROVITORC IS 'Quantidade aprovada.';
COMMENT ON    COLUMN    VDITORCAMENTO.STATUSITORC IS '"*"  - Orçamento em aberto;
"OA" - Orçamento em aberto;
"OC" - Orçamento completo/impresso;
"OL" - Orçamento liberado/aprovado;
"OV" - Orçamento faturado.
"OP" - Orçamento produzido.
"CA" - Orçamento Cancelado/Não Aprovado.';
COMMENT ON    COLUMN    VDITORCAMENTO.CODEMPPE IS 'Código da empresa do prazo de entrega.';
COMMENT ON    COLUMN    VDITORCAMENTO.CODFILIALPE IS 'Código da filial do prazo de entrega.';
COMMENT ON    COLUMN    VDITORCAMENTO.CODPE IS 'Código do prazo de entrega.';
COMMENT ON    COLUMN    VDITORCAMENTO.DIASPE IS 'Prazo de entrega (em dias) no ítem.';
COMMENT ON    COLUMN    VDITORCAMENTO.EMMANUT IS 'Flag para por a tabela em manutençao (S/N).';
COMMENT ON    COLUMN    VDITORCAMENTO.SITENTITORC IS 'Situação da entrega 
N - Não entregue
E - Entregue
';
COMMENT ON    COLUMN    VDITORCAMENTO.SITTERMRITORC IS 'Situação do termo de recebimento.
E - Emitir
N - Não emitir
O - Emitido';
COMMENT ON    COLUMN    VDITORCAMENTO.CANCITORC IS 'Flag de cancelamento S/N.';
COMMENT ON    COLUMN    VDITORCAMENTO.FATITORC IS 'Flag de faturamento S/N/P
(Sim, não, parcial)';
COMMENT ON    COLUMN    VDITORCAMENTO.VLRCOMISITORC IS 'Valor previsto de comissão no item de orçamento.';
COMMENT ON    COLUMN    VDITORCAMENTO.PERCCOMISITORC IS 'Percentual previsto de comissão no item de orçamento.';
COMMENT ON    COLUMN    VDITORCAMENTO.VLRFRETEITORC IS 'Valor previsto de frete por item do orçamento.';
COMMENT ON    COLUMN    VDITORCAMENTO.DTAPROVITORC IS 'Data de aprovação do ítem de orçamento.';
COMMENT ON    COLUMN    VDITORCAMENTO.SITPRODITORC IS 'Situação da produção do ítem de orçamento.
PE - Pendente
EP - Em produção
NP - Não produzir
PD - Produzido';
COMMENT ON    COLUMN    VDITREGRACOMIS.CODEMPVD IS 'Código da empresa do vendedor.';
COMMENT ON    COLUMN    VDITREGRACOMIS.CODFILIALVD IS 'Código da filial do vendedor ';
COMMENT ON    COLUMN    VDITREGRACOMIS.CODVEND IS 'Código do vendedor.';
COMMENT ON    COLUMN    VDITVENDA.CODEMPAX IS 'Código da empresa na tabela de almoxarifados.';
COMMENT ON    COLUMN    VDITVENDA.CODFILIALAX IS 'Código da filial na tabela de almoxarifados.';
COMMENT ON    COLUMN    VDITVENDA.CODALMOX IS 'Código do almoxarifado.';
COMMENT ON    COLUMN    VDITVENDA.QTDITVENDACANC IS 'Quantidade cancelada';
COMMENT ON    COLUMN    VDITVENDA.VLRBASEICMSBRUTITVENDA IS 'Base de cálculo do ICMS sem redução e outras alterações.';
COMMENT ON    COLUMN    VDITVENDA.VLRBASEICMSSTITVENDA IS 'Valor da base de calculo do ICMS da substituição tributária.';
COMMENT ON    COLUMN    VDITVENDA.VLRICMSSTITVENDA IS 'Valor do ICMS da substituição tributária.';
COMMENT ON    COLUMN    VDITVENDA.VLRBASECOMISITVENDA IS 'Valor base para comissionamento
Qtd x Preco comissao (cad.prod.)';
COMMENT ON    COLUMN    VDITVENDA.MARGEMVLAGRITVENDA IS 'Margem de valor agregado para calculo da base de calculo do icms de substituição tributária.';
COMMENT ON    COLUMN    VDITVENDA.TIPOST IS 'Tipo de substituição tributária.';
COMMENT ON    COLUMN    VDITVENDA.CANCITVENDA IS 'Flag para marcar se o item foi cancelado.';
COMMENT ON    COLUMN    VDITVENDA.CODEMPPE IS 'Código da empresa do prazo de entrega.';
COMMENT ON    COLUMN    VDITVENDA.CODFILIALPE IS 'Código da filial do prazo de entrega.';
COMMENT ON    COLUMN    VDITVENDA.CODPE IS 'Código do prazo de entrega.';
COMMENT ON    COLUMN    VDITVENDA.DIASPE IS 'Prazo de entrega (em dias) no ítem.';
COMMENT ON    COLUMN    VDITVENDA.CODCONV IS 'Código do conveniado';
COMMENT ON    COLUMN    VDITVENDA.CODEMPIF IS 'Código da empresa do item de classificação fiscal (fk transitória, utilizada no trigger que carrega a tabela lfitvenda)';
COMMENT ON    COLUMN    VDITVENDA.CODFILIALIF IS 'Código da filial do item de classificação fiscal (fk transitória, utilizada no trigger que carrega a tabela lfitvenda)';
COMMENT ON    COLUMN    VDITVENDA.CODFISC IS 'Código da classificação fiscal (fk transitória, utilizada no trigger que carrega a tabela lfitvenda)';
COMMENT ON    COLUMN    VDITVENDA.CODITFISC IS 'Código do tem de classificação fiscal (fk transitória, utilizada no trigger que carrega a tabela lfitvenda)';
COMMENT ON    COLUMN    VDITVENDA.CODEMPCP IS 'Código da empresa da compra (devolução)';
COMMENT ON    COLUMN    VDITVENDA.CODFILIALCP IS 'Código da filial da compra (devolução)';
COMMENT ON    COLUMN    VDITVENDA.CODCOMPRA IS 'Código da compra (devolução)';
COMMENT ON    COLUMN    VDITVENDA.CODITCOMPRA IS 'Código do ítem da compra (devolução)';
COMMENT ON    COLUMN    VDITVENDA.CODEMPVR IS 'Código da empresa do item da nota de remessa';
COMMENT ON    COLUMN    VDITVENDA.CODFILIALVR IS 'Código da filial do item de nota de remessa';
COMMENT ON    COLUMN    VDITVENDA.TIPOVENDAVR IS 'Tipo da venda do item de nota de remessa';
COMMENT ON    COLUMN    VDITVENDA.CODVENDAVR IS 'Código da venda do item de nota de remessa';
COMMENT ON    COLUMN    VDITVENDA.CODITVENDAVR IS 'Código do item de nota de remessa';
COMMENT ON    COLUMN    VDITVENDA.CODEMPNS IS 'Código da empresa do numero de serie';
COMMENT ON    COLUMN    VDITVENDA.CODFILIALNS IS 'Código da filial do número de série.';
COMMENT ON    COLUMN    VDITVENDA.NUMSERIETMP IS 'Campo para abrigar temporariamente o número de série do produto (para uso do trigger quando produto for unitário)';
COMMENT ON    COLUMN    VDITVENDA.VLRBASEICMSSTRETITVENDA IS 'Valor da base de calculo do icms st retido na opearação anterior.';
COMMENT ON    COLUMN    VDITVENDA.VLRICMSSTRETITVENDA IS 'Valor do icms st retido na operação anterior.
';
COMMENT ON    COLUMN    VDITVENDA.EMMANUT IS 'Flag para manutenção da tabela (S/N).';
COMMENT ON TABLE        VDITVENDASERIE IS 'Tabela de vinculo entre item de venda e seus respectivos numeros de serie.';
COMMENT ON    COLUMN    VDORCAMENTO.STATUSORC IS '"*"  - Orçamento em aberto;
"OA" - Orçamento em aberto;
"OC" - Orçamento completo/impresso;
"OL" - Orçamento liberado/aprovado;
"OV" - Orçamento faturado.
"OP" - Orçamento produzido.
"CA" - Orçamento Cancelado/Não Aprovado.';
COMMENT ON    COLUMN    VDORCAMENTO.EMMANUT IS 'Flag para por a tabela em manutençao (S/N).';
COMMENT ON    COLUMN    VDORCAMENTO.CODCLCOMIS IS 'CODIGO DA CLASSIFICAÇÃO DA COMISSÃO';
COMMENT ON    COLUMN    VDORCAMENTO.CODEMPTN IS 'Código da empresa da transportadora.';
COMMENT ON    COLUMN    VDORCAMENTO.CODFILIALTN IS 'Código da filial da transportadora.';
COMMENT ON    COLUMN    VDORCAMENTO.CODTRAN IS 'Código da transportadora.';
COMMENT ON    COLUMN    VDORCAMENTO.TIPOFRETE IS 'C - CIF ; F - FOB';
COMMENT ON    COLUMN    VDORCAMENTO.ADICFRETE IS 'Indica se deve adicionar o valor do frete ao total dos produtos.';
COMMENT ON    COLUMN    VDORCAMENTO.VLRFRETEORC IS 'Valor previsto em despesa com frete.';
COMMENT ON    COLUMN    VDORCAMENTO.VLRCOMISORC IS 'Valor previsto de comissão no orçamento.';
COMMENT ON    COLUMN    VDORCAMENTO.CODEMPTM IS 'Código da empresa do tipo de movimento previsto para o faturamento do orçamento.';
COMMENT ON    COLUMN    VDORCAMENTO.CODFILIALTM IS 'Código da filial do tipo de movimento previsto para o faturamento do orçamento.';
COMMENT ON    COLUMN    VDORCAMENTO.CODTIPOMOV IS 'Código do tipo de movimento previsto para o faturamento do orçamento.';
COMMENT ON    COLUMN    VDORCAMENTO.DTAPROVORC IS 'Data da aprovação do orçamento.';
COMMENT ON    COLUMN    VDORCAMENTO.JUSTIFICCANCORC IS 'Justificativa pelo cancelamento do orçamento.';
COMMENT ON    COLUMN    VDORCAMENTO.ACORC IS 'Indica o recebedor do orçamento (Aos cuidados de)';
COMMENT ON TABLE        VDPRAZOENT IS 'Prazos de entrega.';
COMMENT ON    COLUMN    VDPRAZOENT.CODEMP IS 'Código da empresa.';
COMMENT ON    COLUMN    VDPRAZOENT.CODFILIAL IS 'Código da filial';
COMMENT ON    COLUMN    VDPRAZOENT.CODPE IS 'Cód. prazo de entrega.';
COMMENT ON    COLUMN    VDPRAZOENT.DESCPE IS 'Descrição do prazo de entrega';
COMMENT ON    COLUMN    VDPRAZOENT.DIASPE IS 'Número de dias para entrega.';
COMMENT ON    COLUMN    VDPRECOPROD.TIPOPRECOPROD IS 'Indica qual o tipo de preço que originário.
B - Preço Base
I - Custo informado;
O - Outros;
';
COMMENT ON    COLUMN    VDPRECOPROD.DTALTPRECO IS 'Data da alteração do preço.
';
COMMENT ON    COLUMN    VDPRECOPROD.HALTPRECO IS 'Hora de alteração do preço.
';
COMMENT ON    COLUMN    VDPRECOPROD.PRECOANT IS 'Preço anterior à última alteração.
';
COMMENT ON    COLUMN    VDPRECOPROD.IDUSUALTPRECO IS 'Usuário que realizou a alteração no preço.
';
COMMENT ON TABLE        VDREGRACOMIS IS 'Regras de comissões';
COMMENT ON    COLUMN    VDREGRACOMIS.PERCCOMISGERAL IS 'Percentual de comissão para o grupo de comissionados (comissionamento especial por producao)';
COMMENT ON    COLUMN    VDREMCONSIG.CODREMCO IS 'Código da remessa de consignação.';
COMMENT ON    COLUMN    VDREMCONSIG.CODPROD IS 'Código do produto remetido.';
COMMENT ON    COLUMN    VDREMCONSIG.QTDSAIDA IS 'Quantidade remetida';
COMMENT ON    COLUMN    VDREMCONSIG.QTDDEVOL IS 'Quantidade devolvida.';
COMMENT ON    COLUMN    VDREMCONSIG.QTDTROCA IS 'Quantidade remetida para troca.';
COMMENT ON    COLUMN    VDREMCONSIG.QTDBONIF IS 'Quantidade remetida em bonificação.';
COMMENT ON    COLUMN    VDREMCONSIG.PRECO IS 'Preço do produto remetido.';
COMMENT ON TABLE        VDSETORROTA IS 'Tela de rotas baseada no setor dos clientes.';
COMMENT ON TABLE        VDTEF IS 'Tabela para vendas com TEF vinculado';
COMMENT ON TABLE        VDTIPOCLI IS 'Tipos de clientes.';
COMMENT ON    COLUMN    VDTIPOCLI.CHEQTIPOCLI IS 'Flag que indica se haverá aba adicional no cadastro de clientes (cheque).';
COMMENT ON    COLUMN    VDTIPOCLI.FISTIPOCLI IS 'Flag que indica se haverá aba adicional no cadastro de clientes (pessoa física).';
COMMENT ON    COLUMN    VDTIPOCLI.JURTIPOCLI IS 'Flag que indica se haverá aba adicional no cadastro de clientes (pessoa jurídica).';
COMMENT ON    COLUMN    VDTIPOCLI.FILTIPOCLI IS 'Flag que indica se haverá aba adicional no cadastro de clientes (Filiação).';
COMMENT ON    COLUMN    VDTIPOCLI.LOCTRABTIPOCLI IS 'Flag que indica se haverá aba adicional no cadastro de clientes (local de trabalho).';
COMMENT ON    COLUMN    VDTIPOCLI.REFCOMLTIPOCLI IS 'Flag que indica se haverá aba adicional no cadastro de clientes (Referências comerciais).';
COMMENT ON    COLUMN    VDTIPOCLI.BANCTIPOCLI IS 'Flag que indica se haverá aba adicional no cadastro de clientes (Dados bancários).';
COMMENT ON    COLUMN    VDTIPOCLI.REFPESTIPOCLI IS 'Flag que indica se haverá aba adicional no cadastro de clientes (referências pessoais).';
COMMENT ON    COLUMN    VDTIPOCLI.CONJTIPOCLI IS 'Flag que indica se haverá aba adicional no cadastro de clientes (dados do cônjuge jurídica).';
COMMENT ON    COLUMN    VDTIPOCLI.VEICTIPOCLI IS 'Flag que indica se haverá aba adicional no cadastro de clientes (veículos).';
COMMENT ON    COLUMN    VDTIPOCLI.IMOVTIPOCLI IS 'Flag que indica se haverá aba adicional no cadastro de clientes (imóveis).';
COMMENT ON    COLUMN    VDTIPOCLI.TERRATIPOCLI IS 'Flag que indica se haverá aba adicional no cadastro de clientes (terras).';
COMMENT ON    COLUMN    VDTIPOCLI.PESAUTCPTIPOCLI IS 'Flag para dados complementares (pessoa autorizada a comprar).';
COMMENT ON    COLUMN    VDTIPOCLI.AVALTIPOCLI IS 'Flag para dados complementares (avalista).';
COMMENT ON    COLUMN    VDTIPOCLI.SOCIOTIPOCLI IS 'Flag para dados complementares (sócios).';
COMMENT ON    COLUMN    VDTIPOCLI.PRODRURALTIPOCLI IS 'Flag que indica se cliente é produtor rural.';
COMMENT ON    COLUMN    VDTIPOCLI.SIGLATIPOCLI IS 'Sigla ou abreviação da descrição do tipo de cliente (utilizado em alguns relatórios)';
COMMENT ON TABLE        VDTIPOVEND IS 'Tipos de comissionados.';
COMMENT ON TABLE        VDTRANSP IS 'Transportadoras.';
COMMENT ON    COLUMN    VDTRANSP.CPFTRAN IS 'CPF do transportador';
COMMENT ON    COLUMN    VDTRANSP.DDDFONETRAN IS 'DDD do telefone principal.';
COMMENT ON    COLUMN    VDTRANSP.DDDFAXTRAN IS 'DDD do Fax.';
COMMENT ON    COLUMN    VDTRANSP.DDDCELTRAN IS 'DDD do celular da transportadora.';
COMMENT ON    COLUMN    VDTRANSP.CODFILIALUC IS 'Código na tabela de unificação de códigos (SGUNIFCOD).';
COMMENT ON    COLUMN    VDTRANSP.CODFOR IS 'Correspondente na tabela de fornecedores.';
COMMENT ON    COLUMN    VDTRANSP.CONJUGETRAN IS 'Nome do conjuge do transportado (pessoa física)';
COMMENT ON    COLUMN    VDTRANSP.PLACATRAN IS 'Placa do veículo do transportador (pessoa física)';
COMMENT ON    COLUMN    VDTRANSP.NRODEPENDTRAN IS 'Número de dependentes do transportador (pessoa física)';
COMMENT ON    COLUMN    VDTRANSP.RGTRAN IS 'Número da Identidade.';
COMMENT ON    COLUMN    VDTRANSP.CODGPS IS 'Código de pagamento do gps/inss.';
COMMENT ON    COLUMN    VDTRANSP.NROPISTRAN IS 'Número do PIS.';
COMMENT ON    COLUMN    VDTRANSP.OBSTRAN IS 'Observações.';
COMMENT ON    COLUMN    VDTRANSP.EMAILNFETRAN IS 'Email para envio do XML da Nfe.';
COMMENT ON    COLUMN    VDVENDA.SUBTIPOVENDA IS 'Subtipo da venda - NF = Nota fiscal / NC = Nota complementar.';
COMMENT ON    COLUMN    VDVENDA.DTCOMPVENDA IS 'Data de competência.';
COMMENT ON    COLUMN    VDVENDA.CALCICMSVENDA IS 'Indica se deve calcular o valor do ICMS na nota.';
COMMENT ON    COLUMN    VDVENDA.IMPICMSVENDA IS 'Indica se deve imprimir o valor do ICMS na nota.';
COMMENT ON    COLUMN    VDVENDA.CALCIPIVENDA IS 'Indica se deve calcular o valor do IPI na nota.';
COMMENT ON    COLUMN    VDVENDA.IMPIPIVENDA IS 'Indica se deve imprimir o valor do IPI na nota.';
COMMENT ON    COLUMN    VDVENDA.CALCISSVENDA IS 'Indica se deve calcular o valor do ISS na nota.';
COMMENT ON    COLUMN    VDVENDA.IMPIISSVENDA IS 'Indica se deve imprimir o valor do ISS na nota.';
COMMENT ON    COLUMN    VDVENDA.CALCPISVENDA IS 'Indica se deve calcular o valor do PIS na nota.';
COMMENT ON    COLUMN    VDVENDA.IMPPISVENDA IS 'Indica se deve imprimir o valor do PIS na nota.';
COMMENT ON    COLUMN    VDVENDA.CALCCOFINSVENDA IS 'Indica se deve calcular o valor do COFINS na nota.';
COMMENT ON    COLUMN    VDVENDA.IMPCOFINSVENDA IS 'Indica se deve imprimir o valor do COFINS na nota.';
COMMENT ON    COLUMN    VDVENDA.CALCIRVENDA IS 'Indica se deve calcular o valor do IR na nota.';
COMMENT ON    COLUMN    VDVENDA.IMPIRVENDA IS 'Indica se deve imprimir o valor do IR na nota.';
COMMENT ON    COLUMN    VDVENDA.CALCCSOCIALVENDA IS 'Indica se deve calcular o valor da CONTRIBUICAO SOCIAL na nota.';
COMMENT ON    COLUMN    VDVENDA.IMPCSOCIALVENDA IS 'Indica se deve imprimir o valor da CONTRIBUICAO SOCIAL na nota.';
COMMENT ON    COLUMN    VDVENDA.CODEMPCB IS 'Código da empresa na tabela FNCARTCOB.';
COMMENT ON    COLUMN    VDVENDA.CODFILIALCB IS 'Código da filial na tabela FNCARTCOB.';
COMMENT ON    COLUMN    VDVENDA.CODCARTCOB IS 'Código da carteira de cobrança.';
COMMENT ON    COLUMN    VDVENDA.PEDCLIVENDA IS 'Número do pedido do cliente.';
COMMENT ON    COLUMN    VDVENDA.VLRICMSSTVENDA IS 'Valor do icms da substituição tributária.';
COMMENT ON    COLUMN    VDVENDA.VLRBASEICMSSTVENDA IS 'Valor da base de calculo do icms de substituição tributária.';
COMMENT ON    COLUMN    VDVENDA.EMMANUT IS 'Flag que indica se a tabela está em manutenção (S/N).';
COMMENT ON    COLUMN    VDVENDA.VLRICMSSIMPLES IS 'Valor do crédito de ICMS destacado na venda quando do enquadramento no simples.';
COMMENT ON    COLUMN    VDVENDA.PERCICMSSIMPLES IS 'Alíquota do crédito de ICMS destacado na venda quando do enquadramento no simples.';
COMMENT ON    COLUMN    VDVENDA.VLRBASECOMIS IS 'Valor base para calculo das comissões especiais.';
COMMENT ON    COLUMN    VDVENDA.CHAVENFEVENDA IS 'Chave de acesso da nota fiscal eletrônica.';
COMMENT ON    COLUMN    VDVENDA.OBSREC IS 'Observação a ser repassada para a tabela fnreceber no trigger/procedure de inserção.';
COMMENT ON    COLUMN    VDVENDA.INFCOMPL IS 'Informações complementares da nota fiscal ( de interesse do fisco );';
COMMENT ON    COLUMN    VDVENDA.SITDOC IS 'Situação do documento fiscal:
00-Documento regular;
01-Documento regular expontâneo;
02-Documento cancelado;
03-Documento cancelado expontâneo
04-NFE Denegada;
05-NFE Numeração inutilizada;
06-Documento fiscal complementar;
07-Documento fiscal complementar expontâneo;
08-Documento emitido com base em Regime Especial ou Norma Específica;';
COMMENT ON    COLUMN    VDVENDA.OBSNFE IS 'Observacoes geradas na emissao da nfe.';
COMMENT ON    COLUMN    VDVENDA.DESCIPIVENDA IS 'Indica se o valor do IPI foi descontado no preço dos ítens.
';
COMMENT ON    COLUMN    VDVENDA.CODEMPOP IS 'Código da OP vinculada (remessa industrialização)
';
COMMENT ON    COLUMN    VDVENDA.CODFILIALOP IS 'Código da filial da OP vinculada (remessa industrialização)
';
COMMENT ON    COLUMN    VDVENDA.SEQOP IS 'Código da sequencia da OP vinculada (remessa industrialização)
';
COMMENT ON    COLUMN    VDVENDA.CODOP IS 'CCódigo da OP vinculada (remessa industrialização)
';
COMMENT ON    COLUMN    VDVENDA.MOTIVOCANCVENDA IS 'Motivo do cancelamento da venda.';
COMMENT ON    COLUMN    VDVENDACOMIS.TIPOVENDA IS 'Tipo de venda
O = Orçamento
V = Venda
E = Vencda Ecf
';
COMMENT ON TABLE        VDVENDACONSIG IS 'Tabela de vendas em consignação.';
COMMENT ON    COLUMN    VDVENDACONSIG.CODCONSIG IS 'Código da consignação.';
COMMENT ON    COLUMN    VDVENDACONSIG.CODVENDACO IS 'Código da venda em consignação.';
COMMENT ON    COLUMN    VDVENDACONSIG.CODCLI IS 'Código do cliente.';
COMMENT ON    COLUMN    VDVENDACONSIG.CODPROD IS 'Código do produto.';
COMMENT ON    COLUMN    VDVENDACONSIG.PRECO IS 'Preço praticado.';
COMMENT ON    COLUMN    VDVENDACONSIG.PRECOVENDA IS 'Preço aplicado ao cliente.';
COMMENT ON    COLUMN    VDVENDACONSIG.QTDVENDACO IS 'Quantidade vendida.';
COMMENT ON    COLUMN    VDVENDACONSIG.QTDTROCA IS 'Quantidade trocada.';
COMMENT ON    COLUMN    VDVENDACONSIG.QTDBONIF IS 'Quantidade de bonificações.';
COMMENT ON    COLUMN    VDVENDACONSIG.DESCONTO IS 'Desconto praticado.';
COMMENT ON    COLUMN    VDVENDACONSIG.RECEBIDO IS 'Flag indicador de recebido.';
COMMENT ON    COLUMN    VDVENDACONSIG.CODEMPVD IS 'Código da empresa do item de venda';
COMMENT ON    COLUMN    VDVENDACONSIG.CODFILIALVD IS 'Código da filial do item de venda.';
COMMENT ON    COLUMN    VDVENDACONSIG.TIPOVENDA IS 'Tipo da venda do item de venda.';
COMMENT ON    COLUMN    VDVENDACONSIG.CODVENDA IS 'Código da venda do item de venda.';
COMMENT ON    COLUMN    VDVENDACONSIG.CODITVENDA IS 'Código do item de venda.';
COMMENT ON    COLUMN    VDVENDACONSIG.CODEMPSL IS 'Código da empresa do sub-lançamento financeiro.';
COMMENT ON    COLUMN    VDVENDACONSIG.CODFILIALSL IS 'Código da filial do sub-lançamento financeiro.';
COMMENT ON    COLUMN    VDVENDACONSIG.CODLANCA IS 'Código do lançamento financeiro.';
COMMENT ON    COLUMN    VDVENDACONSIG.CODSUBLANCA IS 'Código do sub-lançamento financeiro.';
COMMENT ON    COLUMN    VDVENDACONSIG.CODEMPSC IS 'Código da empresa do sub-lançamento financeiro de contra-partida.';
COMMENT ON    COLUMN    VDVENDACONSIG.CODFILIALSC IS 'Código da filial do sub-lançamento financeiro de contra-partida.';
COMMENT ON    COLUMN    VDVENDACONSIG.CODLANCASC IS 'Código do lançamento financeiro de contra-partida.';
COMMENT ON    COLUMN    VDVENDACONSIG.CODSUBLANCASC IS 'Código do sub-lançamento financeiro de contra-partida.';
COMMENT ON    COLUMN    VDVENDEDOR.CODFORNVEND IS 'CODIGO DO VENDEDOR NO FORNECEDOR
';
COMMENT ON    COLUMN    VDVENDEDOR.CODFUNC IS 'Funcao/Cargo do comissionado.';
COMMENT ON    COLUMN    VDVENDEDOR.CODEMPFU IS 'Código da empresa da funcao/cargo do vendedor.';
COMMENT ON    COLUMN    VDVENDEDOR.CODFILIALFU IS 'Código da filial da funcao do comissionado.';
COMMENT ON    COLUMN    VDVENDEDOR.DDDFONEVEND IS 'DDD do telefone do vendedor.';
COMMENT ON    COLUMN    VDVENDEDOR.DDDFAXVEND IS 'DDD do fax do vendedor.';
COMMENT ON    COLUMN    VDVENDEDOR.DDDCELVEND IS 'DDD do celular do vendedor.';
COMMENT ON    COLUMN    VDVENDEDOR.SSPVEND IS 'Orgão de emissão do RG do vendedor.';
COMMENT ON    COLUMN    VDVENDEDOR.OBSVEND IS 'Observações do vendedor.';
COMMENT ON    COLUMN    VDVENDEDOR.ATIVOCOMIS IS 'Indica se o comissionado esta ativo.';
COMMENT ON    COLUMN    VDVENDEDOR.CODEMPCA IS 'Código da empresa da conta do vendedor.';
COMMENT ON    COLUMN    VDVENDEDOR.CODFILIALCA IS 'Código da filial da conta do vendedor.';
COMMENT ON    COLUMN    VDVENDEDOR.NUMCONTA IS 'Numero da conta do vendedor.';
COMMENT ON    COLUMN    VDVENDEDOR.VLRABONO IS 'Valor do abono (utilizado nos relatórios de comissionamento pela produção.';
COMMENT ON    COLUMN    VDVENDEDOR.VLRDESCONTO IS 'Valor do desconto (utilizado nos relatórios de comissionamento pela produção.';
COMMENT ON    PARAMETER EQMOVPRODISP.SEQSUBPROD IS 'Sequencial do subproduto';
COMMENT ON    PARAMETER EQMOVPRODIUDSP.SEQSUBPROD IS 'Sequencial de subproduto';
COMMENT ON    PARAMETER FNADICRECEBERSP01.VLRRETENSAOISS IS 'Indica se deve ser realizada a retensão do tributo ISS (descontando do valor final do título)';
COMMENT ON    PARAMETER LFBUSCAFISCALSP.CODEMPCF IS 'Código da empresa do cliente ou fornecedor';
COMMENT ON    PARAMETER LFBUSCAFISCALSP.CODFILIALCF IS 'Código da filial do cliente ou fornecedor';
COMMENT ON    PARAMETER LFBUSCAFISCALSP.CODCF IS 'Código do cliente ou fornecedor';
COMMENT ON    PARAMETER LFBUSCAFISCALSP.TIPOBUSCA IS 'Indica se a busca é para VD venda ou CP compra';
COMMENT ON    PARAMETER LFBUSCAFISCALSP.CODEMPIFP IS 'Código da empresa do item de classificação fiscal ';
COMMENT ON    PARAMETER LFBUSCAFISCALSP.CODFILIALIFP IS 'Código da filial do ítem de classificação fiscal';
COMMENT ON    PARAMETER LFBUSCAFISCALSP.CODFISCP IS 'Código da classificação fiscal';
COMMENT ON    PARAMETER LFBUSCAFISCALSP.CODITFISCP IS 'Código do ítem de classficiação fiscal';
COMMENT ON PROCEDURE    LFBUSCAFISCALSP02 IS 'Procedure para busca de informações fiscais de um ítem de venda, utilizada para preencher dados da tabela lfitvenda.';
COMMENT ON PROCEDURE    LFBUSCAPREVTRIBORC IS 'Procedure para busca de previsão de tributos incidentes em ítem de orçamento para calculo da previsão de lucratividade.';
COMMENT ON PROCEDURE    PPCUSTOPRODSP IS 'Retorna o custo unitário do produto';
COMMENT ON    PARAMETER PPGERAOP.TIPOPROCESS IS 'Tipo de processamento (D=Detalhado, A=Agrupado, C=Comum)';
COMMENT ON    PARAMETER PPGERAOP.CODEMPOP IS 'Código da empresa da ordem de produção';
COMMENT ON    PARAMETER PPGERAOP.CODFILIALOP IS 'Código da filial da ordem de produção';
COMMENT ON    PARAMETER PPGERAOP.CODOP IS 'Código da ordem de produção';
COMMENT ON    PARAMETER PPGERAOP.SEQOP IS 'Sequencia da ordem de produção';
COMMENT ON    PARAMETER PPGERAOP.CODEMPPD IS 'Código da empresa do produto';
COMMENT ON    PARAMETER PPGERAOP.CODFILIALPD IS 'Código da filial do produto';
COMMENT ON    PARAMETER PPGERAOP.CODPROD IS 'Código do produto';
COMMENT ON    PARAMETER PPGERAOP.CODEMPOC IS 'Código da emrpesa do orçamento';
COMMENT ON    PARAMETER PPGERAOP.CODFILIALOC IS 'Código da filial do orçamento';
COMMENT ON    PARAMETER PPGERAOP.CODORC IS 'Código do orçamento';
COMMENT ON    PARAMETER PPGERAOP.TIPOORC IS 'Tipo de orçamento';
COMMENT ON    PARAMETER PPGERAOP.CODITORC IS 'Código do ítem de orçamento';
COMMENT ON    PARAMETER PPGERAOP.QTDSUGPRODOP IS 'Quantidade sugerida para fabricação';
COMMENT ON    PARAMETER PPGERAOP.DTFABROP IS 'Data de fabricação';
COMMENT ON    PARAMETER PPGERAOP.SEQEST IS 'Sequência da estrutura';
COMMENT ON    PARAMETER PPGERAOP.CODEMPET IS 'Código da empresa da estação de trabalho';
COMMENT ON    PARAMETER PPGERAOP.CODFILIALET IS 'Código da filial da estação de trabalho';
COMMENT ON    PARAMETER PPGERAOP.CODEST IS 'Código da estação de trabalho';
COMMENT ON    PARAMETER PPGERAOP.AGRUPDATAAPROV IS 'Indica se o agrupamento é por data de aprovação';
COMMENT ON    PARAMETER PPGERAOP.AGRUPDTFABROP IS 'Indica se o agrupamento é por data de fabricação';
COMMENT ON    PARAMETER PPGERAOP.AGRUPCODCLI IS 'Indica se o agrupamento é por código de cliente';
COMMENT ON    PARAMETER PPGERAOP.CODEMPCL IS 'Código da empresa do cliente do lote processado';
COMMENT ON    PARAMETER PPGERAOP.CODFILIALCL IS 'Código da filial do cliente do lote processado';
COMMENT ON    PARAMETER PPGERAOP.CODCLI IS 'Código do cliente do lote processado';
COMMENT ON    PARAMETER PPGERAOP.DATAAPROV IS 'Data de aprovação do lote processado';
COMMENT ON    PARAMETER PPGERAOP.CODEMPCP IS 'Código da empresa do item de compra (conversão de produtos)';
COMMENT ON    PARAMETER PPGERAOP.CODFILIALCP IS 'Código da filial do item de compra (conversão de produtos)';
COMMENT ON    PARAMETER PPGERAOP.CODCOMPRA IS 'Código da compra (conversão de produtos)';
COMMENT ON    PARAMETER PPGERAOP.CODITCOMPRA IS 'Código do item de compra (conversão de produtos)';
COMMENT ON    PARAMETER PPGERAOP.JUSTFICQTDPROD IS 'Justificativa por divergencias na quantidade final (conversão de produtos)';
COMMENT ON    PARAMETER PPGERAOP.CODEMPPDENTRADA IS 'Código da empresa do produto de entrada (conversão de produtos)';
COMMENT ON    PARAMETER PPGERAOP.CODFILIALPDENTRADA IS 'Código da filial do produto de entrada (conversão de produtos)';
COMMENT ON    PARAMETER PPGERAOP.CODPRODENTRADA IS 'Código do produto de entrada (conversão de produtos)';
COMMENT ON    PARAMETER PPGERAOP.QTDENTRADA IS 'Quantidade do produto de entrada (conversão de produtos)';
COMMENT ON PROCEDURE    PPGERAOPCQ IS 'Procedure disparada após a inserção na tabela PPOP, realiza varredura na estrutura do produto, verificando as análises necessárias
no controle de qualidade e gerando registros referentes ao controle de qualidade na tabela PPOPCQ.';
COMMENT ON    PARAMETER RHLISTACANDVAGASP.ICODVAGA IS 'Código da vaga';
COMMENT ON    PARAMETER RHLISTACANDVAGASP.ICODFUNC IS 'Código da função';
COMMENT ON PROCEDURE    TKGERACAMPANHACTO IS 'Procedure para geração de registros nas tabelas TKCAMPANHACTO e TKSITCAMP.';
COMMENT ON PROCEDURE    VDRETULTVDCLIPROD IS 'Procedure para relatório de ultimas vendas por cliente/produto.';
COMMIT WORK;
