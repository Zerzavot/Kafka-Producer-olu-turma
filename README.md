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
•	Bilgisayar>Özellikler>Gelişmiş Sistem Ayarları>Gelişmiş>Ortam Değişkenleri<br> yeni deyip ZOOKEEPER_HOME isimli kullanıcı değişkenini ekleyelim. Sisteminizde nereye kurduysanız değişken değeri kısmına onu yazın. (Ör.  C:\BigData\zookeeper )
•	Bilgisayar>Özellikler>Gelişmiş Sistem Ayarları>Gelişmiş>Ortam Değişkenleri<br> User variables for user kısmındaki Path kısmına tıklayıp düzenle diyelim. Yeni deyip zookeeper’ ımızı ekleyelim. “%ZOOKEEPER_HOME%\bin” şeklinde olmalıdır.

•	Cmd komut istemine aşağıdaki komut ile başlat diyebiliriz.<br>
zookeeper-server-start.bat C:\BigData\Kafka\config\zookeeper.properties herhangi bir hata ile karşılaşmamanız gerekiyor.
(veya cmd komut istemine zkserver yazarak çalıştırabilirsiniz.)
