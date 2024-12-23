package com.frcalderon.inventory.domain.command;

import com.frcalderon.inventory.domain.model.Item;
import com.frcalderon.inventory.domain.model.Location;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
public class UpdateItemCommandTests {

    @ParameterizedTest
    @MethodSource("providerForNameHasInvalidLength")
    void shouldThrowExceptionWhenNameHasInvalidLength(String name) {
        assertThatThrownBy(
                () -> new UpdateItemCommand(name, 0, new Location(1L, "foo"))
        )
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Name is required and should contain between 0 and 50 characters");
    }

    private static Stream<String> providerForNameHasInvalidLength() {
        return Stream.of(
                null,
                "",
                " ",
                "x".repeat(Item.NAME_MAX_LENGTH + 1)
        );
    }

    @ParameterizedTest
    @MethodSource("providerForQuantityIsNegative")
    void shouldThrowExceptionWhenQuantityIsNegative(Integer quantity) {
        assertThatThrownBy(
                () -> new UpdateItemCommand("foo", quantity, new Location(1L, "foo"))
        )
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Quantity is required and cannot be negative");
    }

    private static Stream<Integer> providerForQuantityIsNegative() {
        return Stream.of(
                null,
                -1
        );
    }

    @Test
    void shouldThrowExceptionWhenLocationIsNull() {
        assertThatThrownBy(
                () -> new UpdateItemCommand("foo", 1, null)
        )
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Location is required");
    }
}
