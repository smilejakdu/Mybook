package com.example.cns09.study.Navi.StopWatch;


import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.cns09.study.R;

import java.util.ArrayList;


public class Stopadapter extends RecyclerView.Adapter<Stopadapter.ViewHolder> {

    ArrayList<StopInfo> sList;  // private 에서 바꿈
    Context sContext;

    public Stopadapter(Context context, ArrayList<StopInfo> list) {
        this.sList = list;
        this.sContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(sContext).inflate(R.layout.activity_stop_recycler, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String tvTime = sList.get(position).getTime();
        Log.d("tvTime", "onBindViewHolder: " + tvTime);
        if (sList.get(position).getTime().equals("")) {
            return;
        }
        holder.time.setText(sList.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        return sList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

        TextView time;

        public ViewHolder(View view) {
            super(view);
            time = view.findViewById(R.id.tv_time);
            view.setOnCreateContextMenuListener(this);  // onCreateContextMenuListener 리스너를 현재 클래스에서 구현한다고 설정해 둡니다.

        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            MenuItem Delete = menu.add(Menu.NONE, 1000, 0, "삭제");    // 삭제번튼 0
            MenuItem totalDelete = menu.add(Menu.FIRST, 1001, 1, "전체삭제");//전체삭제 1
            Delete.setOnMenuItemClickListener(onEditMenu);
            totalDelete.setOnMenuItemClickListener(onEditMenu);

        }

        private final MenuItem.OnMenuItemClickListener onEditMenu = new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {

                    case 1000:
                        // 삭제하는 부분
                        SharedStop sharedStop = new SharedStop();
                        sList.remove(getAdapterPosition());
                        sharedStop.clear(sContext);
                        String qwe = "";

                        for (int i = 0; i < sList.size(); i++) {  // sList 싸이즈만큼 돌리게 된다 .
                            qwe = qwe + sharedStop.stopInfoString(sList.get(i)) + "@";   // for문이 돌면서 , sharedStop 안에있는 stopInfoString 함수에 들어가게되고 ,
                        }

                        sharedStop.saveData(sContext, qwe);
                        notifyItemRemoved(getAdapterPosition());
                        notifyItemRangeChanged(getAdapterPosition(), sList.size());
                        break;
                    case 1001:
//                        전체삭제
                        sharedStop = new SharedStop();
                        sharedStop.clear(sContext);
                        sList.clear();
                        notifyDataSetChanged();

                        break;
                }
                return true;
            }
        };
    }
}
