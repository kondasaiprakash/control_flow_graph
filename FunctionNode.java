import java.util.*;

public class FunctionNode
{
    public static List<FunctionNode> function_nodes = new ArrayList<FunctionNode>();
    public String function_name;
    public String function_code;
    public Node graph;
    public List<FunctionNode> parents;
    public List<FunctionNode> childs;
    int check = 0;
    int total_nodes_counter = 0;
    public FunctionNode(String name,String function_code)
    {
        this.function_name = name;
        this.function_code = function_code;
        // this.graph = graph;
        function_nodes.add(this);
        parents = new ArrayList<FunctionNode>();
        childs = new ArrayList<FunctionNode>();
    }

    public static FunctionNode getter(String name)
    {
        int check = 0;
        for(int i = 0; i < function_nodes.size(); i++)
        {
            if(function_nodes.get(i).function_name.equals(name))
            {
                check = 1;
                return function_nodes.get(i); 
            }
        }
        // try
        // {
        if(check != 1)
        {
            System.out.println("function is not existing ");
            System.exit(1);
        }
        return null;

    }

    public static void functions_print()
    {
        System.out.println("number of functions is : " + function_nodes.size());
        for(int i = 0; i < function_nodes.size(); i++)
        {
            System.out.println(function_nodes.get(i).function_code);
        }
    }


    public void linear_independent_paths(Node node,String path)
    {
    // if(node.check == 0)
    // {
        System.out.println(node.nodeid);
        path = path + "->" +node.nodeid;
        node.check = 1;
        for(int i = 0; i < node.child_nodes.size(); i++)
        {
            if(node.child_nodes.get(i).check == 0)
            {
                linear_independent_paths(node.child_nodes.get(i),path);
            }
            if(i == node.child_nodes.size()-1)
            {

            }

        }
    // }

    }

    public void normal_display(Node node)
    {
        System.out.println(node.nodeid);
        System.out.println(node.description);
        this.manual_test_display(node);
        node.check = 1;
        int var = 0;
        for(int i = 0; i < node.child_nodes.size(); i++)
        {
            if(node.child_nodes.get(i).check == var)
            {
                normal_display(node.child_nodes.get(i));
            }
            else if(i == node.child_nodes.size()-1)
            {
                System.out.println(node.child_nodes.get(i).description + " in else condition");
            }

            // if(i == node.child_nodes.size()-1)
            // {
            //     var = var + 1;
            //     i = 0;
            // }
        }
    }


    public void manual_test_display(Node node)
    {
        total_nodes_counter = total_nodes_counter + 1;
        System.out.println(total_nodes_counter +" " + Node.total_nodes );
        // System.out.println("displaying childdren of node" + node.nodeid);
        for(int i = 0; i < node.child_nodes.size(); i++)
        {
            System.out.println(node.child_nodes.get(i).description);
        }
        // System.out.println("ended displaying the children");
    }

    public void matrix_display()
    {
        for(int i = 0; i < Node.all_nodes.size(); i++)
        {
            System.out.println("------------------------"+ Node.all_nodes.get(i).nodeid +"----------------------------");
            // System.out.println(Node.all_nodes.get(i).nodeid);
            System.out.println(Node.all_nodes.get(i).description);
            // System.out.println("-----------child nodes -----------------");
            String dis = "child_nodes : ";
            for(int j = 0; j < Node.all_nodes.get(i).child_nodes.size(); j++)
            {
                dis = dis + "->" + Node.all_nodes.get(i).child_nodes.get(j).nodeid;
            }
            System.out.println(dis);

        }
        System.out.println("======================= end ==============================");
    }

    public void matrix()
    {
        int[][] mat = new int[Node.all_nodes.size()+1][Node.all_nodes.size()+1];
        for(int i = 0; i < Node.all_nodes.size(); i++)
        {
            for(int j = 0; j < Node.all_nodes.get(i).child_nodes.size(); j++)
            {
                int k = Node.all_nodes.get(i).child_nodes.get(j).nodeid;
                mat[i][k] = 1;
            }
        }

        for(int i = 0; i < mat.length; i++)
        {
            for(int j = 0; j < mat[0].length; j++)
            {
                System.out.print(mat[i][j] + " ");

            }
            System.out.println();
        }
    }



}