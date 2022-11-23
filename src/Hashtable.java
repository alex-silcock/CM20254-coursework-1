public class Hashtable {
    // uses open addressing to handle collisions
    // hash table stores String keys
    // to compute hash value:
    //      int hashcode = myString.hashCode()
    //      int hashValue = (hashcode & 0x7fffffff) % capacity;
    // capacity is size of hashtable
    // bit mask changes negative values to positive

    int capacity;
    int filledSlots = 0;
    String[] storageArray;

    public Hashtable(int size) {
	// Add code here for your constructor
        this.capacity = size;
        this.storageArray = new String[this.capacity];
    }

    public int getHashValue(String key) {
        int hashcode = key.hashCode();
        int hashValue = (hashcode & 0x7fffffff) % this.capacity;
        return hashValue;
    }

    public void add(String key) {
	// Add code here for your add method, using linear probing to resolve collisions
    // if table is full, add operation does nothing - shouldn't destroy any entries
    // does not have to add strings that are already in the table
    // linear probing if collision - search forwards until finds an empty slot, wrap around at end of array
    if (this.filledSlots == this.capacity) { return; }

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

    public boolean search(String key) {
	// Add code here for your search method
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

    public void deleteAndRehash(int hashValue) {
        int nextHash;
        if (hashValue == this.capacity - 1) {
            nextHash = 0;
        } else {
            nextHash = hashValue + 1;
        }
        while (this.storageArray[nextHash] != null) {
            String key = this.storageArray[nextHash];
            this.storageArray[nextHash] = null;
            add(key);
            nextHash ++;
            if (nextHash > this.capacity) {
                nextHash = 0;
            }
        }
    }

    public void delete(String key) {
        // TODO - deleting - reorganise the cluster from the free slot we've created, to re-fill that slot if necessary
        // Probe the next values, if the hash value is the value of the index we deleted from then shift to that index
        int hashValue = getHashValue(key);
        if (this.storageArray[hashValue] == null) { return; }
        if (this.storageArray[hashValue].compareTo(key) == 0) {
            this.storageArray[hashValue] = null;
            // TODO - iterate to the next value which is null, rehashing each value and inserting it into the hash table again

            deleteAndRehash(hashValue);

        }
        else {
            // linear probe
            int probeCount = 0;
            boolean alreadyWrapped = false;
            while (this.storageArray[hashValue + probeCount] != null) {
                if (this.storageArray[hashValue + probeCount].compareTo(key) == 0) {
                    this.storageArray[hashValue + probeCount] = null;
                    deleteAndRehash(hashValue + probeCount);
                }
                probeCount++;
                // handle the wrap around
                if (hashValue + probeCount == this.capacity) {
                    if (alreadyWrapped) {
                        return;
                    }
                    alreadyWrapped = true;
                    probeCount = -hashValue;
                }
            }
        }

    }

    public String get(int i) {
        return this.storageArray[i];
    }

    public void printTable() {
        for (int i = 0; i < this.capacity; i++) {
            System.out.println(i + " : " + this.storageArray[i]);
        }
    }

    public static void main (String[] args) {
        // TODO - java docs code
        // abcdefg and abcdegH and abcdeh) have the same hash value
        // AaAa BBBB AaBB BBAa have the same hash value
        // AaAaAa AaAaBB AaBBAa AaBBBB BBAaAa BBAaBB BBBBAa BBBBBB
        Hashtable table = new Hashtable(8);
        String[] words = {"apple0", "apple1", "apple2", "Apple1", "Apple2"};

        for (String s : words) {
            table.add(s);
            System.out.println(s + " " + table.getHashValue(s));
        }
        table.printTable();
        table.delete("apple1");
        System.out.println();
        table.printTable();
    }

}
