package com.education.allahabad.Adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.education.allahabad.Modelclass;
import com.education.allahabad.R;

import java.util.ArrayList;

public class Veiw_details_adapter extends RecyclerView.Adapter<Veiw_details_adapter.holder> {
    Context context;

    public Veiw_details_adapter(Context context, ArrayList<Modelclass> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    ArrayList<Modelclass>arrayList;

    String correct_ans,wrong_ans;

    @NonNull
    @Override
    public Veiw_details_adapter.holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_details_custom, parent, false);
        return new Veiw_details_adapter.holder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull Veiw_details_adapter.holder holder, int position) {
            holder.question.setText(Html.fromHtml(arrayList.get(position).getViewdetailsquestio()));

             correct_ans = arrayList.get(position).getCorrect_ans();
             wrong_ans = arrayList.get(position).getWrong_ans();

             if (correct_ans.equals(wrong_ans)){
                 holder.a.setText("Selected Answer : "+"( "+wrong_ans+" )");
                 holder.b.setText("Correct Answer : "+"( "+correct_ans+" )");

                 holder.a.setTextColor(context.getResources().getColor(R.color.green));
                 holder.b.setTextColor(context.getResources().getColor(R.color.green));
             }else if (!correct_ans.equals(wrong_ans)){
                 holder.a.setText("Selected Answer : "+"( "+wrong_ans+" )");
                 holder.b.setText("Correct Answer : "+"( "+correct_ans+" )");

                 holder.a.setTextColor(context.getResources().getColor(R.color.red));
                 holder.b.setTextColor(context.getResources().getColor(R.color.green));

             }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class holder extends RecyclerView.ViewHolder {
        TextView question,a,b;
        public holder(@NonNull View itemView) {
            super(itemView);
            question=itemView.findViewById(R.id.question);
            a=itemView.findViewById(R.id.a);
            b=itemView.findViewById(R.id.b);
        }
    }
}
