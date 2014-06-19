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
        int result = (int) (x ^ (x >>> 16));
        result = 15 * result + (int) (y ^ (y >>> 16));
        result = 15 * result + (int) (z ^ (z >>> 16));
        return result;
    }
    
    public String hashString() {
        return this.x + ":" + this.y + ":" + this.z;
    }
}
