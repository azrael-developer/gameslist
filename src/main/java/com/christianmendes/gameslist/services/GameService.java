package com.christianmendes.gameslist.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.christianmendes.gameslist.dto.GameDTO;
import com.christianmendes.gameslist.dto.GameMinDTO;
import com.christianmendes.gameslist.entities.Games;
import com.christianmendes.gameslist.projections.GameMinProjection;
import com.christianmendes.gameslist.repositories.GameRepository;

@Service
public class GameService {

    @Autowired  
    private GameRepository gameRepository;

    @Transactional(readOnly = true)
    public GameDTO findById(Long id) {
        Games result = gameRepository.findById(id).get();
        return new GameDTO(result);
    }

    @Transactional(readOnly = true)
    public List<GameMinDTO> findAll() {
            List<Games> result = gameRepository.findAll();
            List<GameMinDTO> dto = result.stream().map(x -> new GameMinDTO(x)).toList();
            return dto;
    }

    @Transactional(readOnly = true)
    public List<GameMinDTO> findByList(Long listId) {
            List<GameMinProjection> result = gameRepository.searchByList(listId);
            List<GameMinDTO> dto = result.stream().map(x -> new GameMinDTO(x)).toList();
            return dto;
    }

}
