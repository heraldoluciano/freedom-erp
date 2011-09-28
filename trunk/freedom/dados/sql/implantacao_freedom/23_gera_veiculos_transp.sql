insert into vdveiculo (
    codemp, codfilial, codveic ,placa, codpais, codmunic, siglauf, codemptn, codfilialtn, codtran )
select
    codemp, codfilial, (select coalesce(max(codveic) + 1, 1) from vdveiculo ) ,placatran , codpais, codmunic, siglauf,
    codemp, codfilial, codtran
from vdtransp
where placatran is not null and codemp=#CODEMP# and codfilial=1;
