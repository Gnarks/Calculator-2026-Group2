package calculator.functions;

import calculator.Expression;
import calculator.atoms.AtomType;
import visitor.Visitor;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import calculator.Notation;
import visitor.Printer;

/**
 * Expression type for pseudo-random calculator functions.
 */
public final class RandomFunction implements Expression {

    private final AtomType type;
    private final List<Expression> args;

    private RandomFunction(AtomType type, List<Expression> args) {
        this.type = type;
        this.args = args;
    }

    public AtomType getType() {
        return type;
    }

    public List<Expression> getArgs() {
        return args;
    }

    public boolean hasSeed() {
        return (type == AtomType.INTEGER && args.size() == 2) ||
               (type == AtomType.RATIONNAL && args.size() == 2) ||
               (type == AtomType.REAL && args.size() == 1) ||
               (type == AtomType.COMPLEX && args.size() == 1);
    }

    public static RandomFunction randomInteger(Expression max) {
        List<Expression> args = new ArrayList<>();
        args.add(max);
        return new RandomFunction(AtomType.INTEGER, args);
    }

    public static RandomFunction randomInteger(Expression seed, Expression max) {
        List<Expression> args = new ArrayList<>();
        args.add(seed);
        args.add(max);
        return new RandomFunction(AtomType.INTEGER, args);
    }

    public static RandomFunction randomRational(Expression max) {
        List<Expression> args = new ArrayList<>();
        args.add(max);
        return new RandomFunction(AtomType.RATIONNAL, args);
    }

    public static RandomFunction randomRational(Expression seed, Expression max) {
        List<Expression> args = new ArrayList<>();
        args.add(seed);
        args.add(max);
        return new RandomFunction(AtomType.RATIONNAL, args);
    }

    public static RandomFunction randomReal() {
        return new RandomFunction(AtomType.REAL, new ArrayList<>());
    }

    public static RandomFunction randomReal(Expression seed) {
        List<Expression> args = new ArrayList<>();
        args.add(seed);
        return new RandomFunction(AtomType.REAL, args);
    }

    public static RandomFunction randomComplex() {
        return new RandomFunction(AtomType.COMPLEX, new ArrayList<>());
    }

    public static RandomFunction randomComplex(Expression seed) {
        List<Expression> args = new ArrayList<>();
        args.add(seed);
        return new RandomFunction(AtomType.COMPLEX, args);
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }

    @Override
	public final String toString() {
		Printer p = new Printer();
		this.accept(p);
		return p.getResult();
	}

	public final String toString(Notation n) {
		Printer p = new Printer(n);
		this.accept(p);
		return p.getResult();
	}

	@Override
	public boolean equals(Object o) {
		if (o == null)
			return false;
		if (this == o)
			return true;
		if (getClass() != o.getClass())
			return false;
        RandomFunction that = (RandomFunction) o;
        return type == that.type && Objects.equals(args, that.args);
	}
}
