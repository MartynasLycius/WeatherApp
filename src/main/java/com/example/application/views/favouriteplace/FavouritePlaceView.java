package com.example.application.views.favouriteplace;

import com.example.application.dto.CityGeoCoding;
import com.example.application.services.SecurityService;
import com.example.application.services.UserFavouritePlaceService;
import com.example.application.views.MainLayout;
import com.example.application.views.weatherdetail.WeatherDetail;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.PermitAll;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@PermitAll
@PageTitle("Favourite Places")
@Route(value = "favourite-place", layout = MainLayout.class)
public class FavouritePlaceView extends VerticalLayout implements AfterNavigationObserver {
    private RestTemplate restTemplate;
    private SecurityService securityService;
    private UserFavouritePlaceService userFavouritePlaceService;

    private Div cityListDiv = new Div();
    private H2 notFoundFavouriteH2 = new H2("No Favourite Place Found");

    public FavouritePlaceView(RestTemplate restTemplate, SecurityService securityService,
                              UserFavouritePlaceService userFavouritePlaceService) {
        this.restTemplate = restTemplate;
        this.securityService = securityService;
        this.userFavouritePlaceService = userFavouritePlaceService;

        setMargin(true);
        addClassName("card-list-view");
        add(cityListDiv);
    }

    private HorizontalLayout createCard(CityGeoCoding cityGeoCoding) {
        HorizontalLayout card = new HorizontalLayout();
        card.addClassName("card");
        card.setSpacing(false);
        card.getThemeList().add("spacing-s");

        VerticalLayout description = new VerticalLayout();
        description.addClassName("description");
        description.setSpacing(false);
        description.setPadding(false);

        HorizontalLayout header = new HorizontalLayout();
        header.addClassName("header");
        header.setSpacing(false);
        header.getThemeList().add("spacing-s");

        Span name = new Span(cityGeoCoding.name);
        name.addClassName("name");
        Span date = new Span(cityGeoCoding.country);
        date.addClassName("date");
        header.add(name, date);

        HorizontalLayout actions = new HorizontalLayout();
        actions.addClassName("actions");
        actions.setSpacing(false);
        actions.getThemeList().add("spacing-s");

        Icon likeIcon = VaadinIcon.HEART.create();
        likeIcon.addClassName("icon");
        likeIcon.addClassName("icon-red");

        likeIcon.addClickListener(iconClickEvent -> {
            if (this.securityService.isLoggedIn()) {
                String msg = this.userFavouritePlaceService.makePlaceFavourite(cityGeoCoding);
                Notification.show(msg);
                this.getCityList();
            }
            else {
                likeIcon.getUI().ifPresent(ui -> {
                    ui.navigate("login");
                });
            }
        });

        header.addClickListener(event -> {
            header.getUI().ifPresent(ui -> {
                QueryParameters params = QueryParameters.simple(Map.of(
                        "city", cityGeoCoding.name,
                        "lon", String.valueOf(cityGeoCoding.longitude),
                        "lat", String.valueOf(cityGeoCoding.latitude),
                        "tz", String.valueOf(cityGeoCoding.timezone)
                ));
                ui.navigate(WeatherDetail.class, params);
            });
        });

        actions.add(likeIcon);

        description.add(header, actions);
        card.add(description);
        return card;
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        this.getCityList();
    }

    private void getCityList() {
        List<CityGeoCoding> cityGeoCodingList = this.userFavouritePlaceService.getCurrentUserFavouriteCityGeoCoding();
        if (cityGeoCodingList == null) cityGeoCodingList = new ArrayList<>();
        addCitListDiv(cityGeoCodingList);
    }

    private void addCitListDiv(List<CityGeoCoding> cityGeoCodingList) {
        cityListDiv.removeAll();
        for (CityGeoCoding cityGeoCoding : cityGeoCodingList) {
            cityListDiv.add(createCard(cityGeoCoding));
        }
        if (cityGeoCodingList.size() < 1) {
            add(notFoundFavouriteH2);
        }
    }
}
