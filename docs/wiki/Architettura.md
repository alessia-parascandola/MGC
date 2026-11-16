# Architettura e responsabilità

Package radice: `it.unicam.cs.mpgc.rpg126692` (matricola identificata: 126692).

- Main: selezione console e composizione della partita.
- gui: avvio JavaFX, navigazione FXML, selezione e visualizzazione.
- GestorePartita: sequenza delle stanze, ricompense, gestione inventario ed effetti condivisi.
- GestoreCombattimento: turni, confronto simboli, contrattacchi ed effetti boss.
- Cassero: pila finita; intro in cima, capitoli mescolati, boss in fondo.
- carte: modello astratto, mostri, intro, mazzo; Eventi e Boss specializzano le risoluzioni.
- personaggi: stato salute, attributi, dado e inventario; factory dei sei personaggi.
- dadi: sei facce, simboli, risultati; il dado personaggio restituisce copie modificabili.
- oggetti: inventario a due mani, armi, cibo, scudo, pozioni e reliquie.
- Interazione: porta minima per testo e decisioni.
- Interazioni: contesto per thread con adattatore console unico e ripristino anche su eccezione.
- InterazioneGrafica: adattatore JavaFX con racconto e input nei dialoghi.
- StoricoPartite: scrittura e lettura degli esiti TSV UTF-8 su file.

## Flusso

Scelta personaggio (35 HP nel codice attuale), costruzione casuale del Cassero,
intro, capitoli, boss. Ogni carta esegue il comportamento polimorfico. I mostri
preparano simboli fissi più dado capitolo. Ogni turno permette uso oggetti,
lancio con effetti già previsti, rimozione dei simboli e contrattacco se il
nemico è ancora vivo. I boss applicano le proprie regole. A vittoria sul
mostro viene pescata una ricompensa con scelta di sostituzione quando occorre.
Gli eventi possono richiedere scelte o più tentativi. Zero HP conclude con
sconfitta; superare tutte le carte conclude con vittoria.

## Persistenza

StoricoPartite salva istante ISO, personaggio ed esito. Cartella creata al primo
salvataggio; file assente restituisce lista vuota. Errori IOException mostrati
all'utente, senza dichiarare un salvataggio riuscito. La lettura è disponibile
tramite leggi(), verificata riaprendo il repository nel test. Non c'è ancora
un visualizzatore storico né il salvataggio completo di mazzi e inventario.

## SOLID ed estensioni

Le regole non importano JavaFX. Console e GUI usano le stesse risoluzioni;
non sono mantenuti due combattimenti differenti. Le gerarchie di carte e
oggetti consentono nuove specializzazioni, registrate nei rispettivi mazzi.
Interazione è piccola e sostituibile da fake di test o altre presentazioni.

Limiti: Interazioni è una soluzione di migrazione con contesto implicito;
l'iniezione esplicita sarebbe preferibile per un servizio web. Le regole sono
ancora sincrone e formulate con prompt testuali: una futura UI mobile/web
richiederà un protocollo di comandi/stati, pur potendo riusare il dominio.
GestorePartita conserva diverse responsabilità; inventari e tracciati espongono
liste modificabili. La persistenza è separata dalla GUI ma concreta e non
ancora iniettata tramite repository. Non si rivendica piena conformità SOLID.

## Ambiguità conservate

Gli HP massimi sono 35; i messaggi leggono il massimo dal personaggio. Alcuni oggetti
annunciano effetti non chiaramente applicati (es. dado capitolo extra): occorre
confronto con le regole scelte dall'autore prima di introdurre nuove meccaniche.
Alcuni eventi aggiungono direttamente ricompense: se le mani sono piene possono
non acquisirle, come nella versione terminale originaria.

La GUI permette di consultare gli esiti tramite il pulsante Storico.
