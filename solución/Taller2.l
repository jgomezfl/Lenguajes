%option noyywrap

%{
#include <stdio.h>

#define YY_DECL int yylex()

#include "Taller2.tab.h"

%}

%%
"Int"       { return T_INT; }
"Float"     { return T_FLOAT; }
"String"    { return T_STRING; }
"True"      { return L_TRUE; }
"False"     { return L_FALSE; }
"For"       { return T_FOR; }
"While"     { return T_WHILE; }
"If"        { return T_COND; }
"Write"     { return T_WRITE; }
"Read"      { return T_READ; }
[a-zA-Z][a-zA-Z0-9_]*   { yylval = strdup(yytext); return T_ID; }
";"         { return T_SEMICOLON; }
"\n"        { return T_NEWLINE; }
[ \t]       { /* Ignorar espacios y tabulaciones */ }
"="		    {return T_EQUAL;}
"+"		    {return T_PLUS;}
"-"		    {return T_MINUS;}
"*"		    {return T_MULTIPLY;}
"/"		    {return T_DIVIDE;}
"^"		    {return T_POW;}
"("		    {return T_LEFT;}
")"		    {return T_RIGHT;}
"{"         {return T_LEFT1;}
"}"         {return T_RIGHT1;}
">"		    {return T_MAYOR;}
"<"		    {return T_MENOR;}
"=="		{return T_IGUAL;}
"!="		{return T_DIFERENTE;}
">="		{return T_MAYOR_IGUAL;}
"<="		{return T_MENOR_IGUAL;}
"&&"		{return T_AND;}
"||"		{return T_OR;}
"!"		    {return T_NOT;}
[0-9]+      { yylval = atoi(yytext); return L_INT; }
[0-9]+\.[0-9]+  { yylval = atof(yytext); return L_FLOAT; }
\"[^\"]*\"  { yylval = strdup(yytext); return L_STRING; }

.           { /* Caracteres no reconocidos */ }

%%