package use;

import java.util.Objects;

public class WeightedEdge {
	private int weight;
	
	public WeightedEdge(int weight) {
		this.weight= weight;
	}
	
	public int getWeight() {
		return weight;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		
		if (obj == null || ! (obj instanceof WeightedEdge))
			return false;
		
		WeightedEdge other = (WeightedEdge) obj;
		
		return weight == other.weight;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(weight);
	}

	public String toString() {
		return String.format("[weight %s]", weight);
	}
}
