import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor1 {
    public static void main(String[] args) {
        int puerto = 9000;

        try {
            ServerSocket servidor = new ServerSocket(puerto);
            System.out.println("Servidor iniciado en el puerto " + puerto);

            while (true) {
                Socket cliente = servidor.accept();
                System.out.println("Cliente conectado desde " + cliente.getInetAddress().getHostName());

                ObjectInputStream entrada = new ObjectInputStream(cliente.getInputStream());
                double[][] A1 = (double[][]) entrada.readObject();
                double[][] B1 = (double[][]) entrada.readObject();
                double[][] B2 = (double[][]) entrada.readObject();
                double[][] B3 = (double[][]) entrada.readObject();

                System.out.println("Matriz A1 recibida:");
                mostrarMatriz(A1);

                System.out.println("Matriz B1 recibida:");
                mostrarMatriz(B1);

                System.out.println("Matriz B2 recibida:");
                mostrarMatriz(B2);

                System.out.println("Matriz B3 recibida:");
                mostrarMatriz(B3);

                cliente.close();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void mostrarMatriz(double[][] matriz) {
        for (double[] fila : matriz) {
            for (double elemento : fila) {
                System.out.print(elemento + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
