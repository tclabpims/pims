package com.pims.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by king on 2016/9/28.
 */
@Entity
@Table(name = "PIMS_PATHOLOGY_REPORT_PDF")
public class PimsPathologyReportPdf {
    private long pdffileid;
    private long pdfsampleid;
    private String pdfpathologycode;
    private String pdffilename;
    private String pdffilesize;
    private String pdffilesavepath;
    private Date pdfuploadtime;
    private String pdfuploaduser;
    private String pdfuploadip;
    private Long pdfuploadtimes;
    private Date pdflastuploadtime;
    private String pdflastuploaduser;
    private String pdffirstv;
    private Long pdffirstn;
    private Date pdffirstd;
    private Date pdfcreatetime;
    private String pdfcreateuser;

    @Id
    @Column(name = "PDFFILEID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="SEQ_PDFFILEID")
    @SequenceGenerator(name = "SEQ_PDFFILEID", sequenceName = "SEQ_PDFFILEID", allocationSize=1)
    public long getPdffileid() {
        return pdffileid;
    }

    public void setPdffileid(long pdffileid) {
        this.pdffileid = pdffileid;
    }

    @Basic
    @Column(name = "PDFSAMPLEID")
    public long getPdfsampleid() {
        return pdfsampleid;
    }

    public void setPdfsampleid(long pdfsampleid) {
        this.pdfsampleid = pdfsampleid;
    }

    @Basic
    @Column(name = "PDFPATHOLOGYCODE")
    public String getPdfpathologycode() {
        return pdfpathologycode;
    }

    public void setPdfpathologycode(String pdfpathologycode) {
        this.pdfpathologycode = pdfpathologycode;
    }

    @Basic
    @Column(name = "PDFFILENAME")
    public String getPdffilename() {
        return pdffilename;
    }

    public void setPdffilename(String pdffilename) {
        this.pdffilename = pdffilename;
    }

    @Basic
    @Column(name = "PDFFILESIZE")
    public String getPdffilesize() {
        return pdffilesize;
    }

    public void setPdffilesize(String pdffilesize) {
        this.pdffilesize = pdffilesize;
    }

    @Basic
    @Column(name = "PDFFILESAVEPATH")
    public String getPdffilesavepath() {
        return pdffilesavepath;
    }

    public void setPdffilesavepath(String pdffilesavepath) {
        this.pdffilesavepath = pdffilesavepath;
    }

    @Basic
    @Column(name = "PDFUPLOADTIME")
    public Date getPdfuploadtime() {
        return pdfuploadtime;
    }

    public void setPdfuploadtime(Date pdfuploadtime) {
        this.pdfuploadtime = pdfuploadtime;
    }

    @Basic
    @Column(name = "PDFUPLOADUSER")
    public String getPdfuploaduser() {
        return pdfuploaduser;
    }

    public void setPdfuploaduser(String pdfuploaduser) {
        this.pdfuploaduser = pdfuploaduser;
    }

    @Basic
    @Column(name = "PDFUPLOADIP")
    public String getPdfuploadip() {
        return pdfuploadip;
    }

    public void setPdfuploadip(String pdfuploadip) {
        this.pdfuploadip = pdfuploadip;
    }

    @Basic
    @Column(name = "PDFUPLOADTIMES")
    public Long getPdfuploadtimes() {
        return pdfuploadtimes;
    }

    public void setPdfuploadtimes(Long pdfuploadtimes) {
        this.pdfuploadtimes = pdfuploadtimes;
    }

    @Basic
    @Column(name = "PDFLASTUPLOADTIME")
    public Date getPdflastuploadtime() {
        return pdflastuploadtime;
    }

    public void setPdflastuploadtime(Date pdflastuploadtime) {
        this.pdflastuploadtime = pdflastuploadtime;
    }

    @Basic
    @Column(name = "PDFLASTUPLOADUSER")
    public String getPdflastuploaduser() {
        return pdflastuploaduser;
    }

    public void setPdflastuploaduser(String pdflastuploaduser) {
        this.pdflastuploaduser = pdflastuploaduser;
    }

    @Basic
    @Column(name = "PDFFIRSTV")
    public String getPdffirstv() {
        return pdffirstv;
    }

    public void setPdffirstv(String pdffirstv) {
        this.pdffirstv = pdffirstv;
    }

    @Basic
    @Column(name = "PDFFIRSTN")
    public Long getPdffirstn() {
        return pdffirstn;
    }

    public void setPdffirstn(Long pdffirstn) {
        this.pdffirstn = pdffirstn;
    }

    @Basic
    @Column(name = "PDFFIRSTD")
    public Date getPdffirstd() {
        return pdffirstd;
    }

    public void setPdffirstd(Date pdffirstd) {
        this.pdffirstd = pdffirstd;
    }

    @Basic
    @Column(name = "PDFCREATETIME")
    public Date getPdfcreatetime() {
        return pdfcreatetime;
    }

    public void setPdfcreatetime(Date pdfcreatetime) {
        this.pdfcreatetime = pdfcreatetime;
    }

    @Basic
    @Column(name = "PDFCREATEUSER")
    public String getPdfcreateuser() {
        return pdfcreateuser;
    }

    public void setPdfcreateuser(String pdfcreateuser) {
        this.pdfcreateuser = pdfcreateuser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PimsPathologyReportPdf that = (PimsPathologyReportPdf) o;

        if (pdffileid != that.pdffileid) return false;
        if (pdfsampleid != that.pdfsampleid) return false;
        if (pdfpathologycode != null ? !pdfpathologycode.equals(that.pdfpathologycode) : that.pdfpathologycode != null)
            return false;
        if (pdffilename != null ? !pdffilename.equals(that.pdffilename) : that.pdffilename != null) return false;
        if (pdffilesize != null ? !pdffilesize.equals(that.pdffilesize) : that.pdffilesize != null) return false;
        if (pdffilesavepath != null ? !pdffilesavepath.equals(that.pdffilesavepath) : that.pdffilesavepath != null)
            return false;
        if (pdfuploadtime != null ? !pdfuploadtime.equals(that.pdfuploadtime) : that.pdfuploadtime != null)
            return false;
        if (pdfuploaduser != null ? !pdfuploaduser.equals(that.pdfuploaduser) : that.pdfuploaduser != null)
            return false;
        if (pdfuploadip != null ? !pdfuploadip.equals(that.pdfuploadip) : that.pdfuploadip != null) return false;
        if (pdfuploadtimes != null ? !pdfuploadtimes.equals(that.pdfuploadtimes) : that.pdfuploadtimes != null)
            return false;
        if (pdflastuploadtime != null ? !pdflastuploadtime.equals(that.pdflastuploadtime) : that.pdflastuploadtime != null)
            return false;
        if (pdflastuploaduser != null ? !pdflastuploaduser.equals(that.pdflastuploaduser) : that.pdflastuploaduser != null)
            return false;
        if (pdffirstv != null ? !pdffirstv.equals(that.pdffirstv) : that.pdffirstv != null) return false;
        if (pdffirstn != null ? !pdffirstn.equals(that.pdffirstn) : that.pdffirstn != null) return false;
        if (pdffirstd != null ? !pdffirstd.equals(that.pdffirstd) : that.pdffirstd != null) return false;
        if (pdfcreatetime != null ? !pdfcreatetime.equals(that.pdfcreatetime) : that.pdfcreatetime != null)
            return false;
        if (pdfcreateuser != null ? !pdfcreateuser.equals(that.pdfcreateuser) : that.pdfcreateuser != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (pdffileid ^ (pdffileid >>> 32));
        result = 31 * result + (int) (pdfsampleid ^ (pdfsampleid >>> 32));
        result = 31 * result + (pdfpathologycode != null ? pdfpathologycode.hashCode() : 0);
        result = 31 * result + (pdffilename != null ? pdffilename.hashCode() : 0);
        result = 31 * result + (pdffilesize != null ? pdffilesize.hashCode() : 0);
        result = 31 * result + (pdffilesavepath != null ? pdffilesavepath.hashCode() : 0);
        result = 31 * result + (pdfuploadtime != null ? pdfuploadtime.hashCode() : 0);
        result = 31 * result + (pdfuploaduser != null ? pdfuploaduser.hashCode() : 0);
        result = 31 * result + (pdfuploadip != null ? pdfuploadip.hashCode() : 0);
        result = 31 * result + (pdfuploadtimes != null ? pdfuploadtimes.hashCode() : 0);
        result = 31 * result + (pdflastuploadtime != null ? pdflastuploadtime.hashCode() : 0);
        result = 31 * result + (pdflastuploaduser != null ? pdflastuploaduser.hashCode() : 0);
        result = 31 * result + (pdffirstv != null ? pdffirstv.hashCode() : 0);
        result = 31 * result + (pdffirstn != null ? pdffirstn.hashCode() : 0);
        result = 31 * result + (pdffirstd != null ? pdffirstd.hashCode() : 0);
        result = 31 * result + (pdfcreatetime != null ? pdfcreatetime.hashCode() : 0);
        result = 31 * result + (pdfcreateuser != null ? pdfcreateuser.hashCode() : 0);
        return result;
    }
}
