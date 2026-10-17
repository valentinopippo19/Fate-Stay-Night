package fate;

import java.awt.Desktop;
import java.awt.GraphicsEnvironment;
import java.net.URI;

public class Main {
  public static void main(String[] args) {
    try {
      int port = WebServer.iniciar(8080);
      String url = "http://localhost:" + port;
      System.out.println("FATE Algorithm War: " + url);
      try { if (!GraphicsEnvironment.isHeadless() && Desktop.isDesktopSupported()) Desktop.getDesktop().browse(URI.create(url)); } catch (Throwable ignored) {}
    } catch (Exception e) { e.printStackTrace(); }
  }
}
