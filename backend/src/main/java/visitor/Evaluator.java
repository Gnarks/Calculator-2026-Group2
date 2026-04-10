package visitor;

import calculator.Expression;
import calculator.functions.*;
import calculator.operations.Operation;
import calculator.atoms.*;
import calculator.atoms.visitor.AtomCaster;
import calculator.atoms.visitor.AtomComparator;

import java.util.ArrayList;

/**
 * Evaluation is a concrete visitor that serves to
 * compute and evaluate the results of arithmetic expressions.
 */
public class Evaluator extends Visitor {

	/** The result of the evaluation will be stored in this private variable */
	private Atom computedValue;

	/**
	 * The angle representation, can either be DEG or RAD.
	 * Is used for the sin, cos and tan functions.
	 *
	 */
	private final AngleMode angleMode;

	/**
	 * Default constructor of the class.
	 * Initializes the angleMode to the default value RAD.
	 *
	 */
	public Evaluator() {
		this(AngleMode.RAD);
	}

	/**
	 * Constructs an Evaluator with a given angle representation.
	 * Can either be DEG or RAD
	 *
	 * @param angleMode the angle representation
	 */
	public Evaluator(AngleMode angleMode) {
		this.angleMode = angleMode;
	}

	/**
	 * getter method to obtain the result of the evaluation
	 *
	 * @return an Integer object containing the result of the evaluation
	 */
	public Atom getResult() {
		return computedValue;
	}

	/**
	 * Use the visitor design pattern to visit a Real.
	 *
	 * @param r The Real being visited
	 */
	public void visit(Real r) {
		computedValue = r;
	}

        /**
         * Use the visitor design pattern to visit an IntegerAtom.
         *
         * @param i The IntegerAtom being visited
         */
        public void visit(IntegerAtom i) {
                computedValue = i;
        }

        /**
         * Use the visitor design pattern to visit a Complex number.
         *
         * @param c The Complex number being visited
         */
        @Override
        public void visit(Complex c) {
                computedValue = c;
        }

        /**
         * Use the visitor design pattern to visit a Rationnal number.
         *
         * @param q The Rationnal number being visited
         */
	 */
	public void visit(Operation o) {
		ArrayList<Atom> evaluatedArgs = new ArrayList<>();
		// first loop to recursively evaluate each subexpression
		for (Expression a : o.args) {
			a.accept(this);
			evaluatedArgs.add(computedValue);
		}
		// second loop to accumulate all the evaluated subresults
		Atom result = evaluatedArgs.get(0);
		int max = evaluatedArgs.size();

		for (int counter = 1; counter < max; counter++) {
			Atom other = evaluatedArgs.get(counter);
			// here compare the atoms to get the type to cast to
			AtomComparator atomComp = new AtomComparator();
			result.accept(atomComp);
			other.accept(atomComp);

			// cast both the operand to the Atom Type to cast to
			AtomCaster atomCaster = new AtomCaster(atomComp.getResult());
			result.accept(atomCaster);
			result = atomCaster.getResult();

			other.accept(atomCaster);
			other = atomCaster.getResult();

			// apply the operation between the now casted Atoms
			result = result.apply(o, other);
		}
		// store the accumulated result
		computedValue = result;
	}

	@Override
        /**
         * Evaluates a unary function. If the function is trigonometric and the angle
         * mode is set to DEGREE, it will automatically convert the nested value to radians first.
         *
         * @param o the unary function to evaluate
         */
	public void visit(UnaryFunction o) {
		o.getArg().accept(this);
		Atom converted = null;
		if(o instanceof TrigonometricFunction && angleMode == AngleMode.DEG)
			converted = computedValue.toRadian();
		computedValue = (converted == null) ? computedValue.apply(o) : converted.apply(o);
	}

	@Override
        /**
         * Evaluates a binary function by first computing its two arguments
         * and then applying the function on the properly casted Atoms.
         *
         * @param f the binary function to evaluate
         */
	public void visit(BinaryFunction f) {
		f.getFirstArg().accept(this);
		Atom firstValue = computedValue;

		f.getSecondArg().accept(this);
		Atom secondValue = computedValue;

		// Cast both operands to a compatible atom type before applying the function.
		AtomComparator atomComp = new AtomComparator();
		firstValue.accept(atomComp);
		secondValue.accept(atomComp);

		AtomCaster atomCaster = new AtomCaster(atomComp.getResult());
		firstValue.accept(atomCaster);
		firstValue = atomCaster.getResult();

		secondValue.accept(atomCaster);
		secondValue = atomCaster.getResult();

		computedValue = firstValue.apply(f, secondValue);
	}

	/**
	 * Evaluates a {@code RandomFunction} node.
	 * Random functions generate pseudorandom numbers based on given arguments (max bounds and/or a random seed).
	 * If a seed is present, it is configured on the generator prior to evaluation.
	 * 
	 * @param f the {@code RandomFunction} node to be evaluated
	 * @throws UnsupportedOperationException if any of the provided random function arguments do not evaluate to integers
	 */
	@Override
	public void visit(RandomFunction f) {
		long seed = 0;
		int max = 0;
		boolean useSeed = false;

		// Extract arguments
		for (int i = 0; i < f.getArgs().size(); i++) {
			f.getArgs().get(i).accept(this);
			Atom argValue = computedValue;
			if (!(argValue instanceof IntegerAtom)) {
				throw new UnsupportedOperationException("Random arguments must evaluate to an integer");
			}
			int val = ((IntegerAtom) argValue).getValue();

			if (f.hasSeed() && i == 0) {
				seed = val;
				useSeed = true;
			} else {
				max = val;
			}
		}

		RandomGenerator generator = useSeed ? new RandomGenerator(seed) : new RandomGenerator();

		switch (f.getType()) {
			case INTEGER -> computedValue = generator.generateInteger(max);
			case RATIONNAL -> computedValue = generator.generateRational(max);
			case REAL -> computedValue = generator.generateReal();
			case COMPLEX -> computedValue = generator.generateComplex();
		}
	}

}
