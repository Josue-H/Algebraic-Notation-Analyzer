package ajedrez;
import static ajedrez.Token.*; 
import java.io.*;

%%
%class Lexer
%type Token
%line
%column
%unicode 


// Definición de caracteres
COLUMNA = [a-h]548
FILA = [1-8]
SALTO = [\n]
E = [ ,\t,\n,\r]+
N = [0-9]+
PTO =\.


%{
    public String lexeme;
    public int line_count;
    public int column_count;
%}

// Definición de reglas
%%


[a-h] { line_count=yyline;column_count=yycolumn; lexeme=yytext(); return COLUMNAS;}
[1-8] { line_count=yyline;column_count=yycolumn; lexeme=yytext(); return FILAS;}
P  { line_count=yyline;column_count=yycolumn; lexeme=yytext(); return PEON;}
D  { line_count=yyline;column_count=yycolumn; lexeme=yytext(); return DAMA; }
R { line_count=yyline;column_count=yycolumn; lexeme=yytext(); return REY; }
T  { line_count=yyline;column_count=yycolumn; lexeme=yytext(); return TORRE; }
A { line_count=yyline;column_count=yycolumn; lexeme=yytext(); return ALFIL; }
C { line_count=yyline;column_count=yycolumn; lexeme=yytext(); return CABALLO; }
/*P({COLUMNA}{FILA})  | ({COLUMNA}{FILA})   { line_count=yyline;column_count=yycolumn; lexeme=yytext(); return MOV_PEON;}
D({COLUMNA}|{FILA})?({COLUMNA}{FILA})? { line_count=yyline;column_count=yycolumn; lexeme=yytext(); return MOV_DAMA; }
R({COLUMNA}|{FILA})?({COLUMNA}{FILA})? { line_count=yyline;column_count=yycolumn; lexeme=yytext(); return MOV_REY; }
T({COLUMNA}|{FILA})?({COLUMNA}{FILA})? { line_count=yyline;column_count=yycolumn; lexeme=yytext(); return MOV_TORRE; }
A({COLUMNA}|{FILA})?({COLUMNA}{FILA})? { line_count=yyline;column_count=yycolumn; lexeme=yytext(); return MOV_ALFIL; }
C({COLUMNA}|{FILA})?({COLUMNA}{FILA})? { line_count=yyline;column_count=yycolumn; lexeme=yytext(); return MOV_CABALLO; }*/
"O-O"  { line_count=yyline;column_count=yycolumn; lexeme=yytext(); return ENROQUE_CORTO;}
"O-O-O"  { line_count=yyline;column_count=yycolumn; lexeme=yytext(); return ENROQUE_LARGO;}
({COLUMNA}{FILA}) "=D"  { line_count=yyline;column_count=yycolumn; lexeme=yytext(); return PROMOCION;}
x({COLUMNA})({FILA}) | x { line_count=yyline;column_count=yycolumn; lexeme=yytext(); return CAPTURA;}
"+"   { line_count=yyline;column_count=yycolumn; lexeme=yytext(); return JAQUE; }
"#" | "++" { line_count=yyline;column_count=yycolumn; lexeme=yytext(); return JAQUE_MATE;}
"!" { line_count=yyline;column_count=yycolumn; lexeme=yytext(); return COMMENT_BUENA;}
"?" { line_count=yyline;column_count=yycolumn; lexeme=yytext(); return COMMENT_MALA;}
"!!" { line_count=yyline;column_count=yycolumn; lexeme=yytext(); return COMMENT_MUY_BUENA;}
"??" { line_count=yyline;column_count=yycolumn; lexeme=yytext(); return COMMENT_MUY_MALA;}
"!?" { line_count=yyline;column_count=yycolumn; lexeme=yytext(); return COMMENT_INTERESANTE;}
"?!" { line_count=yyline;column_count=yycolumn; lexeme=yytext(); return COMMENT_DUDOSA;}
"=" { line_count=yyline;column_count=yycolumn; lexeme=yytext(); return COMMENT_IGUALDAD;}
"+/=" { line_count=yyline;column_count=yycolumn; lexeme=yytext(); return COMMENT_LIG_VENTAJA_BLANCA;}
"=/+" { line_count=yyline;column_count=yycolumn; lexeme=yytext(); return COMMENT_LIG_VENTAJA_NEGRA;}
"+/-" { line_count=yyline;column_count=yycolumn; lexeme=yytext(); return COMMENT_VENTAJA_BLANCA;}
"-/+" { line_count=yyline;column_count=yycolumn; lexeme=yytext(); return COMMENT_VENTAJA_NEGRA;}
 "+-" { line_count=yyline;column_count=yycolumn; lexeme=yytext(); return COMMENT_VENTAJA_DECISIVA_BLANCA;}
"-+" { line_count=yyline;column_count=yycolumn; lexeme=yytext(); return COMMENT_VENTAJA_DECISIVA_NEGRA;}
"∞" { line_count=yyline;column_count=yycolumn; lexeme=yytext(); return COMMENT_INCERTIDUMBRE;}
"=/∞" { line_count=yyline;column_count=yycolumn; lexeme=yytext(); return COMMENT_COMPENSADO;}
({N} {PTO}) { line_count=yyline;column_count=yycolumn; lexeme=yytext(); return N ;}
({SALTO})   {/*ignore*/; }               
({E}) { }
.           {line_count=yyline;column_count=yycolumn; lexeme=yytext(); return ERROR;}





