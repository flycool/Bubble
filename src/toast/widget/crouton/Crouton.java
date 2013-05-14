
package toast.widget.crouton;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.FrameLayout.LayoutParams;

/**
 * Displays information in a non-invasive context related manner. Like
 */
public class Crouton {
    private static final int IMAGE_ID = 0x100;
    private static final int TEXT_ID = 0x101;
    private final CharSequence text;
    private final Style style;
    private Configuration configuration = null;
    private final View customView;

    private OnClickListener onClickListener;

    private Activity activity;
    private ViewGroup viewGroup;
    private FrameLayout croutonView;
    private Animation inAnimation;
    private Animation outAnimation;
    private LifecycleCallback lifecycleCallback = null;

    private Crouton(Activity activity, CharSequence text, Style style) {
        this.activity = activity;
        this.viewGroup = null;
        this.text = text;
        this.style = style;
        this.customView = null;
    }

    private Crouton(Activity activity, CharSequence text, Style style, ViewGroup viewGroup) {
        this.activity = activity;
        this.viewGroup = viewGroup;
        this.text = text;
        this.style = style;
        this.customView = null;
    }

    private Crouton(Activity activity, View customView) {
        this.activity = activity;
        this.customView = customView;
        this.viewGroup = null;
        this.text = null;
        this.style = new Style.Builder().build();
    }

    private Crouton(Activity activity, View customView, ViewGroup viewGroup) {
        this(activity, customView, viewGroup, new Configuration());
    }

    private Crouton(final Activity activity, final View customView, final ViewGroup viewGroup,
            final Configuration configuration) {
        this.activity = activity;
        this.customView = customView;
        this.viewGroup = viewGroup;
        this.text = null;
        this.style = new Style.Builder().build();
        this.configuration = configuration;
    }

    // =============================makeText================================================================

    public static Crouton makeText(Activity activity, CharSequence text, Style style) {
        return new Crouton(activity, text, style);
    }

    public static Crouton makeText(Activity activity, CharSequence text, Style style,
            ViewGroup viewGroup) {
        return new Crouton(activity, text, style, viewGroup);
    }

    public static Crouton makeText(Activity activity, CharSequence text, Style style,
            int ViewGroupId) {
        return new Crouton(activity, text, style, (ViewGroup) activity.findViewById(ViewGroupId));
    }

    public static Crouton makeText(Activity activity, int textResourceId, Style style) {
        return makeText(activity, activity.getString(textResourceId), style);
    }

    public static Crouton makeText(Activity activity, int textResourceId, Style style,
            ViewGroup viewGroup) {
        return makeText(activity, activity.getString(textResourceId), style, viewGroup);
    }

    public static Crouton makeText(Activity activity, int textResourceId, Style style,
            int viewGroupResId) {
        return makeText(activity, activity.getString(textResourceId), style,
                (ViewGroup) activity.findViewById(viewGroupResId));
    }

    // ==========================make======================================================================

    public static Crouton make(Activity activity, View customView) {
        return new Crouton(activity, customView);
    }

    public static Crouton make(Activity activity, View customView, ViewGroup viewGroup) {
        return new Crouton(activity, customView, viewGroup);
    }

    public static Crouton make(Activity activity, View customView, int viewGroupResId) {
        return new Crouton(activity, customView, (ViewGroup) activity.findViewById(viewGroupResId));
    }

    public static Crouton make(Activity activity, View customView, int viewGroupResId,
            final Configuration configuration) {
        return new Crouton(activity, customView, (ViewGroup) activity.findViewById(viewGroupResId),
                configuration);
    }

    // ========================showText=========================================================

    public static void showText(Activity activity, CharSequence text, Style style) {
        makeText(activity, text, style).show();
    }

    public static void showText(Activity activity, CharSequence text, Style style,
            ViewGroup viewGroup) {
        makeText(activity, text, style, viewGroup).show();
    }

    public static void showText(Activity activity, CharSequence text, Style style,
            int viewGroupResId) {
        makeText(activity, text, style, (ViewGroup) activity.findViewById(viewGroupResId)).show();
    }

    public static void showText(Activity activity, CharSequence text, Style style,
            int viewGroupResId,
            final Configuration configuration) {
        makeText(activity, text, style, (ViewGroup) activity.findViewById(viewGroupResId))
                .setConfiguration(configuration)
                .show();
    }

    public static void showText(Activity activity, int textResourceId, Style style) {
        showText(activity, activity.getString(textResourceId), style);
    }

    public static void showText(Activity activity, int textResourceId, Style style,
            ViewGroup viewGroup) {
        showText(activity, activity.getString(textResourceId), style, viewGroup);
    }

    public static void showText(Activity activity, int textResourceId, Style style,
            int viewGroupResId) {
        showText(activity, activity.getString(textResourceId), style, viewGroupResId);
    }

    // =====================show========================================================================

    public static void show(Activity activity, View customView) {
        make(activity, customView).show();
    }

    public static void show(Activity activity, View customView, ViewGroup viewGroup) {
        make(activity, customView, viewGroup).show();
    }

    public static void show(Activity activity, View customView, int viewGroupResId) {
        make(activity, customView, viewGroupResId).show();
    }

    // ==================================================================================================

    public static void hide(Crouton crouton) {

    }

    public static void cancelAllCroutons() {

    }

    public static void clearCroutonsForActivity(Activity activity) {

    }

    public void cancel() {

    }

    public void show() {

    }

    public Animation getInAnimation() {

        return inAnimation;
    }

    public Animation getOutAnimation() {

        return outAnimation;
    }

    public void setLifecycleCallback(LifecycleCallback lifecycleCallback) {
        this.lifecycleCallback = lifecycleCallback;
    }

    public Crouton setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
        return this;
    }

    public Crouton setConfiguration(final Configuration configuration) {
        this.configuration = configuration;
        return this;
    }

    @Override
    public String toString() {
        return "Crouton{" +
                "text=" + text +
                ", style=" + style +
                ", configuration=" + configuration +
                ", customView=" + customView +
                ", onClickListener=" + onClickListener +
                ", activity=" + activity +
                ", viewGroup=" + viewGroup +
                ", croutonView=" + croutonView +
                ", inAnimation=" + inAnimation +
                ", outAnimation=" + outAnimation +
                ", lifecycleCallback=" + lifecycleCallback +
                '}';
    }

    public static String getLicenseText() {
        return "This application uses the Crouton library.\n\n" +
                "Copyright 2012 - 2013 Benjamin Weiss \n" +
                "Copyright 2012 Neofonie Mobile GmbH\n" +
                "\n" +
                "Licensed under the Apache License, Version 2.0 (the \"License\");\n" +
                "you may not use this file except in compliance with the License.\n" +
                "You may obtain a copy of the License at\n" +
                "\n" +
                "   http://www.apache.org/licenses/LICENSE-2.0\n" +
                "\n" +
                "Unless required by applicable law or agreed to in writing, software\n" +
                "distributed under the License is distributed on an \"AS IS\" BASIS,\n" +
                "WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n" +
                "See the License for the specific language governing permissions and\n" +
                "limitations under the License.";
    }

    // ////////////////////////////////////////////////////////////////////////////////////
    // You have reached the internal API of Crouton.
    // If you do not plan to develop for Crouton there is nothing of interest
    // below here.
    // ////////////////////////////////////////////////////////////////////////////////////

    boolean isShowing() {
        return (null != activity) && (isCroutonViewNotNull() || isCustomViewNotNull());
    }

    private boolean isCroutonViewNotNull() {
        return (null != croutonView) && (null != croutonView.getParent());
    }

    private boolean isCustomViewNotNull() {
        return (null != customView) && (null != customView.getParent());
    }

    void detachActivity() {
        activity = null;
    }

    void detachViewGroup() {
        viewGroup = null;
    }

    void detachLifecycleCallback() {
        lifecycleCallback = null;
    }

    LifecycleCallback getLifecycleCallback() {
        return lifecycleCallback;
    }

    Style getStyle() {
        return style;
    }

    Configuration getConfiguration() {
        if (configuration == null) {
            configuration = getStyle().configuration;
        }
        return configuration;
    }

    Activity getActivity() {
        return activity;
    }

    ViewGroup getViewGroup() {
        return viewGroup;
    }

    CharSequence getText() {
        return text;
    }

    View getView() {
        if (this.customView != null) {
            return this.customView;
        }

        if (this.croutonView == null) {
            initializeCroutonView();
        }

        return this.croutonView;
    }

    private void mearsureCroutionView() {
        View view = getView();
        int widthSpec;
        if (viewGroup != null) {
            widthSpec = View.MeasureSpec.makeMeasureSpec(viewGroup.getMeasuredWidth(),
                    View.MeasureSpec.AT_MOST);
        } else {
            widthSpec = View.MeasureSpec.makeMeasureSpec(activity.getWindow().getDecorView()
                    .getMeasuredWidth(),
                    View.MeasureSpec.AT_MOST);
        }

        view.measure(widthSpec, View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
    }

    private void initializeCroutonView() {
        final Resources resources = activity.getResources();

        croutonView = initializeCroutonViewGroup(resources);

        // create content view
        RelativeLayout contentView = initializeContentView(resources);
        croutonView.addView(contentView);
    }

    private FrameLayout initializeCroutonViewGroup(Resources resources) {
        FrameLayout croutonView = new FrameLayout(activity);

        if (onClickListener != null) {
            croutonView.setOnClickListener(onClickListener);
        }

        final int width;
        if (this.style.widthDimensionResId > 0) {
            width = resources.getDimensionPixelSize(this.style.widthDimensionResId);
        } else {
            width = this.style.widthInPixels;
        }

        final int height;
        if (this.style.heightDimensionResId > 0) {
            height = resources.getDimensionPixelSize(this.style.heightDimensionResId);
        } else {
            height = this.style.heightInPixels;
        }

        // set layoutParams
        croutonView.setLayoutParams(
                new FrameLayout.LayoutParams(width != 0 ? width
                        : FrameLayout.LayoutParams.MATCH_PARENT, height));

        // set background
        if (this.style.backgroundColorValue != -1) {
            croutonView.setBackgroundColor(this.style.backgroundColorValue);
        } else {
            croutonView.setBackgroundColor(resources.getColor(this.style.backgroundColorResourceId));
        }

        // set the background drawable if set. This will override the background
        // color.
        if (this.style.backgroundDrawableResourceId != 0) {
            
            Bitmap background = BitmapFactory.decodeResource(resources, this.style.backgroundDrawableResourceId);
            BitmapDrawable drawable = new BitmapDrawable(resources, background); 
            if (this.style.isTileEnabled) {
                drawable.setTileModeXY(Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
            }
            croutonView.setBackgroundDrawable(drawable);
        }
        
        return croutonView;
    }

    private RelativeLayout initializeContentView(Resources resources) {
        RelativeLayout contentView = new RelativeLayout(activity);
        contentView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT));
        
        // set padding
        
        
        // only setup image if one is requested
        ImageView image = null;
        if ((this.style.imageDrawable != null) || (this.style.imageResId != 0)) {
            image = initializeImageView(); 
            contentView.addView(image, image.getLayoutParams());
        }
        
        // set text
        TextView text = initializeTextView(resources);
        RelativeLayout.LayoutParams textParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        if (image != null) {
            textParams.addRule(RelativeLayout.RIGHT_OF, image.getId());
        }
        contentView.addView(text, textParams);
        
        return contentView;
    }
    
    private ImageView initializeImageView() {
        ImageView image;
        image = new ImageView(activity);
        image.setId(IMAGE_ID);
        image.setAdjustViewBounds(true);
        image.setScaleType(this.style.imageScaleType);
        
        if (this.style.imageDrawable != null) {
            image.setImageDrawable(this.style.imageDrawable);
        }
        
        if (this.style.imageResId != 0) {
            image.setImageResource(this.style.imageResId);
        }
        
        RelativeLayout.LayoutParams imageParams = new RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT);
        imageParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        imageParams.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
    
        image.setLayoutParams(imageParams);
        
        return image;
    }
    
    private TextView initializeTextView(Resources resources) {
        TextView text = new TextView(this.activity);
        text.setId(TEXT_ID);
        text.setText(this.text);
        text.setTypeface(Typeface.DEFAULT_BOLD);
        text.setGravity(this.style.gravity);
        
        // set the text color if set
        if (this.style.textColorResourceId != 0) {
          text.setTextColor(resources.getColor(this.style.textColorResourceId));
        }
        
        if (this.style.textSize != 0) {
            text.setTextSize(TypedValue.COMPLEX_UNIT_SP, this.style.textSize);
        }
        
        // Setup the shadow if requested
        if (this.style.textShadowColorResId != 0) {
            initializeTextViewShadow(resources, text);
        }
        
        // Set the text appearance
        if (this.style.textAppearanceResId != 0) {
            text.setTextAppearance(this.activity, this.style.textAppearanceResId);
        }
        
        return text;
    }

    private void initializeTextViewShadow(final Resources resources, final TextView text) {
        int textShadowColor = resources.getColor(this.style.textShadowColorResId);
        float textShadowRadius = this.style.textShadowRadius;
        float textShadowDx = this.style.textShadowDx;
        float textShadowDy = this.style.textShadowDy;
        text.setShadowLayer(textShadowRadius, textShadowDx, textShadowDy, textShadowColor);
    }
}
