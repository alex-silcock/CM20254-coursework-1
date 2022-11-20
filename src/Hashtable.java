public class Hashtable {
    // uses open addressing to handle collisions
    // hash table stores String keys
    // to compute hash value:
    //      int hashcode = myString.hashCode()
    //      int hashValue = (hashcode & 0x7fffffff) % capacity;
    // capacity is size of hashtable
    // bit mask changes negative values to positive

    int size;
    double loadFactor = 0.0;
    String[] storageArray = new String[this.size];

    public Hashtable(int size) {
	// Add code here for your constructor
        this.size = size;
    }

    public void add(String key) {
	// Add code here for your add method, using linear probing to resolve collisions
    // if table is full, add operation does nothing - shouldn't destroy any entries
    //
    }

    public boolean search(String key) {
	// Add code here for your search method
        return true;
    }

    public void delete(String key) {
	// Add code here for your delete method
    }

    public String get(int i) {
	// Add code here for your get method
        return "hello";
    }

    public static void main (String[] args) {
        // write code for unit tests
        // java docs code
        System.out.println("Hello world");
        Hashtable ht = new Hashtable(5);
        ht.add("hello");
    }

}
