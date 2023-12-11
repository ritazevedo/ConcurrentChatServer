package org.codeforall.iorns;

import java.io.*;
import java.net.Socket;

import static org.codeforall.iorns.ChatServer.PORTNUMBER;

public class Client implements Runnable {

    public static void main(String[] args) {

            try {

                String hostname = "localhost";

                Socket clientSocket = new Socket(hostname, PORTNUMBER);


                PrintWriter out = new PrintWriter((clientSocket.getOutputStream()), true);
                BufferedWriter bufferedWriter = new BufferedWriter(out);

                String line = "";
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

                while (!line.equals("exit")) {

                    line = bufferedReader.readLine();

                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    bufferedWriter.flush();

                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }



    }

    @Override
    public void run() {

    }
}
