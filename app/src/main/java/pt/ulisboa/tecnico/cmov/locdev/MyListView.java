package pt.ulisboa.tecnico.cmov.locdev;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ListView;


public class MyListView extends ListView {

    private android.view.ViewGroup.LayoutParams params;
    private int oldCount = 0;

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        if (getCount() != oldCount)
        {
            int height = getChildAt(0).getHeight() + 1 ;
            oldCount = getCount();
            params = getLayoutParams();
            params.height = getCount() * height;
            params.height = 1000;
            setLayoutParams(params);
        }
        super.onDraw(canvas);
    }

}
