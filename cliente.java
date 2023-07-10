import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.net.*;
import java.util.Scanner;

public class cliente {
    public static void main(String[] args) {
        int nodo = Integer.parseInt(args[0]);
        


        if(nodo == 0){
            Scanner sc = new Scanner(System.in);
            System.out.print("Ingrese el valor de N: ");
            int N = sc.nextInt();

            double[][] A = new double[N][N];
            double[][] B = new double[N][N];
            double[][] C = new double[N][N];
            double [][] bT = new double[N][N];

            // Inicializar matrices A y B
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    A[i][j] = 2 * i + j;
                    B[i][j] = 3 * i - j;
                }
            }




            // Dividir matrices A y B
            double[][] A1 = new double[N/3][N];
            double[][] A2 = new double[N/3][N];
            double[][] A3 = new double[N/3][N];

            double[][] B1 = new double[N][N/3];
            double[][] B2 = new double[N][N/3];
            double[][] B3 = new double[N][N/3];

            // Dividir matriz A en 3 pero en filas
            for (int i = 0; i < N/3; i++) {
                for (int j = 0; j < N; j++) {
                    A1[i][j] = A[i][j];
                    A2[i][j] = A[i + N/3][j];
                    A3[i][j] = A[i + 2*N/3][j];
                }
            }

            //dividir matriz B en 3 pero en columnas,aqui vamos a crear la transpuesta.
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N/3; j++) {
                    B1[i][j] = B[i][j];
                    B2[i][j] = B[i][j + N/3];
                    B3[i][j] = B[i][j + 2*N/3];
                }
            }


        

            // Enviar matrices A1, B1, B2 y B3 al servidor 1
            try {
                Socket socket = new Socket("20.127.50.41", 50000);
                ObjectOutputStream salida = new ObjectOutputStream(socket.getOutputStream());

                salida.writeObject(A1);
                salida.writeObject(B1);
                salida.writeObject(B2);
                salida.writeObject(B3);

                System.out.println("Matrices enviadas al servidor 1");
                ObjectInputStream entrada1 = new ObjectInputStream(socket.getInputStream());
                double[][] C1 = (double[][]) entrada1.readObject();

                socket.close();

                // Unir matriz C1 con matriz C
                C = C1;
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

            

            // Enviar matrices A2, B1, B2 y B3 al servidor 2
            try {
                Socket socket = new Socket("20.232.181.111", 50000);
                ObjectOutputStream salida = new ObjectOutputStream(socket.getOutputStream());

                salida.writeObject(A2);
                salida.writeObject(B1);
                salida.writeObject(B2);
                salida.writeObject(B3);

                System.out.println("Matrices enviadas al servidor 2");
                ObjectInputStream entrada1 = new ObjectInputStream(socket.getInputStream());
                double[][] C2 = (double[][]) entrada1.readObject();

                socket.close();

                // Unir matriz C1 con matriz C
                C = unirMatrices(C, C2);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

            // Enviar matrices A3, B1, B2 y B3 al servidor 3
            try {
                    Socket socket = new Socket("20.169.150.45", 50000);
                    ObjectOutputStream salida = new ObjectOutputStream(socket.getOutputStream());

                    salida.writeObject(A3);
                    salida.writeObject(B1);
                    salida.writeObject(B2);
                    salida.writeObject(B3);

                    System.out.println("Matrices enviadas al servidor 3");
                    ObjectInputStream entrada1 = new ObjectInputStream(socket.getInputStream());
                    double[][] C3 = (double[][]) entrada1.readObject();

                    socket.close();

                // Unir matriz C1 con matriz C
                C = unirMatrices(C, C3);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        if(N == 12){
            System.out.println("Matriz A");
            imprimirMatriz(A);
            System.out.println("");
            System.out.println("Matriz B");
            imprimirMatriz(B);
            System.out.println("");
            System.out.println("Matriz C");
            imprimirMatriz(C);
            System.out.println("");
            System.out.println(obtenerChecksum(C));
        }   
        else{
            System.out.println(obtenerChecksum(C));
        }
            }
    else {
         servidorNodo();
    }
       
    }
    

private static double[][] unirMatrices(double[][] matriz1, double[][] matriz2) {
    double[][] matrizResultante = new double[matriz1.length + matriz2.length][matriz1[0].length];
    int filaActual = 0;
    for (double[] fila : matriz1) {
        matrizResultante[filaActual++] = fila;
    }
    for (double[] fila : matriz2) {
        matrizResultante[filaActual++] = fila;
    }
    return matrizResultante;
}

private static double obtenerChecksum(double[][] matriz) {
    double checksum = 0.0;

    for (int i = 0; i < matriz.length; i++) {
        for (int j = 0; j < matriz[0].length; j++) {
            double valor = matriz[i][j];
            checksum += valor;
        }
    }

    return checksum;
}
private static void imprimirMatriz(double[][] matriz) {
    for (int i = 0; i < matriz.length; i++) {
        for (int j = 0; j < matriz[0].length; j++) {
            System.out.print(matriz[i][j] + " ");
        }
        System.out.println();
    }
}

private static void servidorNodo() {
    final int PUERTO = 50000;
        try {
            ServerSocket servidor = new ServerSocket(PUERTO);
            System.out.println("Nodo  iniciado en el puerto " + PUERTO);

            while (true) {
                Socket cliente = servidor.accept();
                System.out.println("Cliente conectado desde " + cliente.getInetAddress().getHostName());

                ObjectInputStream entrada = new ObjectInputStream(cliente.getInputStream());
                double[][] A = (double[][]) entrada.readObject();
                double[][] B1 = (double[][]) entrada.readObject();
                double[][] B2 = (double[][]) entrada.readObject();
                double[][] B3 = (double[][]) entrada.readObject();
                double[][] C1 = multiplicarMatrices(A, B1);
                double[][] C2 = multiplicarMatrices(A, B2);
                double[][] C3 = multiplicarMatrices(A, B3);

                double[][] C = unirMatrices(C1, C2, C3);
                System.out.println("matriz multiplicada.");
                ObjectOutputStream salida = new ObjectOutputStream(cliente.getOutputStream());
                salida.writeObject(C);

                cliente.close();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
public static double[][] multiplicarMatrices(double[][] a, double[][] b) {
    int aRows = a.length;
    int aCols = a[0].length;
    int bRows = b.length;
    int bCols = b[0].length;
        System.out.println("Valores= " + aRows + " " + aCols + " " + bRows + " " + bCols);
    if (aCols != bRows) {
        throw new IllegalArgumentException("Matrices are not compatible for multiplication.");
    }

    double[][] result = new double[aRows][bCols];

    for (int i = 0; i < aRows; i++) {
        for (int j = 0; j < bCols; j++) {
            for (int k = 0; k < aCols; k++) {
                result[i][j] += a[i][k] * b[k][j];
            }
        }
    }

    return result;
}



private static double[][] unirMatrices(double[][] C1, double[][] C2, double[][] C3) {
    int n = C1.length;
    int m = C1[0].length;

    double[][] C = new double[n][m * 3];

    for (int i = 0; i < n; i++) {
        for (int j = 0; j < m; j++) {
            C[i][j] = C1[i][j];
            C[i][m + j] = C2[i][j];
            C[i][2 * m + j] = C3[i][j];
        }
    }

    return C;
}

}