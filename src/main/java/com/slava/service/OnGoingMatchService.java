package com.slava.service;

import com.slava.dto.MatchDTO;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;


public class OnGoingMatchService {

    private static OnGoingMatchService INSTANCE;

    private OnGoingMatchService(){};
    private  Map<UUID, MatchDTO> matchs = new HashMap<>();
    public UUID saveMatch(MatchDTO matchDTO) {
        UUID uuid = UUID.randomUUID();
        matchs.put(uuid, matchDTO);
        return uuid;
    }

    public Optional<MatchDTO> getMatch(String uuid) {
            return Optional.ofNullable(matchs.get(uuid));
    }

    public void updateMatch(String uuid, MatchDTO matchDTO) {
        UUID uuid1 = UUID.fromString(uuid);
        if(matchs.containsKey(uuid)) {
            matchs.put(uuid1, matchDTO);
        }
    }

    public void deleteMatch(UUID uuid) {
        matchs.remove(uuid);
    }

    public synchronized static OnGoingMatchService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new OnGoingMatchService();
        }
        return INSTANCE;
    }
}
