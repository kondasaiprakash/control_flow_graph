#include<stdio.h>
#include<stdlib.h>
int max(int num1, int num2) {

   int result;
   int i ;
 
   if (num1 > num2)
    {
      result = num1;
      for(i = 0; i < num1; i++)
      {
          print(i);
      }
    }
   else
   {
      result = num2;
   }
   return result; 
}

void main()
{
    while(1)
    {
    printf("maximum of two numbers");
    int c = max(10,20);
    printf("%d",c);
    }

}