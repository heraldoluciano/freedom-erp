Summary: Freedom - Módulo Standart
Name: freedom
Version: 1.0 
Release: 10 
Group: Applications/Client
License: LPG
BuildRoot: /var/tmp/%{name}-buildroot
%description
Este é o módulo standart do sistema Freedom, ele compreende
a funções básicas para o funcionamento das rotinas de:

- Estoque
- Financeiro
- Fiscal
- Venda
- Compra
- Produção

%install

cd freedom

rm -rf $RPM_BUILD_ROOT
mkdir -p $RPM_BUILD_ROOT/opt/freedom/jars

install -s -m 744 freedom.ini $RPM_BUILD_ROOT/opt/freedom/freedom.ini
install -s -m 755 Freedom.sh $RPM_BUILD_ROOT/opt/freedom/Freedom.sh
install -s -m 755 freedomstd.jar $RPM_BUILD_ROOT/opt/freedom/jars/freedomstd.jar
install -s -m 755 interclient.jar $RPM_BUILD_ROOT/opt/freedom/jars/interclient.jar
install -s -m 755 jfreechart-0.9.12.jar $RPM_BUILD_ROOT/opt/freedom/jars/jfreechart-0.9.12.jar
install -s -m 755 jcommon-0.8.7.jar $RPM_BUILD_ROOT/opt/freedom/jars/jcommon-0.8.7.jar

%clean
rm -rf $RPM_BUILD_ROOT

%files
%dir /opt/freedom
%dir /opt/freedom/jars
/opt/freedom/freedomstd.ini
/opt/freedom/Freedom.sh
/opt/freedom/jars/freedomstd.jar
/opt/freedom/jars/interclient.jar
/opt/freedom/jars/jfreechart-0.9.12.jar
/opt/freedom/jars/jcommon-0.8.7.jar

%changelog
* Tue Dec 09 2003 Fernando Oliveira da Silva <fernando@stpinf.com> 
- Primeira versão RPM do freedom (release 10)

