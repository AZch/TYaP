public class Triad {
    String proc = "";
    String operand1 = "";
    String operand2 = "";

    public Triad( String pr, String op1, String op2) {
        this.operand1 = op1;
        this.operand2 = op2;
        this.proc = pr;
    }

    public boolean isOnleOperand2() {
        return !proc.equals("") && !operand1.equals("") && operand2.equals("");
    }
}
