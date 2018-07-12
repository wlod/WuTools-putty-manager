package net.wlodi.tools.putty.service;


import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.wlodi.tools.putty.repository.PuttySessionWindowsRegistryRepository;
import net.wlodi.tools.putty.repository.conf.AppConf;
import net.wlodi.tools.putty.repository.dto.PuttySessionEntryDTO;
import net.wlodi.tools.putty.service.dto.PuttySessionEntryDiffDTO;


public class PuttySessionService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger( PuttySessionService.class );
    
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

    public void loadWindowsExportedRegistryFile( File exportedRegistryFile ) {
        LOGGER.info( "Try load registry data from exported file: {}.", exportedRegistryFile);
        try (Stream<String> stream = Files.lines(Paths.get(exportedRegistryFile.getPath()), StandardCharsets.UTF_16LE)) {

            stream.
                filter(s -> s.matches(AppConf.REGISTRY_EXPORTED_FILE_KEY_TYPE_VALUE_PATTERN)).
                map(registryLine -> PuttySessionEntryDTO.createFromFileRawLine(registryLine)).
                // collect(Collectors.toList()));
                forEach(System.out::println);

        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

}
