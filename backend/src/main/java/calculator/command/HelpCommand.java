package calculator.command;

public class HelpCommand implements CLICommand {
    @Override
    public boolean execute(String args) {
        System.out.println("======================================================================");
        System.out.println("                         CALCULATOR HELP                              ");
        System.out.println("======================================================================");
        System.out.println("Welcome to the Calculator! This tool allows you to evaluate arithmetic");
        System.out.println("expressions directly from the command line.");
        System.out.println();
        System.out.println("USAGE:");
        System.out.println("  calc> <expression>          Evaluates the mathematical expression.");
        System.out.println("  calc> eval <expression>     (Same as above)");
        System.out.println("  calc> help                  Displays this help message.");
        System.out.println("  calc> exit                  Exits the calculator.");
        System.out.println();
        System.out.println("AVAILABLE OPERATORS:");
        System.out.println("  +   Addition                 -   Subtraction");
        System.out.println("  *   Multiplication           /   Division");
        System.out.println("  **  Power");
        System.out.println();
        System.out.println("AVAILABLE FUNCTIONS:");
        System.out.println("  sin(x), cos(x), tan(x)       Trigonometric functions");
        System.out.println("  asin(x), acos(x), atan(x)    Inverse trigonometric functions");
        System.out.println("  sinh(x), cosh(x), tanh(x)    Hyperbolic functions");
        System.out.println("  ln(x), log(x)                Natural and base-10 logarithms");
        System.out.println("  sqrt(x)                      Square root");
        System.out.println();
        System.out.println("CONSTANTS & NUMBERS:");
        System.out.println("  pi                           Archimedes' constant (3.1415...)");
        System.out.println("  e                            Euler's number (2.7182...)");
        System.out.println("  i                            Imaginary unit");
        System.out.println("  Integers and decimals are supported (e.g. 42, 3.14)");
        System.out.println("======================================================================");
        return true;
    }
}
