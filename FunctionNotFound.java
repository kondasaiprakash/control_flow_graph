public class FunctionNotFound extends Exception
{
    public FunctionNotFound(String msg)
    {
        super(msg);
    }
    public FunctionNotFound()
    {
        super("called a function that is not existing");
    }
}