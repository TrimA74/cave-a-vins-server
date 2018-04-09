package com.reference.api.controller;


import com.reference.api.models.Compartment;
import com.reference.api.models.User;
import com.reference.api.repository.CompartmentRepository;
import com.reference.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/compartment")
public class CompartmentController {
    @Autowired
    private CompartmentRepository compartmentRepository;
    @Autowired
    private UserRepository userRepository;

    /**
     * Create a new compartment
     *
     * @param compartment
     * @return savedCompartment
     */
    @RequestMapping(path = "/create",
            method = RequestMethod.POST,
            consumes =  MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Create a compartment")
    public Compartment add(@RequestBody Compartment compartment) {
        User u = userRepository.findOne(compartment.getOwner().getId());

        return compartmentRepository.save(new Compartment(compartment.getName(), u, compartment.getPhotoUrl()));
    }

    /**
     *
     * Upload an image for the compartment
     *
     * @param url
     * @return savedCompartment
     */
    @RequestMapping(path = "/{compartmentId}/uploadImage",
            method = RequestMethod.POST,
            consumes =  MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Upload an image for the compartment")
    public Compartment uploadImage(@RequestParam String url, @PathVariable("compartmentId") Long id) {
        Compartment tmpCompartment = compartmentRepository.findOne(id);
        return compartmentRepository.save(tmpCompartment.setPhotoUrl(url));
    }

    /***
     * Update a compartment
     */
    @RequestMapping(path="/update", method = RequestMethod.POST, produces = "application/json")
    @ApiOperation(value = "Update a compartment")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = Compartment.class),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Server Error")})
    public Compartment updateCompartment(@RequestBody Compartment compartment) {
        Compartment c = compartmentRepository.findOne(compartment.getId());

        c.id(compartment.getId());
        c.name(compartment.getName());
        c.setPhotoUrl(compartment.getPhotoUrl());

        return compartmentRepository.save(c);
    }

    /**
     * Deletes compartment identified with <code>id</code>
     * @param id
     */
    @RequestMapping(path = "/delete/{id}",
            method = RequestMethod.DELETE)
    @ApiOperation(value = "Delete a compartment")
    public void delete(@PathVariable Long id) {
        compartmentRepository.delete(id);
    }



}
