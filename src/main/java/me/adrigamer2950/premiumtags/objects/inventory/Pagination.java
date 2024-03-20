package me.adrigamer2950.premiumtags.objects.inventory;

import java.util.ArrayList;
import java.util.List;

public class Pagination<T> extends ArrayList<T> {

    private final int pageSize;

    public Pagination(int pageSize, List<T> objects) {
        this.pageSize = pageSize;
        addAll(objects);
    }

    public int totalPages() {
        return (int) Math.ceil((double) size() / pageSize);
    }

    public List<T> getPage(int page) {
        if(page < 0 || page >= totalPages()) throw new IndexOutOfBoundsException("Index: " + page + ", Size: " + totalPages());

        int max = (page * pageSize) + pageSize;

        if(max > size()) max = size();

        return this.subList(page * pageSize, max);
    }
}
