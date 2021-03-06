package pl.umk.mat.gobooks.resources;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import pl.umk.mat.gobooks.commons.exceptions.ResourceNotFound;

@RestController
@RequestMapping("api/resources")
@RequiredArgsConstructor
@Tag(name = "Resource Controller", description = "This controller provides the resource management logic.")
public class ResourcesController {
    private final ResourcesService resourcesService;

    @GetMapping("/{imageName}")
    @ResponseBody
    @Operation(summary = ".", tags = {"Resource Controller"})
    public HttpEntity<byte[]> getImage(
            @PathVariable String imageName
    ) throws ResourceNotFound {
        return resourcesService.getImage(imageName);
    }
}
