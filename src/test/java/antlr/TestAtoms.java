package antlr;

//Import Junit5 libraries for unit testing:
import static org.junit.jupiter.api.Assertions.*;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.jupiter.api.*;
import calculator.Expression;
import calculator.MyNumber;
import calculator.Notation;
import calculator.calculatorLexer;
import calculator.calculatorParser;
import visitor.Printer;

class TestAtoms {

	/**
	 * Tests the constants
	 * TODO implement REAL
	 */
	@Test
	void testConstant() {
		// String[] constants = { "e", "pi" };
		//
		// for (String constant : constants) {
		// calculatorLexer cLex = new calculatorLexer(CharStreams.fromString(constant));
		// CommonTokenStream tokens = new CommonTokenStream(cLex);
		// calculatorParser parser = new calculatorParser(tokens);
		// ParseTree tree = parser.complete();
		//
		// antlr.CompleteVisitor cVisit = new antlr.CompleteVisitor();
		// Expression result = cVisit.visit(tree);
		// // checks if the expression is an instance of MyNumber
		// assertTrue(result instanceof MyNumber);
		//
		// // checks if the result of the expression is correct
		// if (constant == "pi") {
		// assertEquals(new MyNumber(3), result);
		//
		// }
		// }
	}

	/**
	 * Tests the constants
	 * TODO implement Complex
	 */
	@Test
	void testComplex() {

	}

	/**
	 * TODO change all integers to reals
	 */
	@Test
	void testInteger() {
		String integer = "2";

		calculatorLexer cLex = new calculatorLexer(CharStreams.fromString(integer));
		CommonTokenStream tokens = new CommonTokenStream(cLex);
		calculatorParser parser = new calculatorParser(tokens);
		ParseTree tree = parser.complete();

		System.out.println(tree.toStringTree(parser));

		antlr.CompleteVisitor cVisit = new antlr.CompleteVisitor();
		Expression result = cVisit.visit(tree);
		// checks if the expression is an instance of MyNumber
		assertTrue(result instanceof MyNumber);

		// checks if the value is correct
		assertEquals(result, new MyNumber(2));

	}

	/**
	 * TODO implement REAL
	 */
	@Test
	void testScientific() {

	}

	/**
	 * TODO implement REAL
	 */
	@Test
	void testReal() {

	}

}
