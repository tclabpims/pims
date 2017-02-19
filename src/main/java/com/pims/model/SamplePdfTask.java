package com.pims.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by king on 2017/2/10.
 */
@Entity
@Table(name = "SAMPLE_PDF_TASK")
public class SamplePdfTask {
    private long taskid;
    private long tasksampleid;
    private int taskstates;
    private String taskurl;
    private String taskresult;
    private Date taskcreatetime;
    private String taskcreateuser;
    private String taskfirstv;
    private String tasksecondv;
    private String taskthirdv;
    private Date taskfirstd;
    private Date tasksecondd;
    private int taskfirstn;

    @Id
    @Column(name = "TASKID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="SAMPLETASK")
    @SequenceGenerator(name = "SAMPLETASK", sequenceName = "SAMPLETASK", allocationSize=1)
    public long getTaskid() {
        return taskid;
    }

    public void setTaskid(long taskid) {
        this.taskid = taskid;
    }

    @Basic
    @Column(name = "TASKSAMPLEID")
    public long getTasksampleid() {
        return tasksampleid;
    }

    public void setTasksampleid(long tasksampleid) {
        this.tasksampleid = tasksampleid;
    }

    @Basic
    @Column(name = "TASKSTATES")
    public int getTaskstates() {
        return taskstates;
    }

    public void setTaskstates(int taskstates) {
        this.taskstates = taskstates;
    }

    @Basic
    @Column(name = "TASKURL")
    public String getTaskurl() {
        return taskurl;
    }

    public void setTaskurl(String taskurl) {
        this.taskurl = taskurl;
    }

    @Basic
    @Column(name = "TASKRESULT")
    public String getTaskresult() {
        return taskresult;
    }

    public void setTaskresult(String taskresult) {
        this.taskresult = taskresult;
    }

    @Basic
    @Column(name = "TASKCREATETIME")
    public Date getTaskcreatetime() {
        return taskcreatetime;
    }

    public void setTaskcreatetime(Date taskcreatetime) {
        this.taskcreatetime = taskcreatetime;
    }

    @Basic
    @Column(name = "TASKCREATEUSER")
    public String getTaskcreateuser() {
        return taskcreateuser;
    }

    public void setTaskcreateuser(String taskcreateuser) {
        this.taskcreateuser = taskcreateuser;
    }

    @Basic
    @Column(name = "TASKFIRSTV")
    public String getTaskfirstv() {
        return taskfirstv;
    }

    public void setTaskfirstv(String taskfirstv) {
        this.taskfirstv = taskfirstv;
    }

    @Basic
    @Column(name = "TASKSECONDV")
    public String getTasksecondv() {
        return tasksecondv;
    }

    public void setTasksecondv(String tasksecondv) {
        this.tasksecondv = tasksecondv;
    }

    @Basic
    @Column(name = "TASKTHIRDV")
    public String getTaskthirdv() {
        return taskthirdv;
    }

    public void setTaskthirdv(String taskthirdv) {
        this.taskthirdv = taskthirdv;
    }

    @Basic
    @Column(name = "TASKFIRSTD")
    public Date getTaskfirstd() {
        return taskfirstd;
    }

    public void setTaskfirstd(Date taskfirstd) {
        this.taskfirstd = taskfirstd;
    }

    @Basic
    @Column(name = "TASKSECONDD")
    public Date getTasksecondd() {
        return tasksecondd;
    }

    public void setTasksecondd(Date tasksecondd) {
        this.tasksecondd = tasksecondd;
    }

    @Basic
    @Column(name = "TASKFIRSTN")
    public int getTaskfirstn() {
        return taskfirstn;
    }

    public void setTaskfirstn(int taskfirstn) {
        this.taskfirstn = taskfirstn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SamplePdfTask that = (SamplePdfTask) o;

        if (taskid != that.taskid) return false;
        if (tasksampleid != that.tasksampleid) return false;
        if (taskstates != that.taskstates) return false;
        if (taskurl != null ? !taskurl.equals(that.taskurl) : that.taskurl != null) return false;
        if (taskresult != null ? !taskresult.equals(that.taskresult) : that.taskresult != null) return false;
        if (taskcreatetime != null ? !taskcreatetime.equals(that.taskcreatetime) : that.taskcreatetime != null)
            return false;
        if (taskcreateuser != null ? !taskcreateuser.equals(that.taskcreateuser) : that.taskcreateuser != null)
            return false;
        if (taskfirstv != null ? !taskfirstv.equals(that.taskfirstv) : that.taskfirstv != null) return false;
        if (tasksecondv != null ? !tasksecondv.equals(that.tasksecondv) : that.tasksecondv != null) return false;
        if (taskthirdv != null ? !taskthirdv.equals(that.taskthirdv) : that.taskthirdv != null) return false;
        if (taskfirstd != null ? !taskfirstd.equals(that.taskfirstd) : that.taskfirstd != null) return false;
        if (tasksecondd != null ? !tasksecondd.equals(that.tasksecondd) : that.tasksecondd != null) return false;
        if (taskfirstn != that.taskfirstn) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (taskid ^ (taskid >>> 32));
        result = 31 * result + (int) (tasksampleid ^ (tasksampleid >>> 32));
        result = 31 * result + (taskstates ^ (taskstates >>> 32));
        result = 31 * result + (taskurl != null ? taskurl.hashCode() : 0);
        result = 31 * result + (taskresult != null ? taskresult.hashCode() : 0);
        result = 31 * result + (taskcreatetime != null ? taskcreatetime.hashCode() : 0);
        result = 31 * result + (taskcreateuser != null ? taskcreateuser.hashCode() : 0);
        result = 31 * result + (taskfirstv != null ? taskfirstv.hashCode() : 0);
        result = 31 * result + (tasksecondv != null ? tasksecondv.hashCode() : 0);
        result = 31 * result + (taskthirdv != null ? taskthirdv.hashCode() : 0);
        result = 31 * result + (taskfirstd != null ? taskfirstd.hashCode() : 0);
        result = 31 * result + (tasksecondd != null ? tasksecondd.hashCode() : 0);
        result = 31 * result + (taskfirstn ^ (taskfirstn >>> 32));
        return result;
    }
}
