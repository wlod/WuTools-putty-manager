package net.wlodi.tools.putty.service;


import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
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

    private List<PuttySessionEntryDTO> puttySessionFileConfiguration;

    private static PuttySessionService inst = new PuttySessionService();;

    public static PuttySessionService inst( ) {
        return inst;
    }

    private PuttySessionService() {

    }

    /**
     * 
     * TODO Currently: new values from file are skipped
     * 
     * @param sessionName
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public List<PuttySessionEntryDiffDTO> getPuttySessionEntryDiffSList( String sessionName ) throws IOException , InterruptedException {

        List<PuttySessionEntryDTO> puttySessionEntries = puttySessionRepository.getSessionConfiguration( sessionName );

        return puttySessionEntries
                .stream()
                .map( puttySessionEntry -> PuttySessionEntryDiffDTO.mapFrom( puttySessionEntry, getEntryFromFile( puttySessionEntry ) ) )
                .collect( Collectors.toList() );
    }
    
    /**
     * 
     * @param exportedRegistryFile
     * @throws IOException
     */
    public void loadWindowsExportedRegistryFile( File exportedRegistryFile ) throws IOException {
        LOGGER.info( "Try load registry data from exported file: {}.", exportedRegistryFile );
        try (Stream<String> stream = Files.lines( Paths.get( exportedRegistryFile.getPath() ), StandardCharsets.UTF_16LE )) {

            puttySessionFileConfiguration = stream
                    .filter( s -> s.matches( AppConf.REGISTRY_EXPORTED_FILE_KEY_TYPE_VALUE_PATTERN ) )
                    .map( registryLine -> PuttySessionEntryDTO.createFromFileRawLine( registryLine ) )
                    .collect( Collectors.toList() );

        }
        catch ( IOException e ) {
            LOGGER.info( "Can not read values from file: {}.", exportedRegistryFile );
            throw new IOException( e );
        }

    }

    /**
     * 
     * TODO Validation and add comment/information about entry.
     * 
     * 
     * @param puttySessionEntry
     * @return
     */
    private Optional<PuttySessionEntryDTO> getEntryFromFile( PuttySessionEntryDTO puttySessionEntry ) {
        
        if(puttySessionFileConfiguration == null) {
            return Optional.empty();
        }
        
        return puttySessionFileConfiguration
                .stream()
                .filter( s -> s.getName().equals( puttySessionEntry.getName() ) )
                .filter( s -> s.getType().equals( puttySessionEntry.getType() ) )
                .findFirst();
    }

}
