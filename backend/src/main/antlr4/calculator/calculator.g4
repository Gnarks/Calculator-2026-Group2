grammar calculator;
// grammar used to parse the calculator's expressions

complete
    : expressionIN EOF // infix expression
		| expressionPOST EOF // postfix expression
		| expressionPRE EOF// prefix expression
    ;


// prefix expression 
expressionPRE
		// spaces version 
		// ===================
    : operator expressionPRE expressionPRE #PRE1 // simple operation with 2 expressions 
    | operator LPAR expressionPRE expressionPRE+ RPAR #PRE2 // arbitrary operation with mandatory parenthesis for more then 2 expressions 
    | LPAR expressionPRE expressionPRE+ RPAR #PRE3 // implicit multiplication for more then 2 expressions  
    | funcname LPAR expressionPRE+ RPAR #PRE4 // mandatory parenthesis for more then 2 parameters
		// ===================
	

	  // COMMA version
		// ===================
    | operator LPAR expressionPRE (COMMA expressionPRE)+ RPAR #PRE5 // arbitrary operation between 2 or more expressions 
		| LPAR expressionPRE (COMMA expressionPRE)+ RPAR #PRE6 // implicit multiplication between 2 or more expressions  
    | funcname LPAR expressionPRE (COMMA expressionPRE)* RPAR #PRE7 // mandatory parenthesis for more then 2 parameters
		// ===================

    | atom #PRE8 // the atom for the base case
		| funcname expressionPRE #PRE9 // simple function with only 1 parameter 
		;

// post expression 
expressionPOST
		// spaces version 
		// ===================
    : expressionPOST expressionPOST operator // simple operation with 2 expressions
		| LPAR expressionPOST expressionPOST+ RPAR operator // arbitrary operation with mandatory parenthesis for more then 2 expressions 
		| LPAR expressionPOST expressionPOST+ RPAR // implicit multiplication for more then 2 expressions 
    | LPAR expressionPOST+ RPAR funcname // mandatory parenthesis for more then 2 parameters 
		// ===================
	
	  // COMMA version
		// ===================
    | LPAR expressionPOST (COMMA expressionPOST)+ RPAR operator // arbitrary operation with mandatory parenthesis for more then 2 expressions 
		| LPAR expressionPOST (COMMA expressionPOST)+ RPAR // implicit multiplication for more then 2 expressions 
    | LPAR expressionPOST (COMMA expressionPOST)* RPAR funcname // mandatory parenthesis for more then 2 parameters
		// ===================

    | atom // the atom for the base case
    | expressionPOST funcname // simple function with only 1 parameter 
		;

operator
		:PLUS
		|MINUS
		|TIMES
		|DIV
		|POW
		;

// the basic expressionIN with operation priority 
// addition expressionINs
expressionIN
    : multExp ((PLUS | MINUS) multExp)*
    ;

// multiplication expressions
multExp
    : powExp ((TIMES | DIV) powExp)*
    | powExp (LPAR powExp RPAR)*
    ;


// exponentiation expressions
powExp
    : atomIN (POW atomIN)*
    ;

// upgraded atom infix taking into account :
// - signed atoms 
// - atoms with parenthesis
// - multiplication of the form : (34)e or (59i)pi(3 +5)
atomIN
		: (PLUS | MINUS)* atom (constant)*
		| (LPAR expressionIN RPAR)? atom (constant)* (LPAR expressionIN RPAR)?
		| (LPAR expressionIN RPAR)
    | functionIN
    ;

// atoms being either complex numbers or numbers
atom : complex | number  ;

// any real number either being in the form of a constant (pi, e), a real (54.66) or in scientific notation (34.55E10) 
number : constant | real | scientific ;

// scientific notation with integer exponent
scientific: (real | INT) E (PLUS | MINUS)? INT;

// real numbers separating units from decimals with a dot
real : INT (DOT INT)? ;

// complex numbers are formed of numbers followed by i
complex : number? (constant)* I ;

// constants : pi, e 
constant : PI | EULER ;

// functions of the form : (infix notation)
// function (parameter1, parameter2, ...)
functionIN : funcname LPAR expressionIN (COMMA expressionIN)* RPAR ;

//accepted functions
funcname
    : COS // 1 parameter
    | TAN // 1 parameter
    | SIN // 1 parameter
    | ACOS // 1 parameter
    | ATAN // 1 parameter
    | ASIN // 1 parameter
    | LOG // 2 parameter : log(x, base)
    | LN // 1 parameter
    | SQRT // 1  
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
