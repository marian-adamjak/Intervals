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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class represents interval similar to mathematical interval. The interval is
 * bounded by two values (edges), both edges can be infinity (infinity is
 * expressed by null). Edge has to extend Comparable interface. If edge extends
 * {@code Number} or implements {@code Measurable}, then interval can calculate
 * lenght. Lower edge is called {@code start}, higher edge is called
 * {@code end}. If edge is null then null represents infinity
 *
 * @author Marian Adamjak
 * @param <T> type of edges
 */
public class Interval<T extends Comparable<T>> implements Cloneable {

    /**
     * Enumeration for compare position two intervals. First part of text enum
     * value describe position start point of compared interval against
     * reference interval and second part of text enum value describe position
     * end point of compared inetrval against reference interval.
     */
    public enum IntevalComparison {

        /**
         * start and end edges of compared interval is before (lower) then start
         * edge of reference interval
         */
        BeforeBefore,
        /**
         * strat edge of compared interval is before (lower) then start edge of
         * reference interval and end edge of compared interval is equals to
         * start edge of reference interval
         */
        BeforeStart,
        /**
         * strat edge of compared interval is before (lower) then start edge of
         * reference interval and end edge of compared interval is between start
         * and end edges of refernece interval
         */
        BeforeBetween,
        /**
         * strat edge of compared interval is before (lower) then start edge of
         * reference interval and end edge of compared interval is equals to end
         * edge of reference interval
         */
        BeforeEnd,
        /**
         * strat edge of compared interval is before (lower) then start edge of
         * reference interval and end edge of compared interval is after
         * (higher) then end edge of reference interval
         */
        BeforeAfter,
        /**
         * strat edge of compared interval is equal to start edge of reference
         * interval and end edge of compared interval is between start and end
         * edges of refernece interval
         */
        StartBetween,
        /**
         * strat edge of compared interval is equal to start edge of reference
         * interval and end edge of compared interval is equals to end edge of
         * reference interval
         */
        StartEnd,
        /**
         * strat edge of compared interval is equal to start edge of reference
         * interval and end edge of compared interval is after (higher) then end
         * edge of reference interval
         */
        StartAfter,
        /**
         * strat and edges of compared interval are between start and end edges
         * of reference interval (compared interval is completely inside into
         * reference interval)
         */
        BetweenBetween,
        /**
         * start edge of compared interval is between start and end edges of
         * reference interval and end edge of compared interval is equal to end
         * edge of reference interval
         */
        BetweenEnd,
        /**
         * start edge of compared interval is between start and end edges of
         * reference interval and end edge of compared interval is after
         * (higher) then end edge of reference interval
         */
        BetweenAfter,
        /**
         * start edge of compared interval is equal to end edge of reference
         * interval and end edge of compared interval is after (higher) then end
         * edge of reference interval
         */
        EndAfter,
        /**
         * start and end edges of comparetd interval are after (higher) then end
         * edge of reference interval
         */
        AfterAfter,
        /**
         * Unknown position (due bad comparison input)
         */
        Undefined;

        /**
         * Return information about occurance of comparison result.
         *
         * @param comps expected comparison result. If no result is given then
         * false is returned
         * @return true if comparison result is in expected result, otherwise
         * false
         */
        public boolean isIn(IntevalComparison... comps) {
            if (comps == null || comps.length == 0) {
                return false;
            }
            for (IntevalComparison comp : comps) {
                if (this == comp) {
                    return true;
                }
            }
            return false;
        }
    }

    /**
     * Enumeration to given position discrete value in interval
     */
    public enum PositionAgainstInterval {

        /**
         * compared value is before (lower) then start edge
         */
        Before,
        /**
         * compared value is equals to start edge
         */
        AtStart,
        /**
         * compared value is between start and edge (value is inside of
         * interval)
         */
        Between,
        /**
         * compared value is equals to end edge
         */
        AtEnd,
        /**
         * compared value is after (higher) then edge edge
         */
        After;

        public boolean isIn(PositionAgainstInterval... positions) {
            if (positions == null || positions.length == 0) {
                return false;
            }
            for (PositionAgainstInterval pos : positions) {
                if (this == pos) {
                    return true;
                }
            }
            return false;
        }
    }

    private final T start;
    private final T end;

    /**
     * Create instance from other interval. {@code clone} method to create new
     * instance is used.
     *
     * @param other input interval
     * @throws ArgumentNullException if the other is null
     * @throws IllegalArgumentException if other onterval does not suport clone
     */
    public Interval(Interval<T> other) {
        if (other == null) {
            throw new ArgumentNullException("Other inetreval is null", "other");
        }
        Interval<T> newint;
        try {
            newint = (Interval<T>) other.clone();
        } catch (CloneNotSupportedException ex) {
            throw new IllegalArgumentException("Other interval does not support clone", ex);
        }
        this.start = newint.getStart();
        this.end = newint.getEnd();
    }

    /**
     * Create instance from edges. If both edges are not null and start &gt; end,
     * then edges will change
     *
     * @param start start edge, null represents infinity
     * @param end end edge, null represents infinity
     */
    public Interval(T start, T end) {

        if ((start != null && end != null) && start.compareTo(end) > 0) {
            this.start = end;
            this.end = start;
        } else {
            this.start = start;
            this.end = end;
        }
    }

    /**
     * Return value of start edge.
     *
     * @return start edge
     */
    public T getStart() {
        return start;
    }

    /**
     * Return value of end edge
     *
     * @return end edge
     */
    public T getEnd() {
        return end;
    }

    /**
     * Informs about infinity of start edge of instace (infinity edge = null).
     *
     * @return true if start edge is null, otherwise false
     */
    public boolean isInfiniteStart() {
        return (start == null);
    }

    /**
     * Informs about infinity of end edge of instace (infinity edge = null).
     *
     * @return true if end edge is null, otherwise false
     */
    public boolean isInfiniteEnd() {
        return (end == null);
    }

    /**
     * Tests if instance and other interval have equal start edge.
     *
     * @param other compared interval
     * @return true if start edge of booth intervals is same, otherwise flase
     */
    public boolean isEqualStart(Interval<T> other) {
        if (start == null) {
            return other.start == null;
        } else if (other.start == null) {
            return false;
        } else {
            return start.compareTo(other.start) == 0;
        }
    }

    /**
     * Tests if instance and other interval have equal end edge.
     *
     * @param other compared interval
     * @return true if end edge of booth intervals is same, otherwise flase
     */
    public boolean isEqualEnd(Interval<T> other) {
        if (end == null) {
            return other.end == null;
        } else if (other.end == null) {
            return false;
        } else {
            return end.compareTo(other.end) == 0;
        }
    }

    /**
     * Return the position given value against instance
     *
     * @param position compared value
     * @return position against interval
     */
    public PositionAgainstInterval positionAgainstInterval(T position) {
        if (position == null) {
            throw new ArgumentNullException("Position is null", "position");
        }
        if (isInfiniteStart() && isInfiniteEnd()) {
            return PositionAgainstInterval.Between;
        }

        if (isInfiniteStart() && isInfiniteEnd() == false) {
            if (position.compareTo(end) < 0) {
                return PositionAgainstInterval.Between;
            } else if (position.compareTo(end) == 0) {
                return PositionAgainstInterval.AtEnd;
            } else {
                return PositionAgainstInterval.After;
            }
        }

        if (isInfiniteStart() == false && isInfiniteEnd()) {
            if (position.compareTo(start) < 0) {
                return PositionAgainstInterval.Before;
            } else if (position.compareTo(start) == 0) {
                return PositionAgainstInterval.AtStart;
            } else {
                return PositionAgainstInterval.Between;
            }
        }

        if (start.equals(end)) {
            if (position.compareTo(start) < 0) {
                return PositionAgainstInterval.Before;
            } else if (position.compareTo(start) == 0) {
                return PositionAgainstInterval.Between;
            } else {
                return PositionAgainstInterval.After;
            }
        }

        if (position.compareTo(start) < 0) {
            return PositionAgainstInterval.Before;
        } else if (position.compareTo(start) == 0) {
            return PositionAgainstInterval.AtStart;
        } else if (position.compareTo(end) < 0) {
            return PositionAgainstInterval.Between;
        } else if (position.compareTo(end) == 0) {
            return PositionAgainstInterval.AtEnd;
        } else {
            return PositionAgainstInterval.After;
        }

    }

    /**
     * Comapre position against other interval.
     *
     * @param other compared interval, if null the ArgumentNullException is
     * thrown
     * @return enumIntervalComaprison where first part of text enum value
     * describe position other start point against instance and second part of
     * text enum value describe position other end point against instance.
     * @throws ArgumentNullException - if other is null
     */
    public IntevalComparison compareToOther(Interval<T> other) {
        if (other == null) {
            throw new ArgumentNullException("Parameter Other is null.", "Other");
        }
        PositionAgainstInterval otherStartPosition;
        PositionAgainstInterval otherEndPosition;
        if ((other.isInfiniteStart() == false) && (other.isInfiniteEnd() == false)) {
            otherStartPosition = positionAgainstInterval(other.getStart());
            otherEndPosition = positionAgainstInterval(other.getEnd());
            switch (otherStartPosition) {
                case Before:
                    switch (otherEndPosition) {
                        case Before:
                            return IntevalComparison.BeforeBefore;
                        case AtStart:
                            return IntevalComparison.BeforeStart;
                        case Between:
                            return IntevalComparison.BeforeBetween;
                        case AtEnd:
                            return IntevalComparison.BeforeEnd;
                        case After:
                            return IntevalComparison.BeforeAfter;
                    }
                case AtStart:
                    switch (otherEndPosition) {
                        case Between:
                            return IntevalComparison.StartBetween;
                        case AtEnd:
                            return IntevalComparison.StartEnd;
                        case After:
                            return IntevalComparison.StartAfter;
                    }
                case Between:
                    switch (otherEndPosition) {
                        case Between:
                            return IntevalComparison.BetweenBetween;
                        case AtEnd:
                            return IntevalComparison.BetweenEnd;
                        case After:
                            return IntevalComparison.BetweenAfter;
                    }
                case AtEnd:
                    return IntevalComparison.EndAfter;
                case After:
                    return IntevalComparison.AfterAfter;
            }
        } else if (other.isInfiniteStart() == false) {
            otherStartPosition = positionAgainstInterval(other.getStart());
            switch (otherStartPosition) {
                case Before:
                    if (isInfiniteEnd()) {
                        return IntevalComparison.BeforeEnd;
                    } else {
                        return IntevalComparison.BeforeAfter;
                    }
                case AtStart:
                    if (isInfiniteEnd()) {
                        return IntevalComparison.StartEnd;
                    } else {
                        return IntevalComparison.StartAfter;
                    }
                case Between:
                    if (isInfiniteEnd()) {
                        return IntevalComparison.BetweenEnd;
                    } else {
                        return IntevalComparison.BetweenAfter;
                    }
                case AtEnd:
                    return IntevalComparison.EndAfter;
                case After:
                    return IntevalComparison.AfterAfter;
            }
        } else if (other.isInfiniteEnd() == false) {
            otherEndPosition = positionAgainstInterval(other.getEnd());
            switch (otherEndPosition) {
                case Before:
                    return IntevalComparison.BeforeBefore;
                case AtStart:
                    return IntevalComparison.BeforeStart;
                case Between:
                    if (isInfiniteStart()) {
                        return IntevalComparison.StartBetween;
                    } else {
                        return IntevalComparison.BeforeBetween;
                    }
                case AtEnd:
                    if (isInfiniteStart()) {
                        return IntevalComparison.StartEnd;
                    } else {
                        return IntevalComparison.BeforeEnd;
                    }
                case After:
                    if (isInfiniteStart()) {
                        return IntevalComparison.StartAfter;
                    } else {
                        return IntevalComparison.BeforeAfter;
                    }
            }
        } else if (this.isInfiniteStart() == false && this.isInfiniteEnd() == false) {
            return IntevalComparison.BetweenBetween;
        } else if (this.isInfiniteStart() == false) {
            return IntevalComparison.BetweenEnd;
        } else if (this.isInfiniteEnd() == false) {
            return IntevalComparison.StartBetween;
        } else {
            return IntevalComparison.StartEnd;
        }
        return IntevalComparison.Undefined; // no way to catch this statement
    }

    /**
     * Checks, if instance is completely inside in given other interval. If one
     * of edge is equal to corresponding edge of given interval then false is
     * returned. If other interval is null then false is returned
     *
     * @param other compared interval
     * @return true if start and end edge of instance are between start and end
     * edges of other interval otherwise false.
     */
    public boolean isCompletelyInside(Interval<T> other) {
        if (other == null) {
            return false;
        }
        IntevalComparison comp = this.compareToOther(other);
        return comp == IntevalComparison.BeforeAfter;
    }

    /**
     * Chceks, if instance is inside in given other interval include edges. If
     * other interval is null then false is returned
     *
     * @param other compared interval
     * @return true if:
     * <ul>
     * <li>start edge of instance is equal to start edge or between start and
     * end edge of other inetrval AND</li>
     * <li>end edge of instace is equal to end edge or between start and end
     * edge of other inetrval</li>
     * </ul>
     * otherwise false
     */
    public boolean isCompletelyInsideOrAtEdge(Interval<T> other) {
        if (other == null) {
            return false;
        }
        IntevalComparison comp = compareToOther(other);
        return comp.isIn(IntevalComparison.BeforeAfter, IntevalComparison.StartAfter, IntevalComparison.BeforeEnd, IntevalComparison.StartEnd);
    }

    /**
     * Checks, if instance contains given other inetreval completely. If other
     * interval is null then false is returned
     *
     * @param other - compared interval
     * @return true if start and end edge of other inetrval are between start
     * and end edges of instance otherwise false.
     */
    public boolean completelyContains(Interval<T> other) {
        if (other == null) {
            return false;
        }
        IntevalComparison comp = compareToOther(other);
        return comp == IntevalComparison.BetweenBetween;
    }

    /**
     * Checks, if instance contains given other inetreval include edges. If
     * other interval is null then false is returned
     *
     * @param other compared interval
     * @return true if:
     * <ul>
     * <li>start edge of other inetrval is equal to start edge or between start
     * and end edge of instance AND</li>
     * <li>end edge of other interval is equal to end edge or between start and
     * end edge of instance</li>
     * </ul>
     * otherwise false
     */
    public boolean completelyContainsOrAtEdge(Interval<T> other) {
        if (other == null) {
            return false;
        }
        IntevalComparison comp = compareToOther(other);
        return comp.isIn(IntevalComparison.BetweenBetween, IntevalComparison.StartBetween, IntevalComparison.BetweenEnd, IntevalComparison.StartEnd);
    }

    /**
     * Checks, if is there is any overlap with other interval. If intervals only
     * touch on edges then no overlap is identified (e.g intervals (10,20) and
     * (20,30) have no overlap.
     *
     * @param other tested interval (if null false is returned)
     * @return true if overalap other false
     */
    public boolean isOverlap(Interval<T> other) {
        if (other == null) {
            return false;
        }
        IntevalComparison comp = compareToOther(other);

        return comp.isIn(IntevalComparison.BeforeBefore, IntevalComparison.BeforeStart, IntevalComparison.EndAfter, IntevalComparison.AfterAfter) == false;
    }

    /**
     * Find interval that is overlap between instance and other interval
     *
     * @param other other interval (if null then null is returned)
     * @return interval, which is overlap of this interval and another interval
     * or null if no overlap between intevals
     */
    public Interval<T> overlap(Interval<T> other) {

        if (other == null) {
            return null;
        }
        if (isOverlap(other) == false) {
            return null;
        }

        T newStart;
        T newEnd;

        if (isInfiniteStart()) {
            newStart = other.getStart();
        } else if (other.isInfiniteStart()) {
            newStart = getStart();
        } else {
            newStart = maximum(getStart(), other.getStart());
        }

        if (isInfiniteEnd()) {
            newEnd = other.getEnd();
        } else if (other.isInfiniteEnd()) {
            newEnd = getEnd();
        } else {
            newEnd = minimum(getEnd(), other.getEnd());
        }

        return new Interval<>(newStart, newEnd);
    }

    /**
     * Create union from instance and other interval
     *
     * @param other other interval (if null then null is returned)
     * @return interval, which which is union of this inetval and another
     * interval. if there is no overlap of intervals then null is returned
     */
    public Interval<T> union(Interval<T> other) {

        if (other == null) {
            return null;
        }
        if (isOverlap(other) == false) {
            return null;
        }

        T newStart = minimum(getStart(), other.getStart());
        T newEnd = maximum(getEnd(), other.getEnd());

        return new Interval<>(newStart, newEnd);
    }

    /**
     * Cut instance by other interval. If other interval completely contains
     * instance emtpy list is returned. If instance completely contains other
     * interval two intervals are returned. Otherwise one interval is returned
     * with modified edge. If othre is null or if there is no overlap between
     * instance and other, then interval same as instance is returned
     *
     * @param other interval to use cut instance
     * @return list of interval(s) created by cut operation
     */
    public List<Interval<T>> cutBy(Interval<T> other) {
        List<Interval<T>> outlist = new ArrayList<>();
        if (isOverlap(other) == false) {
            outlist.add(new Interval<>(this.getStart(), this.getEnd()));
        } else {
            Interval<T> overlap = this.overlap(other);
            if (overlap.getStart().compareTo(overlap.getEnd()) == 0) {
                outlist.add(new Interval<>(this.getStart(), this.getEnd()));
            } else if (overlap.compareToOther(this) != IntevalComparison.StartEnd) {
                if (this.completelyContains(overlap)) {
                    outlist.add(new Interval<>(this.getStart(), overlap.getStart()));
                    outlist.add(new Interval<>(overlap.getEnd(), this.getEnd()));
                } else if (this.getStart().compareTo(overlap.getStart()) == 0) {
                    outlist.add(new Interval<>(overlap.getEnd(), this.getEnd()));
                } else {
                    outlist.add(new Interval<>(this.getStart(), overlap.getStart()));
                }
            }
        }
        return outlist;
    }

    /**
     * Chceks, if type of interval can compute length.
     *
     * @return true if type of interval (T) is instance of Number or Measurable
     * and start or end is not null, otherwise false.
     */
    public boolean hasLength() {
        Class<? extends Comparable> cls;

        if (this.getStart() != null) {
            cls = this.getStart().getClass();
        } else if (this.getEnd() != null) {
            cls = this.getEnd().getClass();
        } else {
            return false;
        }
        for (Class<?> ifc : cls.getInterfaces()) {
            if (ifc.getSimpleName().equals(Measurable.class.getSimpleName())) {
                return true;
            }
        }
        List<Class<?>> superclasses = getSuperclasses(cls);
        for (Class<?> sc : superclasses) {
            if (sc.getSimpleName().equals(Number.class.getSimpleName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Return Length of interval (subtract end - start). If there is no
     * possibility to compute length then an exception is thrown.
     *
     * @see #hasLength()
     * @return subtract end - start or Double.POSITIVE_INFINITY if one of
     * bondary is infinite
     * @exception UnsupportedOperationException - if T is not instance of Number
     * or Measurable
     *
     */
    public Double getLength() {

        if (hasLength() == false) {
            throw new UnsupportedOperationException("This method can not be used for this type of Interval.");
        }
        if (isInfiniteStart() == false && isInfiniteEnd() == false) {
            Double startValue;
            Double endValue;
            if (getStart() instanceof Number) {
                startValue = ((Number) getStart()).doubleValue();
                endValue = ((Number) getEnd()).doubleValue();
            } else if (getStart() instanceof Measurable) {
                startValue = ((Measurable) getStart()).getMetrics();
                endValue = ((Measurable) getEnd()).getMetrics();
            } else {
                throw new UnsupportedOperationException("This method can not be used for this type of Interval.");
            }
            return endValue - startValue;
        } else {
            return Double.POSITIVE_INFINITY;
        }

    }

    /**
     * Return string prepresentation of instance
     *
     * @return - string representation of instance
     */
    @Override
    public String toString() {
        String startStr = (isInfiniteStart()) ? "INF" : getStart().toString();
        String endStr = (isInfiniteEnd()) ? "INF" : getEnd().toString();
        return "Start: " + startStr + " End: " + endStr;
    }

    /**
     * Return hash code of instance
     *
     * @return - hash code
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.start);
        hash = 59 * hash + Objects.hashCode(this.end);
        return hash;
    }

    /**
     * Tests if instance is equal to other. Instances are equal if they are of
     * the same type and of the same edges
     *
     * @param other compared object
     * @return true if other object is same type and start end end edges are
     * equals
     */
    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (getClass() != other.getClass()) {
            return false;
        }
        final Interval<T> otherInterval = (Interval<T>) other;
        return this.isEqualStart(otherInterval) && this.isEqualEnd(otherInterval);
    }

    /**
     * Create new instance based on this instance (create clone of instance)
     *
     * @return new instance
     * @throws java.lang.CloneNotSupportedException if there is no possibility
     * to clone edge
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
        T newStart = cloneEdge(this.start);
        T newEnd = cloneEdge(this.end);
        return new Interval(newStart, newEnd);
    }

    private T minimum(T edge1, T edge2) {
        if (edge1 == null) {
            return null;
        }
        if (edge2 == null) {
            return null;
        }
        if (edge1.compareTo(edge2) < 0) {
            return edge1;
        } else {
            return edge2;
        }
    }

    private T maximum(T edge1, T edge2) {
        if (edge1 == null) {
            return null;
        }
        if (edge2 == null) {
            return null;
        }
        if (edge1.compareTo(edge2) > 0) {
            return edge1;
        } else {
            return edge2;
        }
    }

    private List<Class<?>> getSuperclasses(Class<?> clazz) {
        List<Class<?>> output = new ArrayList<>();
        output.add(clazz);
        Class<?> sc = clazz.getSuperclass();
        while (sc != null) {
            output.add(sc);
            sc = sc.getSuperclass();
        }
        return output;
    }

    private T cloneEdge(T edge) throws CloneNotSupportedException {
        if (edge == null) {
            return null;
        }
        if ((edge instanceof Cloneable) == false) {
            T newNonCloneEdge = edge;
            return newNonCloneEdge;
        }
        Class<?> cls = edge.getClass();
        Method cloneMethod;
        Object newEdge;
        try {
            cloneMethod = cls.getDeclaredMethod("clone");
            newEdge = cloneMethod.invoke(edge);
        } catch (NoSuchMethodException ex) {
            T newNonCloneEdge = edge;
            return newNonCloneEdge;
        } catch (InvocationTargetException | IllegalArgumentException | IllegalAccessException | SecurityException ex) {
            throw new CloneNotSupportedException();
        }
        return (T) newEdge;
    }
}
