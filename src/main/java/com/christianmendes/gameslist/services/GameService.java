package com.christianmendes.gameslist.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.christianmendes.gameslist.dto.GameMinDTO;
import com.christianmendes.gameslist.entities.Games;
import com.christianmendes.gameslist.repositories.GameRepository;

@Service
public class GameService {

    @Autowired  
    private GameRepository gameRepository;

    public List<GameMinDTO> findAll() {
            List<Games> result = gameRepository.findAll();
            List<GameMinDTO> dto = result.stream().map(x -> new GameMinDTO(x)).toList();
            return dto;
    }

}
