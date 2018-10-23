import java.net.*;
import java.io.*;

class HelloServer2 {
    public static void main(String argv[]) throws Exception
    {
		int i =0;
        //On installe le combine sur le numero de telephone
        ServerSocket serversocket = new ServerSocket(1111);

        //On attend les appels entrants
        Socket socket = serversocket.accept();

        //On ouvre un tube pour envoyer un message
        PrintStream out = new PrintStream( socket.getOutputStream() );

        //une boucle envoyant plusieurs messages
		while(i!=5){
			out.println( "Hello World!" );
			i++;
		}
        //On raccroche
        socket.close();
    }
}
