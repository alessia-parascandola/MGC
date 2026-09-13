package it.unicam.cs.mpgc.rpg126692.carte;

import it.unicam.cs.mpgc.rpg126692.carte.Boss.*;
import it.unicam.cs.mpgc.rpg126692.carte.Eventi.*;
import it.unicam.cs.mpgc.rpg126692.dadi.Simbolo;

import java.util.ArrayList;
import java.util.List;

public class MazzoCapitoli {
    private ArrayList<CartaCapitolo> carteCapitolo = new ArrayList<>();
    private List<CartaBoss> carteBoss = new ArrayList<>();
    private CartaIntro cartaIntro;

    public MazzoCapitoli(){
        // Carta Intro
        this.cartaIntro = (new CartaIntro());

        // Carte Mostro
        carteCapitolo.add(new CartaMostro("Mentre passi, una pesante porta di legno \n" +
                "si apre violentemente e un pazzo vestito di stracci si lancia fuori. Impugna le sue catene \n" +
                "a mo' di arma e il suo folle sguardo manda un chiaro messaggio: ti stai frapponendo tra lui e la libertà.",
                "/images/cassero/pazzo_con_catene.png", List.of(Simbolo.FORZA, Simbolo.ASTUZIA), 2));
        carteCapitolo.add(new CartaMostro("Questa sala è fiancheggiata da grotte e disseminata di ossa masticate. \n" +
                "Un verso gutturale risuona mentre quacosa emerge dall'ombra.",
                "/images/cassero/ragno.png", List.of(Simbolo.FORZA, Simbolo.FORZA, Simbolo.ASTUZIA), 2));
        carteCapitolo.add(new CartaMostro("Mentre questre tre megere si avvicinano, noti che i loro piedi non toccano \n" +
                "il terreno. Con una risata stridula, si protendono verso di te \n" +
                "cercandoti con mani rinescchite e lingue marcescenti.",
                "/images/cassero/megere.png", List.of(Simbolo.SAGGEZZA), 1));
        carteCapitolo.add(new CartaMostro("Un nobiluomo sembra fuori posto girovagando nelle viscere del castello. \n" +
                "Offre una ricompensa se prometti di non dire a nessuno di averlo visto. Mentre parli si copre il naso, \n" +
                "visibilmente disgustato dalla tua presenza. Non si fida di te.",
                "/images/cassero/nobiluomo.png", List.of(Simbolo.ASTUZIA), 1));
        carteCapitolo.add(new CartaMostro("Con un lamento straziante, un'orrenda creatura emerge dall'oscurità. \n" +
                "Appena ti vede comincia a sbavare.",
                "/images/cassero/lamento_straziante.png", List.of(Simbolo.FORZA), 3));
        carteCapitolo.add(new CartaMostro("Svoltato l'angolo incroci il cammino di due guardie. Esse si scambiano \n" +
                "sguardi confusi, e poi si lanciano in avanti per catturarti.",
                "/images/cassero/due_guardie.png", null, 2));
        carteCapitolo.add(new CartaMostro("Qualcuno sta bloccando la porta di questa stanza e dai una spallata \n" +
                "per forzarla. Solo quando entri, calpestando gusci d'uovo sotto i \n" +
                "tuoi piedi, ti rendi conto di aver distrutto il nido di una creatura. \n" +
                "Si ode un lugubre lamento mentre la bestia si avvicina \n" +
                "dall'oscurità; il dolore è presto sostituito dalla furia.",
                "/images/cassero/lugubre_lamento.png", List.of(Simbolo.FORZA, Simbolo.ASTUZIA), 2));
        carteCapitolo.add(new CartaMostro("Odi un urlo provenire dalle tue spalle e ti giri troppo tardi per \n" +
                "evitare le grinfie di un barcollante ghoul.",
                "/images/cassero/ghoul.png", List.of(Simbolo.SAGGEZZA), 2));
        carteCapitolo.add(new CartaMostro("Emergi in una piccola arena sabbiosa. Al centro, una donna molto \n" +
                "più alta e muscolosa dite solleva la sua spada consumata e proclama: \n" +
                "'Sfidanti, preparatevi a morire!'",
                "/images/cassero/donna_muscolosa.png", List.of(Simbolo.FORZA, Simbolo.ASTUZIA, Simbolo.SAGGEZZA), 1));
        carteCapitolo.add(new CartaMostro("Affrettandoti in questa sala illuminata da candele, interrompi una \n" +
                "cerimonia sacrificale. Un uomo in una tunica e con una maschera \n" +
                "funeraria dichiara che anche tu ora dovrai essere sacrificato.",
                "/images/cassero/cerimonia_sacrificale.png", List.of(Simbolo.FORZA, Simbolo.SAGGEZZA), 2));
        carteCapitolo.add(new CartaMostro("Il tanfo di sterco impregna l'aria mentre entri in questa sala \n" +
                "fiancheggiata da stalle. Una bestia da soma agitata si è liberata \n" +
                "dalla sua gabbia e ti blocca il passaggio. E' messa all'angolo, le sue \n" +
                "possenti corna pronte a straziare chiunque si avvicini.",
                "/images/cassero/bestia_da_soma.png", List.of(Simbolo.ASTUZIA), 2));
        carteCapitolo.add(new CartaMostro("Pulisci la polvere dal coperchio di una grande tomba sperando \n" +
                "di riuscire a leggerne l'iscrizione. Al tuo tocco, il coperchio scorre \n" +
                "di lato e un gigantesco cavaliere scheletrico ne emerge, gridando \n" +
                "che non avresti dovuto disturbarlo.",
                "/images/cassero/cavaliere_scheletrico.png", List.of(Simbolo.SAGGEZZA), 2));
        carteCapitolo.add(new CartaMostro("Alla fine di questo passaggio vi è un antico pozzo scavato nella \n" +
                "roccia. Mentre ti sporgi per controllare le acque, queste cominciano \n" +
                "ad agitarsi trasformandosi in un torrente impetuoso. \n" +
                "Hai disturbato uno spirito maligno.",
                "/images/cassero/pozzo.png", List.of(Simbolo.ASTUZIA, Simbolo.SAGGEZZA), 2));
        carteCapitolo.add(new CartaMostro("Percepisci qualcosa di strano in questa stanza. C'è un improvviso \n" +
                "calo di temperatura e l'aria si fa immobile. Poi, lo vedi... uno spettro.",
                "/images/cassero/calo_temperatura.png", List.of(Simbolo.FORZA), 1));
        carteCapitolo.add(new CartaMostro("Una creatura composta di parti di animali macellati cucite insieme \n" +
                "si avvicina a te. E' così ributtante che istintivamente inizi \n" +
                "a sferrari colpi.",
                "/images/cassero/creatura_animali.png", List.of(Simbolo.SAGGEZZA), 1));
        carteCapitolo.add(new CartaMostro("Una mostruosa creatura alata si fionda su di te dall'oscurità \n" +
                "sovrastante. Il suo grido di caccia è assordante.",
                "/images/cassero/creatura_alata.png", List.of(Simbolo.FORZA, Simbolo.SAGGEZZA), 2));
        carteCapitolo.add(new CartaMostro("Interrompi un uomo-bestia mentre divora una preda fresca. Egli \n" +
                "alza la testa, mentre dei tendini penzolano dalle fauci imbrattate \n" +
                "di sangue, e carica.",
                "/images/cassero/uomo_bestia.png", List.of(Simbolo.FORZA, Simbolo.ASTUZIA), 2));
        carteCapitolo.add(new CartaMostro("Questo corridoio si riempie improvvisamente di penetranti \n" +
                "maledizioni sussurrate. Fiamme eruttano dalle torce montate sui \n" +
                "muri andando a formare la figura di un guerriero demoniaco, \n" +
                "mentre arcane lingue di fuoco si alzano dal suo teschio annerito.",
                "/images/cassero/guerriero_demoniaco.png", List.of(Simbolo.ASTUZIA, Simbolo.SAGGEZZA), 2));
        carteCapitolo.add(new CartaMostro("Dove una volta vi erano carne e pelliccia, ora le ossa di questa \n" +
                "scheletrica bestia crepitano minacciose, intrise di magia oscura.",
                "/images/cassero/bestia_scheletrica.png", List.of(Simbolo.ASTUZIA, Simbolo.SAGGEZZA), 2));
        carteCapitolo.add(new CartaMostro("A un tavolo siede un uomo con il viso nascosto nell'ombra. Mentre \n" +
                "gli passi accanto, egli barcolla in avanti e ti afferra \n" +
                "a sé con un ringhio, rivelando un volto pieno di cicatrici e \n" +
                "pessime intenzioni.",
                "/images/cassero/uomo_viso_nascosto.png", List.of(Simbolo.FORZA, Simbolo.SAGGEZZA),2));
        carteCapitolo.add(new CartaMostro("Questo passaggio ti conduce accanto a oscure celle aperte. \n" +
                "Dall'interno, il suono di mani scheletriche che afferrano il metallo \n" +
                "è inconfondibile.",
                "/images/cassero/mani_scheletriche.png", List.of(Simbolo.FORZA, Simbolo.FORZA), 1));
        carteCapitolo.add(new CartaMostro("Due bruti deformi stanno discutendo la maniera migliore \n" +
                "di uccidere e divorare un altro uomo, quando notano il tuo arrivo.",
                "/images/cassero/bruti.png", List.of(Simbolo.ASTUZIA), 2));
        carteCapitolo.add(new CartaMostro("L'aroma di cibo caldo ti ha condotto alle cucine. Sul tavolo vicino \n" +
                "alla porta vi è un tortino di carne fumante. Provi ad afferrarlo, ma il cuoco \n" +
                "ti coglie sul fatto e giura di usarti come ripieno dei suoi prossimi tortini.",
                "/images/cassero/aroma_cibo.png", List.of(Simbolo.FORZA, Simbolo.SAGGEZZA), 2));

        // Carte Evento
        carteCapitolo.add(new EventoRicompensaDiretta("Dall'oscurità di fronte a te, una freccia sibila vicino al tuo orecchio. \n" +
                "Prima che tu possa reagire, un'altra penetra la tua spalla, facendoti accasciare agonizzante contro il muro. \n" +
                "Perdi 2 HP.\n" +
                "Strisciando cautamente lungo il passaggio, ti imbatti nel corpo di un uomo crivellato di frecce. \n" +
                "Non vi è alcun segno di aggressori, quindi decidi di controllare se stesse trasportando qualcosa di valore. \n" +
                "Pesca una carta Oggetto.", "/images/cassero/freccia.png", 1, 2));
        carteCapitolo.add(new EventoRicompensaDiretta("Per entrare da questa porta sei obbligato a spostare degli spessi rampicanti spinosi. \n" +
                "Essi prendono improvvisamente vita, avviluppando i tuoi polsi e trascinandoti dentro al roveto. \n" +
                "Perdi 1 HP.\n" +
                "Nel fare a pezzi le piante aggressive, queste alla fine retrocedono nelle crepe dei vecchi muri di pietra, \n" +
                "portando alla luce gli averi di precedenti vittime. \n" +
                "Pesca due carte Oggetto.", "/images/cassero/rampicanti.png", 2, 1));
        carteCapitolo.add(new EventoRicompensaDiretta("Passi attraverso una scala che serve da cloaca sotto le latrine del castello. \n" +
                "Mentre scegli attentamente il tuo percorso attraverso il sudiciume, qualcosa di strano cattura la tua attenzione. \n" +
                "Pesca una carta Oggetto.", "/images/cassero/latrine.png", 1, 0));
        carteCapitolo.add(new EventoRicompensaDiretta("Ti imbatti in una stanza che alcune guardie hanno lasciato di recente. \n" +
                "Infatti, il fuoco ancora arde sotto la grata e una marmitta di brodo fumante borbotta sulla stufa. \n" +
                "Improvvisamente odi l'avvicinarsi di passi e voci iraconde. Afferri ciò che puoi prima di \n" +
                "sgattaiolare via nell'oscurità. \n" +
                "Pesca una carta Oggetto.", "/images/cassero/cucina.png", 1, 0));
        carteCapitolo.add(new EventoLameOscillanti());
        carteCapitolo.add(new EventoStormoDiPipistrelli());
        carteCapitolo.add(new EventoStanzaDelTorturatore());
        carteCapitolo.add(new EventoTorreCampanaria());
        carteCapitolo.add(new CavernaAllagata());
        carteCapitolo.add(new EventoPioggiaAcido());
        carteCapitolo.add(new EventoIntriganteStraniero());
        carteCapitolo.add(new EventoBarattoUomoNascosto());


        // Carte Boss
        carteBoss.add(new TerroreDelSottosuolo());
        carteBoss.add(new SacerdotessaFolle());
        carteBoss.add(new LOscuro());
        carteBoss.add(new SignoreDelleTenebre());
        carteBoss.add(new Mutaforma());
    }

    public CartaIntro getCartaIntro(){
        return cartaIntro;
    }
    public List<CartaCapitolo> getCarteCapitolo(){
        return new ArrayList<>(carteCapitolo);
    }
    public List<CartaBoss> getCarteBoss(){
        return new ArrayList<>(carteBoss);
    }
}
