package de.fhws.fiw.fds.suttondemo.server.api.services;

import de.fhws.fiw.fds.sutton.server.api.security.User;
import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.ServletRequestAdapter.SpringServletRequest;
import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.requestAdapter.SpringRequest;
import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.responseAdapter.SpringResponse;
import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.uriInfoAdapter.SpringUriInfoAdapter;
import de.fhws.fiw.fds.sutton.server.api.services.AbstractSpringService;
import de.fhws.fiw.fds.suttondemo.server.api.states.users.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.Collection;

@RestController
@RequestMapping("users")
public class UserSpringService extends AbstractSpringService {

    public UserSpringService(HttpServletRequest httpServletRequest) {
        super(httpServletRequest);
    }

    @GetMapping(value = "", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Collection<User>> getAllUsers(WebRequest request) {
        return new GetAllUsers.Builder<ResponseEntity<Collection<User>>>()
                .setQuery(new GetAllUsers.AllUsers<>())
                .setSuttonRequest(new SpringRequest(request))
                .setSuttonResponse(new SpringResponse<>())
                .setUriInfo(new SpringUriInfoAdapter(this.httpServletRequest))
                .setSuttonServletRequest(new SpringServletRequest(this.httpServletRequest))
                .build()
                .execute();
    }

    @GetMapping(value = "/{userId}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<User> getUserById(@PathVariable final long userId, WebRequest request) {
        return new GetUserById.Builder<ResponseEntity<User>>()
                .setRequestedId(userId)
                .setSuttonRequest(new SpringRequest(request))
                .setSuttonResponse(new SpringResponse<>())
                .setUriInfo(new SpringUriInfoAdapter(this.httpServletRequest))
                .setSuttonServletRequest(new SpringServletRequest(this.httpServletRequest))
                .build()
                .execute();
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Void> postNewUser(@RequestBody final User newUser, WebRequest request) {
        return new PostNewUser.Builder<ResponseEntity<Void>>()
                .setModelToCreate(newUser)
                .setSuttonRequest(new SpringRequest(request))
                .setSuttonResponse(new SpringResponse<>())
                .setUriInfo(new SpringUriInfoAdapter(this.httpServletRequest))
                .setSuttonServletRequest(new SpringServletRequest(this.httpServletRequest))
                .build()
                .execute();
    }

    @PutMapping(value = "/{userId}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Void> putUser(@PathVariable final long userId,
                                        @RequestBody final User updatedUser,
                                        WebRequest request) {
        return new PutUser.Builder<ResponseEntity<Void>>()
                .setRequestedId(userId)
                .setModelToUpdate(updatedUser)
                .setSuttonRequest(new SpringRequest(request))
                .setSuttonResponse(new SpringResponse<>())
                .setUriInfo(new SpringUriInfoAdapter(this.httpServletRequest))
                .setSuttonServletRequest(new SpringServletRequest(this.httpServletRequest))
                .build()
                .execute();
    }

    @DeleteMapping(value = "/{userId}")
    public ResponseEntity<Void> deleteUserById(@PathVariable final long userId, WebRequest request) {
        return new DeleteUser.Builder<ResponseEntity<Void>>()
                .setRequestedId(userId)
                .setSuttonRequest(new SpringRequest(request))
                .setSuttonResponse(new SpringResponse<>())
                .setUriInfo(new SpringUriInfoAdapter(this.httpServletRequest))
                .setSuttonServletRequest(new SpringServletRequest(this.httpServletRequest))
                .build()
                .execute();
    }
}
