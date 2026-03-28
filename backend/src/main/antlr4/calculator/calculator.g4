grammar calculator;
// grammar used to parse the calculator's expressions

complete
    : expressionIN EOF #CompleteInfix // infix expression
    | expressionPOST EOF #CompletePostfix // postfix expression
    | expressionPRE EOF #CompletePrefix // prefix expression
    ;


// prefix expression 
expressionPRE
		// spaces version 
		// ===================
    : operator expressionPRE expressionPRE #Pre2Param // simple operation with 2 expressions 
    | operator LPAR expressionPRE expressionPRE+ RPAR #PrePlus2Param // arbitrary operation with mandatory parenthesis for more then 2 expressions 
    | LPAR expressionPRE expressionPRE+ RPAR #PreMult // implicit multiplication for more then 2 expressions  
    | funcname LPAR expressionPRE+ RPAR #PreFunc // mandatory parenthesis for more then 2 parameters
		// ===================
	

	  // COMMA version
		// ===================
    | operator LPAR expressionPRE (COMMA expressionPRE)+ RPAR #PrePlus2Param // arbitrary operation between 2 or more expressions 
		| LPAR expressionPRE (COMMA expressionPRE)+ RPAR #PreMult // implicit multiplication between 2 or more expressions  
    | funcname LPAR expressionPRE (COMMA expressionPRE)* RPAR #PreFunc // mandatory parenthesis for more then 2 parameters
		// ===================

    | atom #PreAtom // the atom for the base case
		| funcname expressionPRE #PreFunc1Param // simple function with only 1 parameter 
		;

// post expression 
expressionPOST
		// spaces version 
		// ===================
    : expressionPOST expressionPOST operator #Post2Param // simple operation with 2 expressions
		| LPAR expressionPOST expressionPOST+ RPAR operator  #PostPlus2Param // arbitrary operation with mandatory parenthesis for more then 2 expressions 
		| LPAR expressionPOST expressionPOST+ RPAR #PostMult // implicit multiplication for more then 2 expressions 
    | LPAR expressionPOST+ RPAR funcname #PostFunc // mandatory parenthesis for more then 2 parameters 
		// ===================
	
	  // COMMA version
		// ===================
    | LPAR expressionPOST (COMMA expressionPOST)+ RPAR operator #PostPlus2Param // arbitrary operation with mandatory parenthesis for more then 2 expressions 
		| LPAR expressionPOST (COMMA expressionPOST)+ RPAR #PostMult // implicit multiplication for more then 2 expressions 
    | LPAR expressionPOST (COMMA expressionPOST)* RPAR funcname #PostFunc // mandatory parenthesis for more then 2 parameters
		// ===================

    | atom #PostAtom // the atom for the base case
    | expressionPOST funcname #PostFunc1Param // simple function with only 1 parameter 
		;

operator
    :PLUS #OpPlus
    |MINUS #OpMinus
    |TIMES #OpTimes
    |DIV #OpDiv
    |POW #OpPow
		;

// the basic expressionIN with operation priority 
// addition expressionINs
expressionIN
  : multExp ((PLUS | MINUS) multExp)* #INAddSub
    ;

// multiplication expressions
multExp
  : powExp ((TIMES | DIV) powExp)* #INTimesDiv
    | powExp (LPAR powExp RPAR)* #INMult
    ;


// exponentiation expressions
powExp
  : atomIN (POW atomIN)* #INPow
    ;

// upgraded atom infix taking into account :
// - signed atoms 
// - atoms with parenthesis
// - multiplication of the form : (34)e or (59i)pi(3 +5)
atomIN
		: (PLUS | MINUS)* atom (constant)* #INSignedAtom
		| (LPAR expressionIN RPAR)? atom (constant)* (LPAR expressionIN RPAR)? #INImplicitMult
		| (LPAR expressionIN RPAR) #INParenthesis
    | functionIN #INFunction
    ;

// atoms being either complex numbers or numbers
atom : complex #AtomComplex | number #AtomNumber ;

// any real number either being in the form of a constant (pi, e), a real (54.66) or in scientific notation (34.55E10) 
number : constant #NumberConstant | real #NumberReal | scientific #NumberScientific ;

// scientific notation with integer exponent
scientific: (real | INT) E (PLUS | MINUS)? INT #ScientificNumber;

// real numbers separating units from decimals with a dot
real : INT (DOT INT)? #RealNumber;

// complex numbers are formed of numbers followed by i
complex : number? (constant)* I #ComplexNumber;

// constants : pi, e 
constant : PI #ConstPi | EULER #ConstEuler ;

// functions of the form : (infix notation)
// function (parameter1, parameter2, ...)
functionIN : funcname LPAR expressionIN (COMMA expressionIN)* RPAR #InfixFunctionCall;

//accepted functions
funcname
    : COS #FnCos // 1 parameter
    | TAN #FnTan // 1 parameter
    | SIN #FnSin // 1 parameter
    | ACOS #FnAcos // 1 parameter
    | ATAN #FnAtan // 1 parameter
    | ASIN #FnAsin // 1 parameter
    | LOG #FnLog // 2 parameter : log(x, base)
    | LN #FnLn // 1 parameter
    | SQRT #FnSqrt // 1  
		;


COS : 'cos' ;
SIN : 'sin' ;
TAN : 'tan' ;
ACOS : 'acos' ;
ASIN : 'asin' ;
ATAN : 'atan' ;
LN : 'ln' ;
LOG : 'log' ;
SQRT : 'sqrt' ;
LPAR : '(' ;
RPAR : ')' ;
PLUS : '+' ;
MINUS : '-' ;
TIMES : '*' ;
DIV : '/' ;
COMMA : ',' ;
DOT : '.' ;
POW : '**' ;
PI : 'pi' ;
EULER : 'e' ;
I   : 'i' ;


INT :'0' .. '9'+ ;
// as of now, only the capital e will be used for scientific notation
E : 'E' ;

// skips the white spaces and carriage return
WS
    : [ \r\n\t]+ -> skip
    ;
