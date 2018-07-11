package net.wlodi.tools.putty.service;


import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import net.wlodi.tools.putty.repository.PuttySessionWindowsRegistryRepository;
import net.wlodi.tools.putty.repository.dto.PuttySessionEntryDTO;
import net.wlodi.tools.putty.service.dto.PuttySessionEntryDiffDTO;


public class PuttySessionService {

    private PuttySessionWindowsRegistryRepository puttySessionRepository = PuttySessionWindowsRegistryRepository.inst();

    private static PuttySessionService inst;

    public static PuttySessionService inst( ) {
        if (inst == null) {
            inst = new PuttySessionService();
        }
        return inst;
    }

    private PuttySessionService() {

    }

    public List<PuttySessionEntryDiffDTO> getPuttySessionEntryDiffSList( String sessionName ) throws IOException , InterruptedException {

        List<PuttySessionEntryDTO> puttySessionEntries = puttySessionRepository.getSessionConfiguration( sessionName );

        // TODO nalozyc konfiguracje z pliku
        return puttySessionEntries.stream().map( PuttySessionEntryDiffDTO::mapFrom ).collect( Collectors.toList() );
    }

}
