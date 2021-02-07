package services;

import org.junit.jupiter.api.Test;

import java.util.List;

class CardIdServiceTest {

    @Test
    void shouldReturnListType() {
        assert(CardIdService.generateAlphanumericList() instanceof List);
    }
}