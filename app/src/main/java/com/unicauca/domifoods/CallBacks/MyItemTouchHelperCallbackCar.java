package com.unicauca.domifoods.CallBacks;

import android.graphics.Canvas;
import android.graphics.Color;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.unicauca.domifoods.R;
import com.unicauca.domifoods.adapters.AdapterListProducts;
import com.unicauca.domifoods.fragments.ShoppingcarFragment;
import com.unicauca.domifoods.interfaces.CallBackItemTouch;

public class MyItemTouchHelperCallbackCar extends ItemTouchHelper.Callback {

    CallBackItemTouch callBackItemTouch;

    public MyItemTouchHelperCallbackCar(CallBackItemTouch callBackItemTouch) {
        this.callBackItemTouch = callBackItemTouch;
    }

    public MyItemTouchHelperCallbackCar(ShoppingcarFragment shoppingcarFragment) {

    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        final int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;

        return makeMovementFlags(dragFlags,swipeFlags);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        callBackItemTouch.itemTouchOnMode(viewHolder.getLayoutPosition(),target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        callBackItemTouch.onSwiped(viewHolder,viewHolder.getAdapterPosition());
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if(actionState==ItemTouchHelper.ACTION_STATE_DRAG){
            super.onChildDraw(c,recyclerView,viewHolder,dX,dY,actionState,isCurrentlyActive);
        }else{
            final View foregroundView = ((AdapterListProducts.ViewHolder)viewHolder).ViewB;
            getDefaultUIUtil().onDrawOver(c,recyclerView,foregroundView,dX,dY,actionState,isCurrentlyActive);
        }
    }

    @Override
    public void onChildDrawOver(@NonNull Canvas c, @NonNull RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if(actionState!=ItemTouchHelper.ACTION_STATE_DRAG){
            final View foregroundView = ((AdapterListProducts.ViewHolder)viewHolder).ViewF;//cambio
            getDefaultUIUtil().onDraw(c,recyclerView,foregroundView,dX,dY,actionState,isCurrentlyActive);
        }
    }

    @Override
    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        final View foregroundView = ((AdapterListProducts.ViewHolder)viewHolder).ViewF;
        foregroundView.setBackgroundColor(ContextCompat.getColor(((AdapterListProducts.ViewHolder)viewHolder).ViewF.getContext(), R.color.blanco));
        getDefaultUIUtil().clearView(foregroundView);
    }

    @Override
    public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);
        if(viewHolder!=null){
            final View foregroundView=((AdapterListProducts.ViewHolder)viewHolder).ViewF;
            if(actionState==ItemTouchHelper.ACTION_STATE_DRAG){
                foregroundView.setBackgroundColor(Color.LTGRAY);
            }
            getDefaultUIUtil().onSelected(foregroundView);
        }
    }

    @Override
    public int convertToAbsoluteDirection(int flags, int layoutDirection) {
        return super.convertToAbsoluteDirection(flags, layoutDirection);
    }
}
