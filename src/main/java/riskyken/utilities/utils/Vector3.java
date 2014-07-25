package riskyken.utilities.utils;

public class Vector3 {
    public int x;
    public int y;
    public int z;
    
    public Vector3(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vector3 that = (Vector3) o;

        if (x != that.x) return false;
        if (y != that.y) return false;
        if (z != that.z) return false;

        return true;
    }
    
    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		result = prime * result + z;
		return result;
	}
    
    public String hashString() {
        return this.x + ":" + this.y + ":" + this.z;
    }
}
