# Verifica manuale prima della consegna

1. Avviare GUI, scegliere ognuno dei sei personaggi, verificare immagini e HP.
2. Pescare intro e capitoli; controllare dorso boss quando rimane una carta.
3. Eventi straniero e baratto: entrambe le scelte, input non valido, mani piene.
4. Eventi a tentativi: caverna, lame, torturatore; verificare ogni passaggio e HP.
5. Combattimento: singolo, doppio, scudo, uso consumabili, rilanci, reliquie.
6. Ripetere per ciascuno dei cinque boss e controllare i rispettivi effetti.
7. Controllare ricompensa con sostituzione, uso cibo e scarto tra le stanze.
8. Verificare blocco di nuove azioni dopo morte e dopo vittoria.
9. Terminare una partita per ciascuna modalità e riaprire il file storico.
10. Verificare errore salvataggio con cartella non scrivibile, senza perdere esito.
11. Ripetere build e avvio su Linux/macOS con JDK 25 e schermo disponibile.

I test automatici coprono ordine del mazzo, isolamento del lancio, scelta evento,
combattimento doppio deterministico, salute, inventario, scudo, ripristino contesto
e persistenza. Non sostituiscono il collaudo interattivo di tutte le carte.

Verificare che il contatore sopra le carte mostri 1/17 all'introduzione,
2/17 al primo capitolo e 17/17 al boss (0/17 prima della prima pesca).
Con un'arma che consente il rilancio, accettare il rilancio e provare sia
la scelta del primo sia del secondo risultato, incluso un doppio con scudo.

Elisir dell'intuizione: durante il combattimento scegliere il simbolo del
mostro e la nuova faccia; controllare aggiornamento del tracciato e scarto
della pozione. Annullare in entrambe le scelte e provarla fuori combattimento:
non deve essere consumata. Le prove e le reliquie devono riportare entrambi
i simboli dei risultati doppi prima delle decisioni.

Il dado del personaggio deve aggiornarsi dopo i lanci nelle prove e negli
scontri. Provare un singolo e un doppio; dopo un rilancio con arma, scegliere
il primo risultato e verificare che l'immagine torni alla faccia selezionata.
