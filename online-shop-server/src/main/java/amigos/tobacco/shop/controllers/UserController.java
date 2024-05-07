package amigos.tobacco.shop.controllers;

import amigos.tobacco.shop.model.dto.SuccessDTO;
import amigos.tobacco.shop.model.dto.auth.UserDTO;
import amigos.tobacco.shop.model.dto.auth.UserInfoDTO;
import amigos.tobacco.shop.services.interfaces.UserService;
import amigos.tobacco.shop.swagger.SwaggerConfig;
import amigos.tobacco.shop.swagger.SwaggerHttpStatus;
import amigos.tobacco.shop.swagger.SwaggerMessages;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "1. User", description = "These endpoints are used to manipulate users.")
@SecurityRequirement(name = SwaggerConfig.SECURITY_SCHEME_NAME)
@RestController
@RequestMapping("/api/user")
@Validated
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Returns user info.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = SwaggerHttpStatus.OK, description = SwaggerMessages.USER_INFO_IS_RETURNED,
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserInfoDTO.class))}),
            @ApiResponse(responseCode = SwaggerHttpStatus.NOT_FOUND, description = SwaggerMessages.USER_DOES_NOT_EXIST,
                    content = @Content),
            @ApiResponse(responseCode = SwaggerHttpStatus.INTERNAL_SERVER_ERROR, description = SwaggerMessages.INTERNAL_SERVER_ERROR,
                    content = @Content)})
    @GetMapping("/{username}")
    public ResponseEntity<UserInfoDTO> getUserInfo(@Parameter(description = "User's username.") @PathVariable String username) {
        UserInfoDTO userInfoDTO = userService.getUserInfo(username);

        return new ResponseEntity<>(userInfoDTO, HttpStatus.OK);
    }

    @Operation(summary = "Updates user photo.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = SwaggerHttpStatus.OK, description = SwaggerMessages.USER_PHOTO_IS_UPDATED,
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SuccessDTO.class))}),
            @ApiResponse(responseCode = SwaggerHttpStatus.NOT_FOUND, description = SwaggerMessages.USER_DOES_NOT_EXIST,
                    content = @Content),
            @ApiResponse(responseCode = SwaggerHttpStatus.INTERNAL_SERVER_ERROR, description = SwaggerMessages.INTERNAL_SERVER_ERROR,
                    content = @Content)})
    @PostMapping(path = "/{username}/photo", consumes = "multipart/form-data")
    public ResponseEntity<SuccessDTO> setUserPhoto(@PathVariable String username, @Parameter(description = "User's photo.", content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE)) @RequestPart("photo") MultipartFile photo) {
        userService.updatePhoto(username, photo);

        SuccessDTO successDTO = new SuccessDTO();
        successDTO.setStatus(200);
        successDTO.setMessage("user_photo_updated_successfully");

        return new ResponseEntity<>(successDTO, HttpStatus.OK);
    }

    @Operation(summary = "Returns user photo.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = SwaggerHttpStatus.OK, description = SwaggerMessages.USER_PHOTO_RETURNED,
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = byte[].class))}),
            @ApiResponse(responseCode = SwaggerHttpStatus.NOT_FOUND, description = SwaggerMessages.USER_DOES_NOT_EXIST,
                    content = @Content),
            @ApiResponse(responseCode = SwaggerHttpStatus.INTERNAL_SERVER_ERROR, description = SwaggerMessages.INTERNAL_SERVER_ERROR,
                    content = @Content)})
    @GetMapping(path = "/{username}/photo")
    public ResponseEntity<byte[]> getUserPhoto(@PathVariable String username) {
        byte[] photo = userService.getByUsername(username).getPhoto();

        return new ResponseEntity<>(photo, HttpStatus.OK);
    }

    @Operation(summary = "Updates user information.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = SwaggerHttpStatus.OK, description = SwaggerMessages.USER_INFO_UPDATED,
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SuccessDTO.class))}),
            @ApiResponse(responseCode = SwaggerHttpStatus.NOT_FOUND, description = SwaggerMessages.USER_DOES_NOT_EXIST,
                    content = @Content),
            @ApiResponse(responseCode = SwaggerHttpStatus.INTERNAL_SERVER_ERROR, description = SwaggerMessages.INTERNAL_SERVER_ERROR,
                    content = @Content)})
    @PutMapping(path = "/{username}")
    public ResponseEntity<SuccessDTO> updateUser(@PathVariable String username, @Parameter(description = "User's updated information.") @Validated @RequestBody UserDTO userDTO) {
        userService.updateUser(username, userDTO);

        SuccessDTO successDTO = new SuccessDTO();
        successDTO.setStatus(200);
        successDTO.setMessage("user_information_updated_successfully");

        return new ResponseEntity<>(successDTO, HttpStatus.OK);
    }
}
