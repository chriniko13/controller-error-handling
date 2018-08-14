package com.chriniko.controllererrorhandling.resource;

import com.chriniko.controllererrorhandling.domain.Service;
import com.chriniko.controllererrorhandling.domain.Status;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/services")
public class ServiceResource {


    @RequestMapping(
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody
    HttpEntity<Status> save(@RequestBody Service service,
                            @RequestHeader(name = "sdk_api_v") String sdkApiVersion) {

        System.out.println("    >>> sdkApiVersions: " + sdkApiVersion);
        System.out.println("    >>> service: " + service);

        return ResponseEntity.ok(new Status("service saved successfully!"));
    }

}
