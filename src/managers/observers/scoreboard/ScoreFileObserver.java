package managers.observers.scoreboard;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ScoreFileObserver extends Observer{
	private HashMap<String, Integer> scores;
	private String filename = System.getProperty("user.dir") + "/resource/log/scoreboard.log";

	public ScoreFileObserver(Subject subject){
      this.subject = subject;
      this.subject.attach(this);
      initScore();
	}
	
	public Map<String, Integer> getScores() {
		return scores;
	}
	
	private void initScore() {
		Map<String, Integer> oldScore = null;
		try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
			oldScore = (HashMap<String, Integer>)in.readObject();
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		} catch (ClassNotFoundException e) {
		}
		this.scores = new HashMap<>();
	}

   @Override
   public void update() {
	   if (this.subject.getState() == 2) {
		   int points = 0;
		   String id = "";
		   try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
	    	   // TODO insert here data to print on log
			   for (Observer e : this.subject.observers) {
				   if (e.getClass() == PointsAccumulatorObserver.class) {
					   points = ((PointsAccumulatorObserver) e).getPoints();
					   id = ((PointsAccumulatorObserver) e).getName();
				   }
			   }
			   scores.put(id, points);
	           out.writeObject(scores);
		   } catch (FileNotFoundException e) {
			e.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	   }
   }
			
}
