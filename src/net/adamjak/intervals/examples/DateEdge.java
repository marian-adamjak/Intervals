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

package net.adamjak.intervals.examples;

import java.util.Date;
import net.adamjak.intervals.Measurable;

/**
 * Class demonstrate how to implement Measurable interface.
 * Class extends class {@link java.util.Date} to use in class as an edge
 * Implemantation of Measurable give a possibility find out which interval of date is longer.
 * <p>
 * <br>Code example:<br>
 * <code>
 * Calendar calendar = Calendar.getInstance();<br>
 * calendar.set(2016, 1, 1);<br>
 * DateEdge startDate = new DateEdge(calendar.getTimeInMillis());<br>
 * calendar.set(2017, 1, 1);<br>
 * DateEdge endDate = new DateEdge(calendar.getTimeInMillis());<br>
 * Interval&lt;Date&gt; dateInterval = new Interval&lt;&gt;(startDate,endDate);<br>
 * System.out.println("Start date: " + dateInterval.getStart());<br>
 * System.out.println("End date:" + dateInterval.getEnd());<br>
 * Long durationInSeconds = new Double(dateInterval.getLength()/1000).longValue();<br>
 * System.out.println("Lenght in seconds: " + durationInSeconds);<br>
 * </code>
 * </p>
 * @author Marian Adamjak
 */
public class DateEdge extends Date implements Measurable, Cloneable {

    private static final long serialVersionUID = 1965994389878168103L;

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
