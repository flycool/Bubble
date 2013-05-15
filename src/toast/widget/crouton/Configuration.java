
package toast.widget.crouton;

/**
 * Allows configuring a {@link Crouton}s behaviour aside from the actual view,
 * which is defined via {@link Style}.
 * <p/>
 * This allows to re-use a {@link Style} while modifying parameters that only
 * have to be applied when the {@link Crouton} is being displayed.
 */
public class Configuration {

    public static final int DURATION_INFINITE = -1;
    public static final int DURATION_SHORT = 2000;
    public static final int DURATION_LONG = 4000;

    public static final Configuration DEFAULT;
    static {
        DEFAULT = new Builder().setDuration(DURATION_SHORT).builder();
    }

    final int durationInMilliseconds;
    final int inAnimationResId;
    final int outAnimationResId;

    public Configuration(Builder builder) {
        this.durationInMilliseconds = builder.durationInMilliseconds;
        this.inAnimationResId = builder.inAnimationResId;
        this.outAnimationResId = builder.outAnimationResId;
    }

    /** Creates a {@link Builder} to build a {@link Configuration} upon. */
    public static class Builder {
        private int durationInMilliseconds = DURATION_SHORT;
        private int inAnimationResId = 0;
        private int outAnimationResId = 0;

        public Builder setDuration(final int duration) {
            this.durationInMilliseconds = duration;
            return this;
        }

        public Builder setInAnimation(final int inAnimationResId) {
            this.inAnimationResId = inAnimationResId;
            return this;
        }

        public Builder setOutAnimation(final int outAnimationResId) {
            this.outAnimationResId = outAnimationResId;
            return this;
        }

        public Configuration builder() {
            return new Configuration(this);
        }
    }

    @Override
    public String toString() {
        return "Configuration{" +
            "durationInMilliseconds=" + durationInMilliseconds +
            ", inAnimationResId=" + inAnimationResId +
            ", outAnimationResId=" + outAnimationResId +
            '}';
    }
}
