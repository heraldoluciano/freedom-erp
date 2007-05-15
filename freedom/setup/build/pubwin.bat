@echo "Publica‡Æo do disco de instala‡Æo Windows"
rem "call geradisco.bat"
rem "copy ..\disco\win\disco.zip .\freedom_1_1_3_0.zip"
ftp -s:ftpwin.cmd -A upload.sourceforge.net
rem "del .\freedom_1_1_3_0.zip /Q"
