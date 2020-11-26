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
        Configuration.startMaximized = true;
        open("https://github.com/");
    }

    @Test
    public void createIssueByClearSelenide() {
        $("[href='/login']").click();
        $("#login_field").val(UserForLogin.USERLOGIN);
        $("#password").val(UserForLogin.USERPASS);
        $("[value='Sign in']").click();
        $("[aria-label='View profile and more']").click();
        $(byText("Your repositories")).click();
        $(byText("qa_guru_3_2")).click();
        $("[data-content='Issues']").click();
        $("[data-hotkey='c']").click();
        String nameIssue = "Issue " + System.currentTimeMillis();
        $("#issue_title").val(nameIssue);
        $("#assignees-select-menu").click();
//        String assignedUser = "PodleshyySergey";
        $("#assignee-filter-field").val(assignedUser);
        $(".js-username").click();
        $("#assignees-select-menu").click();
        $("#labels-select-menu").click();
        $("[data-label-name='" + lableBug + "']").parent().click();
        $("#labels-select-menu").click();
        $x("//button[contains(text(),'Submit new issue')]").click();

        $("#assignees-select-menu").sibling(0).$("span").shouldHave(Condition.text(assignedUser));
//        String lableBug = "bug";
        $("#labels-select-menu").sibling(0).$("span").shouldHave(Condition.text(lableBug));

        $("[data-content='Issues']").click();
        $(byText(nameIssue)).shouldBe(Condition.visible);
    }

}
