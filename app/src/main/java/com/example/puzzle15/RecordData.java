package com.example.puzzle15;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class RecordData implements Parcelable {
    private long time,step;
    private Date date;

    public RecordData(long time, long step, Date date) {
        this.time = time;
        this.step = step;
        this.date = date;
    }

    protected RecordData(Parcel in) {
        time = in.readLong();
        step = in.readLong();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(time);
        dest.writeLong(step);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RecordData> CREATOR = new Creator<RecordData>() {
        @Override
        public RecordData createFromParcel(Parcel in) {
            return new RecordData(in);
        }

        @Override
        public RecordData[] newArray(int size) {
            return new RecordData[size];
        }
    };

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getStep() {
        return step;
    }

    public void setStep(long step) {
        this.step = step;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "RecordData{" +
                "time=" + time +
                ", step=" + step +
                ", date=" + date +
                '}';
    }
}
