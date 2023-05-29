package com.example.application.views.homepage;

import com.example.application.dto.CityGeoCoding;
import com.example.application.services.LocationService;
import com.example.application.security.SecurityService;
import com.example.application.services.UserFavouritePlaceService;
import com.example.application.views.MainLayout;
import com.example.application.views.weatherdetail.WeatherDetail;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H5;
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

import java.util.ArrayList;
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
    private Button nextButton = new Button("Next");
    private Button prevButton = new Button("Previous");

    private List<Integer> pageRange = new ArrayList<>();
    private Integer currentPage = 1;

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

        HorizontalLayout paginationHorizontalLayout = new HorizontalLayout(pageSelect, prevButton, nextButton);
        paginationHorizontalLayout.setAlignItems(Alignment.END);
        paginationDiv.add(paginationHorizontalLayout);
        this.addPaginationSelectListener();
        this.addPaginationButtonClickListener();
    }

    private HorizontalLayout createCard(CityGeoCoding cityGeoCoding) {
        HorizontalLayout card = new HorizontalLayout();
        card.addClassName("card");
        card.setSpacing(false);
        card.getThemeList().add("spacing-s");

        VerticalLayout description = new VerticalLayout();
        description.addClassName("location-item");
        description.setSpacing(false);
        description.setPadding(false);

        HorizontalLayout header = new HorizontalLayout();
        header.addClassName("header");
        header.setSpacing(false);
        header.getThemeList().add("spacing-s");

        Span name = new Span(cityGeoCoding.getName());
        name.addClassName("name");
        Span date = new Span(cityGeoCoding.getCountry());
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
                Notification.show(msg, 5000, Notification.Position.TOP_CENTER);
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
                        "city", cityGeoCoding.getName(),
                        "lon", String.valueOf(cityGeoCoding.getLongitude()),
                        "lat", String.valueOf(cityGeoCoding.getLatitude()),
                        "tz", String.valueOf(cityGeoCoding.getTimezone())
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
        else {
            remove(paginationDiv);
            this.addCitListDiv(this.locationService.getLocationByCityNamePage(1));
            cityListDiv.add(new H5("Result Not Found"));
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
        int totalPage = (int) Math.ceil(((double)total / perPage));
        this.pageRange = IntStream.range(1, totalPage+1).boxed().collect(Collectors.toList());

        pageSelect.setLabel("Pages");
        pageSelect.setItems(this.pageRange);
        pageSelect.setValue(this.currentPage);
    }

    private void addPaginationSelectListener() {
        this.pageSelect.addValueChangeListener(e -> {
            Integer page = e.getValue();
            if (page != null) {
                this.currentPage = page;
                this.addCitListDiv(this.locationService.getLocationByCityNamePage(page));
            }
        });
    }

    private void addPaginationButtonClickListener() {
        this.nextButton.addClickListener(e -> {
            if (this.canNavigate(this.currentPage + 1)) {
                pageSelect.setValue(this.currentPage + 1);
            }
        });

        this.prevButton.addClickListener(e -> {
            if (this.canNavigate(this.currentPage - 1)) {
                pageSelect.setValue(this.currentPage -1);
            }
        });
    }

    private Boolean canNavigate(int page) {
        if (this.pageRange.size() > 0) {
            Integer min = this.pageRange.get(0);
            Integer max = this.pageRange.get(pageRange.size() - 1);

            return page >= min && page <= max;
        }
        return false;
    }
}
