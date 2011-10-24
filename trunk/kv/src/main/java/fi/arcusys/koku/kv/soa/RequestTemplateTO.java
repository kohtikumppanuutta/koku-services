package fi.arcusys.koku.kv.soa;

import java.util.List;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Sep 2, 2011
 */
public class RequestTemplateTO extends RequestTemplateSummary {
    private List<QuestionTO> questions;
    private List<MultipleChoiceTO> choices;

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
     * @return the choices
     */
    public List<MultipleChoiceTO> getChoices() {
        return choices;
    }

    /**
     * @param choices the choices to set
     */
    public void setChoices(List<MultipleChoiceTO> choices) {
        this.choices = choices;
    }

    
}
