SELECT C.CODCLI CODEMIT, C.RAZCLI RAZEMIT,  CAST( ' 2008-2-2' AS DATE) DATA, 'A' TIPO, 0 DOC,
( COALESCE( ( SELECT SUM(R.VLRREC)
                FROM FNRECEBER R
                    WHERE R.CODEMP=25 AND R.CODFILIAL=1 AND R.CODEMPCL=C.CODEMP AND
                          R.CODFILIALCL=C.CODFILIAL AND R.CODCLI=C.CODCLI AND R.DATAREC < '2008-02-02' ),0)
                          - COALESCE( ( SELECT SUM(L.VLRLANCA)
                                            FROM FNLANCA L
                                                WHERE L.CODEMPCL=25 AND L.CODFILIALCL=1 AND L.CODCLI=C.CODCLI AND
                                                      L.DATALANCA < '2008-02-02'
           ), 0
) ) VLRDEB,

( COALESCE( ( SELECT SUM(CP.vlrliqcompra)
                FROM cpcompra CP, eqtipomov TM, eqclifor CF
                    WHERE CP.CODEMP=25 AND CP.CODFILIAL=1 AND  TM.CODEMP=CP.codemptm and
                          TM.CODFILIAL = CP.codfilialtm AND TM.estipomov='E' and tm.tipomov='DV'
                          AND CF.codemp=C.codemp AND CF.CODFILIAL=C.CODFILIAL AND CF.CODCLI=C.codcli
                          AND CP.codempfr=CF.codempfr AND CP.codfilialfr=CF.codfilialfr
                          AND CP.codfor=CF.codfor
                          AND CP.dtentcompra < '2008-02-02'
           ),0
) ) VLRCRED


FROM VDCLIENTE C
    WHERE C.CODEMP=25 AND C.CODFILIAL=1 AND C.CODCLI=13
          AND ( EXISTS (
                        SELECT *
                            FROM FNRECEBER R2
                                WHERE R2.CODEMP=25 AND R2.CODFILIAL=1 AND R2.CODEMPCL=C.CODEMP
                                    AND R2.CODFILIALCL=C.CODFILIAL AND R2.CODCLI=C.CODCLI
                                    AND DATAREC BETWEEN '2008-02-02' AND '2008-02-02' )
          OR EXISTS (
                      SELECT *
                            FROM FNLANCA L2, FNRECEBER R2
                                WHERE L2.CODEMPRC=R2.CODEMP AND L2.CODFILIALRC=R2.CODFILIAL AND L2.CODREC=R2.CODREC
                                    AND C.CODEMP=R2.CODEMPCL AND C.CODFILIAL=R2.CODFILIALCL AND C.CODCLI=R2.CODCLI
                                    AND R2.CODEMP=25 AND R2.CODFILIAL=1
                                    AND L2.DATALANCA BETWEEN '2008-02-02'  AND '2008-02-02') )

UNION

SELECT R.CODCLI CODEMIT, C.RAZCLI RAZEMIT,R.DATAREC DATA, 'Q' TIPO, R.DOCREC DOC, R.VLRPARCREC VLRDEB,
    (R.VLRPARCREC-R.VLRREC)*-1 VLRCRED
    FROM FNRECEBER R, VDCLIENTE C
        WHERE C.CODEMP=R.CODEMPCL AND C.CODFILIAL=R.CODFILIALCL AND C.CODCLI=R.CODCLI
            AND R.CODEMP=25 AND R.CODFILIAL=1 AND C.CODCLI=13 AND R.DATAREC BETWEEN '2008-02-02' AND '2008-02-02'

UNION

SELECT R.CODCLI CODEMIT,C.RAZCLI RAZEMIT, L.DATALANCA DATA,  'R' TIPO, R.DOCREC DOC, 0.00 VLRDEB, (L.VLRLANCA * -1) VLRCRED
FROM FNLANCA L, FNRECEBER R, VDCLIENTE C
WHERE L.CODEMPRC=R.CODEMP AND L.CODFILIALRC=R.CODFILIAL AND L.CODREC=R.CODREC AND C.CODEMP=R.CODEMPCL
AND C.CODFILIAL=R.CODFILIALCL AND C.CODCLI=R.CODCLI AND C.CODCLI=13 AND R.CODEMP=25 AND R.CODFILIAL=1
AND L.DATALANCA BETWEEN '2008-02-02' AND '2008-02-02'

UNION
SELECT CF.CODCLI CODEMIT,C.RAZCLI RAZEMIT, CP.dtentcompra, 'D' TIPO, CP.doccompra DOC, CP.vlrliqcompra VLRDEB, 0.00 VLRCRED
                FROM cpcompra CP, eqtipomov TM, eqclifor CF,VDCLIENTE C
                    WHERE CP.CODEMP=25 AND CP.CODFILIAL=1 AND  TM.CODEMP=CP.codemptm and
                          TM.CODFILIAL = CP.codfilialtm AND TM.estipomov='E' and tm.tipomov='DV'
                          AND CF.codemp=C.codemp AND CF.CODFILIAL=C.CODFILIAL AND CF.CODCLI=C.codcli
                          AND CP.codempfr=CF.codempfr AND CP.codfilialfr=CF.codfilialfr
                          AND CP.codfor=CF.codfor
                          AND CP.dtentcompra BETWEEN '2008-02-02' AND '2008-02-02'
                          AND C.CODEMP=CF.CODEMP AND C.CODFILIAL=CF.CODFILIAL AND C.CODCLI=CF.CODCLI

ORDER BY 1, 2, 3, 4, 5