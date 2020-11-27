import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Step;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;

import static com.codeborne.selenide.Selectors.withText;
import static io.qameta.allure.Allure.step;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CreateIssueTests {

    String assignedUser = "PodleshyySergey";
    String lableBug = "bug";
    String repository = "qa_guru_3_2";

    @Test
    @Order(0)
    @DisplayName("Создание Issue на чистом Selenide")
    public void createIssueByClearSelenide() {
        SelenideLogger.addListener("allure", new AllureSelenide());

        String nameIssue = "Issue " + System.currentTimeMillis();

//        Открытие начальной страницы GitHub
        Configuration.startMaximized = true;
        open("https://github.com/");
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
        $(byText(repository)).click();
//        Переход на вкладку 'Issues' в репозитории
        $("[data-content='Issues']").click();
//        Обращение к кнопке создания Issue
        $("[data-hotkey='c']").click();
//        Ввод имени репозитория
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
        sleep(1000);
//        Выход из учетной записи GitHub
        $("[aria-label='View profile and more']").click();
        $x("//*[contains(text(),'Sign out')]").click();
    }

    @Test
    @Order(1)
    @DisplayName("Создание Issue с использованием лямбда шагов")
    public void createIssueByLambda() {
        String nameIssue = "Issue " + System.currentTimeMillis();

        step("Открытие начальной страницы GitHub", () -> {
            Configuration.startMaximized = true;
            open("https://github.com/");
        });
        step("Переход на форму авторизации", () -> {
            $("[href='/login']").click();
        });
        step("Ввод логина", () -> {
            $("#login_field").val(UserForLogin.USERLOGIN);
        });
        step("Ввод пароля", () -> {
            $("#password").val(UserForLogin.USERPASS);
        });
        step("Обращение к кнопке 'Sing in'", () -> {
            $("[value='Sign in']").click();
        });
        step("Открытие списка репозиториев", () -> {
            $("[aria-label='View profile and more']").click();
            $(byText("Your repositories")).click();
        });
        step("Переход в репозиторий 'qa_guru_3_2'", () -> {
            $(byText(repository)).click();
        });
        step("Переход на вкладку 'Issues' в репозитории", () -> {
            $("[data-content='Issues']").click();
        });
        step("Обращение к кнопке создания Issue", () -> {
            $("[data-hotkey='c']").click();
        });
        step("Ввод имени репозитория", () -> {
            $("#issue_title").val(nameIssue);
        });
        step("Выбор того, кому будет назначена Issue", () -> {
            $("#assignees-select-menu").click();
            $("#assignee-filter-field").val(assignedUser);
            $(".js-username").click();
            $("#assignees-select-menu").click();
        });
        step("Выбор Label для Issue", () -> {
            $("#labels-select-menu").click();
            $("[data-label-name='" + lableBug + "']").parent().click();
            $("#labels-select-menu").click();
        });
        step("Обращение к кнопке сохранения Issue", () -> {
            $x("//button[contains(text(),'Submit new issue')]").click();
        });

        step("Проверка того, что в добавленной Issue отображается логин того, кому назначили и выбранная Label", () -> {
            $("#assignees-select-menu").sibling(0).$("span").shouldHave(Condition.text(assignedUser));
            $("#labels-select-menu").sibling(0).$("span").shouldHave(Condition.text(lableBug));
        });

        step("Переход на вкладку 'Issues' в репозитории и проверка, что Issue с таким именем есть в списке", () -> {
            $("[data-content='Issues']").click();
            sleep(1000);
            $(byText(nameIssue)).shouldBe(Condition.visible);
            sleep(1000);
        });

        step("Выход из учетной записи GitHub", () -> {
            $("[aria-label='View profile and more']").click();
            $x("//*[contains(text(),'Sign out')]").click();
        });
    }

    @Test
    @Order(2)
    @DisplayName("Создание Issue с использованием аннотаций")
    public void createIssueByAnnotationStep() {
        String nameIssue = "Issue " + System.currentTimeMillis();

        final BaseSteps baseSteps = new BaseSteps();

        baseSteps.goToGitHub();
        baseSteps.userLogin();
        baseSteps.openRepoList();
        baseSteps.goToRepo(repository);
        baseSteps.openTabIssue();
        baseSteps.createIssue(nameIssue, assignedUser, lableBug);

        baseSteps.checkAssignerAndLabelIssue(assignedUser, lableBug);
        baseSteps.checkIssueInList(nameIssue);

        baseSteps.deloginGitHub();
    }


    public static class BaseSteps {
        @Step("Открытие начальной страницы GitHub")
        public void goToGitHub() {
            Configuration.startMaximized = true;
            open("https://github.com/");
        }

        @Step("Авторизации на GitHub")
        public void userLogin() {
            $("[href='/login']").click();
            $("#login_field").val(UserForLogin.USERLOGIN);
            $("#password").val(UserForLogin.USERPASS);
            $("[value='Sign in']").click();
        }

        @Step("Открытие списка репозиториев")
        public void openRepoList() {
            $("[aria-label='View profile and more']").click();
            $(byText("Your repositories")).click();
        }

        @Step("Переход в репозиторий ")
        public void goToRepo(String repo) {
            $(byText(repo)).click();
        }

        @Step("Переход на вкладку 'Issues' в репозитории")
        public void openTabIssue() {
            $("[data-content='Issues']").click();
        }

        @Step("Создание новой Issue")
        public void createIssue(String nameIssue, String assignedUser, String lableBug) {
            //        Обращение к кнопке создания Issue
            $("[data-hotkey='c']").click();
            //        Ввод имени репозитория
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
        }

        @Step("Проверка отображения в Issue 'Assigner' и 'Label'")
        public void checkAssignerAndLabelIssue(String assignedUser, String lableBug) {
            //        Проверка того, что в добавленной Issue отображается логин того, кому назначили и выбранная Label
            $("#assignees-select-menu").sibling(0).$("span").shouldHave(Condition.text(assignedUser));
            $("#labels-select-menu").sibling(0).$("span").shouldHave(Condition.text(lableBug));
        }

        @Step("Переход на вкладку 'Issues' в репозитории и проверка, что Issue с таким именем есть в списке")
        public void checkIssueInList(String nameIssue) {
            //        Переход на вкладку 'Issues' в репозитории и проверка, что Issue с таким именем есть в списке
            $("[data-content='Issues']").click();
            sleep(1000);
            $(byText(nameIssue)).shouldBe(Condition.visible);
            sleep(1000);
        }

        @Step("Выход из учетной записи GitHub")
        public void deloginGitHub() {
            $("[aria-label='View profile and more']").click();
            $x("//*[contains(text(),'Sign out')]").click();
        }
    }
}
