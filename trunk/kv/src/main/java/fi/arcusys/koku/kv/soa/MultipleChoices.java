package fi.arcusys.koku.kv.soa;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Sep 2, 2011
 */
public class MultipleChoices {
    private List<MultipleChoiceTO> choices;

    /**
     * @return the choices
     */
    @XmlElement (name = "choice")
    public List<MultipleChoiceTO> getChoices() {
        return choices;
    }

    /**
     * @param choices the choices to set
     */
    public void setChoices(final List<MultipleChoiceTO> choices) {
        this.choices = choices;
    }
}
