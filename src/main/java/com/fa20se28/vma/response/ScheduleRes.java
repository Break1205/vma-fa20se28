package com.fa20se28.vma.response;

import com.fa20se28.vma.model.Schedule;

import java.util.List;

public class ScheduleRes {
    private List<Schedule> schedules;

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }
}
