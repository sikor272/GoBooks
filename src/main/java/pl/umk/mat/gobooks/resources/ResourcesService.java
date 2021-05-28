package pl.umk.mat.gobooks.resources;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import pl.umk.mat.gobooks.commons.exceptions.ResourceNotFound;
import pl.umk.mat.gobooks.config.Config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class ResourcesService {
    private final Config config;

    public HttpEntity<byte[]> getImage(String imageName) throws ResourceNotFound {
        try {
            byte[] image = Files.readAllBytes(Paths.get(config.getImageDir() + imageName));
            var headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);
            headers.setContentLength(image.length);
            return new HttpEntity<>(image, headers);
        } catch (IOException e) {
            throw new ResourceNotFound("Image not found");
        }

    }
}
