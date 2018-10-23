/*
Server HTTP en JAVA:

-creer un serveur sur le port 8080
-récupérer la requête du client et l'afficher
	-forger à la main avec telnet
	-avec un vrai navigateur
-3 fichiers (avec gestion des erreurs minimal)
	-un fichier text/html
	-un fichier pdf
	-un fichier PNG
Protocol HTTP/1.1
GET /toto.html
GET /toto.pdf
GET /toto.png
*/
import java.net.*;
import java.io.*;

class HelloServerHttp {
    public static void main(String argv[]) throws Exception
    {
		int i =0;
        //On installe le combine sur le numero de telephone
        ServerSocket serversocket = new ServerSocket(8080);

        //On attend les appels entrants
        Socket socket = serversocket.accept();

        InputStreamReader inputStream = new InputStreamReader( socket.getInputStream() );
		BufferedReader input = new BufferedReader(inputStream );
	    PrintWriter out = new PrintWriter(socket.getOutputStream());
		
		String header="";
		while(true){
			String line = input.readLine();
			header+=""+line+"\n";
			if(line.equals("")){
				break;
			}
		}
			
		System.out.print(header);
		String []get =header.split("\n");
		String []f=header.split(" /");
		String f1 =f[1];
		String []name=f1.split(" ");
		File file =null;
			StringBuilder sb = new StringBuilder();
		try{
			file = new File(name[0]);
			BufferedReader thefile = new BufferedReader(new FileReader(file));
			String fileLine = thefile.readLine();
				while (fileLine != null) {
		    		sb.append(fileLine);
		        	sb.append("\n");
		        	fileLine = thefile.readLine();
		         }
		}catch(FileNotFoundException s){
				out.print("HTTP/1.1 404 Not Found");
		    	out.print("Content-Type: text/html");
				out.print("\r\n");
				out.print("<p> ERROR 404 file not found !!</p>");
		}
		if(get[0].equals("GET /toto.html HTTP/1.1")){
			out.println("HTTP/1.1 200 OK");
		    out.println("Content-Type: text/html");
			out.println("\r\n");
			out.println("<p> Text sent </p>"+sb);
			out.flush();
		}
		//On raccroche
        socket.close();
    }
}
