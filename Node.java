import java.util.*;

public class Node
{
    int nodeid;
    int stack_id;
    String name;
    String description;
    // List<Integer> line_nos;
    List<Node> parent_nodes;
    List<Node> child_nodes;
    int check = 0;
    Node supportNode;
    static int total_nodes = 0;
    public static List<Node> all_nodes = new ArrayList<Node>();

    public Node(String function_name, String description,int stack_id)
    {
        this.nodeid = total_nodes + 1;
        this.name = function_name;
        this.description = description;
        this.stack_id = stack_id;
        // this.line_nos = line_nos;
        this.check = 0;
        parent_nodes = new ArrayList<Node>();
        child_nodes = new ArrayList<Node>();
        total_nodes = total_nodes + 1;
        
    }
    public void attach_parent(Node node)
    {
        parent_nodes.add(node);

    }
    public void attach_child(Node node)
    {
        child_nodes.add(node);
    }


    


}