package com.predictor.galaxy;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.predictor.library.utils.CNCommonAdapter;
import com.predictor.library.utils.CNCommonViewHolder;

import java.util.List;

public class TestAdapter {
    public class Report {
        public Report(int reportId, String reportContent) {
            this.reportId = reportId;
            this.reportContent = reportContent;
        }

        int reportId;
        String reportContent;

        public int getReportId() {
            return reportId;
        }

        public void setReportId(int reportId) {
            this.reportId = reportId;
        }

        public String getReportContent() {
            return reportContent;
        }

        public void setReportContent(String reportContent) {
            this.reportContent = reportContent;
        }
    }

    public class ReportAdapter extends CNCommonAdapter<Report> {

        public ReportAdapter(Context context, List<Report> data) {
            super(context, data);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            CNCommonViewHolder viewHolder = CNCommonViewHolder.get(mContext, convertView, parent,
                    R.layout.row_report, position);
            TextView mReportTv = viewHolder.getView(R.id.report_tv);
            mReportTv.setText(data.get(position).getReportContent());
            return viewHolder.getConvertView();
        }
    }
}
