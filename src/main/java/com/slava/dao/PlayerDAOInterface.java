package com.slava.dao;

import com.slava.entity.Player;

public interface PlayerDAOInterface extends CrudRepository<Player, Long> {
     Player findByName(String name);
}
