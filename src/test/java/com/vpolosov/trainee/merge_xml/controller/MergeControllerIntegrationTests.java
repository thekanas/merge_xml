package com.vpolosov.trainee.merge_xml.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@ActiveProfiles("test")
@DisplayName("Тестирование контроллера MergeController")
public class MergeControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    private static Path getFixturesPath() {
        return Paths.get("src", "test", "java",
                "com", "vpolosov", "trainee", "merge_xml", "test_fixtures", "sourceXml", "InvalidCurrCode")
            .toAbsolutePath().normalize();
    }

    @DisplayName("Тест контроллера patchXml() когда переданы файлы с невалидным кодом валюты")
    @Test
    void patchXml_whenInvalidCurrCode_thenReturnInvalidCurrencyCodeValueException() throws Exception {
        String path = getFixturesPath().toString();

        MockHttpServletResponse responsePost = mockMvc
            .perform(
                post("/xml")
                    .contentType(MediaType.TEXT_PLAIN)
                    .content(path)
            )
            .andReturn()
            .getResponse();

        assertThat(responsePost.getStatus()).isEqualTo(400);
        responsePost.setCharacterEncoding("UTF-8");
        assertThat(responsePost.getContentAsString()).contains("Допустимое значение кода валюты 810");
    }
}
