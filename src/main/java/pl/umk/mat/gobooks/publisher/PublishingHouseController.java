package pl.umk.mat.gobooks.publisher;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.umk.mat.gobooks.commons.IterableResponse;
import pl.umk.mat.gobooks.commons.exceptions.BadRequest;
import pl.umk.mat.gobooks.commons.exceptions.ResourceAlreadyExist;
import pl.umk.mat.gobooks.commons.exceptions.ResourceNotFound;
import pl.umk.mat.gobooks.publisher.dto.NewPublishingHouse;
import pl.umk.mat.gobooks.publisher.dto.PublishingHouseResponse;

import javax.validation.Valid;

@RestController
@RequestMapping("api/publishinghouse")
@RequiredArgsConstructor
@Tag(name = "Publishing House Controller", description = "This controller provide logic for publishing house.")
public class PublishingHouseController {
    private final PublishingHouseService publishingHouseService;

    @GetMapping
    @Operation(summary = ".", tags = {"Publishing House Controller"})
    public IterableResponse<PublishingHouseResponse> getAll(Pageable pageable) {
        return new IterableResponse<>(publishingHouseService.findAll(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = ".", tags = {"Publishing House Controller"})
    public PublishingHouseResponse getById(@PathVariable Long id) {
        return publishingHouseService.findById(id);
    }

    @GetMapping("/search")
    @Operation(summary = ".", tags = {"Publishing House Controller"})
    public IterableResponse<PublishingHouseResponse> search(
            @RequestParam(defaultValue = "") String name,
            Pageable pageable
    ) {
        return new IterableResponse<>(publishingHouseService.search(name, pageable));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = ".", tags = {"Publishing House Controller"})
    public PublishingHouseResponse save(
            @RequestBody @Valid NewPublishingHouse newPublishingHouse
    ) throws ResourceAlreadyExist {
        return publishingHouseService.save(newPublishingHouse);
    }

    @PatchMapping("/{id}")
    @Operation(summary = ".", tags = {"Publishing House Controller"})
    public PublishingHouseResponse updateName(
            @PathVariable Long id,
            @RequestBody @Valid NewPublishingHouse newPublishingHouse
    ) throws ResourceAlreadyExist, ResourceNotFound {
        return publishingHouseService.updateName(id, newPublishingHouse);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = ".", tags = {"Publishing House Controller"})
    public void delete(@PathVariable Long id) throws ResourceNotFound, BadRequest {
        publishingHouseService.delete(id);
    }


}
