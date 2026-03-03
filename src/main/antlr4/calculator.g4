grammar calculator;
// grammar used to parse the calculator's expressions

complete
    : expression EOF
    ;

// the basic expression with operation priority 
// addition expressions
expression
    : multExp ((PLUS | MINUS) multExp)*
    ;

// multiplication expressions
multExp
    : powExp ((TIMES | DIV) powExp)*
    | powExp (LPAR powExp RPAR)*
    ;


// exponentiation expressions
powExp
    : preAtom (POW preAtom)*
    ;

// upgraded atom taking into account :
// - signed atoms 
// - atoms with parenthesis
// - multiplication of the form : (34)e or (59i)pi(3 +5)
preAtom
		: (PLUS | MINUS)* atom
		| (LPAR expression RPAR)? atom (LPAR expression RPAR)?
		| (LPAR expression RPAR)
    | function
    ;

// atoms being either complex numbers or numbers
atom : complex | number  ;

// any real number either being in the form of a constant (pi, e), a real (54.66) or in scientific notation (34.55E10) 
number : constant | real | scientific ;

// scientific notation with integer exponent
scientific: (real | INT) E SIGN? INT?;

// real numbers separating units from decimals with a dot
real : INT (DOT INT)? ;

// complex numbers are formed of numbers followed by i
complex : number* I ;

// constants : pi, e 
constant : PI | EULER ;

// functions of the form :
// function (parameter1, parameter2, ...)
function : funcname LPAR expression (COMMA expression)* RPAR ;

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

SIGN : PLUS | MINUS ;


// skips the white spaces and carriage return
WS
    : [ \r\n\t]+ -> skip
    ;
