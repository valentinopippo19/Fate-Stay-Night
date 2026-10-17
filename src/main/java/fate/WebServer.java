package fate;

import com.sun.net.httpserver.*;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Servidor HTTP sin framework externo. Expone datos, algoritmos y combate
 * interactivo al frontend.
 */
public class WebServer {
  static HttpServer server;
  static final String WEB_ROOT="web";
  static final Map<String,Battle> BATTLES=new ConcurrentHashMap<>();

  public static void main(String[] a)throws Exception{int port=iniciar(8080);System.out.println("Web: http://localhost:"+port);}

  public static synchronized int iniciar(int preferredPort)throws Exception{
    if(server!=null)return server.getAddress().getPort();
    Exception last=null;
    for(int port=preferredPort;port<preferredPort+20;port++){
      try{server=HttpServer.create(new InetSocketAddress(port),0);last=null;break;}catch(IOException ex){last=ex;}
    }
    if(server==null)throw last;
    server.createContext("/api/personajes",e->safe(e,WebServer::characters));
    server.createContext("/api/algoritmos",e->safe(e,WebServer::algorithms));
    server.createContext("/api/complejidad",e->safe(e,WebServer::complexity));
    server.createContext("/api/combate/iniciar",e->safe(e,WebServer::combatStart));
    server.createContext("/api/combate/accion",e->safe(e,WebServer::combatAction));
    server.createContext("/api/combate/estado",e->safe(e,WebServer::combatState));
    server.createContext("/",e->safe(e,WebServer::staticFile));
    server.setExecutor(null);server.start();
    return server.getAddress().getPort();
  }

  interface Handler{void run(HttpExchange e)throws Exception;}
  static void safe(HttpExchange e,Handler h){try{if("OPTIONS".equalsIgnoreCase(e.getRequestMethod())){cors(e);e.sendResponseHeaders(204,-1);return;}h.run(e);}catch(Exception ex){try{respond(e,"{\"error\":\""+q(ex.getMessage()==null?"Error interno":ex.getMessage())+"\"}","application/json",400);}catch(Exception ignored){}}}

  static void characters(HttpExchange e)throws IOException{if(!"GET".equalsIgnoreCase(e.getRequestMethod())){respond(e,"{\"error\":\"GET requerido\"}","application/json",405);return;}StringBuilder s=new StringBuilder("[");for(int i=0;i<Datos.N;i++){if(i>0)s.append(',');s.append("{\"id\":").append(i)
    .append(",\"nombre\":\"").append(q(Datos.NOMBRE[i])).append("\"")
    .append(",\"identidad\":\"").append(q(Datos.IDENTIDAD[i])).append("\"")
    .append(",\"clase\":\"").append(q(Datos.CLASE[i])).append("\"")
    .append(",\"poder\":").append(Datos.PODER[i]).append(",\"costo\":").append(Datos.COSTO[i]).append(",\"mana\":").append(Datos.MANA[i]).append(",\"vida\":").append(Datos.VIDA[i])
    .append(",\"defensa\":\"").append(q(Datos.DEFENSA[i])).append("\"")
    .append(",\"master\":\"").append(q(Datos.MASTER[i])).append("\"")
    .append(",\"arma\":\"").append(q(Datos.ARMA[i])).append("\"")
    .append(",\"noble\":\"").append(q(Datos.NOBLE[i])).append("\"")
    .append(",\"habilidades\":\"").append(q(Datos.HABILIDADES[i])).append("\"")
    .append(",\"historia\":\"").append(q(Datos.HISTORIA[i])).append("\"")
    .append(",\"imagen\":\"").append(q(Datos.IMAGEN[i])).append("\"")
    .append(",\"video\":\"").append(q(Datos.VIDEO[i])).append("\"")
    .append(",\"color\":\"").append(q(Datos.COLOR[i])).append("\"}");
  }respond(e,s.append(']').toString(),"application/json",200);}

  static void algorithms(HttpExchange e)throws IOException{
    String tipo=query(e,"tipo");StringBuilder out=new StringBuilder();
    switch(tipo==null?"all":tipo){
      case "merge","quick"->out.append(Algoritmos.sortTrace(tipo));
      case "greedy"->{int p=intQuery(e,"presupuesto",25);int[]r=Algoritmos.greedy(p,out);out.append("\\nEquipo: ").append(Arrays.toString(Algoritmos.names(r))).append("\\nPoder: ").append(Algoritmos.suma(r,Datos.PODER));}
      case "dp"->{int p=intQuery(e,"presupuesto",25);int[]r=Algoritmos.mochila(p,out);out.append("\\nEquipo óptimo: ").append(Arrays.toString(Algoritmos.names(r))).append("\\nPoder: ").append(Algoritmos.suma(r,Datos.PODER));}
      case "backtracking"->out.append(Algoritmos.queens(8)).append("\\n\\n").append(teamBacktracking());
      case "bfs"->{int id=intQuery(e,"start",0);int[]r=Algoritmos.bfs(id,out);out.append("\\nOrden: ").append(Arrays.toString(Algoritmos.names(r)));}
      case "dfs"->{int id=intQuery(e,"start",0);int[]r=Algoritmos.dfs(id,out);out.append("\\nOrden: ").append(Arrays.toString(Algoritmos.names(r)));}
      case "dijkstra"->{int id=intQuery(e,"start",0);Algoritmos.dijkstra(id,out);}
      case "floyd"->Algoritmos.floyd(out);
      case "prim"->out.append(Algoritmos.mst("prim"));
      case "kruskal"->out.append(Algoritmos.mst("kruskal"));
      case "cycle"->out.append("DETECCIÓN DE CICLOS EN GRAFO\\n\\nResultado: ").append(Algoritmos.hasCycle()?"se detectó un ciclo.":"grafo acíclico.");
      case "huffman"->Algoritmos.huffman(Datos.HISTORIA[intQuery(e,"start",0)],out);
      case "minimax"->out.append(Algoritmos.minimaxDemo(intQuery(e,"depth",3)));
      case "branch"->{int p=intQuery(e,"presupuesto",25);int[]r=Algoritmos.branchAndBound(p,out);out.append("\\nCandidatos encontrados: ").append(Arrays.toString(Algoritmos.names(r))).append("\\nPoder: ").append(Algoritmos.suma(r,Datos.PODER));}
      case "combo"->{int id=intQuery(e,"start",0);Algoritmos.maxComboDP(id,Datos.MANA[id],out);}
      default->out.append("Laboratorio disponible: MergeSort, QuickSort, Greedy, DP, Backtracking/Ocho Reinas, BFS, DFS, Dijkstra, Floyd, Prim, Kruskal, ciclos, Huffman, Minimax/Alfa-Beta, Branch & Bound y DP de combos.");
    }
    respond(e,"{\"tipo\":\""+q(tipo==null?"all":tipo)+"\",\"resultado\":\""+q(out.toString())+"\"}","application/json",200);
  }

  static String teamBacktracking(){StringBuilder s=new StringBuilder();int[]r=Algoritmos.backtracking(4,3300,s);return "BACKTRACKING — EQUIPO\\n"+Arrays.toString(Algoritmos.names(r))+"\\nPoder: "+Algoritmos.suma(r,Datos.PODER)+"\\n\\nTraza:\\n"+s;}
  static void complexity(HttpExchange e)throws IOException{
    String body="{\"medidas\":[{\"nombre\":\"MergeSort\",\"tecnica\":\"Divide y conquista\",\"tiempo\":\"O(n log n)\",\"espacio\":\"O(n)\"},{\"nombre\":\"QuickSort\",\"tecnica\":\"Divide y conquista\",\"tiempo\":\"O(n log n) promedio / O(n²) peor\",\"espacio\":\"O(log n) promedio\"},{\"nombre\":\"Greedy\",\"tecnica\":\"Voraz\",\"tiempo\":\"O(n log n)\",\"espacio\":\"O(n)\"},{\"nombre\":\"Mochila 0/1\",\"tecnica\":\"Programación dinámica\",\"tiempo\":\"O(n·P)\",\"espacio\":\"O(n·P)\"},{\"nombre\":\"BFS\",\"tecnica\":\"Grafos\",\"tiempo\":\"O(V+E)\",\"espacio\":\"O(V)\"},{\"nombre\":\"DFS\",\"tecnica\":\"Grafos\",\"tiempo\":\"O(V+E)\",\"espacio\":\"O(V)\"},{\"nombre\":\"Dijkstra\",\"tecnica\":\"Grafos\",\"tiempo\":\"O(V²)\",\"espacio\":\"O(V)\"},{\"nombre\":\"Floyd\",\"tecnica\":\"Grafos\",\"tiempo\":\"O(V³)\",\"espacio\":\"O(V²)\"},{\"nombre\":\"Prim\",\"tecnica\":\"Greedy sobre grafos\",\"tiempo\":\"O(V²)\",\"espacio\":\"O(V)\"},{\"nombre\":\"Kruskal\",\"tecnica\":\"Greedy sobre grafos\",\"tiempo\":\"O(E log E)\",\"espacio\":\"O(V)\"},{\"nombre\":\"Backtracking\",\"tecnica\":\"Exploración exhaustiva\",\"tiempo\":\"Exponencial peor caso\",\"espacio\":\"Depende de profundidad\"},{\"nombre\":\"Minimax + alfa-beta\",\"tecnica\":\"Búsqueda en juegos\",\"tiempo\":\"O(b^d) peor, mejora con poda\",\"espacio\":\"O(d) DFS\"},{\"nombre\":\"Branch & Bound\",\"tecnica\":\"Ramificación y poda\",\"tiempo\":\"Exponencial peor caso\",\"espacio\":\"Depende de la búsqueda\"},{\"nombre\":\"Huffman\",\"tecnica\":\"Greedy\",\"tiempo\":\"O(n log n)\",\"espacio\":\"O(n)\"}]}";
    respond(e,body,"application/json",200);
  }

  static void combatStart(HttpExchange e)throws IOException{
    if(!"POST".equalsIgnoreCase(e.getRequestMethod())){respond(e,"{\"error\":\"POST requerido\"}","application/json",405);return;}
    Map<String,String>m=parse(new String(e.getRequestBody().readAllBytes(),StandardCharsets.UTF_8));
    int a=Integer.parseInt(m.getOrDefault("a","0")),b=Integer.parseInt(m.getOrDefault("b","1"));if(a==b)throw new IllegalArgumentException("Elegí dos Servants diferentes.");
    String id=UUID.randomUUID().toString();Battle x=new Battle(id,a,b);BATTLES.put(id,x);respond(e,x.json(),"application/json",200);
  }

  static void combatAction(HttpExchange e)throws IOException{
    if(!"POST".equalsIgnoreCase(e.getRequestMethod())){respond(e,"{\"error\":\"POST requerido\"}","application/json",405);return;}
    Map<String,String>m=parse(new String(e.getRequestBody().readAllBytes(),StandardCharsets.UTF_8));Battle x=BATTLES.get(m.get("id"));if(x==null)throw new IllegalArgumentException("Combate no encontrado.");
    x.action(m.getOrDefault("action","attack"));respond(e,x.json(),"application/json",200);
  }

  static void combatState(HttpExchange e)throws IOException{Battle x=BATTLES.get(query(e,"id"));if(x==null)throw new IllegalArgumentException("Combate no encontrado.");respond(e,x.json(),"application/json",200);}

  static final class Battle{
    final String id;final int a,b;int hpA,hpB,manaA,manaB,turn=1;boolean defendA=false,defendB=false,finished=false;String winner="";final StringBuilder log=new StringBuilder();
    Battle(String id,int a,int b){this.id=id;this.a=a;this.b=b;hpA=Datos.VIDA[a];hpB=Datos.VIDA[b];manaA=Datos.MANA[a];manaB=Datos.MANA[b];log.append("COMBATE INICIADO — ").append(Datos.NOMBRE[a]).append(" VS ").append(Datos.NOMBRE[b]).append("\\n");}
    synchronized void action(String act){
      if(finished)return;
      int action=switch(act.toLowerCase()){case "special"->2;case "defend"->3;default->1;};
      if(action==2&&manaA<25)action=1;
      int dmg=Algoritmos.damage(a,b,action,System.nanoTime());
      if(defendB)dmg=Math.max(5,dmg/2);
      if(action==3){defendA=true;log.append("T").append(turn).append(": ").append(Datos.NOMBRE[a]).append(" adopta defensa.\\n");}
      else {hpB=Math.max(0,hpB-dmg);if(action==2)manaA-=25;log.append("T").append(turn).append(": ").append(Datos.NOMBRE[a]).append(action==2?" usa NOBLE PHANTASM":" ataca").append(" por ").append(dmg).append(" de daño.\\n");}
      defendB=false;
      if(hpB<=0){finish(a);return;}
      int ai=Algoritmos.aiAction(hpB,hpA,manaB,manaA,Datos.PODER[b],Datos.PODER[a]);
      int aiDmg=Algoritmos.damage(b,a,ai,System.nanoTime());
      if(defendA)aiDmg=Math.max(5,aiDmg/2);
      if(ai==3){defendB=true;log.append("T").append(turn).append(": ").append(Datos.NOMBRE[b]).append(" se defiende (MINIMAX).\\n");}
      else {hpA=Math.max(0,hpA-aiDmg);if(ai==2)manaB=Math.max(0,manaB-25);log.append("T").append(turn).append(": ").append(Datos.NOMBRE[b]).append(ai==2?" usa NOBLE PHANTASM (MINIMAX)":" responde").append(" por ").append(aiDmg).append(" de daño.\\n");}
      defendA=false;
      if(hpA<=0)finish(b);turn++;
    }
    void finish(int w){finished=true;winner=Datos.NOMBRE[w];log.append("VICTORIA: ").append(winner).append("\\n");}
    String json(){return "{\"id\":\""+q(id)+"\",\"a\":\""+q(Datos.NOMBRE[a])+"\",\"b\":\""+q(Datos.NOMBRE[b])+"\",\"hpA\":"+hpA+",\"hpB\":"+hpB+",\"maxA\":"+Datos.VIDA[a]+",\"maxB\":"+Datos.VIDA[b]+",\"manaA\":"+manaA+",\"manaB\":"+manaB+",\"turn\":"+turn+",\"finished\":"+finished+",\"winner\":\""+q(winner)+"\",\"log\":\""+q(log.toString())+"\"}";}
  }

  static Map<String,String> parse(String s){Map<String,String>m=new HashMap<>();s=s.trim();if(s.startsWith("{"))s=s.substring(1);if(s.endsWith("}"))s=s.substring(0,s.length()-1);for(String p:s.split(",")){String[]x=p.split(":",2);if(x.length==2)m.put(x[0].replace("\"","").trim(),x[1].replace("\"","").trim());}return m;}
  static String query(HttpExchange e,String key){String q=e.getRequestURI().getRawQuery();if(q==null)return null;for(String p:q.split("&")){String[]x=p.split("=",2);if(x.length==2&&URLDecoder.decode(x[0],StandardCharsets.UTF_8).equals(key))return URLDecoder.decode(x[1],StandardCharsets.UTF_8);}return null;}
  static int intQuery(HttpExchange e,String key,int def){try{String s=query(e,key);return s==null?def:Integer.parseInt(s);}catch(Exception ex){return def;}}
  static String q(String s){if(s==null)return "";return s.replace("\\","\\\\").replace("\"","\\\"").replace("\r","").replace("\n","\\n");}
  static void cors(HttpExchange e){e.getResponseHeaders().set("Access-Control-Allow-Origin","*");e.getResponseHeaders().set("Access-Control-Allow-Headers","Content-Type");e.getResponseHeaders().set("Access-Control-Allow-Methods","GET,POST,OPTIONS");}
  static void respond(HttpExchange e,String body,String type,int status)throws IOException{byte[]b=body.getBytes(StandardCharsets.UTF_8);cors(e);e.getResponseHeaders().set("Content-Type",type+"; charset=UTF-8");e.sendResponseHeaders(status,b.length);try(OutputStream o=e.getResponseBody()){o.write(b);}}
  static void staticFile(HttpExchange e)throws IOException{
    String p=e.getRequestURI().getPath();
    if(p.equals("/"))p="/index.html";
    String relative=p.substring(1);
    if(relative.contains("..")||relative.startsWith("/")||relative.indexOf('\\')>=0){e.sendResponseHeaders(404,-1);return;}
    String resource=WEB_ROOT+"/"+relative;
    ClassLoader cl=WebServer.class.getClassLoader();
    try(InputStream in=cl.getResourceAsStream(resource)){
      if(in==null){e.sendResponseHeaders(404,-1);return;}
      String ct=contentType(relative);
      e.getResponseHeaders().set("Content-Type",ct);
      e.sendResponseHeaders(200,0);
      try(OutputStream o=e.getResponseBody()){in.transferTo(o);}
    }
  }

  static String contentType(String p){String x=p.toLowerCase(Locale.ROOT);if(x.endsWith(".html"))return"text/html; charset=UTF-8";if(x.endsWith(".js"))return"application/javascript; charset=UTF-8";if(x.endsWith(".css"))return"text/css; charset=UTF-8";if(x.endsWith(".json"))return"application/json; charset=UTF-8";if(x.endsWith(".jpg")||x.endsWith(".jpeg"))return"image/jpeg";if(x.endsWith(".png"))return"image/png";if(x.endsWith(".gif"))return"image/gif";if(x.endsWith(".svg"))return"image/svg+xml";if(x.endsWith(".mp4"))return"video/mp4";if(x.endsWith(".webm"))return"video/webm";if(x.endsWith(".txt"))return"text/plain; charset=UTF-8";return"application/octet-stream";}
}
