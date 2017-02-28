package leon.homework.JavaBean;

/**
 * Created by mjhzds on 2017/2/6.
 */

public class Subject {

    private String subjectName;
    private int imageId;
    private String deadLine;
    private String subjectNum;

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public void setDeadLine(String deadLine) {
        this.deadLine = deadLine;
    }

    public void setSubjectNum(String subjectNum) {
        this.subjectNum = subjectNum;
    }

    public void setSubjectName(String subjectName) {

        this.subjectName = subjectName;
    }

    public Subject(String subjectName, int imageId, String deadLine, String subjectNum) {
        this.subjectName = subjectName;
        this.imageId = imageId;
        this.subjectNum = subjectNum;
        this.deadLine = deadLine;
    }

    public String getSubjectNum() {
        return subjectNum;
    }

    public String getDeadLine() {
        return deadLine;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public int getSubjectImage() {
        return imageId;
    }
}
