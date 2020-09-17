//package com.github.itxiaox.view;
//
//import android.util.Pair;
//import android.view.View;
//
//import androidx.recyclerview.widget.DefaultItemAnimator;
//import androidx.recyclerview.widget.DividerItemDecoration;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.jishimed.jsutils.JSUtils;
//import com.jishimed.pocus.commonlib.HasKey;
//import com.jishimed.viewlib.adapter.CommonAdapter;
//import com.jishimed.viewlib.adapter.HeaderAndFooterWrapper;
//import com.jishimed.viewlib.adapter.MultiItemTypeAdapter;
//import com.jishimed.viewlib.adapter.ViewHolder;
//import com.scwang.smartrefresh.layout.SmartRefreshLayout;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.ListIterator;
//
//import io.reactivex.Observable;
//import io.reactivex.ObservableEmitter;
//import io.reactivex.ObservableOnSubscribe;
//import io.reactivex.android.schedulers.AndroidSchedulers;
//import io.reactivex.disposables.Disposable;
//import io.reactivex.schedulers.Schedulers;
//
///**
// * ListFragment Base
// * @param <T>   Type of list item
// * @param <F>   Type of loading filter
// */
//public abstract class BaseListFragment<T extends HasKey<T>, F> extends com.jishimed.viewlib.fragment.BaseDialogFragment<T> {
//    private static final String TAG = "BaseListFragment";
//
//    public enum LoadType {
//        Init, Refresh, LoadMore
//    }
//    protected SmartRefreshLayout refreshLayout;
//    protected CommonAdapter adapter;
//    protected RecyclerView recyclerView;
//    protected List<T> mItems = new ArrayList<>();
//    private int offset = 0;    //Since 0.
//    private int pageSize = 10;
//    private int totalCount = 0;
//    protected F filter;
//
//    //Disposable and Emitter for Loading Items.
//    private Disposable dpLoading;
//    private ObservableEmitter<LoadType> mEmitter;
//    private final Object emitWait = new Object();
//
//    /**
//     * 设置RecyclerView
//     * @param recyclerView
//     * @param orientation
//     * @param layoutId
//     */
//    protected void initRecyclerView(RecyclerView recyclerView, int orientation, int layoutId) {
//        this.recyclerView = recyclerView;
//
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
//        layoutManager.setOrientation(orientation);
//        recyclerView.setLayoutManager(layoutManager);
//
//        //设置Item增加、移除动画
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//
//        //添加分割线
//        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), orientation));
//
//        //recyclerView.addOnLayoutChangeListener(this::onRecyclerViewLayoutChange);
//
//        adapter = new CommonAdapter<T>(getActivity(), layoutId, mItems) {
//            @Override
//            protected void convert(ViewHolder holder, T item, int position) {
////                int height = holder.itemView.getMeasuredHeight();
////                if ((needCalculated || height != itemHeight) && height > 0) {
////                    itemHeight = height;
////                    int pageSize = (viewHeight + itemHeight - 1) / itemHeight;
////                    setPageSize(pageSize);
////                    needCalculated = false;
////                }
//                bindItem(holder, item, position);
//
//            }
//        };
//
//        recyclerView.setAdapter(adapter);
//
//        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
//                if(mItems==null||position<0||position>=mItems.size())return;
//                T t = mItems.get(position);
//                com.jishimed.viewlib.fragment.BaseListFragment.this.onItemClick(view, holder, position, t);
//            }
//
//            @Override
//            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
//                if(mItems==null||position<0||position>=mItems.size())return false;
//                T t = mItems.get(position);
//                return com.jishimed.viewlib.fragment.BaseListFragment.this.onItemLongClick(view, holder, position, t);
//            }
//        });
//
//    }
//
////    private void onRecyclerViewLayoutChange(View v, int left, int top, int right, int bottom,
////                                            int oldLeft, int oldTop, int oldRight, int oldBottom) {
////        viewHeight = bottom - top;
////        needCalculated = true;
////
////        //Visible Item Count
////        LinearLayoutManager mgr = ((LinearLayoutManager)recyclerView.getLayoutManager());
////
////        int visibleItemCount = mgr.findLastVisibleItemPosition()-mgr.findFirstVisibleItemPosition()+1;
//////        setPageSize(visibleItemCount);
////
////        int childCount = recyclerView.getChildCount();
////        JSUtils.d(TAG, "Child Count=%d. VisibleItemCount=%d. ItemCount=%d.",
////                childCount, visibleItemCount, mItems.size());
////    }
//
//    protected void addHeaderAndFooterView(View headerView, View footerView) {
//        if (recyclerView == null || adapter == null || (headerView == null && footerView == null))
//            return;
//
//        HeaderAndFooterWrapper headerAndFooterWrapper = new HeaderAndFooterWrapper<>(adapter);
//        if (headerView != null)
//            headerAndFooterWrapper.addHeaderView(headerView);
//        if (footerView != null)
//            headerAndFooterWrapper.addFootView(footerView);
//
//        recyclerView.setAdapter(headerAndFooterWrapper);
//        adapter.notifyDataSetChanged();
//    }
//
//    @Override
//    public void onDestroyView() {
//        if (dpLoading != null) {
//            if (!dpLoading.isDisposed()) dpLoading.dispose();
//            dpLoading = null;
//        }
//        mEmitter = null;
//
//        super.onDestroyView();
//    }
//
//    protected void setPageSize(int size) {
//        JSUtils.d("Holder", "setPageSize: offset=%d, old=%d, new=%d.", offset, pageSize, size);
//        if (size != pageSize) {
//            pageSize = size;
//            load(LoadType.Init);
//        }
//    }
//
//    protected int getPageSize() {
//        return  pageSize;
//    }
//
//    public void setItems(List<T> items) {
//        mItems.clear();
//        mItems.addAll(items);
//        offset = mItems.size();
//        if (adapter != null) {
//            adapter.notifyDataSetChanged();
//        }
//    }
//
//    /**
//     * 初始化数据
//     *  @param loadType
//     *
//     */
//    protected void load(LoadType loadType) {
//        offset = (loadType == LoadType.LoadMore) ? (offset + pageSize) : 0;
//        synchronized (emitWait) {
//            if (mEmitter == null) {
//                try {
//                    emitWait.wait();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            JSUtils.d(TAG, "load: type=%s, offset=%d, pageSize=%d, mEmitter=%s.",
//                    loadType, offset, pageSize, mEmitter);
//            if (mEmitter != null) {
//                mEmitter.onNext(loadType);
//            }
//        }
//    }
//
//    /**
//     * 设置子布局数据
//     *
//     * @param holder
//     * @param t
//     * @param position
//     */
//    protected abstract void bindItem(ViewHolder holder, T t, int position);
//
//    /**
//     * 点击item回调
//     *
//     * @param view
//     * @param holder
//     * @param position
//     * @param t
//     */
//    protected void onItemClick(View view, RecyclerView.ViewHolder holder, int position, T t) {
//    }
//
//    /**
//     * 长按item回调
//     *
//     * @param view
//     * @param holder
//     * @param position
//     * @param t
//     * @return
//     */
//    protected boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position, T t) {
//        return false;
//    }
//
//    protected abstract int getItemCount(F filter);
//
//    protected abstract List<T> loadItems(F filter, int offset, int limit);
//
//    /**
//     * 刷新
//     *
//     * @param layout
//     */
//    protected void initRefreshLayout(SmartRefreshLayout layout) {
//        refreshLayout = layout;
//
//        //Create Observable for item loading
//        dpLoading = Observable.create((ObservableOnSubscribe<LoadType>) e -> {
//                    synchronized (emitWait) {
//                        mEmitter = e;
//                        emitWait.notifyAll();
//                    }
//                })
//                .subscribeOn(Schedulers.io())
//                .observeOn(Schedulers.io()) //Make the action loading StudyItems run on IO thread.
//                .map( type -> {
//                    JSUtils.d(TAG, "Loading thread: " + Thread.currentThread().getName());
//
//                    totalCount = getItemCount(filter);
//                    List<T> items = loadItems(filter, offset, pageSize);
//
//                    boolean changed = addItems(type, items);
//                    return new Pair<>(type, changed);
//                })
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe( pair -> {
//                    refreshUI(pair.first, 100);
//
//                    //Notify change if changed.
//                    if (pair.second) {
//                        // 加载完毕后在 UI 线程结束下拉刷新
//                        adapter.notifyDataSetChanged();
//                    }
//                });
//
//        refreshLayout.setOnRefreshListener( l -> load(LoadType.Refresh));
//
//        refreshLayout.setOnLoadMoreListener( l -> {
//            if (offset < totalCount) {
//                load(LoadType.LoadMore);
//            } else {
//                l.finishLoadMore(500);
//            }
//        });
//    }
//
//    private boolean addItems(LoadType type, List<T> items) {
//        boolean changed = false;
//        switch (type) {
//            case Init: {
//                mItems.clear();
//                mItems.addAll(items);
//                changed = true;
//                break;
//            }
//            case Refresh: {
//                ListIterator<T> it = items.listIterator(items.size());
//                while (it.hasPrevious()) {
//                    T item = it.previous();//listIterator逆向向前遍历
//                    if (item == null) continue;
//
//                    boolean found = false;
//                    int cnt = mItems.size();
//                    for (int i=0; i < cnt; i++) {
//                        T other = mItems.get(i);
//                        if (item.keyEquals(other)) {
//                            if (!item.equals(other)) {
//                                mItems.set(i, item);
//                                changed = true;
//                            }
//                            found = true;
//                            break;
//                        }
//                    }
//
//                    if (!found) {
//                        mItems.add(0, item);
//                        changed = true;
//                    }
////                    if (!mItems.contains(item)) {
////                        mItems.add(0, item);
////                        changed = true;
////                    }
//                }
//                break;
//            }
//            case LoadMore: {
//                for (T item : items) {
//                    if (item == null) continue;
//
//                    boolean found = false;
//                    int cnt = mItems.size();
//                    for (int i = 0; i < cnt; i++) {
//                        T other = mItems.get(i);
//                        if (item.keyEquals(other)) {
//                            if (!item.equals(other)) {
//                                mItems.set(i, item);
//                                changed = true;
//                            }
//                            found = true;
//                            break;
//                        }
//                    }
//
//                    if (!found) {
//                        mItems.add(item);
//                        changed = true;
//                    }
//                }
////                for (T item : items) {
////                    if (!mItems.contains(item)) {
////                        mItems.add(item);
////                        changed = true;
////                    }
////                }
//                break;
//            }
//        }
//        return changed;
//    }
//
//    private void refreshUI(LoadType type, int delayed) {
//        switch (type) {
//            case Init:
//                break;
//            case Refresh:
//                refreshLayout.finishRefresh(delayed);
//                break;
//            case LoadMore:
//                refreshLayout.finishLoadMore(delayed);
//                break;
//        }
//    }
//}
