package com.reference.api.controller;

import java.util.List;

import com.reference.api.models.Compartment;
import com.reference.api.repository.CompartmentRepository;
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
        Compartment savedCompartment =  compartmentRepository.save(compartment);

        return savedCompartment;
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
        Compartment savedCompartment =  compartmentRepository.save(tmpCompartment.setPhotoUrl(url));
        return savedCompartment;
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
        Compartment savedCompartment = compartmentRepository.save(compartment);
        return savedCompartment;
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