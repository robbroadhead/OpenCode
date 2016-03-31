#include <stdio.h>          //c header for printf and file functions
#include <string.h>          //c header for printf and file functions
#include <stdlib.h>         //c header for conversion functions (atoi, etc)
#include <conio.h>          //c header for keyboard reading functions
#include <dos.h>          //c header for keyboard reading functions

void setcur(int rowp,int colp)
{
    /* Declare the variables to be used */
    union REGS  inreg,outreg;               // input,output registers
    
    inreg.h.ah = 2;
    inreg.h.bh = 0;
    inreg.h.dh = (char) rowp;
    inreg.h.dl = (char) colp;
    int86 (0x10, &inreg,&outreg);
} // End of setcur()


void clrscreen()
{
    /* Declare the variables to be used */
    union REGS  inreg,outreg;               // input,output registers

    setcur(0,0);
    inreg.h.ah = 10;
    inreg.h.bh = 0;
    inreg.x.cx = 2048;
    inreg.h.al = ' ';
    int86 (0x10,&inreg,&outreg);
} // End of clrscreen().


int LogInformation(char *theString)
{
  /* Declare variables to be used */
  FILE *fpLog;

  if((fpLog=fopen("c:\\result.txt","a+t"))==NULL)
    {
      return -1;
    } else {
      fprintf (fpLog, "%s",theString);
        
      // Clean up after ourself
      fclose(fpLog);
    }
    
  return 0;
} // End of LogInformation().


void main()
{
    /* Declare the variables to be used */
    int i,j,tempchar;
    int results[5][100];
    char logString[100];

    for(i=0;i<5;i++) {
        for(j=0;j<100;j++) {
            results[i][j]=0;
        }
    }
    
    clrscreen();
    for(j=0;j<100;j++) {
        printf("Answer %d:",j + 1);
        while ((tempchar < 97) || (tempchar > 101)) {
        	tempchar=getch();
        	if (tempchar = 8) {
        		return;
        	}
        }
        results[tempchar - 97][j]++;
        clrscreen();
    }
    
    for(j=0;j<100;j++) {
        strcpy(logString,"");
        sprintf(logString,"A: %d",results[0][j]);
        sprintf(logString,"%s|B: %d",logString,results[1][j]);
        sprintf(logString,"%s|C: %d",logString,results[2][j]);
        sprintf(logString,"%s|D: %d",logString,results[3][j]);
        sprintf(logString,"%s|E: %d\n",logString,results[4][j]);
        printf("%s",logString);
        LogInformation(logString);
    }
} // End of main()
