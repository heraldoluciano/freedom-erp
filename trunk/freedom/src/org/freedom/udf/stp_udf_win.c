#include <unixpass.h>
#include <stdio.h>
char *ib_password(char *val);
int printLog(char *val);
char *ib_password(char *val) {
	val = (char *)mycrypt(val,"9z");
	val = (char *)(mycrypt(val+2,"9z")+2);
	return (char *)val;	
}

int printLog(char *val) {
   printf(val);
   return 0;
}