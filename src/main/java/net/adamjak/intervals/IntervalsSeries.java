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
package net.adamjak.intervals;

import com.google.common.base.Optional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import net.adamjak.intervals.Interval.IntevalComparison;

/**
 * Class represents collection of intervals and values related to them. Pair
 * interval - value can represents some real object or status that has validity
 * only between boundaries (edges) of interval. Registered intervals must not
 * overlap. Collection pairs interval - value is stored as {@link HashMap} where
 * interval is the key, but interval can not be null. Methods to add
 * ({@code insertNew} and new pair {@code putValue}) or update collection
 * ({@code changeEdges} and {@code erase}) work with collection sorted by start
 * edge of intervals.
 *
 * @author Marian Adamjak
 * @param <T> type of intervals' edges
 * @param <V> type of values
 */
public class IntervalsSeries<T extends Comparable<T>, V> {

    /**
     * Type of the sort
     */
    public enum SortOrder {
        /**
         * objects will be sorted in ascending order
         */
        ASCENDING,
        /**
         * objects will be sorted in descending order
         */
        DESCENDING
    }

    private final Map<Interval<T>, V> intervalValueMap = new HashMap<>();

    /**
     * Returns the number of interval-value pairs in this series.
     *
     * @return returns the number of interval-value pairs in this series
     */
    public int size() {
        return intervalValueMap.size();
    }

    /**
     * Return true if series is empty (no interval-value pair is registered)
     *
     * @return true if series is empty
     */
    public boolean isEmpty() {
        return intervalValueMap.isEmpty();
    }

    /**
     * Return true if series contain pair interval-value whit given interval
     *
     * @param interval The interval whose presence in this series is to be
     * tested
     * @throws ArgumentNullException if interval is null
     * @return true if series contain pair interval-value whit given interval
     */
    public boolean containsInterval(Interval<T> interval) {
        if (interval == null) {
            throw new ArgumentNullException("Interval can not be null", "interval");
        }
        return intervalValueMap.containsKey(interval);
    }

    /**
     * Returns true if this series has one or more intervals with the specified value.
     *
     * @param value value whose presence in this series is to be tested
     * @return true if this series has one or more intervals with the specified value
     */
    public boolean containsValue(V value) {
        return intervalValueMap.containsValue(value);
    }

    /**
     * Returns the value to which the specified interval is mapped, or null if
     * this series contains no mapping for the interval.
     *
     * @param interval the interval whose associated value is to be returned
     * @throws ArgumentNullException if interval is null
     * @return the value to which the specified interval is mapped, or null if
     * this series contains no mapping for the interval
     */
    public V getValue(Interval<T> interval) {
        if (interval == null) {
            throw new ArgumentNullException("Interval can not be null", "interval");
        }
        return intervalValueMap.get(interval);
    }

    /**
     * Put new pair (interval - value) into series. If there is pair with given
     * interval in the series then value will be replaced by given value. If
     * there is inetrval in series overlaped given interval then
     * {@link IllegalOvelapException} will be thrown.
     *
     * @param interval interval with which the specified value is to be
     * associated. If it is null then {@link ArgumentNullException} is thorwn.
     * @param value value to be associated with the specified interval
     * @throws ArgumentNullException if specified interval or value is null
     * @throws IllegalOvelapException if specified interval overlaps another
     * interval in series
     * @return the previous value associated with interval or null if there was
     * no mapping interval.
     */
    public V putValue(Interval<T> interval, V value) {
        if (interval == null) {
            throw new ArgumentNullException("Interval can not be null", "interval");
        }
        if (value == null) {
            throw new ArgumentNullException("Value can not be null", "value");
        }
        if (containsInterval(interval)) {
            return intervalValueMap.put(interval, value);
        }
        if (isOverlapExcludeEdgeWith(interval)) {
            throw new IllegalOvelapException("Can not put value if there is overal input interval with another interval in series");
        }
        return intervalValueMap.put(interval, value);
    }

    /**
     * Remove specified interval and associated value from this series if
     * present.
     *
     * @param interval interval whose mapping is to be removed from the series
     * @throws ArgumentNullException if specified interval is null
     * @return the previous value associated with interval or null if there was
     * no mapping interval.
     */
    public V remove(Interval<T> interval) {
        if (interval == null) {
            throw new ArgumentNullException("Interval can not be null", "interval");
        }
        return intervalValueMap.remove(interval);
    }

    /**
     * Remove all pair interval - value from this series
     */
    public void clear() {
        intervalValueMap.clear();
    }

    /**
     * Returns a Set view of the intervals contained in this series. Method is
     * equal to {@code HashMap.keySet}.
     *
     * @return a set view of the keys contained in this map
     */
    public Set<Interval<T>> getIntevals() {
        return Collections.unmodifiableSet(intervalValueMap.keySet());
    }

    /**
     * Returns a Collection view of the values contained in this series. Method
     * is equal to {@code HashSet.values}.
     *
     * @return a Collection view of the values contained in this series.
     */
    public Collection<V> getValues() {
        return Collections.unmodifiableCollection(intervalValueMap.values());
    }

    /**
     * Returns a Set view of the mappings contained in this series. Method is
     * equal to {@code HashSet.entrySet}
     *
     * @return a Set view of the mappings contained in this series.
     */
    public Set<Entry<Interval<T>, V>> getPairs() {
        return Collections.unmodifiableSet(intervalValueMap.entrySet());
    }

    
    /**
     * Return true if specified interval overlaps another intervals in series. 
     * @param interval The interval whose overlap in series is tested
     * @param edgesIncluded control if edges are included into overlap testing. 
     * <ul>
     * <li>{@code true} - edges are included and intervals (10,20) and (20,30) are overlaped </li>
     * <li>{@code false} - edges are not included and intervals (10,20) and (20,30) are not overlaped </li>
     * </ul>
     * @throws ArgumentNullException if specified interval is null
     * @return true if specified interval overlaps with another intervals in
     * series
     */
    public boolean isOverlapWith(Interval<T> interval, boolean edgesIncluded) {
        if (interval == null) {
            throw new ArgumentNullException("Interval can not be null", "interval");
        }
        if(edgesIncluded){
            return this.isOverlapIncludeEdgeWith(interval);
        } else {
            return this.isOverlapExcludeEdgeWith(interval);
        }
    }
    
    
    private boolean isOverlapExcludeEdgeWith(Interval<T> interval) {
        for (Interval<T> in : getIntevals()) {
            if (in.isOverlap(interval)) {
                return true;
            }
        }
        return false;
    }

    private boolean isOverlapIncludeEdgeWith(Interval<T> interval) {

        for (Interval<T> in : getIntevals()) {
            IntevalComparison compare = in.compareToOther(interval);
            if (compare.isIn(IntevalComparison.AfterAfter, IntevalComparison.BeforeBefore) == false) {
                return true;
            }
        }
        return false;
    }

    
    /**
     * Returns a List of intervals that overlap with the given interval. Edges
     * are used to overlap test by {@code includeEdges} argument
     *
     * @param interval The interval whose overlap in this series is to be tested
     * @param includeEdges control if edges are included into overlap testing. 
     * <ul>
     * <li>{@code true} - edges are included and intervals (10,20) and (20,30) are overlaped </li>
     * <li>{@code false} - edges are not included and intervals (10,20) and (20,30) are not overlaped </li>
     * </ul>
     * @throws ArgumentNullException - if specified interval is null
     * @return a List of intervals that overlap with the specified interval.
     */
    public List<Interval<T>> getOverlapedWith(Interval<T> interval, boolean includeEdges){
        if (interval == null) {
            throw new ArgumentNullException("Interval can not be null", "interval");
        }
        if(includeEdges){
            return this.getOverlapedIncludeEdgesWith(interval);
        } else {
            return this.getOverlapedExcludeEdgesWith(interval);
        }
    }
    
    /**
     * Returns a List of intervals that overlap with the given interval. Edges
     * are not used to overlap test (e.g. if given interval is (10,20) then
     * (20,30) are not overlaped and interval (20,30) will not add into result)
     *
     * @param interval The interval whose overlap in this series is to be tested
     * @throws ArgumentNullException - if specified interval is null
     * @return a List of intervals that overlap with the specified interval.
     */
    private List<Interval<T>> getOverlapedExcludeEdgesWith(Interval<T> interval) {
        
        List<Interval<T>> output = new ArrayList<>();
        for (Interval<T> in : getIntevals()) {
            if (in.isOverlap(interval)) {
                output.add(in);
            }
        }
        return output;
    }

    /**
     * Returns a List of intervals that overlap with the given interval. Edges
     * are used to overlap test (e.g. if specified interval is (10,20) then
     * (20,30) are overlaped and it will add into result)
     *
     * @param interval - The interval whose overlap in this series is to be
     * tested
     * @throws ArgumentNullException - if specified interval is null
     * @return a List of intervals that overlap with the specified interval.
     */
    private List<Interval<T>> getOverlapedIncludeEdgesWith(Interval<T> interval) {
        if (interval == null) {
            throw new ArgumentNullException("Interval can not be null", "interval");
        }
        List<Interval<T>> output = new ArrayList<>();
        for (Interval<T> in : getIntevals()) {
            IntevalComparison compare = in.compareToOther(interval);
            if (compare.isIn(IntevalComparison.AfterAfter,IntevalComparison.BeforeBefore) == false) {
                output.add(in);
            }
        }
        return output;
    }

    /**
     * Returns a sorted Set view of the intervals contained in this series.
     * Method return same Set as {@code getIntervals}, but Set is sorted by
     * start edge. Edges are sorted in ascending order.
     *
     * @return a sorted Set view of the intervals contained in this series in ascending order
     */
    public Set<Interval<T>> getIntervalsSorted() {
        return getIntervalsSorted(SortOrder.ASCENDING);
    }

    /**
     * Returns a sorted Set view of the intervals contained in this series.
     * Method return same Set as {@code getIntervals}, but Set is sorted by
     * start edge. Edges are sorted by sortOrder argument.
     * @param sortOrder type of the sort
     * @return a sorted Set view of the intervals contained in this series in specified order
     */
    public Set<Interval<T>> getIntervalsSorted(SortOrder sortOrder) {

        Comparator<Interval<T>> comparator;

        switch (sortOrder) {
            case ASCENDING:
                comparator = new Comparator<Interval<T>>() {
                    @Override
                    public int compare(Interval<T> o1, Interval<T> o2) {
                        T c1 = o1.getStart();
                        T c2 = o2.getStart();
                        int output = nullableCompare(c1, c2);
                        if (output == 0) {
                            c1 = o1.getEnd();
                            c2 = o2.getEnd();
                            output = nullableCompare(c1, c2);
                        }
                        return output;
                    }
                };
                break;
            case DESCENDING:
                comparator = new Comparator<Interval<T>>() {
                    @Override
                    public int compare(Interval<T> o1, Interval<T> o2) {
                        T c1 = o1.getStart();
                        T c2 = o2.getStart();
                        int output = nullableCompare(c1, c2);
                        if (output == 0) {
                            c1 = o1.getEnd();
                            c2 = o2.getEnd();
                            output = nullableCompare(c1, c2);
                        }
                        return output * (-1);
                    }
                };
                break;
            default:
                throw new IllegalArgumentException("Unknown order type: " + sortOrder.name());
        }

        SortedSet<Interval<T>> sortKeys = new TreeSet<>(comparator);
        sortKeys.addAll(intervalValueMap.keySet());
        return Collections.unmodifiableSet(sortKeys);
    }

    /**
     * Return a List of interval(s) that represent gap(s) among intervals
     * contained in this series. Gap is space between end and start edges in
     * sorted interval. Intervals are sorted by start edge. If series is empty
     * or contains only one pair interval - value then empty List is returned.
     *
     * Example - if series contains inetrvals (10,20) (25,30) (30,40) and
     * (50,60) then list with (20,25) and (40,50) is returned.
     *
     * @return a List of interval(s) that represent gap(s) among intervals
     * contained in this series.
     */
    public List<Interval<T>> getGaps() {
        List<Interval<T>> output = new ArrayList<>();
        if (intervalValueMap.size() <= 1) {
            return output;
        }
        Set<Interval<T>> keys = getIntervalsSorted();
        Iterator<Interval<T>> iterator = keys.iterator();
        Interval<T> previous = iterator.next();
        while (iterator.hasNext()) {
            Interval<T> current = iterator.next();
            if (nullableCompare(previous.getEnd(), current.getStart()) == -1) {
                T end = previous.getEnd();
                T start = current.getStart();
                output.add(new Interval<>(end, start));
            }
            previous = current;
        }
        return output;
    }

    /**
     * Return the lowest start edge of all intervals contained in series. If
     * series is empty then null is returned.
     *
     * @return the lowest start edge of all intervals contained in series.
     */
    public T getStartMinimum() {
        Set<Interval<T>> keys = getIntervalsSorted();
        Optional<Interval<T>> first = getFirstElement(keys);
        if (first.isPresent()) {
            return first.get().getStart();
        } else {
            return null;
        }
    }

    /**
     * Return the lowest end edge of all intervals contained in series. If
     * series is empty then null is returned.
     *
     * @return the lowest end edge of all intervals contained in series.
     */
    public T getEndMinimum() {
        Set<Interval<T>> keys = getIntervalsSorted();
        Optional<Interval<T>> first = getFirstElement(keys);
        if (first.isPresent()) {
            return first.get().getEnd();
        } else {
            return null;
        }
    }

    /**
     * Return the highest start edge of all intervals contained in series. If
     * series is empty then null is returned.
     *
     * @return the highest start edge of all intervals contained in series.
     */
    public T getStartMaximum() {
        Set<Interval<T>> keys = getIntervalsSorted();
        Optional<Interval<T>> last = getLastElement(keys);
        if (last.isPresent()) {
            return last.get().getStart();
        } else {
            return null;
        }
    }

    /**
     * Return the highest end edge of all intervals contained in series. If
     * series is empty then null is returned.
     *
     * @return the highest end edge of all intervals contained in series.
     */
    public T getEndMaximum() {
        Set<Interval<T>> keys = getIntervalsSorted();
        Optional<Interval<T>> last = getLastElement(keys);
        if (last.isPresent()) {
            return last.get().getEnd();
        } else {
            return null;
        }
    }

    /**
     * Return interval cretaed from getStartMinimum and getEndMaximum.
     *
     * @return interval cretaed from getStartMinimum and getEndMaximum.
     */
    public Interval<T> getExtent() {
        return new Interval<>(this.getStartMinimum(), this.getEndMaximum());
    }

    /**
     * Find interval(s) from series that contain specified point. Point select
     * interval if point is between start and end edge. If includeEdge is true
     * then point select interval with equals start or end edge, too. If there
     * is no interval contains specified point then empty List is returned.
     *
     * Example - series contains intervals (10,20) (20,30) (40,50). Next results
     * are returned by specified parametres:
     * <ul>
     * <li>point = 10 includeEdge = true result = [(10,20)]</li>
     * <li>point = 10 includeEdge = false result = []</li>
     * <li>point = 15 includeEdge = true result = [(10,20)]</li>
     * <li>point = 15 includeEdge = false result = [(10,20)] </li>
     * <li>point = 20 includeEdge = true result = [(10,20) (20,30)]</li>
     * <li>point = 35 includeEdge = true result = []</li>
     * </ul>
     *
     * @param point The point that is used to find interval(s)
     * @param includeEdge if it is true then edges are included into test.
     * @return List of found interval(s).
     * @throws ArgumentNullException if point is null
     */
    public List<Interval<T>> getIntervalByPoint(T point, boolean includeEdge) {

        if (this.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        if (point == null) {
            throw new ArgumentNullException("Search point can not be null", "point");
        }

        List<Interval<T>> output = new ArrayList<>();
        Set<Interval<T>> sortedIntervals = this.getIntervalsSorted();
        Optional<Interval<T>> firstInterval = this.getFirstElement(sortedIntervals);
        if (firstInterval.isPresent()) {
            Interval.PositionAgainstInterval position = firstInterval.get().positionAgainstInterval(point);
            if (position.isIn(Interval.PositionAgainstInterval.Before)) {
                return Collections.EMPTY_LIST;
            }
        }

        final Iterator<Interval<T>> iterator = sortedIntervals.iterator();

        do {
            Interval<T> interval = iterator.next();
            Interval.PositionAgainstInterval position = interval.positionAgainstInterval(point);
            if (position.isIn(Interval.PositionAgainstInterval.Between)) {
                output.add(interval);
                return output;
            }
            if (includeEdge) {
                if (position.isIn(Interval.PositionAgainstInterval.AtEnd,
                        Interval.PositionAgainstInterval.AtStart)) {
                    output.add(interval);
                }
            }
        } while (iterator.hasNext());
        return output;
    }

    /**
     * Return sum of all lengths of intervals, if type of interval edges support
     * length computation. If series is empty then null is returned. If one of
     * interval has infinity edge, then Double.POSITIVE_INFINITY is returned.
     *
     * @return sum of all lengths of intervals or null if series is empty
     * @throws UnsupportedOperationException if type of intervals edge does not
     * support length computation
     * @see Interval#hasLength()
     */
    public Double getTotalLength() {
        Set<Interval<T>> intervals = this.getIntevals();
        Optional<Interval<T>> first = this.getFirstElement(intervals);
        if (first.isPresent()) {
            if (first.get().hasLength() == false) {
                throw new UnsupportedOperationException("This method can not be used for this type of Interval.");
            }
            Double output = null;
            Iterator<Interval<T>> iterator = intervals.iterator();
            do {
                Interval<T> interval = iterator.next();
                Double lenght = interval.getLength();
                if (lenght == Double.POSITIVE_INFINITY) {
                    return Double.POSITIVE_INFINITY;
                }
                output = (output == null ? lenght : output + lenght);
            } while (iterator.hasNext());
            return output;
        } else {
            return null;
        }
    }

    /**
     * Safely changes the edges of interval. Edges of oldInterval will replced
     * by edges from newInterval. Value mapped to oldInterval will be mapped to
     * newInterval. Operation work safely and will complete only if newInterval
     * does not overlap other intervals in series. If there is overlap no change
     * will be done. If series does not contain oldInterval no changes will be
     * done.
     *
     * @param oldInterval current interval
     * @param newInterval new interval
     * @return true if opertion is done with change edges, otherwise false
     * @throws ArgumentNullException if oldInterval or newInterval is null
     */
    public boolean changeEdges(Interval<T> oldInterval, Interval<T> newInterval) {
        if (oldInterval == null) {
            throw new ArgumentNullException("oldInterval can not be null", "oldInterval");
        }
        if (newInterval == null) {
            throw new ArgumentNullException("newInterval can not be null", "newInterval");
        }
        if (this.containsInterval(oldInterval) == false) {
            return false;
        }
        V value = this.remove(oldInterval);
        if (this.isOverlapWith(newInterval,false)) {
            this.putValue(oldInterval, value);
            return false;
        } else {
            this.putValue(newInterval, value);
            return true;
        }
    }

    /**
     * Insert new pair interval-value into series. Erase method with specified
     * interval is called before. New pair is put into created gap.
     *
     * @param interval specified interval
     * @param value value maped with interval
     * @throws ArgumentNullException if interval or value is null
     */
    public void insertNew(Interval<T> interval, V value) {
        if (interval == null) {
            throw new ArgumentNullException("Interval can not be null", "interval");
        }
        if (value == null) {
            throw new ArgumentNullException("Value can not be null", "value");
        }
        this.erase(interval);
        this.putValue(interval, value);
    }

    /**
     * Make gap in series. Interval that is fully overlaped by specified
     * {@code  eraseInterval} will be removed. If the interval is overlaped
     * partialy then its edges will be chenaged properly.
     *
     * @param eraseInterval - specified interval to make gap
     * @throws ArgumentNullException if eraseInterval is null
     */
    public void erase(Interval<T> eraseInterval) {
        if (eraseInterval == null) {
            throw new ArgumentNullException("eraseInterval can not be null", "eraseInterval");
        }
        List<Interval<T>> overlaps = this.getOverlapedWith(eraseInterval,false);
        for (Interval<T> overlaped : overlaps) {
            Interval<T> erase = overlaped.overlap(eraseInterval);
            if (erase.getStart().equals(erase.getEnd())) {
                continue;
            }
            if (erase.isEqualStart(overlaped)) {
                if (erase.isEqualEnd(overlaped)) {
                    this.remove(overlaped);
                } else {
                    V value = this.remove(overlaped);
                    Interval<T> newInterval = new Interval<>(erase.getEnd(), overlaped.getEnd());
                    this.putValue(newInterval, value);
                }
            } else if (erase.isEqualEnd(overlaped)) {
                V value = this.remove(overlaped);
                Interval<T> newInterval = new Interval<>(overlaped.getStart(), erase.getStart());
                this.putValue(newInterval, value);
            } else {
                V value = this.remove(overlaped);
                Interval<T> newInterval01 = new Interval<>(overlaped.getStart(), erase.getStart());
                Interval<T> newInterval02 = new Interval<>(erase.getEnd(), overlaped.getEnd());
                this.putValue(newInterval01, value);
                this.putValue(newInterval02, value);
            }
        }
    }

    private int nullableCompare(Comparable s1, Comparable s2) {
        int output;
        if (s1 == null) {
            if (s2 == null) {
                output = 0;
            } else {
                output = -1;
            }
        } else if (s2 == null) {
            output = 1;
        } else {
            output = s1.compareTo(s2);
        }
        return output;
    }

    private <T> Optional<T> getFirstElement(final Collection<T> c) {
        if (c.isEmpty()) {
            return Optional.absent();
        }
        final Iterator<T> itr = c.iterator();
        T element = itr.next();
        return Optional.fromNullable(element);
    }

    private <T> Optional<T> getLastElement(final Collection<T> c) {
        if (c.isEmpty()) {
            return Optional.absent();
        }
        final Iterator<T> itr = c.iterator();
        T lastElement = itr.next();
        while (itr.hasNext()) {
            lastElement = itr.next();
        }
        return Optional.fromNullable(lastElement);
    }

}
