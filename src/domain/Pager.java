package domain;

public class Pager {
	private int totalCnt;
	private int perPageNum; //페이지당 글의 수
	private int perChapterNum; //챕터당 페이지 수
	private int startPage; //시작 페이지
	private int endPage; //종료 페이지
	private int nowPage; //현재 보고자 하는 페이지
	
	private int totalPage; //전체 페이지수
	
	private boolean next; //이전
	private boolean prev; //다음
	
	public Pager(){
		nowPage = 1; //기본값으로 1
		perPageNum = 10; //기본값을 10
		perChapterNum = 5; //기본값을 5
	}
	
	//calc함수는 totalcnt와 nowPage가 설정된 이후에 실행함.
	public void calc(){
		totalPage = (int)Math.ceil( totalCnt / (double)perPageNum);
		endPage = (int)Math.ceil(nowPage / (double)perChapterNum ) * perChapterNum; //엔드 페이지 구하고
		startPage = endPage - perChapterNum + 1; //end를 이용해서 start를 구함.
		prev = true;
		next = true;
		if(totalPage < endPage){
			endPage = totalPage; //전체 페이지 수가 더 적다면 endPage 조정
			next = false;
		}
		if(startPage == 1){
			prev = false;
		}
	}
	
	//겟터와 셋터들
	public int getTotalCnt() {
		return totalCnt;
	}
	
	public void setTotalCnt(int totalCnt) {
		this.totalCnt = totalCnt;
	}
	public int getPerPageNum() {
		return perPageNum;
	}
	public void setPerPageNum(int perPageNum) {
		this.perPageNum = perPageNum;
	}
	public int getPerChapterNum() {
		return perChapterNum;
	}
	public void setPerChapterNum(int perChapterNum) {
		this.perChapterNum = perChapterNum;
	}
	public int getStartPage() {
		return startPage;
	}
	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}
	public int getEndPage() {
		return endPage;
	}
	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public int getNowPage() {
		return nowPage;
	}

	public void setNowPage(int nowPage) {
		this.nowPage = nowPage;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public boolean isNext() {
		return next;
	}

	public void setNext(boolean next) {
		this.next = next;
	}

	public boolean isPrev() {
		return prev;
	}

	public void setPrev(boolean prev) {
		this.prev = prev;
	}

	@Override
	public String toString() {
		return "Pager [totalCnt=" + totalCnt + ", perPageNum=" + perPageNum + ", perChapterNum=" + perChapterNum
				+ ", startPage=" + startPage + ", endPage=" + endPage + ", nowPage=" + nowPage + ", totalPage="
				+ totalPage + ", next=" + next + ", prev=" + prev + "]";
	}
	
}
