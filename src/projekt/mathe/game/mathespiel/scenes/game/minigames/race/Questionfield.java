package projekt.mathe.game.mathespiel.scenes.game.minigames.race;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.List;
import java.awt.event.MouseEvent;
import java.sql.DatabaseMetaData;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

import javax.security.auth.x500.X500Principal;
import javax.swing.JOptionPane;

import org.omg.CORBA.PRIVATE_MEMBER;

import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.elements.ScreenElement;
import projekt.mathe.game.engine.help.Animator;
import projekt.mathe.game.engine.help.Helper;
import projekt.mathe.game.engine.help.Helper.FONT;

public class Questionfield extends ScreenElement{

	private static final int QUESTIONS = 7, CORRECT_QUESTIONS = (int) (QUESTIONS / 2f) + 1;
	private float absolutX, absolutY;
	private String state;
	private Animator delayAnimator;
	private QuestionContainer questionContainer;
	private ResultScreen resultScreen;
	private Runnable onAnsweredCorrectly, onAnsweredNotCorrectly;
	private int questionsAnswered, questionsAnsweredCorrectly;
	
	public Questionfield(Scene container, Runnable onAnsweredCorrectly, Runnable onAnsweredNotCorrectly) {
		super(container, container.camera.translateAbsolutX(-1), container.camera.translateAbsolutY(-1), 500, 300);
		this.absolutX = 100;
		this.absolutY = 200;
		this.onAnsweredCorrectly = onAnsweredCorrectly;
		this.onAnsweredNotCorrectly = onAnsweredNotCorrectly;
		state = "waiting";
		questionContainer = new QuestionContainer(container);
		resultScreen = new ResultScreen(container);
	}

	public boolean finished() {
		return questionsAnswered >= QUESTIONS;
	}
	
	public boolean finishedCorrectly() {
		return questionsAnsweredCorrectly >= CORRECT_QUESTIONS;
	}
	
	public void start() {
		state = "running";
		delayAnimator = new Animator(120, 1);
		questionsAnswered = 0;
		questionsAnsweredCorrectly = 0;
	}
	
	@Override
	public void onMouseClicked(MouseEvent e) {
		questionContainer.onMouseClicked(e);
	}
	
	@Override
	public void onMouseDragged(MouseEvent e) {
		questionContainer.onMouseDragged(e);
	}
	
	@Override
	public void onMouseMoved(MouseEvent e) {
		questionContainer.onMouseMoved(e);
	}
	
	@Override
	public void onMousePressed(MouseEvent e) {
		questionContainer.onMousePressed(e);
	}
	
	@Override
	public void onTick(float delta) {
		setX(getContainer().camera.translateAbsolutX(absolutX));
		setY(getContainer().camera.translateAbsolutY(absolutY));
		switch (state) {
			case "running" :
				delayAnimator.calculate(delta);
				if(delayAnimator.finished()) {
					delayAnimator.reset();
					state = "question";
					questionContainer.reset(getRandomQuestion());
				}
				break;
			case "question" :
				questionContainer.onTick(delta);
				if(questionContainer.finished()) {
					state = "answered";
					if(questionContainer.isAnswered()) {
						if(questionContainer.answeredCorrectly) {
							resultScreen.reset("t");
						}else {
							resultScreen.reset("f");
						}
					}else {
						resultScreen.reset("n");
					}
				}
				break;
			case "answered" :
				resultScreen.onTick(delta);
				if(resultScreen.isFinished()) {
					questionsAnswered++;
					if(resultScreen.getAnsweredState().equals("t")) {
						questionsAnsweredCorrectly++;
					}
					if(resultScreen.getAnsweredState().equals("t")) {
						onAnsweredCorrectly.run();
					}else {
						onAnsweredNotCorrectly.run();
					}
					if(questionsAnswered < QUESTIONS) {
						state = "running";
					}else {
						state = "finished";
					}
				}
				break;
			case "finished" : 
				break;
		}
	}

	@Override
	public void onPaint(Graphics2D g2d) {
		if(state.equals("question")) {
			questionContainer.onPerformacePaint(g2d);
		}else if(state.equals("answered")) {
			resultScreen.onPerformacePaint(g2d);
		}
	}

	public class QuestionContainer extends ScreenElement{

		private int absolutX, absolutY;
		private Question question;
		private AnswerButton button1, button2;
		private boolean answered, answeredCorrectly, finished;
		private Animator timeLimit;
		private Animator alpha;
		
		public QuestionContainer(Scene container) {
			super(container, -1, -1, 800, 300);
			absolutX = 240;
			absolutY = 370;
			alpha = new Animator(20, 1);
		}

		public void reset(Question question) {
			this.question = question;
			button1 = new AnswerButton(getContainer(), 490, 500, question.getAnswer1(), new Runnable() {
				@Override
				public void run() {
					checkAnswer(question.getAnswer1());
				}
			});
			button2 = new AnswerButton(getContainer(), 690, 500, question.getAnswer2(), new Runnable() {
				@Override
				public void run() {
					checkAnswer(question.getAnswer2());
				}
			});
			answered = false;
			answeredCorrectly = false;
			finished = false;
			timeLimit = new Animator(question.getSeconds() * 60, 1);
			alpha.reset();
		}
		
		private void checkAnswer(String answer) {
			answered = true;
			answeredCorrectly = question.wasAnswerCorrect(answer);
		}
		
		public boolean finished() {
			return finished && alpha.finished();
		}
		
		public boolean isAnswered() {
			return answered;
		}

		public boolean isAnsweredCorrectly() {
			return answeredCorrectly;
		}

		@Override
		public void onMousePressed(MouseEvent e) {
			if(button1 != null && button2 != null && !answered) {
				button1.onMousePressed(e);
				button2.onMousePressed(e);
			}
		}
		
		@Override
		public void onMouseDragged(MouseEvent e) {
			if(button1 != null && button2 != null && !answered) {
				button1.onMouseDragged(e);
				button2.onMouseDragged(e);
			}
		}
		
		@Override
		public void onMouseMoved(MouseEvent e) {
			if(button1 != null && button2 != null && !answered) {
				button1.onMouseMoved(e);
				button2.onMouseMoved(e);
			}
		}
		
		@Override
		public void onMouseClicked(MouseEvent e) {
			if(button1 != null && button2 != null && !answered && !timeLimit.finished()) {
				button1.onMouseClicked(e);
				button2.onMouseClicked(e);
			}
		}
		
		@Override
		public void onTick(float delta) {
			alpha.calculate(delta);
			setX(getContainer().camera.translateAbsolutX(absolutX));
			setY(getContainer().camera.translateAbsolutY(absolutY));
			button1.onTick(delta);
			button2.onTick(delta);
			timeLimit.calculate(delta);
			question.getQuestionPainter().onTick(delta);
			if((timeLimit.finished() || answered) && !finished) {
				finished = true;
				alpha.reset();
			}
		}

		@Override
		public void onPaint(Graphics2D g2d) {
			Composite alphaComposite = g2d.getComposite();
			if(finished) {
				g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1 - alpha.getCurrValueRelative()));
			}else {
				g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha.getCurrValueRelative()));
			}
			g2d.setColor(new Color(.5f, .5f, .5f, .85f));
			g2d.fill(getBounds());
			g2d.setColor(Color.WHITE);
			g2d.setStroke(new BasicStroke(5f));
			g2d.draw(getBounds());
			g2d.setColor(new Color(.4f, .4f, .4f, .8f));
			g2d.fillRect((int) getX() + 3, (int) getY() + 27, (int) getW() - 6, 50);
			question.getQuestionPainter().onPaint(g2d);
			button1.onPerformacePaint(g2d);
			button2.onPerformacePaint(g2d);
			g2d.setColor(Color.WHITE);
			g2d.fillRect((int) getX() + 60, (int) getY() + 250, 680, 15);
			g2d.setColor(Color.RED);
			g2d.fillRect((int) getX() + 65, (int) getY() + 255, (int) (670 * timeLimit.getCurrValueRelative()), 5);
			g2d.setComposite(alphaComposite);
		}
		
		private class AnswerButton extends ScreenElement{
			
			private String answer;
			private Fraction fraction;
			private int absolutX, absolutY;
			private String state;
			private Runnable runnable;
			
			public AnswerButton(Scene container, int x, int y, String answer, Runnable runnable) {
				super(container, x, y, 100, 50);
				absolutX = x;
				absolutY = y;
				if(!answer.contains(" : ")) {
					this.answer = answer;
				}else {
					fraction = new Fraction(container, x + 50, y + 22, Integer.valueOf(answer.split(" : ")[0]), Integer.valueOf(answer.split(" : ")[1]));
				}
				state = "untouched";
				this.runnable = runnable;
			}

			@Override
			public void onMousePressed(MouseEvent e) {
				if(getBounds().contains(e.getPoint())) {
					if(!state.equals("clicked")) {
						state = "pressed";
					}
				}
			}
			
			@Override
			public void onMouseDragged(MouseEvent e) {
				if(getBounds().contains(e.getPoint())) {
					if(!state.equals("pressed") && !state.equals("clicked")) {
						state = "hovered";
					}
				}else if(!state.equals("clicked")){
					state = "untouched";
				}
			}
			
			@Override
			public void onMouseMoved(MouseEvent e) {
				if(getBounds().contains(e.getPoint())) {
					if(!state.equals("pressed") && !state.equals("clicked")) {
						state = "hovered";
					}
				}else if(!state.equals("clicked")){
					state = "untouched";
				}
			}
			
			@Override
			public void onMouseClicked(MouseEvent e) {
				if(getBounds().contains(e.getPoint())) {
					state = "clicked";
					runnable.run();
				}
			}
			
			@Override
			public void onTick(float delta) {
				setX(getContainer().camera.translateAbsolutX(absolutX));
				setY(getContainer().camera.translateAbsolutY(absolutY));
				if(answer == null) {
					fraction.onTick(delta);
				}
			}

			@Override
			public void onPaint(Graphics2D g2d) {
				g2d.setColor(Color.WHITE);
				g2d.setStroke(new BasicStroke(4f));
				g2d.draw(getBounds());
				switch (state) {
					case "untouched": g2d.setColor(Color.LIGHT_GRAY); break;
					case "hovered": g2d.setColor(Color.GRAY); break;
					case "pressed": g2d.setColor(Color.DARK_GRAY); break;
					case "clicked": g2d.setColor(Color.WHITE); break;
				}
				g2d.fill(getBounds());
				if(answer != null) {
					Helper.drawStringAroundPosition((int) getBounds().getCenterX(), (int) getBounds().getCenterY(), answer.toUpperCase(), Color.BLACK, 20, FONT.VCR, g2d, null, -1);
				}else {
					fraction.onPaint(g2d);
				}
			}

		}
		
	}
	
	public class Question{
		
		private String question;
		private String correctAnswer;
		private String answer1, answer2;
		private int seconds;
		private QuestionPainter questionPainter;
		
		public Question(String question, String correctAnswer, String incorrectAnswer, int seconds) {
			this.question = question;
			this.correctAnswer = correctAnswer;
			if(ThreadLocalRandom.current().nextBoolean()) {
				answer1 = correctAnswer;
				answer2 = incorrectAnswer;
			}else {
				answer1 = incorrectAnswer;
				answer2 = correctAnswer;
			}
			this.seconds = seconds;
			this.questionPainter = new QuestionPainter(getContainer(), Questionfield.this, question.toUpperCase());
		}

		public QuestionPainter getQuestionPainter() {
			return questionPainter;
		}
		
		public String getQuestion() {
			return question;
		}

		public String getAnswer1() {
			return answer1;
		}

		public String getAnswer2() {
			return answer2;
		}

		public int getSeconds() {
			return seconds;
		}
		
		public boolean wasAnswerCorrect(String s) {
			return correctAnswer.equals(s);
		}
		
	}
	
	private int time = 20;
	private ArrayList<Question> questions = new ArrayList<Question>(Arrays.asList(
			new Question("1 : 3 • 2 : 3 = ?", "2 : 9", "1 : 3", time),
			new Question("Mal-Rechnen heißt Division.", "falsch", "wahr", time),
			new Question("1 : 2 + ? = 1", "0,5", "2", time),
			new Question("5 : 100 = ?", "1 : 20", "2 : 10", time),
			new Question("3 : 27 = 1 : 9", "wahr", "falsch", time),
			new Question("0,5 + 1 : 2 • 1 : 2 = ?", "3 : 4", "1 : 2", time),
			new Question("9 : 7 ist ein echter Bruch.", "falsch", "wahr", time),
			new Question("(2 + 2) • 1 : 3 = 2 : 3 + 2 : 3", "wahr", "falsch", time),
			new Question("(3 • 3) • 1 : 4 = 3 : 4 • 3 : 4", "falsch", "wahr", time),
			new Question("Man teilt den Dividenden durch den Divisor.", "wahr", "falsch", time),
			new Question("Das Ergebnis einer Division heißt Produkt.", "falsch", "wahr", time),
			new Question("3 : 4 + 1 : 4 • 1 : 2 = ?", "7 : 8", "1 : 2", time),
			new Question("1 : 9 = 1 : 3 • 3", "falsch", "wahr", time),
			new Question("1 : 100 = 1 : 10 • 0,1", "wahr", "falsch", time),
			new Question("3 : 7 • 9 : 13 = 3 : 7 • 13 : 9", "wahr", "falsch", time),
			new Question("32 : 35 = 1 - 6 : 70", "wahr", "falsch", time),
			new Question("Das Ergebnis einer Addition heißt Summe.", "wahr", "falsch", time),
			new Question("3 : 5 > 4 : 6", "falsch", "wahr", time),
			new Question("2 : 9 < 3 : 11", "wahr", "falsch", time),
			new Question("5 : 8 ? 6 : 9", "<", ">", time),
			new Question("37 : 74 = ?", "0,5", "1 : 3", time),
			new Question("2 : 3 • 1 : 3 = 3 : 9", "falsch", "wahr", time),
			new Question("1 : 2 • 1 : 2 = (2 + 2) • 1 : 16", "wahr", "falsch", time),
			new Question("Potenz-Rechnen gehört zu den Grundrechenarten.", "falsch", "wahr", time),
			new Question("2 : 3 • 3", "2", "6 : 9", time),
			new Question("547 : 547 = 432 : 432", "wahr", "falsch", time),
			new Question("Die Zahl über dem Bruchstrich heißt...", "Zähler", "Nenner", time),
			new Question("341 : 482 ist ein ... Bruch.", "echter", "unechter", time),
			new Question("Bruchrechnen ist dasselbe wie eine Multiplikation.", "falsch", "wahr", time)
		));
	private Question getRandomQuestion() {
		int index = ThreadLocalRandom.current().nextInt(0, questions.size());
		Question question = questions.get(index);
		questions.remove(index);
		System.out.println(question.getQuestion());
		return question;
	}
	
	public ComplexChest generateComplexChest(int middleX, int middleY, String s) {
		String[] split = s.split(" : ");
		ArrayList<Integer[]> fractions = new ArrayList<>();
		for(int i = 0; i < split.length - 1; i++) {
			int pos = 0;
			char number = 0;
			while (number != ' ' && split[i].length() - 1 - pos >= 0) {
				number = split[i].charAt(split[i].length() - 1 - pos);
				if(number != ' ') {
					pos++;
				}
			}
			String n1 = "";
			for(int p = 0; p < pos; p++) {
				n1 += Character.getNumericValue(split[i].charAt(split[i].length() - 1 - p));
			}
			n1 = new StringBuilder(n1).reverse().toString();
			pos = 0;
			number = 0;
			while (number != ' ' && pos <= split[i + 1].length() - 1) {
				number = split[i + 1].charAt(pos);
				if(number != ' ') {
					pos++;
				}
			}
			String n2 = "";
			for(int p = 0; p < pos; p++) {
				n2 += Character.getNumericValue(split[i + 1].charAt(p));
			}
			fractions.add(new Integer[] {Integer.valueOf(n1), Integer.valueOf(n2)});
		}
		
		for(Integer[] is : fractions) {
			s = s.replaceFirst(is[0] + " : " + is[1], "#");
		}

		int space = 5;
		int numberSize = 10;
		int fractionSize = 46;
		
		int totalSize = 0;
		for(int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if(c == '#') {
				totalSize += fractionSize + space;
			}else {
				totalSize += numberSize + space;
			}
		}
		totalSize -= space;
		
		ArrayList<Fraction> fsArrayList = new ArrayList<>();
		ArrayList<CharInfo> charInfos = new ArrayList<>();
		
		int x = middleX - totalSize/2;
		int fIndex = 0;
		for(int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if(c == '#') {
				fsArrayList.add(new Fraction(getContainer(), x + fractionSize/2, middleY, fractions.get(fIndex)[0], fractions.get(fIndex)[1]));
				fIndex++;
				x += fractionSize + space;
			}else {
				charInfos.add(new CharInfo(x + numberSize/2, middleY, c));
				x += numberSize + space;
			}
		}
		
		return new ComplexChest(fsArrayList, charInfos);
		
	}
	
	private class Fraction extends ScreenElement{

		private int absolutMiddleX, absolutMiddleY;
		private int number1, number2;
		
		public Fraction(Scene container, int middleX, int middleY, int number1, int number2) {
			super(container, -1, -1, -1, -1);
			absolutMiddleX = middleX;
			absolutMiddleY = middleY;
			this.number1 = number1;
			this.number2 = number2;
		}

		@Override
		public void onTick(float delta) {
			setX(getContainer().camera.translateAbsolutX(absolutMiddleX));
			setY(getContainer().camera.translateAbsolutY(absolutMiddleY));
		}

		@Override
		public void onPaint(Graphics2D g2d) {
			Helper.drawStringAroundPosition((int) getX(), (int) getY() - 13, "" + number1, Color.WHITE, 20, FONT.VCR, g2d, null, -1);
			Helper.drawStringAroundPosition((int) getX(), (int) getY() + 13, "" + number2, Color.WHITE, 20, FONT.VCR, g2d, null, -1);
			g2d.setColor(Color.WHITE);
			g2d.fillRect((int) getX() - 23, (int) getY() + 1, 46, 2);
			
		}
		
	}
	
	public class CharInfo {
		
		private int middleX, middleY;
		private char c;
		private float x, y;
		
		public CharInfo(int middleX, int middleY, char c) {
			this.middleX = middleX;
			this.middleY = middleY;
			this.c = c;
			x = -1;
			y = -1;
		}

		public void onTick(float delta) {
			x = getContainer().camera.translateAbsolutX(middleX);
			y = getContainer().camera.translateAbsolutY(middleY);
		}
		
		public int getMiddleX() {
			return Math.round(x);
		}

		public int getMiddleY() {
			return Math.round(y);
		}

		public char getC() {
			return c;
		}
		
	}
	
	public class ComplexChest {
		
		private ArrayList<Fraction> fractions;
		private ArrayList<CharInfo> charInfos;
		
		public ComplexChest(ArrayList<Fraction> fractions, ArrayList<CharInfo> charInfos) {
			this.fractions = fractions;
			this.charInfos = charInfos;
		}

		public void onTick(float delta) {
			for(Fraction fraction : fractions) {
				fraction.onTick(delta);
			}
			for(CharInfo charInfo : charInfos) {
				charInfo.onTick(delta);
			}
		}
		
		public void onPaint(Graphics2D g2d) {
			for(CharInfo charInfo : charInfos) {
				Helper.drawStringAroundPosition(charInfo.getMiddleX(), charInfo.getMiddleY(), String.valueOf(charInfo.getC()), Color.WHITE, 26, FONT.VCR, g2d, null, -1);
			}
			for(Fraction fraction : fractions) {
				fraction.onPaint(g2d);
			}
		}
		
	}
	
}
