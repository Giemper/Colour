package magdaleno.guillermo.colour;

import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;
import android.widget.ImageView;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends ActionBarActivity {

    float x = 0, y = 0, z = 0, s = 0;
    int r, g, b = 0;
    public int p_width, p_height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        p_width=this.getWindowManager().getDefaultDisplay().getWidth();
        p_height=this.getWindowManager().getDefaultDisplay().getHeight();
        
        
        //Start Color (The first color the user sees as it opens the app)
        int random_r = (int)Math.floor(Math.random()*257) - 1;
        int random_g = (int)Math.floor(Math.random()*257) - 1;
        int random_b = (int)Math.floor(Math.random()*257) - 1;

        paint_color(random_r, random_g, random_b);
    }
    
    //Reads finger position
    public boolean onTouchEvent(MotionEvent event) {
        int x_forOnTouch = Math.round(event.getX());
        int y_forOnTouch = Math.round(event.getY());

        box_position(x_forOnTouch, y_forOnTouch);
        paint_color(r, g, b);

        splash();
        
        return true;

    }
    
    //Calculates the color depending on the coordinates
    public void box_position(int x_touch, int y_touch){
        if((x_touch >= 100) && (x_touch < p_width - 99) )
            x = x_touch - 100;
        if((y_touch >= 150) && (y_touch < p_height - 149))
            y = y_touch - 150;
        z = ((Math.round(x) ^ 2) + (Math.round(y) ^ 2)) ^ (1/2);

        r = Math.round((x / (p_width-198)) * 255);
        g = Math.round((y / (p_height-298)) * (255));
        b = ((r^2) + (g^2)) ^ (1/2);

        if(r >= 127)
            if(g >= 127)
                if(r < g) {
                    b = 255 - b; //Seccion 2
                    if(b < 0)
                        b = 0;
                }
                else
                    b = b/2;
            else
                if(r <= (255 - g)) {
                    b = 255 - b; //Seccion 4
                    if(b < 0)
                        b = 0;
                }
        else
            if(g >= 127.5)
                if(g <= (255 - r)) {
                    b = 255 - b; //Seccion 6
                    if(b < 0)
                        b = 0;
                }
                else
                    b = ((r^2) + (g^2)) ^ (1/2); // Seccion 5
            else
                if (g >= r)
                    b = ((r^2) + (g^2)) ^ (1/2); // Seccion 8
    }
    
    //Paints the background of the app
    public void paint_color(int r, int g, int b){
        ImageView i = (ImageView) findViewById(R.id.imageView);
        ImageView box = (ImageView) findViewById(R.id.light);
        int ra = r * 2 / 4;
        int ga = g * 2 / 4;
        int ba = b * 2 / 4;

        i.setBackgroundColor(Color.rgb(r, g, b));
        box.setBackgroundColor(Color.rgb(ra, ga, ba));

        String hex = Integer.toHexString(Color.rgb(r, g, b));
        String hexUpper = ("#" + hex.charAt(2)+hex.charAt(3)+hex.charAt(4)+hex.charAt(5)+hex.charAt(6)+hex.charAt(7)).toUpperCase();
        TextView Code = (TextView) findViewById(R.id.Hex);
        Code.setText(hexUpper);

        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= Build.VERSION_CODES.LOLLIPOP){
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.rgb(ra, ga, ba));
            window.setNavigationBarColor(Color.rgb(r,g,b));
        }
    }

    //Removes App Name and instruction after first click
    public void splash(){
        if(s == 0) {
            //Initial Splash Screen
            AlphaAnimation ani = new AlphaAnimation(1.0f, 0.0f);
            ani.setDuration(500);
            ani.setFillAfter(true);
            TextView v = (TextView) findViewById(R.id.v);
            TextView t = (TextView) findViewById(R.id.Touch);
            v.startAnimation(ani);
            t.startAnimation(ani);
            s++;
        }
    }

}
