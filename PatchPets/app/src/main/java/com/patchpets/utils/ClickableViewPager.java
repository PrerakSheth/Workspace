package com.patchpets.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.Scroller;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;

public class ClickableViewPager extends ViewPager {

    private OnItemClickListener mOnItemClickListener;

    public ClickableViewPager(Context context) {
        super(context);
        setup();
    }

    public ClickableViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup();
    }

    private void setup() {
        final GestureDetector tapGestureDetector = new    GestureDetector(getContext(), new TapGestureListener());

        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                tapGestureDetector.onTouchEvent(event);
                return false;
            }
        });
        init();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private class TapGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            if(mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(getCurrentItem());
            }
            return true;
        }
    }

    public static final int        DEFAULT_INTERVAL            = 1500;

    public static final int        LEFT                        = 0;
    public static final int        RIGHT                       = 1;

    /** do nothing when sliding at the last or first item **/
    public static final int        SLIDE_BORDER_MODE_NONE      = 0;
    /** cycle when sliding at the last or first item **/
    public static final int        SLIDE_BORDER_MODE_CYCLE     = 1;
    /** deliver event to parent when sliding at the last or first item **/
    public static final int        SLIDE_BORDER_MODE_TO_PARENT = 2;

    /** auto scroll time in milliseconds, default is {@link #DEFAULT_INTERVAL} **/
    private long                   interval                    = DEFAULT_INTERVAL;
    /** auto scroll direction, default is {@link #RIGHT} **/
    private int                    direction                   = RIGHT;
    /** whether automatic cycle when auto scroll reaching the last or first item, default is true **/
    private boolean                isCycle                     = true;
    /** whether stop auto scroll when touching, default is true **/
    private boolean                stopScrollWhenTouch         = true;
    /** how to process when sliding at the last or first item, default is {@link #SLIDE_BORDER_MODE_NONE} **/
    private int                    slideBorderMode             = SLIDE_BORDER_MODE_NONE;
    /** whether animating when auto scroll at the last or first item **/
    private boolean                isBorderAnimation           = true;
    /** scroll factor for auto scroll animation, default is 1.0 **/
    private double                 autoScrollFactor            = 1.0;
    /** scroll factor for swipe scroll animation, default is 1.0 **/
    private double                 swipeScrollFactor           = 1.0;

    private Handler handler;
    private boolean                isAutoScroll                = false;
    private boolean                isStopByTouch               = false;
    private float                  touchX                      = 0f, downX = 0f;
    private CustomDurationScroller scroller                    = null;

    public static final int        SCROLL_WHAT                 = 0;

    private void init() {
        handler = new MyHandler(this);
        setViewPagerScroller();
    }

    public void startAutoScroll() {
        isAutoScroll = true;
        sendScrollMessage((long)(interval + scroller.getDuration() / autoScrollFactor * swipeScrollFactor));
    }

    public void startAutoScroll(int delayTimeInMills) {
        isAutoScroll = true;
        sendScrollMessage(delayTimeInMills);
    }

    public void stopAutoScroll() {
        isAutoScroll = false;
        handler.removeMessages(SCROLL_WHAT);
    }

    public void setSwipeScrollDurationFactor(double scrollFactor) {
        swipeScrollFactor = scrollFactor;
    }

    public void setAutoScrollDurationFactor(double scrollFactor) {
        autoScrollFactor = scrollFactor;
    }

    private void sendScrollMessage(long delayTimeInMills) {
        /** remove messages before, keeps one message is running at most **/
        handler.removeMessages(SCROLL_WHAT);
        handler.sendEmptyMessageDelayed(SCROLL_WHAT, delayTimeInMills);
    }

    private void setViewPagerScroller() {
        try {
            Field scrollerField = ViewPager.class.getDeclaredField("mScroller");
            scrollerField.setAccessible(true);
            Field interpolatorField = ViewPager.class.getDeclaredField("sInterpolator");
            interpolatorField.setAccessible(true);

            scroller = new CustomDurationScroller(getContext(), (Interpolator)interpolatorField.get(null));
            scrollerField.set(this, scroller);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void scrollOnce() {
        PagerAdapter adapter = getAdapter();
        int currentItem = getCurrentItem();
        int totalCount;
        if (adapter == null || (totalCount = adapter.getCount()) <= 1) {
            return;
        }

        int nextItem = (direction == LEFT) ? --currentItem : ++currentItem;
        if (nextItem < 0) {
            if (isCycle) {
                setCurrentItem(totalCount - 1, isBorderAnimation);
            }
        } else if (nextItem == totalCount) {
            if (isCycle) {
                setCurrentItem(0, isBorderAnimation);
            }
        } else {
            setCurrentItem(nextItem, true);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = MotionEventCompat.getActionMasked(ev);

        if (stopScrollWhenTouch) {
            if ((action == MotionEvent.ACTION_DOWN) && isAutoScroll) {
                isStopByTouch = true;
                stopAutoScroll();
            } else if (ev.getAction() == MotionEvent.ACTION_UP && isStopByTouch) {
                startAutoScroll();
            }
        }

        if (slideBorderMode == SLIDE_BORDER_MODE_TO_PARENT || slideBorderMode == SLIDE_BORDER_MODE_CYCLE) {
            touchX = ev.getX();
            if (ev.getAction() == MotionEvent.ACTION_DOWN) {
                downX = touchX;
            }
            int currentItem = getCurrentItem();
            PagerAdapter adapter = getAdapter();
            int pageCount = adapter == null ? 0 : adapter.getCount();
            /**
             * current index is first one and slide to right or current index is last one and slide to left.<br/>
             * if slide border mode is to parent, then requestDisallowInterceptTouchEvent false.<br/>
             * else scroll to last one when current item is first one, scroll to first one when current item is last
             * one.
             */
            if ((currentItem == 0 && downX <= touchX) || (currentItem == pageCount - 1 && downX >= touchX)) {
                if (slideBorderMode == SLIDE_BORDER_MODE_TO_PARENT) {
                    getParent().requestDisallowInterceptTouchEvent(false);
                } else {
                    if (pageCount > 1) {
                        setCurrentItem(pageCount - currentItem - 1, isBorderAnimation);
                    }
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                return super.dispatchTouchEvent(ev);
            }
        }
        getParent().requestDisallowInterceptTouchEvent(true);

        return super.dispatchTouchEvent(ev);
    }

    private static class MyHandler extends Handler {

        private final WeakReference<ClickableViewPager> autoScrollViewPager;

        public MyHandler(ClickableViewPager autoScrollViewPager) {
            this.autoScrollViewPager = new WeakReference<ClickableViewPager>(autoScrollViewPager);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case SCROLL_WHAT:
                    ClickableViewPager pager = this.autoScrollViewPager.get();
                    if (pager != null) {
                        pager.scroller.setScrollDurationFactor(pager.autoScrollFactor);
                        pager.scrollOnce();
                        pager.scroller.setScrollDurationFactor(pager.swipeScrollFactor);
                        pager.sendScrollMessage(pager.interval + pager.scroller.getDuration());
                    }
                default:
                    break;
            }
        }
    }

    public long getInterval() {
        return interval;
    }

    public void setInterval(long interval) {
        this.interval = interval;
    }

    public int getDirection() {
        return (direction == LEFT) ? LEFT : RIGHT;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public boolean isCycle() {
        return isCycle;
    }

    public void setCycle(boolean isCycle) {
        this.isCycle = isCycle;
    }

    public boolean isStopScrollWhenTouch() {
        return stopScrollWhenTouch;
    }

    public void setStopScrollWhenTouch(boolean stopScrollWhenTouch) {
        this.stopScrollWhenTouch = stopScrollWhenTouch;
    }

    public int getSlideBorderMode() {
        return slideBorderMode;
    }

    public void setSlideBorderMode(int slideBorderMode) {
        this.slideBorderMode = slideBorderMode;
    }

    public boolean isBorderAnimation() {
        return isBorderAnimation;
    }

    public void setBorderAnimation(boolean isBorderAnimation) {
        this.isBorderAnimation = isBorderAnimation;
    }

    public class CustomDurationScroller extends Scroller {

        private double scrollFactor = 1;

        public CustomDurationScroller(Context context) {
            super(context);
        }

        public CustomDurationScroller(Context context, Interpolator interpolator) {
            super(context, interpolator);
        }

        public void setScrollDurationFactor(double scrollFactor) {
            this.scrollFactor = scrollFactor;
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            super.startScroll(startX, startY, dx, dy, (int)(duration * scrollFactor));
        }
    }
}