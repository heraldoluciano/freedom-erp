update sguf set nomecurto=rtrim(substring(nomeuf from 11 for 80))
where substring(nomeuf from 1 for 9) in ('Estado do','Estado de', 'Estado da');

update sguf set nomecurto=rtrim(substring(nomeuf from 15 for 80))
where substring(nomeuf from 1 for 13) in ('Território de');

update sguf set nomecurto=nomeuf
where nomeuf in ('Distrito Federal');

commit work;
