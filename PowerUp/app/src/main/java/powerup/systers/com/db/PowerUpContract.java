package powerup.systers.com.db;

import android.provider.BaseColumns;

public class PowerUpContract {

    public static final class ScenarioEntry implements BaseColumns {

        public static final String TABLE_NAME = "Scenario";

        public static final String COLUMN_ID = "ID";

        public static final String COLUMN_SCENARIO_NAME = "ScenarioName";

        public static final String COLUMN_TIMESTAMP = "Timestamp";

        public static final String COLUMN_ASKER = "Asker";

        public static final String COLUMN_AVATAR = "Avatar";

        public static final String COLUMN_FIRST_QUESTION_ID = "FirstQID";

        public static final String COLUMN_COMPLETED = "Completed";

        public static final String COLUMN_NEXT_SCENARIO_ID = "NextScenarioID";

        public static final String COLUMN_REPLAYED = "Replayed";

    }

    public static final class AvatarEntry implements BaseColumns {

        public static final String TABLE_NAME = "Avatar";

        public static final String COLUMN_ID = "ID";

        public static final String COLUMN_FACE = "Face";

        public static final String COLUMN_CLOTHES = "Clothes";

        public static final String COLUMN_HAIR = "Hair";

        public static final String COLUMN_EYES = "Eyes";

    }

    public static final class QuestionEntry implements BaseColumns {

        public static final String TABLE_NAME = "Question";

        public static final String COLUMN_QUESTION_ID = "QID";

        public static final String COLUMN_SCENARIO_ID = "ScenarioID";

        public static final String COLUMN_QUESTION_DESCRIPTION = "QDes";

    }

    public static final class AnswerEntry implements BaseColumns {

        public static final String TABLE_NAME = "Answer";

        public static final String COLUMN_ANSWER_ID = "AID";

        public static final String COLUMN_QUESTION_ID = "QID";

        public static final String COLUMN_ANSWER_DESCRIPTION = "ADes";

        public static final String COLUMN_NEXT_ID = "NextID";

        public static final String COLUMN_POINTS = "Points";

    }

    public static final class PointEntry implements BaseColumns {

        public static final String TABLE_NAME = "Point";

        public static final String COLUMN_STRENGTH = "Strength";

        public static final String COLUMN_INVISIBILITY = "Invisibility";

        public static final String COLUMN_HEALING = "Healing";

        public static final String COLUMN_TELEPATHY = "Telepathy";

    }
}
