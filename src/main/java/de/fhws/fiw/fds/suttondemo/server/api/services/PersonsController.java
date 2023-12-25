package de.fhws.fiw.fds.suttondemo.server.api.services;

import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.ServletRequestAdapter.SpringServletRequest;
import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.requestAdapter.SpringRequest;
import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.responseAdapter.SpringResponse;
import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.uriInfoAdapter.SpringUriInfoAdapter;
import de.fhws.fiw.fds.sutton.server.api.services.AbstractSpringService;
import de.fhws.fiw.fds.suttondemo.server.api.models.Location;
import de.fhws.fiw.fds.suttondemo.server.api.models.Person;
import de.fhws.fiw.fds.suttondemo.server.api.queries.QueryByFirstAndLastName;
import de.fhws.fiw.fds.suttondemo.server.api.queries.QueryByLocationName;
import de.fhws.fiw.fds.suttondemo.server.api.states.person_locations.*;
import de.fhws.fiw.fds.suttondemo.server.api.states.persons.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("persons")
public class PersonsController extends AbstractSpringService {

    @Autowired
    public PersonsController(HttpServletRequest httpServletRequest) {
        super(httpServletRequest);
    }

    @GetMapping(value = "", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Collection<Person>> getAllPersons(
            @RequestParam(name = "firstname", defaultValue = "") final String firstName,
            @RequestParam(name = "lastname", defaultValue = "") final String lastName,
            @RequestParam(name = "offset", defaultValue = "0") final int offset,
            @RequestParam(name = "size", defaultValue = "20") final int size,
            @RequestParam(name = "wait", defaultValue = "0") final int waitingTime
    ) {
        return new GetAllPersons.Builder<ResponseEntity<Collection<Person>>>()
                .setQuery(new QueryByFirstAndLastName<>(firstName, lastName, offset, size, waitingTime))
                .setSuttonServletRequest(new SpringServletRequest(httpServletRequest))
                .setUriInfo(new SpringUriInfoAdapter(this.httpServletRequest))
                .setSuttonRequest(new SpringRequest(httpServletRequest))
                .setSuttonResponse(new SpringResponse<>())
                .build()
                .execute();
    }

    @GetMapping(value = "/{personId}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Person> getPersonById(
            @PathVariable final long personId
    ) {
        return new GetSinglePerson.Builder<ResponseEntity<Person>>()
                .setRequestedId(personId)
                .setUriInfo(new SpringUriInfoAdapter(this.httpServletRequest))
                .setSuttonRequest(new SpringRequest(httpServletRequest))
                .setSuttonServletRequest(new SpringServletRequest(httpServletRequest))
                .setSuttonResponse(new SpringResponse<>())
                .build()
                .execute();
    }

    @PostMapping(value = "", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Void> createSinglePerson(
            @RequestBody final Person personModel
    ) {
        return new PostNewPerson.Builder<ResponseEntity<Void>>()
                .setModelToCreate(personModel)
                .setUriInfo(new SpringUriInfoAdapter(this.httpServletRequest))
                .setSuttonRequest(new SpringRequest(httpServletRequest))
                .setSuttonServletRequest(new SpringServletRequest(httpServletRequest))
                .setSuttonResponse(new SpringResponse<>())
                .build()
                .execute();
    }

    @PutMapping(value = "/{personId}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Void> updateSinglePerson(
            @PathVariable final long personId,
            @RequestBody final Person personModel
    ) {
        return new PutSinglePerson.Builder<ResponseEntity<Void>>()
                .setRequestedId(personId)
                .setModelToUpdate(personModel)
                .setUriInfo(new SpringUriInfoAdapter(this.httpServletRequest))
                .setSuttonRequest(new SpringRequest(httpServletRequest))
                .setSuttonServletRequest(new SpringServletRequest(httpServletRequest))
                .setSuttonResponse(new SpringResponse<>())
                .build()
                .execute();
    }

    @DeleteMapping(value = "/{personId}")
    public ResponseEntity<?> deleteSinglePerson(
            @PathVariable final long personId
    ) {
        return new DeleteSinglePerson.Builder<ResponseEntity<Void>>()
                .setRequestedId(personId)
                .setUriInfo(new SpringUriInfoAdapter(this.httpServletRequest))
                .setSuttonRequest(new SpringRequest(httpServletRequest))
                .setSuttonServletRequest(new SpringServletRequest(httpServletRequest))
                .setSuttonResponse(new SpringResponse<>())
                .build()
                .execute();
    }

    @GetMapping(value = "/{personId}/locations",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Collection<Location>> getLocationsOfPerson(
            @PathVariable final long personId,
            @RequestParam(name = "cityname", defaultValue = "") final String cityName,
            @RequestParam(name = "offset", defaultValue = "0") int offset,
            @RequestParam(name = "size", defaultValue = "20") int size,
            @RequestParam(name = "wait", defaultValue = "0") int waitingTime) {
        return new GetAllLocationsOfPerson.Builder<ResponseEntity<Collection<Location>>>()
                .setParentId(personId)
                .setQuery(new QueryByLocationName<>(personId, cityName, offset, size, waitingTime))
                .setUriInfo(new SpringUriInfoAdapter(this.httpServletRequest))
                .setSuttonRequest(new SpringRequest(httpServletRequest))
                .setSuttonServletRequest(new SpringServletRequest(httpServletRequest))
                .setSuttonResponse(new SpringResponse<>())
                .build()
                .execute();
    }

    @GetMapping(value = "{personId}/locations/{locationId}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Location> getLocationByIdOfPerson(
            @PathVariable final long personId,
            @PathVariable final long locationId) {
        return new GetSingleLocationOfPerson.Builder<ResponseEntity<Location>>()
                .setParentId(personId)
                .setRequestedId(locationId)
                .setUriInfo(new SpringUriInfoAdapter(this.httpServletRequest))
                .setSuttonRequest(new SpringRequest(httpServletRequest))
                .setSuttonServletRequest(new SpringServletRequest(httpServletRequest))
                .setSuttonResponse(new SpringResponse<>())
                .build()
                .execute();
    }

    @PostMapping(value = "/{personId}/locations", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Void> createNewLocationOfPerson(
            @PathVariable final long personId,
            @RequestBody final Location location) {
        return new PostNewLocationOfPerson.Builder<ResponseEntity<Void>>()
                .setParentId(personId)
                .setModelToCreate(location)
                .setUriInfo(new SpringUriInfoAdapter(this.httpServletRequest))
                .setSuttonRequest(new SpringRequest(httpServletRequest))
                .setSuttonServletRequest(new SpringServletRequest(httpServletRequest))
                .setSuttonResponse(new SpringResponse<>())
                .build()
                .execute();
    }

    @PutMapping(value = "/{personId}/locations/{locationId}",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Void> updateNewLocationOfPerson(
            @PathVariable final long personId,
            @PathVariable final long locationId,
            @RequestBody final Location location) {
        return new PutSingleLocationOfPerson.Builder<ResponseEntity<Void>>()
                .setParentId(personId)
                .setRequestedId(locationId)
                .setModelToUpdate(location)
                .setUriInfo(new SpringUriInfoAdapter(this.httpServletRequest))
                .setSuttonRequest(new SpringRequest(httpServletRequest))
                .setSuttonServletRequest(new SpringServletRequest(httpServletRequest))
                .setSuttonResponse(new SpringResponse<>())
                .build()
                .execute();
    }

    @DeleteMapping(value = "{personId}/locations/{locationId}")
    public ResponseEntity<Void> deleteLocationOfPerson(
            @PathVariable final long personId,
            @PathVariable final long locationId) {
        return new DeleteSingleLocationOfPerson.Builder<ResponseEntity<Void>>()
                .setParentId(personId)
                .setRequestedId(locationId)
                .setUriInfo(new SpringUriInfoAdapter(this.httpServletRequest))
                .setSuttonRequest(new SpringRequest(httpServletRequest))
                .setSuttonServletRequest(new SpringServletRequest(httpServletRequest))
                .setSuttonResponse(new SpringResponse<>())
                .build()
                .execute();
    }
}
