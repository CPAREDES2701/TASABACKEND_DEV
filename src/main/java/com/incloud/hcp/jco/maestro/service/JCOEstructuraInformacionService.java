package com.incloud.hcp.jco.maestro.service;

import com.incloud.hcp.jco.maestro.dto.EstructuraInformacionDto;
import com.incloud.hcp.jco.maestro.dto.MensajeDto;

public interface JCOEstructuraInformacionService {

    MensajeDto EditarEstructuraInf(EstructuraInformacionDto estructuraInformacionDto)throws Exception;
}
