import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.*;
import java.util.*;
import java.util.ArrayList.*;
import java.util.Stack;
import java.util.Scanner;
public class ControlFlow

{
    public String cfg_modify(String txt)
    {
        Stack st = new Stack<String>();
        String recursion_depth = "(\\{|\\})";
        Pattern recursion = Pattern.compile(recursion_depth);
        Matcher m = recursion.matcher(txt);
        StringBuffer result = new StringBuffer();
        int i = 0;
        int popped = 0;
        while(m.find())
        {
            if(m.group().contentEquals("{"))
            {
                st.push("{");
                if(popped == 0)
                {
                    i = i + 1;
                }
                if(popped == 1)
                {
                    popped = 0;
                }
                m.appendReplacement(result, "\\$"+i);
                
                

                // prev = 1;

            }
            else
            {
                st.pop();
                
                if(popped != 0)
                {
                    i = i - 1;
                    
                }
                popped = 1;
                m.appendReplacement(result, "\\$"+i);
            }
        }
        String resu = result.toString();
        return resu;
    }

    public void graph_design(FunctionNode node)

    {
        // public static List<String> c_keywords = new ArrayList<String>();
        // ArrayList<String> list = new ArrayList(Arrays.asList("while", "for", "do","if","else","switch","break","continue"));
        
        // String pat = "([\\s\\w]+\\([\\s\\w,]*\\)[\\s]*\\$(\\d)+)|(([\\w\\s]+)\\([\\w\\s]*\\);)";
        // String pat2 = "(([^\\d\\$][\\s\\w]+)\\([\\s\\w,]*\\)[\\s]*\\$(\\d)+)|(([\\w\\s]+)\\([\\w\\s]*\\)\\;)";
        // String pat3 = "(([^\\d\\$][\\s\\w]+)\\([\\s\\w,\\>\\<\\=\\!]*\\)[\\s]*\\$(\\d)+)|(([\\w\\s]+)\\([\\w\\s]*\\)\\;)|(([\\w\\s])\\$(\\d)+[\\w\\s\\=\\;]*\\$(\\d)+)";
        // String final_pat = "(([^\\d\\$]([\\w]+)[\\s]*)\\([\\s\\w,\\>\\<\\=\\!\\*\\-\\+\\]*\\)[\\s]*\\$(\\d)+)|(([^\\d\\$]([\\w]+)[\\s]*)\\([\\w\\s,\\\"\\%]*\\)\\;)|(([^\\d\\$]([\\w]+)[\\s]*)\\$(\\d)+)|([^\\d][\\w\\s\\d=]+\\;)|(\\$(\\d)+)";
        String final_pat = "(([^\\d\\$]([\\w]+)[\\s]*)\\([\\s\\w,;\\>\\<\\=\\!\\*\\-\\+]*\\)[\\s]*\\$(\\d)+)|(([^\\d\\$]([\\w]+)[\\s]*)\\([\\w\\s,\\\"\\%]*\\)\\;)|(([^\\d\\$]([\\w]+)[\\s]*)\\$(\\d)+)|([^\\d\\$][\\w\\s\\d=]+\\;)|(\\$(\\d)+)";
        // String pat1 = "(\\$(\\d)+)";
        // String pat2 = "([^\\d][\\w\\s\\d=]+\\;)";
        // String pat3 = "(([^\\d\\$]([\\w]+)[\\s]*)\\$(\\d)+)";
        // String pat4 = "(([^\\d\\$]([\\w]+)[\\s]*)\\([\\s\\w,\\>\\<\\=\\!\\*\\-\\+]*\\)[\\s]*\\$(\\d)+)";
        // String pat5 = "(([^\\d\\$]([\\w]+)[\\s]*)\\([\\w\\s,\\\"\\%]*\\)\\;)";
        // String pat6 = pat1 +"|" + pat2;
        Stack<Node> st = new Stack<Node>();
        Scanner scan = new Scanner(System.in);
        Pattern pattern = Pattern.compile(final_pat);
        // Pattern ppat1 = Pattern.compile(pat4);
        // // System.out.println("pattern successful");
        // int p1 = scan.nextInt();
        // if(p1 == 0)
        // {
        //     System.exit(0);
        // }
        // Pattern ppat2 = Pattern.compile(pat5);
        // System.out.println("pattern successful");
        // int p2 = scan.nextInt();
        // if(p2 == 0)
        // {
        //     System.exit(0);
        // }
        // Pattern ppat3 = Pattern.compile(pat3);
        // System.out.println("pattern successful");
        // int p3 = scan.nextInt();
        // if(p3 == 0)
        // {
        //     System.exit(0);
        // }
        // Pattern pattern = Pattern.compile(pat1+"|"+pat2+"|"+pat3+"|"+pat4+"|"+pat5);
        String test = cfg_modify(node.function_code);
        // System.out.println(test);
        Node function_graph = null;
        Matcher m = pattern.matcher(test);
        // Matcher m = ppat1.matcher(test);
        // Sta
        List<String> function_names = FunctionExtractor.function_names;
        while(m.find())
        {
            // System.out.println("0: " +m.group(0)+" \n 1 : " +m.group(1)+" \n 2: "+m.group(2) +"\n 3 : "+m.group(3) +" \n 4 : "+ m.group(4)+"\n 5 : " +m.group(5)+" \n 6 : "+m.group(6)+ " \n 7 :"+m.group(7));
            if(m.group(3)!=null)
            {
                String name = m.group(3);
                int id = Integer.parseInt(m.group(4));
                String desc = m.group(1);
                Node one = new Node(name,desc,id);
                Node.all_nodes.add(one);
                int c = 0;
                for(int i = 0; i < function_names.size(); i++)
                {
                    if(name.contentEquals(function_names.get(i)))
                    {
                        st.push(one);
                        // one.supportNode = one;
                        one.supportNode = one;
                        function_graph = one;
                        c = 1;
                    }
                }
                if(c!=1)
                {
                    // if(name.contentEquals("if"))
                    // {
                        Node x = st.peek();
                        x.supportNode.child_nodes.add(one);
                        st.push(one);
                        x.supportNode.supportNode = one;
                        one.supportNode = one;
                        // x.supporNode = one;

                    // }
                }
                

            }
            // else if(m.group())
            else if(m.group(7)!= null)
            {
                int check = 0;
                for(int i = 0; i < function_names.size(); i++)
                {
                    if(m.group(7).contentEquals(function_names.get(i)))
                    {
                        Node x = st.peek();
                        Node s = FunctionNode.getter(function_names.get(i)).graph;
                        x.supportNode.child_nodes.add(s);
                        x.supportNode = s.supportNode;
                        check = 1;
                    }
                }
                if(check != 1)
                {
                    if(!m.group(7).contentEquals("while"))
                    {
                        String name = m.group(7);
                        int id = 0;
                        String desc = m.group(5);
                        Node one = new Node(name,desc,id);
                        Node.all_nodes.add(one);
                        Node x = st.peek();
                        x.supportNode.child_nodes.add(one);
                        // x.supportNode.child_nodes.add(one);
                        x.supportNode = one;   
                    }
                }
            }
            else if(m.group(10)!= null)
            {
                String name = m.group(10);
                int id = Integer.parseInt(m.group(11));
                String desc = m.group(8);
                Node one = new Node(name, desc,id);
                Node.all_nodes.add(one);
                Node x = st.peek();
                st.push(one);
                if(name.contentEquals("else"))
                {
                    // x.supportNode.child_nodes.add(one);
                    // x.supportNode.supportNode = one;
                    one.supportNode = one;
                    x.supportNode.supportNode.child_nodes.add(one);
                }
                else
                {
                    x.supportNode.child_nodes.add(one);
                    x.supportNode.supportNode = one;
                }
            }

            else if(m.group(12)!= null)
            {
                String name = m.group(12);
                int id = 0;
                String desc = m.group(12);
                // System.out.println(name);
                // System.out.println(desc);
                Node x = st.peek();
                Node one = new Node(name, desc, id);
                Node.all_nodes.add(one);
                x.supportNode.child_nodes.add(one);
                x.supportNode = one;
            }
            else if(m.group(13)!= null)
            {
                String name = m.group(13);
                int id = Integer.parseInt(m.group(14));
                String desc = "end"+m.group(14);
                Node one = new Node(name,desc,id);
                Node.all_nodes.add(one);
                Node x = st.peek();
                if(x.name.contentEquals("for") || x.name.contentEquals("while") )
                {
                    x.supportNode.child_nodes.add(one);
                    one.child_nodes.add(x);
                    x.supportNode = x;
                    Node y = st.pop();
                    Node z = st.peek();
                    z.supportNode = y.supportNode;
                } 
                else if(x.name.contentEquals("do"))
                {
                    one.description = "do ending";
                    x.supportNode.child_nodes.add(one);
                    one.child_nodes.add(x);
                    x.supportNode = one;
                    Node y = st.pop();
                    Node z = st.peek();
                    z.supportNode = y.supportNode;

                }
                else if(x.name.contentEquals("if"))
                {
                    one.name = "if-root";
                    one.description = "if-root";
                    Node y = st.pop();
                    one.child_nodes.add(y);
                    Node end = new Node("if-end-root","if-end-root",0);
                    Node.all_nodes.add(end);
                    end.supportNode = one;
                    y.supportNode.child_nodes.add(end);
                    one.supportNode = end;
                    Node z = st.peek();
                    if(z.child_nodes.remove(y))
                    {
                        System.out.println("removed node");
                    }
                    z.supportNode.child_nodes.add(one);
                    z.supportNode = one.supportNode;


                }
                else if(x.name.contentEquals("else"))
                {
                    Node y = st.pop();
                    Node z = st.peek();
                    // z.supportNode.supportNode.child_nodes.add(y);
                    y.supportNode.child_nodes.add(z.supportNode);

                }
                else if(x.name.contentEquals(function_graph.name))
                {
                    one.name = "end"+function_graph.name;
                    // int id = 0;
                    one.description = "function finish"; 
                    // Node one = new Node(name,desc,id);
                    Node y = st.pop();
                    y.supportNode.child_nodes.add(one);

                    
                }

            }

        }
        node.graph = function_graph;

        
    }
    
}