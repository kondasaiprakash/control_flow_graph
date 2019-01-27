import java.util.HashSet;
import java.util.Set;
import java.util.*;

public class MethodNode
{

    String name;
    String method_code;
    String input;
    ArrayList<String> input_variables;
    Set<String> variables;
    public static ArrayList<MethodNode> method_nodes;

    public MethodNode(String name, String method_code,String input)
    {
        this.name = name;
        // this.variables = var;
        this.method_code = method_code;
        this.input = input;
        variables = new HashSet<String>();
        input_variables = new ArrayList<String>();
    }
    

}