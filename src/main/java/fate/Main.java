package fate;
public class Main{public static void main(String[]args){if(args.length>0&&args[0].equalsIgnoreCase("web")){try{WebServer.iniciar(8080);System.out.println("http://localhost:8080");}catch(Exception e){e.printStackTrace();}}else DesktopApp.mostrar();}}
