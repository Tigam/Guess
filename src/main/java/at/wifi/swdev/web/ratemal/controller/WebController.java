package at.wifi.swdev.web.ratemal.controller;

import at.wifi.swdev.web.ratemal.data.RateDaten;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WebController {

  @Autowired
  private RateDaten daten;
  
  private int gewonnenZaehler;
  

  public WebController() {
    // Initialisierung
    //daten = new RateDaten(); // macht das Framework für SessionScope
  }

  @PostConstruct
  public void init() {
    // Wird ausgeführt, wenn alle Framework Aufgaben erledigt sind.
    gewonnenZaehler = 0;
  }

  @GetMapping({"/", "/welcome"})
  public String doWelcome() {
    return "welcome"; // templates/welcome.html
  }

  @GetMapping("/raten")
  public String doRaten() {
    daten.init();
    return "raten"; // templates/raten.html
  }

  @PostMapping("/raten")
  public String handleRaten(Model model,
          @RequestParam(name = "txtInput") String eingabe) {

    System.out.println("Eingabe: " + eingabe);
    // Abfrage ob Zahl richtig...
    int zahl;
    try {
      daten.incAnzahlderVersuche();
      zahl = Integer.parseInt(eingabe);
      if (zahl < 1 || zahl > 10) {
        throw new NumberFormatException("Außerhalb des Bereichs!");
      }
    } catch (NumberFormatException nfe) {
      model.addAttribute("anzahlVersuche", daten.getAnzahlDerVersuche());
      model.addAttribute("errorMessage", "Keine Zahl oder außerhalb des Bereichs!");
      return "raten"; // templates/raten.html
    }

    if (zahl == daten.getZufallsZahl()) {
      gewonnenZaehler++;
      model.addAttribute("anzahlVersuche", daten.getAnzahlDerVersuche());
      String imageURL = "images/winner_" + (gewonnenZaehler % 3) +".gif";
      model.addAttribute("imageURL", imageURL);
      return "gewonnen"; // templates/gewonnen.html
    } else {
      model.addAttribute("errorMessage", "Falsche Zahl, bitte weiter raten!");
      model.addAttribute("anzahlVersuche", daten.getAnzahlDerVersuche());
      return "raten"; // templates/raten.html
    }
  }

}
