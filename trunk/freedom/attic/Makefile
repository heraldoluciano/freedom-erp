MAKE=make
RPM_BUILD_DIR=/usr/src/rpm/BUILD/freedom
RPM_BINARY_DIR=/usr/src/rpm/RPMS/i386
JAR=jar
JAVADOC=javadoc
JAR_FLAGS=cvf
JAVADOC_FLAGS=-d doc -sourcepath /aplic/appjava -classpath /aplic/appjava
JAR_ARQ=jars/freedomstd.jar
JAR_CFG_ARQ=jars/freedomcfg.jar
JAR_ATD_ARQ=jars/freedomatd.jar
JAR_PCP_ARQ=jars/freedompcp.jar
JAR_FNC_ARQ=jars/freedomfnc.jar
JAR_TMK_ARQ=jars/freedomtmk.jar
JAR_BIBLI_ARQS=jars/interclient.jar jars/jfreechart-0.9.12.jar jars/jcommon-0.8.7.jar
INI_ARQ=freedom.ini
SH_ARQ=Freedom.sh
PACOTES= \
	bibli.compo \
	bibli.telas \
	bibli.acao \
	bibli.funcoes \
	projetos.freedomstd \
	projetos.freedomatd \
	projetos.freedomcfg
CLASS_FREEDOMCFG_FILES=projetos/freedomcfg/*.class
CLASS_FREEDOMFNC_FILES=projetos/freedomfnc/*.class
CLASS_FREEDOMPDV_FILES=projetos/freedompdv/*.class
CLASS_FREEDOMTMK_FILES= \
                   projetos/freedomtmk/*.class \
                   projetos/freedomatd/FAtendente.class \
                   projetos/freedomatd/FTipoAtend.class \
                   projetos/freedomstd/DLRCliente.class \
                   projetos/freedomstd/FCliente.class \
                   projetos/freedomstd/FSetor.class \
                   projetos/freedomstd/DLRSetor.class \
                   projetos/freedomstd/FTipoCli.class \
                   projetos/freedomstd/DLRTipoCli.class \
                   projetos/freedomstd/FVendedor.class \
                   projetos/freedomstd/FClasCli.class \
                   projetos/freedomstd/DLRClasCli.class
CLASS_FREEDOMATD_FILES= \
                   projetos/freedomstdatd/*.class \
                   projetos/freedomstd/FCliente.class \
                   projetos/freedomstd/DLRCliente.class
CLASS_FREEDOMPCP_FILES= \
                   projetos/freedomstdpcp/*.class \
                   projetos/freedomstd/DLGrupo.class \
                   projetos/freedomstd/DLRAlmox.class \
                   projetos/freedomstd/DLRMarca.class \
                   projetos/freedomstd/DLRProduto.class \
                   projetos/freedomstd/DLRUnidade.class \
                   projetos/freedomstd/DLSubGrupo.class \
                   projetos/freedomstd/FAlmox.class \
                   projetos/freedomstd/FCLFiscal.class \
                   projetos/freedomstd/FGrupo.class \
                   projetos/freedomstd/FItCLFiscal.class \
                   projetos/freedomstd/FMarca.class \
                   projetos/freedomstd/FProduto.class \
                   projetos/freedomstd/FUnidade.class
CLASS_FREEDOMFNC_FILES= \
                   projetos/freedomfnc/*.class \
                   projetos/freedomstd/FBanco.class \
                   projetos/freedomstd/FCentroCusto.class \
                   projetos/freedomstd/FClasCli.class \
                   projetos/freedomstd/FCliente.class \
                   projetos/freedomstd/FConta.class \
                   projetos/freedomstd/FEmpresa.class \
                   projetos/freedomstd/FLanca.class \
                   projetos/freedomstd/FMoeda.class \
                   projetos/freedomstd/FLanca*.class \
                   projetos/freedomstd/FPapel.class \
                   projetos/freedomstd/FManutPag.class \
                   projetos/freedomstd/FManutRec.class \
                   projetos/freedomstd/FSetor.class \
                   projetos/freedomstd/FModBoleto.class \
                   projetos/freedomstd/FPlanoPag.class \
                   projetos/freedomstd/FRBoleto.class \
                   projetos/freedomstd/FRExtrato.class \
                   projetos/freedomstd/FFornecedor.class \
                   projetos/freedomstd/FImpressora*.class \
                   projetos/freedomstd/FRPagar.class \
                   projetos/freedomstd/FRRazaoFin.class \
                   projetos/freedomstd/FRReceber.class \
                   projetos/freedomstd/FTerminal.class \
                   projetos/freedomstd/FTipoCli.class \
                   projetos/freedomstd/FTipoCob.class \
                   projetos/freedomstd/FImpressora*.class \
                   projetos/freedomstd/FTipoFor.class \
                   projetos/freedomstd/FImpressora.class \
                   projetos/freedomstd/FPlanejamento.class \
                   projetos/freedomstd/FVendedor.class \
                   projetos/freedomstd/FProcessaSL*.class \
                   projetos/freedomstd/DLRBanco.class \
                   projetos/freedomstd/DLCCPrim.class \
                   projetos/freedomstd/DLCCSin.class \
                   projetos/freedomstd/FProcessaSL*.class \
                   projetos/freedomstd/FProcessaSL.class \
                   projetos/freedomstd/FRBalancete.class \
                   projetos/freedomstd/FRBalanceteGrafico.class \
                   projetos/freedomstd/DLCCAnal.class \
                   projetos/freedomstd/DLRClasCli.class \
                   projetos/freedomstd/DLRCliente.class \
                   projetos/freedomstd/FSubLanca.class \
                   projetos/freedomstd/DLBaixaPag.class \
                   projetos/freedomstd/DLBaixaPag.class \
                   projetos/freedomstd/DLEditaPag.class \
                   projetos/freedomstd/DLEditaRec.class \
                   projetos/freedomstd/DLPlanPrim.class \
                   projetos/freedomstd/FRCentroCusto.class \
                   projetos/freedomstd/DLPlanSin.class \
                   projetos/freedomstd/DLAnalBanc.class \
                   projetos/freedomstd/FRFluxoCaixa.class \
                   projetos/freedomstd/FRGraficoCC.class \
                   projetos/freedomstd/FRInadimplentes.class \
                   projetos/freedomstd/FTerminal*.class \
                   projetos/freedomstd/DLRCentroCusto.class \
                   projetos/freedomstd/DLRFornecedor.class \
                   projetos/freedomstd/DLDataTransf.class \
                   projetos/freedomstd/DLPlanAnal.class \
                   projetos/freedomstd/DLNovoPag*.class \
                   projetos/freedomstd/DLNovoRec*.class \
                   projetos/freedomstd/DLRSetor.class \
                   projetos/freedomstd/DLRPlanejamento.class \
                   projetos/freedomstd/DLRTipoCli.class \
                   projetos/freedomstd/DLRPlanoPag.class \
                   projetos/freedomstd/DLRTipoCob.class \
                   projetos/freedomstd/DLRTipoFor.class \
                   projetos/freedomstd/DLFechaPag.class \
                   projetos/freedomatd/FConveniado.class
CLASS_FREEDOMSTD_FILES= \
                   projetos/freedomstd/*.class \
                   projetos/freedomatd/DLDescontItOrc.class
CLASS_BIBLI_FILES= \
	bibli/compo/*.class \
	bibli/telas/*.class \
	bibli/acao/*.class \
	bibli/funcoes/*.class
CLASS_LAYOUT_FILES=layout/*.class
CLASS_RELATO_FILES=relato/*.class
CLASS_BMP_FILES=bmps/*.class
IMG_FILES=bmps/*.gif bmps/*.jpg

all: projetos layout relato bmps bibli

projetos:
	$(MAKE) -C projetos

layout: 
	$(MAKE) -C layout

relato: 
	$(MAKE) -C relato

bmps: 
	$(MAKE) -C bmps

bibli: 
	$(MAKE) -C bibli

freedom_jar: 
	$(JAR) \
	$(JAR_FLAGS) \
	$(JAR_ARQ) \
	$(CLASS_BIBLI_FILES) \
	$(CLASS_BMP_FILES) \
	$(CLASS_FREEDOMSTD_FILES) \
	$(CLASS_LAYOUT_FILES) \
	$(CLASS_RELATO_FILES) \
	$(IMG_FILES)
	
freedomfnc_jar: 
	$(JAR) \
	$(JAR_FLAGS) \
	$(JAR_FNC_ARQ) \
	$(CLASS_BIBLI_FILES) \
	$(CLASS_BMP_FILES) \
	$(CLASS_FREEDOMFNC_FILES) \
	$(CLASS_LAYOUT_FILES) \
	$(CLASS_RELATO_FILES) \
	$(IMG_FILES)

freedomcfg_jar: 
	$(JAR) \
	$(JAR_FLAGS) \
	$(JAR_CFG_ARQ) \
	$(CLASS_BIBLI_FILES) \
	$(CLASS_BMP_FILES) \
	$(CLASS_LAYOUT_FILES) \
	$(IMG_FILES) \
	$(CLASS_FREEDOMCFG_FILES)

freedomatd_jar: 
	$(JAR) \
	$(JAR_FLAGS) \
	$(JAR_ATD_ARQ) \
	$(CLASS_BIBLI_FILES) \
	$(CLASS_BMP_FILES) \
	$(CLASS_LAYOUT_FILES) \
	$(IMG_FILES) \
	$(CLASS_FREEDOMATD_FILES)

freedompcp_jar: 
	$(JAR) \
	$(JAR_FLAGS) \
	$(JAR_PCP_ARQ) \
	$(CLASS_BIBLI_FILES) \
	$(CLASS_BMP_FILES) \
	$(CLASS_LAYOUT_FILES) \
	$(IMG_FILES) \
	$(CLASS_FREEDOMPCP_FILES)

freedomtmk_jar: 
	$(JAR) \
	$(JAR_FLAGS) \
	$(JAR_TMK_ARQ) \
	$(CLASS_BIBLI_FILES) \
	$(CLASS_BMP_FILES) \
	$(CLASS_LAYOUT_FILES) \
	$(IMG_FILES) \
	$(CLASS_FREEDOMTMK_FILES)

doc: 
	$(JAVADOC) $(JAVADOC_FLAGS) $(PACOTES)
	
rpm: 
	mkdir -p $(RPM_BUILD_DIR)
	cp \
        $(SH_ARQ) \
        $(INI_ARQ) \
        $(JAR_ARQ) \
        $(JAR_BIBLI_ARQS) \
        $(RPM_BUILD_DIR)
	rpm -ba monta.spec
	cp $(RPM_BINARY_DIR)/freedom*.rpm ./

jar: freedomstd_jar
	
clean:
	rm -rf \
	$(CLASS_BIBLI_FILES) \
	$(CLASS_BMP_FILES) \
	$(CLASS_FREEDOMSTD_FILES) \
	$(CLASS_FREEDOMTMK_FILES) \
	$(CLASS_FREEDOMFNC_FILES) \
	$(CLASS_FREEDOMPDV_FILES) \
	$(CLASS_FREEDOMCFG_FILES) \
	$(CLASS_FREEDOMATD_FILES) \
	$(CLASS_FREEDOMPCP_FILES) \
	$(RPM_BUILD_DIR) \
	$(CLASS_LAYOUT_FILES)

.PHONY: \
	projetos \
	layout \
	bmps \
	bibli \
	freedomstd_jar \
	freedomcfg_jar \
	freedomatd_jar \
	jar \
	all \
	doc \
	clean
