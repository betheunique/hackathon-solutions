package com.verto.analytics.model;

import com.verto.analytics.util.DefaultResponse;

import java.util.List;

/**
 * @author abhishekrai
 * @since 21/06/2017
 */
public class StatisticsInfo extends DefaultResponse {

    private List<DataInfo> dataStatistics;

    public List<DataInfo> getDataStatistics() {
        return dataStatistics;
    }

    public void setDataStatistics(List<DataInfo> dataStatistics) {
        this.dataStatistics = dataStatistics;
    }

    @Override
    public String toString() {
        return "StatisticsInfo{" +
                "dataStatistics=" + dataStatistics +
                '}';
    }
}
