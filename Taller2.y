%{
#include <stdio.h>
#include <stdlib.h>

extern int yylex();
extern int yyparse();
extern FILE* yyin;
extern char* yytext;

void yyerror(const char* s);

int currentLine = 1;  // Variable para rastrear el número de línea
char* currentText = NULL;  // Variable para rastrear el texto actual
%}

%token T_ID T_INT T_FLOAT T_STRING T_FOR T_WHILE T_COND T_WRITE T_READ
// Operadores aritmeticos
%token T_EQUAL T_PLUS T_MINUS T_MULTIPLY T_DIVIDE T_POW
//Operadores relacionales
%token T_MAYOR T_MENOR T_IGUAL T_DIFERENTE T_MAYOR_IGUAL T_MENOR_IGUAL
//Operadores booleanos
%token T_AND T_OR T_NOT
//Signos de puntuacion
%token T_LEFT T_RIGHT T_SEMICOLON T_LEFT1 T_RIGHT1

%token L_INT L_FLOAT L_STRING L_TRUE L_FALSE
%token T_NEWLINE

%left T_PLUS T_MINUS
%left T_MULTIPLY T_DIVIDE

%start Programa

%%

Programa:
    | Programa sentencia
;

sentencia: T_NEWLINE{ currentLine++; }
    | dec T_SEMICOLON
    | exp_alg T_SEMICOLON
    | asig T_SEMICOLON
    | exp_bool T_SEMICOLON
    | bucle T_LEFT1 Programa T_RIGHT1
    | cond T_LEFT1 Programa T_RIGHT1
    | write T_SEMICOLON
    | read T_SEMICOLON
;

dec: dec_Int
    | dec_Float
    | dec_String
;

dec_Int: T_INT T_ID
    | T_INT asig_INT
;

dec_Float: T_FLOAT T_ID
    | T_FLOAT asig_FLOAT
;

dec_String: T_STRING T_ID
    | T_STRING asig_STRING
;

exp_alg: exp_alg_int | exp_alg_float
;

exp_alg_int: L_INT
    | exp_alg_int T_PLUS exp_alg_int
    | exp_alg_int T_MINUS exp_alg_int
    | exp_alg_int T_MULTIPLY exp_alg_int
    | exp_alg_int T_POW exp_alg_int
    | T_LEFT exp_alg_int T_RIGHT
;

exp_alg_float: L_FLOAT
    | exp_alg_float T_PLUS exp_alg_float
    | exp_alg_float T_MINUS exp_alg_float
    | exp_alg_float T_MULTIPLY exp_alg_float
    | exp_alg_float T_DIVIDE exp_alg_float
    | exp_alg_float T_PLUS exp_alg_int
    | exp_alg_float T_MINUS exp_alg_int
    | exp_alg_float T_MULTIPLY exp_alg_int
    | exp_alg_float T_DIVIDE exp_alg_int
    | exp_alg_int T_PLUS exp_alg_float
    | exp_alg_int T_MINUS exp_alg_float
    | exp_alg_int T_MULTIPLY exp_alg_float
    | exp_alg_int T_DIVIDE exp_alg_float
    | exp_alg_int T_DIVIDE exp_alg_int
    | T_LEFT exp_alg_float T_RIGHT
;

asig: asig_INT
    | asig_FLOAT
    | asig_STRING
    | T_ID T_EQUAL T_ID
;

asig_INT: T_ID T_EQUAL exp_alg_int
;

asig_FLOAT: T_ID T_EQUAL exp_alg_float
;

asig_STRING : T_ID T_EQUAL L_STRING
;

exp_bool:
    | op_bool
;

op_rel: termino T_MAYOR termino
    | termino T_MENOR termino
    | termino T_IGUAL termino
    | termino T_DIFERENTE termino
    | termino T_MAYOR_IGUAL termino
    | termino T_MENOR_IGUAL termino
;

op_bool: L_TRUE | L_FALSE | T_ID | op_rel
    | op_bool T_AND op_bool
    | op_bool T_OR op_bool
    | T_NOT op_bool
    | T_LEFT op_bool T_RIGHT
;

termino: L_FLOAT | L_INT | T_ID;
termino_bucle: T_ID T_MENOR L_INT
    | T_ID T_MENOR_IGUAL L_INT
    | T_ID T_MAYOR L_INT
    | T_ID T_MAYOR_IGUAL L_INT
    | T_ID T_MENOR L_FLOAT
    | T_ID T_MENOR_IGUAL L_FLOAT
    | T_ID T_MAYOR L_FLOAT
    | T_ID T_MAYOR_IGUAL L_FLOAT
;
op_for: T_ID T_PLUS termino
    | T_ID T_MINUS termino
;

bucle: bucle_for
    | bucle_while
;

bucle_for:
    | T_FOR T_LEFT dec_Int T_SEMICOLON termino_bucle T_SEMICOLON op_for T_RIGHT
    | T_FOR T_LEFT asig_INT T_SEMICOLON termino_bucle T_SEMICOLON op_for T_RIGHT
;

bucle_while: T_WHILE T_LEFT exp_bool T_RIGHT;

cond: T_COND T_LEFT exp_bool T_RIGHT;

write: T_WRITE T_ID;

read: T_READ T_ID;

%%

int main() {
	yyin = stdin;

	do {
		yyparse();
	} while(!feof(yyin));

	return 0;
}

void yyerror(const char* s) {
    fprintf(stderr, "Error: %s at Line %d", s, currentLine);
	exit(1);
}
