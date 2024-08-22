package com.slava.util;


import com.slava.dto.MatchDTO;
import com.slava.dto.PlayerDTO;
import com.slava.entity.Match;
import com.slava.entity.Player;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;

public class MapperUtil {

    private static final ModelMapper modelMapper = new ModelMapper();

    static {
        // Настройка, чтобы ModelMapper знал, как переводить Match в MatchDTO
        modelMapper.addMappings(new PropertyMap<Match, MatchDTO>() {
            @Override
            protected void configure() {
                map(source.getPlayer1(), destination.getPlayer1());
                map(source.getPlayer2(), destination.getPlayer2());
                map(source.getWinner(), destination.getWinner());
            }
        });

        // Также можно настроить конвертацию Player -> PlayerDTO
        modelMapper.addMappings(new PropertyMap<Player, PlayerDTO>() {
            @Override
            protected void configure() {
                map().setName(source.getName());
            }
        });
    }

    public static ModelMapper getModelMapper() {
        return modelMapper;
    }
}
