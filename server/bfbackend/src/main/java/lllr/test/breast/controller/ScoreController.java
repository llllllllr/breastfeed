package lllr.test.breast.controller;


import lllr.test.breast.common.ServerResponse;
import lllr.test.breast.service.inter.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/score")
@Controller
public class ScoreController {

    @Autowired
    ScoreService scoreService;

    @GetMapping("/get")
    ServerResponse<Integer> getScore(@RequestParam("userid") Integer userid)
    {
        return scoreService.getScore(userid);
    }
    @GetMapping("/update")
    ServerResponse<Integer> updateScore(@RequestParam("userid") Integer userid)
    {
        return scoreService.getScore(userid);
    }

}
