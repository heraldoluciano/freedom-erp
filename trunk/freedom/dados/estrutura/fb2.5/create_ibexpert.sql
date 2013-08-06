SET SQL DIALECT 3;

SET NAMES ISO8859_1;

CONNECT 'localhost:c:/opt/firebird/dados/desenv/freedom.fdb'
  USER 'SYSDBA'
  PASSWORD 'masterkey';

DROP DATABASE 'localhost:c:/opt/firebird/dados/desenv/freedom.fdb';

CREATE DATABASE 'localhost:c:/opt/firebird/dados/desenv/freedom.fdb'
  USER 'SYSDBA' PASSWORD 'masterkey'
  PAGE_SIZE 16384
  DEFAULT CHARACTER SET ISO8859_1 COLLATION PT_BR;

ALTER CHARACTER SET ISO8859_1 SET DEFAULT COLLATION PT_BR;

INPUT freedom.sql;

COMMIT WORK;

INPUT description.sql;

COMMIT WORK;

/*
SET SQL DIALECT 3;

SET NAMES WIN1252;

CONNECT 'localhost:c:/opt/firebird/dados/desenv/freedom.fdb'
  USER 'SYSDBA'
  PASSWORD 'masterkey';

DROP DATABASE 'localhost:c:/opt/firebird/dados/desenv/freedom.fdb';

CREATE DATABASE 'localhost:c:/opt/firebird/dados/desenv/freedom.fdb'
  USER 'SYSDBA' PASSWORD 'masterkey'
  PAGE_SIZE 16384
  DEFAULT CHARACTER SET WIN1252 COLLATION WIN_PTBR;

ALTER CHARACTER SET WIN1252 SET DEFAULT COLLATION WIN_PTBR;

INPUT freedom.sql;

COMMIT WORK;

INPUT description.sql;

COMMIT WORK;
*/
