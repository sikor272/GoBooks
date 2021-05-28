package pl.umk.mat.gobooks.users;

import lombok.RequiredArgsConstructor;
import net.bytebuddy.utility.RandomString;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pl.umk.mat.gobooks.auth.enums.Role;
import pl.umk.mat.gobooks.auth.utils.UserPrincipal;
import pl.umk.mat.gobooks.commons.exceptions.BadRequest;
import pl.umk.mat.gobooks.commons.exceptions.ResourceAlreadyExist;
import pl.umk.mat.gobooks.commons.exceptions.Unauthorized;
import pl.umk.mat.gobooks.config.Config;
import pl.umk.mat.gobooks.users.dto.UserResponse;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private static final String AVATAR_DEFAULT_JPG = "avatar_default.jpg";
    private final UserRepository userRepository;
    private final Config config;

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    public List<UserResponse> findAll(UserPrincipal userPrincipal, Pageable pageable) throws Unauthorized {
        if (userPrincipal.getUser().getRole() != Role.ADMIN) {
            throw new Unauthorized("No permission to get data");
        }
        return userRepository.findAll(pageable).stream()
                .map(UserResponse::new)
                .collect(Collectors.toList());
    }

    public UserResponse findById(UserPrincipal userPrincipal, Long id) throws BadRequest {
        if (userPrincipal.getUser().getRole() != Role.ADMIN) {
            throw new BadRequest("No permission to get data");
        }
        return new UserResponse(userRepository.findByIdOrThrow(id));
    }


    @Transactional
    public UserResponse setUserAvatar(
            UserPrincipal userPrincipal, MultipartFile file
    ) throws IOException, BadRequest, ResourceAlreadyExist {
        String originalName = file.getOriginalFilename();
        if (originalName == null) {
            throw new BadRequest("Incorrect file name.");
        }
        var user = userPrincipal.getUser();
        String fileExtension = originalName.substring(originalName.lastIndexOf(".") + 1).toLowerCase();
        if (fileExtension.equals(file.getOriginalFilename()))
            throw new BadRequest("Incorrect file extension.");
        if (!Pattern.compile(config.getImageRegex()).matcher(fileExtension).find()) {
            throw new BadRequest("Incorrect file type (only jpg, jpeg, png supported).");
        }
        String filename = "avatar_" + RandomString.make(20) + "." + fileExtension;
        String filePath = config.getImageDir() + filename;
        var directory = new File(config.getImageDir());
        if (!directory.exists()) {
            directory.mkdirs();
        }
        if (new File(filePath).exists())
            throw new ResourceAlreadyExist("File exist try later.");
        if (user.getAvatar() != null && !"".equals(user.getAvatar()) && !user.getAvatar().equals(config.getImageDir() + AVATAR_DEFAULT_JPG)) {
            new File(config.getImageDir() + user.getAvatar()).delete();
        }
        user.setAvatar(filename);
        Files.copy(
                file.getInputStream(),
                Path.of(filePath),
                StandardCopyOption.REPLACE_EXISTING
        );
        userRepository.save(user);
        return new UserResponse(user);
    }

    @Transactional
    public UserResponse deleteUserAvatar(UserPrincipal userPrincipal) {
        User user = userPrincipal.getUser();
        if (!"".equals(user.getAvatar()) && !user.getAvatar().equals(config.getImageDir() + AVATAR_DEFAULT_JPG)) {
            new File(config.getImageDir() + user.getAvatar()).delete();
        }
        user.setAvatar(AVATAR_DEFAULT_JPG);
        userRepository.save(user);
        return new UserResponse(user);
    }
}
