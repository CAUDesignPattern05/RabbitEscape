package rabbitescape.engine;

public interface Observable
{
    public void addBehaviour(Behaviour behaviour);
    public void deleteBehaviour(Behaviour behaviour);
//    public void step( World world );
}
