package jose.johnson.jose.mazeme;

import android.graphics.Canvas;

import javax.security.auth.callback.Callback;

/**
 * Created by Jose Johnson on 16/1/2018.
 */

public interface GameObject {
    public void draw(Canvas canvas);
    public void update();
}
