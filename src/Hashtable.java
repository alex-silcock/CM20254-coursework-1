public class Hashtable {
    int capacity;
    int filledSlots = 0;
    String[] storageArray;

    public Hashtable(int size) {
        this.capacity = size;
        this.storageArray = new String[this.capacity];
    }

    public int getHashValue(String key) {
        return (key.hashCode() & 0x7fffffff) % this.capacity;
    }

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
                    if (alreadyWrapped) {
                        return;
                    }
                    probeCount = -hashValue;
                    alreadyWrapped = true;
                }
            }
        }
    }

    public String get(int i) {
        return this.storageArray[i];
    }

    public void printTable() {
//        int cluster_length = 0;
//        int max_cluster = 0;
        for (int i = 0; i < this.capacity; i++) {
            System.out.println(i + " : " + this.storageArray[i]);
//            if (this.storageArray[i] != null) {
//                cluster_length ++;
//            } else {
//                if (cluster_length > max_cluster) {
//                    max_cluster = cluster_length;
//                }
//                cluster_length = 0;
//            }
        }
//        System.out.println(max_cluster);
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
