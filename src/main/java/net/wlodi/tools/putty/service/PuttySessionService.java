package net.wlodi.tools.putty.service;


import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.wlodi.tools.putty.repository.JavaUtils;
import net.wlodi.tools.putty.repository.PuttySessionWindowsRegistryRepository;
import net.wlodi.tools.putty.repository.conf.AppConf;
import net.wlodi.tools.putty.repository.dto.PuttySessionEntryDTO;
import net.wlodi.tools.putty.repository.dto.RegistryType;
import net.wlodi.tools.putty.service.dto.PuttySessionEntryDiffDTO;


public class PuttySessionService {

    private static final Logger LOGGER = LoggerFactory.getLogger( PuttySessionService.class );

    private final Map<String , Integer> SESSIONS_NAME_DIFF_COUNT_CACHE = new HashMap<>();

    private final Map<String , List<PuttySessionEntryDiffDTO>> SESSIONS_NAME_ENTRIES_DIFF = new HashMap<>();

    private PuttySessionWindowsRegistryRepository puttySessionRepository = PuttySessionWindowsRegistryRepository.inst();

    private String puttySessionFileConfigurationHeader;

    private List<PuttySessionEntryDTO> puttySessionFileConfiguration;

    private static PuttySessionService inst = new PuttySessionService();

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
    public List<PuttySessionEntryDiffDTO> getPuttySessionEntryDiffList( String sessionName ) throws IOException , InterruptedException {
        List<PuttySessionEntryDTO> puttySessionEntries = puttySessionRepository.getSessionConfiguration( sessionName );
        List<PuttySessionEntryDiffDTO> puttySessionEntriesDiff = SESSIONS_NAME_ENTRIES_DIFF.get( sessionName );
        if (puttySessionEntriesDiff == null) {
            puttySessionEntriesDiff = puttySessionEntries
                    .stream()
                    .map( puttySessionEntry -> PuttySessionEntryDiffDTO.mapFrom( puttySessionEntry, getEntryFromFile( puttySessionEntry ) ) )
                    .peek( diff -> countDiffForSession( diff, sessionName ) )
                    .collect( Collectors.toList() );
            SESSIONS_NAME_ENTRIES_DIFF.put( sessionName, puttySessionEntriesDiff );
        }
        return puttySessionEntriesDiff;
    }

    /**
     * TODO Maybe it is not the best way - method: getPuttySessionEntryDiffList should invoked earlier.
     * 
     * @param sessionName
     * @return number of changes for session
     */
    public Integer getNumberOfChanges( String sessionName ) {
        return SESSIONS_NAME_DIFF_COUNT_CACHE.get( sessionName );
    }
    
    public void updateNumberOfChanges( String sessionName, int changes ) {
        Integer sessionDiffCounter = SESSIONS_NAME_DIFF_COUNT_CACHE.get( sessionName );
        if(sessionDiffCounter != null) {
            sessionDiffCounter += changes;
            SESSIONS_NAME_DIFF_COUNT_CACHE.put( sessionName, sessionDiffCounter );
        }
    }

    /**
     * 
     * @param diff
     * @param sessionName
     */
    private void countDiffForSession( PuttySessionEntryDiffDTO diff , String sessionName ) {
        if (PuttySessionEntryDiffDTO.NEW_VALUE_REGISTRY.equals( diff.getComment() ) == false) {
            return;
        }

        Integer sessionDiffCounter = SESSIONS_NAME_DIFF_COUNT_CACHE.get( sessionName );
        if (sessionDiffCounter == null) {
            SESSIONS_NAME_DIFF_COUNT_CACHE.put( sessionName, 1 );
        }
        else {
            SESSIONS_NAME_DIFF_COUNT_CACHE.put( sessionName, ++sessionDiffCounter );
        }
    }

    /**
     * 
     * @param exportedRegistryFile
     * @throws IOException
     */
    public void loadWindowsExportedRegistryFile( File exportedRegistryFile ) throws IOException {
        LOGGER.info( "Trying load registry data from exported file: {}.", exportedRegistryFile );
        try (Stream<String> stream = Files.lines( Paths.get( exportedRegistryFile.getPath() ), StandardCharsets.UTF_16LE )) {

            puttySessionFileConfiguration = stream
                    .peek( s -> registerHeader( s ) )
                    .filter( s -> s.matches( AppConf.REGISTRY_EXPORTED_FILE_KEY_TYPE_VALUE_PATTERN ) )
                    .map( registryLine -> PuttySessionEntryDTO.createFromFileRawLine( registryLine ) )
                    .collect( Collectors.toList() );

            loadDiffWithCurrentLoadedSessions();

        }
        catch ( IOException | InterruptedException e ) {
            LOGGER.info( "Can not read values from file: {}.", exportedRegistryFile );
            throw new IOException( e );
        }

    }

    private void loadDiffWithCurrentLoadedSessions( ) throws IOException , InterruptedException {
        SESSIONS_NAME_ENTRIES_DIFF.clear();
        SESSIONS_NAME_DIFF_COUNT_CACHE.clear();
        for ( String sessionName : puttySessionRepository.getSessionsName() ) {
            getPuttySessionEntryDiffList( sessionName );
        }
    }

    private void registerHeader( String rawUTF16LELine ) {
        if (StringUtils.isBlank( rawUTF16LELine ) || puttySessionFileConfigurationHeader != null) {
            return;
        }

        String rawUTF16LELineWithoutBOM = JavaUtils.removeUTF16_LE_BOM( rawUTF16LELine );
        if (rawUTF16LELineWithoutBOM.startsWith( AppConf.REGISTRY_FIRST_LINE_NAME_PATTERN )) {
            puttySessionFileConfigurationHeader = rawUTF16LELine;
        }
    }

    /**
     * 
     * TODO tears ;-(
     * 
     * @param selectedFile
     */
    public void createRegitryMergeFile( File selectedFile ) {
        LOGGER.info( "Try save registry merge file to path: {}.", selectedFile );
        try {

            ArrayList<String> mergeLinesContent = new ArrayList<>();
            int sessionHeaderNameCounter = 0;

            if (puttySessionFileConfigurationHeader != null) {
                mergeLinesContent.add( puttySessionFileConfigurationHeader );
                mergeLinesContent.add( "" );
                sessionHeaderNameCounter += 2;
            }

            mergeLinesContent.add( "[" + AppConf.REGISTRY_KEY + "]" );
            mergeLinesContent.add( "" );
            sessionHeaderNameCounter += 2;

            for ( String sessionName : puttySessionRepository.getSessionsName() ) {

                mergeLinesContent.add( "[" + AppConf.REGISTRY_KEY + "\\" + sessionName + "]" );

                for ( PuttySessionEntryDiffDTO puttySessionDiff : getPuttySessionEntryDiffList( sessionName ) ) {
                    if (puttySessionDiff.isAppend()) {

                        StringBuilder rawLine = new StringBuilder( "" );
                        rawLine.append( "\"" ).append( puttySessionDiff.getName() ).append( "\"" );
                        rawLine.append( "=" );

                        if (puttySessionDiff.getType().equals( RegistryType.REG_SZ.name() ) == false) {
                            String typeForFile = RegistryType.getTypeNameForFile( puttySessionDiff.getType() );
                            rawLine.append( typeForFile ).append( ":" ).append( puttySessionDiff.getNewValue() );
                        }
                        else {
                            rawLine.append( "\"" ).append( puttySessionDiff.getNewValue() ).append( "\"" );
                        }

                        mergeLinesContent.add( rawLine.toString() );
                    }
                }

                mergeLinesContent.add( "" );
                sessionHeaderNameCounter += 2;
            }

            if (sessionHeaderNameCounter == mergeLinesContent.size()) {
                LOGGER.info( "The new registry file doesn't affect to current configuration." );
                mergeLinesContent.clear();
            }

            Files.write( Paths.get( selectedFile.getPath() ),
                    mergeLinesContent,
                    StandardCharsets.UTF_16LE,
                    StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING );

            LOGGER.info( "Created new registry file {}.", selectedFile );
        }
        catch ( IOException | InterruptedException e ) {
            LOGGER.error( "Can not write values to file: {}.", selectedFile, e );
        }

    }

    /**
     * 
     * @param puttySessionEntry
     * @return
     */
    private Optional<PuttySessionEntryDTO> getEntryFromFile( PuttySessionEntryDTO puttySessionEntry ) {

        if (puttySessionFileConfiguration == null) {
            return Optional.empty();
        }

        return puttySessionFileConfiguration
                .stream()
                .filter( s -> s.getName().equals( puttySessionEntry.getName() ) )
                .filter( s -> s.getType().equals( puttySessionEntry.getType() ) )
                .findFirst();
    }

}
