import java.io.*;
import java.net.*;
import java.util.*;





public class Server {
    private static final int PORT = 5555;
    private static Set&lt;ClientHandler&gt; clientHandlers = new HashSet&lt;&gt;();




    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.o
              
              
              
              
              ut.println("Chat Server started on port " + PORT);
            
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket);
                
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                clientHandlers.add(clientHandler);
                new Thread(clientHandler).start();
            }
        }
          
          
          catch (IOException e) {
            System.out.println("Server exception: " + e.getMessage());
            e.printStackTrace();
        }
    }



    public static void broadcast(String message, ClientHandler excludeClient) {
        for (ClientHandler client : clientHandlers) {
            if (client != excludeClient) {
                client.sendMessage(message);
            }
        }
    }



    public static void removeClient(ClientHandler client) {
        clientHandlers.remove(client);
        System.out.println("Client disconnected: " + client.getClientUsername());
    }

    private static class ClientHandler implements Runnable {
        private Socket clientSocket;
        private PrintWriter out;
        private BufferedReader in;
        private String clientUsername;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        public String getClientUsername() {
            return clientUsername;
        }

        @Override
        public void run() {
            try {
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                // Get the name or username
                out.println("Enter your username: ");
                clientUsername = in.readLine();
                System.out.println(clientUsername + " has joined the chat.");
                broadcast(clientUsername + " has joined the chat!", this);

                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    if (inputLine.equals("/quit")) {
                        break;
                    }
                    String formattedMessage = "[" + clientUsername + "]: " + inputLine;
                    broadcast(formattedMessage, this);
                }
            } catch (IOException e) {
                System.out.println("Error in ClientHandler: " + e.getMessage());
            } finally {
                try {
                    if (clientSocket != null) {
                        clientSocket.close();
                    }
                } catch (IOException e) {
                    System.out.println("Error closing socket: " + e.getMessage());
                }
                removeClient(this);
                broadcast(clientUsername + " has left the chat.", this);
            }
        }

      //print ,,,,,
        public void sendMessage(String message) {
            out.println(message);
        }
    }
}

