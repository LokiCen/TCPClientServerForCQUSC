import java.io.*;
import java.net.*;

class TCPClient1 {
    public static void main(String argv[]) throws Exception {
        String sentence;
        String modifiedSentence;

        try (Socket clientSocket = new Socket("localhost", 2525)) {
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

            // Prompt for userID
            System.out.print("Enter userID: ");
            sentence = inFromUser.readLine();
            outToServer.writeBytes(sentence + '\n');

            // Receive response from server
            modifiedSentence = inFromServer.readLine();
            System.out.println("FROM SERVER: " + modifiedSentence);

            // Check if the server prompted for password
            if ("500 AUTH REQUIRED!".equals(modifiedSentence.trim())) {
                // Prompt for password
                System.out.print("Enter password: ");
                sentence = inFromUser.readLine();
                outToServer.writeBytes(sentence + '\n');

                // Receive response from server
                modifiedSentence = inFromServer.readLine();
                System.out.println("FROM SERVER: " + modifiedSentence);

                // If password verification is successful, start sending commands
                if ("525 OK!".equals(modifiedSentence.trim())) {
                    boolean done = false;
                    while (!done) {
                        System.out.print("Enter command: ");
                        sentence = inFromUser.readLine();
                        outToServer.writeBytes(sentence + '\n');

                        modifiedSentence = inFromServer.readLine();
                        System.out.println("FROM SERVER: " + modifiedSentence);

                        if ("BYE".equalsIgnoreCase(sentence.trim())) {
                            done = true;
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
