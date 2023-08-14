package com.eastnetic.application.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class PaginationComponent extends HorizontalLayout {

    private final Span pageField = new Span();
    private final Button nextButton = new Button("Next");
    private final Button prevButton = new Button("Previous");

    private int totalPages = 1;
    private int currentPage = 1;

    private final PaginationListener paginationListener;

    public PaginationComponent(PaginationListener paginationListener) {
        this.paginationListener = paginationListener;

        prevButton.addClickListener(e -> showPage(currentPage - 1));
        nextButton.addClickListener(e -> showPage(currentPage + 1));

        add(prevButton, pageField, nextButton);
        setAlignItems(FlexComponent.Alignment.CENTER);

        setVisible(false);

        updateButtons();
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
        updateButtons();
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
        updateButtons();
    }

    public int getPageSize() {
        return 10;
    }

    public void showPage(int page) {
        paginationListener.onPageChange(page);
    }

    protected void updateButtons() {
        prevButton.setEnabled(currentPage > 1);
        nextButton.setEnabled(currentPage < totalPages);
        pageField.setText("Page " + currentPage + " of " + totalPages);
    }

    public interface PaginationListener {
        void onPageChange(int page);
    }
}

