package norbi.interview.controller;

import norbi.interview.converter.ExcelConverter;
import norbi.interview.domain.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

@Controller
public class HomeController {

    private ExcelConverter excelConverter;
    private Random rand = new Random();
    private HashMap<String, List<Question>> cookiesAndLists = new HashMap<>();
    private List<String> cookieNames = new ArrayList<>();

    @Autowired
    public HomeController(ExcelConverter excelConverter) {
        this.excelConverter = excelConverter;
    }


    @GetMapping("/reset")
    public String addQuestion(Model model, HttpServletRequest request) {
        cookiesAndLists.put(getClientsCookie(request).getName(), excelConverter.readXLSFile());
        Question question = cookiesAndLists.get(getClientsCookie(request).getName()).get(rand.nextInt(cookiesAndLists.get(getClientsCookie(request).getName()).size()));
        model.addAttribute("question", question);
        model.addAttribute("count", cookiesAndLists.get(getClientsCookie(request).getName()).size());

        return "index";
    }

    @GetMapping("/")
    public String nextOne(Model model, HttpServletRequest request, HttpServletResponse response) {
        if (getClientsCookie(request) == null) {
            Cookie cookie = new Cookie("interviewQnAID" + rand.nextInt(1000), null);
            cookiesAndLists.put(cookie.getName(), excelConverter.readXLSFile());
            response.addCookie(cookie);
            cookieNames.add(cookie.getName());
                return "redirect: https://interviewqna.herokuapp.com/";
        }
        Question question = cookiesAndLists.get(getClientsCookie(request).getName()).get(rand.nextInt(cookiesAndLists.get(getClientsCookie(request).getName()).size()));
        model.addAttribute("question", question);
        model.addAttribute("count", cookiesAndLists.get(getClientsCookie(request).getName()).size());

        return "index";
    }

    @PostMapping("/nextQuestion")
    public String nextQuestion(@ModelAttribute("question") Question question, HttpServletRequest request) {
        if (cookiesAndLists.get(getClientsCookie(request).getName()).size() == 1) {
            return "redirect: /reset";
        }

        cookiesAndLists.get(getClientsCookie(request).getName()).remove(question);
        return "redirect: https://interviewqna.herokuapp.com/";
    }



    private Cookie getClientsCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if(cookies!=null) {
            for (Cookie cookie : cookies) {
                for (String cookieName : cookieNames) {
                    if (cookie.getName().equals(cookieName)) {
                        return cookie;
                    }
                }
            }
        }
        return null;
    }

}
