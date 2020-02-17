import java.io.PrintStream;

public class CompilerException extends RuntimeException {
    private CompilerError type;

    public CompilerException(CompilerError type) {
        super();
        this.type = type;
    }

    public void printDescription(PrintStream out) {
        switch (type) {
            case UNDEFINED_VARIABLE:
                out.println("Trying to call an undefined variable.");
                break;
            default:
                out.println("Unknown compile exception.");
        }
    }
}
