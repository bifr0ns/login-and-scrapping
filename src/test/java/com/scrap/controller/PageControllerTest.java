package com.scrap.controller;

import com.scrap.model.entity.Url;
import com.scrap.model.request.RequestPage;
import com.scrap.service.IJwtService;
import com.scrap.service.IPageService;
import com.scrap.util.Constants;
import com.scrap.util.MockData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.concurrent.CompletableFuture;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class PageControllerTest {

  @Autowired
  private PageController pageController;

  @MockBean
  private IJwtService jwtService;

  @MockBean
  private IPageService pageService;

  private MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(pageController)
            .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
            .build();
  }

  @Test
  void testScrapPage() throws Exception {
    RequestPage requestPage = new RequestPage();

    when(jwtService.getUserIdByToken(MockData.TOKEN)).thenReturn("1");

    Url newPage = new Url();
    when(pageService.createNewPageAsync(1, requestPage.getUrl()))
            .thenReturn(CompletableFuture.completedFuture(newPage));
    when(pageService.getLinksFromUrlAsync(newPage, requestPage.getUrl()))
            .thenReturn(CompletableFuture.completedFuture(null));

    mockMvc.perform(MockMvcRequestBuilders
                    .post("/pages")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{}")
                    .header("token", MockData.TOKEN)
            )
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json(
                    "{\"msgResponse\":\"" + Constants.SUCCESS + "\"}")
            );
  }

  @Test
  void testGetPagesForUser() throws Exception {
    when(jwtService.getUserIdByToken(MockData.TOKEN)).thenReturn("1");

    when(pageService.getPagesForUser(any(), any())).thenReturn(MockData.PAGE);

    mockMvc.perform(MockMvcRequestBuilders
                    .get("/pages")
                    .header("token", MockData.TOKEN)
                    .param("page", "0")
                    .param("size", "10")
            )
            .andExpect(MockMvcResultMatchers.status().isOk());
  }

  @Test
  void testGetPageInfo() throws Exception {
    when(jwtService.getUserIdByToken(any())).thenReturn("1");
    when(pageService.getUserFromPageId(1)).thenReturn("1");
    when(pageService.getPageInfo(any(), any())).thenReturn(MockData.PAGE_INFO);

    mockMvc.perform(MockMvcRequestBuilders
                    .get("/pages/1")
                    .header("token", MockData.TOKEN)
                    .param("page", "0")
                    .param("size", "10")
            )
            .andExpect(MockMvcResultMatchers.status().isOk());
  }

  @Test
  void testGetPageInfoAndReturn404() throws Exception {
    when(jwtService.getUserIdByToken(any())).thenReturn("1");
    when(pageService.getUserFromPageId(1)).thenReturn("2");
    when(pageService.getPageInfo(any(), any())).thenReturn(MockData.PAGE_INFO);

    mockMvc.perform(MockMvcRequestBuilders
                    .get("/pages/1")
                    .header("token", MockData.TOKEN)
                    .param("page", "0")
                    .param("size", "10")
            )
            .andExpect(MockMvcResultMatchers.status().isNotFound());
  }
}
