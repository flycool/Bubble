
package toast.widget.crouton;

import android.app.Activity;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/** Manages the lifecycle of {@link Crouton}s. */
public class Manager extends Handler {
    private static final class Messages {
        private Messages() {
        }

        public static final int DISPLAY_CROUTON = 0xc2007;
        public static final int ADD_CROUTON_TO_VIEW = 0xc20074dd;
        public static final int REMOVE_CROUTON = 0xc2007de1;
    }

    private static Manager INSTANCE;

    private Queue<Crouton> croutonQueue;

    private Manager() {
        croutonQueue = new LinkedBlockingQueue<Crouton>();
    }

    static Manager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Manager();
        }
        return INSTANCE;
    }

    /**
     * Inserts a {@link Crouton} to be displayed.
     */
    void add(Crouton crouton) {
        croutonQueue.add(crouton);
        displayCrouton();
    }

    private void displayCrouton() {
        if (croutonQueue.isEmpty()) {
            return;
        }

        final Crouton currentCrouton = croutonQueue.peek();

        // If the activity is null we poll the Crouton off the queue.
        if (currentCrouton.getActivity() == null) {
            croutonQueue.poll();
        }

        if (!currentCrouton.isShowing()) {
            // display crouton
            sendMessage(currentCrouton, Messages.ADD_CROUTON_TO_VIEW);
            if (currentCrouton.getLifecycleCallback() != null) {
                currentCrouton.getLifecycleCallback().onDisplayed();
            }
        } else {
            sendMessageDelayed(currentCrouton, Messages.DISPLAY_CROUTON,
                    caculateCroutonDuration(currentCrouton));
        }

    }

    private long caculateCroutonDuration(Crouton crouton) {
        long croutonDuration = crouton.getConfiguration().durationInMilliseconds;
        croutonDuration += crouton.getInAnimation().getDuration();
        croutonDuration += crouton.getOutAnimation().getDuration();
        return croutonDuration;
    }

    private void sendMessageDelayed(Crouton crouton, final int messageId, long delay) {
        final Message message = obtainMessage(messageId);
        message.obj = crouton;
        sendMessageDelayed(message, delay);
    }

    private void sendMessage(Crouton crouton, final int messageId) {
        final Message message = obtainMessage(messageId);
        message.obj = crouton;
        sendMessage(message);
    }

    @Override
    public void handleMessage(Message msg) {
        final Crouton crouton = (Crouton) msg.obj;

        switch (msg.what) {
            case Messages.DISPLAY_CROUTON: {
                displayCrouton();
                break;
            }
            case Messages.ADD_CROUTON_TO_VIEW: {
                addCroutonToView(crouton);
                break;
            }
            case Messages.REMOVE_CROUTON: {
                removeCrouton(crouton);
                if (crouton.getLifecycleCallback() != null) {
                    crouton.getLifecycleCallback().onRemoved();
                }
                break;
            }
            default: {
                super.handleMessage(msg);
                break;
            }
        }
    }

    /**
     * Adds a {@link Crouton} to the {@link ViewParent} of it's {@link Activity}
     * .
     * 
     * @param crouton The {@link Crouton} that should be added.
     */
    private void addCroutonToView(final Crouton crouton) {
        if (crouton.isShowing()) {
            return;
        }

        final View croutonView = crouton.getView();
        if (croutonView.getParent() == null) {
            ViewGroup.LayoutParams params = croutonView.getLayoutParams();
            if (params == null) {
                params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }

            // display Crouton in ViewGroup is it has been supplied
            if (crouton.getViewGroup() != null) {
                if (crouton.getViewGroup() instanceof FrameLayout) {
                    crouton.getViewGroup().addView(croutonView, params);
                } else {
                    crouton.getViewGroup().addView(croutonView, 0, params);
                }
            } else {
                Activity activity = crouton.getActivity();
                if (activity == null || activity.isFinishing()) {
                    return;
                }
                activity.addContentView(croutonView, params);
            }
        }

        croutonView.requestLayout();// This is needed so the animation can use
                                    // the measured with/height
        croutonView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
                            croutonView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        } else {
                            croutonView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        }

                        croutonView.startAnimation(crouton.getInAnimation());
                        if (Configuration.DURATION_INFINITE != crouton.getConfiguration().durationInMilliseconds) {
                            sendMessageDelayed(crouton, Messages.REMOVE_CROUTON,
                                    crouton.getConfiguration().durationInMilliseconds
                                            + crouton.getInAnimation().getDuration());
                        }
                    }
                });

    }

    protected void removeCrouton(Crouton crouton) {
        View croutonView = crouton.getView();
        ViewGroup croutonParentView = (ViewGroup) croutonView.getParent();

        if (croutonParentView != null) {
            croutonView.startAnimation(crouton.getOutAnimation());

            // Remove the crouton from the queue.
            Crouton removed = croutonQueue.poll();

            // Remove the crouton from the view's parent.
            croutonParentView.removeView(croutonView);
            if (removed != null) {
                removed.detachActivity();
                removed.detachViewGroup();
                if (removed.getLifecycleCallback() != null) {
                    removed.getLifecycleCallback().onRemoved();
                }
                removed.detachLifecycleCallback();
            }

            // Send a message to display the next crouton but delay it by the
            // out
            // animation duration to make sure it finishes
            sendMessageDelayed(crouton, Messages.DISPLAY_CROUTON, crouton.getOutAnimation()
                    .getDuration());
        }
    }

    void removeCroutonImmediately(Crouton crouton) {
        // if Crouton has already been displayed then it may not be in the queue
        // (because it was popped).
        // This ensures the displayed Crouton is removed from its parent
        // immediately, whether another instance
        // of it exists in the queue or not.
        // Note: crouton.isShowing() is false here even if it really is showing,
        // as croutonView object in
        // Crouton seems to be out of sync with reality!
        if (crouton.getActivity() != null && crouton.getView() != null
                && crouton.getView().getParent() != null) {
            ((ViewGroup) crouton.getView().getParent()).removeView(crouton.getView());

            // remove any messages pending for the crouton
            removeAllMessagesForCrouton(crouton);
        }
        // remove any matching croutons from queue
        if (croutonQueue != null) {
            final Iterator<Crouton> croutonIterator = croutonQueue.iterator();
            while (croutonIterator.hasNext()) {
                final Crouton c = croutonIterator.next();
                if (c.equals(crouton) && c.getActivity() != null) {
                    // remove the crouton from the content view
                    if (crouton.isShowing()) {
                        ((ViewGroup) c.getView().getParent()).removeView(c.getView());
                    }

                    removeAllMessagesForCrouton(c);
                    croutonIterator.remove();
                    // we have found our crouton so just break
                    break;
                }
            }
        }
    }

    void clearCroutonQueue() {
        removeAllMessages();

        if (null != croutonQueue) {
            // remove any views that may already have been added to the
            // activity's
            // content view
            for (Crouton crouton : croutonQueue) {
                if (crouton.isShowing()) {
                    ((ViewGroup) crouton.getView().getParent()).removeView(crouton.getView());
                }
            }
            croutonQueue.clear();
        }
    }

    void clearCroutonsForActivity(Activity activity) {
        if (null != croutonQueue) {
            Iterator<Crouton> croutonIterator = croutonQueue.iterator();
            while (croutonIterator.hasNext()) {
                Crouton crouton = croutonIterator.next();
                if ((null != crouton.getActivity()) && crouton.getActivity().equals(activity)) {
                    // remove the crouton from the content view
                    if (crouton.isShowing()) {
                        ((ViewGroup) crouton.getView().getParent()).removeView(crouton.getView());
                    }

                    removeAllMessagesForCrouton(crouton);

                    // remove the crouton from the queue
                    croutonIterator.remove();
                }
            }
        }
    }

    private void removeAllMessages() {
        removeMessages(Messages.ADD_CROUTON_TO_VIEW);
        removeMessages(Messages.DISPLAY_CROUTON);
        removeMessages(Messages.REMOVE_CROUTON);
    }

    private void removeAllMessagesForCrouton(Crouton crouton) {
        removeMessages(Messages.ADD_CROUTON_TO_VIEW, crouton);
        removeMessages(Messages.DISPLAY_CROUTON, crouton);
        removeMessages(Messages.REMOVE_CROUTON, crouton);
    }

    @Override
    public String toString() {
      return "Manager{" +
        "croutonQueue=" + croutonQueue +
        '}';
    }
}
