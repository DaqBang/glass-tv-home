package com.gathin.tvhome;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;

/**
 * The Nothing OS background: a plain colour with a faint grid of dots over it.
 *
 * <p>One dot is drawn once into a tile the size of the grid's step, and a repeating shader paints
 * it across the screen, so a frame costs one fill and one shaded rect, with no allocation.
 */
final class DotGridDrawable extends Drawable {

    private static final float STEP_DP = 22f;
    private static final float DOT_DP = 1.6f;
    private static final int DOT_COLOR = 0x26FFFFFF;

    private final int background;
    private final Paint dots = new Paint();

    DotGridDrawable(float density, int backgroundColor) {
        background = 0xFF000000 | backgroundColor;
        int step = Math.max(4, Math.round(STEP_DP * density));
        Bitmap tile = Bitmap.createBitmap(step, step, Bitmap.Config.ARGB_8888);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(DOT_COLOR);
        new Canvas(tile).drawCircle(step / 2f, step / 2f, DOT_DP * density / 2f, p);
        dots.setShader(new BitmapShader(tile, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT));
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawColor(background);
        canvas.drawRect(getBounds(), dots);
    }

    @Override
    public void setAlpha(int alpha) {
        dots.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        dots.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.OPAQUE;
    }
}
