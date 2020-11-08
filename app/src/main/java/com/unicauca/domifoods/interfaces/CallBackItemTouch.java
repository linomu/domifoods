package com.unicauca.domifoods.interfaces;

import androidx.recyclerview.widget.RecyclerView;

public interface CallBackItemTouch {

    void itemTouchOnMode(int oldPosition, int newPosition);
    void onSwiped(RecyclerView.ViewHolder viewHolder, int position);
}
