public class Equ extends Relation {
    public Expression left;
    public Expression right;

    public Equ(Expression left, Expression right){
        super(left, right);
    }
}
