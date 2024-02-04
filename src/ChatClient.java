import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {

    private final String SERVER_ADDRESS = "127.0.0.1";
    private Socket clienSocket;
    private Scanner scanner;

    public ChatClient(){
        scanner = new Scanner(System.in);
    }

    public void start() throws IOException{
        clienSocket = new Socket(SERVER_ADDRESS, ChatServer.PORT);
        clienSocket.getOutputStream(); // fluxo de saida de dados
        System.out.println("Cliente conectado ao servidor em: " + SERVER_ADDRESS + ":" + ChatServer.PORT);
        messageLoop();
    }

    private void messageLoop(){
        String msg;
        do {
            System.out.print("Digite uma mensagem (ou sair para finalizar): ");
            msg = scanner.nextLine();

        } while (!msg.equalsIgnoreCase("sair"));
    }

    public static void main(String[] args) {

        try {
            ChatClient client = new ChatClient();
            client.start();
        } catch (IOException e) {
            System.out.println("Erro ao iniciar o servidor: " + e.getMessage());
        }

        System.out.println("Cliente finalizado");
    }
}
