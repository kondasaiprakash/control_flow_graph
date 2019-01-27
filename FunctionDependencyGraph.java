import java.util.ArrayList;
import java.util.*;

public class FunctionDependencyGraph
{
    public List<FunctionNode> root;

    public FunctionDependencyGraph()
    {
        this.root = new ArrayList<FunctionNode>();

    }

    public void graph()
    {
        ArrayList<FunctionNode> one = (ArrayList<FunctionNode>)FunctionNode.function_nodes;
        for(int i = 0; i < one.size(); i++)
        {
            if(one.get(i).parents.size() == 0)
            {
                root.add(one.get(i));
            }
        }

    }
    public void create_function_graph()
    {
        Queue<FunctionNode> q = new LinkedList<FunctionNode>();
        ControlFlow cfl = new ControlFlow();
        // System.out.println("in forward");
        for(int i = 0; i < root.size(); i++)
        {
            q.add(root.get(i));
            
        }
        // System.out.println("in forward");
        // try
        // {
        //     // System.out.println("in forward");
        //     System.out.println(root.size());
        //     if(root.size() == 0)
        //     {
        //         throw new NoIndependentNodes();

        //     }



        // }
        // catch(NoIndependentNodes exp)
        // {
        //     System.out.println(exp.getMessage());
        //     System.exit(1);
        // }
        int count = 0;
        while(q.size() != 0)
        {
            int verified = 0;
            FunctionNode x = q.remove();
            x.check = 1;
            // one.early_times_calculator(x);
            // cfl.graph_design(x);
            cfl.graph_design(x);
            // System.out.println(val);

            count++;
            for(int i = 0; i < x.childs.size(); i++)
            {
                FunctionNode child = x.childs.get(i);
                for(int k = 0; k < child.parents.size(); k++)
                {
                    FunctionNode parent = child.parents.get(k);
                    if(parent.check != 1)
                    {
                        verified = 0;
                        break;
                    }
                    verified = 1;
                }
                if(verified != 0)
                {
                    q.add(child);
                }
            }
        }
        // try
        // {
         
            if(count < FunctionNode.function_nodes.size())
            {
                System.out.println("this system as circular dependency in it");
                System.exit(1);
                // throw new CircularDependency();

            }
        // }
        // catch(CircularDependency exp)
        // {
        //     System.out.println(exp.getMessage());
        //     System.exit(1);
        // }
   

    }

    
    
}