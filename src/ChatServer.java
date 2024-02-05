import java.io.IOException;
import java.net.ServerSocket;

public class ChatServer {

    public static final int PORT = 4000;
    private ServerSocket serverSocket;

    public void start() throws IOException{
        serverSocket = new ServerSocket(PORT);
        System.out.println("Servidor iniciado na porta: " + PORT);
        clientConnectionLoop();
    }

    private void clientConnectionLoop() throws IOException{
        while (true) {
            ClientSocket clientSocket = new ClientSocket(serverSocket.accept()); // blocking

            new Thread(() -> clientMessageLoop(clientSocket)).start();
        }
    }

    public void clientMessageLoop(ClientSocket clientSocket){
        String msg;
        try{
            while ((msg = clientSocket.getMsg()) != null) {
                if ("sair".equals(msg)) {
                    return;
                }
                System.out.printf("Msg recebida do cliente %s: %s\n", 
                clientSocket.getRemoteSocketAddress(),
                msg);
            }
        } finally{
            clientSocket.close();
        }
    }

    public static void main(String[] args) {

        try {
            ChatServer server = new ChatServer();
            server.start();
        } catch (IOException e) {
            System.out.println("Erro ao iniciar o servidor: " + e.getMessage());
        }

        System.out.println("Servidor finalizado");
    }
}