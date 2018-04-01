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

import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.elements.ScreenElement;
import projekt.mathe.game.engine.help.Animator;
import projekt.mathe.game.engine.help.Helper;
import projekt.mathe.game.engine.help.Helper.FONT;

public class Questionfield extends ScreenElement{

	private static final int QUESTIONS = 1, CORRECT_QUESTIONS = (int) (QUESTIONS / 2f) + 1;
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
			Helper.drawStringAroundPosition((int) getX() + 400, (int) getY() + 50, question.getQuestion().toUpperCase(), Color.WHITE, 26, FONT.VCR, g2d, null, -1);
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
			private int absolutX, absolutY;
			private String state;
			private Runnable runnable;
			
			public AnswerButton(Scene container, int x, int y, String answer, Runnable runnable) {
				super(container, x, y, 100, 50);
				absolutX = x;
				absolutY = y;
				this.answer = answer;
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
				Helper.drawStringAroundPosition((int) getBounds().getCenterX(), (int) getBounds().getCenterY(), answer.toUpperCase(), Color.BLACK, 20, FONT.VCR, g2d, null, -1);
			}

		}
		
	}
	
	public class Question{
		
		private String question;
		private String correctAnswer;
		private String answer1, answer2;
		private int seconds;
		
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
			new Question("Die Zahl unter dem Bruchstrich heißt Zähler.", "falsch", "wahr", time),
			new Question("(1 : 3) • (2 : 3) = ?", "2 : 9", "1 : 3", time),
			new Question("Mal-Rechnen heißt Division.", "falsch", "wahr", time),
			new Question("(1 : 2) + ? = 1", "0,5", "2", time),
			new Question("5 : 100 = ?", "1 : 20", "2 : 10", time),
			new Question("3 : 27 = 1 : 9", "wahr", "falsch", time),
			new Question("0,5 + (1 : 2) • (1 : 2) = ?", "3 : 4", "1 : 2", time),
			new Question("9 : 7 ist ein echter Bruch.", "falsch", "wahr", time),
			new Question("(2 + 2) : 3 = (2 : 3) + (2 : 3)", "wahr", "falsch", time),
			new Question("(3 • 3) : 4 = (3 : 4) • (3 : 4)", "falsch", "wahr", time),
			new Question("Man teilt den Dividenden durch den Divisor.", "wahr", "falsch", time),
			new Question("Das Ergebnis einer Division heißt Produkt.", "falsch", "wahr", time),
			new Question("(3 : 4) + (1 : 4) • (1 : 2) = ?", "7 : 8", "1 : 2", time),
			new Question("1 : 9 = (1 : 3) • 3", "falsch", "wahr", time),
			new Question("1 : 100 = (1 : 10) : 10", "wahr", "falsch", time),
			new Question("(3 : 7) : (9 : 13) = (3 : 7) • (13 : 9)", "wahr", "falsch", time),
			new Question("(32 : 35) = 1 - (6 : 70)", "wahr", "falsch", time),
			new Question("Das Ergebnis einer Addition heißt Summe.", "wahr", "falsch", time),
			new Question("(3 : 5) > (4 : 6)", "falsch", "wahr", time),
			new Question("(2 : 9) < (3 : 11)", "wahr", "falsch", time),
			new Question("(5 : 8) ? (6 : 9)", "<", ">", time),
			new Question("37 : 74 = ?", "0,5", "1 : 3", time),
			new Question("(1 : 3) • (1 : 3) = 1 : (3 + 3)", "falsch", "wahr", time),
			new Question("(1 : 2) • (1 : 2) = 1 : (2 + 2)", "wahr", "falsch", time),
			new Question("Potenz-Rechnen gehört zu den Grundrechenarten.", "falsch", "wahr", time),
			new Question("(2 : 3) • 3", "2", "6 : 9", time),
			new Question("547 : 547 = 432 : 432", "wahr", "falsch", time),
			new Question("Die Zahl über dem Bruchstrich heißt...", "Zähler", "Nenner", time),
			new Question("(341 : 482) ist ein ... Bruch.", "echter", "unechter", time),
			new Question("Bruchrechnen ist dasselbe wie eine Multiplikation.", "falsch", "wahr", time)
		));
	private Question getRandomQuestion() {
		int index = ThreadLocalRandom.current().nextInt(0, questions.size());
		Question question = questions.get(index);
		questions.remove(index);
		return question;
	}
	
}
