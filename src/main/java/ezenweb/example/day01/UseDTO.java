package ezenweb.example.day01;

public class UseDTO {
    //1. 필드
    private int mno;
    private String mid;
    private String mpw;
    private long point;
    private String phone;

    //오른쪽 클릭 -> generate -> 자동생성
    //2. 생성자 [1. 빈생성자 2. 풀생성자]

    //1) 빈생성자
    public UseDTO () {}

    //2) 풀생성자
    public UseDTO (int mno, String mid, String mpw, long point, String phone) {
        this.mno = mno;
        this.mid = mid;
        this.mpw = mpw;
        this.point = point;
        this.phone = phone;
    }

    //3. toString()
    @Override
    public String toString() {
        return "일반DTO{" +
                "mno=" + mno +
                ", mid='" + mid + '\'' +
                ", mpw='" + mpw + '\'' +
                ", point=" + point +
                ", phone='" + phone + '\'' +
                '}';
    }

    //4. getter & setter
    public int getMno() {
        return mno;
    }

    public void setMno(int mno) {
        this.mno = mno;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getMpw() {
        return mpw;
    }

    public void setMpw(String mpw) {
        this.mpw = mpw;
    }

    public long getPoint() {
        return point;
    }

    public void setPoint(long point) {
        this.point = point;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}