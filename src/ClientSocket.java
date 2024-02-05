import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketAddress;

public class ClientSocket {
    private final Socket socket;
    private final BufferedReader in;
    private final PrintWriter out;

    public ClientSocket(Socket socket) throws IOException{
        this.socket = socket;

        System.out.println("Cliente: " + socket.getRemoteSocketAddress() + " conectou");

        this.out = new PrintWriter(socket.getOutputStream(), true);
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public SocketAddress getRemoteSocketAddress(){
        return socket.getRemoteSocketAddress();
    }

    public void close(){
        try {
            socket.close();
            in.close();
            out.close();
        } catch (IOException e) {
            System.out.println("Erro ao fechar socket: " + e.getMessage());
        }

    }

    public String getMsg(){

        try {
            return in.readLine();
        } catch (IOException e) {
            return null;
        }
    }

    public boolean sendMsg(String msg){
        out.println(msg);
        return !out.checkError();
    }
}
