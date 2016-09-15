package xyz.truehrms.parameters;

public class Parameters {
    private String employee_id, month, year, searchInput, PageSize, PageIndex;

    public void setEmployeeID(String empcode) {
        this.employee_id = empcode;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setSearchInput(String searchInput) {
        this.searchInput = searchInput;
    }

    public void setPageIndex(String pageIndex) {
        PageIndex = pageIndex;
    }

    public void setPageSize(String pageSize) {
        PageSize = pageSize;
    }
}
