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
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.adamjak.intervals.ArgumentNullException;
import net.adamjak.intervals.Interval;
import net.adamjak.intervals.Interval.PositionAgainstInterval;
import net.adamjak.intervals.Measurable;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test class to perform Unit test for Interval.class
 *
 * @author Marian Adamjak
 */
public class IntervalTest {

    private final Calendar calendar = Calendar.getInstance();

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getStart method, of class Interval.
     */
    @Test
    public void testGetStart() {
        Integer startValue = 1;
        Integer endValue = 2;
        Interval<Integer> instance01 = new Interval<>(startValue, endValue);
        Interval<Integer> instance02 = new Interval<>(endValue, startValue);
        assertEquals(startValue, instance01.getStart());
        assertEquals(startValue, instance02.getStart());
    }

    /**
     * Test of getEnd method, of class Interval.
     */
    @Test
    public void testGetEnd() {
        Integer startValue = 1;
        Integer endValue = 2;
        Interval<Integer> instance01 = new Interval<>(startValue, endValue);
        Interval<Integer> instance02 = new Interval<>(endValue, startValue);
        assertEquals(endValue, instance01.getEnd());
        assertEquals(endValue, instance02.getEnd());
    }

    /**
     * Test of isInfiniteStart method, of class Interval.
     */
    @Test
    public void testIsInfiniteStart() {
        Integer startValue = 1;
        Integer endValue = 2;
        Interval<Integer> instanceNoInfiniteStart = new Interval<>(startValue, endValue);
        Interval<Integer> instanceInfiniteStart = new Interval<>(null, endValue);
        assertFalse("Bad method isInfiniteStart with not null start", instanceNoInfiniteStart.isInfiniteStart());
        assertTrue("Bad method isInfiniteStart with null start", instanceInfiniteStart.isInfiniteStart());
    }

    /**
     * Test of isInfiniteEnd method, of class Interval.
     */
    @Test
    public void testIsInfiniteEnd() {
        Integer startValue = 1;
        Integer endValue = 2;
        Interval<Integer> instanceNoInfiniteEnd = new Interval<>(startValue, endValue);
        Interval<Integer> instanceInfiniteEnd = new Interval<>(startValue, null);
        assertFalse("Bad method isInfiniteEnd with not null start", instanceNoInfiniteEnd.isInfiniteEnd());
        assertTrue("Bad method isInfiniteEnd with null start", instanceInfiniteEnd.isInfiniteEnd());
    }

    /**
     * Test of isEqualStart method with non null edges, of class Interval.
     */
    @Test
    public void testIsEqualStartNotNullEdges() {
        Integer startValue0 = 1;
        Integer startValue1 = 2;
        Integer endValue = 3;
        Interval<Integer> instance01 = new Interval<>(startValue0, endValue);
        Interval<Integer> instance02 = new Interval<>(startValue0, endValue);
        Interval<Integer> instance03 = new Interval<>(startValue1, endValue);
        assertTrue("Bad method isEqualStart with equal non null start edges", instance01.isEqualStart(instance02));
        assertFalse("Bad method isEqualStart with equal different non null start edges", instance01.isEqualStart(instance03));
    }

    /**
     * Test of isEqualStart method with null edges, of class Interval.
     */
    @Test
    public void testIsEqualStartNullEdges() {
        Integer startValue0 = null;
        Integer startValue1 = 2;
        Integer endValue = 3;
        Interval<Integer> instance01 = new Interval<>(startValue0, endValue);
        Interval<Integer> instance02 = new Interval<>(startValue0, endValue);
        Interval<Integer> instance03 = new Interval<>(startValue1, endValue);
        assertTrue("Bad method isEqualStart with equal non null start edges", instance01.isEqualStart(instance02));
        assertFalse("Bad method isEqualStart with equal different non null start edges", instance01.isEqualStart(instance03));
    }

    /**
     * Test of isEqualEnd method with non null edges, of class Interval.
     */
    @Test
    public void testIsEqualEndtNotNullEdges() {
        Integer startValue = 1;
        Integer endValue0 = 3;
        Integer endValue1 = 4;
        Interval<Integer> instance01 = new Interval<>(startValue, endValue0);
        Interval<Integer> instance02 = new Interval<>(startValue, endValue0);
        Interval<Integer> instance03 = new Interval<>(startValue, endValue1);
        assertTrue("Bad method isEqualEnd with equal non null end edges", instance01.isEqualEnd(instance02));
        assertFalse("Bad method isEqualEnd with equal different non null end edges", instance01.isEqualEnd(instance03));
    }

    /**
     * Test of isEqualStart method with null edges, of class Interval.
     */
    @Test
    public void testIsEqualEndNullEdges() {
        Integer startValue = 1;
        Integer endValue0 = null;
        Integer endValue1 = 4;
        Interval<Integer> instance01 = new Interval<>(startValue, endValue0);
        Interval<Integer> instance02 = new Interval<>(startValue, endValue0);
        Interval<Integer> instance03 = new Interval<>(startValue, endValue1);
        assertTrue("Bad method isEqualEnd with equal null end edges", instance01.isEqualEnd(instance02));
        assertFalse("Bad method isEqualEnd with equal different null end edges", instance01.isEqualEnd(instance03));
    }

    /**
     * Test of positionAgainstInterval method, of class Interval. Edges are not
     * null.
     */
    @Test
    public void testPositionAgainstIntervalNotNull() {
        Integer insidePosition = 15;
        Integer start = insidePosition - 5;
        Integer end = insidePosition + 5;
        Interval<Integer> intInterval = new Interval<>(start, end);
        Map<PositionAgainstInterval, Integer> testPositions = new HashMap<>();
        testPositions.put(PositionAgainstInterval.Before, start - 1);
        testPositions.put(PositionAgainstInterval.AtStart, start);
        testPositions.put(PositionAgainstInterval.Between, insidePosition);
        testPositions.put(PositionAgainstInterval.AtEnd, end);
        testPositions.put(PositionAgainstInterval.After, end + 1);

        for (PositionAgainstInterval pos : testPositions.keySet()) {
            Integer testPoistion = testPositions.get(pos);
            assertEquals("Bad method positionAgainstInterval", pos, intInterval.positionAgainstInterval(testPoistion));
        }
    }

    /**
     * Test of positionAgainstInterval method, of class Interval. Edges are
     * null.
     */
    @Test
    public void testPositionAgainstIntervalWithNull() {
        Integer startEdge = 10;
        Integer endEdge = 20;
        Interval<Integer> startNull = new Interval<>(null, endEdge);
        Interval<Integer> endNull = new Interval<>(startEdge, null);
        Interval<Integer> bothNull = new Interval<>(null, null);
        assertEquals("Bad method positionAgainstInterval", PositionAgainstInterval.Between, bothNull.positionAgainstInterval(100));
        assertEquals("Bad method positionAgainstInterval", PositionAgainstInterval.Between, startNull.positionAgainstInterval(endEdge - 100));
        assertEquals("Bad method positionAgainstInterval", PositionAgainstInterval.AtEnd, startNull.positionAgainstInterval(endEdge));
        assertEquals("Bad method positionAgainstInterval", PositionAgainstInterval.After, startNull.positionAgainstInterval(endEdge + 100));
        assertEquals("Bad method positionAgainstInterval", PositionAgainstInterval.Before, endNull.positionAgainstInterval(startEdge - 100));
        assertEquals("Bad method positionAgainstInterval", PositionAgainstInterval.AtStart, endNull.positionAgainstInterval(startEdge));
        assertEquals("Bad method positionAgainstInterval", PositionAgainstInterval.Between, endNull.positionAgainstInterval(startEdge + 100));
    }

    @Test(expected = ArgumentNullException.class)
    public void testPositionAgainstIntervalArgumentNull() {
        Interval<Integer> intInterval = new Interval<>(10, 20);
        intInterval.positionAgainstInterval(null);
        fail("Bad method positionAgainstInterval - null argument has not been detected");
    }

    /**
     * Test of compareToOther method, of class Interval.
     */
    @Test
    public void testCompareToOtherNotNull() {
        Integer delta = 10;
        Integer start = 10;
        Integer end = start + 5 * delta;
        Interval<Integer> refInt = new Interval<>(start, end);
        Map<Interval.IntevalComparison, Interval<Integer>> compIntMap = new HashMap<>();
        compIntMap.put(Interval.IntevalComparison.BeforeBefore, new Interval<>(start - 2 * delta, start - delta));
        compIntMap.put(Interval.IntevalComparison.BeforeStart, new Interval<>(start - delta, start));
        compIntMap.put(Interval.IntevalComparison.BeforeBetween, new Interval<>(start - delta, start + delta));
        compIntMap.put(Interval.IntevalComparison.BeforeEnd, new Interval<>(start - delta, end));
        compIntMap.put(Interval.IntevalComparison.BeforeAfter, new Interval<>(start - delta, end + delta));
        compIntMap.put(Interval.IntevalComparison.StartBetween, new Interval<>(start, start + delta));
        compIntMap.put(Interval.IntevalComparison.StartEnd, new Interval<>(start, end));
        compIntMap.put(Interval.IntevalComparison.StartAfter, new Interval<>(start, end + delta));
        compIntMap.put(Interval.IntevalComparison.BetweenBetween, new Interval<>(start + delta, end - delta));
        compIntMap.put(Interval.IntevalComparison.BetweenEnd, new Interval<>(start + delta, end));
        compIntMap.put(Interval.IntevalComparison.BetweenAfter, new Interval<>(start + delta, end + delta));
        compIntMap.put(Interval.IntevalComparison.EndAfter, new Interval<>(end, end + delta));
        compIntMap.put(Interval.IntevalComparison.AfterAfter, new Interval<>(end + delta, end + 2 * delta));
        for (Interval.IntevalComparison comparisonResult : compIntMap.keySet()) {
            Interval.IntevalComparison testedResult = refInt.compareToOther(compIntMap.get(comparisonResult));
            assertTrue("Bad compareToOther result " + comparisonResult.name(), comparisonResult.equals(testedResult));
        }
    }

    /**
     * Test of isCompletelyInside method, of class Interval.
     */
    @Test
    public void testIsCompletelyInside() {
        Interval<Integer> refInt = new Interval<>(20, 40);
        Interval<Integer> completelyInside = new Interval<>(10, 50);
        List<Interval<Integer>> notCompletelyInside = new ArrayList();
        notCompletelyInside.add(new Interval<>(0, 5));
        notCompletelyInside.add(new Interval<>(0, 20));
        notCompletelyInside.add(new Interval<>(0, 40));
        notCompletelyInside.add(new Interval<>(20, 40));
        notCompletelyInside.add(new Interval<>(20, 45));
        notCompletelyInside.add(new Interval<>(25, 40));
        notCompletelyInside.add(new Interval<>(25, 45));
        notCompletelyInside.add(new Interval<>(40, 50));
        notCompletelyInside.add(new Interval<>(45, 55));
        assertTrue("Bad method isCompletelyInside - dont detect inside interval", refInt.isCompletelyInside(completelyInside));
        for (Interval<Integer> testedInterval : notCompletelyInside) {
            assertFalse("Bad method isCompletelyInside. Interval " + refInt.toString() + " has been detected inside to " + testedInterval.toString(), refInt.isCompletelyInside(testedInterval));
        }
    }

    /**
     * Test of isCompletelyInsideOrAtEdge method, of class Interval.
     */
    @Test
    public void testIsCompletelyInsideOrAtEdge() {
        Interval<Integer> refInt = new Interval<>(20, 40);
        List<Interval<Integer>> completelyInside = new ArrayList();
        completelyInside.add(new Interval<>(10, 50));
        completelyInside.add(new Interval<>(20, 50));
        completelyInside.add(new Interval<>(10, 40));
        completelyInside.add(new Interval<>(20, 40));
        List<Interval<Integer>> notCompletelyInside = new ArrayList();
        notCompletelyInside.add(new Interval<>(0, 5));
        notCompletelyInside.add(new Interval<>(0, 20));
        notCompletelyInside.add(new Interval<>(25, 40));
        notCompletelyInside.add(new Interval<>(25, 45));
        notCompletelyInside.add(new Interval<>(40, 50));
        notCompletelyInside.add(new Interval<>(45, 55));
        for (Interval<Integer> testedInterval : completelyInside) {
            assertTrue("Bad method isCompletelyInsideOrAtEdge. Interval " + refInt.toString() + " has not been detected inside to " + testedInterval.toString(), refInt.isCompletelyInsideOrAtEdge(testedInterval));
        }
        for (Interval<Integer> testedInterval : notCompletelyInside) {
            assertFalse("Bad method isCompletelyInsideOrAtEdge. Interval " + refInt.toString() + " has been detected inside to " + testedInterval.toString(), refInt.isCompletelyInsideOrAtEdge(testedInterval));
        }

    }

    /**
     * Test of completelyContains method, of class Interval.
     */
    @Test
    public void testCompletelyContains() {
        Interval<Integer> refInt = new Interval<>(10, 50);
        Interval<Integer> completelyContain = new Interval<>(20, 40);
        List<Interval<Integer>> notCompletelyContain = new ArrayList();
        notCompletelyContain.add(new Interval<>(0, 5));
        notCompletelyContain.add(new Interval<>(0, 10));
        notCompletelyContain.add(new Interval<>(0, 15));
        notCompletelyContain.add(new Interval<>(0, 50));
        notCompletelyContain.add(new Interval<>(0, 55));
        notCompletelyContain.add(new Interval<>(10, 15));
        notCompletelyContain.add(new Interval<>(10, 50));
        notCompletelyContain.add(new Interval<>(10, 55));
        notCompletelyContain.add(new Interval<>(15, 50));
        notCompletelyContain.add(new Interval<>(15, 55));
        notCompletelyContain.add(new Interval<>(50, 55));
        notCompletelyContain.add(new Interval<>(55, 60));
        assertTrue("Bad method completelyContains - don't detect interval inside referce interval", refInt.completelyContains(completelyContain));
        for (Interval<Integer> testedInterval : notCompletelyContain) {
            assertFalse("Bad method completelyContains. Interval " + testedInterval.toString() + " has been detected inside to " + refInt.toString(), refInt.completelyContains(testedInterval));
        }
    }

    /**
     * Test of completelyContainsOrAtEdge method, of class Interval.
     */
    @Test
    public void testCompletelyContainsOrAtEdge() {
        Interval<Integer> refInt = new Interval<>(10, 50);
        List<Interval<Integer>> completelyContain = new ArrayList();
        completelyContain.add(new Interval(10, 30));
        completelyContain.add(new Interval(10, 50));
        completelyContain.add(new Interval(15, 30));
        completelyContain.add(new Interval(15, 50));
        List<Interval<Integer>> notCompletelyContain = new ArrayList();
        notCompletelyContain.add(new Interval<>(0, 5));
        notCompletelyContain.add(new Interval<>(0, 10));
        notCompletelyContain.add(new Interval<>(0, 15));
        notCompletelyContain.add(new Interval<>(0, 50));
        notCompletelyContain.add(new Interval<>(0, 55));
        notCompletelyContain.add(new Interval<>(10, 55));
        notCompletelyContain.add(new Interval<>(15, 55));
        notCompletelyContain.add(new Interval<>(50, 55));
        notCompletelyContain.add(new Interval<>(55, 60));
        for (Interval<Integer> testedInterval : completelyContain) {
            assertTrue("Bad method completelyContainsOrAtEdge. Interval " + testedInterval.toString() + " has not been detected inside to " + refInt.toString(), refInt.completelyContainsOrAtEdge(testedInterval));
        }
        for (Interval<Integer> testedInterval : notCompletelyContain) {
            assertFalse("Bad method completelyContainsOrAtEdge. Interval " + testedInterval.toString() + " has been detected inside to " + refInt.toString(), refInt.completelyContainsOrAtEdge(testedInterval));
        }
    }

    /**
     * Test of isOverlap method, of class Interval.
     */
    @Test
    public void testIsOverlap() {
        Interval<Integer> refInt = new Interval<>(10, 50);
        List<Interval<Integer>> overlaped = new ArrayList<>();
        overlaped.add(new Interval<>(5, 20));
        overlaped.add(new Interval<>(10, 20));
        overlaped.add(new Interval<>(10, 50));
        overlaped.add(new Interval<>(15, 20));
        overlaped.add(new Interval<>(15, 50));
        overlaped.add(new Interval<>(15, 55));
        List<Interval<Integer>> notOverlaped = new ArrayList<>();
        notOverlaped.add(new Interval<>(0, 5));
        notOverlaped.add(new Interval<>(0, 10));
        notOverlaped.add(new Interval<>(50, 55));
        notOverlaped.add(new Interval<>(55, 60));
        for (Interval<Integer> testedInterval : overlaped) {
            assertTrue("Bad method isOverlap. " + refInt.toString() + " has not tested as overlaped with " + testedInterval.toString(), refInt.isOverlap(testedInterval));
        }
        for (Interval<Integer> testedInterval : notOverlaped) {
            assertFalse("Bad method isOverlap. " + refInt.toString() + "has tested as overlaped with " + testedInterval.toString(), refInt.isOverlap(testedInterval));
        }
    }

    /**
     * Test of overlap method, of class Interval.
     */
    @Test
    public void testOverlap() {
        Interval<Integer> refInt = new Interval<>(10, 50);
        List<TwoInterval<Interval<Integer>>> overlaped = new ArrayList<>();
        overlaped.add(new TwoInterval<>(new Interval<>(5, 20), new Interval<>(10,20)));
        overlaped.add(new TwoInterval<>(new Interval<>(10, 20), new Interval<>(10,20)));
        overlaped.add(new TwoInterval<>(new Interval<>(10, 50), new Interval<>(10,50)));
        overlaped.add(new TwoInterval<>(new Interval<>(15, 20), new Interval<>(15,20)));
        overlaped.add(new TwoInterval<>(new Interval<>(15, 50), new Interval<>(15,50)));
        overlaped.add(new TwoInterval<>(new Interval<>(15, 55), new Interval<>(15,50)));
        List<Interval<Integer>> notOverlaped = new ArrayList<>();
        notOverlaped.add(new Interval<>(0, 5));
        notOverlaped.add(new Interval<>(0, 10));
        notOverlaped.add(new Interval<>(50, 55));
        notOverlaped.add(new Interval<>(55, 60));
        for (TwoInterval<Interval<Integer>> testedIntervals : overlaped) {
            assertEquals("Bad method Overlap.", testedIntervals.getInterval02(), refInt.overlap(testedIntervals.interval01));
        }
        for (Interval<Integer> testedInterval : notOverlaped) {
            assertNull("Bad method Overlap. Overlap has found between " + refInt.toString() + " and " + testedInterval.toString(), refInt.overlap(testedInterval));
        }
    }

    /**
     * Test of union method, of class Interval.
     */
    @Test
    public void testUnion() {
        Interval<Integer> refInt = new Interval<>(10, 50);
        List<TwoInterval<Interval<Integer>>> overlaped = new ArrayList<>();
        overlaped.add(new TwoInterval<>(new Interval<>(5, 20), new Interval<>(5,50)));
        overlaped.add(new TwoInterval<>(new Interval<>(10, 20), new Interval<>(10,50)));
        overlaped.add(new TwoInterval<>(new Interval<>(10, 50), new Interval<>(10,50)));
        overlaped.add(new TwoInterval<>(new Interval<>(15, 20), new Interval<>(10,50)));
        overlaped.add(new TwoInterval<>(new Interval<>(15, 50), new Interval<>(10,50)));
        overlaped.add(new TwoInterval<>(new Interval<>(15, 55), new Interval<>(10,55)));
        List<Interval<Integer>> notOverlaped = new ArrayList<>();
        notOverlaped.add(new Interval<>(0, 5));
        notOverlaped.add(new Interval<>(0, 10));
        notOverlaped.add(new Interval<>(50, 55));
        notOverlaped.add(new Interval<>(55, 60));
        for (TwoInterval<Interval<Integer>> testedIntervals : overlaped) {
            assertEquals("Bad method Union.", testedIntervals.getInterval02(), refInt.union(testedIntervals.interval01));
        }
        for (Interval<Integer> testedInterval : notOverlaped) {
            assertNull("Bad method Union. Union has found between " + refInt.toString() + " and " + testedInterval.toString(), refInt.union(testedInterval));
        }
    }

    /**
     * Test of cutBy method, of class Interval.
     * Cut interval is null and Cut interval has no overlap with instance to be cut
     */
    @Test
    public void testCutBy01() {
        Interval<Integer> refInt = new Interval<>(10, 50);
        assertEquals("Bad method cutBy - null input", refInt, refInt.cutBy(null).get(0));
        assertEquals("Bad method cutBy - no verlaped input", refInt, refInt.cutBy(new Interval<>(0,5)).get(0));
    }

    /**
     * Test of cutBy method, of class Interval.
     * Cut interval completely contains instance to be cut
     */
    @Test
    public void testCutBy02() {
        Interval<Integer> refInt = new Interval<>(10, 50);
        assertEquals("Bad method cutBy - Cut interval completely contains instance", 0, refInt.cutBy(new Interval<>(0,60)).size());
    }
    
        /**
     * Test of cutBy method, of class Interval.
     * Cut interval is completely inside instance to be cut
     */
    @Test
    public void testCutBy03() {
        Interval<Integer> refInt = new Interval<>(10, 50);
        Interval<Integer> cutInterval = new Interval<>(20,40);
        List<Interval<Integer>> output = refInt.cutBy(cutInterval);
        assertEquals("Bad method cutBy - Cut interval is completely inside instance. Bad size of output is returned", 2, output.size());
        assertTrue("Bad method cutBy - Cut interval is completely inside instance.", output.contains(new Interval<>(10,20)));
        assertTrue("Bad method cutBy - Cut interval is completely inside instance.", output.contains(new Interval<>(40,50)));
    }
    
            /**
     * Test of cutBy method, of class Interval.
     * Cut interval is not completely inside instance to be cut but overlaped it
     */
    @Test
    public void testCutBy04() {
        Interval<Integer> refInt = new Interval<>(10, 50);
        Interval<Integer> cutInterval01 = new Interval<>(5,15);
        Interval<Integer> cutInterval02 = new Interval<>(40,60);
        List<Interval<Integer>> output01 = refInt.cutBy(cutInterval01);
        List<Interval<Integer>> output02 = refInt.cutBy(cutInterval02);
        assertEquals("Bad method cutBy - Cut interval is overlaped instance. Bad size of output is returned", 1, output01.size());
        assertEquals("Bad method cutBy - Cut interval is overlaped instance. Bad size of output is returned", 1, output02.size());
        assertEquals("Bad method cutBy - Cut interval is overlaped instance. Bad result.", new Interval<>(15,50) ,output01.get(0));
        assertEquals("Bad method cutBy - Cut interval is overlaped instance. Bad result.", new Interval<>(10,40) ,output02.get(0));
    }
    
    /**
     * Test of hasLength method, of class Interval.
     */
    @Test
    public void testHasLength() {
        Interval<Integer> integerInterval = new Interval<>(1, 2);
        Interval<Date> dateInterval = new Interval<>(createDate(2010, 2, 2), createDate(2011, 2, 2));
        DateEdge de01 = new DateEdge(2015, 6, 9, 11, 30, 10);
        DateEdge de02 = new DateEdge(2015, 6, 9, 11, 30, 11);
        Interval<Date> measurableInterval = new Interval<Date>(de01, de02);
        assertTrue("Bad method hasInterval with Number type", integerInterval.hasLength());
        assertTrue("Bad method hasInterval with measurable type", measurableInterval.hasLength());
        assertFalse("Bad method hasInterval with no measurable type", dateInterval.hasLength());
    }

    /**
     * Test of getLength method, of class Interval with valid type and not null
     * edges.
     */
    @Test
    public void testGetLengthValidTypeNotNull() {
        Integer startValue = 1;
        Integer endValue = 2;
        Long timeStart = 1000L;
        Long timeEnd = 1001L;
        Interval<Integer> integerInterval = new Interval<>(startValue, endValue);
        DateEdge deStart = new DateEdge(timeStart);
        DateEdge deEnd = new DateEdge(timeEnd);
        Interval<Date> measurableInterval = new Interval<Date>(deStart, deEnd);
        assertEquals("Bad method getLeght - interger edges", new Double(endValue - startValue), integerInterval.getLength());
        assertEquals("Bad method getLeght - measurable edges", new Double(timeEnd - timeStart), measurableInterval.getLength());
    }

    /**
     * Test of getLength method, of class Interval with valid type and null
     * edges.
     */
    @Test
    public void testGetLengthValidTypeWithNull() {
        Integer startValue = 1;
        Integer endValue = null;
        Interval<Integer> integerInterval = new Interval<>(startValue, endValue);
        assertEquals("Bad method getLeght - interger edges with null edge", new Double(Double.POSITIVE_INFINITY), integerInterval.getLength());
    }

    /**
     * Test of getLength method, of class Interval with invalid type of edge.
     */
    @Test(expected = UnsupportedOperationException.class)
    public void testGetLengthInvalidType() {
        Interval<Date> dateInterval = new Interval<>(createDate(2010, 2, 2), createDate(2011, 2, 2));
        dateInterval.getLength();
        fail("Bad method getLenght - no exception has been thrown for invalid edge type");
    }

    /**
     * Test of equals method, of class Interval.
     */
    @Test
    public void testEquals() {
        Integer startValue = 1;
        Integer endValue0 = 3;
        Integer endValue1 = 4;
        Interval<Integer> instance01 = new Interval<>(startValue, endValue0);
        Interval<Integer> instance02 = new Interval<>(startValue, endValue0);
        Interval<Integer> instance03 = new Interval<>(startValue, endValue1);
        assertTrue("Bad equals - equal intervals", instance01.equals(instance02));
        assertFalse("Bad equals - different intervals", instance01.equals(instance03));
        assertFalse("Bad equals - null object", instance01.equals(null));
        assertFalse("Bad equals - different type", instance01.equals(new String("abc")));
    }

    /**
     * Test of clone method, of class Interval.
     */
    @Test
    public void testClone() {
        Integer start = 1;
        Integer end = 2;
        Interval<Integer> intInterval = new Interval<>(start, end);
        Interval<Integer> cloneInterval = new Interval<>(intInterval);
        assertTrue("Bad clone intervals - integer", intInterval.equals(cloneInterval));
        Date startDate = createDate(2010, 2, 2);
        Date endDate = createDate(2010, 3, 3);
        Interval<Date> dateInterval01 = new Interval<>(startDate, endDate);
        Interval<Date> cloneDateInterval01 = new Interval<>(dateInterval01);
        assertTrue("Bad clone intervals - date", dateInterval01.equals(cloneDateInterval01));
        Interval<Date> dateInterval02 = new Interval<>(startDate, null);
        Interval<Date> cloneDateInterval02 = new Interval<>(dateInterval02);
        assertTrue("Bad clone intervals - date / null", dateInterval02.equals(cloneDateInterval02));
    }

    /**
     * Create object Date
     *
     * @param year number of year
     * @param month number of month in year (1 - january, 2 - february etc.)
     * @param day number of day in month
     * @return
     */
    private Date createDate(int year, int month, int day) {
        this.calendar.set(year, month - 1, day);
        return this.calendar.getTime();
    }

    private class DateEdge extends Date implements Measurable, Cloneable {

        private static final long serialVersionUID = 1214875703330276514L;

        public DateEdge() {
        }

        public DateEdge(String s) {
            super(s);
        }

        public DateEdge(long date) {
            super(date);
        }

        public DateEdge(int year, int month, int date) {
            super(year, month, date);
        }

        public DateEdge(int year, int month, int date, int hrs, int min) {
            super(year, month, date, hrs, min);
        }

        public DateEdge(int year, int month, int date, int hrs, int min, int sec) {
            super(year, month, date, hrs, min, sec);
        }

        @Override
        public Double getMetrics() {
            return (double) getTime();
        }

        @Override
        public Object clone() {
            return super.clone();
        }

    }

    private class TwoInterval<V extends Interval<?>> {

        private final V interval01;
        private final V interval02;

        public TwoInterval(V interval01, V interval02) {
            this.interval01 = interval01;
            this.interval02 = interval02;
        }

        public V getInterval01() {
            return interval01;
        }

        public V getInterval02() {
            return interval02;
        }

    }

}
