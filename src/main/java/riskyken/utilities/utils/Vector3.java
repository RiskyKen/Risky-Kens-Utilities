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
    
    public boolean equals(Vector3 blockCoord) {
		if (this.x == blockCoord.x) {
			if (this.y == blockCoord.y) {
				if (this.z == blockCoord.z) {
					return true;
				}
			}
		}
		return false;
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
    public int hashCode()
    {
        int result = (int) (x ^ (x >>> 32));
        result = 31 * result + (int) (y ^ (y >>> 32));
        result = 31 * result + (int) (z ^ (z >>> 32));
        return result;
    }
}
