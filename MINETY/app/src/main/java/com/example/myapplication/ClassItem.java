package com.example.myapplication;

public class ClassItem {
    private String className;
    private String subjectName;
    private long cid;
    public long getCid()
    {
        return cid;
    }

    public void setCid(long cid)
    {
        this.cid=cid;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public ClassItem(String className, String subjectName) {
        this.className = className;
        this.subjectName = subjectName;
    }

    public ClassItem(String className, String subjectName, long cid)
    {
        this.className = className;
        this.subjectName = subjectName;
        this.cid=cid;
    }
}
