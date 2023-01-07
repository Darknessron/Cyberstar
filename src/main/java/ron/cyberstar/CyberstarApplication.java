package ron.cyberstar;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CyberstarApplication {

  public static void main(String[] args) throws Exception {
    SpringApplication.run(CyberstarApplication.class, args);
    //writeData();
  }

  public static void writeData() throws Exception {
    File file = new File("D://data.sql");
    if (!file.exists()) file.createNewFile();
    FileWriter fw = new FileWriter(file);

    for (int i = 0; i<= 100; i++)  {
      fw.write(String.format("insert into PUBLIC.\"cyberstar\" (\"login_id\", \"name\") values('Ron%1$03d', 'Test%2$03d'); \r\n", i, i));
    }
    fw.flush();
    fw.close();





  }
}
