/*
 * CadastroWrapper.java
 *
 */

package com.tetrasoft.web.basico;

public class CadastroWrapper {

    private long numRows = -1;
    private long currentPage = 1;
    private long currentRow = 1;
    private long numRecordsPerPage = 25;

    /** Creates a new instance of CadastroWrapper */
    public CadastroWrapper() {
    }

    public String moveFirstEnabled() {
        if (currentPage==1)
            return "0";
        return "1";
    }
    public String moveLastEnabled() {
        if (currentPage*numRecordsPerPage<numRows) return "1";
        return "0";
    }

    public long getNumRows() {
        return numRows;
    }

    public void setNumRows(long value) {
        this.numRows=value;
    }

    public long getNumPages() {
        return ((long)Math.ceil((double)numRows/numRecordsPerPage));
    }

    public long getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(long value) {
        this.currentPage=value;
    }

    public long getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(long value) {
        this.currentRow=value;
    }

    public long getNumRecordsPerPage() {
        return numRecordsPerPage;
    }

    public void setNumRecordsPerPage(long value) {
        this.numRecordsPerPage=value;
    }

    public void moveNextPage() {
        currentRow+=numRecordsPerPage;
        currentPage = ((long)Math.ceil((double)currentRow/numRecordsPerPage));
    }
    public void movePrevPage() {
        currentRow-=numRecordsPerPage;
        currentPage = ((long)Math.ceil((double)currentRow/numRecordsPerPage));
    }
    public void moveFirstPage() {
        currentRow=1;
        currentPage=1;
    }
    public void moveLastPage() {
        currentPage=getNumPages();
        currentRow = (currentPage-1)*numRecordsPerPage+1;
    }

    public void reset() {
        numRows=-1;
        currentRow=1;
        currentPage=1;
    }

}
