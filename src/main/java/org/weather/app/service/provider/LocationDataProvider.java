package org.weather.app.service.provider;

import com.vaadin.flow.data.provider.AbstractBackEndDataProvider;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.provider.QuerySortOrder;
import com.vaadin.flow.data.provider.SortDirection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import org.apache.commons.lang3.StringUtils;
import org.weather.app.config.Constants;
import org.weather.app.service.LocationService;
import org.weather.app.service.dto.Location;
import org.weather.app.service.dto.SearchFilter;

public class LocationDataProvider extends AbstractBackEndDataProvider<Location, SearchFilter> {

  final LocationService locationService;

  public LocationDataProvider(LocationService locationService) {
    this.locationService = locationService;
  }

  @Override
  protected int sizeInBackEnd(Query<Location, SearchFilter> query) {
    return (int) fetchFromBackEnd(query).count();
  }

  @Override
  protected Stream<Location> fetchFromBackEnd(Query<Location, SearchFilter> query) {
    Stream<Location> streamOfLocations = null;

    // no location list will be available without searching
    if (query.getFilter().isPresent()
        && StringUtils.isNotEmpty(query.getFilter().get().getSearchTerm())) {
      streamOfLocations =
          locationService.findByLocationName(query.getFilter().get().getSearchTerm()).stream();
    }

    // apply filter
    if (Objects.nonNull(streamOfLocations) && query.getFilter().isPresent()) {
      streamOfLocations =
          streamOfLocations.filter(location -> query.getFilter().get().check(location));
    }

    // apply sorting
    if (Objects.nonNull(streamOfLocations) && query.getSortOrders().size() > 0) {
      streamOfLocations = streamOfLocations.sorted(locationSortComparator(query.getSortOrders()));
    }

    // finally, doing pagination
    return Objects.nonNull(streamOfLocations)
        ? streamOfLocations.skip(query.getOffset()).limit(query.getLimit())
        : Stream.empty();
  }

  private static Comparator<Location> locationSortComparator(List<QuerySortOrder> sortOrders) {
    return sortOrders.stream()
        .map(
            sortOrder -> {
              Comparator<Location> comparator = locationSortedBy(sortOrder.getSorted());
              if (sortOrder.getDirection().equals(SortDirection.DESCENDING)) {
                comparator = comparator.reversed();
              }
              return comparator;
            })
        .reduce(Comparator::thenComparing)
        .orElse((p1, p2) -> 0);
  }

  private static Comparator<Location> locationSortedBy(String sortedBy) {
    if (sortedBy.equals(Constants.FILED_NAME)) {
      return Comparator.comparing(Location::getName);
    } else if (sortedBy.equals(Constants.FILED_COUNTRY)) {
      return Comparator.comparing(Location::getCountry);
    }
    return (p1, p2) -> 0;
  }
}
