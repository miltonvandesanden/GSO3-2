/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fontys.time;

/**
 *
 * @author Stefan
 */
public class TimeSpan2 implements ITimeSpan
{
    private ITime beginTime;
    private long duration; //duration in minutes
    
    public TimeSpan2(ITime beginTime, ITime endTime)
    {
        if (beginTime.compareTo(endTime) >= 0) {
            throw new IllegalArgumentException("begin time "
                    + beginTime + " must be earlier than end time " + endTime);
        }

        this.beginTime = beginTime;
        duration = beginTime.difference(endTime);
    }

    @Override
    public ITime getBeginTime() {
        return beginTime;
    }

    @Override
    public ITime getEndTime() {
        return beginTime.plus((int) duration);
    }

    @Override
    public int length()
    {
        return (int) duration;
    }

    @Override
    public void setBeginTime(ITime beginTime)
    {
        if (beginTime.compareTo(getEndTime()) >= 0)
        {
            throw new IllegalArgumentException("begin time " + beginTime + " must be earlier than end time " + getEndTime());
        }

        this.beginTime = beginTime;
    }

    @Override
    public void setEndTime(ITime endTime)
    {
        if (endTime.compareTo(beginTime) <= 0)
        {
            throw new IllegalArgumentException("end time " + getEndTime() + " must be later than begin time " + beginTime);
        }

        duration = beginTime.difference(endTime);
    }

    @Override
    public void move(int minutes)
    {
        if(minutes < 0)
        {
            throw new IllegalArgumentException("length of period must be positive");
        }
        
        beginTime = beginTime.plus(minutes);
    }

    @Override
    public void changeLengthWith(int minutes)
    {
        if(minutes < 0)
        {
            throw new IllegalArgumentException("length of period must be positive");
        }
        
        duration += minutes;
    }

    @Override
    public boolean isPartOf(ITimeSpan timeSpan2)
    {
        return (beginTime.compareTo(timeSpan2.getBeginTime()) >= 0 && getEndTime().compareTo(timeSpan2.getEndTime()) <= 0);

    }

    @Override
    public ITimeSpan unionWith(ITimeSpan timeSpan2)
    {
        if(beginTime.compareTo(timeSpan2.getEndTime()) > 0 || getEndTime().compareTo(timeSpan2.getBeginTime()) < 0) {
            return null;
        }
        
        ITime begintime, endtime;
        if (this.beginTime.compareTo(timeSpan2.getBeginTime()) < 0)
        {
            begintime = this.beginTime;
        } else
        {
            begintime = timeSpan2.getBeginTime();
        }

        if (getEndTime().compareTo(timeSpan2.getEndTime()) > 0)
        {
            endtime = getEndTime();
        } else
        {
            endtime = timeSpan2.getEndTime();
        }

        return new TimeSpan2(begintime, endtime);        
    }

    @Override
    public ITimeSpan intersectionWith(ITimeSpan timeSpan2)
    {
        ITime begintime, endtime;
        
        if (this.beginTime.compareTo(timeSpan2.getBeginTime()) > 0)
        {
            begintime = this.beginTime;
        } else
        {
            begintime = timeSpan2.getBeginTime();
        }

        if(getEndTime().compareTo(timeSpan2.getEndTime()) < 0)
        {
            endtime = getEndTime();
        } else
        {
            endtime = timeSpan2.getEndTime();
        }

        /*if (begintime.compareTo(endtime) >= 0) {
            return null;
        }*/

        return new TimeSpan2(begintime, endtime);
    }
    
    @Override
    public boolean equals(Object other)
    {
        TimeSpan2 other2 = (TimeSpan2) other;
        
        if(!this.beginTime.equals(other2.getBeginTime()))
        {
            return false;
        }
        
        if(!getEndTime().equals(other2.getEndTime()))
        {
            return false;
        }
        
        return true;
    }
    
    /*@Override
    public String toString()
    {
        return "bt: " + beginTime.toString() + " - et: " + getEndTime().toString();
    }*/
}
