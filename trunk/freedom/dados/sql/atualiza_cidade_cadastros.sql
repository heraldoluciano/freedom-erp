
/* Atualiza o cadastro de empresas */

update sgempresa emp
set emp.codpais=(select mu.codpais from sgmunicipio mu
                 where rtrim(lower(emp.ufemp))=rtrim(lower(mu.siglauf))
                 and rtrim(lower(emp.cidemp))=rtrim(lower(mu.nomemunic))),
 emp.siglauf=(select mu.siglauf from sgmunicipio mu
                 where rtrim(lower(emp.ufemp))=rtrim(lower(mu.siglauf))
                 and rtrim(lower(emp.cidemp))=rtrim(lower(mu.nomemunic))),
 emp.codmunic=(select mu.codmunic from sgmunicipio mu
                 where rtrim(lower(emp.ufemp))=rtrim(lower(mu.siglauf))
                 and rtrim(lower(emp.cidemp))=rtrim(lower(mu.nomemunic)))
 where emp.codmunic is null;

/* Atualiza o cadastro de filiais */

update sgfilial fil
set fil.codpais=(select mu.codpais from sgmunicipio mu
                 where rtrim(lower(fil.uffilial))=rtrim(lower(mu.siglauf))
                 and rtrim(lower(fil.cidfilial))=rtrim(lower(mu.nomemunic))),
 fil.siglauf=(select mu.siglauf from sgmunicipio mu
                 where rtrim(lower(fil.uffilial))=rtrim(lower(mu.siglauf))
                 and rtrim(lower(fil.cidfilial))=rtrim(lower(mu.nomemunic))),
 fil.codmunic=(select mu.codmunic from sgmunicipio mu
                 where rtrim(lower(fil.uffilial))=rtrim(lower(mu.siglauf))
                 and rtrim(lower(fil.cidfilial))=rtrim(lower(mu.nomemunic)))
 where fil.codmunic is null;

/* Atualiza o cadastro de clientes endereço principal*/

update vdcliente cli
set cli.codpais=(select mu.codpais from sgmunicipio mu
                 where rtrim(lower(cli.ufcli))=rtrim(lower(mu.siglauf))
                 and rtrim(lower(cli.cidcli))=rtrim(lower(mu.nomemunic))),
 cli.siglauf=(select mu.siglauf from sgmunicipio mu
                 where rtrim(lower(cli.ufcli))=rtrim(lower(mu.siglauf))
                 and rtrim(lower(cli.cidcli))=rtrim(lower(mu.nomemunic))),
 cli.codmunic=(select mu.codmunic from sgmunicipio mu
                 where rtrim(lower(cli.ufcli))=rtrim(lower(mu.siglauf))
                 and rtrim(lower(cli.cidcli))=rtrim(lower(mu.nomemunic)))
 where cli.codmunic is null;

/* Atualiza o cadastro de clientes endereço de entrega*/

update vdcliente cli
set cli.codpaisent =(select mu.codpais from sgmunicipio mu
                 where rtrim(lower(cli.ufent))=rtrim(lower(mu.siglauf))
                 and rtrim(lower(cli.cident))=rtrim(lower(mu.nomemunic))),
 cli.siglaufent=(select mu.siglauf from sgmunicipio mu
                 where rtrim(lower(cli.ufent))=rtrim(lower(mu.siglauf))
                 and rtrim(lower(cli.cident))=rtrim(lower(mu.nomemunic))),
 cli.codmunicent=(select mu.codmunic from sgmunicipio mu
                 where rtrim(lower(cli.ufent))=rtrim(lower(mu.siglauf))
                 and rtrim(lower(cli.cident))=rtrim(lower(mu.nomemunic)))
 where cli.codmunicent is null;

/* Atualiza o cadastro de clientes endereço de cobrança*/

update vdcliente cli
set cli.codpaiscob =(select mu.codpais from sgmunicipio mu
                 where rtrim(lower(cli.ufcob))=rtrim(lower(mu.siglauf))
                 and rtrim(lower(cli.cidcob))=rtrim(lower(mu.nomemunic))),
 cli.siglaufcob=(select mu.siglauf from sgmunicipio mu
                 where rtrim(lower(cli.ufcob))=rtrim(lower(mu.siglauf))
                 and rtrim(lower(cli.cidcob))=rtrim(lower(mu.nomemunic))),
 cli.codmuniccob=(select mu.codmunic from sgmunicipio mu
                 where rtrim(lower(cli.ufcob))=rtrim(lower(mu.siglauf))
                 and rtrim(lower(cli.cidcob))=rtrim(lower(mu.nomemunic)))
 where cli.codmuniccob is null;

update cpforneced forn
set forn.codpais=(select mu.codpais from sgmunicipio mu
                 where rtrim(lower(forn.uffor))=rtrim(lower(mu.siglauf))
                 and rtrim(lower(forn.cidfor))=rtrim(lower(mu.nomemunic))),
 forn.siglauf=(select mu.siglauf from sgmunicipio mu
                 where rtrim(lower(forn.uffor))=rtrim(lower(mu.siglauf))
                 and rtrim(lower(forn.cidfor))=rtrim(lower(mu.nomemunic))),
 forn.codmunic=(select mu.codmunic from sgmunicipio mu
                 where rtrim(lower(forn.uffor))=rtrim(lower(mu.siglauf))
                 and rtrim(lower(forn.cidfor))=rtrim(lower(mu.nomemunic)))
 where forn.codmunic is null;

update vdtransp t
set t.codpais=(select mu.codpais from sgmunicipio mu
                 where rtrim(lower(t.uftran))=rtrim(lower(mu.siglauf))
                 and rtrim(lower(t.cidtran))=rtrim(lower(mu.nomemunic))),
 t.siglauf=(select mu.siglauf from sgmunicipio mu
                 where rtrim(lower(t.uftran))=rtrim(lower(mu.siglauf))
                 and rtrim(lower(t.cidtran))=rtrim(lower(mu.nomemunic))),
 t.codmunic=(select mu.codmunic from sgmunicipio mu
                 where rtrim(lower(t.uftran))=rtrim(lower(mu.siglauf))
                 and rtrim(lower(t.cidtran))=rtrim(lower(mu.nomemunic)))
 t.codmunic is null;

update tkcontato tk
set tk.codpais=(select mu.codpais from sgmunicipio mu
                 where rtrim(lower(tk.ufcto))=rtrim(lower(mu.siglauf))
                 and rtrim(lower(tk.cidcto))=rtrim(lower(mu.nomemunic))),
 tk.siglauf=(select mu.siglauf from sgmunicipio mu
                 where rtrim(lower(tk.ufcto))=rtrim(lower(mu.siglauf))
                 and rtrim(lower(tk.cidcto))=rtrim(lower(mu.nomemunic))),
 tk.codmunic=(select mu.codmunic from sgmunicipio mu
                 where rtrim(lower(tk.ufcto))=rtrim(lower(mu.siglauf))
                 and rtrim(lower(tk.cidcto))=rtrim(lower(mu.nomemunic)))
 tk.codmunic is null;

commit work

