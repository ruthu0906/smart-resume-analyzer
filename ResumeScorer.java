package logic;

import java.io.*;
import java.util.*;

public class ResumeScorer {
    public static class Result {
        public int score;
        public List<String> missingSkills = new ArrayList<>();
        public String suggestion;
    }

    public static Result score(ResumeParser.Parsed p, String skillFile) {
        Result r = new Result();
        Set<String> skillSet = new HashSet<>();
        try (BufferedReader br = new BufferedReader(new FileReader(skillFile))) {
            String s;
            while ((s = br.readLine()) != null)
                skillSet.add(s.toLowerCase().trim());
        } catch (IOException e) {
            System.out.println("Skill file not found");
        }

        String text = p.text.toLowerCase();
        int found = 0;
        for (String s : skillSet) {
            if (text.contains(s)) found++;
            else r.missingSkills.add(s);
        }

        r.score = (int) ((found / (double) skillSet.size()) * 100);
        if (r.score >= 80) r.suggestion = "Excellent resume!";
        else if (r.score >= 50) r.suggestion = "Add more relevant skills/projects.";
        else r.suggestion = "Needs major improvement: add missing skills.";

        return r;
    }
}
