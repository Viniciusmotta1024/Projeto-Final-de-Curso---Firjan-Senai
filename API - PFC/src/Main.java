import controller.ApiServidor;

public class Main {

    public static void main(String[] args) {

        try {

            ApiServidor.iniciar();

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

}