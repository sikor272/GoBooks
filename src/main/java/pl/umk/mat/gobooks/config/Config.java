package pl.umk.mat.gobooks.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    private String imageRegex = "jpeg|jpg|png";
    private String imageDir = "D:\\images\\";

    public String getImageRegex() {
        return imageRegex;
    }

    public String getImageDir() {
        return imageDir;
    }
}
