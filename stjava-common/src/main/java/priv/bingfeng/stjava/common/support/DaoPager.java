package priv.bingfeng.stjava.common.support;

public class DaoPager {

	private int pageNo;
	private int pageSize;
	private int totalCount;
	
	private int pageCount;
	
	public DaoPager() {}
	public DaoPager(int pageSize, int totalCount) {
		this.init(1, pageSize, totalCount);
	}
	public DaoPager(int pageNo, int pageSize, int totalCount) {
		this.init(pageNo, pageSize, totalCount);
	}
	private void init(int pageNo, int pageSize, int totalCount) {
		if (pageNo < 1 || pageSize < 1)
			throw new IllegalArgumentException("pageNo与pageSize必须大于0");
		
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.totalCount = totalCount;
		
		pageCount = totalCount / pageSize;
		if (totalCount % pageSize > 0)
			pageCount++;
	}

	public int getStartRow() {
		return (pageNo - 1) * pageSize;
	}

	public int getEndRow() {
		return Math.min(pageNo * pageSize, totalCount);
	}
	
	public boolean next() {
		return ++pageNo <= pageCount;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getPageNo() {
		return pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public int getTotalCount() {
		return totalCount;
	}

}
