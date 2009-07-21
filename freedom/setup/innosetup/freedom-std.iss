[Setup]
AppName=Freedom Standard
AppVerName=Freedom Standard v1.1
AppCopyright=
DefaultDirName=c:\opt\freedom
DefaultGroupName=Freedom Standard
UninstallDisplayIcon={app}\freedomstd.ico
Compression=bzip/9
OutputDir=M:\freedom-installer
[Languages]
Name: "br"; MessagesFile: "pt_BR.isl"

; uncomment the following line if you want your installation to run on NT 3.51 too.
; MinVersion=4,3.51

[Files]
Source: "..\jars\freedomstd.jar"; DestDir: "{app}\jars"; AfterInstall: adicClassPath('jars\freedomstd.jar')
Source: "..\jars\firebirdsql-full.jar"; DestDir: "{app}\jars"; AfterInstall: adicClassPath('jars\firebirdsql-full.jar')
Source: "..\jars\itext-1.02b.jar"; DestDir: "{app}\jars"; AfterInstall: adicClassPath('jars\itext-1.02b.jar')
Source: "..\jars\jcommon-0.8.7.jar"; DestDir: "{app}\jars"; AfterInstall: adicClassPath('jars\jcommon-0.8.7')
Source: "..\jars\jfreechart-0.9.12.jar"; DestDir: "{app}\jars"; AfterInstall: adicClassPath('jars\jfreechart-0.9.12')
Source: "..\bmps\freedomstd.ico"; DestDir: "{app}"; AfterInstall: criaBat(ExpandConstant('{app}'),'freedom.bat','std')

[INI]
Filename: "{app}\freedom.ini"; Section: "parametros"; Key: "driver"; String: "org.firebirdsql.jdbc.FBDriver"; Flags: createkeyifdoesntexist
Filename: "{app}\freedom.ini"; Section: "parametros"; Key: "banco"; String: "jdbc:firebirdsql:{code:getDBLocation}"; Flags: createkeyifdoesntexist
Filename: "{app}\freedom.ini"; Section: "parametros"; Key: "codemp"; String: "99"; Flags: createkeyifdoesntexist
Filename: "{app}\freedom.ini"; Section: "parametros"; Key: "numterm"; String: "1"; Flags: createkeyifdoesntexist
Filename: "{app}\freedom.ini"; Section: "parametros"; Key: "os"; String: "windows"; Flags: createkeyifdoesntexist
Filename: "{app}\freedom.ini"; Section: "parametros"; Key: "temp"; String: "{code:getDirTemp}"; Flags: createkeyifdoesntexist

[Icons]
Name: "{group}\Freedom Standard"; Filename: "{app}\freedom.bat";  WorkingDir: "{app}"; IconFilename: "{app}\freedomstd.ico"; Flags: closeonexit
Name: "{userdesktop}\Freedom Standard"; Filename: "{app}\freedom.bat";  WorkingDir: "{app}"; IconFilename: "{app}\freedomstd.ico"; Flags: closeonexit

[Run]
Flags: postinstall nowait; FileName: "{app}\freedom.bat"; Description: "Executar o Freedom"

[code]

var
  sClassPath: String;
  TipoServer: TInputOptionWizardPage;
  InfoLocalFirebird: TInputDirWizardPage;
  InfoServerFirebird: TInputQueryWizardPage;
const
  fbExe = '\bin\fbguard.exe';
  
procedure InitializeWizard;
begin
  TipoServer := CreateInputOptionPage(wpWelcome,
    'Banco de dados Firebird', 'Onde o servidor Firebird está instalado?',
    'Por favor, selecione o local onde o servidor Firebird está rodando, então clique em Avançar.',
    True, False);
  TipoServer.Add('Neste computador que estou utilizando.');
  TipoServer.Add('Em um servidor separado.');

  InfoLocalFirebird := CreateInputDirPage(TipoServer.ID,
    'Banco de dados Firebird', 'Em qual diretório o Firebird foi instalado?',
    ''+#13#10+'Especifique qual diretório o Firebird está instalado, então clique em Avançar.'+#13#10,
    False, '');
  InfoLocalFirebird.Add('');

  InfoServerFirebird := CreateInputQueryPage(TipoServer.ID,
    'Banco de dados Firebird', 'Em qual servidor o Firebird está rodando?',
    ''+#13#10+'Especifique o servidor Firebird, então clique em Avançar.'+#13#10);
  InfoServerFirebird.Add('Host', False);
  InfoServerFirebird.Add('Porta', False);
  InfoServerFirebird.Add('Caminho completo para o arquivo ''.fdb''.', False);


// Ajustando os parametros default:

  TipoServer.SelectedValueIndex := 0;
  InfoServerFirebird.Values[0] := 'meuservidor.minhaempresa.com.br';
  InfoServerFirebird.Values[1] := '3050';
  InfoServerFirebird.Values[2] := '/opt/firebird/dados/freedom.fdb';
end;

function InitializeSetup(): Boolean;
begin
  sClassPath := '';
  Result := true;
end;
function ShouldSkipPage(PageID: Integer): Boolean;
begin
  { Skip pages that shouldn't be shown }
  if (PageID = InfoLocalFirebird.ID) and (TipoServer.SelectedValueIndex <> 0) then
    Result := True
  else if (PageID = InfoServerFirebird.ID) and (TipoServer.SelectedValueIndex <> 1) then
    Result := True
  else
    Result := False;
end;

function NextButtonClick(CurPageID: Integer): Boolean;
begin
  Result := True;
  if (CurPageID = InfoLocalFirebird.ID) and not FileExists(InfoLocalFirebird.Values[0]+fbExe) then
  begin
    MsgBox('O diretório do Firebird especificado não está correto!', mbInformation, MB_OK)
    Result := False;
    Exit;
  end
  else if (CurPageID = InfoServerFirebird.ID) then
  begin
    if (Trim(InfoServerFirebird.Values[0]) = '') then
    begin
      MsgBox('O host do servidor Firebird está em branco!', mbInformation, MB_OK)
      Result := False;
      Exit;
    end
    else if (Length(InfoServerFirebird.Values[1]) < 1) then
    begin
      MsgBox('A porta do servidor Firebird está em branco!', mbInformation, MB_OK)
      Result := False;
      Exit;
    end
    else if (Trim(InfoServerFirebird.Values[2]) = '') then
    begin
      MsgBox('O caminho do arquivo ''.fdb'' está em branco!!', mbInformation, MB_OK)
      Result := False;
      Exit;
    end;
    try
      StrToInt(InfoServerFirebird.Values[1]);
    except
      MsgBox('A porta do servidor Firebird especificada não está correta!', mbInformation, MB_OK)
      Result := False;
      Exit;
    end;
  end;
end;
procedure adicLinha(var sVar: String;sVal: String);
begin
  sVar := sVar + sVal + #13#10;
end;
procedure adicClassPath(sVal: String);
begin
  sClassPath := sClassPath + sVal + ';';
end;
procedure criaBat(sCam,sArq,sMod: String);
var
  sTexto: String;
begin
  sTexto := '';
  adicLinha(sTexto,'cd '+sCam);
  adicLinha(sTexto,'javaw -classpath "'+sClassPath+'" projetos.freedom'+sMod+'.Freedom'+upperCase(sMod));
  SaveStringToFile(sCam+'\'+sArq,sTexto,false);
end;
function getDBLocation(Param: String): String;
begin
  Result := '';
  if (TipoServer.SelectedValueIndex = 0) then
    Result := 'localhost/3050:'+InfoLocalFirebird.Values[0]
  else if (TipoServer.SelectedValueIndex = 1) then
    Result := InfoServerFirebird.Values[0]+'/'+InfoServerFirebird.Values[1]+':'+InfoServerFirebird.Values[2];
end;
function getDirTemp(Param: String): String;
begin
  Result := GetTempDir;
end;
function UpdateReadyMemo(Space, NewLine, MemoUserInfoInfo, MemoDirInfo, MemoTypeInfo,
  MemoComponentsInfo, MemoGroupInfo, MemoTasksInfo: String): String;
var
  S: String;
begin

  S := NewLine + 'String de conexão com Firebird:' + NewLine;
  S := S + Space + getDBLocation('') + NewLine + NewLine;
  S := S + MemoGroupInfo + NewLine + NewLine;
  S := S + MemoDirInfo + NewLine + NewLine;

  Result := S;
end;

