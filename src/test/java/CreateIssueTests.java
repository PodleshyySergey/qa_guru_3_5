import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class CreateIssueTests {

    String assignedUser = "PodleshyySergey";
    String lableBug = "bug";

    @BeforeEach
    public void setUp() {
//        Открытие начальной страницы GitHub
        Configuration.startMaximized = true;
        open("https://github.com/");
    }

    @Test
    public void createIssueByClearSelenide() {
//        Переход на форму авторизации
        $("[href='/login']").click();
//        Ввод логина
        $("#login_field").val(UserForLogin.USERLOGIN);
//        Ввод пароля
        $("#password").val(UserForLogin.USERPASS);
//        Обращение к кнопке "Sing in"
        $("[value='Sign in']").click();
//        Открытие списка репозиториев
        $("[aria-label='View profile and more']").click();
        $(byText("Your repositories")).click();
//        Переход в репозиторий "qa_guru_3_2"
        $(byText("qa_guru_3_2")).click();
//        Переход на вкладку 'Issues' в репозитории
        $("[data-content='Issues']").click();
//        Обращение к кнопке создания репозитория
        $("[data-hotkey='c']").click();
//        Ввод имени репозитория
        String nameIssue = "Issue " + System.currentTimeMillis();
        $("#issue_title").val(nameIssue);
//       Выбор того, кому будет назначена Issue
        $("#assignees-select-menu").click();
        $("#assignee-filter-field").val(assignedUser);
        $(".js-username").click();
        $("#assignees-select-menu").click();
//        Выбор Label для Issue
        $("#labels-select-menu").click();
        $("[data-label-name='" + lableBug + "']").parent().click();
        $("#labels-select-menu").click();
//        Обращение к кнопке сохранения Issue
        $x("//button[contains(text(),'Submit new issue')]").click();

//        Проверка того, что в добавленной Issue отображается логин того, кому назначили и выбранная Label
        $("#assignees-select-menu").sibling(0).$("span").shouldHave(Condition.text(assignedUser));
        $("#labels-select-menu").sibling(0).$("span").shouldHave(Condition.text(lableBug));

//        Переход на вкладку 'Issues' в репозитории и проверка, что Issue с таким именем есть в списке
        $("[data-content='Issues']").click();
        sleep(1000);
        $(byText(nameIssue)).shouldBe(Condition.visible);
    }

}
