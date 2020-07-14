package org.java.poker.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.java.poker.web.rest.TestUtil;

public class DealWinnerTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DealWinner.class);
        DealWinner dealWinner1 = new DealWinner();
        dealWinner1.setId(1L);
        DealWinner dealWinner2 = new DealWinner();
        dealWinner2.setId(dealWinner1.getId());
        assertThat(dealWinner1).isEqualTo(dealWinner2);
        dealWinner2.setId(2L);
        assertThat(dealWinner1).isNotEqualTo(dealWinner2);
        dealWinner1.setId(null);
        assertThat(dealWinner1).isNotEqualTo(dealWinner2);
    }
}
