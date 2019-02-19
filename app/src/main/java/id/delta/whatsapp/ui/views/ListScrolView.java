package id.delta.whatsapp.ui.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;

import com.whatsapp.observablelistview.ObservableListView;

public class ListScrolView extends ObservableListView {

    private OnScrollListener onScrollListener;
    private OnDetectScrollListener onDetectScrollListener;
    private int mOffset = 0;

    public interface OnDetectScrollListener {

        void onUpScrolling();

        void onDownScrolling();

        void onBottomReached();
    }

    public ListScrolView(Context context) {
        super(context);
        onCreate(context, null, null);
    }

    public ListScrolView(Context context, AttributeSet attrs) {
        super(context, attrs);
        onCreate(context, attrs, null);
    }

    public ListScrolView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        onCreate(context, attrs, null);
    }

    @SuppressWarnings("UnusedParameters")
    private void onCreate(Context context, AttributeSet attrs, Integer defStyle) {
        setListeners();
    }

    private void setListeners() {
        super.setOnScrollListener(new OnScrollListener() {

            private int oldTop;
            private int oldFirstVisibleItem;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (onScrollListener != null) {
                    onScrollListener.onScrollStateChanged(view, scrollState);
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (onScrollListener != null) {
                    onScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
                }

                if (onDetectScrollListener != null) {
                    onDetectedListScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
                }
            }

            private void onDetectedListScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                View view = absListView.getChildAt(0);
                int top = (view == null) ? 0 : view.getTop();

              /*  if (firstVisibleItem == oldFirstVisibleItem) {
                    if (top > oldTop) {
                        onDetectScrollListener.onUpScrolling();
                    } else if (top < oldTop) {
                        onDetectScrollListener.onDownScrolling();
                    }
                } else {
                    if (firstVisibleItem < oldFirstVisibleItem) {
                        onDetectScrollListener.onUpScrolling();
                    } else {
                        onDetectScrollListener.onDownScrolling();
                    }
                }*/

                int position = firstVisibleItem+visibleItemCount;
                int limit = totalItemCount - mOffset;
                if (position >= limit && totalItemCount > 0) {
                    if (onDetectScrollListener != null ) {
                        onDetectScrollListener.onBottomReached();
                    }
                }else {
                    if (onDetectScrollListener != null ) {
                        onDetectScrollListener.onUpScrolling();
                    }
                }

                oldTop = top;
                oldFirstVisibleItem = firstVisibleItem;
            }
        });
    }

    @Override
    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.onScrollListener = onScrollListener;
    }

    public void setOnDetectScrollListener(OnDetectScrollListener onDetectScrollListener) {
        this.onDetectScrollListener = onDetectScrollListener;
    }
}
