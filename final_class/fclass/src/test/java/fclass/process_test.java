package fclass;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class process_test {
    @Test
    public void simplecalc() {
        process.parser("りんごは200");
        process.parser("りこは3");
        process.parser("値段はりんご*りこ");
        String ret = process.parser("値段を表示").get(0);
        System.out.println(ret);
        assertEquals("600", ret);
    }

    @Test
    public void compcalc() {
        process.parser("りんごは200");
        process.parser("りこは3");
        process.parser("みかんは120");
        process.parser("みこは4");
        process.parser("値段=りんご*りこ+みかんかけるみこ");
        String ret = process.parser("値段を表示").get(0);
        System.out.println(ret);
        assertEquals("1080", ret);
    }

    @Test
    public void compcalc2() {
        process.parser("りんごは200");
        process.parser("りこは3");
        process.parser("みかんは120");
        process.parser("みこは4");
        process.parser("税は1.08");
        process.parser("クーポンは150");
        process.parser("値段=(りんご*りこ+みかんかけるみこ-クーポン)*税");
        String ret = process.parser("値段を表示").get(0);
        System.out.println(ret);
        assertEquals("1004.4", ret);
    }

    @Test
    public void compcalc3() {
        process.parser("りんごは200");
        process.parser("りこは3");
        process.parser("みかんは120");
        process.parser("みこは4");
        process.parser("税は1.08");
        process.parser("クーポンは150");
        process.parser("値段は(りんご*りこ+みかんかけるみこ-クーポン)*1.08");
        String ret = process.parser("値段を表示").get(0);
        System.out.println(ret);
        assertEquals("1004.4", ret);
    }

    @Test
    public void printTest() {
        String ret = process.parser("値段を表示").get(0);
        System.out.println(ret);
        assertEquals("値段", ret);
    }
}
