package powerup.systers.com.utils;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import powerup.systers.com.data.entities.Accessory;
import powerup.systers.com.data.entities.Answer;
import powerup.systers.com.data.entities.Clothes;
import powerup.systers.com.data.entities.Hair;
import powerup.systers.com.data.entities.Question;
import powerup.systers.com.data.entities.Scenario;

public class CSVReader {
    private Context context;
    private String line;
    private String csvSplitBy = ",";

    public CSVReader(Context context, String fileName) {
        this.context = context;
    }

    // read Questions from csv file & return a List of Question.
    public List<Question> getQuestionList() throws IOException {
        List<Question> questionList = new ArrayList<>();
        BufferedReader br = null;
        br = new BufferedReader(new InputStreamReader(context.getAssets().open("Question.csv")));

        while ((line = br.readLine()) != null) {
            String[] row = line.split(csvSplitBy);
            questionList.add(new Question(Integer.valueOf(row[0]), Integer.valueOf(row[1]), row[2]));
        }
        br.close();
        return questionList;
    }

    // read Answers from csv file & return a List of Answer.
    public List<Answer> getAnswerList() throws IOException {
        List<Answer> answerList = new ArrayList<>();
        BufferedReader br = null;
        br = new BufferedReader(new InputStreamReader(context.getAssets().open("Answer.csv")));

        while ((line = br.readLine()) != null) {
            String[] row = line.split(csvSplitBy);
            answerList.add(new Answer(Integer.valueOf(row[0]), Integer.valueOf(row[1]),
                    row[2], Integer.valueOf(row[3]), Integer.valueOf(row[4])));
        }
        br.close();
        return answerList;
    }

    // read Scenario from csv file & return a List of Scenario data.
    public List<Scenario> getScenarioList() throws IOException {
        List<Scenario> scenarioList = new ArrayList<>();
        BufferedReader br = null;
        br = new BufferedReader(new InputStreamReader(context.getAssets().open("Scenario.csv")));

        while ((line = br.readLine()) != null) {
            String[] row = line.split(csvSplitBy);
            scenarioList.add(new Scenario(Integer.valueOf(row[0]), row[1], row[2], row[3], Integer.valueOf(row[4]),
                    Integer.valueOf(row[5]), 0, Integer.valueOf(row[6]), 0));
        }
        br.close();
        return scenarioList;
    }

    // read Clothes from csv file & return a List of Clothes.
    public List<Clothes> getClothesList() throws IOException {
        List<Clothes> clothList = new ArrayList<>();
        BufferedReader br = null;
        br = new BufferedReader(new InputStreamReader(context.getAssets().open("Clothes.csv")));

        while ((line = br.readLine()) != null) {
            String[] row = line.split(csvSplitBy);
            clothList.add(new Clothes(Integer.valueOf(row[0]), row[1], Integer.valueOf(row[2]), 0));
        }
        br.close();
        return clothList;
    }

    // read Hair from csv file & return a List of Hair data.
    public List<Hair> getHairList() throws IOException {
        List<Hair> hairList = new ArrayList<>();
        BufferedReader br = null;
        br = new BufferedReader(new InputStreamReader(context.getAssets().open("Hair.csv")));

        while ((line = br.readLine()) != null) {
            String[] row = line.split(csvSplitBy);
            hairList.add(new Hair(Integer.valueOf(row[0]), row[1], Integer.valueOf(row[2]), 0));
        }
        br.close();
        return hairList;
    }

    // read Accessories from csv file & return a List of Accessory data.
    public List<Accessory> getAccessoriesList() throws IOException {
        List<Accessory> accessoryList = new ArrayList<>();
        BufferedReader br = null;
        br = new BufferedReader(new InputStreamReader(context.getAssets().open("Accessories.csv")));

        while ((line = br.readLine()) != null) {
            String[] row = line.split(csvSplitBy);
            accessoryList.add(new Accessory(Integer.valueOf(row[0]), row[1], Integer.valueOf(row[2]), 0));
        }
        br.close();
        return accessoryList;
    }
}
