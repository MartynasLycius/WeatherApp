package org.weather.app.components;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;

public class ComponentUtils {

    public static Div componentsInDiv(String style, Component... components){
        Div div = new Div(components);
        if(style != null && !style.isEmpty()){
            div.addClassName(style);
        }
        return div;
    }
    public static Div componentInDiv(Component component, String... style){
        Div div = new Div(component);
        if(style != null && style.length > 0){
            div.addClassNames(style);
        }
        return div;
    }

    public static Image createImage(String src, String alterText, String title, String sizeX, String sizeY, String... styles){
        Image image = new Image();
        image.setSrc(src);
        image.setHeight(sizeX);
        image.setWidth(sizeY);
        image.setTitle(title);
        if(alterText != null && !alterText.isEmpty()){
            image.setAlt(alterText);
        }
        if(styles != null && styles.length > 0){
            image.addClassNames(styles);
        }

        return image;
    }
}
