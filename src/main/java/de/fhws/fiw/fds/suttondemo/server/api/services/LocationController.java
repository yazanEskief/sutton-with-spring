package de.fhws.fiw.fds.suttondemo.server.api.services;

import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.ServletRequestAdapter.SpringServletRequest;
import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.requestAdapter.SpringRequest;
import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.responseAdapter.SpringResponse;
import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.uriInfoAdapter.SpringUriInfoAdapter;
import de.fhws.fiw.fds.sutton.server.api.services.AbstractSpringService;
import de.fhws.fiw.fds.suttondemo.server.api.models.Location;
import de.fhws.fiw.fds.suttondemo.server.api.states.locations.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("locations")
public class LocationController extends AbstractSpringService {

    public LocationController(HttpServletRequest httpServletRequest) {
        super(httpServletRequest);
    }

    @GetMapping(value = "", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Collection<Location>> getAllLocations() {
        return new GetAllLocations.Builder<ResponseEntity<Collection<Location>>>()
                .setQuery(new GetAllLocations.AllLocations<>())
                .setUriInfo(new SpringUriInfoAdapter(this.httpServletRequest))
                .setSuttonRequest(new SpringRequest(httpServletRequest))
                .setSuttonServletRequest(new SpringServletRequest(httpServletRequest))
                .setSuttonResponse(new SpringResponse<>())
                .build()
                .execute();
    }

    @GetMapping(value = "/{locationId}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Location> getSingleLocation(@PathVariable final long locationId) {
        return new GetSingleLocation.Builder<ResponseEntity<Location>>()
                .setRequestedId(locationId)
                .setUriInfo(new SpringUriInfoAdapter(this.httpServletRequest))
                .setSuttonRequest(new SpringRequest(httpServletRequest))
                .setSuttonServletRequest(new SpringServletRequest(httpServletRequest))
                .setSuttonResponse(new SpringResponse<>())
                .build()
                .execute();
    }

    @PostMapping(value = "", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Void> createSingleLocation(@RequestBody final Location locationModel) {
        return new PostNewLocation.Builder<ResponseEntity<Void>>()
                .setModelToCreate(locationModel)
                .setUriInfo(new SpringUriInfoAdapter(this.httpServletRequest))
                .setSuttonRequest(new SpringRequest(httpServletRequest))
                .setSuttonServletRequest(new SpringServletRequest(httpServletRequest))
                .setSuttonResponse(new SpringResponse<>())
                .build()
                .execute();
    }

    @PutMapping(value = "/{locationId}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Void> updateSingleLocation(
            @PathVariable final long locationId,
            @RequestBody final Location locationModel
    ) {
        return new PutSingleLocation.Builder<ResponseEntity<Void>>()
                .setRequestedId(locationId)
                .setModelToUpdate(locationModel)
                .setUriInfo(new SpringUriInfoAdapter(this.httpServletRequest))
                .setSuttonRequest(new SpringRequest(httpServletRequest))
                .setSuttonServletRequest(new SpringServletRequest(httpServletRequest))
                .setSuttonResponse(new SpringResponse<>())
                .build()
                .execute();
    }

    @DeleteMapping(value = "{locationId}")
    public ResponseEntity<Void> deleteSingleLocation(@PathVariable final long locationId) {
        return new DeleteSingleLocation.Builder<ResponseEntity<Void>>()
                .setRequestedId(locationId)
                .setUriInfo(new SpringUriInfoAdapter(this.httpServletRequest))
                .setSuttonRequest(new SpringRequest(httpServletRequest))
                .setSuttonServletRequest(new SpringServletRequest(httpServletRequest))
                .setSuttonResponse(new SpringResponse<>())
                .build()
                .execute();
    }
}
