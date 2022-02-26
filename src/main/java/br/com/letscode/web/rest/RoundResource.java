package br.com.letscode.web.rest;

import br.com.letscode.domain.Play;
import br.com.letscode.domain.Round;
import br.com.letscode.repository.RoundRepository;
import br.com.letscode.service.RoundService;
import br.com.letscode.service.dto.RoundValidDTO;
import br.com.letscode.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.jhipster.web.util.HeaderUtil;

/**
 * REST controller for managing {@link br.com.letscode.domain.Round}.
 */
@RestController
@RequestMapping("/api")
public class RoundResource {

    private final Logger log = LoggerFactory.getLogger(RoundResource.class);

    private static final String ENTITY_NAME = "round";

    private String applicationName =  "moviesbattleApp";

    private final RoundService roundService;

    private final RoundRepository roundRepository;

    public RoundResource(RoundService roundService, RoundRepository roundRepository) {
        this.roundService = roundService;
        this.roundRepository = roundRepository;
    }

    @PostMapping("/rounds/{idPlay}")
    public ResponseEntity<Round> newRound(@PathVariable(value = "idPlay", required = false) final String idPlay) throws URISyntaxException {
        Round result = roundService.newRound(new Play(idPlay));

        return ResponseEntity
            .created(new URI("/api/rounds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/rounds")
    public ResponseEntity<Round> validMovies(@Valid @RequestBody RoundValidDTO round) throws URISyntaxException {
        log.debug("REST request to update Round : {}, {}", round.getMovieId(), round);

        if (round.getRoundId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }

        if (!roundRepository.existsById(round.getRoundId())) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Round result = roundService.validMovies(round);

        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }
}
