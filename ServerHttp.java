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

class ServerHttp {
    public static void main(String argv[]) throws Exception
    {
		int i =0;
        //On installe le combine sur le numero de telephone
        ServerSocket serversocket = new ServerSocket(8080);

        //On attend les appels entrants
        Socket socket = serversocket.accept();

        InputStreamReader inputStream = new InputStreamReader( socket.getInputStream() );
		BufferedReader input = new BufferedReader(inputStream );
	    PrintStream out = new PrintStream(socket.getOutputStream());
		
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
		System.out.print(""+name[0]);
		File file =null;
		try{
			file = new File(name[0]);
			FileInputStream inputstream = new FileInputStream(file);
			long fileLength = file.length();
			byte[] filecontent = new byte[(int)fileLength];
			int data = inputstream.read(filecontent);
			while(data != -1){
				data = inputstream.read(filecontent);
			}
			if(get[0].equals("GET /"+name[0]+" HTTP/1.1")){
			//content-type: text/htm
			//				application/pdf
			//				image/png
				String s ="HTTP/1.1 200 OK\r\n";
				byte b[] = s.getBytes();
		   	 	String s1 = "Content-Type: image/png\r\n";
				byte b1[] =s1.getBytes();
				String s2 = "\r\n";
				byte b2[] = s2.getBytes();
				out.write(b);
				out.write(b1);
				out.write(b2);
				out.write(filecontent);
				out.flush();
			}

		}catch(FileNotFoundException s){
				String s3 ="HTTP/1.1 404 Not found\r\n";
				byte b3[] = s3.getBytes();
		   	 	String s4 = "Content-Type: text/html\r\n";
				byte b4[] =s4.getBytes();
				String s5 = "\r\n";
				byte b5[] = s5.getBytes();
		   	 	String s6 = "<p>404 Not Found<p>\r\n";
				byte b6[] =s6.getBytes();
				out.write(b3);
				out.write(b4);
				out.write(b5);
				out.write(b6);
				out.flush();
		}
		//On raccroche
        socket.close();
    }
}
