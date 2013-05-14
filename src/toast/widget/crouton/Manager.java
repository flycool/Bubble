package toast.widget.crouton;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;


/** Manages the lifecycle of {@link Crouton}s. */
public class Manager extends Handler {
    private static final class Messages {
        private Messages() {}
        
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
            sendMessageDelayed(currentCrouton, Messages.DISPLAY_CROUTON, caculateCroutonDuration(currentCrouton));
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
        
        switch(msg.what) {
            case Messages.DISPLAY_CROUTON : {
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
     * Adds a {@link Crouton} to the {@link ViewParent} of it's {@link Activity}.
     *
     * @param crouton
     *   The {@link Crouton} that should be added.
     */
    private void addCroutonToView(final Crouton crouton) {
        if (crouton.isShowing()) {
            return;
        }
        
        final View croutonView = crouton.getView();
        if (croutonView.getParent() == null) {
            ViewGroup.LayoutParams params = croutonView.getLayoutParams();
            if (params == null) {
                params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
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
        
    }
    
    private void removeCrouton(Crouton crouton) {
        
    }
}
