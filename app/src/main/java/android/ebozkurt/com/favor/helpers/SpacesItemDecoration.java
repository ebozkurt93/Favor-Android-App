package android.ebozkurt.com.favor.helpers;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by erdem on 27.10.2017.
 */

public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
    private final int mSpace;

    public SpacesItemDecoration(int space) {
        this.mSpace = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left = mSpace;
        outRect.right = mSpace;

        if (parent.getChildAdapterPosition(view) == 0)
            outRect.left = mSpace * 2;
        if (parent.getChildAdapterPosition(view) == parent.getChildCount() + 1)
            outRect.right = mSpace * 2;

        // Add top margin only for the first item to avoid double space between items
        /*if (parent.getChildAdapterPosition(view) == 0)
            outRect.top = mSpace;*/
    }
}

