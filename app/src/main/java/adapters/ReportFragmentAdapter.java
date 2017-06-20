package adapters;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hs.userportal.CaseCodeModel;
import com.hs.userportal.OrderDetailsModel;
import com.hs.userportal.R;
import com.hs.userportal.TestNames;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rishabh on 16/6/17.
 */

public class ReportFragmentAdapter extends RecyclerView.Adapter<ReportFragmentAdapter.MyViewHolder> {

    private List<CaseCodeModel> adapterCaseCodeObjectList = new ArrayList<>();
    private List<Object> listofAllObjects = new ArrayList<>() ;
    private Context context;
    private TestListAdapter testListAdapter;
    private RecyclerView recyclerViewTestList;
    private RecyclerView.LayoutManager layoutManager;
    private List<TestNames> testNamesArrayList = new ArrayList<>();
    private TestListAdapter.OnRowTouchAction listener ;
    private OrderListAdapter orderListAdapter ;
    private OnPdfTouch onPdfTouchListener ;


    public ReportFragmentAdapter(Context context, List<Object> list, TestListAdapter.OnRowTouchAction listener, OnPdfTouch onPdfTouchListener) {

        this.context = context;
        this.listener = listener ;
      listofAllObjects = list ;
        this.onPdfTouchListener = onPdfTouchListener ;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reportfragment_single_row, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {



        layoutManager = new LinearLayoutManager(context);


        if(listofAllObjects.get(position) instanceof CaseCodeModel) {

            holder.tvLocationName.setText(((CaseCodeModel) listofAllObjects.get(position)).getLocationName());
            holder.tvAdviceDate.setText(((CaseCodeModel) listofAllObjects.get(position)).getDateandTime());
            holder.tvReferredBy.setText(((CaseCodeModel) listofAllObjects.get(position)).getReferrerName());
            holder.tvCaseCode.setText(((CaseCodeModel) listofAllObjects.get(position)).getCaseCode());
            holder.lvTestList.setHasFixedSize(true);
            holder.lvTestList.setLayoutManager(layoutManager);
            testNamesArrayList = ((CaseCodeModel) listofAllObjects.get(position)).getListOfTestNamesInCaseCode()  ;
            testListAdapter = new TestListAdapter(testNamesArrayList , listener , ((CaseCodeModel) listofAllObjects.get(position)).getCaseID() );
            holder.lvTestList.setAdapter(testListAdapter);

            holder.llPDF.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onPdfTouchListener.onPdfTouch(((CaseCodeModel) listofAllObjects.get(position)));
                }
            });
        }else if (listofAllObjects.get(position) instanceof OrderDetailsModel) {


            holder.tvLocationName.setText(((OrderDetailsModel) listofAllObjects.get(position)).getCentreName());
            holder.tvAdviceDate.setText(((OrderDetailsModel) listofAllObjects.get(position)).getOrderDateTime());
            holder.tvReferredBy.setText("-");
            holder.tvCaseCodeLabel.setText("Order Code");
            holder.tvCaseCode.setText(((OrderDetailsModel) listofAllObjects.get(position)).getOrderID());

            orderListAdapter = new OrderListAdapter(((OrderDetailsModel) listofAllObjects.get(position)).getTestName());
            holder.lvTestList.setLayoutManager(layoutManager);
            holder.lvTestList.setHasFixedSize(true);
            holder.lvTestList.setAdapter(orderListAdapter);
        }




    }

    @Override
    public int getItemCount() {
        return listofAllObjects.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tvLocationName;
        private TextView tvAdviceDate;
        private TextView tvReferredBy;
        private TextView tvCaseCode;
        private RecyclerView lvTestList;
        private LinearLayout llPDF ;
        private TextView tvCaseCodeLabel ;

        public MyViewHolder(View itemView) {
            super(itemView);

            tvLocationName = (TextView) itemView.findViewById(R.id.application_name_tv);
            tvAdviceDate = (TextView) itemView.findViewById(R.id.advice_date_value_tv);
            tvReferredBy = (TextView) itemView.findViewById(R.id.referred_by_value_tv);
            tvCaseCode = (TextView) itemView.findViewById(R.id.casecode_value_tv);
            lvTestList = (RecyclerView) itemView.findViewById(R.id.testnames_recyler_view);
            llPDF = (LinearLayout) itemView.findViewById(R.id.view_report_container);
            tvCaseCodeLabel = (TextView) itemView.findViewById(R.id.casecode_tv);

        }
    }


    public interface OnPdfTouch{
        public void onPdfTouch(CaseCodeModel caseCodeModel) ;
    }
}
