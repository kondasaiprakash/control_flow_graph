import java.io.*;
import java.util.*;
public class cfg
{
public static void main(String[] args)throws Exception
{

ArrayList<String> a = new ArrayList<String>();

File file = new File("small.c");

BufferedReader br = new BufferedReader(new FileReader(file));

String st;
while ((st = br.readLine()) != null)
            a.add(st);

int adj[][]=new int[a.size()][a.size()];

int i=0;
int flag=-1;
while(i<a.size())
   {

      if(a.get(i).endsWith(";"))
      {
        if(i==a.size()-1)
            break;
        adj[i][i+1]=1;

      }
      else if(a.get(i).startsWith("for"))
           {
               flag=i;
               while(!(a.get(i).endsWith("}")))
               {
                   adj[i][i+1]=1;
                   i++;
               }
               adj[i][flag]=1;

               if(i==a.size()-1)
                break;
               adj[flag][i+1]=1;


           }
      else if(a.get(i).startsWith("while"))
           {
               flag=i;
               while(!(a.get(i).endsWith("}")))
               {
                   adj[i][i+1]=1;
                   i++;
               }
               adj[i][flag]=1;
               if(i==a.size()-1)
                break;
               adj[flag][i+1]=1;
           }
           i++;


   }

   for(int k=0;k<a.size();k++)
   {
       for(int j=0;j<a.size();j++)
       {
         System.out.print(adj[k][j]);
       }
       System.out.println("");
   }
}
}