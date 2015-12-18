
Il gruppo si pone come obiettivo quello di realizzare un software adibito alla gestione di uno o più magazzini per una casa editrice realmente esistente.


Un membro del nostro gruppo di lavoro ha riscontrato l'esigenza di migliorare l'organizzazione e la gestione dei magazzini della casa editrice di famiglia.

Dato questo interesse ci siamo preposti come obiettivo la realizzazione di un software che più possibile si avvicini alla gestione del reale caso di utilizzo, pur aggiungendo ulteriori feature accessorie.


Il software esporrà le seguenti funzionalità:

  Creazione e inizializzazione di uno o più magazzini
  Creazione e inizializzazione dei libri con relativi campi
  Ricerca nel database di libri, a partire da uno o più campi a discrezione dell'utente, con relativo autocompletamento in fase di registrazione dei movimenti
  Gestione movimenti interni/esterni, verso clienti, fornitori ed altri magazzini
  Creazione di file che rappresentino il resoconto di un dato periodo di tempo(mensile,annuale) estratto da un database
  Rappresentazione statistica dei dati utili presenti in database


Freature opzionali:

  Lettura di un file da utilizzare come input per registrazione multipla di movimenti
  Creazione di grafici statistici a partire dai dati raccolti
  Visualizzazione posizione corriere espresso per i libri spediti attraverso API corriere/RSS
  Invio di mail relative ai resoconti direttamente dal software verso un ipotetico commercialista


Challenge:

  La realizzazione di grafici e dell'invio delle mail richiederà l'uso di librerie specifiche da identificare
  L'implementazione del tracciamento del corriere necessiterà dell'utilizzo di una libreria non ancora identificata
  La moltitudine di interfacce grafiche annidate e complesse necessiterà una particolare attenzione


Suddivisione del lavoro fra i 4 componenti del gruppo:

  Feroce Marcello: Model
  Cecchetti Giulia: View
  Croccolino Lorenzo: Controller
  Colombo Andrea: View + Ricerca API
