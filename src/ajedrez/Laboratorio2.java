/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ajedrez;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author HP
 */
public class Laboratorio2 {
  
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        String ruta1 = "A:\\HP\\Septimo Semestre\\Compiladores\\Documentos\\Ajedrez\\Ajedrez\\src\\ajedrez\\Lexer.flex";
        String ruta2 = "A:\\HP\\Septimo Semestre\\Compiladores\\Documentos\\Ajedrez\\Ajedrez\\src\\ajedrez\\LexerCup.flex";
        String[]  rutaS = {"-parser", "Sintax", "A:\\HP\\Septimo Semestre\\Compiladores\\Documentos\\Ajedrez\\Ajedrez\\src\\ajedrez\\Sintax.cup"};
        generar(ruta1, ruta2, rutaS);
    }
    
    public static void generar( String ruta1, String ruta2, String[]  rutaS ) throws IOException, Exception{
        File archivo;
        archivo =  new File(ruta1);
        jflex.Main.generate(archivo);
        archivo =  new File(ruta2);
        jflex.Main.generate(archivo);
        java_cup.Main.main(rutaS);
        
        
        Path rutaSym = Paths.get("A:\\HP\\Septimo Semestre\\Compiladores\\Documentos\\Ajedrez\\Ajedrez\\src\\ajedrez\\sym.java");
        if(Files.exists(rutaSym)){
            Files.delete(rutaSym);
        }
        Files.move(
                Paths.get("A:\\HP\\Septimo Semestre\\Compiladores\\Documentos\\Ajedrez\\Ajedrez\\sym.java"),
                Paths.get("A:\\HP\\Septimo Semestre\\Compiladores\\Documentos\\Ajedrez\\Ajedrez\\src\\Ajedrez\\sym.java")
        );
        
        
        Path rutaSin = Paths.get("A:\\HP\\Septimo Semestre\\Compiladores\\Documentos\\Ajedrez\\Ajedrez\\src\\ajedrez\\Sintax.java");
        if(Files.exists(rutaSin)){
            Files.delete(rutaSin);
        }        
        Files.move(
            Paths.get("A:\\HP\\Septimo Semestre\\Compiladores\\Documentos\\Ajedrez\\Ajedrez\\Sintax.java"),
            Paths.get("A:\\HP\\Septimo Semestre\\Compiladores\\Documentos\\Ajedrez\\Ajedrez\\src\\ajedrez\\Sintax.java")
        );
    }
    
}
