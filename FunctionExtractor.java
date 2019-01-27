import java.io.BufferedReader;
import java.util.List;
import java.util.regex.*;
import java.util.stream.Collectors;
import java.util.*;
import java.io.IOException;

public class FunctionExtractor
{
    

    public static List<BufferedReader> files;
    String function_pattern = "([\\w\\s]+)\\(([\\w\\s,]*)\\)[\\s]*\\{[\\$\\@\\s\\w;\\+\\=\\-\\(\\)\\\"\\'\\%\\/\\*,><!]*\\}";
    String pat_updated = "([\\w\\s]+\\([\\w\\s,]*\\)[\\s]*\\{([\\w\\s]+\\([\\w\\s,]*\\)[\\s]*\\{([\\s\\w;\\+\\=\\-\\(\\)\\\"\\'\\%,><!])*\\})*([\\s\\w;\\+\\=\\-\\(\\)\\\"\\'\\%,><!])*\\})";
    String function_name_extract = "([\\w\\s]+)\\([\\w\\s,]*\\)[\\s]*\\{";  
    String recursion_depth = "(\\{|\\})";
    String global_variables_pat = "([\\w\\s]*)\\{([\\w\\s]*[\\s\\w;\\+\\=\\-]*;)";
    String g_var_pat = "(([ ]*int )|([ ]*short )|([ ]*boolean )|([ ]*byte )|([ ]*char )|([ ]*long )|([ ]*float )|([ ]*double ))[\\s]*([a-zA-Z_@][\\w@_]*)";
    Pattern glob = Pattern.compile(global_variables_pat);
    Pattern g_var = Pattern.compile(g_var_pat);
    Pattern recursion = Pattern.compile(recursion_depth);
    Pattern pattern = Pattern.compile(function_pattern);
    Pattern pattern2 = Pattern.compile(function_name_extract);
    List<String> functions_list = new ArrayList<String>();
    public static List<String> function_names = new ArrayList<String>();

    
    public FunctionExtractor(ArrayList<BufferedReader> files)
    {
        this.files = files;
    }
    public FunctionExtractor()
    {

    }
    public Pattern recursionPattern(String file)
    {
        Matcher recur = recursion.matcher(file);
        int size = 0;
        // String prev = "";
        // String cur = "";
        Stack st = new Stack<String>();
        String pt = "([\\w\\s]+\\([\\w\\s,]*\\)[\\s]*\\{([\\s\\w;\\+\\=\\-\\(\\)\\\"\\'\\%,><!])*\\})*";
        String start = "([\\w\\s]+\\([\\w\\s,]*\\)[\\s]*\\{";
        String end = "((\\{)*[\\s\\w;\\+\\=\\-\\(\\)\\\"\\'\\%,><!](\\})*)*\\})*";
        String sent_start ="";
        String sent_end="";
        while(recur.find())
        {
            // System.out.println(recur.group());
            if(recur.group().contentEquals("{"))
            {
                st.push("{");
                // System.out.println(st.size());
                if(st.size() > size)
                {
                    size = st.size();
                }
            }
            else
            {
                if(st.size() > 0)
                {
                    st.pop();
                }
            }
            // if(size == 0)
            // {
            //     if(recur.group().equals('{'))
            //     {
            //         size = 1;
            //         prev = '{';
            //     }

            // }
            // else
            // {
            //     if(recur.group().equals('{') && prev.equals('{'))
            //     {
            //         size = size + 1;
            //         prev = '{';
            //         cur = "{";
            //     }
            //     else if(recur.group().equals("{") && )
                
            // }
        }
        System.out.println("this is size of recrrsion " + size);
        for(int i = 0; i < size; i++)
        {
            // System.out.println(i);
            sent_start = sent_start+start;
            sent_end = sent_end + end;
        }
        String final_pat = sent_start + sent_end;
        Pattern final_pattern = Pattern.compile(final_pat);
        return final_pattern;
    }

    public String modified_text(String txt,int val)
    {   
        Stack st = new Stack<String>();
        // Pattern pat = Pattern.compile("({|})");
        Matcher rec = recursion.matcher(txt);

        StringBuffer result = new StringBuffer();
        // Map<String, String> map = new HashMap<>();
        // map.put("{","$");
        // map.put("}","@");
        while(rec.find())
        {
            if(st.size() <= val)
            {
                if(rec.group().contentEquals("{"))
                {
                    st.push("{");
                    rec.appendReplacement(result, "{");
                }
                // else
                // {
                //     st.pop();
  
                // }
            }
            else
            {
                if(rec.group().contentEquals("{"))
                {
                    st.push("{");
                    rec.appendReplacement(result, "\\$");
                }
                else
                {
                    st.pop();
                    if(st.size() > val)
                    {
                        rec.appendReplacement(result, "\\@");
                    }
                    else
                    {
                        rec.appendReplacement(result, "}");
                    }
                }
            }
        }
        String resul = result.toString();
        // System.out.println(resul);
        return resul;
    }
    public ArrayList<MethodNode> extract_methods(String one)
    {

        System.out.println(one);
        Matcher g = glob.matcher(one);
        Matcher var = null;
        if(g.find())
        {
            var = g_var.matcher(g.group(2));
            Cohesion.global_var = g.group(2);
            Cohesion.class_name = g.group(1);
        }
        Matcher m = pattern.matcher(one);
        String mvu = "\\b(?:";
        while(var.find())
        {
            Cohesion.gvars.add(var.group(10));
            
        }
        String middle = String.join("|", Cohesion.gvars);
        mvu = mvu + middle + ")\\b";
        System.out.println(mvu);
        Pattern gvm = Pattern.compile(mvu);
        

        ArrayList<MethodNode> methods = new ArrayList<MethodNode>();
        MethodNode method;
        Matcher chec;
        while (m.find()) {
            System.out.println(m.group(1));
            String elements[] = m.group(1).split(" ");
            String method_name = elements[elements.length -1];
            String input = m.group(2);
            String txt = m.group().replaceAll("\\$", "{");
            txt = txt.replaceAll("\\@", "}");
            chec = g_var.matcher(input);
            
            // System.out.println()
            method = new MethodNode(method_name,txt,input);
            while(chec.find())
            {
                method.input_variables.add(chec.group(10));

            }
            Matcher ma = gvm.matcher(txt);
            while(ma.find())
            {
                method.variables.add(ma.group());
            }
            methods.add(method);

        }
        return methods;

    }
    public void extract_functions()
    {

        for(int i = 0; i < files.size(); i++)
        {
            String total_file = files.get(i).lines().collect(Collectors.joining("\n"));
            // System.out.println(total_file);
            // Matcher recur = recursion.matcher(total_file);
            String file = modified_text(total_file,0);
            Matcher m = pattern.matcher(file);
            // Pattern final_pat = recursionPattern(total_file);
            // Matcher m = final_pat.matcher(total_file);
            // System.out.println("printing functions");
            while (m.find()) {
                // System.out.println(m.group());
                String txt = m.group().replaceAll("\\$", "{");
                txt = txt.replaceAll("\\@", "}");
                // System.out.println()
                functions_list.add(txt);
            }
        }
        for(int i = 0; i < functions_list.size(); i++)
        {
            // System.out.println(functions_list.get(i));
            Matcher m = pattern2.matcher(functions_list.get(i));
            // if(!m.find())
            // {
            //     System.out.println("could not recognize");
            // }
            if(m.find())
            {
                String[] one = m.group(1).split(" ");
                // System.out.println(one[one.length-1]);
                function_names.add(one[one.length-1]);
                FunctionNode fNode = new FunctionNode(one[one.length-1],functions_list.get(i));
            }
        }
    }

    public void link_dependencies()
    {
        ArrayList<FunctionNode> one = (ArrayList<FunctionNode>)FunctionNode.function_nodes;
        String pat = "([\\w\\d]+)[\\s]*\\([\\w\\s\\d,]*\\)[\\s]*;";
        
        Pattern dep = Pattern.compile(pat);
        // for(int i = 0; i < function_names.size(); i++)
        // {
        //     System.out.println(function_names.get(i));
        // }
        for(int i = 0; i < one.size(); i++)
        {
            Matcher m = dep.matcher(one.get(i).function_code);
            while(m.find())
            {
                String name = m.group(1);
                for(int j = 0; j < one.size(); j++)
                {
                    
                    if(name.equals(one.get(j).function_name) && one.get(j).check == 0 && i!=j)
                    {
                        one.get(i).parents.add(one.get(j));
                        one.get(j).childs.add(one.get(i));
                        one.get(j).check = 1;
                        // System.out.println(m.group(1));

                    }
            
                }
            }
            check_clean();

        }
    }
    public static void check_clean()
    {
        ArrayList<FunctionNode> one = (ArrayList<FunctionNode>)FunctionNode.function_nodes;
        for(int i = 0; i < one.size(); i++)
        {
            one.get(i).check = 0;
        }
    }
    public static void main(String[] args) throws IOException
    {
        InputReader ir = new InputReader();
        
        ArrayList<BufferedReader> rdr = new ArrayList<BufferedReader>();
        rdr.add(ir.read_file("verify.c"));
        FunctionExtractor one = new FunctionExtractor(rdr);
        // System.out.println(one.function_pattern);
        one.extract_functions();
        one.link_dependencies();
        FunctionDependencyGraph graph = new FunctionDependencyGraph();
        graph.graph();
        graph.create_function_graph();

        FunctionNode main_function = FunctionNode.getter("main");
        if(main_function.graph != null) 
        {
            // System.out.println("successful");
        }
        
        // main_function.normal_display(main_function.graph);
        System.out.println("parent-child format");
        main_function.matrix_display();
        System.out.println("matrix format");
        main_function.matrix();
        // FunctionNode.functions_print();
    }
    

    


}