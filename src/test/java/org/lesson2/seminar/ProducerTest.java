package org.lesson2.seminar;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ProducerTest {

    @Test
    void testProducer() {
        //given
        ConsumerTelegram consumerTelegram = new ConsumerTelegram();
        ConsumerViber consumerViber = new ConsumerViber();
        Producer producer = new Producer();
        producer.subscribe(consumerTelegram);
        producer.subscribe(consumerViber);
        //when
        producer.sendMessage("Message from Producer");
        //then
        Assertions.assertEquals(1, consumerTelegram.getCount());
        Assertions.assertEquals(1, consumerViber.getCount());
    }
}
