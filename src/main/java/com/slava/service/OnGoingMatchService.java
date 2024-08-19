package com.slava.service;

import com.slava.dto.MatchDTO;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class OnGoingMatchService {

    private static OnGoingMatchService INSTANCE;

    private OnGoingMatchService(){};
    private volatile  Map<String, MatchDTO> matches = new HashMap<>();
    public synchronized String saveMatch(MatchDTO matchDTO) {
        UUID uuid = UUID.randomUUID();
        matches.put(uuid.toString(), matchDTO);
        return uuid.toString();
    }

    public synchronized Boolean isMatchExist(String uuid) {
        if (matches.containsKey(uuid)) {
            return  true;
        } else {
            return false;
        }
    }

    public  synchronized MatchDTO getMatch(String uuid) {
        return (matches.get(uuid));
    }

    public void updateMatch(String uuid, MatchDTO matchDTO) {

        if(matches.containsKey(uuid)) {
            matches.put(uuid, matchDTO);
        }
    }

    public synchronized void deleteMatch(String uuid) {
        matches.remove(uuid);
    }

    public synchronized static OnGoingMatchService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new OnGoingMatchService();
        }
        return INSTANCE;
    }
}
