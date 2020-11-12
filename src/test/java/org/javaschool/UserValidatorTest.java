package org.javaschool;

import org.javaschool.dto.UserDto;
import org.javaschool.services.interfaces.UserService;
import org.javaschool.validation.UserValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserValidatorTest {

    @InjectMocks
    private UserValidator userValidator;
    @Mock
    private UserService userService;

    private static UserDto userDto;
    private static Errors errors;

    private static final String USERNAME_VALID = "username";
    private static final String EMAIL_VALID = "valid@email.com";
    private static final String PASSWORD_VALID = "password";
    private static final String EMPTY_STRING = "";
    private static final String STRING_SHORT = "short";
    private static final String STRING_LONG = "abcdefghijklnopqrstuvwxyz1234567890";

    @BeforeEach
    public void setUp() {
        userDto = UserDto.builder()
                .username(USERNAME_VALID)
                .email(EMAIL_VALID)
                .password(PASSWORD_VALID)
                .passwordConfirm(PASSWORD_VALID)
                .build();
        errors = new BeanPropertyBindingResult(userDto, "userDto");
        when(userService.findUserByUsername(anyString())).thenReturn(null);
        when(userService.findUserByEmail(anyString())).thenReturn(null);
    }

    @Test
    public void validateUserValid() {
        userValidator.validate(userDto, errors);
        assertFalse(errors.hasErrors());
    }

    @Test
    public void validateUserEmptyName() {
        userDto.setUsername(EMPTY_STRING);
        userValidator.validate(userDto, errors);
        assertTrue(errors.hasErrors());
        assertNotNull(errors.getFieldError("username"));
    }

    @Test
    public void validateUserShortName() {
        userDto.setUsername(STRING_SHORT);
        userValidator.validate(userDto, errors);
        assertTrue(errors.hasErrors());
        assertNotNull(errors.getFieldError("username"));
    }

    @Test
    public void validateUserLongName() {
        userDto.setUsername(STRING_LONG);
        userValidator.validate(userDto, errors);
        assertTrue(errors.hasErrors());
        assertNotNull(errors.getFieldError("username"));
    }

    @Test
    public void validateUserDuplicateName() {
        when(userService.findUserByUsername(anyString())).thenReturn(new UserDto());
        userValidator.validate(userDto, errors);
        assertTrue(errors.hasErrors());
        assertNotNull(errors.getFieldError("username"));
    }

    @Test
    public void validateUserEmptyEmail() {
        userDto.setEmail(EMPTY_STRING);
        userValidator.validate(userDto, errors);
        assertTrue(errors.hasErrors());
        assertNotNull(errors.getFieldError("email"));
    }

    @Test
    public void validateUserShortEmail() {
        userDto.setEmail(STRING_SHORT);
        userValidator.validate(userDto, errors);
        assertTrue(errors.hasErrors());
        assertNotNull(errors.getFieldError("email"));
    }

    @Test
    public void validateUserLongEmail() {
        userDto.setEmail(STRING_LONG);
        userValidator.validate(userDto, errors);
        assertTrue(errors.hasErrors());
        assertNotNull(errors.getFieldError("email"));
    }

    @Test
    public void validateUserDuplicateEmail() {
        when(userService.findUserByEmail(anyString())).thenReturn(new UserDto());
        userValidator.validate(userDto, errors);
        assertTrue(errors.hasErrors());
        assertNotNull(errors.getFieldError("email"));
    }

    @Test
    public void validateUserEmptyPassword() {
        userDto.setPassword(EMPTY_STRING);
        userValidator.validate(userDto, errors);
        assertTrue(errors.hasErrors());
        assertNotNull(errors.getFieldError("password"));
    }

    @Test
    public void validateUserShortPassword() {
        userDto.setPassword(STRING_SHORT);
        userValidator.validate(userDto, errors);
        assertTrue(errors.hasErrors());
        assertNotNull(errors.getFieldError("password"));
    }

    @Test
    public void validateUserLongPassword() {
        userDto.setPassword(STRING_LONG);
        userValidator.validate(userDto, errors);
        assertTrue(errors.hasErrors());
        assertNotNull(errors.getFieldError("password"));
    }

    @Test
    public void validateUserEmptyPasswordConfirm() {
        userDto.setPasswordConfirm(EMPTY_STRING);
        userValidator.validate(userDto, errors);
        assertTrue(errors.hasErrors());
        assertNotNull(errors.getFieldError("passwordConfirm"));
    }

    @Test
    public void validateUserDifferentPassword() {
        userDto.setPassword(USERNAME_VALID);
        userValidator.validate(userDto, errors);
        assertTrue(errors.hasErrors());
        assertNotNull(errors.getFieldError("passwordConfirm"));
    }

    @Test
    public void validateUserDifferentPasswordConfirm() {
        userDto.setPasswordConfirm(USERNAME_VALID);
        userValidator.validate(userDto, errors);
        assertTrue(errors.hasErrors());
        assertNotNull(errors.getFieldError("passwordConfirm"));
    }
}