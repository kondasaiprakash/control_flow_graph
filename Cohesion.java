import java.util.*;
import java.util.stream.Collectors;
import java.io.*;
import java.util.Set;
import java.util.Collections;

public class Cohesion
{
    public int P ;
    public int Q ;

    public String code;
    public static String class_name;
    public String modified_code;
    public static String global_var;
    public static ArrayList<String> gvars = new ArrayList<String>();
    ArrayList<MethodNode> methods;

    Cohesion(String filename) throws IOException
    {
        InputReader one = new InputReader();
        code = one.read_file(filename).lines().collect(Collectors.joining("\n"));
        this.P = 0;
        this.Q = 0;
    
    }

    public void method_extractor()
    {
        FunctionExtractor fnex = new FunctionExtractor();
        this.modified_code = fnex.modified_text(this.code, 1);
        MethodNode.method_nodes = fnex.extract_methods(this.modified_code);

        for(int i = 0; i < MethodNode.method_nodes.size(); i++)
        {
            for(int j = i+1; j < MethodNode.method_nodes.size(); j++)
            {
                if(i != j)
                {
                    if(Collections.disjoint(MethodNode.method_nodes.get(i).variables, MethodNode.method_nodes.get(j).variables))
                    {
                        P = P + 1;
                    }
                    else
                    {
                        Q = Q + 1;
                    }
                }

            }
        }
       
        // for(int i = 0; i < gvars.size(); i++)
        // {
        //     int check = 0;
        //     for(int j = 0; j < MethodNode.method_nodes.size(); j++)
        //     {
        //         List<String> varString = new ArrayList<String>();
        //         varString.addAll(MethodNode.method_nodes.get(j).variables);
        //         for(int k = 0; k < varString.size(); k++)
        //         {
        //             if(gvars.get(i).contentEquals(varString.get(k)))
        //             {
        //                 check = check + 1;
        //                 break;
        //             }
        //         }
        //     }
        //     if(check > 1)
        //     {
        //         Q = Q + 1;
        //     }
        //     else
        //     {
        //         P = P  + 1;
        //     }
        // }
        // if()
        if((P-Q) < 0)
        {
            System.out.println(0);
        }
        else
        {

            System.out.println("the Lcom value is " + (P-Q));
        }


    }

    public static void main(String[] args) throws Exception
    {
        Cohesion co = new Cohesion("Calculator.java");
        co.method_extractor();
    }

}