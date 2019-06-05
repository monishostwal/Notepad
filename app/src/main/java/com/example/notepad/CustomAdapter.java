package com.example.notepad;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter {
    public static List<Todo> items;
    public static  Context context;
    public AbsMethod absMethod;

    public void setAbsMethod(AbsMethod absMethod) {
        this.absMethod = absMethod;
    }

    public CustomAdapter(Context context, List<Todo> getdata){
        this.items= getdata;
        this.items.add(new Todo("",false));
        this.context=context;
    }
    @NonNull

    @Override

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater=LayoutInflater.from(viewGroup.getContext());
        View view= inflater.inflate(R.layout.recyclerview_row,viewGroup,false);
        ViewHolder item= new ViewHolder(view);


        return item;

    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, final int i) {
        final Todo todo1=items.get(i);

        ((ViewHolder)viewHolder).task.setText(todo1.getTask());
        ((ViewHolder)viewHolder).check.setChecked(todo1.check);





        ((ViewHolder)viewHolder).check.setVisibility(View.VISIBLE);
        if(((ViewHolder)viewHolder).check.isChecked())
            ((ViewHolder) viewHolder).task.setPaintFlags(((ViewHolder) viewHolder).task.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);


        ((ViewHolder)viewHolder).check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked ) {

                    ((ViewHolder) viewHolder).task.setPaintFlags(((ViewHolder) viewHolder).task.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    todo1.setCheck(true);
                    Toast.makeText(context,"Congratulations you have completed your task!!",Toast.LENGTH_SHORT).show();




                }
                else {
                    ((ViewHolder) viewHolder).task.setPaintFlags(((ViewHolder) viewHolder).task.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                    todo1.setCheck(false);
                    Toast.makeText(context,"Oh No!! You have to do one more task",Toast.LENGTH_SHORT).show();
                }
                absMethod. onCheckChange(todo1);

            }


        });
       items.set(i,todo1);
    }




    @Override
    public int getItemCount()
    {
        return items.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {

        return position;
    }

    public void update(String s, boolean b) {
        int pos=getItemCount();
        items.add(pos-1, new Todo(s,b));
        notifyItemInserted(-pos);


    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        private EditText task;
        private CheckBox check;



        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            task = (EditText) itemView.findViewById(R.id.task);
            check = (CheckBox) itemView.findViewById(R.id.check);


            task.setOnKeyListener(new View.OnKeyListener() {
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if ((event.getAction() == KeyEvent.ACTION_UP) &&
                            (keyCode == KeyEvent.KEYCODE_ENTER) && !task.getText().toString().equals("")) {
                        absMethod.onPressEnter(task.getText().toString(),check.isChecked());
                        Toast.makeText(context,"Oh No!! You have to do one more task",Toast.LENGTH_SHORT).show();


                        return true;
                    }
                    return false;
                }
            });


        }
    }

}

