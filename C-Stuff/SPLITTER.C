/****************************************************************************
 *  File: Splitter.c
 *
 *  Purpose: This program reads in the first splitSize characters from a file
 *           and writes them out to the result file.
 *
 *  Developed by:  Rob Broadhead
 *
 *  Modification Log:
 *     11-13-1998: Created File
 *     11-23-1998: Added tail functionality
 *
 ****************************************************************************/

/* Include files */
/* #include <condefs.h> */
#include <stdio.h>
#include <string.h>
#include <stdlib.h>

//---------------------------------------------------------------------------

/* Constant values */
#define VERSION "1.1"

/* Declare pointers for in and out files */
FILE  *fSource ;
FILE  *fTarget;
char *direction;
char *sourceName;
char *targetName;


/* Prototypes */
void WyerParseFile (long);

/* Function Implementation */
void main (int argc,char* argv[])
{
  /* Declare variables to be used */
  long splitSize;

  /* Initialize variables */
  sourceName = (char *) malloc(sizeof(char)*128);
  targetName = (char *) malloc(sizeof(char)*128);
  direction = (char *) malloc(sizeof(char)*4);
  strcpy(targetName,"result.dmp");
  splitSize = 24000;

  /* See if we have what we need */
  if (argc < 3)
  {
    printf("Splitter Version %s\nCopyright (c) Wyer Creative Communications 1998\n",VERSION);
    printf("\nUsage: splitter dirflag infile [outfile] [size]\n");
    printf("       dirflag --> H to peel front of file T to peel tail\n");
    printf("       infile  --> filename of source file\n");
    printf("       outfile --> filename of target file [default:result.dmp]\n");
    printf("       size    --> number of kbytes to place into target [default:24000]\n\n");
    return;
  }

  /* Get the command line arguments */
  strcpy(direction,argv[1]);
  strcpy(sourceName,argv[2]);
  if (argc > 3) {
    strcpy(targetName,argv[3]);

    if (argc > 4) {
      splitSize=atol(argv[4]);
    }
  }

  // Convert splitSize to k
  splitSize=(long) (splitSize * 1024);

  printf("\nSplitting with following parms: Src:%s  Dest:%s  Flag:%s  Size:%dK\n",sourceName,targetName,direction,splitSize/1024);
  WyerParseFile(splitSize);

  return;
}


void WyerParseFile (long theSize)
{
  long count,curPos;
  void *curChar;

  curPos=0;
  if (( fSource = fopen ( sourceName,"rbt" ) ) != NULL ) {
    if ( ( fTarget = fopen ( targetName, "wb" ) ) != NULL )
         {
        /* Start the file pointer at the beginning */
        rewind ( fSource );
        
        /* Place the file pointer at the end */
        fseek(fSource,0L,SEEK_END);
        curPos=ftell(fSource);

        /* Start the file pointer at the beginning */
        rewind ( fSource );

        /* If we want the end then we need to move the file pointer */
        /* to the proper starting point                             */
        if (!strcmp(direction,"T")) {
          if (curPos > theSize) {
            for(count=0;count< (curPos - theSize);count++) {
              fread(&curChar,sizeof(char),1,fSource);
            }
          }
        }
        
        
        /* Pull characters in until we get end-of-file */
        for (count=0;count < theSize;count++)
          {
            fread(&curChar,sizeof(char),1,fSource);
            if (feof(fSource)) {
              count = theSize;
            }
            fwrite(&curChar,sizeof(char),1,fTarget);
          }

        /* Clean up the file handles */
           fclose ( fTarget );
           fclose ( fSource );
         }
      else
      {
        printf ( "Error: Couldn't create/open target file:%s \n",targetName );
        }
    }
  else
    printf (" Error: Unable to open file for reading !!! [%s] \n",sourceName );
} /* End of WyerParseFile(long) */

