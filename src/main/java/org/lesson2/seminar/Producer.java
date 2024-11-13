package org.lesson2.seminar;

import java.util.ArrayList;
import java.util.List;

public class Producer implements IProducer{

    List<IConsumer> consumerList = new ArrayList<>();

    @Override
    public void subscribe(IConsumer consumer) {
        consumerList.add(consumer);
    }

    public void sendMessage(String message) {
        consumerList.forEach(v -> {
            v.getMessage(message);
        });
    }
}
