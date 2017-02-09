package br.com.visent.analise.adapter;

import java.time.Duration;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class DurationAdapter extends XmlAdapter<String, Duration> {

    @Override
    public Duration unmarshal(String durationString) throws Exception {
        return AdapterHelper.stringToDuration(durationString);
    }

    @Override
    public String marshal(Duration duration) throws Exception {
        return AdapterHelper.durationToString(duration);
    }
    
}