# Intervals
Intervals is small Java library to manipulate with intervals (typically mathematical, time or similar intervals).

### Terms
* Interval is bounded by the edges.Interval has exactly two edges.
* Edges are represented by comparable objects. Infinite edges are allowed
* If edge is represented by object with metrics then you can compute length of interval
* Interval series is a collection of pairs interval - value (interval - payload).

### Features

##### Interval's features:

* Interval is immutable object
* You can find out position "point" against interval. The type of point have to be same as type of edges. 
* You can compare position of two intervals. 
* Position of point against interval and position of two intervals are expressed by intuitive enumerations.

##### Intervals series' features:

* Series holds pairs of interval - value as a map, the key of map is interval.
* Overlapped intervals in series are not allowed
* You can find out pair by "point". The type of point have to be same as type of intervals' edges.
* You can insert new pair interval - value. If interval of new pair overlaps another interval in series then overlapped part of interval will be replaced new interval.
* You can find out all gaps among intervals in series

### Typical use

* To store information about attendance during a period
* To store information about objects with time limited validity
* To store data for reservation systems
* ...

### Author
Marian Adamjak, Slovakia, <marian@adamjak.net>
