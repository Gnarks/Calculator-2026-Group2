package visitor;

import calculator.Expression;
import calculator.operations.Operation;
import calculator.atoms.*;
import calculator.atoms.visitor.AtomCaster;
import calculator.atoms.visitor.AtomComparator;
import calculator.functions.BinaryFunction;
import calculator.functions.UnaryFunction;

import java.util.ArrayList;

/**
 * Evaluation is a concrete visitor that serves to
 * compute and evaluate the results of arithmetic expressions.
 */
public class Evaluator extends Visitor {

	/**
	 * Default constructor of the class. Does not initialise anything.
	 */
	public Evaluator() {
	}

	/** The result of the evaluation will be stored in this private variable */
	private Atom computedValue;

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

	public void visit(IntegerAtom i) {
		computedValue = i;
	}

	@Override
	public void visit(Complex c) {
		computedValue = c;
	}

	@Override
	public void visit(Rationnal q) {
		computedValue = q;
	}

	/**
	 * Use the visitor design pattern to visit an operation
	 * 
	 * @param o The operation being visited
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
	public void visit(UnaryFunction o) {
		o.getArg().accept(this);
		computedValue = computedValue.apply(o);
	}

	@Override
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

		if (firstValue instanceof Real && secondValue instanceof Real) {
			computedValue = f.op((Real) firstValue, (Real) secondValue);
		} else if (firstValue instanceof Complex && secondValue instanceof Complex) {
			computedValue = f.op((Complex) firstValue, (Complex) secondValue);
		} else if (firstValue instanceof IntegerAtom && secondValue instanceof IntegerAtom) {
			computedValue = f.op((IntegerAtom) firstValue, (IntegerAtom) secondValue);
		} else if (firstValue instanceof Rationnal && secondValue instanceof Rationnal) {
			computedValue = f.op((Rationnal) firstValue, (Rationnal) secondValue);
		} else {
			throw new IllegalStateException("Unsupported atom types for binary function evaluation");
		}
	}

}
