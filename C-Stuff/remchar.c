/****************************************************************************
 *  File: RemChar.c 
 *
 *  Purpose:  Removes all occurances of a character from a text file
 *
 *  Developed by:  Rob Broadhead
 *
 *  Modification Log:
 *     09-24-1999: Created file
 *     
 ****************************************************************************/

/* RemChar.c */
#include <stdio.h>
#include <string.h>
#include <stdlib.h>

/* Constant Values */
#define VERSION "1.0"

/* Declare pointers for in and out files */
FILE  *fSource ;
FILE  *fTarget;
char  *sourceName;
char  *theChar;

/* We read in sourceLine, parse it and spit it */
/* back out as resultLine                      */
char  *sourceLine;
char  *resultLine;


void RemoveCharacter( )
{
  /* Declare the variables to be used */
  int  curChar,resCount;
  int nSize;

  nSize=1024;
  if ( ( fSource = fopen ( sourceName,"rt" ) ) != NULL ) 
    {
      /* Start the file pointer at the beginning */
      rewind ( fSource );
      /* Clear out the storage strings to be safe */
      sourceLine=(char *) calloc(1,sizeof(char)*nSize);
      resultLine=(char *) calloc(1,sizeof(char)*nSize);

      resCount=0;
      curChar=fgetc(fSource);
      /* Pull characters in until we get end-of-line */
      while (curChar != EOF ) {
        /* Allocate space */
        free(sourceLine);
        free(resultLine);

        sourceLine=(char *) calloc(1,sizeof(char)*nSize);
        resultLine=(char *) calloc(1,sizeof(char)*nSize);
        resCount=0;
        
        while (curChar != 10 ) { 
          /* Load the sourceLine */
          if (curChar != theChar[0]) {
            resultLine[resCount++]=curChar;
          }
          curChar=fgetc(fSource);
        } // End of while loop  
      
        if ( ( fTarget = fopen ( "result.txt", "a" ) ) != NULL )
        {   
          fprintf ( fTarget, resultLine );
          fprintf ( fTarget, "\n");
          fclose ( fTarget );
        }
        else
          printf ( "Error: Couldn't create/open target file. \n" );

        curChar=fgetc(fSource);
        curChar=fgetc(fSource);
      } // End of while loop  
      
      fclose ( fSource ); 
    }
  else
    printf (" Error: Unable to open file for reading !!! \n" );

} /* End of RemoveCharacter File */


/* Function Implementation */
void main (int argc,char* argv[])
{
  /* Initialize variables */
  sourceName = (char *) malloc(sizeof(char)*128);
  theChar = (char *) malloc(sizeof(char)*20);

  /* See if we have what we need */
  if (argc < 3)
  {
    printf("Character Remover %s\nCopyright (c) Sleepless Nights Software 1999\n",VERSION);
    printf("\nUsage: RemChar infile character\n");
    printf("       infile    -> filename of source file\n");
    printf("       character -> Character to be removed\n\n");
    return;
  }

  /* Get the command line arguments */
  strcpy(sourceName,argv[1]);
  strcpy(theChar,argv[2]);

  RemoveCharacter();
}

