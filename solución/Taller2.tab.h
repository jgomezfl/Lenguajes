
/* A Bison parser, made by GNU Bison 2.4.1.  */

/* Skeleton interface for Bison's Yacc-like parsers in C
   
      Copyright (C) 1984, 1989, 1990, 2000, 2001, 2002, 2003, 2004, 2005, 2006
   Free Software Foundation, Inc.
   
   This program is free software: you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation, either version 3 of the License, or
   (at your option) any later version.
   
   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.
   
   You should have received a copy of the GNU General Public License
   along with this program.  If not, see <http://www.gnu.org/licenses/>.  */

/* As a special exception, you may create a larger work that contains
   part or all of the Bison parser skeleton and distribute that work
   under terms of your choice, so long as that work isn't itself a
   parser generator using the skeleton or a modified version thereof
   as a parser skeleton.  Alternatively, if you modify or redistribute
   the parser skeleton itself, you may (at your option) remove this
   special exception, which will cause the skeleton and the resulting
   Bison output files to be licensed under the GNU General Public
   License without this special exception.
   
   This special exception was added by the Free Software Foundation in
   version 2.2 of Bison.  */


/* Tokens.  */
#ifndef YYTOKENTYPE
# define YYTOKENTYPE
   /* Put the tokens into the symbol table, so that GDB and other debuggers
      know about them.  */
   enum yytokentype {
     T_ID = 258,
     T_INT = 259,
     T_FLOAT = 260,
     T_STRING = 261,
     T_FOR = 262,
     T_WHILE = 263,
     T_COND = 264,
     T_WRITE = 265,
     T_READ = 266,
     T_EQUAL = 267,
     T_PLUS = 268,
     T_MINUS = 269,
     T_MULTIPLY = 270,
     T_DIVIDE = 271,
     T_POW = 272,
     T_MAYOR = 273,
     T_MENOR = 274,
     T_IGUAL = 275,
     T_DIFERENTE = 276,
     T_MAYOR_IGUAL = 277,
     T_MENOR_IGUAL = 278,
     T_AND = 279,
     T_OR = 280,
     T_NOT = 281,
     T_LEFT = 282,
     T_RIGHT = 283,
     T_SEMICOLON = 284,
     T_LEFT1 = 285,
     T_RIGHT1 = 286,
     L_INT = 287,
     L_FLOAT = 288,
     L_STRING = 289,
     L_TRUE = 290,
     L_FALSE = 291,
     T_NEWLINE = 292
   };
#endif



#if ! defined YYSTYPE && ! defined YYSTYPE_IS_DECLARED
typedef int YYSTYPE;
# define YYSTYPE_IS_TRIVIAL 1
# define yystype YYSTYPE /* obsolescent; will be withdrawn */
# define YYSTYPE_IS_DECLARED 1
#endif

extern YYSTYPE yylval;


