import org.apache.kafka.clients.producer.ProducerRecord;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import java.util.Scanner;

public class SampleProducer {
    public SampleProducer(){
        //Constructor oluşturalım
        //kafka serverla bağlantı kurabilmemiz için
        //bootstrap.servers: What is the location of kafka server
        //Kafka server'ın adresi.
        Properties properties=new Properties();
        properties.put( "bootstrap.servers","localhost:9092");
        properties.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        
        ProducerRecord producerRecord=new ProducerRecord("topictest","name","---Bunu gonderdim gitti---");
        //topic name,data göndermek istenilen topic adı
        //key
        //value
        KafkaProducer kafkaProducer=new KafkaProducer(properties);
        kafkaProducer.send(producerRecord);
        kafkaProducer.close();
        //Kafka producer'ın kafka server'a data göndermesi        
                
                
             
   
        
        
        
       
    }
}
