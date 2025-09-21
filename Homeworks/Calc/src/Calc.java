import java.util.Scanner;

public class Calc {

    // check if token is an integer (supports negatives like -42)
    private static boolean isInteger(String s) {
        if (s == null || s.isEmpty()) return false;
        int i = (s.charAt(0) == '-') ? 1 : 0;
        if (i == 1 && s.length() == 1) return false; // just "-" is not a number
        for (; i < s.length(); i++) {
            if (!Character.isDigit(s.charAt(i))) return false;
        }
        return true;
    }

    // print an error to STDERR with required "# "
    private static void err(String msg) {
        System.err.println("# " + msg);
    }

    // do a binary op using top two stack values
    // pop order: b = top, a = next; compute a (op) b
    private static void doOp(Stack<Integer> st, String op) {
        // need at least 2 values; if not, leave stack unchanged
        if (st.isEmpty()) {
            err("not enough operands");
            return;
        }
        int b = st.pop(); // right
        if (st.isEmpty()) {
            // put b back so stack stays the same
            st.push(b);
            err("not enough operands");
            return;
        }
        int a = st.pop(); // left

        switch (op) {
            case "+":
                st.push(a + b);
                break;

            case "-":
                st.push(a - b);
                break;

            case "*":
                st.push(a * b);
                break;

            case "/":
                if (b == 0) {
                    // error; restore original stack order (a then b)
                    st.push(a);
                    st.push(b);
                    err("division by zero");
                } else {
                    st.push(a / b); // integer division
                }
                break;

            case "%":
                if (b == 0) {
                    // error; restore original stack order (a then b)
                    st.push(a);
                    st.push(b);
                    err("division by zero");
                } else {
                    st.push(a % b);
                }
                break;

            default:
                // unknown operator; restore a and b since we can't proceed
                st.push(a);
                st.push(b);
                err("unknown operator: " + op);
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Stack<Integer> st = new LinkedStack<>();

        System.out.println("Enter RPN tokens (numbers, + - * / %, ? ^), end with !:");

        while (in.hasNext()) {
            String tok = in.next();

            if (tok.equals("!")) {
                return; // quit program
            } else if (isInteger(tok)) {
                // push valid integer
                st.push(Integer.parseInt(tok));

            } else if (tok.equals("+") || tok.equals("-") || tok.equals("*")
                    || tok.equals("/") || tok.equals("%")) {
                // do math with top two values
                doOp(st, tok);

            } else if (tok.equals("?")) {
                // print whole stack to STDOUT
                System.out.println(st.toString());

            } else if (tok.equals("^")) {
                // print and remove top; error if empty
                if (st.isEmpty()) {
                    err("stack is empty");
                } else {
                    System.out.println(st.pop());
                }

            } else {
                // bad/unknown token; keep going
                err("bad token: " + tok);
            }
        }
    }
}
