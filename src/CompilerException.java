import java.io.PrintStream;

public class CompilerException extends RuntimeException {
    private CompilerError type;

    public CompilerException(CompilerError type) {
        super();
        this.type = type;
    }

    public void printDescription(PrintStream out) {
        switch (type) {
            case CALL_TO_UNDEFINED_VARIABLE:
                out.println("[COMPILATION ERROR] Trying to reference an undefined variable.");
                break;
            case CALL_TO_UNDEFINED_FUNCTION:
                out.println("[COMPILATION ERROR] Trying to reference an undefined function.");
                break;
            default:
                out.println("[COMPILATION ERROR] Unknown compile exception.");
        }
    }
}
