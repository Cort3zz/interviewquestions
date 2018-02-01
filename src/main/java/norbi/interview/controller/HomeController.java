package norbi.interview.controller;

import norbi.interview.converter.ExcelConverter;
import norbi.interview.domain.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

@Controller
public class HomeController {

    ExcelConverter excelConverter;
    Random rand = new Random();
    HashMap<String, List<Question>> sessionsAndLists = new HashMap<>();

    @Autowired
    public HomeController(ExcelConverter excelConverter) {
        this.excelConverter = excelConverter;
    }


    @GetMapping("/reset")
    public String addQuestion(Model model, HttpServletRequest request) {
        sessionsAndLists.put(request.getSession().getId(), excelConverter.readXLSFile());
        addRandomQuestAndCountToModel(model, sessionsAndLists.get(request.getSession().getId()));
        return "index";
    }

    @GetMapping("/")
    public String nextOne(Model model, HttpServletRequest request) {
            if (!sessionsAndLists.containsKey(request.getSession().getId())) {
                sessionsAndLists.put(request.getSession().getId(), excelConverter.readXLSFile());
            }
        addRandomQuestAndCountToModel(model, sessionsAndLists.get(request.getSession().getId()));

        return "index";
    }

    @GetMapping("/nextQuestion")
    public String nextQuestion(@ModelAttribute("question") Question question,HttpServletRequest request) {
        if(sessionsAndLists.get(request.getSession().getId()).size()==1){
            return "redirect: /reset";
        }
        sessionsAndLists.get(request.getSession().getId()).remove(question);
        return "redirect: /";
    }

    private void addRandomQuestAndCountToModel (Model model, List<Question> listOfQuestions){
        Question question = listOfQuestions.get(rand.nextInt(listOfQuestions.size()));
        model.addAttribute("question", question);
        model.addAttribute("count", listOfQuestions.size());
    }
}
