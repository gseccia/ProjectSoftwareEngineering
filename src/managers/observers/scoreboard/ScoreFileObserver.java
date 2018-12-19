package managers.observers.scoreboard;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import managers.observers.Observer;
import managers.observers.Subject;

public class ScoreFileObserver extends Observer implements Serializable {
	
	private ArrayList<Score> scores;
	private String filename = System.getProperty("user.dir") + File.separator+"resource"+File.separator+"log"+
			File.separator+"scoreboard.log";
	public static ScoreFileObserver instance;
	
	public static ScoreFileObserver getInstance(Subject subject) {
		if(instance == null) 
			instance = new ScoreFileObserver(subject);
		return instance;
	}
	
	private ScoreFileObserver(Subject subject){
      this.subject = subject;
      this.subject.attach(this);
      this.scores = new ArrayList<>();
      initScore();
	}
	
	public ArrayList<Score> getScores() {
		return scores;
	}
	
	public boolean contains(int value, String id) {
		boolean flag = false;
		Score s = new Score(value, id);
		for(Score score : scores) {
			if(s.equals(score)) {
				flag = true;
				break;
			}	
		}
		return flag;
	}
	
	private void initScore() {
		if(Files.exists(Paths.get(filename), LinkOption.NOFOLLOW_LINKS)) {
			try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
				scores = (ArrayList<Score>)in.readObject();
			} catch (FileNotFoundException e) {
			} catch (IOException e) {
			} catch (ClassNotFoundException e) {
			}
		}
		else {
			try {
				Files.createFile(Paths.get(filename));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

   @Override
   /**
    *  This method updates the scoreboard list with the best 5 results. Scores are saved as couples (player name, score).
    *  If the player dies the scoreboard is updated if and only if it is not worse than the worst current score. The scoreboard
    *  keeps only five results, causing a deletion of the worst score during insertions with full scoreboard.
    */
   public void update() {
	   if (this.subject.getState() == 2) {
		   int points = 0;
		   String id = "";
		   try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
	    	   // TODO insert here data to print on log
			   for (Observer e : this.subject.getObservers()) {
				   if (e.getClass() == PointsAccumulatorObserver.class) {
					   points = ((PointsAccumulatorObserver) e).getPoints();
					   id = ((PointsAccumulatorObserver) e).getName();
				   }
			   }
			   Score toAdd = new Score(points, id, Calendar.getInstance(TimeZone.getTimeZone("Europe/Rome"),Locale.ITALY));
			   if(scores.size() == 0) {
				   scores.add(toAdd);
			   }
			   else if(!(toAdd.compareTo(scores.get(scores.size()-1)) < 0)) {
				   if(scores.size() == 5) {
					   scores.remove(scores.size()-1);
				   }
				   int i = 0;
				   for(Score s : scores) {
					   if(s.compareTo(toAdd) < 0) {
						   break;
					   }
					   i++;
				   }
				   scores.add(i, toAdd);
			   }
	           out.writeObject(scores);
		    } catch (FileNotFoundException e) {
		    	e.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
	   }
   }
			
}
