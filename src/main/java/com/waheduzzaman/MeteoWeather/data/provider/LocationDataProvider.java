package com.waheduzzaman.MeteoWeather.data.provider;

import com.vaadin.flow.data.provider.AbstractBackEndDataProvider;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.provider.QuerySortOrder;
import com.vaadin.flow.data.provider.SortDirection;
import com.waheduzzaman.MeteoWeather.data.dto.location.Location;
import com.waheduzzaman.MeteoWeather.data.filter.LocationFilter;
import com.waheduzzaman.MeteoWeather.service.AbstractLocationService;
import com.waheduzzaman.MeteoWeather.service.impl.LocationServiceImpl;
import org.apache.commons.lang3.StringUtils;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;


public class LocationDataProvider extends AbstractBackEndDataProvider<Location, LocationFilter> {
    final AbstractLocationService locationService;

    public LocationDataProvider(LocationServiceImpl locationService) {
        this.locationService = locationService;
    }

    @Override
    protected Stream<Location> fetchFromBackEnd(Query<Location, LocationFilter> query) {
        Stream<Location> stream = null;

        // Searching
        if (query.getFilter().isPresent() && StringUtils.isNotEmpty(query.getFilter().get().getSearchTerm())) {
            stream = locationService.getLocation(query.getFilter().get().getSearchTerm()).stream();
        }

        // Filtering
        if (stream != null && query.getFilter().isPresent()) {
            stream = stream.filter(location -> query.getFilter().get().test(location));
        }

        // Sorting
        if (stream != null && query.getSortOrders().size() > 0) {
            stream = stream.sorted(sortComparator(query.getSortOrders()));
        }

        // Pagination
        return stream != null ? stream.skip(query.getOffset()).limit(query.getLimit()) : Stream.empty();
    }

    @Override
    protected int sizeInBackEnd(Query<Location, LocationFilter> query) {
        return (int) fetchFromBackEnd(query).count();
    }

    private static Comparator<Location> sortComparator(List<QuerySortOrder> sortOrders) {
        return sortOrders.stream().map(sortOrder -> {
            Comparator<Location> comparator = locationFieldComparator(sortOrder.getSorted());

            if (sortOrder.getDirection() == SortDirection.DESCENDING) {
                comparator = comparator.reversed();
            }

            return comparator;
        }).reduce(Comparator::thenComparing).orElse((p1, p2) -> 0);
    }

    private static Comparator<Location> locationFieldComparator(String sorted) {
        if (sorted.equals("name")) {
            return Comparator.comparing(Location::getName);
        } else if (sorted.equals("country")) {
            return Comparator.comparing(Location::getCountry);
        }
        return (p1, p2) -> 0;
    }
}