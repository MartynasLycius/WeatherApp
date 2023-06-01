package com.example.application.views.grid;

import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.Synchronize;

/**
 * Utility interface used to define lit-pagination properties
 */
public interface LitPaginationModel extends HasElement {

	@Synchronize("page-change")
	default int getPage() {
		return Integer.valueOf(getElement().getProperty("page", "0"));
	}

	default void setPage(int page) {
		getElement().setProperty("page", Integer.toString(page));
	}

	/**
	 * Returns the total number of items.
	 *
	 * @return total
	 */
	@Synchronize("change")
	default int getTotal() {
		return Integer.valueOf(getElement().getProperty("total", "0"));
	}

	/**
	 * Sets the total number of items from which number of pages gets calculated.
	 *
	 * @param total
	 */
	default void setTotal(int total) {
		getElement().setProperty("total", Integer.toString(total));
	}

	/**
	 * Sets the max number of items a page should display in order to calculated the
	 * number of pages.
	 *
	 * @param limit
	 */
	default void setLimit(int limit) {
		getElement().setProperty("limit", Integer.toString(limit));
	}

	/**
	 * Gets the actual number of items set to be displayed on for each page.
	 *
	 * @return
	 */
	@Synchronize("change")
	default int getLimit() {
		return Integer.valueOf(getElement().getProperty("limit", "0"));
	}

	/**
	 * Returns the selected page on the paginator.
	 *
	 * @return page
	 */
	@Synchronize("page-change")
	default int getPages() {
		return Integer.valueOf(getElement().getProperty("pages", "0"));
	}

	default void setPages(int size) {
		getElement().setProperty("pages", Integer.toString(size));
	}

	default void setSize(int size) {
		getElement().setProperty("size", Integer.toString(size));
	}

	/**
	 * Sets the text to display for the `Page` term in the Paginator.
	 *
	 * @param pageText
	 */
	default void setPageText(String pageText) {
		getElement().setProperty("pageText", pageText);
	}

	/**
	 * Sets the text to display for the `of` term in the Paginator.
	 *
	 * @param ofText
	 */
	default void setOfText(String ofText) {
		getElement().setProperty("ofText", ofText);
	}

}