package br.com.letscode.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.letscode.IntegrationTest;
import br.com.letscode.domain.Play;
import br.com.letscode.domain.Player;
import br.com.letscode.repository.PlayRepository;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PlayResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PlayResourceIT {

    private static final Double DEFAULT_SCORE = 1.0;
    private static final Double UPDATED_SCORE = 1.0;

    private static final Instant DEFAULT_START = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_ERRORS = 1;
    private static final Integer UPDATED_ERRORS = 2;

    private static final String ENTITY_API_URL = "/api/plays";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PlayRepository playRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPlayMockMvc;

    private Play play;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Play createEntity(EntityManager em) {
        Play play = new Play().score(DEFAULT_SCORE).start(DEFAULT_START).end(DEFAULT_END).errors(DEFAULT_ERRORS);
        // Add required entity
        Player player;
        if (TestUtil.findAll(em, Player.class).isEmpty()) {
            player = PlayerResourceIT.createEntity(em);
            em.persist(player);
            em.flush();
        } else {
            player = TestUtil.findAll(em, Player.class).get(0);
        }
        play.setPlayer(player);
        return play;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Play createUpdatedEntity(EntityManager em) {
        Play play = new Play().score(UPDATED_SCORE).start(UPDATED_START).end(UPDATED_END).errors(UPDATED_ERRORS);

        return play;
    }

    @BeforeEach
    public void initTest() {
        play = createEntity(em);
    }

    @Test
    @Transactional
    void startPlay() throws Exception {
        int databaseSizeBeforeCreate = playRepository.findAll().size();
        // Create the Play
        restPlayMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(play)))
            .andExpect(status().isCreated());

        List<Play> playList = playRepository.findAll();
        assertThat(playList).hasSize(databaseSizeBeforeCreate + 1);
        Play testPlay = playList.get(playList.size() - 1);
        assertThat(testPlay.getScore()).isEqualTo(DEFAULT_SCORE);
        assertThat(testPlay.getStart()).isEqualTo(DEFAULT_START);
        assertThat(testPlay.getEnd()).isEqualTo(DEFAULT_END);
        assertThat(testPlay.getErrors()).isEqualTo(DEFAULT_ERRORS);
    }

    @Test
    @Transactional
    void createPlayWithExistingId() throws Exception {
        // Create the Play with an existing ID
        play.setId(
            "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGgiOiJST0xFX0FETUlOLFJPTEVfVVNFUiIsImV4cCI6MTY0NTY4MTMwNn0.B0dqVTD53wa4qjDoZwc1SQK4ndrmDMaS7nGOl-dzTLD6sbxC9BAF3X-0_2O_pcsY_jqZwDKKkedUBL7shEC9hA"
        );

        int databaseSizeBeforeCreate = playRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlayMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(play)))
            .andExpect(status().isBadRequest());

        // Validate the Play in the database
        List<Play> playList = playRepository.findAll();
        assertThat(playList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPlays() throws Exception {
        // Initialize the database
        playRepository.saveAndFlush(play);

        // Get all the playList
        restPlayMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(play.getId())))
            .andExpect(jsonPath("$.[*].score").value(hasItem(DEFAULT_SCORE)))
            .andExpect(jsonPath("$.[*].start").value(hasItem(DEFAULT_START.toString())))
            .andExpect(jsonPath("$.[*].end").value(hasItem(DEFAULT_END.toString())))
            .andExpect(jsonPath("$.[*].errors").value(hasItem(DEFAULT_ERRORS)));
    }

    @Test
    @Transactional
    void getPlay() throws Exception {
        // Initialize the database
        playRepository.saveAndFlush(play);

        // Get the play
        restPlayMockMvc
            .perform(get(ENTITY_API_URL_ID, play.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(play.getId()))
            .andExpect(jsonPath("$.score").value(DEFAULT_SCORE))
            .andExpect(jsonPath("$.start").value(DEFAULT_START.toString()))
            .andExpect(jsonPath("$.end").value(DEFAULT_END.toString()))
            .andExpect(jsonPath("$.errors").value(DEFAULT_ERRORS));
    }

    @Test
    @Transactional
    void getNonExistingPlay() throws Exception {
        // Get the play
        restPlayMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPlay() throws Exception {
        // Initialize the database
        playRepository.saveAndFlush(play);

        int databaseSizeBeforeUpdate = playRepository.findAll().size();

        // Update the play
        Play updatedPlay = playRepository.findById(play.getId()).get();
        // Disconnect from session so that the updates on updatedPlay are not directly saved in db
        em.detach(updatedPlay);
        updatedPlay.score(UPDATED_SCORE).start(UPDATED_START).end(UPDATED_END).errors(UPDATED_ERRORS);

        restPlayMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPlay.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPlay))
            )
            .andExpect(status().isOk());

        // Validate the Play in the database
        List<Play> playList = playRepository.findAll();
        assertThat(playList).hasSize(databaseSizeBeforeUpdate);
        Play testPlay = playList.get(playList.size() - 1);
        assertThat(testPlay.getScore()).isEqualTo(UPDATED_SCORE);
        assertThat(testPlay.getStart()).isEqualTo(UPDATED_START);
        assertThat(testPlay.getEnd()).isEqualTo(UPDATED_END);
        assertThat(testPlay.getErrors()).isEqualTo(UPDATED_ERRORS);
    }

    @Test
    @Transactional
    void putNonExistingPlay() throws Exception {
        int databaseSizeBeforeUpdate = playRepository.findAll().size();
        play.setId(
            "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGgiOiJST0xFX0FETUlOLFJPTEVfVVNFUiIsImV4cCI6MTY0NTY4MTMwNn0.B0dqVTD53wa4qjDoZwc1SQK4ndrmDMaS7nGOl-dzTLD6sbxC9BAF3X-0_2O_pcsY_jqZwDKKkedUBL7shEC9hA"
        );

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlayMockMvc
            .perform(
                put(ENTITY_API_URL_ID, play.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(play))
            )
            .andExpect(status().isBadRequest());

        // Validate the Play in the database
        List<Play> playList = playRepository.findAll();
        assertThat(playList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPlay() throws Exception {
        int databaseSizeBeforeUpdate = playRepository.findAll().size();
        play.setId(
            "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGgiOiJST0xFX0FETUlOLFJPTEVfVVNFUiIsImV4cCI6MTY0NTY4MTMwNn0.B0dqVTD53wa4qjDoZwc1SQK4ndrmDMaS7nGOl-dzTLD6sbxC9BAF3X-0_2O_pcsY_jqZwDKKkedUBL7shEC9hA"
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlayMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(play))
            )
            .andExpect(status().isBadRequest());

        // Validate the Play in the database
        List<Play> playList = playRepository.findAll();
        assertThat(playList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPlay() throws Exception {
        int databaseSizeBeforeUpdate = playRepository.findAll().size();
        play.setId(
            "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGgiOiJST0xFX0FETUlOLFJPTEVfVVNFUiIsImV4cCI6MTY0NTY4MTMwNn0.B0dqVTD53wa4qjDoZwc1SQK4ndrmDMaS7nGOl-dzTLD6sbxC9BAF3X-0_2O_pcsY_jqZwDKKkedUBL7shEC9hA"
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlayMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(play)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Play in the database
        List<Play> playList = playRepository.findAll();
        assertThat(playList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePlayWithPatch() throws Exception {
        // Initialize the database
        playRepository.saveAndFlush(play);

        int databaseSizeBeforeUpdate = playRepository.findAll().size();

        // Update the play using partial update
        Play partialUpdatedPlay = new Play();
        partialUpdatedPlay.setId(play.getId());

        partialUpdatedPlay.start(UPDATED_START);

        restPlayMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlay.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPlay))
            )
            .andExpect(status().isOk());

        // Validate the Play in the database
        List<Play> playList = playRepository.findAll();
        assertThat(playList).hasSize(databaseSizeBeforeUpdate);
        Play testPlay = playList.get(playList.size() - 1);
        assertThat(testPlay.getScore()).isEqualTo(DEFAULT_SCORE);
        assertThat(testPlay.getStart()).isEqualTo(UPDATED_START);
        assertThat(testPlay.getEnd()).isEqualTo(DEFAULT_END);
        assertThat(testPlay.getErrors()).isEqualTo(DEFAULT_ERRORS);
    }

    @Test
    @Transactional
    void fullUpdatePlayWithPatch() throws Exception {
        // Initialize the database
        playRepository.saveAndFlush(play);

        int databaseSizeBeforeUpdate = playRepository.findAll().size();

        // Update the play using partial update
        Play partialUpdatedPlay = new Play();
        partialUpdatedPlay.setId(play.getId());

        partialUpdatedPlay.score(UPDATED_SCORE).start(UPDATED_START).end(UPDATED_END).errors(UPDATED_ERRORS);

        restPlayMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlay.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPlay))
            )
            .andExpect(status().isOk());

        // Validate the Play in the database
        List<Play> playList = playRepository.findAll();
        assertThat(playList).hasSize(databaseSizeBeforeUpdate);
        Play testPlay = playList.get(playList.size() - 1);
        assertThat(testPlay.getScore()).isEqualTo(UPDATED_SCORE);
        assertThat(testPlay.getStart()).isEqualTo(UPDATED_START);
        assertThat(testPlay.getEnd()).isEqualTo(UPDATED_END);
        assertThat(testPlay.getErrors()).isEqualTo(UPDATED_ERRORS);
    }

    @Test
    @Transactional
    void patchNonExistingPlay() throws Exception {
        int databaseSizeBeforeUpdate = playRepository.findAll().size();
        play.setId(
            "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGgiOiJST0xFX0FETUlOLFJPTEVfVVNFUiIsImV4cCI6MTY0NTY4MTMwNn0.B0dqVTD53wa4qjDoZwc1SQK4ndrmDMaS7nGOl-dzTLD6sbxC9BAF3X-0_2O_pcsY_jqZwDKKkedUBL7shEC9hA"
        );

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlayMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, play.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(play))
            )
            .andExpect(status().isBadRequest());

        // Validate the Play in the database
        List<Play> playList = playRepository.findAll();
        assertThat(playList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPlay() throws Exception {
        int databaseSizeBeforeUpdate = playRepository.findAll().size();
        play.setId(
            "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGgiOiJST0xFX0FETUlOLFJPTEVfVVNFUiIsImV4cCI6MTY0NTY4MTMwNn0.B0dqVTD53wa4qjDoZwc1SQK4ndrmDMaS7nGOl-dzTLD6sbxC9BAF3X-0_2O_pcsY_jqZwDKKkedUBL7shEC9hA"
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlayMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(play))
            )
            .andExpect(status().isBadRequest());

        // Validate the Play in the database
        List<Play> playList = playRepository.findAll();
        assertThat(playList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPlay() throws Exception {
        int databaseSizeBeforeUpdate = playRepository.findAll().size();
        play.setId(
            "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGgiOiJST0xFX0FETUlOLFJPTEVfVVNFUiIsImV4cCI6MTY0NTY4MTMwNn0.B0dqVTD53wa4qjDoZwc1SQK4ndrmDMaS7nGOl-dzTLD6sbxC9BAF3X-0_2O_pcsY_jqZwDKKkedUBL7shEC9hA"
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlayMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(play)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Play in the database
        List<Play> playList = playRepository.findAll();
        assertThat(playList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePlay() throws Exception {
        // Initialize the database
        playRepository.saveAndFlush(play);

        int databaseSizeBeforeDelete = playRepository.findAll().size();

        // Delete the play
        restPlayMockMvc
            .perform(delete(ENTITY_API_URL_ID, play.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Play> playList = playRepository.findAll();
        assertThat(playList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
