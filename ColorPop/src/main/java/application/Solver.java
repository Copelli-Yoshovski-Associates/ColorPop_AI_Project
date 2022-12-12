package application;

import application.controller.GameBoardController;
import application.model.Block;
import application.model.Color;
import application.model.Move;
import it.unical.mat.embasp.base.Handler;
import it.unical.mat.embasp.base.InputProgram;
import it.unical.mat.embasp.base.OptionDescriptor;
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

	public static void setup() {
		System.out.println("Solver setup");

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
		handler.addProgram(encoding);
	}

	public static void addFactBlock(Block b) {
//		System.out.println(String.format("UN FATTO È %s", b.toString()));
		try {
			facts.addObjectInput(b);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public static void prossimaMossa() {
		handler.addProgram(facts);

		// L'handler invoca DLV2 in modo SINCRONO dando come input il programma logico e
		// i fatti ...
		Output output = handler.startSync();

//		System.out.println("Output: " + output.getOutput());

		// Analizziamo l'answer ...
		AnswerSets answerSets = (AnswerSets) output;

//		System.out.println("RISPOSTE: " + answerSets.getAnswersets().size());

		try {
			if(answerSets.getAnswersets().isEmpty()) return;
			if (!app.gameOver()) {
				AnswerSet a = answerSets.getOptimalAnswerSets().get(0);

			//	System.out.println("OKKK: " + answerSets.getOptimalAnswerSets().get(0));

//				System.out.println("ATOMS: "+a.getAtoms());

				for(Object obj : a.getAtoms()) {
					if(obj instanceof Move) {
						Move m = (Move) obj;
						System.out.println(m);
						app.removeNeighbors(m.getX(), m.getY());
						Thread.sleep(80);
					}
				}
			} else {
				System.out.println("ABBIAMO TERMINATO LE MOSSE :D ");
				app.showResults();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		facts.clearAll();
	}

}
