import org.testng.annotations.Test;
import static org.junit.Assert.*;

public class HashtableTests {
    @Test
    public void precheck1() {
        Hashtable table = new Hashtable(8);
        table.add("apple0");
        table.add("apple2");
        table.add("apple4");
        assertEquals("apple2", table.get(0));
        assertNull(table.get(1));
        assertEquals("apple4", table.get(2));
        assertNull(table.get(3));
        assertNull(table.get(4));
        assertNull(table.get(5));
        assertEquals("apple0", table.get(6));
        assertNull(table.get(7));
    }

    @Test
    public void precheck2() {
        Hashtable table = new Hashtable(8);
        assertFalse(table.search("apple0"));
        table.add("apple0");
        assertTrue(table.search("apple0"));
        assertFalse(table.search("Apple0"));
        table.add("apple2");
        assertTrue(table.search("apple2"));
        table.add("apple4");
        assertTrue(table.search("apple4"));
    }

    @Test
    public void precheck3() {
        Hashtable table = new Hashtable(8);
        table.add("apple0");
        table.add("apple2");
        table.add("apple4");
        assertTrue(table.search("apple2"));
        table.delete("apple2");
        assertFalse(table.search("apple2"));
        assertTrue(table.search("apple4"));
    }

    @Test
    public void collisions1() {
        Hashtable table = new Hashtable(4);
        String[] words = {"AaAa", "BBBB", "AaBB", "BBAa"};

        for (String s : words) {
            table.add(s);
        }
        assertEquals("AaAa", table.get(0));
        assertEquals("BBBB", table.get(1));
        assertEquals("AaBB", table.get(2));
        assertEquals("BBAa", table.get(3));
    }

    @Test
    public void wrapAround1() {
        Hashtable table = new Hashtable(5);
        String[] words = {"abcdef", "abcdefg", "abcdegH", "abcdeh)"};

        for (String s : words) {
            table.add(s);
        }
        assertEquals("abcdeh)", table.get(0));
        assertNull(table.get(1));
        assertEquals("abcdefg", table.get(2));
        assertEquals("abcdegH", table.get(3));
        assertEquals("abcdef", table.get(4));
    }

    @Test
    public void delete1() {
        Hashtable table = new Hashtable(8);
        String[] words = {"apple0", "apple2", "apple9", "Apple1", "Apple2"};
        assertFalse(table.search("Apple1"));

        for (String s : words) {
            table.add(s);
        }
        assertEquals("Apple2", table.get(2));
        table.delete("Apple1");
        assertTrue(table.search("Apple2"));
    }

    @Test
    public void delete2() {
        Hashtable table = new Hashtable(8);
        String[] words = {"apple0", "apple1", "apple2", "Apple1", "Apple2"};

        for (String s : words) {
            table.add(s);
        }
        table.delete("apple1");
        assertEquals("apple2", table.get(0));
        assertEquals("Apple2", table.get(1));
        assertEquals("Apple1", table.get(7));
        assertTrue(table.search("Apple2"));
    }

    @Test
    public void deleteFromFullTableOfSameValues() {
        Hashtable table = new Hashtable(8);
        String[] words = {"AaAaAa" , "AaAaBB",  "AaBBAa",  "AaBBBB",  "BBAaAa",  "BBAaBB",  "BBBBAa", "BBBBBB"};

        for (String s : words) {
            table.add(s);
        }

        table.delete("AaAaAa");
        assertEquals("AaAaBB", table.get(0));
        assertEquals("AaBBAa", table.get(1));
        assertEquals("AaBBBB", table.get(2));
        assertEquals("BBAaAa", table.get(3));
        assertEquals("BBAaBB", table.get(4));
        assertEquals("BBBBAa", table.get(5));
        assertEquals("BBBBBB", table.get(6));
        assertNull(table.get(7));
    }

    @Test
    public void deleteFromEndAndMoveStartBack() {
        Hashtable table = new Hashtable(8);
        String[] words = {"AaAaAaa" , "AaAaBBa",  "AaBBAaa",  "AaBBBBa",  "BBAaAaa",  "BBAaBBa",  "BBBBAaa", "apple0"};

        for (String s : words) {
            table.add(s);
        }
        table.delete("AaAaAaa");
        assertNull(table.get(0));
        assertEquals("AaAaBBa", table.get(1));
        assertEquals("AaBBAaa", table.get(2));
        assertEquals("AaBBBBa", table.get(3));
        assertEquals("BBAaAaa", table.get(4));
        assertEquals("BBAaBBa", table.get(5));
        assertEquals("BBBBAaa", table.get(6));
        assertEquals("apple0", table.get(7));
    }

    @Test
    public void addToFullTable() {
        Hashtable table = new Hashtable(4);
        String[] words = {"apple1", "apple2", "apple3", "apple4"};

        for (String s : words) {
            table.add(s);
        }
        table.add("apple5");
        assertEquals(words[1], table.get(0));
        assertEquals(words[2], table.get(1));
        assertEquals(words[3], table.get(2));
        assertEquals(words[0], table.get(3));
        assertFalse(table.search("apple5"));
    }

    @Test
    public void addSameString() {
        Hashtable table = new Hashtable(6);
        String[] words = {"Hello", "Hello", "Hello", "Hello", "Hello", "Hello"};

        for (String s : words) {
            table.add(s);
        }
        assertNull(table.get(0));
        assertNull(table.get(1));
        assertEquals("Hello", table.get(2));
        assertNull(table.get(3));
        assertNull(table.get(4));
        assertNull(table.get(5));
    }

    @Test
    public void caseSensitiveAdd() {
        Hashtable table = new Hashtable(6);
        String[] words = {"Hello", "hello", "HELLO", "HeLlO", "hElLo", "HEllO"};

        for (String s : words) {
            table.add(s);
        }
        assertEquals("HELLO", table.get(0));
        assertEquals("hElLo", table.get(1));
        assertEquals("Hello", table.get(2));
        assertEquals("HEllO", table.get(3));
        assertEquals("hello", table.get(4));
        assertEquals("HeLlO", table.get(5));
    }

    @Test
    public void searchingFullTable() {
        Hashtable table = new Hashtable(4);
        String[] words = {"apple1", "apple2", "apple3", "apple4"};

        for (String s : words) {
            table.add(s);
        }
        table.add("apple5");
        assertFalse(table.search("apple5"));
    }

    @Test
    public void checkInitialValuesAreNull() {
        Hashtable table = new Hashtable(10);

        for (int i = 0; i < 10; i++) {
            assertNull(table.get(i));
        }
    }

    @Test
    public void deleteEmptyTable() {
        Hashtable table = new Hashtable(5);
        table.delete("");

        for (int i = 0; i < 5; i++) {
            assertNull(table.get(i));
        }
    }

    @Test(expectedExceptions = ArrayIndexOutOfBoundsException.class)
    public void outOfRangeGet() {
        Hashtable table = new Hashtable(5);
        table.get(6);
    }
}
