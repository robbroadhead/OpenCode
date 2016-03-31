/*  PathFind.c
	Written by: Rob Broadhead
	Created: 3/14/1997
	Finds out whether or not a file is in the current path. This is done by doing
    a ls command with the file name as a parameter for each directory in the path.
	This is the unix version written for Linux.

        09/04/1997: dumped errors to /dev/null to give cleaner display.
        08/27/2000: Increased the size of file names and path lengths.
        10/22/2002: Cleaned up some of the comments and did the equivalent of
  					a pretty print.
	$Id: PATHFIND.C,v 1.1 2007/12/27 14:47:34 robbroadhead Exp $
*/

#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <ctype.h>

main(int argc, char *argv[])
{
  char *theFile,*thePath,*curPath,*tempStr;
  int found=0,filesize;
  FILE *stream;

  /* Make sure they gave an argument */
  if (argc == 1)
  {
    printf("\nYou must specify a file to search for.\nFormat: pathfind filename\nfilename: the file you are searching for (e.g. pkzip.exe)");
    return -1;
  }

  /* Initialize stuff */
  thePath = (char *) malloc(sizeof(char)*8000);
  curPath = (char *) malloc(sizeof(char)*8000);
  tempStr = (char *) malloc(sizeof(char)*8000);
  theFile = (char *) malloc(sizeof(char)*120);

  /* Get the file to search for */
  strcpy(theFile,argv[1]);
  printf("Searching for: %s\n",theFile);

  /* Get the path */
  system("set |grep ^PATH > /tmp/path.txt");
  stream = fopen("/tmp/path.txt","r");
  while (fscanf(stream,"%s",tempStr) != EOF) {
    strcat(thePath,tempStr);
  }

  /* Close the file */
  fclose(stream);

  /* Parse the path */
  curPath = strtok(thePath,"=");
  curPath = strtok(NULL,":");

  /* Go through each item in the path and search for the file. */
  while ((curPath != NULL) && (found !=1))
  {
    /* Build the command line string and remove any trailing slashes. */
    sprintf(tempStr,"ls %s/%s 1> /tmp/path.txt 2> /dev/null",curPath,theFile);
    if (strstr(tempStr,"//")!= NULL)
      sprintf(tempStr,"ls %s%s 1> /tmp/path.txt 2> /dev/null ",curPath,theFile);

	/* Execute the command */
	system(tempStr);

	/* Grab the result file for this search */
    stream = fopen("/tmp/path.txt","r");
    fscanf(stream,"%s",thePath);
    fclose(stream);

    /* See if we found the file (line > \n\eof) */
    if (strstr(thePath,theFile)!= NULL)
	  found = 1;
    else
	  curPath = strtok(NULL,":");
    }

  /* Communicate the results */
  if (found==1)
    {
      printf("Found it!  In %s\n",curPath);
      curPath = NULL;
    }
  else
    printf("Did not find it!\n");

  /* Clean up variables */
  free(thePath);
  free(curPath);
  free(tempStr);
  free(theFile);

  return 0;
}  // The main program

