package com.incloud.hcp.rest;


import com.incloud.hcp.jco.maestro.dto.EstructuraInformacionDto;
import com.incloud.hcp.jco.maestro.dto.MensajeDto;
import com.incloud.hcp.jco.maestro.service.JCOEstructuraInformacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
@RequestMapping(value = "/api/EstructuraInformacion")
public class EstructuraInformacionRest {

    @Autowired
    private JCOEstructuraInformacionService jcoEstructuraInformacionService;

    @PostMapping(value = "/Editar_Nuevo/", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<MensajeDto> EditarEstructuraInf(@RequestBody EstructuraInformacionDto imports){

        try {
            return Optional.ofNullable(this.jcoEstructuraInformacionService.EditarEstructuraInf(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            //String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(e.toString());
        }

    }
}
