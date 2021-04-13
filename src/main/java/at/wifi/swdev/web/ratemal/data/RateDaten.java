package at.wifi.swdev.web.ratemal.data;

import java.io.Serializable;
import java.util.Random;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class RateDaten implements Serializable {

  private int zufallsZahl;
  private int anzahlDerVersuche;

  public void init() {
    Random random = new Random();
    anzahlDerVersuche = 0;
    zufallsZahl = random.nextInt(10) + 1;
    System.out.println("Cheat: Zufallszahl " + zufallsZahl);
  }

  public int getZufallsZahl() {
    return zufallsZahl;
  }

  public int getAnzahlDerVersuche() {
    return anzahlDerVersuche;
  }

  public void incAnzahlderVersuche() {
    this.anzahlDerVersuche++;
  }

}
