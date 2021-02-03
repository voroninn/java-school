package org.javaschool.validation;

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
    public void validateUserNameEmpty() {
        userDto.setUsername(EMPTY_STRING);
        userValidator.validate(userDto, errors);
        assertTrue(errors.hasErrors());
        assertNotNull(errors.getFieldError("username"));
    }

    @Test
    public void validateUserNameShort() {
        userDto.setUsername(STRING_SHORT);
        userValidator.validate(userDto, errors);
        assertTrue(errors.hasErrors());
        assertNotNull(errors.getFieldError("username"));
    }

    @Test
    public void validateUserNameLong() {
        userDto.setUsername(STRING_LONG);
        userValidator.validate(userDto, errors);
        assertTrue(errors.hasErrors());
        assertNotNull(errors.getFieldError("username"));
    }

    @Test
    public void validateUserNameDuplicate() {
        when(userService.findUserByUsername(anyString())).thenReturn(new UserDto());
        userValidator.validate(userDto, errors);
        assertTrue(errors.hasErrors());
        assertNotNull(errors.getFieldError("username"));
    }

    @Test
    public void validateUserEmailEmpty() {
        userDto.setEmail(EMPTY_STRING);
        userValidator.validate(userDto, errors);
        assertTrue(errors.hasErrors());
        assertNotNull(errors.getFieldError("email"));
    }

    @Test
    public void validateUserEmailShort() {
        userDto.setEmail(STRING_SHORT);
        userValidator.validate(userDto, errors);
        assertTrue(errors.hasErrors());
        assertNotNull(errors.getFieldError("email"));
    }

    @Test
    public void validateUserEmailLong() {
        userDto.setEmail(STRING_LONG);
        userValidator.validate(userDto, errors);
        assertTrue(errors.hasErrors());
        assertNotNull(errors.getFieldError("email"));
    }

    @Test
    public void validateUserEmailDuplicate() {
        when(userService.findUserByEmail(anyString())).thenReturn(new UserDto());
        userValidator.validate(userDto, errors);
        assertTrue(errors.hasErrors());
        assertNotNull(errors.getFieldError("email"));
    }

    @Test
    public void validateUserPasswordEmpty() {
        userDto.setPassword(EMPTY_STRING);
        userValidator.validate(userDto, errors);
        assertTrue(errors.hasErrors());
        assertNotNull(errors.getFieldError("password"));
    }

    @Test
    public void validateUserPasswordShort() {
        userDto.setPassword(STRING_SHORT);
        userValidator.validate(userDto, errors);
        assertTrue(errors.hasErrors());
        assertNotNull(errors.getFieldError("password"));
    }

    @Test
    public void validateUserPasswordLong() {
        userDto.setPassword(STRING_LONG);
        userValidator.validate(userDto, errors);
        assertTrue(errors.hasErrors());
        assertNotNull(errors.getFieldError("password"));
    }

    @Test
    public void validateUserPasswordConfirmEmpty() {
        userDto.setPasswordConfirm(EMPTY_STRING);
        userValidator.validate(userDto, errors);
        assertTrue(errors.hasErrors());
        assertNotNull(errors.getFieldError("passwordConfirm"));
    }

    @Test
    public void validateUserPasswordDifferent() {
        userDto.setPassword(USERNAME_VALID);
        userValidator.validate(userDto, errors);
        assertTrue(errors.hasErrors());
        assertNotNull(errors.getFieldError("passwordConfirm"));
    }

    @Test
    public void validateUserPasswordConfirmDifferent() {
        userDto.setPasswordConfirm(USERNAME_VALID);
        userValidator.validate(userDto, errors);
        assertTrue(errors.hasErrors());
        assertNotNull(errors.getFieldError("passwordConfirm"));
    }
}