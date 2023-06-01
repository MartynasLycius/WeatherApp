package com.example.application.views.grid;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.DomEvent;
import com.vaadin.flow.component.EventData;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.shared.Registration;

/**
 * Pagination component based on lit-pagination webcomponent.
 * 
 * @author tanzil
 *
 */

@Tag("lit-pagination")
@NpmPackage(value = "@polymer/paper-button", version = "^3.0.1")
@NpmPackage(value = "@polymer/iron-iconset-svg", version = "^3.0.1")
@NpmPackage(value = "@polymer/paper-icon-button", version = "^3.0.2")
@NpmPackage(value = "lit-element", version = "^3.2.2")
@NpmPackage(value = "lit-html", version = "2.4.0")
@JsModule("./lit-pagination.js")
public class LitPagination extends Component implements LitPaginationModel {

	public LitPagination() {
		this.setTotal(2);
		setPageSize(1);
		setSize(2);
	}

	public int getPageSize() {
		return getLimit();
	}

	/**
	 * Sets the page size, that is number of items a page could display.
	 * 
	 * @param pageSize
	 */
	public void setPageSize(int pageSize) {
		setLimit(pageSize);
	}

	/**
	 * Sets the count of the pages displayed before or after the current page.
	 * 
	 * @param size
	 */
	public void setPaginatorSize(int size) {
		setSize(size);
		setPages(size);
	}

	public void refresh() {
		this.getElement().executeJs("$0.observePageCount($1,$2,$3)", this, this.getPage(), this.getPageSize(),
				this.getTotal());
	}

	protected Registration addPageChangeListener(ComponentEventListener<PageChangeEvent> listener) {
		return super.addListener(PageChangeEvent.class, listener);
	}

	@DomEvent("page-change")
	public static class PageChangeEvent extends ComponentEvent<LitPagination> {
		private final int newPage;
		private final int oldPage;

		public PageChangeEvent(LitPagination source, boolean fromClient, @EventData("event.detail.newPage") int newPage,
				@EventData("event.detail.oldPage") int oldPage) {
			super(source, fromClient);
			this.newPage = newPage;
			this.oldPage = oldPage;
		}

		/**
		 * Returns the new selected page.
		 * 
		 * @return based 1 index of the selected page
		 */
		public int getNewPage() {
			return newPage;
		}

		/**
		 * Returns the previously selected page before it was changed.
		 * 
		 * @return based 1 index of the previously selected page
		 */
		public int getOldPage() {
			return oldPage;
		}
	}
}