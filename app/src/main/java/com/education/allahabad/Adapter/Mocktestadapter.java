package com.education.allahabad.Adapter;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.education.allahabad.Description;
import com.education.allahabad.Modelclass;
import com.education.allahabad.R;

import java.util.ArrayList;

public class Mocktestadapter extends RecyclerView.Adapter<Mocktestadapter.viewholder> {
    Context context;

    public Mocktestadapter(Context context, ArrayList<Modelclass> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    ArrayList<Modelclass>arrayList;
    @NonNull
    @Override
    public Mocktestadapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.mocktest,parent,false);

        return new Mocktestadapter.viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Mocktestadapter.viewholder holder, @SuppressLint("RecyclerView") int position) {
        holder.quename.setText(arrayList.get(position).getQuenames());
        holder.que.setText(arrayList.get(position).getQuestion());
        holder.mark.setText(arrayList.get(position).getMarks());
        holder.min.setText(arrayList.get(position).getMinutes());

      //  SharedPreferences sharedPreferences = context.getSharedPreferences("id", MODE_PRIVATE);
      //  String user_id = sharedPreferences.getString("userid", null);

        holder.attemptbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(context, Description.class);
                intent.putExtra("id",arrayList.get(position).getId());
                intent.putExtra("testName",arrayList.get(position).getQuenames());
                intent.putExtra("totalMinute",arrayList.get(position).getMinutes());
                intent.putExtra("totalMarks",arrayList.get(position).getMarks());

                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });











    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        TextView que,mark,min,quename;
        Button attemptbtn;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            quename=itemView.findViewById(R.id.txt1);
            que=itemView.findViewById(R.id.txt4);
            mark=itemView.findViewById(R.id.txt6);
            min=itemView.findViewById(R.id.txt8);
            attemptbtn=itemView.findViewById(R.id.attempt);


        }
    }

}
