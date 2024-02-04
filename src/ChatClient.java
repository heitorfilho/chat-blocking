//import java.io.BufferedWriter;
import java.io.IOException;
//import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {

    private final String SERVER_ADDRESS = "127.0.0.1";
    private Socket clienSocket;
    private Scanner scanner;
    private PrintWriter out;

    public ChatClient(){
        scanner = new Scanner(System.in);
    }

    public void start() throws IOException{
        clienSocket = new Socket(SERVER_ADDRESS, ChatServer.PORT);

        //this.out = new BufferedWriter(new OutputStreamWriter(clienSocket.getOutputStream()));
        // OutputStream - byte []
        // OutputStreamWriter - char []
        // BufferedWriter - String + nextline()

        this.out = new PrintWriter(clienSocket.getOutputStream(), true);

        System.out.println("Cliente conectado ao servidor em: " + SERVER_ADDRESS + ":" + ChatServer.PORT);
        messageLoop();
    }

    private void messageLoop() throws IOException{
        String msg;
        do {
            System.out.print("Digite uma mensagem (ou sair para finalizar): ");
            msg = scanner.nextLine();

            //out.write(msg); BufferedWriter
            //out.newLine(); BufferedWriter

            out.println(msg);
            //out.flush(); // garante que todo dado do buffer foi escrito, com o autoflush:true se torna automatico a cada println

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
