package com.qikserve.supermarket.service;

import com.qikserve.supermarket.domain.User;
import com.qikserve.supermarket.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserService service;

    private final User userMock = new User("Jan");

    @Test
    void shouldReturnAUserIfItExists() {
        doReturn(Optional.of(userMock)).when(repository).findById(anyString());
        User user = service.find("Jan");
        assertNotNull(user);

        doReturn(Optional.empty()).when(repository).findById(anyString());
        user = service.find("Jan");
        assertNull(user);
    }

    @Test
    void shouldReturnTheInformationWhetherOrNotTheUserExistsOrNot() {
        doReturn(Optional.of(userMock)).when(repository).findById(anyString());
        assertTrue(service.exists("Jan"));

        doReturn(Optional.empty()).when(repository).findById(anyString());
        assertFalse(service.exists("Jan"));
    }

    @Test
    void shouldCreateAnUser() {
        doReturn(userMock).when(repository).save(any(User.class));

        User user = service.create("Jan");
        assertEquals(user.getUsername(), "Jan");
        assertNotNull(user.getCreatedAt());
    }
}