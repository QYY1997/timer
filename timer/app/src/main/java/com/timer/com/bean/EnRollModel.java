package com.timer.com.bean;

/**
 * @author : qiuyiyang
 * @date : 2022/2/12  3:11
 * @desc :
 */
public class EnRollModel {

    /**
     * dateCreated : 1557213408000
     * demand : {"cls":"二年级","grade":"小学","school":"b","title":"100米","type":"田径运动"}
     * id : 5cd130e05b153aeafe85eebb
     * record : 10秒
     * road : 4-5
     * score : 80
     * studentName : e
     */

    private long dateCreated;
    private DemandBean demand;
    private String id;
    private String record;
    private String road;
    private int score;
    private String studentName;
    /**
     * cls : 1班
     * editAble : 1
     */

    private String cls;
    private int editAble;

    public long getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(long dateCreated) {
        this.dateCreated = dateCreated;
    }

    public DemandBean getDemand() {
        return demand;
    }

    public void setDemand(DemandBean demand) {
        this.demand = demand;
    }

    public String getId() {
        return id == null ? "" : id;
    }

    public void setId(String id) {
        this.id = (id == null ? "" : id);
    }

    public String getRecord() {
        return record == null ? "" : record;
    }

    public void setRecord(String record) {
        this.record = (record == null ? "" : record);
    }

    public String getRoad() {
        return road == null ? "" : road;
    }

    public void setRoad(String road) {
        this.road = (road == null ? "" : road);
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getStudentName() {
        return studentName == null ? "" : studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = (studentName == null ? "" : studentName);
    }

    public String getCls() {
        return cls == null ? "" : cls;
    }

    public void setCls(String cls) {
        this.cls = (cls == null ? "" : cls);
    }

    public int getEditAble() {
        return editAble;
    }

    public void setEditAble(int editAble) {
        this.editAble = editAble;
    }

    public static class DemandBean {
        /**
         * cls : 二年级
         * grade : 小学
         * school : b
         * title : 100米
         * type : 田径运动
         */

        private String cls;
        private String grade;
        private String school;
        private String title;
        private String type;

        public String getCls() {
            return cls == null ? "" : cls;
        }

        public void setCls(String cls) {
            this.cls = (cls == null ? "" : cls);
        }

        public String getGrade() {
            return grade == null ? "" : grade;
        }

        public void setGrade(String grade) {
            this.grade = (grade == null ? "" : grade);
        }

        public String getSchool() {
            return school == null ? "" : school;
        }

        public void setSchool(String school) {
            this.school = (school == null ? "" : school);
        }

        public String getTitle() {
            return title == null ? "" : title;
        }

        public void setTitle(String title) {
            this.title = (title == null ? "" : title);
        }

        public String getType() {
            return type == null ? "" : type;
        }

        public void setType(String type) {
            this.type = (type == null ? "" : type);
        }
    }
}
