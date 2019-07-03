package mguell.sample_tmdb.utils;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class RecyclerViewMargin extends RecyclerView.ItemDecoration {

    private final int columns;
    private final int margin;

    public RecyclerViewMargin(@IntRange(from = 0) final int margin,
                              @IntRange(from = 0) final int columns) {
        this.margin = margin;
        this.columns = columns;

    }

    @Override
    public void getItemOffsets(@NonNull final Rect outRect,
                               @NonNull final View view,
                               @NonNull final RecyclerView parent,
                               @NonNull final RecyclerView.State state) {

        final int position = parent.getChildLayoutPosition(view);
        outRect.right = margin;
        outRect.bottom = margin;
        if (position < columns) {
            outRect.top = margin;
        }
        if (position % columns == 0) {
            outRect.left = margin;
        }
    }
}