package ajedrez;

public class Tablero {
    private char[][] tablero;
    public int movimientos = 0;
    public boolean esCaptura;
    public boolean esPromocion;
    public boolean turnoB;
    public boolean jaque = false; 
    public boolean jaqueBlancas;

    public Tablero() {
        tablero = new char[8][8];
        System.out.println("Inicio Juego");
        inicializarTablero();
    }

    private void inicializarTablero() {
        tablero[0] = new char[] {'T', 'C', 'A', 'D', 'R', 'A', 'C', 'T'};
        tablero[1] = new char[] {'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P'};
        for (int i = 2; i < 6; i++) {
            tablero[i] = new char[] {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
        }
        tablero[6] = new char[] {'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p'};
        tablero[7] = new char[] {'t', 'c', 'a', 'd', 'r', 'a', 'c', 't'};
    }
    public boolean realizarMovimientoAmbiguo(String movimiento){
         System.out.println("Movimiento recibido: " + movimiento);
        movimientos = movimientos +1;
        int[] origen;

        System.out.println(movimientos);
        String piezaStr = movimiento.substring(0, 1);
         char desambiguacion = movimiento.charAt(1);
        String destinoStr = movimiento.substring(movimiento.length() - 2);
        
        if(movimientos % 2==0){
           piezaStr =  piezaStr.toLowerCase();
           turnoB = false;
        }else{
            piezaStr = piezaStr.toUpperCase();
            turnoB = true;
        }
        
        int[] destino = parsePosicion(destinoStr);
        char pieza = piezaStr.charAt(0);
        System.out.println(esCaptura);
        System.out.println(esPromocion);
        return buscarPiezaDesambiguacion(pieza, destino, desambiguacion);
    }
    
     private boolean buscarPiezaDesambiguacion(char pieza, int[] destino, char desambiguacion) {
        if (Character.isDigit(desambiguacion)) {
            int fila = 8 - Character.getNumericValue(desambiguacion);
            for (int j = 0; j < 8; j++) {
                if (tablero[fila][j] == pieza) {
                    int[] origen = {fila, j};
                    if (esMovimientoValido(origen, destino)) {
                        return true;
                    }
                }
            }
        } else if (Character.isLetter(desambiguacion)) {
            int columna = desambiguacion - 'a';
            for (int i = 0; i < 8; i++) {
                if (tablero[i][columna] == pieza) {
                    int[] origen = {i, columna};
                    if (esMovimientoValido(origen, destino)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
     private boolean esMovimientoValido(int[] origen, int[] destino) {
        char pieza = tablero[origen[0]][origen[1]];
         System.out.println("Piza: "+pieza);
        switch (Character.toLowerCase(pieza)) {
            case 'p': return moverPeon(origen, destino);
            case 't': return moverLineaRecta(origen, destino);
            case 'c': return moverCaballo(origen, destino);
            case 'a': return moverDiagonal(origen, destino);
            case 'd': return moverLineaRecta(origen, destino) || moverDiagonal(origen, destino);
            case 'r': return moverRey(origen, destino);
            default: return false;
        }
    }

    
    public boolean realizarMovimiento(String movimiento) {
        System.out.println("Movimiento recibido: " + movimiento);
        movimientos = movimientos +1;
        if (movimiento.length() < 3) {
            return false;
        }
        System.out.println(movimientos);
        String piezaStr = movimiento.substring(0, 1);
        String destinoStr = movimiento.substring(movimiento.length() - 2);
        
        if(movimientos % 2==0){
           piezaStr =  piezaStr.toLowerCase();
           turnoB = false;
        }else{
            piezaStr = piezaStr.toUpperCase();
            turnoB = true;
        }
        
        int[] destino = parsePosicion(destinoStr);
        char pieza = piezaStr.charAt(0);
        System.out.println(pieza);
        System.out.println(esCaptura);
        System.out.println(esPromocion);
        return buscarYRealizarMovimiento(pieza, destino);
    }   

    private boolean buscarYRealizarMovimiento(char pieza, int[] destino) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (tablero[i][j] == pieza) {
                    int[] origen = {i, j};
                    System.out.println("Pieza encontrada en: " + i + "," + j);
                    switch (pieza) {
                        case 'P': case 'p': if (moverPeon(origen, destino)) return true; break;
                        case 'T': case 't': if (moverTorre(origen, destino)) return true; break;
                        case 'C': case 'c': if (moverCaballo(origen, destino)) return true; break;
                        case 'A': case 'a': if (moverAlfil(origen, destino)) return true; break;
                        case 'D': case 'd': if (moverDama(origen, destino)) return true; break;
                        case 'R': case 'r': if (moverRey(origen, destino)) return true; break;
                        default: break;
                    }
                }
            }
        }
        return false;
    }

    private boolean moverPeon(int[] origen, int[] destino) {
        System.out.println("Moviendo peón de " + origen[0] + "," + origen[1] + " a " + destino[0] + "," + destino[1]);
        int dir = (Character.isUpperCase(tablero[origen[0]][origen[1]])) ? 1 : -1; // Corrección de la dirección

        // Movimiento hacia adelante
        if (destino[1] == origen[1] && destino[0] == origen[0] + dir ) {
            return moverPieza(origen, destino);
        }
        // Movimiento doble hacia adelante desde la posición inicial
        if (destino[1] == origen[1] && destino[0] == origen[0] + 2 * dir && 
            ((dir == 1 && origen[0] == 1) || (dir == -1 && origen[0] == 6))  && tablero[origen[0] + dir][origen[1]] == ' ') {
            return moverPieza(origen, destino);
        }
        // Captura diagonal
        if (Math.abs(destino[1] - origen[1]) == 1 && destino[0] == origen[0] + dir && tablero[destino[0]][destino[1]] != ' ' && Character.isLowerCase(tablero[destino[0]][destino[1]]) != Character.isLowerCase(tablero[origen[0]][origen[1]])) {
            return moverPieza(origen, destino);
        }
        return false;
    }



    private boolean moverTorre(int[] origen, int[] destino) {
        if (origen[0] == destino[0] || origen[1] == destino[1]) {
            return moverLineaRecta(origen, destino);
        }
        return false;
    }

    private boolean moverCaballo(int[] origen, int[] destino) {
        if ((Math.abs(destino[0] - origen[0]) == 2 && Math.abs(destino[1] - origen[1]) == 1) ||
            (Math.abs(destino[0] - origen[0]) == 1 && Math.abs(destino[1] - origen[1]) == 2)) {
            return moverPieza(origen, destino);
        }
        return false;
    }

    private boolean moverAlfil(int[] origen, int[] destino) {
        if (Math.abs(destino[0] - origen[0]) == Math.abs(destino[1] - origen[1])) {
            return moverDiagonal(origen, destino);
        }
        return false;
    }

    private boolean moverDama(int[] origen, int[] destino) {
        return moverLineaRecta(origen, destino) || moverDiagonal(origen, destino);
    }

    private boolean moverRey(int[] origen, int[] destino) {
        if (Math.abs(destino[0] - origen[0]) <= 1 && Math.abs(destino[1] - origen[1]) <= 1) {
            return moverPieza(origen, destino);
        }
        return false;
    }

private boolean moverLineaRecta(int[] origen, int[] destino) {
    int dirX = Integer.compare(destino[0], origen[0]);
    int dirY = Integer.compare(destino[1], origen[1]);
    int x = origen[0] + dirX;
    int y = origen[1] + dirY;
    while (x != destino[0] || y != destino[1]) {
        if (tablero[x][y] != ' ') return false; // Si hay una pieza en el camino, el movimiento es inválido
        x += dirX;
        y += dirY;
    }
    return moverPieza(origen, destino);
}

private boolean moverDiagonal(int[] origen, int[] destino) {
    int dirX = Integer.compare(destino[0], origen[0]);
    int dirY = Integer.compare(destino[1], origen[1]);
    int x = origen[0] + dirX;
    int y = origen[1] + dirY;
    while (x != destino[0] || y != destino[1]) {
        if (tablero[x][y] != ' ') return false; // Si hay una pieza en el camino, el movimiento es inválido
        x += dirX;
        y += dirY;
    }
    return moverPieza(origen, destino);
}


private boolean moverPieza(int[] origen, int[] destino) {
    char pieza = tablero[origen[0]][origen[1]];
    System.out.println(destino[0]+" "+destino[1]+"  "+tablero[destino[0]][destino[1]]);
    if(esCaptura==false){
        if (tablero[destino[0]][destino[1]] == ' ' ) {
            tablero[destino[0]][destino[1]] = pieza;
            tablero[origen[0]][origen[1]] = ' ';
            System.out.println("Movimiento realizado: " + pieza + " a " + destino[0] + "," + destino[1]);
            if(esPromocion == true){
                verificarPromocionPeon(turnoB);
            }

                verificarJaque(true);
            
            imprimirTablero();
            return true;
        } 
    }else {
         // Verifica que haya una pieza en el destino y que sea de un color diferente
        if (tablero[destino[0]][destino[1]] != ' ' && Character.isLowerCase(tablero[destino[0]][destino[1]]) != Character.isLowerCase(pieza)) {
            tablero[destino[0]][destino[1]] = pieza;
            tablero[origen[0]][origen[1]] = ' ';
            System.out.println("Movimiento realizado: " + pieza + " a " + destino[0] + "," + destino[1]);
            if(esPromocion == true){
                verificarPromocionPeon(turnoB);
            } 
                verificarJaque(true);
          
            imprimirTablero();
            return true;
            
        } 
        
    }
    imprimirTablero();
    return false;
}

 
  public boolean verificarJaque(boolean esTurnoBlanco) {
        System.out.println("Entramos a verificar Jaque");
        char rey = esTurnoBlanco ? 'r' : 'R';
        int[] posRey = buscarRey(rey);

        if (posRey == null) {
            System.out.println("El rey " + (esTurnoBlanco ? "negro" : "blanco") + " no se encuentra en el tablero.");
            return false;
        }

        boolean jaque = estaEnJaque(posRey, esTurnoBlanco);
        if (jaque) {
            System.out.println("¡Jaque al rey " + (esTurnoBlanco ? "negro" : "blanco") + "!");
        }
        System.out.println(jaque);
        if(esTurnoBlanco){
            jaqueBlancas = jaque;
        }
        return jaque;
    }

    public boolean verificarJaqueMate(boolean esTurnoBlanco) {
        char rey = esTurnoBlanco ? 'r' : 'R';
        int[] posRey = buscarRey(rey);

        if (posRey == null) {
            System.out.println("El rey " + (esTurnoBlanco ? "negro" : "blanco") + " no se encuentra en el tablero.");
            return false;
        }

        boolean jaqueMate = esJaqueMate(posRey, esTurnoBlanco);
        if (jaqueMate) {
            System.out.println("¡Jaque mate! El rey " + (esTurnoBlanco ? "negro" : "blanco") + " ha sido derrotado.");
        }

        return jaqueMate;
    }

    private int[] buscarRey(char rey) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (tablero[i][j] == rey) {
                    return new int[] {i, j};
                }
            }
        }
        return null;
    }

private boolean estaEnJaque(int[] posRey, boolean esTurnoBlanco) {
    int reyX = posRey[0];
    int reyY = posRey[1];
    char rey = esTurnoBlanco ? 'r' : 'R';
    char torre = esTurnoBlanco ? 'T' : 't';
    char dama = esTurnoBlanco ? 'D' : 'd';
    char alfil = esTurnoBlanco ? 'A' : 'a';
    char caballo = esTurnoBlanco ? 'C' : 'c';
    char peon = esTurnoBlanco ? 'P' : 'p';

    // Verificar ataques horizontales y verticales
    for (int i = 0; i < 8; i++) {
        if (i != reyX && (tablero[i][reyY] == torre || tablero[i][reyY] == dama)) return true;
        if (i != reyY && (tablero[reyX][i] == torre || tablero[reyX][i] == dama)) return true;
    }

    // Verificar ataques diagonales
    for (int i = -1; i <= 1; i += 2) {
        for (int j = -1; j <= 1; j += 2) {
            int x = reyX + i, y = reyY + j;
            while (x >= 0 && x < 8 && y >= 0 && y < 8) {
                if (tablero[x][y] == alfil || tablero[x][y] == dama) return true;
                if (tablero[x][y] != ' ') break;
                x += i;
                y += j;
            }
        }
    }

    // Verificar ataques de caballos
    int[][] movimientosCaballo = {
        {1, 2}, {2, 1}, {2, -1}, {1, -2},
        {-1, -2}, {-2, -1}, {-2, 1}, {-1, 2}
    };
    for (int[] mov : movimientosCaballo) {
        int x = reyX + mov[0];
        int y = reyY + mov[1];
        if (x >= 0 && x < 8 && y >= 0 && y < 8 && tablero[x][y] == caballo) return true;
    }

    // Verificar ataques de peones
    int dir = esTurnoBlanco ? -1 : 1;
    if (reyX + dir >= 0 && reyX + dir < 8) {
        if (reyY - 1 >= 0 && tablero[reyX + dir][reyY - 1] == peon) return true;
        if (reyY + 1 < 8 && tablero[reyX + dir][reyY + 1] == peon) return true;
    }

    // Verificar ataques de otro rey
    for (int i = -1; i <= 1; i++) {
        for (int j = -1; j <= 1; j++) {
            if (i == 0 && j == 0) continue;
            int x = reyX + i;
            int y = reyY + j;
            if (x >= 0 && x < 8 && y >= 0 && y < 8 && tablero[x][y] == rey) return true;
        }
    }

    return false;
}


private boolean esJaqueMate(int[] posRey, boolean esTurnoBlanco) {
    int reyX = posRey[0];
    int reyY = posRey[1];
    char rey = esTurnoBlanco ? 'r' : 'R';

    for (int i = -1; i <= 1; i++) {
        for (int j = -1; j <= 1; j++) {
            if (i == 0 && j == 0) continue;
            int x = reyX + i;
            int y = reyY + j;
            if (x >= 0 && x < 8 && y >= 0 && y < 8 && tablero[x][y] == ' ') {
                char temp = tablero[x][y];
                tablero[x][y] = rey;
                tablero[reyX][reyY] = ' ';
                if (!estaEnJaque(new int[] {x, y}, esTurnoBlanco)) {
                    tablero[x][y] = temp;
                    tablero[reyX][reyY] = rey;
                    return false;
                }
                tablero[x][y] = temp;
                tablero[reyX][reyY] = rey;
            }
        }
    }
    return true;
}
    public boolean enroqueCorto(boolean esBlanco) {
        
        int fila = esBlanco ? 0 : 7;
        char rey = esBlanco ? 'R' : 'r';
        char torre = esBlanco ? 'T' : 't';
        System.out.println(rey);
        System.out.println(tablero[fila][4]);
        System.out.println(tablero[fila][7]);
        // Verificar que el rey y la torre no se han movido
        if (tablero[fila][4] != rey || tablero[fila][7] != torre) {
            return false;
        }

        // Verificar que no hay piezas entre el rey y la torre
        for (int i = 5; i <= 6; i++) {
            if (tablero[fila][i] != ' ') {
                return false;
            }
        }



        // Realizar el enroque
        tablero[fila][4] = ' ';
        tablero[fila][6] = rey;
        tablero[fila][7] = ' ';
        tablero[fila][5] = torre;

        return true;
    }

    public boolean enroqueLargo(boolean esBlanco) {
        
        int fila = esBlanco ? 0 : 7;
        char rey = esBlanco ? 'R' : 'r';
        char torre = esBlanco ? 'T' : 't';
        

        // Verificar que el rey y la torre no se han movido
        if (tablero[fila][4] != rey || tablero[fila][0] != torre) {
            return false;
        }

        // Verificar que no hay piezas entre el rey y la torre
        for (int i = 1; i <= 3; i++) {
            if (tablero[fila][i] != ' ') {
                return false;
            }
        }


        // Realizar el enroque
        tablero[fila][4] = ' ';
        tablero[fila][2] = rey;
        tablero[fila][0] = ' ';
        tablero[fila][3] = torre;

        return true;
    }
    public void verificarPromocionPeon(boolean esTurnoBlanco) {
        System.out.println("Verificando Promoción");
        System.out.println(esTurnoBlanco);
    int filaPromocion = esTurnoBlanco ? 7 : 0; // Fila de promoción para blancos es la fila 7 y para negros es la fila 0
    char peon = esTurnoBlanco ? 'P' : 'p'; // Peón blanco es 'P' y peón negro es 'p'
    char dama = esTurnoBlanco ? 'D' : 'd'; // Dama blanca es 'D' y dama negra es 'd'

    // Verificar la fila de promoción correspondiente
    for (int columna = 0; columna < 8; columna++) {
        if (tablero[filaPromocion][columna] == peon) {
            promocionarPeon(filaPromocion, columna, dama);
        }
    }
}

public void promocionarPeon(int fila, int columna, char nuevaPieza) {
    tablero[fila][columna] = nuevaPieza; // Promocionar el peón a la nueva pieza (Dama en este caso)
    System.out.println("Peón en (" + fila + "," + columna + ") ha sido promovido a " + nuevaPieza);
}



private int[] parsePosicion(String posicion) {
    int columna = posicion.charAt(0) - 'a'; // Columna de 'a'-'h' a 0-7
    int fila = Integer.parseInt(posicion.substring(1)); // Fila de '8'-'1' a 0-7
    return new int[] {fila-1, columna};
}

public void imprimirTablero() {
    for (int i = 0; i < 8; i++) {
        for (int j = 0; j < 8; j++) {
            System.out.print(tablero[i][j] + " ");
        }
        System.out.println();
    }
}
}