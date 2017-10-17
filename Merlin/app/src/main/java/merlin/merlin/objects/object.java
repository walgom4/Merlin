package merlin.merlin.objects;

import android.app.Application;

import java.util.List;

/**
 * Created by walgom on 13/10/2017.
 */

public class object extends Application {
    List<element> elements;
    element Element;

    public List<element> getElements() {
        return elements;
    }

    public void setElements(List<element> elements) {
        this.elements = elements;
    }

    public element getElement() {
        return Element;
    }

    public void setElement(element element) {
        Element = element;
    }
}
