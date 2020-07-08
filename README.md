# Kafka-Producer-olusturma
Kafka ve Zookeper Windows'a kurulumu ve basit bir Kafka Producer oluşturma

### Zookeper Kurulum
Kafkada çalışmak için zookeeper’ın varlığına ihtiyaç duyulmaktadır. <br>
Biz apache-zookeeper-3.6.1 sürümünü indireceğiz.<br>
İndirilecek site: https://zookeeper.apache.org/releases.html#download<br>
<br>
•	Zookeeper dosyamızın içine bir yeni klasör açalım adını “data” koyduk.<br>
•	İndirilen dosyamızın içine girip conf klasörünü açalım. zoo.cfg dosyasını notepad++ ile açıp, <br>
dataDir=/tmp/zookeeper bu kod satırını dataDir=C:\\zookeeper\\data şeklinde değiştirelim. <br>Data isimli dosyanızın yolunu eklemiş olacaksınız.<br>
•	Bilgisayar>Özellikler>Gelişmiş Sistem Ayarları>Gelişmiş>Ortam Değişkenleri<br> yeni deyip ZOOKEEPER_HOME isimli kullanıcı değişkenini ekleyelim.<br>
Sisteminizde nereye kurduysanız değişken değeri kısmına onu yazın. (Ör.  C:\BigData\zookeeper )<br>
•	Bilgisayar>Özellikler>Gelişmiş Sistem Ayarları>Gelişmiş>Ortam Değişkenleri<br> 
User variables for user kısmındaki Path kısmına tıklayıp düzenle diyelim. Yeni deyip zookeeper’ ımızı ekleyelim.<br>
“%ZOOKEEPER_HOME%\bin” şeklinde olmalıdır.<br>
•	Cmd komut istemine aşağıdaki komut ile başlat diyebiliriz.<br>
```
zookeeper-server-start.bat C:\BigData\Kafka\config\zookeeper.properties
```
herhangi bir hata ile karşılaşmamanız gerekiyor.(veya cmd komut istemine zkserver yazarak çalıştırabilirsiniz.)<br>


### Kafka Kurulumu
Kafka indirirken en önemli nokta scala sürümünüzle uyumlu olan kafka sürümünü indirmektir.<br>
Sitesi: https://kafka.apache.org/downloads<br>
(Scala versiyonumuz 2.11.12 olduğu için Kafka  kafka_2.11-2.4.1.tgz indirdik.)<br> 
•	Bilgisayar>Özellikler>Gelişmiş Sistem Ayarları>Gelişmiş>Ortam Değişkenleri yeni deyip<br>
KAFKA_HOME isimli kullanıcı değişkenini ekleyelim. Sisteminizde nereye kurduysanız değişken değeri kısmına onu yazın. (Ör.  C:\BigData\Kafka)<br>
•	Bilgisayar>Özellikler>Gelişmiş Sistem Ayarları>Gelişmiş>Ortam Değişkenleri<br>
User variables for user kısmındaki Path kısmına tıklayıp düzenle diyelim. Yeni deyip kafka’ ımızı ekleyelim.<br>
“C:\BigData\Kafka\bin\windows” şeklinde olmalıdır.
•	Loglar için kafka klasörümüzün içine kafka-logs isimli yeni bir dosya oluşturalım.<br>
Kafka>conf>server.properties dosyasını açalım. log.dirs=C:\BigData\Kafka\kafka-logs olarak değiştirelim.
•	Başlatmak için cmd ye <br>
```
kafka-server-start.bat C:\BigData\Kafka\config\server.properties<br>
```
(Note:Önemli bir nokta->zookeper' ı cmd de zkserver deyip çalıştırdıktan sonra o komut istemini kapatmayın. Yeni bir cmd den kafka çalıştırın. Aksi taktirde kafka çalışmayacaktır.)


### Topic Nedir?
Topic kullanıcı tanımlı kategori ismidir. Kafka message’ları Topic‘lerde tutar. Bir Kafka cluster’ında binlerce topic olabilir. Producer’lar topic’lere mesaj gönderirken,consumer lar ise topic’leri dinler. Bir veya birden fazla bölümden(partitions) meydana gelirler(log dosyaları). Her partition topic’in bir kısmına sahip olur. Ve her bölüm kendi içinde sıralıdır. Bu sıraya offset denilir. Her partitionda birbirinden bağımsız bir offset sırası vardır.
Topic’e gelen mesaj bölüm sonuna eklenir. Her mesajın offset ve partition bilgisi vardır. Veri bir kez bir partition’a yazıldıktan sonra bir daha değiştirilemez. (immutability). Tüm mesajlar belirlenen süre boyunca (immutable olarak gerçekleştiği için)  partition ve offset bilgisi değişmeden tutulur. Bu işlem sayesinde istenilen mesaj, herhangi bir okuma işleminden sonra bile kaybolmaz, bu sayede tekrar erişim mümkün olmaktadır.

### Topic Oluşturma
```
C:\Users\fatma>cd /BigData/Kafka
C:\BigData\Kafka> bin\windows\zookeeper-server-start.bat config\zookeeper.propertie
```
(Kafka server’ı başlatmış olduk.)<br>  
```
C:\BigData\Kafka> bin\windows\kafka-topics.bat --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic test
```
(Test isimli bir topic oluşturduk.Tek bir partition’a sahip.)<br>
```
C:\BigData\Kafka>bin\windows\kafka-topics.bat --list --bootstrap-server localhost:9092
test
```
(topic’ i görme komutumuz)<br> 
•	Alternative way to see;<br> 
```
C:\BigData\Kafka>bin\windows\kafka-topics.bat --list --zookeeper localhost:2181 
test
```
 

### Producer Nedir?
Producer’lar Topic’lere message gönderir (Publisher pozisyonundadırlar.) Veriyi topic’e key ile ve key’siz olmak üzere iki farklı yolla gönderebilirler.<br> 
•	Key ile gönderme (semantic partitioning)<br> 
•	Key’siz gönderme (round robin)<br> <br> 

Keyle gönderirken hangi message’in hangi bölüme gideceğine karar verilebilir.
Key ile gönderme durumunda ilk gönderilen mesaj hangi partition’a gittiyse aynı key ile gönderilen diğer mesajlar da aynı partition’a gönderilirler. Belli bir sınıftaki veriye consumer tarafından sıralı bir şekilde ulaşılmasını istiyorsa key ile gönderme tercih edilir.
Örneğin bir message gönderilirken message key verilmiş ise gönderilen bu message, message key’e göre ilgili topic’in semantic partitioning bölümüne yazılmaktadır. Yani istediğimiz message’ları aynı partition içerisine gönderebiliriz. <br> 

Key’siz gönderme yönteminde Kafka iş yükünü dağıtmak için (load balancing) sıralı bir şekilde (round robin) gönderecektir. Bu durumda verimize sonradan sıralı bir şekilde ulaşamayız.<br> 
### Java ile Kafka Producer oluşturma

•	Öncelikle topictest adında bir topic oluşturalım.<br>
```
C:\BigData\Kafka>bin\windows\kafka-topics.bat --create --replication-factor 1 --partitions 1 --topic topictest --zookeeper  localhost:2181 
Created topic topictest.
```

•	Topic listesine bakalım.<br>
```
C:\BigData\Kafka> bin\windows\kafka-topics.bat --list --zookeeper localhost:2181
test
topictest
```

•	Apache Netbeans kurulumu yapalım.<br> 
•	Bir maven projesi oluşturduktan sonra Project Files kısmında gelen pom.xml dosyasına bazı eklentiler yapmamız gerekiyor.<br>
```
       <dependencies>
        <!-- https://mvnrepository.com/artifact/org.apache.kafka/kafka-clients -->
        <dependency>
            <groupId>org.apache.kafka</groupId>
            <artifactId>kafka-clients</artifactId>
            <version>2.4.1</version>
        </dependency>
        
        <!-- https://mvnrepository.com/artifact/org.slf4j/slf4j-simple -->
        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-simple</artifactId>
            <version>1.7.28</version>
        </dependency>

    </dependencies>
```

•	Producer class’ımızı ve main’i yazıyoruz.<br> 

•	Bir cmd isteminde zookeeper’ı,bir diğerinde kafka’yı çalıştıralım.<br> 
•	Bir başka cmd komut istemine<br> 
```
C:\Users\fatma>cd /BigData/Kafka
C:\BigData\Kafka>.\bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic topictest --from-beginning
```

•	Çıktı:<br> 
```
SLF4J: Class path contains multiple SLF4J bindings.
SLF4J: Found binding in [jar:file:/C:/BigData/Kafka/libs/slf4j-log4j12-1.7.28.jar!/org/slf4j/impl/StaticLoggerBinder.class]
SLF4J: Found binding in [jar:file:/C:/BigData/Kafka/libs/slf4j-simple-1.7.28.jar!/org/slf4j/impl/StaticLoggerBinder.class] 
SLF4J: See http://www.slf4j.org/codes.html#multiple_bindings for an explanation.
SLF4J: Actual binding is of type [org.slf4j.impl.Log4jLoggerFactory]
---Bunu gonderdim gitti---
```





