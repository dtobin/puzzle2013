#include <stdio.h>

static unsigned char primeTbl[100000];

int main (void) {
  int i, j;

  for (i = 0; i < sizeof(primeTbl); i++)
    primeTbl[i] = 1;

  primeTbl[0] = 0;
  primeTbl[1] = 0;
  for (i = 2; i < sizeof(primeTbl); i++)
    if (primeTbl[i])
      for (j = i + i; j < sizeof(primeTbl); j += i)
	primeTbl[j] = 0;

  printf ("static unsigned char primeTbl[] = {");
  for (i = 0; i < sizeof(primeTbl); i++) {
    if ((i % 50) == 0) {
      printf ("\n   ");
    }
    printf ("%d,", primeTbl[i]);
  }
  printf ("\n};\n");
  printf ("#define isPrime(x) "
	  "((x < sizeof(primeTbl) ? primeTbl[x] : isPrimeFn(x))\n");

  return 0;
}
