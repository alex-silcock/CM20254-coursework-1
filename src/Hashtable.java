/**
 * Hashtable
 *
 * @author Alex Silcock
 * @version 1.0
 */
public class Hashtable {
    int capacity;
    int filledSlots = 0;
    String[] storageArray;

    /**
     * Constructor for the hash table
     *
     * @param size
     *      The number of strings that can be stored in the hash table
     */
    public Hashtable(int size) {
        this.capacity = size;
        this.storageArray = new String[this.capacity];
    }

    /**
     * Returns the Hash Value for the given key
     *
     * @param key
     *      The string to be hashed
     * @return The hash value of the string
     */
    public int getHashValue(String key) {
        return (key.hashCode() & 0x7fffffff) % this.capacity;
    }

    /**
     * Adds a string to the hash table
     *
     * @param key
     *      The string to be added to the hash table
     */
    public void add(String key) {
        if (this.filledSlots == this.capacity || search(key)) {
            return;
        }

        int hashValue = getHashValue(key);
        if (this.storageArray[hashValue] == null){
            this.storageArray[hashValue] = key;
        }
        else {
            // linear probe
            int probeCount = 0;
            while (this.storageArray[hashValue + probeCount] != null) {
                probeCount++;
                // handle the wrap around
                if (hashValue + probeCount == this.capacity) {
                    probeCount = -hashValue;
                }
            }
            this.storageArray[hashValue + probeCount] = key;
        }
        this.filledSlots += 1;
    }

    /**
     * Searches to see if a string exists in the hash table
     *
     * @param key
     *      The string to be searched for
     * @return
     *      True if the string exists in the hash table, False if the string doesn't exist
     *      in the hash table
     */
    public boolean search(String key) {
        int hashValue = getHashValue(key);
        if (this.storageArray[hashValue] == null) { return false; }
        if (this.storageArray[hashValue].compareTo(key) == 0) {
            return true;
        }
        else {
            // linear probe
            int probeCount = 0;
            boolean alreadyWrapped = false;
            while (this.storageArray[hashValue + probeCount] != null) {
                if (this.storageArray[hashValue + probeCount].compareTo(key) == 0) {
                    return true;
                }
                probeCount++;

                // handle the wrap around
                if (hashValue + probeCount == this.capacity) {
                    // if we have already wrapped around, word not in table
                    if (alreadyWrapped) {
                        return false;
                    }
                    probeCount = -hashValue;
                    alreadyWrapped = true;
                }
            }
        }
        return false;
    }

    /**
     * Deletes the string from the hash table and rehashes everything after the deleted string
     * @param hashValue
     *      The hash value of the string to be deleted
     */
    public void deleteAndRehash(int hashValue) {
        this.storageArray[hashValue] = null;
        int nextHash;
        if (hashValue == this.capacity - 1) {
            nextHash = 0;
        } else {
            nextHash = hashValue + 1;
        }

        while (this.storageArray[nextHash] != null) {
            String key = this.storageArray[nextHash];
            this.storageArray[nextHash] = null;
            this.filledSlots -= 1;
            add(key);
            nextHash++;

            if (nextHash == this.capacity) {
                nextHash = 0;
            }
        }
        this.filledSlots -= 1;
    }

    /**
     * Deletes a string from the hash table
     * @param key
     *      The string to be deleted from the table
     */
    public void delete(String key) {
        int hashValue = getHashValue(key);
        if (this.storageArray[hashValue] == null) { return; }
        if (this.storageArray[hashValue].compareTo(key) == 0) {
            deleteAndRehash(hashValue);
        }
        else {
            // linear probe
            int probeCount = 0;
            boolean alreadyWrapped = false;
            while (this.storageArray[hashValue + probeCount] != null) {
                if (this.storageArray[hashValue + probeCount].compareTo(key) == 0) {
                    deleteAndRehash(hashValue + probeCount);
                }
                probeCount++;
                // handle the wrap around
                if (hashValue + probeCount == this.capacity) {
                    if (alreadyWrapped) { return; }
                    probeCount = -hashValue;
                    alreadyWrapped = true;
                }

            }
        }
    }

    /**
     * Returns the string at index i in the hash table
     * @param i
     *      The index to be returned
     * @return
     *      String at index i
     */
    public String get(int i) {
        return this.storageArray[i];
    }

    /**
     * Prints the hash table
     */
    public void printTable() {
        for (int i = 0; i < this.capacity; i++) {
            System.out.println(i + " : " + this.storageArray[i]);
        }
    }

    /**
     * Finds the maximum cluster in the hash table
     * @return
     *      int - the maximum cluster
     */
    public int getMaxCluster() {
        // TODO - find the cluster if there is a wrap around
        int cluster_length = 0;
        int max_cluster = 0;
        if (this.filledSlots == this.capacity) { return this.filledSlots; }

        for (int i = 0; i < this.capacity; i++) {
            if (this.storageArray[i] == null) {
                if (cluster_length > max_cluster) {
                    max_cluster = cluster_length;
                }
                cluster_length = 0;
            } else {
                cluster_length++;
            }

        }
        return max_cluster;
    }

    public static void main (String[] args) {
        // TODO - java docs code
        // abcdefg and abcdegH and abcdeh) have the same hash value
        // AaAa BBBB AaBB BBAa have the same hash value
        // AaAaAa AaAaBB AaBBAa AaBBBB BBAaAa BBAaBB BBBBAa BBBBBB
        Hashtable table = new Hashtable(8);
        String[] words = {"AaAaAa" , "AaAaBB",  "AaBBAa",  "AaBBBB",  "BBAaAa",  "BBAaBB",  "BBBBAa", "BBBBBB"};

        for (String s : words) {
            table.add(s);
        }
        table.printTable();
        table.delete("AaAaAa");
        System.out.println();
        table.printTable();
    }
}
