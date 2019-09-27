public class Greater extends Relation {
    public Expression left;
    public Expression right;

    public Greater(Expression left, Expression right){
        super(left, right);
    }
}
