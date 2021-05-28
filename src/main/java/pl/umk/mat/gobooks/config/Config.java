package pl.umk.mat.gobooks.config;

import org.springframework.context.annotation.Configuration;

import java.nio.file.FileSystems;

@Configuration
public class Config {
    static String imageRegex = "jpeg|jpg|png";
    static String imageDir = "avatars";

    public String getImageRegex() {
        return imageRegex;
    }

    public String getImageDir() {
        return System.getProperty("user.dir") + FileSystems.getDefault().getSeparator() + imageDir + FileSystems.getDefault().getSeparator();
    }
}
