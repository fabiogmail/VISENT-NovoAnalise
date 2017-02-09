package br.com.visent.analise.adapter;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class LocalTimeAdapter extends XmlAdapter<String, LocalTime> {

    @Override
    public LocalTime unmarshal(String timeString) throws Exception {
        return LocalTime.parse(timeString, DateTimeFormatter.ofPattern("H:mm"));
    }
    
    @Override
    public String marshal(LocalTime localTime) throws Exception {
        return DateTimeFormatter.ofPattern("HH:mm").format(localTime);
    }
}