
#pragma hdrstop
#include <condefs.h>
#include <stdio.h>          //c header for printf and file functions
#include <string.h>          //c header for printf and file functions
#include <stdlib.h>         //c header for conversion functions (atoi, etc)
#include <conio.h>          //c header for keyboard reading functions
#include <dos.h>          //c header for keyboard reading functions



//---------------------------------------------------------------------------
#pragma argsused
int LogInformation(char *theString)
{
  /* Declare variables to be used */
  FILE *fpLog;

  if((fpLog=fopen("scores.dat","a+t"))==NULL)
    {
      return -1;
    } else {
      fprintf (fpLog, "%s",theString);

      // Clean up after ourself
      fclose(fpLog);
    }

  return 0;
} // End of LogInformation().


int main(int argc, char **argv)
{
    /* Declare the variables to be used */
    int i,j,tempchar,ans,retVal;
    int *a,*b,*c,*d,*e;
    int results[5][100];
    char logString[100];
 //   char srcLine[50];
    FILE *fSource;

    a=(int *)(malloc(1*sizeof(int)));
    b=(int *)(malloc(1*sizeof(int)));
    c=(int *)(malloc(1*sizeof(int)));
    d=(int *)(malloc(1*sizeof(int)));
    e=(int *)(malloc(1*sizeof(int)));
    /* Read in the file */
    if((fSource=fopen("scores.dat","rt"))!=NULL)
    {
      j=0;
      retVal=0;
      while(retVal!=EOF) {
        retVal=fscanf(fSource,"%d|%d|%d|%d|%d\n",a,b,c,d,e);
        results[0][j]=*a;
        results[1][j]=*b;
        results[2][j]=*c;
        results[3][j]=*d;
        results[4][j]=*e;
        j++;
      }
      for(j=0;j<100;j++) {
          printf("A: %d",results[0][j]);
          printf("|B: %d",results[1][j]);
          printf("|C: %d",results[2][j]);
          printf("|D: %d",results[3][j]);
          printf("|E: %d\n",results[4][j]);
      }
      fclose(fSource);
    } else {
      for(i=0;i<5;i++) {
        for(j=0;j<100;j++) {
          results[i][j]=0;
        }
      }
    }

    ans = 0;
    while ((ans!='n') && (ans!='N')) {
      clrscr();
      for(j=0;j<100;j++) {
          printf("Answer %d:",j + 1);
          tempchar = 0;
          while ((tempchar < 97) || (tempchar > 101)) {
          	  tempchar=getch();
          	  printf("Illegal: %d",tempchar);
          	  if (tempchar==8) {
          	  	 return 0;
          	  }
          }
          results[tempchar - 97][j]++;
          clrscr();
      }
      ans=0;
      while ((ans!='y') && (ans!='Y') && (ans!='n') && (ans!='N')) {
        printf("Enter another test?[y/n]: ");
        ans = getch();
        clrscr();
      }
    }

    for(j=0;j<100;j++) {
        printf("A: %d",results[0][j]);
        printf("|B: %d",results[1][j]);
        printf("|C: %d",results[2][j]);
        printf("|D: %d",results[3][j]);
        printf("|E: %d\n",results[4][j]);
        sprintf(logString,"%d|%d|%d|%d|%d\n",results[0][j],results[1][j],results[2][j],results[3][j],results[4][j]);
        LogInformation(logString);
    }
} // End of main()
