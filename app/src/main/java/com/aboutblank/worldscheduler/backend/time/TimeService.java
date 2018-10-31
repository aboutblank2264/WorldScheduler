package com.aboutblank.worldscheduler.backend.time;

import java.util.List;

public interface TimeService {

    List<String> getCityNames();

    String getTimeDifference(String timeZoneId);
}
