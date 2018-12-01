package managers.observers.scoreboard;

import java.io.Serializable;
import java.util.Date;


public class Score implements Comparable<Score>, Serializable {
	private int value;
	private String id;
	private Date date;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + value;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Score other = (Score) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (value != other.value)
			return false;
		return true;
	}
	
	public Score(int value, String id, Date date) {
		this.value = value;
		this.id = id;
		this.date = date;
	}
	
	public Score(int value, String id) {
		this.value = value;
		this.id = id;
	}
	
	public int getValue() {
		return value;
	}
	
	@Override
	public int compareTo(Score other) {
		if(value > other.getValue()) {
			return 1;
		}
		else if (value == other.getValue())
			return 0;
		else
			return -1;
	}
}
