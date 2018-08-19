package powerup.systers.com.utils;

import android.content.Context;
import android.util.Log;

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
    private String TAG = "CSVReader";
    public CSVReader(Context context) {
        this.context = context;
    }

    /*
    * @params filename: contains csv filename that need to be read
    * type: contains what type of object needs to be created for adding in List
    * type values: 0 - Question, 1 - Answer, 2 - Scenario, 3 - Clothes, 4 - Hair, 5 - Accessory
    * */
    public <T> List<T> getList(String filename, int type) {
        List<T> list = new ArrayList<T>();
        BufferedReader br = null;
        try{
            br =  new BufferedReader(new InputStreamReader(context.getAssets().open(filename)));
            while ((line = br.readLine()) != null) {
                String[] row = line.split(csvSplitBy);
                if(row.length > 0) {
                    switch (type) {
                        case 0: list.add((T) new Question(Integer.valueOf(row[0]), Integer.valueOf(row[1]), row[2]));
                                break;
                        case 1: list.add((T) new Answer(Integer.valueOf(row[0]), Integer.valueOf(row[1]),
                                            row[2], Integer.valueOf(row[3]), Integer.valueOf(row[4])));
                                break;
                        case 2: list.add((T) new Scenario(Integer.valueOf(row[0]), row[1], row[2], row[3], Integer.valueOf(row[4]),
                                    Integer.valueOf(row[5]), 0, Integer.valueOf(row[6]), 0));
                                break;
                        case 3: list.add((T) new Clothes(Integer.valueOf(row[0]), row[1], Integer.valueOf(row[2]), 0));
                                break;
                        case 4: list.add((T) new Hair(Integer.valueOf(row[0]), row[1], Integer.valueOf(row[2]), 0));
                                break;
                        case 5: list.add((T) new Accessory(Integer.valueOf(row[0]), row[1], Integer.valueOf(row[2]), 0));
                                break;
                        default: Log.e(TAG, "Invalid file type");
                                break;
                    }
                }
            }
        } catch (IOException e) {
            Log.e(TAG, "IOException while opening the file: "+ filename);
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                    Log.e(TAG, "IOException while closing the file: "+ filename);
            }
        }
        return list;
    }
}