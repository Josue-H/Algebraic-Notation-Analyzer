package ajedrez;
import java_cup.runtime.Symbol; 
import java.io.*;

%%
%class LexerCup
%type java_cup.runtime.Symbol
%cup
%full
%char
%line
%column
%unicode 

// Definición de caracteres
COLUMNAS = [a-h]
FILAS = [1-8]
N = [0-9]+
PTO =\.
espacio=[ ,\t,\r,\n]+


%{
    private Symbol symbol(int type, Object value){
        return new Symbol(type, yyline, yycolumn, value);
    }

    private Symbol symbol(int type){
        return new Symbol(type, yyline, yycolumn);
    }
%}

// Definición de reglas
%%

{espacio} {/*Ignore*/}
({COLUMNAS}) { return new Symbol( sym.COLUMNA, yyline, yychar, yytext());}
({FILAS}) { return new Symbol( sym.FILA, yyline, yychar, yytext());} 
P   { return new Symbol( sym.PEON, yyline, yychar, yytext());} 
D  { return new Symbol( sym.DAMA, yyline, yychar, yytext());  }
R { return new Symbol( sym.REY, yyline, yychar, yytext());  }
T  { return new Symbol( sym.TORRE, yyline, yychar, yytext());  }
A { return new Symbol( sym.ALFIL, yyline, yychar, yytext());  }
C { return new Symbol( sym.CABALLO, yyline, yychar, yytext());  }
"=D"  { return new Symbol( sym.PROMOCION, yyline, yychar, yytext());}
"O-O"  { return new Symbol( sym.ENROQUE_CORTO, yyline, yychar, yytext());} 
"O-O-O"  { return new Symbol( sym.ENROQUE_LARGO, yyline, yychar, yytext());} 
x { return new Symbol( sym.CAPTURA, yyline, yychar, yytext());} 
"+"   { return new Symbol( sym.JAQUE, yyline, yychar, yytext());  }
"#" | "++" { return new Symbol( sym.JAQUE_MATE, yyline, yychar, yytext());} 
"!" { return new Symbol( sym.COMMENT_BUENA, yyline, yychar, yytext());} 
"?" { return new Symbol( sym.COMMENT_MALA, yyline, yychar, yytext());} 
"!!" { return new Symbol( sym.COMMENT_MUY_BUENA, yyline, yychar, yytext());} 
"??" { return new Symbol( sym.COMMENT_MUY_MALA, yyline, yychar, yytext());} 
"!?" { return new Symbol( sym.COMMENT_INTERESANTE, yyline, yychar, yytext());} 
"?!" { return new Symbol( sym.COMMENT_DUDOSA, yyline, yychar, yytext());} 
"=" { return new Symbol( sym.COMMENT_IGUALDAD, yyline, yychar, yytext());} 
"+/=" { return new Symbol( sym.COMMENT_LIG_VENTAJA_BLANCA, yyline, yychar, yytext());} 
"=/+" { return new Symbol( sym.COMMENT_LIG_VENTAJA_NEGRA, yyline, yychar, yytext());} 
"+/-" { return new Symbol( sym.COMMENT_VENTAJA_BLANCA, yyline, yychar, yytext());} 
"-/+" { return new Symbol( sym.COMMENT_VENTAJA_NEGRA, yyline, yychar, yytext());} 
 "+-" { return new Symbol( sym.COMMENT_VENTAJA_DECISIVA_BLANCA, yyline, yychar, yytext());} 
"-+" { return new Symbol( sym.COMMENT_VENTAJA_DECISIVA_NEGRA, yyline, yychar, yytext());} 
"∞" { return new Symbol( sym.COMMENT_INCERTIDUMBRE, yyline, yychar, yytext());} 
"=/∞" { return new Symbol( sym.COMMENT_COMPENSADO, yyline, yychar, yytext());} 
({N} {PTO}) { return new Symbol( sym.N, yyline, yychar, yytext()); }
.           {return new Symbol(sym.error, yyline, yychar, yytext()); }





