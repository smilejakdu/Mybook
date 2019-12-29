package com.example.cns09.study.Navi.StopWatch;



import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
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


public class Stopadapter extends RecyclerView.Adapter<Stopadapter.MyViewHolder>  {

    ArrayList<StopInfo> sList;  // private 에서 바꿈
    Context sContext;

    //  나는 받을때 시간하나만 받으면 된다 = >TextView 하나

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

        TextView time;

        // 여기에 시간이 적히면 시간을 받아오는 Text 를 적어줘야한다.
        public MyViewHolder(View view){
            super(view);
            time = view.findViewById(R.id.tv_time);
            view.setOnCreateContextMenuListener(this);  // onCreateContextMenuListener 리스너를 현재 클래스에서 구현한다고 설정해 둡니다.

            // 여기서 view. 해서 onItemSelected 하는 줄 알았는데 안됨 .

        }
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            // 3. 컨텍스트 메뉴를 생성하고 메뉴 항목 선택시 호출되는 리스너를 등록해줍니다. ID 1001, 1002로 어떤 메뉴를 선택했는지 리스너에서 구분하게 됩니다.
            MenuItem Delete = menu.add(Menu.NONE , 1001,1,"삭제");    // 삭제번튼 2
            Delete.setOnMenuItemClickListener(onEditMenu);

        }

        // 4. 컨텍스트 메뉴에서 항목 클릭시 동작을 설정합니다.
        private final MenuItem.OnMenuItemClickListener onEditMenu = new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {

                    case 1001:
                        // 삭제하는 부분
                        SharedStop sharedStop = new SharedStop();
                        sList.remove(getAdapterPosition());
                        sharedStop.clear(sContext);
                        String qwe="";
                        // 이거하면 다 지우게 된다.
                        Log.d("qwerasdf삭제","sharedStop.clear(sContext); : "+sharedStop);

                        for(int i=0; i<sList.size(); i++){  // sList 싸이즈만큼 돌리게 된다 .

                            Log.d("qwerasdf","sList.get(i).toString() :"+sList.get(i).toString());
                            qwe = qwe+ sharedStop.stopInfoString(sList.get(i))+"@";   // for문이 돌면서 , sharedStop 안에있는 stopInfoString 함수에 들어가게되고 ,
                            Log.d("qwerasdf삭제2","abc :"+qwe);
                            //삭제하고 나서 삭제한거만 제외하고 다시 다 넣어줘야하는데 , 자꾸 덮어진다 .
                        }

                        sharedStop.saveData(sContext,qwe);
                        //해당 position 을 remove 하게 된다 .
                        notifyItemRemoved(getAdapterPosition());
                        // 밑에 있는것은 정확하게 무슨뜻일까 ?? => 지정한다 아이템 범위 교환 ( 얻는다 Adapter position() , tList.size());
                        notifyItemRangeChanged(getAdapterPosition(), sList.size());
                        break;

                }
                return true;
            }
        };
    }


//    public Stopadapter(Stopwatch context, ArrayList<StopInfo> stopInfoArrayList) {
//    }


    public Stopadapter (Context context , ArrayList<StopInfo>list){
        sList = list ;
        sContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_stop_recycler, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    // 각 항목을 구성하기 위해서 호출
    // onCreateViewHolder 가 onBindViewHolder 파라미터로 넘어오게 된다 그래서 , public MyViewHolder 가  (MyViewHolder holder ...로 같아야 한다.
    public void onBindViewHolder(MyViewHolder holder, int position) {
//        holder.ivPicture.setImageResource(foodInfoArrayList.get(position).drawableId);
        Log.d("aasdfasdf","abc : "+sList.get(position).time);
        if(sList.get(position).time.equals("")){
            return;
        }
        holder.time.setText(sList.get(position).time);
        // 위에를 주석처리하게 되면 , 시간 시간 시간 으로 한줄씩 늘어나게 된다 .
    }

    @Override
    // 항목의 개수를 판단
    public int getItemCount() {
        return sList.size();
    }
}
