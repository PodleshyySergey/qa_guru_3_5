import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class CreateIssueTests {

    @BeforeEach
    public void setUp() {
//        Configuration.baseUrl = "https://github.com/";
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
        $x("//button[contains(text(),'Submit new issue')]").click();

        $("[data-content='Issues']").click();
        $(byText(nameIssue)).shouldBe(Condition.visible);
    }

}
