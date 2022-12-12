package application;

import application.controller.GameBoardController;
import application.model.Block;
import application.model.Color;
import application.model.Move;
import it.unical.mat.embasp.base.Handler;
import it.unical.mat.embasp.base.InputProgram;
import it.unical.mat.embasp.base.Output;
import it.unical.mat.embasp.languages.IllegalAnnotationException;
import it.unical.mat.embasp.languages.ObjectNotValidException;
import it.unical.mat.embasp.languages.asp.ASPInputProgram;
import it.unical.mat.embasp.languages.asp.ASPMapper;
import it.unical.mat.embasp.languages.asp.AnswerSet;
import it.unical.mat.embasp.languages.asp.AnswerSets;
import it.unical.mat.embasp.platforms.desktop.DesktopHandler;
import it.unical.mat.embasp.specializations.dlv2.desktop.DLV2DesktopService;

public class Solver {

	private static GameBoardController app;

	private static String encodingResource = "encodings/asp2.dlv";

	private static Handler handler;

	private static InputProgram facts = new ASPInputProgram();

	public static void setController(GameBoardController appl) {
		app = appl;
	}

	public static void setup() throws InterruptedException {
		System.out.println("Solver setup");
		// Creazione dell'oggetto handler che si occuperà di gestire l'invocazione
		// del sistema ASP da utilizzare ...

		handler = new DesktopHandler(new DLV2DesktopService("lib/dlv-2.exe"));

		// Specifichiamo i fatti in input ...
		try {
			ASPMapper.getInstance().registerClass(Block.class);
			ASPMapper.getInstance().registerClass(Move.class);
		} catch (ObjectNotValidException | IllegalAnnotationException e1) {
			e1.printStackTrace();
		}

		// Specifichiamo il programma logico tramite file ...
		InputProgram encoding = new ASPInputProgram();
		encoding.addFilesPath(encodingResource);

		// Aggiungiamo all'handler il programma logico ...
		handler.addProgram(encoding);
	}

//	 public static void addFactTessera(Tessera t) throws Exception {
//	 System.out.println(String.format("UN FATTO È
	// %s",t.getWithGlobalIndex().toString()));
	// facts.addObjectInput(t.getWithGlobalIndex());
	// }

//	 public static void addFactSbloccabile(Tessera t1, Tessera s1) throws
//	 Exception{
//	 facts.addObjectInput(new TesseraSbloccabile(t1.getWithGlobalIndex(),

//	 s1.getWithGlobalIndex()));
//	 }

	public static void addFactBlock(Block b) throws Exception {
//		System.out.println(String.format("UN FATTO È %s", b.toString()));
		facts.addObjectInput(b);
	}

	public static void prossimaMossa() throws InterruptedException {
		handler.addProgram(facts);

		// L'handler invoca DLV2 in modo SINCRONO dando come input il programma logico e
		// i fatti ...
		Output output = handler.startSync();

		// Analizziamo l'answer ...
		AnswerSets answerSets = (AnswerSets) output;

//		System.out.println("RISPOSTE: " + answerSets.getAnswersets().size());

		try {
			if (!answerSets.getOptimalAnswerSets().isEmpty()) {
				AnswerSet a = answerSets.getOptimalAnswerSets().get(0);

			//	System.out.println("OKKK: " + answerSets.getOptimalAnswerSets().get(0));

//				System.out.println("ATOMS: "+a.getAtoms());

				for(Object obj : a.getAtoms()) {
					if(obj instanceof Move) {
						Move m = (Move) obj;
						System.out.println(m);
						app.removeNeighbors(m.getX(), m.getY());
						//Thread.sleep(800);
					}
				}
			} else {
				System.out.println("ABBIAMO TERMINATO LE MOSSE :D ");
				System.exit(-1);
				Thread.sleep(40000000);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Thread.sleep(1000000);
		}

		facts.clearAll();
	}

}
