package org.codeforall.iorns;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ChatServer {

    public static final int PORTNUMBER = 7075;
    private ServerSocket serverSocket;
    private static LinkedList<ServerWorker> list;


    public static void main(String[] args) {

        try {
            ChatServer chatServer = new ChatServer();
            list = new LinkedList<>();
            chatServer.listen(PORTNUMBER);


        } catch (NumberFormatException e) {

            System.exit(1);

        }

    }

    private void listen(int port) {

        try {

            serverSocket = new ServerSocket(port);
            serve(serverSocket);

        } catch (IOException e) {

            System.exit(1);

        }
    }

    private void serve(ServerSocket serverSocket) {

        while (true) {

            try {

                Socket clientSocket = serverSocket.accept();


                ExecutorService executor = Executors.newCachedThreadPool();

                ServerWorker server = new ServerWorker(clientSocket);

                list.add(server);

                executor.execute(server);

            } catch (IOException e) {

                System.exit(1);

            }
        }
    }

    public void sendAll(String message, String name) {

        for (ServerWorker serverWorker : list) {
            if (serverWorker.getName() == name) {
                continue;
            }
            serverWorker.send(message);
        }

    }

    public void sendWhisper(String message, String name) {

        for (ServerWorker serverWorker : list) {
            if (serverWorker.getName().equals(name)) {
                serverWorker.send(message);
            }
        }

    }

    public void sendList(String name) {

        for (ServerWorker serverWorker : list) {
            if (serverWorker.getName().equals(name)) {
                serverWorker.send(String.valueOf(ServerWorker.names));
            }
        }

    }


    public class ServerWorker implements Runnable {

        private Socket socket;
        private BufferedReader in;
        private OutputStream outputStream;
        private String line;
        private String name;
        private static LinkedList<String> names = new LinkedList<>();

        public ServerWorker(Socket socket) throws IOException {
            this.socket = socket;
        }

        public void defineName() throws IOException {

            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            PrintWriter printWriter = new PrintWriter(outputStream, true);

            printWriter.println("Enter your name:");
            name = bufferedReader.readLine();
            names.add(name);


        }

        public void setupStreams() throws IOException {

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            outputStream = socket.getOutputStream();

        }

        public void send(String message) {

            PrintWriter out = new PrintWriter(outputStream, true);
            out.println(message);

        }

        public String getName() {
            return name;
        }

        @Override
        public void run() {

            try {

                defineName();

                line = "";

                while (true) {

                    setupStreams();

                    line = name + " - " + in.readLine();

                    String[] firstLines = line.split(" ", 3);

                    String[] secondLines = firstLines[2].split("-", 3);

                    if (secondLines[0].equals("/whisper")) {
                        String message = name + " - " + secondLines[2];
                        sendWhisper(message, secondLines[1]);
                        continue;
                    }

                    if (secondLines[0].equals("/list")) {
                        sendList(name);
                        continue;
                    }

                    if(secondLines[0].equals("/quit")) {
                        sendAll(name + " has left the chat.", name);
                        names.remove(name);
                        socket.close();
                        return;
                    }

                    sendAll(line, name);
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
