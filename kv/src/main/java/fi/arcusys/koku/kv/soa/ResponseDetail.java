package fi.arcusys.koku.kv.soa;

import java.util.List;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Oct 6, 2011
 */
public class ResponseDetail extends ResponseSummary {
    private List<QuestionTO> questions;
    private List<AnswerTO> answers;
    /**
     * @return the questions
     */
    public List<QuestionTO> getQuestions() {
        return questions;
    }
    /**
     * @param questions the questions to set
     */
    public void setQuestions(List<QuestionTO> questions) {
        this.questions = questions;
    }
    /**
     * @return the answers
     */
    public List<AnswerTO> getAnswers() {
        return answers;
    }
    /**
     * @param answers the answers to set
     */
    public void setAnswers(List<AnswerTO> answers) {
        this.answers = answers;
    }
}
