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
package test.intervals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.adamjak.intervals.ArgumentNullException;
import net.adamjak.intervals.IllegalOvelapException;
import net.adamjak.intervals.Interval;
import net.adamjak.intervals.IntervalsSeries;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author madamjak
 */
public class IntervalsSeries01Test {

    private static final IntervalsSeries<Integer, String> EMPTY_SERIES = new IntervalsSeries<>();
    private static final IntervalsSeries<Integer, String> TESTED_SERIES = new IntervalsSeries<>();
    private static final List<Interval<Integer>> INTERVAL_IN_SERIES = new ArrayList<>();
    private static final List<Interval<Integer>> INTERVAL_NOT_IN_SERIES = new ArrayList<>();
    private static final List<String> VALUES_IN_SERIES = new ArrayList<>();
    private static final List<String> VALUES_NOT_IN_SERIES = new ArrayList<>();

    public IntervalsSeries01Test() {
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

        INTERVAL_NOT_IN_SERIES.add(new Interval<>(0, 5));
        INTERVAL_NOT_IN_SERIES.add(new Interval<>(10, 15));
        INTERVAL_NOT_IN_SERIES.add(new Interval<>(25, 45));
        INTERVAL_NOT_IN_SERIES.add(new Interval<>(75, 85));
        INTERVAL_NOT_IN_SERIES.add(new Interval<>(90, 100));
        VALUES_NOT_IN_SERIES.add("abcd");
        VALUES_NOT_IN_SERIES.add("first");
        VALUES_NOT_IN_SERIES.add("empty");
        VALUES_NOT_IN_SERIES.add("");
        VALUES_NOT_IN_SERIES.add("xyz");
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
     * Test of size method, of class IntervalsSeries.
     */
    @Test
    public void testSize() {
        assertEquals("Bad method size - Empty series has not size equals 0", 0, EMPTY_SERIES.size());
        assertEquals("Bad method size", INTERVAL_IN_SERIES.size(), TESTED_SERIES.size());
    }

    /**
     * Test of isEmpty method, of class IntervalsSeries.
     */
    @Test
    public void testIsEmpty() {
        assertTrue("Bad method isEmpty", EMPTY_SERIES.isEmpty());
        assertFalse("Bad method isEmpty", TESTED_SERIES.isEmpty());
    }

    /**
     * Test of containsInterval method, of class IntervalsSeries. Null input
     */
    @Test(expected = ArgumentNullException.class)
    public void testContainsInterval01() {
        TESTED_SERIES.containsInterval(null);
    }

    /**
     * Test of containsInterval method, of class IntervalsSeries.
     */
    @Test
    public void testContainsInterval02() {
        for (Interval<Integer> interval : INTERVAL_IN_SERIES) {
            assertTrue("Bad method containsInterval - It has not found interval", TESTED_SERIES.containsInterval(interval));
        }
        for (Interval<Integer> interval : INTERVAL_NOT_IN_SERIES) {
            assertFalse("Bad method containsInterval - It has found interval", TESTED_SERIES.containsInterval(interval));
        }
        assertFalse("Bad method - It has found interval in empty series", EMPTY_SERIES.containsInterval(INTERVAL_IN_SERIES.get(0)));
        assertFalse("Bad method - It has found interval in empty series", EMPTY_SERIES.containsInterval(INTERVAL_NOT_IN_SERIES.get(0)));
    }

    /**
     * Test of containsValue method, of class IntervalsSeries.
     */
    @Test
    public void testContainsValue() {
        for (String value : VALUES_IN_SERIES) {
            assertTrue("Bad method containsValue - It has not found value", TESTED_SERIES.containsValue(value));
        }
        for (String value : VALUES_NOT_IN_SERIES) {
            assertFalse("Bad method containsValue - It has found value", TESTED_SERIES.containsValue(value));
        }
        assertFalse("Bad method - It has found value in empty series", EMPTY_SERIES.containsValue(VALUES_IN_SERIES.get(0)));
        assertFalse("Bad method - It has found value in empty series", EMPTY_SERIES.containsValue(VALUES_NOT_IN_SERIES.get(0)));
    }

    /**
     * Test of getValue method, of class IntervalsSeries. Null input.
     */
    @Test(expected = ArgumentNullException.class)
    public void testGetValue01() {
        TESTED_SERIES.getValue(null);
    }

    /**
     * Test of getValue method, of class IntervalsSeries.
     */
    @Test
    public void testGetValue02() {
        for (int i = 0; i < INTERVAL_IN_SERIES.size(); i++) {
            assertEquals("Bad method getValue - Bad or nothing value has found", VALUES_IN_SERIES.get(i), TESTED_SERIES.getValue(INTERVAL_IN_SERIES.get(i)));
        }
        for (Interval<Integer> interval : INTERVAL_NOT_IN_SERIES) {
            assertNull("Bad method getValue - Value has found by interval that is not in series", TESTED_SERIES.getValue(interval));
        }
        assertNull("Bad method getValue - Value has found in empty series", EMPTY_SERIES.getValue(INTERVAL_IN_SERIES.get(0)));
        assertNull("Bad method getValue - Value has found in empty series", EMPTY_SERIES.getValue(INTERVAL_NOT_IN_SERIES.get(0)));
    }

    /**
     * Test of putValue method, of class IntervalsSeries. Put new pair (interval
     * - value)
     */
    @Test
    public void testPutValue01() {
        Interval<Integer> newInterval = new Interval<>(200, 210);
        String newValue = "New Value";
        String output = TESTED_SERIES.putValue(newInterval, newValue);
        assertNull("Bad putValue method - Not null value has returned.", output);
        assertTrue("Bad putValue method - New interval has not found", TESTED_SERIES.containsInterval(newInterval));
        assertTrue("Bad putValue method - New value has not found", TESTED_SERIES.containsValue(newValue));
    }

    /**
     * Test of putValue method, of class IntervalsSeries. Put existed pair
     * (interval - value)
     */
    @Test
    public void testPutValue02() {
        int inputIndex = 1;
        Interval<Integer> newInterval = INTERVAL_IN_SERIES.get(inputIndex);
        String newValue = "New Value";
        String output = TESTED_SERIES.putValue(newInterval, newValue);
        assertEquals("Bad putValue method - Bad value has been returned.", VALUES_IN_SERIES.get(inputIndex), output);
        assertTrue("Bad putValue method - New interval has not found", TESTED_SERIES.containsInterval(newInterval));
        assertTrue("Bad putValue method - New value has not found", TESTED_SERIES.containsValue(newValue));
    }

    /**
     * Test of putValue method, of class IntervalsSeries. Put null Interval
     */
    @Test(expected = ArgumentNullException.class)
    public void testPutValue03() {
        TESTED_SERIES.putValue(null, "abcd");
    }

    /**
     * Test of putValue method, of class IntervalsSeries. Put null value
     */
    @Test(expected = ArgumentNullException.class)
    public void testPutValue04() {
        TESTED_SERIES.putValue(new Interval<>(200, 210), null);
    }

    /**
     * Test of putValue method, of class IntervalsSeries. Put overlaped interval
     */
    @Test(expected = IllegalOvelapException.class)
    public void testPutValue05() {
        Interval<Integer> newInterval = new Interval<>(INTERVAL_IN_SERIES.get(1).getStart() + 1, INTERVAL_IN_SERIES.get(1).getEnd() + 1);
        TESTED_SERIES.putValue(newInterval, "abcd");
    }

    /**
     * Test of remove method, of class IntervalsSeries. Remove pair with existed
     * interval
     */
    @Test
    public void testRemove01() {
        int indexToremove = 1;
        Interval<Integer> intervalToRemove = INTERVAL_IN_SERIES.get(indexToremove);
        String output = TESTED_SERIES.remove(intervalToRemove);
        assertEquals("Bad remove method - Bad value has been returned", VALUES_IN_SERIES.get(indexToremove), output);
        assertEquals("Bad remove method - Bad size after remove", INTERVAL_IN_SERIES.size() - 1, TESTED_SERIES.size());
        assertFalse("Bad remove method - Removed interval has been found", TESTED_SERIES.containsInterval(intervalToRemove));
    }

    /**
     * Test of remove method, of class IntervalsSeries. Remove pair with non
     * existed interval
     */
    @Test
    public void testRemove02() {
        int indexToremove = 1;
        Interval<Integer> intervalToRemove = INTERVAL_NOT_IN_SERIES.get(indexToremove);
        String output = TESTED_SERIES.remove(intervalToRemove);
        assertNull("Bad remove method - Not null value has been returned", output);
        assertEquals("Bad remove method - Bad size after remove", INTERVAL_IN_SERIES.size(), TESTED_SERIES.size());
    }

    /**
     * Test of remove method, of class IntervalsSeries. Remove pair with null
     * interval
     */
    @Test(expected = ArgumentNullException.class)
    public void testRemove03() {
        TESTED_SERIES.remove(null);
    }

    /**
     * Test of clear method, of class IntervalsSeries.
     */
    @Test
    public void testClear() {
        TESTED_SERIES.clear();
        assertEquals("Bad remove method - Bad size after clear", 0, TESTED_SERIES.size());
    }

    /**
     * Test of getIntevals method, of class IntervalsSeries.
     */
    @Test
    public void testGetIntevals() {
        Set<Interval<Integer>> output = TESTED_SERIES.getIntevals();
        for (Interval<Integer> interval : INTERVAL_IN_SERIES) {
            assertTrue("Bad method getIntervals - At least one interval has not been returned", output.contains(interval));
        }
        for (Interval<Integer> interval : INTERVAL_NOT_IN_SERIES) {
            assertFalse("Bad method getIntervals - At least one bad interval has been returned", output.contains(interval));
        }
    }

    /**
     * Test of getValues method, of class IntervalsSeries.
     */
    @Test
    public void testGetValues() {
        Collection<String> output = TESTED_SERIES.getValues();
        for (String value : VALUES_IN_SERIES) {
            assertTrue("Bad method getIntervals - At least one value has not been returned", output.contains(value));
        }
        for (String value : VALUES_NOT_IN_SERIES) {
            assertFalse("Bad method getIntervals - At least one bad value has been returned", output.contains(value));
        }
    }

    /**
     * Test of getPairs method, of class IntervalsSeries.
     */
    @Test
    public void testGetPairs() {
        Set<Map.Entry<Interval<Integer>, String>> pairs = TESTED_SERIES.getPairs();
        assertEquals("Bad method getPairs - different size of returned Set and series", TESTED_SERIES.size(), pairs.size());
        for (Map.Entry<Interval<Integer>, String> pair : pairs) {
            Interval<Integer> interval = pair.getKey();
            String value = pair.getValue();
            int valueIndex = INTERVAL_IN_SERIES.indexOf(interval);
            assertTrue("Bad method getPairs - At least one bad interval has been returned", valueIndex >= 0);
            assertEquals("Bad method getPairs - At least one bad value has been returned", VALUES_IN_SERIES.get(valueIndex), value);
        }
        for (int i = 0; i < INTERVAL_IN_SERIES.size(); i++) {
            Interval<Integer> interval = INTERVAL_IN_SERIES.get(i);
            String value = VALUES_IN_SERIES.get(i);
            boolean foundInterval = false;
            String foundValue = null;
            for (Map.Entry<Interval<Integer>, String> pair : pairs) {
                if (pair.getKey().equals(interval)) {
                    foundInterval = true;
                    foundValue = pair.getValue();
                    break;
                }
            }
            if (foundInterval) {
                assertEquals("Bad method getPairs - Bad value on interval " + interval.toString() + " has been returned", value, foundValue);
            } else {
                fail("Bad method getPairs - Interval " + interval.toString() + " has not been returned in Set");
            }
        }
    }

    /**
     * Test of isOverlapWith method, of class IntervalsSeries. Exclude edges
     */
    @Test
    public void testIsOverlapWith01() {
        List<Interval<Integer>> overlapIntervals = new ArrayList<>();
        overlapIntervals.add(new Interval<>(11, 19));
        overlapIntervals.add(new Interval<>(10, 19));
        overlapIntervals.add(new Interval<>(11, 20));
        overlapIntervals.add(new Interval<>(10, 20));
        overlapIntervals.add(new Interval<>(9, 11));
        overlapIntervals.add(new Interval<>(19, 21));
        overlapIntervals.add(new Interval<>(29, 31));

        List<Interval<Integer>> noOverlapIntervals = new ArrayList<>();
        noOverlapIntervals.add(new Interval<>(5, 9));
        noOverlapIntervals.add(new Interval<>(5, 10));
        noOverlapIntervals.add(new Interval<>(30, 35));
        noOverlapIntervals.add(new Interval<>(30, 40));
        noOverlapIntervals.add(new Interval<>(80, 90));
        noOverlapIntervals.add(new Interval<>(85, 90));

        for (Interval<Integer> interval : overlapIntervals) {
            assertTrue("Bad method isOverlapWith - no overlap has been detected with " + interval.toString(), TESTED_SERIES.isOverlapWith(interval, false));
        }
        for (Interval<Integer> interval : noOverlapIntervals) {
            assertFalse("Bad method isOverlapWith - overlap has been detected with " + interval.toString(), TESTED_SERIES.isOverlapWith(interval, false));
        }
    }

    /**
     * Test of isOverlapWith method, of class IntervalsSeries. Include edges
     */
    @Test
    public void testIsOverlapWith02() {
        List<Interval<Integer>> overlapIntervals = new ArrayList<>();
        overlapIntervals.add(new Interval<>(11, 19));
        overlapIntervals.add(new Interval<>(5, 10));
        overlapIntervals.add(new Interval<>(10, 19));
        overlapIntervals.add(new Interval<>(11, 20));
        overlapIntervals.add(new Interval<>(10, 20));
        overlapIntervals.add(new Interval<>(9, 11));
        overlapIntervals.add(new Interval<>(19, 21));
        overlapIntervals.add(new Interval<>(29, 31));
        overlapIntervals.add(new Interval<>(30, 35));
        overlapIntervals.add(new Interval<>(30, 40));
        overlapIntervals.add(new Interval<>(80, 90));

        List<Interval<Integer>> noOverlapIntervals = new ArrayList<>();
        noOverlapIntervals.add(new Interval<>(5, 9));
        noOverlapIntervals.add(new Interval<>(31, 39));
        noOverlapIntervals.add(new Interval<>(85, 90));

        for (Interval<Integer> interval : overlapIntervals) {
            assertTrue("Bad method isOverlapWith - no overlap has been detected with " + interval.toString(), TESTED_SERIES.isOverlapWith(interval, true));
        }
        for (Interval<Integer> interval : noOverlapIntervals) {
            assertFalse("Bad method isOverlapWith - overlap has been detected with " + interval.toString(), TESTED_SERIES.isOverlapWith(interval, true));
        }
    }

    /**
     * Test of isOverlapWith method, of class IntervalsSeries. Input null
     * interval
     */
    @Test(expected = ArgumentNullException.class)
    public void testIsOverlapWith03() {
        TESTED_SERIES.isOverlapWith(null, true);
    }

    /**
     * Test of getOverlapedExcludeEdgesWith method, of class IntervalsSeries.
     * Include edges
     */
    @Test
    public void testGetOverlapedWith01() {
        List<Interval<Integer>> overlapedIntervals;
        overlapedIntervals = TESTED_SERIES.getOverlapedWith(new Interval<>(5, 10), true);
        assertEquals("Bad method getOverlapedWith - bad size of returned list", 1, overlapedIntervals.size());
        assertEquals("Bad method getOverlapedWith - different interval has been returned", INTERVAL_IN_SERIES.get(0), overlapedIntervals.get(0));
        overlapedIntervals = TESTED_SERIES.getOverlapedWith(new Interval<>(5, 15), true);
        assertEquals("Bad method getOverlapedWith - bad size of returned list", 1, overlapedIntervals.size());
        assertEquals("Bad method getOverlapedWith - different interval has been returned", INTERVAL_IN_SERIES.get(0), overlapedIntervals.get(0));
        overlapedIntervals = TESTED_SERIES.getOverlapedWith(new Interval<>(5, 20), true);
        assertEquals("Bad method getOverlapedWith - bad size of returned list", 2, overlapedIntervals.size());
        assertTrue("Bad method getOverlapedWith - excepted interval has not been returned", overlapedIntervals.contains(INTERVAL_IN_SERIES.get(0)));
        assertTrue("Bad method getOverlapedWith - excepted interval has not been returned", overlapedIntervals.contains(INTERVAL_IN_SERIES.get(1)));
    }

    /**
     * Test of getOverlapedExcludeEdgesWith method, of class IntervalsSeries.
     * Exclude edges
     */
    @Test
    public void testGetOverlapedWith02() {
        List<Interval<Integer>> overlapedIntervals;
        overlapedIntervals = TESTED_SERIES.getOverlapedWith(new Interval<>(5, 10), false);
        assertEquals("Bad method getOverlapedWith - bad size of returned list", 0, overlapedIntervals.size());
        overlapedIntervals = TESTED_SERIES.getOverlapedWith(new Interval<>(5, 15), false);
        assertEquals("Bad method getOverlapedWith - bad size of returned list", 1, overlapedIntervals.size());
        assertEquals("Bad method getOverlapedWith - different interval has been returned", INTERVAL_IN_SERIES.get(0), overlapedIntervals.get(0));
        overlapedIntervals = TESTED_SERIES.getOverlapedWith(new Interval<>(5, 20), false);
        assertEquals("Bad method getOverlapedWith - bad size of returned list", 1, overlapedIntervals.size());
        assertEquals("Bad method getOverlapedWith - different interval has been returned", INTERVAL_IN_SERIES.get(0), overlapedIntervals.get(0));
        overlapedIntervals = TESTED_SERIES.getOverlapedWith(new Interval<>(5, 21), false);
        assertEquals("Bad method getOverlapedWith - bad size of returned list", 2, overlapedIntervals.size());
        assertTrue("Bad method getOverlapedWith - excepted interval has not been returned", overlapedIntervals.contains(INTERVAL_IN_SERIES.get(0)));
        assertTrue("Bad method getOverlapedWith - excepted interval has not been returned", overlapedIntervals.contains(INTERVAL_IN_SERIES.get(1)));
    }

    /**
     * Test of isOverlapWith method, of class IntervalsSeries. Input null
     * interval
     */
    @Test(expected = ArgumentNullException.class)
    public void testGetOverlapedWith03() {
        TESTED_SERIES.getOverlapedWith(null, true);
    }

    /**
     * Test of getIntervalsSorted method, of class IntervalsSeries.
     */
    @Test
    public void testGetIntervalsSorted() {
        IntervalsSeries<Integer, String> series = new IntervalsSeries<>();
        List<Interval<Integer>> intervals = new ArrayList<>();
        intervals.add(new Interval<>(10, 20));
        intervals.add(new Interval<>(30, 40));
        intervals.add(new Interval<>(50, 60));
        intervals.add(new Interval<>(70, 80));
        for (Interval<Integer> interval : intervals) {
            series.putValue(interval, "value");
        }

        List<Interval<Integer>> ascOrderedIntervals01 = listFromSet(series.getIntervalsSorted(IntervalsSeries.SortOrder.ASCENDING));
        List<Interval<Integer>> ascOrderedIntervals02 = listFromSet(series.getIntervalsSorted());
        List<Interval<Integer>> descOrderedIntervals = listFromSet(series.getIntervalsSorted(IntervalsSeries.SortOrder.DESCENDING));

        for (int i = 0; i < intervals.size(); i++) {
            assertEquals("Bad method getIntervalsSorted (ascending) - intervals are not sorted", intervals.get(i), ascOrderedIntervals01.get(i));
            assertEquals("Bad method getIntervalsSorted (no argument) - intervals are not sorted", intervals.get(i), ascOrderedIntervals02.get(i));
        }

        for (int i = 0; i < intervals.size(); i++) {
            assertEquals("Bad method getIntervalsSorted (descendig) - intervals are not sorted", intervals.get(i), descOrderedIntervals.get(intervals.size() - i - 1));
        }

    }

    /**
     * Test of getGaps method, of class IntervalsSeries.
     */
    @Test
    public void testGetGaps01() {
        IntervalsSeries<Integer, String> series = new IntervalsSeries<>();
        List<Interval<Integer>> intervals = new ArrayList<>();
        List<Interval<Integer>> gaps = new ArrayList<>();

        intervals.add(new Interval<>(10, 20));
        gaps.add(new Interval<>(20, 30));
        intervals.add(new Interval<>(30, 40));
        gaps.add(new Interval<>(40, 50));
        intervals.add(new Interval<>(50, 60));
        gaps.add(new Interval<>(60, 70));
        intervals.add(new Interval<>(70, 80));

        for (Interval<Integer> interval : intervals) {
            series.putValue(interval, "value");
        }

        List<Interval<Integer>> output = series.getGaps();
        assertEquals("Bad method getGaps - different size of output lists", gaps.size(), output.size());
        for (Interval<Integer> gap : gaps) {
            assertTrue("Bad method getGaps - Atleast one gap is not in output list", output.contains(gap));
        }
    }

    /**
     * Test of getGaps method, of class IntervalsSeries. Empty series, series
     * with one pair, series without gaps
     */
    @Test
    public void testGetGaps02() {
        assertEquals("Bad method getGaps (empty series) - size of output lists is not zero", 0, EMPTY_SERIES.getGaps().size());
        IntervalsSeries<Integer, String> series = new IntervalsSeries<>();
        series.putValue(new Interval<>(10, 20), "abc");
        assertEquals("Bad method getGaps (empty series) - size of output lists is not zero", 0, series.getGaps().size());
        series.putValue(new Interval<>(20, 30), "abc");
        assertEquals("Bad method getGaps (empty series) - size of output lists is not zero", 0, series.getGaps().size());
    }

    /**
     * Test of getStartMinimum method, of class IntervalsSeries.
     */
    @Test
    public void testExtendMethods() {
        IntervalsSeries<Integer, String> series = new IntervalsSeries<>();
        List<Interval<Integer>> intervals = new ArrayList<>();
        intervals.add(new Interval<>(10, 20));
        intervals.add(new Interval<>(30, 40));
        intervals.add(new Interval<>(50, 60));
        intervals.add(new Interval<>(70, 80));
        for (Interval<Integer> interval : intervals) {
            series.putValue(interval, "value");
        }
        assertEquals("Bad method getStartMinimum", 10L, series.getStartMinimum().longValue());
        assertEquals("Bad method getStartMaximum", 70L, series.getStartMaximum().longValue());
        assertEquals("Bad method getEndMinimum", 20L, series.getEndMinimum().longValue());
        assertEquals("Bad method getEndMaximum", 80L, series.getEndMaximum().longValue());
        assertEquals("Bad method getExtend", new Interval<>(10, 80), series.getExtent());

    }

    /**
     * Test of getIntervalByPoint method, of class IntervalsSeries.
     */
    @Test
    public void testGetIntervalByPoint() {
        IntervalsSeries<Integer, String> series = new IntervalsSeries<>();
        List<Interval<Integer>> intervals = new ArrayList<>();
        intervals.add(new Interval<>(10, 20));
        intervals.add(new Interval<>(30, 40));
        intervals.add(new Interval<>(50, 60));
        intervals.add(new Interval<>(60, 70));
        for (Interval<Integer> interval : intervals) {
            series.putValue(interval, "value");
        }
        assertTrue("Bad method getIntervalByPoint - Test 01", series.getIntervalByPoint(5, true).isEmpty());
        assertTrue("Bad method getIntervalByPoint - Test 02", series.getIntervalByPoint(20, false).isEmpty());
        assertTrue("Bad method getIntervalByPoint - Test 03", series.getIntervalByPoint(25, false).isEmpty());
        assertTrue("Bad method getIntervalByPoint - Test 03", series.getIntervalByPoint(60, false).isEmpty());

        assertTrue("Bad method getIntervalByPoint - Test 04", series.getIntervalByPoint(15, false).contains(new Interval<>(10, 20)));
        assertTrue("Bad method getIntervalByPoint - Test 05", series.getIntervalByPoint(15, true).contains(new Interval<>(10, 20)));
        assertTrue("Bad method getIntervalByPoint - Test 06", series.getIntervalByPoint(20, true).contains(new Interval<>(10, 20)));

        List<Interval<Integer>> twoIntervalsOutput = series.getIntervalByPoint(60, true);
        assertEquals("Bad method getIntervalByPoint - Test 07", 2, twoIntervalsOutput.size());
        assertTrue("Bad method getIntervalByPoint - Test 08", twoIntervalsOutput.contains(new Interval<>(50, 60)));
        assertTrue("Bad method getIntervalByPoint - Test 09", twoIntervalsOutput.contains(new Interval<>(60, 70)));
    }

    /**
     * Test of getTotalLength method, of class IntervalsSeries.
     */
    @Test
    public void testGetTotalLength01() {
        IntervalsSeries<Integer, String> series = new IntervalsSeries<>();
        List<Interval<Integer>> intervals = new ArrayList<>();
        intervals.add(new Interval<>(10, 20));
        intervals.add(new Interval<>(30, 40));
        intervals.add(new Interval<>(50, 60));
        intervals.add(new Interval<>(60, 70));
        for (Interval<Integer> interval : intervals) {
            series.putValue(interval, "value");
        }
        assertEquals("Bad method getTotalLength", 40, series.getTotalLength().longValue());
        assertNull("Bad method getTotalLength", EMPTY_SERIES.getTotalLength());
    }

    /**
     * Test of getTotalLength method, of class IntervalsSeries.
     */
    @Test(expected = UnsupportedOperationException.class)
    public void testGetTotalLength02() {
        IntervalsSeries<String, String> series = new IntervalsSeries<>();
        List<Interval<String>> intervals = new ArrayList<>();
        intervals.add(new Interval<>("a", "c"));
        intervals.add(new Interval<>("d", "e"));

        for (Interval<String> interval : intervals) {
            series.putValue(interval, "value");
        }
        series.getTotalLength();
    }

    private <U> List<U> listFromSet(Set<U> input) {
        if (input == null) {
            return null;
        }
        List<U> output = new ArrayList<>();
        Iterator<U> iterator = input.iterator();
        while (iterator.hasNext()) {
            output.add(iterator.next());
        }
        return Collections.unmodifiableList(output);
    }

}
