
package toast.widget.crouton;

import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

/** The style for a {@link Crouton}. */
public class Style {

    public static final int holoRedLight = 0xffff4444;
    public static final int holoGreenLight = 0xff99cc00;
    public static final int holoBlueLight = 0xff33b5e5;

    public static final Style ALERT;
    public static final Style CONFIRM;
    public static final Style INFO;

    static {
        ALERT = new Builder().setBackgroundColorValue(holoRedLight).build();
        CONFIRM = new Builder().setBackgroundColorValue(holoGreenLight).build();
        INFO = new Builder().setBackgroundColorValue(holoBlueLight).build();
    }

    final Configuration configuration;
    final int backgroundColorResourceId;
    final int backgroundDrawableResourceId;
    final boolean isTileEnabled;
    final int textColorResourceId;
    final int heightInPixels;
    final int heightDimensionResId;
    final int widthInPixels;
    final int widthDimensionResId;
    final int gravity;
    final Drawable imageDrawable;
    final int textSize;
    final int textShadowColorResId;
    final float textShadowRadius;
    final float textShadowDx;
    final float textShadowDy;
    final int textAppearanceResId;
    final int imageResId;
    final ImageView.ScaleType imageScaleType;
    final int paddingInPixels;
    final int paddingDimensionResId;
    final int backgroundColorValue;

    public Style(Builder builder) {
        this.configuration = builder.configuration;
        this.backgroundColorResourceId = builder.backgroundColorResourceId;
        this.backgroundDrawableResourceId = builder.backgroundDrawableResourceId;
        this.isTileEnabled = builder.isTileEnabled;
        this.textColorResourceId = builder.textColorResourceId;
        this.heightInPixels = builder.heightInPixels;
        this.heightDimensionResId = builder.heightDimensionResId;
        this.widthInPixels = builder.widthInPixels;
        this.widthDimensionResId = builder.widthDimensionResId;
        this.gravity = builder.gravity;
        this.imageDrawable = builder.imageDrawable;
        this.textSize = builder.textSize;
        this.textShadowColorResId = builder.textShadowColorResId;
        this.textShadowRadius = builder.textShadowRadius;
        this.textShadowDx = builder.textShadowDx;
        this.textShadowDy = builder.textShadowDy;
        this.textAppearanceResId = builder.textAppearanceResId;
        this.imageResId = builder.imageResId;
        this.imageScaleType = builder.imageScaleType;
        this.paddingInPixels = builder.paddingInPixels;
        this.paddingDimensionResId = builder.paddingDimensionResId;
        this.backgroundColorValue = builder.backgroundColorValue;
    }

    public static class Builder {
        private Configuration configuration;
        private int backgroundColorValue;
        private int backgroundColorResourceId;
        private int backgroundDrawableResourceId;
        private boolean isTileEnabled;
        private int textColorResourceId;
        private int heightInPixels;
        private int heightDimensionResId;
        private int widthInPixels;
        private int widthDimensionResId;
        private int gravity;
        private Drawable imageDrawable;
        private int textSize;
        private int textShadowColorResId;
        private float textShadowRadius;
        private float textShadowDx;
        private float textShadowDy;
        private int textAppearanceResId;
        private int imageResId;
        private ImageView.ScaleType imageScaleType;
        private int paddingInPixels;
        private int paddingDimensionResId;

        public Builder() {
            configuration = Configuration.DEFAULT;
            paddingInPixels = 10;
            backgroundColorResourceId = android.R.color.holo_blue_light;
            backgroundDrawableResourceId = 0;
            backgroundColorValue = -1;
            isTileEnabled = false;
            textColorResourceId = android.R.color.white;
            heightInPixels = LayoutParams.WRAP_CONTENT;
            widthInPixels = LayoutParams.MATCH_PARENT;
            gravity = Gravity.CENTER;
            imageDrawable = null;
            imageResId = 0;
            imageScaleType = ImageView.ScaleType.FIT_XY;
        }

        public Builder(final Style baseStyle) {
            configuration = baseStyle.configuration;
            backgroundColorValue = baseStyle.backgroundColorValue;
            backgroundColorResourceId = baseStyle.backgroundColorResourceId;
            backgroundDrawableResourceId = baseStyle.backgroundDrawableResourceId;
            isTileEnabled = baseStyle.isTileEnabled;
            textColorResourceId = baseStyle.textColorResourceId;
            heightInPixels = baseStyle.heightInPixels;
            heightDimensionResId = baseStyle.heightDimensionResId;
            widthInPixels = baseStyle.widthInPixels;
            widthDimensionResId = baseStyle.widthDimensionResId;
            gravity = baseStyle.gravity;
            imageDrawable = baseStyle.imageDrawable;
            textSize = baseStyle.textSize;
            textShadowColorResId = baseStyle.textShadowColorResId;
            textShadowRadius = baseStyle.textShadowRadius;
            textShadowDx = baseStyle.textShadowDx;
            textShadowDy = baseStyle.textShadowDy;
            textAppearanceResId = baseStyle.textAppearanceResId;
            imageResId = baseStyle.imageResId;
            imageScaleType = baseStyle.imageScaleType;
            paddingInPixels = baseStyle.paddingInPixels;
            paddingDimensionResId = baseStyle.paddingDimensionResId;
        }

        public Builder setConfiguration(Configuration configuration) {
            this.configuration = configuration;
            return this;
        }

        public Builder setBackgroundColor(int backgroundColorResourceId) {
            this.backgroundColorResourceId = backgroundColorResourceId;

            return this;
        }

        public Builder setBackgroundColorValue(int backgroundColorValue) {
            this.backgroundColorValue = backgroundColorValue;
            return this;
        }

        public Builder setBackgroundDrawable(int backgroundDrawableResourceId) {
            this.backgroundDrawableResourceId = backgroundDrawableResourceId;

            return this;
        }

        public Builder setHeight(int height) {
            this.heightInPixels = height;

            return this;
        }

        public Builder setHeightDimensionResId(int heightDimensionResId) {
            this.heightDimensionResId = heightDimensionResId;

            return this;
        }

        public Builder setWidth(int width) {
            this.widthInPixels = width;

            return this;
        }

        public Builder setWidthDimensionResId(int widthDimensionResId) {
            this.widthDimensionResId = widthDimensionResId;

            return this;
        }

        public Builder setTileEnabled(boolean isTileEnabled) {
            this.isTileEnabled = isTileEnabled;

            return this;
        }

        public Builder setTextColor(int textColor) {
            this.textColorResourceId = textColor;

            return this;
        }

        public Builder setGravity(int gravity) {
            this.gravity = gravity;

            return this;
        }

        public Builder setImageDrawable(Drawable imageDrawable) {
            this.imageDrawable = imageDrawable;

            return this;
        }

        public Builder setImageResource(int imageResId) {
            this.imageResId = imageResId;

            return this;
        }

        public Builder setTextSize(int textSize) {
            this.textSize = textSize;
            return this;
        }

        public Builder setTextShadowColor(int textShadowColorResId) {
            this.textShadowColorResId = textShadowColorResId;
            return this;
        }

        public Builder setTextShadowRadius(float textShadowRadius) {
            this.textShadowRadius = textShadowRadius;
            return this;
        }

        public Builder setTextShadowDx(float textShadowDx) {
            this.textShadowDx = textShadowDx;
            return this;
        }

        public Builder setTextShadowDy(float textShadowDy) {
            this.textShadowDy = textShadowDy;
            return this;
        }

        public Builder setTextAppearance(int textAppearanceResId) {
            this.textAppearanceResId = textAppearanceResId;
            return this;
        }

        public Builder setImageScaleType(ImageView.ScaleType imageScaleType) {
            this.imageScaleType = imageScaleType;
            return this;
        }

        public Builder setPaddingInPixels(int padding) {
            this.paddingInPixels = padding;
            return this;
        }

        public Builder setPaddingDimensionResId(int paddingResId) {
            this.paddingDimensionResId = paddingResId;
            return this;
        }

        public Style build() {
            return new Style(this);
        }
    }

    @Override
    public String toString() {
        return "Style{" +
                "configuration=" + configuration +
                ", backgroundColorResourceId=" + backgroundColorResourceId +
                ", backgroundDrawableResourceId=" + backgroundDrawableResourceId +
                ", backgroundColorValue=" + backgroundColorValue +
                ", isTileEnabled=" + isTileEnabled +
                ", textColorResourceId=" + textColorResourceId +
                ", heightInPixels=" + heightInPixels +
                ", heightDimensionResId=" + heightDimensionResId +
                ", widthInPixels=" + widthInPixels +
                ", widthDimensionResId=" + widthDimensionResId +
                ", gravity=" + gravity +
                ", imageDrawable=" + imageDrawable +
                ", imageResId=" + imageResId +
                ", imageScaleType=" + imageScaleType +
                ", textSize=" + textSize +
                ", textShadowColorResId=" + textShadowColorResId +
                ", textShadowRadius=" + textShadowRadius +
                ", textShadowDy=" + textShadowDy +
                ", textShadowDx=" + textShadowDx +
                ", textAppearanceResId=" + textAppearanceResId +
                ", paddingInPixels=" + paddingInPixels +
                ", paddingDimensionResId=" + paddingDimensionResId +
                '}';
    }
}
