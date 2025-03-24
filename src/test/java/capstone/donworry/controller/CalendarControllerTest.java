package capstone.donworry.controller;

import capstone.donworry.domain.Expense;
import capstone.donworry.domain.ExpenseCategory;
import capstone.donworry.domain.PaymentMethod;
import capstone.donworry.dto.AddExpenseRequest;
import capstone.donworry.dto.UpdateExpenseRequest;
import capstone.donworry.repository.CalendarRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CalendarControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    CalendarRepository calendarRepository;





    @DisplayName("addExpense: 가계부 항목 추가에 성공")
    @Test
    @WithMockUser(username = "testUser", roles = {"USER"}) // 인증문제해결: 가짜 사용자 추가
    public void addExpense() throws Exception{
        // given
        final String url = "/api/calendar";
        final String title = "title";
        final Long amount = 100L;
        final LocalDate expenseDate = LocalDate.now();
        final ExpenseCategory category = ExpenseCategory.valueOf("FOOD");
        final PaymentMethod payment = PaymentMethod.valueOf("CARD");
        final AddExpenseRequest userRequest = new AddExpenseRequest(title, amount, expenseDate, category, payment);

        final String requestBody = objectMapper.writeValueAsString(userRequest);



        // when
        ResultActions result = mockMvc.perform(post(url)
                .with(csrf()) // csrf 우회를 위한 토큰 추가
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        // then
        result.andExpect(status().isCreated());

        List<Expense> expenses = calendarRepository.findAll();

        assertThat(expenses.size()).isEqualTo(1);
        assertThat(expenses.get(0).getTitle()).isEqualTo(title);
        assertThat(expenses.get(0).getAmount()).isEqualTo(amount);
        assertThat(expenses.get(0).getCategory()).isEqualTo(category);
        assertThat(expenses.get(0).getPayment()).isEqualTo(payment);
    }


    @DisplayName("deleteExpense: 가계부 항목 삭제 성공")
    @Test
    public void deleteExpense() throws Exception{

        // given
        final String url = "/api/calendar/{id}";
        final String title = "title";
        final Long amount = 100L;
        final LocalDate expenseDate = LocalDate.now();
        final ExpenseCategory category = ExpenseCategory.valueOf("FOOD");
        final PaymentMethod payment = PaymentMethod.valueOf("CARD");

        Expense savedExpense = calendarRepository.save(Expense.builder()
                .title(title)
                .amount(amount)
                .expenseDate(expenseDate)
                .category(category)
                .payment(payment)
                .build());

        // when
        mockMvc.perform(delete(url, savedExpense.getExpenseId()))
                .andExpect(status().isOk());

        // then
        List<Expense> expenses = calendarRepository.findAll();
        assertThat(expenses).isEmpty();
    }

    @DisplayName("updateExpense: 가계부 항목 수정 성공")
    @Test
    public void updateExpense() throws Exception{
        // given
        final String url = "/api/calendar/{id}";
        final String title = "title";
        final Long amount = 100L;
        final LocalDate expenseDate = LocalDate.now();
        final ExpenseCategory category = ExpenseCategory.valueOf("FOOD");
        final PaymentMethod payment = PaymentMethod.valueOf("CARD");

        Expense savedExpense = calendarRepository.save(Expense.builder()
                .title(title)
                .amount(amount)
                .expenseDate(expenseDate)
                .category(category)
                .payment(payment)
                .build());

        final String newTitle = "new title";
        final Long newAmount = 200L;

        UpdateExpenseRequest request = new UpdateExpenseRequest(newTitle, newAmount, expenseDate, category, payment);

        // when
        ResultActions result = mockMvc.perform(put(url, savedExpense.getExpenseId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(request)));

        // then
        result.andExpect(status().isOk());

        Expense expense = calendarRepository.findById(savedExpense.getExpenseId()).get();
        assertThat(expense.getTitle()).isEqualTo(newTitle);
        assertThat(expense.getAmount()).isEqualTo(newAmount);
    }
}