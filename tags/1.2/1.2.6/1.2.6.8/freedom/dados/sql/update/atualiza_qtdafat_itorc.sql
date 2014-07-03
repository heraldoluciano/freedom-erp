update vditorcamento i set emmanut='S' , qtdfatitorc=0, qtdafatitorc=0
  where qtdfatitorc is null or qtdafatitorc is null;

update vditorcamento i set i.emmanut='S', i.qtdfatitorc=i.qtditorc
  where i.fatitorc='S';
  
update vditorcamento i set i.emmanut='S', i.qtdafatitorc=i.qtditorc
  where i.fatitorc='N';

update vditorcamento i set i.emmanut='N' where i.emmanut='S';

commit work;
