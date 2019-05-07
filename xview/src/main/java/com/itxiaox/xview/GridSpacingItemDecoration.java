//package com.itxiaox.xxview;
//
//
//import android.graphics.Rect;
//import android.support.v7.widget.GridLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.view.View;
//
///**
// * Created by anonymous on 2016/9/9 0009.
// * <p>
// * GridSpacingItemDecoration
// * <p>
// * int spanCount = 3; // 3 columns
// * int spacing = 50; // 50px
// * boolean includeEdge = false;
// * s.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
// */
//public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {
//
//    private int spanCount;
//    private int spacing;
//    private boolean includeEdge;
//
//    public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
//        this.spanCount = spanCount;
//        this.spacing = spacing;
//        this.includeEdge = includeEdge;
//    }
//
//    @Override
//    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//        GridLayoutManager layoutManager = (GridLayoutManager) parent.getLayoutManager();
//
//        int position = parent.getChildAdapterPosition(view); // item position
//
//        int spansize = layoutManager.getSpanSizeLookup().getSpanSize(position);
//        if(spansize>1){//跨行
//            return;
//        }
//
//        int column = position % spanCount; // item column
//
//        if (includeEdge) {
//            outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
//            outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)
//
//            if (position < spanCount) { // top edge
//                outRect.top = spacing;
//            }
//            outRect.bottom = spacing; // item bottom
//        } else {
//            outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
//            outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
//            if (position >= spanCount) {
//                outRect.top = spacing; // item top
//            }
//        }
//    }
//}