import java.io.PrintStream;

public class CompilerException extends RuntimeException {
    private CompilerError type;

    public CompilerException(CompilerError type) {
        super();
        this.type = type;
    }

    public void printDescription(PrintStream out) {
        switch (type) {
            case VAR_ALREADY_DECLARED:
                out.println("[COMPILATION ERROR] Trying to declare an already declared var.");
                break;
            case CALL_TO_UNDEFINED_VAR:
                out.println("[COMPILATION ERROR] Trying to reference an undefined var.");
                break;

            case TAB_ALREADY_DECLARED:
                out.println("[COMPILATION ERROR] Trying to declare an already declared array.");
                break;
            case CALL_TO_UNDEFINED_TAB:
                out.println("[COMPILATION ERROR] Trying to reference an undefined array.");
                break;

            case FUNCTION_ALREADY_DECLARED:
                out.println("[COMPILATION ERROR] Trying to declare an already declared function.");
                break;
            case CALL_TO_UNDEFINED_FUNCTION:
                out.println("[COMPILATION ERROR] Trying to reference an undefined function.");
                break;

            default:
                out.println("[COMPILATION ERROR] Unknown compile exception.");
        }
    }
}
