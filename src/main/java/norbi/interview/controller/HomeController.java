package norbi.interview.controller;

import norbi.interview.converter.ExcelConverter;
import norbi.interview.domain.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;
import java.util.Random;

@Controller
public class HomeController {

    ExcelConverter excelConverter;
    List<Question> listOfQuestions;
    Random rand = new Random();


    @Autowired
    public HomeController(ExcelConverter excelConverter) {
        this.excelConverter = excelConverter;
        listOfQuestions = excelConverter.readXLSFile();
    }


    @GetMapping("/reset")
    public String addQuestion(Model model) {
        listOfQuestions = excelConverter.readXLSFile();
        Question question = listOfQuestions.get(rand.nextInt(listOfQuestions.size()));
        model.addAttribute("question", question);
        model.addAttribute("count", listOfQuestions.size());
        return "index";
    }

    @GetMapping("/")
    public String nextOne(Model model) {
        Question question = listOfQuestions.get(rand.nextInt(listOfQuestions.size()));
        model.addAttribute("question", question);
        model.addAttribute("count", listOfQuestions.size());
        return "index";
    }

    @GetMapping("/nextQuestion")
    public String nextQuestion(@ModelAttribute("question") Question question) {
        if(listOfQuestions.size()==1){
            return "redirect: /reset";
        }
        listOfQuestions.remove(question);
        return "redirect: /";
    }
}
