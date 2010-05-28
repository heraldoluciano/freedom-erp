@echo "Publica‡Æo do disco de instala‡Æo Windows"
copy \arquiv~1\bitroc~1.1\output\*.exe ..\disco\win
ftp -s:ftpexe.ftp -A upload.sourceforge.net
