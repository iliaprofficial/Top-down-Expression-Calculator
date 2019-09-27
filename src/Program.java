public class Program {
    public static void main(String[] args) {
        String input = "1+(26-98)*15+777"; // input expression
        Parser parser = new Parser(input);
        Expression root = parser.parse(); // parse the expression
        System.out.println(calculate(root)); // print the answer
    }

    private static int calculate(Expression node) {
        int result = 0;
        if (node instanceof Primary) { // if it is a number return it
            Primary nodeN = (Primary) node;
            result = nodeN.value;
        } else if (node instanceof Sum) // if it is a sum, call recursion to calculate it
            result = calculate(node.left) + calculate(node.right);
        else if (node instanceof Sub)
            result = calculate(node.left) - calculate(node.right);
        else if (node instanceof Factor)
            result = calculate(node.left) * calculate(node.right);
        // relation operations
        else if (node instanceof Greater && calculate(node.left) > calculate(node.right))
            result = 1;
        else if (node instanceof Less && calculate(node.left) < calculate(node.right))
            result = 1;
        else if (node instanceof Equ && calculate(node.left) == calculate(node.right))
            result = 1;
        return result;
    }
}