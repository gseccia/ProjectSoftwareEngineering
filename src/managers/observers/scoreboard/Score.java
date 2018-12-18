package managers.observers.scoreboard;

import java.io.Serializable;
import java.util.Calendar;

public class Score implements Comparable<Score>, Serializable {
	private int value;
	private String id;
	private Calendar date;
	
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
	
	public Score(int value, String id, Calendar date) {
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
	
	@Override
	public String toString() {
		return id+" "+value+" "+(date.get(Calendar.MONTH)+1)+"/"+date.get(Calendar.DAY_OF_MONTH)+"/"+date.get(Calendar.YEAR);
	}
}
