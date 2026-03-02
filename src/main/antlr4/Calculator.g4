/*
BSD License

Copyright (c) 2013, Tom Everett
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions
are met:

1. Redistributions of source code must retain the above copyright
   notice, this list of conditions and the following disclaimer.
2. Redistributions in binary form must reproduce the above copyright
   notice, this list of conditions and the following disclaimer in the
   documentation and/or other materials provided with the distribution.
3. Neither the name of Tom Everett nor the names of its contributors
   may be used to endorse or promote products derived from this software
   without specific prior written permission.

The basis of the following grammar can be found at https://github.com/antlr/grammars-v4/blob/master/calculator/calculator.g4
Respecting the first condition of the modification of the initial source code, the copyright is retained. 
*/

// $antlr-format alignTrailingComments true, columnLimit 150, minEmptyLines 1, maxEmptyLinesToKeep 1, reflowComments false, useTab false
// $antlr-format allowShortRulesOnASingleLine false, allowShortBlocksOnASingleLine true, alignSemicolons hanging, alignColons hanging

grammar calculator;

equation
    : expression EOF
    ;

expression
    : multiplyingExpression ((PLUS | MINUS) multiplyingExpression)*
    ;

multiplyingExpression
    : powExpression ((TIMES | DIV) powExpression)*
    ;

powExpression
    : signedAtom (POW signedAtom)*
    ;

signedAtom
    : PLUS signedAtom
    | MINUS signedAtom
    | func_
    | atom
    ;

atom
    : scientific
		| complex
    | constant
    | LPAREN expression RPAREN
    ;

scientific
    : SCIENTIFIC_NUMBER
    ;

complex 
		: COMPLEX_NUMBER
		;

constant
    : PI
    | EULER
    | I
    ;

func_
    : funcname LPAREN expression (COMMA expression)* RPAREN
    ;

funcname
    : COS
    | TAN
    | SIN
    | ACOS
    | ATAN
    | ASIN
    | LOG
    | LN
    | SQRT
    ;

COS
    : 'cos'
    ;

SIN
    : 'sin'
    ;

TAN
    : 'tan'
    ;

ACOS
    : 'acos'
    ;

ASIN
    : 'asin'
    ;

ATAN
    : 'atan'
    ;

LN
    : 'ln'
    ;

LOG
    : 'log'
    ;

SQRT
    : 'sqrt'
    ;

LPAREN
    : '('
    ;

RPAREN
    : ')'
    ;

PLUS
    : '+'
    ;

MINUS
    : '-'
    ;

TIMES
    : '*'
    ;

DIV
    : '/'
    ;

COMMA
    : ','
    ;

POINT
    : '.'
    ;

POW
    : '**'
    ;

PI
    : 'pi'
    ;

EULER
    : 'e'
    ;

I
    : 'i'
    ;


SCIENTIFIC_NUMBER
    : NUMBER (E SIGN? NUMBER)?
    ;


COMPLEX_NUMBER
    : (SCIENTIFIC_NUMBER SIGN)? SCIENTIFIC_NUMBER I
		;

fragment NUMBER
    : '0' ..'9'+ ('.' '0' ..'9'+)?
    ;

// as of now, only the cap e will be used for scientific notation
fragment E
    : 'E'
    ;

fragment SIGN
    : '+'
    | '-'
    ;

WS
    : [ \r\n\t]+ -> skip
    ;
