SET SQL DIALECT 3;

SET NAMES WIN1252;

SET CLIENTLIB 'fbclient.dll';

CONNECT 'localhost:c:/opt/firebird/dados/desenv/freedom.fdb'
  USER 'SYSDBA'
  PASSWORD 'masterkey';

DROP DATABASE 'localhost:c:/opt/firebird/dados/desenv/freedom.fdb';

CREATE DATABASE 'localhost:c:/opt/firebird/dados/desenv/freedom.fdb'
  USER 'SYSDBA' PASSWORD 'masterkey'
  PAGE_SIZE 16384
  DEFAULT CHARACTER SET WIN1252 COLLATION WIN_PTBR;

/*  Character sets */
ALTER CHARACTER SET WIN1252 SET DEFAULT COLLATION WIN_PTBR;

INPUT freedom.sql;

COMMIT WORK;

INPUT description.sql;

COMMIT WORK;
