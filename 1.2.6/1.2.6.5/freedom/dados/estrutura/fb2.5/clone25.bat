echo off
REM  +--------------------------------------------------------+
REM  | Opções do fbclone
REM  +--------------------------------------------------------+
REM 'h',  'help',            '', 'Show this help message', false);
REM 'v',  'verbose',         '', 'Show details', false);
REM 'po', 'pump-only',       '', 'Only pump data from source database into target database (source database and target database must share the same metadata structure)', false);
REM 'e',  'empty-tables',    '', 'Empty tables before data pump', false);
REM 'd',  'dump',            'file', 'Dump SQL script into file', false);
REM 'rd', 'repair-dump',     'file', 'Trace errors and SQL into a repair.sql file', false);
REM 'ps', 'page-size',       'page size', 'Overrides target database page size', false);
REM 's',  'source',          'database', 'Source database connection string', true);
REM 'su', 'source-user',     'user',     'User name used to connect source database', false);
REM 'sp', 'source-password', 'password', 'Password used to connect source database', false);
REM 'sl', 'source-library',  'library',  'Client Library used to connect source database', false);
REM 't',  'target',          'database', 'Target database connection string', true);
REM 'tu', 'target-user',     'user',     'User name used to connect target database', false);
REM 'tp', 'target-password', 'password', 'Password used to connect target dat base', false);
REM 'tl', 'target-library',  'library',  'Client Library used to connect target database', false);
REM 'tc', 'target-charset',  'charset',  'Target database default character set (default: source charset)', false);
REM 'rc', 'read-charset',    'charset',  'Character set to read from source database (default: source charset)', false);
REM 'wc', 'write-charset',   'charset',  'Character set to write into target database (default: source charset)', false);
REM 'ics','ignore-charset',    '',        'Ignore Source database Character set (force using default)', false);
REM 'ko', 'keep-octets',       '',        'Keep OCTETS Charset (use with -ics)', false);
REM 'ic', 'ignore-collation',  '',        'Ignore Source database Collation (force using default)', false);
REM 'xt', 'exclude-table',   'list', 'Comma separated list of tables to exclude from data pump', false);
REM 'u',  'user',     'user',     'User name used to connect both source and target databases', false);
REM 'p',  'password', 'password', 'Password used to connect both source and target databases', false);
REM 'l',  'library',  'library',  'Client Library used to connect both source and target databases', false);
REM 'f',  'failsafe',        '',          'Commit transaction every record (same as using -ci 1)', false);
REM 'ci', 'commit-interval', 'number',    'Commit transaction every <number> record', false);
REM  +--------------------------------------------------------+
echo off
SET ISC_USER=SYSDBA
SET ISC_PASSWORD=masterkey
echo on
del c:\opt\firebird\dados\desenv\FREEDOM_TABELAS_T.FDB
copy /y c:\opt\firebird\dados\desenv\FREEDOM_TABELAS.FDB c:\opt\firebird\dados\desenv\FREEDOM_TABELAS_T.FDB
fbclone25.exe -v -ic -ics -ci 10000 -po -e -rd erros.log -s localhost/3050:c:/opt/firebird/dados/desenv/FREEDOM_25_NONE.FDB -t localhost/3050:c:/opt/firebird/dados/desenv/FREEDOM_TABELAS_T.FDB -tc ISO8859_1 -rc ISO8859_1 -wc ISO8859_1
pause
