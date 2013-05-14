package toast.widget.crouton;

import android.graphics.drawable.Drawable;
import android.widget.ImageView.ScaleType;

public class Style {
    
    public Configuration configuration;
    public int widthDimensionResId;
    public int widthInPixels;
    public int heightDimensionResId;
    public int heightInPixels;
    public int backgroundColorValue;
    public int backgroundColorResourceId;
    public int backgroundDrawableResourceId;
    public boolean isTileEnabled;
    public Drawable imageDrawable;
    public int imageResId;
    public ScaleType imageScaleType;
    public int gravity;
    public int textColorResourceId;
    public int textSize;
    public int textShadowColorResId;
    public int textAppearanceResId;
    public float textShadowRadius;
    public float textShadowDx;
    public float textShadowDy;

    public Style(Builder builder) {
        
    }
    
    public static class Builder {
        
        public Builder() {
        }
        
        public Style build() {
            return new Style(this);
        }
    }
}
