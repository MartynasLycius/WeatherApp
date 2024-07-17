package org.weather.app.components;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import org.weather.app.entities.FavoriteCity;
import org.weather.app.views.SearchLocation;

import java.util.List;

public class SearchBar extends HorizontalLayout {

    private TextField searchField;
    private Button searchButton;
    private SearchLocation searchLocation;
    private Select<String> select;

    public SearchBar(SearchLocation searchLocation) {

        setWidth("80%");
        getStyle().set("background-color", "rgba(211, 196, 218, 0.5)");
        setHeight("75px");
        this.searchLocation = searchLocation;
        searchField = new TextFieldBuilder()
                .withPlaceholder("Enter your city name here")
                .withWidth("300px")
                .withRequired(true)
                .build();

        Div searchDiv = new Div(searchField);
        searchDiv.getStyle().set("margin-left" , "15%");
        searchDiv.getStyle().set("margin-top" , "15px");

        Text logoutText =  new Text("Logout");
        Div registerDiv = new Div(logoutText);
        registerDiv.getStyle().set("margin-left" , "10%");
        registerDiv.getStyle().set("margin-top" , "3%");
        registerDiv.addClassNames("default-text", "font-weight-bold");

        searchButton = new Button("Search");
        searchButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        searchButton.getStyle().set("margin-top" , "20px");

        select = new Select<>();
        select.setLabel("My favorite");
        select.getStyle().set("margin-top" , "-1%");
        select.getStyle().set("margin-left" , "5%");
        select.addValueChangeListener(event->this.searchLocation.onSelectFromFavorite(event.getValue()));

        add(searchDiv, searchButton, select, registerDiv);

        registerDiv.addClickListener(divClickEvent -> searchLocation.handleLogout());
    }

    public TextField getSearchField() {
        return searchField;
    }

    public Button getSearchButton() {
        return searchButton;
    }

    public void setFavoriteCities(List<FavoriteCity> favoriteCityList){
        if(null != favoriteCityList && !favoriteCityList.isEmpty()){
            List<String> names = favoriteCityList.stream()
                    .map(FavoriteCity::getName) // Extract the name from each FavoriteCity
                    .toList();
            select.setItems(names);
        }
    }
}
