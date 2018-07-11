package net.wlodi.tools.putty.repository;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.wlodi.tools.putty.repository.conf.AppConf;
import net.wlodi.tools.putty.repository.dto.PuttySessionEntryDTO;

/**
 * 
 * Based on {@link https://stackoverflow.com/questions/62289/read-write-to-windows-registry-using-java}
 * 
 * 
 * @author wlodi
 *
 */

public class PuttySessionWindowsRegistryRepository {
    
    private static final Logger LOGGER = LoggerFactory.getLogger( PuttySessionWindowsRegistryRepository.class );
    
    private List<String> SESSIONS_NAME_CACHE = null;
    
    private Map<String, List<PuttySessionEntryDTO>> SESSION_NAME_TYPE_VALUE_CACHE = new HashMap<>();
    
    private static PuttySessionWindowsRegistryRepository inst;
    
    public static PuttySessionWindowsRegistryRepository inst() {
        if(inst == null) {
            inst = new PuttySessionWindowsRegistryRepository();
        }
        return inst;
    }
    
    private PuttySessionWindowsRegistryRepository() {
        
    }
    
    public List<String> getSessionsName () throws IOException, InterruptedException {
        return getSessionsName(AppConf.REGISTRY_KEY);
    }
    
    public List<PuttySessionEntryDTO> getSessionConfiguration(String sessionName) throws IOException, InterruptedException {
        List<PuttySessionEntryDTO> sessionConfiguration = SESSION_NAME_TYPE_VALUE_CACHE.get( sessionName );
        if(sessionConfiguration == null) {
            
            String keyPath = AppConf.REGISTRY_KEY + "\\" + sessionName;
            
            sessionConfiguration = Collections.synchronizedList(
                    getRawSessions(keyPath).
                    stream().
                    filter( StringUtils::isNotBlank ).
                    skip( 1 ). // skip first line - is keyPath return by 'req query {keyPath}'.
                    peek(session -> getSessionLog(keyPath, session)).
                    map(session -> PuttySessionEntryDTO.createFromRepositoryRawLine(session)).
                    collect(Collectors.toList()));
            
            
            SESSION_NAME_TYPE_VALUE_CACHE.put(sessionName, sessionConfiguration);
        }
        return sessionConfiguration;
    }
    
    private List<String> getSessionsName( String keyPath ) throws IOException , InterruptedException {
        if(SESSIONS_NAME_CACHE == null) {
            SESSIONS_NAME_CACHE = Collections.synchronizedList(
                                    getRawSessions(keyPath).
                                        stream().
                                        filter( StringUtils::isNotBlank ).
                                        peek(session -> getSessionLog(keyPath, session)).
                                        map(session -> session.replaceAll( AppConf.REGISTRY_KEY_FOR_REPLACE,"" )).
                                        collect(Collectors.toList()));
        }
        return SESSIONS_NAME_CACHE;
    }

    private List<String> getRawSessions( String keyPath ) throws IOException , InterruptedException {
        Process keyReader = Runtime.getRuntime().exec( "reg query \"" + keyPath + "\"");

        BufferedReader outputReader;
        String readLine;
        List<String> sessions = new ArrayList<>();

        outputReader = new BufferedReader( new InputStreamReader( keyReader.getInputStream() ) );

        while ( (readLine = outputReader.readLine()) != null ) {
            sessions.add( readLine );
        }

        keyReader.waitFor();

        return sessions;
    }
    
    private void getSessionLog( String registryKey, String session ) {
        LOGGER.info("Registry key: {} - session: {}.", registryKey, session);
    }
//    
//    public static void main( String[] args ) {
//        
//        try {
//           // new PuttySessionsRepository().getSessionsName().forEach( entry -> System.out.println( entry ) );
//            
//            // new PuttySessionsRepository().getRawSessions(AppConf.REGISTRY_KEY + "\\" + "aktualizacja@10.48.192.77-hyper-jboss").forEach( entry -> System.out.println( entry ) ) ;
//            new PuttySessionRepository().getSessionConfiguration("aktualizacja@10.48.192.77-hyper-jboss").forEach( entry -> System.out.println( entry ) ) ;
//            
//        }
//        catch ( IOException | InterruptedException e ) {
//            e.printStackTrace();
//        }
//        
//    }
    
}
