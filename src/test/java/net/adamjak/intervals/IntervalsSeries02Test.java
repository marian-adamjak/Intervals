/*
 * Copyright (c) 2015, Marian Adamjak
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the <organization> nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.

 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package test.java.net.adamjak.intervals;

import java.util.ArrayList;
import java.util.List;
import net.adamjak.intervals.ArgumentNullException;
import net.adamjak.intervals.Interval;
import net.adamjak.intervals.IntervalsSeries;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author madamjak
 */
public class IntervalsSeries02Test {
    private static final IntervalsSeries<Integer, String> EMPTY_SERIES = new IntervalsSeries<>();
    private static final IntervalsSeries<Integer, String> TESTED_SERIES = new IntervalsSeries<>();
    private static final List<Interval<Integer>> INTERVAL_IN_SERIES = new ArrayList<>();
    private static final List<String> VALUES_IN_SERIES = new ArrayList<>();
    
    public IntervalsSeries02Test() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        INTERVAL_IN_SERIES.add(new Interval<>(10, 20));
        INTERVAL_IN_SERIES.add(new Interval<>(20, 30));
        INTERVAL_IN_SERIES.add(new Interval<>(40, 50));
        INTERVAL_IN_SERIES.add(new Interval<>(60, 70));
        INTERVAL_IN_SERIES.add(new Interval<>(70, 80));
        VALUES_IN_SERIES.add("First");
        VALUES_IN_SERIES.add("Second");
        VALUES_IN_SERIES.add("Third");
        VALUES_IN_SERIES.add("Fourth");
        VALUES_IN_SERIES.add("Fifth");
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        EMPTY_SERIES.clear();
        TESTED_SERIES.clear();
        for (int i = 0; i < INTERVAL_IN_SERIES.size(); i++) {
            TESTED_SERIES.putValue(INTERVAL_IN_SERIES.get(i), VALUES_IN_SERIES.get(i));
        }
    }

    @After
    public void tearDown() {
    }

    
    /**
     * Test of changeEdges method, of class IntervalsSeries.
     */
    @Test
    public void testChangeEdges01() {
        IntervalsSeries<Integer, String> series = new IntervalsSeries<>();
        List<Interval<Integer>> intervals = new ArrayList<>();
        intervals.add(new Interval<>(10, 20));
        intervals.add(new Interval<>(30, 40));
        intervals.add(new Interval<>(55, 60));
        for (Interval<Integer> interval : intervals) {
            series.putValue(interval, "value");
        }
        assertTrue("Bad method changeEdges - Test01", series.changeEdges(new Interval<>(30, 40), new Interval<>(25, 45)));
        assertTrue("Bad method changeEdges - Test02", series.getIntevals().contains(new Interval<>(25, 45)));
        assertTrue("Bad method changeEdges - Test03", series.changeEdges(new Interval<>(55, 60), new Interval<>(50, 60)));
        assertTrue("Bad method changeEdges - Test04", series.getIntevals().contains(new Interval<>(50, 60)));
        assertFalse("Bad method changeEdges - Test05", series.changeEdges(new Interval<>(55, 60), new Interval<>(45, 60)));
        assertFalse("Bad method changeEdges - Test06", series.getIntevals().contains(new Interval<>(45, 60)));
        assertTrue("Bad method changeEdges - Test07", series.changeEdges(new Interval<>(50, 60), new Interval<>(50, 65)));
        assertTrue("Bad method changeEdges - Test08", series.getIntevals().contains(new Interval<>(50, 65)));
        assertTrue("Bad method changeEdges - Test09", series.changeEdges(new Interval<>(10, 20), new Interval<>(null, 15)));
        assertTrue("Bad method changeEdges - Test10", series.getIntevals().contains(new Interval<>(null, 15)));
        assertFalse("Bad method changeEdges - Test11", series.changeEdges(new Interval<>(50, 65), new Interval<>(35, 60)));
        assertFalse("Bad method changeEdges - Test12", series.getIntevals().contains(new Interval<>(35, 60)));
        assertFalse("Bad method changeEdges - Test13", EMPTY_SERIES.changeEdges(new Interval<>(50, 65), new Interval<>(35, 60)));
    }

    /**
     * Test of changeEdges method, of class IntervalsSeries. Null old interval
     */
    @Test(expected = ArgumentNullException.class)
    public void testChangeEdges02() {
        TESTED_SERIES.changeEdges(null, new Interval<>(0, 1));
    }

    /**
     * Test of changeEdges method, of class IntervalsSeries. Null new interval
     */
    @Test(expected = ArgumentNullException.class)
    public void testChangeEdges03() {
        TESTED_SERIES.changeEdges(new Interval<>(0, 1), null);
    }

    /**
     * Test of insertNew method, of class IntervalsSeries.
     */
    @Test
    public void testInsertNew01() {
        IntervalsSeries<Integer, String> series = new IntervalsSeries<>();
        List<Interval<Integer>> intervals = new ArrayList<>();
        intervals.add(new Interval<>(10, 20));
        intervals.add(new Interval<>(30, 40));
        intervals.add(new Interval<>(50, 60));
        intervals.add(new Interval<>(70, 80));
        List<String> values = new ArrayList<>();
        values.add("01");
        values.add("02");
        values.add("03");
        values.add("04");
        for (int i = 0; i < intervals.size(); i++) {
            series.putValue(intervals.get(i), values.get(i));
        }
        series.insertNew(new Interval<>(0, 5), "05");
        series.insertNew(new Interval<>(15, 25), "06");
        series.insertNew(new Interval<>(28, 55), "07");
        series.insertNew(new Interval<>(61, 62), "08");
        series.insertNew(new Interval<>(75, 76), "09");
        series.insertNew(new Interval<>(79, 85), "10");
        series.insertNew(new Interval<>(100, 110), "11");
        assertEquals("Bad method InsertNew - test 1", "05", series.getValue(new Interval<>(0, 5)));
        assertEquals("Bad method InsertNew - test 2", "01", series.getValue(new Interval<>(10, 15)));
        assertEquals("Bad method InsertNew - test 3", "06", series.getValue(new Interval<>(15, 25)));
        assertEquals("Bad method InsertNew - test 4", "07", series.getValue(new Interval<>(28, 55)));
        assertEquals("Bad method InsertNew - test 5", "03", series.getValue(new Interval<>(55, 60)));
        assertEquals("Bad method InsertNew - test 6", "08", series.getValue(new Interval<>(61, 62)));
        assertEquals("Bad method InsertNew - test 7", "04", series.getValue(new Interval<>(70, 75)));
        assertEquals("Bad method InsertNew - test 8", "09", series.getValue(new Interval<>(75, 76)));
        assertEquals("Bad method InsertNew - test 9", "04", series.getValue(new Interval<>(76, 79)));
        assertEquals("Bad method InsertNew - test 10", "10", series.getValue(new Interval<>(79, 85)));
        assertEquals("Bad method InsertNew - test 11", "11", series.getValue(new Interval<>(100, 110)));
        assertFalse("Bad method InsertNew - test 12", series.getIntevals().contains(new Interval<>(30, 40)));

    }

    /**
     * Test of insertNew method, of class IntervalsSeries. Null interval
     */
    @Test(expected = ArgumentNullException.class)
    public void testInsertNew02() {
        TESTED_SERIES.insertNew(null, "abc");
    }

    /**
     * Test of insertNew method, of class IntervalsSeries. Null value
     */
    @Test(expected = ArgumentNullException.class)
    public void testInsertNew03() {
        TESTED_SERIES.insertNew(new Interval<>(100, 110), null);
    }

    /**
     * Test of erase method, of class IntervalsSeries.
     */
    @Test
    public void testErase01() {
        IntervalsSeries<Integer, String> series = new IntervalsSeries<>();
        List<Interval<Integer>> intervals = new ArrayList<>();
        intervals.add(new Interval<>(10, 20));
        intervals.add(new Interval<>(30, 40));
        intervals.add(new Interval<>(50, 60));
        intervals.add(new Interval<>(70, 80));
        List<String> values = new ArrayList<>();
        values.add("01");
        values.add("02");
        values.add("03");
        values.add("04");
        for (int i = 0; i < intervals.size(); i++) {
            series.putValue(intervals.get(i), values.get(i));
        }
        series.erase(new Interval<>(0, 5));
        series.erase(new Interval<>(0, 11));
        series.erase(new Interval<>(15, 16));
        series.erase(new Interval<>(25, 45));
        series.erase(new Interval<>(60, 70));
        series.erase(new Interval<>(75, 85));
        series.erase(new Interval<>(90, 100));
        assertEquals("Bad method Erase - test 1", "01", series.getValue(new Interval<>(11, 15)));
        assertEquals("Bad method Erase - test 2", "01", series.getValue(new Interval<>(16, 20)));
        assertEquals("Bad method Erase - test 3", "03", series.getValue(new Interval<>(50, 60)));
        assertEquals("Bad method Erase - test 4", "04", series.getValue(new Interval<>(70, 75)));
        assertFalse("Bad method Erase - test 5", series.getIntevals().contains(new Interval<>(0, 5)));
        assertFalse("Bad method Erase - test 6", series.getIntevals().contains(new Interval<>(15, 16)));
        assertFalse("Bad method Erase - test 7", series.getIntevals().contains(new Interval<>(30, 40)));
        assertFalse("Bad method Erase - test 8", series.getIntevals().contains(new Interval<>(90, 100)));
    }
    
    /**
     * Test of erase method, of class IntervalsSeries. Input interval null
     */
    @Test(expected = ArgumentNullException.class)
    public void testErase02() {
        TESTED_SERIES.erase(null);
    }
    
    /**
     * Test of erase method, of class IntervalsSeries. Erase all intervals in series
     */
    @Test()
    public void testErase03() {
        TESTED_SERIES.erase(new Interval<>(null,null));
        assertTrue("Bad method Erase - erase all series", TESTED_SERIES.isEmpty());
    }

    
    
}
