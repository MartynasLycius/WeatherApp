package com.example.application.views.homepage;

import com.example.application.dto.CityGeoCoding;
import com.example.application.services.LocationService;
import com.example.application.services.SecurityService;
import com.example.application.services.UserFavouritePlaceService;
import com.example.application.views.MainLayout;
import com.example.application.views.weatherdetail.WeatherDetail;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import jakarta.annotation.security.PermitAll;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@PermitAll
@AnonymousAllowed
@PageTitle("Weather Application")
@Route(value = "home", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class HomePageView extends VerticalLayout implements AfterNavigationObserver {
    private RestTemplate restTemplate;
    private SecurityService securityService;
    private UserFavouritePlaceService userFavouritePlaceService;
    private LocationService locationService;

    private TextField cityName = new TextField("City Name");
    private Button searchButton = new Button("Search");
    private Div cityListDiv = new Div();
    private Select<Integer> pageSelect = new Select<>();
    private Div paginationDiv = new Div();

    public HomePageView(RestTemplate restTemplate, SecurityService securityService,
                        UserFavouritePlaceService userFavouritePlaceService, LocationService locationService) {
        this.restTemplate = restTemplate;
        this.securityService = securityService;
        this.userFavouritePlaceService = userFavouritePlaceService;
        this.locationService = locationService;

        searchButton.addClickListener(e -> {
            String searchCityName = cityName.getValue().trim();
            if (!searchCityName.equals("")) {
                VaadinSession.getCurrent().setAttribute("searched_city", searchCityName);
                this.getCityList();
            }
        });
        searchButton.addClickShortcut(Key.ENTER);

        setMargin(true);
        HorizontalLayout topHeading = new HorizontalLayout();
        topHeading.setVerticalComponentAlignment(Alignment.END, cityName, searchButton);

        topHeading.add(cityName, searchButton);
        add(topHeading);
        addClassName("card-list-view");
        cityListDiv.getElement().setAttribute("style", "max-height: 450px; overflow-x:scroll");
        add(cityListDiv);

        paginationDiv.add(pageSelect);
        this.addPaginationSelectListener();
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
        String searchCity = this.cityName.getValue();
        if (searchCity.equals("")) {
            searchCity = (String) VaadinSession.getCurrent().getAttribute("searched_city");
        }
        if (searchCity != null) this.cityName.setValue(searchCity);

        this.getCityList();
    }

    private void getCityList() {
        String searchCity = this.cityName.getValue().trim();
        this.locationService.loadLocationData(searchCity);

        if (this.locationService.getTotal() > 0) {
            add(paginationDiv);
            createPagination(this.locationService.getTotal());
        }
    }

    private void addCitListDiv(List<CityGeoCoding> cityGeoCodingList) {
        cityListDiv.removeAll();
        for (CityGeoCoding cityGeoCoding : cityGeoCodingList) {
            cityListDiv.add(createCard(cityGeoCoding));
        }
    }

    private void createPagination(Integer total) {
        int perPage = 10;
        int totalPage = (int) Math.ceil(total / perPage);
        List<Integer> range = IntStream.range(1, totalPage+1).boxed().collect(Collectors.toList());

        pageSelect.setLabel("Pages");
        pageSelect.setItems(range);
        pageSelect.setValue(1);
    }

    private void addPaginationSelectListener() {
        this.pageSelect.addValueChangeListener(e -> {
            Integer page = e.getValue();
            if (page != null) {
                this.addCitListDiv(this.locationService.getLocationByCityNamePage(page));
            }
        });
    }
}
