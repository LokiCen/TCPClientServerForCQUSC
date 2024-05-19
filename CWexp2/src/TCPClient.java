import java.io.*;
import java.net.*;

class TCPClient {
    public static void main(String argv[]) throws Exception {
        String sentence;
        String modifiedSentence;
        Socket clientSocket = new Socket("localhost", 2525);
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

        // Send userID
        sentence = "HELO 2022141620221578";
        outToServer.writeBytes(sentence + '\n');

        // Receive response from server
        modifiedSentence = inFromServer.readLine();
        System.out.println("FROM SERVER: " + modifiedSentence);

        // Check if the server prompted for password
        if ("500 AUTH REQUIRED!".equals(modifiedSentence.trim())) {
            // Send password
            sentence = "PASS 123456"; // Replace with the correct password
            outToServer.writeBytes(sentence + '\n');

            // Receive response from server
            modifiedSentence = inFromServer.readLine();
            System.out.println("FROM SERVER: " + modifiedSentence);

            // If password verification is successful, start sending commands
            if ("525 OK!".equals(modifiedSentence.trim())) {
                do {
                    sentence = inFromUser.readLine();
                    outToServer.writeBytes(sentence + '\n');
                    modifiedSentence = inFromServer.readLine();
                    System.out.println("FROM SERVER: " + modifiedSentence);
                } while (!sentence.equalsIgnoreCase("BYE"));
            }
        }

        clientSocket.close();
    }
}
