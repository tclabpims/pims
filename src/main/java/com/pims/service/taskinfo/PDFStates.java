package com.pims.service.taskinfo;

import com.pims.service.UpdateReportDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by king on 2017/1/11.
 */
@Service("updatepdfstates")
public class PDFStates {

    @Autowired
    private UpdateReportDataService updateReportDataService;

    public void run(){
        updateReportDataService.updatepdf();
    }

}
