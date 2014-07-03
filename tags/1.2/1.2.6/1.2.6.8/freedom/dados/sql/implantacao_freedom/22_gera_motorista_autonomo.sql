insert into vdmotorista (
    codemp, codfilial, codmot, cnh, codpais, codmunic, siglauf, nomemot, cpfmot,
    endmot, nummot, complmot, bairmot, cepmot, fonemot, celmot, dddmot, conjugemot,
    nrodependmot, rgmot, sspmot, nropismot, codemptn, codfilialtn, codtran )
select
    codemp, codfilial, (select coalesce(max(codmot) + 1, 1) from vdmotorista) , '000', codpais, codmunic, siglauf, nometran, cpftran,
    endtran, numtran, compltran, bairtran, ceptran, fonetran, celtran, dddfonetran, conjugetran,
    nrodependtran, rgtran, ssptran, nropistran, codemp, codfilial, codtran
from vdtransp
where placatran is not null and codemp=#CODEMP# and codfilial=1;
