//import java.io.BufferedWriter;
import java.io.IOException;
//import java.io.OutputStreamWriter;
//import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient implements Runnable{

    private final String SERVER_ADDRESS = "127.0.0.1";
    private ClientSocket clientSocket;
    private Scanner scanner;

    public ChatClient(){
        scanner = new Scanner(System.in);
    }

    public void start() throws IOException{

        //this.out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        // OutputStream - byte []
        // OutputStreamWriter - char []
        // BufferedWriter - String + nextline()
        //this.out = new PrintWriter(clientSocket.getOutputStream(), true);

        try{
            clientSocket = new ClientSocket(new Socket(SERVER_ADDRESS, ChatServer.PORT));
            System.out.println("Cliente conectado ao servidor em: " + SERVER_ADDRESS + ":" + ChatServer.PORT);
            new Thread(this).start();
            messageLoop();
        } finally{
            clientSocket.close();
        }
    }

    // mostra a mensagem sempre que recebe
    @Override
    public void run() {
        String msg;
        while ((msg = clientSocket.getMsg()) != null) {
            System.out.println("Mensagem recebida do servidor: "
            + msg);
        }
    }

    private void messageLoop() throws IOException{
        String msg;
        do {
            System.out.println("Digite uma mensagem (ou sair para finalizar): ");
            msg = scanner.nextLine();

            //out.write(msg); BufferedWriter
            //out.newLine(); BufferedWriter
            //out.flush(); // garante que todo dado do buffer foi escrito, com o autoflush:true se torna automatico a cada println

           // clientSocket.sendMsg(msg);
        } while (!msg.equalsIgnoreCase("sair") && clientSocket.sendMsg(msg));
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
