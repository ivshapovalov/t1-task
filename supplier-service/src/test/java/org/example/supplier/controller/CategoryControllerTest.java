package org.example.supplier.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.supplier.CommonTest;
import org.example.supplier.exceptions.CategoryNotFoundException;
import org.example.supplier.model.dto.request.CategoryRequest;
import org.example.supplier.model.dto.response.GenericResponse;
import org.example.supplier.model.dto.response.PagingInfo;
import org.example.supplier.model.entity.Category;
import org.example.supplier.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CategoryController.class)
@AutoConfigureMockMvc
public class CategoryControllerTest extends CommonTest {

    @Value("${supplier.page.default.number}")
    Integer pageDefaultNumber;
    @Value("${supplier.page.default.size}")
    Integer pageDefaultSize;
    @Value("${supplier.page.default.sort}")
    String[] pageDefaultSort;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private CategoryService categoryService;

    @Test
    public void addCategoryWhenOneItemInRequestReturnOk() throws Exception {
        String jsonRequest = """
                    [
                        {
                            "name":"Category 1"
                        }
                    ]
                """;

        List<Category> response = Collections.singletonList(new Category("Category 1"));

        String jsonResponse = objectMapper.writeValueAsString(response);

        when(categoryService.addCategories(any(List.class))).thenReturn(response);
        this.mockMvc.perform(post(apiUrl + "/categories")
                        .content(jsonRequest).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(content().json(jsonResponse));

        verify(categoryService).addCategories(any(List.class));
        verifyNoMoreInteractions(categoryService);
    }

    @Test
    public void addCategoryWhenThreeItemsInRequestReturnOk() throws Exception {
        String jsonRequest = """
                    [
                        {
                            "name":"Category 1",
                            "name":"Category 2",
                            "name":"Category 3"
                        }
                    ]
                """;
        List<Category> response = List.of(
                new Category("Category 1"),
                new Category("Category 2"),
                new Category("Category 3"));

        String jsonResponse = objectMapper.writeValueAsString(response);

        when(categoryService.addCategories(any(List.class))).thenReturn(response);
        this.mockMvc.perform(post(apiUrl + "/categories")
                        .content(jsonRequest).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(content().json(jsonResponse));

        verify(categoryService).addCategories(any(List.class));
        verifyNoMoreInteractions(categoryService);
    }

    @Test
    public void addCategoryWhenCategoryNameAbsentBadRequest() throws Exception {
        String jsonRequest = """
                    [
                        {
                            "temp":"Category 1"
                        }
                    ]
                """;
        this.mockMvc.perform(post(apiUrl + "/categories")
                        .content(jsonRequest).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.statusMessage", is("Bad Request")))
                .andExpect(jsonPath("$.exception", containsString("ConstraintViolationException")))
                .andExpect(jsonPath("$.path", is("/api/v1/categories")))
                .andExpect(jsonPath("$.message.name", is("must not be blank")));

        verifyNoInteractions(categoryService);
    }

    @Test
    public void addCategoryWhenCategoryNameIsEmptyBadRequest() throws Exception {
        String jsonRequest = """
                    [
                        {
                            "name":""
                        }
                    ]
                """;

        this.mockMvc.perform(post(apiUrl + "/categories")
                        .content(jsonRequest).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.statusMessage", is("Bad Request")))
                .andExpect(jsonPath("$.exception", containsString("ConstraintViolationException")))
                .andExpect(jsonPath("$.path", is("/api/v1/categories")))
                .andExpect(jsonPath("$.message.name", is("must not be blank")));

        verifyNoInteractions(categoryService);
    }

    @Test
    public void addCategoryWhenCategoryNameIsNullBadRequest() throws Exception {
        String jsonRequest = """
                    [
                        {
                            "name":null
                        }
                    ]
                """;

        this.mockMvc.perform(post(apiUrl + "/categories")
                        .content(jsonRequest).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.statusMessage", is("Bad Request")))
                .andExpect(jsonPath("$.exception", containsString("ConstraintViolationException")))
                .andExpect(jsonPath("$.path", is("/api/v1/categories")))
                .andExpect(jsonPath("$.message.name", is("must not be blank")));

        verifyNoInteractions(categoryService);
    }

    @Test
    public void getCategoriesWhenNoCategoriesExistsReturnOk() throws Exception {
        List<Category> categories = new ArrayList<>();
        int page = 1;
        int size = 10;

        PagingInfo pagingInfo = new PagingInfo();
        pagingInfo.setCurrentPage(page);
        pagingInfo.setItemPerPage(size);
        pagingInfo.setTotalPages(10);
        pagingInfo.setTotalElements(100);

        GenericResponse genericResponse = new GenericResponse();
        genericResponse.setResult(categories);
        genericResponse.setPagingInfo(pagingInfo);

        String jsonResponse = objectMapper.writeValueAsString(genericResponse);

        when(categoryService.getCategories(any(), any(), any())).thenReturn(genericResponse);
        this.mockMvc.perform(get(apiUrl + "/categories"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResponse));

        verify(categoryService).getCategories(any(), any(), any());
        verifyNoMoreInteractions(categoryService);
    }

    @Test
    public void getCategoriesWhenFiveCategoriesExistsAndPaginationIsDefaultReturnOk() throws Exception {
        List<Category> categories = List.of(
                new Category("Category 1"),
                new Category("Category 2"),
                new Category("Category 3"),
                new Category("Category 4"),
                new Category("Category 5")
        );

        PagingInfo pagingInfo = new PagingInfo();
        pagingInfo.setCurrentPage(pageDefaultNumber);
        pagingInfo.setItemPerPage(pageDefaultSize);
        pagingInfo.setTotalPages(1);
        pagingInfo.setTotalElements(5);

        GenericResponse genericResponse = new GenericResponse();
        genericResponse.setResult(categories);
        genericResponse.setPagingInfo(pagingInfo);

        String jsonResponse = objectMapper.writeValueAsString(genericResponse);

        when(categoryService.getCategories(null, null, null)).thenReturn(genericResponse);
        this.mockMvc.perform(get(apiUrl + "/categories"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResponse));

        verify(categoryService).getCategories(null, null, null);
        verifyNoMoreInteractions(categoryService);
    }

    @Test
    public void getCategoriesWhenFiveCategoriesExistsAndPaginationIsUsedReturnOk() throws Exception {
        List<Category> categories = List.of(
                new Category("Category 2")
        );

        int currentPageNumber = 2;
        int itemsPerPage = 1;
        String[] sort = {"id", "asc"};

        PagingInfo pagingInfo = new PagingInfo();
        pagingInfo.setCurrentPage(currentPageNumber);
        pagingInfo.setItemPerPage(itemsPerPage);
        pagingInfo.setTotalPages(5);
        pagingInfo.setTotalElements(5);

        GenericResponse genericResponse = new GenericResponse();
        genericResponse.setResult(categories);
        genericResponse.setPagingInfo(pagingInfo);

        String jsonResponse = objectMapper.writeValueAsString(genericResponse);

        when(categoryService.getCategories(currentPageNumber, itemsPerPage, sort)).thenReturn(genericResponse);
        this.mockMvc.perform(get(
                        apiUrl + "/categories?page=%s&size=%s&sort=%s".formatted(
                                currentPageNumber, itemsPerPage, Arrays.stream(sort).collect(Collectors.joining(",")))))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResponse));

        verify(categoryService).getCategories(currentPageNumber, itemsPerPage, sort);
        verifyNoMoreInteractions(categoryService);
    }

    @Test
    public void getCategoryByIdWhenCategoryNotExistsReturnBadRequest() throws Exception {
        Long categoryId = 1L;
        Category category = new Category("Category 1");
        String jsonResponse = objectMapper.writeValueAsString(category);

        when(categoryService.getCategoryById(categoryId)).thenReturn(category);
        this.mockMvc.perform(get(apiUrl + "/categories/%s".formatted(categoryId)))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResponse));

        verify(categoryService).getCategoryById(categoryId);
        verifyNoMoreInteractions(categoryService);
    }

    @Test
    public void getCategoryWhenIdIsInvalidBadRequest() throws Exception {
        String categoryId = "111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111";

        String errorMessage = "Failed to convert value of type 'java.lang.String' to required type 'java.lang.Long'; For input string: " +
                "\"111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111\"";

        this.mockMvc.perform(get(apiUrl + "/categories/%s".formatted(categoryId)))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.statusMessage", is("Bad Request")))
                .andExpect(jsonPath("$.exception", containsString("MethodArgumentTypeMismatchException")))
                .andExpect(jsonPath("$.path", is("/api/v1/categories/%s".formatted(categoryId))))
                .andExpect(jsonPath("$.message", is(errorMessage)));
        verifyNoMoreInteractions(categoryService);
    }

    @Test
    public void getCategoryWhenCategoryNotExistsBadRequest() throws Exception {
        Long categoryId = 111L;

        String errorMessage = "Could not find category with id '111'";

        when(categoryService.getCategoryById(categoryId)).thenThrow(new CategoryNotFoundException(categoryId));

        this.mockMvc.perform(get(apiUrl + "/categories/%s".formatted(categoryId)))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.statusMessage", is("Bad Request")))
                .andExpect(jsonPath("$.exception", containsString("CategoryNotFoundException")))
                .andExpect(jsonPath("$.path", is("/api/v1/categories/%s".formatted(categoryId))))
                .andExpect(jsonPath("$.message", is(errorMessage)));
        verify(categoryService).getCategoryById(categoryId);
        verifyNoMoreInteractions(categoryService);
    }

    @Test
    public void updateCategoryByIdWhenCategoryIsEmptyReturnBadRequest() throws Exception {

        String errorMessage = "No static resource api/v1/categories.";

        String jsonRequest = objectMapper.writeValueAsString(new CategoryRequest("Category 1"));

        this.mockMvc.perform(put(apiUrl + "/categories/")
                        .content(jsonRequest).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.statusMessage", is("Bad Request")))
                .andExpect(jsonPath("$.exception", containsString("NoResourceFoundException")))
                .andExpect(jsonPath("$.path", is("/api/v1/categories/")))
                .andExpect(jsonPath("$.message", is(errorMessage)));

        verifyNoMoreInteractions(categoryService);
    }

    @Test
    public void updateCategoryWhenIdIsInvalidBadRequest() throws Exception {
        String categoryId = "111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111";

        String errorMessage = "Failed to convert value of type 'java.lang.String' to required type 'java.lang.Long'; For input string: " +
                "\"111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111\"";

        String jsonRequest = objectMapper.writeValueAsString(new CategoryRequest("Category 1"));

        this.mockMvc.perform(put(apiUrl + "/categories/%s".formatted(categoryId))
                        .content(jsonRequest).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.statusMessage", is("Bad Request")))
                .andExpect(jsonPath("$.exception", containsString("MethodArgumentTypeMismatchException")))
                .andExpect(jsonPath("$.path", is("/api/v1/categories/%s".formatted(categoryId))))
                .andExpect(jsonPath("$.message", is(errorMessage)));
        verifyNoMoreInteractions(categoryService);
    }

    @Test
    public void updateCategoryWhenCategoryNotExistsBadRequest() throws Exception {
        Long categoryId = 111L;

        String errorMessage = "Could not find category with id '111'";

        String jsonRequest = objectMapper.writeValueAsString(new CategoryRequest("Category 1"));

        doThrow(new CategoryNotFoundException(categoryId)).when(categoryService).updateCategory(any(), any());

        this.mockMvc.perform(put(apiUrl + "/categories/%s".formatted(categoryId))
                        .content(jsonRequest).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.statusMessage", is("Bad Request")))
                .andExpect(jsonPath("$.exception", containsString("CategoryNotFoundException")))
                .andExpect(jsonPath("$.path", is("/api/v1/categories/%s".formatted(categoryId))))
                .andExpect(jsonPath("$.message", is(errorMessage)));
        verify(categoryService).updateCategory(any(), any());
        verifyNoMoreInteractions(categoryService);
    }

    @Test
    public void updateCategoryWhenRequestNotContainsNameBadRequest() throws Exception {
        Long categoryId = 111L;

        String errorMessage = "must not be blank";

        String jsonRequest = """                    
                        {
                            "temp":"Category 1"
                        }
                """;

        doNothing().when(categoryService).updateCategory(any(), any());

        this.mockMvc.perform(put(apiUrl + "/categories/%s".formatted(categoryId))
                        .content(jsonRequest).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.statusMessage", is("Bad Request")))
                .andExpect(jsonPath("$.exception", containsString("MethodArgumentNotValidException")))
                .andExpect(jsonPath("$.path", is("/api/v1/categories/%s".formatted(categoryId))))
                .andExpect(jsonPath("$.message.name", is(errorMessage)));
        verifyNoMoreInteractions(categoryService);
    }

    @Test
    public void updateCategoryWhenRequestNameFieldIsEmptyBadRequest() throws Exception {
        Long categoryId = 111L;

        String errorMessage = "must not be blank";

        String jsonRequest = """                    
                        {
                            "name":""
                        }
                """;

        doNothing().when(categoryService).updateCategory(any(), any());

        this.mockMvc.perform(put(apiUrl + "/categories/%s".formatted(categoryId))
                        .content(jsonRequest).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.statusMessage", is("Bad Request")))
                .andExpect(jsonPath("$.exception", containsString("MethodArgumentNotValidException")))
                .andExpect(jsonPath("$.path", is("/api/v1/categories/%s".formatted(categoryId))))
                .andExpect(jsonPath("$.message.name", is(errorMessage)));
        verifyNoMoreInteractions(categoryService);
    }

    @Test
    public void updateCategoryWhenRequestNameFieldIsNullBadRequest() throws Exception {
        Long categoryId = 111L;

        String errorMessage = "must not be blank";

        String jsonRequest = """                    
                        {
                            "name":null
                        }
                """;

        doNothing().when(categoryService).updateCategory(any(), any());

        this.mockMvc.perform(put(apiUrl + "/categories/%s".formatted(categoryId))
                        .content(jsonRequest).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.statusMessage", is("Bad Request")))
                .andExpect(jsonPath("$.exception", containsString("MethodArgumentNotValidException")))
                .andExpect(jsonPath("$.path", is("/api/v1/categories/%s".formatted(categoryId))))
                .andExpect(jsonPath("$.message.name", is(errorMessage)));
        verifyNoMoreInteractions(categoryService);
    }

    @Test
    public void updateCategoryReturnOk() throws Exception {
        Long categoryId = 10L;

        String jsonRequest = """
                        {
                            "name":"Category 1"
                        }
                """;

        doNothing().when(categoryService).updateCategory(any(), any());
        this.mockMvc.perform(put(apiUrl + "/categories/%s".formatted(categoryId))
                        .content(jsonRequest).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        verify(categoryService).updateCategory(any(), any());
        verifyNoMoreInteractions(categoryService);
    }

    @Test
    public void deleteCategoryByIdWhenCategoryIsEmptyReturnBadRequest() throws Exception {

        String errorMessage = "No static resource api/v1/categories.";

        this.mockMvc.perform(delete(apiUrl + "/categories/"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.statusMessage", is("Bad Request")))
                .andExpect(jsonPath("$.exception", containsString("NoResourceFoundException")))
                .andExpect(jsonPath("$.path", is("/api/v1/categories/")))
                .andExpect(jsonPath("$.message", is(errorMessage)));

        verifyNoMoreInteractions(categoryService);
    }

    @Test
    public void deleteCategoryWhenIdIsInvalidBadRequest() throws Exception {
        String categoryId = "111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111";

        String errorMessage = "Failed to convert value of type 'java.lang.String' to required type 'java.lang.Long'; For input string: " +
                "\"111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111\"";

        this.mockMvc.perform(delete(apiUrl + "/categories/%s".formatted(categoryId)))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.statusMessage", is("Bad Request")))
                .andExpect(jsonPath("$.exception", containsString("MethodArgumentTypeMismatchException")))
                .andExpect(jsonPath("$.path", is("/api/v1/categories/%s".formatted(categoryId))))
                .andExpect(jsonPath("$.message", is(errorMessage)));
        verifyNoMoreInteractions(categoryService);
    }

    @Test
    public void deleteCategoryWhenCategoryNotExistsBadRequest() throws Exception {
        Long categoryId = 111L;

        String errorMessage = "Could not find category with id '111'";
        doThrow(new CategoryNotFoundException(categoryId)).when(categoryService).deleteCategory(any());

        this.mockMvc.perform(delete(apiUrl + "/categories/%s".formatted(categoryId)))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.statusMessage", is("Bad Request")))
                .andExpect(jsonPath("$.exception", containsString("CategoryNotFoundException")))
                .andExpect(jsonPath("$.path", is("/api/v1/categories/%s".formatted(categoryId))))
                .andExpect(jsonPath("$.message", is(errorMessage)));
        verify(categoryService).deleteCategory(any());
        verifyNoMoreInteractions(categoryService);
    }

}
